// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.util.PropFilename;
import com.progress.common.property.PropertyManager;
import java.util.Vector;
import com.progress.ubroker.util.PropGroupDescriptor;
import java.io.File;
import com.progress.common.util.Getopt;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.common.util.ICmdConst;
import com.progress.ubroker.util.IPropConst;

public class UBCfgClient implements IPropConst, ICmdConst, IBTMsgCodes
{
    private static final boolean DEBUG_TRACE = false;
    static final int OPT_BAD = 100;
    protected PropMgrUtils m_propMgr;
    protected String m_propFilePath;
    protected String m_personality;
    protected UBPropValidate m_propValidate;
    public static final long[] WS_HELP_MESSAGES;
    public static final long[] DEFAULT_HELP_MESSAGES;
    public static Object[] argList;
    
    public UBCfgClient() {
        this.m_propMgr = null;
        this.m_personality = "";
        this.m_propValidate = null;
    }
    
    private static void printUsage(final int n) {
        System.out.println(getHelpMessage(n));
        System.out.println("\n\n");
    }
    
    public static void main(String[] array) {
        String optArg = null;
        String optArg2 = null;
        int n = 0;
        int n2 = 0;
        int n3 = 2;
        final boolean b = false;
        final UBCfgClient ubCfgClient = new UBCfgClient();
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("name:", 50), new Getopt.GetoptList("i:", 50), new Getopt.GetoptList("f:", 120), new Getopt.GetoptList("propfile:", 120), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("h", 40), new Getopt.GetoptList("m", 130), new Getopt.GetoptList("messenger", 130), new Getopt.GetoptList("v", 140), new Getopt.GetoptList("validate", 140), new Getopt.GetoptList("t:", 110), new Getopt.GetoptList("z", 100), new Getopt.GetoptList("", 0) };
        System.out.print("\n");
        if (array != null && array.length <= 3) {
            if (array.length == 3) {
                if (array[2].length() == 0) {
                    array = new String[] { array[0], array[1] };
                }
            }
            else if (array.length == 2) {
                if (array[1].length() == 0) {
                    array = new String[] { array[0] };
                }
            }
            else if (array.length == 1 && array[0].length() == 0) {
                array = new String[0];
            }
        }
        final Getopt getopt = new Getopt(array);
        if (!b) {
            int opt;
            while ((opt = getopt.getOpt(array2)) != -1) {
                switch (opt) {
                    case 100: {
                        continue;
                    }
                    case 40: {
                        printUsage(n3);
                        System.exit(0);
                        continue;
                    }
                    case 110: {
                        ubCfgClient.m_personality = getopt.getOptArg();
                        if (ubCfgClient.m_personality.equals("AS")) {
                            n3 = 2;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("WS")) {
                            n3 = 1;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("NS")) {
                            n3 = 6;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("OD")) {
                            n3 = 4;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("OR")) {
                            n3 = 3;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("AD")) {
                            n3 = 8;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("AIA")) {
                            n3 = 9;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("MS")) {
                            n3 = 5;
                            continue;
                        }
                        if (ubCfgClient.m_personality.equals("WSA")) {
                            n3 = 10;
                            continue;
                        }
                        continue;
                    }
                    case 120: {
                        optArg2 = getopt.getOptArg();
                        continue;
                    }
                    case 63: {
                        UBCfgClient.argList = new Object[] { getopt.getOptArg() };
                        System.out.println(UBToolsMsg.getMsg(7094295313015382095L, UBCfgClient.argList) + "\n");
                        printUsage(n3);
                        System.exit(0);
                        continue;
                    }
                }
            }
        }
        else {
            n3 = 2;
        }
        final String defaultPropFileName = getDefaultPropFileName();
        try {
            try {
                if (optArg2 == null) {
                    optArg2 = defaultPropFileName;
                }
                if (new File(optArg2).isFile()) {
                    ubCfgClient.m_propMgr = new PropMgrUtils(optArg2, true, false);
                }
                else {
                    UBCfgClient.argList = new Object[] { optArg2 };
                    System.exit(1);
                }
            }
            catch (PropMgrUtils.LoadPropFileError loadPropFileError2) {
                UBCfgClient.argList = new Object[] { optArg2 };
                System.out.println(UBToolsMsg.getMsg(7094295313015382252L, UBCfgClient.argList) + "\n");
                System.exit(1);
            }
            catch (Exception ex) {
                UBCfgClient.argList = new Object[] { ex.getMessage() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382251L, UBCfgClient.argList) + "\n");
                System.exit(1);
            }
            ubCfgClient.m_propFilePath = ubCfgClient.m_propMgr.getPropFilePath();
            ubCfgClient.m_propValidate = new UBPropValidate(ubCfgClient.m_propMgr);
            try {
                ubCfgClient.m_propMgr.loadPropFile(ubCfgClient.m_propFilePath);
            }
            catch (PropMgrUtils.LoadPropFileError loadPropFileError) {
                UBCfgClient.argList = new Object[] { loadPropFileError.getMessage() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382252L, UBCfgClient.argList) + "\n");
            }
            catch (Exception ex2) {
                UBCfgClient.argList = new Object[] { ex2.toString() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382253L, UBCfgClient.argList) + "\n");
            }
        }
        catch (Exception ex3) {
            UBCfgClient.argList = new Object[] { ex3.toString() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382253L, UBCfgClient.argList) + "\n");
            ex3.printStackTrace();
        }
        final Getopt getopt2 = new Getopt(array);
        int opt2;
        while ((opt2 = getopt2.getOpt(array2)) != -1) {
            switch (opt2) {
                case 100: {
                    continue;
                }
                case 50: {
                    optArg = getopt2.getOptArg();
                    continue;
                }
                case 130: {
                    if (n3 == 1) {
                        n = 1;
                        continue;
                    }
                    UBCfgClient.argList = new Object[] { getopt2.getOptArg(), ubCfgClient.m_personality };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382259L, UBCfgClient.argList) + "\n");
                    printUsage(n3);
                    System.exit(0);
                    continue;
                }
            }
        }
        if (n == 1) {
            if (optArg == null) {
                n2 = 1;
            }
            else if (!ubCfgClient.m_propValidate.validateSvcName(ubCfgClient.m_propMgr.getMsngrServices(), optArg)) {
                UBCfgClient.argList = new Object[] { optArg };
                System.out.println(UBToolsMsg.getMsg(7094295313015382254L, UBCfgClient.argList) + "\n");
                System.exit(0);
            }
        }
        final String[] serviceList = ubCfgClient.getServiceList(n3);
        if (n2 == 1) {
            ubCfgClient.displayServiceInfo(ubCfgClient.m_propMgr.getMsngrServices());
        }
        else if (optArg == null) {
            switch (n3) {
                case 2: {
                    if (serviceList != null) {
                        ubCfgClient.displayServiceInfo(serviceList);
                        break;
                    }
                    break;
                }
                case 1: {
                    if (serviceList != null) {
                        ubCfgClient.displayServiceInfo(serviceList);
                        ubCfgClient.displayServiceInfo(ubCfgClient.m_propMgr.getMsngrServices());
                        break;
                    }
                    break;
                }
                case 6: {
                    if (serviceList != null) {
                        ubCfgClient.displayServiceInfo(serviceList);
                        break;
                    }
                    break;
                }
                default: {
                    if (serviceList != null) {
                        ubCfgClient.displayServiceInfo(serviceList);
                        break;
                    }
                    break;
                }
            }
        }
        else {
            ubCfgClient.displayServiceInfo(optArg);
        }
        System.exit(0);
    }
    
    private void displayServiceInfo(final String s) {
        if (this.m_propMgr != null) {
            try {
                final PropGroupDescriptor ubPersonStrForSvcName = this.m_propMgr.findUBPersonStrForSvcName(s);
                if (ubPersonStrForSvcName == null) {
                    UBCfgClient.argList = new Object[] { s, this.m_personality };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382255L, UBCfgClient.argList) + "\n");
                    return;
                }
                final String getfullPropSpec = ubPersonStrForSvcName.getfullPropSpec();
                String svcTypeStr = ubPersonStrForSvcName.getSvcTypeStr();
                if (svcTypeStr == null) {
                    svcTypeStr = "";
                }
                if (!svcTypeStr.equals(this.m_personality) && (!this.m_personality.equals("WS") || !svcTypeStr.equals("MSNGR"))) {
                    UBCfgClient.argList = new Object[] { s, this.m_personality };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382255L, UBCfgClient.argList) + "\n");
                    return;
                }
                final UBValidateObject ubValidateObject = new UBValidateObject(this.m_propMgr.getProperties(getfullPropSpec), this.m_propMgr.getPropertiesNoAncestor(getfullPropSpec), getfullPropSpec);
                this.printPropertyCollection(ubValidateObject);
                if (ubValidateObject != null) {
                    this.m_propValidate.valAllPropsInCollection(ubValidateObject);
                    final String[] valMessages = this.m_propValidate.getValMessages();
                    final String[] envMessages = this.m_propValidate.getEnvMessages();
                    if (valMessages != null) {
                        for (int valMessageCount = this.m_propValidate.getValMessageCount(), i = 0; i < valMessageCount; ++i) {
                            System.out.println(valMessages[i]);
                        }
                    }
                    if (envMessages != null && !this.m_propValidate.isEnvMessageEmpty()) {
                        System.out.println("Environment:");
                        for (int envMessagesCount = this.m_propValidate.getEnvMessagesCount(), j = 0; j < envMessagesCount; ++j) {
                            System.out.println(envMessages[j]);
                        }
                    }
                }
            }
            catch (Exception ex) {
                UBCfgClient.argList = new Object[] { ex.toString() };
                System.out.println(UBToolsMsg.getMsg(7094295313015382256L, UBCfgClient.argList) + "\n");
            }
        }
    }
    
    private void displayServiceInfo(final String[] array) {
        final Vector vector = new Vector();
        for (int i = 0; i < array.length; ++i) {
            try {
                final PropertyManager.PropertyCollection properties = this.m_propMgr.getProperties(array[i]);
                final PropertyManager.PropertyCollection propertiesNoAncestor = this.m_propMgr.getPropertiesNoAncestor(array[i]);
                if (properties == null || propertiesNoAncestor == null) {
                    UBCfgClient.argList = new Object[] { array[i], this.m_personality };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382255L, UBCfgClient.argList) + "\n");
                    return;
                }
                final UBValidateObject ubValidateObject = new UBValidateObject(properties, propertiesNoAncestor, array[i]);
                this.printPropertyCollection(ubValidateObject);
                this.m_propValidate.valAllPropsInCollection(ubValidateObject);
                final String[] valMessages = this.m_propValidate.getValMessages();
                if (valMessages != null) {
                    for (int j = 0; j < valMessages.length; ++j) {
                        System.out.println(valMessages[j]);
                    }
                }
                final String[] envMessages = this.m_propValidate.getEnvMessages();
                if (envMessages != null && !this.m_propValidate.isEnvMessageEmpty()) {
                    System.out.println("\nEnvironment:");
                    for (int length = envMessages.length, k = 0; k < length; ++k) {
                        System.out.println(envMessages[k]);
                    }
                }
            }
            catch (Exception ex) {
                UBCfgClient.argList = new Object[] { array[i], this.m_personality };
                System.out.println(UBToolsMsg.getMsg(7094295313015382255L, UBCfgClient.argList) + "\n");
            }
        }
    }
    
    private static String getDefaultPropFileName() {
        return PropFilename.getFullPath();
    }
    
    private static boolean isServiceOnNT() {
        return System.getProperty("os.name").indexOf("Windows") >= 0;
    }
    
    private static String getHelpMessage(final int n) {
        final StringBuffer sb = new StringBuffer();
        switch (n) {
            case 2: {
                final Object[] array = { "AppServer" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array) + "\n");
                sb.append("========================================================================\n");
                for (int i = 1; i < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++i) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[i], array) + "\n");
                }
                break;
            }
            case 1: {
                final Object[] array2 = { "WebSpeed Broker" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.WS_HELP_MESSAGES[0], array2) + "\n");
                sb.append("========================================================================\n");
                for (int j = 1; j < UBCfgClient.WS_HELP_MESSAGES.length; ++j) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.WS_HELP_MESSAGES[j], array2) + "\n");
                }
                break;
            }
            case 6: {
                final Object[] array3 = { "NameServer" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array3) + "\n");
                sb.append("========================================================================\n");
                for (int k = 1; k < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++k) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[k], array3) + "\n");
                }
                break;
            }
            case 3: {
                final Object[] array4 = { "Oracle Dataserver" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array4) + "\n");
                sb.append("========================================================================\n");
                for (int l = 1; l < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++l) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[l], array4) + "\n");
                }
                break;
            }
            case 4: {
                final Object[] array5 = { "ODBC Dataserver" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array5) + "\n");
                sb.append("========================================================================\n");
                for (int n2 = 1; n2 < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++n2) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[n2], array5) + "\n");
                }
                break;
            }
            case 5: {
                final Object[] array6 = { "MSS Dataserver" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array6) + "\n");
                sb.append("========================================================================\n");
                for (int n3 = 1; n3 < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++n3) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[n3], array6) + "\n");
                }
                break;
            }
            case 8: {
                final Object[] array7 = { "Adapter Broker" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array7) + "\n");
                sb.append("========================================================================\n");
                for (int n4 = 1; n4 < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++n4) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[n4], array7) + "\n");
                }
                break;
            }
            case 9: {
                final Object[] array8 = { "AppServer Internet Adapter Broker" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array8) + "\n");
                sb.append("========================================================================\n");
                for (int n5 = 1; n5 < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++n5) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[n5], array8) + "\n");
                }
                break;
            }
            case 10: {
                final Object[] array9 = { "Web Services Adapter" };
                sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[0], array9) + "\n");
                sb.append("========================================================================\n");
                for (int n6 = 1; n6 < UBCfgClient.DEFAULT_HELP_MESSAGES.length; ++n6) {
                    sb.append(UBToolsMsg.getMsgStripCode(UBCfgClient.DEFAULT_HELP_MESSAGES[n6], array9) + "\n");
                }
                break;
            }
        }
        return sb.toString();
    }
    
    private String[] getServiceList(final int n) {
        String[] array = null;
        switch (n) {
            case 2: {
                array = this.m_propMgr.getASServices();
                break;
            }
            case 1: {
                array = this.m_propMgr.getWSServices();
                break;
            }
            case 3: {
                array = this.m_propMgr.getORServices();
                break;
            }
            case 4: {
                array = this.m_propMgr.getODServices();
                break;
            }
            case 6: {
                array = this.m_propMgr.getNSServices();
                break;
            }
            case 8: {
                array = this.m_propMgr.getAdapterServices();
                break;
            }
            case 9: {
                array = this.m_propMgr.getAiaServices();
                break;
            }
            case 5: {
                array = this.m_propMgr.getMSSServices();
                break;
            }
            case 10: {
                array = this.m_propMgr.getWsaServices();
                break;
            }
        }
        return array;
    }
    
    private void printPropertyCollection(final UBValidateObject ubValidateObject) {
        final String fullSvcPath = ubValidateObject.m_fullSvcPath;
        System.out.println("\n\n");
        System.out.println(UBToolsMsg.getMsgStripCode(7094295313015382342L, new Object[] { fullSvcPath }));
        System.out.println("======================================================================================\n");
        final PropMgrUtils propMgr = this.m_propMgr;
        PropertyManager.PropertyCollection collection;
        if (PropMgrUtils.getSvcTypeStr(ubValidateObject.m_fullSvcPath) == "NS" && this.m_propValidate.isRemote(ubValidateObject)) {
            collection = ubValidateObject.getNoAncestor();
        }
        else {
            collection = ubValidateObject.m_propColl;
        }
        if (collection != null) {
            while (collection.hasMoreElements()) {
                final Object nextElement = collection.nextElement();
                final String name = ((PropertyManager.Property)nextElement).getName();
                final String value = ((PropertyManager.Property)nextElement).getValue();
                if (value != null && value.length() > 0) {
                    String expandedFileName = null;
                    if (this.isExpandableName(value)) {
                        expandedFileName = this.getExpandedFileName(value, name, fullSvcPath);
                    }
                    if (expandedFileName != null) {
                        System.out.println(name + ": " + value + "     " + "(" + expandedFileName + ")");
                    }
                    else {
                        System.out.println(name + ": " + value);
                    }
                }
            }
        }
        System.out.println("--------------------------------------------------------------------------------------\n");
        ubValidateObject.m_propColl.reset();
    }
    
    private String getExpandedFileName(final String s, final String s2, final String s3) {
        final String expandedPropertyValue = this.m_propMgr.getExpandedPropertyValue(s2, s3);
        if (expandedPropertyValue == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015381964L, new Object[] { s2 }));
        }
        return expandedPropertyValue;
    }
    
    private boolean isExpandableName(final String s) {
        boolean b = false;
        if (s.indexOf(64) != -1 || s.indexOf(36) != -1) {
            b = true;
        }
        return b;
    }
    
    static {
        WS_HELP_MESSAGES = new long[] { 7094295313015382213L, 7094295313015382201L, 7094295313015382203L, 7094295313015382214L, 7094295313015382217L, 7094295313015382218L, 7094295313015382215L, 7094295313015382216L };
        DEFAULT_HELP_MESSAGES = new long[] { 7094295313015382213L, 7094295313015382201L, 7094295313015382203L, 7094295313015382214L, 7094295313015382215L, 7094295313015382216L };
    }
}
