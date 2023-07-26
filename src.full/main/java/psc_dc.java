import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_dc extends psc_an implements psc_am, Cloneable, Serializable
{
    private String a;
    private String[] b;
    protected String c;
    private int d;
    public static final int e = 0;
    public static final int f = 1;
    protected SecureRandom g;
    private byte[] h;
    private String i;
    protected int j;
    protected int k;
    protected static final byte[][] l;
    protected int m;
    protected transient psc_dd n;
    protected byte[] o;
    protected transient psc_dd p;
    protected short[] q;
    private static final boolean[] r;
    private static final String[] s;
    private static final String t = "psc_dc";
    private static final String[] u;
    
    static psc_dc a(final byte[] array, final int n, final String s) throws psc_ao {
        final StringBuffer sb = new StringBuffer();
        byte[] a = null;
        try {
            a = psc_gu.a(sb, array, n);
            final psc_dc a2 = a(sb.toString(), s);
            a2.a(a, 0, a.length);
            return a2;
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Could not read BER info.");
        }
        finally {
            if (a != null) {
                psc_au.c(a);
            }
        }
    }
    
    public static psc_dc a(final String str, final String str2) throws psc_ao {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_dc a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.a = array[i].c();
                    a3.b = array[i].d();
                    return a3;
                }
            }
            catch (psc_be psc_be) {}
        }
        throw new psc_ao("A JSAFE_SecretKey object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_dc a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, psc_dc.u, 9, psc_dc.r, psc_dc.s, "psc_dc", array2, array3);
        if (a == null) {
            return null;
        }
        ((psc_dc)a[0]).a(psc_ba.b(array[0]));
        return (psc_dc)a[0];
    }
    
    protected psc_dc(final String c, final int j, final int k) {
        this.j = 0;
        this.k = -1;
        this.m = -15;
        this.c = c;
        this.j = j;
        this.k = k;
        this.d = 0;
    }
    
    protected psc_dc(final String c, final int j, final int k, final int d) {
        this.j = 0;
        this.k = -1;
        this.m = -15;
        this.c = c;
        this.j = j;
        this.k = k;
        this.d = d;
    }
    
    int c() {
        return this.d;
    }
    
    protected void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public String k() {
        return this.a;
    }
    
    public String[] i() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public String l() {
        return this.c;
    }
    
    abstract int a(final int p0);
    
    public void a(final char[] array, int n, final int n2) {
        if (this.q != null) {
            psc_au.b(this.q, this.p);
            this.p = null;
            this.q = null;
        }
        if (array == null) {
            return;
        }
        this.q = new short[n2];
        for (int i = 0; i < n2; ++i, ++n) {
            this.q[i] = (short)array[n];
        }
        (this.p = psc_au.b(this.q)).c();
    }
    
    public char[] j() throws psc_bf {
        if (this.q == null) {
            throw new psc_bf("Key object not set with password.");
        }
        final char[] array = new char[this.q.length];
        this.p.d();
        for (int i = 0; i < this.q.length; ++i) {
            array[i] = (char)this.q[i];
        }
        this.p.c();
        return array;
    }
    
    public int q() {
        return this.k;
    }
    
    public int p() {
        return this.j;
    }
    
    public String[] n() {
        return new String[] { "Clear" };
    }
    
    public String[] o() {
        if (this.o == null) {
            return new String[0];
        }
        return new String[] { "Clear" };
    }
    
    public String b(final boolean b) {
        if (!b) {
            return "Clear";
        }
        return this.c + "SecretKeyBER";
    }
    
    public void a(final String str, final byte[] array, final int n, final int n2) throws psc_bf, psc_ao {
        if (str.compareTo("Clear") != 0) {
            throw new psc_ao("Unimplemented key format: " + str);
        }
        this.a(array, n, n2);
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_bf {
        if (this.o != null) {
            psc_au.b(this.o, this.n);
            this.n = null;
            this.o = null;
        }
        if (!this.b(n2 * 8)) {
            throw new psc_bf("Invalid key data length");
        }
        System.arraycopy(array, n, this.o = new byte[n2], 0, n2);
        (this.n = psc_au.b(this.o)).c();
    }
    
    public void a(final String s, final byte[][] array) throws psc_bf, psc_ao {
        if (array.length != 1) {
            throw new psc_bf("Invalid key data.");
        }
        this.a(s, array[0], 0, array[0].length);
    }
    
    public void c(final byte[][] array) throws psc_bf {
        if (array.length != 1) {
            throw new psc_bf("Invalid key data.");
        }
        this.a(array[0], 0, array[0].length);
    }
    
    public byte[][] b(final String s) throws psc_ao {
        final byte[] a = this.a(s);
        if (this.o == null) {
            return new byte[0][];
        }
        return new byte[][] { a };
    }
    
    public byte[][] m() {
        if (this.o == null) {
            return new byte[0][];
        }
        return new byte[][] { this.r() };
    }
    
    public byte[] a(final String str) throws psc_ao {
        if (str.compareTo("Clear") == 0) {
            return this.r();
        }
        if (str.compareTo(this.c + "SecretKeyBER") != 0) {
            throw new psc_ao("Unimplemented key format: " + str);
        }
        return this.d();
    }
    
    public byte[] r() {
        if (this.o == null) {
            return new byte[0];
        }
        final byte[] array = new byte[this.o.length];
        this.n.d();
        System.arraycopy(this.o, 0, array, 0, this.o.length);
        this.n.c();
        return array;
    }
    
    protected byte[] d() throws psc_ao {
        byte[] r = null;
        try {
            r = this.r();
            return psc_gu.a(this.c, r);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Cannot compute Secret Key BER.");
        }
        finally {
            if (r != null) {
                psc_au.c(r);
            }
        }
    }
    
    public boolean b(final int n) {
        return n <= this.k && n >= this.j;
    }
    
    boolean a(final byte[] array) {
        if (array == null) {
            return false;
        }
        for (int i = 0; i < psc_dc.l.length; ++i) {
            if (psc_dc.l[i] != null) {
                if (array.length == psc_dc.l[i].length) {
                    int n;
                    for (n = 0; n < array.length && array[n] == psc_dc.l[i][n]; ++n) {}
                    if (n == array.length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void a(int[] array, final SecureRandom g) throws psc_be, psc_en {
        if (array == null) {
            array = new int[] { this.a(-1) };
        }
        if (array.length != 1) {
            throw new psc_be("Incorrect number of parameters for key generation: expected 1");
        }
        if (!this.b(array[0])) {
            throw new psc_be("Incorrect key length requested for key generation");
        }
        this.m = array[0];
        if (g != null) {
            this.g = g;
        }
        if (this.g == null) {
            throw new psc_en("Need a random object.");
        }
    }
    
    public void s() throws psc_en {
        if (this.m < 0) {
            throw new psc_en("Cannot ReInit, object not initialized.");
        }
    }
    
    public void t() throws psc_be {
        final int n = (this.m + 7) / 8;
        if (this.m < 0) {
            throw new psc_be("Cannot generate, object not initialized.");
        }
        final byte[] bytes = new byte[n];
        int n2 = 0;
        while (++n2 <= 100) {
            this.g.nextBytes(bytes);
            final int n3 = this.m % 8;
            if (n3 != 0) {
                bytes[0] = (byte)((bytes[0] & 0xFF) >>> 8 - n3);
            }
            if (!this.a(bytes)) {
                try {
                    this.a(bytes, 0, bytes.length);
                }
                catch (psc_bf psc_bf) {
                    throw new psc_be("Invalid Key Length for Generation.");
                }
                finally {
                    psc_au.c(bytes);
                }
                return;
            }
        }
        throw new psc_be("Invalid key length.");
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av e = this.e();
        objectOutputStream.defaultWriteObject();
        this.a(e);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.f();
    }
    
    private psc_av e() {
        if (this.n != null) {
            this.n.d();
        }
        if (this.p != null) {
            this.p.d();
        }
        if (this.g == null) {
            return null;
        }
        if (!(this.g instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.g;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.i = psc_av.getAlgorithm();
        this.h = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.g;
        this.g = null;
        return psc_av2;
    }
    
    private void a(final psc_av g) {
        if (this.n != null) {
            this.n.c();
        }
        if (this.p != null) {
            this.p.c();
        }
        if (this.h == null) {
            return;
        }
        for (int i = 0; i < this.h.length; ++i) {
            this.h[i] = 0;
        }
        this.h = null;
        this.i = null;
        this.g = g;
    }
    
    private void f() {
        if (this.o != null) {
            (this.n = psc_au.b(this.o)).c();
        }
        if (this.q != null) {
            (this.p = psc_au.b(this.q)).c();
        }
        if (this.h == null) {
            return;
        }
        this.g = psc_av.a(this.i, this.h);
        for (int i = 0; i < this.h.length; ++i) {
            this.h[i] = 0;
        }
        this.h = null;
        this.i = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_dc psc_dc = (psc_dc)super.clone();
        psc_dc.o = null;
        psc_dc.n = null;
        psc_dc.q = null;
        psc_dc.p = null;
        if (this.o != null) {
            psc_dc.o = (byte[])psc_au.a(this.o, this.n);
            psc_dc.n = psc_au.a(psc_dc.o);
        }
        if (this.q != null) {
            psc_dc.q = (short[])psc_au.a(this.q, this.p);
            psc_dc.p = psc_au.a(psc_dc.q);
        }
        psc_dc.a(this);
        return psc_dc;
    }
    
    protected void a(final psc_dc psc_dc) {
        this.a = psc_dc.a;
        if (psc_dc.b != null) {
            this.b = new String[psc_dc.b.length];
            for (int i = 0; i < psc_dc.b.length; ++i) {
                this.b[i] = psc_dc.b[i];
            }
        }
    }
    
    public void y() {
        super.y();
        if (this.o != null) {
            psc_au.b(this.o, this.n);
        }
        this.o = null;
        this.n = null;
        this.m = -15;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        l = new byte[0][];
        r = new boolean[] { true };
        s = new String[] { "psc_dc" };
        u = new String[] { "Key" };
    }
}
