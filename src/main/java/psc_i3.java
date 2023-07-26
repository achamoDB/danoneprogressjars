import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i3 extends psc_dr implements psc_iv, Cloneable, Serializable
{
    public psc_i3() {
    }
    
    public psc_i3(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "PKCS1Block02Pad";
    }
    
    public boolean e() {
        return true;
    }
    
    public int a(final int n, final int n2) {
        if (n2 < 12 || n > n2 - 11) {
            return -1;
        }
        return n2 - n;
    }
    
    public int a(final int n) {
        if (n < 12) {
            return 0;
        }
        return n - 11;
    }
    
    public int a(final byte[] array, int i, int j, final int n, final Object o, final SecureRandom secureRandom) throws psc_gx {
        if (n < 12 || j > n - 11) {
            throw new psc_gx("Cannot perform padding: not enough space");
        }
        final int n2 = n - j;
        --j;
        int n3 = n - 1;
        while (j >= 0) {
            array[n3] = array[i + j];
            --n3;
            --j;
        }
        array[i] = (array[n3] = 0);
        array[i + 1] = 2;
        for (i += 2; i < n3; ++i) {
            byte b;
            do {
                b = (byte)(secureRandom.nextInt() & 0xFF);
            } while (b == 0);
            array[i] = b;
        }
        return n2;
    }
    
    public int a(final byte[] array, int i, int n, final Object o) throws psc_gx {
        try {
            if (array[i] != 0 || array[i + 1] != 2) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
            int j;
            for (n += i, j = i + 2; j < n && array[j] != 0; ++j) {}
            if (j == n) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
            final int n2 = n - j - 1;
            ++j;
            while (j < n) {
                array[i] = array[j];
                ++j;
                ++i;
            }
            while (i < n) {
                array[i] = 0;
                ++i;
            }
            return n2;
        }
        catch (psc_ap psc_ap) {
            throw new psc_gx("Could not perform unpadding: invalid input");
        }
    }
    
    public byte[] k() throws psc_ao {
        return null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return new psc_i3();
    }
}
