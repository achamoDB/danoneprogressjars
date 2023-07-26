// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f9 extends psc_f8
{
    public psc_f9() {
        super(7, "VeriSignCRSPKIStatus");
    }
    
    public psc_f9(final int n) {
        this();
        this.d(n);
    }
    
    public void c(final int n) {
        this.d(n);
    }
    
    public int g() {
        return this.h();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_f9 psc_f9 = new psc_f9();
        super.a(psc_f9);
        return psc_f9;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_f9)) {
            return false;
        }
        final psc_f9 psc_f9 = (psc_f9)o;
        return super.equals(o);
    }
}
