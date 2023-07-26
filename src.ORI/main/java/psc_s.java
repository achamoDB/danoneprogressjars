// 
// Decompiled by Procyon v0.5.36
// 

public class psc_s
{
    public static final int a = -1;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 7;
    public static final int i = 8;
    public static final int j = 9;
    public static final int k = 10;
    public static final int l = 11;
    public static final int m = 12;
    public static final int n = 13;
    public static final int o = 14;
    public static final String[] p;
    public static final byte[][] q;
    private static final int r = 0;
    private static final int s = 15;
    private static final int t = 16;
    private static final int u = 17;
    private static final int v = 17;
    private static final int w = 21;
    private static final int x = 18;
    private static final int y = 21;
    private static final int z = 21;
    private static final int aa = 21;
    private static final int ab = 22;
    private static final int ac = 49;
    private static final int ad = 22;
    private static final int ae = 22;
    private static final int af = 23;
    private static final int ag = 38;
    private static final int ah = 39;
    private static final int ai = 50;
    private static final int aj = 51;
    private static final int ak = 58;
    private static final int al = 58;
    private static final int am = 58;
    private static final int an = 59;
    private static final int ao = 59;
    private static final int ap = 59;
    private static final int aq = 60;
    private static final int ar = 61;
    private static final int as = 63;
    
    public static byte[] a(final String s, final int n) {
        int a;
        int b;
        for (a = a(n), b = b(n); a <= b && s.compareTo(psc_s.p[a]) != 0; ++a) {}
        if (a > b) {
            return new byte[0];
        }
        return psc_s.q[a].clone();
    }
    
    public static String b(final byte[] array, final int n, final int n2, final int n3) {
        int a;
        int b;
        for (a = a(n3), b = b(n3); a <= b && !a(array, n, n2, a); ++a) {}
        if (a > b) {
            return null;
        }
        return psc_s.p[a];
    }
    
    private static boolean a(final byte[] array, int n, final int n2, final int n3) {
        if (n2 != psc_s.q[n3].length) {
            return false;
        }
        if (n2 > array.length - n) {
            return false;
        }
        for (int i = 0; i < psc_s.q[n3].length; ++i, ++n) {
            if (array[n] != psc_s.q[n3][i]) {
                return false;
            }
        }
        return true;
    }
    
    private static int a(final int n) {
        switch (n) {
            case 1: {
                return 0;
            }
            case 2: {
                return 16;
            }
            case 3: {
                return 17;
            }
            case 4: {
                return 18;
            }
            case 5: {
                return 21;
            }
            case 6: {
                return 22;
            }
            case 7: {
                return 22;
            }
            case 8: {
                return 23;
            }
            case 9: {
                return 39;
            }
            case 10: {
                return 51;
            }
            case 11: {
                return 58;
            }
            case 12: {
                return 59;
            }
            case 13: {
                return 59;
            }
            case 14: {
                return 61;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static int b(final int n) {
        switch (n) {
            case 1: {
                return 15;
            }
            case 2: {
                return 17;
            }
            case 3: {
                return 21;
            }
            case 4: {
                return 21;
            }
            case 5: {
                return 21;
            }
            case 6: {
                return 49;
            }
            case 7: {
                return 22;
            }
            case 8: {
                return 38;
            }
            case 9: {
                return 50;
            }
            case 10: {
                return 58;
            }
            case 11: {
                return 58;
            }
            case 12: {
                return 59;
            }
            case 13: {
                return 60;
            }
            case 14: {
                return 63;
            }
            default: {
                return psc_s.p.length - 1;
            }
        }
    }
    
    static {
        p = new String[] { "MD5/RSA/PKCS1Block01Pad", "SHA1/RSA/PKCS1Block01Pad", "SHA1/RSA/PKCS1Block01Pad", "MD2/RSA/PKCS1Block01Pad", "RSA/PKCS1V2PSS", "SHA1/DSA/NoPad", "SHA1/DSA/NoPad", "RSAWithSHA1ISO_OIW", "RSAWithSHA1PKCS", "DSAWithSHA1X957", "DSAWithSHA1X930", "RIPEMD160/RSA/PKCS1Block01Pad", "SHA256/RSA/PKCS1Block01Pad", "SHA384/RSA/PKCS1Block01Pad", "SHA512/RSA/PKCS1Block01Pad", "SHA224/RSA/PKCS1Block01Pad", "RSA/PKCS1V2OAEPPad", "RSA", "DSA", "DSA", "DSAX957", "DH", "RC4", "RC2/CBC/PKCS5Padding", "RC5/CBC/PKCS5Padding", "DES/CBC/PKCS5Padding", "DES/ECB/NoPad", "DES/CFB/NoPad", "DESX/CBC/PKCS5Padding", "3DES_EDE/CBC/PKCS5Padding", "AES128/ECB/NoPad", "AES192/ECB/NoPad", "AES256/ECB/NoPad", "AES128/CBC/NoPad", "AES192/CBC/NoPad", "AES256/CBC/NoPad", "AES128/CFB/NoPad", "AES192/CFB/NoPad", "AES256/CFB/NoPad", "PBE/MD2/DES/CBC/PKCS5PBE", "PBE/MD5/DES/CBC/PKCS5PBE", "PBE/SHA1/DES/CBC/PKCS5PBE", "PBE/MD2/RC2/CBC/PKCS5PBE", "PBE/MD5/RC2/CBC/PKCS5PBE", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-3", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-2", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-128", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-40", "PBE/SHA1/RC4/PKCS12V1PBE-1-128", "PBE/SHA1/RC4/PKCS12V1PBE-1-40", "PBE/HMAC/SHA1/PKIXPBE", "MD5", "MD2", "SHA256", "SHA384", "SHA512", "SHA224", "RIPEMD160", "SHA1", "MGF1", "SpecifiedParams", "HMAC/SHA1", "HMAC/SHA1a", "HMAC/SHA256", "RSA", "PBES2", "PBKDF2" };
        q = new byte[][] { { 42, -122, 72, -122, -9, 13, 1, 1, 4 }, { 42, -122, 72, -122, -9, 13, 1, 1, 5 }, { 43, 14, 3, 2, 29 }, { 42, -122, 72, -122, -9, 13, 1, 1, 2 }, { 42, -122, 72, -122, -9, 13, 1, 1, 10 }, { 43, 14, 3, 2, 27 }, { 42, -122, 72, -50, 56, 4, 3 }, { 43, 14, 3, 2, 29 }, { 42, -122, 72, -122, -9, 13, 1, 1, 5 }, { 42, -122, 72, -50, 56, 4, 3 }, { 43, 14, 3, 2, 27 }, { 43, 36, 3, 3, 1, 2 }, { 42, -122, 72, -122, -9, 13, 1, 1, 11 }, { 42, -122, 72, -122, -9, 13, 1, 1, 12 }, { 42, -122, 72, -122, -9, 13, 1, 1, 13 }, { 42, -122, 72, -122, -9, 13, 1, 1, 14 }, { 42, -122, 72, -122, -9, 13, 1, 1, 7 }, { 42, -122, 72, -122, -9, 13, 1, 1, 1 }, { 43, 14, 3, 2, 12 }, { 42, -122, 72, -50, 56, 4, 1 }, { 42, -122, 72, -50, 56, 4, 1 }, { 42, -122, 72, -122, -9, 13, 1, 3, 1 }, { 42, -122, 72, -122, -9, 13, 3, 4 }, { 42, -122, 72, -122, -9, 13, 3, 2 }, { 42, -122, 72, -122, -9, 13, 3, 9 }, { 43, 14, 3, 2, 7 }, { 43, 14, 3, 2, 6 }, { 43, 14, 3, 2, 9 }, { 42, -122, 72, -122, -9, 13, 3, 6 }, { 42, -122, 72, -122, -9, 13, 3, 7 }, { 96, -122, 72, 1, 101, 3, 4, 1, 1 }, { 96, -122, 72, 1, 101, 3, 4, 1, 21 }, { 96, -122, 72, 1, 101, 3, 4, 1, 41 }, { 96, -122, 72, 1, 101, 3, 4, 1, 2 }, { 96, -122, 72, 1, 101, 3, 4, 1, 22 }, { 96, -122, 72, 1, 101, 3, 4, 1, 42 }, { 96, -122, 72, 1, 101, 3, 4, 1, 4 }, { 96, -122, 72, 1, 101, 3, 4, 1, 24 }, { 96, -122, 72, 1, 101, 3, 4, 1, 44 }, { 42, -122, 72, -122, -9, 13, 1, 5, 1 }, { 42, -122, 72, -122, -9, 13, 1, 5, 3 }, { 42, -122, 72, -122, -9, 13, 1, 5, 10 }, { 42, -122, 72, -122, -9, 13, 1, 5, 4 }, { 42, -122, 72, -122, -9, 13, 1, 5, 6 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 3 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 4 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 5 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 6 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 1 }, { 42, -122, 72, -122, -9, 13, 1, 12, 1, 2 }, { 42, -122, 72, -122, -10, 125, 7, 66, 13 }, { 42, -122, 72, -122, -9, 13, 2, 5 }, { 42, -122, 72, -122, -9, 13, 2, 2 }, { 96, -122, 72, 1, 101, 3, 4, 2, 1 }, { 96, -122, 72, 1, 101, 3, 4, 2, 2 }, { 96, -122, 72, 1, 101, 3, 4, 2, 3 }, { 96, -122, 72, 1, 101, 3, 4, 2, 4 }, { 43, 36, 3, 2, 1 }, { 43, 14, 3, 2, 26 }, { 42, -122, 72, -122, -9, 13, 1, 1, 8 }, { 42, -122, 72, -122, -9, 13, 1, 1, 9 }, { 43, 6, 1, 5, 5, 8, 1, 2 }, { 42, -122, 72, -122, -9, 13, 2, 7 }, { 42, -122, 72, -122, -9, 13, 2, 9 }, { 85, 8, 1, 1 }, { 42, -122, 72, -122, -9, 13, 1, 5, 13 }, { 42, -122, 72, -122, -9, 13, 1, 5, 12 } };
    }
}
