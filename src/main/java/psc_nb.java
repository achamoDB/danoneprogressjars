import java.util.Date;
import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nb implements Cloneable, Serializable
{
    private Vector[] a;
    protected static int b;
    private boolean c;
    private psc_n d;
    
    public psc_nb() {
        this.a = this.a(3);
        this.c = false;
        this.d = null;
    }
    
    public psc_nb(final byte[] array, final int n, final int n2) throws psc_g {
        this.a = this.a(3);
        this.c = false;
        this.d = null;
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_k psc_k = new psc_k(77824);
                final psc_p psc_p = new psc_p(0);
                final psc_k psc_k2 = new psc_k(65280);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_p, psc_k2, psc_k, psc_j });
                final byte[] obj = new byte[psc_p.d];
                System.arraycopy(psc_p.b, psc_p.c, obj, 0, psc_p.d);
                this.a[0].addElement(obj);
                if (psc_k.a) {
                    this.a[2].addElement(new psc_bg(psc_k.b, psc_k.c, 0, 3));
                }
                else {
                    this.a[2].addElement(null);
                }
                final psc_z psc_z = new psc_z(0);
                final psc_ai psc_ai = new psc_ai(65536);
                final psc_ak psc_ak = new psc_ak(65536);
                psc_l.a(psc_k2.b, psc_k2.c, new psc_i[] { psc_z, psc_ai, psc_ak, psc_j });
                if (psc_ai.a) {
                    this.a[1].addElement(psc_ai.a);
                }
                else if (psc_ak.a) {
                    this.a[1].addElement(psc_ak.a);
                }
            }
        }
        catch (Exception ex) {
            throw new psc_g("Cannot decode the BER of Revoked Certificates.");
        }
    }
    
    private Vector[] a(final int n) {
        final Vector[] array = new Vector[n];
        for (int i = 0; i < n; ++i) {
            array[i] = new Vector();
        }
        return array;
    }
    
    public void a(final byte[] obj, final Date obj2, final psc_bg obj3) throws psc_g {
        if (obj == null || obj2 == null) {
            throw new psc_g("Values cannot be NULL.");
        }
        this.a[0].addElement(obj);
        this.a[1].addElement(obj2);
        if (obj3 != null && obj3.b() != 3) {
            throw new psc_g("Wrong extensions type: should be CRLEntry extensions.");
        }
        this.a[2].addElement(obj3);
    }
    
    public byte[] d(final int index) throws psc_g {
        if (index >= this.a()) {
            throw new psc_g("Invalid index");
        }
        return this.a[0].elementAt(index);
    }
    
    public Date e(final int index) throws psc_g {
        if (index >= this.a()) {
            throw new psc_g("Invalid index");
        }
        return this.a[1].elementAt(index);
    }
    
    public psc_bg f(final int index) throws psc_g {
        if (index >= this.a()) {
            throw new psc_g("Invalid index");
        }
        final psc_bg psc_bg = this.a[2].elementAt(index);
        if (psc_bg != null) {
            return psc_bg;
        }
        return null;
    }
    
    public int a() {
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
    
    public int g(final int b) throws psc_g {
        this.b(psc_nb.b = b);
        try {
            return this.d.a();
        }
        catch (psc_m psc_m) {
            throw new psc_g(psc_m.getMessage());
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final boolean c) throws psc_g {
        if (array == null) {
            throw new psc_g("Specified array is null.");
        }
        try {
            this.c = c;
            if (this.d == null || n2 != psc_nb.b) {
                this.g(n2);
            }
            final int a = this.d.a(array, n);
            this.d = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.d = null;
            throw new psc_g("Unable to encode Revoked Certificates.");
        }
    }
    
    private void b(final int n) throws psc_g {
        if (this.d != null && n == psc_nb.b) {
            return;
        }
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(n, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.a[0].size(); ++i) {
                obj.b(this.c(i));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Can't encode RevokedCerts" + psc_m.getMessage());
        }
        final psc_i[] anArray = new psc_i[vector.size()];
        vector.copyInto(anArray);
        this.d = new psc_n(anArray);
    }
    
    private psc_k c(final int index) throws psc_g {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_i psc_i = null;
        psc_k psc_k;
        try {
            if (this.a[2].elementAt(index) != null) {
                final byte[] array = new byte[this.a[2].elementAt(index).b(0)];
                final int n = 0;
                psc_i = new psc_k(77824, true, 0, array, n, this.a[2].elementAt(index).b(array, n, 0));
            }
            final byte[] array2 = this.a[0].elementAt(index);
            final psc_p psc_p = new psc_p(0, true, 0, array2, 0, array2.length, true);
            final Date date = this.a[1].elementAt(index);
            psc_n psc_n;
            if (this.c) {
                final psc_ai psc_ai = new psc_ai(0, true, 0, date);
                if (psc_i != null) {
                    psc_n = new psc_n(new psc_i[] { psc_h, psc_p, psc_ai, psc_i, psc_j });
                }
                else {
                    psc_n = new psc_n(new psc_i[] { psc_h, psc_p, psc_ai, psc_j });
                }
            }
            else {
                final psc_ak psc_ak = new psc_ak(0, true, 0, date);
                if (psc_i != null) {
                    psc_n = new psc_n(new psc_i[] { psc_h, psc_p, psc_ak, psc_i, psc_j });
                }
                else {
                    psc_n = new psc_n(new psc_i[] { psc_h, psc_p, psc_ak, psc_j });
                }
            }
            psc_n.a();
            final byte[] array3 = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array3, 0, psc_n.a(array3, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode RevokedCertificates");
        }
        return psc_k;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nb)) {
            return false;
        }
        final psc_nb psc_nb = (psc_nb)o;
        final int length = this.a.length;
        if (length != psc_nb.a.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final int size = this.a[i].size();
            if (size != psc_nb.a[i].size()) {
                return false;
            }
            for (int j = 0; j < size; ++j) {
                if (!this.a[i].elementAt(j).equals(psc_nb.a[i].elementAt(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_nb psc_nb = new psc_nb();
        for (int i = 0; i < this.a.length; ++i) {
            for (int j = 0; j < this.a[i].size(); ++j) {
                psc_nb.a[i].addElement(this.a[i].elementAt(j));
            }
        }
        psc_nb.b = psc_nb.b;
        try {
            if (this.d != null) {
                psc_nb.b(psc_nb.b);
            }
        }
        catch (psc_g psc_g) {
            throw new CloneNotSupportedException("Cannot get ASN1 Template");
        }
        return psc_nb;
    }
}
