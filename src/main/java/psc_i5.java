import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i5 extends psc_dr implements psc_et, Cloneable, Serializable
{
    public psc_i5() {
    }
    
    public psc_i5(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "X931Pad";
    }
    
    public boolean e() {
        return false;
    }
    
    public int a(final int n, final int n2) {
        if (n2 < 128 || n2 % 32 != 0) {
            return -1;
        }
        return n2 - n;
    }
    
    public int a(final int n) {
        if (n < 128 || n % 32 != 0) {
            return 0;
        }
        return 20;
    }
    
    public int a(final byte[] array, final int n, final int n2, final int n3, final Object o, final SecureRandom secureRandom) throws psc_gx {
        if (n3 < 128 || n3 % 32 != 0) {
            throw new psc_gx("Invalid block size for X9.31 padding. Should be a multiple of 32 and no less than 32.");
        }
        if (n2 != 20) {
            throw new psc_gx("Invalid input for X9.31 padding.");
        }
        final int n4 = 51;
        if (o != null) {
            try {
                if (((psc_az)o).e().compareTo("SHA1") != 0) {
                    throw new psc_gx("Invalid digest for X9.31 padding.");
                }
            }
            catch (ClassCastException ex) {}
        }
        array[n + n3 - 1] = -52;
        array[n + n3 - 2] = (byte)n4;
        int i = n + n3 - 3;
        for (int j = n2 - 1; j >= 0; --j, --i) {
            array[i] = array[j + n];
        }
        array[i] = -70;
        --i;
        while (i > n) {
            array[i] = -69;
            --i;
        }
        array[n] = 107;
        return n3 - n2;
    }
    
    public int a(final byte[] array, int i, final int n, final Object o) throws psc_gx {
        if (array[i] != 107) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        final int n2 = 13260;
        int h = 20;
        if (o != null) {
            try {
                final psc_az psc_az = (psc_az)o;
                if (psc_az.e().compareTo("SHA1") != 0) {
                    throw new psc_gx("Unknown digest for X9.31 padding.");
                }
                h = psc_az.h();
            }
            catch (ClassCastException ex) {}
        }
        if (array[i + n - 1] != (byte)(n2 & 0xFF) || array[i + n - 2] != (byte)(n2 >> 8)) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        int j;
        for (j = i + 1; j < i + n && array[j] == -69; ++j) {}
        if (array[j] != -70) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        ++j;
        if (n + i - j - 2 != h) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        while (j < n - 2) {
            array[i] = array[j];
            ++j;
            ++i;
        }
        while (i < n) {
            array[i] = 0;
            ++i;
        }
        return h;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2, final Object o) throws psc_gx {
        return n2;
    }
    
    public byte[] k() throws psc_ao {
        return null;
    }
}
