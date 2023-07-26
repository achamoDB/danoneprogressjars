// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.chimera.common.Tools;
import com.progress.chimera.ChimeraException;
import java.util.Enumeration;
import java.util.Vector;

public class LoadJDBCDriver implements ISQLConstants
{
    private Vector m_JDBCDrivers;
    static SQLExplorerLog m_log;
    
    public Enumeration getDrivers() {
        return this.m_JDBCDrivers.elements();
    }
    
    public LoadJDBCDriver() {
        this.m_JDBCDrivers = new Vector();
        final String property = System.getProperty("SQLExplorer.JdbcDriver");
        if (property != null) {
            this.m_JDBCDrivers.addElement(property);
        }
        this.m_JDBCDrivers.addElement("com.ddtek.jdbc.openedge.OpenEdgeDriver");
    }
    
    public void loadDrivers() throws ChimeraException {
        final Enumeration drivers = this.getDrivers();
        int n = 0;
        while (drivers.hasMoreElements()) {
            final String s = drivers.nextElement();
            try {
                this.loadDriver(s);
            }
            catch (Exception ex) {
                ++n;
            }
        }
        if (n == this.m_JDBCDrivers.size()) {
            final StringBuffer sb = new StringBuffer();
            sb.append("### No JDBC drivers loaded ###" + ISQLConstants.NEWLINE);
            final Enumeration<String> elements = (Enumeration<String>)this.m_JDBCDrivers.elements();
            while (elements.hasMoreElements()) {
                sb.append(elements.nextElement() + ISQLConstants.NEWLINE);
            }
            LoadJDBCDriver.m_log.log(sb.toString());
            throw new ChimeraException("No JDBC drivers loaded.");
        }
    }
    
    public void loadDriver(final String s) throws Exception {
        LoadJDBCDriver.m_log.log("Loading JDBC driver " + s + ".");
        try {
            Class.forName(s);
        }
        catch (Exception ex) {
            Tools.px("### Exception loading JDBC driver. ###", ex);
            throw ex;
        }
    }
    
    static {
        LoadJDBCDriver.m_log = SQLExplorerLog.get();
    }
}
