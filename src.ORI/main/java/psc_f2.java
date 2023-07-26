import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f2 extends psc_fz implements Cloneable, Serializable
{
    private char[] a;
    private int b;
    psc_n c;
    
    public psc_f2() {
        super(1, "ChallengePassword");
    }
    
    public psc_f2(final char[] array, final int n, final int n2, final int n3) throws psc_f0 {
        this();
        this.a(array, n, n2, n3);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        final psc_fo psc_fo = new psc_fo(0);
        final psc_j psc_j = new psc_j();
        final psc_z psc_z = new psc_z(0);
        try {
            final psc_aa psc_aa = new psc_aa(0);
            final psc_ac psc_ac = new psc_ac(0);
            final psc_ad psc_ad = new psc_ad(0);
            final psc_ae psc_ae = new psc_ae(0);
            final psc_af psc_af = new psc_af(0);
            final psc_ag psc_ag = new psc_ag(0);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_z, psc_aa, psc_ac, psc_ad, psc_ae, psc_af, psc_ag, psc_j, psc_j });
            if (psc_aa.a) {
                this.b = 4864;
                this.a = psc_aa.e().toCharArray();
            }
            else if (psc_ac.a) {
                this.b = 5120;
                this.a = psc_ac.e().toCharArray();
            }
            else if (psc_ad.a) {
                this.b = 7168;
                this.a = psc_ad.e().toCharArray();
            }
            else if (psc_ae.a) {
                this.b = 7680;
                this.a = psc_ae.e().toCharArray();
            }
            else if (psc_af.a) {
                this.b = 3072;
                this.a = this.e(psc_af.b, psc_af.c, psc_af.d).toCharArray();
            }
            else if (psc_ag.a) {
                this.b = 5632;
                this.a = psc_ag.e().toCharArray();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode ChallengePassword.");
        }
    }
    
    public void a(final char[] array, final int n, final int n2, final int b) throws psc_f0 {
        if (array == null || n2 == 0) {
            throw new psc_f0("Password is null.");
        }
        System.arraycopy(array, n, this.a = new char[n2], 0, n2);
        if (b != 4864 && b != 5120 && b != 7168 && b != 3072 && b != 7680 && b != 5632) {
            throw new psc_f0("Invalid String Type.");
        }
        this.b = b;
    }
    
    public char[] g() {
        if (this.a == null) {
            return null;
        }
        final char[] array = new char[this.a.length];
        for (int i = 0; i < this.a.length; ++i) {
            array[i] = (char)(this.a[i] & '\u00ff');
        }
        return array;
    }
    
    protected int d() {
        this.c = null;
        if (this.a == null) {
            return 0;
        }
        if (this.b == 0) {
            this.b = 3072;
        }
        try {
            final psc_j psc_j = new psc_j();
            final psc_fo psc_fo = new psc_fo(0, true, 0);
            final psc_z psc_z = new psc_z(0, 0);
            final byte[] bytes = new byte[this.a.length];
            for (int i = 0; i < this.a.length; ++i) {
                bytes[i] = (byte)this.a[i];
            }
            psc_i psc_i = null;
            switch (this.b) {
                case 5120: {
                    psc_i = new psc_ac(0, true, 0, bytes, 0, bytes.length);
                    break;
                }
                case 4864: {
                    psc_i = new psc_aa(0, true, 0, bytes, 0, bytes.length);
                    break;
                }
                case 7168: {
                    psc_i = new psc_ad(0, true, 0, bytes, 0, bytes.length, 1);
                    break;
                }
                case 5632: {
                    psc_i = new psc_ag(0, true, 0, bytes, 0, bytes.length);
                    break;
                }
                case 3072: {
                    final byte[] a = this.a(new String(bytes));
                    if (a.length < 2) {
                        return 0;
                    }
                    psc_i = new psc_af(0, true, 0, a, 2, a.length - 2);
                    break;
                }
                case 7680: {
                    psc_i = new psc_ae(0, true, 0, bytes, 0, bytes.length, 1);
                    break;
                }
                default: {
                    return 0;
                }
            }
            this.c = new psc_n(new psc_i[] { psc_fo, psc_z, psc_i, psc_j, psc_j });
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
        final psc_f2 psc_f2 = new psc_f2();
        if (this.a != null) {
            psc_f2.a = this.a.clone();
        }
        psc_f2.b = this.b;
        super.a(psc_f2);
        return psc_f2;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_f2)) {
            return false;
        }
        final psc_f2 psc_f2 = (psc_f2)o;
        if (this.a != null) {
            if (psc_f2.a == null) {
                return false;
            }
            if (this.a.length != psc_f2.a.length) {
                return false;
            }
            for (int i = 0; i < this.a.length; ++i) {
                if (this.a[i] != psc_f2.a[i]) {
                    return false;
                }
            }
        }
        else if (psc_f2.a != null) {
            return false;
        }
        return true;
    }
    
    protected void e() {
        super.e();
        this.c = null;
        if (this.a != null) {
            for (int i = 0; i < this.a.length; ++i) {
                this.a[i] = '\0';
            }
            this.a = null;
        }
    }
    
    protected void finalize() {
        this.e();
    }
}
