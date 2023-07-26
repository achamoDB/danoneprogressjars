import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_aq
{
    public static final String a = "3.5.2";
    private static final boolean b = false;
    public static final int c = 0;
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 3;
    private static int g;
    public static final int h = 0;
    public static final int i = 1;
    public static final int j = 2;
    private static int k;
    public static final int l = 0;
    public static final int m = 1;
    private static int n;
    private static psc_aq o;
    private static final String p = "Access violation: an internal FIPS 140 check invoked by a class outside the allowed package, Crypto-J has been disabled";
    private static boolean q;
    public static byte[] r;
    public static byte[] s;
    private static String t;
    
    private psc_aq() {
    }
    
    public static psc_aq g() {
        if (psc_aq.o == null) {
            psc_aq.o = new psc_aq();
        }
        return psc_aq.o;
    }
    
    public static SecureRandom h() {
        return psc_av.c();
    }
    
    public static final boolean i() {
        return psc_aq.q;
    }
    
    public static int j() {
        if (psc_aq.g == 0) {
            a();
        }
        return psc_aq.g;
    }
    
    public static int k() {
        return psc_aq.k;
    }
    
    public static void b(final int k) throws psc_en {
        if (!i()) {
            throw new psc_en("Can't change the mode in a non-fips toolkit");
        }
        if (k != 0 && k != 1) {
            throw new psc_en("Invalid parameter, only FIPS_MODE or NON_FIPS_MODE allowed");
        }
        psc_aq.k = k;
    }
    
    public static int l() {
        return psc_aq.n;
    }
    
    public static void c(final int n) throws psc_en {
        if (!i()) {
            throw new psc_en("Can't change the user role in a non-fips toolkit");
        }
        if (n != 1 && n != 0) {
            throw new psc_en("Invalid parameter, only USER_ROLE or CRYPTO_OFFICER_ROLE allowed");
        }
        psc_aq.n = n;
    }
    
    private static void a(final int g) {
        if (g == 0) {
            return;
        }
        Label_0150: {
            switch (psc_aq.g) {
                case 0: {
                    switch (g) {
                        case 1:
                        case 3: {
                            psc_aq.g = g;
                            break Label_0150;
                        }
                        default: {
                            break Label_0150;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (g) {
                        case 2:
                        case 3: {
                            psc_aq.g = g;
                            break Label_0150;
                        }
                        default: {
                            break Label_0150;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (g) {
                        case 1:
                        case 3: {
                            psc_aq.g = g;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    static synchronized void a() {
        if (psc_aq.g == 0) {
            if (!b()) {
                throw new SecurityException("Crypto-J is disabled, a FIPS 140 required self-integity check failed");
            }
            f();
        }
    }
    
    static final synchronized boolean b() {
        return true;
    }
    
    public static boolean m() {
        if (psc_aq.g == 0) {
            try {
                return f();
            }
            catch (SecurityException ex) {
                a(3);
            }
        }
        return psc_aq.g == 2;
    }
    
    public static synchronized boolean n() throws psc_en {
        if (psc_aq.n != 0) {
            throw new psc_en("Method available on Crypto Officer Role");
        }
        try {
            return f();
        }
        catch (SecurityException ex) {
            a(3);
            return false;
        }
    }
    
    private static boolean f() {
        a(1);
        if (psc_ar.a().o()) {
            a(2);
            return true;
        }
        a(3);
        return false;
    }
    
    static final boolean c() {
        return psc_aq.k == 0;
    }
    
    static final boolean d() {
        return psc_aq.g == 3;
    }
    
    static final void e() {
        if (psc_aq.k != 0) {
            return;
        }
        a();
        if (psc_aq.g == 3) {
            throw new SecurityException("Crypto-J has entered an inoperable state, an internal FIPS 140 self-verification test has failed");
        }
    }
    
    private static boolean a(final String s) {
        final psc_ar a = psc_ar.a();
        boolean b = false;
        if (s.equals("-testAES")) {
            b = a.b();
        }
        else if (s.equals("-testDES")) {
            b = a.c();
        }
        else if (s.equals("-testTDES")) {
            b = a.d();
        }
        else if (s.equals("-testHMACSHA")) {
            b = a.g();
        }
        else if (s.equals("-testSHA1")) {
            b = a.e();
        }
        else if (s.equals("-testSHA2")) {
            b = a.f();
        }
        else if (s.equals("-testDSA")) {
            b = a.h();
        }
        else if (s.equals("-testRSA")) {
            b = a.i();
        }
        else if (s.equals("-testFIPS186Random")) {
            b = a.j();
        }
        return b;
    }
    
    private static boolean a(final String[] array) {
        boolean b = false;
        final int n = psc_aq.n;
        try {
            psc_aq.n = 0;
            if (array[0].equals("-testAll")) {
                b = n();
            }
            else {
                b = a(array[0]);
            }
        }
        catch (psc_en psc_en) {
            throw new SecurityException(psc_en.getMessage());
        }
        finally {
            psc_aq.n = n;
        }
        return b;
    }
    
    public static void a(final SecureRandom secureRandom) {
        if (psc_aq.n != 0 || psc_aq.k != 2) {
            throw new IllegalStateException("CRYPTO_OFFICER_ROLE and FIPS_TESTING_MODE required");
        }
        if (psc_aq.r != null) {
            for (int i = 0; i < psc_aq.r.length; ++i) {
                psc_aq.r[i] = 0;
            }
            psc_aq.r = null;
            if (secureRandom instanceof psc_ev) {
                ((psc_ev)secureRandom).h();
            }
        }
    }
    
    public static void a(final SecureRandom secureRandom, final byte[] array) {
        if (psc_aq.n != 0 || psc_aq.k != 2) {
            throw new IllegalStateException("CRYPTO_OFFICER_ROLE and FIPS_TESTING_MODE required");
        }
        if (array == null) {
            throw new IllegalArgumentException("Null argument");
        }
        psc_aq.r = array.clone();
        if (secureRandom instanceof psc_ev) {
            ((psc_ev)secureRandom).a(psc_aq.r);
        }
    }
    
    public static void b(final SecureRandom secureRandom) {
        if (psc_aq.n != 0 || psc_aq.k != 2) {
            throw new IllegalStateException("CRYPTO_OFFICER_ROLE and FIPS_TESTING_MODE required");
        }
        if (psc_aq.s != null) {
            for (int i = 0; i < psc_aq.s.length; ++i) {
                psc_aq.s[i] = 0;
            }
            psc_aq.s = null;
            if (secureRandom instanceof psc_ev) {
                ((psc_ev)secureRandom).j();
            }
        }
    }
    
    public static void b(final SecureRandom secureRandom, final byte[] array) {
        if (psc_aq.n != 0 || psc_aq.k != 2) {
            throw new IllegalStateException("CRYPTO_OFFICER_ROLE and FIPS_TESTING_MODE required");
        }
        if (array == null) {
            throw new IllegalArgumentException("null argument");
        }
        psc_aq.s = array.clone();
        if (secureRandom instanceof psc_ev) {
            ((psc_ev)secureRandom).b(psc_aq.s);
        }
    }
    
    public static void b(final String[] array) {
        if (array.length == 0) {
            System.out.println(psc_nd.g + new psc_nc().a());
        }
        else if (array.length == 1) {
            if (array[0].equals("-verify")) {
                array[0] = "-testAll";
            }
            if (!array[0].startsWith("-test")) {
                System.err.println(psc_aq.t);
                return;
            }
            System.out.println("Crypto-J power-up self-tests " + (a(array) ? "passed" : "failed"));
        }
    }
    
    static {
        psc_aq.g = 2;
        psc_aq.k = 1;
        psc_aq.n = 1;
        psc_aq.o = null;
        psc_aq.q = false;
        psc_aq.t = "Usage: java com.rsa.jsafe.CryptoJ <options>          \n  where options are:                                 \n    -verify            run all powerup self tests    \n    -testAll           run all powerup self tests    \n    -testAES           AES self-test                 \n    -testDES           DES self-test                 \n    -testTDES          Triple DES self-test          \n    -testSHA1          SHA1 self-test                \n    -testSHA2          SHA-224/256/384/512 self-tests\n    -testHMACSHA       HMACSHA self-tests            \n    -testDSA           DSA sign/verify self-test     \n    -testRSA           RSA sign/verify self-test     \n    -testFIPS186Random FIPS186Random self-test       \n    default is to print the version string           \n";
    }
}
