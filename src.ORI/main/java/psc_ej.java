import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ej extends psc_eh implements Cloneable, Serializable
{
    protected static final int a = 1;
    protected static final int b = 2;
    protected static final int c = 3;
    protected static final int d = 4;
    protected static final int e = 5;
    protected static final int f = 6;
    protected static final int g = 7;
    private int h;
    protected psc_az i;
    protected psc_ei j;
    
    psc_ej(final psc_ei j, final psc_az i) {
        this.j = j;
        this.i = i;
        this.h = 1;
    }
    
    protected psc_ej() {
    }
    
    protected psc_az c() {
        return this.i;
    }
    
    protected psc_ei d() {
        return this.j;
    }
    
    public int[] l() {
        return this.j.c();
    }
    
    public byte[] g() throws psc_ao {
        return this.j.a(this.i);
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        this.j.a(array, n);
    }
    
    public String j() {
        return this.j.d();
    }
    
    public String k() {
        if (this.i != null) {
            return this.i.e();
        }
        return null;
    }
    
    public int m() {
        switch (this.h) {
            case 2:
            case 3:
            case 5:
            case 6: {
                return this.j.e();
            }
            default: {
                return -1;
            }
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ej psc_ej = new psc_ej();
        if (this.j != null) {
            psc_ej.j = (psc_ei)this.j.clone();
        }
        if (this.i != null) {
            psc_ej.i = (psc_az)this.i.clone();
        }
        psc_ej.h = this.h;
        psc_ej.a(this);
        return psc_ej;
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.j.a(this.i, psc_dc, secureRandom);
        this.h = 2;
    }
    
    public void o() throws psc_en {
        switch (this.h) {
            case 2:
            case 3:
            case 4: {
                this.j.f();
                this.h = 2;
            }
            default: {
                throw new psc_en("Object not Initialized for MAC");
            }
        }
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_en {
        switch (this.h) {
            case 2:
            case 3: {
                this.j.h();
                this.j.a(array, n, n2);
                this.h = 3;
                this.j.g();
            }
            default: {
                throw new psc_en("Object not Initialized for MAC");
            }
        }
    }
    
    public int c(final byte[] array, final int n) throws psc_en {
        switch (this.h) {
            case 2:
            case 3: {
                this.j.h();
                final int b = this.j.b(array, n);
                this.h = 4;
                this.j.g();
                return b;
            }
            default: {
                throw new psc_en("Object Not Initialized");
            }
        }
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.j.a(this.i, psc_dc, secureRandom);
        this.h = 5;
    }
    
    public void r() throws psc_en {
        switch (this.h) {
            case 5:
            case 6:
            case 7: {
                this.j.f();
                this.h = 5;
            }
            default: {
                throw new psc_en("Object not Initialized for verify");
            }
        }
    }
    
    public void c(final byte[] array, final int n, final int n2) throws psc_en {
        switch (this.h) {
            case 5:
            case 6: {
                this.j.h();
                this.j.a(array, n, n2);
                this.j.g();
                this.h = 6;
            }
            default: {
                throw new psc_en("Object Not Initialized for verify");
            }
        }
    }
    
    public boolean d(final byte[] array, final int n, final int n2) throws psc_en {
        switch (this.h) {
            case 5:
            case 6: {
                this.j.h();
                final byte[] array2 = new byte[this.j.e()];
                final int b = this.j.b(array2, 0);
                this.j.g();
                this.h = 7;
                return a(array, n, n2, array2, 0, b);
            }
            default: {
                throw new psc_en("Object not initialized for verify.");
            }
        }
    }
    
    private static boolean a(final byte[] array, int n, final int n2, final byte[] array2, int n3, final int n4) {
        if (array == null || array2 == null) {
            return array == null && array2 == null;
        }
        if (n2 != n4) {
            return false;
        }
        for (int i = 0; i < n2; ++i, ++n, ++n3) {
            if (array[n] != array2[n3]) {
                return false;
            }
        }
        return true;
    }
    
    public void y() {
        super.y();
        if (this.i != null) {
            this.i.y();
        }
        if (this.j != null) {
            this.j.y();
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
