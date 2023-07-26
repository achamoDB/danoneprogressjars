// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

public final class NVPair
{
    private String name;
    private String value;
    
    public NVPair(final String name, final String value) {
        this.name = name;
        this.value = value;
    }
    
    public NVPair(final NVPair nvPair) {
        this(nvPair.name, nvPair.value);
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final String getValue() {
        return this.value;
    }
    
    public String toString() {
        return this.getClass().getName() + "[name=" + this.name + ",value=" + this.value + "]";
    }
}
