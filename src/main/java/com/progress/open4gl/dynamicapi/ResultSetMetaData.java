// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLError;
import java.sql.SQLException;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.Open4GLException;
import java.io.Serializable;
import com.progress.open4gl.ProResultSetMetaData;

public class ResultSetMetaData extends MetaDataBase implements ProResultSetMetaData, Serializable
{
    protected FieldDesc[] fields;
    protected int numColumns;
    protected int[] columns;
    protected int uniqueId;
    protected ExternalHashtable nameTable;
    
    public ResultSetMetaData(final int uniqueId, final int n) {
        super(0);
        this.fields = new FieldDesc[n];
        this.uniqueId = uniqueId;
        this.numColumns = 0;
        this.columns = null;
        this.nameTable = new ExternalHashtable();
    }
    
    public ResultSetMetaData(final java.sql.ResultSetMetaData resultSetMetaData) throws ProSQLException {
        super(0);
        try {
            final int columnCount = resultSetMetaData.getColumnCount();
            this.fields = new FieldDesc[columnCount];
            this.uniqueId = 0;
            this.numColumns = 0;
            this.columns = null;
            this.nameTable = new ExternalHashtable();
            for (int i = 1; i < columnCount; ++i) {
                final int columnType = resultSetMetaData.getColumnType(i);
                int n = 0;
                switch (columnType) {
                    case 4: {
                        n = 4;
                        break;
                    }
                    case -5: {
                        n = 41;
                        break;
                    }
                    case 3: {
                        n = 5;
                        break;
                    }
                    case -1: {
                        n = 1;
                        break;
                    }
                    case -7: {
                        n = 3;
                        break;
                    }
                    case 91: {
                        n = 2;
                        break;
                    }
                    case -3: {
                        n = 8;
                        break;
                    }
                    default: {
                        throw new ProSQLException(new Open4GLException(7665970990714725440L, new Object[] { new Integer(columnType) }).getMessage());
                    }
                }
                this.setFieldDesc(i, resultSetMetaData.getColumnName(i), 0, n);
            }
        }
        catch (SQLException ex) {
            throw new ProSQLException(ex.toString());
        }
    }
    
    ResultSetMetaData() {
        super(0);
    }
    
    public FieldDesc setFieldDesc(final int n, final String s, final int n2, final int n3) {
        return this.setFieldDesc(n, s, n2, n3, 0, 0, 0);
    }
    
    public FieldDesc setFieldDesc(final int n, final String s, final int n2, final int n3, final int n4, final int n5) {
        return this.setFieldDesc(n, s, n2, n3, n4, n5, 0);
    }
    
    public FieldDesc setFieldDesc(final int value, String s, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (value < 1 || value > this.fields.length) {
            final Open4GLError open4GLError = new Open4GLError(7665970990714723375L, null);
            open4GLError.printStackTrace();
            throw open4GLError;
        }
        this.fields[value - 1] = new FieldDesc(s, n, n2, this.numColumns, n3, n4, n5);
        this.numColumns += ((n != 0) ? n : 1);
        if (s == null) {
            s = "";
        }
        if (this.nameTable.put(s.toUpperCase(), new Integer(value)) != null) {
            throw new Open4GLError(7665970990714723381L, new Object[] { s });
        }
        return this.fields[value - 1];
    }
    
    int fieldToColumn(final String s, final int n) throws ProSQLException {
        final int nameToField = this.nameToField(s);
        if (nameToField == 0) {
            throw ResultSet.getProSQLException(7665970990714723382L, "S1002", s);
        }
        return this.fieldToColumn(nameToField, n);
    }
    
    int fieldToColumn(final String s) throws ProSQLException {
        return this.fieldToColumn(s, 0);
    }
    
    int nameToField(final String s) {
        final Integer n = (Integer)this.nameTable.get(s.toUpperCase());
        if (n == null) {
            return 0;
        }
        return n;
    }
    
    void columnToField() {
        this.columns = new int[this.numColumns];
        int n = 0;
        for (int i = 0; i < this.fields.length; ++i) {
            int extent = this.fields[i].getExtent();
            if (extent == 0) {
                extent = 1;
            }
            for (int j = 0; j < extent; ++j) {
                this.columns[n++] = i;
            }
        }
    }
    
    protected void validateCol(final int n) throws ProSQLException {
        if (this.columns == null) {
            this.columnToField();
        }
        if (n < 1 || n > this.columns.length) {
            throw ResultSet.getProSQLException(7665970990714723343L, "S1002", new Integer(n).toString());
        }
        if (this.fields[this.columns[n - 1]] == null) {
            throw ResultSet.getProSQLException(7665970990714723343L, "S1002", new Integer(n).toString());
        }
    }
    
    private boolean validateFld(final int n) {
        return n >= 1 && n <= this.fields.length && this.fields[n - 1] != null;
    }
    
    protected static boolean validate(final int n, final ResultSetMetaData resultSetMetaData, final ResultSetMetaData resultSetMetaData2) throws ProSQLException {
        if (resultSetMetaData == resultSetMetaData2) {
            return true;
        }
        if (resultSetMetaData == null) {
            return false;
        }
        if (resultSetMetaData2 == null) {
            return false;
        }
        if (resultSetMetaData.getColumnCount() != resultSetMetaData2.getColumnCount()) {
            throw ResultSet.getProSQLException(7665970990714723371L, "S1002", "Mismatch column count for parameter number " + n);
        }
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
            if (resultSetMetaData.getColumnType(i) != resultSetMetaData2.getColumnType(i)) {
                throw ResultSet.getProSQLException(7665970990714723371L, "S1002", "Mismatch type for column number " + i + " in metadata for parameter number " + n);
            }
        }
        return true;
    }
    
    protected boolean validateFields() throws ClientException {
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i] == null) {
                throw new ClientException(3, 7665970990714729310L, new Object[] { new Integer(this.fields.length) });
            }
        }
        return true;
    }
    
    public int getProColumnType(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getType();
    }
    
    public int getFieldCount() {
        return this.fields.length;
    }
    
    int fieldToColumn(final int value, int value2) throws ProSQLException {
        final FieldDesc fieldDesc = this.getFieldDesc(value);
        if (fieldDesc == null) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        final int extent = fieldDesc.getExtent();
        final String string = new Integer(extent).toString();
        final String string2 = new Integer(value2).toString();
        final String name = fieldDesc.getName();
        if (value2 == 0) {
            if (extent != 0) {
                throw ResultSet.getProSQLException(44L, "S1002", name);
            }
            ++value2;
        }
        if (value2 < 1) {
            throw ResultSet.getProSQLException(7665970990714723378L, "S1002", name);
        }
        if (value2 <= extent || (extent <= 0 && value2 <= 1)) {
            return fieldDesc.getNumColumns() + value2;
        }
        if (extent == 0) {
            throw ResultSet.getProSQLException(7665970990714723380L, "S1002", name);
        }
        throw ResultSet.getProSQLException(7665970990714723379L, "S1002", name, string, string2);
    }
    
    public int getFieldProType(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getType();
    }
    
    public int getColumnProType(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getType();
    }
    
    public int getColumnUserOrder(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getUserOrder();
    }
    
    public int getColumnXMLMapping(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getXMLMapping();
    }
    
    public String getFieldJavaTypeName(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getJavaType();
    }
    
    public String getColumnJavaTypeName(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getJavaType();
    }
    
    FieldDesc getFieldDesc(final int n) {
        if (!this.validateFld(n)) {
            return null;
        }
        return this.fields[n - 1];
    }
    
    public int getFieldExtent(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getExtent();
    }
    
    public int getColumnCount() {
        return this.numColumns;
    }
    
    public String getColumnName(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getName();
    }
    
    public String getFieldName(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getName();
    }
    
    public int getFlag(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getFlag();
    }
    
    public int getColumnType(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getSqlType();
    }
    
    public String getColumnTypeName(final int n) throws ProSQLException {
        this.validateCol(n);
        return this.fields[this.columns[n - 1]].getTypeName();
    }
    
    public String getFieldTypeName(final int value) throws ProSQLException {
        if (!this.validateFld(value)) {
            throw ResultSet.getProSQLException(7665970990714723344L, "S1002", new Integer(value).toString());
        }
        return this.fields[value - 1].getTypeName();
    }
    
    void print(final Tracer tracer) {
        for (int length = this.fields.length, i = 1; i <= length; ++i) {
            this.fields[i - 1].print(tracer);
        }
    }
    
    public boolean isAutoIncrement(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isCaseSensitive(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isSearchable(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isCurrency(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public int isNullable(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isSigned(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public int getColumnDisplaySize(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public String getColumnLabel(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public String getSchemaName(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public int getPrecision(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public int getScale(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public String getTableName(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public String getCatalogName(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isReadOnly(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isWritable(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public boolean isDefinitelyWritable(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
    
    public String getColumnClassName(final int n) throws ProSQLException {
        throw ResultSet.getProSQLException();
    }
}
