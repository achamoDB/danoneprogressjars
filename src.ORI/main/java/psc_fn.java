import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fn implements Cloneable, Serializable
{
    private String a;
    private String b;
    private String c;
    private String d;
    protected int e;
    protected psc_n f;
    private static final int g = 8388608;
    private static final int h = 8454145;
    private static final int i = 8454146;
    private static final int j = 8454147;
    
    public psc_fn(final byte[] array, final int n, final int e) throws psc_v {
        this.f = null;
        this.e = e;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_fo psc_fo = new psc_fo(e);
            final psc_aa psc_aa = new psc_aa(8388608, 1, 40);
            final psc_aa psc_aa2 = new psc_aa(8454145, 1, 16);
            final psc_aa psc_aa3 = new psc_aa(8454146, 1, 5);
            final psc_aa psc_aa4 = new psc_aa(8454147, 1, 3);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_aa, psc_aa2, psc_aa3, psc_aa4, new psc_j() });
            if (!psc_aa.a) {
                throw new psc_v("Surname is missing.");
            }
            this.a = psc_aa.e();
            if (psc_aa2.a) {
                this.b = psc_aa2.e();
            }
            if (psc_aa3.a) {
                this.c = psc_aa3.e();
            }
            if (psc_aa4.a) {
                this.d = psc_aa4.e();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the Personal name.");
        }
    }
    
    public psc_fn() {
        this.f = null;
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
    
    public void c(final String c) {
        if (c != null) {
            this.c = c;
        }
    }
    
    public void d(final String d) {
        if (d != null) {
            this.d = d;
        }
    }
    
    public String b() {
        return this.a;
    }
    
    public String c() {
        return this.b;
    }
    
    public String d() {
        return this.c;
    }
    
    public String e() {
        return this.d;
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
        if (this.c != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.c);
        }
        if (this.d != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.d);
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
    
    public int a(final int e) {
        this.e = e;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.f == null || n2 != this.e) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode PersonalName.");
            }
            final int a = this.f.a(array, n);
            this.f = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.f = null;
            throw new psc_v("Unable to encode PersonalName.");
        }
    }
    
    private int a() {
        try {
            int n = 0;
            final psc_fo psc_fo = new psc_fo(this.e, true, 0);
            final psc_j psc_j = new psc_j();
            if (this.a == null) {
                return 0;
            }
            final psc_aa psc_aa = new psc_aa(8388608, true, 0, this.a, 1, 40);
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i psc_i3 = null;
            if (this.b != null) {
                psc_i3 = new psc_aa(8454145, true, 0, this.b, 1, 16);
                n = 1;
            }
            if (this.c != null) {
                psc_i2 = new psc_aa(8454146, true, 0, this.c, 1, 5);
                if (n == 0) {
                    n = 2;
                }
                else {
                    n = 3;
                }
            }
            if (this.d != null) {
                psc_i = new psc_aa(8454147, true, 0, this.d, 1, 3);
                if (n == 0) {
                    n = 4;
                }
                else if (n == 1) {
                    n = 5;
                }
                else if (n == 2) {
                    n = 6;
                }
                else {
                    n = 7;
                }
            }
            switch (n) {
                case 0: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_j });
                    break;
                }
                case 1: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i3, psc_j });
                    break;
                }
                case 2: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i2, psc_j });
                    break;
                }
                case 3: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i3, psc_i2, psc_j });
                    break;
                }
                case 4: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i, psc_j });
                    break;
                }
                case 5: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i3, psc_i, psc_j });
                    break;
                }
                case 6: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i2, psc_i, psc_j });
                    break;
                }
                case 7: {
                    this.f = new psc_n(new psc_i[] { psc_fo, psc_aa, psc_i3, psc_i2, psc_i, psc_j });
                    break;
                }
            }
            return this.f.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fn)) {
            return false;
        }
        final psc_fn psc_fn = (psc_fn)o;
        if (this.a != null) {
            if (!this.a.equals(psc_fn.a)) {
                return false;
            }
        }
        else if (psc_fn.a != null) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_fn.b)) {
                return false;
            }
        }
        else if (psc_fn.b != null) {
            return false;
        }
        if (this.c != null) {
            if (!this.c.equals(psc_fn.c)) {
                return false;
            }
        }
        else if (psc_fn.c != null) {
            return false;
        }
        if (this.d != null) {
            if (!this.d.equals(psc_fn.d)) {
                return false;
            }
        }
        else if (psc_fn.d != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fn psc_fn = new psc_fn();
        if (this.a != null) {
            psc_fn.a = new String(this.a);
        }
        if (this.b != null) {
            psc_fn.b = new String(this.b);
        }
        if (this.c != null) {
            psc_fn.c = new String(this.c);
        }
        if (this.d != null) {
            psc_fn.d = new String(this.d);
        }
        psc_fn.e = this.e;
        if (this.f != null) {
            psc_fn.a();
        }
        return psc_fn;
    }
}
