import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_nq
{
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 4;
    public static final int d = 8;
    public static final int e = 16;
    public static final int f = 32;
    public static final int g = 64;
    public static final int h = 128;
    public static final int i = 256;
    public static final int j = 512;
    public static final int k = 1024;
    public static final int l = 2048;
    public static final int m = 4096;
    public static final int n = 8192;
    public static final int o = 16384;
    private int p;
    private psc_f[] q;
    private byte[][] r;
    private Date s;
    private psc_ed t;
    
    public psc_nq(final int p5, final psc_f[] q, final byte[][] r, final Date s, final psc_ed t) {
        this.p = p5;
        this.q = q;
        this.r = r;
        this.s = s;
        this.t = t;
    }
    
    public int a() {
        return this.p;
    }
    
    public psc_f[] b() {
        if (this.q == null) {
            return null;
        }
        return this.q.clone();
    }
    
    public byte[][] c() {
        if (this.r == null) {
            return null;
        }
        return this.r.clone();
    }
    
    public Date d() {
        return this.s;
    }
    
    public psc_ed e() {
        return this.t;
    }
}
