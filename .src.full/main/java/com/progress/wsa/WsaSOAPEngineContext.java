// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.io.File;
import java.util.Hashtable;

public class WsaSOAPEngineContext extends Hashtable implements WsaConstants
{
    private Hashtable m_parent;
    
    public WsaSOAPEngineContext() {
        this.m_parent = null;
    }
    
    public WsaSOAPEngineContext(final Hashtable parent) {
        this.m_parent = null;
        this.m_parent = parent;
    }
    
    public Object get(final Object o) {
        Object o2 = super.get(o);
        if (null == o2 && null != this.m_parent) {
            o2 = this.m_parent.get(o);
        }
        return o2;
    }
    
    public String getRealPath(final String str) {
        final String s = (String)this.get("psc.wsa.deployment.path");
        if (null == s) {}
        String s2 = s.trim();
        String s3;
        if (null != str && 0 < str.length()) {
            if (!s2.endsWith("/") && !s2.endsWith("\\")) {
                s2 += File.separatorChar;
            }
            s3 = s2 + str;
        }
        else {
            s3 = s2;
        }
        if (File.separatorChar != '/') {
            s3 = s3.replace(File.separatorChar, '/');
        }
        return s3;
    }
}
