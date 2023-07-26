import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fj implements Cloneable, Serializable
{
    private psc_fk a;
    private psc_fp b;
    private psc_fq c;
    protected int d;
    protected psc_n e;
    
    public psc_fj(final byte[] array, final int n, final int d) throws psc_v {
        this.e = null;
        this.d = d;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(d);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(77824);
            final psc_k psc_k3 = new psc_k(78080);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_k, psc_k2, psc_k3, psc_j });
            this.a = new psc_fk(psc_k.b, psc_k.c, 0);
            if (psc_k2.a) {
                this.b = new psc_fp(psc_k2.b, psc_k2.c, 0);
            }
            if (psc_k3.a) {
                this.c = new psc_fq(psc_k3.b, psc_k3.c, 0);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the ORAddress." + psc_m.getMessage());
        }
    }
    
    public psc_fj() {
        this.e = null;
    }
    
    public void a(final psc_fk a) {
        if (a != null) {
            this.a = a;
        }
    }
    
    public void a(final psc_fp b) {
        if (b != null) {
            this.b = b;
        }
    }
    
    public void a(final psc_fq c) {
        if (c != null) {
            this.c = c;
        }
    }
    
    public psc_fk b() {
        return this.a;
    }
    
    public psc_fp c() {
        return this.b;
    }
    
    public psc_fq d() {
        return this.c;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.a != null) {
            sb.append(this.a.toString());
        }
        if (this.b != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.b.toString());
        }
        if (this.c != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.c.toString());
        }
        return sb.toString();
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Unable to determine length of the BER" + psc_m.getMessage());
        }
    }
    
    public int a(final int d) {
        this.d = d;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.e == null || n2 != this.d) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode ORAddress");
            }
            final int a = this.e.a(array, n);
            this.e = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.e = null;
            throw new psc_v("Unable to encode ORAddress" + psc_m.getMessage());
        }
    }
    
    private int a() {
        if (this.e != null && this.d == this.d) {
            return 0;
        }
        try {
            final psc_h psc_h = new psc_h(this.d, true, 0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            int n = 0;
            if (this.a == null) {
                return 0;
            }
            final byte[] array = new byte[this.a.a(0)];
            final psc_k psc_k = new psc_k(12288, true, 0, array, 0, this.a.a(array, 0, 0));
            if (this.b != null) {
                final byte[] array2 = new byte[this.b.c(65536)];
                psc_i = new psc_k(77824, true, 0, array2, 0, this.b.a(array2, 0, 65536));
                n = 1;
            }
            if (this.c != null) {
                final byte[] array3 = new byte[this.c.b(65536)];
                psc_i2 = new psc_k(78080, true, 0, array3, 0, this.c.a(array3, 0, 65536));
                if (n == 1) {
                    n = 2;
                }
                else {
                    n = 3;
                }
            }
            switch (n) {
                case 0: {
                    this.e = new psc_n(new psc_i[] { psc_h, psc_k, psc_j });
                    break;
                }
                case 1: {
                    this.e = new psc_n(new psc_i[] { psc_h, psc_k, psc_i, psc_j });
                    break;
                }
                case 2: {
                    this.e = new psc_n(new psc_i[] { psc_h, psc_k, psc_i, psc_i2, psc_j });
                    break;
                }
                case 3: {
                    this.e = new psc_n(new psc_i[] { psc_h, psc_k, psc_i2, psc_j });
                    break;
                }
            }
            return this.e.a();
        }
        catch (psc_v psc_v) {
            return 0;
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fj)) {
            return false;
        }
        final psc_fj psc_fj = (psc_fj)o;
        if (this.a != null) {
            if (!this.a.equals(psc_fj.a)) {
                return false;
            }
        }
        else if (psc_fj.a != null) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_fj.b)) {
                return false;
            }
        }
        else if (psc_fj.b != null) {
            return false;
        }
        if (this.c != null) {
            if (!this.c.equals(psc_fj.c)) {
                return false;
            }
        }
        else if (psc_fj.c != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fj psc_fj = new psc_fj();
        if (this.a != null) {
            psc_fj.a = (psc_fk)this.a.clone();
        }
        if (this.b != null) {
            psc_fj.b = (psc_fp)this.b.clone();
        }
        if (this.c != null) {
            psc_fj.c = (psc_fq)this.c.clone();
        }
        psc_fj.d = this.d;
        if (this.e != null) {
            psc_fj.a();
        }
        return psc_fj;
    }
}
