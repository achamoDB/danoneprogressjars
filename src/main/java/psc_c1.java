import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c1 extends psc_aj implements Cloneable, Serializable, psc_bj, psc_bl, psc_b1
{
    private byte[] a;
    
    public psc_c1() {
        super.bt = false;
        super.bu = -1;
        super.a = "NonStandardExtension";
    }
    
    public psc_c1(final byte[] array, final boolean bt, final byte[] array2) {
        if (array != null) {
            super.bv = array.clone();
            super.bw = array.length;
        }
        if (array2 != null) {
            this.a = array2.clone();
        }
        super.bt = bt;
        super.bu = -1;
        super.a = "NonStandardExtension";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final int n2 = 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
            System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        }
        catch (psc_m psc_m) {}
    }
    
    public void c(final byte[] array, final int n, final int bw) {
        if (array != null && bw != 0) {
            super.bv = new byte[bw];
            super.bw = bw;
            System.arraycopy(array, n, super.bv, 0, bw);
        }
    }
    
    public byte[] a() {
        if (super.bv != null && super.bw != 0) {
            final byte[] array = new byte[super.bw];
            System.arraycopy(super.bv, 0, array, 0, super.bw);
            return array;
        }
        return null;
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        }
    }
    
    public byte[] g() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.a.length];
        System.arraycopy(this.a, 0, array, 0, this.a.length);
        return array;
    }
    
    public int e() {
        if (this.a != null) {
            return this.a.length;
        }
        return 0;
    }
    
    public int e(final byte[] array, final int n) {
        if (this.a == null) {
            return 0;
        }
        System.arraycopy(this.a, 0, array, n, this.a.length);
        return this.a.length;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_c1 psc_c1 = new psc_c1();
        if (this.a != null) {
            psc_c1.a = this.a.clone();
        }
        super.a(psc_c1);
        return psc_c1;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
}
