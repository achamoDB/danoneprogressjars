// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h9
{
    psc_dw a;
    psc_dw b;
    psc_c2 c;
    psc_h6 d;
    psc_h7 e;
    byte[] f;
    byte[] g;
    byte[] h;
    byte[] i;
    String j;
    
    void a(final byte[] array, final byte[] array2, final byte[] array3, final boolean b) throws psc_d {
        try {
            final int n = this.b.f() * 2;
            this.h = new byte[n + (16 - n % 16) % 16];
            final psc_da a = psc_da.a("MD5", this.c.e());
            for (int n2 = this.h.length / 16, i = 0; i < n2; ++i) {
                final byte[] array4 = { (byte)(48 + i) };
                a.i();
                a.a(array, 0, array.length);
                a.a(array4, 0, 1);
                a.a(array2, 0, 16);
                a.a(array3, 0, array3.length);
                a.c(this.h, i * 16);
            }
            if (b) {
                final int a2 = this.b.a(this.h, 0);
                this.b.d(this.h, 0);
                this.b.b(this.h, a2);
            }
            else {
                final int b2 = this.b.b(this.h, 0);
                this.b.c(this.h, 0);
                this.b.a(this.h, b2);
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("An error occured while setting the secret keys: " + psc_ap.getMessage());
        }
    }
    
    public psc_dw d() {
        return this.a;
    }
    
    boolean a(final byte[] array, final byte[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
}
