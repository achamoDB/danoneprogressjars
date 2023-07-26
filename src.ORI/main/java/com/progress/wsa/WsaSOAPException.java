// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import com.progress.common.exception.ExceptionMessageAdapter;
import org.apache.soap.SOAPException;

public class WsaSOAPException extends SOAPException
{
    protected Object[] m_arguments;
    protected long m_messageId;
    protected String m_pattern;
    
    public WsaSOAPException(final String s, final String s2) {
        super(s, s2);
        this.m_arguments = null;
        this.m_messageId = 0L;
        this.m_pattern = null;
    }
    
    public WsaSOAPException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
        this.m_arguments = null;
        this.m_messageId = 0L;
        this.m_pattern = null;
    }
    
    public WsaSOAPException(final String s, final String s2, final Object[] array) {
        this(s, s2, 0L, array, null);
    }
    
    public WsaSOAPException(final String s, final String s2, final Object[] array, final Throwable t) {
        this(s, s2, 0L, array, t);
    }
    
    public WsaSOAPException(final String s, final long n, final Object[] array) {
        this(s, null, n, array, null);
    }
    
    public WsaSOAPException(final String s, final long n, final Object[] array, final Throwable t) {
        this(s, null, n, array, t);
    }
    
    public boolean isProgressException() throws Exception {
        return null != this.m_pattern || 0L != this.m_messageId;
    }
    
    public Object getArgument(final int n) throws IndexOutOfBoundsException {
        return this.m_arguments[n];
    }
    
    public String getLocalizedMessage() {
        String s;
        if (this.m_messageId != 0L) {
            s = ExceptionMessageAdapter.getMessage(this.m_messageId, this.m_arguments);
        }
        else if (null != this.m_pattern) {
            s = ExceptionMessageAdapter.getMessage(this.m_pattern, this.m_arguments);
        }
        else {
            s = super.getMessage();
        }
        return s;
    }
    
    public String getMessage() {
        String s = this.getLocalizedMessage();
        if (null == s) {
            final String name = this.getClass().getName();
            s = name.substring(name.lastIndexOf(".1"));
        }
        return s;
    }
    
    public long getMessageId() {
        return 0xFFFFFFFFFFFFL & this.m_messageId;
    }
    
    protected void setMessageId(final long messageId) {
        this.m_messageId = messageId;
    }
    
    public String toString() {
        return "msg=" + this.getLocalizedMessage() + ((super.getTargetException() != null) ? ("; targetException=" + super.getTargetException().getMessage()) : "") + "]";
    }
    
    protected WsaSOAPException(final String s, final String pattern, final long messageId, final Object[] array, final Throwable t) {
        super(s, (String)null, t);
        this.m_arguments = null;
        this.m_messageId = 0L;
        this.m_pattern = null;
        this.m_arguments = ((null == array) ? new Object[0] : array);
        this.m_messageId = messageId;
        this.m_pattern = pattern;
    }
}
