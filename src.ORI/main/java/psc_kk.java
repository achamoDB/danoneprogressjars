import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kk extends psc_ki implements psc_dl, Cloneable, Serializable
{
    public psc_kk() {
        super.c = 12;
    }
    
    public psc_kk(final int[] array) throws psc_be {
        super(array);
        super.c = 12;
    }
    
    public String d() {
        return "AES192";
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kk psc_kk = new psc_kk();
        this.a(psc_kk);
        return psc_kk;
    }
}
