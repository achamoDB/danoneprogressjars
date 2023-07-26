import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gi extends psc_fz implements Cloneable, Serializable
{
    private Vector a;
    private Vector b;
    psc_n c;
    
    public psc_gi() {
        super(16, "postalAddress");
        this.a = new Vector();
        this.b = new Vector();
    }
    
    public psc_gi(final String s, final int n) throws psc_f0 {
        this();
        this.a(s, n);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_k, psc_j });
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(65280));
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
            for (int i = 0; i < psc_w.j(); ++i) {
                final psc_k psc_k2 = (psc_k)psc_w.a(i);
                final psc_z psc_z = new psc_z(0);
                final psc_aa psc_aa = new psc_aa(0, 1, -1);
                final psc_ac psc_ac = new psc_ac(0, 1, -1);
                final psc_ad psc_ad = new psc_ad(0, 1, -1);
                final psc_ae psc_ae = new psc_ae(0, 1, -1);
                final psc_af psc_af = new psc_af(0);
                final psc_ag psc_ag = new psc_ag(0, 1, -1);
                psc_l.a(psc_k2.b, psc_k2.c, new psc_i[] { psc_z, psc_aa, psc_ac, psc_ad, psc_ae, psc_af, psc_ag, psc_j });
                if (psc_aa.a) {
                    this.b.addElement(new Integer(4864));
                    this.a.addElement(psc_aa.e());
                }
                else if (psc_ac.a) {
                    this.b.addElement(new Integer(5120));
                    this.a.addElement(psc_ac.e());
                }
                else if (psc_ad.a) {
                    this.b.addElement(new Integer(7168));
                    this.a.addElement(psc_ad.e());
                }
                else if (psc_ae.a) {
                    this.b.addElement(new Integer(7680));
                    this.a.addElement(psc_ae.e());
                }
                else if (psc_af.a) {
                    this.b.addElement(new Integer(3072));
                    this.a.addElement(this.e(psc_af.b, psc_af.c, psc_af.d));
                }
                else {
                    if (!psc_ag.a) {
                        throw new psc_f0("DirectoryString expected.");
                    }
                    this.b.addElement(new Integer(5632));
                    this.a.addElement(psc_ag.e());
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode postalAddress." + psc_m.getMessage());
        }
    }
    
    public void a(final String obj, final int value) throws psc_f0 {
        if (obj == null) {
            throw new psc_f0("PostalAddress is null.");
        }
        this.a.addElement(obj);
        if (value != 4864 && value != 5120 && value != 7168 && value != 3072 && value != 7680 && value != 5632) {
            throw new psc_f0("Invalid String Type.");
        }
        this.b.addElement(new Integer(value));
    }
    
    public String[] g() {
        final String[] array = new String[this.a.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (String)this.a.elementAt(i);
        }
        return array;
    }
    
    protected int d() {
        this.c = null;
        if (this.a == null) {
            return 0;
        }
        super.ab = super.ab;
        try {
            final psc_w psc_w = new psc_w(0, true, 0, 12288, new psc_k(65280));
            final psc_j psc_j = new psc_j();
            final psc_fo psc_fo = new psc_fo(0, true, 0);
            final psc_z psc_z = new psc_z(0);
            final psc_k psc_k = new psc_k(65280);
            for (int i = 0; i < this.a.size(); ++i) {
                int intValue = this.b.elementAt(i);
                if (intValue == 0) {
                    intValue = 3072;
                }
                psc_i psc_i = null;
                switch (intValue) {
                    case 5120: {
                        psc_i = new psc_ac(0, true, 0, this.a.elementAt(i));
                        break;
                    }
                    case 4864: {
                        psc_i = new psc_aa(0, true, 0, this.a.elementAt(i));
                        break;
                    }
                    case 7168: {
                        psc_i = new psc_ad(0, true, 0, this.a.elementAt(i));
                        break;
                    }
                    case 5632: {
                        psc_i = new psc_ag(0, true, 0, this.a.elementAt(i));
                        break;
                    }
                    case 3072: {
                        final byte[] a = this.a(this.a.elementAt(i));
                        if (a.length < 2) {
                            return 0;
                        }
                        psc_i = new psc_af(0, true, 0, a, 2, a.length - 2);
                        break;
                    }
                    case 7680: {
                        psc_i = new psc_ae(0, true, 0, this.a.elementAt(i));
                        break;
                    }
                    default: {
                        return 0;
                    }
                }
                final psc_n psc_n = new psc_n(new psc_i[] { psc_z, psc_i, psc_j });
                final byte[] array = new byte[psc_n.a()];
                psc_w.b(new psc_k(0, true, 0, array, 0, psc_n.a(array, 0)));
            }
            this.c = new psc_n(new psc_i[] { psc_fo, psc_w, psc_j });
            return this.c.a();
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    protected int c(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.c == null && this.d() == 0) {
            return 0;
        }
        try {
            final int a = this.c.a(array, n);
            super.ac = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.ac = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gi psc_gi = new psc_gi();
        for (int i = 0; i < this.a.size(); ++i) {
            psc_gi.a.addElement(this.a.elementAt(i));
            psc_gi.b.addElement(this.b.elementAt(i));
        }
        super.a(psc_gi);
        return psc_gi;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gi)) {
            return false;
        }
        final psc_gi psc_gi = (psc_gi)o;
        if (this.a.size() != psc_gi.a.size()) {
            return false;
        }
        for (int i = 0; i < this.a.size(); ++i) {
            if (!((String)this.a.elementAt(i)).equals(psc_gi.a.elementAt(i))) {
                return false;
            }
            if (!((Integer)this.b.elementAt(i)).equals(psc_gi.b.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    protected void e() {
        super.e();
        this.a = new Vector();
        this.b = new Vector();
        this.c = null;
    }
}
