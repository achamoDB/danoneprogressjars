import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_is extends psc_iq implements Cloneable, Serializable
{
    private psc_ir a;
    private psc_iv b;
    private SecureRandom c;
    private byte[] d;
    private String e;
    private int f;
    private int g;
    private int h;
    private byte[] i;
    private int j;
    private int k;
    private int l;
    private static final int m = 1;
    private static final int n = 2;
    private static final int o = 1;
    private static final int p = 2;
    private static final int q = 3;
    private static final int r = 4;
    private static final int s = 5;
    private static final int t = 6;
    private static final int u = 7;
    private static final int v = -1;
    
    public psc_is(final psc_ir a, final psc_iv b) {
        this.a = a;
        this.b = b;
        this.k = 1;
    }
    
    private psc_is() {
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (this.b instanceof psc_iu) {
            ((psc_iu)this.b).a(array, n, n2);
        }
    }
    
    public byte[] o() {
        if (this.b instanceof psc_iu) {
            return ((psc_iu)this.b).j();
        }
        return null;
    }
    
    public String p() {
        return this.a.d();
    }
    
    protected void b(final byte[] array, final int n) throws psc_ao, psc_be {
        this.a.a(array, n);
    }
    
    public byte[] c() throws psc_ao {
        return this.a.a(this.b);
    }
    
    public String q() {
        return this.b.d();
    }
    
    public String r() {
        if (this.b instanceof psc_iu) {
            return ((psc_iu)this.b).f();
        }
        return null;
    }
    
    public String s() {
        if (this.b instanceof psc_iu) {
            return ((psc_iu)this.b).g();
        }
        return null;
    }
    
    public String t() {
        if (this.b instanceof psc_iu) {
            return ((psc_iu)this.b).h();
        }
        return null;
    }
    
    public String u() {
        if (this.b instanceof psc_iu) {
            return ((psc_iu)this.b).i();
        }
        return null;
    }
    
    protected int d() {
        return this.a.b(this.b);
    }
    
    protected int e() {
        return this.f;
    }
    
    protected int f() {
        return this.g;
    }
    
    public int[] aa() {
        return this.a.c();
    }
    
    protected int a(final int n) {
        final int n2 = n + this.j;
        return n2 + this.b.a(n2, this.f);
    }
    
    public void c(final int n) {
        if (this.a != null) {
            this.a.a(n);
        }
    }
    
    public int ac() {
        if (this.a != null) {
            return this.a.j();
        }
        return 0;
    }
    
    protected String a(final psc_al psc_al, final SecureRandom c, final psc_nf[] array, final int[] array2) {
        array2[0] = 3;
        this.l = 1;
        switch (this.k) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7: {
                if (c != null) {
                    this.c = c;
                }
                if (this.b.e() && this.c == null) {
                    return "This operation needs a random object.";
                }
                array2[0] = 1;
                final String a = this.a.a(psc_al, this.b, c, array);
                if (a != null) {
                    return a;
                }
                this.a.k();
                this.f = this.a.f();
                this.g = this.a.g();
                this.i = new byte[this.f];
                this.h = this.b.a(this.f);
                this.j = 0;
                this.k = 2;
                return null;
            }
            default: {
                return "Object already initialized.";
            }
        }
    }
    
    protected String a(final psc_dt psc_dt, final SecureRandom c, final psc_nf[] array, final int[] array2) {
        array2[0] = 3;
        this.l = 2;
        switch (this.k) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7: {
                if (c != null) {
                    this.c = c;
                }
                if (this.b.e() && this.c == null) {
                    return "This operation needs a random object.";
                }
                array2[0] = 1;
                final String a = this.a.a(psc_dt, this.b, c, array);
                if (a != null) {
                    return a;
                }
                this.a.k();
                this.f = this.a.f();
                this.g = this.a.g();
                this.i = new byte[this.f];
                this.h = this.b.a(this.f);
                this.j = 0;
                this.k = 2;
                return null;
            }
            default: {
                return "Object already initialized.";
            }
        }
    }
    
    protected void g() throws psc_en {
        switch (this.k) {
            case 2:
            case 3:
            case 4: {
                this.j = 0;
                this.k = 2;
            }
            case 5:
            case 6:
            case 7: {
                throw new psc_en("Object initialized for decryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    protected byte[] a(final psc_dc psc_dc, final boolean b) throws psc_en {
        if (this.k != 2) {
            throw new psc_en("Cannot wrap key, object needs new initialization.");
        }
        byte[] l = null;
        int length = 0;
        if (b) {
            try {
                l = this.l();
                if (l == null) {
                    throw new psc_en("Cannot wrap the given key into a BER.");
                }
                length = l.length;
            }
            catch (psc_ao psc_ao2) {
                throw new psc_en("Cannot wrap the given key into a BER.");
            }
        }
        byte[] a = null;
        int n = 0;
        if (this.a.a(b)) {
            a = this.a.a(psc_dc, b, this.b);
            n = a.length;
            this.k = 4;
        }
        else {
            byte[] a2 = null;
            try {
                a2 = psc_dc.a(psc_dc.b(b));
                n = this.b(a2.length);
                a = new byte[n];
                n = this.c(a2, 0, a2.length, a, 0);
                n += this.f(a, n);
            }
            catch (psc_e1 psc_e1) {
                throw new psc_en(psc_e1.getMessage());
            }
            catch (psc_ao psc_ao) {
                throw new psc_en(psc_ao.getMessage());
            }
            catch (psc_gx psc_gx) {
                throw new psc_en(psc_gx.getMessage());
            }
            finally {
                if (a2 != null) {
                    this.d(a2);
                }
            }
        }
        if (b) {
            return psc_k5.a(l, 0, length, a, 0, n);
        }
        if (n == a.length) {
            return a;
        }
        final byte[] array = new byte[n];
        System.arraycopy(a, 0, array, 0, n);
        return array;
    }
    
    protected int a(final byte[] array, int n, final int n2, final byte[] array2, int n3) throws psc_en, psc_e1 {
        switch (this.k) {
            case 2:
            case 3: {
                if (n2 <= 0) {
                    this.k = 3;
                    return 0;
                }
                int n4 = 0;
                int i = this.j + n2;
                if (this.h != -1 && i > this.h) {
                    throw new psc_e1("Invalid Input Length");
                }
                if (this.j != 0) {
                    if (i < this.f) {
                        for (int j = this.j; j < i; ++j, ++n) {
                            this.i[j] = array[n];
                        }
                        this.j = i;
                        this.k = 3;
                        return 0;
                    }
                    for (int k = this.j; k < this.f; ++k, ++n) {
                        this.i[k] = array[n];
                    }
                    if (!this.a.b(this.i, 0)) {
                        throw new psc_e1("Invalid Input Data");
                    }
                    this.a.l();
                    int n5;
                    if (this.l == 1) {
                        n5 = this.a.a(this.i, 0, array2, n3);
                    }
                    else {
                        if (this.l != 2) {
                            throw new psc_en("Invalid key type.");
                        }
                        n5 = this.a.b(this.i, 0, array2, n3);
                    }
                    n4 += n5;
                    if (n5 == 0) {
                        throw new psc_en("Could not perform RSA operation.");
                    }
                    n3 += this.g;
                    i -= this.f;
                    this.j = 0;
                }
                while (i >= this.f) {
                    if (!this.a.b(array, n)) {
                        throw new psc_e1("Invalid Input Data");
                    }
                    this.a.l();
                    int n6;
                    if (this.l == 1) {
                        n6 = this.a.a(array, n, array2, n3);
                    }
                    else {
                        if (this.l != 2) {
                            throw new psc_en("Invalid key type.");
                        }
                        n6 = this.a.b(array, n, array2, n3);
                    }
                    n4 += n6;
                    if (n6 == 0) {
                        throw new psc_en("Could not perform RSA operation.");
                    }
                    n += this.f;
                    n3 += this.g;
                    i -= this.f;
                }
                this.a.k();
                this.j = i;
                if (this.j > 0) {
                    for (int l = 0; l < this.j; ++l, ++n) {
                        this.i[l] = array[n];
                    }
                }
                this.k = 3;
                return n4;
            }
            case 5:
            case 6:
            case 7: {
                throw new psc_en("Object initialized for decrypt");
            }
            default: {
                throw new psc_en("Object not initialized");
            }
        }
    }
    
    protected int c(final byte[] array, final int n) throws psc_en, psc_e1, psc_gx {
        switch (this.k) {
            case 2:
            case 3: {
                this.j += this.b.a(this.i, 0, this.j, this.f, null, this.c);
                if (this.j == 0) {
                    this.k = 4;
                    return 0;
                }
                if (this.j != this.f) {
                    throw new psc_e1("Invalid input length for encryption. Length should be multiple of " + this.f + " - Block Size.");
                }
                if (!this.a.b(this.i, 0)) {
                    throw new psc_e1("Invalid input.");
                }
                this.a.l();
                int n2;
                if (this.l == 1) {
                    n2 = this.a.a(this.i, 0, array, n);
                }
                else {
                    if (this.l != 2) {
                        throw new psc_en("Invalid key type.");
                    }
                    n2 = this.a.b(this.i, 0, array, n);
                }
                if (n2 == 0) {
                    throw new psc_en("Could not perform RSA operation.");
                }
                this.j = 0;
                this.a.k();
                this.k = 4;
                return n2;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for decryption");
            }
            default: {
                throw new psc_en("Object not initialized");
            }
        }
    }
    
    public String a(final psc_dt psc_dt, final psc_nf[] array, final int[] array2) {
        array2[0] = 3;
        this.l = 2;
        switch (this.k) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7: {
                array2[0] = 1;
                final String b = this.a.b(psc_dt, this.b, null, array);
                if (b != null) {
                    return b;
                }
                this.a.k();
                this.f = this.a.h();
                this.g = this.a.i();
                this.i = new byte[this.f];
                this.h = this.b.a(this.f);
                if (this.h != -1) {
                    this.h = this.f;
                }
                this.j = 0;
                this.k = 5;
                return null;
            }
            default: {
                return "Object already initialized.";
            }
        }
    }
    
    public String a(final psc_al psc_al, final psc_nf[] array, final int[] array2) {
        array2[0] = 3;
        this.l = 1;
        switch (this.k) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7: {
                array2[0] = 1;
                final String b = this.a.b(psc_al, this.b, null, array);
                if (b != null) {
                    return b;
                }
                this.a.k();
                this.f = this.a.h();
                this.g = this.a.i();
                this.i = new byte[this.f];
                this.h = this.b.a(this.f);
                if (this.h != -1) {
                    this.h = this.f;
                }
                this.j = 0;
                this.k = 5;
                return null;
            }
            default: {
                return "Object already initialized.";
            }
        }
    }
    
    protected void h() throws psc_en {
        switch (this.k) {
            case 5:
            case 6:
            case 7: {
                this.j = 0;
                this.k = 5;
            }
            case 2:
            case 3:
            case 4: {
                throw new psc_en("Object initialized for encryption.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public psc_dc a(final byte[] array, int n, int n2, final boolean b, final String s, String m) throws psc_en {
        if (this.k != 5) {
            throw new psc_en("Cannot unwrap key, object needs new initialization.");
        }
        if (m == null) {
            m = this.m();
        }
        if (b) {
            final int[] a = psc_k5.a(array, n);
            n = a[0];
            n2 = a[1];
        }
        if (this.a.a(b)) {
            this.k = 4;
            return this.a.a(array, n, n2, b, this.b, m);
        }
        byte[] array2 = null;
        try {
            array2 = new byte[this.b(n2)];
            final int d = this.d(array, n, n2, array2, 0);
            final int n3 = d + this.g(array2, d);
            psc_dc psc_dc;
            if (b) {
                psc_dc = psc_dc.a(array2, 0, m);
            }
            else {
                psc_dc = psc_dc.a(s, m);
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
        catch (psc_e1 psc_e1) {
            throw new psc_en(psc_e1.getMessage());
        }
        catch (psc_gx psc_gx) {
            throw new psc_en(psc_gx.getMessage());
        }
        finally {
            if (array2 != null) {
                this.d(array2);
            }
        }
    }
    
    public int b(final byte[] array, int n, final int n2, final byte[] array2, int n3) throws psc_en, psc_e1 {
        switch (this.k) {
            case 5:
            case 6: {
                if (n2 <= 0) {
                    this.k = 6;
                    return 0;
                }
                int n4 = 0;
                int i = n2 + this.j;
                final int n5 = this.f - this.j;
                if (this.h != -1 && i > this.h) {
                    throw new psc_e1("Invalid input Length.");
                }
                final int n6 = (this.b.a(0, this.f) == 0) ? (this.f - 1) : this.f;
                if (n5 == this.f) {
                    if (i <= n6) {
                        for (int j = 0; j < i; ++j, ++n) {
                            this.i[j] = array[n];
                        }
                        this.j = i;
                        this.k = 6;
                        return 0;
                    }
                }
                else if (n5 == 0) {
                    this.a.l();
                    if (this.l == 2) {
                        n4 += this.a.b(this.i, 0, array2, n3);
                    }
                    else {
                        if (this.l != 1) {
                            throw new psc_en("Invalid key type.");
                        }
                        n4 += this.a.a(this.i, 0, array2, n3);
                    }
                    n3 += this.g;
                    i -= this.f;
                    this.j = 0;
                }
                else {
                    if (i <= n6) {
                        for (int k = this.j; k < i; ++k, ++n) {
                            this.i[k] = array[n];
                        }
                        this.j = i;
                        this.k = 6;
                        return 0;
                    }
                    for (int l = this.j; l < this.f; ++l, ++n) {
                        this.i[l] = array[n];
                    }
                    this.a.l();
                    if (this.l == 2) {
                        n4 += this.a.b(this.i, 0, array2, n3);
                    }
                    else {
                        if (this.l != 1) {
                            throw new psc_en("Invalid key type.");
                        }
                        n4 += this.a.a(this.i, 0, array2, n3);
                    }
                    n3 += this.g;
                    i -= this.f;
                    this.j = 0;
                }
                this.a.l();
                while (i > n6) {
                    if (this.l == 2) {
                        n4 += this.a.b(array, n, array2, n3);
                    }
                    else {
                        if (this.l != 1) {
                            throw new psc_en("Invalid key type.");
                        }
                        n4 += this.a.a(array, n, array2, n3);
                    }
                    n += this.f;
                    n3 += this.g;
                    i -= this.f;
                }
                this.a.k();
                if ((this.j = i) > 0) {
                    for (int n7 = 0; n7 < this.j; ++n7, ++n) {
                        this.i[n7] = array[n];
                    }
                }
                this.k = 6;
                return n4;
            }
            case 2:
            case 3:
            case 4: {
                throw new psc_en("Object Not Initialized for Decrypt");
            }
            default: {
                throw new psc_en("Object not initialized");
            }
        }
    }
    
    public int d(final byte[] array, int n) throws psc_en, psc_e1, psc_gx {
        switch (this.k) {
            case 5:
            case 6: {
                final int n2 = 0;
                int a = 0;
                if (this.j != 0) {
                    if (this.j != this.f) {
                        throw new psc_e1("Invalid input length for decryption. Length should be multiple of " + this.f + " - Block Size.");
                    }
                    final byte[] array2 = new byte[this.g];
                    this.a.l();
                    if (this.l == 2) {
                        final int n3 = n2 + this.a.b(this.i, 0, array2, 0);
                    }
                    else {
                        if (this.l != 1) {
                            throw new psc_en("Invalid key type.");
                        }
                        final int n4 = n2 + this.a.a(this.i, 0, array2, 0);
                    }
                    this.a.k();
                    a = this.b.a(array2, 0, this.g, null);
                    if (a > 0) {
                        for (int i = 0; i < a; ++i, ++n) {
                            array[n] = array2[i];
                        }
                    }
                    this.d(array2);
                }
                this.k = 7;
                return a;
            }
            case 2:
            case 3:
            case 4: {
                throw new psc_en("Object not initialized for Decrypt.");
            }
            default: {
                throw new psc_en("Object Not Initialized");
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av j = this.j();
        objectOutputStream.defaultWriteObject();
        this.a(j);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.k();
    }
    
    private psc_av j() {
        if (this.c == null) {
            return null;
        }
        if (!(this.c instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.c;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.e = psc_av.getAlgorithm();
        this.d = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.c;
        this.c = null;
        return psc_av2;
    }
    
    private void a(final psc_av c) {
        if (this.d == null) {
            return;
        }
        for (int i = 0; i < this.d.length; ++i) {
            this.d[i] = 0;
        }
        this.d = null;
        this.e = null;
        this.c = c;
    }
    
    private void k() {
        if (this.d == null) {
            return;
        }
        this.c = psc_av.a(this.e, this.d);
        for (int i = 0; i < this.d.length; ++i) {
            this.d[i] = 0;
        }
        this.d = null;
        this.e = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_is psc_is = new psc_is();
        if (this.a != null) {
            psc_is.a = (psc_ir)this.a.clone();
        }
        if (this.b != null) {
            psc_is.b = (psc_iv)this.b.clone();
        }
        psc_is.c = this.c;
        psc_is.f = this.f;
        psc_is.g = this.g;
        psc_is.h = this.h;
        if (this.i != null) {
            psc_is.i = this.i.clone();
        }
        psc_is.j = this.j;
        psc_is.k = this.k;
        psc_is.l = this.l;
        psc_is.a(this);
        return psc_is;
    }
    
    public void y() {
        super.y();
    }
    
    protected void i() {
        if (this.a != null) {
            this.a.y();
        }
        this.d(this.i);
        this.k = 1;
    }
    
    protected void finalize() {
        this.y();
    }
}
