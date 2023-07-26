// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.FilenameFilter;
import com.progress.common.ehnlog.ILogEvntHandler;
import java.io.File;
import com.progress.common.ehnlog.IAppLogger;
import java.text.DecimalFormat;

public class ubFileWatchDog extends Thread implements ubConstants, IWatchable
{
    static final int MAX_SEQUENCE_NUMBER = 999999;
    static final int SEQNUMSIZE = 6;
    static final int NUMLOGFILES_LOWER = 2;
    private static DecimalFormat fmt1;
    private int m_sequencenum_pos;
    private int m_watchdogInterval;
    private int m_logThreshold;
    private int m_numLogFiles;
    private boolean m_appendMode;
    private int m_numFilesOnSystem;
    private int m_nextNumSequence;
    private String m_currLogFileName;
    private String m_rolledFiles;
    private StringBuffer m_nextLogFileName;
    private StringBuffer m_fileNameToDelete;
    private ubWatchDog m_fileWatchdog;
    private IAppLogger m_log;
    private File m_File;
    private ubProperties m_properties;
    private ILogEvntHandler event_handler;
    private static final int LOG_THRESHOLD_MIN = 500000;
    private static final int LOG_THRESHOLD_MAX = 2147438647;
    
    public ubFileWatchDog(final ubProperties properties, final IAppLogger log) {
        this.m_properties = properties;
        this.m_logThreshold = this.m_properties.getValueAsInt("srvrLogThreshold");
        this.m_numLogFiles = this.m_properties.getValueAsInt("srvrNumLogFiles");
        this.m_appendMode = (this.m_properties.getValueAsInt("srvrLogAppend") == 1);
        this.m_numFilesOnSystem = 0;
        this.m_nextNumSequence = 1;
        this.m_nextLogFileName = null;
        this.m_fileNameToDelete = null;
        this.m_watchdogInterval = this.m_properties.getValueAsInt("srvrLogWatchdogInterval") * 1000;
        this.m_File = null;
        this.m_log = log;
        if (this.m_logThreshold >= 500000 && this.m_logThreshold <= 2147438647) {
            this.processExistingFiles();
            this.m_properties.adjustServerLogFileValue(this.m_currLogFileName);
        }
    }
    
    public void run() {
        if (this.m_logThreshold >= 500000) {
            if (this.m_log.ifLogBasic(2L, 1)) {
                this.m_log.logBasic(1, "Starting fileWatchDog thread ...");
            }
            (this.m_nextLogFileName = new StringBuffer(this.m_currLogFileName)).replace(this.m_sequencenum_pos, this.m_sequencenum_pos + 6, ubFileWatchDog.fmt1.format(this.m_nextNumSequence));
            this.m_properties.initServerLog(this.m_log, false);
            this.m_File = new File(this.m_currLogFileName);
            this.deleteNextLogFile();
            this.checkFileSize();
            this.deleteLogFiles();
            this.event_handler = new LogEvntHandler(this.m_properties, this.m_log);
            ((LogEvntHandler)this.event_handler).setMainLog(false);
            (this.m_fileWatchdog = new ubWatchDog("fileWatchdog", this, this.m_watchdogInterval, 6, this.m_log)).start();
        }
    }
    
    public void watchEvent() {
        this.checkFileSize();
        this.deleteLogFiles();
    }
    
    public void close() {
        if (this.m_logThreshold >= 500000) {
            if (this.m_fileWatchdog != null) {
                this.m_fileWatchdog.setInterval(0L);
            }
            this.deleteLogFiles();
        }
        this.event_handler = null;
    }
    
    public int getCurrentSequenceNumber() {
        return this.m_nextNumSequence - 1;
    }
    
    private void checkFileSize() {
        if (this.m_File != null && this.m_File.length() > this.m_logThreshold) {
            this.assignNextLogFile();
            this.m_properties.adjustServerLogFileValue(this.m_currLogFileName);
            if (this.m_log.ifLogBasic(2L, 1)) {
                this.m_log.logBasic(1, "Switching to new log file ... " + this.m_currLogFileName);
            }
            this.m_properties.initServerLog(this.m_log, true);
            this.m_File = new File(this.m_currLogFileName);
            if (this.event_handler != null) {
                this.event_handler.sendFileNameChangedEvent(this.m_currLogFileName);
            }
            ++this.m_numFilesOnSystem;
            this.m_rolledFiles = this.m_rolledFiles + this.m_currLogFileName.substring(this.m_sequencenum_pos, this.m_sequencenum_pos + 6) + ",";
        }
    }
    
    private void deleteNextLogFile() {
        final boolean removeLog = this.removeLog(this.m_nextLogFileName.toString());
        if (!removeLog && this.m_log.ifLogBasic(2L, 1)) {
            this.m_log.logBasic(1, "File deletion for " + (Object)this.m_nextLogFileName + " returned " + removeLog);
        }
        final int index;
        if (this.m_rolledFiles != null && (index = this.m_rolledFiles.indexOf(ubFileWatchDog.fmt1.format(this.m_nextNumSequence))) != -1) {
            --this.m_numFilesOnSystem;
            final int beginIndex = 7;
            try {
                if (index == 0) {
                    this.m_rolledFiles = this.m_rolledFiles.substring(beginIndex);
                }
                else {
                    this.m_rolledFiles = this.m_rolledFiles.substring(0, index) + this.m_rolledFiles.substring(index + beginIndex);
                }
            }
            catch (Exception ex) {
                if (this.m_log.ifLogBasic(2L, 1)) {
                    this.m_log.logStackTrace(1, "Exception: ", ex);
                }
            }
        }
    }
    
    private void deleteLogFiles() {
        int numFilesOnSystem = this.m_numFilesOnSystem;
        int n = 0;
        if (this.m_numLogFiles < 2) {
            return;
        }
        if (this.m_numLogFiles < numFilesOnSystem) {
            int beginIndex = 0;
            final StringBuffer sb = new StringBuffer();
            if (this.m_fileNameToDelete == null) {
                this.m_fileNameToDelete = new StringBuffer(this.m_currLogFileName);
            }
            try {
                while (this.m_numLogFiles < numFilesOnSystem) {
                    final String substring = this.m_rolledFiles.substring(beginIndex, beginIndex + 6);
                    this.m_fileNameToDelete.replace(this.m_sequencenum_pos, this.m_sequencenum_pos + 6, substring);
                    --numFilesOnSystem;
                    if (!this.removeLog(this.m_fileNameToDelete.toString())) {
                        if (this.m_log.ifLogBasic(2L, 1)) {
                            this.m_log.logBasic(1, "Failed to delete " + this.m_fileNameToDelete.toString());
                        }
                        ++n;
                        sb.append(substring + ",");
                    }
                    else if (this.m_log.ifLogBasic(2L, 1)) {
                        this.m_log.logBasic(1, "Deleted " + this.m_fileNameToDelete.toString());
                    }
                    beginIndex = beginIndex + 6 + 1;
                }
                if (n > 0) {
                    this.m_rolledFiles = sb.toString() + this.m_rolledFiles.substring(beginIndex);
                }
                else {
                    this.m_rolledFiles = this.m_rolledFiles.substring(beginIndex);
                }
            }
            catch (Exception ex) {
                if (this.m_log.ifLogBasic(2L, 1)) {
                    this.m_log.logStackTrace(1, "Exception: ", ex);
                }
            }
            this.m_numFilesOnSystem = numFilesOnSystem + n;
        }
    }
    
    private void assignNextLogFile() {
        if (this.m_nextNumSequence >= 999999) {
            this.m_nextNumSequence = 0;
        }
        this.m_currLogFileName = this.m_nextLogFileName.toString();
        ++this.m_nextNumSequence;
        try {
            this.m_nextLogFileName.replace(this.m_sequencenum_pos, this.m_sequencenum_pos + 6, ubFileWatchDog.fmt1.format(this.m_nextNumSequence));
        }
        catch (Exception ex) {
            if (this.m_log.ifLogBasic(2L, 1)) {
                this.m_log.logStackTrace(1, "Exception: ", ex);
            }
        }
        this.deleteNextLogFile();
    }
    
    private void processExistingFiles() {
        boolean b = false;
        final StringBuffer sb = new StringBuffer();
        try {
            if (this.m_numLogFiles < 2) {
                b = true;
            }
            final File file = new File(this.m_properties.getValueAsString("srvrLogFile"));
            final File file2 = new File(file.getAbsolutePath());
            final String parent = file2.getParent();
            final String name = file2.getName();
            final int lastIndex = name.lastIndexOf(46);
            final String absolutePath = file2.getAbsolutePath();
            if (lastIndex == -1) {
                this.m_sequencenum_pos = absolutePath.length() + 1;
            }
            else {
                this.m_sequencenum_pos = absolutePath.lastIndexOf(46) + 1;
            }
            final int n = name.length() + 6 + 1;
            String substring;
            String substring2;
            int n2;
            if (lastIndex == -1) {
                substring = name;
                substring2 = "";
                n2 = name.length() + 1;
            }
            else {
                substring = name.substring(0, lastIndex);
                substring2 = name.substring(lastIndex);
                n2 = lastIndex + 1;
            }
            final String[] list = new File(parent).list(new DirFilter(parent, substring, substring2));
            this.m_numFilesOnSystem = list.length;
            int n3 = 0;
            int n4 = 0;
            for (int i = 0; i < list.length; ++i) {
                int int1;
                try {
                    int1 = Integer.parseInt(list[i].substring(n2, n2 + 6));
                }
                catch (Exception ex2) {
                    int1 = 1;
                }
                if (int1 > n4 + 1) {
                    n3 = i;
                    this.m_nextNumSequence = n4;
                }
                n4 = int1;
            }
            if (n3 == 0) {
                this.m_nextNumSequence = n4;
            }
            int numFilesOnSystem;
            if (b) {
                numFilesOnSystem = 0;
            }
            else {
                numFilesOnSystem = this.m_numFilesOnSystem - this.m_numLogFiles;
            }
            if (!this.m_appendMode) {
                numFilesOnSystem = this.m_numFilesOnSystem;
            }
            if (numFilesOnSystem < 0) {
                numFilesOnSystem = 0;
            }
            if (this.m_log.ifLogBasic(2L, 1)) {
                this.m_log.logBasic(1, "fileWatchDog: Need to remove " + numFilesOnSystem + " files.");
            }
            if (list.length > 0) {
                for (int n5 = n3, j = 0; j < list.length; ++j, ++n5) {
                    if (j < numFilesOnSystem) {
                        this.removeLog(list[n5]);
                        if (this.m_log.ifLogBasic(2L, 1)) {
                            this.m_log.logBasic(1, "fileWatchDog: removed: " + list[n5]);
                        }
                    }
                    else if (!b) {
                        sb.append(list[n5].substring(n2, n2 + 6) + ",");
                    }
                    if (n5 == list.length - 1) {
                        n5 = -1;
                    }
                }
            }
            this.m_numFilesOnSystem -= numFilesOnSystem;
            if (this.m_numFilesOnSystem == 0) {
                this.m_nextNumSequence = 1;
                try {
                    String s;
                    if (n2 == -1) {
                        s = name + "." + ubFileWatchDog.fmt1.format(this.m_nextNumSequence);
                    }
                    else {
                        s = substring + "." + ubFileWatchDog.fmt1.format(this.m_nextNumSequence) + substring2;
                    }
                    if (file.exists()) {
                        if (this.m_appendMode) {
                            final File dest = new File(s);
                            if (this.m_log.ifLogBasic(2L, 1)) {
                                this.m_log.logBasic(1, "Renaming base file to " + s);
                            }
                            file.renameTo(dest);
                        }
                        else {
                            file.delete();
                        }
                    }
                }
                catch (Exception ex3) {}
                sb.append(ubFileWatchDog.fmt1.format(this.m_nextNumSequence) + ",");
                this.m_numFilesOnSystem = 1;
            }
            if (substring2.length() == 0) {
                this.m_currLogFileName = absolutePath + "." + ubFileWatchDog.fmt1.format(this.m_nextNumSequence);
            }
            else {
                this.m_currLogFileName = absolutePath.substring(0, this.m_sequencenum_pos) + ubFileWatchDog.fmt1.format(this.m_nextNumSequence) + substring2;
            }
            ++this.m_nextNumSequence;
            if (this.m_nextNumSequence > 999999) {
                this.m_nextNumSequence = 1;
            }
            if (!b) {
                this.m_rolledFiles = sb.toString();
                if (this.m_log.ifLogBasic(2L, 1)) {
                    this.m_log.logBasic(1, "fileWatchDog: Existing files: " + this.m_rolledFiles);
                }
            }
        }
        catch (Exception ex) {
            if (this.m_log.ifLogBasic(2L, 1)) {
                this.m_log.logStackTrace(1, "fileWatchDog: ", ex);
            }
        }
    }
    
    private boolean removeLog(final String pathname) {
        final File file = new File(pathname);
        return file == null || !file.exists() || file.delete();
    }
    
    static {
        ubFileWatchDog.fmt1 = new DecimalFormat("000000");
    }
    
    protected class DirFilter implements FilenameFilter
    {
        private String filename;
        private String extension;
        private int extlen;
        private int validBkpFileNameLen;
        
        public DirFilter(final String s, final String filename, final String extension) {
            this.filename = filename;
            this.extension = extension;
            this.extlen = this.extension.length();
            this.validBkpFileNameLen = this.filename.length() + this.extlen + 6 + 1;
        }
        
        public boolean accept(final File file, final String s) {
            return (this.extlen <= 0 || s.endsWith(this.extension)) && s.startsWith(this.filename + ".") && s.length() == this.validBkpFileNameLen;
        }
    }
}
