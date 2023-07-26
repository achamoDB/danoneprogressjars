import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kg extends psc_dr implements psc_es, Cloneable, Serializable
{
    private psc_az a;
    private psc_eq b;
    private int c;
    private static final byte[] d;
    
    public psc_kg() {
    }
    
    public psc_kg(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "PKCS1V2PSS";
    }
    
    public boolean e() {
        return true;
    }
    
    public void a(final psc_az a, final psc_eq b) {
        this.a = a;
        this.b = b;
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length != 1) {
            throw new psc_be("Incorrect number of parameters: expected one.");
        }
        this.c = array[0];
        if (this.c != 1 && this.c != 2) {
            throw new psc_be("PKCS1V2PSS tfOption must be either 1 or 2.");
        }
    }
    
    public int a(final int n, final int n2) {
        if (8 * n + 8 * this.c + 1 > n2 * 8 - 1) {
            return -1;
        }
        return n2 - n;
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
    
    public int a(final int n) {
        return -1;
    }
    
    public int a(final byte[] array, final int n, final int n2, final int n3, final Object o, final SecureRandom secureRandom) throws psc_gx {
        final int n4 = n3 * 8 - 1;
        final int n5 = (n4 + 7) / 8;
        int n6 = n2;
        if (8 * n2 + 8 * this.c + 1 > n4) {
            throw new psc_gx("Cannot perform padding: not enough space");
        }
        final int n7 = n3 - n2;
        if (8 * n2 + 8 * n6 + 8 * this.c + 1 > n4) {
            n6 = 0;
        }
        final byte[] bytes = new byte[n6];
        secureRandom.nextBytes(bytes);
        final byte[] array2 = new byte[n2];
        try {
            this.a.j();
            this.a.a(psc_kg.d, 0, psc_kg.d.length);
            this.a.a(array, n, n2);
            this.a.a(bytes, 0, n6);
            this.a.b(array2, 0);
        }
        catch (Exception ex) {
            throw new psc_gx("Cannot perform padding: not enough space");
        }
        final int n8 = n5 - n6 - n2 - this.c - 1;
        final int n9 = n8 + 1 + n6;
        final byte[] array3 = new byte[n9];
        int i;
        for (i = 0; i < n8; ++i) {
            array3[i] = 0;
        }
        array3[i] = 1;
        System.arraycopy(bytes, 0, array3, i + 1, n6);
        this.b.a(array2, 0, n2, array3, 0, n9);
        final byte[] array4 = array3;
        final int n10 = 0;
        array4[n10] &= (byte)(255 >> 8 * n5 - n4);
        System.arraycopy(array3, 0, array, 0, n9);
        System.arraycopy(array2, 0, array, n9, n2);
        if (this.c == 1) {
            array[n5 - 1] = -68;
        }
        else if (this.c == 2) {
            array[n5 - 2] = 51;
            array[n5 - 1] = -52;
        }
        return n7;
    }
    
    public int a(final byte[] array, final int n, final int n2, final Object o) throws psc_gx {
        final int n3 = n2 * 8 - 1;
        final int n4 = (n3 + 7) / 8;
        final int h = this.a.h();
        if (this.c == 1) {
            if (array[n4 - 1] != -68) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
            if (this.c == 2 && (array[n4 - 2] != 51 || array[n4 - 1] != -52)) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
        }
        int n5 = h;
        if (8 * h + 8 * n5 + 8 * this.c + 1 > n3) {
            n5 = 0;
        }
        final int n6 = n4 - h - this.c;
        final byte[] array2 = new byte[n6];
        System.arraycopy(array, n, array2, 0, n6);
        this.b.a(array, n + n6, h, array2, 0, n6);
        final byte[] array3 = array2;
        final int n7 = 0;
        array3[n7] &= (byte)(255 >> 8 * n4 - n3);
        for (int i = 0; i < n4 - h - n5 - this.c - 1; ++i) {
            if (array2[i] != 0) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
        }
        if (array2[n4 - h - n5 - this.c - 1] != 1) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        System.arraycopy(array2, 0, array, 0, n6);
        return n4 - n6 - this.c;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2, final Object o) throws psc_gx {
        final int n3 = n * 8 - 1;
        if (o == null) {
            return -1;
        }
        int h;
        byte[] array3;
        try {
            final psc_az psc_az = (psc_az)o;
            h = psc_az.h();
            array3 = new byte[h];
            int n4 = h;
            if (2 * (8 * h) + 8 * this.c + 1 > n3) {
                n4 = 0;
            }
            psc_az.j();
            psc_az.a(psc_kg.d, 0, psc_kg.d.length);
            psc_az.a(array2, 0, n2);
            psc_az.a(array, n - n4 - h - this.c, n4);
            psc_az.b(array3, 0);
        }
        catch (Exception ex) {
            throw new psc_gx("Cannot perform unpadding");
        }
        System.arraycopy(array, n - this.c - h, array2, 0, h);
        System.arraycopy(array3, 0, array, 0, h);
        return h;
    }
    
    public byte[] k() throws psc_ao {
        try {
            return psc_io.a(this.a.e(), this.b.d(), this.b.d(), this.c);
        }
        catch (NullPointerException ex) {
            throw new psc_ao("Could not generate the DER of the PSS AlgID parameters.");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kg psc_kg = new psc_kg();
        if (this.a != null) {
            psc_kg.a = (psc_ey)this.a.clone();
        }
        if (this.b != null) {
            psc_kg.b = (psc_eq)this.b.clone();
        }
        psc_kg.c = this.c;
        return psc_kg;
    }
    
    static {
        d = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
