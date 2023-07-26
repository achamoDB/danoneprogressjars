import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

interface psc_dg extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    void a(final int p0);
    
    void a(final psc_dc p0, final SecureRandom p1) throws psc_bf;
    
    void b(final psc_dc p0, final SecureRandom p1) throws psc_bf;
    
    boolean a(final boolean p0);
    
    byte[] a(final psc_am p0, final boolean p1, final psc_di p2, final psc_dm p3) throws psc_en;
    
    psc_dt a(final byte[] p0, final int p1, final int p2, final psc_di p3, final psc_dm p4, final String p5) throws psc_en;
    
    psc_al b(final byte[] p0, final int p1, final int p2, final psc_di p3, final psc_dm p4, final String p5) throws psc_en;
    
    psc_dc a(final byte[] p0, final int p1, final int p2, final boolean p3, final psc_di p4, final psc_dm p5, final String p6) throws psc_en;
    
    void y();
    
    void e();
    
    void f();
    
    Object clone() throws CloneNotSupportedException;
}
