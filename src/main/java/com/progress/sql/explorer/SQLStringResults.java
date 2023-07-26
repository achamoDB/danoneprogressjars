// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.international.resources.ProgressResources;
import com.progress.common.exception.ProException;
import java.util.Enumeration;
import com.progress.chimera.util.Misc;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.SQLWarning;
import java.sql.SQLException;

public class SQLStringResults implements ISQLConstants, ISQLProperties
{
    private boolean is_OSF1;
    protected SQLProperties m_SQLProps;
    protected SQLUtil m_SQLUtil;
    protected String m_statementText;
    protected SQLException m_SQLExceptionList;
    protected SQLWarning m_SQLWarningList;
    protected SQLConnect m_SQLConnect;
    protected Vector m_columnInfoVector;
    protected Vector m_resultSetVector;
    protected Vector m_resultSetVectorList;
    protected int m_resultSetVectorIdx;
    protected int m_resultSetsDeleted;
    protected ColumnInfo[] m_columnInfoArray;
    protected long m_fetchedCount;
    protected int m_updateCount;
    private int m_lineCount;
    private int m_recordNumber;
    protected int m_statementType;
    protected boolean m_isResultSet;
    protected boolean m_fetchLimitReached;
    protected ResultSet m_resultSet;
    protected ResultSetMetaData m_resultSetMetaData;
    String sqlQuery;
    static SQLExplorerLog m_log;
    static final String UNDERLINES = "-------------------------------------";
    
    public SQLStringResults(final SQLProperties sqlProperties, final SQLConnect sqlConnect) throws SQLException, Exception {
        this(sqlProperties, sqlConnect, null);
    }
    
    public SQLStringResults(final SQLProperties sqlProps, final SQLConnect sqlConnect, final String statementText) {
        this.is_OSF1 = System.getProperty("os.name").equalsIgnoreCase("OSF1");
        this.m_SQLProps = null;
        this.m_SQLUtil = new SQLUtil();
        this.m_statementText = null;
        this.m_SQLExceptionList = null;
        this.m_SQLWarningList = null;
        this.m_SQLConnect = null;
        this.m_columnInfoVector = null;
        this.m_resultSetVector = new Vector();
        this.m_resultSetVectorList = new Vector();
        this.m_resultSetVectorIdx = -1;
        this.m_resultSetsDeleted = 0;
        this.m_columnInfoArray = null;
        this.m_fetchedCount = 0L;
        this.m_updateCount = 0;
        this.m_lineCount = 0;
        this.m_recordNumber = 0;
        final SQLUtil sqlUtil = this.m_SQLUtil;
        this.m_statementType = 9;
        this.m_isResultSet = false;
        this.m_fetchLimitReached = false;
        this.m_resultSet = null;
        this.m_resultSetMetaData = null;
        this.sqlQuery = "select owner from sysprogress.systables where tbl='syscolumns'";
        this.m_SQLProps = sqlProps;
        this.m_SQLConnect = sqlConnect;
        this.m_statementText = statementText;
        final Connection connection = this.m_SQLConnect.getConnection();
        try {
            this.m_SQLConnect.closeStatement();
            if (statementText == null || statementText.length() == 0) {
                return;
            }
            Label_0608: {
                switch (this.m_statementType = this.m_SQLUtil.getStatementType(this.m_statementText)) {
                    case 0:
                    case 2: {
                        this.m_updateCount = this.m_SQLConnect.createStatement().executeUpdate(this.m_statementText + ISQLConstants.NEWLINE);
                        this.m_resultSet = null;
                        this.m_isResultSet = false;
                        break;
                    }
                    case 5: {
                        this.setReturnValues(this.m_isResultSet = this.m_SQLConnect.createCallableStatement(this.m_statementText).execute());
                        break;
                    }
                    case 1: {
                        this.m_resultSet = this.m_SQLConnect.createStatement().executeQuery(this.m_statementText);
                        this.m_isResultSet = true;
                        this.m_updateCount = -1;
                        break;
                    }
                    case 9: {
                        this.setReturnValues(this.m_isResultSet = this.m_SQLConnect.createStatement().execute(this.m_statementText));
                        break;
                    }
                    case 3: {
                        connection.commit();
                        break;
                    }
                    case 4: {
                        connection.rollback();
                        break;
                    }
                    case 8: {
                        switch (this.m_SQLUtil.whichStatement(8, this.m_statementText)) {
                            case 1: {
                                this.m_resultSet = this.m_SQLConnect.createStatement().executeQuery(this.m_statementText);
                                this.m_isResultSet = true;
                                this.m_updateCount = -1;
                                break Label_0608;
                            }
                            default: {
                                this.setReturnValues(this.m_isResultSet = this.m_SQLConnect.createStatement().execute(this.m_statementText));
                                break Label_0608;
                            }
                        }
                        break;
                    }
                    default: {
                        this.setReturnValues(this.m_isResultSet = this.m_SQLConnect.createStatement().execute(this.m_statementText));
                        break;
                    }
                }
            }
            if (this.m_resultSet != null) {
                this.m_resultSetMetaData = this.m_resultSet.getMetaData();
                if (this.m_columnInfoVector == null) {
                    this.m_columnInfoVector = this.vectorizeColumnInfo();
                }
            }
        }
        catch (SQLException ex) {
            this.getSQLErrors(ex);
            if (ex.getErrorCode() == -20217) {
                this.m_SQLConnect.setConnection(null);
            }
        }
    }
    
    public void closeResultSet() throws SQLException {
        if (this.m_resultSet != null) {
            this.m_resultSet.close();
        }
    }
    
    public void setReturnValues(final boolean b) throws SQLException {
        final Statement statement = this.m_SQLConnect.getStatement();
        if (b) {
            this.m_resultSet = statement.getResultSet();
            this.m_updateCount = -1;
        }
        else {
            this.m_updateCount = statement.getUpdateCount();
            this.m_resultSet = null;
        }
    }
    
    public void getSQLWarnings() {
        final Connection connection = this.m_SQLConnect.getConnection();
        if (connection != null && this.m_SQLWarningList == null) {
            try {
                if (!this.m_SQLProps.getDisableWarnings()) {
                    this.m_SQLWarningList = connection.getWarnings();
                }
                connection.clearWarnings();
            }
            catch (SQLException ex) {}
        }
    }
    
    public void getSQLErrors(final SQLException sqlExceptionList) {
        final Statement statement = this.m_SQLConnect.getStatement();
        if (sqlExceptionList != null) {
            this.m_SQLExceptionList = sqlExceptionList;
        }
        if (statement != null) {
            try {
                this.m_SQLWarningList = statement.getWarnings();
            }
            catch (SQLException ex) {}
        }
        this.getSQLWarnings();
    }
    
    public boolean isFetchLimitReached() {
        return this.m_fetchLimitReached;
    }
    
    public int getSQLExceptionCount() {
        int n = 0;
        for (SQLException ex = this.m_SQLExceptionList; ex != null; ex = ex.getNextException()) {
            ++n;
        }
        return n;
    }
    
    public SQLException getSQLExceptionList() {
        return this.m_SQLExceptionList;
    }
    
    public int getSQLWarningCount() {
        int n = 0;
        for (SQLWarning sqlWarning = this.m_SQLWarningList; sqlWarning != null; sqlWarning = sqlWarning.getNextWarning()) {
            ++n;
        }
        return n;
    }
    
    public SQLWarning getSQLWarningList() {
        return this.m_SQLWarningList;
    }
    
    public Vector getColumnInfoVector() {
        if (this.m_columnInfoVector == null) {
            return new Vector();
        }
        return this.m_columnInfoVector;
    }
    
    public int getColumnCount() {
        if (this.m_columnInfoArray == null) {
            return 0;
        }
        return this.m_columnInfoArray.length;
    }
    
    public ColumnInfo[] getColumnInfoArray() {
        if (this.m_columnInfoArray == null) {
            return new ColumnInfo[0];
        }
        return this.m_columnInfoArray;
    }
    
    public int getResultSetSize() {
        return this.m_resultSetVector.size();
    }
    
    public Vector getResultSetVector() {
        return this.m_resultSetVector;
    }
    
    public String getStatementText() {
        return this.m_statementText;
    }
    
    public String getSQLExceptionString() {
        return this.getSQLExceptionString(this.m_SQLExceptionList);
    }
    
    public String getSQLExceptionString(final SQLException ex) {
        int i = 0;
        String s = "";
        SQLException nextException;
        for (nextException = ex; nextException != null; nextException = nextException.getNextException()) {
            ++i;
            s += this.m_SQLUtil.sqlExceptionString(nextException, "=== SQL Exception " + i + " ===");
        }
        if (null != nextException) {
            s = s + "### Too many SQL Exceptions. ###" + ISQLConstants.NEWLINE;
        }
        return s;
    }
    
    public String getSQLWarningString() {
        return this.getSQLWarningString(this.m_SQLWarningList);
    }
    
    public String getSQLWarningString(final SQLWarning sqlWarning) {
        int i = 0;
        String str = "";
        SQLWarning nextWarning;
        for (nextWarning = sqlWarning; nextWarning != null; nextWarning = nextWarning.getNextWarning()) {
            ++i;
            str += this.m_SQLUtil.sqlExceptionString(nextWarning, "=== SQL Warning " + i + " ===");
        }
        if (null != nextWarning) {
            str = "### Too many SQLWarnings. ###" + ISQLConstants.NEWLINE;
        }
        return str;
    }
    
    public int getResultGroupNumber() {
        return this.m_resultSetVectorIdx + this.m_resultSetsDeleted;
    }
    
    public boolean isFirstResultSet() {
        return this.m_resultSetVectorIdx == 1;
    }
    
    public boolean isLastResultSet() {
        return this.m_resultSetVectorIdx > 0 && this.m_resultSetVectorIdx == this.m_resultSetVectorList.size() && this.m_resultSet == null;
    }
    
    public void getPrevResultSetVector() throws ResultSetBeginException {
        if (!this.isFirstResultSet()) {
            --this.m_resultSetVectorIdx;
            this.m_resultSetVector = this.m_resultSetVectorList.elementAt(this.m_resultSetVectorIdx - 1);
            return;
        }
        throw new ResultSetBeginException();
    }
    
    public void getNextResultSetVector() throws ResultSetEndException {
        if (this.m_resultSetVectorIdx < this.m_resultSetVectorList.size()) {
            ++this.m_resultSetVectorIdx;
            this.m_resultSetVector = this.m_resultSetVectorList.elementAt(this.m_resultSetVectorIdx - 1);
        }
        else {
            this.getOutput();
        }
    }
    
    public void getOutput() throws ResultSetEndException {
        try {
            final Statement statement = this.m_SQLConnect.getStatement();
            if (statement == null) {
                return;
            }
            if (this.m_updateCount >= 0) {
                return;
            }
            this.m_fetchLimitReached = false;
            while (!this.m_fetchLimitReached && this.m_resultSet != null) {
                this.m_resultSetVector = this.vectorizeResultSet();
                if (this.m_SQLProps.getMode() == 1) {
                    if (this.m_resultSetVectorIdx >= this.m_SQLProps.getResultSetLimit()) {
                        this.m_resultSetVectorList.removeElementAt(0);
                        ++this.m_resultSetsDeleted;
                    }
                    this.m_resultSetVectorList.addElement(this.m_resultSetVector);
                    this.m_resultSetVectorIdx = this.m_resultSetVectorList.size();
                }
                if (!this.m_fetchLimitReached && this.m_resultSet == null && statement.getMoreResults()) {
                    this.setReturnValues(true);
                }
            }
        }
        catch (SQLException ex) {
            this.getSQLErrors(ex);
        }
    }
    
    public void printOutput(final BufferedWriter bufferedWriter) throws IOException {
        this.printOutput(null, bufferedWriter);
    }
    
    public void printOutput(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        if (this.m_SQLProps.getReportFormat().equalsIgnoreCase("By Label")) {
            this.printResultSetByLabel(bufferedReader, bufferedWriter);
        }
        else if (this.printColumnHeadings(bufferedReader, bufferedWriter) == 0) {
            this.printResultSet(bufferedReader, bufferedWriter);
        }
    }
    
    public void generateOutput(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        if (this.isUpdateCount()) {
            final int statementType = this.m_statementType;
            final SQLUtil sqlUtil = this.m_SQLUtil;
            if (statementType == 0) {
                this.printUpdateCount(bufferedReader, bufferedWriter);
            }
        }
        if (!this.isResultSet()) {
            return;
        }
        if (this.m_columnInfoVector == null || this.m_columnInfoVector.size() <= 0) {
            return;
        }
        this.m_recordNumber = 0;
        this.printOutput(bufferedReader, bufferedWriter);
        boolean b;
        do {
            b = false;
            if (bufferedReader != null && this.m_SQLProps.getHasFetchLimit() && this.m_resultSet != null) {
                String lowerCase;
                do {
                    bufferedWriter.write("Fetch next " + this.m_SQLProps.getFetchLimit() + " records (y/n)? ");
                    bufferedWriter.flush();
                    ++this.m_lineCount;
                    lowerCase = bufferedReader.readLine().toLowerCase();
                } while (!lowerCase.startsWith("y") && !lowerCase.startsWith("n"));
                if (!lowerCase.startsWith("y")) {
                    continue;
                }
                b = true;
                try {
                    this.getOutput();
                    this.printOutput(bufferedReader, bufferedWriter);
                }
                catch (ResultSetEndException ex) {}
            }
        } while (this.isFetchLimitReached() && b);
    }
    
    public int printColumnHeadings(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        if (this.m_columnInfoVector.size() == 0) {
            return 0;
        }
        if (this.m_columnInfoArray == null) {
            return 0;
        }
        final int length = this.m_columnInfoArray.length;
        for (int i = 0; i < length; ++i) {
            final ColumnInfo columnInfo = this.m_columnInfoArray[i];
            bufferedWriter.write(Misc.fill(columnInfo.getColumnName(), columnInfo.getDisplayWidth(), columnInfo.isNumeric()) + " ");
        }
        bufferedWriter.newLine();
        for (int j = 0; j < length; ++j) {
            bufferedWriter.write(Misc.fill("", this.m_columnInfoArray[j].getDisplayWidth(), "-------------------------------------") + " ");
        }
        bufferedWriter.newLine();
        this.m_lineCount += 2;
        return this.checkPageCount(bufferedReader, bufferedWriter);
    }
    
    private int checkPageCount(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        int n = 0;
        if (bufferedReader != null && this.m_SQLProps.getPager() && this.m_lineCount >= this.m_SQLProps.getPageLimit()) {
            this.m_lineCount = 0;
            bufferedWriter.write("--more--");
            bufferedWriter.flush();
            ++this.m_lineCount;
            if (bufferedReader.readLine().toLowerCase().startsWith("q")) {
                n = 1;
            }
        }
        return n;
    }
    
    private void printResultSet(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        int checkPageCount = 0;
        if (!this.isResultSet()) {
            return;
        }
        if (this.m_columnInfoVector.size() <= 0) {
            return;
        }
        for (Enumeration elements = this.m_resultSetVector.elements(); elements.hasMoreElements() && checkPageCount == 0; checkPageCount = this.checkPageCount(bufferedReader, bufferedWriter)) {
            final String[] array = elements.nextElement();
            for (int i = 0; i < array.length; ++i) {
                String s = array[i];
                if (null == s) {
                    s = "";
                }
                final ColumnInfo columnInfo = this.m_columnInfoArray[i];
                bufferedWriter.write(Misc.fill(s, columnInfo.getDisplayWidth(), columnInfo.isNumeric()) + " ");
            }
            bufferedWriter.newLine();
            bufferedWriter.flush();
            ++this.m_lineCount;
        }
    }
    
    private void printResultSetByLabel(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) throws IOException {
        if (!this.isResultSet() || this.m_columnInfoVector.size() == 0) {
            return;
        }
        final ColumnInfo[] array = this.m_columnInfoVector.elementAt(0);
        int checkPageCount = 0;
        int max = 0;
        final int length = (this.getResultSetSize() + "").length();
        for (int i = 0; i < array.length; ++i) {
            max = Math.max(array[i].getColumnName().length(), max);
        }
        this.m_lineCount = 0;
        final Enumeration elements = this.m_resultSetVector.elements();
        while (elements.hasMoreElements() && checkPageCount == 0) {
            ++this.m_recordNumber;
            final String[] array2 = elements.nextElement();
            for (int n = 0; n < array2.length && checkPageCount == 0; checkPageCount = this.checkPageCount(bufferedReader, bufferedWriter), ++n) {
                String s = array2[n];
                if (null == s) {
                    s = "";
                }
                final ColumnInfo columnInfo = array[n];
                bufferedWriter.write(Misc.fill("[" + this.m_recordNumber + "]", length + 2, false) + " ");
                bufferedWriter.write(Misc.fill(columnInfo.getColumnName() + ":", max + 1, false) + " ");
                bufferedWriter.write(Misc.fill(s, columnInfo.getDisplayWidth(), false) + " ");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                ++this.m_lineCount;
            }
        }
    }
    
    public boolean isResultSet() {
        return this.m_isResultSet;
    }
    
    public boolean isUpdateCount() {
        return !this.m_isResultSet;
    }
    
    public long getFetchedCount() {
        return this.m_fetchedCount;
    }
    
    public long getUpdateCount() {
        return this.m_updateCount;
    }
    
    protected Vector vectorizeResultSet() throws SQLException, ResultSetEndException {
        long n = 0L;
        final int columnCount = this.m_resultSetMetaData.getColumnCount();
        final int columnWidthLimit = this.m_SQLProps.getColumnWidthLimit();
        final Vector<String[]> vector = new Vector<String[]>();
        final boolean hasColumnLimit = this.m_SQLProps.getHasColumnLimit();
        this.m_fetchLimitReached = false;
        while (!this.m_fetchLimitReached && this.m_resultSet != null) {
            if (this.m_SQLProps.getHasFetchLimit() && n >= this.m_SQLProps.getFetchLimit()) {
                this.m_fetchLimitReached = true;
                break;
            }
            if (!this.m_resultSet.next()) {
                this.m_resultSet = null;
                if (n == 0L) {
                    throw new ResultSetEndException();
                }
                break;
            }
            else {
                final String[] obj = new String[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    this.m_resultSetMetaData.getColumnTypeName(i + 1);
                    final String string = this.m_resultSet.getString(i + 1);
                    if (null == string) {
                        obj[i] = null;
                    }
                    else if (hasColumnLimit && string.length() > columnWidthLimit) {
                        obj[i] = string.substring(0, columnWidthLimit);
                    }
                    else {
                        obj[i] = string;
                    }
                }
                ++n;
                ++this.m_fetchedCount;
                vector.addElement(obj);
            }
        }
        return vector;
    }
    
    protected Vector vectorizeColumnInfo() throws SQLException {
        final Vector<ColumnInfo[]> vector = new Vector<ColumnInfo[]>();
        if (this.m_resultSetMetaData == null) {
            return vector;
        }
        final int columnCount = this.m_resultSetMetaData.getColumnCount();
        this.m_columnInfoArray = new ColumnInfo[columnCount];
        for (int i = 0; i < columnCount; ++i) {
            String columnName = this.m_resultSetMetaData.getColumnName(i + 1);
            if (columnName == null) {
                columnName = "";
            }
            final int columnDisplaySize = this.m_resultSetMetaData.getColumnDisplaySize(i + 1);
            final int columnType = this.m_resultSetMetaData.getColumnType(i + 1);
            int columnWidthLimit;
            int columnWidthMinimum;
            if (this.m_SQLProps.getHasColumnLimit()) {
                columnWidthLimit = this.m_SQLProps.getColumnWidthLimit();
                columnWidthMinimum = this.m_SQLProps.getColumnWidthMinimum();
            }
            else {
                columnWidthLimit = columnDisplaySize;
                columnWidthMinimum = 0;
            }
            this.m_columnInfoArray[i] = new ColumnInfo(columnName, this.m_SQLUtil.columnWidth(columnDisplaySize, columnName.length(), columnWidthLimit, columnWidthMinimum), this.m_SQLUtil.isNumeric(columnType));
        }
        vector.addElement(this.m_columnInfoArray);
        return vector;
    }
    
    protected void printUpdateCount(final BufferedReader bufferedReader, final BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write("Update count = " + this.getUpdateCount() + ".");
            bufferedWriter.newLine();
        }
        catch (IOException obj) {
            System.err.println("### Exception in printUpdateCount(). ###");
            System.err.println(obj + "");
        }
    }
    
    static {
        SQLStringResults.m_log = SQLExplorerLog.get();
    }
    
    public static class ResultSetBeginException extends ProException
    {
        public ResultSetBeginException() {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "This is the first result set."), new Object[0]);
        }
    }
    
    public static class ResultSetEndException extends ProException
    {
        public ResultSetEndException() {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "This is the last result set."), new Object[0]);
        }
    }
    
    public class ColumnInfo
    {
        String m_columnName;
        boolean m_isNumeric;
        int m_displayWidth;
        
        public ColumnInfo(final String columnName, final int displayWidth, final boolean isNumeric) {
            this.m_columnName = columnName;
            this.m_displayWidth = displayWidth;
            this.m_isNumeric = isNumeric;
        }
        
        public String getColumnName() {
            return this.m_columnName;
        }
        
        public boolean isNumeric() {
            return this.m_isNumeric;
        }
        
        public int getDisplayWidth() {
            return this.m_displayWidth;
        }
        
        public String toString() {
            return this.getColumnName();
        }
    }
}
