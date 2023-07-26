import java.util.Vector;
import java.security.SecureRandom;
import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_e extends psc_f implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    private byte[] d;
    private int e;
    private int f;
    private psc_u g;
    private psc_u h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private boolean l;
    private Date m;
    private Date n;
    private psc_bg o;
    protected int p;
    private psc_n q;
    protected int r;
    private psc_n s;
    private psc_n t;
    
    public psc_e() {
        this.f = 2;
        this.l = false;
    }
    
    public psc_e(final psc_ah psc_ah) {
        this.f = 2;
        this.l = false;
        this.a(psc_ah);
    }
    
    public psc_e(final byte[] array, final int n, final int n2) throws psc_g {
        this(array, n, n2, null);
    }
    
    public psc_e(final byte[] array, final int n, final int n2, final psc_ah psc_ah) throws psc_g {
        this.f = 2;
        this.l = false;
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.a(array, n, n2);
        this.a(psc_ah);
    }
    
    public static int c(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER encoding." + psc_m.getMessage());
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.p();
        final psc_i[] c = c(array, n, n2);
        this.d(c[1].b, c[1].c);
        super.h = new byte[c[3].d];
        System.arraycopy(c[3].b, c[3].c, super.h, 0, c[3].d);
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
            throw new psc_g("Could not BER decode the cert." + psc_m.getMessage());
        }
        return array2;
    }
    
    public int c(final int n) {
        return this.a(n);
    }
    
    private int a(final int p) {
        this.p = p;
        if (this.d == null) {
            this.e = this.c();
        }
        if (this.e == 0) {
            return 0;
        }
        if (super.f == null || super.h == null) {
            return 0;
        }
        try {
            this.q = new psc_n(new psc_i[] { new psc_h(p, true, 0), new psc_k(12288, true, 0, null, 0, this.e), new psc_k(12288, true, 0, null, 0, super.f.length), new psc_k(768, true, 0, null, 0, super.h.length), new psc_j() });
            return this.q.a();
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
            if ((this.q == null || n2 != this.p) && this.a(n2) == 0) {
                throw new psc_g("Could not encode: Possibly some of the required fields of this certificate object are not set.");
            }
            n4 = n3 + this.q.a(array, n);
            this.q = null;
        }
        catch (psc_m psc_m) {
            this.q = null;
            throw new psc_g("Could not encode: " + psc_m.getMessage());
        }
        int n5;
        if (this.d != null && this.e != 0) {
            System.arraycopy(this.d, 0, array, n + n4, this.e);
            n5 = n4 + this.e;
        }
        else {
            final int e = this.e(array, n + n4);
            if (e == 0) {
                throw new psc_g("Could not encode, missing data.");
            }
            n5 = n4 + e;
        }
        System.arraycopy(super.f, 0, array, n + n5, super.f.length);
        final int n6 = n5 + super.f.length;
        System.arraycopy(super.h, 0, array, n + n6, super.h.length);
        return n6 + super.h.length;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        this.y();
        this.o();
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(this.p);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(10616832);
        final psc_p psc_p2 = new psc_p(0);
        final psc_k psc_k = new psc_k(12288);
        final psc_k psc_k2 = new psc_k(12288);
        final psc_k psc_k3 = new psc_k(12288);
        final psc_k psc_k4 = new psc_k(12288);
        final psc_k psc_k5 = new psc_k(12288);
        final psc_k psc_k6 = new psc_k(8454913);
        final psc_k psc_k7 = new psc_k(8454914);
        final psc_k psc_k8 = new psc_k(10563587);
        final psc_i[] array2 = { psc_h, psc_p, psc_p2, psc_k, psc_k2, psc_k3, psc_k4, psc_k5, psc_k6, psc_k7, psc_k8, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not BER decode the cert info." + psc_m.getMessage());
        }
        if (psc_p.a) {
            this.e(psc_p.b, psc_p.c, psc_p.d);
        }
        this.f(psc_p2.b, psc_p2.c, psc_p2.d);
        if (!this.b(psc_k.b, psc_k.c, psc_k.d)) {
            throw new psc_g("Unknown or invalid signature algorithm.");
        }
        try {
            this.b(new psc_u(psc_k2.b, psc_k2.c, 0));
        }
        catch (psc_v psc_v) {
            throw new psc_g("Invalid issuer name: " + psc_v.getMessage());
        }
        this.a(psc_k3.b, psc_k3.c);
        try {
            this.a(new psc_u(psc_k4.b, psc_k4.c, 0));
        }
        catch (psc_v psc_v2) {
            throw new psc_g("Invalid subject name: " + psc_v2.getMessage());
        }
        this.f(psc_k5.b, psc_k5.c);
        if (psc_k6.a) {
            if (this.f == 0) {
                throw new psc_g("Version 1 certs not allowed to have issuer unique ID.");
            }
            this.j = new byte[psc_k6.d];
            System.arraycopy(psc_k6.b, psc_k6.c, this.j, 0, psc_k6.d);
        }
        if (psc_k7.a) {
            if (this.f == 0) {
                throw new psc_g("Version 1 certs not allowed to have subject unique ID.");
            }
            this.k = new byte[psc_k7.d];
            System.arraycopy(psc_k7.b, psc_k7.c, this.k, 0, psc_k7.d);
        }
        if (psc_k8.a) {
            this.b(new psc_bg(psc_k8.b, psc_k8.c, 10485763, 1));
        }
        this.e = c(array, n) - n;
        System.arraycopy(array, n, this.d = new byte[this.e], 0, this.e);
    }
    
    protected void d(final int f) throws psc_g {
        if (f != 0 && f != 1 && f != 2) {
            throw new psc_g("Invalid X.509 Certificate version.");
        }
        this.f = f;
    }
    
    protected void e(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null || n2 > 4) {
            throw new psc_g("Invalid X.509 Certificate version.");
        }
        int f = 0;
        for (int i = n; i < n + n2; ++i) {
            f = (f << 8 | (array[n] & 0xFF));
        }
        if (f != 0 && f != 1 && f != 2) {
            throw new psc_g("Invalid X.509 Certificate version.");
        }
        this.f = f;
    }
    
    private boolean b(final byte[] array, int n, final int n2) {
        if (array == null || n2 == 0) {
            return false;
        }
        try {
            if (super.f == null) {
                System.arraycopy(array, n, super.f = new byte[n2], 0, n2);
                return psc_q.b(array, n, 1, null) != null;
            }
        }
        catch (psc_m psc_m) {
            return false;
        }
        if (n2 != super.f.length) {
            return false;
        }
        for (int i = 0; i < n2; ++i, ++n) {
            if (array[n] != super.f[i]) {
                return false;
            }
        }
        return true;
    }
    
    public int c() {
        if (this.e != 0) {
            return this.e;
        }
        return this.a();
    }
    
    private int a() {
        this.r = this.p;
        if (super.e == null || super.f == null || this.i == null || this.m == null || this.n == null) {
            return 0;
        }
        if (this.g == null && !this.b(17)) {
            return 0;
        }
        if (this.h == null && !this.b(18)) {
            return 0;
        }
        final int b = this.b();
        try {
            final psc_h psc_h = new psc_h(this.p, true, 0);
            final psc_j psc_j = new psc_j();
            boolean b2 = true;
            if (this.f == 0) {
                b2 = false;
            }
            final psc_p psc_p = new psc_p(10616832, b2, 0, this.f);
            psc_p psc_p2;
            if ((this.i[0] & 0x80) >> 7 == 0) {
                psc_p2 = new psc_p(0, true, 0, this.i, 0, this.i.length, true);
            }
            else {
                psc_p2 = new psc_p(0, true, 0, this.i, 0, this.i.length, false);
            }
            final psc_k psc_k = new psc_k(12288, true, 0, super.f, 0, super.f.length);
            final psc_k psc_k2 = new psc_k(12288, true, 0, null, 0, b);
            final psc_k psc_k3 = new psc_k(12288, true, 0, null, 0, super.e.length);
            int d;
            if (this.h != null) {
                d = this.h.d(0);
            }
            else {
                d = 2;
            }
            final psc_k psc_k4 = new psc_k(12288, true, 0, null, 0, d);
            int d2;
            if (this.g != null) {
                d2 = this.g.d(0);
            }
            else {
                d2 = 2;
            }
            final psc_k psc_k5 = new psc_k(12288, true, 0, null, 0, d2);
            boolean b3 = false;
            int length = 0;
            if (this.f != 0 && this.j != null) {
                b3 = true;
                length = this.j.length;
            }
            final psc_k psc_k6 = new psc_k(8454913, b3, 0, null, 0, length);
            boolean b4 = false;
            int length2 = 0;
            if (this.f != 0 && this.k != null) {
                b4 = true;
                length2 = this.k.length;
            }
            final psc_k psc_k7 = new psc_k(8454914, b4, 0, null, 0, length2);
            boolean b5 = false;
            int b6 = 0;
            if (this.f == 2 && this.o != null) {
                b6 = this.o.b(10551299);
                if (b6 != 0) {
                    b5 = true;
                }
            }
            this.s = new psc_n(new psc_i[] { psc_h, psc_p, psc_p2, psc_k, psc_k4, psc_k2, psc_k5, psc_k3, psc_k6, psc_k7, new psc_k(10563587, b5, 0, null, 0, b6), psc_j });
            return this.s.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Passed array is null");
        }
        final int n2 = 0;
        int n3;
        try {
            if (this.s == null && this.a() == 0) {
                throw new psc_g("Cannot encode innerDER, information missing.");
            }
            n3 = n2 + this.s.a(array, n);
            this.s = null;
        }
        catch (psc_m psc_m) {
            this.s = null;
            throw new psc_g("Could not encode: " + psc_m.getMessage());
        }
        int n5;
        try {
            if (this.h != null) {
                n3 += this.h.a(array, n + n3, 0);
            }
            else {
                array[n + n3] = 48;
                array[n + n3 + 1] = 0;
                n3 += 2;
            }
            final int b = this.b(array, n + n3);
            if (b == 0) {
                throw new psc_g("Could not encode Validity.");
            }
            int n4 = n3 + b;
            if (this.g != null) {
                n4 += this.g.a(array, n + n4, 0);
            }
            else {
                array[n + n4] = 48;
                array[n + n4 + 1] = 0;
                n4 += 2;
            }
            System.arraycopy(super.e, 0, array, n + n4, super.e.length);
            n5 = n4 + super.e.length;
            if (this.f != 0 && this.j != null) {
                System.arraycopy(this.j, 0, array, n + n5, this.j.length);
                n5 += this.j.length;
            }
            if (this.f != 0 && this.k != null) {
                System.arraycopy(this.k, 0, array, n + n5, this.k.length);
                n5 += this.k.length;
            }
            if (this.f == 2 && this.o != null) {
                n5 += this.o.b(array, n + n5, 10551299);
            }
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not encode a Name: " + psc_v.getMessage());
        }
        return n5;
    }
    
    public void a(final psc_mr psc_mr) throws psc_g {
        this.p();
        if (psc_mr == null) {
            throw new psc_g("Cert Request is null.");
        }
        this.a(psc_mr.c());
        this.a(psc_mr.a("Java"));
        final psc_cr d = psc_mr.d();
        if (d == null) {
            return;
        }
        final psc_fz d2 = d.d(2);
        if (d2 == null) {
            this.e(0);
            return;
        }
        this.e(2);
        this.b(((psc_f3)d2).g());
    }
    
    public byte[] d() throws psc_g {
        if (super.h == null) {
            throw new psc_g("Object not signed.");
        }
        final psc_d4 psc_d4 = new psc_d4(0);
        final psc_i[] array = { psc_d4 };
        try {
            psc_l.a(super.h, 0, array);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot extract the signature." + psc_m.getMessage());
        }
        final byte[] array2 = new byte[psc_d4.d];
        System.arraycopy(psc_d4.b, psc_d4.c, array2, 0, psc_d4.d);
        return array2;
    }
    
    public void e(final int n) throws psc_g {
        if (n == this.f) {
            return;
        }
        if (n != 0 && n != 1 && n != 2) {
            throw new psc_g("Invalid cert version: " + n);
        }
        this.y();
        this.o();
        switch (n) {
            case 0: {
                if (!this.a(this.o)) {
                    throw new psc_g("You can not use X509 V1 version for a certificate with extensions.");
                }
                if (this.j != null) {
                    throw new psc_g("You can not use X509 V1 version for a certificate with issuer unique ID.");
                }
                if (this.k != null) {
                    throw new psc_g("You can not use X509 V1 version for a certificate with subject unique ID.");
                }
                break;
            }
            case 1: {
                if (!this.a(this.o)) {
                    throw new psc_g("You can not use X509 V2 version for a certificate with extensions.");
                }
                break;
            }
        }
        this.f = n;
    }
    
    public int e() {
        return this.f;
    }
    
    public void a(final psc_u psc_u) throws psc_g {
        this.y();
        this.o();
        if (psc_u == null) {
            if (!this.b(17)) {
                throw new psc_g("Cannot set the cert with the given subjectName.");
            }
        }
        else {
            try {
                this.g = (psc_u)psc_u.clone();
            }
            catch (CloneNotSupportedException ex) {
                throw new psc_g("Cannot set the cert with the given subjectName.");
            }
        }
    }
    
    public psc_u f() {
        if (this.g == null) {
            return null;
        }
        try {
            return (psc_u)this.g.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void b(final psc_u psc_u) throws psc_g {
        this.y();
        this.o();
        if (psc_u == null) {
            if (!this.b(18)) {
                throw new psc_g("Cannot set the cert with the given issuerName.");
            }
        }
        else {
            try {
                this.h = (psc_u)psc_u.clone();
            }
            catch (CloneNotSupportedException ex) {
                throw new psc_g("Cannot set the cert with the given issuerName.");
            }
        }
    }
    
    public psc_u g() {
        if (this.h == null) {
            return null;
        }
        try {
            return (psc_u)this.h.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void f(final byte[] array, final int n, final int n2) {
        this.y();
        this.o();
        this.i = new byte[n2];
        if (array == null) {
            return;
        }
        System.arraycopy(array, n, this.i, 0, n2);
    }
    
    public byte[] h() {
        if (this.i == null) {
            return new byte[0];
        }
        return this.i.clone();
    }
    
    public byte[] i() throws psc_g {
        if (this.h == null || this.i == null) {
            throw new psc_g("Cannot get issuerSerial, not all values set.");
        }
        try {
            final byte[] array = new byte[this.h.d(0)];
            final int a = this.h.a(array, 0, 0);
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            psc_p psc_p;
            if ((this.i[0] & 0x80) >> 7 == 0) {
                psc_p = new psc_p(0, true, 0, this.i, 0, this.i.length, true);
            }
            else {
                psc_p = new psc_p(0, true, 0, this.i, 0, this.i.length, false);
            }
            return psc_l.a(new psc_i[] { psc_h, new psc_k(12288, true, 0, array, 0, a), psc_p, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot encode issuerSerial: " + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_g("Cannot encode issuerSerial: " + psc_v.getMessage());
        }
    }
    
    public boolean g(final byte[] array, int n, final int n2) {
        if (array == null || n2 == 0) {
            return false;
        }
        try {
            final byte[] i = this.i();
            if (i.length != n2) {
                return false;
            }
            for (int j = 0; j < n2; ++j, ++n) {
                if (array[n] != i[j]) {
                    return false;
                }
            }
            return true;
        }
        catch (psc_g psc_g) {
            return false;
        }
    }
    
    public boolean c(final psc_u psc_u) {
        return this.g != null && psc_u != null && this.g.equals(psc_u);
    }
    
    public void a(final boolean l) {
        this.l = l;
    }
    
    private void a(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_z psc_z = new psc_z(0);
        final psc_z psc_z2 = new psc_z(0);
        final psc_ak psc_ak = new psc_ak(0);
        final psc_ak psc_ak2 = new psc_ak(0);
        final psc_ai psc_ai = new psc_ai(0);
        final psc_ai psc_ai2 = new psc_ai(0);
        final psc_i[] array2 = { psc_h, psc_z, psc_ak, psc_ai, psc_j, psc_z2, psc_ak2, psc_ai2, psc_j, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot extract Validity." + psc_m.getMessage());
        }
        Date date = psc_ai.a;
        if (!psc_ai.a) {
            date = psc_ak.a;
        }
        Date date2 = psc_ai2.a;
        if (!psc_ai2.a) {
            date2 = psc_ak2.a;
        }
        this.a(date, date2);
    }
    
    public void a(final Date date, final Date date2) throws psc_g {
        this.y();
        this.o();
        if (date == null || date2 == null) {
            throw new psc_g("Cannot set the validity with the given dates.");
        }
        this.m = new Date(date.getTime());
        this.n = new Date(date2.getTime());
        if (!this.n.after(this.m)) {
            throw new psc_g("Cannot set the validity with the given dates.");
        }
    }
    
    private int b() {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_ai psc_ai;
        psc_ai psc_ai2;
        if (this.l) {
            psc_ai = new psc_ai(0, true, 0, this.m);
            psc_ai2 = new psc_ai(0, true, 0, this.n);
        }
        else {
            psc_ai = new psc_ak(0, true, 0, this.m);
            psc_ai2 = new psc_ak(0, true, 0, this.n);
        }
        this.t = new psc_n(new psc_i[] { psc_h, psc_ai, psc_ai2, psc_j });
        try {
            return this.t.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    private int b(final byte[] array, final int n) {
        if (this.t == null && this.b() == 0) {
            return 0;
        }
        try {
            final int a = this.t.a(array, n);
            this.t = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.t = null;
            return 0;
        }
    }
    
    public Date j() {
        if (this.m == null) {
            return null;
        }
        return new Date(this.m.getTime());
    }
    
    public Date k() {
        if (this.n == null) {
            return null;
        }
        return new Date(this.n.getTime());
    }
    
    public boolean a(final Date date) {
        return this.m != null && this.n != null && date != null && this.m.before(date) && this.n.after(date);
    }
    
    public void h(final byte[] array, final int n, final int n2) throws psc_g {
        this.y();
        this.o();
        if (this.f == 0) {
            throw new psc_g("Cannot set unique ID on a version 1 cert.");
        }
        if (array == null) {
            return;
        }
        try {
            this.j = psc_l.a(new psc_i[] { new psc_d4(8388609, true, 0, array, n, n2, n2 * 8, false) });
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot set issuerUniqueID: " + psc_m.getMessage());
        }
    }
    
    public byte[] l() {
        if (this.j == null) {
            return null;
        }
        try {
            final int n = 2 + psc_o.a(this.j, 1);
            final byte[] array = new byte[this.j.length - n];
            System.arraycopy(this.j, n, array, 0, array.length);
            return array;
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
    
    public void i(final byte[] array, final int n, final int n2) throws psc_g {
        this.y();
        this.o();
        if (this.f == 0) {
            throw new psc_g("Cannot set unique ID on a version 1 cert.");
        }
        if (array == null) {
            return;
        }
        try {
            this.k = psc_l.a(new psc_i[] { new psc_d4(8388610, true, 0, array, n, n2, n2 * 8, false) });
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot set subjectUniqueID: " + psc_m.getMessage());
        }
    }
    
    public byte[] m() {
        if (this.k == null) {
            return null;
        }
        try {
            final int n = 2 + psc_o.a(this.k, 1);
            final byte[] array = new byte[this.k.length - n];
            System.arraycopy(this.k, n, array, 0, array.length);
            return array;
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
    
    public void b(final psc_bg psc_bg) throws psc_g {
        if (this.a(psc_bg)) {
            return;
        }
        if (psc_bg.b() != 1) {
            throw new psc_g("Wrong extensions type: should be Cert extensions.");
        }
        this.y();
        this.o();
        try {
            this.o = (psc_bg)psc_bg.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Cannot set the cert with the given extensions(" + ex.getMessage() + ").");
        }
        if (this.f != 2) {
            this.e(2);
        }
    }
    
    public psc_bg n() {
        if (this.o == null) {
            return null;
        }
        try {
            return (psc_bg)this.o.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void a(final String s, final String s2, final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_g {
        this.y();
        this.o();
        if (s == null || s2 == null || psc_dt == null) {
            throw new psc_g("Specified values are null.");
        }
        try {
            final String a = this.a(s);
            if (a == null) {
                super.f = psc_q.a(s, 1, null, 0, 0);
            }
            else {
                super.f = psc_q.a(a, 1, null, 0, 0);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot sign, unknown algorithm.(" + psc_m.getMessage() + ")");
        }
        this.e = this.c();
        if (this.e == 0) {
            throw new psc_g("Cannot sign certificate, values not set.");
        }
        this.d = new byte[this.e];
        this.e = this.e(this.d, 0);
        final byte[] a2 = this.a(s, s2, psc_dt, secureRandom, this.d, 0, this.e);
        try {
            super.h = psc_l.a(new psc_i[] { new psc_d4(0, true, 0, a2, 0, a2.length, a2.length * 8, false) });
        }
        catch (psc_m psc_m2) {
            this.y();
            throw new psc_g("Cannot sign the cert as presently set." + psc_m2.getMessage());
        }
    }
    
    public boolean a(final String s, final psc_al psc_al, final SecureRandom secureRandom) throws psc_g {
        if (s == null || psc_al == null) {
            throw new psc_g("Specified values are null.");
        }
        if (this.d == null) {
            throw new psc_g("Cannot verify certificate, values not set.");
        }
        final byte[] d = this.d();
        return this.a(s, psc_al, secureRandom, this.d, 0, this.e, d, 0, d.length);
    }
    
    private boolean b(final int n) {
        if (this.f == 2 && this.o != null) {
            final Vector a = this.o.a;
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
        if (o == null || !(o instanceof psc_e)) {
            return false;
        }
        final psc_e psc_e = (psc_e)o;
        try {
            final int c = this.c(0);
            final int c2 = psc_e.c(0);
            if (c != c2) {
                return false;
            }
            final byte[] array = new byte[c];
            final byte[] array2 = new byte[c2];
            final int d = this.d(array, 0, 0);
            if (d != psc_e.d(array2, 0, 0)) {
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
        final psc_e psc_e = (psc_e)super.clone();
        if (super.e != null) {
            psc_e.e = super.e.clone();
        }
        if (super.f != null) {
            psc_e.f = super.f.clone();
        }
        if (super.h != null) {
            psc_e.h = super.h.clone();
        }
        psc_e.g = super.g;
        if (super.i != null) {
            psc_e.i = new String(super.i);
        }
        if (super.j != null) {
            psc_e.j = super.j.clone();
        }
        if (this.d != null) {
            psc_e.d = this.d.clone();
        }
        psc_e.e = this.e;
        psc_e.f = this.f;
        if (this.g != null) {
            psc_e.g = (psc_u)this.g.clone();
        }
        if (this.h != null) {
            psc_e.h = (psc_u)this.h.clone();
        }
        if (this.i != null) {
            psc_e.i = this.i.clone();
        }
        if (this.j != null) {
            psc_e.j = this.j.clone();
        }
        if (this.k != null) {
            psc_e.k = this.k.clone();
        }
        psc_e.l = this.l;
        if (this.m != null) {
            psc_e.m = new Date(this.m.getTime());
        }
        if (this.n != null) {
            psc_e.n = new Date(this.n.getTime());
        }
        if (this.o != null) {
            psc_e.o = (psc_bg)this.o.clone();
        }
        psc_e.p = this.p;
        psc_e.r = this.r;
        if (this.q != null) {
            psc_e.a(this.p);
        }
        if (this.s != null) {
            psc_e.a();
        }
        if (this.t != null) {
            psc_e.b();
        }
        return psc_e;
    }
    
    protected void o() {
        this.q = null;
        this.s = null;
        this.d = null;
        this.e = 0;
    }
    
    protected void p() {
        super.p();
        this.o();
        this.d = null;
        this.e = 0;
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.m = null;
        this.n = null;
        this.o = null;
    }
    
    private boolean a(final psc_bg psc_bg) {
        return psc_bg == null || psc_bg.a() == 0;
    }
}
