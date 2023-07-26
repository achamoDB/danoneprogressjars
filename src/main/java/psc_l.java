// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l
{
    public static final int a = 65536;
    public static final int b = 131072;
    protected static final int c = 262144;
    static final int d = 4194304;
    static final int e = 8388608;
    static final int f = 12582912;
    static final int g = 2097152;
    public static final int h = 8388608;
    public static final int i = 10485760;
    public static final int j = 4194304;
    public static final int k = 6291456;
    public static final int l = 12582912;
    public static final int m = 14680064;
    public static final int n = 65280;
    public static final int o = 16777216;
    public static final int p = 8192;
    protected static final int q = 0;
    public static final int r = 12288;
    public static final int s = 12544;
    public static final int t = 256;
    public static final int u = 512;
    public static final int v = 2560;
    public static final int w = 768;
    public static final int x = 1024;
    public static final int y = 1280;
    public static final int z = 3072;
    public static final int aa = 4608;
    public static final int ab = 4864;
    public static final int ac = 5120;
    public static final int ad = 5632;
    public static final int ae = 7168;
    public static final int af = 7680;
    public static final int ag = 1536;
    public static final int ah = 5888;
    public static final int ai = 6144;
    public static final int aj = 48;
    protected static final int ak = 6;
    public static final int al = 5;
    public static final int am = 0;
    static final int an = 67108864;
    static final int ao = 255;
    static final int ap = 65280;
    static final int aq = 983040;
    static final int ar = 14680064;
    static final int as = 50331648;
    static final int at = 8;
    static final int au = 16;
    static final int av = 0;
    static final int aw = 16128;
    static final int ax = 127;
    static final int ay = 36;
    static final int az = 35;
    static final int a0 = 44;
    static final int a1 = 51;
    static final int a2 = 52;
    static final int a3 = 54;
    static final int a4 = 60;
    static final int a5 = 62;
    public static final int a6 = 0;
    
    public static byte[] a(final psc_i[] array) throws psc_m {
        final psc_n psc_n = new psc_n(array);
        final byte[] array2 = new byte[psc_n.a()];
        final int a = psc_n.a(array2, 0);
        if (a == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[a];
        System.arraycopy(array2, 0, array3, 0, a);
        return array3;
    }
    
    public static int a(final byte[] array, final int n, final psc_i[] array2) throws psc_m {
        if (array == null) {
            throw new psc_m("ASN1.berDecode: encoding should not be null.");
        }
        final psc_n psc_n = new psc_n(array2);
        psc_n.c();
        final int a = psc_n.a(array, n, array.length - n);
        psc_n.d();
        return a;
    }
}
