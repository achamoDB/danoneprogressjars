// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.auth;

import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.PasswordCallback;
import java.io.IOException;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.Callback;
import java.util.Iterator;
import javax.security.auth.login.LoginContext;
import java.lang.reflect.Method;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.io.PrintStream;
import java.util.HashSet;
import javax.security.auth.Subject;
import com.progress.common.util.PasswordString;
import javax.security.auth.callback.CallbackHandler;
import java.security.Principal;

public class PscUser implements Principal, CallbackHandler
{
    protected String m_userName;
    protected String m_loginContext;
    protected PasswordString m_pw;
    protected Subject m_userSubject;
    protected HashSet m_userRoles;
    protected long m_lastAccess;
    protected boolean m_debug;
    protected PrintStream m_debugStream;
    private boolean useWorkaround;
    
    public PscUser(final String userName, final String loginContext) {
        this.m_loginContext = null;
        this.m_userSubject = null;
        this.m_userRoles = null;
        this.m_lastAccess = System.currentTimeMillis();
        this.m_debug = false;
        this.m_debugStream = System.out;
        this.useWorkaround = false;
        this.m_userName = userName;
        this.m_loginContext = loginContext;
        this.m_lastAccess = System.currentTimeMillis();
        if (null == this.m_loginContext) {
            this.m_loginContext = "DEFAULT";
        }
        if (!this.isLocalOnlyMode()) {
            this.useWorkaround = true;
        }
    }
    
    public PscUser(final String userName, final String s, final String loginContext) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        this.m_loginContext = null;
        this.m_userSubject = null;
        this.m_userRoles = null;
        this.m_lastAccess = System.currentTimeMillis();
        this.m_debug = false;
        this.m_debugStream = System.out;
        this.useWorkaround = false;
        this.m_userName = userName;
        this.m_loginContext = loginContext;
        this.m_lastAccess = System.currentTimeMillis();
        if (!this.isLocalOnlyMode()) {
            this.useWorkaround = true;
        }
        if (null == this.m_loginContext) {
            this.m_loginContext = "DEFAULT";
        }
        if (null != s && this.authenticate(s)) {
            this.m_pw = new PasswordString(s);
        }
    }
    
    protected PscUser() {
        this.m_loginContext = null;
        this.m_userSubject = null;
        this.m_userRoles = null;
        this.m_lastAccess = System.currentTimeMillis();
        this.m_debug = false;
        this.m_debugStream = System.out;
        this.useWorkaround = false;
    }
    
    private boolean isLocalOnlyMode() {
        final Boolean b = new Boolean(true);
        Boolean b2;
        try {
            Method method = null;
            final Object[] args = new Object[0];
            final Class[] parameterTypes = new Class[0];
            if (method == null) {
                method = Class.forName("com.progress.isq.ipqos.resources.ComponentSurrogateUtil").getMethod("isLocalOnlyMode", (Class<?>[])parameterTypes);
            }
            final Object invoke = method.invoke(null, args);
            if (invoke != null && invoke instanceof Boolean) {
                b2 = (Boolean)invoke;
            }
            else {
                b2 = new Boolean(true);
            }
        }
        catch (Exception ex) {
            b2 = new Boolean(true);
        }
        return b2 == null || b2;
    }
    
    public boolean equals(final Object o) {
        return o instanceof Principal && ((Principal)o).getName().equals(this.getName());
    }
    
    public String getName() {
        return this.m_userName;
    }
    
    public String toString() {
        return this.m_userName;
    }
    
    public long lastAccessed() {
        return this.m_lastAccess;
    }
    
    public Subject getUserSubject() {
        return this.m_userSubject;
    }
    
    public void logout() throws LoginException {
        if (null != this.m_userSubject) {
            try {
                new LoginContext(this.m_loginContext, this.m_userSubject, this).logout();
            }
            catch (Exception ex) {
                throw new LoginException("General Exception: " + ex.toString());
            }
        }
        this.m_userSubject = null;
        this.m_userRoles = null;
        this.m_pw = null;
    }
    
    public String[] getUserRoles() {
        if (null != this.m_userRoles) {
            return (String[])this.m_userRoles.toArray(new String[0]);
        }
        return new String[0];
    }
    
    public synchronized boolean authenticate(final String s) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        this.m_lastAccess = System.currentTimeMillis();
        boolean b;
        if (null != this.m_userSubject) {
            if (!this.m_pw.test(s)) {
                throw new LoginException("Invalid user password");
            }
            b = true;
        }
        else {
            this.m_pw = new PasswordString(s);
            try {
                final LoginContext loginContext = new LoginContext(this.m_loginContext, this);
                loginContext.login();
                this.m_userSubject = loginContext.getSubject();
                this.m_userRoles = new HashSet();
                for (final UserGroupPrincipal next : this.m_userSubject.getPrincipals()) {
                    if (this.useWorkaround) {
                        final Class<? extends UserGroupPrincipal> class1 = next.getClass();
                        if (class1.getName().equalsIgnoreCase("com.progress.auth.UserGroupPrincipal")) {
                            final Method method = class1.getMethod("getName", (Class[])new Class[0]);
                            if (method != null) {
                                this.m_userRoles.add(method.invoke(next, (Object[])null));
                            }
                        }
                    }
                    if (next instanceof UserGroupPrincipal) {
                        this.m_userRoles.add(next.getName());
                    }
                }
                b = true;
            }
            catch (FailedLoginException ex) {
                throw ex;
            }
            catch (CredentialExpiredException ex2) {
                throw ex2;
            }
            catch (AccountExpiredException ex3) {
                throw ex3;
            }
            catch (Exception ex4) {
                this.m_pw = null;
                this.m_userSubject = null;
                this.m_userRoles = null;
                throw new FailedLoginException("General Exception: " + ex4.toString());
            }
        }
        return b;
    }
    
    public boolean isUserInRole(final String o) {
        return null != this.m_userRoles && this.m_userRoles.contains(o);
    }
    
    public void handle(final Callback[] array) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] instanceof NameCallback) {
                final NameCallback nameCallback = (NameCallback)array[i];
                if (null == this.m_userName) {
                    throw new IOException("Cannot supply null user id");
                }
                nameCallback.setName(this.m_userName);
            }
            else if (array[i] instanceof PasswordCallback) {
                final PasswordCallback passwordCallback = (PasswordCallback)array[i];
                if (null == this.m_pw) {
                    throw new IOException("Cannot supply null password");
                }
                passwordCallback.setPassword(this.m_pw.toString().toCharArray());
            }
            else {
                if (!(array[i] instanceof TextOutputCallback)) {
                    throw new UnsupportedCallbackException(array[i], "PscUser");
                }
                final TextOutputCallback textOutputCallback = (TextOutputCallback)array[i];
                if (this.m_debug) {
                    this.m_debugStream.println(textOutputCallback.getMessage());
                }
            }
        }
    }
    
    public void setDebug(final boolean debug, PrintStream out) {
        this.m_debug = debug;
        if (!this.m_debug) {
            out = System.out;
        }
        else if (null != out) {
            this.m_debugStream = out;
        }
    }
}
