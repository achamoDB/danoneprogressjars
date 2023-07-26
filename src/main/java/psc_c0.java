import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c0 extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Vector a;
    psc_n b;
    
    public psc_c0() {
        this.a = new Vector();
        super.bu = 124;
        super.bt = false;
        this.a(psc_aj.bn);
        super.a = "Biometric_Info";
    }
    
    public psc_c0(final psc_gs obj, final boolean bt) {
        this.a = new Vector();
        super.bu = 124;
        super.bt = bt;
        this.a(psc_aj.bn);
        super.a = "Biometric_Info";
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public void a(final psc_gs obj) {
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public int a() {
        return this.a.size();
    }
    
    public psc_gs a(final int index) throws psc_g {
        if (this.a.size() <= index || index < 0) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a.elementAt(index);
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null || n < 0) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a.addElement(new psc_gs(a.b, a.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot decode the BER of this extension." + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_g("Cannot decode the BER of this etension." + psc_v.getMessage());
        }
    }
    
    public int e() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(0, true, 0, 12288, new psc_k(12288));
            final psc_j psc_j = new psc_j();
            vector.addElement(obj);
            for (int i = 0; i < this.a.size(); ++i) {
                try {
                    final psc_gs psc_gs = this.a.elementAt(i);
                    final byte[] array = new byte[psc_gs.b(0)];
                    obj.b(new psc_k(0, true, 0, array, 0, psc_gs.c(array, 0, 0)));
                }
                catch (psc_m psc_m) {
                    return 0;
                }
                catch (psc_v psc_v) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.b = new psc_n(anArray);
            return this.b.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.b.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_c0 psc_c0 = new psc_c0();
        for (int i = 0; i < this.a.size(); ++i) {
            psc_c0.a.addElement(((psc_gs)this.a.elementAt(i)).clone());
        }
        if (this.b != null) {
            psc_c0.e();
        }
        super.a(psc_c0);
        return psc_c0;
    }
    
    protected void f() {
        super.f();
        this.a = new Vector();
        this.b = null;
    }
}
