import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ez implements psc_e0, Cloneable, Serializable
{
    public static final int[] a;
    public static byte[] b;
    public static final psc_ez c;
    public static final psc_ez d;
    private int[] e;
    private int f;
    private transient psc_dd g;
    private int h;
    private static final int i = 0;
    private static final int j = 1;
    private static final int k = 2;
    private static final int l = 4;
    private static final int m = 32;
    private static final long n = 4294967295L;
    private static final String o = "JCMPInt operation yields negative result.";
    
    private static psc_ez a(final int n) {
        final psc_ez psc_ez = new psc_ez();
        psc_ez.b(n);
        return psc_ez;
    }
    
    public void h(final int n) throws psc_e1 {
        if (n < 0) {
            throw new psc_e1("JCMPInt operation yields negative result.");
        }
        this.d(1);
        this.e[0] = n;
        this.f = 1;
    }
    
    private void b(final int n) {
        this.d(1);
        this.e[0] = n;
        this.f = 1;
    }
    
    public void a(final psc_e0 psc_e0) {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        if (psc_ez.f == 0) {
            psc_ez.b(0);
        }
        this.d(psc_ez.f);
        final int h = psc_ez.h;
        if ((h & 0x1) != 0x0) {
            psc_ez.q();
        }
        for (int i = 0; i < psc_ez.f; ++i) {
            this.e[i] = psc_ez.e[i];
        }
        if ((h & 0x1) != 0x0) {
            psc_ez.p();
        }
        this.f = psc_ez.f;
        this.d();
    }
    
    public void a(final byte[] array, final int n, int n2) {
        final int f = (n2 * 8 + 31) / 32;
        this.d(f);
        this.f = f;
        int n3 = n + n2 - 1;
        for (int i = 0; i < this.f; ++i, n2 -= 4) {
            int n4 = 24;
            if (n2 < 4) {
                n4 = n2 - 1 << 3;
            }
            int n5 = 0;
            for (int j = 0; j <= n4; j += 8, --n3) {
                n5 |= (array[n3] & 0xFF) << j;
            }
            this.e[i] = n5;
        }
        this.d();
    }
    
    public void i(final int n) throws psc_e1 {
        if (n < 0) {
            this.h();
            throw new psc_e1("Cannot create a JCMPInt with a negative exponent.");
        }
        this.c(n / 32 + 1);
        this.a(n, 1);
    }
    
    private void c(final int n) {
        if (!this.d(n)) {
            for (int i = 0; i < this.e.length; ++i) {
                this.e[i] = 0;
            }
        }
        this.f = 1;
    }
    
    private boolean d(final int n) {
        if (this.e == null) {
            this.e = new int[n];
            return true;
        }
        if (this.e.length < n) {
            if (this.g != null) {
                this.r();
            }
            this.e = new int[n];
            return true;
        }
        this.h();
        return false;
    }
    
    public boolean i() {
        if ((this.h & 0x1) == 0x0) {
            return (this.e[0] & 0x1) == 0x0;
        }
        this.q();
        final boolean b = (this.e[0] & 0x1) == 0x0;
        this.p();
        return b;
    }
    
    public boolean j() {
        return !this.i();
    }
    
    public boolean k() {
        if ((this.h & 0x1) == 0x0) {
            return this.f == 1 && this.e[0] == 0;
        }
        this.q();
        final boolean b = this.f == 1 && this.e[0] == 0;
        this.p();
        return b;
    }
    
    boolean a() {
        if ((this.h & 0x1) == 0x0) {
            return this.f == 1 && this.e[0] == 3;
        }
        this.q();
        final boolean b = this.f == 1 && this.e[0] == 3;
        this.p();
        return b;
    }
    
    public int l() {
        final byte[] n = this.n();
        int n2 = 0;
        for (int i = 0; i < n.length; ++i) {
            n2 = (n2 << 8) + (n[i] & 0xFF);
        }
        return n2;
    }
    
    public long m() {
        final byte[] n = this.n();
        long n2 = 0L;
        for (int i = 0; i < n.length; ++i) {
            n2 = (n2 << 8) + (n[i] & 0xFF);
        }
        return n2;
    }
    
    public byte[] n() {
        if (this.f == 0) {
            return new byte[1];
        }
        final int h = this.h;
        if ((h & 0x1) != 0x0) {
            this.q();
        }
        int n = (this.o() + 7) / 8;
        final byte[] array = new byte[n];
        int n2 = n - 1;
        for (int i = 0; i < this.f; ++i, n -= 4) {
            int n3 = this.e[i];
            int n4 = 4;
            if (n < 4) {
                n4 = n;
            }
            for (int j = 0; j < n4; ++j, --n2) {
                array[n2] = (byte)n3;
                n3 >>>= 8;
            }
        }
        if ((h & 0x1) != 0x0) {
            this.p();
        }
        return array;
    }
    
    public byte[] j(final int i) throws psc_e1 {
        final byte[] n = this.n();
        if (n.length == i) {
            return n;
        }
        if (n.length > i) {
            psc_au.c(n);
            throw new psc_e1("JCMPInt too large for toFixedLenOctetString (" + i + ")");
        }
        final byte[] array = new byte[i];
        System.arraycopy(n, 0, array, i - n.length, n.length);
        psc_au.c(n);
        return array;
    }
    
    public int o() {
        if (this.f == 0) {
            this.b(0);
        }
        final int h = this.h;
        if ((h & 0x1) != 0x0) {
            this.q();
        }
        this.d();
        int n = this.e[this.f - 1];
        if ((h & 0x1) != 0x0) {
            this.p();
        }
        if (this.f == 1 && n == 0) {
            return 1;
        }
        int n2 = this.f * 32;
        if ((n & 0xFFFF0000) == 0x0) {
            n2 -= 16;
            n <<= 16;
        }
        if ((n & 0xFF000000) == 0x0) {
            n2 -= 8;
            n <<= 8;
        }
        if ((n & 0xF0000000) == 0x0) {
            n2 -= 4;
            n <<= 4;
        }
        while ((n & Integer.MIN_VALUE) == 0x0) {
            --n2;
            n <<= 1;
            if (n2 <= 0) {
                return n2;
            }
        }
        return n2;
    }
    
    public int k(int n) {
        final int h = this.h;
        if ((h & 0x1) != 0x0) {
            this.q();
        }
        if (this.o() <= n) {
            if ((h & 0x1) != 0x0) {
                this.p();
            }
            return 0;
        }
        final int n2 = n / 32;
        n %= 32;
        final int n3 = this.e[n2];
        if ((h & 0x1) != 0x0) {
            this.p();
        }
        return n3 >>> n & 0x1;
    }
    
    public void a(int n, final int n2) {
        final int n3 = n / 32;
        n %= 32;
        if (n3 < this.f) {
            final int h = this.h;
            if ((h & 0x1) != 0x0) {
                this.q();
            }
            final int n4 = this.e[n3];
            final int n5 = 1 << n;
            int n6 = n4 & ~n5;
            if (n2 != 0) {
                n6 |= n5;
            }
            this.e[n3] = n6;
            this.d();
            if ((h & 0x1) != 0x0) {
                this.p();
            }
            return;
        }
        if (n2 == 0) {
            return;
        }
        final int h2 = this.h;
        if ((h2 & 0x1) != 0x0) {
            this.q();
        }
        final int n7 = n3 - this.f + 1;
        final int[] array = new int[n7];
        array[n7 - 1] = 1 << n;
        this.a(array, 0, n7);
        if ((h2 & 0x1) != 0x0) {
            this.p();
        }
    }
    
    public int b(final psc_e0 psc_e0) {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        if (this.f == 0) {
            this.b(0);
        }
        if (psc_ez.f == 0) {
            psc_ez.b(0);
        }
        if (this.f > psc_ez.f) {
            return 1;
        }
        if (this.f < psc_ez.f) {
            return -1;
        }
        final int a = this.a(this, psc_ez, null);
        int n = 0;
        int i = this.f - 1;
        while (i >= 0) {
            final long n2 = (long)this.e[i] & 0xFFFFFFFFL;
            final long n3 = (long)psc_ez.e[i] & 0xFFFFFFFFL;
            if (n2 == n3) {
                --i;
            }
            else {
                n = -1;
                if (n2 > n3) {
                    n = 1;
                    break;
                }
                break;
            }
        }
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        return n;
    }
    
    private int a(final psc_ez psc_ez) {
        if (this.f > psc_ez.f) {
            return 1;
        }
        if (this.f < psc_ez.f) {
            return -1;
        }
        int n = 0;
        int i = this.f - 1;
        while (i >= 0) {
            final long n2 = (long)this.e[i] & 0xFFFFFFFFL;
            final long n3 = (long)psc_ez.e[i] & 0xFFFFFFFFL;
            if (n2 == n3) {
                --i;
            }
            else {
                n = -1;
                if (n2 > n3) {
                    n = 1;
                    break;
                }
                break;
            }
        }
        return n;
    }
    
    public void a(final psc_e0 psc_e0, final psc_e0 psc_e2) {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final int a = this.a(this, psc_ez, null);
        int f = this.f;
        if (psc_ez.f > f) {
            f = psc_ez.f;
        }
        psc_ez2.d(f);
        final int a2 = this.a(this.e, this.f, psc_ez.e, psc_ez.f, psc_ez2.e);
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        psc_ez2.f = f;
        if (a2 != 0) {
            psc_ez2.f(f);
        }
        psc_ez2.d();
    }
    
    public void l(final int n) throws psc_e1 {
        if (this.f == 0) {
            this.b(0);
        }
        final int h = this.h;
        if ((h & 0x1) != 0x0) {
            this.q();
        }
        final long n2 = ((long)this.e[0] & 0xFFFFFFFFL) + n;
        this.e[0] = (int)n2;
        if (n2 >>> 32 != 0L) {
            if (n >= 0) {
                this.f(1);
            }
            else {
                this.g(1);
            }
        }
        this.d();
        if ((h & 0x1) != 0x0) {
            this.p();
        }
    }
    
    public void c(final psc_e0 psc_e0) {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final int a = this.a(this, psc_ez, null);
        int f = this.f;
        if (psc_ez.f > f) {
            f = psc_ez.f;
        }
        int[] e = this.e;
        if (this.e.length < f) {
            e = new int[f];
        }
        final int a2 = this.a(this.e, this.f, psc_ez.e, psc_ez.f, e);
        if (e != this.e) {
            this.r();
            this.e = e;
        }
        this.f = f;
        if (a2 != 0) {
            this.f(f);
        }
        this.d();
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
    }
    
    private int a(final int[] array, final int n, final int[] array2, final int n2, final int[] array3) {
        int i = 0;
        long n3 = 0L;
        if (n < n2) {
            while (i < n) {
                final long n4 = ((long)array[i] & 0xFFFFFFFFL) + ((long)array2[i] & 0xFFFFFFFFL) + n3;
                array3[i] = (int)n4;
                n3 = n4 >>> 32;
                ++i;
            }
            while (i < n2) {
                array3[i] = array2[i];
                ++i;
            }
            long n6;
            for (int n5 = n; n3 != 0L && n5 < n2; n3 = n6 >>> 32, ++n5) {
                n6 = ((long)array3[n5] & 0xFFFFFFFFL) + n3;
                array3[n5] = (int)n6;
            }
            return (int)n3;
        }
        while (i < n2) {
            final long n7 = ((long)array[i] & 0xFFFFFFFFL) + ((long)array2[i] & 0xFFFFFFFFL) + n3;
            array3[i] = (int)n7;
            n3 = n7 >>> 32;
            ++i;
        }
        if (n == n2) {
            return (int)n3;
        }
        if (array3 != array) {
            while (i < n) {
                array3[i] = array[i];
                ++i;
            }
            i = n2;
        }
        while (n3 != 0L && i < n) {
            final long n8 = ((long)array3[i] & 0xFFFFFFFFL) + n3;
            array3[i] = (int)n8;
            n3 = n8 >>> 32;
            ++i;
        }
        return (int)n3;
    }
    
    public void b(final psc_e0 psc_e0, final psc_e0 psc_e2) throws psc_e1 {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final int a = this.a(this, psc_ez, null);
        final int a2 = this.a(psc_ez);
        if (a2 > 0) {
            final int f = this.f;
            psc_ez2.d(f);
            this.b(this.e, this.f, psc_ez.e, psc_ez.f, psc_ez2.e);
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            psc_ez2.f = f;
            psc_ez2.d();
            return;
        }
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        psc_ez2.b(0);
        if (a2 == 0) {
            return;
        }
        throw new psc_e1("JCMPInt operation yields negative result.");
    }
    
    public void m(final int n) throws psc_e1 {
        this.l(-n);
    }
    
    public void d(final psc_e0 psc_e0) throws psc_e1 {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final int a = this.a(this, psc_ez, null);
        final int a2 = this.a(psc_ez);
        if (a2 > 0) {
            this.b(this.e, this.f, psc_ez.e, psc_ez.f, this.e);
            this.d();
            return;
        }
        this.b(0);
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        if (a2 == 0) {
            return;
        }
        throw new psc_e1("JCMPInt operation yields negative result.");
    }
    
    private int b(final int[] array, final int n, final int[] array2, final int n2, final int[] array3) {
        int i = 0;
        long n3 = 0L;
        while (i < n2) {
            final long n4 = ((long)array[i] & 0xFFFFFFFFL) - ((long)array2[i] & 0xFFFFFFFFL) + n3;
            array3[i] = (int)n4;
            n3 = n4 >> 32;
            ++i;
        }
        if (n == n2) {
            return (int)n3;
        }
        if (array3 != array) {
            while (i < n) {
                array3[i] = array[i];
                ++i;
            }
            i = n2;
        }
        while (n3 != 0L && i < n) {
            final long n5 = ((long)array3[i] & 0xFFFFFFFFL) + n3;
            array3[i] = (int)n5;
            n3 = n5 >> 32;
            ++i;
        }
        return (int)n3;
    }
    
    public void c(final psc_e0 psc_e0, final psc_e0 psc_e2) {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final int a = this.a(this, psc_ez, null);
        psc_ez2.c(this.f + psc_ez.f);
        final long n = (long)this.e[0] & 0xFFFFFFFFL;
        final int f = psc_ez.f;
        long n2 = 0L;
        int n3 = 0;
        for (int i = 0; i < f; ++i, ++n3) {
            final long n4 = ((long)psc_ez.e[i] & 0xFFFFFFFFL) * n + n2;
            psc_ez2.e[n3] = (int)n4;
            n2 = n4 >>> 32;
        }
        psc_ez2.e[n3] = (int)n2;
        for (int j = 1; j < this.f; ++j) {
            final long n5 = (long)this.e[j] & 0xFFFFFFFFL;
            n3 = j;
            long n6 = 0L;
            for (int k = 0; k < f; ++k, ++n3) {
                final long n7 = ((long)psc_ez.e[k] & 0xFFFFFFFFL) * n5 + ((long)psc_ez2.e[n3] & 0xFFFFFFFFL) + n6;
                psc_ez2.e[n3] = (int)n7;
                n6 = n7 >>> 32;
            }
            psc_ez2.e[n3] = (int)n6;
        }
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        psc_ez2.f = n3 + 1;
        psc_ez2.d();
    }
    
    private void a(final int n, final int n2, final int n3, final psc_ez psc_ez, int n4) {
        final long n5 = (long)n & 0xFFFFFFFFL;
        long n6 = 0L;
        for (int i = 0; i < n3; ++i, ++n4) {
            final long n7 = n5 * ((long)this.e[n2 + i] & 0xFFFFFFFFL) + ((long)psc_ez.e[n4] & 0xFFFFFFFFL) + n6;
            psc_ez.e[n4] = (int)n7;
            n6 = n7 >>> 32;
        }
        final long n8 = n6 + ((long)psc_ez.e[n4] & 0xFFFFFFFFL);
        psc_ez.e[n4] = (int)n8;
        final long n9 = n8 >>> 32;
        if (n4 + 1 > psc_ez.f) {
            psc_ez.f = n4 + 1;
        }
        if (n9 != 0L) {
            psc_ez.f(n4 + 1);
        }
    }
    
    public void a(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_e1 {
        final psc_ez psc_ez = new psc_ez();
        final int a = this.a(this, (psc_ez)psc_e0, (psc_ez)psc_e2);
        this.c(psc_e0, psc_ez);
        Label_0055: {
            try {
                psc_ez.d(psc_e2, psc_e3);
                break Label_0055;
            }
            finally {
                if (a != 0) {
                    this.a(a, this, (psc_ez)psc_e0, (psc_ez)psc_e2);
                }
                psc_ez.r();
                while (true) {}
                // iftrue(Label_0077:, a == 0)
            Label_0077:
                while (true) {
                    this.a(a, this, (psc_ez)psc_e0, (psc_ez)psc_e2);
                    break Label_0077;
                    continue;
                }
                psc_ez.r();
            }
        }
    }
    
    public void b(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_e1 {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final psc_ez psc_ez3 = (psc_ez)psc_e3;
        final int a = this.a(this, psc_ez, null);
        psc_ez.d();
        Object o = null;
        final int f = this.f + 2 - psc_ez.f;
        if (f > 0) {
            o = new int[f];
        }
        if (psc_ez.f == 1 && psc_ez.e[0] == 0) {
            psc_ez2.b(0);
            psc_ez3.b(0);
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            throw new psc_e1("Cannot divide by zero.");
        }
        final int a2 = this.a(psc_ez);
        if (a2 <= 0) {
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            if (a2 == 0) {
                psc_ez2.b(1);
                psc_ez3.b(0);
                return;
            }
            psc_ez2.h(0);
            psc_ez3.a((psc_e0)this);
        }
        else {
            final int o2 = this.o();
            final int o3 = psc_ez.o();
            if (o2 == o3) {
                psc_ez2.b(1);
                this.b(psc_ez, (psc_e0)psc_ez3);
                if (a != 0) {
                    this.a(a, this, psc_ez, null);
                }
                return;
            }
            int n = o3 % 32;
            if (n != 0) {
                n = 32 - n;
            }
            if (f > psc_ez.f) {
                psc_ez2.c(f);
            }
            psc_ez2.a((psc_e0)psc_ez);
            psc_ez3.c(this.f + 1);
            psc_ez3.a((psc_e0)this);
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            if (n != 0) {
                psc_ez3.n(n);
                psc_ez2.n(n);
            }
            final int[] e = psc_ez3.e;
            final int f2 = psc_ez3.f;
            final int[] e2 = psc_ez2.e;
            final int f3 = psc_ez2.f;
            int i = f2 - f3;
            int n2 = f2 - 1;
            int n3;
            int n4;
            for (n3 = f2 - 1, n4 = f3 - 1; n4 > 0 && e[n3] == e2[n4]; --n3, --n4) {}
            if (((long)e[n3] & 0xFFFFFFFFL) > ((long)e2[n4] & 0xFFFFFFFFL)) {
                long n5 = 0L;
                for (int j = 0, n6 = i; j < f3; ++j, ++n6) {
                    final long n7 = n5 + ((long)e[n6] & 0xFFFFFFFFL) - ((long)e2[j] & 0xFFFFFFFFL);
                    e[n6] = (int)n7;
                    n5 = n7 >> 32;
                }
                o[i] = true;
            }
            --i;
            final long n8 = (long)e2[0] & 0xFFFFFFFFL;
            final long n9 = (long)e2[f3 - 1] & 0xFFFFFFFFL;
            final long n10 = n9 >>> 1;
            long n11 = 0L;
            if (f3 > 1) {
                n11 = ((long)e2[f3 - 2] & 0xFFFFFFFFL);
            }
            while (i >= 0) {
                final long n12 = (long)e[n2] << 32 | ((long)e[n2 - 1] & 0xFFFFFFFFL);
                long n13;
                long n14;
                if (n12 >= 0L) {
                    n13 = n12 / n9;
                    n14 = n12 - n13 * n9;
                }
                else {
                    for (n13 = (n12 >>> 1) / n10, n14 = n12 - n13 * n9; n14 < 0L; n14 += n9, --n13) {}
                    while (n14 > n9) {
                        n14 -= n9;
                        ++n13;
                    }
                }
                Label_1100: {
                    if (f3 != 1 && n13 != 0L) {
                        final long n15 = n13 * n11;
                        final long n16 = n15 & 0xFFFFFFFFL;
                        while (true) {
                            for (long n17 = n15 >>> 32; n14 <= n17; n14 += n9, n17 -= n11) {
                                if (n14 == n17 && ((long)e[n2 - 2] & 0xFFFFFFFFL) >= n16) {
                                    if (n13 > 4294967295L) {
                                        n13 = 4294967295L;
                                    }
                                    final long n18 = n13 * n8;
                                    final long n19 = ((long)e[i] & 0xFFFFFFFFL) - (n18 & 0xFFFFFFFFL);
                                    e[i] = (int)n19;
                                    long n20 = (n18 >>> 32) - (n19 >> 32);
                                    for (int k = 1, n21 = i + 1; k < f3; ++k, ++n21) {
                                        final long n22 = n13 * ((long)e2[k] & 0xFFFFFFFFL) + n20;
                                        final long n23 = ((long)e[n21] & 0xFFFFFFFFL) - (n22 & 0xFFFFFFFFL);
                                        e[n21] = (int)n23;
                                        n20 = (n22 >>> 32) - (n23 >> 32);
                                    }
                                    if (n20 != 0L) {
                                        final long n24 = ((long)e[n2] & 0xFFFFFFFFL) - n20;
                                        e[n2] = 0;
                                        if (n24 < 0L) {
                                            --n13;
                                            long n25 = 0L;
                                            for (int l = 0, n26 = i; l < f3; ++l, ++n26) {
                                                final long n27 = n25 + (((long)e[n26] & 0xFFFFFFFFL) + ((long)e2[l] & 0xFFFFFFFFL));
                                                e[n26] = (int)n27;
                                                n25 = n27 >>> 32;
                                            }
                                        }
                                    }
                                    o[i] = (int)n13;
                                    --n2;
                                    break Label_1100;
                                }
                                --n13;
                            }
                            continue;
                        }
                    }
                    o[i] = (int)n13;
                    e[n2] = 0;
                    e[n2 - 1] = (int)n14;
                    --n2;
                }
                --i;
            }
            System.arraycopy(o, 0, psc_ez2.e, 0, f);
            psc_ez2.f = f;
            psc_ez2.d();
            psc_ez3.f = f2;
            psc_ez3.p(n);
            psc_ez3.d();
        }
    }
    
    public void d(final psc_e0 psc_e0, final psc_e0 psc_e2) throws psc_e1 {
        final psc_ez psc_ez = new psc_ez();
        try {
            this.b(psc_e0, psc_ez, psc_e2);
        }
        finally {
            psc_ez.r();
        }
    }
    
    public boolean e(final psc_e0 psc_e0, final psc_e0 psc_e2) throws psc_e1 {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final int a = this.a(this, psc_ez, null);
        if (this.a(psc_ez) >= 0) {
            psc_ez2.b(0);
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            throw new psc_e1("Cannot invert, operand >= modulus");
        }
        if (this.f == 1) {
            if (this.e[0] == 0) {
                psc_ez2.b(0);
                if (a != 0) {
                    this.a(a, this, psc_ez, null);
                }
                return false;
            }
            if (this.e[0] == 1) {
                psc_ez2.b(1);
                if (a != 0) {
                    this.a(a, this, psc_ez, null);
                }
                return true;
            }
        }
        final psc_ez psc_ez3 = new psc_ez();
        final psc_ez psc_ez4 = new psc_ez();
        psc_ez.b(this, psc_ez3, (psc_e0)psc_ez4);
        if (psc_ez4.f == 1 && psc_ez4.e[0] == 0) {
            psc_ez2.b(0);
            if (a != 0) {
                this.a(a, this, psc_ez, null);
            }
            return false;
        }
        final psc_ez psc_ez5 = new psc_ez();
        psc_ez5.a((psc_e0)this);
        if (a != 0) {
            this.a(a, this, psc_ez, null);
        }
        final psc_ez psc_ez6 = new psc_ez();
        psc_ez6.c(this.f);
        psc_ez6.b(1);
        int n = 1;
        int n2 = -1;
        final psc_ez psc_ez7 = new psc_ez();
        int n3;
        boolean b;
        while (true) {
            n3 = 1;
            b = true;
            if (psc_ez4.f == 1 && psc_ez4.e[0] == 1) {
                break;
            }
            psc_ez5.b(psc_ez4, psc_ez2, (psc_e0)psc_ez7);
            b = false;
            if (psc_ez7.f == 1 && psc_ez7.e[0] == 0) {
                break;
            }
            psc_ez2.c(psc_ez3, (psc_e0)psc_ez5);
            final int n4 = n2;
            n2 = psc_ez6.a(n, psc_ez5, n2, psc_ez2);
            final int n5 = n4;
            b(psc_ez5, psc_ez7);
            n3 = 4;
            b = true;
            if (psc_ez5.f == 1 && psc_ez5.e[0] == 1) {
                break;
            }
            psc_ez4.b(psc_ez5, psc_ez2, (psc_e0)psc_ez7);
            b = false;
            if (psc_ez7.f == 1 && psc_ez7.e[0] == 0) {
                break;
            }
            psc_ez2.c(psc_ez6, (psc_e0)psc_ez4);
            final int n6 = n2;
            n2 = psc_ez3.a(n5, psc_ez4, n2, psc_ez2);
            n = n6;
            b(psc_ez4, psc_ez7);
        }
        if (!b) {
            psc_ez2.h();
        }
        else {
            switch (n3) {
                case 1: {
                    if (n2 == 1) {
                        psc_ez2.a((psc_e0)psc_ez3);
                        break;
                    }
                    psc_ez.b(psc_ez3, (psc_e0)psc_ez2);
                    break;
                }
                case 4: {
                    if (n2 == 1) {
                        psc_ez2.a((psc_e0)psc_ez6);
                        break;
                    }
                    psc_ez.b(psc_ez6, (psc_e0)psc_ez2);
                    break;
                }
                default: {
                    psc_ez2.h();
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
    
    private boolean a(final psc_ez psc_ez, final psc_ez psc_ez2) throws psc_e1 {
        psc_ez psc_ez3 = new psc_ez();
        psc_ez psc_ez4 = new psc_ez();
        psc_ez psc_ez5 = new psc_ez();
        psc_ez psc_ez6 = new psc_ez();
        psc_ez3.c(psc_ez.f);
        psc_ez3.b(1);
        psc_ez4.c(psc_ez.f);
        psc_ez5.a((psc_e0)this);
        psc_ez6.a((psc_e0)psc_ez);
        int i = 1;
        int n = 1;
        int b = psc_ez5.b();
        boolean b2 = true;
        while (true) {
            if (psc_ez5.f == 1) {
                if (psc_ez5.e[0] == 1) {
                    break;
                }
                if (psc_ez5.e[0] == 0) {
                    b2 = false;
                    break;
                }
            }
            if (psc_ez5.b((psc_e0)psc_ez6) < 0) {
                final psc_ez psc_ez7 = psc_ez5;
                psc_ez5 = psc_ez6;
                psc_ez6 = psc_ez7;
                final psc_ez psc_ez8 = psc_ez3;
                psc_ez3 = psc_ez4;
                psc_ez4 = psc_ez8;
                final int n2 = i;
                i = n;
                n = n2;
            }
            if (((psc_ez5.e[0] ^ psc_ez6.e[0]) & 0x3) == 0x0) {
                psc_ez5.d(psc_ez6);
                i = psc_ez3.a(i, psc_ez4, n, psc_ez2);
            }
            else {
                psc_ez5.c(psc_ez6);
                i = psc_ez3.a(i, psc_ez4, -n, psc_ez2);
            }
            final int b3 = psc_ez5.b();
            if (b3 != 0) {
                psc_ez4.n(b3);
                b += b3;
            }
        }
        if (b2) {
            while (i < 0) {
                i = psc_ez3.a(i, psc_ez, -1, psc_ez2);
            }
            int n3 = psc_ez.e[0];
            for (int j = 0; j < 4; ++j) {
                n3 *= 2 - n3 * psc_ez.e[0];
            }
            final int n4 = -n3;
            final int n5 = b >> 5;
            psc_ez2.c(psc_ez.f + 1 + n5);
            psc_ez2.a((psc_e0)psc_ez3);
            for (int k = 0; k < n5; ++k) {
                psc_ez.a(n4 * psc_ez2.e[k], 0, psc_ez.f, psc_ez2, k);
            }
            psc_ez2.q(n5);
            for (int l = psc_ez2.f; l < psc_ez2.e.length; ++l) {
                psc_ez2.e[l] = 0;
            }
            final int n6 = b & 0x1F;
            if (n6 != 0) {
                psc_ez.a(n4 * psc_ez2.e[0] & (1 << n6) - 1, 0, psc_ez.f, psc_ez2, 0);
                psc_ez2.p(n6);
            }
        }
        if (psc_ez2.b((psc_e0)psc_ez) >= 0) {
            psc_ez2.d(psc_ez);
        }
        return b2;
    }
    
    private int b() {
        int n;
        for (n = 0; n < this.f && this.e[n] == 0; ++n) {}
        if (n >= this.f) {
            return 0;
        }
        int n2;
        int n3;
        for (n2 = this.e[n], n3 = n * 8; (n2 & 0x1) != 0x1; n2 >>>= 1, ++n3) {}
        if (n3 != 0) {
            this.p(n3);
        }
        return n3;
    }
    
    public int a(final int n, final psc_ez psc_ez, final int n2, final psc_ez psc_ez2) throws psc_e1 {
        if (this.b((psc_e0)psc_ez) >= 0) {
            if (n == n2) {
                this.d(psc_ez);
            }
            else {
                this.c(psc_ez);
            }
            return n;
        }
        if (n != n2) {
            this.c(psc_ez);
            return -n2;
        }
        b(this, psc_ez2);
        psc_ez.b(psc_ez2, (psc_e0)this);
        return -n2;
    }
    
    private static void b(final psc_ez psc_ez, final psc_ez psc_ez2) {
        final int[] e = psc_ez.e;
        final int f = psc_ez.f;
        psc_ez.e = psc_ez2.e;
        psc_ez.f = psc_ez2.f;
        psc_ez2.e = e;
        psc_ez2.f = f;
    }
    
    protected void a(final psc_ez psc_ez, final psc_ez psc_ez2, final psc_ez psc_ez3, final int n) throws psc_e1 {
        if (n < 0) {
            if (psc_ez.e[0] == 0) {
                psc_ez3.b(1);
            }
            else {
                psc_ez3.a((psc_e0)this);
            }
            return;
        }
        if (psc_ez.a()) {
            this.c(psc_ez2, psc_ez3);
            return;
        }
        this.a(this, psc_ez2, (psc_e0)psc_ez3);
    }
    
    public void c(final psc_ez psc_ez, final psc_ez psc_ez2) throws psc_e1 {
        final psc_ez psc_ez3 = new psc_ez();
        final psc_ez psc_ez4 = new psc_ez();
        try {
            this.c(this, (psc_e0)psc_ez3);
            psc_ez3.b(psc_ez, psc_ez4, (psc_e0)psc_ez2);
            psc_ez2.c(this, (psc_e0)psc_ez3);
            psc_ez3.b(psc_ez, psc_ez4, (psc_e0)psc_ez2);
        }
        finally {
            psc_ez3.r();
            psc_ez4.r();
        }
    }
    
    public void c(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_e1 {
        final psc_ez psc_ez = (psc_ez)psc_e0;
        final psc_ez psc_ez2 = (psc_ez)psc_e2;
        final psc_ez psc_ez3 = (psc_ez)psc_e3;
        psc_ez psc_ez4 = null;
        psc_ez[] array = null;
        final int a = this.a(this, psc_ez, psc_ez2);
        Label_0543: {
            try {
                if (this.a(psc_ez2) >= 0) {
                    psc_ez3.b(0);
                    throw new psc_e1("Cannot compute modExp, the base is larger than the modulus.");
                }
                int i = psc_ez.o() - 2;
                if (i <= 0) {
                    this.a(psc_ez, psc_ez2, psc_ez3, i);
                    break Label_0543;
                }
                final int c = psc_ez2.c();
                int e = this.e(i);
                int n = 1 << e - 1;
                array = new psc_ez[++n];
                array[0] = new psc_ez();
                psc_ez4 = new psc_ez();
                final int f = psc_ez2.f;
                psc_ez3.c(2 * f + 1);
                psc_ez4.c(2 * f + 1);
                psc_ez3.a((psc_e0)this);
                psc_ez3.o(f);
                psc_ez3.b(psc_ez2, psc_ez4, (psc_e0)array[0]);
                psc_ez4.a((psc_e0)array[0]);
                int n2 = 0;
                final int[] array2 = new int[2];
                do {
                    if (e > i + 1) {
                        e = i + 1;
                    }
                    psc_ez.a(e, i, array2);
                    int j = array2[0];
                    i -= j;
                    if (n2 == 1) {
                        psc_ez3.a(psc_ez2, c, psc_ez4);
                        --j;
                        n2 = 0;
                    }
                    while (j > 0) {
                        psc_ez4.a(psc_ez2, c, psc_ez3);
                        --j;
                        n2 = 1;
                        if (j <= 0) {
                            break;
                        }
                        psc_ez3.a(psc_ez2, c, psc_ez4);
                        n2 = 0;
                        --j;
                    }
                    final int n3 = array2[1];
                    if (n3 == 0) {
                        continue;
                    }
                    final int n4 = n3 - 1 >>> 1;
                    if (array[n4] == null) {
                        this.a(psc_ez2, c, n4, array);
                    }
                    if (n2 == 0) {
                        array[n4].a(psc_ez4, psc_ez2, c, psc_ez3);
                    }
                    else {
                        array[n4].a(psc_ez3, psc_ez2, c, psc_ez4);
                    }
                    n2 ^= 0x1;
                } while (i >= 0);
                if (n2 == 1) {
                    b(psc_ez4, psc_ez3);
                }
                array[0].b(1);
                array[0].a(psc_ez4, psc_ez2, c, psc_ez3);
                break Label_0543;
            }
            finally {
                if (psc_ez4 != null) {
                    psc_ez4.r();
                }
                if (array != null) {
                    for (int k = 0; k < array.length; ++k) {
                        if (array[k] != null) {
                            array[k].r();
                        }
                    }
                }
                Label_0678: {
                    if (a != 0) {
                        this.a(a, this, psc_ez, psc_ez2);
                        break Label_0678;
                    }
                    break Label_0678;
                }
                while (true) {}
                // iftrue(Label_0540:, a == 0)
                // iftrue(Label_0518:, array[n6] == null)
                // iftrue(Label_0593:, n5 >= array.length)
                // iftrue(Label_0593:, array == null)
                // iftrue(Label_0555:, psc_ez4 == null)
                // iftrue(Label_0486:, psc_ez4 == null)
                // iftrue(Label_0524:, n6 >= array.length)
                // iftrue(Label_0609:, a == 0)
                // iftrue(Label_0524:, array == null)
            Block_16_Outer:
                while (true) {
                    Block_22: {
                        while (true) {
                            int n5 = 0;
                            int n6 = 0;
                            Label_0486:Block_24_Outer:
                            while (true) {
                                Block_20: {
                                    while (true) {
                                        while (true) {
                                        Block_18:
                                            while (true) {
                                                Label_0494: {
                                                Label_0563_Outer:
                                                    while (true) {
                                                        ++n5;
                                                        while (true) {
                                                            break Label_0563;
                                                            Label_0540: {
                                                                return;
                                                            }
                                                            Label_0524:
                                                            Block_21: {
                                                                Block_19: {
                                                                    break Block_19;
                                                                    break Block_18;
                                                                    ++n6;
                                                                    break Label_0494;
                                                                    break Block_22;
                                                                    break Block_21;
                                                                }
                                                                this.a(a, this, psc_ez, psc_ez2);
                                                                return;
                                                                break Block_20;
                                                                while (true) {
                                                                    psc_ez4.r();
                                                                    break Label_0486;
                                                                    continue Block_24_Outer;
                                                                }
                                                            }
                                                            n5 = 0;
                                                            continue Block_24_Outer;
                                                        }
                                                        array[n5].r();
                                                        continue Label_0563_Outer;
                                                    }
                                                    this.a(a, this, psc_ez, psc_ez2);
                                                    return;
                                                    n6 = 0;
                                                    break Label_0494;
                                                    Label_0609: {
                                                        return;
                                                    }
                                                }
                                                continue Block_24_Outer;
                                            }
                                            array[n6].r();
                                            continue Block_24_Outer;
                                        }
                                        Label_0593: {
                                            continue Block_16_Outer;
                                        }
                                    }
                                }
                                psc_ez4.r();
                                continue Block_16_Outer;
                            }
                            continue;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0587:, array[n5] == null)
        }
    }
    
    private int e(final int n) {
        if (n >= 1018) {
            return 6;
        }
        if (n >= 380) {
            return 5;
        }
        if (n >= 78) {
            return 4;
        }
        if (n >= 17) {
            return 3;
        }
        return 2;
    }
    
    private void a(int n, int n2, final int[] array) {
        int n3 = n2 / 32;
        int n4 = n2 % 32;
        int n5 = 31 - n4;
        final int n6 = this.e[n3];
        --n3;
        int n7 = n6 << n5;
        ++n5;
        int n8 = 0;
        while ((n7 & Integer.MIN_VALUE) == 0x0) {
            ++n8;
            --n2;
            --n4;
            if (n2 < 0) {
                array[0] = n8;
                array[1] = n7 >> 31;
                return;
            }
            if (n4 < 0) {
                n7 = this.e[n3];
                --n3;
                n4 = 31;
            }
            else {
                n7 <<= 1;
            }
        }
        if (n4 + 1 < n) {
            if (n3 >= 0) {
                n7 |= this.e[n3] >>> n4 + 1;
            }
            else {
                n = n4 + 1;
            }
        }
        int n9;
        for (n9 = n7 >>> 32 - n; (n9 & 0x1) == 0x0; n9 >>>= 1, --n) {}
        array[0] = n8 + n;
        array[1] = n9;
    }
    
    private void a(final psc_ez psc_ez, final int n, final int n2, final psc_ez[] array) throws psc_e1 {
        final int n3 = array.length - 1;
        if (array[n3] == null) {
            array[n3] = new psc_ez();
            array[0].a(psc_ez, n, array[n3]);
        }
        if (array[n2 - 1] == null) {
            this.a(psc_ez, n, n2 - 1, array);
        }
        array[n2] = new psc_ez();
        array[n2 - 1].a(array[n3], psc_ez, n, array[n2]);
    }
    
    public void a(final int n, final psc_e0[] array, final psc_e0 psc_e0) throws psc_e1 {
        final int length = array.length;
        if (length != n * 3) {
            throw new psc_e1("Invalid CRT data.");
        }
        final psc_ez[] array2 = new psc_ez[length];
        final psc_ez[] array3 = new psc_ez[n];
        final psc_ez psc_ez = new psc_ez();
        final psc_ez psc_ez2 = new psc_ez();
        final psc_ez psc_ez3 = new psc_ez();
        final boolean[] array4 = new boolean[length + 1];
        Label_0513: {
            try {
                for (int i = 0; i < length; ++i) {
                    int n2;
                    if ((n2 = i) == 1) {
                        n2 = 2;
                    }
                    else if (i == 2) {
                        n2 = 1;
                    }
                    else if (i == n + 1) {
                        n2 = n + 2;
                    }
                    else if (i == n + 2) {
                        n2 = n + 1;
                    }
                    array2[n2] = (psc_ez)array[i];
                    if ((array2[n2].h & 0x1) == 0x1) {
                        array4[length] = (array4[n2] = true);
                        array2[n2].q();
                    }
                }
                for (int j = 0; j < n; ++j) {
                    if (j == 0) {
                        array3[j] = (psc_ez)psc_e0;
                    }
                    else {
                        array3[j] = new psc_ez();
                    }
                    this.d(array2[j + 1], (psc_e0)psc_ez);
                    psc_ez.c(array2[j + n + 1], array2[j + 1], array3[j]);
                }
                psc_ez3.a((psc_e0)array2[1]);
                int n3 = 1;
                while (true) {
                    if (array3[n3].a(1, array3[0], 1, psc_ez) < 0) {
                        if (array3[n3].b((psc_e0)array2[n3 + 1]) <= 0) {
                            array3[n3].a(1, array2[n3 + 1], 1, psc_ez);
                        }
                        else {
                            array3[n3].b(array2[n3 + 1], psc_ez, (psc_e0)psc_ez2);
                            array2[n3 + 1].b(psc_ez2, (psc_e0)array3[n3]);
                        }
                    }
                    array3[n3].c(array2[n3 + 2 * n], (psc_e0)psc_ez);
                    psc_ez.b(array2[n3 + 1], psc_ez2, (psc_e0)array3[n3]);
                    psc_ez3.c(array3[n3], (psc_e0)psc_ez);
                    array3[0].c(psc_ez);
                    if (n3 == n - 1) {
                        break;
                    }
                    psc_ez3.c(array2[n3 + 1], (psc_e0)psc_ez);
                    psc_ez3.a((psc_e0)psc_ez);
                    ++n3;
                }
                break Label_0513;
            }
            catch (ClassCastException ex) {
                throw new psc_e1("Invalid JCMPInt input.");
            }
            finally {
                if (array4[length]) {
                    for (int k = 0; k < length; ++k) {
                        if (array4[k]) {
                            array2[k].p();
                        }
                    }
                }
                for (int l = 1; l < n; ++l) {
                    if (array3[l] != null) {
                        array3[l].r();
                    }
                }
                psc_ez.r();
                psc_ez2.r();
                psc_ez3.r();
                while (true) {}
                // iftrue(Label_0557:, array4[length] != true)
                // iftrue(Label_0551:, array4[n5] != true)
                int n4 = 0;
                int n5 = 0;
                Block_21_Outer:Block_18_Outer:
                while (true) {
                    Label_0582: {
                        while (true) {
                            Label_0527: {
                                while (true) {
                                Label_0551:
                                    while (true) {
                                        Block_19: {
                                            Block_17: {
                                                break Block_17;
                                                Label_0557: {
                                                    n4 = 1;
                                                }
                                                break Label_0560;
                                                array3[n4].r();
                                                break Label_0582;
                                                Label_0588:
                                                psc_ez.r();
                                                psc_ez2.r();
                                                psc_ez3.r();
                                                return;
                                                break Block_19;
                                            }
                                            n5 = 0;
                                            break Label_0527;
                                        }
                                        array2[n5].p();
                                        break Label_0551;
                                        continue Block_18_Outer;
                                    }
                                    ++n5;
                                    break Label_0527;
                                    continue;
                                }
                            }
                            continue;
                        }
                    }
                    ++n4;
                    continue Block_21_Outer;
                }
            }
            // iftrue(Label_0582:, array3[n4] == null)
            // iftrue(Label_0588:, n4 >= n)
            // iftrue(Label_0557:, n5 >= length)
        }
    }
    
    private int c() throws psc_e1 {
        final long n = (long)this.e[0] & 0xFFFFFFFFL;
        if ((n & 0x1L) == 0x0L) {
            throw new psc_e1("Cannot find the Montgomery coefficient of an even number.");
        }
        long n2 = 1L;
        long n3 = 2L;
        long n4 = 3L;
        if ((n4 & n) >= n3) {
            n2 += n3;
        }
        for (int i = 3; i <= 32; ++i) {
            n3 <<= 1;
            n4 |= n3;
            if ((n * n2 & n4) > n3) {
                n2 += n3;
            }
        }
        return (int)(-n2 & 0xFFFFFFFFL);
    }
    
    private void a(final psc_ez psc_ez, final psc_ez psc_ez2, final int n, final psc_ez psc_ez3) throws psc_e1 {
        final int f = psc_ez2.f;
        psc_ez3.c(f * 2 + 1);
        final int[] e = this.e;
        final int[] e2 = psc_ez.e;
        final int[] e3 = psc_ez2.e;
        final int[] e4 = psc_ez3.e;
        final int f2 = psc_ez.f;
        final int f3 = this.f;
        final long n2 = (long)e[0] & 0xFFFFFFFFL;
        long n3 = 0L;
        int i;
        for (i = 0; i < f2; ++i) {
            final long n4 = n2 * ((long)e2[i] & 0xFFFFFFFFL) + n3;
            e4[i] = (int)n4;
            n3 = n4 >>> 32;
        }
        e4[i] = (int)n3;
        final long n5 = (long)(e4[0] * n) & 0xFFFFFFFFL;
        int n6 = 0;
        long n7 = 0L;
        for (int j = 0; j < f; ++j, ++n6) {
            final long n8 = n5 * ((long)e3[j] & 0xFFFFFFFFL) + ((long)e4[n6] & 0xFFFFFFFFL) + n7;
            e4[n6] = (int)n8;
            n7 = n8 >>> 32;
        }
        final long n9 = n7 + ((long)e4[n6] & 0xFFFFFFFFL);
        e4[n6] = (int)n9;
        if (n9 >>> 32 != 0L) {
            final int[] array = e4;
            final int n10 = n6 + 1;
            ++array[n10];
            if (e4[n6 + 1] == 0) {
                psc_ez3.f(psc_ez3.f = n6 + 2);
            }
        }
        int k;
        for (k = 1; k < f3; ++k) {
            final long n11 = (long)e[k] & 0xFFFFFFFFL;
            int n12 = k;
            long n13 = 0L;
            for (int l = 0; l < f2; ++l, ++n12) {
                final long n14 = n11 * ((long)e2[l] & 0xFFFFFFFFL) + ((long)e4[n12] & 0xFFFFFFFFL) + n13;
                e4[n12] = (int)n14;
                n13 = n14 >>> 32;
            }
            final long n15 = n13 + ((long)e4[n12] & 0xFFFFFFFFL);
            e4[n12] = (int)n15;
            if (n15 >>> 32 != 0L) {
                final int[] array2 = e4;
                final int n16 = n12 + 1;
                ++array2[n16];
                if (e4[n12 + 1] == 0) {
                    psc_ez3.f(psc_ez3.f = n12 + 2);
                }
            }
            final long n17 = (long)(e4[k] * n) & 0xFFFFFFFFL;
            int n18 = k;
            long n19 = 0L;
            for (int n20 = 0; n20 < f; ++n20, ++n18) {
                final long n21 = n17 * ((long)e3[n20] & 0xFFFFFFFFL) + ((long)e4[n18] & 0xFFFFFFFFL) + n19;
                e4[n18] = (int)n21;
                n19 = n21 >>> 32;
            }
            final long n22 = n19 + ((long)e4[n18] & 0xFFFFFFFFL);
            e4[n18] = (int)n22;
            if (n22 >>> 32 != 0L) {
                final int[] array3 = e4;
                final int n23 = n18 + 1;
                ++array3[n23];
                if (e4[n18 + 1] == 0) {
                    psc_ez3.f(psc_ez3.f = n18 + 2);
                }
            }
        }
        while (k < f) {
            final long n24 = (long)(e4[k] * n) & 0xFFFFFFFFL;
            int n25 = k;
            long n26 = 0L;
            for (int n27 = 0; n27 < f; ++n27, ++n25) {
                final long n28 = n24 * ((long)e3[n27] & 0xFFFFFFFFL) + ((long)e4[n25] & 0xFFFFFFFFL) + n26;
                e4[n25] = (int)n28;
                n26 = n28 >>> 32;
            }
            final long n29 = n26 + ((long)e4[n25] & 0xFFFFFFFFL);
            e4[n25] = (int)n29;
            if (n29 >>> 32 != 0L) {
                final int[] array4 = e4;
                final int n30 = n25 + 1;
                ++array4[n30];
                if (e4[n25 + 1] == 0) {
                    psc_ez3.f(psc_ez3.f = n25 + 2);
                }
            }
            ++k;
        }
        psc_ez3.f = f * 2 + 1;
        psc_ez3.d();
        psc_ez3.q(f);
        psc_ez3.d();
        if (psc_ez3.b((psc_e0)psc_ez2) >= 0) {
            psc_ez3.d(psc_ez2);
        }
    }
    
    private void a(final psc_ez psc_ez, final int n, final psc_ez psc_ez2) throws psc_e1 {
        final int f = psc_ez.f;
        psc_ez2.c(f * 2 + 1);
        final int[] e = this.e;
        final int[] e2 = psc_ez.e;
        final int[] e3 = psc_ez2.e;
        final int f2 = this.f;
        final long n2 = (long)e[0] & 0xFFFFFFFFL;
        long n3 = 0L;
        int i;
        for (i = 1; i < f2; ++i) {
            final long n4 = n2 * ((long)e[i] & 0xFFFFFFFFL) + n3;
            e3[i] = (int)n4;
            n3 = n4 >>> 32;
        }
        e3[i] = (int)n3;
        for (int j = 1; j < f2 - 1; ++j) {
            final long n5 = (long)e[j] & 0xFFFFFFFFL;
            long n6 = 0L;
            int n7 = 2 * j + 1;
            for (int k = j + 1; k < f2; ++k, ++n7) {
                final long n8 = n5 * ((long)e[k] & 0xFFFFFFFFL) + ((long)e3[n7] & 0xFFFFFFFFL) + n6;
                e3[n7] = (int)n8;
                n6 = n8 >>> 32;
            }
            final long n9 = n6 + ((long)e3[n7] & 0xFFFFFFFFL);
            e3[n7] = (int)n9;
            if (n9 >>> 32 != 0L) {
                final int[] array = e3;
                final int n10 = n7 + 1;
                ++array[n10];
                if (e3[n7 + 1] == 0) {
                    psc_ez2.f(psc_ez2.f = n7 + 2);
                }
            }
        }
        psc_ez2.f = f * 2 + 1;
        psc_ez2.d();
        psc_ez2.n(1);
        psc_ez2.d();
        this.b(psc_ez2);
        for (int l = 0; l < f; ++l) {
            final long n11 = (long)(e3[l] * n) & 0xFFFFFFFFL;
            int n12 = l;
            long n13 = 0L;
            for (int n14 = 0; n14 < f; ++n14, ++n12) {
                final long n15 = n11 * ((long)e2[n14] & 0xFFFFFFFFL) + ((long)e3[n12] & 0xFFFFFFFFL) + n13;
                e3[n12] = (int)n15;
                n13 = n15 >>> 32;
            }
            final long n16 = n13 + ((long)e3[n12] & 0xFFFFFFFFL);
            e3[n12] = (int)n16;
            if (n16 >>> 32 != 0L) {
                final int[] array2 = e3;
                final int n17 = n12 + 1;
                ++array2[n17];
                if (e3[n12 + 1] == 0) {
                    psc_ez2.f(psc_ez2.f = n12 + 2);
                }
            }
        }
        psc_ez2.f = f * 2 + 1;
        psc_ez2.d();
        psc_ez2.q(f);
        psc_ez2.d();
        if (psc_ez2.b((psc_e0)psc_ez) >= 0) {
            psc_ez2.d(psc_ez);
        }
    }
    
    private void b(final psc_ez psc_ez) {
        final int f = this.f;
        long n = 0L;
        final int[] e = psc_ez.e;
        final int[] e2 = this.e;
        int i;
        int f2;
        for (i = 0, f2 = 0; i < f; ++i, f2 += 2) {
            final long n2 = (long)e2[i] & 0xFFFFFFFFL;
            final long n3 = n2 * n2;
            final long n4 = n3 >>> 32;
            final long n5 = n + ((n3 & 0xFFFFFFFFL) + ((long)e[f2] & 0xFFFFFFFFL));
            e[f2] = (int)n5;
            final long n6 = (n5 >>> 32) + (n4 + ((long)e[f2 + 1] & 0xFFFFFFFFL));
            e[f2 + 1] = (int)n6;
            n = n6 >>> 32;
        }
        if (f2 > psc_ez.f) {
            psc_ez.f = f2;
        }
        if (n != 0L) {
            psc_ez.f(f2);
        }
    }
    
    public void f(final psc_e0 psc_e0, final psc_e0 psc_e2) {
        d(this, psc_e0, psc_e2);
    }
    
    public static void d(final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) {
        psc_e0 psc_e4 = null;
        psc_e0 psc_e5 = null;
        psc_e0 psc_e6 = null;
        psc_ez psc_ez = null;
        Label_0157: {
            try {
                psc_e5 = new psc_ez();
                psc_e6 = new psc_ez();
                psc_e4 = new psc_ez();
                psc_ez = new psc_ez();
                psc_e0.c(psc_e2, psc_e4);
                b((psc_ez)psc_e0, (psc_ez)psc_e2, psc_ez);
                psc_e4.b(psc_ez, psc_e3, psc_e6);
                break Label_0157;
            }
            catch (psc_e1 psc_e7) {
                psc_e7.printStackTrace();
                break Label_0157;
            }
            finally {
                if (psc_e4 != null) {
                    psc_e4.r();
                }
                if (psc_e5 != null) {
                    psc_e5.r();
                }
                if (psc_e4 != null) {
                    psc_e6.r();
                }
                Label_0250: {
                    if (psc_ez != null) {
                        psc_ez.r();
                        break Label_0250;
                    }
                    break Label_0250;
                }
                while (true) {}
                return;
                // iftrue(Label_0121:, psc_e4 == null)
                // iftrue(Label_0202:, psc_ez == null)
                // iftrue(Label_0192:, psc_e4 == null)
                // iftrue(Label_0169:, psc_e4 == null)
                // iftrue(Label_0154:, psc_ez == null)
                // iftrue(Label_0133:, psc_e5 == null)
                // iftrue(Label_0181:, psc_e5 == null)
                // iftrue(Label_0144:, psc_e4 == null)
                Block_8: {
                    while (true) {
                        Block_9_Outer:Label_0192_Outer:Label_0181_Outer:
                        while (true) {
                            while (true) {
                                Label_0169: {
                                    Label_0121: {
                                        Label_0144: {
                                            while (true) {
                                            Block_12_Outer:
                                                while (true) {
                                                    while (true) {
                                                        while (true) {
                                                            psc_e4.r();
                                                            break Label_0121;
                                                            Label_0202: {
                                                                return;
                                                            }
                                                            psc_e6.r();
                                                            break Label_0144;
                                                            psc_e4.r();
                                                            break Label_0169;
                                                            psc_ez.r();
                                                            return;
                                                            continue Block_9_Outer;
                                                        }
                                                        continue Label_0192_Outer;
                                                    }
                                                    break Block_12_Outer;
                                                    continue Block_12_Outer;
                                                }
                                                psc_e6.r();
                                                continue Label_0181_Outer;
                                            }
                                        }
                                        break Block_8;
                                    }
                                    break Block_9_Outer;
                                }
                                psc_e5.r();
                                continue;
                            }
                            continue Label_0181_Outer;
                        }
                        psc_e5.r();
                        continue;
                    }
                    Label_0154: {
                        return;
                    }
                }
                psc_ez.r();
            }
        }
    }
    
    public void d(final psc_ez psc_ez, final psc_ez psc_ez2) throws psc_e1 {
        b(this, psc_ez, psc_ez2);
    }
    
    public static void b(final psc_ez psc_ez, final psc_ez psc_ez2, final psc_ez psc_ez3) throws psc_e1 {
        psc_ez psc_ez4 = null;
        psc_ez psc_ez5 = null;
        Label_0114: {
            try {
                psc_ez4 = new psc_ez();
                psc_ez5 = new psc_ez();
                if (psc_ez.b((psc_e0)psc_ez2) >= 0) {
                    psc_ez4.a((psc_e0)psc_ez);
                    psc_ez5.a((psc_e0)psc_ez2);
                }
                else {
                    psc_ez4.a((psc_e0)psc_ez2);
                    psc_ez5.a((psc_e0)psc_ez);
                }
                psc_ez4.d(psc_ez5, (psc_e0)psc_ez3);
                while (!psc_ez3.k()) {
                    psc_ez4.a((psc_e0)psc_ez5);
                    psc_ez5.a((psc_e0)psc_ez3);
                    psc_ez4.d(psc_ez5, (psc_e0)psc_ez3);
                }
                psc_ez3.a((psc_e0)psc_ez5);
                break Label_0114;
            }
            finally {
                if (psc_ez4 != null) {
                    psc_ez4.r();
                }
                Label_0157: {
                    if (psc_ez5 != null) {
                        psc_ez5.r();
                        break Label_0157;
                    }
                    break Label_0157;
                }
                while (true) {}
                // iftrue(Label_0134:, psc_ez5 == null)
                // iftrue(Label_0124:, psc_ez4 == null)
                while (true) {
                    psc_ez4.r();
                    Label_0124: {
                        psc_ez5.r();
                    }
                    return;
                    continue;
                }
                Label_0134:;
            }
        }
    }
    
    public boolean a(final psc_e0 psc_e0, final SecureRandom secureRandom, final boolean b) throws psc_e1 {
        if (this.f == 0) {
            throw new psc_e1("Cannot build a prime, the JCMPInt is not set.");
        }
        final int o = this.o();
        if (o < 101 || o > 2048) {
            throw new psc_e1("Cannot build a prime, the length is inappropriate.");
        }
        final int[] e = this.e;
        final int n = 0;
        e[n] |= 0x1;
        return psc_le.a(this, null, null, psc_e0, 0, secureRandom);
    }
    
    public boolean b(final psc_e0 psc_e0, final SecureRandom secureRandom, final boolean b) throws psc_e1 {
        if (this.f == 0) {
            throw new psc_e1("Cannot build a prime, the JCMPInt is not set.");
        }
        final int o = this.o();
        if (o < 101 || o > 2048) {
            throw new psc_e1("Cannot build a prime, the length is inappropriate.");
        }
        final int[] e = this.e;
        final int n = 0;
        e[n] |= 0x1;
        if (psc_e0 != null) {
            return psc_le.a(this, psc_e0, secureRandom, b);
        }
        return psc_le.a(this, null, null, psc_e0, 0, secureRandom);
    }
    
    public boolean a(final int n, final psc_e0 psc_e0, final int n2, final boolean b, final SecureRandom secureRandom) throws psc_e1 {
        if (n <= 9) {
            throw new psc_e1("Cannot generate a prime of length <= 9");
        }
        psc_ez psc_ez = null;
        psc_ez psc_ez2 = null;
        final byte[] bytes = new byte[(n + 7) / 8];
        byte[] bytes2 = null;
        byte[] bytes3 = null;
        final byte b2 = 16;
        final byte b3 = 31;
        if (b) {
            bytes2 = new byte[13];
            bytes3 = new byte[13];
        }
        int n3 = 128;
        int n4 = 0;
        int n5 = n % 8;
        if (!b) {
            final int n6 = 192;
            if (n5 == 1) {
                n4 = 128;
            }
            if (n5 != 0) {
                n5 = 8 - n5;
            }
            n3 = n6 >>> n5;
        }
        final int n7 = 255 >>> n5;
        if (b) {
            secureRandom.nextBytes(bytes2);
            secureRandom.nextBytes(bytes3);
            bytes2[0] &= b3;
            bytes2[0] |= b2;
            bytes2[12] |= 0x1;
            bytes3[0] &= b3;
            bytes3[0] |= b2;
            bytes3[12] |= 0x1;
        }
        secureRandom.nextBytes(bytes);
        bytes[0] &= (byte)n7;
        bytes[0] |= (byte)n3;
        bytes[1] |= (byte)n4;
        final byte[] array = bytes;
        final int n8 = bytes.length - 1;
        array[n8] |= 0x1;
        if (b) {
            if (!a(bytes)) {
                return false;
            }
            psc_ez = new psc_ez();
            psc_ez2 = new psc_ez();
            psc_ez.a(bytes2, 0, bytes2.length);
            psc_ez2.a(bytes3, 0, bytes3.length);
            psc_au.c(bytes2);
            psc_au.c(bytes3);
            if (!psc_ez.a(psc_e0, secureRandom, b) || !psc_ez2.a(psc_e0, secureRandom, b)) {
                psc_ez.r();
                psc_ez2.r();
                return false;
            }
        }
        this.a(bytes, 0, bytes.length);
        psc_au.c(bytes);
        final boolean a = psc_le.a(this, psc_ez, psc_ez2, psc_e0, n2, secureRandom);
        if (psc_ez != null) {
            psc_ez.r();
        }
        if (psc_ez2 != null) {
            psc_ez2.r();
        }
        return a;
    }
    
    public boolean a(final int n, final psc_e0 psc_e0, final int n2, final boolean b, final SecureRandom secureRandom, final byte[] array, final byte[][] array2) throws psc_e1 {
        if (n <= 9) {
            throw new psc_e1("Cannot generate a prime of length <= 9");
        }
        final int n3 = (n + 7) / 8;
        byte[] bytes = null;
        byte[] bytes2 = null;
        final byte b2 = 16;
        final byte b3 = 31;
        if (b) {
            bytes = new byte[13];
            bytes2 = new byte[13];
        }
        int n4 = 128;
        int n5 = 0;
        int n6 = n % 8;
        if (!b) {
            final int n7 = 192;
            if (n6 == 1) {
                n5 = 128;
            }
            if (n6 != 0) {
                n6 = 8 - n6;
            }
            n4 = n7 >>> n6;
        }
        final int n8 = 255 >>> n6;
        byte[] bytes3;
        if (array != null) {
            bytes3 = array.clone();
        }
        else {
            bytes3 = new byte[n3];
            secureRandom.nextBytes(bytes3);
            if (n2 == 3 && array2 != null) {
                array2[0] = bytes3.clone();
            }
            bytes3[0] &= (byte)n8;
            bytes3[0] |= (byte)n4;
            bytes3[1] |= (byte)n5;
            final byte[] array3 = bytes3;
            final int n9 = bytes3.length - 1;
            array3[n9] |= 0x1;
            if (b) {
                System.arraycopy(bytes3, 0, psc_ez.b, 0, 4);
                final byte[] b4 = psc_ez.b;
                final int n10 = 0;
                b4[n10] |= 0xFFFFFF80;
                if (!a(psc_ez.b)) {
                    final byte[] b5 = psc_ez.b;
                    final int n11 = 0;
                    b5[n11] |= 0x4;
                }
                final int n12 = (n - 25) / 8;
                System.arraycopy(psc_ez.b, 0, bytes3, 0, 4);
                if (secureRandom instanceof psc_av) {
                    ((psc_av)secureRandom).b(bytes3, 4, n12);
                }
                else {
                    final byte[] bytes4 = new byte[n12];
                    secureRandom.nextBytes(bytes4);
                    System.arraycopy(bytes4, 0, bytes3, 4, n12);
                }
            }
        }
        if (b) {
            secureRandom.nextBytes(bytes);
            secureRandom.nextBytes(bytes2);
            bytes[0] &= b3;
            bytes[0] |= b2;
            bytes[12] |= 0x1;
            bytes2[0] &= b3;
            bytes2[0] |= b2;
            bytes2[12] |= 0x1;
        }
        return this.a(n, psc_e0, n2, b, secureRandom, bytes, bytes2, bytes3, new byte[][] { null, null, null });
    }
    
    public boolean a(final int n, final psc_e0 psc_e0, final int n2, final boolean b, final SecureRandom secureRandom, final byte[] array, final byte[] array2, final byte[] array3, final byte[][] array4) throws psc_e1 {
        if (n <= 9) {
            throw new psc_e1("Cannot generate a prime of length <= 9");
        }
        final int n3 = (n + 7) / 8;
        final int[] array5 = { 181, 4, 243, 51 };
        for (int n4 = 0; n4 < 4 && (array3[n4] & 0xFF) <= array5[n4]; ++n4) {
            if ((array3[n4] & 0xFF) < array5[n4]) {
                return false;
            }
        }
        final psc_ez psc_ez = new psc_ez();
        final psc_ez psc_ez2 = new psc_ez();
        psc_ez.a(array, 0, array.length);
        psc_ez2.a(array2, 0, array2.length);
        Label_0343: {
            try {
                if (!psc_ez.b(psc_e0, secureRandom, b)) {
                    final boolean b2 = false;
                    break Label_0343;
                }
                if (!psc_ez2.b(psc_e0, secureRandom, b)) {
                    final boolean b3 = false;
                    break Label_0343;
                }
                array4[0] = psc_ez.n();
                array4[1] = psc_ez2.n();
                this.a(array3, 0, n3);
                final boolean b4 = psc_le.b(this, psc_ez, psc_ez2, psc_e0, n2, secureRandom, array4[2]);
                array4[2] = this.n();
                final boolean b5 = b4;
                break Label_0343;
            }
            finally {
                if (psc_ez != null) {
                    psc_ez.r();
                }
                Label_0390: {
                    if (psc_ez2 != null) {
                        psc_ez2.r();
                        break Label_0390;
                    }
                    break Label_0390;
                }
                while (true) {}
                Label_0315: {
                    return;
                }
                // iftrue(Label_0355:, psc_ez == null)
                // iftrue(Label_0330:, psc_ez == null)
                // iftrue(Label_0305:, psc_ez == null)
                // iftrue(Label_0315:, psc_ez2 == null)
                final boolean b2;
                final boolean b3;
                final boolean b5;
                Label_0305_Outer:Block_13_Outer:
                while (true) {
                    Label_0355: {
                        while (true) {
                            Label_0330: {
                                while (true) {
                                    Block_10: {
                                        Block_14: {
                                            break Block_14;
                                            while (true) {
                                                psc_ez2.r();
                                                return b2;
                                                Block_12: {
                                                    break Block_12;
                                                    psc_ez2.r();
                                                    return b5;
                                                    break Block_10;
                                                }
                                                psc_ez.r();
                                                break Label_0330;
                                                continue Label_0305_Outer;
                                            }
                                        }
                                        psc_ez.r();
                                        break Label_0355;
                                        Label_0365:
                                        return b5;
                                        psc_ez2.r();
                                        return b3;
                                    }
                                    psc_ez.r();
                                    continue Block_13_Outer;
                                }
                            }
                            continue;
                        }
                        Label_0340:
                        return b3;
                    }
                    continue;
                }
            }
            // iftrue(Label_0340:, psc_ez2 == null)
            // iftrue(Label_0365:, psc_ez2 == null)
        }
    }
    
    static boolean a(final byte[] array) {
        for (int i = 0; i < 4; ++i) {
            if ((array[i] & 0xFF) >= psc_ez.a[i]) {
                return true;
            }
        }
        return false;
    }
    
    public void n(final int n) {
        if (n <= 0) {
            return;
        }
        final int n2 = n >>> 5;
        final int n3 = n & 0x1F;
        if (n2 != 0) {
            this.o(n2);
        }
        if (n3 == 0) {
            return;
        }
        final int n4 = this.f - 2;
        final int n5 = 32 - n3;
        final int n6 = this.e[this.f - 1] >>> n5;
        final int[] e = this.e;
        final int n7 = this.f - 1;
        e[n7] <<= n3;
        if (n6 != 0) {
            this.a(new int[] { n6 }, 0, 1);
        }
        int n8 = 0;
        int i;
        for (i = 0; i <= n4; ++i) {
            final int n9 = this.e[i] << n3 | n8;
            n8 = this.e[i] >>> n5;
            this.e[i] = n9;
        }
        final int[] e2 = this.e;
        final int n10 = i;
        e2[n10] |= n8;
        this.d();
    }
    
    public void o(final int n) {
        if (n <= 0) {
            return;
        }
        if (this.e.length < this.f + n) {
            final int[] e = new int[this.e.length + n];
            System.arraycopy(this.e, 0, e, n, this.f);
            this.e = e;
            this.f += n;
            this.d();
            return;
        }
        for (int i = this.f - 1; i >= 0; --i) {
            this.e[i + n] = this.e[i];
        }
        for (int j = 0; j < n; ++j) {
            this.e[j] = 0;
        }
        this.f += n;
        this.d();
    }
    
    public void p(final int n) {
        if (n <= 0) {
            return;
        }
        final int n2 = n >>> 5;
        final int n3 = n & 0x1F;
        if (n2 != 0) {
            this.q(n2);
        }
        if (n3 == 0) {
            return;
        }
        final int n4 = 32 - n3;
        int n5 = 0;
        for (int i = this.f - 1; i >= 0; --i) {
            final int n6 = this.e[i] << n4;
            this.e[i] >>>= n3;
            final int[] e = this.e;
            final int n7 = i;
            e[n7] |= n5;
            n5 = n6;
        }
        this.d();
    }
    
    public void q(final int n) {
        if (n <= 0) {
            return;
        }
        if (n >= this.f) {
            this.e[0] = 0;
            this.f = 1;
            return;
        }
        for (int i = 0; i < this.f - n; ++i) {
            this.e[i] = this.e[i + n];
        }
        this.f -= n;
        this.d();
    }
    
    private void f(int i) {
        while (i < this.f) {
            final long n = ((long)this.e[i] & 0xFFFFFFFFL) + 1L;
            this.e[i] = (int)n;
            if (n >>> 32 == 0L) {
                return;
            }
            ++i;
        }
        this.a(new int[] { 1 }, 0, 1);
    }
    
    private void g(int i) throws psc_e1 {
        if (i >= this.f) {
            throw new psc_e1("JCMPInt operation yields negative result.");
        }
        while (i < this.f) {
            final long n = ((long)this.e[i] & 0xFFFFFFFFL) - 1L;
            this.e[i] = (int)n;
            if (n + 1L >>> 32 == 0L) {
                if (this.e[i] == 0 && i == this.f - 1) {
                    --this.f;
                }
                return;
            }
            ++i;
        }
        throw new psc_e1("JCMPInt operation yields negative result.");
    }
    
    private void d() {
        if (this.e == null) {
            this.f = 0;
            return;
        }
        for (int n = this.f - 1; n > 0 && this.e[n] == 0; --n) {
            --this.f;
        }
    }
    
    private void a(final int[] array, final int n, final int n2) {
        if (this.e == null) {
            this.e = new int[n2];
        }
        if (this.e.length < this.f + n2) {
            final int[] e = new int[this.f + n2];
            final int f = this.f;
            System.arraycopy(this.e, 0, e, 0, this.f);
            this.r();
            this.e = e;
            this.f = f;
        }
        for (int i = 0; i < n2; ++i) {
            this.e[this.f + i] = array[i + n];
        }
        this.f += n2;
        this.d();
    }
    
    private int a(final psc_ez psc_ez, final psc_ez psc_ez2, final psc_ez psc_ez3) {
        int n = 0;
        if (psc_ez.f == 0) {
            psc_ez.b(0);
        }
        if ((psc_ez.h & 0x1) != 0x0) {
            n |= 0x1;
            psc_ez.q();
        }
        if (psc_ez2.f == 0) {
            psc_ez2.b(0);
        }
        if ((psc_ez2.h & 0x1) != 0x0) {
            n |= 0x2;
            psc_ez2.q();
        }
        if (psc_ez3 != null) {
            if (psc_ez3.f == 0) {
                psc_ez3.b(0);
            }
            if ((psc_ez3.h & 0x1) != 0x0) {
                n |= 0x2;
                psc_ez3.q();
            }
        }
        return n;
    }
    
    private void a(final int n, final psc_ez psc_ez, final psc_ez psc_ez2, final psc_ez psc_ez3) {
        if ((n & 0x1) == 0x1) {
            psc_ez.p();
        }
        if ((n & 0x2) == 0x1) {
            psc_ez2.p();
        }
        if ((n & 0x4) == 0x1) {
            psc_ez3.p();
        }
    }
    
    public void p() {
        if ((this.h & 0x2) != 0x0) {
            return;
        }
        if (this.g != null) {
            this.g.c();
            this.h = 1;
            return;
        }
        if (this.f == 0) {
            this.b(0);
        }
        this.g = psc_au.b(this.e);
        if (!this.g.a()) {
            this.h = 2;
        }
        else {
            this.h = 1;
        }
    }
    
    public void q() {
        if ((this.h & 0x1) != 0x0) {
            this.g.d();
            this.h = 4;
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.e();
        objectOutputStream.defaultWriteObject();
        this.f();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.g();
    }
    
    private void e() {
        if ((this.h & 0x1) == 0x0) {
            return;
        }
        this.q();
        this.h = 1;
    }
    
    private void f() {
        if (this.h == 1) {
            this.h = 4;
            this.p();
        }
    }
    
    private void g() {
        if (this.h == 1) {
            this.h = 0;
            this.p();
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ez psc_ez = new psc_ez();
        psc_ez.a((psc_e0)this);
        if (this.h == 1) {
            psc_ez.p();
        }
        return psc_ez;
    }
    
    private void h() {
        this.f = 0;
        if (this.g == null) {
            return;
        }
        this.g.e();
        this.h = 4;
    }
    
    public void r() {
        psc_au.b(this.e, this.g);
        this.g = null;
        this.e = null;
        this.f = 0;
        this.h = 0;
    }
    
    protected void finalize() throws Throwable {
        try {
            this.r();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        a = new int[] { 181, 4, 243, 51 };
        psc_ez.b = new byte[4];
        c = a(0);
        d = a(1);
    }
}
