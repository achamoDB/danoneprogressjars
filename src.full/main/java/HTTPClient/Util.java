// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.net.URL;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.BitSet;

public class Util
{
    private static final BitSet Separators;
    private static final BitSet TokenChar;
    private static final BitSet UnsafeChar;
    private static DateFormat http_format;
    private static DateFormat parse_1123;
    private static DateFormat parse_850;
    private static DateFormat parse_asctime;
    private static final Object http_format_lock;
    private static final Object http_parse_lock;
    static final char[] hex_map;
    
    private Util() {
    }
    
    static final Object[] resizeArray(final Object[] array, final int length) {
        final Object[] array2 = (Object[])Array.newInstance(array.getClass().getComponentType(), length);
        System.arraycopy(array, 0, array2, 0, (array.length < length) ? array.length : length);
        return array2;
    }
    
    static final NVPair[] resizeArray(final NVPair[] array, final int n) {
        final NVPair[] array2 = new NVPair[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final AuthorizationInfo[] resizeArray(final AuthorizationInfo[] array, final int n) {
        final AuthorizationInfo[] array2 = new AuthorizationInfo[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final Cookie[] resizeArray(final Cookie[] array, final int n) {
        final Cookie[] array2 = new Cookie[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final String[] resizeArray(final String[] array, final int n) {
        final String[] array2 = new String[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final boolean[] resizeArray(final boolean[] array, final int n) {
        final boolean[] array2 = new boolean[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final byte[] resizeArray(final byte[] array, final int n) {
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final char[] resizeArray(final char[] array, final int n) {
        final char[] array2 = new char[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static final int[] resizeArray(final int[] array, final int n) {
        final int[] array2 = new int[n];
        System.arraycopy(array, 0, array2, 0, (array.length < n) ? array.length : n);
        return array2;
    }
    
    static String[] splitProperty(final String str) {
        if (str == null) {
            return new String[0];
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
        final String[] array = new String[stringTokenizer.countTokens()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = stringTokenizer.nextToken().trim();
        }
        return array;
    }
    
    static final Hashtable getList(final Hashtable hashtable, final Object o) {
        synchronized (hashtable) {
            Hashtable value = hashtable.get(o);
            if (value == null) {
                value = new Hashtable();
                hashtable.put(o, value);
            }
            return value;
        }
    }
    
    static final int[] compile_search(final byte[] array) {
        final int[] array2 = { 0, 1, 0, 1, 0, 1 };
        for (int i = 0; i < array.length; ++i) {
            int n;
            for (n = i + 1; n < array.length && array[i] != array[n]; ++n) {}
            if (n < array.length) {
                if (n - i > array2[1]) {
                    array2[4] = array2[2];
                    array2[5] = array2[3];
                    array2[2] = array2[0];
                    array2[3] = array2[1];
                    array2[1] = n - (array2[0] = i);
                }
                else if (n - i > array2[3]) {
                    array2[4] = array2[2];
                    array2[5] = array2[3];
                    array2[3] = n - (array2[2] = i);
                }
                else if (n - i > array2[3]) {
                    array2[5] = n - (array2[4] = i);
                }
            }
        }
        final int[] array3 = array2;
        final int n2 = 1;
        array3[n2] += array2[0];
        final int[] array4 = array2;
        final int n3 = 3;
        array4[n3] += array2[2];
        final int[] array5 = array2;
        final int n4 = 5;
        array5[n4] += array2[4];
        return array2;
    }
    
    static final int findStr(final byte[] array, final int[] array2, final byte[] array3, int n, final int n2) {
        final int n3 = array2[0];
        final int n4 = array2[1];
        final int n5 = n4 - n3;
        final int n6 = array2[2];
        final int n7 = array2[3];
        final int n8 = n7 - n6;
        final int n9 = array2[4];
        final int n10 = array2[5];
        final int n11 = n10 - n9;
        while (n + array.length <= n2) {
            if (array[n4] == array3[n + n4]) {
                if (array[n3] == array3[n + n3]) {
                    boolean b = true;
                    for (int i = 0; i < array.length; ++i) {
                        if (array[i] != array3[n + i]) {
                            b = false;
                            break;
                        }
                    }
                    if (b) {
                        break;
                    }
                }
                n += n5;
            }
            else if (array[n7] == array3[n + n7]) {
                n += n8;
            }
            else if (array[n10] == array3[n + n10]) {
                n += n11;
            }
            else {
                ++n;
            }
        }
        if (n + array.length > n2) {
            return -1;
        }
        return n;
    }
    
    public static final String dequoteString(final String s) {
        if (s.indexOf(92) == -1) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        int i = 0;
        int n = 0;
        while (i < charArray.length) {
            if (charArray[i] == '\\' && i + 1 < charArray.length) {
                System.arraycopy(charArray, i + 1, charArray, i, charArray.length - i - 1);
                ++n;
            }
            ++i;
        }
        return new String(charArray, 0, charArray.length - n);
    }
    
    public static final String quoteString(final String s, final String s2) {
        char[] charArray;
        int n;
        for (charArray = s2.toCharArray(), n = 0; n < charArray.length && s.indexOf(charArray[n]) == -1; ++n) {}
        if (n == charArray.length) {
            return s;
        }
        int length = s.length();
        char[] resizeArray = new char[length * 2];
        s.getChars(0, length, resizeArray, 0);
        for (int i = 0; i < length; ++i) {
            if (s2.indexOf(resizeArray[i], 0) != -1) {
                if (length == resizeArray.length) {
                    resizeArray = resizeArray(resizeArray, length + s.length());
                }
                System.arraycopy(resizeArray, i, resizeArray, i + 1, length - i);
                ++length;
                resizeArray[i++] = '\\';
            }
        }
        return new String(resizeArray, 0, length);
    }
    
    public static final Vector parseHeader(final String s) throws ParseException {
        return parseHeader(s, true);
    }
    
    public static final Vector parseHeader(final String str, final boolean b) throws ParseException {
        if (str == null) {
            return null;
        }
        final char[] charArray = str.toCharArray();
        final Vector<HttpHeaderElement> vector = new Vector<HttpHeaderElement>();
        int n = 1;
        int offset = -1;
        int n2 = 0;
        final int length = charArray.length;
        final int[] array = { 0 };
        while (true) {
            if (n == 0) {
                offset = skipSpace(charArray, n2);
                if (offset == length) {
                    break;
                }
                if (charArray[offset] != ',') {
                    throw new ParseException("Bad header format: '" + str + "'\nExpected \",\" at position " + offset);
                }
            }
            n = 0;
            offset = skipSpace(charArray, offset + 1);
            if (offset == length) {
                break;
            }
            if (charArray[offset] == ',') {
                n2 = offset;
            }
            else {
                if (charArray[offset] == '=' || charArray[offset] == ';' || charArray[offset] == '\"') {
                    throw new ParseException("Bad header format: '" + str + "'\nEmpty element name at position " + offset);
                }
                int n3;
                for (n3 = offset + 1; n3 < length && !Character.isWhitespace(charArray[n3]) && charArray[n3] != '=' && charArray[n3] != ',' && charArray[n3] != ';'; ++n3) {}
                final String s = new String(charArray, offset, n3 - offset);
                final int skipSpace = skipSpace(charArray, n3);
                String value;
                if (skipSpace < length && charArray[skipSpace] == '=') {
                    array[0] = skipSpace + 1;
                    value = parseValue(charArray, array, str, b);
                    n2 = array[0];
                }
                else {
                    value = null;
                    n2 = skipSpace;
                }
                NVPair[] resizeArray = new NVPair[0];
                while (true) {
                    offset = skipSpace(charArray, n2);
                    if (offset == length) {
                        break;
                    }
                    if (charArray[offset] != ';') {
                        break;
                    }
                    offset = skipSpace(charArray, offset + 1);
                    if (offset == length || charArray[offset] == ',') {
                        n2 = offset;
                        break;
                    }
                    if (charArray[offset] == ';') {
                        n2 = offset;
                    }
                    else {
                        if (charArray[offset] == '=' || charArray[offset] == '\"') {
                            throw new ParseException("Bad header format: '" + str + "'\nEmpty parameter name at position " + offset);
                        }
                        int n4;
                        for (n4 = offset + 1; n4 < length && !Character.isWhitespace(charArray[n4]) && charArray[n4] != '=' && charArray[n4] != ',' && charArray[n4] != ';'; ++n4) {}
                        final String s2 = new String(charArray, offset, n4 - offset);
                        final int skipSpace2 = skipSpace(charArray, n4);
                        String value2;
                        if (skipSpace2 < length && charArray[skipSpace2] == '=') {
                            array[0] = skipSpace2 + 1;
                            value2 = parseValue(charArray, array, str, b);
                            n2 = array[0];
                        }
                        else {
                            value2 = null;
                            n2 = skipSpace2;
                        }
                        resizeArray = resizeArray(resizeArray, resizeArray.length + 1);
                        resizeArray[resizeArray.length - 1] = new NVPair(s2, value2);
                    }
                }
                vector.addElement(new HttpHeaderElement(s, value, resizeArray));
            }
        }
        return vector;
    }
    
    private static String parseValue(final char[] array, final int[] array2, final String str, final boolean b) throws ParseException {
        final int n = array2[0];
        final int length = array.length;
        int skipSpace = skipSpace(array, n);
        int n2;
        String s;
        if (skipSpace < length && array[skipSpace] == '\"') {
            n2 = ++skipSpace;
            char[] value = null;
            int n3 = 0;
            int n4 = skipSpace;
            while (n2 < length && array[n2] != '\"') {
                if (array[n2] == '\\') {
                    if (b) {
                        if (value == null) {
                            value = new char[array.length];
                        }
                        System.arraycopy(array, n4, value, n3, n2 - n4);
                        n3 += n2 - n4;
                        n4 = ++n2;
                    }
                    else {
                        ++n2;
                    }
                }
                ++n2;
            }
            if (n2 == length) {
                throw new ParseException("Bad header format: '" + str + "'\nClosing <\"> for quoted-string" + " starting at position " + (skipSpace - 1) + " not found");
            }
            if (value != null) {
                System.arraycopy(array, n4, value, n3, n2 - n4);
                s = new String(value, 0, n3 + (n2 - n4));
            }
            else {
                s = new String(array, skipSpace, n2 - skipSpace);
            }
            ++n2;
        }
        else {
            for (n2 = skipSpace; n2 < length && !Character.isWhitespace(array[n2]) && array[n2] != ',' && array[n2] != ';'; ++n2) {}
            s = new String(array, skipSpace, n2 - skipSpace);
        }
        array2[0] = n2;
        return s;
    }
    
    public static final boolean hasToken(final String s, final String s2) throws ParseException {
        return s != null && parseHeader(s).contains(new HttpHeaderElement(s2));
    }
    
    public static final HttpHeaderElement getElement(final Vector vector, final String s) {
        final int index = vector.indexOf(new HttpHeaderElement(s));
        if (index == -1) {
            return null;
        }
        return vector.elementAt(index);
    }
    
    public static final String getParameter(final String anotherString, final String s) throws ParseException {
        final NVPair[] params = parseHeader(s).firstElement().getParams();
        for (int i = 0; i < params.length; ++i) {
            if (params[i].getName().equalsIgnoreCase(anotherString)) {
                return params[i].getValue();
            }
        }
        return null;
    }
    
    public static final String assembleHeader(final Vector vector) {
        final StringBuffer sb = new StringBuffer(200);
        for (int size = vector.size(), i = 0; i < size; ++i) {
            vector.elementAt(i).appendTo(sb);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
    
    static final int skipSpace(final char[] array, int n) {
        while (n < array.length && Character.isWhitespace(array[n])) {
            ++n;
        }
        return n;
    }
    
    static final int findSpace(final char[] array, int n) {
        while (n < array.length && !Character.isWhitespace(array[n])) {
            ++n;
        }
        return n;
    }
    
    static final int skipToken(final char[] array, int n) {
        while (n < array.length && Util.TokenChar.get(array[n])) {
            ++n;
        }
        return n;
    }
    
    static final boolean needsQuoting(final String s) {
        int length;
        int index;
        for (length = s.length(), index = 0; index < length && Util.TokenChar.get(s.charAt(index)); ++index) {}
        return index < length;
    }
    
    public static final boolean sameHttpURL(final URL url, final URL url2) {
        if (!url.getProtocol().equalsIgnoreCase(url2.getProtocol())) {
            return false;
        }
        if (!url.getHost().equalsIgnoreCase(url2.getHost())) {
            return false;
        }
        int n = url.getPort();
        int n2 = url2.getPort();
        if (n == -1) {
            n = URI.defaultPort(url.getProtocol());
        }
        if (n2 == -1) {
            n2 = URI.defaultPort(url.getProtocol());
        }
        if (n != n2) {
            return false;
        }
        try {
            return URI.unescape(url.getFile(), null).equals(URI.unescape(url2.getFile(), null));
        }
        catch (ParseException ex) {
            return url.getFile().equals(url2.getFile());
        }
    }
    
    public static final int defaultPort(final String s) {
        return URI.defaultPort(s);
    }
    
    static final Date parseHttpDate(final String source) {
        synchronized (Util.http_parse_lock) {
            if (Util.parse_1123 == null) {
                setupParsers();
            }
        }
        try {
            return Util.parse_1123.parse(source);
        }
        catch (java.text.ParseException ex2) {
            try {
                return Util.parse_850.parse(source);
            }
            catch (java.text.ParseException ex3) {
                try {
                    return Util.parse_asctime.parse(source);
                }
                catch (java.text.ParseException ex) {
                    throw new IllegalArgumentException(ex.toString());
                }
            }
        }
    }
    
    private static final void setupParsers() {
        Util.parse_1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Util.parse_850 = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss 'GMT'", Locale.US);
        Util.parse_asctime = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
        Util.parse_1123.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Util.parse_850.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Util.parse_asctime.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Util.parse_1123.setLenient(true);
        Util.parse_850.setLenient(true);
        Util.parse_asctime.setLenient(true);
    }
    
    public static final String httpDate(final Date date) {
        synchronized (Util.http_format_lock) {
            if (Util.http_format == null) {
                setupFormatter();
            }
        }
        return Util.http_format.format(date);
    }
    
    private static final void setupFormatter() {
        (Util.http_format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)).setTimeZone(new SimpleTimeZone(0, "GMT"));
    }
    
    static final String escapeUnsafeChars(final String s) {
        final int length = s.length();
        final char[] value = new char[3 * length];
        int count = 0;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '\u0080' || Util.UnsafeChar.get(char1)) {
                value[count++] = '%';
                value[count++] = Util.hex_map[(char1 & '\u00f0') >>> 4];
                value[count++] = Util.hex_map[char1 & '\u000f'];
            }
            else {
                value[count++] = char1;
            }
        }
        if (count > length) {
            return new String(value, 0, count);
        }
        return s;
    }
    
    public static final String getPath(final String s) {
        int length = s.length();
        final int index;
        if ((index = s.indexOf(35)) != -1) {
            length = index;
        }
        final int index2;
        if ((index2 = s.indexOf(63)) != -1 && index2 < length) {
            length = index2;
        }
        final int index3;
        if ((index3 = s.indexOf(59)) != -1 && index3 < length) {
            length = index3;
        }
        return s.substring(0, length);
    }
    
    public static final String getParams(final String s) {
        final int index;
        if ((index = s.indexOf(59)) == -1) {
            return null;
        }
        final int index2;
        if ((index2 = s.indexOf(35)) != -1 && index2 < index) {
            return null;
        }
        final int index3;
        if ((index3 = s.indexOf(63)) != -1 && index3 < index) {
            return null;
        }
        if (index3 == -1 && index2 == -1) {
            return s.substring(index + 1);
        }
        if (index2 == -1 || (index3 != -1 && index3 < index2)) {
            return s.substring(index + 1, index3);
        }
        return s.substring(index + 1, index2);
    }
    
    public static final String getQuery(final String s) {
        final int index;
        if ((index = s.indexOf(63)) == -1) {
            return null;
        }
        final int index2;
        if ((index2 = s.indexOf(35)) != -1 && index2 < index) {
            return null;
        }
        if (index2 == -1) {
            return s.substring(index + 1);
        }
        return s.substring(index + 1, index2);
    }
    
    public static final String getFragment(final String s) {
        final int index;
        if ((index = s.indexOf(35)) == -1) {
            return null;
        }
        return s.substring(index + 1);
    }
    
    public static final boolean wildcardMatch(final String s, final String s2) {
        return wildcardMatch(s, s2, 0, 0, s.length(), s2.length());
    }
    
    private static final boolean wildcardMatch(final String s, final String s2, final int toffset, int n, final int n2, final int n3) {
        final int index = s.indexOf(42, toffset);
        if (index < 0) {
            return n2 - toffset == n3 - n && s.regionMatches(toffset, s2, n, n2 - toffset);
        }
        if (!s.regionMatches(toffset, s2, n, index - toffset)) {
            return false;
        }
        if (index == n2 - 1) {
            return true;
        }
        while (!wildcardMatch(s, s2, index + 1, n, n2, n3) && n < n3) {
            ++n;
        }
        return n < n3;
    }
    
    static {
        Separators = new BitSet(128);
        TokenChar = new BitSet(128);
        UnsafeChar = new BitSet(128);
        http_format_lock = new Object();
        http_parse_lock = new Object();
        Util.Separators.set(40);
        Util.Separators.set(41);
        Util.Separators.set(60);
        Util.Separators.set(62);
        Util.Separators.set(64);
        Util.Separators.set(44);
        Util.Separators.set(59);
        Util.Separators.set(58);
        Util.Separators.set(92);
        Util.Separators.set(34);
        Util.Separators.set(47);
        Util.Separators.set(91);
        Util.Separators.set(93);
        Util.Separators.set(63);
        Util.Separators.set(61);
        Util.Separators.set(123);
        Util.Separators.set(125);
        Util.Separators.set(32);
        Util.Separators.set(9);
        for (int i = 32; i < 127; ++i) {
            Util.TokenChar.set(i);
        }
        Util.TokenChar.xor(Util.Separators);
        for (int j = 0; j < 32; ++j) {
            Util.UnsafeChar.set(j);
        }
        Util.UnsafeChar.set(32);
        Util.UnsafeChar.set(60);
        Util.UnsafeChar.set(62);
        Util.UnsafeChar.set(34);
        Util.UnsafeChar.set(123);
        Util.UnsafeChar.set(125);
        Util.UnsafeChar.set(124);
        Util.UnsafeChar.set(92);
        Util.UnsafeChar.set(94);
        Util.UnsafeChar.set(126);
        Util.UnsafeChar.set(91);
        Util.UnsafeChar.set(93);
        Util.UnsafeChar.set(96);
        Util.UnsafeChar.set(127);
        hex_map = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
}
