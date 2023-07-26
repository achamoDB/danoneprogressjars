// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.common.util.InstallPath;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.common.util.ProgressVersion;
import java.rmi.RemoteException;
import java.util.HashSet;
import com.progress.common.util.NtKrnlException;

public class NTAdminServer extends AdminServer
{
    public static final int EVENT_ALL_ACCESS = 2031619;
    boolean isService;
    int serviceEventHandle;
    
    public static native int ntCreateEvent(final boolean p0, final boolean p1, final String p2) throws NtKrnlException;
    
    public static native int ntOpenEvent(final int p0, final boolean p1, final String p2) throws NtKrnlException;
    
    public static native boolean ntSetEvent(final int p0) throws NtKrnlException;
    
    public static native boolean ntCloseHandle(final int p0) throws NtKrnlException;
    
    public NTAdminServer(final String s) throws RemoteException {
        this(new String[0], null);
    }
    
    public NTAdminServer(final String[] array) throws RemoteException {
        this(array, null);
    }
    
    public NTAdminServer(final String[] array, final HashSet set) throws RemoteException {
        super(array, set);
        this.isService = false;
        this.serviceEventHandle = 0;
    }
    
    public void openServiceEventObject() throws Exception {
        final String string = "AdminService" + ProgressVersion.getVersion();
        try {
            this.serviceEventHandle = ntOpenEvent(2031619, false, string);
            if (this.serviceEventHandle == 0) {
                this.isService = false;
                throw new Exception("Failed to open AdminService EventObject, error is unknown");
            }
            this.isService = true;
        }
        catch (NtKrnlException ex) {
            this.isService = false;
            throw new Exception("Failed to open AdminService EventObject, error is " + ex.toString());
        }
    }
    
    public void signalServiceEventObject() throws Exception {
        if (this.serviceEventHandle == 0) {
            try {
                this.openServiceEventObject();
            }
            catch (Exception ex2) {
                return;
            }
        }
        if (!this.isService) {
            return;
        }
        try {
            if (!ntSetEvent(this.serviceEventHandle)) {
                throw new Exception("Failed to signal AdminService EventObject, error is unknown");
            }
        }
        catch (NtKrnlException ex) {
            throw new Exception("Failed to signal AdminService EventObject, error is " + ex.toString());
        }
    }
    
    public void createDBEventObject(String s) throws Exception {
        if (s == null) {
            s = "";
        }
        final String lowerCase = s.toLowerCase();
        if (this.serviceEventHandle == 0) {
            try {
                this.openServiceEventObject();
            }
            catch (Exception ex2) {
                return;
            }
        }
        if (!this.isService) {
            return;
        }
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.NTAMsgBundle");
        AdminServer.m_adminServerSubsystem.log(5, progressResources.getTranString("Confirmed we are a NT service."));
        final String string = "progress.db." + lowerCase.trim().replace('\\', '.');
        try {
            if (ntCreateEvent(false, false, string) == 0) {
                throw new Exception("Failed to create DB EventObject from AdminService, error is unknown");
            }
        }
        catch (NtKrnlException ex) {
            throw new Exception("Failed to create DB EventObject from AdminService, error is " + ex.toString());
        }
        AdminServer.m_adminServerSubsystem.log(5, progressResources.getTranString("Created DB EventObject for", (Object)lowerCase));
    }
    
    public void closeDBEventObject(final String s) throws Exception {
        final String string = "progress.db" + s.trim().replace('\\', '.');
        int ntOpenEvent;
        try {
            ntOpenEvent = ntOpenEvent(2031619, false, string);
            if (ntOpenEvent == 0) {
                return;
            }
        }
        catch (NtKrnlException ex2) {
            return;
        }
        try {
            if (ntCloseHandle(ntOpenEvent)) {
                throw new Exception("Failed to close DB EventObject for " + s);
            }
        }
        catch (NtKrnlException ex) {
            throw new Exception("Failed to close DB EventObject for " + s + " : error= " + ex.toString());
        }
        AdminServer.m_adminServerSubsystem.log(5, ((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.NTAMsgBundle")).getTranString("Closed DB EventObject for", (Object)s));
    }
    
    public static void main(final String[] array) {
        try {
            new NTAdminServer(array, null);
        }
        catch (RemoteException ex) {
            System.err.println(AdminServerType.admBundle.getTranString("AdminServer failed to start:", new Object[] { ex.getMessage() }));
            System.exit(1);
        }
    }
    
    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            final InstallPath installPath = new InstallPath();
            System.load(installPath.fullyQualifyFile("ntjavamain.dll"));
            System.load(installPath.fullyQualifyFile("ntadminserver.dll"));
        }
    }
}
