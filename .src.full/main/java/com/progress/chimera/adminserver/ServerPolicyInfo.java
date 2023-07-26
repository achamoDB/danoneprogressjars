// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.chimera.util.Const;
import java.lang.reflect.Method;
import com.progress.chimera.common.Tools;
import com.progress.chimera.ChimeraException;
import java.net.URL;
import java.util.Vector;
import java.io.FilenameFilter;
import java.io.File;
import java.util.StringTokenizer;
import java.net.URLClassLoader;

public class ServerPolicyInfo
{
    private static final boolean IS_NT;
    private static final String FILE_SEPARATOR;
    private static final String PATH_SEPARATOR;
    private String m_id;
    private String m_classLoader;
    private String m_jvmargs;
    private String m_classpath;
    private String m_pluginClasspath;
    private String m_policyFile;
    private String m_authPolicy;
    private String m_loginConfig;
    private String m_umask;
    private int m_refCount;
    private URLClassLoader m_URLclassLoader;
    private ClassLoader m_parentClassloader;
    
    public ServerPolicyInfo(final String id, final String classLoader, final String jvmargs, final String str, final String str2, final String s, final String authPolicy, final String loginConfig, final String umask) throws Exception {
        this.m_id = null;
        this.m_classLoader = null;
        this.m_jvmargs = null;
        this.m_classpath = null;
        this.m_pluginClasspath = null;
        this.m_policyFile = null;
        this.m_authPolicy = null;
        this.m_loginConfig = null;
        this.m_umask = null;
        this.m_refCount = 0;
        this.m_URLclassLoader = null;
        this.m_parentClassloader = this.getClass().getClassLoader();
        this.m_id = id;
        this.m_classLoader = classLoader;
        this.m_jvmargs = jvmargs;
        this.m_policyFile = s.replace('/', ServerPolicyInfo.FILE_SEPARATOR.charAt(0));
        this.m_authPolicy = authPolicy;
        this.m_loginConfig = loginConfig;
        this.m_umask = umask;
        this.m_refCount = 0;
        try {
            if (str2 != null) {
                final StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    final File file = new File(stringTokenizer.nextToken());
                    if (file.getPath().endsWith("*.jar")) {
                        final File[] listFiles = file.getParentFile().listFiles(new JarFilter(".jar"));
                        if (listFiles == null) {
                            continue;
                        }
                        for (int i = 0; i < listFiles.length; ++i) {
                            if (this.m_pluginClasspath == null) {
                                this.m_pluginClasspath = listFiles[i].toString();
                            }
                            else {
                                this.m_pluginClasspath = this.m_pluginClasspath + ServerPolicyInfo.PATH_SEPARATOR + listFiles[i].toString();
                            }
                        }
                    }
                    else if (this.m_pluginClasspath == null) {
                        this.m_pluginClasspath = file.toString();
                    }
                    else {
                        this.m_pluginClasspath = this.m_pluginClasspath + ServerPolicyInfo.PATH_SEPARATOR + file.toString();
                    }
                }
            }
            final Vector vector = new Vector<URL>();
            if (str != null) {
                final StringTokenizer stringTokenizer2 = new StringTokenizer(str, ",");
                while (stringTokenizer2.hasMoreTokens()) {
                    final File file2 = new File(stringTokenizer2.nextToken());
                    if (file2.getPath().endsWith("*.jar")) {
                        final File[] listFiles2 = file2.getParentFile().listFiles(new JarFilter(".jar"));
                        if (listFiles2 == null) {
                            continue;
                        }
                        for (int j = 0; j < listFiles2.length; ++j) {
                            if (this.m_classpath == null) {
                                this.m_classpath = listFiles2[j].toString();
                            }
                            else {
                                this.m_classpath = this.m_classpath + ServerPolicyInfo.PATH_SEPARATOR + listFiles2[j].toString();
                            }
                            if (ServerPolicyInfo.IS_NT) {
                                vector.addElement(listFiles2[j].toURI().toURL());
                            }
                            else {
                                vector.addElement(listFiles2[j].toURL());
                            }
                        }
                    }
                    else {
                        if (this.m_classpath == null) {
                            this.m_classpath = file2.toString();
                        }
                        else {
                            this.m_classpath = this.m_classpath + ServerPolicyInfo.PATH_SEPARATOR + file2.toString();
                        }
                        if (ServerPolicyInfo.IS_NT) {
                            vector.addElement(file2.toURI().toURL());
                        }
                        else {
                            vector.addElement(file2.toURL());
                        }
                    }
                }
                if (!this.m_classLoader.equals("java.net.URLClassLoader")) {
                    throw new ChimeraException("Error: Unsupported classloader - " + this.m_classLoader + ".");
                }
                Method method;
                try {
                    method = this.m_parentClassloader.getClass().getMethod("appendURL", URL.class);
                }
                catch (Exception ex2) {
                    method = null;
                }
                final URL[] urls = new URL[vector.size()];
                for (int k = 0; k < vector.size(); ++k) {
                    urls[k] = vector.get(k);
                    if (method != null) {
                        method.invoke(this.m_parentClassloader, urls[k]);
                    }
                }
                this.m_URLclassLoader = new URLClassLoader(urls, this.m_parentClassloader);
            }
        }
        catch (Exception ex) {
            Tools.px(ex, "Error creating classloader " + this.m_classLoader + ".");
            throw ex;
        }
    }
    
    public int getRefCount() {
        return this.m_refCount;
    }
    
    public void incrementRefCount() {
        ++this.m_refCount;
    }
    
    public ClassLoader getClassloader() {
        return this.m_URLclassLoader;
    }
    
    public String getClasspath() {
        return this.m_classpath;
    }
    
    public String getId() {
        return this.m_id;
    }
    
    public String getPluginClasspath() {
        return this.m_pluginClasspath;
    }
    
    public String getJvmArgs() {
        return this.m_jvmargs;
    }
    
    public String getPolicyFile() {
        return this.m_policyFile;
    }
    
    public String getAuthPolicy() {
        return this.m_authPolicy;
    }
    
    public String getLoginConfig() {
        return this.m_loginConfig;
    }
    
    public String getUmask() {
        return this.m_umask;
    }
    
    public String toVerboseString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("id=" + this.m_id + Const.NEWLINE);
        sb.append("refCount=" + this.m_refCount + Const.NEWLINE);
        sb.append("classpath=" + this.m_classpath + Const.NEWLINE);
        sb.append("jvmargs=" + this.m_jvmargs + Const.NEWLINE);
        sb.append("pluginclasspath=" + this.m_pluginClasspath + Const.NEWLINE);
        sb.append("policyfile=" + this.m_policyFile + Const.NEWLINE);
        sb.append("authpolicy=" + this.m_authPolicy + Const.NEWLINE);
        sb.append("loginconfig=" + this.m_loginConfig + Const.NEWLINE);
        sb.append("umask=" + this.m_umask + Const.NEWLINE);
        sb.append("classloader=" + this.m_URLclassLoader.toString() + Const.NEWLINE);
        sb.append(Const.NEWLINE);
        return sb.toString();
    }
    
    static {
        IS_NT = System.getProperty("os.name").startsWith("Windows");
        FILE_SEPARATOR = System.getProperty("file.separator");
        PATH_SEPARATOR = System.getProperty("path.separator");
    }
    
    private class JarFilter implements FilenameFilter
    {
        private String filter;
        
        public JarFilter(final String filter) {
            this.filter = filter;
        }
        
        public boolean accept(final File file, final String s) {
            return s.endsWith(this.filter);
        }
    }
}
