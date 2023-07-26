import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fi implements Cloneable, Serializable
{
    private static final int a = 10485760;
    private byte[] b;
    private byte[] c;
    psc_n d;
    protected static int e;
    
    public psc_fi() {
    }
    
    public psc_fi(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
        }
        if (array2 != null && n4 != 0) {
            System.arraycopy(array2, n3, this.c = new byte[n4], 0, n4);
        }
    }
    
    public void a(final byte[] array, final int n, final int e) throws psc_v {
        psc_fi.e = e;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(e);
            final psc_r psc_r = new psc_r(0);
            final psc_k psc_k = new psc_k(10551040);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_k, new psc_j() });
            this.b = new byte[psc_r.d];
            System.arraycopy(psc_r.b, psc_r.c, this.b, 0, psc_r.d);
            final int n2 = 1 + psc_o.a(psc_k.b, psc_k.c + 1);
            this.c = new byte[psc_k.d - n2];
            System.arraycopy(psc_k.b, psc_k.c + n2, this.c, 0, psc_k.d - n2);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the OtherName.");
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
        }
        if (array2 != null && n4 != 0) {
            System.arraycopy(array2, n3, this.c = new byte[n4], 0, n4);
        }
    }
    
    public byte[] b() {
        return this.b;
    }
    
    public byte[] c() {
        return this.c;
    }
    
    public String toString() {
        return new String(this.c);
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
    
    public int a(final int e) throws psc_v {
        psc_fi.e = e;
        return this.a();
    }
    
    public int b(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.d == null || n2 != psc_fi.e) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode Other Name.");
            }
            final int a = this.d.a(array, n);
            this.d = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.d = null;
            throw new psc_v("Unable to encode Other Name.");
        }
    }
    
    private int a() throws psc_v {
        if (this.c == null || this.b == null) {
            throw new psc_v("Cannot encode OtherName: values are not set.");
        }
        try {
            final int b = psc_o.b(this.c.length);
            final byte[] array = new byte[this.c.length + 1 + b];
            array[0] = -96;
            psc_o.b(array, 1, this.c.length);
            System.arraycopy(this.c, 0, array, b + 1, this.c.length);
            this.d = new psc_n(new psc_i[] { new psc_h(psc_fi.e, true, 0), new psc_r(16777216, true, 0, this.b, 0, this.b.length), new psc_k(65280, true, 0, array, 0, array.length), new psc_j() });
            return this.d.a();
        }
        catch (psc_m psc_m) {
            throw new psc_v(psc_m.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fi)) {
            return false;
        }
        final psc_fi psc_fi = (psc_fi)o;
        return (this.b == null || this.b.equals(psc_fi.b)) && (this.c == null || this.c.equals(psc_fi.c));
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fi psc_fi = new psc_fi();
        psc_fi.b = this.b;
        psc_fi.c = this.c;
        psc_fi.e = psc_fi.e;
        try {
            if (this.d != null) {
                psc_fi.a();
            }
        }
        catch (psc_v psc_v) {
            throw new CloneNotSupportedException("Cannot get ASN1 Template");
        }
        return psc_fi;
    }
}
