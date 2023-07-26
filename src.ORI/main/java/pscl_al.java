import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_al extends pscl_q
{
    public Date a;
    public int b;
    
    public pscl_al(final int n, final boolean b, final int n2, final Date date) {
        super(n, b, n2, 6144);
        this.b = 50;
        if (date == null) {
            return;
        }
        this.a = date;
        final Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        instance.setTime(date);
        super.l = a(instance, 4, true);
        super.m = 0;
        super.n = super.l.length;
        super.o |= 0x20000;
    }
    
    public pscl_al(final int n, final boolean b, final int n2, final int n3) {
        super(n, b, n2, n3);
        this.b = 50;
    }
    
    public void a(final int b) {
        if (b >= 0 && b < 100) {
            this.b = b;
        }
    }
    
    public void c() {
        super.c();
        this.a = null;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3) throws pscl_x {
        final int a = super.a(pscl_v, n, array, n2, n3);
        if ((super.o & 0x2000000) != 0x0) {
            this.b(4);
        }
        return a;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3, final int n4) throws pscl_x {
        final int a = super.a(pscl_v, n, array, n2, n3);
        if ((super.o & 0x2000000) != 0x0) {
            this.b(n4);
        }
        return a;
    }
    
    public void b(final int n) {
        if (super.l == null) {
            return;
        }
        final int m = super.m;
        final int n2 = super.n;
        int a = this.a(m, n);
        int n3 = m + n;
        int n4 = n2 - n;
        if (a < 100) {
            if (a < this.b) {
                a += 100;
            }
            a += 1900;
        }
        final int a2 = this.a(n3, 2);
        n3 += 2;
        n4 -= 2;
        int a3 = this.a(n3, 2);
        n3 += 2;
        n4 -= 2;
        int a4 = this.a(n3, 2);
        n3 += 2;
        n4 -= 2;
        int a5 = this.a(n3, 2);
        n3 += 2;
        n4 -= 2;
        int a6 = 0;
        int a7 = 0;
        int n5 = 0;
        if (super.l[n3] >= 48 && super.l[n3] <= 57) {
            a6 = this.a(n3, 2);
            n3 += 2;
            n4 -= 2;
            if (super.l[n3] == 46) {
                a7 = this.a(n3 + 1, 1);
                n3 += 2;
                n4 -= 2;
            }
        }
        if (super.l[n3] == 43 || super.l[n3] == 45) {
            boolean b = false;
            if (super.l[n3] == 45) {
                b = true;
            }
            ++n3;
            --n4;
            int a8 = this.a(n3, 2);
            if (!b) {
                a8 = -a8;
            }
            n3 += 2;
            n4 -= 2;
            int a9 = this.a(n3, 2);
            if (!b) {
                a9 = -a9;
            }
            n3 += 2;
            n4 -= 2;
            a5 += a9;
            if (a5 > 60) {
                ++a4;
                a5 -= 60;
            }
            if (a5 < 0) {
                --a4;
                a5 += 60;
            }
            a4 += a8;
            if (a4 >= 24) {
                ++a3;
                a4 -= 24;
            }
            if (a4 < 0) {
                --a3;
                a4 += 24;
            }
            n5 = 1;
        }
        if (n4 > 0 && super.l[n3] == 90) {
            n5 = 1;
        }
        Calendar calendar;
        if (n5 == 1) {
            calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        }
        else {
            calendar = Calendar.getInstance();
        }
        calendar.set(1, a);
        calendar.set(2, a2 - 1);
        calendar.set(5, a3);
        calendar.set(11, a4);
        calendar.set(12, a5);
        calendar.set(13, a6);
        calendar.set(14, a7 * 100);
        this.a = calendar.getTime();
    }
    
    private int a(final int n, final int n2) {
        int n3 = 0;
        int n4 = 1;
        for (int i = n + n2 - 1; i >= n; --i) {
            n3 += (super.l[i] - 48) * n4;
            n4 *= 10;
        }
        return n3;
    }
    
    public static byte[] a(final Calendar calendar, final int n, final boolean b) {
        int n2 = 11 + n;
        if (b) {
            n2 += 2;
        }
        final byte[] array = new byte[n2];
        final int n3 = 0;
        final int n4 = n3 + a(calendar.get(1), n, array, n3);
        final int n5 = n4 + a(calendar.get(2) + 1, 2, array, n4);
        final int n6 = n5 + a(calendar.get(5), 2, array, n5);
        final int n7 = n6 + a(calendar.get(11), 2, array, n6);
        final int n8 = n7 + a(calendar.get(12), 2, array, n7);
        int n9 = n8 + a(calendar.get(13), 2, array, n8);
        if (b) {
            array[n9] = 46;
            ++n9;
            n9 += a(calendar.get(14) / 100, 1, array, n9);
        }
        array[n9] = 90;
        return array;
    }
    
    public static int a(int n, final int n2, final byte[] array, int n3) {
        int n4 = 1;
        for (int i = 1; i <= n2; ++i) {
            n4 *= 10;
        }
        n -= n / n4 * n4;
        int n5 = n4 / 10;
        for (int j = 0; j < n2; ++j, ++n3) {
            int n6 = n / n5;
            n -= n6 * n5;
            n6 += 48;
            array[n3] = (byte)n6;
            n5 /= 10;
        }
        return n2;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_al;
    }
    
    public pscl_q g() {
        return new pscl_al(super.f, true, super.h, null);
    }
}
