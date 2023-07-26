import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cx extends psc_aj implements Cloneable, Serializable, psc_cw
{
    psc_n a;
    Vector b;
    public static byte[] c;
    
    public psc_cx() {
        super.bu = 121;
        super.bt = false;
        this.a(psc_aj.bh);
        super.a = "OCSPAcceptableResponses";
        this.b = null;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null || n2 == 0) {
            throw new psc_g("Missing values");
        }
        final byte[] obj = new byte[n2];
        System.arraycopy(array, n, obj, 0, n2);
        if (this.b == null) {
            this.b = new Vector();
        }
        this.b.addElement(obj);
    }
    
    public int a() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }
    
    public byte[] b(final int index) throws psc_d7 {
        if (this.b.size() <= index) {
            throw new psc_d7("Specified index is invalid.");
        }
        if (this.b == null) {
            return null;
        }
        return this.b.elementAt(index);
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(super.bx, 12288, new psc_k(1536));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_r psc_r = new psc_r(16777216);
                psc_l.a(a.b, a.c, new psc_i[] { psc_r });
                if (this.b == null) {
                    this.b = new Vector();
                }
                final byte[] obj = new byte[psc_r.d];
                System.arraycopy(psc_r.b, psc_r.c, obj, 0, psc_r.d);
                this.b.addElement(obj);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode Extended Key Usage extension.");
        }
    }
    
    public int e() {
        final int size = this.b.size();
        if (size == 0) {
            return 0;
        }
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(0, true, 0, 12288, new psc_k(1536));
            vector.addElement(obj);
            for (int i = 0; i < size; ++i) {
                try {
                    obj.b(this.a(i));
                }
                catch (Exception ex) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.a = new psc_n(anArray);
            return this.a.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.a == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.a.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    private psc_k a(final int index) throws psc_g {
        try {
            final byte[] array = this.b.elementAt(index);
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_r(16777216, true, 0, array, 0, array.length) });
            final byte[] array2 = new byte[psc_n.a()];
            return new psc_k(1536, true, 0, array2, 0, psc_n.a(array2, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode Acceptable Response Type");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cx psc_cx = new psc_cx();
        if (this.b != null) {
            psc_cx.b = (Vector)this.b.clone();
        }
        if (this.a != null) {
            psc_cx.e();
        }
        super.a(psc_cx);
        return psc_cx;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
    
    static {
        psc_cx.c = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 1 };
    }
}
