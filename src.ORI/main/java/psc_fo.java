// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fo extends psc_i
{
    private int[] a;
    private int b;
    private int c;
    
    public psc_fo(final int n) {
        this(n, true, 0);
    }
    
    public psc_fo(final int n, final boolean b, final int n2) {
        super(n, b, n2, 12544);
        super.l |= 0x4000000;
        super.q |= 0x20000;
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        super.d = 0;
        if (super.a) {
            final int a = psc_n.a(array, n);
            if (a == -1) {
                throw new psc_m("EndContainer is missing.");
            }
            int a2 = n + 1;
            while (array[a2].g != -1) {
                super.d += array[a2].a(array, a2);
                if (array[a2].n) {
                    a2 = psc_n.a(array, a2);
                    if (a2 == -1) {
                        throw new psc_m("EndContainer is missing.");
                    }
                }
                ++a2;
                if (array.length == a2) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            this.a(array, n, a);
        }
        return super.a(array, n);
    }
    
    int a(final psc_i[] array, final int n, final int n2) throws psc_m {
        final int n3 = n2 - n;
        this.a = new int[n3];
        for (int i = 0; i < n3; ++i) {
            this.a[i] = -1;
        }
        int a = n + 1;
        while (array[a].g != -1) {
            this.c(array, a);
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
        return n2;
    }
    
    private void c(final psc_i[] array, final int n) {
        int n2 = array[n].g;
        if (array[n].e != -1) {
            n2 = array[n].e;
        }
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i] == -1) {
                this.a[i] = n;
                break;
            }
            int n3 = array[this.a[i]].g;
            if (array[this.a[i]].e != -1) {
                n3 = array[this.a[i]].e;
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
    
    int a(final psc_i[] array, int n, final byte[] array2, int n2) throws psc_m {
        final int n3 = n2;
        n2 += super.a(array, n, array2, n2);
        if (!super.a) {
            return n2 - n3;
        }
        int n4 = 0;
        while (n4 < this.a.length && this.a[n4] != -1) {
            n = this.a[n4];
            if (n != -1) {
                n2 += array[n].a(array, n, array2, n2);
            }
            ++n4;
            if (array.length == n4) {
                throw new psc_m("EndContainer is missing.");
            }
        }
        return n2 - n3;
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
    
    void d() {
        super.d();
        this.a = null;
        this.b = -1;
        this.c = 0;
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] array, int n2) throws psc_m {
        final int n3 = n2;
        if (this.a == null) {
            this.d(psc_n.a, n);
        }
        while (this.c > 0) {
            if (super.k == 0) {
                return this.a(psc_n, n, array, n2, n3, super.ac);
            }
            if (super.ac < 1) {
                super.q |= 0x1000000;
                return n2 - n3;
            }
            if (super.k == -1 && array[n2] == 0) {
                n2 += this.a(psc_n, n, array, n2, n3, super.ac);
                this.c = 0;
                break;
            }
            if (this.b == -1) {
                this.b = this.a(array[n2] & 0xFF, psc_n.a);
                if (this.a[this.b - this.a[0]] != 0) {
                    throw new psc_m("Unexpected tag in SET.");
                }
                this.a[this.b - this.a[0]] = this.b;
            }
            final int a = psc_n.a[this.b].a(psc_n, this.b, array, n2, n2 + super.ac);
            n2 += a;
            super.ac -= a;
            if (super.k > 0) {
                super.k -= a;
            }
            if ((psc_n.a[this.b].q & 0x2000000) == 0x0) {
                super.q |= 0x1000000;
                return n2 - n3;
            }
            --this.c;
            this.b = -1;
        }
        n2 += this.a(psc_n, array, n2, super.ac);
        if (super.ac == 0) {
            super.q |= 0x1000000;
        }
        if ((super.q & 0x1000000) != 0x0 || (super.q & 0x2000000) != 0x0) {
            return n2 - n3;
        }
        throw new psc_m("Improper ending to indefinite length.");
    }
    
    private int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3, final int n4) throws psc_m {
        final int[] array2 = { n2, n3, n4 };
        if (psc_n.a(psc_n, n, array, array2) != this.a[this.a.length - 1]) {
            throw new psc_m("Invalid encoding: expected tag or data not there.");
        }
        if (super.k == -1) {
            super.q = 2;
        }
        return array2[0] - array2[1];
    }
    
    private void d(final psc_i[] array, int i) throws psc_m {
        final int a = psc_n.a(array, i);
        if (a == -1) {
            throw new psc_m("EndContainer is missing.");
        }
        (this.a = new int[a - i + 1])[0] = i;
        this.a[this.a.length - 1] = a;
        ++i;
        while (i < a) {
            if (array[i].n) {
                i = psc_n.a(array, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
            ++i;
            ++this.c;
        }
    }
    
    private int a(final int n, final psc_i[] array) throws psc_m {
        for (int i = this.a[0] + 1; i < this.a[this.a.length - 1]; ++i) {
            if (array[i].a(n, array, i)) {
                return i;
            }
            if (array[i].n) {
                i = psc_n.a(array, i);
                if (i == -1) {
                    throw new psc_m("EndContainer is missing.");
                }
            }
        }
        throw new psc_m("Unexpected tag in SET.");
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
        return psc_i instanceof psc_fo;
    }
    
    protected psc_i b() {
        return new psc_fo(super.l, true, super.m);
    }
}
