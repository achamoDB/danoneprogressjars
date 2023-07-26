import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j4 extends psc_dc implements Cloneable, Serializable
{
    private psc_j3 a;
    private psc_j3 b;
    private psc_j3 c;
    private static final int d = 64;
    private static final int e = 192;
    private static final int f = 8;
    private static final int g = 16;
    private static final int h = 24;
    private int i;
    
    public psc_j4() {
        super("3DES_EDE", 64, 192);
    }
    
    public void a(final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        final int[] array2 = { 192 };
        this.i = 24;
        if (array != null) {
            if (array.length > 1) {
                throw new psc_be("Wrong number of parameters: Expected 1 for 3DES key generation");
            }
            switch (array[0]) {
                case 56:
                case 64: {
                    array2[0] = 64;
                    this.i = 8;
                    break;
                }
                case 112:
                case 128: {
                    array2[0] = 128;
                    this.i = 16;
                    break;
                }
                case 168:
                case 192: {
                    this.i = 24;
                    break;
                }
                default: {
                    throw new psc_be("Incorrect 3DES key length.");
                }
            }
        }
        super.a(array2, secureRandom);
    }
    
    boolean a(final byte[] array) {
        if (array.length != 24 && this.i == 24) {
            return true;
        }
        if (array.length != 16 && this.i == 16) {
            return true;
        }
        for (int i = 0; i < psc_j3.a.length; ++i) {
            int n;
            for (n = 0; n < 8 && array[n] == psc_j3.a[i][n]; ++n) {}
            if (n == 8) {
                return true;
            }
            if (this.i >= 16) {
                int n2;
                for (n2 = 0; n2 < 8 && array[n2 + 8] == psc_j3.a[i][n2]; ++n2) {}
                if (n2 == 8) {
                    return true;
                }
            }
            if (this.i == 24) {
                int n3;
                for (n3 = 0; n3 < 8 && array[n3 + 16] == psc_j3.a[i][n3]; ++n3) {}
                if (n3 == 8) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void t() throws psc_be {
        final int n = (super.m + 7) / 8;
        if (super.m < 0) {
            throw new psc_be("Cannot generate, object not initialized.");
        }
        final byte[] bytes = new byte[n];
        int n2 = 0;
        while (++n2 <= 100) {
            super.g.nextBytes(bytes);
            final int n3 = super.m % 8;
            if (n3 != 0) {
                bytes[0] = (byte)((bytes[0] & 0xFF) >>> 8 - n3);
            }
            if (!this.a(bytes)) {
                try {
                    this.a(bytes, 0, bytes.length);
                }
                catch (psc_bf psc_bf) {
                    throw new psc_be("Invalid Key Length for Generation.");
                }
                finally {
                    psc_au.c(bytes);
                }
                return;
            }
        }
        throw new psc_be("Invalid key length.");
    }
    
    int a(final int n) {
        switch (n) {
            case -1: {
                return 192;
            }
            case 1: {
                return 64;
            }
            case 2: {
                return 128;
            }
            case 3: {
                return 192;
            }
            default: {
                return -1;
            }
        }
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_bf {
        if (super.o != null) {
            psc_au.b(super.o, super.n);
            super.n = null;
            super.o = null;
        }
        super.o = new byte[24];
        switch (n2) {
            case 8: {
                System.arraycopy(array, n, super.o, 0, n2);
                System.arraycopy(array, n, super.o, n2, n2);
                System.arraycopy(array, n, super.o, n2 * 2, n2);
                break;
            }
            case 16: {
                System.arraycopy(array, n, super.o, 0, n2 / 2);
                System.arraycopy(array, n + n2 / 2, super.o, n2 / 2, n2 / 2);
                System.arraycopy(array, n, super.o, n2, n2 / 2);
                break;
            }
            case 24: {
                System.arraycopy(array, n, super.o, 0, n2);
                break;
            }
            default: {
                throw new psc_bf("Invalid key length");
            }
        }
        if (this.a == null) {
            this.a = new psc_j3();
        }
        if (this.b == null) {
            this.b = new psc_j3();
        }
        if (this.c == null) {
            this.c = new psc_j3();
        }
        this.a.a(super.o, 0, 8);
        this.b.a(super.o, 8, 8);
        this.c.a(super.o, 16, 8);
        (super.n = psc_au.b(super.o)).c();
    }
    
    public byte[] r() {
        final byte[] r = this.a.r();
        final byte[] r2 = this.b.r();
        final byte[] r3 = this.c.r();
        final byte[] array = new byte[r.length + r2.length + r3.length];
        final int n = 0;
        System.arraycopy(r, 0, array, n, r.length);
        psc_au.c(r);
        final int n2 = n + r.length;
        System.arraycopy(r2, 0, array, n2, r2.length);
        psc_au.c(r2);
        final int n3 = n2 + r2.length;
        System.arraycopy(r3, 0, array, n3, r3.length);
        psc_au.c(r2);
        final int n4 = n3 + r3.length;
        return array;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_j4 psc_j4 = (psc_j4)super.clone();
        if (this.a != null) {
            psc_j4.a = (psc_j3)this.a.clone();
        }
        if (this.b != null) {
            psc_j4.b = (psc_j3)this.b.clone();
        }
        if (this.c != null) {
            psc_j4.c = (psc_j3)this.c.clone();
        }
        return psc_j4;
    }
    
    protected psc_j3 e() {
        try {
            return (psc_j3)this.a.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    protected psc_j3 f() {
        try {
            return (psc_j3)this.b.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    protected psc_j3 g() {
        try {
            return (psc_j3)this.c.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        if (this.b != null) {
            this.b.y();
        }
        if (this.c != null) {
            this.c.y();
        }
        final psc_j3 a = null;
        this.c = a;
        this.b = a;
        this.a = a;
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
