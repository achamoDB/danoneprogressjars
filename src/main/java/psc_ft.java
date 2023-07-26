import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ft implements Cloneable, Serializable
{
    private String[][] a;
    int b;
    protected static int c;
    private psc_n d;
    
    public psc_ft() {
        this.a = new String[4][2];
        this.b = 0;
        this.d = null;
    }
    
    public psc_ft(final byte[] array, final int n, final int n2) throws psc_v {
        this.a = new String[4][2];
        this.b = 0;
        this.d = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            final int j = psc_w.j();
            if (j > 4) {
                throw new psc_v("Too many TeletexDomainDefinedAttributes: MAX number is 4.");
            }
            for (int i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_ac psc_ac = new psc_ac(0, 1, 8);
                final psc_ac psc_ac2 = new psc_ac(0, 1, 128);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_ac, psc_ac2, psc_j });
                this.a[i][0] = psc_ac.e();
                this.a[i][1] = psc_ac2.e();
                ++this.b;
            }
        }
        catch (Exception ex) {
            throw new psc_v("Cannot decode the BER of TeletexDomainDefinedAttributes.");
        }
    }
    
    public void a(final String original, final String original2) throws psc_v {
        if (original == null || original2 == null) {
            throw new psc_v("Specified values are null.");
        }
        if (original.length() > 8 || original2.length() > 128) {
            throw new psc_v("Specified values are too long.");
        }
        if (this.b < 3) {
            this.a[this.b][0] = new String(original);
            this.a[this.b][1] = new String(original2);
            ++this.b;
            return;
        }
        throw new psc_v("Cannot add Attribute: MAX attribute number is 4.");
    }
    
    public String[] b(final int n) throws psc_v {
        if (n > 3) {
            throw new psc_v("Specified index is invalid.");
        }
        return new String[] { new String(this.a[n][0]), new String(this.a[n][1]) };
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.b; ++i) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(this.a[i][0]));
            sb.append(",");
            sb.append(new String(this.a[i][1]));
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
    
    public int c(final int c) throws psc_v {
        psc_ft.c = c;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.d == null || n2 != psc_ft.c) {
                this.c(n2);
            }
            final int a = this.d.a(array, n);
            this.d = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.d = null;
            throw new psc_v("Unable to encode TeletexDomainDefinedAttributes");
        }
    }
    
    private int a() throws psc_v {
        if (this.d != null && psc_ft.c == psc_ft.c) {
            return 0;
        }
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(psc_ft.c, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.b; ++i) {
                try {
                    obj.b(this.a(i));
                }
                catch (psc_m psc_m2) {
                    throw new psc_v("Can't encode TeletexDomainDefinedAttributes");
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.d = new psc_n(anArray);
            return this.d.a();
        }
        catch (psc_m psc_m) {
            throw new psc_v(psc_m.getMessage());
        }
    }
    
    private psc_k a(final int n) throws psc_v {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_k psc_k;
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, new psc_ac(0, true, 0, this.a[n][0], 1, 8), new psc_ac(0, true, 0, this.a[n][1], 1, 128), psc_j });
            final byte[] array = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array, 0, psc_n.a(array, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_v(" Can't encode TeletexDomainDefinedAttribute");
        }
        return psc_k;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_ft)) {
            return false;
        }
        final psc_ft psc_ft = (psc_ft)o;
        if (this.b != psc_ft.b) {
            return false;
        }
        for (int i = 0; i < this.b; ++i) {
            if (!this.a[i][0].equals(psc_ft.a[i][0])) {
                return false;
            }
            if (!this.a[i][1].equals(psc_ft.a[i][1])) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ft psc_ft = new psc_ft();
        psc_ft.b = this.b;
        for (int i = 0; i < this.b; ++i) {
            psc_ft.a[i][0] = new String(this.a[i][0]);
            psc_ft.a[i][1] = new String(this.a[i][1]);
        }
        psc_ft.c = psc_ft.c;
        try {
            if (this.d != null) {
                psc_ft.a();
            }
        }
        catch (psc_v psc_v) {
            throw new CloneNotSupportedException("Cannot get ASN1 Template");
        }
        return psc_ft;
    }
}
