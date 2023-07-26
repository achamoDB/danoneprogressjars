import java.io.UnsupportedEncodingException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ip
{
    static final String a;
    static final byte[] b;
    
    public static byte[] a(final String s, final String charsetName) {
        try {
            return s.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException ex) {
            return s.getBytes();
        }
    }
    
    public static boolean a(final char c) {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ': {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static byte[] a(final byte[] array, final String s, final byte[] array2) {
        String s2;
        try {
            s2 = new String(array, "8859_1");
        }
        catch (UnsupportedEncodingException ex) {
            s2 = new String(array);
        }
        final StringBuffer sb = new StringBuffer("");
        if (s == null || s.equals("CLEAR")) {
            final String string = sb.append("-----BEGIN RSA PRIVATE KEY-----").append(psc_ip.a).append(s2).append(psc_ip.a).append("-----END RSA PRIVATE KEY-----").toString();
            try {
                return string.getBytes("8859_1");
            }
            catch (UnsupportedEncodingException ex2) {
                return string.getBytes();
            }
        }
        String str = null;
        if (s.equals("DES")) {
            str = "Proc-Type: 4,ENCRYPTED\nDEK-Info: DES-CBC,";
        }
        else if (s.equals("3DES_EDE")) {
            str = "Proc-Type: 4,ENCRYPTED\nDEK-Info: DES-EDE3-CBC,";
        }
        final StringBuffer append = sb.append("-----BEGIN RSA PRIVATE KEY-----").append(psc_ip.a).append(str).append(b(array2, 0, array2.length)).append("\n\n").append(s2).append(psc_ip.a).append("-----END RSA PRIVATE KEY-----");
        try {
            return append.toString().getBytes("8859_1");
        }
        catch (UnsupportedEncodingException ex3) {
            return append.toString().getBytes();
        }
    }
    
    public static byte[] a(final byte[] array) throws psc_bf {
        final String str = "-----BEGIN RSA PRIVATE KEY-----";
        String s;
        try {
            s = new String(array, 0, array.length, "8859_1");
        }
        catch (UnsupportedEncodingException ex) {
            s = new String(array, 0, array.length);
        }
        final int index = s.indexOf(str);
        if (index < 0) {
            throw new psc_bf("Not a valid RSA private SSLC key, missing header");
        }
        int beginIndex;
        for (beginIndex = index + str.length(); a(s.charAt(beginIndex)); ++beginIndex) {}
        final int index2 = s.indexOf("-", beginIndex);
        if (index2 < 0) {
            throw new psc_bf("Not a valid RSA private SSLC key, missing footer");
        }
        int n;
        int endIndex;
        for (n = index2 - beginIndex, endIndex = index2; Character.isWhitespace(s.charAt(beginIndex + n - 1)); --n, --endIndex) {}
        final String substring = s.substring(beginIndex, endIndex);
        byte[] array2;
        try {
            array2 = substring.getBytes("8859_1");
        }
        catch (UnsupportedEncodingException ex2) {
            array2 = substring.getBytes();
        }
        return array2;
    }
    
    public static byte[] b(final byte[] array) throws psc_bf {
        psc_lj a = null;
        try {
            a = psc_lj.a("Base64-64", "Java");
            a.i();
            final int a2 = a.a(array.length);
            byte[] array2 = new byte[a2];
            final int b = a.b(array, 0, array.length, array2, 0);
            final int n = b + a.b(array2, b);
            if (n != a2) {
                final byte[] array3 = new byte[n];
                System.arraycopy(array2, 0, array3, 0, n);
                array2 = array3;
            }
            return array2;
        }
        catch (psc_ao psc_ao) {
            throw new psc_bf(psc_ao.getMessage());
        }
        catch (psc_en psc_en) {
            throw new psc_bf(psc_en.getMessage());
        }
        catch (psc_e1 psc_e1) {
            throw new psc_bf(psc_e1.getMessage());
        }
        catch (psc_be psc_be) {
            throw new psc_bf(psc_be.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    static byte[] a(final byte[] array, final int n, final int n2) {
        int n3 = n / n2;
        if (n - n3 * n2 > 0) {
            ++n3;
        }
        final byte[] array2 = new byte[n + n3 * psc_ip.b.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = 0;
        }
        int j = 0;
        int n4 = 0;
        while (j < n) {
            array2[n4++] = array[j];
            if (j % n2 == n2 - 1) {
                for (int k = 0; k < psc_ip.b.length; ++k) {
                    array2[n4++] = psc_ip.b[k];
                }
            }
            ++j;
        }
        return array2;
    }
    
    public static byte[] c(final byte[] array) throws psc_bf {
        psc_lj a = null;
        try {
            a = psc_lj.a("Base64-64", "Java");
            a.g();
            final int a2 = a.a(array.length);
            final byte[] array2 = new byte[a2];
            final int a3 = a.a(array, 0, array.length, array2, 0);
            final int n = a3 + a.a(array2, a3);
            byte[] array3 = array2;
            if (n != a2) {
                final byte[] array4 = new byte[n];
                System.arraycopy(array3, 0, array4, 0, n);
                array3 = array4;
            }
            return array3;
        }
        catch (psc_ao psc_ao) {
            throw new psc_bf(psc_ao.getMessage());
        }
        catch (psc_en psc_en) {
            throw new psc_bf(psc_en.getMessage());
        }
        catch (psc_be psc_be) {
            throw new psc_bf(psc_be.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    public static byte[] a(final byte[] array, final String[] array2, final byte[] array3) throws psc_bf {
        final String str = "-----BEGIN RSA PRIVATE KEY-----";
        final String s = "DEK-Info: DES-CBC,";
        final String s2 = "DEK-Info: DES-EDE3-CBC,";
        String s3;
        try {
            s3 = new String(array, 0, array.length, "8859_1");
        }
        catch (UnsupportedEncodingException ex) {
            s3 = new String(array, 0, array.length);
        }
        if (s3.indexOf(str) < 0) {
            throw new psc_bf("Not a valid RSA private SSLC key, missing header");
        }
        final int index = s3.indexOf(s);
        final int index2 = s3.indexOf(s2);
        final int n = 8;
        int beginIndex;
        if (index > 0) {
            array2[0] = "DES";
            beginIndex = index + s.length();
        }
        else {
            if (index2 <= 0) {
                throw new psc_bf("Not a valid SSL-C PEM encoded RSA private key, missing encryption algorithm indicator expected either " + s + " or " + s2);
            }
            array2[0] = "3DES_EDE";
            beginIndex = index2 + s2.length();
        }
        final int n2 = n * 2;
        final byte[] a = a(s3.substring(beginIndex, beginIndex + n2));
        System.arraycopy(a, 0, array3, 0, a.length);
        int beginIndex2;
        for (beginIndex2 = beginIndex + n2; a(s3.charAt(beginIndex2)); ++beginIndex2) {}
        final int index3 = s3.indexOf("-----END RSA PRIVATE KEY-----", beginIndex2);
        if (index3 < 0) {
            throw new psc_bf("Not a valid RSA private SSL-C key, missing footer");
        }
        int n3 = index3 - beginIndex2;
        int endIndex;
        for (endIndex = index3; a(s3.charAt(endIndex - 1)); --endIndex) {
            --n3;
        }
        final String substring = s3.substring(beginIndex2, endIndex);
        byte[] array4;
        try {
            array4 = substring.getBytes("8859_1");
        }
        catch (UnsupportedEncodingException ex2) {
            array4 = substring.getBytes();
        }
        if (array4.length != n3) {
            System.err.println("ERROR");
        }
        return array4;
    }
    
    public static void a(final byte[] array, final int n, final int[] array2) throws psc_bf {
        if ((array[n] & 0x80) == 0x0) {
            array2[array2[0] = 1] = array[n];
            return;
        }
        final int n2 = array[n] & 0x7F;
        if (n2 > 5) {
            throw new psc_bf("Invalid SSL-C key, too may octets");
        }
        int n3 = 1;
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            ++n3;
            n4 += (array[n + 1 + i] & 0xFF) << 8 * (n2 - i - 1);
        }
        array2[0] = n3;
        array2[1] = n4;
    }
    
    static int[] a(final byte[] array, final int n) throws psc_en {
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_k psc_k = new psc_k(12288);
        final psc_t psc_t = new psc_t(0);
        final psc_i[] array2 = { psc_h, psc_k, psc_t, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_en("Cannot build the PKCS #8 unencrypted key: " + psc_m.getMessage());
        }
        return new int[] { psc_t.c, psc_t.d };
    }
    
    public static byte[] d(final byte[] array) throws psc_bf {
        final int n = 1;
        final int n2 = array.length - 1;
        final int[] array2 = { 0, 0 };
        a(array, n, array2);
        int n3 = n + array2[0];
        int n4 = n2 - array2[0];
        for (int i = 0; i < 2; ++i) {
            ++n3;
            --n4;
            a(array, n3, array2);
            n3 += array2[0] + array2[1];
            n4 -= array2[0] + array2[1];
        }
        ++n3;
        --n4;
        a(array, n3, array2);
        final int n5 = n3 + array2[0];
        final int j = n4 - array2[0];
        a(array, n5 + 1, array2);
        final int k = array2[1] + array2[0] + 1;
        if (k != j) {
            throw new RuntimeException("FATAL ERROR: dataLen = " + j + ", but computed len = " + k);
        }
        final byte[] array3 = new byte[k];
        System.arraycopy(array, n5, array3, 0, k);
        return array3;
    }
    
    public static byte[] e(final byte[] array) throws psc_bf {
        final String[] array2 = { null };
        final byte[] array3 = new byte[8];
        a(array, array2, array3);
        return array3;
    }
    
    public static byte[] f(final byte[] array) throws psc_bf {
        final byte[] array2 = { 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4 };
        final byte[] a = a(array.length);
        final int n = array2.length + a.length + array.length;
        final byte[] a2 = a(n);
        final byte[] array3 = new byte[1 + a2.length + n];
        int n2 = 0;
        array3[n2++] = 48;
        System.arraycopy(a2, 0, array3, n2, a2.length);
        final int n3 = n2 + a2.length;
        System.arraycopy(array2, 0, array3, n3, array2.length);
        final int n4 = n3 + array2.length;
        System.arraycopy(a, 0, array3, n4, a.length);
        final int n5 = n4 + a.length;
        System.arraycopy(array, 0, array3, n5, array.length);
        final int n6 = n5 + array.length;
        return array3;
    }
    
    public static byte[] a(final int n) throws psc_bf {
        byte[] array;
        if (n < 128) {
            array = new byte[] { (byte)n };
        }
        else if (n < 255) {
            array = new byte[] { -127, (byte)n };
        }
        else {
            if (n >= 65535) {
                throw new psc_bf("key length is too big");
            }
            array = new byte[] { -126, (byte)(n >> 8), (byte)n };
        }
        return array;
    }
    
    public static String a(final byte[] array, final int n, final byte[] array2) throws psc_bf {
        final String[] array3 = { null };
        a(array, array3, array2);
        return "PBE/MD5/" + array3[0] + "/CBC/SSLCPBE";
    }
    
    public static final String b(final byte[] array, final int n, final int n2) {
        return psc_a5.a(array, n, n2);
    }
    
    public static final byte[] a(final String s) {
        return psc_a5.b(s);
    }
    
    static {
        a = System.getProperty("line.separator");
        b = a(psc_ip.a, "8859_1");
    }
}
