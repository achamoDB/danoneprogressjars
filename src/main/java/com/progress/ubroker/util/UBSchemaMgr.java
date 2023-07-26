// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.guiproperties.PropertyCategory;
import java.rmi.RemoteException;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.common.guiproperties.PropertyValueSet;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.common.guiproperties.GUIMetaSchema;
import java.io.Serializable;

public class UBSchemaMgr implements Serializable
{
    public static boolean m_schemaLoaded;
    static String m_schemaFileSpec;
    static GUIMetaSchema m_gmsObj;
    
    public UBSchemaMgr() {
    }
    
    public UBSchemaMgr(final String schemaFileSpec) {
        UBSchemaMgr.m_schemaFileSpec = schemaFileSpec;
        try {
            load(UBSchemaMgr.m_schemaFileSpec);
        }
        catch (Exception ex) {}
    }
    
    public static synchronized boolean load(final String s) {
        try {
            if (!UBSchemaMgr.m_schemaLoaded) {
                UBSchemaMgr.m_schemaFileSpec = s;
                UBSchemaMgr.m_gmsObj = GUIMetaSchema.instantiate(UBSchemaMgr.m_schemaFileSpec);
                UBSchemaMgr.m_schemaLoaded = true;
                UBToolsMsg.logMsg("schema file: " + s + " is loaded.");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return UBSchemaMgr.m_schemaLoaded;
    }
    
    public static PropertyValueSet getPVS(final String s, final IPropertyManagerRemote propertyManagerRemote) {
        final int lastIndex = s.lastIndexOf(46);
        return getPVS(s.substring(0, lastIndex), s.substring(lastIndex + 1), propertyManagerRemote);
    }
    
    public static PropertyValueSet getPVS(final String s, final String s2, final IPropertyManagerRemote propertyManagerRemote) {
        if (UBSchemaMgr.m_schemaLoaded) {
            try {
                return new PropertyValueSet(UBSchemaMgr.m_gmsObj, propertyManagerRemote, s, s2);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    public static PropertyValueSet loadPVS(final IYodaRMI yodaRMI, final IPropertyManagerRemote propertyManagerRemote, final String s) throws RemoteException {
        try {
            if (UBSchemaMgr.m_schemaLoaded && propertyManagerRemote != null) {
                try {
                    return getPVS(s, propertyManagerRemote);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        return null;
    }
    
    public static PropertyValueSet loadPVS(final IYodaRMI yodaRMI, final IPropertyManagerRemote propertyManagerRemote, final String s, final String s2, final String s3) throws RemoteException {
        try {
            if (UBSchemaMgr.m_schemaLoaded && propertyManagerRemote != null) {
                try {
                    return getPVS(s, s2, propertyManagerRemote);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        return null;
    }
    
    public static PropertyCategory findPresentationCategory(final String str) {
        UBToolsMsg.logMsg("Finding presentation category: " + str);
        try {
            return UBSchemaMgr.m_gmsObj.findCategory(str);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static {
        UBSchemaMgr.m_schemaLoaded = false;
        UBSchemaMgr.m_schemaFileSpec = null;
        UBSchemaMgr.m_gmsObj = null;
    }
}
