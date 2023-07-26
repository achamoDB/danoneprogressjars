// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.guiproperties.XPropertyException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.property.EPropertiesChanged;
import com.progress.chimera.common.Tools;
import com.progress.chimera.log.ConnectionManagerLog;
import java.rmi.server.RemoteStub;
import com.progress.common.guiproperties.GUIMetaSchema;
import com.progress.message.jpMsg;

public class JATools implements jpMsg
{
    static Boolean diaSync;
    static boolean isServer;
    static String propertySchemaFile;
    static GUIMetaSchema propertySchema;
    
    public static void writeOutProperties(final IJAPlugIn ijaPlugIn) {
        writeOutProperties(ijaPlugIn, null);
    }
    
    public static void writeOutProperties(final IJAPlugIn ijaPlugIn, final RemoteStub obj) {
        final JAPlugIn jaPlugIn = (JAPlugIn)ijaPlugIn;
        ConnectionManagerLog.get().log(3, 7669630165411962094L, new Object[] { jaPlugIn.configFile() });
        try {
            jaPlugIn.properties().save(jaPlugIn.configFile(), "Juniper Properties File");
        }
        catch (Throwable t) {
            Tools.px(t);
        }
        try {
            if (obj != null) {
                System.out.println("Posting EPropertiesChanged event for " + obj);
                ijaPlugIn.getEventBroker().postEvent(new EPropertiesChanged(obj, null));
            }
        }
        catch (RemoteException ex) {
            Tools.px(ex, "Error posting EPropertiesChanged event");
        }
    }
    
    public static void setIsServer() {
        JATools.isServer = true;
    }
    
    public static boolean isServer() {
        return JATools.isServer;
    }
    
    public static void addToArrayProp(final JAPlugIn jaPlugIn, final String anotherString, final String str) {
        String[] arrayProperty;
        try {
            arrayProperty = jaPlugIn.properties().getArrayProperty(str);
            if (arrayProperty != null) {
                for (int i = 0; i < arrayProperty.length; ++i) {
                    if (arrayProperty[i].trim().equalsIgnoreCase(anotherString)) {
                        return;
                    }
                }
            }
        }
        catch (Throwable t2) {
            return;
        }
        int length;
        String[] array;
        if (arrayProperty != null) {
            if (arrayProperty.length == 1 && arrayProperty[0].equals("")) {
                length = 0;
                array = new String[] { null };
            }
            else {
                length = arrayProperty.length;
                array = new String[length + 1];
            }
        }
        else {
            length = 0;
            array = new String[] { null };
        }
        int j;
        for (j = 0; j < length; ++j) {
            array[j] = arrayProperty[j];
        }
        array[j] = anotherString;
        try {
            jaPlugIn.properties().putArrayProperty(str, array);
        }
        catch (Throwable t) {
            Tools.px(t, "JATools::addToArrayProp: Failed to write array property: " + str);
        }
    }
    
    public static void removeFromArrayProp(final JAPlugIn jaPlugIn, final String anotherString, final String s) {
        int n = -1;
        String[] arrayProperty;
        try {
            try {
                arrayProperty = jaPlugIn.properties().getArrayProperty(s);
            }
            catch (Throwable t3) {
                arrayProperty = null;
            }
            if (arrayProperty == null) {
                return;
            }
            for (int i = 0; i < arrayProperty.length; ++i) {
                final String trim = arrayProperty[i].trim();
                if (trim.length() != 0) {
                    if (trim != null) {
                        if (trim.equalsIgnoreCase(anotherString)) {
                            n = i;
                            break;
                        }
                    }
                }
            }
            if (n == -1) {
                return;
            }
        }
        catch (Throwable t4) {
            return;
        }
        if (arrayProperty.length == 0) {
            return;
        }
        if (arrayProperty.length == 1) {
            try {
                jaPlugIn.properties().putArrayProperty(s, null);
            }
            catch (Throwable t) {
                Tools.px(t, "JATools::removeFromArrayProp: Failed to write array property: " + s);
            }
        }
        else {
            final String[] array = new String[arrayProperty.length - 1];
            int j = 0;
            int n2 = 0;
            while (j < arrayProperty.length) {
                if (j != n) {
                    array[n2] = arrayProperty[j];
                    ++n2;
                }
                ++j;
            }
            try {
                jaPlugIn.properties().putArrayProperty(s, array);
            }
            catch (Throwable t2) {
                Tools.px(t2, "JATools::removeFromArrayProp: Failed to write array property: " + s);
            }
        }
    }
    
    static ThreadGroup rootThreadGroup() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        for (ThreadGroup threadGroup2 = threadGroup.getParent(); threadGroup2 != null; threadGroup2 = threadGroup.getParent()) {
            threadGroup = threadGroup2;
        }
        return threadGroup;
    }
    
    static void dumpThreads(final PrintStream printStream) {
        final ThreadGroup rootThreadGroup = rootThreadGroup();
        final ThreadGroup[] list = new ThreadGroup[rootThreadGroup.activeGroupCount()];
        rootThreadGroup.enumerate(list);
        final String str = "   ";
        final String str2 = "ThreadGroup";
        final String str3 = "___________";
        final int length = str2.length();
        final String str4 = "Thread";
        final String str5 = "______";
        str4.length();
        int n = str2.length();
        for (int i = 0; i < list.length; ++i) {
            final String name = list[i].getName();
            if (n < name.length()) {
                n = name.length();
            }
        }
        final int n2 = 3;
        final StringBuffer buffer = new StringBuffer(n + n2);
        for (int j = 0; j < n + n2; ++j) {
            buffer.insert(j, ' ');
        }
        final String s = new String(buffer);
        final Thread[] list2 = new Thread[rootThreadGroup.activeCount()];
        rootThreadGroup.enumerate(list2);
        printStream.println("");
        printStream.println("List of active threads...");
        printStream.println("");
        printStream.println(str + str2 + s.substring(length) + str4);
        printStream.println(str + str3 + s.substring(length) + str5);
        printStream.println("");
        Object anObject = null;
        for (int k = 0; k < list2.length; ++k) {
            final Thread thread = list2[k];
            final String name2 = thread.getThreadGroup().getName();
            if (!name2.equals(anObject)) {
                printStream.println("");
                anObject = name2;
            }
            final int length2 = name2.length();
            final String name3 = thread.getName();
            name3.length();
            final String name4 = thread.getClass().getName();
            name4.length();
            s.substring(length2);
            printStream.println(str + name2 + s.substring(length2) + name3 + " (" + name4 + ")");
        }
        printStream.println("");
    }
    
    static String getPropertySchemaFile() throws XJAException {
        if (JATools.propertySchemaFile == null) {
            JATools.propertySchemaFile = System.getProperty("Juniper.ConfigSchemaFile");
        }
        if (JATools.propertySchemaFile == null) {
            throw new XJAException("Property schema file not specified.");
        }
        return JATools.propertySchemaFile;
    }
    
    static GUIMetaSchema getPropertySchema() throws XJAException {
        try {
            if (JATools.propertySchema == null) {
                JATools.propertySchema = GUIMetaSchema.instantiate(getPropertySchemaFile());
            }
        }
        catch (XPropertyException previous) {
            final XJAException ex = new XJAException("Error loading propertySchema");
            ex.setPrevious(previous);
            throw ex;
        }
        return JATools.propertySchema;
    }
    
    public static RemoteStub evObj(final IJARemoteObject ijaRemoteObject) throws RemoteException {
        RemoteStub remoteStub;
        if (ijaRemoteObject instanceof RemoteStub) {
            remoteStub = (RemoteStub)ijaRemoteObject;
        }
        else {
            remoteStub = ijaRemoteObject.remoteStub();
        }
        return remoteStub;
    }
    
    static {
        JATools.diaSync = null;
        JATools.isServer = false;
        JATools.propertySchemaFile = null;
        JATools.propertySchema = null;
    }
}
