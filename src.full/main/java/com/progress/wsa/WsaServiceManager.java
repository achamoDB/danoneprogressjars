// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.admin.AppContainer;
import com.progress.wsa.tools.ListInfo;
import com.progress.wsa.admin.WSAD;
import com.progress.wsa.tools.QueryInfo;

public interface WsaServiceManager
{
    QueryInfo pscquery(final String p0) throws WsaSOAPException;
    
    ListInfo pscdeploy(final String p0, final WSAD p1) throws WsaSOAPException;
    
    AppContainer pscundeploy(final String p0) throws WsaSOAPException;
    
    ListInfo[] psclist() throws WsaSOAPException;
    
    StatusInfo appstatus(final String p0) throws WsaSOAPException;
    
    void resetappstatus(final String p0) throws WsaSOAPException;
    
    Hashtable getRuntimeProperties(final String p0) throws WsaSOAPException;
    
    void setRuntimeProperties(final String p0, final Hashtable p1) throws WsaSOAPException;
    
    boolean enableApp(final String p0) throws WsaSOAPException;
    
    boolean disableApp(final String p0) throws WsaSOAPException;
    
    void resetRuntimeProperties(final String p0) throws WsaSOAPException;
    
    void generateNoWSDLList(final Writer p0) throws IOException;
}
