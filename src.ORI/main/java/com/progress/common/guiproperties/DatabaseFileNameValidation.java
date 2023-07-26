// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class DatabaseFileNameValidation implements IPropertyValidation
{
    private StringBuffer errorMessage;
    
    public DatabaseFileNameValidation() {
        this.errorMessage = new StringBuffer();
    }
    
    public boolean isValid(final String str, final String s) {
        boolean b = true;
        if (str != null) {
            this.errorMessage.append("Invalid property '" + str + "':  ");
        }
        if (s == null || s.length() == 0) {
            this.errorMessage.append("A database name must be specified.");
            b = false;
        }
        else if (IPropertyValidation.IS_WINDOWS) {
            if (s.indexOf("/") >= 0) {
                this.errorMessage.append("Unix style format cannot be utilized for Windows file specification.");
                b = false;
            }
        }
        else if (s.indexOf("\\") >= 0) {
            this.errorMessage.append("Windows style format cannot be utilized for Unix file specification.");
            b = false;
        }
        if (b) {
            if (s.length() > 255) {
                this.errorMessage.append("Database filename specification cannot exceed 255 characters.");
                b = false;
            }
            else {
                final String lowerCase = s.substring((s.lastIndexOf(IPropertyValidation.FILE_SEPARATOR) == -1) ? 0 : (s.lastIndexOf(IPropertyValidation.FILE_SEPARATOR) + 1)).toLowerCase();
                final String substring = lowerCase.substring(0, (lowerCase.lastIndexOf(".db") == -1) ? lowerCase.length() : lowerCase.lastIndexOf(".db"));
                if (substring.length() > 11 || substring.length() == 0) {
                    this.errorMessage.append("Database name must be between 1 and 11 characters in length.");
                    b = false;
                }
            }
        }
        if (b) {
            this.errorMessage = new StringBuffer();
        }
        return b;
    }
    
    public String getErrorMessage() {
        return this.errorMessage.toString();
    }
}
