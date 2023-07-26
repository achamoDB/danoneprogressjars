// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ac extends pscl_q
{
    public pscl_ac() {
        super.c = -1;
        super.o = 33554432;
    }
    
    public void a() {
        super.c = -1;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_ac;
    }
    
    public pscl_q g() {
        return new pscl_ac();
    }
}
