import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_f implements pscl_g
{
    public int a(final pscl_b pscl_b, final pscl_j[] array) throws pscl_ai, pscl_h {
        final Date date = new Date();
        final pscl_j[] c = pscl_b.c();
        int n = 0;
        try {
            if (!array[0].a(date)) {
                throw new pscl_ai("certificate expired", 1, 45);
            }
            int i;
            for (i = 0; i < array.length; ++i) {
                for (n = 0; n < c.length && !pscl_k.a(c[n].c(), array[i].b()); ++n) {}
                if (n < c.length) {
                    break;
                }
            }
            if (n >= c.length || i >= array.length) {
                return -1;
            }
            if (!array[i].a(new pscl_ax(c[n].f(), 0))) {
                throw new pscl_ai("Bad certificate", 1, 42);
            }
            return n;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (Exception ex) {
            throw new pscl_h("can't verify certificate");
        }
    }
}
