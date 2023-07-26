import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ar
{
    static final byte[] a;
    static final byte[] b;
    static final byte[] c;
    private static psc_ar d;
    private static final boolean e = false;
    protected Vector f;
    
    private psc_ar() {
        this.f = new Vector();
    }
    
    public static psc_ar a() {
        if (psc_ar.d == null) {
            psc_ar.d = new psc_ar();
        }
        return psc_ar.d;
    }
    
    public static boolean a(final psc_al psc_al, final psc_dt psc_dt) {
        return new a7().a(psc_al, psc_dt);
    }
    
    public static boolean b(final psc_al psc_al, final psc_dt psc_dt) {
        return new a8().b(psc_al, psc_dt);
    }
    
    public boolean b() {
        return this.a(new as());
    }
    
    public boolean c() {
        return this.a(new a0());
    }
    
    public boolean d() {
        return this.a(new a1());
    }
    
    public boolean e() {
        return this.a(new a2());
    }
    
    public boolean f() {
        return this.a(new a3());
    }
    
    public boolean g() {
        return this.a(new a4());
    }
    
    public boolean h() {
        return this.a(new a7());
    }
    
    public boolean i() {
        return this.a(new a8());
    }
    
    public boolean j() {
        return this.a(new a9());
    }
    
    private synchronized void a(final Throwable obj) {
        this.f.addElement(obj);
    }
    
    private boolean a(final at at) {
        final boolean b = psc_au.b();
        boolean a = false;
        try {
            psc_au.a(false);
            a = at.a();
        }
        catch (Throwable t) {
            final String str = "Running kat.getClass().getName() caugth ";
            if (t instanceof SecurityException) {
                this.a(t);
            }
            else {
                this.a(new SecurityException(str + t.toString()));
            }
        }
        finally {
            psc_au.a(b);
        }
        return a;
    }
    
    public synchronized int k() {
        return this.f.size();
    }
    
    public synchronized Enumeration l() {
        return this.f.elements();
    }
    
    public Vector m() {
        return (Vector)this.f.clone();
    }
    
    public static synchronized void n() {
        final Enumeration<Throwable> elements = a().f.elements();
        while (elements.hasMoreElements()) {
            elements.nextElement().printStackTrace();
        }
    }
    
    private static void a(final String s, final boolean b) {
    }
    
    public boolean o() {
        boolean b;
        if (b = this.b()) {
            if (b = this.c()) {
                if (b = this.d()) {
                    if (b = this.e()) {
                        if (b = this.f()) {
                            if (b = this.g()) {
                                if (b = this.h()) {
                                    if (b = this.i()) {
                                        if (!(b = this.j())) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return b;
    }
    
    static {
        a = new byte[20];
        b = psc_a5.b("0011223344556677889900112233445566778899");
        c = new byte[20];
        psc_ar.d = null;
    }
    
    static class a4 extends psc_a5 implements at
    {
        static final a4.a6[] a;
        
        public a4() {
            this(false);
        }
        
        public a4(final boolean b) {
            a4.a[0] = new a4.a6("SHA1", 0, 8, 10, psc_a5.b("cad223a5ad1baf0a"), psc_a5.b("e2125d5978d0dee9dabd1d555a31264a82dfeebe05fce42ff4a4ca2c3579c317328dad29ce82b0b69def17b354065f757e748ebb7189c7ef81991aa973a2a075352ec8d6cc7bec8b8dda281090556b686294ce1afa02257aee2bd429e768e5df53bf57685905b8ed1245f675f66672ab940756e287ae2956a522a0b57915b7de"), "337cc230c23c751cdf13");
            a4.a[1] = new a4.a6("SHA224", 0, 8, 14, psc_a5.b("1945b7ef51f8ca6f"), psc_a5.b("cc25fef6f8e249d558054eb3383f52d19e0c2fb3d02bf6321651d8cdc11e47406c50585882376c0501690fb17db620d0675e33b88f8233b3e8d94c538ba40706ffe4b7eab8ca15e3dd6f27ea4fc566a8e6ce1f43fe0b1a7832387aea6736e4b90d49e357a1838b9776805d07b6546b80a3c2bdfb23aff2a67cd52a385cbc2481"), "d359568c389101afb116d2a60a5f");
            a4.a[2] = new a4.a6("SHA256", 0, 8, 16, psc_a5.b("e41312a1c2a42071"), psc_a5.b("639beb40e1d2774de872b8683fb54ebf29663083e84e4bf680f06cc6bd765f9977ea59e915a99312501538a93a8b81107e951e47e960d90529707608d592dda9ae127bb3252fe5751dccbe16f168dc29b950a681c913c0477b37eb492f2a26958f7b1947184ab49fd6fe14576e36a63362018ed991514ae3fdad9233d1278386"), "39060a81ef1d08522729b67d0a1be0f2");
            a4.a[3] = new a4.a6("SHA384", 0, 8, 24, psc_a5.b("37df1cfd00b45665"), psc_a5.b("4d5d66d35ef28bf781294ac723452eb6c5584b97b7fe22f8234bc5b24e023cbe1b08264ad118a30c1db772d75b6b576c1128c092bc0ba769d04bbafbfa6cba14a8b52154cbe50782db2246231c32dd4b21ed92b48ef2dc0d229aff94f42a79e7fc4d9d17d6c17effc3500ed4f10287fdfc910a24b41d868d2121dca6c4a641e0"), "450f68c3d529a22ee0e8d85b261480c1534e06482746b59b");
            a4.a[4] = new a4.a6("SHA512", 0, 8, 32, psc_a5.b("db5798aa221db73a"), psc_a5.b("81c541a1f5cc439b4854692fbca5adfdc672c6706faeaa38faa252fbc73dbd4777ab37cc5129dbc8e41757c2ee4eef7b7db5c522540f57c7901749128ccf65350115e01f7277def2c74839b838f37e526c9039b0e2b57348918f78c0cb31deee672bc2031f5f550337109879603c6368db2cac84e14906a24331686e4e0a305c"), "9ff4521c1571e25a0c63838d229bd4ab1f55812c04fc52585a6b6ec6d50a8cfd");
        }
        
        public boolean a() {
            for (int i = 0; i < a4.a.length; ++i) {
                if (a4.a[i] != null && !a4.a[i].a()) {
                    return false;
                }
            }
            return true;
        }
        
        static {
            a = new a4.a6[5];
        }
    }
    
    interface at
    {
        boolean a();
    }
    
    static class a8 implements at
    {
        private String[] a;
        
        public a8() {
            this.a = new String[] { "SHA1/RSA/PKCS1Block01Pad", "SHA224/RSA/PKCS1Block01Pad", "SHA256/RSA/PKCS1Block01Pad", "SHA384/RSA/PKCS1Block01Pad", "SHA512/RSA/PKCS1Block01Pad", "SHA1/X931RSA/X931Pad", "SHA1/RSA/PKCS1V2PSS-1/MGF1/SHA1", "SHA224/RSA/PKCS1V2PSS-1/MGF1/SHA1", "SHA256/RSA/PKCS1V2PSS-1/MGF1/SHA1", "SHA384/RSA/PKCS1V2PSS-1/MGF1/SHA1", "SHA512/RSA/PKCS1V2PSS-1/MGF1/SHA1" };
        }
        
        private boolean a(final String s, final psc_av psc_av, final psc_dt psc_dt, final psc_al psc_al, final byte[] array) throws Exception {
            psc_eo a = null;
            psc_eo a2 = null;
            Label_0100: {
                try {
                    a = psc_eo.a(s, "Java");
                    a.a(psc_dt, psc_av);
                    a.d(array, 0, array.length);
                    final byte[] t = a.t();
                    a2 = psc_eo.a(s, "Java");
                    a2.a(psc_al, null);
                    a2.e(array, 0, array.length);
                    final boolean f = a2.f(t, 0, t.length);
                    break Label_0100;
                }
                catch (Exception ex) {
                    throw ex;
                }
                finally {
                    if (psc_av != null) {
                        psc_av.y();
                    }
                    if (a != null) {
                        a.y();
                    }
                    Label_0163: {
                        if (a2 != null) {
                            a2.y();
                            break Label_0163;
                        }
                        break Label_0163;
                    }
                    while (true) {}
                Block_7_Outer:
                    while (true) {
                        while (true) {
                            Block_6: {
                                break Block_6;
                                a2.y();
                                return;
                            }
                            a.y();
                            Label_0120: {
                                continue;
                            }
                        }
                        Block_5: {
                            break Block_5;
                            Label_0130: {
                                return;
                            }
                        }
                        psc_av.y();
                        continue Block_7_Outer;
                    }
                }
                // iftrue(Label_0120:, a == null)
                // iftrue(Label_0130:, a2 == null)
                // iftrue(Label_0110:, psc_av == null)
            }
        }
        
        public boolean a(final psc_al psc_al, final psc_dt psc_dt) {
            psc_av psc_av = null;
            int n = 1;
            Label_0129: {
                try {
                    final byte[] bytes = "Simple data to sign".getBytes("UTF8");
                    psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    for (int i = 0; i < this.a.length; ++i) {
                        psc_av.y();
                        psc_av.setSeed(psc_ar.a);
                        n = ((n && this.a(this.a[i], psc_av, psc_dt, psc_al, bytes)) ? 1 : 0);
                        if (n == 0) {
                            break;
                        }
                    }
                    final int n2 = n;
                    break Label_0129;
                }
                catch (Exception ex) {
                    throw new SecurityException(ex.toString());
                }
                finally {
                    if (psc_al != null) {
                        psc_al.y();
                    }
                    if (psc_dt != null) {
                        psc_dt.y();
                    }
                    Label_0184: {
                        if (psc_av != null) {
                            psc_av.y();
                            break Label_0184;
                        }
                        break Label_0184;
                    }
                    while (true) {}
                    // iftrue(Label_0147:, psc_dt == null)
                    // iftrue(Label_0155:, psc_av == null)
                    while (true) {
                        final int n2;
                        Block_11: {
                            while (true) {
                            Block_10:
                                while (true) {
                                    break Block_10;
                                    break Block_11;
                                    psc_al.y();
                                    continue;
                                }
                                psc_dt.y();
                                continue;
                            }
                            Label_0155: {
                                return n2 != 0;
                            }
                        }
                        psc_av.y();
                        return n2 != 0;
                        continue;
                    }
                }
                // iftrue(Label_0139:, psc_al == null)
            }
        }
        
        public boolean b(final psc_al psc_al, final psc_dt psc_dt) {
            Label_0073: {
                try {
                    final byte[] bytes = "Simple data to sign".getBytes("UTF8");
                    final psc_av psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    psc_av.g(psc_ar.a);
                    final boolean a = this.a("SHA1/RSA/PKCS1Block01Pad", psc_av, psc_dt, psc_al, bytes);
                    break Label_0073;
                }
                catch (Exception ex) {
                    throw new SecurityException(ex.toString());
                }
                finally {
                    if (psc_al != null) {
                        psc_al.y();
                    }
                    Label_0112: {
                        if (psc_dt != null) {
                            psc_dt.y();
                            break Label_0112;
                        }
                        break Label_0112;
                    }
                    while (true) {}
                    // iftrue(Label_0083:, psc_al == null)
                    // iftrue(Label_0091:, psc_dt == null)
                    while (true) {
                        Block_5: {
                            break Block_5;
                            final boolean a;
                            while (true) {
                                psc_dt.y();
                                return a;
                                continue;
                            }
                            Label_0091: {
                                return a;
                            }
                        }
                        psc_al.y();
                        continue;
                    }
                }
            }
        }
        
        public boolean a() {
            final byte[] b = psc_a5.b("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003");
            final byte[] b2 = psc_a5.b("8c9298d4e044e4323c86241f787853d872fe5cabb4bd850780fdd0dd2ac31f2ecf9d754c8d238ce5e20a64273170813c4434703cb0828d07451d0df7ac5b3a5900e12d7cd62e2c47403a81515e3cf30818c2bbf357930756d40b8b009647fb214dea1899de314934437c9f2975e4b5e17b80562a3876a934d7c92befa943f351");
            final byte[] b3 = psc_a5.b("176dc4237ab626085f6bb0afe969634ebdd50f71f374eb81402a4d7a31cb2fdd229a3e376cdb4226505710b132e8158a0b5e12b4c815c22be0da2cfe9cb9df0e963e84ad227c2f2cc177828ac484ef8402466473309fa66c03fa9fa0aa7060dad6762ba012fd5677304066a52dde6caca95f8416f9560e227c5dfd9b2a769871");
            final byte[][] array = { b2, b };
            final byte[][] array2 = { b2, b3 };
            psc_al a = null;
            psc_dt a2 = null;
            Label_0140: {
                try {
                    a2 = psc_dt.a("RSA", "Java");
                    a = psc_al.a("RSA", "Java");
                    a.a("RSAPublicKey", array);
                    a2.a("RSAPrivateKey", array2);
                    final boolean a3 = this.a(a, a2);
                    break Label_0140;
                }
                catch (Exception ex) {
                    throw new SecurityException(ex.toString());
                }
                finally {
                    if (a2 != null) {
                        a2.y();
                    }
                    Label_0187: {
                        if (a != null) {
                            a.y();
                            break Label_0187;
                        }
                        break Label_0187;
                    }
                    while (true) {}
                    // iftrue(Label_0152:, a2 == null)
                    while (true) {
                        Label_0152: {
                            while (true) {
                                a2.y();
                                break Label_0152;
                                continue;
                            }
                            a.y();
                            Label_0162: {
                                return;
                            }
                        }
                        continue;
                    }
                }
                // iftrue(Label_0162:, a == null)
            }
        }
    }
    
    static class a7 implements at
    {
        static final int[] a;
        static final byte[][][] b;
        
        public a7() {
        }
        
        public boolean a(final psc_al psc_al, final psc_dt psc_dt) {
            psc_eo a = null;
            psc_eo a2 = null;
            Label_0184: {
                try {
                    final byte[] bytes = "Simple data to sign".getBytes("UTF8");
                    final psc_av psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    psc_av.g(psc_ar.a);
                    a = psc_eo.a("SHA1/DSA", "Java");
                    a.a(psc_dt, psc_av);
                    a.d(bytes, 0, bytes.length);
                    final byte[] t = a.t();
                    a2 = psc_eo.a("SHA1/DSA", "Java");
                    a2.a(psc_al, psc_av);
                    a2.e(bytes, 0, bytes.length);
                    final boolean f = a2.f(t, 0, t.length);
                    break Label_0184;
                }
                catch (Throwable t2) {
                    final boolean b = false;
                    break Label_0184;
                }
                finally {
                    if (psc_al != null) {
                        psc_al.y();
                    }
                    if (psc_dt != null) {
                        psc_dt.y();
                    }
                    if (a != null) {
                        a.y();
                    }
                    Label_0263: {
                        if (a2 != null) {
                            a2.y();
                            break Label_0263;
                        }
                        break Label_0263;
                    }
                    while (true) {}
                    // iftrue(Label_0171:, a == null)
                    // iftrue(Label_0212:, a == null)
                    // iftrue(Label_0181:, a2 == null)
                    // iftrue(Label_0194:, psc_al == null)
                    // iftrue(Label_0153:, psc_al == null)
                    // iftrue(Label_0222:, a2 == null)
                    // iftrue(Label_0202:, psc_dt == null)
                Block_10_Outer:
                    while (true) {
                        Label_0153: {
                            while (true) {
                            Block_12_Outer:
                                while (true) {
                                    while (true) {
                                        Label_0212: {
                                            final boolean b;
                                        Label_0171_Outer:
                                            while (true) {
                                                Block_9: {
                                                Block_5:
                                                    while (true) {
                                                        Block_7: {
                                                            while (true) {
                                                                a.y();
                                                                break Label_0212;
                                                                break Block_7;
                                                                continue Label_0171_Outer;
                                                            }
                                                            final boolean f;
                                                            Label_0181: {
                                                                return f;
                                                            }
                                                            Block_8: {
                                                                break Block_8;
                                                                a2.y();
                                                                return b;
                                                                break Block_9;
                                                                break Block_5;
                                                            }
                                                            a2.y();
                                                            return f;
                                                        }
                                                        a.y();
                                                        continue Block_12_Outer;
                                                    }
                                                    psc_al.y();
                                                    break Label_0153;
                                                }
                                                psc_al.y();
                                                break Block_12_Outer;
                                                psc_dt.y();
                                                continue Label_0171_Outer;
                                            }
                                            Label_0222: {
                                                return b;
                                            }
                                        }
                                        continue Block_10_Outer;
                                    }
                                    psc_dt.y();
                                    continue Block_12_Outer;
                                }
                                continue;
                            }
                        }
                        continue;
                    }
                }
                // iftrue(Label_0161:, psc_dt == null)
            }
        }
        
        public psc_em a(final byte[] array, final byte[] array2, final byte[] array3, final SecureRandom secureRandom) {
            psc_em a = null;
            psc_el a2 = null;
            final byte[][] array4 = new byte[2][];
            try {
                a2 = psc_el.a("DSA", "Java");
                a2.a(new byte[][] { array, array2, array3 });
                a = psc_em.a("DSA", "Java");
                a.a(a2, null, secureRandom);
                a.s();
            }
            catch (Exception ex) {
                return null;
            }
            finally {
                if (a2 != null) {
                    a2.y();
                }
            }
            return a;
        }
        
        public boolean a() {
            psc_av psc_av = null;
            psc_em a = null;
            final byte[] seed = new byte[40];
            boolean b = false;
            Label_0228: {
                try {
                    psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    for (int i = 0; i < a7.a.length; ++i) {
                        final int n = a7.a[i];
                        final byte[] array = a7.b[i][0];
                        final byte[] array2 = a7.b[i][1];
                        final byte[] array3 = a7.b[i][2];
                        final byte[] array4 = a7.b[i][3];
                        final byte[] array5 = a7.b[i][4];
                        psc_av.setSeed(seed);
                        a = this.a(array, array2, array3, psc_av);
                        final byte[][] b2 = a.p().b("DSAPublicValue");
                        final byte[] array6 = a.q().b("DSAPrivateValue")[0];
                        final byte[] array7 = b2[0];
                        psc_a5.a("X", array4, array6);
                        psc_a5.a("Y", array5, array7);
                        psc_av.y();
                    }
                    b = true;
                    break Label_0228;
                }
                catch (Throwable t) {
                    break Label_0228;
                }
                finally {
                    if (psc_av != null) {
                        psc_av.y();
                    }
                    Label_0267: {
                        if (a != null) {
                            a.y();
                            break Label_0267;
                        }
                        break Label_0267;
                    }
                    while (true) {}
                    // iftrue(Label_0217:, psc_av == null)
                    // iftrue(Label_0246:, a == null)
                    // iftrue(Label_0238:, psc_av == null)
                    // iftrue(Label_0225:, a == null)
                    while (true) {
                        Block_6: {
                            break Block_6;
                        Block_7:
                            while (true) {
                                a.y();
                                return b;
                                Label_0225: {
                                    return b;
                                }
                                Label_0246:
                                return b;
                                Block_8: {
                                    break Block_8;
                                    break Block_7;
                                }
                                psc_av.y();
                                continue;
                            }
                            a.y();
                            return b;
                        }
                        psc_av.y();
                        continue;
                    }
                }
            }
        }
        
        static {
            a = new int[] { 512 };
            b = new byte[][][] { { psc_a5.b("979b51fb1f2b1abb4a7184f8f01ac7c07fb6866b449b396a0740627346a2a65ad91c315a9d46205483b5bc59d24220ae9b9ec3b03f011704d2d3692c62cfb01b"), psc_a5.b("b89fc43e9c88c9cf75d0da5bded9bb730d9fc477"), psc_a5.b("2b160bfe2ac0828de35d98e322391c82a6aed5180a013200354da4317e6971656ec6d53dbdf2478a17f4796eb94953bb2962fab74a51b477a1f43b5c21e17294"), psc_a5.b("fc3e9e2c385827ca257e2df3f2b92d3c16e180"), psc_a5.b("471a656c5eb7196e1e692cc1e2c4984cb561e769532611e664789e90cf1c3d052bf1381ab43c5c9c6b685d14b6053d93d04b6c37b0167f0af2f39bbd7196a055") } };
        }
    }
    
    static class a9 extends psc_a5 implements at
    {
        public boolean a() {
            psc_ev psc_ev = null;
            psc_ev psc_ev2 = null;
            final byte[] b = psc_a5.b("b116d4cbda06ee2bc90a702c7e2cdc2aba888c44");
            final byte[] b2 = psc_a5.b("b116d4cbda06ee2bc90a702c7e2cdc2aba888c447173ae6bdaccb2d9845d6096ece584f8d4b5e58b");
            final byte[] b3 = psc_a5.b("51f5f5ebede5d91c4210c524a057a5abd6a200aea8c5d58619de038941e7914182cf62f2742dbbca");
            byte[] array;
            byte[] array2;
            boolean b4;
            Block_8_Outer:Label_0198_Outer:
            while (true) {
                Label_0188: {
                    try {
                        array = new byte[20];
                        array2 = new byte[40];
                        psc_ev = (psc_ev)psc_av.getInstance("FIPS186Random", "Java");
                        psc_ev.setSeed(psc_ar.a);
                        psc_ev.nextBytes(array);
                        psc_a5.a(b, array);
                        psc_ev.y();
                        psc_ev.setSeed(psc_ar.a);
                        psc_ev.nextBytes(array2);
                        psc_a5.a(b2, array2);
                        psc_ev2 = new psc_e2(psc_ar.a);
                        psc_ev2.nextBytes(array2);
                        psc_a5.a(b3, array2);
                        break Block_8_Outer;
                    }
                    catch (Exception ex) {
                        throw new SecurityException(ex.getMessage());
                    }
                    catch (Throwable t) {
                        b4 = false;
                        break Label_0188;
                    }
                    finally {
                        if (psc_ev != null) {
                            psc_ev.y();
                        }
                        Label_0227: {
                            if (psc_ev2 != null) {
                                psc_ev2.y();
                                break Label_0227;
                            }
                            break Label_0227;
                        }
                        while (true) {}
                        // iftrue(Label_0198:, psc_ev == null)
                        // iftrue(Label_0206:, psc_ev2 == null)
                        // iftrue(Label_0185:, psc_ev2 == null)
                        // iftrue(Label_0177:, psc_ev == null)
                    Block_10:
                        while (true) {
                            Block_9: {
                                break Block_9;
                                Label_0185: {
                                    while (true) {
                                        while (true) {
                                            psc_ev2.y();
                                            break Label_0185;
                                            break Block_10;
                                            psc_ev.y();
                                            Label_0177: {
                                                continue Label_0198_Outer;
                                            }
                                        }
                                        continue;
                                    }
                                }
                                continue Block_8_Outer;
                            }
                            psc_ev.y();
                            continue;
                        }
                        psc_ev2.y();
                        Label_0206: {
                            return b4;
                        }
                    }
                }
                break;
            }
        }
    }
    
    static class a3 implements at
    {
        static char[] a;
        static String b;
        static String[] c;
        static byte[][] d;
        static byte[][] e;
        
        public a3() {
        }
        
        public boolean a() {
            return this.b() && this.c() && this.d() && this.e();
        }
        
        public boolean b() {
            return this.a(0);
        }
        
        public boolean c() {
            return this.a(1);
        }
        
        public boolean d() {
            return this.a(1);
        }
        
        public boolean e() {
            return this.a(3);
        }
        
        public boolean a(final int n) {
            psc_da a = null;
            try {
                a = psc_da.a(a3.c[n], "Java");
                a.i();
                a.a(a3.d[n], 0, a3.d[n].length);
                psc_a5.a(a3.e[n], a.j());
            }
            catch (Exception ex) {
                throw new SecurityException(ex.toString());
            }
            finally {
                if (a != null) {
                    a.y();
                }
            }
            return true;
        }
        
        static {
            a3.a = new char[1000000];
            for (int i = 0; i < 1000000; ++i) {
                a3.a[i] = 'a';
            }
            a3.b = new String(a3.a, 0, a3.a.length);
            a3.c = new String[] { "SHA224", "SHA256", "SHA384", "SHA512" };
            a3.d = new byte[][] { psc_a5.a(a3.b, "8859_1"), psc_a5.b("e3d72570dcdd787ce3887ab2cd684652"), psc_a5.b("a41c497779c0375ff10a7f4e08591739"), psc_a5.b("8d4e3c0e3889191491816e9d98bff0a0") };
            a3.e = new byte[][] { psc_a5.b("20794655980c91d8bbb4c1ea97618a4bf03f42581948b2ee4ee7ad67"), psc_a5.b("175ee69b02ba9b58e2b0a5fd13819cea573f3940a94f825128cf4209beabb4e8"), psc_a5.b("c9a68443a005812256b8ec76b00516f0dbb74fab26d665913f194b6ffb0e91ea9967566b58109cbc675cc208e4c823f7"), psc_a5.b("cb0b67a4b8712cd73c9aabc0b199e9269b20844afb75acbdd1c153c9828924c3ddedaafe669c5fdd0bc66f630f6773988213eb1b16f517ad0de4b2f0c95c90f8") };
        }
    }
    
    static class a2 implements at
    {
        static byte[] a;
        static byte[] b;
        
        public a2() {
        }
        
        public boolean a() {
            psc_da a = null;
            try {
                a = psc_da.a("SHA1", "Java");
                a.i();
                a.a(a2.a, 0, a2.a.length);
                final byte[] j = a.j();
                psc_a5.a("digestData and expectedResult", a2.b, 0, a2.b.length, j, 0, j.length);
            }
            catch (Exception ex) {
                throw new SecurityException(ex.toString());
            }
            finally {
                if (a != null) {
                    a.y();
                }
            }
            return true;
        }
        
        static {
            a2.a = psc_a5.b("616263");
            a2.b = psc_a5.b("a9993e364706816aba3e25717850c26c9cd0d89d");
        }
    }
    
    static class a1 implements at
    {
        static byte[] a;
        static byte[] b;
        static byte[] c;
        static byte[][] d;
        static final String[] e;
        
        public a1() {
        }
        
        public boolean a() {
            psc_df a = null;
            psc_df a2 = null;
            psc_dc o = null;
            Label_0311: {
                try {
                    final psc_av psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    psc_av.g(psc_ar.a);
                    for (int i = 0; i < a1.e.length; ++i) {
                        final String string = "3DES_EDE/" + a1.e[i] + "/NoPad";
                        a = psc_df.a(string, "Java");
                        a2 = psc_df.a(string, "Java");
                        if (i != 0) {
                            a.b(a1.b, 0, a1.b.length);
                            a2.b(a1.b, 0, a1.b.length);
                        }
                        o = a.o();
                        o.a("Clear", a1.a, 0, a1.a.length);
                        final byte[] array = new byte[a.a(a1.c.length)];
                        a.c(o, psc_av);
                        final int a3 = a.a(a1.c, 0, a1.c.length, array, 0);
                        psc_a5.a("encryptedData and expectedResult", array, 0, a3 + a.b(array, a3), a1.d[i], 0, a1.d[i].length);
                        final byte[] array2 = new byte[a2.a(array.length)];
                        a2.b(o);
                        final int b = a2.b(array, 0, array.length, array2, 0);
                        psc_a5.a("decryptedData and dataToEncrypt", array2, 0, b + a2.c(array2, b), a1.c, 0, a1.c.length);
                    }
                    break Label_0311;
                }
                catch (Exception ex) {
                    throw new SecurityException(ex.toString());
                }
                finally {
                    if (a != null) {
                        a.y();
                    }
                    if (a2 != null) {
                        a2.y();
                    }
                    Label_0370: {
                        if (o != null) {
                            o.y();
                            break Label_0370;
                        }
                        break Label_0370;
                    }
                    while (true) {}
                    return true;
                    // iftrue(Label_0339:, o == null)
                    // iftrue(Label_0329:, a2 == null)
                    while (true) {
                        Block_9: {
                            while (true) {
                                while (true) {
                                    break Block_9;
                                    a2.y();
                                    continue;
                                }
                                Label_0339: {
                                    return true;
                                }
                                a.y();
                                Label_0321:
                                continue;
                            }
                        }
                        o.y();
                        return true;
                        continue;
                    }
                }
                // iftrue(Label_0321:, a == null)
            }
        }
        
        static {
            a1.a = psc_a5.b("010101010101010101010101010101010101010101010101");
            a1.b = new byte[8];
            a1.c = psc_a5.b("8000000000000000");
            a1.d = new byte[][] { psc_a5.b("95f8a5e5dd31d900"), psc_a5.b("95f8a5e5dd31d900"), psc_a5.b("0ca64de9c1b123a7"), psc_a5.b("0ca64de9c1b123a7") };
            e = new String[] { "ECB" };
        }
    }
    
    static class a0 implements at
    {
        static byte[] a;
        static byte[] b;
        static byte[] c;
        static byte[][] d;
        static final String[] e;
        static final byte[] f;
        
        public a0() {
        }
        
        public boolean a() {
            psc_df a = null;
            psc_df a2 = null;
            psc_dc o = null;
            Label_0311: {
                try {
                    final psc_av psc_av = (psc_av)psc_av.getInstance("FIPS186Random", "Java");
                    psc_av.g(psc_ar.a);
                    for (int i = 0; i < a0.e.length; ++i) {
                        final String string = "DES/" + a0.e[i] + "/NoPad";
                        a = psc_df.a(string, "Java");
                        a2 = psc_df.a(string, "Java");
                        if (i != 0) {
                            a.b(a0.b, 0, a0.b.length);
                            a2.b(a0.b, 0, a0.b.length);
                        }
                        o = a.o();
                        o.a("Clear", a0.a, 0, a0.a.length);
                        final byte[] array = new byte[a.a(a0.c.length)];
                        a.c(o, psc_av);
                        final int a3 = a.a(a0.c, 0, a0.c.length, array, 0);
                        psc_a5.a("encryptedData and expectedResult", array, 0, a3 + a.b(array, a3), a0.d[i], 0, a0.d[i].length);
                        final byte[] array2 = new byte[a2.a(array.length)];
                        a2.b(o);
                        final int b = a2.b(array, 0, array.length, array2, 0);
                        psc_a5.a("decryptedData and dataToEncrypt", array2, 0, b + a2.c(array2, b), a0.c, 0, a0.c.length);
                    }
                    break Label_0311;
                }
                catch (Exception ex) {
                    throw new SecurityException(ex.toString());
                }
                finally {
                    if (a != null) {
                        a.y();
                    }
                    if (a2 != null) {
                        a2.y();
                    }
                    Label_0370: {
                        if (o != null) {
                            o.y();
                            break Label_0370;
                        }
                        break Label_0370;
                    }
                    while (true) {}
                    // iftrue(Label_0339:, o == null)
                    // iftrue(Label_0329:, a2 == null)
                    // iftrue(Label_0321:, a == null)
                    Block_9: {
                        while (true) {
                            while (true) {
                                a2.y();
                                Label_0329: {
                                    break Block_9;
                                }
                                a.y();
                                Label_0321:
                                continue;
                            }
                            Label_0339: {
                                return true;
                            }
                            continue;
                        }
                    }
                    o.y();
                    return true;
                }
            }
        }
        
        static {
            a0.a = psc_a5.b("0101010101010101");
            a0.b = psc_a5.b("0000000000000000");
            a0.c = psc_a5.b("8000000000000000");
            a0.d = new byte[][] { psc_a5.b("95f8a5e5dd31d900"), psc_a5.b("95f8a5e5dd31d900"), psc_a5.b("0ca64de9c1b123a7"), psc_a5.b("0ca64de9c1b123a7") };
            e = new String[] { "ECB" };
            f = psc_a5.b("789b8d29");
        }
    }
    
    static class as implements at
    {
        public static final int a = 0;
        public static final int b = 1;
        public static final int c = 0;
        public static final int d = 1;
        public static final int e = 2;
        public static final int f = 3;
        static final byte[][] g;
        static final byte[][] h;
        static final byte[][] i;
        static final as.ne[] j;
        
        public as() {
        }
        
        static boolean a(final int n, final String str, final int n2, final byte[] array, final byte[] array2, final int n3, final byte[] array3, final byte[] array4) {
            psc_df a = null;
            psc_dc o = null;
            final byte[] array5 = array2.clone();
            final byte[] array6 = array.clone();
            Label_0221: {
                try {
                    a = psc_df.a(str + "/NoPad", "Java");
                    o = a.o();
                    o.a("Clear", array6, 0, array6.length);
                    if (n == 0) {
                        a.b(array5, 0, array5.length);
                        a.c(o, null);
                        a.a(array3, 0, n3 * 16, array4, 0);
                    }
                    else {
                        a.b(array5, 0, array5.length);
                        a.d(o, null);
                        a.b(array3, 0, n3 * 16, array4, 0);
                    }
                    final boolean b = true;
                    break Label_0221;
                }
                catch (Throwable t) {
                    final boolean b2 = false;
                    break Label_0221;
                }
                finally {
                    if (a != null) {
                        a.y();
                    }
                    Label_0268: {
                        if (o != null) {
                            o.y();
                            break Label_0268;
                        }
                        break Label_0268;
                    }
                    while (true) {}
                    // iftrue(Label_0218:, o == null)
                    // iftrue(Label_0208:, a == null)
                    // iftrue(Label_0233:, a == null)
                    while (true) {
                        Label_0233: {
                            final boolean b2;
                        Label_0208_Outer:
                            while (true) {
                                a.y();
                                break Label_0233;
                                o.y();
                                return b2;
                                while (true) {
                                    o.y();
                                    Label_0218: {
                                        return;
                                    }
                                    a.y();
                                    continue;
                                }
                                continue Label_0208_Outer;
                            }
                            Label_0243: {
                                return b2;
                            }
                        }
                        continue;
                    }
                }
                // iftrue(Label_0243:, o == null)
            }
        }
        
        boolean a(final as.ne ne) {
            final String a = ne.a;
            final int b = ne.b;
            final int c = ne.c;
            final int d = ne.d;
            final byte[] array = new byte[2];
            final byte[] array2 = new byte[16];
            final byte[] array3 = new byte[d / 8];
            byte[][] array4 = null;
            switch (d) {
                case 128: {
                    array4 = as.g;
                    break;
                }
                case 192: {
                    array4 = as.h;
                    break;
                }
                case 256: {
                    array4 = as.i;
                    break;
                }
            }
            byte[] array5 = null;
            byte[] array6 = null;
            byte[] array7 = null;
            switch (c) {
                case 0:
                case 1: {
                    array5 = ((c == 0) ? new byte[0] : new byte[16]);
                    array6 = array4[0];
                    array7 = array4[1];
                    break;
                }
                case 2:
                case 3: {
                    array5 = array4[0];
                    array6 = array2;
                    array7 = array4[1];
                    break;
                }
            }
            final byte[] array8 = new byte[16];
            byte[] array9;
            byte[] array10;
            if (b == 0) {
                array9 = array6;
                array10 = array7;
            }
            else {
                array9 = array7;
                array10 = array6;
            }
            return a(b, a, d, array3, array5, 1, array9, array8) && a(array10, array8, 16) == 0;
        }
        
        public static int a(final byte[] array, final byte[] array2, final int n) {
            for (int i = 0; i < n; ++i) {
                final int n2 = array[i] - array2[i];
                if (n2 != 0) {
                    return n2;
                }
            }
            return 0;
        }
        
        public boolean a() {
            for (int n = 0; as.j[n].a != null; ++n) {
                if (!this.a(as.j[n])) {
                    return false;
                }
            }
            return true;
        }
        
        static {
            g = new byte[][] { psc_a5.b("80000000000000000000000000000000"), psc_a5.b("3ad78e726c1ec02b7ebfe92b23d9ec34") };
            h = new byte[][] { psc_a5.b("80000000000000000000000000000000"), psc_a5.b("6cd02513e8d4dc986b4afe087a60bd0c") };
            i = new byte[][] { psc_a5.b("80000000000000000000000000000000"), psc_a5.b("ddc6bf790c15760d8d9aeb6f9a75fd4e") };
            j = new as.ne[] { new as.ne("AES128/ECB", 0, 0, 128), new as.ne("AES128/ECB", 1, 0, 128), new as.ne("AES192/ECB", 0, 0, 192), new as.ne("AES192/ECB", 1, 0, 192), new as.ne("AES256/ECB", 0, 0, 256), new as.ne("AES256/ECB", 1, 0, 256), new as.ne((String)null, 0, 0, 0) };
        }
    }
}
