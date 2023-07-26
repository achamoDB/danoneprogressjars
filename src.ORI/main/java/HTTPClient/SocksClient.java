// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.UnknownHostException;
import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

class SocksClient
{
    private String socks_host;
    private int socks_port;
    private int socks_version;
    private static final byte CONNECT = 1;
    private static final byte BIND = 2;
    private static final byte UDP_ASS = 3;
    private static final byte NO_AUTH = 0;
    private static final byte GSSAPI = 1;
    private static final byte USERPWD = 2;
    private static final byte NO_ACC = -1;
    private static final byte IP_V4 = 1;
    private static final byte DMNAME = 3;
    private static final byte IP_V6 = 4;
    private boolean v4A;
    private byte[] user;
    
    SocksClient(final String socks_host, final int socks_port) {
        this.v4A = false;
        this.user = null;
        this.socks_host = socks_host;
        this.socks_port = socks_port;
        this.socks_version = -1;
    }
    
    SocksClient(final String socks_host, final int socks_port, final int n) throws SocksException {
        this.v4A = false;
        this.user = null;
        this.socks_host = socks_host;
        this.socks_port = socks_port;
        if (n != 4 && n != 5) {
            throw new SocksException("SOCKS Version not supported: " + n);
        }
        this.socks_version = n;
    }
    
    Socket getSocket(final String s, final int n) throws IOException {
        return this.getSocket(s, n, null, -1);
    }
    
    Socket getSocket(final String s, final int n, final InetAddress inetAddress, final int n2) throws IOException {
        Socket socket = null;
        try {
            Log.write(64, "Socks: contacting server on " + this.socks_host + ":" + this.socks_port);
            socket = connect(this.socks_host, this.socks_port, inetAddress, n2);
            final InputStream inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();
            switch (this.socks_version) {
                case 4: {
                    this.v4ProtExchg(inputStream, outputStream, s, n);
                    break;
                }
                case 5: {
                    this.v5ProtExchg(inputStream, outputStream, s, n);
                    break;
                }
                case -1: {
                    try {
                        this.v4ProtExchg(inputStream, outputStream, s, n);
                        this.socks_version = 4;
                    }
                    catch (SocksException ex) {
                        Log.write(64, "Socks: V4 request failed: " + ex.getMessage());
                        socket.close();
                        socket = connect(this.socks_host, this.socks_port, inetAddress, n2);
                        this.v5ProtExchg(socket.getInputStream(), socket.getOutputStream(), s, n);
                        this.socks_version = 5;
                    }
                    break;
                }
                default: {
                    throw new Error("SocksClient internal error: unknown version " + this.socks_version);
                }
            }
            Log.write(64, "Socks: connection established.");
            return socket;
        }
        catch (IOException ex2) {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException ex3) {}
            }
            throw ex2;
        }
    }
    
    private static final Socket connect(final String host, final int n, final InetAddress localAddr, final int localPort) throws IOException {
        final InetAddress[] allByName = InetAddress.getAllByName(host);
        int i = 0;
        while (i < allByName.length) {
            try {
                if (localAddr == null) {
                    return new Socket(allByName[i], n);
                }
                return new Socket(allByName[i], n, localAddr, localPort);
            }
            catch (SocketException ex) {
                if (i < allByName.length - 1) {
                    ++i;
                    continue;
                }
                throw ex;
            }
            break;
        }
        return null;
    }
    
    private void v4ProtExchg(final InputStream inputStream, final OutputStream out, final String s, final int i) throws SocksException, IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        Log.write(64, "Socks: Beginning V4 Protocol Exchange for host " + s + ":" + i);
        byte[] address = { 0, 0, 0, 42 };
        if (!this.v4A) {
            try {
                address = InetAddress.getByName(s).getAddress();
            }
            catch (UnknownHostException ex) {
                this.v4A = true;
            }
            catch (SecurityException ex2) {
                this.v4A = true;
            }
            if (this.v4A) {
                Log.write(64, "Socks: Switching to version 4A");
            }
        }
        if (this.user == null) {
            String property;
            try {
                property = System.getProperty("user.name", "");
            }
            catch (SecurityException ex3) {
                property = "";
            }
            final byte[] bytes = property.getBytes();
            System.arraycopy(bytes, 0, this.user = new byte[bytes.length + 1], 0, bytes.length);
            this.user[property.length()] = 0;
        }
        Log.write(64, "Socks: Sending connect request for user " + new String(this.user, 0, this.user.length - 1));
        byteArrayOutputStream.reset();
        byteArrayOutputStream.write(4);
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(i >> 8 & 0xFF);
        byteArrayOutputStream.write(i & 0xFF);
        byteArrayOutputStream.write(address);
        byteArrayOutputStream.write(this.user);
        if (this.v4A) {
            byteArrayOutputStream.write(s.getBytes("8859_1"));
            byteArrayOutputStream.write(0);
        }
        byteArrayOutputStream.writeTo(out);
        final int read = inputStream.read();
        if (read == -1) {
            throw new SocksException("Connection refused by server");
        }
        if (read == 4) {
            Log.write(64, "Socks: Warning: received version 4 instead of 0");
        }
        else if (read != 0) {
            throw new SocksException("Received invalid version: " + read + "; expected: 0");
        }
        final int read2 = inputStream.read();
        Log.write(64, "Socks: Received response; version: " + read + "; status: " + read2);
        switch (read2) {
            case 90: {
                final byte[] b = new byte[6];
                int read3;
                for (int n = 0; n < b.length && (read3 = inputStream.read(b, 0, b.length - n)) != -1; n += read3) {}
            }
            case 91: {
                throw new SocksException("Connection request rejected");
            }
            case 92: {
                throw new SocksException("Connection request rejected: can't connect to identd");
            }
            case 93: {
                throw new SocksException("Connection request rejected: identd reports different user-id from " + new String(this.user, 0, this.user.length - 1));
            }
            default: {
                throw new SocksException("Connection request rejected: unknown error " + read2);
            }
        }
    }
    
    private void v5ProtExchg(final InputStream inputStream, final OutputStream outputStream, final String str, final int i) throws SocksException, IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        Log.write(64, "Socks: Beginning V5 Protocol Exchange for host " + str + ":" + i);
        Log.write(64, "Socks: Sending authentication request; methods No-Authentication, Username/Password");
        byteArrayOutputStream.reset();
        byteArrayOutputStream.write(5);
        byteArrayOutputStream.write(2);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(2);
        byteArrayOutputStream.writeTo(outputStream);
        final int read = inputStream.read();
        if (read == -1) {
            throw new SocksException("Connection refused by server");
        }
        if (read != 5) {
            throw new SocksException("Received invalid version: " + read + "; expected: 5");
        }
        final int read2 = inputStream.read();
        Log.write(64, "Socks: Received response; version: " + read + "; method: " + read2);
        switch (read2) {
            case 0: {
                break;
            }
            case 1: {
                this.negotiate_gssapi(inputStream, outputStream);
                break;
            }
            case 2: {
                this.negotiate_userpwd(inputStream, outputStream);
                break;
            }
            case -1: {
                throw new SocksException("Server unwilling to accept any standard authentication methods");
            }
            default: {
                throw new SocksException("Cannot handle authentication method " + read2);
            }
        }
        Log.write(64, "Socks: Sending connect request");
        byteArrayOutputStream.reset();
        byteArrayOutputStream.write(5);
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(str.length() & 0xFF);
        byteArrayOutputStream.write(str.getBytes("8859_1"));
        byteArrayOutputStream.write(i >> 8 & 0xFF);
        byteArrayOutputStream.write(i & 0xFF);
        byteArrayOutputStream.writeTo(outputStream);
        final int read3 = inputStream.read();
        if (read3 != 5) {
            throw new SocksException("Received invalid version: " + read3 + "; expected: 5");
        }
        final int read4 = inputStream.read();
        Log.write(64, "Socks: Received response; version: " + read3 + "; status: " + read4);
        switch (read4) {
            case 0: {
                inputStream.read();
                final int read5 = inputStream.read();
                int read6 = 0;
                switch (read5) {
                    case 4: {
                        read6 = 16;
                        break;
                    }
                    case 1: {
                        read6 = 4;
                        break;
                    }
                    case 3: {
                        read6 = inputStream.read();
                        break;
                    }
                    default: {
                        throw new SocksException("Invalid address type received from server: " + read5);
                    }
                }
                final byte[] b = new byte[read6 + 2];
                int read7;
                for (int n = 0; n < b.length && (read7 = inputStream.read(b, 0, b.length - n)) != -1; n += read7) {}
            }
            case 1: {
                throw new SocksException("General SOCKS server failure");
            }
            case 2: {
                throw new SocksException("Connection not allowed");
            }
            case 3: {
                throw new SocksException("Network unreachable");
            }
            case 4: {
                throw new SocksException("Host unreachable");
            }
            case 5: {
                throw new SocksException("Connection refused");
            }
            case 6: {
                throw new SocksException("TTL expired");
            }
            case 7: {
                throw new SocksException("Command not supported");
            }
            case 8: {
                throw new SocksException("Address type not supported");
            }
            default: {
                throw new SocksException("Unknown reply received from server: " + read4);
            }
        }
    }
    
    private void negotiate_gssapi(final InputStream inputStream, final OutputStream outputStream) throws SocksException, IOException {
        throw new SocksException("GSSAPI authentication protocol not implemented");
    }
    
    private void negotiate_userpwd(final InputStream inputStream, final OutputStream outputStream) throws SocksException, IOException {
        Log.write(64, "Socks: Entering authorization subnegotiation; method: Username/Password");
        AuthorizationInfo authorization;
        try {
            authorization = AuthorizationInfo.getAuthorization(this.socks_host, this.socks_port, "SOCKS5", "USER/PASS", null, null, true);
        }
        catch (AuthSchemeNotImplException ex) {
            authorization = null;
        }
        if (authorization == null) {
            throw new SocksException("No Authorization info for SOCKS found (server requested username/password).");
        }
        final NVPair[] params = authorization.getParams();
        if (params == null || params.length == 0) {
            throw new SocksException("No Username/Password found in authorization info for SOCKS.");
        }
        final String name = params[0].getName();
        final String value = params[0].getValue();
        Log.write(64, "Socks: Sending authorization request for user " + name);
        final byte[] bytes = name.getBytes();
        final byte[] bytes2 = value.getBytes();
        final byte[] b = new byte[2 + bytes.length + 1 + bytes2.length];
        b[0] = 1;
        b[1] = (byte)bytes.length;
        System.arraycopy(bytes, 0, b, 2, bytes.length);
        b[2 + b[1]] = (byte)bytes2.length;
        System.arraycopy(bytes2, 0, b, 2 + b[1] + 1, bytes2.length);
        outputStream.write(b);
        final int read = inputStream.read();
        if (read != 1) {
            throw new SocksException("Wrong version received in username/password subnegotiation response: " + read + "; expected: 1");
        }
        final int read2 = inputStream.read();
        if (read2 != 0) {
            throw new SocksException("Username/Password authentication failed; status: " + read2);
        }
        Log.write(64, "Socks: Received response; version: " + read + "; status: " + read2);
    }
    
    public String toString() {
        return this.getClass().getName() + "[" + this.socks_host + ":" + this.socks_port + "]";
    }
}
