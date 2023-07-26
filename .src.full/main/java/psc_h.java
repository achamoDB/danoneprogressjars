// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h extends psc_i
{
    public psc_h(final int n) {
        this(n, true, 0);
    }
    
    public psc_h(final int n, final boolean b, final int n2) {
        super(n, b, n2, 12288);
        super.l |= 0x4000000;
        super.q |= 0x20000;
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        super.d = 0;
        if (super.a) {
            int a = n + 1;
            if (array.length == a) {
                throw new psc_m("EndContainer is missing.");
            }
            if (array[a] == null) {
                throw new psc_m("container #" + a + " is null");
            }
            while (array[a].g != -1) {
                super.d += array[a].a(array, a);
                if (array[a].n) {
                    a = psc_n.a(array, a);
                    if (a == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
                ++a;
                if (array.length == a) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
        return super.a(array, n);
    }
    
    void b(final psc_i[] array, int a) throws psc_m {
        this.c();
        super.k = -1;
        ++a;
        while (array[a].g != -1) {
            array[a].b(array, a);
            if (array[a].n) {
                a = psc_n.a(array, a);
                if (a == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++a;
            if (array.length == a) {
                throw new psc_m("EndContainer is missing.");
            }
        }
        super.q |= 0x10000;
    }
    
    int a(final psc_i[] array, int a, final byte[] array2, int n) throws psc_m {
        final int n2 = n;
        n += super.a(array, a, array2, n);
        if (!super.a) {
            return n - n2;
        }
        ++a;
        while (array[a].g != -1) {
            n += array[a].a(array, a, array2, n);
            if (array[a].n) {
                a = psc_n.a(array, a);
                if (a == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++a;
            if (array.length == a) {
                throw new psc_m("EndContainer is missing.");
            }
        }
        return n - n2;
    }
    
    protected int c(final psc_i[] array, int a, final byte[] array2, int n) throws psc_m {
        final int n2 = n;
        ++a;
        while (array[a].g != -1) {
            if ((array[a].q & 0x2000000) == 0x0) {
                n += array[a].b(array, a, array2, n);
                if ((array[a].q & 0x2000000) == 0x0) {
                    break;
                }
                if (array[a].n) {
                    a = psc_n.a(array, a);
                    if (a == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
                if (array.length == a + 1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++a;
        }
        if (array[a].g == -1) {
            super.q = 262144;
        }
        return n - n2;
    }
    
    protected int a(final psc_n psc_n, int n, final byte[] array, int n2) throws psc_m {
        final int n3 = n;
        final int n4 = n2;
        ++n;
        while (psc_n.a[n].g != -1 && ((super.ac > 0 && super.k != 0) || psc_n.e != 0)) {
            int a = n;
            if (psc_n.a[a].n) {
                a = psc_n.a(psc_n.a, a);
                if (a == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++a;
            if ((psc_n.a[n].q & 0x2000000) != 0x0) {
                n = a;
            }
            else {
                final int a2 = psc_n.a[n].a(psc_n, n, array, n2, n2 + super.ac);
                n2 += a2;
                super.ac -= a2;
                if (super.k >= 0) {
                    super.k -= a2;
                    if (super.k < 0) {
                        throw new psc_m("Invalid encoding: SEQUENCE and data length do not match.");
                    }
                }
                if ((psc_n.a[n].q & 0x2000000) == 0x0) {
                    super.q |= 0x1000000;
                    return n2 - n4;
                }
                n = a;
                if (psc_n.a.length == n) {
                    throw new psc_m("EndContainer is missing.");
                }
                continue;
            }
        }
        if (psc_n.a[n].g == -1) {
            if (super.k == 0) {
                super.q = 33554432;
                return n2 - n4;
            }
            if (super.k > 0) {
                throw new psc_m("Invalid encoding: not enough data or containers.");
            }
            if (super.ac == 0) {
                super.q |= 0x1000000;
            }
            n2 += this.a(psc_n, array, n2, super.ac);
            if ((super.q & 0x1000000) != 0x0) {
                return n2 - n4;
            }
            if ((super.q & 0x2000000) != 0x0) {
                return n2 - n4;
            }
            throw new psc_m("Invalid encoding: not enough data or containers.");
        }
        else {
            if (super.k != 0) {
                super.q |= 0x1000000;
                return n2 - n4;
            }
            final int a3 = psc_n.a(psc_n.a, n3);
            if (a3 == -1) {
                throw new psc_m("EndContainer is missing.");
            }
            for (int i = n3 + 1; i <= a3; ++i) {
                if (!psc_n.a[i].f()) {
                    if (psc_n.a[i].g != -1) {
                        psc_n.a[i].b(psc_n, i);
                    }
                }
            }
            super.q = 33554432;
            return n2 - n4;
        }
    }
    
    protected void b(final psc_n psc_n, int i) throws psc_m {
        super.b(psc_n, i);
        final int a = psc_n.a(psc_n.a, i);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        ++i;
        while (i < a) {
            psc_n.b(i);
            ++i;
        }
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_h;
    }
    
    protected psc_i b() {
        return new psc_h(super.l, true, super.m);
    }
}
