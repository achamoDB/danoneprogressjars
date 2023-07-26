import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

abstract class psc_kr extends psc_an implements Cloneable, Serializable
{
    abstract void a(final byte[] p0, final int p1, final int p2) throws psc_bf;
    
    abstract void a(final byte[] p0, final int p1, final byte[] p2, final int p3);
    
    abstract void b(final byte[] p0, final int p1, final byte[] p2, final int p3);
    
    abstract void c();
    
    abstract void d();
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
