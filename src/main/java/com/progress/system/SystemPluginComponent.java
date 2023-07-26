// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import java.rmi.RemoteException;
import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import java.io.RandomAccessFile;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.networkevents.IEventObject;
import java.net.UnknownHostException;
import com.progress.common.util.NetInfo;
import java.net.InetAddress;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.progress.common.log.ProLog;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Hashtable;
import java.util.StringTokenizer;
import com.progress.common.util.ProgressVersion;
import com.progress.common.util.Environment;
import java.util.Date;
import java.text.DateFormat;
import java.net.DatagramSocket;
import com.progress.common.util.Port;
import java.io.File;
import java.util.Vector;
import com.progress.osmetrics.OSMetricsImpl;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.mf.AbstractPluginComponent;

public class SystemPluginComponent extends AbstractPluginComponent
{
    public static final String OS_PROP_PREFIX = "TBD";
    public static final String OS_METRIC_PREFIX = "osmetrics.";
    public static final String NEWLINE;
    private static final IManagementInfo[] MANAGEMENT_INFO;
    private String m_osHost;
    private String m_osName;
    private String m_osVersion;
    private SystemPlugIn m_systemPlugin;
    public static final String FILE_FOUND = "FileFound";
    public static final String FILE_OFFSET = "FileOffset";
    public static final String FILE_OFFSETS = "FileOffsets";
    public static final String FILE_BUFFER = "FileBuffer";
    public static final String FILE_ERROR = "FileError";
    public static final String FILE_IS_EOF = "FileIsEof";
    public static final String FILE_LAST_LINE_OFFSET = "FileLastLineOffset";
    public static final String FILE_LAST_LINE = "FileLastLine";
    public static final String FILE_NEXT_LINE_OFFSET = "FileNextLineOffset";
    public static final String FILE_NEXT_LINE = "FileNextLine";
    public static final String FILE_LAST_MODIFIED = "FileLastModified";
    public static final String FILE_CAN_READ = "FileCanRead";
    public static final String FILE_CAN_WRITE = "FileCanWrite";
    public static final String FILE_LINE_COUNT = "FileLineCount";
    public static final String FILE_IS_DIRECTORY = "FileIsDirectory";
    public static final String BOOKMARK_KEY = "bookmarkString";
    public static final String ON_FIRST_SEARCH_KEY = "onFirstSearch";
    public static final String USE_BOOKMARK_KEY = "useBookmark";
    public static final String TRUNCATE_ACTION_KEY = "truncateAction";
    public static final String LAST_LINE_KEY = "LastLine";
    public static final String UNIQUE_KEY = "unique";
    
    public SystemPluginComponent() {
        this.m_osHost = null;
        this.m_osName = null;
        this.m_osVersion = null;
        this.m_systemPlugin = null;
    }
    
    public IManagementInfo[] getManagementInfo() {
        return SystemPluginComponent.MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
        this.m_systemPlugin = (SystemPlugIn)super.m_pluginObject;
    }
    
    private OSMetricsImpl getOSMetrics() {
        return this.m_systemPlugin.getOSMetrics();
    }
    
    public Boolean isLibraryLoaded() {
        boolean loaded = false;
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (osMetrics != null) {
            loaded = osMetrics.isLoaded();
        }
        return new Boolean(loaded);
    }
    
    public Boolean isLibraryInitialized() {
        boolean initialized = false;
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (osMetrics != null) {
            initialized = osMetrics.isInitialized();
        }
        return new Boolean(initialized);
    }
    
    public String getHostName() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (this.m_osHost == null && osMetrics != null) {
            this.m_osHost = osMetrics.getHostName();
        }
        return this.m_osHost;
    }
    
    public String getIPAddress() {
        String s = null;
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (osMetrics != null) {
            final Vector ipAddressStrings = osMetrics.getIPAddressStrings();
            if (ipAddressStrings == null) {
                s = "-";
            }
            else {
                s = ipAddressStrings.elementAt(0);
            }
        }
        return s;
    }
    
    public String getIPAddresses() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        String s = "";
        if (osMetrics != null) {
            final Vector ipAddressStrings = osMetrics.getIPAddressStrings();
            for (int i = 0; i < ipAddressStrings.size(); ++i) {
                final String str = ipAddressStrings.elementAt(i);
                if (i > 0) {
                    s += ", ";
                }
                s += str;
            }
        }
        return s;
    }
    
    public Boolean fileExists(final String pathname) {
        boolean exists = false;
        try {
            if (pathname != null) {
                exists = new File(pathname).exists();
            }
        }
        catch (Exception ex) {}
        return new Boolean(exists);
    }
    
    public Boolean isPortInUse(final String s, final String s2) {
        final Boolean true = Boolean.TRUE;
        Boolean b;
        if (s2 != null && s2.equalsIgnoreCase("udp")) {
            b = this.isUdpPortInUse(s);
        }
        else {
            b = this.isTcpPortInUse(s);
        }
        return b;
    }
    
    public Boolean isTcpPortInUse(final String s) {
        boolean inUse = false;
        try {
            inUse = new Port(s).isInUse();
        }
        catch (Exception ex) {}
        return new Boolean(inUse);
    }
    
    public Boolean isUdpPortInUse(final String s) {
        boolean connected;
        try {
            final DatagramSocket datagramSocket = new DatagramSocket(new Integer(s));
            connected = datagramSocket.isConnected();
            datagramSocket.close();
        }
        catch (Exception ex) {
            connected = true;
        }
        return new Boolean(connected);
    }
    
    public String getOSName() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (this.m_osName == null && osMetrics != null) {
            this.m_osName = osMetrics.getOSName();
        }
        return this.m_osName;
    }
    
    public String getOSVersion() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (this.m_osVersion == null && osMetrics != null) {
            this.m_osVersion = osMetrics.getOSVersion();
        }
        return this.m_osVersion;
    }
    
    public String getSystemTime() {
        return DateFormat.getDateTimeInstance(2, 2).format(new Date(System.currentTimeMillis()));
    }
    
    public Long getSystemUpTime() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        long upTime = 0L;
        if (osMetrics != null) {
            upTime = osMetrics.getUpTime();
        }
        return new Long(upTime);
    }
    
    public Long getNumberOfProcessors() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        long numberOfProcessors = 0L;
        if (osMetrics != null) {
            numberOfProcessors = osMetrics.getNumberOfProcessors();
        }
        return new Long(numberOfProcessors);
    }
    
    public Long getMemoryPageSize() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        long memoryPageSize = 0L;
        if (osMetrics != null) {
            memoryPageSize = osMetrics.getMemoryPageSize();
        }
        return new Long(memoryPageSize);
    }
    
    public String getJavaVendor() {
        return this.getProperty("java.vendor");
    }
    
    public String getJavaVersion() {
        return this.getProperty("java.version");
    }
    
    public String getJavaClasspath() {
        return this.pathToList(System.getProperty("java.class.path"));
    }
    
    public String getJavaLibpath() {
        return this.pathToList(this.getProperty("java.library.path"));
    }
    
    public String getPath() {
        final String environmentValueJNI = new Environment().getEnvironmentValueJNI("PATH");
        if (environmentValueJNI != null) {
            return this.pathToList(environmentValueJNI);
        }
        return "";
    }
    
    public String getProgressVersion() {
        return ProgressVersion.getFullVersion();
    }
    
    public String getDLC() {
        return this.getProperty("Install.Dir");
    }
    
    public String getProperty(final String key) {
        String property = System.getProperty(key);
        if (property == null) {
            property = "-";
        }
        return property;
    }
    
    public String pathToList(final String str) {
        String string = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
        while (stringTokenizer.hasMoreTokens()) {
            string = string + stringTokenizer.nextToken() + "<br>";
        }
        return string;
    }
    
    public void start(final String s) {
        this.m_systemPlugin.start();
    }
    
    public void stop(final String s) {
        this.m_systemPlugin.shutdown();
    }
    
    public void destroy(final String s) {
        try {
            this.destroy(new Integer(s));
        }
        catch (NumberFormatException ex) {}
    }
    
    public void destroy(final Integer n) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            osMetrics.closeQuery((int)n);
        }
    }
    
    public String getValidSystemType(final String s) {
        String s2 = null;
        if (s != null) {
            if (s.equalsIgnoreCase("SYSTEM")) {
                s2 = "SYSTEM";
            }
            else if (s.equalsIgnoreCase("CPU")) {
                s2 = "CPU";
            }
            else if (s.equalsIgnoreCase("DISK")) {
                s2 = "DISK";
            }
            else if (s.equalsIgnoreCase("MEMORY")) {
                s2 = "MEMORY";
            }
            else if (s.equalsIgnoreCase("FILESYSTEM")) {
                s2 = "FILESYSTEM";
            }
            else if (s.equalsIgnoreCase("PROCESS")) {
                s2 = "PROCESS";
            }
        }
        return s2;
    }
    
    public String[] getInstanceNames() {
        return this.getInstanceNamesV().toArray(new String[0]);
    }
    
    public String[] getInstanceNames(final String s) {
        return this.getInstanceNamesV(s).toArray(new String[0]);
    }
    
    public String[] getInstanceNames(final String[] array) {
        return this.getInstanceNamesV(array, new Boolean(false)).toArray(new String[0]);
    }
    
    public Vector getInstanceNamesV() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        Vector instanceNamesV = new Vector();
        if (osMetrics != null) {
            instanceNamesV = this.getInstanceNamesV(osMetrics.getObjectStrings().toArray(new String[0]), new Boolean(false));
        }
        return instanceNamesV;
    }
    
    public Vector getInstanceNamesV(final String s) {
        Vector instanceNamesV = new Vector();
        if (s != null) {
            instanceNamesV = this.getInstanceNamesV(new String[] { s }, new Boolean(true));
        }
        return instanceNamesV;
    }
    
    public Vector getInstanceNamesV(final String[] array, Boolean b) {
        final Vector vector = new Vector<String>();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (b == null) {
            b = new Boolean(false);
        }
        if (osMetrics != null && array != null) {
            for (int i = 0; i < array.length; ++i) {
                final String validSystemType = this.getValidSystemType(array[i]);
                if (validSystemType != null) {
                    final Vector objectInstanceStrings = osMetrics.getObjectInstanceStrings(validSystemType);
                    for (int j = 0; j < objectInstanceStrings.size(); ++j) {
                        final String str = objectInstanceStrings.elementAt(j);
                        String string;
                        if (b) {
                            string = str;
                        }
                        else {
                            string = validSystemType + "." + str;
                        }
                        if (!vector.contains(string)) {
                            vector.add(string);
                        }
                    }
                }
            }
        }
        return vector;
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "osmetrics.";
    }
    
    public Hashtable getStatistics(final String s, final String[] array) {
        return null;
    }
    
    public Hashtable getStatistics2(final Integer n) {
        final int n2 = (n != null) ? n : -1;
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n2 != -1 && osMetrics != null && osMetrics.collectQueryData(n2)) {
            final Properties formattedQueryProperties = osMetrics.getFormattedQueryProperties(n2, false);
            final Enumeration<?> propertyNames = formattedQueryProperties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String s = (String)propertyNames.nextElement();
                final String property = formattedQueryProperties.getProperty(s);
                if (s != null && property != null) {
                    hashtable.put(s, property);
                }
            }
        }
        return hashtable;
    }
    
    public Hashtable getStatistics(final Integer n) {
        final int n2 = (n != null) ? n : -1;
        final Hashtable<String, Object[]> hashtable = new Hashtable<String, Object[]>();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n2 != -1 && osMetrics != null && osMetrics.collectQueryData(n2)) {
            final String[] queryElements = osMetrics.getQueryElements(n2);
            final Properties formattedQueryProperties = osMetrics.getFormattedQueryProperties(n2, false);
            for (int i = 0; i < queryElements.length; ++i) {
                final String s = queryElements[i];
                final int index = s.indexOf(46);
                final String substring = s.substring(0, index);
                s.substring(index + 1);
                final String[] formattedValueDescriptions = osMetrics.getFormattedValueDescriptions(substring);
                final Vector vector = new Vector<String>();
                for (int j = 0; j < formattedValueDescriptions.length; ++j) {
                    vector.addElement(formattedQueryProperties.getProperty(s + "." + formattedValueDescriptions[j]));
                }
                hashtable.put(s, vector.toArray());
            }
        }
        return hashtable;
    }
    
    public Boolean doCollectStatistics(final Integer n) {
        boolean collectQueryData = false;
        if (n != null) {
            final OSMetricsImpl osMetrics = this.getOSMetrics();
            if (osMetrics != null) {
                collectQueryData = osMetrics.collectQueryData((int)n);
            }
        }
        return new Boolean(collectQueryData);
    }
    
    private Hashtable Properties2Hash(final Properties properties) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if (properties != null) {
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String s = (String)propertyNames.nextElement();
                final String property = properties.getProperty(s);
                if (s != null && property != null) {
                    hashtable.put(s, property);
                }
            }
        }
        return hashtable;
    }
    
    public String[] getProcessSchema() {
        return this.getStringValueSchema("PROCESS");
    }
    
    public String[] getStringValueSchema(String validSystemType) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        String[] stringValueDescriptions = new String[0];
        validSystemType = this.getValidSystemType(validSystemType);
        if (validSystemType != null && osMetrics != null) {
            stringValueDescriptions = osMetrics.getStringValueDescriptions(validSystemType);
        }
        return stringValueDescriptions;
    }
    
    public String[] getFormattedValueSchema(String validSystemType) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        String[] formattedValueDescriptions = new String[0];
        validSystemType = this.getValidSystemType(validSystemType);
        if (validSystemType != null && osMetrics != null) {
            formattedValueDescriptions = osMetrics.getFormattedValueDescriptions(validSystemType);
        }
        return formattedValueDescriptions;
    }
    
    public Properties getRawStatistics(final Integer n) {
        return this.getRawStatistics(n, new Boolean(false));
    }
    
    public Properties getRawStatistics(final Integer n, final Boolean b) {
        Properties rawQueryProperties = new Properties();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            rawQueryProperties = osMetrics.getRawQueryProperties((int)n, (boolean)b);
        }
        return rawQueryProperties;
    }
    
    public Properties getStringStatistics(final Integer n) {
        Properties stringQueryProperties = new Properties();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            stringQueryProperties = osMetrics.getStringQueryProperties((int)n);
        }
        return stringQueryProperties;
    }
    
    public Properties getFormattedStatistics(final Integer n) {
        return this.getFormattedStatistics(n, new Boolean(false));
    }
    
    public Properties getFormattedStatistics(final Integer n, final Boolean b) {
        Properties formattedQueryProperties = new Properties();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            formattedQueryProperties = osMetrics.getFormattedQueryProperties((int)n, (boolean)b);
        }
        return formattedQueryProperties;
    }
    
    public Hashtable getStatisticSchema(final String[] array) {
        return null;
    }
    
    public Hashtable getStatisticSchema(final Integer n) {
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            final String[] elements = this.getElements(n);
            for (int i = 0; i < elements.length; ++i) {
                final String key = elements[i];
                final int index = key.indexOf(46);
                if (index > 0) {
                    hashtable.put(key, osMetrics.getFormattedValueDescriptions(key.substring(0, index)));
                }
            }
        }
        return hashtable;
    }
    
    public Hashtable getConfiguration(final String s) {
        return null;
    }
    
    public Hashtable getConfigurationSchema(final String[] array) {
        return null;
    }
    
    public void setConfiguration(final String s, final Hashtable hashtable) throws Exception {
    }
    
    public Hashtable getThresholds(final String s) {
        return null;
    }
    
    public Hashtable getThresholdSchema(final String[] array) {
        return null;
    }
    
    public void setThresholds(final String s, final Hashtable hashtable) throws Exception {
        throw new Exception("Method not implemented");
    }
    
    public Boolean killProcess(final String s) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        boolean killProcessInstance = false;
        if (osMetrics != null && s != null) {
            killProcessInstance = osMetrics.killProcessInstance(s);
        }
        return new Boolean(killProcessInstance);
    }
    
    public Integer createSet() {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        int openQuery = -1;
        if (osMetrics != null) {
            openQuery = osMetrics.openQuery();
        }
        return new Integer(openQuery);
    }
    
    public Boolean addElements(final Integer n, final String[] array) {
        boolean value = false;
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            final int intValue = n;
            value = true;
            for (int i = 0; i < array.length; ++i) {
                final String s = array[i];
                final int index = s.indexOf(46);
                if (index > 0) {
                    final String substring = s.substring(0, index);
                    final String substring2 = s.substring(index + 1);
                    if (substring.equalsIgnoreCase("process")) {
                        osMetrics.addProcessInstance(intValue, substring2);
                    }
                    if (!osMetrics.addQueryElement(intValue, substring, substring2)) {
                        value = false;
                    }
                }
            }
        }
        return new Boolean(value);
    }
    
    public Boolean addElements(final Integer n, final String s, final String[] array) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        final boolean b = s != null && s.equalsIgnoreCase("process");
        boolean value = true;
        if (n != null && osMetrics != null) {
            final int intValue = n;
            for (int n2 = 0; n2 < array.length && value; ++n2) {
                final String s2 = array[n2];
                if (b) {
                    osMetrics.addProcessInstance(intValue, s2);
                }
                if (!osMetrics.addQueryElement(intValue, s, s2)) {
                    value = false;
                }
            }
        }
        return new Boolean(value);
    }
    
    public Boolean removeElements(final Integer n, final String[] array) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        boolean value = true;
        if (n != null && osMetrics != null) {
            final int intValue = n;
            for (int i = 0; i < array.length; ++i) {
                final String s = array[i];
                final int index = s.indexOf(46);
                if (index > 0) {
                    final String substring = s.substring(0, index);
                    final String substring2 = s.substring(index + 1);
                    if (!osMetrics.removeQueryElement(intValue, substring, substring2)) {
                        value = false;
                    }
                    if (substring.equalsIgnoreCase("process")) {
                        osMetrics.removeProcessInstance(intValue, substring2);
                    }
                }
            }
        }
        return new Boolean(value);
    }
    
    public Boolean removeElements(final Integer n, final String s, final String[] array) {
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        final boolean b = s != null && s.equalsIgnoreCase("process");
        boolean value = true;
        if (n != null && osMetrics != null) {
            final int intValue = n;
            for (int i = 0; i < array.length; ++i) {
                final String s2 = array[i];
                if (!osMetrics.removeQueryElement(intValue, s, s2)) {
                    value = false;
                }
                if (b) {
                    osMetrics.removeProcessInstance(intValue, s2);
                }
            }
        }
        return new Boolean(value);
    }
    
    public String[] getElements(final Integer n) {
        String[] queryElements = new String[0];
        final OSMetricsImpl osMetrics = this.getOSMetrics();
        if (n != null && osMetrics != null) {
            queryElements = osMetrics.getQueryElements((int)n);
        }
        return queryElements;
    }
    
    public String[] getElementSchema(final String s) {
        final String[] formattedValueSchema = this.getFormattedValueSchema(s);
        final String[] stringValueSchema = this.getStringValueSchema(s);
        if (formattedValueSchema != null && stringValueSchema != null && stringValueSchema.length > 0) {
            final Vector<String> vector = new Vector<String>();
            for (int i = 0; i < formattedValueSchema.length; ++i) {
                vector.add(formattedValueSchema[i]);
            }
            for (int j = 0; j < stringValueSchema.length; ++j) {
                vector.add(stringValueSchema[j]);
            }
            return vector.toArray(new String[0]);
        }
        return formattedValueSchema;
    }
    
    public static boolean copyFile(final String s, final String str) {
        boolean b = false;
        try {
            final File file = new File(s);
            final File file2 = new File(str);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
            for (String str2 = bufferedReader.readLine(); str2 != null; str2 = bufferedReader.readLine()) {
                bufferedWriter.write(str2 + SystemPluginComponent.NEWLINE);
            }
            bufferedReader.close();
            bufferedWriter.close();
            b = true;
        }
        catch (FileNotFoundException ex) {
            ProLog.logd("SystemPluginComponent", 4, "Cannot copy " + s + " to " + str + ";");
            ProLog.logd("SystemPluginComponent", 4, s + " not found: " + ex.getMessage());
        }
        catch (IOException ex2) {
            ProLog.logd("SystemPluginComponent", 4, "Cannot copy " + s + " to " + str + ";");
            ProLog.logd("SystemPluginComponent", 4, "Error: " + ex2.getMessage());
        }
        return b;
    }
    
    public static boolean isLocalHost(final String host) {
        boolean b;
        try {
            b = (InetAddress.getByName(null).equals(InetAddress.getByName(host)) || NetInfo.isLocalHost(host));
        }
        catch (UnknownHostException ex) {
            b = false;
        }
        return b;
    }
    
    public Boolean isAdminServerStartupComplete() {
        boolean adminServerStartupComplete;
        try {
            adminServerStartupComplete = this.m_systemPlugin.isAdminServerStartupComplete();
        }
        catch (Exception ex) {
            adminServerStartupComplete = false;
        }
        return new Boolean(adminServerStartupComplete);
    }
    
    public Boolean isPluginLicensed(final String s) {
        boolean pluginLicensed;
        try {
            pluginLicensed = this.m_systemPlugin.isPluginLicensed(s);
        }
        catch (Exception ex) {
            pluginLicensed = false;
        }
        return new Boolean(pluginLicensed);
    }
    
    public Boolean isRemoteFileViewerSupported() {
        return new Boolean(true);
    }
    
    public Boolean isOpenEdgeV2Supported() {
        return new Boolean(true);
    }
    
    public Boolean isOpenEdge102BSupported() {
        return new Boolean(true);
    }
    
    public Hashtable getFileStatistics(final String pathname) {
        final Hashtable<String, Boolean> hashtable = new Hashtable<String, Boolean>();
        try {
            final File file = new File(pathname);
            hashtable.put("FileFound", new Boolean(file.exists()));
            hashtable.put("FileCanRead", new Boolean(file.canRead()));
            hashtable.put("FileCanWrite", new Boolean(file.canWrite()));
            hashtable.put("FileIsDirectory", new Boolean(file.isDirectory()));
            hashtable.put("FileLastModified", (Boolean)(Object)new Long(file.lastModified()));
            hashtable.put("FileOffset", (Boolean)(Object)new Long(file.length()));
            hashtable.put("FileIsEof", new Boolean(true));
        }
        catch (Exception ex) {
            hashtable.put("FileError", (Boolean)ex.getMessage());
        }
        return hashtable;
    }
    
    protected void postWorkCompletedEvent(final ESystemObject eSystemObject, final Hashtable hashtable) {
        try {
            final AbstractPluginComponent pluginComponent = SystemPlugIn.get().getPluginComponent();
            final IEventBroker eventBroker = SystemPlugIn.get().getEventBroker();
            if (pluginComponent != null && eSystemObject != null) {
                eSystemObject.setSource(pluginComponent.getCanonicalName());
                eventBroker.postEvent(eSystemObject);
            }
        }
        catch (Exception ex) {
            ProLog.logd("SystemPluginComponent", 4, "Unable to post EReloadLogFileViewer");
        }
    }
    
    protected Hashtable getFileBufferSync(final String pathname, final Long n, final Long n2, final Integer n3) {
        final Hashtable<String, Long> hashtable = new Hashtable<String, Long>();
        RandomAccessFile randomAccessFile = null;
        boolean exists = false;
        boolean value = false;
        final Vector value2 = new Vector<String>();
        final int n4 = (n3 != null) ? n3 : 0;
        final long n5 = (n != null) ? n : 0L;
        long length = (n2 != null) ? n2 : -1L;
        long lastModified = -1L;
        Long value3 = null;
        try {
            final File file = new File(pathname);
            lastModified = file.lastModified();
            exists = file.exists();
            long pos = (n5 < 0L) ? 0L : n5;
            if (n2 == null || length < 0L) {
                length = file.length();
            }
            if (pos == length) {
                value = true;
            }
            if (n4 > 0 && pos != length) {
                if (pos > length) {
                    pos = 0L;
                }
                randomAccessFile = new RandomAccessFile(file, "r");
                randomAccessFile.seek(pos);
                long value4 = pos;
                String value5 = randomAccessFile.readLine();
                long value6 = randomAccessFile.getFilePointer();
                for (int n6 = 0; value5 != null && n6 < n4 && value6 != length; value5 = randomAccessFile.readLine(), value6 = randomAccessFile.getFilePointer(), ++n6) {
                    value2.add(value5);
                    value4 = value6;
                    value3 = (Long)value5;
                }
                value = (value5 == null || value6 == length);
                if (value5 != null && value6 == length) {
                    value2.add(value5);
                    value4 = value6;
                    value3 = (Long)value5;
                }
                if (value3 != null) {
                    hashtable.put("FileLastLineOffset", new Long(value4));
                    hashtable.put("FileLastLine", value3);
                }
                if (value5 != null) {
                    hashtable.put("FileNextLineOffset", new Long(value6));
                    hashtable.put("FileNextLine", (Long)value5);
                }
            }
        }
        catch (FileNotFoundException ex3) {
            exists = false;
        }
        catch (IOException ex) {
            hashtable.put("FileError", (Long)ex.getMessage());
        }
        catch (Exception ex2) {
            hashtable.put("FileError", (Long)ex2.getMessage());
        }
        finally {
            try {
                hashtable.put("FileLastModified", new Long(lastModified));
                hashtable.put("FileFound", (Long)(Object)new Boolean(exists));
                hashtable.put("FileBuffer", (Long)value2);
                hashtable.put("FileOffset", new Long(length));
                hashtable.put("FileIsEof", (Long)(Object)new Boolean(value));
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
            catch (IOException ex4) {}
        }
        return hashtable;
    }
    
    public void getFileBuffer(final String s, final Long n, final Long n2, final Integer n3) {
        try {
            final getFileBufferThread getFileBufferThread = new getFileBufferThread(this, s, n, n2, n3);
            getFileBufferThread.start();
            getFileBufferThread.join();
        }
        catch (Throwable t) {
            ProLog.logd("SystemPluginComponent", 3, "getFileBuffer: " + t.getMessage());
        }
    }
    
    protected Hashtable updateLogFileMonitorSync(final String pathname, final Long n, final Integer n2, final Hashtable hashtable) {
        Hashtable<Object, Boolean> fileBufferSync = new Hashtable<Object, Boolean>();
        boolean exists = false;
        String string = null;
        RandomAccessFile randomAccessFile = null;
        final int value = (n2 != null) ? n2 : 2500;
        final long n3;
        final long pos = n3 = ((n != null) ? n : 0L);
        long filePointer = -1L;
        final String str = (hashtable != null) ? hashtable.get("bookmarkString") : null;
        final String s = (hashtable != null) ? hashtable.get("LastLine") : null;
        try {
            final File file = new File(pathname);
            exists = file.exists();
            long filePointer2 = (n3 < 0L) ? 0L : n3;
            final long length = file.length();
            boolean b = false;
            final int n4 = (s == null) ? 0 : s.length();
            if (pos > 0L) {
                if (pos > length) {
                    b = true;
                    filePointer2 = 0L;
                }
                else {
                    randomAccessFile = new RandomAccessFile(file, "r");
                    randomAccessFile.seek(pos);
                    final String line = randomAccessFile.readLine();
                    if (line == null || !line.equals(s)) {
                        b = true;
                        filePointer2 = 0L;
                    }
                    else {
                        b = false;
                        filePointer2 = randomAccessFile.getFilePointer();
                    }
                }
            }
            final boolean b2 = n4 > 0;
            if (b) {
                if (randomAccessFile == null) {
                    randomAccessFile = new RandomAccessFile(file, "r");
                }
                int n5 = 0;
                long n6 = (length > 0L) ? length : 0L;
                for (int n7 = 0; n6 > 0L && n5 == 0 && n7 < value; ++n7) {
                    final long previousLineOffset = getPreviousLineOffset(randomAccessFile, n6 - 1L);
                    randomAccessFile.seek(previousLineOffset);
                    final String line2 = randomAccessFile.readLine();
                    if (b2 && line2.equals(s)) {
                        n5 = 1;
                    }
                    else {
                        n6 = previousLineOffset;
                    }
                }
                randomAccessFile.close();
                filePointer2 = ((n6 >= 0L) ? n6 : 0L);
            }
            fileBufferSync = (Hashtable<Object, Boolean>)this.getFileBufferSync(pathname, new Long(filePointer2), new Long(length), new Integer(value));
            if (file.canWrite() && str != null && str.length() > 0 && (fileBufferSync.get("FileIsEof") != null && fileBufferSync.get("FileIsEof")) && filePointer2 != length) {
                string = "(" + new Date().toString() + ")" + str;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    }
                    catch (IOException ex3) {}
                }
                try {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(length);
                    randomAccessFile.writeBytes(SystemPluginComponent.NEWLINE);
                    filePointer = randomAccessFile.getFilePointer();
                    randomAccessFile.writeBytes(string);
                    randomAccessFile.writeBytes(SystemPluginComponent.NEWLINE);
                    final long filePointer3 = randomAccessFile.getFilePointer();
                    fileBufferSync.put("FileLastLineOffset", (Boolean)(Object)new Long(filePointer));
                    fileBufferSync.put("FileLastLine", (Boolean)string);
                    fileBufferSync.put("FileOffset", (Boolean)(Object)new Long(filePointer3));
                }
                finally {
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        }
                        catch (IOException ex4) {}
                    }
                }
            }
        }
        catch (FileNotFoundException ex5) {
            exists = false;
        }
        catch (IOException ex) {
            fileBufferSync.put("FileError", (Boolean)ex.getMessage());
        }
        catch (Exception ex2) {
            fileBufferSync.put("FileError", (Boolean)ex2.getMessage());
        }
        finally {
            fileBufferSync.put("FileFound", new Boolean(exists));
            if (string != null) {
                fileBufferSync.put("FileLastLine", (Boolean)string);
                fileBufferSync.put("FileLastLineOffset", (Boolean)(Object)new Long(filePointer));
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                }
                catch (IOException ex6) {}
            }
        }
        return fileBufferSync;
    }
    
    public void updateLogFileMonitor(final String s, final Long n, final Integer n2, final Hashtable hashtable) {
        try {
            final updateLogFileMonitorThread updateLogFileMonitorThread = new updateLogFileMonitorThread(this, s, n, n2, hashtable);
            updateLogFileMonitorThread.start();
            updateLogFileMonitorThread.join();
        }
        catch (Throwable t) {
            ProLog.logd("SystemPluginComponent", 3, "updateLogFileMonitor: " + t.getMessage());
        }
    }
    
    private static long getPreviousLineOffset(final RandomAccessFile randomAccessFile, long pos) throws IOException {
        int n = 0;
        while (pos >= 0L) {
            randomAccessFile.seek(pos);
            --pos;
            switch (randomAccessFile.read()) {
                case 10: {
                    if (n > 0) {
                        return randomAccessFile.getFilePointer();
                    }
                    ++n;
                    continue;
                }
                case -1: {
                    continue;
                }
                default: {
                    ++n;
                    continue;
                }
            }
        }
        if (pos < 0L) {
            pos = 0L;
        }
        return pos;
    }
    
    protected Hashtable reloadLogFileViewerSync(final String pathname, final Integer n) {
        final Hashtable<String, Long> hashtable = new Hashtable<String, Long>();
        RandomAccessFile randomAccessFile = null;
        boolean value = false;
        boolean value2 = false;
        final Vector<Long> value3 = new Vector<Long>();
        final Vector value4 = new Vector<Long>();
        final int n2 = (n != null) ? n : 0;
        int n3 = 0;
        int value5 = 0;
        long filePointer = 0L;
        try {
            final File file = new File(pathname);
            randomAccessFile = new RandomAccessFile(file, "r");
            value = true;
            value4.add(new Long(0L));
            String line;
            do {
                if (n3 == n2) {
                    filePointer = randomAccessFile.getFilePointer();
                    value4.add(new Long(filePointer));
                    n3 = 0;
                }
                line = randomAccessFile.readLine();
                ++n3;
                ++value5;
            } while (line != null);
            randomAccessFile.seek(filePointer);
            String e = randomAccessFile.readLine();
            for (int n4 = 0; e != null && n4 < n2; e = randomAccessFile.readLine(), ++n4) {
                value3.add((Long)e);
            }
            if (e == null) {
                value2 = true;
                --value5;
            }
            hashtable.put("FileOffset", new Long(randomAccessFile.getFilePointer()));
            hashtable.put("FileIsEof", (Long)(Object)new Boolean(value2));
            hashtable.put("FileLastModified", new Long(file.lastModified()));
        }
        catch (FileNotFoundException ex3) {
            value = false;
        }
        catch (IOException ex) {
            hashtable.put("FileError", (Long)ex.getMessage());
        }
        catch (Exception ex2) {
            hashtable.put("FileError", (Long)ex2.getMessage());
        }
        finally {
            try {
                hashtable.put("FileFound", (Long)(Object)new Boolean(value));
                hashtable.put("FileBuffer", (Long)value3);
                hashtable.put("FileOffsets", (Long)value4);
                hashtable.put("FileLineCount", (Long)new Integer(value5));
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
            catch (IOException ex4) {}
        }
        return hashtable;
    }
    
    public void reloadLogFileViewer(final String s, final Integer n) {
        try {
            final reloadLogFileViewerThread reloadLogFileViewerThread = new reloadLogFileViewerThread(this, s, n);
            reloadLogFileViewerThread.start();
            reloadLogFileViewerThread.join();
        }
        catch (Throwable t) {
            ProLog.logd("SystemPluginComponent", 3, "reloadLogFileViewer: " + t.getMessage());
        }
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
        MANAGEMENT_INFO = new IManagementInfo[AbstractPluginComponent.BASE_MANAGEMENT_INFO.length + 69];
        int length = AbstractPluginComponent.BASE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(AbstractPluginComponent.BASE_MANAGEMENT_INFO, 0, SystemPluginComponent.MANAGEMENT_INFO, 0, AbstractPluginComponent.BASE_MANAGEMENT_INFO.length);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("HostName", String.class.getName(), "Host name", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("InstanceNames", String[].class.getName(), "Instances", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("OSName", String.class.getName(), "Operating System name", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("OSVersion", String.class.getName(), "Operating System version", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("Path", String.class.getName(), "Operating System search path", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("DLC", String.class.getName(), "Install directory", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("IPAddress", String.class.getName(), " IP address", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("IPAddresses", String.class.getName(), " All IP addresses", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("JavaClasspath", String.class.getName(), "JAVA classpath", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("JavaLibpath", String.class.getName(), "JAVA library search path", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("JavaVendor", String.class.getName(), "JVM vendor", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("JavaVersion", String.class.getName(), "JVM version", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("ProgressVersion", String.class.getName(), "Progress version", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("SystemTime", String.class.getName(), "System current time", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("SystemUpTime", Long.class.getName(), "System uptime", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("NumberOfProcessors", Long.class.getName(), "Number of processors on system", true, false);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("MemoryPageSize", Long.class.getName(), "Page size of memory on system", true, false);
        try {
            method = SystemPluginComponent.class.getMethod("killProcess", String.class);
        }
        catch (Exception ex) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Kill the process identified by the PID argument", method);
        try {
            method = SystemPluginComponent.class.getMethod("isLocalHost", String.class);
        }
        catch (Exception ex2) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the given host the local host?", method);
        try {
            method = SystemPluginComponent.class.getMethod("copyFile", String.class, String.class);
        }
        catch (Exception ex3) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Copy file1 to file2", method);
        try {
            method = SystemPluginComponent.class.getMethod("isPluginLicensed", String.class);
        }
        catch (Exception ex4) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the given plugin licensed?", method);
        try {
            method = SystemPluginComponent.class.getMethod("isAdminServerStartupComplete", String.class);
        }
        catch (Exception ex5) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Has the AdminServer fully loaded, initialized, and started the plugins?", method);
        try {
            method = SystemPluginComponent.class.getMethod("addElements", Integer.class, String.class, String[].class);
        }
        catch (Exception ex6) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add object instances to the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("addElements", Integer.class, String[].class);
        }
        catch (Exception ex7) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add object instances to the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("createSet", (Class[])new Class[0]);
        }
        catch (Exception ex8) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Creates sets? ....", method);
        try {
            method = SystemPluginComponent.class.getMethod("destroy", String.class);
        }
        catch (Exception ex9) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the named query set", method);
        try {
            method = SystemPluginComponent.class.getMethod("destroy", Integer.class);
        }
        catch (Exception ex10) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the named query set", method);
        try {
            method = SystemPluginComponent.class.getMethod("getElements", Integer.class);
        }
        catch (Exception ex11) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a list of the <systemType>.<objectInstance> instances that are members of the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getElementSchema", String.class);
        }
        catch (Exception ex12) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a list of the <systemType>.<objectInstance> instances that are members of the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("removeElements", Integer.class, String[].class);
        }
        catch (Exception ex13) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Remove the object instances from the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("removeElements", Integer.class, String.class, String[].class);
        }
        catch (Exception ex14) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Remove the object instances from the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("isLibraryLoaded", (Class[])new Class[0]);
        }
        catch (Exception ex15) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the osmetrics.dll loaded.", method);
        try {
            method = SystemPluginComponent.class.getMethod("isLibraryInitialized", (Class[])new Class[0]);
        }
        catch (Exception ex16) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the osmetrics.dll loaded and initial OS metric system objects initialized?", method);
        try {
            method = SystemPluginComponent.class.getMethod("getInstanceNames", String.class);
        }
        catch (Exception ex17) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the list of instances for the named system type.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getInstanceNames", String[].class);
        }
        catch (Exception ex18) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the list of instances for the named system types.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getInstanceNamesV", (Class[])new Class[0]);
        }
        catch (Exception ex19) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the list of <systemType>.<instance> names managed.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getInstanceNamesV", String.class);
        }
        catch (Exception ex20) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the list of instances for the named system type.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getInstanceNamesV", String[].class, Boolean.class);
        }
        catch (Exception ex21) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the list of instances for the named system types.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getConfiguration", String.class);
        }
        catch (Exception ex22) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a set of properties for the specified configuration (property) name.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getConfigurationSchema", String[].class);
        }
        catch (Exception ex23) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of configuration element names.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getProperty", String.class);
        }
        catch (Exception ex24) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the specified JAVA system property.", method);
        try {
            method = SystemPluginComponent.class.getMethod("fileExists", String.class);
        }
        catch (Exception ex25) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named file exist on this system?", method);
        try {
            method = SystemPluginComponent.class.getMethod("isPortInUse", String.class, String.class);
        }
        catch (Exception ex26) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named port or service name in use for the named protocol?", method);
        try {
            method = SystemPluginComponent.class.getMethod("isTcpPortInUse", String.class);
        }
        catch (Exception ex27) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named TCP port or service name in use?", method);
        try {
            method = SystemPluginComponent.class.getMethod("isUdpPortInUse", String.class);
        }
        catch (Exception ex28) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named UDP port or service name in use?", method);
        try {
            method = SystemPluginComponent.class.getMethod("doCollectStatistics", Integer.class);
        }
        catch (Exception ex29) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Collect both the raw and formatted statistics for the specified query.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getFormattedStatistics", Integer.class);
        }
        catch (Exception ex30) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the formatted statistics for the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getFormattedStatistics", Integer.class, Boolean.class);
        }
        catch (Exception ex31) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the formatted statistics for the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getRawStatistics", Integer.class);
        }
        catch (Exception ex32) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the raw statistics for the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getRawStatistics", Integer.class, Boolean.class);
        }
        catch (Exception ex33) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the raw statistics for the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStatistics", Integer.class);
        }
        catch (Exception ex34) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Collect data for each <objectType>.<objectInstance> member of the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStatistics", String.class, String[].class);
        }
        catch (Exception ex35) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return from the specified instance, the set of statistics for the listed statistic names.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStatistics2", Integer.class);
        }
        catch (Exception ex36) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Collect data for each <objectType>.<objectInstance> member of the named query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStatisticSchema", Integer.class);
        }
        catch (Exception ex37) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition descriptions for the specified query set.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStatisticSchema", String[].class);
        }
        catch (Exception ex38) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of statistic names.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getStringStatistics", Integer.class);
        }
        catch (Exception ex39) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of statistic names.", method);
        try {
            method = SystemPluginComponent.class.getMethod("isOpenEdge102BSupported", (Class[])new Class[0]);
        }
        catch (Exception ex40) {}
        try {
            method = SystemPluginComponent.class.getMethod("isOpenEdgeV2Supported", (Class[])new Class[0]);
        }
        catch (Exception ex41) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Always returns true when the OE servers are supported.", method);
        try {
            method = SystemPluginComponent.class.getMethod("isRemoteFileViewerSupported", (Class[])new Class[0]);
        }
        catch (Exception ex42) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Always returns true when the remote file viewer function exists.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getFileStatistics", String.class);
        }
        catch (Exception ex43) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return information about the named file.", method);
        try {
            method = SystemPluginComponent.class.getMethod("getFileBuffer", String.class, Long.class, Long.class, Integer.class);
        }
        catch (Exception ex44) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a portion of the named file starting at the offset specified up to the named size or the ending offset specified.", method);
        try {
            method = SystemPluginComponent.class.getMethod("updateLogFileMonitor", String.class, Long.class, Integer.class, Hashtable.class);
        }
        catch (Exception ex45) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Log File Monitor: Return a portion of the named file starting at the line after the offset specified up to the named size; bookmark if requested.", method);
        try {
            method = SystemPluginComponent.class.getMethod("reloadLogFileViewer", String.class, Integer.class);
        }
        catch (Exception ex46) {}
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Log File Viewer: Reload a file for display by the log file viewer.", method);
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "AppStateTestEvent", "Test osmetrics application state event.");
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)0, INotification.SUBCATEGORY_TEXT[0], "SysStateTestEvent", "Test osmetrics system state event.");
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ESystemObject", "Generic system event.");
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EReloadLogFileViewer", "Log file information has been reloaded for display.");
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EUpdateLogFileMonitor", "Log file monitor information has been updated.");
        SystemPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EGetFileBuffer", "Return a portion of the specified file that falls within the offsets specified.");
    }
    
    public class getFileBufferThread extends Thread
    {
        SystemPluginComponent spc;
        String fullFileSpec;
        Long beginOffset;
        Long endOffset;
        Integer bufferSize;
        
        public getFileBufferThread(final SystemPluginComponent spc, final String fullFileSpec, final Long beginOffset, final Long endOffset, final Integer bufferSize) {
            this.spc = null;
            this.fullFileSpec = null;
            this.beginOffset = null;
            this.endOffset = null;
            this.bufferSize = null;
            this.spc = spc;
            this.fullFileSpec = fullFileSpec;
            this.beginOffset = beginOffset;
            this.endOffset = endOffset;
            this.bufferSize = bufferSize;
        }
        
        public void run() {
            Hashtable fileBufferSync = null;
            try {
                fileBufferSync = this.spc.getFileBufferSync(this.fullFileSpec, this.beginOffset, this.endOffset, this.bufferSize);
            }
            catch (Exception ex) {
                ProLog.logd("SystemPluginComponent", 3, "getFileBuffer: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally {
                try {
                    this.spc.postWorkCompletedEvent(new EGetFileBuffer(this.fullFileSpec, fileBufferSync), fileBufferSync);
                }
                catch (RemoteException ex2) {}
            }
        }
    }
    
    protected class updateLogFileMonitorThread extends Thread
    {
        SystemPluginComponent spc;
        String fullFileSpec;
        Long lastLineOffset;
        Integer bufferSize;
        Hashtable lfmProperties;
        
        public updateLogFileMonitorThread(final SystemPluginComponent spc, final String fullFileSpec, final Long lastLineOffset, final Integer bufferSize, final Hashtable lfmProperties) {
            this.spc = null;
            this.fullFileSpec = null;
            this.lastLineOffset = null;
            this.bufferSize = null;
            this.lfmProperties = null;
            this.spc = spc;
            this.fullFileSpec = fullFileSpec;
            this.lastLineOffset = lastLineOffset;
            this.bufferSize = bufferSize;
            this.lfmProperties = lfmProperties;
        }
        
        public void run() {
            Hashtable updateLogFileMonitorSync = null;
            try {
                updateLogFileMonitorSync = this.spc.updateLogFileMonitorSync(this.fullFileSpec, this.lastLineOffset, this.bufferSize, this.lfmProperties);
            }
            catch (Exception ex) {
                ProLog.logd("SystemPluginComponent", 3, "updateLogFileMonitor: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally {
                try {
                    this.spc.postWorkCompletedEvent(new EUpdateLogFileMonitor(this.fullFileSpec, updateLogFileMonitorSync), updateLogFileMonitorSync);
                }
                catch (RemoteException ex2) {}
            }
        }
    }
    
    protected class reloadLogFileViewerThread extends Thread
    {
        String fullFileSpec;
        Integer bufferSize;
        SystemPluginComponent spc;
        
        public reloadLogFileViewerThread(final SystemPluginComponent spc, final String fullFileSpec, final Integer bufferSize) {
            this.fullFileSpec = null;
            this.bufferSize = null;
            this.spc = null;
            this.bufferSize = bufferSize;
            this.fullFileSpec = fullFileSpec;
            this.spc = spc;
        }
        
        public void run() {
            Hashtable reloadLogFileViewerSync = null;
            try {
                reloadLogFileViewerSync = this.spc.reloadLogFileViewerSync(this.fullFileSpec, this.bufferSize);
            }
            catch (Exception ex) {
                ProLog.logd("SystemPluginComponent", 3, "reloadLogFileViewer: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally {
                try {
                    this.spc.postWorkCompletedEvent(new EReloadLogFileViewer(this.fullFileSpec, reloadLogFileViewerSync), reloadLogFileViewerSync);
                }
                catch (RemoteException ex2) {}
            }
        }
    }
}
