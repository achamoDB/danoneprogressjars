import java.math.BigInteger;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gd extends psc_fz implements Cloneable, Serializable
{
    private byte[] a;
    psc_n b;
    
    public psc_gd() {
        super(11, "VeriSignCRSTransactionID");
    }
    
    public psc_gd(final byte[] array, final int n, final int n2) {
        this();
        this.f(array, n, n2);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(65536);
            final psc_aa psc_aa = new psc_aa(65536);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_p, psc_aa, psc_j });
            if (psc_p.a) {
                if (psc_p.d > 0) {
                    this.a = new byte[psc_p.d];
                    System.arraycopy(psc_p.b, psc_p.c, this.a, 0, psc_p.d);
                }
            }
            else {
                if (!psc_aa.a) {
                    throw new psc_f0("Unexpected encoding.");
                }
                this.a = new BigInteger(psc_aa.e(), 10).toByteArray();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode VeriSignCRSTransactionID.");
        }
    }
    
    public void f(final byte[] array, final int n, final int n2) {
        this.e();
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        }
    }
    
    public byte[] g() {
        if (this.a == null) {
            return null;
        }
        return this.a.clone();
    }
    
    protected int d() {
        this.b = null;
        int length = 0;
        if (this.a != null) {
            length = this.a.length;
        }
        final psc_i[] array = { new psc_fo(0, true, 0), null, new psc_j() };
        try {
            if (psc_ah.c(psc_nz.c)) {
                String string;
                if (length == 0) {
                    string = "0";
                }
                else {
                    string = new BigInteger(this.a).toString();
                }
                array[1] = new psc_aa(0, true, 0, string);
            }
            else {
                array[1] = new psc_p(0, true, 0, this.a, 0, length, true);
            }
            this.b = new psc_n(array);
            return this.b.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    protected int c(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.d() == 0) {
            return 0;
        }
        try {
            final int a = this.b.a(array, n);
            super.ac = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.ac = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gd psc_gd = new psc_gd();
        if (this.a != null) {
            psc_gd.a = this.a.clone();
        }
        super.a(psc_gd);
        return psc_gd;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_gd && psc_k4.a(((psc_gd)o).a, this.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
