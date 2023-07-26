// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

public class Typing
{
    private static void listMembersI(final Class clazz) {
        System.out.println("  Members from " + clazz.toString() + ":");
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        final int length = declaredConstructors.length;
        if (length != 0) {
            System.out.println("    Constructors:");
            for (int i = 0; i < length; ++i) {
                System.out.println("      " + declaredConstructors[i].toString());
            }
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        final int length2 = declaredFields.length;
        if (length2 != 0) {
            System.out.println("    Fields:");
            for (int j = 0; j < length2; ++j) {
                System.out.println("      " + declaredFields[j].toString());
            }
        }
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        final int length3 = declaredMethods.length;
        if (length3 != 0) {
            System.out.println("    Methods:");
            for (int k = 0; k < length3; ++k) {
                System.out.println("      " + declaredMethods[k].toString());
            }
        }
        final Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            listMembersI(superclass);
        }
    }
    
    public static void listMembers(final Class clazz) {
        System.out.println("Members for " + clazz.toString());
        listMembersI(clazz);
        System.out.println();
    }
    
    public static boolean subclass(final String className, final String className2) {
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
        return subclass(forName, forName2);
    }
    
    public static boolean subclass(final Class clazz, final Class clazz2) {
        if (clazz == clazz2) {
            return true;
        }
        final Class superclass = clazz.getSuperclass();
        return superclass != null && subclass(superclass, clazz2);
    }
    
    public static boolean subtype(final Class clazz, final Class clazz2) {
        clazz.getName();
        clazz2.getName();
        if (clazz == clazz2) {
            return true;
        }
        final Class superclass = clazz.getSuperclass();
        if (superclass != null && subtype(superclass, clazz2)) {
            return true;
        }
        final Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            if (subtype(interfaces[i], clazz2)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean instance(final Object o, final Class clazz) {
        return subtype(o.getClass(), clazz);
    }
    
    public static boolean instanceProper(final Object o, final Class clazz) {
        return subclass(o.getClass(), clazz);
    }
}
