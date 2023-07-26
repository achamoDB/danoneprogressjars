import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_jc extends psc_em implements psc_i2, Cloneable, Serializable
{
    private Class a;
    private SecureRandom b;
    private byte[] c;
    private String d;
    private psc_el e;
    private byte[][] f;
    private int g;
    private static final int h = 1;
    private static final int i = 2;
    
    public psc_jc() {
        this.g = 1;
    }
    
    public String o() {
        return "DH";
    }
    
    private void i() {
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
        return psc_al instanceof psc_jv && psc_dt instanceof psc_js && super.a(psc_al, psc_dt);
    }
    
    protected boolean a(final psc_el psc_el, final int[] array, final SecureRandom b, final psc_nf[] array2) {
        this.f();
        this.h();
        if (b != null) {
            this.b = b;
        }
        if (psc_el == null) {
            return false;
        }
        if (!(psc_el instanceof psc_jp)) {
            return false;
        }
        if (array != null && array.length != 0) {
            return false;
        }
        if (this.b == null) {
            return false;
        }
        try {
            this.e = (psc_el)psc_el.clone();
            this.f = psc_el.b("DHParameters");
        }
        catch (CloneNotSupportedException ex) {
            return false;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        this.g = 2;
        return true;
    }
    
    protected void d() throws psc_en {
        this.f();
        if (this.g != 2) {
            throw new psc_en("Cannot reInit, object not initialized.");
        }
    }
    
    protected void e() throws psc_en {
        if (this.g != 2) {
            throw new psc_en("Object not initialized.");
        }
        this.i();
        psc_al a = null;
        psc_dt a2 = null;
        psc_i9 psc_i9 = null;
        psc_iy psc_iy = null;
        byte[] p = null;
        Label_0238: {
            try {
                a = psc_al.a("DH", this.m());
                a2 = psc_dt.a("DH", this.m());
                psc_i9 = new psc_i9();
                psc_i9.a(this.a);
                psc_iy = new psc_iy(psc_i9);
                psc_iy.a(this.e, this.b);
                final byte[] m = psc_iy.m();
                p = psc_iy.p();
                a2.a("DHPrivateKey", new byte[][] { this.f[0], this.f[1], this.f[2], p });
                a.a("DHPublicKey", new byte[][] { this.f[0], this.f[1], this.f[2], m });
                this.b(a, a2);
                break Label_0238;
            }
            catch (psc_ap psc_ap) {
                throw new psc_en("A necessary object is missing.");
            }
            finally {
                if (p != null) {
                    for (int i = 0; i < p.length; ++i) {
                        p[i] = 0;
                    }
                }
                if (a2 != null) {
                    a2.y();
                }
                if (a != null) {
                    a.y();
                }
                if (psc_i9 != null) {
                    psc_i9.y();
                }
                Label_0369: {
                    if (psc_iy != null) {
                        psc_iy.y();
                        break Label_0369;
                    }
                    break Label_0369;
                }
                while (true) {}
                Label_0302: {
                    return;
                }
                // iftrue(Label_0268:, n >= p.length)
                int n = 0;
                Label_0248:Block_8_Outer:
                while (true) {
                    Block_6: {
                        Block_9_Outer:Label_0292_Outer:Block_10_Outer:
                        while (true) {
                            Block_11: {
                                while (true) {
                                    Label_0284: {
                                        while (true) {
                                            while (true) {
                                                Label_0276: {
                                                    while (true) {
                                                        p[n] = 0;
                                                        ++n;
                                                        break Label_0248;
                                                        a2.y();
                                                        break Label_0276;
                                                        continue Block_8_Outer;
                                                    }
                                                    a.y();
                                                    break Label_0284;
                                                    break Block_11;
                                                }
                                                continue Label_0292_Outer;
                                            }
                                            break Block_6;
                                            psc_i9.y();
                                            continue Block_10_Outer;
                                        }
                                    }
                                    continue;
                                }
                            }
                            psc_iy.y();
                            return;
                            Label_0268:
                            continue Block_9_Outer;
                        }
                    }
                    n = 0;
                    continue Label_0248;
                }
            }
            // iftrue(Label_0302:, psc_iy == null)
            // iftrue(Label_0284:, a == null)
            // iftrue(Label_0268:, p == null)
            // iftrue(Label_0292:, psc_i9 == null)
            // iftrue(Label_0276:, a2 == null)
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
    
    private void k() {
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
    
    protected psc_em c() throws CloneNotSupportedException {
        final psc_jc psc_jc = new psc_jc();
        psc_jc.g = this.g;
        psc_jc.b = this.b;
        psc_jc.a = this.a;
        if (this.e != null) {
            psc_jc.e = (psc_el)this.e.clone();
        }
        if (this.f != null) {
            final byte[][] f = new byte[this.f.length][];
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != null) {
                    f[i] = this.f[i].clone();
                }
            }
            psc_jc.f = f;
        }
        psc_jc.a(this);
        return psc_jc;
    }
    
    protected void f() {
        super.f();
    }
    
    protected void h() {
        if (this.e != null) {
            this.e.y();
        }
        this.e = null;
        if (this.f != null) {
            for (int i = 0; i < this.f.length; ++i) {
                this.d(this.f[i]);
            }
        }
        this.f = null;
    }
    
    public void y() {
        super.y();
    }
    
    public void g() {
        this.f();
        this.h();
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
