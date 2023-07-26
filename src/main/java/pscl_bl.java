// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_bl extends pscl_bm
{
    public byte[] a;
    public byte[] b;
    public byte[] c;
    
    public pscl_bl(final byte[] array, final int n) throws pscl_bk {
        System.arraycopy(array, n, this.c = new byte[array.length - n], 0, this.c.length);
        try {
            final pscl_z pscl_z = new pscl_z(0, true, 0);
            final pscl_z pscl_z2 = new pscl_z(0, true, 0);
            final pscl_ac pscl_ac = new pscl_ac();
            final pscl_r pscl_r = new pscl_r(0, true, 0, 0);
            final pscl_aa pscl_aa = new pscl_aa(0, true, 0, null, 0, 0, 10, 11);
            final pscl_t pscl_t = new pscl_t(130816, true, 0, null, 0, 0);
            final pscl_t pscl_t2 = new pscl_t(130816, true, 0, null, 0, 0);
            final pscl_s pscl_s = new pscl_s(0, true, 0, null, 0, 0);
            pscl_u.a(array, n, new pscl_q[] { pscl_z, pscl_r, pscl_z2, pscl_aa, pscl_t, pscl_ac, pscl_s, pscl_t2, pscl_ac });
            final byte[] array2 = new byte[pscl_s.n];
            System.arraycopy(pscl_s.l, pscl_s.m, array2, 0, array2.length);
            final pscl_z pscl_z3 = new pscl_z(0, true, 0);
            final pscl_r pscl_r2 = new pscl_r(0, true, 0, null, 0, 0, true);
            final pscl_r pscl_r3 = new pscl_r(0, true, 0, null, 0, 0, true);
            final pscl_r pscl_r4 = new pscl_r(0, true, 0, null, 0, 0, true);
            final pscl_r pscl_r5 = new pscl_r(0, true, 0, null, 0, 0, true);
            pscl_u.a(array2, 0, new pscl_q[] { pscl_z3, pscl_r2, pscl_r3, pscl_r4, pscl_r5, new pscl_r(0, true, 0, null, 0, 0, true), new pscl_r(0, true, 0, null, 0, 0, true), new pscl_r(0, true, 0, null, 0, 0, true), new pscl_r(0, true, 0, null, 0, 0, true), new pscl_r(0, true, 0, null, 0, 0, true), pscl_ac });
            this.a = new byte[pscl_r3.n];
            System.arraycopy(pscl_r3.l, pscl_r3.m, this.a, 0, pscl_r3.n);
            this.b = new byte[pscl_r5.n];
            System.arraycopy(pscl_r5.l, pscl_r5.m, this.b, 0, pscl_r5.n);
        }
        catch (pscl_x pscl_x) {
            throw new pscl_bk("Could not load key");
        }
    }
    
    public pscl_bl(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws pscl_h {
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        System.arraycopy(array2, n3, this.b = new byte[n4], 0, n4);
    }
    
    public byte[] a() {
        final byte[] array = new byte[this.a.length];
        System.arraycopy(this.a, 0, array, 0, this.a.length);
        return array;
    }
    
    public byte[] b() {
        final byte[] array = new byte[this.b.length];
        System.arraycopy(this.b, 0, array, 0, this.b.length);
        return array;
    }
    
    public byte[] c() {
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        return array;
    }
    
    public void d() {
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = 0;
        }
        for (int j = 0; j < this.b.length; ++j) {
            this.b[j] = 0;
        }
    }
}
