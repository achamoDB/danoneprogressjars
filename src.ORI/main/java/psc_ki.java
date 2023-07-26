import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ki extends psc_an implements psc_dl, Cloneable, Serializable
{
    private static final byte[] a;
    private int[] b;
    protected int c;
    private int d;
    private transient psc_dd e;
    private int f;
    private static final int g = 0;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 4;
    public static final int k = 16;
    
    public psc_ki() {
        this.b = new int[60];
        this.f = 0;
    }
    
    public psc_ki(final int[] array) throws psc_be {
        this.b = new int[60];
        this.f = 0;
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null) {
            return;
        }
        if (array.length == 0) {
            return;
        }
        throw new psc_be("Incorrect number of AES algorithm parameters: expected none.");
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_di.a(array, n, n2);
    }
    
    public byte[] a(final psc_di psc_di, final psc_dm psc_dm) throws psc_ao {
        throw new psc_ao("Not yet implemented.");
    }
    
    public byte[] a(final byte[] array) {
        return array;
    }
    
    public String d() {
        if (this.d == 10) {
            return "AES128";
        }
        if (this.d == 12) {
            return "AES192";
        }
        if (this.d == 14) {
            return "AES256";
        }
        return "AES";
    }
    
    public int g() {
        return 16;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int n) {
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.a(psc_dc, true);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.a(psc_dc, false);
    }
    
    private void a(final psc_dc psc_dc, final boolean b) throws psc_bf {
        byte[] a;
        try {
            a = ((psc_jz)psc_dc).a("Clear");
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        if (a == null) {
            throw new psc_bf("Invalid AES key length");
        }
        if (this.e != null) {
            this.e.e();
        }
        final int length = a.length;
        this.d = length / 4 + 6;
        if (this.c != 0 && this.d != this.c) {
            throw new psc_bf("AES" + (this.c - 6) * 32 + " instantiated, key size is " + (this.d - 6) * 32);
        }
        int n;
        if (length == 16) {
            n = 4;
        }
        else if (length == 24) {
            n = 6;
        }
        else {
            if (length != 32) {
                throw new psc_bf("Invalid AES key length");
            }
            n = 8;
        }
        for (int i = 0, n2 = 0; i < length; this.b[n2++] = ((a[i++] & 0xFF) << 24 | (a[i++] & 0xFF) << 16 | (a[i++] & 0xFF) << 8 | (a[i++] & 0xFF))) {}
        final int n3 = 4 * (this.d + 1);
        int n4 = n;
        int n5 = 0;
        int n6 = this.b[n4 - 1];
        int n7 = 0;
        for (int j = n; j < n3; j += n) {
            final int n8 = (psc_n2.a[n6 >> 16 & 0xFF] & 0xFF) << 24 ^ (psc_n2.a[n6 >> 8 & 0xFF] & 0xFF) << 16 ^ (psc_n2.a[n6 & 0xFF] & 0xFF) << 8 ^ (psc_n2.a[n6 >> 24 & 0xFF] & 0xFF) ^ (psc_ki.a[n7++] & 0xFF) << 24 ^ this.b[n5++];
            this.b[n4++] = n8;
            final int n9 = n8 ^ this.b[n5++];
            this.b[n4++] = n9;
            final int n10 = n9 ^ this.b[n5++];
            this.b[n4++] = n10;
            n6 = (n10 ^ this.b[n5++]);
            this.b[n4++] = n6;
            if (n >= 6) {
                if (n == 6) {
                    final int n11 = n6 ^ this.b[n5++];
                    this.b[n4++] = n11;
                    n6 = (n11 ^ this.b[n5++]);
                    this.b[n4++] = n6;
                }
                else {
                    if (j == 56) {
                        break;
                    }
                    final int n12 = ((psc_n2.a[n6 & 0xFF] & 0xFF) | (psc_n2.a[n6 >> 8 & 0xFF] & 0xFF) << 8 | (psc_n2.a[n6 >> 16 & 0xFF] & 0xFF) << 16 | (psc_n2.a[n6 >> 24 & 0xFF] & 0xFF) << 24) ^ this.b[n5++];
                    this.b[n4++] = n12;
                    final int n13 = n12 ^ this.b[n5++];
                    this.b[n4++] = n13;
                    final int n14 = n13 ^ this.b[n5++];
                    this.b[n4++] = n14;
                    n6 = (n14 ^ this.b[n5++]);
                    this.b[n4++] = n6;
                }
            }
        }
        if (!b) {
            int n15 = 4;
            for (int k = 1; k < this.d; ++k) {
                for (int l = 0; l < 4; ++l) {
                    final int n16 = this.b[n15];
                    this.b[n15++] = (psc_n2.b[n16 >>> 24 & 0xFF] ^ psc_n2.c[n16 >>> 16 & 0xFF] ^ psc_n2.d[n16 >>> 8 & 0xFF] ^ psc_n2.e[n16 & 0xFF]);
                }
            }
        }
        this.e();
        this.d(a);
    }
    
    public int a(final byte[] array, int n, final byte[] array2, int n2) {
        int n3 = 4;
        int n4 = this.b[0] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n5 = this.b[1] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n6 = this.b[2] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n7 = this.b[3] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        for (int i = this.d - 1; i > 0; --i) {
            final int n8 = psc_n2.f[n4 >>> 24] ^ psc_n2.g[n5 >>> 16 & 0xFF] ^ psc_n2.h[n6 >>> 8 & 0xFF] ^ psc_n2.i[n7 & 0xFF];
            final int n9 = psc_n2.f[n5 >>> 24] ^ psc_n2.g[n6 >>> 16 & 0xFF] ^ psc_n2.h[n7 >>> 8 & 0xFF] ^ psc_n2.i[n4 & 0xFF];
            final int n10 = psc_n2.f[n6 >>> 24] ^ psc_n2.g[n7 >>> 16 & 0xFF] ^ psc_n2.h[n4 >>> 8 & 0xFF] ^ psc_n2.i[n5 & 0xFF];
            final int n11 = psc_n2.f[n7 >>> 24] ^ psc_n2.g[n4 >>> 16 & 0xFF] ^ psc_n2.h[n5 >>> 8 & 0xFF] ^ psc_n2.i[n6 & 0xFF];
            n4 = (n8 ^ this.b[n3++]);
            n5 = (n9 ^ this.b[n3++]);
            n6 = (n10 ^ this.b[n3++]);
            n7 = (n11 ^ this.b[n3++]);
        }
        final int n12 = this.b[n3++] ^ ((psc_n2.h[n4 >>> 24] & 0xFF000000) | (psc_n2.f[n5 >>> 16 & 0xFF] & 0xFF0000) | (psc_n2.f[n6 >>> 8 & 0xFF] & 0xFF00) | (psc_n2.h[n7 & 0xFF] & 0xFF));
        final int n13 = this.b[n3++] ^ ((psc_n2.h[n5 >>> 24] & 0xFF000000) | (psc_n2.f[n6 >>> 16 & 0xFF] & 0xFF0000) | (psc_n2.f[n7 >>> 8 & 0xFF] & 0xFF00) | (psc_n2.h[n4 & 0xFF] & 0xFF));
        final int n14 = this.b[n3++] ^ ((psc_n2.h[n6 >>> 24] & 0xFF000000) | (psc_n2.f[n7 >>> 16 & 0xFF] & 0xFF0000) | (psc_n2.f[n4 >>> 8 & 0xFF] & 0xFF00) | (psc_n2.h[n5 & 0xFF] & 0xFF));
        final int n15 = this.b[n3++] ^ ((psc_n2.h[n7 >>> 24] & 0xFF000000) | (psc_n2.f[n4 >>> 16 & 0xFF] & 0xFF0000) | (psc_n2.f[n5 >>> 8 & 0xFF] & 0xFF00) | (psc_n2.h[n6 & 0xFF] & 0xFF));
        array2[n2++] = (byte)(n12 >> 24);
        array2[n2++] = (byte)(n12 >> 16);
        array2[n2++] = (byte)(n12 >> 8);
        array2[n2++] = (byte)n12;
        array2[n2++] = (byte)(n13 >> 24);
        array2[n2++] = (byte)(n13 >> 16);
        array2[n2++] = (byte)(n13 >> 8);
        array2[n2++] = (byte)n13;
        array2[n2++] = (byte)(n14 >> 24);
        array2[n2++] = (byte)(n14 >> 16);
        array2[n2++] = (byte)(n14 >> 8);
        array2[n2++] = (byte)n14;
        array2[n2++] = (byte)(n15 >> 24);
        array2[n2++] = (byte)(n15 >> 16);
        array2[n2++] = (byte)(n15 >> 8);
        array2[n2++] = (byte)n15;
        return 16;
    }
    
    public int b(final byte[] array, int n, final byte[] array2, int n2) {
        int n3 = this.d * 4;
        int n4 = this.b[n3++] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n5 = this.b[n3++] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n6 = this.b[n3++] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        int n7 = this.b[n3++] ^ ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF));
        n3 -= 8;
        for (int i = this.d - 1; i > 0; --i) {
            final int n8 = psc_n2.j[n4 >>> 24] ^ psc_n2.k[n7 >>> 16 & 0xFF] ^ psc_n2.l[n6 >>> 8 & 0xFF] ^ psc_n2.m[n5 & 0xFF];
            final int n9 = psc_n2.j[n5 >>> 24] ^ psc_n2.k[n4 >>> 16 & 0xFF] ^ psc_n2.l[n7 >>> 8 & 0xFF] ^ psc_n2.m[n6 & 0xFF];
            final int n10 = psc_n2.j[n6 >>> 24] ^ psc_n2.k[n5 >>> 16 & 0xFF] ^ psc_n2.l[n4 >>> 8 & 0xFF] ^ psc_n2.m[n7 & 0xFF];
            final int n11 = psc_n2.j[n7 >>> 24] ^ psc_n2.k[n6 >>> 16 & 0xFF] ^ psc_n2.l[n5 >>> 8 & 0xFF] ^ psc_n2.m[n4 & 0xFF];
            n4 = (n8 ^ this.b[n3++]);
            n5 = (n9 ^ this.b[n3++]);
            n6 = (n10 ^ this.b[n3++]);
            n7 = (n11 ^ this.b[n3++]);
            n3 -= 8;
        }
        final int n12 = this.b[0];
        final int n13 = this.b[1];
        final int n14 = this.b[2];
        final int n15 = this.b[3];
        array2[n2++] = (byte)(psc_n2.n[n4 >>> 24] ^ n12 >>> 24);
        array2[n2++] = (byte)(psc_n2.n[n7 >>> 16 & 0xFF] ^ n12 >>> 16);
        array2[n2++] = (byte)(psc_n2.n[n6 >>> 8 & 0xFF] ^ n12 >>> 8);
        array2[n2++] = (byte)(psc_n2.n[n5 & 0xFF] ^ n12);
        array2[n2++] = (byte)(psc_n2.n[n5 >>> 24] ^ n13 >>> 24);
        array2[n2++] = (byte)(psc_n2.n[n4 >>> 16 & 0xFF] ^ n13 >>> 16);
        array2[n2++] = (byte)(psc_n2.n[n7 >>> 8 & 0xFF] ^ n13 >>> 8);
        array2[n2++] = (byte)(psc_n2.n[n6 & 0xFF] ^ n13);
        array2[n2++] = (byte)(psc_n2.n[n6 >>> 24] ^ n14 >>> 24);
        array2[n2++] = (byte)(psc_n2.n[n5 >>> 16 & 0xFF] ^ n14 >>> 16);
        array2[n2++] = (byte)(psc_n2.n[n4 >>> 8 & 0xFF] ^ n14 >>> 8);
        array2[n2++] = (byte)(psc_n2.n[n7 & 0xFF] ^ n14);
        array2[n2++] = (byte)(psc_n2.n[n7 >>> 24] ^ n15 >>> 24);
        array2[n2++] = (byte)(psc_n2.n[n6 >>> 16 & 0xFF] ^ n15 >>> 16);
        array2[n2++] = (byte)(psc_n2.n[n5 >>> 8 & 0xFF] ^ n15 >>> 8);
        array2[n2++] = (byte)(psc_n2.n[n4 & 0xFF] ^ n15);
        return 16;
    }
    
    public byte[] a(final psc_am psc_am, final boolean b, final psc_di psc_di, final psc_dm psc_dm) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_al b(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public void e() {
        if ((this.f & 0x2) != 0x0) {
            return;
        }
        if (this.e == null) {
            this.e = psc_au.b(this.b);
            if (!this.e.a()) {
                this.f = 2;
                return;
            }
            this.f = 1;
        }
        else {
            this.e.c();
            this.f = 1;
        }
    }
    
    public void f() {
        if ((this.f & 0x1) != 0x0) {
            this.e.d();
            this.f = 4;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ki psc_ki = new psc_ki();
        this.a(psc_ki);
        return psc_ki;
    }
    
    protected void a(final psc_ki psc_ki) throws CloneNotSupportedException {
        final int f = this.f;
        this.f();
        if (this.b != null) {
            System.arraycopy(this.b, 0, psc_ki.b, 0, this.b.length);
        }
        if ((f & 0x1) != 0x0) {
            this.e();
            psc_ki.e();
        }
        psc_ki.c = this.c;
        psc_ki.d = this.d;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.h();
        objectOutputStream.defaultWriteObject();
        this.i();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.j();
    }
    
    private void h() {
        if ((this.f & 0x1) == 0x0) {
            return;
        }
        this.f();
        this.f = 1;
    }
    
    private void i() {
        if (this.f == 1) {
            this.f = 4;
            this.e();
        }
    }
    
    private void j() {
        if (this.f == 1) {
            this.f = 0;
            this.e();
        }
    }
    
    public void y() {
        super.y();
        psc_au.b(this.b, this.e);
        this.e = null;
        this.f = 0;
        this.d = 0;
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
        a = new byte[] { 1, 2, 4, 8, 16, 32, 64, -128, 27, 54, 108, -40, -85, 77, -102, 47, 94, -68, 99, -58, -105, 53, 106, -44, -77, 125, -6, -17, -59, -111 };
    }
}
