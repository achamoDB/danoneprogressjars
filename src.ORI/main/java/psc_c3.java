import java.util.Hashtable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c3
{
    private Hashtable a;
    private String b;
    
    public psc_c3() {
        this("Java");
    }
    
    public psc_c3(final String s) {
        this(new Hashtable(), s);
    }
    
    private psc_c3(final Hashtable a, final String b) {
        this.a = a;
        this.b = b;
    }
    
    public void a(final psc_n0 key, final String value) {
        this.a.put(key, value);
    }
    
    public String a(final psc_n0 key) {
        final String s = this.a.get(key);
        return (s == null) ? this.b : s;
    }
    
    public String a() {
        return this.b;
    }
    
    public Object clone() {
        return new psc_c3((Hashtable)this.a.clone(), this.b);
    }
}
