// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_my
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 0;
    public static final int e = 1;
    public static final int f = 2;
    private int g;
    private int h;
    private Object i;
    
    public psc_my() {
        this.g = 2;
        this.h = 0;
        this.i = null;
    }
    
    public psc_my(final int g, final int h, final Object i) {
        this.g = g;
        this.h = h;
        this.i = i;
    }
    
    public String toString() {
        String str = null;
        switch (this.g) {
            case 0: {
                str = "CERT_NOT_REVOKED";
                break;
            }
            case 1: {
                str = "CERT_REVOKED";
                break;
            }
            case 2: {
                str = "CERT_REVOCATION_UNKNOWN";
                break;
            }
            default: {
                str = "???";
                break;
            }
        }
        String str2 = null;
        String str3 = null;
        switch (this.h) {
            case 0: {
                str2 = "CRE_NONE";
                str3 = "null";
                break;
            }
            case 1: {
                str2 = "CRE_CRL";
                str3 = ((psc_n9)this.i).toString();
                break;
            }
            case 2: {
                str2 = "CRE_OCSP";
                str3 = ((psc_np)this.i).toString();
                break;
            }
            default: {
                str2 = "???";
                str3 = "???";
                break;
            }
        }
        return "{status=" + str + ",evidenceType=" + str2 + ",evidence=" + str3 + "}";
    }
    
    public void a(final int g) {
        this.g = g;
    }
    
    public void b(final int h) {
        this.h = h;
    }
    
    public void a(final Object i) {
        this.i = i;
    }
    
    public int a() {
        return this.g;
    }
    
    public int b() {
        return this.h;
    }
    
    public Object c() {
        return this.i;
    }
}
