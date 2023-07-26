import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ai extends psc_i
{
    public Date a;
    protected int b;
    
    public psc_ai(final int n) {
        this(n, true, 0, null);
    }
    
    public psc_ai(final int n, final boolean b, final int n2, final Date date) {
        super(n, b, n2, 6144);
        this.b = 50;
        if (date != null) {
            this.a(date, 4, true);
        }
    }
    
    protected psc_ai(final int n, final boolean b, final int n2, final int n3) {
        super(n, b, n2, n3);
        this.b = 50;
    }
    
    protected void a(final int b) {
        if (b >= 0 && b < 100) {
            this.b = b;
        }
    }
    
    void a() {
        super.a();
        this.a = null;
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        final int a = super.a(psc_n, n, array, n2, n3);
        if ((super.q & 0x2000000) != 0x0) {
            this.b(4);
        }
        return a;
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3, final int n4) throws psc_m {
        final int a = super.a(psc_n, n, array, n2, n3);
        if ((super.q & 0x2000000) != 0x0) {
            this.b(n4);
        }
        return a;
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
            if (a < this.b) {
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
        final int a7 = this.a(n2, 2);
        n2 += 2;
        n3 -= 2;
        if (super.b[n2] == 46) {
            a6 = this.a(n2 + 1, 1);
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
            int a8 = this.a(n2, 2);
            if (!b) {
                a8 = -a8;
            }
            n2 += 2;
            n3 -= 2;
            int a9 = this.a(n2, 2);
            if (!b) {
                a9 = -a9;
            }
            n2 += 2;
            n3 -= 2;
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
            n4 = 1;
        }
        if (n3 > 0 && super.b[n2] == 90) {
            n4 = 1;
        }
        Calendar calendar;
        if (n4 == 1) {
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
        calendar.set(13, a7);
        calendar.set(14, a6 * 100);
        this.a = calendar.getTime();
    }
    
    protected int a(final int n, final int n2) {
        int n3 = 0;
        int n4 = 1;
        for (int i = n + n2 - 1; i >= n; --i) {
            n3 += (super.b[i] - 48) * n4;
            n4 *= 10;
        }
        return n3;
    }
    
    protected void a(final Date date, final int n, final boolean b) {
        this.a = date;
        final Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        instance.setTime(date);
        super.b = a(instance, n, b);
        super.c = 0;
        super.d = super.b.length;
        super.q |= 0x20000;
    }
    
    private static byte[] a(final Calendar calendar, final int n, final boolean b) {
        int n2 = 11 + n;
        if (b) {
            n2 += 2;
        }
        byte[] array = new byte[n2];
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
            final int n10 = calendar.get(14) / 100;
            if (n10 != 0) {
                n9 += a(n10, 1, array, n9);
            }
            else {
                --n9;
                final byte[] array2 = new byte[array.length - 2];
                System.arraycopy(array, 0, array2, 0, array.length - 3);
                array = new byte[array.length - 2];
                System.arraycopy(array2, 0, array, 0, array2.length);
            }
        }
        array[n9] = 90;
        return array;
    }
    
    protected static int a(int n, final int n2, final byte[] array, int n3) {
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
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_ai;
    }
    
    protected psc_i b() {
        return new psc_ai(super.l, true, super.m, null);
    }
}
