// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import java.util.NoSuchElementException;
import java.util.Enumeration;

class ObjectTrackerEnumeration implements Enumeration
{
    private ObjectTracker \u01c9;
    private int \u01ca;
    private boolean \u01cb;
    
    public Object nextElement() {
        synchronized (this.\u01c9) {
            while (this.\u01ca < this.\u01c9.\u01c7.size()) {
                final ITrackerObject trackerObject = (ITrackerObject)this.\u01c9.\u01c7.getArray()[this.\u01ca++];
                if (!this.\u01cb || trackerObject.isSelected()) {
                    // monitorexit(this.\u01c9)
                    return trackerObject;
                }
            }
        }
        // monitorexit(this.\u01c9)
        throw new NoSuchElementException("ObjectTrackerEnumeration");
    }
    
    ObjectTrackerEnumeration(final ObjectTracker \u01c9, final boolean \u01cc) {
        this.\u01c9 = \u01c9;
        this.\u01cb = \u01cc;
    }
    
    public boolean hasMoreElements() {
        if (this.\u01c9.\u01c7 != null) {
            final int size = this.\u01c9.\u01c7.size();
            if (!this.\u01cb) {
                return this.\u01ca < size;
            }
            for (int i = this.\u01ca; i < size; ++i) {
                if (((ITrackerObject)this.\u01c9.\u01c7.getArray()[i]).isSelected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
