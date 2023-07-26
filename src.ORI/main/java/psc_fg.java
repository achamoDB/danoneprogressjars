// 
// Decompiled by Procyon v0.5.36
// 

class psc_fg extends psc_e3
{
    private int a;
    private static psc_fg b;
    
    private psc_fg() {
    }
    
    public static psc_e3 a() {
        return psc_fg.b = new psc_fg();
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        if (array.length != 1) {
            if (array.length == 4) {
                this.a = 3;
            }
            Label_0420: {
                switch (n) {
                    case 0: {
                        if (this.a != 3) {
                            this.a = 1;
                            final Object a = this.a(array[n]);
                            if (a != null) {
                                return a;
                            }
                            this.a = 2;
                        }
                        if (array[n].startsWith("SHA1", 0)) {
                            return new psc_ew();
                        }
                        if (array[n].startsWith("MD5", 0)) {
                            return new psc_ef();
                        }
                        if (array[n].startsWith("MD2", 0)) {
                            return new psc_jm();
                        }
                        if (array[n].startsWith("SHA224", 0)) {
                            return new psc_je();
                        }
                        if (array[n].startsWith("SHA256", 0)) {
                            return new psc_jf();
                        }
                        if (array[n].startsWith("SHA384", 0)) {
                            return new psc_jg();
                        }
                        if (array[n].startsWith("SHA512", 0)) {
                            return new psc_jh();
                        }
                        if (array[n].startsWith("RIPEMD160", 0)) {
                            return new psc_jn();
                        }
                        return null;
                    }
                    case 1: {
                        if (this.a == 1) {
                            return this.b(array[n]);
                        }
                        if (this.a == 3) {
                            return this.a(array[n]);
                        }
                        if (array[n].startsWith("RC4", 0)) {
                            return new psc_kh();
                        }
                        return null;
                    }
                    case 2: {
                        if (this.a == 1) {
                            if (array[n].startsWith("PKCS5Padding", 0)) {
                                return new psc_dq();
                            }
                            if (array[n].startsWith("NoPad", 0)) {
                                return new psc_i6();
                            }
                            if (array[n].startsWith("ISO10126Padding", 0)) {
                                return new psc_kz();
                            }
                            return null;
                        }
                        else {
                            if (this.a == 3) {
                                return this.b(array[n]);
                            }
                            break Label_0420;
                        }
                        break;
                    }
                    case 3: {
                        if (array[n].startsWith("PKCS5PBE", 0)) {
                            return new psc_k0();
                        }
                        if (array[n].startsWith("PKCS5V2PBE", 0)) {
                            return new psc_k1();
                        }
                        if (array[n].startsWith("PKCS12V1PBE", 0)) {
                            return new psc_ji();
                        }
                        if (array[n].startsWith("PKCS12PBE", 0)) {
                            return new psc_jk();
                        }
                        if (array[n].startsWith("SSLCPBE", 0)) {
                            return new psc_k2();
                        }
                        break;
                    }
                }
            }
            return null;
        }
        if (array[n].startsWith("RC4", 0)) {
            return new psc_kh();
        }
        return null;
    }
    
    private Object a(final String s) {
        if (s.startsWith("AES")) {
            if (s.length() == 3) {
                return new psc_ki();
            }
            if (s.endsWith("128")) {
                return new psc_kj();
            }
            if (s.endsWith("192")) {
                return new psc_kk();
            }
            if (s.endsWith("256")) {
                return new psc_kl();
            }
        }
        if (s.compareTo("DES") == 0) {
            return new psc_km();
        }
        if (s.startsWith("3DES_EDE", 0)) {
            return new psc_kn();
        }
        if (s.startsWith("RC2", 0)) {
            return new psc_ko();
        }
        if (s.startsWith("RC5", 0)) {
            return new psc_kp();
        }
        if (s.startsWith("DESX", 0)) {
            return new psc_kt();
        }
        return null;
    }
    
    private Object b(final String s) {
        if (s.startsWith("CBC", 0)) {
            return new psc_ku();
        }
        if (s.startsWith("ECB", 0)) {
            return new psc_kv();
        }
        if (s.startsWith("CFB", 0)) {
            return new psc_kw();
        }
        if (s.startsWith("OFB", 0)) {
            return new psc_ky();
        }
        return null;
    }
    
    static {
        psc_fg.b = null;
    }
}
