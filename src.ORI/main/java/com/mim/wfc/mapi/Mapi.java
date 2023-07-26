// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.mapi;

import com.mim.wfc.license._cls753A;
import com.ms.dll.Root;
import com.ms.dll.DllLib;
import com.ms.wfc.ui.Control;

public class Mapi
{
    public static final int LOGON_UI = 1;
    public static final int PASSWORD_UI = 131072;
    public static final int NEW_SESSION = 2;
    public static final int FORCE_DOWNLOAD = 4096;
    public static final int EXTENDED = 32;
    public static final int DIALOG = 8;
    public static final int USE_DEFAULT = 64;
    public static final int UNREAD_ONLY = 32;
    public static final int GUARANTEE_FIFO = 256;
    public static final int PEEK = 128;
    public static final int SUPPRESS_ATTACH = 2048;
    public static final int ENVELOPE_ONLY = 64;
    public static final int BODY_AS_FILE = 512;
    public static final int AB_NOMODIFY = 1024;
    private static /* synthetic */ Class class$com$mim$wfc$mapi$Win32MapiRecipDesc;
    private static /* synthetic */ Class class$com$mim$wfc$mapi$Win32MapiFileDesc;
    private static /* synthetic */ Class class$com$mim$wfc$mapi$Win32MapiMessage;
    
    public static int logon(final Control control, final String s, final String s2, final int n) throws MapiException {
        final int[] array = { 0 };
        final int mapiLogon = Win32Mapi.MAPILogon((control != null) ? control.getHandle() : 0, s, s2, n, 0, array);
        if (mapiLogon != 0) {
            throw new MapiException(mapiLogon);
        }
        return array[0];
    }
    
    public static void deleteMail(final int n, final Control control, final String s) throws MapiException {
    }
    
    public static String findNext(final int n, final Control control, final String s, final String s2, final int n2) throws MapiException {
        final StringBuffer sb = new StringBuffer(512);
        final int mapiFindNext = Win32Mapi.MAPIFindNext(n, (control != null) ? control.getHandle() : 0, s, s2, n2, 0, sb);
        if (mapiFindNext == 16) {
            return null;
        }
        if (mapiFindNext != 0) {
            throw new MapiException(mapiFindNext);
        }
        return sb.toString();
    }
    
    public static void logoff(final int n, final Control control) throws MapiException {
        final int mapiLogoff = Win32Mapi.MAPILogoff(n, (control != null) ? control.getHandle() : 0, 0, 0);
        if (mapiLogoff != 0) {
            throw new MapiException(mapiLogoff);
        }
    }
    
    public static void sendMail(final int n, final String subject, final String noteText, final String name, final String str, final String pathName, final String fileName, final Control control, final int n2) throws MapiException {
        final MapiMessage mapiMessage = new MapiMessage();
        mapiMessage.subject = subject;
        mapiMessage.noteText = noteText;
        if (str != null) {
            final MapiRecipDesc mapiRecipDesc = new MapiRecipDesc();
            mapiRecipDesc.recipClass = 1;
            mapiRecipDesc.name = name;
            mapiRecipDesc.address = "SMTP:" + str;
            (mapiMessage.recips = new MapiRecipDesc[1])[0] = mapiRecipDesc;
        }
        else {
            mapiMessage.recips = null;
        }
        if (pathName != null) {
            final MapiFileDesc mapiFileDesc = new MapiFileDesc();
            mapiFileDesc.flags = 0;
            mapiFileDesc.pathName = pathName;
            mapiFileDesc.fileName = fileName;
            (mapiMessage.files = new MapiFileDesc[1])[0] = mapiFileDesc;
        }
        else {
            mapiMessage.files = null;
        }
        sendMail(n, mapiMessage, control, n2);
    }
    
    public static void sendMail(final int n, final String subject, final String noteText, final MapiRecipDesc[] recips, final MapiFileDesc[] files, final Control control, final int n2) throws MapiException {
        final MapiMessage mapiMessage = new MapiMessage();
        mapiMessage.recips = recips;
        mapiMessage.files = files;
        mapiMessage.subject = subject;
        mapiMessage.noteText = noteText;
        sendMail(n, mapiMessage, control, n2);
    }
    
    public static void sendMail(final int n, final MapiMessage mapiMessage, final Control control, final int n2) throws MapiException {
        final MapiRecipDesc[] recips = mapiMessage.recips;
        final MapiFileDesc[] files = mapiMessage.files;
        Win32MapiRecipDesc win32MapiRecipDesc = null;
        Win32MapiRecipDesc[] array = null;
        Win32MapiFileDesc[] array2 = null;
        final int nRecipCount = (recips != null) ? recips.length : 0;
        final int nFileCount = (files != null) ? files.length : 0;
        final Win32MapiMessage win32MapiMessage = new Win32MapiMessage();
        win32MapiMessage.subject = DllLib.stringToCoTaskMemAnsi(mapiMessage.subject);
        win32MapiMessage.noteText = DllLib.stringToCoTaskMemAnsi(mapiMessage.noteText);
        win32MapiMessage.messageType = DllLib.stringToCoTaskMemAnsi(mapiMessage.messageType);
        win32MapiMessage.dateReceived = DllLib.stringToCoTaskMemAnsi(mapiMessage.dateReceived);
        win32MapiMessage.conversationID = DllLib.stringToCoTaskMemAnsi(mapiMessage.conversationID);
        win32MapiMessage.flags = mapiMessage.flags;
        win32MapiMessage.pOriginator = 0;
        win32MapiMessage.nRecipCount = nRecipCount;
        win32MapiMessage.nFileCount = nFileCount;
        final int alloc = Root.alloc((Object)win32MapiMessage);
        final int addr = DllLib.addrOf(alloc);
        if (mapiMessage.originator != null) {
            final Win32MapiRecipDesc win32MapiRecipDesc2 = new Win32MapiRecipDesc();
            final int size = DllLib.sizeOf((Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc = class$("com.mim.wfc.mapi.Win32MapiRecipDesc")));
            final int allocCoTaskMem = DllLib.allocCoTaskMem(size);
            win32MapiRecipDesc = new Win32MapiRecipDesc();
            win32MapiRecipDesc.nReserved = 0;
            win32MapiRecipDesc.recipClass = mapiMessage.originator.recipClass;
            win32MapiRecipDesc.name = DllLib.stringToCoTaskMemAnsi(mapiMessage.originator.name);
            win32MapiRecipDesc.address = DllLib.stringToCoTaskMemAnsi(mapiMessage.originator.address);
            win32MapiRecipDesc.EIDSize = 0;
            DllLib.copy((Object)win32MapiRecipDesc, win32MapiRecipDesc.pEntryID = 0, allocCoTaskMem, size);
            win32MapiMessage.pOriginator = allocCoTaskMem;
        }
        else {
            win32MapiMessage.pOriginator = 0;
        }
        if (nRecipCount > 0) {
            array = new Win32MapiRecipDesc[nRecipCount];
            final int size2 = DllLib.sizeOf((Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc = class$("com.mim.wfc.mapi.Win32MapiRecipDesc")));
            final int allocCoTaskMem2 = DllLib.allocCoTaskMem(size2 * nRecipCount);
            for (int i = 0; i < nRecipCount; ++i) {
                array[i] = new Win32MapiRecipDesc();
                array[i].nReserved = 0;
                array[i].recipClass = recips[i].recipClass;
                array[i].name = DllLib.stringToCoTaskMemAnsi(recips[i].name);
                array[i].address = DllLib.stringToCoTaskMemAnsi(recips[i].address);
                array[i].EIDSize = 0;
                array[i].pEntryID = 0;
                DllLib.copy((Object)array[i], 0, allocCoTaskMem2 + i * size2, size2);
            }
            win32MapiMessage.pRecips = allocCoTaskMem2;
        }
        else {
            win32MapiMessage.pRecips = 0;
        }
        if (nFileCount > 0) {
            array2 = new Win32MapiFileDesc[nFileCount];
            final int size3 = DllLib.sizeOf((Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc = class$("com.mim.wfc.mapi.Win32MapiFileDesc")));
            final int allocCoTaskMem3 = DllLib.allocCoTaskMem(size3 * nFileCount);
            for (int j = 0; j < nFileCount; ++j) {
                array2[j] = new Win32MapiFileDesc();
                array2[j].nReserved = 0;
                array2[j].flags = files[j].flags;
                array2[j].nPosition = -1;
                array2[j].pathName = DllLib.stringToCoTaskMemAnsi(files[j].pathName);
                array2[j].fileName = DllLib.stringToCoTaskMemAnsi(files[j].fileName);
                array2[j].pFileType = 0;
                DllLib.copy((Object)array2[j], 0, allocCoTaskMem3 + j * size3, size3);
            }
            win32MapiMessage.pFiles = allocCoTaskMem3;
        }
        else {
            win32MapiMessage.pFiles = 0;
        }
        final int mapiSendMail = Win32Mapi.MAPISendMail(n, (control != null) ? control.getHandle() : 0, addr, n2, 0);
        if (nRecipCount > 0) {
            for (int k = 0; k < nRecipCount; ++k) {
                DllLib.freeCoTaskMem(array[k].name);
                DllLib.freeCoTaskMem(array[k].address);
            }
            DllLib.freeCoTaskMem(win32MapiMessage.pRecips);
        }
        if (nFileCount > 0) {
            for (int l = 0; l < nFileCount; ++l) {
                DllLib.freeCoTaskMem(array2[l].pathName);
                DllLib.freeCoTaskMem(array2[l].fileName);
            }
            DllLib.freeCoTaskMem(win32MapiMessage.pFiles);
        }
        if (win32MapiMessage.pOriginator != 0) {
            DllLib.freeCoTaskMem(win32MapiRecipDesc.name);
            DllLib.freeCoTaskMem(win32MapiRecipDesc.address);
            DllLib.freeCoTaskMem(win32MapiMessage.pOriginator);
        }
        DllLib.freeCoTaskMem(win32MapiMessage.messageType);
        DllLib.freeCoTaskMem(win32MapiMessage.subject);
        DllLib.freeCoTaskMem(win32MapiMessage.noteText);
        DllLib.freeCoTaskMem(win32MapiMessage.dateReceived);
        DllLib.freeCoTaskMem(win32MapiMessage.conversationID);
        Root.free(alloc);
        if (mapiSendMail != 0) {
            throw new MapiException(mapiSendMail);
        }
    }
    
    public static String saveMail(final int n, final Control control, final MapiMessage mapiMessage, final int n2, final String s) throws MapiException {
        return null;
    }
    
    static {
        _cls753A._mth821F();
    }
    
    private static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public static void sendDocuments(final Control control, final String[] array, final String[] array2) throws MapiException {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (i > 0) {
                    sb.append(";");
                }
                sb.append(array[i]);
            }
        }
        if (array2 != null) {
            for (int j = 0; j < array2.length; ++j) {
                if (j > 0) {
                    sb2.append(";");
                }
                sb2.append(array2[j]);
            }
        }
        final int mapiSendDocuments = Win32Mapi.MAPISendDocuments((control != null) ? control.getHandle() : 0, ";", sb.toString(), sb2.toString(), 0);
        if (mapiSendDocuments != 0) {
            throw new MapiException(mapiSendDocuments);
        }
    }
    
    public static MapiMessage readMail(final int n, final Control control, final String s, final int n2) throws MapiException {
        new StringBuffer(512);
        final int[] array = { 0 };
        final int mapiReadMail = Win32Mapi.MAPIReadMail(n, (control != null) ? control.getHandle() : 0, s, n2, 0, array);
        if (mapiReadMail != 0) {
            throw new MapiException(mapiReadMail);
        }
        final int n3 = array[0];
        final Win32MapiMessage win32MapiMessage = (Win32MapiMessage)DllLib.ptrToStruct((Mapi.class$com$mim$wfc$mapi$Win32MapiMessage != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiMessage : (Mapi.class$com$mim$wfc$mapi$Win32MapiMessage = class$("com.mim.wfc.mapi.Win32MapiMessage")), n3);
        final MapiMessage mapiMessage = new MapiMessage();
        mapiMessage.subject = DllLib.ptrToStringAnsi(win32MapiMessage.subject);
        mapiMessage.noteText = DllLib.ptrToStringAnsi(win32MapiMessage.noteText);
        mapiMessage.messageType = DllLib.ptrToStringAnsi(win32MapiMessage.messageType);
        mapiMessage.dateReceived = DllLib.ptrToStringAnsi(win32MapiMessage.dateReceived);
        mapiMessage.conversationID = DllLib.ptrToStringAnsi(win32MapiMessage.conversationID);
        mapiMessage.flags = win32MapiMessage.flags;
        if (win32MapiMessage.pOriginator != 0) {
            final MapiMessage mapiMessage2 = mapiMessage;
            final MapiRecipDesc originator = new MapiRecipDesc();
            mapiMessage2.originator = originator;
            final MapiRecipDesc mapiRecipDesc = originator;
            final Win32MapiRecipDesc win32MapiRecipDesc = (Win32MapiRecipDesc)DllLib.ptrToStruct((Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc = class$("com.mim.wfc.mapi.Win32MapiRecipDesc")), win32MapiMessage.pOriginator);
            mapiRecipDesc.recipClass = win32MapiRecipDesc.recipClass;
            mapiRecipDesc.name = DllLib.ptrToStringAnsi(win32MapiRecipDesc.name);
            mapiRecipDesc.address = DllLib.ptrToStringAnsi(win32MapiRecipDesc.address);
        }
        final int nRecipCount = win32MapiMessage.nRecipCount;
        if (nRecipCount > 0 && win32MapiMessage.pRecips != 0) {
            final MapiMessage mapiMessage3 = mapiMessage;
            final MapiRecipDesc[] recips = new MapiRecipDesc[nRecipCount];
            mapiMessage3.recips = recips;
            final MapiRecipDesc[] array2 = recips;
            final int size = DllLib.sizeOf((Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc = class$("com.mim.wfc.mapi.Win32MapiRecipDesc")));
            final int pRecips = win32MapiMessage.pRecips;
            for (int i = 0; i < nRecipCount; ++i) {
                array2[i] = new MapiRecipDesc();
                final Win32MapiRecipDesc win32MapiRecipDesc2 = (Win32MapiRecipDesc)DllLib.ptrToStruct((Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiRecipDesc = class$("com.mim.wfc.mapi.Win32MapiRecipDesc")), pRecips + i * size);
                array2[i].recipClass = win32MapiRecipDesc2.recipClass;
                array2[i].name = DllLib.ptrToStringAnsi(win32MapiRecipDesc2.name);
                array2[i].address = DllLib.ptrToStringAnsi(win32MapiRecipDesc2.address);
            }
        }
        final int nFileCount = win32MapiMessage.nFileCount;
        if (nFileCount > 0 && win32MapiMessage.pFiles != 0) {
            final MapiMessage mapiMessage4 = mapiMessage;
            final MapiFileDesc[] files = new MapiFileDesc[nFileCount];
            mapiMessage4.files = files;
            final MapiFileDesc[] array3 = files;
            final int size2 = DllLib.sizeOf((Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc = class$("com.mim.wfc.mapi.Win32MapiFileDesc")));
            final int pFiles = win32MapiMessage.pFiles;
            for (int j = 0; j < nFileCount; ++j) {
                array3[j] = new MapiFileDesc();
                final Win32MapiFileDesc win32MapiFileDesc = (Win32MapiFileDesc)DllLib.ptrToStruct((Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc != null) ? Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc : (Mapi.class$com$mim$wfc$mapi$Win32MapiFileDesc = class$("com.mim.wfc.mapi.Win32MapiFileDesc")), pFiles + j * size2);
                array3[j].pathName = DllLib.ptrToStringAnsi(win32MapiFileDesc.pathName);
                array3[j].fileName = DllLib.ptrToStringAnsi(win32MapiFileDesc.fileName);
            }
        }
        Win32Mapi.MAPIFreeBuffer(n3);
        return mapiMessage;
    }
}
