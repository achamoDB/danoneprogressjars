// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IProductInfo extends Remote
{
    boolean checkR2Run(final int p0) throws LicenseMgr.NotLicensed, RemoteException;
    
    int ReserveUsers(final int p0) throws LicenseMgr.ExceededMaxUsers, RemoteException;
    
    int ReleaseUsers(final int p0) throws LicenseMgr.InvalidUserCount, RemoteException;
    
    int getCurrentUserCount() throws RemoteException;
    
    String getProductDescription() throws RemoteException;
    
    String getProductVersion() throws RemoteException;
    
    int getProductID() throws RemoteException;
    
    String getSerialNumber() throws RemoteException;
    
    String getControlNumber() throws RemoteException;
    
    int getMaxUserCount() throws RemoteException;
}
