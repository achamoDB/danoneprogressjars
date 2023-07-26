// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import java.util.Hashtable;
import java.io.ObjectInput;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class NameServer_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 816107662656773740L;
    
    static {
        operations = new Operation[] { new Operation("int emergencyShutdown()"), new Operation("java.lang.Object getData(java.lang.String[])"), new Operation("com.progress.nameserver.NameServer.AppService getDetailStatus()[]"), new Operation("java.lang.String getStatusFormatted(int)"), new Operation("java.util.Hashtable getStatusStructured(int, java.lang.Object)"), new Operation("java.lang.Object getSummaryStatus()[][]"), new Operation("int invokeCommand(int, java.lang.Object[])"), new Operation("long ping()"), new Operation("int shutDown()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 816107662656773740L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final NameServer nameServer = (NameServer)remote;
        Label_0124: {
            while (true) {
                Label_0610: {
                    switch (n) {
                        case 1: {
                            break Label_0124;
                        }
                        case 0: {
                            remoteCall.releaseInputStream();
                            final int emergencyShutdown = nameServer.emergencyShutdown();
                            try {
                                remoteCall.getResultStream(true).writeInt(emergencyShutdown);
                                return;
                            }
                            catch (IOException ex3) {
                                throw new MarshalException("error marshalling return", ex3);
                            }
                            String[] array;
                            try {
                                array = (String[])remoteCall.getInputStream().readObject();
                            }
                            catch (IOException ex4) {
                                throw new UnmarshalException("error unmarshalling arguments", ex4);
                            }
                            catch (ClassNotFoundException ex5) {
                                throw new UnmarshalException("error unmarshalling arguments", ex5);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                            final Object data = nameServer.getData(array);
                            try {
                                remoteCall.getResultStream(true).writeObject(data);
                                return;
                            }
                            catch (IOException ex6) {
                                throw new MarshalException("error marshalling return", ex6);
                            }
                        }
                        case 3: {
                            continue;
                        }
                        case 2: {
                            remoteCall.releaseInputStream();
                            final NameServer.AppService[] detailStatus = nameServer.getDetailStatus();
                            try {
                                remoteCall.getResultStream(true).writeObject(detailStatus);
                                return;
                            }
                            catch (IOException ex) {
                                throw new MarshalException("error marshalling return", ex);
                            }
                            try {
                                final int int1 = remoteCall.getInputStream().readInt();
                            }
                            catch (IOException ex2) {
                                throw new UnmarshalException("error unmarshalling arguments", ex2);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        case 6: {
                            break Label_0610;
                        }
                        case 4: {
                            int int2 = 0;
                            Object object = null;
                            Label_0421: {
                                break Label_0421;
                                final int int1;
                                final String statusFormatted = nameServer.getStatusFormatted(int1);
                                try {
                                    remoteCall.getResultStream(true).writeObject(statusFormatted);
                                    return;
                                }
                                catch (IOException ex7) {
                                    throw new MarshalException("error marshalling return", ex7);
                                }
                                try {
                                    final ObjectInput inputStream = remoteCall.getInputStream();
                                    int2 = inputStream.readInt();
                                    object = inputStream.readObject();
                                }
                                catch (IOException ex8) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex8);
                                }
                                catch (ClassNotFoundException ex9) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex9);
                                }
                                finally {
                                    remoteCall.releaseInputStream();
                                }
                            }
                            final Hashtable statusStructured = nameServer.getStatusStructured(int2, object);
                            try {
                                remoteCall.getResultStream(true).writeObject(statusStructured);
                                return;
                            }
                            catch (IOException ex10) {
                                throw new MarshalException("error marshalling return", ex10);
                            }
                        }
                        case 7: {
                            remoteCall.releaseInputStream();
                            final long ping = nameServer.ping();
                            try {
                                remoteCall.getResultStream(true).writeLong(ping);
                                return;
                            }
                            catch (IOException ex16) {
                                throw new MarshalException("error marshalling return", ex16);
                            }
                        }
                        case 8: {
                            remoteCall.releaseInputStream();
                            final int shutDown = nameServer.shutDown();
                            try {
                                remoteCall.getResultStream(true).writeInt(shutDown);
                                return;
                            }
                            catch (IOException ex15) {
                                throw new MarshalException("error marshalling return", ex15);
                            }
                            break;
                        }
                        case 5: {
                            remoteCall.releaseInputStream();
                            final Object[][] summaryStatus = nameServer.getSummaryStatus();
                            try {
                                remoteCall.getResultStream(true).writeObject(summaryStatus);
                                return;
                            }
                            catch (IOException ex11) {
                                throw new MarshalException("error marshalling return", ex11);
                            }
                            int int3;
                            Object[] array2;
                            try {
                                final ObjectInput inputStream2 = remoteCall.getInputStream();
                                int3 = inputStream2.readInt();
                                array2 = (Object[])inputStream2.readObject();
                            }
                            catch (IOException ex12) {
                                throw new UnmarshalException("error unmarshalling arguments", ex12);
                            }
                            catch (ClassNotFoundException ex13) {
                                throw new UnmarshalException("error unmarshalling arguments", ex13);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                            final int invokeCommand = nameServer.invokeCommand(int3, array2);
                            try {
                                remoteCall.getResultStream(true).writeInt(invokeCommand);
                                return;
                            }
                            catch (IOException ex14) {
                                throw new MarshalException("error marshalling return", ex14);
                            }
                        }
                    }
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return NameServer_Skel.operations.clone();
    }
}
