// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.sonicsw.xqimpl.script.IParameterValue;
import java.util.ArrayList;
import com.sonicsw.xqimpl.script.ParameterValueFactory;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.sonicsw.xqimpl.util.DOMUtils;
import org.w3c.dom.Element;
import com.progress.open4gl.proxygen.PGProc;

public class OEScriptCommand
{
    protected PGProc m_pgProc;
    protected OEInvocationScriptObject m_so;
    protected int m_ipIndex;
    protected int m_specialOp;
    
    OEScriptCommand(final Element element, final OEInvocationScriptObject so) {
        this.m_pgProc = null;
        this.m_so = null;
        this.m_ipIndex = -1;
        this.m_specialOp = -1;
        this.m_so = so;
        final Element elementByTagName = DOMUtils.getElementByTagName(element, "Operation");
        final NamedNodeMap attributes = elementByTagName.getAttributes();
        final Node namedItem = attributes.getNamedItem("ipIndex");
        if (null != namedItem) {
            this.m_ipIndex = Integer.parseInt(namedItem.getNodeValue());
        }
        final Node namedItem2 = attributes.getNamedItem("specialOp");
        if (null != namedItem2) {
            this.m_specialOp = Integer.parseInt(namedItem2.getNodeValue());
        }
        if (this.m_specialOp == -1) {
            (this.m_pgProc = new PGProc()).readXML(elementByTagName);
        }
    }
    
    public void execute(final List list, final ParameterValueFactory parameterValueFactory, final ArrayList<IParameterValue> list2) throws ScriptEngineException, OEFaultException {
        final String execute = this.m_so.getOpenClient().execute(list, this.m_pgProc, this.m_specialOp, this.m_ipIndex, parameterValueFactory, list2);
        if (null != execute) {
            this.m_so.setConnectionId(execute);
        }
    }
}
