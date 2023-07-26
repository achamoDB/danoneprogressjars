// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.io.PrintWriter;
import com.progress.common.property.PropertyManager;
import com.progress.common.ehnlog.LogHandler;
import com.progress.common.ehnlog.IAppLogger;
import java.io.InputStream;
import java.util.Map;

public interface WsaSOAPEngine
{
    void initialize(final String p0, final String p1, final String p2, final String p3, final String p4) throws WsaSOAPEngineException;
    
    void initialize(final Map p0) throws WsaSOAPEngineException;
    
    void shutdown() throws WsaSOAPEngineException;
    
    void incRefCount();
    
    WsaSOAPRequest createRequest(final InputStream p0, final int p1, final String p2) throws WsaSOAPEngineException;
    
    WsaSOAPRequest createRequest(final InputStream p0, final int p1, final String p2, final String p3, final String p4) throws WsaSOAPEngineException;
    
    IAppLogger getLogger();
    
    void setLogger(final LogHandler p0) throws WsaSOAPEngineException;
    
    void registerStatistics(final RuntimeStats p0);
    
    WsaStats getStatistics();
    
    WsaState getState();
    
    void setPropertyManager(final PropertyManager p0) throws WsaSOAPEngineException;
    
    Map getProperties() throws WsaSOAPEngineException;
    
    void setProperties(final Map p0) throws WsaSOAPEngineException;
    
    Object getProperty(final String p0) throws WsaSOAPEngineException;
    
    void setProperty(final String p0, final Object p1) throws WsaSOAPEngineException;
    
    WsaSecurityManager getSecurityManager();
    
    WsaServiceManager getServiceManager();
    
    void generateHTTPError(final PrintWriter p0, final int p1, final String p2) throws IOException;
    
    void getWSDLListing(final PrintWriter p0, final String p1, final WsaUser p2) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException;
    
    void getWSDLDocument(final PrintWriter p0, final String p1, final WsaUser p2) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException;
    
    long getWSDLDocumentLen(final String p0, final WsaUser p1) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException;
}
