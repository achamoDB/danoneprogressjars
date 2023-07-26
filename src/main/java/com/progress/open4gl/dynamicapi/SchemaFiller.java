// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataRelationMetaData;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.Open4GLException;
import java.util.Vector;
import com.progress.open4gl.ProDataGraphMetaData;
import java.io.IOException;
import java.io.InputStream;

public class SchemaFiller
{
    public SchemaFiller(final InputStream inputStream, final ParameterSet set, final MetaSchema metaSchema, final int n, final boolean b, int n2) throws ClientException, FGLErrorException, Open4GLException, ProDataException {
        if (inputStream == null) {
            return;
        }
        final int numParams = set.getNumParams();
        final Vector schemas = metaSchema.getSchemas();
        int n3 = 1;
        if (n > 9 && b) {
            n3 = 0;
        }
        int n4 = 0;
        for (int i = 1; i <= numParams; ++i) {
            final boolean b2 = false;
            if (set.isResultSet(i) || set.isMappedTable(i)) {
                if (set.isOutput(i) && (set.getProType(i) == 17 || n > 9)) {
                    if (n3 != 0) {
                        n3 = 0;
                        try {
                            int n5 = inputStream.read();
                            if (n5 == 82) {
                                inputStream.read();
                                inputStream.read();
                                n5 = inputStream.read();
                            }
                            if (n5 == 1) {
                                inputStream.read();
                                n2 = inputStream.read();
                                if (n2 == 4 && set.getProType(i) == 17) {
                                    throw new ClientException(2, 7665970990714728443L, new Object[] { "TABLE-HANDLE" });
                                }
                            }
                        }
                        catch (IOException ex) {
                            ExceptionConverter.convertIOException(ex);
                        }
                    }
                    final ProDataGraphMetaDataIndicator proDataGraphMetaDataIndicator = schemas.elementAt(n4);
                    if (!b2) {
                        if (set.isMappedTable(i)) {
                            n2 = this.ReadResultSetMetaDataIntoDG(inputStream, proDataGraphMetaDataIndicator, n, n2);
                        }
                        else {
                            n2 = this.ReadResultSetMetaData(inputStream, proDataGraphMetaDataIndicator, n, n2);
                        }
                    }
                    else {
                        proDataGraphMetaDataIndicator.setMetaData(null);
                    }
                    set.setReadHdr(false);
                }
                ++n4;
            }
            else if (set.isDataGraph(i)) {
                if (set.isOutput(i)) {
                    if (n3 != 0) {
                        n3 = 0;
                        try {
                            int n6 = inputStream.read();
                            if (n6 == 82) {
                                inputStream.read();
                                inputStream.read();
                                n6 = inputStream.read();
                            }
                            if (n6 == 1) {
                                inputStream.read();
                                if (inputStream.read() == 4 && set.getProType(i) == 37) {
                                    throw new ClientException(2, 7665970990714728443L, new Object[] { "DATASET-HANDLE" });
                                }
                            }
                        }
                        catch (IOException ex2) {
                            ExceptionConverter.convertIOException(ex2);
                        }
                    }
                    final ProDataGraphMetaDataIndicator element = new ProDataGraphMetaDataIndicator((ProDataGraphMetaData)schemas.elementAt(n4).getMetaData(), i, set.getMode(i), set.isMappedTable(i));
                    schemas.remove(n4);
                    schemas.add(n4, element);
                    n2 = this.ReadProDataGraphMetaData(inputStream, element, n, n2);
                    set.setReadHdr(false);
                }
                ++n4;
            }
        }
    }
    
    private int ReadResultSetMetaData(final InputStream inputStream, final MetaDataIndicator metaDataIndicator, final int n, int n2) throws ClientException, FGLErrorException {
        try {
            ResultSet set;
            if (n > 9) {
                set = new ResultSet(TableParamMetaSchema.metaDataV2, new StreamReader(inputStream, n));
            }
            else {
                set = new ResultSet(TableParamMetaSchema.metaData, new StreamReader(inputStream, n));
            }
            if (!set.next()) {
                metaDataIndicator.setMetaData(null);
                return n2;
            }
            final int int1 = set.getInt(1);
            metaDataIndicator.setParamNum(set.getInt(2));
            final int int2 = set.getInt(3);
            if (int2 != 0) {
                metaDataIndicator.setInOut(int2);
            }
            final int int3 = set.getInt(4);
            String string = null;
            int int4 = 0;
            int int5 = 0;
            String string2 = null;
            String string3 = null;
            if (n > 9) {
                set.getString(5);
                string = set.getString(6);
                int4 = set.getInt(7);
                int5 = set.getInt(8);
                string2 = set.getString(9);
                set.getInt(10);
                if (n2 != 4) {
                    string3 = set.getString(11);
                    set.getString(12);
                    set.getString(13);
                    set.getString(14);
                }
            }
            if (set.next()) {
                throw new ClientException(2, 17L, null);
            }
            if (n2 == 4) {
                n2 = set.getMarshalLevel();
                return n2;
            }
            DataObjectMetaData metaData = null;
            ResultSetMetaData rsmd;
            if (metaDataIndicator.m_typeCode == 0) {
                rsmd = new ResultSetMetaData(int1, int3);
                metaDataIndicator.setMetaData(rsmd);
            }
            else {
                metaData = new ProDataObjectMetaData(string, int3, false, int4, string3, null, null);
                metaData.setFlag(int5);
                metaData.m_tableErrorString = string2;
                rsmd = metaData.m_rsmd;
                metaDataIndicator.setMetaData(metaData);
            }
            ResultSet set2;
            if (n > 9) {
                set2 = new ResultSet(FieldParamMetaSchema.metaDataV2, new StreamReader(inputStream, n));
                for (int i = 1; i <= int3; ++i) {
                    set2.next();
                    final int int6 = set2.getInt(1);
                    final int int7 = set2.getInt(2);
                    final String string4 = set2.getString(3);
                    set2.getInt(4);
                    final int int8 = set2.getInt(5);
                    final int int9 = set2.getInt(6);
                    set2.getString(7);
                    set2.getString(8);
                    final int int10 = set2.getInt(9);
                    set2.getString(10);
                    set2.getString(11);
                    if (metaDataIndicator.m_typeCode == 0) {
                        rsmd.setFieldDesc(i, string4, int7, int6, int8, int10, int9);
                    }
                    else {
                        metaData.setFieldDesc(i, string4, int7, int6, int8, int10, int9);
                    }
                }
            }
            else {
                set2 = new ResultSet(FieldParamMetaSchema.metaData, new StreamReader(inputStream, n));
                for (int j = 1; j <= int3; ++j) {
                    set2.next();
                    rsmd.setFieldDesc(j, set2.getString(3), set2.getInt(2), set2.getInt(1));
                }
            }
            set2.next();
            n2 = set2.getMarshalLevel();
            return n2;
        }
        catch (ProSQLException ex) {
            throw ExceptionConverter.convertFromProSQLException(ex);
        }
    }
    
    private int ReadProDataGraphMetaData(final InputStream inputStream, final MetaDataIndicator metaDataIndicator, final int n, int n2) throws ClientException, Open4GLException, ProDataException {
        try {
            final ProDataGraphMetaData proDataGraphMetaData = (ProDataGraphMetaData)metaDataIndicator.getMetaData();
            final ResultSet set = new ResultSet(TableParamMetaSchema.dgMetaData, new StreamReader(inputStream, n));
            if (!set.next()) {
                metaDataIndicator.setMetaData(null);
                return n2;
            }
            final ProDataGraphMetaData proDataGraphMetaData2 = new ProDataGraphMetaData(1);
            proDataGraphMetaData2.setCodePage(set.getString(1));
            proDataGraphMetaData2.setProDataGraphName(set.getString(2));
            final int int1 = set.getInt(3);
            int n3 = set.getInt(4);
            if (n3 == 0 && metaDataIndicator != null) {
                n3 = metaDataIndicator.getInOut();
            }
            final int int2 = set.getInt(5);
            final int int3 = set.getInt(6);
            proDataGraphMetaData2.setFlag(set.getInt(7));
            proDataGraphMetaData2.setXMLNamespace(set.getString(8));
            proDataGraphMetaData2.setXMLPrefix(set.getString(9));
            proDataGraphMetaData2.setExtendedProperties(set.getString(10));
            if (set.next()) {
                throw new ClientException(2, 17L, null);
            }
            n2 = set.getMarshalLevel();
            if (int3 > 0) {
                final ProDataRelationMetaData[] proDataRelationMetaData = new ProDataRelationMetaData[int3];
                final ResultSet set2 = new ResultSet(TableParamMetaSchema.dgRelationData, new StreamReader(inputStream, n));
                set2.next();
                for (int i = 0; i < int3; ++i) {
                    proDataRelationMetaData[i] = new ProDataRelationMetaData();
                    proDataRelationMetaData[i].m_ChildIx = set2.getInt(1);
                    proDataRelationMetaData[i].m_ParentIx = set2.getInt(2);
                    proDataRelationMetaData[i].m_Flag = set2.getInt(3);
                    proDataRelationMetaData[i].m_NumPairs = set2.getInt(4);
                    proDataRelationMetaData[i].setPairsList(set2.getString(5));
                    proDataRelationMetaData[i].m_LinkName = set2.getString(6);
                    proDataRelationMetaData[i].m_ExtendedProperties = set2.getString(7);
                    set2.next();
                    if (i == int3 - 1) {
                        n2 = set2.getMarshalLevel();
                    }
                }
                proDataGraphMetaData2.setProDataRelationMetaData(proDataRelationMetaData);
            }
            final ProDataObjectMetaDataIndicator[] proDataObjectMetaDataIndicator = new ProDataObjectMetaDataIndicator[int2];
            for (int j = 0; j < int2; ++j) {
                ProDataObjectMetaData tableMetaData = null;
                if (proDataGraphMetaData != null) {
                    tableMetaData = proDataGraphMetaData.getTableMetaData(j);
                }
                proDataObjectMetaDataIndicator[j] = new ProDataObjectMetaDataIndicator(tableMetaData, metaDataIndicator.getParamNum(), metaDataIndicator.getInOut());
                n2 = this.ReadResultSetMetaData(inputStream, proDataObjectMetaDataIndicator[j], n, n2);
            }
            proDataGraphMetaData2.setProDataObjectMetaDataIndicator(proDataObjectMetaDataIndicator);
            for (int k = 0; k < int3; ++k) {
                proDataGraphMetaData2.getRelationMetaData(k).setPairsIndexInfo(proDataGraphMetaData2);
            }
            metaDataIndicator.setMetaData(proDataGraphMetaData2);
            DataGraphMetaData.validate(metaDataIndicator.getParamNum(), metaDataIndicator.getInOut(), proDataGraphMetaData, int1, n3, proDataGraphMetaData2);
            return n2;
        }
        catch (ProSQLException ex) {
            throw ExceptionConverter.convertFromProSQLException(ex);
        }
    }
    
    private int ReadResultSetMetaDataIntoDG(final InputStream inputStream, final MetaDataIndicator metaDataIndicator, final int n, int readResultSetMetaData) throws ClientException, Open4GLException, ProDataException {
        final ProDataGraphMetaData proDataGraphMetaData = (ProDataGraphMetaData)metaDataIndicator.getMetaData();
        ProDataGraphMetaData metaData = new ProDataGraphMetaData(1);
        final ProDataObjectMetaDataIndicator[] proDataObjectMetaDataIndicator = { null };
        ProDataObjectMetaData tableMetaData = null;
        if (proDataGraphMetaData != null) {
            tableMetaData = proDataGraphMetaData.getTableMetaData(0);
        }
        proDataObjectMetaDataIndicator[0] = new ProDataObjectMetaDataIndicator(tableMetaData, metaDataIndicator.getParamNum(), metaDataIndicator.getInOut());
        readResultSetMetaData = this.ReadResultSetMetaData(inputStream, proDataObjectMetaDataIndicator[0], n, readResultSetMetaData);
        metaData.setProDataObjectMetaDataIndicator(proDataObjectMetaDataIndicator);
        final ProDataObjectMetaData proDataObjectMetaData = (ProDataObjectMetaData)proDataObjectMetaDataIndicator[0].getMetaData();
        if (proDataGraphMetaData == null && proDataObjectMetaData == null) {
            metaData = null;
        }
        metaDataIndicator.setMetaData(metaData);
        DataGraphMetaData.validate(metaDataIndicator.getParamNum(), metaDataIndicator.getInOut(), proDataGraphMetaData, proDataObjectMetaDataIndicator[0].getParamNum(), proDataObjectMetaDataIndicator[0].getInOut(), metaData);
        return readResultSetMetaData;
    }
}
