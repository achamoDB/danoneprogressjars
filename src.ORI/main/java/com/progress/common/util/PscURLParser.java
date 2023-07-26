// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.StringTokenizer;
import java.net.MalformedURLException;

public class PscURLParser
{
    private static final String m_defHost = "localhost";
    private static final String m_defPath = "/";
    private static final String[] m_validSchemes;
    public static final String[] m_defPorts;
    private String m_scheme;
    private String m_authority;
    private String m_authorityUser;
    private String m_authorityPwd;
    private String m_authorityHost;
    private String m_authorityPort;
    private String m_path;
    private String m_query;
    private String m_fragment;
    
    public PscURLParser() {
        this.m_scheme = PscURLParser.m_validSchemes[0];
        this.m_authority = "localhost";
        this.m_authorityUser = null;
        this.m_authorityPwd = "";
        this.m_authorityHost = "localhost";
        this.m_authorityPort = null;
        this.m_path = "/";
        this.m_query = null;
        this.m_fragment = null;
    }
    
    public PscURLParser(final String s) throws MalformedURLException {
        this.m_scheme = PscURLParser.m_validSchemes[0];
        this.m_authority = "localhost";
        this.m_authorityUser = null;
        this.m_authorityPwd = "";
        this.m_authorityHost = "localhost";
        this.m_authorityPort = null;
        this.m_path = "/";
        this.m_query = null;
        this.m_fragment = null;
        if (null != s && 0 < s.length()) {
            this.parseURL(s);
        }
    }
    
    public String getScheme() {
        return (null != this.m_scheme) ? new String(this.m_scheme) : null;
    }
    
    public String getAuthority() {
        final StringBuffer sb = new StringBuffer(512);
        if (null != this.m_authorityUser) {
            sb.append(this.m_authorityUser);
            if (null != this.m_authorityPwd) {
                sb.append(":");
                sb.append(this.m_authorityPwd);
            }
            sb.append("@");
        }
        if (null != this.m_authorityHost) {
            sb.append(this.m_authorityHost);
            if (null != this.m_authorityPort) {
                sb.append(":");
                sb.append(this.m_authorityPort);
            }
        }
        return (0 < sb.length()) ? new String(sb.toString()) : null;
    }
    
    public String getPath() {
        return new String(this.m_path);
    }
    
    public String getQuery() {
        return (null == this.m_query) ? null : new String(this.m_query);
    }
    
    public String getFragment() {
        return (null == this.m_fragment) ? null : new String(this.m_fragment);
    }
    
    public String getUserId() {
        return (null == this.m_authorityUser) ? null : new String(this.m_authorityUser);
    }
    
    public String getUserPassword() {
        return (null == this.m_authorityPwd) ? null : new String(this.m_authorityPwd);
    }
    
    public String getHost() {
        return new String(this.m_authorityHost);
    }
    
    public int getPort() {
        if (null == this.m_authorityPort) {
            return Integer.parseInt(this.getDefaultPort(this.m_scheme));
        }
        return Integer.parseInt(this.m_authorityPort);
    }
    
    public void setScheme(final String scheme) {
        if (null != scheme && 0 < scheme.length()) {
            this.m_scheme = scheme;
        }
        else {
            this.m_scheme = PscURLParser.m_validSchemes[0];
        }
    }
    
    public String getService() {
        String s = null;
        if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[0]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[1]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[4]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5])) {
            if (1 < this.m_path.length()) {
                s = this.m_path.substring(1);
            }
        }
        else if ((this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[2]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[3])) && null != this.m_query) {
            final StringTokenizer stringTokenizer = new StringTokenizer(this.m_query, "&");
            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                if (nextToken.startsWith("AppService=")) {
                    s = nextToken.substring(nextToken.indexOf("=") + 1);
                    break;
                }
            }
        }
        return s;
    }
    
    public void setPath(final String s) {
        if (null != s && 0 < s.length()) {
            if (s.startsWith("/")) {
                this.m_path = s;
            }
            else {
                this.m_path = "/" + s;
            }
        }
        else {
            this.m_path = "/";
        }
    }
    
    public void setQuery(final String query) {
        if (null != query && 0 < query.length()) {
            if (query.startsWith("?")) {
                if (1 < query.length()) {
                    this.m_query = query.substring(1);
                }
                else {
                    this.m_query = null;
                }
            }
            else {
                this.m_query = query;
            }
        }
        else {
            this.m_query = null;
        }
    }
    
    public void setFragment(final String fragment) {
        if (null != fragment && 0 < fragment.length()) {
            if (fragment.startsWith("#")) {
                if (1 < fragment.length()) {
                    this.m_fragment = fragment.substring(1);
                }
                else {
                    this.m_fragment = null;
                }
            }
            else {
                this.m_fragment = fragment;
            }
        }
        else {
            this.m_fragment = null;
        }
    }
    
    public void setUserId(final String authorityUser) {
        if (null != authorityUser && 0 < authorityUser.length()) {
            this.m_authorityUser = authorityUser;
        }
        else {
            this.m_authorityUser = null;
            this.m_authorityPwd = null;
        }
    }
    
    public void setUserPassword(final String authorityPwd) {
        if (null != authorityPwd && 0 < authorityPwd.length()) {
            if (null != this.m_authorityUser && 0 < this.m_authorityUser.length()) {
                this.m_authorityPwd = authorityPwd;
            }
        }
        else if (null != this.m_authorityUser && 0 < this.m_authorityUser.length()) {
            this.m_authorityPwd = "";
        }
        else {
            this.m_authorityPwd = null;
        }
    }
    
    public void setHost(final String authorityHost) {
        if (null != authorityHost && 0 < authorityHost.length()) {
            this.m_authorityHost = authorityHost;
        }
        else {
            this.m_authorityHost = "localhost";
        }
    }
    
    public void setPort(final int i) {
        int n = i;
        if (-1 == n) {
            this.m_authorityPort = null;
        }
        else {
            if (3 > n) {
                n = 3;
            }
            if (65536 < n) {}
            this.m_authorityPort = Integer.toString(i);
        }
    }
    
    public void setPort(final String s) {
        if (null == s || 0 == s.length()) {
            this.m_authorityPort = null;
        }
        else {
            try {
                this.m_authorityPort = Integer.toString(Integer.parseInt(s));
            }
            catch (Exception ex) {}
        }
    }
    
    public void setService(final String s) {
        final StringBuffer sb = new StringBuffer();
        if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[0]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[1]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[4]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5])) {
            this.m_path = "/";
            if (null != s && 0 < s.length()) {
                sb.append("/");
                sb.append(s);
                this.m_path = sb.toString();
            }
        }
        else if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[2]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[3])) {
            if (null != this.m_query) {
                this.m_query = null;
            }
            if (null != s && 0 < s.length()) {
                sb.append("AppService=");
                sb.append(s);
                this.m_query = sb.toString();
            }
        }
    }
    
    public void parseURL(final String s) throws MalformedURLException {
        final int beginIndex = 0;
        final int length = s.length();
        int n = 0;
        final int n2 = length - 1;
        this.init();
        if (0 < length) {
            final int index = s.indexOf(":", beginIndex);
            final int index2 = s.indexOf("/", beginIndex);
            final int n3 = (-1 == index2) ? length : index2;
            if (-1 != index && index < n3 && index > beginIndex) {
                this.m_scheme = s.substring(beginIndex, index);
                n = index + 1;
            }
            if (n <= n2) {
                int index3 = s.indexOf("//", n);
                if (-1 != index3) {
                    index3 += 2;
                    int endIndex = s.indexOf("/", index3);
                    if (-1 == endIndex) {
                        endIndex = s.indexOf("?", index3);
                    }
                    if (-1 == endIndex) {
                        endIndex = s.indexOf("#", index3);
                    }
                    if (-1 != endIndex) {
                        if (endIndex > index3) {
                            this.m_authority = s.substring(index3, endIndex);
                        }
                        n = endIndex;
                    }
                    else if (0 < n2 - index3) {
                        this.m_authority = s.substring(index3);
                        n = n2 + 1;
                    }
                    else {
                        n = index3;
                    }
                    if (null != this.m_authority && 0 < this.m_authority.length()) {
                        this.parseAuthority();
                    }
                }
            }
            if (n <= n2) {
                final int index4 = s.indexOf("/", n);
                if (-1 != index4) {
                    int endIndex2 = s.indexOf("?", index4);
                    if (-1 == endIndex2) {
                        endIndex2 = s.indexOf("#", index4);
                    }
                    if (-1 != endIndex2) {
                        if (endIndex2 > index4) {
                            this.m_path = s.substring(index4, endIndex2);
                        }
                        n = endIndex2;
                    }
                    else if (0 < n2 - index4) {
                        this.m_path = s.substring(index4);
                        n = n2 + 1;
                    }
                    else {
                        n = index4 + 1;
                    }
                }
            }
            if (n <= n2) {
                int index5 = s.indexOf("?", n);
                if (-1 != index5) {
                    ++index5;
                    final int index6 = s.indexOf("#", index5);
                    if (-1 != index6) {
                        if (index6 > index5) {
                            this.m_query = s.substring(index5, index6);
                        }
                        n = index6;
                    }
                    else if (0 < n2 - index5) {
                        this.m_query = s.substring(index5);
                        n = n2 + 1;
                    }
                    else {
                        n = index5 + 1;
                    }
                }
            }
            if (n < n2) {
                if (0 == this.m_path.compareTo("/") && null == this.m_query) {
                    this.m_path += s.substring(n);
                }
                else {
                    this.m_fragment = s.substring(++n);
                }
            }
        }
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(512);
        sb.append(this.m_scheme);
        sb.append("://");
        if (null != this.m_authorityUser) {
            sb.append(this.m_authorityUser);
            if (null != this.m_authorityPwd) {
                sb.append(":");
                sb.append(this.m_authorityPwd);
            }
            sb.append("@");
        }
        if (null != this.m_authorityHost) {
            sb.append(this.m_authorityHost);
            if (null != this.m_authorityPort) {
                sb.append(":");
                sb.append(this.m_authorityPort);
            }
            else if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[0]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[1]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[4]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5])) {
                sb.append(":");
                sb.append(this.getDefaultPort(this.m_scheme));
            }
        }
        sb.append(this.m_path);
        if (null != this.m_query) {
            sb.append("?");
            sb.append(this.m_query);
        }
        if (null != this.m_fragment) {
            sb.append("#");
            sb.append(this.m_fragment);
        }
        return new String(sb.toString());
    }
    
    public String getURL() throws MalformedURLException {
        boolean b = false;
        for (int i = 0; i < PscURLParser.m_validSchemes.length; ++i) {
            if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[i])) {
                b = true;
            }
        }
        if (!b) {
            throw new MalformedURLException("URL' scheme is not supported: " + this.m_authorityPort);
        }
        int n;
        try {
            if (null != this.m_authorityPort) {
                n = Integer.parseInt(this.m_authorityPort);
            }
            else {
                n = Integer.parseInt(this.getDefaultPort(this.m_scheme));
            }
        }
        catch (Exception ex) {
            throw new MalformedURLException("URL' port number is invalid: " + this.m_authorityPort);
        }
        if (2 > n || 65536 < n) {
            throw new MalformedURLException("URL' port number is invalid: " + this.m_authorityPort);
        }
        return this.toString();
    }
    
    public String getAppServerURL(final String s) throws MalformedURLException {
        if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[0]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[4])) {
            if (0 == this.m_path.compareTo("/")) {
                if (null == s || 0 >= s.length()) {
                    throw new MalformedURLException("Missing service name");
                }
                this.m_path += s;
            }
            if (-1 != this.m_path.indexOf("/", 1)) {
                throw new MalformedURLException("The URL's service name includes a sub-path");
            }
        }
        else if (this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[2]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[3])) {
            final String service = this.getService();
            if (null == service) {
                this.setService(null);
            }
            else {
                this.setService(service);
            }
            if (null == this.m_query) {
                if (null == s || 0 >= s.length()) {
                    throw new MalformedURLException("Missing service name");
                }
                if (-1 != s.indexOf("/", 1)) {
                    throw new MalformedURLException("The URL's service name includes a sub-path");
                }
                this.m_query = "AppService=" + s;
            }
            int n;
            try {
                if (null != this.m_authorityPort) {
                    n = Integer.parseInt(this.m_authorityPort);
                }
                else {
                    n = Integer.parseInt(this.getDefaultPort(this.m_scheme));
                }
            }
            catch (Exception ex) {
                throw new MalformedURLException("URL' port number is invalid: " + this.m_authorityPort);
            }
            if (2 > n || 65536 < n) {
                throw new MalformedURLException("URL' port number is invalid: " + this.m_authorityPort);
            }
            if (0 == this.m_path.compareTo("/")) {
                throw new MalformedURLException("The URL is missing the AIA adapter path");
            }
        }
        else if (!this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[1]) && !this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5])) {
            throw new MalformedURLException("Unsupported network scheme: " + this.m_scheme);
        }
        return this.toString();
    }
    
    public String getDefaultPort(final String s) {
        String s2 = "";
        if (null != s && 0 < s.length()) {
            for (int i = 0; i < PscURLParser.m_validSchemes.length; ++i) {
                if (s.equalsIgnoreCase(PscURLParser.m_validSchemes[i])) {
                    s2 = PscURLParser.m_defPorts[i];
                    break;
                }
            }
        }
        return s2;
    }
    
    public void run(final String[] array) throws Exception {
        if (1 > array.length) {
            System.out.println("usage: PscURLParser <url> [def_service]");
        }
        else {
            this.parseURL(array[0]);
            System.out.println("Results:");
            System.out.println("    scheme:    " + this.getScheme());
            System.out.println("    authority: " + this.getAuthority());
            if (null != this.m_authority) {
                if (null != this.m_authorityUser) {
                    System.out.println("        user info:     ");
                    System.out.println("            uid: " + this.getUserId());
                    System.out.println("            pwd: " + this.getUserPassword());
                }
                if (null != this.m_authorityHost) {
                    System.out.println("        net addr: ");
                    System.out.println("            host: " + this.getHost());
                    System.out.println("            port: " + Integer.toString(this.getPort()));
                }
            }
            System.out.println("    path:      " + this.getPath());
            System.out.println("    query:     " + this.getQuery());
            System.out.println("    fragment:  " + this.getFragment());
            System.out.println("toString(): " + this.toString());
            System.out.println("getURL(): " + this.getURL());
            System.out.println("Resulting AppServer URL: " + this.getAppServerURL((1 < array.length) ? array[1] : null));
            System.out.println("Service: " + this.getService());
            this.setService(null);
            System.out.println("Remove Service: " + this.getURL());
            this.setService("newService");
            System.out.println("set Service: " + this.getURL());
        }
    }
    
    public static void main(final String[] array) {
        final PscURLParser pscURLParser = new PscURLParser();
        try {
            try {
                pscURLParser.run(array);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception ex2) {
            System.out.println("Error: " + ex2.toString());
        }
    }
    
    protected void init() {
        this.m_scheme = PscURLParser.m_validSchemes[0];
        this.m_authority = "localhost";
        this.m_authorityUser = null;
        this.m_authorityPwd = "";
        this.m_authorityHost = "localhost";
        this.m_authorityPort = null;
        this.m_path = "/";
        this.m_query = null;
        this.m_fragment = null;
    }
    
    protected void parseAuthority() throws MalformedURLException {
        int index = this.m_authority.indexOf("@");
        String substring = null;
        String authorityHost;
        if (-1 != index) {
            substring = this.m_authority.substring(0, index);
            authorityHost = this.m_authority.substring(++index);
        }
        else {
            authorityHost = this.m_authority;
        }
        if (null != substring) {
            int lastIndex = substring.lastIndexOf(":");
            if (-1 != lastIndex) {
                this.m_authorityUser = substring.substring(0, lastIndex);
                this.m_authorityPwd = substring.substring(++lastIndex);
            }
            else {
                this.m_authorityUser = substring;
            }
        }
        if (null != authorityHost) {
            if (authorityHost.charAt(0) == '[') {
                int index2 = authorityHost.indexOf("]");
                if (index2 == -1) {
                    throw new MalformedURLException("Invalid IPv6 literal address: " + authorityHost);
                }
                this.m_authorityHost = authorityHost.substring(1, index2++);
                if (authorityHost.length() > index2) {
                    if (authorityHost.charAt(index2) != ':') {
                        throw new MalformedURLException("Invalid IPv6 literal address: " + authorityHost);
                    }
                    ++index2;
                    this.m_authorityPort = this.parseAuthorityPort(authorityHost.substring(index2));
                }
            }
            else {
                int lastIndex2;
                if ((lastIndex2 = authorityHost.lastIndexOf(":")) != -1) {
                    this.m_authorityHost = authorityHost.substring(0, lastIndex2);
                    this.m_authorityPort = this.parseAuthorityPort(authorityHost.substring(++lastIndex2));
                }
                else {
                    this.m_authorityHost = authorityHost;
                }
            }
        }
    }
    
    private String parseAuthorityPort(final String s) throws MalformedURLException {
        String s2 = s;
        if (0 == s2.compareTo(this.getDefaultPort(this.m_scheme))) {
            s2 = null;
        }
        else {
            int int1;
            try {
                int1 = Integer.parseInt(s2);
            }
            catch (Exception ex) {
                throw new MalformedURLException("Invalid numeric port number: " + this.m_authorityPort);
            }
            if (2 >= int1 || 65536 < int1) {
                throw new MalformedURLException("Invalid numeric port number: " + this.m_authorityPort);
            }
        }
        return s2;
    }
    
    public boolean isDirectConnect() {
        return this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[1]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[2]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[3]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5]);
    }
    
    public boolean isSSLConnect() {
        return this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[4]) || this.m_scheme.equalsIgnoreCase(PscURLParser.m_validSchemes[5]);
    }
    
    static {
        m_validSchemes = new String[] { "AppServer", "AppServerDC", "http", "https", "AppServerS", "AppServerDCS" };
        m_defPorts = new String[] { "5162", "3090", "80", "443", "5162", "3090" };
    }
}
