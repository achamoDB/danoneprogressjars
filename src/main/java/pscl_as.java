import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FilterOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_as extends FilterOutputStream
{
    public static int a;
    private byte[] b;
    private int c;
    private int d;
    private int e;
    public int f;
    private pscl_bn g;
    private pscl_i h;
    private int i;
    private pscl_d j;
    private pscl_d k;
    public boolean l;
    public byte[] m;
    private int n;
    private PrintStream o;
    
    public pscl_as(final OutputStream out, final pscl_d j, final pscl_d k) {
        super(out);
        this.e = 768;
        this.m = new byte[5];
        this.b = new byte[pscl_as.a + 2048];
        this.c = 0;
        this.j = j;
        this.k = k;
    }
    
    public void a() throws IOException {
        this.m[0] = (byte)this.f;
        this.m[1] = (byte)(this.e >> 8);
        this.m[2] = (byte)(this.e & 0xFF);
        this.m[3] = (byte)(this.d >> 8);
        this.m[4] = (byte)(this.d & 0xFF);
        super.out.write(this.m, 0, 5);
        if ((this.n & 0x2) == 0x2) {
            this.o.println("[Output] Header: \n" + pscl_k.a(this.m, 0, 5));
        }
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void b(final int e) {
        this.e = e;
    }
    
    public void write(final byte[] array, int n, int i) throws IOException {
        if (i <= pscl_as.a - this.c) {
            System.arraycopy(array, n, this.b, this.c, i);
            this.c += i;
        }
        else {
            do {
                this.flush();
                if (i <= pscl_as.a - this.c) {
                    System.arraycopy(array, n, this.b, this.c, i);
                    this.c += i;
                    this.d = this.c;
                    i = 0;
                }
                else {
                    System.arraycopy(array, n, this.b, this.c, pscl_as.a - this.c);
                    i -= pscl_as.a - this.c;
                    n += pscl_as.a - this.c;
                    this.c = pscl_as.a;
                    this.d = pscl_as.a;
                }
            } while (i > 0);
        }
    }
    
    public void write(final int n) throws IOException {
        if (this.c + 1 <= pscl_as.a) {
            this.b[this.c] = (byte)n;
            ++this.c;
        }
        else {
            this.flush();
            this.b[this.c] = (byte)n;
            ++this.c;
        }
    }
    
    public void c(final int n) throws IOException {
        if (this.c + 2 <= pscl_as.a) {
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
    
    public void d(final int n) throws IOException {
        if (this.c + 3 <= pscl_as.a) {
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
    
    public void e(final int n) throws IOException {
        if (this.c + 4 <= pscl_as.a) {
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
    
    public void flush() throws IOException {
        if (this.c == 0) {
            return;
        }
        this.d = this.c;
        if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
            this.o.println("[Output] Data: \n" + pscl_k.a(this.b, 0, this.d));
        }
        try {
            if (this.f == 22) {
                this.b();
            }
            this.j();
            this.i();
            if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
                this.o.println("[Output] Ciphertext: \n" + pscl_k.a(this.b, 0, this.d));
            }
        }
        catch (Exception ex) {
            return;
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
    
    public void a(final pscl_i h) {
        this.h = h;
        if (this.h.e() == 1) {
            this.l = false;
        }
        else {
            this.l = true;
        }
    }
    
    public void a(final pscl_bn g) {
        this.g = g;
    }
    
    public pscl_i c() {
        return this.h;
    }
    
    public pscl_bn d() {
        return this.g;
    }
    
    private void h() throws pscl_h, pscl_av {
        final int a;
        if ((a = this.g.a(this.b, 0, this.d, this.b, 0)) > pscl_as.a + 1024) {
            throw new pscl_av("Compressed data out of boundary");
        }
        this.d = a;
    }
    
    private void i() throws pscl_h, pscl_av {
        if (this.l) {
            final int e = this.h.e();
            this.i = e - this.d % e;
            if (this.d + this.i + 1 > pscl_as.a + 2048) {
                throw new pscl_av("Padding data out of boundary");
            }
            for (int i = 0; i < this.i; ++i) {
                this.b[this.d + i] = (byte)(this.i - 1);
            }
            this.d += this.i;
        }
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.b, 0)) > pscl_as.a + 2048) {
            throw new pscl_av("Encrypted data out of boundary");
        }
        this.d = a;
    }
    
    private void j() throws pscl_h, pscl_av {
        final int a;
        if ((a = this.h.a(this.b, 0, this.d, this.b, this.d, (byte)this.f, false)) + this.d > pscl_as.a + 2048) {
            throw new pscl_av("MAC data size out of boundary");
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
    
    public void close() throws IOException {
        this.c = 0;
        this.f = 21;
        this.e = 768;
        this.write(1);
        this.write(0);
        this.flush();
        super.out.close();
    }
    
    public pscl_d f() {
        return this.j;
    }
    
    public pscl_d g() {
        return this.k;
    }
    
    static {
        pscl_as.a = 14336;
    }
}
