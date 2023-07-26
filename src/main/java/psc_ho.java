import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FilterOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ho extends FilterOutputStream
{
    public static int a;
    private byte[] b;
    private int c;
    private int d;
    private int e;
    protected int f;
    private psc_c5 g;
    private psc_dw h;
    private int i;
    private psc_da j;
    private psc_da k;
    boolean l;
    byte[] m;
    private int n;
    private PrintStream o;
    
    public psc_ho(final OutputStream out, final psc_da j, final psc_da k) {
        super(out);
        this.e = 768;
        this.m = new byte[5];
        this.b = new byte[psc_ho.a + 2048];
        this.c = 0;
        this.j = j;
        this.k = k;
    }
    
    protected void a() throws InterruptedIOException, IOException {
        this.m[0] = (byte)this.f;
        this.m[1] = (byte)(this.e >> 8);
        this.m[2] = (byte)(this.e & 0xFF);
        this.m[3] = (byte)(this.d >> 8);
        this.m[4] = (byte)(this.d & 0xFF);
        super.out.write(this.m, 0, 5);
        if ((this.n & 0x2) == 0x2) {
            this.o.println("[Output] Header: \n" + psc_c9.a(this.m, 0, 5));
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
        if (i <= psc_ho.a - this.c) {
            System.arraycopy(array, n, this.b, this.c, i);
            this.c += i;
        }
        else {
            do {
                this.flush();
                if (i <= psc_ho.a - this.c) {
                    System.arraycopy(array, n, this.b, this.c, i);
                    this.c += i;
                    this.d = this.c;
                    i = 0;
                }
                else {
                    System.arraycopy(array, n, this.b, this.c, psc_ho.a - this.c);
                    i -= psc_ho.a - this.c;
                    n += psc_ho.a - this.c;
                    this.c = psc_ho.a;
                    this.d = psc_ho.a;
                }
            } while (i > 0);
        }
    }
    
    public void write(final int n) throws InterruptedIOException, IOException {
        if (this.c + 1 <= psc_ho.a) {
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
        if (this.c + 2 <= psc_ho.a) {
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
        if (this.c + 3 <= psc_ho.a) {
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
        if (this.c + 4 <= psc_ho.a) {
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
        this.d = this.c;
        if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
            this.o.println("[Output] Data: \n" + psc_c9.a(this.b, 0, this.d));
        }
        try {
            if (this.f == 22) {
                this.b();
            }
            this.f();
            this.h();
            this.g();
            if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
                this.o.println("[Output] Ciphertext: \n" + psc_c9.a(this.b, 0, this.d));
            }
        }
        catch (Exception ex) {
            throw new IOException("packet output failed: " + ex.getMessage());
        }
        this.a();
        super.out.write(this.b, 0, this.d);
        this.c = 0;
        super.out.flush();
    }
    
    public void b() throws IOException {
        try {
            this.j.a(this.b, 0, this.d);
            this.k.a(this.b, 0, this.d);
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
        if ((a = this.g.a(this.b, 0, this.d, this.b, 0)) > psc_ho.a + 1024) {
            throw new psc_g8("Compressed data out of boundary");
        }
        this.d = a;
    }
    
    private void g() throws psc_d, psc_g8 {
        if (this.l) {
            final int s = this.h.s();
            this.i = s - this.d % s;
            if (this.d + this.i + 1 > psc_ho.a + 2048) {
                throw new psc_g8("Padding data out of boundary");
            }
            for (int i = 0; i < this.i; ++i) {
                this.b[this.d + i] = (byte)(this.i - 1);
            }
            this.d += this.i;
        }
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.b, 0)) > psc_ho.a + 2048) {
            throw new psc_g8("Encrypted data out of boundary");
        }
        this.d = a;
    }
    
    private void h() throws psc_d, psc_g8 {
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.b, this.d, (byte)this.f, false)) + this.d > psc_ho.a + 2048) {
            throw new psc_g8("MAC data size out of boundary");
        }
        this.d += a;
    }
    
    public void a(final int n, final PrintStream o) {
        this.n = n;
        this.o = o;
    }
    
    public OutputStream e() {
        return super.out;
    }
    
    public void close() throws InterruptedIOException, IOException {
        this.c = 0;
        this.f = 21;
        this.e = 768;
        this.write(1);
        this.write(0);
        this.flush();
        super.out.close();
    }
    
    static {
        psc_ho.a = 14336;
    }
}
