// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.rmi.RemoteException;
import com.progress.common.licensemgr.ProductInfo;
import com.progress.common.licensemgr.LicenseMgr;

public class getServerCnt implements ubConstants
{
    private static final int END_OF_LIST = 0;
    private static final int ARBITRARY_LARGE_VALUE = 4096;
    private boolean hasDevLicense;
    private boolean hasNonDevLicense;
    
    public getServerCnt() {
        this.hasDevLicense = false;
        this.hasNonDevLicense = false;
    }
    
    public int getMaxServers(final int n, final int n2) throws LicenseMgr.CannotContactLicenseMgr, LicenseMgr.NotLicensed {
        final int[] array = new int[12];
        final LicenseMgr licenseMgr = new LicenseMgr();
        boolean b;
        if (n2 == 0) {
            try {
                b = licenseMgr.checkR2Run(25165824);
            }
            catch (LicenseMgr.NotLicensed notLicensed) {
                b = false;
            }
            array[0] = 304;
            array[1] = 305;
            array[2] = 299;
            array[3] = 243;
            array[4] = 274;
            array[5] = 340;
            array[6] = 111;
            array[7] = 113;
            array[8] = 114;
            array[9] = 109;
            array[10] = 0;
        }
        else {
            if (n2 != 1) {
                return n;
            }
            try {
                b = licenseMgr.checkR2Run(4325376);
            }
            catch (LicenseMgr.NotLicensed notLicensed2) {
                b = false;
            }
            array[0] = 306;
            array[1] = 305;
            array[2] = 270;
            array[3] = 283;
            array[4] = 299;
            array[5] = 268;
            array[6] = 111;
            array[7] = 113;
            array[8] = 114;
            array[9] = 109;
            array[10] = 0;
        }
        if (!b) {
            throw new LicenseMgr.NotLicensed();
        }
        int n3 = 0;
        for (int n4 = 0; array[n4] != 0; ++n4) {
            try {
                final int n5 = array[n4];
                int maxUserCount = new ProductInfo(n5, licenseMgr).getMaxUserCount();
                if (maxUserCount == -1) {
                    maxUserCount = 4096;
                }
                if (n5 == 306 || n5 == 304 || n5 == 305 || n5 == 299 || n5 == 243 || n5 == 268 || n5 == 114 || n5 == 109) {
                    this.hasDevLicense = true;
                    maxUserCount = 2;
                }
                else {
                    this.hasNonDevLicense = true;
                }
                if (maxUserCount > n3) {
                    n3 = maxUserCount;
                }
            }
            catch (LicenseMgr.NoSuchProduct noSuchProduct) {}
            catch (RemoteException ex) {}
        }
        if (n3 == 0) {
            throw new LicenseMgr.NotLicensed();
        }
        return n3;
    }
    
    public boolean hasDevLicenseProduct() {
        return this.hasDevLicense;
    }
    
    public boolean hasNonDevLicenseProduct() {
        return this.hasNonDevLicense;
    }
}
