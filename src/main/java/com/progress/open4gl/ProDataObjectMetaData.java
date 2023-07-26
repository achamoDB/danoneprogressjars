// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.List;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EClass;
import com.progress.open4gl.dynamicapi.DataObjectMetaData;

public class ProDataObjectMetaData extends DataObjectMetaData
{
    public ProDataObjectMetaData(final String s, final int n, final boolean b, final int n2, final String s2, final String s3, final String s4) {
        super(s, n, b, n2, s2, s3, s4);
    }
    
    public void setFieldMetaData(final int value, final String s, final int n, final int n2, final int n3, final int n4) throws ProDataException {
        if (value < 1 || value > super.m_numFields) {
            throw new ProDataException(new Open4GLException(7665970990714723343L, new Object[] { new Integer(value) }).getMessage());
        }
        if (s == null) {
            throw new ProDataException(new Open4GLException(7665970990714725434L, null).getMessage());
        }
        if (n < 0) {
            throw new ProDataException(new Open4GLException(7665970990714725435L, null).getMessage());
        }
        if (n2 < 1 || n2 > 41) {
            throw new ProDataException(new Open4GLException(7665970990714725436L, null).getMessage());
        }
        try {
            super.m_rsmd.setFieldDesc(value, s, n, n2, n3, n4);
        }
        catch (Open4GLError open4GLError) {
            if (open4GLError.getMessageId() == 7221L) {
                throw new ProDataException(new Open4GLException(7665970990714725437L, null).getMessage());
            }
        }
    }
    
    public String getTableName() {
        return super.m_bufferName;
    }
    
    public boolean getBImageFlag() {
        return (super.m_flag & 0x2) != 0x0;
    }
    
    public int getFieldCount() {
        return super.m_rsmd.getFieldCount();
    }
    
    public boolean getNoSchemaMarshal() {
        return super.m_noSchemaMarshal;
    }
    
    public void setNoSchemaMarshal(final boolean noSchemaMarshal) {
        super.m_noSchemaMarshal = noSchemaMarshal;
    }
    
    public int getUserOrder(final int n) throws ProDataException {
        return super.getUserOrder(n);
    }
    
    public int getXMLMapping(final int n) throws ProDataException {
        return super.getXMLMapping(n);
    }
    
    public int getProType(final int n) throws ProDataException {
        return this.getProFieldType(n);
    }
    
    public int getExtent(final int n) throws ProDataException {
        return this.getFieldExtent(n);
    }
    
    public String getFieldName(final int n) throws ProDataException {
        return super.getFieldName(n);
    }
    
    protected int getFieldIndex(final String s) throws ProDataException {
        return super.getFieldIndex(s);
    }
    
    protected int getFieldExtent(final int n) throws ProDataException {
        return super.getFieldExtent(n);
    }
    
    protected boolean hasTableError() {
        return (super.m_flag & 0x4) != 0x0;
    }
    
    protected String getTableErrorString() {
        return super.m_tableErrorString;
    }
    
    protected void setTableErrorString(final String tableErrorString) {
        super.m_tableErrorString = tableErrorString;
    }
    
    protected void addFieldSchemaInfoToClass(final EClass eClass, final ProDataGraphMetaData proDataGraphMetaData) throws ProDataException {
        final EcoreFactory einstance = EcoreFactory.eINSTANCE;
        final EcorePackage einstance2 = EcorePackage.eINSTANCE;
        for (int i = 0; i < this.getFieldCount(); ++i) {
            final int proFieldType = this.getProFieldType(i);
            final EAttribute eAttribute = einstance.createEAttribute();
            ((ENamedElement)eAttribute).setName(this.getFieldName(i));
            switch (proFieldType) {
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
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEJavaObject());
                    break;
                }
                case 5: {
                    ((ETypedElement)eAttribute).setEType((EClassifier)einstance2.getEBigDecimal());
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
            final int fieldExtent = this.getFieldExtent(i);
            if (fieldExtent > 1) {
                ((ETypedElement)eAttribute).setLowerBound(fieldExtent);
                ((ETypedElement)eAttribute).setUpperBound(fieldExtent);
                ((ETypedElement)eAttribute).setUnique(false);
            }
            ((List<ETypedElement>)eClass.getEStructuralFeatures()).add((ETypedElement)eAttribute);
        }
    }
}
