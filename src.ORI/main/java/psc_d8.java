// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_d8
{
    private String a;
    private int b;
    
    public psc_d8(final int b, final String a) throws psc_d7 {
        this.a = null;
        if (a == null) {
            throw new psc_d7("Provider.Provider: type should not be null.");
        }
        this.b = b;
        this.a = a;
    }
    
    public int a() {
        return this.b;
    }
    
    public String b() {
        return this.a;
    }
    
    public psc_ea a(final psc_ah psc_ah) throws psc_d9 {
        throw new psc_d9("Provider.instantiate: Each subclass of Provider should overwrite this method.");
    }
}
