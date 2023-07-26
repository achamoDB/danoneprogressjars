import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_od
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    private int f;
    private psc_nw g;
    private psc_ny h;
    private String i;
    private int[] j;
    private int k;
    private Date l;
    private byte[] m;
    private String[] n;
    private psc_f[] o;
    private psc_na[] p;
    private Object q;
    
    public psc_od() {
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = -1;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
    }
    
    public void a(final int f, final psc_nw g, final psc_ny h, final String i, final int[] j) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
    }
    
    public int f() {
        return this.f;
    }
    
    public psc_nw g() {
        return this.g;
    }
    
    public psc_ny h() {
        return this.h;
    }
    
    public String i() {
        return this.i;
    }
    
    public int[] j() {
        return this.j;
    }
    
    public int k() {
        return this.k;
    }
    
    public void a(final int k) {
        this.k = k;
    }
    
    public Date l() {
        return this.l;
    }
    
    public void a(final Date l) {
        this.l = l;
    }
    
    public Object m() {
        return this.q;
    }
    
    public void a(final Object q) {
        this.q = q;
    }
    
    public byte[] n() {
        return this.m;
    }
    
    public void a(final byte[] m) {
        this.m = m;
    }
    
    public String[] o() {
        return this.n;
    }
    
    public void a(final String[] n) {
        this.n = n;
    }
    
    public psc_f[] p() {
        return this.o;
    }
    
    public void b(final psc_f[] o) {
        this.o = o;
    }
    
    public psc_na[] q() {
        return this.p;
    }
    
    public void a(final psc_na[] p) {
        this.p = p;
    }
}
