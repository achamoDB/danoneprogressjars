// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_u
{
    public static final int a = 65536;
    public static final int b = 131072;
    public static final int c = 262144;
    public static final int d = 4194304;
    public static final int e = 8388608;
    public static final int f = 12582912;
    public static final int g = 2097152;
    public static final int h = 8388608;
    public static final int i = 10485760;
    public static final int j = 4194304;
    public static final int k = 6291456;
    public static final int l = 12582912;
    public static final int m = 14680064;
    public static final int n = 65280;
    public static final int o = 16777216;
    public static final int p = 16777216;
    public static final int q = 8192;
    public static final int r = 0;
    public static final int s = 12288;
    public static final int t = 12544;
    public static final int u = 256;
    public static final int v = 512;
    public static final int w = 2560;
    public static final int x = 768;
    public static final int y = 1024;
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
    public static final int aj = 32512;
    public static final int ak = 48;
    public static final int al = 6;
    public static final int am = 5;
    public static final int an = 0;
    public static final int ao = 67108864;
    public static final int ap = 255;
    public static final int aq = 65280;
    public static final int ar = 983040;
    public static final int as = 14680064;
    public static final int at = 50331648;
    public static final int au = 8;
    public static final int av = 16;
    public static final int aw = 0;
    public static final int ax = 16128;
    public static final int ay = 127;
    public static final int az = 36;
    public static final int a0 = 35;
    public static final int a1 = 44;
    public static final int a2 = 51;
    public static final int a3 = 52;
    public static final int a4 = 54;
    public static final int a5 = 60;
    public static final int a6 = 62;
    
    public static byte[] a(final pscl_q[] array) throws pscl_x {
        final pscl_v pscl_v = new pscl_v(array);
        final byte[] array2 = new byte[pscl_v.a()];
        final int a = pscl_v.a(array2, 0);
        if (a == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[a];
        System.arraycopy(array2, 0, array3, 0, a);
        return array3;
    }
    
    public static int a(final pscl_q[] array, final byte[] array2, final int n) throws pscl_x {
        final pscl_v pscl_v = new pscl_v(array);
        pscl_v.a();
        return pscl_v.a(array2, n);
    }
    
    public static pscl_v b(final pscl_q[] array) throws pscl_x {
        final pscl_v pscl_v = new pscl_v(array);
        pscl_v.b();
        return pscl_v;
    }
    
    public static int a(final pscl_v pscl_v, final byte[] array, final int n) throws pscl_x {
        return pscl_v.b(array, n);
    }
    
    public static int b(final pscl_v pscl_v, final byte[] array, final int n) throws pscl_x {
        return pscl_v.c(array, n);
    }
    
    public static int a(final byte[] array, final int n, final pscl_q[] array2) throws pscl_x {
        final pscl_v pscl_v = new pscl_v(array2);
        pscl_v.c();
        final int a = pscl_v.a(array, n, array.length - n);
        pscl_v.d();
        return a;
    }
    
    public static pscl_v c(final pscl_q[] array) throws pscl_x {
        final pscl_v pscl_v = new pscl_v(array);
        pscl_v.c();
        return pscl_v;
    }
    
    public static int a(final pscl_v pscl_v, final byte[] array, final int n, final int n2) throws pscl_x {
        return pscl_v.a(array, n, n2);
    }
    
    public static void a(final pscl_v pscl_v) throws pscl_x {
        pscl_v.d();
    }
}
