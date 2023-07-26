import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_jx extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] a();
    
    String b();
    
    int a(final int p0, final boolean p1);
    
    void c();
    
    int a(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4);
    
    int a(final byte[] p0, final int p1);
    
    void d();
    
    int b(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_e1;
    
    int b(final byte[] p0, final int p1) throws psc_e1;
    
    void e();
    
    void f();
    
    void g();
    
    Object clone() throws CloneNotSupportedException;
}
