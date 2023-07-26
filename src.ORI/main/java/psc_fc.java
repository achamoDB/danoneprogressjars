// 
// Decompiled by Procyon v0.5.36
// 

class psc_fc extends psc_e3
{
    private static psc_fc a;
    
    private psc_fc() {
    }
    
    public static psc_e3 a() {
        if (psc_fc.a == null) {
            psc_fc.a = new psc_fc();
        }
        return psc_fc.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("Base64", 0)) {
            return new psc_jw();
        }
        return null;
    }
    
    static {
        psc_fc.a = null;
    }
}
