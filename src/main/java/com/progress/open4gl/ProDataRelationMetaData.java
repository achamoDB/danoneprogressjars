// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import org.eclipse.emf.ecore.ENamedElement;
import java.util.List;
import com.progress.open4gl.dynamicapi.O4GLProDataObjectList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EClass;
import com.progress.open4gl.dynamicapi.DataRelationMetaData;

public class ProDataRelationMetaData extends DataRelationMetaData
{
    public ProDataRelationMetaData() {
    }
    
    public ProDataRelationMetaData(final String s, final ProDataObjectMetaData proDataObjectMetaData, final ProDataObjectMetaData proDataObjectMetaData2) {
        super(s, proDataObjectMetaData, proDataObjectMetaData2);
    }
    
    public ProDataRelationMetaData(final String s, final int n, final int n2, final int n3, final String s2) {
        super(s, n, n2, n3, s2);
    }
    
    public ProDataRelationMetaData(final String s, final int n, final int n2, final int n3, final String s2, final int n4, final String s3) {
        super(s, n, n2, n3, s2, n4, s3);
    }
    
    public ProDataRelationMetaData(final ProDataRelationMetaData proDataRelationMetaData) {
        super(proDataRelationMetaData);
    }
    
    protected void addSchemaInfoToClass(final EClass[] array, final ProDataGraphMetaData proDataGraphMetaData) {
        final EcoreFactory einstance = EcoreFactory.eINSTANCE;
        final EClass eClass = array[super.m_ParentIx];
        final EClass eClass2 = array[super.m_ChildIx];
        final ProDataObjectMetaData tableMetaData = proDataGraphMetaData.getTableMetaData(super.m_ParentIx);
        final ProDataObjectMetaData tableMetaData2 = proDataGraphMetaData.getTableMetaData(super.m_ChildIx);
        final EReference eReference = einstance.createEReference();
        ((ENamedElement)eReference).setName(tableMetaData2.getTableName() + "Child");
        ((ETypedElement)eReference).setEType((EClassifier)array[super.m_ChildIx]);
        ((ETypedElement)eReference).setUpperBound(-1);
        ((List<ETypedElement>)eClass.getEStructuralFeatures()).add((ETypedElement)eReference);
        final EList eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
        final EReference eReference2 = einstance.createEReference();
        ((ENamedElement)eReference2).setName(tableMetaData.getTableName() + "Parent");
        ((ETypedElement)eReference2).setEType((EClassifier)array[super.m_ParentIx]);
        ((ETypedElement)eReference2).setUpperBound(-1);
        ((List<ETypedElement>)eClass2.getEStructuralFeatures()).add((ETypedElement)eReference2);
        final EList eAllStructuralFeatures2 = eClass2.getEAllStructuralFeatures();
        super.m_ParentRef = eReference2;
        super.m_ChildRef = eReference;
        super.m_ParentRefColIdx = ((List)eAllStructuralFeatures2).size() - 1;
        super.m_ChildRefColIdx = ((List)eAllStructuralFeatures).size() - 1;
    }
    
    public void setColumns(final int n, final int n2) throws ProDataException {
        if (super.m_ParentMetaData == null || super.m_ChildMetaData == null) {
            throw O4GLProDataObjectList.getProDataException(7665970990714728737L, new Object[] { "setColumns()" });
        }
        super.m_NumPairs = 1;
        super.m_PairsArray = new String[2];
        super.m_PairsIdxArray = new int[2];
        super.m_PairsArray[0] = super.m_ParentMetaData.getFieldName(n);
        super.m_PairsArray[1] = super.m_ChildMetaData.getFieldName(n2);
        super.m_PairsIdxArray[0] = n;
        super.m_PairsIdxArray[1] = n2;
        super.m_PairsList = super.m_PairsArray[0] + "," + super.m_PairsArray[1];
    }
    
    public void setColumns(final int[] array, final int[] array2) throws ProDataException {
        if (super.m_ParentMetaData == null || super.m_ChildMetaData == null) {
            throw O4GLProDataObjectList.getProDataException(7665970990714728737L, new Object[] { "setColumns()" });
        }
        if (array == null || array2 == null) {
            return;
        }
        final int length = array.length;
        if (length != array2.length) {
            throw O4GLProDataObjectList.getProDataException(7665970990714728738L);
        }
        super.m_NumPairs = length;
        final int n = length * 2;
        super.m_PairsArray = new String[n];
        super.m_PairsIdxArray = new int[n];
        super.m_PairsList = "";
        for (int i = 0, n2 = 0; i < n; i += 2, ++n2) {
            super.m_PairsArray[i] = super.m_ParentMetaData.getFieldName(array[n2]);
            super.m_PairsArray[i + 1] = super.m_ChildMetaData.getFieldName(array2[n2]);
            super.m_PairsIdxArray[i] = array[n2];
            super.m_PairsIdxArray[i + 1] = array2[n2];
            super.m_PairsList = super.m_PairsList + ((n2 == 0) ? "" : ",") + super.m_PairsArray[i] + "," + super.m_PairsArray[i + 1];
        }
    }
    
    public int[] getParentColumns() throws ProDataException {
        if (super.m_NumPairs == 0) {
            return null;
        }
        if (super.m_ParentMetaData == null || super.m_ChildMetaData == null) {
            throw O4GLProDataObjectList.getProDataException(7665970990714728737L, new Object[] { "getParentColumns()" });
        }
        final int[] array = new int[super.m_NumPairs];
        for (int n = super.m_NumPairs * 2, i = 0, n2 = 0; i < n; ++i, ++i, ++n2) {
            array[n2] = super.m_PairsIdxArray[i];
        }
        return array;
    }
    
    public int[] getChildColumns() throws ProDataException {
        if (super.m_NumPairs == 0) {
            return null;
        }
        if (super.m_ParentMetaData == null || super.m_ChildMetaData == null) {
            throw O4GLProDataObjectList.getProDataException(7665970990714728737L, new Object[] { "getChildColumns()" });
        }
        final int[] array = new int[super.m_NumPairs];
        for (int n = super.m_NumPairs * 2, i = 1, n2 = 0; i < n; ++i, ++i, ++n2) {
            array[n2] = super.m_PairsIdxArray[i];
        }
        return array;
    }
    
    protected int getNumPairs() {
        return super.m_NumPairs;
    }
    
    protected void setNumPairs(final int numPairs) {
        super.m_NumPairs = numPairs;
    }
    
    public String getRelationName() {
        return super.m_LinkName;
    }
    
    protected void setRelationName(final String linkName) {
        super.m_LinkName = linkName;
    }
    
    protected String getExtendedProperties() {
        return super.m_ExtendedProperties;
    }
    
    protected void setExtendedProperties(final String extendedProperties) {
        super.m_ExtendedProperties = extendedProperties;
    }
    
    protected int getParentRefColIdx() {
        return super.m_ParentRefColIdx;
    }
    
    protected int getChildIx() {
        return super.m_ChildIx;
    }
    
    protected void setChildIx(final int childIx) {
        super.m_ChildIx = childIx;
    }
    
    protected int getParentIx() {
        return super.m_ParentIx;
    }
    
    protected void setParentIx(final int parentIx) {
        super.m_ParentIx = parentIx;
    }
    
    protected int getChildRefColIdx() {
        return super.m_ChildRefColIdx;
    }
    
    protected void fillChildRelations(final List list, final List list2) {
        super.fillChildRelations(list, list2);
    }
}
