import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_ks extends psc_kr implements Cloneable, Serializable
{
    private long[] a;
    private transient psc_dd b;
    private int c;
    private static final long d = -5196783011329398165L;
    private static final long e = -7046029254386353131L;
    private long f;
    private long g;
    
    psc_ks(final int n) {
        final int c = 2 * n + 2;
        this.a = new long[c];
        this.c = c;
    }
    
    private psc_ks() {
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.e();
        objectOutputStream.defaultWriteObject();
        this.f();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.g();
    }
    
    private void e() {
        if (this.b != null) {
            this.b.d();
        }
    }
    
    private void f() {
        if (this.b != null) {
            this.b.c();
        }
    }
    
    private void g() {
        if (this.a == null) {
            return;
        }
        (this.b = psc_au.b(this.a)).c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ks psc_ks = new psc_ks();
        psc_ks.a = null;
        if (this.a != null) {
            psc_ks.a = (long[])psc_au.a(this.a, this.b);
        }
        psc_ks.b = psc_au.a(psc_ks.a);
        psc_ks.f = this.f;
        psc_ks.g = this.g;
        psc_ks.c = this.c;
        return psc_ks;
    }
    
    void a(final byte[] array, int n, int i) {
        if (this.b != null) {
            this.b.e();
        }
        final int n2 = (i == 0) ? 1 : ((i + 7) / 8);
        final long[] array2 = new long[n2];
        int n3 = 0;
        while (i >= 8) {
            final long[] array3 = array2;
            final int n4 = n3;
            array3[n4] += ((long)array[n] & 0xFFL);
            final long[] array4 = array2;
            final int n5 = n3;
            array4[n5] += ((long)array[n + 1] & 0xFFL) << 8;
            final long[] array5 = array2;
            final int n6 = n3;
            array5[n6] += ((long)array[n + 2] & 0xFFL) << 16;
            final long[] array6 = array2;
            final int n7 = n3;
            array6[n7] += ((long)array[n + 3] & 0xFFL) << 24;
            final long[] array7 = array2;
            final int n8 = n3;
            array7[n8] += ((long)array[n + 4] & 0xFFL) << 32;
            final long[] array8 = array2;
            final int n9 = n3;
            array8[n9] += ((long)array[n + 5] & 0xFFL) << 40;
            final long[] array9 = array2;
            final int n10 = n3;
            array9[n10] += ((long)array[n + 6] & 0xFFL) << 48;
            final long[] array10 = array2;
            final int n11 = n3;
            array10[n11] += ((long)array[n + 7] & 0xFFL) << 56;
            ++n3;
            n += 8;
            i -= 8;
        }
        for (int n12 = 0; i > 0; --i, ++n, n12 += 8) {
            final long[] array11 = array2;
            final int n13 = n3;
            array11[n13] += ((long)array[n] & 0xFFL) << n12;
        }
        this.a[0] = -5196783011329398165L;
        for (int j = 1; j < this.c; ++j) {
            this.a[j] = this.a[j - 1] - 7046029254386353131L;
        }
        int n14 = 0;
        int n15 = 0;
        this.f = 0L;
        this.g = 0L;
        for (int k = (n2 > this.c) ? (3 * n2) : (3 * this.c); k > 0; --k) {
            this.f = this.f + this.g + this.a[n14];
            this.f = (this.f << 3 | this.f >>> 61);
            this.a[n14] = this.f;
            final long n16 = this.f + this.g;
            this.g = array2[n15] + n16;
            final long n17 = n16 & 0x3FL;
            array2[n15] = (this.g = (this.g << (int)n17 | this.g >>> (int)(64L - n17)));
            if (++n14 >= this.c) {
                n14 = 0;
            }
            if (++n15 >= n2) {
                n15 = 0;
            }
        }
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        psc_au.c(array2);
    }
    
    void a(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.f = ((long)array[n] & 0xFFL);
        this.f += ((long)array[n + 1] & 0xFFL) << 8;
        this.f += ((long)array[n + 2] & 0xFFL) << 16;
        this.f += ((long)array[n + 3] & 0xFFL) << 24;
        this.f += ((long)array[n + 4] & 0xFFL) << 32;
        this.f += ((long)array[n + 5] & 0xFFL) << 40;
        this.f += ((long)array[n + 6] & 0xFFL) << 48;
        this.f += ((long)array[n + 7] & 0xFFL) << 56;
        this.g = ((long)array[n + 8] & 0xFFL);
        this.g += ((long)array[n + 9] & 0xFFL) << 8;
        this.g += ((long)array[n + 10] & 0xFFL) << 16;
        this.g += ((long)array[n + 11] & 0xFFL) << 24;
        this.g += ((long)array[n + 12] & 0xFFL) << 32;
        this.g += ((long)array[n + 13] & 0xFFL) << 40;
        this.g += ((long)array[n + 14] & 0xFFL) << 48;
        this.g += ((long)array[n + 15] & 0xFFL) << 56;
        this.f += this.a[0];
        this.g += this.a[1];
        for (int i = 2; i < this.c; i += 2) {
            this.f ^= this.g;
            final long n3 = this.g & 0x3FL;
            this.f = (this.f << (int)n3 | this.f >>> (int)(64L - n3));
            this.f += this.a[i];
            this.g ^= this.f;
            final long n4 = this.f & 0x3FL;
            this.g = (this.g << (int)n4 | this.g >>> (int)(64L - n4));
            this.g += this.a[i + 1];
        }
        array2[n2] = (byte)(this.f & 0xFFL);
        array2[n2 + 1] = (byte)(this.f >>> 8 & 0xFFL);
        array2[n2 + 2] = (byte)(this.f >>> 16 & 0xFFL);
        array2[n2 + 3] = (byte)(this.f >>> 24 & 0xFFL);
        array2[n2 + 4] = (byte)(this.f >>> 32 & 0xFFL);
        array2[n2 + 5] = (byte)(this.f >>> 40 & 0xFFL);
        array2[n2 + 6] = (byte)(this.f >>> 48 & 0xFFL);
        array2[n2 + 7] = (byte)(this.f >>> 56 & 0xFFL);
        array2[n2 + 8] = (byte)(this.g & 0xFFL);
        array2[n2 + 9] = (byte)(this.g >>> 8 & 0xFFL);
        array2[n2 + 10] = (byte)(this.g >>> 16 & 0xFFL);
        array2[n2 + 11] = (byte)(this.g >>> 24 & 0xFFL);
        array2[n2 + 12] = (byte)(this.g >>> 32 & 0xFFL);
        array2[n2 + 13] = (byte)(this.g >>> 40 & 0xFFL);
        array2[n2 + 14] = (byte)(this.g >>> 48 & 0xFFL);
        array2[n2 + 15] = (byte)(this.g >>> 56 & 0xFFL);
    }
    
    void b(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.f = ((long)array[n] & 0xFFL);
        this.f += ((long)array[n + 1] & 0xFFL) << 8;
        this.f += ((long)array[n + 2] & 0xFFL) << 16;
        this.f += ((long)array[n + 3] & 0xFFL) << 24;
        this.f += ((long)array[n + 4] & 0xFFL) << 32;
        this.f += ((long)array[n + 5] & 0xFFL) << 40;
        this.f += ((long)array[n + 6] & 0xFFL) << 48;
        this.f += ((long)array[n + 7] & 0xFFL) << 56;
        this.g = ((long)array[n + 8] & 0xFFL);
        this.g += ((long)array[n + 9] & 0xFFL) << 8;
        this.g += ((long)array[n + 10] & 0xFFL) << 16;
        this.g += ((long)array[n + 11] & 0xFFL) << 24;
        this.g += ((long)array[n + 12] & 0xFFL) << 32;
        this.g += ((long)array[n + 13] & 0xFFL) << 40;
        this.g += ((long)array[n + 14] & 0xFFL) << 48;
        this.g += ((long)array[n + 15] & 0xFFL) << 56;
        for (int i = this.c - 1; i > 2; i -= 2) {
            this.g -= this.a[i];
            final long n3 = this.f & 0x3FL;
            this.g = (this.g >>> (int)n3 | this.g << (int)(64L - n3));
            this.g ^= this.f;
            this.f -= this.a[i - 1];
            final long n4 = this.g & 0x3FL;
            this.f = (this.f >>> (int)n4 | this.f << (int)(64L - n4));
            this.f ^= this.g;
        }
        this.g -= this.a[1];
        this.f -= this.a[0];
        array2[n2] = (byte)(this.f & 0xFFL);
        array2[n2 + 1] = (byte)(this.f >>> 8 & 0xFFL);
        array2[n2 + 2] = (byte)(this.f >>> 16 & 0xFFL);
        array2[n2 + 3] = (byte)(this.f >>> 24 & 0xFFL);
        array2[n2 + 4] = (byte)(this.f >>> 32 & 0xFFL);
        array2[n2 + 5] = (byte)(this.f >>> 40 & 0xFFL);
        array2[n2 + 6] = (byte)(this.f >>> 48 & 0xFFL);
        array2[n2 + 7] = (byte)(this.f >>> 56 & 0xFFL);
        array2[n2 + 8] = (byte)(this.g & 0xFFL);
        array2[n2 + 9] = (byte)(this.g >>> 8 & 0xFFL);
        array2[n2 + 10] = (byte)(this.g >>> 16 & 0xFFL);
        array2[n2 + 11] = (byte)(this.g >>> 24 & 0xFFL);
        array2[n2 + 12] = (byte)(this.g >>> 32 & 0xFFL);
        array2[n2 + 13] = (byte)(this.g >>> 40 & 0xFFL);
        array2[n2 + 14] = (byte)(this.g >>> 48 & 0xFFL);
        array2[n2 + 15] = (byte)(this.g >>> 56 & 0xFFL);
    }
    
    void c() {
        if (this.b != null) {
            this.b.c();
        }
    }
    
    void d() {
        if (this.b != null) {
            this.b.d();
        }
    }
    
    void y() {
        super.y();
        psc_au.c(this.a, this.b);
        this.b = null;
        this.f = 0L;
        this.g = 0L;
    }
}
