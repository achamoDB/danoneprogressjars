import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_df extends psc_an implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static boolean[] c;
    private static boolean[] d;
    private static boolean[] e;
    private static boolean[] f;
    private static String[] g;
    private static String[] h;
    private static String[] i;
    private static String[] j;
    private static final String k = "psc_df";
    static final int l = 1;
    static final int m = 3;
    static final int n = 4;
    static final int o = 5;
    
    public static psc_df a(final byte[] array, final int n, final String s) throws psc_ao, psc_be, psc_gw {
        psc_k psc_k;
        String b;
        try {
            psc_k = new psc_k(130816, false, 0, null, 0, 0);
            b = psc_q.b(array, n, 6, psc_k);
        }
        catch (psc_m psc_m) {
            try {
                final byte[] array2 = new byte[8];
                final psc_df a = a(psc_ip.a(array, 0, array2), s);
                a.c(array2, 0, 8);
                return a;
            }
            catch (psc_bf psc_bf) {
                throw new psc_ao("Could not decode the data.(" + psc_bf.getMessage() + ")");
            }
        }
        final psc_df a2 = a(b, s);
        a2.a(psc_k.b, psc_k.c, psc_k.d);
        return a2;
    }
    
    public static int a(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 6);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        throw new psc_ao("Cannot instantiate, unknown BER algorithm ID.");
    }
    
    public static psc_df a(final String str, final String str2) throws psc_ao, psc_be {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_df a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.a = array[i].c();
                    a3.b = array[i].d();
                    return a3;
                }
            }
            catch (psc_be psc_be) {
                if (i >= a.length) {
                    throw psc_be;
                }
            }
        }
        throw new psc_ao("A JSAFE_SymmetricCipher object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_df a(String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        final int length = array.length;
        boolean[] array4 = null;
        String[] array5 = null;
        switch (length) {
            case 1: {
                array4 = psc_df.c;
                array5 = psc_df.h;
                break;
            }
            case 3: {
                array4 = psc_df.d;
                array5 = psc_df.g;
                break;
            }
            case 4: {
                array4 = psc_df.f;
                array5 = psc_df.j;
                final String[] array6 = new String[3];
                for (int i = 0; i < 3; ++i) {
                    array6[i] = array[i + 1];
                }
                array = array6;
                break;
            }
            case 5: {
                array4 = psc_df.e;
                array5 = psc_df.i;
                final String[] array7 = new String[4];
                for (int j = 0; j < 4; ++j) {
                    array7[j] = array[j + 1];
                }
                array = array7;
                break;
            }
            default: {
                return null;
            }
        }
        final Object[] a = psc_bd.a(array, null, 12, array4, array5, "psc_df", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_df) {
            return (psc_df)a[0];
        }
        switch (length) {
            case 1: {
                ((psc_gv)a[0]).a(psc_ba.b(array[0]));
                return new psc_dh((psc_gv)a[0]);
            }
            case 3: {
                ((psc_dl)a[0]).a(psc_ba.b(array[0]));
                ((psc_di)a[1]).a(psc_ba.b(array[1]));
                ((psc_dm)a[2]).a(psc_ba.b(array[2]));
                return new psc_dk((psc_dl)a[0], (psc_di)a[1], (psc_dm)a[2]);
            }
            case 4: {
                ((psc_az)a[0]).a(psc_ba.b(array[0]));
                ((psc_gv)a[1]).a(psc_ba.b(array[1]));
                ((psc_dn)a[2]).a(psc_ba.b(array[2]));
                return new psc_do((psc_az)a[0], (psc_gv)a[1], (psc_dn)a[2]);
            }
            case 5: {
                ((psc_az)a[0]).a(psc_ba.b(array[0]));
                final int[] b = psc_ba.b(array[3]);
                ((psc_dn)a[3]).a(b);
                int n = -1;
                if (b != null && b.length >= 2) {
                    n = b[1];
                }
                int[] b2 = psc_ba.b(array[1]);
                if (array[1].startsWith("RC2") && !array[3].startsWith("PKCS5V2PBE") && n >= 0) {
                    b2 = new int[] { n };
                }
                ((psc_dl)a[1]).a(b2);
                ((psc_di)a[2]).a(psc_ba.b(array[2]));
                return new psc_dp((psc_az)a[0], (psc_dl)a[1], (psc_di)a[2], (psc_dn)a[3]);
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract byte[] f() throws psc_ao;
    
    public String g() {
        return this.a;
    }
    
    public String[] h() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String i();
    
    public String j() {
        return null;
    }
    
    public String k() {
        return null;
    }
    
    public String l() {
        return null;
    }
    
    public String m() {
        return null;
    }
    
    public int n() {
        return 1;
    }
    
    public psc_dc o() {
        final String i = this.i();
        final String g = this.g();
        try {
            return psc_dc.a(i, g);
        }
        catch (psc_ap psc_ap) {
            return null;
        }
    }
    
    public abstract int[] p();
    
    public abstract void a(final int[] p0) throws psc_be;
    
    public int[] q() {
        return new int[0];
    }
    
    public int[] r() {
        return new int[0];
    }
    
    public int[] s() {
        return new int[0];
    }
    
    public void b(final int[] array) throws psc_be {
    }
    
    public void a(final SecureRandom secureRandom) throws psc_gw, psc_en {
    }
    
    public void b(final byte[] array, final int n, final int n2) throws psc_gw {
    }
    
    public byte[] t() {
        return null;
    }
    
    public void b(final SecureRandom secureRandom) throws psc_en {
    }
    
    public void c(final byte[] array, final int n, final int n2) {
    }
    
    public byte[] u() {
        return null;
    }
    
    public abstract int a(final int p0);
    
    public void a(final psc_dc psc_dc) throws psc_en, psc_bf, psc_gw {
        this.c(psc_dc, null);
    }
    
    public abstract void c(final psc_dc p0, final SecureRandom p1) throws psc_en, psc_bf, psc_gw;
    
    public abstract void v() throws psc_en, psc_gw;
    
    public byte[] d(final byte[] array, final int n, final int n2) throws psc_en, psc_gw {
        if (n2 <= 0) {
            this.a(array, n, n2, null, 0);
            return new byte[0];
        }
        final int a = this.a(n2);
        if (a == 0) {
            this.a(array, n, n2, null, 0);
            return new byte[0];
        }
        final byte[] array2 = new byte[a];
        final int a2 = this.a(array, n, n2, array2, 0);
        if (a2 == 0) {
            return new byte[0];
        }
        if (a2 < array2.length) {
            final byte[] array3 = new byte[a2];
            int i;
            for (i = 0; i < a2; ++i) {
                array3[i] = array2[i];
                array2[i] = 0;
            }
            while (i < array2.length) {
                array2[i] = 0;
                ++i;
            }
            return array3;
        }
        return array2;
    }
    
    public abstract int a(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en, psc_gw;
    
    public byte[] a(final psc_dt psc_dt, final boolean b) throws psc_en {
        return this.a(psc_dt, b, null);
    }
    
    public byte[] a(final psc_dt psc_dt, final boolean b, final String str) throws psc_en {
        if (psc_dt == null) {
            throw new psc_en("The key to wrap is null.");
        }
        if (str != null) {
            if (str.endsWith("PEM") || str.startsWith("SSLC")) {
                return this.a(psc_dt, str);
            }
            String[] o;
            int n;
            for (o = psc_dt.o(), n = 0; n < o.length && (!str.equals(o[n]) || !str.endsWith("BER")); ++n) {}
            if (n >= o.length) {
                throw new psc_en("The  format, " + str + ", is not valid for this key");
            }
        }
        return this.a((psc_am)psc_dt, b, str);
    }
    
    protected byte[] a(final psc_dt psc_dt, final String s) throws psc_en {
        return new byte[0];
    }
    
    public byte[] a(final psc_al psc_al, final boolean b) throws psc_en {
        if (psc_al == null) {
            throw new psc_en("The key to wrap is null.");
        }
        return this.a((psc_am)psc_al, b);
    }
    
    public byte[] a(final psc_dc psc_dc, final boolean b) throws psc_en {
        if (psc_dc == null) {
            throw new psc_en("The key to wrap is null.");
        }
        return this.a((psc_am)psc_dc, b);
    }
    
    private byte[] a(final psc_am psc_am, final boolean b) throws psc_en {
        return this.a(psc_am, b, null);
    }
    
    private byte[] a(final psc_am psc_am, final boolean b, final String s) throws psc_en {
        byte[] f = null;
        int length = 0;
        if (b) {
            try {
                f = this.f();
                if (f == null) {
                    throw new psc_en("Cannot wrap the given key into a BER.");
                }
                length = f.length;
            }
            catch (psc_ao psc_ao) {
                throw new psc_en("Cannot wrap the given key into a BER.");
            }
        }
        return this.a(psc_am, b, s, f, 0, length);
    }
    
    protected abstract byte[] a(final psc_am p0, final boolean p1, final byte[] p2, final int p3, final int p4) throws psc_en;
    
    protected abstract byte[] a(final psc_am p0, final boolean p1, final String p2, final byte[] p3, final int p4, final int p5) throws psc_en;
    
    public byte[] w() throws psc_en, psc_e1, psc_gw, psc_gx {
        final int a = this.a(0);
        if (a == 0) {
            this.b(null, 0);
            return new byte[0];
        }
        final byte[] array = new byte[a];
        final int b = this.b(array, 0);
        if (b == 0) {
            return new byte[0];
        }
        if (b < array.length) {
            final byte[] array2 = new byte[b];
            int i;
            for (i = 0; i < b; ++i) {
                array2[i] = array[i];
                array[i] = 0;
            }
            while (i < array.length) {
                array[i] = 0;
                ++i;
            }
            return array2;
        }
        return array;
    }
    
    public abstract int b(final byte[] p0, final int p1) throws psc_en, psc_e1, psc_gw, psc_gx;
    
    public void b(final psc_dc psc_dc) throws psc_en, psc_bf, psc_gw {
        this.d(psc_dc, null);
    }
    
    public abstract void d(final psc_dc p0, final SecureRandom p1) throws psc_en, psc_bf, psc_gw;
    
    public abstract void x() throws psc_en, psc_gw;
    
    public psc_dt a(final byte[] array, final int n, final int n2, final boolean b) throws psc_en {
        return this.a(array, n, n2, b, null);
    }
    
    public abstract psc_dt a(final byte[] p0, final int p1, final int p2, final boolean p3, final String p4) throws psc_en;
    
    public psc_al b(final byte[] array, final int n, final int n2, final boolean b) throws psc_en {
        return this.b(array, n, n2, b, null);
    }
    
    public abstract psc_al b(final byte[] p0, final int p1, final int p2, final boolean p3, final String p4) throws psc_en;
    
    public psc_dc c(final byte[] array, final int n, final int n2, final boolean b, final String s) throws psc_en {
        return this.a(array, n, n2, b, s, null);
    }
    
    public abstract psc_dc a(final byte[] p0, final int p1, final int p2, final boolean p3, final String p4, final String p5) throws psc_en;
    
    public byte[] e(final byte[] array, final int n, final int n2) throws psc_en, psc_gw {
        if (n2 <= 0) {
            this.b(array, n, n2, null, 0);
            return new byte[0];
        }
        final int a = this.a(n2);
        if (a == 0) {
            this.b(array, n, n2, null, 0);
            return new byte[0];
        }
        final byte[] array2 = new byte[a];
        final int b = this.b(array, n, n2, array2, 0);
        if (b == 0) {
            return new byte[0];
        }
        if (b < array2.length) {
            final byte[] array3 = new byte[b];
            int i;
            for (i = 0; i < b; ++i) {
                array3[i] = array2[i];
                array2[i] = 0;
            }
            while (i < array2.length) {
                array2[i] = 0;
                ++i;
            }
            return array3;
        }
        return array2;
    }
    
    public abstract int b(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en, psc_gw;
    
    public byte[] z() throws psc_en, psc_e1, psc_gw, psc_gx {
        final int a = this.a(0);
        if (a == 0) {
            this.c(null, 0);
            return new byte[0];
        }
        final byte[] array = new byte[a];
        final int c = this.c(array, 0);
        if (c == 0) {
            return new byte[0];
        }
        if (c < array.length) {
            final byte[] array2 = new byte[c];
            int i;
            for (i = 0; i < c; ++i) {
                array2[i] = array[i];
                array[i] = 0;
            }
            while (i < array.length) {
                array[i] = 0;
                ++i;
            }
            return array2;
        }
        return array;
    }
    
    public abstract int c(final byte[] p0, final int p1) throws psc_en, psc_e1, psc_gw, psc_gx;
    
    protected void a(final psc_df psc_df) {
        this.a = psc_df.a;
        this.b = new String[psc_df.b.length];
        for (int i = 0; i < psc_df.b.length; ++i) {
            this.b[i] = psc_df.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        psc_df.c = new boolean[] { true };
        psc_df.d = new boolean[] { true, false, false };
        psc_df.e = new boolean[] { true, true, false, false };
        psc_df.f = new boolean[] { true, true, false };
        psc_df.g = new String[] { "com.rsa.jsafe.JA_AlgaeBlockCipher", "psc_di", "com.rsa.jsafe.JA_SymmetricPaddingScheme" };
        psc_df.h = new String[] { "com.rsa.jsafe.JA_AlgaeStreamCipher" };
        psc_df.i = new String[] { "com.rsa.jsafe.JA_AlgaeDigest", "com.rsa.jsafe.JA_AlgaeBlockCipher", "psc_di", "com.rsa.jsafe.JA_PasswordStandard" };
        psc_df.j = new String[] { "com.rsa.jsafe.JA_AlgaeDigest", "com.rsa.jsafe.JA_AlgaeStreamCipher", "com.rsa.jsafe.JA_PasswordStandard" };
    }
}
