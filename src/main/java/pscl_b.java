import java.io.OutputStream;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.PrintStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_b
{
    public static final int a = 2;
    public static final int b = 768;
    public static final int c = 769;
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 4;
    private int g;
    private PrintStream h;
    private pscl_i[] i;
    private String j;
    private int[] k;
    private int l;
    public static final int m = 0;
    public static final int n = 1;
    public static final int o = 2;
    private Hashtable p;
    private Hashtable q;
    private long r;
    private int s;
    private boolean t;
    private Hashtable u;
    private pscl_e v;
    private pscl_g w;
    private boolean x;
    
    public pscl_b() throws pscl_h {
        this.j = "Java";
        this.k = new int[] { 768 };
        this.l = 0;
        this.r = 120000L;
        this.s = 16384;
        this.t = true;
        this.x = false;
        this.p = new Hashtable();
        this.u = new Hashtable();
        this.q = new Hashtable();
        this.g = 0;
        this.h = System.out;
        this.v = new pscl_e(new pscl_c());
        this.w = new pscl_f();
    }
    
    public synchronized void a(final pscl_i[] i) throws pscl_h {
        this.i = i;
        i[0].a(this.j);
    }
    
    public pscl_i[] a() throws pscl_h {
        if (this.i == null) {
            (this.i = new pscl_i[1])[0] = new pscl_m();
        }
        return this.i;
    }
    
    public synchronized void a(final String s) throws pscl_h {
    }
    
    public String b() {
        return "Java";
    }
    
    public void a(final byte[][] array) throws pscl_h {
        for (int i = 0; i < array.length; ++i) {
            try {
                final pscl_j value = new pscl_j(array[i], 0, 0);
                try {
                    this.p.put(pscl_k.a(value.a()), value);
                }
                catch (Exception ex) {
                    if ((this.g & 0x4) == 0x4) {
                        this.h.println("Could not load certificate " + i);
                    }
                }
            }
            catch (Exception ex2) {
                if ((this.g & 0x4) == 0x4) {
                    this.h.println("Could not instantiate certificate number " + i);
                }
            }
        }
    }
    
    public void a(final pscl_j[] array) {
        for (int i = 0; i < array.length; ++i) {
            try {
                this.p.put(pscl_k.a(array[i].a()), array[i]);
            }
            catch (Exception ex) {
                if ((this.g & 0x4) == 0x4) {
                    this.h.println("Could not load certificate " + i);
                }
            }
        }
    }
    
    public void a(final byte[] array) throws pscl_h {
        try {
            final pscl_j value = new pscl_j(array, 0, 0);
            this.p.put(pscl_k.a(value.a()), value);
        }
        catch (Exception ex) {
            throw new pscl_h("Could not instantiate certificate");
        }
    }
    
    public void a(final pscl_j value) throws pscl_h {
        try {
            this.p.put(pscl_k.a(value.a()), value);
        }
        catch (Exception ex) {
            throw new pscl_h("Could not instantiate certificate");
        }
    }
    
    public void b(final byte[] array) throws pscl_h {
        try {
            this.b(new pscl_j(array, 0, 0));
        }
        catch (Exception ex) {
            throw new pscl_h("Could not understand given cert to delete");
        }
    }
    
    public void b(final pscl_j pscl_j) throws pscl_h {
        try {
            final String a = pscl_k.a(pscl_j.a());
            if (!this.p.containsKey(a)) {
                throw new pscl_h("Can not find the certificate in the list of CA certs");
            }
            this.p.remove(a);
        }
        catch (Exception ex) {
            throw new pscl_h("Could not understand the certificate");
        }
    }
    
    public pscl_j[] c() {
        final pscl_j[] array = new pscl_j[this.p.size()];
        final Enumeration<pscl_j> elements = (Enumeration<pscl_j>)this.p.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            array[n] = elements.nextElement();
            ++n;
        }
        return array;
    }
    
    public synchronized void a(final long r) throws pscl_h {
        this.r = r;
    }
    
    public synchronized long d() {
        return this.r;
    }
    
    public synchronized void b(final long n) throws pscl_h {
        if (n <= 0L) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final Vector<String> vector = new Vector<String>();
        final Enumeration<String> keys = this.q.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            if (currentTimeMillis - ((pscl_l)this.q.get(s)).b() >= n) {
                vector.addElement(s);
            }
        }
        final Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            this.q.remove(elements.nextElement());
        }
    }
    
    public void a(final pscl_l pscl_l) {
        if (pscl_l == null) {
            return;
        }
        this.c(pscl_l.e());
    }
    
    public void c(final byte[] array) {
        if (array == null) {
            return;
        }
        synchronized (this.q) {
            this.q.remove(pscl_k.a(array));
        }
    }
    
    public synchronized void b(final pscl_l value) throws pscl_h {
        if (this.r < 0L) {
            return;
        }
        this.b(this.r);
        if (value != null) {
            value.a(System.currentTimeMillis());
            this.q.put(value.f(), value);
            return;
        }
        throw new pscl_h("Session to be cached is null");
    }
    
    public pscl_l d(final byte[] array) throws pscl_h {
        this.b(this.r);
        if (array == null) {
            return null;
        }
        return this.q.get(pscl_k.a(array));
    }
    
    public pscl_l b(final String host) throws pscl_h {
        String hostAddress;
        try {
            hostAddress = InetAddress.getByName(host).getHostAddress();
        }
        catch (UnknownHostException ex) {
            throw new pscl_h(ex.getMessage());
        }
        return this.c(hostAddress);
    }
    
    public pscl_l c(final String s) throws pscl_h {
        this.b(this.r);
        if (s == null) {
            return null;
        }
        synchronized (this.q) {
            final Enumeration<pscl_l> elements = this.q.elements();
            while (elements.hasMoreElements()) {
                final pscl_l pscl_l = elements.nextElement();
                if (pscl_l.h().toLowerCase().equals(s.toLowerCase())) {
                    return pscl_l;
                }
            }
            return null;
        }
    }
    
    public void a(final int[] array) throws pscl_h {
        System.arraycopy(array, 0, this.k = new int[array.length], 0, array.length);
    }
    
    public int[] e() {
        return this.k;
    }
    
    public void a(final int s) throws pscl_h {
        if (s < 1) {
            throw new pscl_h("Invalid packet size");
        }
        if ((this.s = s) < 16384) {
            throw new pscl_h("packet size is too small");
        }
    }
    
    public int f() {
        return this.s;
    }
    
    public void a(final boolean t) {
        this.t = t;
    }
    
    public boolean g() {
        return this.t;
    }
    
    public void a(final byte[][] array, final byte[] array2, final char[] array3) throws pscl_h {
        final pscl_j[] array4 = new pscl_j[array.length];
        for (int i = 0; i < array4.length; ++i) {
            try {
                array4[i] = new pscl_j(array[i], 0, 0);
            }
            catch (Exception ex) {
                throw new pscl_h("Could not load certificate " + i);
            }
        }
        this.a(array4, array2, array3);
    }
    
    public void a(final pscl_j[] array, final byte[] array2, final char[] array3) throws pscl_h {
        try {
            final pscl_bg pscl_bg = new pscl_bg("SHA1", "PKCS12PBE");
            final int[] a = pscl_bj.a(array2, 0, pscl_bg.f());
            final int n = a[0];
            final int n2 = a[1];
            pscl_bg.b(array3, null);
            final byte[] array4 = new byte[pscl_bg.a(n2)];
            pscl_bg.b(array2, n, n2, array4, 0);
            this.a(array, new pscl_bl(array4, 0));
        }
        catch (Exception ex) {
            throw new pscl_h("Could not create the private key: " + ex.getMessage());
        }
    }
    
    public void a(final pscl_j[] array, final pscl_bm pscl_bm) throws pscl_h {
        try {
            this.u.put(pscl_k.a(array[0].a()), new pscl_a2(array, (pscl_bl)pscl_bm));
        }
        catch (Exception ex) {
            throw new pscl_h("Could not parse certificate");
        }
    }
    
    public pscl_j[][] h() {
        final Enumeration<pscl_a2> elements = this.u.elements();
        final pscl_j[][] array = new pscl_j[this.u.size()][];
        for (int i = 0; i < array.length; ++i) {
            array[i] = elements.nextElement().a();
        }
        return array;
    }
    
    public pscl_j[] a(final pscl_j[][] array, final pscl_i pscl_i) throws pscl_h {
        return array[0];
    }
    
    public pscl_a2 b(final pscl_j[] array) {
        try {
            return this.u.get(pscl_k.a(array[0].a()));
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public pscl_e i() {
        return this.v;
    }
    
    public void e(final byte[] array) {
        this.v.a(array);
    }
    
    public pscl_e j() throws pscl_h {
        try {
            final pscl_e pscl_e = new pscl_e(new pscl_c());
            final byte[] array = new byte[40];
            this.v.b(array);
            pscl_e.a(array);
            return pscl_e;
        }
        catch (Exception ex) {
            throw new pscl_h("Not able to instantiate a new random with algorithm SHA1Random and device " + this.j);
        }
    }
    
    public void b(final int l) throws pscl_h {
        this.l = l;
    }
    
    public int k() {
        return this.l;
    }
    
    public char[] c(final pscl_j[] array) {
        return new char[0];
    }
    
    public void a(final pscl_g w) {
        this.w = w;
    }
    
    public pscl_g l() {
        return this.w;
    }
    
    public void b(final boolean x) {
        this.x = x;
    }
    
    public boolean m() {
        return this.x;
    }
    
    public void c(final int g) {
        this.g = g;
    }
    
    public int n() {
        return this.g;
    }
    
    public void a(final OutputStream out) {
        if (out instanceof PrintStream) {
            this.h = (PrintStream)out;
        }
        else {
            this.h = new PrintStream(out);
        }
    }
    
    public PrintStream o() {
        return this.h;
    }
    
    public Enumeration p() throws pscl_h {
        this.b(this.r);
        final Hashtable hashtable;
        synchronized (this.q) {
            hashtable = (Hashtable)this.q.clone();
        }
        final Vector vector = new Vector<byte[]>(hashtable.size());
        final Enumeration<pscl_l> elements = hashtable.elements();
        while (elements.hasMoreElements()) {
            vector.addElement(elements.nextElement().e());
        }
        return vector.elements();
    }
}
