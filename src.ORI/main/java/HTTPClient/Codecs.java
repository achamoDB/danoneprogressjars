// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.URL;
import java.net.URLConnection;
import java.io.EOFException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

public class Codecs
{
    private static BitSet BoundChar;
    private static BitSet EBCDICUnsafeChar;
    private static byte[] Base64EncMap;
    private static byte[] Base64DecMap;
    private static char[] UUEncMap;
    private static byte[] UUDecMap;
    private static final String ContDisp = "\r\nContent-Disposition: form-data; name=\"";
    private static final String FileName = "\"; filename=\"";
    private static final String ContType = "\r\nContent-Type: ";
    private static final String Boundary = "\r\n----------ieoau._._+2_8_GoodLuck8.3-dskdfJwSJKl234324jfLdsjfdAuaoei-----";
    private static NVPair[] dummy;
    
    private Codecs() {
    }
    
    public static final String base64Encode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return new String(base64Encode(s.getBytes("8859_1")), "8859_1");
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public static final byte[] base64Encode(final byte[] array) {
        if (array == null) {
            return null;
        }
        final byte[] array2 = new byte[(array.length + 2) / 3 * 4];
        int i = 0;
        int j = 0;
        while (i < array.length - 2) {
            array2[j++] = Codecs.Base64EncMap[array[i] >>> 2 & 0x3F];
            array2[j++] = Codecs.Base64EncMap[(array[i + 1] >>> 4 & 0xF) | (array[i] << 4 & 0x3F)];
            array2[j++] = Codecs.Base64EncMap[(array[i + 2] >>> 6 & 0x3) | (array[i + 1] << 2 & 0x3F)];
            array2[j++] = Codecs.Base64EncMap[array[i + 2] & 0x3F];
            i += 3;
        }
        if (i < array.length) {
            array2[j++] = Codecs.Base64EncMap[array[i] >>> 2 & 0x3F];
            if (i < array.length - 1) {
                array2[j++] = Codecs.Base64EncMap[(array[i + 1] >>> 4 & 0xF) | (array[i] << 4 & 0x3F)];
                array2[j++] = Codecs.Base64EncMap[array[i + 1] << 2 & 0x3F];
            }
            else {
                array2[j++] = Codecs.Base64EncMap[array[i] << 4 & 0x3F];
            }
        }
        while (j < array2.length) {
            array2[j] = 61;
            ++j;
        }
        return array2;
    }
    
    public static final String base64Decode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return new String(base64Decode(s.getBytes("8859_1")), "8859_1");
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public static final byte[] base64Decode(final byte[] array) {
        if (array == null) {
            return null;
        }
        int length;
        for (length = array.length; array[length - 1] == 61; --length) {}
        final byte[] array2 = new byte[length - array.length / 4];
        for (int i = 0; i < array.length; ++i) {
            array[i] = Codecs.Base64DecMap[array[i]];
        }
        int n = 0;
        int j;
        for (j = 0; j < array2.length - 2; j += 3) {
            array2[j] = (byte)((array[n] << 2 & 0xFF) | (array[n + 1] >>> 4 & 0x3));
            array2[j + 1] = (byte)((array[n + 1] << 4 & 0xFF) | (array[n + 2] >>> 2 & 0xF));
            array2[j + 2] = (byte)((array[n + 2] << 6 & 0xFF) | (array[n + 3] & 0x3F));
            n += 4;
        }
        if (j < array2.length) {
            array2[j] = (byte)((array[n] << 2 & 0xFF) | (array[n + 1] >>> 4 & 0x3));
        }
        if (++j < array2.length) {
            array2[j] = (byte)((array[n + 1] << 4 & 0xFF) | (array[n + 2] >>> 2 & 0xF));
        }
        return array2;
    }
    
    public static final char[] uuencode(final byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return new char[0];
        }
        final int n = 45;
        final char[] charArray = System.getProperty("line.separator", "\n").toCharArray();
        final char[] array2 = new char[(array.length + 2) / 3 * 4 + (array.length + n - 1) / n * (charArray.length + 1)];
        int i = 0;
        int j = 0;
        while (i + n < array.length) {
            array2[j++] = Codecs.UUEncMap[n];
            while (i < i + n) {
                array2[j++] = Codecs.UUEncMap[array[i] >>> 2 & 0x3F];
                array2[j++] = Codecs.UUEncMap[(array[i + 1] >>> 4 & 0xF) | (array[i] << 4 & 0x3F)];
                array2[j++] = Codecs.UUEncMap[(array[i + 2] >>> 6 & 0x3) | (array[i + 1] << 2 & 0x3F)];
                array2[j++] = Codecs.UUEncMap[array[i + 2] & 0x3F];
                i += 3;
            }
            for (int k = 0; k < charArray.length; ++k) {
                array2[j++] = charArray[k];
            }
        }
        array2[j++] = Codecs.UUEncMap[array.length - i];
        while (i + 2 < array.length) {
            array2[j++] = Codecs.UUEncMap[array[i] >>> 2 & 0x3F];
            array2[j++] = Codecs.UUEncMap[(array[i + 1] >>> 4 & 0xF) | (array[i] << 4 & 0x3F)];
            array2[j++] = Codecs.UUEncMap[(array[i + 2] >>> 6 & 0x3) | (array[i + 1] << 2 & 0x3F)];
            array2[j++] = Codecs.UUEncMap[array[i + 2] & 0x3F];
            i += 3;
        }
        if (i < array.length - 1) {
            array2[j++] = Codecs.UUEncMap[array[i] >>> 2 & 0x3F];
            array2[j++] = Codecs.UUEncMap[(array[i + 1] >>> 4 & 0xF) | (array[i] << 4 & 0x3F)];
            array2[j++] = Codecs.UUEncMap[array[i + 1] << 2 & 0x3F];
            array2[j++] = Codecs.UUEncMap[0];
        }
        else if (i < array.length) {
            array2[j++] = Codecs.UUEncMap[array[i] >>> 2 & 0x3F];
            array2[j++] = Codecs.UUEncMap[array[i] << 4 & 0x3F];
            array2[j++] = Codecs.UUEncMap[0];
            array2[j++] = Codecs.UUEncMap[0];
        }
        for (int l = 0; l < charArray.length; ++l) {
            array2[j++] = charArray[l];
        }
        if (j != array2.length) {
            throw new Error("Calculated " + array2.length + " chars but wrote " + j + " chars!");
        }
        return array2;
    }
    
    private static final byte[] uudecode(final BufferedReader bufferedReader) throws ParseException, IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null && !line.startsWith("begin ")) {}
        if (line == null) {
            throw new ParseException("'begin' line not found");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(line);
        stringTokenizer.nextToken();
        try {
            Integer.parseInt(stringTokenizer.nextToken(), 8);
        }
        catch (Exception ex) {
            throw new ParseException("Invalid mode on line: " + line);
        }
        try {
            stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex2) {
            throw new ParseException("No file name found on line: " + line);
        }
        byte[] resizeArray = new byte[1000];
        int n = 0;
        String line2;
        while ((line2 = bufferedReader.readLine()) != null && !line2.equals("end")) {
            final byte[] uudecode = uudecode(line2.toCharArray());
            if (n + uudecode.length > resizeArray.length) {
                resizeArray = Util.resizeArray(resizeArray, n + 1000);
            }
            System.arraycopy(uudecode, 0, resizeArray, n, uudecode.length);
            n += uudecode.length;
        }
        if (line2 == null) {
            throw new ParseException("'end' line not found");
        }
        return Util.resizeArray(resizeArray, n);
    }
    
    public static final byte[] uudecode(final char[] array) {
        if (array == null) {
            return null;
        }
        final byte[] array2 = new byte[array.length / 4 * 3];
        int i = 0;
        byte b = 0;
        while (i < array.length) {
            int n;
            byte b2;
            byte b3;
            byte b4;
            byte b5;
            for (n = b + Codecs.UUDecMap[array[i++]]; b < n - 2; array2[b++] = (byte)((b2 << 2 & 0xFF) | (b3 >>> 4 & 0x3)), array2[b++] = (byte)((b3 << 4 & 0xFF) | (b4 >>> 2 & 0xF)), array2[b++] = (byte)((b4 << 6 & 0xFF) | (b5 & 0x3F)), i += 4) {
                b2 = Codecs.UUDecMap[array[i]];
                b3 = Codecs.UUDecMap[array[i + 1]];
                b4 = Codecs.UUDecMap[array[i + 2]];
                b5 = Codecs.UUDecMap[array[i + 3]];
            }
            if (b < n) {
                array2[b++] = (byte)((Codecs.UUDecMap[array[i]] << 2 & 0xFF) | (Codecs.UUDecMap[array[i + 1]] >>> 4 & 0x3));
            }
            if (b < n) {
                array2[b++] = (byte)((Codecs.UUDecMap[array[i + 1]] << 4 & 0xFF) | (Codecs.UUDecMap[array[i + 2]] >>> 2 & 0xF));
            }
            while (i < array.length && array[i] != '\n' && array[i] != '\r') {
                ++i;
            }
            while (i < array.length && (array[i] == '\n' || array[i] == '\r')) {
                ++i;
            }
        }
        return Util.resizeArray(array2, b);
    }
    
    public static final String quotedPrintableEncode(final String s) {
        if (s == null) {
            return null;
        }
        final char[] array = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final char[] charArray = System.getProperty("line.separator", "\n").toCharArray();
        char[] resizeArray = new char[(int)(s.length() * 1.5)];
        final char[] charArray2 = s.toCharArray();
        int n = 0;
        int n2 = 1;
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char bitIndex = charArray2[i];
            if (bitIndex == charArray[0] && match(charArray2, i, charArray)) {
                if (resizeArray[n2 - 1] == ' ') {
                    resizeArray[n2 - 1] = '=';
                    resizeArray[n2++] = '2';
                    resizeArray[n2++] = '0';
                }
                else if (resizeArray[n2 - 1] == '\t') {
                    resizeArray[n2 - 1] = '=';
                    resizeArray[n2++] = '0';
                    resizeArray[n2++] = '9';
                }
                resizeArray[n2++] = '\r';
                resizeArray[n2++] = '\n';
                i += charArray.length - 1;
                n = n2;
            }
            else if (bitIndex > '~' || (bitIndex < ' ' && bitIndex != '\t') || bitIndex == '=' || Codecs.EBCDICUnsafeChar.get(bitIndex)) {
                resizeArray[n2++] = '=';
                resizeArray[n2++] = array[(bitIndex & '\u00f0') >>> 4];
                resizeArray[n2++] = array[bitIndex & '\u000f'];
            }
            else {
                resizeArray[n2++] = bitIndex;
            }
            if (n2 > n + 70) {
                resizeArray[n2++] = '=';
                resizeArray[n2++] = '\r';
                resizeArray[n2++] = '\n';
                n = n2;
            }
            if (n2 > resizeArray.length - 5) {
                resizeArray = Util.resizeArray(resizeArray, resizeArray.length + 500);
            }
        }
        return String.valueOf(resizeArray, 1, n2 - 1);
    }
    
    private static final boolean match(final char[] array, final int n, final char[] array2) {
        if (array.length < n + array2.length) {
            return false;
        }
        for (int i = 1; i < array2.length; ++i) {
            if (array[n + i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static final String quotedPrintableDecode(final String s) throws ParseException {
        if (s == null) {
            return null;
        }
        char[] resizeArray = new char[(int)(s.length() * 1.1)];
        final char[] charArray = s.toCharArray();
        final char[] charArray2 = System.getProperty("line.separator", "\n").toCharArray();
        int n = 0;
        int count = 0;
        final int length = s.length();
        int i = 0;
        while (i < length) {
            final char c = charArray[i++];
            if (c == '=') {
                if (i >= length - 1) {
                    throw new ParseException("Premature end of input detected");
                }
                if (charArray[i] == '\n' || charArray[i] == '\r') {
                    ++i;
                    if (charArray[i - 1] == '\r' && charArray[i] == '\n') {
                        ++i;
                    }
                }
                else {
                    final int digit = Character.digit(charArray[i], 16);
                    final int digit2 = Character.digit(charArray[i + 1], 16);
                    if ((digit | digit2) < 0) {
                        throw new ParseException(new String(charArray, i - 1, 3) + " is an invalid code");
                    }
                    final char c2 = (char)(digit << 4 | digit2);
                    i += 2;
                    resizeArray[count++] = c2;
                }
                n = count;
            }
            else if (c == '\n' || c == '\r') {
                if (c == '\r' && i < length && charArray[i] == '\n') {
                    ++i;
                }
                for (int j = 0; j < charArray2.length; ++j) {
                    resizeArray[n++] = charArray2[j];
                }
                count = n;
            }
            else {
                resizeArray[count++] = c;
                if (c != ' ' && c != '\t') {
                    n = count;
                }
            }
            if (count > resizeArray.length - charArray2.length - 2) {
                resizeArray = Util.resizeArray(resizeArray, resizeArray.length + 500);
            }
        }
        return new String(resizeArray, 0, count);
    }
    
    public static final String URLEncode(final String s) {
        if (s == null) {
            return null;
        }
        return URLEncoder.encode(s);
    }
    
    public static final String URLDecode(final String s) throws ParseException {
        if (s == null) {
            return null;
        }
        final char[] data = new char[s.length()];
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '+') {
                data[count++] = ' ';
            }
            else {
                if (char1 == '%') {
                    try {
                        data[count++] = (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
                        i += 2;
                        continue;
                    }
                    catch (NumberFormatException ex) {
                        throw new ParseException(s.substring(i, i + 3) + " is an invalid code");
                    }
                }
                data[count++] = char1;
            }
        }
        return String.valueOf(data, 0, count);
    }
    
    public static final NVPair[] mpFormDataDecode(final byte[] array, final String s, final String s2) throws IOException, ParseException {
        return mpFormDataDecode(array, s, s2, null);
    }
    
    public static final NVPair[] mpFormDataDecode(final byte[] array, final String str, final String parent, final FilenameMangler filenameMangler) throws IOException, ParseException {
        final String parameter = Util.getParameter("boundary", str);
        if (parameter == null) {
            throw new ParseException("'boundary' parameter not found in Content-type: " + str);
        }
        final byte[] bytes = ("--" + parameter + "\r\n").getBytes("8859_1");
        final byte[] bytes2 = ("\r\n--" + parameter + "\r\n").getBytes("8859_1");
        final byte[] bytes3 = ("\r\n--" + parameter + "--").getBytes("8859_1");
        final int[] compile_search = Util.compile_search(bytes);
        final int[] compile_search2 = Util.compile_search(bytes2);
        final int[] compile_search3 = Util.compile_search(bytes3);
        final int str2 = Util.findStr(bytes, compile_search, array, 0, array.length);
        if (str2 == -1) {
            throw new ParseException("Starting boundary not found: " + new String(bytes, "8859_1"));
        }
        int offset = str2 + bytes.length;
        NVPair[] resizeArray = new NVPair[10];
        int i = 0;
        int n = 0;
        while (i == 0) {
            int j = Util.findStr(bytes2, compile_search2, array, offset, array.length);
            if (j == -1) {
                j = Util.findStr(bytes3, compile_search3, array, offset, array.length);
                if (j == -1) {
                    throw new ParseException("Ending boundary not found: " + new String(bytes3, "8859_1"));
                }
                i = 1;
            }
            String value = null;
            String child = null;
            String s = null;
            while (true) {
                int n2 = findEOL(array, offset) + 2;
                if (n2 - 2 <= offset) {
                    offset += 2;
                    if (offset > j) {
                        throw new ParseException("End of header not found at offset " + j);
                    }
                    if (s == null) {
                        throw new ParseException("Missing 'Content-Disposition' header at offset " + offset);
                    }
                    String s2;
                    if (child != null) {
                        if (filenameMangler != null) {
                            child = filenameMangler.mangleFilename(child, value);
                        }
                        if (child != null && child.length() > 0) {
                            final FileOutputStream fileOutputStream = new FileOutputStream(new File(parent, child));
                            fileOutputStream.write(array, offset, j - offset);
                            fileOutputStream.close();
                        }
                        s2 = child;
                    }
                    else {
                        s2 = new String(array, offset, j - offset, "8859_1");
                    }
                    if (n >= resizeArray.length) {
                        resizeArray = Util.resizeArray(resizeArray, n + 10);
                    }
                    resizeArray[n] = new NVPair(value, s2);
                    offset = j + bytes2.length;
                    ++n;
                    break;
                }
                else {
                    String string;
                    byte b;
                    for (string = new String(array, offset, n2 - 2 - offset, "8859_1"), offset = n2; n2 < array.length - 1 && ((b = array[n2]) == 32 || b == 9); n2 = findEOL(array, offset) + 2, string += new String(array, offset, n2 - 2 - offset, "8859_1"), offset = n2) {}
                    if (!string.regionMatches(true, 0, "Content-Disposition", 0, 19)) {
                        continue;
                    }
                    final HttpHeaderElement element = Util.getElement(Util.parseHeader(string.substring(string.indexOf(58) + 1)), "form-data");
                    if (element == null) {
                        throw new ParseException("Expected 'Content-Disposition: form-data' in line: " + string);
                    }
                    final NVPair[] params = element.getParams();
                    child = (value = null);
                    for (int k = 0; k < params.length; ++k) {
                        if (params[k].getName().equalsIgnoreCase("name")) {
                            value = params[k].getValue();
                        }
                        if (params[k].getName().equalsIgnoreCase("filename")) {
                            child = params[k].getValue();
                        }
                    }
                    if (value == null) {
                        throw new ParseException("'name' parameter not found in header: " + string);
                    }
                    s = string;
                }
            }
        }
        return Util.resizeArray(resizeArray, n);
    }
    
    private static final int findEOL(final byte[] array, int i) {
        while (i < array.length - 1) {
            if (array[i++] == 13) {
                if (array[i] != 10) {
                    continue;
                }
                break;
            }
        }
        return i - 1;
    }
    
    public static final byte[] mpFormDataEncode(final NVPair[] array, final NVPair[] array2, final NVPair[] array3) throws IOException {
        return mpFormDataEncode(array, array2, array3, null);
    }
    
    public static final byte[] mpFormDataEncode(NVPair[] dummy, NVPair[] dummy2, final NVPair[] array, final FilenameMangler filenameMangler) throws IOException {
        final byte[] bytes = "\r\n----------ieoau._._+2_8_GoodLuck8.3-dskdfJwSJKl234324jfLdsjfdAuaoei-----".getBytes("8859_1");
        final byte[] bytes2 = "\r\nContent-Disposition: form-data; name=\"".getBytes("8859_1");
        final byte[] bytes3 = "\r\nContent-Type: ".getBytes("8859_1");
        final byte[] bytes4 = "\"; filename=\"".getBytes("8859_1");
        int n = 0;
        final int n2 = bytes.length + bytes2.length + 1 + 2 + 2;
        if (dummy == null) {
            dummy = Codecs.dummy;
        }
        if (dummy2 == null) {
            dummy2 = Codecs.dummy;
        }
        for (int i = 0; i < dummy.length; ++i) {
            if (dummy[i] != null) {
                n += n2 + dummy[i].getName().length() + dummy[i].getValue().length();
            }
        }
        for (int j = 0; j < dummy2.length; ++j) {
            if (dummy2[j] != null) {
                final File file = new File(dummy2[j].getValue());
                String s = file.getName();
                if (filenameMangler != null) {
                    s = filenameMangler.mangleFilename(s, dummy2[j].getName());
                }
                if (s != null) {
                    n = (int)(n + (n2 + dummy2[j].getName().length() + bytes4.length) + (s.length() + file.length()));
                    final String contentType = CT.getContentType(file.getName());
                    if (contentType != null) {
                        n += bytes3.length + contentType.length();
                    }
                }
            }
        }
        if (n == 0) {
            array[0] = new NVPair("Content-Type", "application/octet-stream");
            return new byte[0];
        }
        n -= 2;
        final int k = n + (bytes.length + 2 + 2);
        final byte[] b = new byte[k];
        int off = 0;
        int l = 808464432;
    Label_0321:
        while (l != 2054847098) {
            off = 0;
            while (!Codecs.BoundChar.get(l & 0xFF)) {
                ++l;
            }
            while (!Codecs.BoundChar.get(l >> 8 & 0xFF)) {
                l += 256;
            }
            while (!Codecs.BoundChar.get(l >> 16 & 0xFF)) {
                l += 65536;
            }
            while (!Codecs.BoundChar.get(l >> 24 & 0xFF)) {
                l += 16777216;
            }
            bytes[40] = (byte)(l & 0xFF);
            bytes[42] = (byte)(l >> 8 & 0xFF);
            bytes[44] = (byte)(l >> 16 & 0xFF);
            bytes[46] = (byte)(l >> 24 & 0xFF);
            int n3 = 2;
            final int[] compile_search = Util.compile_search(bytes);
        Label_1183:
            while (true) {
                for (int n4 = 0; n4 < dummy.length; ++n4) {
                    if (dummy[n4] != null) {
                        System.arraycopy(bytes, n3, b, off, bytes.length - n3);
                        final int n5 = off + (bytes.length - n3);
                        n3 = 0;
                        final int n6 = n5;
                        System.arraycopy(bytes2, 0, b, n5, bytes2.length);
                        final int n7 = n5 + bytes2.length;
                        final int length = dummy[n4].getName().length();
                        System.arraycopy(dummy[n4].getName().getBytes("8859_1"), 0, b, n7, length);
                        int n8 = n7 + length;
                        b[n8++] = 34;
                        b[n8++] = 13;
                        b[n8++] = 10;
                        b[n8++] = 13;
                        b[n8++] = 10;
                        final int length2 = dummy[n4].getValue().length();
                        System.arraycopy(dummy[n4].getValue().getBytes("8859_1"), 0, b, n8, length2);
                        off = n8 + length2;
                        if (off - n6 >= bytes.length && Util.findStr(bytes, compile_search, b, n6, off) != -1) {
                            ++l;
                            continue Label_0321;
                        }
                    }
                }
                for (int n9 = 0; n9 < dummy2.length; ++n9) {
                    if (dummy2[n9] != null) {
                        final File file2 = new File(dummy2[n9].getValue());
                        String s2 = file2.getName();
                        if (filenameMangler != null) {
                            s2 = filenameMangler.mangleFilename(s2, dummy2[n9].getName());
                        }
                        if (s2 != null) {
                            System.arraycopy(bytes, n3, b, off, bytes.length - n3);
                            final int n10 = off + (bytes.length - n3);
                            n3 = 0;
                            final int n11 = n10;
                            System.arraycopy(bytes2, 0, b, n10, bytes2.length);
                            final int n12 = n10 + bytes2.length;
                            final int length3 = dummy2[n9].getName().length();
                            System.arraycopy(dummy2[n9].getName().getBytes("8859_1"), 0, b, n12, length3);
                            final int n13 = n12 + length3;
                            System.arraycopy(bytes4, 0, b, n13, bytes4.length);
                            final int n14 = n13 + bytes4.length;
                            final int length4 = s2.length();
                            System.arraycopy(s2.getBytes("8859_1"), 0, b, n14, length4);
                            off = n14 + length4;
                            b[off++] = 34;
                            final String contentType2 = CT.getContentType(file2.getName());
                            if (contentType2 != null) {
                                System.arraycopy(bytes3, 0, b, off, bytes3.length);
                                final int n15 = off + bytes3.length;
                                System.arraycopy(contentType2.getBytes("8859_1"), 0, b, n15, contentType2.length());
                                off = n15 + contentType2.length();
                            }
                            b[off++] = 13;
                            b[off++] = 10;
                            b[off++] = 13;
                            b[off++] = 10;
                            int len = (int)file2.length();
                            final FileInputStream fileInputStream = new FileInputStream(file2);
                            while (len > 0) {
                                final int read = fileInputStream.read(b, off, len);
                                len -= read;
                                off += read;
                            }
                            fileInputStream.close();
                            if (off - n11 >= bytes.length && Util.findStr(bytes, compile_search, b, n11, off) != -1) {
                                continue Label_1183;
                            }
                        }
                    }
                }
                break;
            }
            break;
        }
        System.arraycopy(bytes, 0, b, off, bytes.length);
        int m = off + bytes.length;
        b[m++] = 45;
        b[m++] = 45;
        b[m++] = 13;
        b[m++] = 10;
        if (m != k) {
            throw new Error("Calculated " + k + " bytes but wrote " + m + " bytes!");
        }
        array[0] = new NVPair("Content-Type", "multipart/form-data; boundary=" + new String(bytes, 4, bytes.length - 4, "8859_1"));
        return b;
    }
    
    public static final String nv2query(final NVPair[] array) {
        if (array == null) {
            return null;
        }
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                sb.append(URLEncode(array[i].getName()) + "=" + URLEncode(array[i].getValue()) + "&");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public static final NVPair[] query2nv(final String s) throws ParseException {
        if (s == null) {
            return null;
        }
        int index = -1;
        int n = 1;
        while ((index = s.indexOf(38, index + 1)) != -1) {
            ++n;
        }
        final NVPair[] array = new NVPair[n];
        int n2 = 0;
        for (int i = 0; i < array.length; ++i) {
            final int index2 = s.indexOf(61, n2);
            int n3 = s.indexOf(38, n2);
            if (n3 == -1) {
                n3 = s.length();
            }
            if (index2 == -1 || index2 >= n3) {
                throw new ParseException("'=' missing in " + s.substring(n2, n3));
            }
            array[i] = new NVPair(URLDecode(s.substring(n2, index2)), URLDecode(s.substring(index2 + 1, n3)));
            n2 = n3 + 1;
        }
        return array;
    }
    
    public static final byte[] chunkedEncode(final byte[] array, final NVPair[] array2, final boolean b) {
        return chunkedEncode(array, 0, (array == null) ? 0 : array.length, array2, b);
    }
    
    public static final byte[] chunkedEncode(byte[] array, final int n, int i, NVPair[] array2, final boolean b) {
        if (array == null) {
            array = new byte[0];
            i = 0;
        }
        if (b && array2 == null) {
            array2 = new NVPair[0];
        }
        final String string = Integer.toString(i, 16);
        int n2 = 0;
        if (i > 0) {
            n2 += string.length() + 2 + i + 2;
        }
        if (b) {
            n2 += 3;
            for (int j = 0; j < array2.length; ++j) {
                n2 += array2[j].getName().length() + 2 + array2[j].getValue().length() + 2;
            }
            n2 += 2;
        }
        final byte[] array3 = new byte[n2];
        int k = 0;
        if (i > 0) {
            final int length = string.length();
            try {
                System.arraycopy(string.getBytes("8859_1"), 0, array3, k, length);
            }
            catch (UnsupportedEncodingException ex) {
                throw new Error(ex.toString());
            }
            int n3 = k + length;
            array3[n3++] = 13;
            array3[n3++] = 10;
            System.arraycopy(array, n, array3, n3, i);
            k = n3 + i;
            array3[k++] = 13;
            array3[k++] = 10;
        }
        if (b) {
            array3[k++] = 48;
            array3[k++] = 13;
            array3[k++] = 10;
            for (int l = 0; l < array2.length; ++l) {
                final int length2 = array2[l].getName().length();
                try {
                    System.arraycopy(array2[l].getName().getBytes("8859_1"), 0, array3, k, length2);
                }
                catch (UnsupportedEncodingException ex2) {
                    throw new Error(ex2.toString());
                }
                int n4 = k + length2;
                array3[n4++] = 58;
                array3[n4++] = 32;
                final int length3 = array2[l].getValue().length();
                try {
                    System.arraycopy(array2[l].getValue().getBytes("8859_1"), 0, array3, n4, length3);
                }
                catch (UnsupportedEncodingException ex3) {
                    throw new Error(ex3.toString());
                }
                k = n4 + length3;
                array3[k++] = 13;
                array3[k++] = 10;
            }
            array3[k++] = 13;
            array3[k++] = 10;
        }
        if (k != array3.length) {
            throw new Error("Calculated " + array3.length + " bytes but wrote " + k + " bytes!");
        }
        return array3;
    }
    
    public static final Object chunkedDecode(final InputStream in) throws ParseException, IOException {
        final long chunkLength = getChunkLength(in);
        if (chunkLength > 2147483647L) {
            throw new ParseException("Can't deal with chunk lengths greater Integer.MAX_VALUE: " + chunkLength + " > " + Integer.MAX_VALUE);
        }
        if (chunkLength <= 0L) {
            NVPair[] resizeArray = new NVPair[0];
            String line;
            while ((line = new BufferedReader(new InputStreamReader(in, "8859_1")).readLine()) != null && line.length() > 0) {
                final int index = line.indexOf(58);
                if (index == -1) {
                    throw new ParseException("Error in Footer format: no ':' found in '" + line + "'");
                }
                resizeArray = Util.resizeArray(resizeArray, resizeArray.length + 1);
                resizeArray[resizeArray.length - 1] = new NVPair(line.substring(0, index).trim(), line.substring(index + 1).trim());
            }
            return resizeArray;
        }
        byte[] b;
        int off;
        int read;
        for (b = new byte[(int)chunkLength], off = 0, read = 0; read != -1 && off < b.length; read = in.read(b, off, b.length - off), off += read) {}
        if (read == -1) {
            throw new ParseException("Premature EOF while reading chunk;Expected: " + b.length + " Bytes, " + "Received: " + (off + 1) + " Bytes");
        }
        in.read();
        in.read();
        return b;
    }
    
    static final long getChunkLength(final InputStream inputStream) throws ParseException, IOException {
        final byte[] bytes = new byte[16];
        int length = 0;
        int read;
        while ((read = inputStream.read()) > 0) {
            if (read != 32) {
                if (read == 9) {
                    continue;
                }
                break;
            }
        }
        if (read < 0) {
            throw new EOFException("Premature EOF while reading chunk length");
        }
        bytes[length++] = (byte)read;
        int n;
        while ((n = inputStream.read()) > 0 && n != 13 && n != 10 && n != 32 && n != 9 && n != 59 && length < bytes.length) {
            bytes[length++] = (byte)n;
        }
        while ((n == 32 || n == 9) && (n = inputStream.read()) > 0) {}
        if (n == 59) {
            while ((n = inputStream.read()) > 0 && n != 13 && n != 10) {}
        }
        if (n < 0) {
            throw new EOFException("Premature EOF while reading chunk length");
        }
        if (n != 10 && (n != 13 || inputStream.read() != 10)) {
            throw new ParseException("Didn't find valid chunk length: " + new String(bytes, 0, length, "8859_1"));
        }
        try {
            return Long.parseLong(new String(bytes, 0, length, "8859_1").trim(), 16);
        }
        catch (NumberFormatException ex) {
            throw new ParseException("Didn't find valid chunk length: " + new String(bytes, 0, length, "8859_1"));
        }
    }
    
    static {
        Codecs.BoundChar = new BitSet(256);
        for (int i = 48; i <= 57; ++i) {
            Codecs.BoundChar.set(i);
        }
        for (int j = 65; j <= 90; ++j) {
            Codecs.BoundChar.set(j);
        }
        for (int k = 97; k <= 122; ++k) {
            Codecs.BoundChar.set(k);
        }
        Codecs.BoundChar.set(43);
        Codecs.BoundChar.set(95);
        Codecs.BoundChar.set(45);
        Codecs.BoundChar.set(46);
        (Codecs.EBCDICUnsafeChar = new BitSet(256)).set(33);
        Codecs.EBCDICUnsafeChar.set(34);
        Codecs.EBCDICUnsafeChar.set(35);
        Codecs.EBCDICUnsafeChar.set(36);
        Codecs.EBCDICUnsafeChar.set(64);
        Codecs.EBCDICUnsafeChar.set(91);
        Codecs.EBCDICUnsafeChar.set(92);
        Codecs.EBCDICUnsafeChar.set(93);
        Codecs.EBCDICUnsafeChar.set(94);
        Codecs.EBCDICUnsafeChar.set(96);
        Codecs.EBCDICUnsafeChar.set(123);
        Codecs.EBCDICUnsafeChar.set(124);
        Codecs.EBCDICUnsafeChar.set(125);
        Codecs.EBCDICUnsafeChar.set(126);
        Codecs.Base64EncMap = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        Codecs.Base64DecMap = new byte[128];
        for (int l = 0; l < Codecs.Base64EncMap.length; ++l) {
            Codecs.Base64DecMap[Codecs.Base64EncMap[l]] = (byte)l;
        }
        Codecs.UUEncMap = new char[64];
        for (int n = 0; n < Codecs.UUEncMap.length; ++n) {
            Codecs.UUEncMap[n] = (char)(n + 32);
        }
        Codecs.UUDecMap = new byte[128];
        for (int n2 = 0; n2 < Codecs.UUEncMap.length; ++n2) {
            Codecs.UUDecMap[Codecs.UUEncMap[n2]] = (byte)n2;
        }
        Codecs.dummy = new NVPair[0];
    }
    
    private static class CT extends URLConnection
    {
        protected static final String getContentType(final String fname) {
            return URLConnection.guessContentTypeFromName(fname);
        }
        
        private CT() {
            super(null);
        }
        
        public void connect() {
        }
    }
}
