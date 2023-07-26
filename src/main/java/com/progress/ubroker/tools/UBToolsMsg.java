// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.message.IProMessage;
import com.progress.common.util.PromsgsFile;
import com.progress.common.log.LogSystem;
import com.progress.chimera.log.AdminServerLog;
import com.progress.common.exception.ExceptionMessageAdapter;

public class UBToolsMsg
{
    static boolean msgAdapterInited;
    
    public static String getMsg(final long n) {
        initMsgAdapterIfNotYet();
        if (UBToolsMsg.msgAdapterInited) {
            return ExceptionMessageAdapter.getMessage(n, null);
        }
        return null;
    }
    
    public static String getMsg(final long n, final Object[] array) {
        initMsgAdapterIfNotYet();
        if (UBToolsMsg.msgAdapterInited) {
            return ExceptionMessageAdapter.getMessage(n, array);
        }
        return null;
    }
    
    public static String getMsgStripCode(final long n) {
        final String msg = getMsg(n);
        if (msg != null) {
            return stripMsgCode(msg);
        }
        return null;
    }
    
    public static String getMsgStripCode(final long n, final Object[] array) {
        final String msg = getMsg(n, array);
        if (msg != null) {
            return stripMsgCode(msg);
        }
        return null;
    }
    
    public static void logMsg(final String s) {
        AdminServerLog.get().log(s);
    }
    
    public static void logMsg(final int n, final String s) {
        AdminServerLog.get().log(n, s);
    }
    
    public static void logError(final String s) {
        AdminServerLog.get().logErr(s);
    }
    
    public static void logError(final int n, final String s) {
        AdminServerLog.get().logErr(n, s);
    }
    
    public static void logException(final String s) {
        logException(3, s);
    }
    
    public static void logException(final int n, final String s) {
        AdminServerLog.get().logErr(n, s);
    }
    
    public static void logMsgDebug(final String s) {
        LogSystem.logIt("UBroker", 5, s);
    }
    
    private static void initMsgAdapterIfNotYet() {
        if (!UBToolsMsg.msgAdapterInited) {
            try {
                ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
                UBToolsMsg.msgAdapterInited = true;
            }
            catch (Exception ex) {
                logException("Error - fail to get access to Promsgs file..(" + ex.toString() + ")");
            }
        }
    }
    
    private static String stripMsgCode(String substring) {
        final int lastIndex = substring.lastIndexOf(40);
        if (lastIndex > 0 && substring.lastIndexOf(41, lastIndex) - lastIndex <= 5) {
            substring = substring.substring(0, lastIndex);
        }
        return substring;
    }
    
    static {
        UBToolsMsg.msgAdapterInited = false;
    }
}
