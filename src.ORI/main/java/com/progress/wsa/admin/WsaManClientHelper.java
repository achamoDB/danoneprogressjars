// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.util.Enumeration;
import java.util.List;
import java.util.Collections;
import com.progress.wsa.tools.PropElement;
import java.util.Vector;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.ubroker.tools.wsa.WSRemoteCmdStatus;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.tools.wsa.WSRemoteCmdDescriptor;
import java.rmi.RemoteException;
import com.progress.wsa.tools.WsaLoginInfo;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.common.util.ICmdConst;

public class WsaManClientHelper implements ICmdConst
{
    String wsaName;
    String wsName;
    
    public WsaManClientHelper() {
        this.wsaName = "";
        this.wsName = "";
    }
    
    public String help(final IYodaRMI yodaRMI, final Object[] array) throws RemoteException {
        return this.help(yodaRMI, null, array);
    }
    
    public String help(final IYodaRMI yodaRMI, final WsaLoginInfo wsaLoginInfo, final Object[] array) throws RemoteException {
        final WSRemoteCmdDescriptor wsRemoteCmdDescriptor = new WSRemoteCmdDescriptor();
        try {
            this.wsaName = ((IChimeraHierarchy)yodaRMI).getDisplayName();
        }
        catch (Exception ex) {}
        switch ((int)array[0]) {
            case 240: {
                wsRemoteCmdDescriptor.makeDeployWSCmd(wsaLoginInfo, (String)array[1], (String)array[2]);
                final WSRemoteCmdStatus wsRemoteCmdStatus = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s = (String)wsRemoteCmdStatus.fetchDeployWSStatus();
                if (s != null) {
                    return s;
                }
                return "%%Failed:" + wsRemoteCmdStatus.getDetailStatusMsg();
            }
            case 250: {
                wsRemoteCmdDescriptor.makeUndeployWSCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus2 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus2.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus2.getDetailStatusMsg();
            }
            case 260: {
                wsRemoteCmdDescriptor.makeListWSCmd(wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus3 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s2 = (String)wsRemoteCmdStatus3.fetchListWSStatus();
                if (s2 != null) {
                    return s2;
                }
                return "%%Failed:" + wsRemoteCmdStatus3.getDetailStatusMsg();
            }
            case 310: {
                wsRemoteCmdDescriptor.makeGetRTStatsCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus4 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s3 = (String)wsRemoteCmdStatus4.fetchGetRTStatsStatus();
                if (s3 != null) {
                    return s3;
                }
                return "%%Failed:" + wsRemoteCmdStatus4.getDetailStatusMsg();
            }
            case 320: {
                wsRemoteCmdDescriptor.makeResetRTStatsCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus5 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus5.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus5.getDetailStatusMsg();
            }
            case 340: {
                wsRemoteCmdDescriptor.makeGetRTDefaultsCmd(wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus6 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final Vector vector = (Vector)wsRemoteCmdStatus6.fetchGetRTDefaultsStatus();
                if (vector != null) {
                    return this.convertV2S(vector);
                }
                return "%%Failed:" + wsRemoteCmdStatus6.getDetailStatusMsg();
            }
            case 350: {
                wsRemoteCmdDescriptor.makeUpdateOneRTDefaultCmd((String)array[1], (String)array[2], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus7 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus7.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus7.getDetailStatusMsg();
            }
            case 360: {
                wsRemoteCmdDescriptor.makeGetRTPropertiesCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus8 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final Vector vector2 = (Vector)wsRemoteCmdStatus8.fetchGetRTPropertiesStatus();
                if (vector2 != null) {
                    return this.convertV2S(vector2);
                }
                return "%%Failed:" + wsRemoteCmdStatus8.getDetailStatusMsg();
            }
            case 370: {
                wsRemoteCmdDescriptor.makeUpdateOneRTPropCmd((String)array[1], (String)array[2], (String)array[3], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus9 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus9.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus9.getDetailStatusMsg();
            }
            case 380: {
                wsRemoteCmdDescriptor.makeImportWSCmd(wsaLoginInfo, (String)array[1], (String)array[2]);
                final WSRemoteCmdStatus wsRemoteCmdStatus10 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s4 = (String)wsRemoteCmdStatus10.fetchImportWSStatus();
                if (s4 != null) {
                    return s4;
                }
                return "%%Failed:" + wsRemoteCmdStatus10.getDetailStatusMsg();
            }
            case 390: {
                wsRemoteCmdDescriptor.makeExportWSCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus11 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String fetchExportWSStatus = wsRemoteCmdStatus11.fetchExportWSStatus();
                if (fetchExportWSStatus != null) {
                    return fetchExportWSStatus;
                }
                return "%%Failed:" + wsRemoteCmdStatus11.getDetailStatusMsg();
            }
            case 400: {
                wsRemoteCmdDescriptor.makeEnableWSCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus12 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus12.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus12.getDetailStatusMsg();
            }
            case 410: {
                wsRemoteCmdDescriptor.makeDisableWSCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus13 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus13.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus13.getDetailStatusMsg();
            }
            case 420: {
                wsRemoteCmdDescriptor.makeResetRTDefaultsCmd(wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus14 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus14.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus14.getDetailStatusMsg();
            }
            case 430:
            case 450: {
                wsRemoteCmdDescriptor.makeUpdateWSCmd(wsaLoginInfo, (String)array[1], (String)array[2]);
                final WSRemoteCmdStatus wsRemoteCmdStatus15 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s5 = (String)wsRemoteCmdStatus15.fetchUpdateWSStatus();
                if (s5 != null) {
                    return s5;
                }
                return "%%Failed:" + wsRemoteCmdStatus15.getDetailStatusMsg();
            }
            case 460: {
                wsRemoteCmdDescriptor.makeResetRTPropertiesCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus16 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus16.getSuccessOrFailure()) {
                    return "Success";
                }
                return "%%Failed:" + wsRemoteCmdStatus16.getDetailStatusMsg();
            }
            case 500: {
                wsRemoteCmdDescriptor.makePingWsaCmd(wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus17 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                if (wsRemoteCmdStatus17.getSuccessOrFailure()) {
                    return wsRemoteCmdStatus17.getDetailStatusMsg();
                }
                return "%%Failed:" + wsRemoteCmdStatus17.getDetailStatusMsg();
            }
            case 10: {
                wsRemoteCmdDescriptor.makeQueryWSCmd((String)array[1], wsaLoginInfo);
                final WSRemoteCmdStatus wsRemoteCmdStatus18 = (WSRemoteCmdStatus)yodaRMI.doRemoteToolCmd(wsRemoteCmdDescriptor);
                final String s6 = (String)wsRemoteCmdStatus18.fetchQueryWSStatus();
                if (s6 != null) {
                    return s6;
                }
                return "%%Failed:" + wsRemoteCmdStatus18.getDetailStatusMsg();
            }
            default: {
                return "Unknown command. Cannot proceed.";
            }
        }
    }
    
    private String convertV2S(final Vector vector) {
        final StringBuffer sb = new StringBuffer();
        final Vector<String> list = (Vector<String>)new Vector<Comparable>(vector.size());
        final Enumeration<PropElement> elements = vector.elements();
        while (elements.hasMoreElements()) {
            final PropElement propElement = elements.nextElement();
            list.addElement("   " + (String)propElement.key() + ": " + (String)propElement.value() + "\n");
        }
        Collections.sort((List<Comparable>)list);
        for (int i = 0; i < list.size(); ++i) {
            sb.append((Object)list.elementAt(i));
        }
        return sb.toString();
    }
}
