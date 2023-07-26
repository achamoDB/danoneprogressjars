import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cr implements Cloneable, Serializable
{
    private Vector a;
    protected int b;
    private psc_n c;
    
    public psc_cr() {
    }
    
    public psc_cr(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.a(array, n, n2);
    }
    
    public static int a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not read the BER encoding.");
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.b();
        try {
            final psc_w psc_w = new psc_w(n2, 12544, new psc_k(12288));
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
            throw new psc_f0("Could not read the BER of the Attributes.");
        }
    }
    
    public int b(final int n) {
        return this.a(n);
    }
    
    private int a(final int b) {
        this.b = b;
        try {
            final psc_w psc_w = new psc_w(b, true, 0, 12544, new psc_k(12288));
            int size = 0;
            if (this.a != null) {
                size = this.a.size();
            }
            if (size == 0) {
                return 2;
            }
            for (int i = 0; i < size; ++i) {
                final psc_fz psc_fz = this.a.elementAt(i);
                final byte[] array = new byte[psc_fz.a(0)];
                psc_w.b(new psc_k(12288, true, 0, array, 0, psc_fz.c(array, 0, 0)));
            }
            this.c = new psc_n(new psc_i[] { psc_w });
            return this.c.a();
        }
        catch (psc_m psc_m) {
            this.c = null;
            return 0;
        }
        catch (psc_f0 psc_f0) {
            this.c = null;
            return 0;
        }
    }
    
    public int b(final byte[] array, final int n, final int n2) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Specified array is null.");
        }
        if ((this.c == null || n2 != this.b) && this.a(n2) == 0) {
            throw new psc_f0("Cannot compute the DER of the Attributes");
        }
        if (this.a == null) {
            array[n] = -96;
            array[n + 1] = 0;
            return 2;
        }
        try {
            return this.c.a(array, n);
        }
        catch (psc_m psc_m) {
            this.c = null;
            return 0;
        }
    }
    
    public int a(final psc_fz o) {
        if (o == null) {
            return -1;
        }
        this.b();
        if (this.a == null) {
            (this.a = new Vector()).addElement(o);
            return 0;
        }
        final psc_fz a = this.a(o.b());
        if (a != null) {
            this.a.removeElement(a);
        }
        this.a.addElement(o);
        return this.a.indexOf(o);
    }
    
    public int a() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }
    
    public psc_fz c(final int index) {
        try {
            if (this.a != null) {
                return this.a.elementAt(index);
            }
            return null;
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    public psc_fz d(final int n) {
        if (this.a == null) {
            return null;
        }
        for (int a = this.a(), i = 0; i < a; ++i) {
            final psc_fz c = this.c(i);
            if (c.c() == n) {
                return c;
            }
        }
        return null;
    }
    
    public psc_fz a(final byte[] array) {
        if (this.a == null) {
            return null;
        }
        for (int a = this.a(), i = 0; i < a; ++i) {
            final psc_fz c = this.c(i);
            if (c.a(array)) {
                return c;
            }
        }
        return null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cr psc_cr = new psc_cr();
        if (this.a != null) {
            psc_cr.a = new Vector();
            for (int size = this.a.size(), i = 0; i < size; ++i) {
                psc_cr.a((psc_fz)this.c(i).clone());
            }
        }
        if (this.c != null) {
            psc_cr.b(this.b);
        }
        return psc_cr;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_cr)) {
            return false;
        }
        final psc_cr psc_cr = (psc_cr)o;
        if (this.a.size() != psc_cr.a.size()) {
            return false;
        }
        try {
            final int b = this.b(0);
            final int b2 = psc_cr.b(0);
            if (b != b2) {
                return false;
            }
            final byte[] array = new byte[b];
            final byte[] array2 = new byte[b2];
            final int b3 = this.b(array, 0, 0);
            if (b3 != psc_cr.b(array2, 0, 0)) {
                return false;
            }
            for (int i = 0; i < b3; ++i) {
                if (array[i] != array2[i]) {
                    return false;
                }
            }
        }
        catch (psc_f0 psc_f0) {
            return false;
        }
        return true;
    }
    
    protected void b() {
        this.c = null;
    }
}
