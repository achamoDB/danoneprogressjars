import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_i1 extends psc_an implements psc_ir, psc_ep, psc_i2
{
    protected Class a;
    protected SecureRandom b;
    protected byte[] c;
    protected String d;
    protected int e;
    protected int f;
    protected int g;
    protected int h;
    protected psc_e0[] i;
    protected psc_e0 j;
    protected psc_e0 k;
    protected psc_e0 l;
    
    public psc_i1() {
        this.e = -1;
        this.f = 0;
        this.g = 2;
    }
    
    public psc_i1(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String d() {
        return "RSA";
    }
    
    public String e() {
        return "PKCS1Block02Pad";
    }
    
    public String m() {
        return "PKCS1Block01Pad";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] a(final psc_iv psc_iv) throws psc_ao {
        return psc_nm.a(this, psc_iv);
    }
    
    public byte[] a(final psc_az psc_az, final psc_et psc_et, final String s, final boolean b) throws psc_ao {
        return psc_nm.a(this, psc_az, psc_et, s, b);
    }
    
    public int f() {
        return this.e;
    }
    
    public int g() {
        return this.e;
    }
    
    public int h() {
        return this.e;
    }
    
    public int i() {
        return this.e;
    }
    
    public int o() {
        return this.e;
    }
    
    public boolean n() {
        return false;
    }
    
    public int p() {
        return this.f;
    }
    
    public int b(final psc_iv psc_iv) {
        return psc_iv.a(this.e);
    }
    
    public int q() {
        return this.e;
    }
    
    public boolean a(final psc_az psc_az) {
        return true;
    }
    
    public boolean a(final psc_et psc_et) {
        return true;
    }
    
    protected void u() {
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
    
    public boolean b(final byte[] array, final int n) {
        psc_e0 psc_e0 = null;
        try {
            this.u();
            psc_e0 = this.a.newInstance();
            psc_e0.a(array, n, this.e);
            return psc_e0.b(this.i[0]) < 0;
        }
        catch (InstantiationException ex) {
            return false;
        }
        catch (IllegalAccessException ex2) {
            return false;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        finally {
            if (psc_e0 != null) {
                psc_e0.r();
            }
        }
    }
    
    public boolean r() {
        return false;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int g) {
        if (g >= 0 && g <= 2) {
            this.g = g;
        }
    }
    
    public int j() {
        if (this.i == null) {
            return 0;
        }
        if (this.i.length <= 2) {
            return 0;
        }
        return this.g;
    }
    
    public byte[] a(final psc_dc psc_dc, final boolean b, final psc_iv psc_iv) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_iv psc_iv, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public String a(final psc_al psc_al, final psc_iv psc_iv, final SecureRandom secureRandom, final psc_nf[] array) {
        return this.a(psc_al, null, psc_iv, secureRandom, array, false);
    }
    
    public String a(final psc_dt psc_dt, final psc_iv psc_iv, final SecureRandom secureRandom, final psc_nf[] array) {
        return this.a(psc_dt, null, psc_iv, secureRandom, array, false);
    }
    
    protected String a(final psc_al psc_al, final psc_az psc_az, final psc_dj psc_dj, final SecureRandom b, final psc_nf[] array, final boolean b2) {
        if (b != null) {
            this.b = b;
        }
        final byte[][] array2 = null;
        final byte[][] a = psc_k7.a(psc_al, "RSAPublicKey");
        if (a == null || a.length < 2) {
            return "Key not set to RSA public.";
        }
        this.u();
        this.v();
        try {
            this.i = new psc_e0[2];
            for (int i = 0; i < 2; ++i) {
                (this.i[i] = this.a.newInstance()).a(a[i], 0, a[i].length);
            }
        }
        catch (psc_ap psc_ap) {
            return "Improper RSA key.";
        }
        catch (InstantiationException ex) {
            return "Invalid arithmetic class.";
        }
        catch (IllegalAccessException ex2) {
            return "Invalid arithmetic class.";
        }
        this.e = a[0].length;
        this.h = -2;
        return null;
    }
    
    public String b(final psc_dt psc_dt, final psc_iv psc_iv, final SecureRandom secureRandom, final psc_nf[] array) {
        return this.a(psc_dt, null, psc_iv, secureRandom, array, false);
    }
    
    public String b(final psc_al psc_al, final psc_iv psc_iv, final SecureRandom secureRandom, final psc_nf[] array) {
        return this.a(psc_al, null, psc_iv, secureRandom, array, false);
    }
    
    protected String a(final psc_dt psc_dt, final psc_az psc_az, final psc_dj psc_dj, final SecureRandom b, final psc_nf[] array, final boolean b2) {
        if (b != null) {
            this.b = b;
        }
        byte[][] array2 = null;
        Label_0727: {
            try {
                this.v();
                this.u();
                if (this.g == 2) {
                    array2 = psc_k7.a(psc_dt, "RSAPrivateKeyBlind");
                    this.g = 0;
                    if (array2 != null && array2.length != 0) {
                        this.g = 2;
                        (this.k = this.a.newInstance()).a(array2[array2.length - 2], 0, array2[array2.length - 2].length);
                        (this.l = this.a.newInstance()).a(array2[array2.length - 1], 0, array2[array2.length - 1].length);
                    }
                }
                else {
                    array2 = psc_k7.a(psc_dt, "RSAMultiPrimePrivateKeyCRT");
                }
                int n = 0;
                if (array2 == null || array2.length == 0) {
                    array2 = psc_k7.a(psc_dt, "RSAPrivateKeyCRT");
                    n = 1;
                    if (array2 == null || array2.length == 0) {
                        this.g = 0;
                        array2 = psc_k7.a(psc_dt, "RSAPrivateKey");
                        n = 2;
                        if (array2 == null || array2.length == 0) {
                            final String s = "Key not set to RSA private.";
                            break Label_0727;
                        }
                    }
                }
                if (n == 0) {
                    this.h = (array2[3][0] & 0xFF);
                }
                if (n == 1) {
                    this.h = 2;
                }
                if (n != 2) {
                    (this.j = this.a.newInstance()).a(array2[1], 0, array2[1].length);
                    this.i = new psc_e0[this.h * 3];
                    int n2 = 4;
                    if (n == 1) {
                        n2 = 3;
                    }
                    int length = array2.length;
                    if (this.k != null) {
                        length -= 2;
                    }
                    int i = 0;
                    int n3 = 0;
                    while (i < length) {
                        if (i <= 0 || i >= n2) {
                            (this.i[n3] = this.a.newInstance()).a(array2[i], 0, array2[i].length);
                            ++n3;
                        }
                        ++i;
                    }
                }
                else {
                    this.i = new psc_e0[2];
                    for (int j = 0; j < 2; ++j) {
                        (this.i[j] = this.a.newInstance()).a(array2[j], 0, array2[j].length);
                    }
                    this.h = -1;
                }
                this.e = array2[0].length;
                break Label_0727;
            }
            catch (psc_ap psc_ap) {
                final String s2 = "Improper RSA key.";
                break Label_0727;
            }
            catch (InstantiationException ex) {
                final String s2 = "Invalid arithmetic class.";
                break Label_0727;
            }
            catch (IllegalAccessException ex2) {
                final String s2 = "Invalid arithmetic class.";
                break Label_0727;
            }
            finally {
                Label_0812: {
                    if (array2 != null) {
                        for (int k = 1; k < array2.length; ++k) {
                            if (array2[k] != null) {
                                this.d(array2[k]);
                            }
                        }
                        break Label_0812;
                    }
                    break Label_0812;
                }
                while (true) {}
                // iftrue(Label_0636:, array2 == null)
                // iftrue(Label_0718:, array2[n6] == null)
                // iftrue(Label_0768:, n4 >= array2.length)
                // iftrue(Label_0680:, array2 == null)
                // iftrue(Label_0768:, array2 == null)
                // iftrue(Label_0680:, n8 >= array2.length)
                // iftrue(Label_0592:, n5 >= array2.length)
                // iftrue(Label_0586:, array2[n5] == null)
                // iftrue(Label_0674:, array2[n8] == null)
                // iftrue(Label_0636:, n7 >= array2.length)
                // iftrue(Label_0762:, array2[n4] == null)
                // iftrue(Label_0724:, n6 >= array2.length)
                // iftrue(Label_0592:, array2 == null)
                // iftrue(Label_0630:, array2[n7] == null)
                // iftrue(Label_0724:, array2 == null)
                final String s2;
                while (true) {
                    final String s;
                    int n4 = 0;
                    int n5;
                    int n6;
                    int n7 = 0;
                    int n8 = 0;
                    Label_0605_Outer:Label_0737_Outer:Label_0649_Outer:
                    while (true) {
                    Block_27:
                        while (true) {
                            Label_0674: {
                            Label_0737:
                                while (true) {
                                    Label_0762: {
                                        while (true) {
                                            Block_31: {
                                                while (true) {
                                                Label_0605:
                                                    while (true) {
                                                        Label_0586_Outer:Label_0718_Outer:
                                                        while (true) {
                                                            this.d(array2[n4]);
                                                            break Label_0762;
                                                        Block_36:
                                                            while (true) {
                                                                Block_34: {
                                                                    Block_35: {
                                                                    Block_30:
                                                                        while (true) {
                                                                            Label_0561: {
                                                                                while (true) {
                                                                                    ++n5;
                                                                                    break Label_0561;
                                                                                    Block_26: {
                                                                                        break Block_26;
                                                                                        ++n6;
                                                                                        break Label_0605;
                                                                                        this.d(array2[n7]);
                                                                                        break Label_0586_Outer;
                                                                                    }
                                                                                    n7 = 1;
                                                                                    break Label_0605;
                                                                                    break Block_34;
                                                                                    this.d(array2[n5]);
                                                                                    continue Label_0718_Outer;
                                                                                }
                                                                                break Block_36;
                                                                                n5 = 1;
                                                                                break Label_0561;
                                                                                break Label_0737;
                                                                                break Block_35;
                                                                                break Block_30;
                                                                            }
                                                                            continue Label_0737_Outer;
                                                                        }
                                                                        break Block_31;
                                                                        break Block_27;
                                                                        Label_0592: {
                                                                            return s;
                                                                        }
                                                                    }
                                                                    n4 = 1;
                                                                    continue Label_0737;
                                                                    n6 = 1;
                                                                    break Label_0605;
                                                                }
                                                                this.d(array2[n6]);
                                                                continue Label_0605_Outer;
                                                            }
                                                            continue Label_0586_Outer;
                                                        }
                                                        ++n7;
                                                        continue Label_0605;
                                                    }
                                                    continue Label_0737_Outer;
                                                }
                                                Label_0768: {
                                                    return s2;
                                                }
                                            }
                                            this.d(array2[n8]);
                                            break Label_0674;
                                            continue Label_0649_Outer;
                                        }
                                        Label_0680: {
                                            return s2;
                                        }
                                    }
                                    ++n4;
                                    continue Label_0737;
                                }
                                n8 = 1;
                                continue;
                            }
                            ++n8;
                            continue;
                        }
                        continue Label_0737_Outer;
                    }
                    continue;
                }
                Label_0724: {
                    return s2;
                }
                Label_0636:
                return null;
            }
        }
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        Object j = null;
        Label_0343: {
            try {
                psc_e0 = this.a.newInstance();
                psc_e2 = this.a.newInstance();
                psc_e0.a(array, n, this.e);
                psc_e0.c(this.i[1], this.i[0], psc_e2);
                j = psc_e2.j(this.e);
                if (j == null || j.length == 0) {
                    final int n3 = 0;
                    break Label_0343;
                }
                for (int i = 0; i < j.length; ++i) {
                    array2[i + n2] = j[i];
                }
                break Label_0343;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_0343;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_0343;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_0343;
            }
            finally {
                if (j != null) {
                    psc_au.c(j);
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                Label_0418: {
                    if (psc_e2 != null) {
                        psc_e2.r();
                        break Label_0418;
                    }
                    break Label_0418;
                }
                while (true) {}
                return this.e;
                // iftrue(Label_0379:, psc_e2 == null)
                // iftrue(Label_0340:, psc_e2 == null)
                // iftrue(Label_0355:, j == null)
                // iftrue(Label_0238:, j == null)
                // iftrue(Label_0328:, psc_e0 == null)
                // iftrue(Label_0277:, j == null)
                // iftrue(Label_0301:, psc_e2 == null)
                // iftrue(Label_0367:, psc_e0 == null)
                // iftrue(Label_0211:, psc_e0 == null)
                // iftrue(Label_0223:, psc_e2 == null)
                // iftrue(Label_0250:, psc_e0 == null)
                // iftrue(Label_0262:, psc_e2 == null)
                // iftrue(Label_0199:, j == null)
                // iftrue(Label_0289:, psc_e0 == null)
                // iftrue(Label_0316:, j == null)
                final boolean b;
                while (true) {
                    final int n3;
                    Label_0289_Outer:Block_16_Outer:
                    while (true) {
                        Block_17: {
                            while (true) {
                                Label_0277: {
                                    Block_23: {
                                        while (true) {
                                            Block_15: {
                                                Block_22_Outer:Block_11_Outer:Block_14_Outer:Block_9_Outer:
                                                while (true) {
                                                    Block_19: {
                                                        while (true) {
                                                            while (true) {
                                                                Block_21: {
                                                                    while (true) {
                                                                        Block_20: {
                                                                            while (true) {
                                                                                Block_10: {
                                                                                Label_0238:
                                                                                    while (true) {
                                                                                        while (true) {
                                                                                            Block_12: {
                                                                                                while (true) {
                                                                                                    break Block_23;
                                                                                                    break Block_20;
                                                                                                    break Block_21;
                                                                                                    break Block_12;
                                                                                                    psc_e0.r();
                                                                                                    continue Block_22_Outer;
                                                                                                }
                                                                                                break Block_19;
                                                                                                psc_e2.r();
                                                                                                return n3;
                                                                                                break Block_15;
                                                                                            }
                                                                                            psc_au.c(j);
                                                                                            break Label_0238;
                                                                                            Label_0301: {
                                                                                                return b ? 1 : 0;
                                                                                            }
                                                                                            psc_e2.r();
                                                                                            return this.e;
                                                                                            break Block_17;
                                                                                            continue Block_11_Outer;
                                                                                        }
                                                                                        psc_au.c(j);
                                                                                        Label_0199: {
                                                                                            break Block_10;
                                                                                        }
                                                                                        Label_0262:
                                                                                        return this.e;
                                                                                        continue Block_14_Outer;
                                                                                    }
                                                                                    break Label_0289_Outer;
                                                                                }
                                                                                psc_e0.r();
                                                                                continue Block_16_Outer;
                                                                            }
                                                                        }
                                                                        psc_e2.r();
                                                                        return b ? 1 : 0;
                                                                        continue Label_0289_Outer;
                                                                    }
                                                                }
                                                                psc_au.c(j);
                                                                continue Block_9_Outer;
                                                            }
                                                            continue Block_16_Outer;
                                                        }
                                                    }
                                                    psc_e0.r();
                                                    continue Label_0289_Outer;
                                                }
                                                Label_0379: {
                                                    return b ? 1 : 0;
                                                }
                                            }
                                            psc_au.c(j);
                                            break Label_0277;
                                            Label_0223: {
                                                return n3;
                                            }
                                            psc_e0.r();
                                            continue Block_16_Outer;
                                        }
                                    }
                                    psc_e2.r();
                                    return b ? 1 : 0;
                                }
                                continue;
                            }
                        }
                        psc_e2.r();
                        return b ? 1 : 0;
                        psc_au.c(j);
                        continue;
                    }
                    psc_e0.r();
                    continue;
                }
                Label_0340: {
                    return b ? 1 : 0;
                }
            }
        }
    }
    
    public int b(final byte[] array, final int n, final byte[] array2, final int n2) {
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        Object j = null;
        Label_0842: {
            try {
                psc_e2 = this.a.newInstance();
                psc_e0 = this.a.newInstance();
                psc_e2.a(array, n, this.e);
                if (this.g == 2 && (this.l == null || this.k == null)) {
                    this.g = 1;
                }
                if (this.g == 1) {
                    psc_e3 = this.a.newInstance();
                    this.l = this.a.newInstance();
                    this.k = this.a.newInstance();
                    if (!a(this.i[0], this.i[1], this.i[2], this.j, array, n, this.e, this.k, this.l, psc_e3)) {
                        this.g = 0;
                        this.l = null;
                        this.k = null;
                    }
                }
                if (this.h == -1) {
                    psc_e2.c(this.i[1], this.i[0], psc_e0);
                }
                else if (this.g == 0) {
                    psc_e2.a(this.h, this.i, psc_e0);
                }
                else {
                    if (psc_e3 == null) {
                        psc_e3 = this.a.newInstance();
                    }
                    psc_e2.a(this.k, this.i[0], psc_e3);
                    psc_e3.a(this.h, this.i, psc_e2);
                    psc_e2.a(this.l, this.i[0], psc_e0);
                }
                j = psc_e0.j(this.e);
                if (j == null || j.length == 0) {
                    final int n3 = 0;
                    break Label_0842;
                }
                for (int i = 0; i < j.length; ++i) {
                    array2[i + n2] = j[i];
                }
                break Label_0842;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_0842;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_0842;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_0842;
            }
            finally {
                if (j != null) {
                    psc_au.c(j);
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (this.g != 2) {
                    if (this.k != null) {
                        this.k.r();
                    }
                    if (this.l != null) {
                        this.l.r();
                    }
                    this.k = null;
                    this.l = null;
                }
                Label_1041: {
                    if (psc_e3 != null) {
                        psc_e3.r();
                        break Label_1041;
                    }
                    break Label_1041;
                }
                while (true) {}
                // iftrue(Label_0866:, psc_e0 == null)
                // iftrue(Label_0563:, psc_e0 == null)
                // iftrue(Label_0738:, psc_e3 == null)
                // iftrue(Label_0726:, this.g == 2)
                // iftrue(Label_0854:, j == null)
                // iftrue(Label_0637:, psc_e3 == null)
                // iftrue(Label_0524:, this.g == 2)
                // iftrue(Label_0599:, this.k == null)
                // iftrue(Label_0716:, this.l == null)
                // iftrue(Label_0575:, psc_e2 == null)
                // iftrue(Label_0839:, psc_e3 == null)
                // iftrue(Label_0498:, this.k == null)
                // iftrue(Label_0940:, psc_e3 == null)
                // iftrue(Label_0615:, this.l == null)
                // iftrue(Label_0450:, j == null)
                // iftrue(Label_0462:, psc_e0 == null)
                // iftrue(Label_0928:, this.g == 2)
                // iftrue(Label_0753:, j == null)
                // iftrue(Label_0625:, this.g == 2)
                // iftrue(Label_0551:, j == null)
                // iftrue(Label_0827:, this.g == 2)
                // iftrue(Label_0817:, this.l == null)
                // iftrue(Label_0536:, psc_e3 == null)
                // iftrue(Label_0700:, this.k == null)
                // iftrue(Label_0474:, psc_e2 == null)
                // iftrue(Label_0765:, psc_e0 == null)
                // iftrue(Label_0902:, this.k == null)
                // iftrue(Label_0514:, this.l == null)
                // iftrue(Label_0664:, psc_e0 == null)
                // iftrue(Label_0878:, psc_e2 == null)
                // iftrue(Label_0652:, j == null)
                // iftrue(Label_0777:, psc_e2 == null)
                // iftrue(Label_0676:, psc_e2 == null)
                // iftrue(Label_0801:, this.k == null)
                // iftrue(Label_0918:, this.l == null)
                while (true) {
                Block_49:
                    while (true) {
                        Block_34: {
                            final int n3;
                            final boolean b;
                            Block_18_Outer:Label_0625_Outer:Label_0563_Outer:
                            while (true) {
                                Label_0716_Outer:Label_0777_Outer:
                                while (true) {
                                    Block_24: {
                                    Block_41_Outer:
                                        while (true) {
                                        Block_39:
                                            while (true) {
                                            Block_40:
                                                while (true) {
                                                Block_30_Outer:
                                                    while (true) {
                                                        Label_0664: {
                                                            Label_0765: {
                                                                while (true) {
                                                                Label_0575_Outer:
                                                                    while (true) {
                                                                    Block_46_Outer:
                                                                        while (true) {
                                                                            Block_25: {
                                                                            Block_48:
                                                                                while (true) {
                                                                                    Label_0866: {
                                                                                        Block_36: {
                                                                                            while (true) {
                                                                                                Label_0652: {
                                                                                                    while (true) {
                                                                                                        Label_0498: {
                                                                                                        Block_47:
                                                                                                            while (true) {
                                                                                                                Label_0753: {
                                                                                                                    while (true) {
                                                                                                                    Block_22_Outer:
                                                                                                                        while (true) {
                                                                                                                            Label_0462: {
                                                                                                                                Block_33: {
                                                                                                                                    while (true) {
                                                                                                                                        Label_0524: {
                                                                                                                                            while (true) {
                                                                                                                                            Block_26_Outer:
                                                                                                                                                while (true) {
                                                                                                                                                Block_37_Outer:
                                                                                                                                                    while (true) {
                                                                                                                                                        while (true) {
                                                                                                                                                            Block_45: {
                                                                                                                                                                while (true) {
                                                                                                                                                                    Block_27: {
                                                                                                                                                                    Label_0928_Outer:
                                                                                                                                                                        while (true) {
                                                                                                                                                                            Label_0450: {
                                                                                                                                                                                while (true) {
                                                                                                                                                                                    Label_0854_Outer:Label_0726_Outer:
                                                                                                                                                                                    while (true) {
                                                                                                                                                                                        this.k = null;
                                                                                                                                                                                        this.l = null;
                                                                                                                                                                                        break Label_0524;
                                                                                                                                                                                        while (true) {
                                                                                                                                                                                        Block_50_Outer:
                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                        while (true) {
                                                                                                                                                                                                            Block_19: {
                                                                                                                                                                                                                while (true) {
                                                                                                                                                                                                                    break Block_45;
                                                                                                                                                                                                                    break Block_24;
                                                                                                                                                                                                                    break Block_36;
                                                                                                                                                                                                                    psc_e2.r();
                                                                                                                                                                                                                Block_44:
                                                                                                                                                                                                                    while (true) {
                                                                                                                                                                                                                        Label_0827: {
                                                                                                                                                                                                                            while (true) {
                                                                                                                                                                                                                                Label_0474: {
                                                                                                                                                                                                                                    break Label_0474;
                                                                                                                                                                                                                                    this.k = null;
                                                                                                                                                                                                                                    this.l = null;
                                                                                                                                                                                                                                    break Label_0827;
                                                                                                                                                                                                                                    psc_e3.r();
                                                                                                                                                                                                                                    return b ? 1 : 0;
                                                                                                                                                                                                                                    break Block_33;
                                                                                                                                                                                                                                    psc_e3.r();
                                                                                                                                                                                                                                    return b ? 1 : 0;
                                                                                                                                                                                                                                    break Block_44;
                                                                                                                                                                                                                                    break Block_50_Outer;
                                                                                                                                                                                                                                    psc_e0.r();
                                                                                                                                                                                                                                    break Label_0765;
                                                                                                                                                                                                                                    psc_au.c(j);
                                                                                                                                                                                                                                    break Label_0450;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                break Block_19;
                                                                                                                                                                                                                                break Block_27;
                                                                                                                                                                                                                                psc_e0.r();
                                                                                                                                                                                                                                break Label_0462;
                                                                                                                                                                                                                                break Block_30_Outer;
                                                                                                                                                                                                                                psc_au.c(j);
                                                                                                                                                                                                                                break Label_0753;
                                                                                                                                                                                                                                break Block_25;
                                                                                                                                                                                                                                psc_e2.r();
                                                                                                                                                                                                                                continue Block_50_Outer;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            Label_0839: {
                                                                                                                                                                                                                                return b ? 1 : 0;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        continue Block_50_Outer;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    psc_au.c(j);
                                                                                                                                                                                                                    continue Label_0726_Outer;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                Label_0940: {
                                                                                                                                                                                                                    return b ? 1 : 0;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                this.k.r();
                                                                                                                                                                                                                break Label_0498;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            continue Block_30_Outer;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        psc_au.c(j);
                                                                                                                                                                                                        break Label_0652;
                                                                                                                                                                                                        this.l.r();
                                                                                                                                                                                                        break Block_46_Outer;
                                                                                                                                                                                                        Label_0637: {
                                                                                                                                                                                                            return this.e;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        continue Label_0625_Outer;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    psc_e3.r();
                                                                                                                                                                                                    return n3;
                                                                                                                                                                                                    continue Label_0928_Outer;
                                                                                                                                                                                                }
                                                                                                                                                                                                this.k = null;
                                                                                                                                                                                                this.l = null;
                                                                                                                                                                                                continue Block_18_Outer;
                                                                                                                                                                                            }
                                                                                                                                                                                            psc_e3.r();
                                                                                                                                                                                            return this.e;
                                                                                                                                                                                            continue Block_26_Outer;
                                                                                                                                                                                        }
                                                                                                                                                                                        this.l.r();
                                                                                                                                                                                        continue Label_0854_Outer;
                                                                                                                                                                                    }
                                                                                                                                                                                    this.k = null;
                                                                                                                                                                                    this.l = null;
                                                                                                                                                                                    continue Block_22_Outer;
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            continue Block_37_Outer;
                                                                                                                                                                        }
                                                                                                                                                                        break Block_47;
                                                                                                                                                                    }
                                                                                                                                                                    this.k.r();
                                                                                                                                                                    continue Label_0716_Outer;
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            psc_e0.r();
                                                                                                                                                            break Label_0866;
                                                                                                                                                            continue Label_0563_Outer;
                                                                                                                                                        }
                                                                                                                                                        continue Block_37_Outer;
                                                                                                                                                    }
                                                                                                                                                    break Label_0716_Outer;
                                                                                                                                                    break Block_40;
                                                                                                                                                    this.l.r();
                                                                                                                                                    continue Label_0625_Outer;
                                                                                                                                                }
                                                                                                                                                this.k.r();
                                                                                                                                                continue Block_41_Outer;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        continue Label_0716_Outer;
                                                                                                                                    }
                                                                                                                                    psc_e0.r();
                                                                                                                                    break Label_0664;
                                                                                                                                }
                                                                                                                                break Block_34;
                                                                                                                                Label_0536: {
                                                                                                                                    return n3;
                                                                                                                                }
                                                                                                                            }
                                                                                                                            continue Label_0625_Outer;
                                                                                                                        }
                                                                                                                        psc_e2.r();
                                                                                                                        continue Label_0575_Outer;
                                                                                                                    }
                                                                                                                }
                                                                                                                continue Label_0563_Outer;
                                                                                                            }
                                                                                                            break Block_48;
                                                                                                        }
                                                                                                        continue Label_0575_Outer;
                                                                                                    }
                                                                                                }
                                                                                                continue Block_46_Outer;
                                                                                            }
                                                                                        }
                                                                                        psc_e3.r();
                                                                                        return b ? 1 : 0;
                                                                                    }
                                                                                    continue;
                                                                                }
                                                                                this.k.r();
                                                                                break Block_18_Outer;
                                                                            }
                                                                            psc_e2.r();
                                                                            continue Label_0777_Outer;
                                                                        }
                                                                        this.k = null;
                                                                        this.l = null;
                                                                        continue Label_0563_Outer;
                                                                    }
                                                                    continue Label_0716_Outer;
                                                                }
                                                            }
                                                            break Block_39;
                                                        }
                                                        continue Label_0716_Outer;
                                                    }
                                                    this.l.r();
                                                    continue Label_0777_Outer;
                                                }
                                                continue;
                                            }
                                            psc_e2.r();
                                            continue Block_41_Outer;
                                        }
                                        Label_0738: {
                                            return b ? 1 : 0;
                                        }
                                    }
                                    psc_e0.r();
                                    continue;
                                }
                                psc_au.c(j);
                                continue Label_0563_Outer;
                            }
                            break Block_49;
                        }
                        this.k.r();
                        continue;
                    }
                    this.l.r();
                    continue;
                }
            }
        }
    }
    
    static boolean a(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4, final byte[] array5, final int n, final int n2, final byte[] array6, final byte[] array7) {
        psc_ez psc_ez = null;
        psc_ez psc_ez2 = null;
        psc_ez psc_ez3 = null;
        psc_ez psc_ez4 = null;
        psc_ez psc_ez5 = null;
        psc_ez psc_ez6 = null;
        psc_e0 psc_e0 = null;
        Object j = null;
        Object i = null;
        Label_0429: {
            try {
                psc_ez = new psc_ez();
                psc_ez.a(array, 0, array.length);
                psc_ez2 = new psc_ez();
                psc_ez2.a(array2, 0, array2.length);
                psc_ez3 = new psc_ez();
                psc_ez3.a(array3, 0, array3.length);
                psc_ez4 = new psc_ez();
                psc_ez4.a(array4, 0, array4.length);
                psc_ez5 = new psc_ez();
                psc_ez6 = new psc_ez();
                psc_e0 = new psc_ez();
                if (!a(psc_ez, psc_ez2, psc_ez3, psc_ez4, array5, n, n2, psc_ez5, psc_ez6, psc_e0)) {
                    final boolean b = false;
                    break Label_0429;
                }
                j = psc_ez5.j(array.length);
                System.arraycopy(j, 0, array6, 0, j.length);
                i = psc_ez6.j(array.length);
                System.arraycopy(i, 0, array7, 0, i.length);
                final boolean b2 = true;
                break Label_0429;
            }
            catch (Exception ex) {
                final boolean b3 = false;
                break Label_0429;
            }
            finally {
                if (psc_ez != null) {
                    psc_ez.r();
                }
                if (psc_ez2 != null) {
                    psc_ez2.r();
                }
                if (psc_ez3 != null) {
                    psc_ez3.r();
                }
                if (psc_ez4 != null) {
                    psc_ez4.r();
                }
                if (psc_ez5 != null) {
                    psc_ez5.r();
                }
                if (psc_ez6 != null) {
                    psc_ez6.r();
                }
                if (psc_e0 != null) {
                    ((psc_ez)psc_e0).r();
                }
                if (j != null) {
                    psc_au.c(j);
                }
                Label_0616: {
                    if (i != null) {
                        psc_au.c(i);
                        break Label_0616;
                    }
                    break Label_0616;
                }
                while (true) {}
                // iftrue(Label_0441:, psc_ez == null)
                // iftrue(Label_0301:, psc_ez6 == null)
                // iftrue(Label_0281:, psc_ez4 == null)
                // iftrue(Label_0481:, psc_ez5 == null)
                // iftrue(Label_0321:, j == null)
                // iftrue(Label_0291:, psc_ez5 == null)
                // iftrue(Label_0491:, psc_ez6 == null)
                // iftrue(Label_0261:, psc_ez2 == null)
                // iftrue(Label_0416:, j == null)
                // iftrue(Label_0461:, psc_ez3 == null)
                // iftrue(Label_0451:, psc_ez2 == null)
                // iftrue(Label_0501:, psc_e0 == null)
                // iftrue(Label_0396:, psc_ez6 == null)
                // iftrue(Label_0331:, i == null)
                // iftrue(Label_0386:, psc_ez5 == null)
                // iftrue(Label_0346:, psc_ez == null)
                // iftrue(Label_0426:, i == null)
                // iftrue(Label_0356:, psc_ez2 == null)
                // iftrue(Label_0251:, psc_ez == null)
                // iftrue(Label_0406:, psc_e0 == null)
                // iftrue(Label_0511:, j == null)
                // iftrue(Label_0271:, psc_ez3 == null)
                // iftrue(Label_0311:, psc_e0 == null)
                // iftrue(Label_0366:, psc_ez3 == null)
                // iftrue(Label_0471:, psc_ez4 == null)
                // iftrue(Label_0521:, i == null)
                // iftrue(Label_0376:, psc_ez4 == null)
                final boolean b;
                final boolean b2;
                final boolean b3;
                Block_32_Outer:Block_18_Outer:
                while (true) {
                    Block_28: {
                        while (true) {
                        Label_0366:
                            while (true) {
                            Label_0271_Outer:
                                while (true) {
                                    Label_0461:Label_0386_Outer:
                                    while (true) {
                                    Label_0321_Outer:
                                        while (true) {
                                        Block_19:
                                            while (true) {
                                            Label_0376_Outer:
                                                while (true) {
                                                Label_0416_Outer:
                                                    while (true) {
                                                    Label_0261_Outer:
                                                        while (true) {
                                                            Block_22: {
                                                                while (true) {
                                                                    Block_7:Block_31_Outer:
                                                                    while (true) {
                                                                        Label_0301: {
                                                                            Block_16: {
                                                                                while (true) {
                                                                                    Label_0501: {
                                                                                        while (true) {
                                                                                        Label_0406_Outer:
                                                                                            while (true) {
                                                                                                while (true) {
                                                                                                Block_15_Outer:
                                                                                                    while (true) {
                                                                                                        Block_9: {
                                                                                                            Block_11: {
                                                                                                                Block_23: {
                                                                                                                    Block_20: {
                                                                                                                        while (true) {
                                                                                                                            while (true) {
                                                                                                                                while (true) {
                                                                                                                                    Block_29: {
                                                                                                                                        while (true) {
                                                                                                                                            while (true) {
                                                                                                                                                Label_0441: {
                                                                                                                                                    while (true) {
                                                                                                                                                        Label_0451: {
                                                                                                                                                            while (true) {
                                                                                                                                                                Block_24: {
                                                                                                                                                                    break Block_24;
                                                                                                                                                                    psc_ez2.r();
                                                                                                                                                                    break Label_0451;
                                                                                                                                                                    ((psc_ez)psc_e0).r();
                                                                                                                                                                    while (true) {
                                                                                                                                                                        Label_0311: {
                                                                                                                                                                            break Label_0311;
                                                                                                                                                                            psc_ez4.r();
                                                                                                                                                                            Label_0471: {
                                                                                                                                                                                break Label_0471;
                                                                                                                                                                                break Block_11;
                                                                                                                                                                                break Block_9;
                                                                                                                                                                            }
                                                                                                                                                                            break Block_28;
                                                                                                                                                                        }
                                                                                                                                                                        break Label_0376_Outer;
                                                                                                                                                                        Block_10: {
                                                                                                                                                                            break Block_10;
                                                                                                                                                                            ((psc_ez)psc_e0).r();
                                                                                                                                                                            break Label_0501;
                                                                                                                                                                            psc_ez3.r();
                                                                                                                                                                            break Label_0461;
                                                                                                                                                                            break Block_29;
                                                                                                                                                                            break Block_7;
                                                                                                                                                                        }
                                                                                                                                                                        psc_ez5.r();
                                                                                                                                                                        continue Label_0271_Outer;
                                                                                                                                                                    }
                                                                                                                                                                    psc_ez.r();
                                                                                                                                                                    break Block_15_Outer;
                                                                                                                                                                }
                                                                                                                                                                psc_ez.r();
                                                                                                                                                                break Label_0441;
                                                                                                                                                                psc_au.c(j);
                                                                                                                                                                break Label_0271_Outer;
                                                                                                                                                                psc_ez.r();
                                                                                                                                                                continue Block_15_Outer;
                                                                                                                                                            }
                                                                                                                                                            psc_au.c(i);
                                                                                                                                                            return b;
                                                                                                                                                            break Block_22;
                                                                                                                                                        }
                                                                                                                                                        continue Block_15_Outer;
                                                                                                                                                    }
                                                                                                                                                    psc_au.c(i);
                                                                                                                                                    return b3;
                                                                                                                                                    Label_0331: {
                                                                                                                                                        return b;
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                continue Label_0271_Outer;
                                                                                                                                            }
                                                                                                                                            continue Block_15_Outer;
                                                                                                                                        }
                                                                                                                                        break Block_20;
                                                                                                                                    }
                                                                                                                                    psc_ez6.r();
                                                                                                                                    continue Label_0386_Outer;
                                                                                                                                }
                                                                                                                                continue Label_0406_Outer;
                                                                                                                            }
                                                                                                                            psc_ez3.r();
                                                                                                                            break Label_0366;
                                                                                                                            break Block_19;
                                                                                                                            continue Block_31_Outer;
                                                                                                                        }
                                                                                                                        break Block_23;
                                                                                                                    }
                                                                                                                    psc_ez6.r();
                                                                                                                    break Label_0406_Outer;
                                                                                                                }
                                                                                                                psc_au.c(i);
                                                                                                                return b2;
                                                                                                            }
                                                                                                            psc_ez6.r();
                                                                                                            break Label_0301;
                                                                                                        }
                                                                                                        psc_ez4.r();
                                                                                                        continue Block_31_Outer;
                                                                                                    }
                                                                                                    break Block_16;
                                                                                                    ((psc_ez)psc_e0).r();
                                                                                                    continue Block_32_Outer;
                                                                                                }
                                                                                                Label_0521: {
                                                                                                    return b3;
                                                                                                }
                                                                                                continue Label_0406_Outer;
                                                                                            }
                                                                                            continue Label_0261_Outer;
                                                                                        }
                                                                                    }
                                                                                    continue Block_32_Outer;
                                                                                }
                                                                                Label_0426: {
                                                                                    return b2;
                                                                                }
                                                                            }
                                                                            psc_ez2.r();
                                                                            break Label_0416_Outer;
                                                                            break Label_0321_Outer;
                                                                        }
                                                                        continue Label_0271_Outer;
                                                                    }
                                                                    psc_ez2.r();
                                                                    continue Block_18_Outer;
                                                                }
                                                            }
                                                            psc_au.c(j);
                                                            continue Block_18_Outer;
                                                        }
                                                        psc_ez4.r();
                                                        continue Label_0416_Outer;
                                                    }
                                                    continue Label_0376_Outer;
                                                }
                                                psc_au.c(j);
                                                continue Block_18_Outer;
                                            }
                                            psc_ez5.r();
                                            continue Label_0321_Outer;
                                        }
                                        psc_ez3.r();
                                        continue Block_32_Outer;
                                    }
                                    continue Label_0271_Outer;
                                }
                                continue Block_18_Outer;
                            }
                            continue;
                        }
                    }
                    psc_ez5.r();
                    continue;
                }
            }
        }
    }
    
    static boolean a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3, final psc_e0 psc_e4, final byte[] array, final int n, final int n2, final psc_e0 psc_e5, final psc_e0 psc_e6, final psc_e0 psc_e7) {
        boolean e = false;
        byte[] array2 = null;
        byte[] n3 = null;
        byte[] n4 = null;
        psc_ef psc_ef = null;
        psc_aw psc_aw = null;
        psc_ay psc_ay = null;
        Label_0509: {
            try {
                final int n5 = (psc_e0.o() + 7) / 8;
                if (n5 <= 0) {
                    final boolean b = false;
                    break Label_0509;
                }
                array2 = new byte[n5];
                n3 = psc_e2.n();
                n4 = psc_e3.n();
                psc_ef = new psc_ef();
                psc_ef.j();
                psc_ef.a(n4, 0, n4.length);
                if (array != null) {
                    psc_ef.a(array, n, n2);
                }
                psc_ef.b(array2, 0);
                psc_ef.j();
                psc_ef.a(n3, 0, n3.length);
                psc_ef.a(array2, 0, 16);
                psc_ef.b(array2, 0);
                psc_aw = new psc_aw();
                psc_ay = new psc_ay(psc_aw, false);
                psc_ay.g(array2);
                for (int i = 0; i < 5; ++i) {
                    psc_ay.b(array2, 0, array2.length);
                    array2[0] = 0;
                    psc_e7.a(array2, 0, array2.length);
                    e = psc_e7.e(psc_e0, psc_e6);
                    if (e) {
                        break;
                    }
                }
                if (!e) {
                    final boolean b2 = false;
                    break Label_0509;
                }
                psc_e7.c(psc_e4, psc_e0, psc_e5);
                final boolean b3 = true;
                break Label_0509;
            }
            catch (Exception ex) {
                final boolean b4 = false;
                break Label_0509;
            }
            finally {
                if (psc_ef != null) {
                    psc_ef.y();
                }
                if (psc_aw != null) {
                    psc_aw.y();
                }
                if (psc_ay != null) {
                    psc_ay.y();
                }
                if (array2 != null) {
                    psc_au.c(array2);
                }
                if (n3 != null) {
                    psc_au.c(n3);
                }
                Label_0636: {
                    if (n4 != null) {
                        psc_au.c(n4);
                        break Label_0636;
                    }
                    break Label_0636;
                }
                while (true) {}
                // iftrue(Label_0431:, n3 == null)
                // iftrue(Label_0456:, psc_ef == null)
                // iftrue(Label_0571:, n4 == null)
                // iftrue(Label_0421:, array2 == null)
                // iftrue(Label_0366:, n3 == null)
                // iftrue(Label_0551:, array2 == null)
                // iftrue(Label_0466:, psc_aw == null)
                // iftrue(Label_0521:, psc_ef == null)
                // iftrue(Label_0476:, psc_ay == null)
                // iftrue(Label_0541:, psc_ay == null)
                // iftrue(Label_0391:, psc_ef == null)
                // iftrue(Label_0376:, n4 == null)
                // iftrue(Label_0486:, array2 == null)
                // iftrue(Label_0346:, psc_ay == null)
                // iftrue(Label_0326:, psc_ef == null)
                // iftrue(Label_0441:, n4 == null)
                // iftrue(Label_0401:, psc_aw == null)
                // iftrue(Label_0496:, n3 == null)
                // iftrue(Label_0336:, psc_aw == null)
                // iftrue(Label_0561:, n3 == null)
                // iftrue(Label_0356:, array2 == null)
                // iftrue(Label_0506:, n4 == null)
                // iftrue(Label_0411:, psc_ay == null)
                // iftrue(Label_0531:, psc_aw == null)
                while (true) {
                    Block_16: {
                        while (true) {
                            final boolean b;
                            final boolean b2;
                            final boolean b3;
                            final boolean b4;
                            Label_0541_Outer:Block_29_Outer:
                            while (true) {
                                Block_18: {
                                    while (true) {
                                    Label_0456_Outer:
                                        while (true) {
                                        Label_0531_Outer:
                                            while (true) {
                                                Block_22: {
                                                    while (true) {
                                                        Label_0401: {
                                                            Block_32_Outer:Label_0326_Outer:
                                                            while (true) {
                                                                Label_0496: {
                                                                    while (true) {
                                                                        Block_10: {
                                                                            while (true) {
                                                                                Block_26_Outer:Block_11_Outer:
                                                                                while (true) {
                                                                                Label_0551:
                                                                                    while (true) {
                                                                                    Block_17_Outer:
                                                                                        while (true) {
                                                                                            Label_0486: {
                                                                                                while (true) {
                                                                                                    while (true) {
                                                                                                    Block_12_Outer:
                                                                                                        while (true) {
                                                                                                            while (true) {
                                                                                                            Label_0336:
                                                                                                                while (true) {
                                                                                                                    Block_28_Outer:Block_24_Outer:
                                                                                                                    while (true) {
                                                                                                                        psc_au.c(n4);
                                                                                                                        return b;
                                                                                                                        psc_au.c(array2);
                                                                                                                        break Label_0486;
                                                                                                                        Label_0366: {
                                                                                                                            Block_31: {
                                                                                                                                while (true) {
                                                                                                                                Block_23:
                                                                                                                                    while (true) {
                                                                                                                                    Label_0421_Outer:
                                                                                                                                        while (true) {
                                                                                                                                            psc_ef.y();
                                                                                                                                            break Label_0456_Outer;
                                                                                                                                            psc_au.c(n4);
                                                                                                                                            return b3;
                                                                                                                                            while (true) {
                                                                                                                                                while (true) {
                                                                                                                                                    break Label_0541_Outer;
                                                                                                                                                    psc_ay.y();
                                                                                                                                                    break Block_28_Outer;
                                                                                                                                                    psc_au.c(n3);
                                                                                                                                                    Label_0561: {
                                                                                                                                                        break Label_0561;
                                                                                                                                                        break Block_22;
                                                                                                                                                        psc_au.c(n3);
                                                                                                                                                        break Label_0496;
                                                                                                                                                    }
                                                                                                                                                    break Block_12_Outer;
                                                                                                                                                    psc_au.c(array2);
                                                                                                                                                    continue Block_24_Outer;
                                                                                                                                                }
                                                                                                                                                continue Block_11_Outer;
                                                                                                                                            }
                                                                                                                                            psc_aw.y();
                                                                                                                                            break Label_0336;
                                                                                                                                            break Label_0421_Outer;
                                                                                                                                            break Block_31;
                                                                                                                                            break Block_23;
                                                                                                                                            psc_au.c(n4);
                                                                                                                                            return b2;
                                                                                                                                            psc_aw.y();
                                                                                                                                            break Label_0401;
                                                                                                                                            continue Label_0421_Outer;
                                                                                                                                        }
                                                                                                                                        psc_au.c(n3);
                                                                                                                                        break Label_0366;
                                                                                                                                        continue Block_32_Outer;
                                                                                                                                    }
                                                                                                                                    psc_aw.y();
                                                                                                                                    continue Block_12_Outer;
                                                                                                                                }
                                                                                                                                psc_ay.y();
                                                                                                                                break Block_26_Outer;
                                                                                                                            }
                                                                                                                            psc_au.c(array2);
                                                                                                                            break Label_0551;
                                                                                                                            break Label_0531_Outer;
                                                                                                                            Label_0571: {
                                                                                                                                return b4;
                                                                                                                            }
                                                                                                                            break Block_16;
                                                                                                                        }
                                                                                                                        continue Block_28_Outer;
                                                                                                                    }
                                                                                                                    continue Block_32_Outer;
                                                                                                                }
                                                                                                                continue Label_0531_Outer;
                                                                                                            }
                                                                                                            break Block_10;
                                                                                                            continue Block_17_Outer;
                                                                                                        }
                                                                                                        psc_au.c(n4);
                                                                                                        return b4;
                                                                                                        psc_au.c(array2);
                                                                                                        continue Label_0541_Outer;
                                                                                                    }
                                                                                                    continue Label_0531_Outer;
                                                                                                }
                                                                                            }
                                                                                            continue Block_11_Outer;
                                                                                        }
                                                                                        continue Label_0541_Outer;
                                                                                    }
                                                                                    continue Block_26_Outer;
                                                                                }
                                                                                continue Label_0326_Outer;
                                                                            }
                                                                            Label_0441: {
                                                                                return b2;
                                                                            }
                                                                        }
                                                                        psc_ef.y();
                                                                        continue Block_29_Outer;
                                                                    }
                                                                    Label_0506: {
                                                                        return b3;
                                                                    }
                                                                }
                                                                continue Block_32_Outer;
                                                            }
                                                            Label_0376: {
                                                                return b;
                                                            }
                                                        }
                                                        break Block_18;
                                                        psc_aw.y();
                                                        continue Block_29_Outer;
                                                    }
                                                }
                                                psc_ef.y();
                                                continue Block_29_Outer;
                                            }
                                            psc_ay.y();
                                            continue Label_0456_Outer;
                                        }
                                        continue;
                                    }
                                }
                                psc_ay.y();
                                continue;
                            }
                            psc_au.c(n3);
                            continue;
                        }
                    }
                    psc_ef.y();
                    continue;
                }
            }
        }
    }
    
    public String a(final psc_el psc_el) {
        if (psc_el != null) {
            return "RSA signatures expect no system parameters.";
        }
        return null;
    }
    
    public String a(final psc_dt psc_dt, final psc_az psc_az, final psc_et psc_et, final SecureRandom secureRandom, final psc_nf[] array) {
        final String a = this.a(psc_dt, psc_az, psc_et, secureRandom, array, true);
        if (a != null) {
            return a;
        }
        if (psc_az.e().compareTo("NoDigest") == 0) {
            this.f = psc_et.a(this.e);
        }
        else {
            this.f = -1;
            if (psc_et.a(psc_az.i(), this.e) == -1) {
                return "Key too small for RSA signatures.";
            }
        }
        return null;
    }
    
    public void s() throws psc_en {
    }
    
    public String a(final psc_al psc_al, final psc_az psc_az, final psc_et psc_et, final SecureRandom secureRandom, final psc_nf[] array) {
        final String a = this.a(psc_al, psc_az, psc_et, secureRandom, array, true);
        if (a != null) {
            return a;
        }
        if (psc_az.e().compareTo("NoDigest") == 0) {
            this.f = psc_et.a(this.e);
        }
        else {
            this.f = -1;
            if (psc_et.a(psc_az.i(), this.e) == -1) {
                return "Key too small for RSA signatures.";
            }
        }
        return null;
    }
    
    public void t() throws psc_en {
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        throw new psc_en("Improper call to signUpdate.");
    }
    
    public int a(final byte[] array, final int n, final int n2, final psc_az psc_az, final psc_et psc_et, final byte[] array2, final int n3) {
        return this.b(array, n, array2, n3);
    }
    
    public void b(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        throw new psc_en("Improper call to verifyUpdate.");
    }
    
    public boolean a(final byte[] array, final int n, int a, final psc_az psc_az, final psc_et psc_et, final byte[] array2, final int n2, final int n3) {
        if (n3 != this.e) {
            return false;
        }
        final byte[] array3 = new byte[this.e];
        this.a(array2, n2, array3, 0);
        int a2;
        try {
            a2 = psc_et.a(array3, 0, this.e, psc_az);
        }
        catch (psc_gx psc_gx) {
            return false;
        }
        try {
            a = psc_et.a(array3, this.e, array, a, psc_az);
        }
        catch (psc_gx psc_gx2) {
            return false;
        }
        if (a2 != a) {
            return false;
        }
        for (int i = 0; i < a; ++i) {
            if (array3[i] != array[n + i]) {
                return false;
            }
        }
        return true;
    }
    
    public void k() {
        if (this.i == null || this.i.length < 2 || this.h == -2) {
            return;
        }
        for (int i = 1; i < this.i.length; ++i) {
            if (this.i[i] != null) {
                this.i[i].p();
            }
        }
    }
    
    public void l() {
        if (this.i == null || this.i.length < 2 || this.h == -2) {
            return;
        }
        for (int i = 1; i < this.i.length; ++i) {
            if (this.i[i] != null) {
                this.i[i].q();
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
        if (this.b == null) {
            return null;
        }
        if (!(this.b instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.b;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.d = psc_av.getAlgorithm();
        this.c = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.b;
        this.b = null;
        return psc_av2;
    }
    
    private void a(final psc_av b) {
        this.k();
        if (this.c == null) {
            return;
        }
        for (int i = 0; i < this.c.length; ++i) {
            this.c[i] = 0;
        }
        this.c = null;
        this.d = null;
        this.b = b;
    }
    
    private void x() {
        this.k();
        if (this.c == null) {
            return;
        }
        this.b = psc_av.a(this.d, this.c);
        for (int i = 0; i < this.c.length; ++i) {
            this.c[i] = 0;
        }
        this.c = null;
        this.d = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_i1 psc_i1 = new psc_i1();
        this.a(psc_i1);
        return psc_i1;
    }
    
    protected void a(final psc_i1 psc_i1) throws CloneNotSupportedException {
        psc_i1.a = this.a;
        psc_i1.b = this.b;
        psc_i1.d = this.d;
        psc_i1.e = this.e;
        psc_i1.f = this.f;
        psc_i1.h = this.h;
        psc_i1.g = this.g;
        if (this.j != null) {
            psc_i1.j = (psc_e0)this.j.clone();
        }
        if (this.k != null) {
            psc_i1.k = (psc_e0)this.k.clone();
        }
        if (this.l != null) {
            psc_i1.l = (psc_e0)this.l.clone();
        }
        if (this.i != null) {
            this.l();
            psc_i1.i = new psc_e0[this.i.length];
            for (int i = 0; i < this.i.length; ++i) {
                if (this.i[i] != null) {
                    psc_i1.i[i] = (psc_e0)this.i[i].clone();
                }
            }
            this.k();
            psc_i1.k();
        }
    }
    
    protected void v() {
        if (this.i != null) {
            for (int i = 0; i < this.i.length; ++i) {
                if (this.i[i] != null) {
                    this.i[i].r();
                    this.i[i] = null;
                }
            }
            this.i = null;
        }
        if (this.j != null) {
            this.j.r();
        }
        this.k = null;
        if (this.k != null) {
            this.k.r();
        }
        this.l = null;
        if (this.l != null) {
            this.l.r();
        }
        this.j = null;
        this.h = 0;
        this.e = -1;
    }
    
    public void y() {
        super.y();
        this.v();
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.f = 0;
        this.g = 2;
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
