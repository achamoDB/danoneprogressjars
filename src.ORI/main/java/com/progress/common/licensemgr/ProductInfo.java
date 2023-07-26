// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProductInfo extends UnicastRemoteObject implements IProductInfo
{
    LicenseMgr licenseMgr;
    LicenseMgr.ProductList pList;
    private int reservedUsers;
    
    public ProductInfo(final int n, final LicenseMgr licenseMgr) throws LicenseMgr.NoSuchProduct, RemoteException {
        this.licenseMgr = null;
        this.pList = null;
        this.reservedUsers = 0;
        this.licenseMgr = licenseMgr;
        this.pList = this.licenseMgr.getProductList(n);
        if (this.pList == null) {
            throw new LicenseMgr.NoSuchProduct(n);
        }
    }
    
    public boolean checkR2Run() throws LicenseMgr.NotLicensed {
        return this.licenseMgr.checkR2Run(this.pList.productID());
    }
    
    public boolean checkR2Run(final int n) throws LicenseMgr.NotLicensed {
        return this.licenseMgr.checkR2Run(n);
    }
    
    public int ReserveUsers(final int n) throws LicenseMgr.ExceededMaxUsers {
        if (this.reservedUsers + n < this.pList.maxProductUsers()) {
            this.reservedUsers += n;
            return n;
        }
        throw new LicenseMgr.ExceededMaxUsers(this.pList.maxProductUsers());
    }
    
    public int ReleaseUsers(final int n) throws LicenseMgr.InvalidUserCount {
        if (this.reservedUsers - n < 0) {
            throw new LicenseMgr.InvalidUserCount(this.reservedUsers, n);
        }
        this.reservedUsers -= n;
        return n;
    }
    
    public int getCurrentUserCount() {
        return this.reservedUsers;
    }
    
    public String getProductDescription() {
        return this.pList.productDescription();
    }
    
    public String getProductVersion() {
        return this.pList.productVersion();
    }
    
    public int getProductID() {
        return this.pList.productID();
    }
    
    public String getSerialNumber() {
        return this.pList.productSerialNum();
    }
    
    public String getControlNumber() {
        return this.pList.productControlNum();
    }
    
    public int getMaxUserCount() {
        return this.pList.maxProductUsers();
    }
}
