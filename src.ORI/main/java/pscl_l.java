import java.net.UnknownHostException;
import java.net.InetAddress;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_l
{
    private byte[] a;
    private byte[] b;
    private String c;
    private String d;
    private byte[] e;
    private long f;
    private int g;
    public pscl_i h;
    public pscl_j[] i;
    public pscl_j[] j;
    public pscl_v k;
    
    public pscl_l(final byte[] array, final int n, final pscl_b pscl_b) throws pscl_h {
        try {
            final pscl_z pscl_z = new pscl_z(0, true, 0);
            final pscl_ac pscl_ac = new pscl_ac();
            final pscl_r pscl_r = new pscl_r(0, true, 0, 0);
            final pscl_r pscl_r2 = new pscl_r(0, true, 0, 0);
            final pscl_s pscl_s = new pscl_s(0, true, 0, null, 0, 0);
            final pscl_s pscl_s2 = new pscl_s(0, true, 0, null, 0, 0);
            final pscl_s pscl_s3 = new pscl_s(0, true, 0, null, 0, 0);
            final pscl_s pscl_s4 = new pscl_s(0, true, 0, null, 0, 0);
            final pscl_r pscl_r3 = new pscl_r(0, true, 0, 0);
            pscl_u.a(array, n, new pscl_q[] { pscl_z, pscl_r, pscl_r2, pscl_s, pscl_s2, pscl_s3, pscl_s4, pscl_r3, new pscl_r(0, true, 0, 0), new pscl_t(12288, true, 0, null, 0, 0), new pscl_aa(0, true, 0, "RC4", 0, 40), pscl_ac });
            this.g = pscl_r2.d();
            final pscl_i[] a = pscl_b.a();
            final byte[] array2 = new byte[3];
            final byte[] array3 = new byte[2];
            System.arraycopy(pscl_s.l, pscl_s.m, array2, 0, 3);
            System.arraycopy(pscl_s.l, pscl_s.m + 1, array3, 0, 2);
            for (int i = 0; i < a.length; ++i) {
                if (pscl_k.a(a[i].b(768), array3)) {
                    this.h = a[i];
                }
            }
            this.a = new byte[pscl_s2.n];
            System.arraycopy(pscl_s2.l, pscl_s2.m, this.a, 0, this.a.length);
            this.e = new byte[pscl_s3.n];
            System.arraycopy(pscl_s3.l, pscl_s3.m, this.e, 0, this.e.length);
            this.b = new byte[pscl_s4.n];
            if (pscl_s4.n > 0) {
                System.arraycopy(pscl_s4.l, pscl_s4.m, this.b, 0, this.b.length);
            }
            this.f = pscl_r3.d();
        }
        catch (pscl_x pscl_x) {
            throw new pscl_h("Not able to BER decode the given session");
        }
    }
    
    public pscl_l(final byte[] array, final String d, final long f, final byte[] array2, final pscl_i h, final pscl_j[] i, final pscl_j[] j, final pscl_b pscl_b, final int g) {
        if (array == null) {
            this.a = new byte[32];
            pscl_b.i().b(this.a);
        }
        else {
            System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
        }
        this.f = f;
        this.h = h;
        this.c = pscl_k.a(this.a);
        this.d = d;
        if (array2 == null) {
            this.e = null;
        }
        else {
            System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        }
        this.i = i;
        this.j = j;
        this.g = g;
    }
    
    public pscl_l(final byte[] array, final pscl_b pscl_b, final int n) {
        this(array, "localhost", System.currentTimeMillis(), null, null, null, null, pscl_b, n);
    }
    
    public void a(final long f) {
        this.f = f;
    }
    
    public long b() {
        return this.f;
    }
    
    public void a(final int g) {
        this.g = g;
    }
    
    public int c() {
        return this.g;
    }
    
    public void a(final byte[] array) {
        if (array == null) {
            return;
        }
        System.arraycopy(array, 0, this.b = new byte[array.length], 0, array.length);
    }
    
    public byte[] d() {
        final byte[] array = new byte[this.b.length];
        System.arraycopy(this.b, 0, array, 0, array.length);
        return array;
    }
    
    public byte[] e() {
        final byte[] array = new byte[this.a.length];
        System.arraycopy(this.a, 0, array, 0, array.length);
        return array;
    }
    
    public String f() {
        return this.c;
    }
    
    public String g() {
        String hostName = null;
        try {
            hostName = InetAddress.getByName(this.d).getHostName();
        }
        catch (UnknownHostException ex) {}
        return hostName;
    }
    
    public String h() {
        return this.d;
    }
    
    public void b(final byte[] array) {
        if (this.e != null) {
            for (int i = 0; i < this.e.length; ++i) {
                this.e[i] = 0;
            }
        }
        System.arraycopy(array, 0, this.e = new byte[array.length], 0, array.length);
    }
    
    public byte[] i() {
        if (this.e == null) {
            return null;
        }
        final byte[] array = new byte[this.e.length];
        System.arraycopy(this.e, 0, array, 0, array.length);
        return array;
    }
    
    public void a(final pscl_j[] array) {
        this.i = new pscl_j[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.i[i] = array[i];
        }
    }
    
    public pscl_j[] j() {
        return this.i;
    }
    
    public void b(final pscl_j[] array) {
        this.j = new pscl_j[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.j[i] = array[i];
        }
    }
    
    public pscl_j[] k() {
        return this.j;
    }
    
    public pscl_i l() {
        return this.h;
    }
    
    public int m() {
        return this.a();
    }
    
    private int a() {
        final pscl_z pscl_z = new pscl_z(0, true, 0);
        final pscl_ac pscl_ac = new pscl_ac();
        final pscl_r pscl_r = new pscl_r(0, true, 0, 1);
        final pscl_r pscl_r2 = new pscl_r(0, true, 0, this.g);
        final byte[] array = new byte[3];
        if (this.h != null) {
            if (this.h.b(this.g).length == 2) {
                System.arraycopy(this.h.b(this.g), 0, array, 1, 2);
            }
            else {
                System.arraycopy(this.h.b(this.g), 0, array, 0, 3);
            }
        }
        final pscl_s pscl_s = new pscl_s(0, true, 0, array, 0, array.length);
        final pscl_s pscl_s2 = new pscl_s(0, true, 0, this.a, 0, this.a.length);
        final pscl_s pscl_s3 = new pscl_s(0, true, 0, this.e, 0, this.e.length);
        pscl_s pscl_s4;
        if (this.b == null) {
            pscl_s4 = new pscl_s(0, true, 0, new byte[0], 0, 0);
        }
        else {
            pscl_s4 = new pscl_s(0, true, 0, this.b, 0, this.b.length);
        }
        final pscl_r pscl_r3 = new pscl_r(0, true, 0, (int)this.f);
        final pscl_r pscl_r4 = new pscl_r(0, true, 0, 0);
        byte[] array2;
        if (this.i != null) {
            array2 = new byte[this.i[0].a(0)];
            this.i[0].a(array2, 0, 0);
        }
        else if (this.j != null) {
            array2 = new byte[this.j[0].a(0)];
            this.j[0].a(array2, 0, 0);
        }
        else {
            array2 = new byte[0];
        }
        this.k = new pscl_v(new pscl_q[] { pscl_z, pscl_r, pscl_r2, pscl_s, pscl_s2, pscl_s3, pscl_s4, pscl_r3, pscl_r4, new pscl_t(0, true, 0, array2, 0, array2.length), new pscl_aa(0, true, 0, "RC4", 0, 40), pscl_ac });
        return this.k.a();
    }
    
    public int a(final byte[] array, final int n) throws pscl_x {
        this.a();
        return this.k.a(array, n);
    }
    
    public long n() {
        throw new UnsupportedOperationException("SSL-J Lite is a client implementation only");
    }
    
    public void o() {
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        for (int j = 0; j < this.a.length; ++j) {
            this.a[j] = 0;
        }
        this.f = 0L;
    }
}
