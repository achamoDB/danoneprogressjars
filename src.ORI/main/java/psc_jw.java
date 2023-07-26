import java.io.UnsupportedEncodingException;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_jw implements psc_jx
{
    public static final int a = 64;
    public static final int b = 65;
    public static final int c = 64;
    public static final int d = 0;
    private static final byte[] e;
    private int f;
    private static final int g = 4;
    private byte[] h;
    private int i;
    private int j;
    
    public static byte[] a(final String s, final String charsetName) {
        try {
            return s.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException ex) {
            return s.getBytes();
        }
    }
    
    public psc_jw() {
        this.h = new byte[4];
    }
    
    private void h() {
        this.f = 0;
        this.j = 0;
    }
    
    public psc_jw(final int[] array) throws psc_be {
        this.h = new byte[4];
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        this.i = 64;
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length != 1) {
            throw new psc_be("Incorrect number of parameters: expected 1");
        }
        if (array[0] < 0 || array[0] > 76) {
            throw new psc_be("Parameter out of range");
        }
        if (array[0] % 4 != 0) {
            throw new psc_be("Parameter must be a multiple of 4");
        }
        this.i = array[0];
    }
    
    public int[] a() {
        return new int[] { this.i };
    }
    
    public String b() {
        return "Base64";
    }
    
    public int a(final int n, final boolean b) {
        int n2 = 0;
        if (b) {
            if (n == 0) {
                return (this.i > 0) ? 6 : 4;
            }
            if (n % 3 != 0) {
                n2 = ((this.i > 0) ? 6 : 4);
            }
            if (n / 3 > 0) {
                final int n3 = 4 * n / 3;
                n2 += n3;
                if (this.i > 0) {
                    n2 += 2 * (n3 / this.i);
                }
            }
        }
        else {
            final int i = this.i();
            if (n == 0) {
                return i;
            }
            n2 = i + 3 * (n / 4);
        }
        return n2;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jw psc_jw = new psc_jw();
        psc_jw.f = this.f;
        if (this.h != null) {
            psc_jw.h = this.h.clone();
        }
        psc_jw.i = this.i;
        psc_jw.j = psc_jw.j;
        return psc_jw;
    }
    
    public void c() {
        this.h();
    }
    
    public int a(final byte[] array, int n, int n2, final byte[] array2, int n3) {
        if (n2 <= 0) {
            return 0;
        }
        final int n4 = n3;
        while (n2-- > 0) {
            this.h[this.f++] = array[n++];
            ++this.j;
            if (this.f == 3) {
                this.f = 0;
                array2[n3++] = psc_jw.e[this.h[0] >> 2 & 0x3F];
                array2[n3++] = psc_jw.e[(this.h[0] << 4 & 0x30) | (this.h[1] >> 4 & 0xF)];
                array2[n3++] = psc_jw.e[(this.h[1] << 2 & 0x3C) | (this.h[2] >> 6 & 0x3)];
                array2[n3++] = psc_jw.e[this.h[2] & 0x3F];
                if (this.i <= 0 || 4 * (this.j / 3) != this.i) {
                    continue;
                }
                this.j = 0;
                array2[n3++] = 13;
                array2[n3++] = 10;
            }
        }
        return n3 - n4;
    }
    
    public int a(final byte[] array, int n) {
        if (this.f == 0) {
            return 0;
        }
        final int n2 = n;
        array[n++] = psc_jw.e[this.h[0] >> 2 & 0x3F];
        if (this.f == 1) {
            array[n++] = psc_jw.e[this.h[0] << 4 & 0x30];
            array[n++] = psc_jw.e[64];
        }
        else if (this.f == 2) {
            array[n++] = psc_jw.e[(this.h[0] << 4 & 0x30) | (this.h[1] >> 4 & 0xF)];
            array[n++] = psc_jw.e[this.h[1] << 2 & 0x3C];
        }
        array[n++] = psc_jw.e[64];
        this.f = 0;
        return n - n2;
    }
    
    public void d() {
        this.h();
    }
    
    public int b(final byte[] array, int n, int n2, final byte[] array2, int n3) throws psc_e1 {
        if (n2 <= 0) {
            return 0;
        }
        final int n4 = n3;
        while (n2-- > 0) {
            final int a = this.a(array[n++]);
            if (a < 0) {
                throw new psc_e1("invalid character");
            }
            if (a != 65) {
                this.h[this.f++] = (byte)a;
            }
            if (this.f != 4) {
                continue;
            }
            for (int i = 0; i < 4; ++i) {
                if (this.h[i] == 64) {
                    return n3 - n4;
                }
            }
            array2[n3++] = (byte)((this.h[0] << 2 & 0xFC) | (this.h[1] >> 4 & 0x3));
            array2[n3++] = (byte)((this.h[1] << 4 & 0xF0) | (this.h[2] >> 2 & 0xF));
            array2[n3++] = (byte)((this.h[2] << 6 & 0xC0) | (this.h[3] & 0x3F));
            this.f = 0;
        }
        return n3 - n4;
    }
    
    private int i() {
        if (this.f == 0) {
            return 3;
        }
        if (this.h[2] == 64) {
            return 1;
        }
        if (this.h[3] == 64) {
            return 2;
        }
        return 3;
    }
    
    public int b(final byte[] array, int n) throws psc_e1 {
        if (this.f == 0) {
            return 0;
        }
        if (this.f != 4) {
            throw new psc_e1("Unexpected padding chars");
        }
        if (this.h[0] == 64 || this.h[1] == 64) {
            throw new psc_e1("Unexpected padding chars");
        }
        this.f = 0;
        array[n++] = (byte)((this.h[0] << 2 & 0xFC) | (this.h[1] >> 4 & 0x3));
        if (this.h[2] == 64) {
            return 1;
        }
        array[n++] = (byte)((this.h[1] << 4 & 0xF0) | (this.h[2] >> 2 & 0xF));
        if (this.h[3] == 64) {
            return 2;
        }
        array[n] = (byte)((this.h[2] << 6 & 0xC0) | (this.h[3] & 0x3F));
        return 3;
    }
    
    private int a(final int n) {
        if (n == 13 || n == 10 || n == 32 || n == 9) {
            return 65;
        }
        int n2;
        for (n2 = 64; n2 >= 0 && (byte)n != psc_jw.e[n2]; --n2) {}
        return n2;
    }
    
    public void e() {
    }
    
    public void f() {
    }
    
    public void g() {
        for (int i = 0; i < this.h.length; ++i) {
            this.h[i] = 0;
        }
        this.f = 0;
        this.j = 0;
    }
    
    protected void finalize() throws Throwable {
        try {
            this.g();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        e = a("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", "8859_1");
    }
}
