import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_oj implements Cloneable
{
    private int a;
    private Date b;
    
    public psc_oj(final long date) {
        this.a = -1;
        this.b = new Date(date);
    }
    
    public psc_oj(final int a, final Date date) {
        this.a = a;
        this.b = ((date == null) ? null : new Date(date.getTime()));
    }
    
    public psc_oj(final int a, final long date) {
        this.a = a;
        this.b = new Date(date);
    }
    
    public Object a(final psc_oj psc_oj) {
        return (psc_oj == null) ? null : new psc_oj(psc_oj.a, psc_oj.b);
    }
    
    public int a() {
        return this.a;
    }
    
    public Date b() {
        return new Date(this.b.getTime());
    }
    
    public void a(final int a) {
        this.a = a;
    }
}
