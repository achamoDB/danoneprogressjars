// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.util.Hashtable;
import java.io.ObjectInput;
import java.util.Enumeration;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class ubListenerThread_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8236540001301514060L;
    
    static {
        operations = new Operation[] { new Operation("int emergencyShutdown()"), new Operation("java.lang.Object getData(java.lang.String[])"), new Operation("java.util.Enumeration getDetailStatusRSFieldLabels()"), new Operation("java.util.Enumeration getStatusArray(int)"), new Operation("java.lang.String getStatusFormatted(int)"), new Operation("java.util.Hashtable getStatusStructured(int, java.lang.Object)"), new Operation("java.util.Enumeration getSummaryStatusRSData()"), new Operation("java.util.Enumeration getSummaryStatusRSFieldLabels()"), new Operation("int invokeCommand(int, java.lang.Object[])"), new Operation("long ping()"), new Operation("int shutDown()"), new Operation("int startServers(int)"), new Operation("boolean stopServer(int)"), new Operation("int trimBy(int)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 8236540001301514060L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final ubListenerThread ubListenerThread = (ubListenerThread)remote;
    Label_1034_Outer:
        while (true) {
            Label_0794: {
                while (true) {
                    switch (n) {
                        case 1: {
                            break Label_1034_Outer;
                        }
                        case 0: {
                            remoteCall.releaseInputStream();
                            final int emergencyShutdown = ubListenerThread.emergencyShutdown();
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
                            final Object data = ubListenerThread.getData(array);
                            try {
                                remoteCall.getResultStream(true).writeObject(data);
                                return;
                            }
                            catch (IOException ex6) {
                                throw new MarshalException("error marshalling return", ex6);
                            }
                        }
                        case 3: {
                            continue Label_1034_Outer;
                        }
                        case 2: {
                            remoteCall.releaseInputStream();
                            final Enumeration detailStatusRSFieldLabels = ubListenerThread.getDetailStatusRSFieldLabels();
                            try {
                                remoteCall.getResultStream(true).writeObject(detailStatusRSFieldLabels);
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
                        case 4: {
                            Label_0441: {
                                break Label_0441;
                                final int int1;
                                final Enumeration statusArray = ubListenerThread.getStatusArray(int1);
                                try {
                                    remoteCall.getResultStream(true).writeObject(statusArray);
                                    return;
                                }
                                catch (IOException ex7) {
                                    throw new MarshalException("error marshalling return", ex7);
                                }
                                try {
                                    final int int2 = remoteCall.getInputStream().readInt();
                                }
                                catch (IOException ex8) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex8);
                                }
                                finally {
                                    remoteCall.releaseInputStream();
                                }
                            }
                        }
                        case 5: {
                            int int3 = 0;
                            Object object = null;
                            Label_0557: {
                                break Label_0557;
                                final int int2;
                                final String statusFormatted = ubListenerThread.getStatusFormatted(int2);
                                try {
                                    remoteCall.getResultStream(true).writeObject(statusFormatted);
                                    return;
                                }
                                catch (IOException ex9) {
                                    throw new MarshalException("error marshalling return", ex9);
                                }
                                try {
                                    final ObjectInput inputStream = remoteCall.getInputStream();
                                    int3 = inputStream.readInt();
                                    object = inputStream.readObject();
                                }
                                catch (IOException ex10) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex10);
                                }
                                catch (ClassNotFoundException ex11) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex11);
                                }
                                finally {
                                    remoteCall.releaseInputStream();
                                }
                            }
                            final Hashtable statusStructured = ubListenerThread.getStatusStructured(int3, object);
                            try {
                                remoteCall.getResultStream(true).writeObject(statusStructured);
                                return;
                            }
                            catch (IOException ex12) {
                                throw new MarshalException("error marshalling return", ex12);
                            }
                        }
                        case 8: {
                            break Label_0794;
                        }
                        case 6: {
                            remoteCall.releaseInputStream();
                            final Enumeration summaryStatusRSData = ubListenerThread.getSummaryStatusRSData();
                            try {
                                remoteCall.getResultStream(true).writeObject(summaryStatusRSData);
                                return;
                            }
                            catch (IOException ex13) {
                                throw new MarshalException("error marshalling return", ex13);
                            }
                        }
                        case 9: {
                            remoteCall.releaseInputStream();
                            final long ping = ubListenerThread.ping();
                            try {
                                remoteCall.getResultStream(true).writeLong(ping);
                                return;
                            }
                            catch (IOException ex20) {
                                throw new MarshalException("error marshalling return", ex20);
                            }
                        }
                        case 7: {
                            remoteCall.releaseInputStream();
                            final Enumeration summaryStatusRSFieldLabels = ubListenerThread.getSummaryStatusRSFieldLabels();
                            try {
                                remoteCall.getResultStream(true).writeObject(summaryStatusRSFieldLabels);
                                return;
                            }
                            catch (IOException ex14) {
                                throw new MarshalException("error marshalling return", ex14);
                            }
                            int int4;
                            Object[] array2;
                            try {
                                final ObjectInput inputStream2 = remoteCall.getInputStream();
                                int4 = inputStream2.readInt();
                                array2 = (Object[])inputStream2.readObject();
                            }
                            catch (IOException ex15) {
                                throw new UnmarshalException("error unmarshalling arguments", ex15);
                            }
                            catch (ClassNotFoundException ex16) {
                                throw new UnmarshalException("error unmarshalling arguments", ex16);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                            final int invokeCommand = ubListenerThread.invokeCommand(int4, array2);
                            try {
                                remoteCall.getResultStream(true).writeInt(invokeCommand);
                                return;
                            }
                            catch (IOException ex17) {
                                throw new MarshalException("error marshalling return", ex17);
                            }
                        }
                        case 11: {
                            continue;
                        }
                        case 10: {
                            remoteCall.releaseInputStream();
                            final int shutDown = ubListenerThread.shutDown();
                            try {
                                remoteCall.getResultStream(true).writeInt(shutDown);
                                return;
                            }
                            catch (IOException ex18) {
                                throw new MarshalException("error marshalling return", ex18);
                            }
                            try {
                                final int int5 = remoteCall.getInputStream().readInt();
                            }
                            catch (IOException ex19) {
                                throw new UnmarshalException("error unmarshalling arguments", ex19);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        case 12: {
                            Label_1150: {
                                break Label_1150;
                                final int int5;
                                final int startServers = ubListenerThread.startServers(int5);
                                try {
                                    remoteCall.getResultStream(true).writeInt(startServers);
                                    return;
                                }
                                catch (IOException ex21) {
                                    throw new MarshalException("error marshalling return", ex21);
                                }
                                try {
                                    final int int6 = remoteCall.getInputStream().readInt();
                                }
                                catch (IOException ex22) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex22);
                                }
                                finally {
                                    remoteCall.releaseInputStream();
                                }
                            }
                        }
                        case 13: {
                            int int7 = 0;
                            Label_1266: {
                                break Label_1266;
                                final int int6;
                                final boolean stopServer = ubListenerThread.stopServer(int6);
                                try {
                                    remoteCall.getResultStream(true).writeBoolean(stopServer);
                                    return;
                                }
                                catch (IOException ex23) {
                                    throw new MarshalException("error marshalling return", ex23);
                                }
                                try {
                                    int7 = remoteCall.getInputStream().readInt();
                                }
                                catch (IOException ex24) {
                                    throw new UnmarshalException("error unmarshalling arguments", ex24);
                                }
                                finally {
                                    remoteCall.releaseInputStream();
                                }
                            }
                            final int trimBy = ubListenerThread.trimBy(int7);
                            try {
                                remoteCall.getResultStream(true).writeInt(trimBy);
                                return;
                            }
                            catch (IOException ex25) {
                                throw new MarshalException("error marshalling return", ex25);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return ubListenerThread_Skel.operations.clone();
    }
}
