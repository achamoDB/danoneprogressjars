// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.util.Date;
import com.progress.wsa.admin.AppRuntimeStats;
import java.io.Serializable;

public class StatusInfo implements Serializable
{
    protected int m_requests;
    protected int m_faults;
    protected int m_activeRequests;
    protected int m_objectNotFound;
    protected int m_clientError;
    protected int m_providerError;
    protected int m_connectError;
    protected int m_4glClientError;
    protected int m_4glServerError;
    protected int m_4glAppError;
    protected int m_nameServerError;
    protected int m_objectPoolFull;
    protected int m_objectPoolExpired;
    protected int m_objectPoolBusy;
    protected int m_objectPoolDisconnect;
    protected int m_appserverConnections;
    protected int m_appObjects;
    protected int m_subappObjects;
    protected int m_procObjects;
    protected long m_creationTime;
    protected long m_startTime;
    
    public StatusInfo() {
        this.m_requests = 0;
        this.m_faults = 0;
        this.m_activeRequests = 0;
        this.m_objectNotFound = 0;
        this.m_clientError = 0;
        this.m_providerError = 0;
        this.m_connectError = 0;
        this.m_4glClientError = 0;
        this.m_4glServerError = 0;
        this.m_4glAppError = 0;
        this.m_nameServerError = 0;
        this.m_objectPoolFull = 0;
        this.m_objectPoolExpired = 0;
        this.m_objectPoolBusy = 0;
        this.m_objectPoolDisconnect = 0;
        this.m_appserverConnections = 0;
        this.m_appObjects = 0;
        this.m_subappObjects = 0;
        this.m_procObjects = 0;
        this.m_creationTime = 0L;
        this.m_startTime = 0L;
    }
    
    public StatusInfo(final AppRuntimeStats appRuntimeStats) {
        this.m_requests = 0;
        this.m_faults = 0;
        this.m_activeRequests = 0;
        this.m_objectNotFound = 0;
        this.m_clientError = 0;
        this.m_providerError = 0;
        this.m_connectError = 0;
        this.m_4glClientError = 0;
        this.m_4glServerError = 0;
        this.m_4glAppError = 0;
        this.m_nameServerError = 0;
        this.m_objectPoolFull = 0;
        this.m_objectPoolExpired = 0;
        this.m_objectPoolBusy = 0;
        this.m_objectPoolDisconnect = 0;
        this.m_appserverConnections = 0;
        this.m_appObjects = 0;
        this.m_subappObjects = 0;
        this.m_procObjects = 0;
        this.m_creationTime = 0L;
        this.m_startTime = 0L;
        this.m_requests = appRuntimeStats.getCounter(0);
        this.m_faults = appRuntimeStats.getCounter(1);
        this.m_activeRequests = appRuntimeStats.getCounter(2);
        this.m_objectNotFound = appRuntimeStats.getCounter(3);
        this.m_clientError = appRuntimeStats.getCounter(4);
        this.m_providerError = appRuntimeStats.getCounter(5);
        this.m_connectError = appRuntimeStats.getCounter(6);
        this.m_4glClientError = appRuntimeStats.getCounter(7);
        this.m_4glServerError = appRuntimeStats.getCounter(8);
        this.m_4glAppError = appRuntimeStats.getCounter(9);
        this.m_nameServerError = appRuntimeStats.getCounter(10);
        this.m_objectPoolFull = appRuntimeStats.getCounter(11);
        this.m_objectPoolExpired = appRuntimeStats.getCounter(12);
        this.m_appserverConnections = 0;
        this.m_appObjects = 0;
        this.m_subappObjects = 0;
        this.m_procObjects = 0;
        this.m_creationTime = appRuntimeStats.creationTime();
        this.m_startTime = appRuntimeStats.lastResetTime();
    }
    
    public int getRequests() {
        return this.m_requests;
    }
    
    public void setRequests(final int requests) {
        this.m_requests = requests;
    }
    
    public int getFaults() {
        return this.m_faults;
    }
    
    public void setFaults(final int faults) {
        this.m_faults = faults;
    }
    
    public int getActiveRequests() {
        return this.m_activeRequests;
    }
    
    public void setActiveRequests(final int activeRequests) {
        this.m_activeRequests = activeRequests;
    }
    
    public int getObjectNotFound() {
        return this.m_objectNotFound;
    }
    
    public void setObjectNotFound(final int objectNotFound) {
        this.m_objectNotFound = objectNotFound;
    }
    
    public int getClientError() {
        return this.m_clientError;
    }
    
    public void setClientError(final int clientError) {
        this.m_clientError = clientError;
    }
    
    public int getProviderError() {
        return this.m_providerError;
    }
    
    public void setProviderError(final int providerError) {
        this.m_providerError = providerError;
    }
    
    public int getConnectError() {
        return this.m_connectError;
    }
    
    public void setConnectError(final int connectError) {
        this.m_connectError = connectError;
    }
    
    public int getClientError4GL() {
        return this.m_4glClientError;
    }
    
    public void setClientError4GL(final int 4glClientError) {
        this.m_4glClientError = 4glClientError;
    }
    
    public int getServerError4GL() {
        return this.m_4glServerError;
    }
    
    public void setServerError4GL(final int 4glServerError) {
        this.m_4glServerError = 4glServerError;
    }
    
    public int getAppError4GL() {
        return this.m_4glAppError;
    }
    
    public void setAppError4GL(final int 4glAppError) {
        this.m_4glAppError = 4glAppError;
    }
    
    public int getNameServerError() {
        return this.m_nameServerError;
    }
    
    public void setNameServerError(final int nameServerError) {
        this.m_nameServerError = nameServerError;
    }
    
    public int getObjectPoolFull() {
        return this.m_objectPoolFull;
    }
    
    public void setObjectPoolFull(final int objectPoolFull) {
        this.m_objectPoolFull = objectPoolFull;
    }
    
    public int getObjectPoolExpired() {
        return this.m_objectPoolExpired;
    }
    
    public void setObjectPoolExpired(final int objectPoolExpired) {
        this.m_objectPoolExpired = objectPoolExpired;
    }
    
    public int getAppserverConnections() {
        return this.m_appserverConnections;
    }
    
    public void setAppserverConnections(final int appserverConnections) {
        this.m_appserverConnections = appserverConnections;
    }
    
    public int getAppObjects() {
        return this.m_appObjects;
    }
    
    public void setAppObjects(final int appObjects) {
        this.m_appObjects = appObjects;
    }
    
    public int getSubappObjects() {
        return this.m_subappObjects;
    }
    
    public void setSubappObjects(final int subappObjects) {
        this.m_subappObjects = subappObjects;
    }
    
    public int getProcObjects() {
        return this.m_procObjects;
    }
    
    public void setProcObjects(final int procObjects) {
        this.m_procObjects = procObjects;
    }
    
    public Date getCreationTime() {
        return new Date(this.m_creationTime);
    }
    
    public void setCreationTime(final Date date) {
        this.m_creationTime = date.getTime();
    }
    
    public Date getStartTime() {
        return new Date(this.m_startTime);
    }
    
    public void setStartTime(final Date date) {
        this.m_startTime = date.getTime();
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        final int i = this.m_clientError + this.m_connectError + this.m_objectPoolFull + this.m_objectPoolExpired + this.m_providerError + this.m_4glClientError + this.m_4glAppError + this.m_4glServerError + this.m_nameServerError;
        sb.append("   Creation Time: " + this.getCreationTime());
        sb.append("\n   Start Time   : " + this.getStartTime() + "\n");
        sb.append("\n   Number of Requests             : " + this.m_requests);
        sb.append("\n   Number of Active Requests      : " + this.m_activeRequests);
        sb.append("\n   Number of SOAP Faults          : " + this.m_faults);
        sb.append("\n   Number of Objects Not Found    : " + this.m_objectNotFound);
        sb.append("\n   Number of Errors               : " + i);
        sb.append("\n   Number of AppServer Connections: " + this.m_appserverConnections);
        sb.append("\n   Number of Proxy AppObjects     : " + this.m_appObjects);
        sb.append("\n   Number of Proxy SubAppObjects  : " + this.m_subappObjects);
        sb.append("\n   Number of Proxy ProcObjects    : " + this.m_procObjects);
        if (i > 0) {
            sb.append("\n    Number of Client Errors                : " + this.m_clientError);
            sb.append("\n    Number of Connect Errors               : " + this.m_connectError);
            sb.append("\n    Number of Provider Errors              : " + this.m_providerError);
            sb.append("\n    Number of 4GL Client Errors            : " + this.m_4glClientError);
            sb.append("\n    Number of 4GL Server Errors            : " + this.m_4glAppError);
            sb.append("\n    Number of 4GL App Errors               : " + this.m_4glServerError);
            sb.append("\n    Number of NameServer Errors            : " + this.m_nameServerError);
            sb.append("\n    Number of Object Pool Full Errors      : " + this.m_objectPoolFull);
            sb.append("\n    Number of Object Pool Expired Errors   : " + this.m_objectPoolExpired);
        }
        return sb.toString();
    }
    
    public void copyContentFrom(final StatusInfo statusInfo) {
        this.m_requests = statusInfo.getRequests();
        this.m_faults = statusInfo.getFaults();
        this.m_activeRequests = statusInfo.getActiveRequests();
        this.m_objectNotFound = statusInfo.getObjectNotFound();
        this.m_clientError = statusInfo.getClientError();
        this.m_providerError = statusInfo.getProviderError();
        this.m_connectError = statusInfo.getConnectError();
        this.m_4glClientError = statusInfo.getClientError4GL();
        this.m_4glServerError = statusInfo.getServerError4GL();
        this.m_4glAppError = statusInfo.getAppError4GL();
        this.m_nameServerError = statusInfo.getNameServerError();
        this.m_objectPoolFull = statusInfo.getObjectPoolFull();
        this.m_objectPoolExpired = statusInfo.getObjectPoolExpired();
        this.m_appserverConnections = statusInfo.getAppserverConnections();
        this.m_appObjects = statusInfo.getAppObjects();
        this.m_subappObjects = statusInfo.getSubappObjects();
        this.m_procObjects = statusInfo.getProcObjects();
        this.m_creationTime = statusInfo.getCreationTime().getTime();
        this.m_startTime = statusInfo.getStartTime().getTime();
    }
}
