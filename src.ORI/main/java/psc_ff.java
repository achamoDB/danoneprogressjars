// 
// Decompiled by Procyon v0.5.36
// 

class psc_ff extends psc_e3
{
    private static psc_ff a;
    
    private psc_ff() {
    }
    
    public static psc_e3 a() {
        if (psc_ff.a == null) {
            psc_ff.a = new psc_ff();
        }
        return psc_ff.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        switch (n) {
            case 0: {
                if (array[n].startsWith("SHA1", 0)) {
                    return new psc_ew();
                }
                if (array[n].startsWith("MD5", 0)) {
                    return new psc_ef();
                }
                if (array[n].startsWith("MD2", 0)) {
                    return new psc_jm();
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
                if (array[n].startsWith("RIPEMD160", 0)) {
                    return new psc_jn();
                }
                if (array[n].startsWith("NoDigest", 0)) {
                    return new psc_kc();
                }
                return null;
            }
            case 1: {
                if (array[n].startsWith("RSA", 0)) {
                    return new psc_i1();
                }
                if (array[n].startsWith("X931RSA", 0)) {
                    return new psc_kd();
                }
                if (array[n].startsWith("DSA", 0)) {
                    return new psc_ke();
                }
                return null;
            }
            case 2: {
                if (array[n].startsWith("PKCS1Block01Pad", 0)) {
                    return new psc_kf();
                }
                if (array[n].startsWith("X931Pad", 0)) {
                    return new psc_i5();
                }
                if (array[n].startsWith("PKCS1V2PSS", 0)) {
                    return new psc_kg();
                }
                if (array[n].startsWith("NoPad", 0)) {
                    return new psc_i6();
                }
                return null;
            }
            case 3: {
                if (array[n].startsWith("MGF1", 0)) {
                    return new psc_i7();
                }
                return null;
            }
            case 4: {
                if (array[n].startsWith("SHA1", 0)) {
                    return new psc_ew();
                }
                return null;
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        psc_ff.a = null;
    }
}
