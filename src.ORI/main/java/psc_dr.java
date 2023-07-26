import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

abstract class psc_dr extends psc_an implements Cloneable, Serializable
{
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public boolean l() {
        return false;
    }
}
