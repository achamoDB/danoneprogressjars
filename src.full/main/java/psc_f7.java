// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f7 extends psc_f8
{
    public psc_f7() {
        super(6, "VeriSignCRSMessageType");
    }
    
    public psc_f7(final int n) {
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
        final psc_f7 psc_f7 = new psc_f7();
        super.a(psc_f7);
        return psc_f7;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_f7 && super.equals(o);
    }
}
