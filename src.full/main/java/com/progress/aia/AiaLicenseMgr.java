// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import com.progress.common.exception.ProException;
import com.progress.common.licensemgr.LicenseMgr;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

public class AiaLicenseMgr implements ubConstants
{
    private static final int SCHEME_HTTP = 0;
    private static final int SCHEME_HTTPS = 1;
    private static final int SCHEME_UNSUPPORTED = 2;
    IAppLogger log;
    boolean https_enabled;
    boolean https_licensed;
    
    public AiaLicenseMgr(final boolean https_enabled, final IAppLogger log) throws LicenseMgr.CannotContactLicenseMgr, LicenseMgr.NotLicensed {
        this.log = log;
        this.https_enabled = https_enabled;
        this.https_licensed = true;
        if (log.ifLogBasic(1L, 0)) {
            log.logBasic(0, "AIA_HTTPS is " + (this.https_licensed ? "" : "not ") + "licensed in this configuration.");
        }
    }
    
    public void checkRequest(final String str) throws AiaLicenseException {
        final String upperCase = str.toUpperCase();
        switch (upperCase.equals("HTTP") ? 0 : (upperCase.equals("HTTPS") ? 1 : 2)) {
            case 0: {
                if (this.https_enabled && this.https_licensed) {
                    throw new RedirectException("HTTP");
                }
                break;
            }
            case 1: {
                if (!this.https_licensed) {
                    throw new HTTPSNotLicensedException("HTTPS");
                }
                if (!this.https_enabled) {
                    throw new HTTPSNotEnabledException("HTTPS");
                }
                break;
            }
            default: {
                throw new UnsupportedSchemeException(str);
            }
        }
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "checkRequest(" + str + ") ok.");
        }
    }
    
    public void print(final IAppLogger appLogger) {
        if (!appLogger.ifLogBasic(2L, 1)) {
            return;
        }
        appLogger.logBasic(1, "https_enabled         : " + this.https_enabled);
        appLogger.logBasic(1, "https_licensed        : " + this.https_licensed);
    }
    
    public static class AiaLicenseException extends ProException
    {
        public AiaLicenseException(final String s) {
            super("AiaLicenseException", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class RedirectException extends AiaLicenseException
    {
        public RedirectException(final String str) {
            super("RedirectException[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class HTTPSNotLicensedException extends AiaLicenseException
    {
        public HTTPSNotLicensedException(final String str) {
            super("HTTPSNotLicensedException[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class HTTPSNotEnabledException extends AiaLicenseException
    {
        public HTTPSNotEnabledException(final String str) {
            super("HTTPSNotEnabledException[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class UnsupportedSchemeException extends AiaLicenseException
    {
        public UnsupportedSchemeException(final String str) {
            super("UnsupportedSchemeException[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
}
