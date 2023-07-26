// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Enumeration;

final class CIHashtableEnumeration implements Enumeration
{
    Enumeration HTEnum;
    
    public CIHashtableEnumeration(final Enumeration htEnum) {
        this.HTEnum = htEnum;
    }
    
    public boolean hasMoreElements() {
        return this.HTEnum.hasMoreElements();
    }
    
    public Object nextElement() {
        final CIString nextElement = this.HTEnum.nextElement();
        if (nextElement instanceof CIString) {
            return nextElement.getString();
        }
        return nextElement;
    }
}
