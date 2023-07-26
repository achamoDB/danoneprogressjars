import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_aw extends psc_an implements psc_ax, Cloneable, Serializable
{
    private static final byte[] a;
    
    public psc_aw() {
    }
    
    public psc_aw(final int[] array) throws psc_be {
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
        return "MD5Random";
    }
    
    public psc_az f() {
        return new psc_ef();
    }
    
    public byte[] g() {
        final byte[] array = new byte[psc_aw.a.length];
        System.arraycopy(psc_aw.a, 0, array, 0, psc_aw.a.length);
        return array;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return new psc_aw();
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
        a = new byte[] { -44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 127 };
    }
}
