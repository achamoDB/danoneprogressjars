import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_di extends psc_an implements Cloneable, Serializable
{
    protected String a;
    
    private psc_di() {
    }
    
    protected psc_di(final String a) {
        this.a = a;
    }
    
    boolean c() {
        return false;
    }
    
    void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect Number of Algorithm Parameters: expected none.");
        }
    }
    
    int[] d() {
        return new int[0];
    }
    
    boolean e() {
        return false;
    }
    
    String f() {
        return this.a;
    }
    
    public abstract void a(final byte[] p0, final int p1, final int p2) throws psc_ao, psc_be, psc_gw;
    
    abstract byte[] g() throws psc_ao;
    
    boolean h() {
        return true;
    }
    
    abstract int a(final int p0);
    
    abstract void b(final byte[] p0, final int p1, final int p2) throws psc_gw;
    
    abstract int i();
    
    abstract byte[] j();
    
    abstract int k();
    
    abstract void a(final psc_dl p0, final psc_dc p1, final SecureRandom p2) throws psc_bf, psc_gw;
    
    abstract void b(final psc_dl p0, final psc_dc p1, final SecureRandom p2) throws psc_bf, psc_gw;
    
    abstract int a(final psc_dl p0, final byte[] p1, final int p2, final byte[] p3, final int p4);
    
    abstract int b(final psc_dl p0, final byte[] p1, final int p2, final byte[] p3, final int p4);
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return 0;
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return 0;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final Object clone = super.clone();
        if (this.a != null) {
            ((psc_di)clone).a = new String(this.a);
        }
        return clone;
    }
    
    public void y() {
        super.y();
    }
}
