// 
// Decompiled by Procyon v0.5.36
// 

final class psc_nm extends psc_an
{
    static byte[] a(final psc_i1 psc_i1, final psc_iv psc_iv) throws psc_ao {
        final String d = psc_iv.d();
        String string;
        if (psc_i1.e().compareTo(d) == 0) {
            string = "RSA";
        }
        else {
            string = "RSA/" + d;
        }
        final byte[] k = psc_iv.k();
        int length = 0;
        if (k != null) {
            length = k.length;
        }
        try {
            return psc_q.a(string, 2, k, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("DER for " + string + " unknown(" + psc_m.getMessage() + ")");
        }
    }
    
    static byte[] a(final psc_i1 psc_i1, final psc_az psc_az, final psc_et psc_et, final String str, final boolean b) throws psc_ao {
        String str2;
        if (str == null) {
            if (psc_et.d().compareTo("PKCS1V2PSS") == 0) {
                str2 = "RSA/" + psc_et.d();
            }
            else {
                str2 = psc_az.e() + "/RSA/" + psc_et.d();
            }
        }
        else {
            if (psc_az.e().compareTo("SHA1") != 0) {
                throw new psc_ao("DER for " + str + " with " + psc_az.e() + " unknown");
            }
            str2 = str;
        }
        final byte[] k = psc_et.k();
        int length = 0;
        if (k != null) {
            length = k.length;
        }
        try {
            return psc_q.a(str2, 1, k, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("DER for " + str2 + " unknown(" + psc_m.getMessage() + ")");
        }
    }
}
