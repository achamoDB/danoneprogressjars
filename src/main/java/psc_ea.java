// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_ea
{
    private String a;
    protected psc_ah b;
    
    public psc_ea(final psc_ah b, final String a) throws psc_d7 {
        this.a = null;
        if (b == null) {
            throw new psc_d7("ProviderImplementation.ProviderImplementation: certJ cannot be null.");
        }
        if (a == null) {
            throw new psc_d7("ProviderImplementation.ProviderImplementation: name has to be a non-null String.");
        }
        this.b = b;
        this.a = a;
    }
    
    public String b() {
        return this.a;
    }
    
    public void c() {
    }
    
    public String toString() {
        return "Provider named " + this.a;
    }
}
