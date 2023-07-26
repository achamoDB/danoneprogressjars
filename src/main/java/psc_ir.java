import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_ir extends Cloneable, Serializable
{
    void a(final byte[] p0, final int p1) throws psc_ao, psc_be;
    
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    String e();
    
    byte[] a(final psc_iv p0) throws psc_ao;
    
    int b(final psc_iv p0);
    
    boolean a(final boolean p0);
    
    byte[] a(final psc_dc p0, final boolean p1, final psc_iv p2) throws psc_en;
    
    psc_dc a(final byte[] p0, final int p1, final int p2, final boolean p3, final psc_iv p4, final String p5) throws psc_en;
    
    int f();
    
    int g();
    
    int h();
    
    int i();
    
    boolean b(final byte[] p0, final int p1);
    
    void a(final int p0);
    
    int j();
    
    String a(final psc_al p0, final psc_iv p1, final SecureRandom p2, final psc_nf[] p3);
    
    String a(final psc_dt p0, final psc_iv p1, final SecureRandom p2, final psc_nf[] p3);
    
    String b(final psc_dt p0, final psc_iv p1, final SecureRandom p2, final psc_nf[] p3);
    
    String b(final psc_al p0, final psc_iv p1, final SecureRandom p2, final psc_nf[] p3);
    
    int a(final byte[] p0, final int p1, final byte[] p2, final int p3);
    
    int b(final byte[] p0, final int p1, final byte[] p2, final int p3);
    
    Object clone() throws CloneNotSupportedException;
    
    void k();
    
    void l();
    
    void y();
}
