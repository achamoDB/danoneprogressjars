// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class HttpOutputStream extends OutputStream
{
    private static final NVPair[] empty;
    private int length;
    private int rcvd;
    private Request req;
    private Response resp;
    private OutputStream os;
    private ByteArrayOutputStream bos;
    private NVPair[] trailers;
    private int con_to;
    private boolean ignore;
    
    public HttpOutputStream() {
        this.rcvd = 0;
        this.req = null;
        this.resp = null;
        this.os = null;
        this.bos = null;
        this.trailers = HttpOutputStream.empty;
        this.con_to = 0;
        this.ignore = false;
        this.length = -1;
    }
    
    public HttpOutputStream(final int length) {
        this.rcvd = 0;
        this.req = null;
        this.resp = null;
        this.os = null;
        this.bos = null;
        this.trailers = HttpOutputStream.empty;
        this.con_to = 0;
        this.ignore = false;
        if (length < 0) {
            throw new IllegalArgumentException("Length must be greater equal 0");
        }
        this.length = length;
    }
    
    void goAhead(final Request req, final OutputStream os, final int con_to) {
        this.req = req;
        this.os = os;
        this.con_to = con_to;
        if (os == null) {
            this.bos = new ByteArrayOutputStream();
        }
        Log.write(1, "OutS:  Stream ready for writing");
        if (this.bos != null) {
            Log.write(1, "OutS:  Buffering all data before sending request");
        }
    }
    
    void ignoreData(final Request req) {
        this.req = req;
        this.ignore = true;
    }
    
    synchronized Response getResponse() {
        while (this.resp == null) {
            try {
                this.wait();
            }
            catch (InterruptedException ex) {}
        }
        return this.resp;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public NVPair[] getTrailers() {
        return this.trailers;
    }
    
    public void setTrailers(final NVPair[] trailers) {
        if (trailers != null) {
            this.trailers = trailers;
        }
        else {
            this.trailers = HttpOutputStream.empty;
        }
    }
    
    public void reset() {
        this.rcvd = 0;
        this.req = null;
        this.resp = null;
        this.os = null;
        this.bos = null;
        this.con_to = 0;
        this.ignore = false;
    }
    
    public void write(final int n) throws IOException, IllegalAccessError {
        this.write(new byte[] { (byte)n }, 0, 1);
    }
    
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException, IllegalAccessError {
        if (this.req == null) {
            throw new IllegalAccessError("Stream not associated with a request");
        }
        if (this.ignore) {
            return;
        }
        if (this.length != -1 && this.rcvd + n2 > this.length) {
            final IOException ex = new IOException("Tried to write too many bytes (" + (this.rcvd + n2) + " > " + this.length + ")");
            this.req.getConnection().closeDemux(ex, false);
            this.req.getConnection().outputFinished();
            throw ex;
        }
        try {
            if (this.bos != null) {
                this.bos.write(array, n, n2);
            }
            else if (this.length != -1) {
                this.os.write(array, n, n2);
            }
            else {
                this.os.write(Codecs.chunkedEncode(array, n, n2, null, false));
            }
        }
        catch (IOException ex2) {
            this.req.getConnection().closeDemux(ex2, true);
            this.req.getConnection().outputFinished();
            throw ex2;
        }
        this.rcvd += n2;
    }
    
    public synchronized void close() throws IOException, IllegalAccessError {
        if (this.req == null) {
            throw new IllegalAccessError("Stream not associated with a request");
        }
        if (this.ignore) {
            return;
        }
        if (this.bos != null) {
            this.req.setData(this.bos.toByteArray());
            this.req.setStream(null);
            if (this.trailers.length > 0) {
                final NVPair[] headers = this.req.getHeaders();
                int length = headers.length;
                for (int i = 0; i < length; ++i) {
                    if (headers[i].getName().equalsIgnoreCase("Trailer")) {
                        System.arraycopy(headers, i + 1, headers, i, length - i - 1);
                        --length;
                    }
                }
                final NVPair[] resizeArray = Util.resizeArray(headers, length + this.trailers.length);
                System.arraycopy(this.trailers, 0, resizeArray, length, this.trailers.length);
                this.req.setHeaders(resizeArray);
            }
            Log.write(1, "OutS:  Sending request");
            try {
                this.resp = this.req.getConnection().sendRequest(this.req, this.con_to);
            }
            catch (ModuleException ex) {
                throw new IOException(ex.toString());
            }
            this.notify();
        }
        else {
            if (this.rcvd < this.length) {
                final IOException ex2 = new IOException("Premature close: only " + this.rcvd + " bytes written instead of the " + "expected " + this.length);
                this.req.getConnection().closeDemux(ex2, false);
                this.req.getConnection().outputFinished();
                throw ex2;
            }
            try {
                if (this.length == -1) {
                    if (Log.isEnabled(1) && this.trailers.length > 0) {
                        Log.write(1, "OutS:  Sending trailers:");
                        for (int j = 0; j < this.trailers.length; ++j) {
                            Log.write(1, "       " + this.trailers[j].getName() + ": " + this.trailers[j].getValue());
                        }
                    }
                    this.os.write(Codecs.chunkedEncode(null, 0, 0, this.trailers, true));
                }
                this.os.flush();
                Log.write(1, "OutS:  All data sent");
            }
            catch (IOException ex3) {
                this.req.getConnection().closeDemux(ex3, true);
                throw ex3;
            }
            finally {
                this.req.getConnection().outputFinished();
            }
        }
    }
    
    public String toString() {
        return this.getClass().getName() + "[length=" + this.length + "]";
    }
    
    static {
        empty = new NVPair[0];
    }
}
