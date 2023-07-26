// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.exception;

public abstract class ProException extends Exception implements IChainableException
{
    private Object[] m_arguments;
    private long m_messageId;
    private String m_pattern;
    private Throwable m_previous;
    
    protected ProException(final String pattern, final long messageId, final Object[] arguments, final Throwable previous) {
        this.m_arguments = arguments;
        this.m_messageId = messageId;
        this.m_pattern = pattern;
        this.m_previous = previous;
    }
    
    public ProException(final long n, final Object[] array, final Throwable t) {
        this(null, n, array, t);
    }
    
    public ProException(final long n, final Object[] array) {
        this(null, n, array, null);
    }
    
    public ProException(final String s, final Object[] array, final Throwable t) {
        this(s, 0L, array, t);
    }
    
    public ProException(final String s, final Object[] array) {
        this(s, 0L, array, null);
    }
    
    public ProException() {
    }
    
    public Object getArgument(final int n) {
        return this.m_arguments[n];
    }
    
    public String getLocalizedMessage() {
        String s;
        if (this.m_messageId != 0L) {
            s = ExceptionMessageAdapter.getMessage(this.m_messageId, this.m_arguments);
        }
        else {
            s = ExceptionMessageAdapter.getMessage(this.m_pattern, this.m_arguments);
        }
        return s;
    }
    
    public String getMessage() {
        return this.getLocalizedMessage();
    }
    
    public long getMessageId() {
        return 0xFFFFFFFFFFFFL & this.m_messageId;
    }
    
    public void setPrevious(final Throwable previous) {
        this.m_previous = previous;
    }
    
    public Throwable getPrevious() {
        return this.m_previous;
    }
    
    protected void setMessageId(final long messageId) {
        this.m_messageId = messageId;
    }
    
    public String toString() {
        return this.getClass().getName() + ": " + this.getLocalizedMessage();
    }
}
