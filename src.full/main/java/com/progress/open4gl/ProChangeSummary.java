// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import org.eclipse.emf.ecore.change.ChangeDescription;
import commonj.sdo.DataGraph;
import org.eclipse.emf.ecore.sdo.EChangeSummary;
import commonj.sdo.Type;
import java.util.Vector;
import commonj.sdo.Property;
import java.util.Iterator;
import java.util.List;
import commonj.sdo.DataObject;
import commonj.sdo.ChangeSummary;
import com.progress.open4gl.dynamicapi.ChangeSumm;

public class ProChangeSummary extends ChangeSumm
{
    public ProChangeSummary(final ChangeSummary changeSummary) {
        super(changeSummary);
    }
    
    public ProDataGraph getChanges() throws Exception, Open4GLException, ProDataException {
        if (super.m_chgSummary.isLogging()) {
            super.m_chgSummary.endLogging();
        }
        final ProDataGraph proDataGraph = (ProDataGraph)super.m_chgSummary.getDataGraph();
        proDataGraph.getMetaData();
        final ProDataGraph proDataGraph2 = new ProDataGraph(proDataGraph);
        final int numTables = proDataGraph.getNumTables();
        this.initVectors(numTables);
        for (int i = 0; i < numTables; ++i) {
            final Iterator iterator = proDataGraph.getProDataObjects(i).iterator();
            while (iterator.hasNext()) {
                final ProDataObject changedDataObject = ChangeSumm.getChangedDataObject(iterator.next());
                if (changedDataObject != null) {
                    if (super.m_chgSummary.isCreated((DataObject)changedDataObject)) {
                        super.m_rowState[i].add(ChangeSumm.ADD);
                        super.m_newDataObjs[i].add(this.createNewRow(changedDataObject, proDataGraph2));
                    }
                    else if (super.m_chgSummary.isDeleted((DataObject)changedDataObject)) {
                        super.m_rowState[i].add(ChangeSumm.DELETE);
                        final ProDataObject origForDeletedRow = this.createOrigForDeletedRow(changedDataObject, proDataGraph2);
                        proDataGraph2.addProDataObject(origForDeletedRow);
                        super.m_deleteDataObjs[i].add(origForDeletedRow);
                    }
                    else {
                        super.m_rowState[i].add(ChangeSumm.CHANGE);
                        proDataGraph2.addProDataObject(this.createOrigForModifiedRow(changedDataObject, proDataGraph2));
                        super.m_changeDataObjs[i].add(changedDataObject);
                        super.m_changeSettings[i].add(super.m_chgSummary.getOldValues((DataObject)changedDataObject));
                    }
                }
            }
        }
        for (final ProDataObject proDataObject : super.m_chgSummary.getChangedDataObjects()) {
            if (super.m_chgSummary.isDeleted((DataObject)proDataObject)) {
                final ProDataObject origForDeletedRow2 = this.createOrigForDeletedRow(proDataObject, proDataGraph2);
                if (origForDeletedRow2 == null) {
                    continue;
                }
                final int tableIndex = proDataGraph2.getTableIndex(origForDeletedRow2.getTableName());
                super.m_rowState[tableIndex].add(ChangeSumm.DELETE);
                proDataGraph2.addProDataObject(origForDeletedRow2);
                super.m_deleteDataObjs[tableIndex].add(origForDeletedRow2);
            }
        }
        final ChangeSummary changeSummary = proDataGraph2.getChangeSummary();
        changeSummary.beginLogging();
        for (int j = 0; j < numTables; ++j) {
            proDataGraph2.getProDataObjects(j);
            int n = 0;
            int index = 0;
            int n2 = 0;
            int n3 = 0;
            final Iterator iterator3 = super.m_rowState[j].iterator();
            while (iterator3.hasNext()) {
                if (iterator3.next() == (int)ChangeSumm.ADD) {
                    proDataGraph2.addProDataObject(n3, super.m_newDataObjs[j].get(n++));
                }
                ++n2;
                ++n3;
            }
            final List proDataObjects = proDataGraph2.getProDataObjects(j);
            int n4 = 0;
            final Iterator iterator4 = super.m_rowState[j].iterator();
            while (iterator4.hasNext()) {
                if (iterator4.next() == (int)ChangeSumm.CHANGE) {
                    final ProDataObject proDataObject2 = proDataObjects.get(n4);
                    final ProDataObject proDataObject3 = super.m_changeDataObjs[j].get(index);
                    final Iterator<ChangeSummary.Setting> iterator5 = super.m_changeSettings[j].get(index++).iterator();
                    while (iterator5.hasNext()) {
                        final Property property = iterator5.next().getProperty();
                        proDataObject2.set(property, proDataObject3.get(property));
                    }
                }
                ++n4;
            }
            final Iterator iterator6 = super.m_deleteDataObjs[j].iterator();
            while (iterator6.hasNext()) {
                iterator6.next().delete();
            }
        }
        changeSummary.endLogging();
        return proDataGraph2;
    }
    
    private void initVectors(final int n) {
        super.m_rowState = new Vector[n];
        super.m_changeDataObjs = new Vector[n];
        super.m_changeSettings = new Vector[n];
        super.m_newDataObjs = new Vector[n];
        super.m_deleteDataObjs = new Vector[n];
        for (int i = 0; i < n; ++i) {
            super.m_rowState[i] = new Vector();
            super.m_changeDataObjs[i] = new Vector();
            super.m_changeSettings[i] = new Vector();
            super.m_newDataObjs[i] = new Vector();
            super.m_deleteDataObjs[i] = new Vector();
        }
    }
    
    private ProDataObject createOrigForModifiedRow(final ProDataObject proDataObject, final ProDataGraph proDataGraph) throws Open4GLException {
        final Type type = proDataObject.getType();
        final ProDataObject proDataObject2 = proDataGraph.createProDataObject(type.getName());
        final List oldValues = super.m_chgSummary.getOldValues((DataObject)proDataObject);
        for (final Property property : type.getProperties()) {
            proDataObject2.set(property, ChangeSumm.getOrigValue(proDataObject, property, oldValues));
        }
        return proDataObject2;
    }
    
    private ProDataObject createOrigForDeletedRow(final ProDataObject proDataObject, final ProDataGraph proDataGraph) throws Open4GLException {
        final String name = proDataObject.getType().getName();
        final List oldValues = super.m_chgSummary.getOldValues((DataObject)proDataObject);
        if (oldValues == null) {
            return null;
        }
        final ProDataObject proDataObject2 = proDataGraph.createProDataObject(name);
        for (final ChangeSummary.Setting setting : oldValues) {
            final Property property = setting.getProperty();
            if (setting.getProperty().getType().getName().equals("EJavaObject")) {
                if (!setting.isSet()) {
                    continue;
                }
                proDataObject2.set(property, setting.getValue());
            }
            else {
                proDataObject2.set(property, setting.getValue());
            }
        }
        return proDataObject2;
    }
    
    private ProDataObject createNewRow(final ProDataObject proDataObject, final ProDataGraph proDataGraph) throws Open4GLException {
        final Type type = proDataObject.getType();
        final ProDataObject proDataObject2 = proDataGraph.createProDataObject(type.getName());
        for (final Property property : type.getProperties()) {
            proDataObject2.set(property, proDataObject.get(property));
        }
        return proDataObject2;
    }
    
    private void applyChangesToDataObj(final ProDataObject proDataObject, final ProDataGraph proDataGraph) throws Exception, Open4GLException {
        final Type type = proDataObject.getType();
        ProDataObject proDataObject2 = null;
        final List oldValues = super.m_chgSummary.getOldValues((DataObject)proDataObject);
        final Vector<Object> vector = new Vector<Object>();
        final Iterator iterator = type.getProperties().iterator();
        while (iterator.hasNext()) {
            vector.add(ChangeSumm.getOrigValue(proDataObject, iterator.next(), oldValues));
        }
        final Iterator<ProDataObject> iterator2 = proDataGraph.getProDataObjects(proDataObject.getTableName()).iterator();
        while (iterator2.hasNext()) {
            proDataObject2 = iterator2.next();
            if (this.hasSameValues(proDataObject2, vector)) {
                break;
            }
            proDataObject2 = null;
        }
        if (proDataObject2 != null) {
            final Iterator<ChangeSummary.Setting> iterator3 = oldValues.iterator();
            while (iterator3.hasNext()) {
                final Property property = iterator3.next().getProperty();
                proDataObject2.set(property, proDataObject.get(property));
            }
        }
    }
    
    private boolean hasSameValues(final ProDataObject proDataObject, final List list) {
        final Type type = proDataObject.getType();
        if (proDataObject.getMetaData().getFieldCount() != list.size()) {
            return false;
        }
        final List properties = type.getProperties();
        final Iterator<Object> iterator = list.iterator();
        final Iterator<Property> iterator2 = properties.iterator();
        while (iterator2.hasNext()) {
            if (!proDataObject.get((Property)iterator2.next()).equals(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public void beginLogging() {
        ((ChangeDescription)super.m_chgSummary).apply();
        super.m_chgSummary.beginLogging();
    }
    
    public void endLogging() {
        super.m_chgSummary.endLogging();
    }
    
    public List getChangedDataObjects() {
        return super.m_chgSummary.getChangedDataObjects();
    }
    
    public DataGraph getDataGraph() {
        return super.m_chgSummary.getDataGraph();
    }
    
    public List getOldValues(final ProDataObject proDataObject) {
        return super.m_chgSummary.getOldValues((DataObject)proDataObject);
    }
    
    public boolean isCreated(final ProDataObject proDataObject) {
        return super.m_chgSummary.isCreated((DataObject)proDataObject);
    }
    
    public boolean isDeleted(final ProDataObject proDataObject) {
        return super.m_chgSummary.isDeleted((DataObject)proDataObject);
    }
    
    public boolean isLogging() {
        return super.m_chgSummary.isLogging();
    }
}
