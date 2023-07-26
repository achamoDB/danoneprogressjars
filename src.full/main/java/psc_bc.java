// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bc extends psc_bd
{
    private String[] a;
    
    psc_bc() {
    }
    
    public Object[] a(final String[] array, final String[] array2, final int n, final boolean[] array3, final String[] array4, final String s, final String[] array5, final psc_bd[] array6) {
        final Object[] array7 = new Object[array.length];
        this.a = new String[array.length];
        final psc_e3 a = psc_e3.a(n);
        for (int i = 0; i < array.length; ++i) {
            final Object a2 = a.a(array, i, "com.rsa.jsafe.JA_", array2, array4);
            if (a2 != null) {
                array7[i] = a2;
                this.a[i] = "Java";
            }
            else {
                if (array3[i]) {
                    return null;
                }
                final String[] array8 = { array[i] };
                String[] array9 = null;
                if (array2 != null) {
                    array9 = new String[] { array2[i] };
                }
                final Object a3 = this.a(array5, array6, array8, array9, n, new boolean[] { true }, new String[] { array4[i] }, i);
                if (a3 == null) {
                    return null;
                }
                array7[i] = a3;
            }
        }
        return array7;
    }
    
    private Object a(final String[] array, final psc_bd[] array2, final String[] array3, final String[] array4, final int n, final boolean[] array5, final String[] array6, final int n2) {
        for (int i = 0; i < array.length; ++i) {
            if (i != n2) {
                if (array2[i] == null) {
                    array2[i] = psc_bb.a(array[i]);
                }
                final Object[] a = array2[i].a(array3, array4, n, array5, array6, null, null, null);
                if (a != null) {
                    this.a[i] = array2[i].c();
                    return a[0];
                }
            }
        }
        return null;
    }
    
    public String c() {
        return "Java";
    }
    
    public String[] d() {
        return this.a;
    }
}
