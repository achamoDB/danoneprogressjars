// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.Remote;
import com.progress.common.util.IMessageCallback;
import java.rmi.RMISecurityManager;
import com.progress.common.rmiregistry.RegistryManager;

public class StandAloneBroker
{
    static MessageHandler messHan;
    static RegistryManager regMan;
    
    public static void main(final String[] array) {
        try {
            System.setSecurityManager(new RMISecurityManager());
        }
        catch (Throwable t) {
            System.out.println("Can't start broker because: " + t.getMessage());
            System.exit(0);
        }
        String str = null;
        try {
            final EventBroker eventBroker = new EventBroker(StandAloneBroker.messHan);
            str = "EventBroker";
            if (array.length > 0) {
                str = array[0];
            }
            StandAloneBroker.regMan.register(str, eventBroker);
        }
        catch (Throwable t2) {
            System.out.println("Can't start broker " + str + " because: " + t2.getMessage());
            System.exit(0);
        }
        System.out.println("Broker Ready.");
    }
    
    static {
        StandAloneBroker.messHan = new MessageHandler();
        StandAloneBroker.regMan = new RegistryManager(StandAloneBroker.messHan);
    }
}
