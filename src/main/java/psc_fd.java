// 
// Decompiled by Procyon v0.5.36
// 

class psc_fd extends psc_e3
{
    private static psc_fd a;
    
    private psc_fd() {
    }
    
    public static psc_e3 a() {
        if (psc_fd.a == null) {
            psc_fd.a = new psc_fd();
        }
        return psc_fd.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array[n].startsWith("RC4", 0)) {
            return new psc_jy();
        }
        if (array[n].compareTo("AES") == 0) {
            return new psc_jz();
        }
        if (array[n].compareTo("AES128") == 0) {
            return new psc_j0();
        }
        if (array[n].compareTo("AES192") == 0) {
            return new psc_j1();
        }
        if (array[n].compareTo("AES256") == 0) {
            return new psc_j2();
        }
        if (array[n].compareTo("DES") == 0) {
            return new psc_j3();
        }
        if (array[n].startsWith("3DES_EDE", 0)) {
            return new psc_j4();
        }
        if (array[n].startsWith("HMAC", 0)) {
            return new psc_j5();
        }
        if (array[n].startsWith("RC2", 0)) {
            return new psc_j6();
        }
        if (array[n].startsWith("RC5", 0)) {
            return new psc_j7();
        }
        if (array[n].startsWith("DESX", 0)) {
            return new psc_j8();
        }
        return null;
    }
    
    static {
        psc_fd.a = null;
    }
}
