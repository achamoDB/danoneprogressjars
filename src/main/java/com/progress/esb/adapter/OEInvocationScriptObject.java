// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.script.ParameterValueFactory;
import com.sonicsw.xqimpl.script.IParameterValue;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.sonicsw.xqimpl.util.DOMUtils;
import com.sonicsw.xqimpl.script.BaseScriptCommand;
import com.sonicsw.xqimpl.util.log.XQLogImpl;
import com.sonicsw.xqimpl.script.IScriptCommand;
import com.sonicsw.xqimpl.script.ParameterConstants;
import com.sonicsw.xq.XQMessage;
import com.sonicsw.xq.XQParameters;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import org.w3c.dom.Element;
import com.sonicsw.xq.XQLog;
import com.sonicsw.xqimpl.script.ScriptObject;

public class OEInvocationScriptObject extends ScriptObject
{
    protected OpenClient m_client;
    private String m_smHeader;
    private static XQLog m_log;
    
    public OEInvocationScriptObject(final Element element, final OpenClient client) throws ScriptEngineException {
        super(element);
        this.m_client = null;
        this.m_smHeader = null;
        this.m_client = client;
    }
    
    public void initRuntimeContext(final XQParameters xqParameters, final XQMessage xqMessage) {
        super.initRuntimeContext(xqParameters, xqMessage);
        try {
            final String value = new ParameterConstants((Element)xqParameters.getParameterObject("command", 2)).getParameterConstantsMap().get("connIdHeader");
            if (null != value) {
                this.m_smHeader = value;
            }
        }
        catch (ScriptEngineException ex) {
            OEInvocationScriptObject.m_log.logError(ex.toString());
        }
    }
    
    public IScriptCommand createScriptCommand(final Element element) throws ScriptEngineException {
        return (IScriptCommand)new OEInvocationScriptCommand(element, this);
    }
    
    public String getScriptType() {
        return "urn:schemas-progress-com:esboe:0001";
    }
    
    public OpenClient getOpenClient() {
        return this.m_client;
    }
    
    public String getSMHeader() {
        if (null != this.m_smHeader) {
            return this.m_smHeader;
        }
        return this.m_client.getSMHeader();
    }
    
    public boolean isSessionManaged() {
        return this.m_client.isSessionManaged();
    }
    
    public void setConnectionId(final String s) {
        try {
            this.getXQMessage().setStringHeader(this.getSMHeader(), s);
        }
        catch (Exception ex) {
            OEInvocationScriptObject.m_log.logError((Throwable)ex);
        }
    }
    
    static {
        OEInvocationScriptObject.m_log = XQLogImpl.getCategoryLog(64);
    }
    
    public class OEInvocationScriptCommand extends BaseScriptCommand
    {
        private OEScriptCommand m_scriptCommand;
        private OEInvocationScriptObject m_scriptObj;
        
        OEInvocationScriptCommand(final Element element, final OEInvocationScriptObject scriptObj) throws ScriptEngineException {
            super(element);
            this.m_scriptCommand = null;
            this.m_scriptObj = null;
            this.m_scriptObj = scriptObj;
            this.m_scriptCommand = new OEScriptCommand(DOMUtils.getImmediateChildElementByName(element, "command"), scriptObj);
        }
        
        public OEScriptCommand getScriptCommand() {
            return this.m_scriptCommand;
        }
        
        public List execute(final List list, final Map map) throws Exception {
            final ParameterValueFactory parameterValueFactory = this.getParameterValueFactory();
            if (this.m_scriptObj.isSessionManaged()) {
                final String stringHeader = this.m_scriptObj.getXQMessage().getStringHeader(this.m_scriptObj.getSMHeader());
                if (null != stringHeader && stringHeader.length() > 0) {
                    list.add(parameterValueFactory.createParameterValue("openedge.session-managed.id", "xsd:string", stringHeader));
                }
            }
            final ArrayList<IParameterValue> list2 = new ArrayList<IParameterValue>();
            this.m_scriptCommand.execute(list, parameterValueFactory, list2);
            return list2;
        }
    }
}
