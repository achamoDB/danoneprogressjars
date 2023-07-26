// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_no extends psc_an
{
    public static byte[] a(final psc_az psc_az) throws psc_ao {
        try {
            return psc_q.a("HMAC/" + psc_az.e(), 14, null, 0, 0);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("DER for HMAC unknown(" + psc_m.getMessage() + ")");
        }
    }
}
