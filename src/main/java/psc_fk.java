import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fk implements Cloneable, Serializable
{
    private psc_fm a;
    private psc_fm b;
    private String c;
    private String d;
    private psc_fm e;
    private String f;
    private String g;
    private psc_fn h;
    private String[] i;
    private int j;
    protected int k;
    protected psc_n l;
    
    public psc_fk(final byte[] array, final int n, final int k) throws psc_v {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = new String[4];
        this.j = 0;
        this.l = null;
        this.k = k;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(k);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(4259841);
            final psc_k psc_k2 = new psc_k(4259842);
            final psc_fl psc_fl = new psc_fl(8454144, 1, 16);
            final psc_aa psc_aa = new psc_aa(8454145, 1, 24);
            final psc_k psc_k3 = new psc_k(8454146);
            final psc_aa psc_aa2 = new psc_aa(8454147, 1, 64);
            final psc_fl psc_fl2 = new psc_fl(8454148, 1, 32);
            final psc_k psc_k4 = new psc_k(8466693);
            final psc_k psc_k5 = new psc_k(8466438);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_k, psc_k2, psc_fl, psc_aa, psc_k3, psc_aa2, psc_fl2, psc_k4, psc_k5, psc_j });
            if (psc_k.a) {
                this.a = new psc_fm(0, psc_k.b, psc_k.c, 0);
            }
            if (psc_k2.a) {
                this.b = new psc_fm(1, psc_k2.b, psc_k2.c, 0);
            }
            if (psc_fl.a) {
                this.c = psc_fl.e();
            }
            if (psc_aa.a) {
                this.d = psc_aa.e();
            }
            if (psc_k3.a) {
                this.e = new psc_fm(2, psc_k3.b, psc_k3.c, 8454146);
            }
            if (psc_aa2.a) {
                this.f = psc_aa2.e();
            }
            if (psc_fl2.a) {
                this.g = psc_fl2.e();
            }
            if (psc_k4.a) {
                this.h = new psc_fn(psc_k4.b, psc_k4.c, 8454149);
            }
            if (psc_k5.a) {
                final psc_w psc_w = new psc_w(8454150, 12288, new psc_k(4864));
                psc_l.a(psc_k5.b, psc_k5.c, new psc_i[] { psc_w });
                final int j = psc_w.j();
                if (j > 4) {
                    throw new psc_v("Too many entries in OrganizationalUnitNanes: MAX number is 4.");
                }
                for (int i = 0; i < j; ++i) {
                    final psc_i a = psc_w.a(i);
                    final psc_aa psc_aa3 = new psc_aa(0, 1, 32);
                    psc_l.a(a.b, a.c, new psc_i[] { psc_aa3 });
                    this.i[this.j] = psc_aa3.e();
                    ++this.j;
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the BuiltInStandardAttributes.");
        }
    }
    
    public psc_fk() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = new String[4];
        this.j = 0;
        this.l = null;
    }
    
    public void a(final psc_fm a) throws psc_v {
        if (a == null) {
            throw new psc_v("Specified value is null.");
        }
        if (a.b() == 0) {
            this.a = a;
            return;
        }
        throw new psc_v("Specified value is of wrong type.");
    }
    
    public void b(final psc_fm b) throws psc_v {
        if (b == null) {
            throw new psc_v("Specified value is null.");
        }
        if (b.b() == 1) {
            this.b = b;
            return;
        }
        throw new psc_v("Specified value is of wrong type.");
    }
    
    public void a(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 16) {
            throw new psc_v("Specified value is too long.");
        }
        this.c = new String(original);
    }
    
    public void b(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 24) {
            throw new psc_v("Specified value is too long.");
        }
        this.d = new String(original);
    }
    
    public void c(final psc_fm e) throws psc_v {
        if (e == null) {
            throw new psc_v("Specified value is null.");
        }
        if (e.b() == 2) {
            this.e = e;
            return;
        }
        throw new psc_v("Specified value is of wrong type.");
    }
    
    public void c(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 64) {
            throw new psc_v("Specified value is too long.");
        }
        this.f = new String(original);
    }
    
    public void d(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 32) {
            throw new psc_v("Specified value is too long.");
        }
        this.g = new String(original);
    }
    
    public void a(final psc_fn h) {
        if (h != null) {
            this.h = h;
        }
    }
    
    public void e(final String original) throws psc_v {
        if (original == null) {
            throw new psc_v("Specified value is null.");
        }
        if (original.length() > 32) {
            throw new psc_v("Specified value is too long.");
        }
        if (this.j == 4) {
            throw new psc_v("Cannot add Organizational Unit Names attribute: MAX number is 4");
        }
        this.i[this.j] = new String(original);
        ++this.j;
    }
    
    public psc_fm c() {
        return this.a;
    }
    
    public psc_fm d() {
        return this.b;
    }
    
    public String e() {
        return this.c;
    }
    
    public String f() {
        return this.d;
    }
    
    public psc_fm g() {
        return this.e;
    }
    
    public String h() {
        return this.f;
    }
    
    public String i() {
        return this.g;
    }
    
    public psc_fn j() {
        return this.h;
    }
    
    public String[] k() {
        return this.i;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.a != null) {
            sb.append(this.a.toString());
        }
        if (this.b != null) {
            sb.append(",");
            sb.append(this.b.toString());
        }
        if (this.c != null) {
            sb.append(",");
            sb.append(this.c);
        }
        if (this.d != null) {
            sb.append(",");
            sb.append(this.d);
        }
        if (this.e != null) {
            sb.append(",");
            sb.append(this.e.toString());
        }
        if (this.f != null) {
            sb.append(",");
            sb.append(this.f);
        }
        if (this.g != null) {
            sb.append(",");
            sb.append(this.g);
        }
        if (this.h != null) {
            sb.append(",");
            sb.append(this.h.toString());
        }
        for (int i = 0; i < this.j; ++i) {
            sb.append(",");
            sb.append(this.i[i]);
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
    
    public int a(final int k) throws psc_v {
        this.k = k;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.l == null || n2 != this.k) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode BuiltInStandardAttributes");
            }
            final int a = this.l.a(array, n);
            this.l = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.l = null;
            throw new psc_v("Unable to encode BuiltInStandardAttributes");
        }
    }
    
    private int a() {
        if (this.l != null && this.k == this.k) {
            return 0;
        }
        try {
            final psc_h obj = new psc_h(0, true, 0);
            final psc_j obj2 = new psc_j();
            final Vector vector = new Vector<psc_h>();
            vector.addElement(obj);
            if (this.a != null) {
                final byte[] array = new byte[this.a.a(65536)];
                vector.addElement((psc_h)new psc_k(65536, true, 0, array, 0, this.a.a(array, 0, 65536)));
            }
            if (this.b != null) {
                final byte[] array2 = new byte[this.b.a(65536)];
                vector.addElement((psc_h)new psc_k(65536, true, 0, array2, 0, this.b.a(array2, 0, 65536)));
            }
            if (this.c != null) {
                vector.addElement((psc_h)new psc_fl(8454144, true, 0, this.c, 1, 16));
            }
            if (this.d != null) {
                vector.addElement((psc_h)new psc_aa(8454145, true, 0, this.d, 1, 24));
            }
            if (this.e != null) {
                final byte[] array3 = new byte[this.e.a(8454146)];
                vector.addElement((psc_h)new psc_k(8454146, true, 0, array3, 0, this.e.a(array3, 0, 8454146)));
            }
            if (this.f != null) {
                vector.addElement((psc_h)new psc_aa(8454147, true, 0, this.f, 1, 64));
            }
            if (this.g != null) {
                vector.addElement((psc_h)new psc_fl(8454148, true, 0, this.g, 1, 32));
            }
            if (this.h != null) {
                final byte[] array4 = new byte[this.h.a(8454149)];
                vector.addElement((psc_h)new psc_k(8454149, true, 0, array4, 0, this.h.a(array4, 0, 8454149)));
            }
            if (this.j != 0) {
                vector.addElement((psc_h)this.b());
            }
            vector.addElement((psc_h)obj2);
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.l = new psc_n(anArray);
            return this.l.a();
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
            final psc_w obj = new psc_w(8454150, true, 0, 12288, new psc_k(4864));
            vector.addElement(obj);
            for (int i = 0; i < this.j; ++i) {
                final psc_n psc_n = new psc_n(new psc_i[] { new psc_aa(0, true, 0, this.i[i], 1, 32) });
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
            throw new psc_v(" Can't encode Organizational Unit Names.");
        }
        return psc_k;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fk)) {
            return false;
        }
        final psc_fk psc_fk = (psc_fk)o;
        if (this.j != psc_fk.j) {
            return false;
        }
        if (this.a != null) {
            if (!this.a.equals(psc_fk.a)) {
                return false;
            }
        }
        else if (psc_fk.a != null) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_fk.b)) {
                return false;
            }
        }
        else if (psc_fk.b != null) {
            return false;
        }
        if (this.c != null) {
            if (!this.c.equals(psc_fk.c)) {
                return false;
            }
        }
        else if (psc_fk.c != null) {
            return false;
        }
        if (this.d != null) {
            if (!this.d.equals(psc_fk.d)) {
                return false;
            }
        }
        else if (psc_fk.d != null) {
            return false;
        }
        if (this.e != null) {
            if (!this.e.equals(psc_fk.e)) {
                return false;
            }
        }
        else if (psc_fk.e != null) {
            return false;
        }
        if (this.f != null) {
            if (!this.f.equals(psc_fk.f)) {
                return false;
            }
        }
        else if (psc_fk.f != null) {
            return false;
        }
        if (this.g != null) {
            if (!this.g.equals(psc_fk.g)) {
                return false;
            }
        }
        else if (psc_fk.g != null) {
            return false;
        }
        if (this.h != null) {
            if (!this.h.equals(psc_fk.h)) {
                return false;
            }
        }
        else if (psc_fk.h != null) {
            return false;
        }
        for (int i = 0; i < this.j; ++i) {
            if (!this.i[i].equals(psc_fk.i[i])) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fk psc_fk = new psc_fk();
        psc_fk.j = this.j;
        if (this.a != null) {
            psc_fk.a = (psc_fm)this.a.clone();
        }
        if (this.b != null) {
            psc_fk.b = (psc_fm)this.b.clone();
        }
        if (this.c != null) {
            psc_fk.c = new String(this.c);
        }
        if (this.d != null) {
            psc_fk.d = new String(this.d);
        }
        if (this.e != null) {
            psc_fk.e = (psc_fm)this.e.clone();
        }
        if (this.f != null) {
            psc_fk.f = new String(this.f);
        }
        if (this.g != null) {
            psc_fk.g = new String(this.g);
        }
        if (this.h != null) {
            psc_fk.h = (psc_fn)this.h.clone();
        }
        for (int i = 0; i < this.j; ++i) {
            psc_fk.i[i] = new String(this.i[i]);
        }
        psc_fk.k = this.k;
        if (this.l != null) {
            psc_fk.a();
        }
        return psc_fk;
    }
}
