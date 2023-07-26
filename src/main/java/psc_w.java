import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_w extends psc_i
{
    private int a;
    private psc_i b;
    private Vector c;
    private int d;
    
    public psc_w(final int n, final int n2, final psc_i psc_i) throws psc_m {
        this(n, true, 0, n2, psc_i);
    }
    
    public psc_w(final int n, final boolean b, final int n2, final int a, final psc_i b2) throws psc_m {
        super(n, b, n2, a);
        if (b && b2 == null) {
            throw new psc_m("OfContainer.OfContainer: internalContainer should not be null if data is present.");
        }
        super.n = false;
        super.l |= 0x4000000;
        this.b = b2;
        this.a = a;
    }
    
    public void b(final psc_i obj) throws psc_m {
        if (super.q == 0 || (super.q & 0x2000000) != 0x0) {
            this.c = new Vector();
            super.q = 131072;
        }
        if ((super.q & 0x20000000) != 0x0) {
            throw new psc_m("Method noMoreData for OfContainer has been called.");
        }
        if (this.b == null) {
            throw new psc_m("OfContainer.addContainer: internalContainer is null.");
        }
        if (this.b.a(obj)) {
            this.c.addElement(obj);
            return;
        }
        throw new psc_m("Improper internal container for OF.");
    }
    
    public void h() {
        super.q |= 0x20000000;
    }
    
    public int j() {
        return this.c.size();
    }
    
    public psc_i a(final int index) throws psc_m {
        if (index < 0 || index >= this.c.size()) {
            throw new psc_m("No container at requested index.");
        }
        return this.c.elementAt(index);
    }
    
    protected void c() {
        super.n = true;
        super.c();
        super.n = false;
        this.d = 0;
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        super.d = 0;
        this.c();
        if (!super.a) {
            return this.a(false, null, 0);
        }
        if (this.c == null) {
            super.d = 0;
        }
        else {
            for (int i = 0; i < this.c.size(); ++i) {
                super.d += ((psc_i)this.c.elementAt(i)).a((psc_i[])null, 0);
            }
        }
        super.k = super.d;
        final int f = psc_o.a(super.g) + super.k + psc_o.b(super.d);
        if (super.e == -1) {
            return f;
        }
        super.f = f;
        return psc_o.a(super.e) + super.f + psc_o.b(f);
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        final int n3 = n2;
        n2 += super.a(array, n, array2, n2);
        if (!super.a || this.c == null) {
            return n2 - n3;
        }
        final int size = this.c.size();
        final int[] array3 = new int[size];
        if (this.a == 12544) {
            for (int i = 1; i < size; ++i) {
                int n4;
                for (n4 = 0; n4 < i && !this.a(i, array3, n4); ++n4) {}
                if (n4 >= i) {
                    array3[i] = i;
                }
            }
        }
        else {
            for (int j = 0; j < size; ++j) {
                array3[j] = j;
            }
        }
        for (int k = 0; k < size; ++k) {
            n2 += ((psc_i)this.c.elementAt(array3[k])).a(array, n, array2, n2);
        }
        return n2 - n3;
    }
    
    private boolean a(final int index, final int[] array, final int n) {
        final psc_i psc_i = this.c.elementAt(index);
        final psc_i psc_i2 = this.c.elementAt(array[n]);
        if (psc_i.d > psc_i2.d) {
            return false;
        }
        if (psc_i.d < psc_i2.d) {
            return this.b(index, array, n);
        }
        int c;
        int c2;
        int i;
        for (c = psc_i.c, c2 = psc_i2.c, i = 0; i < psc_i.d; ++i, ++c, ++c2) {
            if (psc_i.b[c] > psc_i2.b[c2]) {
                return false;
            }
            if (psc_i.b[c] < psc_i2.b[c2]) {
                break;
            }
        }
        return i < psc_i.d && this.b(index, array, n);
    }
    
    private boolean b(final int n, final int[] array, final int n2) {
        for (int i = array.length - 1; i > n2; --i) {
            array[i] = array[i - 1];
        }
        array[n2] = n;
        return true;
    }
    
    protected int c(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        final int n3 = n2;
        while (this.d < this.c.size()) {
            final psc_i psc_i = this.c.elementAt(this.d);
            if ((super.q & 0x10000000) == 0x0) {
                psc_i.b((psc_i[])null, 0);
                super.q |= 0x10000000;
            }
            n2 += psc_i.b(null, 0, array2, n2);
            if (!psc_i.f()) {
                return n2 - n3;
            }
            super.q ^= 0x10000000;
            ++this.d;
        }
        if ((super.q & 0x20000000) != 0x0) {
            super.q |= 0x40000;
        }
        return n2 - n3;
    }
    
    void d() {
        super.d();
        this.d = 0;
        this.c = new Vector();
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] array, int n2) throws psc_m {
        final int n3 = n2;
        final int size = this.c.size();
        final int c = this.c(psc_n, array, n2, super.ac);
        if ((super.q & 0x2000000) != 0x0 || (super.q & 0x1000000) != 0x0) {
            return c;
        }
        if (this.d < size) {
            final psc_i psc_i = this.c.elementAt(this.d);
            final int a = psc_i.a(psc_n, n, array, n2, n2 + super.ac);
            if (super.k > 0) {
                super.k -= a;
            }
            if ((psc_i.q & 0x2000000) == 0x0) {
                super.q |= 0x1000000;
                return a;
            }
            n2 += a;
            super.ac -= a;
            ++this.d;
            n2 += this.c(psc_n, array, n2, super.ac);
            if ((super.q & 0x2000000) != 0x0 || (super.q & 0x1000000) != 0x0) {
                return n2 - n3;
            }
        }
        while (super.ac > 0) {
            final psc_i e = this.e();
            e.d();
            final int a2 = e.a(psc_n, n, array, n2, n2 + super.ac);
            if (super.k > 0) {
                super.k -= a2;
            }
            n2 += a2;
            super.ac -= a2;
            this.b(e);
            if ((e.q & 0x2000000) == 0x0) {
                super.q |= 0x1000000;
                return n2 - n3;
            }
            ++this.d;
            n2 += this.c(psc_n, array, n2, super.ac);
            if ((super.q & 0x2000000) != 0x0 || (super.q & 0x1000000) != 0x0) {
                return n2 - n3;
            }
        }
        super.q |= 0x1000000;
        return n2 - n3;
    }
    
    private int c(final psc_n psc_n, final byte[] array, int n, int n2) throws psc_m {
        if (super.k > 0) {
            return 0;
        }
        if (super.k == 0) {
            super.q = 33554432;
            return 0;
        }
        int n3 = super.q & 0xFFFF;
        if (n3 == 0) {
            super.q = 33554432;
            return 0;
        }
        if (n2 < 1) {
            super.q |= 0x1000000;
            return 0;
        }
        if (this.d != -1 && psc_n.e == 0 && array[n] != 0) {
            return 0;
        }
        final int n4 = n;
        final int b = this.b(psc_n, array, n, n2);
        n += b;
        if ((super.q & 0x1000000) != 0x0) {
            return n - n4;
        }
        n2 -= b;
        if (psc_n.c[0] != 0 || psc_n.c[1] != 0) {
            throw new psc_m("Improper ending to indefinite length.");
        }
        super.q -= 2;
        n3 -= 2;
        psc_n.e = 0;
        if (n3 == 0) {
            super.q = 33554432;
            return n - n4;
        }
        this.d = -1;
        n += this.c(psc_n, array, n, n2);
        if ((super.q & 0x2000000) != 0x0 || (super.q & 0x1000000) != 0x0) {
            return n - n4;
        }
        throw new psc_m("Improper ending to indefinite length.");
    }
    
    private psc_i e() {
        return this.b.b();
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_w;
    }
    
    protected psc_i b() {
        try {
            return new psc_w(super.l, true, super.m, this.a, this.b);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
