import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b extends IOException
{
    public static final int a = 1;
    public static final int b = 2;
    private int c;
    public static final int d = 1;
    public static final int e = 2;
    public static final int f = 3;
    public static final int g = 4;
    public static final int h = 0;
    public static final int i = 10;
    public static final int j = 20;
    public static final int k = 21;
    public static final int l = 22;
    public static final int m = 30;
    public static final int n = 40;
    public static final int o = 41;
    public static final int p = 42;
    public static final int q = 43;
    public static final int r = 44;
    public static final int s = 45;
    public static final int t = 46;
    public static final int u = 47;
    public static final int v = 48;
    public static final int w = 49;
    public static final int x = 50;
    public static final int y = 51;
    public static final int z = 60;
    public static final int aa = 70;
    public static final int ab = 71;
    public static final int ac = 80;
    public static final int ad = 90;
    public static final int ae = 100;
    private int af;
    
    public psc_b(final String message, final int c, final int af) {
        super(message);
        this.c = c;
        this.af = af;
    }
    
    public int a() {
        return this.c;
    }
    
    public int b() {
        return this.af;
    }
    
    public static String a(final int n) {
        switch (n) {
            case 1: {
                return "No Cipher Error";
            }
            case 2: {
                return "No Certificate Error";
            }
            case 3: {
                return "Bad Certificate Error";
            }
            case 4: {
                return "Unsupported Certificate Type Error";
            }
            case 0: {
                return "Close Notify";
            }
            case 10: {
                return "Unexpected Message";
            }
            case 20: {
                return "Bad Record Mac";
            }
            case 30: {
                return "Decompression Failure";
            }
            case 21: {
                return "Decryption Failed";
            }
            case 40: {
                return "Handshake Failure.  No supported cipher suites";
            }
            case 41: {
                return "No Certificate";
            }
            case 42: {
                return "Bad Certificate";
            }
            case 43: {
                return "Unsupported Certificate";
            }
            case 44: {
                return "Certificate Revoked";
            }
            case 45: {
                return "Certificate Expired";
            }
            case 46: {
                return "Certificate Unknown";
            }
            case 47: {
                return "Illegal Parameter";
            }
            default: {
                return "";
            }
        }
    }
}
