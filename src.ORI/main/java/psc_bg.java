import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bg implements Cloneable, Serializable
{
    protected Vector a;
    protected int b;
    private int c;
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 3;
    public static final int g = 4;
    public static final int h = 5;
    private int i;
    private int j;
    private psc_n k;
    
    public psc_bg(final int c) throws psc_g {
        this.i = 1;
        this.j = 5;
        if (c > this.j || c < this.i) {
            throw new psc_g("Invalid extensions type.");
        }
        this.c = c;
    }
    
    public psc_bg(final byte[] array, final int n, final int n2, final int c) throws psc_g {
        this.i = 1;
        this.j = 5;
        if (c > this.j || c < this.i) {
            throw new psc_g("Invalid extensions type.");
        }
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.c = c;
        this.a(array, n, n2);
    }
    
    public static int a(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            return n + psc_o.b(array, n);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER encoding.");
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.c();
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            final int j = psc_w.j();
            if (j > 0) {
                this.a = new Vector();
            }
            for (int i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_aj a2 = psc_aj.a(a.b, a.c);
                switch (this.c) {
                    case 1: {
                        if (!(a2 instanceof psc_bj)) {
                            throw new psc_g("Extension of the wrong type");
                        }
                        break;
                    }
                    case 2: {
                        if (!(a2 instanceof psc_bl)) {
                            throw new psc_g("Extension of the wrong type");
                        }
                        break;
                    }
                    case 3: {
                        if (!(a2 instanceof psc_b1)) {
                            throw new psc_g("Extension of the wrong type");
                        }
                        break;
                    }
                    case 4: {
                        if (!(a2 instanceof psc_b2)) {
                            throw new psc_g("Extension of the wrong type");
                        }
                        break;
                    }
                    case 5: {
                        if (!(a2 instanceof psc_cw)) {
                            throw new psc_g("Extension of the wrong type");
                        }
                        break;
                    }
                }
                if (this.d(a2.c()) != null && a2.c() != -1) {
                    throw new psc_g("Extension of " + a2.d() + " type already exists.");
                }
                this.a.addElement(a2);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER of the Extensions.");
        }
    }
    
    public int b(final int n) {
        return this.a(n);
    }
    
    private int a(final int b) {
        this.b = b;
        try {
            final psc_w psc_w = new psc_w(b, true, 0, 12288, new psc_k(12288));
            int size = 0;
            if (this.a != null) {
                size = this.a.size();
            }
            for (int i = 0; i < size; ++i) {
                psc_w.b(new psc_k(12288, true, 0, null, 0, ((psc_aj)this.a.elementAt(i)).e(0)));
            }
            this.k = new psc_n(new psc_i[] { psc_w });
            return this.k.a();
        }
        catch (psc_m psc_m) {
            this.k = null;
            return 0;
        }
    }
    
    public int b(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return 0;
        }
        if ((this.k == null || n2 != this.b) && this.a(n2) == 0) {
            return 0;
        }
        final int n3 = 0;
        int n4;
        try {
            n4 = n3 + this.k.a(array, n);
            this.k = null;
        }
        catch (psc_m psc_m) {
            this.k = null;
            return 0;
        }
        int size = 0;
        if (this.a != null) {
            size = this.a.size();
        }
        for (int i = 0; i < size; ++i) {
            n4 += ((psc_aj)this.a.elementAt(i)).d(array, n + n4, 0);
        }
        return n4;
    }
    
    public int a(final psc_aj psc_aj) throws psc_g {
        if (psc_aj == null) {
            throw new psc_g("Specified extension is null.");
        }
        this.c();
        if (this.a == null) {
            this.a = new Vector();
        }
        switch (this.c) {
            case 1: {
                if (!(psc_aj instanceof psc_bj)) {
                    throw new psc_g("Extension of the wrong type");
                }
                break;
            }
            case 2: {
                if (!(psc_aj instanceof psc_bl)) {
                    throw new psc_g("Extension of the wrong type");
                }
                break;
            }
            case 3: {
                if (!(psc_aj instanceof psc_b1)) {
                    throw new psc_g("Extension of the wrong type");
                }
                break;
            }
        }
        if (this.d(psc_aj.c()) == null || psc_aj.c() == -1) {
            this.a.addElement(psc_aj);
            return this.a.indexOf(psc_aj);
        }
        throw new psc_g("Extension of " + psc_aj.d() + " type already exists.");
    }
    
    public int a() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }
    
    public int b() {
        return this.c;
    }
    
    public psc_aj c(final int index) throws psc_g {
        if (this.a == null) {
            throw new psc_g(" There is no extensions.");
        }
        if (index < this.a()) {
            return this.a.elementAt(index);
        }
        throw new psc_g("Invalid index");
    }
    
    public psc_aj d(final int n) throws psc_g {
        if (this.a == null) {
            throw new psc_g("There is no extensions.");
        }
        final int a = this.a();
        try {
            for (int i = 0; i < a; ++i) {
                final psc_aj c = this.c(i);
                if (c.c() == n) {
                    return (psc_aj)c.clone();
                }
            }
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Clone Exception");
        }
        return null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        try {
            final psc_bg psc_bg = new psc_bg(this.c);
            if (this.a != null) {
                psc_bg.a = new Vector();
                for (int size = this.a.size(), i = 0; i < size; ++i) {
                    psc_bg.a((psc_aj)this.c(i).clone());
                }
            }
            if (this.k != null) {
                psc_bg.b(this.b);
            }
            return psc_bg;
        }
        catch (psc_g psc_g) {
            throw new CloneNotSupportedException("Wrong Extensions type.");
        }
    }
    
    protected void c() {
        this.k = null;
    }
}
