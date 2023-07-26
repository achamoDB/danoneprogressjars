// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.sonicsw.xqimpl.script.ScriptObject;
import com.sonicsw.mf.common.IComponentContext;
import com.progress.esb.tools.EsbRuntimeProperties;
import org.w3c.dom.Element;
import com.sonicsw.xq.XQLog;
import com.sonicsw.xqimpl.script.ScriptEngine;

public class OEInvocationScriptEngine extends ScriptEngine
{
    private static XQLog m_log;
    protected Element m_esboeCommand;
    protected int m_operatingMode;
    protected String m_appServiceURL;
    protected EsbRuntimeProperties m_props;
    protected OpenClient m_client;
    
    public OEInvocationScriptEngine(final IComponentContext componentContext, final Element esboeCommand, final OpenClient client) {
        super(componentContext);
        this.m_esboeCommand = null;
        this.m_operatingMode = 0;
        this.m_appServiceURL = null;
        this.m_props = null;
        this.m_client = null;
        this.m_esboeCommand = esboeCommand;
        this.m_client = client;
    }
    
    public ScriptObject createScriptObject(final Element element) throws ScriptEngineException {
        return new OEInvocationScriptObject(element, this.m_client);
    }
    
    public ScriptObject createScriptObject() throws ScriptEngineException {
        return new OEInvocationScriptObject(this.m_esboeCommand, this.m_client);
    }
    
    static {
        OEInvocationScriptEngine.m_log = XQLogImpl.getCategoryLog(64);
    }
}
