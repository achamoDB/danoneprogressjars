// 
// Decompiled by Procyon v0.5.36
// 

public class psc_z extends psc_i
{
    private int a;
    
    public psc_z(final int n) {
        this(n, 0);
    }
    
    public psc_z(final int l, final int n) {
        super(l, true, n, 16128);
        super.n = true;
        super.q |= 0x20000;
        super.l = l;
        final int n2 = super.l & 0xE00000;
        if (n2 == 0) {
            return;
        }
        super.l |= 0x200000;
        super.e = ((n2 | 0x200000) >>> 16 | (super.l & 0xFF));
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        super.a = true;
        super.d = 0;
        final int a = psc_n.a(array, n);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        this.a = -1;
        for (int i = n + 1; i < a; ++i) {
            final psc_i psc_i = array[i];
            psc_i.l |= 0x40000;
            if (this.a == -1) {
                super.d = array[i].a(array, i);
                if (array[i].a) {
                    this.a = i;
                }
            }
            else {
                array[i].a = false;
            }
            if (array[i].n) {
                i = psc_n.a(array, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
        if (this.a == -1) {
            super.a = false;
            return this.a(false, null, 0);
        }
        if (super.e != -1) {
            return psc_o.a(super.e) + super.d + psc_o.b(super.d);
        }
        return super.d;
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        if (!super.a) {
            return this.a(true, array2, n2);
        }
        int n3 = 0;
        if (super.e != -1) {
            final int a = psc_o.a(array2, n2, super.e);
            n3 = a + psc_o.b(array2, n2 + a, super.d);
            n2 += n3;
        }
        super.q = 33554432;
        return n3 + array[this.a].a(array, this.a, array2, n2);
    }
    
    void b(final psc_i[] array, final int n) throws psc_m {
        super.a = true;
        final int a = psc_n.a(array, n);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        super.q = 196608;
        this.a = -1;
        for (int i = n + 1; i < a; ++i) {
            final psc_i psc_i = array[i];
            psc_i.l |= 0x40000;
            if (this.a == -1) {
                if (array[i].a) {
                    array[i].b(array, i);
                    this.a = i;
                }
            }
            else {
                array[i].a = false;
                array[i].q = 33554432;
            }
            if (array[i].n) {
                i = psc_n.a(array, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
        super.q |= 0x10000;
        if (this.a != -1) {
            return;
        }
        super.a = false;
    }
    
    int b(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        if ((super.q & 0x2000000) != 0x0) {
            return 0;
        }
        if (!super.a) {
            super.q = 33554432;
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (super.e != -1 && (super.q & 0x10000) != 0x0) {
            array2[n2] = (byte)super.e;
            array2[n2 + 1] = -128;
            n2 += 2;
        }
        if ((super.q & 0x10000) != 0x0) {
            super.q ^= 0x10000;
        }
        n2 += array[this.a].b(array, this.a, array2, n2);
        if (array[this.a].f()) {
            super.q = 33554432;
            if (super.e != -1) {
                array2[n2 + 1] = (array2[n2] = 0);
                n2 += 2;
            }
        }
        return n2 - n3;
    }
    
    void d() {
        this.a();
        super.q = 0;
        super.q = 1048576;
        if (super.e != -1) {
            super.q |= 0x200000;
        }
        super.a = false;
    }
    
    protected int b(final psc_n psc_n, final int n, final byte[] array, int n2, int n3) throws psc_m {
        final int n4 = n2;
        final int b = this.b(psc_n, array, n2, n3);
        if ((super.q & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        if ((super.q & 0x200000) != 0x0) {
            if ((psc_n.c[0] & 0xFF) != super.e) {
                this.b(psc_n, n);
                return n2 - n4;
            }
            super.q ^= 0x200000;
            super.f = psc_o.b(psc_n.c, 1);
            if (super.f == -1) {
                super.q += 2;
            }
            psc_n.e = 0;
            final int b2 = this.b(psc_n, array, n2, n3);
            n2 += b2;
            n3 -= b2;
            if ((super.q & 0x1000000) != 0x0) {
                return n2 - n4;
            }
        }
        final int a = psc_n.a(psc_n.a, n);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        this.a = n + 1;
        final int n5 = psc_n.c[0] & 0xFF;
        while (this.a < a) {
            if (psc_n.a[this.a].a(n5, psc_n.a, this.a)) {
                int i = this.a + 1;
                super.a = true;
                if (psc_n.a[this.a].n) {
                    i = psc_n.a(psc_n.a, this.a) + 1;
                    if (i == 0) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
                while (i < a) {
                    psc_n.b(i);
                    if (psc_n.a[i].n) {
                        i = psc_n.a(psc_n.a, i);
                        if (i == -1) {
                            throw new psc_m("EndContainer is missing.");
                        }
                    }
                    ++i;
                }
                super.q ^= 0x100000;
                return n2 - n4;
            }
            psc_n.b(this.a);
            if (psc_n.a[this.a].n) {
                this.a = psc_n.a(psc_n.a, this.a);
                if (this.a == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++this.a;
        }
        this.b(psc_n, n);
        return n2 - n4;
    }
    
    protected void b(final psc_n psc_n, final int n) throws psc_m {
        super.b(psc_n, n);
        final int a = psc_n.a(psc_n.a, n);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        for (int i = n + 1; i < a; ++i) {
            psc_n.b(i);
            if (psc_n.a[i].n) {
                this.a = psc_n.a(psc_n.a, i);
                if (this.a == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] array, final int n2) throws psc_m {
        final int a = psc_n.a[this.a].a(psc_n, this.a, array, n2, n2 + super.ac);
        if (psc_n.a[this.a].f()) {
            super.q = 33554432;
        }
        return a;
    }
    
    boolean a(final int n, final psc_i[] array, final int n2) throws psc_m {
        if ((super.q & 0x2000000) != 0x0) {
            return false;
        }
        final int a = psc_n.a(array, n2);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        for (int i = n2 + 1; i < a; ++i) {
            if (array[i].a(n, array, i)) {
                return true;
            }
            if (array[i].n) {
                i = psc_n.a(array, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
        return false;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_z;
    }
    
    protected psc_i b() {
        return new psc_z(super.l, super.m);
    }
}
