// 
// Decompiled by Procyon v0.5.36
// 

class psc_e6 extends psc_e3
{
    private static psc_e6 a;
    
    private psc_e6() {
    }
    
    public static psc_e3 a() {
        if (psc_e6.a == null) {
            psc_e6.a = new psc_e6();
        }
        return psc_e6.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("RSA", 0)) {
            return new psc_ja();
        }
        if (array[n].startsWith("DSA", 0)) {
            return new psc_jb();
        }
        if (array[n].startsWith("DH", 0)) {
            return new psc_jc();
        }
        return null;
    }
    
    static {
        psc_e6.a = null;
    }
}
