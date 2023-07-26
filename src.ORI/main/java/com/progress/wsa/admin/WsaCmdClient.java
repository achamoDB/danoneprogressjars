// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import com.progress.wsa.WsaConstants;
import com.progress.common.util.Getopt;
import java.util.Enumeration;
import com.progress.wsa.tools.WsaStatusInfo;
import java.util.Hashtable;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.QueryInfo;
import java.io.IOException;
import org.apache.soap.rpc.Response;
import com.progress.wsa.tools.ListInfo;
import com.progress.ubroker.util.Logger;
import java.util.Properties;
import java.util.Vector;
import org.apache.soap.rpc.Parameter;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.common.util.ICmdConst;
import com.progress.ubroker.util.IPropConst;
import com.progress.ubroker.tools.RemoteCommandClient;

public class WsaCmdClient extends RemoteCommandClient implements IPropConst, ICmdConst, IBTMsgCodes
{
    public static Object[] argList;
    private static final int HELP_MESSAGE = 50;
    private static final int MISSING_WSA_URL = 51;
    private static final int MISSING_ARGS = 52;
    
    public static void printUsage(final int n) {
        switch (n) {
            case 51: {
                System.out.println("Missing -wsaURL parameter\n");
                break;
            }
            case 52: {
                System.out.println(UBToolsMsg.getMsg(7094295313015382097L));
                break;
            }
        }
        System.out.println(getHelpMessage());
    }
    
    public static String getHelpMessage() {
        final Object[] array = { "Web Services Adapter" };
        final StringBuffer sb = new StringBuffer();
        sb.append("Usage for Web Services Adapter\n");
        sb.append("========================================================================\n");
        sb.append("\nUsage:  wsdeploy -deploy [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("-wsm                                   Location of WSM file\n");
        sb.append("\n\nUsage:  wsdeploy -undeploy [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("-app                                   Friendly Name or Target URI of the\n");
        sb.append("                                       Web Services application\n");
        sb.append("\n\nUsage:  wsdeploy -list [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("\n\nUsage:  wsdeploy -query [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("-app                                   Friendly Name or Target URI of the\n");
        sb.append("                                       Web Services application\n");
        sb.append("\n\nUsage:  wsdeploy -status [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("-app                                   Friendly Name or Target URI of the\n");
        sb.append("                                       Web Services application\n");
        sb.append("\n\nUsage:  wsdeploy -reset [options]\n");
        sb.append("where \"options\" include:\n\n");
        sb.append("-help or -h                            help\n");
        sb.append("-wsaURL                                URL of WSA instance\n");
        sb.append("-app                                   Friendly Name or Target URI of the\n");
        sb.append("                                       Web Services application\n");
        return sb.toString();
    }
    
    public void deploy(final String str, final String s, final WSAD wsad, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("WSAD", (Class)wsad.getClass(), (Object)wsad, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscdeploy", vector);
        }
        catch (Exception ex3) {
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Successfully deployed Web Service " + invokeAdminMethod.getReturnValue().getValue());
        }
    }
    
    public void undeploy(final String str, final String s, final String s2, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscundeploy", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to undeploy service " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Undeployed " + s);
            if (s2 != null) {
                final AppContainer appContainer = (AppContainer)invokeAdminMethod.getReturnValue().getValue();
                try {
                    appContainer.saveWSDFile(s2);
                }
                catch (IOException ex4) {
                    System.out.println("Unable to save wsd file to: " + s2);
                    ex4.printStackTrace();
                }
                System.out.println("Saved wsd file to: " + s2);
            }
        }
    }
    
    public ListInfo[] list(final String s, final boolean b) throws Exception {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        ListInfo[] array = null;
        System.out.println("Connecting to WSA at:  " + s);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(s, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("psclist", null);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to list Web Services");
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            array = (ListInfo[])invokeAdminMethod.getReturnValue().getValue();
            if (array == null) {
                System.out.println("There are No deployed Web Services at " + s + "\n");
            }
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return array;
    }
    
    public QueryInfo query(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        QueryInfo queryInfo = null;
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscquery", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to query");
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            queryInfo = (QueryInfo)invokeAdminMethod.getReturnValue().getValue();
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return queryInfo;
    }
    
    public StatusInfo getStats(final String str, final String str2, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)str2, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        StatusInfo statusInfo = null;
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("appstatus", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to get the status of " + str2);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            statusInfo = (StatusInfo)invokeAdminMethod.getReturnValue().getValue();
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return statusInfo;
    }
    
    public void resetStats(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetappstatus", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to reset the status of " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Reset the status of " + s);
        }
    }
    
    public void resetWsaStats(final String str, final String s, final boolean b) throws Exception {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetwsastatus", null);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to reset the status of " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Reset the status of " + s);
        }
    }
    
    public void enable(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("enableApp", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to enable " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Enabled Web Service application " + s);
        }
    }
    
    public void disable(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("disableApp", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to disable " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Disabled Web Service application " + s);
        }
    }
    
    public boolean setProperties(final String str, final String s, final Hashtable hashtable, final String key, final String s2, final boolean b) throws Exception {
        boolean b2 = false;
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Hashtable<String, Object> hashtable2 = new Hashtable<String, Object>();
        final Object convertValue = this.convertValue(hashtable, key, s2);
        if (convertValue != null) {
            hashtable2.put(key, convertValue);
            final Parameter obj2 = new Parameter("properties", Hashtable.class, (Object)hashtable2, "http://schemas.xmlsoap.org/soap/encoding/");
            final Vector<Parameter> vector = new Vector<Parameter>();
            vector.addElement(obj);
            vector.addElement(obj2);
            final WsaAdminClient wsaAdminClient = new WsaAdminClient();
            System.out.println("Connecting to WSA at:  " + str);
            try {
                wsaAdminClient.init(null, null, 0, 0);
            }
            catch (Exception ex) {
                System.out.println("Initialization error");
                throw ex;
            }
            System.out.println("Searching for WSA instance");
            try {
                wsaAdminClient.connect(str, null, null);
            }
            catch (Exception ex2) {
                System.out.println("Unable to connect to WSA");
                throw ex2;
            }
            Response invokeAdminMethod;
            try {
                invokeAdminMethod = wsaAdminClient.invokeAdminMethod("setRuntimeProperties", vector);
            }
            catch (Exception ex3) {
                throw ex3;
            }
            wsaAdminClient.disconnect();
            if (invokeAdminMethod.generatedFault()) {
                b2 = false;
                System.out.println(invokeAdminMethod.getFault().getFaultString());
                if (b) {
                    System.out.println("\nThe entire SOAP Fault:");
                    System.out.println(invokeAdminMethod.getFault());
                }
            }
            else {
                b2 = true;
            }
        }
        return b2;
    }
    
    public Hashtable getProperties(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        Hashtable hashtable = null;
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("getRuntimeProperties", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to get Runtime Props");
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            hashtable = (Hashtable)invokeAdminMethod.getReturnValue().getValue();
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return hashtable;
    }
    
    public void resetProperties(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetRuntimeProperties", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to reset the runtime properties");
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Reset the runtime properties");
        }
    }
    
    public String pingWSA(final String str, final boolean b) throws Exception {
        _PingResponse pingResponse = null;
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            final String connect = wsaAdminClient.connect(str, null, null);
            pingResponse = new _PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {}
        return pingResponse.toString();
    }
    
    public void importApp(final String str, final AppContainer appContainer, final boolean b) throws Exception {
        final Parameter obj = new Parameter("AppContainer", (Class)appContainer.getClass(), (Object)appContainer, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        vector.addElement(obj);
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("importApp", vector);
        }
        catch (Exception ex3) {
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Successfully imported Web Service " + invokeAdminMethod.getReturnValue().getValue());
        }
    }
    
    public AppContainer exportApp(final String str, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        AppContainer appContainer = null;
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("exportApp", vector);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to export");
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            appContainer = (AppContainer)invokeAdminMethod.getReturnValue().getValue();
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return appContainer;
    }
    
    public void update(final String str, final WSAD wsad, final String s, final boolean b) throws Exception {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("WSAD", (Class)wsad.getClass(), (Object)wsad, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        System.out.println("Connecting to WSA at:  " + str);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(str, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("update", vector);
        }
        catch (Exception ex3) {
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (invokeAdminMethod.generatedFault()) {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        else {
            System.out.println("Successfully updated Web Service " + invokeAdminMethod.getReturnValue().getValue());
        }
    }
    
    public WsaStatusInfo wsaStats(final String s, final boolean b) throws Exception {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        WsaStatusInfo wsaStatusInfo = null;
        System.out.println("Connecting to WSA at:  " + s);
        try {
            wsaAdminClient.init(null, null, 0, 0);
        }
        catch (Exception ex) {
            System.out.println("Initialization error");
            throw ex;
        }
        System.out.println("Searching for WSA instance");
        try {
            wsaAdminClient.connect(s, null, null);
        }
        catch (Exception ex2) {
            System.out.println("Unable to connect to WSA");
            throw ex2;
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("wsastatus", null);
        }
        catch (Exception ex3) {
            System.out.println("Error trying to get the status of WSA at " + s);
            throw ex3;
        }
        wsaAdminClient.disconnect();
        if (!invokeAdminMethod.generatedFault()) {
            wsaStatusInfo = (WsaStatusInfo)invokeAdminMethod.getReturnValue().getValue();
        }
        else {
            System.out.println(invokeAdminMethod.getFault().getFaultString());
            if (b) {
                System.out.println("\nThe entire SOAP Fault:");
                System.out.println(invokeAdminMethod.getFault());
            }
        }
        return wsaStatusInfo;
    }
    
    public String runtimePropsStr(final Hashtable hashtable, final boolean b) {
        final StringBuffer sb = new StringBuffer();
        if (b) {
            hashtable.remove("runtimePropertyVersion");
        }
        final Enumeration<K> keys = (Enumeration<K>)hashtable.keys();
        while (keys.hasMoreElements()) {
            final K nextElement = keys.nextElement();
            sb.append("  " + nextElement + ":  " + hashtable.get(nextElement) + "\n");
        }
        return sb.toString();
    }
    
    private Object convertValue(final Hashtable hashtable, final String s, final String s2) {
        Object o = null;
        Object value = null;
        try {
            value = hashtable.get(s);
        }
        catch (Exception ex) {}
        if (value == null) {
            System.out.println("Invalid property name: " + s);
        }
        else if (value.getClass().getName().equals("java.lang.Integer")) {
            try {
                o = new Integer(s2);
            }
            catch (Exception ex2) {
                System.out.println("Invalid value type. -value should be an integer");
            }
        }
        else if (value.getClass().getName().equals("java.lang.Long")) {
            try {
                o = new Long(s2);
            }
            catch (Exception ex3) {
                System.out.println("Invalid value type. -value should be an long");
            }
        }
        else if (value.getClass().getName().equals("java.lang.String")) {
            o = s2;
        }
        else if (value.getClass().getName().equals("java.lang.Boolean")) {
            try {
                o = new Boolean(s2);
            }
            catch (Exception ex4) {
                System.out.println("Invalid value type. -value should be an boolean");
            }
        }
        else {
            System.out.println("The wrong class type???");
        }
        return o;
    }
    
    public static void main(final String[] array) {
        boolean b = false;
        final WsaCmdClient wsaCmdClient = new WsaCmdClient();
        String optArg = null;
        String optArg2 = null;
        String optArg3 = null;
        String optArg4 = null;
        String optArg5 = null;
        String optArg6 = null;
        String optArg7 = null;
        String optArg8 = null;
        WSAD wsad = null;
        AppContainer loadWSDFile = null;
        System.out.print("\n");
        int n = -1;
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("deploy", 240), new Getopt.GetoptList("undeploy", 250), new Getopt.GetoptList("query", 10), new Getopt.GetoptList("q", 10), new Getopt.GetoptList("list", 260), new Getopt.GetoptList("wsm:", 280), new Getopt.GetoptList("wsaURL:", 300), new Getopt.GetoptList("app:", 270), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("h", 40), new Getopt.GetoptList("g", 230), new Getopt.GetoptList("getstats", 310), new Getopt.GetoptList("resetStats", 320), new Getopt.GetoptList("enable", 400), new Getopt.GetoptList("disable", 410), new Getopt.GetoptList("setprops", 370), new Getopt.GetoptList("getprops", 360), new Getopt.GetoptList("prop:", 120), new Getopt.GetoptList("value:", 330), new Getopt.GetoptList("import", 380), new Getopt.GetoptList("export", 390), new Getopt.GetoptList("wsd:", 440), new Getopt.GetoptList("update", 450), new Getopt.GetoptList("getdefaults", 340), new Getopt.GetoptList("setdefaults", 350), new Getopt.GetoptList("resetdefaults", 420), new Getopt.GetoptList("test", 430), new Getopt.GetoptList("namespace:", 470), new Getopt.GetoptList("encoding:", 480), new Getopt.GetoptList("resetprops", 460), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 40: {
                    printUsage(50);
                    System.exit(0);
                    continue;
                }
                case 240: {
                    n = 240;
                    continue;
                }
                case 250: {
                    n = 250;
                    continue;
                }
                case 260: {
                    n = 260;
                    continue;
                }
                case 10: {
                    n = 10;
                    continue;
                }
                case 310: {
                    n = 310;
                    continue;
                }
                case 320: {
                    n = 320;
                    continue;
                }
                case 420: {
                    n = 420;
                    continue;
                }
                case 400: {
                    n = 400;
                    continue;
                }
                case 410: {
                    n = 410;
                    continue;
                }
                case 370: {
                    n = 370;
                    continue;
                }
                case 360: {
                    n = 360;
                    continue;
                }
                case 380: {
                    n = 380;
                    continue;
                }
                case 390: {
                    n = 390;
                    continue;
                }
                case 450: {
                    n = 450;
                    continue;
                }
                case 340: {
                    n = 340;
                    continue;
                }
                case 350: {
                    n = 350;
                    continue;
                }
                case 430: {
                    n = 430;
                    continue;
                }
                case 460: {
                    n = 460;
                    continue;
                }
                case 280: {
                    optArg2 = getopt.getOptArg();
                    continue;
                }
                case 300: {
                    optArg = getopt.getOptArg();
                    continue;
                }
                case 270: {
                    optArg3 = getopt.getOptArg();
                    continue;
                }
                case 120: {
                    optArg4 = getopt.getOptArg();
                    continue;
                }
                case 330: {
                    optArg5 = getopt.getOptArg();
                    continue;
                }
                case 440: {
                    optArg6 = getopt.getOptArg();
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
                case 230: {
                    b = true;
                    continue;
                }
                case 63: {
                    WsaCmdClient.argList = new Object[] { getopt.getOptArg() };
                    System.out.println(UBToolsMsg.getMsg(7094295313015382095L, WsaCmdClient.argList));
                    printUsage(50);
                    System.exit(1);
                    continue;
                }
            }
        }
        if (optArg == null) {
            printUsage(51);
            System.exit(0);
        }
        String str = optArg3;
        switch (n) {
            case 240: {
                if (optArg2 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    wsad = WSAD.loadWSMFile(System.getProperty("Install.Dir"), optArg2);
                }
                catch (IOException ex20) {}
                if (wsad == null) {
                    System.out.println("Unable to read wsm file: " + optArg2);
                    return;
                }
                try {
                    if (optArg7 != null) {
                        wsad.setWebServiceNamespace(optArg7);
                    }
                    if (optArg8 != null) {
                        try {
                            final int intValue = Integer.valueOf(optArg8);
                            if (intValue < 1 || intValue > WsaConstants.WSA_SERVICE_ENCODING.length) {
                                System.out.println("Invalid encoding style number: " + optArg8);
                                System.out.println("Supported encoding styles are:");
                                return;
                            }
                            wsad.setEncodingStyle(intValue);
                        }
                        catch (NumberFormatException ex21) {
                            System.out.println("Invalid encoding style number: " + optArg8);
                            System.out.println("Supported encoding styles are:");
                            return;
                        }
                    }
                    wsaCmdClient.deploy(optArg, optArg3, wsad, b);
                }
                catch (Exception ex) {
                    System.out.println("Error trying to deploy wsm file " + optArg2);
                    if (b) {
                        System.out.println("");
                        ex.printStackTrace();
                    }
                }
                break;
            }
            case 250: {
                if (optArg3 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    wsaCmdClient.undeploy(optArg, optArg3, optArg6, b);
                }
                catch (Exception ex2) {
                    if (b) {
                        System.out.println("");
                        ex2.printStackTrace();
                    }
                }
                break;
            }
            case 260: {
                try {
                    final ListInfo[] list = wsaCmdClient.list(optArg, b);
                    if (list != null && list.length != 0) {
                        System.out.println("List of Web Services at " + optArg + "\n");
                        for (int i = 0; i < list.length; ++i) {
                            System.out.println(list[i] + "\n");
                        }
                        System.out.println("\n");
                    }
                }
                catch (Exception ex3) {
                    if (b) {
                        System.out.println("");
                        ex3.printStackTrace();
                    }
                }
                break;
            }
            case 10: {
                if (optArg3 == null) {
                    try {
                        final String pingWSA = wsaCmdClient.pingWSA(optArg, b);
                        if (pingWSA != null) {
                            System.out.println(pingWSA);
                        }
                        else {
                            System.out.println("WSA is not running");
                        }
                    }
                    catch (Exception ex4) {
                        System.out.println("WSA status unknown");
                        if (b) {
                            System.out.println("");
                            ex4.printStackTrace();
                        }
                    }
                    break;
                }
                try {
                    final QueryInfo query = wsaCmdClient.query(optArg, optArg3, b);
                    if (query != null) {
                        System.out.println("Results of querying " + query);
                    }
                }
                catch (Exception ex5) {
                    if (b) {
                        System.out.println("");
                        ex5.printStackTrace();
                    }
                }
                break;
            }
            case 430: {
                if (optArg3 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    final QueryInfo query2 = wsaCmdClient.query(optArg, optArg3, b);
                    if (query2 != null) {
                        System.out.println("Status of " + optArg3 + " is " + query2.getListInfo().getStatus());
                    }
                    else {
                        System.out.println("Status of " + optArg3 + " is " + "UNKNOWN");
                    }
                }
                catch (Exception ex6) {
                    System.out.println("Status of " + optArg3 + " is " + "UNKNOWN");
                    if (b) {
                        System.out.println("");
                        ex6.printStackTrace();
                    }
                }
                break;
            }
            case 310: {
                try {
                    if (optArg3 == null) {
                        final String str2 = "WSA";
                        final WsaStatusInfo wsaStats = wsaCmdClient.wsaStats(optArg, b);
                        if (wsaStats != null) {
                            System.out.println("Status of " + str2);
                            System.out.println(wsaStats);
                        }
                    }
                    else {
                        final String str3 = optArg3;
                        final StatusInfo stats = wsaCmdClient.getStats(optArg, optArg3, b);
                        if (stats != null) {
                            System.out.println("Status of " + str3);
                            System.out.println(stats);
                        }
                    }
                }
                catch (Exception ex7) {
                    if (b) {
                        System.out.println("");
                        ex7.printStackTrace();
                    }
                }
                break;
            }
            case 320: {
                try {
                    if (optArg3 == null) {
                        wsaCmdClient.resetWsaStats(optArg, "WSA", b);
                    }
                    else {
                        wsaCmdClient.resetStats(optArg, optArg3, b);
                    }
                }
                catch (Exception ex8) {
                    if (b) {
                        System.out.println("");
                        ex8.printStackTrace();
                    }
                }
                break;
            }
            case 400: {
                if (optArg3 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    wsaCmdClient.enable(optArg, optArg3, b);
                }
                catch (Exception ex9) {
                    System.out.println("Error trying to enable: " + optArg3);
                    if (b) {
                        System.out.println("");
                        ex9.printStackTrace();
                    }
                }
                break;
            }
            case 410: {
                if (optArg3 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    wsaCmdClient.disable(optArg, optArg3, b);
                }
                catch (Exception ex10) {
                    System.out.println("Error trying to disable: " + optArg3);
                    if (b) {
                        System.out.println("");
                        ex10.printStackTrace();
                    }
                }
                break;
            }
            case 370: {
                if (optArg4 == null || optArg5 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    if (optArg3 == null) {
                        optArg3 = "urn:services-progress-com:wsa-admin:0002";
                        str = "WSA";
                    }
                    if (wsaCmdClient.setProperties(optArg, optArg3, wsaCmdClient.getProperties(optArg, optArg3, b), optArg4, optArg5, b)) {
                        System.out.println("For " + str + " successfully set " + optArg4 + " to " + optArg5);
                    }
                }
                catch (Exception ex11) {
                    System.out.println("Error trying to set property: " + optArg4 + " to value: " + optArg5 + " for " + str);
                    if (b) {
                        System.out.println("");
                        ex11.printStackTrace();
                    }
                }
                break;
            }
            case 460: {
                try {
                    if (optArg3 == null) {
                        printUsage(52);
                        System.exit(0);
                    }
                    wsaCmdClient.resetProperties(optArg, optArg3, b);
                }
                catch (Exception ex12) {
                    if (b) {
                        System.out.println("");
                        ex12.printStackTrace();
                    }
                }
                break;
            }
            case 360: {
                try {
                    boolean b2 = true;
                    if (optArg3 == null) {
                        optArg3 = "urn:services-progress-com:wsa-admin:0002";
                        b2 = false;
                        str = "WSA";
                    }
                    final Hashtable properties = wsaCmdClient.getProperties(optArg, optArg3, b);
                    if (properties != null) {
                        System.out.println(str + "'s Runtime Properties:\n" + wsaCmdClient.runtimePropsStr(properties, b2));
                    }
                }
                catch (Exception ex13) {
                    if (b) {
                        System.out.println("");
                        ex13.printStackTrace();
                    }
                }
                break;
            }
            case 350: {
                if (optArg4 == null || optArg5 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    if (wsaCmdClient.setProperties(optArg, "urn:services-progress-com:wsa-default:0001", wsaCmdClient.getProperties(optArg, "urn:services-progress-com:wsa-default:0001", b), optArg4, optArg5, b)) {
                        System.out.println("Successfully set default property " + optArg4 + " to " + optArg5);
                    }
                }
                catch (Exception ex14) {
                    System.out.println("Error trying to set default property: " + optArg4 + " to value: " + optArg5);
                    if (b) {
                        System.out.println("");
                        ex14.printStackTrace();
                    }
                }
                break;
            }
            case 340: {
                try {
                    final Hashtable properties2 = wsaCmdClient.getProperties(optArg, "urn:services-progress-com:wsa-default:0001", b);
                    if (properties2 != null) {
                        System.out.println("Default Runtime Properties:\n" + wsaCmdClient.runtimePropsStr(properties2, true));
                    }
                }
                catch (Exception ex15) {
                    if (b) {
                        System.out.println("");
                        ex15.printStackTrace();
                    }
                }
                break;
            }
            case 420: {
                try {
                    wsaCmdClient.resetProperties(optArg, "urn:services-progress-com:wsa-default:0001", b);
                }
                catch (Exception ex16) {
                    if (b) {
                        System.out.println("");
                        ex16.printStackTrace();
                    }
                }
                break;
            }
            case 380: {
                if (optArg6 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    loadWSDFile = AppContainer.loadWSDFile(System.getProperty("Install.Dir"), optArg6);
                }
                catch (IOException ex22) {}
                if (loadWSDFile == null) {
                    System.out.println("Unable to read wsd file: " + optArg6);
                    return;
                }
                try {
                    if (optArg3 != null) {
                        loadWSDFile.setFriendlyName(optArg3);
                    }
                    if (optArg7 != null) {
                        loadWSDFile.getWSAD().setWebServiceNamespace(optArg7);
                    }
                    if (optArg8 != null) {
                        final int intValue2 = Integer.valueOf(optArg8);
                        if (intValue2 < 1 || intValue2 > WsaConstants.WSA_SERVICE_ENCODING.length) {
                            System.out.println("Invalid encoding style number: " + optArg8);
                            System.out.println("Supported encoding styles are:");
                            return;
                        }
                        loadWSDFile.getWSAD().setEncodingStyle(intValue2);
                    }
                    wsaCmdClient.importApp(optArg, loadWSDFile, b);
                }
                catch (NumberFormatException ex23) {
                    System.out.println("Invalid encoding style number: " + optArg8);
                    System.out.println("Supported encoding styles are:");
                    System.exit(1);
                }
                catch (Exception ex17) {
                    System.out.println("Error trying to import wsd file " + optArg6);
                    if (b) {
                        System.out.println("");
                        ex17.printStackTrace();
                    }
                }
                break;
            }
            case 390: {
                if (optArg3 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    final AppContainer exportApp = wsaCmdClient.exportApp(optArg, optArg3, b);
                    if (exportApp != null) {
                        final String str4 = (optArg6 != null) ? optArg6 : (exportApp.getFriendlyName() + ".wsd");
                        exportApp.saveWSDFile(str4);
                        System.out.println("Exported wsd file to " + str4);
                    }
                }
                catch (Exception ex18) {
                    if (b) {
                        System.out.println("");
                        ex18.printStackTrace();
                    }
                }
                break;
            }
            case 450: {
                if (optArg2 == null) {
                    printUsage(52);
                    System.exit(0);
                }
                try {
                    wsad = WSAD.loadWSMFile(System.getProperty("Install.Dir"), optArg2);
                }
                catch (IOException ex24) {}
                if (wsad == null) {
                    System.out.println("Unable to read wsm file: " + optArg2);
                    return;
                }
                try {
                    if (optArg7 != null) {
                        wsad.setWebServiceNamespace(optArg7);
                    }
                    if (optArg8 != null) {
                        try {
                            final int intValue3 = Integer.valueOf(optArg8);
                            if (intValue3 < 1 || intValue3 > WsaConstants.WSA_SERVICE_ENCODING.length) {
                                System.out.println("Invalid encoding style number: " + optArg8);
                                System.out.println("Supported encoding styles are:");
                                System.exit(1);
                            }
                            wsad.setEncodingStyle(intValue3);
                        }
                        catch (NumberFormatException ex25) {
                            System.out.println("Invalid encoding style number: " + optArg8);
                            System.out.println("Supported encoding styles are:");
                            System.exit(1);
                        }
                    }
                    wsaCmdClient.update(optArg, wsad, optArg3, b);
                }
                catch (Exception ex19) {
                    System.out.println("Error trying to update " + optArg3 + " with wsm file " + optArg2);
                    if (b) {
                        System.out.println("");
                        ex19.printStackTrace();
                    }
                }
                break;
            }
            default: {
                printUsage(52);
                break;
            }
        }
        System.exit(0);
    }
    
    protected class _PingResponse
    {
        private String m_status;
        private String m_wsaName;
        private int m_adminEnabled;
        private int m_webAppEnabled;
        private int m_wsdlEnabled;
        
        protected _PingResponse() {
            this.m_status = null;
            this.m_wsaName = null;
            this.m_adminEnabled = 0;
            this.m_webAppEnabled = 0;
            this.m_wsdlEnabled = 0;
        }
        
        public String getStatus() {
            return this.m_status;
        }
        
        public void setStatus(final String status) {
            this.m_status = status;
        }
        
        public String getWsaName() {
            return this.m_wsaName;
        }
        
        public void setWsaName(final String wsaName) {
            this.m_wsaName = wsaName;
        }
        
        public int getAdminEnabled() {
            return this.m_adminEnabled;
        }
        
        public void setAdminEnabled(final int adminEnabled) {
            this.m_adminEnabled = adminEnabled;
        }
        
        public int getWebAppEnabled() {
            return this.m_webAppEnabled;
        }
        
        public void setWebAppEnabled(final int webAppEnabled) {
            this.m_webAppEnabled = webAppEnabled;
        }
        
        public int getWSDLEnabled() {
            return this.m_wsdlEnabled;
        }
        
        public void setWSDLEnabled(final int wsdlEnabled) {
            this.m_wsdlEnabled = wsdlEnabled;
        }
        
        public void convertMsg(final String s) {
            final int n = s.indexOf("Status:") + 7;
            final String substring = s.substring(n, s.indexOf("<", n) - 2);
            final int index = substring.indexOf(":");
            final int lastIndex = substring.lastIndexOf(":");
            this.m_wsaName = substring.substring(0, index);
            this.m_status = substring.substring(index + 1, lastIndex);
            final String substring2 = substring.substring(lastIndex + 1);
            this.m_adminEnabled = Integer.valueOf(substring2.substring(0, 1));
            this.m_webAppEnabled = Integer.valueOf(substring2.substring(1, 2));
            this.m_wsdlEnabled = Integer.valueOf(substring2.substring(2, 3));
        }
        
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append("Web Service Adapter " + this.m_wsaName + " is " + (this.m_status.equals("OK") ? "running" : "not running"));
            sb.append("\n  adminEnabled  = " + this.m_adminEnabled);
            sb.append("\n  webAppEnabled = " + this.m_webAppEnabled);
            sb.append("\n  wsdlEnabled   = " + this.m_wsdlEnabled);
            return sb.toString();
        }
    }
}
