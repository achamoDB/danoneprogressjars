import java.util.Vector;
import java.security.SecureRandom;
import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_m9 extends psc_na implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    private byte[] c;
    private int d;
    private int e;
    private psc_u f;
    private psc_nb g;
    private boolean h;
    private Date i;
    private Date j;
    private psc_bg k;
    protected int l;
    private psc_n m;
    protected int n;
    private psc_n o;
    
    public psc_m9() {
        this.e = 0;
        this.h = false;
    }
    
    public psc_m9(final psc_ah psc_ah) {
        this.e = 0;
        this.h = false;
        this.a(psc_ah);
    }
    
    public psc_m9(final byte[] array, final int n, final int n2) throws psc_g {
        this(array, n, n2, null);
    }
    
    public psc_m9(final byte[] array, final int n, final int n2, final psc_ah psc_ah) throws psc_g {
        this.e = 0;
        this.h = false;
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.a(array, n, n2);
        this.a(psc_ah);
    }
    
    public static int a(final byte[] array, final int n) throws psc_g {
        try {
            if (array == null) {
                throw new psc_g("Encoding is null.");
            }
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER encoding.");
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_g {
        this.k();
        final psc_i[] c = c(array, n, n2);
        this.b(c[1].b, c[1].c);
        super.g = new byte[c[3].d];
        System.arraycopy(c[3].b, c[3].c, super.g, 0, c[3].d);
        if (!this.b(c[2].b, c[2].c, c[2].d)) {
            throw new psc_g("Unknown or invalid signature algorithm.");
        }
    }
    
    protected static psc_i[] c(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_i[] array2 = { new psc_h(n2), new psc_k(12288), new psc_k(12288), new psc_k(768), new psc_j() };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not BER decode the CRL.");
        }
        return array2;
    }
    
    public int c(final int n) {
        return this.a(n);
    }
    
    private int a(final int l) {
        this.l = l;
        if (this.c == null) {
            this.d = this.b();
        }
        if (this.d == 0) {
            return 0;
        }
        if (super.e == null || super.g == null) {
            return 0;
        }
        try {
            this.m = new psc_n(new psc_i[] { new psc_h(l, true, 0), new psc_k(12288, true, 0, null, 0, this.d), new psc_k(12288, true, 0, null, 0, super.e.length), new psc_k(768, true, 0, null, 0, super.g.length), new psc_j() });
            return this.m.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int d(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Specified array is null.");
        }
        final int n3 = 0;
        int n4;
        try {
            if (this.m == null || n2 != this.l) {
                this.a(n2);
            }
            n4 = n3 + this.m.a(array, n);
            this.m = null;
        }
        catch (psc_m psc_m) {
            this.m = null;
            throw new psc_g("Could not encode: " + psc_m.getMessage());
        }
        int n5;
        if (this.c != null && this.d != 0 && this.n == 0) {
            System.arraycopy(this.c, 0, array, n + n4, this.d);
            n5 = n4 + this.d;
        }
        else {
            final int c = this.c(array, n + n4);
            if (c == 0) {
                throw new psc_g("Could not encode, missing data.");
            }
            n5 = n4 + c;
        }
        System.arraycopy(super.e, 0, array, n + n5, super.e.length);
        final int n6 = n5 + super.e.length;
        System.arraycopy(super.g, 0, array, n + n6, super.g.length);
        return n6 + super.g.length;
    }
    
    public void b(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.s();
        this.j();
        final psc_h psc_h = new psc_h(this.l);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(65536);
        final psc_k psc_k = new psc_k(12288);
        final psc_k psc_k2 = new psc_k(12288);
        final psc_z psc_z = new psc_z(0);
        final psc_ak psc_ak = new psc_ak(0);
        final psc_ai psc_ai = new psc_ai(0);
        final psc_z psc_z2 = new psc_z(65536);
        final psc_ak psc_ak2 = new psc_ak(0);
        final psc_ai psc_ai2 = new psc_ai(0);
        final psc_k psc_k3 = new psc_k(77824);
        final psc_k psc_k4 = new psc_k(10563584);
        final psc_i[] array2 = { psc_h, psc_p, psc_k, psc_k2, psc_z, psc_ak, psc_ai, psc_j, psc_z2, psc_ak2, psc_ai2, psc_j, psc_k3, psc_k4, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not BER decode the CRL info.");
        }
        if (psc_p.a) {
            this.e(psc_p.b, psc_p.c, psc_p.d);
        }
        if (!this.b(psc_k.b, psc_k.c, psc_k.d)) {
            throw new psc_g("Unknown or invalid signature algorithm.");
        }
        try {
            this.a(new psc_u(psc_k2.b, psc_k2.c, 0));
        }
        catch (psc_v psc_v) {
            throw new psc_g("Invalid issuer name: " + psc_v.getMessage());
        }
        if (psc_ak.a) {
            this.i = psc_ak.a;
        }
        else {
            this.i = psc_ai.a;
        }
        if (psc_ak2.a) {
            this.j = psc_ak2.a;
        }
        else if (psc_ai2.a) {
            this.j = psc_ai2.a;
        }
        if (psc_k3.a) {
            this.g = new psc_nb(psc_k3.b, psc_k3.c, 0);
        }
        if (psc_k4.a) {
            this.a(new psc_bg(psc_k4.b, psc_k4.c, 10485760, 2));
        }
        this.d = a(array, n) - n;
        System.arraycopy(array, n, this.c = new byte[this.d], 0, this.d);
    }
    
    protected void d(final int e) throws psc_g {
        if (e != 1) {
            throw new psc_g("Invalid X.509 CRL version.");
        }
        this.e = e;
    }
    
    protected void e(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null || n2 > 4) {
            throw new psc_g("Invalid X.509 Certificate version.");
        }
        int e = 0;
        for (int i = n; i < n + n2; ++i) {
            e = (e << 8 | (array[n] & 0xFF));
        }
        if (e != 1) {
            throw new psc_g("Invalid X.509 CRL version.");
        }
        this.e = e;
    }
    
    private boolean b(final byte[] array, int n, final int n2) {
        if (array == null || n2 == 0) {
            return false;
        }
        try {
            if (super.e == null) {
                System.arraycopy(array, n, super.e = new byte[n2], 0, n2);
                return psc_q.b(array, n, 1, null) != null;
            }
        }
        catch (psc_m psc_m) {
            return false;
        }
        if (n2 != super.e.length) {
            return false;
        }
        for (int i = 0; i < n2; ++i, ++n) {
            if (array[n] != super.e[i]) {
                return false;
            }
        }
        return true;
    }
    
    public int b() {
        return this.a();
    }
    
    private int a() {
        this.n = this.l;
        if (this.i == null || super.e == null) {
            return 0;
        }
        if (this.f == null && !this.b(18)) {
            return 0;
        }
        final psc_h psc_h = new psc_h(this.l, true, 0);
        final psc_j psc_j = new psc_j();
        boolean b = true;
        if (this.e == 0) {
            b = false;
        }
        final psc_p psc_p = new psc_p(65536, b, 0, this.e);
        psc_k psc_k;
        try {
            psc_k = new psc_k(12288, true, 0, super.e, 0, super.e.length);
        }
        catch (psc_m psc_m) {
            return 0;
        }
        psc_ai psc_ai;
        if (this.h) {
            psc_ai = new psc_ai(0, true, 0, this.i);
        }
        else {
            psc_ai = new psc_ak(0, true, 0, this.i);
        }
        boolean b2 = false;
        if (this.j != null) {
            b2 = true;
        }
        psc_ai psc_ai2;
        if (this.h) {
            psc_ai2 = new psc_ai(65536, b2, 0, this.j);
        }
        else {
            psc_ai2 = new psc_ak(65536, b2, 0, this.j);
        }
        boolean b3 = false;
        int g = 0;
        try {
            if (this.g != null) {
                g = this.g.g(65536);
                b3 = true;
            }
        }
        catch (psc_g psc_g) {
            return 0;
        }
        try {
            final psc_k psc_k2 = new psc_k(77824, b3, 0, null, 0, g);
            int d;
            byte[] array;
            try {
                if (this.f != null) {
                    d = this.f.d(0);
                    array = new byte[d];
                    this.f.a(array, 0, 0);
                }
                else {
                    d = 2;
                    array = new byte[d];
                    array[0] = 48;
                    array[1] = 0;
                }
            }
            catch (psc_v psc_v) {
                return 0;
            }
            final psc_k psc_k3 = new psc_k(12288, true, 0, array, 0, d);
            boolean b4 = false;
            int b5 = 0;
            if (this.e == 1 && this.k != null) {
                b5 = this.k.b(10551296);
                if (b5 != 0) {
                    b4 = true;
                }
            }
            this.o = new psc_n(new psc_i[] { psc_h, psc_p, psc_k, psc_k3, psc_ai, psc_ai2, psc_k2, new psc_k(10563584, b4, 0, null, 0, b5), psc_j });
            return this.o.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    public int c(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Passed array is null.");
        }
        final int n2 = 0;
        int n3;
        try {
            if (this.o == null && this.a() == 0) {
                throw new psc_g("Cannot encode innerDER, information missing.");
            }
            n3 = n2 + this.o.a(array, n);
            this.o = null;
        }
        catch (psc_m psc_m) {
            this.o = null;
            throw new psc_g("Could not encode: " + psc_m.getMessage());
        }
        if (this.g != null) {
            n3 += this.g.a(array, n + n3, 65536, this.h);
        }
        if (this.e == 1 && this.k != null) {
            n3 += this.k.b(array, n + n3, 10551296);
        }
        return n3;
    }
    
    public byte[] c() throws psc_g {
        if (super.g == null) {
            throw new psc_g("Object not signed.");
        }
        final psc_d4 psc_d4 = new psc_d4(0);
        final psc_i[] array = { psc_d4 };
        try {
            psc_l.a(super.g, 0, array);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot extract the signature.");
        }
        final byte[] array2 = new byte[psc_d4.d];
        System.arraycopy(psc_d4.b, psc_d4.c, array2, 0, psc_d4.d);
        return array2;
    }
    
    public void e(final int n) throws psc_g {
        this.s();
        this.j();
        this.e = n;
        if (n == 0 || n == 1) {
            return;
        }
        this.e = 0;
        throw new psc_g("Invalid CRL version: " + n);
    }
    
    public int d() {
        return this.e;
    }
    
    public void a(final psc_u psc_u) throws psc_g {
        this.s();
        this.j();
        if (psc_u == null) {
            if (!this.b(18)) {
                throw new psc_g("Cannot set the CRL with null issuerName.");
            }
        }
        else {
            try {
                this.f = (psc_u)psc_u.clone();
            }
            catch (CloneNotSupportedException ex) {
                throw new psc_g("Cannot set the CRL with the given issuerName.");
            }
        }
    }
    
    public psc_u e() {
        if (this.f == null) {
            return null;
        }
        try {
            return (psc_u)this.f.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void a(final Date date) {
        this.s();
        this.j();
        if (date != null) {
            this.j = new Date(date.getTime());
        }
    }
    
    public Date f() {
        if (this.j == null) {
            return null;
        }
        return new Date(this.j.getTime());
    }
    
    public void b(final Date date) {
        this.s();
        this.j();
        if (date != null) {
            this.i = new Date(date.getTime());
        }
    }
    
    public Date g() {
        if (this.i == null) {
            return null;
        }
        return new Date(this.i.getTime());
    }
    
    public void a(final psc_nb psc_nb) throws psc_g {
        this.s();
        this.j();
        if (psc_nb != null) {
            try {
                this.g = (psc_nb)psc_nb.clone();
            }
            catch (CloneNotSupportedException ex) {
                throw new psc_g("Cannot set the CRL with the given revoked Certs.");
            }
        }
    }
    
    public psc_nb h() {
        if (this.g == null) {
            return null;
        }
        try {
            return (psc_nb)this.g.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public boolean b(final psc_u psc_u) {
        return this.f != null && psc_u != null && this.f.equals(psc_u);
    }
    
    public void a(final boolean h) {
        this.h = h;
    }
    
    public void a(final psc_bg psc_bg) throws psc_g {
        this.s();
        this.j();
        if (psc_bg == null) {
            throw new psc_g("CRL extensions are null.");
        }
        if (psc_bg.b() != 2) {
            throw new psc_g("Wrong extensions type: should be CRL extensions.");
        }
        if (this.e == 0) {
            for (int i = 0; i < psc_bg.a(); ++i) {
                if (psc_bg.c(i).b()) {
                    throw new psc_g("Cannot set critical extensions on a version 1 CRL.");
                }
            }
        }
        try {
            this.k = (psc_bg)psc_bg.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Cannot set the CRL with the given extensions.");
        }
    }
    
    public psc_bg i() {
        if (this.k == null) {
            return null;
        }
        try {
            return (psc_bg)this.k.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void a(final String s, final String s2, final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_g {
        this.s();
        this.j();
        if (s == null || s2 == null || psc_dt == null) {
            throw new psc_g("Cannot sign, specified values are null.");
        }
        try {
            final String r = this.r();
            if (r == null) {
                super.e = psc_q.a(s, 1, null, 0, 0);
            }
            else {
                super.e = psc_q.a(r, 1, null, 0, 0);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot sign, unknown algorithm.(" + psc_m.getMessage() + ")");
        }
        this.d = this.b();
        if (this.d == 0) {
            throw new psc_g("Cannot sign CRL, values not set.");
        }
        this.c = new byte[this.d];
        this.d = this.c(this.c, 0);
        final byte[] a = this.a(s, s2, psc_dt, secureRandom, this.c, 0, this.d);
        try {
            super.g = psc_l.a(new psc_i[] { new psc_d4(0, true, 0, a, 0, a.length, a.length * 8, false) });
        }
        catch (psc_m psc_m2) {
            this.s();
            throw new psc_g("Cannot sign the CRL as presently set.");
        }
    }
    
    public boolean a(final String s, final psc_al psc_al, final SecureRandom secureRandom) throws psc_g {
        if (this.c == null) {
            throw new psc_g("Cannot verify CRL, values not set.");
        }
        if (s == null || psc_al == null) {
            throw new psc_g("Specified values are null.");
        }
        final byte[] c = this.c();
        return this.a(s, psc_al, secureRandom, this.c, 0, this.d, c, 0, c.length);
    }
    
    private boolean b(final int n) {
        if (this.e == 1 && this.k != null) {
            final Vector a = this.k.a;
            for (int i = 0; i < a.size(); ++i) {
                final psc_aj psc_aj = a.elementAt(i);
                if (psc_aj.c() == n && psc_aj.b()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_m9)) {
            return false;
        }
        final psc_m9 psc_m9 = (psc_m9)o;
        try {
            final int c = this.c(0);
            final int c2 = psc_m9.c(0);
            if (c != c2) {
                return false;
            }
            final byte[] array = new byte[c];
            final byte[] array2 = new byte[c2];
            final int d = this.d(array, 0, 0);
            if (d != psc_m9.d(array2, 0, 0)) {
                return false;
            }
            for (int i = 0; i < d; ++i) {
                if (array[i] != array2[i]) {
                    return false;
                }
            }
        }
        catch (psc_g psc_g) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_m9 psc_m9 = (psc_m9)super.clone();
        if (super.e != null) {
            psc_m9.e = super.e.clone();
        }
        psc_m9.f = super.f;
        if (super.g != null) {
            psc_m9.g = super.g.clone();
        }
        if (super.h != null) {
            psc_m9.h = new String(super.h);
        }
        if (super.i != null) {
            psc_m9.i = super.i.clone();
        }
        if (this.c != null) {
            psc_m9.c = this.c.clone();
        }
        psc_m9.d = this.d;
        psc_m9.e = this.e;
        if (this.f != null) {
            psc_m9.f = (psc_u)this.f.clone();
        }
        if (this.g != null) {
            psc_m9.g = (psc_nb)this.g.clone();
        }
        if (this.i != null) {
            psc_m9.i = new Date(this.i.getTime());
        }
        if (this.j != null) {
            psc_m9.j = new Date(this.j.getTime());
        }
        if (this.k != null) {
            psc_m9.k = (psc_bg)this.k.clone();
        }
        psc_m9.l = this.l;
        psc_m9.n = this.n;
        if (this.m != null) {
            psc_m9.a(this.l);
        }
        if (this.o != null) {
            psc_m9.a();
        }
        return psc_m9;
    }
    
    protected void j() {
        this.m = null;
        this.o = null;
        this.c = null;
        this.d = 0;
    }
    
    protected void k() {
        super.k();
        this.j();
        this.c = null;
        this.d = 0;
        this.e = 0;
        this.f = null;
        this.g = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.h = false;
    }
}
