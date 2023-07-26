// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.util.Vector;

class JAConfigsCategory extends JAAbstractCategory
{
    public JAConfigsCategory(final JADatabase jaDatabase, final String s, final Vector vector) throws RemoteException {
        super(jaDatabase, s, vector);
    }
    
    public String getMMCClientClass() {
        return "com.progress.vj.juniper.JuniperMMCConfigsCategoryClient";
    }
}
