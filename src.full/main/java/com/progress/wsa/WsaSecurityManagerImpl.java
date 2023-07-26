// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import org.apache.soap.rpc.RPCMessage;
import java.util.Enumeration;
import java.security.Permission;
import java.util.StringTokenizer;
import java.util.Vector;
import com.progress.auth.PscAuthPermission;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Call;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import com.progress.auth.PscPrincipal;
import java.security.PermissionCollection;
import com.progress.auth.UserGroupPrincipal;
import com.progress.common.util.PasswordString;
import com.progress.common.ehnlog.AppLogger;
import java.util.Hashtable;

public class WsaSecurityManagerImpl implements WsaSecurityManager
{
    protected static final int LOGINMODE_NONE = 0;
    protected static final int LOGINMODE_JSE = 1;
    protected static final int LOGINMODE_WSA = 2;
    protected static final String ANONYMOUS_USER_ID = "anonymous_user";
    protected static final String ANONYMOUS_WSDL_ID = "anonymous_developer";
    protected static final String ANONYMOUS_ADMIN_ID = "anonymous_administrator";
    protected static final int APP_USER_CACHE = 0;
    protected static final int ADMIN_USER_CACHE = 1;
    protected static final int WSDL_USER_CACHE = 2;
    protected static final String RT_PROPERTIES_WSA = "(WSA)";
    protected static final String RT_PROPERTIES_DEFAULTS = "(DEFAULTS)";
    protected static final String RT_PROPERTIES_APPLICATION = "(APPLICATION)";
    private Hashtable m_roles;
    private Hashtable m_mappedPermissions;
    private String[] m_roleNames;
    private Hashtable m_appUserCache;
    private Hashtable m_adminUserCache;
    private Hashtable m_wsdlUserCache;
    protected WsaSOAPEngineContext m_context;
    protected WsaProperties m_properties;
    protected AppLogger m_log;
    protected int m_adminMode;
    protected int m_wsdlMode;
    protected int m_appMode;
    private WsaUser m_anonymousUser;
    private WsaUser m_anonymousAdminUser;
    private WsaUser m_anonymousWsdlUser;
    
    public WsaSecurityManagerImpl(final WsaSOAPEngineContext context) {
        this.m_roles = new Hashtable();
        this.m_mappedPermissions = new Hashtable();
        this.m_roleNames = null;
        this.m_appUserCache = new Hashtable();
        this.m_adminUserCache = new Hashtable();
        this.m_wsdlUserCache = new Hashtable();
        this.m_context = null;
        this.m_properties = null;
        this.m_log = null;
        this.m_adminMode = 0;
        this.m_wsdlMode = 0;
        this.m_appMode = 0;
        this.m_anonymousUser = null;
        this.m_anonymousAdminUser = null;
        this.m_anonymousWsdlUser = null;
        this.m_context = context;
        this.m_properties = (WsaProperties)this.m_context.get("psc.wsa.params");
        this.m_log = (AppLogger)this.m_context.get("psc.wsa.log");
        if (this.m_properties.adminAuth) {
            this.m_adminMode = 1;
        }
        if (this.m_properties.wsdlAuth) {
            this.m_wsdlMode = 1;
        }
        if (this.m_properties.appAuth) {
            this.m_appMode = 1;
        }
        this.loadRolesFromProperties();
        this.createAnonymousUser();
        this.mapAdminMethodPermissions();
    }
    
    public WsaUser authenticateApplicationUser(final String s, final PasswordString passwordString) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        WsaUser wsaUser = null;
        String s2 = s;
        if (null == s2) {
            s2 = "anonymous_user";
        }
        try {
            switch (this.m_appMode) {
                case 0: {
                    wsaUser = this.m_anonymousUser;
                    break;
                }
                case 1: {
                    wsaUser = this.getCachedUser(0, s2);
                    if (null == wsaUser) {
                        final WsaProperties properties = this.m_properties;
                        final String key = "DEFAULTUSER";
                        wsaUser = new WsaUser(this.m_properties.loginModule, s2, null, new UserGroupPrincipal[] { new UserGroupPrincipal(key) }, (PermissionCollection)this.m_roles.get(key));
                        if (this.m_properties.cacheUsers) {
                            this.cacheUser(0, s2, wsaUser);
                        }
                        break;
                    }
                    wsaUser.updateLastAccessed();
                    break;
                }
                case 2: {
                    throw new Exception("JAAS Login support is not available");
                }
            }
        }
        catch (LoginException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            final Object[] array = { "Web Services", s2, ex2.getMessage() };
            final AppLogger log = this.m_log;
            final String formatMessage = AppLogger.formatMessage(8607504787811871451L, array);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, formatMessage, ex2);
            }
            throw new LoginException(formatMessage);
        }
        return wsaUser;
    }
    
    public WsaUser authenticateWsdlUser(final String s, final PasswordString passwordString) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        WsaUser wsaUser = null;
        String s2 = s;
        if (null == s2) {
            s2 = "anonymous_user";
        }
        try {
            switch (this.m_wsdlMode) {
                case 0: {
                    wsaUser = this.m_anonymousWsdlUser;
                    break;
                }
                case 1: {
                    wsaUser = this.getCachedUser(2, s2);
                    if (null == wsaUser) {
                        final WsaProperties properties = this.m_properties;
                        final String key = "DEFAULTWSDL";
                        wsaUser = new WsaUser(this.m_properties.loginModule, s2, null, new UserGroupPrincipal[] { new UserGroupPrincipal(key) }, (PermissionCollection)this.m_roles.get(key));
                        if (this.m_properties.cacheUsers) {
                            this.cacheUser(2, s2, wsaUser);
                        }
                        break;
                    }
                    wsaUser.updateLastAccessed();
                    break;
                }
                case 2: {
                    throw new Exception("JAAS Login support is not available");
                }
            }
        }
        catch (LoginException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            final Object[] array = { "WSDL", s2, ex2.getMessage() };
            final AppLogger log = this.m_log;
            final String formatMessage = AppLogger.formatMessage(8607504787811871451L, array);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, formatMessage, ex2);
            }
            throw new LoginException(formatMessage);
        }
        return wsaUser;
    }
    
    public WsaUser authenticateAdminUser(final String s, final PasswordString passwordString) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        WsaUser wsaUser = null;
        String s2 = s;
        if (null == s2) {
            s2 = "anonymous_user";
        }
        try {
            switch (this.m_adminMode) {
                case 0: {
                    wsaUser = this.m_anonymousAdminUser;
                    break;
                }
                case 1: {
                    wsaUser = this.getCachedUser(1, s2);
                    if (null == wsaUser) {
                        final String key = "none";
                        wsaUser = new WsaUser(this.m_properties.loginModule, s2, null, new UserGroupPrincipal[] { new UserGroupPrincipal(key) }, (PermissionCollection)this.m_roles.get(key));
                        if (this.m_properties.cacheUsers) {
                            this.cacheUser(1, s2, wsaUser);
                        }
                        break;
                    }
                    wsaUser.updateLastAccessed();
                    break;
                }
                case 2: {
                    throw new Exception("JAAS Login support is not available");
                }
            }
        }
        catch (LoginException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            final Object[] array = { "administration", s2, ex2.getMessage() };
            final AppLogger log = this.m_log;
            final String formatMessage = AppLogger.formatMessage(8607504787811871451L, array);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, formatMessage, ex2);
            }
            throw new LoginException(formatMessage);
        }
        return wsaUser;
    }
    
    public WsaUser authenticateAdminUser(final String s, final PasswordString passwordString, final String[] array) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException {
        WsaUser wsaUser = null;
        String s2 = s;
        if (null == s2) {
            s2 = "anonymous_user";
        }
        try {
            switch (this.m_adminMode) {
                case 0: {
                    wsaUser = this.m_anonymousAdminUser;
                    break;
                }
                case 1: {
                    wsaUser = this.getCachedUser(1, s2);
                    if (null == wsaUser) {
                        UserGroupPrincipal[] array2;
                        if (array == null) {
                            array2 = new UserGroupPrincipal[] { new UserGroupPrincipal("none") };
                        }
                        else {
                            array2 = new UserGroupPrincipal[array.length];
                            for (int i = 0; i < array.length; ++i) {
                                array2[i] = new UserGroupPrincipal(array[i]);
                            }
                        }
                        try {
                            wsaUser = new WsaUser(this.m_properties.loginModule, s2, passwordString.toString(), array2, this.m_roles.get(array[0]));
                        }
                        catch (LoginException ex) {
                            ex.printStackTrace();
                        }
                        if (this.m_properties.cacheUsers) {
                            this.cacheUser(1, s2, wsaUser);
                        }
                        break;
                    }
                    wsaUser.updateLastAccessed();
                    break;
                }
                case 2: {
                    throw new Exception("JAAS Login support is not available");
                }
            }
        }
        catch (LoginException ex2) {
            throw ex2;
        }
        catch (Exception ex3) {
            final Object[] array3 = { "administration", s2, ex3.getMessage() };
            final AppLogger log = this.m_log;
            final String formatMessage = AppLogger.formatMessage(8607504787811871451L, array3);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, formatMessage, ex3);
            }
            throw new LoginException(formatMessage);
        }
        return wsaUser;
    }
    
    public boolean authorizeApplicationUser(final WsaUser wsaUser, final String s) throws WsaSOAPEngineException {
        final boolean b = true;
        if (this.m_properties.appAuth) {
            if (b && this.m_log.ifLogVerbose(1L, 0)) {
                this.m_log.logVerbose(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "granted", "Web Service", s });
            }
            if (!b && this.m_log.ifLogBasic(1L, 0)) {
                this.m_log.logBasic(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "denied", "Web Service", s });
            }
        }
        return b;
    }
    
    public boolean authorizeWsdlUser(final WsaUser wsaUser, final String s) throws WsaSOAPEngineException {
        final boolean b = true;
        if (this.m_properties.wsdlAuth) {
            if (b && this.m_log.ifLogVerbose(1L, 0)) {
                this.m_log.logVerbose(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "granted", "WSDL", s });
            }
            if (!b && this.m_log.ifLogBasic(1L, 0)) {
                this.m_log.logBasic(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "denied", "WSDL", s });
            }
        }
        return b;
    }
    
    public boolean authorizeAdminUser(final WsaUser wsaUser, final Call call) throws WsaSOAPEngineException {
        boolean canDo = true;
        String str = ((RPCMessage)call).getMethodName();
        if (this.m_properties.adminAuth) {
            try {
                if (str.endsWith("RuntimeProperties")) {
                    final Vector params = ((RPCMessage)call).getParams();
                    if (null == params || 0 >= params.size()) {
                        final Object[] array = { str, "targetURI" };
                        if (this.m_log.ifLogVerbose(1L, 0)) {
                            this.m_log.logVerbose(0, 8607504787811871413L, array);
                        }
                        throw new WsaSOAPEngineException(8607504787811871413L, array);
                    }
                    final Parameter parameter = params.elementAt(0);
                    if (parameter.getType() != String.class) {
                        final Object[] array2 = { "targetURI" };
                        if (this.m_log.ifLogVerbose(1L, 0)) {
                            this.m_log.logVerbose(0, 8607504787811871394L, array2);
                        }
                        throw new WsaSOAPEngineException(8607504787811871394L, array2);
                    }
                    final String s = (String)parameter.getValue();
                    if (s.equals("urn:services-progress-com:wsa-admin:0002")) {
                        str += "(WSA)";
                    }
                    else if (s.equals("urn:services-progress-com:wsa-default:0001")) {
                        str += "(DEFAULTS)";
                    }
                    else {
                        str += "(APPLICATION)";
                    }
                }
                final PscAuthPermission pscAuthPermission = this.m_mappedPermissions.get(str);
                if (null == pscAuthPermission) {
                    this.m_log.logError(8607504787811871454L, new Object[] { wsaUser.getName(), str });
                    canDo = false;
                }
                else {
                    canDo = wsaUser.canDo(pscAuthPermission);
                    if (canDo && this.m_log.ifLogVerbose(1L, 0)) {
                        this.m_log.logVerbose(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "granted", "administration", str });
                    }
                    if (!canDo && this.m_log.ifLogBasic(1L, 0)) {
                        this.m_log.logBasic(0, 8607504787811871452L, new Object[] { wsaUser.getName(), "denied", "administration", str });
                    }
                }
            }
            catch (Exception ex) {
                this.m_log.logError(8607504787811871454L, new Object[] { wsaUser.getName(), str });
                if (this.m_log.ifLogExtended(1L, 0)) {
                    this.m_log.logStackTrace(0, "Error looking up mapped administration method permission " + str + " for " + wsaUser.getName() + ".", ex);
                }
                canDo = false;
            }
        }
        return canDo;
    }
    
    public void clearUserCache() {
        synchronized (this.m_appUserCache) {
            this.m_appUserCache = new Hashtable();
        }
        synchronized (this.m_adminUserCache) {
            this.m_adminUserCache = new Hashtable();
        }
        synchronized (this.m_wsdlUserCache) {
            this.m_wsdlUserCache = new Hashtable();
        }
    }
    
    public void logoutUser(final int n, final String key) {
        try {
            switch (n) {
                case 0: {
                    synchronized (this.m_appUserCache) {
                        if (null != key) {
                            this.m_appUserCache.remove(key);
                        }
                    }
                    break;
                }
                case 1: {
                    synchronized (this.m_adminUserCache) {
                        if (null != key) {
                            this.m_adminUserCache.remove(key);
                        }
                    }
                    break;
                }
                case 2: {
                    synchronized (this.m_wsdlUserCache) {
                        if (null != key) {
                            this.m_wsdlUserCache.remove(key);
                        }
                    }
                    break;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void logoutUser(final int n, final WsaUser wsaUser) {
        if (null != wsaUser) {
            this.logoutUser(n, wsaUser.getName());
        }
    }
    
    public boolean cleanupExpiredUser(final WsaUser wsaUser) {
        boolean b = false;
        try {
            if (0L < this.m_properties.maxUserLifetime && null != wsaUser && wsaUser.lastAccessed() - wsaUser.createTime() > this.m_properties.maxUserLifetime) {
                b = true;
            }
        }
        catch (Exception ex) {}
        return b;
    }
    
    public String[] getRoleNames() {
        return this.m_roleNames;
    }
    
    protected void mapAdminMethodPermissions() {
        try {
            final String string = "wsa." + this.m_properties.instanceName + ".";
            this.m_mappedPermissions.put("pscdeploy", new PscAuthPermission(string + "servlet.services", "write"));
            this.m_mappedPermissions.put("pscundeploy", new PscAuthPermission(string + "servlet.services", "delete"));
            this.m_mappedPermissions.put("psclist", new PscAuthPermission(string + "servlet.services", "read"));
            this.m_mappedPermissions.put("pscquery", new PscAuthPermission(string + "servlet.services", "read"));
            this.m_mappedPermissions.put("exportApp", new PscAuthPermission(string + "servlet.services", "read"));
            this.m_mappedPermissions.put("importApp", new PscAuthPermission(string + "servlet.services", "write"));
            this.m_mappedPermissions.put("update", new PscAuthPermission(string + "servlet.services", "write"));
            this.m_mappedPermissions.put("wsastatus", new PscAuthPermission(string + "servlet.stats", "read"));
            this.m_mappedPermissions.put("resetwsastatus", new PscAuthPermission(string + "servlet.stats", "write"));
            this.m_mappedPermissions.put("appstatus", new PscAuthPermission(string + "apps.stats", "read"));
            this.m_mappedPermissions.put("resetappstatus", new PscAuthPermission(string + "apps.stats", "write"));
            this.m_mappedPermissions.put("enableApp", new PscAuthPermission(string + "apps.enable", "read"));
            this.m_mappedPermissions.put("disableApp", new PscAuthPermission(string + "apps.enable", "write"));
            this.m_mappedPermissions.put("getRuntimeProperties(APPLICATION)", new PscAuthPermission(string + "apps.props", "read"));
            this.m_mappedPermissions.put("setRuntimeProperties(APPLICATION)", new PscAuthPermission(string + "apps.props", "write"));
            this.m_mappedPermissions.put("resetRuntimeProperties(APPLICATION)", new PscAuthPermission(string + "apps.props", "write"));
            this.m_mappedPermissions.put("getRuntimeProperties(DEFAULTS)", new PscAuthPermission(string + "apps.defaults", "read"));
            this.m_mappedPermissions.put("setRuntimeProperties(DEFAULTS)", new PscAuthPermission(string + "apps.defaults", "write"));
            this.m_mappedPermissions.put("resetRuntimeProperties(DEFAULTS)", new PscAuthPermission(string + "apps.defaults", "write"));
            this.m_mappedPermissions.put("getRuntimeProperties(WSA)", new PscAuthPermission(string + "servlet.props", "read"));
            this.m_mappedPermissions.put("setRuntimeProperties(WSA)", new PscAuthPermission(string + "servlet.props", "write"));
            this.m_mappedPermissions.put("resetRuntimeProperties(WSA)", new PscAuthPermission(string + "servlet.props", "write"));
        }
        catch (Exception ex) {
            this.m_log.logError(8607504787811871458L, new Object[0]);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, "Error initializing administration method Permissions", ex);
            }
        }
    }
    
    protected void createAnonymousUser() {
        try {
            final UserGroupPrincipal[] array = { null };
            final int n = 0;
            final WsaProperties properties = this.m_properties;
            array[n] = new UserGroupPrincipal("DEFAULTUSER");
            final String s = "NONE";
            final String s2 = "anonymous_administrator";
            final String s3 = null;
            final UserGroupPrincipal[] array2 = array;
            final Hashtable roles = this.m_roles;
            final WsaProperties properties2 = this.m_properties;
            this.m_anonymousAdminUser = new WsaUser(s, s2, s3, array2, roles.get("DEFAULTUSER"));
            final UserGroupPrincipal[] array3 = { null };
            final int n2 = 0;
            final WsaProperties properties3 = this.m_properties;
            array3[n2] = new UserGroupPrincipal("DEFAULTADMIN");
            final String s4 = "NONE";
            final String s5 = "anonymous_user";
            final String s6 = null;
            final UserGroupPrincipal[] array4 = array3;
            final Hashtable roles2 = this.m_roles;
            final WsaProperties properties4 = this.m_properties;
            this.m_anonymousUser = new WsaUser(s4, s5, s6, array4, roles2.get("DEFAULTADMIN"));
            final UserGroupPrincipal[] array5 = { null };
            final int n3 = 0;
            final WsaProperties properties5 = this.m_properties;
            array5[n3] = new UserGroupPrincipal("DEFAULTWSDL");
            final String s7 = "NONE";
            final String s8 = "anonymous_developer";
            final String s9 = null;
            final UserGroupPrincipal[] array6 = array5;
            final Hashtable roles3 = this.m_roles;
            final WsaProperties properties6 = this.m_properties;
            this.m_anonymousWsdlUser = new WsaUser(s7, s8, s9, array6, roles3.get("DEFAULTWSDL"));
        }
        catch (Exception ex) {
            this.m_log.logError(8607504787811871459L, new Object[0]);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, "Error initializing anonymous user ids", ex);
            }
        }
    }
    
    protected WsaUser getCachedUser(final int n, final String key) {
        WsaUser key2 = null;
        try {
            if (null != key && 0 < key.length()) {
                switch (n) {
                    case 0: {
                        synchronized (this.m_appUserCache) {
                            key2 = this.m_appUserCache.get(key);
                            if (this.cleanupExpiredUser(key2)) {
                                this.m_appUserCache.remove(key2);
                                key2 = null;
                            }
                        }
                        break;
                    }
                    case 1: {
                        synchronized (this.m_adminUserCache) {
                            key2 = this.m_adminUserCache.get(key);
                            if (this.cleanupExpiredUser(key2)) {
                                this.m_adminUserCache.remove(key2);
                                key2 = null;
                            }
                        }
                        break;
                    }
                    case 2: {
                        synchronized (this.m_wsdlUserCache) {
                            key2 = this.m_wsdlUserCache.get(key);
                            if (this.cleanupExpiredUser(key2)) {
                                this.m_wsdlUserCache.remove(key2);
                                key2 = null;
                            }
                        }
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return key2;
    }
    
    protected void cacheUser(final int n, final String key, final WsaUser value) {
        try {
            if (null != value && null != key && 0 < key.length()) {
                switch (n) {
                    case 0: {
                        synchronized (this.m_appUserCache) {
                            this.m_appUserCache.put(key, value);
                        }
                        break;
                    }
                    case 1: {
                        synchronized (this.m_adminUserCache) {
                            this.m_adminUserCache.put(key, value);
                        }
                        break;
                    }
                    case 2: {
                        synchronized (this.m_wsdlUserCache) {
                            this.m_wsdlUserCache.put(key, value);
                        }
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    protected void loadRolesFromProperties() {
        try {
            final Object[] array = new Object[3];
            final PscAuthPermission pscAuthPermission = new PscAuthPermission("master");
            if (null != this.m_properties.adminRoles && 0 < this.m_properties.adminRoles.length()) {
                int count = 0;
                final char[] charArray = this.m_properties.adminRoles.toCharArray();
                final char[] value = new char[charArray.length];
                for (int i = 0; i < charArray.length; ++i) {
                    if (charArray[i] != ' ' && charArray[i] != '\t') {
                        value[count++] = charArray[i];
                    }
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(new String(value, 0, count), ",");
                final int countTokens = stringTokenizer.countTokens();
                if (0 < countTokens) {
                    int n = 0;
                    this.m_roleNames = new String[countTokens];
                    while (stringTokenizer.hasMoreTokens()) {
                        this.m_roleNames[n++] = stringTokenizer.nextToken();
                    }
                }
            }
            final Enumeration<String> keys = this.m_properties.roleDefinitions.keys();
            while (keys.hasMoreElements()) {
                final String s = keys.nextElement();
                final Hashtable<String, Hashtable<String, Hashtable<String, Hashtable>>> hashtable = this.m_properties.roleDefinitions.get(s);
                final Enumeration<String> keys2 = hashtable.keys();
                final PermissionCollection permissionCollection = pscAuthPermission.newPermissionCollection();
                final UserGroupPrincipal userGroupPrincipal = new UserGroupPrincipal(s);
                if (this.m_log.ifLogExtended(1L, 0)) {
                    array[0] = s;
                    this.m_log.logExtended(0, 8607504787811871462L, array);
                }
                while (keys2.hasMoreElements()) {
                    final String key = keys2.nextElement();
                    final String s2 = (String)hashtable.get(key);
                    if (null != s2 && 0 < s2.length()) {
                        permissionCollection.add(new PscAuthPermission(key, s2));
                        if (!this.m_log.ifLogExtended(1L, 0)) {
                            continue;
                        }
                        array[1] = key;
                        array[2] = s2;
                        this.m_log.logExtended(0, 8607504787811871463L, array);
                    }
                }
                this.m_roles.put(s, permissionCollection);
            }
        }
        catch (Exception ex) {
            this.m_log.logError(8607504787811871464L, new Object[0]);
            if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logStackTrace(0, "Error initializing roles from property definitions", ex);
            }
        }
    }
}
