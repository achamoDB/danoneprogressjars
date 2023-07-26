import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kl extends psc_ki implements psc_dl, Cloneable, Serializable
{
    public psc_kl() {
        super.c = 14;
    }
    
    public psc_kl(final int[] array) throws psc_be {
        super(array);
        super.c = 14;
    }
    
    public String d() {
        return "AES256";
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kl psc_kl = new psc_kl();
        this.a(psc_kl);
        return psc_kl;
    }
}
