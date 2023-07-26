// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_bf extends pscl_bb implements pscl_i
{
    public pscl_bf() {
        super("MD5");
    }
    
    public pscl_i r() {
        return new pscl_bf();
    }
    
    public boolean n() {
        return true;
    }
    
    public String l() {
        return "RSA_Export_With_RC4_40_MD5";
    }
    
    public byte[] b(final int n) {
        return new byte[] { 0, 3 };
    }
}
