import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i9 extends psc_an implements psc_ix, psc_i2, Cloneable, Serializable
{
    private int a;
    private Class b;
    private SecureRandom c;
    private byte[] d;
    private String e;
    private psc_e0 f;
    private psc_e0 g;
    private int h;
    private psc_e0 i;
    private psc_e0 j;
    private psc_e0 k;
    
    public psc_i9() {
    }
    
    public psc_i9(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Invalid instantiation parameters. Expected no parameters.");
        }
    }
    
    public String d() {
        return "DH";
    }
    
    public int f() {
        return this.a;
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public boolean a(final byte[] array, final int n) throws psc_ao, psc_be {
        try {
            return psc_nn.a(this, array, n);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao(psc_ap.getMessage());
        }
    }
    
    public byte[] e() throws psc_ao {
        return psc_nn.a(this.f, this.g, this.h);
    }
    
    private void l() {
        if (this.b == null) {
            this.b = psc_k6.a();
        }
    }
    
    public void a(final Class b) throws psc_k9 {
        try {
            final psc_e0 psc_e0 = b.newInstance();
        }
        catch (InstantiationException ex) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        this.b = b;
    }
    
    void a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4, final int h, final boolean b, final byte[] array3, final int n5, final int n6) throws psc_be {
        this.p();
        try {
            this.l();
            this.f = this.b.newInstance();
            this.g = this.b.newInstance();
            this.f.a(array, n, n2);
            this.g.a(array2, n3, n4);
            final int o = this.f.o();
            if (o < 256 || o > 2048) {
                throw new psc_be("Invalid DH prime size. Should be between 256 and 2048 bits.");
            }
            if (this.g.b(this.f) >= 0) {
                throw new psc_be("Invalid DH base size.");
            }
            this.h = o - 1;
            if (h != -1) {
                this.h = h;
            }
            else if (b) {
                this.h = 0;
                for (int i = 0, n7 = 0; i < n6; ++i, n7 += 8) {
                    final int h2 = this.h << n7;
                    this.h = h2;
                    this.h = (h2 | (array3[i + n5] & 0xFF));
                }
            }
            if (this.h >= o || this.h < 160) {
                throw new psc_be("Invalid DH max exponent length. Should be no less than 160 but less than " + o);
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
        this.m();
    }
    
    public void a(final SecureRandom c) {
        if (c != null) {
            this.c = c;
        }
    }
    
    public void a(final psc_el psc_el) throws psc_be {
        try {
            final byte[][] b = psc_el.b("DHParameters");
            if (b == null || b.length != 3) {
                throw new psc_be("Invalid DH parameters.");
            }
            this.a(b[0], 0, b[0].length, b[1], 0, b[1].length, -1, true, b[2], 0, b[2].length);
        }
        catch (psc_ao psc_ao) {
            throw new psc_be("Invalid DH parameters.");
        }
    }
    
    public boolean a(final psc_al psc_al) {
        final byte[][] a = psc_k7.a(psc_al, "DHPublicKey");
        if (a == null || a.length != 4) {
            return false;
        }
        try {
            this.a(a[0], 0, a[0].length, a[1], 0, a[1].length, -1, true, a[2], 0, a[2].length);
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        return true;
    }
    
    public boolean a(final psc_dt psc_dt) {
        final byte[][] a = psc_k7.a(psc_dt, "DHPrivateKey");
        if (a == null || a.length != 4) {
            return false;
        }
        try {
            this.a(a[0], 0, a[0].length, a[1], 0, a[1].length, -1, true, a[2], 0, a[2].length);
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        return true;
    }
    
    private void m() {
        if (this.f == null) {
            return;
        }
        this.a = (this.f.o() + 7) / 8;
    }
    
    public byte[][] g() {
        if (this.f == null || this.g == null) {
            return new byte[0][];
        }
        int n = 4;
        if (this.h <= 16777215) {
            --n;
            if (this.h <= 65535) {
                --n;
                if (this.h <= 255) {
                    --n;
                }
            }
        }
        byte[] n2;
        byte[] n3;
        try {
            n2 = this.f.n();
            n3 = this.g.n();
        }
        catch (psc_ap psc_ap) {
            n2 = null;
            n3 = null;
        }
        final byte[] array = new byte[n];
        for (int i = n - 1, n4 = 0; i >= 0; --i, n4 += 8) {
            array[i] = (byte)(this.h >>> n4 & 0xFF);
        }
        return new byte[][] { n2, n3, array };
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        try {
            (this.k = this.b.newInstance()).a(array, n, n2);
        }
        catch (psc_ap psc_ap) {
            this.k = null;
        }
        catch (InstantiationException ex) {
            this.k = null;
        }
        catch (IllegalAccessException ex2) {
            this.k = null;
        }
    }
    
    public void b(final psc_al psc_al) throws psc_bf {
        final byte[][] a = psc_k7.a(psc_al, "DHPublicValue");
        if (a == null || a.length != 1) {
            throw new psc_bf("Invalid DH public key.");
        }
        this.a(a[0], 0, a[0].length);
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        if (this.j != null) {
            this.j.r();
        }
        try {
            (this.j = this.b.newInstance()).a(array, n, n2);
            this.j.p();
        }
        catch (InstantiationException ex) {
            this.j = null;
        }
        catch (IllegalAccessException ex2) {
            this.j = null;
        }
        catch (psc_ap psc_ap) {
            this.j = null;
        }
    }
    
    public void b(final psc_dt psc_dt) throws psc_bf {
        final byte[][] a = psc_k7.a(psc_dt, "DHPrivateValue");
        if (a == null || a.length != 1) {
            throw new psc_bf("Invalid DH private key.");
        }
        this.b(a[0], 0, a[0].length);
        this.d(a[0]);
    }
    
    public void h() {
        int h = this.h;
        if (h < 160) {
            h = this.f.o() - 1;
        }
        final int n = (h + 7) / 8;
        final byte[] bytes = new byte[n];
        this.c.nextBytes(bytes);
        final int n2 = n * 8 - h;
        byte b = -1;
        for (int i = 0; i < n2; ++i) {
            b = (byte)((b & 0xFF) >>> 1);
            final byte[] array = bytes;
            final int n3 = 0;
            array[n3] &= b;
        }
        this.b(bytes, 0, bytes.length);
        this.d(bytes);
    }
    
    public int b(final byte[] array, final int n) {
        if (this.j == null) {
            return 0;
        }
        byte[] n2 = null;
        try {
            n2 = this.j.n();
            System.arraycopy(n2, 0, array, n, n2.length);
            return n2.length;
        }
        catch (psc_ap psc_ap) {
            return 0;
        }
        finally {
            if (n2 != null) {
                this.d(n2);
            }
        }
    }
    
    public void i() {
        try {
            this.i = this.b.newInstance();
            this.g.c(this.j, this.f, this.i);
        }
        catch (InstantiationException ex) {
            this.j = null;
        }
        catch (IllegalAccessException ex2) {
            this.j = null;
        }
        catch (psc_ap psc_ap) {
            this.i = null;
        }
    }
    
    public int c(final byte[] array, int n) {
        if (this.i == null) {
            return 0;
        }
        final int n2 = (this.f.o() + 7) / 8;
        try {
            final byte[] j = this.i.j(n2);
            for (int i = 0; i < j.length; ++i, ++n) {
                array[n] = j[i];
            }
            return j.length;
        }
        catch (psc_ap psc_ap) {
            return 0;
        }
    }
    
    public int d(final byte[] array, final int n) {
        psc_e0 psc_e0 = null;
        byte[] j = null;
        Label_0211: {
            try {
                psc_e0 = this.b.newInstance();
                this.k.c(this.j, this.f, psc_e0);
                j = psc_e0.j((this.f.o() + 7) / 8);
                System.arraycopy(j, 0, array, n, j.length);
                final int length = j.length;
                break Label_0211;
            }
            catch (InstantiationException ex) {
                final boolean b = false;
                break Label_0211;
            }
            catch (IllegalAccessException ex2) {
                final boolean b = false;
                break Label_0211;
            }
            catch (psc_ap psc_ap) {
                final boolean b = false;
                break Label_0211;
            }
            finally {
                if (j != null) {
                    this.d(j);
                }
                Label_0260: {
                    if (psc_e0 != null) {
                        psc_e0.r();
                        break Label_0260;
                    }
                    break Label_0260;
                }
                while (true) {}
                // iftrue(Label_0224:, j == null)
                // iftrue(Label_0198:, j == null)
                // iftrue(Label_0172:, j == null)
                // iftrue(Label_0182:, psc_e0 == null)
                // iftrue(Label_0146:, j == null)
                // iftrue(Label_0234:, psc_e0 == null)
                // iftrue(Label_0156:, psc_e0 == null)
                // iftrue(Label_0208:, psc_e0 == null)
                final int length;
                Block_8: {
                    final boolean b;
                    Block_10: {
                        while (true) {
                            Block_13: {
                                break Block_13;
                                Label_0208: {
                                    return b ? 1 : 0;
                                }
                                Label_0234:
                                return b ? 1 : 0;
                                Block_7_Outer:Block_14_Outer:
                                while (true) {
                                    psc_e0.r();
                                    return b ? 1 : 0;
                                    Label_0198: {
                                        Label_0146: {
                                            while (true) {
                                                while (true) {
                                                    this.d(j);
                                                    break Label_0146;
                                                    this.d(j);
                                                    break Label_0198;
                                                    this.d(j);
                                                    Label_0172: {
                                                        break Label_0172;
                                                        psc_e0.r();
                                                        return b ? 1 : 0;
                                                        Label_0156:
                                                        return length;
                                                    }
                                                    break Block_10;
                                                    Label_0182:
                                                    return b ? 1 : 0;
                                                    continue Block_14_Outer;
                                                }
                                                continue;
                                            }
                                        }
                                        break Block_8;
                                    }
                                    continue Block_7_Outer;
                                }
                            }
                            this.d(j);
                            continue;
                        }
                    }
                    psc_e0.r();
                    return b ? 1 : 0;
                }
                psc_e0.r();
                return length;
            }
        }
    }
    
    public void j() {
    }
    
    public void k() {
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av n = this.n();
        objectOutputStream.defaultWriteObject();
        this.a(n);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.o();
    }
    
    private psc_av n() {
        if (this.j != null) {
            this.j.q();
        }
        if (this.c == null) {
            return null;
        }
        if (!(this.c instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.c;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.e = psc_av.getAlgorithm();
        this.d = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.c;
        this.c = null;
        return psc_av2;
    }
    
    private void a(final psc_av c) {
        if (this.j != null) {
            this.j.p();
        }
        if (this.d == null) {
            return;
        }
        for (int i = 0; i < this.d.length; ++i) {
            this.d[i] = 0;
        }
        this.d = null;
        this.e = null;
        this.c = c;
    }
    
    private void o() {
        if (this.j != null) {
            this.j.p();
        }
        if (this.d == null) {
            return;
        }
        this.c = psc_av.a(this.e, this.d);
        for (int i = 0; i < this.d.length; ++i) {
            this.d[i] = 0;
        }
        this.d = null;
        this.e = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_i9 psc_i9 = new psc_i9();
        psc_i9.b = this.b;
        psc_i9.c = this.c;
        psc_i9.a = this.a;
        if (this.f != null) {
            psc_i9.f = (psc_e0)this.f.clone();
        }
        if (this.g != null) {
            psc_i9.g = (psc_e0)this.g.clone();
        }
        psc_i9.h = this.h;
        if (this.i != null) {
            psc_i9.i = (psc_e0)this.i.clone();
        }
        if (this.j != null) {
            psc_i9.j = (psc_e0)this.j.clone();
        }
        if (this.k != null) {
            psc_i9.k = (psc_e0)this.k.clone();
        }
        return psc_i9;
    }
    
    private void p() {
        if (this.f != null) {
            this.f.r();
        }
        if (this.g != null) {
            this.g.r();
        }
        this.h = 0;
        final psc_e0 psc_e0 = null;
        this.g = psc_e0;
        this.f = psc_e0;
    }
    
    private void q() {
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
        this.p();
        this.q();
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
