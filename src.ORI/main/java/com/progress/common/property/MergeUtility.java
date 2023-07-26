// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.international.resources.ProgressResources;
import com.progress.common.exception.ProException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;

public class MergeUtility implements MergePropertiesConst
{
    private MergeGetopt m_options;
    private PropertyManager m_propertyManager;
    
    public MergeUtility(final PropertyManager propertyManager) {
        this.m_options = null;
        this.m_propertyManager = null;
        (this.m_propertyManager = propertyManager).setPutPropertyFilter(new MergePropertyFilter(this.m_propertyManager));
    }
    
    public MergeUtility(final PropertyManager propertyManager, final MergeGetopt options) throws PropertyManager.PropertyException, createGroupException, IOException {
        this.m_options = null;
        this.m_propertyManager = null;
        this.m_options = options;
        (this.m_propertyManager = propertyManager).setPutPropertyFilter(new MergePropertyFilter(this.m_propertyManager));
        if (options.isListAll()) {
            if (options.getRecurse() && options.isDatabaseType()) {
                this.listDatabaseGroup(options.getGroupName());
            }
            else {
                this.listAll(options.getGroupName());
            }
            return;
        }
        if (options.isListOnly()) {
            this.listOnly(options.getGroupName());
            return;
        }
        if (options.getBackup()) {
            this.doBackup(options.getPropertyFile(), options.getPropertyFileHeaderBackup());
        }
        if (options.isCreate()) {
            this.create(options.getDelta());
        }
        if (options.isDelete()) {
            this.delete(options.getDelta());
        }
        if (options.isUpdate()) {
            this.update(options.getDelta());
        }
        if (!options.isValidate()) {
            this.m_propertyManager.save(options.getPropertyFile(), this.m_options.getPropertyFileHeader(), null, MergeProperties.m_recursive, MergeProperties.m_includeAllRegistered, MergeProperties.m_includeAncestors, MergeProperties.m_saveComments, MergeProperties.m_includeCommentedDefaults);
        }
    }
    
    public void doBackup(final String str, final String s) throws PropertyManager.SaveIOException, PropertyManager.NoSuchGroupException, IOException {
        this.m_propertyManager.save(str + new SimpleDateFormat("MMddyyyyhhmmss").format(new Date()), s, null, MergeProperties.m_recursive, MergeProperties.m_includeAllRegistered, MergeProperties.m_includeAncestors, MergeProperties.m_saveComments, MergeProperties.m_includeCommentedDefaults);
    }
    
    public void update(final String fileName) throws PropertyManager.LoadIOException, PropertyManager.PropertySyntaxException, PropertyManager.PropertyVersionException, PropertyManager.LoadFileNotFoundException, PropertyManager.GroupNameException, PropertyManager.PropertyNameException, PropertyManager.PropertyValueException, PropertyManager.NoSuchGroupException, PropertyManager.PropertyException, FileNotFoundException, IOException {
        final FileReader fileReader = new FileReader(fileName);
        this.m_propertyManager.load(fileReader, true);
        fileReader.close();
    }
    
    public void delete(final String fileName) throws FileNotFoundException, IOException {
        final FileReader in = new FileReader(fileName);
        final BufferedReader bufferedReader = new BufferedReader(in);
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            final String trim = line.trim();
            if (!trim.startsWith("[")) {
                continue;
            }
            final int index = trim.indexOf("]");
            if (index <= 0) {
                continue;
            }
            final String substring = trim.substring(1, index);
            if (this.m_options.getRecurse() && this.m_options.isDatabaseType()) {
                this.deleteDatabaseGroup(substring);
            }
            else {
                this.m_propertyManager.removeGroup(substring);
            }
        }
        bufferedReader.close();
        in.close();
    }
    
    public void deleteDatabaseGroup(final String str) {
        final String string = "database." + str;
        final String[] arrayProperty = this.m_propertyManager.getArrayProperty(string + ".configurations");
        if (arrayProperty != null) {
            for (int i = 0; i < arrayProperty.length; ++i) {
                final String string2 = "configuration." + arrayProperty[i];
                final String[] arrayProperty2 = this.m_propertyManager.getArrayProperty(string2 + ".servergroups");
                for (int j = 0; j < arrayProperty2.length; ++j) {
                    this.m_propertyManager.removeGroup("servergroup." + arrayProperty2[j]);
                }
                this.m_propertyManager.removeGroup(string2);
            }
        }
        this.m_propertyManager.removeGroup(string);
    }
    
    public void listDatabaseGroup(final String str) throws PropertyManager.NoSuchGroupException {
        final String string = "database." + str;
        this.listOnly(string);
        final String[] arrayProperty = this.m_propertyManager.getArrayProperty(string + ".configurations");
        if (arrayProperty != null) {
            for (int i = 0; i < arrayProperty.length; ++i) {
                final String string2 = "configuration." + arrayProperty[i];
                this.listOnly(string2);
                final String[] arrayProperty2 = this.m_propertyManager.getArrayProperty(string2 + ".servergroups");
                for (int j = 0; j < arrayProperty2.length; ++j) {
                    this.listOnly("servergroup." + arrayProperty2[j]);
                }
            }
        }
    }
    
    public void listOnly(final String s) throws PropertyManager.NoSuchGroupException {
        this.list(s, false, false);
    }
    
    public void listAll(final String s) throws PropertyManager.NoSuchGroupException {
        this.list(s, true, true);
    }
    
    private void list(final String s, final boolean b, final boolean b2) throws PropertyManager.NoSuchGroupException {
        final PropertyManager.PropertyCollection properties = this.m_propertyManager.properties(s, b, b2);
        System.out.println("[" + s + "]");
        while (properties.hasMoreElements()) {
            final PropertyManager.Property property = (PropertyManager.Property)properties.nextElement();
            System.out.println("    " + property.getName() + "=" + this.m_propertyManager.getProperty(s + "." + property.getName()));
        }
    }
    
    public void create(final String fileName) throws PropertyManager.LoadIOException, PropertyManager.PropertySyntaxException, PropertyManager.PropertyVersionException, PropertyManager.LoadFileNotFoundException, PropertyManager.GroupNameException, PropertyManager.PropertyNameException, PropertyManager.PropertyValueException, PropertyManager.NoSuchGroupException, PropertyManager.PropertyException, createGroupException, FileNotFoundException, IOException {
        final FileReader in = new FileReader(fileName);
        final BufferedReader bufferedReader = new BufferedReader(in);
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                bufferedReader.close();
                in.close();
                this.update(fileName);
                return;
            }
            final String trim = line.trim();
            if (!trim.startsWith("[")) {
                continue;
            }
            final int index = trim.indexOf("]");
            if (index <= 0) {
                continue;
            }
            final String substring = trim.substring(1, index);
            if (this.m_propertyManager.isKnownGroup(substring)) {
                throw new createGroupException(substring);
            }
        }
    }
    
    public static class createGroupException extends ProException
    {
        public createGroupException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "pre-existing group", new Object[] { s }), (Object[])null);
        }
    }
}
