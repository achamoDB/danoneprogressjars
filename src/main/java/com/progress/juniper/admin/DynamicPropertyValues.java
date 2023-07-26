// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.log.Excp;
import com.progress.common.licensemgr.LicenseMgr;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.licensemgr.R2Rcodes;
import com.progress.common.licensemgr.ProductIDlist;

public class DynamicPropertyValues implements ProductIDlist, R2Rcodes
{
    static ILicenseMgr license;
    
    private static ILicenseMgr getLicenseMgr() {
        if (DynamicPropertyValues.license != null) {
            return DynamicPropertyValues.license;
        }
        final JAPlugIn value = JAPlugIn.get();
        try {
            if (value != null) {
                DynamicPropertyValues.license = value.getLicenseManager();
            }
            else {
                DynamicPropertyValues.license = new LicenseMgr();
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        return DynamicPropertyValues.license;
    }
    
    private static int getLicenseUserCount(final ILicenseMgr licenseMgr, final int n, final int n2) {
        int maxUserCount = 0;
        int n3 = 0;
        try {
            if (licenseMgr.checkR2Run(n2)) {
                try {
                    maxUserCount = licenseMgr.getProductInfo(n).getMaxUserCount();
                }
                catch (LicenseMgr.NoSuchProduct noSuchProduct) {}
                if (maxUserCount == 512) {
                    n3 = -1;
                }
            }
        }
        catch (LicenseMgr.NotLicensed notLicensed) {}
        catch (RemoteException ex) {}
        return n3;
    }
    
    public static Object getMaxUsersDefault(final String s) {
        Integer n = new Integer(0);
        if (JATools.isServer()) {
            try {
                while (true) {
                    final ILicenseMgr licenseMgr = getLicenseMgr();
                    try {
                        if (licenseMgr.checkR2Run(9437184)) {
                            final int licenseUserCount = getLicenseUserCount(licenseMgr, 252, 9437184);
                            if (licenseUserCount <= 0) {
                                n = new Integer(20);
                                break;
                            }
                            n = new Integer((licenseUserCount < 20) ? licenseUserCount : 20);
                            break;
                        }
                    }
                    catch (LicenseMgr.NotLicensed notLicensed2) {}
                    try {
                        if (licenseMgr.checkR2Run(13631489)) {
                            final int licenseUserCount2 = getLicenseUserCount(licenseMgr, 251, 13631489);
                            if (licenseUserCount2 <= 0) {
                                n = new Integer(20);
                                break;
                            }
                            n = new Integer((licenseUserCount2 < 20) ? licenseUserCount2 : 20);
                            break;
                        }
                    }
                    catch (LicenseMgr.NotLicensed notLicensed3) {}
                    try {
                        if (licenseMgr.checkR2Run(24838144)) {
                            n = new Integer(5);
                            break;
                        }
                        continue;
                    }
                    catch (LicenseMgr.NotLicensed notLicensed) {
                        Excp.print(notLicensed);
                        n = new Integer(5);
                        break;
                    }
                }
            }
            catch (RemoteException ex) {
                Excp.print(ex);
                n = new Integer(20);
            }
        }
        return n;
    }
    
    public static Object getAsynchronousPageWritersDefault(final String s) {
        Integer n = new Integer(0);
        if (JATools.isServer()) {
            try {
                final ILicenseMgr licenseMgr = getLicenseMgr();
                try {
                    if (licenseMgr.checkR2Run(9437184)) {
                        n = new Integer(1);
                    }
                }
                catch (LicenseMgr.NotLicensed notLicensed) {
                    n = new Integer(0);
                }
            }
            catch (RemoteException ex) {
                Excp.print(ex);
                n = new Integer(1);
            }
        }
        return n;
    }
    
    public static Object getBeforeImageProcessDefault(final String s) {
        Boolean b = new Boolean(false);
        if (JATools.isServer()) {
            try {
                final ILicenseMgr licenseMgr = getLicenseMgr();
                try {
                    if (licenseMgr.checkR2Run(9437184)) {
                        b = new Boolean(true);
                    }
                }
                catch (LicenseMgr.NotLicensed notLicensed) {
                    b = new Boolean(false);
                }
            }
            catch (RemoteException ex) {
                Excp.print(ex);
                b = new Boolean(true);
            }
        }
        return b;
    }
    
    public static Object getMaxDynamicPortDefault(final String s) {
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            return new Integer(5000);
        }
        return new Integer(2000);
    }
    
    public static Object getMinDynamicPortDefault(final String s) {
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            return new Integer(3000);
        }
        return new Integer(1025);
    }
    
    static {
        DynamicPropertyValues.license = null;
    }
}
