import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_p extends pscl_q
{
    private int a;
    private pscl_q b;
    private Vector c;
    private int d;
    
    public pscl_p(final int n, final boolean b, final int n2, final int a, final int n3, final int n4, final int n5) {
        super(n, b, n2, a);
        super.i = false;
        super.f |= 0x4000000;
        this.a = a;
        switch (n3) {
            case 512: {
                this.b = new pscl_r(n4, true, n5, 0);
            }
            case 1024: {
                this.b = new pscl_s(n4, true, n5, null, 0, 0);
            }
            default: {
                this.b = new pscl_t(n4, true, n5, null, 0, 0);
            }
        }
    }
    
    public pscl_p(final int n, final boolean b, final int n2, final int a, final pscl_q b2) {
        super(n, b, n2, a);
        super.i = false;
        super.f |= 0x4000000;
        this.b = b2;
        this.a = a;
    }
    
    public void a(final pscl_q obj) throws pscl_x {
        if (super.o == 0 || (super.o & 0x2000000) != 0x0) {
            this.c = new Vector();
            super.o = 131072;
        }
        if ((super.o & 0x20000000) != 0x0) {
            throw new pscl_x("Method noMoreData for OfContainer has been called.");
        }
        if (this.b.b(obj)) {
            this.c.addElement(obj);
            return;
        }
        throw new pscl_x("Improper internal container for OF.");
    }
    
    public void f() {
        super.o |= 0x20000000;
    }
    
    public int d() {
        return this.c.size();
    }
    
    public pscl_q a(final int index) throws pscl_x {
        if (index < 0 || index >= this.c.size()) {
            throw new pscl_x("No container at requested index.");
        }
        return this.c.elementAt(index);
    }
    
    public void a() {
        super.i = true;
        super.a();
        super.i = false;
        this.d = 0;
    }
    
    public void a(final byte[] array, final int n, final int n2, final boolean b) throws pscl_x {
        throw new pscl_x("An OfContainer cannot accept new data through this method.");
    }
    
    public int a(final pscl_q[] array, final int n) {
        super.n = 0;
        this.a();
        if (!super.g) {
            return this.a(false, null, 0);
        }
        for (int i = 0; i < this.c.size(); ++i) {
            super.n += ((pscl_q)this.c.elementAt(i)).a((pscl_q[])null, 0);
        }
        super.e = super.n;
        final int b = 1 + super.e + pscl_w.a(super.n);
        if (super.a == -1) {
            return b;
        }
        super.b = b;
        return 1 + super.b + pscl_w.a(b);
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        final int n3 = n2;
        n2 += super.a(array, n, array2, n2);
        if (!super.g) {
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
            n2 += ((pscl_q)this.c.elementAt(array3[k])).a(array, n, array2, n2);
        }
        return n2 - n3;
    }
    
    private boolean a(final int index, final int[] array, final int n) {
        final pscl_q pscl_q = this.c.elementAt(index);
        final pscl_q pscl_q2 = this.c.elementAt(array[n]);
        if (pscl_q.n > pscl_q2.n) {
            return false;
        }
        if (pscl_q.n < pscl_q2.n) {
            return this.b(index, array, n);
        }
        int m;
        int i;
        int j;
        for (m = pscl_q.m, i = pscl_q2.m, j = 0; j < pscl_q.n; ++j, ++m, ++i) {
            if (pscl_q.l[m] > pscl_q2.l[i]) {
                return false;
            }
            if (pscl_q.l[m] < pscl_q2.l[i]) {
                break;
            }
        }
        return j < pscl_q.n && this.b(index, array, n);
    }
    
    private boolean b(final int n, final int[] array, final int n2) {
        for (int i = array.length - 1; i > n2; --i) {
            array[i] = array[i - 1];
        }
        array[n2] = n;
        return true;
    }
    
    public int b(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        final int n3 = n2;
        while (this.d < this.c.size()) {
            final pscl_q pscl_q = this.c.elementAt(this.d);
            if ((super.o & 0x10000000) == 0x0) {
                pscl_q.b((pscl_q[])null, 0);
                super.o |= 0x10000000;
            }
            n2 += pscl_q.c(null, 0, array2, n2);
            if (!pscl_q.h()) {
                return n2 - n3;
            }
            super.o ^= 0x10000000;
            ++this.d;
        }
        if ((super.o & 0x20000000) != 0x0) {
            super.o |= 0x40000;
        }
        return n2 - n3;
    }
    
    public void b() {
        super.b();
        this.d = 0;
        this.c = new Vector();
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, int n2, int i, final boolean b) throws pscl_x {
        final int n3 = n2;
        final int size = this.c.size();
        final int d = this.d(pscl_v, array, n2, i);
        if ((super.o & 0x2000000) != 0x0 || (super.o & 0x1000000) != 0x0) {
            return d;
        }
        if (this.d < size) {
            final pscl_q pscl_q = this.c.elementAt(this.d);
            final int a = pscl_q.a(pscl_v, n, array, n2, n2 + i);
            if (super.e > 0) {
                super.e -= a;
            }
            if ((pscl_q.o & 0x2000000) == 0x0) {
                super.o |= 0x1000000;
                return a;
            }
            n2 += a;
            i -= a;
            ++this.d;
            n2 += this.d(pscl_v, array, n2, i);
            if ((super.o & 0x2000000) != 0x0 || (super.o & 0x1000000) != 0x0) {
                return n2 - n3;
            }
        }
        while (i > 0) {
            final pscl_q e = this.e();
            e.b();
            final int a2 = e.a(pscl_v, n, array, n2, n2 + i);
            if (super.e > 0) {
                super.e -= a2;
            }
            n2 += a2;
            i -= a2;
            this.a(e);
            if ((e.o & 0x2000000) == 0x0) {
                super.o |= 0x1000000;
                return n2 - n3;
            }
            ++this.d;
            n2 += this.d(pscl_v, array, n2, i);
            if ((super.o & 0x2000000) != 0x0 || (super.o & 0x1000000) != 0x0) {
                return n2 - n3;
            }
        }
        super.o |= 0x1000000;
        return n2 - n3;
    }
    
    private int d(final pscl_v pscl_v, final byte[] array, int n, int n2) throws pscl_x {
        if (super.e > 0) {
            return 0;
        }
        if (super.e == 0) {
            super.o = 33554432;
            return 0;
        }
        int n3 = super.o & 0xFFFF;
        if (n3 == 0) {
            super.o = 33554432;
            return 0;
        }
        if (n2 < 1) {
            super.o |= 0x1000000;
            return 0;
        }
        if (this.d != -1 && pscl_v.d == 0 && array[n] != 0) {
            return 0;
        }
        final int n4 = n;
        final int b = this.b(pscl_v, array, n, n2);
        n += b;
        if ((super.o & 0x1000000) != 0x0) {
            return n - n4;
        }
        n2 -= b;
        if (pscl_v.c[0] != 0 || pscl_v.c[1] != 0) {
            throw new pscl_x("Improper ending to indefinite length.");
        }
        super.o -= 2;
        n3 -= 2;
        pscl_v.d = 0;
        if (n3 == 0) {
            super.o = 33554432;
            return n - n4;
        }
        this.d = -1;
        n += this.d(pscl_v, array, n, n2);
        if ((super.o & 0x2000000) != 0x0 || (super.o & 0x1000000) != 0x0) {
            return n - n4;
        }
        throw new pscl_x("Improper ending to indefinite length.");
    }
    
    private pscl_q e() {
        return this.b.g();
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_p;
    }
    
    public pscl_q g() {
        return new pscl_p(super.f, true, super.h, this.a, this.b);
    }
}
