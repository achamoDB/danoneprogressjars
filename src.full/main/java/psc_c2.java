import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.PrintStream;
import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c2
{
    public static final int a = 2;
    public static final int b = 768;
    public static final int c = 769;
    public static final int d = 0;
    public static final int e = 1;
    public static final int f = 2;
    public static final int g = 4;
    public static final int h = 0;
    public static final int i = 1;
    public static final int j = 2;
    private Vector k;
    private int l;
    private PrintStream m;
    private psc_dw[] n;
    private psc_dw[] o;
    private boolean p;
    private psc_c3 q;
    private psc_nf[] r;
    private psc_c5[] s;
    private int[] t;
    private int u;
    private Hashtable v;
    private Hashtable w;
    private long x;
    private int y;
    private boolean z;
    private Vector aa;
    private psc_av ab;
    private psc_c8 ac;
    private boolean ad;
    
    public psc_c2() throws psc_d {
        this.k = new Vector(1);
        this.n = new psc_dw[0];
        this.p = false;
        this.q = new psc_c3();
        this.r = new psc_nf[0];
        this.s = new psc_c5[] { new psc_c4() };
        this.t = new int[] { 2, 768, 769 };
        this.u = 0;
        this.x = 120000L;
        this.y = 16384;
        this.z = true;
        this.ad = false;
        this.v = new Hashtable();
        this.aa = new Vector();
        this.w = new Hashtable();
        this.l = 0;
        this.m = System.out;
        this.a();
        this.ac = new psc_c7();
    }
    
    private void a() throws psc_d {
        this.ab = this.r();
    }
    
    public synchronized void a(final psc_dw[] array) throws psc_d {
        if (array == null || array.length == 0) {
            throw new psc_d("null/empty cipher suites parameter not allowed");
        }
        this.p = false;
        this.n = array.clone();
    }
    
    public synchronized psc_dw[] c() throws psc_d {
        if (this.n == null || this.n.length == 0) {
            throw new psc_d("no cipher suites have been set yet");
        }
        return this.n;
    }
    
    public synchronized psc_dw[] d() throws psc_d {
        if (!this.p) {
            this.b();
            this.p = true;
        }
        return this.o;
    }
    
    private void b() throws psc_d {
        this.c();
        final Vector vector = new Vector<psc_dw>(this.n.length);
        for (int i = 0; i < this.n.length; ++i) {
            if (this.n[i].l() || this.a(this.n[i]) != null) {
                vector.add(this.n[i]);
            }
        }
        final int size = vector.size();
        if (size == 0) {
            throw new psc_d("Server certificates and cipher suites do not match.");
        }
        this.o = new psc_dw[size];
        for (int j = 0; j < size; ++j) {
            this.o[j] = vector.get(j);
        }
    }
    
    public String e() {
        return this.q.a();
    }
    
    public synchronized void a(final String s) throws psc_d {
        this.a(new psc_c3(s));
    }
    
    public psc_c3 f() {
        return this.q;
    }
    
    public synchronized void a(final psc_c3 q) throws psc_d {
        this.q = q;
        final String a = this.q.a(psc_n0.i);
        for (int i = 0; i < this.n.length; ++i) {
            this.n[i].a(q);
        }
        if (!this.q.a(psc_n0.i).equals(a)) {
            this.a();
        }
    }
    
    public synchronized psc_nf[] g() {
        return this.r;
    }
    
    public synchronized void a(final psc_nf[] array) {
        this.r = array.clone();
        for (int i = 0; i < this.n.length; ++i) {
            this.n[i].a(array);
        }
    }
    
    public synchronized void a(final psc_ng[] array) throws psc_d {
        final int length = array.length;
        final psc_nf[] array2 = new psc_nf[length];
        try {
            for (int i = 0; i < length; ++i) {
                array2[i] = psc_nf.b(array[i]);
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("Unable to create device sessions: " + psc_ap.getMessage());
        }
    }
    
    public synchronized void h() {
        for (int i = 0; i < this.n.length; ++i) {
            this.n[i].p();
        }
    }
    
    public void a(final psc_c5[] s) {
        this.s = s;
    }
    
    public psc_c5[] i() {
        return this.s;
    }
    
    public void a(final byte[][] array) throws psc_d {
        if (!this.v.isEmpty()) {
            this.v.clear();
        }
        for (int i = 0; i < array.length; ++i) {
            psc_e value;
            try {
                value = new psc_e(array[i], 0, 0);
            }
            catch (psc_g psc_g) {
                this.a("instantiate", i, psc_g, true);
                continue;
            }
            try {
                this.a(value);
                this.v.put(psc_c9.a(value.i()), value);
            }
            catch (psc_g psc_g2) {
                this.a("load", i, psc_g2, true);
            }
        }
    }
    
    public void b(final psc_e[] array) throws psc_d {
        if (!this.v.isEmpty()) {
            this.v.clear();
        }
        for (int i = 0; i < array.length; ++i) {
            try {
                this.a(array[i]);
                this.v.put(psc_c9.a(array[i].i()), array[i]);
            }
            catch (psc_g psc_g) {
                this.a("load", i, psc_g, true);
            }
        }
    }
    
    public void a(final byte[] array) throws psc_d {
        psc_e value = null;
        try {
            value = new psc_e(array, 0, 0);
        }
        catch (psc_g psc_g) {
            this.a("instantiate", -1, psc_g, false);
        }
        try {
            this.a(value);
            this.v.put(psc_c9.a(value.i()), value);
        }
        catch (psc_g psc_g2) {
            this.a("load", -1, psc_g2, false);
        }
    }
    
    public void b(final psc_e value) throws psc_d {
        try {
            this.a(value);
            this.v.put(psc_c9.a(value.i()), value);
        }
        catch (psc_g psc_g) {
            this.a("load", -1, psc_g, false);
        }
    }
    
    public void b(final byte[] array) throws psc_d {
        try {
            this.c(new psc_e(array, 0, 0));
        }
        catch (psc_g psc_g) {
            throw new psc_d("Could not understand given cert to delete");
        }
    }
    
    public void c(final psc_e psc_e) throws psc_d {
        try {
            final String a = psc_c9.a(psc_e.i());
            if (!this.v.containsKey(a)) {
                throw new psc_d("Can not find the certificate in the list of CA certs");
            }
            this.v.remove(a);
        }
        catch (psc_g psc_g) {
            throw new psc_d("Could not understand the certificate");
        }
    }
    
    public psc_e[] j() {
        final psc_e[] array = new psc_e[this.v.size()];
        final Enumeration<psc_e> elements = (Enumeration<psc_e>)this.v.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            array[n] = elements.nextElement();
            ++n;
        }
        return array;
    }
    
    public synchronized void a(final long x) throws psc_d {
        this.x = x;
    }
    
    public synchronized long k() {
        return this.x;
    }
    
    public synchronized void b(final long n) throws psc_d {
        if (n <= 0L) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final Vector<String> vector = new Vector<String>();
        final Enumeration<String> keys = this.w.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            if (currentTimeMillis - ((psc_d2)this.w.get(s)).a() >= n) {
                vector.addElement(s);
            }
        }
        final Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            this.w.remove(elements.nextElement());
        }
    }
    
    public void a(final psc_d2 psc_d2) {
        if (psc_d2 == null) {
            return;
        }
        this.c(psc_d2.e());
    }
    
    public void c(final byte[] array) {
        if (array == null) {
            return;
        }
        synchronized (this.w) {
            this.w.remove(psc_c9.a(array));
        }
    }
    
    public synchronized void b(final psc_d2 value) throws psc_d {
        if (this.x >= 0L) {
            this.b(this.x);
        }
        if (value != null && this.x != 0L) {
            value.a(System.currentTimeMillis());
            this.w.put(value.f(), value);
        }
        else if (this.x != 0L) {
            throw new psc_d("Session to be cached is null");
        }
    }
    
    public psc_d2 d(final byte[] array) throws psc_d {
        this.b(this.x);
        if (array == null) {
            return null;
        }
        synchronized (this.w) {
            return this.w.get(psc_c9.a(array));
        }
    }
    
    public psc_d2 b(final String host) throws psc_d {
        String hostAddress;
        try {
            hostAddress = InetAddress.getByName(host).getHostAddress();
        }
        catch (UnknownHostException ex) {
            throw new psc_d(ex.getMessage());
        }
        return this.c(hostAddress);
    }
    
    public psc_d2 c(final String s) throws psc_d {
        this.b(this.x);
        if (s == null) {
            return null;
        }
        synchronized (this.w) {
            final Enumeration<psc_d2> elements = this.w.elements();
            while (elements.hasMoreElements()) {
                final psc_d2 psc_d2 = elements.nextElement();
                if (psc_d2.h().toLowerCase().equals(s.toLowerCase())) {
                    return psc_d2;
                }
            }
            return null;
        }
    }
    
    public Enumeration l() throws psc_d {
        this.b(this.x);
        final Hashtable hashtable;
        synchronized (this.w) {
            hashtable = (Hashtable)this.w.clone();
        }
        final Vector vector = new Vector<byte[]>(hashtable.size());
        final Enumeration<psc_d2> elements = hashtable.elements();
        while (elements.hasMoreElements()) {
            vector.addElement(elements.nextElement().e());
        }
        return vector.elements();
    }
    
    public void a(final int[] array) throws psc_d {
        if (array == null) {
            throw new psc_d("versions provided is null");
        }
        if (array.length == 0) {
            throw new psc_d("versions provided is zero length");
        }
        for (int i = 0; i < array.length; ++i) {
            final int n = array[i];
            if (n != 2 && n != 768 && n != 769) {
                throw new psc_d("versions provided has unknown values");
            }
        }
        System.arraycopy(array, 0, this.t = new int[array.length], 0, array.length);
    }
    
    public int[] m() {
        return this.t;
    }
    
    public void a(final int y) throws psc_d {
        if (y < 1) {
            throw new psc_d("Invalid packet size");
        }
        if ((this.y = y) < 16384) {
            throw new psc_d("packet size is too small");
        }
    }
    
    public int n() {
        return this.y;
    }
    
    public void a(final boolean z) {
        this.z = z;
    }
    
    public boolean o() {
        return this.z;
    }
    
    public void a(final byte[][] array, final byte[] array2, final char[] array3) throws psc_d {
        final psc_e[] array4 = new psc_e[array.length];
        for (int i = 0; i < array4.length; ++i) {
            try {
                array4[i] = new psc_e(array[i], 0, 0);
            }
            catch (psc_g psc_g) {
                this.a("instantiate", i, psc_g, false);
            }
        }
        this.a(array4, array2, array3);
    }
    
    public synchronized void a(final psc_e[] array, final psc_dt psc_dt) throws psc_d {
        this.p = false;
        this.a(array);
        this.aa.addElement(new psc_ds(array, psc_dt));
    }
    
    public synchronized void a(final psc_e[] array, final byte[] array2, final char[] array3) throws psc_d {
        this.p = false;
        this.a(array);
        this.aa.addElement(new psc_ds(array, array2, array3));
    }
    
    public psc_ds[] p() {
        final psc_ds[] anArray = new psc_ds[this.aa.size()];
        this.aa.copyInto(anArray);
        return anArray;
    }
    
    public psc_ds a(final psc_dw psc_dw) {
        String anObject;
        String anObject2;
        if (!psc_dw.m()) {
            anObject = psc_dw.x();
            anObject2 = psc_dw.z();
        }
        else {
            anObject = psc_dw.z();
            anObject2 = psc_dw.z();
        }
        for (int i = 0; i < this.aa.size(); ++i) {
            final psc_ds psc_ds = this.aa.elementAt(i);
            final String l = psc_ds.l();
            if (psc_ds.k().equals(anObject) && l.equals(anObject2)) {
                return psc_ds;
            }
        }
        return null;
    }
    
    public psc_av q() {
        return this.ab;
    }
    
    public void e(final byte[] array) {
        this.ab.g(array);
    }
    
    public psc_av r() throws psc_d {
        byte[] array = null;
        if (this.ab != null) {
            array = new byte[40];
            this.ab.nextBytes(array);
        }
        final String a = this.q.a(psc_n0.i);
        psc_av psc_av;
        try {
            psc_av = (psc_av)psc_av.getInstance("SHA1Random", a);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new psc_d("unable to instantiate a new random with algorithm SHA1Random and device " + a + ": " + ex.getMessage());
        }
        if (array != null) {
            psc_av.g(array);
        }
        return psc_av;
    }
    
    public void b(final int n) throws psc_d {
        if (n != 0 && n != 1 && n != 2) {
            throw new psc_d("the value must be either SSLParams.CLIENT_AUTH_NONE, SSLParams.CLIENT_AUTH_REQUESTEDor SSLParams.CLIENT_AUTH_REQUIRED, but the value passed was<" + n + ">");
        }
        this.u = n;
    }
    
    public int s() {
        return this.u;
    }
    
    public char[] c(final psc_e[] array) {
        return new char[0];
    }
    
    public void a(final psc_c8 ac) {
        this.ac = ac;
    }
    
    public psc_c8 t() {
        return this.ac;
    }
    
    public void b(final boolean ad) {
        this.ad = ad;
    }
    
    public boolean u() {
        return this.ad;
    }
    
    public void c(final int l) {
        this.l = l;
    }
    
    public int v() {
        return this.l;
    }
    
    public boolean w() {
        return (this.l & 0x1) == 0x1;
    }
    
    public boolean x() {
        return (this.l & 0x2) == 0x2;
    }
    
    public boolean y() {
        return (this.l & 0x4) == 0x4;
    }
    
    public void a(final OutputStream out) {
        if (out instanceof PrintStream) {
            this.m = (PrintStream)out;
        }
        else {
            this.m = new PrintStream(out);
        }
    }
    
    public PrintStream z() {
        return this.m;
    }
    
    public void a(final psc_n1 psc_n1) {
        if (!this.k.contains(psc_n1)) {
            this.k.addElement(psc_n1);
        }
    }
    
    public void b(final psc_n1 psc_n1) {
        if (this.k.contains(psc_n1)) {
            this.k.removeElement(psc_n1);
        }
    }
    
    public boolean c(final psc_n1 o) {
        return this.k.contains(o);
    }
    
    private void a(final psc_e psc_e) throws psc_g {
        if (psc_c7.b(psc_e, this)) {
            throw new psc_g("Weak public key detected");
        }
    }
    
    private void a(final String str, final int i, final Exception ex, final boolean b) throws psc_d {
        final StringBuffer sb = new StringBuffer();
        sb.append("Could not ");
        sb.append(str);
        sb.append(" certificate ");
        if (i != -1) {
            sb.append(i);
        }
        sb.append(": ");
        sb.append(ex.getMessage());
        if (this.y()) {
            this.m.println(sb.toString());
        }
        if (!b) {
            throw new psc_d(sb.toString());
        }
    }
    
    private void a(final psc_e[] array) throws psc_d {
        int i = -1;
        try {
            for (i = 0; i < array.length; ++i) {
                this.a(array[i]);
            }
        }
        catch (psc_g psc_g) {
            this.a("load", i, psc_g, false);
        }
    }
}
