import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_dd implements Serializable
{
    private Object a;
    private int b;
    private psc_de c;
    private boolean d;
    private boolean e;
    
    psc_dd(final Object a, final int b, final boolean d) {
        this.d = false;
        this.e = false;
        this.b = b;
        if (this.b == 4) {
            return;
        }
        this.a = a;
        this.d = d;
        if (this.d) {
            this.c = new psc_de(b);
            this.c();
        }
    }
    
    boolean a() {
        return this.d;
    }
    
    boolean b() {
        return this.e;
    }
    
    void c() {
        if (this.e || !this.d || this.c == null) {
            return;
        }
        this.c.a(this.a);
        this.e = true;
    }
    
    void d() {
        if (!this.e || !this.d || this.c == null) {
            return;
        }
        this.c.a(this.a);
        this.e = false;
    }
    
    void e() {
        psc_au.c(this.a);
        this.e = false;
    }
    
    synchronized void a(final Object o) {
        if (!this.e) {
            return;
        }
        this.c.a(o);
    }
    
    protected void f() {
        if (this.a != null) {
            psc_au.c(this.a);
        }
        if (this.c != null) {
            this.c.a();
        }
        this.a = null;
        this.b = 4;
        this.c = null;
        this.e = false;
    }
}
