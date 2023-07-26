// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.NoSuchElementException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.io.EOFException;
import java.io.SequenceInputStream;
import java.util.Vector;
import java.net.ProtocolException;
import java.io.ByteArrayInputStream;
import java.io.InterruptedIOException;
import java.util.Date;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public final class Response implements RoResponse, GlobalConstants, Cloneable
{
    private static final Hashtable singleValueHeaders;
    private HTTPConnection connection;
    private StreamDemultiplexor stream_handler;
    HTTPResponse http_resp;
    int timeout;
    public InputStream inp_stream;
    private RespInputStream resp_inp_stream;
    private String method;
    String resource;
    private boolean used_proxy;
    private boolean sent_entity;
    int StatusCode;
    String ReasonLine;
    String Version;
    URI EffectiveURI;
    CIHashtable Headers;
    CIHashtable Trailers;
    int ContentLength;
    int cd_type;
    byte[] Data;
    boolean reading_headers;
    boolean got_headers;
    boolean got_trailers;
    private IOException exception;
    boolean final_resp;
    boolean retry;
    private byte[] buf;
    private int buf_pos;
    private StringBuffer hdrs;
    private boolean reading_lines;
    private boolean bol;
    private boolean got_cr;
    boolean trailers_read;
    Request req;
    boolean isFirstResponse;
    
    Response(final Request request, final boolean used_proxy, final StreamDemultiplexor stream_handler) throws IOException {
        this.timeout = 0;
        this.resp_inp_stream = null;
        this.StatusCode = 0;
        this.EffectiveURI = null;
        this.Headers = new CIHashtable();
        this.Trailers = new CIHashtable();
        this.ContentLength = -1;
        this.cd_type = 1;
        this.Data = null;
        this.reading_headers = false;
        this.got_headers = false;
        this.got_trailers = false;
        this.exception = null;
        this.final_resp = false;
        this.retry = false;
        this.buf = new byte[7];
        this.buf_pos = 0;
        this.hdrs = new StringBuffer(400);
        this.reading_lines = false;
        this.bol = true;
        this.got_cr = false;
        this.trailers_read = false;
        this.req = null;
        this.isFirstResponse = false;
        this.connection = request.getConnection();
        this.method = request.getMethod();
        this.resource = request.getRequestURI();
        this.used_proxy = used_proxy;
        this.stream_handler = stream_handler;
        this.sent_entity = (request.getData() != null);
        stream_handler.register(this, request);
        this.resp_inp_stream = stream_handler.getStream(this);
        this.inp_stream = this.resp_inp_stream;
    }
    
    Response(final Request request, final InputStream inp_stream) throws IOException {
        this.timeout = 0;
        this.resp_inp_stream = null;
        this.StatusCode = 0;
        this.EffectiveURI = null;
        this.Headers = new CIHashtable();
        this.Trailers = new CIHashtable();
        this.ContentLength = -1;
        this.cd_type = 1;
        this.Data = null;
        this.reading_headers = false;
        this.got_headers = false;
        this.got_trailers = false;
        this.exception = null;
        this.final_resp = false;
        this.retry = false;
        this.buf = new byte[7];
        this.buf_pos = 0;
        this.hdrs = new StringBuffer(400);
        this.reading_lines = false;
        this.bol = true;
        this.got_cr = false;
        this.trailers_read = false;
        this.req = null;
        this.isFirstResponse = false;
        this.connection = request.getConnection();
        this.method = request.getMethod();
        this.resource = request.getRequestURI();
        this.used_proxy = false;
        this.stream_handler = null;
        this.sent_entity = (request.getData() != null);
        this.inp_stream = inp_stream;
    }
    
    public Response(final String version, final int statusCode, final String reasonLine, final NVPair[] array, final byte[] data, final InputStream inp_stream, final int contentLength) {
        this.timeout = 0;
        this.resp_inp_stream = null;
        this.StatusCode = 0;
        this.EffectiveURI = null;
        this.Headers = new CIHashtable();
        this.Trailers = new CIHashtable();
        this.ContentLength = -1;
        this.cd_type = 1;
        this.Data = null;
        this.reading_headers = false;
        this.got_headers = false;
        this.got_trailers = false;
        this.exception = null;
        this.final_resp = false;
        this.retry = false;
        this.buf = new byte[7];
        this.buf_pos = 0;
        this.hdrs = new StringBuffer(400);
        this.reading_lines = false;
        this.bol = true;
        this.got_cr = false;
        this.trailers_read = false;
        this.req = null;
        this.isFirstResponse = false;
        this.Version = version;
        this.StatusCode = statusCode;
        this.ReasonLine = reasonLine;
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.setHeader(array[i].getName(), array[i].getValue());
            }
        }
        if (data != null) {
            this.Data = data;
        }
        else if (inp_stream == null) {
            this.Data = new byte[0];
        }
        else {
            this.inp_stream = inp_stream;
            this.ContentLength = contentLength;
        }
        this.got_headers = true;
        this.got_trailers = true;
    }
    
    public final int getStatusCode() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return this.StatusCode;
    }
    
    public final String getReasonLine() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return this.ReasonLine;
    }
    
    public final String getVersion() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return this.Version;
    }
    
    int getContinue() throws IOException {
        this.getHeaders(false);
        return this.StatusCode;
    }
    
    public final URI getEffectiveURI() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return this.EffectiveURI;
    }
    
    public void setEffectiveURI(final URI effectiveURI) {
        this.EffectiveURI = effectiveURI;
    }
    
    public final URL getEffectiveURL() throws IOException {
        return this.getEffectiveURI().toURL();
    }
    
    public void setEffectiveURL(final URL url) {
        try {
            this.setEffectiveURI(new URI(url));
        }
        catch (ParseException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public String getHeader(final String s) throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return (String)this.Headers.get(s.trim());
    }
    
    public int getHeaderAsInt(final String s) throws IOException, NumberFormatException {
        final String header = this.getHeader(s);
        if (header == null) {
            throw new NumberFormatException("null");
        }
        return Integer.parseInt(header);
    }
    
    public Date getHeaderAsDate(final String s) throws IOException, IllegalArgumentException {
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
    
    public void setHeader(final String s, final String s2) {
        this.Headers.put(s.trim(), s2.trim());
    }
    
    public void deleteHeader(final String s) {
        this.Headers.remove(s.trim());
    }
    
    public String getTrailer(final String s) throws IOException {
        if (!this.got_trailers) {
            this.getTrailers();
        }
        return (String)this.Trailers.get(s.trim());
    }
    
    public int getTrailerAsInt(final String s) throws IOException, NumberFormatException {
        final String trailer = this.getTrailer(s);
        if (trailer == null) {
            throw new NumberFormatException("null");
        }
        return Integer.parseInt(trailer);
    }
    
    public Date getTrailerAsDate(final String s) throws IOException, IllegalArgumentException {
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
    
    public void setTrailer(final String s, final String s2) {
        this.Trailers.put(s.trim(), s2.trim());
    }
    
    public void deleteTrailer(final String s) {
        this.Trailers.remove(s.trim());
    }
    
    public synchronized byte[] getData() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        if (this.Data == null) {
            try {
                this.readResponseData(this.inp_stream);
            }
            catch (InterruptedIOException ex) {
                throw ex;
            }
            catch (IOException ex2) {
                Log.write(2, "Resp:  (" + this.inp_stream.hashCode() + ")", ex2);
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
    
    public synchronized InputStream getInputStream() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        if (this.Data == null) {
            return this.inp_stream;
        }
        return new ByteArrayInputStream(this.Data);
    }
    
    public synchronized boolean hasEntity() throws IOException {
        if (!this.got_headers) {
            this.getHeaders(true);
        }
        return this.cd_type != 2;
    }
    
    public void setRetryRequest(final boolean retry) {
        this.retry = retry;
    }
    
    public boolean retryRequest() {
        return this.retry;
    }
    
    private synchronized void getHeaders(final boolean b) throws IOException {
        if (this.got_headers) {
            return;
        }
        if (this.exception != null) {
            this.exception.fillInStackTrace();
            throw this.exception;
        }
        this.reading_headers = true;
        try {
            do {
                this.Headers.clear();
                this.parseResponseHeaders(this.readResponseHeaders(this.inp_stream));
            } while ((this.StatusCode == 100 && b) || (this.StatusCode > 101 && this.StatusCode < 200));
        }
        catch (IOException exception) {
            if (!(exception instanceof InterruptedIOException)) {
                this.exception = exception;
            }
            if (exception instanceof ProtocolException) {
                this.cd_type = 3;
                if (this.stream_handler != null) {
                    this.stream_handler.markForClose(this);
                }
            }
            throw exception;
        }
        finally {
            this.reading_headers = false;
        }
        if (this.StatusCode == 100) {
            return;
        }
        int int1 = -1;
        final String s = (String)this.Headers.get("Content-Length");
        if (s != null) {
            try {
                int1 = Integer.parseInt(s);
                if (int1 < 0) {
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException ex2) {
                throw new ProtocolException("Invalid Content-length header received: " + s);
            }
        }
        boolean equalsIgnoreCase = false;
        boolean b2 = true;
        boolean b3 = false;
        Vector header = null;
        try {
            header = Util.parseHeader((String)this.Headers.get("Transfer-Encoding"));
        }
        catch (ParseException ex3) {}
        if (header != null) {
            equalsIgnoreCase = header.lastElement().getName().equalsIgnoreCase("chunked");
            for (int i = 0; i < header.size(); ++i) {
                if (header.elementAt(i).getName().equalsIgnoreCase("identity")) {
                    header.removeElementAt(i--);
                }
                else {
                    b2 = false;
                }
            }
        }
        try {
            final String s2;
            if ((s2 = (String)this.Headers.get("Content-Type")) != null) {
                final Vector header2 = Util.parseHeader(s2);
                b3 = (header2.contains(new HttpHeaderElement("multipart/byteranges")) || header2.contains(new HttpHeaderElement("multipart/x-byteranges")));
            }
        }
        catch (ParseException ex4) {}
        if (this.StatusCode < 200 || this.StatusCode == 204 || this.StatusCode == 205 || this.StatusCode == 304) {
            this.cd_type = 2;
        }
        else if (equalsIgnoreCase) {
            this.cd_type = 5;
            header.removeElementAt(header.size() - 1);
            if (header.size() > 0) {
                this.setHeader("Transfer-Encoding", Util.assembleHeader(header));
            }
            else {
                this.deleteHeader("Transfer-Encoding");
            }
        }
        else if (int1 != -1 && b2) {
            this.cd_type = 4;
        }
        else if (b3 && b2) {
            this.cd_type = 6;
        }
        else if (!this.method.equals("HEAD")) {
            this.cd_type = 3;
            if (this.stream_handler != null) {
                this.stream_handler.markForClose(this);
            }
            if (this.Version.equals("HTTP/0.9")) {
                this.inp_stream = new SequenceInputStream(new ByteArrayInputStream(this.Data), this.inp_stream);
                this.Data = null;
            }
        }
        if (this.cd_type == 4) {
            this.ContentLength = int1;
        }
        else {
            this.deleteHeader("Content-Length");
        }
        if (this.method.equals("HEAD")) {
            this.cd_type = 2;
        }
        if (this.cd_type == 2) {
            this.ContentLength = 0;
            this.Data = new byte[0];
            this.inp_stream.close();
        }
        Log.write(2, "Resp:  Response entity delimiter: " + ((this.cd_type == 2) ? "No Entity" : ((this.cd_type == 3) ? "Close" : ((this.cd_type == 4) ? "Content-Length" : ((this.cd_type == 5) ? "Chunked" : ((this.cd_type == 6) ? "Multipart" : "???"))))) + " (" + this.inp_stream.hashCode() + ")");
        if (this.connection.ServerProtocolVersion >= 65537) {
            this.deleteHeader("Proxy-Connection");
        }
        else {
            if (this.connection.getProxyHost() != null) {
                this.deleteHeader("Connection");
            }
            else {
                this.deleteHeader("Proxy-Connection");
            }
            Vector<HttpHeaderElement> header3;
            try {
                header3 = (Vector<HttpHeaderElement>)Util.parseHeader((String)this.Headers.get("Connection"));
            }
            catch (ParseException ex5) {
                header3 = null;
            }
            if (header3 != null) {
                for (int j = 0; j < header3.size(); ++j) {
                    final String name = header3.elementAt(j).getName();
                    if (!name.equalsIgnoreCase("keep-alive")) {
                        header3.removeElementAt(j);
                        this.deleteHeader(name);
                        --j;
                    }
                }
                if (header3.size() > 0) {
                    this.setHeader("Connection", Util.assembleHeader(header3));
                }
                else {
                    this.deleteHeader("Connection");
                }
            }
            Vector<HttpHeaderElement> header4;
            try {
                header4 = (Vector<HttpHeaderElement>)Util.parseHeader((String)this.Headers.get("Proxy-Connection"));
            }
            catch (ParseException ex6) {
                header4 = null;
            }
            if (header4 != null) {
                for (int k = 0; k < header4.size(); ++k) {
                    final String name2 = header4.elementAt(k).getName();
                    if (!name2.equalsIgnoreCase("keep-alive")) {
                        header4.removeElementAt(k);
                        this.deleteHeader(name2);
                        --k;
                    }
                }
                if (header4.size() > 0) {
                    this.setHeader("Proxy-Connection", Util.assembleHeader(header4));
                }
                else {
                    this.deleteHeader("Proxy-Connection");
                }
            }
        }
        this.got_headers = true;
        if (this.isFirstResponse && !this.connection.handleFirstRequest(this.req, this)) {
            Response sendRequest;
            try {
                sendRequest = this.connection.sendRequest(this.req, this.timeout);
            }
            catch (ModuleException ex) {
                throw new IOException(ex.toString());
            }
            sendRequest.getVersion();
            this.StatusCode = sendRequest.StatusCode;
            this.ReasonLine = sendRequest.ReasonLine;
            this.Version = sendRequest.Version;
            this.EffectiveURI = sendRequest.EffectiveURI;
            this.ContentLength = sendRequest.ContentLength;
            this.Headers = sendRequest.Headers;
            this.inp_stream = sendRequest.inp_stream;
            this.Data = sendRequest.Data;
            this.req = null;
        }
    }
    
    private String readResponseHeaders(final InputStream inputStream) throws IOException {
        if (this.buf_pos == 0) {
            Log.write(2, "Resp:  Reading Response headers " + this.inp_stream.hashCode());
        }
        else {
            Log.write(2, "Resp:  Resuming reading Response headers " + this.inp_stream.hashCode());
        }
        if (!this.reading_lines) {
            try {
                Label_0123: {
                    if (this.buf_pos == 0) {
                        int read;
                        while ((read = inputStream.read()) != -1) {
                            if (!Character.isWhitespace((char)read)) {
                                this.buf[0] = (byte)read;
                                this.buf_pos = 1;
                                break Label_0123;
                            }
                        }
                        throw new EOFException("Encountered premature EOF while reading Version");
                    }
                }
                while (this.buf_pos < this.buf.length) {
                    final int read2 = inputStream.read(this.buf, this.buf_pos, this.buf.length - this.buf_pos);
                    if (read2 == -1) {
                        throw new EOFException("Encountered premature EOF while reading Version");
                    }
                    this.buf_pos += read2;
                }
            }
            catch (EOFException ex) {
                Log.write(2, "Resp:  (" + this.inp_stream.hashCode() + ")", ex);
                throw ex;
            }
            for (int i = 0; i < this.buf.length; ++i) {
                this.hdrs.append((char)this.buf[i]);
            }
            this.reading_lines = true;
        }
        if (this.hdrs.toString().startsWith("HTTP/") || this.hdrs.toString().startsWith("HTTP ")) {
            this.readLines(inputStream);
        }
        this.buf_pos = 0;
        this.reading_lines = false;
        this.bol = true;
        this.got_cr = false;
        final String string = this.hdrs.toString();
        this.hdrs.setLength(0);
        return string;
    }
    
    void readTrailers(final InputStream inputStream) throws IOException {
        try {
            this.readLines(inputStream);
            this.trailers_read = true;
        }
        catch (IOException exception) {
            if (!(exception instanceof InterruptedIOException)) {
                this.exception = exception;
            }
            throw exception;
        }
    }
    
    private void readLines(final InputStream inputStream) throws IOException {
        while (true) {
            final int read = inputStream.read();
            switch (read) {
                case -1: {
                    throw new EOFException("Encountered premature EOF while reading headers:\n" + (Object)this.hdrs);
                }
                case 13: {
                    this.got_cr = true;
                    continue;
                }
                case 10: {
                    if (this.bol) {
                        return;
                    }
                    this.hdrs.append('\n');
                    this.bol = true;
                    this.got_cr = false;
                    continue;
                }
                case 9:
                case 32: {
                    if (this.bol) {
                        this.hdrs.setCharAt(this.hdrs.length() - 1, ' ');
                        this.bol = false;
                        continue;
                    }
                    break;
                }
            }
            if (this.got_cr) {
                this.hdrs.append('\r');
                this.got_cr = false;
            }
            this.hdrs.append((char)(read & 0xFF));
            this.bol = false;
        }
    }
    
    private void parseResponseHeaders(final String s) throws ProtocolException {
        String nextToken = null;
        final StringTokenizer stringTokenizer = new StringTokenizer(s, "\r\n");
        if (Log.isEnabled(2)) {
            Log.write(2, "Resp:  Parsing Response headers from Request \"" + this.method + " " + this.resource + "\":  (" + this.inp_stream.hashCode() + ")\n\n" + s);
        }
        if (!s.regionMatches(true, 0, "HTTP/", 0, 5) && !s.regionMatches(true, 0, "HTTP ", 0, 5)) {
            this.Version = "HTTP/0.9";
            this.StatusCode = 200;
            this.ReasonLine = "OK";
            try {
                this.Data = s.getBytes("8859_1");
            }
            catch (UnsupportedEncodingException ex) {
                throw new Error(ex.toString());
            }
            return;
        }
        StringTokenizer stringTokenizer2;
        try {
            nextToken = stringTokenizer.nextToken();
            stringTokenizer2 = new StringTokenizer(nextToken, " \t");
            this.Version = stringTokenizer2.nextToken();
            this.StatusCode = Integer.valueOf(stringTokenizer2.nextToken());
            if (this.Version.equalsIgnoreCase("HTTP")) {
                this.Version = "HTTP/1.0";
            }
        }
        catch (NoSuchElementException ex2) {
            throw new ProtocolException("Invalid HTTP status line received: " + nextToken);
        }
        try {
            this.ReasonLine = stringTokenizer2.nextToken("").trim();
        }
        catch (NoSuchElementException ex3) {
            this.ReasonLine = "";
        }
        if (this.StatusCode >= 300 && this.sent_entity && this.stream_handler != null) {
            this.stream_handler.markForClose(this);
        }
        this.parseHeaderFields(stringTokenizer, this.Headers);
        if (this.Headers.get("Trailer") != null && this.resp_inp_stream != null) {
            this.resp_inp_stream.dontTruncate();
        }
        boolean b;
        if (this.Version.equalsIgnoreCase("HTTP/0.9") || this.Version.equalsIgnoreCase("HTTP/1.0")) {
            b = false;
        }
        else {
            b = true;
        }
        try {
            final String s2 = (String)this.Headers.get("Connection");
            final String s3 = (String)this.Headers.get("Proxy-Connection");
            if (((b && s2 != null && Util.hasToken(s2, "close")) || (!b && (this.used_proxy || s2 == null || !Util.hasToken(s2, "keep-alive")) && (!this.used_proxy || s3 == null || !Util.hasToken(s3, "keep-alive")))) && this.stream_handler != null) {
                this.stream_handler.markForClose(this);
            }
        }
        catch (ParseException ex4) {}
    }
    
    private synchronized void getTrailers() throws IOException {
        if (this.got_trailers) {
            return;
        }
        if (this.exception != null) {
            this.exception.fillInStackTrace();
            throw this.exception;
        }
        Log.write(2, "Resp:  Reading Response trailers " + this.inp_stream.hashCode());
        try {
            if (!this.trailers_read && this.resp_inp_stream != null) {
                this.resp_inp_stream.readAll(this.timeout);
            }
            if (this.trailers_read) {
                Log.write(2, "Resp:  Parsing Response trailers from Request \"" + this.method + " " + this.resource + "\":  (" + this.inp_stream.hashCode() + ")\n\n" + (Object)this.hdrs);
                this.parseHeaderFields(new StringTokenizer(this.hdrs.toString(), "\r\n"), this.Trailers);
            }
        }
        finally {
            this.got_trailers = true;
        }
    }
    
    private void parseHeaderFields(final StringTokenizer stringTokenizer, final CIHashtable ciHashtable) throws ProtocolException {
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            int endIndex = nextToken.indexOf(58);
            if (endIndex == -1) {
                endIndex = nextToken.indexOf(32);
            }
            if (endIndex == -1) {
                throw new ProtocolException("Invalid HTTP header received: " + nextToken);
            }
            final String trim = nextToken.substring(0, endIndex).trim();
            final String trim2 = nextToken.substring(endIndex + 1).trim();
            if (!Response.singleValueHeaders.containsKey(trim.toLowerCase())) {
                final String str = (String)ciHashtable.get(trim);
                if (str == null) {
                    ciHashtable.put(trim, trim2);
                }
                else {
                    ciHashtable.put(trim, str + ", " + trim2);
                }
            }
            else {
                ciHashtable.put(trim, trim2);
            }
        }
    }
    
    private void readResponseData(final InputStream inputStream) throws IOException {
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
                do {
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
    
    void markAsFirstResponse(final Request req) {
        this.req = req;
        this.isFirstResponse = true;
    }
    
    public Object clone() {
        Response response;
        try {
            response = (Response)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString());
        }
        response.Headers = (CIHashtable)this.Headers.clone();
        response.Trailers = (CIHashtable)this.Trailers.clone();
        return response;
    }
    
    static {
        final String[] array = { "age", "location", "content-base", "content-length", "content-location", "content-md5", "content-range", "content-type", "date", "etag", "expires", "proxy-authenticate", "retry-after" };
        singleValueHeaders = new Hashtable(array.length);
        for (int i = 0; i < array.length; ++i) {
            Response.singleValueHeaders.put(array[i], array[i]);
        }
    }
}
