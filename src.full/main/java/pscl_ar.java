import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.FilterInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ar extends FilterInputStream
{
    private byte[] a;
    public static int b;
    public int c;
    private int d;
    private int e;
    private int f;
    public int g;
    private int h;
    private pscl_bn i;
    private pscl_i j;
    public pscl_d k;
    public pscl_d l;
    public boolean m;
    private int n;
    private int o;
    private PrintStream p;
    public static final int q = 20;
    public static final int r = 21;
    public static final int s = 22;
    public static final int t = 23;
    public static final int u = 255;
    public byte[] v;
    public boolean w;
    
    public pscl_ar(final InputStream inputStream, final pscl_d pscl_d, final pscl_d pscl_d2) throws IOException, pscl_h {
        this(inputStream, pscl_d, pscl_d2, null);
    }
    
    public pscl_ar(final InputStream in, final pscl_d k, final pscl_d l, final byte[] array) throws IOException, pscl_h {
        super(in);
        this.a = null;
        this.e = 0;
        this.h = 0;
        this.v = null;
        this.w = false;
        this.a = new byte[pscl_ar.b];
        this.c = 0;
        this.k = k;
        this.l = l;
        if (array != null) {
            if (array.length != 5) {
                throw new pscl_h("Wrong header length");
            }
            System.arraycopy(array, 0, this.v = new byte[array.length], 0, array.length);
            this.w = true;
        }
    }
    
    public pscl_ar(final InputStream in, final byte[] array, final pscl_d k, final pscl_d l) throws Exception {
        super(in);
        this.a = null;
        this.e = 0;
        this.h = 0;
        this.v = null;
        this.w = false;
        System.arraycopy(array, 0, this.a = new byte[pscl_ar.b], 0, array.length);
        this.c = 0;
        this.e = array.length;
        this.k = k;
        this.l = l;
    }
    
    private int a(final byte[] b, final int n, final int n2) throws IOException {
        int i = 0;
        Throwable t = null;
        while (i < n2) {
            try {
                i += super.in.read(b, n + i, n2 - i);
            }
            catch (InterruptedIOException ex) {
                this.h += i;
                ex.bytesTransferred = 0;
                throw ex;
            }
            catch (IOException ex2) {
                t = ex2;
            }
            if (i < 0 || t != null) {
                final String x = (i < 0) ? "socket closed prematurely" : ("socket failed: " + t.getMessage());
                if ((this.o & 0x4) == 0x4) {
                    this.p.println(x);
                }
                throw new pscl_ah(x, 1, 0);
            }
        }
        return i;
    }
    
    public int a() throws IOException, InterruptedIOException {
        this.e = 0;
        this.h = 0;
        int n;
        if (this.v == null) {
            this.v = new byte[5];
            if ((n = this.a(this.v, 0, 5)) <= 0) {
                this.v = null;
                return -1;
            }
            this.w = true;
        }
        else if (!this.w) {
            if ((n = this.a(this.v, 0, 5)) <= 0) {
                this.v = null;
                return -1;
            }
            this.w = true;
        }
        else {
            n = this.v.length;
        }
        if ((this.o & 0x2) == 0x2) {
            this.p.println("[Input] Header: \n" + pscl_k.a(this.v, 0, 5));
        }
        this.g = (this.v[0] & 0xFF);
        this.f = ((this.v[1] & 0xFF) << 8 | (this.v[2] & 0xFF));
        this.e = ((this.v[3] & 0xFF) << 8 | (this.v[4] & 0xFF));
        this.v = null;
        this.w = false;
        return n;
    }
    
    public int b() throws IOException, InterruptedIOException, pscl_av {
        int a = 0;
        this.c = 16384;
        if (this.h == this.e) {
            a = this.a();
            this.h = 0;
        }
        if (a < 0) {
            return -1;
        }
        if (this.e > pscl_ar.b) {
            throw new pscl_av("Length out of packet boundary");
        }
        int a2;
        try {
            a2 = this.a(this.a, this.h, this.e - this.h);
        }
        catch (InterruptedIOException ex) {
            throw ex;
        }
        this.h += a2;
        if ((this.o & 0x2) == 0x2 && (this.o & 0x4) == 0x4) {
            this.p.println("[Input] Data: \n" + pscl_k.a(this.a, 0, this.e));
        }
        try {
            this.n();
            this.o();
            this.c = 0;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (Exception ex2) {
            throw new IOException("readBuffer() error: " + ex2.getMessage());
        }
        if ((this.o & 0x2) == 0x2 && (this.o & 0x4) == 0x4) {
            this.p.println("[Input] Plaintext: \n" + pscl_k.a(this.a, 0, this.e));
        }
        if (this.g == 21) {
            final int read = this.read();
            final int read2 = this.read();
            throw new pscl_ah("An Alert was received: " + pscl_ah.a(read2), read, read2);
        }
        return a2;
    }
    
    public int read(final byte[] array, int n, final int n2) throws IOException, pscl_av, InterruptedIOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("Invalid input parameter(s)");
        }
        if (n2 == 0) {
            return n2;
        }
        if ((this.e == 0 || this.c >= this.h) && this.b() < 0) {
            return -1;
        }
        int i = n2;
        int n3 = 0;
        while (i > this.e - this.c) {
            System.arraycopy(this.a, this.c, array, n, this.e - this.c);
            n3 += this.e - this.c;
            this.a(this.e - this.c);
            i -= this.e - this.c;
            n += this.e - this.c;
            this.c = this.e;
            if (super.in.available() <= 0) {
                return n2 - i;
            }
            try {
                if (this.b() < 0) {
                    return n2 - i;
                }
            }
            catch (InterruptedIOException ex) {
                ex.bytesTransferred += n3;
                throw ex;
            }
            if (i <= 0) {
                return n2;
            }
        }
        System.arraycopy(this.a, this.c, array, n, i);
        this.a(i);
        this.c += i;
        return n2;
    }
    
    public int read() throws IOException, InterruptedIOException {
        int n;
        if (this.c + 1 <= this.h) {
            n = (this.a[this.c] & 0xFF);
            this.a(1);
            ++this.c;
        }
        else {
            final int b = this.b();
            if (b < 0) {
                return b;
            }
            n = (this.a[this.c] & 0xFF);
            this.a(1);
            ++this.c;
        }
        return n;
    }
    
    public int c() throws IOException, pscl_av, InterruptedIOException {
        int n;
        if (this.c + 2 <= this.h) {
            n = ((this.a[this.c] & 0xFF) << 8 | (this.a[this.c + 1] & 0xFF));
            this.a(2);
            this.c += 2;
        }
        else {
            if (this.b() < 0) {
                throw new IOException("readUint16() error");
            }
            n = ((this.a[this.c] & 0xFF) << 8 | (this.a[this.c + 1] & 0xFF));
            this.a(2);
            this.c += 2;
        }
        return n;
    }
    
    public int d() throws IOException, pscl_av, InterruptedIOException {
        int n;
        if (this.c + 3 <= this.h) {
            n = ((this.a[this.c] & 0xFF) << 16 | (this.a[this.c + 1] & 0xFF) << 8 | (this.a[this.c + 2] & 0xFF));
            this.a(3);
            this.c += 3;
        }
        else {
            if (this.b() < 0) {
                throw new IOException("readUint24() error");
            }
            n = ((this.a[this.c] & 0xFF) << 16 | (this.a[this.c + 1] & 0xFF) << 8 | (this.a[this.c + 2] & 0xFF));
            this.a(3);
            this.c += 3;
        }
        return n;
    }
    
    public int e() throws IOException, pscl_av, InterruptedIOException {
        int n;
        if (this.c + 4 <= this.h) {
            n = ((this.a[this.c] & 0xFF) << 24 | (this.a[this.c + 1] & 0xFF) << 16 | (this.a[this.c + 2] & 0xFF) << 8 | (this.a[this.c + 3] & 0xFF));
            this.a(4);
            this.c += 4;
        }
        else {
            if (this.b() < 0) {
                throw new IOException("readUint32()");
            }
            n = ((this.a[this.c] & 0xFF) << 24 | (this.a[this.c + 1] & 0xFF) << 16 | (this.a[this.c + 2] & 0xFF) << 8 | (this.a[this.c + 3] & 0xFF));
            this.a(4);
            this.c += 4;
        }
        return n;
    }
    
    public int f() {
        return this.f;
    }
    
    public int g() throws IOException {
        if (this.c >= this.e) {
            pscl_au.d(this);
        }
        return this.g;
    }
    
    public int h() {
        return this.e;
    }
    
    public void a(final pscl_i j) {
        this.j = j;
        this.d = this.j.i();
        if (this.j.e() == 1) {
            this.m = false;
        }
        else {
            this.m = true;
        }
    }
    
    public void a(final pscl_bn i) {
        this.i = i;
    }
    
    public pscl_i i() {
        return this.j;
    }
    
    public pscl_bn j() {
        return this.i;
    }
    
    public pscl_d k() {
        return this.k;
    }
    
    public pscl_d l() {
        return this.l;
    }
    
    public void a(final int n) throws IOException {
        if (this.g == 22) {
            try {
                this.k.a(this.a, this.c, n);
                this.l.a(this.a, this.c, n);
            }
            catch (Exception ex) {
                throw new IOException(ex.getMessage());
            }
        }
    }
    
    private void n() throws pscl_av, pscl_h {
        final int b;
        if ((b = this.j.b(this.a, 0, this.e, this.a, 0)) > pscl_ar.b - 1024) {
            throw new pscl_ai("Could not decrypt the packet", 2, 20);
        }
        if (this.m) {
            this.n = this.a[b - 1];
            if (this.n > this.j.e()) {
                throw new pscl_ai("Could not decrypt the packet", 2, 20);
            }
            this.e = b - this.n;
            --this.e;
            this.h = this.e;
        }
        else {
            this.e = b;
            this.h = this.e;
        }
    }
    
    private void o() throws pscl_av, pscl_h {
        final byte[] array = new byte[this.d];
        if (this.j.a(this.a, 0, this.e - this.d, array, 0, (byte)this.g, true) != this.d) {
            throw new pscl_ai("MAC data does not match", 2, 20);
        }
        for (int i = 0; i < this.d; ++i) {
            if (array[i] != this.a[this.e - this.d + i]) {
                throw new pscl_ai("MAC data does not match", 2, 20);
            }
        }
        this.e -= this.d;
        this.h = this.e;
    }
    
    public int available() throws IOException {
        if (this.c >= this.e) {
            try {
                if (super.in.available() < 1) {
                    return super.in.available();
                }
            }
            catch (IOException ex) {
                return -1;
            }
            this.b();
        }
        return this.e - this.c;
    }
    
    public void a(final int o, final PrintStream p2) {
        this.o = o;
        this.p = p2;
    }
    
    public InputStream m() {
        return super.in;
    }
    
    public void mark(final int n) {
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void reset() {
    }
    
    static {
        pscl_ar.b = 18432;
    }
}
