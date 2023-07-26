// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.auth;

import java.security.Principal;

public class PscPrincipal implements Principal
{
    protected String m_name;
    
    public PscPrincipal() {
        this.m_name = null;
    }
    
    public PscPrincipal(final String name) {
        this.m_name = null;
        if (name == null) {
            throw new NullPointerException("illegal null input");
        }
        this.m_name = name;
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public String toString() {
        return "PscPrincipal:  " + this.m_name;
    }
    
    public boolean equals(final Object o) {
        boolean b = false;
        if (this == o) {
            return true;
        }
        if (o instanceof PscPrincipal && this.getName().equals(((PscPrincipal)o).getName())) {
            b = true;
        }
        return b;
    }
    
    public int hashCode() {
        return this.m_name.hashCode();
    }
    
    protected void finalize() throws Throwable {
        this.m_name = null;
    }
}
