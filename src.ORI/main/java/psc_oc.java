import java.util.Properties;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_oc extends psc_od
{
    private psc_ol a;
    private psc_f b;
    private psc_dt c;
    private Properties d;
    private psc_f[] e;
    
    public psc_oc(final psc_ol a) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.a = a;
    }
    
    public psc_ol a() {
        return this.a;
    }
    
    public psc_f b() {
        return this.b;
    }
    
    public void a(final psc_f b) {
        this.b = b;
    }
    
    public psc_dt c() {
        return this.c;
    }
    
    public void a(final psc_dt c) {
        this.c = c;
    }
    
    public Properties d() {
        return this.d;
    }
    
    public void a(final Properties d) {
        this.d = d;
    }
    
    public psc_f[] e() {
        return this.e;
    }
    
    public void a(final psc_f[] e) {
        this.e = e;
    }
}
