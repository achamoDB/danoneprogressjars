// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Vector;
import java.io.IOException;

class VerifyRspAuth implements HashVerifier, GlobalConstants
{
    private String uri;
    private String HA1;
    private String alg;
    private String nonce;
    private String cnonce;
    private String nc;
    private String hdr;
    private RoResponse resp;
    
    public VerifyRspAuth(final String uri, final String ha1, final String alg, final String nonce, final String cnonce, final String nc, final String hdr, final RoResponse resp) {
        this.uri = uri;
        this.HA1 = ha1;
        this.alg = alg;
        this.nonce = nonce;
        this.cnonce = cnonce;
        this.nc = nc;
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
        final HttpHeaderElement element = Util.getElement(header, "qop");
        final String value;
        if (element == null || (value = element.getValue()) == null || (!value.equalsIgnoreCase("auth") && !value.equalsIgnoreCase("auth-int"))) {
            return;
        }
        final HttpHeaderElement element2 = Util.getElement(header, "rspauth");
        if (element2 == null || element2.getValue() == null) {
            return;
        }
        final byte[] unHex = DefaultAuthHandler.unHex(element2.getValue());
        final HttpHeaderElement element3 = Util.getElement(header, "cnonce");
        if (element3 != null && element3.getValue() != null && !element3.getValue().equals(this.cnonce)) {
            throw new IOException("Digest auth scheme: received wrong client-nonce '" + element3.getValue() + "' - expected '" + this.cnonce + "'");
        }
        final HttpHeaderElement element4 = Util.getElement(header, "nc");
        if (element4 != null && element4.getValue() != null && !element4.getValue().equals(this.nc)) {
            throw new IOException("Digest auth scheme: received wrong nonce-count '" + element4.getValue() + "' - expected '" + this.nc + "'");
        }
        String str;
        if (this.alg != null && this.alg.equalsIgnoreCase("MD5-sess")) {
            str = MD5.hexDigest(this.HA1 + ":" + this.nonce + ":" + this.cnonce);
        }
        else {
            str = this.HA1;
        }
        String str2 = ":" + this.uri;
        if (value.equalsIgnoreCase("auth-int")) {
            str2 = str2 + ":" + MD5.toHex(digest);
        }
        digest = MD5.digest(str + ":" + this.nonce + ":" + this.nc + ":" + this.cnonce + ":" + value + ":" + MD5.hexDigest(str2));
        for (int i = 0; i < digest.length; ++i) {
            if (digest[i] != unHex[i]) {
                throw new IOException("MD5-Digest mismatch: expected " + DefaultAuthHandler.hex(unHex) + " but calculated " + DefaultAuthHandler.hex(digest));
            }
        }
        Log.write(8, "Auth:  rspauth from " + this.hdr + " successfully verified");
    }
}
