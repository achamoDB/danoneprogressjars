// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.FileReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.net.URL;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.OutputStream;
import java.io.File;
import java.awt.TextArea;
import java.io.PrintWriter;
import java.util.Vector;

public abstract class CertLoader
{
    public static final int CERT_TYPE_NULL = 0;
    public static final int CERT_TYPE_PEM = 1;
    public static final int CERT_TYPE_DER = 2;
    public static final int CERT_TYPE_ZIP = 3;
    public static final int CERT_TYPE_JAR = 4;
    public static final int CERT_TYPE_DCL = 5;
    public static final int CERT_TYPE_DIRECTORY = 6;
    public static final int LOAD_SOURCE_FILE = 0;
    public static final int LOAD_SOURCE_CLASSPATH = 1;
    protected boolean m_flagDebug;
    protected boolean m_flagMicrosoftJVM;
    protected boolean m_flagIsApplet;
    protected boolean m_flagRunningInApplet;
    protected boolean m_flagIgnoreLoadErrors;
    protected boolean m_flagSearchDiskFilesOnly;
    protected boolean m_flagSearchClasspathOnly;
    protected Vector m_x509Certificates;
    protected PrintWriter m_printStream;
    protected TextArea m_textArea;
    protected CertFile m_certFile;
    protected CertData m_certData;
    protected File m_rootPath;
    
    public CertLoader() {
        this.m_flagDebug = false;
        this.m_flagMicrosoftJVM = false;
        this.m_flagIsApplet = false;
        this.m_flagRunningInApplet = false;
        this.m_flagIgnoreLoadErrors = false;
        this.m_flagSearchDiskFilesOnly = false;
        this.m_flagSearchClasspathOnly = false;
        this.m_x509Certificates = new Vector();
        this.m_printStream = new PrintWriter(System.out, true);
        this.m_textArea = null;
        this.m_certFile = null;
        this.m_certData = null;
        this.m_rootPath = new File(".");
    }
    
    public void load(final String[] array) throws Exception, IllegalArgumentException, FileNotFoundException {
        if (0 == array.length) {
            throw new IllegalArgumentException("Insufficient arguments");
        }
        int n = 0;
        int n2 = 1;
        while (n2 != 0 && n < array.length) {
            if ('-' == array[n].charAt(0)) {
                if (array[n].equalsIgnoreCase("-d")) {
                    this.m_flagDebug = true;
                }
                else if (array[n].equalsIgnoreCase("-a")) {
                    this.m_flagRunningInApplet = true;
                    this.m_flagSearchClasspathOnly = true;
                    this.m_flagSearchDiskFilesOnly = false;
                }
                else if (array[n].equalsIgnoreCase("-i")) {
                    this.m_flagIgnoreLoadErrors = true;
                }
                else if (array[n].equalsIgnoreCase("-f")) {
                    this.m_flagSearchDiskFilesOnly = true;
                    this.m_flagSearchClasspathOnly = false;
                }
                else if (array[n].equalsIgnoreCase("-p")) {
                    this.m_flagSearchClasspathOnly = true;
                    this.m_flagSearchDiskFilesOnly = false;
                }
                else {
                    if (!array[n].equalsIgnoreCase("-r")) {
                        throw new IllegalArgumentException("Unknown argument: " + array[n]);
                    }
                    if (++n >= array.length) {
                        throw new IllegalArgumentException("No value supplied to -r option");
                    }
                    if ('-' == array[n].charAt(0)) {
                        throw new IllegalArgumentException("No value supplied to -r option");
                    }
                    this.m_rootPath = new File(array[n]);
                }
                ++n;
            }
            else {
                n2 = 0;
            }
        }
        if (n >= array.length) {
            throw new IllegalArgumentException("Missing certificate store name to load");
        }
        if (this.m_flagDebug) {
            this.dumpEnvironment();
        }
        if (-1 != System.getProperty("java.vendor").indexOf("Microsoft")) {
            this.m_flagMicrosoftJVM = true;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(array[n], ";");
        while (stringTokenizer.hasMoreElements()) {
            final String nextToken = stringTokenizer.nextToken();
            final File file = new File(nextToken);
            boolean b = false;
            boolean resource = false;
            boolean b2 = false;
            File file2;
            if (file.isAbsolute()) {
                file2 = file;
            }
            else {
                file2 = new File(this.m_rootPath, nextToken);
            }
            if (!this.m_flagRunningInApplet && !this.m_flagSearchClasspathOnly) {
                b2 = (file.exists() & file.isDirectory());
            }
            if (!b2) {
                if (!this.m_flagSearchClasspathOnly) {
                    if (this.m_flagDebug && this.m_flagDebug) {
                        this.println("Testing file access: " + nextToken);
                    }
                    b = (file2.exists() & file2.isFile());
                }
                if (!b && !this.m_flagSearchDiskFilesOnly) {
                    resource = this.isResource(nextToken);
                }
            }
            boolean b3;
            if (resource) {
                b3 = true;
            }
            else {
                b3 = false;
            }
            if (!b && !resource && !b2) {
                if (!this.m_flagIgnoreLoadErrors) {
                    throw new FileNotFoundException("The load path entry can't be found : " + nextToken);
                }
                if (!this.m_flagDebug) {
                    continue;
                }
                this.println("The load path entry can't be found : " + nextToken);
            }
            else {
                try {
                    if (nextToken.endsWith(".jar")) {
                        this.m_certFile = new CertFile(nextToken, 4, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        if (this.m_flagMicrosoftJVM) {
                            if (b) {
                                this.loadFromZipFile(file2.getPath());
                            }
                            else {
                                this.loadFromZipResource(nextToken);
                            }
                        }
                        else if (b) {
                            this.loadFromZipFile(file2.getPath());
                        }
                        else {
                            this.loadFromZipResource(nextToken);
                        }
                    }
                    else if (nextToken.endsWith(".zip")) {
                        this.m_certFile = new CertFile(nextToken, 3, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        if (b) {
                            this.loadFromZipFile(file2.getPath());
                        }
                        else {
                            this.loadFromZipResource(nextToken);
                        }
                    }
                    else if (nextToken.endsWith(".pem") || nextToken.endsWith(".txt") || nextToken.endsWith(".0")) {
                        this.m_certFile = new CertFile(nextToken, 1, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        this.m_certData = new CertData(nextToken);
                        this.m_certFile.addCertData(this.m_certData);
                        if (b) {
                            this.loadPemFile(file2.getPath());
                        }
                        else {
                            this.loadCertResource(nextToken);
                        }
                    }
                    else if (nextToken.endsWith(".cer") || nextToken.endsWith(".crt")) {
                        this.m_certFile = new CertFile(nextToken, 2, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        this.m_certData = new CertData(nextToken);
                        this.m_certFile.addCertData(this.m_certData);
                        if (b) {
                            this.loadBinaryFile(file2.getPath());
                        }
                        else {
                            this.loadCertResource(nextToken);
                        }
                    }
                    else if (nextToken.endsWith(".dcl")) {
                        this.m_certFile = new CertFile(nextToken, 5, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        if (b) {
                            this.loadFromManifestFile(file2.getPath());
                        }
                        else {
                            this.loadFromManifestResource(nextToken);
                        }
                    }
                    else if (b2) {
                        this.m_certFile = new CertFile(nextToken, 6, (int)(b3 ? 1 : 0));
                        this.m_x509Certificates.addElement(this.m_certFile);
                        this.loadFromDirectory(nextToken);
                    }
                    else {
                        if (!this.m_flagIgnoreLoadErrors) {
                            throw new Exception("Not a certificate store : " + nextToken);
                        }
                        this.println("Not a supported certificate store : " + nextToken);
                    }
                }
                catch (Exception ex) {
                    if (!this.m_flagIgnoreLoadErrors) {
                        throw ex;
                    }
                    continue;
                }
            }
        }
    }
    
    public void setLogStream(final PrintWriter printStream) throws Exception {
        if (null == printStream) {
            this.m_printStream = new PrintWriter(System.out, true);
        }
        else {
            this.m_printStream = printStream;
        }
    }
    
    public void setLogStream(final TextArea textArea) throws Exception {
        this.m_textArea = textArea;
    }
    
    public static void usage() {
        System.out.println("");
        System.out.println("usage: loadpermcerts <options> <path>");
        System.out.println("   <options> load options");
        System.out.println("   -d               print debug information");
        System.out.println("   -a               running in an Applet context");
        System.out.println("   -i               ignore load errors");
        System.out.println("   -f               load from disk files only");
        System.out.println("   -p               load from Java CLASSPATH only");
        System.out.println("   <path> certificate store load path ( entry[;entry[...]] ) ");
        System.out.println("        xxxx.jar    load pem files from a single jar file.");
        System.out.println("        xxxx.zip    load pem files from a single zip file.");
        System.out.println("        xxxx.pem    load single pem certificate file.");
        System.out.println("        xxxx.0      load single pem certificate file.");
        System.out.println("        xxxx.cer    load single binary certificate file.");
        System.out.println("        xxxx.crt    load single binary certificate file.");
        System.out.println("        xxxx.dcl    load single certificate files from a");
        System.out.println("                    digital-certificate-list formatted file");
        System.out.println("        (directory) load single certificate files from a");
        System.out.println("                    disk directory");
        System.out.println("");
    }
    
    protected void println(final String s) {
        if (!this.m_flagRunningInApplet) {
            try {
                this.m_printStream.println("[CertLoader] " + s);
            }
            catch (Exception ex) {}
        }
        else if (null != this.m_textArea) {
            this.m_textArea.append("[CertLoader] ");
            this.m_textArea.append(s);
            this.m_textArea.append("\n");
            this.m_textArea.repaint();
        }
    }
    
    protected abstract void createAndStoreBinaryCertificate(final byte[] p0) throws Exception;
    
    public int numberOfCertificates() {
        if (null != this.m_x509Certificates) {
            int n = 0;
            final Enumeration<CertFile> elements = this.m_x509Certificates.elements();
            while (elements.hasMoreElements()) {
                final Enumeration enumCertData = elements.nextElement().enumCertData();
                while (enumCertData.hasMoreElements()) {
                    n += enumCertData.nextElement().getCertCount();
                }
            }
            return n;
        }
        return 0;
    }
    
    public Enumeration enumCertFiles() {
        return this.m_x509Certificates.elements();
    }
    
    protected boolean readBinaryCert(final BufferedInputStream bufferedInputStream) throws Exception, IOException, InstantiationException {
        int n = 0;
        final byte[] b = new byte[4096];
        while (0 != bufferedInputStream.available()) {
            final int read = bufferedInputStream.read(b, n, b.length - n);
            if (read <= 0) {
                throw new IOException("Error reading 0 bytes from file before EOL");
            }
            n += read;
        }
        if (this.m_flagDebug) {
            this.println("  Loaded " + n + " bytes of certificate data.");
        }
        final byte[] array = new byte[n];
        System.arraycopy(b, 0, array, 0, n);
        boolean b2;
        try {
            this.createAndStoreBinaryCertificate(array);
            b2 = true;
        }
        catch (Exception ex) {
            throw ex;
        }
        return b2;
    }
    
    protected boolean readPemCert(final BufferedReader bufferedReader) throws Exception, EOFException {
        int n = 0;
        int i = 1;
        while (i != 0) {
            boolean b = false;
            final boolean b2 = true;
            while (b2) {
                final String line = bufferedReader.readLine();
                if (null == line) {
                    i = 0;
                    break;
                }
                if (-1 != line.indexOf("-BEGIN CERTIFICATE-") || -1 != line.indexOf("-BEGIN X509 CERTIFICATE-")) {
                    if (this.m_flagDebug) {
                        this.println(line);
                    }
                    b = true;
                    break;
                }
            }
            if (b) {
                final StringBuffer sb = new StringBuffer(4096);
                boolean b3 = false;
                while (b2) {
                    final String line2 = bufferedReader.readLine();
                    if (null == line2) {
                        throw new EOFException("Unexpected end of file");
                    }
                    if (this.m_flagDebug) {
                        this.println(line2);
                    }
                    if (-1 != line2.indexOf("-END CERTIFICATE-") || -1 != line2.indexOf("-END X509 CERTIFICATE-")) {
                        b3 = true;
                        break;
                    }
                    sb.append(line2);
                }
                if (!b3) {
                    continue;
                }
                final Base64 base64 = new Base64();
                try {
                    if (this.m_flagDebug) {
                        this.println("Converting PEM to binary...");
                    }
                    this.readBinaryCert(new BufferedInputStream(new ByteArrayInputStream(Base64.decode(sb.toString()))));
                    ++n;
                }
                catch (Exception ex) {
                    if (!this.m_flagIgnoreLoadErrors) {
                        throw new Exception("Cannot convert PEM certificate to binary : " + ex.getMessage());
                    }
                    this.println("Cannot convert PEM certificate to binary : " + ex.getMessage());
                }
            }
        }
        return 0 < n;
    }
    
    protected void loadBinaryFile(final String str) throws Exception {
        if (!new File(str).exists()) {
            throw new Exception("Error: Could not find file: " + str);
        }
        FileInputStream in;
        BufferedInputStream bufferedInputStream;
        try {
            in = new FileInputStream(str);
            bufferedInputStream = new BufferedInputStream(in);
        }
        catch (Exception ex) {
            throw new Exception("Error accessing " + str + " : " + ex.getMessage());
        }
        if (this.m_flagDebug) {
            this.println("Reading from file: " + str);
        }
        final boolean binaryCert = this.readBinaryCert(bufferedInputStream);
        in.close();
        if (!binaryCert) {
            throw new Exception("No certificate found");
        }
    }
    
    protected void loadPemFile(final String str) throws Exception {
        if (!new File(str).exists()) {
            throw new Exception("Error: Could not find file: " + str);
        }
        FileInputStream in;
        BufferedReader bufferedReader;
        try {
            in = new FileInputStream(str);
            bufferedReader = new BufferedReader(new InputStreamReader(in));
        }
        catch (Exception ex) {
            throw new Exception("Error accessing " + str + " : " + ex.getMessage());
        }
        if (this.m_flagDebug) {
            this.println("Reading from file: " + str);
        }
        try {
            final boolean pemCert = this.readPemCert(bufferedReader);
            in.close();
            if (!pemCert) {
                if (!this.m_flagIgnoreLoadErrors) {
                    throw new Exception("No certificate found in PEM file");
                }
                this.println("No certificate found in PEM file");
            }
        }
        catch (Exception ex2) {
            in.close();
            if (!this.m_flagIgnoreLoadErrors) {
                throw ex2;
            }
        }
    }
    
    protected boolean loadCertResource(final String str) throws Exception {
        boolean loadCertStream = false;
        InputStream resourceStream;
        try {
            resourceStream = this.getResourceStream(str);
            if (null == resourceStream) {
                throw new Exception("Could not find certificate resource entry: " + str);
            }
        }
        catch (Exception ex) {
            throw new Exception("Cannot get certificate resource stream : " + ex.toString());
        }
        try {
            loadCertStream = this.loadCertStream(str, resourceStream);
        }
        catch (Exception ex2) {
            if (!this.m_flagIgnoreLoadErrors) {
                throw ex2;
            }
            this.println(ex2.getMessage());
        }
        return loadCertStream;
    }
    
    protected boolean loadCertStream(final String s, final InputStream inputStream) throws Exception {
        boolean b = true;
        if (s.endsWith(".pem") || s.endsWith(".0") || s.endsWith(".txt")) {
            if (this.m_flagDebug) {
                this.println("Loading certificate stream for " + s);
            }
            InputStreamReader in;
            try {
                in = new InputStreamReader(inputStream);
            }
            catch (Exception ex2) {
                throw new Exception("Cannot get input stream reader...");
            }
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(in);
            }
            catch (Exception ex3) {
                throw new Exception("Cannot get buffered reader...");
            }
            this.m_certData = new CertData(s);
            this.m_certFile.addCertData(this.m_certData);
            b = this.readPemCert(bufferedReader);
        }
        else if (s.endsWith(".cer") || s.endsWith(".crt")) {
            if (this.m_flagDebug) {
                this.println("Loading certificate stream for " + s);
            }
            BufferedInputStream bufferedInputStream;
            try {
                bufferedInputStream = new BufferedInputStream(inputStream);
            }
            catch (Exception ex) {
                throw new Exception("Couldn't access " + s + " : " + ex.getMessage());
            }
            this.m_certData = new CertData(s);
            this.m_certFile.addCertData(this.m_certData);
            b = this.readBinaryCert(bufferedInputStream);
        }
        else if (this.m_flagDebug) {
            this.println("Skipping certificate stream " + s);
        }
        return b;
    }
    
    protected void loadFromZipResource(final String str) throws Exception {
        final Class<? extends CertLoader> class1 = this.getClass();
        try {
            if (this.m_flagDebug) {
                this.println("Searching for Zip file resource " + str + "...");
            }
            if (null == class1) {
                throw new Exception("Cannot obtain class loader");
            }
            URL resourceURL;
            try {
                resourceURL = this.getResourceURL(str);
            }
            catch (Exception ex) {
                throw new Exception("Could not get the Zip file resource: " + str + " : " + ex.toString());
            }
            if (null == resourceURL) {
                throw new Exception("Could not find " + str + " in the classpath");
            }
            String s = resourceURL.getFile();
            if (s.startsWith("/FILE")) {
                s = resourceURL.getFile().substring("/FILE".length());
            }
            else if (s.startsWith("/")) {
                s = resourceURL.getFile().substring(1);
            }
            if (-1 != s.indexOf(43)) {
                s = s.replace('+', '.');
            }
            this.loadFromZipFile(s);
        }
        catch (Exception ex2) {
            throw new Exception("loadFromZipResource(): " + ex2.getMessage());
        }
    }
    
    protected void loadFromZipFile(final String pathname) throws Exception {
        int i = 0;
        final String s = new String(System.getProperty("java.class.path") + ";" + pathname);
        if (this.m_flagDebug) {
            this.println("Loading from Zip file: " + pathname);
        }
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(new File(pathname));
        }
        catch (Exception ex) {
            throw new Exception("Can't new ZipFile : " + ex.getMessage());
        }
        try {
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            int j = 0;
            while (entries.hasMoreElements()) {
                final ZipEntry entry = (ZipEntry)entries.nextElement();
                final String name = entry.getName();
                InputStream inputStream = null;
                if (-1 != name.indexOf("META-INF")) {
                    continue;
                }
                if (this.m_flagDebug) {
                    this.println("**Loading cert: " + name);
                }
                boolean loadCertStream;
                try {
                    inputStream = zipFile.getInputStream(entry);
                    loadCertStream = this.loadCertStream(name, inputStream);
                    inputStream.close();
                }
                catch (Exception ex2) {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                    throw ex2;
                }
                if (!loadCertStream) {
                    throw new Exception("Couldn't read zip entry: " + entry.getName());
                }
                ++i;
                ++j;
            }
            if (this.m_flagDebug) {
                this.println("The Zip file had " + j + " entries");
                this.println("Successfully read " + i + " entries");
            }
        }
        catch (Exception ex3) {
            throw ex3;
        }
    }
    
    protected void loadFromDirectory(final String s) throws Exception {
        final File parent = new File(s);
        final String[] list = parent.list();
        if (this.m_flagDebug) {
            this.println("Loading from directory : " + s);
        }
        for (int i = 0; i < list.length; ++i) {
            final File file = new File(parent, list[i]);
            try {
                this.m_certData = new CertData(file.getPath());
                this.m_certFile.addCertData(this.m_certData);
                if (this.isPemCertFile(file)) {
                    this.loadPemFile(file.getPath());
                }
                if (this.isBinaryCertFile(file)) {
                    this.loadBinaryFile(file.getPath());
                }
            }
            catch (Exception ex) {
                if (!this.m_flagIgnoreLoadErrors) {
                    throw ex;
                }
                this.println(ex.toString());
            }
        }
    }
    
    protected void loadFromManifestFile(final String s) throws Exception {
        final File file = new File("./", s);
        final File file2 = new File(file.getParent());
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        if (this.m_flagDebug) {
            this.println("Loading from digital-certificate-list (.dcl) file : " + s);
        }
        try {
            this.loadManifest(bufferedReader, file2);
            bufferedReader.close();
        }
        catch (Exception ex) {
            if (null != bufferedReader) {
                bufferedReader.close();
            }
            if (!this.m_flagIgnoreLoadErrors) {
                throw ex;
            }
            this.println(ex.toString());
        }
    }
    
    protected void loadFromManifestResource(final String str) throws Exception {
        InputStream resourceStream = null;
        if (this.m_flagDebug) {
            this.println("Loading from Manifest resource : " + str);
        }
        try {
            resourceStream = this.getResourceStream(str);
            this.loadManifest(new BufferedReader(new InputStreamReader(resourceStream)), null);
            resourceStream.close();
        }
        catch (Exception ex) {
            if (null != resourceStream) {
                resourceStream.close();
            }
            if (!this.m_flagIgnoreLoadErrors) {
                throw ex;
            }
            this.println(ex.toString());
        }
    }
    
    protected void loadManifest(final BufferedReader bufferedReader, final File file) throws Exception {
        int i = 1;
        final boolean b = null != file;
        String line = null;
        final int length = "Name: ".length();
        try {
            while (i != 0) {
                final boolean b2 = true;
                boolean b3 = false;
                while (b2) {
                    line = bufferedReader.readLine();
                    if (null == line) {
                        i = 0;
                        break;
                    }
                    final int length2 = line.length();
                    if (0 < length2 && line.startsWith("Name: ") && length < length2) {
                        b3 = true;
                        break;
                    }
                }
                if (b3) {
                    final String substring = line.substring(length);
                    if (substring.endsWith(".cer") || substring.endsWith(".crt")) {
                        if (b) {
                            final File file2 = new File(file, substring);
                            this.m_certData = new CertData(file2.getPath());
                            this.m_certFile.addCertData(this.m_certData);
                            this.loadBinaryFile(file2.getPath());
                        }
                        else {
                            this.m_certData = new CertData(substring);
                            this.m_certFile.addCertData(this.m_certData);
                            this.loadCertResource(substring);
                        }
                    }
                    else if (substring.endsWith(".pem") || substring.endsWith(".0")) {
                        if (b) {
                            final File file3 = new File(file, substring);
                            this.m_certData = new CertData(file3.getPath());
                            this.m_certFile.addCertData(this.m_certData);
                            this.loadPemFile(file3.getPath());
                        }
                        else {
                            this.m_certData = new CertData(substring);
                            this.m_certFile.addCertData(this.m_certData);
                            this.loadCertResource(substring);
                        }
                    }
                    else {
                        if (!this.m_flagDebug) {
                            continue;
                        }
                        this.println("Skipping manifest entry : " + substring);
                    }
                }
            }
        }
        catch (Exception ex) {
            if (!this.m_flagIgnoreLoadErrors) {
                throw ex;
            }
            this.println(ex.toString());
        }
    }
    
    protected boolean isPemCertFile(final File file) {
        return file.isFile() && (file.getName().endsWith(".pem") || file.getName().endsWith(".0"));
    }
    
    protected boolean isBinaryCertFile(final File file) {
        return file.isFile() && (file.getName().endsWith(".cer") || file.getName().endsWith(".crt"));
    }
    
    protected boolean isResource(final String str) {
        boolean b = false;
        try {
            final InputStream resourceStream = this.getResourceStream(str);
            if (null != resourceStream) {
                resourceStream.close();
                b = true;
            }
            else if (this.m_flagDebug) {
                this.println("The file " + str + " is not a CLASSPATH resource");
            }
        }
        catch (Exception ex) {}
        return b;
    }
    
    protected InputStream getResourceStream(final String s) {
        InputStream inputStream = null;
        try {
            if (null != s && 0 < s.length()) {
                if (this.m_flagMicrosoftJVM && !s.startsWith("/")) {
                    final StringBuffer sb = new StringBuffer();
                    sb.append("/");
                    sb.append(s);
                    if (this.m_flagDebug) {
                        this.println("Using Microsoft resource loader for: " + sb.toString());
                    }
                    inputStream = this.getClass().getResourceAsStream(sb.toString());
                }
                else {
                    if (this.m_flagDebug) {
                        this.println("Using Java resource loader for: " + s);
                    }
                    inputStream = this.getClass().getClassLoader().getResourceAsStream(s);
                }
            }
        }
        catch (Exception ex) {
            if (this.m_flagDebug) {
                this.println("The resource stream " + s + " could not be obtained.");
            }
        }
        return inputStream;
    }
    
    protected URL getResourceURL(final String s) {
        URL url = null;
        try {
            if (null != s && 0 < s.length()) {
                if (this.m_flagMicrosoftJVM && !s.startsWith("/")) {
                    final StringBuffer sb = new StringBuffer();
                    sb.append("/");
                    sb.append(s);
                    if (this.m_flagDebug) {
                        this.println("Getting URL to Microsoft resource : " + sb.toString());
                    }
                    url = this.getClass().getResource(sb.toString());
                }
                else {
                    if (this.m_flagDebug) {
                        this.println("Getting Java URL resource: " + s);
                    }
                    url = this.getClass().getClassLoader().getResource(s);
                }
            }
        }
        catch (Exception ex) {
            if (this.m_flagDebug) {
                this.println("The resource URL for " + s + " could not be obtained.");
            }
        }
        return url;
    }
    
    protected void dumpEnvironment() {
        if (this.m_flagDebug) {
            this.println("Using environment:");
            this.println("    OS arch   : " + System.getProperty("os.arch"));
            this.println("    OS name   : " + System.getProperty("os.name"));
            this.println("    OS version: " + System.getProperty("os.version"));
            this.println("    Vendor    : " + System.getProperty("java.vendor"));
            this.println("    IsApplet  : " + this.m_flagRunningInApplet);
            final StringBuffer sb = new StringBuffer();
            if (!this.m_flagSearchClasspathOnly && !this.m_flagSearchDiskFilesOnly) {
                sb.append("Files & CLASSPATH");
            }
            else {
                if (this.m_flagSearchClasspathOnly) {
                    sb.append("CLASSPATH ");
                }
                if (this.m_flagSearchDiskFilesOnly) {
                    sb.append("Files");
                }
            }
            if (!this.m_flagRunningInApplet) {
                try {
                    this.println("    CLASSPATH : " + System.getProperty("java.class.path"));
                }
                catch (Exception ex) {}
            }
            this.println("");
        }
    }
    
    public class CertData
    {
        protected String m_certName;
        protected Vector m_certs;
        protected Enumeration m_certEnum;
        
        CertData(final String certName) {
            this.m_certName = null;
            this.m_certs = new Vector();
            this.m_certEnum = null;
            this.m_certName = certName;
        }
        
        protected void finalize() throws Throwable {
            this.m_certName = null;
            this.m_certs.removeAllElements();
        }
        
        public String getCertName() {
            return this.m_certName;
        }
        
        public int getCertCount() {
            return this.m_certs.size();
        }
        
        public void addCert(final Object obj) {
            this.m_certs.addElement(obj);
        }
        
        public Enumeration enumCerts() {
            return this.m_certEnum = this.m_certs.elements();
        }
        
        public boolean hasMoreElements() {
            return this.m_certEnum.hasMoreElements();
        }
        
        public Object nextElement() {
            return this.m_certEnum.nextElement();
        }
    }
    
    public class CertFile
    {
        protected String m_loadPath;
        protected int m_certType;
        protected int m_loadSource;
        protected Vector m_certData;
        protected Enumeration m_certEnum;
        
        CertFile(final String loadPath, final int certType, final int loadSource) {
            this.m_loadPath = null;
            this.m_certType = 0;
            this.m_loadSource = 0;
            this.m_certData = new Vector();
            this.m_certEnum = null;
            this.m_loadPath = loadPath;
            this.m_certType = certType;
            this.m_loadSource = loadSource;
        }
        
        protected void finalize() throws Throwable {
            this.m_loadPath = null;
            this.m_certData.removeAllElements();
        }
        
        public void addCertData(final CertData obj) {
            this.m_certData.addElement(obj);
        }
        
        public boolean removeCertData(final CertData obj) {
            this.m_certEnum = null;
            return this.m_certData.removeElement(obj);
        }
        
        public String getLoadPath() {
            return this.m_loadPath;
        }
        
        public int getCertFileType() {
            return this.m_certType;
        }
        
        public Enumeration enumCertData() {
            return this.m_certEnum = this.m_certData.elements();
        }
        
        public boolean hasMoreElements() {
            return this.m_certEnum.hasMoreElements();
        }
        
        public Object nextElement() {
            return this.m_certEnum.nextElement();
        }
    }
}
