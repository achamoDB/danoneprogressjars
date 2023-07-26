// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import java.io.DataOutput;
import java.rmi.MarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import com.progress.chimera.util.SerializableEnumeration;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.server.RemoteStub;

public final class LicenseMgr_Stub extends RemoteStub implements ILicenseMgr
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8843607631225853438L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.util.SerializableEnumeration EnumerateProducts()"), new Operation("void LicenseMgrTerm()"), new Operation("boolean checkExpiration(int)"), new Operation("boolean checkR2Run(int)"), new Operation("boolean checkR2Run2(int)"), new Operation("java.lang.String getCompanyName()"), new Operation("int getProductCount()"), new Operation("com.progress.common.licensemgr.IProductInfo getProductInfo(int)"), new Operation("boolean productIsLicensed(int)") };
    }
    
    public LicenseMgr_Stub() {
    }
    
    public LicenseMgr_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public SerializableEnumeration EnumerateProducts() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 0, 8843607631225853438L);
            super.ref.invoke(call);
            SerializableEnumeration serializableEnumeration;
            try {
                serializableEnumeration = (SerializableEnumeration)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return serializableEnumeration;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public void LicenseMgrTerm() throws LicenseMgr.LicenseError, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 1, 8843607631225853438L);
            super.ref.invoke(call);
            super.ref.done(call);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (RemoteException ex2) {
            throw ex2;
        }
        catch (LicenseMgr.LicenseError licenseError) {
            throw licenseError;
        }
        catch (Exception ex3) {
            throw new UnexpectedException("undeclared checked exception", ex3);
        }
    }
    
    public boolean checkExpiration(final int n) throws LicenseMgr.LicenseError, LicenseMgr.NoSuchProduct, LicenseMgr.ProductExpired, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 2, 8843607631225853438L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (LicenseMgr.LicenseError licenseError) {
            throw licenseError;
        }
        catch (LicenseMgr.NoSuchProduct noSuchProduct) {
            throw noSuchProduct;
        }
        catch (LicenseMgr.ProductExpired productExpired) {
            throw productExpired;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public boolean checkR2Run(final int n) throws LicenseMgr.NotLicensed, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 3, 8843607631225853438L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (LicenseMgr.NotLicensed notLicensed) {
            throw notLicensed;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public boolean checkR2Run2(final int n) throws LicenseMgr.NotLicensed, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 4, 8843607631225853438L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (LicenseMgr.NotLicensed notLicensed) {
            throw notLicensed;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public String getCompanyName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 5, 8843607631225853438L);
            super.ref.invoke(call);
            String s;
            try {
                s = (String)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return s;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public int getProductCount() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 6, 8843607631225853438L);
            super.ref.invoke(call);
            int int1;
            try {
                int1 = call.getInputStream().readInt();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return int1;
        }
        catch (RuntimeException ex2) {
            throw ex2;
        }
        catch (RemoteException ex3) {
            throw ex3;
        }
        catch (Exception ex4) {
            throw new UnexpectedException("undeclared checked exception", ex4);
        }
    }
    
    public IProductInfo getProductInfo(final int n) throws LicenseMgr.NoSuchProduct, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 7, 8843607631225853438L);
            try {
                final Object outputStream = call.getOutputStream();
                ((DataOutput)outputStream).writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            Object outputStream;
            try {
                outputStream = call.getInputStream().readObject();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            catch (ClassNotFoundException ex3) {
                throw new UnmarshalException("error unmarshalling return", ex3);
            }
            finally {
                super.ref.done(call);
            }
            return (IProductInfo)outputStream;
        }
        catch (RuntimeException ex4) {
            throw ex4;
        }
        catch (RemoteException ex5) {
            throw ex5;
        }
        catch (LicenseMgr.NoSuchProduct noSuchProduct) {
            throw noSuchProduct;
        }
        catch (Exception ex6) {
            throw new UnexpectedException("undeclared checked exception", ex6);
        }
    }
    
    public boolean productIsLicensed(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LicenseMgr_Stub.operations, 8, 8843607631225853438L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
}
