// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.Vector;
import java.io.File;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class LogFileRef_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1430537160551055871L;
    
    static {
        operations = new Operation[] { new Operation("void closeFile()"), new Operation("java.io.File getFile()"), new Operation("java.util.Vector getFileData(int)"), new Operation("java.lang.String getFileLength()"), new Operation("java.lang.String getFilePathName()"), new Operation("long getLastReadPosition()"), new Operation("com.progress.common.log.Monitor getMonitor()"), new Operation("long getNextReadPosition()"), new Operation("boolean isBackward()"), new Operation("boolean isFoward()"), new Operation("void setBackward()"), new Operation("void setFile(java.io.File)"), new Operation("void setFilter(java.lang.String)"), new Operation("void setForward()"), new Operation("void setLastReadPosition(long)"), new Operation("void setMonitor(com.progress.common.log.Monitor)"), new Operation("void setNextReadPosition(long)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 1430537160551055871L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final LogFileRef logFileRef = (LogFileRef)remote;
    Label_0958_Outer:
        while (true) {
            while (true) {
                switch (n) {
                    case 2: {
                        break Label_0958_Outer;
                    }
                    case 0: {
                        remoteCall.releaseInputStream();
                        logFileRef.closeFile();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex) {
                            throw new MarshalException("error marshalling return", ex);
                        }
                    }
                    case 1: {
                        remoteCall.releaseInputStream();
                        final File file = logFileRef.getFile();
                        try {
                            remoteCall.getResultStream(true).writeObject(file);
                            return;
                        }
                        catch (IOException ex2) {
                            throw new MarshalException("error marshalling return", ex2);
                        }
                        int int1;
                        try {
                            int1 = remoteCall.getInputStream().readInt();
                        }
                        catch (IOException ex3) {
                            throw new UnmarshalException("error unmarshalling arguments", ex3);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final Vector fileData = logFileRef.getFileData(int1);
                        try {
                            remoteCall.getResultStream(true).writeObject(fileData);
                            return;
                        }
                        catch (IOException ex4) {
                            throw new MarshalException("error marshalling return", ex4);
                        }
                    }
                    case 3: {
                        remoteCall.releaseInputStream();
                        final String fileLength = logFileRef.getFileLength();
                        try {
                            remoteCall.getResultStream(true).writeObject(fileLength);
                            return;
                        }
                        catch (IOException ex5) {
                            throw new MarshalException("error marshalling return", ex5);
                        }
                    }
                    case 4: {
                        remoteCall.releaseInputStream();
                        final String filePathName = logFileRef.getFilePathName();
                        try {
                            remoteCall.getResultStream(true).writeObject(filePathName);
                            return;
                        }
                        catch (IOException ex6) {
                            throw new MarshalException("error marshalling return", ex6);
                        }
                    }
                    case 5: {
                        remoteCall.releaseInputStream();
                        final long lastReadPosition = logFileRef.getLastReadPosition();
                        try {
                            remoteCall.getResultStream(true).writeLong(lastReadPosition);
                            return;
                        }
                        catch (IOException ex7) {
                            throw new MarshalException("error marshalling return", ex7);
                        }
                    }
                    case 6: {
                        remoteCall.releaseInputStream();
                        final Monitor monitor = logFileRef.getMonitor();
                        try {
                            remoteCall.getResultStream(true).writeObject(monitor);
                            return;
                        }
                        catch (IOException ex8) {
                            throw new MarshalException("error marshalling return", ex8);
                        }
                    }
                    case 7: {
                        remoteCall.releaseInputStream();
                        final long nextReadPosition = logFileRef.getNextReadPosition();
                        try {
                            remoteCall.getResultStream(true).writeLong(nextReadPosition);
                            return;
                        }
                        catch (IOException ex9) {
                            throw new MarshalException("error marshalling return", ex9);
                        }
                    }
                    case 9: {
                        remoteCall.releaseInputStream();
                        final boolean foward = logFileRef.isFoward();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(foward);
                            return;
                        }
                        catch (IOException ex14) {
                            throw new MarshalException("error marshalling return", ex14);
                        }
                    }
                    case 8: {
                        remoteCall.releaseInputStream();
                        final boolean backward = logFileRef.isBackward();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(backward);
                            return;
                        }
                        catch (IOException ex10) {
                            throw new MarshalException("error marshalling return", ex10);
                        }
                    }
                    case 11: {
                        continue Label_0958_Outer;
                    }
                    case 10: {
                        remoteCall.releaseInputStream();
                        logFileRef.setBackward();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex11) {
                            throw new MarshalException("error marshalling return", ex11);
                        }
                        try {
                            final File file2 = (File)remoteCall.getInputStream().readObject();
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
                    }
                    case 12: {
                        String filter = null;
                        Label_0801: {
                            break Label_0801;
                            final File file2;
                            logFileRef.setFile(file2);
                            try {
                                remoteCall.getResultStream(true);
                                return;
                            }
                            catch (IOException ex15) {
                                throw new MarshalException("error marshalling return", ex15);
                            }
                            try {
                                filter = (String)remoteCall.getInputStream().readObject();
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
                        logFileRef.setFilter(filter);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex18) {
                            throw new MarshalException("error marshalling return", ex18);
                        }
                    }
                    case 14: {
                        continue;
                    }
                    case 13: {
                        remoteCall.releaseInputStream();
                        logFileRef.setForward();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex19) {
                            throw new MarshalException("error marshalling return", ex19);
                        }
                        try {
                            final long long1 = remoteCall.getInputStream().readLong();
                        }
                        catch (IOException ex20) {
                            throw new UnmarshalException("error unmarshalling arguments", ex20);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    case 15: {
                        Label_1062: {
                            break Label_1062;
                            final long long1;
                            logFileRef.setLastReadPosition(long1);
                            try {
                                remoteCall.getResultStream(true);
                                return;
                            }
                            catch (IOException ex21) {
                                throw new MarshalException("error marshalling return", ex21);
                            }
                            try {
                                final Monitor monitor2 = (Monitor)remoteCall.getInputStream().readObject();
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
                    case 16: {
                        long long2 = 0L;
                        Label_1183: {
                            break Label_1183;
                            final Monitor monitor2;
                            logFileRef.setMonitor(monitor2);
                            try {
                                remoteCall.getResultStream(true);
                                return;
                            }
                            catch (IOException ex24) {
                                throw new MarshalException("error marshalling return", ex24);
                            }
                            try {
                                long2 = remoteCall.getInputStream().readLong();
                            }
                            catch (IOException ex25) {
                                throw new UnmarshalException("error unmarshalling arguments", ex25);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        logFileRef.setNextReadPosition(long2);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex26) {
                            throw new MarshalException("error marshalling return", ex26);
                        }
                        break;
                    }
                }
                break;
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return LogFileRef_Skel.operations.clone();
    }
}
