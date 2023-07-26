// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

public class RMIPortInUse extends RegistryManagerException
{
    public RMIPortInUse(final int value) {
        super("Port \"%i<port number>\" in use.", new Object[] { new Integer(value) });
    }
    
    public int getPortNumber() {
        return (int)this.getArgument(0);
    }
}
