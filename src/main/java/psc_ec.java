// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ec extends psc_eb
{
    public psc_ec(final psc_ah psc_ah) {
        super(psc_ah);
    }
    
    public psc_oc a(final byte[] array, final psc_ob psc_ob) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (array == null) {
            throw new psc_d7("PKIService.readCertificationResponseMessage: response cannot be null.");
        }
        try {
            return a.a(array, psc_ob);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.readCertificationResponseMessage: Does not support this service.");
        }
    }
    
    public byte[] a(final psc_oe psc_oe, final psc_ob psc_ob) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (psc_oe == null) {
            throw new psc_d7("PKIService.writeCertificationRequestMessage: request cannot be null.");
        }
        try {
            return a.a(psc_oe, psc_ob);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.writeCertificationRequestMessage: Does not support this service.");
        }
    }
    
    public psc_oc a(final psc_oe psc_oe, final psc_ob psc_ob, final psc_ed psc_ed) throws psc_d7, psc_mv, psc_n8 {
        return this.b(psc_oe, psc_ob, psc_ed);
    }
    
    public psc_oc b(final psc_oe psc_oe, final psc_ob psc_ob, final psc_ed psc_ed) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (psc_oe == null) {
            throw new psc_d7("PKIService.sendRequest: request cannot be null.");
        }
        try {
            return a.b(psc_oe, psc_ob, psc_ed);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.sendRequest: does not support this service.");
        }
    }
    
    public psc_of a(final byte[] array) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (array == null) {
            throw new psc_d7("PKIService.sendMessage: request cannot be null.");
        }
        try {
            return a.a(array);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.sendMessage: Does not support this service.");
        }
    }
    
    public void a(final psc_oe psc_oe, final psc_dt psc_dt, final psc_og psc_og) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (psc_oe == null) {
            throw new psc_d7("PKIService.generateProofOfPossession: request cannot be null.");
        }
        try {
            a.a(psc_oe, psc_dt, psc_og);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.generateProofOfPossession: Does not support this service.");
        }
    }
    
    public boolean a(final psc_od psc_od, final psc_oh psc_oh) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (psc_od == null) {
            throw new psc_d7("PKIService.validateProofOfPossession: message cannot be null.");
        }
        try {
            return a.a(psc_od, psc_oh);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.validateProofOfPossession: Does not support this service.");
        }
    }
    
    public void a(final psc_oe psc_oe, final int n, final byte[] array) throws psc_d7, psc_mv, psc_n8 {
        final psc_nj a = this.a();
        if (psc_oe == null) {
            throw new psc_d7("PKIService.provideProofOfPossession: request cannot be null.");
        }
        try {
            a.a(psc_oe, n, array);
        }
        catch (psc_n4 psc_n4) {
            throw new psc_mv("PKIService.provideProofOfPossession: Does not support this service.");
        }
    }
    
    private psc_nj a() throws psc_mv {
        if (this.m() == 0) {
            throw new psc_mv("PKIService.getProvider: no PKI provider bound to this object.");
        }
        return (psc_nj)this.a(0);
    }
}
