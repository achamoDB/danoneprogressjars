// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.sonicsw.xq.XQParameters;
import com.sonicsw.xq.XQProcessContext;
import java.net.InetAddress;
import com.sonicsw.xq.XQServiceContext;
import com.progress.open4gl.Open4GLException;

public class OEFaultException extends Exception
{
    private Open4GLException m_exception;
    private String m_errMsg;
    
    public OEFaultException(final Open4GLException exception) {
        super(exception.getMessage());
        this.m_exception = null;
        this.m_errMsg = null;
        this.m_exception = exception;
    }
    
    public OEFaultException(final Open4GLException exception, final String errMsg) {
        super(exception.getMessage());
        this.m_exception = null;
        this.m_errMsg = null;
        this.m_exception = exception;
        this.m_errMsg = errMsg;
    }
    
    public String getAsXML(final XQServiceContext xqServiceContext) {
        final XQProcessContext processContext = xqServiceContext.getProcessContext();
        final XQParameters parameters = xqServiceContext.getParameters();
        final StringBuffer sb = new StringBuffer();
        sb.append("<exception xmlns=\"http://www.sonicsw.com/sonicesb/exception\">\n");
        sb.append("<message>Fault String: ");
        sb.append(this.m_exception.getMessage());
        if (null != this.m_errMsg) {
            sb.append(this.m_errMsg);
        }
        sb.append("</message>\n<class>");
        sb.append(this.m_exception.getClass().getCanonicalName());
        sb.append("</class>\n<detail>\n<faultDetails container=\"");
        sb.append(parameters.getParameter("SonicXQ.ContainerName", 1));
        sb.append("\" host=\"");
        try {
            sb.append(InetAddress.getLocalHost().getHostName());
        }
        catch (Exception ex) {}
        sb.append("\" process=\"");
        sb.append(processContext.getName());
        sb.append("\" serviceApplication=\"");
        sb.append(parameters.getParameter("SonicXQ.ServiceName", 1));
        sb.append("\" step=\"");
        sb.append(processContext.getStepName());
        sb.append("\" topLevelProcess=\"");
        sb.append(processContext.getTopProcessName());
        sb.append("\" />\n</detail>");
        sb.append(this.addStackTrace());
        sb.append("\n</exception>");
        return sb.toString();
    }
    
    private String addStackTrace() {
        final StringWriter out = new StringWriter();
        this.m_exception.printStackTrace(new PrintWriter(out));
        return "<stacktrace><![CDATA[" + out.toString() + "]]></stacktrace>";
    }
    
    public String getAsText(final XQServiceContext xqServiceContext) {
        xqServiceContext.getProcessContext();
        xqServiceContext.getParameters();
        final StringBuffer sb = new StringBuffer();
        sb.append(this.m_exception.getClass().getCanonicalName());
        sb.append(": ");
        sb.append(this.m_exception.getMessage());
        if (null != this.m_errMsg) {
            sb.append(this.m_errMsg);
        }
        sb.append("\n");
        sb.append(this.addStackTrace());
        return sb.toString();
    }
}
