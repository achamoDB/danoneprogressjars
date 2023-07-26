// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.io.File;
import java.util.StringTokenizer;
import java.net.URL;
import java.util.Vector;
import java.util.HashSet;

public class AdminServerStarter implements IAdminServerConst
{
    public AdminServerStarter(final String[] array, final HashSet set) {
        try {
            final Vector vector = new Vector<URL>();
            final String property = System.getProperty("java.class.path");
            if (property != null) {
                final StringTokenizer stringTokenizer = new StringTokenizer(property, IAdminServerConst.PATH_SEPARATOR);
                while (stringTokenizer.hasMoreTokens()) {
                    final File file = new File(stringTokenizer.nextToken());
                    if (IAdminServerConst.IS_NT) {
                        vector.addElement(file.toURI().toURL());
                    }
                    else {
                        vector.addElement(file.toURL());
                    }
                }
            }
            final URL[] array2 = new URL[vector.size()];
            for (int i = 0; i < vector.size(); ++i) {
                array2[i] = vector.get(i);
            }
            ClassLoader classLoader = null;
            if (Thread.currentThread().getContextClassLoader().getParent().getClass().getName().startsWith("com.sonicsw")) {
                classLoader = AdminServerStarter.class.getClassLoader();
            }
            final AdminLoader loader = new AdminLoader(array2, classLoader);
            final Class[] parameterTypes = { array.getClass() };
            final Object[] args = { array };
            final Class<?> forName = Class.forName(IAdminServerConst.ADMIN_SERVER_CLASSNAME, true, loader);
            forName.getMethod("main", (Class[])parameterTypes).invoke(forName, args);
        }
        catch (Exception ex) {
            System.err.println("AdminServer failed to start:");
            if (ex.getMessage() != null) {
                System.err.println(ex.getMessage());
            }
            System.exit(1);
        }
    }
    
    public static void main(final String[] array) {
        new AdminServerStarter(array, null);
    }
}
