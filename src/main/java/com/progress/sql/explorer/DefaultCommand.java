// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.international.resources.ProgressResources;
import java.sql.SQLException;
import java.io.IOException;

public class DefaultCommand implements ISQLConstants, ISQLProperties
{
    protected boolean m_charMode;
    protected String m_commandText;
    protected String m_csqualifier;
    protected String m_ciqualifier;
    protected String m_runFile;
    protected String[] m_args;
    protected SQLLogging m_SQLLogging;
    protected SQLProperties m_SQLProps;
    protected SQLUtil m_SQLUtil;
    protected int m_currentPropertyIdx;
    protected String m_currentPropertyName;
    protected String m_unknownResetOptions;
    static SQLExplorerLog m_log;
    
    public DefaultCommand(final String[] array, final SQLProperties sqlProps, final SQLLogging sqlLogging, final boolean charMode) {
        this.m_charMode = true;
        this.m_commandText = null;
        this.m_csqualifier = null;
        this.m_ciqualifier = null;
        this.m_runFile = null;
        this.m_args = null;
        this.m_SQLLogging = null;
        this.m_SQLProps = null;
        this.m_SQLUtil = new SQLUtil();
        this.m_currentPropertyIdx = 0;
        this.m_currentPropertyName = "";
        this.m_unknownResetOptions = "";
        this.m_SQLProps = sqlProps;
        this.m_SQLLogging = sqlLogging;
        this.m_charMode = charMode;
        if (array.length > 0) {
            this.setCommand(array[0].toLowerCase());
        }
        else {
            this.setCommand("");
        }
        this.m_csqualifier = "";
        this.m_ciqualifier = "";
        if (array.length > 1) {
            this.m_args = new String[array.length - 1];
            for (int i = 1; i < array.length; ++i) {
                this.m_args[i - 1] = array[i];
                this.m_csqualifier = this.m_csqualifier + " " + array[i];
                this.m_ciqualifier = this.m_ciqualifier + " " + array[i].toLowerCase();
            }
            this.m_csqualifier = this.m_csqualifier.trim();
            this.m_ciqualifier = this.m_ciqualifier.trim();
        }
        else {
            this.m_args = new String[0];
        }
    }
    
    private int getPropertiesIdx(final String s) {
        final int length = ISQLProperties.m_properties.length;
        int n = 0;
        final String lowerCase = s.toLowerCase();
        for (int n2 = 1; n == 0 && n2 < length; ++n2) {
            final String lowerCase2 = ISQLProperties.m_properties[n2][1].toLowerCase();
            final String lowerCase3 = ISQLProperties.m_properties[n2][2].toLowerCase();
            if (lowerCase.startsWith(lowerCase2) && lowerCase3.startsWith(lowerCase)) {
                n = n2;
            }
        }
        return n;
    }
    
    private void setCommandIdx(final int currentPropertyIdx) {
        this.m_currentPropertyIdx = currentPropertyIdx;
    }
    
    public int getCommandIdx() {
        return this.m_currentPropertyIdx;
    }
    
    private void setCommandName(final String currentPropertyName) {
        this.m_currentPropertyName = currentPropertyName;
    }
    
    public String getCommandName() {
        return this.m_currentPropertyName;
    }
    
    private int getPropertyIdx(final int n) {
        return Integer.valueOf(ISQLProperties.m_properties[n][0]);
    }
    
    private String getPropertyShortName(final int n) {
        return ISQLProperties.m_properties[n][1];
    }
    
    private String getPropertyFullName(final int n) {
        return ISQLProperties.m_properties[n][2];
    }
    
    public int executeCommand() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException, SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, IOException, SQLException, SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException {
        int n = 1;
        int propertyIdx = 0;
        int propertiesIdx = 0;
        try {
            propertiesIdx = this.getPropertiesIdx(this.m_commandText);
            switch (propertyIdx = this.getPropertyIdx(propertiesIdx)) {
                case 16: {
                    return 0;
                }
                case 25: {
                    return 0;
                }
                case 7: {
                    return 0;
                }
                case 1: {
                    n = this.executeAutocommit();
                    break;
                }
                case 2: {
                    n = this.executeColumnWidthLimit();
                    break;
                }
                case 3: {
                    n = this.executeColumnWidthMin();
                    break;
                }
                case 5: {
                    n = this.executeConnect();
                    break;
                }
                case 6: {
                    n = this.executeDisableWarnings();
                    break;
                }
                case 8: {
                    n = (this.m_charMode ? this.executeEchoCmd() : this.executeEcho());
                    break;
                }
                case 9: {
                    n = this.executeEchoAll();
                    break;
                }
                case 10: {
                    n = this.executeEchoCmd();
                    break;
                }
                case 11: {
                    n = this.executeEchoSql();
                    break;
                }
                case 12: {
                    n = this.executeEchoComments();
                    break;
                }
                case 4: {
                    n = this.executeConnectTimeout();
                    break;
                }
                case 13: {
                    n = this.executeFetchLimit();
                    break;
                }
                case 14: {
                    n = this.executeHasColumnLimit();
                    break;
                }
                case 15: {
                    n = this.executeHasFetchLimit();
                    break;
                }
                case 17: {
                    n = this.executeLogfile();
                    break;
                }
                case 18: {
                    n = this.executeLogging();
                    break;
                }
                case 19: {
                    n = this.executePageLimit();
                    break;
                }
                case 20: {
                    n = this.executePager();
                    break;
                }
                case 21: {
                    n = this.executeReportFormat();
                    break;
                }
                case 22: {
                    n = this.executeReset();
                    break;
                }
                case 23: {
                    n = this.executeResultSetLimit();
                    break;
                }
                case 24: {
                    n = this.executeRun();
                    break;
                }
                case 26: {
                    n = this.executeSqlVerbose();
                    break;
                }
                case 27: {
                    n = this.executeTableFormat();
                    break;
                }
                case 28: {
                    n = this.executeTransactionIsolation();
                    break;
                }
                case 29: {
                    n = this.executeUseUrlFormat();
                    break;
                }
                default: {
                    n = 1;
                    break;
                }
            }
        }
        finally {
            this.setCommandIdx(propertyIdx);
            this.setCommandName(this.getPropertyFullName(propertiesIdx));
        }
        return n;
    }
    
    public String getHelpString() {
        return this.getHelp(new String[0]);
    }
    
    public String getHelp() {
        return this.getHelp(this.m_args);
    }
    
    public String getHelp(final String[] array) {
        final int length = ISQLProperties.m_properties.length;
        final String[] array2 = new String[length + 1];
        String str;
        String string = str = ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "Available options include:");
        for (int i = 1; i < length; ++i) {
            final String propertyFullName = this.getPropertyFullName(i);
            final int propertyIdx = this.getPropertyIdx(i);
            array2[propertyIdx] = this.m_SQLProps.getPropertyValue(propertyFullName, 1);
            if (array2[propertyIdx].length() != 0) {
                string = string + ISQLConstants.NEWLINE + array2[propertyIdx];
            }
        }
        if (array.length > 0) {
            for (int j = 0; j < array.length; ++j) {
                final String s = array[j];
                final int propertyIdx2 = this.getPropertyIdx(this.getPropertiesIdx(s));
                if (propertyIdx2 == 0) {
                    str = str + ISQLConstants.NEWLINE + "    @" + s + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", ": Unknown property");
                }
                else if (array2[propertyIdx2].length() == 0) {
                    str = str + ISQLConstants.NEWLINE + "    @" + s + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", ": Unknown property");
                }
                else {
                    str = str + ISQLConstants.NEWLINE + array2[propertyIdx2];
                }
            }
            string = str;
        }
        return string;
    }
    
    public String showOptions() {
        return this.showOptions(this.m_args);
    }
    
    public String showOptions(final String[] array) {
        final int length = ISQLProperties.m_properties.length;
        final String[] array2 = new String[length + 1];
        String str;
        String string = str = ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "Options values are:");
        for (int i = 1; i < length; ++i) {
            final String propertyFullName = this.getPropertyFullName(i);
            final int propertyIdx = this.getPropertyIdx(i);
            array2[propertyIdx] = this.m_SQLProps.getPropertyValue(propertyFullName, 2);
            if (array2[propertyIdx].length() != 0) {
                string = string + ISQLConstants.NEWLINE + "    " + propertyFullName + " " + array2[propertyIdx];
            }
        }
        if (array.length > 0) {
            for (int j = 0; j < array.length; ++j) {
                final String s = array[j];
                final int propertiesIdx = this.getPropertiesIdx(s);
                final int propertyIdx2 = this.getPropertyIdx(propertiesIdx);
                if (propertyIdx2 == 0) {
                    str = str + ISQLConstants.NEWLINE + "    " + s + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", ": Unknown property");
                }
                else if (array2[propertyIdx2].length() == 0) {
                    str = str + ISQLConstants.NEWLINE + "    " + s + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", ": Unknown property");
                }
                else {
                    str = str + ISQLConstants.NEWLINE + "    " + this.getPropertyFullName(propertiesIdx) + " " + array2[propertyIdx2];
                }
            }
            string = str;
        }
        return string;
    }
    
    private int executeReset() throws SQLException, IOException, SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        if (this.m_args.length > 0) {
            for (int i = 0; i < this.m_args.length; ++i) {
                final String lowerCase = this.m_args[i].toLowerCase();
                switch (this.getPropertyIdx(this.getPropertiesIdx(lowerCase))) {
                    case 1: {
                        this.m_SQLProps.defaultAutoCommit();
                        break;
                    }
                    case 2: {
                        this.m_SQLProps.defaultColumnWidthLimit();
                        break;
                    }
                    case 3: {
                        this.m_SQLProps.defaultColumnWidthMinimum();
                        break;
                    }
                    case 4: {
                        this.m_SQLProps.defaultConnectTimeout();
                        break;
                    }
                    case 6: {
                        this.m_SQLProps.defaultDisableWarnings();
                        break;
                    }
                    case 8: {
                        if (this.m_charMode) {
                            this.m_SQLProps.defaultEchoCmd();
                            break;
                        }
                        this.m_SQLProps.defaultEcho();
                        break;
                    }
                    case 9: {
                        this.m_SQLProps.defaultEchoAll();
                        break;
                    }
                    case 10: {
                        this.m_SQLProps.defaultEchoCmd();
                        break;
                    }
                    case 11: {
                        this.m_SQLProps.defaultEchoSql();
                        break;
                    }
                    case 12: {
                        this.m_SQLProps.defaultEchoComments();
                        break;
                    }
                    case 14: {
                        this.m_SQLProps.defaultHasColumnLimit();
                    }
                    case 13: {
                        this.m_SQLProps.defaultFetchLimit();
                        break;
                    }
                    case 15: {
                        this.m_SQLProps.defaultHasFetchLimit();
                        break;
                    }
                    case 17: {
                        this.m_SQLProps.defaultLogfile();
                        break;
                    }
                    case 18: {
                        this.m_SQLProps.defaultLogging();
                        break;
                    }
                    case 19: {
                        this.m_SQLProps.defaultPageLimit();
                        break;
                    }
                    case 20: {
                        this.m_SQLProps.defaultPager();
                        break;
                    }
                    case 21: {
                        this.m_SQLProps.defaultReportFormat();
                        break;
                    }
                    case 23: {
                        this.m_SQLProps.defaultResultSetLimit();
                        break;
                    }
                    case 26: {
                        this.m_SQLProps.defaultSqlVerbose();
                        break;
                    }
                    case 27: {
                        this.m_SQLProps.defaultTableFormat();
                        break;
                    }
                    case 28: {
                        this.m_SQLProps.defaultTransactionIsolation();
                        break;
                    }
                    case 29: {
                        this.m_SQLProps.defaultUrlFormat();
                        break;
                    }
                    default: {
                        if (!lowerCase.equals("options")) {
                            this.m_unknownResetOptions = this.m_unknownResetOptions + ISQLConstants.NEWLINE + "    " + lowerCase + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", ": Unknown property");
                            break;
                        }
                        this.m_SQLProps.defaultCommon();
                        if (this.m_charMode) {
                            this.m_SQLProps.defaultChar();
                            break;
                        }
                        this.m_SQLProps.defaultGUI();
                        break;
                    }
                }
            }
        }
        else {
            this.m_SQLProps.defaultCommon();
            if (this.m_charMode) {
                this.m_SQLProps.defaultChar();
            }
            else {
                this.m_SQLProps.defaultGUI();
            }
        }
        return 0;
    }
    
    public String getResetString() {
        return this.m_unknownResetOptions;
    }
    
    protected void setCommand(final String s) {
        this.m_commandText = s.toLowerCase();
    }
    
    public String toString() {
        final int propertiesIdx = this.getPropertiesIdx(this.m_commandText);
        final String str = (this.getPropertyIdx(propertiesIdx) == 0) ? this.m_commandText : this.getPropertyFullName(propertiesIdx);
        if (this.m_csqualifier.length() > 0) {
            return str + " " + this.m_csqualifier;
        }
        return this.m_commandText;
    }
    
    private int executeEchoCmd() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setEchoCmd(this.m_ciqualifier);
        return 0;
    }
    
    private int executeEchoAll() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setEchoAll(this.m_ciqualifier);
        return 0;
    }
    
    private int executeEchoSql() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setEchoSql(this.m_ciqualifier);
        return 0;
    }
    
    private int executeEchoComments() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setEchoComments(this.m_ciqualifier);
        return 0;
    }
    
    private int executeEcho() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setEcho(this.m_ciqualifier);
        return 0;
    }
    
    private int executeConnect() throws SQLConnect.DriverProtocolException, SQLConnect.DriverNameException, SQLConnect.NetworkProtocolException, SQLConnect.PortOrServiceException, SQLConnect.UrlException, SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setSqlUrl(this.m_csqualifier);
        return 0;
    }
    
    private int executeConnectTimeout() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setConnectTimeout(this.m_ciqualifier);
        return 0;
    }
    
    private int executeLogging() throws IOException, SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setLogging(this.m_ciqualifier);
        return 0;
    }
    
    private int executeLogfile() throws SQLLogging.InvalidFileException, SQLLogging.FileNotReadableException, SQLLogging.FileNotWritableException, IOException, SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setLogfile(this.m_csqualifier);
        return 0;
    }
    
    private int executeReportFormat() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setReportFormat(this.m_csqualifier);
        return 0;
    }
    
    private int executeResultSetLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setResultSetLimit(this.m_csqualifier);
        return 0;
    }
    
    public void setRunFile(final String runFile) {
        this.m_runFile = runFile;
    }
    
    public String getRunFile() {
        if (this.m_runFile == null) {
            this.m_runFile = this.m_args[0];
        }
        return this.m_runFile;
    }
    
    private int executeRun() throws SQLProperties.PropertyValueException {
        DefaultCommand.m_log.log("Running file " + this.m_args[0] + ".");
        this.setRunFile(this.m_args[0]);
        return 0;
    }
    
    private int executeAutocommit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException, SQLException {
        this.m_SQLProps.setAutoCommit(this.m_ciqualifier);
        return 0;
    }
    
    private int executeDisableWarnings() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setDisableWarnings(this.m_ciqualifier);
        return 0;
    }
    
    private int executeSqlVerbose() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setSqlVerbose(this.m_ciqualifier);
        return 0;
    }
    
    private int executeUseUrlFormat() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setUrlFormat(this.m_ciqualifier);
        return 0;
    }
    
    private int executeFetchLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setFetchLimit(this.m_ciqualifier);
        return 0;
    }
    
    private int executeHasColumnLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setHasColumnLimit(this.m_ciqualifier);
        return 0;
    }
    
    private int executeHasFetchLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setHasFetchLimit(this.m_ciqualifier);
        return 0;
    }
    
    private int executePager() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setPager(this.m_ciqualifier);
        return 0;
    }
    
    private int executePageLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setPageLimit(this.m_ciqualifier);
        return 0;
    }
    
    private int executeColumnWidthLimit() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setColumnWidthLimit(this.m_ciqualifier);
        return 0;
    }
    
    private int executeColumnWidthMin() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setColumnWidthMinimum(this.m_ciqualifier);
        return 0;
    }
    
    private int executeTableFormat() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException {
        this.m_SQLProps.setTableFormat(this.m_ciqualifier);
        return 0;
    }
    
    private int executeTransactionIsolation() throws SQLProperties.PropertyValueException, SQLProperties.PropertyUnknownException, SQLException {
        try {
            this.m_SQLProps.setTransactionIsolationIdx(Integer.valueOf(this.m_ciqualifier));
        }
        catch (NumberFormatException ex) {
            this.m_SQLProps.setTransactionIsolationText(this.m_csqualifier);
        }
        return 0;
    }
    
    static {
        DefaultCommand.m_log = SQLExplorerLog.get();
    }
}
