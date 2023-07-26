// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.chimera.util.Const;
import java.util.Vector;

public class ServerPluginInfo
{
    String m_id;
    Vector m_productNumberList;
    String m_className;
    String m_archive;
    String[] m_args;
    private String m_personality;
    private String[] m_dependList;
    private ServerPolicyInfo m_policyInfo;
    private int m_refCount;
    private int m_index;
    private boolean m_isRunning;
    private transient Class m_class;
    private transient Thread m_thread;
    private transient IServerPlugin m_instance;
    
    public ServerPluginInfo(final String id, final Vector productNumberList, final String className, final String archive, final String[] args) {
        this.m_id = null;
        this.m_productNumberList = new Vector();
        this.m_className = null;
        this.m_archive = null;
        this.m_args = null;
        this.m_personality = null;
        this.m_dependList = null;
        this.m_policyInfo = null;
        this.m_refCount = 0;
        this.m_index = 0;
        this.m_isRunning = false;
        this.m_class = null;
        this.m_thread = null;
        this.m_instance = null;
        this.m_id = id;
        this.m_productNumberList = productNumberList;
        this.m_className = className;
        this.m_archive = archive;
        this.m_args = args;
    }
    
    public ServerPluginInfo(final String id, final Vector productNumberList, final String className, final String archive, final String[] args, final ServerPolicyInfo policyInfo, final String[] dependList, final String personality) {
        this.m_id = null;
        this.m_productNumberList = new Vector();
        this.m_className = null;
        this.m_archive = null;
        this.m_args = null;
        this.m_personality = null;
        this.m_dependList = null;
        this.m_policyInfo = null;
        this.m_refCount = 0;
        this.m_index = 0;
        this.m_isRunning = false;
        this.m_class = null;
        this.m_thread = null;
        this.m_instance = null;
        this.m_id = id;
        this.m_productNumberList = productNumberList;
        this.m_className = className;
        this.m_archive = archive;
        this.m_args = args;
        this.m_policyInfo = policyInfo;
        this.m_dependList = dependList;
        this.m_refCount = 0;
        this.m_policyInfo.incrementRefCount();
        this.m_personality = personality;
    }
    
    public String getPersonality() {
        return this.m_personality;
    }
    
    public boolean isRunning() {
        return this.m_isRunning;
    }
    
    public void setRunning(final boolean isRunning) {
        this.m_isRunning = isRunning;
    }
    
    public int getIndex() {
        return this.m_index;
    }
    
    public void setIndex(final int index) {
        this.m_index = index;
    }
    
    public int getRefCount() {
        return this.m_refCount;
    }
    
    public void incrementRefCount() {
        ++this.m_refCount;
    }
    
    public ServerPolicyInfo getPolicy() {
        return this.m_policyInfo;
    }
    
    public String[] getDependencyList() {
        return this.m_dependList;
    }
    
    public String getId() {
        return this.m_id;
    }
    
    public Vector getProductNumberList() {
        return this.m_productNumberList;
    }
    
    public String getClassName() {
        return this.m_className;
    }
    
    public String[] getArgs() {
        return this.m_args;
    }
    
    public Class getPluginClass() {
        return this.m_class;
    }
    
    protected void setPluginClass(final Class class1) {
        this.m_class = class1;
    }
    
    public String getPluginArchive() {
        return this.m_archive;
    }
    
    public Thread getThread() {
        return this.m_thread;
    }
    
    protected void setThread(final Thread thread) {
        this.m_thread = thread;
    }
    
    public IServerPlugin getPluginInstance() {
        return this.m_instance;
    }
    
    protected void setPluginInstance(final IServerPlugin instance) {
        this.m_instance = instance;
    }
    
    public String toVerboseString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("id=" + this.m_id + Const.NEWLINE);
        sb.append("prodNums=[");
        for (int i = 0; i < this.m_productNumberList.size(); ++i) {
            sb.append(this.m_productNumberList.elementAt(i) + ",");
        }
        if (this.m_productNumberList.size() > 0) {
            sb.setCharAt(sb.length() - 1, ']');
        }
        else {
            sb.append(']');
        }
        sb.append(Const.NEWLINE);
        sb.append("class=" + this.m_className + Const.NEWLINE);
        sb.append("args:" + Const.NEWLINE);
        for (int j = 0; j < this.m_args.length; ++j) {
            sb.append("    " + this.m_args[j] + Const.NEWLINE);
        }
        sb.append("index=" + this.m_index + Const.NEWLINE);
        sb.append("refCount=" + this.m_refCount + Const.NEWLINE);
        sb.append("running=" + this.m_isRunning + Const.NEWLINE);
        sb.append("personality=" + this.m_personality + Const.NEWLINE);
        sb.append("dependancies:" + Const.NEWLINE);
        for (int k = 0; k < this.m_dependList.length; ++k) {
            sb.append("    " + this.m_dependList[k] + Const.NEWLINE);
        }
        if (this.m_instance != null) {
            sb.append("Loaded with: " + this.m_instance.getClass().getClassLoader() + Const.NEWLINE);
        }
        sb.append("policy=" + this.m_policyInfo.getId() + Const.NEWLINE);
        sb.append(Const.NEWLINE);
        return sb.toString();
    }
}
