// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lg extends psc_an
{
    static void a(final psc_jq psc_jq, final byte[] array, final int n) throws psc_bf {
        psc_r psc_r = null;
        psc_t psc_t = null;
        psc_i[] b = null;
        psc_p[] a = null;
        boolean b2 = false;
        int n2;
        int a2;
        int n3;
        int n4;
        int n5;
        int i;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        Label_0449_Outer:Block_16_Outer:Block_21_Outer:
        while (true) {
            Label_0367: {
                try {
                    psc_r = new psc_r(0, 3);
                    psc_t = new psc_t(0);
                    psc_lh.a(array, n, psc_r, null, psc_t);
                    if (psc_r.a.compareTo("RSA") != 0) {
                        throw new psc_bf("Invalid RSA key BER encoding.");
                    }
                    n2 = 2;
                    b = b(0, null);
                    psc_l.a(psc_t.b, psc_t.c, b);
                    if (b[10].b != null) {
                        a = a(b[10].b, b[10].c);
                        n2 += a.length / 3;
                    }
                    a2 = psc_jq.a(n2);
                    n3 = 5;
                    n4 = n2 - 2;
                    if (n4 == 0) {
                        n3 = -1;
                    }
                    n5 = 0;
                    i = 0;
                    n6 = -2;
                    n7 = 2;
                    n8 = 0;
                    while (i < a2) {
                        if (i == n3) {
                            b2 = psc_jq.a(a[n8].b, a[n8].c, a[n8].d, i, n6);
                            ++n8;
                            ++n3;
                            if (--n4 == 0) {
                                n4 = n2 - 2;
                                n3 += 2;
                                if (++n5 >= 2) {
                                    --n3;
                                }
                            }
                        }
                        else {
                            b2 = psc_jq.a(b[n7].b, b[n7].c, b[n7].d, i, n6);
                            ++n7;
                        }
                        if (!b2) {
                            break;
                        }
                        ++i;
                        ++n6;
                    }
                    break Label_0449_Outer;
                    break Label_0367;
                }
                catch (psc_m psc_m) {
                    throw new psc_bf("Could not decode RSA key from BER: " + psc_m.getMessage());
                }
                finally {
                    if (psc_r != null) {
                        psc_r.i();
                    }
                    if (psc_t != null) {
                        psc_t.i();
                    }
                    if (b != null) {
                        for (int j = 0; j < b.length; ++j) {
                            if (b[j] != null) {
                                b[j].i();
                            }
                        }
                    }
                    Label_0578: {
                        if (a != null) {
                            for (int k = 0; k < a.length; ++k) {
                                if (a[k] != null) {
                                    a[k].i();
                                }
                            }
                            break Label_0578;
                        }
                        break Label_0578;
                    }
                    while (true) {}
                    // iftrue(Label_0479:, n9 >= a.length)
                    // iftrue(Label_0382:, b2)
                    // iftrue(Label_0479:, a == null)
                    // iftrue(Label_0441:, n10 >= b.length)
                    // iftrue(Label_0393:, psc_r == null)
                    // iftrue(Label_0435:, b[n10] == null)
                    // iftrue(Label_0403:, psc_t == null)
                    // iftrue(Label_0441:, b == null)
                    while (true) {
                        Block_20: {
                            while (true) {
                            Block_15_Outer:
                                while (true) {
                                    break Block_20;
                                    throw new psc_bf("Invalid RSA key BER encoding.");
                                Label_0435_Outer:
                                    while (true) {
                                    Label_0393:
                                        while (true) {
                                            Block_17: {
                                            Label_0411_Outer:
                                                while (true) {
                                                    while (true) {
                                                        Block_19: {
                                                            break Block_19;
                                                            psc_t.i();
                                                            break Block_15_Outer;
                                                            ++n10;
                                                            break Label_0411;
                                                            Label_0479: {
                                                                continue Label_0449_Outer;
                                                            }
                                                        }
                                                        n9 = 0;
                                                        continue Block_15_Outer;
                                                        break Block_17;
                                                        n10 = 0;
                                                        continue Block_16_Outer;
                                                    }
                                                    b[n10].i();
                                                    continue Label_0411_Outer;
                                                }
                                                a[n9].i();
                                                break Label_0435_Outer;
                                                Label_0382: {
                                                    return;
                                                }
                                                while (true) {
                                                    psc_r.i();
                                                    break Label_0393;
                                                    continue;
                                                }
                                            }
                                            continue Block_21_Outer;
                                        }
                                        continue Label_0435_Outer;
                                    }
                                    ++n9;
                                    continue Block_15_Outer;
                                }
                                continue Block_21_Outer;
                            }
                        }
                        continue;
                    }
                }
                // iftrue(Label_0473:, a[n9] == null)
            }
            break;
        }
    }
    
    static byte[] a(final int n, final byte[][] array) throws psc_bf {
        psc_i[] b = null;
        byte[] a = null;
        Label_0052: {
            try {
                b = b(n, array);
                a = psc_l.a(b);
                final byte[] a2 = psc_lh.a("RSA", null, a);
                break Label_0052;
            }
            catch (psc_m psc_m) {
                throw new psc_bf("Could not compute RSA Private Key BER.");
            }
            finally {
                if (a != null) {
                    psc_au.c(a);
                }
                Label_0197: {
                    if (b != null) {
                        for (int i = 0; i < b.length; ++i) {
                            if (b[i] != null) {
                                if (i == 10 && b[10].b != null) {
                                    psc_au.c(b[10].b);
                                }
                                b[i].i();
                            }
                        }
                        break Label_0197;
                    }
                    break Label_0197;
                }
                while (true) {}
                // iftrue(Label_0110:, n2 != 10 || b[10].b == null)
                // iftrue(Label_0062:, a == null)
                // iftrue(Label_0123:, n2 >= b.length)
                final byte[] a2;
                int n2 = 0;
                Label_0117_Outer:Label_0069_Outer:
                while (true) {
                    Block_7: {
                        while (true) {
                        Label_0069:
                            while (true) {
                                while (true) {
                                    Block_10: {
                                        while (true) {
                                            psc_au.c(a);
                                            break Label_0069;
                                            break Block_10;
                                            continue Label_0117_Outer;
                                        }
                                        ++n2;
                                        break Label_0069;
                                    }
                                    psc_au.c(b[10].b);
                                    Label_0110: {
                                        break Label_0110;
                                        Label_0123: {
                                            return a2;
                                        }
                                    }
                                    b[n2].i();
                                    continue Label_0069_Outer;
                                }
                                break Block_7;
                                n2 = 0;
                                continue Label_0069;
                            }
                            continue;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0123:, b == null)
            // iftrue(Label_0117:, b[n2] == null)
        }
    }
    
    private static psc_i[] b(final int n, final byte[][] array) throws psc_m {
        final psc_p[] array2 = new psc_p[9];
        for (int i = 0; i < 9; ++i) {
            array2[i] = new psc_p(0, true, 0, null, 0, 0, true);
        }
        final psc_k psc_k = new psc_k(77824, false, 0, null, 0, 0);
        if (array != null) {
            a(array2, psc_k, n, array);
        }
        return new psc_i[] { new psc_h(0, true, 0), array2[0], array2[1], array2[2], array2[3], array2[4], array2[5], array2[6], array2[7], array2[8], psc_k, new psc_j() };
    }
    
    private static void a(final psc_p[] array, final psc_k psc_k, final int n, final byte[][] array2) throws psc_m {
        array[0].a(new byte[1], 0, 1, true, true);
        int i;
        int n2;
        for (i = 0, n2 = 1; i < 5; ++i, ++n2) {
            array[n2].a(array2[i], 0, array2[i].length, true, true);
        }
        int n3 = i + (n - 2);
        for (int j = 0; j < 2; ++j, ++n3, ++n2) {
            array[n2].a(array2[n3], 0, array2[n3].length, true, true);
        }
        final int n4 = n3 + (n - 2);
        array[n2].a(array2[n4], 0, array2[n4].length, true, true);
        if (n == 2) {
            return;
        }
        final psc_w psc_w = new psc_w(0, true, 0, 12288, a(null, 0, 0));
        Label_0247: {
            try {
                for (int k = 0; k < n - 2; ++k) {
                    psc_w.b(a(array2, k + 5, n));
                }
                final byte[] a = psc_l.a(new psc_i[] { psc_w });
                psc_k.a(a, 0, a.length, true, true);
                break Label_0247;
            }
            finally {
                for (int l = psc_w.j(), n5 = 0; n5 < l; ++n5) {
                    psc_w.a(n5).i();
                }
                psc_w.i();
                while (true) {}
                return;
                while (true) {
                    int n6 = 0;
                    Block_8: {
                        break Block_8;
                        Label_0286: {
                            psc_w.i();
                        }
                        return;
                        final int m = psc_w.j();
                        n6 = 0;
                        continue;
                    }
                    psc_w.a(n6).i();
                    ++n6;
                    continue;
                }
            }
            // iftrue(Label_0286:, n6 >= m)
        }
    }
    
    private static psc_p[] a(final byte[] array, final int n) throws psc_bf {
        psc_w psc_w;
        int j;
        try {
            psc_w = new psc_w(0, true, 0, 12288, a(null, 0, 0));
            psc_l.a(array, n, new psc_i[] { psc_w });
            j = psc_w.j();
        }
        catch (psc_m psc_m) {
            throw new psc_bf("MultiPrime BER encoding contained unexpected data.");
        }
        final psc_p[] array2 = new psc_p[3 * j];
        int n2 = 0;
        for (int i = 0, n3 = 0; i < j; ++i, ++n3) {
            try {
                final psc_i[] e = ((psc_li)psc_w.a(i)).e();
                if (e.length != 5) {
                    n2 = 1;
                    break;
                }
                array2[n3] = (psc_p)e[1];
                array2[n3 + j] = (psc_p)e[2];
                array2[n3 + 2 * j] = (psc_p)e[3];
            }
            catch (ClassCastException ex) {
                n2 = 1;
                break;
            }
            catch (psc_m psc_m2) {
                n2 = 1;
                break;
            }
        }
        if (n2 == 1) {
            throw new psc_bf("MultiPrime BER encoding contained unexpected data.");
        }
        return array2;
    }
    
    private static psc_li a(final byte[][] array, int n, final int n2) throws psc_m {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final psc_p[] array2 = new psc_p[3];
        for (int i = 0; i < 3; ++i) {
            array2[i] = new psc_p(0, true, 0, null, 0, 0, true);
            if (array != null) {
                array2[i].a(array[n], 0, array[n].length, true, true);
                n += n2;
                if (i == 1) {
                    --n;
                }
            }
        }
        return new psc_li(new psc_i[] { psc_h, array2[0], array2[1], array2[2], psc_j });
    }
}
