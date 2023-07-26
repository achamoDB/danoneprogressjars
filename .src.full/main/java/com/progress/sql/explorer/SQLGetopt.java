// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.common.util.Getopt;

public class SQLGetopt implements ISQLConstants, ISQLProperties
{
    static final int OPT_COMMAND = 10;
    static final int OPT_DATABASE = 20;
    static final int OPT_DEBUG = 30;
    static final int OPT_HELP = 40;
    static final int OPT_HOST = 50;
    static final int OPT_INFILE = 60;
    static final int OPT_OUTFILE = 70;
    static final int OPT_PASSWORD = 80;
    static final int OPT_SERVICE = 90;
    static final int OPT_URL = 100;
    static final int OPT_USER = 110;
    static final int OPT_VERBOSE = 120;
    static final int OPT_CHAR = 130;
    static final int OPT_CODEPAGE = 140;
    static final int OPT_SSL = 150;
    static final int OPT_NSR = 160;
    static final int OPT_NHV = 170;
    static final int OPT_DRIVERURL = 180;
    static final int UNKOPT = 63;
    private String sqlDatabase;
    private String sqlHost;
    private String sqlService;
    private String sqlCommand;
    private String sqlInFile;
    private String sqlOutFile;
    private String sqlInitialUrl;
    private String driverUrl;
    private boolean sqlHelp;
    private String sqlCodePage;
    private String inputArgs;
    private String invalidArgs;
    private SQLProperties m_SQLProps;
    
    public String getInputArgs() {
        return this.inputArgs;
    }
    
    public String getInvalidArgs() {
        return this.invalidArgs;
    }
    
    public String getDriverUrl() {
        return this.driverUrl;
    }
    
    public void setdriverUrl(final String sqlCodePage) {
        this.sqlCodePage = sqlCodePage;
    }
    
    public String getSqlCodePage() {
        return this.sqlCodePage;
    }
    
    public void setSqlCodePage(final String sqlCodePage) {
        this.sqlCodePage = sqlCodePage;
    }
    
    public String getSqlInitialUrl() {
        return this.sqlInitialUrl;
    }
    
    public String getSqlCommand() {
        return this.sqlCommand;
    }
    
    public void setSqlCommand(final String sqlCommand) {
        this.sqlCommand = sqlCommand;
    }
    
    public boolean getSqlHelp() {
        return this.sqlHelp;
    }
    
    public void setSqlHelp(final boolean sqlHelp) {
        this.sqlHelp = sqlHelp;
    }
    
    public String getSqlInFile() {
        return this.sqlInFile;
    }
    
    public void setSqlInFile(final String sqlInFile) {
        this.sqlInFile = sqlInFile;
    }
    
    public String getSqlOutFile() {
        return this.sqlOutFile;
    }
    
    public void setSqlOutFile(final String sqlOutFile) {
        this.sqlOutFile = sqlOutFile;
    }
    
    public SQLGetopt(final String[] array, final SQLProperties sqlProps) throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException, SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException {
        this.sqlDatabase = new String();
        this.sqlHost = "localhost";
        this.sqlService = new String();
        this.sqlCommand = new String();
        this.sqlInFile = new String();
        this.sqlOutFile = new String();
        this.sqlInitialUrl = new String();
        this.driverUrl = null;
        this.sqlHelp = false;
        this.sqlCodePage = new String();
        this.inputArgs = new String();
        this.invalidArgs = new String();
        this.m_SQLProps = null;
        this.m_SQLProps = sqlProps;
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("command:", 10), new Getopt.GetoptList("db:", 20), new Getopt.GetoptList("sqldebug", 30), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("H:", 50), new Getopt.GetoptList("infile:", 60), new Getopt.GetoptList("outfile:", 70), new Getopt.GetoptList("password:", 80), new Getopt.GetoptList("S:", 90), new Getopt.GetoptList("url:", 100), new Getopt.GetoptList("user:", 110), new Getopt.GetoptList("sqlverbose", 120), new Getopt.GetoptList("char", 130), new Getopt.GetoptList("codepage:", 140), new Getopt.GetoptList("driverUrl:", 180), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(false);
        boolean b = false;
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 140: {
                    this.setSqlCodePage(getopt.getOptArg());
                    continue;
                }
                case 10: {
                    this.setSqlCommand(getopt.getOptArg().replace(';', ' '));
                    continue;
                }
                case 20: {
                    this.sqlDatabase = getopt.getOptArg();
                    this.m_SQLProps.setSqlDatabase(this.sqlDatabase);
                    continue;
                }
                case 30: {
                    this.m_SQLProps.setSqlDebug(true);
                    continue;
                }
                case 40: {
                    this.setSqlHelp(true);
                    continue;
                }
                case 50: {
                    this.sqlHost = getopt.getOptArg();
                    this.m_SQLProps.setSqlHost(this.sqlHost);
                    continue;
                }
                case 60: {
                    this.setSqlInFile(getopt.getOptArg());
                    continue;
                }
                case 70: {
                    this.setSqlOutFile(getopt.getOptArg());
                    continue;
                }
                case 80: {
                    this.m_SQLProps.setSqlPassword(getopt.getOptArg());
                    continue;
                }
                case 90: {
                    this.sqlService = getopt.getOptArg();
                    this.m_SQLProps.setSqlService(this.sqlService);
                    continue;
                }
                case 100: {
                    this.sqlInitialUrl = getopt.getOptArg();
                    this.m_SQLProps.setSqlUrl(this.sqlInitialUrl);
                    continue;
                }
                case 180: {
                    this.driverUrl = getopt.getOptArg();
                    this.sqlInitialUrl = this.driverUrl;
                    continue;
                }
                case 110: {
                    this.m_SQLProps.setSqlUser(getopt.getOptArg());
                    continue;
                }
                case 120: {
                    b = true;
                }
                case 130: {
                    continue;
                }
                case 63: {
                    if (!array[getopt.getOptInd()].startsWith("-") && this.sqlDatabase.length() == 0) {
                        this.sqlDatabase = getopt.getOptArg();
                        this.m_SQLProps.setSqlDatabase(this.sqlDatabase);
                        continue;
                    }
                    this.invalidArgs = this.invalidArgs + getopt.getOptArg() + " ";
                    continue;
                }
            }
        }
        if (this.sqlDatabase.length() != 0) {
            final int lastIndex = this.sqlDatabase.lastIndexOf(ISQLConstants.DIR_SEPARATOR);
            if (lastIndex > 0) {
                this.sqlDatabase = this.sqlDatabase.substring(lastIndex + 1);
                this.m_SQLProps.setSqlDatabase(this.sqlDatabase);
            }
        }
        if (this.sqlHost.length() != 0 && this.sqlService.length() != 0 && this.sqlDatabase.length() != 0) {
            this.m_SQLProps.setSqlUrl(this.sqlHost, this.sqlService, this.sqlDatabase, this.m_SQLProps.getSqlSSL(), this.m_SQLProps.getSqlSSLSR(), this.m_SQLProps.getSqlSSLHV());
            this.sqlInitialUrl = this.m_SQLProps.getSqlUrl();
        }
        else if (this.sqlInitialUrl.length() != 0) {
            this.m_SQLProps.setSqlUrl(this.sqlInitialUrl);
        }
        final boolean sqlVerbose = this.m_SQLProps.getSqlVerbose();
        if (b && !sqlVerbose) {
            this.m_SQLProps.setSqlVerbose(true);
        }
        else if (!b && sqlVerbose) {
            this.m_SQLProps.setSqlVerbose(false);
        }
        for (int i = 0; i < array.length; ++i) {
            this.inputArgs = this.inputArgs + array[i] + " ";
            if (array[i].startsWith("-p")) {
                this.inputArgs += "xxx ";
                ++i;
            }
        }
    }
}
