import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ke extends psc_an implements psc_ep, psc_i2, Cloneable, Serializable
{
    protected Class a;
    protected psc_e2 b;
    protected int c;
    protected int d;
    protected static final int e = 20;
    protected int f;
    protected static final int g = 40;
    protected static final int h = 48;
    protected psc_e0 i;
    protected psc_e0 j;
    protected psc_e0 k;
    protected psc_e0 l;
    protected psc_e0 m;
    byte[] n;
    private transient psc_dd o;
    
    public psc_ke() {
        this.c = -1;
        this.d = 0;
        this.f = 0;
    }
    
    public psc_ke(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of instantiation parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String d() {
        return "DSA";
    }
    
    public String m() {
        return "NoPad";
    }
    
    public int o() {
        return this.c;
    }
    
    public int p() {
        return this.d;
    }
    
    public boolean n() {
        return false;
    }
    
    public int q() {
        return this.f;
    }
    
    public boolean a(final psc_az psc_az) {
        final String e = psc_az.e();
        return e.compareTo("SHA1") == 0 || e.compareTo("NoDigest") == 0;
    }
    
    public boolean a(final psc_et psc_et) {
        return psc_et.d().compareTo("NoPad") == 0;
    }
    
    protected void e() {
        if (this.a == null) {
            this.a = psc_k6.a();
        }
    }
    
    public void a(final Class a) throws psc_k9 {
        try {
            final psc_e0 psc_e0 = a.newInstance();
        }
        catch (InstantiationException ex) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        this.a = a;
    }
    
    public void a(final byte[] array, final int n) throws psc_ao, psc_be {
        psc_lr.a(this, array, n);
    }
    
    public byte[] a(final psc_az psc_az, final psc_et psc_et, final String s, final boolean b) throws psc_ao {
        return psc_lr.a(psc_az, psc_et, s, b, this.i, this.j, this.k);
    }
    
    void a(final int n, final byte[] array, final int n2, final int n3, final byte[] array2, final int n4, final int n5, final byte[] array3, final int n6, final int n7) throws psc_be {
        this.h();
        this.e();
        try {
            this.i = this.a.newInstance();
            this.j = this.a.newInstance();
            this.k = this.a.newInstance();
            this.i.a(array, n2, n3);
            this.j.a(array2, n4, n5);
            this.k.a(array3, n6, n7);
            final int o = this.i.o();
            if (n != -1 && o > n) {
                throw new psc_be("DSA prime size mismatch.");
            }
            if (o < 512 || o > 4096) {
                throw new psc_be("Invalid DSA prime size.");
            }
            final int o2 = this.j.o();
            if (o2 < 160 || o2 > 160) {
                throw new psc_be("Invalid DSA subprime size.");
            }
            if (this.k.b(this.i) >= 0) {
                throw new psc_be("Invalid DSA base size.");
            }
        }
        catch (psc_e1 psc_e1) {
            throw new psc_be("Bad arithmetic class.");
        }
        catch (InstantiationException ex) {
            throw new psc_be("Bad arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_be("Bad arithmetic class.");
        }
    }
    
    public boolean r() {
        return this.b == null;
    }
    
    public String a(final psc_el psc_el) {
        if (psc_el == null) {
            return null;
        }
        this.e();
        try {
            final byte[][] b = psc_el.b("DSAParameters");
            this.i = this.a.newInstance();
            this.j = this.a.newInstance();
            this.k = this.a.newInstance();
            this.i.a(b[0], 0, b[0].length);
            this.j.a(b[1], 0, b[1].length);
            this.k.a(b[2], 0, b[2].length);
        }
        catch (psc_ap psc_ap) {
            return "Invalid DSA system parameters.";
        }
        catch (InstantiationException ex) {
            return "Bad arithmetic class.";
        }
        catch (IllegalAccessException ex2) {
            return "Bad arithmetic class.";
        }
        return null;
    }
    
    private void a(final SecureRandom secureRandom) {
        final byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        (this.b = new psc_e2(bytes)).a(true);
        this.n = new byte[40];
        this.o = psc_au.b(this.n);
        if (psc_aq.c()) {
            this.l();
            this.b.b(this.n, 0, this.n.length);
            this.k();
        }
    }
    
    public void a(final int n) {
    }
    
    public int j() {
        return 0;
    }
    
    public String a(final psc_dt psc_dt, final psc_az psc_az, final psc_et psc_et, final SecureRandom secureRandom, final psc_nf[] array) {
        this.f();
        if (secureRandom != null) {
            this.a(secureRandom);
        }
        String[] o;
        int n;
        for (o = psc_dt.o(), n = 0; n < o.length && o[n].compareTo("DSAPrivateKey") != 0; ++n) {}
        if (n < o.length) {
            return this.a(psc_dt, psc_az, psc_et);
        }
        if (this.i == null || this.j == null || this.k == null) {
            return "Cannot initialize for DSA without the system parameters.";
        }
        byte[][] b = null;
        Label_0291: {
            try {
                b = psc_dt.b("DSAPrivateValue");
                (this.m = this.a.newInstance()).a(b[0], 0, b[0].length);
                this.m.p();
                break Label_0291;
            }
            catch (psc_ap psc_ap) {
                final String s = "Could not initialize for DSA with the given key.";
                break Label_0291;
            }
            catch (InstantiationException ex) {
                final String s = "Bad arithmetic class.";
                break Label_0291;
            }
            catch (IllegalAccessException ex2) {
                final String s = "Bad arithmetic class.";
                break Label_0291;
            }
            finally {
                Label_0338: {
                    if (b != null && b.length >= 1) {
                        this.d(b[0]);
                        break Label_0338;
                    }
                    break Label_0338;
                }
                while (true) {}
                // iftrue(Label_0238:, b == null || b.length < 1)
                // iftrue(Label_0263:, b == null || b.length < 1)
                // iftrue(Label_0313:, b == null || b.length < 1)
                final String s;
                Block_20: {
                    Block_16: {
                        Block_14: {
                            break Block_14;
                            break Block_16;
                            Label_0263: {
                                return s;
                            }
                            Label_0288:
                            return s;
                        }
                        this.d(b[0]);
                        return this.a(psc_az, psc_et);
                        break Block_20;
                        Label_0238: {
                            return this.a(psc_az, psc_et);
                        }
                    }
                    this.d(b[0]);
                    return s;
                    Label_0313: {
                        return s;
                    }
                }
                this.d(b[0]);
                return s;
                // iftrue(Label_0288:, b == null || b.length < 1)
                this.d(b[0]);
                return s;
            }
        }
    }
    
    private String a(final psc_dt psc_dt, final psc_az psc_az, final psc_et psc_et) {
        this.h();
        this.e();
        byte[][] b = null;
        Label_0359: {
            try {
                b = psc_dt.b("DSAPrivateKey");
                this.i = this.a.newInstance();
                this.j = this.a.newInstance();
                this.k = this.a.newInstance();
                this.m = this.a.newInstance();
                this.i.a(b[0], 0, b[0].length);
                this.j.a(b[1], 0, b[1].length);
                this.k.a(b[2], 0, b[2].length);
                this.m.a(b[3], 0, b[3].length);
                this.m.p();
                break Label_0359;
            }
            catch (psc_ap psc_ap) {
                final String s = "Could not initialize for DSA with the given key.";
                break Label_0359;
            }
            catch (InstantiationException ex) {
                final String s = "Bad arithmetic class.";
                break Label_0359;
            }
            catch (IllegalAccessException ex2) {
                final String s = "Bad arithmetic class.";
                break Label_0359;
            }
            finally {
                Label_0444: {
                    if (b != null && b.length >= 4) {
                        for (int i = 0; i < b[3].length; ++i) {
                            b[3][i] = 0;
                        }
                        break Label_0444;
                    }
                    break Label_0444;
                }
                while (true) {}
                // iftrue(Label_0268:, n >= b[3].length)
                // iftrue(Label_0268:, b == null || b.length < 4)
                // iftrue(Label_0356:, b == null || b.length < 4)
                // iftrue(Label_0400:, n3 >= b[3].length)
                // iftrue(Label_0400:, b == null || b.length < 4)
                // iftrue(Label_0312:, n2 >= b[3].length)
                // iftrue(Label_0356:, n4 >= b[3].length)
                while (true) {
                    final String s;
                    int n = 0;
                    int n2;
                    int n3 = 0;
                    int n4 = 0;
                    Block_12_Outer:Label_0376_Outer:
                    while (true) {
                    Label_0332:
                        while (true) {
                        Label_0376:
                            while (true) {
                                Block_18: {
                                    Block_14: {
                                        while (true) {
                                            Label_0288: {
                                                while (true) {
                                                    Block_9: {
                                                        break Block_9;
                                                        n2 = 0;
                                                        break Label_0288;
                                                        b[3][n2] = 0;
                                                        ++n2;
                                                        break Label_0288;
                                                        n3 = 0;
                                                        break Label_0376;
                                                        break Label_0332;
                                                        Label_0268: {
                                                            return this.a(psc_az, psc_et);
                                                        }
                                                        Label_0356:
                                                        return s;
                                                    }
                                                    b[3][n] = 0;
                                                    ++n;
                                                    continue Block_12_Outer;
                                                    break Block_14;
                                                    break Block_18;
                                                    continue Label_0376_Outer;
                                                }
                                            }
                                            continue Label_0376_Outer;
                                        }
                                        break Label_0376;
                                        Label_0312: {
                                            return s;
                                        }
                                    }
                                    n4 = 0;
                                    continue Label_0332;
                                }
                                b[3][n3] = 0;
                                ++n3;
                                continue Label_0376;
                            }
                            b[3][n4] = 0;
                            ++n4;
                            continue Label_0332;
                        }
                        n = 0;
                        continue Block_12_Outer;
                    }
                    Label_0400: {
                        return s;
                    }
                    continue;
                }
            }
            // iftrue(Label_0312:, b == null || b.length < 4)
        }
    }
    
    public void s() throws psc_en {
    }
    
    public String a(final psc_al psc_al, final psc_az psc_az, final psc_et psc_et, final SecureRandom secureRandom, final psc_nf[] array) {
        this.f();
        if (secureRandom != null) {
            this.a(secureRandom);
        }
        String[] o;
        int n;
        for (o = psc_al.o(), n = 0; n < o.length && o[n].compareTo("DSAPublicKey") != 0; ++n) {}
        if (n < o.length) {
            return this.a(psc_al, psc_az, psc_et);
        }
        if (this.i == null || this.j == null || this.k == null) {
            return "Cannot initialize for DSA without the system parameters.";
        }
        try {
            final byte[][] b = psc_al.b("DSAPublicValue");
            (this.l = this.a.newInstance()).a(b[0], 0, b[0].length);
        }
        catch (psc_ap psc_ap) {
            return "Could not initialize for DSA with the given key.";
        }
        catch (InstantiationException ex) {
            return "Bad arithmetic class.";
        }
        catch (IllegalAccessException ex2) {
            return "Bad arithmetic class.";
        }
        return this.a(psc_az, psc_et);
    }
    
    private String a(final psc_al psc_al, final psc_az psc_az, final psc_et psc_et) {
        this.h();
        this.e();
        final byte[][] array = null;
        try {
            final byte[][] b = psc_al.b("DSAPublicKey");
            this.i = this.a.newInstance();
            this.j = this.a.newInstance();
            this.k = this.a.newInstance();
            this.l = this.a.newInstance();
            this.i.a(b[0], 0, b[0].length);
            this.j.a(b[1], 0, b[1].length);
            this.k.a(b[2], 0, b[2].length);
            this.l.a(b[3], 0, b[3].length);
        }
        catch (psc_ap psc_ap) {
            return "Could not initialize for DSA with the given key.";
        }
        catch (InstantiationException ex) {
            return "Bad arithmetic class.";
        }
        catch (IllegalAccessException ex2) {
            return "Bad arithmetic class.";
        }
        return this.a(psc_az, psc_et);
    }
    
    public void t() throws psc_en {
    }
    
    protected String a(final psc_az psc_az, final psc_et psc_et) {
        final String e = psc_az.e();
        this.c = 20;
        if (e.compareTo("NoDigest") != 0) {
            this.d = -1;
            this.f = 48;
            return null;
        }
        this.d = 20;
        this.f = 40;
        return null;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        throw new psc_en("Improper call to signUpdate.");
    }
    
    public int a(final byte[] array, final int n, final int n2, final psc_az psc_az, final psc_et psc_et, final byte[] array2, int n3) {
        final byte[] array3 = new byte[40];
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        Label_1074: {
            try {
                this.e();
                psc_e0 = this.a.newInstance();
                psc_e2 = this.a.newInstance();
                psc_e3 = this.a.newInstance();
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e6 = this.a.newInstance();
                psc_e7 = this.a.newInstance();
                while (true) {
                    this.b.b(array3, 0, array3.length);
                    this.b.a(this.n, 0, array3, 0, array3.length);
                    System.arraycopy(array3, 0, this.n, 0, array3.length);
                    psc_e0.a(array3, 0, array3.length);
                    psc_e0.d(this.j, psc_e2);
                    if (!psc_e2.e(this.j, psc_e3)) {
                        continue;
                    }
                    this.k.c(psc_e2, this.i, psc_e0);
                    psc_e0.d(this.j, psc_e4);
                    this.m.a(psc_e4, this.j, psc_e5);
                    psc_e6.a(array, n, n2);
                    psc_e6.a(psc_e5, psc_e0);
                    psc_e3.a(psc_e0, this.j, psc_e7);
                    if (psc_e7.o() > 1) {
                        break;
                    }
                }
                final byte[] j = psc_e4.j(20);
                final byte[] i = psc_e7.j(20);
                if (psc_az.e().compareTo("NoDigest") == 0) {
                    for (int k = 0; k < 20; ++k, ++n3) {
                        array2[n3] = j[k];
                        array2[n3 + 20] = i[k];
                    }
                    final int n4 = 40;
                    break Label_1074;
                }
                final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_p(0, true, 0, j, 0, j.length, true), new psc_p(0, true, 0, i, 0, i.length, true), new psc_j() });
                psc_n.a();
                final int a = psc_n.a(array2, n3);
                break Label_1074;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_1074;
            }
            catch (psc_m psc_m) {
                final boolean b = false;
                break Label_1074;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_1074;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_1074;
            }
            finally {
                if (psc_e2 != null) {
                    psc_e2.r();
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
                if (psc_e6 != null) {
                    psc_e6.r();
                }
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                psc_au.c(array3);
                psc_au.c(this.n);
                while (true) {}
                // iftrue(Label_0619:, psc_e5 == null)
                // iftrue(Label_0655:, psc_e0 == null)
                // iftrue(Label_1059:, psc_e0 == null)
                // iftrue(Label_1100:, psc_e3 == null)
                // iftrue(Label_0886:, psc_e2 == null)
                // iftrue(Label_0845:, psc_e7 == null)
                // iftrue(Label_1011:, psc_e4 == null)
                // iftrue(Label_0583:, psc_e2 == null)
                // iftrue(Label_0809:, psc_e4 == null)
                // iftrue(Label_0857:, psc_e0 == null)
                // iftrue(Label_1047:, psc_e7 == null)
                // iftrue(Label_0999:, psc_e3 == null)
                // iftrue(Label_0720:, psc_e5 == null)
                // iftrue(Label_1124:, psc_e5 == null)
                // iftrue(Label_0595:, psc_e3 == null)
                // iftrue(Label_0821:, psc_e5 == null)
                // iftrue(Label_1035:, psc_e6 == null)
                // iftrue(Label_1023:, psc_e5 == null)
                // iftrue(Label_0631:, psc_e6 == null)
                // iftrue(Label_1136:, psc_e6 == null)
                // iftrue(Label_0708:, psc_e4 == null)
                // iftrue(Label_0684:, psc_e2 == null)
                // iftrue(Label_0696:, psc_e3 == null)
                // iftrue(Label_0732:, psc_e6 == null)
                // iftrue(Label_1160:, psc_e0 == null)
                // iftrue(Label_0946:, psc_e7 == null)
                // iftrue(Label_1148:, psc_e7 == null)
                // iftrue(Label_1088:, psc_e2 == null)
                // iftrue(Label_0934:, psc_e6 == null)
                // iftrue(Label_0922:, psc_e5 == null)
                // iftrue(Label_0744:, psc_e7 == null)
                // iftrue(Label_0958:, psc_e0 == null)
                // iftrue(Label_1112:, psc_e4 == null)
                // iftrue(Label_0643:, psc_e7 == null)
                // iftrue(Label_0910:, psc_e4 == null)
                // iftrue(Label_0898:, psc_e3 == null)
                // iftrue(Label_0797:, psc_e3 == null)
                // iftrue(Label_0785:, psc_e2 == null)
                // iftrue(Label_0756:, psc_e0 == null)
                // iftrue(Label_0833:, psc_e6 == null)
                // iftrue(Label_0987:, psc_e2 == null)
                // iftrue(Label_0607:, psc_e4 == null)
                final boolean b;
                Label_1160: {
                    while (true) {
                        final int n4;
                        final int a;
                        Label_0595:Label_0785_Outer:
                        while (true) {
                            while (true) {
                                Block_30_Outer:Label_0958_Outer:Label_0655_Outer:
                                while (true) {
                                Label_1112_Outer:
                                    while (true) {
                                    Label_0946_Outer:
                                        while (true) {
                                        Block_49:
                                            while (true) {
                                            Block_38:
                                                while (true) {
                                                    Block_23_Outer:Label_1047_Outer:Label_0845_Outer:Label_0857_Outer:
                                                    while (true) {
                                                    Label_0821:
                                                        while (true) {
                                                            Block_32:Block_25_Outer:
                                                            while (true) {
                                                            Label_0910_Outer:
                                                                while (true) {
                                                                    Label_0744: {
                                                                        Label_1059: {
                                                                            while (true) {
                                                                                Block_35: {
                                                                                    Label_1088_Outer:Label_0934_Outer:
                                                                                    while (true) {
                                                                                        Block_45: {
                                                                                        Block_27_Outer:
                                                                                            while (true) {
                                                                                                Block_37: {
                                                                                                    Label_0756: {
                                                                                                        while (true) {
                                                                                                            while (true) {
                                                                                                                Label_0886: {
                                                                                                                Label_0797_Outer:
                                                                                                                    while (true) {
                                                                                                                    Label_0809_Outer:
                                                                                                                        while (true) {
                                                                                                                        Label_0631:
                                                                                                                            while (true) {
                                                                                                                                Block_28: {
                                                                                                                                    Label_1100: {
                                                                                                                                    Label_0696_Outer:
                                                                                                                                        while (true) {
                                                                                                                                            Block_12: {
                                                                                                                                                while (true) {
                                                                                                                                                    Block_19: {
                                                                                                                                                        Block_29: {
                                                                                                                                                            Label_0732: {
                                                                                                                                                                while (true) {
                                                                                                                                                                Label_0922:
                                                                                                                                                                    while (true) {
                                                                                                                                                                    Block_52_Outer:
                                                                                                                                                                        while (true) {
                                                                                                                                                                            while (true) {
                                                                                                                                                                                Label_1136: {
                                                                                                                                                                                    Block_46: {
                                                                                                                                                                                        while (true) {
                                                                                                                                                                                        Label_1148:
                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                Block_44: {
                                                                                                                                                                                                Block_33:
                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                        Label_0720: {
                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                Block_42: {
                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                        Block_13_Outer:Block_51_Outer:
                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                            psc_e4.r();
                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                Label_0619: {
                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                        Label_1023: {
                                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                    Label_0708: {
                                                                                                                                                                                                                                                        break Label_0708;
                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                                                Label_0987: {
                                                                                                                                                                                                                                                                    Label_0999: {
                                                                                                                                                                                                                                                                        Label_0833: {
                                                                                                                                                                                                                                                                            Block_48: {
                                                                                                                                                                                                                                                                                Block_15: {
                                                                                                                                                                                                                                                                                    break Block_15;
                                                                                                                                                                                                                                                                                    break Label_0946_Outer;
                                                                                                                                                                                                                                                                                    psc_e6.r();
                                                                                                                                                                                                                                                                                    break Label_0833;
                                                                                                                                                                                                                                                                                    psc_e6.r();
                                                                                                                                                                                                                                                                                    break Label_0732;
                                                                                                                                                                                                                                                                                    break Block_46;
                                                                                                                                                                                                                                                                                    break Block_48;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                psc_e5.r();
                                                                                                                                                                                                                                                                                break Label_0619;
                                                                                                                                                                                                                                                                                break Block_33;
                                                                                                                                                                                                                                                                                psc_e3.r();
                                                                                                                                                                                                                                                                                break Label_0999;
                                                                                                                                                                                                                                                                                psc_au.c(array3);
                                                                                                                                                                                                                                                                                psc_au.c(this.n);
                                                                                                                                                                                                                                                                                return b ? 1 : 0;
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            psc_e3.r();
                                                                                                                                                                                                                                                                            break Label_1100;
                                                                                                                                                                                                                                                                            psc_e5.r();
                                                                                                                                                                                                                                                                            break Label_0720;
                                                                                                                                                                                                                                                                            psc_e2.r();
                                                                                                                                                                                                                                                                            break Label_0987;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        break Label_0910_Outer;
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    break Block_42;
                                                                                                                                                                                                                                                                    break Block_12;
                                                                                                                                                                                                                                                                    psc_e3.r();
                                                                                                                                                                                                                                                                    break Label_0595;
                                                                                                                                                                                                                                                                    psc_e6.r();
                                                                                                                                                                                                                                                                    break Label_1136;
                                                                                                                                                                                                                                                                    break Block_28;
                                                                                                                                                                                                                                                                    psc_e5.r();
                                                                                                                                                                                                                                                                    break Label_1023;
                                                                                                                                                                                                                                                                    break Block_32;
                                                                                                                                                                                                                                                                    break Block_45;
                                                                                                                                                                                                                                                                    psc_au.c(array3);
                                                                                                                                                                                                                                                                    psc_au.c(this.n);
                                                                                                                                                                                                                                                                    return n4;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                continue Label_0958_Outer;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            psc_e7.r();
                                                                                                                                                                                                                                                            continue Block_30_Outer;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    continue Block_13_Outer;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                break Block_13_Outer;
                                                                                                                                                                                                                                                continue Block_51_Outer;
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            break Block_29;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        break Block_44;
                                                                                                                                                                                                                                        psc_e0.r();
                                                                                                                                                                                                                                        break Label_0756;
                                                                                                                                                                                                                                        psc_e0.r();
                                                                                                                                                                                                                                        break Label_1160;
                                                                                                                                                                                                                                        continue Label_0845_Outer;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                psc_e6.r();
                                                                                                                                                                                                                                break Label_0631;
                                                                                                                                                                                                                                continue Label_0797_Outer;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            psc_e7.r();
                                                                                                                                                                                                                            break Label_1148;
                                                                                                                                                                                                                            continue Block_30_Outer;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        psc_e5.r();
                                                                                                                                                                                                                        continue Block_52_Outer;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    break Block_19;
                                                                                                                                                                                                                    psc_au.c(array3);
                                                                                                                                                                                                                    psc_au.c(this.n);
                                                                                                                                                                                                                    return b ? 1 : 0;
                                                                                                                                                                                                                    break Block_52_Outer;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                psc_e4.r();
                                                                                                                                                                                                                continue Block_52_Outer;
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                        continue Label_1047_Outer;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    psc_e2.r();
                                                                                                                                                                                                    break Label_0886;
                                                                                                                                                                                                }
                                                                                                                                                                                                psc_e6.r();
                                                                                                                                                                                                continue Label_0655_Outer;
                                                                                                                                                                                            }
                                                                                                                                                                                            continue Block_52_Outer;
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                    psc_e0.r();
                                                                                                                                                                                    break Label_1059;
                                                                                                                                                                                    break Block_38;
                                                                                                                                                                                    psc_e5.r();
                                                                                                                                                                                    break Label_0922;
                                                                                                                                                                                }
                                                                                                                                                                                continue Label_0696_Outer;
                                                                                                                                                                            }
                                                                                                                                                                            break Label_0809_Outer;
                                                                                                                                                                            psc_e3.r();
                                                                                                                                                                            continue Label_0845_Outer;
                                                                                                                                                                        }
                                                                                                                                                                        psc_e3.r();
                                                                                                                                                                        continue Label_0857_Outer;
                                                                                                                                                                    }
                                                                                                                                                                    break Block_37;
                                                                                                                                                                    continue Block_27_Outer;
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            break Label_1088_Outer;
                                                                                                                                                        }
                                                                                                                                                        psc_e5.r();
                                                                                                                                                        break Label_0821;
                                                                                                                                                    }
                                                                                                                                                    psc_e2.r();
                                                                                                                                                    continue Label_0934_Outer;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            psc_e2.r();
                                                                                                                                            continue Label_0809_Outer;
                                                                                                                                        }
                                                                                                                                        psc_e3.r();
                                                                                                                                        break Label_0797_Outer;
                                                                                                                                        break Block_23_Outer;
                                                                                                                                    }
                                                                                                                                    break Block_49;
                                                                                                                                }
                                                                                                                                psc_e4.r();
                                                                                                                                continue Block_25_Outer;
                                                                                                                            }
                                                                                                                            continue Label_1112_Outer;
                                                                                                                        }
                                                                                                                        psc_e2.r();
                                                                                                                        continue Label_0958_Outer;
                                                                                                                    }
                                                                                                                    break Block_35;
                                                                                                                }
                                                                                                                continue Label_0946_Outer;
                                                                                                            }
                                                                                                            continue Label_0910_Outer;
                                                                                                        }
                                                                                                    }
                                                                                                    psc_au.c(array3);
                                                                                                    psc_au.c(this.n);
                                                                                                    return a;
                                                                                                }
                                                                                                psc_e6.r();
                                                                                                continue Label_0910_Outer;
                                                                                            }
                                                                                            break Block_30_Outer;
                                                                                        }
                                                                                        psc_e7.r();
                                                                                        continue Label_1088_Outer;
                                                                                    }
                                                                                    psc_e7.r();
                                                                                    break Label_0744;
                                                                                }
                                                                                psc_e4.r();
                                                                                continue Label_0946_Outer;
                                                                            }
                                                                        }
                                                                        psc_au.c(array3);
                                                                        psc_au.c(this.n);
                                                                        return b ? 1 : 0;
                                                                    }
                                                                    continue Label_0857_Outer;
                                                                }
                                                                psc_e7.r();
                                                                continue Label_0655_Outer;
                                                            }
                                                            psc_e0.r();
                                                            continue Label_0946_Outer;
                                                        }
                                                        continue Block_23_Outer;
                                                    }
                                                    psc_e0.r();
                                                    continue Label_0655_Outer;
                                                }
                                                psc_e7.r();
                                                continue Label_0785_Outer;
                                            }
                                            psc_e4.r();
                                            continue Label_0946_Outer;
                                        }
                                        psc_e0.r();
                                        continue Label_0785_Outer;
                                    }
                                    psc_e4.r();
                                    continue Label_0655_Outer;
                                }
                                psc_e2.r();
                                continue;
                            }
                            continue Label_0785_Outer;
                        }
                        continue;
                    }
                }
                psc_au.c(array3);
                psc_au.c(this.n);
                return b ? 1 : 0;
            }
        }
    }
    
    public void b(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        throw new psc_en("Improper call to verifyUpdate.");
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final psc_az psc_az, final psc_et psc_et, final byte[] array2, int n3, final int n4) {
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_e0 psc_e7 = null;
        psc_e0 psc_e8 = null;
        psc_e0 psc_e9 = null;
        psc_e0 psc_e10 = null;
        final byte[] array3 = new byte[20];
        final byte[] array4 = new byte[20];
        Label_1302: {
            try {
                this.e();
                psc_e0 = this.a.newInstance();
                psc_e3 = this.a.newInstance();
                psc_e2 = this.a.newInstance();
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e6 = this.a.newInstance();
                psc_e7 = this.a.newInstance();
                psc_e8 = this.a.newInstance();
                psc_e9 = this.a.newInstance();
                psc_e10 = this.a.newInstance();
                if (psc_az.e().compareTo("NoDigest") == 0) {
                    for (int i = 0; i < 20; ++i, ++n3) {
                        array3[i] = array2[n3];
                        array4[i] = array2[n3 + 20];
                    }
                }
                else {
                    final psc_h psc_h = new psc_h(0);
                    final psc_j psc_j = new psc_j();
                    final psc_p psc_p = new psc_p(0);
                    final psc_p psc_p2 = new psc_p(0);
                    psc_l.a(array2, n3, new psc_i[] { psc_h, psc_p, psc_p2, psc_j });
                    for (int n5 = psc_p.c + psc_p.d - 1, j = 19; j >= 0; --j, --n5) {
                        if (n5 >= psc_p.c) {
                            array3[j] = psc_p.b[n5];
                        }
                    }
                    for (int n6 = psc_p2.c + psc_p2.d - 1, k = 19; k >= 0; --k, --n6) {
                        if (n6 >= psc_p2.c) {
                            array4[k] = psc_p2.b[n6];
                        }
                    }
                }
                psc_e3.a(array3, 0, 20);
                psc_e2.a(array4, 0, 20);
                psc_e2.e(this.j, psc_e4);
                psc_e5.a(array, n, n2);
                psc_e5.a(psc_e4, this.j, psc_e6);
                psc_e3.a(psc_e4, this.j, psc_e7);
                this.k.c(psc_e6, this.i, psc_e8);
                this.l.c(psc_e7, this.i, psc_e9);
                psc_e8.a(psc_e9, this.i, psc_e0);
                psc_e0.d(this.j, psc_e10);
                byte[] l;
                int n7;
                for (l = psc_e10.j(20), n7 = 0; n7 < 20 && l[n7] == array3[n7]; ++n7) {}
                if (n7 < 20) {
                    final boolean b = false;
                    break Label_1302;
                }
                final boolean b2 = true;
                break Label_1302;
            }
            catch (psc_ap psc_ap) {
                final boolean b3 = false;
                break Label_1302;
            }
            catch (psc_m psc_m) {
                final boolean b3 = false;
                break Label_1302;
            }
            catch (InstantiationException ex) {
                final boolean b3 = false;
                break Label_1302;
            }
            catch (IllegalAccessException ex2) {
                final boolean b3 = false;
                break Label_1302;
            }
            finally {
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e2 != null) {
                    psc_e2.r();
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
                if (psc_e7 != null) {
                    psc_e7.r();
                }
                if (psc_e8 != null) {
                    psc_e8.r();
                }
                if (psc_e9 != null) {
                    psc_e9.r();
                }
                if (psc_e10 != null) {
                    psc_e10.r();
                }
                Label_1549: {
                    if (psc_e0 != null) {
                        psc_e0.r();
                        break Label_1549;
                    }
                    break Label_1549;
                }
                while (true) {}
                // iftrue(Label_0775:, psc_e9 == null)
                // iftrue(Label_1215:, psc_e4 == null)
                // iftrue(Label_0751:, psc_e7 == null)
                // iftrue(Label_0965:, psc_e4 == null)
                // iftrue(Label_1388:, psc_e8 == null)
                // iftrue(Label_0739:, psc_e6 == null)
                // iftrue(Label_1412:, psc_e10 == null)
                // iftrue(Label_1239:, psc_e6 == null)
                // iftrue(Label_0840:, psc_e4 == null)
                // iftrue(Label_1287:, psc_e10 == null)
                // iftrue(Label_1078:, psc_e2 == null)
                // iftrue(Label_1316:, psc_e3 == null)
                // iftrue(Label_0727:, psc_e5 == null)
                // iftrue(Label_0941:, psc_e3 == null)
                // iftrue(Label_1066:, psc_e3 == null)
                // iftrue(Label_1162:, psc_e10 == null)
                // iftrue(Label_1013:, psc_e8 == null)
                // iftrue(Label_0953:, psc_e2 == null)
                // iftrue(Label_1328:, psc_e2 == null)
                // iftrue(Label_1400:, psc_e9 == null)
                // iftrue(Label_1299:, psc_e0 == null)
                // iftrue(Label_0799:, psc_e0 == null)
                // iftrue(Label_1126:, psc_e7 == null)
                // iftrue(Label_1203:, psc_e2 == null)
                // iftrue(Label_0828:, psc_e2 == null)
                // iftrue(Label_0912:, psc_e10 == null)
                // iftrue(Label_0924:, psc_e0 == null)
                // iftrue(Label_1174:, psc_e0 == null)
                // iftrue(Label_0977:, psc_e5 == null)
                // iftrue(Label_0787:, psc_e10 == null)
                // iftrue(Label_1340:, psc_e4 == null)
                // iftrue(Label_1364:, psc_e6 == null)
                // iftrue(Label_1251:, psc_e7 == null)
                // iftrue(Label_0816:, psc_e3 == null)
                // iftrue(Label_1263:, psc_e8 == null)
                // iftrue(Label_1037:, psc_e10 == null)
                // iftrue(Label_1114:, psc_e6 == null)
                // iftrue(Label_1275:, psc_e9 == null)
                // iftrue(Label_0715:, psc_e4 == null)
                // iftrue(Label_0876:, psc_e7 == null)
                // iftrue(Label_0852:, psc_e5 == null)
                // iftrue(Label_0864:, psc_e6 == null)
                // iftrue(Label_0691:, psc_e3 == null)
                // iftrue(Label_1025:, psc_e9 == null)
                // iftrue(Label_0763:, psc_e8 == null)
                // iftrue(Label_1001:, psc_e7 == null)
                // iftrue(Label_0703:, psc_e2 == null)
                // iftrue(Label_1227:, psc_e5 == null)
                // iftrue(Label_1376:, psc_e7 == null)
                // iftrue(Label_1424:, psc_e0 == null)
                // iftrue(Label_1191:, psc_e3 == null)
                // iftrue(Label_0989:, psc_e6 == null)
                // iftrue(Label_1352:, psc_e5 == null)
                // iftrue(Label_0900:, psc_e9 == null)
                // iftrue(Label_1049:, psc_e0 == null)
                // iftrue(Label_1138:, psc_e8 == null)
                // iftrue(Label_1102:, psc_e5 == null)
                // iftrue(Label_1150:, psc_e9 == null)
                // iftrue(Label_1090:, psc_e4 == null)
                // iftrue(Label_0888:, psc_e8 == null)
                while (true) {
                    Block_67: {
                        while (true) {
                        Block_54_Outer:
                            while (true) {
                            Label_0691_Outer:
                                while (true) {
                                    Label_1138: {
                                        final boolean b3;
                                    Block_50_Outer:
                                        while (true) {
                                            Block_17: {
                                                while (true) {
                                                    final boolean b;
                                                    final boolean b2;
                                                    Label_1227_Outer:Block_41_Outer:Label_0828_Outer:Label_1001_Outer:
                                                    while (true) {
                                                    Label_0965_Outer:
                                                        while (true) {
                                                        Label_0852_Outer:
                                                            while (true) {
                                                                Block_39: {
                                                                    while (true) {
                                                                    Label_1126:
                                                                        while (true) {
                                                                        Block_34_Outer:
                                                                            while (true) {
                                                                                Block_28: {
                                                                                    while (true) {
                                                                                    Label_1162_Outer:
                                                                                        while (true) {
                                                                                            Label_0888: {
                                                                                            Block_70_Outer:
                                                                                                while (true) {
                                                                                                    Block_55: {
                                                                                                        while (true) {
                                                                                                            Label_1340: {
                                                                                                                while (true) {
                                                                                                                Block_37:
                                                                                                                    while (true) {
                                                                                                                        Label_0977: {
                                                                                                                            while (true) {
                                                                                                                                while (true) {
                                                                                                                                Label_1066_Outer:
                                                                                                                                    while (true) {
                                                                                                                                    Block_60:
                                                                                                                                        while (true) {
                                                                                                                                            Block_29: {
                                                                                                                                                while (true) {
                                                                                                                                                    Block_18_Outer:Label_1328_Outer:Block_72_Outer:
                                                                                                                                                    while (true) {
                                                                                                                                                        Block_47: {
                                                                                                                                                            while (true) {
                                                                                                                                                                Label_1364: {
                                                                                                                                                                    Block_66: {
                                                                                                                                                                    Block_32:
                                                                                                                                                                        while (true) {
                                                                                                                                                                            Block_68: {
                                                                                                                                                                            Label_1215:
                                                                                                                                                                                while (true) {
                                                                                                                                                                                    Block_52: {
                                                                                                                                                                                        Label_0989: {
                                                                                                                                                                                            while (true) {
                                                                                                                                                                                            Label_0751:
                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                    Label_1013: {
                                                                                                                                                                                                        Block_36: {
                                                                                                                                                                                                            Block_22:Block_19_Outer:Block_31_Outer:
                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                Block_58: {
                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                        Block_43: {
                                                                                                                                                                                                                        Label_1150_Outer:
                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                            Label_0703:
                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                    Label_1376_Outer:Block_64_Outer:
                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                        Block_63_Outer:Block_45_Outer:
                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                            Label_1263: {
                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                                    Block_62_Outer:
                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                            Label_1251: {
                                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                                    Block_59: {
                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                                                            Label_0816_Outer:
                                                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                                                                        Block_26:Block_25_Outer:Block_69_Outer:
                                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                                                                    Label_0775: {
                                                                                                                                                                                                                                                                                                    Label_1191_Outer:
                                                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                                                                                                Label_0912: {
                                                                                                                                                                                                                                                                                                                    Block_75: {
                                                                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                                                                            Label_0900: {
                                                                                                                                                                                                                                                                                                                                Label_1114: {
                                                                                                                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                                                                                                                        Block_20: {
                                                                                                                                                                                                                                                                                                                                            Label_1287: {
                                                                                                                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                                                                                                                    Label_1388: {
                                                                                                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                                                                                                            Block_65: {
                                                                                                                                                                                                                                                                                                                                                                Label_0715: {
                                                                                                                                                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                                                                                                                                                        Label_1275: {
                                                                                                                                                                                                                                                                                                                                                                            Block_73: {
                                                                                                                                                                                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                                                                                                                                                                                    Block_61: {
                                                                                                                                                                                                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                                                                                                                                                                                                            Block_24: {
                                                                                                                                                                                                                                                                                                                                                                                                break Block_24;
                                                                                                                                                                                                                                                                                                                                                                                                break Block_59;
                                                                                                                                                                                                                                                                                                                                                                                                break Block_22;
                                                                                                                                                                                                                                                                                                                                                                                                break Block_39;
                                                                                                                                                                                                                                                                                                                                                                                                break Block_73;
                                                                                                                                                                                                                                                                                                                                                                                                psc_e9.r();
                                                                                                                                                                                                                                                                                                                                                                                                break Label_1275;
                                                                                                                                                                                                                                                                                                                                                                                                break Label_0965_Outer;
                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                            psc_e9.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_0775;
                                                                                                                                                                                                                                                                                                                                                                                            psc_e4.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_0715;
                                                                                                                                                                                                                                                                                                                                                                                            Label_0799: {
                                                                                                                                                                                                                                                                                                                                                                                                return b;
                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                            psc_e2.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_0691_Outer;
                                                                                                                                                                                                                                                                                                                                                                                            psc_e8.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_1263;
                                                                                                                                                                                                                                                                                                                                                                                            break Block_75;
                                                                                                                                                                                                                                                                                                                                                                                            break Block_61;
                                                                                                                                                                                                                                                                                                                                                                                            psc_e6.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_0989;
                                                                                                                                                                                                                                                                                                                                                                                            psc_e10.r();
                                                                                                                                                                                                                                                                                                                                                                                            break Label_0912;
                                                                                                                                                                                                                                                                                                                                                                                            psc_e2.r();
                                                                                                                                                                                                                                                                                                                                                                                            continue Label_1376_Outer;
                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                        psc_e10.r();
                                                                                                                                                                                                                                                                                                                                                                                        break Label_1162_Outer;
                                                                                                                                                                                                                                                                                                                                                                                        break Block_29;
                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                    psc_e6.r();
                                                                                                                                                                                                                                                                                                                                                                                    break Label_0816_Outer;
                                                                                                                                                                                                                                                                                                                                                                                    psc_e9.r();
                                                                                                                                                                                                                                                                                                                                                                                    continue Label_1227_Outer;
                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                            psc_e8.r();
                                                                                                                                                                                                                                                                                                                                                                            break Label_1388;
                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                        break Block_65;
                                                                                                                                                                                                                                                                                                                                                                        continue Block_63_Outer;
                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                    break Block_67;
                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                break Block_20;
                                                                                                                                                                                                                                                                                                                                                                break Block_37;
                                                                                                                                                                                                                                                                                                                                                                break Block_47;
                                                                                                                                                                                                                                                                                                                                                                psc_e4.r();
                                                                                                                                                                                                                                                                                                                                                                break Label_1227_Outer;
                                                                                                                                                                                                                                                                                                                                                                psc_e8.r();
                                                                                                                                                                                                                                                                                                                                                                break Label_0888;
                                                                                                                                                                                                                                                                                                                                                                psc_e6.r();
                                                                                                                                                                                                                                                                                                                                                                break Label_1114;
                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                            psc_e10.r();
                                                                                                                                                                                                                                                                                                                                                            break Label_1287;
                                                                                                                                                                                                                                                                                                                                                            break Block_55;
                                                                                                                                                                                                                                                                                                                                                            break Block_43;
                                                                                                                                                                                                                                                                                                                                                            continue Block_45_Outer;
                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                        psc_e0.r();
                                                                                                                                                                                                                                                                                                                                                        return b3;
                                                                                                                                                                                                                                                                                                                                                        break Block_68;
                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                    continue Label_1066_Outer;
                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                psc_e6.r();
                                                                                                                                                                                                                                                                                                                                                break Label_1364;
                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                            break Block_66;
                                                                                                                                                                                                                                                                                                                                            psc_e7.r();
                                                                                                                                                                                                                                                                                                                                            break Label_1251;
                                                                                                                                                                                                                                                                                                                                            psc_e2.r();
                                                                                                                                                                                                                                                                                                                                            break Label_0703;
                                                                                                                                                                                                                                                                                                                                            Label_1049: {
                                                                                                                                                                                                                                                                                                                                                return b3;
                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                        psc_e5.r();
                                                                                                                                                                                                                                                                                                                                        continue Block_19_Outer;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    psc_e9.r();
                                                                                                                                                                                                                                                                                                                                    break Label_0900;
                                                                                                                                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                                                                                                                                        break Block_26;
                                                                                                                                                                                                                                                                                                                                        psc_e10.r();
                                                                                                                                                                                                                                                                                                                                        continue Block_25_Outer;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    psc_e5.r();
                                                                                                                                                                                                                                                                                                                                    break Label_0977;
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                break Block_52;
                                                                                                                                                                                                                                                                                                                                break Block_58;
                                                                                                                                                                                                                                                                                                                                psc_e4.r();
                                                                                                                                                                                                                                                                                                                                break Label_1340;
                                                                                                                                                                                                                                                                                                                                break Block_28;
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                            continue Block_45_Outer;
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                    psc_e10.r();
                                                                                                                                                                                                                                                                                                                    break Block_18_Outer;
                                                                                                                                                                                                                                                                                                                    psc_e6.r();
                                                                                                                                                                                                                                                                                                                    break Label_1150_Outer;
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                break Block_36;
                                                                                                                                                                                                                                                                                                                break Block_50_Outer;
                                                                                                                                                                                                                                                                                                                psc_e3.r();
                                                                                                                                                                                                                                                                                                                continue Block_69_Outer;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            continue Label_1191_Outer;
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                        psc_e9.r();
                                                                                                                                                                                                                                                                                                        break Block_62_Outer;
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                    continue Block_69_Outer;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                continue Label_0816_Outer;
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                            psc_e7.r();
                                                                                                                                                                                                                                                                                            continue Block_64_Outer;
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                        psc_e0.r();
                                                                                                                                                                                                                                                                                        return b;
                                                                                                                                                                                                                                                                                        psc_e3.r();
                                                                                                                                                                                                                                                                                        continue Block_31_Outer;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    continue Block_62_Outer;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                continue Block_18_Outer;
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            psc_e5.r();
                                                                                                                                                                                                                                                                            continue Block_70_Outer;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    psc_e4.r();
                                                                                                                                                                                                                                                                    break Label_1215;
                                                                                                                                                                                                                                                                    continue Block_70_Outer;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            continue Label_1227_Outer;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                        continue Label_0828_Outer;
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    continue Label_1150_Outer;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            continue Block_19_Outer;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        psc_e8.r();
                                                                                                                                                                                                                                        continue Block_64_Outer;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    psc_e9.r();
                                                                                                                                                                                                                                    continue Label_1001_Outer;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                continue Label_1227_Outer;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            break Block_32;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        psc_e8.r();
                                                                                                                                                                                                                        break Label_1013;
                                                                                                                                                                                                                        break Block_34_Outer;
                                                                                                                                                                                                                        continue Label_1162_Outer;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                                psc_e2.r();
                                                                                                                                                                                                                continue Block_19_Outer;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            psc_e7.r();
                                                                                                                                                                                                            break Label_0751;
                                                                                                                                                                                                            break Block_17;
                                                                                                                                                                                                            psc_e0.r();
                                                                                                                                                                                                            return b3;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        psc_e0.r();
                                                                                                                                                                                                        return b2;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    continue Label_1328_Outer;
                                                                                                                                                                                                }
                                                                                                                                                                                                continue Block_54_Outer;
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                        break Label_0852_Outer;
                                                                                                                                                                                    }
                                                                                                                                                                                    psc_e7.r();
                                                                                                                                                                                    break Label_1126;
                                                                                                                                                                                    continue Block_34_Outer;
                                                                                                                                                                                }
                                                                                                                                                                                break Block_60;
                                                                                                                                                                            }
                                                                                                                                                                            psc_e2.r();
                                                                                                                                                                            continue Block_72_Outer;
                                                                                                                                                                        }
                                                                                                                                                                        psc_e7.r();
                                                                                                                                                                        break Block_54_Outer;
                                                                                                                                                                    }
                                                                                                                                                                    psc_e0.r();
                                                                                                                                                                    return b3;
                                                                                                                                                                }
                                                                                                                                                                continue Block_70_Outer;
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        psc_e3.r();
                                                                                                                                                        continue Label_1001_Outer;
                                                                                                                                                    }
                                                                                                                                                    continue Block_34_Outer;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            psc_e4.r();
                                                                                                                                            continue Label_0852_Outer;
                                                                                                                                        }
                                                                                                                                        psc_e5.r();
                                                                                                                                        continue Block_41_Outer;
                                                                                                                                    }
                                                                                                                                    Label_0924: {
                                                                                                                                        return b2;
                                                                                                                                    }
                                                                                                                                    psc_e8.r();
                                                                                                                                    break Label_1138;
                                                                                                                                    psc_e5.r();
                                                                                                                                    continue Block_54_Outer;
                                                                                                                                }
                                                                                                                                Label_1174: {
                                                                                                                                    return b3;
                                                                                                                                }
                                                                                                                                continue Label_0965_Outer;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        continue Label_0828_Outer;
                                                                                                                    }
                                                                                                                    psc_e3.r();
                                                                                                                    continue Block_34_Outer;
                                                                                                                }
                                                                                                                Label_1299: {
                                                                                                                    return b3;
                                                                                                                }
                                                                                                            }
                                                                                                            continue Block_54_Outer;
                                                                                                        }
                                                                                                    }
                                                                                                    psc_e10.r();
                                                                                                    continue Label_0965_Outer;
                                                                                                }
                                                                                                Label_1424: {
                                                                                                    return b3;
                                                                                                }
                                                                                            }
                                                                                            continue Label_0965_Outer;
                                                                                        }
                                                                                        continue Label_0691_Outer;
                                                                                    }
                                                                                }
                                                                                psc_e2.r();
                                                                                continue Label_1001_Outer;
                                                                            }
                                                                            psc_e5.r();
                                                                            continue Label_0691_Outer;
                                                                        }
                                                                        continue Block_50_Outer;
                                                                    }
                                                                }
                                                                psc_e4.r();
                                                                continue Block_54_Outer;
                                                            }
                                                            psc_e7.r();
                                                            continue Label_0965_Outer;
                                                        }
                                                        psc_e6.r();
                                                        continue Block_54_Outer;
                                                    }
                                                    continue;
                                                }
                                            }
                                            psc_e3.r();
                                            continue;
                                        }
                                        psc_e0.r();
                                        return b3;
                                    }
                                    continue;
                                }
                                continue;
                            }
                            continue;
                        }
                    }
                    psc_e3.r();
                    continue;
                }
            }
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ke psc_ke = new psc_ke();
        psc_ke.a = this.a;
        psc_ke.b = this.b;
        psc_ke.c = this.c;
        psc_ke.d = this.d;
        psc_ke.f = this.f;
        if (this.i != null) {
            psc_ke.i = (psc_ez)this.i.clone();
        }
        if (this.j != null) {
            psc_ke.j = (psc_ez)this.j.clone();
        }
        if (this.k != null) {
            psc_ke.k = (psc_ez)this.k.clone();
        }
        if (this.l != null) {
            psc_ke.l = (psc_ez)this.l.clone();
        }
        if (this.m != null) {
            psc_ke.m = (psc_ez)this.m.clone();
        }
        if (this.o != null) {
            psc_ke.n = (byte[])psc_au.a(this.n, this.o);
            psc_ke.o = psc_au.a(psc_ke.n);
        }
        return psc_ke;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.l();
        objectOutputStream.defaultWriteObject();
        this.k();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
        this.g();
    }
    
    private void g() {
        this.o = psc_au.b(this.n);
        this.k();
    }
    
    public void k() {
        if (this.m != null) {
            this.m.p();
        }
        if (this.o != null) {
            this.o.c();
        }
    }
    
    public void l() {
        if (this.m != null) {
            this.m.q();
        }
        if (this.o != null) {
            this.o.d();
        }
    }
    
    protected void f() {
        if (this.l != null) {
            this.l.r();
        }
        if (this.m != null) {
            this.m.r();
        }
        final psc_e0 psc_e0 = null;
        this.m = psc_e0;
        this.l = psc_e0;
    }
    
    private void h() {
        if (this.i != null) {
            this.i.r();
        }
        if (this.j != null) {
            this.j.r();
        }
        if (this.k != null) {
            this.k.r();
        }
        final psc_e0 i = null;
        this.k = i;
        this.j = i;
        this.i = i;
    }
    
    public void y() {
        super.y();
        this.f();
        this.h();
        psc_au.b(this.n, this.o);
        this.n = null;
        this.c = -1;
        this.d = 0;
        this.f = 0;
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
