import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ka extends psc_av implements psc_ax
{
    private int a;
    private int b;
    private psc_ew[] c;
    private int[] d;
    private static final int e = 0;
    private static final int f = 1;
    private static final int g = 2;
    private byte[] h;
    private static final int[] i;
    private boolean j;
    private boolean k;
    private byte[] l;
    private psc_ez m;
    private static final byte[] n;
    private psc_ez o;
    private psc_ez p;
    private boolean q;
    byte[] r;
    private transient psc_dd s;
    private static final int t = 20;
    private transient psc_dd u;
    static final int v = 20;
    
    public psc_ka() {
        this.l = new byte[44];
        this.a = 1;
        this.b = 0;
        this.j = false;
        this.k = false;
        if (psc_aq.i() && psc_av.b()) {
            this.q = true;
        }
        this.h = null;
        this.r = null;
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            this.a = 1;
            this.j();
            return;
        }
        if (array.length > 1) {
            throw new psc_be("Wrong Number of Parameters: expected one - number of streams.");
        }
        this.a = array[0];
        this.j();
    }
    
    private void j() throws psc_be {
        if (this.a < 1 || this.a > 6) {
            throw new psc_be("Invalid Number of Streams: expected 1 to 6.");
        }
        this.c = new psc_ew[this.a];
        this.d = new int[this.a];
        for (int i = 0; i < this.a; ++i) {
            (this.c[i] = new psc_ew()).j();
            this.d[i] = 2;
        }
        (this.m = new psc_ez()).a(psc_ka.n, 0, psc_ka.n.length);
        this.o = new psc_ez();
        this.p = new psc_ez();
        this.b = 0;
        this.j = false;
        this.k = false;
        this.h = null;
        this.u = null;
    }
    
    public int[] q() {
        return this.e();
    }
    
    public int[] e() {
        return new int[] { this.a };
    }
    
    public String getAlgorithm() {
        return "X931Random";
    }
    
    public psc_az f() {
        return null;
    }
    
    public byte[] g() {
        return null;
    }
    
    public void g(final byte[] array) {
        if (array == null) {
            return;
        }
        if (this.h == null) {
            this.h = new byte[84 + 65 * this.a];
            this.u = psc_au.b(this.h);
            this.r = new byte[20];
            if (this.s == null) {
                this.s = psc_au.b(this.r);
            }
        }
        int n = array.length / this.a;
        int n2 = array.length - n;
        int i = 0;
        int n3 = 0;
        while (i < this.a) {
            try {
                this.c[i].a(array, n3, n);
                this.c[i].m();
            }
            catch (psc_ap psc_ap) {}
            this.d[i] = 1;
            n3 += n;
            if (i < this.a - 1) {
                n = n2 / (this.a - i - 1);
            }
            else {
                n = n2;
            }
            n2 -= n;
            ++i;
        }
    }
    
    public void h(final byte[] array) throws psc_e1 {
        if (this.j) {
            throw new psc_e1("Can call extraSeed method only once.");
        }
        if (this.h == null) {
            this.h = new byte[84 + 65 * this.a];
            this.u = psc_au.b(this.h);
            this.r = new byte[20];
            if (this.s == null) {
                this.s = psc_au.b(this.r);
            }
        }
        if (this.k) {
            throw new psc_e1("Cannot call extraSeed method after generating random bytes.");
        }
        this.j = true;
        final int n = this.a * 64;
        final int n2 = this.a * 20;
        if (array.length > n || array.length < n2) {
            throw new psc_e1("Invalid extraSeed length, must be 20 to 64 times number of streams.");
        }
        this.i();
        int n3 = array.length / this.a;
        int n4 = array.length - n3;
        int i = 0;
        int n5 = 0;
        while (i < this.a) {
            this.h[psc_ka.i[i]] = (byte)n3;
            System.arraycopy(array, n5, this.h, psc_ka.i[i] + 1, n3);
            n5 += n3;
            if (i < this.a - 1) {
                n3 = n4 / (this.a - i - 1);
            }
            else {
                n3 = n4;
            }
            n4 -= n3;
            ++i;
        }
        this.h();
    }
    
    public void r() {
        psc_av.c();
        for (int i = 0; i < this.a; ++i) {
            this.g(psc_av.a(20));
        }
    }
    
    private void a(final byte[] array, int n, int i) {
        if (!this.k) {
            this.k();
        }
        final psc_ew psc_ew = this.c[this.b];
        this.i();
        int n2 = psc_ka.i[this.b];
        final byte b = this.h[n2];
        ++n2;
        ++this.b;
        if (this.b >= this.a) {
            this.b = 0;
        }
        while (i > 0) {
            i -= this.a(psc_ew, i, b, n2, this.d[this.b], array, n);
            n += 20;
            this.d[this.b] = 0;
        }
        this.d[this.b] = 2;
        this.h();
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        if (this.h == null) {
            this.r();
        }
        if (array == null) {
            throw new IllegalArgumentException("null parameter");
        }
        if (n + n2 > array.length) {
            throw new ArrayIndexOutOfBoundsException("offset+numberOfBytes > " + array.length);
        }
        if (this.q && psc_aq.c()) {
            this.a(this.r, 0, 20);
        }
        this.a(array, n, n2);
    }
    
    private int a(final psc_ew psc_ew, final int n, int n2, final int n3, int n4, final byte[] array, final int n5) {
        if (n4 == 1) {
            psc_ew.c(this.h, 64);
            if (this.j) {
                for (int i = 0; i < 20; ++i) {
                    final byte[] h = this.h;
                    final int n6 = i + n3;
                    h[n6] ^= this.h[i + 64];
                }
            }
            else {
                System.arraycopy(this.h, 64, this.h, n3, 20);
            }
            n4 = 2;
        }
        if (n4 == 2) {
            int j;
            for (j = 0; j < 20; ++j) {
                this.h[j] = this.h[j + n3];
            }
            while (j < n2) {
                this.h[j] = this.h[j + n3];
                ++j;
            }
        }
        if (n2 < 20) {
            n2 = 20;
        }
        final byte[] b = this.b(n2);
        int n7 = 1;
        int n8 = n2 - 1;
        for (int k = 19; k >= 0; --k, --n8) {
            final int n9 = n7 + ((this.h[n8] & 0xFF) + (b[k] & 0xFF));
            b[k] = 0;
            this.h[n8] = (byte)n9;
            n7 = n9 >>> 8;
        }
        while (n7 > 0 && n8 >= 0) {
            final int n10 = n7 + (this.h[n8] & 0xFF);
            this.h[n8] = (byte)n10;
            n7 = n10 >>> 8;
            --n8;
        }
        int n11 = 20;
        if (n < n11) {
            n11 = n;
        }
        System.arraycopy(this.h, n2 - 20, array, n5, n11);
        if (this.q) {
            this.q = false;
        }
        else {
            this.a(this.r, 0, this.h, n2 - 20, 20);
            System.arraycopy(this.h, n2 - 20, this.r, 0, 20);
        }
        if (n - n11 == 0) {
            System.arraycopy(this.h, 0, this.h, n3, n2);
        }
        return n11;
    }
    
    private byte[] b(final int n) {
        try {
            final psc_ew psc_ew = new psc_ew();
            psc_ew.j();
            psc_ew.a(this.h, 0, n);
            psc_ew.a(this.l, 0, 64 - n);
            psc_ew.c(this.h, 64);
            this.o.a(this.h, 64, 20);
            this.o.d(this.m, (psc_e0)this.p);
            return this.p.j(20);
        }
        catch (psc_ap psc_ap) {
            throw new SecurityException(psc_ap.getMessage());
        }
        finally {
            this.o.r();
            this.p.r();
        }
    }
    
    private void k() {
        if (this.a < 1) {
            this.k = false;
            return;
        }
        try {
            for (int i = 1; i < this.a; ++i) {
                for (int j = 0; j < i; ++j) {
                    this.a(j, i);
                }
            }
        }
        catch (psc_e1 psc_e1) {
            this.r();
        }
        finally {
            this.k = true;
        }
    }
    
    private void a(final int n, final int n2) throws psc_e1 {
        final byte[] array = new byte[20];
        final byte[] array2 = new byte[20];
        this.c[n].c(array, 0);
        this.c[n2].c(array2, 0);
        if (!this.b(array, 0, array2, 0, 20)) {
            return;
        }
        if (this.b(this.h, psc_ka.i[n], this.h, psc_ka.i[n2], 64)) {
            throw new psc_e1("Streams are not independent");
        }
    }
    
    private boolean b(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int n4 = n, n5 = n2, i = 0; i < n3; ++i, ++n4, ++n5) {
            if (array[n4] != array2[n5]) {
                return false;
            }
        }
        return true;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.l();
        objectOutputStream.defaultWriteObject();
        this.m();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.n();
    }
    
    private void l() {
        if (this.u != null) {
            this.u.d();
        }
        if (this.s != null) {
            this.s.d();
        }
    }
    
    private void m() {
        if (this.u != null) {
            this.u.c();
        }
        if (this.s != null) {
            this.s.c();
        }
    }
    
    private void n() {
        if (this.h == null || this.r == null) {
            return;
        }
        (this.u = psc_au.b(this.h)).c();
        (this.s = psc_au.b(this.r)).c();
    }
    
    public void h() {
        this.u.c();
        this.s.c();
    }
    
    public void i() {
        this.u.d();
        this.s.d();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ka psc_ka = new psc_ka();
        psc_ka.a = this.a;
        psc_ka.b = this.b;
        psc_ka.j = this.j;
        psc_ka.k = this.k;
        psc_ka.q = this.q;
        if (this.c != null) {
            psc_ka.c = new psc_ew[this.a];
            for (int i = 0; i < this.a; ++i) {
                psc_ka.c[i] = (psc_ew)this.c[i].clone();
            }
        }
        if (this.h != null) {
            psc_ka.h = (byte[])psc_au.a(this.h, this.u);
            psc_ka.u = psc_au.a(psc_ka.h);
        }
        if (this.r != null) {
            psc_ka.r = (byte[])psc_au.a(this.r, this.s);
            psc_ka.s = psc_au.a(psc_ka.r);
        }
        if (this.d != null) {
            psc_ka.d = new int[this.a];
            for (int j = 0; j < this.a; ++j) {
                psc_ka.d[j] = this.d[j];
            }
        }
        if (this.m != null) {
            psc_ka.m = (psc_ez)this.m.clone();
        }
        if (this.o != null) {
            psc_ka.o = (psc_ez)this.o.clone();
        }
        if (this.p != null) {
            psc_ka.p = (psc_ez)this.p.clone();
        }
        psc_ka.a(this);
        return psc_ka;
    }
    
    public void y() {
        super.y();
        if (this.c != null) {
            for (int i = 0; i < this.a; ++i) {
                this.c[i].y();
            }
        }
        psc_au.c(this.h, this.u);
        this.u = null;
        psc_au.c(this.r, this.s);
        this.s = null;
        this.h = null;
        if (psc_aq.i() && psc_av.b()) {
            this.q = true;
        }
        this.r = null;
        this.b = 0;
        this.j = false;
        this.k = false;
        try {
            this.j();
        }
        catch (psc_be psc_be) {
            throw new SecurityException(psc_be.getMessage());
        }
    }
    
    protected void finalize() throws Throwable {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        i = new int[] { 84, 149, 214, 279, 344, 409 };
        n = new byte[] { -78, 13, -80, -79, 1, -33, 12, 102, 36, -4, 19, -110, -70, 85, -9, 125, 87, 116, -127, -27 };
    }
}
