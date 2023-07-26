// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_m implements pscl_i
{
    public byte[] a;
    public byte[] b;
    
    public pscl_m() {
        this.a = new byte[] { 0, 0 };
        this.b = new byte[] { 0, 0, 0 };
    }
    
    public void a(final String s) {
    }
    
    public int a(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public int b(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public int c(final byte[] array, final int n) {
        return 0;
    }
    
    public int d(final byte[] array, final int n) {
        return 0;
    }
    
    public void a(final pscl_bl pscl_bl) throws pscl_h {
    }
    
    public void e(final byte[] array, final int n) {
    }
    
    public void a(final pscl_ax pscl_ax) throws pscl_h {
    }
    
    public void b(final pscl_ax pscl_ax) throws pscl_h {
    }
    
    public pscl_ax b() {
        return null;
    }
    
    public void f(final byte[] array, final int n) {
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        if (array2 == null) {
            throw new pscl_h("Output is null");
        }
        if (array == null) {
            throw new pscl_h("Input is null");
        }
        if (array2.length - n3 < n2) {
            throw new pscl_h("Output does not have enough space");
        }
        if (array.length < n + n2) {
            throw new pscl_h("Inout is not long enough to hold data");
        }
        System.arraycopy(array, n, array2, n3, n2);
        return n2;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        if (array2 == null) {
            throw new pscl_h("Output is null");
        }
        if (array == null) {
            throw new pscl_h("Input is null");
        }
        if (array2.length - n3 < n2) {
            throw new pscl_h("Output does not have enough space");
        }
        if (array.length < n + n2) {
            throw new pscl_h("Inout is not long enough to hold data");
        }
        System.arraycopy(array, n, array2, n3, n2);
        return n2;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final pscl_e pscl_e) throws pscl_h {
        return 0;
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws pscl_h {
        return true;
    }
    
    public byte[][] a(final pscl_e pscl_e) throws pscl_h {
        return null;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final pscl_e pscl_e) throws pscl_h {
        return 0;
    }
    
    public int c() {
        return 0;
    }
    
    public int d() {
        return 0;
    }
    
    public int c(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        return 0;
    }
    
    public int e() {
        return 1;
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
    
    public int g(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public int h(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public byte[] k() {
        return new byte[0];
    }
    
    public byte[] j() {
        return new byte[0];
    }
    
    public int a(final int n) {
        return n;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final byte[] array3, final int n3, final boolean b) throws pscl_h {
        return 0;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final byte b, final boolean b2) throws pscl_h {
        return 0;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final boolean b, final long n4) throws pscl_h {
        return 0;
    }
    
    public String l() {
        return "Null_With_Null_Null";
    }
    
    public boolean n() {
        return true;
    }
    
    public boolean o() {
        return true;
    }
    
    public byte[] b(final int n) {
        switch (n) {
            case 1: {
                return this.b;
            }
            case 2:
            case 3: {
                return this.a;
            }
            default: {
                return this.a;
            }
        }
    }
    
    public String m() {
        return "NULL";
    }
    
    public pscl_i r() {
        return new pscl_m();
    }
    
    public void s() {
    }
    
    public byte[][] b(final pscl_e pscl_e) {
        return null;
    }
    
    public byte[] c(final pscl_e pscl_e) {
        return null;
    }
    
    public byte[] a(final pscl_e pscl_e, final byte[] array) throws pscl_h {
        return null;
    }
    
    public String q() {
        return "Null";
    }
    
    public String f() {
        return "Null";
    }
    
    public String a() {
        return "Null";
    }
    
    public boolean p() {
        return false;
    }
}
