// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.util.InstallPath;
import java.io.IOException;
import com.progress.ubroker.tools.UBCmdClient;
import java.util.Hashtable;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

class ubroker implements ubConstants
{
    public static final String UBROKER_VER = "v102B (15-Feb-12)";
    static ubProperties brokerProperties;
    static RequestQueue requestQueue;
    static IAppLogger log;
    static ubListenerThread portListener;
    static EventBroker eb;
    
    public static void main(final String[] array) {
        System.err.println("ubroker : v102B (15-Feb-12)");
        Thread.currentThread().setName("Main  ");
        try {
            ubroker.eb = new EventBroker();
            ubroker.brokerProperties = new ubProperties(ubroker.eb);
        }
        catch (Throwable t2) {
            System.err.println("Unable to monitor properties file");
            ubroker.brokerProperties = new ubProperties();
            ubroker.eb = null;
        }
        ubroker.log = ubroker.brokerProperties.processArgs(array);
        try {
            JavaServices.createServices(ubroker.brokerProperties, "sessionId");
        }
        catch (Throwable t) {
            System.err.println(t.toString());
            System.exit(1);
        }
        if (ubroker.log.ifLogBasic(1L, 0)) {
            ubroker.log.logBasic(0, 7665689515738013542L, new Object[] { "v102B (15-Feb-12)" });
        }
        (ubroker.portListener = new ubListenerThread(ubroker.brokerProperties, ubroker.log, ubroker.eb)).setPriority(ubroker.brokerProperties.getValueAsInt("listenerThreadPriority"));
        ubroker.portListener.start();
        Label_2261: {
            if (ubroker.brokerProperties.rmiURL == null) {
                while (true) {
                    try {
                        System.err.println("::S-Summary D-Detail X-Execute T-TrimBy K-Kill E-Exit A-Abort F-Fathom\n::L-ConnSummary C-ConnDetail Y-ListAllProps Z-ListPropName");
                        final int menuChoice = getMenuChoice();
                        if (menuChoice == -1) {
                            break;
                        }
                        switch (menuChoice) {
                            case 68:
                            case 100: {
                                System.err.println("Detail:\n" + ubroker.portListener.getStatusFormatted(1));
                                continue;
                            }
                            case 70:
                            case 102: {
                                try {
                                    final Hashtable hashtable = (Hashtable)ubroker.portListener.getData(new String[] { "actAgent", "actReq", "actClient", "actProcAS", "actProcWS" });
                                    final Object[][] array2 = hashtable.get("actAgent");
                                    final Object[][] array3 = hashtable.get("actProcAS");
                                    final Object[][] array4 = hashtable.get("actProcWS");
                                    final Integer[] array5 = (Integer[])hashtable.get("actReq");
                                    final Integer[] array6 = (Integer[])hashtable.get("actClient");
                                    System.err.println("<actAgent>");
                                    for (int i = 0; i < array2[0].length; ++i) {
                                        System.err.println("PID: " + array2[0][i]);
                                        switch ((int)array2[1][i]) {
                                            case 3: {
                                                System.err.println("\t           Status: UBTHREAD_STATE_BOUND");
                                                break;
                                            }
                                            case 2: {
                                                System.err.println("\t           Status: UBTHREAD_STATE_BUSY");
                                                break;
                                            }
                                            case 0: {
                                                System.err.println("\t           Status: UBTHREAD_STATE_IDLE");
                                                break;
                                            }
                                            case 1: {
                                                System.err.println("\t           Status: UBTHREAD_STATE_READY");
                                                break;
                                            }
                                            case 4: {
                                                System.err.println("\t           Status: UBTHREAD_STATE_DEAD");
                                                break;
                                            }
                                            default: {
                                                System.err.println("\t           Status: " + array2[i][1]);
                                                break;
                                            }
                                        }
                                        System.err.println("\t      Status Time:" + array2[2][i]);
                                        System.err.println("\t       Idle count: " + array2[3][i]);
                                        System.err.println("\t        Idle Time: " + array2[4][i]);
                                        System.err.println("\t       Busy count: " + array2[5][i]);
                                        System.err.println("\t        Busy Time: " + array2[6][i]);
                                        System.err.println("\t     Locked Count: " + array2[7][i]);
                                        System.err.println("\t      Locked Time: " + array2[8][i]);
                                    }
                                    System.err.println("</actAgent>");
                                    System.err.println("<actReq>");
                                    System.err.println("\tRequests Completed: " + array5[0]);
                                    System.err.println("\t   Requests Queued: " + array5[1]);
                                    System.err.println("\t Requests Rejected: " + array5[2]);
                                    System.err.println("</actReq>");
                                    System.err.println("<actClient>");
                                    System.err.println("\tCurrent clients connected: " + array6[0]);
                                    System.err.println("\t            Total clients: " + array6[1]);
                                    System.err.println("</actClient>");
                                    System.err.println("<actProcAS>");
                                    for (int j = 0; j < array3[0].length; ++j) {
                                        System.err.println("\tName: " + array3[0][j]);
                                        System.err.println("\tParent: " + array3[1][j]);
                                        System.err.println("\tType: " + array3[2][j]);
                                        System.err.println("\tSuccess: " + array3[3][j]);
                                        System.err.println("\tError: " + array3[4][j]);
                                        System.err.println("\tStop: " + array3[5][j]);
                                        System.err.println("\tQuit: " + array3[6][j]);
                                        System.err.println("\tDebug: " + array3[7][j]);
                                        System.err.println("\tTime: " + array3[8][j]);
                                        System.err.println("\tCalls: " + array3[9][j]);
                                        System.err.println();
                                    }
                                    System.err.println("</actProcAS>");
                                    System.err.println("<actProcWS>");
                                    for (int k = 0; k < array4[0].length; ++k) {
                                        System.err.println("\t Name: " + array4[0][k]);
                                        System.err.println("\t Time: " + array4[1][k]);
                                        System.err.println("\tCalls: " + array4[2][k]);
                                        System.err.println();
                                    }
                                    System.err.println("</actProcWS>");
                                    final Hashtable hashtable2 = (Hashtable)ubroker.portListener.getData(new String[] { "client summary", "client detail" });
                                    final Hashtable hashtable3 = hashtable2.get("client summary");
                                    System.err.println("<client summary>");
                                    UBCmdClient.displayClientConnectionSummary(System.err, hashtable3);
                                    System.err.println("</client summary>");
                                    final Hashtable hashtable4 = hashtable2.get("client detail");
                                    System.err.println("<client detail>");
                                    UBCmdClient.displayClientConnectionDetail(System.err, hashtable4);
                                    System.err.println("</client detail>");
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                continue;
                            }
                            case 83:
                            case 115: {
                                System.err.println("Summary:\n" + ubroker.portListener.getStatusFormatted(0));
                                continue;
                            }
                            case 88:
                            case 120: {
                                displayPrompt("Num servers to start");
                                System.err.println("ret=: " + ubroker.portListener.startServers(getKbdInt()) + "\n");
                                continue;
                            }
                            case 84:
                            case 116: {
                                displayPrompt("Num servers to stop");
                                System.err.println("ret=: " + ubroker.portListener.trimBy(getKbdInt()) + "\n");
                                continue;
                            }
                            case 75:
                            case 107: {
                                displayPrompt("PID of server to kill");
                                System.err.println("ret=: " + ubroker.portListener.stopServer(getKbdInt()) + "\n");
                                continue;
                            }
                            case 69:
                            case 101: {
                                try {
                                    System.err.print("Shutting down .");
                                    while (ubroker.portListener.requestShutdown() == 1) {
                                        Thread.sleep(2000L);
                                        System.err.print(".");
                                    }
                                    System.err.println(".");
                                }
                                catch (InterruptedException obj) {
                                    ubroker.log.logError("main() shutdown sleep InterruptedException:  " + obj);
                                }
                                break Label_2261;
                            }
                            case 65:
                            case 97: {
                                ubroker.portListener.requestEmergencyShutdown();
                                break Label_2261;
                            }
                            case 76:
                            case 108: {
                                UBCmdClient.displayClientConnectionSummary(System.err, ubroker.portListener.getStatusStructured(0, null));
                                continue;
                            }
                            case 67:
                            case 99: {
                                displayPrompt("Connection Handle (0 for all)");
                                UBCmdClient.displayClientConnectionDetail(System.err, ubroker.portListener.getStatusStructured(1, new Integer(getKbdInt())));
                                continue;
                            }
                            case 89:
                            case 121: {
                                UBCmdClient.displayFilteredProperties(System.err, ubroker.portListener.getStatusStructured(4, null));
                                continue;
                            }
                            case 90:
                            case 122: {
                                displayPrompt("Property Name (regex)");
                                UBCmdClient.displayFilteredProperties(System.err, ubroker.portListener.getStatusStructured(3, new String(getKbdInput())));
                                continue;
                            }
                        }
                    }
                    catch (IOException obj2) {
                        ubroker.log.logError("main() IOException:  " + obj2 + ":" + obj2.getMessage());
                    }
                }
            }
            try {
                ubroker.portListener.join();
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex2) {
                ubroker.log.logError(7665689515738013544L, new Object[] { ex2.toString() });
            }
        }
        if (ubroker.log.ifLogBasic(1L, 0)) {
            ubroker.log.logBasic(0, 7665689515738013545L, new Object[] { "v102B (15-Feb-12)" });
        }
        ubroker.log.logClose();
        System.exit(0);
    }
    
    private static int getKbdChar() throws IOException {
        int read = 0;
        int i = 1;
        while (i != 0) {
            if (!ubroker.portListener.isRunning()) {
                return -1;
            }
            try {
                if (System.in.available() > 0) {
                    read = System.in.read();
                    i = 0;
                }
                else {
                    Thread.sleep(1000L);
                }
                continue;
            }
            catch (IOException obj) {
                ubroker.log.logError("getKbdChar() got IOException:  " + obj);
                return -1;
            }
            catch (InterruptedException obj2) {
                ubroker.log.logError("getKbdChar() sleep InterruptedException:  " + obj2);
                continue;
            }
            break;
        }
        return read;
    }
    
    private static int getMenuChoice() {
        int n = 0;
        final byte[] array = new byte[100];
        try {
            int kbdChar;
            for (n = 0; n < array.length && (kbdChar = getKbdChar()) != 10; ++n) {
                if (kbdChar == -1) {
                    return -1;
                }
                array[n] = (byte)kbdChar;
            }
        }
        catch (IOException obj) {
            if (ubroker.log.ifLogBasic(1L, 0)) {
                ubroker.log.logBasic(0, "getMenuChoice() IOException:  " + obj + ":" + obj.getMessage());
            }
        }
        return (n == 0) ? 0 : array[0];
    }
    
    private static String getKbdInput() {
        int length = 0;
        final byte[] bytes = new byte[100];
        try {
            int kbdChar;
            for (length = 0; length < bytes.length && (kbdChar = getKbdChar()) != 10; ++length) {
                bytes[length] = (byte)kbdChar;
            }
        }
        catch (IOException obj) {
            if (ubroker.log.ifLogBasic(1L, 0)) {
                ubroker.log.logBasic(0, "getKbdInput() IOException:  " + obj + ":" + obj.getMessage());
            }
        }
        if (length > 0 && bytes[length - 1] == 13) {
            --length;
        }
        return (length == 0) ? new String("") : new String(bytes, 0, length);
    }
    
    private static int getKbdInt() {
        int int1 = 0;
        final String kbdInput = getKbdInput();
        if (kbdInput.length() > 0) {
            try {
                int1 = Integer.parseInt(kbdInput);
            }
            catch (NumberFormatException ex) {}
        }
        return int1;
    }
    
    private static void displayPrompt(final String str) {
        System.err.print(str + " : ");
    }
    
    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            System.load(new InstallPath().fullyQualifyFile("ntjavamain.dll"));
        }
    }
}
