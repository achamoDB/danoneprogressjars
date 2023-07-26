// 
// Decompiled by Procyon v0.5.36
// 

public class psc_k7
{
    public static byte[][] a(final psc_am psc_am, final String anotherString) {
        if (psc_am == null || anotherString == null) {
            return null;
        }
        try {
            final String[] o = psc_am.o();
            for (int i = 0; i < o.length; ++i) {
                if (o[i].compareTo(anotherString) == 0) {
                    return psc_am.b(anotherString);
                }
            }
        }
        catch (psc_ap psc_ap) {
            return null;
        }
        return null;
    }
}
