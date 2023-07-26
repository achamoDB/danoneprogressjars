import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kz extends psc_dr implements psc_dm
{
    public psc_kz() {
    }
    
    public psc_kz(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "ISO10126Padding";
    }
    
    public boolean e() {
        return true;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be {
    }
    
    public int a(final int n, final int n2) {
        if (n < 0) {
            return -1;
        }
        if (n2 == 1) {
            return 0;
        }
        return n2 - n % n2;
    }
    
    public int a(final byte[] array, final int n, final int n2, final int n3, final Object o, final SecureRandom secureRandom) throws psc_gx {
        if (n2 < 0) {
            throw new psc_gx("Negative input length to padding");
        }
        if (n3 == 1) {
            return 0;
        }
        final int n4 = n3 - n2 % n3;
        final int n5 = n2 + n + n4 - 1;
        array[n5] = (byte)n4;
        for (int i = n2 + n; i < n5; ++i) {
            array[i] = (byte)secureRandom.nextInt();
        }
        return n4;
    }
    
    public int a(final byte[] array, final int n, final int n2, final Object o) throws psc_gx {
        if (n2 == 1) {
            return 0;
        }
        if (n + n2 - 1 >= array.length) {
            throw new psc_gx("Could not perform unpadding: invalid input.");
        }
        final int n3 = array[n + n2 - 1] & 0xFF;
        if (n3 < 1 || n3 > n2) {
            throw new psc_gx("Could not perform unpadding: invalid pad byte.");
        }
        return n2 - n3;
    }
}
