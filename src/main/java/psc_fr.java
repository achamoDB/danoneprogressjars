import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fr implements Cloneable, Serializable
{
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 4;
    public static final int e = 5;
    public static final int f = 6;
    public static final int g = 7;
    public static final int h = 8;
    public static final int i = 9;
    public static final int j = 10;
    public static final int k = 11;
    public static final int l = 12;
    public static final int m = 13;
    public static final int n = 14;
    public static final int o = 15;
    public static final int p = 16;
    public static final int q = 17;
    public static final int r = 18;
    public static final int s = 19;
    public static final int t = 20;
    public static final int u = 21;
    public static final int v = 22;
    public static final int w = 23;
    private int x;
    private String y;
    private psc_fs z;
    private String[] aa;
    private int ab;
    private psc_fm ac;
    private psc_fu ad;
    private psc_fv ae;
    private psc_fw af;
    private int ag;
    private psc_ft ah;
    protected int ai;
    protected psc_n aj;
    
    public psc_fr(final byte[] array, final int n, final int ai) throws psc_v {
        this.aa = new String[4];
        this.ab = 0;
        this.aj = null;
        this.ai = ai;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(ai);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(8388608);
            final psc_k psc_k = new psc_k(8453889);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_p, psc_k, psc_j });
            switch (this.x = psc_p.e()) {
                case 1: {
                    final psc_aa psc_aa = new psc_aa(8388609, 1, 64);
                    psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_aa });
                    this.y = psc_aa.e();
                    break;
                }
                case 7: {
                    final psc_aa psc_aa2 = new psc_aa(8388609, 1, 16);
                    psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_aa2 });
                    this.y = psc_aa2.e();
                    break;
                }
                case 2:
                case 3: {
                    final psc_ac psc_ac = new psc_ac(8388609, 1, 64);
                    psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_ac });
                    this.y = psc_ac.e();
                    break;
                }
                case 4: {
                    this.z = new psc_fs(psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 5: {
                    final psc_w psc_w = new psc_w(8388609, 12288, new psc_k(5120));
                    psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
                    final int j = psc_w.j();
                    if (j > 4) {
                        throw new psc_v("Too many entries in TeletexOrganizationalUnitNames: MAX number is 4.");
                    }
                    for (int i = 0; i < j; ++i) {
                        final psc_i a = psc_w.a(i);
                        final psc_ac psc_ac2 = new psc_ac(0, 1, 32);
                        psc_l.a(a.b, a.c, new psc_i[] { psc_ac2 });
                        this.aa[this.ab] = psc_ac2.e();
                        ++this.ab;
                    }
                    break;
                }
                case 6: {
                    this.ah = new psc_ft(psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 8: {
                    this.ac = new psc_fm(4, psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 9: {
                    this.ac = new psc_fm(3, psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21: {
                    this.ad = new psc_fu(psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 16: {
                    this.ae = new psc_fv(psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 22: {
                    this.af = new psc_fw(psc_k.b, psc_k.c, 8388609);
                    break;
                }
                case 23: {
                    final psc_p psc_p2 = new psc_p(8388609);
                    psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_p2 });
                    this.ag = psc_p2.e();
                    if (this.ag < 3 || this.ag > 8) {
                        throw new psc_v("Wrong TerminalType value");
                    }
                    break;
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the ExtensionAttribute.");
        }
    }
    
    public psc_fr() {
        this.aa = new String[4];
        this.ab = 0;
        this.aj = null;
    }
    
    public void a(final int x, final Object o) throws psc_v {
        if (o == null) {
            throw new psc_v("Value is null.");
        }
        if (x > 23 || x < 1) {
            throw new psc_v("Wrong type.");
        }
        switch (this.x = x) {
            case 1: {
                if (o instanceof String && ((String)o).length() < 65) {
                    this.y = (String)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 7: {
                if (o instanceof String && ((String)o).length() < 17) {
                    this.y = (String)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 2:
            case 3: {
                if (o instanceof String && ((String)o).length() < 65) {
                    this.y = (String)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 4: {
                if (o instanceof psc_fs) {
                    this.z = (psc_fs)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 5: {
                if (o instanceof Vector && ((Vector)o).size() < 5) {
                    for (int i = 0; i < ((Vector)o).size(); ++i) {
                        if (!(((Vector)o).elementAt(i) instanceof String)) {
                            throw new psc_v("Wrong value type");
                        }
                        this.aa[this.ab] = (String)((Vector)o).elementAt(i);
                        ++this.ab;
                    }
                    break;
                }
                throw new psc_v("Too many entries in TeletexOrganizationalUnitNames: MAX number is 4.");
            }
            case 6: {
                if (o instanceof psc_ft) {
                    this.ah = (psc_ft)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 8: {
                if (o instanceof psc_fm && ((psc_fm)o).b() == 4) {
                    this.ac = (psc_fm)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 9: {
                if (o instanceof psc_fm && ((psc_fm)o).b() == 3) {
                    this.ac = (psc_fm)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                if (o instanceof psc_fu) {
                    this.ad = (psc_fu)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 16: {
                if (o instanceof psc_fv) {
                    this.ae = (psc_fv)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 22: {
                if (o instanceof psc_fw) {
                    this.af = (psc_fw)o;
                    break;
                }
                throw new psc_v("Wrong value type");
            }
            case 23: {
                if (!(o instanceof Integer)) {
                    throw new psc_v("Wrong value type");
                }
                this.ag = (int)o;
                if (this.ag < 3 || this.ag > 8) {
                    throw new psc_v("Wrong TerminalType value");
                }
                break;
            }
        }
    }
    
    public int b() {
        return this.x;
    }
    
    public Object c() {
        switch (this.x) {
            case 1:
            case 2:
            case 3:
            case 7: {
                return this.y;
            }
            case 4: {
                return this.z;
            }
            case 5: {
                final Vector<String> vector = new Vector<String>();
                for (int i = 0; i < this.ab; ++i) {
                    vector.addElement(this.aa[i]);
                }
                return vector;
            }
            case 6: {
                return this.ah;
            }
            case 8:
            case 9: {
                return this.ac;
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                return this.ad;
            }
            case 16: {
                return this.ae;
            }
            case 22: {
                return this.af;
            }
            case 23: {
                return new Integer(this.ag);
            }
            default: {
                return null;
            }
        }
    }
    
    public String toString() {
        switch (this.x) {
            case 1:
            case 2:
            case 3:
            case 7: {
                return this.y;
            }
            case 4: {
                return this.z.toString();
            }
            case 5: {
                final StringBuffer sb = new StringBuffer();
                for (int i = 0; i < this.ab - 1; ++i) {
                    sb.append(this.aa[i]);
                    sb.append(",");
                }
                sb.append(this.aa[this.ab - 1]);
                return sb.toString();
            }
            case 6: {
                return this.ah.toString();
            }
            case 8:
            case 9: {
                return this.ac.toString();
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                return this.ad.toString();
            }
            case 16: {
                return this.ae.toString();
            }
            case 22: {
                return this.af.toString();
            }
            case 23: {
                return Integer.toString(this.ag);
            }
            default: {
                return null;
            }
        }
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
    
    public int a(final int ai) throws psc_v {
        this.ai = ai;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.aj == null || n2 != this.ai) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode ExtensionAttribute");
            }
            final int a = this.aj.a(array, n);
            this.aj = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.aj = null;
            throw new psc_v("Unable to encode ExtensionAttribute");
        }
    }
    
    private int a() {
        if (this.aj != null && this.ai == this.ai) {
            return 0;
        }
        try {
            final psc_h psc_h = new psc_h(this.ai, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(8388608, true, 0, this.x);
            switch (this.x) {
                case 1: {
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_aa(8388609, true, 0, this.y, 1, 64), psc_j });
                    break;
                }
                case 7: {
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_aa(8388609, true, 0, this.y, 1, 16), psc_j });
                    break;
                }
                case 2:
                case 3: {
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_ac(8388609, true, 0, this.y, 1, 64), psc_j });
                    break;
                }
                case 4: {
                    final byte[] array = new byte[this.z.a(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array, 0, this.z.a(array, 0, 8388609)), psc_j });
                    break;
                }
                case 5: {
                    final psc_w obj = new psc_w(8388609, true, 0, 12288, new psc_k(5120));
                    final Vector vector = new Vector<psc_w>();
                    vector.addElement(obj);
                    for (int i = 0; i < this.ab; ++i) {
                        final psc_n psc_n = new psc_n(new psc_i[] { new psc_ac(0, true, 0, this.aa[i], 1, 32) });
                        final byte[] array2 = new byte[psc_n.a()];
                        obj.b(new psc_k(5120, true, 0, array2, 0, psc_n.a(array2, 0)));
                    }
                    final psc_i[] anArray = new psc_i[vector.size()];
                    vector.copyInto(anArray);
                    final psc_n psc_n2 = new psc_n(anArray);
                    final byte[] array3 = new byte[psc_n2.a()];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(12288, true, 0, array3, 0, psc_n2.a(array3, 0)), psc_j });
                    break;
                }
                case 6: {
                    final byte[] array4 = new byte[this.ah.c(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array4, 0, this.ah.a(array4, 0, 8388609)), psc_j });
                    break;
                }
                case 8:
                case 9: {
                    final byte[] array5 = new byte[this.ac.a(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array5, 0, this.ac.a(array5, 0, 8388609)), psc_j });
                    break;
                }
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21: {
                    final byte[] array6 = new byte[this.ad.a(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array6, 0, this.ad.a(array6, 0, 8388609)), psc_j });
                    break;
                }
                case 16: {
                    final byte[] array7 = new byte[this.ae.a(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array7, 0, this.ae.a(array7, 0, 8388609)), psc_j });
                    break;
                }
                case 22: {
                    final byte[] array8 = new byte[this.af.a(8388609)];
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_k(8453889, true, 0, array8, 0, this.af.a(array8, 0, 8388609)), psc_j });
                    break;
                }
                case 23: {
                    this.aj = new psc_n(new psc_i[] { psc_h, psc_p, new psc_p(8388609, true, 0, this.ag), psc_j });
                    break;
                }
            }
            return this.aj.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fr)) {
            return false;
        }
        final psc_fr psc_fr = (psc_fr)o;
        if (this.x != psc_fr.x) {
            return false;
        }
        switch (this.x) {
            case 1:
            case 2:
            case 3:
            case 7: {
                if (!this.y.equals(psc_fr.y)) {
                    return false;
                }
                break;
            }
            case 4: {
                if (!this.z.equals(psc_fr.z)) {
                    return false;
                }
                break;
            }
            case 5: {
                if (this.ab != psc_fr.ab) {
                    return false;
                }
                for (int i = 0; i < this.ab; ++i) {
                    if (!this.aa[i].equals(psc_fr.aa[i])) {
                        return false;
                    }
                }
                break;
            }
            case 6: {
                if (!this.ah.equals(psc_fr.ah)) {
                    return false;
                }
                break;
            }
            case 8:
            case 9: {
                if (!this.ac.equals(psc_fr.ac)) {
                    return false;
                }
                break;
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                if (!this.ad.equals(psc_fr.ad)) {
                    return false;
                }
                break;
            }
            case 16: {
                if (!this.ae.equals(psc_fr.ae)) {
                    return false;
                }
                break;
            }
            case 22: {
                if (!this.af.equals(psc_fr.af)) {
                    return false;
                }
                break;
            }
            case 23: {
                if (this.ag != psc_fr.ag) {
                    return false;
                }
                break;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fr psc_fr = new psc_fr();
        psc_fr.x = this.x;
        switch (this.x) {
            case 1:
            case 2:
            case 3:
            case 7: {
                psc_fr.y = new String(this.y);
                break;
            }
            case 4: {
                psc_fr.z = (psc_fs)this.z.clone();
                break;
            }
            case 5: {
                psc_fr.ab = this.ab;
                for (int i = 0; i < this.ab; ++i) {
                    psc_fr.aa[i] = new String(this.aa[i]);
                }
                break;
            }
            case 6: {
                psc_fr.ah = (psc_ft)this.ah.clone();
                break;
            }
            case 8:
            case 9: {
                psc_fr.ac = (psc_fm)this.ac.clone();
                break;
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                psc_fr.ad = (psc_fu)this.ad.clone();
                break;
            }
            case 16: {
                psc_fr.ae = (psc_fv)this.ae.clone();
                break;
            }
            case 22: {
                psc_fr.af = (psc_fw)this.af.clone();
                break;
            }
            case 23: {
                psc_fr.ag = this.ag;
                break;
            }
        }
        psc_fr.ai = this.ai;
        if (this.aj != null) {
            psc_fr.a();
        }
        return psc_fr;
    }
}
