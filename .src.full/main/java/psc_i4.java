import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i4 extends psc_dr implements psc_iu, Cloneable, Serializable
{
    private psc_ex a;
    private psc_eq b;
    private psc_it c;
    private int d;
    
    public psc_i4() {
    }
    
    public psc_i4(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "PKCS1V2OAEPPad";
    }
    
    public boolean e() {
        return true;
    }
    
    public void a(final psc_ex a, final psc_eq b, final psc_it c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = a.h();
    }
    
    public int a(final int n, final int n2) {
        if (n + (2 * this.d + 2) > n2) {
            return -1;
        }
        return n2 - n;
    }
    
    public int a(final int n) {
        return n - (2 * this.d + 2);
    }
    
    public String f() {
        if (this.a != null) {
            return this.a.e();
        }
        return null;
    }
    
    public String g() {
        if (this.b != null) {
            return this.b.d();
        }
        return null;
    }
    
    public String h() {
        if (this.b != null) {
            return this.b.e();
        }
        return null;
    }
    
    public String i() {
        if (this.c != null) {
            return this.c.c();
        }
        return null;
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (this.c != null) {
            this.c.a(array, n, n2);
        }
    }
    
    public byte[] j() {
        if (this.c != null) {
            return this.c.b();
        }
        return null;
    }
    
    public int a(final byte[] array, final int n, int i, final int n2, final Object o, final SecureRandom secureRandom) throws psc_gx {
        if (i + (2 * this.d + 2) > n2) {
            throw new psc_gx("Cannot perform padding: not enough space");
        }
        final int n3 = n2 - i;
        int n4 = n + n2 - 1;
        --i;
        while (i >= 0) {
            array[n4] = array[n + i];
            --n4;
            --i;
        }
        array[n4] = 1;
        for (int j = n + 2 * this.d + 1; j < n4; ++j) {
            array[j] = 0;
        }
        final int n5 = n + this.d + 1;
        this.c.a(this.a, array, n5, array, n4 + 1, i);
        final byte[] bytes = new byte[this.d];
        secureRandom.nextBytes(bytes);
        System.arraycopy(bytes, 0, array, n + 1, this.d);
        psc_au.c(bytes);
        final int n6 = n2 - this.d - 1;
        this.b.a(array, n + 1, this.d, array, n5, n6);
        this.b.a(array, n5, n6, array, n + 1, this.d);
        array[n] = 0;
        return n3;
    }
    
    public int a(final byte[] array, int n, final int n2, final Object o) throws psc_gx {
        if (array[n] != 0) {
            throw new psc_gx("Cannot perform unpadding: incorrect format.");
        }
        final int n3 = n + 1 + this.d;
        this.b.a(array, n3, n2 - 1 - this.d, array, n + 1, this.d);
        this.b.a(array, n + 1, this.d, array, n3, n2 - 1 - this.d);
        int i = n + 1 + 2 * this.d;
        while (i < n + n2) {
            if (array[i] == 0) {
                ++i;
            }
            else {
                if (array[i] == 1) {
                    break;
                }
                throw new psc_gx("Cannot perform unpadding: incorrect format.");
            }
        }
        if (i >= n + n2) {
            throw new psc_gx("Cannot perform unpadding: incorrect format.");
        }
        int n4 = i + 1;
        final int n5 = n2 - (n4 - n);
        if (!this.c.b(this.a, array, n + 1 + this.d, array, n4, n5)) {
            throw new psc_gx("Cannot perform unpadding: incorrect format.");
        }
        int j;
        for (j = 0; j < n5; ++j, ++n4, ++n) {
            array[n] = array[n4];
        }
        while (j < n2) {
            array[n] = 0;
            ++j;
            ++n;
        }
        return n5;
    }
    
    public byte[] k() throws psc_ao {
        try {
            return psc_nl.a(this.a.e(), this.b.d(), this.b.d(), this.c.c(), this.c.b());
        }
        catch (NullPointerException ex) {
            throw new psc_ao("Could not generate the DER of the OAEP AlgID parameters.");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_i4 psc_i4 = new psc_i4();
        if (this.a != null) {
            psc_i4.a = (psc_ex)this.a.clone();
        }
        if (this.b != null) {
            psc_i4.b = (psc_eq)this.b.clone();
        }
        if (this.c != null) {
            psc_i4.c = (psc_it)this.c.clone();
        }
        psc_i4.d = this.d;
        return psc_i4;
    }
}
