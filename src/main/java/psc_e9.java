// 
// Decompiled by Procyon v0.5.36
// 

class psc_e9 extends psc_e3
{
    private static psc_e9 a;
    
    private psc_e9() {
    }
    
    public static psc_e3 a() {
        if (psc_e9.a == null) {
            psc_e9.a = new psc_e9();
        }
        return psc_e9.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("DSA", 0)) {
            return new psc_jo();
        }
        if (array[n].startsWith("DH", 0)) {
            return new psc_jp();
        }
        return null;
    }
    
    static {
        psc_e9.a = null;
    }
}
