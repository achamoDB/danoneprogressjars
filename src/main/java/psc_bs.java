import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bs extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Vector a;
    psc_n b;
    
    public psc_bs() {
        this.a = new Vector();
        super.bu = 9;
        super.bt = false;
        this.c(9);
        super.a = "SubjectDirectoryAttributes";
    }
    
    public psc_bs(final psc_fz obj, final boolean bt) {
        this.a = new Vector();
        super.bu = 9;
        super.bt = bt;
        this.c(9);
        if (obj != null) {
            this.a.addElement(obj);
        }
        super.a = "SubjectDirectoryAttributes";
    }
    
    public void a(final psc_fz obj) {
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public psc_fz a(final int index) throws psc_g {
        if (index < this.a.size()) {
            return this.a.elementAt(index);
        }
        throw new psc_g("Invalid Index");
    }
    
    public int a() {
        return this.a.size();
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            final int j = psc_w.j();
            if (j > 0) {
                this.a = new Vector();
            }
            for (int i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a.addElement(psc_fz.a(a.b, a.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode SubjectDirectoryAttributes extension.");
        }
        catch (psc_f0 psc_f0) {
            throw new psc_g("Could not decode SubjectDirectoryAttributes extension.");
        }
    }
    
    public int e() {
        try {
            final psc_w psc_w = new psc_w(0, true, 0, 12288, new psc_k(12288));
            int size = 0;
            if (this.a != null) {
                size = this.a.size();
            }
            for (int i = 0; i < size; ++i) {
                final psc_fz psc_fz = this.a.elementAt(i);
                final byte[] array = new byte[psc_fz.a(0)];
                psc_w.b(new psc_k(12288, true, 0, array, 0, psc_fz.c(array, 0, 0)));
            }
            this.b = new psc_n(new psc_i[] { psc_w });
            return this.b.a();
        }
        catch (Exception ex) {
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
            return this.b.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bs psc_bs = new psc_bs();
        psc_bs.a = (Vector)this.a.clone();
        if (this.b != null) {
            psc_bs.e();
        }
        super.a(psc_bs);
        return psc_bs;
    }
    
    protected void f() {
        super.f();
        this.a = new Vector();
    }
}
