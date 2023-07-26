// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import java.util.Enumeration;
import java.io.Serializable;
import com.progress.ubroker.util.ToolRemoteCmdStatus;

public class WSRemoteCmdStatus extends ToolRemoteCmdStatus implements Serializable
{
    public WSRemoteCmdStatus() {
    }
    
    public WSRemoteCmdStatus(final int n) {
        super(n);
    }
    
    public String fetchExportWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                final Enumeration resultSet = this.getResultSet();
                final StringBuffer sb = new StringBuffer();
                while (resultSet.hasMoreElements()) {
                    sb.append(resultSet.nextElement());
                }
                return sb.toString();
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public Object fetchUpdateWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchQueryWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchPingWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchPingWsaStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchGetRTStatsStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchGetRTDefaultsStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchGetRTPropertiesStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchListWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchDeployWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public Object fetchImportWSStatus() {
        try {
            if (this.getSuccessOrFailure()) {
                return this.getResultSet().nextElement();
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public void setExportWSStatus(final String[] array) {
        if (array != null) {
            this.setSuccessStatus();
            this.addResultSet(array);
        }
        else {
            this.setErrorStatus(-1);
        }
    }
    
    public void setGetRTPropertiesStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setGetRTDefaultsStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setGetRTStatsStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setPingWsaStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setQueryWSStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setListWSStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setDeployWSStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setUpdateWSStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
    
    public void setImportWSStatus(final Object o) {
        this.addResultSet_setStatus(o);
    }
}
