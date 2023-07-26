// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class ProductInfo_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1393871651578745254L;
    
    static {
        operations = new Operation[] { new Operation("int ReleaseUsers(int)"), new Operation("int ReserveUsers(int)"), new Operation("boolean checkR2Run(int)"), new Operation("java.lang.String getControlNumber()"), new Operation("int getCurrentUserCount()"), new Operation("int getMaxUserCount()"), new Operation("java.lang.String getProductDescription()"), new Operation("int getProductID()"), new Operation("java.lang.String getProductVersion()"), new Operation("java.lang.String getSerialNumber()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 1393871651578745254L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final ProductInfo productInfo = (ProductInfo)remote;
        switch (n) {
            case 0: {
                try {
                    final int int1 = remoteCall.getInputStream().readInt();
                }
                catch (IOException ex) {
                    throw new UnmarshalException("error unmarshalling arguments", ex);
                }
                finally {
                    remoteCall.releaseInputStream();
                }
            }
            case 1: {
                Label_0196: {
                    break Label_0196;
                    final int int1;
                    final int releaseUsers = productInfo.ReleaseUsers(int1);
                    try {
                        remoteCall.getResultStream(true).writeInt(releaseUsers);
                        return;
                    }
                    catch (IOException ex2) {
                        throw new MarshalException("error marshalling return", ex2);
                    }
                    try {
                        final int int2 = remoteCall.getInputStream().readInt();
                    }
                    catch (IOException ex3) {
                        throw new UnmarshalException("error unmarshalling arguments", ex3);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                }
            }
            case 2: {
                int int3 = 0;
                Label_0312: {
                    break Label_0312;
                    final int int2;
                    final int reserveUsers = productInfo.ReserveUsers(int2);
                    try {
                        remoteCall.getResultStream(true).writeInt(reserveUsers);
                        return;
                    }
                    catch (IOException ex4) {
                        throw new MarshalException("error marshalling return", ex4);
                    }
                    try {
                        int3 = remoteCall.getInputStream().readInt();
                    }
                    catch (IOException ex5) {
                        throw new UnmarshalException("error unmarshalling arguments", ex5);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                }
                final boolean checkR2Run = productInfo.checkR2Run(int3);
                try {
                    remoteCall.getResultStream(true).writeBoolean(checkR2Run);
                    return;
                }
                catch (IOException ex6) {
                    throw new MarshalException("error marshalling return", ex6);
                }
            }
            case 3: {
                remoteCall.releaseInputStream();
                final String controlNumber = productInfo.getControlNumber();
                try {
                    remoteCall.getResultStream(true).writeObject(controlNumber);
                    return;
                }
                catch (IOException ex7) {
                    throw new MarshalException("error marshalling return", ex7);
                }
            }
            case 4: {
                remoteCall.releaseInputStream();
                final int currentUserCount = productInfo.getCurrentUserCount();
                try {
                    remoteCall.getResultStream(true).writeInt(currentUserCount);
                    return;
                }
                catch (IOException ex8) {
                    throw new MarshalException("error marshalling return", ex8);
                }
            }
            case 5: {
                remoteCall.releaseInputStream();
                final int maxUserCount = productInfo.getMaxUserCount();
                try {
                    remoteCall.getResultStream(true).writeInt(maxUserCount);
                    return;
                }
                catch (IOException ex9) {
                    throw new MarshalException("error marshalling return", ex9);
                }
            }
            case 6: {
                remoteCall.releaseInputStream();
                final String productDescription = productInfo.getProductDescription();
                try {
                    remoteCall.getResultStream(true).writeObject(productDescription);
                    return;
                }
                catch (IOException ex10) {
                    throw new MarshalException("error marshalling return", ex10);
                }
            }
            case 7: {
                remoteCall.releaseInputStream();
                final int productID = productInfo.getProductID();
                try {
                    remoteCall.getResultStream(true).writeInt(productID);
                    return;
                }
                catch (IOException ex11) {
                    throw new MarshalException("error marshalling return", ex11);
                }
            }
            case 8: {
                remoteCall.releaseInputStream();
                final String productVersion = productInfo.getProductVersion();
                try {
                    remoteCall.getResultStream(true).writeObject(productVersion);
                    return;
                }
                catch (IOException ex12) {
                    throw new MarshalException("error marshalling return", ex12);
                }
            }
            case 9: {
                remoteCall.releaseInputStream();
                final String serialNumber = productInfo.getSerialNumber();
                try {
                    remoteCall.getResultStream(true).writeObject(serialNumber);
                    return;
                }
                catch (IOException ex13) {
                    throw new MarshalException("error marshalling return", ex13);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return ProductInfo_Skel.operations.clone();
    }
}
