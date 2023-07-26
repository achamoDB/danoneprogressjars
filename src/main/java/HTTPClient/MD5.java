// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

class MD5
{
    private static final char[] hex;
    
    public static final String toHex(final byte[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            sb.append(MD5.hex[array[i] >> 4 & 0xF]).append(MD5.hex[array[i] & 0xF]);
        }
        return sb.toString();
    }
    
    public static final byte[] digest(final byte[] input) {
        try {
            return MessageDigest.getInstance("MD5").digest(input);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public static final byte[] digest(final byte[] input, final byte[] input2) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(input);
            return instance.digest(input2);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public static final String hexDigest(final byte[] array) {
        return toHex(digest(array));
    }
    
    public static final String hexDigest(final byte[] array, final byte[] array2) {
        return toHex(digest(array, array2));
    }
    
    public static final byte[] digest(final String s) {
        try {
            return digest(s.getBytes("8859_1"));
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public static final String hexDigest(final String s) {
        try {
            return toHex(digest(s.getBytes("8859_1")));
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex.toString());
        }
    }
    
    static {
        hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
}
