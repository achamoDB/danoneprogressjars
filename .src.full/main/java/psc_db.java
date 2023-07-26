import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_db extends psc_da implements Cloneable, Serializable
{
    private psc_az a;
    private int b;
    private int c;
    private int d;
    private static final int e = 1;
    private static final int f = 2;
    private static final int g = 3;
    private static final int h = 4;
    
    psc_db(final psc_az a) {
        this.a = a;
        this.b = a.h();
        this.c = a.i();
        this.d = 1;
    }
    
    private psc_db() {
    }
    
    void a(final byte[] array, final int n) {
        this.a.a(array, n);
    }
    
    public byte[] c() {
        return this.a.d();
    }
    
    public String f() {
        return this.a.e();
    }
    
    public int g() {
        return this.b;
    }
    
    public int h() {
        return this.c;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_db psc_db = new psc_db();
        if (this.a != null) {
            psc_db.a = (psc_az)this.a.clone();
        }
        psc_db.b = this.b;
        psc_db.c = this.c;
        psc_db.d = this.d;
        psc_db.a(this);
        return psc_db;
    }
    
    public void i() {
        this.a.j();
        this.d = 2;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_en {
        switch (this.d) {
            case 2:
            case 3: {
                this.a.a(array, n, n2);
                this.d = 3;
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int c(final byte[] array, final int n) throws psc_en {
        switch (this.d) {
            case 2:
            case 3: {
                final int b = this.a.b(array, n);
                this.d = 4;
                return b;
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.a.a(array, n, array2, n2);
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        this.d = 1;
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
