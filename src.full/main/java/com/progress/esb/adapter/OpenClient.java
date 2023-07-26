// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import org.w3c.dom.Node;
import com.progress.wsa.xmr.SchemaParser;
import com.progress.open4gl.proxygen.PGMetaData;
import com.progress.open4gl.ProSQLException;
import org.w3c.dom.Element;
import com.sonicsw.xqimpl.util.DOMUtils;
import com.progress.open4gl.Handle;
import com.progress.open4gl.ProResultSetMetaDataImpl;
import java.sql.ResultSet;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.Memptr;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGMethod;
import com.progress.open4gl.proxygen.PGParam;
import com.progress.open4gl.javaproxy.ParamArray;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.proxygen.PGAppObj;
import com.progress.open4gl.proxygen.PGProc;
import java.util.ArrayList;
import com.sonicsw.xqimpl.script.ParameterValueFactory;
import java.util.Iterator;
import com.sonicsw.xqimpl.script.IParameterValue;
import java.util.List;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.common.ehnlog.AppLogger;
import com.progress.esb.tools.EsbRuntimeProperties;
import java.util.Random;
import com.progress.open4gl.javaproxy.OpenProcObject;
import java.util.HashMap;
import com.progress.open4gl.javaproxy.Connection;
import com.progress.open4gl.javaproxy.OpenAppObject;

public class OpenClient
{
    public static final String CONNECTION_ID_NAME = "openedge.session-managed.id";
    protected OpenAppObject m_client;
    protected Connection m_connection;
    protected boolean m_isConnected;
    protected HashMap<String, OpenAppObject> m_appObjects;
    protected HashMap<String, OpenProcObject> m_procObjects;
    protected Random m_rnd;
    protected int m_operatingMode;
    protected String m_smHeader;
    protected String m_appServiceURL;
    protected EsbRuntimeProperties m_props;
    private AppLogger m_log;
    private OEEsbInterceptor m_oeInterceptor;
    private boolean m_doInteraction;
    private static final int SESSION_MANAGED = 0;
    private static final int SESSION_FREE = 1;
    private static final int SM_CONNECT = 0;
    private static final int SM_DISCONNECT = 1;
    private static final int PO_RELEASE = 2;
    
    public OpenClient(final int operatingMode, final String appServiceURL, final EsbRuntimeProperties props, final String smHeader, final AppLogger log) {
        this.m_client = null;
        this.m_connection = null;
        this.m_isConnected = false;
        this.m_appObjects = null;
        this.m_procObjects = null;
        this.m_rnd = null;
        this.m_operatingMode = 0;
        this.m_smHeader = null;
        this.m_appServiceURL = null;
        this.m_props = null;
        this.m_log = null;
        this.m_oeInterceptor = null;
        this.m_doInteraction = false;
        this.m_operatingMode = operatingMode;
        this.m_log = log;
        this.m_appServiceURL = appServiceURL;
        this.m_props = props;
        (this.m_connection = new Connection(appServiceURL, null, null, null)).setProperties(props.getAsProperties());
        this.m_connection.setSessionModel(this.m_operatingMode);
        this.m_procObjects = new HashMap<String, OpenProcObject>();
        if (0 == this.m_operatingMode) {
            this.m_appObjects = new HashMap<String, OpenAppObject>();
            this.m_smHeader = smHeader;
        }
        this.m_rnd = new Random();
    }
    
    public boolean isConnected() {
        return this.m_isConnected;
    }
    
    public String getSMHeader() {
        return this.m_smHeader;
    }
    
    public boolean isSessionManaged() {
        return this.m_operatingMode == 0;
    }
    
    public void setInterceptor(final OEEsbInterceptor oeInterceptor) {
        this.m_oeInterceptor = oeInterceptor;
    }
    
    private OpenAppObject getAppObj(final String original) throws ScriptEngineException, OEFaultException {
        if (this.m_operatingMode == 1) {
            if (!this.m_isConnected) {
                try {
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811875187L, null);
                    }
                    if (this.m_doInteraction) {
                        if (this.m_log.ifLogVerbose(4L, 2)) {
                            this.m_log.logVerbose(2, this.m_oeInterceptor.toString());
                        }
                        this.m_connection.setProperty("PROGRESS.Session.manifest", this.m_oeInterceptor);
                    }
                    this.m_client = new OpenAppObject(this.m_connection, "", this.m_log);
                    this.m_isConnected = true;
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811875188L, null);
                    }
                    return this.m_client;
                }
                catch (Open4GLException ex) {
                    this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875189L, new String[] { ex.getMessage() }));
                    throw new OEFaultException(ex);
                }
                catch (Exception linkedException) {
                    final ScriptEngineException ex2 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875189L, new String[] { linkedException.getMessage() }), 0);
                    ex2.setLinkedException((Throwable)linkedException);
                    throw ex2;
                }
            }
            return this.m_client;
        }
        if (this.m_log.ifLogVerbose(4L, 2)) {
            this.m_log.logVerbose(2, 8607504787811875190L, new String[] { new String(original) });
        }
        final OpenAppObject openAppObject = this.m_appObjects.get(original);
        if (null == openAppObject) {
            throw new ScriptEngineException(AppLogger.formatMessage(8607504787811875191L, new String[] { new String(original) }), 0);
        }
        return openAppObject;
    }
    
    private String getConnId(final List list) {
        final Iterator<IParameterValue> iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                final IParameterValue parameterValue = iterator.next();
                if (parameterValue.getParamName().equals("openedge.session-managed.id")) {
                    return parameterValue.getAsString();
                }
                continue;
            }
            catch (ScriptEngineException ex) {}
        }
        return null;
    }
    
    private String connectSMAppServer(final List list, final ParameterValueFactory parameterValueFactory, final ArrayList list2) throws ScriptEngineException, OEFaultException {
        try {
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875187L, null);
            }
            final String asString = this.getMappedParam(list, "username").getAsString();
            final String asString2 = this.getMappedParam(list, "password").getAsString();
            final String asString3 = this.getMappedParam(list, "AppServerInfo").getAsString();
            Connection connection;
            if (null != asString || null != asString2 || null != asString3) {
                connection = new Connection(this.m_appServiceURL, asString, asString2, asString3);
                connection.setProperties(this.m_props.getAsProperties());
                connection.setSessionModel(this.m_operatingMode);
            }
            else {
                connection = this.m_connection;
            }
            final OpenAppObject value = new OpenAppObject(connection, "");
            if (this.m_doInteraction) {
                this.m_oeInterceptor.analyze("_connect");
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, this.m_oeInterceptor.toString());
                }
                this.m_connection.setProperty("PROGRESS.Session.manifest", this.m_oeInterceptor);
            }
            final String s = (String)value._getConnectionId();
            this.m_appObjects.put(s, value);
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875190L, new String[] { new String(s) });
            }
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875188L, null);
            }
            return s;
        }
        catch (Open4GLException ex) {
            this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875189L, new String[] { ex.getMessage() }));
            throw new OEFaultException(ex);
        }
        catch (Exception ex2) {
            final String formatMessage = AppLogger.formatMessage(8607504787811875189L, new String[] { ex2.getMessage() });
            this.m_log.logStackTrace(formatMessage, ex2);
            throw new ScriptEngineException(formatMessage, 0);
        }
    }
    
    private void disconnectSMAppServer(final List list) throws ScriptEngineException, OEFaultException {
        if (this.m_doInteraction) {
            this.m_oeInterceptor.analyze("_disconnect");
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, this.m_oeInterceptor.toString());
            }
            this.m_connection.setProperty("PROGRESS.Session.manifest", this.m_oeInterceptor);
        }
        final String connId = this.getConnId(list);
        if (null == connId) {
            throw new ScriptEngineException("Unable to locate connection id", 0);
        }
        try {
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875192L, new String[] { new String(connId) });
            }
            final OpenAppObject openAppObject = this.m_appObjects.remove(connId);
            if (null == openAppObject) {
                throw new ScriptEngineException(AppLogger.formatMessage(8607504787811875191L, new String[] { new String(connId) }), 0);
            }
            openAppObject._release();
        }
        catch (Open4GLException ex) {
            this.m_log.logBasic(0, "Unable to release AppObject: " + ex.getMessage());
            throw new OEFaultException(ex);
        }
        catch (Exception linkedException) {
            final ScriptEngineException ex2 = new ScriptEngineException("Unable to release AppObject: " + linkedException.getMessage(), 0);
            ex2.setLinkedException((Throwable)linkedException);
            throw ex2;
        }
    }
    
    private void releaseProcObject(final List list) throws ScriptEngineException, OEFaultException {
        final IParameterValue mappedParam = this.getMappedParam(list, "_procid");
        try {
            final String asString = mappedParam.getAsString();
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875193L, new String[] { new String(asString) });
            }
            final OpenProcObject openProcObject = this.m_procObjects.remove(asString);
            if (null == openProcObject) {
                throw new ScriptEngineException(AppLogger.formatMessage(8607504787811875194L, new String[] { new String(asString) }), 0);
            }
            openProcObject._release();
        }
        catch (Open4GLException ex) {
            this.m_log.logBasic(0, "Unable to release ProcObject: " + ex.getMessage());
            throw new OEFaultException(ex);
        }
        catch (Exception linkedException) {
            final ScriptEngineException ex2 = new ScriptEngineException("Unable to release ProcObject: " + linkedException.getMessage(), 0);
            ex2.setLinkedException((Throwable)linkedException);
            throw ex2;
        }
    }
    
    private void runProcedure(final List list, final PGProc pgProc, final ParameterValueFactory parameterValueFactory, final ArrayList<IParameterValue> list2) throws ScriptEngineException, OEFaultException {
        String connId = null;
        String generateProcId = null;
        if (this.m_operatingMode == 0) {
            connId = this.getConnId(list);
            if (null == connId) {
                throw new ScriptEngineException(AppLogger.formatMessage(8607504787811875196L, new String[] { new String(this.m_smHeader) }), 0);
            }
        }
        final OpenAppObject appObj = this.getAppObj(connId);
        ParamArray paramArray = this.createParamArray(pgProc, list);
        final String procPath = pgProc.getProcPath();
        String s;
        if (procPath == null || procPath.length() == 0) {
            s = pgProc.getProcName();
        }
        else {
            s = PGAppObj.fixOSPath(2, procPath) + pgProc.getProcName();
        }
        final String procExt = pgProc.getProcExt();
        if (procExt.length() > 0) {
            if (procExt.charAt(0) == '.') {
                s += procExt;
            }
            else {
                s = s + "." + procExt;
            }
        }
        try {
            if (pgProc.isPersistent()) {
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811875197L, new String[] { new String(s) });
                }
                final OpenProcObject po = appObj.createPO(s, paramArray);
                generateProcId = this.generateProcId(pgProc);
                this.m_procObjects.put(generateProcId, po);
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811875198L, new String[] { new String(generateProcId) });
                }
            }
            else {
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811875199L, new String[] { new String(s) });
                }
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, this.m_oeInterceptor.toString());
                }
                appObj.runProc(s, paramArray);
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811875200L, null);
                }
            }
        }
        catch (Open4GLException ex) {
            boolean b = false;
            String procReturnString = null;
            if (ex.getMessageId() == 7175L) {
                try {
                    this.m_isConnected = false;
                    this.m_log.logBasic(0, "Reconnecting to AppServer...");
                    paramArray = this.createParamArray(pgProc, list);
                    this.getAppObj(connId).runProc(s, paramArray);
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811875200L, null);
                    }
                    b = true;
                }
                catch (Open4GLException ex2) {
                    ex = (RunTime4GLException)ex2;
                }
            }
            if (!b) {
                if (ex instanceof RunTime4GLException && ex.hasProcReturnString()) {
                    procReturnString = ex.getProcReturnString();
                }
                this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875201L, new String[] { ex.getMessage(), procReturnString }));
                OEFaultException ex3;
                if (null != procReturnString) {
                    ex3 = new OEFaultException(ex, procReturnString);
                }
                else {
                    ex3 = new OEFaultException(ex);
                }
                throw ex3;
            }
        }
        catch (Exception linkedException) {
            final ScriptEngineException ex4 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875201L, new String[] { linkedException.getMessage() }), 0);
            ex4.setLinkedException((Throwable)linkedException);
            throw ex4;
        }
        final PGParam[] parameters = pgProc.getProcDetail().getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getParamMode() != 1) {
                final String paramName = parameters[i].getParamName();
                final int value = parameters[i].getParamOrdinal() - 1;
                final IParameterValue mappedParam = this.getMappedParam(list, paramName);
                if (this.m_log.ifLogExtended(4L, 2)) {
                    this.m_log.logExtended(2, 8607504787811875202L, new Object[] { new String(paramName), new Integer(value) });
                }
                final String s2 = null;
                Object outputParameter;
                try {
                    outputParameter = paramArray.getOutputParameter(value);
                }
                catch (Open4GLException linkedException2) {
                    final ScriptEngineException ex5 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875203L, new String[] { new String(paramName), linkedException2.getMessage() }), 0);
                    ex5.setLinkedException((Throwable)linkedException2);
                    throw ex5;
                }
                IParameterValue e;
                if (null != outputParameter) {
                    final String serializeParameter = this.serializeParameter(outputParameter, parameters[i]);
                    e = parameterValueFactory.createParameterValue(mappedParam.getParamName(), mappedParam.getBaseType(), serializeParameter);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        if (null != serializeParameter) {
                            String string;
                            if (serializeParameter.length() > 64) {
                                string = serializeParameter.substring(0, 64) + "-[truncated]";
                            }
                            else {
                                string = serializeParameter;
                            }
                            this.m_log.logExtended(2, 8607504787811875204L, new String[] { new String(paramName), new String(string) });
                        }
                    }
                }
                else {
                    e = parameterValueFactory.createParameterValue(mappedParam.getParamName(), mappedParam.getBaseType(), s2);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, 8607504787811875204L, new String[] { new String(paramName), new String("unknown") });
                    }
                }
                list2.add(e);
            }
        }
        if (pgProc.getProcDetail().usesReturnValue()) {
            final IParameterValue mappedParam2 = this.getMappedParam(list, pgProc.getProcDetail().getReturnValue().getParamName());
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875205L, null);
            }
            String procReturnString2 = paramArray.getProcReturnString();
            final IParameterValue parameterValue = parameterValueFactory.createParameterValue(mappedParam2.getParamName(), mappedParam2.getBaseType(), procReturnString2);
            if (this.m_log.ifLogExtended(4L, 2)) {
                if (null == procReturnString2) {
                    procReturnString2 = "unknown";
                }
                this.m_log.logExtended(2, 8607504787811875206L, new String[] { new String(procReturnString2) });
            }
            list2.add(parameterValue);
        }
        if (null != generateProcId) {
            final IParameterValue mappedParam3 = this.getMappedParam(list, "_procid");
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875208L, null);
            }
            final IParameterValue parameterValue2 = parameterValueFactory.createParameterValue(mappedParam3.getParamName(), mappedParam3.getBaseType(), generateProcId);
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875206L, new String[] { new String(generateProcId) });
            }
            list2.add(parameterValue2);
        }
    }
    
    private void runInternalProcedure(final OpenProcObject openProcObject, final List list, final PGProc pgProc, final int n, final ParameterValueFactory parameterValueFactory, final ArrayList<IParameterValue> list2) throws ScriptEngineException, OEFaultException {
        final PGMethod internalProcs = pgProc.getProcDetail().getInternalProcs(n);
        final String internalProc = internalProcs.getInternalProc();
        final ParamArray paramArray = this.createParamArray(internalProcs, list);
        if (internalProcs.getProcType() == 2) {
            try {
                paramArray.setReturnType(internalProcs.getMethodDetail().getReturnValue().getParamType());
            }
            catch (Open4GLException ex) {
                this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875214L, new String[] { new String(internalProc), ex.getMessage() }));
                throw new OEFaultException(ex);
            }
            catch (Exception linkedException) {
                final ScriptEngineException ex2 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875214L, new String[] { new String(internalProc), linkedException.getMessage() }), 0);
                ex2.setLinkedException((Throwable)linkedException);
                throw ex2;
            }
        }
        try {
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875212L, new String[] { new String(internalProc) });
            }
            openProcObject.runProc(internalProc, paramArray);
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811875200L, null);
            }
        }
        catch (Open4GLException ex3) {
            this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875214L, new String[] { new String(internalProc), ex3.getMessage() }));
            throw new OEFaultException(ex3);
        }
        catch (Exception linkedException2) {
            final ScriptEngineException ex4 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875214L, new String[] { new String(internalProc), linkedException2.getMessage() }), 0);
            ex4.setLinkedException((Throwable)linkedException2);
            throw ex4;
        }
        final PGParam[] parameters = internalProcs.getMethodDetail().getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getParamMode() != 1) {
                final String paramName = parameters[i].getParamName();
                final int value = parameters[i].getParamOrdinal() - 1;
                final IParameterValue mappedParam = this.getMappedParam(list, paramName);
                if (this.m_log.ifLogExtended(4L, 2)) {
                    this.m_log.logExtended(2, 8607504787811875202L, new Object[] { new String(paramName), new Integer(value) });
                }
                final String s = null;
                Object outputParameter;
                try {
                    outputParameter = paramArray.getOutputParameter(value);
                }
                catch (Open4GLException linkedException3) {
                    final ScriptEngineException ex5 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875203L, new String[] { new String(paramName), linkedException3.getMessage() }), 0);
                    ex5.setLinkedException((Throwable)linkedException3);
                    throw ex5;
                }
                IParameterValue e;
                if (null != outputParameter) {
                    final String serializeParameter = this.serializeParameter(outputParameter, parameters[i]);
                    e = parameterValueFactory.createParameterValue(mappedParam.getParamName(), mappedParam.getBaseType(), serializeParameter);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, 8607504787811875204L, new String[] { new String(paramName), new String(serializeParameter) });
                    }
                }
                else {
                    e = parameterValueFactory.createParameterValue(mappedParam.getParamName(), mappedParam.getBaseType(), s);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, 8607504787811875204L, new String[] { new String(paramName), new String("unknown") });
                    }
                }
                list2.add(e);
            }
        }
        if (internalProcs.getProcType() == 2) {
            final PGParam returnValue = internalProcs.getMethodDetail().getReturnValue();
            final IParameterValue mappedParam2 = this.getMappedParam(list, returnValue.getParamName());
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875205L, null);
            }
            final String serializeParameter2 = this.serializeParameter(paramArray.getReturnValue(), returnValue);
            final IParameterValue parameterValue = parameterValueFactory.createParameterValue(mappedParam2.getParamName(), mappedParam2.getBaseType(), serializeParameter2);
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875206L, new String[] { new String(serializeParameter2) });
            }
            list2.add(parameterValue);
        }
        else if (internalProcs.getMethodDetail().usesReturnValue()) {
            final IParameterValue mappedParam3 = this.getMappedParam(list, internalProcs.getMethodDetail().getReturnValue().getParamName());
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875205L, null);
            }
            final String procReturnString = paramArray.getProcReturnString();
            final IParameterValue parameterValue2 = parameterValueFactory.createParameterValue(mappedParam3.getParamName(), mappedParam3.getBaseType(), procReturnString);
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811875206L, new String[] { new String(procReturnString) });
            }
            list2.add(parameterValue2);
        }
    }
    
    private ParamArray createParamArray(final PGProc pgProc, final List list) throws ScriptEngineException, OEFaultException {
        final PGParam[] parameters = pgProc.getProcDetail().getParameters();
        final ParamArray paramArray = new ParamArray(parameters.length);
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getParamMode() == 2) {
                this.addOutputParam(parameters[i], paramArray);
            }
            else {
                this.addInputParam(parameters[i], paramArray, list);
            }
        }
        return paramArray;
    }
    
    private ParamArray createParamArray(final PGMethod pgMethod, final List list) throws ScriptEngineException, OEFaultException {
        final PGParam[] parameters = pgMethod.getMethodDetail().getParameters();
        final ParamArray paramArray = new ParamArray(parameters.length);
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getParamMode() == 2) {
                this.addOutputParam(parameters[i], paramArray);
            }
            else {
                this.addInputParam(parameters[i], paramArray, list);
            }
        }
        return paramArray;
    }
    
    public String execute(final List list, final PGProc pgProc, final int n, final int n2, final ParameterValueFactory parameterValueFactory, final ArrayList<IParameterValue> list2) throws ScriptEngineException, OEFaultException {
        String s = null;
        this.m_doInteraction = this.m_oeInterceptor.isInstrumented();
        if (-1 < n) {
            switch (n) {
                case 0: {
                    s = this.connectSMAppServer(list, parameterValueFactory, list2);
                    break;
                }
                case 1: {
                    this.disconnectSMAppServer(list);
                    break;
                }
                case 2: {
                    this.releaseProcObject(list);
                    break;
                }
            }
        }
        else if (n2 == -1) {
            if (this.m_doInteraction) {
                final String procPath = pgProc.getProcPath();
                String s2;
                if (procPath == null || procPath.length() == 0) {
                    s2 = pgProc.getProcName();
                }
                else {
                    s2 = procPath + pgProc.getProcName();
                }
                this.m_oeInterceptor.analyze(s2);
            }
            this.runProcedure(list, pgProc, parameterValueFactory, list2);
        }
        else {
            OpenProcObject po;
            if (pgProc.getExecutionMode() == 1) {
                this.m_log.logBasic(0, "Executing Persistent Procedure operation");
                po = this.m_procObjects.get(this.getMappedParam(list, "_procid").getAsString());
            }
            else {
                try {
                    this.m_log.logBasic(0, "Executing Single-run operation");
                    if (this.m_operatingMode == 0) {
                        s = this.getConnId(list);
                        if (null == s) {
                            throw new ScriptEngineException(AppLogger.formatMessage(8607504787811875196L, new String[] { new String(this.m_smHeader) }), 0);
                        }
                    }
                    final OpenAppObject appObj = this.getAppObj(s);
                    final String procPath2 = pgProc.getProcPath();
                    String s3;
                    if (procPath2 == null || procPath2.length() == 0) {
                        s3 = pgProc.getProcName();
                    }
                    else {
                        s3 = PGAppObj.fixOSPath(2, procPath2) + pgProc.getProcName();
                    }
                    po = appObj.createPO(s3, 9);
                }
                catch (Open4GLException ex) {
                    String procReturnString = null;
                    if (ex instanceof RunTime4GLException && ((RunTime4GLException)ex).hasProcReturnString()) {
                        procReturnString = ((RunTime4GLException)ex).getProcReturnString();
                    }
                    this.m_log.logBasic(0, AppLogger.formatMessage(8607504787811875201L, new String[] { ex.getMessage(), procReturnString }));
                    OEFaultException ex2;
                    if (null != procReturnString) {
                        ex2 = new OEFaultException(ex, procReturnString);
                    }
                    else {
                        ex2 = new OEFaultException(ex);
                    }
                    throw ex2;
                }
                catch (Exception linkedException) {
                    final ScriptEngineException ex3 = new ScriptEngineException(AppLogger.formatMessage(8607504787811875201L, new String[] { linkedException.getMessage() }), 0);
                    ex3.setLinkedException((Throwable)linkedException);
                    throw ex3;
                }
            }
            if (this.m_doInteraction) {
                final String procPath3 = pgProc.getProcPath();
                String str;
                if (procPath3 == null || procPath3.length() == 0) {
                    str = pgProc.getProcName();
                }
                else {
                    str = procPath3 + pgProc.getProcName();
                }
                this.m_oeInterceptor.analyze(str + "." + pgProc.getProcDetail().getInternalProcs(n2).getInternalProc());
            }
            this.runInternalProcedure(po, list, pgProc, n2, parameterValueFactory, list2);
        }
        return s;
    }
    
    private String generateProcId(final PGProc pgProc) {
        return pgProc.getProcName() + ":" + Integer.toString(this.m_rnd.nextInt());
    }
    
    private String serializeParameter(final Object o, final PGParam pgParam) throws ScriptEngineException, OEFaultException {
        String s = null;
        switch (pgParam.getParamType()) {
            case 1:
            case 39: {
                s = EsbChar.serializeParameter(o, pgParam, null);
                break;
            }
            case 2: {
                s = EsbDate.serializeParameter(o, pgParam, null);
                break;
            }
            case 3: {
                s = EsbLogical.serializeParameter(o, pgParam, null);
                break;
            }
            case 4: {
                s = EsbInteger.serializeParameter(o, pgParam, null);
                break;
            }
            case 5: {
                s = EsbDecimal.serializeParameter(o, pgParam, null);
                break;
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                s = EsbInt64.serializeParameter(o, pgParam, null);
                break;
            }
            case 8:
            case 11:
            case 13: {
                s = EsbMemptr.serializeParameter(o, pgParam, null);
                break;
            }
            case 15:
            case 17: {
                s = EsbResultSet.serializeParameter(o, pgParam);
                break;
            }
            case 36:
            case 37: {
                s = EsbDataSet.serializeParameter(o, pgParam);
                break;
            }
            case 34: {
                s = EsbDateTime.serializeParameter(o, pgParam, null);
                break;
            }
            case 40: {
                s = EsbDateTimeTZ.serializeParameter(o, pgParam, null);
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    private void addOutputParam(final PGParam pgParam, final ParamArray paramArray) throws ScriptEngineException, OEFaultException {
        final String paramName = pgParam.getParamName();
        final int value = pgParam.getParamOrdinal() - 1;
        final int paramType = pgParam.getParamType();
        final int extent = pgParam.getExtent();
        if (this.m_log.ifLogExtended(4L, 2)) {
            this.m_log.logExtended(2, 8607504787811875216L, new Object[] { new String(paramName), new Integer(value), new String(Parameter.proToName(paramType)), new Integer(extent) });
        }
        try {
            switch (paramType) {
                case 1: {
                    if (pgParam.isExtentField()) {
                        paramArray.addCharacterArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addCharacter(value, null, 2);
                    break;
                }
                case 39: {
                    if (pgParam.isExtentField()) {
                        paramArray.addLongcharArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addLongchar(value, null, 2);
                    break;
                }
                case 2: {
                    if (pgParam.isExtentField()) {
                        paramArray.addDateArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addDate(value, null, 2);
                    break;
                }
                case 3: {
                    if (pgParam.isExtentField()) {
                        paramArray.addLogicalArray(value, (boolean[])null, 2, extent);
                        break;
                    }
                    paramArray.addLogical(value, null, 2);
                    break;
                }
                case 4: {
                    if (pgParam.isExtentField()) {
                        paramArray.addIntegerArray(value, (Integer[])null, 2, extent);
                        break;
                    }
                    paramArray.addInteger(value, null, 2);
                    break;
                }
                case 5: {
                    if (pgParam.isExtentField()) {
                        paramArray.addDecimalArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addDecimal(value, null, 2);
                    break;
                }
                case 7: {
                    if (pgParam.isExtentField()) {
                        paramArray.addRecidArray(value, (Long[])null, 2, extent);
                        break;
                    }
                    paramArray.addRecid(value, null, 2);
                    break;
                }
                case 8: {
                    if (pgParam.isExtentField()) {
                        paramArray.addRawArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addRaw(value, null, 2);
                    break;
                }
                case 11: {
                    if (pgParam.isExtentField()) {
                        paramArray.addMemptrArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addMemptr(value, null, 2);
                    break;
                }
                case 13: {
                    if (pgParam.isExtentField()) {
                        paramArray.addRowidArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addRowid(value, null, 2);
                    break;
                }
                case 14: {
                    if (pgParam.isExtentField()) {
                        paramArray.addCOMHandleArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addCOMHandle(value, null, 2);
                    break;
                }
                case 15: {
                    paramArray.addTable(value, null, 2, this.genMetaData(pgParam));
                    break;
                }
                case 17: {
                    paramArray.addTableHandle(value, null, 2, new ProResultSetMetaDataImpl(0));
                    break;
                }
                case 36: {
                    paramArray.addDataset(value, null, 2);
                    break;
                }
                case 37: {
                    paramArray.addDatasetHandle(value, null, 2);
                    break;
                }
                case 34: {
                    if (pgParam.isExtentField()) {
                        paramArray.addDatetimeArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addDatetime(value, null, 2);
                    break;
                }
                case 40: {
                    if (pgParam.isExtentField()) {
                        paramArray.addDatetimeTZArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addDatetimeTZ(value, null, 2);
                    break;
                }
                case 41: {
                    if (pgParam.isExtentField()) {
                        paramArray.addInt64Array(value, (long[])null, 2, extent);
                        break;
                    }
                    paramArray.addInt64(value, null, 2);
                    break;
                }
                case 10: {
                    if (pgParam.isExtentField()) {
                        paramArray.addHandleArray(value, null, 2, extent);
                        break;
                    }
                    paramArray.addHandle(value, null, 2);
                    break;
                }
                default: {
                    throw new ScriptEngineException("Invalid parameter type: " + Integer.toString(paramType), 0);
                }
            }
        }
        catch (Open4GLException linkedException) {
            final ScriptEngineException ex = new ScriptEngineException("Unable to add output parameter: " + linkedException.getMessage(), 0);
            ex.setLinkedException((Throwable)linkedException);
            throw ex;
        }
    }
    
    private void addInputParam(final PGParam pgParam, final ParamArray paramArray, final List list) throws ScriptEngineException, OEFaultException {
        final String paramName = pgParam.getParamName();
        final int value = pgParam.getParamOrdinal() - 1;
        final int paramType = pgParam.getParamType();
        final int paramMode = pgParam.getParamMode();
        final int extent = pgParam.getExtent();
        final IParameterValue mappedParam = this.getMappedParam(list, paramName);
        final String asString = mappedParam.getAsString();
        if (this.m_log.ifLogExtended(4L, 2)) {
            String string;
            if (null == asString) {
                string = "?";
            }
            else if (asString.length() > 64) {
                string = asString.substring(0, 64) + "-[truncated]";
            }
            else {
                string = asString;
            }
            this.m_log.logExtended(2, 8607504787811875218L, new Object[] { new String(paramName), new Integer(value), new String(Parameter.proToName(paramType)), new Integer(extent), new String(string) });
        }
        try {
            switch (paramType) {
                case 1: {
                    if (pgParam.isExtentField()) {
                        String[] array = null;
                        final Element asElement = mappedParam.getAsElement();
                        if (null != asElement) {
                            array = (String[])EsbChar.parseParameter(asElement, pgParam);
                        }
                        paramArray.addCharacterArray(value, array, paramMode, extent);
                        break;
                    }
                    paramArray.addCharacter(value, (String)EsbChar.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 39: {
                    if (pgParam.isExtentField()) {
                        String[] array2 = null;
                        final Element asElement2 = mappedParam.getAsElement();
                        if (null != asElement2) {
                            array2 = (String[])EsbChar.parseParameter(asElement2, pgParam);
                        }
                        paramArray.addLongcharArray(value, array2, paramMode, extent);
                        break;
                    }
                    paramArray.addLongchar(value, (String)EsbChar.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 2: {
                    if (pgParam.isExtentField()) {
                        GregorianCalendar[] array3 = null;
                        final Element asElement3 = mappedParam.getAsElement();
                        if (null != asElement3) {
                            array3 = (GregorianCalendar[])EsbDate.parseParameter(asElement3, pgParam);
                        }
                        paramArray.addDateArray(value, array3, paramMode, extent);
                        break;
                    }
                    paramArray.addDate(value, (GregorianCalendar)EsbDate.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 3: {
                    if (pgParam.isExtentField()) {
                        Boolean[] array4 = null;
                        final Element asElement4 = mappedParam.getAsElement();
                        if (null != asElement4) {
                            array4 = (Boolean[])EsbLogical.parseParameter(asElement4, pgParam);
                        }
                        paramArray.addLogicalArray(value, array4, paramMode, extent);
                        break;
                    }
                    paramArray.addLogical(value, (Boolean)EsbLogical.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 4: {
                    if (pgParam.isExtentField()) {
                        Integer[] array5 = null;
                        final Element asElement5 = mappedParam.getAsElement();
                        if (null != asElement5) {
                            array5 = (Integer[])EsbInteger.parseParameter(asElement5, pgParam);
                        }
                        paramArray.addIntegerArray(value, array5, paramMode, extent);
                        break;
                    }
                    paramArray.addInteger(value, (Integer)EsbInteger.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 5: {
                    if (pgParam.isExtentField()) {
                        BigDecimal[] array6 = null;
                        final Element asElement6 = mappedParam.getAsElement();
                        if (null != asElement6) {
                            array6 = (BigDecimal[])EsbDecimal.parseParameter(asElement6, pgParam);
                        }
                        paramArray.addDecimalArray(value, array6, paramMode, extent);
                        break;
                    }
                    paramArray.addDecimal(value, (BigDecimal)EsbDecimal.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 11: {
                    if (pgParam.isExtentField()) {
                        Memptr[] array7 = null;
                        final Element asElement7 = mappedParam.getAsElement();
                        if (null != asElement7) {
                            array7 = (Memptr[])EsbMemptr.parseParameter(asElement7, pgParam);
                        }
                        paramArray.addMemptrArray(value, array7, paramMode, extent);
                        break;
                    }
                    paramArray.addMemptr(value, (Memptr)EsbMemptr.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 15: {
                    final ProResultSetMetaDataImpl genMetaData = this.genMetaData(pgParam);
                    paramArray.addTable(value, (ResultSet)EsbResultSet.parseParameter(mappedParam.getAsElement(), pgParam, genMetaData), paramMode, genMetaData);
                    break;
                }
                case 17: {
                    final Element asElement8 = mappedParam.getAsElement();
                    final ProResultSetMetaDataImpl genMetaData2 = this.genMetaData(asElement8);
                    if (null == asElement8) {
                        paramArray.addTableHandle(value, null, paramMode, new ProResultSetMetaDataImpl(0));
                        break;
                    }
                    paramArray.addTableHandle(value, (ResultSet)EsbResultSet.parseParameter(DOMUtils.getImmediateChildElementByName(asElement8, "Data"), pgParam, genMetaData2), paramMode, genMetaData2);
                    break;
                }
                case 34: {
                    if (pgParam.isExtentField()) {
                        GregorianCalendar[] array8 = null;
                        final Element asElement9 = mappedParam.getAsElement();
                        if (null != asElement9) {
                            array8 = (GregorianCalendar[])EsbDateTime.parseParameter(asElement9, pgParam);
                        }
                        paramArray.addDatetimeArray(value, array8, paramMode, extent);
                        break;
                    }
                    paramArray.addDatetime(value, (GregorianCalendar)EsbDateTime.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 40: {
                    if (pgParam.isExtentField()) {
                        GregorianCalendar[] array9 = null;
                        final Element asElement10 = mappedParam.getAsElement();
                        if (null != asElement10) {
                            array9 = (GregorianCalendar[])EsbDateTimeTZ.parseParameter(asElement10, pgParam);
                        }
                        paramArray.addDatetimeTZArray(value, array9, paramMode, extent);
                        break;
                    }
                    paramArray.addDatetimeTZ(value, (GregorianCalendar)EsbDateTimeTZ.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 41: {
                    if (pgParam.isExtentField()) {
                        Long[] array10 = null;
                        final Element asElement11 = mappedParam.getAsElement();
                        if (null != asElement11) {
                            array10 = (Long[])EsbInt64.parseParameter(asElement11, pgParam);
                        }
                        paramArray.addInt64Array(value, array10, paramMode, extent);
                        break;
                    }
                    paramArray.addInt64(value, (Long)EsbInt64.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 10: {
                    if (pgParam.isExtentField()) {
                        Handle[] array11 = null;
                        final Element asElement12 = mappedParam.getAsElement();
                        if (null != asElement12) {
                            array11 = (Handle[])EsbInt64.parseParameter(asElement12, pgParam);
                        }
                        paramArray.addHandleArray(value, array11, paramMode, extent);
                        break;
                    }
                    Handle handle = null;
                    final Long n = (Long)EsbInt64.parseParameter(asString, pgParam);
                    if (null != n) {
                        handle = new Handle(n);
                    }
                    paramArray.addHandle(value, handle, paramMode);
                    break;
                }
                case 14: {
                    if (pgParam.isExtentField()) {
                        COMHandle[] array12 = null;
                        final Element asElement13 = mappedParam.getAsElement();
                        if (null != asElement13) {
                            array12 = (COMHandle[])EsbInt64.parseParameter(asElement13, pgParam);
                        }
                        paramArray.addCOMHandleArray(value, array12, paramMode, extent);
                        break;
                    }
                    COMHandle comHandle = null;
                    final Long n2 = (Long)EsbInt64.parseParameter(asString, pgParam);
                    if (null != n2) {
                        comHandle = new COMHandle(n2);
                    }
                    paramArray.addCOMHandle(value, comHandle, paramMode);
                    break;
                }
                case 7: {
                    if (pgParam.isExtentField()) {
                        Long[] array13 = null;
                        final Element asElement14 = mappedParam.getAsElement();
                        if (null != asElement14) {
                            array13 = (Long[])EsbInt64.parseParameter(asElement14, pgParam);
                        }
                        paramArray.addRecidArray(value, array13, paramMode, extent);
                        break;
                    }
                    paramArray.addRecid(value, (Long)EsbInt64.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 8: {
                    if (pgParam.isExtentField()) {
                        byte[][] array14 = null;
                        final Element asElement15 = mappedParam.getAsElement();
                        if (null != asElement15) {
                            array14 = (byte[][])EsbMemptr.parseParameter(asElement15, pgParam);
                        }
                        paramArray.addRawArray(value, array14, paramMode, extent);
                        break;
                    }
                    final Memptr memptr = (Memptr)EsbMemptr.parseParameter(asString, pgParam);
                    if (null == memptr) {
                        paramArray.addRaw(value, null, paramMode);
                    }
                    else {
                        paramArray.addRaw(value, memptr.getBytes(), paramMode);
                    }
                    break;
                }
                case 13: {
                    if (pgParam.isExtentField()) {
                        Rowid[] array15 = null;
                        final Element asElement16 = mappedParam.getAsElement();
                        if (null != asElement16) {
                            array15 = (Rowid[])EsbMemptr.parseParameter(asElement16, pgParam);
                        }
                        paramArray.addRowidArray(value, array15, paramMode, extent);
                        break;
                    }
                    Rowid rowid = null;
                    final Memptr memptr2 = (Memptr)EsbMemptr.parseParameter(asString, pgParam);
                    if (null != memptr2) {
                        rowid = new Rowid(memptr2.getBytes());
                    }
                    paramArray.addRowid(value, rowid, paramMode);
                    break;
                }
                case 36: {
                    paramArray.addDataset(value, (String)EsbDataSet.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                case 37: {
                    paramArray.addDatasetHandle(value, (String)EsbDataSet.parseParameter(asString, pgParam), paramMode);
                    break;
                }
                default: {
                    throw new ScriptEngineException("Invalid parameter type: " + Integer.toString(paramType), 0);
                }
            }
        }
        catch (Open4GLException linkedException) {
            final ScriptEngineException ex = new ScriptEngineException("Unable to add inout parameter: " + linkedException.getMessage(), 0);
            ex.setLinkedException((Throwable)linkedException);
            throw ex;
        }
    }
    
    private IParameterValue getMappedParam(final List list, final String s) {
        IParameterValue parameterValue = null;
        for (final IParameterValue parameterValue2 : list) {
            if (s.equals(parameterValue2.getParamName())) {
                parameterValue = parameterValue2;
                break;
            }
        }
        return parameterValue;
    }
    
    private ProResultSetMetaDataImpl genMetaData(final PGParam pgParam) throws ScriptEngineException {
        final PGMetaData[] metaData = pgParam.getMetaData();
        final int length = metaData.length;
        final ProResultSetMetaDataImpl proResultSetMetaDataImpl = new ProResultSetMetaDataImpl(length);
        if (15 != pgParam.getParamType()) {
            throw new ScriptEngineException("Wrong param type", 0);
        }
        for (int i = 0; i < length; ++i) {
            try {
                proResultSetMetaDataImpl.setFieldMetaData(i + 1, metaData[i].getFieldName(), metaData[i].getExtent(), metaData[i].getType());
            }
            catch (ProSQLException linkedException) {
                final ScriptEngineException ex = new ScriptEngineException(AppLogger.formatMessage(8607504787811875220L, new String[] { linkedException.getMessage() }), 0);
                ex.setLinkedException((Throwable)linkedException);
                throw ex;
            }
        }
        return proResultSetMetaDataImpl;
    }
    
    private ProResultSetMetaDataImpl genMetaData(final Element element) throws ScriptEngineException {
        final ProResultSetMetaDataImpl proResultSetMetaDataImpl = new ProResultSetMetaDataImpl(0);
        if (null == element) {
            return proResultSetMetaDataImpl;
        }
        ProResultSetMetaDataImpl proResultSetMetaDataImpl2;
        try {
            proResultSetMetaDataImpl2 = new ProResultSetMetaDataImpl(new SchemaParser().parseDataElement(element));
        }
        catch (Exception linkedException) {
            final ScriptEngineException ex = new ScriptEngineException(AppLogger.formatMessage(8607504787811875220L, new String[] { linkedException.getMessage() }), 0);
            ex.setLinkedException((Throwable)linkedException);
            throw ex;
        }
        return proResultSetMetaDataImpl2;
    }
}
