import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c6 extends psc_av implements Cloneable, Serializable
{
    private psc_az a;
    private psc_gt b;
    private int c;
    private int d;
    private transient psc_dd e;
    private boolean f;
    private boolean g;
    
    public psc_c6(final psc_gt b, final psc_az a) {
        this.f = false;
        this.g = false;
        this.b = b;
        this.a = a;
    }
    
    public synchronized void r() {
        this.b.e();
    }
    
    public synchronized void g(final byte[] array) {
        this.b.a(array);
    }
    
    public synchronized void b(final byte[] array, final int n, final int n2) {
        final int n3 = this.a.f() - 8;
        final int h = this.a.h();
        if (n3 <= 0) {
            this.b.a(array, n, n2);
            return;
        }
        final byte[] array2 = new byte[h];
        for (int i = 0; i < n2; i += h) {
            final byte[] array3 = new byte[n3];
            this.b.a(array3, 0, n3);
            try {
                this.a.j();
                this.a.a(array3, 0, n3);
                this.a.b(array2, 0);
                this.a.y();
            }
            catch (psc_ap psc_ap) {
                this.b.a(array2, 0, n3);
                for (int j = 0; j < n3; ++j) {
                    final byte[] array4 = array2;
                    final int n4 = j;
                    array4[n4] ^= array3[j];
                }
            }
            if (h > n2 - i) {
                System.arraycopy(array2, 0, array, i, n2 - i);
            }
            else {
                System.arraycopy(array2, 0, array, i, h);
            }
            psc_au.c(array2);
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final boolean e = this.e();
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
        this.f();
    }
    
    private boolean e() {
        if (!this.f) {
            return false;
        }
        this.e.d();
        return true;
    }
    
    private void a(final boolean b) {
        if (b) {
            this.e.c();
        }
    }
    
    private void f() {
        this.g = false;
        this.f = false;
        this.e.c();
    }
    
    public String getAlgorithm() {
        return this.b.c() + "/" + this.a.e();
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
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
