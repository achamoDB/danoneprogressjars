import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j3 extends psc_dc implements Cloneable, Serializable
{
    protected static final byte[][] a;
    private static final int b = 64;
    private static final int c = 64;
    
    public psc_j3() {
        super("DES", 64, 64);
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_bf {
        if (super.o != null) {
            psc_au.b(super.o, super.n);
            super.n = null;
            super.o = null;
        }
        if (n2 != 8) {
            throw new psc_bf("Invalid key data length. Should be 8 bytes long.");
        }
        System.arraycopy(array, n, super.o = new byte[n2], 0, n2);
        for (int i = 0; i < 8; ++i) {
            byte b = super.o[i];
            byte b2 = 1;
            for (int j = 0; j < 7; ++j) {
                b >>>= 1;
                b2 ^= b;
            }
            final byte b3 = (byte)(b2 & 0x1);
            final byte[] o = super.o;
            final int n3 = i;
            o[n3] &= 0xFFFFFFFE;
            final byte[] o2 = super.o;
            final int n4 = i;
            o2[n4] |= b3;
        }
        (super.n = psc_au.b(super.o)).c();
    }
    
    public void a(final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        final int[] array2 = { 64 };
        if (array != null) {
            if (array.length > 1) {
                throw new psc_be("Wrong number of parameters: Expected 1 for DES key generation - KeyLenInBits");
            }
            if (array[0] != 56 && array[0] != 64) {
                throw new psc_be("Incorrect DES key length. 56 or 64 bits are acceptable.");
            }
        }
        super.a(array2, secureRandom);
    }
    
    int a(final int n) {
        if (n == -1 || n == 56 || n == 64) {
            return 64;
        }
        return -1;
    }
    
    static {
        a = new byte[][] { { 1, 1, 1, 1, 1, 1, 1, 1 }, { -2, -2, -2, -2, -2, -2, -2, -2 }, { 31, 31, 31, 31, 31, 31, 31, 31 }, { -32, -32, -32, -32, -32, -32, -32, -32 }, { 1, -2, 1, -2, 1, -2, 1, -2 }, { 31, -32, 31, -32, 14, -15, 14, -15 }, { 1, -32, 1, -32, 1, -15, 1, -15 }, { 31, -2, 31, -2, 14, -2, 14, -2 }, { 1, 31, 1, 31, 1, 14, 1, 14 }, { -32, -2, -32, -2, -15, -2, -15, -2 }, { -2, 1, -2, 1, -2, 1, -2, 1 }, { -32, 31, -32, 31, -15, 14, -15, 14 }, { -32, 1, -32, 1, -15, 1, -15, 1 }, { -2, 31, -2, 31, -2, 14, -2, 14 }, { 31, 1, 31, 1, 14, 1, 14, 1 }, { -2, -32, -2, -32, -2, -15, -2, -15 } };
    }
}
