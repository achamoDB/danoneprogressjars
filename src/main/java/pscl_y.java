// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_y extends pscl_q
{
    private int[] a;
    private int b;
    private int c;
    
    public pscl_y(final int n, final boolean b, final int n2) {
        super(n, b, n2, 12544);
        super.f |= 0x4000000;
        super.o |= 0x20000;
    }
    
    public int a(final pscl_q[] array, final int n) {
        super.n = 0;
        if (super.g) {
            final int a = pscl_v.a(array, n);
            for (int a2 = n + 1; array[a2].c != -1; ++a2) {
                super.n += array[a2].a(array, a2);
                if (array[a2].i) {
                    a2 = pscl_v.a(array, a2);
                }
            }
            this.a(array, n, a);
        }
        return super.a(array, n);
    }
    
    public int a(final pscl_q[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        this.a = new int[n3];
        for (int i = 0; i < n3; ++i) {
            this.a[i] = -1;
        }
        for (int a = n + 1; array[a].c != -1; ++a) {
            this.c(array, a);
            if (array[a].i) {
                a = pscl_v.a(array, a);
            }
        }
        return n2;
    }
    
    private void c(final pscl_q[] array, final int n) {
        int n2 = array[n].c;
        if (array[n].a != -1) {
            n2 = array[n].a;
        }
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i] == -1) {
                this.a[i] = n;
                break;
            }
            int n3 = array[this.a[i]].c;
            if (array[this.a[i]].a != -1) {
                n3 = array[this.a[i]].a;
            }
            if (n2 < n3) {
                this.a(n, i);
                break;
            }
        }
    }
    
    private void a(final int n, int n2) {
        int n3 = this.a[n2];
        this.a[n2] = n;
        ++n2;
        while (this.a[n2] != -1) {
            final int n4 = this.a[n2];
            this.a[n2] = n3;
            n3 = n4;
            ++n2;
        }
        this.a[n2] = n3;
    }
    
    public void b(final pscl_q[] array, int a) {
        this.a();
        super.e = -1;
        ++a;
        while (array[a].c != -1) {
            array[a].b(array, a);
            if (array[a].i) {
                a = pscl_v.a(array, a);
            }
            ++a;
        }
        super.o |= 0x10000;
    }
    
    public int a(final pscl_q[] array, int n, final byte[] array2, int n2) throws pscl_x {
        final int n3 = n2;
        n2 += super.a(array, n, array2, n2);
        if (!super.g) {
            return n2 - n3;
        }
        for (int n4 = 0; n4 < this.a.length && this.a[n4] != -1; ++n4) {
            n = this.a[n4];
            if (n != -1) {
                n2 += array[n].a(array, n, array2, n2);
            }
        }
        return n2 - n3;
    }
    
    public int b(final pscl_q[] array, int a, final byte[] array2, int n) throws pscl_x {
        final int n2 = n;
        ++a;
        while (array[a].c != -1) {
            if ((array[a].o & 0x2000000) == 0x0) {
                n += array[a].c(array, a, array2, n);
                if ((array[a].o & 0x2000000) == 0x0) {
                    break;
                }
                if (array[a].i) {
                    a = pscl_v.a(array, a);
                }
            }
            ++a;
        }
        if (array[a].c == -1) {
            super.o = 262144;
        }
        return n - n2;
    }
    
    public void b() {
        super.b();
        this.a = null;
        this.b = -1;
        this.c = 0;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, int n2, int n3, final boolean b) throws pscl_x {
        final int n4 = n2;
        if (this.a == null) {
            this.d(pscl_v.a, n);
        }
        for (int n5 = 1, i = this.a[n5]; i > 0; i = this.a[n5]) {
            pscl_v.a[i].l = null;
            pscl_v.a[i].m = 0;
            pscl_v.a[i].n = 0;
            if (++n5 > this.a.length - 2) {
                break;
            }
        }
        while (this.c > 0) {
            if (super.e == 0) {
                return this.a(pscl_v, n, array, n2, n4, n3);
            }
            if (n3 < 1) {
                super.o |= 0x1000000;
                return n2 - n4;
            }
            if (super.e == -1 && array[n2] == 0) {
                this.a(pscl_v, n, array, n2, n4, n3);
                this.c = 0;
                break;
            }
            if (this.b == -1) {
                this.b = this.a(array[n2] & 0xFF, pscl_v.a);
                if (this.a[this.b - this.a[0]] != 0) {
                    throw new pscl_x("Unexpected tag in SET.");
                }
                this.a[this.b - this.a[0]] = this.b;
            }
            final int a = pscl_v.a[this.b].a(pscl_v, this.b, array, n2, n2 + n3);
            n2 += a;
            n3 -= a;
            if (super.e > 0) {
                super.e -= a;
            }
            if ((pscl_v.a[this.b].o & 0x2000000) == 0x0) {
                super.o |= 0x1000000;
                return n2 - n4;
            }
            --this.c;
            this.b = -1;
        }
        n2 += this.a(pscl_v, array, n2, n3);
        if ((super.o & 0x1000000) != 0x0 || (super.o & 0x2000000) != 0x0) {
            return n2 - n4;
        }
        throw new pscl_x("Improper ending to indefinite length.");
    }
    
    private int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3, final int n4) throws pscl_x {
        if (pscl_v.a(n) != this.a[this.a.length - 1]) {
            throw new pscl_x("Invalid encoding: expected tag or data not there.");
        }
        if (super.e == -1) {
            super.o = 2;
        }
        return n2 - n3;
    }
    
    private void d(final pscl_q[] array, int i) {
        final int a = pscl_v.a(array, i);
        (this.a = new int[a - i + 1])[0] = i;
        this.a[this.a.length - 1] = a;
        ++i;
        while (i < a) {
            if (array[i].i) {
                i = pscl_v.a(array, i);
            }
            ++i;
            ++this.c;
        }
    }
    
    private int a(final int n, final pscl_q[] array) throws pscl_x {
        for (int i = this.a[0] + 1; i < this.a[this.a.length - 1]; ++i) {
            if (array[i].a(n, array, i)) {
                return i;
            }
            if (array[i].i) {
                i = pscl_v.a(array, i);
            }
        }
        throw new pscl_x("Unexpected tag in SET.");
    }
    
    public void a(final pscl_v pscl_v, int i) throws pscl_x {
        super.a(pscl_v, i);
        final int a = pscl_v.a(pscl_v.a, i);
        ++i;
        while (i < a) {
            pscl_v.b(i);
            ++i;
        }
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_y;
    }
    
    public pscl_q g() {
        return new pscl_y(super.f, true, super.h);
    }
}
