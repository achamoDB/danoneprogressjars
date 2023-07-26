// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.io.ObjectInput;
import com.progress.common.rmiregistry.IPingable;
import com.progress.common.rmiregistry.RegistryManager;
import java.rmi.server.RemoteStub;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class EventBroker_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7002897034209665987L;
    
    static {
        operations = new Operation[] { new Operation("void closeEventStream(com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, java.lang.Class, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, java.rmi.server.RemoteStub, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject locateObject(java.lang.String, com.progress.common.rmiregistry.RegistryManager, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject locateObject(java.lang.String, com.progress.common.rmiregistry.RegistryManager, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, int, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventStream openEventStream(java.lang.String)"), new Operation("com.progress.common.networkevents.IEventStream openEventStream(java.lang.String, int)"), new Operation("void ping()"), new Operation("void postEvent(com.progress.common.networkevents.IEventObject)"), new Operation("void printDispatchTableRemote()"), new Operation("void revokeInterest(com.progress.common.networkevents.IEventInterestObject)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -7002897034209665987L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final EventBroker eventBroker = (EventBroker)remote;
        Label_1852: {
            while (true) {
                switch (n) {
                    case 0: {
                        try {
                            final IEventStream eventStream = (IEventStream)remoteCall.getInputStream().readObject();
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
                    }
                    case 1: {
                        Label_0221: {
                            break Label_0221;
                            final IEventStream eventStream;
                            eventBroker.closeEventStream(eventStream);
                            try {
                                remoteCall.getResultStream(true);
                                return;
                            }
                            catch (IOException ex3) {
                                throw new MarshalException("error marshalling return", ex3);
                            }
                            try {
                                final ObjectInput inputStream = remoteCall.getInputStream();
                                final Class clazz = (Class)inputStream.readObject();
                                final IEventListener eventListener = (IEventListener)inputStream.readObject();
                                final IEventStream eventStream2 = (IEventStream)inputStream.readObject();
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
                        }
                    }
                    case 2: {
                        Label_0382: {
                            break Label_0382;
                            final Class clazz;
                            final IEventListener eventListener;
                            final IEventStream eventStream2;
                            final IEventInterestObject expressInterest = eventBroker.expressInterest(clazz, eventListener, eventStream2);
                            try {
                                remoteCall.getResultStream(true).writeObject(expressInterest);
                                return;
                            }
                            catch (IOException ex6) {
                                throw new MarshalException("error marshalling return", ex6);
                            }
                            try {
                                final ObjectInput inputStream2 = remoteCall.getInputStream();
                                final Class clazz2 = (Class)inputStream2.readObject();
                                final IEventListener eventListener2 = (IEventListener)inputStream2.readObject();
                                final Class clazz3 = (Class)inputStream2.readObject();
                                final IEventStream eventStream3 = (IEventStream)inputStream2.readObject();
                            }
                            catch (IOException ex7) {
                                throw new UnmarshalException("error unmarshalling arguments", ex7);
                            }
                            catch (ClassNotFoundException ex8) {
                                throw new UnmarshalException("error unmarshalling arguments", ex8);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 3: {
                        Label_0557: {
                            break Label_0557;
                            final Class clazz2;
                            final IEventListener eventListener2;
                            final Class clazz3;
                            final IEventStream eventStream3;
                            final IEventInterestObject expressInterest2 = eventBroker.expressInterest(clazz2, eventListener2, clazz3, eventStream3);
                            try {
                                remoteCall.getResultStream(true).writeObject(expressInterest2);
                                return;
                            }
                            catch (IOException ex9) {
                                throw new MarshalException("error marshalling return", ex9);
                            }
                            try {
                                final ObjectInput inputStream3 = remoteCall.getInputStream();
                                final Class clazz4 = (Class)inputStream3.readObject();
                                final IEventListener eventListener3 = (IEventListener)inputStream3.readObject();
                                final RemoteStub remoteStub = (RemoteStub)inputStream3.readObject();
                                final IEventStream eventStream4 = (IEventStream)inputStream3.readObject();
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
                    }
                    case 4: {
                        Label_0732: {
                            break Label_0732;
                            final Class clazz4;
                            final IEventListener eventListener3;
                            final RemoteStub remoteStub;
                            final IEventStream eventStream4;
                            final IEventInterestObject expressInterest3 = eventBroker.expressInterest(clazz4, eventListener3, remoteStub, eventStream4);
                            try {
                                remoteCall.getResultStream(true).writeObject(expressInterest3);
                                return;
                            }
                            catch (IOException ex12) {
                                throw new MarshalException("error marshalling return", ex12);
                            }
                            try {
                                final ObjectInput inputStream4 = remoteCall.getInputStream();
                                final String s = (String)inputStream4.readObject();
                                final RegistryManager registryManager = (RegistryManager)inputStream4.readObject();
                                final int int1 = inputStream4.readInt();
                                final IEventListener eventListener4 = (IEventListener)inputStream4.readObject();
                            }
                            catch (IOException ex13) {
                                throw new UnmarshalException("error unmarshalling arguments", ex13);
                            }
                            catch (ClassNotFoundException ex14) {
                                throw new UnmarshalException("error unmarshalling arguments", ex14);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 5: {
                        Label_0904: {
                            break Label_0904;
                            final String s;
                            final RegistryManager registryManager;
                            final int int1;
                            final IEventListener eventListener4;
                            final IEventInterestObject locateObject = eventBroker.locateObject(s, registryManager, int1, eventListener4);
                            try {
                                remoteCall.getResultStream(true).writeObject(locateObject);
                                return;
                            }
                            catch (IOException ex15) {
                                throw new MarshalException("error marshalling return", ex15);
                            }
                            try {
                                final ObjectInput inputStream5 = remoteCall.getInputStream();
                                final String s2 = (String)inputStream5.readObject();
                                final RegistryManager registryManager2 = (RegistryManager)inputStream5.readObject();
                                final IEventListener eventListener5 = (IEventListener)inputStream5.readObject();
                            }
                            catch (IOException ex16) {
                                throw new UnmarshalException("error unmarshalling arguments", ex16);
                            }
                            catch (ClassNotFoundException ex17) {
                                throw new UnmarshalException("error unmarshalling arguments", ex17);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 6: {
                        Label_1065: {
                            break Label_1065;
                            final String s2;
                            final RegistryManager registryManager2;
                            final IEventListener eventListener5;
                            final IEventInterestObject locateObject2 = eventBroker.locateObject(s2, registryManager2, eventListener5);
                            try {
                                remoteCall.getResultStream(true).writeObject(locateObject2);
                                return;
                            }
                            catch (IOException ex18) {
                                throw new MarshalException("error marshalling return", ex18);
                            }
                            try {
                                final ObjectInput inputStream6 = remoteCall.getInputStream();
                                final IPingable pingable = (IPingable)inputStream6.readObject();
                                final int int2 = inputStream6.readInt();
                                final int int3 = inputStream6.readInt();
                                final IEventListener eventListener6 = (IEventListener)inputStream6.readObject();
                            }
                            catch (IOException ex19) {
                                throw new UnmarshalException("error unmarshalling arguments", ex19);
                            }
                            catch (ClassNotFoundException ex20) {
                                throw new UnmarshalException("error unmarshalling arguments", ex20);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 7: {
                        Label_1234: {
                            break Label_1234;
                            final IPingable pingable;
                            final int int2;
                            final int int3;
                            final IEventListener eventListener6;
                            final IEventInterestObject monitorObject = eventBroker.monitorObject(pingable, int2, int3, eventListener6);
                            try {
                                remoteCall.getResultStream(true).writeObject(monitorObject);
                                return;
                            }
                            catch (IOException ex21) {
                                throw new MarshalException("error marshalling return", ex21);
                            }
                            try {
                                final ObjectInput inputStream7 = remoteCall.getInputStream();
                                final IPingable pingable2 = (IPingable)inputStream7.readObject();
                                final int int4 = inputStream7.readInt();
                                final IEventListener eventListener7 = (IEventListener)inputStream7.readObject();
                            }
                            catch (IOException ex22) {
                                throw new UnmarshalException("error unmarshalling arguments", ex22);
                            }
                            catch (ClassNotFoundException ex23) {
                                throw new UnmarshalException("error unmarshalling arguments", ex23);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 8: {
                        Label_1392: {
                            break Label_1392;
                            final IPingable pingable2;
                            final int int4;
                            final IEventListener eventListener7;
                            final IEventInterestObject monitorObject2 = eventBroker.monitorObject(pingable2, int4, eventListener7);
                            try {
                                remoteCall.getResultStream(true).writeObject(monitorObject2);
                                return;
                            }
                            catch (IOException ex24) {
                                throw new MarshalException("error marshalling return", ex24);
                            }
                            try {
                                final ObjectInput inputStream8 = remoteCall.getInputStream();
                                final IPingable pingable3 = (IPingable)inputStream8.readObject();
                                final IEventListener eventListener8 = (IEventListener)inputStream8.readObject();
                            }
                            catch (IOException ex25) {
                                throw new UnmarshalException("error unmarshalling arguments", ex25);
                            }
                            catch (ClassNotFoundException ex26) {
                                throw new UnmarshalException("error unmarshalling arguments", ex26);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 9: {
                        Label_1539: {
                            break Label_1539;
                            final IPingable pingable3;
                            final IEventListener eventListener8;
                            final IEventInterestObject monitorObject3 = eventBroker.monitorObject(pingable3, eventListener8);
                            try {
                                remoteCall.getResultStream(true).writeObject(monitorObject3);
                                return;
                            }
                            catch (IOException ex27) {
                                throw new MarshalException("error marshalling return", ex27);
                            }
                            try {
                                final String s3 = (String)remoteCall.getInputStream().readObject();
                            }
                            catch (IOException ex28) {
                                throw new UnmarshalException("error unmarshalling arguments", ex28);
                            }
                            catch (ClassNotFoundException ex29) {
                                throw new UnmarshalException("error unmarshalling arguments", ex29);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 12: {
                        break Label_1852;
                    }
                    case 11: {
                        remoteCall.releaseInputStream();
                        eventBroker.ping();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex38) {
                            throw new MarshalException("error marshalling return", ex38);
                        }
                        IEventObject eventObject;
                        try {
                            eventObject = (IEventObject)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex39) {
                            throw new UnmarshalException("error unmarshalling arguments", ex39);
                        }
                        catch (ClassNotFoundException ex40) {
                            throw new UnmarshalException("error unmarshalling arguments", ex40);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        eventBroker.postEvent(eventObject);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex41) {
                            throw new MarshalException("error marshalling return", ex41);
                        }
                    }
                    case 13: {
                        remoteCall.releaseInputStream();
                        eventBroker.printDispatchTableRemote();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex34) {
                            throw new MarshalException("error marshalling return", ex34);
                        }
                        IEventInterestObject eventInterestObject;
                        try {
                            eventInterestObject = (IEventInterestObject)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex35) {
                            throw new UnmarshalException("error unmarshalling arguments", ex35);
                        }
                        catch (ClassNotFoundException ex36) {
                            throw new UnmarshalException("error unmarshalling arguments", ex36);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        eventBroker.revokeInterest(eventInterestObject);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex37) {
                            throw new MarshalException("error marshalling return", ex37);
                        }
                        break;
                    }
                    case 10: {
                        String s4 = null;
                        int int5 = 0;
                        Label_1672: {
                            break Label_1672;
                            final String s3;
                            final IEventStream openEventStream = eventBroker.openEventStream(s3);
                            try {
                                remoteCall.getResultStream(true).writeObject(openEventStream);
                                return;
                            }
                            catch (IOException ex30) {
                                throw new MarshalException("error marshalling return", ex30);
                            }
                            try {
                                final ObjectInput inputStream9 = remoteCall.getInputStream();
                                s4 = (String)inputStream9.readObject();
                                int5 = inputStream9.readInt();
                            }
                            catch (IOException ex31) {
                                throw new UnmarshalException("error unmarshalling arguments", ex31);
                            }
                            catch (ClassNotFoundException ex32) {
                                throw new UnmarshalException("error unmarshalling arguments", ex32);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        final IEventStream openEventStream2 = eventBroker.openEventStream(s4, int5);
                        try {
                            remoteCall.getResultStream(true).writeObject(openEventStream2);
                            return;
                        }
                        catch (IOException ex33) {
                            throw new MarshalException("error marshalling return", ex33);
                        }
                    }
                    case 14: {
                        continue;
                    }
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return EventBroker_Skel.operations.clone();
    }
}
