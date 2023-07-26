// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLError;
import java.util.StringTokenizer;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.ProSQLException;
import java.util.Vector;
import com.progress.open4gl.SDOResultSetMetaData;

class SDOResultSetMetaDataImpl extends ResultSetMetaData implements SDOResultSetMetaData
{
    static final int NUM_ROWOBJ_FLDS = 5;
    private Vector extendedFieldDesc;
    
    private SDOResultSetMetaDataImpl(final FieldDesc[] fields, final int numColumns, final int[] columns, final ExternalHashtable nameTable, final Vector extendedFieldDesc) {
        super.fields = fields;
        super.numColumns = numColumns;
        super.columns = columns;
        super.uniqueId = 0;
        super.nameTable = nameTable;
        this.extendedFieldDesc = extendedFieldDesc;
    }
    
    public String getColumnValExp(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[1];
    }
    
    public String getColumnValMsg(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[2];
    }
    
    public String getTableName(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[3];
    }
    
    public int getColumnDisplaySize(final int n) throws ProSQLException {
        this.validateCol(n);
        return Integer.valueOf(((String[])this.extendedFieldDesc.elementAt(n - 1))[4]);
    }
    
    public int isNullable(final int n) throws ProSQLException {
        this.validateCol(n);
        if (((String[])this.extendedFieldDesc.elementAt(n - 1))[5].equals("yes")) {
            return 0;
        }
        return 1;
    }
    
    public String getColumnFormat(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[6];
    }
    
    public String getColumnInitialValue(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[7];
    }
    
    public String getColumnLabel(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[8];
    }
    
    public boolean isWritable(final int n) throws ProSQLException {
        return !this.isReadOnly(n);
    }
    
    public boolean isReadOnly(final int n) throws ProSQLException {
        this.validateCol(n);
        final String s = ((String[])this.extendedFieldDesc.elementAt(n - 1))[10];
        return !s.equals("no") && !s.equals("NO");
    }
    
    String get4GLType(final int n) throws ProSQLException {
        this.validateCol(n);
        return ((String[])this.extendedFieldDesc.elementAt(n - 1))[9];
    }
    
    static SDOResultSetMetaDataImpl createSDOSchema(final ResultSetMetaData resultSetMetaData, final Vector vector) throws ProSQLException {
        if (!resultSetMetaData.getColumnName(resultSetMetaData.numColumns).equals("RowUserProp")) {
            throw ResultSet.getProSQLException(7665970990714725132L);
        }
        final int n = resultSetMetaData.numColumns - 5;
        final FieldDesc[] array = new FieldDesc[resultSetMetaData.fields.length - 5];
        final int[] array2 = new int[resultSetMetaData.columns.length - 5];
        for (int i = 0; i < array.length; ++i) {
            array[i] = resultSetMetaData.fields[i];
        }
        for (int j = 0; j < array2.length; ++j) {
            array2[j] = resultSetMetaData.columns[j];
        }
        return new SDOResultSetMetaDataImpl(array, n, array2, resultSetMetaData.nameTable, vector);
    }
    
    static Vector getColumnProperties(final String s, final int n) {
        final ParseColProp parseColProp = new ParseColProp(s, n);
        final Vector<String[]> vector = new Vector<String[]>();
        final int numProps = parseColProp.getNumProps();
        while (parseColProp.next()) {
            final String[] obj = new String[numProps];
            obj[0] = parseColProp.getColumnName();
            for (int i = 1; i < numProps; ++i) {
                obj[i] = parseColProp.getProp(i);
            }
            vector.addElement(obj);
        }
        return vector;
    }
    
    private static void addSystemField(final Vector vector, final String s, final String s2, final int n) {
        final String[] obj = new String[n + 1];
        obj[0] = s;
        obj[9] = s2;
        vector.addElement(obj);
    }
    
    private static ResultSetMetaData createFromVector(final Vector vector) {
        final int size = vector.size();
        final ResultSetMetaData resultSetMetaData = new ResultSetMetaData(0, size);
        for (int i = 0; i < size; ++i) {
            final String[] array = vector.elementAt(i);
            resultSetMetaData.setFieldDesc(i + 1, array[0], 0, Parameter.fromProNameToProNum(array[9]));
        }
        return resultSetMetaData;
    }
    
    static ResultSetMetaData createSendRowsMetadata(final Vector vector, final int n) {
        addSystemField(vector, "RowNum", "INTEGER", n);
        addSystemField(vector, "RowIdent", "CHARACTER", n);
        addSystemField(vector, "RowMod", "CHARACTER", n);
        addSystemField(vector, "RowIdentIdx", "CHARACTER", n);
        addSystemField(vector, "RowUserProp", "CHARACTER", n);
        return createFromVector(vector);
    }
    
    static ResultSetMetaData createServerCommitMetaData(final Vector vector, final int n) {
        addSystemField(vector, "ChangedFields", "CHARACTER", n);
        return createFromVector(vector);
    }
    
    static void trimSystemFields(final Vector vector) {
        final int size = vector.size();
        for (int i = 1; i <= 6; ++i) {
            vector.removeElementAt(size - i);
        }
    }
    
    static class ParseColProp
    {
        private static final char COL_DELIMITER = '\u0003';
        private static final char PROP_DELIMITER = '\u0004';
        private static final char[] COL_DELIMITER_ARRAY;
        private static final String COL_DELIMITER_STR;
        private int numProps;
        private String[] currentColumn;
        private int[] delMap;
        private StringTokenizer columns;
        
        ParseColProp(final String str, final int n) {
            this.numProps = n + 1;
            this.columns = new StringTokenizer(str, ParseColProp.COL_DELIMITER_STR, false);
            this.currentColumn = new String[this.numProps];
            this.delMap = new int[this.numProps + 1];
        }
        
        int getNumProps() {
            return this.numProps;
        }
        
        boolean next() {
            if (!this.columns.hasMoreTokens()) {
                return false;
            }
            final String nextToken = this.columns.nextToken();
            this.delMap[0] = -1;
            this.delMap[this.numProps] = nextToken.length();
            for (int n = 1, index = 0; index < nextToken.length() && n < this.numProps; ++index) {
                if (nextToken.charAt(index) == '\u0004') {
                    this.delMap[n++] = index;
                }
            }
            int n2 = 0;
            int n3 = 0;
            for (int i = 1; i <= this.numProps; ++i, ++n2) {
                this.currentColumn[n2] = nextToken.substring(this.delMap[n3] + 1, this.delMap[i]);
                ++n3;
            }
            if (n2 != this.numProps) {
                throw new Open4GLError();
            }
            return true;
        }
        
        String getColumnName() {
            return this.currentColumn[0];
        }
        
        String getProp(final int n) {
            return this.currentColumn[n];
        }
        
        static {
            COL_DELIMITER_ARRAY = new char[] { '\u0003' };
            COL_DELIMITER_STR = new String(ParseColProp.COL_DELIMITER_ARRAY);
        }
    }
}
