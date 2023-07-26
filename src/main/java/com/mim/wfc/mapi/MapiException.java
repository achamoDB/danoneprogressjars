// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.mapi;

public class MapiException extends Exception
{
    protected int code;
    public static final int SUCCESS = 0;
    public static final int USER_ABORT = 1;
    public static final int FAILURE = 2;
    public static final int LOGON_FAILURE = 3;
    public static final int DISK_FULL = 4;
    public static final int INSUFFICIENT_MEMORY = 5;
    public static final int ACCESS_DENIED = 6;
    public static final int TOO_MANY_SESSIONS = 8;
    public static final int TOO_MANY_FILES = 9;
    public static final int TOO_MANY_RECIPIENTS = 10;
    public static final int ATTACHMENT_NOT_FOUND = 11;
    public static final int ATTACHMENT_OPEN_FAILURE = 12;
    public static final int ATTACHMENT_WRITE_FAILURE = 13;
    public static final int UNKNOWN_RECIPIENT = 14;
    public static final int BAD_RECIPTYPE = 15;
    public static final int NO_MESSAGES = 16;
    public static final int INVALID_MESSAGE = 17;
    public static final int TEXT_TOO_LARGE = 18;
    public static final int INVALID_SESSION = 19;
    public static final int TYPE_NOT_SUPPORTED = 20;
    public static final int AMBIGUOUS_RECIPIENT = 21;
    public static final int MESSAGE_IN_USE = 22;
    public static final int NETWORK_FAILURE = 23;
    public static final int INVALID_EDITFIELDS = 24;
    public static final int INVALID_RECIPS = 25;
    public static final int NOT_SUPPORTED = 26;
    
    MapiException(final int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public String getText() {
        String s = null;
        switch (this.code) {
            case 0: {
                s = null;
                break;
            }
            case 1: {
                s = "A mail dialog was canceled by the user";
                break;
            }
            case 3: {
                s = "The login procedure failed";
                break;
            }
            case 4: {
                s = "An attachment could not be written to a temporary file because there was not enough space on the disk";
                break;
            }
            case 5: {
                s = "There was insufficient memory to proceed.";
                break;
            }
            case 6: {
                s = "The access to the message was denied.";
                break;
            }
            case 8: {
                s = "The user had too many sessions open simultaneously.";
                break;
            }
            case 9: {
                s = "There were too many file attachments.";
                break;
            }
            case 10: {
                s = "There were too many recipients.";
                break;
            }
            case 11: {
                s = "The specified attachment was not found.";
                break;
            }
            case 12: {
                s = "The specified attachment could not be opened.";
                break;
            }
            case 13: {
                s = "An attachment could not be written to a temporary file. Check directory permissions!";
                break;
            }
            case 14: {
                s = "A recipient did not appear in the address list.";
                break;
            }
            case 15: {
                s = "The type of a recipient was not 'TO', 'CC', or 'BCC'.";
                break;
            }
            case 16: {
                s = "A matching message could not be found.";
                break;
            }
            case 17: {
                s = "An invalid message identifier was passed in the messageId parameter.";
                break;
            }
            case 18: {
                s = "The text in the message was too large.";
                break;
            }
            case 19: {
                s = "An invalid session handle was used for the session parameter.";
                break;
            }
            case 20: {
                s = "The supplied message or file type is not supported by the underlying mail system.";
                break;
            }
            case 21: {
                s = "A recipient matched more than one of the recipient descriptor structures and the DIALOG flag was not set";
                break;
            }
            case 22: {
                s = "The message cannot be deleted or saved because it is currently in use.";
                break;
            }
            case 23: {
                s = "A network failure occured.";
                break;
            }
            case 24: {
                s = "The value of the editFields parameter was outside the range of 0 through 4.";
                break;
            }
            case 25: {
                s = "One or more recipients were invalid or did not resolve to any address.";
                break;
            }
            case 26: {
                s = "The operation was not supported by the underlying messaging system.";
                break;
            }
            default: {
                s = "An unspecified error occurred.";
                break;
            }
        }
        return s;
    }
}
