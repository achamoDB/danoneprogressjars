import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j9 extends psc_an implements Cloneable, Serializable, psc_ax
{
    private static final byte[] a;
    
    public psc_j9() {
    }
    
    public psc_j9(final int[] array) throws psc_be {
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of Parameters: expected none");
        }
    }
    
    public int[] e() {
        return new int[0];
    }
    
    public String getAlgorithm() {
        return "SHA1Random";
    }
    
    public psc_az f() {
        return new psc_ew();
    }
    
    public byte[] g() {
        final byte[] array = new byte[psc_j9.a.length];
        System.arraycopy(psc_j9.a, 0, array, 0, psc_j9.a.length);
        return array;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return new psc_j9();
    }
    
    public void y() {
        super.y();
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        a = new byte[] { -38, 57, -93, -18, 94, 107, 75, 13, 50, 85, -65, -17, -107, 96, 24, -112, -81, -40, 7, 9 };
    }
}
