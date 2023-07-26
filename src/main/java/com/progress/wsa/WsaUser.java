// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.security.Permission;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import com.progress.auth.PscAuthPermission;
import com.progress.auth.UserGroupPrincipal;
import java.util.HashSet;
import com.progress.common.util.PasswordString;
import com.progress.auth.PscPrincipal;
import java.security.PermissionCollection;
import com.progress.auth.PscUser;

public class WsaUser extends PscUser
{
    private PermissionCollection m_permissionCollection;
    private long m_createTime;
    
    public WsaUser(final String s, final String s2, final String s3, final PscPrincipal[] array, final PermissionCollection permissionCollection) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        super(s2, s);
        this.m_permissionCollection = null;
        this.m_createTime = System.currentTimeMillis();
        this.m_createTime = super.m_lastAccess;
        if (super.m_loginContext != null && super.m_loginContext.equals("WSA")) {
            if (null != s3 && this.authenticate(s3)) {
                super.m_pw = new PasswordString(s3);
            }
        }
        else {
            if (null != s3) {
                super.m_pw = new PasswordString(s3);
            }
            if (null != array) {
                super.m_userRoles = new HashSet();
                for (int i = 0; i < array.length; ++i) {
                    final PscPrincipal pscPrincipal = array[i];
                    if (pscPrincipal instanceof UserGroupPrincipal) {
                        super.m_userRoles.add(((UserGroupPrincipal)pscPrincipal).getName());
                    }
                }
            }
            if (null != permissionCollection) {
                this.m_permissionCollection = permissionCollection;
            }
            else {
                this.m_permissionCollection = new PscAuthPermission("master").newPermissionCollection();
            }
        }
    }
    
    public WsaUser(final String s, final String s2) {
        super(s, s2);
        this.m_permissionCollection = null;
        this.m_createTime = System.currentTimeMillis();
        this.m_createTime = super.m_lastAccess;
        this.m_permissionCollection = new PscAuthPermission("master").newPermissionCollection();
    }
    
    public WsaUser(final String s, final String s2, final String s3) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        super(s, s2, s3);
        this.m_permissionCollection = null;
        this.m_createTime = System.currentTimeMillis();
        this.m_createTime = super.m_lastAccess;
        this.m_permissionCollection = new PscAuthPermission("master").newPermissionCollection();
    }
    
    public WsaUser(final String s, final String s2, final String s3, final String[] array) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        this.m_permissionCollection = null;
        this.m_createTime = System.currentTimeMillis();
        if (!s3.equals("WSA")) {
            super.m_userName = s;
            super.m_loginContext = s3;
            super.m_lastAccess = System.currentTimeMillis();
            if (null == super.m_loginContext) {
                super.m_loginContext = "DEFAULT";
            }
            if (null != s2 && this.authenticate(s2)) {
                super.m_pw = new PasswordString(s2);
            }
        }
        super.m_userName = s;
        super.m_loginContext = s3;
        this.m_createTime = super.m_lastAccess;
        super.m_userRoles = new HashSet();
        for (int i = 0; i < array.length; ++i) {
            super.m_userRoles.add(array[i]);
        }
        this.m_permissionCollection = new PscAuthPermission("master").newPermissionCollection();
    }
    
    public boolean canDo(final PscAuthPermission pscAuthPermission) {
        boolean b;
        if (null != super.m_userSubject) {
            b = pscAuthPermission.canDo(super.m_userSubject);
        }
        else {
            b = this.m_permissionCollection.implies(pscAuthPermission);
        }
        return b;
    }
    
    public void updateLastAccessed() {
        super.m_lastAccess = System.currentTimeMillis();
    }
    
    public long createTime() {
        return this.m_createTime;
    }
}
