import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kb extends psc_an implements psc_gt, Cloneable, Serializable
{
    private byte[] a;
    
    public String c() {
        return "DummyRandom";
    }
    
    public void a(final byte[] array) {
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(this.a, 0, array, n, n2);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return new psc_kb();
    }
    
    public void y() {
        super.y();
        this.d(this.a);
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none.");
        }
    }
    
    public int[] d() {
        return new int[0];
    }
    
    public void e() {
    }
    
    public void a(final long n) {
    }
    
    public void b(final byte[] array) {
    }
}
