import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_e2 extends psc_ev implements Cloneable, Serializable
{
    public psc_e2(final byte[] array) {
        super(array);
    }
    
    protected psc_e2() {
    }
    
    public String getAlgorithm() {
        return "JA_FIPS186DSASigGenPRNG";
    }
    
    public psc_ew k() {
        return new psc_i0();
    }
    
    protected psc_ev l() {
        return new psc_e2();
    }
}
