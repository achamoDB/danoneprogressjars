// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ga extends psc_f8
{
    public psc_ga() {
        super(8, "VeriSignCRSFailureInfo");
    }
    
    public psc_ga(final int n) {
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
        final psc_ga psc_ga = new psc_ga();
        super.a(psc_ga);
        return psc_ga;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_ga)) {
            return false;
        }
        final psc_ga psc_ga = (psc_ga)o;
        return super.equals(o);
    }
}
