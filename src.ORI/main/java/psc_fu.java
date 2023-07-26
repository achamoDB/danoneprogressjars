import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fu implements Cloneable, Serializable
{
    private String a;
    private String b;
    protected int c;
    protected psc_n d;
    
    public psc_fu(final byte[] array, final int n, final int c) throws psc_v {
        this.d = null;
        this.c = c;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_fo psc_fo = new psc_fo(c);
            final psc_aa psc_aa = new psc_aa(65536, 1, 30);
            final psc_ac psc_ac = new psc_ac(65536, 1, 30);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_aa, psc_ac, new psc_j() });
            if (psc_aa.a) {
                this.a = psc_aa.e();
            }
            if (psc_ac.a) {
                this.b = psc_ac.e();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the RDSParameter.");
        }
    }
    
    public psc_fu() {
        this.d = null;
    }
    
    public void a(final String a) {
        if (a != null) {
            this.a = a;
        }
    }
    
    public void b(final String b) {
        if (b != null) {
            this.b = b;
        }
    }
    
    public String b() {
        return this.a;
    }
    
    public String c() {
        return this.b;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.a != null) {
            sb.append(this.a);
        }
        if (this.b != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.b);
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
            throw new psc_v("Unable to determine length of the BER");
        }
    }
    
    public int a(final int c) {
        this.c = c;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.d == null || n2 != this.c) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode PDSParameter.");
            }
            final int a = this.d.a(array, n);
            this.d = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.d = null;
            throw new psc_v("Unable to encode PDSParameter.");
        }
    }
    
    private int a() {
        if (this.d != null && this.c == this.c) {
            return 0;
        }
        try {
            int n = 0;
            final psc_fo psc_fo = new psc_fo(this.c, true, 0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            if (this.a != null) {
                psc_i2 = new psc_aa(65536, true, 0, this.a, 1, 30);
                n = 1;
            }
            if (this.b != null) {
                psc_i = new psc_ac(65536, true, 0, this.b, 1, 30);
                if (n == 0) {
                    n = 2;
                }
                else {
                    n = 3;
                }
            }
            switch (n) {
                case 0: {
                    this.d = new psc_n(new psc_i[] { psc_fo, psc_j });
                    break;
                }
                case 1: {
                    this.d = new psc_n(new psc_i[] { psc_fo, psc_i2, psc_j });
                    break;
                }
                case 2: {
                    this.d = new psc_n(new psc_i[] { psc_fo, psc_i, psc_j });
                    break;
                }
                case 3: {
                    this.d = new psc_n(new psc_i[] { psc_fo, psc_i2, psc_i, psc_j });
                    break;
                }
            }
            return this.d.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fu)) {
            return false;
        }
        final psc_fu psc_fu = (psc_fu)o;
        if (this.a != null) {
            if (!this.a.equals(psc_fu.a)) {
                return false;
            }
        }
        else if (psc_fu.a != null) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_fu.b)) {
                return false;
            }
        }
        else if (psc_fu.b != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fu psc_fu = new psc_fu();
        if (this.a != null) {
            psc_fu.a = new String(this.a);
        }
        if (this.b != null) {
            psc_fu.b = new String(this.b);
        }
        psc_fu.c = this.c;
        if (this.d != null) {
            psc_fu.a();
        }
        return psc_fu;
    }
}
