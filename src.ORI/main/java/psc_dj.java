import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_dj extends Cloneable, Serializable
{
    void a(final int[] p0) throws psc_be;
    
    int[] c();
    
    String d();
    
    int a(final int p0, final int p1);
    
    boolean e();
    
    int a(final byte[] p0, final int p1, final int p2, final int p3, final Object p4, final SecureRandom p5) throws psc_gx;
    
    int a(final byte[] p0, final int p1, final int p2, final Object p3) throws psc_gx;
}
