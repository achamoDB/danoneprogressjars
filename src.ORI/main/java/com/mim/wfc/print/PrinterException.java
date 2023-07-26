// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.mim.wfc.util.Error;

public class PrinterException extends RuntimeException
{
    protected int code;
    protected String funcName;
    public static final int INVALID_PRINTER = 0;
    public static final int INVALID_ARGUMENT = 1;
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        String systemErrorMessage;
        if (this.code == 0) {
            systemErrorMessage = "Invalid printer graphics object";
        }
        else {
            systemErrorMessage = Error.getSystemErrorMessage(this.code);
        }
        if (systemErrorMessage != null) {
            sb.append(systemErrorMessage);
        }
        else {
            sb.append("Unknown error (error code = ");
            sb.append(this.code);
            sb.append(")");
        }
        sb.append(", function ");
        sb.append(this.funcName);
        return sb.toString();
    }
    
    PrinterException(final int code, final String funcName) {
        this.code = code;
        this.funcName = funcName;
    }
    
    public String getFuncName() {
        return this.funcName;
    }
    
    public int getCode() {
        return this.code;
    }
}
