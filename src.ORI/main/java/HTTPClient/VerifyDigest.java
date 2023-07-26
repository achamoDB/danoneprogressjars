// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Vector;
import java.io.IOException;

class VerifyDigest implements HashVerifier, GlobalConstants
{
    private String HA1;
    private String nonce;
    private String method;
    private String uri;
    private String hdr;
    private RoResponse resp;
    
    public VerifyDigest(final String ha1, final String nonce, final String method, final String uri, final String hdr, final RoResponse resp) {
        this.HA1 = ha1;
        this.nonce = nonce;
        this.method = method;
        this.uri = uri;
        this.hdr = hdr;
        this.resp = resp;
    }
    
    public void verifyHash(byte[] digest, final long n) throws IOException {
        String s = this.resp.getHeader(this.hdr);
        if (s == null) {
            s = this.resp.getTrailer(this.hdr);
        }
        if (s == null) {
            return;
        }
        Vector header;
        try {
            header = Util.parseHeader(s);
        }
        catch (ParseException ex) {
            throw new IOException(ex.toString());
        }
        final HttpHeaderElement element = Util.getElement(header, "digest");
        if (element == null || element.getValue() == null) {
            return;
        }
        final byte[] unHex = DefaultAuthHandler.unHex(element.getValue());
        digest = MD5.digest(this.HA1 + ":" + this.nonce + ":" + this.method + ":" + header_val("Date", this.resp) + ":" + MD5.hexDigest(this.uri + ":" + header_val("Content-Type", this.resp) + ":" + header_val("Content-Length", this.resp) + ":" + header_val("Content-Encoding", this.resp) + ":" + header_val("Last-Modified", this.resp) + ":" + header_val("Expires", this.resp)) + ":" + MD5.toHex(digest));
        for (int i = 0; i < digest.length; ++i) {
            if (digest[i] != unHex[i]) {
                throw new IOException("MD5-Digest mismatch: expected " + DefaultAuthHandler.hex(unHex) + " but calculated " + DefaultAuthHandler.hex(digest));
            }
        }
        Log.write(8, "Auth:  digest from " + this.hdr + " successfully verified");
    }
    
    private static final String header_val(final String s, final RoResponse roResponse) throws IOException {
        final String header = roResponse.getHeader(s);
        final String trailer = roResponse.getTrailer(s);
        return (header != null) ? header : ((trailer != null) ? trailer : "");
    }
}
