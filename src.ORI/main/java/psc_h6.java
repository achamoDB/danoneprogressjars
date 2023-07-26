import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.FilterInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h6 extends FilterInputStream
{
    protected Socket a;
    private static final int b = 2;
    private byte[] c;
    public static int d;
    public static int e;
    public static int f;
    public static int g;
    public static int h;
    int i;
    int j;
    boolean k;
    private int l;
    private int m;
    private int n;
    private long o;
    private psc_dw p;
    int q;
    PrintStream r;
    private int s;
    byte[] t;
    boolean u;
    private int v;
    private int w;
    private int x;
    private boolean y;
    private boolean z;
    
    public psc_h6(final Socket a, final InputStream in, final long o) throws IOException {
        super(in);
        this.c = null;
        this.t = new byte[2];
        this.a = a;
        this.c = new byte[psc_h6.f];
        this.j = 0;
        this.n = 0;
        this.o = o;
    }
    
    public psc_h6(final Socket a, final InputStream in, final byte[] array, final long o) throws IOException {
        super(in);
        this.c = null;
        this.t = new byte[2];
        this.a = a;
        this.c = new byte[psc_h6.f];
        this.j = 0;
        this.n = 0;
        this.o = o;
        if (array != null) {
            if (array.length != 2) {
                throw new psc_d("Wrong header length");
            }
            System.arraycopy(array, 0, this.t, 0, 2);
            this.u = true;
        }
    }
    
    private void a(final int n, final IOException ex, final boolean b) throws IOException {
        final String s = (n == -1) ? "socket closed prematurely" : ("socket failed: " + ex.getMessage());
        if ((this.q & 0x4) == 0x4) {
            this.r.println(s);
        }
        if (b) {
            throw new IOException(s);
        }
    }
    
    private boolean k() throws IOException {
        try {
            final int available = super.in.available();
            if (available == 0) {
                return false;
            }
            if (available == -1) {
                this.a(-1, null, true);
            }
            int len = this.w - this.v;
            if (len > available) {
                len = available;
            }
            final int read = super.in.read(this.c, this.v, len);
            if (read == -1) {
                this.a(-1, null, true);
            }
            this.v += read;
            if (this.v == this.w) {
                return true;
            }
        }
        catch (InterruptedIOException ex2) {
            return false;
        }
        catch (IOException ex) {
            this.a(0, ex, true);
        }
        return false;
    }
    
    private boolean l() throws IOException {
        if (!this.z) {
            this.w = 3;
            this.v = 0;
            this.z = true;
        }
        if (this.k()) {
            System.arraycopy(this.c, 0, this.t, 0, 2);
            this.u = true;
            if ((this.t[0] & 0x80) == 0x80) {
                this.w = ((this.t[0] & 0x7F) << 8 | (this.t[1] & 0xFF));
                this.x = 0;
                this.c[0] = this.c[2];
                this.v = 1;
            }
            else {
                this.w = ((this.t[0] & 0x3F) << 8 | (this.t[1] & 0xFF));
                this.x = this.c[2];
                this.v = 0;
            }
            this.y = true;
            this.z = false;
            return true;
        }
        return false;
    }
    
    private int a(final byte[] array, final int n, final int n2) throws IOException {
        int i = 0;
        int a = 0;
        IOException ex = null;
        while (i < n2) {
            try {
                a = 0;
                a = psc_gy.a(this.a, super.in, array, n + i, n2 - i);
            }
            catch (InterruptedIOException ex2) {
                this.s += i;
                ex2.bytesTransferred = 0;
                throw ex2;
            }
            catch (IOException ex3) {
                ex = ex3;
            }
            if (a == -1 || ex != null) {
                this.a(a, ex, false);
                return -1;
            }
            i += a;
        }
        return i;
    }
    
    private int m() throws IOException, InterruptedIOException {
        if (!this.u) {
            int v = 0;
            if (this.z) {
                v = this.v;
                System.arraycopy(this.c, 0, this.t, 0, v);
                this.z = false;
            }
            if (this.a(this.t, v, 2 - v) == -1) {
                return -1;
            }
        }
        else {
            this.u = false;
        }
        if ((this.q & 0x2) == 0x2) {
            this.r.println("[Input] Header: \n" + psc_c9.a(this.t, 0, 2));
        }
        if ((this.t[0] & 0x80) == 0x80) {
            this.l = ((this.t[0] & 0x7F) << 8 | (this.t[1] & 0xFF));
            this.k = false;
            this.m = 0;
            this.i = psc_h6.f;
            return 2;
        }
        this.l = ((this.t[0] & 0x3F) << 8 | (this.t[1] & 0xFF));
        this.k = true;
        if (this.y) {
            this.m = this.x;
        }
        else {
            this.m = psc_gy.a(this.a, super.in);
        }
        if (this.m == -1) {
            return -1;
        }
        if ((this.q & 0x2) == 0x2) {
            this.r.println("[Input] Padding: " + psc_c9.a(this.m, 1));
        }
        this.i = psc_h6.h - psc_h6.g;
        return 3;
    }
    
    public int a() throws psc_b, psc_g8, IOException, InterruptedIOException {
        this.j = 65535;
        int m = 0;
        if (this.s == this.l) {
            m = this.m();
            this.s = 0;
        }
        if (m == -1) {
            return -1;
        }
        if (this.l > this.i) {
            throw new psc_g8("Length out of packet boundary");
        }
        if (this.y) {
            this.s = this.v;
            this.y = false;
        }
        int a;
        try {
            a = this.a(this.c, this.s, this.l - this.s);
            if (a == -1) {
                return -1;
            }
        }
        catch (InterruptedIOException ex) {
            throw ex;
        }
        this.s += a;
        if ((this.q & 0x2) == 0x2 && (this.q & 0x4) == 0x4) {
            this.r.println("[Input] Data: \n" + psc_c9.a(this.c, 0, this.l));
        }
        try {
            this.n();
            if ((this.q & 0x2) == 0x2 && (this.q & 0x4) == 0x4) {
                this.r.println("[Input] Plaintext: \n" + psc_c9.a(this.c, 0, this.l));
            }
            this.o();
            if (this.p.h() == 0 && this.c[0] == 0) {
                String str = "Alert Unknown";
                switch (this.c[1]) {
                    case 1: {
                        str = "No supported ciphers";
                        break;
                    }
                    case 2: {
                        str = "No certificate";
                        break;
                    }
                    case 3: {
                        str = "Bad certificate";
                        break;
                    }
                    case 4: {
                        str = "Unsupported Certificate Type";
                        break;
                    }
                }
                throw new psc_b("An Alert Was Received: " + str, 0, this.c[1]);
            }
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (Exception ex2) {
            throw new IOException("readBuffer() error");
        }
        this.j = this.n;
        return a;
    }
    
    public int read(final byte[] array, int n, final int n2) throws psc_b, IOException, psc_g8, InterruptedIOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("Invalid input parameter(s)");
        }
        if (n2 == 0) {
            return n2;
        }
        if ((this.l == 0 || this.j >= this.s) && this.a() < 0) {
            return -1;
        }
        int i = n2;
        int n3 = 0;
        while (i > this.s - this.j) {
            System.arraycopy(this.c, this.j, array, n, this.l - this.j);
            n3 += this.l - this.j;
            i -= this.l - this.j;
            n += this.l - this.j;
            this.j = this.l;
            if (super.in.available() < 0) {
                if (n2 == i) {
                    return -1;
                }
                return n2 - i;
            }
            else {
                if (super.in.available() == 0 && n2 - i > 0) {
                    return n2 - i;
                }
                try {
                    if (this.a() < 0) {
                        if (n2 == i) {
                            return -1;
                        }
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
                continue;
            }
        }
        System.arraycopy(this.c, this.j, array, n, i);
        this.j += i;
        return n2;
    }
    
    public int read() throws psc_b, IOException, InterruptedIOException {
        int n;
        if (this.j + 1 <= this.s) {
            n = (this.c[this.j] & 0xFF);
            ++this.j;
        }
        else {
            try {
                if (this.a() < 0) {
                    return -1;
                }
                n = (this.c[this.j] & 0xFF);
                ++this.j;
            }
            catch (psc_b psc_b) {
                throw psc_b;
            }
            catch (InterruptedIOException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                throw new IOException("read() error");
            }
        }
        return n;
    }
    
    public int b() throws psc_b, IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.j + 2 <= this.s) {
            n = ((this.c[this.j] & 0xFF) << 8 | (this.c[this.j + 1] & 0xFF));
            this.j += 2;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint16() error");
            }
            n = ((this.c[this.j] & 0xFF) << 8 | (this.c[this.j + 1] & 0xFF));
            this.j += 2;
        }
        return n;
    }
    
    public int c() throws psc_b, IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.j + 3 <= this.s) {
            n = ((this.c[this.j] & 0xFF) << 16 | (this.c[this.j + 1] & 0xFF) << 8 | (this.c[this.j + 2] & 0xFF));
            this.j += 3;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint24() error");
            }
            n = ((this.c[this.j] & 0xFF) << 16 | (this.c[this.j + 1] & 0xFF) << 8 | (this.c[this.j + 2] & 0xFF));
            this.j += 3;
        }
        return n;
    }
    
    public int d() throws psc_b, IOException, psc_g8, InterruptedIOException {
        int n;
        if (this.j + 4 <= this.s) {
            n = ((this.c[this.j] & 0xFF) << 24 | (this.c[this.j + 1] & 0xFF) << 16 | (this.c[this.j + 2] & 0xFF) << 8 | (this.c[this.j + 3] & 0xFF));
            this.j += 4;
        }
        else {
            if (this.a() < 0) {
                throw new IOException("readUint32()");
            }
            n = ((this.c[this.j] & 0xFF) << 24 | (this.c[this.j + 1] & 0xFF) << 16 | (this.c[this.j + 2] & 0xFF) << 8 | (this.c[this.j + 3] & 0xFF));
            this.j += 4;
        }
        return n;
    }
    
    public int e() {
        return 2;
    }
    
    public int f() {
        return this.l - this.n;
    }
    
    public int g() {
        return this.m;
    }
    
    public void a(final psc_dw p) {
        this.p = p;
        this.n = this.p.h();
    }
    
    public psc_dw h() {
        return this.p;
    }
    
    private void n() throws psc_g8, psc_d {
        final byte[] array = new byte[this.l];
        final int b;
        if ((b = this.p.b(this.c, 0, this.l, this.c, 0)) > this.i) {
            throw new psc_g8("Decrypted data out of boundary");
        }
        this.l = b;
        System.arraycopy(this.c, 0, array, 0, b);
        this.l -= this.m;
        this.s = this.l;
    }
    
    private void o() throws psc_g8, psc_d {
        final byte[] array = new byte[this.n];
        if (this.p.a(this.c, this.n, this.l + this.m - this.n, array, 0, true, this.o) != this.n) {
            throw new psc_g8("MAC data does not match");
        }
        for (int i = 0; i < this.n; ++i) {
            if (array[i] != this.c[i]) {
                throw new psc_g8("MAC data does not match");
            }
        }
        ++this.o;
    }
    
    public long i() {
        return this.o;
    }
    
    public int available() throws IOException {
        if (this.j >= this.l) {
            try {
                if (!this.u && !this.l()) {
                    return 0;
                }
                if (!this.k()) {
                    return 0;
                }
            }
            catch (IOException ex) {
                return -1;
            }
            if (this.a() == -1) {
                return -1;
            }
        }
        return this.l - this.j;
    }
    
    public void a(final int q, final PrintStream r) {
        this.q = q;
        this.r = r;
    }
    
    public void mark(final int n) {
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void reset() {
    }
    
    public Socket j() {
        return this.a;
    }
    
    static {
        psc_h6.d = 1;
        psc_h6.e = 1;
        psc_h6.f = 32767;
        psc_h6.g = 255;
        psc_h6.h = 16383;
    }
}
