// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EReference;
import java.util.GregorianCalendar;
import org.eclipse.emf.ecore.EStructuralFeature;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.sdo.impl.DynamicEDataObjectImpl;

public class ProDataObject extends DynamicEDataObjectImpl
{
    private ProDataObjectMetaData m_domd;
    private String m_rowErrorString;
    private boolean m_hasError;
    private boolean m_loadedChildren;
    
    public ProDataObject(final EClass eClass) {
        super(eClass);
        this.m_domd = null;
        this.m_rowErrorString = null;
        this.m_hasError = false;
        this.m_loadedChildren = false;
    }
    
    public void delete() {
        final ProDataGraphMetaData metaData = ((ProDataGraph)this.getDataGraph()).getMetaData();
        final String tableName = this.getTableName();
        try {
            final String[] tableNames = metaData.getTableNames();
            for (int i = 0; i < metaData.getNumRelations(); ++i) {
                final ProDataRelationMetaData relationMetaData = metaData.getRelationMetaData(i);
                if (tableName.compareToIgnoreCase(tableNames[relationMetaData.getChildIx()]) == 0) {
                    final ProDataObject parentRow = this.getParentRow(relationMetaData.getRelationName());
                    if (parentRow != null && ((BasicEObjectImpl)parentRow).eGet((EStructuralFeature)relationMetaData.getChildRef()) != null) {
                        ((List)((BasicEObjectImpl)parentRow).eGet((EStructuralFeature)relationMetaData.getChildRef())).remove(this);
                    }
                }
                if (tableName.compareToIgnoreCase(tableNames[relationMetaData.getParentIx()]) == 0) {
                    final List childRows = this.getChildRows(relationMetaData.getRelationName());
                    int size = 0;
                    if (childRows != null) {
                        size = childRows.size();
                    }
                    for (int j = 0; j < size; ++j) {
                        final ProDataObject proDataObject = childRows.get(j);
                        if (((BasicEObjectImpl)proDataObject).eGet((EStructuralFeature)relationMetaData.getParentRef()) != null) {
                            ((List)((BasicEObjectImpl)proDataObject).eGet((EStructuralFeature)relationMetaData.getParentRef())).remove(this);
                        }
                    }
                }
            }
        }
        catch (Open4GLException ex) {}
        super.delete();
    }
    
    public int getFieldCount() {
        if (this.m_domd == null) {
            return 0;
        }
        return this.m_domd.getFieldCount();
    }
    
    public String getTableName() {
        if (this.m_domd == null) {
            return null;
        }
        return this.m_domd.getTableName();
    }
    
    public GregorianCalendar getGregorianCalendar(final int value) throws ProDataException {
        final Object value2 = this.get(value);
        if (value2 == null) {
            return null;
        }
        if (!(value2 instanceof GregorianCalendar)) {
            throw new ProDataException(7665970990714723337L, new Object[] { "Property Index " + new Integer(value).toString(), "GregorianCalendar" });
        }
        return (GregorianCalendar)value2;
    }
    
    public GregorianCalendar getGregorianCalendar(final String str) throws ProDataException {
        final Object value = this.get(str);
        if (value == null) {
            return null;
        }
        if (!(value instanceof GregorianCalendar)) {
            throw new ProDataException(7665970990714723337L, new Object[] { "Property Name " + str, "GregorianCalendar" });
        }
        return (GregorianCalendar)value;
    }
    
    public void setGregorianCalendar(final int n, final GregorianCalendar gregorianCalendar) throws ProDataException {
        final int proType = this.m_domd.getProType(n);
        if (proType != 2 && proType != 34 && proType != 40) {
            throw new ProDataException(7665970990714728772L, new Object[] { this.m_domd.getFieldName(n), "GregorianCalendar" });
        }
        this.set(n, (Object)gregorianCalendar);
    }
    
    public void setGregorianCalendar(final String s, final GregorianCalendar gregorianCalendar) throws ProDataException {
        final int proType = this.m_domd.getProType(this.m_domd.getFieldIndex(s));
        if (proType != 2 && proType != 34 && proType != 40) {
            throw new ProDataException(7665970990714728772L, new Object[] { s, "GregorianCalendar" });
        }
        this.set(s, (Object)gregorianCalendar);
    }
    
    public ProDataObjectMetaData getMetaData() {
        return this.m_domd;
    }
    
    protected void setMetaData(final ProDataObjectMetaData domd) {
        this.m_domd = domd;
    }
    
    public List getChildRows(final String s) throws Open4GLException {
        final ProDataGraph proDataGraph = (ProDataGraph)this.getDataGraph();
        final ProDataGraphMetaData metaData = proDataGraph.getMetaData();
        final ProDataRelationMetaData relationMetaData = metaData.getRelationMetaData(s);
        if (relationMetaData == null) {
            throw new Open4GLException(7665970990714728739L, new Object[] { "getChildRows()" });
        }
        try {
            if (RunTimeProperties.getPostponeRelationInfo() && !this.m_loadedChildren) {
                relationMetaData.fillChildRelation(this, proDataGraph.getProDataObjects(relationMetaData.getChildIx()));
                this.m_loadedChildren = true;
            }
        }
        catch (Exception ex) {
            throw new Open4GLException(ex.getMessage());
        }
        if (this.getTableName().compareToIgnoreCase(metaData.getTableNames()[relationMetaData.getParentIx()]) != 0) {
            throw new Open4GLException(7665970990714728740L, new Object[] { "getChildRows()", "parent" });
        }
        final List list = this.getList(relationMetaData.getChildRefColIdx());
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }
    
    public ProDataObject getParentRow(final String s) throws Open4GLException {
        final ProDataGraphMetaData metaData = ((ProDataGraph)this.getDataGraph()).getMetaData();
        final ProDataRelationMetaData relationMetaData = metaData.getRelationMetaData(s);
        if (relationMetaData == null) {
            throw new Open4GLException(7665970990714728739L, new Object[] { "getParentRow()" });
        }
        if (this.getTableName().compareToIgnoreCase(metaData.getTableNames()[relationMetaData.getChildIx()]) != 0) {
            throw new Open4GLException(7665970990714728740L, new Object[] { "getParentRow()", "child" });
        }
        final List list = this.getList(relationMetaData.getParentRefColIdx());
        ProDataObject proDataObject;
        if (list == null || list.size() == 0) {
            proDataObject = null;
        }
        else {
            proDataObject = list.get(0);
        }
        return proDataObject;
    }
    
    public String getRowErrorString() {
        return this.m_rowErrorString;
    }
    
    public void setRowErrorString(final String rowErrorString) {
        this.m_rowErrorString = rowErrorString;
    }
    
    public boolean hasRowError() {
        return this.m_hasError;
    }
    
    public void setHasRowError(final boolean hasError) {
        this.m_hasError = hasError;
    }
    
    public String getTableErrorString() {
        if (this.m_domd == null) {
            return null;
        }
        return this.m_domd.getTableErrorString();
    }
    
    public boolean hasTableError() {
        return this.m_domd != null && this.m_domd.hasTableError();
    }
    
    protected static void addFieldSchemaInfo(final EReference eReference, final EClass eClass, final EClass[] array, final ProDataGraphMetaData proDataGraphMetaData, final ProDataObjectMetaData proDataObjectMetaData) throws ProDataException {
        final EcoreFactory einstance = EcoreFactory.eINSTANCE;
        final EcorePackage einstance2 = EcorePackage.eINSTANCE;
        for (int i = 1; i <= proDataObjectMetaData.getFieldCount(); ++i) {
            final int proType = proDataObjectMetaData.getProType(i);
            final EAttribute eAttribute = einstance.createEAttribute();
            ((ENamedElement)eAttribute).setName(proDataObjectMetaData.getFieldName(i));
            switch (proType) {
                case 1:
                case 19: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEString());
                    break;
                }
                case 4: {
                    if (RunTimeProperties.getDatasetNullInitials()) {
                        ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEIntegerObject());
                        break;
                    }
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEInt());
                    break;
                }
                case 7:
                case 41: {
                    if (RunTimeProperties.getDatasetNullInitials()) {
                        ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getELongObject());
                        break;
                    }
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getELong());
                    break;
                }
                case 3: {
                    if (RunTimeProperties.getDatasetNullInitials()) {
                        ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEBooleanObject());
                        break;
                    }
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEBoolean());
                    break;
                }
                case 2:
                case 34:
                case 40: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEDate());
                    break;
                }
                case 5: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEBigDecimal());
                    break;
                }
                case 18: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEByteArray());
                    break;
                }
                case 10:
                case 14: {
                    if (RunTimeProperties.getDatasetNullInitials()) {
                        ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getELongObject());
                        break;
                    }
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getELong());
                    break;
                }
                case 8:
                case 13: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEByteArray());
                    break;
                }
            }
            final int fieldExtent = proDataObjectMetaData.getFieldExtent(i);
            if (fieldExtent > 1) {
                ((ETypedElement)eAttribute).setLowerBound(fieldExtent);
                ((ETypedElement)eAttribute).setUpperBound(fieldExtent);
            }
            ((List<ETypedElement>)eClass.getEStructuralFeatures()).add((ETypedElement)eAttribute);
        }
    }
    
    protected static class FactoryImpl extends EFactoryImpl
    {
        public FactoryImpl() {
        }
        
        public EObject basicCreate(final EClass eClass) {
            return (EObject)new ProDataObject(eClass);
        }
    }
}
