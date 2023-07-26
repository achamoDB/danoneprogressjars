// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.IOException;

public class DefaultAuthHandler implements AuthorizationHandler, GlobalConstants
{
    private static final byte[] NUL;
    private static final int DI_A1 = 0;
    private static final int DI_A1S = 1;
    private static final int DI_QOP = 2;
    private static byte[] digest_secret;
    private static AuthorizationPrompter prompter;
    private static boolean prompterSet;
    
    public AuthorizationInfo fixupAuthInfo(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final AuthorizationInfo authorizationInfo2, final RoResponse roResponse) throws AuthSchemeNotImplException {
        if (authorizationInfo.getScheme().equalsIgnoreCase("Basic") || authorizationInfo.getScheme().equalsIgnoreCase("SOCKS5")) {
            return authorizationInfo;
        }
        if (!authorizationInfo.getScheme().equalsIgnoreCase("Digest")) {
            throw new AuthSchemeNotImplException(authorizationInfo.getScheme());
        }
        if (Log.isEnabled(8)) {
            Log.write(8, "Auth:  fixing up Authorization for host " + authorizationInfo.getHost() + ":" + authorizationInfo.getPort() + "; scheme: " + authorizationInfo.getScheme() + "; realm: " + authorizationInfo.getRealm());
        }
        return digest_fixup(authorizationInfo, roRequest, authorizationInfo2, roResponse);
    }
    
    public AuthorizationInfo getAuthorization(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final RoResponse roResponse) throws AuthSchemeNotImplException, IOException {
        if (Log.isEnabled(8)) {
            Log.write(8, "Auth:  Requesting Authorization for host " + authorizationInfo.getHost() + ":" + authorizationInfo.getPort() + "; scheme: " + authorizationInfo.getScheme() + "; realm: " + authorizationInfo.getRealm());
        }
        if (!authorizationInfo.getScheme().equalsIgnoreCase("Basic") && !authorizationInfo.getScheme().equalsIgnoreCase("Digest") && !authorizationInfo.getScheme().equalsIgnoreCase("SOCKS5")) {
            throw new AuthSchemeNotImplException(authorizationInfo.getScheme());
        }
        if (authorizationInfo.getScheme().equalsIgnoreCase("Digest")) {
            final AuthorizationInfo digest_check_stale = digest_check_stale(authorizationInfo, roRequest, roResponse);
            if (digest_check_stale != null) {
                return digest_check_stale;
            }
        }
        final NVPair usernamePassword;
        synchronized (this.getClass()) {
            if (!roRequest.allowUI() || (DefaultAuthHandler.prompterSet && DefaultAuthHandler.prompter == null)) {
                return null;
            }
            if (DefaultAuthHandler.prompter == null) {
                setDefaultPrompter();
            }
            usernamePassword = DefaultAuthHandler.prompter.getUsernamePassword(authorizationInfo, roResponse.getStatusCode() == 407);
        }
        if (usernamePassword == null) {
            return null;
        }
        AuthorizationInfo digest_fixup;
        if (authorizationInfo.getScheme().equalsIgnoreCase("basic")) {
            digest_fixup = new AuthorizationInfo(authorizationInfo.getHost(), authorizationInfo.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), Codecs.base64Encode(usernamePassword.getName() + ":" + usernamePassword.getValue()));
        }
        else if (authorizationInfo.getScheme().equalsIgnoreCase("Digest")) {
            digest_fixup = digest_fixup(digest_gen_auth_info(authorizationInfo.getHost(), authorizationInfo.getPort(), authorizationInfo.getRealm(), usernamePassword.getName(), usernamePassword.getValue(), roRequest.getConnection().getContext()), roRequest, authorizationInfo, null);
        }
        else {
            digest_fixup = new AuthorizationInfo(authorizationInfo.getHost(), authorizationInfo.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), new NVPair[] { usernamePassword }, null);
        }
        System.gc();
        Log.write(8, "Auth:  Got Authorization");
        return digest_fixup;
    }
    
    public void handleAuthHeaders(final Response response, final RoRequest roRequest, final AuthorizationInfo authorizationInfo, final AuthorizationInfo authorizationInfo2) throws IOException {
        String header = response.getHeader("Authentication-Info");
        String header2 = response.getHeader("Proxy-Authentication-Info");
        if (header == null && authorizationInfo != null && hasParam(authorizationInfo.getParams(), "qop", "auth-int")) {
            header = "";
        }
        if (header2 == null && authorizationInfo2 != null && hasParam(authorizationInfo2.getParams(), "qop", "auth-int")) {
            header2 = "";
        }
        try {
            handleAuthInfo(header, "Authentication-Info", authorizationInfo, response, roRequest, true);
            handleAuthInfo(header2, "Proxy-Authentication-Info", authorizationInfo2, response, roRequest, true);
        }
        catch (ParseException ex) {
            throw new IOException(ex.toString());
        }
    }
    
    public void handleAuthTrailers(final Response response, final RoRequest roRequest, final AuthorizationInfo authorizationInfo, final AuthorizationInfo authorizationInfo2) throws IOException {
        final String trailer = response.getTrailer("Authentication-Info");
        final String trailer2 = response.getTrailer("Proxy-Authentication-Info");
        try {
            handleAuthInfo(trailer, "Authentication-Info", authorizationInfo, response, roRequest, false);
            handleAuthInfo(trailer2, "Proxy-Authentication-Info", authorizationInfo2, response, roRequest, false);
        }
        catch (ParseException ex) {
            throw new IOException(ex.toString());
        }
    }
    
    private static void handleAuthInfo(final String s, final String s2, final AuthorizationInfo authorizationInfo, final Response response, final RoRequest roRequest, final boolean b) throws ParseException, IOException {
        if (s == null) {
            return;
        }
        final Vector header = Util.parseHeader(s);
        final HttpHeaderElement element;
        if (handle_nextnonce(authorizationInfo, roRequest, element = Util.getElement(header, "nextnonce"))) {
            header.removeElement(element);
        }
        final HttpHeaderElement element2;
        if (handle_discard(authorizationInfo, roRequest, element2 = Util.getElement(header, "discard"))) {
            header.removeElement(element2);
        }
        if (b) {
            HttpHeaderElement element3 = null;
            if (header != null && (element3 = Util.getElement(header, "qop")) != null && element3.getValue() != null) {
                handle_rspauth(authorizationInfo, response, roRequest, header, s2);
            }
            else if (authorizationInfo != null && ((Util.hasToken(response.getHeader("Trailer"), s2) && hasParam(authorizationInfo.getParams(), "qop", null)) || hasParam(authorizationInfo.getParams(), "qop", "auth-int"))) {
                handle_rspauth(authorizationInfo, response, roRequest, null, s2);
            }
            else if ((header != null && element3 == null && header.contains(new HttpHeaderElement("digest"))) || (Util.hasToken(response.getHeader("Trailer"), s2) && authorizationInfo != null && !hasParam(authorizationInfo.getParams(), "qop", null))) {
                handle_digest(authorizationInfo, response, roRequest, s2);
            }
        }
        if (header.size() > 0) {
            response.setHeader(s2, Util.assembleHeader(header));
        }
        else {
            response.deleteHeader(s2);
        }
    }
    
    private static final boolean hasParam(final NVPair[] array, final String anotherString, final String anotherString2) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getName().equalsIgnoreCase(anotherString) && (anotherString2 == null || array[i].getValue().equalsIgnoreCase(anotherString2))) {
                return true;
            }
        }
        return false;
    }
    
    private static AuthorizationInfo digest_gen_auth_info(final String s, final int n, final String str, final String str2, final String str3, final Object o) {
        final String[] array = { MD5.hexDigest(str2 + ":" + str + ":" + str3), null, null };
        final AuthorizationInfo authorization = AuthorizationInfo.getAuthorization(s, n, "Digest", str, o);
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
        return new AuthorizationInfo(s, n, "Digest", str, params, array);
    }
    
    private static AuthorizationInfo digest_fixup(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final AuthorizationInfo authorizationInfo2, final RoResponse roResponse) throws AuthSchemeNotImplException {
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        NVPair[] params = null;
        if (authorizationInfo2 != null) {
            params = authorizationInfo2.getParams();
            for (int i = 0; i < params.length; ++i) {
                final String lowerCase = params[i].getName().toLowerCase();
                if (lowerCase.equals("domain")) {
                    n = i;
                }
                else if (lowerCase.equals("nonce")) {
                    n2 = i;
                }
                else if (lowerCase.equals("opaque")) {
                    n4 = i;
                }
                else if (lowerCase.equals("algorithm")) {
                    n3 = i;
                }
                else if (lowerCase.equals("stale")) {
                    n5 = i;
                }
                else if (lowerCase.equals("digest-required")) {
                    n6 = i;
                }
                else if (lowerCase.equals("qop")) {
                    n7 = i;
                }
            }
        }
        int n8 = -1;
        int n9 = -1;
        int n10 = -1;
        int n11 = -1;
        int n12 = -1;
        int n13 = -1;
        int n14 = -1;
        int length = -1;
        int n15 = -1;
        int n16 = -1;
        NVPair[] params2;
        final String[] extraInfo;
        synchronized (authorizationInfo) {
            params2 = authorizationInfo.getParams();
            for (int j = 0; j < params2.length; ++j) {
                final String lowerCase2 = params2[j].getName().toLowerCase();
                if (lowerCase2.equals("uri")) {
                    n8 = j;
                }
                else if (!lowerCase2.equals("username")) {
                    if (lowerCase2.equals("algorithm")) {
                        n9 = j;
                    }
                    else if (lowerCase2.equals("nonce")) {
                        n11 = j;
                    }
                    else if (lowerCase2.equals("cnonce")) {
                        n12 = j;
                    }
                    else if (lowerCase2.equals("nc")) {
                        n13 = j;
                    }
                    else if (lowerCase2.equals("response")) {
                        n10 = j;
                    }
                    else if (lowerCase2.equals("opaque")) {
                        n14 = j;
                    }
                    else if (lowerCase2.equals("digest")) {
                        length = j;
                    }
                    else if (lowerCase2.equals("digest-required")) {
                        n15 = j;
                    }
                    else if (lowerCase2.equals("qop")) {
                        n16 = j;
                    }
                }
            }
            extraInfo = (String[])authorizationInfo.getExtraInfo();
            if (n9 != -1 && !params2[n9].getValue().equalsIgnoreCase("MD5") && !params2[n9].getValue().equalsIgnoreCase("MD5-sess")) {
                throw new AuthSchemeNotImplException("Digest auth scheme: Algorithm " + params2[n9].getValue() + " not implemented");
            }
            if (n3 != -1 && !params[n3].getValue().equalsIgnoreCase("MD5") && !params[n3].getValue().equalsIgnoreCase("MD5-sess")) {
                throw new AuthSchemeNotImplException("Digest auth scheme: Algorithm " + params[n3].getValue() + " not implemented");
            }
            params2[n8] = new NVPair("uri", URI.escape(roRequest.getRequestURI(), URI.escpdPathChar, false));
            final String value = params2[n11].getValue();
            if (n2 != -1 && !value.equals(params[n2].getValue())) {
                params2[n11] = params[n2];
            }
            if (n4 != -1) {
                if (n14 == -1) {
                    params2 = Util.resizeArray(params2, params2.length + 1);
                    n14 = params2.length - 1;
                }
                params2[n14] = params[n4];
            }
            if (n3 != -1) {
                if (n9 == -1) {
                    params2 = Util.resizeArray(params2, params2.length + 1);
                    n9 = params2.length - 1;
                }
                params2[n9] = params[n3];
            }
            if (n7 != -1 || (n3 != -1 && params[n3].getValue().equalsIgnoreCase("MD5-sess"))) {
                if (n12 == -1) {
                    params2 = Util.resizeArray(params2, params2.length + 1);
                    n12 = params2.length - 1;
                }
                if (DefaultAuthHandler.digest_secret == null) {
                    DefaultAuthHandler.digest_secret = gen_random_bytes(20);
                }
                final long currentTimeMillis = System.currentTimeMillis();
                params2[n12] = new NVPair("cnonce", MD5.hexDigest(DefaultAuthHandler.digest_secret, new byte[] { (byte)(currentTimeMillis & 0xFFL), (byte)(currentTimeMillis >> 8 & 0xFFL), (byte)(currentTimeMillis >> 16 & 0xFFL), (byte)(currentTimeMillis >> 24 & 0xFFL), (byte)(currentTimeMillis >> 32 & 0xFFL), (byte)(currentTimeMillis >> 40 & 0xFFL), (byte)(currentTimeMillis >> 48 & 0xFFL), (byte)(currentTimeMillis >> 56 & 0xFFL) }));
            }
            if (n7 != -1) {
                if (n16 == -1) {
                    params2 = Util.resizeArray(params2, params2.length + 1);
                    n16 = params2.length - 1;
                }
                extraInfo[2] = params[n7].getValue();
                final String[] splitList = splitList(extraInfo[2], ",");
                String s = null;
                for (int k = 0; k < splitList.length; ++k) {
                    if (splitList[k].equalsIgnoreCase("auth-int") && (roRequest.getStream() == null || (roRequest.getConnection().ServProtVersKnown && roRequest.getConnection().ServerProtocolVersion >= 65537))) {
                        s = "auth-int";
                        break;
                    }
                    if (splitList[k].equalsIgnoreCase("auth")) {
                        s = "auth";
                    }
                }
                if (s == null) {
                    for (int l = 0; l < splitList.length; ++l) {
                        if (splitList[l].equalsIgnoreCase("auth-int")) {
                            throw new AuthSchemeNotImplException("Digest auth scheme: Can't comply with qop option 'auth-int' because an HttpOutputStream is being used and the server doesn't speak HTTP/1.1");
                        }
                    }
                    throw new AuthSchemeNotImplException("Digest auth scheme: None of the available qop options '" + params[n7].getValue() + "' implemented");
                }
                params2[n16] = new NVPair("qop", s);
            }
            if (n16 != -1) {
                if (n13 == -1) {
                    params2 = Util.resizeArray(params2, params2.length + 1);
                    n13 = params2.length - 1;
                    params2[n13] = new NVPair("nc", "00000001");
                }
                else if (value.equals(params2[n11].getValue())) {
                    final String hexString = Long.toHexString(Long.parseLong(params2[n13].getValue(), 16) + 1L);
                    params2[n13] = new NVPair("nc", "00000000".substring(hexString.length()) + hexString);
                }
                else {
                    params2[n13] = new NVPair("nc", "00000001");
                }
            }
            if (authorizationInfo2 != null && (n5 == -1 || !params[n5].getValue().equalsIgnoreCase("true")) && n9 != -1 && params2[n9].getValue().equalsIgnoreCase("MD5-sess")) {
                extraInfo[1] = MD5.hexDigest(extraInfo[0] + ":" + params2[n11].getValue() + ":" + params2[n12].getValue());
            }
            authorizationInfo.setParams(params2);
            authorizationInfo.setExtraInfo(extraInfo);
        }
        String hexDigest = null;
        if (n16 != -1 && params2[n16].getValue().equalsIgnoreCase("auth-int") && roRequest.getStream() == null) {
            hexDigest = MD5.hexDigest((roRequest.getData() == null) ? DefaultAuthHandler.NUL : roRequest.getData());
        }
        if (roRequest.getStream() == null) {
            params2[n10] = new NVPair("response", calcResponseAttr(hexDigest, extraInfo, params2, n9, n8, n16, n11, n13, n12, roRequest.getMethod()));
        }
        boolean b = false;
        if (n6 != -1 && (params[n6].getValue() == null || params[n6].getValue().equalsIgnoreCase("true"))) {
            b = true;
        }
        AuthorizationInfo authorizationInfo3;
        if ((b || length != -1) && roRequest.getStream() == null) {
            NVPair[] array;
            if (length == -1) {
                array = Util.resizeArray(params2, params2.length + 1);
                length = params2.length;
            }
            else {
                array = params2;
            }
            array[length] = new NVPair("digest", calc_digest(roRequest, extraInfo[0], params2[n11].getValue()));
            if (n15 == -1) {
                final int length2 = array.length;
                array = Util.resizeArray(array, array.length + 1);
                array[length2] = new NVPair("digest-required", "true");
            }
            authorizationInfo3 = new AuthorizationInfo(authorizationInfo.getHost(), authorizationInfo.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), array, extraInfo);
        }
        else if (b) {
            authorizationInfo3 = null;
        }
        else {
            authorizationInfo3 = new AuthorizationInfo(authorizationInfo.getHost(), authorizationInfo.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), params2, extraInfo);
        }
        final boolean b2 = authorizationInfo2 != null && authorizationInfo2.getHost().equalsIgnoreCase(roRequest.getConnection().getHost());
        if (n != -1) {
            URI uri = null;
            try {
                uri = new URI(roRequest.getConnection().getProtocol(), roRequest.getConnection().getHost(), roRequest.getConnection().getPort(), roRequest.getRequestURI());
            }
            catch (ParseException ex) {}
            final StringTokenizer stringTokenizer = new StringTokenizer(params[n].getValue());
            while (stringTokenizer.hasMoreTokens()) {
                URI uri2;
                try {
                    uri2 = new URI(uri, stringTokenizer.nextToken());
                }
                catch (ParseException ex2) {
                    continue;
                }
                if (uri2.getHost() == null) {
                    continue;
                }
                AuthorizationInfo authorization = AuthorizationInfo.getAuthorization(uri2.getHost(), uri2.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), roRequest.getConnection().getContext());
                if (authorization == null) {
                    params2[n8] = new NVPair("uri", uri2.getPathAndQuery());
                    authorization = new AuthorizationInfo(uri2.getHost(), uri2.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), params2, extraInfo);
                    AuthorizationInfo.addAuthorization(authorization);
                }
                if (!b2) {
                    continue;
                }
                authorization.addPath(uri2.getPathAndQuery());
            }
        }
        else if (b2 && authorizationInfo2 != null) {
            final AuthorizationInfo authorization2 = AuthorizationInfo.getAuthorization(authorizationInfo2.getHost(), authorizationInfo2.getPort(), authorizationInfo.getScheme(), authorizationInfo.getRealm(), roRequest.getConnection().getContext());
            if (authorization2 != null) {
                authorization2.addPath("/");
            }
        }
        return authorizationInfo3;
    }
    
    private static AuthorizationInfo digest_check_stale(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final RoResponse roResponse) throws AuthSchemeNotImplException, IOException {
        AuthorizationInfo authorization = null;
        final NVPair[] params = authorizationInfo.getParams();
        int i = 0;
        while (i < params.length) {
            if (params[i].getName().equalsIgnoreCase("stale") && params[i].getValue().equalsIgnoreCase("true")) {
                authorization = AuthorizationInfo.getAuthorization(authorizationInfo, roRequest, roResponse, false);
                if (authorization != null) {
                    return digest_fixup(authorization, roRequest, authorizationInfo, roResponse);
                }
                break;
            }
            else {
                ++i;
            }
        }
        return authorization;
    }
    
    private static boolean handle_nextnonce(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final HttpHeaderElement httpHeaderElement) throws IOException {
        if (authorizationInfo == null || httpHeaderElement == null || httpHeaderElement.getValue() == null) {
            return false;
        }
        AuthorizationInfo authorization;
        try {
            authorization = AuthorizationInfo.getAuthorization(authorizationInfo, roRequest, null, false);
        }
        catch (AuthSchemeNotImplException ex) {
            authorization = authorizationInfo;
        }
        synchronized (authorization) {
            authorization.setParams(setValue(setValue(authorization.getParams(), "nonce", httpHeaderElement.getValue()), "nc", "00000000"));
        }
        return true;
    }
    
    private static boolean handle_digest(final AuthorizationInfo authorizationInfo, final Response response, final RoRequest roRequest, final String s) throws IOException {
        if (authorizationInfo == null) {
            return false;
        }
        final NVPair[] params = authorizationInfo.getParams();
        final VerifyDigest verifyDigest = new VerifyDigest(((String[])authorizationInfo.getExtraInfo())[0], getValue(params, "nonce"), roRequest.getMethod(), getValue(params, "uri"), s, response);
        if (response.hasEntity()) {
            Log.write(8, "Auth:  pushing md5-check-stream to verify digest from " + s);
            response.inp_stream = new MD5InputStream(response.inp_stream, verifyDigest);
        }
        else {
            Log.write(8, "Auth:  verifying digest from " + s);
            verifyDigest.verifyHash(MD5.digest(DefaultAuthHandler.NUL), 0L);
        }
        return true;
    }
    
    private static boolean handle_rspauth(final AuthorizationInfo authorizationInfo, final Response response, final RoRequest roRequest, final Vector vector, final String s) throws IOException {
        if (authorizationInfo == null) {
            return false;
        }
        final NVPair[] params = authorizationInfo.getParams();
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        for (int i = 0; i < params.length; ++i) {
            final String lowerCase = params[i].getName().toLowerCase();
            if (lowerCase.equals("uri")) {
                n = i;
            }
            else if (lowerCase.equals("algorithm")) {
                n2 = i;
            }
            else if (lowerCase.equals("nonce")) {
                n3 = i;
            }
            else if (lowerCase.equals("cnonce")) {
                n4 = i;
            }
            else if (lowerCase.equals("nc")) {
                n5 = i;
            }
        }
        final VerifyRspAuth verifyRspAuth = new VerifyRspAuth(params[n].getValue(), ((String[])authorizationInfo.getExtraInfo())[0], (n2 == -1) ? null : params[n2].getValue(), params[n3].getValue(), (n4 == -1) ? "" : params[n4].getValue(), (n5 == -1) ? "" : params[n5].getValue(), s, response);
        final HttpHeaderElement element;
        if (vector != null && (element = Util.getElement(vector, "qop")) != null && element.getValue() != null && (element.getValue().equalsIgnoreCase("auth") || (!response.hasEntity() && element.getValue().equalsIgnoreCase("auth-int")))) {
            Log.write(8, "Auth:  verifying rspauth from " + s);
            verifyRspAuth.verifyHash(MD5.digest(DefaultAuthHandler.NUL), 0L);
        }
        else {
            Log.write(8, "Auth:  pushing md5-check-stream to verify rspauth from " + s);
            response.inp_stream = new MD5InputStream(response.inp_stream, verifyRspAuth);
        }
        return true;
    }
    
    private static String calcResponseAttr(final String str, final String[] array, final NVPair[] array2, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final String str2) {
        String s;
        if (n != -1 && array2[n].getValue().equalsIgnoreCase("MD5-sess")) {
            s = array[1];
        }
        else {
            s = array[0];
        }
        String str3 = str2 + ":" + array2[n2].getValue();
        if (n3 != -1 && array2[n3].getValue().equalsIgnoreCase("auth-int")) {
            str3 = str3 + ":" + str;
        }
        final String hexDigest = MD5.hexDigest(str3);
        String s2;
        if (n3 == -1) {
            s2 = MD5.hexDigest(s + ":" + array2[n4].getValue() + ":" + hexDigest);
        }
        else {
            s2 = MD5.hexDigest(s + ":" + array2[n4].getValue() + ":" + array2[n5].getValue() + ":" + array2[n6].getValue() + ":" + array2[n3].getValue() + ":" + hexDigest);
        }
        return s2;
    }
    
    private static String calc_digest(final RoRequest roRequest, final String str, final String str2) {
        if (roRequest.getStream() != null) {
            return "";
        }
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        for (int i = 0; i < roRequest.getHeaders().length; ++i) {
            final String name = roRequest.getHeaders()[i].getName();
            if (name.equalsIgnoreCase("Content-type")) {
                n = i;
            }
            else if (name.equalsIgnoreCase("Content-Encoding")) {
                n2 = i;
            }
            else if (name.equalsIgnoreCase("Last-Modified")) {
                n3 = i;
            }
            else if (name.equalsIgnoreCase("Expires")) {
                n4 = i;
            }
            else if (name.equalsIgnoreCase("Date")) {
                n5 = i;
            }
        }
        final NVPair[] headers = roRequest.getHeaders();
        final byte[] array = (roRequest.getData() == null) ? DefaultAuthHandler.NUL : roRequest.getData();
        final String hexDigest = MD5.hexDigest(array);
        final String string = str + ":" + str2 + ":" + roRequest.getMethod() + ":" + ((n5 == -1) ? "" : headers[n5].getValue()) + ":" + MD5.hexDigest(roRequest.getRequestURI() + ":" + ((n == -1) ? "" : headers[n].getValue()) + ":" + array.length + ":" + ((n2 == -1) ? "" : headers[n2].getValue()) + ":" + ((n3 == -1) ? "" : headers[n3].getValue()) + ":" + ((n4 == -1) ? "" : headers[n4].getValue())) + ":" + hexDigest;
        if (Log.isEnabled(8)) {
            Log.write(8, "Auth:  Entity-Info: '" + roRequest.getRequestURI() + ":" + ((n == -1) ? "" : headers[n].getValue()) + ":" + array.length + ":" + ((n2 == -1) ? "" : headers[n2].getValue()) + ":" + ((n3 == -1) ? "" : headers[n3].getValue()) + ":" + ((n4 == -1) ? "" : headers[n4].getValue()) + "'");
            Log.write(8, "Auth:  Entity-Body: '" + hexDigest + "'");
            Log.write(8, "Auth:  Entity-Digest: '" + string + "'");
        }
        return MD5.hexDigest(string);
    }
    
    private static boolean handle_discard(final AuthorizationInfo authorizationInfo, final RoRequest roRequest, final HttpHeaderElement httpHeaderElement) {
        if (httpHeaderElement != null && authorizationInfo != null) {
            AuthorizationInfo.removeAuthorization(authorizationInfo, roRequest.getConnection().getContext());
            return true;
        }
        return false;
    }
    
    private static byte[] gen_random_bytes(final int n) {
        try {
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream("/dev/random"));
            final byte[] b = new byte[n];
            dataInputStream.readFully(b);
            try {
                dataInputStream.close();
            }
            catch (IOException ex) {}
            return b;
        }
        catch (Throwable t) {
            final byte[] array = new byte[n];
            try {
                final long freeMemory = Runtime.getRuntime().freeMemory();
                array[0] = (byte)(freeMemory & 0xFFL);
                array[1] = (byte)(freeMemory >> 8 & 0xFFL);
                final int hashCode = array.hashCode();
                array[2] = (byte)(hashCode & 0xFF);
                array[3] = (byte)(hashCode >> 8 & 0xFF);
                array[4] = (byte)(hashCode >> 16 & 0xFF);
                array[5] = (byte)(hashCode >> 24 & 0xFF);
                final long currentTimeMillis = System.currentTimeMillis();
                array[6] = (byte)(currentTimeMillis & 0xFFL);
                array[7] = (byte)(currentTimeMillis >> 8 & 0xFFL);
            }
            catch (ArrayIndexOutOfBoundsException ex2) {}
            return array;
        }
    }
    
    private static final String getValue(final NVPair[] array, final String anotherString) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i].getName().equalsIgnoreCase(anotherString)) {
                return array[i].getValue();
            }
        }
        return null;
    }
    
    private static final int getIndex(final NVPair[] array, final String anotherString) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i].getName().equalsIgnoreCase(anotherString)) {
                return i;
            }
        }
        return -1;
    }
    
    private static final NVPair[] setValue(NVPair[] resizeArray, final String s, final String s2) {
        int n = getIndex(resizeArray, s);
        if (n == -1) {
            n = resizeArray.length;
            resizeArray = Util.resizeArray(resizeArray, resizeArray.length + 1);
        }
        resizeArray[n] = new NVPair(s, s2);
        return resizeArray;
    }
    
    private static String[] splitList(final String str, final String delim) {
        if (str == null) {
            return new String[0];
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, delim);
        final String[] array = new String[stringTokenizer.countTokens()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = stringTokenizer.nextToken().trim();
        }
        return array;
    }
    
    static String hex(final byte[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 3);
        for (int i = 0; i < array.length; ++i) {
            sb.append(Character.forDigit(array[i] >> 4 & 0xF, 16));
            sb.append(Character.forDigit(array[i] & 0xF, 16));
            sb.append(':');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    
    static final byte[] unHex(final String s) {
        final byte[] array = new byte[s.length() / 2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (byte)(0xFF & Integer.parseInt(s.substring(2 * i, 2 * (i + 1)), 16));
        }
        return array;
    }
    
    public static synchronized AuthorizationPrompter setAuthorizationPrompter(final AuthorizationPrompter prompter) {
        final AuthorizationPrompter prompter2 = DefaultAuthHandler.prompter;
        DefaultAuthHandler.prompter = prompter;
        DefaultAuthHandler.prompterSet = true;
        return prompter2;
    }
    
    private static void setDefaultPrompter() {
        if (!SimpleAuthPrompt.canUseCLPrompt() || isAWTRunning()) {
            DefaultAuthHandler.prompter = new SimpleAuthPopup();
        }
        else {
            DefaultAuthHandler.prompter = new SimpleAuthPrompt();
        }
    }
    
    private static final boolean isAWTRunning() {
        ThreadGroup threadGroup;
        for (threadGroup = Thread.currentThread().getThreadGroup(); threadGroup.getParent() != null; threadGroup = threadGroup.getParent()) {}
        final Thread[] list = new Thread[threadGroup.activeCount() + 5];
        for (int enumerate = threadGroup.enumerate(list), i = 0; i < enumerate; ++i) {
            if (list[i].getName().startsWith("AWT-")) {
                return true;
            }
        }
        return false;
    }
    
    static {
        NUL = new byte[0];
        DefaultAuthHandler.digest_secret = null;
        DefaultAuthHandler.prompter = null;
        DefaultAuthHandler.prompterSet = false;
    }
}
