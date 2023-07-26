import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jb extends psc_em implements psc_i2, Cloneable, Serializable
{
    protected Class a;
    protected int b;
    protected byte[][] c;
    protected SecureRandom d;
    protected byte[] e;
    protected String f;
    protected int g;
    protected static final int h = 1;
    protected static final int i = 2;
    
    public psc_jb() {
        this.g = 1;
    }
    
    public psc_jb(final psc_ju psc_ju, final psc_jr psc_jr) throws psc_bf {
        this.b(psc_ju, psc_jr);
        this.g = 1;
    }
    
    public String o() {
        return "DSA";
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
        return psc_al instanceof psc_ju && psc_dt instanceof psc_jr && super.a(psc_al, psc_dt);
    }
    
    protected boolean a(final psc_el psc_el, final int[] array, final SecureRandom d, final psc_nf[] array2) {
        psc_an.a();
        this.f();
        this.j();
        if (d != null) {
            this.d = d;
        }
        if (psc_el == null) {
            return false;
        }
        if (!(psc_el instanceof psc_jo)) {
            return false;
        }
        if (array != null && array.length != 0) {
            return false;
        }
        if (this.d == null) {
            return false;
        }
        try {
            this.c = psc_el.b("DSAParameters");
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        this.g = 2;
        return true;
    }
    
    protected void d() throws psc_en {
        psc_an.a();
        this.f();
        if (this.g != 2) {
            throw new psc_en("Cannot reInit, object not initialized.");
        }
    }
    
    void i() {
        if (psc_aq.k() == 1) {
            return;
        }
        if (psc_ar.a(this.p(), this.q())) {
            return;
        }
        throw new SecurityException("An internal FIPS 140-2 required pairwise consistency check failed");
    }
    
    protected void e() throws psc_en {
        if (this.g != 2) {
            throw new psc_en("Cannot generate, object not initialized.");
        }
        this.h();
        psc_e0 psc_e0 = null;
        psc_e0 psc_e2 = null;
        psc_e0 psc_e3 = null;
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        byte[] array = null;
        byte[] array2 = null;
        byte[] n = null;
        psc_al a = null;
        psc_dt a2 = null;
        psc_ev psc_ev = null;
        final byte[] bytes = new byte[20];
        byte[] n2;
        int n3;
        int n4;
        Block_17_Outer:Label_0611_Outer:Block_10_Outer:Label_0641_Outer:Label_0557_Outer:Label_0601_Outer:
        while (true) {
            try {
                psc_e0 = this.a.newInstance();
                psc_e0.a(this.c[0], 0, this.c[0].length);
                this.b = psc_e0.o();
                psc_e2 = this.a.newInstance();
                psc_e2.a(this.c[1], 0, this.c[1].length);
                psc_e3 = this.a.newInstance();
                psc_e3.a(this.c[2], 0, this.c[2].length);
                array = new byte[this.c[1].length * 2];
                array2 = new byte[this.c[1].length * 2];
                this.d.nextBytes(bytes);
                psc_ev = new psc_ev(bytes);
                psc_ev.a(true);
                psc_ev.nextBytes(array2);
                psc_ev.nextBytes(array);
                psc_ev.a(array2, 0, array, 0, array.length);
                psc_e4 = this.a.newInstance();
                psc_e5 = this.a.newInstance();
                psc_e5.i(this.b);
                psc_e5.a(array, 0, array.length);
                psc_e5.d(psc_e2, psc_e4);
                psc_e3.c(psc_e4, psc_e0, psc_e5);
                n = psc_e4.n();
                n2 = psc_e5.n();
                a = psc_al.a("DSA", this.m());
                a2 = psc_dt.a("DSA", this.m());
                a.a("DSAPublicKey", new byte[][] { this.c[0], this.c[1], this.c[2], n2 });
                a2.a("DSAPrivateKey", new byte[][] { this.c[0], this.c[1], this.c[2], n });
                this.b(a, a2);
                this.i();
                break Block_17_Outer;
            }
            catch (InstantiationException ex) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (IllegalAccessException ex2) {
                throw new psc_en("Bad arithmetic class.");
            }
            catch (psc_ap psc_ap) {
                throw new psc_en("Could not generate the key pair at this time.");
            }
            finally {
                if (array != null) {
                    for (int i = 0; i < array.length; ++i) {
                        array[i] = (array2[i] = 0);
                    }
                }
                if (n != null) {
                    for (int j = 0; j < n.length; ++j) {
                        n[j] = 0;
                    }
                }
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e0 != null) {
                    psc_e0.r();
                }
                if (psc_e2 != null) {
                    psc_e2.r();
                }
                if (psc_e3 != null) {
                    psc_e3.r();
                }
                if (a != null) {
                    a.y();
                }
                if (a2 != null) {
                    a2.y();
                }
                Label_0812: {
                    if (psc_ev != null) {
                        psc_ev.y();
                        break Label_0812;
                    }
                    break Label_0812;
                }
                while (true) {}
                // iftrue(Label_0589:, psc_e4 == null)
                // iftrue(Label_0621:, psc_e2 == null)
                // iftrue(Label_0651:, a2 == null)
                // iftrue(Label_0549:, array == null)
                // iftrue(Label_0577:, n4 >= n.length)
                // iftrue(Label_0631:, psc_e3 == null)
                // iftrue(Label_0661:, psc_ev == null)
                // iftrue(Label_0601:, psc_e5 == null)
                // iftrue(Label_0641:, a == null)
                // iftrue(Label_0611:, psc_e0 == null)
                // iftrue(Label_0549:, n3 >= array.length)
                // iftrue(Label_0577:, n == null)
                while (true) {
                Block_14:
                    while (true) {
                        Block_16: {
                            while (true) {
                                Label_0557:Label_0589_Outer:
                                while (true) {
                                    while (true) {
                                    Label_0651_Outer:
                                        while (true) {
                                            Block_18: {
                                                Block_12: {
                                                    while (true) {
                                                    Block_19:
                                                        while (true) {
                                                            Label_0523: {
                                                                Label_0661: {
                                                                    while (true) {
                                                                        Label_0631: {
                                                                            while (true) {
                                                                                while (true) {
                                                                                Block_9_Outer:
                                                                                    while (true) {
                                                                                        psc_e3.r();
                                                                                        break Label_0631;
                                                                                        Label_0577: {
                                                                                            break Label_0651_Outer;
                                                                                        }
                                                                                        while (true) {
                                                                                            n3 = 0;
                                                                                            break Label_0523;
                                                                                            break Block_16;
                                                                                            array[n3] = (array2[n3] = 0);
                                                                                            ++n3;
                                                                                            break Label_0523;
                                                                                            psc_ev.y();
                                                                                            break Label_0661;
                                                                                            break Block_19;
                                                                                            continue Label_0611_Outer;
                                                                                        }
                                                                                        break Block_12;
                                                                                        continue Block_9_Outer;
                                                                                    }
                                                                                    psc_e0.r();
                                                                                    continue Block_10_Outer;
                                                                                }
                                                                                continue Label_0641_Outer;
                                                                            }
                                                                            break Block_14;
                                                                        }
                                                                        break Block_18;
                                                                        continue Label_0651_Outer;
                                                                    }
                                                                }
                                                                continue Block_17_Outer;
                                                            }
                                                            continue Label_0641_Outer;
                                                        }
                                                        a2.y();
                                                        continue Label_0589_Outer;
                                                    }
                                                }
                                                n[n4] = 0;
                                                ++n4;
                                                continue Label_0557;
                                            }
                                            a.y();
                                            continue Label_0557_Outer;
                                        }
                                        psc_e4.r();
                                        continue Label_0601_Outer;
                                    }
                                    n4 = 0;
                                    continue Label_0557;
                                }
                                Label_0549: {
                                    continue;
                                }
                            }
                        }
                        psc_e2.r();
                        continue Label_0601_Outer;
                    }
                    psc_e5.r();
                    continue;
                }
            }
            break;
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av k = this.k();
        objectOutputStream.defaultWriteObject();
        this.a(k);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.l();
    }
    
    private psc_av k() {
        if (this.d == null) {
            return null;
        }
        if (!(this.d instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.d;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.f = psc_av.getAlgorithm();
        this.e = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.d;
        this.d = null;
        return psc_av2;
    }
    
    private void a(final psc_av d) {
        if (this.e == null) {
            return;
        }
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        this.e = null;
        this.f = null;
        this.d = d;
    }
    
    private void l() {
        if (this.e == null) {
            return;
        }
        this.d = psc_av.a(this.f, this.e);
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        this.e = null;
        this.f = null;
    }
    
    protected psc_em c() throws CloneNotSupportedException {
        final psc_jb psc_jb = new psc_jb();
        psc_jb.a = this.a;
        psc_jb.b = this.b;
        if (this.c != null) {
            final byte[][] c = new byte[this.c.length][];
            for (int i = 0; i < this.c.length; ++i) {
                if (this.c[i] != null) {
                    c[i] = this.c[i].clone();
                }
            }
            psc_jb.c = c;
        }
        psc_jb.d = this.d;
        psc_jb.g = this.g;
        psc_jb.a(this);
        return psc_jb;
    }
    
    protected void f() {
        super.f();
    }
    
    protected void j() {
        if (this.c != null) {
            for (int i = 0; i < this.c.length; ++i) {
                this.d(this.c[i]);
            }
        }
        this.c = null;
        this.b = 0;
    }
    
    public void y() {
        super.y();
    }
    
    protected void g() {
        this.f();
        this.j();
        this.g = 1;
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
