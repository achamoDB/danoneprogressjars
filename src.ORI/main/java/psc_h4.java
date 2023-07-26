import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h4
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 7;
    public static final int i = 8;
    private int j;
    
    public psc_h4() {
    }
    
    public psc_h4(final int j) {
        this.j = j;
    }
    
    public void b(final int j) {
        this.j = j;
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
    }
    
    public void b(final psc_h6 psc_h6) throws IOException, psc_g8 {
    }
    
    public String toString() {
        return (this.j == 1) ? "CLIENT_HELLO" : ((this.j == 2) ? "CLIENT_MASTER_KEY" : ((this.j == 3) ? "CLIENT_FINISHED" : ((this.j == 4) ? "SERVER_HELLO" : ((this.j == 5) ? "SERVER_VERIFY" : ((this.j == 6) ? "SERVER_FINISHED" : ((this.j == 7) ? "REQUEST_CERTIFICATE" : ((this.j == 8) ? "CLIENT_CERTIFICATE" : ("UNKNOWN (" + this.j + ")"))))))));
    }
}
