// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.tools;

import java.net.InetAddress;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.util.AppService;
import com.progress.common.util.Getopt;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import com.progress.common.text.QuotedString;
import java.io.File;
import com.progress.common.log.ProLog;
import com.progress.chimera.adminserver.AdminServerType;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsFile;
import com.progress.mf.runtime.ManagementProperties;
import com.progress.message.amMsg;
import com.progress.common.util.ICmdConst;

public class ConfigurationTool implements IConfigToolConst, ICmdConst, amMsg
{
    public boolean VERBOSE;
    public boolean m_query;
    public boolean m_help;
    public boolean m_enable;
    public boolean m_disable;
    public boolean m_update;
    public boolean m_debug;
    public boolean m_isDSEnabled;
    public String m_domainName;
    public String m_containerId;
    public String m_containerName;
    public String m_hostName;
    public String m_portNumber;
    public String m_userName;
    public String m_password;
    public String m_fathomExtDir;
    public String[] REPLACEWORDS;
    public int exitStatus;
    public static String m_mgtPropFile;
    public static ManagementProperties m_properties;
    private static final boolean IS_WINDOWS;
    
    public static void main(final String[] array) {
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        }
        catch (PromsgsFile.PromsgsFileIOException ex) {
            System.out.println("There is a problem with the Progress Installation, promsgs cannot be found");
        }
        final ConfigurationTool configurationTool = new ConfigurationTool();
        boolean b = configurationTool.loadProperties();
        if (b) {
            b = configurationTool.configure(array);
        }
        System.exit(b ? 0 : 1);
    }
    
    public ConfigurationTool() {
        this.VERBOSE = false;
        this.m_query = false;
        this.m_help = false;
        this.m_enable = false;
        this.m_disable = false;
        this.m_update = false;
        this.m_debug = false;
        this.m_isDSEnabled = false;
        this.m_domainName = "Fathom1";
        this.m_containerId = null;
        this.m_containerName = null;
        this.m_hostName = null;
        this.m_portNumber = "6835";
        this.m_userName = "Administrator";
        this.m_password = "Administrator";
        this.m_fathomExtDir = null;
        this.REPLACEWORDS = null;
        this.exitStatus = 0;
    }
    
    public boolean configure(final String[] array) {
        boolean b = false;
        if (array.length == 0) {
            this.printUsage();
            return false;
        }
        System.out.println("");
        this.exitStatus = this.validateCommandLineArguments(array, AdminServerType.isDSEnabled());
        if (this.exitStatus != 0) {
            if (this.m_help) {
                this.printUsage();
            }
            return false;
        }
        if (this.m_debug) {
            System.out.println(ProLog.format(7020267394140482794L));
        }
        if (this.m_enable || this.m_update) {
            String s = null;
            final boolean fathomEnabled = AdminServerType.getFathomEnabled();
            b = this.validateJavaVersion();
            if (b && this.m_isDSEnabled && fathomEnabled) {
                s = AdminServerType.getFathomInstallDir();
                if (!s.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
                    s += IConfigToolConst.FILE_SEPARATOR;
                }
                if (!new File(s).exists()) {
                    b = false;
                    final String string = System.getProperty("Install.Dir") + IConfigToolConst.FILE_SEPARATOR + "fathom.init.params";
                    System.out.println(ProLog.format(7020267394140482799L));
                    System.out.println(ProLog.format(7020267394140486541L, string));
                    System.out.println("");
                }
                else {
                    this.m_fathomExtDir = s + "jars" + IConfigToolConst.FILE_SEPARATOR + "ext" + IConfigToolConst.FILE_SEPARATOR;
                }
            }
            if (b) {
                b = this.validateInstallDir();
            }
            if (b) {
                final boolean checkForJars = this.checkForJars();
                if (checkForJars) {
                    System.out.println(ProLog.format(7020267394140482801L));
                }
                if (!checkForJars) {
                    System.out.print(ProLog.format(7020267394140482803L));
                    if (this.m_isDSEnabled) {
                        String pathname = System.getProperty("SONICMQ_SETUP_DIR");
                        if (pathname == null) {
                            pathname = s + "sonic_setup";
                            final File file = new File(pathname);
                            if (!file.exists()) {
                                b = file.mkdir();
                                if (!b) {
                                    System.out.println(ProLog.format(7020267394140482807L, pathname));
                                }
                            }
                        }
                        if (b) {
                            b = this.unpackAllFiles(this.m_fathomExtDir + "fmsetup.jar", pathname);
                        }
                    }
                    else {
                        b = this.unpackJars(IConfigToolConst.JAVA_EXT_DIR + "fm.jar", IConfigToolConst.JAVA_EXT_DIR);
                        if (b) {
                            b = this.unpackFile(IConfigToolConst.JAVA_EXT_DIR + "fm.jar", "MFConfigurationElements.xsd", System.getProperty("Install.Dir"));
                        }
                    }
                    if (b) {
                        System.out.println(ProLog.format(7020267394140482804L));
                    }
                }
            }
            if (b) {
                String s2 = "com.progress.mf.tools.FMClientInstaller";
                if (this.m_isDSEnabled) {
                    s2 = "com.progress.mf.tools.FMSetup";
                }
                System.out.println(ProLog.format(7020267394140482808L));
                b = this.runInstaller(s2, array);
            }
        }
        else if (this.m_disable) {
            b = this.setEnabledProperty(false);
            if (b) {
                System.out.println(ProLog.format(7020267394140482809L));
                System.out.println(" ");
            }
        }
        else if (this.m_query) {
            b = this.printConfigSettings();
        }
        if (b && !this.m_query) {
            System.out.println(ProLog.format(7020267394140482810L));
            System.out.println(" ");
        }
        return b;
    }
    
    public boolean execInstaller(final String obj, final String[] array) {
        final String property = System.getProperty("Install.Dir");
        final String obj2 = ConfigurationTool.IS_WINDOWS ? ("-Xbootclasspath/p:" + new QuotedString(IConfigToolConst.JAVA_EXT_DIR + "xercesImpl_mq61.jar")) : ("-Xbootclasspath/p:" + IConfigToolConst.JAVA_EXT_DIR + "xercesImpl_mq61.jar");
        final StringBuffer sb = new StringBuffer(256);
        for (int i = 0; i < IConfigToolConst.CLASSPATH_JARS.length; ++i) {
            sb.append(IConfigToolConst.JAVA_EXT_DIR + IConfigToolConst.CLASSPATH_JARS[i] + IConfigToolConst.PATH_SEPARATOR);
        }
        sb.append(System.getProperty("java.class.path"));
        final String string = sb.toString();
        final Vector vector = new Vector<String>();
        final String property2 = System.getProperty("os.arch");
        String string2;
        if (property2.equals("sparcv9") || property2.equals("IA64W") || property2.equals("PA_RISC2.0W")) {
            string2 = property2 + IConfigToolConst.FILE_SEPARATOR;
        }
        else {
            string2 = "";
        }
        vector.addElement(System.getProperty("java.home") + IConfigToolConst.FILE_SEPARATOR + "bin" + IConfigToolConst.FILE_SEPARATOR + string2 + "java");
        final String property3 = System.getProperty("java.jvmargs");
        if (property3 != null && property3.length() > 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property3);
            while (stringTokenizer.hasMoreElements()) {
                vector.addElement(stringTokenizer.nextToken());
            }
        }
        vector.addElement(obj2);
        vector.addElement("-Djava.class.path=" + string);
        vector.addElement("-DInstall.Dir=" + property);
        final String property4 = System.getProperty("Work.Dir");
        if (property4 != null) {
            vector.addElement("-DWork.Dir=" + property4);
        }
        vector.addElement(obj);
        for (int j = 0; j < array.length; ++j) {
            vector.addElement(array[j]);
        }
        final String[] cmdarray = new String[vector.size()];
        for (int k = 0; k < vector.size(); ++k) {
            cmdarray[k] = vector.elementAt(k);
        }
        if (this.m_debug) {
            for (int l = 0; l < cmdarray.length; ++l) {
                System.out.println("installerCmd[" + l + "] = " + cmdarray[l]);
            }
        }
        boolean b;
        try {
            final Process exec = Runtime.getRuntime().exec(cmdarray);
            this.readProcessOutput(new BufferedReader(new InputStreamReader(exec.getInputStream())));
            final int processExitStatus = this.getProcessExitStatus(exec);
            if (this.m_debug && processExitStatus != 0) {
                System.out.println(ProLog.format(7020267394140482811L, new Integer(processExitStatus)));
            }
            b = (processExitStatus == 0);
        }
        catch (IOException ex) {
            b = false;
            System.out.println(ProLog.format(7020267394140482812L));
            if (this.m_debug) {
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    public boolean runInstaller(final String s, final String[] array) {
        boolean b;
        try {
            final boolean isDSEnabled = this.m_isDSEnabled;
            System.out.print(ProLog.format(7020267394140482813L));
            if (isDSEnabled) {
                System.out.println(ProLog.format(7020267394140482814L));
                System.out.println(ProLog.format(7020267394140482816L));
                try {
                    b = this.execInstaller(s, array);
                }
                catch (Exception ex) {
                    b = false;
                    System.out.println(ProLog.format(7020267394140482817L));
                    System.out.println(ProLog.format(7020267394140482818L, s));
                    if (this.m_debug) {
                        ex.printStackTrace();
                    }
                }
            }
            else {
                System.out.println(ProLog.format(7020267394140482815L));
                System.out.println(ProLog.format(7020267394140482819L));
                b = this.execInstaller(s, array);
            }
        }
        catch (Throwable t) {
            b = false;
            System.out.println(ProLog.format(7020267394140482820L, t.getMessage()));
            if (this.m_debug) {
                t.printStackTrace();
            }
        }
        return b;
    }
    
    public void readProcessOutput(final BufferedReader bufferedReader) {
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException ex) {
            if (this.m_debug) {
                System.out.println(ProLog.format(7020267394140482821L, ex.getMessage()));
            }
        }
    }
    
    public void readProcessOutput(final BufferedReader bufferedReader, final String s) {
        this.readProcessOutput(bufferedReader, s, false);
    }
    
    public void readProcessOutput(final BufferedReader bufferedReader, final String str, final boolean b) {
        try {
            String x;
            while ((x = bufferedReader.readLine()) != null) {
                final int index;
                if (str != null && str.length() > 0 && (index = x.indexOf(str)) >= 0) {
                    x = x.substring(index);
                }
                if (b) {
                    System.out.print(".");
                }
                else {
                    System.out.println(x);
                }
            }
            if (b) {
                System.out.println();
            }
        }
        catch (Throwable t) {
            System.out.println(ProLog.format(7020267394140482821L, t.getMessage()));
        }
    }
    
    public int getProcessExitStatus(final Process process) {
        Integer n = null;
        while (n == null) {
            int i = 0;
            while (i < 6) {
                try {
                    n = new Integer(process.exitValue());
                }
                catch (IllegalThreadStateException ex) {
                    if (this.m_debug) {
                        System.out.println(ProLog.format(7020267394140482822L));
                    }
                    try {
                        Thread.sleep(5000L);
                    }
                    catch (InterruptedException ex2) {}
                    ++i;
                    continue;
                }
                break;
            }
            if (n == null) {
                n = new Integer(-1);
                if (!this.m_debug) {
                    continue;
                }
                System.out.println(ProLog.format(7020267394140482823L));
            }
        }
        final int intValue = n;
        if (this.m_debug && intValue != 0) {
            System.out.println(ProLog.format(7020267394140482824L, new Integer(intValue)));
        }
        return intValue;
    }
    
    public boolean copyFile(final String pathname, final String pathname2) {
        final File file = new File(pathname);
        final File file2 = new File(pathname2);
        final byte[] array = new byte[2048];
        boolean exists = file.exists();
        if (exists) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final FileOutputStream fileOutputStream = new FileOutputStream(file2);
                int read;
                while ((read = fileInputStream.read(array)) > 0) {
                    fileOutputStream.write(array, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (Exception ex) {
                exists = false;
                System.out.println(ProLog.format(7020267394140482825L, pathname, ex.getMessage()));
                if (this.m_debug) {
                    ex.printStackTrace();
                }
            }
        }
        else {
            System.out.println(ProLog.format(7020267394140482827L, pathname));
        }
        return exists;
    }
    
    public boolean deleteFile(final String s) {
        return this.deleteFiles(new String[] { s });
    }
    
    public boolean deleteFiles(final String[] array) {
        final File[] array2 = new File[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = new File(array[i]);
        }
        return deleteFiles(array2, this.m_debug);
    }
    
    public boolean deleteFiles(final File[] array) {
        return deleteFiles(array, this.m_debug);
    }
    
    public static boolean deleteFiles(final File[] array, final boolean b) {
        boolean b2 = true;
        for (int n = 0; n < array.length && b2; ++n) {
            if (array[n].exists()) {
                if (array[n].isFile()) {
                    b2 = array[n].delete();
                    if (!b2) {
                        if (!array[n].canWrite()) {
                            System.out.println(ProLog.format(7020267394140482828L));
                        }
                        System.out.println(ProLog.format(7020267394140482829L, array[n].getName()));
                    }
                }
                else if (array[n].isDirectory()) {
                    b2 = deleteDirectory(array[n], b);
                }
            }
            else if (b) {
                System.out.println(ProLog.format(7020267394140482830L, array[n].getName()));
            }
        }
        return b2;
    }
    
    private boolean deleteDirectory(final File file) {
        return deleteDirectory(file, this.m_debug);
    }
    
    public static boolean deleteDirectory(final File file, final boolean b) {
        boolean b2;
        if (file.exists() && file.isDirectory() && file.canWrite()) {
            b2 = deleteFiles(file.listFiles(), b);
            if (b2) {
                b2 = file.delete();
            }
        }
        else {
            b2 = false;
            String x = ProLog.format(7020267394140482835L, file.getName());
            if (file.exists()) {
                if (!file.isDirectory()) {
                    x = ProLog.format(7020267394140482832L, file.getName());
                }
                else if (!file.canWrite()) {
                    x = ProLog.format(7020267394140482833L, file.getName());
                }
                System.out.println(x);
            }
            else if (b) {
                System.out.println(ProLog.format(7020267394140482836L, file.getName()));
            }
        }
        if (!b2) {
            System.out.println(ProLog.format(7020267394140482835L, file.getName()));
        }
        return b2;
    }
    
    public boolean unpackJars(final String name, final String s) {
        boolean saveJarEntry = false;
        try {
            final JarFile jarFile = new JarFile(name);
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                final String name2 = jarEntry.getName();
                if (name2.endsWith(".jar")) {
                    saveJarEntry = this.saveJarEntry(jarFile, jarEntry, s);
                    if (!saveJarEntry) {
                        System.out.println(ProLog.format(7020267394140482837L, name, name2, s));
                        break;
                    }
                    continue;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7020267394140482838L, name, ex.getMessage()));
            if (this.m_debug) {
                ex.printStackTrace();
            }
        }
        return saveJarEntry;
    }
    
    public boolean unpackFile(final String name, final String name2, final String s) {
        boolean saveJarEntry = false;
        try {
            final JarFile jarFile = new JarFile(name);
            final ZipEntry entry = jarFile.getEntry(name2);
            if (entry != null) {
                saveJarEntry = this.saveJarEntry(jarFile, entry, s);
                if (!saveJarEntry) {
                    System.out.println(ProLog.format(7020267394140482837L, name, name2, s));
                }
            }
            else {
                System.out.println(ProLog.format(7020267394140482839L, name, name2));
            }
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7020267394140482838L, name, ex.getMessage()));
            if (this.m_debug) {
                ex.printStackTrace();
            }
        }
        return saveJarEntry;
    }
    
    public boolean unpackAllFiles(final String name, final String s) {
        boolean saveJarEntry = true;
        try {
            final JarFile jarFile = new JarFile(name);
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                final String name2 = jarEntry.getName();
                if (!jarEntry.isDirectory()) {
                    if (name2.endsWith("META-INF/MANIFEST.MF")) {
                        continue;
                    }
                    saveJarEntry = this.saveJarEntry(jarFile, jarEntry, s);
                }
                if (!saveJarEntry) {
                    System.out.println(ProLog.format(7020267394140482837L, name, name2, s));
                    break;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7020267394140482838L, name, ex.getMessage()));
            if (this.m_debug) {
                ex.printStackTrace();
            }
        }
        return saveJarEntry;
    }
    
    private boolean saveJarEntry(final JarFile jarFile, final ZipEntry ze, final String str) {
        boolean mkdirs = true;
        final String name = ze.getName();
        final byte[] array = new byte[2048];
        final String s = str.endsWith(IConfigToolConst.FILE_SEPARATOR) ? str : (str + IConfigToolConst.FILE_SEPARATOR);
        if (name.lastIndexOf("/") > 0) {
            final String string = s + name;
            final File file = new File(string.substring(0, string.lastIndexOf("/")));
            if (!file.exists() || !file.isDirectory()) {
                mkdirs = file.mkdirs();
            }
        }
        if (mkdirs) {
            try {
                final InputStream inputStream = jarFile.getInputStream(ze);
                final FileOutputStream fileOutputStream = new FileOutputStream(new File(s + name));
                int read;
                while ((read = inputStream.read(array)) > 0) {
                    fileOutputStream.write(array, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (Exception ex) {
                mkdirs = false;
                System.out.println(ProLog.format(7020267394140482840L, name, ex.getMessage()));
                if (this.m_debug) {
                    ex.printStackTrace();
                }
            }
        }
        return mkdirs;
    }
    
    private boolean checkForJars() {
        final String property = System.getProperty("Install.Dir");
        boolean b = false;
        if (this.m_isDSEnabled) {
            String str = AdminServerType.getFathomInstallDir();
            if (!str.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
                str += IConfigToolConst.FILE_SEPARATOR;
            }
            String s = System.getProperty("SONICMQ_SETUP_DIR");
            if (s == null) {
                s = str + "sonic_setup";
            }
            if (!s.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
                s += IConfigToolConst.FILE_SEPARATOR;
            }
            String s2 = System.getProperty("SONICMQ_INSTALL_DIR");
            if (s2 == null) {
                s2 = str + "MQ6.1";
            }
            if (!s2.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
                s2 += IConfigToolConst.FILE_SEPARATOR;
            }
            final File file = new File(s2 + "_uninst50");
            b = (file.exists() && file.isDirectory());
            if (!b) {
                final File file2 = new File(s + "setup.jar");
                b = (file2.exists() && file2.length() > 0L);
            }
        }
        else {
            int n = 0;
            for (int i = 0; i < IConfigToolConst.CLASSPATH_JARS.length; ++i) {
                final File file3 = new File(IConfigToolConst.JAVA_EXT_DIR + IConfigToolConst.CLASSPATH_JARS[i]);
                b = (file3.exists() && file3.length() > 0L);
                if (b) {
                    ++n;
                }
            }
            if (n != IConfigToolConst.CLASSPATH_JARS.length) {
                b = false;
            }
            if (b) {
                final File file4 = new File(property + IConfigToolConst.FILE_SEPARATOR + "MFConfigurationElements.xsd");
                b = (file4.exists() && file4.length() > 0L);
            }
        }
        return b;
    }
    
    public void tailorFile(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter, final String[] array, final String[] array2) throws Exception {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.length() > 0) {
                String tailorString = this.tailorString(line, array, array2);
                if (tailorString == null) {
                    tailorString = line;
                }
                bufferedWriter.write(tailorString);
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
    }
    
    public void tailorFile(final String str, final String pathname, final String[] array, final String[] array2) throws Exception {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/com/progress/mf/tools/" + str)));
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(pathname))));
        this.tailorFile(bufferedReader, bufferedWriter, array, array2);
        bufferedWriter.close();
    }
    
    public String tailorString(String string, final String[] array, final String[] array2) {
        String s = null;
        for (int i = 0; i < array.length; ++i) {
            final int index = string.indexOf(array[i]);
            if (index > 0) {
                s = (string = string.substring(0, index) + array2[i] + string.substring(index + array[i].length()));
            }
        }
        return s;
    }
    
    public int validateCommandLineArguments(final String[] array) {
        return this.validateCommandLineArguments(array, AdminServerType.isDSEnabled());
    }
    
    public int validateCommandLineArguments(final String[] array, final boolean isDSEnabled) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("domain:", 510), new Getopt.GetoptList("port:", 100), new Getopt.GetoptList("name:", 50), new Getopt.GetoptList("host:", 90), new Getopt.GetoptList("user:", 60), new Getopt.GetoptList("u:", 60), new Getopt.GetoptList("p:", 220), new Getopt.GetoptList("password:", 220), new Getopt.GetoptList("install", 520), new Getopt.GetoptList("enable", 400), new Getopt.GetoptList("disable", 410), new Getopt.GetoptList("update", 450), new Getopt.GetoptList("query", 10), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("debug", 530), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        boolean b = false;
        this.m_isDSEnabled = isDSEnabled;
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1 && n == 0) {
            switch (opt) {
                case 40: {
                    this.m_help = true;
                    n = 1;
                    continue;
                }
                case 530: {
                    this.m_debug = true;
                    continue;
                }
                case 10: {
                    n2 = 10;
                    ++n3;
                    continue;
                }
                case 400: {
                    n2 = 400;
                    ++n3;
                    continue;
                }
                case 410: {
                    n2 = 410;
                    ++n3;
                    continue;
                }
                case 450: {
                    n2 = 450;
                    ++n3;
                    continue;
                }
                case 50: {
                    this.m_containerName = getopt.getOptArg();
                    continue;
                }
                case 510: {
                    this.m_domainName = getopt.getOptArg();
                    continue;
                }
                case 60: {
                    this.m_userName = getopt.getOptArg();
                    continue;
                }
                case 220: {
                    this.m_password = getopt.getOptArg();
                    continue;
                }
                case 90: {
                    this.m_hostName = getopt.getOptArg();
                    continue;
                }
                case 100: {
                    this.m_portNumber = getopt.getOptArg();
                    continue;
                }
                case 63: {
                    b = true;
                    getopt.getOptArg();
                    continue;
                }
            }
        }
        if (n == 0) {
            switch (n2) {
                case 10: {
                    this.m_query = true;
                    break;
                }
                case 400: {
                    this.m_enable = true;
                    break;
                }
                case 410: {
                    this.m_disable = true;
                    break;
                }
                case 450: {
                    this.m_update = true;
                    break;
                }
                default: {
                    if (!this.m_help) {
                        this.printNoOperationError();
                        n = 1;
                        break;
                    }
                    break;
                }
            }
        }
        if (!this.m_help) {
            if (b) {
                System.out.println(ProLog.format(7020267394140482841L));
                n = 1;
            }
            if (n3 > 1) {
                System.out.println(" ");
                System.out.println(ProLog.format(7020267394140482842L));
                System.out.println(ProLog.format(7020267394140482843L));
                n = 1;
            }
            if (this.m_isDSEnabled && this.m_enable && this.isConfigChange()) {
                System.out.println(" ");
                System.out.println(ProLog.format(7020267394140482844L));
                this.printConfigSettings();
                n = 1;
            }
            if (!this.m_isDSEnabled && !this.m_disable && !this.m_query) {
                if (this.m_hostName == null) {
                    System.out.println(" ");
                    System.out.println(ProLog.format(7020267394140482845L));
                    n = 1;
                }
                if (this.m_containerName != null && !this.validateContainerName(this.m_containerName)) {
                    n = 1;
                }
            }
        }
        return n;
    }
    
    private boolean validateContainerName(final String s) {
        boolean b = true;
        if (s != null) {
            final Character invalidChar = this.getInvalidChar(s);
            if (invalidChar != null) {
                final String s2 = "";
                String s3;
                if (invalidChar == '\\' || invalidChar == '\"') {
                    s3 = s2 + " ' \\" + invalidChar + " '";
                }
                else {
                    s3 = s2 + " ' " + invalidChar + " '";
                }
                System.out.println(" ");
                System.out.println(ProLog.format(7020267394140482846L, s3));
                b = false;
            }
            if (s.length() > 31) {
                System.out.println(" ");
                System.out.println(ProLog.format(7020267394140482847L));
                b = false;
            }
            if (!AppService.validateName(s)) {
                System.out.println(" ");
                System.out.println(ProLog.format(7020267394140482848L));
                System.out.println(ProLog.format(7020267394140482849L));
                b = false;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    private Character getInvalidChar(final String str) {
        final StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < sb.length(); ++i) {
            final char char1 = sb.charAt(i);
            if (!Character.isJavaIdentifierPart(char1) && char1 != '-') {
                return new Character(char1);
            }
        }
        return null;
    }
    
    public void printUsage() {
        System.out.println(" ");
        System.out.println(ProLog.format(7020267394140482866L));
        System.out.println(" ");
        System.out.println(ProLog.format(7020267394140482894L));
        System.out.println(ProLog.format(7020267394140482869L));
        final String format = ProLog.format(7020267394140482871L);
        if (format != null && format.length() > 0) {
            System.out.println(format);
        }
        System.out.println(" ");
        System.out.println(ProLog.format(7020267394140482874L));
        final String format2 = ProLog.format(7020267394140482875L);
        if (format2 != null && format2.length() > 0) {
            System.out.println(format2);
        }
        System.out.println(ProLog.format(7020267394140482876L));
        final String format3 = ProLog.format(7020267394140482877L);
        if (format3 != null && format3.length() > 0) {
            System.out.println(format3);
        }
        System.out.println(ProLog.format(7020267394140482878L));
        final String format4 = ProLog.format(7020267394140482879L);
        if (format4 != null && format4.length() > 0) {
            System.out.println(format4);
        }
        System.out.println(ProLog.format(7020267394140482880L));
        final String format5 = ProLog.format(7020267394140482881L);
        if (format5 != null && format5.length() > 0) {
            System.out.println(format5);
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(ProLog.format(7020267394140482882L));
        final String format6 = ProLog.format(7020267394140482883L);
        if (format6 != null && format6.length() > 0) {
            System.out.println(format6);
        }
        System.out.println(" ");
        System.out.println(ProLog.format(7020267394140482884L));
        final String format7 = ProLog.format(7020267394140482885L);
        if (format7 != null && format7.length() > 0) {
            System.out.println(format7);
        }
        System.out.println(ProLog.format(7020267394140482886L));
        final String format8 = ProLog.format(7020267394140482887L);
        if (format8 != null && format8.length() > 0) {
            System.out.println(format8);
        }
        System.out.println(ProLog.format(7020267394140482888L));
        final String format9 = ProLog.format(7020267394140482889L);
        if (format9 != null && format9.length() > 0) {
            System.out.println(format9);
        }
        System.out.println(ProLog.format(7020267394140482890L));
        final String format10 = ProLog.format(7020267394140482891L);
        if (format10 != null && format10.length() > 0) {
            System.out.println(format10);
        }
        System.out.println(ProLog.format(7020267394140482892L));
        final String format11 = ProLog.format(7020267394140482893L);
        if (format11 != null && format11.length() > 0) {
            System.out.println(format11);
        }
        System.out.println(" ");
    }
    
    public boolean printConfigSettings() {
        ManagementProperties managementProperties = null;
        String s = System.getProperty("Management.ConfigFile");
        if (s == null || s.length() < 1) {
            s = System.getProperty("Install.Dir") + IConfigToolConst.FILE_SEPARATOR + "properties" + IConfigToolConst.FILE_SEPARATOR + "management.properties";
        }
        boolean b;
        try {
            managementProperties = new ManagementProperties(s, null);
            b = true;
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7021956244000746668L, s, ex.getMessage()));
            if (this.m_debug) {
                ex.printStackTrace();
            }
            b = false;
        }
        if (b) {
            final String s2 = managementProperties.getDirectoryService() ? "Fathom" : "Remote";
            final String s3 = managementProperties.getIsMonitored() ? "Enabled" : "Disabled";
            String domainName = managementProperties.getDomainName();
            String containerName = managementProperties.getContainerName();
            String hostName = managementProperties.getHostName();
            String value = String.valueOf(managementProperties.getPort());
            if (containerName == null) {
                domainName = (containerName = (hostName = (value = "")));
            }
            System.out.println(" ");
            System.out.println(ProLog.format(7020267394140482896L));
            System.out.println(ProLog.format(7020267394140482897L));
            final String format = ProLog.format(7020267394140482898L);
            if (format != null && format.length() > 0) {
                System.out.println(format);
            }
            System.out.println(ProLog.format(7020267394140482896L));
            System.out.println(ProLog.format(7020267394140482899L, s2));
            final String format2 = ProLog.format(7020267394140482900L);
            if (format2 != null && format2.length() > 0) {
                System.out.println(format2);
            }
            System.out.println(ProLog.format(7020267394140482902L, s3));
            final String format3 = ProLog.format(7020267394140482903L);
            if (format3 != null && format3.length() > 0) {
                System.out.println(format3);
            }
            System.out.println(ProLog.format(7020267394140482905L, domainName));
            final String format4 = ProLog.format(7020267394140482906L);
            if (format4 != null && format4.length() > 0) {
                System.out.println(format4);
            }
            System.out.println(ProLog.format(7020267394140482907L, containerName));
            final String format5 = ProLog.format(7020267394140482908L);
            if (format5 != null && format5.length() > 0) {
                System.out.println(format5);
            }
            System.out.println(ProLog.format(7020267394140482909L, hostName));
            final String format6 = ProLog.format(7020267394140482910L);
            if (format6 != null && format6.length() > 0) {
                System.out.println(format6);
            }
            System.out.println(ProLog.format(7020267394140482911L, value));
            final String format7 = ProLog.format(7020267394140482912L);
            if (format7 != null && format7.length() > 0) {
                System.out.println(format7);
            }
            System.out.println(ProLog.format(7020267394140482896L));
            System.out.println(" ");
            System.out.println(" ");
        }
        return b;
    }
    
    private void printNoOperationError() {
        System.out.println(IConfigToolConst.NEWLINE + ProLog.format(7020267394140482850L));
    }
    
    private void printValues() {
    }
    
    public boolean loadProperties() {
        ConfigurationTool.m_mgtPropFile = System.getProperty("Management.ConfigFile");
        if (ConfigurationTool.m_mgtPropFile == null || ConfigurationTool.m_mgtPropFile.length() < 1) {
            ConfigurationTool.m_mgtPropFile = System.getProperty("Install.Dir") + IConfigToolConst.FILE_SEPARATOR + "properties" + IConfigToolConst.FILE_SEPARATOR + "management.properties";
        }
        boolean b;
        try {
            ConfigurationTool.m_properties = new ManagementProperties(ConfigurationTool.m_mgtPropFile, null);
            b = true;
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7021956244000746668L, ConfigurationTool.m_mgtPropFile, ex.getMessage()));
            if (this.m_debug) {
                ex.printStackTrace();
            }
            b = false;
        }
        return b;
    }
    
    public boolean isConfigChange() {
        boolean b = false;
        if (ConfigurationTool.m_properties.getContainerName() != null) {
            if (this.m_isDSEnabled != ConfigurationTool.m_properties.getDirectoryService() || Integer.parseInt(this.m_portNumber) != ConfigurationTool.m_properties.getPort() || !this.m_domainName.equals(ConfigurationTool.m_properties.getDomainName())) {
                b = true;
            }
            if (!b && ((this.m_hostName != null && !this.m_hostName.equals(ConfigurationTool.m_properties.getHostName())) || (this.m_containerName != null && !this.m_containerName.equals(ConfigurationTool.m_properties.getContainerName())))) {
                b = true;
            }
            if (!b && (this.m_hostName == null || this.m_containerName == null) && this.m_hostName == null) {
                final String hostName = ConfigurationTool.m_properties.getHostName();
                if (hostName != null && !hostName.equals(this.getHostName())) {
                    b = true;
                }
                if (!b) {
                    final String containerName = ConfigurationTool.m_properties.getContainerName();
                    if (containerName != null && !containerName.equals("FathomAdminserver1")) {
                        b = true;
                    }
                }
            }
        }
        return b;
    }
    
    public boolean setEnabledProperty(final boolean isMonitored) {
        if (this.m_domainName != null) {
            ConfigurationTool.m_properties.setDomainName(this.m_domainName);
        }
        if (this.m_containerName != null) {
            ConfigurationTool.m_properties.setContainerName(this.m_containerName);
        }
        if (this.m_isDSEnabled && this.m_hostName == null) {
            this.m_hostName = this.getHostName();
        }
        if (this.m_hostName != null) {
            ConfigurationTool.m_properties.setHostName(this.m_hostName);
        }
        ConfigurationTool.m_properties.setPort(Integer.parseInt(this.m_portNumber));
        ConfigurationTool.m_properties.setIsDirectoryService(this.m_isDSEnabled);
        boolean setIsMonitored = ConfigurationTool.m_properties.setIsMonitored(isMonitored);
        try {
            if (setIsMonitored) {
                ConfigurationTool.m_properties.save(ConfigurationTool.m_mgtPropFile, "Management Plugin Properties File");
            }
        }
        catch (Exception ex) {
            setIsMonitored = false;
        }
        if (!setIsMonitored) {
            final String s = isMonitored ? "enable" : "disable";
            System.out.println(ProLog.format(7020267394140482851L, ConfigurationTool.m_mgtPropFile));
            System.out.println(ProLog.format(7020267394140482852L));
        }
        return setIsMonitored;
    }
    
    protected boolean copyXsdFile(String string) {
        boolean copyFile = true;
        if (string == null) {
            string = ".";
        }
        if (!string.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
            string += IConfigToolConst.FILE_SEPARATOR;
        }
        String s = System.getProperty("Work.Dir");
        if (s == null) {
            s = System.getProperty("user.dir", "." + IConfigToolConst.FILE_SEPARATOR);
        }
        if (!s.endsWith(IConfigToolConst.FILE_SEPARATOR)) {
            s += IConfigToolConst.FILE_SEPARATOR;
        }
        final String string2 = string + "MFConfigurationElements.xsd";
        final String string3 = s + "MFConfigurationElements.xsd";
        if (!new File(string3).exists()) {
            System.out.println(ProLog.format(7020267394140482853L, "MFConfigurationElements.xsd", s));
            copyFile = this.copyFile(string2, string3);
        }
        if (!copyFile) {
            System.out.println(ProLog.format(7020267394140482858L, string2, string3));
            System.out.println(ProLog.format(7020267394140482859L));
            System.out.println(ProLog.format(7020267394140482861L));
        }
        return copyFile;
    }
    
    public String getHostName() {
        String s = null;
        try {
            s = InetAddress.getLocalHost().getHostName();
            final int index = s.indexOf(".");
            if (index > 0) {
                s = s.substring(0, index);
            }
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7020267394140482862L, ex.toString()));
        }
        return s;
    }
    
    private boolean validateJavaVersion() {
        boolean b = true;
        final String anObject = "1.5";
        final String str = "1.5.0";
        final String property = System.getProperty("os.name");
        final String property2 = System.getProperty("java.version");
        final String property3 = System.getProperty("java.specification.version");
        final boolean equalsIgnoreCase = property.equalsIgnoreCase("HP-UX");
        final boolean equalsIgnoreCase2 = property.equalsIgnoreCase("OSF1");
        final boolean boolean1 = Boolean.getBoolean("ByPassVersionCheck");
        if ((equalsIgnoreCase || equalsIgnoreCase2) && !boolean1) {
            b = false;
            if (property3.equals(anObject) || property2.indexOf(str) >= 0) {
                b = true;
            }
            if (!b) {
                System.out.println(ProLog.format(7020267394140482864L, property2));
                System.out.println(ProLog.format(7020267394140482865L, property, str));
            }
        }
        return b;
    }
    
    private boolean validateInstallDir() {
        boolean b = true;
        boolean b2 = false;
        final String property = System.getProperty("java.specification.version");
        try {
            if (new Double(property) >= 1.4) {
                b2 = true;
            }
        }
        catch (Exception ex) {
            b2 = false;
        }
        final String property2 = System.getProperty("Install.Dir");
        if (!b2 && property2.indexOf(32) >= 0) {
            b = false;
            System.out.println(ProLog.format(7020267394140483353L, property2));
            System.out.println(ProLog.format(7020267394140483354L));
        }
        if (this.m_isDSEnabled) {
            final String fathomInstallDir = AdminServerType.getFathomInstallDir();
            if (!b2 && fathomInstallDir.indexOf(32) >= 0) {
                b = false;
                System.out.println(ProLog.format(7020267394140483353L, fathomInstallDir));
                System.out.println(ProLog.format(7020267394140483355L));
            }
        }
        if (!b) {
            System.out.println(ProLog.format(7020267394140483356L));
        }
        return b;
    }
    
    static {
        ConfigurationTool.m_mgtPropFile = null;
        ConfigurationTool.m_properties = null;
        IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");
    }
}
