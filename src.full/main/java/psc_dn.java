import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_dn extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    String e();
    
    void a(final psc_dg p0, final psc_di p1, final psc_dm p2, final byte[] p3, final int p4, final int p5) throws psc_ao, psc_gw, psc_be;
    
    byte[] a(final psc_az p0, final psc_dg p1, final psc_di p2, final psc_dm p3) throws psc_ao;
    
    byte[] f() throws psc_ao;
    
    byte[] a(final String p0, final String p1) throws psc_ao;
    
    void a(final byte[] p0, final int p1, final int p2);
    
    byte[] g();
    
    boolean h();
    
    void a(final psc_az p0, final psc_di p1, final int p2, final psc_dc p3) throws psc_en, psc_bf;
    
    Object clone() throws CloneNotSupportedException;
    
    void y();
}
