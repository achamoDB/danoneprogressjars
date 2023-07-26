import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i6 extends psc_dr implements psc_dm, psc_iv, psc_et, Cloneable, Serializable
{
    public psc_i6() {
    }
    
    public psc_i6(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public String d() {
        return "NoPad";
    }
    
    public boolean l() {
        return true;
    }
    
    public boolean e() {
        return false;
    }
    
    public int a(final int n, final int n2) {
        return 0;
    }
    
    public int a(final int n) {
        return -1;
    }
    
    public int a(final byte[] array, final int n, final int n2, final int n3, final Object o, final SecureRandom secureRandom) throws psc_gx {
        if (n2 == 0 || n3 == -1) {
            return 0;
        }
        if (n2 == n3) {
            return 0;
        }
        throw new psc_gx("The input requires padding, but NoPad was instantiated.");
    }
    
    public int a(final byte[] array, final int n, final int n2, final Object o) throws psc_gx {
        return n2;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be {
    }
    
    public byte[] k() {
        return null;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2, final Object o) throws psc_gx {
        return n2;
    }
}
