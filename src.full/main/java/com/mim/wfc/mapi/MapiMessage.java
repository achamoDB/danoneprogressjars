// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.mapi;

import com.ms.wfc.io.WinIOException;
import com.ms.wfc.io.File;

public class MapiMessage
{
    public String subject;
    public String noteText;
    public String messageType;
    public String dateReceived;
    public String conversationID;
    public int flags;
    public MapiRecipDesc originator;
    public MapiRecipDesc[] recips;
    public MapiFileDesc[] files;
    public static final int UNREAD = 1;
    public static final int RECEIPT_REQUESTED = 2;
    public static final int SENT = 4;
    
    public void deleteAttachementFiles() {
        if (this.files != null) {
            for (int i = 0; i < this.files.length; ++i) {
                if (this.files[i].fileName != null) {
                    try {
                        File.delete(this.files[i].fileName);
                    }
                    catch (WinIOException ex) {}
                }
            }
        }
    }
}
