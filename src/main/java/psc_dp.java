import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

class psc_dp extends psc_dk implements Cloneable, Serializable
{
    private psc_az a;
    private psc_dn b;
    private byte[] c;
    
    psc_dp(final psc_az a, final psc_dl psc_dl, final psc_di psc_di, final psc_dn b) {
        super(psc_dl, psc_di, new psc_dq());
        this.a = a;
        this.b = b;
    }
    
    private psc_dp() {
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        this.b.a(super.a, super.b, super.c, array, n, n2);
    }
    
    public byte[] f() throws psc_ao {
        return this.b.a(this.a, super.a, super.b, super.c);
    }
    
    public String l() {
        return this.a.e();
    }
    
    public String m() {
        return this.b.d();
    }
    
    public int[] s() {
        return this.b.c();
    }
    
    public void b(final int[] array) throws psc_be {
        this.b.a(array);
    }
    
    public void c(final byte[] array, final int n, final int n2) {
        final int c = this.c();
        if (c == 3 || c == 4 || c == 6 || c == 7) {
            System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
        }
        else {
            this.b.a(array, n, n2);
            this.c = null;
        }
    }
    
    public void b(final SecureRandom secureRandom) throws psc_en {
        if (secureRandom == null) {
            throw new psc_en("Salt generation needs a random object.");
        }
        final byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        this.c(bytes, 0, bytes.length);
    }
    
    public byte[] u() {
        return this.b.g();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_dp psc_dp = (psc_dp)super.clone();
        if (this.a != null) {
            psc_dp.a = (psc_az)this.a.clone();
        }
        if (this.b != null) {
            psc_dp.b = (psc_dn)this.b.clone();
        }
        if (this.c != null) {
            psc_dp.c = this.c.clone();
        }
        return psc_dp;
    }
    
    public void b(final byte[] array, final int n, final int n2) throws psc_gw {
        if (this.b != null) {
            if (this.b.h()) {
                super.b(array, n, n2);
            }
            return;
        }
        throw new psc_gw("With the current PBE, the IV must come from the password and salt.");
    }
    
    public void a(final SecureRandom secureRandom) throws psc_gw, psc_en {
        if (this.b != null) {
            if (this.b.h()) {
                super.a(secureRandom);
            }
            return;
        }
        throw new psc_gw("With the current PBE, the IV must come from the password and salt.");
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_gw {
        this.e();
        super.c(psc_dc, secureRandom);
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_gw {
        this.e();
        this.b.a(this.a, super.b, this.n(), psc_dc);
        super.c(psc_dc, secureRandom);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_gw {
        this.e();
        super.d(psc_dc, secureRandom);
    }
    
    public void d(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf, psc_gw {
        this.e();
        this.b.a(this.a, super.b, this.n(), psc_dc);
        super.d(psc_dc, secureRandom);
    }
    
    public void v() throws psc_en, psc_gw {
        this.e();
        super.v();
    }
    
    public void x() throws psc_en, psc_gw {
        this.e();
        super.x();
    }
    
    private void e() {
        final int c = this.c();
        if ((c == 3 || c == 4 || c == 6 || c == 7) && this.c != null) {
            this.b.a(this.c, 0, this.c.length);
            this.c = null;
        }
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        if (this.b != null) {
            this.b.y();
        }
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final String s) throws psc_en {
        psc_k3 psc_k3 = null;
        if (this.b instanceof psc_k3) {
            psc_k3 = (psc_k3)this.b;
        }
        byte[] b = array;
        int n3 = n;
        int length = n2;
        byte[] array2 = null;
        byte[] a = null;
        Label_0325: {
            try {
                if (psc_k3 != null) {
                    b = psc_k3.b(array, n, n2);
                }
                if (b != array) {
                    n3 = 0;
                    length = b.length;
                }
                array2 = new byte[this.a(b.length)];
                final int b2 = this.b(b, n3, length, array2, 0);
                final int n4 = b2 + this.c(array2, b2);
                if (psc_k3 != null) {
                    a = psc_k3.a(array2);
                }
                else {
                    a = array2;
                }
                final psc_dt a2 = psc_dt.a(a, 0, s);
                break Label_0325;
            }
            catch (psc_bf psc_bf) {
                throw new psc_en("Could not unwrap private key: " + psc_bf.getMessage());
            }
            catch (psc_ao psc_ao) {
                throw new psc_en("Could not unwrap private key: " + psc_ao.toString());
            }
            catch (psc_gw psc_gw) {
                throw new psc_en("Could not unwrap private key: " + psc_gw.getMessage());
            }
            catch (psc_e1 psc_e1) {
                throw new psc_en("Could not unwrap private key: " + psc_e1.getMessage());
            }
            catch (psc_gx psc_gx) {
                throw new psc_en("Could not unwrap private key: " + psc_gx.toString());
            }
            finally {
                if (array2 != null) {
                    psc_au.c(array2);
                }
                Label_0372: {
                    if (a != null) {
                        psc_au.c(a);
                        break Label_0372;
                    }
                    break Label_0372;
                }
                while (true) {}
                // iftrue(Label_0337:, array2 == null)
                psc_au.c(array2);
                // iftrue(Label_0347:, a == null)
                final psc_dt a2;
                Block_15: {
                    break Block_15;
                    Label_0347: {
                        return a2;
                    }
                }
                psc_au.c(a);
                return a2;
            }
        }
    }
    
    public byte[] a(final psc_dt psc_dt, final String s) throws psc_en {
        if (this.c() != 2) {
            throw new psc_en("Cannot wrap key, object needs new initialization.");
        }
        psc_k3 psc_k3 = null;
        if (this.b instanceof psc_k3) {
            psc_k3 = (psc_k3)this.b;
        }
        byte[] array = null;
        final byte[][] array2 = null;
    Label_0260_Outer:
        while (true) {
            try {
                final byte[][] b = psc_dt.b("RSAPrivateKeyBER");
                byte[] b2;
                array = (b2 = b[0]);
                if (psc_k3 != null) {
                    b2 = psc_k3.b(array);
                }
                if (b2 != b[0]) {
                    array = b2;
                }
                final byte[] array3 = new byte[this.a(array.length)];
                final int a = this.a(array, 0, array.length, array3, 0);
                final int n = a + this.b(array3, a);
                break Label_0260_Outer;
            }
            catch (psc_ao psc_ao) {
                throw new psc_en("The key given cannot be wrapped.");
            }
            catch (psc_gw psc_gw) {
                throw new psc_en(psc_gw.getMessage());
            }
            catch (psc_e1 psc_e1) {
                throw new psc_en(psc_e1.getMessage());
            }
            catch (psc_gx psc_gx) {
                throw new psc_en(psc_gx.getMessage());
            }
            catch (psc_bf psc_bf) {
                throw new psc_en(psc_bf.getMessage());
            }
            finally {
                Label_0334: {
                    if (array != null && array.length > 0) {
                        psc_au.c(array);
                        break Label_0334;
                    }
                    break Label_0334;
                }
                while (true) {}
                // iftrue(Label_0267:, psc_k3 != null)
                // iftrue(Label_0313:, array == null || array.length <= 0)
                // iftrue(Label_0260:, n == array3.length)
            Label_0313:
                while (true) {
                    return;
                    try {
                        Label_0267: {
                            return psc_k3.c(array3);
                        }
                    }
                    catch (psc_bf psc_bf2) {
                        throw new psc_en(psc_bf2.getMessage());
                    }
                    psc_au.c(array);
                    break Label_0313;
                    final int n;
                    final byte[] array4 = new byte[n];
                    System.arraycopy(array3, 0, array4, 0, n);
                    array3 = array4;
                    continue;
                }
                continue Label_0260_Outer;
            }
            break;
        }
    }
}
