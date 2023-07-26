// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.util.Date;
import java.text.DateFormat;
import com.progress.common.networkevents.EventBroker;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Hashtable;
import com.progress.common.property.PropertyManager;

public class SMProperties extends PropertyManager
{
    private static final String VERSION = "%% version 1.1";
    public static String schemaFileName;
    public static String propertyFileName;
    String m_plugInId;
    private static final Hashtable ipLookup;
    
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
        if (propertyValueForWriting == null || groupNameForWriting == null || propertyNameForWriting == null || propertyNameForWriting.equalsIgnoreCase("DisplayName") || propertyNameForWriting.equalsIgnoreCase("DatabaseName")) {
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
        return SMProperties.schemaFileName;
    }
    
    public SMProperties(final String s) throws PropertyException {
        this(s, null);
    }
    
    public SMProperties(final String s, final EventBroker eventBroker) throws PropertyValueException, LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyException {
        super(getSchemaFileName(), eventBroker);
        this.m_plugInId = null;
        if (s != null && s.length() != 0) {
            this.load(s);
        }
    }
    
    public void generatePropFile() throws SaveIOException, NoSuchGroupException {
        this.generatePropFile(SMProperties.propertyFileName);
    }
    
    public void generatePropFile(final String s) throws SaveIOException, NoSuchGroupException {
        this.save(s, "Created: " + DateFormat.getDateInstance().format(new Date()));
    }
    
    public void save() throws SaveIOException {
        this.save(SMPlugIn.get().getPropertyFileName(), "Script Managed (Remote) Database Properties File");
    }
    
    public static void main(final String[] array) {
        String str = null;
        if (array != null && array.length != 0) {
            str = array[0];
        }
        try {
            if (str != null) {
                System.out.println("Using property file " + str);
                final SMProperties smProperties = new SMProperties(str);
            }
            else {
                final String propertyFileName = SMProperties.propertyFileName;
                new SMProperties(propertyFileName).generatePropFile(propertyFileName);
            }
        }
        catch (Exception obj) {
            System.out.println(" Got exeception " + obj);
            obj.printStackTrace();
        }
        System.exit(0);
    }
    
    static {
        SMProperties.schemaFileName = "smdatabase.schema";
        SMProperties.propertyFileName = "smdatabase.properties";
        ipLookup = new Hashtable();
    }
}
