// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.Remote;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Vector;
import java.rmi.UnknownHostException;
import com.progress.chimera.adminserver.IAdminServerConnection;
import java.rmi.RMISecurityManager;
import java.net.InetAddress;
import org.w3c.dom.Document;
import com.progress.chimera.common.IChimeraHierarchy;
import java.io.FileNotFoundException;
import org.w3c.dom.Node;
import com.progress.common.ehnlog.AppLogger;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.wsa.tools.WsaLoginInfo;
import com.progress.common.util.acctAuthenticate;
import com.progress.wsa.WsaConstants;
import java.io.IOException;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.common.util.crypto;
import com.progress.common.util.Getopt;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.common.util.ICmdConst;
import com.progress.ubroker.util.IPropConst;
import com.progress.ubroker.tools.RemoteCommandClient;

public class WsaManClient extends RemoteCommandClient implements IPropConst, ICmdConst, IBTMsgCodes
{
    static boolean msgAdapterInited;
    public static final String ADMINSERVER_FAILURE_STR = "%%Failed:";
    public static final boolean DEBUG_TRACE = false;
    public static final long MAN_WSAUTH_HELP = 8607504787811871257L;
    public static final long MAN_WSM_HELP = 8607504787811871258L;
    public static final long MAN_WSD_HELP = 8607504787811871259L;
    public static final long MAN_APP_HELP = 8607504787811871260L;
    public static final long MAN_ENCOD_HELP = 8607504787811871261L;
    public static final long MAN_NAMSPC_HELP = 8607504787811871262L;
    public static final long MAN_PROP_HELP = 8607504787811871263L;
    public static final long MAN_VALUE_HELP = 8607504787811871264L;
    public static final long MAN_WSAQUERY_HELP = 8607504787811871265L;
    public static final long MAN_DEPLOY_HELP = 8607504787811871266L;
    public static final long MAN_UNDEPLOY_HELP = 8607504787811871267L;
    public static final long MAN_IMPORT_HELP = 8607504787811871268L;
    public static final long MAN_LIST_HELP = 8607504787811871269L;
    public static final long MAN_GETDEFS_HELP = 8607504787811871270L;
    public static final long MAN_SETDEFS_HELP = 8607504787811871271L;
    public static final long MAN_RESETDEFS_HELP = 8607504787811871272L;
    public static final long MAN_ENABLE_HELP = 8607504787811871273L;
    public static final long MAN_DISABLE_HELP = 8607504787811871274L;
    public static final long MAN_UPDATE_HELP = 8607504787811871275L;
    public static final long MAN_EXPORT_HELP = 8607504787811871276L;
    public static final long MAN_GETSTATS_HELP = 8607504787811871277L;
    public static final long MAN_RESETSTATS_HELP = 8607504787811871278L;
    public static final long MAN_GETPROPS = 8607504787811871279L;
    public static final long MAN_SETPROPS = 8607504787811871280L;
    public static final long MAN_RESETPROPS_HELP = 8607504787811871281L;
    public static final long MAN_ENTER_WS_USER = 8607504787811871370L;
    public static final long MAN_ENTER_WS_PASSWORD = 8607504787811871371L;
    public static final long[] WSA_HELP_MESSAGES;
    public static Object[] argList;
    
    public static void printUsage(final int n) {
        System.out.println(getHelpMessage());
    }
    
    public static void main(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("deploy", 240), new Getopt.GetoptList("undeploy", 250), new Getopt.GetoptList("query", 10), new Getopt.GetoptList("q", 10), new Getopt.GetoptList("list", 260), new Getopt.GetoptList("wsm:", 280), new Getopt.GetoptList("appname:", 270), new Getopt.GetoptList("g", 230), new Getopt.GetoptList("getstats", 310), new Getopt.GetoptList("resetStats", 320), new Getopt.GetoptList("enable", 400), new Getopt.GetoptList("disable", 410), new Getopt.GetoptList("setprops", 370), new Getopt.GetoptList("getprops", 360), new Getopt.GetoptList("prop:", 120), new Getopt.GetoptList("value:", 330), new Getopt.GetoptList("import", 380), new Getopt.GetoptList("export", 390), new Getopt.GetoptList("wsd:", 440), new Getopt.GetoptList("update", 450), new Getopt.GetoptList("getdefaults", 340), new Getopt.GetoptList("setdefaults", 350), new Getopt.GetoptList("resetdefaults", 420), new Getopt.GetoptList("test", 430), new Getopt.GetoptList("namespace:", 470), new Getopt.GetoptList("encoding:", 480), new Getopt.GetoptList("webserverauth:", 490), new Getopt.GetoptList("resetprops", 460), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("h", 40), new Getopt.GetoptList("name:", 50), new Getopt.GetoptList("i:", 50), new Getopt.GetoptList("user:", 60), new Getopt.GetoptList("u:", 60), new Getopt.GetoptList("host:", 90), new Getopt.GetoptList("r:", 90), new Getopt.GetoptList("port:", 100), new Getopt.GetoptList("t:", 110), new Getopt.GetoptList("", 0) };
        int value = -1;
        String s = null;
        String optArg = null;
        String optArg2 = null;
        String optArg3 = null;
        String optArg4 = null;
        Object optArg5 = null;
        Object optArg6 = null;
        String optArg7 = null;
        String optArg8 = null;
        WSAD loadWSMFile = null;
        AppContainer loadWSDFile = null;
        new Integer(20931).toString();
        String s2 = null;
        String encrypt = "";
        System.out.print("\n");
        boolean remoteSystem = false;
        final int n = 2;
        final crypto crypto = new crypto();
        final WsaManClient wsaManClient = new WsaManClient();
        Object[] array3 = null;
        final Getopt getopt = new Getopt(array);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 40: {
                    printUsage(n);
                    System.exit(0);
                    continue;
                }
                case 240: {
                    value = 240;
                    s = "deploy";
                    continue;
                }
                case 250: {
                    value = 250;
                    s = "undeploy";
                    continue;
                }
                case 260: {
                    value = 260;
                    s = "list";
                    continue;
                }
                case 310: {
                    value = 310;
                    s = "get runtime statistics";
                    continue;
                }
                case 320: {
                    value = 320;
                    s = "reset runtime statistics";
                    continue;
                }
                case 400: {
                    value = 400;
                    s = "enable";
                    continue;
                }
                case 410: {
                    value = 410;
                    s = "disable";
                    continue;
                }
                case 370: {
                    value = 370;
                    s = "set runtime properties";
                    continue;
                }
                case 360: {
                    value = 360;
                    s = "get runtime properties";
                    continue;
                }
                case 380: {
                    value = 380;
                    s = "import";
                    continue;
                }
                case 390: {
                    value = 390;
                    s = "export";
                    continue;
                }
                case 450: {
                    value = 450;
                    s = "update";
                    continue;
                }
                case 340: {
                    value = 340;
                    s = "get default properties";
                    continue;
                }
                case 350: {
                    value = 350;
                    s = "set default properties";
                    continue;
                }
                case 420: {
                    value = 420;
                    s = "reset default properties";
                    continue;
                }
                case 460: {
                    value = 460;
                    s = "reset runtime properties";
                    continue;
                }
                case 280: {
                    optArg2 = getopt.getOptArg();
                    continue;
                }
                case 270: {
                    optArg4 = getopt.getOptArg();
                    if (!wsaManClient.validFriendlyName(optArg4)) {
                        WsaManClient.argList = new Object[] { optArg4 };
                        System.out.println(UBToolsMsg.getMsg(8607504787811871284L, WsaManClient.argList));
                        System.exit(1);
                        continue;
                    }
                    continue;
                }
                case 120: {
                    optArg5 = getopt.getOptArg();
                    continue;
                }
                case 330: {
                    optArg6 = getopt.getOptArg();
                    continue;
                }
                case 440: {
                    optArg3 = getopt.getOptArg();
                    continue;
                }
                case 50: {
                    optArg = getopt.getOptArg();
                    continue;
                }
                case 10: {
                    value = 10;
                    s = "query";
                    continue;
                }
                case 470: {
                    optArg7 = getopt.getOptArg();
                    continue;
                }
                case 480: {
                    optArg8 = getopt.getOptArg();
                    continue;
                }
                case 490: {
                    s2 = getopt.getOptArg();
                    continue;
                }
                case 100: {
                    wsaManClient.m_rmiPort = getopt.getOptArg();
                    continue;
                }
                case 90: {
                    wsaManClient.m_host = getopt.getOptArg();
                    remoteSystem = isRemoteSystem(wsaManClient.m_host);
                    continue;
                }
                case 60: {
                    wsaManClient.m_userName = getopt.getOptArg();
                    continue;
                }
                case 63: {
                    WsaManClient.argList = new Object[] { getopt.getOptArg() };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382095L, WsaManClient.argList));
                    printUsage(n);
                    System.exit(1);
                    continue;
                }
            }
        }
        if (optArg == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382096L));
            printUsage(n);
            System.exit(1);
        }
        if (s2 != null) {
            final String[] promptForWSP = promptForWSP(s2);
            final String s3 = promptForWSP[0];
            final String s4 = promptForWSP[1];
            s2 = crypto.encrypt(s3);
            encrypt = crypto.encrypt(s4);
        }
        final String str = (optArg4 != null) ? optArg4 : optArg7;
        switch (value) {
            case 240:
            case 450: {
                if (optArg2 != null) {
                    try {
                        loadWSMFile = WSAD.loadWSMFile(System.getProperty("Install.Dir"), optArg2);
                    }
                    catch (IOException ex2) {}
                    if (loadWSMFile == null) {
                        WsaManClient.argList = new Object[] { "WSM", optArg2 };
                        System.out.println(UBToolsMsg.getMsg(8607504787811871283L, WsaManClient.argList));
                        System.exit(1);
                    }
                    if (optArg7 != null) {
                        loadWSMFile.setWebServiceNamespace(optArg7);
                    }
                    if (optArg8 != null) {
                        try {
                            final int intValue = Integer.valueOf(optArg8);
                            if (intValue < 1 || intValue > WsaConstants.WSA_SERVICE_ENCODING.length - 1) {
                                WsaManClient.argList = new Object[] { optArg8, "\n  1 - RPC/Encoded\n  2 - RPC/Literal\n  3 - Document/Literal" };
                                System.out.println(UBToolsMsg.getMsg(8607504787811871288L, WsaManClient.argList));
                                System.exit(1);
                            }
                            loadWSMFile.setEncodingStyle(intValue);
                        }
                        catch (NumberFormatException ex3) {
                            WsaManClient.argList = new Object[] { optArg8, "\n  1 - RPC/Encoded\n  2 - RPC/Literal\n  3 - Document/Literal" + "\n" };
                            System.out.println(UBToolsMsg.getMsg(8607504787811871288L, WsaManClient.argList));
                            System.exit(1);
                        }
                    }
                    array3 = new Object[] { new Integer(value), optArg4, loadWSMFile.toString() };
                    break;
                }
                break;
            }
            case 250:
            case 390:
            case 400:
            case 410:
            case 460: {
                if (str != null) {
                    array3 = new Object[] { new Integer(value), str };
                    break;
                }
                break;
            }
            case 260: {
                array3 = new Object[] { new Integer(value) };
                break;
            }
            case 10: {
                if (str == null) {
                    array3 = new Object[] { new Integer(500) };
                    break;
                }
                array3 = new Object[] { new Integer(value), str };
                break;
            }
            case 310:
            case 320:
            case 360: {
                array3 = new Object[] { new Integer(value), str };
                break;
            }
            case 370: {
                if (optArg5 != null && optArg6 != null) {
                    array3 = new Object[] { new Integer(value), str, optArg5, optArg6 };
                    break;
                }
                break;
            }
            case 350: {
                if (optArg5 != null && optArg6 != null) {
                    array3 = new Object[] { new Integer(value), optArg5, optArg6 };
                    break;
                }
                break;
            }
            case 340:
            case 420: {
                array3 = new Object[] { new Integer(value) };
                break;
            }
            case 380: {
                if (optArg3 != null) {
                    try {
                        loadWSDFile = AppContainer.loadWSDFile(System.getProperty("Install.Dir"), optArg3);
                    }
                    catch (IOException ex4) {}
                    if (loadWSDFile == null) {
                        WsaManClient.argList = new Object[] { "WSD", optArg2 };
                        System.out.println(UBToolsMsg.getMsg(8607504787811871283L, WsaManClient.argList));
                        System.exit(1);
                    }
                    if (optArg7 != null) {
                        loadWSDFile.getWSAD().setWebServiceNamespace(optArg7);
                    }
                    if (optArg8 != null) {
                        try {
                            final int intValue2 = Integer.valueOf(optArg8);
                            if (intValue2 < 1 || intValue2 > WsaConstants.WSA_SERVICE_ENCODING.length - 1) {
                                WsaManClient.argList = new Object[] { optArg8, "\n  1 - RPC/Encoded\n  2 - RPC/Literal\n  3 - Document/Literal" + "\n" };
                                System.out.println(UBToolsMsg.getMsg(8607504787811871288L, WsaManClient.argList));
                                System.exit(1);
                            }
                            loadWSDFile.getWSAD().setEncodingStyle(intValue2);
                        }
                        catch (NumberFormatException ex5) {
                            WsaManClient.argList = new Object[] { optArg8, "\n  1 - RPC/Encoded\n  2 - RPC/Literal\n  3 - Document/Literal" + "\n" };
                            System.out.println(UBToolsMsg.getMsg(8607504787811871288L, WsaManClient.argList));
                            System.exit(1);
                        }
                    }
                    array3 = new Object[] { new Integer(value), str, loadWSDFile.toString() };
                    break;
                }
                break;
            }
        }
        if (array3 == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382097L));
            printUsage(n);
            System.exit(1);
        }
        if (!remoteSystem) {
            if (wsaManClient.m_userName == null) {
                wsaManClient.m_userName = getLocalUser();
                wsaManClient.m_password = new acctAuthenticate().generatePassword(wsaManClient.m_userName);
            }
            else if (wsaManClient.m_password == null && wsaManClient.m_userName.equals(getLocalUser())) {
                wsaManClient.m_password = new acctAuthenticate().generatePassword(wsaManClient.m_userName);
            }
        }
        if (wsaManClient.m_userName == null || wsaManClient.m_password == null) {
            final String[] promptForUP = promptForUP(wsaManClient.m_userName);
            wsaManClient.m_userName = promptForUP[0];
            wsaManClient.m_password = promptForUP[1];
        }
        wsaManClient.m_userName = crypto.encrypt(wsaManClient.m_userName);
        wsaManClient.m_password = crypto.encrypt(wsaManClient.m_password);
        try {
            final WsaLoginInfo wsaLoginInfo = new WsaLoginInfo(wsaManClient.m_userName, wsaManClient.m_password, s2, encrypt);
            if (s2 != null) {
                wsaLoginInfo.setWsaLoginInfo(s2, encrypt);
            }
            WsaManClient.argList = new Object[] { wsaManClient.createURL() };
            System.out.println("\n" + UBToolsMsg.getMsg(7094295313015382104L, WsaManClient.argList));
            final IChimeraHierarchy connectEX = wsaManClient.connectEX(optArg);
            IYodaRMI yodaRMI = null;
            if (connectEX instanceof IYodaRMI) {
                yodaRMI = (IYodaRMI)connectEX;
            }
            if (yodaRMI != null) {
                try {
                    int i = 0;
                    String s5 = "";
                    int n2 = 1;
                    while (i == 0) {
                        s5 = new WsaManClientHelper().help(yodaRMI, wsaLoginInfo, array3);
                        if (n2 < 2 && wsaManClient.webServerAuthFailure(s5) && s2 == null) {
                            final String[] promptForWSP2 = promptForWSP(null);
                            wsaLoginInfo.setWsaLoginInfo(crypto.encrypt(promptForWSP2[0]), crypto.encrypt(promptForWSP2[1]));
                        }
                        else {
                            i = 1;
                        }
                        ++n2;
                    }
                    switch (value) {
                        case 390: {
                            if (s5 != null || s5.startsWith("%%Failed:")) {
                                final AppContainer appContainer = new AppContainer();
                                final Document str2 = new WsaParser(System.getProperty("Install.Dir"), null).parseStr(s5, 0);
                                if (str2 != null) {
                                    appContainer.readXML(str2.getDocumentElement());
                                    final String str3 = (optArg3 != null) ? optArg3 : (appContainer.getFriendlyName() + ".wsd");
                                    try {
                                        appContainer.saveWSDFile(str3);
                                    }
                                    catch (FileNotFoundException ex6) {
                                        UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList = new Object[] { str + " to wsd file: " + str3 + "\nCreate the directory structure and/or make the file writeable first then export." });
                                    }
                                    catch (IOException ex7) {
                                        UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList = new Object[] { str + " to wsd file: " + str3 });
                                    }
                                    WsaManClient.argList = new Object[] { s + "ed", str + " to wsd file: " + str3 };
                                    s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                                }
                                else {
                                    WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                    s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                }
                                break;
                            }
                            WsaManClient.argList = new Object[] { s };
                            s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                            break;
                        }
                        case 260: {
                            if (s5 == null || s5.equals("")) {
                                WsaManClient.argList = new Object[] { optArg };
                                s5 = UBToolsMsg.getMsg(8607504787811871221L, WsaManClient.argList);
                                break;
                            }
                            if (!s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { optArg, "\n" + s5 };
                                s5 = UBToolsMsg.getMsg(8607504787811871222L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                            s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                            break;
                        }
                        case 10: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871219L, WsaManClient.argList);
                            break;
                        }
                        case 250: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s + "ed", str };
                            s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                            break;
                        }
                        case 240: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s + "ed", s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                            break;
                        }
                        case 310: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { (str == null) ? optArg : str, "\n" + s5 + "\n" };
                            s5 = UBToolsMsg.getMsg(8607504787811871240L, WsaManClient.argList);
                            break;
                        }
                        case 320: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { (str == null) ? optArg : str };
                            s5 = UBToolsMsg.getMsg(8607504787811871241L, WsaManClient.argList);
                            break;
                        }
                        case 400:
                        case 410: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s + "d", str };
                            s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                            break;
                        }
                        case 370: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { "runtime", optArg5, optArg6 };
                            s5 = UBToolsMsg.getMsg(8607504787811871239L, WsaManClient.argList);
                            break;
                        }
                        case 360: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            if (str == null) {
                                WsaManClient.argList = new Object[] { optArg, "\n" + s5 };
                                s5 = UBToolsMsg.getMsg(8607504787811871430L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { str, "\n" + s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871247L, WsaManClient.argList);
                            break;
                        }
                        case 380: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s + "ed", s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                            break;
                        }
                        case 450: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { s + "d", s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871220L, WsaManClient.argList);
                            break;
                        }
                        case 340: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { "\n" + s5 };
                            s5 = UBToolsMsg.getMsg(8607504787811871238L, WsaManClient.argList);
                            break;
                        }
                        case 350: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { "default", optArg5, optArg6 };
                            s5 = UBToolsMsg.getMsg(8607504787811871239L, WsaManClient.argList);
                            break;
                        }
                        case 420: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { "defaults", (str == null) ? optArg : str };
                            s5 = UBToolsMsg.getMsg(8607504787811871248L, WsaManClient.argList);
                            break;
                        }
                        case 460: {
                            if (s5 == null || s5.startsWith("%%Failed:")) {
                                WsaManClient.argList = new Object[] { (s5 != null) ? (s + ".\n" + wsaManClient.getRequestFailureStr(s5)) : s };
                                s5 = UBToolsMsg.getMsg(8607504787811871218L, WsaManClient.argList);
                                break;
                            }
                            WsaManClient.argList = new Object[] { "properties", (str == null) ? optArg : str };
                            s5 = UBToolsMsg.getMsg(8607504787811871248L, WsaManClient.argList);
                            break;
                        }
                    }
                    System.out.println(s5 + "\n");
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                WsaManClient.argList = new Object[] { optArg };
                System.out.println(UBToolsMsg.getMsg(7094295313015382105L, WsaManClient.argList));
            }
        }
        catch (Exception ex8) {
            WsaManClient.argList = new Object[] { optArg };
            System.out.println(UBToolsMsg.getMsg(7094295313015382105L, WsaManClient.argList));
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
            WsaManClient.argList = new Object[] { "" };
            System.out.println(UBToolsMsg.getMsg(7094295313015382106L, WsaManClient.argList));
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
            WsaManClient.argList = new Object[] { ex.toString() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382107L, WsaManClient.argList));
        }
        return property;
    }
    
    public static String getHelpMessage() {
        final StringBuffer sb = new StringBuffer();
        final Object[] array = { "Web Services Adapter" };
        sb.append(UBToolsMsg.getMsgStripCode(WsaManClient.WSA_HELP_MESSAGES[0], array) + "\n");
        sb.append("========================================================================\n");
        for (int i = 1; i < WsaManClient.WSA_HELP_MESSAGES.length; ++i) {
            sb.append(UBToolsMsg.getMsgStripCode(WsaManClient.WSA_HELP_MESSAGES[i], array) + "\n");
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
                WsaManClient.argList = new Object[] { ex.getMessage() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382109L, WsaManClient.argList));
            }
        }
        else {
            array2[0] = s;
        }
        WsaManClient.argList = new Object[] { array2[0] };
        final String msgStripCode = UBToolsMsg.getMsgStripCode(7094295313015382110L, WsaManClient.argList);
        final acctAuthenticate acctAuthenticate = new acctAuthenticate();
        try {
            array2[1] = acctAuthenticate.passwdPromptJNI(msgStripCode).trim();
        }
        catch (Exception ex2) {
            WsaManClient.argList = new Object[] { ex2.getMessage() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382109L, WsaManClient.argList));
        }
        return array2;
    }
    
    public static String[] promptForWSP(final String s) {
        final byte[] array = new byte[50];
        final String[] array2 = new String[2];
        if (s == null) {
            System.out.print(UBToolsMsg.getMsgStripCode(8607504787811871370L));
            try {
                System.in.read(array);
                array2[0] = new String(array).trim();
            }
            catch (Exception ex) {
                WsaManClient.argList = new Object[] { ex.getMessage() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382109L, WsaManClient.argList));
            }
        }
        else {
            array2[0] = s;
        }
        WsaManClient.argList = new Object[] { array2[0] };
        final String msgStripCode = UBToolsMsg.getMsgStripCode(8607504787811871371L, WsaManClient.argList);
        final acctAuthenticate acctAuthenticate = new acctAuthenticate();
        try {
            array2[1] = acctAuthenticate.passwdPromptJNI(msgStripCode).trim();
        }
        catch (Exception ex2) {
            WsaManClient.argList = new Object[] { ex2.getMessage() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382109L, WsaManClient.argList));
        }
        return array2;
    }
    
    protected IChimeraHierarchy connectEX(final String s) {
        IChimeraHierarchy chimeraHierarchy = null;
        super.m_adminConStub = null;
        final String url = this.createURL();
        final String s2 = "com.progress.chimera.common.IChimeraHierarchy";
        try {
            System.setSecurityManager(new RMISecurityManager());
            super.m_adminConStub = (IAdminServerConnection)lookupService(url);
        }
        catch (UnknownHostException ex) {
            WsaManClient.argList = new Object[] { super.m_host };
            System.err.println(UBToolsMsg.getMsg(7094295313015382391L, WsaManClient.argList));
        }
        catch (Exception ex2) {
            WsaManClient.argList = new Object[] { url };
            System.err.println(UBToolsMsg.getMsg(7094295313015382111L, WsaManClient.argList));
            return chimeraHierarchy;
        }
        if (s != null) {
            try {
                super.m_adminStub = super.m_adminConStub.connect(super.m_userName, super.m_password);
                if (super.m_adminStub == null) {
                    System.err.println(UBToolsMsg.getMsgStripCode(7094295313015382352L));
                    return null;
                }
                final Vector plugins = super.m_adminStub.getPlugins(s2);
                WsaManClient.argList = new Object[] { s };
                System.out.println(UBToolsMsg.getMsg(7094295313015382112L, WsaManClient.argList));
                for (int i = 0; i <= plugins.size(); ++i) {
                    final Enumeration children = plugins.elementAt(i).getChildren();
                    if (children != null) {
                        while (children.hasMoreElements()) {
                            final IChimeraHierarchy chimeraHierarchy2 = children.nextElement();
                            if (s.equals(chimeraHierarchy2.getDisplayName())) {
                                chimeraHierarchy = chimeraHierarchy2;
                                break;
                            }
                        }
                        if (chimeraHierarchy != null) {
                            break;
                        }
                    }
                }
            }
            catch (Exception ex3) {
                WsaManClient.argList = new Object[] { s };
                System.out.println(UBToolsMsg.getMsg(7094295313015382113L, WsaManClient.argList));
            }
        }
        return chimeraHierarchy;
    }
    
    public boolean validFriendlyName(final String str) {
        return new StringTokenizer(str, ":;,.<>[]{}|=+()*&^%$#@!`~?\\'\"/ ").countTokens() <= 1;
    }
    
    public String getRequestFailureStr(final String s) {
        return s.substring("%%Failed:".length());
    }
    
    public boolean webServerAuthFailure(final String s) {
        int index = -1;
        try {
            index = s.indexOf(" WEB SERVER AUTHENTICATION FAILURE ");
        }
        catch (NullPointerException ex) {}
        return index != -1;
    }
    
    private static Remote lookupService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        final String host = pscURLParser.getHost();
        final int port = pscURLParser.getPort();
        final String service = pscURLParser.getService();
        final Registry registry = LocateRegistry.getRegistry(host, port);
        return (service == null) ? registry : registry.lookup(service);
    }
    
    static {
        WsaManClient.msgAdapterInited = false;
        WSA_HELP_MESSAGES = new long[] { 7094295313015382202L, 7094295313015382201L, 7094295313015382203L, 7094295313015382205L, 7094295313015382206L, 7094295313015382207L, 8607504787811871257L, 8607504787811871258L, 8607504787811871259L, 8607504787811871260L, 8607504787811871261L, 8607504787811871262L, 8607504787811871263L, 8607504787811871264L, 8607504787811871265L, 8607504787811871266L, 8607504787811871267L, 8607504787811871268L, 8607504787811871269L, 8607504787811871270L, 8607504787811871271L, 8607504787811871272L, 8607504787811871273L, 8607504787811871274L, 8607504787811871275L, 8607504787811871276L, 8607504787811871277L, 8607504787811871278L, 8607504787811871279L, 8607504787811871280L, 8607504787811871281L };
    }
}
