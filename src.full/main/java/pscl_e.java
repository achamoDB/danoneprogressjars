// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_e
{
    private boolean a;
    private pscl_d b;
    private int c;
    private int d;
    private int e;
    private byte[] f;
    private byte[] g;
    private static final byte[] h;
    private static final byte[] i;
    
    public pscl_e(final pscl_d b) {
        this.b = b;
        this.e = 2;
        this.c = this.b.d();
        if (b instanceof pscl_aq) {
            this.g = pscl_e.h;
        }
        else {
            this.g = pscl_e.i;
        }
        this.f = new byte[2 * this.c];
    }
    
    public String a() {
        return this.b.a() + "Random";
    }
    
    public synchronized void a(final byte[] array) {
        if (this.e > 0) {
            --this.e;
        }
        if (!this.a) {
            this.b.f();
            if (this.e == 0) {
                this.b.a(this.f, 0, this.c);
            }
        }
        this.b.a(array, 0, array.length);
        this.a = true;
    }
    
    public void b(final byte[] array) {
        this.a(array, 0, array.length);
    }
    
    public synchronized void a(final byte[] array, final int n, int i) {
        int n2 = n;
        if (this.a) {
            this.b.a(this.f, 0);
            this.d = 0;
            this.a = false;
        }
        while (i > this.d) {
            if (this.d > 0) {
                System.arraycopy(this.f, 2 * this.c - this.d, array, n2, this.d);
                i -= this.d;
                n2 += this.d;
                this.d = 0;
            }
            this.b.f();
            this.b.a(this.f, 0, this.c);
            this.b.a(this.f, this.c);
            this.c();
            this.d = this.c;
        }
        System.arraycopy(this.f, 2 * this.c - this.d, array, n2, i);
        this.d -= i;
    }
    
    private void c() {
        if (this.e != 0) {
            for (int i = this.c - 1; i >= 0; --i) {
                final byte[] f = this.f;
                final int n = i;
                ++f[n];
                if (this.f[i] != 1) {
                    break;
                }
            }
        }
        else {
            int n2 = 0;
            for (int j = this.c - 1; j >= 0; --j) {
                n2 = (this.f[j] & 0xFF) + (this.g[j] & 0xFF) + ((n2 & 0x100) >>> 8);
                this.f[j] = (byte)(n2 & 0xFF);
            }
        }
    }
    
    public void b() {
        if (this.b != null) {
            this.b.g();
        }
        this.a = false;
        this.e = 2;
        this.d = 0;
    }
    
    public void finalize() {
        this.b();
    }
    
    static {
        h = new byte[] { -44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 127 };
        i = new byte[] { -38, 57, -93, -18, 94, 107, 75, 13, 50, 85, -65, -17, -107, 96, 24, -112, -81, -40, 7, 9 };
    }
}
