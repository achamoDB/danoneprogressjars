// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf;

import com.progress.common.networkevents.IEventObject;
import com.sonicsw.mf.common.runtime.IComponentIdentity;
import java.util.HashMap;
import com.sonicsw.mf.common.runtime.INotification;

public abstract class AbstractPluginNotification implements INotification
{
    protected short m_category;
    protected String m_subCategory;
    protected String m_name;
    protected String m_type;
    protected int m_severity;
    protected short m_logType;
    protected String m_sourceHost;
    protected long m_sequence;
    protected long m_timeStamp;
    protected HashMap m_attributes;
    protected IComponentIdentity m_sourceIdentity;
    protected String m_canonicalName;
    private IEventObject m_eventObject;
    
    public AbstractPluginNotification() {
        this.m_category = 0;
        this.m_subCategory = null;
        this.m_name = null;
        this.m_type = null;
        this.m_severity = 0;
        this.m_logType = 0;
        this.m_sourceHost = null;
        this.m_sequence = 0L;
        this.m_timeStamp = 0L;
        this.m_attributes = new HashMap();
        this.m_sourceIdentity = null;
        this.m_canonicalName = null;
        this.m_eventObject = null;
    }
    
    public short getCategory() {
        return this.m_category;
    }
    
    public String getSubCategory() {
        return this.m_subCategory;
    }
    
    public String getEventName() {
        return this.m_name;
    }
    
    public String getType() {
        return this.m_type;
    }
    
    public int getSeverity() {
        return this.m_severity;
    }
    
    public void setLogType(final short logType) {
        this.m_logType = logType;
    }
    
    public short getLogType() {
        return this.m_logType;
    }
    
    public IComponentIdentity getSourceIdentity() {
        return this.m_sourceIdentity;
    }
    
    public void setSourceIdentity(final IComponentIdentity sourceIdentity) {
        this.m_sourceIdentity = sourceIdentity;
    }
    
    public String getSourceHost() {
        return this.m_sourceHost;
    }
    
    public long getSequenceNumber() {
        return this.m_sequence;
    }
    
    public long getTimeStamp() {
        return this.m_timeStamp;
    }
    
    public void setAttribute(final String key, final Object value) {
        this.m_attributes.put(key, value);
    }
    
    public HashMap getAttributes() {
        return this.m_attributes;
    }
    
    public void setEventObject(final IEventObject eventObject) {
        this.m_eventObject = eventObject;
    }
    
    public IEventObject getEventObject() {
        return this.m_eventObject;
    }
    
    public void setCanonicalName(final String canonicalName) {
        this.m_canonicalName = canonicalName;
    }
    
    public String getCanonicalName() {
        return this.m_canonicalName;
    }
}
