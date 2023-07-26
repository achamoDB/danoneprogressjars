// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Enumeration;
import java.util.List;
import java.util.Collections;
import java.util.Vector;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.net.InetAddress;
import com.progress.chimera.common.IChimeraRemoteCommand;
import com.progress.common.util.acctAuthenticate;
import com.progress.common.util.crypto;
import com.progress.common.util.Getopt;
import com.progress.common.util.ICmdConst;
import com.progress.ubroker.util.IPropConst;

public class UBCmdClient extends RemoteCommandClient implements IPropConst, ICmdConst, IBTMsgCodes
{
    public static final boolean DEBUG_TRACE = false;
    public static final long[] AS_HELP_MESSAGES;
    public static final long[] WS_HELP_MESSAGES;
    public static final long[] DEFAULT_HELP_MESSAGES;
    public static Object[] argList;
    
    public static void printUsage(final int n) {
        System.out.println(getHelpMessage(n));
    }
    
    public static void main(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("query", 10), new Getopt.GetoptList("q", 10), new Getopt.GetoptList("start", 20), new Getopt.GetoptList("x", 20), new Getopt.GetoptList("stop", 30), new Getopt.GetoptList("e", 30), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("h", 40), new Getopt.GetoptList("name:", 50), new Getopt.GetoptList("i:", 50), new Getopt.GetoptList("user:", 60), new Getopt.GetoptList("u:", 60), new Getopt.GetoptList("host:", 90), new Getopt.GetoptList("r:", 90), new Getopt.GetoptList("port:", 100), new Getopt.GetoptList("addservers:", 70), new Getopt.GetoptList("addagents:", 70), new Getopt.GetoptList("s:", 70), new Getopt.GetoptList("trimservers:", 80), new Getopt.GetoptList("trimagents:", 80), new Getopt.GetoptList("trim:", 80), new Getopt.GetoptList("t:", 110), new Getopt.GetoptList("kill", 180), new Getopt.GetoptList("k", 180), new Getopt.GetoptList("listclients", 560), new Getopt.GetoptList("l", 560), new Getopt.GetoptList("clientdetail:", 570), new Getopt.GetoptList("c:", 570), new Getopt.GetoptList("listpropname:", 580), new Getopt.GetoptList("z:", 580), new Getopt.GetoptList("listallprops", 590), new Getopt.GetoptList("y", 590), new Getopt.GetoptList("", 0) };
        String optArg = null;
        final boolean b = false;
        new Integer(20931).toString();
        System.out.print("\n");
        boolean remoteSystem = false;
        String optArg2 = null;
        int n = 2;
        boolean b2 = true;
        final crypto crypto = new crypto();
        final Getopt getopt = new Getopt(array);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 40: {
                    printUsage(n);
                    System.exit(0);
                    continue;
                }
                case 110: {
                    optArg2 = getopt.getOptArg();
                    if (optArg2.equals("AS")) {
                        n = 2;
                        continue;
                    }
                    if (optArg2.equals("WS")) {
                        n = 1;
                        continue;
                    }
                    if (optArg2.equals("NS")) {
                        n = 6;
                        continue;
                    }
                    if (optArg2.equals("OD")) {
                        n = 4;
                        continue;
                    }
                    if (optArg2.equals("OR")) {
                        n = 3;
                        continue;
                    }
                    if (optArg2.equals("AD")) {
                        n = 8;
                        continue;
                    }
                    if (optArg2.equals("MS")) {
                        n = 5;
                        continue;
                    }
                    continue;
                }
                case 63: {
                    UBCmdClient.argList = new Object[] { getopt.getOptArg() };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382095L, UBCmdClient.argList));
                    printUsage(n);
                    System.exit(1);
                    continue;
                }
            }
        }
        final UBCmdClient ubCmdClient = new UBCmdClient();
        if (optArg2 == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382245L));
            System.exit(1);
        }
        String[] array3 = null;
        final Getopt getopt2 = new Getopt(array);
        int opt2;
        while ((opt2 = getopt2.getOpt(array2)) != -1) {
            switch (opt2) {
                case 50: {
                    optArg = getopt2.getOptArg();
                    continue;
                }
                case 70: {
                    if (n != 6) {
                        array3 = new String[] { optArg2, "s", getopt2.getOptArg() };
                        continue;
                    }
                    b2 = false;
                    continue;
                }
                case 80: {
                    if (n != 6) {
                        array3 = new String[] { optArg2, "t", getopt2.getOptArg() };
                        continue;
                    }
                    b2 = false;
                    continue;
                }
                case 10: {
                    array3 = new String[] { optArg2, "q" };
                    continue;
                }
                case 30: {
                    array3 = new String[] { optArg2, "e" };
                    continue;
                }
                case 180: {
                    array3 = new String[] { optArg2, "k" };
                    continue;
                }
                case 20: {
                    array3 = new String[] { optArg2, "x" };
                    continue;
                }
                case 100: {
                    ubCmdClient.m_rmiPort = getopt2.getOptArg();
                    continue;
                }
                case 90: {
                    ubCmdClient.m_host = getopt2.getOptArg();
                    remoteSystem = isRemoteSystem(ubCmdClient.m_host);
                    continue;
                }
                case 60: {
                    ubCmdClient.m_userName = getopt2.getOptArg();
                    continue;
                }
                case 580: {
                    array3 = new String[] { optArg2, "z", getopt2.getOptArg() };
                    continue;
                }
                case 590: {
                    array3 = new String[] { optArg2, "y" };
                    continue;
                }
                case 560: {
                    if (n == 2) {
                        array3 = new String[] { optArg2, "l" };
                        continue;
                    }
                    b2 = false;
                    continue;
                }
                case 570: {
                    if (n == 2) {
                        array3 = new String[] { optArg2, "c", getopt2.getOptArg() };
                        try {
                            Long.parseLong(array3[2]);
                        }
                        catch (NumberFormatException ex) {
                            if (array3[2].compareToIgnoreCase("all") == 0) {
                                array3[2] = "0";
                            }
                            else {
                                System.out.println(UBToolsMsg.getMsg(7094295313015388759L));
                                printUsage(n);
                                System.exit(1);
                            }
                        }
                        continue;
                    }
                    b2 = false;
                    continue;
                }
            }
        }
        if (optArg == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382096L));
            printUsage(n);
            System.exit(1);
        }
        if (array3 == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382097L));
            printUsage(n);
            System.exit(1);
        }
        if (!b2) {
            if (n == 6) {
                System.out.println(UBToolsMsg.getMsg(7094295313015382098L));
            }
            else {
                System.out.println(UBToolsMsg.getMsg(7094295313015388758L));
            }
            printUsage(n);
            System.exit(1);
        }
        if (!remoteSystem) {
            if (ubCmdClient.m_userName == null) {
                ubCmdClient.m_userName = getLocalUser();
                ubCmdClient.m_password = new acctAuthenticate().generatePassword(ubCmdClient.m_userName);
            }
            else if (ubCmdClient.m_password == null && ubCmdClient.m_userName.equals(getLocalUser())) {
                ubCmdClient.m_password = new acctAuthenticate().generatePassword(ubCmdClient.m_userName);
            }
        }
        if (ubCmdClient.m_userName == null || ubCmdClient.m_password == null) {
            final String[] promptForUP = promptForUP(ubCmdClient.m_userName);
            ubCmdClient.m_userName = promptForUP[0];
            ubCmdClient.m_password = promptForUP[1];
        }
        ubCmdClient.m_userName = crypto.encrypt(ubCmdClient.m_userName);
        ubCmdClient.m_password = crypto.encrypt(ubCmdClient.m_password);
        if (b) {
            if (array3[0].equals("e")) {
                try {
                    UBCmdClient.argList = new Object[] { optArg };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382099L, UBCmdClient.argList));
                    final IAdminRemote connect = ubCmdClient.connect();
                    int i;
                    do {
                        if ((i = connect.shutDown()) == 0) {
                            UBCmdClient.argList = new Object[] { optArg };
                            System.out.println("\n" + UBToolsMsg.getMsg(7094295313015382100L, UBCmdClient.argList));
                            UBCmdClient.argList = new Object[] { optArg };
                            System.out.println(UBToolsMsg.getMsg(7094295313015382101L, UBCmdClient.argList));
                        }
                        else {
                            UBCmdClient.argList = new Object[] { optArg };
                            System.out.println(UBToolsMsg.getMsg(7094295313015382102L, UBCmdClient.argList));
                        }
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (InterruptedException ex2) {}
                    } while (i == 1);
                    if (i == 2) {
                        UBCmdClient.argList = new Object[] { optArg };
                        System.out.println(UBToolsMsg.getMsg(7094295313015382103L, UBCmdClient.argList));
                        System.exit(1);
                    }
                }
                catch (Exception ex3) {
                    UBCmdClient.argList = new Object[] { optArg };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382114L, UBCmdClient.argList));
                }
            }
            System.exit(0);
        }
        try {
            UBCmdClient.argList = new Object[] { ubCmdClient.createURL() };
            System.out.println("\n" + UBToolsMsg.getMsg(7094295313015382104L, UBCmdClient.argList));
            final IChimeraRemoteCommand connect2 = ubCmdClient.connect(optArg);
            if (connect2 != null) {
                UBCmdClient.argList = new Object[] { connect2.getDisplayName() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382100L, UBCmdClient.argList));
                final int runIt = connect2.runIt(array3);
                if (runIt == 0) {
                    if (array3[1].equals("l") || array3[1].equals("y")) {
                        displayStructuredSystemOutput(connect2, array3[1], null);
                    }
                    else if (array3[1].equals("c") || array3[1].equals("z")) {
                        displayStructuredSystemOutput(connect2, array3[1], array3[2]);
                    }
                    else {
                        System.out.println(connect2.getSystemOutput());
                    }
                }
                else if (runIt == -2) {
                    UBCmdClient.argList = new Object[] { connect2.getDisplayName() };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382998L, UBCmdClient.argList));
                }
                else {
                    String x = connect2.getSystemError();
                    if (x == null) {
                        x = UBToolsMsg.getMsg(7094295313015385900L, UBCmdClient.argList);
                    }
                    System.out.println(x);
                }
            }
            else {
                UBCmdClient.argList = new Object[] { optArg };
                System.out.println(UBToolsMsg.getMsg(7094295313015382105L, UBCmdClient.argList));
            }
        }
        catch (Exception ex4) {
            UBCmdClient.argList = new Object[] { optArg };
            System.out.println(UBToolsMsg.getMsg(7094295313015382105L, UBCmdClient.argList));
        }
        System.exit(0);
    }
    
    public static boolean isRemoteSystem(final String anotherString) {
        boolean b = true;
        try {
            final String hostName = InetAddress.getLocalHost().getHostName();
            if (anotherString.equalsIgnoreCase("LocalHost") || hostName.equalsIgnoreCase(anotherString)) {
                b = false;
            }
        }
        catch (Exception ex) {
            UBCmdClient.argList = new Object[] { "" };
            System.out.println(UBToolsMsg.getMsg(7094295313015382106L, UBCmdClient.argList));
            return false;
        }
        return b;
    }
    
    public static String getLocalUser() {
        String property = null;
        try {
            property = System.getProperty("user.name");
        }
        catch (Exception ex) {
            UBCmdClient.argList = new Object[] { ex.toString() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382107L, UBCmdClient.argList));
        }
        return property;
    }
    
    public static String getHelpMessage(final int n) {
        final StringBuffer sb = new StringBuffer();
        switch (n) {
            case 2: {
                final Object[] array = { "AppServer" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.AS_HELP_MESSAGES[0], array) + "\n");
                sb.append("========================================================================\n");
                for (int i = 1; i < UBCmdClient.AS_HELP_MESSAGES.length; ++i) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.AS_HELP_MESSAGES[i], array) + "\n");
                }
                break;
            }
            case 1: {
                final Object[] array2 = { "WebSpeed Broker" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.WS_HELP_MESSAGES[0], array2) + "\n");
                sb.append("========================================================================\n");
                for (int j = 1; j < UBCmdClient.WS_HELP_MESSAGES.length; ++j) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.WS_HELP_MESSAGES[j], array2) + "\n");
                }
                break;
            }
            case 6: {
                final Object[] array3 = { "NameServer" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[0], array3) + "\n");
                sb.append("========================================================================\n");
                for (int k = 1; k < UBCmdClient.DEFAULT_HELP_MESSAGES.length; ++k) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[k], array3) + "\n");
                }
                break;
            }
            case 3: {
                final Object[] array4 = { "Oracle Dataserver" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[0], array4) + "\n");
                sb.append("========================================================================\n");
                for (int l = 1; l < UBCmdClient.DEFAULT_HELP_MESSAGES.length; ++l) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[l], array4) + "\n");
                }
                break;
            }
            case 4: {
                final Object[] array5 = { "ODBC Server" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[0], array5) + "\n");
                sb.append("========================================================================\n");
                for (int n2 = 1; n2 < UBCmdClient.DEFAULT_HELP_MESSAGES.length; ++n2) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[n2], array5) + "\n");
                }
            }
            case 8: {
                final Object[] array6 = { "Adapter Broker" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[0], array6) + "\n");
                sb.append("========================================================================\n");
                for (int n3 = 1; n3 < UBCmdClient.DEFAULT_HELP_MESSAGES.length; ++n3) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[n3], array6) + "\n");
                }
                break;
            }
            case 5: {
                final Object[] array7 = { "MSS Dataserver" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[0], array7) + "\n");
                sb.append("========================================================================\n");
                for (int n4 = 1; n4 < UBCmdClient.DEFAULT_HELP_MESSAGES.length; ++n4) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCmdClient.DEFAULT_HELP_MESSAGES[n4], array7) + "\n");
                }
                break;
            }
        }
        return sb.toString();
    }
    
    public static String[] promptForUP(final String s) {
        final byte[] array = new byte[50];
        final String[] array2 = new String[2];
        if (s == null) {
            System.out.print(UBToolsMsg.getMsgStripCode(7094295313015382108L));
            try {
                System.in.read(array);
                array2[0] = new String(array).trim();
            }
            catch (Exception ex) {
                System.out.println("Error reading standard input " + ex.getMessage());
                UBCmdClient.argList = new Object[] { ex.getMessage() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382109L, UBCmdClient.argList));
            }
        }
        else {
            array2[0] = s;
        }
        UBCmdClient.argList = new Object[] { array2[0] };
        final String msgStripCode = UBToolsMsg.getMsgStripCode(7094295313015382110L, UBCmdClient.argList);
        final acctAuthenticate acctAuthenticate = new acctAuthenticate();
        try {
            array2[1] = acctAuthenticate.passwdPromptJNI(msgStripCode).trim();
        }
        catch (Exception ex2) {
            UBCmdClient.argList = new Object[] { ex2.getMessage() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382109L, UBCmdClient.argList));
        }
        return array2;
    }
    
    public static void displayStructuredSystemOutput(final IChimeraRemoteCommand chimeraRemoteCommand, final String s, final String s2) throws RemoteException {
        final Hashtable structuredSystemOutput = chimeraRemoteCommand.getStructuredSystemOutput();
        if (s.equals("l")) {
            displayClientConnectionSummary(System.out, structuredSystemOutput);
        }
        if (s.equals("c")) {
            displayClientConnectionDetail(System.out, structuredSystemOutput);
        }
        if (s.equals("z") || s.equals("y")) {
            displayFilteredProperties(System.out, structuredSystemOutput);
        }
    }
    
    public static void displayFilteredProperties(final PrintStream printStream, final Hashtable hashtable) {
        final Vector list = new Vector(hashtable.size());
        final String str = "Active properties\n=================";
        final Enumeration<String> keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            list.add(s + "=" + hashtable.get(s));
        }
        Collections.sort((List<Comparable>)list);
        printStream.println("\n" + str);
        for (int i = 0; i < list.size(); ++i) {
            printStream.println(list.elementAt(i));
        }
    }
    
    public static void displayClientConnectionSummary(final PrintStream printStream, final Hashtable hashtable) {
        final String str = "   ConnHdl User             Rmt IP                       Rmt Port State\n   ------- ----             ------                       -------- -----";
        final String s = hashtable.get("table name");
        if (!s.equals("client summary")) {
            printStream.println(UBToolsMsg.getMsg(7094295313015388757L, new Object[] { s }));
            return;
        }
        final int intValue = (int)hashtable.get("num connections");
        if (intValue == 0) {
            printStream.println(UBToolsMsg.getMsg(7094295313015388755L, new Object[0]));
            return;
        }
        final Vector vector = (Vector)hashtable.get("table data");
        printStream.println("\n" + str);
        for (int i = 0; i < intValue; ++i) {
            final Hashtable<K, Integer> hashtable2 = vector.elementAt(i);
            final Integer n = hashtable2.get("conn hdl");
            final String s2 = (String)hashtable2.get("user name");
            final String s3 = (String)hashtable2.get("remote addr");
            final Integer n2 = hashtable2.get("remote port");
            final String str2 = (String)hashtable2.get("connection state");
            final StringBuffer sb = new StringBuffer();
            sb.append(blankLeft(n, 10) + " ");
            sb.append(blankRight(s2, 16) + " ");
            sb.append(blankRight(s3, 31) + " ");
            sb.append(blankLeft(n2, 5) + " ");
            sb.append(str2);
            printStream.println(sb.toString());
        }
        printStream.println("");
    }
    
    public static void displayClientConnectionDetail(final PrintStream printStream, final Hashtable hashtable) {
        final String s = hashtable.get("table name");
        if (!s.equals("client detail")) {
            printStream.println(UBToolsMsg.getMsg(7094295313015388757L, new Object[] { s }));
            return;
        }
        final int intValue = (int)hashtable.get("num connections");
        if (intValue == 0) {
            printStream.println(UBToolsMsg.getMsg(7094295313015388756L, new Object[0]));
            return;
        }
        final Vector vector = (Vector)hashtable.get("table data");
        printStream.println("");
        for (int i = 0; i < intValue; ++i) {
            final Hashtable<K, Integer> hashtable2 = vector.elementAt(i);
            printStream.println(" conn hdl= " + hashtable2.get("conn hdl"));
            printStream.println(" user name= " + (String)hashtable2.get("user name"));
            printStream.println(" remote addr= " + (String)hashtable2.get("remote addr"));
            printStream.println(" remote port= " + hashtable2.get("remote port"));
            printStream.println(" connection state= " + (String)hashtable2.get("connection state"));
            printStream.println(" conn ID= " + (String)hashtable2.get("conn ID"));
            printStream.println(" request count= " + hashtable2.get("request count"));
            printStream.println(" agent PID= " + hashtable2.get("agent PID"));
            printStream.println(" agent port= " + hashtable2.get("agent port"));
            printStream.println("");
        }
    }
    
    private static String pad(final Object obj, final char c, final int n, final boolean b) {
        final StringBuffer sb = new StringBuffer();
        if (obj != null) {
            sb.append(obj);
        }
        for (int i = n - sb.length(); i > 0; --i) {
            if (b) {
                sb.insert(0, c);
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    private static String blankRight(final Object o, final int n) {
        return pad(o, ' ', n, false);
    }
    
    private static String blankLeft(final Object o, final int n) {
        return pad(o, ' ', n, true);
    }
    
    static {
        AS_HELP_MESSAGES = new long[] { 7094295313015382202L, 7094295313015382201L, 7094295313015382203L, 7094295313015382204L, 7094295313015382205L, 7094295313015382206L, 7094295313015382207L, 7094295313015382208L, 7094295313015382209L, 7094295313015382210L, 7094295313015382211L, 7094295313015388744L, 7094295313015388745L, 7094295313015389046L, 7094295313015382212L };
        WS_HELP_MESSAGES = new long[] { 7094295313015382202L, 7094295313015382201L, 7094295313015382203L, 7094295313015382204L, 7094295313015382205L, 7094295313015382206L, 7094295313015382207L, 7094295313015382208L, 7094295313015382239L, 7094295313015382240L, 7094295313015382211L, 7094295313015389046L, 7094295313015382212L };
        DEFAULT_HELP_MESSAGES = new long[] { 7094295313015382202L, 7094295313015382201L, 7094295313015382203L, 7094295313015382204L, 7094295313015382205L, 7094295313015382206L, 7094295313015382207L, 7094295313015382208L, 7094295313015382211L, 7094295313015389046L, 7094295313015382212L };
    }
}
