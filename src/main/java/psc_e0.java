import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_e0 extends Cloneable, Serializable
{
    boolean k();
    
    boolean i();
    
    boolean j();
    
    void h(final int p0) throws psc_e1;
    
    void a(final psc_e0 p0) throws psc_e1;
    
    void a(final byte[] p0, final int p1, final int p2) throws psc_e1;
    
    int l();
    
    long m();
    
    byte[] n() throws psc_e1;
    
    byte[] j(final int p0) throws psc_e1;
    
    int o();
    
    int k(final int p0) throws psc_e1;
    
    void a(final int p0, final int p1) throws psc_e1;
    
    void i(final int p0) throws psc_e1;
    
    int b(final psc_e0 p0) throws psc_e1;
    
    void a(final psc_e0 p0, final psc_e0 p1) throws psc_e1;
    
    void l(final int p0) throws psc_e1;
    
    void c(final psc_e0 p0) throws psc_e1;
    
    void b(final psc_e0 p0, final psc_e0 p1) throws psc_e1;
    
    void m(final int p0) throws psc_e1;
    
    void d(final psc_e0 p0) throws psc_e1;
    
    void c(final psc_e0 p0, final psc_e0 p1) throws psc_e1;
    
    void a(final psc_e0 p0, final psc_e0 p1, final psc_e0 p2) throws psc_e1;
    
    void b(final psc_e0 p0, final psc_e0 p1, final psc_e0 p2) throws psc_e1;
    
    void d(final psc_e0 p0, final psc_e0 p1) throws psc_e1;
    
    boolean e(final psc_e0 p0, final psc_e0 p1) throws psc_e1;
    
    void c(final psc_e0 p0, final psc_e0 p1, final psc_e0 p2) throws psc_e1;
    
    void a(final int p0, final psc_e0[] p1, final psc_e0 p2) throws psc_e1;
    
    void p(final int p0);
    
    void n(final int p0);
    
    void f(final psc_e0 p0, final psc_e0 p1);
    
    void d(final psc_ez p0, final psc_ez p1) throws psc_e1;
    
    boolean a(final psc_e0 p0, final SecureRandom p1, final boolean p2) throws psc_e1;
    
    boolean a(final int p0, final psc_e0 p1, final int p2, final boolean p3, final SecureRandom p4) throws psc_e1;
    
    boolean a(final int p0, final psc_e0 p1, final int p2, final boolean p3, final SecureRandom p4, final byte[] p5, final byte[][] p6) throws psc_e1;
    
    boolean a(final int p0, final psc_e0 p1, final int p2, final boolean p3, final SecureRandom p4, final byte[] p5, final byte[] p6, final byte[] p7, final byte[][] p8) throws psc_e1;
    
    void p();
    
    void q();
    
    Object clone() throws CloneNotSupportedException;
    
    void r();
}
