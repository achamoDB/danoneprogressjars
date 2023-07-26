// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.chimera.common.Tools;
import java.util.Date;
import com.progress.international.resources.ProgressResources;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;

public class SQLLogging implements ISQLConstants
{
    static SQLExplorerLog m_log;
    String m_logDirName;
    String m_logFullFileName;
    String m_logFileName;
    boolean m_logSet;
    BufferedWriter m_logWriter;
    
    public SQLLogging() {
        this(ISQLConstants.SQL_EXPLORER_SESSION, false);
    }
    
    public SQLLogging(final String s) {
        this(s, false);
    }
    
    public SQLLogging(final boolean b) {
        this(ISQLConstants.SQL_EXPLORER_SESSION, b);
    }
    
    public SQLLogging(final String fullLogFile, final boolean logging) {
        this.m_logDirName = ISQLConstants.WORK_DIR;
        this.m_logFullFileName = ISQLConstants.SQL_EXPLORER_SESSION;
        this.m_logFileName = this.m_logFullFileName.substring(this.m_logFullFileName.lastIndexOf(ISQLConstants.DIR_SEPARATOR) + 1);
        this.m_logSet = false;
        this.m_logWriter = null;
        this.setLogging(logging);
        try {
            this.setFullLogFile(fullLogFile);
        }
        catch (InvalidFileException ex) {
            SQLLogging.m_log.log(ex.toString());
        }
        catch (FileNotReadableException ex2) {
            SQLLogging.m_log.log(ex2.toString());
        }
        catch (FileNotWritableException ex3) {
            SQLLogging.m_log.log(ex3.toString());
        }
    }
    
    public void setLogging(final boolean logSet) {
        this.m_logSet = logSet;
    }
    
    public boolean getLogging() {
        return this.m_logSet;
    }
    
    public void setLogFile(final String logFileName) {
        this.m_logFileName = logFileName;
    }
    
    public String getLogFile() {
        return this.m_logFileName;
    }
    
    public String getFullLogFile() {
        return this.m_logFullFileName;
    }
    
    public boolean isValidLogFile(final String pathname) throws InvalidFileException, FileNotReadableException, FileNotWritableException {
        return this.isValidLogFile(pathname, new File(pathname));
    }
    
    public boolean isValidLogFile(final String s, final File file) throws InvalidFileException, FileNotReadableException, FileNotWritableException {
        OutputStreamWriter outputStreamWriter = null;
        boolean b = false;
        try {
            if (!file.exists()) {
                outputStreamWriter = new FileWriter(file);
                outputStreamWriter.close();
            }
            if (!file.isFile()) {
                throw new InvalidFileException(s);
            }
            if (!file.canRead()) {
                throw new FileNotReadableException(s);
            }
            if (!file.canWrite()) {
                throw new FileNotWritableException(s);
            }
            b = true;
        }
        catch (IOException ex) {
            throw new InvalidFileException(s);
        }
        finally {
            if (outputStreamWriter != null) {
                file.delete();
            }
        }
        return b;
    }
    
    public void setFullLogFile(final String pathname) throws InvalidFileException, FileNotReadableException, FileNotWritableException {
        this.setFullLogFile(pathname, new File(pathname));
    }
    
    public void setFullLogFile(final String s, final File file) throws InvalidFileException, FileNotReadableException, FileNotWritableException {
        if (this.isValidLogFile(s, file)) {
            final String absolutePath = file.getAbsolutePath();
            final String name = file.getName();
            final int lastIndex = absolutePath.lastIndexOf(ISQLConstants.DIR_SEPARATOR);
            if (lastIndex == -1) {
                throw new InvalidFileException(s);
            }
            this.setDir(absolutePath.substring(0, lastIndex));
            this.setLogFile(name);
            this.m_logFullFileName = absolutePath;
        }
    }
    
    private void setDir(final String logDirName) {
        this.m_logDirName = logDirName;
    }
    
    public void openLogSessionWriter() throws InvalidFileException, FileNotReadableException, FileNotWritableException, IOException {
        final String fullLogFile = this.getFullLogFile();
        this.closeLogSessionWriter();
        if (this.m_logSet && this.isValidLogFile(fullLogFile)) {
            this.m_logWriter = new BufferedWriter(new FileWriter(fullLogFile, true));
        }
    }
    
    protected void closeLogSessionWriter() throws IOException {
        if (this.m_logWriter != null) {
            try {
                this.m_logWriter.close();
            }
            catch (IOException ex) {}
        }
        this.m_logWriter = null;
    }
    
    public void logHeader() {
        try {
            this.m_logWriter.write(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "=== Start ===") + new Date());
            this.m_logWriter.newLine();
        }
        catch (IOException obj) {
            Tools.px(obj, "### Exception " + obj + "received while " + "attempting to write header to log file " + this.m_logFullFileName + ". ###");
        }
    }
    
    public void logFooter() {
        try {
            this.m_logWriter.write(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "=== End ===") + new Date());
            this.m_logWriter.newLine();
            this.m_logWriter.newLine();
            this.m_logWriter.flush();
        }
        catch (IOException obj) {
            Tools.px(obj, "### Exception " + obj + "received while " + "attempting to write footer to log file " + this.m_logFullFileName + ". ###");
        }
    }
    
    public void logMessage(final String str) {
        if (this.m_logWriter == null) {
            return;
        }
        try {
            this.m_logWriter.write(str);
            this.m_logWriter.newLine();
        }
        catch (IOException obj) {
            Tools.px(obj, "### Exception " + obj + "received while " + "attempting to write message to log file " + this.m_logFullFileName + ". ###");
        }
    }
    
    public void logResults(final SQLStringResults sqlStringResults) {
        if (this.m_logWriter == null) {
            return;
        }
        try {
            if (sqlStringResults.getSQLExceptionList() != null) {
                this.m_logWriter.newLine();
                this.m_logWriter.write(sqlStringResults.getSQLExceptionString());
                this.m_logWriter.newLine();
            }
            if (sqlStringResults.getSQLWarningList() != null) {
                this.m_logWriter.newLine();
                this.m_logWriter.write(sqlStringResults.getSQLWarningString());
                this.m_logWriter.newLine();
            }
            if (sqlStringResults.isResultSet()) {
                this.m_logWriter.newLine();
                sqlStringResults.printOutput(this.m_logWriter);
            }
        }
        catch (IOException obj) {
            Tools.px(obj, "### Exception " + obj + "received while " + "attempting to write results to log file " + this.m_logFullFileName + ". ###");
        }
    }
    
    static {
        SQLLogging.m_log = SQLExplorerLog.get();
    }
    
    public static class InvalidFileException extends Exception
    {
        public InvalidFileException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "InvalidFileName", (Object)s));
        }
    }
    
    public static class FileNotReadableException extends Exception
    {
        public FileNotReadableException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "FileNotReadable", (Object)s));
        }
    }
    
    public static class FileNotWritableException extends Exception
    {
        public FileNotWritableException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "FileNotWritable", (Object)s));
        }
    }
}
