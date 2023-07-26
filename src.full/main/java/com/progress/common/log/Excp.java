// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import com.progress.common.exception.ProException;
import com.progress.chimera.log.ExceptionLog;
import com.progress.common.util.PromsgsFile;
import java.util.Date;
import com.progress.chimera.util.Const;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Excp
{
    static Object excpFileLock;
    static PrintWriter excpFile;
    static String excpFileName;
    static int excpCounter;
    
    public static int getExcpCounter() {
        return Excp.excpCounter;
    }
    
    public static String getExcpFileName() {
        return Excp.excpFileName;
    }
    
    public static void setExcpFileName(final String original) {
        Excp.excpFileName = new String(original);
    }
    
    public static int print(final Throwable t) {
        return print(t, null);
    }
    
    public static void beep() {
    }
    
    public static int print(final Throwable t, final String str) {
        beep();
        synchronized (Excp.excpFileLock) {
            if (Excp.excpFile == null) {
                OutputStream out = null;
                try {
                    if (Excp.excpFileName == null) {
                        Excp.excpFileName = new String("exceptions.log");
                    }
                    out = new BufferedOutputStream(new FileOutputStream(Excp.excpFileName, true), 1000);
                }
                catch (Throwable t2) {
                    try {
                        System.out.println(((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle")).getTranString("Exception log is inaccessible"));
                        t2.printStackTrace();
                    }
                    catch (Throwable t3) {}
                    System.exit(1);
                }
                (Excp.excpFile = new PrintWriter(out, true)).println(Const.NEWLINE + Const.NEWLINE + "====== Start exception logging == \"" + Excp.excpFileName + "\" opened == " + new Date() + " ======" + Const.NEWLINE);
                setExcpCounter();
            }
            ++Excp.excpCounter;
            Excp.excpFile.println(Const.NEWLINE + "**** " + Excp.excpCounter + " ****" + Const.NEWLINE);
            Excp.excpFile.println("   Exception at " + new Date() + ": " + t.getClass().getName());
            if (str != null) {
                Excp.excpFile.println("   Message (throw): " + str);
            }
            printGuts(t);
            Excp.excpFile.flush();
            if (!(t instanceof PromsgsFile.PromsgsFileIOException)) {
                ExceptionLog.get().logErr("recorded as exception #{0} in file {1}.", array(new Integer(Excp.excpCounter), Excp.excpFileName));
            }
        }
        return Excp.excpCounter;
    }
    
    private static void printGuts(final Throwable t) {
        Excp.excpFile.println("   Message (excp):  " + t.getMessage());
        Excp.excpFile.println("   Stack Trace:");
        t.printStackTrace(Excp.excpFile);
        final Throwable previous;
        if (t instanceof ProException && (previous = ((ProException)t).getPrevious()) != null) {
            Excp.excpFile.println("");
            Excp.excpFile.println("   Nested exception is:");
            printGuts(previous);
        }
    }
    
    private static void setExcpCounter() {
        if (Excp.excpFile == null) {
            Excp.excpCounter = 0;
        }
        else {
            try {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(Excp.excpFileName));
                for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                    if (s.startsWith("**** ")) {
                        final int n = s.indexOf(32) + 1;
                        Excp.excpCounter = Integer.valueOf(s.substring(n, s.indexOf(32, n)));
                    }
                }
            }
            catch (Exception ex) {
                print(ex);
            }
        }
    }
    
    public static int print(final String s, final Throwable t) {
        return print(t, s);
    }
    
    public static void print(final String str) {
        if (str == null) {
            return;
        }
        synchronized (Excp.excpFileLock) {
            if (Excp.excpFile == null) {
                OutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream("exceptions.log", true), 1000);
                }
                catch (Throwable t) {
                    print(t);
                }
                Excp.excpFile = new PrintWriter(out, true);
            }
            Excp.excpFile.println(Const.NEWLINE + "**************************");
            Excp.excpFile.println("   Message at " + new Date() + ":");
            Excp.excpFile.println("   : " + str);
            Excp.excpFile.flush();
        }
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
    
    static {
        Excp.excpFileLock = new Object();
        Excp.excpFile = null;
        Excp.excpFileName = null;
        Excp.excpCounter = 0;
    }
}
