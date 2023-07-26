import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FilterOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h7 extends FilterOutputStream
{
    public static int a;
    public static int b;
    public static int c;
    private int d;
    private byte[] e;
    private int f;
    private int g;
    private int h;
    private long i;
    private psc_dw j;
    private int k;
    boolean l;
    byte[] m;
    int n;
    PrintStream o;
    
    public psc_h7(final OutputStream out, final long i) {
        super(out);
        this.e = new byte[psc_h7.a];
        this.f = 0;
        this.m = new byte[3];
        this.i = i;
    }
    
    protected void a() throws InterruptedIOException, IOException {
        final int g = this.g;
        if (this.l) {
            this.m[0] = (byte)(g >> 8 & 0x3F);
            this.m[1] = (byte)(this.g & 0xFF);
            this.m[2] = (byte)(this.k & 0xFF);
            if ((this.n & 0x2) == 0x2) {
                this.o.println("[Output] Header: \n" + psc_c9.a(this.m, 0, 3));
            }
            super.out.write(this.m, 0, 3);
        }
        else {
            this.m[0] = (byte)(g >> 8 | 0x80);
            this.m[1] = (byte)(this.g & 0xFF);
            if ((this.n & 0x2) == 0x2) {
                this.o.println("[Output] Header: \n" + psc_c9.a(this.m, 0, 2));
            }
            super.out.write(this.m, 0, 2);
        }
    }
    
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, int n, int i) throws InterruptedIOException, IOException {
        if (i <= this.d - this.f) {
            System.arraycopy(array, n, this.e, this.f, i);
            this.f += i;
            this.g = this.f;
        }
        else {
            do {
                this.flush();
                if (i <= this.d - this.f) {
                    System.arraycopy(array, n, this.e, this.f, i);
                    this.g = this.f + i;
                    this.f += i;
                    i = 0;
                }
                else {
                    System.arraycopy(array, n, this.e, this.f, this.d - this.f);
                    i -= this.d - this.f;
                    n += this.d - this.f;
                    this.f = this.d;
                    this.g = this.d;
                }
            } while (i > 0);
        }
    }
    
    public void write(final int n) throws InterruptedIOException, IOException {
        if (this.f + 1 <= this.d) {
            this.e[this.f] = (byte)n;
            ++this.f;
        }
        else {
            this.flush();
            this.e[this.f] = (byte)n;
            ++this.f;
        }
    }
    
    public void a(final int n) throws InterruptedIOException, IOException {
        if (this.f + 2 <= this.d) {
            this.e[this.f + 1] = (byte)n;
            this.e[this.f] = (byte)(n >> 8);
            this.f += 2;
        }
        else {
            this.flush();
            this.e[this.f + 1] = (byte)n;
            this.e[this.f] = (byte)(n >> 8);
            this.f += 2;
        }
    }
    
    public void b(final int n) throws InterruptedIOException, IOException {
        if (this.f + 3 <= this.d) {
            this.e[this.f + 2] = (byte)n;
            this.e[this.f + 1] = (byte)(n >> 8);
            this.e[this.f] = (byte)(n >> 16);
            this.f += 3;
        }
        else {
            this.flush();
            this.e[this.f + 2] = (byte)n;
            this.e[this.f + 1] = (byte)(n >> 8);
            this.e[this.f] = (byte)(n >> 16);
            this.f += 3;
        }
    }
    
    public void c(final int n) throws InterruptedIOException, IOException {
        if (this.f + 4 <= this.d) {
            this.e[this.f + 3] = (byte)n;
            this.e[this.f + 2] = (byte)(n >> 8);
            this.e[this.f + 1] = (byte)(n >> 16);
            this.e[this.f] = (byte)(n >> 24);
            this.f += 4;
        }
        else {
            this.flush();
            this.e[this.f + 3] = (byte)n;
            this.e[this.f + 2] = (byte)(n >> 8);
            this.e[this.f + 1] = (byte)(n >> 16);
            this.e[this.f] = (byte)(n >> 24);
            this.f += 4;
        }
    }
    
    public void flush() throws InterruptedIOException, IOException {
        if (this.f == this.h) {
            return;
        }
        this.g = this.f;
        try {
            this.e();
            if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
                this.o.println("[Output] Data: \n" + psc_c9.a(this.e, 0, this.g));
            }
            this.d();
            if ((this.n & 0x2) == 0x2 && (this.n & 0x4) == 0x4) {
                this.o.println("[Output] Ciphertext: \n" + psc_c9.a(this.e, 0, this.g));
            }
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
        this.a();
        super.out.write(this.e, 0, this.g);
        this.f = this.h;
        super.out.flush();
    }
    
    public void a(final psc_dw j) {
        this.j = j;
        if (this.j.s() == 1) {
            this.l = false;
            this.d = psc_h7.a;
        }
        else {
            this.l = true;
            this.d = psc_h7.c - psc_h7.b;
        }
        this.h = this.j.h();
        this.f = this.h;
    }
    
    public psc_dw b() {
        return this.j;
    }
    
    private void d() throws psc_d, psc_g8 {
        final int a;
        if ((a = this.j.a(this.e, 0, this.g, this.e, 0)) > this.d + this.k) {
            throw new psc_g8("Encrypted data out of boundary");
        }
        this.g = a;
    }
    
    private void e() throws psc_d, psc_g8 {
        if (this.l) {
            final int s = this.j.s();
            this.k = s - this.g % s;
            for (int i = 0; i < this.k; ++i) {
                this.e[this.g + i] = (byte)this.k;
            }
            this.g += this.k;
        }
        if (this.j.a(this.e, this.h, this.g - this.h, this.e, 0, false, this.i) > this.h) {
            throw new psc_g8("MAC data out of boundary");
        }
        ++this.i;
    }
    
    public long c() {
        return this.i;
    }
    
    public void a(final int n, final PrintStream o) {
        this.n = n;
        this.o = o;
    }
    
    static {
        psc_h7.a = 32767;
        psc_h7.b = 255;
        psc_h7.c = 16383;
    }
}
