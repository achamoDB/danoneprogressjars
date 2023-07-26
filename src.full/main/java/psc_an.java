import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_an implements Serializable, Cloneable
{
    protected static final void a() {
        psc_aq.g();
        psc_aq.e();
    }
    
    static final boolean b() {
        return psc_aq.c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public void d(final byte[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            array[i] = 0;
        }
    }
    
    public void c(final int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            array[i] = 0;
        }
    }
    
    void y() {
    }
    
    protected void finalize() {
        this.y();
    }
}
