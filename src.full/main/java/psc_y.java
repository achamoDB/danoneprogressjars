import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_y implements Cloneable, Serializable
{
    public static final int a = -1;
    public static final int b = -1;
    public static int c;
    public static final int d = 0;
    public static byte[] e;
    public static final int f = 64;
    private static final int g = 500;
    public static final int h = 1;
    public static byte[] i;
    public static final int j = 2;
    public static final int k = 2;
    public static byte[] l;
    public static final int m = 128;
    public static final int n = 3;
    public static byte[] o;
    public static final int p = 128;
    public static final int q = 4;
    public static byte[] r;
    public static final int s = 64;
    public static final int t = 5;
    public static byte[] u;
    public static final int v = 128;
    public static final int w = 6;
    public static byte[] x;
    public static final int y = 32;
    public static final int z = 7;
    public static byte[] aa;
    public static final int ab = 64;
    public static final int ac = 8;
    public static byte[] ad;
    public static final int ae = 64;
    public static final int af = 9;
    public static byte[] ag;
    public static final int ah = 128;
    public static final int ai = 10;
    public static byte[] aj;
    public static final int ak = 128;
    public static final int al = 11;
    public static byte[] am;
    public static final int an = 40;
    public static final int ao = 12;
    public static byte[] ap;
    public static final int aq = 32768;
    public static final int ar = 13;
    public static byte[] as;
    public static final int at = 32768;
    public static final int au = 14;
    public static byte[] av;
    public static final int aw = 64;
    public static final int ax = 15;
    public static byte[] ay;
    public static final int az = 32768;
    public static final int a0 = 16;
    public static byte[] a1;
    public static final int a2 = 32768;
    public static final int a3 = 17;
    public static byte[] a4;
    public static final int a5 = 32768;
    public static final int a6 = 18;
    public static byte[] a7;
    public static final int a8 = -1;
    public static final int a9 = 19;
    public static byte[] ba;
    public static final int bb = 64;
    public static final int bc = 20;
    public static byte[] bd;
    public static final int be = -1;
    public static final int bf = 21;
    public static byte[] bg;
    public static final int bh = -1;
    public static final int bi = 22;
    public static byte[] bj;
    public static final int bk = -1;
    public static final int bl = 23;
    public static byte[] bm;
    public static final int bn = 32768;
    public static final int bo = 24;
    public static byte[] bp;
    public static final int bq = 1;
    public static final int br = 25;
    public static byte[] bs;
    public static final int bt = 2;
    public static final int bu = 26;
    public static byte[] bv;
    public static final int bw = 2;
    protected static final psc_mt[] bx;
    protected static final psc_mu[] by;
    private int bz;
    private byte[] b0;
    private byte[] b1;
    private int b2;
    private int b3;
    private int b4;
    private String b5;
    protected psc_n b6;
    
    protected psc_y() {
        this.b4 = psc_y.c;
        this.b5 = null;
        this.b6 = null;
    }
    
    public psc_y(final int bz, final byte[] b0, final byte[] b2, final int b3, final int b4) throws psc_v {
        this.b4 = psc_y.c;
        this.b5 = null;
        this.b6 = null;
        this.bz = bz;
        if (bz >= psc_y.bx.length) {
            this.bz = -1;
        }
        if (b2 == null || b4 == 0) {
            throw new psc_v("BER encoding is null.");
        }
        this.b1 = b2;
        this.b2 = b3;
        this.b3 = b4;
        if (this.bz != -1) {
            switch (bz) {
                case 0: {
                    if (psc_ah.c(psc_nz.a)) {
                        this.a(b2, b3, 64);
                        break;
                    }
                    this.a(b2, b3, 500);
                    break;
                }
                case 1: {
                    if (psc_ah.c(psc_nz.a)) {
                        this.a(b2, b3, 2, 2);
                        break;
                    }
                    this.a(b2, b3, 2);
                    this.b4 = 4864;
                    break;
                }
                case 2: {
                    this.a(b2, b3, 128);
                    break;
                }
                case 3: {
                    this.a(b2, b3, 128);
                    break;
                }
                case 4: {
                    this.a(b2, b3, 64);
                    break;
                }
                case 5: {
                    this.a(b2, b3, 128);
                    break;
                }
                case 6: {
                    this.a(b2, b3, 1, 32);
                    break;
                }
                case 7: {
                    this.a(b2, b3, 64);
                    break;
                }
                case 8: {
                    this.a(b2, b3, 64);
                    break;
                }
                case 9: {
                    this.a(b2, b3, 128);
                    break;
                }
                case 10: {
                    this.a(b2, b3, 128);
                    break;
                }
                case 11: {
                    this.a(b2, b3, 40);
                    break;
                }
                case 12: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 13: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 14: {
                    this.a(b2, b3, 1, 64);
                    break;
                }
                case 15: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 16: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 17: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 18: {
                    this.a(b2, b3, 1, -1);
                    break;
                }
                case 19: {
                    this.b(b2, b3, 1, 19);
                    break;
                }
                case 20: {
                    this.b(b2, b3, -1);
                    break;
                }
                case 21: {
                    this.a(b2, b3, -1);
                    break;
                }
                case 22: {
                    this.a(b2, b3);
                    break;
                }
                case 23: {
                    this.a(b2, b3, 32768);
                    break;
                }
                case 24: {
                    this.a(b2, b3, 1, 1);
                    break;
                }
                case 25: {
                    if (psc_ah.c(psc_nz.a)) {
                        this.a(b2, b3, 2, 2);
                        break;
                    }
                    this.a(b2, b3, 2);
                    this.b4 = 4864;
                    break;
                }
                case 26: {
                    if (psc_ah.c(psc_nz.a)) {
                        this.a(b2, b3, 2, 2);
                        break;
                    }
                    this.a(b2, b3, 2);
                    this.b4 = 4864;
                    break;
                }
            }
            return;
        }
        if (b0 == null) {
            throw new psc_v("AVA type is missing.");
        }
        this.b0 = b0;
        try {
            this.a(this.b1, this.b2, -1);
        }
        catch (psc_v psc_v) {
            this.b5 = null;
        }
    }
    
    public psc_y(final int bz, final byte[] b0, int b2, final String b3) throws psc_v {
        this.b4 = psc_y.c;
        this.b5 = null;
        this.b6 = null;
        this.bz = bz;
        if (bz >= psc_y.bx.length) {
            this.bz = -1;
        }
        if (b2 == 0) {
            switch (bz) {
                case 1:
                case 6:
                case 14:
                case 18:
                case 24:
                case 25:
                case 26: {
                    b2 = 4864;
                    break;
                }
                case 7:
                case 19: {
                    b2 = 5632;
                    break;
                }
                case 22: {
                    b2 = 6144;
                    break;
                }
                case -1: {
                    throw new psc_v("ASN.1 type is missing.");
                }
                default: {
                    b2 = 3072;
                    break;
                }
            }
        }
        this.b4 = b2;
        if (this.bz == -1) {
            if (b0 == null) {
                throw new psc_v("AVA type is missing.");
            }
            this.b0 = b0;
        }
        else {
            this.a(this.bz, this.b4, b3);
        }
        if (!this.a(this.b4)) {
            throw new psc_v("Unsupported ASN1 type. Use another constructor that takes the DER form.");
        }
        if (b3 == null) {
            throw new psc_v("AVA value is missing.");
        }
        this.b5 = b3;
    }
    
    public psc_y(final byte[] b0, final byte[] b2) throws psc_v {
        this.b4 = psc_y.c;
        this.b5 = null;
        this.b6 = null;
        if (b0 == null || b2 == null) {
            throw new psc_v("AVA data is missing.");
        }
        this.bz = c(b0, 0, b0.length);
        this.b0 = b0;
        this.b1 = b2;
        this.b2 = 0;
        this.b3 = b2.length;
        try {
            this.a(this.b1, this.b2, -1);
        }
        catch (psc_v psc_v) {
            this.b5 = null;
        }
    }
    
    public int d() {
        return this.bz;
    }
    
    public int e() {
        return this.b4;
    }
    
    public void b(final String b5) {
        if (b5 != null) {
            this.b5 = b5;
        }
    }
    
    public void e(final byte[] b1, final int b2, final int b3) throws psc_v {
        if (b1 == null || b3 == 0) {
            throw new psc_v("BER encoding is null.");
        }
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        if (this.bz == -1) {
            return;
        }
        switch (this.bz) {
            case 0: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(b1, b2, 64);
                    break;
                }
                this.a(b1, b2, 500);
                break;
            }
            case 1: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(b1, b2, 2, 2);
                    break;
                }
                this.a(b1, b2, 2);
                this.b4 = 4864;
                break;
            }
            case 2: {
                this.a(b1, b2, 128);
                break;
            }
            case 3: {
                this.a(b1, b2, 128);
                break;
            }
            case 4: {
                this.a(b1, b2, 64);
                break;
            }
            case 5: {
                this.a(b1, b2, 128);
                break;
            }
            case 6: {
                this.a(b1, b2, 1, 32);
                break;
            }
            case 7: {
                this.a(b1, b2, 64);
                break;
            }
            case 8: {
                this.a(b1, b2, 64);
                break;
            }
            case 9: {
                this.a(b1, b2, 128);
                break;
            }
            case 10: {
                this.a(b1, b2, 128);
                break;
            }
            case 11: {
                this.a(b1, b2, 40);
                break;
            }
            case 12: {
                this.a(b1, b2, 32768);
                break;
            }
            case 13: {
                this.a(b1, b2, 32768);
                break;
            }
            case 14: {
                this.a(b1, b2, 1, 64);
                break;
            }
            case 15: {
                this.a(b1, b2, 32768);
                break;
            }
            case 16: {
                this.a(b1, b2, 32768);
                break;
            }
            case 17: {
                this.a(b1, b2, 32768);
                break;
            }
            case 18: {
                this.a(b1, b2, 1, -1);
                break;
            }
            case 19: {
                this.b(b1, b2, 1, 64);
                break;
            }
            case 20: {
                this.b(b1, b2, -1);
                break;
            }
            case 21: {
                this.a(b1, b2, -1);
                break;
            }
            case 22: {
                this.a(b1, b2);
                break;
            }
            case 23: {
                this.a(b1, b2, 32768);
                break;
            }
            case 24: {
                this.a(b1, b2, 1, 1);
                break;
            }
            case 25: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(b1, b2, 2, 2);
                    break;
                }
                this.a(b1, b2, 2);
                this.b4 = 4864;
                break;
            }
            case 26: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(b1, b2, 2, 2);
                    break;
                }
                this.a(b1, b2, 2);
                this.b4 = 4864;
                break;
            }
        }
    }
    
    public String f() throws psc_v {
        return this.a(true);
    }
    
    public String a(final boolean b) throws psc_v {
        if (this.b5 == null) {
            if (this.b1 == null) {
                throw new psc_v("Cannot form the attribute's value as a String.");
            }
            return this.b();
        }
        else {
            final StringBuffer sb = new StringBuffer();
            final StringTokenizer stringTokenizer = new StringTokenizer(this.b5, ",+\"\\<>;", true);
            while (stringTokenizer.hasMoreElements()) {
                final String nextToken = stringTokenizer.nextToken();
                if (nextToken.length() == 1) {
                    final char char1 = nextToken.charAt(0);
                    switch (char1) {
                        case 34:
                        case 43:
                        case 44:
                        case 59:
                        case 60:
                        case 62:
                        case 92: {
                            sb.append('\\');
                            sb.append(char1);
                            continue;
                        }
                        default: {
                            sb.append(char1);
                            continue;
                        }
                    }
                }
                else {
                    sb.append(nextToken);
                }
            }
            final String string = sb.toString();
            final StringBuffer sb2 = new StringBuffer();
            final StringTokenizer stringTokenizer2 = new StringTokenizer(string, "#", true);
            while (stringTokenizer2.hasMoreElements()) {
                final String nextToken2 = stringTokenizer2.nextToken();
                if (nextToken2.equals("#")) {
                    if (sb2.length() == 0) {
                        sb2.append("\\#");
                    }
                    else {
                        sb2.append("\\23");
                    }
                }
                else {
                    sb2.append(nextToken2);
                }
            }
            String s = sb2.toString();
            final boolean b2 = s.length() == 1;
            if (s.startsWith(" ")) {
                final StringBuffer sb3 = new StringBuffer();
                sb3.append("\\");
                sb3.append(s);
                s = sb3.toString();
            }
            if (s.endsWith(" ") && !b2) {
                final StringBuffer sb4 = new StringBuffer();
                sb4.append(s);
                sb4.insert(s.length() - 1, "\\");
                s = sb4.toString();
            }
            final StringBuffer sb5 = new StringBuffer();
            if (b) {
                return s;
            }
            for (int i = 0; i < s.length(); ++i) {
                final char char2;
                final char c = char2 = s.charAt(i);
                if (char2 > '\u0080' || char2 < ' ') {
                    final String substring = s.substring(i, i + 1);
                    byte[] bytes = null;
                    try {
                        bytes = substring.getBytes("UTF-8");
                    }
                    catch (UnsupportedEncodingException ex) {}
                    for (int j = 0; j < bytes.length; ++j) {
                        sb5.append('\\');
                        final String upperCase = Integer.toHexString(bytes[j] & 0xFF).toUpperCase();
                        if (upperCase.length() == 1) {
                            sb5.append('0');
                        }
                        sb5.append(upperCase);
                    }
                }
                else {
                    sb5.append(c);
                }
            }
            return sb5.toString();
        }
    }
    
    public String g() throws psc_v {
        if (this.b5 == null && this.b1 == null) {
            throw new psc_v("Cannot form the attribute's value as a String.");
        }
        if (this.b5 == null) {
            return this.b();
        }
        return this.b5;
    }
    
    protected Object clone() throws CloneNotSupportedException {
        final psc_y psc_y = new psc_y();
        psc_y.bz = this.bz;
        if (this.b0 == null) {
            psc_y.b0 = null;
        }
        else {
            psc_y.b0 = new byte[this.b0.length];
            System.arraycopy(this.b0, 0, psc_y.b0, 0, this.b0.length);
        }
        if (this.b1 == null) {
            psc_y.b1 = null;
        }
        else {
            psc_y.b1 = new byte[this.b1.length];
            System.arraycopy(this.b1, 0, psc_y.b1, 0, this.b1.length);
        }
        psc_y.b2 = this.b2;
        psc_y.b3 = this.b3;
        psc_y.b4 = this.b4;
        psc_y.b5 = this.b5;
        return psc_y;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_y)) {
            return false;
        }
        final psc_y psc_y = (psc_y)o;
        return this.a(psc_y) && this.b(psc_y);
    }
    
    private boolean a(final psc_y psc_y) {
        if (this.b0 == null) {
            if (psc_y.b0 == null) {
                return this.bz == psc_y.bz;
            }
            final byte[] a = psc_y.bx[this.bz].a;
            return this.a(a, 0, a.length, psc_y.b0, 0, psc_y.b0.length);
        }
        else {
            if (psc_y.b0 == null) {
                final byte[] a2 = psc_y.bx[psc_y.bz].a;
                return this.a(this.b0, 0, this.b0.length, a2, 0, a2.length);
            }
            return this.a(this.b0, 0, this.b0.length, psc_y.b0, 0, psc_y.b0.length);
        }
    }
    
    public boolean b(final psc_y psc_y) {
        if (this.b5 != null) {
            return psc_y.b5 != null && this.b5.equals(psc_y.b5);
        }
        return this.b1 != null && psc_y.b1 != null && this.a(this.b1, this.b2, this.b3, psc_y.b1, psc_y.b2, psc_y.b3);
    }
    
    private boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array == null) {
            return array2 == null;
        }
        if (array2 == null) {
            return false;
        }
        if (n2 != n4) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (array[n + i] != array2[n3 + i]) {
                return false;
            }
        }
        return true;
    }
    
    private static int c(final byte[] array, final int n, final int n2) {
        if (array == null || n2 == 0) {
            return -1;
        }
        for (int i = 0; i < psc_y.bx.length; ++i) {
            if (n2 == psc_y.bx[i].a.length) {
                int n3;
                for (n3 = 0; n3 < n2 && (array[n3 + n] & 0xFF) == (psc_y.bx[i].a[n3] & 0xFF); ++n3) {}
                if (n3 >= n2) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    protected static int c(final String s) {
        if (s == null) {
            return -1;
        }
        for (int i = 0; i < psc_y.bx.length; ++i) {
            if (s.equalsIgnoreCase(psc_y.bx[i].b)) {
                return i;
            }
        }
        for (int j = 0; j < psc_y.by.length; ++j) {
            if (s.equalsIgnoreCase(psc_y.by[j].b)) {
                return psc_y.by[j].a;
            }
        }
        return -1;
    }
    
    protected static byte[] b(final int n) {
        if (n < 0 || n >= psc_y.bx.length) {
            return null;
        }
        return psc_y.bx[n].a;
    }
    
    private void a(final int n, final int n2, final String s) throws psc_v {
        if (s == null) {
            throw new psc_v("AVA value is null.");
        }
        switch (n) {
            case 0: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(n2, s, 64);
                    break;
                }
                this.a(n2, s, 500);
                break;
            }
            case 1: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(n2, s, 2, 2);
                    break;
                }
                this.a(n2, s, 2);
                this.b4 = 4864;
                break;
            }
            case 2: {
                this.a(n2, s, 128);
                break;
            }
            case 3: {
                this.a(n2, s, 128);
                break;
            }
            case 4: {
                this.a(n2, s, 64);
                break;
            }
            case 5: {
                this.a(n2, s, 128);
                break;
            }
            case 6: {
                this.a(n2, s, 1, 32);
                break;
            }
            case 7: {
                this.b(n2, s, 1, 64);
                break;
            }
            case 8: {
                this.a(n2, s, 64);
                break;
            }
            case 9: {
                this.a(n2, s, 128);
                break;
            }
            case 10: {
                this.a(n2, s, 128);
                break;
            }
            case 11: {
                this.a(n2, s, 40);
                break;
            }
            case 12: {
                this.a(n2, s, 32768);
                break;
            }
            case 13: {
                this.a(n2, s, 32768);
                break;
            }
            case 14: {
                this.a(n2, s, 1, 64);
                break;
            }
            case 15: {
                this.a(n2, s, 32768);
                break;
            }
            case 16: {
                this.a(n2, s, 32768);
                break;
            }
            case 17: {
                this.a(n2, s, 32768);
                break;
            }
            case 18: {
                this.a(n2, s, 1, -1);
                break;
            }
            case 19: {
                this.b(n2, s, 1, 64);
                break;
            }
            case 20: {
                this.a(n2, s, -1);
                break;
            }
            case 21: {
                this.a(n2, s, -1);
                break;
            }
            case 22: {
                this.a(n2, s);
                break;
            }
            case 23: {
                this.a(n2, s, 32768);
                break;
            }
            case 24: {
                this.a(n2, s, 1, 1);
                break;
            }
            case 25: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(n2, s, 2, 2);
                    break;
                }
                this.a(n2, s, 2);
                this.b4 = 4864;
                break;
            }
            case 26: {
                if (psc_ah.c(psc_nz.a)) {
                    this.a(n2, s, 2, 2);
                    break;
                }
                this.a(n2, s, 2);
                this.b4 = 4864;
                break;
            }
        }
    }
    
    private void a(final int n, final String s, final int n2) throws psc_v {
        if (n != 4864 && n != 5120 && n != 7168 && n != 3072 && n != 7680 && n != 5632) {
            throw new psc_v("DirectoryString expected.");
        }
        if (s.length() < 1) {
            throw new psc_v("DirectoryString too small.");
        }
        if (n2 != -1 && s.length() > n2) {
            throw new psc_v("DirectoryString too large.");
        }
    }
    
    private void a(final int n, final String s, final int n2, final int n3) throws psc_v {
        if (n != 4864) {
            throw new psc_v("PrintableString expected.");
        }
        if (n2 != -1 && s.length() < n2) {
            throw new psc_v("PrintableString too small.");
        }
        if (n3 != -1 && s.length() > n3) {
            throw new psc_v("PrintableString too large.");
        }
    }
    
    private void a(final int n, final String s) throws psc_v {
        if (n != 6144) {
            throw new psc_v("GenTime is expected expected.");
        }
    }
    
    private void b(final int n, final String s, final int n2, final int n3) throws psc_v {
        if (n != 5632) {
            throw new psc_v("IA5String expected.");
        }
        if (n2 != -1 && s.length() < n2) {
            throw new psc_v("IA5String too small.");
        }
        if (n3 != -1 && s.length() > n3) {
            throw new psc_v("IA5String too large.");
        }
    }
    
    private boolean a(final int n) {
        return n == 4864 || n == 5120 || n == 7168 || n == 5632 || n == 7680 || n == 3072 || n == 6144;
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        psc_aa psc_aa;
        psc_ac psc_ac;
        psc_ad psc_ad;
        psc_ae psc_ae;
        psc_af psc_af;
        psc_ag psc_ag;
        try {
            final psc_z psc_z = new psc_z(0);
            psc_aa = new psc_aa(0, 1, n2);
            psc_ac = new psc_ac(0, 1, n2);
            psc_ad = new psc_ad(0, 1, n2);
            psc_ae = new psc_ae(0, 1, n2);
            psc_af = new psc_af(0);
            psc_ag = new psc_ag(0, 1, n2);
            psc_l.a(array, n, new psc_i[] { psc_z, psc_aa, psc_ac, psc_ad, psc_af, psc_ae, psc_ag, new psc_j() });
        }
        catch (psc_m psc_m) {
            throw new psc_v("DirectoryString expected.");
        }
        if (psc_aa.a) {
            this.b4 = 4864;
            this.b5 = psc_aa.e();
        }
        else if (psc_ac.a) {
            this.b4 = 5120;
            this.b5 = psc_ac.e();
        }
        else if (psc_ad.a) {
            this.b4 = 7168;
            this.b5 = psc_ad.e();
        }
        else if (psc_ae.a) {
            this.b4 = 7680;
            this.b5 = psc_ae.e();
        }
        else if (psc_af.a) {
            this.b4 = 3072;
            this.b5 = this.d(psc_af.b, psc_af.c, psc_af.d);
        }
        else {
            if (!psc_ag.a) {
                throw new psc_v("DirectoryString expected.");
            }
            this.b4 = 5632;
            this.b5 = psc_ag.e();
        }
    }
    
    void b(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(65280));
            psc_l.a(array, n, new psc_i[] { psc_w });
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < psc_w.j(); ++i) {
                final psc_k psc_k = (psc_k)psc_w.a(i);
                this.a(psc_k.b, psc_k.c, n2);
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(this.b5);
            }
            this.b5 = sb.toString();
            this.b4 = 3072;
        }
        catch (psc_m psc_m) {
            throw new psc_v("Invalid encoding. " + psc_m.getMessage());
        }
    }
    
    void a(final byte[] array, final int n, final int n2, final int n3) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        psc_aa psc_aa;
        try {
            psc_aa = new psc_aa(0, n2, n3);
            psc_l.a(array, n, new psc_i[] { psc_aa });
        }
        catch (psc_m psc_m) {
            throw new psc_v("PrintableString expected.");
        }
        this.b4 = 4864;
        this.b5 = psc_aa.e();
    }
    
    void a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        psc_ai psc_ai;
        try {
            psc_ai = new psc_ai(0);
            psc_l.a(array, n, new psc_i[] { psc_ai });
        }
        catch (psc_m psc_m) {
            throw new psc_v("GenTime expected." + psc_m.getMessage());
        }
        this.b4 = 6144;
        this.b5 = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss z").format(psc_ai.a);
    }
    
    void b(final byte[] array, final int n, final int n2, final int n3) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        psc_ag psc_ag;
        try {
            psc_ag = new psc_ag(0, n2, n3);
            psc_l.a(array, n, new psc_i[] { psc_ag });
        }
        catch (psc_m psc_m) {
            throw new psc_v("IA5String expected.");
        }
        this.b4 = 5632;
        this.b5 = psc_ag.e();
    }
    
    public String toString() {
        return this.b(true);
    }
    
    public String b(final boolean b) {
        String a = null;
        final StringBuffer sb = new StringBuffer();
        try {
            if (this.b5 != null) {
                a = this.a(b);
            }
        }
        catch (psc_v psc_v) {}
        if (this.bz == -1) {
            sb.append(this.a());
            sb.append("=");
            if (a != null) {
                sb.append(a);
            }
            else if (this.b1 != null) {
                sb.append(this.b());
            }
        }
        else {
            if (this.bz == 7 && psc_ah.c(psc_nz.d)) {
                sb.append("Ea");
            }
            else {
                sb.append(psc_y.bx[this.bz].b);
            }
            sb.append("=");
            sb.append(a);
        }
        return sb.toString();
    }
    
    private String a() {
        if (this.b0 == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        int j = 0;
        StringBuffer sb2 = sb.append((this.b0[i] & 0xFF) / 40).append('.').append((this.b0[i] & 0xFF) % 40);
        ++i;
        while (i < this.b0.length) {
            final StringBuffer append = sb2.append('.');
            do {
                j = (j << 7) + (this.b0[i] & 0x7F & 0xFF);
            } while ((this.b0[i++] & 0x80) != 0x0 && i < this.b0.length);
            sb2 = append.append(j);
            j = 0;
        }
        return sb2.toString();
    }
    
    private String b() {
        if (this.b1 == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer().append("#");
        for (int i = this.b2; i < this.b3 + this.b2; ++i) {
            final String hexString = Integer.toHexString(this.b1[i] & 0xFF);
            if (hexString.length() == 1) {
                sb = sb.append("0");
            }
            sb = sb.append(hexString);
        }
        return sb.toString();
    }
    
    private psc_i c() throws psc_v {
        return new psc_ai(0, true, 0, new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss z").parse(this.b5, new ParsePosition(0)));
    }
    
    private psc_i a(final int n, final int n2) throws psc_v {
        try {
            this.b6 = new psc_n(new psc_i[] { new psc_h(0), this.b(n, n2), new psc_j() });
            final byte[] array = new byte[this.b6.a()];
            return new psc_k(12288, true, 0, array, 0, this.b6.a(array, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_v("Invalid String." + psc_m.getMessage());
        }
    }
    
    private psc_i b(final int n, final int n2) throws psc_v {
        try {
            switch (this.b4) {
                case 4864: {
                    return new psc_aa(0, true, 0, this.b5, n, n2);
                }
                case 5120: {
                    return new psc_ac(0, true, 0, this.b5, n, n2);
                }
                case 7168: {
                    return new psc_ad(0, true, 0, this.b5, n, n2);
                }
                case 5632: {
                    return new psc_ag(0, true, 0, this.b5, n, n2);
                }
                case 3072: {
                    final byte[] a = this.a(this.b5);
                    if (a.length < 2) {
                        throw new psc_v("AttributeValueAssertion.createStringContainer: DataOutputStream.writeUTF() did not contain necessary 2 bytes specifying the encoding length.");
                    }
                    return new psc_af(0, true, 0, a, 2, a.length - 2);
                }
                case 7680: {
                    return new psc_ae(0, true, 0, this.b5, n, n2);
                }
                default: {
                    return null;
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Invalid String." + psc_m.getMessage());
        }
    }
    
    private byte[] a(final String s) throws psc_v {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
        }
        catch (IOException ex) {
            throw new psc_v("AttributeValueAssertion.utf8Encode: unable to utf8-encode " + s + "(" + ex.getMessage() + ").");
        }
        return out.toByteArray();
    }
    
    private String d(final byte[] array, final int n, final int n2) throws psc_v {
        final byte[] buf = new byte[n2 + 2];
        buf[0] = (byte)(n2 >> 8 & 0xFF);
        buf[1] = (byte)(n2 & 0xFF);
        System.arraycopy(array, n, buf, 2, n2);
        try {
            return new DataInputStream(new ByteArrayInputStream(buf, 0, buf.length)).readUTF();
        }
        catch (IOException ex) {
            throw new psc_v("AttributeValueAssertion.utf8Decode: " + ex.getMessage() + ").");
        }
    }
    
    public int h() {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            psc_r psc_r;
            if (this.bz != -1) {
                final byte[] a = psc_y.bx[this.bz].a;
                psc_r = new psc_r(16777216, true, 0, a, 0, a.length);
            }
            else {
                psc_r = new psc_r(16777216, true, 0, this.b0, 0, this.b0.length);
            }
            psc_i psc_i = null;
            try {
                switch (this.bz) {
                    case 0: {
                        if (psc_ah.c(psc_nz.a)) {
                            psc_i = this.b(1, 64);
                            break;
                        }
                        psc_i = this.b(1, 500);
                        break;
                    }
                    case 1: {
                        psc_i = this.b(2, 2);
                        break;
                    }
                    case 2: {
                        psc_i = this.b(1, 128);
                        break;
                    }
                    case 3: {
                        psc_i = this.b(1, 128);
                        break;
                    }
                    case 4: {
                        psc_i = this.b(1, 64);
                        break;
                    }
                    case 5: {
                        psc_i = this.b(1, 128);
                        break;
                    }
                    case 6: {
                        psc_i = this.b(1, 32);
                        break;
                    }
                    case 7: {
                        psc_i = this.b(1, 64);
                        break;
                    }
                    case 8: {
                        psc_i = this.b(1, 64);
                        break;
                    }
                    case 9: {
                        psc_i = this.b(1, 128);
                        break;
                    }
                    case 10: {
                        psc_i = this.b(1, 128);
                        break;
                    }
                    case 11: {
                        psc_i = this.b(1, 40);
                        break;
                    }
                    case 12: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 13: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 14: {
                        psc_i = this.b(1, 64);
                        break;
                    }
                    case 15: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 16: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 17: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 18: {
                        psc_i = this.b(1, -1);
                        break;
                    }
                    case 19: {
                        psc_i = this.b(1, 64);
                        break;
                    }
                    case 20: {
                        psc_i = this.a(1, -1);
                        break;
                    }
                    case 21: {
                        psc_i = this.b(1, -1);
                        break;
                    }
                    case 22: {
                        psc_i = this.c();
                        break;
                    }
                    case 23: {
                        psc_i = this.b(1, 32768);
                        break;
                    }
                    case 24: {
                        psc_i = this.b(1, 1);
                        break;
                    }
                    case 25: {
                        psc_i = this.b(2, 2);
                        break;
                    }
                    case 26: {
                        psc_i = this.b(2, 2);
                        break;
                    }
                    default: {
                        if (this.b4 == psc_y.c) {
                            psc_i = new psc_k(65280, true, 0, this.b1, this.b2, this.b3);
                            break;
                        }
                        psc_i = this.b(-1, -1);
                        break;
                    }
                }
            }
            catch (psc_v psc_v) {
                return 0;
            }
            this.b6 = new psc_n(new psc_i[] { psc_h, psc_r, psc_i, new psc_j() });
            return this.b6.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.b6 == null && this.h() == 0) {
                throw new psc_v("Could not encode AVA. ");
            }
            return this.b6.a(array, n);
        }
        catch (psc_m psc_m) {
            this.b6 = null;
            throw new psc_v("Could not encode: " + psc_m.getMessage());
        }
    }
    
    static {
        psc_y.c = -1;
        psc_y.e = new byte[] { 85, 4, 3 };
        psc_y.i = new byte[] { 85, 4, 6 };
        psc_y.l = new byte[] { 85, 4, 7 };
        psc_y.o = new byte[] { 85, 4, 8 };
        psc_y.r = new byte[] { 85, 4, 10 };
        psc_y.u = new byte[] { 85, 4, 11 };
        psc_y.x = new byte[] { 85, 4, 20 };
        psc_y.aa = new byte[] { 42, -122, 72, -122, -9, 13, 1, 9, 1 };
        psc_y.ad = new byte[] { 85, 4, 12 };
        psc_y.ag = new byte[] { 85, 4, 9 };
        psc_y.aj = new byte[] { 85, 4, 15 };
        psc_y.am = new byte[] { 85, 4, 17 };
        psc_y.ap = new byte[] { 85, 4, 4 };
        psc_y.as = new byte[] { 85, 4, 42 };
        psc_y.av = new byte[] { 85, 4, 5 };
        psc_y.ay = new byte[] { 85, 4, 43 };
        psc_y.a1 = new byte[] { 85, 4, 44 };
        psc_y.a4 = new byte[] { 85, 4, 41 };
        psc_y.a7 = new byte[] { 85, 4, 46 };
        psc_y.ba = new byte[] { 9, -110, 38, -119, -109, -14, 44, 100, 1, 25 };
        psc_y.bd = new byte[] { 85, 4, 16 };
        psc_y.bg = new byte[] { 85, 4, 65 };
        psc_y.bj = new byte[] { 43, 6, 1, 5, 5, 7, 9, 1 };
        psc_y.bm = new byte[] { 43, 6, 1, 5, 5, 7, 9, 2 };
        psc_y.bp = new byte[] { 43, 6, 1, 5, 5, 7, 9, 3 };
        psc_y.bs = new byte[] { 43, 6, 1, 5, 5, 7, 9, 4 };
        psc_y.bv = new byte[] { 43, 6, 1, 5, 5, 7, 9, 5 };
        bx = new psc_mt[] { new psc_mt(psc_y.e, "CN"), new psc_mt(psc_y.i, "C"), new psc_mt(psc_y.l, "L"), new psc_mt(psc_y.o, "ST"), new psc_mt(psc_y.r, "O"), new psc_mt(psc_y.u, "OU"), new psc_mt(psc_y.x, "TEL"), new psc_mt(psc_y.aa, "E"), new psc_mt(psc_y.ad, "TITLE"), new psc_mt(psc_y.ag, "STREET"), new psc_mt(psc_y.aj, "BC"), new psc_mt(psc_y.am, "postalCode"), new psc_mt(psc_y.ap, "SN"), new psc_mt(psc_y.as, "givenName"), new psc_mt(psc_y.av, "serialNumber"), new psc_mt(psc_y.ay, "initials"), new psc_mt(psc_y.a1, "generationQualifier"), new psc_mt(psc_y.a4, "name"), new psc_mt(psc_y.a7, "dnQualifier"), new psc_mt(psc_y.ba, "dc"), new psc_mt(psc_y.bd, "postalAddress"), new psc_mt(psc_y.bg, "pseudonym"), new psc_mt(psc_y.bj, "dateOfBirth"), new psc_mt(psc_y.bm, "placeOfBirth"), new psc_mt(psc_y.bp, "gender"), new psc_mt(psc_y.bs, "citizenship"), new psc_mt(psc_y.bv, "residence") };
        by = new psc_mu[] { new psc_mu(7, "email"), new psc_mu(7, "EmailAddress"), new psc_mu(7, "Ea") };
    }
}
