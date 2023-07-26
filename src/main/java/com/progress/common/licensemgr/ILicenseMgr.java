// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import com.progress.chimera.util.SerializableEnumeration;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface ILicenseMgr extends Remote
{
    String getCompanyName() throws RemoteException;
    
    boolean checkR2Run(final int p0) throws LicenseMgr.NotLicensed, RemoteException;
    
    boolean productIsLicensed(final int p0) throws RemoteException;
    
    int getProductCount() throws RemoteException;
    
    SerializableEnumeration EnumerateProducts() throws RemoteException;
    
    IProductInfo getProductInfo(final int p0) throws LicenseMgr.NoSuchProduct, RemoteException;
    
    boolean checkR2Run2(final int p0) throws LicenseMgr.NotLicensed, RemoteException;
    
    boolean checkExpiration(final int p0) throws LicenseMgr.ProductExpired, LicenseMgr.NoSuchProduct, LicenseMgr.LicenseError, RemoteException;
    
    void LicenseMgrTerm() throws LicenseMgr.LicenseError, RemoteException;
}
