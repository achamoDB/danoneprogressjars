// 
// Decompiled by Procyon v0.5.36
// 

class psc_fe extends psc_e3
{
    private static psc_fe a;
    
    private psc_fe() {
    }
    
    public static psc_e3 a() {
        if (psc_fe.a == null) {
            psc_fe.a = new psc_fe();
        }
        return psc_fe.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (n == 0) {
            if (array[n].startsWith("SHA1", 0)) {
                return new psc_j9();
            }
            if (array[n].startsWith("MD5", 0)) {
                return new psc_aw();
            }
            if (array[n].startsWith("X931", 0)) {
                return new psc_ka();
            }
            if (array[n].startsWith("FIPS186", 0)) {
                return new psc_ev();
            }
            if (array[n].startsWith("DummyRandom", 0)) {
                return new psc_kb();
            }
        }
        return this.b(array, n, s, array2);
    }
    
    public Object b(final String[] array, final int n, final String s, final String[] array2) {
        if (!s.startsWith("com.rsa.jsafe")) {
            return null;
        }
        if (array[n].startsWith("SHA1", 0)) {
            return new psc_ew();
        }
        if (array[n].startsWith("No", 0)) {
            return new psc_kc();
        }
        if (array[n].startsWith("MD5", 0)) {
            return new psc_ef();
        }
        if (array[n].startsWith("MD2", 0)) {
            return new psc_jm();
        }
        return null;
    }
    
    static {
        psc_fe.a = null;
    }
}
