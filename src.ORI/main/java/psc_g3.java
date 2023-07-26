import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FilterOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_g3 extends FilterOutputStream
{
    public static int a;
    private byte[] b;
    private int c;
    private int d;
    private int e;
    private int f;
    private psc_c5 g;
    private psc_dw h;
    private psc_da i;
    private psc_da j;
    private int k;
    boolean l;
    byte[] m;
    byte[] n;
    private int o;
    private PrintStream p;
    
    public psc_g3(final OutputStream out, final psc_da i, final psc_da j) {
        super(out);
        this.n = new byte[5];
        this.b = new byte[psc_g3.a + 2048];
        this.c = 0;
        this.i = i;
        this.j = j;
        this.m = new byte[5];
    }
    
    protected void a() throws InterruptedIOException, IOException {
        this.n[0] = (byte)this.f;
        this.n[1] = (byte)(this.e >> 8);
        this.n[2] = (byte)(this.e & 0xFF);
        this.n[3] = (byte)(this.d >> 8);
        this.n[4] = (byte)(this.d & 0xFF);
        super.out.write(this.n, 0, 5);
        if ((this.o & 0x2) == 0x2) {
            this.p.println("[Output] Header: \n" + psc_c9.a(this.n, 0, 5));
        }
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void b(final int e) {
        this.e = e;
    }
    
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, int n, int i) throws InterruptedIOException, IOException {
        if (i < psc_g3.a - this.c) {
            System.arraycopy(array, n, this.b, this.c, i);
            this.c += i;
        }
        else {
            do {
                this.flush();
                if (i <= psc_g3.a - this.c) {
                    System.arraycopy(array, n, this.b, this.c, i);
                    this.c += i;
                    this.d += i;
                    i = 0;
                }
                else {
                    System.arraycopy(array, n, this.b, this.c, psc_g3.a - this.c);
                    i -= psc_g3.a - this.c;
                    n += psc_g3.a - this.c;
                    this.c = psc_g3.a;
                    this.d = psc_g3.a;
                }
            } while (i > 0);
        }
    }
    
    public void write(final int n) throws InterruptedIOException, IOException {
        if (this.c + 1 <= psc_g3.a) {
            this.b[this.c] = (byte)n;
            ++this.c;
        }
        else {
            this.flush();
            this.b[this.c] = (byte)n;
            ++this.c;
        }
    }
    
    public void c(final int n) throws InterruptedIOException, IOException {
        if (this.c + 2 <= psc_g3.a) {
            this.b[this.c + 1] = (byte)n;
            this.b[this.c] = (byte)(n >> 8);
            this.c += 2;
        }
        else {
            this.flush();
            this.b[this.c + 1] = (byte)n;
            this.b[this.c] = (byte)(n >> 8);
            this.c += 2;
        }
    }
    
    public void d(final int n) throws InterruptedIOException, IOException {
        if (this.c + 3 <= psc_g3.a) {
            this.b[this.c + 2] = (byte)n;
            this.b[this.c + 1] = (byte)(n >> 8);
            this.b[this.c] = (byte)(n >> 16);
            this.c += 3;
        }
        else {
            this.flush();
            this.b[this.c + 2] = (byte)n;
            this.b[this.c + 1] = (byte)(n >> 8);
            this.b[this.c] = (byte)(n >> 16);
            this.c += 3;
        }
    }
    
    public void e(final int n) throws InterruptedIOException, IOException {
        if (this.c + 4 <= psc_g3.a) {
            this.b[this.c + 3] = (byte)n;
            this.b[this.c + 2] = (byte)(n >> 8);
            this.b[this.c + 1] = (byte)(n >> 16);
            this.b[this.c] = (byte)(n >> 24);
            this.c += 4;
        }
        else {
            this.flush();
            this.b[this.c + 3] = (byte)n;
            this.b[this.c + 2] = (byte)(n >> 8);
            this.b[this.c + 1] = (byte)(n >> 16);
            this.b[this.c] = (byte)(n >> 24);
            this.c += 4;
        }
    }
    
    public void flush() throws InterruptedIOException, IOException {
        if (this.c == 0) {
            return;
        }
        if ((this.o & 0x2) == 0x2 && (this.o & 0x4) == 0x4) {
            this.p.println("[Output] Data: \n" + psc_c9.a(this.b, 0, this.c));
        }
        this.d = this.c;
        try {
            if (this.f == 22) {
                this.b();
            }
            this.f();
            if ((this.o & 0x2) == 0x2 && (this.o & 0x4) == 0x4) {
                this.p.println("[Output] Compressed: \n" + psc_c9.a(this.b, 0, this.d));
            }
            this.h();
            this.g();
            if ((this.o & 0x2) == 0x2 && (this.o & 0x4) == 0x4) {
                this.p.println("[Output] Ciphertext: \n" + psc_c9.a(this.b, 0, this.d));
            }
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
        this.a();
        super.out.write(this.b, 0, this.d);
        this.c = 0;
        super.out.flush();
    }
    
    public void b() throws IOException {
        try {
            this.i.a(this.b, 0, this.d);
            this.j.a(this.b, 0, this.d);
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
    
    public void a(final psc_dw h) {
        this.h = h;
        if (this.h.s() == 1) {
            this.l = false;
        }
        else {
            this.l = true;
        }
    }
    
    public void a(final psc_c5 g) {
        this.g = g;
    }
    
    public psc_dw c() {
        return this.h;
    }
    
    public psc_c5 d() {
        return this.g;
    }
    
    private void f() throws psc_d, psc_g8 {
        final int a;
        if ((a = this.g.a(this.b, 0, this.d, this.b, 0)) > psc_g3.a + 1024) {
            throw new psc_g8("Compressed data out of boundary");
        }
        this.d = a;
    }
    
    private void g() throws psc_d, psc_g8 {
        if (this.l) {
            final int s = this.h.s();
            this.k = s - this.d % s;
            if (this.d + this.k > psc_g3.a + 2048) {
                throw new psc_g8("Padding data out of boundary");
            }
            for (int i = 0; i < this.k; ++i) {
                this.b[this.d + i] = (byte)(this.k - 1);
            }
            this.d += this.k;
        }
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.b, 0)) > psc_g3.a + 2048) {
            throw new psc_g8("Encrypted data out of boundary");
        }
        this.d = a;
    }
    
    private void h() throws psc_d, psc_g8 {
        this.m[0] = (byte)this.f;
        this.m[1] = 3;
        this.m[2] = 1;
        final int d = this.d;
        this.m[3] = (byte)(d >> 8);
        this.m[4] = (byte)(d & 0xFF);
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.m, this.b, this.d, false)) + this.d > psc_g3.a + 2048) {
            throw new psc_g8("MAC data size out of boundary");
        }
        this.d += a;
    }
    
    public OutputStream e() {
        return super.out;
    }
    
    public void close() throws InterruptedIOException, IOException {
        this.c = 0;
        this.f = 21;
        this.e = 769;
        this.write(1);
        this.write(0);
        this.flush();
        super.out.close();
    }
    
    public void a(final int o, final PrintStream p2) {
        this.o = o;
        this.p = p2;
    }
    
    static {
        psc_g3.a = 14336;
    }
}
