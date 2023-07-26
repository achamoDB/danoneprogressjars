import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_av extends SecureRandom implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static SecureRandom c;
    private static final boolean[] d;
    private static final String[] e;
    private static final boolean[] f;
    private static final String[] g;
    private static final String h = "psc_av";
    
    static final void a() {
        psc_aq.e();
    }
    
    static final boolean b() {
        return psc_aq.c();
    }
    
    psc_av() {
        super(new byte[0]);
    }
    
    private static synchronized SecureRandom e() {
        if (psc_av.c == null) {
            psc_av.c = new SecureRandom();
        }
        return psc_av.c;
    }
    
    protected static SecureRandom c() {
        if (psc_av.c == null) {
            psc_av.c = e();
        }
        return psc_av.c;
    }
    
    protected static byte[] a(final int n) {
        final byte[] bytes = new byte[n];
        c().nextBytes(bytes);
        return bytes;
    }
    
    public static SecureRandom getInstance(final String str, final String str2) throws NoSuchAlgorithmException {
        psc_an.a();
        if (str2 == null) {
            throw new NoSuchAlgorithmException("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new NoSuchAlgorithmException("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_av a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.a = array[i].c();
                    a3.b = array[i].d();
                    return a3;
                }
            }
            catch (psc_be psc_be) {}
        }
        throw new NoSuchAlgorithmException("A JSAFE_SecureRandom object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_av a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length == 1) {
            final Object[] a = psc_bd.a(array, null, 10, psc_av.f, psc_av.g, "psc_av", array2, array3);
            if (a == null) {
                return null;
            }
            ((psc_ax)a[0]).a(psc_ba.b(array[0]));
            final String algorithm = ((psc_ax)a[0]).getAlgorithm();
            if (algorithm.startsWith("X9") || algorithm.equals("FIPS186Random")) {
                return (psc_av)a[0];
            }
            return new psc_ay((psc_ax)a[0]);
        }
        else {
            if (array.length != 2) {
                return null;
            }
            psc_ba.b(array[0]);
            final Object[] a2 = psc_bd.a(array, null, 10, psc_av.d, psc_av.e, "psc_av", array2, array3);
            if (a2 == null) {
                return null;
            }
            return new psc_c6((psc_gt)a2[0], (psc_az)a2[1]);
        }
    }
    
    public String o() {
        return this.a;
    }
    
    public String[] p() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public int[] q() {
        return new int[0];
    }
    
    public abstract String getAlgorithm();
    
    public abstract void g(final byte[] p0);
    
    public void h(final byte[] array) throws psc_e1 {
        this.g(array);
    }
    
    public abstract void r();
    
    public void setSeed(final long n) {
        if (n != 0L) {
            this.a(n);
        }
    }
    
    private void a(long n) {
        final byte[] array = new byte[8];
        for (int i = 0; i < 8; ++i) {
            array[i] = (byte)(n & 0xFFL);
            n >>>= 8;
        }
        this.g(array);
        psc_au.c(array);
    }
    
    public void setSeed(final byte[] array) {
        if (array != null && array.length != 0) {
            this.g(array);
        }
    }
    
    public void nextBytes(final byte[] array) {
        this.b(array, 0, array.length);
    }
    
    public short s() {
        final byte[] array = new byte[2];
        this.b(array, 0, 2);
        final int n = (array[0] & 0xFF) | (array[1] & 0xFF) << 8;
        psc_au.c(array);
        return (short)n;
    }
    
    public int nextInt() {
        final byte[] array = new byte[4];
        this.b(array, 0, 4);
        final int n = (array[0] & 0xFF) | (array[1] & 0xFF) << 8 | (array[2] & 0xFF) << 16 | (array[3] & 0xFF) << 24;
        psc_au.c(array);
        return n;
    }
    
    public long nextLong() {
        final byte[] array = new byte[8];
        this.b(array, 0, 8);
        final long n = (long)(array[0] & 0xFF) | (long)(array[1] & 0xFF) << 8 | (long)(array[2] & 0xFF) << 16 | (long)(array[3] & 0xFF) << 24 | (long)(array[4] & 0xFF) << 32 | (long)(array[5] & 0xFF) << 40 | (long)(array[6] & 0xFF) << 48 | (long)(array[7] & 0xFF) << 56;
        psc_au.c(array);
        return n;
    }
    
    public double nextDouble() {
        final long n = this.nextLong() & Long.MAX_VALUE;
        if (n == 0L) {
            return this.nextDouble();
        }
        if ((n & 0x7FF0000000000000L) == 0x7FF0000000000000L) {
            return this.nextDouble();
        }
        return Double.longBitsToDouble(n);
    }
    
    public float nextFloat() {
        final int n = (int)((long)this.nextInt() & Long.MAX_VALUE);
        if (n == 0) {
            return this.nextFloat();
        }
        if ((n & 0x7F800000) == 0x7F800000) {
            return this.nextFloat();
        }
        return Float.intBitsToFloat(n);
    }
    
    public byte[] c(final int n) {
        final byte[] array = new byte[n];
        this.b(array, 0, n);
        return array;
    }
    
    public abstract void b(final byte[] p0, final int p1, final int p2);
    
    protected void a(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        if (psc_aq.k() == 1) {
            return;
        }
        for (int i = 0; i < n3; ++i) {
            if (array[n + i] != array2[n2 + i]) {
                return;
            }
        }
        throw new SecurityException("Continuous Random Number Generation Check failed");
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    byte[] d() {
        return this.c(256);
    }
    
    static psc_av a(final String s, final byte[] array) {
        psc_av psc_av;
        try {
            psc_av = (psc_av)getInstance(s, "Java");
            psc_av.g(array);
        }
        catch (NoSuchAlgorithmException ex) {
            psc_av = null;
        }
        return psc_av;
    }
    
    protected void a(final psc_av psc_av) throws CloneNotSupportedException {
        this.a = psc_av.a;
        this.b = psc_av.b;
    }
    
    public void y() {
    }
    
    static {
        psc_av.c = null;
        d = new boolean[] { true, false };
        e = new String[] { "com.rsa.jsafe.JA_AlgaeRandom", "com.rsa.jsafe.JA_AlgaeDigest" };
        f = new boolean[] { true };
        g = new String[] { "com.rsa.jsafe.JA_AlgaeChainDigestRandom" };
    }
}
