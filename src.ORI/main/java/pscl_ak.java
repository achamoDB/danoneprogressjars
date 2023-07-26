import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ak extends pscl_al
{
    public pscl_ak(final int n, final boolean b, final int n2, final Date date) {
        super(n, b, n2, 5888);
        if (date == null) {
            return;
        }
        super.a = date;
        final Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        instance.setTime(date);
        super.l = pscl_al.a(instance, 2, false);
        super.m = 0;
        super.n = super.l.length;
        super.o |= 0x20000;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3) throws pscl_x {
        return super.a(pscl_v, n, array, n2, n3, 2);
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_ak;
    }
    
    public pscl_q g() {
        return new pscl_ak(super.f, true, super.h, null);
    }
}
