import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.FilterInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hn extends FilterInputStream
{
    protected Socket a;
    private static final int b = 5;
    private byte[] c;
    public static int d;
    int e;
    private int f;
    private int g;
    private int h;
    protected int i;
    private int j;
    private psc_c5 k;
    private psc_dw l;
    psc_da m;
    psc_da n;
    boolean o;
    private int p;
    private int q;
    private PrintStream r;
    public static final int s = 20;
    public static final int t = 21;
    public static final int u = 22;
    public static final int v = 23;
    public static final int w = 255;
    byte[] x;
    boolean y;
    private int z;
    private int aa;
    private boolean ab;
    private boolean ac;
    
    public psc_hn(final Socket socket, final InputStream inputStream, final psc_da psc_da, final psc_da psc_da2) throws IOException, psc_d {
        this(socket, inputStream, psc_da, psc_da2, null);
    }
    
    public psc_hn(final Socket a, final InputStream in, final psc_da m, final psc_da n, final byte[] array) throws IOException, psc_d {
        super(in);
        this.c = null;
        this.g = 0;
        this.j = 0;
        this.x = new byte[5];
        this.a = a;
        this.c = new byte[psc_hn.d];
        this.e = 0;
        this.m = m;
        this.n = n;
        if (array != null) {
            if (array.length != 5) {
                throw new psc_d("Wrong header length");
            }
            System.arraycopy(array, 0, this.x, 0, 5);
            this.y = true;
        }
    }
    
    public psc_hn(final Socket a, final InputStream in, final byte[] array, final psc_da m, final psc_da n) {
        super(in);
        this.c = null;
        this.g = 0;
        this.j = 0;
        this.x = new byte[5];
        this.a = a;
        System.arraycopy(array, 0, this.c = new byte[psc_hn.d], 0, array.length);
        this.e = 0;
        this.g = array.length;
        this.m = m;
        this.n = n;
    }
    
    private void a(final int n, final IOException ex) throws IOException {
        final String x = (n == -1) ? "socket closed prematurely" : ("socket failed: " + ex.getMessage());
        if ((this.q & 0x4) == 0x4) {
            this.r.println(x);
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
            int len = this.aa - this.z;
            if (len > available) {
                len = available;
            }
            final int read = super.in.read(this.c, this.z, len);
            if (read == -1) {
                this.a(-1, (IOException)null);
            }
            this.z += read;
            if (this.z == this.aa) {
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
        if (!this.ac) {
            this.aa = 5;
            this.z = 0;
            this.ac = true;
        }
        if (this.n()) {
            System.arraycopy(this.c, 0, this.x, 0, 5);
            this.y = true;
            this.aa = ((this.x[3] & 0xFF) << 8 | (this.x[4] & 0xFF));
            this.z = 0;
            this.ab = true;
            this.ac = false;
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
                this.j += i;
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
        if (!this.y) {
            int z = 0;
            if (this.ac) {
                z = this.z;
                System.arraycopy(this.c, 0, this.x, 0, z);
                this.ac = false;
            }
            this.a(this.x, z, 5 - z);
        }
        else {
            this.y = false;
        }
        if ((this.q & 0x2) == 0x2) {
            this.r.println("[Input] Header: \n" + psc_c9.a(this.x, 0, 5));
        }
        this.i = (this.x[0] & 0xFF);
        this.h = ((this.x[1] & 0xFF) << 8 | (this.x[2] & 0xFF));
        this.g = ((this.x[3] & 0xFF) << 8 | (this.x[4] & 0xFF));
    }
    
    public int a() throws InterruptedIOException, psc_g8, IOException {
        this.e = 16384;
        if (this.j == this.g) {
            this.p();
            this.j = 0;
        }
        if (this.g > psc_hn.d) {
            throw new psc_g8("Length out of packet boundary");
        }
        if (this.g == 0) {
            return 0;
        }
        try {
            if (this.ab) {
                this.j = this.z;
                this.ab = false;
            }
            this.a(this.c, this.j, this.g - this.j);
        }
        catch (InterruptedIOException ex) {
            throw ex;
        }
        final int n = this.g - this.j;
        this.j = this.g;
        if ((this.q & 0x2) == 0x2 && (this.q & 0x4) == 0x4) {
            this.r.println("[Input] Data: \n" + psc_c9.a(this.c, 0, this.g));
        }
        boolean b = false;
        try {
            this.r();
        }
        catch (psc_c psc_c) {
            b = true;
        }
        try {
            this.s();
            this.q();
            if ((this.q & 0x2) == 0x2 && (this.q & 0x4) == 0x4) {
                this.r.println("[Input] Plaintext: \n" + psc_c9.a(this.c, 0, this.g));
            }
        }
        catch (psc_c psc_c2) {
            b = true;
        }
        finally {
            if (b) {
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
        if ((this.g == 0 || this.e >= this.j) && this.a() < 0) {
            return -1;
        }
        int i = n2;
        int n3 = 0;
        while (i > this.g - this.e) {
            System.arraycopy(this.c, this.e, array, n, this.g - this.e);
            n3 += this.g - this.e;
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, this.g - this.e);
                    this.n.a(this.c, this.e, this.g - this.e);
                }
                catch (Exception ex2) {
                    throw new IOException("Could not digest input");
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
                this.m.a(this.c, this.e, i);
                this.n.a(this.c, this.e, i);
            }
            catch (Exception ex3) {
                throw new IOException("Could not digest input");
            }
        }
        this.e += i;
        return n2;
    }
    
    public int read() throws IOException, InterruptedIOException {
        int n;
        if (this.e + 1 <= this.j) {
            n = (this.c[this.e] & 0xFF);
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, 1);
                    this.n.a(this.c, this.e, 1);
                }
                catch (Exception ex) {
                    throw new IOException("Could not digest input");
                }
            }
            ++this.e;
        }
        else {
            final int a = this.a();
            if (a < 0) {
                return a;
            }
            n = (this.c[this.e] & 0xFF);
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, 1);
                    this.n.a(this.c, this.e, 1);
                }
                catch (Exception ex2) {
                    throw new IOException("Could not digest input");
                }
            }
            ++this.e;
        }
        return n;
    }
    
    public int b() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 2 <= this.j) {
            n = ((this.c[this.e] & 0xFF) << 8 | (this.c[this.e + 1] & 0xFF));
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, 2);
                    this.n.a(this.c, this.e, 2);
                }
                catch (Exception ex) {
                    throw new IOException("Could not digest input");
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
                    this.m.a(this.c, this.e, 2);
                    this.n.a(this.c, this.e, 2);
                }
                catch (Exception ex2) {
                    throw new IOException("Could not digest input");
                }
            }
            this.e += 2;
        }
        return n;
    }
    
    public int c() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 3 <= this.j) {
            n = ((this.c[this.e] & 0xFF) << 16 | (this.c[this.e + 1] & 0xFF) << 8 | (this.c[this.e + 2] & 0xFF));
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, 3);
                    this.n.a(this.c, this.e, 3);
                }
                catch (Exception ex) {
                    throw new IOException("Could not digest input");
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
                    this.m.a(this.c, this.e, 3);
                    this.n.a(this.c, this.e, 3);
                }
                catch (Exception ex2) {
                    throw new IOException("Could not digest input");
                }
            }
            this.e += 3;
        }
        return n;
    }
    
    public int d() throws IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.e + 4 <= this.j) {
            n = ((this.c[this.e] & 0xFF) << 24 | (this.c[this.e + 1] & 0xFF) << 16 | (this.c[this.e + 2] & 0xFF) << 8 | (this.c[this.e + 3] & 0xFF));
            if (this.i == 22) {
                try {
                    this.m.a(this.c, this.e, 4);
                    this.n.a(this.c, this.e, 4);
                }
                catch (Exception ex) {
                    throw new IOException("Could not digest input");
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
                    this.m.a(this.c, this.e, 4);
                    this.n.a(this.c, this.e, 4);
                }
                catch (Exception ex2) {
                    throw new IOException("Could not digest input");
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
        if (this.e >= this.g) {
            this.a();
        }
        return this.i;
    }
    
    public int g() {
        return this.g;
    }
    
    public void a(final psc_dw l) {
        this.l = l;
        this.f = this.l.h();
        if (this.l.s() == 1) {
            this.o = false;
        }
        else {
            this.o = true;
        }
    }
    
    public void a(final psc_c5 k) {
        this.k = k;
    }
    
    public psc_dw h() {
        return this.l;
    }
    
    public psc_c5 i() {
        return this.k;
    }
    
    public psc_da j() {
        return this.m;
    }
    
    public psc_da k() {
        return this.n;
    }
    
    private void q() throws psc_g8, psc_d {
        final int b;
        if ((b = this.k.b(this.c, 0, this.g, this.c, 0)) > psc_hn.d - 2048) {
            throw new psc_c("decompressed data out of boundary", 2, 20);
        }
        this.g = b;
        this.j = this.g;
    }
    
    private void r() throws psc_g8, psc_d {
        final int b;
        if ((b = this.l.b(this.c, 0, this.g, this.c, 0)) > psc_hn.d - 1024) {
            throw new psc_c("Could not decrypt the packet", 2, 20);
        }
        if (this.o) {
            this.p = this.c[b - 1];
            if (this.p > this.l.s()) {
                throw new psc_c("Could not decrypt the packet", 2, 20);
            }
            this.g = b - this.p;
            --this.g;
            this.j = this.g;
        }
        else {
            this.g = b;
            this.j = this.g;
        }
    }
    
    private void s() throws psc_g8, psc_d {
        final byte[] array = new byte[this.f];
        if (this.l.a(this.c, 0, this.g - this.f, array, 0, (byte)this.i, true) != this.f) {
            throw new psc_c("MAC data does not match", 2, 20);
        }
        for (int i = 0; i < this.f; ++i) {
            if (array[i] != this.c[this.g - this.f + i]) {
                throw new psc_c("MAC data does not match", 2, 20);
            }
        }
        this.g -= this.f;
        this.j = this.g;
    }
    
    public int available() throws IOException {
        if (this.e >= this.g) {
            try {
                if (!this.y && !this.o()) {
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
    
    public void a(final int q, final PrintStream r) {
        this.q = q;
        this.r = r;
    }
    
    public InputStream l() {
        return super.in;
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
        psc_hn.d = 18432;
    }
}
