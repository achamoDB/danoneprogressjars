import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jq extends psc_dt implements Cloneable, Serializable
{
    private int a;
    private byte[][] b;
    private transient psc_dd[] c;
    private transient psc_dd d;
    private transient psc_dd e;
    private byte[] f;
    private byte[] g;
    
    public String l() {
        return "RSA";
    }
    
    public String[] n() {
        return new String[] { "RSAPrivateKeyCRT", "RSAMultiPrimePrivateKeyCRT", "RSAPrivateKey", "RSAPrivateKeyBER", "SSLCPKCS1RSAPrivateKeyBER", "SSLCPKCS1RSAPrivateKeyPEM" };
    }
    
    public String[] o() {
        if (this.b == null || this.b.length < 2) {
            return new String[0];
        }
        if (this.b[0] == null || this.b[1] == null) {
            return new String[0];
        }
        if (this.a == 0) {
            return new String[] { "RSAPrivateKey", "SSLCPKCS1RSAPrivateKeyBER", "SSLCPKCS1RSAPrivateKeyPEM" };
        }
        if (this.a > 2) {
            return new String[] { "RSAMultiPrimePrivateKeyCRT", "RSAPrivateKeyBlind", "RSAPrivateKey", "RSAPrivateKeyBER" };
        }
        return new String[] { "RSAMultiPrimePrivateKeyCRT", "RSAPrivateKeyCRT", "RSAPrivateKeyBlind", "RSAPrivateKey", "RSAPrivateKeyBER", "SSLCPKCS1RSAPrivateKeyBER", "SSLCPKCS1RSAPrivateKeyPEM" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_ao, psc_bf {
        if (s.compareTo("RSAPrivateKeyCRT") == 0) {
            this.c(array);
            return;
        }
        if (s.compareTo("RSAMultiPrimePrivateKeyCRT") == 0) {
            this.a(array);
            return;
        }
        if (s.compareTo("RSAPrivateKey") == 0) {
            this.b(array);
            return;
        }
        if (s.compareTo("RSAPrivateKeyBER") == 0) {
            if (array.length != 1) {
                this.g();
                throw new psc_bf("Invalid BER RSA private key data.");
            }
            this.a(array[0], 0);
        }
        else {
            if (!s.equals("SSLCPKCS1RSAPrivateKeyBER") && !s.equals("SSLCPKCS1RSAPrivateKeyPEM")) {
                this.g();
                throw new psc_ao("Unimplemented RSA Private Key Format.");
            }
            if (array.length != 1) {
                throw new psc_bf("Invalid SSLC RSA private key data.");
            }
            final byte[][] array2 = null;
            final String[] array3 = { null };
            final byte[] array4 = new byte[8];
            byte[] b = array[0];
            if (s.compareTo("SSLCPKCS1RSAPrivateKeyPEM") == 0) {
                b = psc_ip.b(psc_ip.a(array[0]));
            }
            this.a(psc_ip.f(b), 0);
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.g();
        if (array.length != 8) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
        this.a = 2;
        this.b = new byte[8][];
        this.c = new psc_dd[6];
        boolean a = false;
        for (int i = 0, n = -2; i < 8; ++i, ++n) {
            a = this.a(array[i], 0, array[i].length, i, n);
            if (!a) {
                break;
            }
        }
        if (!a) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
    }
    
    public void a(final byte[][] array) throws psc_bf {
        this.g();
        if (array == null || array.length < 4) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
        if (array[3] == null || array[3].length != 1) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
        this.a = (array[3][0] & 0xFF);
        final int n = this.a * 3 + 2;
        if (array.length != n + 1) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
        this.b = new byte[n][];
        this.c = new psc_dd[n - 2];
        boolean a = false;
        int i = 0;
        int n2 = -2;
        int n3 = 0;
        while (i < n + 1) {
            if (i != 3) {
                a = this.a(array[i], 0, array[i].length, n3, n2);
                ++n3;
                if (!a) {
                    break;
                }
                ++n2;
            }
            ++i;
        }
        if (!a) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
    }
    
    private void b(final byte[][] array) throws psc_bf {
        this.g();
        if (array.length != 2) {
            throw new psc_bf("Invalid RSA Private Key Data Format.");
        }
        this.b = new byte[2][];
        this.c = new psc_dd[1];
        boolean a = false;
        for (int i = 0, n = -1; i < 2; ++i, ++n) {
            a = this.a(array[i], 0, array[i].length, i, n);
            if (!a) {
                break;
            }
        }
        if (!a) {
            throw new psc_bf("Invalid RSA Private Key data format");
        }
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.g();
        psc_lg.a(this, array, n);
    }
    
    int a(final int a) {
        final int n = a * 3 + 2;
        this.b = new byte[n][];
        this.c = new psc_dd[n - 2];
        this.a = a;
        return n;
    }
    
    void b(final int a) {
        this.g();
        this.a = a;
        this.b = new byte[a * 3 + 2][];
        this.c = new psc_dd[a * 3];
    }
    
    boolean a(final byte[] array, int n, int n2, final int n3, final int n4) {
        for (int i = n; i < n + n2; ++n, --n2, ++i) {
            if (n >= array.length) {
                return false;
            }
            if (array[n] != 0) {
                break;
            }
        }
        System.arraycopy(array, n, this.b[n3] = new byte[n2], 0, n2);
        if (n4 < 0) {
            return true;
        }
        this.c[n4] = psc_au.b(this.b[n3]);
        return true;
    }
    
    private boolean c(final int n) {
        if (this.b == null || this.b[0] == null) {
            return false;
        }
        int n2 = this.b[0].length * 8;
        int i = this.b[0][0] & 0xFF;
        for (int n3 = 0; i == 0; i = this.b[0][n3]) {
            if (++n3 >= this.b[0].length) {
                return false;
            }
        }
        while ((i & 0x80) == 0x0) {
            --n2;
            i <<= 1;
        }
        return psc_k8.a(n, n2);
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("RSAPrivateKeyCRT") == 0) {
            return this.m();
        }
        if (s.compareTo("RSAPrivateKeyBlind") == 0) {
            return this.c();
        }
        if (s.compareTo("RSAMultiPrimePrivateKeyCRT") == 0) {
            return this.a(false);
        }
        if (s.compareTo("RSAPrivateKeyBER") == 0) {
            return this.i();
        }
        if (s.compareTo("RSAPrivateKey") == 0) {
            return this.h();
        }
        if (!s.equals("SSLCPKCS1RSAPrivateKeyBER") && !s.equals("SSLCPKCS1RSAPrivateKeyPEM")) {
            throw new psc_ao("Unimplemented Key Data Format");
        }
        final byte[][] array = { null };
        final byte[][] i = this.i();
        try {
            array[0] = psc_ip.d(i[0]);
        }
        catch (psc_bf psc_bf) {
            throw new psc_ao(psc_bf.getMessage());
        }
        if (s.compareTo("SSLCPKCS1RSAPrivateKeyPEM") != 0) {
            return array;
        }
        final byte[] array2 = array[0];
        byte[] c;
        try {
            c = psc_ip.c(array2);
        }
        catch (psc_bf psc_bf2) {
            throw new psc_ao(psc_bf2.getMessage());
        }
        return new byte[][] { psc_ip.a(c, "CLEAR", new byte[8]) };
    }
    
    public byte[][] m() {
        if (this.a != 2) {
            return new byte[0][];
        }
        final byte[][] array = new byte[8][];
        for (int i = 0; i < 8; ++i) {
            if (i > 1) {
                this.c[i - 2].d();
                array[i] = this.b[i].clone();
                this.c[i - 2].c();
            }
            else {
                array[i] = this.b[i].clone();
            }
        }
        return array;
    }
    
    public byte[][] a(final boolean b) {
        if (this.a < 2) {
            return new byte[0][];
        }
        int n = 0;
        if (b) {
            n = 2;
        }
        final int n2 = this.a * 3 + 3;
        final byte[][] array = new byte[n2 + n][];
        int i = 0;
        int n3 = 0;
        while (i < n2) {
            if (i == 3) {
                (array[i] = new byte[1])[0] = (byte)this.a;
            }
            else {
                if (n3 > 1) {
                    this.c[n3 - 2].d();
                    array[i] = this.b[n3].clone();
                    this.c[n3 - 2].c();
                }
                else {
                    array[i] = this.b[n3].clone();
                }
                ++n3;
            }
            ++i;
        }
        if (n != 0) {
            this.d.d();
            array[i] = this.f.clone();
            this.d.c();
            this.e.d();
            array[i + 1] = this.g.clone();
            this.e.c();
        }
        return array;
    }
    
    public byte[][] c() {
        if (this.a < 2) {
            return new byte[0][];
        }
        if (this.f == null || this.g == null) {
            final byte[] array = new byte[8];
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < 8; ++i) {
                array[i] = (byte)currentTimeMillis;
                currentTimeMillis >>= 8;
            }
            this.f = new byte[this.b[0].length];
            this.g = new byte[this.b[0].length];
            if (!psc_i1.a(this.b[0], this.b[3], this.b[4], this.b[1], array, 0, 8, this.f, this.g)) {
                this.f = null;
                this.g = null;
                return new byte[0][];
            }
            this.d = psc_au.b(this.f);
            this.e = psc_au.b(this.g);
        }
        return this.a(true);
    }
    
    private byte[][] h() {
        if (this.b == null) {
            return new byte[0][];
        }
        final int n = 0;
        int n2 = 1;
        if (this.a > 0) {
            n2 = 2;
        }
        if (this.b.length < n2 + 1 || this.b[n] == null || this.b[n2] == null) {
            return new byte[0][];
        }
        this.c[0].d();
        final byte[][] array = { this.b[n].clone(), this.b[n2].clone() };
        this.c[0].d();
        return array;
    }
    
    private byte[][] i() {
        if (this.a == 0) {
            return new byte[0][];
        }
        for (int i = 0; i < this.c.length; ++i) {
            this.c[i].d();
        }
        Label_0121: {
            try {
                final byte[][] array = { psc_lg.a(this.a, this.b) };
                break Label_0121;
            }
            catch (psc_ap psc_ap) {
                final byte[][] array2 = new byte[0][];
                break Label_0121;
            }
            finally {
                for (int j = 0; j < this.c.length; ++j) {
                    this.c[j].c();
                }
                while (true) {}
                int n = 0;
                // iftrue(Label_0149:, n2 >= this.c.length)
                while (true) {
                    Label_0094: {
                        break Label_0094;
                        while (true) {
                            int n2 = 0;
                            Block_7: {
                                break Block_7;
                                Label_0118: {
                                    return;
                                }
                                n2 = 0;
                                continue;
                            }
                            this.c[n2].c();
                            ++n2;
                            continue;
                        }
                        this.c[n].c();
                        ++n;
                        break Label_0094;
                        Label_0149: {
                            return;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0118:, n >= this.c.length)
        }
    }
    
    public int q() {
        return 4096;
    }
    
    public int p() {
        return 256;
    }
    
    public boolean equals(final Object o) {
        return o instanceof psc_jq && this.a(o);
    }
    
    public boolean a(final Object o) {
        byte[][] b = null;
        Label_0353: {
            try {
                final psc_dt psc_dt = (psc_dt)o;
                boolean b3;
                if (this.b == null || this.b.length < 2) {
                    if (psc_dt.l().compareTo("RSA") != 0) {
                        final boolean b2 = false;
                        break Label_0353;
                    }
                    final String[] o2 = psc_dt.o();
                    for (int i = 0; i < o2.length; ++i) {
                        if (o2[i].equals("RSAPrivateKey")) {
                            b3 = false;
                            break Label_0353;
                        }
                    }
                    final boolean b4 = true;
                    break Label_0353;
                }
                else {
                    b = psc_dt.b("RSAPrivateKey");
                    if (!this.a(this.b[0], null, b[0], null)) {
                        final boolean b5 = false;
                        break Label_0353;
                    }
                    int n = 1;
                    if (this.b.length > 2) {
                        n = 2;
                    }
                    if (!this.a(this.b[n], this.c[0], b[1], null)) {
                        final boolean b6 = false;
                        break Label_0353;
                    }
                    break Label_0353;
                }
                return b3;
            }
            catch (Exception ex) {
                final boolean b7 = false;
                break Label_0353;
            }
            finally {
                Label_0394: {
                    if (b != null && b.length >= 2) {
                        this.d(b[1]);
                        break Label_0394;
                    }
                    break Label_0394;
                }
                while (true) {}
                Label_0328: {
                    return;
                }
                final boolean b4;
                Label_0284:
                return b4;
                final boolean b7;
                Label_0372:
                return b7;
                // iftrue(Label_0306:, b == null || b.length < 2)
                // iftrue(Label_0240:, b == null || b.length < 2)
                // iftrue(Label_0262:, b == null || b.length < 2)
                // iftrue(Label_0328:, b == null || b.length < 2)
                // iftrue(Label_0350:, b == null || b.length < 2)
                // iftrue(Label_0284:, b == null || b.length < 2)
            Block_23_Outer:
                while (true) {
                    while (true) {
                        Block_19: {
                            final boolean b2;
                            Block_13: {
                            Block_15_Outer:
                                while (true) {
                                    this.d(b[1]);
                                    return;
                                    while (true) {
                                        this.d(b[1]);
                                        return;
                                        this.d(b[1]);
                                        return b4;
                                        Label_0240:
                                        return b2;
                                        Label_0262:
                                        return b3;
                                        break Block_19;
                                        break Block_13;
                                        continue Block_23_Outer;
                                    }
                                    continue Block_15_Outer;
                                }
                                this.d(b[1]);
                                return true;
                            }
                            this.d(b[1]);
                            return b2;
                        }
                        this.d(b[1]);
                        return;
                        continue;
                    }
                    continue Block_23_Outer;
                }
                while (true) {
                    this.d(b[1]);
                    return b7;
                    final boolean b5;
                    Label_0306:
                    return b5;
                    Label_0350:
                    return true;
                    continue;
                }
            }
            // iftrue(Label_0372:, b == null || b.length < 2)
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.d();
        objectOutputStream.defaultWriteObject();
        this.e();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.f();
    }
    
    protected void d() {
        if (this.c == null) {
            return;
        }
        for (int i = 0; i < this.c.length; ++i) {
            if (this.c[i] != null) {
                this.c[i].d();
            }
        }
    }
    
    protected void e() {
        if (this.c == null) {
            return;
        }
        for (int i = 0; i < this.c.length; ++i) {
            if (this.c[i] != null) {
                this.c[i].c();
            }
        }
    }
    
    protected void f() {
        if (this.b == null) {
            return;
        }
        if (this.a != 0) {
            this.c = new psc_dd[this.a * 3];
            for (int i = 2; i < this.b.length; ++i) {
                if (this.b[1] == null) {
                    return;
                }
                this.c[i - 2] = psc_au.b(this.b[i]);
            }
            return;
        }
        if (this.b.length < 2) {
            return;
        }
        if (this.b[1] == null) {
            return;
        }
        (this.c = new psc_dd[1])[0] = psc_au.b(this.b[1]);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jq psc_jq = new psc_jq();
        this.a(psc_jq);
        return psc_jq;
    }
    
    protected void a(final psc_jq psc_jq) throws CloneNotSupportedException {
        psc_jq.a = this.a;
        psc_jq.a(this);
        if (this.b == null) {
            return;
        }
        if (this.f != null) {
            psc_jq.f = this.f.clone();
            psc_jq.d = psc_au.b(this.f);
        }
        if (this.g != null) {
            psc_jq.g = this.g.clone();
            psc_jq.e = psc_au.b(this.g);
        }
        psc_jq.b = new byte[this.b.length][];
        psc_jq.c = new psc_dd[this.c.length];
        int n = -1;
        if (this.a != 0) {
            n = -2;
        }
        for (int i = 0; i < this.b.length; ++i, ++n) {
            if (this.b[i] != null) {
                if (n < 0) {
                    psc_jq.b[i] = this.b[i].clone();
                }
                else {
                    psc_jq.b[i] = (byte[])psc_au.a(this.b[i], this.c[n]);
                    psc_jq.c[n] = psc_au.a(psc_jq.b[i]);
                }
            }
        }
    }
    
    protected void g() {
        if (this.b == null || this.c == null) {
            this.j();
            return;
        }
        if (this.a == 0) {
            if (this.b.length == 2 && this.c.length == 1) {
                psc_au.c(this.b[1], this.c[0]);
            }
            this.j();
            return;
        }
        final int n = this.a * 3;
        if (this.b.length == n + 2 && this.c.length == n) {
            for (int i = 0; i < this.c.length; ++i) {
                psc_au.c(this.b[i + 2], this.c[i]);
            }
        }
        psc_au.c(this.f, this.d);
        psc_au.c(this.g, this.e);
        this.j();
    }
    
    private void j() {
        this.a = 0;
        this.b = null;
        this.c = null;
        this.f = null;
        this.g = null;
    }
    
    public void y() {
        super.y();
        this.g();
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
