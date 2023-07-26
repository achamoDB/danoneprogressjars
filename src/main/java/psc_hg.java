import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hg extends psc_g2
{
    psc_g3 a;
    boolean b;
    psc_c2 c;
    private psc_d2 d;
    
    public psc_hg(final InputStream inputStream, final psc_g3 a, final psc_da psc_da, final psc_da psc_da2, final Socket socket, final psc_d2 d, final boolean b, final psc_c2 c, final psc_dw psc_dw, final psc_c5 psc_c5) throws IOException {
        super(socket, inputStream, psc_da, psc_da2);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        super.a(psc_dw);
        super.a(psc_c5);
    }
    
    public int a() throws IOException, InterruptedIOException {
        int n;
        try {
            n = super.a();
        }
        catch (psc_b psc_b) {
            if (psc_b.b() == 0) {
                try {
                    this.a.close();
                }
                catch (SocketException ex) {
                    this.a.e().close();
                    if ((this.c.v() & 0x4) == 0x4) {
                        this.c.z().println("Socket all ready closed. Unable to send CloseNotify to server.");
                    }
                }
                super.in.close();
                super.a.close();
                return -1;
            }
            throw psc_b;
        }
        if (n == -1) {
            return -1;
        }
        switch (super.i) {
            case 22: {
                if (!this.c.o()) {
                    this.a.a(21);
                    this.a.write(1);
                    this.a.write(100);
                    this.a.flush();
                    this.a.a(23);
                }
                try {
                    if (this.b) {
                        try {
                            final psc_da a = psc_da.a("MD5", "Java");
                            a.i();
                            final psc_da a2 = psc_da.a("SHA1", "Java");
                            a2.i();
                            final psc_g2 psc_g2 = new psc_g2(super.a, super.in, a, a2);
                            psc_g2.a(this.c.v(), this.c.z());
                            psc_g2.a(this.h());
                            psc_g2.a(this.i());
                            final psc_g3 psc_g3 = new psc_g3(this.a.e(), a, a2);
                            psc_g3.a(this.c.v(), this.c.z());
                            psc_g3.a(this.h());
                            psc_g3.a(this.i());
                            psc_g3.a(22);
                            if ((this.c.v() & 0x1) == 0x1) {
                                this.c.z().println("STATE: Receiving the Client Hello");
                            }
                            final psc_g0 psc_g4 = new psc_g0();
                            psc_g4.a(this);
                            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            psc_g4.a(byteArrayOutputStream);
                            final byte[] byteArray = byteArrayOutputStream.toByteArray();
                            a.a(byteArray, 0, byteArray.length);
                            a2.a(byteArray, 0, byteArray.length);
                            if ((this.c.v() & 0x1) == 0x1) {
                                this.c.z().println(psc_g4.toString());
                            }
                            final psc_g4 psc_g5 = new psc_g4(this.c, psc_g4, psc_g2, psc_g3, super.a.getInetAddress().getHostAddress());
                            this.a(psc_g2.h());
                            ((psc_g3)super.a.getOutputStream()).a(psc_g3.c());
                            n = this.a();
                            break;
                        }
                        catch (psc_ap psc_ap) {
                            throw new IOException(psc_ap.getMessage());
                        }
                    }
                    if (this.read() == 0) {
                        psc_gz.b(this.c, this, this.a, this.d, super.a.getInetAddress().getHostAddress());
                    }
                }
                catch (psc_d psc_d) {
                    throw new IOException(psc_d.getMessage());
                }
            }
            case 23: {
                break;
            }
            case 21: {
                return -1;
            }
            default: {
                throw new IOException("Unexpected message.  Got a handshake when not allowed");
            }
        }
        return n;
    }
}
