// 
// Decompiled by Procyon v0.5.36
// 

class psc_e8 extends psc_e3
{
    private static psc_e8 a;
    
    private psc_e8() {
    }
    
    public static psc_e3 a() {
        if (psc_e8.a == null) {
            psc_e8.a = new psc_e8();
        }
        return psc_e8.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
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
        return null;
    }
    
    static {
        psc_e8.a = null;
    }
}
