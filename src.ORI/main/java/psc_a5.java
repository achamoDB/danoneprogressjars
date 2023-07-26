import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.io.PrintWriter;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_a5
{
    public static int a;
    public static final String b;
    public static final PrintWriter c;
    
    protected psc_a5() {
    }
    
    public static void a(final String s, final boolean b) {
        if (!b) {
            a(s);
        }
    }
    
    public static void a(final boolean b) {
        a((String)null, b);
    }
    
    public static void a(final String s) {
        throw new psc_eg(s);
    }
    
    public static void b() {
        a((String)null);
    }
    
    public static void a(final String s, final Object o, final Object obj) {
        if (o == null && obj == null) {
            return;
        }
        if (o != null && o.equals(obj)) {
            return;
        }
        d(s, o, obj);
    }
    
    public static void a(final Object o, final Object o2) {
        a(null, o, o2);
    }
    
    public static void a(final String s, final double value, final double n, final double n2) {
        if (Double.isInfinite(value)) {
            if (value != n) {
                d(s, new Double(value), new Double(n));
            }
        }
        else if (Math.abs(value - n) > n2) {
            d(s, new Double(value), new Double(n));
        }
    }
    
    public static void a(final double n, final double n2, final double n3) {
        a(null, n, n2, n3);
    }
    
    public static void a(final String s, final float value, final float n, final float n2) {
        if (Float.isInfinite(value)) {
            if (value != n) {
                d(s, new Float(value), new Float(n));
            }
        }
        else if (Math.abs(value - n) > n2) {
            d(s, new Float(value), new Float(n));
        }
    }
    
    public static void a(final float n, final float n2, final float n3) {
        a(null, n, n2, n3);
    }
    
    public static void a(final String s, final long value, final long value2) {
        a(s, new Long(value), new Long(value2));
    }
    
    public static void a(final long n, final long n2) {
        a(null, n, n2);
    }
    
    public static void a(final String s, final boolean value, final boolean value2) {
        a(s, new Boolean(value), new Boolean(value2));
    }
    
    public static void a(final boolean b, final boolean b2) {
        a(null, b, b2);
    }
    
    public static void a(final String s, final byte value, final byte value2) {
        a(s, new Byte(value), new Byte(value2));
    }
    
    public static void a(final byte b, final byte b2) {
        a(null, b, b2);
    }
    
    public static void a(final String s, final char value, final char value2) {
        a(s, new Character(value), new Character(value2));
    }
    
    public static void a(final char c, final char c2) {
        a(null, c, c2);
    }
    
    public static void a(final String s, final short value, final short value2) {
        a(s, new Short(value), new Short(value2));
    }
    
    public static void a(final short n, final short n2) {
        a(null, n, n2);
    }
    
    public static void a(final String s, final int value, final int value2) {
        a(s, new Integer(value), new Integer(value2));
    }
    
    public static void a(final int n, final int n2) {
        a((String)null, n, n2);
    }
    
    public static void a(final Object o) {
        a(null, o);
    }
    
    public static void a(final String s, final Object o) {
        a(s, o != null);
    }
    
    public static void b(final Object o) {
        b(null, o);
    }
    
    public static void b(final String s, final Object o) {
        a(s, o == null);
    }
    
    public static void b(final String s, final Object o, final Object o2) {
        if (o == o2) {
            return;
        }
        e(s, o, o2);
    }
    
    public static void b(final Object o, final Object o2) {
        b(null, o, o2);
    }
    
    private static void d(final String str, final Object obj, final Object obj2) {
        String string = "";
        if (str != null) {
            string = str + " ";
        }
        a(string + "expected:<" + obj + "> but was:<" + obj2 + ">");
    }
    
    private static void e(final String str, final Object o, final Object o2) {
        String string = "";
        if (str != null) {
            string = str + " ";
        }
        a(string + "expected same");
    }
    
    public static void a(final byte[] array, final byte[] array2) {
        a(null, array, array2);
    }
    
    public static void a(final String s, final byte[] array, final byte[] array2) {
        if (array == null && array2 == null) {
            return;
        }
        if (array != null && b(array, array2)) {
            return;
        }
        c(s, array, array2);
    }
    
    public static void a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        a(null, array, n, n2, array2, n3, n4);
    }
    
    public static void a(final String s, final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array == null && array2 == null) {
            return;
        }
        if (array != null && b(array, n, n2, array2, n3, n4)) {
            return;
        }
        b(s, array, n, n2, array2, n3, n4);
    }
    
    public static void b(final String s, final byte[] array, final byte[] array2) {
        if (!b(array, array2)) {
            return;
        }
        c(s, a(array, 0, array.length, false), a(array2, 0, array2.length, false));
    }
    
    private static void c(final String str, final Object obj, final Object obj2) {
        String string = "";
        if (str != null) {
            string = str + " - ";
        }
        a(string + "Not expected:<" + obj + "> yet it was:<" + obj2 + ">");
    }
    
    public static boolean b(final byte[] array, final byte[] array2) {
        return b(array, 0, array.length, array2, 0, array2.length);
    }
    
    public static boolean b(final byte[] array, int i, final int n, final byte[] array2, int n2, final int n3) {
        if (array == null || array2 == null) {
            return array == null && array2 == null;
        }
        if (n != n3) {
            return false;
        }
        while (i < n) {
            if (array[i] != array2[n2]) {
                return false;
            }
            ++i;
            ++n2;
        }
        return true;
    }
    
    private static void c(final String str, final byte[] array, final byte[] array2) {
        String string = "";
        if (str != null) {
            string = str + " ";
        }
        a(string + "expected:<" + a(array, 0, array.length, false) + "> but was:<" + a(array2, 0, array2.length, false) + ">");
    }
    
    private static void b(final String str, final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        String string = "";
        if (str != null) {
            string = str + " ";
        }
        a(string + "expected:<" + a(array, n, n2, false) + "> but was:<" + a(array2, n3, n4, false) + ">");
    }
    
    public static String a(final byte[] array, final int n, final int n2, final boolean b) {
        final byte[] array2 = new byte[array.length];
        System.arraycopy(array, n, array2, 0, n2);
        return a(array2, b);
    }
    
    public static String a(final byte[] array, final boolean b) {
        String string = "";
        int n = 0;
        int i = array.length;
        while (i > 0) {
            StringBuffer sb = new StringBuffer();
            for (int n2 = 0; n2 < 8 && i != 0; ++n2, ++n) {
                final String hexString = Integer.toHexString(array[n] & 0xFF);
                if (b) {
                    if (hexString.length() == 1) {
                        sb = sb.append(" 0x0");
                    }
                    else {
                        sb = sb.append(" 0x");
                    }
                }
                sb = sb.append(hexString);
                --i;
                if (b && i != 0) {
                    sb = sb.append(",");
                }
            }
            string += sb.toString();
        }
        return string;
    }
    
    public static byte[] b(final String str) {
        if (str.equals("")) {
            return new byte[0];
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
        String str2 = "";
        while (stringTokenizer.hasMoreTokens()) {
            str2 += stringTokenizer.nextToken();
        }
        if (str2.substring(0, 2).equals("0x")) {
            str2 = str2.substring(2);
        }
        final byte[] array = new byte[(str2.length() + 1) / 2];
        int i = 0;
        int beginIndex = 0;
        if (str2.length() % 2 != 0) {
            try {
                array[0] = Byte.parseByte("0" + str2.substring(0, 1), 16);
            }
            catch (NumberFormatException ex) {
                return null;
            }
            i = 1;
            beginIndex = 1;
        }
        try {
            while (i < array.length) {
                array[i] = Byte.parseByte(str2.substring(beginIndex, beginIndex + 1), 16);
                array[i] *= 16;
                array[i] += Byte.parseByte(str2.substring(beginIndex + 1, beginIndex + 2), 16);
                ++i;
                beginIndex += 2;
            }
        }
        catch (NumberFormatException ex2) {
            return null;
        }
        return array;
    }
    
    public static String a(final byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = array.length, n = 0; i > 0; --i, ++n) {
            final String hexString = Integer.toHexString(array[n] & 0xFF);
            if (hexString.length() == 1) {
                sb = sb.append("0");
            }
            sb = sb.append(hexString);
        }
        return sb.toString();
    }
    
    public static String a(final byte[] array, final int n, int i) {
        StringBuffer sb = new StringBuffer();
        for (int n2 = n; i > 0; --i, ++n2) {
            final String hexString = Integer.toHexString(array[n2] & 0xFF);
            if (hexString.length() == 1) {
                sb = sb.append("0");
            }
            sb = sb.append(hexString);
        }
        return sb.toString();
    }
    
    public static byte[] a(final String s, final String charsetName) {
        try {
            return s.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException ex) {
            return s.getBytes();
        }
    }
    
    public static byte[] b(final byte[] array) throws Throwable {
        final byte[] array2 = new byte[psc_a5.a];
        final byte[] array3 = new byte[psc_a5.a];
        System.arraycopy(array, 0, array2, 0, psc_a5.a);
        System.arraycopy(array, psc_a5.a, array3, 0, psc_a5.a);
        return c(array2, array3);
    }
    
    public static byte[] c(final byte[] array, final byte[] array2) throws Throwable {
        return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_p(0, true, 0, array, 0, array.length, true), new psc_p(0, true, 0, array2, 0, array2.length, true), new psc_j() });
    }
    
    public static byte[] c(final byte[] array) throws Exception {
        final byte[][] d = d(array);
        final byte[] array2 = new byte[2 * psc_a5.a];
        System.arraycopy(d[0], 0, array2, 0, psc_a5.a);
        System.arraycopy(d[1], 0, array2, psc_a5.a, psc_a5.a);
        return array2;
    }
    
    public static byte[][] d(final byte[] array) throws Exception {
        final byte[][] array2 = { new byte[psc_a5.a], new byte[psc_a5.a] };
        final byte[] array3 = array2[0];
        final byte[] array4 = array2[1];
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, 0);
            final psc_p psc_p2 = new psc_p(0, true, 0, 0);
            psc_l.a(array, 0, new psc_i[] { psc_h, psc_p, psc_p2, psc_j });
            for (int n = psc_p.c + psc_p.d - 1, i = psc_a5.a - 1; i >= 0; --i, --n) {
                if (n >= psc_p.c) {
                    array3[i] = psc_p.b[n];
                }
            }
            for (int n2 = psc_p2.c + psc_p2.d - 1, j = psc_a5.a - 1; j >= 0; --j, --n2) {
                if (n2 >= psc_p2.c) {
                    array4[j] = psc_p2.b[n2];
                }
            }
            return array2;
        }
        catch (psc_m psc_m) {
            throw new Exception(psc_m.toString());
        }
    }
    
    static {
        psc_a5.a = 20;
        b = System.getProperty("line.separator");
        c = new PrintWriter(System.out, true);
    }
}
