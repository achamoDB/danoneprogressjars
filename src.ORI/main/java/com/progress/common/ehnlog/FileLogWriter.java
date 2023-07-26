// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class FileLogWriter implements LogWriter
{
    private static final String SEPARATOR = "======================================================================";
    private LogWriter _writer;
    private LogFile _file;
    private ILogEvntHandler _event_handler;
    private boolean _appending;
    private boolean _checkMaxSize;
    private static boolean _isWindows;
    private static String _osName;
    
    public FileLogWriter(final String s, final boolean appending, final long n, final int n2, final int n3, final int n4) {
        this._appending = appending;
        this._file = new LogFile(s, appending, n, n2, n3, n4);
        this._checkMaxSize = (n > 0L);
        if (FileLogWriter._osName.startsWith("Windows")) {
            FileLogWriter._isWindows = true;
        }
        else {
            FileLogWriter._isWindows = false;
        }
        this._event_handler = null;
    }
    
    protected FileLogWriter() {
        this._writer = LogWriter.NULL;
        this._file = null;
        this._appending = false;
        this._event_handler = null;
        if (FileLogWriter._osName.startsWith("Windows")) {
            FileLogWriter._isWindows = true;
        }
        else {
            FileLogWriter._isWindows = false;
        }
    }
    
    public void start() throws IOException {
        if (this._file == null) {
            return;
        }
        this._file.cleanup();
        (this._writer = this._file.createLogWriter()).start();
        if (this._appending) {
            this.write("======================================================================");
        }
    }
    
    private void restart() throws IOException {
        if (this._file == null) {
            return;
        }
        (this._writer = this._file.createLogWriter()).start();
    }
    
    public void stop() {
        this._writer.stop();
        this._file = null;
    }
    
    public void write(final String s) {
        this._writer.write(s);
        int length = s.length();
        if (FileLogWriter._isWindows) {
            length += 2;
        }
        else {
            ++length;
        }
        if (this._checkMaxSize && this._file.hitMaxSize(length)) {
            this.processThreshold();
        }
    }
    
    public void write(final Throwable t) {
        this._writer.write(t);
        if (this._checkMaxSize && this._file.hitMaxSize(t.toString().length())) {
            this.processThreshold();
        }
    }
    
    private void processThreshold() {
        this.flush();
        this._writer.stop();
        this._file.mainThreshold();
        try {
            this.restart();
            if (this._event_handler != null) {
                this._event_handler.sendFileNameChangedEvent(this.getCurrentLogFileName());
            }
        }
        catch (IOException obj) {
            System.err.println("Error opening file " + obj);
        }
    }
    
    public void flush() {
        this._writer.flush();
    }
    
    public String getCurrentLogFileName() {
        if (this._file == null) {
            return null;
        }
        return this._file.getFileName();
    }
    
    public boolean registerThresholdEventHandler(final ILogEvntHandler event_handler) {
        if (this._file != null && this._file.getMaxSizeSetting() > 0L) {
            this._event_handler = event_handler;
            return true;
        }
        return false;
    }
    
    static {
        FileLogWriter._osName = System.getProperty("os.name");
    }
    
    protected class LogFile
    {
        private File _thisFile;
        private String _filename;
        private boolean _appending;
        private long _maxsize;
        private int _numFiles;
        private int _format_bkp_name_ID;
        private int _format_bkp_len;
        private String _BackupFiles;
        private int _numFilesOnSystem;
        private int _seqNumPos;
        private int _currSeqNum;
        private long _fileSize;
        private Formatter _formatter;
        private StreamLogWriter _slw;
        private static final int SEQNUMLIMIT = 999999;
        
        public LogFile(final String s, final boolean appending, final long maxsize, final int numFiles, final int format_bkp_name_ID, final int format_bkp_len) {
            this._seqNumPos = 0;
            this._currSeqNum = 0;
            this._formatter = new DefaultFormatter();
            this._thisFile = new File(s);
            this._filename = s;
            this._appending = appending;
            this._maxsize = maxsize;
            this._numFiles = numFiles;
            this._currSeqNum = 0;
            this._seqNumPos = 0;
            this._format_bkp_name_ID = format_bkp_name_ID;
            this._format_bkp_len = format_bkp_len;
            this._fileSize = this._thisFile.length();
            if (format_bkp_name_ID != -1 && this._maxsize > 0L) {
                this.processExistingFiles();
                this.nextFile();
            }
        }
        
        public long getMaxSizeSetting() {
            return this._maxsize;
        }
        
        public String getFileName() {
            return this._filename;
        }
        
        public void nextFile() {
            this._thisFile = new File(this._filename);
            this._fileSize = this._thisFile.length();
        }
        
        public void cleanup() throws IOException {
            if (!this._thisFile.exists()) {
                return;
            }
            if (!this._appending) {
                this._thisFile.delete();
            }
        }
        
        public LogWriter createLogWriter() throws IOException {
            if (this._filename == null) {
                return LogWriter.NULL;
            }
            if (this._maxsize > 0L && this._fileSize >= this._maxsize) {
                this.mainThreshold();
            }
            this._slw = new StreamLogWriter(new BufferedOutputStream(new FileOutputStream(this._filename, this._appending)));
            this._fileSize = this._thisFile.length();
            return this._slw;
        }
        
        protected boolean needToRename() {
            return this._appending && this._maxsize != 0L && this._thisFile.length() > this._maxsize;
        }
        
        public boolean hitMaxSize(final int n) {
            if (this._maxsize <= 0L) {
                return false;
            }
            this._fileSize += n;
            return this._fileSize >= this._maxsize;
        }
        
        public void mainThreshold() {
            if (this._fileSize >= this._maxsize) {
                if (this._slw != null) {
                    this._slw.stop();
                }
                if (this._format_bkp_name_ID == 1) {
                    this.thresholdTimestamp();
                }
                else if (this._format_bkp_name_ID == 2) {
                    this.thresholdSeqnumber();
                }
                this._fileSize = 0L;
            }
        }
        
        private void thresholdTimestamp() {
            boolean renameTo = false;
            String formatBackupFileName = "";
            final int lastIndex = this._filename.lastIndexOf(46);
            for (int i = 0; i < 2; ++i) {
                try {
                    if (i == 1) {
                        Thread.sleep(1000L);
                    }
                }
                catch (InterruptedException ex) {
                    System.err.println("Error in enhLogThreshold: " + ex.toString());
                }
                formatBackupFileName = this._formatter.formatBackupFileName(this._format_bkp_name_ID, 0);
                String pathname;
                if (lastIndex == -1) {
                    pathname = this._filename + "." + formatBackupFileName;
                }
                else {
                    pathname = this._filename.substring(0, lastIndex) + "." + formatBackupFileName + this._filename.substring(lastIndex);
                }
                renameTo = this._thisFile.renameTo(new File(pathname));
                if (renameTo) {
                    i = 2;
                }
            }
            if (renameTo && this._numFiles > 1) {
                if (this._numFilesOnSystem == this._numFiles) {
                    final int index = this._BackupFiles.indexOf(44);
                    final String substring = this._BackupFiles.substring(0, index);
                    String pathname2;
                    if (lastIndex == -1) {
                        pathname2 = this._filename + "." + substring;
                    }
                    else {
                        pathname2 = this._filename.substring(0, lastIndex) + "." + substring + this._filename.substring(lastIndex);
                    }
                    new File(pathname2).delete();
                    this._BackupFiles = this._BackupFiles.substring(index + 1);
                }
                else {
                    ++this._numFilesOnSystem;
                }
                this._BackupFiles = this._BackupFiles + formatBackupFileName + ",";
            }
        }
        
        private void thresholdSeqnumber() {
            String formatBackupFileName = "";
            final StringBuffer sb = new StringBuffer(this._filename);
            ++this._currSeqNum;
            if (this._currSeqNum > 999999) {
                this._currSeqNum = 1;
            }
            try {
                formatBackupFileName = this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum);
                sb.replace(this._seqNumPos, this._seqNumPos + this._format_bkp_len, formatBackupFileName);
                this._filename = sb.toString();
                this.nextFile();
            }
            catch (Exception ex) {}
            if (this._numFiles > 1) {
                if (this._numFilesOnSystem >= this._numFiles) {
                    final int index = this._BackupFiles.indexOf(44);
                    sb.replace(this._seqNumPos, this._seqNumPos + this._format_bkp_len, this._BackupFiles.substring(0, index));
                    new File(sb.toString()).delete();
                    this._BackupFiles = this._BackupFiles.substring(index + 1);
                }
                else {
                    ++this._numFilesOnSystem;
                }
                this._BackupFiles = this._BackupFiles + formatBackupFileName + ",";
            }
        }
        
        private void removeLog(final String pathname) {
            new File(pathname).delete();
        }
        
        private void processExistingFiles() {
            if (this._format_bkp_name_ID == 1) {
                this.processFilesTimestamp();
            }
            else if (this._format_bkp_name_ID == 2) {
                this.processFilesSeqnum();
                this.nextFile();
            }
        }
        
        private void processFilesTimestamp() {
            String string = "";
            if (this._numFiles < 2) {
                return;
            }
            final File file = new File(this._thisFile.getAbsolutePath());
            final String parent = file.getParent();
            final String name = file.getName();
            final int lastIndex = name.lastIndexOf(46);
            final int n = name.length() + this._format_bkp_len + 1;
            String substring;
            String substring2;
            int beginIndex;
            if (lastIndex == -1) {
                substring = name;
                substring2 = "";
                beginIndex = name.length() + 1;
            }
            else {
                substring = name.substring(0, lastIndex);
                substring2 = name.substring(lastIndex);
                beginIndex = lastIndex + 1;
            }
            final String[] list = new File(parent).list(new DirFilter(file.getParent(), substring, substring2, this._format_bkp_len));
            this._numFilesOnSystem = list.length + 1;
            int n2 = this._numFilesOnSystem - this._numFiles;
            if (n2 < 0) {
                n2 = 0;
            }
            if (list.length > 0) {
                for (int i = 0; i < list.length; ++i) {
                    if (i < n2) {
                        this.removeLog(list[i]);
                    }
                    else {
                        string = string + list[i].substring(beginIndex, beginIndex + this._format_bkp_len) + ",";
                    }
                }
            }
            this._BackupFiles = string;
            this._numFilesOnSystem -= n2;
        }
        
        private void processFilesSeqnum() {
            boolean b = false;
            String s = "";
            if (this._numFiles < 2) {
                b = true;
            }
            final File file = new File(this._thisFile.getAbsolutePath());
            final String parent = file.getParent();
            final String name = file.getName();
            final int lastIndex = name.lastIndexOf(46);
            final String absolutePath = file.getAbsolutePath();
            if (lastIndex == -1) {
                this._seqNumPos = absolutePath.length() + 1;
            }
            else {
                this._seqNumPos = absolutePath.lastIndexOf(46) + 1;
            }
            final int n = name.length() + this._format_bkp_len + 1;
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
            final String[] list = new File(parent).list(new DirFilter(file.getParent(), substring, substring2, this._format_bkp_len));
            this._numFilesOnSystem = list.length;
            int n3 = 0;
            int n4 = 0;
            this._currSeqNum = 1;
            for (int i = 0; i < list.length; ++i) {
                int int1;
                try {
                    int1 = Integer.parseInt(list[i].substring(n2, n2 + this._format_bkp_len));
                }
                catch (Exception ex) {
                    int1 = 1;
                }
                if (int1 > n3 + 1) {
                    n4 = i;
                    this._currSeqNum = n3;
                }
                n3 = int1;
            }
            if (n4 == 0) {
                this._currSeqNum = n3;
            }
            int numFilesOnSystem;
            if (b) {
                numFilesOnSystem = 0;
            }
            else {
                numFilesOnSystem = this._numFilesOnSystem - this._numFiles;
            }
            if (!this._appending) {
                this._currSeqNum = 1;
                numFilesOnSystem = this._numFilesOnSystem;
            }
            if (numFilesOnSystem < 0) {
                numFilesOnSystem = 0;
            }
            if (list.length > 0) {
                int n5 = n4;
                for (int j = 0; j < list.length; ++j) {
                    if (j < numFilesOnSystem) {
                        this.removeLog(list[n5]);
                    }
                    else if (!b) {
                        s = s + list[n5].substring(n2, n2 + this._format_bkp_len) + ",";
                    }
                    if (n5 == list.length - 1) {
                        n5 = -1;
                    }
                    ++n5;
                }
                if (file.exists() && !this._appending) {
                    file.delete();
                }
            }
            else {
                this._currSeqNum = 1;
                String pathname;
                if (n2 == -1) {
                    pathname = name + "." + this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum);
                }
                else {
                    pathname = substring + "." + this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum) + substring2;
                }
                if (file.exists()) {
                    if (this._appending) {
                        file.renameTo(new File(pathname));
                    }
                    else {
                        file.delete();
                    }
                }
                if (!b) {
                    s = this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum) + ",";
                }
                this._numFilesOnSystem = 1;
            }
            if (substring2.length() == 0) {
                this._filename = absolutePath + "." + this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum);
            }
            else {
                this._filename = absolutePath.substring(0, this._seqNumPos) + this._formatter.formatBackupFileName(this._format_bkp_name_ID, this._currSeqNum) + substring2;
            }
            this._BackupFiles = s;
            this._numFilesOnSystem -= numFilesOnSystem;
        }
    }
    
    protected class DirFilter implements FilenameFilter
    {
        private String filename;
        private String extension;
        private int extlen;
        private int validBkpFileNameLen;
        
        public DirFilter(final String s, final String filename, final String extension, final int n) {
            this.filename = filename;
            this.extension = extension;
            this.extlen = this.extension.length();
            this.validBkpFileNameLen = this.filename.length() + this.extlen + n + 1;
        }
        
        public boolean accept(final File file, final String s) {
            return (this.extlen <= 0 || s.endsWith(this.extension)) && s.startsWith(this.filename + ".") && s.length() == this.validBkpFileNameLen;
        }
    }
}
