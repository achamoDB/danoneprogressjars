import java.net.InetAddress;
import java.net.URL;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fh implements Cloneable, Serializable
{
    private static final int a = 8388608;
    private static final int b = 8388609;
    private static final int c = 8388610;
    private static final int d = 8388611;
    private static final int e = 10485764;
    private static final int f = 8388613;
    private static final int g = 8388614;
    private static final int h = 8388615;
    private static final int i = 8388616;
    private String j;
    private psc_fj k;
    private psc_fy l;
    private psc_fi m;
    private psc_u n;
    private byte[] o;
    private int p;
    public static final int q = 1;
    public static final int r = 2;
    public static final int s = 3;
    public static final int t = 4;
    public static final int u = 5;
    public static final int v = 6;
    public static final int w = 7;
    public static final int x = 8;
    public static final int y = 9;
    protected static int z;
    private psc_n aa;
    
    public psc_fh(final byte[] array, final int n, final int n2) throws psc_v {
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = 0;
        this.aa = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_z psc_z = new psc_z(n2);
            final psc_k psc_k = new psc_k(8400896);
            final psc_ag psc_ag = new psc_ag(8388609);
            final psc_ag psc_ag2 = new psc_ag(8388610);
            final psc_k psc_k2 = new psc_k(8400899);
            final psc_k psc_k3 = new psc_k(10498052);
            final psc_k psc_k4 = new psc_k(8400901);
            final psc_ag psc_ag3 = new psc_ag(8388614);
            final psc_t psc_t = new psc_t(8388615);
            final psc_r psc_r = new psc_r(25165832);
            psc_l.a(array, n, new psc_i[] { psc_z, psc_k, psc_ag, psc_ag2, psc_k2, psc_k3, psc_k4, psc_ag3, psc_t, psc_r, new psc_j() });
            if (psc_k.a) {
                final byte[] array2 = new byte[psc_k.d];
                System.arraycopy(psc_k.b, psc_k.c, array2, 0, psc_k.d);
                (this.m = new psc_fi()).a(array2, 0, 8388608);
                this.p = 1;
            }
            else if (psc_ag.a) {
                this.j = psc_ag.e();
                this.p = 2;
            }
            else if (psc_ag2.a) {
                this.j = psc_ag2.e();
                this.p = 3;
            }
            else if (psc_k2.a) {
                final byte[] array3 = new byte[psc_k2.d];
                System.arraycopy(psc_k2.b, psc_k2.c, array3, 0, psc_k2.d);
                this.k = new psc_fj(array3, 0, 8388611);
                this.p = 4;
            }
            else if (psc_k3.a) {
                final byte[] array4 = new byte[psc_k3.d];
                System.arraycopy(psc_k3.b, psc_k3.c, array4, 0, psc_k3.d);
                this.n = new psc_u(array4, 0, 10485764);
                this.p = 5;
            }
            else if (psc_k4.a) {
                final byte[] array5 = new byte[psc_k4.d];
                System.arraycopy(psc_k4.b, psc_k4.c, array5, 0, psc_k4.d);
                this.l = new psc_fy(array5, 0, 8388613);
                this.p = 6;
            }
            else if (psc_ag3.a) {
                this.j = psc_ag3.e();
                this.p = 7;
            }
            else if (psc_t.a) {
                this.o = new byte[psc_t.d];
                System.arraycopy(psc_t.b, psc_t.c, this.o, 0, psc_t.d);
                this.p = 8;
            }
            else if (psc_r.a) {
                this.o = new byte[psc_r.d];
                System.arraycopy(psc_r.b, psc_r.c, this.o, 0, psc_r.d);
                this.p = 9;
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the name.");
        }
    }
    
    public psc_fh() {
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = 0;
        this.aa = null;
    }
    
    public void a(final Object o, final int i) throws psc_v {
        if (o == null) {
            throw new psc_v("Name is null.");
        }
        switch (this.p = i) {
            case 2:
            case 3: {
                if (o instanceof String) {
                    this.j = (String)o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be a String for type(" + i + ").");
            }
            case 7: {
                if (o instanceof String) {
                    this.j = (String)o;
                    break;
                }
                if (o instanceof URL) {
                    this.j = ((URL)o).toString();
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be either a String or a URL for the URL_NAME_TYPE type.");
            }
            case 1: {
                if (o instanceof psc_fi) {
                    this.m = (psc_fi)o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be an OtherName for the OTHER_NAME_TYPE type.");
            }
            case 4: {
                if (o instanceof psc_fj) {
                    this.k = (psc_fj)o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be an ORAddress for the X400ADDRESS_NAME_TYPE type.");
            }
            case 5: {
                if (o instanceof psc_u) {
                    this.n = (psc_u)o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be an X500Name for the DIRECTORY_NAME_TYPE type.");
            }
            case 6: {
                if (o instanceof psc_fy) {
                    this.l = (psc_fy)o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be an EDIPartyName for the EDIPARTY_NAME_TYPE type.");
            }
            case 8: {
                if (o instanceof byte[]) {
                    this.o = (byte[])o;
                    break;
                }
                if (o instanceof InetAddress) {
                    this.o = ((InetAddress)o).getAddress();
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be either a byte array or an InetAddress for the IPADDRESS_NAME_TYPE type.");
            }
            case 9: {
                if (o instanceof byte[]) {
                    this.o = (byte[])o;
                    break;
                }
                throw new psc_v("GeneralName.setGeneralName: name argument should be a byte array for the REGISTERID_NAME_TYPE type.");
            }
            default: {
                throw new psc_v("GeneralName.setGeneralName: unrecoginized type value(" + i + ").");
            }
        }
    }
    
    public Object b() {
        switch (this.p) {
            case 2:
            case 3:
            case 7: {
                return this.j;
            }
            case 8:
            case 9: {
                return this.o;
            }
            case 1: {
                return this.m;
            }
            case 4: {
                return this.k;
            }
            case 5: {
                return this.n;
            }
            case 6: {
                return this.l;
            }
            default: {
                return null;
            }
        }
    }
    
    public int c() {
        return this.p;
    }
    
    public String toString() {
        switch (this.p) {
            case 2:
            case 3:
            case 7: {
                return this.j;
            }
            case 8:
            case 9: {
                final StringBuffer sb = new StringBuffer();
                sb.append("0x");
                for (int i = 0; i < this.o.length; ++i) {
                    final String hexString = Integer.toHexString(this.o[i]);
                    final int length = hexString.length();
                    if (length < 2) {
                        sb.append('0');
                        sb.append(hexString.charAt(0));
                    }
                    else if (length > 2) {
                        sb.append(hexString.charAt(length - 2));
                        sb.append(hexString.charAt(length - 1));
                    }
                    else {
                        sb.append(hexString);
                    }
                }
                return sb.toString();
            }
            case 1: {
                return this.m.toString();
            }
            case 4: {
                return this.k.toString();
            }
            case 5: {
                return this.n.toString();
            }
            case 6: {
                return this.l.toString();
            }
            default: {
                return null;
            }
        }
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Unable to determine length of the BER");
        }
    }
    
    public int a(final int z) throws psc_v {
        psc_fh.z = z;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.aa == null || n2 != psc_fh.z) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode GeneralName.");
            }
            final int a = this.aa.a(array, n);
            this.aa = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.aa = null;
            throw new psc_v("Unable to encode GeneralName.");
        }
    }
    
    private int a() {
        try {
            final psc_z psc_z = new psc_z(psc_fh.z, 0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i = null;
            switch (this.p) {
                case 2: {
                    psc_i = new psc_ag(8388609, true, 0, this.j);
                    break;
                }
                case 3: {
                    psc_i = new psc_ag(8388610, true, 0, this.j);
                    break;
                }
                case 7: {
                    psc_i = new psc_ag(8388614, true, 0, this.j);
                    break;
                }
                case 8: {
                    if (this.o != null) {
                        psc_i = new psc_t(8388615, true, 0, this.o, 0, this.o.length);
                        break;
                    }
                    psc_i = new psc_t(8388615, false, 0, this.o, 0, 0);
                    break;
                }
                case 9: {
                    if (this.o != null) {
                        psc_i = new psc_r(25165832, true, 0, this.o, 0, this.o.length);
                        break;
                    }
                    psc_i = new psc_r(25165832, true, 0, this.o, 0, 0);
                    break;
                }
                case 1: {
                    if (this.m != null) {
                        final byte[] array = new byte[this.m.a(8388608)];
                        psc_i = new psc_k(8400896, true, 0, array, 0, this.m.b(array, 0, 8388608));
                        break;
                    }
                    psc_i = new psc_k(8400896, false, 0, null, 0, 0);
                    break;
                }
                case 4: {
                    if (this.k != null) {
                        final byte[] array2 = new byte[this.k.a(8388611)];
                        psc_i = new psc_k(8400899, true, 0, array2, 0, this.k.a(array2, 0, 8388611));
                        break;
                    }
                    psc_i = new psc_k(8400899, false, 0, null, 0, 0);
                    break;
                }
                case 5: {
                    if (this.n != null) {
                        final byte[] array3 = new byte[this.n.d(10485764)];
                        psc_i = new psc_k(10498052, true, 0, array3, 0, this.n.a(array3, 0, 10485764));
                        break;
                    }
                    psc_i = new psc_k(10498052, false, 0, null, 0, 0);
                    break;
                }
                case 6: {
                    if (this.l != null) {
                        final byte[] array4 = new byte[this.l.a(8388613)];
                        psc_i = new psc_k(8400901, true, 0, array4, 0, this.l.a(array4, 0, 8388613));
                        break;
                    }
                    psc_i = new psc_k(8400901, false, 0, null, 0, 0);
                    break;
                }
                default: {
                    return 0;
                }
            }
            this.aa = new psc_n(new psc_i[] { psc_z, psc_i, psc_j });
            return this.aa.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fh)) {
            return false;
        }
        final psc_fh psc_fh = (psc_fh)o;
        if (this.p != psc_fh.p) {
            return false;
        }
        if (this.j != null) {
            if (!this.j.equals(psc_fh.j)) {
                return false;
            }
        }
        else if (psc_fh.j != null) {
            return false;
        }
        if (this.o != null) {
            if (!this.o.equals(psc_fh.o)) {
                return false;
            }
        }
        else if (psc_fh.o != null) {
            return false;
        }
        if (this.k != null) {
            if (!this.k.equals(psc_fh.k)) {
                return false;
            }
        }
        else if (psc_fh.k != null) {
            return false;
        }
        if (this.l != null) {
            if (!this.l.equals(psc_fh.l)) {
                return false;
            }
        }
        else if (psc_fh.l != null) {
            return false;
        }
        if (this.m != null) {
            if (!this.m.equals(psc_fh.m)) {
                return false;
            }
        }
        else if (psc_fh.m != null) {
            return false;
        }
        if (this.n != null) {
            if (!this.n.equals(psc_fh.n)) {
                return false;
            }
        }
        else if (psc_fh.n != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fh psc_fh = new psc_fh();
        psc_fh.p = this.p;
        if (this.j != null) {
            psc_fh.j = new String(this.j);
        }
        psc_fh.o = this.o;
        psc_fh.k = this.k;
        psc_fh.l = this.l;
        psc_fh.m = this.m;
        psc_fh.n = this.n;
        psc_fh.z = psc_fh.z;
        if (this.aa != null) {
            psc_fh.a();
        }
        return psc_fh;
    }
}
