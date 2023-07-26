// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.net.URL;
import java.util.Vector;
import java.net.URLClassLoader;

public final class AdminLoader extends URLClassLoader
{
    private static final boolean DEBUG = false;
    private Vector excludeList;
    
    public AdminLoader(final URL[] urls, final ClassLoader parent) {
        super(urls, parent);
        this.excludeList = new Vector();
    }
    
    public void excludeClass(final String s) {
        if (this.excludeList.contains(s)) {
            return;
        }
        this.excludeList.addElement(s);
    }
    
    public void appendURL(final URL url) {
        final URL[] urLs = this.getURLs();
        for (int i = 0; i < urLs.length; ++i) {
            if (urLs[i].sameFile(url)) {
                return;
            }
        }
        super.addURL(url);
    }
    
    public Class findClass(final String s) throws ClassNotFoundException {
        for (int i = 0; i < this.excludeList.size(); ++i) {
            if (s.startsWith((String)this.excludeList.elementAt(i))) {
                throw new ClassNotFoundException(s);
            }
        }
        return super.findClass(s);
    }
}
