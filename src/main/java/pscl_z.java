// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_z extends pscl_q
{
    public pscl_z(final int n, final boolean b, final int n2) {
        super(n, b, n2, 12288);
        super.f |= 0x4000000;
        super.o |= 0x20000;
    }
    
    public int a(final pscl_q[] array, final int n) {
        super.n = 0;
        if (super.g) {
            for (int a = n + 1; array[a].c != -1; ++a) {
                super.n += array[a].a(array, a);
                if (array[a].i) {
                    a = pscl_v.a(array, a);
                }
            }
        }
        return super.a(array, n);
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
    
    public int a(final pscl_q[] array, int a, final byte[] array2, int n) throws pscl_x {
        final int n2 = n;
        n += super.a(array, a, array2, n);
        if (!super.g) {
            return n - n2;
        }
        ++a;
        while (array[a].c != -1) {
            n += array[a].a(array, a, array2, n);
            if (array[a].i) {
                a = pscl_v.a(array, a);
            }
            ++a;
        }
        return n - n2;
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
    
    public int a(final pscl_v pscl_v, int n, final byte[] array, int n2, int n3, final boolean b) throws pscl_x {
        final int n4 = n;
        final int n5 = n2;
        ++n;
        while (pscl_v.a[n].c != -1 && n3 > 0 && super.e != 0) {
            int a = n;
            if (pscl_v.a[a].i) {
                a = pscl_v.a(pscl_v.a, a);
            }
            ++a;
            if ((pscl_v.a[n].o & 0x2000000) != 0x0) {
                n = a;
            }
            else {
                final int a2 = pscl_v.a[n].a(pscl_v, n, array, n2, n2 + n3);
                n2 += a2;
                n3 -= a2;
                if (super.e >= 0) {
                    super.e -= a2;
                    if (super.e < 0) {
                        throw new pscl_x("Invalid encoding: SEQUENCE and data length do not match.");
                    }
                }
                if ((pscl_v.a[n].o & 0x2000000) == 0x0) {
                    super.o |= 0x1000000;
                    return n2 - n5;
                }
                n = a;
            }
        }
        if (pscl_v.a[n].c == -1) {
            if (super.e == 0) {
                super.o = 33554432;
                return n2 - n5;
            }
            if (super.e > 0) {
                throw new pscl_x("Invalid encoding: not enough data or containers.");
            }
            n2 += this.a(pscl_v, array, n2, n3);
            if ((super.o & 0x1000000) != 0x0) {
                return n2 - n5;
            }
            if ((super.o & 0x2000000) != 0x0) {
                return n2 - n5;
            }
            throw new pscl_x("Invalid encoding: not enough data or containers.");
        }
        else {
            if (n3 < 1) {
                super.o |= 0x1000000;
                return n2 - n5;
            }
            if (pscl_v.a(n4) != pscl_v.a(pscl_v.a, n4)) {
                throw new pscl_x("Invalid encoding: expected tag or data not there.");
            }
            super.o = 33554432;
            return n2 - n5;
        }
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
        return pscl_q instanceof pscl_z;
    }
    
    public pscl_q g() {
        return new pscl_z(super.f, true, super.h);
    }
}
