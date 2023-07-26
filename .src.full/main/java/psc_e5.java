// 
// Decompiled by Procyon v0.5.36
// 

class psc_e5 extends psc_e3
{
    private static psc_e5 a;
    
    private psc_e5() {
    }
    
    public static psc_e3 a() {
        if (psc_e5.a == null) {
            psc_e5.a = new psc_e5();
        }
        return psc_e5.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("DH", 0)) {
            return new psc_i9();
        }
        return null;
    }
    
    static {
        psc_e5.a = null;
    }
}
