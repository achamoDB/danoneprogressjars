import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_i8 implements psc_it, Cloneable, Serializable
{
    private byte[] a;
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public int[] a() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array == null || n2 <= 0) {
            return;
        }
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
    }
    
    public byte[] b() {
        if (this.a != null) {
            return this.a.clone();
        }
        return null;
    }
    
    public void a(final psc_az psc_az, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) throws psc_gx {
        if (psc_az == null) {
            throw new psc_gx("Invalid OAEP digest.");
        }
        try {
            psc_az.j();
            if (this.a != null) {
                psc_az.a(this.a, 0, this.a.length);
            }
            psc_az.b(array, n);
        }
        catch (psc_ap psc_ap) {
            throw new psc_gx("Invalid OAEP digest.");
        }
        finally {
            if (psc_az != null) {
                psc_az.y();
            }
        }
    }
    
    public boolean b(final psc_az psc_az, final byte[] array, int n, final byte[] array2, final int n2, final int n3) {
        if (psc_az == null) {
            return false;
        }
        final int h = psc_az.h();
        final byte[] array3 = new byte[h];
        try {
            psc_az.j();
            if (this.a != null) {
                psc_az.a(this.a, 0, this.a.length);
            }
            psc_az.b(array3, 0);
            for (int i = 0; i < h; ++i, ++n) {
                if (array3[i] != array[n]) {
                    return false;
                }
            }
            return true;
        }
        catch (psc_ap psc_ap) {
            return false;
        }
        finally {
            psc_az.y();
            psc_au.c(array3);
        }
    }
    
    public String c() {
        return "SpecifiedParams";
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_i8 psc_i8 = new psc_i8();
        if (this.a != null) {
            psc_i8.a = this.a.clone();
        }
        return psc_i8;
    }
}
