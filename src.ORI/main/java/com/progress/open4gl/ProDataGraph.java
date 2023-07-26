// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import org.eclipse.emf.ecore.ETypedElement;
import java.util.Map;
import org.eclipse.emf.ecore.ENamedElement;
import java.util.Iterator;
import java.util.GregorianCalendar;
import java.util.Date;
import commonj.sdo.Property;
import org.eclipse.emf.ecore.EStructuralFeature;
import java.util.List;
import commonj.sdo.DataObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.sdo.impl.DynamicEDataObjectImpl;
import commonj.sdo.ChangeSummary;
import commonj.sdo.DataGraph;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.sdo.EDataObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.sdo.impl.EDataGraphImpl;

public class ProDataGraph extends EDataGraphImpl
{
    private static EcoreFactory m_ecoreFactory;
    private EPackage m_myPackage;
    private ProDataGraphMetaData m_dgmd;
    private EClass m_rootClass;
    private EDataObject m_rootDObject;
    private EClass[] m_ttClass;
    private EReference[] m_ttableClassRef;
    
    public ProDataGraph() {
        this.m_myPackage = null;
        this.m_rootClass = null;
        this.m_rootDObject = null;
        this.m_ttClass = null;
        this.m_ttableClassRef = null;
    }
    
    public ProDataGraph(final ProDataGraphMetaData dgmd) throws ProDataException, Exception {
        this.m_myPackage = null;
        this.m_rootClass = null;
        this.m_rootDObject = null;
        this.m_ttClass = null;
        this.m_ttableClassRef = null;
        this.m_dgmd = dgmd;
        this.createRootClass(dgmd.getProDataGraphName());
        this.createRootDataObject();
        this.setERootObject((EObject)this.m_rootDObject);
    }
    
    public ProDataGraph(final DataGraph dataGraph, final String s, final ProDataRelationMetaData[] array) throws Exception, ProDataException, Open4GLException {
        this.m_myPackage = null;
        this.m_rootClass = null;
        this.m_rootDObject = null;
        this.m_ttClass = null;
        this.m_ttableClassRef = null;
        this.m_dgmd = new ProDataGraphMetaData(dataGraph, s, array);
        this.createRootClass(s);
        this.createRootDataObject();
        this.setERootObject((EObject)this.m_rootDObject);
        this.populateProDataGraph(dataGraph);
        this.setChildTableReferences();
    }
    
    public ProDataGraph(final ProDataGraph proDataGraph) throws ProDataException, Exception, Open4GLException {
        this.m_myPackage = null;
        this.m_rootClass = null;
        this.m_rootDObject = null;
        this.m_ttClass = null;
        this.m_ttableClassRef = null;
        if (proDataGraph == null || proDataGraph.m_dgmd == null) {
            throw new Open4GLException("Invalid call to ProDataGraph(ProDataGraph) constructor: ProDataGraph is null or does not contain meta data.");
        }
        this.m_dgmd = proDataGraph.m_dgmd;
        this.m_rootClass = proDataGraph.m_rootClass;
        this.m_myPackage = proDataGraph.m_myPackage;
        this.m_ttClass = proDataGraph.m_ttClass;
        this.m_ttableClassRef = proDataGraph.m_ttableClassRef;
        this.createRootDataObject();
        this.setERootObject((EObject)this.m_rootDObject);
    }
    
    public ProChangeSummary getProChangeSummary() {
        return new ProChangeSummary(this.getChangeSummary());
    }
    
    public String getProDataGraphName() {
        if (this.m_dgmd == null) {
            return null;
        }
        return this.m_dgmd.getProDataGraphName();
    }
    
    public int getNumTables() {
        if (this.m_dgmd == null) {
            return 0;
        }
        return this.m_dgmd.getNumTables();
    }
    
    public ProDataGraphMetaData getMetaData() {
        return this.m_dgmd;
    }
    
    public void acceptChanges() {
        final ChangeSummary changeSummary = this.getChangeSummary();
        if (changeSummary.isLogging()) {
            changeSummary.beginLogging();
        }
    }
    
    public boolean hasError() {
        return this.m_dgmd != null && this.m_dgmd.hasError();
    }
    
    private void createRootClass(final String s) throws Exception, ProDataException {
        ((ENamedElement)(this.m_rootClass = ProDataGraph.m_ecoreFactory.createEClass())).setName(s);
        ((ENamedElement)(this.m_myPackage = ProDataGraph.m_ecoreFactory.createEPackage())).setName(s);
        this.m_myPackage.setNsPrefix(s);
        this.m_myPackage.setNsURI("urn:" + s + ":sdo");
        ((List<EClass>)this.m_myPackage.getEClassifiers()).add(this.m_rootClass);
        this.m_myPackage.setEFactoryInstance((EFactory)new DynamicEDataObjectImpl.FactoryImpl());
        this.m_myPackage.setEFactoryInstance((EFactory)new ProDataObject.FactoryImpl());
        ((Map<String, EPackage>)this.getResourceSet().getPackageRegistry()).put(this.m_myPackage.getNsURI(), this.m_myPackage);
        final int numTables = this.m_dgmd.getNumTables();
        this.m_ttClass = new EClass[numTables];
        this.m_ttableClassRef = new EReference[numTables];
        for (int i = 0; i < numTables; ++i) {
            final ProDataObjectMetaData tableMetaData = this.m_dgmd.getTableMetaData(i);
            this.m_ttClass[i] = ProDataGraph.m_ecoreFactory.createEClass();
            if (tableMetaData != null) {
                ((ENamedElement)this.m_ttClass[i]).setName(tableMetaData.getTableName());
            }
            this.m_ttableClassRef[i] = ProDataGraph.m_ecoreFactory.createEReference();
            if (tableMetaData != null) {
                ((ENamedElement)this.m_ttableClassRef[i]).setName(tableMetaData.getTableName());
            }
            ((ETypedElement)this.m_ttableClassRef[i]).setEType((EClassifier)this.m_ttClass[i]);
            ((ETypedElement)this.m_ttableClassRef[i]).setUpperBound(-1);
            this.m_ttableClassRef[i].setContainment(true);
            if (tableMetaData != null) {
                tableMetaData.addFieldSchemaInfoToClass(this.m_ttClass[i], this.m_dgmd);
            }
            ((List<EReference>)this.m_rootClass.getEStructuralFeatures()).add(this.m_ttableClassRef[i]);
            ((List<EClass>)this.m_myPackage.getEClassifiers()).add(this.m_ttClass[i]);
        }
        for (int numRelations = this.m_dgmd.getNumRelations(), j = 0; j < numRelations; ++j) {
            this.m_dgmd.getRelationMetaData(j).addSchemaInfoToClass(this.m_ttClass, this.m_dgmd);
        }
    }
    
    private void createRootDataObject() throws Exception {
        try {
            this.m_rootDObject = (EDataObject)EcoreUtil.create(this.m_rootClass);
        }
        catch (Exception obj) {
            System.out.println("Exception caught in initRootDataObject() : " + obj);
            obj.printStackTrace();
        }
    }
    
    public DataObject getRootObject() {
        return (DataObject)this.m_rootDObject;
    }
    
    public ProDataObject createProDataObject(final String str) throws Open4GLException {
        if (this.m_dgmd == null) {
            throw new Open4GLException("Invalid table name: " + str + ". Does not exist in this ProDataGraph");
        }
        final int tableIndex = this.getTableIndex(str);
        final ProDataObjectMetaData tableMetaData = this.m_dgmd.getTableMetaData(tableIndex);
        final ProDataObject proDataObject = (ProDataObject)EcoreUtil.create(this.m_ttClass[tableIndex]);
        proDataObject.setMetaData(tableMetaData);
        return proDataObject;
    }
    
    public void addProDataObject(final ProDataObject proDataObject) throws Open4GLException {
        ((List)((EObject)this.m_rootDObject).eGet((EStructuralFeature)this.m_ttableClassRef[this.getTableIndex(proDataObject.getTableName())])).add(proDataObject);
    }
    
    public void addProDataObject(final int n, final ProDataObject proDataObject) throws Open4GLException {
        ((List)((EObject)this.m_rootDObject).eGet((EStructuralFeature)this.m_ttableClassRef[this.getTableIndex(proDataObject.getTableName())])).add(n, proDataObject);
    }
    
    public List getProDataObjects(final int n) throws Exception {
        return (List)((EObject)this.m_rootDObject).eGet((EStructuralFeature)this.m_ttableClassRef[n]);
    }
    
    public List getProDataObjects(final String s) throws Exception, Open4GLException {
        return this.getProDataObjects(this.getTableIndex(s));
    }
    
    public int getTableIndex(final String str) throws Open4GLException {
        if (this.m_dgmd == null) {
            throw new Open4GLException("Invalid table name: " + str + ". Does not exist in this ProDataGraph");
        }
        return this.m_dgmd.getTableIndex(str);
    }
    
    public String[] getTableNames() throws Open4GLException {
        if (this.m_dgmd == null) {
            return null;
        }
        return this.m_dgmd.getTableNames();
    }
    
    public void setChildTableReferences() throws Exception {
        if (this.m_dgmd == null) {
            return;
        }
        for (int i = 0; i < this.m_dgmd.getNumRelations(); ++i) {
            final ProDataRelationMetaData relationMetaData = this.m_dgmd.getRelationMetaData(i);
            relationMetaData.fillChildRelations(this.getProDataObjects(relationMetaData.getParentIx()), this.getProDataObjects(relationMetaData.getChildIx()));
        }
    }
    
    public void setChildTableReferences(final String s) throws Open4GLException, Exception {
        if (this.m_dgmd == null) {
            return;
        }
        this.setChildTableReferences(this.getTableIndex(s));
    }
    
    public void setChildTableReferences(final int n) throws Open4GLException, Exception {
        if (this.m_dgmd == null) {
            return;
        }
        for (int i = 0; i < this.m_dgmd.getNumRelations(); ++i) {
            final ProDataRelationMetaData relationMetaData = this.m_dgmd.getRelationMetaData(i);
            if (n == relationMetaData.getParentIx()) {
                relationMetaData.fillChildRelations(this.getProDataObjects(n), this.getProDataObjects(relationMetaData.getChildIx()));
            }
        }
    }
    
    private void populateProDataGraph(final DataGraph dataGraph) throws Open4GLException {
        final DataObject dataObject = (DataObject)((EDataGraphImpl)dataGraph).getERootObject();
        final List properties = dataObject.getType().getProperties();
        final ProDataGraphMetaData metaData = this.getMetaData();
        final String[] tableNames = this.getTableNames();
        int n = 0;
        for (final Property property : properties) {
            final List properties2 = property.getType().getProperties();
            final ProDataObjectMetaData tableMetaData = metaData.getTableMetaData(n);
            for (final DataObject dataObject2 : dataObject.getList(property)) {
                final ProDataObject proDataObject = this.createProDataObject(tableNames[n]);
                final int fieldCount = tableMetaData.getFieldCount();
                final Iterator<Property> iterator3 = properties2.iterator();
                for (int i = 0; i < fieldCount; ++i) {
                    Property property2 = null;
                    for (Class instanceClass = null; instanceClass == null; instanceClass = property2.getType().getInstanceClass()) {
                        property2 = iterator3.next();
                    }
                    Object value = dataObject2.get(property2);
                    if (value instanceof Date) {
                        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
                        gregorianCalendar.setTime((Date)value);
                        value = gregorianCalendar;
                    }
                    proDataObject.set(i, value);
                }
                this.addProDataObject(proDataObject);
            }
            ++n;
        }
    }
    
    static {
        ProDataGraph.m_ecoreFactory = EcoreFactory.eINSTANCE;
    }
}
