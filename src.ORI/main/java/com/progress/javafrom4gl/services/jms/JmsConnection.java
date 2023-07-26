// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.ConnectionFactory;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;
import com.progress.javafrom4gl.ServiceConnection;

public class JmsConnection implements ServiceConnection
{
    private Object serviceObject;
    private boolean iAMSecondConnection;
    private SessionContainer session;
    private String connectionId;
    private Log log;
    
    JmsConnection(final SessionContainer session) {
        this.log = ServiceRuntime.getLog();
        this.serviceObject = session.getMessageEventServer();
        this.iAMSecondConnection = true;
        this.session = session;
        this.connectionId = session.getConnectionID();
    }
    
    public String toString() {
        if (!this.iAMSecondConnection) {
            return this.session.toString();
        }
        return "";
    }
    
    JmsConnection(final SessionContainer sessionContainer, final ConnectionFactory connectionFactory, final String connectionId, final StartupParameters startupParameters, final boolean b) throws Exception {
        this.log = ServiceRuntime.getLog();
        sessionContainer.init(connectionFactory, connectionId, startupParameters, b);
        this.connectionId = connectionId;
        this.serviceObject = sessionContainer;
        this.iAMSecondConnection = false;
        this.session = sessionContainer;
    }
    
    public Object _getInitialObject() {
        return this.serviceObject;
    }
    
    public void _disconnect() throws Exception {
        this.session.closeSession(-1);
    }
    
    public String _getExportList() {
        return null;
    }
    
    public void _stop() {
        if (this.iAMSecondConnection) {
            try {
                this.session.shutdownMessageDelivery();
            }
            catch (Exception ex) {
                this.log.LogStackTrace(1, true, this.connectionId, ex);
                throw new Error(ex.getMessage());
            }
        }
    }
}
