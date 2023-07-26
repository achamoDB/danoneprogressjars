import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_dk extends psc_df implements Cloneable, Serializable
{
    protected psc_dl a;
    protected psc_di b;
    protected psc_dm c;
    private SecureRandom d;
    private byte[] e;
    private String f;
    private int g;
    private int h;
    private byte[] i;
    private byte[] j;
    private int k;
    private int l;
    private int m;
    protected static final int n = 1;
    protected static final int o = 2;
    protected static final int p = 3;
    protected static final int q = 4;
    protected static final int r = 5;
    protected static final int s = 6;
    protected static final int t = 7;
    private boolean u;
    
    psc_dk(final psc_dl a, final psc_di b, final psc_dm c) {
        this.u = false;
        int g = b.k();
        if (g == -1) {
            g = a.g();
        }
        this.h = a.g();
        final byte[] i = new byte[g];
        this.a = a;
        this.b = b;
        this.c = c;
        this.g = g;
        this.i = i;
        this.u = (this.b.c() && this.c.l());
        this.m = 1;
    }
    
    protected psc_dk() {
        this.u = false;
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        this.a.a(array, n, n2, this.b, this.c);
        int g = this.b.k();
        if (g == -1) {
            g = this.a.g();
        }
        if (g == this.g) {
            return;
        }
        this.g = g;
        this.i = new byte[this.g];
    }
    
    public byte[] f() throws psc_ao {
        final byte[] a = this.a.a(this.b.g());
        int length = 0;
        if (a != null) {
            length = a.length;
        }
        final String string = this.a.d() + "/" + this.b.f() + "/" + this.c.d();
        try {
            return psc_q.a(string, 8, a, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode given transformation.(" + psc_m.getMessage() + ")");
        }
    }
    
    protected int c() {
        return this.m;
    }
    
    public String i() {
        return this.a.d();
    }
    
    public String j() {
        return this.b.f();
    }
    
    public String k() {
        return this.c.d();
    }
    
    public int n() {
        return this.g;
    }
    
    public int d() {
        return this.a.g();
    }
    
    public int[] p() {
        return this.a.c();
    }
    
    public void a(final int[] array) throws psc_be {
        if (this.a != null) {
            this.a.a(array);
            this.h = this.a.g();
            final int g = this.a.g();
            if (g != this.g) {
                if (this.i != null) {
                    for (int i = 0; i < this.i.length; ++i) {
                        this.i[i] = 0;
                    }
                }
                this.g = g;
                this.i = new byte[this.g];
                this.k = 0;
                this.l = 0;
                this.j = null;
                this.m = 1;
            }
        }
    }
    
    public int[] q() {
        return this.b.d();
    }
    
    public int[] r() {
        return this.c.c();
    }
    
    public void b(final byte[] array, int n, final int n2) throws psc_gw {
        if (n2 != this.b.a(this.h)) {
            throw new psc_gw("Invalid IV length. Should be " + this.b.a(this.h));
        }
        if (this.m == 3 || this.m == 4 || this.m == 6 || this.m == 7) {
            final byte[] j = new byte[n2];
            for (int i = 0; i < n2; ++i, ++n) {
                j[i] = array[n];
            }
            this.j = j;
        }
        else {
            this.b.b(array, n, n2);
            this.j = null;
        }
    }
    
    public void a(final SecureRandom secureRandom) throws psc_gw, psc_en {
        if (secureRandom == null) {
            throw new psc_en("IV generation needs a random object.");
        }
        final byte[] bytes = new byte[this.h];
        secureRandom.nextBytes(bytes);
        this.b(bytes, 0, bytes.length);
    }
    
    public byte[] t() {
        return this.b.j();
    }
    
    public int a(final int n) {
        final int n2 = n + this.k;
        return n2 + this.c.a(n2, this.g);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av e = this.e();
        objectOutputStream.defaultWriteObject();
        this.a(e);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.aa();
    }
    
    private psc_av e() {
        if (this.d == null) {
            return null;
        }
        if (!(this.d instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.d;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.f = psc_av.getAlgorithm();
        this.e = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.d;
        this.d = null;
        return psc_av2;
    }
    
    private void a(final psc_av d) {
        if (this.e == null) {
            return;
        }
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        this.e = null;
        this.f = null;
        this.d = d;
    }
    
    private void aa() {
        if (this.e == null) {
            return;
        }
        this.d = psc_av.a(this.f, this.e);
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        this.e = null;
        this.f = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_dk psc_dk = (psc_dk)super.clone();
        if (this.a != null) {
            psc_dk.a = (psc_dl)this.a.clone();
        }
        if (this.b != null) {
            psc_dk.b = (psc_di)this.b.clone();
        }
        psc_dk.c = this.c;
        psc_dk.d = this.d;
        psc_dk.g = this.g;
        if (this.i != null) {
            psc_dk.i = this.i.clone();
        }
        if (this.j != null) {
            psc_dk.j = this.j.clone();
        }
        psc_dk.k = this.k;
        psc_dk.l = this.l;
        psc_dk.m = this.m;
        psc_dk.a(this);
        return psc_dk;
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom d) throws psc_en, psc_bf, psc_gw {
        switch (this.m) {
            case 3:
            case 4:
            case 6:
            case 7: {
                if (this.j == null) {
                    this.b.b(this.b.j(), 0, this.h);
                    break;
                }
                this.b.b(this.j, 0, this.h);
                this.j = null;
                break;
            }
        }
        this.b.a(this.a, psc_dc, d);
        this.a.e();
        this.d = d;
        this.k = 0;
        this.m = 2;
    }
    
    public void v() throws psc_en, psc_gw {
        Label_0098: {
            switch (this.m) {
                case 3:
                case 4:
                case 6:
                case 7: {
                    if (this.j == null) {
                        this.b.b(this.b.j(), 0, this.h);
                        break Label_0098;
                    }
                    this.b.b(this.j, 0, this.h);
                    this.j = null;
                    break Label_0098;
                }
                case 2:
                case 5: {
                    this.k = 0;
                    this.m = 2;
                }
                default: {
                    throw new psc_en("Object not initialized.");
                }
            }
        }
    }
    
    protected byte[] a(final psc_am psc_am, final boolean b, final byte[] array, final int n, final int n2) throws psc_en {
        return this.a(psc_am, b, null, array, n, n2);
    }
    
    protected byte[] a(final psc_am psc_am, final boolean b, final String s, final byte[] array, final int n, final int n2) throws psc_en {
        if (this.m != 2) {
            throw new psc_en("Cannot wrap key, object needs new initialization.");
        }
        Label_0229: {
            if (this.a.a(b)) {
                final byte[] a = this.a.a(psc_am, b, this.b, this.c);
                final int length = a.length;
                this.m = 4;
                break Label_0229;
            }
            byte[][] b2 = null;
            byte[] a;
            int length;
            int a2;
            byte[] array2;
            Block_12_Outer:Label_0299_Outer:
            while (true) {
                try {
                    b2 = psc_am.b((s != null) ? s : psc_am.b(b));
                    a = new byte[this.a(b2[0].length)];
                    a2 = this.a(b2[0], 0, b2[0].length, a, 0);
                    length = a2 + this.b(a, a2);
                    break Block_12_Outer;
                }
                catch (psc_ao psc_ao) {
                    throw new psc_en("The key given cannot be wrapped.");
                }
                catch (psc_gw psc_gw) {
                    throw new psc_en(psc_gw.getMessage());
                }
                catch (psc_e1 psc_e1) {
                    throw new psc_en(psc_e1.getMessage());
                }
                catch (psc_gx psc_gx) {
                    throw new psc_en(psc_gx.getMessage());
                }
                finally {
                    Label_0322: {
                        if (b2 != null && b2.length > 0) {
                            psc_au.c(b2[0]);
                            break Label_0322;
                        }
                        break Label_0322;
                    }
                    while (true) {}
                    // iftrue(Label_0244:, length != a.length)
                    // iftrue(Label_0264:, b)
                    while (true) {
                        return a;
                        Label_0264: {
                            return psc_k5.a(array, n, n2, a, 0, length);
                        }
                        continue Label_0299_Outer;
                    }
                    Label_0244: {
                        array2 = new byte[length];
                    }
                    System.arraycopy(a, 0, array2, 0, length);
                    return array2;
                    while (true) {
                        continue Block_12_Outer;
                        psc_au.c(b2[0]);
                        continue;
                    }
                }
                // iftrue(Label_0299:, b2 == null || b2.length <= 0)
                break;
            }
        }
    }
    
    public int a(final byte[] array, int n, final int n2, final byte[] array2, int n3) throws psc_en, psc_gw {
        switch (this.m) {
            case 2: {
                if (this.b.h() && this.b.i() == 0) {
                    throw new psc_en("IV missing.");
                }
            }
            case 3: {
                if (n2 <= 0) {
                    this.m = 3;
                    return 0;
                }
                int n4 = 0;
                int i = this.k + n2;
                if (this.k != 0) {
                    if (i < this.g) {
                        for (int j = this.k; j < i; ++j, ++n) {
                            this.i[j] = array[n];
                        }
                        this.k = i;
                        this.m = 3;
                        return 0;
                    }
                    for (int k = this.k; k < this.g; ++k, ++n) {
                        this.i[k] = array[n];
                    }
                    this.a.f();
                    n4 += this.b.a(this.a, this.i, 0, array2, n3);
                    n3 += this.g;
                    i -= this.g;
                    this.k = 0;
                }
                this.a.f();
                if (this.u) {
                    n4 += this.b.a(this.a, array, n, array2, n3, i);
                    i = 0;
                }
                else {
                    while (i >= this.g) {
                        n4 += this.b.a(this.a, array, n, array2, n3);
                        n += this.g;
                        n3 += this.g;
                        i -= this.g;
                    }
                }
                this.a.e();
                this.k = i;
                if (this.k > 0) {
                    for (int l = 0; l < this.k; ++l, ++n) {
                        this.i[l] = array[n];
                    }
                }
                this.m = 3;
                return n4;
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
    
    public int b(final byte[] array, final int n) throws psc_en, psc_e1, psc_gw, psc_gx {
        switch (this.m) {
            case 2:
            case 3: {
                this.k += this.c.a(this.i, 0, this.k, this.g, null, this.d);
                if (this.k == 0) {
                    this.m = 4;
                    return 0;
                }
                if (this.k != this.g) {
                    throw new psc_e1("Invalid input length for encryption.Should be a multiple of the block size - " + this.g + ".");
                }
                this.a.f();
                final int a = this.b.a(this.a, this.i, 0, array, n);
                this.k = 0;
                this.a.e();
                this.m = 4;
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
    
    public void d(final psc_dc psc_dc, final SecureRandom d) throws psc_en, psc_bf, psc_gw {
        switch (this.m) {
            case 3:
            case 4:
            case 6:
            case 7: {
                if (this.j == null) {
                    this.b.b(this.b.j(), 0, this.h);
                    break;
                }
                this.b.b(this.j, 0, this.h);
                this.j = null;
                break;
            }
        }
        this.b.b(this.a, psc_dc, d);
        this.d = d;
        this.k = 0;
        this.l = ((this.c.a(0, this.g) == 0) ? (this.g - 1) : this.g);
        this.m = 5;
    }
    
    public void x() throws psc_en, psc_gw {
        Label_0098: {
            switch (this.m) {
                case 3:
                case 4:
                case 6:
                case 7: {
                    if (this.j == null) {
                        this.b.b(this.b.j(), 0, this.h);
                        break Label_0098;
                    }
                    this.b.b(this.j, 0, this.h);
                    this.j = null;
                    break Label_0098;
                }
                case 2:
                case 5: {
                    this.k = 0;
                    this.m = 5;
                }
                default: {
                    throw new psc_en("Object not initialized.");
                }
            }
        }
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final String s) throws psc_en {
        byte[] array2 = null;
        try {
            array2 = new byte[this.a(n2)];
            final int b = this.b(array, n, n2, array2, 0);
            final int n3 = b + this.c(array2, b);
            return psc_dt.a(array2, 0, s);
        }
        catch (psc_ao psc_ao) {
            throw new psc_en("Could not unwrap private key.");
        }
        catch (psc_gw psc_gw) {
            throw new psc_en(psc_gw.getMessage());
        }
        catch (psc_e1 psc_e1) {
            throw new psc_en(psc_e1.getMessage());
        }
        catch (psc_gx psc_gx) {
            throw new psc_en(psc_gx.getMessage());
        }
        finally {
            if (array2 != null) {
                psc_au.c(array2);
            }
        }
    }
    
    public psc_dt a(final byte[] array, int n, int n2, final boolean b, String g) throws psc_en {
        if (this.m != 5) {
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
            this.m = 7;
            return this.a.a(array, n, n2, this.b, this.c, g);
        }
        return this.a(array, n, n2, g);
    }
    
    public psc_al b(final byte[] array, int n, int n2, final boolean b, String g) throws psc_en {
        if (this.m != 5) {
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
            this.m = 7;
            return this.a.b(array, n, n2, this.b, this.c, g);
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
        catch (psc_gw psc_gw) {
            throw new psc_en(psc_gw.getMessage());
        }
        catch (psc_e1 psc_e1) {
            throw new psc_en(psc_e1.getMessage());
        }
        catch (psc_gx psc_gx) {
            throw new psc_en(psc_gx.getMessage());
        }
    }
    
    public psc_dc a(final byte[] array, int n, int n2, final boolean b, final String s, String g) throws psc_en {
        if (this.m != 5) {
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
            this.m = 7;
            return this.a.a(array, n, n2, b, this.b, this.c, g);
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
        catch (psc_gw psc_gw) {
            throw new psc_en(psc_gw.getMessage());
        }
        catch (psc_e1 psc_e1) {
            throw new psc_en(psc_e1.getMessage());
        }
        catch (psc_gx psc_gx) {
            throw new psc_en(psc_gx.getMessage());
        }
        finally {
            if (array2 != null) {
                psc_au.c(array2);
            }
        }
    }
    
    public int b(final byte[] array, int n, final int n2, final byte[] array2, int n3) throws psc_en, psc_gw {
        switch (this.m) {
            case 5: {
                if (this.b.h() && this.b.i() == 0) {
                    throw new psc_en("IV missing.");
                }
            }
            case 6: {
                if (n2 <= 0) {
                    this.m = 6;
                    return 0;
                }
                int n4 = 0;
                if (this.u) {
                    this.a.f();
                    n4 += this.b.b(this.a, array, n, array2, n3, n2);
                    this.a.e();
                }
                else {
                    int i = n2 + this.k;
                    final int n5 = this.g - this.k;
                    if (n5 == this.g) {
                        if (i <= this.l) {
                            for (int j = 0; j < i; ++j, ++n) {
                                this.i[j] = array[n];
                            }
                            this.k = i;
                            this.m = 6;
                            return 0;
                        }
                    }
                    else if (n5 == 0) {
                        this.a.f();
                        n4 += this.b.b(this.a, this.i, 0, array2, n3);
                        n3 += this.g;
                        i -= this.g;
                        this.k = 0;
                    }
                    else {
                        if (i <= this.l) {
                            for (int k = this.k; k < i; ++k, ++n) {
                                this.i[k] = array[n];
                            }
                            this.k = i;
                            this.m = 6;
                            return 0;
                        }
                        for (int l = this.k; l < this.g; ++l, ++n) {
                            this.i[l] = array[n];
                        }
                        this.a.f();
                        n4 += this.b.b(this.a, this.i, 0, array2, n3);
                        n3 += this.g;
                        i -= this.g;
                        this.k = 0;
                    }
                    this.a.f();
                    while (i > this.l) {
                        n4 += this.b.b(this.a, array, n, array2, n3);
                        n += this.g;
                        n3 += this.g;
                        i -= this.g;
                    }
                    this.a.e();
                    if ((this.k = i) > 0) {
                        for (int n6 = 0; n6 < this.k; ++n6, ++n) {
                            this.i[n6] = array[n];
                        }
                    }
                }
                this.m = 6;
                return n4;
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
    
    public int c(final byte[] array, int n) throws psc_en, psc_e1, psc_gx {
        switch (this.m) {
            case 5:
            case 6: {
                int a = 0;
                if (this.k != 0) {
                    if (this.k != this.g) {
                        throw new psc_e1("Invalid input length for decryption.Should be a multiple of the block size - " + this.g + ".");
                    }
                    this.a.f();
                    final byte[] array2 = new byte[this.g];
                    this.b.b(this.a, this.i, 0, array2, 0);
                    this.a.e();
                    a = this.c.a(array2, 0, this.g, null);
                    this.k = 0;
                    if (a > 0) {
                        for (int i = 0; i < a; ++i, ++n) {
                            array[n] = array2[i];
                        }
                    }
                    for (int j = 0; j < this.g; ++j) {
                        array2[j] = 0;
                    }
                }
                this.m = 7;
                return a;
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
        if (this.b != null) {
            this.b.y();
        }
        if (this.i != null) {
            this.d(this.i);
        }
        this.k = 0;
        this.m = 1;
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
