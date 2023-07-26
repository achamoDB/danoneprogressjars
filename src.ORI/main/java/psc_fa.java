// 
// Decompiled by Procyon v0.5.36
// 

class psc_fa extends psc_e3
{
    private static psc_fa a;
    
    private psc_fa() {
    }
    
    public static psc_e3 a() {
        if (psc_fa.a == null) {
            psc_fa.a = new psc_fa();
        }
        return psc_fa.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("RSA", 0)) {
            return new psc_jq();
        }
        if (array[n].startsWith("DSA", 0)) {
            return new psc_jr();
        }
        if (array[n].startsWith("DH", 0)) {
            return new psc_js();
        }
        return null;
    }
    
    static {
        psc_fa.a = null;
    }
}
