// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

final class CIString
{
    private String string;
    private int hash;
    private static final char[] lc;
    
    public CIString(final String string) {
        this.string = string;
        this.hash = calcHashCode(string);
    }
    
    public final String getString() {
        return this.string;
    }
    
    public int hashCode() {
        return this.hash;
    }
    
    private static final int calcHashCode(final String s) {
        int n = 0;
        final char[] lc = CIString.lc;
        for (int length = s.length(), i = 0; i < length; ++i) {
            n = 31 * n + lc[s.charAt(i)];
        }
        return n;
    }
    
    public boolean equals(final Object o) {
        if (o != null) {
            if (o instanceof CIString) {
                return this.string.equalsIgnoreCase(((CIString)o).string);
            }
            if (o instanceof String) {
                return this.string.equalsIgnoreCase((String)o);
            }
        }
        return false;
    }
    
    public String toString() {
        return this.string;
    }
    
    static {
        lc = new char[256];
        for (char ch = '\0'; ch < '\u0100'; ++ch) {
            CIString.lc[ch] = Character.toLowerCase(ch);
        }
    }
}
