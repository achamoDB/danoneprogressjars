// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.common.exception.ProException;

public class Open4GLException extends ProException
{
    private String m_returnString;
    private boolean m_noRetString;
    
    public Open4GLException(final long n, final Object[] array) {
        super((n <= 70L) ? MessageMap.getMessage(n) : n, array);
        this.m_returnString = null;
        this.m_noRetString = true;
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public Open4GLException() {
        this.m_returnString = null;
        this.m_noRetString = true;
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public Open4GLException(final String s) {
        super(s, (Object[])null);
        this.m_returnString = null;
        this.m_noRetString = true;
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public Open4GLException(final String s, final Object[] array) {
        super(s, array);
        this.m_returnString = null;
        this.m_noRetString = true;
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public boolean hasProcReturnString() {
        return !this.m_noRetString;
    }
    
    public String getProcReturnString() {
        return this.m_returnString;
    }
    
    public void setProcReturnString(final String returnString) {
        this.m_noRetString = false;
        this.m_returnString = returnString;
    }
}
