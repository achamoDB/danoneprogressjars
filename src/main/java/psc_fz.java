import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_fz implements Cloneable, Serializable
{
    public static final int a = 0;
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
    public static final int p = 15;
    public static final int q = 16;
    public static final int r = 17;
    public static final int s = 18;
    public static final int t = 19;
    public static final int u = 20;
    public static final int v = 21;
    public static final int w = 22;
    public static final int x = 23;
    protected static byte[][] y;
    protected int z;
    byte[] aa;
    protected int ab;
    protected psc_n ac;
    protected String ad;
    
    protected psc_fz(final int z, final String ad) {
        this.z = z;
        if (z != 23) {
            this.aa = new byte[psc_fz.y[z].length];
            System.arraycopy(psc_fz.y[z], 0, this.aa, 0, this.aa.length);
        }
        this.ad = ad;
    }
    
    public static psc_fz a(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        final psc_i[] b = b(array, n, n2);
        final int f = f(b[1].b, b[1].c, b[1].d);
        psc_fz psc_fz = null;
        switch (f) {
            case 0: {
                psc_fz = new psc_f1();
                break;
            }
            case 1: {
                psc_fz = new psc_f2();
                break;
            }
            case 2: {
                psc_fz = new psc_f3();
                break;
            }
            case 3: {
                psc_fz = new psc_f4();
                break;
            }
            case 4: {
                psc_fz = new psc_f5();
                break;
            }
            case 5: {
                psc_fz = new psc_f6();
                break;
            }
            case 6: {
                psc_fz = new psc_f7();
                break;
            }
            case 7: {
                psc_fz = new psc_f9();
                break;
            }
            case 8: {
                psc_fz = new psc_ga();
                break;
            }
            case 9: {
                psc_fz = new psc_gb();
                break;
            }
            case 10: {
                psc_fz = new psc_gc();
                break;
            }
            case 11: {
                psc_fz = new psc_gd();
                break;
            }
            case 12: {
                psc_fz = new psc_ge();
                break;
            }
            case 13: {
                psc_fz = new psc_gf();
                break;
            }
            case 14: {
                psc_fz = new psc_gg();
                break;
            }
            case 15: {
                psc_fz = new psc_gh();
                break;
            }
            case 16: {
                psc_fz = new psc_gi();
                break;
            }
            case 17: {
                psc_fz = new psc_gj();
                break;
            }
            case 18: {
                psc_fz = new psc_gk();
                break;
            }
            case 19: {
                psc_fz = new psc_gl();
                break;
            }
            case 20: {
                psc_fz = new psc_gm();
                break;
            }
            case 21: {
                psc_fz = new psc_gn();
                break;
            }
            case 22: {
                psc_fz = new psc_go();
                break;
            }
            case 23: {
                psc_fz = new psc_gp();
                psc_fz.aa = new byte[b[1].d];
                System.arraycopy(b[1].b, b[1].c, psc_fz.aa, 0, b[1].d);
                break;
            }
        }
        psc_fz.a(b[2].b, b[2].c);
        for (int i = 0; i < b.length; ++i) {
            b[i].i();
        }
        return psc_fz;
    }
    
    protected static psc_i[] b(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        try {
            final psc_i[] array2 = { new psc_h(n2), new psc_r(16777216), new psc_k(12544), new psc_j() };
            psc_l.a(array, n, array2);
            return array2;
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Cannot read the BER of the attribute.");
        }
    }
    
    private static int f(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return 23;
        }
        for (int i = 0; i < 23; ++i) {
            if (n2 == psc_fz.y[i].length) {
                int n3;
                for (n3 = 0; n3 < n2 && (array[n3 + n] & 0xFF) == (psc_fz.y[i][n3] & 0xFF); ++n3) {}
                if (n3 >= n2) {
                    return i;
                }
            }
        }
        return 23;
    }
    
    public String a() {
        return this.ad;
    }
    
    protected abstract void a(final byte[] p0, final int p1) throws psc_f0;
    
    public byte[] b() {
        if (this.aa == null) {
            return null;
        }
        return this.aa.clone();
    }
    
    public boolean a(final byte[] array) {
        if (array == null) {
            return false;
        }
        if (array.length != this.aa.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != this.aa[i]) {
                return false;
            }
        }
        return true;
    }
    
    public int c() {
        return this.z;
    }
    
    public int a(final int n) {
        return this.a(n, this.d());
    }
    
    protected abstract int d();
    
    public static int b(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        try {
            return n + psc_o.b(array, n);
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not read the BER encoding.");
        }
    }
    
    public int c(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Passed array is null.");
        }
        return this.d(array, n, n2);
    }
    
    public boolean b(final int n) {
        return n == this.z;
    }
    
    public int a(final int ab, final int n) {
        if (n == 0) {
            return 0;
        }
        this.ab = ab;
        try {
            this.ac = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_r(16777216, true, 0, this.aa, 0, this.aa.length), new psc_k(12544, true, 0, null, 0, n), new psc_j() });
            return this.ac.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    protected int d(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Passed array is null.");
        }
        if ((this.ac == null || this.ab != n2) && this.a(n2) == 0) {
            throw new psc_f0("Could not encode, missing data");
        }
        int a;
        try {
            a = this.ac.a(array, n);
            this.ac = null;
        }
        catch (psc_m psc_m) {
            this.ac = null;
            return 0;
        }
        return a + this.c(array, n + a);
    }
    
    protected abstract int c(final byte[] p0, final int p1);
    
    protected void a(final psc_fz psc_fz) {
        psc_fz.ab = this.ab;
        if (this.ac == null) {
            return;
        }
        psc_fz.a(this.ab);
    }
    
    public abstract Object clone() throws CloneNotSupportedException;
    
    protected void e() {
        this.ac = null;
        this.ab = 0;
    }
    
    protected byte[] a(final String s) throws psc_f0 {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
        }
        catch (IOException ex) {
            throw new psc_f0("pseudonym.utf8Encode: unable to utf8-encode " + s + "(" + ex.getMessage() + ").");
        }
        return out.toByteArray();
    }
    
    protected String e(final byte[] array, final int n, final int n2) throws psc_f0 {
        final byte[] buf = new byte[n2 + 2];
        buf[0] = (byte)(n2 >> 8 & 0xFF);
        buf[1] = (byte)(n2 & 0xFF);
        System.arraycopy(array, n, buf, 2, n2);
        try {
            return new DataInputStream(new ByteArrayInputStream(buf, 0, buf.length)).readUTF();
        }
        catch (IOException ex) {
            throw new psc_f0("Pseudonym.utf8Decode: " + ex.getMessage() + ").");
        }
    }
    
    public void f() {
        this.e();
    }
    
    static {
        psc_fz.y = new byte[][] { { 42, -122, 72, -122, -9, 13, 1, 9, 5 }, { 42, -122, 72, -122, -9, 13, 1, 9, 7 }, { 42, -122, 72, -122, -9, 13, 1, 9, 14 }, { 42, -122, 72, -122, -9, 13, 1, 9, 20 }, { 42, -122, 72, -122, -9, 13, 1, 9, 21 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 1 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 2 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 3 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 4 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 5 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 6 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 7 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 9 }, { 96, -122, 72, 1, -122, -8, 69, 1, 9, 10 }, { 42, -122, 72, -122, -9, 13, 1, 9, 3 }, { 42, -122, 72, -122, -9, 13, 1, 9, 4 }, { 85, 4, 16 }, { 85, 4, 65 }, { 43, 6, 1, 5, 5, 7, 9, 1 }, { 43, 6, 1, 5, 5, 7, 9, 2 }, { 43, 6, 1, 5, 5, 7, 9, 3 }, { 43, 6, 1, 5, 5, 7, 9, 4 }, { 43, 6, 1, 5, 5, 7, 9, 5 } };
    }
}
