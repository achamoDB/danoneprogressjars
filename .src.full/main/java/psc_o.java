// 
// Decompiled by Procyon v0.5.36
// 

public class psc_o
{
    public static int a(int n) {
        int n2 = 1;
        while (true) {
            n >>>= 8;
            if (n == 0) {
                break;
            }
            ++n2;
        }
        return n2;
    }
    
    public static int b(final int n) throws psc_m {
        if (n < 0) {
            throw new psc_m("ASN1Lengths.getLengthLen: dataLen should not be negative.");
        }
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
    
    public static int a(final byte[] array, final int n, int n2) throws psc_m {
        if (array == null) {
            throw new psc_m("ASN1Lengths.writeTag: encoding should not be null.");
        }
        if (n < 0 || n >= array.length) {
            throw new psc_m("ASN1Lengths.writeTag: offset is out of range.");
        }
        final int a = a(n2);
        if (n + a > array.length) {
            throw new psc_m("ASN1Lengths.writeTag: tag falls off of encoding array.");
        }
        for (int i = n + a - 1; i >= n; --i) {
            array[i] = (byte)(n2 & 0xFF);
            n2 >>>= 8;
        }
        return a;
    }
    
    public static int b(final byte[] array, int n, int n2) throws psc_m {
        if (array == null) {
            throw new psc_m("ASN1Lengths.writeLength: encoding should not be null.");
        }
        if (n < 0 || n >= array.length) {
            throw new psc_m("ASN1Lengths.writeLength: offset is out of range.");
        }
        int b = b(n2);
        if (n + b > array.length) {
            throw new psc_m("ASN1Lengths.writeLength: length falls off of encoding array.");
        }
        final int n3;
        if ((n3 = b) > 1) {
            array[n] = (byte)(0x80 | b - 1);
            ++n;
            --b;
        }
        for (int i = n + b - 1; i >= n; --i) {
            array[i] = (byte)(n2 & 0xFF);
            n2 >>>= 8;
        }
        return n3;
    }
    
    public static int a(final byte[] array, final int n) throws psc_m {
        if (array == null) {
            throw new psc_m("ASN1Lengths.determineLengthLen: encoding should not be null.");
        }
        if (n < 0 || n >= array.length) {
            throw new psc_m("ASN1Lengths.determineLengthLen: offset is out of range.");
        }
        final int n2 = array[n] & 0xFF;
        if (n2 <= 128) {
            return 1;
        }
        final int n3 = n2 & 0x7F;
        if (n3 > 4) {
            throw new psc_m("ASN1Lengths.determineLengthLen: length greater than 0x7FFF,FFFF.");
        }
        return n3 + 1;
    }
    
    public static int b(final byte[] array, final int n) throws psc_m {
        final int a = a(array, n);
        final int n2 = array[n] & 0xFF;
        int n3 = 0;
        if ((n2 & 0x80) == 0x0) {
            n3 = n2;
        }
        else {
            if (n + a > array.length) {
                throw new psc_m("ASN1Lengths.determineLength: not enough room in encoding.");
            }
            switch (a) {
                case 1: {
                    n3 = -1;
                    break;
                }
                case 2: {
                    n3 = (array[n + 1] & 0xFF);
                    break;
                }
                case 3: {
                    n3 = ((array[n + 1] & 0xFF) << 8 | (array[n + 2] & 0xFF));
                    break;
                }
                case 4: {
                    n3 = ((array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF));
                    break;
                }
                case 5: {
                    n3 = ((array[n + 1] & 0xFF) << 24 | (array[n + 2] & 0xFF) << 16 | (array[n + 3] & 0xFF) << 8 | (array[n + 4] & 0xFF));
                    if (n3 < 0) {
                        throw new psc_m("ASN1Lengths.determineLength: length greater than 0x7FFF,FFFF.");
                    }
                    break;
                }
            }
        }
        return n3;
    }
}
