// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_lj extends psc_an
{
    private String a;
    private String[] b;
    private static final boolean[] c;
    private static final String[] d;
    private static final String e = "psc_lj";
    
    public static psc_lj a(final String str, final String str2) throws psc_ao, psc_be {
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
                final psc_lj a3 = a(a2, a, array[i], array);
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
        throw new psc_ao("A JSAFE_Recode object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_lj a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 8, psc_lj.c, psc_lj.d, "psc_lj", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_lj) {
            return (psc_lj)a[0];
        }
        ((psc_jx)a[0]).a(psc_ba.b(array[0]));
        return new psc_lk((psc_jx)a[0]);
    }
    
    public String c() {
        return this.a;
    }
    
    public String[] d() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String e();
    
    public abstract int[] f();
    
    public abstract int a(final int p0);
    
    public abstract void g() throws psc_en;
    
    public byte[] a(final byte[] array, final int n, final int n2) throws psc_en {
        final byte[] array2 = new byte[this.a(n2)];
        final int a = this.a(array, n, n2, array2, 0);
        if (a == 0) {
            return new byte[0];
        }
        if (a == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[a];
        System.arraycopy(array2, 0, array3, 0, a);
        psc_au.c(array2);
        return array3;
    }
    
    public abstract int a(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en;
    
    public byte[] h() throws psc_en, psc_e1 {
        final byte[] array = new byte[this.a(0)];
        final int a = this.a(array, 0);
        if (a == 0) {
            return new byte[0];
        }
        if (a == array.length) {
            return array;
        }
        final byte[] array2 = new byte[a];
        System.arraycopy(array, 0, array2, 0, a);
        psc_au.c(array);
        return array2;
    }
    
    public abstract int a(final byte[] p0, final int p1) throws psc_en;
    
    public abstract void i() throws psc_en;
    
    public byte[] b(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        final byte[] array2 = new byte[this.a(n2)];
        final int b = this.b(array, n, n2, array2, 0);
        if (b == 0) {
            return new byte[0];
        }
        if (b == array2.length) {
            return array2;
        }
        final byte[] array3 = new byte[b];
        System.arraycopy(array2, 0, array3, 0, b);
        psc_au.c(array2);
        return array3;
    }
    
    public abstract int b(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en, psc_e1;
    
    public byte[] j() throws psc_en, psc_e1 {
        final byte[] array = new byte[this.a(0)];
        final int b = this.b(array, 0);
        if (b == 0) {
            return new byte[0];
        }
        if (b == array.length) {
            return array;
        }
        final byte[] array2 = new byte[b];
        System.arraycopy(array, 0, array2, 0, b);
        psc_au.c(array);
        return array2;
    }
    
    public abstract int b(final byte[] p0, final int p1) throws psc_en, psc_e1;
    
    protected void a(final psc_lj psc_lj) {
        this.a = psc_lj.a;
        this.b = psc_lj.b;
        for (int i = 0; i < psc_lj.b.length; ++i) {
            this.b[i] = psc_lj.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        c = new boolean[] { true };
        d = new String[] { "com.rsa.jsafe.JA_AlgaeRecode" };
    }
}
