// 
// Decompiled by Procyon v0.5.36
// 

public class psc_n
{
    psc_i[] a;
    int b;
    byte[] c;
    int d;
    int e;
    private boolean f;
    
    public psc_n(psc_i[] a) {
        if (a == null) {
            a = new psc_i[0];
        }
        this.b = a.length;
        this.a = a;
    }
    
    public int a() throws psc_m {
        this.f = false;
        int i = 0;
        int n = 0;
        while (i < this.b) {
            if (this.a[i] == null) {
                throw new psc_m("container #" + i + " is null");
            }
            n += this.a[i].a(this.a, i);
            if (this.a[i].n) {
                i = a(this.a, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++i;
        }
        return n;
    }
    
    public int a(byte[] array, int n) throws psc_m {
        if (array == null) {
            array = new byte[0];
        }
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            n += this.a[i].a(this.a, i, array, n);
            if (this.a[i].n) {
                i = a(this.a, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++i;
        }
        this.f = true;
        return n - n2;
    }
    
    public void b() throws psc_m {
        this.f = false;
        for (int i = 0; i < this.b; ++i) {
            if (this.a[i] == null) {
                throw new psc_m("container is null");
            }
            this.a[i].b(this.a, i);
            if (this.a[i].n) {
                i = a(this.a, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
    }
    
    public int b(final byte[] array, int n) throws psc_m {
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            if ((this.a[i].q & 0x2000000) == 0x0) {
                n += this.a[i].b(this.a, i, array, n);
                if ((this.a[i].q & 0x2000000) == 0x0) {
                    break;
                }
                if (this.a[i].n) {
                    i = a(this.a, i);
                    if (i == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
            }
            ++i;
        }
        if (i >= this.b) {
            this.f = true;
        }
        return n - n2;
    }
    
    public int c(final byte[] array, int n) throws psc_m {
        int i = 0;
        final int n2 = n;
        while (i < this.b) {
            if ((this.a[i].q & 0x2000000) == 0x0) {
                final psc_i psc_i = this.a[i];
                psc_i.q |= 0x40000;
            }
            ++i;
        }
        int j;
        for (j = 0; j < this.b; ++j) {
            if ((this.a[j].q & 0x2000000) == 0x0) {
                n += this.a[j].b(this.a, j, array, n);
                if (this.a[j].n) {
                    j = a(this.a, j);
                    if (j == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
            }
        }
        if (j >= this.b) {
            this.f = true;
        }
        return n - n2;
    }
    
    public void c() {
        this.f = false;
        for (int i = 0; i < this.b; ++i) {
            this.a[i].d();
        }
        this.c = new byte[10];
        this.e = 0;
    }
    
    public int a(final byte[] array, int n, int n2) throws psc_m {
        if (array == null || n < 0 || array.length < n + n2) {
            throw new psc_m("The encoding is null.");
        }
        int i = 0;
        final int n3 = n;
        while (i < this.b) {
            if ((this.a[i].q & 0x2000000) != 0x0) {
                i = this.b(i);
                --i;
            }
            else {
                final int a = this.a[i].a(this, i, array, n, n2 + n);
                n += a;
                n2 -= a;
                if ((this.a[i].q & 0x2000000) == 0x0) {
                    break;
                }
                if (this.a[i].n) {
                    i = a(this.a, i);
                    if (i == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
            }
            ++i;
        }
        if (i >= this.b) {
            this.f = true;
        }
        return n - n3;
    }
    
    public void d() throws psc_m {
        if (this.f) {
            return;
        }
        int i = 0;
        this.e = 0;
        while (i < this.b) {
            i = this.a(i);
            if (this.a[i].n) {
                i = a(this.a, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++i;
        }
        this.f = true;
    }
    
    int a(final int n) throws psc_m {
        int a = n;
        if (this.a[n].n) {
            a = a(this.a, n);
            if (a == -1) {
                throw new psc_m("EndContainer is missing.");
            }
        }
        else if (this.a[n].f()) {
            return a;
        }
        if (n == a) {
            this.a[n].b(this, n);
            return a;
        }
        for (int i = n + 1; i < a; i = this.a(i), ++i) {}
        this.a[n].q = 33554432;
        return a;
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int[] array2) throws psc_m {
        int a = n;
        if (this.a[n].n) {
            a = a(this.a, n);
            if (a == -1) {
                throw new psc_m("EndContainer is missing.");
            }
        }
        else if (this.a[n].f()) {
            return a;
        }
        if (n == a) {
            if (this.a[n].l == 67108864) {
                final int a2 = this.a[n].a(psc_n, array, array2[0], array2[2]);
                final int n2 = 0;
                array2[n2] += a2;
                final int n3 = 2;
                array2[n3] -= a2;
            }
            else {
                this.a[n].b(this, n);
            }
            return a;
        }
        for (int i = n + 1; i < a; i = this.a(psc_n, i, array, array2), ++i) {}
        this.a[n].q = 33554432;
        return a;
    }
    
    public boolean e() {
        return this.f;
    }
    
    static int a(final psc_i[] array, final int n) {
        int i = 1;
        int n2 = n;
        while (i > 0) {
            if (++n2 >= array.length) {
                break;
            }
            if (array[n2] == null) {
                break;
            }
            if (array[n2].n) {
                ++i;
            }
            if (array[n2].g != -1) {
                continue;
            }
            --i;
        }
        if (i > 0) {
            return -1;
        }
        return n2;
    }
    
    int b(int b) {
        this.a[b].a();
        this.a[b].a = false;
        this.a[b].q = 33554432;
        if (this.a[b].n) {
            ++b;
            while (this.a[b].g != -1) {
                b = this.b(b);
            }
        }
        return ++b;
    }
}
