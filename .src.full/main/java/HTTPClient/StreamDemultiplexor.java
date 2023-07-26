// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.SocketException;
import java.io.InputStream;
import java.io.EOFException;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.net.Socket;

class StreamDemultiplexor implements GlobalConstants
{
    private int Protocol;
    private HTTPConnection Connection;
    private BufferedInputStream Stream;
    private Socket Sock;
    private ResponseHandler MarkedForClose;
    private SocketTimeout.TimeoutEntry Timer;
    private static SocketTimeout TimerThread;
    private static Object cleanup;
    private LinkedList RespHandlerList;
    private long chunk_len;
    private int cur_timeout;
    
    StreamDemultiplexor(final int protocol, final Socket socket, final HTTPConnection connection) throws IOException {
        this.Sock = null;
        this.Timer = null;
        this.cur_timeout = 0;
        this.Protocol = protocol;
        this.Connection = connection;
        this.RespHandlerList = new LinkedList();
        this.init(socket);
    }
    
    private void init(final Socket sock) throws IOException {
        Log.write(4, "Demux: Initializing Stream Demultiplexor (" + this.hashCode() + ")");
        this.Sock = sock;
        this.Stream = new BufferedInputStream(sock.getInputStream());
        this.MarkedForClose = null;
        this.chunk_len = -1L;
        (this.Timer = StreamDemultiplexor.TimerThread.setTimeout(this)).hyber();
    }
    
    void register(final Response response, final Request request) throws RetryException {
        synchronized (this.RespHandlerList) {
            if (this.Sock == null) {
                throw new RetryException();
            }
            this.RespHandlerList.addToEnd(new ResponseHandler(response, request, this));
        }
    }
    
    RespInputStream getStream(final Response response) {
        ResponseHandler responseHandler;
        synchronized (this.RespHandlerList) {
            for (responseHandler = (ResponseHandler)this.RespHandlerList.enumerate(); responseHandler != null && responseHandler.resp != response; responseHandler = (ResponseHandler)this.RespHandlerList.next()) {}
        }
        if (responseHandler != null) {
            return responseHandler.stream;
        }
        return null;
    }
    
    void restartTimer() {
        if (this.Timer != null) {
            this.Timer.reset();
        }
    }
    
    int read(final byte[] array, final int n, int n2, final ResponseHandler responseHandler, final int cur_timeout) throws IOException {
        if (responseHandler.exception != null) {
            responseHandler.exception.fillInStackTrace();
            throw responseHandler.exception;
        }
        if (responseHandler.eof) {
            return -1;
        }
        ResponseHandler responseHandler2;
        while ((responseHandler2 = (ResponseHandler)this.RespHandlerList.getFirst()) != null && responseHandler2 != responseHandler) {
            try {
                responseHandler2.stream.readAll(cur_timeout);
                continue;
            }
            catch (IOException ex) {
                if (ex instanceof InterruptedIOException) {
                    throw ex;
                }
                responseHandler.exception.fillInStackTrace();
                throw responseHandler.exception;
            }
            break;
        }
        synchronized (this) {
            if (responseHandler.exception != null) {
                responseHandler.exception.fillInStackTrace();
                throw responseHandler.exception;
            }
            if (responseHandler.resp.cd_type != 1) {
                Log.write(4, "Demux: Reading for stream " + responseHandler.stream.hashCode());
            }
            if (this.Timer != null) {
                this.Timer.hyber();
            }
            try {
                if (cur_timeout != this.cur_timeout) {
                    Log.write(4, "Demux: Setting timeout to " + cur_timeout + " ms");
                    this.Sock.setSoTimeout(cur_timeout);
                    this.cur_timeout = cur_timeout;
                }
                int n3 = 0;
                switch (responseHandler.resp.cd_type) {
                    case 1: {
                        n3 = this.Stream.read(array, n, n2);
                        if (n3 == -1) {
                            throw new EOFException("Premature EOF encountered");
                        }
                        break;
                    }
                    case 2: {
                        n3 = -1;
                        this.close(responseHandler);
                        break;
                    }
                    case 3: {
                        n3 = this.Stream.read(array, n, n2);
                        if (n3 == -1) {
                            this.close(responseHandler);
                            break;
                        }
                        break;
                    }
                    case 4: {
                        final int contentLength = responseHandler.resp.ContentLength;
                        if (n2 > contentLength - responseHandler.stream.count) {
                            n2 = contentLength - responseHandler.stream.count;
                        }
                        n3 = this.Stream.read(array, n, n2);
                        if (n3 == -1) {
                            throw new EOFException("Premature EOF encountered");
                        }
                        if (responseHandler.stream.count + n3 == contentLength) {
                            this.close(responseHandler);
                            break;
                        }
                        break;
                    }
                    case 5: {
                        if (this.chunk_len == -1L) {
                            this.chunk_len = Codecs.getChunkLength(this.Stream);
                        }
                        if (this.chunk_len <= 0L) {
                            responseHandler.resp.readTrailers(this.Stream);
                            n3 = -1;
                            this.close(responseHandler);
                            this.chunk_len = -1L;
                            break;
                        }
                        if (n2 > this.chunk_len) {
                            n2 = (int)this.chunk_len;
                        }
                        n3 = this.Stream.read(array, n, n2);
                        if (n3 == -1) {
                            throw new EOFException("Premature EOF encountered");
                        }
                        this.chunk_len -= n3;
                        if (this.chunk_len == 0L) {
                            this.Stream.read();
                            this.Stream.read();
                            this.chunk_len = -1L;
                            break;
                        }
                        break;
                    }
                    case 6: {
                        final byte[] endBoundary = responseHandler.getEndBoundary(this.Stream);
                        final int[] endCompiled = responseHandler.getEndCompiled(this.Stream);
                        n3 = this.Stream.read(array, n, n2);
                        if (n3 == -1) {
                            throw new EOFException("Premature EOF encountered");
                        }
                        final int pastEnd = this.Stream.pastEnd(endBoundary, endCompiled);
                        if (pastEnd != -1) {
                            n3 -= pastEnd;
                            this.close(responseHandler);
                            break;
                        }
                        break;
                    }
                    default: {
                        throw new Error("Internal Error in StreamDemultiplexor: Invalid cd_type " + responseHandler.resp.cd_type);
                    }
                }
                this.restartTimer();
                return n3;
            }
            catch (InterruptedIOException ex2) {
                this.restartTimer();
                throw ex2;
            }
            catch (IOException ex3) {
                Log.write(4, "Demux: ", ex3);
                this.close(ex3, true);
                throw responseHandler.exception;
            }
            catch (ParseException ex4) {
                Log.write(4, "Demux: ", ex4);
                this.close(new IOException(ex4.toString()), true);
                throw responseHandler.exception;
            }
        }
    }
    
    synchronized long skip(final long n, final ResponseHandler responseHandler) throws IOException {
        if (responseHandler.exception != null) {
            responseHandler.exception.fillInStackTrace();
            throw responseHandler.exception;
        }
        if (responseHandler.eof) {
            return 0L;
        }
        final int read = this.read(new byte[(int)n], 0, (int)n, responseHandler, 0);
        if (read == -1) {
            return 0L;
        }
        return read;
    }
    
    synchronized int available(final ResponseHandler responseHandler) throws IOException {
        if (responseHandler != null && responseHandler.exception != null) {
            responseHandler.exception.fillInStackTrace();
            throw responseHandler.exception;
        }
        if (responseHandler != null && responseHandler.eof) {
            return 0;
        }
        final int available = this.Stream.available();
        if (responseHandler == null) {
            return available;
        }
        switch (responseHandler.resp.cd_type) {
            case 2: {
                return 0;
            }
            case 1: {
                return (available > 0) ? 1 : 0;
            }
            case 3: {
                return available;
            }
            case 4: {
                final int n = responseHandler.resp.ContentLength - responseHandler.stream.count;
                return (available < n) ? available : n;
            }
            case 5: {
                return available;
            }
            case 6: {
                return available;
            }
            default: {
                throw new Error("Internal Error in StreamDemultiplexor: Invalid cd_type " + responseHandler.resp.cd_type);
            }
        }
    }
    
    synchronized void close(final IOException ex, final boolean b) {
        if (this.Sock == null) {
            return;
        }
        Log.write(4, "Demux: Closing all streams and socket (" + this.hashCode() + ")");
        try {
            this.Stream.close();
        }
        catch (IOException ex2) {}
        try {
            this.Sock.close();
        }
        catch (IOException ex3) {}
        this.Sock = null;
        if (this.Timer != null) {
            this.Timer.kill();
            this.Timer = null;
        }
        this.Connection.DemuxList.remove(this);
        if (ex != null) {
            synchronized (this.RespHandlerList) {
                this.retry_requests(ex, b);
            }
        }
    }
    
    private void retry_requests(final IOException ex, final boolean conn_reset) {
        RetryException first = null;
        RetryException ex2 = null;
        for (ResponseHandler responseHandler = (ResponseHandler)this.RespHandlerList.enumerate(); responseHandler != null; responseHandler = (ResponseHandler)this.RespHandlerList.next()) {
            if (responseHandler.resp.got_headers) {
                responseHandler.exception = ex;
            }
            else {
                final RetryException exception = new RetryException(ex.getMessage());
                if (first == null) {
                    first = exception;
                }
                exception.request = responseHandler.request;
                exception.response = responseHandler.resp;
                exception.exception = ex;
                exception.conn_reset = conn_reset;
                exception.first = first;
                exception.addToListAfter(ex2);
                ex2 = exception;
                responseHandler.exception = exception;
            }
            this.RespHandlerList.remove(responseHandler);
        }
    }
    
    private void close(final ResponseHandler responseHandler) {
        synchronized (this.RespHandlerList) {
            if (responseHandler != this.RespHandlerList.getFirst()) {
                return;
            }
            Log.write(4, "Demux: Closing stream " + responseHandler.stream.hashCode());
            responseHandler.eof = true;
            this.RespHandlerList.remove(responseHandler);
        }
        if (responseHandler == this.MarkedForClose) {
            this.close(new IOException("Premature end of Keep-Alive"), false);
        }
        else {
            this.closeSocketIfAllStreamsClosed();
        }
    }
    
    synchronized void closeSocketIfAllStreamsClosed() {
        synchronized (this.RespHandlerList) {
            for (ResponseHandler responseHandler = (ResponseHandler)this.RespHandlerList.enumerate(); responseHandler != null && responseHandler.stream.closed; responseHandler = (ResponseHandler)this.RespHandlerList.next()) {
                if (responseHandler == this.MarkedForClose) {
                    ResponseHandler responseHandler2;
                    do {
                        responseHandler2 = (ResponseHandler)this.RespHandlerList.getFirst();
                        this.RespHandlerList.remove(responseHandler2);
                    } while (responseHandler2 != responseHandler);
                    this.close(new IOException("Premature end of Keep-Alive"), false);
                    return;
                }
            }
        }
    }
    
    synchronized Socket getSocket() {
        if (this.MarkedForClose != null) {
            return null;
        }
        if (this.Timer != null) {
            this.Timer.hyber();
        }
        return this.Sock;
    }
    
    synchronized void markForClose(final Response response) {
        synchronized (this.RespHandlerList) {
            if (this.RespHandlerList.getFirst() == null) {
                this.close(new IOException("Premature end of Keep-Alive"), false);
                return;
            }
            if (this.Timer != null) {
                this.Timer.kill();
                this.Timer = null;
            }
            ResponseHandler markedForClose = null;
            for (ResponseHandler markedForClose2 = (ResponseHandler)this.RespHandlerList.enumerate(); markedForClose2 != null; markedForClose2 = (ResponseHandler)this.RespHandlerList.next()) {
                if (markedForClose2.resp == response) {
                    this.MarkedForClose = markedForClose2;
                    Log.write(4, "Demux: stream " + response.inp_stream.hashCode() + " marked for close");
                    this.closeSocketIfAllStreamsClosed();
                    return;
                }
                if (this.MarkedForClose == markedForClose2) {
                    return;
                }
                markedForClose = markedForClose2;
            }
            if (markedForClose == null) {
                return;
            }
            this.MarkedForClose = markedForClose;
            this.closeSocketIfAllStreamsClosed();
            Log.write(4, "Demux: stream " + markedForClose.stream.hashCode() + " marked for close");
        }
    }
    
    void abort() {
        Log.write(4, "Demux: Aborting socket (" + this.hashCode() + ")");
        synchronized (this.RespHandlerList) {
            for (ResponseHandler responseHandler = (ResponseHandler)this.RespHandlerList.enumerate(); responseHandler != null; responseHandler = (ResponseHandler)this.RespHandlerList.next()) {
                if (responseHandler.resp.http_resp != null) {
                    responseHandler.resp.http_resp.markAborted();
                }
                if (responseHandler.exception == null) {
                    responseHandler.exception = new IOException("Request aborted by user");
                }
            }
            if (this.Sock != null) {
                try {
                    try {
                        this.Sock.setSoLinger(false, 0);
                    }
                    catch (SocketException ex) {}
                    try {
                        this.Stream.close();
                    }
                    catch (IOException ex2) {}
                    try {
                        this.Sock.close();
                    }
                    catch (IOException ex3) {}
                    this.Sock = null;
                    if (this.Timer != null) {
                        this.Timer.kill();
                        this.Timer = null;
                    }
                }
                catch (NullPointerException ex4) {}
                this.Connection.DemuxList.remove(this);
            }
        }
    }
    
    protected void finalize() throws Throwable {
        this.close(null, false);
        super.finalize();
    }
    
    public String toString() {
        String str = null;
        switch (this.Protocol) {
            case 0: {
                str = "HTTP";
                break;
            }
            case 1: {
                str = "HTTPS";
                break;
            }
            case 2: {
                str = "SHTTP";
                break;
            }
            case 3: {
                str = "HTTP_NG";
                break;
            }
            default: {
                throw new Error("HTTPClient Internal Error: invalid protocol " + this.Protocol);
            }
        }
        return this.getClass().getName() + "[Protocol=" + str + "]";
    }
    
    static {
        StreamDemultiplexor.TimerThread = null;
        (StreamDemultiplexor.TimerThread = new SocketTimeout(60)).start();
        StreamDemultiplexor.cleanup = new Object() {
            private final SocketTimeout timer = StreamDemultiplexor.TimerThread;
            
            protected void finalize() {
                this.timer.kill();
            }
        };
    }
}
