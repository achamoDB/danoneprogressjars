// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import com.progress.common.message.IProMessage;

public class PromsgsBundle implements IProMessage
{
    private Vector rbList;
    
    public PromsgsBundle() {
        this.rbList = new Vector(3);
    }
    
    public synchronized String getMessage(final long n) throws ProMessageException {
        final String facility = this.getFacility(n);
        final String string = "com.progress.message." + facility;
        final int id = this.getId(n);
        RbEntry rbEntry = null;
        int i;
        for (i = 0; i < this.rbList.size(); ++i) {
            rbEntry = (RbEntry)this.rbList.elementAt(i);
            if (facility.equals(rbEntry.getFacility())) {
                break;
            }
        }
        String string2;
        try {
            ResourceBundle resourceBundle;
            if (i == this.rbList.size()) {
                resourceBundle = ResourceBundle.getBundle(string);
                this.rbList.addElement(new RbEntry(facility, resourceBundle));
            }
            else {
                resourceBundle = rbEntry.getBundle();
            }
            string2 = resourceBundle.getString(String.valueOf(id));
        }
        catch (MissingResourceException ex) {
            throw new ProMessageException("Unknown Message ID = " + string + " " + id);
        }
        return string2;
    }
    
    private String getFacility(final long n) {
        final char[] str = new char[2];
        final StringBuffer sb = new StringBuffer();
        str[0] = (char)(byte)(n >>> 56);
        str[1] = (char)(byte)(n >>> 48);
        sb.append(str);
        sb.append("Bundle");
        return sb.toString();
    }
    
    private int getId(final long n) {
        return (int)n;
    }
    
    private class RbEntry
    {
        private String facility;
        private ResourceBundle bundle;
        
        public RbEntry(final String facility, final ResourceBundle bundle) {
            this.facility = facility;
            this.bundle = bundle;
        }
        
        public String getFacility() {
            return this.facility;
        }
        
        public ResourceBundle getBundle() {
            return this.bundle;
        }
    }
}
