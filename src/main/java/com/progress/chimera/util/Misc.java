// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.util;

import java.rmi.RemoteException;
import com.progress.chimera.common.Tools;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.chimera.common.IChimeraHierarchy;

public class Misc
{
    public static String BLANKS;
    
    public static String fill(final String s, final int endIndex, final boolean b, final boolean b2, final String s2) {
        String s3;
        if (s.length() >= endIndex) {
            if (b2) {
                s3 = s.substring(0, endIndex);
            }
            else {
                s3 = s;
            }
        }
        else {
            final String fill = makeFill(endIndex, s2);
            if (b) {
                s3 = fill.substring(0, endIndex - s.length()) + s;
            }
            else {
                s3 = s + fill.substring(0, endIndex - s.length());
            }
        }
        return s3;
    }
    
    public static String fill(final String s, final int n, final boolean b, final boolean b2) {
        return fill(s, n, b, b2, Misc.BLANKS);
    }
    
    public static String fill(final String s, final int n, final boolean b) {
        return fill(s, n, b, true, Misc.BLANKS);
    }
    
    public static String fill(final String s, final int n, final String s2) {
        return fill(s, n, false, true, s2);
    }
    
    public static String makeFill(final int n, final String str) {
        String string = str;
        if (n > string.length()) {
            final StringBuffer sb = new StringBuffer(str);
            final char char1 = str.charAt(str.length() - 1);
            for (int i = sb.length(); i <= n; ++i) {
                sb.append(char1);
            }
            string = sb.toString();
        }
        return string;
    }
    
    public static void setStringBuffer(final StringBuffer sb, final String str) {
        sb.setLength(0);
        sb.append(str);
    }
    
    public static String boxedString(final String str) {
        final StringBuffer sb = new StringBuffer();
        final String s = new String(fill("", str.length() + 6, true, true, "=====") + Const.NEWLINE);
        sb.append(s);
        sb.append("== " + str + " ==" + Const.NEWLINE);
        sb.append(s);
        return sb.toString();
    }
    
    public static String boxedString(final String[] array) {
        final StringBuffer sb = new StringBuffer();
        int length = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].length() > length) {
                length = array[i].length();
            }
        }
        final String s = new String(fill("", length + 6, true, true, "=====") + Const.NEWLINE);
        sb.append(s);
        for (int j = 0; j < array.length; ++j) {
            sb.append("== " + fill(array[j], length, false, false) + " ==" + Const.NEWLINE);
        }
        sb.append(s);
        return sb.toString();
    }
    
    public static boolean RMIExportChimera(final IChimeraHierarchy obj) {
        boolean b = false;
        try {
            UnicastRemoteObject.exportObject(obj);
            b = true;
        }
        catch (RemoteException ex) {
            final String x = "### Failed to export IChimeraHierarchy object. ###";
            System.err.println(x);
            System.err.println(ex.getMessage());
            Tools.px(x, ex);
        }
        return b;
    }
    
    public static boolean isBooleanString(final String s) {
        final String lowerCase = s.toLowerCase();
        return lowerCase.equals("on") || lowerCase.equals("true") || lowerCase.equals("off") || lowerCase.equals("false");
    }
    
    public static boolean stringToBoolean(final String s) {
        if (!isBooleanString(s)) {
            return false;
        }
        final String lowerCase = s.toLowerCase();
        return lowerCase.equals("on") || (!lowerCase.equals("off") && new Boolean(lowerCase));
    }
    
    static {
        Misc.BLANKS = "                                                         ";
    }
}
