// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.ByteArrayInputStream;
import java.io.InterruptedIOException;
import java.util.Enumeration;
import java.util.Date;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

public class HTTPResponse implements HTTPClientModuleConstants
{
    private HTTPClientModule[] modules;
    private int timeout;
    private Request request;
    Response response;
    private HttpOutputStream out_stream;
    private InputStream inp_stream;
    private int StatusCode;
    private String ReasonLine;
    private String Version;
    private URI OriginalURI;
    private URI EffectiveURI;
    private CIHashtable Headers;
    private CIHashtable Trailers;
    private int ContentLength;
    private byte[] Data;
    private boolean initialized;
    private boolean got_trailers;
    private boolean aborted;
    private boolean retry;
    private String method;
    private boolean handle_trailers;
    private boolean trailers_handled;
    
    HTTPResponse(final HTTPClientModule[] modules, final int timeout, final Request request) {
        this.request = null;
        this.response = null;
        this.out_stream = null;
        this.OriginalURI = null;
        this.EffectiveURI = null;
        this.Headers = null;
        this.Trailers = null;
        this.ContentLength = -1;
        this.Data = null;
        this.initialized = false;
        this.got_trailers = false;
        this.aborted = false;
        this.retry = false;
        this.method = null;
        this.handle_trailers = false;
        this.trailers_handled = false;
        this.modules = modules;
        this.timeout = timeout;
        try {
            final int index = request.getRequestURI().indexOf(63);
            this.OriginalURI = new URI(request.getConnection().getProtocol(), null, request.getConnection().getHost(), request.getConnection().getPort(), (index < 0) ? request.getRequestURI() : request.getRequestURI().substring(0, index), (index < 0) ? null : request.getRequestURI().substring(index + 1), null);
        }
        catch (ParseException ex) {}
        this.method = request.getMethod();
    }
    
    void set(final Request request, final Response response) {
        this.request = request;
        this.response = response;
        response.http_resp = this;
        response.timeout = this.timeout;
        this.aborted = response.final_resp;
    }
    
    void set(final Request request, final HttpOutputStream out_stream) {
        this.request = request;
        this.out_stream = out_stream;
    }
    
    public final int getStatusCode() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return this.StatusCode;
    }
    
    public final String getReasonLine() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return this.ReasonLine;
    }
    
    public final String getVersion() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return this.Version;
    }
    
    public final String getServer() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return this.getHeader("Server");
    }
    
    public final URI getOriginalURI() {
        return this.OriginalURI;
    }
    
    public final URL getEffectiveURL() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        if (this.EffectiveURI != null) {
            return this.EffectiveURI.toURL();
        }
        return null;
    }
    
    public final URI getEffectiveURI() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        if (this.EffectiveURI != null) {
            return this.EffectiveURI;
        }
        return this.OriginalURI;
    }
    
    public String getHeader(final String s) throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return (String)this.Headers.get(s.trim());
    }
    
    public int getHeaderAsInt(final String s) throws IOException, ModuleException, NumberFormatException {
        final String header = this.getHeader(s);
        if (header == null) {
            throw new NumberFormatException("null");
        }
        return Integer.parseInt(header);
    }
    
    public Date getHeaderAsDate(final String s) throws IOException, IllegalArgumentException, ModuleException {
        String s2 = this.getHeader(s);
        if (s2 == null) {
            return null;
        }
        if (s2.toUpperCase().indexOf("GMT") == -1 && s2.indexOf(32) > 0) {
            s2 += " GMT";
        }
        Date httpDate;
        try {
            httpDate = Util.parseHttpDate(s2);
        }
        catch (IllegalArgumentException ex) {
            long long1;
            try {
                long1 = Long.parseLong(s2);
            }
            catch (NumberFormatException ex2) {
                throw ex;
            }
            if (long1 < 0L) {
                long1 = 0L;
            }
            httpDate = new Date(long1 * 1000L);
        }
        return httpDate;
    }
    
    public Enumeration listHeaders() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        return this.Headers.keys();
    }
    
    public String getTrailer(final String s) throws IOException, ModuleException {
        if (!this.got_trailers) {
            this.getTrailers();
        }
        return (String)this.Trailers.get(s.trim());
    }
    
    public int getTrailerAsInt(final String s) throws IOException, ModuleException, NumberFormatException {
        final String trailer = this.getTrailer(s);
        if (trailer == null) {
            throw new NumberFormatException("null");
        }
        return Integer.parseInt(trailer);
    }
    
    public Date getTrailerAsDate(final String s) throws IOException, IllegalArgumentException, ModuleException {
        String s2 = this.getTrailer(s);
        if (s2 == null) {
            return null;
        }
        if (s2.toUpperCase().indexOf("GMT") == -1 && s2.indexOf(32) > 0) {
            s2 += " GMT";
        }
        Date httpDate;
        try {
            httpDate = Util.parseHttpDate(s2);
        }
        catch (IllegalArgumentException ex) {
            long long1;
            try {
                long1 = Long.parseLong(s2);
            }
            catch (NumberFormatException ex2) {
                throw ex;
            }
            if (long1 < 0L) {
                long1 = 0L;
            }
            httpDate = new Date(long1 * 1000L);
        }
        return httpDate;
    }
    
    public Enumeration listTrailers() throws IOException, ModuleException {
        if (!this.got_trailers) {
            this.getTrailers();
        }
        return this.Trailers.keys();
    }
    
    public synchronized byte[] getData() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        if (this.Data == null) {
            try {
                this.readResponseData(this.inp_stream);
            }
            catch (InterruptedIOException ex) {
                throw ex;
            }
            catch (IOException ex2) {
                Log.write(2, "HResp: (\"" + this.method + " " + this.OriginalURI.getPathAndQuery() + "\")");
                Log.write(2, "       ", ex2);
                try {
                    this.inp_stream.close();
                }
                catch (Exception ex3) {}
                throw ex2;
            }
            this.inp_stream.close();
        }
        return this.Data;
    }
    
    public synchronized String getText() throws IOException, ModuleException, ParseException {
        final String header = this.getHeader("Content-Type");
        if (header == null || !header.toLowerCase().startsWith("text/")) {
            throw new IOException("Content-Type `" + header + "' is not a text type");
        }
        String parameter = Util.getParameter("charset", header);
        if (parameter == null) {
            parameter = "ISO-8859-1";
        }
        return new String(this.getData(), parameter);
    }
    
    public synchronized InputStream getInputStream() throws IOException, ModuleException {
        if (!this.initialized) {
            this.handleResponse();
        }
        if (this.Data == null) {
            return this.inp_stream;
        }
        this.getData();
        return new ByteArrayInputStream(this.Data);
    }
    
    public boolean retryRequest() throws IOException, ModuleException {
        if (!this.initialized) {
            try {
                this.handleResponse();
            }
            catch (RetryException ex) {
                this.retry = this.response.retry;
            }
        }
        return this.retry;
    }
    
    public String toString() {
        if (!this.initialized) {
            try {
                this.handleResponse();
            }
            catch (Exception obj) {
                if (!(obj instanceof InterruptedIOException)) {
                    Log.write(2, "HResp: (\"" + this.method + " " + this.OriginalURI.getPathAndQuery() + "\")");
                    Log.write(2, "       ", obj);
                }
                return "Failed to read headers: " + obj;
            }
        }
        final String property = System.getProperty("line.separator", "\n");
        final StringBuffer sb = new StringBuffer(this.Version);
        sb.append(' ');
        sb.append(this.StatusCode);
        sb.append(' ');
        sb.append(this.ReasonLine);
        sb.append(property);
        if (this.EffectiveURI != null) {
            sb.append("Effective-URI: ");
            sb.append(this.EffectiveURI);
            sb.append(property);
        }
        final Enumeration keys = this.Headers.keys();
        while (keys.hasMoreElements()) {
            final String str = keys.nextElement();
            sb.append(str);
            sb.append(": ");
            sb.append(this.Headers.get(str));
            sb.append(property);
        }
        return sb.toString();
    }
    
    HTTPClientModule[] getModules() {
        return this.modules;
    }
    
    synchronized boolean handleResponse() throws IOException, ModuleException {
        if (this.initialized) {
            return false;
        }
        if (this.out_stream != null) {
            this.response = this.out_stream.getResponse();
            this.response.http_resp = this;
            this.out_stream = null;
        }
    Label_0418:
        while (true) {
            for (int n = 0; n < this.modules.length && !this.aborted; ++n) {
                try {
                    this.modules[n].responsePhase1Handler(this.response, this.request);
                }
                catch (RetryException ex) {
                    if (ex.restart) {
                        continue Label_0418;
                    }
                    throw ex;
                }
            }
            int n2 = 0;
            while (n2 < this.modules.length && !this.aborted) {
                final int responsePhase2Handler = this.modules[n2].responsePhase2Handler(this.response, this.request);
                switch (responsePhase2Handler) {
                    case 10: {
                        ++n2;
                        continue;
                    }
                    case 11: {
                        continue Label_0418;
                    }
                    case 12: {
                        break Label_0418;
                    }
                    case 13:
                    case 15: {
                        this.response.getInputStream().close();
                        if (this.handle_trailers) {
                            this.invokeTrailerHandlers(true);
                        }
                        if (this.request.internal_subrequest) {
                            return true;
                        }
                        this.request.getConnection().handleRequest(this.request, this, this.response, true);
                        if (this.initialized) {
                            break Label_0418;
                        }
                        continue Label_0418;
                    }
                    case 14:
                    case 16: {
                        this.response.getInputStream().close();
                        if (this.handle_trailers) {
                            this.invokeTrailerHandlers(true);
                        }
                        if (this.request.internal_subrequest) {
                            return true;
                        }
                        this.request.getConnection().handleRequest(this.request, this, this.response, false);
                        continue Label_0418;
                    }
                    default: {
                        throw new Error("HTTPClient Internal Error: invalid status " + responsePhase2Handler + " returned by module " + this.modules[n2].getClass().getName());
                    }
                }
            }
            for (int n3 = 0; n3 < this.modules.length && !this.aborted; ++n3) {
                this.modules[n3].responsePhase3Handler(this.response, this.request);
            }
            break;
        }
        this.response.getStatusCode();
        if (!this.request.internal_subrequest) {
            this.init(this.response);
        }
        if (this.handle_trailers) {
            this.invokeTrailerHandlers(false);
        }
        return false;
    }
    
    void init(final Response response) {
        if (this.initialized) {
            return;
        }
        this.StatusCode = response.StatusCode;
        this.ReasonLine = response.ReasonLine;
        this.Version = response.Version;
        this.EffectiveURI = response.EffectiveURI;
        this.ContentLength = response.ContentLength;
        this.Headers = response.Headers;
        this.inp_stream = response.inp_stream;
        this.Data = response.Data;
        this.retry = response.retry;
        this.initialized = true;
    }
    
    void invokeTrailerHandlers(final boolean b) throws IOException, ModuleException {
        if (this.trailers_handled) {
            return;
        }
        if (!b && !this.initialized) {
            this.handle_trailers = true;
            return;
        }
        for (int n = 0; n < this.modules.length && !this.aborted; ++n) {
            this.modules[n].trailerHandler(this.response, this.request);
        }
        this.trailers_handled = true;
    }
    
    void markAborted() {
        this.aborted = true;
    }
    
    private synchronized void getTrailers() throws IOException, ModuleException {
        if (this.got_trailers) {
            return;
        }
        if (!this.initialized) {
            this.handleResponse();
        }
        this.response.getTrailer("Any");
        this.Trailers = this.response.Trailers;
        this.got_trailers = true;
        this.invokeTrailerHandlers(false);
    }
    
    private void readResponseData(final InputStream inputStream) throws IOException, ModuleException {
        if (this.ContentLength == 0) {
            return;
        }
        if (this.Data == null) {
            this.Data = new byte[0];
        }
        int length = this.Data.length;
        try {
            if (this.getHeader("Content-Length") != null) {
                int read = 0;
                this.Data = new byte[this.ContentLength];
                do {
                    length += read;
                    read = inputStream.read(this.Data, length, this.ContentLength - length);
                } while (read != -1 && length + read < this.ContentLength);
            }
            else {
                final int len = 1000;
                int read2 = 0;
                long n = 0L;
                do {
                    if (0L == n++ % 10L) {
                        Thread.currentThread();
                        Thread.yield();
                    }
                    length += read2;
                    this.Data = Util.resizeArray(this.Data, length + len);
                } while ((read2 = inputStream.read(this.Data, length, len)) != -1);
                this.Data = Util.resizeArray(this.Data, length);
            }
        }
        catch (IOException ex) {
            this.Data = Util.resizeArray(this.Data, length);
            throw ex;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException ex2) {}
        }
    }
    
    int getTimeout() {
        return this.timeout;
    }
}
