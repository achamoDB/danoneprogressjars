// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.ProLog;
import com.progress.common.property.ERenegadePropertyFileChange;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.progress.common.property.AbstractMetaSchema;
import java.rmi.RemoteException;
import com.progress.common.text.UnquotedString;
import com.progress.international.resources.ProgressResources;
import com.progress.common.exception.ProException;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.io.File;
import com.progress.common.property.PropertyLog;
import com.progress.common.log.LogSystem;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.EventBroker;
import java.io.IOException;
import java.io.BufferedWriter;
import com.progress.common.networkevents.IEventInterestObject;
import java.util.Hashtable;
import com.progress.common.property.PropertyManager;

public class JuniperProperties extends PropertyManager
{
    private static final String VERSION = "%% version 1.1";
    private static String schemaFileName;
    private static String propertyFileName;
    private Hashtable m_legacyMappings;
    public static char DOUBLEQUOTE;
    public static char SINGLEQUOTE;
    private static String m_genpfMode;
    IEventInterestObject m_propChangeInterestObj;
    PropChangeListener m_propChangeListener;
    public static int DATABASE_DISPLAY_NAME_IDX;
    public static int CONFIGURATION_DISPLAY_NAME_IDX;
    public static int SERVERGROUP_DISPLAY_NAME_IDX;
    
    protected boolean chkPropertyVersion(final String s) {
        return s.trim().toLowerCase().equals("%% version 1.1".toLowerCase().trim());
    }
    
    protected void writePropertyVersion(final BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("%% version 1.1" + PropertyManager.NEWLINE);
    }
    
    protected Hashtable getGroupHashtable() {
        return new SortedHashtable();
    }
    
    protected Hashtable getPropertyHashtable() {
        return new SortedHashtable();
    }
    
    protected String getPropertyNameForWriting(final Property property) {
        return this.getPropertyNameForWriting(super.getGroupNameForWriting(), property);
    }
    
    protected String getPropertyNameForWriting(final String s, final Property property) {
        final String propertyNameForWriting = super.getPropertyNameForWriting(property);
        if (propertyNameForWriting == null || s == null || s.toLowerCase().startsWith("environment")) {
            return propertyNameForWriting;
        }
        return propertyNameForWriting.toLowerCase();
    }
    
    protected String getPropertyValueForWriting(final Property property) {
        final String propertyNameForWriting = super.getPropertyNameForWriting(property);
        String s = super.getPropertyValueForWriting(property);
        final String groupNameForWriting = super.getGroupNameForWriting();
        boolean b = true;
        if (groupNameForWriting != null && groupNameForWriting.toLowerCase().startsWith("environment")) {
            b = false;
        }
        else if (propertyNameForWriting != null && (propertyNameForWriting.equalsIgnoreCase("DisplayName") || propertyNameForWriting.equalsIgnoreCase("DatabaseName") || propertyNameForWriting.equalsIgnoreCase("OtherArgs") || propertyNameForWriting.equalsIgnoreCase("Port") || propertyNameForWriting.equalsIgnoreCase("IPver"))) {
            b = false;
        }
        if (b && s != null) {
            s = s.toLowerCase();
        }
        return s;
    }
    
    protected String getPropertyDefaultValueForWriting(final Property property) {
        final String propertyDefaultValueForWriting = super.getPropertyDefaultValueForWriting(property);
        if (propertyDefaultValueForWriting == null) {
            return propertyDefaultValueForWriting;
        }
        return propertyDefaultValueForWriting.toLowerCase();
    }
    
    protected String getGroupNameForWriting(final String s) {
        final String groupNameForWriting = super.getGroupNameForWriting(s);
        if (groupNameForWriting == null) {
            return groupNameForWriting;
        }
        return groupNameForWriting.toLowerCase();
    }
    
    public JuniperProperties() throws PropertyException {
        this((EventBroker)null);
    }
    
    public JuniperProperties(final EventBroker eventBroker) throws PropertyException {
        super(getSchemaFileName(), eventBroker);
        this.m_legacyMappings = new Hashtable();
        this.m_propChangeInterestObj = null;
        this.m_propChangeListener = null;
        try {
            if (eventBroker != null) {
                this.m_propChangeListener = new PropChangeListener(this);
                this.m_propChangeInterestObj = eventBroker.expressInterest(ERenegadePropertyFileChange.class, this.m_propChangeListener, JAPlugIn.get().getEventStream());
            }
        }
        catch (Throwable t) {
            final PropertyLog log = PropertyManager.m_log;
            LogSystem.logItErr("ConnectionManager", "Unable to start property change listener");
        }
        this.loadLegacyMappings();
    }
    
    public static String getSchemaFileName() {
        final String property = System.getProperty("juniper.schemafile");
        if (property != null && property.length() > 0) {
            JuniperProperties.schemaFileName = property;
        }
        return JuniperProperties.schemaFileName;
    }
    
    public static String getPropertyFileName() {
        return JuniperProperties.propertyFileName;
    }
    
    public JuniperProperties(final String s) throws PropertyException {
        this(s, null);
    }
    
    public JuniperProperties(String propertyFileName, final EventBroker eventBroker) throws PropertyValueException, LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyException {
        this(eventBroker);
        if (propertyFileName == null || propertyFileName.length() == 0) {
            propertyFileName = JuniperProperties.propertyFileName;
        }
        JuniperProperties.propertyFileName = propertyFileName;
        if (new File(propertyFileName).exists()) {
            this.load(propertyFileName);
        }
        else {
            JuniperProperties.m_genpfMode = "defaults";
            this.generatePropFile(propertyFileName);
        }
        this.startPropertyFileMonitor();
    }
    
    public void checkConfiguration(final String str) throws InvalidConfigException {
        if (str == null || !this.isKnownGroup("Configuration." + str)) {
            throw new InvalidConfigException(str);
        }
    }
    
    private Vector readArgs(final String fileName) throws FileNotFoundException, IOException {
        final Vector<String> vector = new Vector<String>();
        if (!new File(fileName).exists()) {
            throw new FileNotFoundException(fileName);
        }
        String line;
        while ((line = new BufferedReader(new FileReader(fileName)).readLine()) != null) {
            final String trim = line.trim();
            final int index = trim.indexOf(35);
            final String str = (index == -1) ? trim : trim.substring(0, index);
            if (str.length() == 0) {
                continue;
            }
            final StringTokenizer stringTokenizer = new StringTokenizer(str);
            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                if (nextToken.toLowerCase().equals("-pf")) {
                    final Vector args = this.readArgs(stringTokenizer.nextToken());
                    for (int size = args.size(), i = 0; i < size; ++i) {
                        vector.addElement(args.elementAt(i));
                    }
                }
                else {
                    vector.addElement(nextToken);
                }
            }
        }
        return vector;
    }
    
    public void processLegacyArguments(final String s, final String s2) throws ProLegacyException, FileNotFoundException, IOException {
        this.processLegacyArguments(s, s2, false);
    }
    
    public void processLegacyArguments(final String s, final String s2, final boolean b) throws ProLegacyException, FileNotFoundException, IOException {
        this.processLegacyArguments(s, s2, false, true);
    }
    
    public void processLegacyArguments(final String s, final String str, final boolean b, final boolean b2) throws ProLegacyException, FileNotFoundException, IOException {
        final Vector displayNames = this.getDisplayNames(str);
        if (displayNames == null) {
            throw new ProLegacyException("Unknown database: " + str);
        }
        this.processLegacyArguments(s, displayNames, b, b2);
    }
    
    public void processLegacyArguments(final String s, final Vector vector, final boolean b) throws ProLegacyException, FileNotFoundException, IOException {
        this.processLegacyArguments(s, vector, b, true);
    }
    
    public void processLegacyArguments(final String s, final Vector vector, final boolean b, final boolean b2) throws ProLegacyException, FileNotFoundException, IOException {
        final Vector args = this.readArgs(s);
        final String[] array = new String[args.size()];
        for (int i = 0; i < args.size(); ++i) {
            array[i] = args.elementAt(i);
        }
        this.processLegacyArguments(array, vector, b, b2);
    }
    
    public void processLegacyArguments(final String[] array, final Vector vector) throws ProLegacyException {
        this.processLegacyArguments(array, vector, false, true);
    }
    
    public void processLegacyArguments(final String[] array, final Vector vector, final boolean b, final boolean b2) throws ProLegacyException {
        int i = 0;
        final String s = vector.elementAt(JuniperProperties.DATABASE_DISPLAY_NAME_IDX);
        final String s2 = vector.elementAt(JuniperProperties.CONFIGURATION_DISPLAY_NAME_IDX);
        final String s3 = vector.elementAt(JuniperProperties.SERVERGROUP_DISPLAY_NAME_IDX);
        String s4 = "";
        while (i < array.length) {
            if (i + 1 < array.length && array[i].startsWith("-") && !array[i + 1].startsWith("-")) {
                final String s5 = array[i];
                final String s6 = array[++i];
                try {
                    this.toProperty(s5, s6, s, s2, s3, b, b2);
                }
                catch (ProException ex) {
                    s4 = s4 + PropertyManager.NEWLINE + ex.getLocalizedMessage().replace(JuniperProperties.DOUBLEQUOTE, JuniperProperties.SINGLEQUOTE);
                }
            }
            else if (array[i].startsWith("-")) {
                final String s7 = array[i];
                try {
                    this.toProperty(s7, s, s2, s3, b);
                }
                catch (ProException ex2) {
                    s4 = s4 + PropertyManager.NEWLINE + ex2.getLocalizedMessage().replace(JuniperProperties.DOUBLEQUOTE, JuniperProperties.SINGLEQUOTE);
                }
            }
            else {
                s4 = s4 + PropertyManager.NEWLINE + new InvalidArgumentException(array[i]).getLocalizedMessage().replace(JuniperProperties.DOUBLEQUOTE, JuniperProperties.SINGLEQUOTE);
            }
            ++i;
        }
        if (s4.length() > 0) {
            throw new ProLegacyException("The following unrecognized arguments were ignored:" + s4);
        }
    }
    
    public void convertArgs(final String[] array, final String s) throws ProLegacyException {
        this.processLegacyArguments(array, this.getDisplayNamesByConfig(s));
    }
    
    public String findDBPath(String string) throws InvalidConfigException {
        String dbPath = null;
        String[] groups;
        try {
            groups = this.groups("ServerGroup", true, false);
        }
        catch (NoSuchGroupException ex) {
            throw new InvalidConfigException(string);
        }
        string = "." + string;
        final int length = string.length();
        for (int i = 0; i < groups.length; ++i) {
            final String other = groups[i];
            final int length2 = other.length();
            if (length2 >= length && string.regionMatches(true, 0, other, length2 - length, length)) {
                dbPath = this.getDBPath(this.getDatabase(this.getConfiguration(other)));
                break;
            }
        }
        return dbPath;
    }
    
    public void generatePropFile() throws SaveIOException, NoSuchGroupException {
        this.generatePropFile(JuniperProperties.propertyFileName);
    }
    
    public void generatePropFile(final String str) throws SaveIOException, NoSuchGroupException {
        if (JuniperProperties.m_genpfMode != null) {
            if (JuniperProperties.m_genpfMode.equals("defaults")) {
                this.save(str, ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Command Line Argument Conversion with defaults"), null, true, true, false);
            }
            else {
                this.save(str, ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Command Line Argument Conversion"));
            }
            System.out.println("Property file " + str + " created.");
        }
    }
    
    public synchronized void save(final String s) throws SaveIOException {
        super.save(JuniperProperties.propertyFileName, s);
    }
    
    public synchronized void save() throws SaveIOException {
        this.save("Juniper Properties File");
    }
    
    public void generatePropFile(final boolean b) throws SaveIOException, NoSuchGroupException {
        if (JuniperProperties.m_genpfMode != null) {
            this.generatePropFile();
            System.exit(0);
        }
    }
    
    public String getEnvironment(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".Environment");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getEnvironmentString(final String s) throws InvalidConfigException {
        final String environment = this.getEnvironment(s);
        if (environment == null) {
            throw new InvalidConfigException(s);
        }
        return this.getPropertiesString("Environment") + " " + this.getPropertiesString("Environment." + environment);
    }
    
    public String getConfiguration(final String str) throws InvalidConfigException {
        final String property = this.getProperty("ServerGroup." + str + ".Configuration");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getDatabase(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".Database");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getDBPath(final String str) throws InvalidConfigException {
        String s = this.getProperty("Database." + str + ".DatabaseName");
        if (s == null) {
            throw new InvalidConfigException(str);
        }
        final int lastIndex = s.lastIndexOf(".db");
        if (lastIndex > 0) {
            s = s.substring(0, lastIndex);
        }
        return s;
    }
    
    public String getSqlYearOffset(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".SqlYearOffset");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getCaseTableName(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".CaseTableName");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getLogCharacterSet(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".LogCharacterSet");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getCollationTable(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".CollationTable");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getInternalCodePage(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".InternalCodePage");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getConversionMap(final String str) throws InvalidConfigException {
        final String property = this.getProperty("Configuration." + str + ".ConversionMap");
        if (property == null) {
            throw new InvalidConfigException(str);
        }
        return property;
    }
    
    public String getProSqlTrc(final String str) throws InvalidServerGroupException, InvalidProSqlTrcLenException, InvalidProSqlTrcCharException {
        final String lowerCase = this.getProperty("ServerGroup." + str + ".ProSqlTrc").toLowerCase();
        if (lowerCase == null) {
            throw new InvalidServerGroupException(str);
        }
        if (lowerCase.length() != 11) {
            throw new InvalidProSqlTrcLenException(lowerCase, lowerCase.length());
        }
        for (int i = 0; i < 11; ++i) {
            if (lowerCase.charAt(i) != 'y' && lowerCase.charAt(i) != 'n') {
                throw new InvalidProSqlTrcCharException(lowerCase, i);
            }
        }
        return lowerCase;
    }
    
    public String getServerGroupName(final String s) {
        String substring = null;
        if (s != null) {
            substring = s.substring(s.lastIndexOf(46) + 1);
        }
        return substring;
    }
    
    public int getServerType(final String str) {
        int n = 0;
        if (str != null) {
            final String property = this.getProperty("ServerGroup." + str + ".type");
            if (property != null) {
                if (property.equalsIgnoreCase("SQL")) {
                    n = 2;
                }
                else if (property.equalsIgnoreCase("4GL")) {
                    n = 1;
                }
                else if (property.equalsIgnoreCase("BOTH")) {
                    n = 3;
                }
            }
        }
        return n;
    }
    
    private void loadDefaults() throws GroupNameException, PropertyNameException, PropertyValueException {
        final String property = System.getProperty("juniper.dbfile");
        final String name = new File((property == null) ? "demo" : new UnquotedString(property).toString()).getName();
        final String string = name + ".virtualconfig";
        new StringBuffer().append(string).append(".virtual2").toString();
        new StringBuffer().append(name).append("(4GL)").toString();
        final String string2 = string + ".virtual";
        final String s = name;
        this.putProperty("Database." + name + ".configurations", string);
        final String string3 = "Configuration." + string;
        this.putProperty(string3 + ".database", name);
        this.putProperty(string3 + ".servergroups", string2);
        final String string4 = "ServerGroup." + string2;
        this.putProperty(string4 + ".configuration", string);
        this.putProperty(string4 + ".displayname", s);
        this.putProperty(string4 + ".type", "sql");
        this.putProperty(string4 + ".port", "1234");
    }
    
    private void initializeProperties() throws PropertyValueException {
        this.restrictRootGroups();
        this.registerGroup("Collection", false, false, true, new Property[] { new Property("DisplayName"), new Property("Autostart"), new Property("Databases") }, false);
        this.registerGroup("Database", false, true, true, new Property[] { new Property("DisplayName"), new Property("DatabaseName", "Demo"), new BooleanProperty("Autostart", "false"), new Property("DefaultConfiguration"), new Property("Configurations") }, true);
        String s;
        String s2;
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            s = "3000";
            s2 = "5000";
        }
        else {
            s = "1025";
            s2 = "2000";
        }
        this.registerGroup("Configuration", false, false, true, new Property[] { new Property("DisplayName"), new Property("Environment"), new IntProperty("AfterImageBuffers", "5", 5, 55), new BooleanProperty("AfterImageStall", "true"), new BooleanProperty("AfterImageProcess", "true"), new IntProperty("AsynchronousPageWriters", "1", 0, Integer.MAX_VALUE), new IntProperty("BeforeImageBuffers", "5", 3, Integer.MAX_VALUE), new BooleanProperty("BeforeImageBufferedWrites", "false"), new IntProperty("BeforeImageClusterAge", "60", 60, Integer.MAX_VALUE), new IntProperty("BeforeImageDelayWrites", "3", 0, 32768), new BooleanProperty("BeforeImageProcess", "true"), new IntProperty("BlocksInDatabaseBuffers", "0", 0, 125000000), new Property("CaseTableName", "BASIC"), new Property("CollationTable", "BASIC"), new Property("ConversionMap", "convmap.cp"), new BooleanProperty("CrashProtection", "true"), new Property("DatabaseCodePage", "BASIC"), new BooleanProperty("DirectIO", "false"), new IntProperty("HashTableEntries", "0", 0, Integer.MAX_VALUE), new Property("InternalCodePage", "iso8859-1"), new IntProperty("LockTableEntries", "8192", 32, Integer.MAX_VALUE), new Property("LogCharacterSet", "iso8859-1"), new IntProperty("MaxServers", "4", 1, 512), new IntProperty("MaxUsers", "20", 1, 2048), new IntProperty("Nap", "1", 1, Integer.MAX_VALUE), new IntProperty("NapMax", "1", 1, Integer.MAX_VALUE), new IntProperty("PageWriterQueueDelay", "100", 0, Integer.MAX_VALUE), new IntProperty("PageWriterQueueMin", "1", 0, Integer.MAX_VALUE), new IntProperty("PageWriterScan", "1", 0, Integer.MAX_VALUE), new IntProperty("PageWriterScanDelay", "1", 0, Integer.MAX_VALUE), new IntProperty("PageWriterMaxBuffers", "25", 0, Integer.MAX_VALUE), new IntProperty("SemaphoreSets", "1", 1, Integer.MAX_VALUE), new IntProperty("SharedMemoryOverflowSize", "0", 0, Integer.MAX_VALUE), new IntProperty("SpinLockRetries", "0", 0, Integer.MAX_VALUE), new IntProperty("SqlYearOffset", "1950", 0, 9999), new BooleanProperty("WatchDogProcess", "true"), new Property("Database"), new Property("ServerGroups") }, true);
        this.registerGroup("ServerGroup", false, false, true, new Property[] { new Property("DisplayName"), new BooleanProperty("NetworkClientSupport", "true"), new Property("Type", "both"), new Property("IPver", "IPv4"), new Property("Host", "localhost"), new Property("Port", "0"), new Property("ServiceName", ""), new IntProperty("InitialServers", "0", 0, 500), new IntProperty("MaxDynamicPort", s2, 1025, 32767), new IntProperty("MinDynamicPort", s, 1025, 32767), new IntProperty("MaxClientsPerServer", "0", 0, 2048), new IntProperty("MinClientsPerServer", "1", 1, 2048), new IntProperty("NumberOfServers", "0", 0, 2048), new Property("ProSqlTrc", "NNNNNNNNNNN"), new IntProperty("MessageBufferSize", "350", 350, 32600), new IntProperty("ReportingInterval", "1", 1, Integer.MAX_VALUE), new Property("ServerExe", ""), new BooleanProperty("Multithreaded", "false"), new Property("Configuration") }, true);
        this.registerGroup("Environment", false, false, false, new Property[0], true);
        this.registerGroup("XYZZY", true, true, true, new Property[] { new EnumProperty("Debug", "Off", new String[] { "Off", "Terse", "Verbose" }), new IntProperty("StartupSleep", "0", 0, 500), new BooleanProperty("JavaDebug", "false"), new BooleanProperty("disableNFSWarning", "false") });
    }
    
    public Vector getDisplayNames(final String obj) {
        final JADatabase database = JADatabase.findDatabase(obj);
        String obj2 = "defaultServerGroup";
        Vector services = null;
        Vector<String> vector = null;
        if (database != null) {
            String displayName;
            try {
                final JAConfiguration jaConfiguration = (JAConfiguration)database.getDefaultConfiguration();
                displayName = jaConfiguration.getDisplayName();
                services = jaConfiguration.services;
            }
            catch (RemoteException ex) {
                displayName = "defaultConfiguration";
                obj2 = "defaultServerGroup";
            }
            vector = new Vector<String>();
            vector.addElement(obj);
            vector.addElement(displayName);
            if (services != null) {
                for (int i = 0; i < services.size(); ++i) {
                    vector.addElement(services.elementAt(0).getDisplayName());
                }
            }
            else {
                vector.addElement(obj2);
            }
        }
        return vector;
    }
    
    private Vector getDisplayNamesByConfig(final String str) {
        final String string = "Configuration." + str;
        String obj = "defaultServerGroup";
        String obj2 = null;
        final Vector<JAService> vector = null;
        String str2 = this.getProperty(string + ".serverGroups");
        final String property = this.getProperty(string + ".database");
        String obj3 = this.getProperty(string + ".displayName");
        if (str2 != null) {
            final int index = str2.indexOf(44);
            if (index >= 0) {
                str2 = str2.substring(0, index);
            }
            obj = this.getProperty(str2 + ".displayName");
        }
        if (property != null) {
            obj2 = this.getProperty(property + ".displayName");
        }
        if (obj3 == null || obj3.length() == 0) {
            final int lastIndex = str.lastIndexOf(46);
            if (lastIndex >= 0) {
                obj3 = str.substring(lastIndex + 1);
            }
        }
        if (obj == null || obj.length() == 0) {
            final int lastIndex2 = str2.lastIndexOf(46);
            if (lastIndex2 >= 0) {
                obj = str2.substring(lastIndex2 + 1);
            }
        }
        if (obj2 == null || obj2.length() == 0) {
            final int index2 = str.indexOf(46);
            if (index2 >= 0) {
                obj2 = str.substring(0, index2);
            }
        }
        final Vector<String> vector2 = new Vector<String>();
        vector2.addElement(obj2);
        vector2.addElement(obj3);
        if (vector != null) {
            for (int i = 0; i < vector.size(); ++i) {
                vector2.addElement(vector.elementAt(0).getDisplayName());
            }
        }
        else {
            vector2.addElement(obj);
        }
        return vector2;
    }
    
    private boolean setByLegacy(final String key, final String s, final String str, final String s2, final String str2, final boolean b) throws GroupNameException, InvalidArgumentException, PropertyNameException, PropertyValueException {
        boolean b2 = true;
        final LegacyMappings legacyMappings = this.m_legacyMappings.get(key);
        if (legacyMappings == null) {
            return false;
        }
        final String managedGroup = legacyMappings.getManagedGroup();
        final String managedProperty = legacyMappings.getManagedProperty();
        String str3;
        if (managedGroup.equalsIgnoreCase("Configuration")) {
            str3 = managedGroup + "." + str + "." + s2;
        }
        else if (managedGroup.equalsIgnoreCase("Database")) {
            str3 = managedGroup + "." + str;
        }
        else {
            str3 = managedGroup + "." + str + "." + s2 + "." + str2;
        }
        if (managedProperty.equalsIgnoreCase("znfs")) {
            this.putBooleanProperty("XYZZY.disableNFSWarning", true);
        }
        else if (managedGroup.equalsIgnoreCase("GenPfMode")) {
            JuniperProperties.m_genpfMode = managedProperty;
        }
        else if (s == null) {
            b2 = false;
            final AbstractMetaSchema.PropStorageObject propStorageObject = super.m_propertyNames.get((managedGroup + "." + managedProperty).toLowerCase());
            if (propStorageObject != null) {
                if (!propStorageObject.type().equalsIgnoreCase("boolean")) {
                    throw new PropertyValueException(managedProperty, "");
                }
                this.putBooleanProperty(str3 + "." + managedProperty, !this.getBooleanProperty(str3 + "." + managedProperty));
                b2 = true;
            }
        }
        else {
            this.putProperty(str3 + "." + managedProperty, s, null, b);
        }
        return b2;
    }
    
    private void toProperty(final String s, final String s2, final String s3, final String s4) throws GroupNameException, InvalidArgumentException, PropertyNameException, PropertyValueException {
        this.toProperty(s, null, s2, s3, s4, false, true);
    }
    
    private void toProperty(final String s, final String s2, final String s3, final String s4, final boolean b) throws GroupNameException, InvalidArgumentException, PropertyNameException, PropertyValueException {
        this.toProperty(s, null, s2, s3, s4, b, true);
    }
    
    private void toProperty(final String s, final String s2, final String s3, final String s4, final String s5) throws GroupNameException, InvalidArgumentException, PropertyNameException, PropertyValueException {
        this.toProperty(s, s2, s3, s4, s5, false, true);
    }
    
    private void toProperty(final String str, final String str2, final String str3, final String str4, final String s, final boolean b, final boolean b2) throws GroupNameException, InvalidArgumentException, PropertyNameException, PropertyValueException {
        if (!this.setByLegacy(str, str2, str3, str4, s, b2)) {
            if (!b) {
                throw new InvalidArgumentException(str, str2);
            }
            final String string = "Configuration." + str3 + "." + str4;
            String property = this.getProperty(string + ".otherArgs");
            if (property == null) {
                property = "";
            }
            String str5 = property + " " + str;
            if (str2 != null) {
                str5 = str5 + " " + str2;
            }
            this.putProperty(string + ".otherArgs", str5);
        }
    }
    
    public void loadLegacyMappings() throws PropertyException {
        final String s = "privatejuniper.schema";
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/com/progress/schema/" + s);
        if (resourceAsStream == null) {
            throw new PropertyException("Error locating schema file: /com/progress/schema/" + s, new Object[0]);
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final int index = trim.indexOf(35);
                final String s2 = (index == -1) ? trim : trim.substring(0, index);
                if (s2.length() == 0) {
                    continue;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(s2, ".");
                if (stringTokenizer.countTokens() == 3) {
                    this.m_legacyMappings.put("-" + stringTokenizer.nextToken().trim(), new LegacyMappings(stringTokenizer.nextToken().trim(), stringTokenizer.nextToken().trim()));
                }
                else {
                    PropertyManager.m_log.log("Ignoring line from file " + s + ": " + s2);
                }
            }
        }
        catch (IOException ex) {
            throw new PropertyException("Error locating schema file: /com/progress/schema/" + s, new Object[0]);
        }
    }
    
    private static void printArray(final String str) {
        if (str != null) {
            System.out.println("Environment=" + str);
        }
    }
    
    public static void main(final String[] array) {
        String propertyFileName;
        if (array != null && array.length != 0) {
            propertyFileName = array[0];
        }
        else {
            propertyFileName = JuniperProperties.propertyFileName;
        }
        try {
            JATools.setIsServer();
            final JuniperProperties juniperProperties = new JuniperProperties(propertyFileName, null);
            final Vector args = juniperProperties.readArgs("d:/91cwork/abc.pf");
            for (int i = 0; i < args.size(); ++i) {
                System.out.println(args.elementAt(i));
            }
            juniperProperties.save(propertyFileName, "Command Line Argument Conversion with defaults", null, true, true, false);
            System.exit(0);
        }
        catch (InvalidServerGroupException ex) {
            System.out.println(ex.getMessage());
        }
        catch (InvalidProSqlTrcLenException ex2) {
            System.out.println(ex2.getMessage());
        }
        catch (InvalidProSqlTrcCharException ex3) {
            System.out.println(ex3.getMessage());
        }
        catch (Exception obj) {
            System.out.println(" Got exeception " + obj);
            obj.printStackTrace();
        }
    }
    
    static {
        JuniperProperties.schemaFileName = "juniper.schema";
        JuniperProperties.propertyFileName = "conmgr.properties";
        JuniperProperties.DOUBLEQUOTE = '\"';
        JuniperProperties.SINGLEQUOTE = '\'';
        JuniperProperties.m_genpfMode = null;
        JuniperProperties.DATABASE_DISPLAY_NAME_IDX = 0;
        JuniperProperties.CONFIGURATION_DISPLAY_NAME_IDX = 1;
        JuniperProperties.SERVERGROUP_DISPLAY_NAME_IDX = 2;
    }
    
    public static class ProLegacyException extends PropertyException
    {
        public ProLegacyException(final String s) {
            super(s, (Object[])null);
        }
    }
    
    public static class InvalidConfigException extends PropertyException
    {
        public InvalidConfigException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid database configuration group", new Object[] { s }), (Object[])null);
        }
        
        public String getGroupName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidServerGroupException extends PropertyException
    {
        public InvalidServerGroupException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid database server group:", new Object[] { s }), (Object[])null);
        }
        
        public String getGroupName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidArgumentException extends PropertyException
    {
        public InvalidArgumentException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid argument, argument:", new Object[] { s }), (Object[])null);
        }
        
        public InvalidArgumentException(final String s, final String s2) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid arguments, argument:", new Object[] { s, s2 }), (Object[])null);
        }
        
        public String getArgumentName() {
            return (String)this.getArgument(0);
        }
        
        public String getArgumentValue() {
            return (String)this.getArgument(1);
        }
    }
    
    public static class InvalidProSqlTrcLenException extends PropertyException
    {
        public InvalidProSqlTrcLenException(final String s, final int value) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Wrong number of characters in prosqltrc", new Object[] { new Integer(value), s }), (Object[])null);
        }
    }
    
    public static class InvalidProSqlTrcCharException extends PropertyException
    {
        public InvalidProSqlTrcCharException(final String s, final int value) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid character found in prosqltrc property value:", new Object[] { new Integer(value), s }), (Object[])null);
        }
    }
    
    public class LegacyMappings
    {
        String managedProperty;
        String managedGroup;
        
        LegacyMappings(final String managedGroup, final String managedProperty) {
            this.managedProperty = null;
            this.managedGroup = null;
            this.managedGroup = managedGroup;
            this.managedProperty = managedProperty;
        }
        
        String getManagedProperty() {
            return this.managedProperty;
        }
        
        String getManagedGroup() {
            return this.managedGroup;
        }
    }
    
    class PropChangeListener extends EventListener
    {
        JuniperProperties m_props;
        
        public PropChangeListener(final JuniperProperties props) {
            this.m_props = null;
            this.m_props = props;
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            if (!(eventObject instanceof ERenegadePropertyFileChange)) {
                return;
            }
            if (!(((ERenegadePropertyFileChange)eventObject).issuer() instanceof JuniperProperties)) {
                return;
            }
            final JuniperProperties props = this.m_props;
            final String propertyFileName = JuniperProperties.getPropertyFileName();
            try {
                if (new File(propertyFileName).exists()) {
                    this.m_props.stopMonitors();
                    this.m_props.load(propertyFileName);
                }
                else {
                    final JuniperProperties props2 = this.m_props;
                    JuniperProperties.m_genpfMode = "defaults";
                    this.m_props.generatePropFile(propertyFileName);
                }
                JAPlugIn.get().refreshDatabases();
            }
            catch (Throwable t) {
                ProLog.logdErr("ConnectionManager", 1, "Unable to reload changes in property file: " + propertyFileName);
                ProLog.logdErr("ConnectionManager", 1, t.getMessage());
            }
            try {
                this.m_props.startPropertyFileMonitor();
            }
            catch (Throwable t2) {
                ProLog.logdErr("ConnectionManager", 1, "Unable to restart monitor for property file: " + propertyFileName);
            }
            this.notify();
        }
    }
    
    public interface Constants
    {
        public static final int UNKNOWN_SERVER = 0;
        public static final int FGL_SERVER = 1;
        public static final int SQL_SERVER = 2;
        public static final int DUAL_SERVER = 3;
    }
}
