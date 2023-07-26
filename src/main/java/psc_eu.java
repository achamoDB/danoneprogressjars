import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_eu extends psc_eo implements Cloneable, Serializable
{
    private psc_az a;
    private psc_ep b;
    private psc_et c;
    private SecureRandom d;
    private byte[] e;
    private String f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private byte[] k;
    private int l;
    private int m;
    private static final int n = 1;
    private static final int o = 2;
    private static final int p = 3;
    private static final int q = 4;
    private static final int r = 5;
    private static final int s = 6;
    private static final int t = 7;
    
    public psc_eu(final psc_az a, final psc_ep b, final psc_et c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.m = 1;
    }
    
    protected psc_eu() {
    }
    
    protected byte[] a(final String s, final boolean b) throws psc_ao {
        if (this.b != null) {
            return this.b.a(this.a, this.c, s, b);
        }
        return null;
    }
    
    protected void b(final byte[] array, final int n) throws psc_ao, psc_be {
        this.b.a(array, n);
    }
    
    public String m() {
        return this.b.d();
    }
    
    public String n() {
        return this.a.e();
    }
    
    public String o() {
        if (this.c == null) {
            return "NoPad";
        }
        return this.c.d();
    }
    
    protected int c() {
        return this.i;
    }
    
    protected int d() {
        return this.h;
    }
    
    public void a(final int n) {
        if (this.b != null) {
            this.b.a(n);
        }
    }
    
    public int r() {
        if (this.b != null) {
            return this.b.j();
        }
        return 0;
    }
    
    protected String a(final psc_dt psc_dt, final psc_el psc_el, final SecureRandom d, final psc_nf[] array, final int[] array2) {
        if (d != null) {
            this.d = d;
        }
        if (psc_el != null) {
            array2[0] = 2;
            final String a = this.b.a(psc_el);
            if (a != null) {
                return a;
            }
        }
        if (this.d == null) {
            array2[0] = 3;
            if (this.c.e() || this.b.r()) {
                return "This operation needs a random object.";
            }
        }
        this.a.j();
        array2[0] = 1;
        final String a2 = this.b.a(psc_dt, this.a, this.c, d, array);
        if (a2 != null) {
            return a2;
        }
        this.b.k();
        this.j = true;
        if (this.b.n()) {
            this.j = false;
        }
        this.g = this.b.o();
        this.h = this.b.q();
        if (this.a.e().equals("NoDigest") && this.c.d().equals("NoPad")) {
            this.i = this.g;
        }
        else {
            this.i = this.b.p();
        }
        if (this.i == 0) {
            return "Invalid Key Length";
        }
        if (this.k != null) {
            this.d(this.k);
            this.k = null;
        }
        this.l = 0;
        if (this.i > 0) {
            if (this.g > this.i) {
                this.k = new byte[this.g];
            }
            else {
                this.k = new byte[this.i];
            }
        }
        this.m = 2;
        return null;
    }
    
    protected void e() throws psc_en {
        switch (this.m) {
            case 2:
            case 3:
            case 4: {
                this.l = 0;
                this.a.j();
                this.b.s();
                this.m = 2;
            }
            case 5:
            case 6:
            case 7: {
                throw new psc_en("Object not initialized for signing");
            }
            default: {
                throw new psc_en("Object not initialized");
            }
        }
    }
    
    protected void a(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        switch (this.m) {
            case 2:
            case 3: {
                if (n2 <= 0) {
                    return;
                }
                if (!this.j) {
                    this.b.a(array, n, n2);
                    return;
                }
                if (this.k == null) {
                    this.a.a(array, n, n2);
                    this.m = 3;
                    return;
                }
                if (this.l + n2 > this.i) {
                    throw new psc_e1("Invalid input length. Maximum: " + this.i + " bytes.");
                }
                System.arraycopy(array, n, this.k, this.l, n2);
                this.l += n2;
                this.m = 3;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for verification");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    protected int c(final byte[] array, final int n) throws psc_en, psc_e1, psc_gx {
        switch (this.m) {
            case 2:
            case 3: {
                if (this.j && this.k == null) {
                    this.k = new byte[this.g];
                    this.l = this.a.b(this.k, 0);
                }
                if (this.k != null) {
                    this.l += this.c.a(this.k, 0, this.l, this.g, this.a, this.d);
                }
                this.b.l();
                final int a = this.b.a(this.k, 0, this.l, this.a, this.c, array, n);
                this.b.k();
                if (a == 0) {
                    throw new psc_e1("Invalid input length");
                }
                if (this.k != null) {
                    this.d(this.k);
                    this.k = null;
                    this.l = 0;
                }
                this.m = 4;
                return a;
            }
            case 5:
            case 6: {
                throw new psc_en("Object initialized for verification.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    protected String a(final psc_al psc_al, final psc_el psc_el, final SecureRandom d, final psc_nf[] array, final int[] array2) {
        if (d != null) {
            this.d = d;
        }
        if (psc_el != null) {
            array2[0] = 2;
            final String a = this.b.a(psc_el);
            if (a != null) {
                return a;
            }
        }
        this.a.j();
        array2[0] = 1;
        final String a2 = this.b.a(psc_al, this.a, this.c, d, array);
        if (a2 != null) {
            return a2;
        }
        this.b.k();
        this.j = true;
        if (this.b.n()) {
            this.j = false;
        }
        this.g = this.b.o();
        if (this.a.e().equals("NoDigest") && this.c.d().equals("NoPad")) {
            this.i = this.g;
        }
        else {
            this.i = this.b.p();
        }
        if (this.i == 0) {
            return "Invalid Key Length";
        }
        if (this.k != null) {
            this.d(this.k);
            this.k = null;
        }
        this.l = 0;
        if (this.i > 0) {
            if (this.g > this.i) {
                this.k = new byte[this.g];
            }
            else {
                this.k = new byte[this.i];
            }
        }
        this.m = 5;
        return null;
    }
    
    protected void f() throws psc_en {
        switch (this.m) {
            case 5:
            case 6:
            case 7: {
                this.l = 0;
                this.a.j();
                this.b.t();
                this.m = 5;
            }
            case 2:
            case 3:
            case 4: {
                throw new psc_en("Object not initialized for verification.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    protected void b(final byte[] array, final int n, final int n2) throws psc_en, psc_e1 {
        switch (this.m) {
            case 5:
            case 6: {
                if (n2 <= 0) {
                    return;
                }
                if (!this.j) {
                    this.b.b(array, n, n2);
                    return;
                }
                if (this.k == null) {
                    this.a.a(array, n, n2);
                    this.m = 6;
                    return;
                }
                if (this.l + n2 > this.i) {
                    throw new psc_e1("Invalid input length. Maximum: " + this.i + " bytes.");
                }
                System.arraycopy(array, n, this.k, this.l, n2);
                this.l += n2;
                this.m = 6;
            }
            case 2:
            case 3: {
                throw new psc_en("Object initialized for signing.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    protected boolean c(final byte[] array, final int n, final int n2) throws psc_en, psc_e1, psc_gx {
        switch (this.m) {
            case 5:
            case 6: {
                if (this.j && this.k == null) {
                    this.k = new byte[this.g];
                    this.l = this.a.b(this.k, 0);
                }
                this.b.l();
                final boolean a = this.b.a(this.k, 0, this.l, this.a, this.c, array, n, n2);
                this.b.k();
                this.d(this.k);
                this.l = 0;
                this.k = null;
                this.m = 7;
                return a;
            }
            case 2:
            case 3: {
                throw new psc_en("Object initialized for signing");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av h = this.h();
        objectOutputStream.defaultWriteObject();
        this.a(h);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
        this.i();
    }
    
    private psc_av h() {
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
    
    private void i() {
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
        final psc_eu psc_eu = new psc_eu();
        if (this.a != null) {
            psc_eu.a = (psc_az)this.a.clone();
        }
        if (this.b != null) {
            psc_eu.b = (psc_ep)this.b.clone();
        }
        if (this.c != null) {
            psc_eu.c = (psc_et)this.c.clone();
        }
        psc_eu.d = this.d;
        psc_eu.g = this.g;
        psc_eu.h = this.h;
        psc_eu.i = this.i;
        if (this.k != null) {
            psc_eu.k = this.k.clone();
        }
        psc_eu.l = this.l;
        psc_eu.j = this.j;
        psc_eu.m = this.m;
        psc_eu.a(this);
        return psc_eu;
    }
    
    public void y() {
        super.y();
    }
    
    public void g() {
        if (this.b != null) {
            this.b.y();
        }
        if (this.a != null) {
            this.a.y();
        }
        this.d(this.k);
        this.k = null;
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
