// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_v
{
    public pscl_q[] a;
    public int b;
    public byte[] c;
    public int d;
    private boolean e;
    
    public pscl_v(final pscl_q[] a) {
        this.b = a.length;
        this.a = a;
    }
    
    public int a() {
        this.e = false;
        int i = 0;
        int n = 0;
        while (i < this.b) {
            n += this.a[i].a(this.a, i);
            if (this.a[i].i) {
                i = a(this.a, i);
            }
            ++i;
        }
        return n;
    }
    
    public int a(final byte[] array, int n) throws pscl_x {
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            n += this.a[i].a(this.a, i, array, n);
            if (this.a[i].i) {
                i = a(this.a, i);
            }
            ++i;
        }
        this.e = true;
        return n - n2;
    }
    
    public void b() {
        this.e = false;
        for (int i = 0; i < this.b; ++i) {
            this.a[i].b(this.a, i);
            if (this.a[i].i) {
                i = a(this.a, i);
            }
        }
    }
    
    public int b(final byte[] array, int n) throws pscl_x {
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            if ((this.a[i].o & 0x2000000) == 0x0) {
                n += this.a[i].c(this.a, i, array, n);
                if ((this.a[i].o & 0x2000000) == 0x0) {
                    break;
                }
                if (this.a[i].i) {
                    i = a(this.a, i);
                }
            }
            ++i;
        }
        if (i >= this.b) {
            this.e = true;
        }
        return n - n2;
    }
    
    public int c(final byte[] array, int n) throws pscl_x {
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            if ((this.a[i].o & 0x2000000) == 0x0) {
                final pscl_q pscl_q = this.a[i];
                pscl_q.o |= 0x40000;
            }
            ++i;
        }
        int j;
        for (j = 0; j < this.b; ++j) {
            if ((this.a[j].o & 0x2000000) == 0x0) {
                n += this.a[j].c(this.a, j, array, n);
                if (this.a[j].i) {
                    j = a(this.a, j);
                }
            }
        }
        if (j >= this.b) {
            this.e = true;
        }
        return n - n2;
    }
    
    public void c() {
        this.e = false;
        for (int i = 0; i < this.b; ++i) {
            this.a[i].b();
        }
        this.c = new byte[6];
        this.d = 0;
    }
    
    public int a(final byte[] array, int n, int n2) throws pscl_x {
        int i = 0;
        final int n3 = n;
        while (i < this.b) {
            if ((this.a[i].o & 0x2000000) != 0x0) {
                i = this.b(i);
                --i;
            }
            else {
                final int a = this.a[i].a(this, i, array, n, n2 + n);
                n += a;
                n2 -= a;
                if ((this.a[i].o & 0x2000000) == 0x0) {
                    break;
                }
                if (this.a[i].i) {
                    i = a(this.a, i);
                }
            }
            ++i;
        }
        if (i >= this.b) {
            this.e = true;
        }
        return n - n3;
    }
    
    public void d() throws pscl_x {
        if (this.e) {
            return;
        }
        int i = 0;
        this.d = 0;
        while (i < this.b) {
            i = this.a(i);
            if (this.a[i].i) {
                i = a(this.a, i);
            }
            ++i;
        }
        this.e = true;
    }
    
    public int a(final int n) throws pscl_x {
        int a = n;
        if (this.a[n].i) {
            a = a(this.a, n);
        }
        else if (this.a[n].h()) {
            return a;
        }
        if (n == a) {
            this.a[n].a(this, n);
            return a;
        }
        for (int i = n + 1; i < a; i = this.a(i), ++i) {}
        this.a[n].o = 33554432;
        return a;
    }
    
    public boolean e() {
        return this.e;
    }
    
    public static int a(final pscl_q[] array, final int n) {
        int n2;
        int n3;
        for (n2 = 1, n3 = n; n2 > 0 && n3 < array.length; --n2) {
            ++n3;
            if (array[n3].i) {
                ++n2;
            }
            if (array[n3].c == -1) {}
        }
        return n3;
    }
    
    public int b(int b) {
        this.a[b].c();
        this.a[b].g = false;
        this.a[b].o = 33554432;
        if (this.a[b].i) {
            ++b;
            while (this.a[b].c != -1) {
                b = this.b(b);
            }
        }
        return ++b;
    }
}
