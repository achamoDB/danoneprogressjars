import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fv implements Cloneable, Serializable
{
    private String[] a;
    private int b;
    private String c;
    protected int d;
    protected psc_n e;
    
    public psc_fv(final byte[] array, final int n, final int d) throws psc_v {
        this.a = new String[6];
        this.b = 0;
        this.e = null;
        this.d = d;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_fo psc_fo = new psc_fo(d);
            final psc_k psc_k = new psc_k(77824);
            final psc_ac psc_ac = new psc_ac(65536, 1, 180);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_k, psc_ac, new psc_j() });
            if (psc_k.a) {
                final psc_w psc_w = new psc_w(65536, 12288, new psc_k(4864));
                psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
                final int j = psc_w.j();
                if (j > 6) {
                    throw new psc_v("Too many entries in PrintableAddress: MAX number is 6.");
                }
                for (int i = 0; i < j; ++i) {
                    final psc_i a = psc_w.a(i);
                    final psc_aa psc_aa = new psc_aa(0, 1, 30);
                    psc_l.a(a.b, a.c, new psc_i[] { psc_aa });
                    this.a[this.b] = psc_aa.e();
                    ++this.b;
                }
            }
            if (psc_ac.a) {
                this.c = psc_ac.e();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the UnformattedPostalAddress.");
        }
    }
    
    public psc_fv() {
        this.a = new String[6];
        this.b = 0;
        this.e = null;
    }
    
    public void a(final String s) throws psc_v {
        if (s == null) {
            throw new psc_v("Specified value is null.");
        }
        if (s.length() > 30) {
            throw new psc_v("Specified value is too long.");
        }
        if (this.b < 5) {
            this.a[this.b] = s;
            ++this.b;
            return;
        }
        throw new psc_v("Cannot add PrintableAddress: MAX  number is 6.");
    }
    
    public void b(final String c) throws psc_v {
        if (c == null) {
            throw new psc_v("Specified value is null.");
        }
        if (c.length() > 180) {
            throw new psc_v("Specified value is too long.");
        }
        this.c = c;
    }
    
    public String[] c() {
        return this.a;
    }
    
    public String d() {
        return this.c;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.b; ++i) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(this.a[i]));
        }
        if (this.c != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(this.c));
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
    
    public int a(final int d) throws psc_v {
        this.d = d;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.e == null || n2 != this.d) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode UnformattedPostalAddress");
            }
            final int a = this.e.a(array, n);
            this.e = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.e = null;
            throw new psc_v("Unable to encode UnformattedPostalAddress");
        }
    }
    
    private int a() {
        if (this.e != null && this.d == this.d) {
            return 0;
        }
        try {
            final psc_fo psc_fo = new psc_fo(this.d, true, 0);
            final psc_j psc_j = new psc_j();
            if (this.b > 0) {
                final psc_k b = this.b();
                if (this.c != null) {
                    this.e = new psc_n(new psc_i[] { psc_fo, b, new psc_ac(65536, true, 0, this.c, 1, 180), psc_j });
                }
                else {
                    this.e = new psc_n(new psc_i[] { psc_fo, b, psc_j });
                }
            }
            else if (this.c != null) {
                this.e = new psc_n(new psc_i[] { psc_fo, new psc_ac(65536, true, 0, this.c, 1, 180), psc_j });
            }
            else {
                this.e = new psc_n(new psc_i[] { psc_fo, psc_j });
            }
            return this.e.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    private psc_k b() throws psc_v {
        final Vector vector = new Vector<psc_w>();
        psc_k psc_k;
        try {
            final psc_w obj = new psc_w(65536, true, 0, 12288, new psc_k(4864));
            vector.addElement(obj);
            for (int i = 0; i < this.b; ++i) {
                final psc_n psc_n = new psc_n(new psc_i[] { new psc_aa(0, true, 0, this.a[i], 1, 30) });
                final byte[] array = new byte[psc_n.a()];
                obj.b(new psc_k(4864, true, 0, array, 0, psc_n.a(array, 0)));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            final psc_n psc_n2 = new psc_n(anArray);
            final byte[] array2 = new byte[psc_n2.a()];
            psc_k = new psc_k(12288, true, 0, array2, 0, psc_n2.a(array2, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_v(" Can't encode PrintAddress");
        }
        return psc_k;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fv)) {
            return false;
        }
        final psc_fv psc_fv = (psc_fv)o;
        if (this.b != psc_fv.b) {
            return false;
        }
        if (this.c != null) {
            if (!this.c.equals(psc_fv.c)) {
                return false;
            }
        }
        else if (psc_fv.c != null) {
            return false;
        }
        for (int i = 0; i < this.b; ++i) {
            if (!this.a[i].equals(psc_fv.a[i])) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fv psc_fv = new psc_fv();
        psc_fv.b = this.b;
        if (this.c != null) {
            psc_fv.c = this.c;
        }
        for (int i = 0; i < this.b; ++i) {
            psc_fv.a[i] = this.a[i];
        }
        psc_fv.d = this.d;
        if (this.e != null) {
            psc_fv.a();
        }
        return psc_fv;
    }
}
