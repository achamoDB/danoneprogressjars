import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kj extends psc_ki implements psc_dl, Cloneable, Serializable
{
    public psc_kj() {
        super.c = 10;
    }
    
    public psc_kj(final int[] array) throws psc_be {
        super(array);
        super.c = 10;
    }
    
    public String d() {
        return "AES128";
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kj psc_kj = new psc_kj();
        this.a(psc_kj);
        return psc_kj;
    }
}
