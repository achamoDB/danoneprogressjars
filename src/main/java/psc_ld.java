import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ld
{
    static final int a = 20;
    private static int b;
    
    psc_ld() {
    }
    
    static void a(final byte[] array, final int n, final byte[] array2, final int n2, final byte[] array3, final SecureRandom secureRandom, final Class clazz) throws psc_en {
        int i = 0;
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        byte[] bytes;
        byte[] k;
        byte[] l;
        byte[] m;
        Label_0462_Outer:Block_15_Outer:
        while (true) {
            try {
                if (n <= n2) {
                    throw new psc_en("Cannot generate p, q, g with a p smaller than q");
                }
                psc_e2 = clazz.newInstance();
                psc_e0 = clazz.newInstance();
                while (i == 0) {
                    if (!psc_e2.a(n2, null, 0, false, secureRandom)) {
                        continue;
                    }
                    if (!a(psc_e2, n, n2, secureRandom, clazz, psc_e0)) {
                        continue;
                    }
                    if (psc_e0.o() != n) {
                        continue;
                    }
                    bytes = new byte[(n + 7) / 8];
                    secureRandom.nextBytes(bytes);
                    psc_e3 = clazz.newInstance();
                    psc_e4 = clazz.newInstance();
                    psc_e3.a(bytes, 0, bytes.length);
                    for (int j = 0; j < bytes.length; ++j) {
                        bytes[j] = 0;
                    }
                    psc_e3.d(psc_e0, psc_e4);
                    psc_e5 = clazz.newInstance();
                    psc_e6 = clazz.newInstance();
                    psc_e0.b(psc_e2, psc_e5, psc_e6);
                    psc_e4.c(psc_e5, psc_e0, psc_e3);
                    psc_e5.a(psc_e3);
                    psc_e3.a(psc_e5, psc_e0, psc_e6);
                    if (psc_e3.b(psc_e6) == 0) {
                        continue;
                    }
                    i = 1;
                }
                k = psc_e0.j(array.length);
                l = psc_e2.j(array2.length);
                m = psc_e3.j(array3.length);
                System.arraycopy(k, 0, array, 0, k.length);
                psc_au.c(k);
                System.arraycopy(l, 0, array2, 0, l.length);
                psc_au.c(l);
                System.arraycopy(m, 0, array3, 0, m.length);
                psc_au.c(m);
                break Label_0462_Outer;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_e1 psc_e7) {
                throw new psc_en("Could not generate a prime with the given parameters.");
            }
            finally {
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                Label_0575: {
                    if (psc_e6 != null) {
                        psc_e6.r();
                        break Label_0575;
                    }
                    break Label_0575;
                }
                while (true) {}
                // iftrue(Label_0474:, psc_e4 == null)
                // iftrue(Label_0450:, psc_e0 == null)
                // iftrue(Label_0462:, psc_e3 == null)
                // iftrue(Label_0486:, psc_e5 == null)
            Block_20_Outer:
                while (true) {
                    while (true) {
                    Block_19_Outer:
                        while (true) {
                        Block_17:
                            while (true) {
                                Block_18: {
                                    break Block_18;
                                    psc_e2.r();
                                Label_0450:
                                    while (true) {
                                        Label_0438: {
                                            break Label_0438;
                                            psc_e6.r();
                                            Label_0498: {
                                                break Label_0498;
                                                psc_e0.r();
                                                break Label_0450;
                                                psc_e5.r();
                                                break Block_19_Outer;
                                            }
                                            continue Label_0462_Outer;
                                        }
                                        continue Block_19_Outer;
                                    }
                                    break Block_17;
                                }
                                psc_e4.r();
                                Label_0474: {
                                    continue;
                                }
                            }
                            psc_e3.r();
                            continue Block_15_Outer;
                        }
                        continue;
                    }
                    continue Block_20_Outer;
                }
            }
            // iftrue(Label_0498:, psc_e6 == null)
            // iftrue(Label_0438:, psc_e2 == null)
            break;
        }
    }
    
    private static boolean a(final psc_e0 psc_e0, final int n, final int n2, final SecureRandom secureRandom, final Class clazz, final psc_e0 psc_e2) throws psc_en {
        if (n <= n2) {
            throw new psc_en("JSAFE cannot generate P, Q, G if Q's length >= P's length.");
        }
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        int a = 0;
        byte[] array;
        int n3;
        psc_e0 psc_e6;
        int i;
        int[] array2;
        boolean b;
        Label_0469_Outer:Label_0493_Outer:Block_21_Outer:
        while (true) {
            try {
                psc_e3 = clazz.newInstance();
                psc_e0.a(psc_e0, psc_e3);
                array = new byte[(n + 7) / 8];
                secureRandom.nextBytes(array);
                n3 = n % 8;
                if (n3 != 0) {
                    n3 = 8 - n3;
                }
                array[0] |= (byte)192;
                array[0] = (byte)((array[0] & 0xFF) >>> n3);
                if (n3 == 7) {
                    array[1] |= (byte)128;
                }
                psc_e2.a(array, 0, array.length);
                secureRandom.nextBytes(array);
                psc_e4 = clazz.newInstance();
                psc_e5 = clazz.newInstance();
                psc_e6 = clazz.newInstance();
                psc_e2.b(psc_e0, psc_e4, psc_e5);
                psc_e2.d(psc_e5);
                if (psc_e2.k(0) == 1) {
                    psc_e2.c(psc_e0);
                }
                psc_e2.l(1);
                i = psc_e2.o();
                array2 = new int[2];
                while (i == n) {
                    b = false;
                    array2[0] = (array2[1] = 0);
                    psc_le.a(array2);
                    do {
                        psc_e4.h(array2[0]);
                        psc_e2.d(psc_e4, psc_e5);
                        if (psc_e5.o() == 1 && psc_e5.k(0) == 0) {
                            b = true;
                        }
                        psc_le.a(array2);
                    } while (!b && array2[1] < 53);
                    if (!b && psc_le.a(psc_e2, psc_e4, psc_e5)) {
                        a = (a(psc_e2, psc_e4, psc_e5, psc_e6, secureRandom) ? 1 : 0);
                    }
                    if (a == 1) {
                        break;
                    }
                    psc_e2.c(psc_e3);
                    i = psc_e2.o();
                }
                break Label_0469_Outer;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_e1 psc_e7) {
                throw new psc_en("Could not generate a prime with the given parameters.");
            }
            finally {
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                Label_0534: {
                    if (psc_e5 != null) {
                        psc_e5.r();
                        break Label_0534;
                    }
                    break Label_0534;
                }
                while (true) {}
                // iftrue(Label_0481:, psc_e4 == null)
                // iftrue(Label_0469:, psc_e3 == null)
                while (true) {
                    Label_0481: {
                        while (true) {
                            while (true) {
                                Block_19: {
                                    Block_20: {
                                        break Block_20;
                                        break Block_19;
                                    }
                                    psc_e4.r();
                                    break Label_0481;
                                    continue Label_0469_Outer;
                                }
                                psc_e3.r();
                                continue Label_0493_Outer;
                            }
                            psc_e5.r();
                            continue Block_21_Outer;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0493:, psc_e5 == null)
            break;
        }
    }
    
    public static void a(final byte[] array, final int n, final byte[] array2, final int n2, final byte[] array3, final byte[] array4, final byte[] bytes, final int[] array5, final SecureRandom secureRandom, final Class clazz) throws psc_en {
        if (n2 != 160) {
            throw new psc_en("primeQ must be exactly 160 bit long to comply with Fips 186");
        }
        if (n < 512 || n % 64 != 0) {
            throw new psc_en("FIPS 186-2 primeP should be a multiple of 64 and at least 512, or 1024 for FIPS");
        }
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        byte[] j;
        byte[] i;
        byte[] k;
        byte[] l;
        Label_0361_Outer:Label_0385_Outer:
        while (true) {
            try {
                psc_e0 = clazz.newInstance();
                psc_e2 = clazz.newInstance();
                psc_e3 = clazz.newInstance();
                psc_e4 = clazz.newInstance();
                psc_e4.h(2);
                try {
                    do {
                        secureRandom.nextBytes(bytes);
                    } while (!a(bytes, n, secureRandom, psc_e0, psc_e2, psc_e3, psc_e4, array5, clazz));
                }
                catch (psc_e1 psc_e5) {
                    throw new RuntimeException("Internal error: " + psc_e5.fillInStackTrace().getMessage());
                }
                j = psc_e0.j(array.length);
                i = psc_e2.j(array2.length);
                k = psc_e3.j(array3.length);
                l = psc_e4.j(array4.length);
                System.arraycopy(j, 0, array, 0, j.length);
                System.arraycopy(i, 0, array2, 0, i.length);
                System.arraycopy(k, 0, array3, 0, k.length);
                System.arraycopy(l, 0, array4, 0, l.length);
                break Label_0361_Outer;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_e1 psc_e6) {
                throw new psc_en("Could not generate a prime with the given parameters. " + psc_e6.getMessage());
            }
            finally {
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e2 != null) {
                    psc_e0.r();
                }
                if (psc_e3 != null) {
                    psc_e0.r();
                }
                Label_0450: {
                    if (psc_e4 != null) {
                        psc_e0.r();
                        break Label_0450;
                    }
                    break Label_0450;
                }
                while (true) {}
                return;
                // iftrue(Label_0373:, psc_e2 == null)
                // iftrue(Label_0397:, psc_e4 == null)
                // iftrue(Label_0385:, psc_e3 == null)
                // iftrue(Label_0361:, psc_e0 == null)
            Label_0397:
                while (true) {
                    Block_16: {
                        while (true) {
                            while (true) {
                                psc_e0.r();
                                Block_17: {
                                    Label_0373: {
                                        break Label_0373;
                                        break Block_17;
                                    }
                                    break Block_16;
                                }
                                psc_e0.r();
                                break Label_0397;
                                psc_e0.r();
                                continue Label_0385_Outer;
                            }
                            continue;
                        }
                    }
                    psc_e0.r();
                    continue;
                }
                continue Label_0361_Outer;
            }
            break;
        }
    }
    
    private static int a(final int n) {
        return 50;
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final SecureRandom secureRandom) {
        return psc_le.a(psc_e0, psc_e2, psc_e3, psc_e4, secureRandom, 50);
    }
    
    public static boolean a(final byte[] array, final int n, final SecureRandom secureRandom, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final int[] array2, final Class clazz) throws psc_e1, psc_en {
        if (!a(array, array.length * 8, secureRandom, psc_e2, clazz)) {
            return false;
        }
        if (!a(array, psc_e2, secureRandom, psc_e0, n, array2, clazz)) {
            return false;
        }
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        Label_0134: {
            try {
                psc_e5 = clazz.newInstance();
                psc_e6 = clazz.newInstance();
                psc_e0.b(psc_e2, psc_e5, psc_e6);
                psc_e4.c(psc_e5, psc_e0, psc_e3);
                final boolean b = true;
                break Label_0134;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            finally {
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                Label_0189: {
                    if (psc_e6 != null) {
                        psc_e6.r();
                        break Label_0189;
                    }
                    break Label_0189;
                }
                while (true) {}
                // iftrue(Label_0160:, psc_e6 == null)
                // iftrue(Label_0148:, psc_e5 == null)
                final boolean b;
            Block_10:
                while (true) {
                    psc_e5.r();
                    Label_0148: {
                        break Label_0148;
                        Label_0160: {
                            return b;
                        }
                    }
                    break Block_10;
                    continue;
                }
                psc_e6.r();
                return b;
            }
        }
    }
    
    private static boolean a(final byte[] array, final psc_e0 psc_e0, final SecureRandom secureRandom, final psc_e0 psc_e2, final int n, final int[] array2, final Class clazz) throws psc_e1, psc_en {
        final int n2 = (n - 1) / 160;
        final int n3 = (n - 1) % 160;
        final int n4 = array.length * 8;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        psc_e0 psc_e8 = null;
        psc_ew psc_ew;
        int n5;
        int i;
        Block_16_Outer:Block_11_Outer:Label_0405_Outer:Label_0429_Outer:
        while (true) {
            try {
                psc_e3 = clazz.newInstance();
                psc_e3.a(psc_e0);
                psc_e3.n(1);
                psc_e4 = clazz.newInstance();
                psc_e4.i(n - 1);
                psc_e7 = clazz.newInstance();
                psc_e5 = clazz.newInstance();
                psc_e6 = clazz.newInstance();
                psc_e8 = clazz.newInstance();
                psc_ew = new psc_ew();
                n5 = 2;
                i = 0;
                do {
                    a(array, n4, n2, n3, n5, psc_e5, psc_e6, psc_ew, psc_e8);
                    psc_e8.a(n - 1, 1);
                    psc_e8.d(psc_e3, psc_e7);
                    psc_e8.d(psc_e7);
                    psc_e8.l(1);
                    if (psc_e8.b(psc_e4) >= 0 && psc_le.a(psc_e8, psc_e5, psc_e6) && psc_le.a(psc_e8, psc_e5, psc_e6, psc_e7, secureRandom, a(n))) {
                        psc_e2.a(psc_e8);
                        array2[0] = i;
                        break;
                    }
                    ++i;
                    n5 += n2 + 1;
                } while (i < 4096);
                break Block_16_Outer;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_e1 psc_e9) {
                throw new psc_en(psc_e9.getMessage());
            }
            finally {
                if (psc_e8 != null) {
                    psc_e8.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                Label_0518: {
                    if (psc_e6 != null) {
                        psc_e6.r();
                        break Label_0518;
                    }
                    break Label_0518;
                }
                while (true) {}
                // iftrue(Label_0393:, psc_e3 == null)
                // iftrue(Label_0405:, psc_e7 == null)
                // iftrue(Label_0429:, psc_e5 == null)
                // iftrue(Label_0417:, psc_e4 == null)
                // iftrue(Label_0441:, psc_e6 == null)
                // iftrue(Label_0381:, psc_e8 == null)
                while (true) {
                    Block_15: {
                        while (true) {
                            Block_13: {
                                Label_0441: {
                                    while (true) {
                                        Label_0381_Outer:Label_0393_Outer:Label_0417_Outer:
                                        while (true) {
                                            psc_e6.r();
                                            break Label_0441;
                                            while (true) {
                                            Block_14:
                                                while (true) {
                                                    Block_12: {
                                                        while (true) {
                                                            break Block_12;
                                                            break Block_13;
                                                            break Block_15;
                                                            psc_e8.r();
                                                            continue Label_0393_Outer;
                                                        }
                                                        break Block_14;
                                                    }
                                                    psc_e3.r();
                                                    continue Label_0417_Outer;
                                                }
                                                psc_e4.r();
                                                continue Block_11_Outer;
                                            }
                                            continue Label_0381_Outer;
                                        }
                                        continue Label_0405_Outer;
                                    }
                                }
                                continue Block_16_Outer;
                            }
                            psc_e7.r();
                            continue Label_0429_Outer;
                        }
                    }
                    psc_e5.r();
                    continue;
                }
            }
            break;
        }
    }
    
    private static void a(final byte[] array, final int n, final int n2, final int n3, final int n4, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_ew psc_ew, final psc_e0 psc_e3) throws psc_e1 {
        final byte[] array2 = new byte[20];
        psc_e3.h(0);
        for (int i = 0; i <= n2; ++i) {
            a(array, n, i, n4, psc_e0, psc_ew, true, array2);
            psc_e2.a(array2, 0, 20);
            if (i == n2) {
                for (int j = n3; j < 160; ++j) {
                    psc_e2.a(j, 0);
                }
            }
            psc_e2.n(i * 160);
            psc_e3.c(psc_e2);
        }
    }
    
    private static boolean a(final byte[] array, final int n, final SecureRandom secureRandom, final psc_e0 psc_e0, final Class clazz) throws psc_e1, psc_en {
        boolean a = false;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        final psc_ew psc_ew = new psc_ew();
        Label_0245: {
            try {
                psc_e2 = clazz.newInstance();
                psc_e3 = clazz.newInstance();
                psc_e4 = clazz.newInstance();
                final byte[] array2 = new byte[20];
                final byte[] array3 = new byte[20];
                a(array, n, 0, 0, psc_e2, psc_ew, false, array2);
                a(array, n, 1, 0, psc_e2, psc_ew, true, array3);
                for (int i = 0; i < 20; ++i) {
                    final byte[] array4 = array2;
                    final int n2 = i;
                    array4[n2] ^= array3[i];
                }
                final byte[] array5 = array2;
                final int n3 = 0;
                array5[n3] |= (byte)128;
                final byte[] array6 = array2;
                final int n4 = 19;
                array6[n4] |= 0x1;
                psc_e0.a(array2, 0, 20);
                if (psc_le.a(psc_e0, psc_e2, psc_e3)) {
                    a = psc_le.a(psc_e0, psc_e2, psc_e3, psc_e4, secureRandom, psc_ld.b);
                }
                final boolean b = a;
                break Label_0245;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_e1 psc_e5) {
                throw new psc_en(psc_e5.getMessage());
            }
            finally {
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                Label_0324: {
                    if (psc_e4 != null) {
                        psc_e4.r();
                        break Label_0324;
                    }
                    break Label_0324;
                }
                while (true) {}
                // iftrue(Label_0271:, psc_e3 == null)
                // iftrue(Label_0259:, psc_e2 == null)
                while (true) {
                    Label_0271: {
                        while (true) {
                            psc_e3.r();
                            break Label_0271;
                            psc_e2.r();
                            continue;
                        }
                        psc_e4.r();
                        Label_0283: {
                            return;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0283:, psc_e4 == null)
        }
    }
    
    private static void a(final byte[] array, final int n, final int n2, final int n3, final psc_e0 psc_e0, final psc_ew psc_ew, final boolean b, final byte[] array2) throws psc_e1 {
        final int length = array.length;
        psc_e0.a(array, 0, length);
        try {
            psc_e0.l(n3 + n2);
            if (b) {
                psc_e0.a(n, 0);
            }
            final byte[] j = psc_e0.j(length);
            psc_ew.j();
            psc_ew.a(j, 0, length);
            psc_ew.b(array2, 0);
        }
        catch (psc_e1 psc_e2) {
            throw new psc_e1("Could not generate prime.");
        }
        catch (psc_en psc_en) {
            throw new psc_e1("Could not generate prime.");
        }
    }
    
    static {
        psc_ld.b = a(160);
    }
}
