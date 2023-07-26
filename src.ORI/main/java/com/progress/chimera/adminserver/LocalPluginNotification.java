// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.sonicsw.mf.common.runtime.INotification;
import com.progress.mf.AbstractPluginNotification;

public class LocalPluginNotification extends AbstractPluginNotification
{
    public LocalPluginNotification(final short category, final String s, final String s2, final int severity) {
        super.m_category = category;
        super.m_subCategory = s;
        super.m_name = s2;
        super.m_severity = severity;
        super.m_timeStamp = System.currentTimeMillis();
        super.m_type = INotification.CATEGORY_TEXT[category] + "." + s + "." + s2;
    }
}
