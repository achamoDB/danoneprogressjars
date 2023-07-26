// 
// Decompiled by Procyon v0.5.36
// 

public abstract class pscl_ae extends pscl_q
{
    public int a;
    public boolean b;
    public int c;
    public int d;
    
    public pscl_ae(int n, final boolean b, final int n2, final int n3, final String s, final int a, final int n4, final int n5) throws pscl_x {
        super(n, b, n2, n3);
        n |= 0x4000000;
        this.a = a;
        this.c = n4 * a;
        this.d = n5 * a;
        if (s == null) {
            return;
        }
        super.l = a(s, a);
        super.k = true;
        super.n = super.l.length;
        super.m = 0;
        this.d();
        super.o |= 0x20000;
    }
    
    public pscl_ae(int n, final boolean b, final int n2, final int n3, final byte[] l, final int m, final int n4, final int a, final int n5, final int n6, final int n7) throws pscl_x {
        super(n, b, n2, n3);
        n |= 0x4000000;
        this.a = a;
        this.c = n6 * a;
        this.d = n7 * a;
        super.n = n4;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        this.a(a, n5);
        this.d();
        super.o |= 0x20000;
    }
    
    public void d() throws pscl_x {
        if (!super.g) {
            return;
        }
        if (((super.o & 0x2000000) != 0x0 || (super.o & 0x40000) != 0x0) && this.c > 0 && super.n < this.c) {
            throw new pscl_x("Invalid String length (too short).");
        }
        if (this.d > 0 && super.n > this.d) {
            throw new pscl_x("Invalid String length (too long).");
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final boolean b, final boolean b2) throws pscl_x {
        super.a(array, n, n2, b, b2);
        this.a(this.a, 1);
        this.b = false;
        this.d();
    }
    
    public void a(final String s, final boolean b, final boolean b2) throws pscl_x {
        final byte[] a = a(s, this.a);
        super.a(a, 0, a.length, b, b2);
        super.k = true;
        this.b = false;
        this.d();
    }
    
    public void f() throws pscl_x {
        super.o |= 0x40000;
        this.d();
    }
    
    public String e() {
        if (super.l == null || super.n == 0) {
            return new String();
        }
        if (this.a == 1) {
            return new String(super.l, super.m, super.n);
        }
        final char[] value = new char[(super.n + this.a - 1) / this.a];
        for (int i = 0, n = super.m + (this.a - 2); i < value.length; ++i, n += this.a) {
            value[i] = (char)((char)(super.l[n] << 8) | (char)(super.l[n + 1] & 0xFF));
        }
        return new String(value);
    }
    
    private static byte[] a(final String s, final int n) {
        if (n == 1) {
            return s.getBytes();
        }
        final char[] charArray = s.toCharArray();
        if (n == 2) {
            final byte[] array = new byte[charArray.length * 2];
            for (int i = 0; i < charArray.length; ++i) {
                array[2 * i] = (byte)((charArray[i] & '\uff00') >>> 8);
                array[2 * i + 1] = (byte)(charArray[i] & '\u00ff');
            }
            return array;
        }
        if (n == 4) {
            final byte[] array2 = new byte[charArray.length * 4];
            for (int j = 0; j < charArray.length; ++j) {
                array2[4 * j + 2] = (byte)((charArray[j] & '\uff00') >>> 8);
                array2[4 * j + 3] = (byte)(charArray[j] & '\u00ff');
            }
            return array2;
        }
        return null;
    }
    
    private void a(final int n, final int n2) {
        if (n == n2) {
            return;
        }
        final int n3 = super.n / n2 * n;
        final byte[] l = new byte[n3];
        if (n < n2) {
            int n4 = 0;
            for (int i = 0; i < super.n; i += n2) {
                for (int j = 0; j < n; ++j, ++n4) {
                    l[n4] = super.l[i + super.m];
                }
            }
        }
        else {
            int m = super.m;
            for (int k = 0; k < n3; k += n) {
                for (int n5 = k + (n - n2), n6 = 0; n6 < n2; ++n6, ++n5, ++m) {
                    l[n5] = super.l[m + super.m];
                }
            }
        }
        super.l = l;
        super.m = 0;
        super.n = n3;
        super.k = true;
    }
    
    public void c() {
        super.c();
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3) throws pscl_x {
        final int a = super.a(pscl_v, n, array, n2, n3);
        this.b = false;
        this.d();
        return a;
    }
}
