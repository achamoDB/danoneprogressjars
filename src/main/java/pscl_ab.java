// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ab
{
    public static final int a = 0;
    public static final int b = 9;
    public static final int c = 10;
    public static final int d = 11;
    public static final int e = 11;
    public static final int f = 15;
    public static final int g = 12;
    public static final int h = 15;
    public static final int i = 15;
    public static final int j = 15;
    public static final int k = 16;
    public static final int l = 34;
    public static final int m = 16;
    public static final int n = 16;
    public static final int o = 17;
    public static final int p = 23;
    public static final int q = 24;
    public static final int r = 34;
    public static final int s = 35;
    public static final int t = 37;
    public static final int u = 37;
    public static final int v = 37;
    public static final int w = 38;
    public static final int x = 38;
    public static final int y = 39;
    public static final int z = 39;
    public static final String[] aa;
    public static final byte[][] ab;
    
    public static byte[] a(final String s, int n, int n2) {
        if (n < 0) {
            n = 0;
        }
        if (n2 < 0 || n2 > pscl_ab.aa.length - 1) {
            n2 = pscl_ab.aa.length - 1;
        }
        while (n <= n2 && s.compareTo(pscl_ab.aa[n]) != 0) {
            ++n;
        }
        if (n > n2) {
            return new byte[0];
        }
        return pscl_ab.ab[n].clone();
    }
    
    public static String a(final byte[] array, final int n, final int n2, int n3, int n4) {
        if (n3 < 0) {
            n3 = 0;
        }
        if (n4 < 0 || n4 > pscl_ab.aa.length - 1) {
            n4 = pscl_ab.aa.length - 1;
        }
        while (n3 <= n4 && !a(array, n, n2, n3)) {
            ++n3;
        }
        if (n3 > n4) {
            return null;
        }
        return pscl_ab.aa[n3];
    }
    
    public static boolean a(final byte[] array, int n, final int n2, final int n3) {
        if (n2 != pscl_ab.ab[n3].length) {
            return false;
        }
        if (n2 > array.length - n) {
            return false;
        }
        for (int i = 0; i < pscl_ab.ab[n3].length; ++i, ++n) {
            if (array[n] != pscl_ab.ab[n3][i]) {
                return false;
            }
        }
        return true;
    }
    
    static {
        aa = new String[] { "MD5/RSA/PKCS1Block01Pad", "SHA1/RSA/PKCS1Block01Pad", "SHA1/RSA/PKCS1Block01Pad", "MD2/RSA/PKCS1Block01Pad", "SHA1/DSA/NoPad", "SHA1/DSA/NoPad", "RSAWithSHA1ISO_OIW", "RSAWithSHA1PKCS", "DSAWithSHA1X957", "DSAWithSHA1X930", "RSA/PKCS1V2OAEPPad", "RSA", "DSA", "DSA", "DSAX957", "DH", "RC4", "RC2/CBC/PKCS5Padding", "RC5/CBC/PKCS5Padding", "DES/CBC/PKCS5Padding", "DES/ECB/NoPad", "DES/CFB/NoPad", "DESX/CBC/PKCS5Padding", "3DES_EDE/CBC/PKCS5Padding", "PBE/MD2/DES/CBC/PKCS5PBE", "PBE/MD5/DES/CBC/PKCS5PBE", "PBE/SHA1/DES/CBC/PKCS5PBE", "PBE/MD2/RC2/CBC/PKCS5PBE", "PBE/MD5/RC2/CBC/PKCS5PBE", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-3", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-2", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-128", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-40", "PBE/SHA1/RC4/PKCS12V1PBE-1-128", "PBE/SHA1/RC4/PKCS12V1PBE-1-40", "MD5", "MD2", "SHA1", "MGF1", "SpecifiedParams" };
        ab = new byte[][] { { 42, -122, 72, -122, -9, 13, 1, 1, 4 }, { 42, -122, 72, -122, -9, 13, 1, 1, 5 }, { 43, 14, 3, 2, 29 }, { 42, -122, 72, -122, -9, 13, 1, 1, 2 }, { 43, 14, 3, 2, 27 }, { 42, -122, 72, -50, 56, 4, 3 }, { 43, 14, 3, 2, 29 }, { 42, -122, 72, -122, -9, 13, 1, 1, 5 }, { 42, -122, 72, -50, 56, 4, 3 }, { 43, 14, 3, 2, 27 }, { 42, -122, 72, -122, -9, 13, 1, 1, 7 }, { 42, -122, 72, -122, -9, 13, 1, 1, 1 }, { 43, 14, 3, 2, 12 }, { 42, -122, 72, -50, 56, 4, 1 }, { 42, -122, 72, -50, 56, 4, 1 }, { 42, -122, 72, -122, -9, 13, 1, 3, 1 }, { 42, -122, 72, -122, -9, 13, 3, 4 }, { 42, -122, 72, -122, -9, 13, 3, 2 }, { 42, -122, 72, -122, -9, 13, 3, 9 }, { 43, 14, 3, 2, 7 }, { 43, 14, 3, 2, 6 }, { 43, 14, 3, 2, 9 }, { 42, -122, 72, -122, -9, 13, 3, 6 }, { 42, -122, 72, -122, -9, 13, 3, 7 }, { 42, -122, 72, -122, -9, 13, 1, 5, 1 }, { 42, -122, 72, -122, -9, 13, 1, 5, 3 }, { 42, -122, 72, -122, -9, 13, 1, 5, 10 }, { 42, -122, 72, -122, -9, 13, 1, 5, 4 }, { 42, -122, 72, -122, -9, 13, 1, 5, 6 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 3 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 4 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 5 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 6 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 1 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 2 }, { 42, -122, 72, -122, -9, 13, 2, 5 }, { 42, -122, 72, -122, -9, 13, 2, 2 }, { 43, 14, 3, 2, 26 }, { 42, -122, 72, -122, -9, 13, 1, 1, 8 }, { 42, -122, 72, -122, -9, 13, 1, 1, 9 } };
    }
}
