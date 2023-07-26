// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.io.PrintStream;

public class Format
{
    private int width;
    private int precision;
    private String pre;
    private String post;
    private boolean leading_zeroes;
    private boolean show_plus;
    private boolean alternate;
    private boolean show_space;
    private boolean left_align;
    private char fmt;
    
    public Format(final String s) {
        this.width = 0;
        this.precision = -1;
        this.pre = "";
        this.post = "";
        this.leading_zeroes = false;
        this.show_plus = false;
        this.alternate = false;
        this.show_space = false;
        this.left_align = false;
        this.fmt = ' ';
        final int length = s.length();
        int i = 0;
        int n = 0;
        while (i == 0) {
            if (n >= length) {
                i = 5;
            }
            else if (s.charAt(n) == '%') {
                if (n >= length - 1) {
                    throw new IllegalArgumentException();
                }
                if (s.charAt(n + 1) == '%') {
                    this.pre += '%';
                    ++n;
                }
                else {
                    i = 1;
                }
            }
            else {
                this.pre += s.charAt(n);
            }
            ++n;
        }
        while (i == 1) {
            if (n >= length) {
                i = 5;
            }
            else if (s.charAt(n) == ' ') {
                this.show_space = true;
            }
            else if (s.charAt(n) == '-') {
                this.left_align = true;
            }
            else if (s.charAt(n) == '+') {
                this.show_plus = true;
            }
            else if (s.charAt(n) == '0') {
                this.leading_zeroes = true;
            }
            else if (s.charAt(n) == '#') {
                this.alternate = true;
            }
            else {
                i = 2;
                --n;
            }
            ++n;
        }
        while (i == 2) {
            if (n >= length) {
                i = 5;
            }
            else if ('0' <= s.charAt(n) && s.charAt(n) <= '9') {
                this.width = this.width * 10 + s.charAt(n) - 48;
                ++n;
            }
            else if (s.charAt(n) == '.') {
                i = 3;
                this.precision = 0;
                ++n;
            }
            else {
                i = 4;
            }
        }
        while (i == 3) {
            if (n >= length) {
                i = 5;
            }
            else if ('0' <= s.charAt(n) && s.charAt(n) <= '9') {
                this.precision = this.precision * 10 + s.charAt(n) - 48;
                ++n;
            }
            else {
                i = 4;
            }
        }
        if (i == 4) {
            if (n < length) {
                this.fmt = s.charAt(n);
            }
            ++n;
        }
        if (n < length) {
            this.post = s.substring(n, length);
        }
    }
    
    public static void print(final PrintStream printStream, final String s, final double n) {
        printStream.print(new Format(s).form(n));
    }
    
    public static void print(final PrintStream printStream, final String s, final long n) {
        printStream.print(new Format(s).form(n));
    }
    
    public static void print(final PrintStream printStream, final String s, final char c) {
        printStream.print(new Format(s).form(c));
    }
    
    public static void print(final PrintStream printStream, final String s, final String s2) {
        printStream.print(new Format(s).form(s2));
    }
    
    public static int atoi(final String s) {
        return (int)atol(s);
    }
    
    public static long atol(final String s) {
        int n;
        for (n = 0; n < s.length() && Character.isWhitespace(s.charAt(n)); ++n) {}
        if (n >= s.length() || s.charAt(n) != '0') {
            return parseLong(s, 10);
        }
        if (n + 1 < s.length() && (s.charAt(n + 1) == 'x' || s.charAt(n + 1) == 'X')) {
            return parseLong(s.substring(n + 2), 16);
        }
        return parseLong(s, 8);
    }
    
    private static long parseLong(final String s, final int n) {
        int i = 0;
        int n2 = 1;
        long n3 = 0L;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            ++i;
        }
        if (i < s.length() && s.charAt(i) == '-') {
            n2 = -1;
            ++i;
        }
        else if (i < s.length() && s.charAt(i) == '+') {
            ++i;
        }
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if ('0' <= char1 && char1 < 48 + n) {
                n3 = n3 * n + char1 - 48L;
            }
            else if ('A' <= char1 && char1 < 65 + n - 10) {
                n3 = n3 * n + char1 - 65L + 10L;
            }
            else {
                if ('a' > char1 || char1 >= 97 + n - 10) {
                    return n3 * n2;
                }
                n3 = n3 * n + char1 - 97L + 10L;
            }
            ++i;
        }
        return n3 * n2;
    }
    
    public static double atof(final String s) {
        int i = 0;
        int n = 1;
        double n2 = 0.0;
        double n3 = 1.0;
        int n4 = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            ++i;
        }
        if (i < s.length() && s.charAt(i) == '-') {
            n = -1;
            ++i;
        }
        else if (i < s.length() && s.charAt(i) == '+') {
            ++i;
        }
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if ('0' <= char1 && char1 <= '9') {
                if (n4 == 0) {
                    n2 = n2 * 10.0 + char1 - 48.0;
                }
                else if (n4 == 1) {
                    n3 /= 10.0;
                    n2 += n3 * (char1 - '0');
                }
            }
            else if (char1 == '.') {
                if (n4 != 0) {
                    return n * n2;
                }
                n4 = 1;
            }
            else {
                if (char1 == 'e' || char1 == 'E') {
                    return n * n2 * Math.pow(10.0, (int)parseLong(s.substring(i + 1), 10));
                }
                return n * n2;
            }
            ++i;
        }
        return n * n2;
    }
    
    public String form(double n) {
        if (this.precision < 0) {
            this.precision = 6;
        }
        int n2 = 1;
        if (n < 0.0) {
            n = -n;
            n2 = -1;
        }
        String s;
        if (this.fmt == 'f') {
            s = this.fixed_format(n);
        }
        else {
            if (this.fmt != 'e' && this.fmt != 'E' && this.fmt != 'g' && this.fmt != 'G') {
                throw new IllegalArgumentException();
            }
            s = this.exp_format(n);
        }
        return this.pad(this.sign(n2, s));
    }
    
    public String form(long lng) {
        int n = 0;
        String s;
        if (this.fmt == 'd' || this.fmt == 'i') {
            n = 1;
            if (lng < 0L) {
                lng = -lng;
                n = -1;
            }
            s = "" + lng;
        }
        else if (this.fmt == 'o') {
            s = convert(lng, 3, 7, "01234567");
        }
        else if (this.fmt == 'x') {
            s = convert(lng, 4, 15, "0123456789abcdef");
        }
        else {
            if (this.fmt != 'X') {
                throw new IllegalArgumentException();
            }
            s = convert(lng, 4, 15, "0123456789ABCDEF");
        }
        return this.pad(this.sign(n, s));
    }
    
    public String form(final char c) {
        if (this.fmt != 'c') {
            throw new IllegalArgumentException();
        }
        return this.pad("" + c);
    }
    
    public String form(String substring) {
        if (this.fmt != 's') {
            throw new IllegalArgumentException();
        }
        if (this.precision >= 0) {
            substring = substring.substring(0, this.precision);
        }
        return this.pad(substring);
    }
    
    public static void main(final String[] array) {
        final double n = 1.23456789012;
        final double n2 = 123.0;
        final double n3 = 1.2345E30;
        final double n4 = 1.02;
        final double n5 = 1.234E-5;
        final int n6 = 51966;
        print(System.out, "x = |%f|\n", n);
        print(System.out, "u = |%20f|\n", n5);
        print(System.out, "x = |% .5f|\n", n);
        print(System.out, "w = |%20.5f|\n", n4);
        print(System.out, "x = |%020.5f|\n", n);
        print(System.out, "x = |%+20.5f|\n", n);
        print(System.out, "x = |%+020.5f|\n", n);
        print(System.out, "x = |% 020.5f|\n", n);
        print(System.out, "y = |%#+20.5f|\n", n2);
        print(System.out, "y = |%-+20.5f|\n", n2);
        print(System.out, "z = |%20.5f|\n", n3);
        print(System.out, "x = |%e|\n", n);
        print(System.out, "u = |%20e|\n", n5);
        print(System.out, "x = |% .5e|\n", n);
        print(System.out, "w = |%20.5e|\n", n4);
        print(System.out, "x = |%020.5e|\n", n);
        print(System.out, "x = |%+20.5e|\n", n);
        print(System.out, "x = |%+020.5e|\n", n);
        print(System.out, "x = |% 020.5e|\n", n);
        print(System.out, "y = |%#+20.5e|\n", n2);
        print(System.out, "y = |%-+20.5e|\n", n2);
        print(System.out, "x = |%g|\n", n);
        print(System.out, "z = |%g|\n", n3);
        print(System.out, "w = |%g|\n", n4);
        print(System.out, "u = |%g|\n", n5);
        print(System.out, "y = |%.2g|\n", n2);
        print(System.out, "y = |%#.2g|\n", n2);
        print(System.out, "d = |%d|\n", n6);
        print(System.out, "d = |%20d|\n", n6);
        print(System.out, "d = |%020d|\n", n6);
        print(System.out, "d = |%+20d|\n", n6);
        print(System.out, "d = |% 020d|\n", n6);
        print(System.out, "d = |%-20d|\n", n6);
        print(System.out, "d = |%20.8d|\n", n6);
        print(System.out, "d = |%x|\n", n6);
        print(System.out, "d = |%20X|\n", n6);
        print(System.out, "d = |%#20x|\n", n6);
        print(System.out, "d = |%020X|\n", n6);
        print(System.out, "d = |%20.8x|\n", n6);
        print(System.out, "d = |%o|\n", n6);
        print(System.out, "d = |%020o|\n", n6);
        print(System.out, "d = |%#20o|\n", n6);
        print(System.out, "d = |%#020o|\n", n6);
        print(System.out, "d = |%20.12o|\n", n6);
        print(System.out, "s = |%-20s|\n", "Hello");
        print(System.out, "s = |%-20c|\n", '!');
    }
    
    private static String repeat(final char c, final int capacity) {
        if (capacity <= 0) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(capacity);
        for (int i = 0; i < capacity; ++i) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    private static String convert(long n, final int n2, final int n3, final String s) {
        if (n == 0L) {
            return "0";
        }
        String string = "";
        while (n != 0L) {
            string = s.charAt((int)(n & (long)n3)) + string;
            n >>>= n2;
        }
        return string;
    }
    
    private String pad(final String s) {
        final String repeat = repeat(' ', this.width - s.length());
        if (this.left_align) {
            return this.pre + s + repeat + this.post;
        }
        return this.pre + repeat + s + this.post;
    }
    
    private String sign(final int n, final String str) {
        String str2 = "";
        if (n < 0) {
            str2 = "-";
        }
        else if (n > 0) {
            if (this.show_plus) {
                str2 = "+";
            }
            else if (this.show_space) {
                str2 = " ";
            }
        }
        else if (this.fmt == 'o' && this.alternate && str.length() > 0 && str.charAt(0) != '0') {
            str2 = "0";
        }
        else if (this.fmt == 'x' && this.alternate) {
            str2 = "0x";
        }
        else if (this.fmt == 'X' && this.alternate) {
            str2 = "0X";
        }
        int n2 = 0;
        if (this.leading_zeroes) {
            n2 = this.width;
        }
        else if ((this.fmt == 'd' || this.fmt == 'i' || this.fmt == 'x' || this.fmt == 'X' || this.fmt == 'o') && this.precision > 0) {
            n2 = this.precision;
        }
        return str2 + repeat('0', n2 - str2.length() - str.length()) + str;
    }
    
    private String fixed_format(final double n) {
        final String str = "";
        if (n > 9.223372036854776E18) {
            return this.exp_format(n);
        }
        final long lng = (long)((this.precision == 0) ? (n + 0.5) : n);
        final String string = str + lng;
        final double n2 = n - lng;
        if (n2 >= 1.0 || n2 < 0.0) {
            return this.exp_format(n);
        }
        return string + this.frac_part(n2);
    }
    
    private String frac_part(final double n) {
        String str = "";
        if (this.precision > 0) {
            double n2 = 1.0;
            String string = "";
            for (int n3 = 1; n3 <= this.precision && n2 <= 9.223372036854776E18; n2 *= 10.0, string += "0", ++n3) {}
            final String string2 = string + (long)(n2 * n + 0.5);
            str = string2.substring(string2.length() - this.precision, string2.length());
        }
        if (this.precision > 0 || this.alternate) {
            str = "." + str;
        }
        if ((this.fmt == 'G' || this.fmt == 'g') && !this.alternate) {
            int n4;
            for (n4 = str.length() - 1; n4 >= 0 && str.charAt(n4) == '0'; --n4) {}
            if (n4 >= 0 && str.charAt(n4) == '.') {
                --n4;
            }
            str = str.substring(0, n4 + 1);
        }
        return str;
    }
    
    private String exp_format(double n) {
        final String str = "";
        int i = 0;
        double n2 = n;
        double n3 = 1.0;
        while (n2 > 10.0) {
            ++i;
            n3 /= 10.0;
            n2 /= 10.0;
        }
        while (n2 < 1.0) {
            --i;
            n3 *= 10.0;
            n2 *= 10.0;
        }
        if ((this.fmt == 'g' || this.fmt == 'G') && i >= -4 && i < this.precision) {
            return this.fixed_format(n);
        }
        n *= n3;
        final String string = str + this.fixed_format(n);
        String s;
        if (this.fmt == 'e' || this.fmt == 'g') {
            s = string + "e";
        }
        else {
            s = string + "E";
        }
        final String s2 = "000";
        String str2;
        String s3;
        if (i >= 0) {
            str2 = s + "+";
            s3 = s2 + i;
        }
        else {
            str2 = s + "-";
            s3 = s2 + -i;
        }
        return str2 + s3.substring(s3.length() - 3, s3.length());
    }
}
