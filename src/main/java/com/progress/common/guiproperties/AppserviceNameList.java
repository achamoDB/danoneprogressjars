// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import com.progress.common.util.AppService;

public class AppserviceNameList extends StringList
{
    AppserviceNameList() throws XPropertyException {
    }
    
    AppserviceNameList(final String s) throws XPropertyException {
        super(s);
    }
    
    public boolean isValidValueAsString(final String s) throws XPropertyException {
        return !s.equals("") && AppService.validateName(s);
    }
    
    public Object toObject(final String s) throws XPropertyException {
        return new AppserviceNameList(s);
    }
}
