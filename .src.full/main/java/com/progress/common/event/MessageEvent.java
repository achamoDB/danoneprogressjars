// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

public abstract class MessageEvent extends ProEvent
{
    private Object[] m_arguments;
    private long m_messageId;
    private String m_pattern;
    
    public MessageEvent(final Object o, final long messageId, final Object[] arguments) {
        super(o);
        this.m_arguments = arguments;
        this.m_messageId = messageId;
        this.m_pattern = null;
    }
    
    public MessageEvent(final Object o, final String pattern, final Object[] arguments) {
        super(o);
        this.m_arguments = arguments;
        this.m_messageId = 0L;
        this.m_pattern = pattern;
    }
    
    public Object getArgument(final int n) {
        return this.m_arguments[n];
    }
    
    public String getLocalizedMessage() {
        String s;
        if (this.m_messageId != 0L) {
            s = EventMessageAdapter.getMessage(this.m_messageId, this.m_arguments);
        }
        else {
            s = EventMessageAdapter.getMessage(this.m_pattern, this.m_arguments);
        }
        return s;
    }
    
    public String getMessage() {
        return this.getLocalizedMessage();
    }
    
    public long getMessageId() {
        return this.m_messageId;
    }
    
    protected void setMessageId(final long messageId) {
        this.m_messageId = messageId;
    }
    
    public String toString() {
        String str = this.getClass().getName() + ": " + this.getLocalizedMessage();
        if (this.m_messageId != 0L) {
            str = str + " (" + this.m_messageId + ")";
        }
        return str;
    }
}
