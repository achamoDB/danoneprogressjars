// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.Date;
import java.io.IOException;

public class InvalidCertificateException extends IOException
{
    public InvalidCertificateException(final Date date, final Date date2) {
        super("Valid from " + date.toString() + " to " + date2.toString());
    }
    
    public InvalidCertificateException(final String message) {
        super(message);
    }
}
