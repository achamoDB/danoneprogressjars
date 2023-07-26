// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.ProtocolException;
import java.io.IOException;
import java.util.Hashtable;

class RedirectionModule implements HTTPClientModule
{
    private static Hashtable perm_redir_cntxt_list;
    private static Hashtable deferred_redir_list;
    private int level;
    private URI lastURI;
    private boolean new_con;
    private Request saved_req;
    
    RedirectionModule() {
        this.level = 0;
        this.lastURI = null;
        this.saved_req = null;
    }
    
    public int requestHandler(final Request request, final Response[] array) {
        final HTTPConnection connection = request.getConnection();
        final HttpOutputStream stream = request.getStream();
        if (stream != null && RedirectionModule.deferred_redir_list.get(stream) != null) {
            this.copyFrom((RedirectionModule)RedirectionModule.deferred_redir_list.remove(stream));
            request.copyFrom(this.saved_req);
            if (this.new_con) {
                return 5;
            }
            return 1;
        }
        else {
            URI key;
            try {
                key = new URI(new URI(connection.getProtocol(), connection.getHost(), connection.getPort(), null), request.getRequestURI());
            }
            catch (ParseException obj) {
                throw new Error("HTTPClient Internal Error: unexpected exception '" + obj + "'");
            }
            final URI uri;
            if ((uri = Util.getList(RedirectionModule.perm_redir_cntxt_list, request.getConnection().getContext()).get(key)) == null) {
                return 0;
            }
            final String pathAndQuery = uri.getPathAndQuery();
            request.setRequestURI(pathAndQuery);
            try {
                this.lastURI = new URI(uri, pathAndQuery);
            }
            catch (ParseException ex) {}
            Log.write(32, "RdirM: matched request in permanent redirection list - redoing request to " + this.lastURI.toExternalForm());
            if (!connection.isCompatibleWith(uri)) {
                HTTPConnection connection2;
                try {
                    connection2 = new HTTPConnection(uri);
                }
                catch (Exception obj2) {
                    throw new Error("HTTPClient Internal Error: unexpected exception '" + obj2 + "'");
                }
                connection2.setSSLParams(request.getConnection().getSSLParams());
                connection2.setContext(request.getConnection().getContext());
                request.setConnection(connection2);
                return 5;
            }
            return 1;
        }
    }
    
    public void responsePhase1Handler(final Response response, final RoRequest roRequest) throws IOException {
        final int statusCode = response.getStatusCode();
        if ((statusCode < 301 || statusCode > 307 || statusCode == 304) && this.lastURI != null) {
            response.setEffectiveURI(this.lastURI);
        }
    }
    
    public int responsePhase2Handler(final Response response, final Request request) throws IOException {
        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 302: {
                if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
                    Log.write(32, "RdirM: Received status: " + statusCode + " " + response.getReasonLine() + " - treating as 303");
                    statusCode = 303;
                }
            }
            case 301:
            case 303:
            case 307: {
                Log.write(32, "RdirM: Handling status: " + statusCode + " " + response.getReasonLine());
                if (!request.getMethod().equals("GET") && !request.getMethod().equals("HEAD") && statusCode != 303) {
                    Log.write(32, "RdirM: not redirected because method is neither HEAD nor GET");
                    if (statusCode == 301 && response.getHeader("Location") != null) {
                        update_perm_redir_list(request, this.resLocHdr(response.getHeader("Location"), request));
                    }
                    response.setEffectiveURI(this.lastURI);
                    return 10;
                }
            }
            case 305:
            case 306: {
                if (statusCode == 305 || statusCode == 306) {
                    Log.write(32, "RdirM: Handling status: " + statusCode + " " + response.getReasonLine());
                }
                if (statusCode == 305 && request.getConnection().getProxyHost() != null) {
                    Log.write(32, "RdirM: 305 ignored because a proxy is already in use");
                    response.setEffectiveURI(this.lastURI);
                    return 10;
                }
                if (this.level >= 15 || response.getHeader("Location") == null) {
                    if (this.level >= 15) {
                        Log.write(32, "RdirM: not redirected because of too many levels of redirection");
                    }
                    else {
                        Log.write(32, "RdirM: not redirected because no Location header was present");
                    }
                    response.setEffectiveURI(this.lastURI);
                    return 10;
                }
                ++this.level;
                final URI resLocHdr = this.resLocHdr(response.getHeader("Location"), request);
                this.new_con = false;
                HTTPConnection connection;
                String requestURI;
                if (statusCode == 305) {
                    connection = new HTTPConnection(request.getConnection().getProtocol(), request.getConnection().getHost(), request.getConnection().getPort());
                    connection.setCurrentProxy(resLocHdr.getHost(), resLocHdr.getPort());
                    connection.setSSLParams(request.getConnection().getSSLParams());
                    connection.setContext(request.getConnection().getContext());
                    this.new_con = true;
                    requestURI = request.getRequestURI();
                    request.setMethod("GET");
                    request.setData(null);
                    request.setStream(null);
                }
                else {
                    if (statusCode == 306) {
                        return 10;
                    }
                    if (request.getConnection().isCompatibleWith(resLocHdr)) {
                        connection = request.getConnection();
                        requestURI = resLocHdr.getPathAndQuery();
                    }
                    else {
                        try {
                            connection = new HTTPConnection(resLocHdr);
                            requestURI = resLocHdr.getPathAndQuery();
                        }
                        catch (Exception ex) {
                            if (request.getConnection().getProxyHost() == null || !resLocHdr.getScheme().equalsIgnoreCase("ftp")) {
                                return 10;
                            }
                            connection = new HTTPConnection("http", request.getConnection().getProxyHost(), request.getConnection().getProxyPort());
                            connection.setCurrentProxy(null, 0);
                            requestURI = resLocHdr.toExternalForm();
                        }
                        connection.setSSLParams(request.getConnection().getSSLParams());
                        connection.setContext(request.getConnection().getContext());
                        this.new_con = true;
                    }
                    if (statusCode == 303) {
                        if (!request.getMethod().equals("HEAD")) {
                            request.setMethod("GET");
                        }
                        request.setData(null);
                        request.setStream(null);
                    }
                    else {
                        if (request.getStream() != null) {
                            if (!HTTPConnection.deferStreamed) {
                                Log.write(32, "RdirM: status " + statusCode + " not handled - request " + "has an output stream");
                                return 10;
                            }
                            this.saved_req = (Request)request.clone();
                            RedirectionModule.deferred_redir_list.put(request.getStream(), this);
                            request.getStream().reset();
                            response.setRetryRequest(true);
                        }
                        if (statusCode == 301) {
                            try {
                                update_perm_redir_list(request, new URI(resLocHdr, requestURI));
                            }
                            catch (ParseException obj) {
                                throw new Error("HTTPClient Internal Error: unexpected exception '" + obj + "'");
                            }
                        }
                    }
                    final NVPair[] headers = request.getHeaders();
                    for (int i = 0; i < headers.length; ++i) {
                        if (headers[i].getName().equalsIgnoreCase("Referer")) {
                            headers[i] = new NVPair("Referer", request.getConnection() + request.getRequestURI());
                            break;
                        }
                    }
                }
                request.setConnection(connection);
                request.setRequestURI(requestURI);
                try {
                    response.getInputStream().close();
                }
                catch (IOException ex2) {}
                if (statusCode != 305 && statusCode != 306) {
                    try {
                        this.lastURI = new URI(resLocHdr, requestURI);
                    }
                    catch (ParseException ex3) {}
                    Log.write(32, "RdirM: request redirected to " + this.lastURI.toExternalForm() + " using method " + request.getMethod());
                }
                else {
                    Log.write(32, "RdirM: resending request using proxy " + connection.getProxyHost() + ":" + connection.getProxyPort());
                }
                if (request.getStream() != null) {
                    return 10;
                }
                if (this.new_con) {
                    return 15;
                }
                return 13;
            }
            default: {
                return 10;
            }
        }
    }
    
    public void responsePhase3Handler(final Response response, final RoRequest roRequest) {
    }
    
    public void trailerHandler(final Response response, final RoRequest roRequest) {
    }
    
    private static void update_perm_redir_list(final RoRequest roRequest, final URI value) {
        final HTTPConnection connection = roRequest.getConnection();
        URI key = null;
        try {
            key = new URI(new URI(connection.getProtocol(), connection.getHost(), connection.getPort(), null), roRequest.getRequestURI());
        }
        catch (ParseException ex) {}
        if (!key.equals(value)) {
            Util.getList(RedirectionModule.perm_redir_cntxt_list, connection.getContext()).put(key, value);
        }
    }
    
    private URI resLocHdr(final String s, final RoRequest roRequest) throws ProtocolException {
        try {
            final URI uri = new URI(new URI(new URI(roRequest.getConnection().getProtocol(), roRequest.getConnection().getHost(), roRequest.getConnection().getPort(), null), roRequest.getRequestURI()), s);
            if (uri.getHost() == null) {
                throw new ProtocolException("Malformed URL in Location header: `" + s + "' - missing host field");
            }
            return uri;
        }
        catch (ParseException ex) {
            throw new ProtocolException("Malformed URL in Location header: `" + s + "' - exception was: " + ex.getMessage());
        }
    }
    
    private void copyFrom(final RedirectionModule redirectionModule) {
        this.level = redirectionModule.level;
        this.lastURI = redirectionModule.lastURI;
        this.saved_req = redirectionModule.saved_req;
    }
    
    static {
        RedirectionModule.perm_redir_cntxt_list = new Hashtable();
        RedirectionModule.deferred_redir_list = new Hashtable();
    }
}
