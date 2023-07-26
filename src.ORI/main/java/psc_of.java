// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_of
{
    private psc_ol a;
    private byte[] b;
    
    public psc_of(final psc_ol psc_ol) {
        this(psc_ol, null);
    }
    
    public psc_of(final psc_ol a, final byte[] b) {
        this.a = a;
        this.b = b;
    }
    
    public psc_ol a() {
        return this.a;
    }
    
    public byte[] b() {
        return this.b.clone();
    }
}
