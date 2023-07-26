// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.common.licensemgr.LicenseMgr;
import java.util.Vector;

public interface IAdminLicenseCheck
{
    boolean adminLicenseCheck(final String p0, final Vector p1) throws LicenseMgr.NotLicensed, LicenseMgr.CannotContactLicenseMgr, LicenseMgr.NoSuchProduct, LicenseMgr.ProductExpired, LicenseMgr.LicenseError;
}
