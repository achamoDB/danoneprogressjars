import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ap
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 11;
    public static final int e = 12;
    public static final int f = 13;
    public static final int g = 14;
    public static final int h = 15;
    public static final int i = 16;
    public static final int j = 20;
    public static final int k = 255;
    private int l;
    
    public pscl_ap() {
    }
    
    public pscl_ap(final int l) {
        this.l = l;
    }
    
    public void a(final int l) {
        this.l = l;
    }
    
    public void a(final pscl_as pscl_as) throws IOException {
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException {
    }
    
    public String toString() {
        return (this.l == 0) ? "HELLO_REQUEST" : ((this.l == 1) ? "CLIENT_HELLO" : ((this.l == 2) ? "SERVER_HELLO" : ((this.l == 11) ? "CERTIFICATE" : ((this.l == 12) ? "SERVER_KEY_EXCHANGE" : ((this.l == 13) ? "CERTIFICATE_REQUEST" : ((this.l == 14) ? "SERVER_HELLO_DONE" : ((this.l == 15) ? "CERTIFICATE_VERIFY" : ((this.l == 16) ? "CLIENT_KEY_EXCHANGE" : ((this.l == 20) ? "FINISHED" : ("UNKNOWN (" + this.l + ")"))))))))));
    }
}
