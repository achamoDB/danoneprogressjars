// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.common.exception.IChainableException;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import com.progress.open4gl.RunTime4GLQuitException;
import com.progress.open4gl.NetworkException;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.BooleanHolder;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.BusySessionException;
import com.progress.open4gl.RunTime4GLException;
import java.util.Enumeration;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.broker.BrokerException;
import com.progress.open4gl.broker.BrokerFactory;
import com.progress.open4gl.ConnectException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Hashtable;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.broker.Broker;
import java.text.DecimalFormat;

public class Session implements Interruptable
{
    private static int MAX_REQUEST_LENGTH;
    private static int SESSION_MANAGED;
    private static Object sessionObjCountLock;
    private static int sessionObjCount;
    private static DecimalFormat fmt6;
    private Broker broker;
    private long nextId;
    private OutputBuffer outBuffer;
    private ResultSet outputSetsChain;
    private Interruptable interrupt;
    private int refCount;
    private Open4GLException localError;
    private SessionLock sessionLock;
    private Tracer tracer;
    private boolean conversationAllocated;
    private String thisUrl;
    private Hashtable procList;
    private Object returnValue;
    private boolean insideRequest;
    private ThreadContextTable threadContextTable;
    private String sessionID;
    private int poolState;
    private IAppLogger log;
    private long m_tsCreated;
    private IPoolProps m_sessionProperties;
    private int m_serverMajorVersion;
    private int m_prop_apsvcl_vers;
    
    public Session(final String s, final IAppLogger log, final IPoolProps sessionProperties) {
        this.tracer = RunTimeProperties.tracer;
        this.m_prop_apsvcl_vers = 0;
        this.sessionID = this.newSessionID(s);
        this.m_tsCreated = System.currentTimeMillis();
        this.log = log;
        this.broker = null;
        this.m_sessionProperties = sessionProperties;
    }
    
    public synchronized void setStop() {
        try {
            this.validate();
        }
        catch (Exception ex) {
            return;
        }
        this.tracer.print("   STOP: setStop() is called.");
        this.sessionLock.interruptWaiters();
        if (this.interrupt != null) {
            this.tracer.print("   STOP: interrupt.stop() is called.");
            this.interrupt.stop();
        }
        else {
            this.tracer.print("   STOP: interrupt is null.");
        }
    }
    
    public void connect(final String s, final String s2, final String str, final String s3, final String str2) throws ConnectException, SystemErrorException {
        this.tracer.print("Stream Protocol Version: " + new Integer(RunTimeProperties.getStreamProtocolVersion()).toString());
        this.tracer.print("Dynamic Api Version: " + new Integer(RunTimeProperties.getDynamicApiVersion()).toString());
        this.tracer.print("CONNECTING to: " + s2 + " ...");
        this.tracer.print(" User : " + str);
        this.tracer.print("Client Info: " + str2);
        if (this.broker != null) {
            throw new ConnectException(29L, null);
        }
        this.init();
        this.thisUrl = s2;
        try {
            this.broker = BrokerFactory.create(this.m_sessionProperties, this.log);
            this.m_serverMajorVersion = 10;
            this.broker.setSessionID(this.toString());
            if (this.connectionMode() == Session.SESSION_MANAGED) {
                this.broker.connect(s, s2, str, s3, str2);
                final String connectionReturnValue = this.broker.getConnectionReturnValue();
                if (connectionReturnValue != null) {
                    this.setRetValue(connectionReturnValue, true);
                }
            }
            else {
                this.broker.xid(s, s2, str, s3, str2);
            }
            final String capability = this.broker.getCapability((short)3001);
            if (capability != null) {
                try {
                    this.m_prop_apsvcl_vers = Integer.parseInt(capability);
                }
                catch (NumberFormatException ex2) {
                    this.m_prop_apsvcl_vers = -1;
                }
            }
            if (this.m_prop_apsvcl_vers < 2) {
                this.m_serverMajorVersion = 9;
            }
            if (this.getServerASKEnabled()) {
                ASKWatchDog.register(this);
            }
        }
        catch (BrokerException ex) {
            throw ExceptionConverter.convertToConnectException(ex);
        }
        this.tracer.print("Connection is successful. ");
        this.tracer.print("Connected to V" + this.m_serverMajorVersion + " AppServer");
    }
    
    public synchronized void addReference() {
        ++this.refCount;
    }
    
    public synchronized void deleteReference() throws SystemErrorException, Open4GLException {
        if (this.refCount == 0) {
            throw new SystemErrorException(31L, null);
        }
        --this.refCount;
        if (this.refCount == 0) {
            ASKWatchDog.deregister(this);
            this.disconnect(false, true);
        }
    }
    
    public boolean isConnected() {
        return this.broker != null;
    }
    
    private void allocate(final String str) throws BrokerException {
        this.tracer.print("Broker Call: allocate(" + str + ")", 4);
        this.broker.allocate(str);
        this.conversationAllocated = true;
    }
    
    private void deallocate() throws BrokerException {
        if (this.broker != null && this.conversationAllocated) {
            this.tracer.print("Broker Call: deallocate()", 4);
            this.broker.deallocate();
        }
        this.conversationAllocated = false;
    }
    
    private synchronized void disconnect(final boolean b, final boolean b2) throws Open4GLException {
        if (b) {
            this.tracer.print("Broker Call: disconnect(force)", 4);
            try {
                if (this.broker != null) {
                    this.broker.unconditionalDisconnect();
                }
            }
            catch (Throwable t2) {}
            this.broker = null;
            return;
        }
        if (this.insideRequest) {
            if (b2) {
                ++this.refCount;
            }
            throw new Open4GLException(67L, null);
        }
        this.tracer.print("DISCONNECTING from: " + this.thisUrl + " ...");
        try {
            if (this.outputSetsChain != null) {
                ResultSet.closeAll(this, this.outputSetsChain);
            }
            final Enumeration<PersistentProc> keys = this.procList.keys();
            while (keys.hasMoreElements()) {
                keys.nextElement().delete();
            }
            this.tracer.print("Broker Call: disconnect()", 4);
            this.broker.disconnect();
            this.broker = null;
        }
        catch (Throwable t) {
            throw new Open4GLException(32L, new Object[] { t.getMessage() });
        }
    }
    
    public PersistentProc runPersistentProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(null, s, set, true, false, 0L, null, 1);
    }
    
    public PersistentProc runPersistentProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(s, s2, set, true, false, 0L, null, 1);
    }
    
    public PersistentProc runPersistentProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(null, s, set, true, false, 0L, resultSetSchema, 1);
    }
    
    public PersistentProc runPersistentProcedure(final String s, final int n) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return new PersistentProc(s, this, n);
    }
    
    public PersistentProc runPersistentProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(s, s2, set, true, false, 0L, resultSetSchema, 1);
    }
    
    public void runProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.runProcedure(null, s, set, false, false, 0L, null, 3);
    }
    
    public void runProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.runProcedure(s, s2, set, false, false, 0L, null, 3);
    }
    
    public void runProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.runProcedure(null, s, set, false, false, 0L, resultSetSchema, 3);
    }
    
    public void runProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.runProcedure(s, s2, set, false, false, 0L, resultSetSchema, 3);
    }
    
    void runProcedure(final String s, final ParameterSet set, final Object o, final ResultSetSchema resultSetSchema) throws Open4GLException {
        this.runProcedure(null, s, set, false, true, (long)o, resultSetSchema, 2);
    }
    
    void runProcedure(final String s, final String s2, final ParameterSet set, final Object o, final ResultSetSchema resultSetSchema) throws Open4GLException {
        this.runProcedure(s, s2, set, false, true, (long)o, resultSetSchema, 2);
    }
    
    public void runStatelessProcedure(final String s, final String s2, final ParameterSet set, final Object o, final ResultSetSchema resultSetSchema, final int n) throws Open4GLException {
        this.runProcedure(s, s2, set, true, true, (long)o, resultSetSchema, n);
    }
    
    ResultSet getAnchor() {
        return this.outputSetsChain;
    }
    
    public synchronized boolean isStreaming() {
        return this.outputSetsChain != null;
    }
    
    public String getConnectionId() {
        try {
            if (this.broker != null) {
                return this.broker.getConnectionID();
            }
        }
        catch (BrokerException ex) {
            ExceptionConverter.convertToSystemErrorException(ex);
        }
        return null;
    }
    
    public String getRequestId() {
        try {
            if (this.broker != null) {
                return this.broker.getRequestID();
            }
        }
        catch (BrokerException ex) {
            ExceptionConverter.convertToSystemErrorException(ex);
        }
        return null;
    }
    
    public String getSSLSubjectName() {
        try {
            if (this.broker != null) {
                return this.broker.getSSLSubjectName();
            }
        }
        catch (BrokerException ex) {
            ExceptionConverter.convertToSystemErrorException(ex);
        }
        return null;
    }
    
    private PersistentProc runProcedure(final String s, final String s2, final ParameterSet set, final boolean b, final boolean b2, final long n, final ResultSetSchema resultSetSchema, final int n2) throws Open4GLException {
        this.validate();
        boolean lock = false;
        try {
            this.insideRequest = true;
            lock = this.sessionLock.lock(!this.waitIfBusy(), null);
            if (!lock) {
                throw new BusySessionException(53L, null);
            }
            return this.runProcedure0(s, s2, set, b, b2, n, resultSetSchema, n2);
        }
        catch (InterruptedException ex3) {
            final Open4GLException ex = new Open4GLException(52L, null);
            RunTimeProperties.tracer.print(ex, 1);
            throw ex;
        }
        catch (Open4GLException ex2) {
            RunTimeProperties.tracer.print(ex2, 1);
            throw ex2;
        }
        catch (Throwable t) {
            RunTimeProperties.tracer.print(t, 1);
            if (!Exception.class.isAssignableFrom(t.getClass()) && !IChainableException.class.isAssignableFrom(t.getClass())) {
                this.disconnect(true, false);
            }
            throw new Open4GLException(t.toString() + ":" + t.getMessage());
        }
        finally {
            this.insideRequest = false;
            if (lock && !this.isStreaming()) {
                this.sessionLock.unLock();
            }
        }
    }
    
    private PersistentProc runProcedure0(final String s, final String str, final ParameterSet set, final boolean b, final boolean b2, long longValue, final ResultSetSchema resultSetSchema, final int n) throws Open4GLException, ProDataException {
        MetaSchema metaSchema = null;
        if (resultSetSchema != null) {
            if (!resultSetSchema.isResultSetSchema()) {
                metaSchema = (MetaSchema)resultSetSchema;
            }
            else {
                metaSchema = new MetaSchema(resultSetSchema);
            }
        }
        if (str == null || str.length() == 0) {
            throw new Open4GLException(7665970990714725246L, null);
        }
        if (this.outputSetsChain != null) {
            throw new Open4GLException(33L, new Object[] { str });
        }
        int n2 = 0;
        if (this.m_serverMajorVersion > 9) {
            n2 = 2;
        }
        int schemaCount = 0;
        Label_0197: {
            if (metaSchema != null) {
                if (!metaSchema.validateParameters(set)) {
                    throw new Open4GLException(34L, new Object[] { str });
                }
                schemaCount = metaSchema.getSchemaCount();
                try {
                    metaSchema.ensureMetaData(set);
                    break Label_0197;
                }
                catch (ProSQLException ex) {
                    this.tracer.print(ex, 1);
                    throw new Open4GLException(ex.getMessage());
                }
            }
            if (set.hasResultSet()) {
                throw new Open4GLError(41L, null);
            }
            schemaCount = 0;
        }
        final MetaSchema metaSchema2 = null;
        Object procId = null;
        this.setInterrupt(null);
        this.localError = null;
        final SinkInputStream sinkInputStream = new SinkInputStream(this.broker);
        final SrcOutputStream srcOutputStream = new SrcOutputStream(this.broker);
        String str2;
        int n3;
        if (b) {
            if (!b2) {
                str2 = "persistent";
                n3 = 1;
                procId = this.getProcId();
                longValue = (long)procId;
            }
            else {
                if (11 == (n3 = n)) {
                    str2 = "stateless-ss";
                }
                else {
                    str2 = "stateless-sr";
                }
                if (set.isFunction()) {
                    ++n3;
                }
                procId = new Long(longValue);
            }
        }
        else if (b2) {
            if (set.isFunction()) {
                str2 = "user defined function";
                n3 = 7;
            }
            else {
                str2 = "internal";
                n3 = 2;
            }
        }
        else {
            str2 = "";
            n3 = 3;
        }
        this.validate();
        if (!set.allSet()) {
            throw new Open4GLException(35L, new Object[] { str });
        }
        this.tracer.print("\nRUN: " + str2 + " procedure - " + str);
        if (RunTimeProperties.tracer.getTraceLevel() >= 2 && metaSchema != null) {
            metaSchema.print(set, this.tracer);
        }
        try {
            this.allocate(s);
            this.tracer.print("    " + str + ":" + " Sending request and input parameter...");
            final int n4 = schemaCount;
            final RequestResultSet set2 = new RequestResultSet(str, set.getNumParams(), n3, (int)longValue, n4, this.m_serverMajorVersion, this.serializeDatasetsAsXml());
            final ParameterResultSet set3 = new ParameterResultSet(set);
            this.outBuffer.reset();
            final InputTableStreamer inputTableStreamer = new InputTableStreamer(this.outBuffer);
            final InputParameterStreamer inputParameterStreamer = new InputParameterStreamer(this.outBuffer, this.serializeDatasetsAsXml());
            this.tracer.print("    " + str + ":" + " Stream request ...", 4);
            inputTableStreamer.streamResultSet(set2, RequestMetaData.metaData, this.m_serverMajorVersion, n2);
            this.tracer.print("    " + str + ":" + " Stream input parameters ...", 4);
            inputParameterStreamer.streamResultSet(set3, ParameterMetaData.metaData, this.m_serverMajorVersion, n2);
            srcOutputStream.write(RunTimeProperties.getStreamProtocolVersion());
            final int len = this.outBuffer.getLen();
            if (len > Session.MAX_REQUEST_LENGTH) {
                throw new ClientException(5, 56L, null);
            }
            srcOutputStream.write(Util.getHighByte((short)len));
            srcOutputStream.write(Util.getLowByte((short)len));
            srcOutputStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
            MemptrParamStream.sendMemptrs(set, srcOutputStream);
            if (n4 > 0) {
                this.tracer.print("    " + str + ":" + " Sending local schema for verification...");
                new SchemaStreamer(metaSchema, set, srcOutputStream, this.outBuffer, this.m_serverMajorVersion).streamOut();
            }
            if (set.hasInputResultSet() || set.hasInputDataGraph()) {
                this.tracer.print("    " + str + ":" + " Sending input result sets...");
                final TableStreamer interrupt = new TableStreamer(srcOutputStream, metaSchema);
                this.setInterrupt(interrupt);
                interrupt.streamOut(set, this.m_serverMajorVersion);
            }
        }
        catch (Exception ex2) {
            this.handleInputErrors(srcOutputStream, ex2, set);
            this.setInterrupt(null);
            if (srcOutputStream.isOpen()) {
                try {
                    this.tracer.print("    " + str + ":" + " Closing the request stream...");
                    srcOutputStream.close();
                }
                catch (IOException ex3) {
                    this.handleInputErrors(srcOutputStream, ex3, set);
                }
            }
        }
        finally {
            this.setInterrupt(null);
            if (srcOutputStream.isOpen()) {
                try {
                    this.tracer.print("    " + str + ":" + " Closing the request stream...");
                    srcOutputStream.close();
                }
                catch (IOException ex4) {
                    this.handleInputErrors(srcOutputStream, ex4, set);
                }
            }
        }
        this.tracer.print("    " + str + ":" + " Waiting for server response...");
        try {
            this.setInterrupt(this);
            this.tracer.print("    " + str + ":" + " Receiving output parameters and return values...");
            final OutputParamReader outputParamReader = new OutputParamReader(sinkInputStream, this);
            final BooleanHolder booleanHolder = new BooleanHolder();
            this.setRetValue(set.setScalarValues(outputParamReader, booleanHolder), booleanHolder.getBooleanValue());
            final ProxyListReader proxyListReader = new ProxyListReader(sinkInputStream);
            this.dumpProcList("looking up proxyList", 4);
            while (proxyListReader.nextProxyID()) {
                final long proxyID = proxyListReader.getProxyID();
                final PersistentProc persistentProcByID = this.findPersistentProcByID(proxyID);
                if (persistentProcByID == null) {
                    this.tracer.print("    " + str + ":" + " server deleted unknown proxy " + proxyID);
                }
                else {
                    this.tracer.print("    " + str + ":" + " server deleted proxy " + proxyID);
                    persistentProcByID.deletedByServer(true);
                    this.procList.remove(persistentProcByID);
                }
            }
            final boolean hasSchemaHeader = proxyListReader.getProxySet().hasSchemaHeader();
            final int marshalLevel = proxyListReader.getProxySet().getMarshalLevel();
            this.dumpProcList("proxyList after procedures deleted", 4);
            MemptrParamStream.recvMemptrs(set, sinkInputStream);
            if (set.hasOutputResultSet() || set.hasOutputDataGraph()) {
                new SchemaFiller(sinkInputStream, set, metaSchema, this.m_serverMajorVersion, hasSchemaHeader, marshalLevel);
            }
            if (set.hasOutputResultSet() || set.hasOutputDataGraph()) {
                this.tracer.print("    " + str + ":" + " Setting output ResultSet and ProDataGraph handlers...");
                MetaSchema metaSchema3;
                if (metaSchema != null) {
                    metaSchema3 = metaSchema;
                }
                else {
                    metaSchema3 = metaSchema2;
                }
                final OutputTableStreamer outputTableStreamer = new OutputTableStreamer(set, metaSchema3, sinkInputStream);
                this.outputSetsChain = outputTableStreamer.createResults(this);
                if (set.hasOutputDataGraph()) {
                    outputTableStreamer.fillResults(this);
                }
            }
        }
        catch (ClientException ex5) {
            this.handleOutputErrors(sinkInputStream, ex5, set);
            if (!this.isStreaming()) {
                this.setInterrupt(null);
            }
            try {
                if (!this.isStreaming()) {
                    this.deallocate();
                }
            }
            catch (BrokerException ex6) {
                if (set != null) {
                    set.cleanUp();
                }
                this.disconnect(true, false);
                throw ExceptionConverter.convertToSystemErrorException(ex6);
            }
        }
        finally {
            if (!this.isStreaming()) {
                this.setInterrupt(null);
            }
            try {
                if (!this.isStreaming()) {
                    this.deallocate();
                }
            }
            catch (BrokerException ex7) {
                if (set != null) {
                    set.cleanUp();
                }
                this.disconnect(true, false);
                throw ExceptionConverter.convertToSystemErrorException(ex7);
            }
        }
        if (b && !b2) {
            final PersistentProc key = new PersistentProc(str, procId, this);
            this.procList.put(key, procId);
            return key;
        }
        return null;
    }
    
    public void manageASKPingRequest() throws Open4GLException {
        boolean lock = false;
        if (!this.isConnected()) {
            return;
        }
        try {
            this.tracer.print(this.toString() + " ++++ acquiring session lock : manageASKPingRequests", 5);
            lock = this.sessionLock.lock(true, null);
            if (!lock) {
                this.tracer.print(this.toString() + " ++++ session not locked : manageASKPingRequest", 5);
                return;
            }
            this.tracer.print(this.toString() + " ++++ session locked : manageASKPingRequest", 5);
            synchronized (this) {
                this.broker.manageASKPingRequest();
            }
        }
        catch (InterruptedException ex4) {
            final Open4GLException ex = new Open4GLException(52L, null);
            RunTimeProperties.tracer.print(ex, 1);
            throw ex;
        }
        catch (Open4GLException ex2) {
            RunTimeProperties.tracer.print(ex2, 1);
            throw ex2;
        }
        catch (Exception ex3) {
            RunTimeProperties.tracer.print(ex3, 1);
            throw new Open4GLException(ex3.getMessage());
        }
        finally {
            if (lock) {
                this.tracer.print(this.toString() + " ++++ unlocking session : manageASKPingRequest", 5);
                this.sessionLock.unLock();
                this.tracer.print(this.toString() + " ++++ session unlocked : manageASKPingRequest", 5);
            }
        }
    }
    
    void setNotStreaming() {
        if (this.outputSetsChain != null && !this.sessionLock.isLocked()) {
            throw new Open4GLError();
        }
        this.outputSetsChain = null;
        this.setInterrupt(null);
        try {
            this.deallocate();
        }
        catch (Exception ex) {
            throw new Open4GLError(36L, new Object[] { ex.getMessage() });
        }
        this.sessionLock.unLock();
    }
    
    private void validate() throws Open4GLException {
        if (this.broker == null) {
            throw new Open4GLException(37L, null);
        }
    }
    
    private void setRetValue(final Object returnValue, final boolean b) {
        if (b) {
            this.returnValue = returnValue;
        }
        this.threadContextTable.put(this.returnValue);
    }
    
    private Object getProcId() {
        return new Long(this.nextId++);
    }
    
    void handleOutputErrors() {
        try {
            this.handleOutputErrors(null, null, null);
        }
        catch (Open4GLException ex) {}
    }
    
    private void handleInputErrors(final SrcOutputStream srcOutputStream, final Exception ex, final ParameterSet set) throws Open4GLException {
        if (ex != null && ex instanceof StopException) {
            try {
                this.tracer.print("   Input STOP: Close the rquest stream.");
                srcOutputStream.stopClose();
            }
            catch (IOException ex2) {
                this.handleInputErrors(srcOutputStream, ex2, set);
            }
        }
        else {
            if (!this.broker.isConnected()) {
                this.disconnect(true, false);
                throw new NetworkException.SendDataException(36L, new Object[] { new Open4GLException(7665970990714724686L, null).getMessage() });
            }
            if (srcOutputStream.isOpen()) {
                try {
                    this.tracer.print("   Input ERROR: Close the rquest stream.");
                    srcOutputStream.badClose();
                    if (ex instanceof ClientException) {
                        this.localError = ExceptionConverter.convertToOpen4GLException((ClientException)ex);
                    }
                }
                catch (IOException ex3) {
                    this.disconnect(true, false);
                    throw new NetworkException.SendDataException(36L, new Object[] { ex3.getMessage() });
                }
            }
        }
    }
    
    private void handleOutputErrors(final InputStream inputStream, final ClientException ex, final ParameterSet set) throws Open4GLException {
        if (set != null) {
            set.cleanUp();
        }
        this.setInterrupt(null);
        if (ex == null || !(ex instanceof FGLErrorException)) {
            if (this.broker.isConnected()) {
                ResultSet.closeAll(this, this.outputSetsChain);
            }
            else {
                this.disconnect(true, false);
            }
            throw ExceptionConverter.convertToOpen4GLException(ex);
        }
        if (ex instanceof VersionErrorException) {
            throw new Open4GLException(43L, new Object[] { new Integer(((VersionErrorException)ex).getServerVersion()).toString(), new Integer(RunTimeProperties.getStreamProtocolVersion()).toString() });
        }
        Open4GLException get4GLError;
        try {
            this.tracer.print("   4GL ERROR: Reading error info...");
            get4GLError = new ErrorMessageReader(inputStream).get4GLError();
            if (get4GLError instanceof RunTime4GLException) {
                final RunTime4GLException ex2 = (RunTime4GLException)get4GLError;
                this.setRetValue(ex2.getProcReturnString(), ex2.hasProcReturnString());
            }
            while (new ProxyListReader(inputStream).nextProxyID()) {}
        }
        catch (ClientException ex3) {
            this.disconnect(true, false);
            throw ExceptionConverter.convertToSystemErrorException(ex3);
        }
        if (this.localError != null) {
            throw this.localError;
        }
        if (get4GLError instanceof RunTime4GLQuitException) {
            try {
                this.disconnect(true, false);
            }
            catch (Open4GLException ex4) {}
        }
        throw get4GLError;
    }
    
    private void init() {
        this.conversationAllocated = false;
        this.refCount = 0;
        this.nextId = 1L;
        this.interrupt = null;
        this.broker = null;
        this.thisUrl = null;
        this.outputSetsChain = null;
        this.outBuffer = new OutputBuffer(Session.MAX_REQUEST_LENGTH * 2);
        this.sessionLock = new SessionLock(this);
        this.procList = new Hashtable();
        this.returnValue = null;
        this.insideRequest = false;
        this.m_serverMajorVersion = 10;
        this.threadContextTable = new ThreadContextTable(100);
    }
    
    void deleteProceduresNoLock(final PersistentProc persistentProc, final ProcListResultSet set, final ResultSetMetaData resultSetMetaData) throws Open4GLException {
        this.dumpProcList("deleteProceduresNoLock", 4);
        this.deleteProcedures1(persistentProc, true, set, resultSetMetaData);
    }
    
    void deleteProcedures(final PersistentProc persistentProc, final ProcListResultSet set, final ResultSetMetaData resultSetMetaData) throws Open4GLException {
        this.dumpProcList("deleteProcedures", 4);
        this.deleteProcedures1(persistentProc, false, set, resultSetMetaData);
    }
    
    PersistentProc findPersistentProcByID(final long lng) {
        this.procList.keys();
        Object obj = null;
        final Enumeration<PersistentProc> keys = this.procList.keys();
        while (keys.hasMoreElements()) {
            final PersistentProc persistentProc = keys.nextElement();
            if ((long)persistentProc.getProcId() == lng) {
                obj = persistentProc;
                break;
            }
        }
        this.tracer.print("findPersistentProcByID : procID " + lng + " ===> " + obj, 4);
        return (PersistentProc)obj;
    }
    
    void dumpProcList(final String str, final int n) {
        this.procList.keys();
        if (this.tracer.getTraceLevel() < n) {
            return;
        }
        this.tracer.print("dumpProcList : " + str, n);
        final Enumeration<PersistentProc> keys = this.procList.keys();
        while (keys.hasMoreElements()) {
            final PersistentProc key = keys.nextElement();
            this.tracer.print("    procedure= " + key.toString() + "  procID= " + this.procList.get(key), n);
        }
        this.tracer.print("dumpProcList : end", n);
    }
    
    private void deleteProcedures1(final PersistentProc persistentProc, final boolean b, final ProcListResultSet set, final ResultSetMetaData resultSetMetaData) throws Open4GLException {
        int lockQueue = 0;
        if (persistentProc.deletedByServer()) {
            this.tracer.print("deleteProcedures1(" + b + ") : procedure " + persistentProc.toString() + " already deleted by server");
            return;
        }
        final DelayedDelete delayedDelete = new DelayedDelete(persistentProc, set, resultSetMetaData);
        try {
            this.insideRequest = true;
            final boolean waitIfBusy = RunTimeProperties.getWaitIfBusy();
            if (!b) {
                lockQueue = this.sessionLock.lockQueue(!waitIfBusy, null, delayedDelete);
                if (lockQueue == 0) {
                    throw new BusySessionException(53L, null);
                }
            }
            if (b || lockQueue == 1) {
                this.deleteProcedures0("delete(" + persistentProc.toString() + ")", set, resultSetMetaData);
            }
        }
        catch (InterruptedException ex3) {
            final Open4GLException ex = new Open4GLException(52L, null);
            RunTimeProperties.tracer.print(ex, 1);
            throw ex;
        }
        catch (Open4GLException ex2) {
            RunTimeProperties.tracer.print(ex2, 1);
            throw ex2;
        }
        finally {
            this.insideRequest = false;
            if (!b) {
                if (lockQueue == 1) {
                    this.tracer.print("deleteProcedures1(" + b + ") : removed " + persistentProc.toString() + "|" + this.procList.remove(persistentProc), 4);
                    this.sessionLock.unLock();
                }
            }
            else {
                this.tracer.print("deleteProcedures1(" + b + ") : removed " + persistentProc.toString() + "|" + this.procList.remove(persistentProc), 4);
            }
        }
    }
    
    private void deleteProcedures0(final String str, final ProcListResultSet set, final ResultSetMetaData resultSetMetaData) throws Open4GLException {
        if (this.outputSetsChain != null) {
            throw new Open4GLException(39L, null);
        }
        int n = 0;
        if (this.m_serverMajorVersion > 9) {
            n = 2;
        }
        final SrcOutputStream srcOutputStream = new SrcOutputStream(this.broker);
        final SinkInputStream sinkInputStream = new SinkInputStream(this.broker);
        this.localError = null;
        try {
            this.tracer.print("Deleting procedure(s) : " + str);
            this.setInterrupt(null);
            this.allocate(str);
            this.outBuffer.reset();
            final InputTableStreamer inputTableStreamer = new InputTableStreamer(this.outBuffer);
            this.outBuffer.write(9);
            inputTableStreamer.streamResultSet(set, resultSetMetaData, this.m_serverMajorVersion, n);
            srcOutputStream.write(RunTimeProperties.getStreamProtocolVersion());
            final short n2 = (short)this.outBuffer.getLen();
            srcOutputStream.write(Util.getHighByte(n2));
            srcOutputStream.write(Util.getLowByte(n2));
            srcOutputStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
        }
        catch (Exception ex) {
            this.handleInputErrors(srcOutputStream, ex, null);
            if (srcOutputStream.isOpen()) {
                try {
                    srcOutputStream.close();
                }
                catch (IOException ex2) {
                    this.handleInputErrors(srcOutputStream, ex2, null);
                }
            }
        }
        finally {
            if (srcOutputStream.isOpen()) {
                try {
                    srcOutputStream.close();
                }
                catch (IOException ex3) {
                    this.handleInputErrors(srcOutputStream, ex3, null);
                }
            }
        }
        try {
            int read = 0;
            int read2 = 0;
            try {
                sinkInputStream.read();
                sinkInputStream.read();
                sinkInputStream.read();
                read = sinkInputStream.read();
                if (read == 10) {
                    read2 = sinkInputStream.read();
                }
            }
            catch (IOException ex4) {
                ExceptionConverter.convertIOException(ex4);
            }
            if (read == 7) {
                throw new FGLErrorException();
            }
            if (read == 10) {
                throw new VersionErrorException(read2);
            }
            if (read == -1) {
                throw new ClientException(16L, new Object[] { new Integer(1).toString() });
            }
            if (read != 8) {}
        }
        catch (ClientException ex5) {
            this.handleOutputErrors(sinkInputStream, ex5, null);
            try {
                this.deallocate();
            }
            catch (BrokerException ex6) {
                this.disconnect(true, false);
                throw ExceptionConverter.convertToSystemErrorException(ex6);
            }
        }
        finally {
            try {
                this.deallocate();
            }
            catch (BrokerException ex7) {
                this.disconnect(true, false);
                throw ExceptionConverter.convertToSystemErrorException(ex7);
            }
        }
    }
    
    public String getReturnValue() {
        try {
            this.validate();
        }
        catch (Exception ex) {
            return null;
        }
        final Object value = this.threadContextTable.get();
        if (value == null && this.threadContextTable.noContext()) {
            return (String)this.returnValue;
        }
        return (String)value;
    }
    
    public void stop() {
        this.tracer.print("   STOP: Session stop() is called.");
        try {
            if (this.broker.isStopping() || this.broker.isReceiving()) {
                this.tracer.print("   STOP: broker.setStop() is called.");
                this.broker.setStop();
            }
            else {
                this.tracer.print("   STOP: broker is not receiving,  no broker.setStop().");
            }
            ResultSet.markAllInvalid(this.outputSetsChain);
        }
        catch (BrokerException ex) {
            ExceptionConverter.convertToSystemErrorException(ex);
        }
    }
    
    void throwOutput() {
        this.tracer.print("   STOP: In throwOutput().");
        if (this.broker.isStopping() || this.broker.isReceiving()) {
            this.tracer.print("   STOP: throwOutput - before flushClose().");
            new SinkInputStream(this.broker).flushClose();
        }
        this.tracer.print("   STOP: before setNotStreaming().");
        this.setNotStreaming();
    }
    
    synchronized void setInterrupt(final Interruptable interrupt) {
        this.interrupt = interrupt;
    }
    
    protected final void finalize() {
        this.tracer.print("finalize() disconnecting broker", 4);
        try {
            ASKWatchDog.deregister(this);
            this.disconnect(true, false);
        }
        catch (Throwable obj) {
            this.tracer.print("error during finalize()= " + obj, 4);
        }
    }
    
    public void shutdown() {
        this.tracer.print("shutdown() disconnecting broker", 4);
        try {
            this.disconnect(true, false);
        }
        catch (Throwable obj) {
            this.tracer.print("error during shutdown()= " + obj, 4);
        }
    }
    
    public String getSessionID() {
        return this.sessionID;
    }
    
    public String toString() {
        return this.sessionID;
    }
    
    public int getPoolState() {
        return this.poolState;
    }
    
    public int setPoolState(final int poolState) {
        final int poolState2 = this.poolState;
        this.poolState = poolState;
        return poolState2;
    }
    
    public int getASKVersion() {
        return (this.broker == null) ? 0 : this.broker.getASKVersion();
    }
    
    public boolean getServerASKEnabled() {
        return this.broker != null && this.broker.getServerASKEnabled();
    }
    
    public boolean getClientASKEnabled() {
        return this.broker != null && this.broker.getClientASKEnabled();
    }
    
    public int getClientASKActivityTimeout() {
        return (this.broker == null) ? 60 : this.broker.getClientASKActivityTimeout();
    }
    
    public int getClientASKResponseTimeout() {
        return (this.broker == null) ? 60 : this.broker.getClientASKResponseTimeout();
    }
    
    public long tsCreated() {
        return this.m_tsCreated;
    }
    
    private String newSessionID(final String str) {
        final String string;
        synchronized (Session.sessionObjCountLock) {
            ++Session.sessionObjCount;
            string = "<session-" + Session.fmt6.format(Session.sessionObjCount) + "|" + str + ">";
        }
        return string;
    }
    
    private int connectionMode() {
        return (this.m_sessionProperties == null) ? Session.SESSION_MANAGED : this.m_sessionProperties.getIntProperty("PROGRESS.Session.sessionModel");
    }
    
    private boolean waitIfBusy() {
        return (this.m_sessionProperties == null) ? RunTimeProperties.getWaitIfBusy() : this.m_sessionProperties.getBooleanProperty("PROGRESS.Session.waitIfBusy");
    }
    
    int getServerMajorVersion() {
        return this.m_serverMajorVersion;
    }
    
    public boolean serializeDatasetsAsXml() {
        boolean equals = false;
        if (this.m_sessionProperties != null) {
            final Object property = this.m_sessionProperties.getProperty("PROGRESS.Session.serializeDatasetAsXML");
            if (null != property) {
                if (property instanceof String) {
                    equals = ((String)property).equals("1");
                }
                else {
                    equals = ((int)property == 1);
                }
            }
        }
        return equals;
    }
    
    static {
        Session.MAX_REQUEST_LENGTH = 32767;
        Session.SESSION_MANAGED = 0;
        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
        Session.sessionObjCountLock = new Object();
        Session.sessionObjCount = 0;
        Session.fmt6 = new DecimalFormat("000000");
    }
}
