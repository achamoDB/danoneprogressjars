// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;

final class RespInputStream extends InputStream implements GlobalConstants
{
    private static boolean dontTimeoutBody;
    private StreamDemultiplexor demux;
    private ResponseHandler resph;
    boolean closed;
    private boolean dont_truncate;
    private byte[] buffer;
    private boolean interrupted;
    private int offset;
    private int end;
    int count;
    private byte[] ch;
    
    RespInputStream(final StreamDemultiplexor demux, final ResponseHandler resph) {
        this.demux = null;
        this.closed = false;
        this.dont_truncate = false;
        this.buffer = null;
        this.interrupted = false;
        this.offset = 0;
        this.end = 0;
        this.count = 0;
        this.ch = new byte[1];
        this.demux = demux;
        this.resph = resph;
    }
    
    public synchronized int read() throws IOException {
        if (this.read(this.ch, 0, 1) == 1) {
            return this.ch[0] & 0xFF;
        }
        return -1;
    }
    
    public synchronized int read(final byte[] array, final int n, int n2) throws IOException {
        if (this.closed) {
            return -1;
        }
        final int n3 = this.end - this.offset;
        if (this.buffer == null || (n3 == 0 && this.interrupted)) {
            if (this.resph.resp.cd_type != 1) {
                Log.write(4, "RspIS: Reading stream " + this.hashCode());
            }
            int n4;
            if (RespInputStream.dontTimeoutBody && this.resph.resp.cd_type != 1) {
                n4 = this.demux.read(array, n, n2, this.resph, 0);
            }
            else {
                n4 = this.demux.read(array, n, n2, this.resph, this.resph.resp.timeout);
            }
            if (n4 != -1 && this.resph.resp.got_headers) {
                this.count += n4;
            }
            return n4;
        }
        if (n3 == 0) {
            return -1;
        }
        n2 = ((n2 > n3) ? n3 : n2);
        System.arraycopy(this.buffer, this.offset, array, n, n2);
        this.offset += n2;
        return n2;
    }
    
    public synchronized long skip(long n) throws IOException {
        if (this.closed) {
            return 0L;
        }
        final int n2 = this.end - this.offset;
        if (this.buffer != null && (n2 != 0 || !this.interrupted)) {
            n = ((n > n2) ? n2 : n);
            this.offset += (int)n;
            return n;
        }
        final long skip = this.demux.skip(n, this.resph);
        if (this.resph.resp.got_headers) {
            this.count += (int)skip;
        }
        return skip;
    }
    
    public synchronized int available() throws IOException {
        if (this.closed) {
            return 0;
        }
        if (this.buffer != null && (this.end - this.offset != 0 || !this.interrupted)) {
            return this.end - this.offset;
        }
        return this.demux.available(this.resph);
    }
    
    public synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.dont_truncate && (this.buffer == null || this.interrupted)) {
                this.readAll(this.resph.resp.timeout);
            }
            Log.write(4, "RspIS: User closed stream " + this.hashCode());
            this.demux.closeSocketIfAllStreamsClosed();
            if (this.dont_truncate) {
                try {
                    this.resph.resp.http_resp.invokeTrailerHandlers(false);
                }
                catch (ModuleException ex) {
                    throw new IOException(ex.toString());
                }
            }
        }
    }
    
    protected void finalize() throws Throwable {
        try {
            this.close();
        }
        finally {
            super.finalize();
        }
    }
    
    void readAll(final int timeout) throws IOException {
        Log.write(4, "RspIS: Read-all on stream " + this.hashCode());
        synchronized (this.resph.resp) {
            if (!this.resph.resp.got_headers) {
                final int timeout2 = this.resph.resp.timeout;
                this.resph.resp.timeout = timeout;
                this.resph.resp.getStatusCode();
                this.resph.resp.timeout = timeout2;
            }
        }
        synchronized (this) {
            if (this.buffer != null && !this.interrupted) {
                return;
            }
            int i = 0;
            try {
                if (this.closed) {
                    this.buffer = new byte[10000];
                    do {
                        this.count += i;
                        i = this.demux.read(this.buffer, 0, this.buffer.length, this.resph, timeout);
                    } while (i != -1);
                    this.buffer = null;
                }
                else {
                    if (this.buffer == null) {
                        this.buffer = new byte[10000];
                        this.offset = 0;
                        this.end = 0;
                    }
                    while (true) {
                        final int read = this.demux.read(this.buffer, this.end, this.buffer.length - this.end, this.resph, timeout);
                        if (read < 0) {
                            break;
                        }
                        this.count += read;
                        this.end += read;
                        this.buffer = Util.resizeArray(this.buffer, this.end + 10000);
                    }
                }
            }
            catch (InterruptedIOException ex) {
                this.interrupted = true;
                throw ex;
            }
            catch (IOException ex2) {
                this.buffer = null;
            }
            this.interrupted = false;
        }
    }
    
    synchronized void dontTruncate() {
        this.dont_truncate = true;
    }
    
    static {
        RespInputStream.dontTimeoutBody = false;
        try {
            RespInputStream.dontTimeoutBody = Boolean.getBoolean("HTTPClient.dontTimeoutRespBody");
            if (RespInputStream.dontTimeoutBody) {
                Log.write(4, "RspIS: disabling timeouts when reading response body");
            }
        }
        catch (Exception ex) {}
    }
}
