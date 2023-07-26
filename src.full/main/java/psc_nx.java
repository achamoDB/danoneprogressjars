import java.util.Date;
import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_nx extends psc_d8
{
    private Vector a;
    private Vector b;
    private Vector c;
    private Vector d;
    
    public psc_nx(final String s) throws psc_d7 {
        super(1, s);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }
    
    public psc_nx(final String s, final Vector a, final Vector b, final Vector c, final Vector d) throws psc_d7 {
        super(1, s);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        if (c == null || d == null || c.size() != d.size()) {
            throw new psc_d7("MemoryDB.MemoryDB: privateKeys and publicKeys should have the same number of elements.");
        }
        if (a != null) {
            this.a = a;
        }
        if (b != null) {
            this.b = b;
        }
        if (c != null) {
            this.c = c;
            this.d = d;
        }
    }
    
    public psc_ea a(final psc_ah psc_ah) throws psc_d9 {
        try {
            return new on(psc_ah, this.b());
        }
        catch (psc_d7 psc_d7) {
            throw new psc_d9("MemoryDB.instantiate: " + psc_d7.getMessage());
        }
    }
    
    private final class on extends psc_ea implements psc_m8
    {
        private Vector a;
        private Vector b;
        private Vector c;
        private Vector d;
        private int e;
        private int f;
        private int g;
        private Object h;
        private Object i;
        private Object j;
        
        private on(final psc_ah psc_ah, final String s) throws psc_d7 {
            super(psc_ah, s);
            this.h = new Object();
            this.i = new Object();
            this.j = new Object();
            if (psc_nx.this.a == null) {
                this.a = new Vector();
            }
            else {
                this.a = psc_nx.this.a;
            }
            if (psc_nx.this.b == null) {
                this.b = new Vector();
            }
            else {
                this.b = psc_nx.this.b;
            }
            if (psc_nx.this.c == null) {
                this.c = new Vector();
                this.d = new Vector();
            }
            else {
                this.c = psc_nx.this.c;
                this.d = psc_nx.this.d;
            }
            this.e = -1;
            this.f = -1;
            this.g = -1;
        }
        
        public void a(final psc_f o) throws psc_nk {
            if (o == null) {
                throw new psc_nk("MemoryDBProvider.insertCertificate: cert should not be null.");
            }
            synchronized (this.a) {
                if (!this.a.contains(o)) {
                    try {
                        this.a.addElement(((psc_e)o).clone());
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.insertCertificate: Unable to clone the certificate(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public void a(final psc_na o) throws psc_nk {
            if (o == null) {
                throw new psc_nk("MemoryDBProvider.insertCRL: crl should not be null.");
            }
            synchronized (this.b) {
                if (!this.b.contains(o)) {
                    try {
                        this.b.addElement(((psc_m9)o).clone());
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.insertCRL: Unable to clone the CRL(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public void a(final psc_f psc_f, final psc_dt psc_dt) throws psc_nk {
            if (psc_f == null) {
                throw new psc_nk("MemoryDB$Implementation.insertPrivateKeyByCertificate: cert should not be null");
            }
            try {
                this.a(psc_f.b(super.b.f()), psc_dt);
            }
            catch (psc_g psc_g) {
                throw new psc_nk("MemoryDB$Implementation.insertPrivateKeyByCertificate: " + psc_g.getMessage());
            }
        }
        
        public void a(final psc_al o, final psc_dt psc_dt) throws psc_nk {
            if (o == null || psc_dt == null) {
                throw new psc_nk("MemoryDBProvider.insertPrivateKeyByPublicKey: Neither publicKey nor privateKey should be null.");
            }
            synchronized (this.c) {
                if (!this.d.contains(o)) {
                    try {
                        this.c.addElement(psc_dt.clone());
                        this.d.addElement(o.clone());
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.insertPrivateKeyByPublicKey: Unable to clone a key(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public int a(final psc_u psc_u, final byte[] array, final Vector vector) throws psc_nk {
            if (psc_u == null || array == null) {
                throw new psc_nk("MemoryDBProvider.Neither issuerName nor serialNumber should be null.");
            }
            synchronized (this.a) {
                for (int i = 0; i < this.a.size(); ++i) {
                    try {
                        final psc_e o = this.a.elementAt(i);
                        if (psc_u.equals(o.g()) && psc_k4.a(array, o.h())) {
                            if (!vector.contains(o)) {
                                vector.addElement(o.clone());
                            }
                            return 1;
                        }
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.selectCertificateByIssuerAndSerialNumber: Unable to clone a certificate(" + ex.getMessage() + ").");
                    }
                    catch (ClassCastException ex2) {}
                }
                return 0;
            }
        }
        
        public int a(final psc_u psc_u, final Vector vector) throws psc_nk {
            if (psc_u == null) {
                throw new psc_nk("MemoryDBProvider.selectCertificateBySubject: subjectName should not be null.");
            }
            int n = 0;
            synchronized (this.a) {
                for (int i = 0; i < this.a.size(); ++i) {
                    try {
                        final psc_e o = this.a.elementAt(i);
                        if (o.f().equals(psc_u)) {
                            if (!vector.contains(o)) {
                                vector.addElement(o.clone());
                            }
                            ++n;
                        }
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.selectCertificateByIssuerAndSerialNumber: Unable to clone a certificate(" + ex.getMessage() + ").");
                    }
                    catch (ClassCastException ex2) {}
                }
                return n;
            }
        }
        
        public int a(final psc_u psc_u, final psc_bg psc_bg, final Vector vector) throws psc_nk {
            if (psc_u == null && psc_bg == null) {
                throw new psc_nk("MemoryDB.selectCertificateByExtensions: Either baseName or extensions should have a non-null value.");
            }
            int n = 0;
            synchronized (this.a) {
                try {
                    for (int i = 0; i < this.a.size(); ++i) {
                        final psc_e o = this.a.elementAt(i);
                        if (psc_u == null || o.f().a(psc_u)) {
                            if (psc_k4.a(psc_bg, o.n())) {
                                if (!vector.contains(o)) {
                                    vector.addElement(o.clone());
                                }
                                ++n;
                            }
                        }
                    }
                }
                catch (CloneNotSupportedException ex) {
                    throw new psc_nk("MemoryDBProvider.selectCertificateByExtensions: Unable to clone a certificate(" + ex.getMessage() + ").");
                }
                return n;
            }
        }
        
        public boolean a() {
            synchronized (this.h) {
                return this.e >= 0;
            }
        }
        
        public void d() {
            synchronized (this.h) {
                this.e = 0;
            }
        }
        
        public psc_f e() throws psc_nk {
            synchronized (this.h) {
                this.d();
                synchronized (this.a) {
                    if (this.a.size() == 0) {
                        this.e = -1;
                        return null;
                    }
                    try {
                        return (psc_f)this.a.elementAt(this.e++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.firstCertificate: Unable to clone a certificate(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public psc_f f() throws psc_nk {
            synchronized (this.h) {
                if (!this.a()) {
                    this.d();
                }
                synchronized (this.a) {
                    if (this.e >= this.a.size()) {
                        this.e = -1;
                        return null;
                    }
                    try {
                        return (psc_f)this.a.elementAt(this.e++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.nextCertificate: Unable to clone a certificate(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public boolean g() throws psc_nk {
            synchronized (this.h) {
                if (!this.a()) {
                    this.d();
                }
                synchronized (this.a) {
                    return this.e < this.a.size();
                }
            }
        }
        
        public int a(final psc_u psc_u, final Date when, final Vector vector) throws psc_nk {
            if (psc_u == null || when == null) {
                throw new psc_nk("MemoryDBProvider.selectCRLByIssuerAndTime: Neither issuerName nor time should be null.");
            }
            Date when2 = new Date(0L);
            psc_m9 o = null;
            synchronized (this.b) {
                for (int i = 0; i < this.b.size(); ++i) {
                    try {
                        final psc_m9 psc_m9 = this.b.elementAt(i);
                        if (psc_u.equals(psc_m9.e())) {
                            final Date g = psc_m9.g();
                            if (!g.after(when) && g.after(when2)) {
                                when2 = g;
                                o = psc_m9;
                            }
                        }
                    }
                    catch (ClassCastException ex2) {}
                }
                if (o == null) {
                    return 0;
                }
                if (!vector.contains(o)) {
                    try {
                        vector.addElement(o.clone());
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.selectCRLByIssuerAndTime: Unable to clone a CRL(" + ex.getMessage() + ").");
                    }
                }
                return 1;
            }
        }
        
        public boolean h() {
            synchronized (this.i) {
                return this.f >= 0;
            }
        }
        
        public void i() {
            synchronized (this.i) {
                this.f = 0;
            }
        }
        
        public psc_na j() throws psc_nk {
            synchronized (this.i) {
                this.i();
                synchronized (this.b) {
                    if (this.b.size() == 0) {
                        this.f = -1;
                        return null;
                    }
                    try {
                        return (psc_na)this.b.elementAt(this.f++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.firstCRL: Unable to clone a CRL(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public psc_na k() throws psc_nk {
            synchronized (this.i) {
                if (!this.h()) {
                    this.i();
                }
                synchronized (this.b) {
                    if (this.f >= this.b.size()) {
                        this.f = -1;
                        return null;
                    }
                    try {
                        return (psc_na)this.b.elementAt(this.f++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDBProvider.nextCRL: Unable to clone a CRL(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public boolean l() throws psc_nk {
            synchronized (this.i) {
                if (!this.h()) {
                    this.i();
                }
                synchronized (this.b) {
                    return this.f < this.b.size();
                }
            }
        }
        
        public psc_dt b(final psc_f psc_f) throws psc_nk {
            if (psc_f == null) {
                throw new psc_nk("MemoryDB$Implementation.selectPrivateKeyByCertificate: cert should not be null.");
            }
            try {
                return this.a(psc_f.b(super.b.f()));
            }
            catch (psc_g psc_g) {
                throw new psc_nk("MemoryDB$Implementation.selectPrivateKeyByCertificate: " + psc_g.getMessage());
            }
        }
        
        public psc_dt a(final psc_al psc_al) throws psc_nk {
            if (psc_al == null) {
                throw new psc_nk("MemoryDB$Implementation.selectPrivateKeyByPublicKey: publicKey should not be null.");
            }
            synchronized (this.c) {
                for (int i = 0; i < this.d.size(); ++i) {
                    if (psc_al.equals(this.d.elementAt(i))) {
                        try {
                            return (psc_dt)((psc_dt)this.c.elementAt(i)).clone();
                        }
                        catch (CloneNotSupportedException ex) {
                            throw new psc_nk("MemoryDB$Implementation.selectPrivateKeyByPublicKeyUnable to clone a private key().");
                        }
                    }
                }
                return null;
            }
        }
        
        public boolean m() {
            synchronized (this.j) {
                return this.g >= 0;
            }
        }
        
        public void n() {
            synchronized (this.j) {
                this.g = 0;
            }
        }
        
        public psc_dt o() throws psc_nk {
            synchronized (this.j) {
                this.n();
                synchronized (this.c) {
                    if (this.c.size() == 0) {
                        this.g = -1;
                        return null;
                    }
                    try {
                        return (psc_dt)this.c.elementAt(this.g++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDB$Implementation.firstPrivateKey: Unable to clone a private key(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public psc_dt p() throws psc_nk {
            synchronized (this.j) {
                if (!this.m()) {
                    this.n();
                }
                synchronized (this.c) {
                    if (this.g >= this.c.size()) {
                        this.g = -1;
                        return null;
                    }
                    try {
                        return (psc_dt)this.c.elementAt(this.g++).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        throw new psc_nk("MemoryDB$Implementation.nextPrivateKey: Unable to clone a private key(" + ex.getMessage() + ").");
                    }
                }
            }
        }
        
        public boolean q() throws psc_nk {
            synchronized (this.j) {
                if (!this.m()) {
                    this.n();
                }
                synchronized (this.c) {
                    return this.g < this.c.size();
                }
            }
        }
        
        public void a(final psc_u psc_u, final byte[] array) throws psc_nk {
            if (psc_u == null || array == null) {
                throw new psc_nk("MemoryDB$Implementation.deleteCertificate: Neither issuerName nor serialNumber should be null.");
            }
            synchronized (this.a) {
                for (int i = 0; i < this.a.size(); ++i) {
                    try {
                        final psc_e psc_e = this.a.elementAt(i);
                        if (psc_u.equals(psc_e.g()) && psc_k4.a(array, psc_e.h())) {
                            this.a.removeElementAt(i);
                        }
                    }
                    catch (ClassCastException ex) {}
                }
            }
        }
        
        public void a(final psc_u psc_u, final Date obj) throws psc_nk {
            if (psc_u == null || obj == null) {
                throw new psc_nk("MemoryDB$Implementation.deleteCRL: Neither issuerName nor lastUpdate should be null.");
            }
            synchronized (this.b) {
                for (int i = 0; i < this.b.size(); ++i) {
                    try {
                        final psc_m9 psc_m9 = this.b.elementAt(i);
                        if (psc_u.equals(psc_m9.e()) && psc_m9.g().equals(obj)) {
                            this.b.removeElementAt(i);
                        }
                    }
                    catch (ClassCastException ex) {}
                }
            }
        }
        
        public void c(final psc_f psc_f) throws psc_nk {
            if (psc_f == null) {
                throw new psc_nk("MemoryDB$Implementation.deletePrivateKeyByCertificate: cert should not be null.");
            }
            try {
                this.b(psc_f.b(super.b.f()));
            }
            catch (psc_g psc_g) {
                throw new psc_nk("MemoryDB$Implementation.deletePrivateKeyByCertificate: " + psc_g.getMessage());
            }
        }
        
        public void b(final psc_al psc_al) throws psc_nk {
            if (psc_al == null) {
                throw new psc_nk("MemoryDB$Implementation.deletePrivateKeyByPublicKey: publickKey should not be null.");
            }
            synchronized (this.c) {
                for (int i = 0; i < this.c.size(); ++i) {
                    if (psc_al.equals(this.d.elementAt(i))) {
                        this.c.removeElementAt(i);
                        this.d.removeElementAt(i);
                        return;
                    }
                }
            }
        }
        
        public String toString() {
            return "In-memory database provider named: " + super.b();
        }
    }
}
