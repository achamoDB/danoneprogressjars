import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_m4 extends psc_m2 implements Cloneable, Serializable
{
    protected byte[] a;
    protected int b;
    
    public psc_m4() {
        super.k = 1;
        psc_i psc_i = null;
        try {
            psc_i = new psc_t(10551296, true, 0, null, 0, 0);
        }
        catch (psc_m psc_m) {}
        (super.p = new psc_i[1])[0] = psc_i;
    }
    
    public void a(final byte[] array, final int n, final int b) throws psc_m3 {
        if (array == null || b <= 0) {
            throw new psc_m3("content is null");
        }
        if (n < 0 || n + b > array.length) {
            throw new psc_m3("Invalid Content data");
        }
        System.arraycopy(array, n, this.a = new byte[b], 0, b);
        this.b = b;
    }
    
    public byte[] b() {
        if (this.a != null) {
            final byte[] array = new byte[this.b];
            System.arraycopy(this.a, 0, array, 0, this.b);
            return array;
        }
        return null;
    }
    
    protected int m() throws psc_m3 {
        super.p[0].b = null;
        super.p[0].d = this.b;
        if (this.a == null) {
            super.p[0].a = false;
        }
        else {
            super.p[0].a = true;
        }
        super.q = new psc_n(super.p);
        try {
            return super.q.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3(psc_m.getMessage());
        }
    }
    
    protected int b(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Cannot write Data: output array is null.");
        }
        try {
            super.p[0].b = this.a;
            super.p[0].c = 0;
            super.p[0].d = this.b;
            return super.q.a(array, n);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not DER encode Data: " + psc_m.getMessage());
        }
    }
    
    protected boolean k(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            if (super.l != 0) {
                (super.p = new psc_i[1])[0] = new psc_t(10551296, true, 0, super.l, null, 0, 0);
            }
            (super.q = new psc_n(super.p)).c();
            super.u = super.q.a(array, n, n2);
            this.a();
            return true;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    protected int m(final byte[] array, final int n, final int n2) throws psc_m3 {
        super.u = 0;
        final int n3 = 0;
        if (array == null) {
            return n3;
        }
        if (super.q == null) {
            (super.q = new psc_n(super.p)).c();
        }
        try {
            if (super.q.e()) {
                return n3;
            }
            super.u = super.q.a(array, n, n2);
            return n3 + this.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    private int a() {
        if (super.p[0].b == null || super.p[0].d == 0) {
            return 0;
        }
        if (this.a == null) {
            if (super.l != 0) {
                this.a = new byte[super.l];
            }
            else {
                this.a = new byte[super.p[0].d];
            }
        }
        if (this.a.length - this.b < super.p[0].d) {
            final byte[] array = new byte[this.b];
            System.arraycopy(this.a, 0, array, 0, this.b);
            this.d(this.a);
            System.arraycopy(array, 0, this.a = new byte[super.p[0].d + this.b], 0, this.b);
            this.d(array);
        }
        final int d = super.p[0].d;
        if (d != 0) {
            System.arraycopy(super.p[0].b, super.p[0].c, this.a, this.b, d);
        }
        this.b += d;
        return d;
    }
    
    public int q() {
        return this.b;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_m4)) {
            return false;
        }
        final psc_m4 psc_m4 = (psc_m4)o;
        return this.b == psc_m4.b && psc_k4.a(this.a, 0, this.b, psc_m4.a, 0, psc_m4.b);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_m4 psc_m4 = (psc_m4)super.clone();
        if (this.a != null) {
            psc_m4.a = new byte[this.a.length];
            System.arraycopy(this.a, 0, psc_m4.a, 0, this.b);
            psc_m4.b = this.b;
        }
        return psc_m4;
    }
    
    public void s() {
        super.s();
        this.d(this.a);
    }
    
    protected void finalize() {
        this.s();
    }
}
