// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.procertm;

import java.util.Hashtable;
import java.io.Writer;
import java.io.FileWriter;
import java.io.OutputStream;
import com.oroinc.text.regex.Pattern;
import com.oroinc.text.regex.PatternMatcherInput;
import com.oroinc.text.regex.Perl5Matcher;
import com.oroinc.text.regex.MalformedPatternException;
import com.oroinc.text.regex.Perl5Compiler;
import java.util.Vector;
import java.io.FileNotFoundException;
import com.progress.common.exception.ProException;
import com.progress.ubroker.util.CertLoader;
import java.io.File;
import java.util.Enumeration;
import com.progress.ubroker.ssl.CertReader;
import java.io.PrintWriter;
import java.util.Properties;

public class CertStore
{
    public static final int CERTFIELD_FILE_NAME = 0;
    public static final int CERTFIELD_SUBJ_NAME = 1;
    public static final int CERTFIELD_ISSUER_NAME = 2;
    private static final int a = 0;
    private static final int b = 1;
    private static final int c = 2;
    private static final int d = 3;
    private static final int e = 4;
    private static final int f = 5;
    private static final int g = 0;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 4;
    private static final int l = 0;
    private static final int m = 1;
    private static final int n = 2;
    private static final int o = 3;
    private static final int p = 4;
    public static final int CERTLOG_ALL = 0;
    public static final int CERTLOG_ERRORS = 1;
    public static final int CERTLOG_TERSE = 2;
    public static final int CERTLOG_VERBOSE = 3;
    public static final int CERTLOG_OPT = 4;
    public static final int CERTLOG_DEBUG = 5;
    public static final int CERTLOG_TRACE = 6;
    public static final String CERTSTORE_DEBUG = "psc.certstore.debug";
    public static final String CERTSTORE_VERBOSE = "psc.certstore.verbose";
    public static final String CERTSTORE_LOGFILE = "psc.certstore.logfile";
    public static final String CERTSTORE_LOGLEVEL = "psc.certstore.loglevel";
    protected Properties q;
    protected boolean r;
    protected boolean s;
    protected String t;
    protected String u;
    protected String v;
    protected int w;
    protected int x;
    protected int y;
    protected boolean z;
    protected int aa;
    protected PrintWriter ab;
    protected CertReader ac;
    
    public CertStore(final Properties properties, final String v) {
        this.q = null;
        this.r = false;
        this.s = false;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = false;
        this.aa = 2;
        this.ab = null;
        this.ac = null;
        this.v = v;
        this.q = new Properties(System.getProperties());
        if (null != properties) {
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String s = (String)propertyNames.nextElement();
                final String property = properties.getProperty(s);
                if (null != property) {
                    ((Hashtable<String, String>)this.q).put(s, property);
                }
            }
        }
        this.b();
    }
    
    public void openCertStore(final String s, final boolean b) throws ProcertmException {
        if (s == null) {
            throw new ProcertmException("Output file is not found");
        }
        if (!s.endsWith(".jar") && !s.endsWith(".zip")) {
            throw new IllegalArgumentException("Invalid output file: " + s);
        }
        if (this.ac != null) {
            throw new ProcertmException("Can not load more then once");
        }
        if (new File(s).exists()) {
            this.c(s);
        }
        else {
            if (!b) {
                throw new IllegalArgumentException("The file does not exist: " + s);
            }
            this.u = s;
        }
    }
    
    public void closeCertStore(final boolean b) throws ProException {
        if (b && this.z) {
            final CertWriter certWriter = new CertWriter(this.u, this.ab, this.aa);
            final Enumeration enumCertFiles = this.ac.enumCertFiles();
            while (enumCertFiles.hasMoreElements()) {
                certWriter.writeCertStore(enumCertFiles.nextElement());
            }
        }
        else if (!this.z) {
            this.a(3, "File " + this.u + " has not been changed");
        }
        else {
            this.a(1, "File " + this.u + " has not been updated, changes were lost");
        }
        this.ab.flush();
        this.ab.close();
        this.ab = null;
    }
    
    public void listDigitalCertificates() throws ProException {
        final CertLoader.CertData[] certificateFiles = this.ac.certificateFiles();
        if (this.r) {
            System.out.println(new CertWriter(null, this.ab, this.aa).getDCL(certificateFiles));
        }
        else {
            for (int i = 0; i < certificateFiles.length; ++i) {
                System.out.println(certificateFiles[i].getCertName());
            }
        }
    }
    
    public void printDigitalCertificateList(String path) throws ProException {
        if (!path.endsWith(".dcl")) {
            throw new ProcertmException("Can not print Digital Certificate List to the file " + path + ". The file is supposed to have extension .dcl");
        }
        if (this.v != null) {
            path = new File(this.v, new File(path).getName()).getPath();
        }
        final CertWriter certWriter = new CertWriter(path, this.ab, this.aa);
        final Enumeration enumCertFiles = this.ac.enumCertFiles();
        while (enumCertFiles.hasMoreElements()) {
            certWriter.writeDCL(enumCertFiles.nextElement(), true);
        }
    }
    
    public void importCertificate(final String[] array) throws ProException {
        if (array.length == 0) {
            throw new ProcertmException("Nothing to import");
        }
        final String[] a = this.a(array);
        if (a.length == 0) {
            this.a(2, "Failed to import, no file(s) found");
            return;
        }
        for (int i = 0; i < a.length; ++i) {
            final String str = a[i];
            this.a(3, "Importing file " + str);
            if (this.ac == null) {
                this.b(this.u, str);
            }
            else {
                this.a(str);
            }
        }
    }
    
    public void exportCertificate(final String[] array) throws ProException {
        this.a(3, "Exporting Certificates");
        if (array.length == 0) {
            throw new ProcertmException("Nothing to export", ProcertmException.DEBUG_INFO);
        }
        final CertLoader.CertData[] certificateFiles = this.ac.certificateFiles();
        final boolean[] array2 = new boolean[certificateFiles.length];
        for (int i = 0; i < array.length; ++i) {
            final String d = this.d(this.e(array[i]));
            boolean b = false;
            for (int j = 0; j < certificateFiles.length; ++j) {
                final String certName = certificateFiles[j].getCertName();
                if (this.a(certName, d)) {
                    b = true;
                    if (!array2[j]) {
                        String replace = certName;
                        if (null != this.v) {
                            try {
                                replace = new File(this.v, certName).getPath().replace('\\', '/');
                            }
                            catch (Exception ex) {
                                throw new ProcertmException("Error generating export file path: \n" + ex.toString());
                            }
                        }
                        new CertWriter(replace, this.ab, this.aa).writeCert(certificateFiles[j]);
                        this.a(3, "exported " + certName);
                        array2[j] = true;
                    }
                }
            }
            if (!b) {
                this.a(2, "Failed to export " + array[i] + ", file not found.");
            }
        }
    }
    
    public void removeCertificate(final String[] array) throws ProException {
        if (array == null || array.length == 0) {
            throw new ProcertmException("Nothing to remove", ProcertmException.DEBUG_INFO);
        }
        final CertLoader.CertFile[] certificateStores = this.ac.certificateStores();
        if (certificateStores == null || certificateStores.length == 0) {
            throw new ProcertmException("No or more hten one Store File", ProcertmException.FATAL_ERROR);
        }
        for (int i = 0; i < certificateStores.length; ++i) {
            final CertReader ac = this.ac;
            CertLoader.CertData[] array2 = CertReader.certificateFiles(certificateStores[i]);
            for (int j = 0; j < array.length; ++j) {
                final String d = this.d(this.e(array[j]));
                boolean b = false;
                for (int k = 0; k < array2.length; ++k) {
                    final String certName = array2[k].getCertName();
                    if (this.a(certName, d)) {
                        this.a(3, "Removed certificate file " + certName);
                        certificateStores[i].removeCertData(array2[k]);
                        b = true;
                    }
                }
                if (b) {
                    this.z = true;
                    final CertReader ac2 = this.ac;
                    array2 = CertReader.certificateFiles(certificateStores[i]);
                }
                else {
                    this.a(2, "Can not remove certificate file " + array[j] + ", the file was not found");
                }
            }
        }
    }
    
    public void convertCertificate(final String str, final String s) throws ProException {
        CertReader b;
        try {
            b = this.b(str);
        }
        catch (FileNotFoundException ex) {
            this.a(1, "Cannot import " + str + ", the file is not found");
            return;
        }
        new CertWriter(s, this.ab, this.aa).writeCert(this.a(b));
    }
    
    private String[] a(final String[] array) throws ProException {
        final Vector<String> vector = new Vector<String>();
        if (array.length == 0) {
            throw new ProcertmException("Nothing to import");
        }
        final File file = new File((this.v == null) ? "." : this.v);
        if (!file.isDirectory()) {
            throw new ProcertmException("Is not a directory: " + file, ProcertmException.DEBUG_INFO);
        }
        for (int i = 0; i < array.length; ++i) {
            final String e = this.e(array[i]);
            String substring = ".";
            int endIndex = e.lastIndexOf(47);
            if (-1 == endIndex) {
                endIndex = e.lastIndexOf(92);
            }
            String str;
            if (-1 != endIndex) {
                substring = e.substring(0, endIndex);
                str = this.d(e.substring(endIndex + 1));
            }
            else {
                str = this.d(e);
            }
            final File file2 = new File(file, substring);
            if (!file2.isDirectory()) {
                if (1 >= array.length) {
                    throw new ProcertmException("Is not a directory: " + file2, ProcertmException.DEBUG_INFO);
                }
                this.a(1, "Is not a directory: " + file2);
            }
            else {
                this.a(5, "Scanning directory " + file2 + " for " + str);
                final String[] list = file2.list();
                boolean b = false;
                for (int j = 0; j < list.length; ++j) {
                    if (list[j].endsWith(".pem") || list[j].endsWith(".crt") || list[j].endsWith(".cer") || list[j].endsWith(".txt") || list[j].endsWith(".0")) {
                        final File file3 = new File(file2, list[j]);
                        if (file3.isFile() && this.a(file3.getName(), str)) {
                            String substring2;
                            try {
                                substring2 = file3.getCanonicalPath().substring(file.getCanonicalPath().length() + 1);
                            }
                            catch (Exception ex) {
                                this.a(1, "Internal error in building path " + this.v + "/" + file3.getPath());
                                break;
                            }
                            final String replace = substring2.replace('\\', '/');
                            this.a(5, "Adding path entry " + replace + " to import collection");
                            vector.add(replace);
                            b = true;
                        }
                    }
                }
                if (!b) {
                    this.a(2, "Cannot find any certificates for " + array[i]);
                }
            }
        }
        return vector.toArray(new String[0]);
    }
    
    private boolean a(final String s, final String str) throws ProException {
        final Perl5Compiler perl5Compiler = new Perl5Compiler();
        Pattern compile;
        try {
            compile = perl5Compiler.compile(str);
        }
        catch (MalformedPatternException ex) {
            this.a(1, "MalformedPatternException " + ex.getMessage());
            throw new ProcertmException("Malformed Pattern " + str);
        }
        return new Perl5Matcher().matches(new PatternMatcherInput(s), compile);
    }
    
    private void a(final String str) throws ProException {
        final CertLoader.CertFile a = this.a();
        CertReader b;
        try {
            b = this.b(str);
        }
        catch (FileNotFoundException ex) {
            this.a(1, "Cannot import " + str + ", the file is not found");
            return;
        }
        final CertLoader.CertData[] certificateFiles = this.ac.certificateFiles();
        for (int i = 0; i < certificateFiles.length; ++i) {
            if (new File(certificateFiles[i].getCertName().toString()).getName().equals(new File(str).getName())) {
                this.a(1, "Can not load " + str + " , entry with this name already exist");
                return;
            }
        }
        a.addCertData(this.a(b));
        this.z = true;
    }
    
    private CertLoader.CertData a(final CertReader certReader) throws ProException {
        if (certReader == null) {
            return null;
        }
        final CertLoader.CertData[] certificateFiles = certReader.certificateFiles();
        if (certificateFiles == null || certificateFiles.length == 0) {
            throw new ProcertmException("No Certificate File found", ProcertmException.FATAL_ERROR);
        }
        if (certificateFiles.length != 1) {
            throw new ProcertmException("More then one Certificate File found", ProcertmException.FATAL_ERROR);
        }
        return certificateFiles[0];
    }
    
    private CertLoader.CertFile a() throws ProException {
        return this.b(this.ac);
    }
    
    private CertLoader.CertFile b(final CertReader certReader) throws ProException {
        if (certReader == null) {
            return null;
        }
        final CertLoader.CertFile[] certificateStores = certReader.certificateStores();
        if (certificateStores == null || certificateStores.length == 0) {
            throw new ProcertmException("No Certificate Store File found", ProcertmException.FATAL_ERROR);
        }
        if (certificateStores.length != 1) {
            throw new ProcertmException("More then one Certificate Store File found", ProcertmException.FATAL_ERROR);
        }
        return certificateStores[0];
    }
    
    private void b() {
        final String property = this.q.getProperty("psc.certstore.debug");
        if (null != property && property.equalsIgnoreCase("true")) {
            this.s = true;
        }
        final String property2 = this.q.getProperty("psc.certstore.verbose");
        if (null != property2 && property2.equalsIgnoreCase("true")) {
            this.r = true;
        }
        final String property3 = this.q.getProperty("psc.certstore.logfile");
        if (null != property3) {
            if (property3.equalsIgnoreCase("stdout")) {
                try {
                    this.ab = new PrintWriter(System.out);
                }
                catch (Exception ex) {}
            }
            else {
                try {
                    this.ab = new PrintWriter(new FileWriter(property3));
                }
                catch (Exception ex2) {}
            }
        }
        final String property4 = this.q.getProperty("psc.certstore.loglevel");
        if (null != property4) {
            try {
                this.aa = Integer.parseInt(property4);
            }
            catch (Exception ex3) {
                this.a(2, "Invalid logging level value, ignored.");
            }
        }
        else {
            if (this.r) {
                this.aa = 3;
            }
            if (this.s) {
                this.aa = 5;
            }
        }
    }
    
    private CertReader b(final String s) throws FileNotFoundException, ProcertmException {
        final CertReader certReader = new CertReader();
        final boolean b = s.endsWith(".zip") || s.endsWith(".jar");
        int n = 2;
        if (this.s) {
            ++n;
        }
        if (!b && null != this.v) {
            n += 2;
        }
        final String[] array = new String[n];
        int n2 = 0;
        array[n2++] = "-f";
        if (this.s) {
            array[n2++] = "-d";
        }
        if (!b && null != this.v) {
            array[n2++] = "-r";
            array[n2++] = this.v;
        }
        array[n2++] = s;
        try {
            certReader.load(array);
        }
        catch (IllegalArgumentException ex) {
            throw new ProcertmException(ex.getMessage(), ProcertmException.FATAL_ERROR);
        }
        catch (FileNotFoundException ex2) {
            throw ex2;
        }
        catch (Exception ex3) {
            if (ex3.getMessage().startsWith("Cannot new JarFile")) {
                throw new ProcertmException("Cannot open Zip file " + s, ProcertmException.USER_INFO);
            }
            throw new ProcertmException(ex3.getMessage() + " " + s);
        }
        return certReader;
    }
    
    private void b(final String s, final String str) throws ProException {
        if (this.ac != null) {
            throw new ProcertmException("A certificate store has already been loaded, cannot load more then one certificate store file", ProcertmException.FATAL_ERROR);
        }
        this.a(3, "Creating new certificate store file " + s + " with certificate file " + str);
        if (!s.endsWith(".jar") && !s.endsWith(".zip")) {
            throw new ProcertmException("Can not create The certificate store file" + s + "must have a file extension of .jar or .zip", ProcertmException.BAD_INPUT);
        }
        if (!str.endsWith(".pem") && !str.endsWith(".crt") && !str.endsWith(".cer") && !str.endsWith(".txt") && !str.endsWith(".0")) {
            throw new ProcertmException("Not a certificate file: " + str, ProcertmException.BAD_INPUT);
        }
        CertReader b;
        try {
            b = this.b(str);
        }
        catch (FileNotFoundException ex) {
            this.a(1, "Cannot find the certificate file " + str + ", the certificate store file was not created");
            return;
        }
        new CertWriter(s, this.ab, this.aa).writeCertStore(this.b(b));
        this.c(s);
    }
    
    private void c(final String s) throws ProcertmException {
        if (!s.endsWith(".jar") && !s.endsWith(".zip")) {
            throw new ProcertmException("Can not load certificate store file" + s + ". It must have a file extension of .jar or .zip", ProcertmException.BAD_INPUT);
        }
        try {
            this.ac = this.b(s);
        }
        catch (FileNotFoundException ex) {
            throw new ProcertmException("Cannot load the certificate store " + s + ", the file was not found." + ProcertmException.BAD_INPUT);
        }
        this.u = s;
        this.a(3, "Opened Certificate Store " + s);
    }
    
    private void a(final int n, final String x) {
        if (this.ab != null && n <= this.aa && null != x) {
            this.ab.println(x);
        }
    }
    
    private String d(final String s) {
        char char1 = ' ';
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length + 20);
        for (int i = 0; i < length; ++i) {
            final char c = char1;
            char1 = s.charAt(i);
            final char c2 = (i < length - 1) ? s.charAt(i + 1) : ' ';
            if ('*' == char1 && '.' != c && ']' != c && '}' != c && '>' != c && ')' != c) {
                sb.append('.');
            }
            if ('.' == char1 && '\\' != c && '*' != c2 && '+' != c2 && '?' != c2) {
                sb.append('\\');
            }
            sb.append(char1);
        }
        return sb.toString();
    }
    
    private String e(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length + 20);
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if ('\'' != char1) {
                if ('\"' != char1) {
                    sb.append(char1);
                }
            }
        }
        return sb.toString();
    }
    
    public class CertInfo
    {
        private String m_certFileName;
        private String m_serialNumber;
        private String m_subjectName;
        private String m_issuerName;
        private String m_fromDate;
        private String m_toDate;
        
        public CertInfo(final String certFileName, final String serialNumber, final String subjectName, final String issuerName, final String fromDate, final String toDate) {
            this.m_certFileName = null;
            this.m_serialNumber = null;
            this.m_subjectName = null;
            this.m_issuerName = null;
            this.m_fromDate = null;
            this.m_toDate = null;
            this.m_certFileName = certFileName;
            this.m_serialNumber = serialNumber;
            this.m_subjectName = subjectName;
            this.m_issuerName = issuerName;
            this.m_fromDate = fromDate;
            this.m_toDate = toDate;
        }
        
        public String getCertFileName() {
            return this.m_certFileName;
        }
        
        public String getSerialNumber() {
            return this.m_serialNumber;
        }
        
        public String getSubjectName() {
            return this.m_subjectName;
        }
        
        public String getIssuerName() {
            return this.m_issuerName;
        }
        
        public String getFromDate() {
            return this.m_fromDate;
        }
        
        public String getToDate() {
            return this.m_toDate;
        }
    }
}
