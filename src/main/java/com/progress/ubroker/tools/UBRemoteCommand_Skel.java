// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import java.util.Enumeration;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class UBRemoteCommand_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -2650217210895588855L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.util.Hashtable getStructuredSystemOutput()"), new Operation("java.lang.String getSystemError()"), new Operation("java.lang.String getSystemOutput()"), new Operation("int runIt(java.lang.String[])"), new Operation("void setSystemInput(java.lang.String)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -2650217210895588855L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final UBRemoteCommand ubRemoteCommand = (UBRemoteCommand)remote;
        while (true) {
            switch (n) {
                case 0: {
                    remoteCall.releaseInputStream();
                    final Enumeration children = ubRemoteCommand.getChildren();
                    try {
                        remoteCall.getResultStream(true).writeObject(children);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                }
                case 1: {
                    remoteCall.releaseInputStream();
                    final String displayName = ubRemoteCommand.getDisplayName();
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName);
                        return;
                    }
                    catch (IOException ex2) {
                        throw new MarshalException("error marshalling return", ex2);
                    }
                }
                case 2: {
                    remoteCall.releaseInputStream();
                    final Hashtable structuredSystemOutput = ubRemoteCommand.getStructuredSystemOutput();
                    try {
                        remoteCall.getResultStream(true).writeObject(structuredSystemOutput);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final String systemError = ubRemoteCommand.getSystemError();
                    try {
                        remoteCall.getResultStream(true).writeObject(systemError);
                        return;
                    }
                    catch (IOException ex4) {
                        throw new MarshalException("error marshalling return", ex4);
                    }
                }
                case 5: {
                    continue;
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final String systemOutput = ubRemoteCommand.getSystemOutput();
                    try {
                        remoteCall.getResultStream(true).writeObject(systemOutput);
                        return;
                    }
                    catch (IOException ex5) {
                        throw new MarshalException("error marshalling return", ex5);
                    }
                    try {
                        final String[] array = (String[])remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex6) {
                        throw new UnmarshalException("error unmarshalling arguments", ex6);
                    }
                    catch (ClassNotFoundException ex7) {
                        throw new UnmarshalException("error unmarshalling arguments", ex7);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                }
                case 6: {
                    String systemInput = null;
                    Label_0441: {
                        break Label_0441;
                        final String[] array;
                        final int runIt = ubRemoteCommand.runIt(array);
                        try {
                            remoteCall.getResultStream(true).writeInt(runIt);
                            return;
                        }
                        catch (IOException ex8) {
                            throw new MarshalException("error marshalling return", ex8);
                        }
                        try {
                            systemInput = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex9) {
                            throw new UnmarshalException("error unmarshalling arguments", ex9);
                        }
                        catch (ClassNotFoundException ex10) {
                            throw new UnmarshalException("error unmarshalling arguments", ex10);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    ubRemoteCommand.setSystemInput(systemInput);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                    break;
                }
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return UBRemoteCommand_Skel.operations.clone();
    }
}
