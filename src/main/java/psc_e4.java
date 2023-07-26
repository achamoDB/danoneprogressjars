// 
// Decompiled by Procyon v0.5.36
// 

class psc_e4 extends psc_e3
{
    private static psc_e4 a;
    
    private psc_e4() {
    }
    
    public static psc_e3 a() {
        if (psc_e4.a == null) {
            psc_e4.a = new psc_e4();
        }
        return psc_e4.a;
    }
    
    public Object a(final String[] array, final int n, final String s, final String[] array2) {
        switch (n) {
            case 0: {
                if (array[n].startsWith("RSA", 0)) {
                    return new psc_i1();
                }
            }
            case 1: {
                if (array[n].startsWith("PKCS1Block02Pad", 0)) {
                    return new psc_i3();
                }
                if (array[n].startsWith("PKCS1V2OAEPPad", 0)) {
                    return new psc_i4();
                }
                if (array[n].startsWith("X931Pad", 0)) {
                    return new psc_i5();
                }
                if (array[n].startsWith("NoPad", 0)) {
                    return new psc_i6();
                }
                return null;
            }
            case 2: {
                if (array[n].startsWith("SHA1", 0)) {
                    return new psc_ew();
                }
                return null;
            }
            case 3: {
                if (array[n].startsWith("MGF1", 0)) {
                    return new psc_i7();
                }
                return null;
            }
            case 4: {
                if (array[n].startsWith("SHA1", 0)) {
                    return new psc_ew();
                }
                return null;
            }
            case 5: {
                if (array[n].startsWith("SpecifiedParams", 0)) {
                    return new psc_i8();
                }
                break;
            }
        }
        return null;
    }
    
    static {
        psc_e4.a = null;
    }
}
