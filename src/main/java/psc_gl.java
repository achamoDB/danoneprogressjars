import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gl extends psc_fz implements Cloneable, Serializable
{
    private String a;
    private int b;
    psc_n c;
    
    public psc_gl() {
        super(19, "placeOfBirth");
    }
    
    public psc_gl(final String s, final int n) throws psc_f0 {
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
            final psc_z psc_z = new psc_z(0);
            final psc_aa psc_aa = new psc_aa(0, 1, -1);
            final psc_ac psc_ac = new psc_ac(0, 1, -1);
            final psc_ad psc_ad = new psc_ad(0, 1, -1);
            final psc_ae psc_ae = new psc_ae(0, 1, -1);
            final psc_af psc_af = new psc_af(0);
            final psc_ag psc_ag = new psc_ag(0, 1, -1);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_z, psc_aa, psc_ac, psc_ad, psc_ae, psc_af, psc_ag, psc_j, psc_j });
            if (psc_aa.a) {
                this.b = 4864;
                this.a = psc_aa.e();
            }
            else if (psc_ac.a) {
                this.b = 5120;
                this.a = psc_ac.e();
            }
            else if (psc_ad.a) {
                this.b = 7168;
                this.a = psc_ad.e();
            }
            else if (psc_ae.a) {
                this.b = 7680;
                this.a = psc_ae.e();
            }
            else if (psc_af.a) {
                this.b = 3072;
                this.a = this.e(psc_af.b, psc_af.c, psc_af.d);
            }
            else {
                if (!psc_ag.a) {
                    throw new psc_f0("DirectoryString expected.");
                }
                this.b = 5632;
                this.a = psc_ag.e();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode placeOfBirth." + psc_m.getMessage());
        }
    }
    
    public void a(final String a, final int b) throws psc_f0 {
        if (a == null) {
            throw new psc_f0("PlaceOfBirth is null.");
        }
        this.e();
        this.a = a;
        if (b != 4864 && b != 5120 && b != 7168 && b != 3072 && b != 7680 && b != 5632) {
            throw new psc_f0("Invalid String Type.");
        }
        this.b = b;
    }
    
    public String g() {
        return this.a;
    }
    
    protected int d() {
        this.c = null;
        if (this.a == null) {
            return 0;
        }
        super.ab = super.ab;
        if (this.b == 0) {
            this.b = 3072;
        }
        try {
            final psc_j psc_j = new psc_j();
            final psc_fo psc_fo = new psc_fo(0, true, 0);
            final psc_z psc_z = new psc_z(0, 0);
            psc_i psc_i = null;
            switch (this.b) {
                case 5120: {
                    psc_i = new psc_ac(0, true, 0, this.a);
                    break;
                }
                case 4864: {
                    psc_i = new psc_aa(0, true, 0, this.a);
                    break;
                }
                case 7168: {
                    psc_i = new psc_ad(0, true, 0, this.a);
                    break;
                }
                case 5632: {
                    psc_i = new psc_ag(0, true, 0, this.a);
                    break;
                }
                case 3072: {
                    final byte[] a = this.a(this.a);
                    if (a.length < 2) {
                        return 0;
                    }
                    psc_i = new psc_af(0, true, 0, a, 2, a.length - 2);
                    break;
                }
                case 7680: {
                    psc_i = new psc_ae(0, true, 0, this.a);
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
        final psc_gl psc_gl = new psc_gl();
        if (this.a != null) {
            psc_gl.a = this.a;
        }
        psc_gl.b = this.b;
        super.a(psc_gl);
        return psc_gl;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gl)) {
            return false;
        }
        final psc_gl psc_gl = (psc_gl)o;
        if (this.a == null) {
            return psc_gl.a == null;
        }
        return psc_gl.a != null && this.a.equals(psc_gl.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.c = null;
    }
}
