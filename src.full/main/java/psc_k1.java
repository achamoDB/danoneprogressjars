import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_k1 extends psc_jj implements Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    private psc_ei d;
    
    public psc_k1() {
        this.b = -1;
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        this.a = array[0];
        if (this.a <= 0) {
            throw new psc_be("PKCS 5 V2 PBE iteration count must be greater than 0.");
        }
        if (array.length == 1) {
            return;
        }
        this.b = array[1];
        if (array.length == 2) {
            return;
        }
        throw new psc_be("Incorrect number of parameters: expected one or two. (iterationCount) or (iterationCount, keySize)");
    }
    
    public int[] c() {
        if (this.b == -1) {
            return new int[] { this.a };
        }
        return new int[] { this.a, this.b };
    }
    
    public String d() {
        return "PKCS5V2PBE";
    }
    
    public String e() {
        return "PKCS5V2PBE";
    }
    
    public void a(final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm, final byte[] array, final int n, final int n2) throws psc_ao, psc_gw, psc_be {
        psc_lv.a(this, psc_dg, psc_di, psc_dm, array, n, n2);
    }
    
    public byte[] a(final psc_az psc_az, final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao {
        return psc_lv.a(this, psc_az, psc_dg, psc_di, psc_dm);
    }
    
    public byte[] a(final String s, final String s2) throws psc_ao {
        throw new psc_ao("Not yet implemented.");
    }
    
    public byte[] f() throws psc_ao {
        throw new psc_ao("Not yet implemented.");
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
        return true;
    }
    
    public void a(final psc_az psc_az, final psc_di psc_di, final int n, final psc_dc psc_dc) throws psc_en, psc_bf {
        if (this.a <= 0) {
            throw new psc_en("PKCS 5 V2 PBE iteration count must be greater than 0.");
        }
        if (this.d == null) {
            this.d = new psc_jd();
        }
        if (!this.d.b(psc_az)) {
            throw new psc_en("Invalid digest algorithm for use with PKCS #5 V2 PBE.");
        }
        char[] j;
        int length;
        for (j = psc_dc.j(), length = j.length; j[length - 1] == '\0'; --length) {}
        final byte[] array = new byte[length];
        for (int i = 0; i < length; ++i) {
            array[i] = (byte)j[i];
            j[i] = '\0';
        }
        final psc_j5 psc_j5 = new psc_j5();
        psc_j5.a(array, 0, array.length);
        psc_au.c(array);
        this.d.a(psc_az, psc_j5, null);
        final int a = psc_dc.a(this.b);
        int n2 = (a + 7) / 8;
        final byte[] array2 = new byte[n2];
        int n3 = 0;
        final int e = this.d.e();
        final byte[] array3 = new byte[e];
        final byte[] array4 = new byte[e];
        final int n4 = (n2 + e - 1) / e;
        final byte[] array5 = new byte[4];
        for (int k = 1; k <= n4; ++k) {
            this.d.a(psc_az, psc_j5, null);
            this.d.a(this.c, 0, this.c.length);
            this.a(array5);
            this.d.a(array5, 0, 4);
            this.d.b(array4, 0);
            System.arraycopy(array4, 0, array3, 0, array4.length);
            for (int l = 1; l < this.a; ++l) {
                this.d.a(psc_az, psc_j5, null);
                this.d.a(array4, 0, array4.length);
                this.d.b(array4, 0);
                for (int n5 = 0; n5 < array3.length; ++n5) {
                    array3[n5] ^= array4[n5];
                }
            }
            int n6;
            if ((n6 = n2) > e) {
                n6 = e;
            }
            System.arraycopy(array3, 0, array2, n3, n6);
            n3 += n6;
            n2 -= n6;
        }
        psc_au.c(array4);
        psc_au.c(array3);
        psc_j5.y();
        final int n7 = n2 * 8 - a;
        if (n7 > 0) {
            final byte b = (byte)(255 >>> n7);
            final byte[] array6 = array2;
            final int n8 = 0;
            array6[n8] &= b;
        }
        psc_dc.a(array2, 0, array2.length);
    }
    
    private void a(final byte[] array) {
        for (int i = 3; i >= 0; --i) {
            final int n = array[i] + 1 & 0xFF;
            array[i] = (byte)n;
            if (n != 0) {
                break;
            }
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_k1 psc_k1 = new psc_k1();
        psc_k1.a = this.a;
        psc_k1.b = this.b;
        if (this.c != null) {
            psc_k1.c = this.c.clone();
        }
        return psc_k1;
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
