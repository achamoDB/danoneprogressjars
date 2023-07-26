// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.progress.common.rmiregistry.ICallable;

public class SQLConnectServer implements ICallable
{
    String m_sqlUrl;
    String m_sqlUser;
    String m_sqlPassword;
    String m_sqlDriverUrl;
    SQLException connectException;
    
    public SQLConnectServer(final String sqlUrl, final String sqlUser, final String sqlPassword, final String sqlDriverUrl) {
        this.m_sqlUrl = null;
        this.m_sqlUser = null;
        this.m_sqlPassword = null;
        this.m_sqlDriverUrl = null;
        this.connectException = null;
        this.m_sqlUrl = sqlUrl;
        this.m_sqlUser = sqlUser;
        this.m_sqlPassword = sqlPassword;
        this.m_sqlDriverUrl = sqlDriverUrl;
    }
    
    public SQLException getConnectException() {
        return this.connectException;
    }
    
    public Object call(final Object o) throws RemoteException {
        Connection connection;
        try {
            if (this.m_sqlDriverUrl == null) {
                connection = DriverManager.getConnection(this.m_sqlUrl, this.m_sqlUser, this.m_sqlPassword);
            }
            else {
                connection = DriverManager.getConnection(this.m_sqlDriverUrl);
            }
        }
        catch (SQLException connectException) {
            this.connectException = connectException;
            connection = null;
        }
        return connection;
    }
}
