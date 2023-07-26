import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i7 extends psc_an implements psc_eq, Cloneable, Serializable
{
    private psc_az a;
    private int b;
    private byte[] c;
    private byte[] d;
    
    public psc_i7() {
        this.c = new byte[4];
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final psc_er psc_er) throws psc_be {
        if (!(psc_er instanceof psc_az)) {
            throw new psc_be("Invalid underlying algorithm for MGF1.");
        }
        this.a = (psc_az)psc_er;
        this.b = this.a.h();
        this.d = new byte[this.b];
    }
    
    public String d() {
        return "MGF1";
    }
    
    public String e() {
        if (this.a != null) {
            return this.a.e();
        }
        return null;
    }
    
    public void a(final byte[] array, final int n, final int n2, final byte[] array2, int n3, int i) throws psc_gx {
        if (this.a == null) {
            throw new psc_gx("Not yet implemented.");
        }
        try {
            while (i >= this.b) {
                this.a.j();
                this.a.a(array, n, n2);
                this.a.a(this.c, 0, this.c.length);
                this.a.b(this.d, 0);
                for (int j = 0; j < this.b; ++j, ++n3) {
                    array2[n3] ^= this.d[j];
                }
                this.g();
                i -= this.b;
            }
            if (i > 0) {
                this.a.j();
                this.a.a(array, n, n2);
                this.a.a(this.c, 0, this.c.length);
                this.a.b(this.d, 0);
                int n4 = 0;
                while (i > 0) {
                    array2[n3] ^= this.d[n4];
                    ++n4;
                    ++n3;
                    --i;
                }
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_gx("Digest error: " + psc_ap.getMessage());
        }
        finally {
            this.a.y();
            psc_au.c(this.d);
            this.f();
        }
    }
    
    private void f() {
        this.c[3] = 0;
    }
    
    private void g() {
        final byte[] c = this.c;
        final int n = 3;
        ++c[n];
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_i7 psc_i7 = new psc_i7();
        if (this.a != null) {
            psc_i7.a = (psc_az)this.a.clone();
        }
        psc_i7.b = this.b;
        if (this.c != null) {
            psc_i7.c = this.c.clone();
        }
        if (this.d != null) {
            psc_i7.d = this.d.clone();
        }
        return psc_i7;
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        this.d(this.c);
        this.d(this.d);
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
