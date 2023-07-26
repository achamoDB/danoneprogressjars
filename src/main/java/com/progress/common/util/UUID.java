// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.rmi.dgc.VMID;

public final class UUID
{
    private VMID v;
    
    public UUID() {
        this.v = new VMID();
    }
    
    public boolean equals(final Object obj) {
        return this.v.equals(obj);
    }
    
    public int hashCode() {
        return this.v.hashCode();
    }
    
    public String toString() {
        return this.v.toString();
    }
    
    public static void main(final String[] array) {
        System.out.println(new UUID());
    }
}
