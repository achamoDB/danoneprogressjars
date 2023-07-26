import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kc extends psc_an implements psc_az, Cloneable, Serializable
{
    public psc_kc() {
    }
    
    public psc_kc(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String e() {
        return "NoDigest";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        return null;
    }
    
    public int f() {
        return 0;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 0;
    }
    
    public int i() {
        return 0;
    }
    
    public void j() {
    }
    
    public void a(final byte[] array, final int n, final int n2) {
    }
    
    public int b(final byte[] array, final int n) {
        return 0;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        return 0;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return new psc_kc();
    }
    
    public void y() {
        super.y();
    }
}
