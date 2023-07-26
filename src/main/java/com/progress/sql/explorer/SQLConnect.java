// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.common.exception.ProException;
import com.progress.common.rmiregistry.TimedOut;
import java.util.Enumeration;
import com.progress.chimera.ChimeraException;
import com.progress.common.rmiregistry.ICallable;
import com.progress.common.rmiregistry.TryIt;
import java.sql.DriverManager;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import com.progress.message.exMsg;

public class SQLConnect implements ISQLConstants, exMsg
{
    LoadJDBCDriver m_loadJDBCDrivers;
    SQLProperties m_SQLProps;
    Connection m_connection;
    Statement m_statement;
    static SQLExplorerLog m_log;
    
    public SQLConnect(final SQLProperties sqlProps) {
        this.m_loadJDBCDrivers = null;
        this.m_SQLProps = null;
        this.m_connection = null;
        this.m_statement = null;
        this.m_SQLProps = sqlProps;
    }
    
    public Connection getConnection() {
        return this.m_connection;
    }
    
    public void setConnection(final Connection connection) {
        try {
            if (connection != this.m_connection && this.m_connection != null && !this.m_connection.isClosed()) {
                if (this.m_SQLProps.getAutoCommit()) {
                    this.m_connection.commit();
                }
                else {
                    this.m_connection.rollback();
                }
                this.m_connection.close();
            }
        }
        catch (SQLException ex) {}
        this.m_connection = connection;
        this.m_SQLProps.setConnection(this.m_connection);
    }
    
    public Statement getStatement() {
        return this.m_statement;
    }
    
    public void closeStatement() throws SQLException {
        if (this.m_statement != null) {
            this.m_statement.close();
            this.m_statement = null;
        }
    }
    
    public CallableStatement createCallableStatement(final String s) throws SQLException {
        this.closeStatement();
        return (CallableStatement)(this.m_statement = this.getConnection().prepareCall(s));
    }
    
    public Statement createStatement() throws SQLException {
        this.closeStatement();
        return this.m_statement = this.getConnection().createStatement();
    }
    
    public void closeConnection() throws SQLException {
        this.closeStatement();
        final Connection connection;
        if ((connection = this.getConnection()) != null) {
            if (!connection.isClosed()) {
                if (this.m_SQLProps.getAutoCommit()) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
                connection.close();
            }
            this.setConnection(null);
        }
    }
    
    public boolean dbConnected() {
        boolean b = false;
        try {
            final Connection connection = this.getConnection();
            if (connection != null) {
                b = !connection.isClosed();
            }
        }
        catch (SQLException ex) {}
        return b;
    }
    
    public String getUrl() {
        String url;
        try {
            url = this.getConnection().getMetaData().getURL();
        }
        catch (SQLException ex) {
            url = "";
        }
        return url;
    }
    
    protected void openConnection() throws SQLException, DriverLoadException, DriverProtocolException, DriverNameException, NetworkProtocolException, PortOrServiceException, UrlException, TimedOut, Exception, Throwable {
        final String message = ExceptionMessageAdapter.getMessage(7311593995036009176L, new Object[] { this.m_SQLProps.getSqlUser(), this.m_SQLProps.getSqlUrl() });
        SQLConnect.m_log.log(message);
        System.out.println(message);
        try {
            this.closeConnection();
            if (this.m_loadJDBCDrivers == null) {
                this.m_loadJDBCDrivers = new LoadJDBCDriver();
            }
            this.m_loadJDBCDrivers.loadDrivers();
            if (this.m_SQLProps.getSqlDebug()) {
                DriverManager.setLogStream(new PrintStream(new FileOutputStream(ISQLConstants.SQL_EXPLORER_LOGFILE, true)));
            }
            if (DriverManager.getLoginTimeout() != 0) {
                DriverManager.setLoginTimeout(this.m_SQLProps.getConnectTimeout());
            }
            final SQLConnectServer sqlConnectServer = new SQLConnectServer(this.m_SQLProps.getSqlUrl(), this.m_SQLProps.getSqlUser(), this.m_SQLProps.getSqlPassword(), this.m_SQLProps.getSqlDriverUrl());
            final Connection connection = (Connection)new TryIt(sqlConnectServer, new Object()).callIt(this.m_SQLProps.getConnectTimeout() * 1000);
            if (connection != null) {
                this.setConnection(connection);
                connection.setTransactionIsolation(this.m_SQLProps.getTransactionIsolation());
                connection.setAutoCommit(this.m_SQLProps.getAutoCommit());
            }
            else {
                final SQLException connectException = sqlConnectServer.getConnectException();
                if (connectException != null) {
                    throw connectException;
                }
            }
        }
        catch (ChimeraException ex) {
            final Enumeration drivers = this.m_loadJDBCDrivers.getDrivers();
            String string = "";
            while (drivers.hasMoreElements()) {
                string = string + ISQLConstants.NEWLINE + "    " + drivers.nextElement();
            }
            throw new DriverLoadException(string);
        }
    }
    
    static {
        SQLConnect.m_log = SQLExplorerLog.get();
    }
    
    public static class UrlException extends ProException
    {
        public UrlException(final String s) {
            super(ExceptionMessageAdapter.getMessage(7311593995036009239L, new Object[] { s, ISQLConstants.NEWLINE }), new Object[0]);
        }
    }
    
    public static class DriverLoadException extends ProException
    {
        public DriverLoadException(final String s) {
            super(ExceptionMessageAdapter.getMessage(7311593995036009537L, new Object[] { s }), new Object[0]);
        }
    }
    
    public static class DriverProtocolException extends ProException
    {
        public DriverProtocolException(final String s) {
            super(ExceptionMessageAdapter.getMessage(7311593995036013534L, new Object[] { ISQLConstants.NEWLINE, s }), new Object[0]);
        }
    }
    
    public static class DriverNameException extends ProException
    {
        public DriverNameException(final String s) {
            super(ExceptionMessageAdapter.getMessage(7311593995036013535L, new Object[] { ISQLConstants.NEWLINE, s }), new Object[0]);
        }
    }
    
    public static class NetworkProtocolException extends ProException
    {
        public NetworkProtocolException(final String s) {
            super(ExceptionMessageAdapter.getMessage(7311593995036009242L, new Object[] { ISQLConstants.NEWLINE, s }), new Object[0]);
        }
    }
    
    public static class PortOrServiceException extends ProException
    {
        public PortOrServiceException() {
            super(ExceptionMessageAdapter.getMessage(7311593995036009243L, new Object[0]), new Object[0]);
        }
    }
}
