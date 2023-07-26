// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;

final class ResponseHandler
{
    RespInputStream stream;
    Response resp;
    Request request;
    boolean eof;
    IOException exception;
    private byte[] endbndry;
    private int[] end_cmp;
    
    ResponseHandler(final Response resp, final Request request, final StreamDemultiplexor streamDemultiplexor) {
        this.eof = false;
        this.exception = null;
        this.endbndry = null;
        this.end_cmp = null;
        this.resp = resp;
        this.request = request;
        this.stream = new RespInputStream(streamDemultiplexor, this);
        Log.write(4, "Demux: Opening stream " + this.stream.hashCode() + " for demux (" + streamDemultiplexor.hashCode() + ")");
    }
    
    byte[] getEndBoundary(final BufferedInputStream bufferedInputStream) throws IOException, ParseException {
        if (this.endbndry == null) {
            this.setupBoundary(bufferedInputStream);
        }
        return this.endbndry;
    }
    
    int[] getEndCompiled(final BufferedInputStream bufferedInputStream) throws IOException, ParseException {
        if (this.end_cmp == null) {
            this.setupBoundary(bufferedInputStream);
        }
        return this.end_cmp;
    }
    
    void setupBoundary(final BufferedInputStream bufferedInputStream) throws IOException, ParseException {
        this.endbndry = ("--" + Util.getParameter("boundary", this.resp.getHeader("Content-Type")) + "--\r\n").getBytes("8859_1");
        this.end_cmp = Util.compile_search(this.endbndry);
        bufferedInputStream.markForSearch();
    }
}
