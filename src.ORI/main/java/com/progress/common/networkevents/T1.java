// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public class T1 implements IT1
{
    static IT1 self;
    static RemoteStub stub;
    
    RemoteStub export(final Remote obj) {
        try {
            T1.stub = UnicastRemoteObject.exportObject(obj);
            System.out.println("Stub = " + T1.stub);
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return T1.stub;
    }
    
    T1() {
        this.export(this);
    }
    
    public static void main(final String[] array) {
        System.setSecurityManager(new RMISecurityManager());
        T1.self = new T1();
        try {
            Naming.rebind("T1", T1.self);
        }
        catch (Throwable obj) {
            System.out.println("Can't bind " + obj);
            System.exit(0);
        }
        try {
            Thread.sleep(1000000L);
        }
        catch (Throwable t) {}
    }
    
    boolean compare(final Object o, final Object o2) {
        if (o instanceof RemoteStub || o2 instanceof RemoteStub) {
            return this.compareRemote(o, o2);
        }
        return o == o2;
    }
    
    Object toStub(final Object obj) {
        if (!(obj instanceof RemoteStub)) {
            System.out.println("Converting to stub: " + obj);
            return this.export((Remote)obj);
        }
        return obj;
    }
    
    boolean compareRemote(Object stub, Object stub2) {
        stub = this.toStub(stub);
        stub2 = this.toStub(stub2);
        return stub.equals(stub2);
    }
    
    public void testSelf(final Remote remote) {
        System.out.println("REMOTE " + remote + " " + remote.hashCode());
        System.out.println("LOCAL  " + this + " " + this.hashCode());
        System.out.println("SELF  " + T1.self + " " + T1.self.hashCode());
        if (remote == T1.stub) {
            System.out.println("aaa");
        }
        if (remote.equals(T1.stub)) {
            System.out.println("bbb");
        }
        if (T1.stub.equals(remote)) {
            System.out.println("ccc");
        }
        if (remote == this) {
            System.out.println("111");
        }
        if (remote.equals(this)) {
            System.out.println("222");
        }
        if (this.equals(remote)) {
            System.out.println("333");
        }
        if (remote == T1.self) {
            System.out.println("444");
        }
        if (remote.equals(T1.self)) {
            System.out.println("555");
        }
        if (T1.self.equals(remote)) {
            System.out.println("666");
        }
        if (this.compare(T1.self, remote)) {
            System.out.println("777");
        }
        System.out.println("Done");
    }
    
    static {
        T1.self = null;
        T1.stub = null;
    }
}
