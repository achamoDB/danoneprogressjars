// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.io.ObjectInput;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class AbstractLogFileReader_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 2549853320296197113L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 2549853320296197113L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AbstractLogFileReader abstractLogFileReader = (AbstractLogFileReader)remote;
        switch (n) {
            case 0: {
                String s;
                IEventListener eventListener;
                try {
                    final ObjectInput inputStream = remoteCall.getInputStream();
                    s = (String)inputStream.readObject();
                    eventListener = (IEventListener)inputStream.readObject();
                }
                catch (IOException ex) {
                    throw new UnmarshalException("error unmarshalling arguments", ex);
                }
                catch (ClassNotFoundException ex2) {
                    throw new UnmarshalException("error unmarshalling arguments", ex2);
                }
                finally {
                    remoteCall.releaseInputStream();
                }
                final IFileRef openFile = abstractLogFileReader.openFile(s, eventListener);
                try {
                    remoteCall.getResultStream(true).writeObject(openFile);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return AbstractLogFileReader_Skel.operations.clone();
    }
}
