import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kf extends psc_dr implements psc_et, Cloneable, Serializable
{
    public psc_kf() {
    }
    
    public psc_kf(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "PKCS1Block01Pad";
    }
    
    public boolean e() {
        return false;
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
            throw new psc_gx("Not enough space to perform padding");
        }
        final int n2 = n - j;
        if (o != null) {
            try {
                final psc_az psc_az = (psc_az)o;
                if (psc_az.i() != 0) {
                    final byte[] array2 = new byte[j];
                    System.arraycopy(array, i, array2, 0, j);
                    j = psc_az.a(array2, 0, array, i);
                }
            }
            catch (ClassCastException ex) {}
        }
        --j;
        int n3 = n - 1;
        while (j >= 0) {
            array[n3] = array[i + j];
            --n3;
            --j;
        }
        array[i] = (array[n3] = 0);
        array[i + 1] = 1;
        for (i += 2; i < n3; ++i) {
            array[i] = -1;
        }
        return n2;
    }
    
    public int a(final byte[] array, int i, int n, final Object o) throws psc_gx {
        if (array[i] != 0 || array[i + 1] != 1) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        int j;
        for (n += i, j = i + 2; j < n && array[j] == -1; ++j) {}
        if (j >= n) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        if (array[j] != 0) {
            throw new psc_gx("Cannot perform unpadding: incorrect format");
        }
        ++j;
        int length = n - j;
        if (o != null) {
            try {
                final psc_az psc_az = (psc_az)o;
                if (psc_az.i() != 0) {
                    if (psc_az.i() != length) {
                        throw new psc_gx("Cannot perform unpadding: incorrect format");
                    }
                    final byte[] f = psc_da.f(array, j);
                    if (psc_da.e(array, j).compareTo(psc_az.e()) != 0) {
                        throw new psc_gx("Cannot perform unpadding: incorrect format");
                    }
                    j = n - f.length;
                    length = f.length;
                    System.arraycopy(f, 0, array, j, length);
                }
            }
            catch (ClassCastException ex) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
            catch (psc_ap psc_ap) {
                throw new psc_gx("Cannot perform unpadding: incorrect format");
            }
        }
        while (j < n) {
            array[i] = array[j];
            ++j;
            ++i;
        }
        while (i < n) {
            array[i] = 0;
            ++i;
        }
        return length;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2, final Object o) throws psc_gx {
        return n2;
    }
    
    public byte[] k() throws psc_ao {
        return null;
    }
}
