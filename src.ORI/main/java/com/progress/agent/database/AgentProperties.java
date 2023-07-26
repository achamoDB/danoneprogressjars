// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.common.log.ProLog;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import com.progress.juniper.admin.JuniperProperties;
import com.progress.juniper.admin.JAPlugIn;
import com.progress.agent.smdatabase.SMPlugIn;
import com.progress.agent.smdatabase.SMProperties;
import java.util.Vector;
import com.progress.juniper.admin.DatabasePluginComponent;
import com.progress.common.property.PropertyList;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.common.networkevents.EventBroker;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Hashtable;
import com.progress.common.dsm.DSMAPI;
import com.progress.common.property.PropertyManager;

public class AgentProperties extends PropertyManager implements IAgentConstants
{
    private static final String VERSION = "%% version 1.1";
    public static String schemaFileName;
    public static String propertyFileName;
    public static AgentProperties m_self;
    private DSMAPI dsmapi;
    String m_plugInId;
    String DATABASE;
    String SMDATABASE;
    public final String METRIC = "metric.";
    public final String VST = "metric.vst.";
    private Hashtable m_vstTables;
    private Hashtable m_vstTables_v9;
    private Hashtable m_vstTables_v10;
    private Hashtable m_vstTables_v10_64;
    private Hashtable m_vstTables_v101c;
    
    public String getPlugInId() {
        return this.m_plugInId;
    }
    
    private void setPlugInId(final String plugInId) {
        this.m_plugInId = plugInId;
    }
    
    protected boolean chkPropertyVersion(final String s) {
        return s.trim().toLowerCase().equals("%% version 1.1".toLowerCase().trim());
    }
    
    protected void writePropertyVersion(final BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("%% version 1.1" + PropertyManager.NEWLINE);
    }
    
    protected Hashtable getGroupHashtable() {
        return new OrderedHashtable();
    }
    
    protected Hashtable getPropertyHashtable() {
        return new SortedHashtable();
    }
    
    protected String getPropertyNameForWriting(final Property property) {
        final String propertyNameForWriting = super.getPropertyNameForWriting(property);
        final String groupNameForWriting = super.getGroupNameForWriting();
        if (propertyNameForWriting == null || groupNameForWriting == null) {
            return propertyNameForWriting;
        }
        return propertyNameForWriting.toLowerCase();
    }
    
    protected String getPropertyValueForWriting(final Property property) {
        final String propertyNameForWriting = super.getPropertyNameForWriting(property);
        final String propertyValueForWriting = super.getPropertyValueForWriting(property);
        final String groupNameForWriting = super.getGroupNameForWriting();
        if (propertyValueForWriting == null || groupNameForWriting == null || propertyNameForWriting == null) {
            return propertyValueForWriting;
        }
        return propertyValueForWriting.toLowerCase();
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
    
    public static String getSchemaFileName() {
        return AgentProperties.schemaFileName;
    }
    
    public static String getPropertyFileName() {
        return AgentProperties.propertyFileName;
    }
    
    public AgentProperties(final String s) throws PropertyException {
        this(s, null);
    }
    
    public AgentProperties(final String propertyFileName, final EventBroker eventBroker) throws PropertyValueException, LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyException {
        super(getSchemaFileName(), eventBroker);
        this.dsmapi = new DSMAPI();
        this.m_plugInId = null;
        this.DATABASE = "Database.";
        this.SMDATABASE = "SMDatabase.";
        this.m_vstTables = new Hashtable();
        this.m_vstTables_v9 = new Hashtable();
        this.m_vstTables_v10 = new Hashtable();
        this.m_vstTables_v10_64 = new Hashtable();
        this.m_vstTables_v101c = new Hashtable();
        AgentProperties.m_self = this;
        if (propertyFileName != null && propertyFileName.length() != 0) {
            this.load(AgentProperties.propertyFileName = propertyFileName);
        }
        this.loadVstData();
        this.loadVstDataExceptions();
    }
    
    public void loadVstData() throws PropertyException {
        this.loadVstData("privateagent.schema", this.getVstHashTable("102b"));
        this.loadVstData("privateagent_v101c.schema", this.getVstHashTable("101c"));
        this.loadVstData("privateagent_v101b.schema", this.getVstHashTable("101"));
        this.loadVstData("privateagent_v10.schema", this.getVstHashTable("10"));
        this.loadVstData("privateagent_v9.schema", this.getVstHashTable("9"));
    }
    
    private void loadVstData(final String s, final Hashtable hashtable) throws PropertyException {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/com/progress/schema/" + s);
        if (resourceAsStream == null) {
            throw new PropertyException("Error locating schema file: /com/progress/schema/" + s, new Object[0]);
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        TableSchema value = null;
        int n = 2;
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String lowerCase = line.trim().toLowerCase();
                final int index = lowerCase.indexOf(35);
                final String s2 = (index == -1) ? lowerCase : lowerCase.substring(0, index);
                if (s2.length() == 0) {
                    continue;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(s2, ".");
                if (stringTokenizer.countTokens() >= 7) {
                    final String nextToken = stringTokenizer.nextToken();
                    final Integer n2 = new Integer(stringTokenizer.nextToken());
                    if (value == null || !nextToken.equals(value.getTableName())) {
                        n = 1;
                        value = new TableSchema(nextToken, n2);
                        value.addColumn("recid".toLowerCase(), new Integer(1), "big", new Integer(0));
                        hashtable.put(nextToken, value);
                    }
                    ++n;
                    final String nextToken2 = stringTokenizer.nextToken();
                    final Integer n3 = new Integer(stringTokenizer.nextToken());
                    final int i = n3;
                    String nextToken3 = stringTokenizer.nextToken();
                    final String nextToken4 = stringTokenizer.nextToken();
                    final Integer n4 = new Integer(stringTokenizer.nextToken());
                    if (n4 > 0) {
                        if (nextToken4.equalsIgnoreCase("integer")) {
                            nextToken3 = "IntegerArray";
                        }
                        else if (nextToken4.equalsIgnoreCase("int64")) {
                            nextToken3 = "LongArray";
                        }
                        else {
                            nextToken3 = "StringArray";
                        }
                    }
                    while (i > n) {
                        value.addColumn("not_used", new Integer(n++), "integer", new Integer(0));
                    }
                    value.addColumn(nextToken2, n3, nextToken3.toLowerCase(), n4);
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
    
    public void loadVstDataExceptions() throws PropertyException {
        this.loadVstDataExceptions("privateagentexcp.schema", this.getVstHashTable("102b"));
        this.loadVstDataExceptions("privateagentexcp_v101c.schema", this.getVstHashTable("101c"));
        this.loadVstDataExceptions("privateagentexcp_v101b.schema", this.getVstHashTable("101"));
        this.loadVstDataExceptions("privateagentexcp_v10.schema", this.getVstHashTable("10"));
        this.loadVstDataExceptions("privateagentexcp_v9.schema", this.getVstHashTable("9"));
    }
    
    private void loadVstDataExceptions(final String s, final Hashtable hashtable) throws PropertyException {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/com/progress/schema/" + s);
        if (resourceAsStream == null) {
            throw new PropertyException("Error locating schema file: /com/progress/schema/" + s, new Object[0]);
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String lowerCase = line.trim().toLowerCase();
                final int index = lowerCase.indexOf(35);
                final String s2 = (index == -1) ? lowerCase : lowerCase.substring(0, index);
                if (s2.length() == 0) {
                    continue;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(s2, ".");
                if (stringTokenizer.countTokens() >= 7) {
                    final String nextToken = stringTokenizer.nextToken();
                    final Integer n = new Integer(stringTokenizer.nextToken());
                    final TableSchema value = hashtable.get(nextToken);
                    final String nextToken2 = stringTokenizer.nextToken();
                    final Integer n2 = new Integer(stringTokenizer.nextToken());
                    final String nextToken3 = stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    value.replaceColumn(nextToken2, n2, nextToken3.toLowerCase(), new Integer(stringTokenizer.nextToken()));
                    hashtable.put(nextToken, value);
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
    
    private Object getRecField(final byte[] array, final int n, final int n2, final int n3) throws DSMAPI.DSMException {
        Object o = null;
        switch (n2) {
            case 1:
            case 2: {
                final DSMAPI dsmapi = this.dsmapi;
                o = DSMAPI.recGetString(array, n);
                break;
            }
            case 3: {
                final DSMAPI dsmapi2 = this.dsmapi;
                o = DSMAPI.recGetIntegerArray(array, n, n3);
                break;
            }
            case 36: {
                final DSMAPI dsmapi3 = this.dsmapi;
                o = DSMAPI.recGetLongArray(array, n, n3);
                break;
            }
            case 4: {
                final DSMAPI dsmapi4 = this.dsmapi;
                o = DSMAPI.recGetStringArray(array, n, n3);
                break;
            }
            case 7:
            case 8: {
                final DSMAPI dsmapi5 = this.dsmapi;
                o = DSMAPI.recGetDecimal(array, n);
                break;
            }
            case 9: {
                final DSMAPI dsmapi6 = this.dsmapi;
                o = DSMAPI.recGetBool(array, n);
                break;
            }
            case 16: {
                final DSMAPI dsmapi7 = this.dsmapi;
                o = DSMAPI.recGetTiny(array, n);
                break;
            }
            case 18: {
                final DSMAPI dsmapi8 = this.dsmapi;
                o = DSMAPI.recGetUnsignedSmall(array, n);
                break;
            }
            case 17: {
                final DSMAPI dsmapi9 = this.dsmapi;
                o = DSMAPI.recGetUnsignedSmall(array, n);
                break;
            }
            case 6: {
                final DSMAPI dsmapi10 = this.dsmapi;
                o = DSMAPI.recGetInteger(array, n);
                break;
            }
            case 5:
            case 19: {
                final DSMAPI dsmapi11 = this.dsmapi;
                o = DSMAPI.recGetUnsignedInteger(array, n);
                break;
            }
            case 20: {
                final DSMAPI dsmapi12 = this.dsmapi;
                o = DSMAPI.recGetLong64(array, n);
                break;
            }
            case 21:
            case 22: {
                final DSMAPI dsmapi13 = this.dsmapi;
                o = DSMAPI.recGetFloat(array, n);
                break;
            }
            case 23: {
                final DSMAPI dsmapi14 = this.dsmapi;
                o = DSMAPI.recGetDouble(array, n);
                break;
            }
            case 24: {
                final DSMAPI dsmapi15 = this.dsmapi;
                o = DSMAPI.recGetBytes(array, n);
                break;
            }
            case 25: {
                o = null;
                break;
            }
            case 32: {
                final DSMAPI dsmapi16 = this.dsmapi;
                o = DSMAPI.recGetDate(array, n);
                break;
            }
            case 33: {
                final DSMAPI dsmapi17 = this.dsmapi;
                o = DSMAPI.recGetTime(array, n);
                break;
            }
            case 34: {
                final DSMAPI dsmapi18 = this.dsmapi;
                o = DSMAPI.recGetTimestamp(array, n);
                break;
            }
            case 35: {
                o = null;
                break;
            }
        }
        return o;
    }
    
    public PropertyList getVstTable(final String s, final String s2) throws PropertyNameException, DatabasePluginComponent.SchemaLockedException, DatabasePluginComponent.SchemaDeniedException, DatabasePluginComponent.SchemaParsingException {
        if (s == null || s.length() == 0 || s2 == null || s2.length() == 0) {
            return null;
        }
        final PropertyList list = new PropertyList();
        this.processVstProperty(list, "metric.vst.", s, s2, null, false);
        return list;
    }
    
    private void processVstProperty(final PropertyList list, final String s, final String s2) throws PropertyNameException, DatabasePluginComponent.SchemaLockedException, DatabasePluginComponent.SchemaDeniedException, DatabasePluginComponent.SchemaParsingException {
        final int index = s2.indexOf(46);
        final String s3 = (index == -1) ? null : s2.substring(0, index);
        final String s4 = (index == -1 || index == s2.length()) ? null : s2.substring(index + 1);
        if (s3 == null || s4 == null) {
            throw new PropertyNameException(s2);
        }
        final int index2 = s4.indexOf(46);
        this.processVstProperty(list, s, s3, (index2 == -1) ? s4 : s4.substring(0, index2), (index2 == -1 || index2 == s4.length()) ? null : s4.substring(index2 + 1), true);
    }
    
    private void processVstProperty(final PropertyList list, final String str, String str2, final String str3, final String str4, final boolean b) throws PropertyNameException, DatabasePluginComponent.SchemaLockedException, DatabasePluginComponent.SchemaDeniedException, DatabasePluginComponent.SchemaParsingException {
        Hashtable<K, TableSchema> vstHashTable = null;
        Vector vector = new Vector<String>();
        if (str2.indexOf("_SMDatabase_") == 0) {
            str2 = str2.substring("_SMDatabase_".length());
            if (b) {
                vector = ((SMProperties)SMPlugIn.get().getPlugInPropertyManager()).expandPropertyNames(this.SMDATABASE + str2 + ".displayName");
                for (int i = 0; i < vector.size(); ++i) {
                    str2 = vector.elementAt(i);
                    str2 = str2.substring(this.SMDATABASE.length());
                    str2 = str2.substring(0, str2.indexOf(46));
                    vector.setElementAt((String)(Object)new String[] { str + "." + str2 + "." + str3, "_SMDatabase_" + str2 }, i);
                }
            }
            else {
                vector.addElement((String)(Object)new String[] { str + "." + str2 + "." + str3, "_SMDatabase_" + str2 });
            }
        }
        else if (b) {
            JAPlugIn.get();
            vector = ((JuniperProperties)JAPlugIn.propertiesS()).expandPropertyNames(this.DATABASE + str2 + ".displayName");
            for (int j = 0; j < vector.size(); ++j) {
                str2 = vector.elementAt(j);
                str2 = str2.substring(this.DATABASE.length());
                str2 = str2.substring(0, str2.indexOf(46));
                vector.setElementAt((String)(Object)new String[] { str + "." + str2 + "." + str3, str2 }, j);
            }
        }
        else {
            vector.addElement((String)(Object)new String[] { str + "." + str2 + "." + str3, str2 });
        }
        if (vector.isEmpty()) {
            String s = str + str2 + str3;
            if (str4 != null) {
                s = s + s + str4;
            }
            throw new PropertyNameException(s);
        }
        for (int k = 0; k < vector.size(); ++k) {
            final Vector<ResultSetSchema> vector2 = new Vector<ResultSetSchema>(128, 64);
            final String[] array = (Object)vector.elementAt(k);
            final String s2 = array[0];
            final String s3 = array[1];
            AgentDatabase.get();
            final AgentDatabase agentDatabase = AgentDatabase.findAgentDatabase(s3);
            int int1 = 1;
            boolean b2 = true;
            TableSchema tableSchema = null;
            Integer tableId = null;
            TableSchema.ColumnSchema columnSchema = null;
            String s4 = null;
            String s5 = null;
            if (agentDatabase != null) {
                synchronized (agentDatabase) {
                    if (vstHashTable == null) {
                        vstHashTable = (Hashtable<K, TableSchema>)this.getVstHashTable(agentDatabase.getSchemaVersion());
                        tableSchema = vstHashTable.get(str3.toLowerCase());
                        if (tableSchema == null) {
                            String s6 = str + str2 + str3;
                            if (str4 != null) {
                                s6 = s6 + s6 + str4;
                            }
                            throw new PropertyNameException(s6);
                        }
                        tableId = tableSchema.getTableId();
                        columnSchema = null;
                        if (str4 == null) {
                            s4 = "QRY_VST_TBL";
                            s5 = tableId.toString();
                        }
                        else {
                            columnSchema = tableSchema.getColumnSchema(str4);
                            if (columnSchema == null) {
                                String s7 = str + str2 + str3;
                                if (str4 != null) {
                                    s7 = s7 + s7 + str4;
                                }
                                throw new PropertyNameException(s7);
                            }
                            final int columnId = columnSchema.getColumnId();
                            final String columnType = columnSchema.getColumnType();
                            s4 = "QRY_VST_COL";
                            s5 = tableId.toString() + "." + columnType.toUpperCase().charAt(0) + "." + columnId;
                        }
                    }
                    while (agentDatabase != null && agentDatabase.isRunning() && int1 == 1) {
                        final byte[] doQuery = agentDatabase.doQuery(s4, s5);
                        if (doQuery != null) {
                            long long1 = 0L;
                            try {
                                final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(doQuery));
                                final int int2 = dataInputStream.readInt();
                                int1 = dataInputStream.readInt();
                                for (int l = 0; l < int2; ++l) {
                                    if (agentDatabase.is64BitRecID()) {
                                        long1 = dataInputStream.readLong();
                                    }
                                    else {
                                        long1 = dataInputStream.readInt();
                                    }
                                    final int int3 = dataInputStream.readInt();
                                    if (int3 <= 32851) {
                                        final byte[] b3 = new byte[int3];
                                        dataInputStream.readFully(b3);
                                        vector2.addElement(new ResultSetSchema(b3, long1));
                                    }
                                    else {
                                        b2 = false;
                                    }
                                }
                            }
                            catch (IOException ex) {
                                ex.printStackTrace();
                                if (!agentDatabase.isRunning()) {
                                    PropertyManager.m_log.log(3, "Agent not running.  Error while trying to retrieve data for " + s2 + "; database: " + str2 + "; vstTable: " + str3);
                                    continue;
                                }
                                int1 = 0;
                                b2 = false;
                            }
                            catch (Exception ex2) {
                                ex2.printStackTrace();
                                PropertyManager.m_log.log(3, "Agent not running.  Error while trying to retrieve data for " + s2 + "; database: " + str2 + "; vstTable: " + str3);
                                int1 = 0;
                                b2 = false;
                            }
                            if (int1 == 1) {
                                s5 = tableId.toString() + "." + long1;
                            }
                        }
                        if (agentDatabase.getQueryResultsId().equals("QRY_REJ_SCH_LOCK")) {
                            throw new DatabasePluginComponent.SchemaLockedException(str2);
                        }
                        if (int1 != 1 && b2) {
                            if (agentDatabase.getQueryResultsId().equals("QRY_VST_TBL_ACK")) {
                                this.processTableQueryResults(list, s2, tableSchema, vector2);
                            }
                            else {
                                if (!agentDatabase.getQueryResultsId().equals("QRY_VST_COL_ACK")) {
                                    continue;
                                }
                                this.processColumnQueryResults(list, s2, tableSchema, vector2, columnSchema);
                            }
                        }
                        else {
                            if (agentDatabase.getQueryResultsId().equals("QRY_VST_TBL_REJ") || agentDatabase.getQueryResultsId().equals("QRY_VST_COL_REJ")) {
                                throw new DatabasePluginComponent.SchemaDeniedException(str2, str3);
                            }
                            if (!b2) {
                                throw new DatabasePluginComponent.SchemaParsingException(str2, str3);
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }
    
    private void processTableQueryResults(final PropertyList list, final String s, final TableSchema tableSchema, final Vector vector) {
        if (tableSchema == null) {
            return;
        }
        tableSchema.getColumnSchema();
        final int columnSchemaSize = tableSchema.getColumnSchemaSize();
        final Vector[] array = new Vector[columnSchemaSize];
        for (int i = 0; i < columnSchemaSize; ++i) {
            array[i] = new Vector();
        }
        for (int j = 0; j < vector.size(); ++j) {
            final Enumeration columnSchema = tableSchema.getColumnSchema();
            final ResultSetSchema resultSetSchema = vector.elementAt(j);
            int n = 0;
            while (columnSchema.hasMoreElements()) {
                final TableSchema.ColumnSchema columnSchema2 = columnSchema.nextElement();
                final int columnId = columnSchema2.getColumnId();
                final int columnTypeId = columnSchema2.getColumnTypeId();
                final int columnExtentSize = columnSchema2.getColumnExtentSize();
                if (columnId > 1) {
                    try {
                        final Object recField = this.getRecField(resultSetSchema.resultSet, columnId, columnTypeId, columnExtentSize);
                        array[n].addElement((recField == null) ? "" : recField.toString());
                        final String s2 = (recField == null) ? "" : recField.toString();
                    }
                    catch (DSMAPI.DSMException ex) {
                        ex.printStackTrace();
                        array[n].addElement("");
                        PropertyManager.m_log.log(3, ex.getMessage() + ": " + s + "." + columnSchema2.getColumnName());
                    }
                    catch (Exception ex2) {
                        ex2.printStackTrace();
                        array[n].addElement("");
                        PropertyManager.m_log.log(3, ex2.getMessage() + ": " + s + "." + columnSchema2.getColumnName());
                    }
                }
                else if (columnId == 1) {
                    array[n].addElement(resultSetSchema.recID + "");
                }
                ++n;
            }
        }
        final Enumeration columnSchema3 = tableSchema.getColumnSchema();
        int n2 = 0;
        while (columnSchema3.hasMoreElements()) {
            list.addProperty(this.getPlugInId(), s, columnSchema3.nextElement().getColumnName(), array[n2]);
            ++n2;
        }
    }
    
    private void processColumnQueryResults(final PropertyList list, final String s, final TableSchema tableSchema, final Vector vector, final TableSchema.ColumnSchema columnSchema) {
        if (tableSchema == null) {
            return;
        }
        final Vector<String> vector2 = new Vector<String>();
        final String columnName = columnSchema.getColumnName();
        columnSchema.getColumnType();
        for (int i = 0; i < vector.size(); ++i) {
            vector2.addElement(vector.elementAt(i));
        }
        list.addProperty(this.getPlugInId(), s, columnName, vector2);
    }
    
    public void processMetric2(final PropertyList list, final String plugInId, final String s) throws DatabasePluginComponent.SchemaLockedException, DatabasePluginComponent.SchemaDeniedException, DatabasePluginComponent.SchemaParsingException {
        this.setPlugInId(plugInId);
        try {
            if (s.toLowerCase().startsWith("metric.vst.")) {
                this.processVstProperty(list, s.substring(0, "metric.vst.".length() - 1), s.substring("metric.vst.".length()));
            }
        }
        catch (PropertyNameException ex) {
            list.addProperty(this.getPlugInId(), s, null, (String[])null);
        }
    }
    
    public Hashtable getVstHashTable() {
        return this.getVstHashTable("102b");
    }
    
    public Hashtable getVstHashTable(final String s) {
        if (s.equals("9")) {
            return this.m_vstTables_v9;
        }
        if (s.equals("10")) {
            return this.m_vstTables_v10;
        }
        if (s.equals("101")) {
            return this.m_vstTables_v10_64;
        }
        if (s.equals("101c")) {
            return this.m_vstTables_v101c;
        }
        return this.m_vstTables;
    }
    
    public TableSchema getVstTableSchema(final String s) {
        return this.getVstTableSchema(s, "102b");
    }
    
    public TableSchema getVstTableSchema(final String s, final String s2) {
        return this.getVstHashTable(s2).get(s.toLowerCase());
    }
    
    public void generatePropFile() throws SaveIOException, NoSuchGroupException {
        this.generatePropFile(AgentProperties.propertyFileName);
    }
    
    public void generatePropFile(final String s) throws SaveIOException, NoSuchGroupException {
        this.save(s, "Created: " + DateFormat.getDateInstance().format(new Date()));
    }
    
    public static Object[] getFullTableSchema(final String s) {
        return getFullTableSchema(s, "9");
    }
    
    public static Object[] getFullTableSchema(final String s, final String s2) {
        final Vector<Hashtable<String, String>> vector = new Vector<Hashtable<String, String>>();
        try {
            final Enumeration columnSchema = AgentProperties.m_self.getVstTableSchema(s, s2).getColumnSchema();
            while (columnSchema.hasMoreElements()) {
                final Hashtable<String, String> e = new Hashtable<String, String>();
                final TableSchema.ColumnSchema columnSchema2 = columnSchema.nextElement();
                e.put("columnName", columnSchema2.getColumnName());
                e.put("columnId", (String)new Integer(columnSchema2.getColumnId()));
                e.put("columnTypeName", columnSchema2.getColumnType());
                e.put("columnTypeId", (String)new Integer(columnSchema2.getColumnTypeId()));
                e.put("columnExtentSize", (String)new Integer(columnSchema2.getColumnExtentSize()));
                vector.add(e);
            }
        }
        catch (Exception ex) {
            ProLog.logd("DatabaseAgent", 4, "getStatisticsSchema failed: " + ex.getMessage());
        }
        return vector.toArray(new Object[0]);
    }
    
    public static Hashtable getPartialTableSchema(final String[] array) {
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        try {
            for (int i = 0; i < array.length; ++i) {
                final Vector<String> vector = new Vector<String>();
                final Enumeration columnSchema = AgentProperties.m_self.getVstTableSchema(array[i]).getColumnSchema();
                while (columnSchema.hasMoreElements()) {
                    vector.add(columnSchema.nextElement().getColumnName());
                }
                hashtable.put(array[i], vector.toArray(new String[0]));
            }
        }
        catch (Exception ex) {
            ProLog.logd("DatabaseAgent", 4, "getStatisticsSchema failed: " + ex.getMessage());
        }
        return hashtable;
    }
    
    public static void main(final String[] array) {
        String str = null;
        if (array != null && array.length != 0) {
            str = array[0];
        }
        try {
            if (str != null) {
                System.out.println("Using property file " + str);
                final AgentProperties agentProperties = new AgentProperties(str);
            }
            else {
                final String propertyFileName = AgentProperties.propertyFileName;
                new AgentProperties(propertyFileName).generatePropFile(propertyFileName);
            }
        }
        catch (Exception obj) {
            System.out.println(" Got exeception " + obj);
            obj.printStackTrace();
        }
        System.exit(0);
    }
    
    static {
        AgentProperties.schemaFileName = "agent.schema";
        AgentProperties.propertyFileName = "agent.properties";
        AgentProperties.m_self = null;
    }
    
    public static class MyOrderedHashtable extends Hashtable
    {
        Vector orderedKeyVector;
        
        public MyOrderedHashtable() {
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
    
    public class ResultSetSchema
    {
        public byte[] resultSet;
        public long recID;
        
        ResultSetSchema(final byte[] resultSet, final long recID) {
            this.resultSet = resultSet;
            this.recID = recID;
        }
    }
    
    public class TableSchema
    {
        private String tableName;
        private Integer tableId;
        private MyOrderedHashtable columnSchemaTable;
        
        TableSchema(final String tableName, final Integer tableId) {
            this.tableName = null;
            this.tableId = null;
            this.columnSchemaTable = new MyOrderedHashtable();
            this.tableName = tableName;
            this.tableId = tableId;
        }
        
        private int getColumnTypeId(final String s) {
            int n = 0;
            if (s.startsWith("char")) {
                n = 1;
            }
            else if (s.equals("varchar")) {
                n = 2;
            }
            else if (s.startsWith("integerarray")) {
                n = 3;
            }
            else if (s.startsWith("longarray")) {
                n = 36;
            }
            else if (s.startsWith("stringarray")) {
                n = 4;
            }
            else if (s.equals("numeric")) {
                n = 7;
            }
            else if (s.equals("decimal")) {
                n = 7;
            }
            else if (s.equals("bit")) {
                n = 9;
            }
            else if (s.startsWith("tiny")) {
                n = 16;
            }
            else if (s.startsWith("smallsigned")) {
                n = 18;
            }
            else if (s.startsWith("small")) {
                n = 17;
            }
            else if (s.startsWith("integersigned")) {
                n = 6;
            }
            else if (s.startsWith("int")) {
                n = 19;
            }
            else if (s.startsWith("big")) {
                n = 20;
            }
            else if (s.equals("real")) {
                n = 21;
            }
            else if (s.equals("float")) {
                n = 22;
            }
            else if (s.startsWith("double")) {
                n = 23;
            }
            else if (s.equals("binary")) {
                n = 24;
            }
            else if (s.equals("varbinary")) {
                n = 25;
            }
            else if (s.equals("date")) {
                n = 32;
            }
            else if (s.equals("time")) {
                n = 33;
            }
            else if (s.equals("timestamp")) {
                n = 34;
            }
            else if (s.equals("ignore")) {
                n = 35;
            }
            return n;
        }
        
        String getTableName() {
            return this.tableName;
        }
        
        Integer getTableId() {
            return this.tableId;
        }
        
        private void addColumn(final String s, final Integer n, final String s2, final Integer n2) {
            this.columnSchemaTable.put(s, new ColumnSchema(s, n, s2, new Integer(this.getColumnTypeId(s2)), n2));
        }
        
        private void replaceColumn(final String s, final Integer n, final String s2, final Integer n2) {
            this.columnSchemaTable.put(s, new ColumnSchema(s, n, s2, new Integer(this.getColumnTypeId(s2)), n2));
        }
        
        public ColumnSchema getColumnSchema(final Object key) {
            return this.columnSchemaTable.get(key);
        }
        
        public Enumeration getColumnSchema() {
            return this.columnSchemaTable.elements();
        }
        
        public int getColumnSchemaSize() {
            return this.columnSchemaTable.size();
        }
        
        public class ColumnSchema
        {
            String columnName;
            Integer columnId;
            String columnType;
            Integer columnTypeId;
            Integer columnExtentSize;
            
            ColumnSchema(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
                this.columnName = null;
                this.columnId = null;
                this.columnType = null;
                this.columnTypeId = null;
                this.columnExtentSize = null;
                this.columnName = (String)o;
                this.columnId = (Integer)o2;
                this.columnType = (String)o3;
                this.columnTypeId = (Integer)o4;
                this.columnExtentSize = (Integer)o5;
            }
            
            public String getColumnName() {
                return this.columnName;
            }
            
            public int getColumnId() {
                if (this.columnId != null) {
                    return this.columnId;
                }
                return -1;
            }
            
            public String getColumnType() {
                return this.columnType;
            }
            
            public int getColumnTypeId() {
                if (this.columnTypeId != null) {
                    return this.columnTypeId;
                }
                return -1;
            }
            
            public int getColumnExtentSize() {
                if (this.columnExtentSize != null) {
                    return this.columnExtentSize;
                }
                return -1;
            }
        }
    }
}
