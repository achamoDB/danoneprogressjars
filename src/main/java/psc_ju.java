import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ju extends psc_al implements psc_am, Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    
    public String l() {
        return "DSA";
    }
    
    public String[] n() {
        return new String[] { "DSAPublicKey", "DSAPublicValue", "DSAPublicKeyBER", "DSAPublicKeyX957BER" };
    }
    
    public String[] o() {
        if (this.f == null) {
            return new String[0];
        }
        if (this.c == null || this.d == null || this.e == null) {
            return new String[] { "DSAPublicValue", "DSAPublicKeyBER", "DSAPublicKeyX957BER" };
        }
        return new String[] { "DSAPublicKey", "DSAPublicValue", "DSAPublicKeyBER", "DSAPublicKeyX957BER" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_ao, psc_bf {
        if (s.compareTo("DSAPublicKeyBER") == 0 || s.compareTo("DSAPublicKeyX957BER") == 0) {
            this.d();
            if (array == null || array.length != 1) {
                throw new psc_ao("Invalid BER DSA public key data.");
            }
            psc_lp.a(this, array[0], 0);
        }
        else {
            if (s.compareTo("DSAPublicKey") == 0) {
                this.c(array);
                return;
            }
            if (s.compareTo("DSAPublicValue") == 0) {
                this.a(array);
                return;
            }
            this.d();
            throw new psc_ao("Unknown DSA key data format.");
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.d();
        this.e();
        if (array == null || array.length != 4) {
            throw new psc_bf("Invalid input for DSA key. Expects {prime, subPrime, base, privateValue}");
        }
        try {
            this.a(-1, array[0], 0, array[0].length, array[1], 0, array[1].length, array[2], 0, array[2].length);
            this.a(array[3], 0, array[3].length);
        }
        catch (NullPointerException ex) {
            throw new psc_bf("Invalid input for DSA key.");
        }
    }
    
    private void a(final byte[][] array) throws psc_bf {
        this.d();
        if (array == null || array.length != 1) {
            throw new psc_bf("Invalid input for DSA key.");
        }
        if (array[0] == null) {
            throw new psc_bf("Invalid input for DSA key.");
        }
        this.a(array[0], 0, array[0].length);
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.d();
        psc_lp.a(this, array, n);
    }
    
    void a(final int n, final byte[] array, int n2, int n3, final byte[] array2, int n4, int n5, final byte[] array3, int n6, int n7) throws psc_bf {
        this.e();
        while (array[n2] == 0) {
            --n3;
            ++n2;
        }
        System.arraycopy(array, n2, this.c = new byte[n3], 0, n3);
        this.a = this.c.length * 8;
        for (int n8 = this.c[0] & 0xFF, i = n8 & 0x80; i == 0; i = (n8 & 0x80)) {
            --this.a;
            n8 <<= 1;
        }
        if (n != -1 && this.a > n) {
            throw new psc_bf("DSA prime size mismatch.");
        }
        if (this.a < 512 || this.a > 4096) {
            throw new psc_bf("Invalid DSA prime size.");
        }
        while (array2[n4] == 0) {
            --n5;
            ++n4;
        }
        System.arraycopy(array2, n4, this.d = new byte[n5], 0, n5);
        this.b = this.d.length * 8;
        for (int n9 = this.d[0] & 0xFF, j = n9 & 0x80; j == 0; j = (n9 & 0x80)) {
            --this.b;
            n9 <<= 1;
        }
        if (this.b < 160 || this.b > 160) {
            throw new psc_bf("Invalid DSA subprime size.");
        }
        while (array3[n6] == 0) {
            --n7;
            ++n6;
        }
        System.arraycopy(array3, n6, this.e = new byte[n7], 0, n7);
        if (this.e.length > this.c.length) {
            throw new psc_bf("Invalid DSA base size.");
        }
        if (this.e.length == this.c.length) {
            int n10;
            for (n10 = 0; n10 < this.e.length && (this.e[n10] & 0xFF) >= (this.c[n10] & 0xFF); ++n10) {
                if ((this.e[n10] & 0xFF) > (this.c[n10] & 0xFF)) {
                    throw new psc_bf("Invalid DSA base size.");
                }
            }
            if (n10 >= this.e.length) {
                throw new psc_bf("Invalid DSA base size.");
            }
        }
    }
    
    void a(final byte[] array, int n, int n2) throws psc_bf {
        this.d();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.f = new byte[n2], 0, n2);
        if (this.c == null || this.d == null || this.e == null) {
            return;
        }
        if (this.f.length > this.c.length) {
            throw new psc_bf("DSA public value incompatible with previously stored parameters.");
        }
        if (this.f.length == this.c.length) {
            int n3;
            for (n3 = 0; n3 < this.f.length && (this.f[n3] & 0xFF) >= (this.c[n3] & 0xFF); ++n3) {
                if ((this.f[n3] & 0xFF) > (this.c[n3] & 0xFF)) {
                    throw new psc_bf("DSA public value incompatible with previously stored parameters.");
                }
            }
            if (n3 >= this.f.length) {
                throw new psc_bf("DSA public value incompatible with previously stored parameters.");
            }
        }
    }
    
    public int q() {
        return 4096;
    }
    
    public int p() {
        return 512;
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("DSAPublicKeyBER") == 0) {
            return this.a((String)null);
        }
        if (s.compareTo("DSAPublicKeyX957BER") == 0) {
            return this.a("DSAX957");
        }
        if (s.compareTo("DSAPublicValue") == 0) {
            return this.c();
        }
        if (s.compareTo("DSAPublicKey") != 0) {
            throw new psc_ao("Unknown DSA key data format.");
        }
        return this.m();
    }
    
    public byte[][] m() {
        if (this.c == null || this.d == null || this.e == null || this.f == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        final byte[] array2 = new byte[this.d.length];
        System.arraycopy(this.d, 0, array2, 0, this.d.length);
        final byte[] array3 = new byte[this.e.length];
        System.arraycopy(this.e, 0, array3, 0, this.e.length);
        final byte[] array4 = new byte[this.f.length];
        System.arraycopy(this.f, 0, array4, 0, this.f.length);
        return new byte[][] { array, array2, array3, array4 };
    }
    
    private byte[][] c() {
        if (this.f == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.f.length];
        System.arraycopy(this.f, 0, array, 0, this.f.length);
        return new byte[][] { array };
    }
    
    private byte[][] a(final String s) {
        if (this.f == null) {
            return new byte[0][];
        }
        try {
            return new byte[][] { psc_lp.a(s, this.c, this.d, this.e, this.f) };
        }
        catch (psc_ap psc_ap) {
            return new byte[0][];
        }
    }
    
    public boolean equals(final Object o) {
        return o instanceof psc_ju && this.a(o);
    }
    
    public boolean a(final Object o) {
        try {
            final psc_al psc_al = (psc_al)o;
            if (this.f == null) {
                if (psc_al.l().compareTo("DSA") != 0) {
                    return false;
                }
                final String[] o2 = psc_al.o();
                for (int i = 0; i < o2.length; ++i) {
                    if (o2[i].equals("DSAPublicKey")) {
                        return false;
                    }
                }
                return true;
            }
            else {
                byte[][] array;
                if (this.c == null || this.d == null || this.e == null) {
                    String[] o3;
                    int n;
                    for (o3 = psc_al.o(), n = 0; n < o3.length && o3[n].compareTo("DSAPublicValue") == 0; ++n) {}
                    if (n != o3.length) {
                        return false;
                    }
                    array = psc_al.b("DSAPublicValue");
                }
                else {
                    array = psc_al.b("DSAPublicKey");
                }
                int n2 = 0;
                if (array.length > 1) {
                    if (!this.a(this.c, null, array[0], null)) {
                        return false;
                    }
                    if (!this.a(this.d, null, array[1], null)) {
                        return false;
                    }
                    if (!this.a(this.e, null, array[2], null)) {
                        return false;
                    }
                    n2 = 3;
                }
                if (!this.a(this.f, null, array[n2], null)) {
                    return false;
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ju psc_ju = new psc_ju();
        this.a(psc_ju);
        return psc_ju;
    }
    
    protected void a(final psc_ju psc_ju) throws CloneNotSupportedException {
        if (this.c != null) {
            psc_ju.c = this.c.clone();
        }
        if (this.d != null) {
            psc_ju.d = this.d.clone();
        }
        if (this.e != null) {
            psc_ju.e = this.e.clone();
        }
        if (this.f != null) {
            psc_ju.f = this.f.clone();
        }
        psc_ju.a = this.a;
        psc_ju.b = this.b;
        psc_ju.a(this);
    }
    
    private void d() {
        this.d(this.f);
        this.f = null;
    }
    
    private void e() {
        this.d(this.c);
        this.d(this.e);
        this.d(this.d);
        final byte[] c = null;
        this.e = c;
        this.d = c;
        this.c = c;
        final int n = 0;
        this.b = n;
        this.a = n;
    }
    
    public void y() {
        super.y();
        this.d();
        this.e();
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
