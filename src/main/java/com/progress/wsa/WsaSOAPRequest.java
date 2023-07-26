// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import com.progress.common.util.PasswordString;
import java.io.IOException;
import java.util.Vector;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import com.progress.auth.PscUser;
import java.io.InputStream;

public interface WsaSOAPRequest
{
    String requestID();
    
    InputStream getInputStream();
    
    void setInputStream(final InputStream p0);
    
    PscUser getUserObject();
    
    void setUserObject(final PscUser p0);
    
    String getWebServiceURLPath();
    
    void setWebServiceURLPath(final String p0);
    
    String getClientIPAddress();
    
    void setClientIPAddress(final String p0);
    
    boolean getBuiltInService();
    
    void setBuiltInService(final boolean p0);
    
    Object getOption(final String p0) throws WsaSOAPEngineException;
    
    void setOption(final String p0, final Object p1) throws WsaSOAPEngineException;
    
    WsaSOAPResponse execute() throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException;
    
    WsaSOAPResponse generateFault(final String p0, final String p1, final Vector p2) throws IOException, WsaSOAPEngineException;
    
    void finish();
    
    int getLength();
    
    String getType();
    
    String getUser();
    
    PasswordString getPassword();
    
    void setPassword(final PasswordString p0);
    
    void setUser(final String p0);
    
    String[] getRoleNames();
    
    void setRoleNames(final String[] p0);
    
    void setActionalManifest(final String p0);
}
