import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

class psc_dh extends psc_df implements Cloneable, Serializable
{
    protected psc_gv a;
    private SecureRandom b;
    private byte[] c;
    private String d;
    private int e;
    protected static final int f = 1;
    protected static final int g = 2;
    protected static final int h = 3;
    protected static final int i = 4;
    protected static final int j = 5;
    protected static final int k = 6;
    protected static final int l = 7;
    
    psc_dh(final psc_gv a) {
        this.a = a;
        this.e = 1;
    }
    
    protected psc_dh() {
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        this.a.a(array, n, n2);
    }
    
    public byte[] f() throws psc_ao {
        final byte[] g = this.a.g();
        int length = 0;
        if (g != null) {
            length = g.length;
        }
        final String d = this.a.d();
        try {
            return psc_q.a(d, 7, g, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode given transformation.(" + psc_m.getMessage() + ")");
        }
    }
    
    protected int c() {
        return this.e;
    }
    
    public String i() {
        return this.a.d();
    }
    
    public int[] p() {
        if (this.a != null) {
            return this.a.c();
        }
        return new int[0];
    }
    
    public void a(final int[] array) throws psc_be {
        if (this.a != null) {
            this.a.a(array);
        }
    }
    
    public int a(final int n) {
        return this.a.b(n);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av d = this.d();
        objectOutputStream.defaultWriteObject();
        this.a(d);
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
    
    private psc_av d() {
        if (this.b == null) {
            return null;
        }
        if (!(this.b instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.b;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.d = psc_av.getAlgorithm();
        this.c = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.b;
        this.b = null;
        return psc_av2;
    }
    
    private void a(final psc_av b) {
        if (this.c == null) {
            return;
        }
        for (int i = 0; i < this.c.length; ++i) {
            this.c[i] = 0;
        }
        this.c = null;
        this.d = null;
        this.b = b;
    }
    
    private void e() {
        if (this.c == null) {
            return;
        }
        this.b = psc_av.a(this.d, this.c);
        for (int i = 0; i < this.c.length; ++i) {
            this.c[i] = 0;
        }
        this.c = null;
        this.d = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_dh psc_dh = (psc_dh)super.clone();
        if (this.a != null) {
            psc_dh.a = (psc_gv)this.a.clone();
        }
        psc_dh.b = this.b;
        psc_dh.e = this.e;
        psc_dh.a(this);
        return psc_dh;
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom b) throws psc_en, psc_bf {
        this.a.a(psc_dc, b);
        this.a.e();
        this.b = b;
        this.e = 2;
    }
    
    public void v() throws psc_en {
        throw new psc_en("Cannot re-initialize this object.");
    }
    
    protected byte[] a(final psc_am psc_am, final boolean b, final byte[] array, final int n, final int n2) throws psc_en {
        return this.a(psc_am, b, null, array, n, n2);
    }
    
    protected byte[] a(final psc_am psc_am, final boolean b, final String s, final byte[] array, final int n, final int n2) throws psc_en {
        if (this.e != 2) {
            throw new psc_en("Cannot wrap key, object needs new initialization.");
        }
        Label_0176: {
            if (this.a.a(b)) {
                final byte[] a = this.a.a(psc_am, b, null, null);
                final int length = a.length;
                this.e = 4;
                break Label_0176;
            }
            byte[][] b2 = null;
            byte[] a;
            int length;
            int a2;
            byte[] array2;
            Label_0246_Outer:Block_11_Outer:
            while (true) {
                try {
                    b2 = psc_am.b((s != null) ? s : psc_am.b(b));
                    a = new byte[this.a(b2[0].length)];
                    a2 = this.a(b2[0], 0, b2[0].length, a, 0);
                    length = a2 + this.b(a, a2);
                    break Label_0246_Outer;
                    break Label_0176;
                }
                catch (psc_ao psc_ao) {
                    throw new psc_en("The key given cannot be wrapped.");
                }
                finally {
                    Label_0269: {
                        if (b2 != null && b2.length > 0) {
                            psc_au.c(b2[0]);
                            break Label_0269;
                        }
                        break Label_0269;
                    }
                    while (true) {}
                    // iftrue(Label_0211:, b)
                    // iftrue(Label_0191:, length != a.length)
                    return a;
                    while (true) {
                        while (true) {
                            continue Label_0246_Outer;
                            psc_au.c(b2[0]);
                            continue Block_11_Outer;
                        }
                        Label_0211: {
                            return psc_k5.a(array, n, n2, a, 0, length);
                        }
                        Label_0191:
                        array2 = new byte[length];
                        System.arraycopy(a, 0, array2, 0, length);
                        return array2;
                        continue;
                    }
                }
                // iftrue(Label_0246:, b2 == null || b2.length <= 0)
                break;
            }
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en {
        switch (this.e) {
            case 2:
            case 3: {
                if (n2 <= 0) {
                    return 0;
                }
                this.a.f();
                final int a = this.a.a(array, n, n2, array2, n3);
                this.e = 3;
                this.a.e();
                return a;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for decryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_en {
        switch (this.e) {
            case 2:
            case 3: {
                this.a.f();
                final int a = this.a.a(array, n);
                this.e = 4;
                this.a.e();
                return a;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for decryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public void d(final psc_dc psc_dc, final SecureRandom b) throws psc_en, psc_bf {
        this.a.b(psc_dc, b);
        this.a.e();
        this.b = b;
        this.e = 5;
    }
    
    public void x() throws psc_en {
        throw new psc_en("Cannot re-initialize this object.");
    }
    
    public psc_dt a(final byte[] array, int n, int n2, final boolean b, String g) throws psc_en {
        if (this.e != 5) {
            throw new psc_en("Cannot unwrap key, object needs new initialization.");
        }
        if (g == null) {
            g = this.g();
        }
        if (b) {
            final int[] a = psc_k5.a(array, n);
            n = a[0];
            n2 = a[1];
        }
        if (this.a.a(false)) {
            this.e = 7;
            return this.a.a(array, n, n2, null, null, g);
        }
        byte[] array2 = null;
        try {
            array2 = new byte[this.a(n2)];
            final int b2 = this.b(array, n, n2, array2, 0);
            final int n3 = b2 + this.c(array2, b2);
            return psc_dt.a(array2, 0, g);
        }
        catch (psc_ao psc_ao) {
            throw new psc_en("Could not unwrap private key.");
        }
        finally {
            if (array2 != null) {
                psc_au.c(array2);
            }
        }
    }
    
    public psc_al b(final byte[] array, int n, int n2, final boolean b, String g) throws psc_en {
        if (this.e != 5) {
            throw new psc_en("Cannot unwrap key, object needs new initialization.");
        }
        if (g == null) {
            g = this.g();
        }
        if (b) {
            final int[] a = psc_k5.a(array, n);
            n = a[0];
            n2 = a[1];
        }
        if (this.a.a(false)) {
            this.e = 7;
            return this.a.b(array, n, n2, null, null, g);
        }
        try {
            final byte[] array2 = new byte[this.a(n2)];
            final int b2 = this.b(array, n, n2, array2, 0);
            final int n3 = b2 + this.c(array2, b2);
            return psc_al.a(array2, 0, g);
        }
        catch (psc_ao psc_ao) {
            throw new psc_en("Could not unwrap public key.");
        }
    }
    
    public psc_dc a(final byte[] array, int n, int n2, final boolean b, final String s, String g) throws psc_en {
        if (this.e != 5) {
            throw new psc_en("Cannot unwrap key, object needs new initialization.");
        }
        if (g == null) {
            g = this.g();
        }
        if (b) {
            final int[] a = psc_k5.a(array, n);
            n = a[0];
            n2 = a[1];
        }
        if (this.a.a(b)) {
            this.e = 7;
            return this.a.a(array, n, n2, b, null, null, g);
        }
        byte[] array2 = null;
        try {
            array2 = new byte[this.a(n2)];
            final int b2 = this.b(array, n, n2, array2, 0);
            final int n3 = b2 + this.c(array2, b2);
            psc_dc psc_dc;
            if (b) {
                psc_dc = psc_dc.a(array2, 0, g);
            }
            else {
                psc_dc = psc_dc.a(s, g);
                psc_dc.a(array2, 0, n3);
            }
            return psc_dc;
        }
        catch (psc_ao psc_ao) {
            throw new psc_en("Could not unwrap secret key.");
        }
        catch (psc_bf psc_bf) {
            throw new psc_en(psc_bf.getMessage());
        }
        finally {
            if (array2 != null) {
                psc_au.c(array2);
            }
        }
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en {
        switch (this.e) {
            case 5:
            case 6: {
                if (n2 <= 0) {
                    return 0;
                }
                this.a.f();
                final int b = this.a.b(array, n, n2, array2, n3);
                this.e = 6;
                this.a.e();
                return b;
            }
            case 2:
            case 3: {
                throw new psc_en("Object initialized for encryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int c(final byte[] array, final int n) throws psc_en {
        switch (this.e) {
            case 5:
            case 6: {
                this.a.f();
                final int b = this.a.b(array, n);
                this.e = 7;
                this.a.e();
                return b;
            }
            case 2:
            case 3: {
                throw new psc_en("Object initialized for encryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        this.e = 1;
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
