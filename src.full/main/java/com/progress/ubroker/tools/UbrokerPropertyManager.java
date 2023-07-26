// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Hashtable;
import com.progress.common.networkevents.EventBroker;
import com.progress.ubroker.util.IPropConst;
import com.progress.common.property.PropertyManager;

public class UbrokerPropertyManager extends PropertyManager implements IPropConst
{
    public UbrokerPropertyManager() {
    }
    
    public UbrokerPropertyManager(final String s, final EventBroker eventBroker) throws PropertyException {
        super(s, eventBroker);
    }
    
    protected Hashtable getGroupHashtable() {
        return new SortedHashtable();
    }
    
    protected Hashtable getPropertyHashtable() {
        if (super.m_getOrderedPropertyHashTable) {
            return new OrderedHashtable();
        }
        return new SortedHashtable();
    }
    
    protected void writePropertyVersion(final BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("%% version b001" + System.getProperty("line.separator"));
    }
    
    protected String getPropertyNameForWriting(final String s, final Property property) {
        final String propertyNameForWriting = super.getPropertyNameForWriting(property);
        if (propertyNameForWriting == null || s == null || s.toLowerCase().startsWith("environment")) {
            return propertyNameForWriting;
        }
        return propertyNameForWriting.toLowerCase();
    }
    
    public void createPropertiesFileFromSchema(final String s) throws CreateDefaultPropertiesFileException {
        try {
            this.save(s, "Ubroker Properties Created from Schema", null, true, true, false, false, false);
        }
        catch (Exception ex) {
            throw new CreateDefaultPropertiesFileException(ex.getMessage());
        }
    }
    
    public static class CreateDefaultPropertiesFileException extends PropertyException
    {
        public CreateDefaultPropertiesFileException(final String s) {
            super("Failed to create ubroker.properties file", new Object[] { s });
        }
    }
}
