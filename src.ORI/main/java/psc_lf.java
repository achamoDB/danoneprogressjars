import java.util.BitSet;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_lf
{
    public static final int a = 0;
    public static final int b = 1;
    public static int c;
    private static final short[] d;
    private static final int e = 52;
    private static final int f = 65521;
    
    public static int a() {
        return psc_lf.c;
    }
    
    public static void b(final int c) {
        psc_lf.c = c;
    }
    
    public static psc_lf a(final psc_e0 psc_e0, final psc_e0 psc_e2) throws psc_e1 {
        return b(psc_e0, new psc_ez(), new psc_ez(), new psc_ez(), psc_e2);
    }
    
    public static psc_lf b(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) throws psc_e1 {
        return a((a(psc_e0.o()) >= psc_lf.c) ? 1 : 0, psc_e0, psc_e2, psc_e3, psc_e4, psc_e5);
    }
    
    public static psc_lf a(final int n, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) throws psc_e1 {
        switch (n) {
            case 0: {
                return new lw(psc_e0, psc_e2, psc_e3, psc_e4, psc_e5);
            }
            case 1: {
                return new lx(psc_e0, psc_e2, psc_e3, psc_e4, psc_e5);
            }
            default: {
                throw new psc_e1("Invalid sieve type");
            }
        }
    }
    
    protected void a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) throws psc_e1 {
        this.c(a(psc_e0.o()));
        final int[] array = new int[2];
        int n;
        do {
            a(array);
            n = array[0];
            psc_e2.h(n);
            psc_e0.d(psc_e2, psc_e3);
            final int l = psc_e3.l();
            int i = 0;
            if (l != 0) {
                if (psc_e5 != null) {
                    i = a(psc_e5, psc_e2, psc_e4, n, l, this.b() - 1);
                }
                else if ((l & 0x1) == 0x1) {
                    i = (n - l) / 2;
                }
                else {
                    i = n - l / 2;
                }
            }
            while (i < this.b()) {
                this.e(i);
                i += n;
            }
        } while (n < 65521 && n > 0);
    }
    
    public abstract void c(final int p0);
    
    public abstract int b();
    
    public abstract boolean d(final int p0);
    
    public abstract void e(final int p0);
    
    static int a(final int n) {
        return (n <= 101) ? 1024 : (4 * n);
    }
    
    static void a(final int[] array) {
        if (array[1] > 52) {
            if (array[0] < 65521) {
                int i = array[0] + 2;
                do {
                    int n;
                    for (n = 0; n <= 52 && i % psc_lf.d[n] != 0; ++n) {}
                    if (n > 52) {
                        array[0] = i;
                        return;
                    }
                    i += 2;
                } while (i <= 65521);
            }
            array[1] = (array[0] = 0);
            return;
        }
        array[0] = psc_lf.d[array[1]];
        final int n2 = 1;
        ++array[n2];
    }
    
    static int a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final int n, int n2, final int n3) {
        try {
            psc_e0.d(psc_e2, psc_e3);
            final byte[] n4 = psc_e3.n();
            int n5 = 0;
            for (int i = 0; i < n4.length; ++i) {
                n5 = (n5 << 8) + (n4[i] & 0xFF);
            }
            int n6;
            for (n6 = 1, n2 = (n2 + n5) % n; n2 != 0 && n6 <= n3; n2 = (n2 + n5) % n, ++n6) {}
            return n6;
        }
        catch (psc_ap psc_ap) {
            return n3 + 1;
        }
    }
    
    static {
        psc_lf.c = 8192;
        d = new short[] { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251 };
    }
    
    static class lx extends psc_lf
    {
        BitSet a;
        
        public lx(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) throws psc_e1 {
            this.a(psc_e0, psc_e2, psc_e3, psc_e4, psc_e5);
        }
        
        public void c(final int nbits) {
            this.a = new BitSet(nbits);
        }
        
        public int b() {
            return this.a.size();
        }
        
        public boolean d(final int bitIndex) {
            return this.a.get(bitIndex);
        }
        
        public void e(final int bitIndex) {
            this.a.set(bitIndex);
        }
    }
    
    static class lw extends psc_lf
    {
        byte[] a;
        
        public lw(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) throws psc_e1 {
            this.a(psc_e0, psc_e2, psc_e3, psc_e4, psc_e5);
        }
        
        public void c(final int n) {
            this.a = new byte[n];
        }
        
        public int b() {
            return this.a.length;
        }
        
        public boolean d(final int n) {
            return this.a[n] != 0;
        }
        
        public void e(final int n) {
            this.a[n] = 1;
        }
    }
}
