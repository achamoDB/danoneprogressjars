// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_aj extends pscl_q
{
    private int a;
    
    public pscl_aj(final int f, final int n) {
        super(f, true, n, 16128);
        super.i = true;
        super.o |= 0x20000;
        super.f = f;
        final int n2 = super.f & 0xE00000;
        if (n2 == 0) {
            return;
        }
        super.f |= 0x200000;
        super.a = ((n2 | 0x200000) >>> 16 | (super.f & 0xFF));
    }
    
    public int a(final pscl_q[] array, final int n) {
        super.g = true;
        super.n = 0;
        final int a = pscl_v.a(array, n);
        this.a = -1;
        for (int i = n + 1; i < a; ++i) {
            final pscl_q pscl_q = array[i];
            pscl_q.f |= 0x40000;
            if (this.a == -1) {
                super.n = array[i].a(array, i);
                if (array[i].g) {
                    this.a = i;
                }
            }
            else {
                array[i].g = false;
            }
            if (array[i].i) {
                i = pscl_v.a(array, i);
            }
        }
        if (this.a == -1) {
            super.g = false;
            return this.a(false, null, 0);
        }
        if (super.a != -1) {
            return pscl_w.a(super.n) + super.n + 1;
        }
        return super.n;
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        if (!super.g) {
            return this.a(true, array2, n2);
        }
        int n3 = 0;
        if (super.a != -1) {
            array2[n2] = (byte)super.a;
            n3 = 1 + pscl_w.a(array2, n2 + 1, super.n);
            n2 += n3;
        }
        super.o = 33554432;
        return n3 + array[this.a].a(array, this.a, array2, n2);
    }
    
    public void b(final pscl_q[] array, final int n) {
        super.g = true;
        final int a = pscl_v.a(array, n);
        super.o = 196608;
        this.a = -1;
        for (int i = n + 1; i < a; ++i) {
            final pscl_q pscl_q = array[i];
            pscl_q.f |= 0x40000;
            if (this.a == -1) {
                if (array[i].g) {
                    array[i].b(array, i);
                    this.a = i;
                }
            }
            else {
                array[i].g = false;
                array[i].o = 33554432;
            }
            if (array[i].i) {
                i = pscl_v.a(array, i);
            }
        }
        super.o |= 0x10000;
        if (this.a != -1) {
            return;
        }
        super.g = false;
    }
    
    public int c(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        if ((super.o & 0x2000000) != 0x0) {
            return 0;
        }
        if (!super.g) {
            super.o = 33554432;
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (super.a != -1 && (super.o & 0x10000) != 0x0) {
            array2[n2] = (byte)super.a;
            array2[n2 + 1] = -128;
            n2 += 2;
        }
        if ((super.o & 0x10000) != 0x0) {
            super.o ^= 0x10000;
        }
        n2 += array[this.a].c(array, this.a, array2, n2);
        if (array[this.a].h()) {
            super.o = 33554432;
            if (super.a != -1) {
                array2[n2 + 1] = (array2[n2] = 0);
                n2 += 2;
            }
        }
        return n2 - n3;
    }
    
    public void b() {
        this.c();
        super.o = 0;
        super.o = 1048576;
        if (super.a != -1) {
            super.o |= 0x200000;
        }
        super.g = false;
    }
    
    public int b(final pscl_v pscl_v, final int n, final byte[] array, int n2, int n3) throws pscl_x {
        final int n4 = n2;
        final int b = this.b(pscl_v, array, n2, n3);
        if ((super.o & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        if ((super.o & 0x200000) != 0x0) {
            if ((pscl_v.c[0] & 0xFF) != super.a) {
                this.a(pscl_v, n);
                return n2 - n4;
            }
            super.o ^= 0x200000;
            super.b = pscl_w.b(pscl_v.c, 1);
            if (super.b == -1) {
                super.o += 2;
            }
            pscl_v.d = 0;
            final int b2 = this.b(pscl_v, array, n2, n3);
            n2 += b2;
            n3 -= b2;
            if ((super.o & 0x1000000) != 0x0) {
                return n2 - n4;
            }
        }
        final int a = pscl_v.a(pscl_v.a, n);
        this.a = n + 1;
        final int n5 = pscl_v.c[0] & 0xFF;
        while (this.a < a) {
            if (pscl_v.a[this.a].a(n5, pscl_v.a, this.a)) {
                int i = this.a + 1;
                super.g = true;
                if (pscl_v.a[this.a].i) {
                    i = pscl_v.a(pscl_v.a, this.a) + 1;
                }
                while (i < a) {
                    pscl_v.b(i);
                    if (pscl_v.a[i].i) {
                        i = pscl_v.a(pscl_v.a, i);
                    }
                    ++i;
                }
                super.o ^= 0x100000;
                return n2 - n4;
            }
            pscl_v.b(this.a);
            if (pscl_v.a[this.a].i) {
                this.a = pscl_v.a(pscl_v.a, this.a);
            }
            ++this.a;
        }
        this.a(pscl_v, n);
        return n2 - n4;
    }
    
    public void a(final pscl_v pscl_v, final int n) throws pscl_x {
        super.a(pscl_v, n);
        for (int a = pscl_v.a(pscl_v.a, n), i = n + 1; i < a; ++i) {
            pscl_v.b(i);
            if (pscl_v.a[i].i) {
                this.a = pscl_v.a(pscl_v.a, i);
            }
        }
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3, final boolean b) throws pscl_x {
        final int a = pscl_v.a[this.a].a(pscl_v, this.a, array, n2, n2 + n3);
        if (pscl_v.a[this.a].h()) {
            super.o = 33554432;
        }
        return a;
    }
    
    public boolean a(final int n, final pscl_q[] array, final int n2) {
        if ((super.o & 0x2000000) != 0x0) {
            return false;
        }
        for (int a = pscl_v.a(array, n2), i = n2 + 1; i < a; ++i) {
            if (array[i].a(n, array, i)) {
                return true;
            }
            if (array[i].i) {
                i = pscl_v.a(array, i);
            }
        }
        return false;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_aj;
    }
    
    public pscl_q g() {
        return new pscl_aj(super.f, super.h);
    }
}
