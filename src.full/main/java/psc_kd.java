import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_kd extends psc_i1 implements psc_ep, psc_i2
{
    public psc_kd() {
    }
    
    public psc_kd(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public String d() {
        return "X931RSA";
    }
    
    public String m() {
        return "X931Pad";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public boolean a(final psc_az psc_az) {
        return psc_az instanceof psc_ew;
    }
    
    public boolean a(final psc_et psc_et) {
        return psc_et instanceof psc_i5;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        final int a = super.a(array, n, array2, n2);
        if ((array2[super.e + n2 - 1] & 0xF) == 0xC) {
            return a;
        }
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        Object j = null;
        Label_0356: {
            try {
                psc_e0 = super.a.newInstance();
                psc_e2 = super.a.newInstance();
                psc_e0.a(array2, n2, super.e);
                super.i[0].b(psc_e0, psc_e2);
                j = psc_e2.j(super.e);
                if (j == null || j.length == 0) {
                    final int n3 = 0;
                    break Label_0356;
                }
                System.arraycopy(j, 0, array2, n2, j.length);
                break Label_0356;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_0356;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_0356;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_0356;
            }
            finally {
                if (j != null) {
                    psc_au.c(j);
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                Label_0431: {
                    if (psc_e2 != null) {
                        psc_e2.r();
                        break Label_0431;
                    }
                    break Label_0431;
                }
                while (true) {}
                // iftrue(Label_0341:, psc_e0 == null)
                // iftrue(Label_0212:, j == null)
                // iftrue(Label_0251:, j == null)
                // iftrue(Label_0314:, psc_e2 == null)
                // iftrue(Label_0263:, psc_e0 == null)
                // iftrue(Label_0368:, j == null)
                // iftrue(Label_0275:, psc_e2 == null)
                // iftrue(Label_0224:, psc_e0 == null)
                // iftrue(Label_0329:, j == null)
                // iftrue(Label_0236:, psc_e2 == null)
                // iftrue(Label_0380:, psc_e0 == null)
                // iftrue(Label_0302:, psc_e0 == null)
                // iftrue(Label_0290:, j == null)
                // iftrue(Label_0353:, psc_e2 == null)
            Block_15_Outer:
                while (true) {
                    Label_0380: {
                        final boolean b;
                        Block_20: {
                            final int n3;
                            Block_11: {
                                Label_0341: {
                                    Block_17: {
                                        while (true) {
                                            Label_0329_Outer:Block_14_Outer:Block_22_Outer:
                                            while (true) {
                                                psc_e0.r();
                                                Label_0290:Block_10_Outer:Block_18_Outer:
                                                while (true) {
                                                    Label_0368: {
                                                        Label_0224: {
                                                            while (true) {
                                                                while (true) {
                                                                    Label_0212: {
                                                                        while (true) {
                                                                            Label_0263: {
                                                                                while (true) {
                                                                                    Block_12: {
                                                                                    Block_13_Outer:
                                                                                        while (true) {
                                                                                            while (true) {
                                                                                                while (true) {
                                                                                                    Block_19: {
                                                                                                        Label_0302: {
                                                                                                            break Label_0302;
                                                                                                            break Block_19;
                                                                                                            psc_e0.r();
                                                                                                            break Label_0263;
                                                                                                            while (true) {
                                                                                                                psc_au.c(j);
                                                                                                                break Label_0212;
                                                                                                                psc_e2.r();
                                                                                                                return a;
                                                                                                                psc_e0.r();
                                                                                                                break Label_0380;
                                                                                                                continue Block_14_Outer;
                                                                                                            }
                                                                                                            break Block_12;
                                                                                                            Label_0275: {
                                                                                                                return a;
                                                                                                            }
                                                                                                        }
                                                                                                        break Block_17;
                                                                                                        psc_au.c(j);
                                                                                                        break Label_0368;
                                                                                                        psc_e2.r();
                                                                                                        return b ? 1 : 0;
                                                                                                        Label_0236: {
                                                                                                            return n3;
                                                                                                        }
                                                                                                        psc_au.c(j);
                                                                                                        break Label_0290;
                                                                                                    }
                                                                                                    psc_e0.r();
                                                                                                    break Label_0341;
                                                                                                    continue Block_14_Outer;
                                                                                                }
                                                                                                psc_e0.r();
                                                                                                break Label_0224;
                                                                                                continue Block_15_Outer;
                                                                                            }
                                                                                            psc_au.c(j);
                                                                                            continue Block_13_Outer;
                                                                                        }
                                                                                        Label_0392: {
                                                                                            return b ? 1 : 0;
                                                                                        }
                                                                                    }
                                                                                    psc_au.c(j);
                                                                                    continue Block_10_Outer;
                                                                                }
                                                                            }
                                                                            continue Block_22_Outer;
                                                                        }
                                                                        Label_0314: {
                                                                            return b ? 1 : 0;
                                                                        }
                                                                    }
                                                                    continue Block_18_Outer;
                                                                }
                                                                continue;
                                                            }
                                                        }
                                                        break Block_11;
                                                    }
                                                    continue Block_15_Outer;
                                                }
                                                continue Label_0329_Outer;
                                            }
                                            Label_0353: {
                                                return b ? 1 : 0;
                                            }
                                            continue;
                                        }
                                    }
                                    psc_e2.r();
                                    return b ? 1 : 0;
                                }
                                break Block_20;
                            }
                            psc_e2.r();
                            return n3;
                        }
                        psc_e2.r();
                        return b ? 1 : 0;
                    }
                    continue;
                }
            }
            // iftrue(Label_0392:, psc_e2 == null)
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final psc_az psc_az, final psc_et psc_et, final byte[] array2, final int n3) {
        final int a = super.a(array, n, n2, psc_az, psc_et, array2, n3);
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        Object j = null;
        Label_0402: {
            try {
                psc_e0 = super.a.newInstance();
                psc_e2 = super.a.newInstance();
                psc_e0.a(array2, n3, super.e);
                super.i[0].b(psc_e0, psc_e2);
                if (psc_e0.b(psc_e2) <= 0) {
                    final int n4 = a;
                    break Label_0402;
                }
                j = psc_e2.j(super.e);
                if (j == null || j.length == 0) {
                    final int n5 = 0;
                    break Label_0402;
                }
                System.arraycopy(j, 0, array2, n3, j.length);
                break Label_0402;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_0402;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_0402;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_0402;
            }
            finally {
                if (j != null) {
                    psc_au.c(j);
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                Label_0477: {
                    if (psc_e2 != null) {
                        psc_e2.r();
                        break Label_0477;
                    }
                    break Label_0477;
                }
                while (true) {}
                Label_0243: {
                    return;
                }
                // iftrue(Label_0219:, j == null)
                // iftrue(Label_0309:, psc_e0 == null)
                // iftrue(Label_0414:, j == null)
                // iftrue(Label_0297:, j == null)
                // iftrue(Label_0348:, psc_e0 == null)
                // iftrue(Label_0321:, psc_e2 == null)
                // iftrue(Label_0243:, psc_e2 == null)
                // iftrue(Label_0231:, psc_e0 == null)
                // iftrue(Label_0375:, j == null)
                // iftrue(Label_0360:, psc_e2 == null)
                // iftrue(Label_0270:, psc_e0 == null)
                // iftrue(Label_0399:, psc_e2 == null)
                // iftrue(Label_0387:, psc_e0 == null)
                // iftrue(Label_0282:, psc_e2 == null)
                // iftrue(Label_0336:, j == null)
                // iftrue(Label_0426:, psc_e0 == null)
                // iftrue(Label_0258:, j == null)
                // iftrue(Label_0438:, psc_e2 == null)
                final boolean b;
                Block_20: {
                    while (true) {
                        Block_13: {
                            Block_17: {
                                Block_23: {
                                    final int n4;
                                    final int n5;
                                    Block_10_Outer:Label_0336_Outer:Block_12_Outer:Block_26_Outer:
                                    while (true) {
                                        Block_15: {
                                            while (true) {
                                            Label_0426:
                                                while (true) {
                                                    Label_0219_Outer:Block_14_Outer:
                                                    while (true) {
                                                        Block_18: {
                                                            Label_0414: {
                                                                while (true) {
                                                                    while (true) {
                                                                        Block_9: {
                                                                            break Block_9;
                                                                            while (true) {
                                                                                Label_0375: {
                                                                                    Label_0387: {
                                                                                        Block_11: {
                                                                                            Label_0258: {
                                                                                                Block_24: {
                                                                                                    Block_16: {
                                                                                                        break Block_16;
                                                                                                        break Block_24;
                                                                                                    }
                                                                                                    psc_e0.r();
                                                                                                    Label_0348: {
                                                                                                        while (true) {
                                                                                                            while (true) {
                                                                                                                Label_0231: {
                                                                                                                    Label_0309: {
                                                                                                                        break Label_0309;
                                                                                                                        while (true) {
                                                                                                                            psc_e0.r();
                                                                                                                            break Label_0348;
                                                                                                                            psc_e0.r();
                                                                                                                            break Label_0231;
                                                                                                                            break Block_15;
                                                                                                                            continue Block_10_Outer;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    break Block_17;
                                                                                                                    psc_au.c(j);
                                                                                                                    break Label_0375;
                                                                                                                }
                                                                                                                break Block_11;
                                                                                                                continue Label_0336_Outer;
                                                                                                            }
                                                                                                            psc_au.c(j);
                                                                                                            break Label_0258;
                                                                                                            continue Label_0219_Outer;
                                                                                                        }
                                                                                                    }
                                                                                                    break Block_20;
                                                                                                    Label_0282:
                                                                                                    return n5;
                                                                                                    psc_e0.r();
                                                                                                    break Label_0387;
                                                                                                }
                                                                                                psc_au.c(j);
                                                                                                break Label_0414;
                                                                                            }
                                                                                            break Block_13;
                                                                                            psc_e2.r();
                                                                                            return n5;
                                                                                            psc_e2.r();
                                                                                            return b ? 1 : 0;
                                                                                            Label_0321:
                                                                                            return super.e;
                                                                                        }
                                                                                        psc_e2.r();
                                                                                        return n4;
                                                                                    }
                                                                                    break Block_23;
                                                                                }
                                                                                continue Block_14_Outer;
                                                                            }
                                                                            Label_0360:
                                                                            return b ? 1 : 0;
                                                                            Label_0438:
                                                                            return b ? 1 : 0;
                                                                        }
                                                                        psc_au.c(j);
                                                                        continue Block_12_Outer;
                                                                    }
                                                                    continue Block_26_Outer;
                                                                }
                                                                break Block_18;
                                                            }
                                                            psc_e0.r();
                                                            break Label_0426;
                                                        }
                                                        psc_au.c(j);
                                                        continue Label_0219_Outer;
                                                    }
                                                    continue Block_26_Outer;
                                                }
                                                continue;
                                            }
                                        }
                                        psc_au.c(j);
                                        continue;
                                    }
                                }
                                psc_e2.r();
                                return b ? 1 : 0;
                            }
                            psc_e2.r();
                            return super.e;
                            Label_0399:
                            return b ? 1 : 0;
                        }
                        psc_e0.r();
                        continue;
                    }
                }
                psc_e2.r();
                return b ? 1 : 0;
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av w = this.w();
        objectOutputStream.defaultWriteObject();
        this.a(w);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.x();
    }
    
    private psc_av w() {
        this.l();
        if (super.b == null) {
            return null;
        }
        if (!(super.b instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)super.b;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        super.d = psc_av.getAlgorithm();
        super.c = psc_av.d();
        final psc_av psc_av2 = (psc_av)super.b;
        super.b = null;
        return psc_av2;
    }
    
    private void a(final psc_av b) {
        this.k();
        if (super.c == null) {
            return;
        }
        for (int i = 0; i < super.c.length; ++i) {
            super.c[i] = 0;
        }
        super.c = null;
        super.d = null;
        super.b = b;
    }
    
    private void x() {
        this.k();
        if (super.c == null) {
            return;
        }
        super.b = psc_av.a(super.d, super.c);
        for (int i = 0; i < super.c.length; ++i) {
            super.c[i] = 0;
        }
        super.c = null;
        super.d = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kd psc_kd = new psc_kd();
        this.a(psc_kd);
        return psc_kd;
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
