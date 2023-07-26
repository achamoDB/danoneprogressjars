// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ol
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 5;
    public static final int h = 6;
    public static final int i = Integer.MIN_VALUE;
    public static final int j = 1073741824;
    public static final int k = 536870912;
    public static final int l = 268435456;
    public static final int m = 134217728;
    public static final int n = 67108864;
    public static final int o = 33554432;
    public static final int p = 16777216;
    public static final int q = 8388608;
    public static final int r = 4194304;
    public static final int s = 2097152;
    public static final int t = 1048576;
    public static final int u = 524288;
    public static final int v = 262144;
    public static final int w = 131072;
    public static final int x = 65536;
    public static final int y = 32768;
    public static final int z = 16384;
    public static final int aa = 8192;
    public static final int ab = 4096;
    public static final int ac = 2048;
    public static final int ad = 1024;
    public static final int ae = 512;
    public static final int af = 256;
    public static final int ag = 128;
    private int ah;
    private int ai;
    private String[] aj;
    private int ak;
    private psc_n al;
    
    public psc_ol(final byte[] array, final int n, final int n2) throws psc_n8 {
        this.ai = 0;
        this.aj = null;
        this.ak = 0;
        this.al = null;
        psc_p psc_p;
        psc_w psc_w;
        psc_d4 psc_d4;
        try {
            final psc_h psc_h = new psc_h(n2);
            psc_p = new psc_p(0);
            psc_w = new psc_w(65536, 12288, new psc_k(3072));
            psc_d4 = new psc_d4(65536);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_p, psc_w, psc_d4, new psc_j() });
        }
        catch (psc_m psc_m) {
            throw new psc_n8("PKIStatusInfo.PKIStatusInfo: decoding PKIStatusInfo faild(" + psc_m.getMessage() + ").");
        }
        try {
            this.ah = psc_p.e();
        }
        catch (psc_m psc_m2) {
            throw new psc_n8("CertResponse.CertResponse: unable to get PKIStatusInfo.status as int(" + psc_m2.getMessage() + ").");
        }
        if (psc_w.a) {
            final int j = psc_w.j();
            this.aj = new String[j];
            for (int i = 0; i < j; ++i) {
                try {
                    final psc_i a = psc_w.a(i);
                    final psc_af psc_af = new psc_af(0);
                    psc_l.a(a.b, a.c, new psc_i[] { psc_af });
                    if (psc_af.a && psc_af.d != 0) {
                        this.aj[i] = new String(psc_af.b, psc_af.c, psc_af.d);
                    }
                    else {
                        this.aj[i] = null;
                    }
                }
                catch (psc_m psc_m3) {
                    throw new psc_n8("PKIStatusInfo.PKIStatusInfo: unable to extract and decode a component of statuString(" + psc_m3.getMessage() + ").");
                }
            }
        }
        if (psc_d4.a) {
            this.ai = this.a(psc_d4);
        }
    }
    
    public psc_ol(final int ah, final int ai, final String[] aj, final int ak) {
        this.ai = 0;
        this.aj = null;
        this.ak = 0;
        this.al = null;
        this.ah = ah;
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
    }
    
    public int a() {
        return this.ah;
    }
    
    public int b() {
        return this.ai;
    }
    
    public String[] c() {
        if (this.aj == null) {
            return null;
        }
        return this.aj.clone();
    }
    
    public int d() {
        return this.ak;
    }
    
    public int a(final int n) throws psc_n8 {
        try {
            final psc_h psc_h = new psc_h(n, true, 0);
            final psc_p psc_p = new psc_p(0, true, 0, this.ah);
            psc_w psc_w;
            if (this.aj == null) {
                psc_w = new psc_w(65536, false, 0, 12288, null);
            }
            else {
                psc_w = new psc_w(65536, true, 0, 12288, new psc_af(0));
                for (int i = 0; i < this.aj.length; ++i) {
                    final byte[] bytes = this.aj[i].getBytes();
                    try {
                        psc_w.b(new psc_af(0, true, 0, bytes, 0, bytes.length));
                    }
                    catch (psc_m psc_m) {
                        throw new psc_n8("PKIStatusInfo.getDERLen: failed to add an element of statusString(" + psc_m.getMessage() + ").");
                    }
                }
            }
            psc_d4 psc_d4;
            if (this.ai == 0) {
                psc_d4 = new psc_d4(65536, false, 0, 0, 0, false);
            }
            else {
                psc_d4 = new psc_d4(65536, true, 0, this.ai, 32, false);
            }
            this.al = new psc_n(new psc_i[] { psc_h, psc_p, psc_w, psc_d4, new psc_j() });
            return this.al.a();
        }
        catch (psc_m psc_m2) {
            this.al = null;
            throw new psc_n8("PKIStatusInfo.getDERLen: failed to encode PKIStatusInfo(" + psc_m2.getMessage() + ").");
        }
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_n8 {
        if (array == null) {
            throw new psc_n8("PKIStatus.getDEREncoding: der should not be null.");
        }
        if (n < 0) {
            throw new psc_n8("PKIStatus.getDEREncoding: offset should not be a negative number.");
        }
        if (this.al == null && array.length - n < this.a(n2)) {
            throw new psc_n8("PKIStatus.getDEREncoding: der is too small to hold the DER-encoding. Use getDERLen to find out how big it should be.");
        }
        try {
            return this.al.a(array, n);
        }
        catch (psc_m psc_m) {
            throw new psc_n8("PKIStatus.getDEREncoding: unable to encode PKIStatusInfo(" + psc_m.getMessage() + ").");
        }
        finally {
            this.al = null;
        }
    }
    
    private int a(final psc_d4 psc_d4) {
        int n = 0;
        for (int i = 0; i < psc_d4.d; ++i) {
            n = (n << 8) + psc_d4.b[psc_d4.c + i];
        }
        for (int j = 0; j < 4 - psc_d4.d; ++j) {
            n <<= 8;
        }
        return n;
    }
}
