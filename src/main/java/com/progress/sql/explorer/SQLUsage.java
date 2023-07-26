// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLUsage implements ISQLConstants
{
    static String m_usage;
    
    public static String help() {
        if (SQLUsage.m_usage.length() > 0) {
            return SQLUsage.m_usage;
        }
        if (ISQLConstants.WINDOWS_PLATFORM) {
            SQLUsage.m_usage = SQLUsage.m_usage + "Usage:  sqlexp [-modeoptions] [-connectoptions] [-generaloptions]" + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "where mode options include:" + ISQLConstants.NEWLINE + "     -char               Optional argument.  Default is GUI mode." + ISQLConstants.NEWLINE;
        }
        else {
            SQLUsage.m_usage = SQLUsage.m_usage + "Usage:  sqlexp [-connectoptions] [-generaloptions]" + ISQLConstants.NEWLINE;
        }
        return SQLUsage.m_usage = SQLUsage.m_usage + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "WHERE connect options include one of the following:" + ISQLConstants.NEWLINE + "     -user <user>        Optional argument.  Default is current user." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -password <passwd>  Optional argument.  User password." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "                             --- EITHER --- " + ISQLConstants.NEWLINE + "     -url <url>          Mandatory argument.  Format is:" + ISQLConstants.NEWLINE + "                             jdbc:datadirect:openedge://<host>:<port>;databaseName=<dbname>" + ISQLConstants.NEWLINE + "                             jdbc:datadirect:openedge://<host>:-1;databaseName=<dbname>;serviceName=<service>" + ISQLConstants.NEWLINE + "                         The -url option may be specified in place of" + ISQLConstants.NEWLINE + "                         the -db, -S, and -H options." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "                             ----- OR -----" + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -db <database>      Mandatory argument.  -db option need not preceed" + ISQLConstants.NEWLINE + "                         the database specification." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -S <service|port>   Mandatory argument.  Service name or port number" + ISQLConstants.NEWLINE + "                         may be specified as an argument to the -S option." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -H <host>           Optional argument.  Default is localhost." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "                             ----- OR -----" + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -driverUrl <url>    Mandatory argument.  Format is: " + ISQLConstants.NEWLINE + "                             \"jdbc:datadirect:openedge://<host>:<port>;databaseName=<dbName>;User=<user>;Password=<password>\"" + ISQLConstants.NEWLINE + "                          The driverUrl must be enclosed in quotes.  All fields listed above must be present." + ISQLConstants.NEWLINE + "                          In addition, driver connection parameters can also be added to the url." + ISQLConstants.NEWLINE + "                          For example, SSL options are now driver connection parameters." + ISQLConstants.NEWLINE + "                          To enable encyption without authentication, " + ISQLConstants.NEWLINE + "                          the -driverUrl option would be as follows:" + ISQLConstants.NEWLINE + "                             \"jdbc:datadirect:openedge://<host>:<port>;databaseName=<dbName>;User=<user>;Password=<password;EncryptionMethod=ssl;validateServerCertificate=false\"" + ISQLConstants.NEWLINE + "                          See SQL documentation for other JDBC driver connection parameters." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "WHERE general options include:" + ISQLConstants.NEWLINE + "     -infile <infile>    Optional argument.  Character mode only." + ISQLConstants.NEWLINE + "                         The SQL Explorer executes the commands in the" + ISQLConstants.NEWLINE + "                         input file then terminates." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -outfile <outfile>  Optional argument.  Character mode only." + ISQLConstants.NEWLINE + "                         Redirects SQL command output from standard" + ISQLConstants.NEWLINE + "                         output to named file." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -command <command>  Optional argument.  Character mode only." + ISQLConstants.NEWLINE + "                         The SQL Explorer executes the specified SQL" + ISQLConstants.NEWLINE + "                         command then terminates." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE + "     -sqlverbose         Optional argument.  Character mode only." + ISQLConstants.NEWLINE + "                         Beginning and ending time stamps" + ISQLConstants.NEWLINE + "                         are printed along with number of rows and columns" + ISQLConstants.NEWLINE + "                         fetched, and the fetch limit." + ISQLConstants.NEWLINE + ISQLConstants.NEWLINE;
    }
    
    static {
        SQLUsage.m_usage = "";
    }
}
