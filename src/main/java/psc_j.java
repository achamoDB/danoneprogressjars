// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j extends psc_i
{
    public psc_j() {
        super.g = -1;
        super.q = 33554432;
    }
    
    protected void c() {
        super.g = -1;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_j;
    }
    
    protected psc_i b() {
        return new psc_j();
    }
}
