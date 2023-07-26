import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_ep extends Cloneable, Serializable
{
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    
    void a(final byte[] p0, final int p1) throws psc_ao, psc_be;
    
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    byte[] a(final psc_az p0, final psc_et p1, final String p2, final boolean p3) throws psc_ao;
    
    String d();
    
    String m();
    
    boolean n();
    
    int o();
    
    int p();
    
    int q();
    
    boolean a(final psc_az p0);
    
    boolean a(final psc_et p0);
    
    boolean r();
    
    String a(final psc_el p0);
    
    void a(final int p0);
    
    int j();
    
    String a(final psc_dt p0, final psc_az p1, final psc_et p2, final SecureRandom p3, final psc_nf[] p4);
    
    void s() throws psc_en;
    
    String a(final psc_al p0, final psc_az p1, final psc_et p2, final SecureRandom p3, final psc_nf[] p4);
    
    void t() throws psc_en;
    
    void a(final byte[] p0, final int p1, final int p2) throws psc_en, psc_e1;
    
    int a(final byte[] p0, final int p1, final int p2, final psc_az p3, final psc_et p4, final byte[] p5, final int p6);
    
    void b(final byte[] p0, final int p1, final int p2) throws psc_en, psc_e1;
    
    boolean a(final byte[] p0, final int p1, final int p2, final psc_az p3, final psc_et p4, final byte[] p5, final int p6, final int p7);
    
    Object clone() throws CloneNotSupportedException;
    
    void k();
    
    void l();
    
    void y();
}
