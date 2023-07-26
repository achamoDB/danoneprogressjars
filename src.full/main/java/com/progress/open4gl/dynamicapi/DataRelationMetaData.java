// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.EStructuralFeature;
import com.progress.open4gl.ProDataObject;
import java.util.List;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.ProDataGraphMetaData;
import com.progress.open4gl.ProDataRelationMetaData;
import org.eclipse.emf.ecore.EReference;
import com.progress.open4gl.ProDataObjectMetaData;

public class DataRelationMetaData
{
    protected int m_ChildIx;
    protected int m_ParentIx;
    protected int m_Flag;
    protected int m_NumPairs;
    protected String m_PairsList;
    protected String[] m_PairsArray;
    protected int[] m_PairsIdxArray;
    protected String m_LinkName;
    protected String m_ExtendedProperties;
    protected ProDataObjectMetaData m_ParentMetaData;
    protected ProDataObjectMetaData m_ChildMetaData;
    protected EReference m_ParentRef;
    protected EReference m_ChildRef;
    protected int m_ParentRefColIdx;
    protected int m_ChildRefColIdx;
    
    public DataRelationMetaData() {
        this.m_ChildIx = 0;
        this.m_ParentIx = 0;
        this.m_Flag = 0;
        this.m_NumPairs = 0;
        this.setPairsList(null);
        this.m_LinkName = null;
        this.m_ExtendedProperties = null;
        this.m_ParentMetaData = null;
        this.m_ChildMetaData = null;
        this.m_ParentRef = null;
        this.m_ChildRef = null;
        this.m_ParentRefColIdx = -1;
        this.m_ChildRefColIdx = -1;
    }
    
    public DataRelationMetaData(final String linkName, final ProDataObjectMetaData parentMetaData, final ProDataObjectMetaData childMetaData) {
        this.m_ChildIx = 0;
        this.m_ParentIx = 0;
        this.m_Flag = 0;
        this.m_NumPairs = 0;
        this.setPairsList(null);
        this.m_LinkName = linkName;
        this.m_ExtendedProperties = null;
        this.m_ParentMetaData = parentMetaData;
        this.m_ChildMetaData = childMetaData;
        this.m_ParentRef = null;
        this.m_ChildRef = null;
        this.m_ParentRefColIdx = -1;
        this.m_ChildRefColIdx = -1;
    }
    
    public DataRelationMetaData(final String s, final int n, final int n2, final int n3, final String s2) {
        this(s, n, n2, n3, s2, 0, null);
    }
    
    public DataRelationMetaData(final String linkName, final int parentIx, final int childIx, final int numPairs, final String pairsList, final int flag, final String extendedProperties) {
        this.m_ChildIx = childIx;
        this.m_ParentIx = parentIx;
        this.m_Flag = flag;
        this.m_NumPairs = numPairs;
        this.setPairsList(pairsList);
        this.m_LinkName = linkName;
        this.m_ExtendedProperties = extendedProperties;
        this.m_ParentMetaData = null;
        this.m_ChildMetaData = null;
        this.m_ParentRef = null;
        this.m_ChildRef = null;
        this.m_ParentRefColIdx = -1;
        this.m_ChildRefColIdx = -1;
    }
    
    public DataRelationMetaData(final ProDataRelationMetaData proDataRelationMetaData) {
        this.m_ChildIx = proDataRelationMetaData.m_ChildIx;
        this.m_ParentIx = proDataRelationMetaData.m_ParentIx;
        this.m_Flag = proDataRelationMetaData.m_Flag;
        this.m_NumPairs = proDataRelationMetaData.m_NumPairs;
        this.setPairsList(proDataRelationMetaData.m_PairsList);
        this.m_LinkName = proDataRelationMetaData.m_LinkName;
        this.m_ExtendedProperties = proDataRelationMetaData.m_ExtendedProperties;
        this.m_ParentMetaData = proDataRelationMetaData.m_ParentMetaData;
        this.m_ChildMetaData = proDataRelationMetaData.m_ChildMetaData;
        this.m_ParentRef = proDataRelationMetaData.m_ParentRef;
        this.m_ChildRef = proDataRelationMetaData.m_ChildRef;
        this.m_ParentRefColIdx = proDataRelationMetaData.m_ParentRefColIdx;
        this.m_ChildRefColIdx = proDataRelationMetaData.m_ChildRefColIdx;
    }
    
    public String getParentTable() {
        String tableName = null;
        if (this.m_ParentMetaData != null) {
            tableName = this.m_ParentMetaData.getTableName();
        }
        return tableName;
    }
    
    public String getChildTable() {
        String tableName = null;
        if (this.m_ChildMetaData != null) {
            tableName = this.m_ChildMetaData.getTableName();
        }
        return tableName;
    }
    
    protected String getPair(final int n) {
        return this.m_PairsArray[n * 2 - 2] + "," + this.m_PairsArray[n * 2 - 1];
    }
    
    protected String getPair(final int n, final int n2) {
        String pair = null;
        if (n2 == 0) {
            pair = this.getPair(n);
        }
        else if (n2 == 1) {
            pair = this.m_PairsArray[n * 2 - 2];
        }
        else if (n2 == 2) {
            pair = this.m_PairsArray[n * 2 - 1];
        }
        return pair;
    }
    
    protected int getPairIdx(final int n, final int n2) {
        int n3 = -1;
        if (n2 == 1) {
            n3 = this.m_PairsIdxArray[n * 2 - 2];
        }
        else if (n2 == 2) {
            n3 = this.m_PairsIdxArray[n * 2 - 1];
        }
        return n3;
    }
    
    protected int getFlag() {
        return this.m_Flag;
    }
    
    protected void setFlag(final int flag) {
        this.m_Flag = flag;
    }
    
    protected String getPairsList() {
        return this.m_PairsList;
    }
    
    protected void setPairsList(String substring) {
        this.m_PairsArray = null;
        this.m_PairsList = substring;
        if (substring == null) {
            return;
        }
        final int length = substring.length();
        int i = 0;
        int n = 1;
        while (i < length) {
            if (substring.charAt(i) == ',') {
                ++n;
            }
            ++i;
        }
        this.m_PairsArray = new String[n];
        this.m_NumPairs = n / 2;
        for (int j = 0; j < n; ++j) {
            final int index = substring.indexOf(",");
            if (index != -1) {
                this.m_PairsArray[j] = substring.substring(0, index);
                substring = substring.substring(index + 1);
            }
            else {
                this.m_PairsArray[j] = substring;
            }
        }
    }
    
    public EReference getParentRef() {
        return this.m_ParentRef;
    }
    
    public EReference getChildRef() {
        return this.m_ChildRef;
    }
    
    protected void setPairsIndexInfo(final ProDataGraphMetaData proDataGraphMetaData) throws ProDataException {
        if (this.m_ParentMetaData == null) {
            this.m_ParentMetaData = proDataGraphMetaData.getTableMetaData(this.m_ParentIx);
            this.m_ChildMetaData = proDataGraphMetaData.getTableMetaData(this.m_ChildIx);
        }
        if (this.m_ParentMetaData != null && this.m_PairsIdxArray == null) {
            this.m_PairsIdxArray = new int[this.m_NumPairs * 2];
            int i = 1;
            int n = 0;
            while (i <= this.m_NumPairs) {
                final int fieldIndex = this.m_ParentMetaData.getFieldIndex(this.m_PairsArray[n]);
                if (fieldIndex == -1) {
                    throw O4GLProDataObjectList.getProDataException(7665970990714728767L, new Object[] { this.m_PairsArray[n], this.m_LinkName });
                }
                this.m_PairsIdxArray[n++] = fieldIndex;
                final int fieldIndex2 = this.m_ChildMetaData.getFieldIndex(this.m_PairsArray[n]);
                if (fieldIndex2 == -1) {
                    throw O4GLProDataObjectList.getProDataException(7665970990714728767L, new Object[] { this.m_PairsArray[n], this.m_LinkName });
                }
                this.m_PairsIdxArray[n++] = fieldIndex2;
                ++i;
            }
        }
    }
    
    protected void setTableIxs(final ProDataGraphMetaData proDataGraphMetaData) throws Open4GLError {
        String str = null;
        final Object[] array = { null };
        if (this.m_ParentMetaData == null) {
            this.m_ParentMetaData = proDataGraphMetaData.getTableMetaData(this.m_ParentIx);
            this.m_ChildMetaData = proDataGraphMetaData.getTableMetaData(this.m_ChildIx);
            return;
        }
        try {
            str = this.getParentTable();
            this.m_ParentIx = proDataGraphMetaData.getTableIndex(str);
            str = this.getChildTable();
            this.m_ChildIx = proDataGraphMetaData.getTableIndex(str);
        }
        catch (Open4GLException ex) {
            array[0] = "Invalid table specified for ProDataRelationMetaData: " + str;
            throw new Open4GLError(7665970990714723371L, array);
        }
    }
    
    protected void fillChildRelations(final List list, final List list2) {
        for (int size = list.size(), i = 0; i < size; ++i) {
            this.fillChildRelation(list.get(i), list2);
        }
    }
    
    public void fillChildRelation(final ProDataObject proDataObject, final List list) {
        final int size = list.size();
        ((List)((BasicEObjectImpl)proDataObject).eGet((EStructuralFeature)this.m_ChildRef)).clear();
        for (int i = 0; i < size; ++i) {
            final ProDataObject proDataObject2 = list.get(i);
            int n = 1;
            for (int j = 1; j <= this.m_NumPairs; ++j) {
                final int pairIdx = this.getPairIdx(j, 1);
                final int pairIdx2 = this.getPairIdx(j, 2);
                final Object value = proDataObject.get(pairIdx);
                final Object value2 = proDataObject2.get(pairIdx2);
                if (value != null || value2 != null) {
                    if (value == null || value2 == null || !value.equals(value2)) {
                        n = 0;
                        break;
                    }
                }
            }
            if (n == 1) {
                ((List)((BasicEObjectImpl)proDataObject).eGet((EStructuralFeature)this.m_ChildRef)).add(proDataObject2);
                ((List)((BasicEObjectImpl)proDataObject2).eGet((EStructuralFeature)this.m_ParentRef)).add(proDataObject);
            }
        }
    }
    
    protected boolean validate(final ProDataGraphMetaData proDataGraphMetaData) throws Open4GLError {
        final Object[] array = { null };
        if (proDataGraphMetaData == null) {
            return false;
        }
        if (this.m_ChildIx >= proDataGraphMetaData.getNumTables()) {
            array[0] = "Invalid Child table specified in ProDataRelationMetaData " + this.m_LinkName;
            throw new Open4GLError(7665970990714723371L, array);
        }
        if (this.m_ParentIx >= proDataGraphMetaData.getNumTables()) {
            array[0] = "Invalid Parent table specified in ProDataRelationMetaData " + this.m_LinkName;
            throw new Open4GLError(7665970990714723371L, array);
        }
        return true;
    }
}
