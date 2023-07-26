// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.nameserver.NameServer;
import com.progress.common.property.IPropertyManagerRemote;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

public class ToolRemoteCmdStatus implements Serializable, IToolCmdConst
{
    public int m_command;
    public int m_statusCode;
    public String m_detailErrorMsg;
    public Vector m_resultSet;
    
    public ToolRemoteCmdStatus() {
        this.m_command = 0;
        this.m_statusCode = 0;
        this.m_detailErrorMsg = null;
        this.m_resultSet = null;
        this.m_resultSet = new Vector();
    }
    
    public ToolRemoteCmdStatus(final int command) {
        this.m_command = 0;
        this.m_statusCode = 0;
        this.m_detailErrorMsg = null;
        this.m_resultSet = null;
        this.m_command = command;
        this.m_resultSet = new Vector();
    }
    
    public int getCommand() {
        return this.m_command;
    }
    
    public int getStatusCode() {
        return this.m_statusCode;
    }
    
    public String getDetailStatusMsg() {
        return this.m_detailErrorMsg;
    }
    
    public Enumeration getResultSet() {
        return this.m_resultSet.elements();
    }
    
    public void setCommand(final int command) {
        this.m_command = command;
    }
    
    public void setStatusCode(final int statusCode) {
        this.m_statusCode = statusCode;
    }
    
    public void setDetailStatusMsg(final String detailErrorMsg) {
        this.m_detailErrorMsg = detailErrorMsg;
    }
    
    public void addResultSet(final Object obj) {
        this.m_resultSet.addElement(obj);
    }
    
    public void addResultSet(final Object[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.addResultSet(array[i]);
        }
    }
    
    public void resetResultset() {
        if (this.m_resultSet.size() > 0) {
            this.m_resultSet = new Vector();
        }
    }
    
    public void setDetailErrMsg(final String s) {
        if (s == null) {
            this.setErrorStatus(-1);
        }
        else {
            this.setErrorStatus(-1, s);
        }
    }
    
    public void setSuccessOrFailureStatus(final boolean b) {
        if (b) {
            this.setSuccessStatus();
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setGetSvcCfgStatus(final char[] array) {
        if (array != null) {
            this.setSuccessStatus();
            this.addResultSet(array);
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setPingSvcStatus(final long value) {
        this.setSuccessStatus();
        this.addResultSet(new Long(value));
    }
    
    public void setGetPrefPropStatus(final char[] array) {
        if (array != null) {
            this.setSuccessStatus();
            this.addResultSet(array);
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setGetSummaryStatuslblStatus(final Enumeration enumeration) {
        this.addResultSet_setStatus(enumeration);
    }
    
    public void setGetSummaryStatusDataStatus(final Enumeration enumeration) {
        this.addResultSet_setStatus(enumeration);
    }
    
    public void setGetDetailStatusLblStatus(final Enumeration enumeration) {
        this.addResultSet_setStatus(enumeration);
    }
    
    public void setGetDetailStatusDataStatus(final Enumeration enumeration) {
        this.addResultSet_setStatus(enumeration);
    }
    
    public void setTrimSrvrByStatus(final int value) {
        if (value == 0) {
            this.setSuccessStatus();
            this.addResultSet(new Integer(value));
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setAddNewSrvrsStatus(final int value) {
        if (value == 0) {
            this.setSuccessStatus();
            this.addResultSet(new Integer(value));
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setGetNSSummaryStatus(final Object[][] array) {
        this.addResultSet_setStatus(array);
    }
    
    public void setGetNSDetailStatus(final Object[] array) {
        this.addResultSet_setStatus(array);
    }
    
    public void setValidateSaveCfgStatus(final CfgValidateErrs cfgValidateErrs) {
        this.addResultSet_setStatus(cfgValidateErrs);
    }
    
    public void setLoadGUISchemaStatus(final char[] array) {
        this.addResultSet_setStatus(array);
    }
    
    public void setLoadGUISchemaStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setGetGUISchemaPropFnStatus(final String[] array) {
        this.addResultSet_setStatus(array);
    }
    
    public void setFetchRPMStatus(final IPropertyManagerRemote propertyManagerRemote) {
        this.addResultSet_setStatus(propertyManagerRemote);
    }
    
    public void setNSInstRefStatus(final int value) {
        this.addResultSet_setStatus(new Integer(value));
    }
    
    public void setValidateValidOnePropStatus(final CfgValidateErrs cfgValidateErrs) {
        this.addResultSet_setStatus(cfgValidateErrs);
    }
    
    public void setNSLocPropStatus(final String s) {
        this.addResultSet_setStatus(s);
    }
    
    public char[] fetchGetSvcCfgStatus() {
        try {
            return this.getResultSet().nextElement();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public boolean fetchSaveSvcCfgStatus() {
        return this.getSuccessOrFailure();
    }
    
    public boolean fetchStartSvcStatus() {
        return this.getSuccessOrFailure();
    }
    
    public boolean fetchStopSvcStatus() {
        return this.getSuccessOrFailure();
    }
    
    public boolean fetchPingSvcStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement() > 0L;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public String[] fetchGetNSNamesStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public String[] fetchGetAdminRoleNamesStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public String[] fetchGetSSLAliasNamesStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public boolean fetchReplacePropertiesStatus() {
        return this.getSuccessOrFailure();
    }
    
    public char[] fetchGetPrefPropStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public boolean fetchPutPrefPropStatus() {
        return this.getSuccessOrFailure();
    }
    
    public char[] fetchLoadGUISchemaStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public String[] fetchGetGUISchemaPropFnStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public IPropertyManagerRemote fetchRPMStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public boolean fetchAddNewGuiToolStatus() {
        return this.getSuccessOrFailure();
    }
    
    public boolean fetchRemoveGuiToolStatus() {
        return this.getSuccessOrFailure();
    }
    
    public Enumeration fetchGetSummaryStatuslblStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Enumeration fetchGetSummaryStatusDataStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Enumeration fetchGetDetailStatusLblStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Enumeration fetchGetDetailStatusDataStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public int fetchTrimSrvrByStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public int fetchAddNewSrvrsStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public CfgValidateErrs fetchValidOnePropValStatus() {
        if (this.getSuccessOrFailure()) {
            return this.getResultSet().nextElement();
        }
        return new CfgValidateErrs();
    }
    
    public int fetchNSInstRefStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return 0;
    }
    
    public Object[][] fetchGetNSSummaryStatStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public NameServer.AppService[] fetchGetNSDetailStatStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public CfgValidateErrs fetchValidateSaveCfgStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public String fetchGetNSLocPropStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public void setSuccessStatus() {
        this.setStatusCode(1);
    }
    
    public void setErrorStatus(final int statusCode) {
        this.setStatusCode(statusCode);
    }
    
    public void setErrorStatus(final int statusCode, final String detailStatusMsg) {
        this.setStatusCode(statusCode);
        this.setDetailStatusMsg(detailStatusMsg);
    }
    
    public boolean getSuccessOrFailure() {
        return this.getStatusCode() == 1;
    }
    
    protected void addResultSet_setStatus(final Object o) {
        if (o != null) {
            this.setSuccessStatus();
            this.addResultSet(o);
        }
        else {
            this.setErrorStatus(-1);
        }
    }
}
