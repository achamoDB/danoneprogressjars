import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

interface psc_ix extends Cloneable, Serializable
{
    boolean a(final byte[] p0, final int p1) throws psc_ao, psc_be;
    
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    byte[] e() throws psc_ao;
    
    int f();
    
    void a(final Class p0) throws psc_k9;
    
    void a(final SecureRandom p0);
    
    void a(final psc_el p0) throws psc_be;
    
    boolean a(final psc_al p0);
    
    boolean a(final psc_dt p0);
    
    byte[][] g();
    
    void a(final byte[] p0, final int p1, final int p2);
    
    void b(final psc_al p0) throws psc_bf;
    
    void b(final byte[] p0, final int p1, final int p2);
    
    void b(final psc_dt p0) throws psc_bf;
    
    void h();
    
    int b(final byte[] p0, final int p1);
    
    void i();
    
    int c(final byte[] p0, final int p1);
    
    int d(final byte[] p0, final int p1);
    
    Object clone() throws CloneNotSupportedException;
    
    void j();
    
    void k();
    
    void y();
}
