import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_eo extends psc_an implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    private String d;
    private String[] e;
    private psc_eo[] f;
    private int g;
    private static final boolean[] h;
    private static final String[] i;
    private static boolean[] j;
    private static String[] k;
    private static final String l = "psc_eo";
    
    public static psc_eo a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        final psc_io psc_io = new psc_io();
        psc_io.a(array, n);
        final psc_eo a = a(psc_io.a, s);
        a.a(array, n);
        return a;
    }
    
    public static int d(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 1);
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
    
    public static psc_eo a(final String str, final String str2) throws psc_ao, psc_be {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final psc_eo[] f = new psc_eo[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_eo a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.d = array[i].c();
                    a3.e = array[i].d();
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
        throw new psc_ao("A JSAFE_Signature object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_eo a(String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length == 2) {
            array = new String[] { array[0], array[1], "NoPad" };
        }
        if (array.length == 5) {
            return b(array, array2, psc_bd, array3);
        }
        if (array.length != 3) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 11, psc_eo.h, psc_eo.i, "psc_eo", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_eo) {
            return (psc_eo)a[0];
        }
        ((psc_az)a[0]).a(psc_ba.b(array[0]));
        ((psc_ep)a[1]).a(psc_ba.b(array[1]));
        ((psc_et)a[2]).a(psc_ba.b(array[2]));
        if (!((psc_ep)a[1]).a((psc_az)a[0])) {
            throw new psc_be("Invalid digest algorithm");
        }
        if (!((psc_ep)a[1]).a((psc_et)a[2])) {
            throw new psc_be("Invalid padding scheme.");
        }
        return new psc_eu((psc_az)a[0], (psc_ep)a[1], (psc_et)a[2]);
    }
    
    private static psc_eo b(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        final Object[] a = psc_bd.a(array, null, 11, psc_eo.j, psc_eo.k, "psc_eo", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_eo) {
            return (psc_eo)a[0];
        }
        ((psc_ey)a[0]).a(psc_ba.b(array[0]));
        ((psc_ep)a[1]).a(psc_ba.b(array[1]));
        ((psc_es)a[2]).a(psc_ba.b(array[2]));
        ((psc_eq)a[3]).a(psc_ba.b(array[3]));
        ((psc_er)a[4]).a(psc_ba.b(array[4]));
        ((psc_eq)a[3]).a((psc_er)a[4]);
        ((psc_es)a[2]).a((psc_az)a[0], (psc_eq)a[3]);
        if (!((psc_ep)a[1]).a((psc_az)a[0])) {
            throw new psc_be("Invalid digest algorithm");
        }
        if (!((psc_ep)a[1]).a((psc_et)a[2])) {
            throw new psc_be("Invalid padding scheme.");
        }
        return new psc_eu((psc_az)a[0], (psc_ep)a[1], (psc_et)a[2]);
    }
    
    public byte[] j() throws psc_ao {
        return this.b(null, true);
    }
    
    public byte[] b(final String s, final boolean b) throws psc_ao {
        if (this.f == null) {
            return this.a(s, b);
        }
        if (this.g != -1) {
            return this.f[this.g].a(s, b);
        }
        for (int i = 0; i < this.f.length; ++i) {
            try {
                if (this.f[i] != null) {
                    return this.f[i].a(s, b);
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
    
    protected abstract byte[] a(final String p0, final boolean p1) throws psc_ao;
    
    public String k() {
        if (this.g != -1) {
            return this.f[this.g].d;
        }
        return this.d;
    }
    
    public String[] l() {
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
    
    public abstract String m();
    
    public abstract String n();
    
    public abstract String o();
    
    public int p() {
        if (this.g != -1) {
            return this.f[this.g].c();
        }
        return this.c();
    }
    
    protected abstract int c();
    
    public int q() {
        if (this.g != -1) {
            return this.f[this.g].d();
        }
        return this.d();
    }
    
    protected abstract int d();
    
    public abstract void a(final int p0);
    
    public abstract int r();
    
    public void a(final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_en, psc_bf {
        try {
            this.a(psc_dt, null, secureRandom, null);
        }
        catch (psc_be psc_be) {
            throw new psc_en(psc_be.getMessage());
        }
    }
    
    public void a(final psc_dt psc_dt, final psc_el psc_el, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_be {
        this.a(psc_dt, psc_el, secureRandom, null);
    }
    
    public void a(final psc_dt psc_dt, final psc_el psc_el, final SecureRandom secureRandom, final psc_nf[] array) throws psc_en, psc_bf, psc_be {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_dt, psc_el, secureRandom, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_dt, psc_el, secureRandom, array, array2);
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
            case 2: {
                throw new psc_be(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_dt p0, final psc_el p1, final SecureRandom p2, final psc_nf[] p3, final int[] p4);
    
    public void s() throws psc_en {
        if (this.f == null) {
            this.e();
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Cannot signReInit, not initialized.");
        }
        this.f[this.g].e();
    }
    
    protected abstract void e() throws psc_en;
    
    public void d(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        if (this.f == null) {
            this.a(array, n, n2);
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        this.f[this.g].a(array, n, n2);
    }
    
    protected abstract void a(final byte[] p0, final int p1, final int p2) throws psc_en, psc_e1;
    
    public byte[] t() throws psc_en, psc_e1, psc_gx {
        final byte[] array = new byte[this.q()];
        final int e = this.e(array, 0);
        if (e == 0) {
            return null;
        }
        if (e == array.length) {
            return array;
        }
        final byte[] array2 = new byte[e];
        for (int i = 0; i < e; ++i) {
            array2[i] = array[i];
        }
        this.d(array);
        return array2;
    }
    
    public int e(final byte[] array, final int n) throws psc_en, psc_e1, psc_gx {
        if (this.f == null) {
            return this.c(array, n);
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        return this.f[this.g].c(array, n);
    }
    
    protected abstract int c(final byte[] p0, final int p1) throws psc_en, psc_e1, psc_gx;
    
    public void a(final psc_al psc_al, final SecureRandom secureRandom) throws psc_en, psc_bf {
        try {
            this.a(psc_al, null, secureRandom, null);
        }
        catch (psc_be psc_be) {
            throw new psc_en(psc_be.getMessage());
        }
    }
    
    public void a(final psc_al psc_al, final psc_el psc_el, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_be {
        this.a(psc_al, psc_el, secureRandom, null);
    }
    
    public void a(final psc_al psc_al, final psc_el psc_el, final SecureRandom secureRandom, final psc_nf[] array) throws psc_en, psc_bf, psc_be {
        final int[] array2 = { 0 };
        String s = null;
        if (this.f == null) {
            s = this.a(psc_al, psc_el, secureRandom, array, array2);
            if (s == null) {
                return;
            }
        }
        else {
            this.g = -1;
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    s = this.f[i].a(psc_al, psc_el, secureRandom, array, array2);
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
            case 2: {
                throw new psc_be(s);
            }
            default: {
                throw new psc_en(s);
            }
        }
    }
    
    protected abstract String a(final psc_al p0, final psc_el p1, final SecureRandom p2, final psc_nf[] p3, final int[] p4);
    
    public void u() throws psc_en {
        if (this.f == null) {
            this.f();
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Cannot verifyReInit, not initialized.");
        }
        this.f[this.g].f();
    }
    
    protected abstract void f() throws psc_en;
    
    public void e(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        if (this.f == null) {
            this.b(array, n, n2);
            return;
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        this.f[this.g].b(array, n, n2);
    }
    
    protected abstract void b(final byte[] p0, final int p1, final int p2) throws psc_en, psc_e1;
    
    public boolean f(final byte[] array, final int n, final int n2) throws psc_en, psc_e1, psc_gx {
        if (this.f == null) {
            return this.c(array, n, n2);
        }
        if (this.g == -1) {
            throw new psc_en("Object not initialized.");
        }
        return this.f[this.g].c(array, n, n2);
    }
    
    protected abstract boolean c(final byte[] p0, final int p1, final int p2) throws psc_en, psc_e1, psc_gx;
    
    protected void a(final psc_eo psc_eo) {
        this.d = psc_eo.d;
        this.e = new String[psc_eo.e.length];
        for (int i = 0; i < psc_eo.e.length; ++i) {
            this.e[i] = psc_eo.e[i];
        }
        this.g = psc_eo.g;
        if (psc_eo.f != null) {
            this.f = new psc_eo[psc_eo.f.length];
            int n = 0;
            try {
                for (int j = 0; j < psc_eo.f.length; ++j) {
                    if (psc_eo.f[j] != null) {
                        if (n == 0) {
                            this.f[j] = this;
                            n = 1;
                        }
                        else {
                            this.f[j] = (psc_eo)psc_eo.f[j].clone();
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
            this.g();
        }
        else {
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    this.f[i].g();
                }
            }
        }
        this.g = -1;
    }
    
    protected abstract void g();
    
    static {
        h = new boolean[] { false, true, false };
        i = new String[] { "com.rsa.jsafe.JA_AlgaeDigest", "com.rsa.jsafe.JA_AlgaeSignature", "com.rsa.jsafe.JA_SignaturePaddingScheme" };
        psc_eo.j = new boolean[] { false, true, false, false, false };
        psc_eo.k = new String[] { "com.rsa.jsafe.JA_PSSDigest", "com.rsa.jsafe.JA_AlgaeSignature", "com.rsa.jsafe.JA_PSSPadding", "com.rsa.jsafe.JA_MaskGeneratingFunction", "com.rsa.jsafe.JA_MGFUnderlyingAlgorithm" };
    }
}
