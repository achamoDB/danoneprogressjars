import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_jl extends psc_jj implements Cloneable, Serializable
{
    private byte[] a;
    private int b;
    private static final byte c = 1;
    private static final byte d = 2;
    private static final byte e = 3;
    
    psc_jl() {
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null) {
            throw new psc_be("Incorrect number of parameters: expected one parameter.");
        }
        if (array.length != 1) {
            throw new psc_be("Incorrect number of parameters: expected one parameter.");
        }
        this.b = array[0];
        if (this.b <= 0) {
            throw new psc_be("PKIX PBE iteration count must be greater than 0.");
        }
    }
    
    public int[] c() {
        return new int[] { this.b };
    }
    
    public String d() {
        return "PKIXPBE";
    }
    
    public String e() {
        return "PKIXPBE";
    }
    
    public boolean h() {
        return false;
    }
    
    public void a(final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm, final byte[] array, final int n, final int n2) throws psc_ao {
        psc_lb.a(this, psc_dg, -1, array, n, n2);
    }
    
    public byte[] f() throws psc_ao {
        return psc_lb.a(this.b, this.a);
    }
    
    public byte[] a(final String s, final String s2) throws psc_ao {
        return psc_lb.a(s, s2, this.b, this.a);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
    }
    
    public byte[] g() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.a.length];
        System.arraycopy(this.a, 0, array, 0, this.a.length);
        return array;
    }
    
    protected byte[] a(final byte[] array) {
        final byte[] array2 = new byte[this.a.length + array.length];
        final int length = array.length;
        for (int i = 0; i < length; ++i) {
            array2[i] = array[i];
        }
        for (int n = length, length2 = this.a.length, j = 0; j < length2; ++j, ++n) {
            array2[n] = this.a[j];
        }
        return array2;
    }
    
    public void a(final psc_az psc_az, final psc_di psc_di, final int n, final psc_dc psc_dc) throws psc_en, psc_bf {
        if (this.b <= 0) {
            throw new psc_en("PKIX PBE iteration count must be greater than 0.");
        }
        final char[] j = psc_dc.j();
        byte[] array = null;
        if (j != null && j.length > 0) {
            array = new byte[j.length];
            for (int i = 0; i < j.length; ++i) {
                array[i] = (byte)j[i];
            }
        }
        final byte[] a = this.a(psc_az, array);
        psc_dc.a(a, 0, a.length);
        psc_au.c(a);
        if (psc_di == null) {
            psc_au.c(array);
            return;
        }
        final byte[] a2 = this.a(psc_az, array);
        psc_au.c(array);
        try {
            psc_di.b(a2, 0, a2.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not set the IV in PKIX PBE.");
        }
    }
    
    private byte[] a(final psc_az psc_az, final byte[] array) throws psc_en {
        final byte[] a = this.a(array);
        final byte[] array2 = new byte[psc_az.h()];
        psc_az.j();
        psc_az.a(a, 0, a.length);
        psc_az.b(array2, 0);
        for (int i = this.b - 1; i > 0; --i) {
            psc_az.j();
            psc_az.a(array2, 0, array2.length);
            psc_az.b(array2, 0);
        }
        psc_au.c(a);
        return array2;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jl psc_jl = new psc_jl();
        psc_jl.b = this.b;
        if (this.a != null) {
            psc_jl.a = this.a.clone();
        }
        return psc_jl;
    }
    
    public void y() {
        super.y();
        this.d(this.a);
        this.a = null;
        this.b = 0;
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
