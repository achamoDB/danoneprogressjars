import java.io.File;
import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ah
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    private static final int f = 0;
    private static final int g = 4;
    public static final int h = 0;
    public static final int i = 1;
    public static final String j = "2.1.3.0";
    private static String k;
    private Vector[] l;
    private String m;
    private psc_nf[] n;
    private static Vector o;
    private static boolean p;
    public static final int q = 0;
    public static final int r = 1;
    public static final int s = 2;
    public static final int t = 3;
    public static final int u = 0;
    public static final int v = 1;
    public static final int w = 0;
    public static final int x = 1;
    
    public void a(final psc_nf[] n) {
        this.n = n;
    }
    
    public psc_nf[] a() {
        return this.n;
    }
    
    public static String b() {
        return psc_ah.k;
    }
    
    public static void a(final String k) {
        psc_ah.k = k;
    }
    
    public psc_ah() throws psc_d9, psc_d5 {
        this(null);
    }
    
    public psc_ah(final psc_d8[] array) throws psc_d9, psc_d5 {
        this.l = new Vector[5];
        this.n = null;
        g();
        this.m = psc_ah.k;
        for (int i = 0; i <= 4; ++i) {
            this.l[i] = new Vector();
        }
        try {
            if (array != null) {
                for (int j = 0; j < array.length; ++j) {
                    this.a(array[j], 1);
                }
            }
            if (this.l[0].size() == 0) {
                this.a(new psc_ee("Default Random"), 1);
            }
        }
        catch (psc_d7 psc_d7) {
            this.c();
            throw new psc_d9("CertJ.CertJ: " + psc_d7.getMessage());
        }
        catch (psc_d9 psc_d8) {
            this.c();
            throw psc_d8;
        }
    }
    
    public void c() {
        for (int i = 0; i <= 4; ++i) {
            final Vector vector = this.l[i];
            for (int j = 0; j < vector.size(); ++j) {
                try {
                    this.a(i, vector.elementAt(j).b());
                }
                catch (Exception ex) {}
            }
            vector.removeAllElements();
        }
    }
    
    public void a(final psc_d8 psc_d8) throws psc_d7, psc_d9 {
        this.a(psc_d8, 1);
    }
    
    public void a(final psc_d8 psc_d8, final int n) throws psc_d7, psc_d9 {
        if (psc_d8 == null) {
            throw new psc_d7("CertJ.registerService: provider can not be null.");
        }
        if (n != 1 && n != 0) {
            throw new psc_d7("CertJ.registerService: order should be either SERVICE_ORDER_LAST or SERVICE_ORDER_FIRST.");
        }
        if (psc_d8.b() == null) {
            throw new psc_d9("CertJ.registerService: provider has to have a name.");
        }
        final int a = psc_d8.a();
        if (a < 0 || a > 4) {
            throw new psc_d9("CertJ.registerService: service type(" + a + ") of the provider is not between " + 0 + " and " + 4 + ".");
        }
        if (a == 0) {
            if (this.l[0] != null && this.l[0].size() != 0) {
                throw new psc_d9("CertJ.registerService: a random service is already registered. Do unregister it first.");
            }
        }
        else {
            psc_eb c = null;
            try {
                c = this.c(psc_d8.a(), psc_d8.b());
            }
            catch (Exception ex) {}
            if (c != null) {
                this.a(c);
                throw new psc_d9("CertJ.registerService: " + c(psc_d8.a()) + " service named " + psc_d8.b() + " is already registered.");
            }
        }
        final psc_ea a2 = psc_d8.a(this);
        final Vector vector = this.l[a];
        switch (n) {
            case 0: {
                vector.insertElementAt(a2, 0);
                break;
            }
            case 1: {
                vector.addElement(a2);
                break;
            }
        }
    }
    
    public void a(final int i, final String anObject) throws psc_d7 {
        if (i < 0 || i > 4) {
            throw new psc_d7("CertJ.unregisterService: type (" + i + ") is not between SPT_FIRST and SPT_LAST.");
        }
        if (anObject == null) {
            throw new psc_d7("CertJ.unregisterService: name should not be null.");
        }
        final Vector vector = this.l[i];
        for (int j = 0; j < vector.size(); ++j) {
            final psc_ea psc_ea = vector.elementAt(j);
            if (psc_ea.b().equals(anObject)) {
                psc_ea.c();
                vector.removeElementAt(j);
                break;
            }
        }
    }
    
    public void b(final psc_d8 psc_d8) throws psc_d7, psc_d9 {
        this.a(psc_d8);
    }
    
    public void b(final psc_d8 psc_d8, final int n) throws psc_d7, psc_d9 {
        this.a(psc_d8, n);
    }
    
    public void b(final int n, final String s) throws psc_d7 {
        this.a(n, s);
    }
    
    public psc_eb c(final int n, final String s) throws psc_d7, psc_d9 {
        final String[] array = { null };
        if (s == null) {
            final Vector vector = this.l[n];
            if (vector.size() == 0) {
                throw new psc_d9("CertJ.bindService: no provider is registered for type" + c(n));
            }
            array[0] = vector.elementAt(0).b();
        }
        else {
            array[0] = s;
        }
        return this.a(n, array);
    }
    
    public psc_eb a(final int i, final String[] array) throws psc_d7, psc_d9 {
        if (i < 0 || i > 4) {
            throw new psc_d7("CertJ.bindServices: type (" + i + ") is not between SPT_FIRST and SPT_LAST.");
        }
        if (array == null) {
            return this.a(i);
        }
        final Vector vector = this.l[i];
        final psc_eb a = psc_eb.a(this, i);
        for (int j = 0; j < array.length; ++j) {
            final String s = array[j];
            boolean b = false;
            for (int k = 0; k < vector.size(); ++k) {
                final psc_ea psc_ea = vector.elementAt(k);
                if (s.equals(psc_ea.b())) {
                    a.a(psc_ea);
                    b = true;
                    break;
                }
            }
            if (!b) {
                throw new psc_d9("CertJ.bindServices: provider of type: " + c(i) + " with name: " + array[j] + " not found.");
            }
        }
        return a;
    }
    
    public psc_eb a(final int i) throws psc_d7, psc_d9 {
        if (i < 0 || i > 4) {
            throw new psc_d7("CertJ.bindServices: type (" + i + ") is not between SPT_FIRST and SPT_LAST.");
        }
        final psc_eb a = psc_eb.a(this, i);
        final Vector vector = this.l[i];
        if (vector.size() == 0) {
            throw new psc_d9("CertJ.bindServices: no providere is registered for type: " + c(i));
        }
        for (int j = 0; j < vector.size(); ++j) {
            a.a(vector.elementAt(j));
        }
        return a;
    }
    
    public void a(final psc_eb psc_eb) {
        psc_eb.l();
    }
    
    public String[] d() {
        final Vector vector = new Vector<String>();
        for (int i = 0; i <= 4; ++i) {
            final Vector vector2 = this.l[i];
            for (int j = 0; j < vector2.size(); ++j) {
                vector.addElement(vector2.elementAt(j).toString());
            }
        }
        final String[] array = new String[vector.size()];
        for (int k = 0; k < vector.size(); ++k) {
            array[k] = vector.elementAt(k);
        }
        return array;
    }
    
    public String[] b(final int n) {
        final Vector vector = this.l[n];
        final Vector vector2 = new Vector<String>();
        for (int i = 0; i < vector.size(); ++i) {
            vector2.addElement(vector.elementAt(i).b());
        }
        final String[] array = new String[vector2.size()];
        for (int j = 0; j < vector2.size(); ++j) {
            array[j] = vector2.elementAt(j);
        }
        return array;
    }
    
    public psc_av e() throws psc_mv, psc_n5 {
        if (this.l[0].size() == 0) {
            throw new psc_mv("CertJ.getRandomObject: no random service is registered.");
        }
        final psc_iz psc_iz = this.l[0].elementAt(0);
        try {
            return psc_iz.d();
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("CertJ.getRandomObject: " + psc_n4.getMessage());
        }
    }
    
    public String f() {
        return this.m;
    }
    
    public void b(final String m) {
        this.m = m;
    }
    
    public boolean a(final psc_nq psc_nq, final Object o) throws psc_d7, psc_mv, psc_n6 {
        return this.a(psc_nq, o, null, null, null, null);
    }
    
    public boolean a(final psc_nq psc_nq, final Object o, final Vector vector, final Vector vector2, final Vector vector3, final Vector vector4) throws psc_d7, psc_mv, psc_n6 {
        if (psc_nq == null) {
            throw new psc_d7("CertJ.buildCertPath: pathCtx should not be null.");
        }
        if (!(o instanceof psc_f) && !(o instanceof psc_na)) {
            throw new psc_d7("CertJ.buildCertPath: startObject should be either Certificate or CRL.");
        }
        final Vector vector5 = this.l[3];
        int i = 0;
        while (i < vector5.size()) {
            try {
                return vector5.elementAt(i).a(psc_nq, o, vector, vector2, vector3, vector4);
            }
            catch (psc_n4 psc_n4) {
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("CertJ.buildCertPath: no provider is found to handle this method.");
    }
    
    public void a(final psc_nq psc_nq, final Object o, final Vector vector) throws psc_d7, psc_mv, psc_n6 {
        if (psc_nq == null) {
            throw new psc_d7("CertJ.getNextCertInPath: pathCtx should not be null.");
        }
        if (!(o instanceof psc_f) && !(o instanceof psc_na)) {
            throw new psc_d7("CertJ.getNextCertInPath: baseObject should be either Certificate or CRL.");
        }
        final Vector vector2 = this.l[3];
        int i = 0;
        while (i < vector2.size()) {
            try {
                vector2.elementAt(i).a(psc_nq, o, vector);
                return;
            }
            catch (psc_n4 psc_n4) {
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("CertJ.getNextCertInPath: no provider is found to handle this method.");
    }
    
    public boolean a(final psc_nq psc_nq, final psc_f psc_f, final psc_al psc_al) throws psc_d7, psc_mv, psc_n6 {
        if (psc_nq == null) {
            throw new psc_d7("CertJ.validateCertificate: pathCtx should not be null.");
        }
        if (psc_f == null) {
            throw new psc_d7("CertJ.validateCertificate: cert should not be null.");
        }
        if (psc_al == null) {
            throw new psc_d7("CertJ.validateCertificate: validationKey should not be null.");
        }
        final Vector vector = this.l[3];
        int i = 0;
        while (i < vector.size()) {
            try {
                return vector.elementAt(i).a(psc_nq, psc_f, psc_al);
            }
            catch (psc_n4 psc_n4) {
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("CertJ.validateCertificate: no provider is found to handle this method.");
    }
    
    public psc_my a(final psc_nq psc_nq, final psc_f psc_f) throws psc_d7, psc_mv, psc_n7 {
        if (psc_nq == null) {
            throw new psc_d7("CertJ.checkCertRevocation: pathCtx should not be null.");
        }
        if (psc_f == null) {
            throw new psc_d7("CertJ.checkCertRevocation: cert should not be null.");
        }
        final Vector vector = this.l[2];
        psc_my psc_my = null;
        for (int i = 0; i < vector.size(); ++i) {
            try {
                final psc_my a = vector.elementAt(i).a(psc_nq, psc_f);
                if (a.a() != 2) {
                    return a;
                }
                if (psc_my == null) {
                    psc_my = a;
                }
            }
            catch (psc_n4 psc_n4) {}
        }
        if (psc_my != null) {
            return psc_my;
        }
        throw new psc_mv("CertJ.checkCertRevocation: no provider is found to handle this method or no registered provider can determine the revocation status of the given certificate.");
    }
    
    public void a(final String s, final char[] array, final psc_ed psc_ed) throws psc_m0 {
        new psc_mz(this, psc_ed, array, s);
    }
    
    public void a(final File file, final char[] array, final psc_ed psc_ed) throws psc_m0 {
        new psc_mz(this, psc_ed, array, file);
    }
    
    public static String c(final int i) {
        switch (i) {
            case 0: {
                return "SPT_RANDOM";
            }
            case 1: {
                return "SPT_DATABASE";
            }
            case 2: {
                return "SPT_CERT_STATUS";
            }
            case 3: {
                return "SPT_CERT_PATH";
            }
            case 4: {
                return "SPT_PKI";
            }
            default: {
                return "Unknown service type: " + i;
            }
        }
    }
    
    public static void a(final psc_nz psc_nz) {
        synchronized (psc_ah.o) {
            if (!psc_ah.o.contains(psc_nz)) {
                psc_ah.o.addElement(psc_nz);
            }
        }
    }
    
    public static void b(final psc_nz obj) {
        synchronized (psc_ah.o) {
            psc_ah.o.removeElement(obj);
        }
    }
    
    public static boolean c(final psc_nz o) {
        final boolean contains;
        synchronized (psc_ah.o) {
            contains = psc_ah.o.contains(o);
        }
        return contains;
    }
    
    public static final boolean g() throws psc_d5 {
        final boolean i = psc_aq.i();
        if ((i && !psc_ah.p) || (!i && psc_ah.p)) {
            throw new psc_d5("Attempt to mix FIPS compliant and non-complaint versions of Cert-J and Crypto-J");
        }
        return psc_ah.p;
    }
    
    public static int h() {
        return psc_aq.j();
    }
    
    public static int i() {
        return psc_aq.k();
    }
    
    public static void d(final int n) throws psc_d5 {
        try {
            psc_aq.b(n);
        }
        catch (psc_en psc_en) {
            throw new psc_d5(psc_en.getMessage());
        }
    }
    
    public static int j() {
        return psc_aq.l();
    }
    
    public static void e(final int n) throws psc_d5 {
        try {
            psc_aq.c(n);
        }
        catch (psc_en psc_en) {
            throw new psc_d5(psc_en.getMessage());
        }
    }
    
    public static boolean k() {
        return psc_aq.m();
    }
    
    public static synchronized boolean l() throws psc_d5 {
        try {
            return psc_aq.n();
        }
        catch (psc_en psc_en) {
            throw new psc_d5(psc_en.getMessage());
        }
    }
    
    static {
        psc_ah.k = "Native/Java";
        psc_ah.o = new Vector(1);
        psc_ah.p = false;
    }
}
