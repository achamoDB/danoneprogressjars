// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import com.progress.open4gl.ProResultSet;

public class XmrResultSetHolder
{
    private ProResultSet m_resultSet;
    private String m_procedureName;
    private String m_namespaceURI;
    private boolean m_isStatic;
    
    public XmrResultSetHolder(final ProResultSet resultSet, final String procedureName, final String namespaceURI, final boolean isStatic) {
        this.m_resultSet = null;
        this.m_procedureName = null;
        this.m_namespaceURI = "";
        this.m_isStatic = false;
        this.m_resultSet = resultSet;
        this.m_procedureName = procedureName;
        this.m_namespaceURI = namespaceURI;
        this.m_isStatic = isStatic;
    }
    
    public ProResultSet getResultSet() {
        return this.m_resultSet;
    }
    
    public String getProcedureName() {
        return this.m_procedureName;
    }
    
    public String getNamespaceURI() {
        return this.m_namespaceURI;
    }
    
    public boolean isStatic() {
        return this.m_isStatic;
    }
}
