// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Vector;
import java.net.ProtocolException;
import java.util.Enumeration;
import java.io.IOException;
import java.util.Hashtable;

public class AuthorizationInfo implements Cloneable
{
    private static Hashtable CntxtList;
    private static AuthorizationHandler AuthHandler;
    private String host;
    private int port;
    private String scheme;
    private String realm;
    private String cookie;
    private NVPair[] auth_params;
    private Object extra_info;
    private String[] paths;
    
    AuthorizationInfo(final String s, final int port) {
        this.auth_params = new NVPair[0];
        this.extra_info = null;
        this.paths = new String[0];
        this.host = s.trim().toLowerCase();
        this.port = port;
    }
    
    public AuthorizationInfo(final String s, final int port, final String s2, final String realm, final NVPair[] array, final Object extra_info) {
        this.auth_params = new NVPair[0];
        this.extra_info = null;
        this.paths = new String[0];
        this.scheme = s2.trim();
        this.host = s.trim().toLowerCase();
        this.port = port;
        this.realm = realm;
        this.cookie = null;
        if (array != null) {
            this.auth_params = Util.resizeArray(array, array.length);
        }
        this.extra_info = extra_info;
    }
    
    public AuthorizationInfo(final String s, final int port, final String s2, final String realm, final String s3) {
        this.auth_params = new NVPair[0];
        this.extra_info = null;
        this.paths = new String[0];
        this.scheme = s2.trim();
        this.host = s.trim().toLowerCase();
        this.port = port;
        this.realm = realm;
        if (s3 != null) {
            this.cookie = s3.trim();
        }
        else {
            this.cookie = null;
        }
    }
    
    AuthorizationInfo(final AuthorizationInfo authorizationInfo) {
        this.auth_params = new NVPair[0];
        this.extra_info = null;
        this.paths = new String[0];
        this.scheme = authorizationInfo.scheme;
        this.host = authorizationInfo.host;
        this.port = authorizationInfo.port;
        this.realm = authorizationInfo.realm;
        this.cookie = authorizationInfo.cookie;
        this.auth_params = Util.resizeArray(authorizationInfo.auth_params, authorizationInfo.auth_params.length);
        this.extra_info = authorizationInfo.extra_info;
    }
    
    public static AuthorizationHandler setAuthHandler(final AuthorizationHandler authHandler) {
        final AuthorizationHandler authHandler2 = AuthorizationInfo.AuthHandler;
        AuthorizationInfo.AuthHandler = authHandler;
        return authHandler2;
    }
    
    public static AuthorizationHandler getAuthHandler() {
        return AuthorizationInfo.AuthHandler;
    }
    
    public static AuthorizationInfo getAuthorization(final String s, final int n, final String s2, final String s3) {
        return getAuthorization(s, n, s2, s3, HTTPConnection.getDefaultContext());
    }
    
    public static synchronized AuthorizationInfo getAuthorization(final String s, final int n, final String s2, final String s3, final Object o) {
        return Util.getList(AuthorizationInfo.CntxtList, o).get(new AuthorizationInfo(s, n, s2, s3, null, null));
    }
    
    static AuthorizationInfo queryAuthHandler(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final RoResponse roResponse) throws AuthSchemeNotImplException, IOException {
        if (AuthorizationInfo.AuthHandler == null) {
            return null;
        }
        final AuthorizationInfo authorization = AuthorizationInfo.AuthHandler.getAuthorization(authorizationInfo, roRequest, roResponse);
        if (authorization != null) {
            if (roRequest != null) {
                addAuthorization((AuthorizationInfo)authorization.clone(), roRequest.getConnection().getContext());
            }
            else {
                addAuthorization((AuthorizationInfo)authorization.clone(), HTTPConnection.getDefaultContext());
            }
        }
        return authorization;
    }
    
    static synchronized AuthorizationInfo getAuthorization(final AuthorizationInfo key, final RoRequest roRequest, final RoResponse roResponse, final boolean b) throws AuthSchemeNotImplException, IOException {
        Hashtable hashtable;
        if (roRequest != null) {
            hashtable = Util.getList(AuthorizationInfo.CntxtList, roRequest.getConnection().getContext());
        }
        else {
            hashtable = Util.getList(AuthorizationInfo.CntxtList, HTTPConnection.getDefaultContext());
        }
        AuthorizationInfo queryAuthHandler = hashtable.get(key);
        if (queryAuthHandler == null && b) {
            queryAuthHandler = queryAuthHandler(key, roRequest, roResponse);
        }
        return queryAuthHandler;
    }
    
    static AuthorizationInfo getAuthorization(final String s, final int n, final String s2, final String s3, final RoRequest roRequest, final RoResponse roResponse, final boolean b) throws AuthSchemeNotImplException, IOException {
        return getAuthorization(new AuthorizationInfo(s, n, s2, s3, null, null), roRequest, roResponse, b);
    }
    
    public static void addAuthorization(final AuthorizationInfo authorizationInfo) {
        addAuthorization(authorizationInfo, HTTPConnection.getDefaultContext());
    }
    
    public static void addAuthorization(final AuthorizationInfo authorizationInfo, final Object o) {
        final Hashtable list = Util.getList(AuthorizationInfo.CntxtList, o);
        final AuthorizationInfo authorizationInfo2 = list.get(authorizationInfo);
        if (authorizationInfo2 != null) {
            final int length = authorizationInfo2.paths.length;
            final int length2 = authorizationInfo.paths.length;
            if (length2 == 0) {
                authorizationInfo.paths = authorizationInfo2.paths;
            }
            else {
                authorizationInfo.paths = Util.resizeArray(authorizationInfo.paths, length2 + length);
                System.arraycopy(authorizationInfo2.paths, 0, authorizationInfo.paths, length2, length);
            }
        }
        list.put(authorizationInfo, authorizationInfo);
    }
    
    public static void addAuthorization(final String s, final int n, final String s2, final String s3, final String s4, final NVPair[] array, final Object o) {
        addAuthorization(s, n, s2, s3, s4, array, o, HTTPConnection.getDefaultContext());
    }
    
    public static void addAuthorization(final String s, final int n, final String s2, final String s3, final String s4, final NVPair[] array, final Object extra_info, final Object o) {
        final AuthorizationInfo authorizationInfo = new AuthorizationInfo(s, n, s2, s3, s4);
        if (array != null && array.length > 0) {
            authorizationInfo.auth_params = Util.resizeArray(array, array.length);
        }
        authorizationInfo.extra_info = extra_info;
        addAuthorization(authorizationInfo, o);
    }
    
    public static void addBasicAuthorization(final String s, final int n, final String s2, final String str, final String str2) {
        addAuthorization(s, n, "Basic", s2, Codecs.base64Encode(str + ":" + str2), null, null);
    }
    
    public static void addBasicAuthorization(final String s, final int n, final String s2, final String str, final String str2, final Object o) {
        addAuthorization(s, n, "Basic", s2, Codecs.base64Encode(str + ":" + str2), null, null, o);
    }
    
    public static void addDigestAuthorization(final String s, final int n, final String s2, final String s3, final String s4) {
        addDigestAuthorization(s, n, s2, s3, s4, HTTPConnection.getDefaultContext());
    }
    
    public static void addDigestAuthorization(final String s, final int n, final String str, final String str2, final String str3, final Object o) {
        final AuthorizationInfo authorization = getAuthorization(s, n, "Digest", str, o);
        NVPair[] params;
        if (authorization == null) {
            params = new NVPair[] { new NVPair("username", str2), new NVPair("uri", ""), new NVPair("nonce", ""), new NVPair("response", "") };
        }
        else {
            params = authorization.getParams();
            for (int i = 0; i < params.length; ++i) {
                if (params[i].getName().equalsIgnoreCase("username")) {
                    params[i] = new NVPair("username", str2);
                    break;
                }
            }
        }
        addAuthorization(s, n, "Digest", str, null, params, new String[] { MD5.hexDigest(str2 + ":" + str + ":" + str3), null, null }, o);
    }
    
    public static void removeAuthorization(final AuthorizationInfo authorizationInfo) {
        removeAuthorization(authorizationInfo, HTTPConnection.getDefaultContext());
    }
    
    public static void removeAuthorization(final AuthorizationInfo key, final Object o) {
        Util.getList(AuthorizationInfo.CntxtList, o).remove(key);
    }
    
    public static void removeAuthorization(final String s, final int n, final String s2, final String s3) {
        removeAuthorization(new AuthorizationInfo(s, n, s2, s3, null, null));
    }
    
    public static void removeAuthorization(final String s, final int n, final String s2, final String s3, final Object o) {
        removeAuthorization(new AuthorizationInfo(s, n, s2, s3, null, null), o);
    }
    
    static AuthorizationInfo findBest(final RoRequest roRequest) {
        final String path = Util.getPath(roRequest.getRequestURI());
        final String host = roRequest.getConnection().getHost();
        final int port = roRequest.getConnection().getPort();
        final Hashtable list = Util.getList(AuthorizationInfo.CntxtList, roRequest.getConnection().getContext());
        final Enumeration<AuthorizationInfo> elements = list.elements();
        while (elements.hasMoreElements()) {
            final AuthorizationInfo authorizationInfo = elements.nextElement();
            if (authorizationInfo.host.equals(host)) {
                if (authorizationInfo.port != port) {
                    continue;
                }
                final String[] paths = authorizationInfo.paths;
                for (int i = 0; i < paths.length; ++i) {
                    if (path.equals(paths[i])) {
                        return authorizationInfo;
                    }
                }
            }
        }
        AuthorizationInfo authorizationInfo2 = null;
        final String substring = path.substring(0, path.lastIndexOf(47) + 1);
        int n = Integer.MAX_VALUE;
        final Enumeration<AuthorizationInfo> elements2 = list.elements();
        while (elements2.hasMoreElements()) {
            final AuthorizationInfo authorizationInfo3 = elements2.nextElement();
            if (authorizationInfo3.host.equals(host)) {
                if (authorizationInfo3.port != port) {
                    continue;
                }
                final String[] paths2 = authorizationInfo3.paths;
                for (int j = 0; j < paths2.length; ++j) {
                    final String substring2 = paths2[j].substring(0, paths2[j].lastIndexOf(47) + 1);
                    if (substring.equals(substring2)) {
                        return authorizationInfo3;
                    }
                    if (substring.startsWith(substring2)) {
                        int n2 = 0;
                        int index = substring2.length() - 1;
                        while ((index = substring.indexOf(47, index + 1)) != -1) {
                            ++n2;
                        }
                        if (n2 < n) {
                            n = n2;
                            authorizationInfo2 = authorizationInfo3;
                        }
                    }
                    else if (substring2.startsWith(substring)) {
                        int n3 = 0;
                        int n4 = substring.length();
                        while ((n4 = substring2.indexOf(47, n4 + 1)) != -1) {
                            ++n3;
                        }
                        if (n3 < n) {
                            n = n3;
                            authorizationInfo2 = authorizationInfo3;
                        }
                    }
                }
            }
        }
        return authorizationInfo2;
    }
    
    public synchronized void addPath(final String s) {
        final String path = Util.getPath(s);
        for (int i = 0; i < this.paths.length; ++i) {
            if (this.paths[i].equals(path)) {
                return;
            }
        }
        (this.paths = Util.resizeArray(this.paths, this.paths.length + 1))[this.paths.length - 1] = path;
    }
    
    static AuthorizationInfo[] parseAuthString(final String s, final RoRequest roRequest, final RoResponse roResponse) throws ProtocolException {
        int n = 0;
        final char[] charArray = s.toCharArray();
        int length = charArray.length;
        final int[] array = new int[2];
        AuthorizationInfo[] resizeArray = new AuthorizationInfo[0];
        while (Character.isWhitespace(charArray[length - 1])) {
            --length;
        }
        while (true) {
            final int skipSpace = Util.skipSpace(charArray, n);
            if (skipSpace == length) {
                break;
            }
            final int space = Util.findSpace(charArray, skipSpace + 1);
            int statusCode;
            try {
                statusCode = roResponse.getStatusCode();
            }
            catch (IOException ex) {
                throw new ProtocolException(ex.toString());
            }
            AuthorizationInfo authorizationInfo;
            if (statusCode == 401) {
                authorizationInfo = new AuthorizationInfo(roRequest.getConnection().getHost(), roRequest.getConnection().getPort());
            }
            else {
                authorizationInfo = new AuthorizationInfo(roRequest.getConnection().getProxyHost(), roRequest.getConnection().getProxyPort());
            }
            if (charArray[space - 1] == ',') {
                authorizationInfo.scheme = s.substring(skipSpace, space - 1);
                n = space;
            }
            else {
                authorizationInfo.scheme = s.substring(skipSpace, space);
                array[0] = skipSpace;
                array[1] = space;
                final Vector params = parseParams(s, charArray, array, length, authorizationInfo);
                n = array[0];
                final int n2 = array[1];
                if (!params.isEmpty()) {
                    params.copyInto(authorizationInfo.auth_params = new NVPair[params.size()]);
                }
            }
            if (authorizationInfo.realm == null) {
                authorizationInfo.realm = "";
            }
            resizeArray = Util.resizeArray(resizeArray, resizeArray.length + 1);
            resizeArray[resizeArray.length - 1] = authorizationInfo;
        }
        return resizeArray;
    }
    
    private static final Vector parseParams(final String s, final char[] array, final int[] array2, final int endIndex, final AuthorizationInfo authorizationInfo) throws ProtocolException {
        final int n = array2[0];
        int endIndex2 = array2[1];
        int n2 = 1;
        final Vector<NVPair> vector = new Vector<NVPair>();
        int beginIndex;
        while (true) {
            beginIndex = Util.skipSpace(array, endIndex2);
            if (beginIndex == endIndex) {
                break;
            }
            if (n2 == 0) {
                if (array[beginIndex] != ',') {
                    throw new ProtocolException("Bad Authentication header format: '" + s + "'\nExpected \",\" at position " + beginIndex);
                }
                beginIndex = Util.skipSpace(array, beginIndex + 1);
                if (beginIndex == endIndex) {
                    break;
                }
                if (array[beginIndex] == ',') {
                    endIndex2 = beginIndex;
                    continue;
                }
            }
            final int n3 = beginIndex;
            for (endIndex2 = beginIndex + 1; endIndex2 < endIndex && !Character.isWhitespace(array[endIndex2]) && array[endIndex2] != '=' && array[endIndex2] != ','; ++endIndex2) {}
            if (n2 != 0 && (endIndex2 == endIndex || (array[endIndex2] == '=' && (endIndex2 + 1 == endIndex || (array[endIndex2 + 1] == '=' && endIndex2 + 2 == endIndex))))) {
                authorizationInfo.cookie = s.substring(beginIndex, endIndex);
                beginIndex = endIndex;
                break;
            }
            final String substring = s.substring(beginIndex, endIndex2);
            final int skipSpace = Util.skipSpace(array, endIndex2);
            if ((skipSpace < endIndex && array[skipSpace] != '=' && array[skipSpace] != ',') || (n2 == 0 && (skipSpace == endIndex || array[skipSpace] == ','))) {
                beginIndex = n3;
                break;
            }
            String realm;
            if (skipSpace < endIndex && array[skipSpace] == '=') {
                int skipSpace2 = Util.skipSpace(array, skipSpace + 1);
                if (skipSpace2 == endIndex) {
                    throw new ProtocolException("Bad Authentication header format: " + s + "\nUnexpected EOL after token" + " at position " + (endIndex2 - 1));
                }
                if (array[skipSpace2] != '\"') {
                    endIndex2 = Util.skipToken(array, skipSpace2);
                    if (endIndex2 == skipSpace2) {
                        throw new ProtocolException("Bad Authentication header format: " + s + "\nToken expected at " + "position " + skipSpace2);
                    }
                    realm = s.substring(skipSpace2, endIndex2);
                }
                else {
                    endIndex2 = skipSpace2++;
                    do {
                        endIndex2 = s.indexOf(34, endIndex2 + 1);
                    } while (endIndex2 != -1 && s.charAt(endIndex2 - 1) == '\\');
                    if (endIndex2 == -1) {
                        throw new ProtocolException("Bad Authentication header format: " + s + "\nClosing <\"> for " + "quoted-string starting at position " + skipSpace2 + " not found");
                    }
                    realm = Util.dequoteString(s.substring(skipSpace2, endIndex2));
                    ++endIndex2;
                }
            }
            else {
                realm = null;
            }
            if (substring.equalsIgnoreCase("realm")) {
                authorizationInfo.realm = realm;
            }
            else {
                vector.addElement(new NVPair(substring, realm));
            }
            n2 = 0;
        }
        array2[0] = beginIndex;
        array2[1] = endIndex2;
        return vector;
    }
    
    public final String getHost() {
        return this.host;
    }
    
    public final int getPort() {
        return this.port;
    }
    
    public final String getScheme() {
        return this.scheme;
    }
    
    public final String getRealm() {
        return this.realm;
    }
    
    public final String getCookie() {
        return this.cookie;
    }
    
    public final void setCookie(final String cookie) {
        this.cookie = cookie;
    }
    
    public final NVPair[] getParams() {
        return Util.resizeArray(this.auth_params, this.auth_params.length);
    }
    
    public final void setParams(final NVPair[] array) {
        if (array != null) {
            this.auth_params = Util.resizeArray(array, array.length);
        }
        else {
            this.auth_params = new NVPair[0];
        }
    }
    
    public final Object getExtraInfo() {
        return this.extra_info;
    }
    
    public final void setExtraInfo(final Object extra_info) {
        this.extra_info = extra_info;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(100);
        sb.append(this.scheme);
        sb.append(" ");
        if (this.cookie != null) {
            sb.append(this.cookie);
        }
        else {
            if (this.realm.length() > 0) {
                sb.append("realm=\"");
                sb.append(Util.quoteString(this.realm, "\\\""));
                sb.append('\"');
            }
            for (int i = 0; i < this.auth_params.length; ++i) {
                sb.append(',');
                sb.append(this.auth_params[i].getName());
                if (this.auth_params[i].getValue() != null) {
                    sb.append("=\"");
                    sb.append(Util.quoteString(this.auth_params[i].getValue(), "\\\""));
                    sb.append('\"');
                }
            }
        }
        return sb.toString();
    }
    
    public int hashCode() {
        return (this.host + this.scheme.toLowerCase() + this.realm).hashCode();
    }
    
    public boolean equals(final Object o) {
        if (o != null && o instanceof AuthorizationInfo) {
            final AuthorizationInfo authorizationInfo = (AuthorizationInfo)o;
            if (this.host.equals(authorizationInfo.host) && this.port == authorizationInfo.port && this.scheme.equalsIgnoreCase(authorizationInfo.scheme) && this.realm.equals(authorizationInfo.realm)) {
                return true;
            }
        }
        return false;
    }
    
    public Object clone() {
        AuthorizationInfo authorizationInfo;
        try {
            authorizationInfo = (AuthorizationInfo)super.clone();
            authorizationInfo.auth_params = Util.resizeArray(this.auth_params, this.auth_params.length);
            try {
                authorizationInfo.extra_info = this.extra_info.getClass().getMethod("clone", (Class<?>[])null).invoke(this.extra_info, (Object[])null);
            }
            catch (Throwable t) {}
            authorizationInfo.paths = new String[this.paths.length];
            System.arraycopy(this.paths, 0, authorizationInfo.paths, 0, this.paths.length);
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString());
        }
        return authorizationInfo;
    }
    
    static {
        AuthorizationInfo.CntxtList = new Hashtable();
        AuthorizationInfo.AuthHandler = new DefaultAuthHandler();
        AuthorizationInfo.CntxtList.put(HTTPConnection.getDefaultContext(), new Hashtable<Object, Hashtable>());
    }
}
