// 
// Decompiled by Procyon v0.5.36
// 

class psc_e7 extends psc_e3
{
    private static psc_e7 a;
    
    private psc_e7() {
    }
    
    public static psc_e3 a() {
        if (psc_e7.a == null) {
            psc_e7.a = new psc_e7();
        }
        return psc_e7.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (n == 0) {
            if (array[n].startsWith("HMAC", 0)) {
                return new psc_jd();
            }
            return null;
        }
        else if (n == 1) {
            if (array[n].startsWith("MD5", 0)) {
                return new psc_ef();
            }
            if (array[n].startsWith("SHA1", 0)) {
                return new psc_ew();
            }
            if (array[n].startsWith("SHA224", 0)) {
                return new psc_je();
            }
            if (array[n].startsWith("SHA256", 0)) {
                return new psc_jf();
            }
            if (array[n].startsWith("SHA384", 0)) {
                return new psc_jg();
            }
            if (array[n].startsWith("SHA512", 0)) {
                return new psc_jh();
            }
            return null;
        }
        else {
            if (n != 2) {
                return null;
            }
            if (array[n].startsWith("PKCS12V1PBE", 0)) {
                return new psc_ji();
            }
            if (array[n].startsWith("PKCS12PBE", 0)) {
                return new psc_jk();
            }
            if (array[n].startsWith("PKIXPBE", 0)) {
                return new psc_jl();
            }
            if (array[n].startsWith("PKCS5V2PBE", 0)) {
                return new psc_jl();
            }
            return null;
        }
    }
    
    static {
        psc_e7.a = null;
    }
}
