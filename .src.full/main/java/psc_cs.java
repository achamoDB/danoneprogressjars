import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cs extends psc_aj implements Cloneable, Serializable, psc_bj
{
    psc_n a;
    
    public psc_cs() {
        super.bu = 117;
        super.bt = false;
        this.a(psc_aj.a9);
        super.a = "OCSPNoCheck";
    }
    
    public psc_cs(final boolean bt) {
        super.bu = 117;
        this.a(psc_aj.a9);
        super.a = "OCSPNoCheck";
        super.bt = bt;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        if (array.length < 2 + n) {
            throw new psc_g("Invalid  encoding of OCSP NoCheck extension.");
        }
        if (array[n] != 5 || array[n + 1] != 0) {
            throw new psc_g("Invalid  encoding of OCSP NoCheck extension.");
        }
    }
    
    public int e() {
        return 2;
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        array[n] = 5;
        array[n + 1] = 0;
        return 2;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cs psc_cs = new psc_cs();
        if (this.a != null) {
            psc_cs.e();
        }
        super.a(psc_cs);
        return psc_cs;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
}
