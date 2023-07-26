// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.common.comsockagent.ServerComSockAgent;
import com.progress.agent.smdatabase.SMPlugIn;
import com.progress.common.text.UnquotedString;
import com.progress.agent.smdatabase.SMDatabase;
import com.progress.common.comsockagent.ComMsgAgent;
import com.progress.chimera.common.Tools;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.message.jpMsg;
import com.progress.common.comsockagent.ServerComSockAgentListener;

class AgentListener extends Thread implements IAgentAPI, ServerComSockAgentListener, jpMsg
{
    static AgentConnectionMgr idLookup;
    static ConnectionManagerLog theLog;
    
    AgentListener() {
        this.setName("Agent Plug-in ComSockAgent Listener");
    }
    
    public synchronized void clientConnect(final int value) {
        AgentListener.theLog.log(4, 7669630165411963175L, new Object[] { new Integer(value) });
    }
    
    void shutDown(final int value) {
        final IAgentDatabase agentDatabase = AgentListener.idLookup.get(new Integer(value));
        if (agentDatabase != null) {
            this.cleanup(agentDatabase, value);
        }
    }
    
    public synchronized void ignoreInput() {
        AgentListener.idLookup = null;
    }
    
    public synchronized void clientDisconnect(final int value) {
        AgentDatabase agentDatabase = null;
        if (AgentListener.idLookup != null) {
            agentDatabase = AgentListener.idLookup.get(new Integer(value));
        }
        if (agentDatabase != null) {
            if (AgentPlugIn.isAgentPlugInShuttingDown() || agentDatabase.isShuttingDown()) {
                AgentListener.theLog.log(3, 7669630165411963177L, new Object[] { agentDatabase.name() });
                agentDatabase.exiting();
            }
            else {
                try {
                    agentDatabase.handleCrash();
                    this.cleanup(agentDatabase, value);
                }
                catch (Throwable t) {
                    Tools.px(t);
                }
            }
            agentDatabase.stopQuery();
        }
    }
    
    synchronized void cleanup(final IAgentDatabase agentDatabase, final int value) {
        ((AgentDatabase)agentDatabase).clearIdValue();
        if (AgentListener.idLookup != null) {
            AgentListener.idLookup.remove(new Integer(value));
        }
    }
    
    public synchronized void messageReceived(final int n, final ComMsgAgent comMsgAgent) {
        if (comMsgAgent.getMsgType() == ComMsgAgent.AMDBPROTO) {
            this.messageReceivedV1(n, comMsgAgent);
            return;
        }
        AgentListener.theLog.log(5, 7669630165411963180L, new Object[] { comMsgAgent, new Integer(n) });
        if (AgentListener.idLookup == null) {
            return;
        }
        AgentDatabase agentDatabase = null;
        final int msgCode = comMsgAgent.getMsgCode();
        final String trim = comMsgAgent.getMsgName().trim();
        try {
            if (msgCode != 100 && msgCode != 117 && msgCode != 118) {
                agentDatabase = AgentListener.idLookup.get(new Integer(n));
                if (agentDatabase == null) {
                    AgentListener.theLog.logErr(2, "Database Agent client ID [" + n + "] not found.");
                    Tools.px(new Exception("Database Agent client ID [" + n + "] not found."));
                    AgentPlugIn.getServerComSockAgent().disconnect(n);
                    return;
                }
            }
            switch (msgCode) {
                case 100:
                case 117:
                case 118: {
                    this.handleRegRequest(n, comMsgAgent);
                    break;
                }
                case 103: {
                    this.handleRunningMessage(n, comMsgAgent, agentDatabase);
                    break;
                }
                case 501: {
                    break;
                }
                case 109: {
                    break;
                }
                case 105: {
                    break;
                }
                case 106: {
                    break;
                }
                case 1204: {
                    agentDatabase.setShuttingDown();
                    break;
                }
                case 1206: {
                    break;
                }
                case 1200: {
                    agentDatabase.setShuttingDown();
                    if (agentDatabase.isRemoteDb()) {
                        ((SMDatabase)agentDatabase.getDatabase()).handleStop();
                        break;
                    }
                    break;
                }
                case 1202: {
                    agentDatabase.setShuttingDown();
                    if (agentDatabase.isRemoteDb()) {
                        ((SMDatabase)agentDatabase.getDatabase()).handleCrash();
                        break;
                    }
                    agentDatabase.handleCrash();
                    break;
                }
                case 1101:
                case 1107:
                case 1113: {
                    agentDatabase.returnQueryResults(trim, comMsgAgent.getData());
                    AgentListener.theLog.log(5, "Query Ack: " + trim);
                    break;
                }
                case 1102:
                case 1108:
                case 1114:
                case 1136: {
                    agentDatabase.returnQueryResults(trim, null);
                    AgentListener.theLog.log(5, "Query Rejected: " + trim);
                    break;
                }
                case 509:
                case 513: {
                    break;
                }
                case 510:
                case 514: {
                    break;
                }
                case 1004:
                case 1006:
                case 1008:
                case 1010: {
                    if (agentDatabase.isRemoteDb()) {
                        int n2 = 4;
                        final String s = new String(comMsgAgent.getData());
                        final SMDatabase smDatabase = (SMDatabase)agentDatabase.getDatabase();
                        if (msgCode == 1004) {
                            n2 = 1;
                        }
                        else if (msgCode == 1006) {
                            n2 = 3;
                        }
                        else if (msgCode == 1008) {
                            n2 = 2;
                        }
                        smDatabase.setAuxProcessStatus(n2, s);
                        break;
                    }
                    break;
                }
                case 1012:
                case 1013:
                case 1015:
                case 1016:
                case 1018:
                case 1019:
                case 1208:
                case 1210:
                case 1212:
                case 1214:
                case 1216:
                case 1218:
                case 1220:
                case 1222: {
                    final String str = new String(comMsgAgent.getData());
                    AgentListener.theLog.log(4, trim);
                    AgentListener.theLog.log(4, "Query: " + trim + " Data: " + str);
                    break;
                }
                default: {
                    AgentListener.theLog.logErr(4, "Invalid message received: " + trim);
                    break;
                }
            }
        }
        catch (Throwable t) {
            AgentListener.theLog.logErr(2, "Error handling message from DB Agent");
            Tools.px(t, "Error handling message from DB Agent");
        }
    }
    
    private void sendLicenseConflictAlert() {
        try {
            final Object invoke = Class.forName("com.progress.isq.ipqos.Probe").getMethod("getProject", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            if (invoke != null) {
                final Class<?> forName = Class.forName("com.progress.isq.ipqos.resources.IResource");
                final Class<?> forName2 = Class.forName("com.progress.isq.ipqos.action.IAction");
                final Class<?> forName3 = Class.forName("com.progress.isq.ipqos.alert.Alert");
                forName3.getMethod("fire", (Class[])new Class[0]).invoke(forName3.getConstructor(forName, Integer.TYPE, forName2, forName2, Long.TYPE).newInstance(invoke, new Integer(1), null, null, new Long(7019985919163771293L)), new Object[0]);
            }
        }
        catch (Exception ex) {}
    }
    
    public void handleRegRequest(final int n, final ComMsgAgent comMsgAgent) throws Exception {
        AgentListener.theLog.log(4, 7669630165411963181L);
        AgentDatabase value = null;
        String s = "Reason unknown.";
        boolean b = false;
        String substring = null;
        String substring2 = null;
        String adminserverPort = null;
        final int msgCode = comMsgAgent.getMsgCode();
        final String string = new UnquotedString(new String(comMsgAgent.getData())).toString();
        String str;
        try {
            String s2 = "0";
            String schemaVersion = "102b";
            if (msgCode == 100) {
                final int index = string.indexOf(59);
                final int endIndex = (index >= 0) ? string.indexOf(59, index + 1) : -1;
                final String substring3 = string.substring(0, index);
                if (endIndex < 0) {
                    str = string.substring(index + 1);
                }
                else {
                    str = string.substring(index + 1, endIndex);
                    s2 = string.substring(endIndex + 1);
                }
                if (substring3.equalsIgnoreCase(AgentPlugIn.propertyFileName)) {
                    value = AgentDatabase.findAgentDatabase(str);
                }
            }
            else {
                final int index2 = string.indexOf(59);
                final int endIndex2 = (index2 >= 0) ? string.indexOf(59, index2 + 1) : -1;
                final int endIndex3 = (endIndex2 >= 0) ? string.indexOf(59, endIndex2 + 1) : -1;
                final int endIndex4 = (endIndex3 >= 0) ? string.indexOf(59, endIndex3 + 1) : -1;
                final int endIndex5 = (endIndex4 >= 0) ? string.indexOf(59, endIndex4 + 1) : -1;
                final int endIndex6 = (endIndex5 >= 0) ? string.indexOf(59, endIndex5 + 1) : -1;
                final int endIndex7 = (endIndex6 >= 0) ? string.indexOf(59, endIndex6 + 1) : -1;
                final String substring4 = string.substring(0, index2);
                final String substring5 = string.substring(index2 + 1, endIndex2);
                substring = string.substring(endIndex2 + 1, endIndex3);
                str = string.substring(endIndex3 + 1, endIndex4);
                substring2 = string.substring(endIndex4 + 1, endIndex5);
                if (endIndex6 < 0) {
                    adminserverPort = string.substring(endIndex5 + 1);
                }
                else {
                    adminserverPort = string.substring(endIndex5 + 1, endIndex6);
                }
                if (endIndex7 < 0) {
                    schemaVersion = string.substring(endIndex6 + 1);
                }
                else {
                    schemaVersion = string.substring(endIndex6 + 1, endIndex7);
                    s2 = string.substring(endIndex7 + 1);
                }
                final SMPlugIn value2 = SMPlugIn.get();
                if (value2 == null || !value2.isInitialized()) {
                    return;
                }
                synchronized (value2) {
                    value = AgentDatabase.findAgentDatabase(str, substring4, substring5, substring2.equals("1"));
                }
                if (value == null) {
                    s = "Remote Database configuration for " + str + " on " + substring4 + " " + substring5 + ":" + substring + " not found.";
                }
            }
            if (value != null) {
                final boolean equals = s2.equals("1");
                value.set64BitSchema(equals);
                boolean b2 = equals;
                if (schemaVersion.equals("101")) {
                    if (!equals) {
                        schemaVersion = "10";
                    }
                    b2 = true;
                }
                value.set64BitRecID(b2);
                value.setSchemaVersion(schemaVersion);
            }
            if (!value.is64BitSchema() && !value.getSchemaVersion().equals("9") && value.getSchemaVersion().compareTo("101") >= 0) {
                AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_REJ", "You need to update virtual system tables (VSTs).".getBytes()));
                value.setIdle();
                return;
            }
        }
        catch (StringIndexOutOfBoundsException ex) {
            AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_REJ", "Exception parsing registration message.".getBytes()));
            value.setIdle();
            return;
        }
        if (msgCode == 117 && value != null && !value.isRunning()) {
            final SMDatabase smDatabase = (SMDatabase)value.getDatabase();
            if (smDatabase != null) {
                final boolean equals2 = substring2.equals("1");
                smDatabase.setAdminserverPort(adminserverPort);
                smDatabase.setBrokerPort(substring);
                smDatabase.setFileSysCS(equals2);
            }
        }
        if (value != null && !value.isRunning()) {
            value.setInitializing();
            value.setIdValue(n);
            AgentListener.idLookup.put(new Integer(n), value);
            AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_ACK"));
            return;
        }
        if (value == null) {
            if (msgCode == 117 || msgCode == 118) {
                AgentListener.theLog.log(2, s);
            }
            else {
                s = "Database for " + str + " not found.";
                AgentListener.theLog.log(2, s);
            }
        }
        else {
            AgentListener.theLog.log(2, "DB Agent already running for " + str + ", registration request denied.");
            b = true;
            value = null;
        }
        AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_REJ", s.getBytes()));
        if (value != null) {
            value.setIdle();
        }
        if (value != null || b) {
            AgentPlugIn.getServerComSockAgent().disconnect(n);
        }
    }
    
    public void handleRunningMessage(final int n, final ComMsgAgent comMsgAgent, final AgentDatabase agentDatabase) throws Exception {
        SMDatabase smDatabase = null;
        final byte[] data = comMsgAgent.getData();
        final String s = new String(data);
        AgentListener.theLog.log(4, 7669630165411963189L);
        if (agentDatabase.isRemoteDb()) {
            smDatabase = (SMDatabase)agentDatabase.getDatabase();
        }
        if (data != null && data.length >= 4) {
            final String str = "         AI enabled : ";
            final String str2 = "Replication enabled : ";
            final String str3 = " Clustering enabled : ";
            final String str4 = "       Version 9 db : ";
            final String str5 = "               CPUs : ";
            agentDatabase.setStartedByMe(data[0] == 49);
            final boolean aiEnabled = data[1] == 49;
            AgentListener.theLog.log(4, str + aiEnabled);
            final boolean replEnabled = data[2] == 49;
            AgentListener.theLog.log(4, str2 + aiEnabled);
            final boolean b = data[3] == 49;
            AgentListener.theLog.log(4, str3 + b);
            if (data.length >= 5) {
                AgentListener.theLog.log(4, str4 + (data[4] == 49));
            }
            if (data.length > 5) {
                final int n2 = data.length - 5;
                final byte[] bytes = new byte[n2];
                System.arraycopy(data, 5, bytes, 0, n2);
                AgentListener.theLog.log(4, str5 + new String(bytes).trim());
            }
            if (agentDatabase.isRemoteDb()) {
                smDatabase.setAIEnabled(aiEnabled);
                smDatabase.setReplEnabled(replEnabled);
                smDatabase.setClustEnabled(b);
            }
        }
        else {
            AgentListener.theLog.log(4, "Invalid mnRunning message, status of  AI, Replication, and Clustering unknown.");
        }
        if (agentDatabase.isRemoteDb()) {
            smDatabase.handleRunning();
            final ServerComSockAgent serverComSockAgent = AgentPlugIn.getServerComSockAgent();
            serverComSockAgent.send(n, new ComMsgAgent("S_AIW_REQ"));
            serverComSockAgent.send(n, new ComMsgAgent("S_APW_REQ"));
            serverComSockAgent.send(n, new ComMsgAgent("S_BIW_REQ"));
            serverComSockAgent.send(n, new ComMsgAgent("S_WDOG_REQ"));
        }
        agentDatabase.setRunning();
        AgentPlugIn.get().getEventBroker().postEvent(agentDatabase.getStartupCompletedEvent());
    }
    
    public synchronized void messageReceivedV1(final int n, final ComMsgAgent comMsgAgent) {
        AgentListener.theLog.log(5, 7669630165411963180L, new Object[] { comMsgAgent, new Integer(n) });
        if (AgentListener.idLookup == null) {
            return;
        }
        AgentDatabase value = null;
        String s = "Reason unknown.";
        boolean b = false;
        try {
            final String trim = comMsgAgent.getMsgName().trim();
            if (trim.equals("REG_REQ") || trim.equals("REG_REQ2") || trim.equals("REG_REQ3")) {
                AgentListener.theLog.log(4, 7669630165411963181L);
                String substring = null;
                String substring2 = null;
                String adminserverPort = null;
                final String s2 = new String(comMsgAgent.getData());
                final String string = new UnquotedString(new String(comMsgAgent.getData())).toString();
                String str;
                try {
                    String substring3 = "9";
                    if (trim.equals("REG_REQ")) {
                        final int index = string.indexOf(59);
                        final String substring4 = string.substring(0, index);
                        str = string.substring(index + 1);
                        if (substring4.equalsIgnoreCase(AgentPlugIn.propertyFileName)) {
                            value = AgentDatabase.findAgentDatabase(str);
                        }
                    }
                    else {
                        final int index2 = string.indexOf(59);
                        final int index3 = string.indexOf(59, index2 + 1);
                        final int index4 = string.indexOf(59, index3 + 1);
                        final int index5 = string.indexOf(59, index4 + 1);
                        final int index6 = string.indexOf(59, index5 + 1);
                        final int index7 = string.indexOf(59, index6 + 1);
                        final String substring5 = string.substring(0, index2);
                        final String substring6 = string.substring(index2 + 1, index3);
                        substring = string.substring(index3 + 1, index4);
                        str = string.substring(index4 + 1, index5);
                        substring2 = string.substring(index5 + 1, index6);
                        if (index7 < 0) {
                            adminserverPort = string.substring(index6 + 1);
                        }
                        else {
                            adminserverPort = string.substring(index6 + 1, index7);
                            substring3 = string.substring(index7 + 1);
                        }
                        if (!SMPlugIn.get().isInitialized()) {
                            return;
                        }
                        synchronized (SMPlugIn.get()) {
                            value = AgentDatabase.findAgentDatabase(str, substring5, substring6, substring2.equals("1"));
                        }
                        if (value == null) {
                            s = "Remote Database configuration for " + str + " on " + substring5 + " " + substring6 + ":" + substring + " not found.";
                        }
                    }
                    if (value != null) {
                        value.set64BitSchema(false);
                        value.set64BitRecID(false);
                        value.setSchemaVersion(substring3);
                    }
                }
                catch (StringIndexOutOfBoundsException ex) {
                    AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_REJ", "Exception parsing registration message.".getBytes()));
                    value.setIdle();
                    return;
                }
                if ((trim.equals("REG_REQ2") || trim.equals("REG_REQ3")) && value != null && !value.isRunning()) {
                    final SMDatabase smDatabase = (SMDatabase)value.getDatabase();
                    if (smDatabase != null) {
                        final boolean equals = substring2.equals("1");
                        smDatabase.setAdminserverPort(adminserverPort);
                        smDatabase.setBrokerPort(substring);
                        smDatabase.setFileSysCS(equals);
                    }
                }
                if (value != null && !value.isRunning()) {
                    value.setInitializing();
                    value.setIdValue(n);
                    AgentListener.idLookup.put(new Integer(n), value);
                    AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_ACK"));
                    return;
                }
                if (value == null) {
                    if (trim.equals("REG_REQ2") || trim.equals("REG_REQ3")) {
                        AgentListener.theLog.log(2, s);
                    }
                    else {
                        s = "Database for " + str + " not found.";
                        AgentListener.theLog.log(2, s);
                    }
                }
                else {
                    AgentListener.theLog.log(2, "DB Agent already running for " + str + ", registration request denied.");
                    b = true;
                    value = null;
                }
                AgentPlugIn.getServerComSockAgent().send(n, new ComMsgAgent("REG_REJ", s.getBytes()));
                if (value != null) {
                    value.setIdle();
                }
                if (value != null || b) {
                    AgentPlugIn.getServerComSockAgent().disconnect(n);
                }
            }
            else {
                final AgentDatabase agentDatabase = AgentListener.idLookup.get(new Integer(n));
                if (agentDatabase == null) {
                    AgentListener.theLog.logErr(2, "Database Agent client ID [" + n + "] not found.");
                    Tools.px(new Exception("Database Agent client ID [" + n + "] not found."));
                    AgentPlugIn.getServerComSockAgent().disconnect(n);
                    return;
                }
                if (trim.equals("REG_CONF")) {
                    this.handleRunningMessage(n, comMsgAgent, agentDatabase);
                    return;
                }
                if (!trim.equals("SHUTD_N_ACK")) {
                    if (trim.equals("CONT_ACK")) {
                        try {
                            if (comMsgAgent.getData() != null) {
                                Integer.parseInt(new String(comMsgAgent.getData()));
                            }
                        }
                        catch (Throwable t2) {}
                    }
                    else if (!trim.equals("SHUTD_ACK")) {
                        if (!trim.equals("SHUTD_REJ")) {
                            if (trim.equals("SHUTD_DISC_NOTIF")) {
                                agentDatabase.setShuttingDown();
                            }
                            else if (!trim.equals("SHUTD_ERR_NOTIF")) {
                                if (trim.equals("SHUTD_N_NOTIF")) {
                                    agentDatabase.setShuttingDown();
                                    if (agentDatabase.isRemoteDb()) {
                                        ((SMDatabase)agentDatabase.getDatabase()).handleStop();
                                    }
                                }
                                else if (trim.equals("SHUTD_E_NOTIF")) {
                                    agentDatabase.setShuttingDown();
                                    if (agentDatabase.isRemoteDb()) {
                                        ((SMDatabase)agentDatabase.getDatabase()).handleCrash();
                                    }
                                }
                                else if (trim.equals("QRY_VST_TBL_ACK") || trim.equals("QRY_SYS_TBL_ACK") || trim.equals("QRY_VST_COL_ACK")) {
                                    agentDatabase.returnQueryResults(trim, comMsgAgent.getData());
                                    AgentListener.theLog.log(5, "Query Ack: " + trim);
                                }
                                else if (trim.equals("QRY_VST_COL_REJ") || trim.equals("QRY_SYS_TBL_REJ") || trim.equals("QRY_VST_TBL_REJ") || trim.equals("QRY_REJ_SCH_LOCK")) {
                                    agentDatabase.returnQueryResults(trim, null);
                                    AgentListener.theLog.log(5, "Query Rejected: " + trim);
                                }
                                else if (trim.equals("S_RPLS_ACK")) {
                                    AgentListener.theLog.log(5, "S_RPLS_ACK");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("S_RPLS_REJ")) {
                                    AgentListener.theLog.log(5, "S_RPLS_REJ");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("S_RPLA_ACK")) {
                                    AgentListener.theLog.log(5, "S_RPLA_ACK");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("S_RPLA_REJ")) {
                                    AgentListener.theLog.log(5, "S_RPLA_REJ");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("S_RPLCA_ACK")) {
                                    AgentListener.theLog.log(5, "S_RPLCA_ACK");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("S_RPLCA_REJ")) {
                                    AgentListener.theLog.log(5, "S_RPLCA_REJ");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_SN_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_SN_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_SE_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_SE_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_SD_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_SD_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_SI_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_SI_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_TA_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_TA_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_TM_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_TM_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_IR_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_IR_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (trim.equals("RPL_CL_NOTIF")) {
                                    AgentListener.theLog.log(5, "RPL_CL_NOTIF");
                                    AgentListener.theLog.log(5, "Query: " + trim + " Data: " + new String(comMsgAgent.getData()));
                                }
                                else if (!trim.equals("DISC_U_ACK")) {
                                    if (!trim.equals("DISC_S_ACK")) {
                                        if (!trim.equals("DISC_U_REJ")) {
                                            if (!trim.equals("DISC_S_REJ")) {
                                                if (trim.equals("S_AIW_ACK")) {
                                                    if (agentDatabase.isRemoteDb()) {
                                                        ((SMDatabase)agentDatabase.getDatabase()).setAuxProcessStatus(1, new String(comMsgAgent.getData()));
                                                    }
                                                }
                                                else if (trim.equals("S_APW_ACK")) {
                                                    if (agentDatabase.isRemoteDb()) {
                                                        final String s3 = new String(comMsgAgent.getData());
                                                        ((SMDatabase)agentDatabase.getDatabase()).setAuxProcessStatus(3, new String(comMsgAgent.getData()));
                                                    }
                                                }
                                                else if (trim.equals("S_BIW_ACK")) {
                                                    if (agentDatabase.isRemoteDb()) {
                                                        final String s4 = new String(comMsgAgent.getData());
                                                        ((SMDatabase)agentDatabase.getDatabase()).setAuxProcessStatus(2, new String(comMsgAgent.getData()));
                                                    }
                                                }
                                                else if (trim.equals("S_WDOG_ACK")) {
                                                    if (agentDatabase.isRemoteDb()) {
                                                        final String s5 = new String(comMsgAgent.getData());
                                                        ((SMDatabase)agentDatabase.getDatabase()).setAuxProcessStatus(4, new String(comMsgAgent.getData()));
                                                    }
                                                }
                                                else {
                                                    AgentListener.theLog.logErr(4, "Invalid message received: " + trim);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable t) {
            AgentListener.theLog.logErr(2, "Error handling message from DB Agent");
            Tools.px(t, "Error handling message from DB Agent");
        }
    }
    
    static {
        AgentListener.idLookup = AgentPlugIn.getAgentConnectionMgr();
        AgentListener.theLog = AgentPlugIn.theLog;
    }
}
