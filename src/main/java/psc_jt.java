import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jt extends psc_al implements Cloneable, Serializable
{
    private byte[] a;
    private byte[] b;
    
    public String l() {
        return "RSA";
    }
    
    public String[] n() {
        return new String[] { "RSAPublicKey", "RSAPublicKeyBER" };
    }
    
    public String[] o() {
        if (this.a == null || this.b == null) {
            return new String[0];
        }
        return new String[] { "RSAPublicKey", "RSAPublicKeyBER" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_bf, psc_ao {
        if (s.compareTo("RSAPublicKeyBER") == 0) {
            if (array == null || array.length != 1) {
                this.c();
                throw new psc_bf("Invalid key data format.");
            }
            this.a(array[0], 0);
        }
        else {
            if (s.compareTo("RSAPublicKey") != 0) {
                this.c();
                throw new psc_bf("Unknown key data format.");
            }
            this.c(array);
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.c();
        if (array == null || array.length != 2) {
            throw new psc_bf("Invalid input for RSA public key, expected { modulus, publicExponent }");
        }
        this.a(array[0], 0, array[0].length, array[1], 0, array[1].length);
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.c();
        psc_ln.a(this, array, n);
    }
    
    void a(final byte[] array, int n, int n2, final byte[] array2, int n3, int n4) throws psc_bf {
        this.c();
        if (array == null || n2 <= 0) {
            throw new psc_bf("RSA modulus not found.");
        }
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        int n5 = this.a.length * 8;
        for (int n6 = this.a[0] & 0xFF, i = n6 & 0x80; i == 0; i = (n6 << 1 & 0xFF)) {
            --n5;
        }
        if (n5 < 256 || n5 > 4096) {
            throw new psc_bf("Invalid RSA modulus size. Expected a value from 256 to 4096 bits long.");
        }
        while (array2[n3] == 0) {
            --n4;
            ++n3;
        }
        System.arraycopy(array2, n3, this.b = new byte[n4], 0, n4);
    }
    
    public int q() {
        return 4096;
    }
    
    public int p() {
        return 256;
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("RSAPublicKeyBER") == 0) {
            return this.d();
        }
        if (s.compareTo("RSAPublicKey") != 0) {
            throw new psc_ao("Unknown key data format.");
        }
        return this.m();
    }
    
    public byte[][] m() {
        if (this.a == null || this.b == null) {
            return new byte[0][];
        }
        return new byte[][] { this.a.clone(), this.b.clone() };
    }
    
    private byte[][] d() {
        if (this.a == null || this.b == null) {
            return new byte[0][];
        }
        try {
            return new byte[][] { psc_ln.a(this.a, this.b) };
        }
        catch (psc_ap psc_ap) {
            return new byte[0][];
        }
    }
    
    public boolean equals(final Object o) {
        return o instanceof psc_jt && this.a(o);
    }
    
    public boolean a(final Object o) {
        try {
            final psc_al psc_al = (psc_al)o;
            if (this.a != null && this.b != null) {
                final byte[][] b = psc_al.b("RSAPublicKey");
                return this.a(this.a, null, b[0], null) && this.a(this.b, null, b[1], null);
            }
            if (psc_al.l().compareTo("RSA") != 0) {
                return false;
            }
            final String[] o2 = psc_al.o();
            for (int i = 0; i < o2.length; ++i) {
                if (o2[i].equals("RSAPublicKey")) {
                    return false;
                }
            }
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jt psc_jt = new psc_jt();
        this.a(psc_jt);
        return psc_jt;
    }
    
    protected void a(final psc_jt psc_jt) throws CloneNotSupportedException {
        if (this.a != null) {
            psc_jt.a = this.a.clone();
        }
        if (this.b != null) {
            psc_jt.b = this.b.clone();
        }
        psc_jt.a(this);
    }
    
    protected void c() {
        final byte[] array = null;
        this.b = array;
        this.a = array;
    }
    
    public void y() {
        super.y();
        this.c();
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
