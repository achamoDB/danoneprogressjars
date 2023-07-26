import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ja extends psc_em implements psc_i2, Cloneable, Serializable
{
    protected Class a;
    protected int b;
    protected int c;
    protected int d;
    protected SecureRandom e;
    protected byte[] f;
    protected String g;
    protected int h;
    protected boolean i;
    protected psc_ez j;
    protected int k;
    protected static final int l = 1;
    protected static final int m = 2;
    
    public psc_ja() {
        this.h = 1;
    }
    
    public String o() {
        return "RSA";
    }
    
    protected void h() {
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
    
    protected boolean a(final psc_al psc_al, final psc_dt psc_dt) {
        return psc_al instanceof psc_jt && psc_dt instanceof psc_jq && super.a(psc_al, psc_dt);
    }
    
    public void b(final psc_el psc_el, final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        psc_an.a();
        if (array == null) {
            throw new psc_be("null key pair generation parameters");
        }
        if (psc_aq.c() && array.length > 2) {
            throw new psc_be("Strong RSA key pair generation requires 2 primes");
        }
        this.b(psc_el, array, secureRandom, null);
        if (this.b < 1024 || this.b % 256 != 0) {
            throw new psc_be("Strong RSA key pair generation requires bit length >= 1024 and multiple of 256.");
        }
        this.j = new psc_ez();
        final int n = (this.b + 1) / 2;
        this.k = this.b / 2 - 100;
        try {
            this.j.i(n);
        }
        catch (psc_e1 psc_e1) {
            throw new psc_be(psc_e1.getMessage());
        }
        try {
            if (this.e instanceof psc_av) {
                final psc_av psc_av = (psc_av)this.e;
                final String algorithm = psc_av.getAlgorithm();
                if (algorithm.compareTo("X931Random") == 0) {
                    final int[] q = psc_av.q();
                    if (q.length < 1 || q[0] != 6) {
                        throw new psc_be("Strong RSA key pair gen requires FIPS186Random, HWRandom or 6-stream X931Random.");
                    }
                }
                else if (algorithm.compareTo("FIPS186Random") != 0 && algorithm.compareTo("HWRandom") != 0) {
                    throw new psc_be("Strong RSA key pair gen requires FIPS186Random, HWRandom or 6-stream X931Random.");
                }
                this.i = true;
                return;
            }
        }
        catch (ClassCastException ex) {
            throw new psc_en(ex.getMessage());
        }
        throw new psc_be("Strong RSA key pair gen requires FIPS186Random, HWRandom or 6-stream X931Random.");
    }
    
    protected boolean a(final psc_el psc_el, final int[] array, final SecureRandom e, final psc_nf[] array2) {
        psc_an.a();
        this.f();
        if (psc_el != null) {
            return false;
        }
        if (array == null || array.length < 1 || array.length > 3) {
            return false;
        }
        if (array[0] < 256 || array[0] > 4096) {
            return false;
        }
        this.b = array[0];
        this.c = 2;
        this.d = 17;
        if (array.length == 3) {
            this.c = array[1];
            this.d = array[2];
        }
        if (array.length == 2) {
            this.d = array[1];
        }
        if (!psc_k8.a(this.c, this.b)) {
            return false;
        }
        if (this.d < 0 || (this.d & 0x1) == 0x0) {
            return false;
        }
        if (e != null) {
            this.e = e;
        }
        if (this.e == null) {
            return false;
        }
        this.i = false;
        this.h = 2;
        return true;
    }
    
    protected void d() throws psc_en {
        psc_an.a();
        this.f();
        if (this.h != 2) {
            throw new psc_en("Cannot reInit, object not initialized.");
        }
    }
    
    void i() {
        if (psc_aq.k() == 1) {
            return;
        }
        if (psc_ar.b(this.p(), this.q())) {
            return;
        }
        throw new SecurityException("An internal FIPS 140-2 required pairwise consistency check failed");
    }
    
    public byte[][] a(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4, final byte[] array5, final byte[] array6) throws psc_en {
        final byte[][] array7 = new byte[8][];
        if (this.h != 2) {
            throw new psc_en("Cannot generate, object not initialized.");
        }
        this.h();
        psc_al a = null;
        psc_dt a2 = null;
        psc_e0 psc_e0 = null;
        final psc_e0[] array8 = new psc_e0[this.c];
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        final psc_e0[] array9 = new psc_e0[this.c];
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        final byte[][] array10 = new byte[this.c * 3 + 3][];
        final byte[][] array11 = new byte[2][];
        (array10[3] = new byte[1])[0] = (byte)this.c;
        Label_1462: {
            try {
                a = psc_al.a("RSA", this.m());
                a2 = psc_dt.a("RSA", this.m());
                psc_e0 = this.a.newInstance();
                for (int i = 0; i < this.c; ++i) {
                    array8[i] = this.a.newInstance();
                    array9[i] = this.a.newInstance();
                }
                psc_e2 = this.a.newInstance();
                psc_e3 = this.a.newInstance();
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e0.h(this.d);
                final int[] array12 = new int[this.c];
                final int n = this.b / this.c;
                int n2 = this.b % this.c;
                for (int j = 0; j < this.c; ++j) {
                    array12[j] = n;
                    if (n2 > 0) {
                        final int[] array13 = array12;
                        final int n3 = j;
                        ++array13[n3];
                        --n2;
                    }
                }
                final int n4 = this.b % this.c;
                int n5 = 1;
                final byte[] array14 = { 0 };
                final byte[][] array15 = { null, null, null };
                boolean b = false;
                while (true) {
                    int n6 = 3;
                    int n7 = 1;
                    int k = 0;
                    while (k < this.c) {
                        int n8 = 0;
                        do {
                            n5 = ((this.a(n5, n7, array14) == 1) ? 4 : 1);
                            switch (k) {
                                case 0: {
                                    b = array8[0].a(array12[0], psc_e0, n6, this.i, this.e, array, array2, array3, array15);
                                    ++n8;
                                    break;
                                }
                                case 1: {
                                    b = array8[k].a(array12[1], psc_e0, n6, this.i, this.e, array4, array5, array6, array15);
                                    ++n8;
                                    break;
                                }
                            }
                            if (!b) {
                                continue;
                            }
                            if (k != 1 || !a(array8[0], array8[1])) {}
                        } while (!b);
                        switch (k) {
                            case 0: {
                                array7[0] = array15[0].clone();
                                array7[1] = array15[1].clone();
                                array7[2] = array15[2].clone();
                                break;
                            }
                            case 1: {
                                array7[3] = array15[0].clone();
                                array7[4] = array15[1].clone();
                                array7[5] = array15[2].clone();
                                break;
                            }
                        }
                        array8[k].b(psc_ez.d, array9[k]);
                        array9[k].d(psc_e0, psc_e4);
                        if (!this.a(psc_e4)) {
                            continue;
                        }
                        if (k == 1 && this.i) {
                            array9[0].c(array9[1], psc_e2);
                            if (psc_e6 == null) {
                                psc_e6 = new psc_ez();
                            }
                            array9[0].f(array9[1], psc_e6);
                            if (!psc_e0.e(psc_e6, psc_e5)) {
                                continue;
                            }
                            if (psc_e5.b(this.j) <= 0) {
                                continue;
                            }
                        }
                        n7 = 4;
                        ++k;
                        n6 = 7;
                    }
                    if (!this.a(array8, array9)) {
                        continue;
                    }
                    if (psc_e2 != null) {
                        array9[0].c(array9[1], psc_e2);
                    }
                    if (psc_e6 != null) {
                        psc_e6 = new psc_ez();
                    }
                    array9[0].f(array9[1], psc_e6);
                    for (int l = 2; l < this.c; ++l) {
                        array9[l].c(psc_e2, psc_e4);
                        psc_e2.a(psc_e4);
                    }
                    if (!psc_e0.e(psc_e6, psc_e5)) {
                        continue;
                    }
                    if (psc_e5.b(psc_ez.c) == 0) {
                        continue;
                    }
                    array8[0].c(array8[1], psc_e3);
                    for (int n9 = 2; n9 < this.c; ++n9) {
                        array8[n9].c(psc_e3, psc_e4);
                        psc_e3.a(psc_e4);
                    }
                    if (!this.a(psc_e0, psc_e5, psc_e3)) {
                        continue;
                    }
                    break;
                }
                array10[0] = psc_e3.n();
                array10[1] = psc_e0.n();
                array10[2] = psc_e5.n();
                int n10 = 4;
                for (int n11 = 0; n11 < this.c; ++n11, ++n10) {
                    array10[n10] = array8[n11].n();
                }
                for (int n12 = 0; n12 < this.c; ++n12, ++n10) {
                    psc_e5.d(array9[n12], psc_e4);
                    array10[n10] = psc_e4.n();
                }
                final psc_e0 psc_e7 = array8[1];
                array8[1] = array8[0];
                array8[0] = psc_e7;
                psc_e3.h(1);
                for (int n13 = 1; n13 < this.c; ++n13, ++n10) {
                    psc_e3.c(array8[n13 - 1], psc_e4);
                    psc_e3.a(psc_e4);
                    psc_e3.d(array8[n13], psc_e4);
                    psc_e4.e(array8[n13], psc_e5);
                    array10[n10] = psc_e5.n();
                }
                final psc_e0 psc_e8 = array8[1];
                array8[1] = array8[0];
                array8[0] = psc_e8;
                a2.a("RSAMultiPrimePrivateKeyCRT", array10);
                array11[0] = array10[0];
                array11[1] = array10[1];
                a.a("RSAPublicKey", array11);
                this.b(a, a2);
                array7[6] = array10[0].clone();
                final byte[] array16 = a2.b("RSAMultiPrimePrivateKeyCRT")[2];
                psc_e6.r();
                psc_e6.a(array16, 0, array16.length);
                array7[7] = psc_e6.j(array7[6].length);
                psc_e8.r();
                this.i();
                final byte[][] array17 = array7;
                break Label_1462;
            }
            catch (psc_ap psc_ap) {
                throw new psc_en("Could not generate the key pair, try reseeding.");
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            finally {
                if (a2 != null) {
                    a2.y();
                }
                if (a != null) {
                    a.y();
                }
                if (array8 != null) {
                    for (int n14 = 0; n14 < this.c; ++n14) {
                        if (array8[n14] != null) {
                            array8[n14].r();
                        }
                    }
                }
                if (array9 != null) {
                    for (int n15 = 0; n15 < this.c; ++n15) {
                        if (array9[n15] != null) {
                            array9[n15].r();
                        }
                    }
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (psc_e6 != null) {
                    psc_e6.r();
                }
                for (int n16 = 2; n16 < array10.length; ++n16) {
                    if (array10[n16] != null) {
                        psc_au.c(array10[n16]);
                    }
                }
                while (true) {}
            Label_1525:
                while (true) {
                    Label_1484: {
                    Label_1641:
                        while (true) {
                            int n18 = 0;
                            Label_1665: {
                                int n17 = 0;
                                int n19 = 0;
                                Block_41_Outer:Block_40_Outer:
                                while (true) {
                                    while (true) {
                                        Label_1533: {
                                        Block_42_Outer:
                                            while (true) {
                                                while (true) {
                                                    Label_1602_Outer:Label_1492_Outer:
                                                    while (true) {
                                                        Label_1614: {
                                                        Label_1492:
                                                            while (true) {
                                                            Label_1519_Outer:
                                                                while (true) {
                                                                    Label_1474: {
                                                                        Block_45: {
                                                                            while (true) {
                                                                            Block_38:
                                                                                while (true) {
                                                                                    while (true) {
                                                                                        Label_1590: {
                                                                                            Block_49: {
                                                                                                while (true) {
                                                                                                    Label_1638: {
                                                                                                        Label_1560: {
                                                                                                        Label_1578_Outer:
                                                                                                            while (true) {
                                                                                                                while (true) {
                                                                                                                    Block_47: {
                                                                                                                        Block_43: {
                                                                                                                            Label_1626: {
                                                                                                                                Block_39: {
                                                                                                                                    break Block_39;
                                                                                                                                    psc_e3.r();
                                                                                                                                    break Label_1626;
                                                                                                                                }
                                                                                                                                n17 = 0;
                                                                                                                                break Label_1533;
                                                                                                                                break Block_45;
                                                                                                                                break Block_43;
                                                                                                                                break Block_49;
                                                                                                                            }
                                                                                                                            break Block_47;
                                                                                                                        }
                                                                                                                        psc_e5.r();
                                                                                                                        break Label_1590;
                                                                                                                    }
                                                                                                                    psc_e6.r();
                                                                                                                    break Label_1638;
                                                                                                                    array9[n17].r();
                                                                                                                    break Label_1560;
                                                                                                                    a.y();
                                                                                                                    break Label_1484;
                                                                                                                    psc_e4.r();
                                                                                                                    continue Block_41_Outer;
                                                                                                                }
                                                                                                                psc_e2.r();
                                                                                                                continue Label_1578_Outer;
                                                                                                            }
                                                                                                            ++n19;
                                                                                                            break Label_1492;
                                                                                                        }
                                                                                                        ++n17;
                                                                                                        break Label_1533;
                                                                                                        a2.y();
                                                                                                        break Label_1474;
                                                                                                        break Block_38;
                                                                                                    }
                                                                                                    n18 = 2;
                                                                                                    break Label_1641;
                                                                                                    continue Block_40_Outer;
                                                                                                }
                                                                                            }
                                                                                            psc_au.c(array10[n18]);
                                                                                            break Label_1665;
                                                                                        }
                                                                                        continue Label_1519_Outer;
                                                                                    }
                                                                                    continue Block_40_Outer;
                                                                                }
                                                                                array8[n19].r();
                                                                                continue Label_1492_Outer;
                                                                            }
                                                                        }
                                                                        psc_e0.r();
                                                                        break Label_1614;
                                                                    }
                                                                    continue Block_42_Outer;
                                                                }
                                                                n19 = 0;
                                                                continue Label_1492;
                                                            }
                                                        }
                                                        continue Label_1602_Outer;
                                                    }
                                                    Label_1566:
                                                    continue Block_40_Outer;
                                                }
                                                continue Block_42_Outer;
                                            }
                                        }
                                        continue;
                                    }
                                    continue Block_40_Outer;
                                }
                            }
                            ++n18;
                            continue Label_1641;
                        }
                    }
                    continue;
                }
                // iftrue(Label_1566:, array9 == null)
                // iftrue(Label_1614:, psc_e0 == null)
                // iftrue(Label_1590:, psc_e5 == null)
                // iftrue(Label_1665:, array10[n18] == null)
                // iftrue(Label_1638:, psc_e6 == null)
                // iftrue(Label_1519:, array8[n19] == null)
                // iftrue(Label_1525:, n19 >= this.c)
                // iftrue(Label_1602:, psc_e2 == null)
                // iftrue(Label_1474:, a2 == null)
                // iftrue(Label_1484:, a == null)
                // iftrue(Label_1626:, psc_e3 == null)
                // iftrue(Label_1578:, psc_e4 == null)
                // iftrue(Label_1560:, array9[n17] == null)
                // iftrue(Label_1566:, n17 >= this.c)
                // iftrue(Label_1671:, n18 >= array10.length)
                // iftrue(Label_1525:, array8 == null)
                Label_1671: {
                    return;
                }
            }
        }
    }
    
    protected void e() throws psc_en {
        if (this.h != 2) {
            throw new psc_en("Cannot generate, object not initialized.");
        }
        this.h();
        psc_al a = null;
        psc_dt a2 = null;
        psc_e0 psc_e0 = null;
        final psc_e0[] array = new psc_e0[this.c];
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        final psc_e0[] array2 = new psc_e0[this.c];
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        final byte[][] array3 = { null };
        final byte[][] array4 = new byte[this.c * 3 + 3][];
        final byte[][] array5 = new byte[2][];
        (array4[3] = new byte[1])[0] = (byte)this.c;
        int[] array6;
        int n;
        int n2;
        int[] array7;
        int n3;
        int n4;
        int n5;
        byte[] array8;
        int n6;
        int n7;
        int k;
        boolean b;
        int n9;
        psc_e0 psc_e7;
        psc_e0 psc_e8;
        int n17;
        int n18;
        int n19;
        int n20;
        Block_54_Outer:Label_1559_Outer:Block_55_Outer:Block_41_Outer:
        while (true) {
            try {
                a = psc_al.a("RSA", this.m());
                a2 = psc_dt.a("RSA", this.m());
                psc_e0 = this.a.newInstance();
                for (int i = 0; i < this.c; ++i) {
                    array[i] = this.a.newInstance();
                    array2[i] = this.a.newInstance();
                }
                psc_e2 = this.a.newInstance();
                psc_e3 = this.a.newInstance();
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e0.h(this.d);
                array6 = new int[this.c];
                n = this.b / this.c;
                n2 = this.b % this.c;
                for (int j = 0; j < this.c; ++j) {
                    array6[j] = n;
                    if (n2 > 0) {
                        array7 = array6;
                        n3 = j;
                        ++array7[n3];
                        --n2;
                    }
                }
                n4 = this.b % this.c;
                n5 = 1;
                array8 = new byte[] { 0 };
                while (true) {
                    n6 = 3;
                    n7 = 1;
                    k = 0;
                    while (k < this.c) {
                        if (this.i) {
                            if (this.a(n5, n7, array8) == 1) {
                                n5 = 4;
                            }
                            else {
                                n5 = 1;
                            }
                            if (k == 0) {
                                b = array[0].a(array6[1], psc_e0, n6, this.i, this.e, null, array3);
                            }
                            else if (k == 1) {
                                b = array[1].a(array6[1], psc_e0, n6, this.i, this.e, this.a(array6[1], array3[0], null), null);
                            }
                            else {
                                b = array[k].a(array6[k], psc_e0, n6, this.i, this.e, null, null);
                            }
                            if (!b) {
                                continue;
                            }
                            if (k == 1 && !a(array[0], array[1])) {
                                continue;
                            }
                        }
                        else if (this.c == 2) {
                            if (!array[k].a(array6[k], psc_e0, n6, this.i, this.e)) {
                                continue;
                            }
                        }
                        else {
                            this.a(array[k], n, n4, this.e);
                            if (!array[k].a(psc_e0, this.e, this.i)) {
                                continue;
                            }
                        }
                        array[k].b(psc_ez.d, array2[k]);
                        array2[k].d(psc_e0, psc_e4);
                        if (!this.a(psc_e4)) {
                            continue;
                        }
                        if (k == 1 && this.i) {
                            array2[0].c(array2[1], psc_e2);
                            if (psc_e6 == null) {
                                psc_e6 = new psc_ez();
                            }
                            array2[0].f(array2[1], psc_e6);
                            if (!psc_e0.e(psc_e6, psc_e5)) {
                                continue;
                            }
                            if (psc_e5.b(this.j) <= 0) {
                                continue;
                            }
                        }
                        n7 = 4;
                        ++k;
                        n6 = 7;
                    }
                    if (!this.a(array, array2)) {
                        continue;
                    }
                    array2[0].c(array2[1], psc_e2);
                    for (int l = 2; l < this.c; ++l) {
                        array2[l].c(psc_e2, psc_e4);
                        psc_e2.a(psc_e4);
                    }
                    if (!this.i) {
                        if (!psc_e0.e(psc_e2, psc_e5)) {
                            continue;
                        }
                    }
                    else {
                        if (psc_e6 != null) {
                            psc_e6 = new psc_ez();
                        }
                        array2[0].f(array2[1], psc_e6);
                        if (!psc_e0.e(psc_e6, psc_e5)) {
                            continue;
                        }
                    }
                    if (psc_e5.b(psc_ez.c) == 0) {
                        continue;
                    }
                    array[0].c(array[1], psc_e3);
                    for (int n8 = 2; n8 < this.c; ++n8) {
                        array[n8].c(psc_e3, psc_e4);
                        psc_e3.a(psc_e4);
                    }
                    if (!this.a(psc_e0, psc_e5, psc_e3)) {
                        continue;
                    }
                    break;
                }
                array4[0] = psc_e3.n();
                array4[1] = psc_e0.n();
                array4[2] = psc_e5.n();
                n9 = 4;
                for (int n10 = 0; n10 < this.c; ++n10, ++n9) {
                    array4[n9] = array[n10].n();
                }
                for (int n11 = 0; n11 < this.c; ++n11, ++n9) {
                    psc_e5.d(array2[n11], psc_e4);
                    array4[n9] = psc_e4.n();
                }
                psc_e7 = array[1];
                array[1] = array[0];
                array[0] = psc_e7;
                psc_e3.h(1);
                for (int n12 = 1; n12 < this.c; ++n12, ++n9) {
                    psc_e3.c(array[n12 - 1], psc_e4);
                    psc_e3.a(psc_e4);
                    psc_e3.d(array[n12], psc_e4);
                    psc_e4.e(array[n12], psc_e5);
                    array4[n9] = psc_e5.n();
                }
                psc_e8 = array[1];
                array[1] = array[0];
                array[0] = psc_e8;
                a2.a("RSAMultiPrimePrivateKeyCRT", array4);
                array5[0] = array4[0];
                array5[1] = array4[1];
                a.a("RSAPublicKey", array5);
                this.b(a, a2);
                this.i();
                break Block_54_Outer;
            }
            catch (psc_ap psc_ap) {
                throw new psc_en("Could not generate the key pair, try reseeding.");
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            finally {
                if (array3[0] != null) {
                    for (int n13 = 0; n13 < array3[0].length; ++n13) {
                        array3[0][n13] = 0;
                    }
                    array3[0] = null;
                }
                if (a2 != null) {
                    a2.y();
                }
                if (a != null) {
                    a.y();
                }
                if (array != null) {
                    for (int n14 = 0; n14 < this.c; ++n14) {
                        if (array[n14] != null) {
                            array[n14].r();
                        }
                    }
                }
                if (array2 != null) {
                    for (int n15 = 0; n15 < this.c; ++n15) {
                        if (array2[n15] != null) {
                            array2[n15].r();
                        }
                    }
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                for (int n16 = 2; n16 < array4.length; ++n16) {
                    if (array4[n16] != null) {
                        psc_au.c(array4[n16]);
                    }
                }
                while (true) {}
                // iftrue(Label_1451:, array[n17] == null)
                // iftrue(Label_1522:, psc_e5 == null)
                // iftrue(Label_1457:, n17 >= this.c)
                // iftrue(Label_1498:, n18 >= this.c)
                // iftrue(Label_1534:, psc_e2 == null)
                // iftrue(Label_1510:, psc_e4 == null)
                // iftrue(Label_1589:, n19 >= array4.length)
                // iftrue(Label_1408:, a2 == null)
                // iftrue(Label_1416:, a == null)
                // iftrue(Label_1492:, array2[n18] == null)
                // iftrue(Label_1457:, array == null)
                // iftrue(Label_1498:, array2 == null)
                // iftrue(Label_1583:, array4[n19] == null)
                // iftrue(Label_1395:, n20 >= array3[0].length)
                // iftrue(Label_1544:, psc_e0 == null)
                // iftrue(Label_1556:, psc_e3 == null)
                while (true) {
                    while (true) {
                    Label_1371:
                        while (true) {
                        Label_1559:
                            while (true) {
                                Block_46_Outer:Block_51_Outer:Label_1465_Outer:Label_1424_Outer:
                                while (true) {
                                    psc_e0.r();
                                    break Label_1371;
                                    Label_1424:Block_45_Outer:Block_48_Outer:Label_1416_Outer:
                                    while (true) {
                                        Block_47: {
                                            Label_1556: {
                                                while (true) {
                                                    Block_44: {
                                                    Block_56:
                                                        while (true) {
                                                            while (true) {
                                                            Label_1400_Outer:
                                                                while (true) {
                                                                    Block_49: {
                                                                    Label_1465:
                                                                        while (true) {
                                                                        Label_1492_Outer:
                                                                            while (true) {
                                                                                while (true) {
                                                                                    Label_1522: {
                                                                                        while (true) {
                                                                                            break Block_47;
                                                                                            psc_e4.r();
                                                                                            while (true) {
                                                                                                Label_1510: {
                                                                                                    break Label_1510;
                                                                                                    ++n18;
                                                                                                    break Label_1465;
                                                                                                    psc_e5.r();
                                                                                                    break Label_1522;
                                                                                                }
                                                                                                continue Label_1424_Outer;
                                                                                            }
                                                                                            continue Block_51_Outer;
                                                                                        }
                                                                                        break Block_49;
                                                                                    }
                                                                                    psc_e2.r();
                                                                                    break Label_1424;
                                                                                    array2[n18].r();
                                                                                    continue Label_1465_Outer;
                                                                                }
                                                                                n17 = 0;
                                                                                continue Label_1424;
                                                                                Label_1498: {
                                                                                    continue Label_1492_Outer;
                                                                                }
                                                                            }
                                                                            n18 = 0;
                                                                            continue Label_1465;
                                                                        }
                                                                        break Block_56;
                                                                        Label_1589: {
                                                                            continue Block_54_Outer;
                                                                        }
                                                                        Label_1408: {
                                                                            while (true) {
                                                                                while (true) {
                                                                                    a2.y();
                                                                                    break Label_1408;
                                                                                    continue Label_1400_Outer;
                                                                                }
                                                                                Label_1395:
                                                                                array3[0] = null;
                                                                                continue Label_1416_Outer;
                                                                            }
                                                                        }
                                                                        break Block_44;
                                                                    }
                                                                    continue Block_45_Outer;
                                                                }
                                                                continue Block_48_Outer;
                                                            }
                                                            Label_1457: {
                                                                continue Label_1559_Outer;
                                                            }
                                                        }
                                                        Block_57: {
                                                            break Block_57;
                                                            psc_e3.r();
                                                            break Label_1556;
                                                        }
                                                        psc_au.c(array4[n19]);
                                                        break Block_46_Outer;
                                                        break Label_1559;
                                                    }
                                                    a.y();
                                                    continue Block_55_Outer;
                                                }
                                                n20 = 0;
                                                continue Label_1371;
                                            }
                                            n19 = 2;
                                            continue Label_1559;
                                        }
                                        array[n17].r();
                                        Label_1451: {
                                            ++n17;
                                        }
                                        continue Label_1424;
                                    }
                                    continue Block_46_Outer;
                                }
                                ++n19;
                                continue Label_1559;
                            }
                            array3[0][n20] = 0;
                            ++n20;
                            continue Label_1371;
                        }
                        continue Block_41_Outer;
                    }
                    continue;
                }
            }
            // iftrue(Label_1400:, array3[0] == null)
            break;
        }
    }
    
    static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2) throws psc_e1 {
        final int n = psc_e0.o() - psc_e2.o();
        return n > 100 || n < 100;
    }
    
    static boolean a(final byte[] array, final byte[] array2) throws psc_e1 {
        final psc_ez psc_ez = new psc_ez();
        final psc_ez psc_ez2 = new psc_ez();
        psc_ez.a(array, 0, array.length);
        psc_ez2.a(array2, 0, array2.length);
        return a(psc_ez, psc_ez2);
    }
    
    byte[] a(final int n, final byte[] array, final byte[] array2) throws psc_e1 {
        if (array == null) {
            return array2;
        }
        psc_ez psc_ez = null;
        psc_ez psc_ez2 = null;
        psc_ez psc_ez3 = null;
        Label_0211: {
            try {
                psc_ez = new psc_ez();
                psc_ez2 = new psc_ez();
                psc_ez3 = new psc_ez();
                final int n2 = (n + 7) / 8;
                psc_ez2.a(array, 0, array.length);
                byte[] array3;
                if (array2 != null) {
                    array3 = array2.clone();
                }
                else {
                    array3 = new byte[n2];
                    this.e.nextBytes(array3);
                }
                psc_ez3.a(array3, 0, array3.length);
                while (true) {
                    if (psc_ez2.b((psc_e0)psc_ez3) > 0) {
                        psc_ez2.b(psc_ez3, (psc_e0)psc_ez);
                    }
                    else {
                        psc_ez3.b(psc_ez2, (psc_e0)psc_ez);
                    }
                    if (psc_ez.o() >= this.k) {
                        break;
                    }
                    this.e.nextBytes(array3);
                    psc_ez3.a(array3, 0, array3.length);
                }
                psc_a5.a(a(array, array3));
                final byte[] array4 = array3;
                break Label_0211;
            }
            finally {
                if (psc_ez2 != null) {
                    psc_ez2.r();
                }
                if (psc_ez3 != null) {
                    psc_ez3.r();
                }
                Label_0278: {
                    if (psc_ez != null) {
                        psc_ez.r();
                        break Label_0278;
                    }
                    break Label_0278;
                }
                while (true) {}
                // iftrue(Label_0233:, psc_ez3 == null)
                // iftrue(Label_0223:, psc_ez2 == null)
                final byte[] array4;
                Label_0233: {
                    while (true) {
                    Block_9:
                        while (true) {
                            break Block_9;
                            psc_ez2.r();
                            continue;
                        }
                        psc_ez3.r();
                        break Label_0233;
                        continue;
                    }
                    Label_0243: {
                        return array4;
                    }
                }
                // iftrue(Label_0243:, psc_ez == null)
                psc_ez.r();
                return array4;
            }
        }
    }
    
    private void a(final psc_e0 psc_e0, final int n, final int n2, final SecureRandom secureRandom) throws psc_en {
        final byte[] bytes = new byte[(n + 7) / 8];
        secureRandom.nextBytes(bytes);
        try {
            bytes[0] = (byte)((bytes[0] & 0xFF) >> bytes.length * 8 - n);
            psc_e0.a(bytes, 0, bytes.length);
            final psc_e0 psc_e2 = this.a.newInstance();
            final psc_e0 psc_e3 = this.a.newInstance();
            final psc_e0 psc_e4 = this.a.newInstance();
            switch (n2) {
                case 2: {
                    final byte[] array = { 80, -94, -117, -25 };
                    final byte[] array2 = { 20, -11, 110, -82 };
                    psc_e2.a(array, 0, array.length);
                    psc_e3.a(array2, 0, array2.length);
                    break;
                }
                case 1: {
                    final byte[] array3 = { 64, 0, 0, 0 };
                    final byte[] array4 = { 16, -94, -117, -25 };
                    psc_e2.a(array3, 0, array3.length);
                    psc_e3.a(array4, 0, array4.length);
                    break;
                }
                default: {
                    final byte[] array5 = { 50, -53, -3, 75 };
                    final byte[] array6 = { 13, 52, 2, -75 };
                    psc_e2.a(array5, 0, array5.length);
                    psc_e3.a(array6, 0, array6.length);
                    break;
                }
            }
            psc_e2.n(n);
            psc_e0.c(psc_e3, psc_e4);
            psc_e4.a(psc_e2, psc_e0);
            psc_e0.p(30);
            psc_e0.a(0, 1);
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Invalid generate input.");
        }
        catch (InstantiationException ex) {
            throw new psc_en("Bad arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_en("Bad arithmetic class.");
        }
        finally {
            psc_au.c(bytes);
        }
    }
    
    private boolean a(final psc_e0[] array, final psc_e0[] array2) {
        try {
            for (int i = 0; i < array.length - 1; ++i) {
                int n = i;
                for (int j = i + 1; j < array.length; ++j) {
                    final int b = array[n].b(array[j]);
                    if (b == 0) {
                        return false;
                    }
                    if (b < 0) {
                        n = j;
                    }
                }
                if (n != i) {
                    final psc_e0 psc_e0 = array[i];
                    array[i] = array[n];
                    array[n] = psc_e0;
                    final psc_e0 psc_e2 = array2[i];
                    array2[i] = array2[n];
                    array2[n] = psc_e2;
                }
            }
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        return true;
    }
    
    private int a(final int n, final int n2, final byte[] array) {
        if (n == n2) {
            return n2;
        }
        for (int i = n; i <= 6; ++i) {
            this.e.nextBytes(array);
        }
        for (int j = 1; j < n2; ++j) {
            this.e.nextBytes(array);
        }
        return n2;
    }
    
    public boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) {
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        boolean b = false;
        Label_0272: {
            try {
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e6 = this.a.newInstance();
                psc_e4.h(127);
                psc_e4.c(psc_e0, psc_e3, psc_e5);
                psc_e5.c(psc_e2, psc_e3, psc_e6);
                if (psc_e6.b(psc_e4) == 0) {
                    b = true;
                }
                break Label_0272;
            }
            catch (InstantiationException ex) {
                b = false;
                break Label_0272;
            }
            catch (IllegalAccessException ex2) {
                b = false;
                break Label_0272;
            }
            catch (psc_e1 psc_e7) {
                b = false;
                break Label_0272;
            }
            finally {
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                Label_0351: {
                    if (psc_e6 != null) {
                        psc_e6.r();
                        break Label_0351;
                    }
                    break Label_0351;
                }
                while (true) {}
                // iftrue(Label_0175:, psc_e5 == null)
                // iftrue(Label_0204:, psc_e4 == null)
                // iftrue(Label_0310:, psc_e6 == null)
                // iftrue(Label_0286:, psc_e4 == null)
                // iftrue(Label_0163:, psc_e4 == null)
                // iftrue(Label_0187:, psc_e6 == null)
                // iftrue(Label_0228:, psc_e6 == null)
                // iftrue(Label_0245:, psc_e4 == null)
                // iftrue(Label_0298:, psc_e5 == null)
                // iftrue(Label_0216:, psc_e5 == null)
                // iftrue(Label_0269:, psc_e6 == null)
                while (true) {
                    Label_0245: {
                        Block_19: {
                            while (true) {
                                Block_11: {
                                    Block_14: {
                                        while (true) {
                                        Block_17:
                                            while (true) {
                                                Block_13_Outer:Block_18_Outer:
                                                while (true) {
                                                    psc_e5.r();
                                                    while (true) {
                                                        while (true) {
                                                            Label_0216: {
                                                                break Label_0216;
                                                            Block_10_Outer:
                                                                while (true) {
                                                                    Block_9: {
                                                                        break Block_9;
                                                                        break Block_11;
                                                                        psc_e6.r();
                                                                        return b;
                                                                        Label_0269: {
                                                                            return b;
                                                                        }
                                                                        Label_0310:
                                                                        return b;
                                                                        break Block_19;
                                                                    }
                                                                    psc_e5.r();
                                                                    while (true) {
                                                                        Label_0175: {
                                                                            break Label_0175;
                                                                            break Block_17;
                                                                            break Block_13_Outer;
                                                                            psc_e6.r();
                                                                            return b;
                                                                        }
                                                                        continue Block_18_Outer;
                                                                    }
                                                                    psc_e5.r();
                                                                    continue Block_10_Outer;
                                                                }
                                                                Label_0228: {
                                                                    return b;
                                                                }
                                                            }
                                                            continue Block_18_Outer;
                                                        }
                                                        break Block_14;
                                                        continue;
                                                    }
                                                    continue Block_13_Outer;
                                                }
                                                psc_e4.r();
                                                continue;
                                            }
                                            psc_e4.r();
                                            continue;
                                        }
                                    }
                                    psc_e4.r();
                                    break Label_0245;
                                }
                                psc_e4.r();
                                continue;
                            }
                            Label_0187: {
                                return b;
                            }
                            psc_e6.r();
                            return b;
                        }
                        psc_e6.r();
                        return b;
                    }
                    psc_e5.r();
                    continue;
                }
            }
            // iftrue(Label_0257:, psc_e5 == null)
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av j = this.j();
        objectOutputStream.defaultWriteObject();
        this.a(j);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.k();
    }
    
    private psc_av j() {
        if (this.e == null) {
            return null;
        }
        if (!(this.e instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.e;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.g = psc_av.getAlgorithm();
        this.f = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.e;
        this.e = null;
        return psc_av2;
    }
    
    private void a(final psc_av e) {
        if (this.f == null) {
            return;
        }
        for (int i = 0; i < this.f.length; ++i) {
            this.f[i] = 0;
        }
        this.f = null;
        this.g = null;
        this.e = e;
    }
    
    private void k() {
        if (this.f == null) {
            return;
        }
        this.e = psc_av.a(this.g, this.f);
        for (int i = 0; i < this.f.length; ++i) {
            this.f[i] = 0;
        }
        this.f = null;
        this.g = null;
    }
    
    protected psc_em c() throws CloneNotSupportedException {
        final psc_ja psc_ja = new psc_ja();
        psc_ja.a = this.a;
        psc_ja.b = this.b;
        psc_ja.c = this.c;
        psc_ja.d = this.d;
        psc_ja.e = this.e;
        psc_ja.h = this.h;
        psc_ja.i = this.i;
        psc_ja.k = this.k;
        if (this.j != null) {
            psc_ja.j = (psc_ez)this.j.clone();
        }
        psc_ja.a(this);
        return psc_ja;
    }
    
    private boolean a(final psc_e0 psc_e0) {
        try {
            final byte[] n = psc_e0.n();
            int d = this.d;
            int i = 0;
            for (int j = 0; j < n.length; ++j) {
                i = (i << 8) + (n[j] & 0xFF);
            }
            while (i > 0) {
                final int n2 = d % i;
                d = i;
                i = n2;
            }
            return d == 1;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
    }
    
    protected void f() {
        super.f();
    }
    
    public void y() {
        super.y();
        if (this.j != null) {
            this.j.r();
        }
    }
    
    protected void g() {
        this.f();
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.h = 1;
        this.i = false;
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
