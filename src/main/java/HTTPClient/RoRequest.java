// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

public interface RoRequest
{
    HTTPConnection getConnection();
    
    String getMethod();
    
    String getRequestURI();
    
    NVPair[] getHeaders();
    
    byte[] getData();
    
    HttpOutputStream getStream();
    
    boolean allowUI();
}
