import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_iq extends psc_an implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    private String d;
    private String[] e;
    private psc_iq[] f;
    private int g;
    private static final boolean[] h;
    private static final String[] i;
    private static boolean[] j;
    private static String[] k;
    private static boolean[] l;
    private static String[] m;
    private static boolean[] n;
    private static String[] o;
    private static final String p = "psc_iq";
    
    public static psc_iq a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        final psc_nl psc_nl = new psc_nl();
        psc_nl.a(array, n);
        final psc_iq a = a(psc_nl.a, s);
        a.a(array, n);
        if (psc_nl.b != null) {
            a.a(psc_nl.b, psc_nl.c, psc_nl.d);
        }
        return a;
    }
    
    public static int e(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 2);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        if (this.f == null) {
            this.b(array, n);
            return;
        }
        for (int i = 0; i < this.f.length; ++i) {
            if (this.f[i] != null) {
                this.f[i].b(array, n);
            }
        }
    }
    
    protected abstract void b(final byte[] p0, final int p1) throws psc_ao, psc_be;
    
    public static psc_iq a(final String str, final String str2) throws psc_ao, psc_be {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final psc_iq[] f = new psc_iq[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_iq a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.d = array[i].c();
                    if (a3.e == null) {
                        a3.e = array[i].d();
                    }
                    f[i] = a3;
                }
            }
            catch (psc_be psc_be) {
                if (i >= a.length) {
                    int n;
                    for (n = 0; n < f.length && f[n] == null; ++n) {}
                    if (n >= f.length) {
                        throw psc_be;
                    }
                }
            }
        }
        int j = 0;
        int n2 = -1;
        while (j < f.length) {
            if (f[j] != null) {
                if (n2 == -1) {
                    n2 = j;
                    f[n2].f = f;
                }
                f[j].g = -1;
            }
            ++j;
        }
        if (n2 != -1) {
            return f[n2];
        }
        throw new psc_ao("A JSAFE_AsymmetricCipher object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_iq a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length == 1) {
            return b(array, array2, psc_bd, array3);
        }
        if (array.length == 6) {
            return c(array, array2, psc_bd, array3);
        }
        if (array.length != 2) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 0, psc_iq.h, psc_iq.i, "psc_iq", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_iq) {
            return (psc_iq)a[0];
        }
        ((psc_ir)a[0]).a(psc_ba.b(array[0]));
        ((psc_iv)a[1]).a(psc_ba.b(array[1]));
        return new psc_is((psc_ir)a[0], (psc_iv)a[1]);
    }
    
    private static psc_iq b(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        final Object[] a = psc_bd.a(array, null, 0, psc_iq.j, psc_iq.k, "psc_iq", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_iq) {
            return (psc_iq)a[0];
        }
        final String[] e = { psc_bd.d()[0], null };
        final String e2 = ((psc_ir)a[0]).e();
        final Object[] a2 = psc_bd.a(new String[] { e2 }, null, 0, psc_iq.l, psc_iq.m, null, array2, array3);
        if (a2 == null) {
            return null;
        }
        e[1] = psc_bd.d()[0];
        ((psc_ir)a[0]).a(psc_ba.b(array[0]));
        ((psc_iv)a2[0]).a(psc_ba.b(e2));
        final psc_is psc_is = new psc_is((psc_ir)a[0], (psc_iv)a2[0]);
        psc_is.e = e;
        return psc_is;
    }
    
    private static psc_iq c(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        final Object[] a = psc_bd.a(array, null, 0, psc_iq.n, psc_iq.o, "psc_iq", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_iq) {
            return (psc_iq)a[0];
        }
        ((psc_ir)a[0]).a(psc_ba.b(array[0]));
        ((psc_iu)a[1]).a(psc_ba.b(array[1]));
        ((psc_ex)a[2]).a(psc_ba.b(array[2]));
        ((psc_eq)a[3]).a(psc_ba.b(array[3]));
        ((psc_er)a[4]).a(psc_ba.b(array[4]));
        ((psc_it)a[5]).a(psc_ba.b(array[5]));
        ((psc_eq)a[3]).a((psc_er)a[4]);
        ((psc_iu)a[1]).a((psc_ex)a[2], (psc_eq)a[3], (psc_it)a[5]);
        return new psc_is((psc_ir)a[0], (psc_iv)a[1]);
    }
    
    public byte[] l() throws psc_ao {
        if (this.f == null) {
            return this.c();
        }
        if (this.g != -1) {
            return this.f[this.g].c();
        }
        for (int i = 0; i < this.f.length; ++i) {
            try {
                if (this.f[i] != null) {
                    return this.f[i].c();
                }
            }
            catch (psc_ao psc_ao) {
                if (i == this.f.length - 1) {
                    throw psc_ao;
                }
            }
        }
        throw new psc_ao("Unknown Alg ID");
    }
    
    protected abstract byte[] c() throws psc_ao;
    
    public String m() {
        if (this.g != -1) {
            return this.f[this.g].d;
        }
        return this.d;
    }
    
    public String[] n() {
        int i = 0;
        String[] array;
        if (this.g == -1) {
            array = new String[this.e.length];
            while (i < this.e.length) {
                array[i] = this.e[i];
                ++i;
            }
        }
        else {
            array = new String[this.f[this.g].e.length];
            while (i < this.f[this.g].e.length) {
                array[i] = this.f[this.g].e[i];
                ++i;
            }
        }
        return array;
    }
    
    public abstract void a(final byte[] p0, final int p1, final int p2);
    
    public abstract byte[] o();
    
    public abstract String p();
    
    public abstract String q();
    
    public abstract String r();
    
    public abstract String s();
    
    public abstract String t();
    
    public abstract String u();
    
    public int v() {
        if (this.g != -1) {
            return this.f[this.g].d();
        }
        return this.d();
    }
    
    protected abstract int d();
    
    public int w() {
        if (this.g != -1) {
            return this.f[this.g].e();
        }
        return this.e();
    }
    
    protected abstract int e();
    
    public int x() {
        if (this.g != -1) {
            return this.f[this.g].f();
        }
        return this.f();
    }
    
    protected abstract int f();
    
    public int b(final int n) {
        if (this.g != -1) {
            return this.f[this.g].a(n);
        }
        return this.a(n);
    }
    
    protected abstract int a(final int p0);
    
    public psc_em z() {
        final String p = this.p();
        final String m = this.m();
        try {
            return psc_em.a(p, m);
        }
        catch (psc_ao psc_ao) {
            return null;
        }
        catch (psc_be psc_be) {
            return null;
        }
    }
    
    public abstract int[] aa();
    
    public int[] ab() {
        return new int[0];
    }
    
    public abstract void c(final int p0);
    
    public abstract int ac();
    
    public void a(final psc_al psc_al) throws psc_en, psc_bf {
        this.a(psc_al, null, (psc_nf[])null);
    }
    
    public void a(final psc_al psc_al, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.a(psc_al, secureRandom, null);
    }
    
    public void a(final psc_al psc_al, final SecureRandom secureRandom, final psc_nf[] array) throws psc_en, psc_bf {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_al, secureRandom, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_al, secureRandom, array, array2);
                    if (s == null) {
                        this.g = i;
                        return;
                    }
                }
            }
        }
        switch (array2[0]) {
            case 1: {
                throw new psc_bf(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_al p0, final SecureRandom p1, final psc_nf[] p2, final int[] p3);
    
    public void a(final psc_dt psc_dt) throws psc_en, psc_bf {
        this.a(psc_dt, null, (psc_nf[])null);
    }
    
    public void a(final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.a(psc_dt, secureRandom, null);
    }
    
    public void a(final psc_dt psc_dt, final SecureRandom secureRandom, final psc_nf[] array) throws psc_en, psc_bf {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_dt, secureRandom, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_dt, secureRandom, array, array2);
                    if (s == null) {
                        this.g = i;
                        return;
                    }
                }
            }
        }
        switch (array2[0]) {
            case 1: {
                throw new psc_bf(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_dt p0, final SecureRandom p1, final psc_nf[] p2, final int[] p3);
    
    public void ad() throws psc_en {
        if (this.f == null) {
            this.g();
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Cannot encryptReInit, not initialized.");
        }
        this.f[this.g].g();
    }
    
    protected abstract void g() throws psc_en;
    
    public byte[] b(final psc_dc psc_dc, final boolean b) throws psc_en {
        if (this.f == null) {
            return this.a(psc_dc, b);
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        return this.f[this.g].a(psc_dc, b);
    }
    
    protected abstract byte[] a(final psc_dc p0, final boolean p1) throws psc_en;
    
    public byte[] b(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        final byte[] array2 = new byte[this.b(n2)];
        final int c = this.c(array, n, n2, array2, 0);
        if (c == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[c];
        for (int i = 0; i < c; ++i) {
            array3[i] = array2[i];
        }
        this.d(array2);
        return array3;
    }
    
    public int c(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en, psc_e1 {
        if (this.f == null) {
            return this.a(array, n, n2, array2, n3);
        }
        if (this.g == -1) {
            throw new psc_en("Cannot encryptUpdate, not initialized.");
        }
        return this.f[this.g].a(array, n, n2, array2, n3);
    }
    
    protected abstract int a(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en, psc_e1;
    
    public byte[] ae() throws psc_en, psc_e1, psc_gx {
        final byte[] array = new byte[this.b(0)];
        final int f = this.f(array, 0);
        if (f == array.length) {
            return array;
        }
        final byte[] array2 = new byte[f];
        for (int i = 0; i < f; ++i) {
            array2[i] = array[i];
        }
        this.d(array);
        return array2;
    }
    
    public int f(final byte[] array, final int n) throws psc_en, psc_e1, psc_gx {
        if (this.f == null) {
            return this.c(array, n);
        }
        if (this.g == -1) {
            throw new psc_en("Cannot encryptFinal, not initialized.");
        }
        return this.f[this.g].c(array, n);
    }
    
    protected abstract int c(final byte[] p0, final int p1) throws psc_en, psc_e1, psc_gx;
    
    public void b(final psc_dt psc_dt) throws psc_en, psc_bf {
        this.a(psc_dt, (psc_nf[])null);
    }
    
    public void a(final psc_dt psc_dt, final psc_nf[] array) throws psc_en, psc_bf {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_dt, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_dt, array, array2);
                    if (s == null) {
                        this.g = i;
                        return;
                    }
                }
            }
        }
        switch (array2[0]) {
            case 1: {
                throw new psc_bf(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_dt p0, final psc_nf[] p1, final int[] p2);
    
    public void b(final psc_al psc_al) throws psc_en, psc_bf {
        this.a(psc_al, (psc_nf[])null);
    }
    
    public void a(final psc_al psc_al, final psc_nf[] array) throws psc_en, psc_bf {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_al, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_al, array, array2);
                    if (s == null) {
                        this.g = i;
                        return;
                    }
                }
            }
        }
        switch (array2[0]) {
            case 1: {
                throw new psc_bf(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_al p0, final psc_nf[] p1, final int[] p2);
    
    public void af() throws psc_en {
        if (this.f == null) {
            this.h();
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Cannot decryptReInit, not initialized.");
        }
        this.f[this.g].h();
    }
    
    protected abstract void h() throws psc_en;
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final String s) throws psc_en {
        return this.b(array, n, n2, b, s, null);
    }
    
    public psc_dc b(final byte[] array, final int n, final int n2, final boolean b, final String s, final String s2) throws psc_en {
        if (this.f == null) {
            return this.a(array, n, n2, b, s, s2);
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        return this.f[this.g].a(array, n, n2, b, s, s2);
    }
    
    protected abstract psc_dc a(final byte[] p0, final int p1, final int p2, final boolean p3, final String p4, final String p5) throws psc_en;
    
    public byte[] c(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        final byte[] array2 = new byte[this.b(n2)];
        final int d = this.d(array, n, n2, array2, 0);
        if (d == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[d];
        for (int i = 0; i < d; ++i) {
            array3[i] = array2[i];
        }
        this.d(array2);
        return array3;
    }
    
    public int d(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en, psc_e1 {
        if (this.f == null) {
            return this.b(array, n, n2, array2, n3);
        }
        if (this.g == -1) {
            throw new psc_en("Cannot decryptUpdate, not initialized.");
        }
        return this.f[this.g].b(array, n, n2, array2, n3);
    }
    
    protected abstract int b(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en, psc_e1;
    
    public byte[] ag() throws psc_en, psc_e1, psc_gx {
        final byte[] array = new byte[this.b(0)];
        final int g = this.g(array, 0);
        if (g == array.length) {
            return array;
        }
        final byte[] array2 = new byte[g];
        for (int i = 0; i < g; ++i) {
            array2[i] = array[i];
        }
        this.d(array);
        return array2;
    }
    
    public int g(final byte[] array, final int n) throws psc_en, psc_e1, psc_gx {
        if (this.f == null) {
            return this.d(array, n);
        }
        if (this.g == -1) {
            throw new psc_en("Cannot decryptFinal, not initialized.");
        }
        return this.f[this.g].d(array, n);
    }
    
    protected abstract int d(final byte[] p0, final int p1) throws psc_en, psc_e1, psc_gx;
    
    protected void a(final psc_iq psc_iq) {
        this.d = psc_iq.d;
        this.e = new String[psc_iq.e.length];
        for (int i = 0; i < psc_iq.e.length; ++i) {
            this.e[i] = psc_iq.e[i];
        }
        this.g = psc_iq.g;
        if (psc_iq.f != null) {
            this.f = new psc_iq[psc_iq.f.length];
            int n = 0;
            try {
                for (int j = 0; j < psc_iq.f.length; ++j) {
                    if (psc_iq.f[j] != null) {
                        if (n == 0) {
                            this.f[j] = this;
                            n = 1;
                        }
                        else {
                            this.f[j] = (psc_iq)psc_iq.f[j].clone();
                        }
                    }
                }
            }
            catch (CloneNotSupportedException ex) {}
        }
    }
    
    public void y() {
        super.y();
        if (this.f == null) {
            this.i();
        }
        else {
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    this.f[i].i();
                }
            }
        }
        this.g = -1;
    }
    
    protected abstract void i();
    
    static {
        h = new boolean[] { true, false };
        i = new String[] { "com.rsa.jsafe.JA_AlgaeAsymmetricCipher", "com.rsa.jsafe.JA_AsymmetricPaddingScheme" };
        psc_iq.j = new boolean[] { true };
        psc_iq.k = new String[] { "com.rsa.jsafe.JA_AlgaeAsymmetricCipher" };
        psc_iq.l = new boolean[] { false };
        psc_iq.m = new String[] { "com.rsa.jsafe.JA_AsymmetricPaddingScheme" };
        psc_iq.n = new boolean[] { true, false, true, false, true, false };
        psc_iq.o = new String[] { "com.rsa.jsafe.JA_AlgaeAsymmetricCipher", "com.rsa.jsafe.JA_OAEPPaddingScheme", "com.rsa.jsafe.JA_OAEPDigest", "com.rsa.jsafe.JA_MaskGeneratingFunction", "com.rsa.jsafe.JA_MGFUnderlyingAlgorithm", "com.rsa.jsafe.JA_OAEPParameterSource" };
    }
}
