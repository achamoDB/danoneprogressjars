import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_js extends psc_dt implements psc_am, Cloneable, Serializable
{
    private byte[] a;
    private byte[] b;
    private int c;
    private transient psc_dd d;
    private byte[] e;
    
    public String l() {
        return "DH";
    }
    
    public String[] n() {
        return new String[] { "DHPrivateKey", "DHPrivateValue", "DHPrivateKeyBER" };
    }
    
    public String[] o() {
        if (this.e == null) {
            return new String[0];
        }
        if (this.a == null || this.b == null) {
            return new String[] { "DHPrivateValue", "DHPrivateKeyBER" };
        }
        return new String[] { "DHPrivateKey", "DHPrivateValue", "DHPrivateKeyBER" };
    }
    
    public void a(final String s, final byte[][] array) throws psc_ao, psc_bf {
        if (s.compareTo("DHPrivateKey") == 0) {
            this.c(array);
            return;
        }
        if (s.compareTo("DHPrivateKeyBER") == 0) {
            if (array == null || array.length != 1) {
                this.h();
                throw new psc_bf("Invalid BER DH private key data.");
            }
            this.a(array[0], 0);
        }
        else {
            if (s.compareTo("DHPrivateValue") == 0) {
                this.a(array);
                return;
            }
            this.h();
            throw new psc_ao("Unknown DH key data format.");
        }
    }
    
    public void c(final byte[][] array) throws psc_bf {
        this.h();
        this.i();
        if (array == null || array.length != 4) {
            throw new psc_bf("Invalid input for DH key. Expected prime, base, maxExponentLen, publicValue");
        }
        try {
            this.a(array[0], 0, array[0].length, array[1], 0, array[1].length, -1, true, array[2], 0, array[2].length);
            this.a(array[3], 0, array[3].length);
        }
        catch (NullPointerException ex) {
            throw new psc_bf("Invalid input for DH key.");
        }
    }
    
    public void a(final byte[][] array) throws psc_bf {
        this.h();
        if (array == null || array.length != 1) {
            throw new psc_bf("Invalid input for DH key.");
        }
        if (array[0] == null) {
            throw new psc_bf("Invalid input for DH key.");
        }
        this.a(array[0], 0, array[0].length);
    }
    
    public void a(final byte[] array, final int n) throws psc_bf {
        this.h();
        this.i();
        psc_lm.a(this, array, n);
    }
    
    void a(final byte[] array, int n, int n2, final byte[] array2, int n3, int n4, final int c, final boolean b, final byte[] array3, final int n5, final int n6) throws psc_bf {
        this.i();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        int n7 = this.a.length * 8;
        for (int n8 = this.a[0] & 0xFF, i = n8 & 0x80; i == 0; i = (n8 & 0x80)) {
            --n7;
            n8 <<= 1;
        }
        if (n7 < 256 || n7 > 2048) {
            throw new psc_bf("Invalid DH prime size.");
        }
        while (array2[n3] == 0) {
            --n4;
            ++n3;
        }
        System.arraycopy(array2, n3, this.b = new byte[n4], 0, n4);
        if (this.b.length > this.a.length) {
            throw new psc_bf("Invalid DH base size.");
        }
        if (this.b.length == this.a.length) {
            int n9;
            for (n9 = 0; n9 < this.b.length && (this.b[n9] & 0xFF) >= (this.a[n9] & 0xFF); ++n9) {
                if ((this.b[n9] & 0xFF) > (this.a[n9] & 0xFF)) {
                    throw new psc_bf("Invalid DH base size.");
                }
            }
            if (n9 >= this.b.length) {
                throw new psc_bf("Invalid DH base size.");
            }
        }
        this.c = n7 - 1;
        if (c != -1) {
            this.c = c;
        }
        else if (b) {
            this.c = 0;
            for (int j = 0; j < n6; ++j) {
                final int c2 = this.c << 8;
                this.c = c2;
                this.c = (c2 | (array3[j + n5] & 0xFF));
            }
        }
        if (this.c >= n7 || this.c < 160) {
            throw new psc_bf("Invalid DH max exponent length.");
        }
    }
    
    void a(final byte[] array, int n, int n2) throws psc_bf {
        this.h();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.e = new byte[n2], 0, n2);
        if (this.c == 0) {
            (this.d = psc_au.b(this.e)).c();
            return;
        }
        int n3 = this.e.length * 8;
        for (int n4 = this.e[0] & 0xFF, i = n4 & 0x80; i == 0; i = (n4 & 0x80)) {
            --n3;
            n4 <<= 1;
        }
        if (n3 > this.c) {
            throw new psc_bf("Invalid DH private exponent size.");
        }
        (this.d = psc_au.b(this.e)).c();
    }
    
    public int q() {
        return 2048;
    }
    
    public int p() {
        return 256;
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (s.compareTo("DHPrivateKeyBER") == 0) {
            return this.d();
        }
        if (s.compareTo("DHPrivateValue") == 0) {
            return this.c();
        }
        if (s.compareTo("DHPrivateKey") != 0) {
            throw new psc_ao("Unknown DH key data format.");
        }
        return this.m();
    }
    
    public byte[][] m() {
        if (this.a == null || this.b == null || this.e == null) {
            return new byte[0][];
        }
        int n = 4;
        if (this.c <= 16777215) {
            --n;
            if (this.c <= 65535) {
                --n;
                if (this.c <= 255) {
                    --n;
                }
            }
        }
        final byte[] array = new byte[n];
        for (int i = n - 1, n2 = 0; i >= 0; --i, n2 += 8) {
            array[i] = (byte)(this.c >>> n2 & 0xFF);
        }
        final byte[] array2 = new byte[this.a.length];
        System.arraycopy(this.a, 0, array2, 0, this.a.length);
        final byte[] array3 = new byte[this.b.length];
        System.arraycopy(this.b, 0, array3, 0, this.b.length);
        final byte[] array4 = new byte[this.e.length];
        this.d.d();
        System.arraycopy(this.e, 0, array4, 0, this.e.length);
        this.d.c();
        return new byte[][] { array2, array3, array, array4 };
    }
    
    private byte[][] c() {
        if (this.e == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.e.length];
        this.d.d();
        System.arraycopy(this.e, 0, array, 0, this.e.length);
        this.d.c();
        return new byte[][] { array };
    }
    
    private byte[][] d() {
        if (this.a == null || this.b == null || this.e == null) {
            return new byte[0][];
        }
        this.d.d();
        try {
            return new byte[][] { psc_lm.a(this.a, this.b, this.e, this.c) };
        }
        catch (psc_ap psc_ap) {
            return new byte[0][];
        }
        finally {
            this.d.c();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.e();
        objectOutputStream.defaultWriteObject();
        this.f();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.g();
    }
    
    private void e() {
        if (this.d != null) {
            this.d.d();
        }
    }
    
    private void f() {
        if (this.d != null) {
            this.d.c();
        }
    }
    
    private void g() {
        if (this.e != null) {
            (this.d = psc_au.b(this.e)).c();
        }
    }
    
    public boolean equals(final Object o) {
        return o instanceof psc_js && this.a(o);
    }
    
    public boolean a(final Object o) {
        byte[][] array = null;
        Label_0546: {
            try {
                final psc_dt psc_dt = (psc_dt)o;
                boolean b;
                if (this.e == null) {
                    if (psc_dt.l().compareTo("DH") != 0) {
                        b = false;
                        break Label_0546;
                    }
                    if (psc_dt.o().length == 0) {
                        final boolean b2 = true;
                        break Label_0546;
                    }
                    final boolean b3 = false;
                    break Label_0546;
                }
                else {
                    if (this.a == null || this.b == null) {
                        String[] o2;
                        int n;
                        for (o2 = psc_dt.o(), n = 0; n < o2.length && o2[n].compareTo("DHPrivateValue") == 0; ++n) {}
                        if (n != o2.length) {
                            final boolean b4 = false;
                            break Label_0546;
                        }
                        array = psc_dt.b("DHPrivateValue");
                    }
                    else {
                        array = psc_dt.b("DHPrivateKey");
                    }
                    int n2 = 0;
                    if (array.length > 1) {
                        if (!this.a(this.a, null, array[0], null)) {
                            final boolean b5 = false;
                            break Label_0546;
                        }
                        if (!this.a(this.b, null, array[1], null)) {
                            final boolean b6 = false;
                            break Label_0546;
                        }
                        n2 = 2;
                    }
                    if (!this.a(this.e, this.d, array[n2], null)) {
                        final boolean b7 = false;
                        break Label_0546;
                    }
                    break Label_0546;
                }
                return b;
            }
            catch (Exception ex) {
                final boolean b8 = false;
                break Label_0546;
            }
            finally {
                Label_0607: {
                    if (array == null) {
                        break Label_0607;
                    }
                    if (array.length == 1) {
                        this.d(array[0]);
                        break Label_0607;
                    }
                    this.d(array[2]);
                    break Label_0607;
                }
                while (true) {}
                // iftrue(Label_0440:, array.length != 1)
                // iftrue(Label_0344:, array.length != 1)
                // iftrue(Label_0383:, array == null)
                // iftrue(Label_0376:, array.length != 1)
                // iftrue(Label_0536:, array.length != 1)
                // iftrue(Label_0472:, array.length != 1)
                // iftrue(Label_0312:, array.length != 1)
                // iftrue(Label_0351:, array == null)
                // iftrue(Label_0479:, array == null)
                // iftrue(Label_0568:, array.length != 1)
                // iftrue(Label_0504:, array.length != 1)
                // iftrue(Label_0575:, array == null)
                // iftrue(Label_0415:, array == null)
                // iftrue(Label_0447:, array == null)
                // iftrue(Label_0511:, array == null)
                // iftrue(Label_0408:, array.length != 1)
                // iftrue(Label_0543:, array == null)
                while (true) {
                    while (true) {
                        Block_31: {
                            final boolean b2;
                        Block_28_Outer:
                            while (true) {
                                Block_22: {
                                    final boolean b3;
                                    final boolean b6;
                                    while (true) {
                                    Block_32_Outer:
                                        while (true) {
                                            Block_21: {
                                                Block_27: {
                                                    while (true) {
                                                        final boolean b;
                                                        final boolean b4;
                                                        final boolean b5;
                                                        final boolean b7;
                                                        final boolean b8;
                                                        Block_17_Outer:Block_18_Outer:Block_26_Outer:Block_33_Outer:
                                                        while (true) {
                                                            this.d(array[0]);
                                                            return b7;
                                                            Label_0351: {
                                                                return b2;
                                                            }
                                                            Label_0344:
                                                            this.d(array[2]);
                                                            return b2;
                                                            Label_0575:
                                                            return b8;
                                                            Block_25: {
                                                                break Block_25;
                                                                Label_0319:
                                                                return b;
                                                                Label_0312:
                                                                this.d(array[2]);
                                                                return b;
                                                            }
                                                            this.d(array[0]);
                                                            return b5;
                                                            while (true) {
                                                                while (true) {
                                                                    while (true) {
                                                                        while (true) {
                                                                            this.d(array[0]);
                                                                            return b;
                                                                            break Block_28_Outer;
                                                                            Block_20: {
                                                                                break Block_20;
                                                                                Label_0511:
                                                                                return b7;
                                                                            }
                                                                            break Block_21;
                                                                            Label_0472:
                                                                            this.d(array[2]);
                                                                            return b6;
                                                                            Label_0568:
                                                                            this.d(array[2]);
                                                                            return b8;
                                                                            this.d(array[0]);
                                                                            return b4;
                                                                            Label_0504:
                                                                            this.d(array[2]);
                                                                            return b7;
                                                                            break Block_31;
                                                                            break Block_27;
                                                                            Label_0415:
                                                                            return b4;
                                                                            Label_0440:
                                                                            this.d(array[2]);
                                                                            return b5;
                                                                            continue Block_18_Outer;
                                                                        }
                                                                        continue Block_26_Outer;
                                                                    }
                                                                    this.d(array[0]);
                                                                    return b8;
                                                                    continue Block_33_Outer;
                                                                }
                                                                Label_0447:
                                                                return b5;
                                                                continue Block_32_Outer;
                                                            }
                                                            continue Block_17_Outer;
                                                        }
                                                        continue Block_28_Outer;
                                                    }
                                                    Label_0543: {
                                                        return true;
                                                    }
                                                    break Block_22;
                                                }
                                                this.d(array[0]);
                                                return b6;
                                                Label_0383: {
                                                    return b3;
                                                }
                                            }
                                            this.d(array[0]);
                                            return b3;
                                            Label_0408: {
                                                this.d(array[2]);
                                            }
                                            return;
                                            continue Block_28_Outer;
                                        }
                                        continue;
                                    }
                                    Label_0479: {
                                        return b6;
                                    }
                                    Label_0376:
                                    this.d(array[2]);
                                    return b3;
                                }
                                continue Block_28_Outer;
                            }
                            this.d(array[0]);
                            return b2;
                            Label_0536: {
                                this.d(array[2]);
                            }
                            return true;
                        }
                        this.d(array[0]);
                        return true;
                        continue;
                    }
                    continue;
                }
            }
            // iftrue(Label_0319:, array == null)
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_js psc_js = new psc_js();
        if (this.a != null) {
            psc_js.a = this.a.clone();
        }
        if (this.b != null) {
            psc_js.b = this.b.clone();
        }
        psc_js.c = this.c;
        if (this.e != null) {
            psc_js.e = (byte[])psc_au.a(this.e, this.d);
            psc_js.d = psc_au.a(psc_js.e);
        }
        psc_js.a(this);
        return psc_js;
    }
    
    private void h() {
        if (this.e == null) {
            return;
        }
        psc_au.c(this.e, this.d);
        this.e = null;
        this.d = null;
    }
    
    private void i() {
        this.d(this.a);
        this.d(this.b);
        this.c = 0;
        final byte[] array = null;
        this.b = array;
        this.a = array;
    }
    
    public void y() {
        super.y();
        this.h();
        this.i();
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
