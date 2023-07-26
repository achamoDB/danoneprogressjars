import java.security.NoSuchAlgorithmException;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ee extends psc_d8
{
    private static final int a = 0;
    private static final int b = 1;
    private static final int c = 2;
    private byte[] d;
    
    public psc_ee(final String s) throws psc_d7 {
        super(0, s);
        this.d = null;
    }
    
    public psc_ee(final String s, long n) throws psc_d7 {
        super(0, s);
        this.d = null;
        this.d = new byte[8];
        for (int i = 0; i < 8; ++i) {
            this.d[7 - i] = (byte)(0xFFL & n);
            n >>= 8;
        }
    }
    
    public psc_ee(final String s, final byte[] d) throws psc_d7 {
        super(0, s);
        this.d = null;
        this.d = d;
    }
    
    public psc_ea a(final psc_ah psc_ah) throws psc_d9 {
        try {
            return new om(psc_ah, this.b());
        }
        catch (psc_d7 psc_d7) {
            throw new psc_d9("DefaultRandom.instantiate: " + psc_d7.getMessage());
        }
    }
    
    private final class om extends psc_ea implements psc_iz
    {
        private psc_av a;
        private int b;
        
        private om(final psc_ah psc_ah, final String s) throws psc_d7 {
            super(psc_ah, s);
            this.a();
            if (psc_ee.this.d != null) {
                this.b = 0;
                this.a.g(psc_ee.this.d);
            }
            this.e();
        }
        
        private void a() {
            try {
                this.a = (psc_av)psc_av.getInstance("HWRandom", "Intel");
                this.b = 1;
            }
            catch (Exception ex) {
                try {
                    this.a = (psc_av)psc_av.getInstance("SHA1Random", super.b.f());
                    this.b = 2;
                }
                catch (NoSuchAlgorithmException ex2) {}
            }
        }
        
        public psc_av d() {
            return this.a;
        }
        
        public void e() {
            switch (this.b) {
                case 1: {
                    this.a.r();
                    break;
                }
                case 2: {
                    this.a.r();
                    break;
                }
            }
        }
        
        public String toString() {
            return "Default Random provider named: " + super.b();
        }
    }
}
