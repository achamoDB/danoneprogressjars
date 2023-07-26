// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class LogContextFactory
{
    private static final String m_packageName = "com.progress.common.ehnlog";
    private static final String m_dfltLogContextName = "Generic";
    
    public static LogContext createLogContext(final String str) throws LogException {
        final String string = "com.progress.common.ehnlog." + str + "LogContext";
        try {
            return (LogContext)Class.forName(string).newInstance();
        }
        catch (ClassNotFoundException ex) {
            if (str.equals("Generic")) {
                throw new LogException("Generic class not found");
            }
            System.err.println(string + " class not found: " + ex.toString());
            createLogContext("Generic");
            return null;
        }
        catch (InstantiationException ex2) {
            if (str.equals("Generic")) {
                throw new LogException("Genericclass could not be instantiated");
            }
            System.err.println(string + " class could not be instantiated: " + ex2.toString());
            createLogContext("Generic");
            return null;
        }
        catch (IllegalAccessException ex3) {
            if (str.equals("Generic")) {
                throw new LogException("Genericclass could not be accessed");
            }
            System.err.println(string + " class could not be accessed: " + ex3.toString());
            createLogContext("Generic");
            return null;
        }
    }
}
