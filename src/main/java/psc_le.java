import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_le
{
    private static short[] a;
    private static int b;
    private static int c;
    
    static int a(final int n) {
        return (n >= 512) ? 8 : 27;
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final SecureRandom secureRandom, final boolean b) throws psc_e1 {
        if (psc_e0.o() != 101) {
            throw new psc_e1("Cannot build a subprime, the length is inappropriate.");
        }
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        Label_0555: {
            try {
                psc_e0.a(0, 1);
                final Class<? extends psc_e0> class1 = psc_e0.getClass();
                psc_e3 = (psc_e0)class1.newInstance();
                psc_e4 = (psc_e0)class1.newInstance();
                psc_e5 = (psc_e0)class1.newInstance();
                psc_e0.n();
                final psc_lf b2 = psc_lf.b(psc_e0, psc_e3, psc_e4, psc_e5, null);
                int n = 0;
                for (int i = 0; i < b2.b(); ++i) {
                    if (b2.d(i)) {
                        ++n;
                    }
                    else {
                        psc_e0.l(n << 1);
                        n = 1;
                        psc_e0.d(psc_e2, psc_e4);
                        if (!psc_e4.k()) {
                            if (b) {
                                if (psc_e6 == null) {
                                    psc_e6 = (psc_e0)class1.newInstance();
                                }
                                if (psc_e7 == null) {
                                    psc_e7 = (psc_e0)class1.newInstance();
                                }
                                if (psc_e5 == null) {
                                    psc_e5 = (psc_e0)class1.newInstance();
                                }
                                if (a(psc_e0, psc_e3, psc_e4, psc_e6, secureRandom) && a(class1, psc_e0, psc_e3, psc_e4, psc_e6, psc_e7, psc_e5)) {
                                    final boolean b3 = true;
                                    break Label_0555;
                                }
                            }
                            else if (a(psc_e0, psc_e3, psc_e4)) {
                                final boolean b4 = true;
                                break Label_0555;
                            }
                        }
                    }
                }
                final boolean b5 = false;
                break Label_0555;
            }
            catch (InstantiationException ex) {
                throw new psc_e1("Could not build a prime (" + ex.getMessage() + ")");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_e1("Could not build a prime (" + ex2.getMessage() + ")");
            }
            catch (psc_ap psc_ap) {
                throw new psc_e1("Could not build a prime (" + psc_ap.getMessage() + ")");
            }
            finally {
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e6 != null) {
                    psc_e6.r();
                }
                Label_0682: {
                    if (psc_e7 != null) {
                        psc_e7.r();
                        break Label_0682;
                    }
                    break Label_0682;
                }
                while (true) {}
                // iftrue(Label_0581:, psc_e4 == null)
                // iftrue(Label_0516:, psc_e4 == null)
                // iftrue(Label_0475:, psc_e6 == null)
                // iftrue(Label_0451:, psc_e4 == null)
                // iftrue(Label_0439:, psc_e3 == null)
                // iftrue(Label_0569:, psc_e3 == null)
                // iftrue(Label_0504:, psc_e3 == null)
                // iftrue(Label_0605:, psc_e6 == null)
                // iftrue(Label_0552:, psc_e7 == null)
                // iftrue(Label_0540:, psc_e6 == null)
                // iftrue(Label_0463:, psc_e5 == null)
                // iftrue(Label_0487:, psc_e7 == null)
                // iftrue(Label_0593:, psc_e5 == null)
                // iftrue(Label_0528:, psc_e5 == null)
                while (true) {
                    final boolean b3;
                    final boolean b4;
                    final boolean b5;
                    Label_0451_Outer:Label_0475_Outer:
                    while (true) {
                        Block_27: {
                            while (true) {
                                Block_22: {
                                    while (true) {
                                    Block_31_Outer:
                                        while (true) {
                                            psc_e5.r();
                                            Label_0516: {
                                                Block_21_Outer:Block_23_Outer:
                                                while (true) {
                                                Label_0581:
                                                    while (true) {
                                                    Block_28_Outer:
                                                        while (true) {
                                                            Label_0528: {
                                                                break Label_0528;
                                                                while (true) {
                                                                Block_24_Outer:
                                                                    while (true) {
                                                                        Label_0593: {
                                                                            while (true) {
                                                                                while (true) {
                                                                                    while (true) {
                                                                                        while (true) {
                                                                                            Label_0439: {
                                                                                                Block_30: {
                                                                                                    break Block_30;
                                                                                                    Label_0552: {
                                                                                                        return b4;
                                                                                                    }
                                                                                                    psc_e5.r();
                                                                                                    break Label_0593;
                                                                                                    psc_e5.r();
                                                                                                    Block_25: {
                                                                                                        Label_0463: {
                                                                                                            break Label_0463;
                                                                                                            psc_e7.r();
                                                                                                            return b3;
                                                                                                            psc_e6.r();
                                                                                                            break Label_0451_Outer;
                                                                                                            break Block_25;
                                                                                                        }
                                                                                                        break Block_22;
                                                                                                        psc_e3.r();
                                                                                                        break Label_0439;
                                                                                                    }
                                                                                                    psc_e4.r();
                                                                                                    break Label_0516;
                                                                                                }
                                                                                                psc_e4.r();
                                                                                                break Label_0581;
                                                                                            }
                                                                                            break Block_31_Outer;
                                                                                            continue Block_24_Outer;
                                                                                        }
                                                                                        psc_e3.r();
                                                                                        continue Block_31_Outer;
                                                                                    }
                                                                                    psc_e3.r();
                                                                                    continue Block_24_Outer;
                                                                                }
                                                                                continue Block_28_Outer;
                                                                            }
                                                                            Label_0487: {
                                                                                return b3;
                                                                            }
                                                                            psc_e7.r();
                                                                            return b4;
                                                                        }
                                                                        continue Block_28_Outer;
                                                                    }
                                                                    continue Label_0451_Outer;
                                                                }
                                                            }
                                                            break Block_27;
                                                            continue Block_23_Outer;
                                                        }
                                                        continue Label_0451_Outer;
                                                    }
                                                    continue Block_21_Outer;
                                                }
                                                psc_e7.r();
                                                return b5;
                                            }
                                            continue Block_31_Outer;
                                        }
                                        psc_e4.r();
                                        continue Label_0475_Outer;
                                    }
                                }
                                psc_e6.r();
                                continue;
                            }
                            Label_0617: {
                                return b5;
                            }
                        }
                        psc_e6.r();
                        continue;
                    }
                    continue;
                }
            }
            // iftrue(Label_0617:, psc_e7 == null)
        }
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final int n, final SecureRandom secureRandom) throws psc_e1 {
        return a(psc_e0, psc_e2, psc_e3, psc_e4, n, secureRandom, null);
    }
    
    public static boolean a(final psc_e0 psc_e0, psc_e0 psc_e2, psc_e0 psc_e3, final psc_e0 psc_e4, final int n, final SecureRandom secureRandom, final byte[] array) throws psc_e1 {
        final int o = psc_e0.o();
        int n2 = 0;
        if (o < 101 || o > 2048) {
            throw new psc_e1("Cannot build a prime, the length is inappropriate.");
        }
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        psc_e0 psc_e8 = null;
        Label_0855: {
            try {
                psc_e0.a(0, 1);
                final Class<? extends psc_e0> class1 = psc_e0.getClass();
                psc_e5 = (psc_e0)class1.newInstance();
                psc_e6 = (psc_e0)class1.newInstance();
                if (psc_e2 != null && psc_e3 != null && psc_e4 != null) {
                    n2 = 1;
                    psc_e7 = (psc_e0)class1.newInstance();
                    psc_e8 = (psc_e0)class1.newInstance();
                    if (!a(class1, n, psc_e8, psc_e2, psc_e3, psc_e4, psc_e7)) {
                        final boolean b = false;
                        break Label_0855;
                    }
                    psc_e0.d(psc_e8, psc_e6);
                    psc_e7.c(psc_e0);
                    psc_e7.d(psc_e6);
                    if (psc_e0.b(psc_e7) > 0) {
                        psc_e7.a(psc_e8, psc_e0);
                    }
                    else {
                        psc_e0.a(psc_e7);
                    }
                    if (psc_e0.k(0) == 0) {
                        if (psc_e8.k(0) == 0) {
                            final boolean b2 = false;
                            break Label_0855;
                        }
                        psc_e0.c(psc_e8);
                    }
                    if (psc_e8.k(0) == 1) {
                        psc_e8.c(psc_e8);
                    }
                }
                final psc_lf b3 = psc_lf.b(psc_e0, psc_e5, psc_e6, psc_e7, psc_e8);
                int n3 = 0;
                for (int i = 0; i < b3.b(); ++i) {
                    if (b3.d(i)) {
                        ++n3;
                    }
                    else {
                        if (psc_e8 != null) {
                            psc_e5.h(n3);
                            psc_e8.c(psc_e5, psc_e7);
                            psc_e0.c(psc_e7);
                        }
                        else {
                            psc_e0.l(n3 << 1);
                        }
                        n3 = 1;
                        if (psc_e4 != null) {
                            psc_e0.d(psc_e4, psc_e6);
                            psc_e5.h(1);
                            if (psc_e5.b(psc_e6) == 0) {
                                continue;
                            }
                        }
                        if (n2 == 1) {
                            if (psc_e2 == null) {
                                psc_e2 = (psc_e0)class1.newInstance();
                            }
                            if (psc_e3 == null) {
                                psc_e3 = (psc_e0)class1.newInstance();
                            }
                            if (psc_e7 == null) {
                                psc_e7 = (psc_e0)class1.newInstance();
                            }
                            if (a(psc_e0, psc_e5, psc_e6, psc_e2, secureRandom) && a(class1, psc_e0, psc_e5, psc_e6, psc_e2, psc_e3, psc_e7)) {
                                final boolean b4 = true;
                                break Label_0855;
                            }
                        }
                        else if (a(psc_e0, psc_e5, psc_e6)) {
                            final boolean b5 = true;
                            break Label_0855;
                        }
                    }
                }
                final boolean b6 = false;
                break Label_0855;
            }
            catch (InstantiationException ex) {
                throw new psc_e1("Could not build a prime (" + ex.getMessage() + ")");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_e1("Could not build a prime (" + ex2.getMessage() + ")");
            }
            catch (psc_ap psc_ap) {
                throw new psc_e1("Could not build a prime (" + psc_ap.getMessage() + ")");
            }
            finally {
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e6 != null) {
                    psc_e6.r();
                }
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                Label_0958: {
                    if (psc_e8 != null) {
                        psc_e8.r();
                        break Label_0958;
                    }
                    break Label_0958;
                }
                while (true) {}
                // iftrue(Label_0787:, psc_e7 == null)
                // iftrue(Label_0828:, psc_e6 == null)
                // iftrue(Label_0869:, psc_e5 == null)
                // iftrue(Label_0905:, psc_e8 == null)
                // iftrue(Label_0852:, psc_e8 == null)
                // iftrue(Label_0681:, psc_e7 == null)
                // iftrue(Label_0763:, psc_e5 == null)
                // iftrue(Label_0693:, psc_e8 == null)
                // iftrue(Label_0881:, psc_e6 == null)
                // iftrue(Label_0799:, psc_e8 == null)
                // iftrue(Label_0734:, psc_e7 == null)
                // iftrue(Label_0816:, psc_e5 == null)
                // iftrue(Label_0710:, psc_e5 == null)
                // iftrue(Label_0657:, psc_e5 == null)
                // iftrue(Label_0746:, psc_e8 == null)
                // iftrue(Label_0669:, psc_e6 == null)
                // iftrue(Label_0775:, psc_e6 == null)
                // iftrue(Label_0893:, psc_e7 == null)
                // iftrue(Label_0840:, psc_e7 == null)
                // iftrue(Label_0722:, psc_e6 == null)
                final boolean b4;
                Block_40: {
                    while (true) {
                    Block_47_Outer:
                        while (true) {
                        Label_0828:
                            while (true) {
                            Label_0881:
                                while (true) {
                                    Block_41: {
                                        final boolean b;
                                        final boolean b2;
                                        final boolean b5;
                                        final boolean b6;
                                        Block_45_Outer:Block_36_Outer:Block_30_Outer:
                                        while (true) {
                                            psc_e6.r();
                                        Label_0763:
                                            while (true) {
                                            Label_0787_Outer:
                                                while (true) {
                                                    Block_35: {
                                                        Block_29: {
                                                            Block_37: {
                                                                while (true) {
                                                                    Label_0840_Outer:Block_33_Outer:
                                                                    while (true) {
                                                                        while (true) {
                                                                            while (true) {
                                                                            Label_0681_Outer:
                                                                                while (true) {
                                                                                    while (true) {
                                                                                        Block_31: {
                                                                                            Block_46: {
                                                                                                Block_48: {
                                                                                                    Label_0869: {
                                                                                                        while (true) {
                                                                                                            Label_0775: {
                                                                                                                break Label_0775;
                                                                                                                Label_0799: {
                                                                                                                    return b4;
                                                                                                                }
                                                                                                                psc_e5.r();
                                                                                                                break Label_0869;
                                                                                                            }
                                                                                                            break Label_0840_Outer;
                                                                                                            Label_0693: {
                                                                                                                return b;
                                                                                                            }
                                                                                                            psc_e8.r();
                                                                                                            return b2;
                                                                                                            break Block_45_Outer;
                                                                                                            continue Block_36_Outer;
                                                                                                        }
                                                                                                        Label_0852: {
                                                                                                            return b5;
                                                                                                        }
                                                                                                        break Block_48;
                                                                                                        while (true) {
                                                                                                            psc_e8.r();
                                                                                                            return b5;
                                                                                                            continue Label_0840_Outer;
                                                                                                        }
                                                                                                        while (true) {
                                                                                                            break Block_31;
                                                                                                            Label_0746:
                                                                                                            return b2;
                                                                                                            break Block_37;
                                                                                                            psc_e6.r();
                                                                                                            continue Block_30_Outer;
                                                                                                        }
                                                                                                        while (true) {
                                                                                                            psc_e8.r();
                                                                                                            return b;
                                                                                                            continue Label_0681_Outer;
                                                                                                        }
                                                                                                        Label_0905:
                                                                                                        return b6;
                                                                                                    }
                                                                                                    break Block_46;
                                                                                                }
                                                                                                psc_e8.r();
                                                                                                return b6;
                                                                                                psc_e5.r();
                                                                                                break Block_47_Outer;
                                                                                            }
                                                                                            psc_e6.r();
                                                                                            break Label_0881;
                                                                                        }
                                                                                        psc_e7.r();
                                                                                        continue Block_33_Outer;
                                                                                    }
                                                                                    break Block_40;
                                                                                    psc_e7.r();
                                                                                    continue Block_30_Outer;
                                                                                }
                                                                                break Block_35;
                                                                                break Block_41;
                                                                                continue Label_0787_Outer;
                                                                            }
                                                                            break Block_29;
                                                                            psc_e6.r();
                                                                            continue Block_47_Outer;
                                                                        }
                                                                        psc_e7.r();
                                                                        continue Label_0840_Outer;
                                                                    }
                                                                    psc_e7.r();
                                                                    continue Block_47_Outer;
                                                                }
                                                            }
                                                            psc_e5.r();
                                                            break Label_0763;
                                                        }
                                                        psc_e5.r();
                                                        break Label_0787_Outer;
                                                    }
                                                    psc_e7.r();
                                                    Label_0734: {
                                                        continue Block_30_Outer;
                                                    }
                                                }
                                                continue Block_47_Outer;
                                            }
                                            continue Block_45_Outer;
                                        }
                                        psc_e6.r();
                                        break Label_0828;
                                    }
                                    psc_e5.r();
                                    continue Block_47_Outer;
                                }
                                continue;
                            }
                            continue Block_47_Outer;
                        }
                        continue;
                    }
                }
                psc_e8.r();
                return b4;
            }
        }
    }
    
    private static boolean a(final Class clazz, final int n, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5, final psc_e0 psc_e6, final psc_e0 psc_e7) throws psc_e1 {
        if (!a(clazz, n, psc_e0, psc_e2, psc_e3, psc_e4, psc_e5)) {
            return false;
        }
        psc_e7.d(psc_e0, psc_e6);
        psc_e5.c(psc_e7);
        psc_e5.d(psc_e6);
        if (psc_e7.b(psc_e5) > 0) {
            psc_e5.a(psc_e0, psc_e7);
        }
        else {
            psc_e7.a(psc_e5);
        }
        return true;
    }
    
    public static boolean b(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final int n, final SecureRandom secureRandom, final byte[] array) throws psc_e1 {
        final int o = psc_e0.o();
        if (o < 101 || o > 2048) {
            throw new psc_e1("Cannot build a prime, the length is inappropriate.");
        }
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        psc_e0 psc_e8 = null;
        Label_0572: {
            try {
                psc_e0.a(0, 1);
                final Class<? extends psc_e0> class1 = psc_e0.getClass();
                psc_e5 = (psc_e0)class1.newInstance();
                psc_e6 = (psc_e0)class1.newInstance();
                psc_e7 = (psc_e0)class1.newInstance();
                psc_e8 = (psc_e0)class1.newInstance();
                final psc_e0 psc_e9 = (psc_e0)class1.newInstance();
                psc_e9.h(1);
                if (!a(class1, n, psc_e8, psc_e2, psc_e3, psc_e4, psc_e7, psc_e6, psc_e0)) {
                    final boolean b = false;
                    break Label_0572;
                }
                psc_a5.a(psc_e8.k(0) == 1);
                if (psc_e0.k(0) == 0) {
                    psc_e0.c(psc_e8);
                }
                psc_e8.c(psc_e8);
                final psc_lf b2 = psc_lf.b(psc_e0, psc_e5, psc_e6, psc_e7, psc_e8);
                int i = 0;
                while (i < b2.b()) {
                    psc_e0.d(psc_e4, psc_e6);
                    if (psc_e6.b(psc_e9) != 0) {
                        if (!b2.d(i)) {
                            if (a(psc_e0, psc_e5, psc_e6, psc_e2, secureRandom) && a(class1, psc_e0, psc_e5, psc_e6, psc_e2, psc_e3, psc_e7)) {
                                final boolean b3 = true;
                                break Label_0572;
                            }
                        }
                    }
                    ++i;
                    psc_e0.c(psc_e8);
                }
                final boolean b4 = false;
                break Label_0572;
            }
            catch (InstantiationException ex) {
                throw new psc_e1("Could not build a prime (" + ex.getMessage() + ")");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_e1("Could not build a prime (" + ex2.getMessage() + ")");
            }
            catch (psc_ap psc_ap) {
                psc_ap.printStackTrace();
                throw new psc_e1("Could not build a prime (" + psc_ap.getMessage() + ")");
            }
            finally {
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e6 != null) {
                    psc_e6.r();
                }
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                Label_0675: {
                    if (psc_e8 != null) {
                        psc_e8.r();
                        break Label_0675;
                    }
                    break Label_0675;
                }
                while (true) {}
                // iftrue(Label_0492:, psc_e6 == null)
                // iftrue(Label_0598:, psc_e6 == null)
                // iftrue(Label_0586:, psc_e5 == null)
                // iftrue(Label_0569:, psc_e8 == null)
                // iftrue(Label_0516:, psc_e8 == null)
                // iftrue(Label_0533:, psc_e5 == null)
                // iftrue(Label_0557:, psc_e7 == null)
                // iftrue(Label_0622:, psc_e8 == null)
                // iftrue(Label_0504:, psc_e7 == null)
                // iftrue(Label_0545:, psc_e6 == null)
                // iftrue(Label_0610:, psc_e7 == null)
                // iftrue(Label_0480:, psc_e5 == null)
                final boolean b3;
                Block_24: {
                    while (true) {
                    Label_0557_Outer:
                        while (true) {
                        Label_0504_Outer:
                            while (true) {
                            Block_23:
                                while (true) {
                                Block_19:
                                    while (true) {
                                        Label_0533: {
                                        Label_0492:
                                            while (true) {
                                                final boolean b4;
                                                while (true) {
                                                    Label_0610: {
                                                        Label_0545: {
                                                            Block_18: {
                                                                final boolean b;
                                                                Block_26_Outer:Block_20_Outer:
                                                                while (true) {
                                                                    psc_e5.r();
                                                                    break Label_0533;
                                                                Block_25_Outer:
                                                                    while (true) {
                                                                        while (true) {
                                                                            while (true) {
                                                                                psc_e6.r();
                                                                                break Label_0504_Outer;
                                                                                psc_e8.r();
                                                                                return b;
                                                                                Label_0569: {
                                                                                    return b3;
                                                                                }
                                                                                break Block_18;
                                                                                psc_e5.r();
                                                                                Label_0586:
                                                                                continue Block_20_Outer;
                                                                            }
                                                                            psc_e6.r();
                                                                            break Label_0545;
                                                                            continue Label_0557_Outer;
                                                                        }
                                                                        psc_e7.r();
                                                                        break Label_0610;
                                                                        break Block_24;
                                                                        continue Block_25_Outer;
                                                                    }
                                                                    psc_e8.r();
                                                                    return b4;
                                                                    continue Block_26_Outer;
                                                                }
                                                                Label_0516: {
                                                                    return b;
                                                                }
                                                            }
                                                            psc_e6.r();
                                                            break Label_0492;
                                                        }
                                                        break Block_23;
                                                    }
                                                    continue;
                                                }
                                                Label_0622: {
                                                    return b4;
                                                }
                                                psc_e5.r();
                                                continue Label_0557_Outer;
                                            }
                                            break Block_19;
                                        }
                                        continue Label_0557_Outer;
                                    }
                                    psc_e7.r();
                                    continue;
                                }
                                psc_e7.r();
                                continue Label_0504_Outer;
                            }
                            continue;
                        }
                        continue;
                    }
                }
                psc_e8.r();
                return b3;
            }
        }
    }
    
    public static boolean b(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final int n, final SecureRandom secureRandom) throws psc_e1 {
        return b(psc_e0, psc_e2, psc_e3, psc_e4, n, secureRandom, null);
    }
    
    static void a(final int[] array) {
        psc_lf.a(array);
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_e1 {
        if (psc_e0 == null) {
            throw new psc_e1("Candidate prime is null");
        }
        if (psc_e0.k()) {
            return false;
        }
        for (int i = 0; i < 4; ++i) {
            psc_e2.h(psc_le.a[i]);
            psc_e2.c(psc_e0, psc_e0, psc_e3);
            if (psc_e3.b(psc_e2) != 0) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean a(final Class clazz, final int n, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) {
        try {
            if (psc_e4.k(0) == 0) {
                return psc_e2.b(psc_e3) != 0 && a(clazz, n, psc_e0, psc_e2, psc_e3, psc_e5);
            }
            return a(clazz, psc_e0, psc_e2, psc_e3, psc_e5);
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        catch (IllegalAccessException ex) {
            return false;
        }
        catch (InstantiationException ex2) {
            return false;
        }
    }
    
    private static boolean a(final Class clazz, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4) {
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        Label_0221: {
            try {
                psc_e5 = clazz.newInstance();
                psc_e6 = clazz.newInstance();
                psc_e2.c(psc_e3, psc_e0);
                a(clazz, psc_e3, psc_e2, psc_e5);
                a(clazz, psc_e2, psc_e3, psc_e6);
                if (psc_e5.b(psc_e6) < 0) {
                    psc_e5.c(psc_e0);
                }
                psc_e5.b(psc_e6, psc_e4);
                final boolean b = true;
                break Label_0221;
            }
            catch (InstantiationException ex) {
                final boolean b2 = false;
                break Label_0221;
            }
            catch (IllegalAccessException ex2) {
                final boolean b2 = false;
                break Label_0221;
            }
            catch (psc_ap psc_ap) {
                final boolean b2 = false;
                break Label_0221;
            }
            finally {
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                Label_0276: {
                    if (psc_e6 != null) {
                        psc_e6.r();
                        break Label_0276;
                    }
                    break Label_0276;
                }
                while (true) {}
                // iftrue(Label_0235:, psc_e5 == null)
                // iftrue(Label_0218:, psc_e6 == null)
                // iftrue(Label_0206:, psc_e5 == null)
                // iftrue(Label_0160:, psc_e6 == null)
                // iftrue(Label_0148:, psc_e5 == null)
                // iftrue(Label_0189:, psc_e6 == null)
                // iftrue(Label_0177:, psc_e5 == null)
                // iftrue(Label_0247:, psc_e6 == null)
                final boolean b2;
                Block_15: {
                    Block_11: {
                    Label_0235:
                        while (true) {
                            Block_12: {
                            Block_13:
                                while (true) {
                                    Label_0177: {
                                        final boolean b;
                                    Block_9:
                                        while (true) {
                                            psc_e5.r();
                                            Label_0148: {
                                                break Label_0148;
                                                Block_14: {
                                                    break Block_14;
                                                    psc_e5.r();
                                                    break Label_0177;
                                                    break Block_13;
                                                }
                                                psc_e5.r();
                                                break Label_0235;
                                                break Block_12;
                                                Label_0160: {
                                                    return b;
                                                }
                                            }
                                            break Block_9;
                                            Label_0247: {
                                                return b2;
                                            }
                                            continue;
                                        }
                                        psc_e6.r();
                                        return b;
                                        Label_0189: {
                                            return b2;
                                        }
                                    }
                                    break Block_11;
                                    continue;
                                }
                                psc_e6.r();
                                return b2;
                            }
                            psc_e5.r();
                            continue;
                        }
                        break Block_15;
                        Label_0218: {
                            return b2;
                        }
                    }
                    psc_e6.r();
                    return b2;
                }
                psc_e6.r();
                return b2;
            }
        }
    }
    
    private static boolean a(final Class clazz, final int n, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4) throws psc_e1, InstantiationException, IllegalAccessException {
        final psc_e0 psc_e5 = clazz.newInstance();
        final psc_e0 psc_e6 = clazz.newInstance();
        final psc_e0 psc_e7 = clazz.newInstance();
        final psc_e0 psc_e8 = clazz.newInstance();
        final psc_e0 psc_e9 = clazz.newInstance();
        final psc_e0 psc_e10 = clazz.newInstance();
        psc_e2.c(psc_e3, psc_e6);
        psc_e5.h(8);
        psc_e6.d(psc_e5, psc_e7);
        psc_e7.e(psc_e5, psc_e8);
        psc_e8.c(psc_e6, psc_e7);
        psc_e6.c(psc_e5, psc_e0);
        psc_e6.h(n);
        psc_e7.c(psc_e6, psc_e8);
        psc_e3.c(psc_e5, psc_e6);
        psc_e6.d(psc_e2, psc_e7);
        psc_e7.e(psc_e2, psc_e6);
        psc_e6.c(psc_e5, psc_e7);
        psc_e7.c(psc_e3, psc_e9);
        psc_e2.c(psc_e5, psc_e6);
        psc_e6.d(psc_e3, psc_e7);
        psc_e7.e(psc_e3, psc_e6);
        psc_e6.c(psc_e5, psc_e7);
        psc_e7.c(psc_e2, psc_e10);
        psc_e8.a(psc_e9, psc_e4);
        if (psc_e4.b(psc_e10) < 0) {
            psc_e4.c(psc_e0);
        }
        psc_e4.d(psc_e10);
        return true;
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final SecureRandom secureRandom) {
        return a(psc_e0, psc_e2, psc_e3, psc_e4, secureRandom, a(psc_e0.o()));
    }
    
    public static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, SecureRandom secureRandom, final int n) {
        int n2 = 0;
        byte[] n3 = null;
        Label_0451: {
            try {
                int n4 = psc_e0.o() % 8;
                if (n4 != 0) {
                    n4 = 8 - n4;
                }
                final int n5 = 255 >> n4;
                final int a = a(psc_e0, psc_e2);
                if (a < 0) {
                    final boolean b = false;
                    break Label_0451;
                }
                n3 = psc_e0.n();
                if (secureRandom == null) {
                    secureRandom = new SecureRandom(n3);
                }
                secureRandom.setSeed(n3);
                for (int i = 0; i < n; ++i) {
                    do {
                        secureRandom.nextBytes(n3);
                        ++n2;
                        n3[0] &= (byte)n5;
                        psc_e3.a(n3, 0, n3.length);
                    } while (psc_e3.o() < 2 || psc_e3.b(psc_e0) >= 0);
                    if (!a(psc_e0, psc_e2, psc_e3, psc_e4, a)) {
                        final boolean b2 = false;
                        break Label_0451;
                    }
                }
                final boolean b3 = true;
                break Label_0451;
            }
            catch (psc_ap psc_ap) {
                final boolean b4 = false;
                break Label_0451;
            }
            finally {
                for (int n6 = n2 % 6; n6 != 0 && n6 < 6; ++n6) {
                    if (n3 == null) {
                        n3 = new byte[] { 0 };
                    }
                    secureRandom.nextBytes(n3);
                }
                if (n3 != null) {
                    psc_au.c(n3);
                }
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                Label_0602: {
                    if (psc_e3 != null) {
                        psc_e3.r();
                        break Label_0602;
                    }
                    break Label_0602;
                }
                while (true) {}
                // iftrue(Label_0495:, n2 == 0 || n2 >= 6)
                // iftrue(Label_0482:, n3 != null)
                // iftrue(Label_0341:, n2 == 0 || n2 >= 6)
                // iftrue(Label_0515:, psc_e2 == null)
                // iftrue(Label_0251:, n3 != null)
                // iftrue(Label_0428:, n3 == null)
                // iftrue(Label_0371:, psc_e3 == null)
                // iftrue(Label_0505:, n3 == null)
                // iftrue(Label_0405:, n3 != null)
                // iftrue(Label_0525:, psc_e3 == null)
                // iftrue(Label_0351:, n3 == null)
                // iftrue(Label_0274:, n3 == null)
                // iftrue(Label_0284:, psc_e2 == null)
                // iftrue(Label_0418:, n2 == 0 || n2 >= 6)
                // iftrue(Label_0438:, psc_e2 == null)
                // iftrue(Label_0361:, psc_e2 == null)
                // iftrue(Label_0448:, psc_e3 == null)
                // iftrue(Label_0264:, n2 == 0 || n2 >= 6)
                // iftrue(Label_0294:, psc_e3 == null)
                while (true) {
                    final boolean b;
                    final boolean b2;
                    final boolean b3;
                    final boolean b4;
                    Label_0306_Outer:Block_12_Outer:
                    while (true) {
                    Label_0361_Outer:
                        while (true) {
                        Label_0229:
                            while (true) {
                                Block_21: {
                                Label_0306:
                                    while (true) {
                                        Block_13_Outer:Block_22_Outer:Block_15_Outer:Block_24_Outer:Block_28_Outer:
                                        while (true) {
                                            while (true) {
                                                Label_0438: {
                                                    Label_0351: {
                                                        while (true) {
                                                            Label_0428: {
                                                                Label_0251: {
                                                                    while (true) {
                                                                        Label_0383: {
                                                                            Label_0505_Outer:Block_14_Outer:
                                                                            while (true) {
                                                                            Label_0274:
                                                                                while (true) {
                                                                                    while (true) {
                                                                                    Block_32:
                                                                                        while (true) {
                                                                                            Block_26: {
                                                                                                while (true) {
                                                                                                    Block_25: {
                                                                                                        while (true) {
                                                                                                            while (true) {
                                                                                                                Label_0515: {
                                                                                                                    Block_31: {
                                                                                                                        Block_33: {
                                                                                                                            while (true) {
                                                                                                                                while (true) {
                                                                                                                                    Block_30: {
                                                                                                                                        break Block_30;
                                                                                                                                        n3 = new byte[] { 0 };
                                                                                                                                        break Label_0251;
                                                                                                                                        psc_e3.r();
                                                                                                                                        return b;
                                                                                                                                    }
                                                                                                                                    break Block_31;
                                                                                                                                    psc_e3.r();
                                                                                                                                    return b2;
                                                                                                                                    break Label_0306_Outer;
                                                                                                                                    psc_au.c(n3);
                                                                                                                                    break Label_0351;
                                                                                                                                    psc_e2.r();
                                                                                                                                    break Label_0361_Outer;
                                                                                                                                    break Block_33;
                                                                                                                                    continue Block_22_Outer;
                                                                                                                                }
                                                                                                                                Label_0418: {
                                                                                                                                    break Block_26;
                                                                                                                                }
                                                                                                                                continue Label_0306_Outer;
                                                                                                                            }
                                                                                                                            Label_0495: {
                                                                                                                                break Block_32;
                                                                                                                            }
                                                                                                                            Label_0525:
                                                                                                                            return b4;
                                                                                                                            secureRandom.nextBytes(n3);
                                                                                                                            ++n2;
                                                                                                                            continue Label_0306;
                                                                                                                            psc_au.c(n3);
                                                                                                                            break Label_0274;
                                                                                                                        }
                                                                                                                        psc_e2.r();
                                                                                                                        break Label_0515;
                                                                                                                        n2 %= 6;
                                                                                                                        break Label_0383;
                                                                                                                        secureRandom.nextBytes(n3);
                                                                                                                        ++n2;
                                                                                                                        break Label_0383;
                                                                                                                        break Block_25;
                                                                                                                        psc_e3.r();
                                                                                                                        return b4;
                                                                                                                    }
                                                                                                                    n3 = new byte[] { 0 };
                                                                                                                    break Label_0505_Outer;
                                                                                                                }
                                                                                                                continue Block_28_Outer;
                                                                                                            }
                                                                                                            psc_e3.r();
                                                                                                            return b3;
                                                                                                            n3 = new byte[] { 0 };
                                                                                                            continue Block_14_Outer;
                                                                                                        }
                                                                                                        Label_0448: {
                                                                                                            return b3;
                                                                                                        }
                                                                                                    }
                                                                                                    n3 = new byte[] { 0 };
                                                                                                    continue Block_24_Outer;
                                                                                                }
                                                                                            }
                                                                                            psc_au.c(n3);
                                                                                            break Label_0428;
                                                                                            Label_0341: {
                                                                                                continue Block_15_Outer;
                                                                                            }
                                                                                        }
                                                                                        psc_au.c(n3);
                                                                                        continue Block_12_Outer;
                                                                                    }
                                                                                    Label_0264: {
                                                                                        continue Block_24_Outer;
                                                                                    }
                                                                                }
                                                                                continue Label_0505_Outer;
                                                                            }
                                                                            secureRandom.nextBytes(n3);
                                                                            ++n2;
                                                                            continue Block_13_Outer;
                                                                        }
                                                                        continue Block_28_Outer;
                                                                    }
                                                                    psc_e2.r();
                                                                    break Label_0438;
                                                                    n2 %= 6;
                                                                    break Label_0229;
                                                                }
                                                                secureRandom.nextBytes(n3);
                                                                ++n2;
                                                                break Label_0229;
                                                                Label_0371: {
                                                                    return b2;
                                                                }
                                                            }
                                                            continue;
                                                        }
                                                    }
                                                    break Block_21;
                                                }
                                                continue;
                                            }
                                            n2 %= 6;
                                            continue Block_13_Outer;
                                        }
                                        n2 %= 6;
                                        continue Label_0306;
                                    }
                                    Label_0294: {
                                        return b;
                                    }
                                }
                                psc_e2.r();
                                continue;
                            }
                            continue Label_0361_Outer;
                        }
                        continue Label_0306_Outer;
                    }
                    continue;
                }
            }
            // iftrue(Label_0328:, n3 != null)
        }
    }
    
    static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final int n) {
        try {
            psc_e3.c(psc_e2, psc_e0, psc_e4);
            int n2 = 0;
            while (psc_e4.o() != 1 || psc_e4.k(0) != 1) {
                psc_e4.l(1);
                if (psc_e4.b(psc_e0) == 0) {
                    return true;
                }
                if (++n2 >= n) {
                    return false;
                }
                psc_e4.m(1);
                psc_e4.a(psc_e4, psc_e0, psc_e3);
                psc_e4.a(psc_e3);
            }
            return n2 == 0;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        finally {
            if (psc_e4 != null) {
                psc_e4.r();
            }
        }
    }
    
    static int a(final psc_e0 psc_e0, final psc_e0 psc_e2) {
        try {
            psc_e2.a(psc_e0);
            psc_e2.m(1);
            int n;
            for (n = 0; psc_e2.k(n) == 0; ++n) {}
            if (n == 0) {
                return -1;
            }
            psc_e2.p(n);
            return n;
        }
        catch (psc_ap psc_ap) {
            return -1;
        }
    }
    
    static boolean a(final Class clazz, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5, final psc_e0 psc_e6) {
        psc_e0 psc_e7 = null;
        psc_e0 psc_e8 = null;
        Label_0418: {
            try {
                if (!a(psc_e0, psc_e2, psc_e3, psc_e4, psc_e5)) {
                    final boolean b = false;
                    break Label_0418;
                }
                psc_e7 = clazz.newInstance();
                psc_e8 = clazz.newInstance();
                psc_e3.a(psc_e0);
                psc_e3.l(1);
                final int o = psc_e3.o();
                psc_e4.a(psc_e3);
                psc_e4.p(1);
                psc_e5.h(1);
                psc_e6.h(1);
                for (int i = o - 2; i >= 0; --i) {
                    if (!a(psc_e3.k(i), psc_e0, psc_e2, psc_e4, psc_e5, psc_e6, psc_e7, psc_e8)) {
                        final boolean b2 = false;
                        break Label_0418;
                    }
                }
                if (psc_e5.o() == 1 && psc_e5.k(0) == 0) {
                    final boolean b3 = true;
                    break Label_0418;
                }
                break Label_0418;
            }
            catch (psc_ap psc_ap) {
                final boolean b4 = false;
                break Label_0418;
            }
            catch (InstantiationException ex) {
                final boolean b4 = false;
                break Label_0418;
            }
            catch (IllegalAccessException ex2) {
                final boolean b4 = false;
                break Label_0418;
            }
            finally {
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                Label_0473: {
                    if (psc_e8 != null) {
                        psc_e8.r();
                        break Label_0473;
                    }
                    break Label_0473;
                }
                while (true) {}
                final boolean b4;
                Label_0444: {
                    return b4;
                }
                // iftrue(Label_0328:, psc_e8 == null)
                // iftrue(Label_0270:, psc_e8 == null)
                // iftrue(Label_0357:, psc_e8 == null)
                // iftrue(Label_0415:, psc_e8 == null)
                // iftrue(Label_0345:, psc_e7 == null)
                // iftrue(Label_0386:, psc_e8 == null)
                // iftrue(Label_0444:, psc_e8 == null)
                // iftrue(Label_0258:, psc_e7 == null)
                // iftrue(Label_0432:, psc_e7 == null)
                // iftrue(Label_0299:, psc_e8 == null)
                // iftrue(Label_0374:, psc_e7 == null)
                // iftrue(Label_0316:, psc_e7 == null)
                // iftrue(Label_0403:, psc_e7 == null)
                // iftrue(Label_0287:, psc_e7 == null)
                while (true) {
                    Block_24: {
                        final boolean b;
                        Block_13: {
                            while (true) {
                                final boolean b2;
                                final boolean b3;
                                Label_0316_Outer:Block_20_Outer:Block_16_Outer:
                                while (true) {
                                    psc_e7.r();
                                    while (true) {
                                        while (true) {
                                            Label_0345_Outer:Block_12_Outer:
                                            while (true) {
                                                Label_0287: {
                                                    while (true) {
                                                    Block_19:
                                                        while (true) {
                                                            Block_18: {
                                                                while (true) {
                                                                    Block_21: {
                                                                        Block_17: {
                                                                            Label_0374: {
                                                                                Block_23: {
                                                                                    while (true) {
                                                                                        Label_0403: {
                                                                                            break Label_0403;
                                                                                            break Block_17;
                                                                                            psc_e7.r();
                                                                                            break Label_0374;
                                                                                            break Block_13;
                                                                                            Label_0328:
                                                                                            return b3;
                                                                                            break Block_19;
                                                                                        }
                                                                                        break Block_23;
                                                                                        psc_e7.r();
                                                                                        continue Label_0345_Outer;
                                                                                    }
                                                                                    break Block_18;
                                                                                    psc_e8.r();
                                                                                    return b4;
                                                                                }
                                                                                psc_e8.r();
                                                                                return b4;
                                                                                Label_0299:
                                                                                return b2;
                                                                                Label_0270:
                                                                                return b;
                                                                                psc_e7.r();
                                                                                break Label_0287;
                                                                            }
                                                                            break Block_21;
                                                                        }
                                                                        psc_e8.r();
                                                                        return b3;
                                                                        Label_0357:
                                                                        return false;
                                                                    }
                                                                    psc_e8.r();
                                                                    return b4;
                                                                    continue Block_16_Outer;
                                                                }
                                                            }
                                                            psc_e7.r();
                                                            continue Block_12_Outer;
                                                        }
                                                        psc_e8.r();
                                                        return false;
                                                        Label_0415:
                                                        return b4;
                                                        continue Block_16_Outer;
                                                    }
                                                    break Block_24;
                                                    Label_0386:
                                                    return b4;
                                                }
                                                psc_e8.r();
                                                return b2;
                                                psc_e7.r();
                                                continue Block_20_Outer;
                                            }
                                            continue Block_16_Outer;
                                        }
                                        continue;
                                    }
                                    continue Label_0316_Outer;
                                }
                                continue;
                            }
                        }
                        psc_e8.r();
                        return b;
                    }
                    psc_e7.r();
                    continue;
                }
            }
        }
    }
    
    static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5) {
        try {
            int n = 1;
            psc_e3.h(5);
            psc_e2.h(5);
            while (a(psc_e2, psc_e0, psc_e4, psc_e5) != -1) {
                psc_e3.l(2);
                n *= -1;
                if (n == -1) {
                    psc_e0.b(psc_e3, psc_e2);
                }
                else {
                    psc_e2.a(psc_e3);
                }
            }
            if (n == -1) {
                psc_e0.b(psc_e3, psc_e2);
            }
            else {
                psc_e2.a(psc_e3);
            }
            return true;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
    }
    
    static int a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4) {
        try {
            int n = 1;
            psc_e3.a(psc_e2);
            while (psc_e0.o() != 1 || psc_e0.k(0) != 0) {
                int n2;
                for (n2 = 0; psc_e0.k(n2) == 0; ++n2) {}
                psc_e0.p(n2);
                if ((n2 & 0x1) == 0x1 && psc_e3.k(1) + psc_e3.k(2) == 1) {
                    n *= -1;
                }
                if (psc_e0.k(1) + psc_e3.k(1) == 2) {
                    n *= -1;
                }
                psc_e3.d(psc_e0, psc_e4);
                psc_e3.a(psc_e0);
                psc_e0.a(psc_e4);
            }
            if (psc_e3.o() > 1) {
                n = 0;
            }
            return n;
        }
        catch (psc_ap psc_ap) {
            return 1;
        }
    }
    
    static boolean a(final int n, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final psc_e0 psc_e5, final psc_e0 psc_e6, final psc_e0 psc_e7) {
        try {
            psc_e4.c(psc_e4, psc_e6);
            psc_e2.c(psc_e6, psc_e7);
            psc_e5.c(psc_e5, psc_e6);
            psc_e6.c(psc_e7);
            psc_e3.a(psc_e6, psc_e0, psc_e7);
            psc_e5.a(psc_e4, psc_e0, psc_e6);
            psc_e4.a(psc_e6);
            psc_e5.a(psc_e7);
            if (n == 0) {
                return true;
            }
            psc_e2.c(psc_e4, psc_e6);
            psc_e6.c(psc_e5);
            psc_e3.a(psc_e6, psc_e0, psc_e7);
            psc_e4.a(psc_e5, psc_e6);
            psc_e3.a(psc_e6, psc_e0, psc_e4);
            psc_e5.a(psc_e7);
            return true;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
    }
    
    private static void a(final Class clazz, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_e1, InstantiationException, IllegalAccessException {
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        Label_0070: {
            try {
                psc_e4 = clazz.newInstance();
                psc_e5 = clazz.newInstance();
                psc_e0.d(psc_e2, psc_e4);
                psc_e4.e(psc_e2, psc_e5);
                psc_e5.c(psc_e0, psc_e3);
                break Label_0070;
            }
            finally {
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                Label_0125: {
                    if (psc_e5 != null) {
                        psc_e5.r();
                        break Label_0125;
                    }
                    break Label_0125;
                }
                while (true) {}
                Label_0096: {
                    return;
                }
                // iftrue(Label_0096:, psc_e5 == null)
                while (true) {
                    while (true) {
                        while (true) {
                            psc_e5.r();
                            return;
                            continue;
                        }
                        psc_e4.r();
                        continue;
                    }
                    continue;
                }
            }
            // iftrue(Label_0084:, psc_e4 == null)
        }
    }
    
    static {
        psc_le.a = new short[] { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251 };
        psc_le.b = 52;
        psc_le.c = 65521;
    }
}
