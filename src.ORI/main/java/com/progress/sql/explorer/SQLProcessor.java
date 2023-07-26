// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.chimera.common.Tools;
import com.progress.chimera.ChimeraException;
import java.sql.SQLException;
import com.progress.common.exception.ProException;
import java.io.BufferedReader;
import com.progress.common.util.IProWorkingCallback;

public class SQLProcessor implements Runnable, ISQLConstants
{
    Throwable m_exception;
    SQLProperties m_SQLProps;
    SQLStringResults m_SQLResults;
    SQLConnect m_SQLConnect;
    IProWorkingCallback m_working;
    boolean m_done;
    Object m_doneLock;
    BufferedReader m_commandInput;
    boolean m_commandInputDone;
    Object m_commandInputLock;
    boolean m_commandOutputDone;
    Object m_commandOutputLock;
    static SQLExplorerLog m_log;
    
    public SQLProcessor(final SQLProperties sqlProperties) {
        this(sqlProperties, null);
    }
    
    public SQLProcessor(final SQLProperties sqlProps, final IProWorkingCallback working) {
        this.m_exception = null;
        this.m_SQLProps = null;
        this.m_SQLResults = null;
        this.m_SQLConnect = null;
        this.m_working = null;
        this.m_done = false;
        this.m_doneLock = new Object();
        this.m_commandInput = null;
        this.m_commandInputDone = false;
        this.m_commandInputLock = new Object();
        this.m_commandOutputDone = false;
        this.m_commandOutputLock = new Object();
        this.m_SQLProps = sqlProps;
        this.m_SQLConnect = new SQLConnect(sqlProps);
        this.m_working = working;
    }
    
    public SQLConnect getSQLConnect() {
        return this.m_SQLConnect;
    }
    
    public void run() {
        this.m_exception = null;
        try {
            this.m_SQLResults = new SQLStringResults(this.m_SQLProps, this.m_SQLConnect);
            this.m_SQLConnect.openConnection();
            this.m_SQLResults.getSQLWarnings();
        }
        catch (ProException exception) {
            this.m_exception = exception;
        }
        catch (SQLException exception2) {
            this.m_SQLResults.getSQLErrors(exception2);
            this.m_exception = exception2;
        }
        catch (ChimeraException exception3) {
            this.m_exception = exception3;
        }
        catch (Throwable exception4) {
            this.m_exception = exception4;
        }
        this.postCommandResults();
        if (this.m_working != null && this.m_SQLProps.getMode() == 1) {
            this.m_working.workingCallback(new Integer(0));
        }
        if (this.m_exception != null) {
            return;
        }
        try {
            while (!this.isDone()) {
                this.waitForCommand();
                if (this.isDone()) {
                    break;
                }
                this.execCommand();
                if (this.isDone()) {
                    break;
                }
                this.postCommandResults();
            }
        }
        catch (Exception exception5) {
            Tools.px("### Exception in SQLProcessor run(). ###", exception5);
            this.m_exception = exception5;
        }
        catch (Throwable t) {
            Tools.px("### Throwable in SQLProcessor run(). ###", t);
        }
    }
    
    protected void waitForCommand() throws Exception {
        synchronized (this.m_commandInputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  waitForCommand().");
            }
            while (!this.m_commandInputDone) {
                try {
                    this.m_commandInputLock.wait();
                    continue;
                }
                catch (InterruptedException ex) {
                    if (this.m_SQLProps.getSqlDebug()) {
                        SQLProcessor.m_log.log("INTERRUPTED waitForCommand().");
                    }
                }
                break;
            }
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT waitForCommand().");
            }
        }
    }
    
    protected void execCommand() throws SQLException, Exception {
        final StringBuffer sb = new StringBuffer();
        try {
            synchronized (this.m_commandInputLock) {
                String line;
                while (null != (line = this.m_commandInput.readLine())) {
                    sb.append(line + ISQLConstants.NEWLINE);
                }
                this.m_commandInput = null;
            }
            final String trim = sb.toString().trim();
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  execCommand( " + trim + " ).");
            }
            this.m_SQLResults = new SQLStringResults(this.m_SQLProps, this.m_SQLConnect, trim);
        }
        finally {
            synchronized (this.m_commandInputLock) {
                this.m_commandInputDone = false;
                if (this.m_SQLProps.getSqlDebug()) {
                    SQLProcessor.m_log.log("OUT execCommand().");
                }
            }
        }
    }
    
    protected void postCommandResults() {
        synchronized (this.m_commandOutputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  postCommandResults().");
            }
            try {
                if (this.m_SQLResults != null) {
                    this.m_SQLResults.getOutput();
                }
            }
            catch (SQLStringResults.ResultSetEndException ex) {}
            this.m_commandOutputDone = true;
            this.m_commandOutputLock.notify();
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT postCommandResults().");
            }
        }
        Thread.yield();
    }
    
    public void wakeUp() {
        synchronized (this.m_commandOutputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN wkeUp() synchronized 1.");
            }
            this.m_commandOutputDone = false;
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT wakeUp() synchronized 1.");
            }
        }
        synchronized (this.m_commandInputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN wkeUp() synchronized 2.");
            }
            this.m_commandInputDone = true;
            this.m_commandInputLock.notify();
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT wakeUp() synchronized 2.");
            }
        }
    }
    
    public boolean waitForOutput() {
        boolean commandOutputDone = false;
        synchronized (this.m_commandOutputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  waitForOutput().");
            }
            while (!this.m_commandOutputDone) {
                try {
                    this.m_commandOutputLock.wait();
                    continue;
                }
                catch (InterruptedException ex) {
                    if (this.m_SQLProps.getSqlDebug()) {
                        SQLProcessor.m_log.log("INTERRUPTED waitForOutput().");
                    }
                }
                break;
            }
            commandOutputDone = this.m_commandOutputDone;
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT waitForOutput().");
            }
        }
        return commandOutputDone;
    }
    
    public boolean waitForOutput(final int i) {
        boolean commandOutputDone = false;
        synchronized (this.m_commandOutputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  waitForOutput( " + i + " ).");
            }
            if (!this.m_commandOutputDone) {
                try {
                    this.m_commandOutputLock.wait(i);
                }
                catch (InterruptedException ex) {
                    if (this.m_SQLProps.getSqlDebug()) {
                        SQLProcessor.m_log.log("INTERRUPTED waitForOutput( timeout ).");
                    }
                }
            }
            commandOutputDone = this.m_commandOutputDone;
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT waitForOutput( timeout ).");
            }
        }
        return commandOutputDone;
    }
    
    public SQLStringResults getSQLResults() {
        synchronized (this.m_commandOutputLock) {
            return this.m_SQLResults;
        }
    }
    
    public void setDone(final boolean done) {
        synchronized (this.m_doneLock) {
            this.m_working = null;
            this.m_done = done;
        }
    }
    
    public boolean isDone() {
        final boolean done;
        synchronized (this.m_doneLock) {
            done = this.m_done;
        }
        return done;
    }
    
    public void setCommandInput(final BufferedReader commandInput) {
        synchronized (this.m_commandInputLock) {
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("IN  setCommandInput().");
            }
            this.m_commandInput = commandInput;
            if (this.m_SQLProps.getSqlDebug()) {
                SQLProcessor.m_log.log("OUT setCommandInput().");
            }
        }
    }
    
    public Throwable getException() {
        return this.m_exception;
    }
    
    protected void getSQLErrors(final SQLException ex) {
        if (this.m_SQLResults != null) {
            this.m_SQLResults.getSQLErrors(ex);
        }
    }
    
    static {
        SQLProcessor.m_log = SQLExplorerLog.get();
    }
}
