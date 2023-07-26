import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fy implements Cloneable, Serializable
{
    private static final int a = 10551296;
    private static final int b = 10485761;
    private String c;
    private int d;
    private String e;
    private int f;
    protected static int g;
    private psc_n h;
    
    public psc_fy(final byte[] array, final int n, final int n2) throws psc_v {
        this.c = null;
        this.d = 0;
        this.e = null;
        this.f = 0;
        this.h = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_z psc_z = new psc_z(10551296);
            final psc_ac psc_ac = new psc_ac(0);
            final psc_aa psc_aa = new psc_aa(0);
            final psc_ad psc_ad = new psc_ad(0);
            final psc_af psc_af = new psc_af(0);
            final psc_ae psc_ae = new psc_ae(0);
            final psc_z psc_z2 = new psc_z(10485761);
            final psc_ac psc_ac2 = new psc_ac(0);
            final psc_aa psc_aa2 = new psc_aa(0);
            final psc_ad psc_ad2 = new psc_ad(0);
            final psc_af psc_af2 = new psc_af(0);
            final psc_ae psc_ae2 = new psc_ae(0);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_z, psc_ac, psc_aa, psc_ad, psc_af, psc_ae, psc_j, psc_z2, psc_ac2, psc_aa2, psc_ad2, psc_af2, psc_ae2, psc_j, psc_j });
            if (psc_z.a) {
                if (psc_ac.a) {
                    this.c = psc_ac.e();
                    this.d = 5120;
                }
                else if (psc_aa.a) {
                    this.c = psc_aa.e();
                    this.d = 4864;
                }
                else if (psc_ad.a) {
                    this.c = psc_ad.e();
                    this.d = 7168;
                }
                else if (psc_af.a) {
                    this.c = new String(psc_af.b, psc_af.c, psc_af.d);
                    this.d = 3072;
                }
                else if (psc_ae.a) {
                    this.c = psc_ae.e();
                    this.d = 7680;
                }
            }
            if (psc_ac2.a) {
                this.e = psc_ac2.e();
                this.f = 5120;
            }
            else if (psc_aa2.a) {
                this.e = psc_aa2.e();
                this.f = 4864;
            }
            else if (psc_ad2.a) {
                this.e = psc_ad2.e();
                this.f = 7168;
            }
            else if (psc_af2.a) {
                this.e = new String(psc_af2.b, psc_af2.c, psc_af2.d);
                this.f = 3072;
            }
            else {
                if (!psc_ae2.a) {
                    throw new psc_v("Party Name field must be set!");
                }
                this.e = psc_ae2.e();
                this.f = 7680;
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the name.");
        }
    }
    
    public psc_fy() {
        this.c = null;
        this.d = 0;
        this.e = null;
        this.f = 0;
        this.h = null;
    }
    
    public void a(final String c, final int d) {
        if (c != null) {
            this.c = c;
            if (d == 0) {
                this.d = 7168;
            }
            else {
                this.d = d;
            }
        }
    }
    
    public void b(final String e, final int f) {
        if (e != null) {
            this.e = e;
            if (f == 0) {
                this.f = 7168;
            }
            else {
                this.f = f;
            }
        }
    }
    
    public String b() {
        return this.c;
    }
    
    public String c() {
        return this.e;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.c != null) {
            sb.append(this.c);
            sb.append(", ");
        }
        sb.append(this.e);
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
    
    public int a(final int g) throws psc_v {
        psc_fy.g = g;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.h == null || n2 != psc_fy.g) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode EDI Party Name.");
            }
            final int a = this.h.a(array, n);
            this.h = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.h = null;
            throw new psc_v("Unable to encode EDI Party Name.");
        }
    }
    
    private int a() throws psc_v {
        final psc_h psc_h = new psc_h(psc_fy.g, true, 0);
        final psc_z psc_z = new psc_z(10551296, 0);
        final psc_z psc_z2 = new psc_z(10485761, 0);
        final psc_j psc_j = new psc_j();
        psc_i psc_i = null;
        final int n = 32767;
        if (this.e == null) {
            throw new psc_v("PartyName is not set.");
        }
        psc_i psc_i2 = null;
        try {
            switch (this.d) {
                case 5120: {
                    psc_i = new psc_ac(0, true, 0, this.c, 1, n);
                    break;
                }
                case 4864: {
                    psc_i = new psc_aa(0, true, 0, this.c, 1, n);
                    break;
                }
                case 7168: {
                    psc_i = new psc_ad(0, true, 0, this.c, 1, n);
                    break;
                }
                case 3072: {
                    if (this.c == null) {
                        psc_i = new psc_af(0, false, 0, null, 0, 0);
                        break;
                    }
                    final byte[] bytes = this.c.getBytes();
                    if (bytes.length > n) {
                        throw new psc_v("Illegal name length");
                    }
                    psc_i = new psc_af(0, true, 0, bytes, 0, bytes.length);
                    break;
                }
                case 7680: {
                    psc_i = new psc_ae(0, true, 0, this.c, 1, n);
                    break;
                }
            }
            switch (this.f) {
                case 5120: {
                    psc_i2 = new psc_ac(0, true, 0, this.e, 1, n);
                    break;
                }
                case 4864: {
                    psc_i2 = new psc_aa(0, true, 0, this.e, 1, n);
                    break;
                }
                case 7168: {
                    psc_i2 = new psc_ad(0, true, 0, this.e, 1, n);
                    break;
                }
                case 3072: {
                    final byte[] bytes2 = this.e.getBytes();
                    if (bytes2.length > n) {
                        throw new psc_v("Illegal name length");
                    }
                    psc_i2 = new psc_af(0, true, 0, bytes2, 0, bytes2.length);
                    break;
                }
                case 7680: {
                    psc_i2 = new psc_ae(0, true, 0, this.e, 1, n);
                    break;
                }
                default: {
                    throw new psc_v("Illegal empty partyName value");
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v(psc_m.getMessage());
        }
        if (psc_i == null) {
            this.h = new psc_n(new psc_i[] { psc_h, psc_z2, psc_i2, psc_j, psc_j });
        }
        else {
            this.h = new psc_n(new psc_i[] { psc_h, psc_z, psc_i, psc_j, psc_z2, psc_i2, psc_j, psc_j });
        }
        try {
            return this.h.a();
        }
        catch (psc_m psc_m2) {
            throw new psc_v(psc_m2.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fy)) {
            return false;
        }
        final psc_fy psc_fy = (psc_fy)o;
        if (this.c != null) {
            if (!this.c.equals(psc_fy.c)) {
                return false;
            }
        }
        else if (psc_fy.c != null) {
            return false;
        }
        if (this.e != null) {
            if (!this.e.equals(psc_fy.e)) {
                return false;
            }
        }
        else if (psc_fy.e != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fy psc_fy = new psc_fy();
        psc_fy.d = this.d;
        psc_fy.f = this.f;
        psc_fy.c = this.c;
        psc_fy.e = this.e;
        psc_fy.g = psc_fy.g;
        try {
            if (this.h != null) {
                psc_fy.a();
            }
        }
        catch (psc_v psc_v) {
            throw new CloneNotSupportedException("Cannot get ASN1 Template");
        }
        return psc_fy;
    }
}
