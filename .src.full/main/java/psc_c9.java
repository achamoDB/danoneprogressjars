// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_c9
{
    public static String a(final byte[] array, final int n, final int n2) {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer("                ");
        sb.append("  000000: ");
        for (int i = 0; i < n2; ++i) {
            if (i % 16 == 0 && i != 0) {
                sb.append("[");
                sb.append(sb2.toString());
                sb.append("]\n");
                sb.append("  ");
                sb.append(a(i, 3));
                sb.append(": ");
                for (int j = 0; j < 16; ++j) {
                    sb2.setCharAt(j, ' ');
                }
            }
            sb.append(a(array[i + n], 1));
            sb.append(" ");
            if (array[i + n] < 32 || array[i + n] > 127 || array[i + n] == 127) {
                sb2.setCharAt(i % 16, '.');
            }
            else {
                sb2.setCharAt(i % 16, (char)array[i + n]);
            }
        }
        if (n2 % 16 != 0 || n2 == 0) {
            for (int k = 0; k < 16 - n2 % 16; ++k) {
                sb.append("   ");
            }
        }
        sb.append("[");
        sb.append(sb2.toString());
        sb.append("]\n");
        return sb.toString();
    }
    
    public static String a(final int n, final int n2) {
        long n3 = n;
        if (n < 0) {
            n3 = n3 + 255L + 1L;
        }
        final char[] value = new char[n2 * 2];
        for (int i = n2 - 1; i >= 0; --i) {
            int n4 = (byte)(n3 >> 4 & 0xFL);
            if (n4 < 0) {
                n4 += 16;
            }
            final byte b = (byte)(n3 & 0xFL);
            if (n4 < 10) {
                value[i * 2] = (char)(48 + n4);
            }
            else {
                value[i * 2] = (char)(65 + n4 - 10);
            }
            if (b < 10) {
                value[i * 2 + 1] = (char)(48 + b);
            }
            else {
                value[i * 2 + 1] = (char)(65 + b - 10);
            }
            n3 >>= 8;
        }
        return new String(value);
    }
    
    public static String a(final byte[] array) {
        return b(array, 0, array.length);
    }
    
    public static String b(final byte[] array, final int n, final int n2) {
        final StringBuffer sb = new StringBuffer();
        for (int i = n; i < n + n2; ++i) {
            sb.append(a(array[i], 1));
            sb.append(" ");
            if ((i & 0xF) == 0xF) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    public static boolean a(final byte[] array, final byte[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
}
