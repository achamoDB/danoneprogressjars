// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.List;
import commonj.sdo.Property;
import java.util.Iterator;
import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.ProDataObject;
import java.util.Vector;
import commonj.sdo.ChangeSummary;

public class ChangeSumm
{
    protected ChangeSummary m_chgSummary;
    protected Vector[] m_rowState;
    protected Vector[] m_changeDataObjs;
    protected Vector[] m_changeSettings;
    protected Vector[] m_newDataObjs;
    protected Vector[] m_deleteDataObjs;
    protected static Integer DELETE;
    protected static Integer CHANGE;
    protected static Integer ADD;
    
    public ChangeSumm(final ChangeSummary chgSummary) {
        this.m_chgSummary = chgSummary;
    }
    
    protected static ProDataObject getChangedDataObject(final ProDataObject obj) {
        for (final ProDataObject proDataObject : ((ProDataGraph)obj.getDataGraph()).getProChangeSummary().getChangedDataObjects()) {
            if (proDataObject.equals(obj)) {
                return proDataObject;
            }
        }
        return null;
    }
    
    protected static Object getOrigValue(final ProDataObject proDataObject, final Property property, final List list) {
        Object o = proDataObject.get(property);
        for (final ChangeSummary.Setting setting : list) {
            final Property property2 = setting.getProperty();
            if (property2.getName().equalsIgnoreCase(property.getName())) {
                if (!property2.getType().getName().equals("EJavaObject")) {
                    o = setting.getValue();
                    break;
                }
                if (setting.isSet()) {
                    o = setting.getValue();
                    break;
                }
                o = null;
                break;
            }
        }
        return o;
    }
    
    static {
        ChangeSumm.DELETE = new Integer(1);
        ChangeSumm.CHANGE = new Integer(2);
        ChangeSumm.ADD = new Integer(3);
    }
}
