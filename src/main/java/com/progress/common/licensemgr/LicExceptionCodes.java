// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

interface LicExceptionCodes
{
    public static final String lmMSG001 = "Unable to contact the license manager, failed to open progress.cfg.";
    public static final String lmMSG002 = "No product exists the Product number: %i<pid> in the license file.";
    public static final String lmMSG003 = "Not licensed to run this product.";
    public static final String lmMSG004 = "Exceeded Maximum user count %i<maxusers> for this product.";
    public static final String lmMSG005 = "Invalid user count specified to release: %i<numRelease>, %i<currRes> currently reserved.";
    public static final String lmMSG006 = "Product time limit has expired. Not licensed to run this product.";
    public static final String lmMSG007 = "License manager error, failed to access license information";
    public static final int WSPROCFG_WEBSPEED_NOT_SUPPORTED = -8;
    public static final int WSPROCFG_INVALID_PARAMETER = -9;
    public static final int WSPROCFG_UNKNOWN_RETVAL = -10;
    public static final int WSPROCFG_INVALID_HDL = -11;
    public static final int WSPROCFG_PRODUCT_EXPIRED = -12;
}
