// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class T2
{
    static IT1 other;
    
    public static void main(final String[] array) {
        System.setSecurityManager(new RMISecurityManager());
        try {
            T2.other = (IT1)Naming.lookup("T1");
        }
        catch (Exception obj) {
            System.out.println("Can't find T1: " + obj);
            System.exit(0);
        }
        System.out.println("Got T1");
        try {
            T2.other.testSelf(T2.other);
        }
        catch (Throwable obj2) {
            System.out.println("Exception calling testSelf: " + obj2);
        }
    }
    
    static {
        T2.other = null;
    }
}
