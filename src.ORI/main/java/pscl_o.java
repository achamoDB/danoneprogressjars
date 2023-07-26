// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_o
{
    public pscl_i a;
    public pscl_bn b;
    public pscl_l c;
    public pscl_j[] d;
    public int e;
    
    public pscl_o() {
        this.e = 0;
    }
    
    public pscl_o(final pscl_i a, final pscl_bn b, final pscl_l c, final pscl_j[] array) {
        this.e = 0;
        this.a = a;
        this.b = b;
        this.c = c;
        if (array == null) {
            this.d = null;
        }
        else {
            System.arraycopy(array, 0, this.d = new pscl_j[array.length], 0, array.length);
        }
    }
    
    public void a(final pscl_i a) {
        this.a = a;
    }
    
    public void a(final pscl_bn b) {
        this.b = b;
    }
    
    public void a(final pscl_l c) {
        this.c = c;
    }
    
    public void a(final pscl_j[] array) {
        if (array == null) {
            this.d = null;
        }
        else {
            System.arraycopy(array, 0, this.d = new pscl_j[array.length], 0, array.length);
        }
    }
    
    public pscl_i a() {
        return this.a;
    }
    
    public pscl_l b() {
        return this.c;
    }
    
    public pscl_bn c() {
        return this.b;
    }
    
    public pscl_j[] d() {
        return this.d;
    }
    
    public void a(final int e) {
        this.e = e;
    }
    
    public int e() {
        return this.e;
    }
}
