// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.chimera.adminserver.IAdminServerConst;

public class PropFilename implements IPropFilename
{
    static final String DEFAULT_PROPERTY_DIR = "properties";
    
    public static String getFullPath() {
        return ((System.getProperty("UbrokerPropsDir") != null) ? System.getProperty("UbrokerPropsDir") : IAdminServerConst.INSTALL_DIR) + IAdminServerConst.FILE_SEPARATOR + "properties" + IAdminServerConst.FILE_SEPARATOR + "ubroker.properties";
    }
    
    public static String getSchemaFilename() {
        return "ubroker.schema";
    }
}
