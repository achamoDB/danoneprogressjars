// 
// Decompiled by Procyon v0.5.36
// 

public class psc_li extends psc_i
{
    private psc_i[] a;
    private psc_n b;
    
    public psc_li(final psc_i[] array) {
        if (array != null) {
            System.arraycopy(array, 0, this.a = new psc_i[array.length], 0, array.length);
            this.b = new psc_n(this.a);
        }
    }
    
    public psc_i[] e() {
        if (this.a == null) {
            return null;
        }
        final psc_i[] array = new psc_i[this.a.length];
        System.arraycopy(this.a, 0, array, 0, this.a.length);
        return array;
    }
    
    public void a(final psc_i[] array) {
        if (array != null) {
            System.arraycopy(array, 0, this.a = new psc_i[array.length], 0, array.length);
            this.b = new psc_n(this.a);
        }
    }
    
    protected void c() {
        if ((super.q & 0x2000000) != 0x0) {
            super.q &= 0xFDFFFFFF;
            super.q |= 0x20000;
        }
        if (this.a != null) {
            super.e = this.a[0].e;
            super.g = this.a[0].g;
        }
    }
    
    public boolean f() {
        return this.b != null && this.b.e();
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        if (this.b == null) {
            throw new psc_m("The ASN1 containers are not set.");
        }
        super.d = this.b.a();
        this.c();
        return super.d;
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, final int n2) throws psc_m {
        if (this.b == null) {
            throw new psc_m("The ASN1 containers are not set.");
        }
        return this.b.a(array2, n2);
    }
    
    void b(final psc_i[] array, final int n) throws psc_m {
        super.q = 131072;
        this.b.b();
        this.c();
    }
    
    int b(final psc_i[] array, final int n, final byte[] array2, final int n2) throws psc_m {
        if (this.b == null) {
            throw new psc_m("The ASN1 containers are not set.");
        }
        super.q = 16777216;
        final int b = this.b.b(array2, n2);
        if (this.b.e()) {
            super.q = 33554432;
        }
        return b;
    }
    
    void d() {
        this.a();
        this.c();
        super.q = 1048576;
        if (super.e != -1) {
            super.q |= 0x200000;
        }
        super.a = true;
        this.b.c();
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        if (this.b == null) {
            throw new psc_m("The ASN1 containers are not set.");
        }
        if (psc_n.e != 0) {
            System.arraycopy(psc_n.c, 0, this.b.c, 0, psc_n.e);
            this.b.e = psc_n.e;
            psc_n.e = 0;
        }
        final int a = this.b.a(array, n2, n3 - n2);
        if (this.b.e()) {
            super.q |= 0x2000000;
        }
        return a;
    }
    
    boolean a(final int n, final psc_i[] array, final int n2) throws psc_m {
        return this.a != null && this.a[0].a(n, this.a, 0);
    }
    
    protected boolean a(final psc_i psc_i) {
        if (!(psc_i instanceof psc_li)) {
            return false;
        }
        final psc_li psc_li = (psc_li)psc_i;
        if (this.a == null) {
            return psc_li.a == null;
        }
        return this.a.length == psc_li.a.length && this.a[0].a(psc_li.a[0]);
    }
    
    protected psc_i b() {
        if (this.a == null) {
            return new psc_li(null);
        }
        final int length = this.a.length;
        final psc_i[] array = new psc_i[length];
        for (int i = 0; i < length; ++i) {
            array[i] = this.a[i].b();
        }
        return new psc_li(array);
    }
    
    public void i() {
        if (this.a != null) {
            for (int i = 0; i < this.a.length; ++i) {
                this.a[i].i();
            }
        }
    }
}
