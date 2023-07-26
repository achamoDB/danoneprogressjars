import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jv extends psc_al implements psc_am, Cloneable, Serializable
{
    private byte[] a;
    private byte[] b;
    private int c;
    private byte[] d;
    
    public String l() {
        return "DH";
    }
    
    public String[] n() {
        return new String[] { "DHPublicKey", "DHPublicValue", "DHPublicKeyBER" };
    }
    
    public String[] o() {
        if (this.d == null) {
            return new String[0];
        }
        if (this.a == null) {
            return new String[] { "DHPublicValue", "DHPublicKeyBER" };
        }
        return new String[] { "DHPublicKey", "DHPublicValue", "DHPublicKeyBER" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_ao, psc_bf {
        if (s.compareTo("DHPublicKey") == 0) {
            this.c(array);
            return;
        }
        if (s.compareTo("DHPublicKeyBER") == 0) {
            if (array == null || array.length != 1) {
                this.e();
                throw new psc_ao("Invalid BER DH public key data.");
            }
            this.a(array[0], 0);
        }
        else {
            if (s.compareTo("DHPublicValue") == 0) {
                this.a(array);
                return;
            }
            this.e();
            throw new psc_ao("Unknown DH key data format.");
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.e();
        if (array == null || array.length != 4) {
            throw new psc_bf("Invalid input for DH key. Expected prime, base, maxExponentLen, publicValue");
        }
        try {
            this.a(array[0], 0, array[0].length, array[1], 0, array[1].length, -1, true, array[2], 0, array[2].length);
            this.a(array[3], 0, array[3].length);
        }
        catch (NullPointerException ex) {
            throw new psc_bf("Invalid input for DH key.");
        }
    }
    
    private void a(final byte[][] array) throws psc_bf {
        this.e();
        if (array == null || array.length != 1) {
            throw new psc_bf("Invalid input for DH key.");
        }
        this.a(array[0], 0, array[0].length);
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.e();
        psc_lq.a(this, array, n);
    }
    
    void a(final byte[] array, int n, int n2, final byte[] array2, int n3, int n4, final int c, final boolean b, final byte[] array3, final int n5, final int n6) throws psc_bf {
        this.f();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        int n7 = this.a.length * 8;
        for (int n8 = this.a[0] & 0xFF, i = n8 & 0x80; i == 0; i = (n8 & 0x80)) {
            --n7;
            n8 <<= 1;
        }
        if (n7 < 256 || n7 > 2048) {
            throw new psc_bf("Invalid DH prime size.");
        }
        while (array2[n3] == 0) {
            --n4;
            ++n3;
        }
        System.arraycopy(array2, n3, this.b = new byte[n4], 0, n4);
        if (this.b.length > this.a.length) {
            throw new psc_bf("Invalid DH base size.");
        }
        if (this.b.length == this.a.length) {
            int n9;
            for (n9 = 0; n9 < this.b.length && (this.b[n9] & 0xFF) >= (this.a[n9] & 0xFF); ++n9) {
                if ((this.b[n9] & 0xFF) > (this.a[n9] & 0xFF)) {
                    throw new psc_bf("Invalid DH base size.");
                }
            }
            if (n9 >= this.b.length) {
                throw new psc_bf("Invalid DH base size.");
            }
        }
        this.c = n7 - 1;
        if (c != -1) {
            this.c = c;
        }
        else if (b) {
            this.c = 0;
            for (int j = 0; j < n6; ++j) {
                final int c2 = this.c << 8;
                this.c = c2;
                this.c = (c2 | (array3[j + n5] & 0xFF));
            }
        }
        if (this.c >= n7 || this.c < 160) {
            throw new psc_bf("Invalid DH max exponent length.");
        }
    }
    
    void a(final byte[] array, int n, int n2) throws psc_bf {
        this.e();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.d = new byte[n2], 0, n2);
    }
    
    public int q() {
        return 2048;
    }
    
    public int p() {
        return 256;
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("DHPublicKeyBER") == 0) {
            return this.d();
        }
        if (s.compareTo("DHPublicValue") == 0) {
            return this.c();
        }
        if (s.compareTo("DHPublicKey") != 0) {
            throw new psc_ao("Unknown DH key data format.");
        }
        return this.m();
    }
    
    public byte[][] m() {
        if (this.a == null || this.b == null) {
            return new byte[0][];
        }
        int n = 4;
        if (this.c <= 16777215) {
            --n;
            if (this.c <= 65535) {
                --n;
                if (this.c <= 255) {
                    --n;
                }
            }
        }
        final byte[] array = new byte[n];
        for (int i = n - 1, n2 = 0; i >= 0; --i, n2 += 8) {
            array[i] = (byte)(this.c >>> n2 & 0xFF);
        }
        final byte[] array2 = new byte[this.a.length];
        System.arraycopy(this.a, 0, array2, 0, this.a.length);
        final byte[] array3 = new byte[this.b.length];
        System.arraycopy(this.b, 0, array3, 0, this.b.length);
        final byte[] array4 = new byte[this.d.length];
        System.arraycopy(this.d, 0, array4, 0, this.d.length);
        return new byte[][] { array2, array3, array, array4 };
    }
    
    private byte[][] c() {
        if (this.d == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.d.length];
        System.arraycopy(this.d, 0, array, 0, this.d.length);
        return new byte[][] { array };
    }
    
    private byte[][] d() {
        if (this.a == null || this.b == null || this.d == null) {
            return new byte[0][];
        }
        try {
            return new byte[][] { psc_lq.a(this.a, this.b, this.d, this.c) };
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
            if (this.d == null) {
                return psc_al.l().compareTo("DH") == 0 && psc_al.o().length == 0;
            }
            byte[][] array;
            if (this.a == null || this.b == null) {
                String[] o2;
                int n;
                for (o2 = psc_al.o(), n = 0; n < o2.length && o2[n].compareTo("DHPublicValue") == 0; ++n) {}
                if (n != o2.length) {
                    return false;
                }
                array = psc_al.b("DHPublicValue");
            }
            else {
                array = psc_al.b("DHPublicKey");
            }
            int n2 = 0;
            if (array.length > 1) {
                if (!this.a(this.a, null, array[0], null)) {
                    return false;
                }
                if (!this.a(this.b, null, array[2], null)) {
                    return false;
                }
                n2 = 3;
            }
            if (!this.a(this.d, null, array[n2], null)) {
                return false;
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jv psc_jv = new psc_jv();
        if (this.a != null) {
            psc_jv.a = this.a.clone();
        }
        if (this.b != null) {
            psc_jv.b = this.b.clone();
        }
        psc_jv.c = this.c;
        if (this.d != null) {
            psc_jv.d = this.d.clone();
        }
        psc_jv.a(this);
        return psc_jv;
    }
    
    private void e() {
        this.d(this.d);
        this.d = null;
    }
    
    private void f() {
        this.d(this.a);
        this.d(this.b);
        final byte[] array = null;
        this.b = array;
        this.a = array;
        this.c = 0;
    }
    
    public void y() {
        super.y();
        this.e();
        this.f();
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
