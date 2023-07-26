// 
// Decompiled by Procyon v0.5.36
// 

public class psc_d1
{
    psc_dw a;
    psc_c5 b;
    psc_d2 c;
    psc_e[] d;
    int e;
    
    public psc_d1() {
        this.e = 0;
    }
    
    public psc_d1(final psc_dw a, final psc_c5 b, final psc_d2 c, final psc_e[] array) {
        this.e = 0;
        this.a = a;
        this.b = b;
        this.c = c;
        if (array == null) {
            this.d = null;
        }
        else {
            System.arraycopy(array, 0, this.d = new psc_e[array.length], 0, array.length);
        }
    }
    
    public void a(final psc_dw a) {
        this.a = a;
    }
    
    public void a(final psc_c5 b) {
        this.b = b;
    }
    
    public void a(final psc_d2 c) {
        this.c = c;
    }
    
    public void a(final psc_e[] array) {
        if (array == null) {
            this.d = null;
        }
        else {
            System.arraycopy(array, 0, this.d = new psc_e[array.length], 0, array.length);
        }
    }
    
    public psc_dw a() {
        return this.a;
    }
    
    public psc_d2 b() {
        return this.c;
    }
    
    public psc_c5 c() {
        return this.b;
    }
    
    public psc_e[] d() {
        return this.d;
    }
    
    public void a(final int e) {
        this.e = e;
    }
    
    public int e() {
        return this.e;
    }
}
