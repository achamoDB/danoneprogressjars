import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Socket;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_dy extends Socket
{
    public static final int a = 443;
    public static final int b = 465;
    public static final int c = 563;
    private psc_c2 d;
    public Socket e;
    private final boolean f;
    private psc_d0 g;
    private psc_d1 h;
    
    public psc_dy(final String host, final int port, final psc_c2 d) throws IOException, UnknownHostException {
        super(host, port);
        this.d = d;
        this.g = new psc_d3(d);
        this.e = this;
        this.f = false;
    }
    
    public psc_dy(final InetAddress address, final int port, final psc_c2 d) throws IOException, psc_d {
        super(address, port);
        this.d = d;
        this.g = new psc_d3(d);
        this.e = this;
        this.f = false;
    }
    
    public psc_dy(final Socket e, final psc_c2 d) throws psc_d {
        this.d = d;
        this.e = e;
        this.f = true;
        this.g = new psc_d3(d);
    }
    
    protected psc_dy(final psc_c2 d) {
        this.d = d;
        this.g = new psc_dz(d);
        this.e = this;
        this.f = false;
    }
    
    public psc_dy(final Socket e, final psc_c2 d, final boolean b) throws psc_d {
        this.d = d;
        this.e = e;
        this.f = true;
        if (!b) {
            this.g = new psc_d3(d);
        }
        else {
            this.g = new psc_dz(d);
        }
    }
    
    public synchronized InputStream getInputStream() throws IOException {
        if (this.g.c() == null) {
            if (this.f) {
                this.h = this.g.a(this.e.getInputStream(), this.e.getOutputStream(), this.e);
            }
            else {
                this.h = this.g.a(super.getInputStream(), super.getOutputStream(), this);
            }
        }
        return this.g.c();
    }
    
    public synchronized OutputStream getOutputStream() throws IOException {
        if (this.g.d() == null) {
            if (this.f) {
                this.h = this.g.a(this.e.getInputStream(), this.e.getOutputStream(), this.e);
            }
            else {
                this.h = this.g.a(super.getInputStream(), super.getOutputStream(), this);
            }
        }
        return this.g.d();
    }
    
    public final psc_dw a() {
        if (this.h == null) {
            return null;
        }
        return this.h.a();
    }
    
    public final psc_d2 b() {
        if (this.h == null) {
            return null;
        }
        return this.h.b();
    }
    
    public final psc_c5 c() {
        if (this.h == null) {
            return null;
        }
        return this.h.c();
    }
    
    public psc_e[] d() {
        if (this.h == null) {
            return null;
        }
        return this.h.d();
    }
    
    public void a(final psc_c2 psc_c2) throws psc_d, psc_b {
        this.h = this.g.b(psc_c2, this.h.e(), this.g.c(), this.g.d(), null, this.e.getInetAddress().getHostAddress());
    }
    
    public void close() throws IOException {
        Label_0077: {
            try {
                this.g.d().flush();
                this.g.d().close();
                break Label_0077;
            }
            catch (Exception ex) {
                break Label_0077;
            }
            finally {
                Label_0134: {
                    try {
                        Label_0129: {
                            if (this.f) {
                                this.e.close();
                                break Label_0129;
                            }
                            super.close();
                            break Label_0129;
                        }
                        break Label_0134;
                    }
                    catch (Exception ex2) {}
                }
                while (true) {}
                return;
                try {
                    if (this.f) {
                        this.e.close();
                    }
                    else {
                        super.close();
                    }
                }
                catch (Exception ex3) {}
                return;
                try {
                    if (this.f) {
                        this.e.close();
                    }
                    else {
                        super.close();
                    }
                }
                catch (Exception ex4) {}
            }
        }
    }
    
    public void shutdownInput() throws IOException {
    }
    
    public void shutdownOutput() throws IOException {
    }
}
