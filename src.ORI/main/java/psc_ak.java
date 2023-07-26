import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ak extends psc_ai
{
    public psc_ak(final int n) {
        this(n, true, 0, null);
    }
    
    public psc_ak(final int n, final boolean b, final int n2, final Date date) {
        super(n, b, n2, 5888);
        if (date != null) {
            this.a(date, 2, false);
        }
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        return super.a(psc_n, n, array, n2, n3, 2);
    }
    
    protected void b(final int n) throws psc_m {
        if (super.b == null) {
            return;
        }
        final int c = super.c;
        final int d = super.d;
        int a = this.a(c, n);
        int n2 = c + n;
        int n3 = d - n;
        if (a < 100) {
            if (a < super.b) {
                a += 100;
            }
            a += 1900;
        }
        final int a2 = this.a(n2, 2);
        n2 += 2;
        n3 -= 2;
        int a3 = this.a(n2, 2);
        n2 += 2;
        n3 -= 2;
        int a4 = this.a(n2, 2);
        n2 += 2;
        n3 -= 2;
        int a5 = this.a(n2, 2);
        n2 += 2;
        n3 -= 2;
        int a6 = 0;
        int n4 = 0;
        if (super.b[n2] >= 48 && super.b[n2] <= 57) {
            a6 = this.a(n2, 2);
            n2 += 2;
            n3 -= 2;
        }
        if (super.b[n2] == 43 || super.b[n2] == 45) {
            boolean b = false;
            if (super.b[n2] == 45) {
                b = true;
            }
            ++n2;
            --n3;
            int a7 = this.a(n2, 2);
            if (!b) {
                a7 = -a7;
            }
            n2 += 2;
            n3 -= 2;
            int a8 = this.a(n2, 2);
            if (!b) {
                a8 = -a8;
            }
            n2 += 2;
            n3 -= 2;
            a5 += a8;
            if (a5 > 60) {
                ++a4;
                a5 -= 60;
            }
            if (a5 < 0) {
                --a4;
                a5 += 60;
            }
            a4 += a7;
            if (a4 >= 24) {
                ++a3;
                a4 -= 24;
            }
            if (a4 < 0) {
                --a3;
                a4 += 24;
            }
            n4 = 1;
        }
        if (n3 > 0 && super.b[n2] == 90) {
            n4 = 1;
        }
        if (n4 == 1) {
            final Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            instance.set(1, a);
            instance.set(2, a2 - 1);
            instance.set(5, a3);
            instance.set(11, a4);
            instance.set(12, a5);
            instance.set(13, a6);
            instance.set(14, 0);
            super.a = instance.getTime();
            return;
        }
        throw new psc_m("UTCTime encoding is invalid: last character should be the character Z, or one of the characters + or -, followed by hhmm, where hh is hour amd mm is minutes.");
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_ak;
    }
    
    protected psc_i b() {
        return new psc_ak(super.l, true, super.m, null);
    }
}
