// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class VersionErrorException extends FGLErrorException
{
    private int serverVersion;
    
    VersionErrorException(final int serverVersion) {
        this.serverVersion = serverVersion;
    }
    
    int getServerVersion() {
        return this.serverVersion;
    }
}
