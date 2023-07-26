import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.FilterInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_g2 extends FilterInputStream
{
    protected Socket a;
    private static final int b = 5;
    private byte[] c;
    public static int d;
    int e;
    int f;
    private int g;
    private int h;
    int i;
    private psc_c5 j;
    private psc_dw k;
    private psc_da l;
    private psc_da m;
    boolean n;
    int o;
    byte[] p;
    public static final int q = 20;
    public static final int r = 21;
    public static final int s = 22;
    public static final int t = 23;
    public static final int u = 255;
    private int v;
    private PrintStream w;
    private int x;
    byte[] y;
    boolean z;
    private int aa;
    private int ab;
    private boolean ac;
    private boolean ad;
    
    public psc_g2(final Socket socket, final InputStream inputStream, final psc_da psc_da, final psc_da psc_da2) throws psc_d {
        this(socket, inputStream, psc_da, psc_da2, null);
    }
    
    public psc_g2(final Socket a, final InputStream in, final psc_da l, final psc_da m, final byte[] array) throws psc_d {
        super(in);
        this.c = null;
        this.g = 0;
        this.y = new byte[5];
        this.a = a;
        this.c = new byte[psc_g2.d];
        this.e = 0;
        this.l = l;
        this.m = m;
        if (array != null) {
            if (array.length != 5) {
                throw new psc_d("Wrong header length");
            }
            System.arraycopy(array, 0, this.y, 0, 5);
            this.z = true;
        }
        this.p = new byte[5];
    }
    
    public psc_g2(final Socket a, final InputStream in, final byte[] array, final psc_da l, final psc_da m) throws Exception {
        super(in);
        this.c = null;
        this.g = 0;
        this.y = new byte[5];
        this.a = a;
        System.arraycopy(array, 0, this.c = new byte[psc_g2.d], 0, array.length);
        this.e = 0;
        this.g = array.length;
        this.l = l;
        this.m = m;
    }
    
    private void a(final int n, final IOException ex) throws IOException {
        final String x = (n == -1) ? "socket closed prematurely" : ("socket failed: " + ex.getMessage());
        if ((this.v & 0x4) == 0x4) {
            this.w.println(x);
        }
        throw new psc_b(x, 1, 0);
    }
    
    private boolean n() throws IOException {
        try {
            final int available = super.in.available();
            if (available == 0) {
                return false;
            }
            if (available == -1) {
                this.a(-1, (IOException)null);
            }
            int len = this.ab - this.aa;
            if (len > available) {
                len = available;
            }
            final int read = super.in.read(this.c, this.aa, len);
            if (read == -1) {
                this.a(-1, (IOException)null);
            }
            this.aa += read;
            if (this.aa == this.ab) {
                return true;
            }
        }
        catch (InterruptedIOException ex2) {
            return false;
        }
        catch (IOException ex) {
            this.a(0, ex);
        }
        return false;
    }
    
    private boolean o() throws IOException {
        if (!this.ad) {
            this.ab = 5;
            this.aa = 0;
            this.ad = true;
        }
        if (this.n()) {
            System.arraycopy(this.c, 0, this.y, 0, 5);
            this.z = true;
            this.ab = ((this.y[3] & 0xFF) << 8 | (this.y[4] & 0xFF));
            this.aa = 0;
            this.ac = true;
            this.ad = false;
            return true;
        }
        return false;
    }
    
    private void a(final byte[] array, final int n, final int n2) throws IOException {
        int i = 0;
        int a = 0;
        IOException ex = null;
        while (i < n2) {
            try {
                a = 0;
                a = psc_gy.a(this.a, super.in, array, n + i, n2 - i);
            }
            catch (InterruptedIOException ex2) {
                this.x += i;
                ex2.bytesTransferred = 0;
                throw ex2;
            }
            catch (IOException ex3) {
                ex = ex3;
            }
            if (a == -1 || ex != null) {
                this.a(a, ex);
            }
            else {
                i += a;
            }
        }
    }
    
    private void p() throws IOException, InterruptedIOException {
        if (!this.z) {
            int aa = 0;
            if (this.ad) {
                aa = this.aa;
                System.arraycopy(this.c, 0, this.y, 0, aa);
                this.ad = false;
            }
            this.a(this.y, aa, 5 - aa);
        }
        else {
            this.z = false;
        }
        if ((this.v & 0x2) == 0x2) {
            this.w.println("[Input] Header: \n" + psc_c9.a(this.y, 0, 5));
        }
        this.i = (this.y[0] & 0xFF);
        this.h = ((this.y[1] & 0xFF) << 8 | (this.y[2] & 0xFF));
        this.g = ((this.y[3] & 0xFF) << 8 | (this.y[4] & 0xFF));
    }
    
    public int a() throws InterruptedIOException, psc_g8, IOException {
        this.e = 16384;
        if (this.x == this.g) {
            this.p();
            this.x = 0;
        }
        if (this.g > psc_g2.d) {
            throw new psc_g8("Length out of packet boundary");
        }
        if (this.g == 0) {
            return 0;
        }
        try {
            if (this.ac) {
                this.x = this.aa;
                this.ac = false;
            }
            this.a(this.c, this.x, this.g - this.x);
        }
        catch (InterruptedIOException ex) {
            throw ex;
        }
        final int n = this.g - this.x;
        this.x = this.g;
        if ((this.v & 0x2) == 0x2 && (this.v & 0x4) == 0x4) {
            this.w.println("[Input] Data: \n" + psc_c9.a(this.c, 0, this.g));
        }
        int n2 = 0;
        try {
            this.r();
        }
        catch (psc_c psc_c) {
            n2 = 1;
        }
        try {
            this.s();
            this.q();
            if ((this.v & 0x2) == 0x2 && (this.v & 0x4) == 0x4) {
                this.w.println("[Input] Plaintext: \n" + psc_c9.a(this.c, 0, this.g));
            }
            if (n2 != 0) {
                throw new psc_c("MAC data does not match", 2, 20);
            }
        }
        catch (psc_c psc_c2) {
            n2 = 1;
        }
        catch (Exception ex2) {
            throw new IOException("readBuffer() error: " + ex2.getMessage());
        }
        finally {
            if (n2 != 0) {
                throw new psc_c("MAC data does not match", 2, 20);
            }
        }
        this.e = 0;
        if (this.i == 21) {
            final int read = this.read();
            final int read2 = this.read();
            throw new psc_b("An Alert was received: " + psc_b.a(read2), read, read2);
        }
        return n;
    }
    
    public int read(final byte[] array, int n, final int n2) throws IOException, psc_g8, InterruptedIOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("Invalid input parameter(s)");
        }
        if (n2 == 0) {
            return n2;
        }
        if ((this.g == 0 || this.e >= this.x) && this.a() < 0) {
            return -1;
        }
        int i = n2;
        int n3 = 0;
        while (i > this.g - this.e) {
            System.arraycopy(this.c, this.e, array, n, this.g - this.e);
            n3 += this.g - this.e;
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, this.g - this.e);
                    this.m.a(this.c, this.e, this.g - this.e);
                }
                catch (Exception ex2) {
                    throw new IOException("Can not digest message");
                }
            }
            i -= this.g - this.e;
            n += this.g - this.e;
            this.e = this.g;
            if (super.in.available() <= 0) {
                return n2 - i;
            }
            try {
                if (this.a() < 0) {
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
        System.arraycopy(this.c, this.e, array, n, i);
        if (this.i == 22) {
            try {
                this.l.a(this.c, this.e, i);
                this.m.a(this.c, this.e, i);
            }
            catch (Exception ex3) {
                throw new IOException("Can not digest message");
            }
        }
        this.e += i;
        return n2;
    }
    
    public int read() throws IOException, InterruptedIOException {
        int n;
        if (this.e + 1 <= this.x) {
            n = (this.c[this.e] & 0xFF);
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 1);
                    this.m.a(this.c, this.e, 1);
                }
                catch (Exception ex2) {
                    throw new IOException("Can not digest message");
                }
            }
            ++this.e;
        }
        else {
            try {
                final int a = this.a();
                if (a < 0) {
                    return a;
                }
                n = (this.c[this.e] & 0xFF);
                if (this.i == 22) {
                    try {
                        this.l.a(this.c, this.e, 1);
                        this.m.a(this.c, this.e, 1);
                    }
                    catch (Exception ex3) {
                        throw new IOException("Can not digest message");
                    }
                }
                ++this.e;
            }
            catch (psc_c psc_c) {
                throw psc_c;
            }
            catch (InterruptedIOException ex) {
                throw ex;
            }
            catch (Exception ex4) {
                throw new IOException("read() error");
            }
        }
        return n;
    }
    
    public int b() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 2 <= this.x) {
            n = ((this.c[this.e] & 0xFF) << 8 | (this.c[this.e + 1] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 2);
                    this.m.a(this.c, this.e, 2);
                }
                catch (Exception ex) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 2;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint16() error");
            }
            n = ((this.c[this.e] & 0xFF) << 8 | (this.c[this.e + 1] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 2);
                    this.m.a(this.c, this.e, 2);
                }
                catch (Exception ex2) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 2;
        }
        return n;
    }
    
    public int c() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 3 <= this.x) {
            n = ((this.c[this.e] & 0xFF) << 16 | (this.c[this.e + 1] & 0xFF) << 8 | (this.c[this.e + 2] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 3);
                    this.m.a(this.c, this.e, 3);
                }
                catch (Exception ex) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 3;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint24() error");
            }
            n = ((this.c[this.e] & 0xFF) << 16 | (this.c[this.e + 1] & 0xFF) << 8 | (this.c[this.e + 2] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 3);
                    this.m.a(this.c, this.e, 3);
                }
                catch (Exception ex2) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 3;
        }
        return n;
    }
    
    public int d() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 4 <= this.x) {
            n = ((this.c[this.e] & 0xFF) << 24 | (this.c[this.e + 1] & 0xFF) << 16 | (this.c[this.e + 2] & 0xFF) << 8 | (this.c[this.e + 3] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 4);
                    this.m.a(this.c, this.e, 4);
                }
                catch (Exception ex) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 4;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint32()");
            }
            n = ((this.c[this.e] & 0xFF) << 24 | (this.c[this.e + 1] & 0xFF) << 16 | (this.c[this.e + 2] & 0xFF) << 8 | (this.c[this.e + 3] & 0xFF));
            if (this.i == 22) {
                try {
                    this.l.a(this.c, this.e, 4);
                    this.m.a(this.c, this.e, 4);
                }
                catch (Exception ex2) {
                    throw new IOException("Can not digest message");
                }
            }
            this.e += 4;
        }
        return n;
    }
    
    public int e() {
        return this.h;
    }
    
    public int f() throws IOException {
        if (this.e == this.g) {
            this.a();
        }
        return this.i;
    }
    
    public int g() {
        return this.g;
    }
    
    public void a(final psc_dw k) {
        this.k = k;
        this.f = this.k.h();
        if (this.k.s() == 1) {
            this.n = false;
        }
        else {
            this.n = true;
        }
    }
    
    public void a(final psc_c5 j) {
        this.j = j;
    }
    
    public psc_dw h() {
        return this.k;
    }
    
    public psc_c5 i() {
        return this.j;
    }
    
    public psc_da j() {
        return this.l;
    }
    
    public psc_da k() {
        return this.m;
    }
    
    private void q() throws psc_g8, psc_d {
        final int b;
        if ((b = this.j.b(this.c, 0, this.g, this.c, 0)) > psc_g2.d - 2048) {
            throw new psc_c("Could not decrompress the data", 2, 20);
        }
        this.g = b;
        this.x = this.g;
    }
    
    private void r() throws psc_g8, psc_d {
        final int b;
        if ((b = this.k.b(this.c, 0, this.g, this.c, 0)) > psc_g2.d - 1024) {
            throw new psc_c("Could not decrypt the ciphertext", 2, 20);
        }
        if (this.n) {
            this.o = this.c[b - 1];
            if (this.o > this.k.s()) {
                throw new psc_c("Could not decrypt the packet", 2, 20);
            }
            this.g = b - (this.o + 1);
            this.x = this.g;
        }
        else {
            this.g = b;
            this.x = this.g;
        }
    }
    
    private void s() throws psc_g8, psc_d {
        final byte[] array = new byte[this.f];
        this.p[0] = (byte)this.i;
        this.p[1] = 3;
        this.p[2] = 1;
        final int n = this.g - this.f;
        this.p[3] = (byte)(n >> 8);
        this.p[4] = (byte)(n & 0xFF);
        if (this.k.a(this.c, 0, this.g - this.f, this.p, array, 0, true) != this.f) {
            throw new psc_c("MAC data does not match", 2, 20);
        }
        for (int i = 0; i < this.f; ++i) {
            if (array[i] != this.c[this.g - this.f + i]) {
                throw new psc_c("MAC data does not match", 2, 20);
            }
        }
        this.g -= this.f;
        this.x = this.g;
    }
    
    public InputStream l() {
        return super.in;
    }
    
    public int available() throws IOException {
        if (this.e >= this.g) {
            try {
                if (!this.z && !this.o()) {
                    return 0;
                }
                if (!this.n()) {
                    return 0;
                }
            }
            catch (IOException ex) {
                return -1;
            }
            this.a();
        }
        return this.g - this.e;
    }
    
    public void a(final int v, final PrintStream w) {
        this.v = v;
        this.w = w;
    }
    
    public void mark(final int n) {
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void reset() {
    }
    
    public Socket m() {
        return this.a;
    }
    
    static {
        psc_g2.d = 18432;
    }
}
