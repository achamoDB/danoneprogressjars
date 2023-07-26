// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.util.Enumeration;
import java.util.Date;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.StringTokenizer;
import com.progress.international.resources.ProgressResources;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Vector;
import java.sql.Connection;
import com.progress.message.exMsg;
import java.util.Hashtable;

public class SQLProperties extends Hashtable implements ISQLConstants, ISQLProperties, exMsg
{
    Connection m_connection;
    SQLLogging m_SQLLogging;
    Vector m_propertyWarningList;
    int m_mode;
    private String sqlDatabase;
    private boolean sqlDebug;
    private String sqlHost;
    private String sqlPassword;
    private String sqlService;
    private String sqlUrl;
    private String sqlUser;
    private boolean sqlSSL;
    private boolean sqlSSLHV;
    private boolean sqlSSLSR;
    private String sqlDriverUrl;
    static SQLExplorerLog m_log;
    
    Object get(final String s) {
        return super.get(s.toLowerCase());
    }
    
    void put(final String s, final SQLProperty value) {
        super.put(s.toLowerCase(), value);
    }
    
    public SQLProperties(final int mode, final SQLLogging sqlLogging) throws IOException, SQLException {
        this.m_connection = null;
        this.m_SQLLogging = null;
        this.m_propertyWarningList = new Vector();
        this.m_mode = 0;
        this.sqlDatabase = new String();
        this.sqlDebug = false;
        this.sqlHost = "localhost";
        this.sqlPassword = new String();
        this.sqlService = new String();
        this.sqlUrl = new String();
        this.sqlUser = System.getProperty("user.name");
        this.sqlSSL = false;
        this.sqlSSLHV = true;
        this.sqlSSLSR = true;
        this.sqlDriverUrl = null;
        this.m_mode = mode;
        this.m_SQLLogging = sqlLogging;
        this.buildPropertyList(3, ISQLProperties.m_booleanProperties);
        this.buildPropertyList(1, ISQLProperties.m_stringProperties);
        this.buildPropertyList(4, ISQLProperties.m_enumerationProperties);
        this.buildPropertyList(5, ISQLProperties.m_enumerationIdxProperties);
        this.buildPropertyList(2, ISQLProperties.m_integerProperties);
        this.load();
    }
    
    public void clearPropertyWarningList() {
        this.m_propertyWarningList.removeAllElements();
    }
    
    public Vector getPropertyWarningList() {
        return this.m_propertyWarningList;
    }
    
    private int getInt(final Object o) {
        final String s = (String)o;
        return new Integer((String)o);
    }
    
    private void buildPropertyHelpList(final Object[][] array) {
        for (int i = 0; i < array.length; ++i) {
            try {
                final String s = (String)array[i][0];
                final String[] array2 = (String[])array[i][1];
                final SQLProperty sqlProperty = (SQLProperty)this.get(s);
                for (int j = 0; j < array2.length; ++j) {
                    sqlProperty.addHelpRange(array2[j]);
                }
            }
            finally {}
        }
    }
    
    private void buildPropertyList(final int n, final Object[][] array) {
        for (int i = 0; i < array.length; ++i) {
            try {
                final String s = (String)array[i][0];
                final String s2 = (String)array[i][1];
                final String s3 = (String)array[i][2];
                SQLProperty sqlProperty = null;
                final int intValue = Integer.valueOf(s2);
                switch (n) {
                    case 3: {
                        sqlProperty = new SQLBooleanProperty(s, intValue, s3);
                        break;
                    }
                    case 4: {
                        sqlProperty = new SQLEnumProperty(s, intValue, s3, (String[])array[i][3]);
                        break;
                    }
                    case 5: {
                        sqlProperty = new SQLEnumIdxProperty(s, intValue, s3, (String[])array[i][3]);
                        break;
                    }
                    case 2: {
                        sqlProperty = new SQLIntProperty(s, intValue, s3, this.getInt(array[i][3]), this.getInt(array[i][4]));
                        break;
                    }
                    case 1: {
                        sqlProperty = new SQLStringProperty(s, intValue, s3, (String[])array[i][3]);
                        break;
                    }
                }
                this.setProperty(true, sqlProperty, s3);
            }
            catch (PropertyValueException ex) {
                System.err.println(ex.getMessage());
            }
            catch (PropertyUnknownException ex2) {
                System.err.println(ex2.getMessage());
            }
        }
    }
    
    protected void defaultCommon() throws PropertyValueException, PropertyUnknownException, SQLException {
        this.defaultAutoCommit();
        this.defaultColumnWidthMinimum();
        this.defaultColumnWidthLimit();
        this.defaultConnectTimeout();
        this.defaultDisableWarnings();
        this.defaultFetchLimit();
        this.defaultHasColumnLimit();
        this.defaultHasFetchLimit();
        this.defaultLogging();
        this.defaultLogfile();
        this.defaultReportFormat();
        this.defaultResultSetLimit();
        this.defaultSqlUrl();
        this.defaultTransactionIsolation();
    }
    
    protected void defaultChar() throws PropertyValueException, PropertyUnknownException, SQLException {
        this.defaultEchoAll();
        this.defaultEchoComments();
        this.defaultEchoCmd();
        this.defaultEchoSql();
        this.defaultPager();
        this.defaultPageLimit();
        this.defaultSqlVerbose();
    }
    
    protected void defaultGUI() throws PropertyValueException, PropertyUnknownException, SQLException {
        this.defaultEcho();
        this.defaultTableFormat();
        this.defaultUrlFormat();
    }
    
    public void setMode(final int mode) {
        this.m_mode = mode;
    }
    
    public int getMode() {
        return this.m_mode;
    }
    
    public synchronized void setConnection(final Connection connection) {
        this.m_connection = connection;
    }
    
    public synchronized boolean isConnected() {
        return this.m_connection != null;
    }
    
    private synchronized void setProperty(final boolean b, final String s, final String s2) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(b, (SQLProperty)this.get(s), s2);
    }
    
    private synchronized void setProperty(final String s, final String s2) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(false, (SQLProperty)this.get(s), s2);
    }
    
    private synchronized void setProperty(final SQLProperty sqlProperty, final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(false, sqlProperty, s);
    }
    
    private synchronized void setProperty(final boolean b, final SQLProperty sqlProperty, final String s) throws PropertyValueException, PropertyUnknownException {
        final String name = sqlProperty.getName();
        sqlProperty.setValue(s);
        this.put(name, sqlProperty);
        if (!b) {
            SQLProperties.m_log.log("Setting " + name + " to " + s);
        }
    }
    
    private synchronized String getDefaultProperty(final String s) throws PropertyUnknownException {
        return ((SQLProperty)this.get(s)).getDefaultValue();
    }
    
    public synchronized int getPropertyMin(final String s) {
        return ((SQLIntProperty)this.get(s)).getMin();
    }
    
    public synchronized int getPropertyMax(final String s) {
        return ((SQLIntProperty)this.get(s)).getMax();
    }
    
    private synchronized String getProperty(final String s) {
        return ((SQLProperty)this.get(s)).getValue();
    }
    
    protected synchronized String getPropertyValue(final String s, final int n) {
        String s2 = "";
        String s3 = "";
        final SQLProperty sqlProperty = (SQLProperty)this.get(s);
        final int mode = sqlProperty.getMode();
        switch (n) {
            case 1: {
                s2 = sqlProperty.getHelpRange();
                break;
            }
            case 2: {
                s2 = sqlProperty.getShow();
                break;
            }
        }
        switch (this.m_mode) {
            case 0: {
                if (mode == 1 || mode == 2) {
                    s3 = s2;
                    break;
                }
                break;
            }
            case 1: {
                if (mode == 1 || mode == 3) {
                    s3 = s2;
                    break;
                }
                break;
            }
        }
        return s3;
    }
    
    private synchronized String getPropertyRange(final String s) {
        return ((SQLProperty)this.get(s)).getRange();
    }
    
    public synchronized void defaultPageLimit() throws PropertyValueException, PropertyUnknownException {
        this.setPageLimit(this.getDefaultProperty("PageLimit"));
    }
    
    public synchronized String getPageLimitRange() {
        return this.getPropertyRange("PageLimit");
    }
    
    public synchronized int getPageLimit() {
        return Integer.valueOf(this.getProperty("PageLimit"));
    }
    
    public synchronized void setPageLimit(final int i) throws PropertyValueException, PropertyUnknownException {
        this.setPageLimit(i + "");
    }
    
    public synchronized void setPageLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "PageLimit", s);
    }
    
    public synchronized void defaultPager() throws PropertyValueException, PropertyUnknownException {
        this.setPager(this.getDefaultProperty("Pager"));
    }
    
    public synchronized boolean getPager() {
        return Boolean.valueOf(this.getProperty("Pager"));
    }
    
    public synchronized void setPager(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setPager(b + "");
    }
    
    public synchronized String getPagerRange() {
        return this.getPropertyRange("Pager");
    }
    
    public synchronized void setPager(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "Pager", s);
    }
    
    public synchronized void defaultReportFormat() throws PropertyValueException, PropertyUnknownException {
        this.setReportFormat(this.getDefaultProperty("ReportFormat"));
    }
    
    public synchronized String getReportFormatRange() {
        return this.getPropertyRange("ReportFormat");
    }
    
    public synchronized String getReportFormat() {
        return this.getProperty("ReportFormat");
    }
    
    public synchronized void setReportFormat(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("ReportFormat", s);
    }
    
    public synchronized void defaultFetchLimit() throws PropertyValueException, PropertyUnknownException {
        this.setFetchLimit(this.getDefaultProperty("FetchLimit"));
    }
    
    public synchronized int getFetchLimit() {
        return Integer.valueOf(this.getProperty("FetchLimit"));
    }
    
    public synchronized String getFetchLimitRange() {
        return this.getPropertyRange("FetchLimit");
    }
    
    public synchronized void setFetchLimit(final long lng) throws PropertyValueException, PropertyUnknownException {
        this.setFetchLimit(lng + "");
    }
    
    public synchronized void setFetchLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("FetchLimit", s);
    }
    
    public synchronized void defaultHasColumnLimit() throws PropertyValueException, PropertyUnknownException {
        this.setHasColumnLimit(this.getDefaultProperty("HasColumnLimit"));
    }
    
    public synchronized boolean getHasColumnLimit() {
        return Boolean.valueOf(this.getProperty("HasColumnLimit"));
    }
    
    public synchronized String getHasColumnLimitRange() {
        return this.getPropertyRange("HasColumnLimit");
    }
    
    public synchronized void setHasColumnLimit(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setHasColumnLimit(b + "");
    }
    
    public synchronized void setHasColumnLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("HasColumnLimit", s);
    }
    
    public synchronized void defaultHasFetchLimit() throws PropertyValueException, PropertyUnknownException {
        this.setHasFetchLimit(this.getDefaultProperty("HasFetchLimit"));
    }
    
    public synchronized boolean getHasFetchLimit() {
        return Boolean.valueOf(this.getProperty("HasFetchLimit"));
    }
    
    public synchronized String getHasFetchLimitRange() {
        return this.getPropertyRange("HasFetchLimit");
    }
    
    public synchronized void setHasFetchLimit(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setHasFetchLimit(b + "");
    }
    
    public synchronized void setHasFetchLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("HasFetchLimit", s);
    }
    
    public synchronized void defaultResultSetLimit() throws PropertyValueException, PropertyUnknownException {
        this.setResultSetLimit(this.getDefaultProperty("ResultSetLimit"));
    }
    
    public synchronized int getResultSetLimit() {
        return Integer.valueOf(this.getProperty("ResultSetLimit"));
    }
    
    public synchronized String getResultSetLimitRange() {
        return this.getPropertyRange("ResultSetLimit");
    }
    
    public synchronized void setResultSetLimit(final long lng) throws PropertyValueException, PropertyUnknownException {
        this.setResultSetLimit(lng + "");
    }
    
    public synchronized void setResultSetLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("ResultSetLimit", s);
    }
    
    public synchronized void defaultColumnWidthLimit() throws PropertyValueException, PropertyUnknownException {
        this.setColumnWidthLimit(this.getDefaultProperty("ColumnWidthLimit"));
    }
    
    public synchronized int getColumnWidthLimit() {
        return Integer.valueOf(this.getProperty("ColumnWidthLimit"));
    }
    
    public synchronized String getColumnWidthLimitRange() {
        return this.getPropertyRange("ColumnWidthLimit");
    }
    
    public synchronized void setColumnWidthLimit(final int i) throws PropertyValueException, PropertyUnknownException {
        this.setColumnWidthLimit(i + "");
    }
    
    public synchronized void setColumnWidthLimit(final String s) throws PropertyValueException, PropertyUnknownException {
        final int columnWidthMinimum = this.getColumnWidthMinimum();
        try {
            if (Integer.valueOf(s) < columnWidthMinimum) {
                throw new PropertyValueException(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "InvalidColumnMaximum", new Object[] { s, "ColumnWidthLimit", new Integer(columnWidthMinimum), "ColumnWidthMinimum" }));
            }
            this.setProperty("ColumnWidthLimit", s);
        }
        catch (NumberFormatException ex) {
            throw new PropertyValueException("ColumnWidthLimit", s);
        }
    }
    
    public synchronized void defaultColumnWidthMinimum() throws PropertyValueException, PropertyUnknownException {
        this.setColumnWidthMinimum(this.getDefaultProperty("ColumnWidthMinimum"));
    }
    
    public synchronized int getColumnWidthMinimum() {
        return Integer.valueOf(this.getProperty("ColumnWidthMinimum"));
    }
    
    public synchronized String getColumnWidthMinimumRange() {
        return this.getPropertyRange("ColumnWidthMinimum");
    }
    
    public synchronized void setColumnWidthMinimum(final int i) throws PropertyValueException, PropertyUnknownException {
        this.setColumnWidthMinimum(i + "");
    }
    
    public synchronized void setColumnWidthMinimum(final String s) throws PropertyValueException, PropertyUnknownException {
        final int columnWidthLimit = this.getColumnWidthLimit();
        try {
            if (Integer.valueOf(s) > columnWidthLimit) {
                throw new PropertyValueException(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "InvalidColumnMinimum", new Object[] { s, "ColumnWidthMinimum", new Integer(columnWidthLimit), "ColumnWidthLimit" }));
            }
            this.setProperty("ColumnWidthMinimum", s);
        }
        catch (NumberFormatException ex) {
            throw new PropertyValueException("ColumnWidthMinimum", s);
        }
    }
    
    public synchronized void defaultDisableWarnings() throws PropertyValueException, PropertyUnknownException {
        this.setDisableWarnings(this.getDefaultProperty("DisableWarnings"));
    }
    
    public synchronized boolean getDisableWarnings() {
        return Boolean.valueOf(this.getProperty("DisableWarnings"));
    }
    
    public synchronized String getDisableWarningRange() {
        return this.getPropertyRange("DisableWarnings");
    }
    
    public synchronized void setDisableWarnings(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setDisableWarnings(b + "");
    }
    
    public synchronized void setDisableWarnings(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("DisableWarnings", s);
    }
    
    public synchronized void defaultAutoCommit() throws PropertyValueException, PropertyUnknownException, SQLException {
        this.setAutoCommit(this.getDefaultProperty("AutoCommit"));
    }
    
    public synchronized boolean getAutoCommit() {
        return Boolean.valueOf(this.getProperty("AutoCommit"));
    }
    
    public synchronized String getAutoCommitRange() {
        return this.getPropertyRange("AutoCommit");
    }
    
    public synchronized void setAutoCommit(final boolean b) throws PropertyValueException, PropertyUnknownException, SQLException {
        this.setAutoCommit(b + "");
    }
    
    public synchronized void setAutoCommit(final String s) throws PropertyValueException, PropertyUnknownException, SQLException {
        boolean b = this.getAutoCommit();
        try {
            this.setProperty("AutoCommit", s);
            if (this.m_connection != null) {
                final boolean booleanValue = Boolean.valueOf(s);
                if ((b = this.m_connection.getAutoCommit()) != booleanValue) {
                    this.m_connection.setAutoCommit(booleanValue);
                }
            }
        }
        catch (SQLException obj) {
            this.setProperty("AutoCommit", b + "");
            SQLProperties.m_log.log("Autocommit exception: " + obj + "");
            throw obj;
        }
    }
    
    public boolean getSqlDebug() {
        return this.sqlDebug;
    }
    
    public boolean getSqlSSL() {
        return this.sqlSSL;
    }
    
    public boolean getSqlSSLSR() {
        return this.sqlSSLSR;
    }
    
    public boolean getSqlSSLHV() {
        return this.sqlSSLHV;
    }
    
    public void getSqlDebug(final boolean sqlDebug) {
        this.sqlDebug = sqlDebug;
    }
    
    public String getSqlDatabase() {
        return this.sqlDatabase;
    }
    
    public String getSqlHost() {
        return this.sqlHost;
    }
    
    public String getSqlService() {
        return this.sqlService;
    }
    
    public String getSqlUser() {
        return this.sqlUser;
    }
    
    public String getSqlPassword() {
        return this.sqlPassword;
    }
    
    private String sqlConstructUrl(final String str, final String s, final String str2, final boolean b, final boolean b2, final boolean b3) {
        final String s2 = new String();
        this.setSqlDatabase(str2);
        this.setSqlService(s);
        this.setSqlHost(str);
        String str3;
        try {
            Integer.parseInt(s);
            str3 = "jdbc:datadirect:openedge://" + str + ":" + s + ";databaseName=" + str2;
        }
        catch (NumberFormatException ex) {
            str3 = "jdbc:datadirect:openedge://" + str + ":-1" + ";databaseName=" + str2 + ";serviceName=" + s;
        }
        if (b || !b2 || !b3) {
            String s3 = str3 + "[";
            if (b) {
                s3 += "-ssl";
            }
            if (!b2) {
                s3 += "-nsr";
            }
            if (!b3) {
                s3 += "-nhv";
            }
            str3 = s3 + "]";
        }
        return str3;
    }
    
    public void validateUrl(final String s) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException {
        int n = 0;
        final String[] array = new String[7];
        String s2 = s.toLowerCase();
        if (s.toLowerCase().startsWith("jdbc:odbc:")) {
            return;
        }
        for (int i = 0; i <= 5; ++i) {
            array[i] = "";
        }
        int n2;
        if (s.toLowerCase().startsWith("jdbc:progress:T:".toLowerCase()) || s.toLowerCase().startsWith("jdbc:jdbcprogress:T:".toLowerCase())) {
            for (n2 = 0; n2 < 5 && n == 0; ++n2) {
                final int index = s2.indexOf(":");
                if (index >= 0) {
                    if (index > 0) {
                        array[n2] = s2.substring(0, index);
                    }
                    else {
                        array[n2] = "";
                    }
                    s2 = s2.substring(index + 1);
                    if (n2 < 5) {
                        array[n2 + 1] = ((s2.length() > 0) ? s2 : "");
                    }
                }
                else {
                    n = 1;
                }
            }
        }
        else {
            for (int j = 0; j < 7; ++j) {
                array[j] = "";
            }
            String str = s;
            n2 = 0;
            final int index2 = str.indexOf(":");
            if (index2 != -1) {
                array[0] = str.substring(0, index2);
                str = str.substring(index2 + 1);
                ++n2;
            }
            else if (str.length() != 0) {
                array[0] = str;
                str = "";
                ++n2;
            }
            else {
                n = 1;
            }
            final int index3 = str.indexOf(":");
            if (index3 != -1 && n == 0) {
                array[1] = str.substring(0, index3);
                str = str.substring(index3 + 1);
                ++n2;
            }
            else if (str.length() != 0) {
                array[1] = str;
                str = "";
                ++n2;
            }
            else {
                n = 1;
            }
            final int index4 = str.indexOf(":");
            if (index4 != -1 && n == 0) {
                array[2] = str.substring(0, index4);
                str = str.substring(index4 + 1);
                ++n2;
            }
            else if (str.length() != 0) {
                array[2] = str;
                str = "";
                ++n2;
            }
            else {
                n = 1;
            }
            if (str.startsWith("//") && n == 0) {
                str = str.substring(2, str.length());
                final int index5 = str.indexOf(":");
                if (index5 != -1 && n == 0) {
                    array[3] = str.substring(0, index5);
                    str = str.substring(index5 + 1);
                    ++n2;
                }
            }
            else if (str.length() != 0) {
                array[3] = str;
                str = "";
                ++n2;
            }
            else {
                n = 1;
            }
            final int index6 = str.indexOf(";");
            if (index6 != -1 && n == 0) {
                array[4] = str.substring(0, index6);
                str = str.substring(index6 + 1);
                ++n2;
            }
            else if (str.length() != 0) {
                array[4] = str;
                str = "";
                ++n2;
            }
            else {
                n = 1;
            }
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ";=");
            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                final String nextToken2 = stringTokenizer.nextToken();
                if (nextToken != null && nextToken.equalsIgnoreCase("databaseName")) {
                    array[5] = nextToken2;
                    ++n2;
                }
                if (nextToken != null && nextToken.equalsIgnoreCase("serviceName")) {
                    array[6] = nextToken2;
                    ++n2;
                }
            }
        }
        if (n != 0 && !array[2].equalsIgnoreCase("openedge")) {
            if (n2 == 0) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009244L, new Object[0]));
            }
            if (n2 == 1) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009245L, new Object[0]));
            }
            if (n2 == 2) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009246L, new Object[0]));
            }
            if (n2 == 3) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009247L, new Object[0]));
            }
            if (n2 == 4) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009248L, new Object[0]));
            }
            if (n2 == 5) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009249L, new Object[0]));
            }
        }
        else {
            if (array[3].length() == 0) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009249L, new Object[0]));
            }
            if (array[4].equals("-1") && array[6].equals("")) {
                throw new SQLConnect.PortOrServiceException();
            }
            if (array[5].length() == 0) {
                throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009249L, new Object[0]));
            }
        }
        if (!array[0].equals("jdbc")) {
            throw new SQLConnect.DriverProtocolException(array[0]);
        }
        if (!array[1].equals("progress") && !array[1].equals("jdbcprogress") && !array[1].equals("datadirect")) {
            throw new SQLConnect.DriverNameException(array[1]);
        }
        if (!array[2].equals("t") && !array[2].equals("tcp") && !array[2].equals("openedge")) {
            throw new SQLConnect.NetworkProtocolException(array[2]);
        }
        if (array[3].length() == 0) {
            throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009249L, new Object[0]));
        }
        if (array[4].length() == 0) {
            throw new SQLConnect.PortOrServiceException();
        }
        if (array[5].length() == 0) {
            throw new SQLConnect.UrlException(ExceptionMessageAdapter.getMessage(7311593995036009249L, new Object[0]));
        }
    }
    
    private void sqlDeconstructUrl(String s) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, PropertyValueException {
        String sqlHost = "";
        String sqlService = "";
        String sqlDatabase = "";
        this.validateUrl(s);
        this.sqlUrl = s;
        if (s.indexOf("jdbc:jdbcprogress:T:") != -1 || s.indexOf("jdbc:progress:T:") != -1 || s.indexOf("jdbc:datadirect:openedge:") != -1) {
            if (s.indexOf("jdbc:jdbcprogress:T:") != -1 || s.indexOf("jdbc:progress:T:") != -1) {
                final int lastIndex = s.lastIndexOf(58);
                sqlDatabase = s.substring(lastIndex + 1);
                s = s.substring(0, lastIndex);
                final int lastIndex2 = s.lastIndexOf(58);
                sqlService = s.substring(lastIndex2 + 1);
                s = s.substring(0, lastIndex2);
                sqlHost = s.substring(s.lastIndexOf(58) + 1);
            }
            else {
                final int index = s.indexOf("//");
                s = ((index != -1) ? s.substring(index + 2) : null);
                if (s != null) {
                    final StringTokenizer stringTokenizer = new StringTokenizer(s, ":;=");
                    if (stringTokenizer.countTokens() >= 4) {
                        sqlHost = stringTokenizer.nextToken();
                        sqlService = stringTokenizer.nextToken();
                        while (stringTokenizer.hasMoreTokens()) {
                            final String nextToken = stringTokenizer.nextToken();
                            final String nextToken2 = stringTokenizer.nextToken();
                            if (nextToken != null && nextToken.equalsIgnoreCase("databaseName")) {
                                if (nextToken2.indexOf(91) != -1) {
                                    sqlDatabase = nextToken2 + ":" + stringTokenizer.nextToken();
                                }
                                else {
                                    sqlDatabase = nextToken2;
                                }
                            }
                            if (nextToken != null && nextToken.equalsIgnoreCase("serviceName")) {
                                sqlService = nextToken2;
                            }
                        }
                    }
                }
            }
        }
        else if (s.indexOf("jdbc:odbc:") == -1) {
            throw new PropertyValueException("Connect", s);
        }
        this.setSqlDatabase(sqlDatabase);
        this.setSqlService(sqlService);
        this.setSqlHost(sqlHost);
    }
    
    public void setSqlDatabase(final String sqlDatabase) {
        this.sqlDatabase = sqlDatabase;
    }
    
    public void setSqlHost(final String sqlHost) {
        this.sqlHost = sqlHost;
    }
    
    public void setSqlService(final String sqlService) {
        this.sqlService = sqlService;
    }
    
    public void setSqlSSL(final boolean sqlSSL) {
        this.sqlSSL = sqlSSL;
    }
    
    public void setSqlSSLHV(final boolean sqlSSLHV) {
        this.sqlSSLHV = sqlSSLHV;
    }
    
    public void setSqlSSLSR(final boolean sqlSSLSR) {
        this.sqlSSLSR = sqlSSLSR;
    }
    
    public void setSqlDebug(final boolean sqlDebug) {
        this.sqlDebug = sqlDebug;
    }
    
    public void setSqlUser(final String sqlUser) {
        this.sqlUser = sqlUser;
    }
    
    public void setSqlPassword(final String sqlPassword) {
        this.sqlPassword = sqlPassword;
    }
    
    public synchronized void defaultSqlUrl() throws PropertyValueException, PropertyUnknownException {
        this.defaultSqlConnect();
    }
    
    public synchronized String getSqlUrl() {
        return this.getSqlConnect();
    }
    
    public synchronized String getSqlUrlRange() {
        return this.getSqlConnectRange();
    }
    
    public synchronized void setSqlUrl(final String s, final String s2, final String s3, final boolean b, final boolean b2, final boolean b3) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, PropertyValueException, PropertyUnknownException {
        this.setSqlConnect(s, s2, s3, b, b2, b3);
    }
    
    public synchronized void setSqlUrl(final String sqlConnect) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, PropertyValueException, PropertyUnknownException {
        this.setSqlConnect(sqlConnect);
    }
    
    public synchronized void resetSqlUrl() {
        try {
            if (this.m_connection != null) {
                this.setSqlUrl(this.m_connection.getMetaData().getURL());
            }
        }
        catch (Exception ex) {
            SQLProperties.m_log.log("Connect property not properly reset: " + ex.getMessage());
        }
    }
    
    public synchronized void defaultSqlConnect() throws PropertyValueException, PropertyUnknownException {
        try {
            if (this.getDefaultProperty("Connect").equals("")) {
                this.setSqlConnect(this.getDefaultProperty("Connect"));
            }
        }
        catch (Exception ex) {
            if (ex instanceof PropertyValueException) {
                throw (PropertyValueException)ex;
            }
            if (ex instanceof PropertyUnknownException) {
                throw (PropertyUnknownException)ex;
            }
        }
    }
    
    public synchronized String getSqlConnect() {
        return this.getProperty("Connect");
    }
    
    public synchronized String getSqlConnectRange() {
        return this.getPropertyRange("Connect");
    }
    
    public synchronized void setSqlConnect(final String s, final String s2, final String s3, final boolean b, final boolean b2, final boolean b3) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, PropertyValueException, PropertyUnknownException {
        this.setProperty("Connect", this.sqlConstructUrl(s, s2, s3, b, b2, b3));
    }
    
    public synchronized void setSqlConnect(final String s) throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, PropertyValueException, PropertyUnknownException {
        this.sqlDeconstructUrl(s);
        this.setProperty("Connect", s);
    }
    
    public synchronized void setSqlDriverUrl(final String sqlDriverUrl) {
        this.sqlDriverUrl = sqlDriverUrl;
    }
    
    public synchronized String getSqlDriverUrl() {
        return this.sqlDriverUrl;
    }
    
    public synchronized void defaultLogging() throws PropertyValueException, PropertyUnknownException {
        try {
            this.setLogging(this.getDefaultProperty("Logging"));
        }
        catch (Exception ex) {
            SQLProperties.m_log.log(ex.getMessage());
        }
    }
    
    public synchronized boolean getLogging() {
        return Boolean.valueOf(this.getProperty("Logging"));
    }
    
    public synchronized String getLoggingRange() {
        return this.getPropertyRange("Logging");
    }
    
    public synchronized void setLogging(final boolean b) throws PropertyValueException, PropertyUnknownException, SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, IOException {
        this.setLogging(b + "");
    }
    
    public synchronized void setLogging(final String s) throws PropertyValueException, PropertyUnknownException, SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, IOException {
        final boolean logging = this.getLogging();
        try {
            this.setProperty("Logging", s);
            final boolean booleanValue = Boolean.valueOf(s);
            if (this.m_SQLLogging.getLogging() != booleanValue) {
                this.m_SQLLogging.setLogging(booleanValue);
                this.m_SQLLogging.openLogSessionWriter();
            }
        }
        catch (Exception ex) {
            this.setProperty("Logging", logging + "");
            this.m_SQLLogging.setLogging(logging);
            if (ex instanceof SQLLogging.InvalidFileException) {
                throw (SQLLogging.InvalidFileException)ex;
            }
            if (ex instanceof SQLLogging.FileNotReadableException) {
                throw (SQLLogging.FileNotReadableException)ex;
            }
            if (ex instanceof SQLLogging.FileNotWritableException) {
                throw (SQLLogging.FileNotWritableException)ex;
            }
            if (ex instanceof PropertyValueException) {
                throw (PropertyValueException)ex;
            }
            if (ex instanceof IOException) {
                throw (IOException)ex;
            }
        }
    }
    
    public synchronized void defaultLogfile() throws PropertyValueException, PropertyUnknownException {
        try {
            this.setLogfile(this.getDefaultProperty("Logfile"));
        }
        catch (SQLLogging.InvalidFileException ex) {
            SQLProperties.m_log.log(ex.getMessage());
        }
        catch (SQLLogging.FileNotReadableException ex2) {
            SQLProperties.m_log.log(ex2.getMessage());
        }
        catch (SQLLogging.FileNotWritableException ex3) {
            SQLProperties.m_log.log(ex3.getMessage());
        }
        catch (IOException ex4) {
            SQLProperties.m_log.log(ex4.getMessage());
        }
    }
    
    public synchronized String getLogfile() {
        return this.getProperty("Logfile");
    }
    
    public synchronized String getLogfileRange() {
        return this.getPropertyRange("Logfile");
    }
    
    public synchronized void setLogfile(final String s) throws SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, IOException, PropertyValueException, PropertyUnknownException {
        final String logfile = this.getLogfile();
        try {
            this.setProperty("Logfile", s);
            if (!this.m_SQLLogging.getFullLogFile().equals(s)) {
                this.m_SQLLogging.setFullLogFile(s);
                this.m_SQLLogging.openLogSessionWriter();
            }
        }
        catch (SQLLogging.InvalidFileException ex) {
            this.setProperty("Logfile", logfile);
            throw ex;
        }
        catch (SQLLogging.FileNotReadableException ex2) {
            this.setProperty("Logfile", logfile);
            throw ex2;
        }
        catch (SQLLogging.FileNotWritableException ex3) {
            this.setProperty("Logfile", logfile);
            throw ex3;
        }
        catch (IOException ex4) {
            this.setProperty("Logfile", logfile);
            throw ex4;
        }
    }
    
    public synchronized void defaultSqlVerbose() throws PropertyValueException, PropertyUnknownException {
        this.setSqlVerbose(this.getDefaultProperty("SqlVerbose"));
    }
    
    public synchronized boolean getSqlVerbose() {
        return Boolean.valueOf(this.getProperty("SqlVerbose"));
    }
    
    public synchronized String getSqlVerboseRange() {
        return this.getPropertyRange("SqlVerbose");
    }
    
    public synchronized void setSqlVerbose(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setSqlVerbose(b + "");
    }
    
    public synchronized void setSqlVerbose(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "SqlVerbose", s);
    }
    
    public synchronized void defaultTransactionIsolation() throws PropertyValueException, PropertyUnknownException, SQLException {
        this.setTransactionIsolationText(this.getDefaultProperty("TransactionIsolation"));
    }
    
    public synchronized int getTransactionIsolation() {
        return this.convertTransEnumIdxToConnectIdx(this.getTransactionIsolationIdx());
    }
    
    public synchronized int getTransactionIsolationIdx() {
        int convertTransTextToIdx = 0;
        try {
            convertTransTextToIdx = this.convertTransTextToIdx(this.getProperty("TransactionIsolation"));
        }
        catch (PropertyValueException ex) {
            SQLProperties.m_log.log(ex.getMessage());
        }
        return convertTransTextToIdx;
    }
    
    public synchronized String getTransactionIsolationRange() {
        return this.getPropertyRange("TransactionIsolation");
    }
    
    public synchronized String getTransactionIsolationText() {
        return this.getProperty("TransactionIsolation");
    }
    
    public synchronized int convertTransTextToIdx(final String anotherString) throws PropertyValueException {
        int n;
        for (n = 0; n < ISQLProperties.TRANS_LEVEL_TEXT.length && !ISQLProperties.TRANS_LEVEL_TEXT[n].equalsIgnoreCase(anotherString); ++n) {}
        if (n >= ISQLProperties.TRANS_LEVEL_TEXT.length) {
            throw new PropertyValueException("TransactionIsolation", anotherString);
        }
        return n;
    }
    
    public synchronized void setTransactionIsolationText(final String s) throws PropertyValueException, PropertyUnknownException, SQLException {
        this.setTransactionIsolation(this.convertTransTextToIdx(s), s);
    }
    
    public synchronized void setTransactionIsolationIdx(final int i) throws PropertyValueException, PropertyUnknownException, SQLException {
        if (i < 0 || i > 3) {
            throw new PropertyValueException("TransactionIsolation", i + "");
        }
        this.setTransactionIsolation(i, ISQLProperties.TRANS_LEVEL_TEXT[i]);
    }
    
    public synchronized int convertTransEnumIdxToConnectIdx(final int n) {
        int n2 = 0;
        switch (n) {
            case 0: {
                n2 = 1;
                break;
            }
            case 1: {
                n2 = 2;
                break;
            }
            case 2: {
                n2 = 4;
                break;
            }
            case 3: {
                n2 = 8;
                break;
            }
        }
        return n2;
    }
    
    public synchronized void setTransactionIsolation(final int n, final String s) throws PropertyValueException, PropertyUnknownException, SQLException {
        final String transactionIsolationText = this.getTransactionIsolationText();
        try {
            this.setProperty("TransactionIsolation", s);
            if (this.m_connection != null) {
                final int convertTransEnumIdxToConnectIdx = this.convertTransEnumIdxToConnectIdx(n);
                if (this.m_connection.getTransactionIsolation() != convertTransEnumIdxToConnectIdx) {
                    this.m_connection.setTransactionIsolation(convertTransEnumIdxToConnectIdx);
                }
            }
        }
        catch (SQLException obj) {
            this.setProperty("TransactionIsolation", transactionIsolationText);
            SQLProperties.m_log.log("Transaction isolation exception: " + obj + "");
            throw obj;
        }
    }
    
    public synchronized void defaultTableFormat() throws PropertyValueException, PropertyUnknownException {
        this.setTableFormat(this.getDefaultProperty("TableFormat"));
    }
    
    public synchronized boolean getTableFormat() {
        return Boolean.valueOf(this.getProperty("TableFormat"));
    }
    
    public synchronized String getTableFormatRange() {
        return this.getPropertyRange("TableFormat");
    }
    
    public synchronized void setTableFormat(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setTableFormat(b + "");
    }
    
    public synchronized void setTableFormat(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 1, "TableFormat", s);
    }
    
    public synchronized void defaultUrlFormat() throws PropertyValueException, PropertyUnknownException {
        this.setUrlFormat(this.getDefaultProperty("UseUrlFormat"));
    }
    
    public synchronized boolean getUrlFormat() {
        return Boolean.valueOf(this.getProperty("UseUrlFormat"));
    }
    
    public synchronized String getUrlFormatRange() {
        return this.getPropertyRange("UseUrlFormat");
    }
    
    public synchronized void setUrlFormat(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setUrlFormat(b + "");
    }
    
    public synchronized void setUrlFormat(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 1, "UseUrlFormat", s);
    }
    
    public synchronized void defaultEcho() throws PropertyValueException, PropertyUnknownException {
        this.setEcho(this.getDefaultProperty("Echo"));
    }
    
    public synchronized boolean getEcho() {
        return Boolean.valueOf(this.getProperty("Echo"));
    }
    
    public synchronized String getEchoRange() {
        return this.getPropertyRange("Echo");
    }
    
    public synchronized void setEcho(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setEcho(b + "");
    }
    
    public synchronized void setEcho(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 1, "Echo", s);
    }
    
    public synchronized void defaultEchoAll() throws PropertyValueException, PropertyUnknownException {
        this.setEchoAll(this.getDefaultProperty("EchoAll"));
    }
    
    public synchronized boolean getEchoAll() {
        return Boolean.valueOf(this.getProperty("EchoAll"));
    }
    
    public synchronized String getEchoAllRange() {
        return this.getPropertyRange("EchoAll");
    }
    
    public synchronized void setEchoAll(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setEchoAll(b + "");
    }
    
    public synchronized void setEchoAll(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "EchoAll", s);
    }
    
    public synchronized void defaultEchoCmd() throws PropertyValueException, PropertyUnknownException {
        this.setEchoCmd(this.getDefaultProperty("EchoCmd"));
    }
    
    public synchronized boolean getEchoCmd() {
        return Boolean.valueOf(this.getProperty("EchoCmd"));
    }
    
    public synchronized String getEchoCmdRange() {
        return this.getPropertyRange("EchoCmd");
    }
    
    public synchronized void setEchoCmd(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setEchoCmd(b + "");
    }
    
    public synchronized void setEchoCmd(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "EchoCmd", s);
    }
    
    public synchronized void defaultEchoComments() throws PropertyValueException, PropertyUnknownException {
        this.setEchoComments(this.getDefaultProperty("EchoComments"));
    }
    
    public synchronized boolean getEchoComments() {
        return Boolean.valueOf(this.getProperty("EchoComments"));
    }
    
    public synchronized String getEchoCommentsRange() {
        return this.getPropertyRange("EchoComments");
    }
    
    public synchronized void setEchoComments(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setEchoComments(b + "");
    }
    
    public synchronized void setEchoComments(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "EchoComments", s);
    }
    
    public synchronized void defaultEchoSql() throws PropertyValueException, PropertyUnknownException {
        this.setEchoSql(this.getDefaultProperty("EchoSql"));
    }
    
    public synchronized boolean getEchoSql() {
        return Boolean.valueOf(this.getProperty("EchoSql"));
    }
    
    public synchronized String getEchoSqlRange() {
        return this.getPropertyRange("EchoSql");
    }
    
    public synchronized void setEchoSql(final boolean b) throws PropertyValueException, PropertyUnknownException {
        this.setEchoSql(b + "");
    }
    
    public synchronized void setEchoSql(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty(this.m_mode != 0, "EchoSql", s);
    }
    
    public synchronized void defaultConnectTimeout() throws PropertyValueException, PropertyUnknownException {
        this.setConnectTimeout(this.getDefaultProperty("ConnectTimeout"));
    }
    
    public synchronized int getConnectTimeout() {
        return Integer.valueOf(this.getProperty("ConnectTimeout"));
    }
    
    public synchronized String getConnectTimeoutRange() {
        return this.getPropertyRange("ConnectTimeout");
    }
    
    public synchronized void setConnectTimeout(final int i) throws PropertyValueException, PropertyUnknownException {
        this.setConnectTimeout(i + "");
    }
    
    public synchronized void setConnectTimeout(final String s) throws PropertyValueException, PropertyUnknownException {
        this.setProperty("ConnectTimeout", s);
    }
    
    private synchronized void load() {
        BufferedReader bufferedReader = null;
        try {
            int n = 0;
            bufferedReader = new BufferedReader(new FileReader(ISQLConstants.SQL_EXPLORER_PROPERTIES));
            SQLProperties.m_log.log(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "LocalPropertiesFileUsed", (Object)ISQLConstants.SQL_EXPLORER_PROPERTIES));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ++n;
                final String trim = line.trim();
                if (trim.length() == 0) {
                    continue;
                }
                if (trim.startsWith("#")) {
                    continue;
                }
                final int index = trim.indexOf("=");
                if (trim.length() < 2 || index <= 0) {
                    this.m_propertyWarningList.addElement(new PropertySyntaxException(trim, n));
                }
                else {
                    final String trim2 = trim.substring(0, index).trim();
                    final String trim3 = trim.substring(index + 1).trim();
                    final SQLProperty sqlProperty = (SQLProperty)this.get(trim2);
                    if (sqlProperty == null) {
                        this.m_propertyWarningList.addElement(new PropertyUnknownException(n, trim2));
                    }
                    else {
                        try {
                            sqlProperty.setValue(trim3);
                            this.put(trim2, sqlProperty);
                        }
                        catch (PropertyValueException ex) {
                            this.m_propertyWarningList.addElement(new PropertyValueException(ex.getMessage() + " in file " + ISQLConstants.SQL_EXPLORER_PROPERTIES));
                        }
                    }
                }
            }
            try {
                final String value = ((SQLProperty)this.get("Connect")).getValue();
                if (!value.equals("jdbc:jdbcprogress:T:localhost:<port>:<database>")) {
                    this.sqlDeconstructUrl(value);
                }
            }
            catch (Exception ex2) {
                this.m_propertyWarningList.addElement(new PropertyValueException(ex2.getMessage() + " in file " + ISQLConstants.SQL_EXPLORER_PROPERTIES));
            }
        }
        catch (FileNotFoundException ex5) {
            SQLProperties.m_log.log(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "Default properties will be used."));
        }
        catch (IOException ex3) {
            SQLProperties.m_log.log(ExceptionMessageAdapter.getMessage(7311593995036009193L, new Object[] { ex3.getMessage() }) + ISQLConstants.NEWLINE);
        }
        finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                }
                catch (Exception ex4) {
                    SQLProperties.m_log.log(ExceptionMessageAdapter.getMessage(7311593995036009354L, new Object[] { ex4.getMessage() }) + ISQLConstants.NEWLINE);
                }
            }
        }
    }
    
    public synchronized void save() throws IOException {
        Writer writer = null;
        final DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();
        try {
            final Enumeration<SQLProperty> elements = this.elements();
            writer = new BufferedWriter(new FileWriter(ISQLConstants.SQL_EXPLORER_PROPERTIES));
            writer.write("#" + ISQLConstants.SQL_EXPLORER_PROPERTIES);
            ((BufferedWriter)writer).newLine();
            writer.write("#" + dateTimeInstance.format(new Date()));
            ((BufferedWriter)writer).newLine();
            while (elements.hasMoreElements()) {
                final SQLProperty sqlProperty = elements.nextElement();
                writer.write(sqlProperty.getName() + "=" + sqlProperty.getValue());
                ((BufferedWriter)writer).newLine();
            }
        }
        finally {
            if (null != writer) {
                try {
                    ((BufferedWriter)writer).flush();
                    ((BufferedWriter)writer).close();
                }
                catch (Exception ex) {
                    SQLProperties.m_log.log(ExceptionMessageAdapter.getMessage(7311593995036009354L, new Object[] { ex.getMessage() }) + ISQLConstants.NEWLINE);
                }
            }
        }
    }
    
    public static void main(final String[] array) {
    }
    
    static {
        SQLProperties.m_log = SQLExplorerLog.get();
    }
    
    public static class PropertyValueException extends Exception
    {
        public PropertyValueException(final String s, final String s2) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "InvalidPropertyValue", (Object)s2, (Object)s));
        }
        
        public PropertyValueException(final String message) {
            super(message);
        }
    }
    
    public static class PropertyUnknownException extends Exception
    {
        public PropertyUnknownException(final int value, final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "UnknownProperty", (Object)s, (Object)new Integer(value), (Object)ISQLConstants.SQL_EXPLORER_PROPERTIES));
        }
    }
    
    public static class PropertySyntaxException extends Exception
    {
        public PropertySyntaxException(final String s, final int value) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "InvalidPropertySyntax", (Object)s, (Object)new Integer(value), (Object)ISQLConstants.SQL_EXPLORER_PROPERTIES));
        }
    }
}
