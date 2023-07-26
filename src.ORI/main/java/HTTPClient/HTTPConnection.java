// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.FilterOutputStream;
import java.io.DataOutputStream;
import java.net.SocketException;
import java.net.NoRouteToHostException;
import java.net.ConnectException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.URL;
import java.applet.Applet;
import com.progress.ubroker.util.ISSLSocketUtils;
import com.progress.ubroker.util.ISSLParams;
import java.util.Vector;
import java.net.InetAddress;

public class HTTPConnection implements GlobalConstants, HTTPClientModuleConstants
{
    public static final String version = "";
    private static final Object dflt_context;
    private Object Context;
    private int Protocol;
    int ServerProtocolVersion;
    boolean ServProtVersKnown;
    private String RequestProtocolVersion;
    private String Host;
    private int Port;
    private InetAddress LocalAddr;
    private int LocalPort;
    private String Proxy_Host;
    private int Proxy_Port;
    private static String Default_Proxy_Host;
    private static int Default_Proxy_Port;
    private static CIHashtable non_proxy_host_list;
    private static Vector non_proxy_dom_list;
    private static Vector non_proxy_addr_list;
    private static Vector non_proxy_mask_list;
    private SocksClient Socks_client;
    private static SocksClient Default_Socks_client;
    private StreamDemultiplexor input_demux;
    LinkedList DemuxList;
    private LinkedList RequestList;
    private boolean doesKeepAlive;
    private boolean keepAliveUnknown;
    private int keepAliveReqMax;
    private int keepAliveReqLeft;
    private static boolean no_chunked;
    private static boolean force_1_0;
    private static boolean neverPipeline;
    private static boolean noKeepAlives;
    private static boolean haveMSLargeWritesBug;
    static boolean deferStreamed;
    private static int DefaultTimeout;
    private int Timeout;
    private NVPair[] DefaultHeaders;
    private static Vector DefaultModuleList;
    private Vector ModuleList;
    private static boolean defaultAllowUI;
    private boolean allowUI;
    private ISSLParams params;
    private ISSLSocketUtils sslSocketUtils;
    private ISSLSocketUtils.ISSLInfo sslSocketInfo;
    private volatile Response early_stall;
    private volatile Response late_stall;
    private volatile Response prev_resp;
    private boolean output_finished;
    
    public HTTPConnection(final Applet applet) throws ProtocolNotSuppException {
        this(applet.getCodeBase().getProtocol(), applet.getCodeBase().getHost(), applet.getCodeBase().getPort());
    }
    
    public HTTPConnection(final String s) {
        this.Context = null;
        this.Proxy_Host = null;
        this.Socks_client = null;
        this.input_demux = null;
        this.DemuxList = new LinkedList();
        this.RequestList = new LinkedList();
        this.doesKeepAlive = false;
        this.keepAliveUnknown = true;
        this.keepAliveReqMax = -1;
        this.DefaultHeaders = new NVPair[0];
        this.params = null;
        this.sslSocketUtils = null;
        this.sslSocketInfo = null;
        this.early_stall = null;
        this.late_stall = null;
        this.prev_resp = null;
        this.output_finished = true;
        this.Setup(0, s, 80, null, -1);
    }
    
    public HTTPConnection(final String s, final int n) {
        this.Context = null;
        this.Proxy_Host = null;
        this.Socks_client = null;
        this.input_demux = null;
        this.DemuxList = new LinkedList();
        this.RequestList = new LinkedList();
        this.doesKeepAlive = false;
        this.keepAliveUnknown = true;
        this.keepAliveReqMax = -1;
        this.DefaultHeaders = new NVPair[0];
        this.params = null;
        this.sslSocketUtils = null;
        this.sslSocketInfo = null;
        this.early_stall = null;
        this.late_stall = null;
        this.prev_resp = null;
        this.output_finished = true;
        this.Setup(0, s, n, null, -1);
    }
    
    public HTTPConnection(final String s, final String s2, final int n) throws ProtocolNotSuppException {
        this(s, s2, n, null, -1);
    }
    
    public HTTPConnection(String lowerCase, final String s, final int n, final InetAddress inetAddress, final int n2) throws ProtocolNotSuppException {
        this.Context = null;
        this.Proxy_Host = null;
        this.Socks_client = null;
        this.input_demux = null;
        this.DemuxList = new LinkedList();
        this.RequestList = new LinkedList();
        this.doesKeepAlive = false;
        this.keepAliveUnknown = true;
        this.keepAliveReqMax = -1;
        this.DefaultHeaders = new NVPair[0];
        this.params = null;
        this.sslSocketUtils = null;
        this.sslSocketInfo = null;
        this.early_stall = null;
        this.late_stall = null;
        this.prev_resp = null;
        this.output_finished = true;
        lowerCase = lowerCase.trim().toLowerCase();
        if (!lowerCase.equals("http") && !lowerCase.equals("https")) {
            throw new ProtocolNotSuppException("Unsupported protocol '" + lowerCase + "'");
        }
        if (lowerCase.equals("http")) {
            this.Setup(0, s, n, inetAddress, n2);
        }
        else if (lowerCase.equals("https")) {
            this.Setup(1, s, n, inetAddress, n2);
        }
        else if (lowerCase.equals("shttp")) {
            this.Setup(2, s, n, inetAddress, n2);
        }
        else if (lowerCase.equals("http-ng")) {
            this.Setup(3, s, n, inetAddress, n2);
        }
    }
    
    public HTTPConnection(final URL url) throws ProtocolNotSuppException {
        this(url.getProtocol(), url.getHost(), url.getPort());
    }
    
    public HTTPConnection(final URI uri) throws ProtocolNotSuppException {
        this(uri.getScheme(), uri.getHost(), uri.getPort());
    }
    
    private void Setup(final int protocol, final String s, final int port, final InetAddress localAddr, final int localPort) {
        this.Protocol = protocol;
        this.Host = s.trim().toLowerCase();
        this.Port = port;
        this.LocalAddr = localAddr;
        this.LocalPort = localPort;
        if (this.Port == -1) {
            this.Port = URI.defaultPort(this.getProtocol());
        }
        if (HTTPConnection.Default_Proxy_Host != null && !this.matchNonProxy(this.Host)) {
            this.setCurrentProxy(HTTPConnection.Default_Proxy_Host, HTTPConnection.Default_Proxy_Port);
        }
        else {
            this.setCurrentProxy(null, 0);
        }
        this.Socks_client = HTTPConnection.Default_Socks_client;
        this.Timeout = HTTPConnection.DefaultTimeout;
        this.ModuleList = (Vector)HTTPConnection.DefaultModuleList.clone();
        this.allowUI = HTTPConnection.defaultAllowUI;
        if (HTTPConnection.noKeepAlives) {
            this.setDefaultHeaders(new NVPair[] { new NVPair("Connection", "close") });
        }
    }
    
    private boolean matchNonProxy(final String host) {
        if (HTTPConnection.non_proxy_host_list.get(host) != null) {
            return true;
        }
        for (int i = 0; i < HTTPConnection.non_proxy_dom_list.size(); ++i) {
            if (host.endsWith((String)HTTPConnection.non_proxy_dom_list.elementAt(i))) {
                return true;
            }
        }
        if (HTTPConnection.non_proxy_addr_list.size() == 0) {
            return false;
        }
        InetAddress[] allByName;
        try {
            allByName = InetAddress.getAllByName(host);
        }
        catch (UnknownHostException ex) {
            return false;
        }
        for (int j = 0; j < HTTPConnection.non_proxy_addr_list.size(); ++j) {
            final byte[] array = HTTPConnection.non_proxy_addr_list.elementAt(j);
            final byte[] array2 = HTTPConnection.non_proxy_mask_list.elementAt(j);
        Label_0191:
            for (int k = 0; k < allByName.length; ++k) {
                final byte[] address = allByName[k].getAddress();
                if (address.length == array.length) {
                    for (int l = 0; l < address.length; ++l) {
                        if ((address[l] & array2[l]) != (array[l] & array2[l])) {
                            continue Label_0191;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public HTTPResponse Head(final String s) throws IOException, ModuleException {
        return this.Head(s, (String)null, null);
    }
    
    public HTTPResponse Head(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.Head(s, array, null);
    }
    
    public HTTPResponse Head(final String s, final NVPair[] array, final NVPair[] array2) throws IOException, ModuleException {
        String str = this.stripRef(s);
        final String nv2query = Codecs.nv2query(array);
        if (nv2query != null && nv2query.length() > 0) {
            str = str + "?" + nv2query;
        }
        return this.setupRequest("HEAD", str, array2, null, null);
    }
    
    public HTTPResponse Head(final String s, final String s2) throws IOException, ModuleException {
        return this.Head(s, s2, null);
    }
    
    public HTTPResponse Head(final String s, final String s2, final NVPair[] array) throws IOException, ModuleException {
        String str = this.stripRef(s);
        if (s2 != null && s2.length() > 0) {
            str = str + "?" + Codecs.URLEncode(s2);
        }
        return this.setupRequest("HEAD", str, array, null, null);
    }
    
    public HTTPResponse Get(final String s) throws IOException, ModuleException {
        return this.Get(s, (String)null, null);
    }
    
    public HTTPResponse Get(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.Get(s, array, null);
    }
    
    public HTTPResponse Get(final String s, final NVPair[] array, final NVPair[] array2) throws IOException, ModuleException {
        String str = this.stripRef(s);
        final String nv2query = Codecs.nv2query(array);
        if (nv2query != null && nv2query.length() > 0) {
            str = str + "?" + nv2query;
        }
        return this.setupRequest("GET", str, array2, null, null);
    }
    
    public HTTPResponse Get(final String s, final String s2) throws IOException, ModuleException {
        return this.Get(s, s2, null);
    }
    
    public HTTPResponse Get(final String s, final String s2, final NVPair[] array) throws IOException, ModuleException {
        String str = this.stripRef(s);
        if (s2 != null && s2.length() > 0) {
            str = str + "?" + Codecs.URLEncode(s2);
        }
        return this.setupRequest("GET", str, array, null, null);
    }
    
    public HTTPResponse Post(final String s) throws IOException, ModuleException {
        return this.Post(s, (byte[])null, null);
    }
    
    public HTTPResponse Post(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.Post(s, Codecs.nv2query(array), new NVPair[] { new NVPair("Content-type", "application/x-www-form-urlencoded") });
    }
    
    public HTTPResponse Post(final String s, final NVPair[] array, NVPair[] resizeArray) throws IOException, ModuleException {
        int n;
        for (n = 0; n < resizeArray.length && !resizeArray[n].getName().equalsIgnoreCase("Content-type"); ++n) {}
        if (n == resizeArray.length) {
            resizeArray = Util.resizeArray(resizeArray, n + 1);
            resizeArray[n] = new NVPair("Content-type", "application/x-www-form-urlencoded");
        }
        return this.Post(s, Codecs.nv2query(array), resizeArray);
    }
    
    public HTTPResponse Post(final String s, final String s2) throws IOException, ModuleException {
        return this.Post(s, s2, null);
    }
    
    public HTTPResponse Post(final String s, final String s2, final NVPair[] array) throws IOException, ModuleException {
        byte[] bytes = null;
        if (s2 != null && s2.length() > 0) {
            bytes = s2.getBytes();
        }
        return this.Post(s, bytes, array);
    }
    
    public HTTPResponse Post(final String s, final byte[] array) throws IOException, ModuleException {
        return this.Post(s, array, null);
    }
    
    public HTTPResponse Post(final String s, byte[] array, final NVPair[] array2) throws IOException, ModuleException {
        if (array == null) {
            array = new byte[0];
        }
        return this.setupRequest("POST", this.stripRef(s), array2, array, null);
    }
    
    public HTTPResponse Post(final String s, final HttpOutputStream httpOutputStream) throws IOException, ModuleException {
        return this.Post(s, httpOutputStream, null);
    }
    
    public HTTPResponse Post(final String s, final HttpOutputStream httpOutputStream, final NVPair[] array) throws IOException, ModuleException {
        return this.setupRequest("POST", this.stripRef(s), array, null, httpOutputStream);
    }
    
    public HTTPResponse Put(final String s, final String s2) throws IOException, ModuleException {
        return this.Put(s, s2, null);
    }
    
    public HTTPResponse Put(final String s, final String s2, final NVPair[] array) throws IOException, ModuleException {
        byte[] bytes = null;
        if (s2 != null && s2.length() > 0) {
            bytes = s2.getBytes();
        }
        return this.Put(s, bytes, array);
    }
    
    public HTTPResponse Put(final String s, final byte[] array) throws IOException, ModuleException {
        return this.Put(s, array, null);
    }
    
    public HTTPResponse Put(final String s, byte[] array, final NVPair[] array2) throws IOException, ModuleException {
        if (array == null) {
            array = new byte[0];
        }
        return this.setupRequest("PUT", this.stripRef(s), array2, array, null);
    }
    
    public HTTPResponse Put(final String s, final HttpOutputStream httpOutputStream) throws IOException, ModuleException {
        return this.Put(s, httpOutputStream, null);
    }
    
    public HTTPResponse Put(final String s, final HttpOutputStream httpOutputStream, final NVPair[] array) throws IOException, ModuleException {
        return this.setupRequest("PUT", this.stripRef(s), array, null, httpOutputStream);
    }
    
    public HTTPResponse Options(final String s) throws IOException, ModuleException {
        return this.Options(s, null, (byte[])null);
    }
    
    public HTTPResponse Options(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.Options(s, array, (byte[])null);
    }
    
    public HTTPResponse Options(final String s, final NVPair[] array, final byte[] array2) throws IOException, ModuleException {
        return this.setupRequest("OPTIONS", this.stripRef(s), array, array2, null);
    }
    
    public HTTPResponse Options(final String s, final NVPair[] array, final HttpOutputStream httpOutputStream) throws IOException, ModuleException {
        return this.setupRequest("OPTIONS", this.stripRef(s), array, null, httpOutputStream);
    }
    
    public HTTPResponse Delete(final String s) throws IOException, ModuleException {
        return this.Delete(s, null);
    }
    
    public HTTPResponse Delete(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.setupRequest("DELETE", this.stripRef(s), array, null, null);
    }
    
    public HTTPResponse Trace(final String s, final NVPair[] array) throws IOException, ModuleException {
        return this.setupRequest("TRACE", this.stripRef(s), array, null, null);
    }
    
    public HTTPResponse Trace(final String s) throws IOException, ModuleException {
        return this.Trace(s, null);
    }
    
    public HTTPResponse ExtensionMethod(final String s, final String s2, final byte[] array, final NVPair[] array2) throws IOException, ModuleException {
        return this.setupRequest(s.trim(), this.stripRef(s2), array2, array, null);
    }
    
    public HTTPResponse ExtensionMethod(final String s, final String s2, final HttpOutputStream httpOutputStream, final NVPair[] array) throws IOException, ModuleException {
        return this.setupRequest(s.trim(), this.stripRef(s2), array, null, httpOutputStream);
    }
    
    public void stop() {
        for (Request request = (Request)this.RequestList.enumerate(); request != null; request = (Request)this.RequestList.next()) {
            request.aborted = true;
        }
        for (StreamDemultiplexor streamDemultiplexor = (StreamDemultiplexor)this.DemuxList.enumerate(); streamDemultiplexor != null; streamDemultiplexor = (StreamDemultiplexor)this.DemuxList.next()) {
            streamDemultiplexor.abort();
        }
    }
    
    public void setSSLParams(final ISSLParams params) {
        this.params = params;
    }
    
    public ISSLParams getSSLParams() {
        return this.params;
    }
    
    public void setSSLSocketUtils(final ISSLSocketUtils sslSocketUtils) {
        this.sslSocketUtils = sslSocketUtils;
    }
    
    public ISSLSocketUtils getSSLSocketUtils() {
        return this.sslSocketUtils;
    }
    
    public ISSLSocketUtils.ISSLInfo getSSLSocketInfo() {
        final ISSLSocketUtils.ISSLInfo sslSocketInfo = this.sslSocketInfo;
        this.sslSocketInfo = null;
        return sslSocketInfo;
    }
    
    public void setDefaultHeaders(final NVPair[] array) {
        final int n = (array == null) ? 0 : array.length;
        NVPair[] resizeArray = new NVPair[n];
        int i = 0;
        int n2 = 0;
        while (i < n) {
            if (array[i] != null) {
                if (!array[i].getName().trim().equalsIgnoreCase("Content-length")) {
                    resizeArray[n2++] = array[i];
                }
            }
            ++i;
        }
        if (n2 < n) {
            resizeArray = Util.resizeArray(resizeArray, n2);
        }
        synchronized (this.DefaultHeaders) {
            this.DefaultHeaders = resizeArray;
        }
    }
    
    public NVPair[] getDefaultHeaders() {
        synchronized (this.DefaultHeaders) {
            return this.DefaultHeaders.clone();
        }
    }
    
    public String getProtocol() {
        switch (this.Protocol) {
            case 0: {
                return "http";
            }
            case 1: {
                return "https";
            }
            case 2: {
                return "shttp";
            }
            case 3: {
                return "http-ng";
            }
            default: {
                throw new Error("HTTPClient Internal Error: invalid protocol " + this.Protocol);
            }
        }
    }
    
    public String getHost() {
        return this.Host;
    }
    
    public int getPort() {
        return this.Port;
    }
    
    public String getProxyHost() {
        return this.Proxy_Host;
    }
    
    public int getProxyPort() {
        return this.Proxy_Port;
    }
    
    public boolean isCompatibleWith(final URI uri) {
        if (!uri.getScheme().equals(this.getProtocol()) || !uri.getHost().equalsIgnoreCase(this.Host)) {
            return false;
        }
        int n = uri.getPort();
        if (n == -1) {
            n = URI.defaultPort(uri.getScheme());
        }
        return n == this.Port;
    }
    
    public void setRawMode(final boolean b) {
        final String[] array = { "HTTPClient.CookieModule", "HTTPClient.RedirectionModule", "HTTPClient.AuthorizationModule", "HTTPClient.DefaultModule", "HTTPClient.TransferEncodingModule", "HTTPClient.ContentMD5Module", "HTTPClient.ContentEncodingModule" };
        for (int i = 0; i < array.length; ++i) {
            try {
                if (b) {
                    this.removeModule(Class.forName(array[i]));
                }
                else {
                    this.addModule(Class.forName(array[i]), -1);
                }
            }
            catch (ClassNotFoundException ex) {}
        }
    }
    
    public static void setDefaultTimeout(final int defaultTimeout) {
        HTTPConnection.DefaultTimeout = defaultTimeout;
    }
    
    public static int getDefaultTimeout() {
        return HTTPConnection.DefaultTimeout;
    }
    
    public void setTimeout(final int timeout) {
        this.Timeout = timeout;
    }
    
    public int getTimeout() {
        return this.Timeout;
    }
    
    public void setAllowUserInteraction(final boolean allowUI) {
        this.allowUI = allowUI;
    }
    
    public boolean getAllowUserInteraction() {
        return this.allowUI;
    }
    
    public static void setDefaultAllowUserInteraction(final boolean defaultAllowUI) {
        HTTPConnection.defaultAllowUI = defaultAllowUI;
    }
    
    public static boolean getDefaultAllowUserInteraction() {
        return HTTPConnection.defaultAllowUI;
    }
    
    public static Class[] getDefaultModules() {
        return getModules(HTTPConnection.DefaultModuleList);
    }
    
    public static boolean addDefaultModule(final Class clazz, final int n) {
        return addModule(HTTPConnection.DefaultModuleList, clazz, n);
    }
    
    public static boolean removeDefaultModule(final Class clazz) {
        return removeModule(HTTPConnection.DefaultModuleList, clazz);
    }
    
    public Class[] getModules() {
        return getModules(this.ModuleList);
    }
    
    public boolean addModule(final Class clazz, final int n) {
        return addModule(this.ModuleList, clazz, n);
    }
    
    public boolean removeModule(final Class clazz) {
        return removeModule(this.ModuleList, clazz);
    }
    
    private static final Class[] getModules(final Vector vector) {
        synchronized (vector) {
            final Class[] anArray = new Class[vector.size()];
            vector.copyInto(anArray);
            return anArray;
        }
    }
    
    private static final boolean addModule(final Vector vector, final Class obj, final int index) {
        if (obj == null) {
            return false;
        }
        try {
            final HTTPClientModule httpClientModule = obj.newInstance();
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new RuntimeException(ex2.toString());
        }
        synchronized (vector) {
            if (vector.contains(obj)) {
                return false;
            }
            if (index < 0) {
                vector.insertElementAt(obj, HTTPConnection.DefaultModuleList.size() + index + 1);
            }
            else {
                vector.insertElementAt(obj, index);
            }
        }
        Log.write(1, "Conn:  Added module " + obj.getName() + " to " + ((vector == HTTPConnection.DefaultModuleList) ? "default " : "") + "list");
        return true;
    }
    
    private static final boolean removeModule(final Vector vector, final Class obj) {
        if (obj == null) {
            return false;
        }
        final boolean removeElement = vector.removeElement(obj);
        if (removeElement) {
            Log.write(1, "Conn:  Removed module " + obj.getName() + " from " + ((vector == HTTPConnection.DefaultModuleList) ? "default " : "") + "list");
        }
        return removeElement;
    }
    
    public void setContext(final Object context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be non-null");
        }
        if (this.Context != null) {
            throw new IllegalStateException("Context already set");
        }
        this.Context = context;
    }
    
    public Object getContext() {
        if (this.Context != null) {
            return this.Context;
        }
        return HTTPConnection.dflt_context;
    }
    
    public static Object getDefaultContext() {
        return HTTPConnection.dflt_context;
    }
    
    public void addDigestAuthorization(final String s, final String s2, final String s3) {
        AuthorizationInfo.addDigestAuthorization(this.Host, this.Port, s, s2, s3, this.getContext());
    }
    
    public void addBasicAuthorization(final String s, final String s2, final String s3) {
        AuthorizationInfo.addBasicAuthorization(this.Host, this.Port, s, s2, s3, this.getContext());
    }
    
    public static void setProxyServer(final String s, final int default_Proxy_Port) {
        if (s == null || s.trim().length() == 0) {
            HTTPConnection.Default_Proxy_Host = null;
        }
        else {
            HTTPConnection.Default_Proxy_Host = s.trim().toLowerCase();
            HTTPConnection.Default_Proxy_Port = default_Proxy_Port;
        }
    }
    
    public synchronized void setCurrentProxy(final String s, final int proxy_Port) {
        if (s == null || s.trim().length() == 0) {
            this.Proxy_Host = null;
        }
        else {
            this.Proxy_Host = s.trim().toLowerCase();
            if (proxy_Port <= 0) {
                this.Proxy_Port = 80;
            }
            else {
                this.Proxy_Port = proxy_Port;
            }
        }
        switch (this.Protocol) {
            case 0:
            case 1: {
                if (HTTPConnection.force_1_0) {
                    this.ServerProtocolVersion = 65536;
                    this.ServProtVersKnown = true;
                    this.RequestProtocolVersion = "HTTP/1.0";
                    break;
                }
                this.ServerProtocolVersion = 65537;
                this.ServProtVersKnown = false;
                this.RequestProtocolVersion = "HTTP/1.1";
                break;
            }
            case 3: {
                this.ServerProtocolVersion = -1;
                this.ServProtVersKnown = false;
                this.RequestProtocolVersion = "";
                break;
            }
            case 2: {
                this.ServerProtocolVersion = -1;
                this.ServProtVersKnown = false;
                this.RequestProtocolVersion = "Secure-HTTP/1.3";
                break;
            }
            default: {
                throw new Error("HTTPClient Internal Error: invalid protocol " + this.Protocol);
            }
        }
        this.keepAliveUnknown = true;
        this.doesKeepAlive = false;
        this.input_demux = null;
        this.early_stall = null;
        this.late_stall = null;
        this.prev_resp = null;
    }
    
    public static void dontProxyFor(String lowerCase) throws ParseException {
        lowerCase = lowerCase.trim().toLowerCase();
        if (lowerCase.charAt(0) == '.') {
            if (!HTTPConnection.non_proxy_dom_list.contains(lowerCase)) {
                HTTPConnection.non_proxy_dom_list.addElement(lowerCase);
            }
            return;
        }
        for (int i = 0; i < lowerCase.length(); ++i) {
            if (!Character.isDigit(lowerCase.charAt(i)) && lowerCase.charAt(i) != '.' && lowerCase.charAt(i) != '/') {
                HTTPConnection.non_proxy_host_list.put(lowerCase, "");
                return;
            }
        }
        final int index;
        byte[] obj;
        byte[] string2arr;
        if ((index = lowerCase.indexOf(47)) != -1) {
            obj = string2arr(lowerCase.substring(0, index));
            string2arr = string2arr(lowerCase.substring(index + 1));
            if (obj.length != string2arr.length) {
                throw new ParseException("length of IP-address (" + obj.length + ") != length of netmask (" + string2arr.length + ")");
            }
        }
        else {
            obj = string2arr(lowerCase);
            string2arr = new byte[obj.length];
            for (int j = 0; j < string2arr.length; ++j) {
                string2arr[j] = -1;
            }
        }
    Label_0322:
        for (int k = 0; k < HTTPConnection.non_proxy_addr_list.size(); ++k) {
            final byte[] array = HTTPConnection.non_proxy_addr_list.elementAt(k);
            final byte[] array2 = HTTPConnection.non_proxy_mask_list.elementAt(k);
            if (array.length == obj.length) {
                for (int l = 0; l < array.length; ++l) {
                    if ((obj[l] & array2[l]) != (array[l] & array2[l])) {
                        continue Label_0322;
                    }
                    if (array2[l] != string2arr[l]) {
                        continue Label_0322;
                    }
                }
                return;
            }
        }
        HTTPConnection.non_proxy_addr_list.addElement(obj);
        HTTPConnection.non_proxy_mask_list.addElement(string2arr);
    }
    
    public static void dontProxyFor(final String[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            try {
                if (array[i] != null) {
                    dontProxyFor(array[i]);
                }
            }
            catch (ParseException ex) {}
        }
    }
    
    public static boolean doProxyFor(String lowerCase) throws ParseException {
        lowerCase = lowerCase.trim().toLowerCase();
        if (lowerCase.charAt(0) == '.') {
            return HTTPConnection.non_proxy_dom_list.removeElement(lowerCase);
        }
        for (int i = 0; i < lowerCase.length(); ++i) {
            if (!Character.isDigit(lowerCase.charAt(i)) && lowerCase.charAt(i) != '.' && lowerCase.charAt(i) != '/') {
                return HTTPConnection.non_proxy_host_list.remove(lowerCase) != null;
            }
        }
        final int index;
        byte[] array;
        byte[] string2arr;
        if ((index = lowerCase.indexOf(47)) != -1) {
            array = string2arr(lowerCase.substring(0, index));
            string2arr = string2arr(lowerCase.substring(index + 1));
            if (array.length != string2arr.length) {
                throw new ParseException("length of IP-address (" + array.length + ") != length of netmask (" + string2arr.length + ")");
            }
        }
        else {
            array = string2arr(lowerCase);
            string2arr = new byte[array.length];
            for (int j = 0; j < string2arr.length; ++j) {
                string2arr[j] = -1;
            }
        }
    Label_0334:
        for (int k = 0; k < HTTPConnection.non_proxy_addr_list.size(); ++k) {
            final byte[] array2 = HTTPConnection.non_proxy_addr_list.elementAt(k);
            final byte[] array3 = HTTPConnection.non_proxy_mask_list.elementAt(k);
            if (array2.length == array.length) {
                for (int l = 0; l < array2.length; ++l) {
                    if ((array[l] & array3[l]) != (array2[l] & array3[l])) {
                        continue Label_0334;
                    }
                    if (array3[l] != string2arr[l]) {
                        continue Label_0334;
                    }
                }
                HTTPConnection.non_proxy_addr_list.removeElementAt(k);
                HTTPConnection.non_proxy_mask_list.removeElementAt(k);
                return true;
            }
        }
        return false;
    }
    
    private static byte[] string2arr(final String s) {
        final char[] dst = new char[s.length()];
        s.getChars(0, dst.length, dst, 0);
        int n = 0;
        for (int i = 0; i < dst.length; ++i) {
            if (dst[i] == '.') {
                ++n;
            }
        }
        final byte[] array = new byte[n + 1];
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < dst.length; ++j) {
            if (dst[j] == '.') {
                array[n2] = (byte)Integer.parseInt(s.substring(n3, j));
                ++n2;
                n3 = j + 1;
            }
        }
        array[n2] = (byte)Integer.parseInt(s.substring(n3));
        return array;
    }
    
    public static void setSocksServer(final String s) {
        setSocksServer(s, 1080);
    }
    
    public static void setSocksServer(final String s, int n) {
        if (n <= 0) {
            n = 1080;
        }
        if (s == null || s.length() == 0) {
            HTTPConnection.Default_Socks_client = null;
        }
        else {
            HTTPConnection.Default_Socks_client = new SocksClient(s, n);
        }
    }
    
    public static void setSocksServer(final String s, int n, final int n2) throws SocksException {
        if (n <= 0) {
            n = 1080;
        }
        if (s == null || s.length() == 0) {
            HTTPConnection.Default_Socks_client = null;
        }
        else {
            HTTPConnection.Default_Socks_client = new SocksClient(s, n, n2);
        }
    }
    
    private final String stripRef(String substring) {
        if (substring == null) {
            return "";
        }
        final int index = substring.indexOf(35);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        return substring.trim();
    }
    
    protected final HTTPResponse setupRequest(final String s, final String s2, final NVPair[] array, final byte[] array2, final HttpOutputStream httpOutputStream) throws IOException, ModuleException {
        final Request request = new Request(this, s, s2, this.mergedHeaders(array), array2, httpOutputStream, this.allowUI);
        this.RequestList.addToEnd(request);
        try {
            final HTTPResponse httpResponse = new HTTPResponse(this.gen_mod_insts(), this.Timeout, request);
            this.handleRequest(request, httpResponse, null, true);
            return httpResponse;
        }
        finally {
            this.RequestList.remove(request);
        }
    }
    
    private NVPair[] mergedHeaders(final NVPair[] array) {
        final int n = (array != null) ? array.length : 0;
        final int n2;
        NVPair[] resizeArray;
        synchronized (this.DefaultHeaders) {
            n2 = ((this.DefaultHeaders != null) ? this.DefaultHeaders.length : 0);
            resizeArray = new NVPair[n + n2];
            System.arraycopy(this.DefaultHeaders, 0, resizeArray, 0, n2);
        }
        int n3 = n2;
        for (int i = 0; i < n; ++i) {
            if (array[i] != null) {
                final String trim = array[i].getName().trim();
                if (!trim.equalsIgnoreCase("Content-length")) {
                    int n4;
                    for (n4 = 0; n4 < n3 && !resizeArray[n4].getName().trim().equalsIgnoreCase(trim); ++n4) {}
                    resizeArray[n4] = array[i];
                    if (n4 == n3) {
                        ++n3;
                    }
                }
            }
        }
        if (n3 < resizeArray.length) {
            resizeArray = Util.resizeArray(resizeArray, n3);
        }
        return resizeArray;
    }
    
    private HTTPClientModule[] gen_mod_insts() {
        synchronized (this.ModuleList) {
            final HTTPClientModule[] array = new HTTPClientModule[this.ModuleList.size()];
            for (int i = 0; i < this.ModuleList.size(); ++i) {
                final Class<HTTPClientModule> clazz = this.ModuleList.elementAt(i);
                try {
                    array[i] = clazz.newInstance();
                }
                catch (Exception obj) {
                    throw new Error("HTTPClient Internal Error: could not create instance of " + clazz.getName() + " -\n" + obj);
                }
            }
            return array;
        }
    }
    
    void handleRequest(final Request request, final HTTPResponse httpResponse, final Response response, final boolean b) throws IOException, ModuleException {
        final Response[] array = { response };
        final HTTPClientModule[] modules = httpResponse.getModules();
        Label_0302: {
            if (b) {
                for (int i = 0; i < modules.length; ++i) {
                    final int requestHandler = modules[i].requestHandler(request, array);
                    switch (requestHandler) {
                        case 0: {
                            break;
                        }
                        case 1: {
                            i = -1;
                            break;
                        }
                        case 2: {
                            break Label_0302;
                        }
                        case 3:
                        case 4: {
                            if (array[0] == null) {
                                throw new Error("HTTPClient Internal Error: no response returned by module " + modules[i].getClass().getName());
                            }
                            httpResponse.set(request, array[0]);
                            if (request.getStream() != null) {
                                request.getStream().ignoreData(request);
                            }
                            if (request.internal_subrequest) {
                                return;
                            }
                            if (requestHandler == 3) {
                                httpResponse.handleResponse();
                            }
                            else {
                                httpResponse.init(array[0]);
                            }
                            return;
                        }
                        case 5: {
                            if (request.internal_subrequest) {
                                return;
                            }
                            request.getConnection().handleRequest(request, httpResponse, array[0], true);
                            return;
                        }
                        case 6: {
                            if (request.internal_subrequest) {
                                return;
                            }
                            request.getConnection().handleRequest(request, httpResponse, array[0], false);
                            return;
                        }
                        default: {
                            throw new Error("HTTPClient Internal Error: invalid status " + requestHandler + " returned by module " + modules[i].getClass().getName());
                        }
                    }
                }
            }
        }
        if (request.internal_subrequest) {
            return;
        }
        if (request.getStream() != null && request.getStream().getLength() == -1) {
            if (!this.ServProtVersKnown || this.ServerProtocolVersion < 65537 || HTTPConnection.no_chunked) {
                request.getStream().goAhead(request, null, httpResponse.getTimeout());
                httpResponse.set(request, request.getStream());
            }
            else {
                NVPair[] headers;
                int n;
                for (headers = request.getHeaders(), n = 0; n < headers.length && !headers[n].getName().equalsIgnoreCase("Transfer-Encoding"); ++n) {}
                if (n == headers.length) {
                    final NVPair[] resizeArray = Util.resizeArray(headers, n + 1);
                    resizeArray[n] = new NVPair("Transfer-Encoding", "chunked");
                    request.setHeaders(resizeArray);
                }
                else {
                    final String value = headers[n].getValue();
                    try {
                        if (!Util.hasToken(value, "chunked")) {
                            headers[n] = new NVPair("Transfer-Encoding", value + ", chunked");
                        }
                    }
                    catch (ParseException ex) {
                        throw new IOException(ex.toString());
                    }
                }
                httpResponse.set(request, this.sendRequest(request, httpResponse.getTimeout()));
            }
        }
        else {
            httpResponse.set(request, this.sendRequest(request, httpResponse.getTimeout()));
        }
        if (request.aborted) {
            throw new IOException("Request aborted by user");
        }
    }
    
    Response sendRequest(final Request request, final int soTimeout) throws IOException, ModuleException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(600);
        Response enableSSLTunneling = null;
        if (this.early_stall != null) {
            try {
                Log.write(1, "Conn:  Early-stalling Request: " + request.getMethod() + " " + request.getRequestURI());
                synchronized (this.early_stall) {
                    try {
                        this.early_stall.getVersion();
                    }
                    catch (IOException ex5) {}
                    this.early_stall = null;
                }
            }
            catch (NullPointerException ex6) {}
        }
        final String[] assembleHeaders = this.assembleHeaders(request, byteArrayOutputStream);
        boolean b;
        try {
            b = ((this.ServerProtocolVersion >= 65537 && !Util.hasToken(assembleHeaders[0], "close")) || (this.ServerProtocolVersion == 65536 && Util.hasToken(assembleHeaders[0], "keep-alive")));
        }
        catch (ParseException ex) {
            throw new IOException(ex.toString());
        }
        synchronized (this) {
            if (this.late_stall != null) {
                if (this.input_demux != null || this.keepAliveUnknown) {
                    Log.write(1, "Conn:  Stalling Request: " + request.getMethod() + " " + request.getRequestURI());
                    try {
                        this.late_stall.getVersion();
                        if (this.keepAliveUnknown) {
                            this.determineKeepAlive(this.late_stall);
                        }
                    }
                    catch (IOException ex7) {}
                }
                this.late_stall = null;
            }
            if ((request.getMethod().equals("POST") || request.dont_pipeline) && this.prev_resp != null && this.input_demux != null) {
                Log.write(1, "Conn:  Stalling Request: " + request.getMethod() + " " + request.getRequestURI());
                try {
                    this.prev_resp.getVersion();
                }
                catch (IOException ex8) {}
            }
            if (!this.output_finished) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex2) {
                    throw new IOException(ex2.toString());
                }
            }
            if (request.aborted) {
                throw new IOException("Request aborted by user");
            }
            int n = 3;
            while (n-- > 0) {
                try {
                    Socket socket;
                    if (this.input_demux == null || (socket = this.input_demux.getSocket()) == null) {
                        socket = this.getSocket(soTimeout);
                        socket.setSoTimeout(soTimeout);
                        if (this.Protocol == 1) {
                            if (this.Proxy_Host != null) {
                                final Socket[] array = { socket };
                                enableSSLTunneling = this.enableSSLTunneling(array, request, soTimeout);
                                if (enableSSLTunneling != null) {
                                    enableSSLTunneling.final_resp = true;
                                    return enableSSLTunneling;
                                }
                                socket = array[0];
                            }
                            if (null == this.sslSocketUtils) {
                                throw new IOException("No object to create SSLSocket");
                            }
                            Log.write(1, "Conn:  creating SSL socket information");
                            socket = this.sslSocketUtils.createSSLSocket(socket, this.params);
                        }
                        this.input_demux = new StreamDemultiplexor(this.Protocol, socket, this);
                        this.DemuxList.addToEnd(this.input_demux);
                        this.keepAliveReqLeft = this.keepAliveReqMax;
                    }
                    if (request.aborted) {
                        throw new IOException("Request aborted by user");
                    }
                    Log.write(1, "Conn:  Sending Request: ", byteArrayOutputStream);
                    OutputStream outputStream = socket.getOutputStream();
                    if (this.Protocol == 1 && null != socket && null != this.sslSocketUtils) {
                        Log.write(1, "Conn:  obtaining SSL socket information");
                        this.sslSocketInfo = this.sslSocketUtils.getSocketSSLInfo(socket);
                    }
                    if (HTTPConnection.haveMSLargeWritesBug) {
                        outputStream = new MSLargeWritesBugStream(outputStream);
                    }
                    byteArrayOutputStream.writeTo(outputStream);
                    try {
                        if (this.ServProtVersKnown && this.ServerProtocolVersion >= 65537 && Util.hasToken(assembleHeaders[1], "100-continue")) {
                            enableSSLTunneling = new Response(request, this.Proxy_Host != null && this.Protocol != 1, this.input_demux);
                            enableSSLTunneling.timeout = 60;
                            if (enableSSLTunneling.getContinue() != 100) {
                                break;
                            }
                        }
                    }
                    catch (ParseException ex3) {
                        throw new IOException(ex3.toString());
                    }
                    catch (InterruptedIOException ex9) {}
                    finally {
                        if (enableSSLTunneling != null) {
                            enableSSLTunneling.timeout = 0;
                        }
                    }
                    if (request.getData() != null && request.getData().length > 0) {
                        if (request.delay_entity > 0L) {
                            final long n2 = request.delay_entity / 100L;
                            final long n3 = request.delay_entity / n2;
                            for (int n4 = 0; n4 < n2 && this.input_demux.available(null) == 0; ++n4) {
                                try {
                                    Thread.sleep(n3);
                                }
                                catch (InterruptedException ex10) {}
                            }
                            if (this.input_demux.available(null) == 0) {
                                outputStream.write(request.getData());
                            }
                            else {
                                b = false;
                            }
                        }
                        else {
                            outputStream.write(request.getData());
                        }
                    }
                    if (request.getStream() != null) {
                        request.getStream().goAhead(request, outputStream, 0);
                    }
                    else {
                        outputStream.flush();
                    }
                    if (enableSSLTunneling == null) {
                        enableSSLTunneling = new Response(request, this.Proxy_Host != null && this.Protocol != 1, this.input_demux);
                    }
                }
                catch (IOException ex4) {
                    Log.write(1, "Conn:  ", ex4);
                    this.closeDemux(ex4, true);
                    if (n == 0 || ex4 instanceof UnknownHostException || ex4 instanceof ConnectException || ex4 instanceof NoRouteToHostException || ex4 instanceof InterruptedIOException || request.aborted) {
                        throw ex4;
                    }
                    Log.write(1, "Conn:  Retrying request");
                    continue;
                }
                break;
            }
            this.prev_resp = enableSSLTunneling;
            if ((!this.keepAliveUnknown && !this.doesKeepAlive) || !b || (this.keepAliveReqMax != -1 && this.keepAliveReqLeft-- == 0)) {
                this.input_demux.markForClose(enableSSLTunneling);
                this.input_demux = null;
            }
            else {
                this.input_demux.restartTimer();
            }
            if (this.keepAliveReqMax != -1) {
                Log.write(1, "Conn:  Number of requests left: " + this.keepAliveReqLeft);
            }
            if (!this.ServProtVersKnown) {
                (this.early_stall = enableSSLTunneling).markAsFirstResponse(request);
            }
            if (this.keepAliveUnknown || !IdempotentSequence.methodIsIdempotent(request.getMethod()) || request.dont_pipeline || HTTPConnection.neverPipeline) {
                this.late_stall = enableSSLTunneling;
            }
            if (request.getStream() != null) {
                this.output_finished = false;
            }
            else {
                this.output_finished = true;
                this.notify();
            }
            Log.write(1, "Conn:  Request sent");
        }
        return enableSSLTunneling;
    }
    
    private Socket getSocket(final int n) throws IOException {
        Socket socket = null;
        String s;
        int port;
        if (this.Proxy_Host != null) {
            s = this.Proxy_Host;
            port = this.Proxy_Port;
        }
        else {
            s = this.Host;
            port = this.Port;
        }
        if (n == 0) {
            if (this.Socks_client != null) {
                Log.write(1, "Conn:  Creating new Socks Socket: " + s + ":" + port);
                socket = this.Socks_client.getSocket(s, port);
            }
            else {
                final InetAddress[] allByName = InetAddress.getAllByName(s);
                int i = 0;
                while (i < allByName.length) {
                    try {
                        if (this.LocalAddr == null) {
                            Log.write(1, "Conn:  Creating new Socket []: " + allByName[i] + ":" + port);
                            socket = new Socket(allByName[i], port);
                        }
                        else {
                            Log.write(1, "Conn:  Reusing Socket[]: " + allByName[i] + ":" + port);
                            socket = new Socket(allByName[i], port, this.LocalAddr, this.LocalPort);
                        }
                    }
                    catch (SocketException ex) {
                        if (i == allByName.length - 1) {
                            throw ex;
                        }
                        ++i;
                        continue;
                    }
                    break;
                }
            }
        }
        else {
            final EstablishConnection establishConnection = new EstablishConnection(s, port, this.Socks_client);
            establishConnection.start();
            try {
                establishConnection.join(n);
            }
            catch (InterruptedException ex2) {}
            if (establishConnection.getException() != null) {
                throw establishConnection.getException();
            }
            if ((socket = establishConnection.getSocket()) == null) {
                establishConnection.forget();
                if ((socket = establishConnection.getSocket()) == null) {
                    throw new InterruptedIOException("Connection establishment timed out");
                }
            }
        }
        return socket;
    }
    
    private Response enableSSLTunneling(final Socket[] array, final Request request, final int n) throws IOException, ModuleException {
        final Vector vector = new Vector<NVPair>();
        for (int i = 0; i < request.getHeaders().length; ++i) {
            final String name = request.getHeaders()[i].getName();
            if (name.equalsIgnoreCase("User-Agent") || name.equalsIgnoreCase("Proxy-Authorization")) {
                vector.addElement(request.getHeaders()[i]);
            }
        }
        final NVPair[] anArray = new NVPair[vector.size()];
        vector.copyInto(anArray);
        final Request request2 = new Request(this, "CONNECT", this.Host + ":" + this.Port, anArray, null, null, request.allowUI());
        request2.internal_subrequest = true;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(600);
        final HTTPResponse httpResponse = new HTTPResponse(this.gen_mod_insts(), n, request2);
        Response response = null;
        while (true) {
            this.handleRequest(request2, httpResponse, response, true);
            byteArrayOutputStream.reset();
            this.assembleHeaders(request2, byteArrayOutputStream);
            Log.write(1, "Conn:  Sending SSL-Tunneling Subrequest: ", byteArrayOutputStream);
            byteArrayOutputStream.writeTo(array[0].getOutputStream());
            response = new Response(request2, array[0].getInputStream());
            if (response.getStatusCode() == 200) {
                return null;
            }
            try {
                response.getData();
            }
            catch (IOException ex) {}
            try {
                array[0].close();
            }
            catch (IOException ex2) {}
            httpResponse.set(request2, response);
            if (!httpResponse.handleResponse()) {
                return response;
            }
            array[0] = this.getSocket(n);
        }
    }
    
    private String[] assembleHeaders(final Request request, final ByteArrayOutputStream out) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(out);
        final String[] array = { "", "" };
        final NVPair[] headers = request.getHeaders();
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        int n8 = -1;
        int n9 = -1;
        int n10 = -1;
        for (int i = 0; i < headers.length; ++i) {
            final String lowerCase = headers[i].getName().trim().toLowerCase();
            if (lowerCase.equals("host")) {
                n = i;
            }
            else if (lowerCase.equals("content-type")) {
                n2 = i;
            }
            else if (lowerCase.equals("user-agent")) {
                n3 = i;
            }
            else if (lowerCase.equals("connection")) {
                n4 = i;
            }
            else if (lowerCase.equals("proxy-connection")) {
                n5 = i;
            }
            else if (lowerCase.equals("keep-alive")) {
                n6 = i;
            }
            else if (lowerCase.equals("expect")) {
                n7 = i;
            }
            else if (lowerCase.equals("te")) {
                n8 = i;
            }
            else if (lowerCase.equals("transfer-encoding")) {
                n9 = i;
            }
            else if (lowerCase.equals("upgrade")) {
                n10 = i;
            }
        }
        final String escapeUnsafeChars = Util.escapeUnsafeChars(request.getRequestURI());
        if (this.Proxy_Host != null && this.Protocol != 1 && !escapeUnsafeChars.equals("*")) {
            dataOutputStream.writeBytes(request.getMethod() + " http://" + this.Host + ":" + this.Port + escapeUnsafeChars + " " + this.RequestProtocolVersion + "\r\n");
        }
        else {
            dataOutputStream.writeBytes(request.getMethod() + " " + escapeUnsafeChars + " " + this.RequestProtocolVersion + "\r\n");
        }
        final String s = (n >= 0) ? headers[n].getValue().trim() : this.Host;
        if (this.Port != URI.defaultPort(this.getProtocol())) {
            dataOutputStream.writeBytes("Host: " + s + ":" + this.Port + "\r\n");
        }
        else {
            dataOutputStream.writeBytes("Host: " + s + "\r\n");
        }
        String s2 = null;
        if (!this.ServProtVersKnown || this.ServerProtocolVersion < 65537 || n4 != -1) {
            if (n4 == -1) {
                s2 = "Keep-Alive";
                array[0] = "Keep-Alive";
            }
            else {
                array[0] = headers[n4].getValue().trim();
                s2 = array[0];
            }
            try {
                if (n6 != -1 && Util.hasToken(array[0], "keep-alive")) {
                    dataOutputStream.writeBytes("Keep-Alive: " + headers[n6].getValue().trim() + "\r\n");
                }
            }
            catch (ParseException ex) {
                throw new IOException(ex.toString());
            }
        }
        if (this.Proxy_Host != null && this.Protocol != 1 && (!this.ServProtVersKnown || this.ServerProtocolVersion < 65537) && s2 != null) {
            dataOutputStream.writeBytes("Proxy-Connection: ");
            dataOutputStream.writeBytes(s2);
            dataOutputStream.writeBytes("\r\n");
            s2 = null;
        }
        Label_0810: {
            if (s2 != null) {
                try {
                    if (!Util.hasToken(s2, "TE")) {
                        s2 += ", TE";
                    }
                    break Label_0810;
                }
                catch (ParseException ex2) {
                    throw new IOException(ex2.toString());
                }
            }
            s2 = "TE";
        }
        if (n10 != -1) {
            s2 += ", Upgrade";
        }
        if (s2 != null) {
            dataOutputStream.writeBytes("Connection: ");
            dataOutputStream.writeBytes(s2);
            dataOutputStream.writeBytes("\r\n");
        }
        if (n8 != -1) {
            dataOutputStream.writeBytes("TE: ");
            Vector header;
            try {
                header = Util.parseHeader(headers[n8].getValue());
            }
            catch (ParseException ex3) {
                throw new IOException(ex3.toString());
            }
            if (!header.contains(new HttpHeaderElement("trailers"))) {
                dataOutputStream.writeBytes("trailers, ");
            }
            dataOutputStream.writeBytes(headers[n8].getValue().trim() + "\r\n");
        }
        else {
            dataOutputStream.writeBytes("TE: trailers\r\n");
        }
        if (n3 != -1) {
            dataOutputStream.writeBytes("User-Agent: " + headers[n3].getValue().trim() + " " + "" + "\r\n");
        }
        else {
            dataOutputStream.writeBytes("User-Agent: \r\n");
        }
        for (int j = 0; j < headers.length; ++j) {
            if (j != n2 && j != n3 && j != n4 && j != n5 && j != n6 && j != n7 && j != n8 && j != n) {
                dataOutputStream.writeBytes(headers[j].getName().trim() + ": " + headers[j].getValue().trim() + "\r\n");
            }
        }
        if (request.getData() != null || request.getStream() != null) {
            dataOutputStream.writeBytes("Content-type: ");
            if (n2 != -1) {
                dataOutputStream.writeBytes(headers[n2].getValue().trim());
            }
            else {
                dataOutputStream.writeBytes("application/octet-stream");
            }
            dataOutputStream.writeBytes("\r\n");
            if (request.getData() != null) {
                dataOutputStream.writeBytes("Content-length: " + request.getData().length + "\r\n");
            }
            else if (request.getStream().getLength() != -1 && n9 == -1) {
                dataOutputStream.writeBytes("Content-length: " + request.getStream().getLength() + "\r\n");
            }
            if (n7 != -1) {
                array[1] = headers[n7].getValue().trim();
                dataOutputStream.writeBytes("Expect: " + array[1] + "\r\n");
            }
        }
        else if (n7 != -1) {
            Vector header2;
            try {
                header2 = Util.parseHeader(headers[n7].getValue());
            }
            catch (ParseException ex4) {
                throw new IOException(ex4.toString());
            }
            while (header2.removeElement(new HttpHeaderElement("100-continue"))) {}
            if (!header2.isEmpty()) {
                array[1] = Util.assembleHeader(header2);
                dataOutputStream.writeBytes("Expect: " + array[1] + "\r\n");
            }
        }
        dataOutputStream.writeBytes("\r\n");
        return array;
    }
    
    boolean handleFirstRequest(final Request request, final Response response) throws IOException {
        this.ServerProtocolVersion = String2ProtVers(response.getVersion());
        this.ServProtVersKnown = true;
        final int statusCode = response.getStatusCode();
        if (this.Proxy_Host != null && this.Protocol != 1 && response.getHeader("Via") == null && statusCode != 407 && statusCode != 502 && statusCode != 504) {
            this.ServerProtocolVersion = 65536;
        }
        Log.write(1, "Conn:  Protocol Version established: " + ProtVers2String(this.ServerProtocolVersion));
        if (this.ServerProtocolVersion == 65536 && (response.getStatusCode() == 400 || response.getStatusCode() == 500)) {
            if (this.input_demux != null) {
                this.input_demux.markForClose(response);
            }
            this.input_demux = null;
            this.RequestProtocolVersion = "HTTP/1.0";
            return false;
        }
        return true;
    }
    
    private void determineKeepAlive(final Response response) throws IOException {
        try {
            String s;
            if (this.ServerProtocolVersion >= 65537 || ((((this.Proxy_Host == null || this.Protocol == 1) && (s = response.getHeader("Connection")) != null) || (this.Proxy_Host != null && this.Protocol != 1 && (s = response.getHeader("Proxy-Connection")) != null)) && Util.hasToken(s, "keep-alive"))) {
                this.doesKeepAlive = true;
                this.keepAliveUnknown = false;
                Log.write(1, "Conn:  Keep-Alive enabled");
            }
            else if (response.getStatusCode() < 400) {
                this.keepAliveUnknown = false;
            }
            final String header;
            if (this.doesKeepAlive && this.ServerProtocolVersion == 65536 && (header = response.getHeader("Keep-Alive")) != null) {
                final HttpHeaderElement element = Util.getElement(Util.parseHeader(header), "max");
                if (element != null && element.getValue() != null) {
                    this.keepAliveReqMax = Integer.parseInt(element.getValue());
                    this.keepAliveReqLeft = this.keepAliveReqMax;
                    Log.write(1, "Conn:  Max Keep-Alive requests: " + this.keepAliveReqMax);
                }
            }
        }
        catch (ParseException ex) {}
        catch (NumberFormatException ex2) {}
        catch (ClassCastException ex3) {}
    }
    
    synchronized void outputFinished() {
        this.output_finished = true;
        this.notify();
    }
    
    synchronized void closeDemux(final IOException ex, final boolean b) {
        if (this.input_demux != null) {
            this.input_demux.close(ex, b);
        }
        this.early_stall = null;
        this.late_stall = null;
        this.prev_resp = null;
    }
    
    static final String ProtVers2String(final int n) {
        return "HTTP/" + (n >>> 16) + "." + (n & 0xFFFF);
    }
    
    static final int String2ProtVers(final String s) {
        final String substring = s.substring(5);
        final int index = substring.indexOf(46);
        return Integer.parseInt(substring.substring(0, index)) << 16 | Integer.parseInt(substring.substring(index + 1));
    }
    
    public String toString() {
        return this.getProtocol() + "://" + this.getHost() + ((this.getPort() != URI.defaultPort(this.getProtocol())) ? (":" + this.getPort()) : "");
    }
    
    static {
        dflt_context = new Object();
        HTTPConnection.Default_Proxy_Host = null;
        HTTPConnection.non_proxy_host_list = new CIHashtable();
        HTTPConnection.non_proxy_dom_list = new Vector();
        HTTPConnection.non_proxy_addr_list = new Vector();
        HTTPConnection.non_proxy_mask_list = new Vector();
        HTTPConnection.Default_Socks_client = null;
        HTTPConnection.no_chunked = false;
        HTTPConnection.force_1_0 = false;
        HTTPConnection.neverPipeline = false;
        HTTPConnection.noKeepAlives = false;
        HTTPConnection.haveMSLargeWritesBug = false;
        HTTPConnection.deferStreamed = false;
        HTTPConnection.DefaultTimeout = 0;
        HTTPConnection.defaultAllowUI = true;
        try {
            final String property = System.getProperty("http.proxyHost");
            if (property == null) {
                throw new Exception();
            }
            final int intValue = Integer.getInteger("http.proxyPort", -1);
            Log.write(1, "Conn:  using proxy " + property + ":" + intValue);
            setProxyServer(property, intValue);
        }
        catch (Exception ex2) {
            try {
                if (Boolean.getBoolean("proxySet")) {
                    final String property2 = System.getProperty("proxyHost");
                    final int intValue2 = Integer.getInteger("proxyPort", -1);
                    Log.write(1, "Conn:  using proxy " + property2 + ":" + intValue2);
                    setProxyServer(property2, intValue2);
                }
            }
            catch (Exception ex3) {
                HTTPConnection.Default_Proxy_Host = null;
            }
        }
        try {
            String s = System.getProperty("HTTPClient.nonProxyHosts");
            if (s == null) {
                s = System.getProperty("http.nonProxyHosts");
            }
            dontProxyFor(Util.splitProperty(s));
        }
        catch (Exception ex4) {}
        try {
            final String property3 = System.getProperty("HTTPClient.socksHost");
            if (property3 != null && property3.length() > 0) {
                final int intValue3 = Integer.getInteger("HTTPClient.socksPort", -1);
                final int intValue4 = Integer.getInteger("HTTPClient.socksVersion", -1);
                Log.write(1, "Conn:  using SOCKS " + property3 + ":" + intValue3);
                if (intValue4 == -1) {
                    setSocksServer(property3, intValue3);
                }
                else {
                    setSocksServer(property3, intValue3, intValue4);
                }
            }
        }
        catch (Exception ex5) {
            HTTPConnection.Default_Socks_client = null;
        }
        String property4 = "HTTPClient.RetryModule|HTTPClient.CookieModule|HTTPClient.RedirectionModule|HTTPClient.AuthorizationModule|HTTPClient.DefaultModule|HTTPClient.TransferEncodingModule|HTTPClient.ContentMD5Module|HTTPClient.ContentEncodingModule";
        boolean b = false;
        try {
            property4 = System.getProperty("HTTPClient.Modules", property4);
        }
        catch (SecurityException ex6) {
            b = true;
        }
        HTTPConnection.DefaultModuleList = new Vector();
        final String[] splitProperty = Util.splitProperty(property4);
        for (int i = 0; i < splitProperty.length; ++i) {
            try {
                HTTPConnection.DefaultModuleList.addElement(Class.forName(splitProperty[i]));
                Log.write(1, "Conn:  added module " + splitProperty[i]);
            }
            catch (ClassNotFoundException ex) {
                if (!b) {
                    throw new NoClassDefFoundError(ex.getMessage());
                }
            }
        }
        try {
            HTTPConnection.neverPipeline = Boolean.getBoolean("HTTPClient.disable_pipelining");
            if (HTTPConnection.neverPipeline) {
                Log.write(1, "Conn:  disabling pipelining");
            }
        }
        catch (Exception ex7) {}
        try {
            HTTPConnection.noKeepAlives = Boolean.getBoolean("HTTPClient.disableKeepAlives");
            if (HTTPConnection.noKeepAlives) {
                Log.write(1, "Conn:  disabling keep-alives");
            }
        }
        catch (Exception ex8) {}
        try {
            HTTPConnection.force_1_0 = Boolean.getBoolean("HTTPClient.forceHTTP_1.0");
            if (HTTPConnection.force_1_0) {
                Log.write(1, "Conn:  forcing HTTP/1.0 requests");
            }
        }
        catch (Exception ex9) {}
        try {
            HTTPConnection.no_chunked = Boolean.getBoolean("HTTPClient.dontChunkRequests");
            if (HTTPConnection.no_chunked) {
                Log.write(1, "Conn:  never chunking requests");
            }
        }
        catch (Exception ex10) {}
        try {
            if (System.getProperty("os.name").indexOf("Windows") >= 0 && System.getProperty("java.version").startsWith("1.1")) {
                HTTPConnection.haveMSLargeWritesBug = true;
            }
            if (HTTPConnection.haveMSLargeWritesBug) {
                Log.write(1, "Conn:  splitting large writes into 20K chunks (M$ bug)");
            }
        }
        catch (Exception ex11) {}
        try {
            HTTPConnection.deferStreamed = Boolean.getBoolean("HTTPClient.deferStreamed");
            if (HTTPConnection.deferStreamed) {
                Log.write(1, "Conn:  enabling defered handling of responses to streamed requests");
            }
        }
        catch (Exception ex12) {}
    }
    
    private class EstablishConnection extends Thread
    {
        String actual_host;
        int actual_port;
        IOException exception;
        Socket sock;
        SocksClient Socks_client;
        boolean close;
        
        EstablishConnection(final String s, final int n, final SocksClient socks_client) {
            super("EstablishConnection (" + s + ":" + n + ")");
            try {
                this.setDaemon(true);
            }
            catch (SecurityException ex) {}
            this.actual_host = s;
            this.actual_port = n;
            this.Socks_client = socks_client;
            this.exception = null;
            this.sock = null;
            this.close = false;
        }
        
        public void run() {
            try {
                if (this.Socks_client != null) {
                    this.sock = this.Socks_client.getSocket(this.actual_host, this.actual_port);
                }
                else {
                    final InetAddress[] allByName = InetAddress.getAllByName(this.actual_host);
                    int i = 0;
                    while (i < allByName.length) {
                        try {
                            if (HTTPConnection.this.LocalAddr == null) {
                                this.sock = new Socket(allByName[i], this.actual_port);
                            }
                            else {
                                this.sock = new Socket(allByName[i], this.actual_port, HTTPConnection.this.LocalAddr, HTTPConnection.this.LocalPort);
                            }
                        }
                        catch (SocketException ex) {
                            if (i == allByName.length - 1 || this.close) {
                                throw ex;
                            }
                            ++i;
                            continue;
                        }
                        break;
                    }
                }
            }
            catch (IOException exception) {
                this.exception = exception;
            }
            if (this.close && this.sock != null) {
                try {
                    this.sock.close();
                }
                catch (IOException ex2) {}
                this.sock = null;
            }
        }
        
        IOException getException() {
            return this.exception;
        }
        
        Socket getSocket() {
            return this.sock;
        }
        
        void forget() {
            this.close = true;
        }
    }
    
    private class MSLargeWritesBugStream extends FilterOutputStream
    {
        private final int CHUNK_SIZE = 20000;
        
        MSLargeWritesBugStream(final OutputStream out) {
            super(out);
        }
        
        public void write(final byte[] array, int n, int i) throws IOException {
            while (i > 20000) {
                super.out.write(array, n, 20000);
                n += 20000;
                i -= 20000;
            }
            super.out.write(array, n, i);
        }
    }
}
