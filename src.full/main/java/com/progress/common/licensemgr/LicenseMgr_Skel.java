// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import com.progress.chimera.util.SerializableEnumeration;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class LicenseMgr_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8843607631225853438L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.util.SerializableEnumeration EnumerateProducts()"), new Operation("void LicenseMgrTerm()"), new Operation("boolean checkExpiration(int)"), new Operation("boolean checkR2Run(int)"), new Operation("boolean checkR2Run2(int)"), new Operation("java.lang.String getCompanyName()"), new Operation("int getProductCount()"), new Operation("com.progress.common.licensemgr.IProductInfo getProductInfo(int)"), new Operation("boolean productIsLicensed(int)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 8843607631225853438L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final LicenseMgr licenseMgr = (LicenseMgr)remote;
    Label_0604_Outer:
        while (true) {
            while (true) {
                switch (n) {
                    case 0: {
                        remoteCall.releaseInputStream();
                        final SerializableEnumeration enumerateProducts = licenseMgr.EnumerateProducts();
                        try {
                            remoteCall.getResultStream(true).writeObject(enumerateProducts);
                            return;
                        }
                        catch (IOException ex) {
                            throw new MarshalException("error marshalling return", ex);
                        }
                    }
                    case 2: {
                        continue Label_0604_Outer;
                    }
                    case 1: {
                        remoteCall.releaseInputStream();
                        licenseMgr.LicenseMgrTerm();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex2) {
                            throw new MarshalException("error marshalling return", ex2);
                        }
                        try {
                            final int int1 = remoteCall.getInputStream().readInt();
                        }
                        catch (IOException ex3) {
                            throw new UnmarshalException("error unmarshalling arguments", ex3);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    case 3: {
                        Label_0276: {
                            break Label_0276;
                            final int int1;
                            final boolean checkExpiration = licenseMgr.checkExpiration(int1);
                            try {
                                remoteCall.getResultStream(true).writeBoolean(checkExpiration);
                                return;
                            }
                            catch (IOException ex4) {
                                throw new MarshalException("error marshalling return", ex4);
                            }
                            try {
                                final int int2 = remoteCall.getInputStream().readInt();
                            }
                            catch (IOException ex5) {
                                throw new UnmarshalException("error unmarshalling arguments", ex5);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                    }
                    case 4: {
                        int int3 = 0;
                        Label_0392: {
                            break Label_0392;
                            final int int2;
                            final boolean checkR2Run = licenseMgr.checkR2Run(int2);
                            try {
                                remoteCall.getResultStream(true).writeBoolean(checkR2Run);
                                return;
                            }
                            catch (IOException ex6) {
                                throw new MarshalException("error marshalling return", ex6);
                            }
                            try {
                                int3 = remoteCall.getInputStream().readInt();
                            }
                            catch (IOException ex7) {
                                throw new UnmarshalException("error unmarshalling arguments", ex7);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        final boolean checkR2Run2 = licenseMgr.checkR2Run2(int3);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(checkR2Run2);
                            return;
                        }
                        catch (IOException ex8) {
                            throw new MarshalException("error marshalling return", ex8);
                        }
                    }
                    case 5: {
                        remoteCall.releaseInputStream();
                        final String companyName = licenseMgr.getCompanyName();
                        try {
                            remoteCall.getResultStream(true).writeObject(companyName);
                            return;
                        }
                        catch (IOException ex9) {
                            throw new MarshalException("error marshalling return", ex9);
                        }
                    }
                    case 7: {
                        continue;
                    }
                    case 6: {
                        remoteCall.releaseInputStream();
                        final int productCount = licenseMgr.getProductCount();
                        try {
                            remoteCall.getResultStream(true).writeInt(productCount);
                            return;
                        }
                        catch (IOException ex10) {
                            throw new MarshalException("error marshalling return", ex10);
                        }
                        try {
                            final int int4 = remoteCall.getInputStream().readInt();
                        }
                        catch (IOException ex11) {
                            throw new UnmarshalException("error unmarshalling arguments", ex11);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    case 8: {
                        int int5 = 0;
                        Label_0720: {
                            break Label_0720;
                            final int int4;
                            final IProductInfo productInfo = licenseMgr.getProductInfo(int4);
                            try {
                                remoteCall.getResultStream(true).writeObject(productInfo);
                                return;
                            }
                            catch (IOException ex12) {
                                throw new MarshalException("error marshalling return", ex12);
                            }
                            try {
                                int5 = remoteCall.getInputStream().readInt();
                            }
                            catch (IOException ex13) {
                                throw new UnmarshalException("error unmarshalling arguments", ex13);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        final boolean productIsLicensed = licenseMgr.productIsLicensed(int5);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(productIsLicensed);
                            return;
                        }
                        catch (IOException ex14) {
                            throw new MarshalException("error marshalling return", ex14);
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
        return LicenseMgr_Skel.operations.clone();
    }
}
