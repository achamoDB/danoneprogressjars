// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.common.exception.ProException;
import java.io.Serializable;
import java.util.StringTokenizer;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.Tools;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.FileReader;
import java.util.Vector;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;
import java.io.File;
import com.progress.common.networkevents.EventBroker;
import java.util.Hashtable;

public class PropertyManager implements IMetaSchema, IPropertyManagerRemote
{
    public static final String SCHEMA_FILE_PATH = "/com/progress/schema/";
    protected static PropertyLog m_log;
    protected MetaSchema m_metaSchema;
    protected Hashtable m_propertyNames;
    protected Hashtable m_propertyGroups;
    protected boolean m_rootGroupsRestricted;
    protected boolean m_saveSorted;
    protected String m_currentGroup;
    protected String m_writingGroup;
    protected IPropertyValueFilter m_getPropertyFilter;
    protected IPropertyValueFilter m_putPropertyFilter;
    protected CommentGroup m_beginCommentGroup;
    protected CommentGroup m_endCommentGroup;
    protected EventBroker m_eventBroker;
    protected PropertyMonitor m_monitor;
    protected String m_path;
    protected File m_file;
    protected long m_lastModified;
    protected PropertyMonitor m_propertyMonitor;
    protected boolean m_getOrderedPropertyHashTable;
    protected static final String NEWLINE;
    protected static final String SEPARATOR;
    protected static final int SAVE_EVEN_IF_EMPTY = 1;
    private static final int RESTRICT_CHILDREN = 2;
    private static final int RESTRICT_GRANDCHILDREN = 4;
    private static final int RESTRICT_PROPERTIES = 8;
    private static final int REGISTERED = 16;
    private static final int REGISTERED_ATTRIBUTES = 31;
    public static final String METRIC = "metric.";
    public static int PROPERTY_GROUP;
    public static int PROPERTY_NAME;
    public static int PROPERTY_WILDCARD;
    public static int PROPERTY_METRIC;
    public static int PROPERTY_INVALID;
    
    public PropertyManager() {
        this(null);
    }
    
    public PropertyManager(final EventBroker eventBroker) {
        this(eventBroker, true);
    }
    
    public PropertyManager(final EventBroker eventBroker, final boolean b) {
        this.m_metaSchema = null;
        this.m_propertyNames = new Hashtable();
        this.m_saveSorted = false;
        this.m_monitor = null;
        this.m_path = null;
        this.m_file = null;
        this.m_lastModified = 0L;
        this.m_propertyMonitor = null;
        this.m_getOrderedPropertyHashTable = false;
        if (Locale.getDefault().getISO3Country().equalsIgnoreCase("TUR")) {
            Locale.setDefault(Locale.ENGLISH);
        }
        if (b) {
            PropertyManager.m_log = PropertyLog.get();
        }
        this.m_propertyGroups = this.getGroupHashtable();
        this.m_rootGroupsRestricted = false;
        this.m_saveSorted = false;
        this.m_currentGroup = null;
        this.m_writingGroup = null;
        this.m_getPropertyFilter = null;
        this.m_putPropertyFilter = null;
        this.m_beginCommentGroup = new CommentGroup();
        this.m_endCommentGroup = new CommentGroup();
        this.m_eventBroker = eventBroker;
        this.m_monitor = null;
        this.m_file = null;
        this.m_path = null;
        this.m_lastModified = 0L;
        try {
            UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {}
    }
    
    public PropertyManager(final String s, final EventBroker eventBroker) throws PropertyException {
        this(eventBroker);
        this.loadSchema(s);
    }
    
    public boolean groupExists(final String s) throws RemoteException {
        return this.findGroup(s) != null;
    }
    
    public final PropertyGroup findGroup(final String s) {
        PropertyGroup propertyGroup = null;
        if (s != null) {
            propertyGroup = this.m_propertyGroups.get(s.toLowerCase());
        }
        return propertyGroup;
    }
    
    private final PropertyGroup findOrCreateGroup(final String s, final boolean b) throws GroupNameException {
        PropertyGroup value = null;
        if (s != null) {
            final String lowerCase = s.toLowerCase();
            value = (PropertyGroup)this.m_propertyGroups.get(lowerCase);
            if (value == null) {
                final PropertyGroup orCreateGroup = this.findOrCreateGroup(this.getParentName(s), b);
                Label_0079: {
                    if (!b) {
                        if (orCreateGroup == null) {
                            if (!this.m_rootGroupsRestricted) {
                                break Label_0079;
                            }
                        }
                        else if ((orCreateGroup.m_attributes & 0x2) == 0x0) {
                            break Label_0079;
                        }
                        throw new GroupNameException(s);
                    }
                }
                value = new PropertyGroup(this, s, orCreateGroup);
                this.m_propertyGroups.put(lowerCase, value);
            }
        }
        return value;
    }
    
    public String getPropertiesString(final String s) {
        String str = "";
        try {
            this.findGroup(s);
            final PropertyCollection properties = this.properties(s, true, true);
            if (properties != null && properties.size() > 0) {
                int n = 0;
                while (properties.hasMoreElements()) {
                    final Property property = (Property)properties.nextElement();
                    final String name = property.getName();
                    final String value = property.getValue();
                    if (str.equals("")) {
                        str = name + "=" + value;
                    }
                    else {
                        str = str + " " + name + "=" + value;
                    }
                    ++n;
                }
            }
        }
        catch (NoSuchGroupException ex) {}
        return str;
    }
    
    public String[] getArrayProperty(final String s) {
        return this.getArrayProperty(s, ",");
    }
    
    protected String[] getArrayProperty(final String s, final String s2) {
        return parseArrayProperty(this.getProperty(s), s2);
    }
    
    protected static String[] parseArrayProperty(final String s, final String s2) {
        return parseArrayProperty(s, s2, true);
    }
    
    protected static String[] parseArrayProperty(final String s, final String s2, final boolean b) {
        String[] array = null;
        if (s != null) {
            if (s.equals("")) {
                return new String[0];
            }
            if (!b) {
                return new String[] { s };
            }
            int n = 1;
            int index = -1;
            while (true) {
                index = s.indexOf(s2, index + 1);
                if (index < 0) {
                    break;
                }
                ++n;
            }
            array = new String[n];
            int n2 = 0;
            int beginIndex = 0;
            while (true) {
                final int index2 = s.indexOf(s2, beginIndex);
                if (index2 < 0) {
                    break;
                }
                if (index2 <= beginIndex) {
                    array[n2++] = "";
                }
                else {
                    array[n2++] = s.substring(beginIndex, index2).trim();
                }
                beginIndex = index2 + 1;
            }
            array[n2++] = s.substring(beginIndex).trim();
        }
        return array;
    }
    
    public boolean getBooleanProperty(final String s) {
        final String property = this.getProperty(s);
        boolean equalsIgnoreCase = false;
        if (property != null) {
            equalsIgnoreCase = property.equalsIgnoreCase("true");
        }
        return equalsIgnoreCase;
    }
    
    public boolean getBooleanProperty(final String s, final boolean b) {
        final String property = this.getProperty(s);
        boolean equalsIgnoreCase = b;
        if (property != null) {
            equalsIgnoreCase = property.equalsIgnoreCase("true");
        }
        return equalsIgnoreCase;
    }
    
    public String getGroupProperty(final String s) {
        return this.getProperty(s);
    }
    
    public String getGroupProperty(final String s, final String s2) {
        String property = this.getProperty(s);
        if (property == null) {
            property = s2;
        }
        return property;
    }
    
    public String getCurrentGroup() {
        return this.m_currentGroup;
    }
    
    public double getDoubleProperty(final String s) {
        final String property = this.getProperty(s);
        double doubleValue = 0.0;
        if (property != null) {
            doubleValue = Double.valueOf(property);
        }
        return doubleValue;
    }
    
    public double getDoubleProperty(final String s, final double n) {
        final String property = this.getProperty(s);
        double doubleValue = n;
        if (property != null) {
            doubleValue = Double.valueOf(property);
        }
        return doubleValue;
    }
    
    public float getFloatProperty(final String s) {
        final String property = this.getProperty(s);
        float floatValue = 0.0f;
        if (property != null) {
            floatValue = Float.valueOf(property);
        }
        return floatValue;
    }
    
    public float getFloatProperty(final String s, final float n) {
        final String property = this.getProperty(s);
        float floatValue = n;
        if (property != null) {
            floatValue = Float.valueOf(property);
        }
        return floatValue;
    }
    
    public int getIntProperty(final String s) {
        String s2 = this.getProperty(s);
        int int1 = 0;
        int radix = 10;
        if (s2 != null) {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            int1 = Integer.parseInt(s2, radix);
        }
        return int1;
    }
    
    public int getIntProperty(final String s, final int n) {
        String s2 = this.getProperty(s);
        int int1 = n;
        int radix = 10;
        if (s2 != null) {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            int1 = Integer.parseInt(s2, radix);
        }
        return int1;
    }
    
    public long getLongProperty(final String s) {
        String s2 = this.getProperty(s);
        long long1 = 0L;
        int radix = 10;
        if (s2 != null) {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            long1 = Long.parseLong(s2, radix);
        }
        return long1;
    }
    
    public long getLongProperty(final String s, final long n) {
        String s2 = this.getProperty(s);
        long long1 = n;
        int radix = 10;
        if (s2 != null) {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            long1 = Long.parseLong(s2, radix);
        }
        return long1;
    }
    
    public final String getParentName(final String s) {
        String substring = null;
        if (s != null) {
            final int lastIndex = s.lastIndexOf(46);
            if (lastIndex >= 0) {
                substring = s.substring(0, lastIndex);
            }
        }
        return substring;
    }
    
    public String getProperty(final String s) {
        return this.getProperty(s, (String[])null);
    }
    
    public String getProperty(final String s, final boolean b) {
        return this.getProperty(s, null, b);
    }
    
    public String getProperty(final String s, final String[] array) {
        return this.getProperty(s, array, true);
    }
    
    public String getProperty(final String str, final String[] array, final boolean b) {
        final String s = (this.m_currentGroup == null) ? str : (this.m_currentGroup + "." + str);
        final String propertyName = this.getPropertyName(s);
        String parentName = this.getParentName(s);
        String s2 = null;
        if (parentName == null) {
            parentName = "";
        }
        final PropertyGroup group = this.findGroup(parentName);
        if (group != null) {
            s2 = group.getProperty(propertyName, array);
        }
        else if (array != null) {
            array[0] = null;
        }
        if (s2 != null && this.m_getPropertyFilter != null && b) {
            s2 = this.m_getPropertyFilter.filterValue(parentName, propertyName, s2);
        }
        return s2;
    }
    
    public String getProperty(final String s, final String s2) {
        String property = this.getProperty(s);
        if (property == null) {
            property = s2;
        }
        return property;
    }
    
    private final String getPropertyName(final String s) {
        String substring = null;
        if (s != null) {
            substring = s.substring(s.lastIndexOf(46) + 1);
        }
        return substring;
    }
    
    public synchronized String[] groups() {
        String[] groups = null;
        try {
            groups = this.groups(null, true, true, false);
        }
        catch (NoSuchGroupException ex) {}
        return groups;
    }
    
    public synchronized String[] groups(final String s) throws NoSuchGroupException {
        return this.groups(s, true, true, false);
    }
    
    public synchronized String[] groups(final String s, final boolean b) throws NoSuchGroupException {
        return this.groups(s, b, true, false);
    }
    
    public synchronized String[] groups(final String s, final boolean b, final boolean b2) throws NoSuchGroupException {
        return this.groups(s, b, b2, false);
    }
    
    public synchronized String[] groups(String name, final boolean b, final boolean b2, boolean b3) throws NoSuchGroupException {
        String[] array = null;
        String string;
        if (name == null) {
            string = "";
            b3 = false;
        }
        else {
            final PropertyGroup propertyGroup = this.m_propertyGroups.get(name.toLowerCase());
            if (propertyGroup == null) {
                throw new NoSuchGroupException(name);
            }
            name = propertyGroup.getName();
            string = name + ".";
        }
        final int length = string.length();
        int n = 0;
        final Enumeration<PropertyGroup> elements = this.m_propertyGroups.elements();
        while (elements.hasMoreElements()) {
            final String name2 = elements.nextElement().getName();
            if (string.regionMatches(true, 0, name2, 0, length) && (b || name2.indexOf(46, length) == -1)) {
                ++n;
            }
        }
        if (b3) {
            ++n;
        }
        if (n > 0) {
            array = new String[n];
            final Enumeration<PropertyGroup> elements2 = this.m_propertyGroups.elements();
            int i = 0;
            if (b3) {
                array[i] = name;
                ++i;
            }
            while (i < n) {
                String other;
                do {
                    other = elements2.nextElement().getName();
                } while (!string.regionMatches(true, 0, other, 0, length) || (!b && other.indexOf(46, length) != -1));
                if (!b2) {
                    other = other.substring(length);
                }
                array[i] = other;
                ++i;
            }
        }
        return array;
    }
    
    public boolean isKnownGroup(final String s) {
        return s != null && this.findGroup(s) != null;
    }
    
    public void resetPropertyManager() {
        this.stopMonitors();
        this.m_propertyGroups = this.getGroupHashtable();
        this.m_currentGroup = null;
        this.m_writingGroup = null;
        this.m_beginCommentGroup = new CommentGroup();
        this.m_endCommentGroup = new CommentGroup();
        this.m_monitor = null;
        this.m_file = null;
        this.m_path = null;
        this.m_lastModified = 0L;
    }
    
    private Hashtable getPropertyNames(final MetaSchema metaSchema) {
        final Hashtable<String, AbstractMetaSchema.PropStorageObject> hashtable = new Hashtable<String, AbstractMetaSchema.PropStorageObject>();
        for (int i = 0; i < metaSchema.propsByGroup.length; ++i) {
            final Vector vector = metaSchema.propsByGroup[i];
            for (int j = 0; j < vector.size(); ++j) {
                final AbstractMetaSchema.PropStorageObject value = vector.elementAt(j);
                hashtable.put(value.group().toLowerCase() + "." + value.propName().toLowerCase(), value);
            }
        }
        return hashtable;
    }
    
    public MetaSchema getMetaSchema() {
        return this.m_metaSchema;
    }
    
    public synchronized void loadSchema(final String s) throws LoadIOException, PropertySyntaxException, PropertyVersionException, SchemaFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, PropertyWithoutCorrespondingGroup, PropertyException {
        this.m_metaSchema = new MetaSchema(this, s);
        if (this.m_metaSchema == null) {
            return;
        }
        this.m_propertyNames = this.getPropertyNames(this.m_metaSchema);
    }
    
    public synchronized void load(final String s) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.load(s, 0);
    }
    
    public synchronized void load(final String s, final int n) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.load(s, true, n);
    }
    
    public synchronized void update(final String s) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.update(s, 0);
    }
    
    public synchronized void update(final String s, final int n) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.load(s, false, n);
    }
    
    public void stopMonitors() {
        synchronized (this) {
            if (this.m_monitor != null) {
                this.m_monitor.destroyThread();
                this.m_monitor = null;
            }
        }
    }
    
    public void finalize() throws Throwable {
        this.stopMonitors();
        super.finalize();
    }
    
    public synchronized void load(final String s, final boolean b) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.load(s, b, 0);
    }
    
    public synchronized void load(final String s, final boolean b, final int n) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        this.load(s, b, n, false);
    }
    
    public synchronized void load(final String fileName, final boolean b, final int n, final boolean b2) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, MultipleFilesSpecified, PropertyException {
        final File file = new File(fileName);
        if (this.m_file != null) {
            this.m_lastModified = this.m_file.lastModified();
            if (!file.equals(this.m_file)) {
                throw new MultipleFilesSpecified();
            }
        }
        else {
            this.m_file = file;
            this.m_path = fileName;
        }
        try {
            if (n > 0) {
                this.startPropertyFileMonitor(n);
            }
            final FileReader fileReader = new FileReader(fileName);
            this.load(fileReader, b, b2);
            fileReader.close();
        }
        catch (FileNotFoundException ex) {
            throw new LoadFileNotFoundException(ex);
        }
        catch (IOException ex2) {
            throw new LoadIOException(ex2);
        }
    }
    
    public synchronized void load(final Reader reader) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, PropertyException {
        this.load(reader, true, false);
    }
    
    public synchronized void update(final Reader reader) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, PropertyException {
        this.load(reader, false, false);
    }
    
    public synchronized void load(final Reader reader, final boolean b) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, PropertyException {
        this.load(reader, b, false);
    }
    
    public synchronized void load(final Reader in, final boolean b, final boolean b2) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, PropertyException {
        String substring = null;
        CommentGroup commentGroup = new CommentGroup();
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        final String currentGroup = this.getCurrentGroup();
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PRPMsgBundle");
        try {
            final BufferedReader bufferedReader = new BufferedReader(in);
            while (true) {
                String upperComment = bufferedReader.readLine();
                ++n;
                if (upperComment == null) {
                    if (b) {
                        this.m_endCommentGroup.setUpperComment(commentGroup.getUpperComment());
                        final CommentGroup commentGroup2 = new CommentGroup();
                    }
                    String s = "";
                    final String[] groups = this.groups();
                    int length;
                    if (groups == null) {
                        length = 0;
                    }
                    else {
                        length = groups.length;
                    }
                    int n4 = 0;
                    for (int i = 0; i < length; ++i, n4 = 0) {
                        final PropertyCollection properties = this.properties(groups[i]);
                        while (properties.hasMoreElements()) {
                            final Property property = (Property)properties.nextElement();
                            try {
                                if (property.canHaveMultipleValues()) {
                                    final String[] arrayProperty = parseArrayProperty(property.getValue(), property.arraySeparator());
                                    for (int j = 0; j < arrayProperty.length; ++j) {
                                        property.validateValue(arrayProperty[j]);
                                    }
                                }
                                else {
                                    property.validateValue(property.getValue());
                                }
                            }
                            catch (PropertyException ex) {
                                if (n4 == 0) {
                                    s += progressResources.getTranString("In group heading", (Object)groups[i]);
                                    ++n4;
                                }
                                s = s + "  " + ex.getMessage() + "\n";
                            }
                        }
                    }
                    if (!s.equals("")) {
                        throw new PropertyException(progressResources.getTranString("The following errors occurred whilst loading a property file:", (Object)s), (Object[])null);
                    }
                    bufferedReader.close();
                }
                else if (upperComment.trim().startsWith("%%")) {
                    final String lowerCase = upperComment.trim().toLowerCase();
                    if (lowerCase.startsWith("%% version") && !this.chkPropertyVersion(upperComment) && !b2) {
                        throw new PropertyVersionException(lowerCase);
                    }
                    if (n2 != 0 || !b) {
                        continue;
                    }
                    this.m_beginCommentGroup.setUpperComment(commentGroup.getUpperComment());
                    commentGroup = new CommentGroup();
                    n2 = 1;
                }
                else {
                    final int index = upperComment.trim().indexOf("#");
                    if (index != -1) {
                        if (index > 0) {
                            final int index2 = upperComment.indexOf("#");
                            commentGroup.setLowerComment(upperComment.substring(index2, upperComment.length()));
                            upperComment = upperComment.substring(0, index2);
                        }
                        else if (index == 0) {
                            commentGroup.setUpperComment(upperComment);
                            continue;
                        }
                    }
                    String str = upperComment.trim();
                    if (str.length() == 0) {
                        continue;
                    }
                    if (n3 == 1) {
                        if (substring != null) {
                            str = substring + str;
                            substring = null;
                        }
                        n3 = 0;
                    }
                    if (str.endsWith(" \\")) {
                        final int lastIndex = str.lastIndexOf(" \\");
                        if (lastIndex >= 0) {
                            substring = str.substring(0, lastIndex);
                        }
                        n3 = 1;
                    }
                    else {
                        if (str.startsWith("[") && str.endsWith("]")) {
                            final String substring2 = str.substring(1, str.length() - 1);
                            this.setCurrentGroup(substring2);
                            if (b) {
                                this.setGroupComment(substring2, commentGroup);
                            }
                        }
                        else {
                            final int index3 = str.indexOf("=");
                            if (str.length() < 2 || index3 <= 0) {
                                throw new PropertySyntaxException(n);
                            }
                            final String trim = str.substring(0, index3).trim();
                            final String trim2 = str.substring(index3 + 1).trim();
                            try {
                                if (b) {
                                    this.putProperty(trim, trim2, commentGroup, false);
                                }
                                else {
                                    this.putProperty(trim, trim2, null, false);
                                }
                            }
                            catch (PropertyNameException ex2) {
                                throw ex2;
                            }
                        }
                        commentGroup = new CommentGroup();
                    }
                }
            }
        }
        catch (FileNotFoundException ex3) {
            throw new LoadFileNotFoundException(ex3);
        }
        catch (IOException ex4) {
            throw new LoadIOException(ex4);
        }
        finally {
            this.setCurrentGroup(currentGroup);
        }
    }
    
    public synchronized void startPropertyFileMonitor() {
        this.startPropertyFileMonitor(5000);
    }
    
    public synchronized void startPropertyFileMonitor(final int n) {
        try {
            (this.m_propertyMonitor = new PropertyMonitor(this, n)).start();
        }
        catch (Throwable t) {
            this.m_propertyMonitor = null;
        }
    }
    
    public synchronized PropertyCollection properties(final String s) throws NoSuchGroupException {
        return this.properties(s, false, false, null);
    }
    
    public synchronized PropertyCollection properties(final String s, final int value) throws NoSuchGroupException {
        return this.properties(s, false, false, new Integer(value));
    }
    
    public synchronized PropertyCollection properties(final String s, final boolean b) throws NoSuchGroupException {
        return this.properties(s, b, false, null);
    }
    
    public synchronized PropertyCollection properties(final String s, final boolean b, final int value) throws NoSuchGroupException {
        return this.properties(s, b, false, new Integer(value));
    }
    
    public synchronized PropertyCollection properties(final String s, final boolean b, final boolean b2) throws NoSuchGroupException {
        return this.properties(s, b, b2, null);
    }
    
    public synchronized PropertyCollection properties(final String s, final boolean b, final boolean b2, final int value) throws NoSuchGroupException {
        return this.properties(s, b, b2, new Integer(value));
    }
    
    private synchronized PropertyCollection properties(final String s, final boolean b, final boolean b2, final Integer n) throws NoSuchGroupException {
        final PropertyGroup group = this.findGroup(s);
        if (group == null) {
            throw new NoSuchGroupException(s);
        }
        final PropertyCollection collection = new PropertyCollection(this, s);
        final Enumeration elements = group.m_properties.elements();
        while (elements.hasMoreElements()) {
            final Property property = elements.nextElement();
            if ((b || property.getValue() != null) && (n == null || n == property.getTag())) {
                collection.put(property, true);
            }
        }
        if (b2) {
            for (PropertyGroup propertyGroup = group.m_parent; propertyGroup != null; propertyGroup = propertyGroup.m_parent) {
                final Enumeration elements2 = propertyGroup.m_properties.elements();
                while (elements2.hasMoreElements()) {
                    final Property property2 = elements2.nextElement();
                    if ((b || property2.getValue() != null) && (n == null || n == property2.getTag())) {
                        collection.put(property2, false);
                    }
                }
            }
        }
        return collection;
    }
    
    public void addArrayProperty(final String s, final String obj) throws GroupNameException, PropertyNameException, PropertyValueException {
        final Vector vector = new Vector<String>();
        final String[] arrayProperty = this.getArrayProperty(s);
        if (arrayProperty != null) {
            for (int i = 0; i < arrayProperty.length; ++i) {
                vector.addElement(arrayProperty[i]);
            }
        }
        vector.addElement(obj);
        final String[] array = new String[vector.size()];
        for (int j = 0; j < array.length; ++j) {
            array[j] = vector.elementAt(j);
        }
        this.putArrayProperty(s, array);
    }
    
    public void removeArrayProperty(final String s, final String s2) throws GroupNameException, PropertyNameException, PropertyValueException {
        final Vector vector = new Vector<String>();
        final String[] arrayProperty = this.getArrayProperty(s);
        if (arrayProperty != null) {
            for (int i = 0; i < arrayProperty.length; ++i) {
                if (!s2.equalsIgnoreCase(arrayProperty[i].toLowerCase())) {
                    vector.addElement(arrayProperty[i]);
                }
            }
        }
        if (vector.size() == 0) {
            this.removeProperty(s);
        }
        else {
            final String[] array = new String[vector.size()];
            for (int j = 0; j < array.length; ++j) {
                array[j] = vector.elementAt(j);
            }
            this.putArrayProperty(s, array);
        }
    }
    
    public boolean putArrayProperty(final String s, final String[] array) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putArrayProperty(s, array, ", ");
    }
    
    protected boolean putArrayProperty(final String s, final String[] array, final String str) throws GroupNameException, PropertyNameException, PropertyValueException {
        String s2 = null;
        if (array != null && array.length > 0) {
            s2 = "";
            int n = 0;
            do {
                if (array[n] != null && !array[n].equals("")) {
                    if (!s2.equals("")) {
                        s2 += str;
                    }
                    s2 += array[n];
                }
            } while (++n < array.length);
        }
        return this.putProperty(s, s2);
    }
    
    public boolean putBooleanProperty(final String s, final boolean b) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, b ? "true" : "false");
    }
    
    public boolean putGroupProperty(final String s, final String s2) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, s2);
    }
    
    public boolean putDoubleProperty(final String s, final double d) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, Double.toString(d));
    }
    
    public boolean putFloatProperty(final String s, final float f) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, Float.toString(f));
    }
    
    public boolean putIntProperty(final String s, final int i) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, Integer.toString(i));
    }
    
    public boolean putLongProperty(final String s, final long i) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, Long.toString(i));
    }
    
    public boolean putProperty(final String s, final String s2) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, s2, null);
    }
    
    public boolean putProperty(final String s, final String s2, final CommentGroup commentGroup) throws GroupNameException, PropertyNameException, PropertyValueException {
        return this.putProperty(s, s2, commentGroup, true);
    }
    
    protected boolean putProperty(final String str, String filterValue, final CommentGroup commentGroup, final boolean b) throws GroupNameException, PropertyNameException, PropertyValueException {
        final String s = (this.m_currentGroup == null) ? str : (this.m_currentGroup + "." + str);
        final String propertyName = this.getPropertyName(s);
        String parentName = this.getParentName(s);
        if (filterValue == null) {
            return this.removeProperty(str);
        }
        if (parentName == null) {
            parentName = "";
        }
        final PropertyGroup orCreateGroup = this.findOrCreateGroup(parentName, false);
        if (filterValue != null && this.m_putPropertyFilter != null) {
            filterValue = this.m_putPropertyFilter.filterValue(parentName, propertyName, filterValue);
        }
        return orCreateGroup.putProperty(propertyName, filterValue, commentGroup, b);
    }
    
    public void registerGroup(final String s, final boolean b, final boolean b2, final boolean b3, final Property[] array) {
        this.registerGroup(s, b, b2, b3, array, false);
    }
    
    public void registerGroup(final String s, final boolean b, final boolean b2, final boolean b3, final Property[] array, final boolean b4) {
        try {
            final PropertyGroup orCreateGroup;
            final PropertyGroup propertyGroup = orCreateGroup = this.findOrCreateGroup(s, (boolean)(1 != 0));
            orCreateGroup.m_attributes |= 0x10;
            if (b) {
                final PropertyGroup propertyGroup2 = propertyGroup;
                propertyGroup2.m_attributes |= 0x2;
            }
            if (b2) {
                final PropertyGroup propertyGroup3 = propertyGroup;
                propertyGroup3.m_attributes |= 0x4;
            }
            if (b3) {
                final PropertyGroup propertyGroup4 = propertyGroup;
                propertyGroup4.m_attributes |= 0x8;
            }
            if (b4) {
                final PropertyGroup propertyGroup5 = propertyGroup;
                propertyGroup5.m_attributes |= 0x1;
            }
            if (array != null) {
                propertyGroup.registerProperties(array);
            }
        }
        catch (GroupNameException ex) {}
    }
    
    public boolean removeProperty(final String str) {
        final String s = (this.m_currentGroup == null) ? str : (this.m_currentGroup + "." + str);
        final String propertyName = this.getPropertyName(s);
        final String parentName = this.getParentName(s);
        boolean removeProperty = false;
        final PropertyGroup group = this.findGroup(parentName);
        if (group != null) {
            removeProperty = group.removeProperty(propertyName);
            if (removeProperty) {
                this.tryToRemoveGroup(parentName);
            }
        }
        return removeProperty;
    }
    
    public void removeGroup(final String str) {
        if (str == null) {
            return;
        }
        final String string = str + ".";
        final int length = string.length();
        final Enumeration<PropertyGroup> elements = (Enumeration<PropertyGroup>)this.m_propertyGroups.elements();
        while (elements.hasMoreElements()) {
            final PropertyGroup propertyGroup = elements.nextElement();
            if (string.regionMatches(true, 0, propertyGroup.m_name, 0, length)) {
                propertyGroup.m_properties = null;
                this.m_propertyGroups.remove(propertyGroup.m_name.toLowerCase());
            }
        }
        this.m_propertyGroups.remove(str.toLowerCase());
    }
    
    public void restrictRootGroups() {
        this.m_rootGroupsRestricted = true;
    }
    
    public boolean rootGroupsRestricted() {
        return this.m_rootGroupsRestricted;
    }
    
    protected String getPropertyNameForWriting(final Property property) {
        return property.getName();
    }
    
    protected String getPropertyNameForWriting(final String s, final Property property) {
        return property.getName();
    }
    
    protected String getPropertyValueForWriting(final Property property) {
        return property.getValue();
    }
    
    protected String getPropertyDefaultValueForWriting(final Property property) {
        return property.getDefaultValue();
    }
    
    protected String getGroupNameForWriting() {
        return this.m_writingGroup;
    }
    
    protected String getGroupNameForWriting(final String writingGroup) {
        return this.m_writingGroup = writingGroup;
    }
    
    public String getPath() {
        return this.m_path;
    }
    
    public synchronized void save(final String s, final String s2) throws SaveIOException {
        try {
            this.save(s, s2, null, true, false, false, true, true);
        }
        catch (NoSuchGroupException ex) {}
    }
    
    public synchronized void save(final String s, final String s2, final String s3) throws NoSuchGroupException, SaveIOException {
        this.save(s, s2, s3, true, false, false, true, true);
    }
    
    public synchronized void save(final String s, final String s2, final String s3, final boolean b) throws NoSuchGroupException, SaveIOException {
        this.save(s, s2, s3, b, false, false, true, true);
    }
    
    public synchronized void save(final String s, final String s2, final String s3, final boolean b, final boolean b2, final boolean b3) throws NoSuchGroupException, SaveIOException {
        this.save(s, s2, s3, b, b2, b3, true, true);
    }
    
    public synchronized void save(final String s, final String s2, final String s3, final boolean b, final boolean b2, final boolean b3, final boolean b4) throws NoSuchGroupException, SaveIOException {
        this.save(s, s2, s3, b, b2, b3, b4, true);
    }
    
    public synchronized void save(String replace, final String s, final String s2, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5) throws NoSuchGroupException, SaveIOException {
        if (this.m_propertyMonitor != null && this.m_file != null && this.m_file.lastModified() > this.m_lastModified) {
            return;
        }
        try {
            if (!PropertyManager.SEPARATOR.equals("/")) {
                replace = replace.replace('/', PropertyManager.SEPARATOR.charAt(0));
            }
            final File file = new File(replace);
            final String parent = file.getParent();
            if (parent != null) {
                final File file2 = new File(parent);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
            }
            final FileWriter fileWriter = new FileWriter(file);
            this.save(fileWriter, s, s2, b, b2, b3, b4, b5);
            fileWriter.close();
            if (this.m_file != null) {
                this.m_lastModified = this.m_file.lastModified();
            }
        }
        catch (IOException ex) {
            throw new SaveIOException(ex);
        }
    }
    
    public synchronized void save(final Writer writer, final String s, final String s2, final boolean b, final boolean b2, final boolean b3) throws NoSuchGroupException, SaveIOException {
        this.save(writer, s, s2, b, b2, b3, true, true);
    }
    
    public synchronized void save(final Writer writer, final String s, final String s2, final boolean b, final boolean b2, final boolean b3, final boolean b4) throws NoSuchGroupException, SaveIOException {
        this.save(writer, s, s2, b, b2, b3, b4, true);
    }
    
    public synchronized void save(final Writer out, final String str, final String s, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5) throws NoSuchGroupException, SaveIOException {
        final CommentGroup commentGroup = new CommentGroup();
        final StringBuffer sb = new StringBuffer();
        String str2 = "";
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(out);
            if (b4) {
                bufferedWriter.write(this.m_beginCommentGroup.getUpperComment() + PropertyManager.NEWLINE);
            }
            bufferedWriter.write("%% " + str + PropertyManager.NEWLINE);
            final DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();
            this.writePropertyVersion(bufferedWriter);
            bufferedWriter.write("%% " + dateTimeInstance.format(new Date()) + PropertyManager.NEWLINE);
            final String[] groups = this.groups(s, b, true, true);
            if (this.m_saveSorted) {
                final Vector list = new Vector<String>(groups.length);
                for (int i = 0; i < groups.length; ++i) {
                    list.add(i, groups[i]);
                }
                Collections.sort((List<Comparable>)list);
                list.copyInto(groups);
            }
            for (int j = 0; j < groups.length; ++j) {
                final String anotherString = groups[j];
                if (b || s.equalsIgnoreCase(groups[j])) {
                    final PropertyGroup group = this.findGroup(anotherString);
                    final PropertyCollection properties = this.properties(groups[j], b2, b3);
                    if (properties.size() > 0 || (group.m_attributes & 0x1) != 0x0) {
                        final StringBuffer sb2 = new StringBuffer();
                        if (b4) {
                            final CommentGroup comment = group.getComment();
                            final String upperComment = comment.getUpperComment();
                            str2 = comment.getLowerComment();
                            if (!upperComment.equals("")) {
                                sb2.append(PropertyManager.NEWLINE);
                                sb2.append(upperComment);
                            }
                        }
                        sb2.append(PropertyManager.NEWLINE);
                        sb2.append("[");
                        sb2.append(this.getGroupNameForWriting(anotherString));
                        sb2.append("]");
                        if (b4 && !str2.equals("")) {
                            sb2.append("    ");
                            sb2.append(str2);
                        }
                        bufferedWriter.write(sb2.toString() + PropertyManager.NEWLINE);
                        while (properties.hasMoreElements()) {
                            final Property property = (Property)properties.nextElement();
                            final String propertyNameForWriting = this.getPropertyNameForWriting(property);
                            final String propertyValueForWriting = this.getPropertyValueForWriting(property);
                            final StringBuffer sb3 = new StringBuffer();
                            if (b4) {
                                final CommentGroup comment2 = property.getComment();
                                final String upperComment2 = comment2.getUpperComment();
                                str2 = comment2.getLowerComment();
                                if (!upperComment2.equals("")) {
                                    sb3.append(upperComment2);
                                    sb3.append(PropertyManager.NEWLINE);
                                }
                            }
                            if (propertyValueForWriting != null) {
                                sb3.append("    ");
                                sb3.append(propertyNameForWriting);
                                sb3.append("=");
                                sb3.append(propertyValueForWriting);
                                if (b4 && !str2.equals("")) {
                                    sb3.append("    ");
                                    sb3.append(str2);
                                }
                                bufferedWriter.write(sb3.toString() + PropertyManager.NEWLINE);
                            }
                            else {
                                boolean equalsIgnoreCase;
                                try {
                                    equalsIgnoreCase = this.m_metaSchema.groupSchemaHashtable.get(anotherString.toLowerCase()).get(propertyNameForWriting.toLowerCase()).get("schemagroup").equalsIgnoreCase(anotherString);
                                }
                                catch (Exception ex2) {
                                    equalsIgnoreCase = false;
                                }
                                if (!property.getRegistered() || !equalsIgnoreCase) {
                                    continue;
                                }
                                String propertyDefaultValueForWriting = this.getPropertyDefaultValueForWriting(property);
                                if (propertyDefaultValueForWriting == null && property instanceof GroupProperty && !((GroupProperty)property).m_validate) {
                                    propertyDefaultValueForWriting = "";
                                }
                                if (propertyDefaultValueForWriting == null) {
                                    continue;
                                }
                                if (b5) {
                                    sb3.append("#");
                                }
                                sb3.append("    ");
                                sb3.append(propertyNameForWriting);
                                sb3.append("=");
                                sb3.append(propertyDefaultValueForWriting);
                                if (b5) {
                                    sb3.append("    # default value");
                                }
                                if (b4 && !str2.equals("")) {
                                    sb3.append("    ");
                                    sb3.append(str2);
                                }
                                bufferedWriter.write(sb3.toString() + PropertyManager.NEWLINE);
                            }
                        }
                    }
                }
            }
            if (b4) {
                bufferedWriter.write(this.m_endCommentGroup.getUpperComment());
            }
            bufferedWriter.close();
        }
        catch (IOException ex) {
            throw new SaveIOException(ex);
        }
    }
    
    public void setCurrentGroup(final String currentGroup) {
        if (currentGroup == null || currentGroup.equals("")) {
            this.m_currentGroup = null;
        }
        else {
            this.m_currentGroup = currentGroup;
        }
    }
    
    public void setGroupComment(final String s, final CommentGroup comment) {
        try {
            this.findOrCreateGroup(s, false).setComment(comment);
        }
        catch (GroupNameException ex) {}
    }
    
    public void setGetPropertyFilter(final IPropertyValueFilter getPropertyFilter) {
        this.m_getPropertyFilter = getPropertyFilter;
    }
    
    public void setPutPropertyFilter(final IPropertyValueFilter putPropertyFilter) {
        this.m_putPropertyFilter = putPropertyFilter;
    }
    
    private void tryToRemoveGroup(final String s) {
        if (s == null) {
            return;
        }
        final PropertyGroup group = this.findGroup(s);
        if (group == null) {
            return;
        }
        if ((group.m_attributes & 0x10) != 0x0) {
            return;
        }
        if (!group.m_properties.isEmpty()) {
            return;
        }
        final Enumeration<PropertyGroup> elements = this.m_propertyGroups.elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement().m_parent == group) {
                return;
            }
        }
        this.m_propertyGroups.remove(s.toLowerCase());
        final String parentName = this.getParentName(s);
        if (parentName != null) {
            this.tryToRemoveGroup(parentName);
        }
    }
    
    public void unregisterGroup(final String s) {
        final PropertyGroup group = this.findGroup(s);
        if (group != null) {
            final PropertyGroup propertyGroup = group;
            propertyGroup.m_attributes &= 0xFFFFFFE0;
            this.tryToRemoveGroup(s);
        }
    }
    
    public void unrestrictRootGroups() {
        this.m_rootGroupsRestricted = false;
    }
    
    protected Hashtable getPropertyHashtable() {
        return new Hashtable();
    }
    
    protected Hashtable getGroupHashtable() {
        return new Hashtable();
    }
    
    protected Hashtable getHashtable() {
        return new Hashtable();
    }
    
    protected boolean chkPropertyVersion(final String s) {
        return true;
    }
    
    protected void writePropertyVersion(final BufferedWriter bufferedWriter) throws IOException {
    }
    
    public PropertyTransferObject makePropertyTransferObjectS(final String s) {
        return new PropertyTransferObject(this, s);
    }
    
    public PropertyTransferObject makePropertyTransferObject(final String s) throws RemoteException {
        return new PropertyTransferObject(this, s);
    }
    
    public Hashtable getGroupSchemaHashtable() {
        Hashtable groupSchemaHashtableS = null;
        final MetaSchema metaSchema = this.getMetaSchema();
        if (metaSchema != null) {
            groupSchemaHashtableS = metaSchema.getGroupSchemaHashtableS();
        }
        return groupSchemaHashtableS;
    }
    
    public Hashtable getGroupAttributeHashtable(final String s) {
        Hashtable groupAttributeHashtableS = null;
        final MetaSchema metaSchema = this.getMetaSchema();
        if (metaSchema != null) {
            groupAttributeHashtableS = metaSchema.getGroupAttributeHashtableS(s);
        }
        return groupAttributeHashtableS;
    }
    
    public Hashtable getPropertySchemaHashtable(final String s) {
        Hashtable propertySchemaHashtableS = null;
        final MetaSchema metaSchema = this.getMetaSchema();
        if (metaSchema != null) {
            propertySchemaHashtableS = metaSchema.getPropertySchemaHashtableS(s);
        }
        return propertySchemaHashtableS;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s) {
        return this.getCategorySchemaHashtable(s, (String[])null);
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String s2) {
        return this.getCategorySchemaHashtable(s, (String[])((s2 != null) ? new String[] { s2 } : null));
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String[] array) {
        Hashtable categorySchemaHashtableS = null;
        final MetaSchema metaSchema = this.getMetaSchema();
        if (metaSchema != null) {
            categorySchemaHashtableS = metaSchema.getCategorySchemaHashtableS(s, array);
        }
        return categorySchemaHashtableS;
    }
    
    public Hashtable getCategoryAttributeHashtable(final String s) {
        Hashtable categoryAttributeHashtableS = null;
        final MetaSchema metaSchema = this.getMetaSchema();
        if (metaSchema != null) {
            categoryAttributeHashtableS = metaSchema.getCategoryAttributeHashtableS(s);
        }
        return categoryAttributeHashtableS;
    }
    
    public Hashtable makePropertyValueHash(final String s) {
        String propertyNameForWriting = null;
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        try {
            final PropertyType propertyType = this.getPropertyType(s);
            if (propertyType.getPropertyType() == PropertyManager.PROPERTY_METRIC || propertyType.getPropertyType() == PropertyManager.PROPERTY_WILDCARD || propertyType.getPropertyType() == PropertyManager.PROPERTY_NAME) {
                final PropertyList list = new PropertyList();
                this.makePropertyListElements(list, null, s);
                final Vector propertyElements = list.getPropertyElements();
                for (int i = 0; i < propertyElements.size(); ++i) {
                    final PropertyList.PropertyElement propertyElement = propertyElements.elementAt(i);
                    final String propertyName = propertyElement.getPropertyName();
                    final Vector propertyValues = propertyElement.getPropertyValues();
                    if (propertyValues != null) {
                        final String[] value = new String[propertyValues.size()];
                        for (int j = 0; j < propertyValues.size(); ++j) {
                            value[j] = propertyValues.elementAt(j);
                        }
                        hashtable.put(propertyName, value);
                    }
                }
            }
            else if (propertyType.getPropertyType() == PropertyManager.PROPERTY_GROUP) {
                this.properties(s, true, true);
                final PropertyCollection properties = this.properties(s, true, true);
                while (properties.hasMoreElements()) {
                    final Property property = properties.nextElement();
                    try {
                        if (property == null) {
                            continue;
                        }
                        propertyNameForWriting = this.getPropertyNameForWriting(s, property);
                        final String[] arrayProperty = parseArrayProperty(property.getValueOrDefault(), property.arraySeparator(), property.canHaveMultipleValues());
                        if (arrayProperty == null) {
                            continue;
                        }
                        hashtable.put(propertyNameForWriting, arrayProperty);
                    }
                    catch (Throwable t) {
                        Tools.px(t, "PropertyTransferObject:  Failed to load property " + propertyNameForWriting);
                    }
                }
            }
        }
        catch (Throwable t2) {
            Tools.px(t2, "PropertyTransferObject:  Failed loading properties.");
        }
        return hashtable;
    }
    
    public synchronized void setConfiguration(final String s, final String str, final Hashtable hashtable) throws NoSuchGroupException, PropertyValuesException {
        final Hashtable hashtable2 = new Hashtable<String, String>();
        final Hashtable propertySchemaHashtable = this.getPropertySchemaHashtable(s);
        String key = null;
        String value = null;
        if (propertySchemaHashtable == null) {
            throw new NoSuchGroupException(s);
        }
        final Enumeration<String> keys = propertySchemaHashtable.keys();
        while (keys.hasMoreElements()) {
            try {
                key = keys.nextElement();
                final String string = str + "." + key;
                value = hashtable.get(key);
                if (value == null) {
                    continue;
                }
                final String property = this.getProperty(string);
                if (value.equals((property == null) ? "" : property)) {
                    continue;
                }
                this.putProperty(string, value);
            }
            catch (PropertyException ex) {
                hashtable2.put(key, value);
            }
        }
        if (hashtable2.size() > 0) {
            throw new PropertyValuesException(hashtable2);
        }
    }
    
    public synchronized void clearGroup(final String str) throws PropertyException {
        final PropertyCollection properties = this.properties(str, true, true);
        while (properties.hasMoreElements()) {
            this.putProperty(str + "." + ((Property)properties.nextElement()).getName(), null);
        }
    }
    
    public synchronized int updateFromClient(final String s, final IPropertyValueProvider propertyValueProvider, final RemoteStub remoteStub, final String str, final Vector vector) throws RemoteException, SaveIOException {
        int n = 0;
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PRPMsgBundle");
        try {
            PropertyCollection properties;
            try {
                properties = this.properties(str, true, true);
            }
            catch (Exception ex3) {
                throw new PropertyException(progressResources.getTranString("Invalid group:", (Object)str), (Object[])null);
            }
            final Enumeration<String> elements = vector.elements();
            while (elements.hasMoreElements()) {
                final String s2 = elements.nextElement();
                Property value = properties.get(s2);
                if (value == null) {
                    continue;
                }
                Object valueRemote;
                try {
                    valueRemote = propertyValueProvider.getValueRemote(s2);
                }
                catch (RemoteException ex) {
                    Tools.px(ex, "Trouble accessing remote property in PVS: " + s2);
                    continue;
                }
                if (s2.equalsIgnoreCase("environment") && !(value instanceof GroupProperty)) {
                    if (value.getValue().equals("")) {
                        value = new GroupProperty(s2, null, value.getDefaultValue(), this, "Environment.", true, 0, true);
                    }
                    else {
                        value = new GroupProperty(s2, value.getValue(), value.getDefaultValue(), this, "Environment.", true, 0, true);
                    }
                }
                if (value instanceof GroupProperty && ((GroupProperty)value).getSubsume()) {
                    final PropertyTransferObject propertyTransferObject = (PropertyTransferObject)valueRemote;
                    final GroupProperty groupProperty = (GroupProperty)value;
                    final String groupName = propertyTransferObject.getGroupName();
                    final String valueOrDefault = value.getValueOrDefault();
                    final String substring = groupName.substring(groupProperty.getPrefix().length());
                    try {
                        this.clearGroup(groupName);
                    }
                    catch (NoSuchGroupException ex4) {}
                    int n2 = 0;
                    final Enumeration elements2 = propertyTransferObject.elements.elements();
                    while (elements2.hasMoreElements()) {
                        final PropertyTransferObject.Element element = elements2.nextElement();
                        this.putProperty(groupName + "." + element.key(), element.values()[0]);
                        ++n2;
                    }
                    if (n2 == 0) {
                        this.putProperty(str + "." + s2, null);
                    }
                    else if (valueOrDefault == null || !substring.equals(valueOrDefault)) {
                        this.putProperty(str + "." + s2, substring);
                    }
                }
                else {
                    final String[] array = (String[])valueRemote;
                    if (this.arrayEquals(array, new String[] { value.getValueOrDefault() })) {
                        continue;
                    }
                    this.putArrayProperty(str + "." + s2, array);
                }
                ++n;
            }
            if (n > 0) {
                this.save(s, "Properties File");
                if (this.m_eventBroker != null) {
                    this.m_eventBroker.postEvent(new EPropertiesChanged(remoteStub, propertyValueProvider));
                }
            }
        }
        catch (SaveIOException ex2) {
            Excp.print(ex2, "Error saving properties. File: " + s);
            throw ex2;
        }
        catch (Throwable cause) {
            Excp.print(cause, "Error whilst saving properties. File: " + s);
            throw new RemoteException(progressResources.getTranString("Can't write property file", (Object)s), cause);
        }
        return n;
    }
    
    boolean arrayEquals(final String[] array, final String[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array2.length; ++j) {
                if (!array[i].equals(array2[i])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void restoreDefaults(final String s, final IPropertyValueProvider propertyValueProvider, final RemoteStub remoteStub, final String str, final Vector vector) throws RemoteException {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PRPMsgBundle");
        try {
            int n = 0;
            PropertyCollection properties;
            try {
                properties = this.properties(str, true, true);
            }
            catch (Throwable t) {
                throw new PropertyException(progressResources.getTranString("Invalid group:", (Object)str), (Object[])null);
            }
            final Enumeration<String> elements = vector.elements();
            while (elements.hasMoreElements()) {
                final String str2 = elements.nextElement();
                try {
                    if (properties.get(str2) == null) {
                        throw new PropertyException(progressResources.getTranString("Client trying to clear unknown property value."), (Object[])null);
                    }
                    this.putProperty(str + "." + str2, null);
                    ++n;
                }
                catch (Exception ex2) {
                    throw new PropertyException(progressResources.getTranString("Client trying to clear unknown property value."), (Object[])null);
                }
            }
            if (n > 0) {
                this.save(s, "Properties File");
                try {
                    if (this.m_eventBroker != null) {
                        this.m_eventBroker.postEvent(new EPropertiesChanged(remoteStub, propertyValueProvider));
                    }
                }
                catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception cause) {
            throw new RemoteException(progressResources.getTranString("Failed to clear property values."), cause);
        }
    }
    
    public Vector expandPropertyNames(final String s) {
        final String lowerCase = s.toLowerCase();
        final String anObject = "*";
        final String s2 = ".";
        final Vector<String> vector = new Vector<String>();
        final int index = lowerCase.indexOf(46);
        final int lastIndex = lowerCase.lastIndexOf(46);
        final String str = (index == -1) ? "" : lowerCase.substring(0, index);
        final String s3 = (lastIndex == -1) ? "" : lowerCase.substring(lastIndex + 1);
        final String str2 = (index == -1 || lastIndex == -1) ? "" : lowerCase.substring(0, lastIndex);
        if (this.m_propertyNames.get(str + "." + s3) == null) {
            return vector;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str2, s2, true);
        final Vector vector2 = new Vector<String>();
        while (stringTokenizer.hasMoreTokens()) {
            vector2.addElement(stringTokenizer.nextToken());
        }
        final Enumeration<String> keys = this.m_propertyGroups.keys();
        while (keys.hasMoreElements()) {
            final String s4 = keys.nextElement();
            int n = 1;
            final StringTokenizer stringTokenizer2 = new StringTokenizer(s4, s2, true);
            if (vector2.size() != stringTokenizer2.countTokens()) {
                continue;
            }
            for (int index2 = 0; index2 < vector2.size() && n != 0; ++index2) {
                final StringTokenizer stringTokenizer3 = new StringTokenizer(vector2.elementAt(index2), anObject, true);
                String s5 = stringTokenizer2.nextToken();
                if (stringTokenizer3.countTokens() == 1) {
                    final String nextToken = stringTokenizer3.nextToken();
                    if (s5.equals(nextToken) || nextToken.equals(anObject)) {
                        n = 1;
                    }
                    else {
                        n = 0;
                    }
                }
                else {
                    final Vector<String> vector3 = new Vector<String>();
                    while (stringTokenizer3.hasMoreTokens()) {
                        vector3.addElement((String)stringTokenizer3.nextElement());
                    }
                    if (vector3.size() > 0) {
                        final String prefix = vector3.elementAt(0);
                        if (!prefix.equals(anObject)) {
                            if (!s5.startsWith(prefix)) {
                                n = 0;
                                continue;
                            }
                            s5 = s5.substring(prefix.length());
                            vector3.removeElementAt(0);
                        }
                    }
                    if (vector3.size() > 0) {
                        final int n2 = vector3.size() - 1;
                        final String suffix = vector3.elementAt(n2);
                        if (!suffix.equals(anObject)) {
                            if (!s5.endsWith(suffix)) {
                                n = 0;
                                continue;
                            }
                            s5 = s5.substring(0, s5.length() - suffix.length());
                            vector3.removeElementAt(n2);
                        }
                    }
                    for (int size = vector3.size(), index3 = 0; index3 < size && n != 0; ++index3) {
                        final String s6 = vector3.elementAt(index3);
                        if (!s6.equals(anObject)) {
                            if (s5.startsWith(s6)) {
                                s5 = s5.substring(s6.length());
                            }
                            else {
                                while (!s5.startsWith(s6) && n != 0) {
                                    if (s5.length() >= 1) {
                                        s5 = s5.substring(1);
                                    }
                                    else {
                                        n = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (n == 0) {
                continue;
            }
            vector.addElement(s4 + "." + s3);
        }
        return vector;
    }
    
    public void processMetric2(final PropertyList list, final String s, final String s2) throws PropertyException {
    }
    
    public void processMetric(final PropertyList list, final String s, final String s2) {
        try {
            this.processMetric2(list, s, s2);
        }
        catch (PropertyException ex) {}
    }
    
    public PropertyType getPropertyType(String substring) {
        String substring2 = null;
        String[] arrayProperty = null;
        int n = PropertyManager.PROPERTY_INVALID;
        if (substring.toLowerCase().startsWith("metric.")) {
            n = PropertyManager.PROPERTY_METRIC;
        }
        else if (substring.indexOf("*") != -1) {
            n = PropertyManager.PROPERTY_WILDCARD;
        }
        else if (this.findGroup(substring) != null) {
            n = PropertyManager.PROPERTY_GROUP;
        }
        else {
            Property registeredProperty = null;
            final int lastIndex = substring.lastIndexOf(46);
            substring2 = substring.substring(0, lastIndex);
            substring = substring.substring(lastIndex + 1);
            final PropertyGroup group = this.findGroup(substring2);
            if (group != null) {
                registeredProperty = group.findRegisteredProperty(substring);
            }
            if (registeredProperty != null) {
                n = PropertyManager.PROPERTY_NAME;
                final String valueOrDefault = registeredProperty.getValueOrDefault();
                if (registeredProperty.canHaveMultipleValues()) {
                    arrayProperty = parseArrayProperty(valueOrDefault, registeredProperty.arraySeparator());
                }
                else {
                    arrayProperty = new String[] { valueOrDefault };
                }
            }
        }
        return new PropertyType(substring2, substring, arrayProperty, n);
    }
    
    public void makePropertyListElements(final PropertyList list, final String s, final String s2) {
        try {
            final PropertyType propertyType = this.getPropertyType(s2);
            if (propertyType.getPropertyType() == PropertyManager.PROPERTY_METRIC) {
                this.processMetric(list, s, s2);
            }
            else if (propertyType.getPropertyType() == PropertyManager.PROPERTY_WILDCARD) {
                final Vector expandPropertyNames = this.expandPropertyNames(s2);
                for (int i = 0; i < expandPropertyNames.size(); ++i) {
                    this.makePropertyListElements(list, s, expandPropertyNames.elementAt(i));
                }
            }
            else if (propertyType.getPropertyType() == PropertyManager.PROPERTY_NAME) {
                list.addProperty(s, propertyType.getPropertyGroup(), propertyType.getPropertyName(), propertyType.getPropertyValues());
            }
            else if (propertyType.getPropertyType() == PropertyManager.PROPERTY_GROUP) {
                String[] arrayProperty = null;
                try {
                    final PropertyCollection properties = this.properties(s2, true, true);
                    while (properties.hasMoreElements()) {
                        final Property property = (Property)properties.nextElement();
                        final String name = property.getName();
                        if (property != null) {
                            arrayProperty = parseArrayProperty(property.getValueOrDefault(), property.arraySeparator());
                            list.addProperty(s, propertyType.getPropertyGroup(), name, arrayProperty);
                        }
                    }
                }
                catch (NoSuchGroupException ex) {
                    list.addProperty(s, propertyType.getPropertyGroup(), null, arrayProperty);
                }
            }
        }
        finally {}
    }
    
    public boolean invokeValidationMethod(final String className, final String s, final String s2, final String s3, final Vector vector) {
        boolean booleanValue;
        try {
            final Class<?> forName = Class.forName(className);
            final Object instance = forName.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            booleanValue = (boolean)forName.getMethod("isValid", String.class, String.class).invoke(instance, s2, s3);
            if (!booleanValue) {
                final String s4 = (String)forName.getMethod("getErrorMessage", (Class[])new Class[0]).invoke(instance, new Object[0]);
                if (s4 != null && s4.length() > 0) {
                    vector.add(new String[] { s, s3, s4 });
                }
            }
        }
        catch (Exception ex) {
            booleanValue = false;
        }
        return booleanValue;
    }
    
    public Vector validateProperties(final String[] array, final String str, final Hashtable hashtable, final Hashtable hashtable2) {
        final Vector<String[]> vector = new Vector<String[]>();
        final PropertyGroup group = this.findGroup(str);
        for (int i = 0; i < array.length; ++i) {
            final Hashtable categoryAttributeHashtable = this.getCategoryAttributeHashtable(array[i]);
            if (categoryAttributeHashtable != null) {
                final String[] array2 = categoryAttributeHashtable.get("fields");
                for (int n = 0; array2 != null && n < array2.length; ++n) {
                    final String str2 = array2[n];
                    final Hashtable<K, String> hashtable3 = hashtable.get(str2);
                    final String string = str + "." + str2;
                    String str3 = hashtable2.get(string);
                    final String s = hashtable3.get("type");
                    final String str4 = hashtable3.get("displayname");
                    final String s2 = hashtable3.get("validationmethod");
                    Property registeredProperty = null;
                    try {
                        if (s.equals("boolean")) {
                            str3 = ((str3 == null) ? "false" : "true");
                        }
                        registeredProperty = group.findRegisteredProperty(str2);
                        if (registeredProperty == null) {
                            throw new Throwable("You must specify a value for property " + str2);
                        }
                        if (s.equalsIgnoreCase("group") && registeredProperty.canHaveMultipleValues()) {
                            final StringTokenizer stringTokenizer = new StringTokenizer(str3, ",", false);
                            for (int j = 0; j < stringTokenizer.countTokens(); ++j) {
                                registeredProperty.validateValue(stringTokenizer.nextToken());
                            }
                        }
                        else {
                            registeredProperty.validateValue(str3);
                        }
                        if (s2 != null && s2.length() > 0) {
                            this.invokeValidationMethod(s2, string, str4, str3, vector);
                        }
                    }
                    catch (PropertyValueException ex) {
                        String str5 = null;
                        String str6 = null;
                        final StringBuffer sb = new StringBuffer("Invalid value for '" + str4 + "' property.  ");
                        String str7;
                        if (registeredProperty instanceof IntProperty) {
                            final IntProperty intProperty = (IntProperty)registeredProperty;
                            str5 = intProperty.getMin() + "";
                            str6 = intProperty.getMax() + "";
                            str7 = intProperty.getDefaultValue();
                        }
                        else if (registeredProperty instanceof LongProperty) {
                            final LongProperty longProperty = (LongProperty)registeredProperty;
                            str5 = longProperty.getMin() + "";
                            str6 = longProperty.getMax() + "";
                            str7 = longProperty.getDefaultValue();
                        }
                        else if (registeredProperty instanceof FloatProperty) {
                            final FloatProperty floatProperty = (FloatProperty)registeredProperty;
                            str5 = floatProperty.getMin() + "";
                            str6 = floatProperty.getMax() + "";
                            str7 = floatProperty.getDefaultValue();
                        }
                        else if (registeredProperty instanceof DoubleProperty) {
                            final DoubleProperty doubleProperty = (DoubleProperty)registeredProperty;
                            str5 = doubleProperty.getMin() + "";
                            str6 = doubleProperty.getMax() + "";
                            str7 = doubleProperty.getDefaultValue();
                        }
                        else if (registeredProperty instanceof EnumProperty) {
                            final EnumProperty enumProperty = (EnumProperty)registeredProperty;
                            final String[] enum1 = enumProperty.getEnum();
                            sb.append("Valid values are: ");
                            for (int k = 0; k < enum1.length; ++k) {
                                if (k > 0) {
                                    sb.append(", ");
                                }
                                sb.append(enum1[k]);
                            }
                            sb.append(".  ");
                            str7 = enumProperty.getDefaultValue();
                        }
                        else {
                            str7 = registeredProperty.getDefaultValue();
                        }
                        if (str5 != null && str6 != null) {
                            sb.append("Range is " + str5 + ".." + str6 + ".  ");
                        }
                        if (str7 != null) {
                            sb.append("Default is " + str7 + ".  ");
                        }
                        vector.add(new String[] { string, str3, sb.toString() });
                    }
                    catch (Throwable t) {
                        vector.add(new String[] { (string == null) ? "Unknown" : string, (str3 == null) ? "Unknown" : str3, t.toString() });
                    }
                }
            }
        }
        return vector;
    }
    
    public void setOrderedPopertyHashTable(final boolean getOrderedPropertyHashTable) {
        this.m_getOrderedPropertyHashTable = getOrderedPropertyHashTable;
    }
    
    static {
        PropertyManager.m_log = null;
        NEWLINE = System.getProperty("line.separator");
        SEPARATOR = System.getProperty("file.separator");
        PropertyManager.PROPERTY_GROUP = 1;
        PropertyManager.PROPERTY_NAME = 2;
        PropertyManager.PROPERTY_WILDCARD = 3;
        PropertyManager.PROPERTY_METRIC = 4;
        PropertyManager.PROPERTY_INVALID = 5;
    }
    
    public static class OrderedHashtable extends Hashtable
    {
        Vector orderedKeyVector;
        
        public OrderedHashtable() {
            this.orderedKeyVector = new Vector();
        }
        
        public synchronized Enumeration keys() {
            return this.orderedKeyVector.elements();
        }
        
        public synchronized Enumeration elements() {
            final Vector<Object> vector = new Vector<Object>();
            final Enumeration keys = this.keys();
            while (keys.hasMoreElements()) {
                vector.addElement(super.get(keys.nextElement()));
            }
            return vector.elements();
        }
        
        public synchronized Object put(final Object key, final Object value) throws NullPointerException {
            Object value2 = null;
            if (!this.orderedKeyVector.contains(key)) {
                this.orderedKeyVector.addElement(key);
            }
            else {
                value2 = this.get(key);
                super.remove(key);
            }
            super.put(key, value);
            return value2;
        }
        
        public synchronized Object remove(final Object o) {
            Object value = null;
            if (this.orderedKeyVector.contains(o)) {
                this.orderedKeyVector.removeElement(o);
                value = super.get(o);
                super.remove(o);
            }
            return value;
        }
        
        public synchronized void clear() {
            super.clear();
            this.orderedKeyVector.removeAllElements();
        }
        
        public synchronized String toString() {
            final Enumeration<String> elements = this.orderedKeyVector.elements();
            String str = "{";
            for (int i = 0; i < super.size(); ++i) {
                if (i > 0) {
                    str += ", ";
                }
                final String nextElement = elements.nextElement();
                str = str + nextElement + "=" + (String)super.get(nextElement);
            }
            return str + "}";
        }
    }
    
    public static class SortedHashtable extends Hashtable
    {
        Vector sortedKeyVector;
        
        public SortedHashtable() {
            this.sortedKeyVector = new Vector();
        }
        
        public synchronized Enumeration keys() {
            return this.sortedKeyVector.elements();
        }
        
        public synchronized Enumeration elements() {
            final Vector<Object> vector = new Vector<Object>();
            final Enumeration keys = this.keys();
            while (keys.hasMoreElements()) {
                vector.addElement(super.get(keys.nextElement()));
            }
            return vector.elements();
        }
        
        public synchronized Object put(final Object key, final Object value) throws NullPointerException {
            Object value2 = null;
            if (!this.sortedKeyVector.contains(key)) {
                int n;
                for (n = 0; n < this.sortedKeyVector.size() && key.toString().compareTo((String)this.sortedKeyVector.elementAt(n)) > 0; ++n) {}
                this.sortedKeyVector.insertElementAt(key, n);
            }
            else {
                value2 = this.get(key);
                super.remove(key);
            }
            super.put(key, value);
            return value2;
        }
        
        public synchronized Object remove(final Object o) {
            Object value = null;
            if (this.sortedKeyVector.contains(o)) {
                this.sortedKeyVector.removeElement(o);
                value = super.get(o);
                super.remove(o);
            }
            return value;
        }
        
        public synchronized void clear() {
            super.clear();
            this.sortedKeyVector.removeAllElements();
        }
        
        public synchronized String toString() {
            final Enumeration<String> elements = this.sortedKeyVector.elements();
            String str = "{";
            for (int i = 0; i < super.size(); ++i) {
                if (i > 0) {
                    str += ", ";
                }
                final String nextElement = elements.nextElement();
                str = str + nextElement + "=" + (String)super.get(nextElement);
            }
            return str + "}";
        }
    }
    
    public static class CommentGroup implements Serializable
    {
        String upperComment;
        String lowerComment;
        
        public CommentGroup() {
            this.upperComment = "";
            this.lowerComment = "";
        }
        
        public String getUpperComment() {
            return this.upperComment;
        }
        
        public String getLowerComment() {
            return this.lowerComment;
        }
        
        public void setUpperComment(final String s) {
            if (this.upperComment.equals("")) {
                this.upperComment = s;
            }
            else {
                this.upperComment = this.upperComment + PropertyManager.NEWLINE + s;
            }
        }
        
        public void setLowerComment(final String str) {
            this.lowerComment += str;
        }
    }
    
    public static class PropertyException extends ProException
    {
        public PropertyException(final String s, final Object[] array) {
            super(s, array);
        }
    }
    
    public static class NoEventBrokerSpecified extends PropertyException
    {
        public NoEventBrokerSpecified() {
            this("");
        }
        
        public NoEventBrokerSpecified(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "No event broker was specified for this property manager.", (Object)s), (Object[])null);
        }
    }
    
    public static class MultipleFilesSpecified extends PropertyException
    {
        public MultipleFilesSpecified() {
            this("");
        }
        
        public MultipleFilesSpecified(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "No event broker was specified for this property manager.", (Object)s), (Object[])null);
        }
    }
    
    public static class GroupNameException extends PropertyException
    {
        public GroupNameException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property group name", new Object[] { s }), (Object[])null);
        }
        
        public String getGroupName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class NoSuchGroupException extends PropertyException
    {
        public NoSuchGroupException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "No such group", new Object[] { s }), (Object[])null);
        }
        
        public String getGroupName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class PropertyWithoutCorrespondingGroup extends PropertyException
    {
        public PropertyWithoutCorrespondingGroup(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Property without group definition", new Object[] { s }), (Object[])null);
        }
        
        public String getPropertyName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class PropertyNameException extends PropertyException
    {
        public PropertyNameException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property name", new Object[] { s }), (Object[])null);
        }
        
        public String getPropertyName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class PropertyValueException extends PropertyException
    {
        public PropertyValueException(final String s, final String s2) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property value for property", new Object[] { s2, s }), (Object[])null);
        }
        
        public String getPropertyName() {
            return (String)this.getArgument(0);
        }
        
        public String getPropertyValue() {
            return (String)this.getArgument(1);
        }
    }
    
    public static class PropertyValuesException extends PropertyException
    {
        Hashtable invalidPairs;
        
        public PropertyValuesException(final Hashtable invalidPairs) {
            super("Invalid property name/value pairs", (Object[])null);
            this.invalidPairs = null;
            this.invalidPairs = invalidPairs;
        }
        
        public Hashtable getInvalidPairs() {
            return this.invalidPairs;
        }
        
        public String getMessage() {
            final StringBuffer sb = new StringBuffer(super.getMessage());
            if (this.invalidPairs != null) {
                final Enumeration<String> keys = (Enumeration<String>)this.invalidPairs.keys();
                while (keys.hasMoreElements()) {
                    final String s = keys.nextElement();
                    final String str = this.invalidPairs.get(s);
                    sb.append(PropertyManager.NEWLINE);
                    sb.append(s);
                    sb.append("=");
                    sb.append(str);
                }
            }
            return sb.toString();
        }
    }
    
    public static class PropertySyntaxException extends PropertyException
    {
        public PropertySyntaxException(final int value) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property syntax on line number", new Object[] { new Integer(value) }), (Object[])null);
        }
        
        public int getLineNumber() {
            return (int)this.getArgument(0);
        }
    }
    
    public static class PropertyVersionException extends PropertyException
    {
        public PropertyVersionException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property version specified", new Object[] { s.toString() }), (Object[])null);
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class LoadFileNotFoundException extends PropertyException
    {
        public LoadFileNotFoundException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Property file not found", new Object[] { s }), (Object[])null);
        }
        
        public LoadFileNotFoundException(final FileNotFoundException previous) {
            this(previous.getMessage());
            this.setPrevious(previous);
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class SchemaFileNotFoundException extends PropertyException
    {
        public SchemaFileNotFoundException(final FileNotFoundException previous) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Property schema file not found", new Object[] { previous.getMessage() }), (Object[])null);
            this.setPrevious(previous);
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class LoadIOException extends PropertyException
    {
        public LoadIOException(final IOException ex) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Input error reading property file", new Object[] { ex.toString() }), (Object[])null);
        }
        
        protected LoadIOException(final String s, final Object[] array) {
            super(s, array);
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class SaveIOException extends PropertyException
    {
        public SaveIOException(final IOException ex) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Output error writing property file", new Object[] { ex.toString() }), (Object[])null);
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class Property implements Serializable
    {
        protected String m_name;
        protected String m_value;
        protected String m_defaultValue;
        protected int m_tag;
        protected boolean m_registered;
        protected boolean m_canHaveMulipleValues;
        protected String m_multipleValueSeparator;
        protected final String m_defaultMultipleValueSeparator = ",";
        protected CommentGroup m_comment;
        
        public Property(final String s) {
            this(s, null, null, 0, true, null);
        }
        
        public Property(final String s, final CommentGroup commentGroup) {
            this(s, null, null, 0, true, commentGroup);
        }
        
        public Property(final String s, final String s2) {
            this(s, null, s2, 0, true, null);
        }
        
        public Property(final String s, final String s2, final CommentGroup commentGroup) {
            this(s, null, s2, 0, true, commentGroup);
        }
        
        public Property(final String s, final String s2, final int n) {
            this(s, null, s2, n, true, null);
        }
        
        public Property(final String s, final String s2, final int n, final CommentGroup commentGroup) {
            this(s, null, s2, n, true, commentGroup);
        }
        
        protected Property(final String s, final String s2, final boolean b) {
            this(s, s2, null, 0, b, null);
        }
        
        protected Property(final String s, final String s2, final boolean b, final CommentGroup commentGroup) {
            this(s, s2, null, 0, b, commentGroup);
        }
        
        protected Property(final String s, final String s2, final String s3, final int n, final boolean b) {
            this(s, s2, s3, n, b, null);
        }
        
        protected Property(final String name, final String value, final String defaultValue, final int tag, final boolean registered, final CommentGroup comment) {
            this.m_multipleValueSeparator = null;
            this.m_comment = new CommentGroup();
            this.m_name = name;
            if (name.equalsIgnoreCase("Form") || name.equalsIgnoreCase("Parent") || name.toLowerCase().startsWith("restrict") || name.equalsIgnoreCase("UI") || name.toLowerCase().startsWith("java") || name.equalsIgnoreCase("defaultChoice")) {}
            this.m_value = value;
            this.m_defaultValue = defaultValue;
            this.m_tag = tag;
            this.m_registered = registered;
            if (comment != null) {
                this.m_comment = comment;
            }
        }
        
        protected void copyValues(final Property property) {
            property.setMultipleValues(this.m_canHaveMulipleValues);
            property.setArraySeparator(this.m_multipleValueSeparator);
        }
        
        public Object clone() {
            final Property property = new Property(this.m_name, this.m_value, this.m_defaultValue, this.m_tag, this.m_registered);
            this.copyValues(property);
            return property;
        }
        
        public final void setMultipleValues(final boolean canHaveMulipleValues) {
            this.m_canHaveMulipleValues = canHaveMulipleValues;
            if (this.m_canHaveMulipleValues) {}
        }
        
        public final boolean canHaveMultipleValues() {
            return this.m_canHaveMulipleValues;
        }
        
        public final String arraySeparator() {
            if (this.m_multipleValueSeparator != null) {
                return this.m_multipleValueSeparator;
            }
            return ",";
        }
        
        public final void setArraySeparator(final String multipleValueSeparator) {
            this.m_multipleValueSeparator = multipleValueSeparator;
        }
        
        public final String getDefaultValue() {
            return this.m_defaultValue;
        }
        
        public final String getName() {
            return this.m_name;
        }
        
        public final int getTag() {
            return this.m_tag;
        }
        
        public final String getValue() {
            return this.m_value;
        }
        
        public final String getValueOrDefault() {
            String s = this.m_value;
            if (s == null) {
                s = this.m_defaultValue;
            }
            return s;
        }
        
        public final boolean getRegistered() {
            return this.m_registered;
        }
        
        public final CommentGroup getComment() {
            return this.m_comment;
        }
        
        public final String getUpperComment() {
            return this.m_comment.upperComment;
        }
        
        public final String getLowerComment() {
            return this.m_comment.lowerComment;
        }
        
        public final void setDefaultValue(final String defaultValue) throws PropertyValueException {
            this.validateValue(defaultValue);
            this.m_defaultValue = defaultValue;
        }
        
        public final void setRegistered(final boolean registered) {
            this.m_registered = registered;
        }
        
        public final void setValue(final String s) throws PropertyValueException {
            this.setValue(s, true);
        }
        
        protected final void setValue(final String value, final boolean b) throws PropertyValueException {
            if (value != null && b) {
                String[] arrayProperty;
                if (!this.canHaveMultipleValues()) {
                    arrayProperty = new String[] { value };
                }
                else {
                    arrayProperty = PropertyManager.parseArrayProperty(value, this.arraySeparator());
                }
                for (int i = 0; i < arrayProperty.length; ++i) {
                    this.validateValue(arrayProperty[i]);
                }
            }
            this.m_value = value;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
        }
        
        public final void setComment(final CommentGroup comment) {
            if (comment != null) {
                this.m_comment = comment;
            }
        }
        
        public final void setUpperComment(final String upperComment) {
            this.m_comment.upperComment = upperComment;
        }
        
        public final void setLowerComment(final String lowerComment) {
            this.m_comment.lowerComment = lowerComment;
        }
    }
    
    public static class BooleanProperty extends Property
    {
        public BooleanProperty(final String s) throws PropertyValueException {
            this(s, null, null, 0, true);
        }
        
        public BooleanProperty(final String s, final String s2) throws PropertyValueException {
            this(s, null, s2, 0, true);
        }
        
        public BooleanProperty(final String s, final String s2, final int n) throws PropertyValueException {
            this(s, null, s2, n, true);
        }
        
        protected BooleanProperty(final String s, final String s2, final String s3, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new BooleanProperty(super.m_name, super.m_value, super.m_defaultValue, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
            if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                return;
            }
            throw new PropertyValueException(this.getName(), s);
        }
    }
    
    public static class GroupProperty extends Property
    {
        PropertyManager m_propertyManager;
        boolean m_subsume;
        String m_prefix;
        boolean m_validate;
        
        public GroupProperty(final String s, final PropertyManager propertyManager) throws PropertyValueException {
            this(s, null, null, propertyManager, "", false, 0, true, true);
        }
        
        public GroupProperty(final String s, final PropertyManager propertyManager, final boolean b) throws PropertyValueException {
            this(s, null, null, propertyManager, "", b, 0, true, true);
        }
        
        public GroupProperty(final String s, final String s2, final PropertyManager propertyManager, final boolean b) throws PropertyValueException {
            this(s, null, s2, propertyManager, "", b, 0, true, true);
        }
        
        public GroupProperty(final String s, final String s2, final PropertyManager propertyManager, final boolean b, final int n) throws PropertyValueException {
            this(s, null, s2, propertyManager, "", b, n, true, true);
        }
        
        public GroupProperty(final String s, final String s2, final String s3, final PropertyManager propertyManager, final boolean b, final int n, final boolean b2) throws PropertyValueException {
            this(s, s2, s3, propertyManager, "", b, n, b2, true);
        }
        
        public GroupProperty(final String s, final String s2, final String s3, final PropertyManager propertyManager, final String s4, final boolean b, final int n, final boolean b2) throws PropertyValueException {
            this(s, s2, s3, propertyManager, s4, b, n, b2, true);
        }
        
        public GroupProperty(final String s, final String s2, final String s3, final PropertyManager propertyManager, final String s4, final boolean subsume, final int n, final boolean b, final boolean validate) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_prefix = ((s4 != null) ? s4 : "");
            this.m_subsume = subsume;
            this.m_propertyManager = propertyManager;
            this.m_validate = validate;
            this.validateValue(s2);
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new GroupProperty(super.m_name, null, super.m_defaultValue, this.m_propertyManager, this.m_prefix, this.m_subsume, super.m_tag, super.m_registered, this.m_validate);
                property.setValue(super.m_value, false);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
            if (this.m_validate && s != null && s.length() > 0 && this.m_propertyManager.findGroup(this.m_prefix + s) == null) {
                throw new PropertyValueException(this.getName(), this.m_prefix + s);
            }
        }
        
        public final boolean getSubsume() {
            return this.m_subsume;
        }
        
        public final PropertyManager getPropertyManager() {
            return this.m_propertyManager;
        }
        
        public final String getPrefix() {
            return this.m_prefix;
        }
    }
    
    public static class DoubleProperty extends Property
    {
        protected double m_min;
        protected double m_max;
        
        public DoubleProperty(final String s) throws PropertyValueException {
            this(s, null, null, Double.MIN_VALUE, Double.MAX_VALUE, 0, true);
        }
        
        public DoubleProperty(final String s, final String s2) throws PropertyValueException {
            this(s, null, s2, Double.MIN_VALUE, Double.MAX_VALUE, 0, true);
        }
        
        public DoubleProperty(final String s, final String s2, final int n) throws PropertyValueException {
            this(s, null, s2, Double.MIN_VALUE, Double.MAX_VALUE, n, true);
        }
        
        public DoubleProperty(final String s, final String s2, final double n, final double n2) throws PropertyValueException {
            this(s, null, s2, n, n2, 0, true);
        }
        
        public DoubleProperty(final String s, final String s2, final double n, final double n2, final int n3) throws PropertyValueException {
            this(s, null, s2, n, n2, n3, true);
        }
        
        protected DoubleProperty(final String s, final String s2, final String s3, final double min, final double max, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_min = min;
            this.m_max = max;
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new DoubleProperty(super.m_name, super.m_value, super.m_defaultValue, this.m_min, this.m_max, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        public final double getMax() {
            return this.m_max;
        }
        
        public final double getMin() {
            return this.m_min;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
            boolean b = false;
            if (s != null) {
                double doubleValue;
                try {
                    doubleValue = Double.valueOf(s);
                }
                catch (NumberFormatException ex) {
                    doubleValue = 0.0;
                    b = true;
                }
                if (b || doubleValue < this.m_min || doubleValue > this.m_max) {
                    throw new PropertyValueException(this.getName(), s);
                }
            }
        }
    }
    
    public static class FloatProperty extends Property
    {
        protected float m_min;
        protected float m_max;
        
        public FloatProperty(final String s) throws PropertyValueException {
            this(s, null, null, Float.MIN_VALUE, Float.MAX_VALUE, 0, true);
        }
        
        public FloatProperty(final String s, final String s2) throws PropertyValueException {
            this(s, null, s2, Float.MIN_VALUE, Float.MAX_VALUE, 0, true);
        }
        
        public FloatProperty(final String s, final String s2, final int n) throws PropertyValueException {
            this(s, null, s2, Float.MIN_VALUE, Float.MAX_VALUE, n, true);
        }
        
        public FloatProperty(final String s, final String s2, final float n, final float n2) throws PropertyValueException {
            this(s, null, s2, n, n2, 0, true);
        }
        
        public FloatProperty(final String s, final String s2, final float n, final float n2, final int n3) throws PropertyValueException {
            this(s, null, s2, n, n2, n3, true);
        }
        
        protected FloatProperty(final String s, final String s2, final String s3, final float min, final float max, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_min = min;
            this.m_max = max;
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new FloatProperty(super.m_name, super.m_value, super.m_defaultValue, this.m_min, this.m_max, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        public final float getMax() {
            return this.m_max;
        }
        
        public final float getMin() {
            return this.m_min;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
            boolean b = false;
            if (s != null) {
                float floatValue;
                try {
                    floatValue = Float.valueOf(s);
                }
                catch (NumberFormatException ex) {
                    floatValue = 0.0f;
                    b = true;
                }
                if (b || floatValue < this.m_min || floatValue > this.m_max) {
                    throw new PropertyValueException(this.getName(), s);
                }
            }
        }
    }
    
    public static class IntProperty extends Property
    {
        protected int m_min;
        protected int m_max;
        
        public IntProperty(final String s) throws PropertyValueException {
            this(s, null, null, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, true);
        }
        
        public IntProperty(final String s, final String s2) throws PropertyValueException {
            this(s, null, s2, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, true);
        }
        
        public IntProperty(final String s, final String s2, final int n) throws PropertyValueException {
            this(s, null, s2, Integer.MIN_VALUE, Integer.MAX_VALUE, n, true);
        }
        
        public IntProperty(final String s, final String s2, final int n, final int n2) throws PropertyValueException {
            this(s, null, s2, n, n2, 0, true);
        }
        
        public IntProperty(final String s, final String s2, final int n, final int n2, final int n3) throws PropertyValueException {
            this(s, null, s2, n, n2, n3, true);
        }
        
        protected IntProperty(final String s, final String s2, final String s3, final int min, final int max, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_min = min;
            this.m_max = max;
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new IntProperty(super.m_name, super.m_value, super.m_defaultValue, this.m_min, this.m_max, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        public final int getMax() {
            return this.m_max;
        }
        
        public final int getMin() {
            return this.m_min;
        }
        
        protected void validateValue(String substring) throws PropertyValueException {
            boolean b = false;
            int radix = 10;
            if (substring != null) {
                int int1;
                try {
                    if (substring.toLowerCase().startsWith("0x")) {
                        radix = 16;
                        substring = substring.substring(2);
                    }
                    int1 = Integer.parseInt(substring, radix);
                }
                catch (NumberFormatException ex) {
                    int1 = 0;
                    b = true;
                }
                if (b || int1 < this.m_min || int1 > this.m_max) {
                    throw new PropertyValueException(this.getName(), substring);
                }
            }
        }
    }
    
    public static class EnumProperty extends Property
    {
        protected String[] m_enum;
        
        public EnumProperty(final String s, final String[] array) throws PropertyValueException {
            this(s, null, null, array, 0, true);
        }
        
        public EnumProperty(final String s, final String s2, final String[] array) throws PropertyValueException {
            this(s, null, s2, array, 0, true);
        }
        
        public EnumProperty(final String s, final String s2, final String[] array, final int n) throws PropertyValueException {
            this(s, null, s2, array, n, true);
        }
        
        protected EnumProperty(final String s, final String s2, final String s3, final String[] enum1, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_enum = enum1;
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new EnumProperty(super.m_name, super.m_value, super.m_defaultValue, this.m_enum, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        public final String[] getEnum() {
            return this.m_enum;
        }
        
        protected void validateValue(final String s) throws PropertyValueException {
            if (s != null && this.m_enum != null) {
                for (int i = 0; i < this.m_enum.length; ++i) {
                    if (s.equalsIgnoreCase(this.m_enum[i])) {
                        return;
                    }
                }
                throw new PropertyValueException(this.getName(), s);
            }
        }
    }
    
    public static class LongProperty extends Property
    {
        protected long m_min;
        protected long m_max;
        
        public LongProperty(final String s) throws PropertyValueException {
            this(s, null, null, Long.MIN_VALUE, Long.MAX_VALUE, 0, true);
        }
        
        public LongProperty(final String s, final String s2) throws PropertyValueException {
            this(s, null, s2, Long.MIN_VALUE, Long.MAX_VALUE, 0, true);
        }
        
        public LongProperty(final String s, final String s2, final int n) throws PropertyValueException {
            this(s, null, s2, Long.MIN_VALUE, Long.MAX_VALUE, n, true);
        }
        
        public LongProperty(final String s, final String s2, final long n, final long n2) throws PropertyValueException {
            this(s, null, s2, n, n2, 0, true);
        }
        
        public LongProperty(final String s, final String s2, final long n, final long n2, final int n3) throws PropertyValueException {
            this(s, null, s2, n, n2, n3, true);
        }
        
        protected LongProperty(final String s, final String s2, final String s3, final long min, final long max, final int n, final boolean b) throws PropertyValueException {
            super(s, s2, s3, n, b);
            this.m_min = min;
            this.m_max = max;
            if (s2 != null) {
                this.validateValue(s2);
            }
            if (s3 != null) {
                this.validateValue(s3);
            }
        }
        
        public Object clone() {
            Property property = null;
            try {
                property = new LongProperty(super.m_name, super.m_value, super.m_defaultValue, this.m_min, this.m_max, super.m_tag, super.m_registered);
                super.copyValues(property);
            }
            catch (PropertyValueException ex) {}
            return property;
        }
        
        public final long getMax() {
            return this.m_max;
        }
        
        public final long getMin() {
            return this.m_min;
        }
        
        protected void validateValue(String substring) throws PropertyValueException {
            boolean b = false;
            int radix = 10;
            if (substring != null) {
                long long1;
                try {
                    if (substring.toLowerCase().startsWith("0x")) {
                        radix = 16;
                        substring = substring.substring(2);
                    }
                    long1 = Long.parseLong(substring, radix);
                }
                catch (NumberFormatException ex) {
                    long1 = 0L;
                    b = true;
                }
                if (b || long1 < this.m_min || long1 > this.m_max) {
                    throw new PropertyValueException(this.getName(), substring);
                }
            }
        }
    }
    
    public static class PropertyCollection implements Enumeration
    {
        private Hashtable m_properties;
        private int m_size;
        private Enumeration m_enum;
        
        public PropertyCollection(final PropertyManager propertyManager, final String s) {
            boolean b = false;
            if (s != null && s.startsWith("Environment")) {
                b = true;
                propertyManager.setOrderedPopertyHashTable(true);
            }
            this.m_properties = propertyManager.getPropertyHashtable();
            if (b) {
                propertyManager.setOrderedPopertyHashTable(false);
            }
            this.m_size = 0;
            this.m_enum = null;
        }
        
        public final Property get(final String s) {
            return this.m_properties.get(s.toLowerCase());
        }
        
        public final boolean hasMoreElements() {
            if (this.m_enum == null) {
                this.m_enum = this.m_properties.elements();
            }
            return this.m_enum.hasMoreElements();
        }
        
        public final Object nextElement() {
            if (this.m_enum == null) {
                this.m_enum = this.m_properties.elements();
            }
            return this.m_enum.nextElement();
        }
        
        protected final void put(final Property property) {
            this.put(property, false);
        }
        
        protected final void put(final Property value, final boolean b) {
            final String lowerCase = value.getName().toLowerCase();
            final Property property = this.m_properties.get(lowerCase);
            if (!b && property != null && property.getValue() != null) {
                return;
            }
            this.m_properties.put(lowerCase, value);
            ++this.m_size;
        }
        
        public final void reset() {
            this.m_enum = null;
        }
        
        public final int size() {
            return this.m_size;
        }
        
        public Hashtable getProperties() {
            return this.m_properties;
        }
    }
    
    public class PropertyGroup implements Serializable
    {
        public Hashtable m_properties;
        protected CommentGroup m_comment;
        protected PropertyGroup m_parent;
        public int m_attributes;
        protected String m_name;
        
        public PropertyGroup(final PropertyManager propertyManager, final PropertyManager propertyManager2, final String s, final PropertyGroup propertyGroup) {
            this(propertyManager, propertyManager2, s, propertyGroup, null);
        }
        
        public PropertyGroup(final PropertyManager propertyManager, final String name, final PropertyGroup parent, final CommentGroup comment) {
            this.m_comment = new CommentGroup();
            boolean b = false;
            this.m_parent = parent;
            this.m_attributes = 0;
            this.m_name = name;
            if (name != null && name.startsWith("Environment")) {
                b = true;
                propertyManager.setOrderedPopertyHashTable(true);
            }
            this.m_properties = propertyManager.getPropertyHashtable();
            if (b) {
                propertyManager.setOrderedPopertyHashTable(false);
            }
            this.setComment(comment);
            if (parent != null) {
                if ((parent.m_attributes & 0x8) != 0x0) {
                    this.m_attributes |= 0x8;
                }
                if ((parent.m_attributes & 0x4) != 0x0) {
                    this.m_attributes |= 0x2;
                }
            }
        }
        
        public Property findRegisteredProperty(final String key) {
            PropertyGroup parent = this;
            Property property = null;
            while ((parent.m_attributes & 0x8) != 0x0) {
                property = parent.m_properties.get(key);
                if (property != null) {
                    break;
                }
                parent = parent.m_parent;
                if (parent == null) {
                    break;
                }
            }
            return property;
        }
        
        public final String getName() {
            return this.m_name;
        }
        
        public final CommentGroup getComment() {
            return this.m_comment;
        }
        
        public final String getUpperComment() {
            return this.m_comment.upperComment;
        }
        
        public final String getLowerComment() {
            return this.m_comment.lowerComment;
        }
        
        public final void setComment(final CommentGroup comment) {
            if (comment != null) {
                this.m_comment = comment;
            }
        }
        
        public final void setUpperComment(final String upperComment) {
            this.m_comment.upperComment = upperComment;
        }
        
        public final void setLowerComment(final String lowerComment) {
            this.m_comment.lowerComment = lowerComment;
        }
        
        public String getProperty(final String s) {
            return this.getProperty(s, null);
        }
        
        public String getProperty(final String s, final String[] array) {
            final String lowerCase = s.toLowerCase();
            PropertyGroup propertyGroup = this;
            String s2 = null;
            Property property;
            do {
                property = propertyGroup.m_properties.get(lowerCase);
                if (property != null) {
                    break;
                }
                propertyGroup = propertyGroup.m_parent;
            } while (propertyGroup != null);
            final PropertyGroup propertyGroup2 = propertyGroup;
            final Property property2 = property;
            while (property != null) {
                s2 = property.getValue();
                if (s2 != null) {
                    break;
                }
                propertyGroup = propertyGroup.m_parent;
                if (propertyGroup == null) {
                    break;
                }
                property = propertyGroup.m_properties.get(lowerCase);
            }
            if (s2 == null && property2 != null) {
                s2 = property2.getDefaultValue();
                propertyGroup = propertyGroup2;
            }
            if (array != null) {
                if (propertyGroup == null) {
                    array[0] = null;
                }
                else {
                    array[0] = propertyGroup.getName();
                }
            }
            return s2;
        }
        
        public synchronized boolean putProperty(final String s, final String s2) throws PropertyNameException, PropertyValueException {
            return this.putProperty(s, s2, null);
        }
        
        public synchronized boolean putProperty(final String s, final String s2, final CommentGroup commentGroup) throws PropertyNameException, PropertyValueException {
            return this.putProperty(s, s2, commentGroup, true);
        }
        
        protected synchronized boolean putProperty(final String s, final String s2, final CommentGroup commentGroup, final boolean b) throws PropertyNameException, PropertyValueException {
            final String lowerCase = s.toLowerCase();
            final Property property = this.m_properties.get(lowerCase);
            final boolean b2 = property != null;
            if (b2) {
                property.setValue(s2, b);
                property.setComment(commentGroup);
            }
            else {
                Property value;
                if ((this.m_attributes & 0x8) != 0x0) {
                    final Property registeredProperty = this.findRegisteredProperty(lowerCase);
                    if (registeredProperty == null) {
                        throw new PropertyNameException(s);
                    }
                    value = (Property)registeredProperty.clone();
                    value.setRegistered(false);
                    value.setValue(s2, b);
                    value.setComment(commentGroup);
                }
                else {
                    value = new Property(s, s2, false, commentGroup);
                }
                this.m_properties.put(lowerCase, value);
            }
            return b2;
        }
        
        public synchronized void registerProperties(final Property[] array) {
            for (int i = 0; i < array.length; ++i) {
                final Property value = array[i];
                this.m_properties.put(value.getName().toLowerCase(), value);
            }
        }
        
        public synchronized boolean removeProperty(final String s) {
            final String lowerCase = s.toLowerCase();
            final Property property = this.m_properties.get(lowerCase);
            if (property != null) {
                if (property.getRegistered()) {
                    try {
                        property.setValue(null);
                    }
                    catch (PropertyValueException ex) {}
                }
                else {
                    this.m_properties.remove(lowerCase);
                }
            }
            return property != null;
        }
    }
    
    class PropertyMonitor extends Thread
    {
        PropertyManager pm;
        int monitorInterval;
        boolean stopRequested;
        
        PropertyMonitor(final PropertyManager pm, final int monitorInterval) throws NoEventBrokerSpecified {
            this.pm = null;
            this.monitorInterval = 5000;
            this.stopRequested = false;
            if (pm.m_monitor != null) {
                pm.m_monitor.destroyThread();
                synchronized (pm) {
                    pm.m_monitor = this;
                }
            }
            if (pm.m_eventBroker == null) {
                throw new NoEventBrokerSpecified(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Cannot monitor file changes for file", (Object)pm.m_path));
            }
            this.pm = pm;
            this.monitorInterval = monitorInterval;
            pm.m_lastModified = pm.m_file.lastModified();
            this.setName("Property File Monitor for : " + pm.m_path);
        }
        
        void destroyThread() {
            this.stopRequested = true;
        }
        
        public void run() {
            try {
                while (!this.stopRequested) {
                    synchronized (this.pm) {
                        if (this.pm.m_file.lastModified() > this.pm.m_lastModified) {
                            try {
                                this.pm.m_eventBroker.postEvent(new ERenegadePropertyFileChange(this.pm, this.pm.m_path));
                                System.out.println("ERenegadePropertyFileChange posted");
                                this.pm.m_lastModified = this.pm.m_file.lastModified();
                            }
                            catch (RemoteException previous) {
                                Excp.print(previous, "Can't post ERenegadePropertyFileChange event");
                                new PropertyException("Can't post ERenegadePropertyFileChange event.", (Object[])null).setPrevious(previous);
                                PropertyManager.this.stopMonitors();
                            }
                            break;
                        }
                    }
                    Thread.sleep(this.monitorInterval);
                    continue;
                    break;
                }
            }
            catch (InterruptedException ex) {}
            synchronized (this.pm) {
                if (this.pm.m_monitor == this) {
                    this.pm.m_monitor = null;
                }
            }
        }
    }
    
    public class PropertyType
    {
        String propertyGroup;
        String propertyName;
        String[] propertyValues;
        boolean isPropertyGroup;
        boolean isPropertyName;
        boolean isPropertyWildcard;
        boolean isPropertyValid;
        int propertyType;
        
        public PropertyType(final String propertyGroup, final String propertyName, final String[] propertyValues, final int propertyType) {
            this.propertyGroup = null;
            this.propertyName = "";
            this.propertyValues = null;
            this.isPropertyGroup = false;
            this.isPropertyName = false;
            this.isPropertyWildcard = false;
            this.isPropertyValid = true;
            this.propertyType = PropertyManager.PROPERTY_INVALID;
            this.propertyGroup = propertyGroup;
            this.propertyName = propertyName;
            this.propertyValues = propertyValues;
            this.propertyType = propertyType;
            if (this.propertyType == PropertyManager.PROPERTY_GROUP) {
                this.isPropertyGroup = true;
            }
            else if (this.propertyType == PropertyManager.PROPERTY_NAME) {
                this.isPropertyName = true;
            }
            else if (this.propertyType == PropertyManager.PROPERTY_WILDCARD) {
                this.isPropertyWildcard = true;
            }
            else {
                this.isPropertyValid = false;
            }
        }
        
        public String[] getPropertyValues() {
            return this.propertyValues;
        }
        
        public String getPropertyName() {
            return this.propertyName;
        }
        
        public String getPropertyGroup() {
            if (this.propertyGroup != null) {
                return this.propertyGroup;
            }
            if (this.isPropertyGroup) {
                this.propertyGroup = this.propertyName;
            }
            else if (this.propertyName != null) {
                this.propertyGroup = this.propertyName.substring(0, this.propertyName.lastIndexOf(46));
            }
            return this.propertyGroup;
        }
        
        public int getPropertyType() {
            return this.propertyType;
        }
        
        public boolean isPropertyValid() {
            return this.isPropertyValid;
        }
        
        public boolean isGroup() {
            return this.isPropertyGroup;
        }
        
        public boolean isProperty() {
            return this.isPropertyName;
        }
        
        public boolean isWildcard() {
            return this.isPropertyWildcard;
        }
        
        public String toString() {
            String s;
            if (this.isPropertyGroup) {
                s = "group";
            }
            else if (this.isPropertyName) {
                s = "property";
            }
            else if (this.isPropertyWildcard) {
                s = "wildcard";
            }
            else {
                s = "invalid";
            }
            return s;
        }
    }
    
    public interface IPropertyValueFilter
    {
        String filterValue(final String p0, final String p1, final String p2);
    }
}
