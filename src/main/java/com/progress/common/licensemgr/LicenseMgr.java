// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import com.progress.common.util.Format;
import java.util.StringTokenizer;
import com.progress.common.exception.ProException;
import com.progress.common.util.InstallPath;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;

public class LicenseMgr implements ProductIDlist, R2Rcodes, LicExceptionCodes, ILicenseMgr
{
    String licenseFileName;
    private static final boolean DEBUG_TRACE = false;
    protected LicenseMgrJNI procfg;
    private String companyName;
    private Vector pidVector;
    private Vector prodVector;
    private Hashtable prodListTable;
    private int jniHandle;
    private int[] ProductIdTable;
    
    public LicenseMgr() throws CannotContactLicenseMgr {
        this(null, null);
    }
    
    public LicenseMgr(final String s) throws CannotContactLicenseMgr {
        this(s, null);
    }
    
    public LicenseMgr(final String s, final int[] productIdTable) throws CannotContactLicenseMgr {
        this.licenseFileName = null;
        this.procfg = null;
        this.pidVector = new Vector();
        this.prodVector = new Vector();
        this.prodListTable = new Hashtable();
        this.jniHandle = -1;
        this.ProductIdTable = new int[] { 10, 21, 22, 102, 104, 105, 106, 107, 108, 109, 111, 113, 114, 115, 129, 217, 219, 221, 225, 232, 238, 243, 244, 246, 248, 250, 251, 252, 256, 268, 270, 274, 283, 284, 299, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 334, 335, 336, 337, 338, 339, 340, 341, 343 };
        try {
            UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException previous) {
            final CannotContactLicenseMgr cannotContactLicenseMgr = new CannotContactLicenseMgr();
            cannotContactLicenseMgr.setPrevious(previous);
            throw cannotContactLicenseMgr;
        }
        if (productIdTable != null) {
            this.setProductIdTable(productIdTable);
        }
        this.licenseOpen(s);
        this.populateProductList();
    }
    
    public String getCompanyName() {
        if (this.companyName == null) {
            this.companyName = this.procfg.getCompanyNameJNI();
        }
        return this.companyName;
    }
    
    public boolean checkR2Run(final int n) throws NotLicensed {
        final int n2 = n & 0xFFFF;
        final int n3 = n >> 16;
        int n4;
        if (this.jniHandle == -1) {
            n4 = this.procfg.checkR2RunJNI(n3, n2);
        }
        else {
            n4 = this.procfg.checkR2Run2JNI(this.jniHandle, n3, n2);
        }
        if (n4 == 0) {
            return true;
        }
        throw new NotLicensed();
    }
    
    public String getLicenseFileName() {
        return this.licenseFileName;
    }
    
    public boolean checkR2Run2(final int n) throws NotLicensed {
        return this.checkR2Run(n);
    }
    
    public boolean checkExpiration(final int n) throws ProductExpired, NoSuchProduct, LicenseError {
        final int checkExpiration2JNI = this.procfg.checkExpiration2JNI(this.jniHandle, n);
        if (checkExpiration2JNI == 0) {
            return true;
        }
        switch (checkExpiration2JNI) {
            case -8: {
                throw new NoSuchProduct(n);
            }
            case -12: {
                throw new ProductExpired();
            }
            default: {
                throw new LicenseError();
            }
        }
    }
    
    public void LicenseMgrTerm() throws LicenseError {
        if (this.procfg != null && this.jniHandle >= 0) {
            this.procfg.procfgTerm2JNI(this.jniHandle);
            return;
        }
        throw new LicenseError();
    }
    
    public Enumeration EnumProductIDs() {
        if (this.prodVector.size() == 0) {
            this.populateProductList();
        }
        return new SerializableEnumeration(this.prodVector);
    }
    
    public SerializableEnumeration EnumerateProducts() {
        if (this.pidVector.size() == 0) {
            this.populateProductList();
        }
        return new SerializableEnumeration(this.pidVector);
    }
    
    public SerializableEnumeration getLicensedProducts() {
        if (this.prodVector.size() == 0) {
            this.populateProductList();
        }
        return new SerializableEnumeration(this.prodVector);
    }
    
    public IProductInfo getProductInfo(final int n) throws NoSuchProduct, RemoteException {
        return new ProductInfo(n, this);
    }
    
    public ProductList getProductList(final int value) {
        ProductList list = this.prodListTable.get(new Integer(value));
        if (list == null) {
            String s;
            if (this.jniHandle == -1) {
                s = this.procfg.getProductInfoJNI(value);
            }
            else {
                s = this.procfg.getProductInfo2JNI(this.jniHandle, value);
            }
            if (s != null) {
                list = new ProductList(s);
                final Integer n = new Integer(list.productID());
                this.prodVector.addElement(list);
                this.pidVector.addElement(n);
                this.prodListTable.put(n, list);
            }
        }
        return list;
    }
    
    public int getProductCount() {
        return this.prodVector.size();
    }
    
    private void populateProductList() {
        for (int i = 0; i < this.ProductIdTable.length; ++i) {
            this.getProductList(this.ProductIdTable[i]);
        }
    }
    
    private void licenseOpen() throws CannotContactLicenseMgr {
        final int n = 1;
        final int n2 = 1;
        if (this.procfg == null) {
            this.procfg = new LicenseMgrJNI();
        }
        if (this.procfg.wsprocfgVersionCheckJNI(n, n2) != 0) {
            throw new CannotContactLicenseMgr();
        }
        if (this.procfg.wsprocfgDllInitJNI(new InstallPath().getInstallPath()) == 0) {
            return;
        }
        throw new CannotContactLicenseMgr();
    }
    
    private void licenseOpen(final String licenseFileName) throws CannotContactLicenseMgr {
        this.licenseOpen();
        if (licenseFileName != null) {
            this.licenseFileName = licenseFileName;
            final int procfgInit2JNI = this.procfg.procfgInit2JNI(licenseFileName);
            if (procfgInit2JNI < 0) {
                throw new CannotContactLicenseMgr();
            }
            this.jniHandle = procfgInit2JNI;
        }
    }
    
    public int getJNIContextHandle() {
        return this.jniHandle;
    }
    
    private void setProductIdTable(final int[] productIdTable) {
        this.ProductIdTable = productIdTable;
    }
    
    public int[] getProductIdTable() {
        return this.ProductIdTable;
    }
    
    public boolean productIsLicensed(final int n) throws RemoteException {
        boolean b = false;
        if (this.getProductList(n) == null) {
            b = true;
        }
        return b;
    }
    
    public static class CannotContactLicenseMgr extends ProException
    {
        public CannotContactLicenseMgr() {
            super("Unable to contact the license manager, failed to open progress.cfg.", (Object[])null);
        }
    }
    
    public static class NoSuchProduct extends ProException
    {
        public NoSuchProduct(final int value) {
            super("No product exists the Product number: %i<pid> in the license file.", new Object[] { new Integer(value) });
        }
    }
    
    public static class NotLicensed extends ProException
    {
        public NotLicensed() {
            super("Not licensed to run this product.", (Object[])null);
        }
    }
    
    public static class ExceededMaxUsers extends ProException
    {
        public ExceededMaxUsers(final int value) {
            super("Exceeded Maximum user count %i<maxusers> for this product.", new Object[] { new Integer(value) });
        }
    }
    
    public static class InvalidUserCount extends ProException
    {
        public InvalidUserCount(final int value, final int value2) {
            super("Invalid user count specified to release: %i<numRelease>, %i<currRes> currently reserved.", new Object[] { new Integer(value), new Integer(value2) });
        }
    }
    
    public static class ProductExpired extends ProException
    {
        public ProductExpired() {
            super("Product time limit has expired. Not licensed to run this product.", (Object[])null);
        }
    }
    
    public static class LicenseError extends ProException
    {
        public LicenseError() {
            super("License manager error, failed to access license information", (Object[])null);
        }
    }
    
    public class ProductList
    {
        private int ProductID;
        private int MaxProductUsers;
        private String ProductDescription;
        private String ProductVersion;
        private String ProductSerialNum;
        private String ProductControlNum;
        
        public ProductList(final int productID, final int maxProductUsers, final String productDescription, final String productVersion, final String productSerialNum, final String productControlNum) {
            this.ProductID = productID;
            this.MaxProductUsers = maxProductUsers;
            this.ProductDescription = productDescription;
            this.ProductVersion = productVersion;
            this.ProductSerialNum = productSerialNum;
            this.ProductControlNum = productControlNum;
        }
        
        public ProductList(final String str) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, "+");
            this.ProductID = Format.atoi(stringTokenizer.nextToken());
            this.MaxProductUsers = Format.atoi(stringTokenizer.nextToken());
            this.ProductDescription = stringTokenizer.nextToken();
            this.ProductVersion = stringTokenizer.nextToken();
            this.ProductSerialNum = stringTokenizer.nextToken();
            this.ProductControlNum = stringTokenizer.nextToken();
        }
        
        public String toString() {
            return this.ProductDescription + ": " + this.ProductID + ": " + this.ProductVersion;
        }
        
        public int productID() {
            return this.ProductID;
        }
        
        public int maxProductUsers() {
            return this.MaxProductUsers;
        }
        
        public String productDescription() {
            return this.ProductDescription;
        }
        
        public String productVersion() {
            return this.ProductVersion;
        }
        
        public String productSerialNum() {
            return this.ProductSerialNum;
        }
        
        public String productControlNum() {
            return this.ProductControlNum;
        }
    }
}
