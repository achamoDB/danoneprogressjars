import java.net.UnknownHostException;
import java.net.InetAddress;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_d2
{
    private byte[] a;
    private byte[] b;
    private String c;
    private String d;
    private byte[] e;
    private long f;
    private long g;
    private int h;
    psc_dw i;
    psc_e[] j;
    psc_e[] k;
    
    public psc_d2(final byte[] array, final String d, final long f, final byte[] array2, final psc_dw i, final psc_e[] j, final psc_e[] k, final psc_c2 psc_c2, final int h) {
        if (array == null) {
            this.a = new byte[32];
            psc_c2.q().nextBytes(this.a);
        }
        else {
            System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
        }
        this.f = f;
        this.i = i;
        this.c = psc_c9.a(this.a);
        this.d = d;
        if (array2 == null) {
            this.e = null;
        }
        else {
            System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        }
        this.j = j;
        this.k = k;
        this.h = h;
    }
    
    public psc_d2(final byte[] array, final psc_c2 psc_c2, final int n) {
        this(array, "localhost", System.currentTimeMillis(), null, null, null, null, psc_c2, n);
    }
    
    public void a(final long f) {
        this.f = f;
    }
    
    public long a() {
        return this.f;
    }
    
    public void b(final long g) {
        this.g = g;
    }
    
    public long b() {
        if (this.g > 0L) {
            return this.g;
        }
        return this.f;
    }
    
    public void a(final int h) {
        this.h = h;
    }
    
    public int c() {
        return this.h;
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
    
    public void a(final psc_e[] array) {
        this.j = new psc_e[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.j[i] = array[i];
        }
    }
    
    public psc_e[] j() {
        return this.j;
    }
    
    public void b(final psc_e[] array) {
        this.k = new psc_e[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.k[i] = array[i];
        }
    }
    
    public psc_e[] k() {
        return this.k;
    }
    
    public psc_dw l() {
        return this.i;
    }
    
    public void m() {
        for (int i = 0; i < this.e.length; ++i) {
            this.e[i] = 0;
        }
        for (int j = 0; j < this.a.length; ++j) {
            this.a[j] = 0;
        }
        this.f = 0L;
    }
}
