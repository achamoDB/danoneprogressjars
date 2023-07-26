import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_m6 implements Cloneable, Serializable
{
    Vector a;
    Vector b;
    Vector c;
    private Vector d;
    private Vector e;
    private Vector f;
    private Vector g;
    
    psc_m6() {
        this.a = new Vector();
        this.b = new Vector();
        this.c = new Vector();
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.g = new Vector();
    }
    
    psc_m6(final psc_f[] array, final psc_na[] array2, final psc_dt[] array3, final psc_cr[] array4, final psc_cr[] array5, final psc_cr[] array6, final String[] array7) {
        this.a = new Vector();
        this.b = new Vector();
        this.c = new Vector();
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.g = new Vector();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.d.addElement(array[i]);
                if (array4 == null || i >= array4.length) {
                    this.a.addElement(null);
                }
                else {
                    this.a.addElement(array4[i]);
                }
            }
        }
        if (array2 != null) {
            for (int j = 0; j < array2.length; ++j) {
                this.f.addElement(array2[j]);
                if (array5 == null || j >= array5.length) {
                    this.c.addElement(null);
                }
                else {
                    this.c.addElement(array5[j]);
                }
            }
        }
        if (array3 != null) {
            for (int k = 0; k < array3.length; ++k) {
                this.e.addElement(array3[k]);
                if (array6 == null || k >= array6.length) {
                    this.b.addElement(null);
                }
                else {
                    this.b.addElement(array6[k]);
                }
                if (array7 == null || k >= array7.length) {
                    this.g.addElement(null);
                }
                else {
                    this.g.addElement(array7[k]);
                }
            }
        }
    }
    
    psc_e a(final psc_cr psc_cr) throws psc_m0 {
        try {
            for (int i = 0; i < this.a.size(); ++i) {
                final psc_cr psc_cr2 = this.a.elementAt(i);
                if (psc_cr2 != null) {
                    if (this.a(psc_cr, psc_cr2)) {
                        return (psc_e)this.d.elementAt(i);
                    }
                }
            }
            throw new psc_m0("No Certificate found for Private Key");
        }
        catch (Exception ex) {
            throw new psc_m0(ex.getMessage());
        }
    }
    
    boolean a(final psc_cr psc_cr, final psc_cr psc_cr2) throws psc_m0 {
        try {
            for (int i = 0; i < psc_cr.a(); ++i) {
                final psc_fz c = psc_cr.c(i);
                for (int j = 0; j < psc_cr2.a(); ++j) {
                    final psc_fz c2 = psc_cr2.c(j);
                    if (c.c() == c2.c()) {
                        switch (c.c()) {
                            case 3: {
                                if (((psc_f4)c).g().equals(((psc_f4)c2).g())) {
                                    return true;
                                }
                                break;
                            }
                            case 4: {
                                final psc_f5 psc_f5 = (psc_f5)c;
                                final psc_f5 psc_f6 = (psc_f5)c2;
                                final byte[] g = psc_f5.g();
                                final byte[] g2 = psc_f6.g();
                                if (g.length != g2.length) {
                                    break;
                                }
                                for (int n = 0; n < g2.length && g[n] == g2[n]; ++n) {}
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        catch (Exception ex) {
            throw new psc_m0(ex.getMessage());
        }
    }
    
    Vector a() {
        return this.d;
    }
    
    Vector b() {
        return this.f;
    }
    
    Vector c() {
        return this.e;
    }
    
    Vector d() {
        return this.a;
    }
    
    Vector e() {
        return this.b;
    }
    
    Vector f() {
        return this.c;
    }
    
    Vector g() {
        return this.g;
    }
}
