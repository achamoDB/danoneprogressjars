// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;

public class ProtocolNotSuppException extends IOException
{
    public ProtocolNotSuppException() {
    }
    
    public ProtocolNotSuppException(final String message) {
        super(message);
    }
}
