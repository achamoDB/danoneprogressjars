import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_ei extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    byte[] a(final psc_az p0) throws psc_ao;
    
    void a(final byte[] p0, final int p1) throws psc_ao, psc_be;
    
    int e();
    
    boolean b(final psc_az p0);
    
    void a(final psc_az p0, final psc_dc p1, final SecureRandom p2) throws psc_en, psc_bf;
    
    void f() throws psc_en;
    
    void a(final byte[] p0, final int p1, final int p2) throws psc_en;
    
    int b(final byte[] p0, final int p1) throws psc_en;
    
    void y();
    
    void g();
    
    void h();
    
    Object clone() throws CloneNotSupportedException;
}
