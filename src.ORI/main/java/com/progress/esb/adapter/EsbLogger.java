// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xq.XQLog;
import com.progress.common.ehnlog.LogHandler;

public class EsbLogger implements LogHandler
{
    protected XQLog m_logger;
    
    EsbLogger(final XQLog logger) {
        this.m_logger = null;
        this.m_logger = logger;
    }
    
    public void log(final int n, final String s, final String s2, final String s3) {
        if (n == 1) {
            this.m_logger.logError(s + " [" + s2 + "] " + s3);
        }
        else {
            this.m_logger.logInformation(s + " [" + s2 + "] " + s3);
        }
    }
    
    public void log(final int n, final String s, final String s2, final String s3, final byte[] array, final int n2) {
        this.log(n, s, s2, s3);
    }
    
    public void log(final int n, final String str, final String str2, final String str3, final Throwable t) {
        this.m_logger.logError(str + " [" + str2 + "] " + str3);
        this.m_logger.logError(t);
    }
}
