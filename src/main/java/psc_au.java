import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_au
{
    protected static psc_av a;
    private static boolean b;
    protected static final int c = 0;
    protected static final int d = 1;
    protected static final int e = 2;
    protected static final int f = 3;
    protected static final int g = 4;
    
    protected static final boolean a() {
        return psc_aq.c();
    }
    
    private static synchronized void c() {
        if (psc_au.a == null) {
            psc_av.c();
            final byte[] seed = SecureRandom.getSeed(20);
            (psc_au.a = new psc_ay(new psc_aw(), false)).g(seed);
            for (int i = 0; i < seed.length; ++i) {
                seed[i] = 0;
            }
        }
    }
    
    private static psc_av d() {
        if (psc_au.a == null) {
            c();
        }
        return psc_au.a;
    }
    
    static synchronized boolean b() {
        return psc_au.b;
    }
    
    public static synchronized void a(final boolean b) {
        psc_au.b = b;
        if (b && psc_au.a == null) {
            psc_au.a = d();
        }
    }
    
    static psc_dd a(final Object o) {
        return b(o);
    }
    
    static psc_dd b(final Object o) {
        final int d = d(o);
        if (d == 4) {
            return null;
        }
        return new psc_dd(o, d, psc_au.b);
    }
    
    static synchronized Object a(final Object o, final psc_dd psc_dd) {
        if (o == null) {
            return null;
        }
        long[] array = null;
        switch (d(o)) {
            case 0: {
                array = (long[])((byte[])o).clone();
                break;
            }
            case 1: {
                array = (long[])((short[])o).clone();
                break;
            }
            case 2: {
                array = (long[])((int[])o).clone();
                break;
            }
            case 3: {
                array = ((long[])o).clone();
                break;
            }
            default: {
                return null;
            }
        }
        if (psc_dd == null) {
            return array;
        }
        psc_dd.a(array);
        return array;
    }
    
    static void b(final Object o, final psc_dd psc_dd) {
        if (psc_dd != null) {
            c(o, psc_dd);
        }
        else {
            c(o);
        }
    }
    
    static void c(final Object o, final psc_dd psc_dd) {
        if (psc_dd == null) {
            return;
        }
        psc_dd.f();
    }
    
    static void c(final Object o) {
        if (o == null) {
            return;
        }
        switch (d(o)) {
            case 0: {
                if (psc_au.a == null) {
                    for (int i = 0; i < ((byte[])o).length; ++i) {
                        ((byte[])o)[i] = 0;
                    }
                    return;
                }
                psc_au.a.nextBytes((byte[])o);
            }
            case 1: {
                if (psc_au.a == null) {
                    for (int j = 0; j < ((short[])o).length; ++j) {
                        ((short[])o)[j] = 0;
                    }
                    return;
                }
                for (int k = 0; k < ((short[])o).length; ++k) {
                    ((short[])o)[k] = psc_au.a.s();
                }
            }
            case 2: {
                if (psc_au.a == null) {
                    for (int l = 0; l < ((int[])o).length; ++l) {
                        ((int[])o)[l] = 0;
                    }
                    return;
                }
                for (int n = 0; n < ((int[])o).length; ++n) {
                    ((int[])o)[n] = psc_au.a.nextInt();
                }
            }
            case 3: {
                if (psc_au.a == null) {
                    for (int n2 = 0; n2 < ((long[])o).length; ++n2) {
                        ((long[])o)[n2] = 0L;
                    }
                    return;
                }
                for (int n3 = 0; n3 < ((long[])o).length; ++n3) {
                    ((long[])o)[n3] = psc_au.a.nextLong();
                }
                break;
            }
        }
    }
    
    protected static int d(final Object o) {
        if (o instanceof byte[]) {
            return 0;
        }
        if (o instanceof short[]) {
            return 1;
        }
        if (o instanceof int[]) {
            return 2;
        }
        if (o instanceof long[]) {
            return 3;
        }
        return 4;
    }
    
    static {
        psc_au.a = null;
        psc_au.b = false;
    }
}
