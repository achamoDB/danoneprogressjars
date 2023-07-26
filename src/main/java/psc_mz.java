import java.math.BigInteger;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mz implements Serializable
{
    public static final int a = 1;
    public static final int b = 2;
    public static final String[] c;
    public static final String[] d;
    public static final int e = 1;
    public static final int[] f;
    private static int g;
    private psc_m1 h;
    private byte[] i;
    private psc_ah j;
    
    protected psc_mz(final psc_ah j) {
        this.h = null;
        this.j = j;
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final InputStream inputStream, final int n) throws psc_m0 {
        this(psc_ah);
        this.a(psc_ed, array, null, inputStream, n);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final char[] array2, final InputStream inputStream, final int n) throws psc_m0 {
        this(psc_ah);
        this.a(psc_ed, array, array2, inputStream, n);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final InputStream inputStream) throws psc_m0 {
        this(psc_ah, psc_ed, array, inputStream, -1);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final char[] array2, final InputStream inputStream) throws psc_m0 {
        this(psc_ah, psc_ed, array, array2, inputStream, -1);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final String pathname) throws psc_m0 {
        this(psc_ah);
        if (pathname == null) {
            throw new psc_m0("PKCS12.PKCS12: pkcs12File should not be null.");
        }
        this.a(psc_ed, array, null, new File(pathname));
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final char[] array2, final String pathname) throws psc_m0 {
        this(psc_ah);
        if (pathname == null) {
            throw new psc_m0("PKCS12.PKCS12: pkcs12File should not be null.");
        }
        this.a(psc_ed, array, array2, new File(pathname));
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final File file) throws psc_m0 {
        this(psc_ah);
        this.a(psc_ed, array, null, file);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, final char[] array2, final File file) throws psc_m0 {
        this(psc_ah);
        this.a(psc_ed, array, array2, file);
    }
    
    private void a(final psc_ed psc_ed, final char[] array, final char[] array2, final InputStream inputStream, final int n) throws psc_m0 {
        if (array == null) {
            throw new psc_m0("PKCS12.importPKCS12: password should not be null.");
        }
        if (inputStream == null) {
            throw new psc_m0("PKCS12.importPKCS12: inputStream should not be null.");
        }
        this.a(inputStream, n);
        this.h = new psc_m1(this.j, psc_ed, array, array2, this.i, 0, 0);
    }
    
    private void a(final psc_ed psc_ed, final char[] array, final char[] array2, final File file) throws psc_m0 {
        if (file == null) {
            throw new psc_m0("PKCS12.import: pkcs12File should not be null.");
        }
        InputStream inputStream = null;
        Label_0112: {
            try {
                inputStream = new FileInputStream(file);
                this.a(psc_ed, array, array2, inputStream, (int)file.length());
                break Label_0112;
            }
            catch (FileNotFoundException ex) {
                throw new psc_m0("PKCS12.import: Could not find file " + file.toString() + "(" + ex.getMessage() + ")");
            }
            finally {
                Label_0247: {
                    if (inputStream != null) {
                        try {
                            ((FileInputStream)inputStream).close();
                            break Label_0247;
                        }
                        catch (IOException ex2) {
                            throw new psc_m0("PKCS12.import: Could not close file " + file.toString() + "(" + ex2.getMessage() + ")");
                        }
                        break Label_0247;
                    }
                    break Label_0247;
                }
                while (true) {}
                // iftrue(Label_0178:, inputStream == null)
                Block_6: {
                    break Block_6;
                    Label_0178: {
                        return;
                    }
                }
                try {
                    ((FileInputStream)inputStream).close();
                }
                catch (IOException ex3) {
                    throw new psc_m0("PKCS12.import: Could not close file " + file.toString() + "(" + ex3.getMessage() + ")");
                }
            }
        }
    }
    
    private void a(final InputStream inputStream, final int i) throws psc_m0 {
        try {
            if (i > 0) {
                this.i = new byte[i];
                final int read = inputStream.read(this.i);
                if (i != read) {
                    throw new psc_m0("PKCS12.readBER: Not enough bytes read. " + i + " bytes expected, " + read + " read.");
                }
            }
            else {
                int n = 0;
                byte[] j = new byte[0];
                final byte[] b = new byte[psc_mz.g];
                while (true) {
                    final int read2 = inputStream.read(b);
                    if (read2 == -1) {
                        break;
                    }
                    final byte[] array = new byte[n + read2];
                    System.arraycopy(j, 0, array, 0, n);
                    System.arraycopy(b, 0, array, n, read2);
                    n += read2;
                    j = array;
                }
                this.i = j;
            }
        }
        catch (Exception ex) {
            throw new psc_m0("PKCS12.readBER: error occurred while reading from stream " + inputStream.toString() + "(" + ex.getMessage() + ")");
        }
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_f[] array, final psc_na[] array2, final psc_dt[] array3, final psc_cr[] array4, final psc_cr[] array5, final psc_cr[] array6) throws psc_d7 {
        this(psc_ah, array, array2, array3, array4, array5, array6, null);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_f[] array, final psc_na[] array2, final psc_dt[] array3, psc_cr[] a, final psc_cr[] array4, psc_cr[] array5, final String[] array6) throws psc_d7 {
        this(psc_ah);
        if (array == null && array2 == null && array3 == null) {
            throw new psc_d7("PKCS12.PKCS12: At least one of certs, crls and keys should not be null.");
        }
        if (array3 != null && array != null && (a == null || array5 == null)) {
            final psc_f[] a2 = this.a(array, array3);
            if (a == null) {
                a = new psc_cr[a2.length];
            }
            else {
                a = a(a2, array, a);
            }
            if (array5 == null) {
                array5 = new psc_cr[array3.length];
            }
            this.a(a2, array3, a, array5);
            this.h = new psc_m1(a2, array2, array3, a, array4, array5, array6);
            return;
        }
        this.h = new psc_m1(array, array2, array3, a, array4, array5, array6);
    }
    
    private static psc_cr[] a(final psc_f[] array, final psc_f[] array2, final psc_cr[] array3) {
        final int length = array3.length;
        final psc_cr[] array4 = new psc_cr[length];
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                if (array[i].hashCode() == array2[j].hashCode()) {
                    array4[i] = array3[j];
                    break;
                }
            }
        }
        return array4;
    }
    
    private psc_f[] a(final psc_f[] array, final psc_dt[] array2) throws psc_d7 {
        final byte[][] array3 = null;
        final byte[][] array4 = null;
        final psc_f[] array5 = new psc_f[array.length];
        final Vector vector = new Vector<psc_f>();
        final psc_ez psc_ez = new psc_ez();
        final psc_ez psc_ez2 = new psc_ez();
        final psc_ez psc_ez3 = new psc_ez();
        final psc_ez psc_ez4 = new psc_ez();
        final psc_ez psc_ez5 = new psc_ez();
        for (int i = 0; i < array.length; ++i) {
            vector.addElement(array[i]);
        }
        int j;
        try {
            for (j = 0; j < array2.length; ++j) {
                final String l = array2[j].l();
                final int size = vector.size();
                if (l.equals("RSA")) {
                    final byte[][] b = array2[j].b("RSAPrivateKeyCRT");
                    int k;
                    for (k = 0; k < size; ++k) {
                        final psc_al b2 = vector.elementAt(k).b("Java");
                        if (b2.l().equals("RSA")) {
                            final byte[][] b3 = b2.b("RSAPublicKey");
                            if (b == null || b3 == null) {
                                throw new psc_d7("Invalid RSA key.");
                            }
                            if (psc_k4.a(b3[0], b[0]) && psc_k4.a(b3[1], b[1])) {
                                array5[j] = vector.elementAt(k);
                                vector.removeElementAt(k);
                                break;
                            }
                        }
                    }
                    if (k == size) {
                        throw new psc_d7("No corresponding cert found.");
                    }
                }
                else if (l.equals("DSA")) {
                    final byte[][] b4 = array2[j].b("DSAPrivateKey");
                    if (b4 == null || b4[0] == null || b4[2] == null || b4[3] == null) {
                        throw new psc_d7("Invalid DSA private key.");
                    }
                    psc_ez.a(b4[0], 0, b4[0].length);
                    psc_ez2.a(b4[2], 0, b4[2].length);
                    psc_ez3.a(b4[3], 0, b4[3].length);
                    psc_ez2.c(psc_ez3, psc_ez, psc_ez4);
                    int index;
                    for (index = 0; index < size; ++index) {
                        final psc_al b5 = vector.elementAt(index).b("Java");
                        if (b5.l().equals("DSA")) {
                            final byte[][] b6 = b5.b("DSAPublicKey");
                            if (b6 == null || b6[3] == null) {
                                throw new psc_d7("Invalid DSA public key.");
                            }
                            psc_ez5.a(b6[3], 0, b6[3].length);
                            if (psc_ez5.b((psc_e0)psc_ez4) == 0) {
                                array5[j] = vector.elementAt(index);
                                vector.removeElementAt(index);
                                break;
                            }
                        }
                    }
                    if (index == size) {
                        throw new psc_d7("No corresponding cert found.");
                    }
                }
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_d7("Invalid key." + psc_ap.getMessage());
        }
        catch (psc_g psc_g) {
            throw new psc_d7("Invalid certificate." + psc_g.getMessage());
        }
        final int size2 = vector.size();
        int n = j;
        for (int index2 = 0; index2 < size2; ++index2) {
            array5[n] = vector.elementAt(index2);
            ++n;
        }
        return array5;
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_u psc_u, final psc_nq psc_nq) throws psc_d7, psc_m0 {
        this(psc_ah, psc_u, psc_nq, null);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_u psc_u, final psc_nq psc_nq, final String s) throws psc_d7, psc_m0 {
        this(psc_ah);
        if (psc_ah == null) {
            throw new psc_d7("PKCS12.PKCS12: certJ should not be null.");
        }
        if (psc_u == null) {
            throw new psc_d7("PKCS12.PKCS12: subjectName should not be null.");
        }
        if (psc_nq == null) {
            throw new psc_d7("PKCS12.PKCS12: pathCtx should not be null.");
        }
        final psc_ed e = psc_nq.e();
        final Vector<psc_f> vector = new Vector<psc_f>();
        int a;
        try {
            a = e.a(psc_u, vector);
        }
        catch (Exception ex) {
            throw new psc_m0("PKCS12.PKCS12: Finding certificates for the subjectName faild (" + ex.getMessage() + ").");
        }
        if (a == 0) {
            throw new psc_m0("PKCS12.PKCS12: No certificate found for the subjectName(" + psc_u.toString() + ").");
        }
        final Vector vector2 = new Vector<psc_f>();
        final Vector vector3 = new Vector<psc_dt>();
        for (int i = 0; i < a; ++i) {
            try {
                final psc_dt b = e.b(vector.elementAt(i));
                if (b != null) {
                    vector3.addElement(b);
                    vector2.addElement(vector.elementAt(i));
                }
            }
            catch (Exception ex2) {
                throw new psc_m0("PKCS12.PKCS12: Retrieving private key certificate failed(" + ex2.getMessage() + ").");
            }
        }
        final Vector<psc_f> vector4 = new Vector<psc_f>();
        final Vector<psc_na> vector5 = new Vector<psc_na>();
        for (int j = 0; j < a; ++j) {
            try {
                psc_ah.a(psc_nq, vector2.elementAt(j), vector2, vector5, vector4, null);
            }
            catch (Exception ex3) {
                throw new psc_m0("PKCS12.PKCS12: Building certification path failed(" + ex3.getMessage() + ").");
            }
        }
        for (int k = 0; k < vector4.size(); ++k) {
            vector2.addElement(vector4.elementAt(k));
        }
        final int size = vector2.size();
        final psc_cr[] array = new psc_cr[size];
        final psc_f[] array2 = new psc_f[size];
        for (int l = 0; l < size; ++l) {
            array2[l] = vector2.elementAt(l);
        }
        final int size2 = vector5.size();
        psc_na[] array3 = null;
        if (size2 != 0) {
            array3 = new psc_na[size2];
            for (int index = 0; index < size2; ++index) {
                array3[index] = vector5.elementAt(index);
            }
        }
        final int size3 = vector3.size();
        final psc_cr[] array4 = new psc_cr[size3];
        final psc_dt[] array5 = new psc_dt[size3];
        final String[] array6 = new String[size3];
        for (int index2 = 0; index2 < size3; ++index2) {
            array5[index2] = vector3.elementAt(index2);
            array6[index2] = s;
        }
        this.a(array2, array5, array, array4);
        this.h = new psc_m1(array2, array3, array5, array, null, array4, array6);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_f psc_f, final psc_nq psc_nq) throws psc_d7, psc_m0 {
        this(psc_ah, psc_f, psc_nq, null);
    }
    
    public psc_mz(final psc_ah psc_ah, final psc_f obj, final psc_nq psc_nq, final String s) throws psc_d7, psc_m0 {
        this(psc_ah);
        if (psc_ah == null) {
            throw new psc_d7("PKCS12.PKCS12: cert should not be null.");
        }
        if (obj == null) {
            throw new psc_d7("PKCS12.PKCS12: certJ should not be null.");
        }
        if (psc_nq == null) {
            throw new psc_d7("PKCS12.PKCS12: pathCtx should not be null.");
        }
        final psc_ed e = psc_nq.e();
        final Vector vector = new Vector<psc_f>();
        vector.addElement(obj);
        final Vector vector2 = new Vector<psc_na>();
        psc_dt b;
        try {
            b = e.b(obj);
        }
        catch (psc_d6 psc_d6) {
            throw new psc_m0("PKCS12.PKCS12: Finding private key for given certificate failed(" + psc_d6.getMessage() + ").");
        }
        if (b == null) {
            throw new psc_m0("PKCS12.PKCS12: Matching private key not found.");
        }
        final Vector<psc_f> vector3 = new Vector<psc_f>();
        try {
            psc_ah.a(psc_nq, obj, vector, vector2, vector3, null);
        }
        catch (psc_d6 psc_d7) {
            throw new psc_m0("PKCS12.PKCS12: Building certification path failed(" + psc_d7.getMessage() + ").");
        }
        for (int i = 0; i < vector3.size(); ++i) {
            vector.addElement(vector3.elementAt(i));
        }
        final int size = vector.size();
        final psc_cr[] array = new psc_cr[size];
        final psc_f[] array2 = new psc_f[size];
        for (int j = 0; j < size; ++j) {
            array2[j] = vector.elementAt(j);
        }
        final int size2 = vector2.size();
        psc_na[] array3 = null;
        if (size2 != 0) {
            array3 = new psc_na[size2];
            for (int k = 0; k < size2; ++k) {
                array3[k] = vector2.elementAt(k);
            }
        }
        final psc_cr[] array4 = { null };
        final psc_dt[] array5 = { b };
        final String[] array6 = { s };
        this.a(array2, array5, array, array4);
        this.h = new psc_m1(array2, array3, array5, array, null, array4, array6);
    }
    
    public void a(final String pathname, final char[] array, final String s, final String s2, final int n, final int n2) throws psc_d7, psc_m0 {
        if (pathname == null) {
            throw new psc_d7("PKCS12.export: filename should not be null.");
        }
        this.a(new File(pathname), array, s, s2, n, n2);
    }
    
    public void a(final File file, final char[] array, final String s, final String s2, final int n, final int n2) throws psc_d7, psc_m0 {
        if (file == null) {
            throw new psc_d7("PKCS12.export: file should not be null.");
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        }
        catch (IOException ex) {
            throw new psc_m0("PKCS12.export: Creation of stream failed(" + ex.getMessage() + ").");
        }
        this.a(fileOutputStream, array, s, s2, n, n2);
    }
    
    public void a(final String pathname, final char[] array, final char[] array2, final String s, final String s2, final int n, final int n2) throws psc_d7, psc_m0 {
        if (pathname == null) {
            throw new psc_d7("PKCS12.export: filename should not be null.");
        }
        this.a(new File(pathname), array, array2, s, s2, n, n2);
    }
    
    public void a(final File file, final char[] array, final char[] array2, final String s, final String s2, final int n, final int n2) throws psc_d7, psc_m0 {
        if (file == null) {
            throw new psc_d7("PKCS12.export: file should not be null.");
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        }
        catch (IOException ex) {
            throw new psc_m0("PKCS12.export: Creation of stream failed(" + ex.getMessage() + ").");
        }
        this.a(fileOutputStream, array, array2, s, s2, n, n2);
    }
    
    public void a(final OutputStream outputStream, final char[] array, final String s, final String s2, final int n, final int n2) throws psc_d7, psc_m0 {
        this.a(outputStream, array, null, s, s2, n, n2);
    }
    
    public void a(final OutputStream outputStream, final char[] array, final char[] array2, String s, String s2, int n, int n2) throws psc_d7, psc_m0 {
        if (outputStream == null) {
            throw new psc_d7("PKCS12.export: stream should not be null.");
        }
        if (this.j == null) {
            throw new psc_m0("PKCS12.export: Object not initialized with certJ.");
        }
        if (this.h == null) {
            throw new psc_m0("PKCS12.export: Object not initialized with pfx.");
        }
        if (array == null) {
            throw new psc_d7("PKCS12.export: password can not be null.");
        }
        if (s == null) {
            s = psc_mz.c[0];
        }
        if (s2 == null) {
            s2 = psc_mz.d[0];
        }
        if (n <= 0) {
            n = 1;
        }
        if (n2 == 0) {
            n2 = psc_mz.f[0];
        }
        else if (!this.b(n2)) {
            throw new psc_d7("PKCS12.export: option is invalid.");
        }
        this.i = this.h.a(this.j, array, array2, s, s2, n, n2);
        try {
            outputStream.write(this.i);
            outputStream.close();
        }
        catch (IOException ex) {
            throw new psc_m0("PKCS12.export: Writing to stream failed(" + ex.getMessage() + ").");
        }
    }
    
    private byte[] a(final File file) throws psc_m0 {
        try {
            final int n = (int)file.length();
            final FileInputStream fileInputStream = new FileInputStream(file);
            final byte[] b = new byte[n];
            fileInputStream.read(b);
            fileInputStream.close();
            return b;
        }
        catch (Exception ex) {
            throw new psc_m0("Could not open file " + file);
        }
    }
    
    public psc_f[] a() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector a = this.h.b().a();
        final psc_f[] array = new psc_f[a.size()];
        for (int i = 0; i < a.size(); ++i) {
            array[i] = a.elementAt(i);
        }
        return array;
    }
    
    public psc_na[] b() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector b = this.h.b().b();
        final psc_na[] array = new psc_na[b.size()];
        for (int i = 0; i < b.size(); ++i) {
            array[i] = b.elementAt(i);
        }
        return array;
    }
    
    public psc_dt[] c() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector c = this.h.b().c();
        final psc_dt[] array = new psc_dt[c.size()];
        for (int i = 0; i < c.size(); ++i) {
            array[i] = c.elementAt(i);
        }
        return array;
    }
    
    public psc_cr[] d() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector e = this.h.b().e();
        final psc_cr[] array = new psc_cr[e.size()];
        for (int i = 0; i < e.size(); ++i) {
            array[i] = e.elementAt(i);
        }
        return array;
    }
    
    public psc_cr[] e() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector d = this.h.b().d();
        final psc_cr[] array = new psc_cr[d.size()];
        for (int i = 0; i < d.size(); ++i) {
            array[i] = d.elementAt(i);
        }
        return array;
    }
    
    public psc_cr[] f() {
        if (this.h == null || this.h.b() == null) {
            return null;
        }
        final Vector f = this.h.b().f();
        final psc_cr[] array = new psc_cr[f.size()];
        for (int i = 0; i < f.size(); ++i) {
            array[i] = f.elementAt(i);
        }
        return array;
    }
    
    private void a(final psc_f[] array, final psc_dt[] array2, final psc_cr[] array3, final psc_cr[] array4) {
        for (int i = 0; i < array2.length; ++i) {
            if (array3[i] == null) {
                array3[i] = new psc_cr();
            }
            if (array4[i] == null) {
                array4[i] = new psc_cr();
            }
            final byte[] a = this.a(i + 1);
            final psc_f5 psc_f5 = new psc_f5(a, 0, a.length);
            final psc_f4 psc_f6 = new psc_f4(((psc_e)array[i]).f().toString());
            if (array3[i].d(3) == null) {
                array3[i].a(psc_f6);
            }
            if (array3[i].d(4) == null) {
                array3[i].a(psc_f5);
            }
            if (array4[i].d(3) == null) {
                array4[i].a(psc_f6);
            }
            if (array4[i].d(4) == null) {
                array4[i].a(psc_f5);
            }
        }
        for (int j = array2.length; j < array.length; ++j) {
            final psc_cr psc_cr = new psc_cr();
            final psc_f4 psc_f7 = new psc_f4(((psc_e)array[j]).f().toString());
            if (array3[j] == null) {
                array3[j] = new psc_cr();
            }
            if (array3[j].d(3) == null) {
                array3[j].a(psc_f7);
            }
        }
    }
    
    private byte[] a(final int n) {
        return BigInteger.valueOf(n).toByteArray();
    }
    
    private boolean b(final int n) {
        for (int i = 0; i < psc_mz.f.length; ++i) {
            if (n == psc_mz.f[i]) {
                return true;
            }
        }
        return false;
    }
    
    static {
        c = new String[] { "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-3", "PBE/SHA1/RC4/PKCS12V1PBE-1-128", "PBE/SHA1/RC4/PKCS12V1PBE-1-40", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-2", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-128", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-40" };
        d = new String[] { "SHA1" };
        f = new int[] { 1, 2 };
        psc_mz.g = 1024;
    }
}
