// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Vector;
import java.io.Serializable;

public class PropertiesSaveDescriptor implements Serializable, IPropConst
{
    Vector m_propList;
    Vector m_valueList;
    Vector m_propertiesToRemove;
    String m_envSubGrp;
    String[] m_appSvcList;
    String m_propGroupName;
    String m_svcName;
    boolean m_delProperty;
    boolean m_addNew;
    
    public PropertiesSaveDescriptor() {
        this.m_propList = null;
        this.m_valueList = null;
        this.m_propertiesToRemove = null;
        this.m_envSubGrp = null;
        this.m_appSvcList = null;
        this.m_propGroupName = null;
        this.m_svcName = null;
        this.m_delProperty = false;
        this.m_addNew = false;
        this.m_propList = new Vector();
        this.m_valueList = new Vector();
        this.m_propertiesToRemove = new Vector();
    }
    
    public void setEnvSubGrpName(final String envSubGrp) {
        this.m_envSubGrp = envSubGrp;
    }
    
    public String getEnvSubGrpName() {
        return this.m_envSubGrp;
    }
    
    public void setPropGroupName(final String propGroupName) {
        this.m_propGroupName = propGroupName;
    }
    
    public String getPropGroupName() {
        return this.m_propGroupName;
    }
    
    public void addPropertyToRemove(final String obj) {
        this.m_propertiesToRemove.addElement(obj);
        this.m_delProperty = true;
    }
    
    public Vector getPropList() {
        return this.m_propList;
    }
    
    public void setPropList(final Vector propList) {
        this.m_propList = propList;
    }
    
    public String[] getPropListStrings() {
        return this.vectorToStrings(this.m_propList);
    }
    
    public void setValueList(final Vector valueList) {
        this.m_valueList = valueList;
    }
    
    public Vector getValueList() {
        return this.m_valueList;
    }
    
    public String[] getValueListStrings() {
        return this.vectorToStrings(this.m_valueList);
    }
    
    public String[] getAppSvcList() {
        return this.m_appSvcList;
    }
    
    public void setAppSvcList(final String[] appSvcList) {
        this.m_appSvcList = appSvcList;
    }
    
    public void setAddNew() {
        this.m_addNew = true;
    }
    
    public boolean isNewInstance() {
        return this.m_addNew;
    }
    
    public String[] getPropertiesToRemove() {
        return this.vectorToStrings(this.m_propertiesToRemove);
    }
    
    public void setSvcName(final String svcName) {
        this.m_svcName = svcName;
    }
    
    public String getSvcName() {
        return this.m_svcName;
    }
    
    public boolean removeProperties() {
        return this.m_delProperty;
    }
    
    private String[] vectorToStrings(final Vector vector) {
        if (vector != null) {
            final String[] anArray = new String[vector.size()];
            vector.copyInto(anArray);
            return anArray;
        }
        return null;
    }
}
