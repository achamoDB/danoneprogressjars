import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j8 extends psc_dc implements Cloneable, Serializable
{
    private static final int a = 192;
    private static final int b = 192;
    private psc_j3 c;
    
    public psc_j8() {
        super("DESX", 192, 192);
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_bf {
        if (super.o != null) {
            psc_au.b(super.o, super.n);
            super.n = null;
            super.o = null;
        }
        if (this.c != null) {
            this.c.y();
        }
        else {
            this.c = new psc_j3();
        }
        if (n2 != 24) {
            throw new psc_bf("Invalid key data length. Should be 24.");
        }
        System.arraycopy(array, n, super.o = new byte[n2], 0, n2);
        this.c.a(super.o, 0, 8);
        (super.n = psc_au.b(super.o)).c();
    }
    
    public void a(final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        final int[] array2 = { 192 };
        if (array != null) {
            if (array.length > 1) {
                throw new psc_be("Wrong number of parameters: Expected 1 for DESX key generation. Key length in bits.");
            }
            if (array[0] != 120 && array[0] != 192) {
                throw new psc_be("Incorrect DESX key length. Expected 120 or 192 (bits).");
            }
        }
        super.a(array2, secureRandom);
    }
    
    int a(final int n) {
        if (n == -1 || n == 120 || n == 192) {
            return 192;
        }
        return -1;
    }
    
    psc_j3 e() {
        return this.c;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_j8 psc_j8 = (psc_j8)super.clone();
        if (this.c != null) {
            psc_j8.c = (psc_j3)this.c.clone();
        }
        return psc_j8;
    }
    
    public void y() {
        super.y();
        if (this.c != null) {
            this.c.y();
        }
        this.c = null;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
