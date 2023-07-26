// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import com.progress.common.util.PasswordString;

public interface WsaSecurityManager
{
    WsaUser authenticateApplicationUser(final String p0, final PasswordString p1) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException;
    
    WsaUser authenticateWsdlUser(final String p0, final PasswordString p1) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException;
    
    WsaUser authenticateAdminUser(final String p0, final PasswordString p1) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException;
    
    WsaUser authenticateAdminUser(final String p0, final PasswordString p1, final String[] p2) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException;
    
    String[] getRoleNames();
}
