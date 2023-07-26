// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.license;

public class LicenseException extends RuntimeException
{
    public static final int INVALID = 1;
    public static final int NOT_FOUND = 2;
    public static final int READ_ERROR = 3;
    public static final int KEY_DOES_NOT_MATCH = 4;
    public static final int COMPUTER_DOES_NOT_MATCH = 5;
    public static final int USER_DOES_NOT_MATCH = 6;
    public static final int VERSION_DOES_NOT_MATCH = 7;
    public static final int TOO_MANY_OBJECTS = 8;
    public static final int EXPIRED = 9;
    public static final int RUNTIME_ONLY = 10;
    public static final int INVALID_INSTALLATION = 11;
    private int \u00c0;
    
    public LicenseException() {
        super("Invalid license");
        this.\u00c0 = 1;
    }
    
    public LicenseException(final int \u00e0, final String message) {
        super(message);
        this.\u00c0 = 1;
        this.\u00c0 = \u00e0;
    }
    
    public int getCause() {
        return this.\u00c0;
    }
}
