// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ds
{
    private String[] a;
    private String b;
    private String c;
    private String d;
    private byte[] e;
    private boolean f;
    private psc_dt g;
    private char[] h;
    private psc_e[] i;
    private byte[][] j;
    private byte[] k;
    private int l;
    
    public psc_ds(final psc_e[] array, final byte[] array2, final char[] array3) throws psc_d {
        this(array);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        if (array3 != null) {
            this.h = new char[array3.length];
            for (int i = 0; i < array3.length; ++i) {
                this.h[i] = array3[i];
                array3[i] = '\0';
            }
            this.f = true;
        }
        else {
            this.h = null;
        }
    }
    
    protected psc_ds(final psc_e[] i) throws psc_d {
        this.f = true;
        try {
            this.a = new String[i.length];
            for (int j = 0; j < this.a.length; ++j) {
                this.a[j] = i[j].g().toString();
            }
            this.b = i[0].f().toString();
            final psc_al b = i[0].b("Java");
            this.c = b.l();
            this.l = b.b(this.c + "PublicKey")[0].length;
            this.d = i[0].r();
            this.d = this.d.substring(this.d.indexOf(47) + 1, this.d.lastIndexOf(47));
            this.i = i;
            this.a();
        }
        catch (psc_g psc_g) {
            throw new psc_d("Could not load the key from the certificate issued to " + this.b);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d(psc_ap.getMessage());
        }
    }
    
    public psc_ds(final psc_e[] i, final psc_dt g) throws psc_d {
        this(i);
        this.g = g;
        this.i = i;
        this.f = false;
    }
    
    private void a() throws psc_g {
        int n = 0;
        int n2 = 0;
        this.j = new byte[this.i.length][];
        for (int i = 0; i < this.i.length; ++i) {
            this.j[i] = new byte[this.i[i].c(0)];
            this.i[i].d(this.j[i], 0, 0);
            n += this.j[i].length;
            n += 3;
        }
        this.k = new byte[n];
        for (int j = 0; j < this.j.length; ++j) {
            final int length = this.j[j].length;
            this.k[n2] = (byte)(length >> 16 & 0xFF);
            this.k[1 + n2] = (byte)(length >> 8 & 0xFF);
            this.k[2 + n2] = (byte)(length & 0xFF);
            n2 += 3;
            System.arraycopy(this.j[j], 0, this.k, n2, length);
            n2 += length;
        }
    }
    
    public boolean a(final String s) throws psc_d {
        try {
            return this.i[0].b(s).l().equals("DSA");
        }
        catch (psc_g psc_g) {
            throw new psc_d("Can not get the signature algorithm of certificate");
        }
    }
    
    public String[] b() {
        return this.a;
    }
    
    public String c() {
        return this.b;
    }
    
    public psc_e[] d() {
        return this.i;
    }
    
    public psc_e a(final int n) {
        return this.i[n];
    }
    
    public int e() {
        return this.i.length;
    }
    
    public byte[] f() {
        return this.e;
    }
    
    public psc_dt g() {
        return this.g;
    }
    
    public char[] h() {
        return this.h;
    }
    
    public boolean i() {
        return this.f;
    }
    
    public int j() {
        return this.l;
    }
    
    public String k() {
        return this.c;
    }
    
    public String l() {
        return this.d;
    }
    
    public byte[][] m() {
        return this.j;
    }
    
    public byte[] b(final int n) {
        return this.j[n];
    }
    
    public byte[] n() {
        return this.k;
    }
}
