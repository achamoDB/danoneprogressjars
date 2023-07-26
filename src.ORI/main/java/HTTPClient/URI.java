// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.Hashtable;

public class URI
{
    public static final boolean ENABLE_BACKWARDS_COMPATIBILITY = true;
    protected static final Hashtable defaultPorts;
    protected static final Hashtable usesGenericSyntax;
    protected static final Hashtable usesSemiGenericSyntax;
    protected static final BitSet alphanumChar;
    protected static final BitSet markChar;
    protected static final BitSet reservedChar;
    protected static final BitSet unreservedChar;
    protected static final BitSet uricChar;
    protected static final BitSet pcharChar;
    protected static final BitSet userinfoChar;
    protected static final BitSet schemeChar;
    protected static final BitSet hostChar;
    protected static final BitSet opaqueChar;
    protected static final BitSet reg_nameChar;
    public static final BitSet resvdSchemeChar;
    public static final BitSet resvdUIChar;
    public static final BitSet resvdHostChar;
    public static final BitSet resvdPathChar;
    public static final BitSet resvdQueryChar;
    public static final BitSet escpdPathChar;
    public static final BitSet escpdQueryChar;
    public static final BitSet escpdFragChar;
    protected static final int OPAQUE = 0;
    protected static final int SEMI_GENERIC = 1;
    protected static final int GENERIC = 2;
    protected int type;
    protected String scheme;
    protected String opaque;
    protected String userinfo;
    protected String host;
    protected int port;
    protected String path;
    protected String query;
    protected String fragment;
    protected URL url;
    private int hashCode;
    private static final char[] hex;
    private static final String nl;
    
    public URI(final String s) throws ParseException {
        this((URI)null, s);
    }
    
    public URI(final URI uri, final String s) throws ParseException {
        this.port = -1;
        this.url = null;
        this.hashCode = -1;
        char[] charArray;
        int beginIndex;
        int length;
        for (charArray = s.toCharArray(), beginIndex = 0, length = charArray.length; beginIndex < length && Character.isWhitespace(charArray[beginIndex]); ++beginIndex) {}
        while (length > 0 && Character.isWhitespace(charArray[length - 1])) {
            --length;
        }
        if (beginIndex < length - 3 && charArray[beginIndex + 3] == ':' && (charArray[beginIndex + 0] == 'u' || charArray[beginIndex + 0] == 'U') && (charArray[beginIndex + 1] == 'r' || charArray[beginIndex + 1] == 'R') && (charArray[beginIndex + 2] == 'i' || charArray[beginIndex + 2] == 'I' || charArray[beginIndex + 2] == 'l' || charArray[beginIndex + 2] == 'L')) {
            beginIndex += 4;
        }
        int endIndex;
        for (endIndex = beginIndex; endIndex < length && charArray[endIndex] != ':' && charArray[endIndex] != '/' && charArray[endIndex] != '?' && charArray[endIndex] != '#'; ++endIndex) {}
        if (endIndex < length && charArray[endIndex] == ':') {
            this.scheme = s.substring(beginIndex, endIndex).trim().toLowerCase();
            beginIndex = endIndex + 1;
        }
        String str = this.scheme;
        if (this.scheme == null) {
            if (uri == null) {
                throw new ParseException("No scheme found");
            }
            str = uri.scheme;
        }
        this.type = (usesGenericSyntax(str) ? 2 : usesSemiGenericSyntax(str));
        if (this.type != 0) {
            if (beginIndex + 1 < length && charArray[beginIndex] == '/' && charArray[beginIndex + 1] == '/') {
                beginIndex += 2;
                int endIndex2;
                for (endIndex2 = beginIndex; endIndex2 < length && charArray[endIndex2] != '/' && charArray[endIndex2] != '?' && charArray[endIndex2] != '#'; ++endIndex2) {}
                this.parse_authority(s.substring(beginIndex, endIndex2), str);
                beginIndex = endIndex2;
            }
            if (this.type == 1) {
                this.path = escape(s.substring(beginIndex), URI.uricChar, true);
                if (this.path.length() > 0 && this.path.charAt(0) != '/') {
                    this.path = '/' + this.path;
                }
            }
            else {
                int endIndex3;
                for (endIndex3 = beginIndex; endIndex3 < length && charArray[endIndex3] != '?' && charArray[endIndex3] != '#'; ++endIndex3) {}
                this.path = escape(s.substring(beginIndex, endIndex3), URI.escpdPathChar, true);
                int beginIndex2 = endIndex3;
                if (beginIndex2 < length && charArray[beginIndex2] == '?') {
                    int endIndex4;
                    for (endIndex4 = ++beginIndex2; endIndex4 < length && charArray[endIndex4] != '#'; ++endIndex4) {}
                    this.query = escape(s.substring(beginIndex2, endIndex4), URI.escpdQueryChar, true);
                    beginIndex2 = endIndex4;
                }
                if (beginIndex2 < length && charArray[beginIndex2] == '#') {
                    this.fragment = escape(s.substring(beginIndex2 + 1, length), URI.escpdFragChar, true);
                }
            }
            if (uri != null) {
                if (this.scheme != null && !this.scheme.equals(uri.scheme)) {
                    return;
                }
                this.scheme = uri.scheme;
                if (this.host != null) {
                    return;
                }
                this.userinfo = uri.userinfo;
                this.host = uri.host;
                this.port = uri.port;
                if (this.type == 1) {
                    return;
                }
                if (this.path.length() == 0 && this.query == null) {
                    this.path = uri.path;
                    this.query = uri.query;
                    return;
                }
                if (this.path.length() == 0 || this.path.charAt(0) != '/') {
                    final int n = (uri.path != null) ? uri.path.lastIndexOf(47) : -1;
                    if (n < 0) {
                        this.path = '/' + this.path;
                    }
                    else {
                        this.path = uri.path.substring(0, n + 1) + this.path;
                    }
                    this.path = canonicalizePath(this.path);
                }
            }
            return;
        }
        if (uri != null && this.scheme == null) {
            throw new ParseException("Can't resolve relative URI for scheme " + str);
        }
        this.opaque = escape(s.substring(beginIndex), URI.opaqueChar, true);
        if (this.opaque.length() > 0 && this.opaque.charAt(0) == '/') {
            this.opaque = "%2F" + this.opaque.substring(1);
        }
    }
    
    public static String canonicalizePath(final String s) {
        int length = s.length();
        final int index;
        if ((index = s.indexOf("/.")) == -1 || (index != length - 2 && s.charAt(index + 2) != '/' && (s.charAt(index + 2) != '.' || (index != length - 3 && s.charAt(index + 3) != '/')))) {
            return s;
        }
        final char[] array = new char[s.length()];
        s.getChars(0, array.length, array, 0);
        int n = 0;
        for (int i = 1; i < length; ++i) {
            if (array[i] == '.' && array[i - 1] == '/') {
                int n2;
                if (i == length - 1) {
                    n2 = i;
                    ++i;
                }
                else if (array[i + 1] == '/') {
                    n2 = i - 1;
                    ++i;
                }
                else {
                    if (array[i + 1] != '.' || (i != length - 2 && array[i + 2] != '/')) {
                        continue;
                    }
                    if (i < n + 2) {
                        n = i + 2;
                        continue;
                    }
                    for (n2 = i - 2; n2 > n && array[n2] != '/'; --n2) {}
                    if (array[n2] != '/') {
                        continue;
                    }
                    if (i == length - 2) {
                        ++n2;
                    }
                    i += 2;
                }
                System.arraycopy(array, i, array, n2, length - i);
                length -= i - n2;
                i = n2;
            }
        }
        return new String(array, 0, length);
    }
    
    private void parse_authority(final String str, final String s) throws ParseException {
        final char[] charArray = str.toCharArray();
        int beginIndex = 0;
        int length;
        int endIndex;
        for (length = charArray.length, endIndex = beginIndex; endIndex < length && charArray[endIndex] != '@'; ++endIndex) {}
        if (endIndex < length && charArray[endIndex] == '@') {
            this.userinfo = escape(str.substring(beginIndex, endIndex), URI.userinfoChar, true);
            beginIndex = endIndex + 1;
        }
        int n = beginIndex;
        if (n < length && charArray[n] == '[') {
            while (n < length && charArray[n] != ']') {
                ++n;
            }
            if (n == length) {
                throw new ParseException("No closing ']' found for opening '[' at position " + beginIndex + " in authority `" + str + "'");
            }
            this.host = str.substring(beginIndex + 1, n);
            ++n;
        }
        else {
            while (n < length && charArray[n] != ':') {
                ++n;
            }
            this.host = escape(str.substring(beginIndex, n), URI.uricChar, true);
        }
        final int n2 = n;
        if (n2 < length - 1 && charArray[n2] == ':') {
            int int1;
            try {
                int1 = Integer.parseInt(unescape(str.substring(n2 + 1, length), null));
                if (int1 < 0) {
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException ex) {
                throw new ParseException(str.substring(n2 + 1, length) + " is an invalid port number");
            }
            if (int1 == defaultPort(s)) {
                this.port = -1;
            }
            else {
                this.port = int1;
            }
        }
    }
    
    public URI(final URL url) throws ParseException {
        this((URI)null, url.toExternalForm());
    }
    
    public URI(final String s, final String s2, final String s3) throws ParseException {
        this(s, null, s2, -1, s3, null, null);
    }
    
    public URI(final String s, final String s2, final int n, final String s3) throws ParseException {
        this(s, null, s2, n, s3, null, null);
    }
    
    public URI(final String s, final String s2, String trim, final int port, final String s3, final String s4, final String s5) throws ParseException {
        this.port = -1;
        this.url = null;
        this.hashCode = -1;
        if (s == null) {
            throw new ParseException("missing scheme");
        }
        this.scheme = escape(s.trim().toLowerCase(), URI.schemeChar, true);
        if (s2 != null) {
            this.userinfo = escape(s2.trim(), URI.userinfoChar, true);
        }
        if (trim != null) {
            trim = trim.trim();
            this.host = (isIPV6Addr(trim) ? trim : escape(trim, URI.hostChar, true));
        }
        if (port != defaultPort(s)) {
            this.port = port;
        }
        if (s3 != null) {
            this.path = escape(s3.trim(), URI.escpdPathChar, true);
        }
        if (s4 != null) {
            this.query = escape(s4.trim(), URI.escpdQueryChar, true);
        }
        if (s5 != null) {
            this.fragment = escape(s5.trim(), URI.escpdFragChar, true);
        }
        this.type = (usesGenericSyntax(s) ? 2 : 1);
    }
    
    private static final boolean isIPV6Addr(final String s) {
        if (s.indexOf(58) < 0) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ((char1 < '0' || char1 > '9') && char1 != ':') {
                return false;
            }
        }
        return true;
    }
    
    public URI(final String s, final String s2) throws ParseException {
        this.port = -1;
        this.url = null;
        this.hashCode = -1;
        if (s == null) {
            throw new ParseException("missing scheme");
        }
        this.scheme = escape(s.trim().toLowerCase(), URI.schemeChar, true);
        this.opaque = escape(s2, URI.opaqueChar, true);
        this.type = 0;
    }
    
    public static boolean usesGenericSyntax(final String s) {
        return URI.usesGenericSyntax.containsKey(s.trim().toLowerCase());
    }
    
    public static boolean usesSemiGenericSyntax(final String s) {
        return URI.usesSemiGenericSyntax.containsKey(s.trim().toLowerCase());
    }
    
    public static final int defaultPort(final String s) {
        final Integer n = URI.defaultPorts.get(s.trim().toLowerCase());
        return (n != null) ? n : 0;
    }
    
    public String getScheme() {
        return this.scheme;
    }
    
    public String getOpaque() {
        return this.opaque;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getUserinfo() {
        return this.userinfo;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getQueryString() {
        return this.query;
    }
    
    public String getPathAndQuery() {
        if (this.query == null) {
            return this.path;
        }
        if (this.path == null) {
            return "?" + this.query;
        }
        return this.path + "?" + this.query;
    }
    
    public String getFragment() {
        return this.fragment;
    }
    
    public boolean isGenericURI() {
        return this.type == 2;
    }
    
    public boolean isSemiGenericURI() {
        return this.type == 1;
    }
    
    public URL toURL() throws MalformedURLException {
        if (this.url != null) {
            return this.url;
        }
        if (this.opaque != null) {
            return this.url = new URL(this.scheme + ":" + this.opaque);
        }
        String host;
        if (this.userinfo != null && this.host != null) {
            host = this.userinfo + "@" + this.host;
        }
        else if (this.userinfo != null) {
            host = this.userinfo + "@";
        }
        else {
            host = this.host;
        }
        final StringBuffer sb = new StringBuffer(100);
        this.assemblePath(sb, true, true, false);
        return this.url = new URL(this.scheme, host, this.port, sb.toString());
    }
    
    private final void assemblePath(final StringBuffer sb, final boolean b, final boolean b2, final boolean b3) {
        if ((this.path == null || this.path.length() == 0) && b) {
            sb.append('/');
        }
        if (this.path != null) {
            sb.append(b3 ? unescapeNoPE(this.path, URI.resvdPathChar) : this.path);
        }
        if (this.query != null) {
            sb.append('?');
            sb.append(b3 ? unescapeNoPE(this.query, URI.resvdQueryChar) : this.query);
        }
        if (this.fragment != null && b2) {
            sb.append('#');
            sb.append(b3 ? unescapeNoPE(this.fragment, null) : this.fragment);
        }
    }
    
    private final String stringify(final boolean b) {
        final StringBuffer sb = new StringBuffer(100);
        if (this.scheme != null) {
            sb.append(b ? unescapeNoPE(this.scheme, URI.resvdSchemeChar) : this.scheme);
            sb.append(':');
        }
        if (this.opaque != null) {
            sb.append(b ? unescapeNoPE(this.opaque, null) : this.opaque);
            return sb.toString();
        }
        if (this.userinfo != null || this.host != null || this.port != -1) {
            sb.append("//");
        }
        if (this.userinfo != null) {
            sb.append(b ? unescapeNoPE(this.userinfo, URI.resvdUIChar) : this.userinfo);
            sb.append('@');
        }
        if (this.host != null) {
            if (this.host.indexOf(58) < 0) {
                sb.append(b ? unescapeNoPE(this.host, URI.resvdHostChar) : this.host);
            }
            else {
                sb.append('[').append(this.host).append(']');
            }
        }
        if (this.port != -1) {
            sb.append(':');
            sb.append(this.port);
        }
        this.assemblePath(sb, false, true, b);
        return sb.toString();
    }
    
    public String toExternalForm() {
        return this.stringify(false);
    }
    
    public String toString() {
        return this.stringify(true);
    }
    
    public boolean equals(final Object o) {
        if (o instanceof URI) {
            final URI uri = (URI)o;
            return this.scheme.equals(uri.scheme) && ((this.type == 0 && areEqual(this.opaque, uri.opaque)) || (this.type == 1 && areEqual(this.userinfo, uri.userinfo) && areEqualIC(this.host, uri.host) && this.port == uri.port && areEqual(this.path, uri.path)) || (this.type == 2 && areEqual(this.userinfo, uri.userinfo) && areEqualIC(this.host, uri.host) && this.port == uri.port && pathsEqual(this.path, uri.path) && areEqual(this.query, uri.query) && areEqual(this.fragment, uri.fragment)));
        }
        if (o instanceof URL) {
            final URL url = (URL)o;
            String s;
            if (this.userinfo != null) {
                s = this.userinfo + "@" + this.host;
            }
            else {
                s = this.host;
            }
            final String pathAndQuery = this.getPathAndQuery();
            return this.scheme.equalsIgnoreCase(url.getProtocol()) && ((this.type == 0 && this.opaque.equals(url.getFile())) || (this.type == 1 && areEqualIC(s, url.getHost()) && (this.port == url.getPort() || url.getPort() == defaultPort(this.scheme)) && areEqual(pathAndQuery, url.getFile())) || (this.type == 2 && areEqualIC(s, url.getHost()) && (this.port == url.getPort() || url.getPort() == defaultPort(this.scheme)) && pathsEqual(pathAndQuery, url.getFile()) && areEqual(this.fragment, url.getRef())));
        }
        return false;
    }
    
    private static final boolean areEqual(final String s, final String anObject) {
        return (s == null && anObject == null) || (s != null && anObject != null && (s.equals(anObject) || unescapeNoPE(s, null).equals(unescapeNoPE(anObject, null))));
    }
    
    private static final boolean areEqualIC(final String s, final String anotherString) {
        return (s == null && anotherString == null) || (s != null && anotherString != null && (s.equalsIgnoreCase(anotherString) || unescapeNoPE(s, null).equalsIgnoreCase(unescapeNoPE(anotherString, null))));
    }
    
    private static final boolean pathsEqual(final String s, final String s2) {
        if (s == null && s2 == null) {
            return true;
        }
        if (s == null || s2 == null) {
            return false;
        }
        if (s.equals(s2)) {
            return true;
        }
        int endIndex;
        int length;
        int endIndex2;
        int length2;
        for (endIndex = 0, length = s.length(), endIndex2 = 0, length2 = s2.length(); endIndex < length && endIndex2 < length2; ++endIndex, ++endIndex2) {
            final int n = endIndex;
            final int n2 = endIndex2;
            char char1;
            while (endIndex < length && (char1 = s.charAt(endIndex)) != '/' && char1 != ';') {
                ++endIndex;
            }
            char char2;
            while (endIndex2 < length2 && (char2 = s2.charAt(endIndex2)) != '/' && char2 != ';') {
                ++endIndex2;
            }
            if ((endIndex == length && endIndex2 < length2) || (endIndex2 == length2 && endIndex < length) || (endIndex < length && endIndex2 < length2 && s.charAt(endIndex) != s2.charAt(endIndex2))) {
                return false;
            }
            if ((!s.regionMatches(n, s2, n2, endIndex - n) || endIndex - n != endIndex2 - n2) && !unescapeNoPE(s.substring(n, endIndex), null).equals(unescapeNoPE(s2.substring(n2, endIndex2), null))) {
                return false;
            }
        }
        return endIndex == length && endIndex2 == length2;
    }
    
    public int hashCode() {
        if (this.hashCode == -1) {
            this.hashCode = ((this.scheme != null) ? unescapeNoPE(this.scheme, null).hashCode() : 0) + ((this.type == 0) ? (((this.opaque != null) ? unescapeNoPE(this.opaque, null).hashCode() : 0) * 7) : (((this.host != null) ? unescapeNoPE(this.host, null).toLowerCase().hashCode() : 0) * 7 + ((this.path != null) ? unescapeNoPE(this.path, null).hashCode() : 0) * 13 + ((this.query != null) ? unescapeNoPE(this.query, null).hashCode() : 0) * 17));
        }
        return this.hashCode;
    }
    
    public static String escape(final String s, final BitSet set, final boolean b) {
        return new String(escape(s.toCharArray(), set, b));
    }
    
    public static char[] escape(final char[] array, final BitSet set, final boolean b) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!set.get(array[i])) {
                n += 2;
                if (b) {
                    if (array[i] >= '\u0080') {
                        n += 3;
                    }
                    if (array[i] >= '\u0800') {
                        n += 3;
                    }
                    if ((array[i] & '\ufc00') == 0xD800 && i + 1 < array.length && (array[i + 1] & '\ufc00') == 0xDC00) {
                        n -= 6;
                    }
                }
            }
        }
        if (n == 0) {
            return array;
        }
        final char[] array2 = new char[array.length + n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            final char bitIndex = array[j];
            if (set.get(bitIndex)) {
                array2[n2++] = bitIndex;
            }
            else if (b) {
                if (bitIndex <= '\u007f') {
                    n2 = enc(array2, n2, bitIndex);
                }
                else if (bitIndex <= '\u07ff') {
                    n2 = enc(array2, enc(array2, n2, 0xC0 | (bitIndex >> 6 & 0x1F)), 0x80 | (bitIndex >> 0 & 0x3F));
                }
                else if ((bitIndex & '\ufc00') != 0xD800 || j + 1 >= array.length || (array[j + 1] & '\ufc00') != 0xDC00) {
                    n2 = enc(array2, enc(array2, enc(array2, n2, 0xE0 | (bitIndex >> 12 & 0xF)), 0x80 | (bitIndex >> 6 & 0x3F)), 0x80 | (bitIndex >> 0 & 0x3F));
                }
                else {
                    final int n3 = ((bitIndex & '\u03ff') << 10 | (array[++j] & '\u03ff')) + 65536;
                    n2 = enc(array2, enc(array2, enc(array2, enc(array2, n2, 0xF0 | (n3 >> 18 & 0x7)), 0x80 | (n3 >> 12 & 0x3F)), 0x80 | (n3 >> 6 & 0x3F)), 0x80 | (n3 >> 0 & 0x3F));
                }
            }
            else {
                n2 = enc(array2, n2, bitIndex);
            }
            ++j;
        }
        return array2;
    }
    
    private static final int enc(final char[] array, int n, final int n2) {
        array[n++] = '%';
        array[n++] = URI.hex[n2 >> 4 & 0xF];
        array[n++] = URI.hex[n2 & 0xF];
        return n;
    }
    
    public static final String unescape(final String s, final BitSet set) throws ParseException {
        if (s == null || s.indexOf(37) == -1) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        final char[] value = new char[charArray.length];
        final char[] array = new char[4];
        int n = 0;
        int n2 = -1;
        int count = 0;
        for (int i = 0; i < charArray.length; ++i) {
            if (charArray[i] == '%') {
                int int1;
                try {
                    if (i + 3 > charArray.length) {
                        throw new NumberFormatException();
                    }
                    int1 = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                    if (int1 < 0) {
                        throw new NumberFormatException();
                    }
                    i += 2;
                }
                catch (NumberFormatException ex) {
                    int1 = charArray[i];
                }
                if (n2 > 0) {
                    if ((int1 & 0xC0) != 0x80) {
                        count = copyBuf(array, n, int1, value, count, set, false);
                        n2 = -1;
                    }
                    else if (n == n2 - 1) {
                        int bitIndex;
                        if ((array[0] & '\u00e0') == 0xC0) {
                            bitIndex = ((array[0] & '\u001f') << 6 | (int1 & 0x3F));
                        }
                        else if ((array[0] & '\u00f0') == 0xE0) {
                            bitIndex = ((array[0] & '\u000f') << 12 | (array[1] & '?') << 6 | (int1 & 0x3F));
                        }
                        else {
                            bitIndex = ((array[0] & '\u0007') << 18 | (array[1] & '?') << 12 | (array[2] & '?') << 6 | (int1 & 0x3F));
                        }
                        if (set != null && set.get(bitIndex)) {
                            count = copyBuf(array, n, bitIndex, value, count, null, true);
                        }
                        else if (n2 < 4) {
                            value[count++] = (char)bitIndex;
                        }
                        else {
                            final int n3 = bitIndex - 65536;
                            value[count++] = (char)(n3 >> 10 | 0xD800);
                            value[count++] = (char)((n3 & 0x3FF) | 0xDC00);
                        }
                        n2 = -1;
                    }
                    else {
                        array[n++] = (char)int1;
                    }
                }
                else if ((int1 & 0xE0) == 0xC0 || (int1 & 0xF0) == 0xE0 || (int1 & 0xF8) == 0xF0) {
                    if ((int1 & 0xE0) == 0xC0) {
                        n2 = 2;
                    }
                    else if ((int1 & 0xF0) == 0xE0) {
                        n2 = 3;
                    }
                    else {
                        n2 = 4;
                    }
                    array[0] = (char)int1;
                    n = 1;
                }
                else if (set != null && set.get(int1)) {
                    value[count++] = charArray[i];
                    i -= 2;
                }
                else {
                    value[count++] = (char)int1;
                }
            }
            else if (n2 > 0) {
                count = copyBuf(array, n, charArray[i], value, count, set, false);
                n2 = -1;
            }
            else {
                value[count++] = charArray[i];
            }
        }
        if (n2 > 0) {
            count = copyBuf(array, n, -1, value, count, set, false);
        }
        return new String(value, 0, count);
    }
    
    private static final int copyBuf(final char[] array, int n, final int n2, final char[] array2, int enc, final BitSet set, final boolean b) {
        if (n2 >= 0) {
            array[n++] = (char)n2;
        }
        for (int i = 0; i < n; ++i) {
            if ((set != null && set.get(array[i])) || b) {
                enc = enc(array2, enc, array[i]);
            }
            else {
                array2[enc++] = array[i];
            }
        }
        return enc;
    }
    
    private static final String unescapeNoPE(final String s, final BitSet set) {
        try {
            return unescape(s, set);
        }
        catch (ParseException ex) {
            return s;
        }
    }
    
    public static void main(final String[] array) throws Exception {
        System.err.println();
        System.err.println("*** URI Tests ...");
        final URI uri = new URI("http://a/b/c/d;p?q");
        testParser(uri, "g:h", "g:h");
        testParser(uri, "g", "http://a/b/c/g");
        testParser(uri, "./g", "http://a/b/c/g");
        testParser(uri, "g/", "http://a/b/c/g/");
        testParser(uri, "/g", "http://a/g");
        testParser(uri, "//g", "http://g");
        testParser(uri, "//[23:54]", "http://[23:54]");
        testParser(uri, "?y", "http://a/b/c/?y");
        testParser(uri, "g?y", "http://a/b/c/g?y");
        testParser(uri, "#s", "http://a/b/c/d;p?q#s");
        testParser(uri, "g#s", "http://a/b/c/g#s");
        testParser(uri, "g?y#s", "http://a/b/c/g?y#s");
        testParser(uri, ";x", "http://a/b/c/;x");
        testParser(uri, "g;x", "http://a/b/c/g;x");
        testParser(uri, "g;x?y#s", "http://a/b/c/g;x?y#s");
        testParser(uri, ".", "http://a/b/c/");
        testParser(uri, "./", "http://a/b/c/");
        testParser(uri, "..", "http://a/b/");
        testParser(uri, "../", "http://a/b/");
        testParser(uri, "../g", "http://a/b/g");
        testParser(uri, "../..", "http://a/");
        testParser(uri, "../../", "http://a/");
        testParser(uri, "../../g", "http://a/g");
        testParser(uri, "", "http://a/b/c/d;p?q");
        testParser(uri, "/./g", "http://a/./g");
        testParser(uri, "/../g", "http://a/../g");
        testParser(uri, "../../../g", "http://a/../g");
        testParser(uri, "../../../../g", "http://a/../../g");
        testParser(uri, "g.", "http://a/b/c/g.");
        testParser(uri, ".g", "http://a/b/c/.g");
        testParser(uri, "g..", "http://a/b/c/g..");
        testParser(uri, "..g", "http://a/b/c/..g");
        testParser(uri, "./../g", "http://a/b/g");
        testParser(uri, "./g/.", "http://a/b/c/g/");
        testParser(uri, "g/./h", "http://a/b/c/g/h");
        testParser(uri, "g/../h", "http://a/b/c/h");
        testParser(uri, "g;x=1/./y", "http://a/b/c/g;x=1/y");
        testParser(uri, "g;x=1/../y", "http://a/b/c/y");
        testParser(uri, "g?y/./x", "http://a/b/c/g?y/./x");
        testParser(uri, "g?y/../x", "http://a/b/c/g?y/../x");
        testParser(uri, "g#s/./x", "http://a/b/c/g#s/./x");
        testParser(uri, "g#s/../x", "http://a/b/c/g#s/../x");
        testParser(uri, "http:g", "http://a/b/c/g");
        testParser(uri, "http:", "http://a/b/c/d;p?q");
        testParser(uri, "./g:h", "http://a/b/c/g:h");
        final URI uri2 = new URI("http://a/b/c/d;p?q=1/2");
        testParser(uri2, "g", "http://a/b/c/g");
        testParser(uri2, "./g", "http://a/b/c/g");
        testParser(uri2, "g/", "http://a/b/c/g/");
        testParser(uri2, "/g", "http://a/g");
        testParser(uri2, "//g", "http://g");
        testParser(uri2, "//[23:54]", "http://[23:54]");
        testParser(uri2, "?y", "http://a/b/c/?y");
        testParser(uri2, "g?y", "http://a/b/c/g?y");
        testParser(uri2, "g?y/./x", "http://a/b/c/g?y/./x");
        testParser(uri2, "g?y/../x", "http://a/b/c/g?y/../x");
        testParser(uri2, "g#s", "http://a/b/c/g#s");
        testParser(uri2, "g#s/./x", "http://a/b/c/g#s/./x");
        testParser(uri2, "g#s/../x", "http://a/b/c/g#s/../x");
        testParser(uri2, "./", "http://a/b/c/");
        testParser(uri2, "../", "http://a/b/");
        testParser(uri2, "../g", "http://a/b/g");
        testParser(uri2, "../../", "http://a/");
        testParser(uri2, "../../g", "http://a/g");
        final URI uri3 = new URI("http://a/b/c/d;p=1/2?q");
        testParser(uri3, "g", "http://a/b/c/d;p=1/g");
        testParser(uri3, "./g", "http://a/b/c/d;p=1/g");
        testParser(uri3, "g/", "http://a/b/c/d;p=1/g/");
        testParser(uri3, "g?y", "http://a/b/c/d;p=1/g?y");
        testParser(uri3, ";x", "http://a/b/c/d;p=1/;x");
        testParser(uri3, "g;x", "http://a/b/c/d;p=1/g;x");
        testParser(uri3, "g;x=1/./y", "http://a/b/c/d;p=1/g;x=1/y");
        testParser(uri3, "g;x=1/../y", "http://a/b/c/d;p=1/y");
        testParser(uri3, "./", "http://a/b/c/d;p=1/");
        testParser(uri3, "../", "http://a/b/c/");
        testParser(uri3, "../g", "http://a/b/c/g");
        testParser(uri3, "../../", "http://a/b/");
        testParser(uri3, "../../g", "http://a/b/g");
        final URI uri4 = new URI("fred:///s//a/b/c");
        testParser(uri4, "g:h", "g:h");
        testPE(uri4, "g");
        final URI uri5 = new URI("http:///s//a/b/c");
        testParser(uri5, "g:h", "g:h");
        testParser(uri5, "g", "http:///s//a/b/g");
        testParser(uri5, "./g", "http:///s//a/b/g");
        testParser(uri5, "g/", "http:///s//a/b/g/");
        testParser(uri5, "/g", "http:///g");
        testParser(uri5, "//g", "http://g");
        testParser(uri5, "//[23:54]", "http://[23:54]");
        testParser(uri5, "//g/x", "http://g/x");
        testParser(uri5, "///g", "http:///g");
        testParser(uri5, "./", "http:///s//a/b/");
        testParser(uri5, "../", "http:///s//a/");
        testParser(uri5, "../g", "http:///s//a/g");
        testParser(uri5, "../../", "http:///s//");
        testParser(uri5, "../../g", "http:///s//g");
        testParser(uri5, "../../../g", "http:///s/g");
        testParser(uri5, "../../../../g", "http:///g");
        final URI uri6 = new URI("http://s");
        testParser(uri6, "ftp:h", "ftp:h");
        testParser(uri6, "ftp://h", "ftp://h");
        testParser(uri6, "//g", "http://g");
        testParser(uri6, "//g?h", "http://g?h");
        testParser(uri6, "g", "http://s/g");
        testParser(uri6, "./g", "http://s/g");
        testParser(uri6, "?g", "http://s/?g");
        testParser(uri6, "#g", "http://s#g");
        final URI uri7 = new URI("http:");
        testParser(uri7, "ftp:h", "ftp:h");
        testParser(uri7, "ftp://h", "ftp://h");
        testParser(uri7, "//g", "http://g");
        testParser(uri7, "g", "http:/g");
        testParser(uri7, "?g", "http:/?g");
        testParser(uri7, "#g", "http:#g");
        final URI uri8 = new URI("http://s/t");
        testParser(uri8, "ftp:/h", "ftp:/h");
        testParser(uri8, "http:/h", "http://s/h");
        final URI uri9 = new URI("http://s/g?h/j");
        testParser(uri9, "k", "http://s/k");
        testParser(uri9, "k?l", "http://s/k?l");
        final URI uri10 = new URI("ldap:");
        testParser(uri10, "ldap:", "ldap:");
        testParser(uri10, "ldap://a", "ldap://a");
        testParser(uri10, "ldap://a/b", "ldap://a/b");
        testParser(uri10, "ldap:/b", "ldap:/b");
        testParser(uri10, "ftp:h", "ftp:h");
        testParser(uri10, "ftp://h", "ftp://h");
        testParser(uri10, "//g", "ldap://g");
        testParser(uri10, "//g?h", "ldap://g/?h");
        testParser(uri10, "g", "ldap:/g");
        testParser(uri10, "./g", "ldap:/./g");
        testParser(uri10, "?g", "ldap:/?g");
        testParser(uri10, "#g", "ldap:/%23g");
        final URI uri11 = new URI("ldap://s");
        testParser(uri11, "ldap:", "ldap://s");
        testParser(uri11, "ldap://a", "ldap://a");
        testParser(uri11, "ldap://a/b", "ldap://a/b");
        testParser(uri11, "ldap:/b", "ldap://s/b");
        testParser(uri11, "ftp:h", "ftp:h");
        testParser(uri11, "ftp://h", "ftp://h");
        testParser(uri11, "//g", "ldap://g");
        testParser(uri11, "//g?h", "ldap://g/?h");
        testParser(uri11, "g", "ldap://s/g");
        testParser(uri11, "./g", "ldap://s/./g");
        testParser(uri11, "?g", "ldap://s/?g");
        testParser(uri11, "#g", "ldap://s/%23g");
        final URI uri12 = new URI("ldap://s/t");
        testParser(uri12, "ftp:/h", "ftp:/h");
        testParser(uri12, "ldap:/h", "ldap://s/h");
        testParser(uri12, "ldap:", "ldap://s");
        testParser(uri12, "ldap://a", "ldap://a");
        testParser(uri12, "ldap://a/b", "ldap://a/b");
        testParser(uri12, "ftp:h", "ftp:h");
        testParser(uri12, "ftp://h", "ftp://h");
        testParser(uri12, "//g", "ldap://g");
        testParser(uri12, "//g?h", "ldap://g/?h");
        testParser(uri12, "g", "ldap://s/g");
        testParser(uri12, "./g", "ldap://s/./g");
        testParser(uri12, "?g", "ldap://s/?g");
        testParser(uri12, "#g", "ldap://s/%23g");
        testNotEqual("http://a/", "nntp://a/");
        testNotEqual("http://a/", "https://a/");
        testNotEqual("http://a/", "shttp://a/");
        testEqual("http://a/", "Http://a/");
        testEqual("http://a/", "hTTP://a/");
        testEqual("url:http://a/", "hTTP://a/");
        testEqual("urI:http://a/", "hTTP://a/");
        testEqual("http://a/", "Http://A/");
        testEqual("http://a.b.c/", "Http://A.b.C/");
        testEqual("http:///", "Http:///");
        testEqual("http://[]/", "Http:///");
        testNotEqual("http:///", "Http://a/");
        testNotEqual("http://[]/", "Http://a/");
        testPE(null, "ftp://[23::43:1/");
        testPE(null, "ftp://[/");
        testEqual("http://a.b.c/", "Http://A.b.C:80/");
        testEqual("http://a.b.c:/", "Http://A.b.C:80/");
        testEqual("http://[23::45:::5:]/", "Http://[23::45:::5:]:80/");
        testEqual("http://[23::45:::5:]:/", "Http://[23::45:::5:]:80/");
        testEqual("nntp://a", "nntp://a:119");
        testEqual("nntp://a:", "nntp://a:119");
        testEqual("nntp://a/", "nntp://a:119/");
        testNotEqual("nntp://a", "nntp://a:118");
        testNotEqual("nntp://a", "nntp://a:0");
        testNotEqual("nntp://a:", "nntp://a:0");
        testEqual("telnet://:23/", "telnet:///");
        testPE(null, "ftp://:a/");
        testPE(null, "ftp://:-1/");
        testPE(null, "ftp://::1/");
        testNotEqual("ftp://me@a", "ftp://a");
        testNotEqual("ftp://me@a", "ftp://Me@a");
        testEqual("ftp://Me@a", "ftp://Me@a");
        testEqual("ftp://Me:My@a:21", "ftp://Me:My@a");
        testEqual("ftp://Me:My@a:", "ftp://Me:My@a");
        testNotEqual("ftp://Me:My@a:21", "ftp://Me:my@a");
        testNotEqual("ftp://Me:My@a:", "ftp://Me:my@a");
        testEqual("ftp://a/b%2b/", "ftp://a/b+/");
        testEqual("ftp://a/b%2b/", "ftp://a/b+/");
        testEqual("ftp://a/b%5E/", "ftp://a/b^/");
        testEqual("ftp://a/b%4C/", "ftp://a/bL/");
        testNotEqual("ftp://a/b/", "ftp://a//b/");
        testNotEqual("ftp://a/b/", "ftp://a/b//");
        testNotEqual("ftp://a/b%4C/", "ftp://a/bl/");
        testNotEqual("ftp://a/b%3f/", "ftp://a/b?/");
        testNotEqual("ftp://a/b%2f/", "ftp://a/b//");
        testNotEqual("ftp://a/b%2fc/", "ftp://a/b/c/");
        testNotEqual("ftp://a/bc/", "ftp://a/b//");
        testNotEqual("ftp://a/bc/", "ftp://a/b/");
        testNotEqual("ftp://a/bc//", "ftp://a/b/");
        testNotEqual("ftp://a/b/", "ftp://a/bc//");
        testNotEqual("ftp://a/b/", "ftp://a/bc/");
        testNotEqual("ftp://a/b//", "ftp://a/bc/");
        testNotEqual("ftp://a/b;fc/", "ftp://a/bf;c/");
        testNotEqual("ftp://a/b%3bfc/", "ftp://a/b;fc/");
        testEqual("ftp://a/b;/;/", "ftp://a/b;/;/");
        testNotEqual("ftp://a/b;/", "ftp://a/b//");
        testNotEqual("ftp://a/b//", "ftp://a/b;/");
        testNotEqual("ftp://a/b/;", "ftp://a/b//");
        testNotEqual("ftp://a/b//", "ftp://a/b/;");
        testNotEqual("ftp://a/b;/", "ftp://a/b;//");
        testNotEqual("ftp://a/b;//", "ftp://a/b;/");
        testEscape("hello\u1212there", "hello%E1%88%92there");
        testEscape("hello\u0232there", "hello%C8%B2there");
        testEscape("hello\uda42\udd42there", "hello%F2%A0%A5%82there");
        testEscape("hello\uda42", "hello%ED%A9%82");
        testEscape("hello\uda42there", "hello%ED%A9%82there");
        testUnescape("hello%F2%A0%A5%82there", "hello\uda42\udd42there");
        testUnescape("hello%F2%A0%A5there", "hello\u00f2 ¥there");
        testUnescape("hello%F2%A0there", "hello\u00f2 there");
        testUnescape("hello%F2there", "hello\u00f2there");
        testUnescape("hello%F2%A0%A5%82", "hello\uda42\udd42");
        testUnescape("hello%F2%A0%A5", "hello\u00f2 ¥");
        testUnescape("hello%F2%A0", "hello\u00f2 ");
        testUnescape("hello%F2", "hello\u00f2");
        testUnescape("hello%E1%88%92there", "hello\u1212there");
        testUnescape("hello%E1%88there", "hello\u00e1\u0088there");
        testUnescape("hello%E1there", "hello\u00e1there");
        testUnescape("hello%E1%71there", "hello\u00e1qthere");
        testUnescape("hello%E1%88", "hello\u00e1\u0088");
        testUnescape("hello%E1%71", "hello\u00e1q");
        testUnescape("hello%E1", "hello\u00e1");
        testUnescape("hello%C8%B2there", "hello\u0232there");
        testUnescape("hello%C8there", "hello\u00c8there");
        testUnescape("hello%C8%71there", "hello\u00c8qthere");
        testUnescape("hello%C8%71", "hello\u00c8q");
        testUnescape("hello%C8", "hello\u00c8");
        testUnescape("%71there", "qthere");
        testUnescape("%B1there", "±there");
        System.err.println("*** Tests finished successfuly");
    }
    
    private static void testParser(final URI obj, final String str, final String s) throws Exception {
        if (!new URI(obj, str).toExternalForm().equals(s)) {
            throw new Exception("Test failed: " + URI.nl + "  base-URI = <" + obj + ">" + URI.nl + "  rel-URI  = <" + str + ">" + URI.nl + "  expected   <" + s + ">" + URI.nl + "  but got    <" + new URI(obj, str) + ">");
        }
    }
    
    private static void testEqual(final String s, final String s2) throws Exception {
        final URI uri = new URI(s);
        final URI uri2 = new URI(s2);
        if (!uri.equals(uri2)) {
            throw new Exception("Test failed: " + URI.nl + "  <" + s + "> != <" + s2 + ">");
        }
        if (uri.hashCode() != uri2.hashCode()) {
            throw new Exception("Test failed: " + URI.nl + "  hashCode <" + s + "> != hashCode <" + s2 + ">");
        }
    }
    
    private static void testNotEqual(final String str, final String str2) throws Exception {
        if (new URI(str).equals(new URI(str2))) {
            throw new Exception("Test failed: " + URI.nl + "  <" + str + "> == <" + str2 + ">");
        }
    }
    
    private static void testPE(final URI uri, final String str) throws Exception {
        boolean b = false;
        try {
            new URI(uri, str);
        }
        catch (ParseException ex) {
            b = true;
        }
        if (!b) {
            throw new Exception("Test failed: " + URI.nl + "  <" + str + "> should be invalid");
        }
    }
    
    private static void testEscape(final String str, final String s) throws Exception {
        final String str2 = new String(escape(str.toCharArray(), URI.uricChar, true));
        if (!str2.equals(s)) {
            throw new Exception("Test failed: " + URI.nl + "  raw-string: " + str + URI.nl + "  escaped:    " + str2 + URI.nl + "  expected:   " + s);
        }
    }
    
    private static void testUnescape(final String str, final String s) throws Exception {
        if (!unescape(str, null).equals(s)) {
            throw new Exception("Test failed: " + URI.nl + "  escaped-string: " + str + URI.nl + "  unescaped:      " + unescape(str, null) + URI.nl + "  expected:       " + s);
        }
    }
    
    static {
        defaultPorts = new Hashtable();
        usesGenericSyntax = new Hashtable();
        usesSemiGenericSyntax = new Hashtable();
        URI.defaultPorts.put("http", new Integer(80));
        URI.defaultPorts.put("shttp", new Integer(80));
        URI.defaultPorts.put("http-ng", new Integer(80));
        URI.defaultPorts.put("coffee", new Integer(80));
        URI.defaultPorts.put("https", new Integer(443));
        URI.defaultPorts.put("ftp", new Integer(21));
        URI.defaultPorts.put("telnet", new Integer(23));
        URI.defaultPorts.put("nntp", new Integer(119));
        URI.defaultPorts.put("news", new Integer(119));
        URI.defaultPorts.put("snews", new Integer(563));
        URI.defaultPorts.put("hnews", new Integer(80));
        URI.defaultPorts.put("smtp", new Integer(25));
        URI.defaultPorts.put("gopher", new Integer(70));
        URI.defaultPorts.put("wais", new Integer(210));
        URI.defaultPorts.put("whois", new Integer(43));
        URI.defaultPorts.put("whois++", new Integer(63));
        URI.defaultPorts.put("rwhois", new Integer(4321));
        URI.defaultPorts.put("imap", new Integer(143));
        URI.defaultPorts.put("pop", new Integer(110));
        URI.defaultPorts.put("prospero", new Integer(1525));
        URI.defaultPorts.put("irc", new Integer(194));
        URI.defaultPorts.put("ldap", new Integer(389));
        URI.defaultPorts.put("nfs", new Integer(2049));
        URI.defaultPorts.put("z39.50r", new Integer(210));
        URI.defaultPorts.put("z39.50s", new Integer(210));
        URI.defaultPorts.put("vemmi", new Integer(575));
        URI.defaultPorts.put("videotex", new Integer(516));
        URI.defaultPorts.put("cmp", new Integer(829));
        URI.usesGenericSyntax.put("http", Boolean.TRUE);
        URI.usesGenericSyntax.put("https", Boolean.TRUE);
        URI.usesGenericSyntax.put("shttp", Boolean.TRUE);
        URI.usesGenericSyntax.put("coffee", Boolean.TRUE);
        URI.usesGenericSyntax.put("ftp", Boolean.TRUE);
        URI.usesGenericSyntax.put("file", Boolean.TRUE);
        URI.usesGenericSyntax.put("nntp", Boolean.TRUE);
        URI.usesGenericSyntax.put("news", Boolean.TRUE);
        URI.usesGenericSyntax.put("snews", Boolean.TRUE);
        URI.usesGenericSyntax.put("hnews", Boolean.TRUE);
        URI.usesGenericSyntax.put("imap", Boolean.TRUE);
        URI.usesGenericSyntax.put("wais", Boolean.TRUE);
        URI.usesGenericSyntax.put("nfs", Boolean.TRUE);
        URI.usesGenericSyntax.put("sip", Boolean.TRUE);
        URI.usesGenericSyntax.put("sips", Boolean.TRUE);
        URI.usesGenericSyntax.put("sipt", Boolean.TRUE);
        URI.usesGenericSyntax.put("sipu", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("ldap", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("irc", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("gopher", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("videotex", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("rwhois", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("whois++", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("smtp", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("telnet", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("prospero", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("pop", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("vemmi", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("z39.50r", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("z39.50s", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("stream", Boolean.TRUE);
        URI.usesSemiGenericSyntax.put("cmp", Boolean.TRUE);
        alphanumChar = new BitSet(128);
        for (int i = 48; i <= 57; ++i) {
            URI.alphanumChar.set(i);
        }
        for (int j = 65; j <= 90; ++j) {
            URI.alphanumChar.set(j);
        }
        for (int k = 97; k <= 122; ++k) {
            URI.alphanumChar.set(k);
        }
        (markChar = new BitSet(128)).set(45);
        URI.markChar.set(95);
        URI.markChar.set(46);
        URI.markChar.set(33);
        URI.markChar.set(126);
        URI.markChar.set(42);
        URI.markChar.set(39);
        URI.markChar.set(40);
        URI.markChar.set(41);
        (reservedChar = new BitSet(128)).set(59);
        URI.reservedChar.set(47);
        URI.reservedChar.set(63);
        URI.reservedChar.set(58);
        URI.reservedChar.set(64);
        URI.reservedChar.set(38);
        URI.reservedChar.set(61);
        URI.reservedChar.set(43);
        URI.reservedChar.set(36);
        URI.reservedChar.set(44);
        (unreservedChar = new BitSet(128)).or(URI.alphanumChar);
        URI.unreservedChar.or(URI.markChar);
        (uricChar = new BitSet(128)).or(URI.unreservedChar);
        URI.uricChar.or(URI.reservedChar);
        URI.uricChar.set(37);
        (pcharChar = new BitSet(128)).or(URI.unreservedChar);
        URI.pcharChar.set(37);
        URI.pcharChar.set(58);
        URI.pcharChar.set(64);
        URI.pcharChar.set(38);
        URI.pcharChar.set(61);
        URI.pcharChar.set(43);
        URI.pcharChar.set(36);
        URI.pcharChar.set(44);
        (userinfoChar = new BitSet(128)).or(URI.unreservedChar);
        URI.userinfoChar.set(37);
        URI.userinfoChar.set(59);
        URI.userinfoChar.set(58);
        URI.userinfoChar.set(38);
        URI.userinfoChar.set(61);
        URI.userinfoChar.set(43);
        URI.userinfoChar.set(36);
        URI.userinfoChar.set(44);
        (schemeChar = new BitSet(128)).or(URI.alphanumChar);
        URI.schemeChar.set(43);
        URI.schemeChar.set(45);
        URI.schemeChar.set(46);
        (opaqueChar = new BitSet(128)).or(URI.uricChar);
        (hostChar = new BitSet(128)).or(URI.alphanumChar);
        URI.hostChar.set(45);
        URI.hostChar.set(46);
        (reg_nameChar = new BitSet(128)).or(URI.unreservedChar);
        URI.reg_nameChar.set(36);
        URI.reg_nameChar.set(44);
        URI.reg_nameChar.set(59);
        URI.reg_nameChar.set(58);
        URI.reg_nameChar.set(64);
        URI.reg_nameChar.set(38);
        URI.reg_nameChar.set(61);
        URI.reg_nameChar.set(43);
        (resvdSchemeChar = new BitSet(128)).set(58);
        (resvdUIChar = new BitSet(128)).set(64);
        (resvdHostChar = new BitSet(128)).set(58);
        URI.resvdHostChar.set(47);
        URI.resvdHostChar.set(63);
        URI.resvdHostChar.set(35);
        (resvdPathChar = new BitSet(128)).set(47);
        URI.resvdPathChar.set(59);
        URI.resvdPathChar.set(63);
        URI.resvdPathChar.set(35);
        (resvdQueryChar = new BitSet(128)).set(35);
        (escpdPathChar = new BitSet(128)).or(URI.pcharChar);
        URI.escpdPathChar.set(37);
        URI.escpdPathChar.set(47);
        URI.escpdPathChar.set(59);
        (escpdQueryChar = new BitSet(128)).or(URI.uricChar);
        URI.escpdQueryChar.clear(35);
        (escpdFragChar = new BitSet(128)).or(URI.uricChar);
        hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        nl = System.getProperty("line.separator");
    }
}
