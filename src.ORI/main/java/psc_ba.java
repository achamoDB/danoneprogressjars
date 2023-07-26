import java.util.StringTokenizer;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ba
{
    static String[] a(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        final int countTokens = stringTokenizer.countTokens();
        final String[] array = new String[countTokens];
        for (int i = 0; i < countTokens; ++i) {
            array[i] = stringTokenizer.nextToken();
        }
        return array;
    }
    
    public static int[] b(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "-");
        final int[] array = new int[stringTokenizer.countTokens() - 1];
        int i = 0;
        stringTokenizer.nextToken();
        while (i < array.length) {
            int radix = 10;
            String s = stringTokenizer.nextToken();
            if (s.startsWith("0x") || s.startsWith("0X")) {
                radix = 16;
                s = s.substring(2, s.length());
            }
            array[i] = Integer.parseInt(s, radix);
            ++i;
        }
        return array;
    }
}
