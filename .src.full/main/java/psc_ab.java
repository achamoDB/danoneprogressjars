import java.io.UnsupportedEncodingException;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_ab extends psc_i
{
    public static final int a = -1;
    protected int b;
    protected boolean c;
    protected int d;
    protected int e;
    
    protected psc_ab(final int n, final int n2, final int n3) throws psc_m {
        this(n, n2, n3, -1, -1);
    }
    
    protected psc_ab(final int n, final int n2, final int n3, final int n4, final int n5) throws psc_m {
        this(n, true, 0, n2, n3, n4, n5);
    }
    
    private psc_ab(final int n, final boolean b, final int n2, final int n3, final int n4, int d, int e) throws psc_m {
        super(n, b, n2, n3);
        super.l |= 0x4000000;
        this.a(n4);
        if (d < 0) {
            d = -1;
        }
        if (e < 0) {
            e = -1;
        }
        if (d != -1 && e != -1 && d > e) {
            throw new psc_m("CharacterStringContainer.CharacterStringContainer: maxLen should be larger than minLen.");
        }
        this.d = d;
        this.e = e;
        super.q |= 0x20000;
    }
    
    protected psc_ab(final int n, final boolean b, final int n2, final int n3, final String s, final int n4, final int n5, final int n6) throws psc_m {
        this(n, b, n2, n3, n4, n5, n6);
        if (s == null) {
            return;
        }
        super.b = a(s, n4);
        super.p = true;
        super.d = super.b.length;
        super.c = 0;
        this.a(true);
        super.q |= 0x20000;
    }
    
    protected psc_ab(final int n, final boolean b, final int n2, final int n3, final byte[] b2, final int c, final int d, final int n4, final int n5, final int n6, final int n7) throws psc_m {
        this(n, b, n2, n3, n4, n6, n7);
        super.d = d;
        if (b2 == null) {
            return;
        }
        super.b = b2;
        if (c < 0 || c >= b2.length) {
            throw new psc_m("CharacterStringContainer.CharacterStringContainer: dataOffset is out of range.");
        }
        super.c = c;
        if (d < 0 || c + d > b2.length) {
            throw new psc_m("CharacterStringContainer.CharacterStringContainer: dataLen is out of range.");
        }
        if (n5 < 1) {
            throw new psc_m("CharacterStringContainer.CharacterStringContainer: bytesPerCharacter should be a positive integer.");
        }
        this.a(n4, n5);
        this.a(true);
        super.q |= 0x20000;
    }
    
    protected psc_ab(final int n, final boolean b, final int n2, final int n3, final int ae, final byte[] array, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) throws psc_m {
        this(n, b, n2, n3, array, n4, n5, n6, n7, n8, n9);
        super.ae = ae;
    }
    
    protected void a(final boolean b) throws psc_m {
        if (!super.a) {
            return;
        }
        if (b) {
            if (((super.q & 0x2000000) != 0x0 || (super.q & 0x40000) != 0x0) && this.d != -1 && super.d < this.d * this.b) {
                throw new psc_m("Invalid String length (too short).");
            }
            if (this.e != -1 && super.d > this.e * this.b) {
                throw new psc_m("Invalid String length (too long).");
            }
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final boolean b, final boolean b2) throws psc_m {
        super.a(array, n, n2, b, b2);
        this.a(this.b, 1);
        this.c = false;
        this.a(true);
    }
    
    public void a(final String s, final boolean b, final boolean b2) throws psc_m {
        if (s == null) {
            throw new psc_m("CharacterStringContainer.addData: newStringData should not be null.");
        }
        final byte[] a = a(s, this.b);
        super.a(a, 0, a.length, b, b2);
        super.p = true;
        this.c = false;
        this.a(true);
    }
    
    public void h() throws psc_m {
        super.q |= 0x40000;
        this.a(true);
    }
    
    public String e() {
        if (super.b == null || super.d == 0) {
            return new String();
        }
        if (this.b == 1) {
            try {
                return new String(super.b, super.c, super.d, "ISO-8859-1");
            }
            catch (UnsupportedEncodingException ex) {
                return new String(super.b, super.c, super.d);
            }
        }
        final char[] value = new char[(super.d + this.b - 1) / this.b];
        for (int i = 0, n = super.c + (this.b - 2); i < value.length; ++i, n += this.b) {
            value[i] = (char)((char)(super.b[n] << 8) | (char)(super.b[n + 1] & 0xFF));
        }
        return new String(value);
    }
    
    private static byte[] a(final String s, final int n) {
        final char[] charArray = s.toCharArray();
        final byte[] array = null;
        switch (n) {
            case 1: {
                try {
                    return s.getBytes("ISO-8859-1");
                }
                catch (UnsupportedEncodingException ex) {
                    return s.getBytes();
                }
            }
            case 2: {
                final byte[] array2 = new byte[charArray.length * 2];
                for (int i = 0; i < charArray.length; ++i) {
                    array2[2 * i] = (byte)((charArray[i] & '\uff00') >>> 8);
                    array2[2 * i + 1] = (byte)(charArray[i] & '\u00ff');
                }
                return array2;
            }
            case 4: {
                final byte[] array3 = new byte[charArray.length * 4];
                for (int j = 0; j < charArray.length; ++j) {
                    array3[4 * j + 2] = (byte)((charArray[j] & '\uff00') >>> 8);
                    array3[4 * j + 3] = (byte)(charArray[j] & '\u00ff');
                }
                return array3;
            }
            default: {
                return array;
            }
        }
    }
    
    private void a(final int n, final int n2) {
        if (n == n2) {
            return;
        }
        final int d = super.d / n2 * n;
        final byte[] b = new byte[d];
        if (n < n2) {
            int n3 = 0;
            for (int i = 0; i < super.d; i += n2) {
                for (int j = 0; j < n; ++j, ++n3) {
                    b[n3] = super.b[i + super.c];
                }
            }
        }
        else {
            int c = super.c;
            for (int k = 0; k < d; k += n) {
                for (int n4 = k + (n - n2), l = 0; l < n2; ++l, ++n4, ++c) {
                    b[n4] = super.b[c + super.c];
                }
            }
        }
        super.b = b;
        super.c = 0;
        super.d = d;
        super.p = true;
    }
    
    void a() {
        super.a();
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        final int a = super.a(psc_n, n, array, n2, n3);
        this.a(this.c = false);
        return a;
    }
    
    private void a(final int b) throws psc_m {
        switch (b) {
            case 1:
            case 2:
            case 4: {
                this.b = b;
            }
            default: {
                throw new psc_m("CharacterStringContainer.setOctetsPerChar: octetsPerCharacter should be 1, 2, or 4");
            }
        }
    }
}
