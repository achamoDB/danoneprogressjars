import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ay extends psc_av implements Cloneable, Serializable
{
    private boolean a;
    private psc_az b;
    private int c;
    private int d;
    private int e;
    private byte[] f;
    private boolean g;
    private byte[] h;
    private transient psc_dd i;
    protected boolean j;
    
    protected psc_ay() {
    }
    
    psc_ay(final psc_ax psc_ax) {
        this(psc_ax, true);
    }
    
    psc_ay(final psc_ax psc_ax, final boolean g) {
        this.e = 2;
        this.b = psc_ax.f();
        this.c = this.b.h();
        this.h = psc_ax.g();
        this.f = null;
        this.g = g;
    }
    
    public String getAlgorithm() {
        return this.b.e() + "Random";
    }
    
    public synchronized void r() {
        this.g(psc_av.a(20));
    }
    
    public synchronized void g(final byte[] array) {
        if (array == null) {
            return;
        }
        if (this.f == null) {
            this.f = new byte[2 * this.c];
            this.i = psc_au.a(this.f);
        }
        if (this.e > 0) {
            --this.e;
        }
        try {
            if (!this.a) {
                this.b.j();
                if (this.e == 0) {
                    this.j();
                    this.b.a(this.f, 0, this.c);
                    this.i();
                }
            }
            this.b.a(array, 0, array.length);
        }
        catch (psc_ap psc_ap) {
            this.r();
        }
        this.a = true;
    }
    
    public synchronized void b(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new IllegalArgumentException("null parameter");
        }
        if (n + n2 > array.length) {
            throw new ArrayIndexOutOfBoundsException("offset+numberOfBytes > " + array.length);
        }
        if (this.f == null) {
            this.r();
        }
        if (!this.j && psc_av.b()) {
            this.a(this.f, 0, this.c);
            this.j = true;
        }
        this.a(array, n, n2);
    }
    
    public synchronized void a(final byte[] array, final int n, final int n2) {
        if (this.f == null) {
            this.r();
        }
        int n3 = n;
        int i = n2;
        this.j();
        try {
            if (this.a) {
                this.b.b(this.f, 0);
                if (this.j && psc_av.b()) {
                    this.a(this.f, this.c, this.f, 0, this.c);
                    System.arraycopy(this.f, 0, this.f, this.c, this.c);
                }
                this.d = 0;
                this.a = false;
            }
            while (i > this.d) {
                if (this.d > 0) {
                    System.arraycopy(this.f, 2 * this.c - this.d, array, n3, this.d);
                    i -= this.d;
                    n3 += this.d;
                    this.d = 0;
                }
                this.b.j();
                this.b.a(this.f, 0, this.c);
                this.b.b(this.f, this.c);
                this.e();
                if (this.j && psc_av.b()) {
                    this.a(this.f, 0, this.f, this.c, this.c);
                    System.arraycopy(this.f, this.c, this.f, 0, this.c);
                }
                this.d = this.c;
            }
            System.arraycopy(this.f, 2 * this.c - this.d, array, n3, i);
            this.d -= i;
        }
        catch (psc_ap psc_ap) {
            final SecureRandom secureRandom = new SecureRandom();
            final byte[] bytes = new byte[20];
            final byte[] bytes2 = new byte[20];
            secureRandom.setSeed(psc_av.a(20));
            if (psc_av.b()) {
                secureRandom.nextBytes(bytes);
            }
            int n5;
            for (int j = n2, n4 = n; j > 0; j -= n5, n4 += n5) {
                secureRandom.nextBytes(bytes2);
                n5 = ((j >= 20) ? 20 : j);
                if (psc_av.b()) {
                    this.a(bytes, 0, bytes2, 0, 20);
                    System.arraycopy(bytes2, 0, bytes, 0, 20);
                }
                System.arraycopy(bytes2, 0, array, n4, n5);
            }
        }
        this.i();
    }
    
    private void e() {
        if (this.e != 0) {
            for (int i = this.c - 1; i >= 0; --i) {
                final byte[] f = this.f;
                final int n = i;
                ++f[n];
                if (this.f[i] != 1) {
                    break;
                }
            }
        }
        else {
            int n2 = 0;
            for (int j = this.c - 1; j >= 0; --j) {
                n2 = (this.f[j] & 0xFF) + (this.h[j] & 0xFF) + ((n2 & 0x100) >>> 8);
                this.f[j] = (byte)(n2 & 0xFF);
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.f();
        objectOutputStream.defaultWriteObject();
        this.g();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.h();
    }
    
    private void f() {
        this.j();
    }
    
    private void g() {
        this.i();
    }
    
    private void h() {
        this.i();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ay psc_ay = new psc_ay();
        psc_ay.a = this.a;
        psc_ay.c = this.c;
        psc_ay.d = this.d;
        psc_ay.e = this.e;
        psc_ay.j = this.j;
        if (this.b != null) {
            psc_ay.b = (psc_az)this.b.clone();
        }
        if (this.i == null) {
            if (this.f != null) {
                psc_ay.f = this.f.clone();
            }
            else {
                psc_ay.f = null;
            }
        }
        else {
            psc_ay.f = (byte[])psc_au.a(this.f, this.i);
            psc_ay.i = psc_au.a(psc_ay.f);
            psc_ay.g = true;
        }
        if (this.h != null) {
            psc_ay.h = this.h.clone();
        }
        psc_ay.a(this);
        return psc_ay;
    }
    
    private void i() {
        if (!this.g) {
            return;
        }
        if (this.f == null) {
            return;
        }
        if (this.i == null) {
            this.i = psc_au.b(this.f);
        }
        if (this.i.a()) {
            this.i.c();
        }
    }
    
    private void j() {
        if (!this.g || this.i == null) {
            return;
        }
        if (this.i.a()) {
            this.i.d();
        }
    }
    
    public void y() {
        super.y();
        if (this.b != null) {
            this.b.y();
        }
        if (this.i != null) {
            psc_au.b(this.f, this.i);
        }
        this.i = null;
        this.a = false;
        this.e = 2;
        this.d = 0;
        this.f = null;
        this.j = false;
    }
    
    protected void finalize() throws Throwable {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
