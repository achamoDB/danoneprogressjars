// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c4 implements psc_c5
{
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_d {
        if (array2 == null) {
            throw new psc_d("Output buffer is null");
        }
        if (array2.length - n3 < n2) {
            throw new psc_d("Not enough allocated space to store output");
        }
        if (array == null) {
            throw new psc_d("Input buffer is null");
        }
        if (array.length < n2 + n) {
            throw new psc_d("Not enough input data");
        }
        System.arraycopy(array, 0, array2, 0, n2);
        return n2;
    }
    
    public int a(final byte[] array, final int n, final int n2) {
        return n2;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_d {
        if (array2 == null) {
            throw new psc_d("Output buffer is null");
        }
        if (array2.length - n3 < n2) {
            throw new psc_d("Not enough allocated space to store output");
        }
        if (array == null) {
            throw new psc_d("Input buffer is null");
        }
        if (array.length < n2 + n) {
            throw new psc_d("Not enough input data");
        }
        System.arraycopy(array, 0, array2, 0, n2);
        return n2;
    }
    
    public int b(final byte[] array, final int n, final int n2) {
        return n2;
    }
    
    public String a() {
        return "Compression.null";
    }
    
    public int a(final int n) {
        return 0;
    }
}
