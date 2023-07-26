import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j5 extends psc_dc implements Cloneable, Serializable
{
    public psc_j5() {
        super("HMAC", 0, 4096, 1);
    }
    
    public void a(final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        final int[] array2 = { 128 };
        if (array != null) {
            if (array.length != 1) {
                throw new psc_be("Wrong number of parameters: Expected 1 for HMAC key generation.");
            }
            if (array[0] < 0 || array[0] > 4096) {
                throw new psc_be("Incorrect HMAC key length.");
            }
            array2[0] = array[0];
        }
        super.a(array2, secureRandom);
    }
    
    int a(final int n) {
        if (n == -1) {
            return 128;
        }
        return n;
    }
}
