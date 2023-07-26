// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import com.progress.common.log.Excp;

public class Tools
{
    public static boolean isaSubclass(final String className, final String className2) {
        Class<?> forName;
        try {
            forName = Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
        Class<?> forName2;
        try {
            forName2 = Class.forName(className2);
        }
        catch (ClassNotFoundException ex2) {
            return false;
        }
        return isaSubclass(forName, forName2);
    }
    
    public static boolean isaSubclass(final Class clazz, final Class clazz2) {
        if (clazz == clazz2) {
            return true;
        }
        final Class superclass = clazz.getSuperclass();
        return superclass != null && isaSubclass(superclass, clazz2);
    }
    
    public static boolean isaSubtype(final Class clazz, final Class clazz2) {
        clazz.getName();
        clazz2.getName();
        if (clazz == clazz2) {
            return true;
        }
        final Class superclass = clazz.getSuperclass();
        if (superclass != null && isaSubtype(superclass, clazz2)) {
            return true;
        }
        final Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            if (isaSubtype(interfaces[i], clazz2)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isaInstance(final Object o, final Class clazz) {
        return isaSubtype(o.getClass(), clazz);
    }
    
    public static boolean isaInstanceProper(final Object o, final Class clazz) {
        return isaSubclass(o.getClass(), clazz);
    }
    
    public static Class lookupClass(final String s) {
        Class<?> forName = null;
        try {
            forName = Class.forName(s);
        }
        catch (Throwable t) {
            System.out.println("*** Class not found: " + s);
        }
        return forName;
    }
    
    public static Class lookupChimeraClass(final String str) {
        return lookupClass("com.progress.chimera." + str);
    }
    
    public static Class lookupCommonClass(final String str) {
        return lookupChimeraClass("common." + str);
    }
    
    public static Class lookupChipClass(final String str) {
        return lookupCommonClass("chip." + str);
    }
    
    public static String getExcpFileName() {
        return Excp.getExcpFileName();
    }
    
    public static int getExcpCounter() {
        return Excp.getExcpCounter();
    }
    
    public static void setExcpFileName(final String excpFileName) {
        Excp.setExcpFileName(excpFileName);
    }
    
    public static int px(final Throwable t) {
        return Excp.print(t, null);
    }
    
    public static int px(final Throwable t, final String s) {
        return Excp.print(t, s);
    }
    
    public static int px(final String s, final Throwable t) {
        return Excp.print(t, s);
    }
    
    public static void ps(final String s) {
        Excp.print(s);
    }
    
    public static Object[] array(final Object o) {
        return new Object[] { o };
    }
    
    public static Object[] array(final Object o, final Object o2) {
        return new Object[] { o, o2 };
    }
    
    public static Object[] array(final Object o, final Object o2, final Object o3) {
        return new Object[] { o, o2, o3 };
    }
    
    public static Object[] array(final Object o, final Object o2, final Object o3, final Object o4) {
        return new Object[] { o, o2, o3, o4 };
    }
}
