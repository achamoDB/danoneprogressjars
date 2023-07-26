import java.util.Date;
import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ed extends psc_eb
{
    public psc_ed(final psc_ah psc_ah) {
        super(psc_ah);
    }
    
    public void a(final psc_f psc_f) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_f);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.insertCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void a(final psc_f[] array) throws psc_mv, psc_nk {
        if (array == null) {
            throw new psc_nk("DatabaseService.insertCertificates: certs should not be null.");
        }
        for (int i = 0; i < array.length; ++i) {
            this.a(array[i]);
        }
    }
    
    public void a(final psc_na psc_na) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_na);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.insertCRL: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void a(final psc_na[] array) throws psc_mv, psc_nk {
        if (array == null) {
            throw new psc_nk("DatabaseService.insertCRLs: crls should not be null.");
        }
        for (int i = 0; i < array.length; ++i) {
            this.a(array[i]);
        }
    }
    
    public void a(final psc_f psc_f, final psc_dt psc_dt) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_f, psc_dt);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.insertPrivateKeyByCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void a(final psc_al psc_al, final psc_dt psc_dt) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_al, psc_dt);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.insertPrivateKeyByPublicKey: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public int a(final psc_u psc_u, final byte[] array, final Vector vector) throws psc_mv, psc_nk {
        this.j();
        if (vector == null) {
            throw new psc_nk("DatabaseService.selectCertificateByIssuerAndSerialNumber: certList should not be null.");
        }
        final int size = vector.size();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final int a = ((psc_m8)this.a(i)).a(psc_u, array, vector);
                b = false;
                if (a > 0) {
                    return vector.size() - size;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectCertificateByIssuerAndSerialNumber: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return 0;
    }
    
    public int a(final psc_u psc_u, final Vector vector) throws psc_mv, psc_nk {
        this.j();
        if (vector == null) {
            throw new psc_nk("DatabaseService.selectCertificateBySubject: certList should not be null.");
        }
        final int size = vector.size();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_u, vector);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectCertificateBySubject: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return vector.size() - size;
    }
    
    public int a(final psc_u psc_u, final psc_bg psc_bg, final Vector vector) throws psc_mv, psc_nk {
        this.j();
        if (vector == null) {
            throw new psc_nk("DatabaseService.selectCertificateByExtensions: certList should not be null.");
        }
        final int size = vector.size();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_u, psc_bg, vector);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectCertificateByExtensions: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return vector.size() - size;
    }
    
    public psc_f n() throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final psc_f e = ((psc_m8)this.a(i)).e();
                b = false;
                if (e != null) {
                    return e;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.firstCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return null;
    }
    
    public psc_f o() throws psc_mv, psc_nk {
        this.j();
        final int g = this.g();
        if (g < 0) {
            throw new psc_nk("DatabaseService.nextCertificate: no iterator is set up. Call firstCertificate first.");
        }
        psc_f f;
        try {
            f = ((psc_m8)this.a(g)).f();
        }
        catch (psc_n4 psc_n4) {
            throw new psc_nk("DatabaseService.nextCertificate: " + psc_n4.getMessage());
        }
        if (f != null) {
            return f;
        }
        for (int i = g + 1; i < this.m(); ++i) {
            try {
                final psc_f e = ((psc_m8)this.a(i)).e();
                if (e != null) {
                    return e;
                }
            }
            catch (psc_n4 psc_n5) {}
        }
        return null;
    }
    
    public boolean p() throws psc_mv, psc_nk {
        this.j();
        final int g = this.g();
        if (g < 0) {
            throw new psc_nk("DatabaseService.hasMoreCertificates: no iterator is set up.");
        }
        boolean g2;
        try {
            g2 = ((psc_m8)this.a(g)).g();
        }
        catch (psc_n4 psc_n5) {
            throw new psc_mv("DatabaseService.hasMoreCertificates: a provider is found to handle this method.");
        }
        if (g2) {
            return true;
        }
        try {
            ((psc_m8)this.a(g)).f();
        }
        catch (Exception ex) {}
        for (int i = g + 1; i < this.m(); ++i) {
            final psc_m8 psc_m8 = (psc_m8)this.a(i);
            try {
                psc_m8.d();
            }
            catch (psc_n4 psc_n6) {
                continue;
            }
            catch (psc_nk psc_nk) {
                throw new psc_mv("DatabaseService.hasMoreCertificates: unable to setup an iterator for a provider(" + psc_nk.getMessage() + ").");
            }
            try {
                if (psc_m8.g()) {
                    return true;
                }
                psc_m8.f();
            }
            catch (psc_n4 psc_n4) {
                throw new psc_nk("DatabaseService.hasMoreCertificates: " + psc_n4.getMessage());
            }
        }
        return false;
    }
    
    public int a(final psc_u psc_u, final Date date, final Vector vector) throws psc_mv, psc_nk {
        this.j();
        if (vector == null) {
            throw new psc_nk("DatabaseService.selectCRLByIssuerAndTime: crlList should not be null.");
        }
        final Vector vector2 = new Vector<psc_m9>();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_u, date, vector2);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectCRLByIssuerAndTime: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        Date when = new Date(0L);
        psc_m9 psc_m9 = null;
        for (int j = 0; j < vector2.size(); ++j) {
            final psc_m9 psc_m10 = vector2.elementAt(j);
            final Date g = psc_m10.g();
            if (g.after(when)) {
                when = g;
                psc_m9 = psc_m10;
            }
        }
        if (psc_m9 == null) {
            return 0;
        }
        if (!vector.contains(psc_m9)) {
            vector.addElement(psc_m9);
        }
        return 1;
    }
    
    public psc_na q() throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final psc_na j = ((psc_m8)this.a(i)).j();
                b = false;
                if (j != null) {
                    return j;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.firstCRL: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return null;
    }
    
    public psc_na r() throws psc_mv, psc_nk {
        this.j();
        final int h = this.h();
        if (h < 0) {
            throw new psc_nk("DatabaseService.nextCRL: no iterator is set up. Call firstCRL first.");
        }
        psc_na k;
        try {
            k = ((psc_m8)this.a(h)).k();
        }
        catch (psc_n4 psc_n4) {
            throw new psc_nk("DatabaseService.nextCRL: " + psc_n4.getMessage());
        }
        if (k != null) {
            return k;
        }
        for (int i = h + 1; i < this.m(); ++i) {
            try {
                final psc_na j = ((psc_m8)this.a(i)).j();
                if (j != null) {
                    return j;
                }
            }
            catch (psc_n4 psc_n5) {}
        }
        return null;
    }
    
    public boolean s() throws psc_mv, psc_nk {
        this.j();
        final int h = this.h();
        if (h < 0) {
            throw new psc_nk("DatabaseService.hasMoreCRLs: no iterator is set up.");
        }
        boolean l;
        try {
            l = ((psc_m8)this.a(h)).l();
        }
        catch (psc_n4 psc_n5) {
            throw new psc_mv("DatabaseService.hasMoreCRLs: a provider is found to handle this method.");
        }
        if (l) {
            return true;
        }
        try {
            ((psc_m8)this.a(h)).k();
        }
        catch (Exception ex) {}
        for (int i = h + 1; i < this.m(); ++i) {
            final psc_m8 psc_m8 = (psc_m8)this.a(i);
            try {
                psc_m8.i();
            }
            catch (psc_n4 psc_n6) {
                continue;
            }
            catch (psc_nk psc_nk) {
                throw new psc_mv("DatabaseService.hasMoreCRLs: unable to setup an iterator for a provider(" + psc_nk.getMessage() + ").");
            }
            try {
                if (psc_m8.l()) {
                    return true;
                }
                psc_m8.k();
            }
            catch (psc_n4 psc_n4) {
                throw new psc_nk("DatabaseService.hasMoreCRLs: " + psc_n4.getMessage());
            }
        }
        return false;
    }
    
    public psc_dt b(final psc_f psc_f) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final psc_dt b2 = ((psc_m8)this.a(i)).b(psc_f);
                b = false;
                if (b2 != null) {
                    return b2;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectPrivateKeyByCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return null;
    }
    
    public psc_dt a(final psc_al psc_al) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final psc_dt a = ((psc_m8)this.a(i)).a(psc_al);
                b = false;
                if (a != null) {
                    return a;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.selectPrivateKeyByPublicKey: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return null;
    }
    
    public psc_dt t() throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final psc_dt o = ((psc_m8)this.a(i)).o();
                b = false;
                if (o != null) {
                    return o;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.firstPrivateKey: no provider is found to handle this method(" + string.substring(1) + ").");
        }
        return null;
    }
    
    public psc_dt u() throws psc_mv, psc_nk {
        this.j();
        final int i = this.i();
        if (i < 0) {
            throw new psc_nk("DatabaseService.nextPrivateKey: no iterator is set up. Call firstPrivateKey first.");
        }
        psc_dt p;
        try {
            p = ((psc_m8)this.a(i)).p();
        }
        catch (psc_n4 psc_n4) {
            throw new psc_nk("DatabaseService.nextPrivateKey: " + psc_n4.getMessage());
        }
        if (p != null) {
            return p;
        }
        for (int j = i + 1; j < this.m(); ++j) {
            try {
                final psc_dt o = ((psc_m8)this.a(j)).o();
                if (o != null) {
                    return o;
                }
            }
            catch (psc_n4 psc_n5) {}
        }
        return null;
    }
    
    public boolean v() throws psc_mv, psc_nk {
        this.j();
        final int i = this.i();
        if (i < 0) {
            throw new psc_nk("DatabaseService.hasMorePrivateKeys: no iterator is set up.");
        }
        boolean q;
        try {
            q = ((psc_m8)this.a(i)).q();
        }
        catch (psc_n4 psc_n5) {
            throw new psc_mv("DatabaseService.hasMorePrivateKeys: a provider is found to handle this method.");
        }
        if (q) {
            return true;
        }
        try {
            ((psc_m8)this.a(i)).p();
        }
        catch (Exception ex) {}
        for (int j = i + 1; j < this.m(); ++j) {
            final psc_m8 psc_m8 = (psc_m8)this.a(j);
            try {
                psc_m8.n();
            }
            catch (psc_n4 psc_n6) {
                continue;
            }
            catch (psc_nk psc_nk) {
                throw new psc_mv("DatabaseService.hasMorePrivateKeys: unable to setup an iterator for a provider(" + psc_nk.getMessage() + ").");
            }
            try {
                if (psc_m8.q()) {
                    return true;
                }
                psc_m8.p();
            }
            catch (psc_n4 psc_n4) {
                throw new psc_nk("DatabaseService.hasMorePrivateKeys: " + psc_n4.getMessage());
            }
        }
        return false;
    }
    
    public void a(final psc_u psc_u, final byte[] array) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_u, array);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.deleteCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void a(final psc_u psc_u, final Date date) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).a(psc_u, date);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.deleteCRL: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void c(final psc_f psc_f) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).c(psc_f);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.deletePrivateKeyByCertificate: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    public void b(final psc_al psc_al) throws psc_mv, psc_nk {
        this.j();
        boolean b = true;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                ((psc_m8)this.a(i)).b(psc_al);
                b = false;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.deletePrivateKeyByPublicKey: no provider is found to handle this method(" + string.substring(1) + ").");
        }
    }
    
    private void a() throws psc_mv, psc_nk {
        this.j();
        String string = "";
        int i = 0;
        while (i < this.m()) {
            try {
                ((psc_m8)this.a(i)).d();
                return;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("DatabaseService.setupCertificateIterator: no provider is found to handle this method(" + string.substring(1) + ").");
    }
    
    private void b() throws psc_mv, psc_nk {
        this.j();
        String string = "";
        int i = 0;
        while (i < this.m()) {
            try {
                ((psc_m8)this.a(i)).i();
                return;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("DatabaseService.setupCRLIterator: no provider is found to handle this method(" + string.substring(1) + ").");
    }
    
    private void c() throws psc_mv, psc_nk {
        this.j();
        String string = "";
        int i = 0;
        while (i < this.m()) {
            try {
                ((psc_m8)this.a(i)).n();
                return;
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
                ++i;
                continue;
            }
            break;
        }
        throw new psc_mv("DatabaseService.setupPrivateKeyIterator: no provider is found to handle this method(" + string.substring(1) + ").");
    }
    
    private boolean d() throws psc_mv, psc_nk {
        this.j();
        return this.g() >= 0;
    }
    
    private boolean e() throws psc_mv, psc_nk {
        this.j();
        return this.h() >= 0;
    }
    
    private boolean f() throws psc_mv, psc_nk {
        this.j();
        return this.i() >= 0;
    }
    
    private int g() throws psc_mv, psc_nk {
        boolean b = true;
        int n = -1;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final boolean a = ((psc_m8)this.a(i)).a();
                b = false;
                if (a) {
                    n = i;
                    break;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.findCurrentCertificateIterator: no provider is found to handle isCertificateIteratorSetup method(" + string.substring(1) + ").");
        }
        return n;
    }
    
    private int h() throws psc_mv, psc_nk {
        boolean b = true;
        int n = -1;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final boolean h = ((psc_m8)this.a(i)).h();
                b = false;
                if (h) {
                    n = i;
                    break;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.findCurrentCRLIterator: no provider is found to handle isCRLIteratorSetup method(" + string.substring(1) + ").");
        }
        return n;
    }
    
    private int i() throws psc_mv, psc_nk {
        boolean b = true;
        int n = -1;
        String string = "";
        for (int i = 0; i < this.m(); ++i) {
            try {
                final boolean m = ((psc_m8)this.a(i)).m();
                b = false;
                if (m) {
                    n = i;
                    break;
                }
            }
            catch (psc_n4 psc_n4) {
                string = string + "/" + psc_n4.getMessage();
            }
        }
        if (b) {
            throw new psc_mv("DatabaseService.findCurrentPrivateKeyIterator: no provider is found to handle isPrivateKeyIteratorSetup method(" + string.substring(1) + ").");
        }
        return n;
    }
    
    private void j() throws psc_mv {
        if (this.m() == 0) {
            throw new psc_mv("DatabaseService.checkForEmptyService: no Database provider is bound to this service. Use CertJ.registerService followed by CertJ.bindService or CertJ.bindServices to obtain a non-empty DatabaseService object.");
        }
    }
}
