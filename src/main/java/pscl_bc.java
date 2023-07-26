import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_bc extends pscl_bd implements Cloneable, Serializable
{
    private int[] a;
    private int b;
    private int c;
    private int d;
    private int e;
    
    public pscl_bc() {
        this.a = new int[256];
    }
    
    public pscl_bc(final int[] array) throws pscl_bk {
        this.a = new int[256];
        this.a(array);
    }
    
    public void a(final int[] array) throws pscl_bk {
        if (array != null && array.length != 0) {
            throw new pscl_bk("Incorrect number of parameters: expected none.");
        }
    }
    
    public int[] a() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public String b() {
        return "RC4";
    }
    
    public int a(final int n) {
        return n;
    }
    
    public void a(final byte[] array, final pscl_e pscl_e) {
        this.c(array, pscl_e);
    }
    
    public void b(final byte[] array, final pscl_e pscl_e) {
        this.c(array, pscl_e);
    }
    
    public void c(final byte[] array, final pscl_e pscl_e) {
        for (int i = 0; i <= 255; ++i) {
            this.a[i] = i;
        }
        this.d = 0;
        this.e = 0;
        int n = 0;
        while (this.d < 256) {
            this.b = this.a[this.d];
            this.e += array[n];
            this.e += this.b;
            this.e &= 0xFF;
            this.c = this.a[this.e];
            ++this.d;
            ++n;
            this.a[this.d - 1] = this.c;
            this.a[this.e] = this.b;
            if (n == array.length) {
                n = 0;
            }
        }
        this.d = 0;
        this.e = 0;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        if (n2 <= 0) {
            return 0;
        }
        this.c(array, n, n2, array2, n3);
        return n2;
    }
    
    public int b(final byte[] array, final int n) {
        return 0;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        if (n2 <= 0) {
            return 0;
        }
        this.c(array, n, n2, array2, n3);
        return n2;
    }
    
    public int c(final byte[] array, final int n) {
        return 0;
    }
    
    private void c(final byte[] array, int i, int n, final byte[] array2, int n2) {
        for (n += i; i < n; ++i, ++n2) {
            ++this.d;
            this.d &= 0xFF;
            this.b = this.a[this.d];
            this.e = (this.e + this.b & 0xFF);
            this.c = this.a[this.e];
            this.a[this.d] = this.c;
            this.a[this.e] = this.b;
            this.c = this.a[this.c + this.b & 0xFF];
            array2[n2] = (byte)(array[i] ^ this.c);
        }
    }
    
    public void c() {
        super.c();
        this.d = 0;
        this.e = 0;
        this.b = 0;
        this.c = 0;
    }
    
    public void finalize() {
        this.c();
    }
}
