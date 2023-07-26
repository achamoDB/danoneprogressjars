import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_kq extends psc_kr implements Cloneable, Serializable
{
    private int[] a;
    private transient psc_dd b;
    private int c;
    private static final int d = -1209970333;
    private static final int e = -1640531527;
    private int f;
    private int g;
    
    psc_kq(final int n) {
        final int c = 2 * n + 2;
        this.a = new int[c];
        this.c = c;
    }
    
    private psc_kq() {
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
        final psc_kq psc_kq = new psc_kq();
        psc_kq.a = null;
        if (this.a != null) {
            psc_kq.a = (int[])psc_au.a(this.a, this.b);
        }
        psc_kq.b = psc_au.a(psc_kq.a);
        psc_kq.f = this.f;
        psc_kq.g = this.g;
        psc_kq.c = this.c;
        return psc_kq;
    }
    
    void a(final byte[] array, int n, int i) {
        if (this.b != null) {
            this.b.e();
        }
        final int n2 = (i == 0) ? 1 : ((i + 3) / 4);
        final int[] array2 = new int[n2];
        int n3 = 0;
        while (i >= 4) {
            final int[] array3 = array2;
            final int n4 = n3;
            array3[n4] += (array[n] & 0xFF);
            ++n;
            final int[] array4 = array2;
            final int n5 = n3;
            array4[n5] += (array[n] & 0xFF) << 8;
            ++n;
            final int[] array5 = array2;
            final int n6 = n3;
            array5[n6] += (array[n] & 0xFF) << 16;
            ++n;
            final int[] array6 = array2;
            final int n7 = n3;
            array6[n7] += (array[n] & 0xFF) << 24;
            ++n3;
            ++n;
            i -= 4;
        }
        for (int n8 = 0; i > 0; --i, ++n, n8 += 8) {
            final int[] array7 = array2;
            final int n9 = n3;
            array7[n9] += (array[n] & 0xFF) << n8;
        }
        this.a[0] = -1209970333;
        for (int j = 1; j < this.c; ++j) {
            this.a[j] = this.a[j - 1] - 1640531527;
        }
        int n10 = 0;
        int n11 = 0;
        this.f = 0;
        this.g = 0;
        for (int k = (n2 > this.c) ? (3 * n2) : (3 * this.c); k > 0; --k) {
            this.f = this.f + this.g + this.a[n10];
            this.f = (this.f << 3 | this.f >>> 29);
            this.a[n10] = this.f;
            final int n12 = this.f + this.g;
            this.g = array2[n11] + n12;
            final int n13 = n12 & 0x1F;
            array2[n11] = (this.g = (this.g << n13 | this.g >>> 32 - n13));
            if (++n10 >= this.c) {
                n10 = 0;
            }
            if (++n11 >= n2) {
                n11 = 0;
            }
        }
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        psc_au.c(array2);
    }
    
    void a(final byte[] array, int n, final byte[] array2, int n2) {
        this.f = (array[n] & 0xFF);
        ++n;
        this.f += (array[n] & 0xFF) << 8;
        ++n;
        this.f += (array[n] & 0xFF) << 16;
        ++n;
        this.f += (array[n] & 0xFF) << 24;
        ++n;
        this.g = (array[n] & 0xFF);
        ++n;
        this.g += (array[n] & 0xFF) << 8;
        ++n;
        this.g += (array[n] & 0xFF) << 16;
        ++n;
        this.g += (array[n] & 0xFF) << 24;
        this.f += this.a[0];
        this.g += this.a[1];
        for (int i = 2; i < this.c; i += 2) {
            this.f ^= this.g;
            final int n3 = this.g & 0x1F;
            this.f = (this.f << n3 | this.f >>> 32 - n3);
            this.f += this.a[i];
            this.g ^= this.f;
            final int n4 = this.f & 0x1F;
            this.g = (this.g << n4 | this.g >>> 32 - n4);
            this.g += this.a[i + 1];
        }
        array2[n2] = (byte)(this.f & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 8 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 16 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 24 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 8 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 16 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 24 & 0xFF);
    }
    
    void b(final byte[] array, int n, final byte[] array2, int n2) {
        this.f = (array[n] & 0xFF);
        ++n;
        this.f += (array[n] & 0xFF) << 8;
        ++n;
        this.f += (array[n] & 0xFF) << 16;
        ++n;
        this.f += (array[n] & 0xFF) << 24;
        ++n;
        this.g = (array[n] & 0xFF);
        ++n;
        this.g += (array[n] & 0xFF) << 8;
        ++n;
        this.g += (array[n] & 0xFF) << 16;
        ++n;
        this.g += (array[n] & 0xFF) << 24;
        for (int i = this.c - 1; i > 2; i -= 2) {
            this.g -= this.a[i];
            final int n3 = this.f & 0x1F;
            this.g = (this.g >>> n3 | this.g << 32 - n3);
            this.g ^= this.f;
            this.f -= this.a[i - 1];
            final int n4 = this.g & 0x1F;
            this.f = (this.f >>> n4 | this.f << 32 - n4);
            this.f ^= this.g;
        }
        this.g -= this.a[1];
        this.f -= this.a[0];
        array2[n2] = (byte)(this.f & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 8 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 16 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.f >>> 24 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 8 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 16 & 0xFF);
        ++n2;
        array2[n2] = (byte)(this.g >>> 24 & 0xFF);
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
        this.f = 0;
        this.g = 0;
    }
}
