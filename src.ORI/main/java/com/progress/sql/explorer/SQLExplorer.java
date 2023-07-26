// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.chimera.util.Misc;
import java.util.Date;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import com.progress.chimera.common.Tools;
import com.progress.common.rmiregistry.TimedOut;
import java.util.Enumeration;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.progress.common.log.LogSystem;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import com.progress.message.exMsg;

public class SQLExplorer implements ISQLConstants, exMsg
{
    protected static String m_invocationCommand;
    SQLConnect m_SQLConnect;
    SQLExecShell m_SQLExec;
    SQLProperties m_SQLProps;
    SQLLogging m_SQLLogging;
    SQLProcessor m_SQLProcessor;
    Thread m_SQLThread;
    BufferedReader m_inputStream;
    BufferedWriter m_outputStream;
    boolean m_debug;
    static final String SEPARATOR = "===";
    static SQLExplorerLog m_log;
    private int m_nbrOfStatements;
    private int m_nbrOfErrors;
    private int m_nbrOfWarnings;
    private SQLGetopt m_SQLOpts;
    private SQLUtil m_SQLUtil;
    
    SQLExplorer(final String[] array) {
        this.m_SQLConnect = null;
        this.m_SQLExec = null;
        this.m_SQLProps = null;
        this.m_SQLLogging = null;
        this.m_SQLProcessor = null;
        this.m_SQLThread = null;
        this.m_inputStream = null;
        this.m_outputStream = null;
        this.m_debug = false;
        this.m_nbrOfStatements = 0;
        this.m_nbrOfErrors = 0;
        this.m_nbrOfWarnings = 0;
        this.m_SQLOpts = null;
        this.m_SQLUtil = new SQLUtil();
        int status = 1;
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.SQLMsgBundle");
        try {
            if (!this.m_SQLUtil.isWritable(ISQLConstants.SQL_EXPLORER_LOGFILE)) {
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009485L, new Object[] { ISQLConstants.SQL_EXPLORER_LOGFILE }));
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009488L, new Object[] { ISQLConstants.SQL_EXPLORER_LOGFILE }));
                System.exit(1);
            }
            if (!this.m_SQLUtil.isWritable(ISQLConstants.SQL_EXPLORER_EXCPFILE)) {
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009485L, new Object[] { ISQLConstants.SQL_EXPLORER_EXCPFILE }));
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009488L, new Object[] { ISQLConstants.SQL_EXPLORER_EXCPFILE }));
                System.exit(1);
            }
            if (this.m_SQLUtil.exists(ISQLConstants.SQL_EXPLORER_PROPERTIES)) {
                if (!this.m_SQLUtil.isReadable(ISQLConstants.SQL_EXPLORER_PROPERTIES)) {
                    System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009486L, new Object[] { ISQLConstants.SQL_EXPLORER_PROPERTIES }));
                }
                if (!this.m_SQLUtil.isWritable(ISQLConstants.SQL_EXPLORER_PROPERTIES)) {
                    System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009485L, new Object[] { ISQLConstants.SQL_EXPLORER_PROPERTIES }));
                }
            }
            try {
                LogSystem.setDefaultLogFileName(ISQLConstants.SQL_EXPLORER_LOGFILE);
                this.m_SQLLogging = new SQLLogging();
                this.m_SQLProps = new SQLProperties(0, this.m_SQLLogging);
                this.m_SQLOpts = new SQLGetopt(array, this.m_SQLProps);
                this.m_SQLProps.setSqlDriverUrl(this.m_SQLOpts.getDriverUrl());
                this.m_SQLExec = new SQLExecShell();
                this.m_debug = this.m_SQLProps.getSqlDebug();
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
            if (!this.m_SQLProps.getDisableWarnings()) {
                final Enumeration<Exception> elements = (Enumeration<Exception>)this.m_SQLProps.getPropertyWarningList().elements();
                while (elements.hasMoreElements()) {
                    System.out.println(progressResources.getTranString("Property ignored", (Object)elements.nextElement().getMessage()));
                }
            }
            this.m_SQLProps.clearPropertyWarningList();
            try {
                this.m_SQLLogging.setFullLogFile(this.m_SQLProps.getLogfile());
                if (this.m_SQLProps.getLogging()) {
                    this.m_SQLLogging.setLogging(true);
                    this.m_SQLLogging.openLogSessionWriter();
                }
            }
            catch (Exception ex2) {
                try {
                    this.m_SQLProps.setLogging(false);
                    this.m_SQLProps.setLogfile(this.m_SQLLogging.getFullLogFile());
                }
                catch (Exception ex5) {
                    System.err.println(ex2.getMessage());
                }
                System.err.println(ex2.getMessage());
                System.err.println(progressResources.getTranString("Setting logfile and logging to false ", (Object)this.m_SQLLogging.getFullLogFile()));
            }
            try {
                if (this.m_SQLProps.getSqlVerbose()) {
                    this.m_SQLProps.setSqlVerbose(true);
                }
            }
            catch (SQLProperties.PropertyValueException ex6) {}
            catch (SQLProperties.PropertyUnknownException ex7) {}
            if (this.m_SQLOpts.getInvalidArgs().length() > 0) {
                System.out.println(progressResources.getTranString("Invalid arguments specified", (Object)this.m_SQLOpts.getInvalidArgs()));
                System.out.println(progressResources.getTranString("For additional help"));
                System.exit(1);
            }
            SQLExplorer.m_log.log("=== SQLExplorer starting. ===");
            if (this.m_SQLOpts.getInputArgs().length() > 0) {
                SQLExplorer.m_log.log("### ARGS:    " + this.m_SQLOpts.getInputArgs());
            }
            if (this.m_SQLOpts.getSqlHelp()) {
                System.out.println(SQLUsage.help());
                System.exit(0);
            }
            if (this.m_SQLOpts.getSqlInitialUrl().length() == 0) {
                System.err.println(progressResources.getTranString("You must specify either a URL or a database, port and host"));
                System.out.println(progressResources.getTranString("For additional help"));
                System.exit(1);
            }
            this.openOutputStreams();
            if (this.startSQLProcessor()) {
                final boolean openCommandInputStream;
                if (openCommandInputStream = this.openCommandInputStream()) {
                    this.processCommands(false);
                }
                final boolean openFileInputStream;
                if (openFileInputStream = this.openFileInputStream()) {
                    this.processCommands(false);
                }
                if (!openCommandInputStream && !openFileInputStream) {
                    this.openStdInputStream();
                    this.processCommands(true);
                }
                this.closeSQLProcessor();
                this.closeInputStreams();
                status = 0;
            }
            this.closeOutputStreams();
        }
        catch (FileNotFoundException ex3) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009197L, new Object[] { ex3.getMessage() }) + ISQLConstants.NEWLINE);
        }
        catch (IOException ex4) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009194L, new Object[] { ex4.getMessage() }));
        }
        finally {
            try {
                this.m_SQLProps.save();
            }
            catch (IOException ex8) {
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009485L, new Object[] { ISQLConstants.SQL_EXPLORER_PROPERTIES }));
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009487L, new Object[] { ISQLConstants.SQL_EXPLORER_PROPERTIES }));
            }
            SQLExplorer.m_log.log("=== SQLExplorer ending. ===");
            System.exit(status);
        }
    }
    
    protected boolean startSQLProcessor() {
        boolean b = false;
        try {
            this.m_SQLProcessor = new SQLProcessor(this.m_SQLProps);
            this.m_SQLConnect = this.m_SQLProcessor.getSQLConnect();
            (this.m_SQLThread = new Thread(this.m_SQLProcessor)).start();
            Thread.yield();
            this.m_SQLProcessor.waitForOutput();
            if (this.m_SQLProcessor.getException() != null) {
                throw this.m_SQLProcessor.getException();
            }
            b = true;
        }
        catch (SQLConnect.DriverProtocolException ex) {
            System.err.println(ex.getMessage());
        }
        catch (SQLConnect.DriverNameException ex2) {
            System.err.println(ex2.getMessage());
        }
        catch (SQLConnect.NetworkProtocolException ex3) {
            System.err.println(ex3.getMessage());
        }
        catch (SQLConnect.PortOrServiceException ex4) {
            System.err.println(ex4.getMessage());
        }
        catch (SQLConnect.UrlException ex5) {
            System.err.println(ex5.getMessage());
        }
        catch (TimedOut timedOut) {
            System.err.println(timedOut.getMessage());
        }
        catch (Throwable t) {
            final String message = t.getMessage();
            if (message == null || message.equals("")) {
                ExceptionMessageAdapter.getMessage(7311593995036009191L, new Object[] { this.m_SQLProps.getSqlUrl() });
            }
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { t.getMessage() }));
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009206L, new Object[] { ISQLConstants.SQL_EXPLORER_LOGFILE, ISQLConstants.SQL_EXPLORER_EXCPFILE }));
            Tools.px(t, "### Connect stack trace. ###");
        }
        return b;
    }
    
    protected boolean closeSQLProcessor() {
        boolean b = false;
        try {
            if (this.m_SQLProcessor != null) {
                if (this.m_SQLConnect != null) {
                    this.m_SQLConnect.closeConnection();
                    this.m_SQLConnect = null;
                }
                this.m_SQLProcessor.setDone(true);
                this.m_SQLProcessor.wakeUp();
                this.m_SQLThread.join();
                this.m_SQLProcessor = null;
                this.m_SQLThread = null;
                b = true;
            }
        }
        catch (InterruptedException ex) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex.getMessage() }));
        }
        catch (SQLException ex2) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex2.getMessage() }));
        }
        return b;
    }
    
    private boolean openFileInputStream() throws IOException {
        boolean b = true;
        if (this.m_SQLOpts.getSqlInFile().length() > 0) {
            this.setInputStream(this.m_SQLUtil.getReaderWithI18NSupport(this.m_SQLOpts.getSqlInFile(), this.m_SQLOpts.getSqlCodePage()));
        }
        else {
            b = false;
        }
        return b;
    }
    
    private boolean openCommandInputStream() throws IOException {
        boolean b = true;
        if (this.m_SQLOpts.getSqlCommand().length() > 0) {
            this.setInputStream(new BufferedReader(new StringReader(this.m_SQLOpts.getSqlCommand())));
        }
        else {
            b = false;
        }
        return b;
    }
    
    private boolean openStdInputStream() throws IOException {
        this.setInputStream(new BufferedReader(new InputStreamReader(System.in)));
        return true;
    }
    
    private void closeInputStreams() throws IOException {
        try {
            final BufferedReader inputStream = this.getInputStream();
            if (inputStream != null) {
                inputStream.close();
                this.setInputStream(null);
            }
        }
        finally {}
    }
    
    private void closeOutputStreams() throws IOException {
        try {
            final BufferedWriter outputStream = this.getOutputStream();
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
                this.setOutputStream(null);
            }
        }
        finally {}
    }
    
    private void openOutputStreams() throws IOException {
        try {
            final String sqlCodePage = this.m_SQLOpts.getSqlCodePage();
            int n = (sqlCodePage.length() <= 0) ? 1 : 0;
            BufferedWriter outputStream = null;
            OutputStream out;
            if (this.m_SQLOpts.getSqlOutFile().length() > 0) {
                out = new FileOutputStream(this.m_SQLOpts.getSqlOutFile(), true);
            }
            else {
                out = System.out;
            }
            if (n == 0) {
                try {
                    outputStream = new BufferedWriter(new OutputStreamWriter(out, sqlCodePage));
                }
                catch (UnsupportedEncodingException ex) {
                    n = 1;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    n = 1;
                }
            }
            if (n != 0) {
                final OutputStreamWriter out2 = new OutputStreamWriter(out);
                outputStream = new BufferedWriter(out2);
                final String encoding = out2.getEncoding();
                if (sqlCodePage.length() > 0) {
                    System.out.println(ExceptionMessageAdapter.getMessage(7311593995036009539L, new Object[] { sqlCodePage, encoding }));
                }
            }
            this.setOutputStream(outputStream);
        }
        finally {}
    }
    
    private BufferedWriter getOutputStream() {
        return this.m_outputStream;
    }
    
    private void setOutputStream(final BufferedWriter outputStream) {
        this.m_outputStream = outputStream;
    }
    
    private BufferedReader getInputStream() {
        return this.m_inputStream;
    }
    
    private void setInputStream(final BufferedReader inputStream) {
        this.m_inputStream = inputStream;
    }
    
    private boolean doDisconnect(final boolean b) {
        final BufferedReader inputStream = this.getInputStream();
        final BufferedWriter outputStream = this.getOutputStream();
        boolean closeSQLProcessor = false;
        String lowerCase = "y";
        try {
            if (b) {
                final String message = ExceptionMessageAdapter.getMessage(7311593995036009201L, new Object[] { this.m_SQLProps.getSqlUser(), (this.m_SQLConnect != null) ? this.m_SQLConnect.getUrl() : "" });
                do {
                    outputStream.write(message + " (y/n) ");
                    outputStream.flush();
                    lowerCase = inputStream.readLine().toLowerCase();
                } while (!lowerCase.startsWith("y") && !lowerCase.startsWith("n"));
            }
            if (lowerCase.startsWith("y")) {
                closeSQLProcessor = this.closeSQLProcessor();
            }
        }
        catch (IOException ex) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex.getMessage() }));
        }
        return closeSQLProcessor;
    }
    
    private boolean doConnection(final boolean b) {
        boolean startSQLProcessor = false;
        boolean dbConnected = false;
        boolean b2 = false;
        try {
            dbConnected = this.dbConnected();
            b2 = (!dbConnected || this.doDisconnect(b));
            if (b2) {
                startSQLProcessor = this.startSQLProcessor();
            }
        }
        finally {
            if (dbConnected && !b2) {
                this.m_SQLProps.resetSqlUrl();
            }
        }
        return startSQLProcessor;
    }
    
    private String processSpecialCommand(final String s, final boolean b) {
        String s2 = "";
        String s3 = "";
        final BufferedWriter outputStream = this.getOutputStream();
        final BufferedReader inputStream = this.getInputStream();
        String string = null;
        try {
            if (this.m_SQLUtil.isAtCommand(s)) {
                final String[] convertStringToArray = this.m_SQLUtil.convertStringToArray(s);
                final DefaultCommand defaultCommand = new DefaultCommand(convertStringToArray, this.m_SQLProps, this.m_SQLLogging, true);
                String s4 = (convertStringToArray.length == 0) ? "" : defaultCommand.toString();
                int doConnection = 0;
                try {
                    final int executeCommand = defaultCommand.executeCommand();
                    final int commandIdx = defaultCommand.getCommandIdx();
                    if (executeCommand == 0) {
                        doConnection = 1;
                    }
                    if (doConnection != 0) {
                        s3 = ExceptionMessageAdapter.getMessage(7311593995036009188L, new Object[] { s4 });
                        if (commandIdx == 24) {
                            final String runFile = defaultCommand.getRunFile();
                            if (this.m_SQLProps.getSqlVerbose()) {
                                outputStream.write(ExceptionMessageAdapter.getMessage(7311593995036009184L, new Object[] { runFile + ".." + ISQLConstants.NEWLINE }));
                                outputStream.flush();
                            }
                            this.setInputStream(this.m_SQLUtil.getReaderWithI18NSupport(runFile, this.m_SQLOpts.getSqlCodePage()));
                            this.processCommands();
                        }
                        else if (commandIdx == 16) {
                            s2 = defaultCommand.getHelp();
                        }
                        else if (commandIdx == 25) {
                            s2 = defaultCommand.showOptions();
                        }
                        else if (commandIdx == 22) {
                            s2 = defaultCommand.getResetString();
                        }
                        else if (commandIdx == 7) {
                            if (this.dbConnected()) {
                                if (this.doDisconnect(b)) {
                                    s2 = ExceptionMessageAdapter.getMessage(7311593995036009178L, new Object[] { this.m_SQLProps.getSqlUser(), this.m_SQLProps.getSqlUrl() });
                                }
                                else {
                                    s2 = ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "No action performed");
                                }
                            }
                            else {
                                doConnection = 0;
                                s2 = ExceptionMessageAdapter.getMessage(7311593995036009190L, null);
                            }
                        }
                        else if (commandIdx == 5) {
                            doConnection = (this.doConnection(b) ? 1 : 0);
                        }
                        if (doConnection == 0) {
                            s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s4 });
                        }
                    }
                    else {
                        if (s4 != "") {
                            s4 += ": ";
                        }
                        s4 += "Command not recognized";
                        s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s4 });
                        s2 = defaultCommand.getHelpString();
                    }
                }
                catch (FileNotFoundException ex) {
                    s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s4 });
                    s3 = s3 + ISQLConstants.NEWLINE + ExceptionMessageAdapter.getMessage(7311593995036009197L, new Object[] { ex.getMessage() });
                }
                catch (IOException ex2) {
                    s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s4 });
                    s3 = s3 + ISQLConstants.NEWLINE + ExceptionMessageAdapter.getMessage(7311593995036009194L, new Object[] { ex2.getMessage() });
                }
                catch (Exception ex3) {
                    s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s4 });
                    s3 = s3 + ISQLConstants.NEWLINE + ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex3.getMessage() });
                }
            }
            else {
                s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { s });
            }
        }
        catch (FileNotFoundException ex4) {
            s3 = ExceptionMessageAdapter.getMessage(7311593995036009197L, new Object[] { ex4.getMessage() });
        }
        catch (Exception ex5) {
            s3 = ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex5.getMessage() });
        }
        finally {
            if (!s2.equals("")) {
                s2 = s2 + ";" + ISQLConstants.NEWLINE;
            }
            string = s2 + s3 + ISQLConstants.NEWLINE;
            this.setInputStream(inputStream);
        }
        return string;
    }
    
    private boolean dbConnected() {
        boolean dbConnected = false;
        if (this.m_SQLConnect != null && this.m_SQLProcessor != null && this.m_SQLThread != null && this.m_SQLThread.isAlive()) {
            dbConnected = this.m_SQLConnect.dbConnected();
        }
        return dbConnected;
    }
    
    private SQLStringResults processSqlCommand(final boolean b, final String str) throws IOException {
        final BufferedReader inputStream = this.getInputStream();
        final BufferedWriter outputStream = this.getOutputStream();
        if (!this.dbConnected()) {
            final String message = ExceptionMessageAdapter.getMessage(7311593995036009190L, null);
            if (this.m_SQLLogging.getLogging()) {
                this.m_SQLLogging.logMessage(message);
            }
            outputStream.write(message + ISQLConstants.NEWLINE);
            return null;
        }
        final Thread sqlThread = this.m_SQLThread;
        Thread.yield();
        final StringReader in = new StringReader(str + ISQLConstants.NEWLINE);
        final BufferedReader commandInput = new BufferedReader(in);
        this.m_SQLProcessor.setCommandInput(commandInput);
        final Thread sqlThread2 = this.m_SQLThread;
        Thread.yield();
        this.m_SQLProcessor.wakeUp();
        final long currentTimeMillis = System.currentTimeMillis();
        Thread.yield();
        ++this.m_nbrOfStatements;
        while (!this.m_SQLProcessor.waitForOutput(4000)) {
            final Throwable exception = this.m_SQLProcessor.getException();
            if (exception != null) {
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { exception.getMessage() + ISQLConstants.NEWLINE }));
            }
            else {
                if (this.m_SQLThread.isAlive()) {
                    continue;
                }
                System.err.println(ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Fatal error encountered. Exiting program."));
                System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009206L, new Object[] { ISQLConstants.SQL_EXPLORER_LOGFILE, ISQLConstants.SQL_EXPLORER_EXCPFILE }));
                Tools.px(exception, "### Thread death stack trace. ###");
                System.exit(1);
            }
        }
        final long currentTimeMillis2 = System.currentTimeMillis();
        Thread.yield();
        if (this.m_SQLProps.getSqlVerbose()) {
            outputStream.write("=== START " + new Date() + " " + "===" + ISQLConstants.NEWLINE);
        }
        SQLStringResults sqlResults = this.m_SQLProcessor.getSQLResults();
        if (sqlResults == null) {
            outputStream.write("Null results." + ISQLConstants.NEWLINE);
        }
        else {
            if (this.m_SQLLogging.getLogging()) {
                this.m_SQLLogging.logResults(sqlResults);
            }
            if (this.m_SQLProps.getSqlVerbose()) {
                outputStream.write(sqlResults.getStatementText() + ";" + ISQLConstants.NEWLINE);
                outputStream.newLine();
            }
            if (b) {
                sqlResults.generateOutput(inputStream, outputStream);
            }
            else {
                sqlResults.generateOutput(null, outputStream);
            }
            this.m_nbrOfErrors += sqlResults.getSQLExceptionCount();
            outputStream.write(sqlResults.getSQLExceptionString());
            this.m_nbrOfWarnings += sqlResults.getSQLWarningCount();
            outputStream.write(sqlResults.getSQLWarningString());
            if (sqlResults.isResultSet() && this.m_SQLProps.getSqlVerbose()) {
                if (this.m_SQLProps.getHasFetchLimit()) {
                    outputStream.write(ExceptionMessageAdapter.getMessage(7311593995036009181L, new Object[] { new Long(sqlResults.getFetchedCount()), new Long(this.m_SQLProps.getFetchLimit()), new Integer(sqlResults.getColumnCount()) }) + ISQLConstants.NEWLINE);
                }
                else {
                    outputStream.write(ExceptionMessageAdapter.getMessage(7311593995036009181L, new Object[] { new Long(sqlResults.getFetchedCount()), "none", new Integer(sqlResults.getColumnCount()) }) + ISQLConstants.NEWLINE);
                }
                outputStream.newLine();
            }
            try {
                sqlResults.closeResultSet();
                sqlResults = null;
            }
            catch (SQLException ex) {
                outputStream.write(ex.getMessage() + ISQLConstants.NEWLINE);
            }
        }
        if (this.m_SQLProps.getSqlVerbose()) {
            final long lng = (currentTimeMillis2 - currentTimeMillis) / 1000L;
            outputStream.write("=== END " + new Date() + "  Processing time = " + lng + "." + Misc.fill(currentTimeMillis2 - currentTimeMillis - lng + "", 3, true, false, "000") + " secs " + "===" + ISQLConstants.NEWLINE);
            outputStream.newLine();
        }
        outputStream.flush();
        in.close();
        commandInput.close();
        if (!this.dbConnected()) {
            final String message2 = ExceptionMessageAdapter.getMessage(7311593995036009190L, null);
            if (this.m_SQLLogging.getLogging()) {
                this.m_SQLLogging.logMessage(message2);
            }
            outputStream.write(message2 + ISQLConstants.NEWLINE);
            return null;
        }
        return sqlResults;
    }
    
    private void processCommands() {
        this.processCommands(false);
    }
    
    private void processCommands(final boolean b) {
        final BufferedReader inputStream = this.getInputStream();
        final BufferedWriter outputStream = this.getOutputStream();
        final BufferedWriter bufferedWriter = b ? outputStream : null;
        try {
            final SQLTextExtractor sqlTextExtractor = new SQLTextExtractor(inputStream, bufferedWriter);
            String s = sqlTextExtractor.getNextStatement();
            while (s != null) {
                String s2 = "";
                if (this.m_SQLProps.getEchoAll()) {
                    outputStream.write(sqlTextExtractor.getUnfilteredInput());
                    outputStream.flush();
                }
                else {
                    if (this.m_SQLProps.getEchoComments()) {
                        outputStream.write(sqlTextExtractor.getUnfilteredComments());
                        outputStream.flush();
                    }
                    if (this.m_SQLProps.getEchoCmd()) {
                        outputStream.write(s);
                        outputStream.flush();
                    }
                }
                if (this.m_SQLLogging.getLogging()) {
                    this.m_SQLLogging.logHeader();
                    this.m_SQLLogging.logMessage(s);
                }
                if (s.length() == 0) {
                    continue;
                }
                if (this.m_SQLUtil.isAtCommand(s)) {
                    s2 = this.processSpecialCommand(s, b);
                    outputStream.write(s2);
                    outputStream.flush();
                }
                else if (this.m_SQLUtil.isShellCommand(s)) {
                    if (ISQLConstants.HAS_SHELL_SUPPORT) {
                        s2 = this.m_SQLExec.runEscapeToShell(0, this.m_SQLUtil.getShellCommand(s));
                    }
                    else {
                        s2 = ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "Escape to shell functionality not supported on this platform") + ISQLConstants.NEWLINE;
                    }
                    outputStream.write(s2);
                    outputStream.flush();
                }
                else {
                    if (this.m_SQLProps.getEchoSql() && !this.m_SQLProps.getEchoCmd() && !this.m_SQLProps.getEchoAll()) {
                        outputStream.write(s);
                        outputStream.flush();
                    }
                    this.processSqlCommand(b, s);
                }
                if (this.m_SQLLogging.getLogging()) {
                    this.m_SQLLogging.logMessage(s2);
                    this.m_SQLLogging.logFooter();
                }
                s = sqlTextExtractor.getNextStatement();
            }
            if (this.m_SQLProps.getEchoAll()) {
                outputStream.write(sqlTextExtractor.getUnfilteredInput());
                outputStream.flush();
            }
            else if (this.m_SQLProps.getEchoComments()) {
                outputStream.write(sqlTextExtractor.getUnfilteredComments());
                outputStream.flush();
            }
            if (this.m_SQLProps.getSqlVerbose()) {
                outputStream.write("===");
                outputStream.write(" " + ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "SUMMARY: SQLStatements", (Object)new Integer(this.m_nbrOfStatements), (Object)new Integer(this.m_nbrOfErrors), (Object)new Integer(this.m_nbrOfWarnings)));
                outputStream.write("===" + ISQLConstants.NEWLINE);
                outputStream.newLine();
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009197L, new Object[] { ex.getMessage() + ISQLConstants.NEWLINE }));
        }
        catch (IOException ex2) {
            System.err.println(ExceptionMessageAdapter.getMessage(7311593995036009189L, new Object[] { ex2.getMessage() + ISQLConstants.NEWLINE }));
        }
    }
    
    public static void main(final String[] array) {
        Tools.setExcpFileName(ISQLConstants.SQL_EXPLORER_EXCPFILE);
        final SQLExplorer sqlExplorer = new SQLExplorer(array);
    }
    
    static {
        SQLExplorer.m_invocationCommand = "java com.progress.sql.explorer.SQLExplorer ";
        SQLExplorer.m_log = SQLExplorerLog.get();
    }
}
