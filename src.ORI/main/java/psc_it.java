import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_it extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] a();
    
    void a(final byte[] p0, final int p1, final int p2);
    
    byte[] b();
    
    void a(final psc_az p0, final byte[] p1, final int p2, final byte[] p3, final int p4, final int p5) throws psc_gx;
    
    boolean b(final psc_az p0, final byte[] p1, final int p2, final byte[] p3, final int p4, final int p5);
    
    String c();
    
    Object clone() throws CloneNotSupportedException;
}
