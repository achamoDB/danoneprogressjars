import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_jj extends psc_an implements psc_dn, Cloneable, Serializable
{
    public byte[] a(final psc_az psc_az, final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao {
        final byte[] f = this.f();
        int length = 0;
        if (f != null) {
            length = f.length;
        }
        final String string = "PBE/" + psc_az.e() + "/" + psc_dg.d() + "/" + psc_di.f() + "/" + this.e();
        try {
            return psc_q.a(string, 9, f, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode given transformation.(" + psc_m.getMessage() + ")");
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public void y() {
        super.y();
    }
}
