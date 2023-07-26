// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.lang.reflect.InvocationTargetException;

public class OSSpecific implements IAdminServerConst
{
    public static AdminServer m_adminserver;
    
    public static AdminServer invokeAdminServer(final String className, final String[] array) {
        try {
            if (OSSpecific.m_adminserver == null) {
                OSSpecific.m_adminserver = (AdminServer)Class.forName(className).getConstructor(array.getClass()).newInstance(array);
            }
        }
        catch (NoSuchMethodException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException ex2) {
            System.err.println(ex2.getMessage());
            ex2.printStackTrace();
            System.exit(1);
        }
        catch (InstantiationException ex3) {
            System.err.println(ex3.getMessage());
            ex3.printStackTrace();
            System.exit(1);
        }
        catch (IllegalAccessException ex4) {
            System.err.println(ex4.getMessage());
            ex4.printStackTrace();
            System.exit(1);
        }
        catch (InvocationTargetException ex5) {
            System.err.println(ex5.getMessage());
            ex5.printStackTrace();
            System.exit(1);
        }
        return OSSpecific.m_adminserver;
    }
    
    static {
        OSSpecific.m_adminserver = null;
    }
}
