// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Date;
import java.math.BigDecimal;
import com.progress.open4gl.SchemaValidationException;
import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.Open4GLError;
import commonj.sdo.Type;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.ProDataException;
import java.util.Iterator;
import java.util.List;
import com.progress.open4gl.Open4GLException;
import commonj.sdo.Property;
import org.eclipse.emf.ecore.sdo.impl.EDataGraphImpl;
import commonj.sdo.DataObject;
import commonj.sdo.DataGraph;
import com.progress.open4gl.ProDataGraphMetaData;
import com.progress.open4gl.ProDataRelationMetaData;

public class DataGraphMetaData extends MetaDataBase
{
    protected int m_TableCount;
    protected String m_CodePage;
    protected String m_ProDataGraphName;
    protected int m_ParmNum;
    protected int m_InOut;
    protected int m_Flag;
    protected String m_XMLNamespace;
    protected String m_XMLPrefix;
    protected String m_ExtendedProperties;
    protected int m_uniqueId;
    protected ProDataRelationMetaData[] m_drmd;
    protected ProDataObjectMetaDataIndicator[] m_domdi;
    protected String[] m_tableNames;
    
    public DataGraphMetaData(final int n) {
        super(1);
        this.init(n, "anonymous", 0, 0);
    }
    
    public DataGraphMetaData(final int n, final String s, final int n2, final int n3) {
        super(1);
        this.init(n, s, n2, n3);
    }
    
    public DataGraphMetaData(final String s) {
        super(1);
        this.init(0, s, 0, 0);
    }
    
    public DataGraphMetaData(final ProDataGraphMetaData proDataGraphMetaData) {
        super(1);
        this.m_TableCount = proDataGraphMetaData.m_TableCount;
        this.m_CodePage = proDataGraphMetaData.m_CodePage;
        this.m_ProDataGraphName = proDataGraphMetaData.m_ProDataGraphName;
        if (this.m_ProDataGraphName == null || this.m_ProDataGraphName.length() == 0) {
            this.m_ProDataGraphName = "anonymous";
        }
        this.m_ParmNum = proDataGraphMetaData.m_ParmNum;
        this.m_InOut = proDataGraphMetaData.m_InOut;
        this.m_Flag = proDataGraphMetaData.m_Flag;
        this.m_XMLNamespace = proDataGraphMetaData.m_XMLNamespace;
        this.m_XMLPrefix = proDataGraphMetaData.m_XMLPrefix;
        this.m_ExtendedProperties = proDataGraphMetaData.m_ExtendedProperties;
        this.m_uniqueId = proDataGraphMetaData.m_uniqueId;
        this.m_tableNames = proDataGraphMetaData.m_tableNames;
    }
    
    public DataGraphMetaData(final DataGraph dataGraph, final String s, final ProDataRelationMetaData[] array) throws ProDataException, Open4GLException {
        super(1);
        this.init(0, s, 0, 0);
        final DataObject dataObject = (DataObject)((EDataGraphImpl)dataGraph).getERootObject();
        final List properties = dataObject.getType().getProperties();
        for (final Property property : properties) {
            if (property.getType().getInstanceClass() != null) {
                throw new Open4GLException("Error: A property in the DataGraph's root DataObject is not a reference property. The root DataObject can only contain reference properties.");
            }
            if (!property.isMany()) {
                throw new Open4GLException("Error: A property in the DataGraph's root DataObject is not many-valued. The root DataObject can only contain many-valued properties.");
            }
        }
        final Iterator<Property> iterator2 = properties.iterator();
        while (iterator2.hasNext()) {
            this.addTableSchema(iterator2.next(), dataObject);
        }
        int length = 0;
        if (array != null) {
            length = array.length;
        }
        for (int i = 0; i < length; ++i) {
            this.addDataRelation(array[i]);
        }
        for (int j = 0; j < length; ++j) {
            this.m_drmd[j].setPairsIndexInfo((ProDataGraphMetaData)this);
        }
    }
    
    private void init(final int uniqueId, final String proDataGraphName, final int parmNum, final int inOut) {
        this.m_uniqueId = uniqueId;
        this.m_TableCount = 0;
        this.m_CodePage = null;
        this.m_ProDataGraphName = proDataGraphName;
        this.m_ParmNum = parmNum;
        this.m_InOut = inOut;
        this.m_Flag = 0;
        this.m_XMLNamespace = null;
        this.m_XMLPrefix = null;
        this.m_ExtendedProperties = null;
    }
    
    public ProDataObjectMetaData getTableMetaData(final int n) {
        if (n >= this.m_TableCount) {
            return null;
        }
        return (ProDataObjectMetaData)this.m_domdi[n].getMetaData();
    }
    
    private void addTableSchema(final Property property, final DataObject dataObject) throws ProDataException {
        final List properties = property.getType().getProperties();
        int n = 0;
        final Iterator<Property> iterator = properties.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getType().getInstanceClass() != null) {
                ++n;
            }
        }
        final ProDataObjectMetaData proDataObjectMetaData = new ProDataObjectMetaData(property.getName(), n, false, 0, null, null, null);
        int n2 = 1;
        for (final Property property2 : properties) {
            final Type type = property2.getType();
            int size = 0;
            if (type.getInstanceClass() != null) {
                final int proType = getProType(type);
                if (property2.isMany()) {
                    final List list = dataObject.getList(property);
                    List list2 = null;
                    for (int n3 = 0; n3 < list.size() && list2 == null; list2 = list.get(n3).getList(property2), ++n3) {}
                    if (list2 != null) {
                        size = list2.size();
                    }
                }
                proDataObjectMetaData.setFieldMetaData(n2, property2.getName(), size, proType, n2 - 1, 0);
                ++n2;
            }
        }
        ((ProDataGraphMetaData)this).addTable(proDataObjectMetaData);
    }
    
    protected void setProDataObjectMetaDataIndicator(final ProDataObjectMetaDataIndicator[] domdi) {
        this.m_domdi = domdi;
        this.m_TableCount = domdi.length;
        this.m_tableNames = new String[this.m_TableCount];
        for (int i = 0; i < this.m_TableCount; ++i) {
            final ProDataObjectMetaData proDataObjectMetaData = (ProDataObjectMetaData)domdi[i].getMetaData();
            if (proDataObjectMetaData != null) {
                this.m_tableNames[i] = proDataObjectMetaData.getTableName();
            }
        }
    }
    
    protected void addDataRelation(final ProDataRelationMetaData proDataRelationMetaData) {
        proDataRelationMetaData.setTableIxs((ProDataGraphMetaData)this);
        proDataRelationMetaData.validate((ProDataGraphMetaData)this);
        try {
            proDataRelationMetaData.setPairsIndexInfo((ProDataGraphMetaData)this);
        }
        catch (ProDataException ex) {
            throw new Open4GLError(7665970990714723371L, new Object[] { ex.getLocalizedMessage() });
        }
        if (this.m_drmd == null) {
            (this.m_drmd = new ProDataRelationMetaData[1])[0] = proDataRelationMetaData;
        }
        else {
            final int n = this.m_drmd.length + 1;
            final ProDataRelationMetaData[] drmd = new ProDataRelationMetaData[n];
            for (int i = 0; i < n - 1; ++i) {
                drmd[i] = this.m_drmd[i];
            }
            drmd[n - 1] = proDataRelationMetaData;
            this.m_drmd = drmd;
        }
    }
    
    protected ProDataObjectMetaDataIndicator getProDataObjectMetaDataIndicator(final int n) {
        if (n >= this.m_TableCount) {
            return null;
        }
        return this.m_domdi[n];
    }
    
    protected void setProDataRelationMetaData(final ProDataRelationMetaData[] drmd) {
        this.m_drmd = drmd;
    }
    
    protected static ProDataGraphMetaData getProDataGraphMetaData(final ProDataGraph proDataGraph, final int n, final int inOut) {
        if (proDataGraph == null) {
            return null;
        }
        final ProDataGraphMetaData metaData = proDataGraph.getMetaData();
        metaData.m_InOut = inOut;
        metaData.m_TableCount = proDataGraph.getNumTables();
        return metaData;
    }
    
    protected static boolean validate(final int i, final int n, final ProDataGraphMetaData proDataGraphMetaData, final int n2, final int n3, final ProDataGraphMetaData proDataGraphMetaData2) throws SchemaValidationException, ProDataException {
        final Object[] array = { null };
        int length = 0;
        if (proDataGraphMetaData == proDataGraphMetaData2) {
            return true;
        }
        if (proDataGraphMetaData == null) {
            return false;
        }
        if (proDataGraphMetaData2 == null) {
            return false;
        }
        if (n2 != i) {
            array[0] = "Mismatch metadata parameter numbers for parameter " + i;
            throw new SchemaValidationException(7665970990714723371L, array);
        }
        if (n != n3) {
            array[0] = "Mismatch In/Out mode for parameter " + i;
            throw new SchemaValidationException(7665970990714723371L, array);
        }
        if (proDataGraphMetaData.m_TableCount != proDataGraphMetaData2.m_TableCount) {
            array[0] = "Table count mismatch for parameter " + i;
            throw new SchemaValidationException(7665970990714723371L, array);
        }
        for (int j = 0; j < proDataGraphMetaData.m_TableCount; ++j) {
            DataObjectMetaData.validate(i, (ProDataObjectMetaData)proDataGraphMetaData.m_domdi[j].getMetaData(), (ProDataObjectMetaData)proDataGraphMetaData2.m_domdi[j].getMetaData());
        }
        if (proDataGraphMetaData2.m_drmd != null) {
            length = proDataGraphMetaData2.m_drmd.length;
        }
        for (int k = 0; k < length; ++k) {
            proDataGraphMetaData2.m_drmd[k].validate(proDataGraphMetaData2);
        }
        return true;
    }
    
    protected int getFlag() {
        return this.m_Flag;
    }
    
    protected void setFlag(final int flag) {
        this.m_Flag = flag;
    }
    
    protected boolean hasError() {
        return (this.m_Flag & 0x1) != 0x0;
    }
    
    protected int getInOut() {
        return this.m_InOut;
    }
    
    protected void setInOut(final int inOut) {
        this.m_InOut = inOut;
    }
    
    protected int getParmNum() {
        return this.m_ParmNum;
    }
    
    protected void setParmNum(final int parmNum) {
        this.m_ParmNum = parmNum;
    }
    
    public String getCodePage() {
        return this.m_CodePage;
    }
    
    protected void setCodePage(final String codePage) {
        this.m_CodePage = codePage;
    }
    
    public String getProDataGraphName() {
        return this.m_ProDataGraphName;
    }
    
    protected void setProDataGraphName(final String proDataGraphName) {
        this.m_ProDataGraphName = proDataGraphName;
    }
    
    public String getXMLNamespace() {
        return this.m_XMLNamespace;
    }
    
    protected void setXMLNamespace(final String xmlNamespace) {
        this.m_XMLNamespace = xmlNamespace;
    }
    
    public String getExtendedProperties() {
        return this.m_ExtendedProperties;
    }
    
    protected void setExtendedProperties(final String extendedProperties) {
        this.m_ExtendedProperties = extendedProperties;
    }
    
    public String getXMLPrefix() {
        return this.m_XMLPrefix;
    }
    
    protected void setXMLPrefix(final String xmlPrefix) {
        this.m_XMLPrefix = xmlPrefix;
    }
    
    private static int getProType(final Type type) {
        final Integer n = new Integer(0);
        final Long n2 = new Long(0L);
        final String s = "temp";
        final BigDecimal bigDecimal = new BigDecimal(0);
        final Boolean b = new Boolean(false);
        final byte[] array = { 0 };
        final Date date = new Date(0L);
        if (type.isInstance((Object)n)) {
            return 4;
        }
        if (type.isInstance((Object)n2)) {
            return 7;
        }
        if (type.isInstance((Object)s)) {
            return 1;
        }
        if (type.isInstance((Object)bigDecimal)) {
            return 5;
        }
        if (type.isInstance((Object)b)) {
            return 3;
        }
        if (type.isInstance((Object)array)) {
            return 8;
        }
        if (type.isInstance((Object)date)) {
            return 2;
        }
        return -1;
    }
}
