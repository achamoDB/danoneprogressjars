// 
// Decompiled by Procyon v0.5.36
// 

class psc_fb extends psc_e3
{
    private static psc_fb a;
    
    private psc_fb() {
    }
    
    public static psc_e3 a() {
        if (psc_fb.a == null) {
            psc_fb.a = new psc_fb();
        }
        return psc_fb.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("RSA", 0)) {
            return new psc_jt();
        }
        if (array[n].startsWith("DSA", 0)) {
            return new psc_ju();
        }
        if (array[n].startsWith("DH", 0)) {
            return new psc_jv();
        }
        return null;
    }
    
    static {
        psc_fb.a = null;
    }
}
