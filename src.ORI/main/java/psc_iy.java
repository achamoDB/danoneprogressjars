import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_iy extends psc_iw implements Cloneable, Serializable
{
    private psc_ix a;
    private SecureRandom b;
    private byte[] c;
    private String d;
    private int e;
    private boolean f;
    private int g;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 4;
    private int l;
    private static final int m = 1;
    private static final int n = 2;
    private static final int o = 3;
    private static final int p = 4;
    
    psc_iy(final psc_ix a) {
        this.a = a;
        this.e = 0;
        this.f = false;
        this.l = 1;
        this.g = 1;
    }
    
    private psc_iy() {
    }
    
    public byte[] e() throws psc_ao {
        return this.a.e();
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        this.f = this.a.a(array, n);
        this.e = this.a.f();
    }
    
    public String h() {
        return this.a.d();
    }
    
    public int k() {
        return this.e;
    }
    
    public psc_el i() throws psc_en {
        if (!this.f) {
            throw new psc_en("Parameters have not been set.");
        }
        final byte[][] g = this.a.g();
        psc_el a;
        try {
            a = psc_el.a(this.a.d(), this.f());
            a.a(g);
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not create a JSAFE_Parameters object.");
        }
        return a;
    }
    
    public void a(final SecureRandom b) throws psc_en {
        if (!this.f) {
            throw new psc_en("Parameters have not been set.");
        }
        if (b != null) {
            this.b = b;
        }
        if (this.b == null) {
            throw new psc_en("Random number generator not set.");
        }
        this.a.a(this.b);
        this.e = this.a.f();
        this.l = 2;
        this.g = 2;
    }
    
    public void a(final psc_el psc_el, final SecureRandom b) throws psc_be, psc_en {
        if (b != null) {
            this.b = b;
        }
        if (this.b == null) {
            throw new psc_en("Random number generator not set.");
        }
        this.a.a(psc_el);
        this.a.a(this.b);
        this.e = this.a.f();
        this.f = true;
        this.l = 2;
        this.g = 2;
    }
    
    public void a(final psc_el psc_el, final psc_al psc_al, final SecureRandom b) throws psc_be, psc_bf, psc_en {
        if (b != null) {
            this.b = b;
        }
        if (this.b == null) {
            throw new psc_en("Random number generator not set.");
        }
        this.a.a(psc_el);
        this.a.a(this.b);
        this.e = this.a.f();
        this.a.b(psc_al);
        this.f = true;
        this.l = 3;
        this.g = 2;
    }
    
    public void a(final psc_el psc_el, final psc_dt psc_dt, final SecureRandom b) throws psc_be, psc_bf {
        if (b != null) {
            this.b = b;
        }
        this.a.a(psc_el);
        this.a.a(this.b);
        this.e = this.a.f();
        this.a.b(psc_dt);
        this.f = true;
        this.l = 4;
        this.g = 3;
    }
    
    public void a(final psc_al psc_al, final SecureRandom b) throws psc_bf, psc_be, psc_en {
        if (b != null) {
            this.b = b;
        }
        if (this.b == null) {
            throw new psc_en("Random number generator not set.");
        }
        if (this.a.a(psc_al)) {
            this.f = true;
        }
        if (!this.f) {
            throw new psc_en("Parameters have not been set.");
        }
        this.a.a(this.b);
        this.e = this.a.f();
        this.a.b(psc_al);
        this.l = 3;
        this.g = 2;
    }
    
    public void a(final psc_dt psc_dt, final SecureRandom b) throws psc_bf, psc_be, psc_en {
        if (b != null) {
            this.b = b;
        }
        if (this.a.a(psc_dt)) {
            this.f = true;
        }
        if (!this.f) {
            throw new psc_en("Parameters have not been set.");
        }
        this.a.a(this.b);
        this.e = this.a.f();
        this.a.b(psc_dt);
        this.l = 4;
        this.g = 3;
    }
    
    public void l() throws psc_en {
        switch (this.g) {
            case 2:
            case 3:
            case 4: {
                switch (this.l) {
                    case 2:
                    case 3: {
                        this.g = 2;
                    }
                    case 4: {
                        this.g = 3;
                        break;
                    }
                }
            }
            default: {
                throw new psc_en("Object Not Initialized");
            }
        }
    }
    
    public int c(final byte[] array, final int n) throws psc_en {
        if (this.l != 2) {
            throw new psc_en("Improper method call order.");
        }
        switch (this.g) {
            case 2: {
                this.a.h();
                this.a.i();
                final int c = this.a.c(array, n);
                this.g = 3;
                return c;
            }
            case 3:
            case 4: {
                throw new psc_en("Improper method call order.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public psc_al n() throws psc_en {
        if (this.l != 3) {
            throw new psc_en("Improper method call order.");
        }
        switch (this.g) {
            case 2: {
                this.a.h();
                this.a.i();
                try {
                    final psc_al a = psc_al.a(this.a.d(), this.f());
                    final byte[][] g = this.a.g();
                    final byte[] array = new byte[this.e];
                    this.a.c(array, 0);
                    final byte[][] array2 = new byte[g.length + 1][];
                    int i;
                    for (i = 0; i < g.length; ++i) {
                        array2[i] = g[i];
                    }
                    array2[i] = array;
                    a.c(array2);
                    this.g = 3;
                    return a;
                }
                catch (psc_ap psc_ap) {
                    throw new psc_en("Could not build public key.");
                }
            }
            case 3:
            case 4: {
                throw new psc_en("Improper method call order.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_en {
        if (this.l != 2) {
            throw new psc_en("Improper method call order.");
        }
        switch (this.g) {
            case 2: {
                throw new psc_en("Improper method call order.");
            }
            case 3:
            case 4: {
                this.a.a(array, n, n2);
                final int d = this.a.d(array2, n3);
                this.g = 4;
                return d;
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int d(final byte[] array, final int n) throws psc_en {
        if (this.l != 3) {
            throw new psc_en("Improper method call order.");
        }
        switch (this.g) {
            case 2: {
                throw new psc_en("Improper method call order.");
            }
            case 3: {
                final int d = this.a.d(array, n);
                this.g = 4;
                return d;
            }
            case 4: {
                throw new psc_en("Improper method call order.");
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int a(final psc_al psc_al, final byte[] array, final int n) throws psc_en, psc_bf {
        if (this.l != 4) {
            throw new psc_en("Improper method call order.");
        }
        switch (this.g) {
            case 2:
            case 3: {
                this.a.b(psc_al);
                final int d = this.a.d(array, n);
                this.g = 4;
                return d;
            }
            default: {
                throw new psc_en("Object not initialized.");
            }
        }
    }
    
    public int e(final byte[] array, final int n) throws psc_en {
        return this.a.b(array, n);
    }
    
    public psc_dt q() throws psc_en {
        try {
            final psc_dt a = psc_dt.a(this.a.d(), this.f());
            final byte[][] g = this.a.g();
            final byte[] array = new byte[this.e];
            this.a.c(array, 0);
            final byte[][] array2 = new byte[g.length + 2][];
            int i;
            for (i = 0; i < g.length; ++i) {
                array2[i] = g[i];
            }
            array2[i] = array;
            final byte[] array3 = new byte[this.e];
            this.a.b(array3, 0);
            array2[i + 1] = array3;
            a.c(array2);
            for (int j = 0; j < array3.length; ++j) {
                array3[j] = 0;
            }
            return a;
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not get private value as key.");
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av c = this.c();
        objectOutputStream.defaultWriteObject();
        this.a(c);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.d();
    }
    
    private psc_av c() {
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
    
    private void d() {
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
        final psc_iy psc_iy = new psc_iy();
        psc_iy.b = this.b;
        psc_iy.e = this.e;
        psc_iy.f = this.f;
        psc_iy.g = this.g;
        psc_iy.l = this.l;
        if (this.a != null) {
            psc_iy.a = (psc_ix)this.a.clone();
        }
        psc_iy.a(this);
        return psc_iy;
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        this.e = 0;
        this.f = false;
        this.l = 1;
        this.g = 1;
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
