import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jr extends psc_dt implements psc_am, Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private transient psc_dd f;
    private byte[] g;
    
    public String l() {
        return "DSA";
    }
    
    public String[] n() {
        return new String[] { "DSAPrivateKey", "DSAPrivateValue", "DSAPrivateKeyBER", "DSAPrivateKeyX957BER" };
    }
    
    public String[] o() {
        if (this.g == null) {
            return new String[0];
        }
        if (this.c == null || this.d == null || this.e == null) {
            return new String[] { "DSAPrivateValue", "DSAPrivateKeyBER", "DSAPrivateKeyX957BER" };
        }
        return new String[] { "DSAPrivateKey", "DSAPrivateValue", "DSAPrivateKeyBER", "DSAPrivateKeyX957BER" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_ao, psc_bf {
        if (s.compareTo("DSAPrivateKeyBER") == 0 || s.compareTo("DSAPrivateKeyX957BER") == 0) {
            this.g();
            if (array.length != 1) {
                throw new psc_bf("Invalid BER DSA private key data.");
            }
            psc_ll.a(this, array[0], 0);
        }
        else {
            if (s.compareTo("DSAPrivateKey") == 0) {
                this.c(array);
                return;
            }
            if (s.compareTo("DSAPrivateValue") == 0) {
                this.a(array);
                return;
            }
            throw new psc_ao("Unknown DSA key data format.");
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.g();
        this.h();
        if (array == null || array.length != 4) {
            throw new psc_bf("Invalid input for DSA key. Expects prime, subPrime, base, privateValue");
        }
        try {
            this.a(-1, array[0], 0, array[0].length, array[1], 0, array[1].length, array[2], 0, array[2].length);
            this.a(array[3], 0, array[3].length);
        }
        catch (NullPointerException ex) {
            throw new psc_bf("Invalid input for DSA key.");
        }
    }
    
    private void a(final byte[][] array) throws psc_bf {
        this.g();
        if (array == null || array.length != 1) {
            throw new psc_bf("Invalid input for DSA key.");
        }
        if (array[0] == null) {
            throw new psc_bf("Invalid input for DSA key.");
        }
        this.a(array[0], 0, array[0].length);
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.g();
        psc_ll.a(this, array, n);
    }
    
    void a(final int n, final byte[] array, int n2, int n3, final byte[] array2, int n4, int n5, final byte[] array3, int n6, int n7) throws psc_bf {
        this.h();
        while (array[n2] == 0) {
            --n3;
            ++n2;
        }
        System.arraycopy(array, n2, this.c = new byte[n3], 0, n3);
        this.a = this.c.length * 8;
        for (int n8 = this.c[0] & 0xFF, i = n8 & 0x80; i == 0; i = (n8 & 0x80)) {
            --this.a;
            n8 <<= 1;
        }
        if (n != -1 && this.a > n) {
            throw new psc_bf("DSA prime size mismatch.");
        }
        if (this.a < 512 || this.a > 4096) {
            throw new psc_bf("Invalid DSA prime size.");
        }
        while (array2[n4] == 0) {
            --n5;
            ++n4;
        }
        System.arraycopy(array2, n4, this.d = new byte[n5], 0, n5);
        this.b = this.d.length * 8;
        for (int n9 = this.d[0] & 0xFF, j = n9 & 0x80; j == 0; j = (n9 & 0x80)) {
            --this.b;
            n9 <<= 1;
        }
        if (this.b < 160 || this.b > 160) {
            throw new psc_bf("Invalid DSA subprime size.");
        }
        while (array3[n6] == 0) {
            --n7;
            ++n6;
        }
        System.arraycopy(array3, n6, this.e = new byte[n7], 0, n7);
        if (this.e.length > this.c.length) {
            throw new psc_bf("Invalid DSA base size.");
        }
        if (this.e.length == this.c.length) {
            int n10;
            for (n10 = 0; n10 < this.e.length && (this.e[n10] & 0xFF) >= (this.c[n10] & 0xFF); ++n10) {
                if ((this.e[n10] & 0xFF) > (this.c[n10] & 0xFF)) {
                    throw new psc_bf("Invalid DSA base size.");
                }
            }
            if (n10 >= this.e.length) {
                throw new psc_bf("Invalid DSA base size.");
            }
        }
    }
    
    void a(final byte[] array, int n, int n2) throws psc_bf {
        this.g();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.g = new byte[n2], 0, n2);
        if (this.c == null || this.d == null || this.e == null) {
            (this.f = psc_au.b(this.g)).c();
            return;
        }
        if (this.g.length > this.d.length) {
            throw new psc_bf("DSA private value incompatible with previously stored parameters.");
        }
        if (this.g.length == this.d.length) {
            int n3;
            for (n3 = 0; n3 < this.g.length && (this.g[n3] & 0xFF) >= (this.d[n3] & 0xFF); ++n3) {
                if ((this.g[n3] & 0xFF) > (this.d[n3] & 0xFF)) {
                    throw new psc_bf("DSA private value incompatible with previously stored parameters.");
                }
            }
            if (n3 >= this.g.length) {
                throw new psc_bf("DSA private value incompatible with previously stored parameters.");
            }
        }
        (this.f = psc_au.b(this.g)).c();
    }
    
    public int q() {
        return 4096;
    }
    
    public int p() {
        return 512;
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("DSAPrivateKeyBER") == 0) {
            return this.a((String)null);
        }
        if (s.compareTo("DSAPrivateKeyX957BER") == 0) {
            return this.a("DSAX957");
        }
        if (s.compareTo("DSAPrivateValue") == 0) {
            return this.f();
        }
        if (s.compareTo("DSAPrivateKey") != 0) {
            throw new psc_ao("Unknown DSA key data format.");
        }
        return this.m();
    }
    
    public byte[][] m() {
        if (this.c == null || this.d == null || this.e == null || this.g == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        final byte[] array2 = new byte[this.d.length];
        System.arraycopy(this.d, 0, array2, 0, this.d.length);
        final byte[] array3 = new byte[this.e.length];
        System.arraycopy(this.e, 0, array3, 0, this.e.length);
        final byte[] array4 = new byte[this.g.length];
        this.f.d();
        System.arraycopy(this.g, 0, array4, 0, this.g.length);
        this.f.c();
        return new byte[][] { array, array2, array3, array4 };
    }
    
    private byte[][] f() {
        if (this.g == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.g.length];
        this.f.d();
        System.arraycopy(this.g, 0, array, 0, this.g.length);
        this.f.c();
        return new byte[][] { array };
    }
    
    private byte[][] a(final String s) {
        if (this.g == null) {
            return new byte[0][];
        }
        this.f.d();
        try {
            return new byte[][] { psc_ll.a(s, this.c, this.d, this.e, this.g) };
        }
        catch (psc_ap psc_ap) {
            return new byte[0][];
        }
        finally {
            this.f.c();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.c();
        objectOutputStream.defaultWriteObject();
        this.d();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.e();
    }
    
    protected void c() {
        if (this.f != null) {
            this.f.d();
        }
    }
    
    protected void d() {
        if (this.f != null) {
            this.f.c();
        }
    }
    
    protected void e() {
        if (this.g != null) {
            (this.f = psc_au.b(this.g)).c();
        }
    }
    
    public boolean equals(final Object o) {
        return o instanceof psc_jr && this.a(o);
    }
    
    public boolean a(final Object o) {
        byte[][] array = null;
        Label_0635: {
            try {
                final psc_dt psc_dt = (psc_dt)o;
                boolean b8;
                if (this.g == null) {
                    if (psc_dt.l().compareTo("DSA") != 0) {
                        final boolean b = false;
                        break Label_0635;
                    }
                    final String[] o2 = psc_dt.o();
                    for (int i = 0; i < o2.length; ++i) {
                        if (o2[i].equals("DSAPrivateKey")) {
                            final boolean b2 = false;
                            break Label_0635;
                        }
                    }
                    final boolean b3 = true;
                    break Label_0635;
                }
                else {
                    if (this.c == null || this.d == null || this.e == null) {
                        String[] o3;
                        int n;
                        for (o3 = psc_dt.o(), n = 0; n < o3.length && o3[n].compareTo("DSAPrivateValue") == 0; ++n) {}
                        if (n != o3.length) {
                            final boolean b4 = false;
                            break Label_0635;
                        }
                        array = psc_dt.b("DSAPrivateValue");
                    }
                    else {
                        array = psc_dt.b("DSAPrivateKey");
                    }
                    int n2 = 0;
                    if (array.length > 1) {
                        if (!this.a(this.c, null, array[0], null)) {
                            final boolean b5 = false;
                            break Label_0635;
                        }
                        if (!this.a(this.d, null, array[1], null)) {
                            final boolean b6 = false;
                            break Label_0635;
                        }
                        if (!this.a(this.e, null, array[2], null)) {
                            final boolean b7 = false;
                            break Label_0635;
                        }
                        n2 = 3;
                    }
                    if (!this.a(this.g, this.f, array[n2], null)) {
                        b8 = false;
                        break Label_0635;
                    }
                    break Label_0635;
                }
                return b8;
            }
            catch (Exception ex) {
                final boolean b9 = false;
                break Label_0635;
            }
            finally {
                Label_0696: {
                    if (array == null) {
                        break Label_0696;
                    }
                    if (array.length == 1) {
                        this.d(array[0]);
                        break Label_0696;
                    }
                    this.d(array[3]);
                    break Label_0696;
                }
                while (true) {}
                Label_0465: {
                    this.d(array[3]);
                }
                return;
                // iftrue(Label_0536:, array == null)
                // iftrue(Label_0561:, array.length != 1)
                // iftrue(Label_0664:, array == null)
                // iftrue(Label_0465:, array.length != 1)
                // iftrue(Label_0529:, array.length != 1)
                // iftrue(Label_0593:, array.length != 1)
                // iftrue(Label_0408:, array == null)
                // iftrue(Label_0440:, array == null)
                // iftrue(Label_0657:, array.length != 1)
                // iftrue(Label_0401:, array.length != 1)
                // iftrue(Label_0433:, array.length != 1)
                // iftrue(Label_0568:, array == null)
                // iftrue(Label_0625:, array.length != 1)
                // iftrue(Label_0632:, array == null)
                // iftrue(Label_0497:, array.length != 1)
                // iftrue(Label_0472:, array == null)
                // iftrue(Label_0504:, array == null)
                // iftrue(Label_0600:, array == null)
                // iftrue(Label_0376:, array == null)
                // iftrue(Label_0369:, array.length != 1)
                final boolean b2;
            Block_22:
                while (true) {
                    final boolean b6;
                Block_27_Outer:
                    while (true) {
                        final boolean b7;
                        Block_32: {
                            while (true) {
                            Block_28_Outer:
                                while (true) {
                                Block_35_Outer:
                                    while (true) {
                                        while (true) {
                                        Block_31_Outer:
                                            while (true) {
                                                while (true) {
                                                    final boolean b3;
                                                    Block_23: {
                                                        final boolean b9;
                                                        Block_38: {
                                                            Block_21: {
                                                                final boolean b8;
                                                                Block_34: {
                                                                    Block_37: {
                                                                    Block_29:
                                                                        while (true) {
                                                                            this.d(array[0]);
                                                                            return b4;
                                                                            final boolean b5;
                                                                            Label_0504:
                                                                            return b5;
                                                                            this.d(array[0]);
                                                                            return true;
                                                                            break Block_29;
                                                                            break Block_32;
                                                                            Label_0593:
                                                                            this.d(array[3]);
                                                                            return b8;
                                                                            Label_0433:
                                                                            this.d(array[3]);
                                                                            return b3;
                                                                            Label_0657:
                                                                            this.d(array[3]);
                                                                            return b9;
                                                                            break Block_37;
                                                                            continue Block_31_Outer;
                                                                        }
                                                                        Block_30: {
                                                                            break Block_30;
                                                                            break Block_34;
                                                                            Label_0369:
                                                                            this.d(array[3]);
                                                                            return;
                                                                            break Block_21;
                                                                            this.d(array[0]);
                                                                            return b;
                                                                            Label_0401:
                                                                            this.d(array[3]);
                                                                            return b2;
                                                                            break Block_23;
                                                                        }
                                                                        this.d(array[0]);
                                                                        return b6;
                                                                        Label_0664:
                                                                        return b9;
                                                                    }
                                                                    break Block_38;
                                                                    this.d(array[0]);
                                                                    return;
                                                                }
                                                                this.d(array[0]);
                                                                return b8;
                                                                Label_0529:
                                                                this.d(array[3]);
                                                                return b6;
                                                            }
                                                            break Block_22;
                                                        }
                                                        this.d(array[0]);
                                                        return b9;
                                                    }
                                                    Block_24: {
                                                        break Block_24;
                                                        Label_0497:
                                                        this.d(array[3]);
                                                        return;
                                                        final boolean b8;
                                                        Label_0600:
                                                        return b8;
                                                    }
                                                    this.d(array[0]);
                                                    return b3;
                                                    continue Block_28_Outer;
                                                }
                                                Label_0472:
                                                return b4;
                                                continue Block_31_Outer;
                                            }
                                            continue Block_27_Outer;
                                        }
                                        Label_0408:
                                        return b2;
                                        Label_0625:
                                        this.d(array[3]);
                                        return true;
                                        Label_0561:
                                        this.d(array[3]);
                                        return b7;
                                        final boolean b3;
                                        Label_0440:
                                        return b3;
                                        continue Block_35_Outer;
                                    }
                                    continue Block_28_Outer;
                                }
                                Label_0632:
                                return true;
                                final boolean b;
                                Label_0376:
                                return b;
                                continue;
                            }
                        }
                        this.d(array[0]);
                        Label_0568:
                        return b7;
                        continue;
                    }
                    Label_0536:
                    return b6;
                    continue;
                }
                this.d(array[0]);
                return b2;
            }
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jr psc_jr = new psc_jr();
        this.a(psc_jr);
        return psc_jr;
    }
    
    protected void a(final psc_jr psc_jr) throws CloneNotSupportedException {
        if (this.c != null) {
            psc_jr.c = this.c.clone();
        }
        if (this.d != null) {
            psc_jr.d = this.d.clone();
        }
        if (this.e != null) {
            psc_jr.e = this.e.clone();
        }
        if (this.g != null) {
            psc_jr.g = (byte[])psc_au.a(this.g, this.f);
            psc_jr.f = psc_au.a(psc_jr.g);
        }
        psc_jr.a = this.a;
        psc_jr.b = this.b;
        psc_jr.a(this);
    }
    
    private void g() {
        if (this.g == null) {
            return;
        }
        psc_au.c(this.g, this.f);
        this.g = null;
        this.f = null;
    }
    
    private void h() {
        this.d(this.c);
        this.d(this.e);
        this.d(this.d);
        final byte[] c = null;
        this.e = c;
        this.d = c;
        this.c = c;
        final int n = 0;
        this.b = n;
        this.a = n;
    }
    
    public void y() {
        super.y();
        this.g();
        this.h();
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
