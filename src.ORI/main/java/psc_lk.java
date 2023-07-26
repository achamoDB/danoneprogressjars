// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_lk extends psc_lj
{
    static final int a = 1;
    static final int b = 2;
    static final int c = 3;
    static final int d = 4;
    static final int e = 5;
    static final int f = 6;
    static final int g = 7;
    private int h;
    private psc_jx i;
    
    psc_lk(final psc_jx i) {
        this.i = i;
        this.h = 1;
    }
    
    private psc_lk() {
    }
    
    public String e() {
        return this.i.b();
    }
    
    public int[] f() {
        return this.i.a();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_lk psc_lk = new psc_lk();
        if (this.i != null) {
            psc_lk.i = (psc_jx)this.i.clone();
        }
        psc_lk.h = this.h;
        psc_lk.a(this);
        return psc_lk;
    }
    
    public int a(final int n) {
        switch (this.h) {
            case 2:
            case 3: {
                return this.i.a(n, true);
            }
            case 5:
            case 6: {
                return this.i.a(n, false);
            }
            default: {
                return -1;
            }
        }
    }
    
    public void g() {
        this.i.c();
        this.h = 2;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en {
        switch (this.h) {
            case 2:
            case 3: {
                if (n2 <= 0) {
                    return 0;
                }
                if (array == null || array.length == 0) {
                    return 0;
                }
                this.i.f();
                final int a = this.i.a(array, n, n2, array2, n3);
                this.h = 3;
                this.i.e();
                return a;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for decoding.");
            }
            default: {
                throw new psc_en("Object Not Initialized");
            }
        }
    }
    
    public int a(final byte[] array, final int n) throws psc_en {
        switch (this.h) {
            case 2:
            case 3: {
                this.i.f();
                final int a = this.i.a(array, n);
                this.h = 4;
                this.i.e();
                return a;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for decryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public void i() {
        this.i.d();
        this.h = 5;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en, psc_e1 {
        switch (this.h) {
            case 5:
            case 6: {
                if (n2 <= 0) {
                    return 0;
                }
                this.i.f();
                final int b = this.i.b(array, n, n2, array2, n3);
                this.h = 6;
                this.i.e();
                return b;
            }
            case 2:
            case 3: {
                throw new psc_en("Object initialized for encoding.");
            }
            default: {
                throw new psc_en("Object Not Initialized");
            }
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_en, psc_e1 {
        switch (this.h) {
            case 5:
            case 6: {
                this.i.f();
                final int b = this.i.b(array, n);
                this.h = 7;
                this.i.e();
                return b;
            }
            default: {
                throw new psc_en("Object not Initialized");
            }
        }
    }
    
    public void y() {
        super.y();
        if (this.i != null) {
            this.i.g();
        }
        this.h = 1;
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
