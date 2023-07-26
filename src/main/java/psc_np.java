import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_np
{
    public static final int a = 1;
    private int b;
    private Date c;
    private Date d;
    private Date e;
    private psc_bg f;
    private psc_oj g;
    
    public psc_np(final int b, final Date c, final Date d, final Date e, final psc_bg f, final psc_oj g) throws psc_d7 {
        if (c == null) {
            throw new psc_d7("producedAtTime == null");
        }
        if (d == null) {
            throw new psc_d7("thisUpdateTime == null");
        }
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }
    
    public String toString() {
        return "{flags=" + Integer.toHexString(this.b) + ",producedAt=" + this.c + ",thisUpdate=" + this.d + ",nextUpdate=" + this.e + ",responseExtensions=" + this.f + ",revocationInfo=" + this.g + "}";
    }
    
    public int a() {
        return this.b;
    }
    
    public void a(final int b) {
        this.b = b;
    }
    
    public Date b() {
        return this.c;
    }
    
    public Date c() {
        return this.d;
    }
    
    public Date d() {
        return this.e;
    }
    
    public psc_bg e() {
        return this.f;
    }
    
    public psc_oj f() {
        return this.g;
    }
}
