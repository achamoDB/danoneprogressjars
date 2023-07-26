import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cy extends psc_aj implements Cloneable, Serializable, psc_b2
{
    private psc_u a;
    private psc_ca b;
    psc_n c;
    
    public psc_cy() {
        super.bu = 122;
        super.bt = false;
        this.a(psc_aj.bj);
        super.a = "OCSPServiceLocator";
        this.a = null;
        this.b = null;
    }
    
    public psc_cy(final psc_u psc_u, final psc_ca psc_ca) throws psc_g {
        if (psc_u == null || psc_ca == null) {
            throw new psc_g("Missing values");
        }
        super.bu = 122;
        super.bt = false;
        this.a(psc_aj.bj);
        super.a = "OCSPServiceLocator";
        try {
            this.a = (psc_u)psc_u.clone();
            this.b = (psc_ca)psc_ca.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g(ex.getMessage());
        }
    }
    
    public int e() {
        if (this.a == null) {
            return 0;
        }
        final byte[] array = new byte[this.a.d(0)];
        try {
            this.a.a(array, 0, array.length);
        }
        catch (psc_v psc_v) {
            return 0;
        }
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_k psc_k = new psc_k(65280, true, 0, array, 0, array.length);
            psc_k psc_k2;
            if (this.b != null) {
                final byte[] a = this.a(this.b);
                psc_k2 = new psc_k(65536, true, 0, a, 0, a.length);
            }
            else {
                psc_k2 = new psc_k(65536, false, 0, null, 0, 0);
            }
            this.c = new psc_n(new psc_i[] { psc_h, psc_k, psc_k2, new psc_j() });
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    private psc_k a(final int n, final Vector vector, final Vector vector2) throws psc_g {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final byte[] array = vector.elementAt(n);
        psc_k psc_k;
        try {
            final psc_r psc_r = new psc_r(16777216, true, 0, array, 0, array.length);
            final psc_fh psc_fh = vector2.elementAt(n);
            final byte[] array2 = new byte[psc_fh.a(0)];
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_r, new psc_k(0, true, 0, array2, 0, psc_fh.a(array2, 0, 0)), psc_j });
            final byte[] array3 = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array3, 0, psc_n.a(array3, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode Access Description");
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not encode Access Description ");
        }
        return psc_k;
    }
    
    private byte[] a(final psc_ca psc_ca) {
        final Vector<psc_fh> vector = new Vector<psc_fh>();
        final Vector<byte[]> vector2 = new Vector<byte[]>();
        final int a = psc_ca.a();
        if (a == 0) {
            return null;
        }
        try {
            for (int i = 0; i < a; ++i) {
                final byte[] g = psc_ca.g(i);
                final byte[] obj = new byte[g.length];
                System.arraycopy(g, 0, obj, 0, g.length);
                vector2.addElement(obj);
                vector.addElement(psc_ca.b(i));
            }
        }
        catch (psc_g psc_g) {
            return null;
        }
        try {
            final Vector vector3 = new Vector<psc_w>();
            final psc_w obj2 = new psc_w(0, true, 0, 12288, new psc_k(12288));
            vector3.addElement(obj2);
            for (int j = 0; j < vector2.size(); ++j) {
                obj2.b(this.a(j, vector2, vector));
            }
            final psc_i[] anArray = new psc_i[vector3.size()];
            vector3.copyInto(anArray);
            final psc_n psc_n = new psc_n(anArray);
            final byte[] array = new byte[psc_n.a()];
            psc_n.a(array, 0);
            return array;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.c == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.c.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_i[] array2 = { new psc_ai(0) };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode ArchiveCutoff extension.");
        }
    }
    
    public psc_u a() {
        return this.a;
    }
    
    public psc_ca g() {
        return this.b;
    }
    
    public void a(final psc_u psc_u) throws CloneNotSupportedException {
        if (psc_u == null) {
            throw new CloneNotSupportedException("Missing value");
        }
        try {
            this.a = (psc_u)psc_u.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new CloneNotSupportedException(ex.getMessage());
        }
    }
    
    public void b(final psc_ca psc_ca) throws CloneNotSupportedException {
        if (psc_ca == null) {
            throw new CloneNotSupportedException("Missing value");
        }
        try {
            this.b = (psc_ca)psc_ca.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new CloneNotSupportedException(ex.getMessage());
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cy psc_cy = new psc_cy();
        if (this.a != null) {
            psc_cy.a = (psc_u)this.a.clone();
        }
        if (this.b != null) {
            psc_cy.b = (psc_ca)this.b.clone();
        }
        if (this.c != null) {
            psc_cy.e();
        }
        super.a(psc_cy);
        return psc_cy;
    }
}
