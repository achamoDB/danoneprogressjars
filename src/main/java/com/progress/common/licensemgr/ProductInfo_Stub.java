// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public final class ProductInfo_Stub extends RemoteStub implements IProductInfo, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1393871651578745254L;
    
    static {
        operations = new Operation[] { new Operation("int ReleaseUsers(int)"), new Operation("int ReserveUsers(int)"), new Operation("boolean checkR2Run(int)"), new Operation("java.lang.String getControlNumber()"), new Operation("int getCurrentUserCount()"), new Operation("int getMaxUserCount()"), new Operation("java.lang.String getProductDescription()"), new Operation("int getProductID()"), new Operation("java.lang.String getProductVersion()"), new Operation("java.lang.String getSerialNumber()") };
    }
    
    public ProductInfo_Stub() {
    }
    
    public ProductInfo_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public int ReleaseUsers(final int n) throws LicenseMgr.InvalidUserCount, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 0, 1393871651578745254L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            int int1;
            try {
                int1 = call.getInputStream().readInt();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return int1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (LicenseMgr.InvalidUserCount invalidUserCount) {
            throw invalidUserCount;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public int ReserveUsers(final int n) throws LicenseMgr.ExceededMaxUsers, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 1, 1393871651578745254L);
            try {
                call.getOutputStream().writeInt(n);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            int int1;
            try {
                int1 = call.getInputStream().readInt();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return int1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (LicenseMgr.ExceededMaxUsers exceededMaxUsers) {
            throw exceededMaxUsers;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public boolean checkR2Run(final int n) throws LicenseMgr.NotLicensed, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 2, 1393871651578745254L);
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
    
    public String getControlNumber() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 3, 1393871651578745254L);
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
    
    public int getCurrentUserCount() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 4, 1393871651578745254L);
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
    
    public int getMaxUserCount() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 5, 1393871651578745254L);
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
    
    public String getProductDescription() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 6, 1393871651578745254L);
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
    
    public int getProductID() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 7, 1393871651578745254L);
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
    
    public String getProductVersion() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 8, 1393871651578745254L);
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
    
    public String getSerialNumber() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ProductInfo_Stub.operations, 9, 1393871651578745254L);
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
}
