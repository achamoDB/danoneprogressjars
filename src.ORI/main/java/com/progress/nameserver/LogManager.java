// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import com.progress.common.exception.ProException;
import com.progress.common.message.IProMessage;
import com.progress.common.event.EventMessageAdapter;
import com.progress.common.ehnlog.ILogEvntHandler;
import com.progress.common.event.LogEvent;
import com.progress.common.event.ProEvent;
import java.io.IOException;
import com.progress.common.util.PromsgsFile;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.common.event.IEventListener;

public class LogManager implements IEventListener
{
    public static final int LOGGING_NONE = 0;
    public static final int LOGGING_BASIC = 2;
    public static final int LOGGING_VERBOSE = 3;
    public static final int LOGGING_EXTENDED = 4;
    public static final String DEFAULT_LOGENTRYTYPES = "NSPlumbing";
    private IAppLogger log;
    private int dest;
    static boolean adapterInit;
    private String entrytypeName;
    
    public LogManager(final int loggingLevel) throws LogManagerInitException {
        this.log = new AppLogger();
        if (this.log != null) {
            this.log.setExecEnvId("NS");
            this.log.setLoggingLevel(loggingLevel);
            this.log.setLogEntries(1L, false, new byte[64]);
        }
        this.dest = 1;
        this.entrytypeName = new String("NSPlumbing");
        try {
            this.adapterInit();
        }
        catch (PromsgsFile.PromsgsFileIOException ex) {
            throw new LogManagerInitException("Terminal");
        }
    }
    
    public LogManager(final String s, final int n, final boolean b, final String s2, final long n2, final int n3) throws LogManagerInitException {
        try {
            this.log = new AppLogger(s, b ? 1 : 0, n, n2, n3, s2, "NS", "NServer");
            this.dest = 2;
            if (this.log != null) {
                this.entrytypeName = this.log.getLogContext().getEntrytypeName(0);
            }
            this.adapterInit();
        }
        catch (IOException ex) {
            throw new LogManagerInitException(s);
        }
        catch (PromsgsFile.PromsgsFileIOException ex2) {
            throw new LogManagerInitException(s);
        }
    }
    
    public void processEvent(final ProEvent proEvent) {
        if (proEvent instanceof NSLogEvent) {
            final int level = ((NSLogEvent)proEvent).getLevel();
            if (this.log != null && this.log.ifLogIt(level, 1L, 0)) {
                this.log.logWriteMessage(this.dest, level, this.log.getExecEnvId(), this.entrytypeName, ((LogEvent)proEvent).getMessage());
            }
        }
        else if (this.log != null && this.log.ifLogIt(1, 1L, 0)) {
            this.log.logWriteMessage(this.dest, 1, this.log.getExecEnvId(), this.entrytypeName, ((LogEvent)proEvent).getMessage());
        }
    }
    
    public boolean registerThresholdEventHandler(final ILogEvntHandler logEvntHandler) {
        return this.log != null && this.dest == 2 && this.log.registerThresholdEventHandler(logEvntHandler);
    }
    
    public String getCurrentLogFileName() {
        if (this.log == null || this.dest != 2) {
            return new String("");
        }
        return this.log.getCurrentLogFileName();
    }
    
    public int setLoggingLevel(final int loggingLevel) {
        if (this.log == null) {
            return 0;
        }
        return this.log.setLoggingLevel(loggingLevel);
    }
    
    public long setLogEntries(final String logEntries) {
        if (this.log == null) {
            return 0L;
        }
        return this.log.setLogEntries(logEntries);
    }
    
    private void adapterInit() throws PromsgsFile.PromsgsFileIOException {
        try {
            if (!LogManager.adapterInit) {
                EventMessageAdapter.setMessageSubsystem(new PromsgsFile());
                LogManager.adapterInit = true;
            }
        }
        catch (PromsgsFile.PromsgsFileIOException ex) {}
    }
    
    static {
        LogManager.adapterInit = false;
    }
    
    public class LogManagerInitException extends ProException
    {
        public LogManagerInitException(final String s) {
            super("Error Initializing Log File %s.", new Object[] { s });
        }
    }
}
