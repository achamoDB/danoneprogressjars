import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gq implements Cloneable, Serializable
{
    private Vector[] a;
    protected static int b;
    private psc_n c;
    
    public psc_gq() {
        this.a = this.a(2);
        this.c = null;
    }
    
    public psc_gq(final byte[] array, final int n, final int b) throws psc_g {
        this.a = this.a(2);
        this.c = null;
        psc_gq.b = b;
        if (array == null) {
            throw new psc_g("Data is missing.");
        }
        try {
            final psc_w psc_w = new psc_w(b, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_k psc_k = new psc_k(65280);
                final psc_r psc_r = new psc_r(0);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
                final byte[] obj = new byte[psc_r.d];
                System.arraycopy(psc_r.b, psc_r.c, obj, 0, psc_r.d);
                this.a[0].addElement(obj);
                final byte[] obj2 = new byte[psc_k.d];
                System.arraycopy(psc_k.b, psc_k.c, obj2, 0, psc_k.d);
                this.a[1].addElement(obj2);
            }
        }
        catch (Exception ex) {
            throw new psc_g("Cannot decode the BER of PolicyQualifiers.");
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_g {
        if (array == null || array2 == null) {
            throw new psc_g(" Null data in setPolicyQualifierInfo");
        }
        final byte[] obj = new byte[n2];
        System.arraycopy(array, n, obj, 0, n2);
        this.a[0].addElement(obj);
        final byte[] obj2 = new byte[n4];
        System.arraycopy(array2, n3, obj2, 0, n4);
        this.a[1].addElement(obj2);
    }
    
    public byte[] c(final int index) throws psc_g {
        if (this.a[0].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[0].elementAt(index);
    }
    
    public byte[] d(final int index) throws psc_g {
        if (this.a[1].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[1].elementAt(index);
    }
    
    private Vector[] a(final int n) {
        final Vector[] array = new Vector[n];
        for (int i = 0; i < n; ++i) {
            array[i] = new Vector();
        }
        return array;
    }
    
    public int b() {
        return this.a[0].size();
    }
    
    public static int a(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Unable to determine length of the BER");
        }
    }
    
    public int e(final int b) throws psc_g {
        psc_gq.b = b;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Specified array is null.");
        }
        try {
            if (this.c == null || n2 != psc_gq.b) {
                this.e(n2);
            }
            final int a = this.c.a(array, n);
            this.c = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.c = null;
            throw new psc_g("Unable to encode PolicyQualifiers");
        }
    }
    
    private int a() {
        if (this.c != null && psc_gq.b == psc_gq.b) {
            return 0;
        }
        try {
            final Vector vector = new Vector<psc_w>();
            final psc_w obj = new psc_w(psc_gq.b, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.a[0].size(); ++i) {
                obj.b(this.b(i));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.c = new psc_n(anArray);
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_g psc_g) {
            return 0;
        }
    }
    
    private psc_k b(final int n) throws psc_g {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final byte[] array = this.a[0].elementAt(n);
        final byte[] array2 = this.a[1].elementAt(n);
        psc_k psc_k;
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, new psc_r(16777216, true, 0, array, 0, array.length), new psc_k(65280, true, 0, array2, 0, array2.length), psc_j });
            final byte[] array3 = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array3, 0, psc_n.a(array3, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode PolicyQualifier");
        }
        return psc_k;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gq)) {
            return false;
        }
        final psc_gq psc_gq = (psc_gq)o;
        final int length = this.a.length;
        if (length != psc_gq.a.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final int size = this.a[i].size();
            if (size != psc_gq.a[i].size()) {
                return false;
            }
            for (int j = 0; j < size; ++j) {
                if (!this.a[i].elementAt(j).equals(psc_gq.a[i].elementAt(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gq psc_gq = new psc_gq();
        for (int i = 0; i < this.a.length; ++i) {
            for (int j = 0; j < this.a[i].size(); ++j) {
                psc_gq.a[i].addElement(this.a[i].elementAt(j));
            }
        }
        psc_gq.b = psc_gq.b;
        if (this.c != null) {
            psc_gq.a();
        }
        return psc_gq;
    }
}
