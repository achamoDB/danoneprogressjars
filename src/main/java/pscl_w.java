// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_w
{
    public static int a(final int n) {
        int n2 = 1;
        if (n > 127) {
            ++n2;
            if (n > 255) {
                ++n2;
                if (n > 65535) {
                    ++n2;
                    if (n > 16777215) {
                        ++n2;
                    }
                }
            }
        }
        return n2;
    }
    
    public static int a(final byte[] array, int n, final int n2) {
        final int a = a(n2);
        switch (a) {
            case 5: {
                array[n] = -124;
                array[n + 1] = (byte)(n2 >>> 24 & 0xFF);
                array[n + 2] = (byte)(n2 >>> 16 & 0xFF);
                array[n + 3] = (byte)(n2 >>> 8 & 0xFF);
                array[n + 4] = (byte)(n2 & 0xFF);
                return a;
            }
            case 4: {
                array[n] = -125;
                array[n + 1] = (byte)(n2 >>> 16 & 0xFF);
                array[n + 2] = (byte)(n2 >>> 8 & 0xFF);
                array[n + 3] = (byte)(n2 & 0xFF);
                return a;
            }
            case 3: {
                array[n] = -126;
                array[n + 1] = (byte)(n2 >>> 8 & 0xFF);
                array[n + 2] = (byte)(n2 & 0xFF);
                return a;
            }
            case 2: {
                array[n] = -127;
                ++n;
                break;
            }
        }
        array[n] = (byte)(n2 & 0xFF);
        return a;
    }
    
    public static int a(final byte[] array, final int n) {
        switch (array[n] & 0xFF) {
            case 129: {
                return 2;
            }
            case 130: {
                return 3;
            }
            case 131: {
                return 4;
            }
            case 132: {
                return 5;
            }
            default: {
                return 1;
            }
        }
    }
    
    public static int b(final byte[] array, final int n) throws pscl_x {
        final int n2 = array[n] & 0xFF;
        switch (n2) {
            case 129: {
                return array[n + 1] & 0xFF;
            }
            case 130: {
                return (array[n + 1] & 0xFF) << 8 | (array[n + 2] & 0xFF);
            }
            case 131: {
                return (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF);
            }
            case 128: {
                return -1;
            }
            default: {
                return n2;
            }
        }
    }
}
