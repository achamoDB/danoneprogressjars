import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ev extends psc_av implements Cloneable, Serializable, psc_ax
{
    private static final byte[] a;
    static final int b = 64;
    static final int c = 20;
    protected psc_ew d;
    private static final byte[] e;
    private psc_ez f;
    private psc_ez g;
    private psc_ez h;
    private byte[] i;
    private transient psc_dd j;
    private byte[] k;
    private boolean l;
    private boolean m;
    private byte[] n;
    private byte[] o;
    private transient psc_dd p;
    private int q;
    
    public void h() {
        if (this.f != null) {
            this.f.r();
        }
        this.f = null;
    }
    
    public void a(final byte[] array) {
        (this.f = new psc_ez()).a(array, 0, array.length);
    }
    
    protected void i() {
        this.l = false;
        this.q = 0;
        this.k = new byte[20];
        this.i = null;
        this.n = null;
        this.o = null;
        if (this.g == null) {
            this.g = new psc_ez();
        }
        if (this.h == null) {
            this.h = new psc_ez();
        }
        if (this.d == null) {
            this.d = this.k();
        }
    }
    
    public psc_ev() {
        this.m = false;
        this.i();
    }
    
    public psc_ev(final byte[] array) {
        this.m = false;
        this.i();
        if (array != null) {
            this.c(array);
        }
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of Parameters: expected none");
        }
    }
    
    public int[] e() {
        return new int[0];
    }
    
    public byte[] g() {
        final byte[] array = new byte[psc_ev.a.length];
        System.arraycopy(psc_ev.a, 0, array, 0, psc_ev.a.length);
        return array;
    }
    
    public psc_az f() {
        return new psc_ew();
    }
    
    public void j() {
        for (int i = 0; i < 0; ++i) {
            this.i[i] = 0;
        }
    }
    
    public void b(final byte[] array) {
        if (array == null) {
            return;
        }
        if (array.length < 20 || array.length > 64) {
            throw new SecurityException("seed bytes length of bounds");
        }
        this.i = new byte[array.length];
        this.n = new byte[20];
        System.arraycopy(array, 0, this.i, 0, array.length);
        this.j = psc_au.b(this.i);
        this.p = psc_au.b(this.n);
    }
    
    protected void c(final byte[] array) {
        if (array == null) {
            return;
        }
        if (psc_aq.k() == 2) {
            this.b(array);
        }
        else {
            this.i = new byte[20];
            this.n = new byte[20];
            try {
                this.d.j();
                this.d.a(array, 0, array.length);
                this.d.b(this.i, 0);
            }
            catch (psc_en psc_en) {
                throw new SecurityException(psc_en.getMessage());
            }
        }
        this.j = psc_au.b(this.i);
        this.p = psc_au.b(this.n);
        if (psc_aq.k() == 0 || psc_aq.k() == 2) {
            this.v();
            this.a(20, this.n, 0);
            if (psc_aq.k() == 2) {
                this.q = 20;
            }
            this.u();
            this.l = true;
        }
    }
    
    protected psc_ew k() {
        return new psc_ew();
    }
    
    public String getAlgorithm() {
        return "FIPS186Random";
    }
    
    public void nextBytes(final byte[] array) {
        if (this.f != null) {
            this.d(array);
        }
        else {
            this.b(array, 0, array.length);
        }
    }
    
    public void d(final byte[] array) {
        if (array.length != 20 && array.length != 40) {
            throw new IllegalArgumentException("Buffer length was " + array.length + ", expected " + 20);
        }
        final byte[] array2 = new byte[40];
        Label_0218: {
            try {
                if (this.o == null) {
                    this.b(this.o = new byte[40], 0, this.o.length);
                    if (psc_aq.k() == 2) {
                        System.arraycopy(this.f(this.o), 0, array, 0, 20);
                        break Label_0218;
                    }
                }
                this.b(array2, 0, array2.length);
                this.a(this.o, 0, array2, 0, 40);
                System.arraycopy(array2, 0, this.o, 0, array2.length);
                System.arraycopy(this.f(array2), 0, array, 0, 20);
                break Label_0218;
            }
            finally {
                if (this.h != null) {
                    this.h.r();
                }
                if (this.g != null) {
                    this.g.r();
                }
                psc_au.c(array2);
                while (true) {}
                // iftrue(Label_0197:, this.h == null)
                // iftrue(Label_0211:, this.g == null)
                // iftrue(Label_0248:, this.g == null)
                // iftrue(Label_0234:, this.h == null)
            Label_0197_Outer:
                while (true) {
                    this.h.r();
                    while (true) {
                        Block_8: {
                            Label_0211: {
                                Block_11: {
                                    Block_9: {
                                        Label_0234: {
                                            break Label_0234;
                                            break Block_8;
                                            break Block_9;
                                        }
                                        break Block_11;
                                    }
                                    this.g.r();
                                    break Label_0211;
                                }
                                this.g.r();
                                break Label_0197_Outer;
                            }
                            psc_au.c(array2);
                            return;
                        }
                        this.h.r();
                        continue;
                    }
                    continue Label_0197_Outer;
                }
                psc_au.c(array2);
            }
        }
    }
    
    public void b(final byte[] array, int n, int i) {
        if (this.i == null) {
            this.r();
        }
        if (array == null) {
            throw new IllegalArgumentException("null parameter");
        }
        if (n + i > array.length) {
            throw new ArrayIndexOutOfBoundsException("offset+numberOfBytes > " + array.length);
        }
        this.v();
        if (this.q > 0) {
            final int n2 = (i < this.q) ? i : this.q;
            System.arraycopy(this.n, 20 - this.q, array, n, n2);
            n += n2;
            i -= n2;
            this.q -= n2;
        }
        while (i > 0) {
            final int a = this.a(i, array, n);
            i -= a;
            n += a;
            this.q = 20 - a;
        }
        this.u();
    }
    
    protected int a(final int n, final byte[] array, final int n2) {
        final byte[] e = this.e(this.i);
        if (this.l && !this.m) {
            this.a(this.n, 0, e, 0, 20);
        }
        System.arraycopy(e, 0, this.n, 0, 20);
        final int n3 = (n > 20) ? 20 : n;
        System.arraycopy(e, 0, array, n2, n3);
        this.a(this.i, e, 1);
        return n3;
    }
    
    private void a(final byte[] array, final byte[] array2) {
        this.a(array, array2, 0);
    }
    
    private void a(final byte[] array, final byte[] array2, final int n) {
        int n2 = n;
        int n3 = array.length - 1;
        for (int i = array2.length - 1; i >= 0; --i, --n3) {
            final int n4 = n2 + ((array[n3] & 0xFF) + (array2[i] & 0xFF));
            array2[i] = 0;
            array[n3] = (byte)n4;
            n2 = n4 >>> 8;
        }
        while (n2 > 0 && n3 >= 0) {
            final int n5 = n2 + (array[n3] & 0xFF);
            array[n3] = (byte)n5;
            n2 = n5 >>> 8;
            --n3;
        }
    }
    
    protected byte[] e(final byte[] array) {
        try {
            this.d.j();
            this.d.a(array, 0, array.length);
            this.d.a(psc_ev.e, 0, 64 - array.length);
            this.d.c(this.k, 0);
        }
        catch (psc_ap psc_ap) {
            throw new SecurityException(psc_ap.getMessage());
        }
        return this.k;
    }
    
    protected byte[] f(final byte[] array) {
        try {
            this.g.a(array, 0, array.length);
            this.g.d(this.f, (psc_e0)this.h);
            return this.h.j(20);
        }
        catch (psc_ap psc_ap) {
            throw new SecurityException(psc_ap.getMessage());
        }
        finally {
            this.g.r();
            this.h.r();
        }
    }
    
    public void g(final byte[] array) {
        if (array == null) {
            return;
        }
        if (this.i == null) {
            this.c(array);
        }
        else {
            if (psc_aq.k() == 2) {
                this.a(this.i, array);
                return;
            }
            try {
                this.d.a(array, 0, array.length);
                this.d.b(this.i, 0);
            }
            catch (psc_ap psc_ap) {
                throw new SecurityException(psc_ap.getMessage());
            }
        }
    }
    
    public void r() {
        final byte[] a = psc_av.a(20);
        this.g(a);
        psc_au.c(a);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.m();
        objectOutputStream.defaultWriteObject();
        this.n();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
        this.t();
    }
    
    private void m() {
        if (this.j != null) {
            this.j.d();
        }
        if (this.p != null) {
            this.p.d();
        }
    }
    
    private void n() {
        if (this.j != null) {
            this.j.c();
        }
        if (this.p != null) {
            this.p.c();
        }
    }
    
    private void t() {
        if (this.i == null || this.n == null) {
            return;
        }
        (this.j = psc_au.b(this.i)).c();
        this.p = psc_au.b(this.n);
        if (this.p != null) {
            this.p.c();
        }
    }
    
    private void u() {
        if (this.j != null) {
            this.j.c();
        }
        if (this.p != null) {
            this.p.c();
        }
    }
    
    private void v() {
        if (this.j != null) {
            this.j.d();
        }
        if (this.p != null) {
            this.p.d();
        }
    }
    
    public void y() {
        super.y();
        if (this.d != null) {
            this.d.y();
        }
        if (this.i != null) {
            System.arraycopy(psc_ev.e, 0, this.i, 0, this.i.length);
        }
        psc_au.c(this.i, this.j);
        if (this.n != null) {
            System.arraycopy(psc_ev.e, 0, this.n, 0, 20);
        }
        psc_au.c(this.n, this.p);
        if (this.o != null) {
            System.arraycopy(psc_ev.e, 0, this.o, 0, 40);
        }
        this.i();
    }
    
    protected psc_ev l() {
        return new psc_ev();
    }
    
    public void a(final boolean m) {
        this.m = m;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ev l = this.l();
        l.l = this.l;
        l.q = this.q;
        if (this.d != null) {
            l.d = (psc_ew)this.d.clone();
        }
        if (this.f != null) {
            l.f = (psc_ez)this.f.clone();
        }
        if (this.g != null) {
            l.g = (psc_ez)this.g.clone();
        }
        if (this.h != null) {
            l.h = (psc_ez)this.h.clone();
        }
        if (this.j != null) {
            l.i = (byte[])psc_au.a(this.i, this.j);
            l.j = psc_au.a(l.i);
        }
        if (this.p != null) {
            l.n = (byte[])psc_au.a(this.n, this.p);
            l.p = psc_au.a(l.n);
        }
        if (this.o != null) {
            l.o = this.o;
        }
        l.a(this);
        return l;
    }
    
    static {
        a = psc_a5.b("da39a3ee5e6b4b0d3255bfef95601890afd80709");
        e = new byte[64];
    }
}
