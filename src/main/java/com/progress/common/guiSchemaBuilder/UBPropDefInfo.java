// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiSchemaBuilder;

import java.util.Enumeration;
import java.io.FileWriter;
import com.progress.common.property.PropertyManager;
import java.util.Vector;
import java.util.Hashtable;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.ubroker.util.IPropConst;

public class UBPropDefInfo extends PropDefInfo implements IPropDefInfo, IPropConst
{
    private boolean m_propFileLoaded;
    private PropMgrUtils m_pmuObj;
    private String m_propFilename;
    private String[] m_propGroupList;
    private Hashtable m_groupProperties;
    
    public UBPropDefInfo(final int n) {
        super(n);
        this.m_propFileLoaded = false;
        super.setPDI(this);
        this.m_propFilename = null;
    }
    
    public UBPropDefInfo(final int n, final String propFilename) {
        super(n);
        this.m_propFileLoaded = false;
        super.setPDI(this);
        this.m_propFilename = propFilename;
    }
    
    public void setPropFilename(final String propFilename) {
        this.m_propFilename = propFilename;
    }
    
    public String getPropFilename() {
        return this.m_propFilename;
    }
    
    public boolean loadProperties() {
        if (this.m_propFilename != null) {
            try {
                this.m_pmuObj = new PropMgrUtils(false, this.m_propFilename);
                return this.m_propFileLoaded = this.m_pmuObj.isPropertyFileLoaded();
            }
            catch (Exception ex) {}
        }
        return false;
    }
    
    public boolean loadProperties(final String propFilename) {
        this.m_propFilename = propFilename;
        try {
            this.m_pmuObj = new PropMgrUtils(false, this.m_propFilename);
            return this.m_propFileLoaded = this.m_pmuObj.isPropertyFileLoaded();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public String[] getPropGroupList() {
        final String[] array = null;
        if (this.m_propFileLoaded) {
            try {
                final String[] servicesList = this.m_pmuObj.getServicesList();
                final Vector vector = new Vector<String>();
                for (int i = 0; i < servicesList.length; ++i) {
                    final int index = servicesList[i].indexOf(".");
                    if (index < 0) {
                        vector.addElement(servicesList[i]);
                    }
                    else {
                        final String substring = servicesList[i].substring(0, index);
                        if (substring.equals("UBroker")) {
                            if (servicesList[i].indexOf(".", index + 1) < 0) {
                                vector.addElement(servicesList[i]);
                            }
                        }
                        else if (substring.equals("WebSpeed")) {
                            vector.addElement(servicesList[i]);
                        }
                    }
                }
                if (vector.size() > 0) {
                    this.m_propGroupList = new String[vector.size()];
                    vector.copyInto(this.m_propGroupList);
                    return this.m_propGroupList;
                }
            }
            catch (Exception ex) {}
        }
        return array;
    }
    
    public Hashtable getPropertyList(final String str) {
        if (this.m_propFileLoaded) {
            try {
                final PropertyManager.PropertyCollection propertiesNoAncestor = this.m_pmuObj.getPropertiesNoAncestor(str);
                final Hashtable<String, String> hashtable = new Hashtable<String, String>();
                while (propertiesNoAncestor.hasMoreElements()) {
                    final PropertyManager.Property property = (PropertyManager.Property)propertiesNoAncestor.nextElement();
                    final String string = str + "." + property.getName();
                    final String tellPropertyDataType = this.tellPropertyDataType(property);
                    System.out.println(string + " = " + tellPropertyDataType);
                    hashtable.put(string, tellPropertyDataType);
                }
                return hashtable;
            }
            catch (Exception ex) {}
        }
        return null;
    }
    
    public void addPropGroupList(final String s) {
    }
    
    public void addProperty(final String s) {
    }
    
    public void setPropertyDataType(final String s, final String s2) {
    }
    
    public void setPropertyList(final Hashtable hashtable) {
    }
    
    public boolean buildGuiSchema() {
        super.buildSchema();
        this.getPropGroupList();
        if (this.m_propGroupList != null) {
            this.m_groupProperties = new Hashtable();
            for (int i = 0; i < this.m_propGroupList.length; ++i) {
                this.m_groupProperties.put(this.m_propGroupList[i], this.getPropertyList(this.m_propGroupList[i]));
            }
        }
        return true;
    }
    
    public boolean saveSchemaDef(final String s) {
        return super.saveSchemaFile(s);
    }
    
    public boolean saveSchemaDef() {
        return false;
    }
    
    public boolean loadSchemaDef(final String s) {
        return false;
    }
    
    public String tellPropertyDataType(final PropertyManager.Property property) {
        return super.tellPropertyDataType(property);
    }
    
    public boolean writeSchema(final FileWriter fileWriter) {
        boolean b = false;
        try {
            for (int i = 0; i < this.m_propGroupList.length; ++i) {
                fileWriter.write(PropDefInfo.NEWLINE + "[" + "Group" + "." + this.m_propGroupList[i] + "]" + PropDefInfo.NEWLINE);
                final Hashtable<String, Hashtable<String, Hashtable<String, Hashtable>>> hashtable = this.m_groupProperties.get(this.m_propGroupList[i]);
                final Enumeration<String> keys = hashtable.keys();
                try {
                    while (keys.hasMoreElements()) {
                        final String s = keys.nextElement();
                        fileWriter.write(PropDefInfo.NEWLINE + "[" + "Property" + "." + (String)hashtable.get(s) + "." + s + "]" + PropDefInfo.NEWLINE);
                        b = true;
                    }
                }
                catch (Exception ex) {
                    b = false;
                }
            }
        }
        catch (Exception ex2) {
            b = false;
        }
        return b;
    }
}
