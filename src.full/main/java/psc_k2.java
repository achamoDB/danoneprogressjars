import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_k2 extends psc_jj implements psc_k3, Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    private String d;
    
    psc_k2() {
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
                throw new psc_be("Incorrect number of parameters: expected one (iteration count) or two (iteration count, keySize)");
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
        return "SSLCPBE";
    }
    
    public String e() {
        return "SSLCPBE";
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
    
    public byte[] b(final byte[] array, final int n, final int n2) throws psc_bf {
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return psc_ip.b(psc_ip.a(array2, new String[1], new byte[8]));
    }
    
    public byte[] a(final byte[] array) throws psc_bf {
        return psc_ip.f(array);
    }
    
    public byte[] b(final byte[] array) throws psc_bf {
        return psc_ip.d(array);
    }
    
    public byte[] c(final byte[] array) throws psc_bf {
        return psc_ip.a(psc_ip.c(array), this.d, this.c);
    }
    
    public void a(final psc_az psc_az, final psc_di psc_di, final int n, final psc_dc psc_dc) throws psc_en, psc_bf {
        this.d = psc_dc.l();
        final int a = psc_dc.a(this.b);
        final int n2 = this.d.equals("DES") ? 8 : 24;
        final byte[] array = new byte[n2];
        int a2 = 0;
        if (psc_di != null) {
            a2 = psc_di.a(n);
        }
        final int h = psc_az.h();
        psc_az.j();
        final char[] j = psc_dc.j();
        final byte[] array2 = new byte[j.length];
        int length = j.length;
        if (length > 0 && j[j.length - 1] == '\0') {
            --length;
        }
        for (int i = 0; i < j.length; ++i) {
            array2[i] = (byte)j[i];
            j[i] = '\0';
        }
        final byte[] array3 = new byte[h];
        int n3 = 0;
        while (true) {
            psc_az.a(array2, 0, length);
            psc_az.a(this.c, 0, this.c.length);
            int n4 = psc_az.b(array3, 0);
            for (int k = this.a - 1; k > 0; --k) {
                psc_az.j();
                psc_az.a(array3, 0, n4);
                n4 = psc_az.b(array3, 0);
            }
            final int n5 = n2 * 8 - a;
            if (n5 > 0) {
                final byte b = (byte)(255 >>> n5);
                final byte[] array4 = array3;
                final int n6 = 0;
                array4[n6] &= b;
            }
            for (int n7 = 0; n3 < n2 && n7 < n4; ++n7, ++n3) {
                array[n3] = array3[n7];
            }
            if (n3 == n2) {
                break;
            }
            psc_az.j();
            psc_az.a(array3, 0, n4);
        }
        psc_au.c(array2);
        psc_dc.a(array, 0, n2);
        try {
            if (a2 > 0) {
                psc_di.b(this.c, 0, this.c.length);
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not set the IV in PKCS 5 PBE.");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_k2 psc_k2 = new psc_k2();
        psc_k2.a = this.a;
        psc_k2.b = this.b;
        if (this.c != null) {
            psc_k2.c = this.c.clone();
        }
        return psc_k2;
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
