// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.URLConnection;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.net.ProtocolException;
import java.io.IOException;
import java.net.URL;
import java.io.OutputStream;
import java.util.Hashtable;

public class HttpURLConnection extends java.net.HttpURLConnection
{
    protected static Hashtable connections;
    protected HTTPConnection con;
    private String urlString;
    private String resource;
    private String method;
    private boolean method_set;
    private static NVPair[] default_headers;
    private NVPair[] headers;
    protected HTTPResponse resp;
    private boolean do_redir;
    private static Class redir_mod;
    private OutputStream output_stream;
    private static String non_proxy_hosts;
    private static String proxy_host;
    private static int proxy_port;
    private String[] hdr_keys;
    private String[] hdr_values;
    
    public HttpURLConnection(final URL url) throws ProtocolNotSuppException, IOException {
        super(url);
        try {
            final String hosts = System.getProperty("http.nonProxyHosts", "");
            if (!hosts.equalsIgnoreCase(HttpURLConnection.non_proxy_hosts)) {
                HttpURLConnection.connections.clear();
                HttpURLConnection.non_proxy_hosts = hosts;
                final String[] list = Util.splitProperty(hosts);
                for (int idx = 0; idx < list.length; ++idx) {
                    HTTPConnection.dontProxyFor(list[idx]);
                }
            }
        }
        catch (ParseException pe) {
            throw new IOException(pe.toString());
        }
        catch (SecurityException ex) {}
        try {
            final String host = System.getProperty("http.proxyHost", "");
            final int port = Integer.getInteger("http.proxyPort", -1);
            if (!host.equalsIgnoreCase(HttpURLConnection.proxy_host) || port != HttpURLConnection.proxy_port) {
                HttpURLConnection.connections.clear();
                HTTPConnection.setProxyServer(HttpURLConnection.proxy_host = host, HttpURLConnection.proxy_port = port);
            }
        }
        catch (SecurityException ex2) {}
        this.con = this.getConnection(url);
        this.method = "GET";
        this.method_set = false;
        this.resource = url.getFile();
        this.headers = HttpURLConnection.default_headers;
        this.do_redir = java.net.HttpURLConnection.getFollowRedirects();
        this.output_stream = null;
        this.urlString = url.toString();
    }
    
    protected HTTPConnection getConnection(final URL url) throws ProtocolNotSuppException {
        String php = String.valueOf(url.getProtocol()) + ":" + url.getHost() + ":" + ((url.getPort() != -1) ? url.getPort() : URI.defaultPort(url.getProtocol()));
        php = php.toLowerCase();
        HTTPConnection con = HttpURLConnection.connections.get(php);
        if (con != null) {
            return con;
        }
        con = new HTTPConnection(url);
        HttpURLConnection.connections.put(php, con);
        return con;
    }
    
    public void setRequestMethod(final String method) throws ProtocolException {
        if (super.connected) {
            throw new ProtocolException("Already connected!");
        }
        Log.write(128, "URLC:  (" + this.urlString + ") Setting request method: " + method);
        this.method = method.trim().toUpperCase();
        this.method_set = true;
    }
    
    public String getRequestMethod() {
        return this.method;
    }
    
    public int getResponseCode() throws IOException {
        if (!super.connected) {
            this.connect();
        }
        try {
            return this.resp.getStatusCode();
        }
        catch (ModuleException me) {
            throw new IOException(me.toString());
        }
    }
    
    public String getResponseMessage() throws IOException {
        if (!super.connected) {
            this.connect();
        }
        try {
            return this.resp.getReasonLine();
        }
        catch (ModuleException me) {
            throw new IOException(me.toString());
        }
    }
    
    public String getHeaderField(final String name) {
        try {
            if (!super.connected) {
                this.connect();
            }
            return this.resp.getHeader(name);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public int getHeaderFieldInt(final String name, final int def) {
        try {
            if (!super.connected) {
                this.connect();
            }
            return this.resp.getHeaderAsInt(name);
        }
        catch (Exception ex) {
            return def;
        }
    }
    
    public long getHeaderFieldDate(final String name, final long def) {
        try {
            if (!super.connected) {
                this.connect();
            }
            return this.resp.getHeaderAsDate(name).getTime();
        }
        catch (Exception ex) {
            return def;
        }
    }
    
    public String getHeaderFieldKey(final int n) {
        if (this.hdr_keys == null) {
            this.fill_hdr_arrays();
        }
        if (n >= 0 && n < this.hdr_keys.length) {
            return this.hdr_keys[n];
        }
        return null;
    }
    
    public String getHeaderField(final int n) {
        if (this.hdr_values == null) {
            this.fill_hdr_arrays();
        }
        if (n >= 0 && n < this.hdr_values.length) {
            return this.hdr_values[n];
        }
        return null;
    }
    
    private void fill_hdr_arrays() {
        try {
            if (!super.connected) {
                this.connect();
            }
            int num = 1;
            Enumeration enum1 = this.resp.listHeaders();
            while (enum1.hasMoreElements()) {
                ++num;
                enum1.nextElement();
            }
            this.hdr_keys = new String[num];
            this.hdr_values = new String[num];
            enum1 = this.resp.listHeaders();
            for (int idx = 1; idx < num; ++idx) {
                this.hdr_keys[idx] = enum1.nextElement();
                this.hdr_values[idx] = this.resp.getHeader(this.hdr_keys[idx]);
            }
            this.hdr_values[0] = String.valueOf(this.resp.getVersion()) + " " + this.resp.getStatusCode() + " " + this.resp.getReasonLine();
        }
        catch (Exception ex) {
            final String[] array = new String[0];
            this.hdr_values = array;
            this.hdr_keys = array;
        }
    }
    
    public InputStream getInputStream() throws IOException {
        if (!super.doInput) {
            throw new ProtocolException("Input not enabled! (use setDoInput(true))");
        }
        if (!super.connected) {
            this.connect();
        }
        InputStream stream;
        try {
            stream = this.resp.getInputStream();
        }
        catch (ModuleException e) {
            throw new IOException(e.toString());
        }
        return stream;
    }
    
    public InputStream getErrorStream() {
        try {
            if (!super.doInput || !super.connected || this.resp.getStatusCode() < 300 || this.resp.getHeaderAsInt("Content-length") <= 0) {
                return null;
            }
            return this.resp.getInputStream();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public synchronized OutputStream getOutputStream() throws IOException {
        if (super.connected) {
            throw new ProtocolException("Already connected!");
        }
        if (!super.doOutput) {
            throw new ProtocolException("Output not enabled! (use setDoOutput(true))");
        }
        if (!this.method_set) {
            this.method = "POST";
        }
        else if (this.method.equals("HEAD") || this.method.equals("GET") || this.method.equals("TRACE")) {
            throw new ProtocolException("Method " + this.method + " does not support output!");
        }
        if (this.getRequestProperty("Content-type") == null) {
            this.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        }
        if (this.output_stream == null) {
            Log.write(128, "URLC:  (" + this.urlString + ") creating output stream");
            final String cl = this.getRequestProperty("Content-Length");
            if (cl != null) {
                this.output_stream = new HttpOutputStream(Integer.parseInt(cl.trim()));
            }
            else if (this.getRequestProperty("Content-type").equals("application/x-www-form-urlencoded")) {
                this.output_stream = new ByteArrayOutputStream(300);
            }
            else {
                this.output_stream = new HttpOutputStream();
            }
            if (this.output_stream instanceof HttpOutputStream) {
                this.connect();
            }
        }
        return this.output_stream;
    }
    
    public URL getURL() {
        if (super.connected) {
            try {
                return this.resp.getEffectiveURI().toURL();
            }
            catch (Exception ex) {
                return null;
            }
        }
        return super.url;
    }
    
    public void setIfModifiedSince(final long time) {
        super.setIfModifiedSince(time);
        this.setRequestProperty("If-Modified-Since", Util.httpDate(new Date(time)));
    }
    
    public void setRequestProperty(final String name, final String value) {
        Log.write(128, "URLC:  (" + this.urlString + ") Setting request property: " + name + " : " + value);
        int idx;
        for (idx = 0; idx < this.headers.length && !this.headers[idx].getName().equalsIgnoreCase(name); ++idx) {}
        if (idx == this.headers.length) {
            this.headers = Util.resizeArray(this.headers, idx + 1);
        }
        this.headers[idx] = new NVPair(name, value);
    }
    
    public String getRequestProperty(final String name) {
        for (int idx = 0; idx < this.headers.length; ++idx) {
            if (this.headers[idx].getName().equalsIgnoreCase(name)) {
                return this.headers[idx].getValue();
            }
        }
        return null;
    }
    
    public static void setDefaultRequestProperty(final String name, final String value) {
        Log.write(128, "URLC:  Setting default request property: " + name + " : " + value);
        int idx;
        for (idx = 0; idx < HttpURLConnection.default_headers.length && !HttpURLConnection.default_headers[idx].getName().equalsIgnoreCase(name); ++idx) {}
        if (idx == HttpURLConnection.default_headers.length) {
            HttpURLConnection.default_headers = Util.resizeArray(HttpURLConnection.default_headers, idx + 1);
        }
        HttpURLConnection.default_headers[idx] = new NVPair(name, value);
    }
    
    public static String getDefaultRequestProperty(final String name) {
        for (int idx = 0; idx < HttpURLConnection.default_headers.length; ++idx) {
            if (HttpURLConnection.default_headers[idx].getName().equalsIgnoreCase(name)) {
                return HttpURLConnection.default_headers[idx].getValue();
            }
        }
        return null;
    }
    
    public void setInstanceFollowRedirects(final boolean set) {
        if (super.connected) {
            throw new IllegalStateException("Already connected!");
        }
        this.do_redir = set;
    }
    
    public boolean getInstanceFollowRedirects() {
        return this.do_redir;
    }
    
    public synchronized void connect() throws IOException {
        if (super.connected) {
            return;
        }
        Log.write(128, "URLC:  (" + this.urlString + ") Connecting ...");
        synchronized (this.con) {
            this.con.setAllowUserInteraction(super.allowUserInteraction);
            if (this.do_redir) {
                this.con.addModule(HttpURLConnection.redir_mod, 2);
            }
            else {
                this.con.removeModule(HttpURLConnection.redir_mod);
            }
            try {
                if (this.output_stream instanceof ByteArrayOutputStream) {
                    this.resp = this.con.ExtensionMethod(this.method, this.resource, ((ByteArrayOutputStream)this.output_stream).toByteArray(), this.headers);
                }
                else {
                    this.resp = this.con.ExtensionMethod(this.method, this.resource, (HttpOutputStream)this.output_stream, this.headers);
                }
            }
            catch (ModuleException e) {
                throw new IOException(e.toString());
            }
        }
        // monitorexit(this.con)
        super.connected = true;
    }
    
    public void disconnect() {
        Log.write(128, "URLC:  (" + this.urlString + ") Disconnecting ...");
        this.con.stop();
    }
    
    public boolean usingProxy() {
        return this.con.getProxyHost() != null;
    }
    
    public String toString() {
        return String.valueOf(this.getClass().getName()) + "[" + super.url + "]";
    }
    
    static {
        HttpURLConnection.connections = new Hashtable();
        HttpURLConnection.default_headers = new NVPair[0];
        try {
            if (Boolean.getBoolean("HTTPClient.HttpURLConnection.AllowUI")) {
                URLConnection.setDefaultAllowUserInteraction(true);
            }
        }
        catch (SecurityException ex) {}
        try {
            HttpURLConnection.redir_mod = Class.forName("HTTPClient.RedirectionModule");
        }
        catch (ClassNotFoundException cnfe) {
            throw new NoClassDefFoundError(cnfe.getMessage());
        }
        try {
            final String agent = System.getProperty("http.agent");
            if (agent != null) {
                setDefaultRequestProperty("User-Agent", agent);
            }
        }
        catch (SecurityException ex2) {}
        HttpURLConnection.non_proxy_hosts = "";
        HttpURLConnection.proxy_host = "";
        HttpURLConnection.proxy_port = -1;
    }
}
