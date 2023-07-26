import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b3 extends psc_aj implements Cloneable, Serializable, psc_b1, psc_b2
{
    private byte[] a;
    private int b;
    private int c;
    psc_n d;
    
    public psc_b3() {
        super.bu = 23;
        super.bt = false;
        this.c(23);
        super.a = "HoldInstructionCode";
    }
    
    public psc_b3(final byte[] array, final int n, final int n2, final boolean bt) {
        super.bu = 23;
        super.bt = bt;
        if (array != null && n2 != 0) {
            this.a(array, n, n2);
        }
        this.c(23);
        super.a = "HoldInstructionCode";
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array == null || n2 == 0) {
            return;
        }
        try {
            this.a = psc_l.a(new psc_i[] { new psc_r(0, true, 0, array, n, n2) });
            this.b = 1 + psc_o.a(this.a, 1);
            this.c = this.a.length - this.b;
        }
        catch (psc_m psc_m) {
            this.a = null;
            this.b = 0;
            this.c = 0;
        }
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_r psc_r = new psc_r(0);
        final psc_i[] array2 = { psc_r };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            this.a = null;
            throw new psc_g("Could not decode HoldInstructionCode extension.");
        }
        this.a(psc_r.b, psc_r.c, psc_r.d);
    }
    
    public byte[] a() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.c];
        System.arraycopy(this.a, this.b, array, 0, this.c);
        return array;
    }
    
    public int e() {
        try {
            this.d = new psc_n(new psc_i[] { new psc_r(0, true, 0, this.a, this.b, this.c) });
            return this.d.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.d == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.d.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b3 psc_b3 = new psc_b3();
        if (this.a != null) {
            psc_b3.a = this.a.clone();
            psc_b3.b = this.b;
            psc_b3.c = this.c;
        }
        super.a(psc_b3);
        return psc_b3;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.c = 0;
        this.b = 0;
    }
}
