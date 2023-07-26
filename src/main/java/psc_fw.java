import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fw implements Cloneable, Serializable
{
    private String a;
    private String b;
    private psc_fx c;
    protected int d;
    protected psc_n e;
    private static final int f = 8388608;
    private static final int g = 8454145;
    private static final int h = 8388608;
    
    public psc_fw(final byte[] array, final int n, final int d) throws psc_v {
        this.e = null;
        this.d = d;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_z psc_z = new psc_z(d);
            final psc_h psc_h = new psc_h(0);
            final psc_fl psc_fl = new psc_fl(8388608, 1, 15);
            final psc_fl psc_fl2 = new psc_fl(8454145, 1, 40);
            final psc_k psc_k = new psc_k(8400896);
            final psc_j psc_j = new psc_j();
            psc_l.a(array, n, new psc_i[] { psc_z, psc_h, psc_fl, psc_fl2, psc_j, psc_k, psc_j });
            if (psc_k.a) {
                final byte[] array2 = new byte[psc_k.d];
                System.arraycopy(psc_k.b, psc_k.c, array2, 0, psc_k.d);
                this.c = new psc_fx(array2, 0, 8388608);
            }
            else {
                this.a = new String(psc_fl.b, psc_fl.c, psc_fl.d);
                if (psc_fl2.a) {
                    this.b = new String(psc_fl2.b, psc_fl2.c, psc_fl2.d);
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the ExtendedNetworkAddress.");
        }
    }
    
    public psc_fw() {
        this.e = null;
    }
    
    public void a(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 15) {
            throw new psc_v("Specified value are too long.");
        }
        this.a = new String(original);
    }
    
    public void b(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 40) {
            throw new psc_v("Specified value are too long.");
        }
        this.b = new String(original);
    }
    
    public void a(final psc_fx c) throws psc_v {
        if (c == null) {
            throw new psc_v("Specified value is null.");
        }
        this.c = c;
    }
    
    public String b() {
        return this.a;
    }
    
    public String c() {
        return this.b;
    }
    
    public psc_fx d() {
        return this.c;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.c != null) {
            sb.append(this.c.toString());
        }
        else {
            if (this.a != null) {
                sb.append(new String(this.a));
            }
            if (this.b != null) {
                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(new String(this.b));
            }
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
    
    public int a(final int d) {
        this.d = d;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.e == null || n2 != this.d) {
                this.a(n2);
            }
            final int a = this.e.a(array, n);
            this.e = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.e = null;
            throw new psc_v("Unable to encode ExtendedNetworkAddress");
        }
    }
    
    private int a() {
        if (this.e != null && this.d == this.d) {
            return 0;
        }
        try {
            final psc_z psc_z = new psc_z(this.d, 0);
            final psc_j psc_j = new psc_j();
            final psc_h psc_h = new psc_h(0, true, 0);
            if (this.c != null) {
                final byte[] array = new byte[this.c.a(8388608)];
                this.e = new psc_n(new psc_i[] { psc_z, new psc_k(12288, true, 0, array, 0, this.c.e(array, 0, 8388608)), psc_j });
            }
            else {
                final psc_fl psc_fl = new psc_fl(8388608, true, 0, this.a, 1, 15);
                if (this.b != null) {
                    this.e = new psc_n(new psc_i[] { psc_z, psc_h, psc_fl, new psc_fl(8454145, true, 0, this.b, 1, 40), psc_j, psc_j });
                }
                else {
                    this.e = new psc_n(new psc_i[] { psc_z, psc_h, psc_fl, psc_j, psc_j });
                }
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
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fw)) {
            return false;
        }
        final psc_fw psc_fw = (psc_fw)o;
        if (this.a != null) {
            if (!this.a.equals(psc_fw.a)) {
                return false;
            }
        }
        else if (psc_fw.a != null) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_fw.b)) {
                return false;
            }
        }
        else if (psc_fw.b != null) {
            return false;
        }
        if (this.c != null) {
            if (!this.c.equals(psc_fw.c)) {
                return false;
            }
        }
        else if (psc_fw.c != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fw psc_fw = new psc_fw();
        if (this.c != null) {
            psc_fw.c = (psc_fx)this.c.clone();
        }
        else {
            if (this.a != null) {
                psc_fw.a = new String(this.a);
            }
            if (this.b != null) {
                psc_fw.b = new String(this.b);
            }
        }
        psc_fw.d = this.d;
        if (this.e != null) {
            psc_fw.a();
        }
        return psc_fw;
    }
}
