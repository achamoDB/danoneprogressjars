import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_u implements Cloneable, Serializable
{
    private Vector a;
    protected int b;
    protected psc_n c;
    private Vector d;
    private static final char[] e;
    
    public psc_u(final byte[] array, final int n, final int n2) throws psc_v, RuntimeException {
        this.a = new Vector();
        this.c = null;
        this.d = new Vector();
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12544));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a.addElement(new psc_x(a.b, a.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the name." + psc_m.getMessage());
        }
    }
    
    public psc_u() throws RuntimeException {
        this.a = new Vector();
        this.c = null;
        this.d = new Vector();
    }
    
    public psc_u(final String str) throws psc_v, RuntimeException {
        this.a = new Vector();
        this.c = null;
        this.d = new Vector();
        if (str == null) {
            throw new psc_v("The string is null.");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "\\,;", true);
        final int countTokens = stringTokenizer.countTokens();
        StringBuffer sb = new StringBuffer();
        final Vector vector = new Vector<String>();
        final String s = new String();
        for (int i = 0; i < countTokens; ++i) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("\\")) {
                sb.append(nextToken);
                sb.append(stringTokenizer.nextToken());
                ++i;
            }
            else if (!nextToken.equals(",") && !nextToken.equals(";")) {
                sb.append(nextToken);
            }
            else {
                final String nextToken2 = stringTokenizer.nextToken();
                ++i;
                if (nextToken2.indexOf("=") == -1) {
                    sb.append(",");
                    sb.append(nextToken2);
                }
                else {
                    for (int beginIndex = 0; nextToken2.substring(beginIndex).startsWith(" "); ++beginIndex) {}
                    vector.addElement(sb.toString());
                    sb = new StringBuffer(nextToken2);
                }
            }
        }
        if (sb.length() != 0) {
            vector.addElement(sb.toString());
        }
        final psc_x psc_x = new psc_x();
        for (int j = vector.size() - 1; j >= 0; --j) {
            final StringTokenizer stringTokenizer2 = new StringTokenizer(vector.elementAt(j), "\\+", true);
            final int countTokens2 = stringTokenizer2.countTokens();
            final Vector vector2 = new Vector<String>();
            StringBuffer sb2 = new StringBuffer();
            for (int k = 0; k < countTokens2; ++k) {
                final String nextToken3 = stringTokenizer2.nextToken();
                if (nextToken3.equals("\\")) {
                    final StringBuffer sb3 = new StringBuffer(stringTokenizer2.nextToken());
                    this.a(sb3);
                    sb2.append(sb3.toString());
                    ++k;
                }
                else {
                    sb2.append(this.a());
                    if (!nextToken3.equals("+")) {
                        sb2.append(nextToken3);
                    }
                    else {
                        vector2.addElement(sb2.toString());
                        sb2 = new StringBuffer();
                    }
                }
            }
            sb2.append(this.a());
            if (sb2.length() != 0) {
                vector2.addElement(sb2.toString());
            }
            final psc_x obj = new psc_x();
            for (int l = 0; l < vector2.size(); ++l) {
                final String s2 = vector2.elementAt(l);
                final int index = s2.indexOf(61);
                if (index == -1) {
                    throw new psc_v("AVA representation is invalid, should be 'type=value'");
                }
                int beginIndex3;
                int beginIndex2;
                for (beginIndex2 = (beginIndex3 = 0); beginIndex3 < index; ++beginIndex3) {
                    if (!s2.substring(beginIndex3, beginIndex3 + 1).equals(" ")) {
                        beginIndex2 = beginIndex3;
                        break;
                    }
                }
                final String substring = s2.substring(beginIndex2, index);
                final int n = index + 1;
                final int c = psc_y.c(substring);
                psc_y psc_y;
                if (c == -1) {
                    psc_y = new psc_y(this.b(substring), this.a(s2.substring(n)));
                }
                else {
                    psc_y = new psc_y(c, psc_y.b(c), 0, s2.substring(n));
                }
                obj.a(psc_y);
            }
            this.a.addElement(obj);
        }
    }
    
    private void a(final StringBuffer sb) throws psc_v {
        if (b(sb)) {
            return;
        }
        this.d.addElement(new Byte(c(sb)));
        if (sb.length() > 2) {
            sb.reverse();
            sb.setLength(sb.length() - 2);
            sb.reverse();
            sb.insert(0, this.a());
            return;
        }
        sb.setLength(0);
    }
    
    private String a() {
        final int size = this.d.size();
        if (size == 0) {
            return new String();
        }
        final byte[] bytes = new byte[size];
        for (int i = 0; i < size; ++i) {
            bytes[i] = (byte)this.d.elementAt(i);
        }
        String s = null;
        try {
            s = new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {}
        this.d.removeAllElements();
        return s;
    }
    
    private static boolean b(final StringBuffer sb) {
        char upperCase;
        int n;
        for (upperCase = Character.toUpperCase(sb.charAt(0)), n = 0; n < psc_u.e.length && upperCase != psc_u.e[n]; ++n) {}
        return n != psc_u.e.length;
    }
    
    private static byte c(final StringBuffer sb) throws psc_v {
        if (sb.length() < 2) {
            throw new psc_v("Invalid UTF-8 escape sequence: Two characters per hex byte required");
        }
        try {
            return (byte)(Integer.parseInt(sb.toString().substring(0, 2), 16) & 0xFF);
        }
        catch (NumberFormatException ex) {
            throw new psc_v("Invalid UTF-8 escape sequence: Escaped hex byte invalid");
        }
    }
    
    private byte[] a(final String s) throws psc_v {
        String substring;
        if (s.startsWith("#")) {
            substring = s.substring(1);
        }
        else {
            substring = s;
        }
        final int length = substring.length();
        byte[] array;
        if (length % 2 == 0) {
            array = new byte[length / 2];
        }
        else {
            array = new byte[length / 2 + 1];
        }
        for (int i = array.length - 1, n = length; i >= 0; --i, n -= 2) {
            if (n == 1) {
                array[i] = (byte)Integer.parseInt(substring.substring(n - 1, n), 16);
            }
            else if (n == length) {
                array[i] = (byte)Integer.parseInt(substring.substring(n - 2), 16);
            }
            else {
                array[i] = (byte)Integer.parseInt(substring.substring(n - 2, n), 16);
            }
        }
        return array;
    }
    
    private byte[] b(final String str) throws psc_v {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
        final int countTokens = stringTokenizer.countTokens();
        if (countTokens < 2) {
            throw new psc_v("Invalid OID: should be the dotted-decimal encoding with at least 2 components.");
        }
        final Integer[] array = new Integer[countTokens];
        try {
            for (int i = 0; i < countTokens; ++i) {
                array[i] = new Integer(stringTokenizer.nextToken());
            }
        }
        catch (NumberFormatException ex) {
            throw new psc_v("Cannot convert String to int. " + ex.getMessage());
        }
        byte[] array2 = { (byte)(array[0] * 40 + array[1]) };
        for (int j = 2; j < countTokens; ++j) {
            final byte[] array3 = new byte[5];
            int n = 4;
            for (int k = array[j]; k != 0; k >>>= 7, --n) {
                array3[n] = (byte)(k & 0x7F);
                final byte[] array4 = array3;
                final int n2 = n;
                array4[n2] |= (byte)128;
            }
            final byte[] array5 = array3;
            final int n3 = 4;
            array5[n3] &= 0x7F;
            final byte[] array6 = new byte[5 - (n + 1)];
            System.arraycopy(array3, n + 1, array6, 0, array6.length);
            final byte[] array7 = new byte[array2.length];
            System.arraycopy(array2, 0, array7, 0, array2.length);
            array2 = new byte[array2.length + array6.length];
            System.arraycopy(array7, 0, array2, 0, array7.length);
            System.arraycopy(array6, 0, array2, array7.length, array6.length);
        }
        return array2;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_u psc_u = new psc_u();
        for (int i = 0; i < this.a.size(); ++i) {
            psc_u.a.addElement(((psc_x)this.a.elementAt(i)).clone());
        }
        psc_u.b = this.b;
        if (this.c != null) {
            psc_u.b();
        }
        return psc_u;
    }
    
    private String a(final boolean b, final boolean b2) {
        final StringBuffer sb = new StringBuffer();
        if (this.a.size() == 0) {
            return "";
        }
        if (b) {
            for (int i = this.a.size() - 1; i >= 1; --i) {
                sb.append(((psc_x)this.a.elementAt(i)).a(b2));
                sb.append(",");
            }
            sb.append(this.a.elementAt(0).a(b2));
        }
        else {
            for (int j = 0; j < this.a.size() - 1; ++j) {
                sb.append(((psc_x)this.a.elementAt(j)).a(b2));
                sb.append(",");
            }
            sb.append(this.a.elementAt(this.a.size() - 1).a(b2));
        }
        return sb.toString();
    }
    
    public String a(final boolean b) {
        return this.a(b, false);
    }
    
    public String toString() {
        return this.a(true, true);
    }
    
    public String c() {
        return this.a(true, false);
    }
    
    public int d() {
        return this.a.size();
    }
    
    public int e() {
        int n = 0;
        for (int i = 0; i < this.a.size(); ++i) {
            n += ((psc_x)this.a.elementAt(i)).b();
        }
        return n;
    }
    
    public psc_x a(final int index) throws psc_v {
        final int size = this.a.size();
        if (index >= 0 && size > index) {
            return this.a.elementAt(index);
        }
        throw new psc_v("Invalid Index.");
    }
    
    public void b(final int index) throws psc_v {
        if (index < this.a.size()) {
            this.a.removeElementAt(index);
            return;
        }
        throw new psc_v("Invalid index.");
    }
    
    public void a(final psc_x obj, final int index) throws psc_v {
        if (obj == null) {
            throw new psc_v("Specified RDN is null.");
        }
        if (index < this.a.size()) {
            this.a.setElementAt(obj, index);
            return;
        }
        throw new psc_v("Invalid index.");
    }
    
    public psc_y c(final int n) {
        for (int size = this.a.size(), i = 0; i < size; ++i) {
            final psc_y c = this.a.elementAt(i).c(n);
            if (c != null) {
                return c;
            }
        }
        return null;
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
            throw new psc_v("Unable to determine length of the BER" + psc_m.getMessage());
        }
    }
    
    public int d(final int b) {
        this.b = b;
        return this.b();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.c == null || n2 != this.b) {
                this.d(n2);
                if (this.c == null) {
                    throw new psc_v("Unable to encode X500Name.");
                }
            }
            final int a = this.c.a(array, n);
            this.c = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.c = null;
            throw new psc_v("Unable to encode X500Name." + psc_m.getMessage());
        }
    }
    
    private int b() {
        try {
            final int size = this.a.size();
            psc_w psc_w;
            if (size == 0) {
                psc_w = new psc_w(this.b, false, 0, 12288, new psc_k(12544));
            }
            else {
                psc_w = new psc_w(this.b, true, 0, 12288, new psc_k(12544));
                for (int i = 0; i < size; ++i) {
                    final psc_x psc_x = this.a.elementAt(i);
                    final byte[] array = new byte[psc_x.d(0)];
                    psc_w.b(new psc_k(12544, true, 0, array, 0, psc_x.b(array, 0, 0)));
                }
            }
            this.c = new psc_n(new psc_i[] { psc_w });
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public void a(final psc_x obj) throws psc_v {
        if (obj == null) {
            throw new psc_v("Specified RDN is null.");
        }
        this.a.addElement(obj);
    }
    
    public void b(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        this.a.addElement(new psc_x(array, n, 0));
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_u)) {
            return false;
        }
        final psc_u psc_u = (psc_u)o;
        final int d = this.d();
        if (d != psc_u.d()) {
            return false;
        }
        for (int i = 0; i < d; ++i) {
            if (!((psc_x)this.a.elementAt(i)).equals(psc_u.a.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean a(final psc_u psc_u) {
        if (psc_u == null) {
            return false;
        }
        final int d = this.d();
        for (int d2 = psc_u.d(), i = 0; i < d2; ++i) {
            int n;
            psc_x psc_x;
            for (n = 0, psc_x = psc_u.a.elementAt(i); n < d && !psc_x.equals(this.a.elementAt(n)); ++n) {}
            if (n >= d) {
                return false;
            }
            if (!psc_x.equals(this.a.elementAt(n))) {
                return false;
            }
        }
        return true;
    }
    
    static {
        e = new char[] { ',', '=', '+', '<', '>', '#', ';', '\"', '\\' };
    }
}
