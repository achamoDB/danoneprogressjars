import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_k0 extends psc_jj implements Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    
    public psc_k0() {
        this.b = -1;
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length == 1) {
            this.a = array[0];
            if (this.a <= 0) {
                throw new psc_be("PKCS 5 PBE iteration count must be greater than 0.");
            }
        }
        else {
            if (array.length != 2) {
                throw new psc_be("Incorrect number of parameters: expected one (iteration count) or two (iteration cound, keySize)");
            }
            this.a = array[0];
            if (this.a <= 0) {
                throw new psc_be("PKCS 5 PBE iteration count must be greater than 0.");
            }
            this.b = array[1];
        }
    }
    
    public int[] c() {
        if (this.b == -1) {
            return new int[] { this.a };
        }
        return new int[] { this.a, this.b };
    }
    
    public String d() {
        return "PKCS5PBE";
    }
    
    public String e() {
        return "PKCS5PBE";
    }
    
    public void a(final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm, final byte[] array, final int n, final int n2) throws psc_ao {
        psc_lu.a(this, psc_dg, array, n, n2);
    }
    
    public byte[] a(final String s, final String s2) throws psc_ao {
        return psc_lu.a(this.a, this.c);
    }
    
    public byte[] f() throws psc_ao {
        return psc_lu.a(this.a, this.c);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
    }
    
    public byte[] g() {
        if (this.c == null) {
            return null;
        }
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        return array;
    }
    
    public boolean h() {
        return false;
    }
    
    public void a(final psc_az psc_az, final psc_di psc_di, final int n, final psc_dc psc_dc) throws psc_en, psc_bf {
        if (this.a <= 0) {
            throw new psc_en("PKCS 5 PBE iteration count must be greater than 0.");
        }
        final int a = psc_dc.a(this.b);
        final int n2 = (a + 7) / 8;
        int a2 = 0;
        if (psc_di != null) {
            a2 = psc_di.a(n);
        }
        final int h = psc_az.h();
        if (h < n2 + a2) {
            throw new psc_en(psc_az.e() + " will not generate enough bytes for the given key size" + " using PKCS #5 PBE.");
        }
        psc_az.j();
        final char[] j = psc_dc.j();
        final byte[] array = new byte[j.length];
        int length = j.length;
        if (length > 0 && j[j.length - 1] == '\0') {
            --length;
        }
        for (int i = 0; i < j.length; ++i) {
            array[i] = (byte)j[i];
            j[i] = '\0';
        }
        psc_az.a(array, 0, length);
        psc_au.c(array);
        psc_az.a(this.c, 0, this.c.length);
        final byte[] array2 = new byte[h];
        psc_az.b(array2, 0);
        for (int k = this.a - 1; k > 0; --k) {
            psc_az.j();
            psc_az.a(array2, 0, array2.length);
            psc_az.b(array2, 0);
        }
        final int n3 = n2 * 8 - a;
        if (n3 > 0) {
            final byte b = (byte)(255 >>> n3);
            final byte[] array3 = array2;
            final int n4 = 0;
            array3[n4] &= b;
        }
        psc_dc.a(array2, 0, n2);
        try {
            if (a2 > 0) {
                psc_di.b(array2, n2, a2);
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not set the IV in PKCS 5 PBE.");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_k0 psc_k0 = new psc_k0();
        psc_k0.a = this.a;
        psc_k0.b = this.b;
        if (this.c != null) {
            psc_k0.c = this.c.clone();
        }
        return psc_k0;
    }
    
    public void y() {
        super.y();
        this.b = -1;
        this.d(this.c);
        this.c = null;
        this.a = 0;
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
