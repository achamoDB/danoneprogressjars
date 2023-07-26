import java.math.BigInteger;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a9
{
    public pscl_e a;
    public String b;
    public int c;
    public BigInteger d;
    public BigInteger e;
    public BigInteger f;
    
    public pscl_a9() {
        this.c = -1;
    }
    
    public String a() {
        return "RSA";
    }
    
    public int b() {
        return this.c;
    }
    
    public int c() {
        return this.c;
    }
    
    public int d() {
        return this.c;
    }
    
    public int e() {
        return this.c;
    }
    
    public int f() {
        return this.c;
    }
    
    public int g() {
        return this.c;
    }
    
    public boolean a(final byte[] array, final int n) {
        BigInteger bigInteger = null;
        try {
            final byte[] val = new byte[array.length - n];
            System.arraycopy(array, n, val, 0, val.length);
            bigInteger = new BigInteger(val);
            return bigInteger.compareTo(this.e) < 0;
        }
        catch (Exception ex) {
            return false;
        }
        finally {
            if (bigInteger != null) {}
        }
    }
    
    public void a(final pscl_ax pscl_ax, final pscl_e a) {
        this.h();
        if (a != null) {
            this.a = a;
        }
        this.e = new BigInteger(pscl_ax.a());
        this.d = new BigInteger(pscl_ax.b());
        this.c = this.e.toByteArray().length;
        if (this.e.toByteArray()[0] == 0) {
            --this.c;
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        final byte[] array3 = new byte[this.c];
        array3[0] = 0;
        array3[1] = 2;
        final int n4 = this.c - 3 - n2;
        final byte[] array4 = new byte[n4];
        this.a.b(array4);
        for (int i = 0; i < array4.length; ++i) {
            if (array4[i] == 0) {
                array4[i] = 1;
            }
        }
        System.arraycopy(array4, 0, array3, 2, array4.length);
        array3[3 + n4] = 0;
        System.arraycopy(array, n, array3, 3 + n4, n2);
        return this.b(array3, 0, array3.length, array2, n3);
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        byte[] val;
        if (array[n] < 0) {
            val = new byte[n2 + 1];
            System.arraycopy(array, n, val, 1, n2);
        }
        else {
            val = new byte[n2];
            System.arraycopy(array, n, val, 0, n2);
        }
        final BigInteger bigInteger = new BigInteger(val);
        BigInteger modPow = null;
        byte[] byteArray = null;
        Label_0278: {
            try {
                modPow = bigInteger.modPow(this.d, this.e);
                byteArray = modPow.toByteArray();
                if (byteArray == null || byteArray.length == 0) {
                    final int n4 = 0;
                    break Label_0278;
                }
                final int n5 = array2.length - n3;
                if (byteArray[0] == 0) {
                    System.arraycopy(byteArray, 1, array2, n3 + n5 - (byteArray.length - 1), byteArray.length - 1);
                }
                else {
                    System.arraycopy(byteArray, 0, array2, n3 + n5 - byteArray.length, byteArray.length);
                }
                break Label_0278;
            }
            catch (Exception ex) {
                final int n6 = 0;
                break Label_0278;
            }
            finally {
                if (byteArray != null) {
                    this.a(byteArray);
                }
                if (bigInteger != null) {
                    bigInteger.subtract(bigInteger);
                }
                Label_0359: {
                    if (modPow != null) {
                        modPow.subtract(bigInteger);
                        break Label_0359;
                    }
                    break Label_0359;
                }
                while (true) {}
                // iftrue(Label_0291:, byteArray == null)
                // iftrue(Label_0249:, byteArray == null)
                // iftrue(Label_0262:, bigInteger == null)
                // iftrue(Label_0304:, bigInteger == null)
                // iftrue(Label_0233:, modPow == null)
                // iftrue(Label_0275:, modPow == null)
                // iftrue(Label_0220:, bigInteger == null)
                // iftrue(Label_0207:, byteArray == null)
                while (true) {
                    Label_0304: {
                        final int n4;
                        Block_10: {
                            final int n6;
                            while (true) {
                                Block_15:Label_0220_Outer:Block_9_Outer:
                                while (true) {
                                    Block_14: {
                                    Label_0249:
                                        while (true) {
                                            this.a(byteArray);
                                            break Label_0249;
                                            Label_0317: {
                                                return n6;
                                            }
                                            break Block_14;
                                            Label_0275:
                                            return this.c;
                                            continue Label_0220_Outer;
                                        }
                                        while (true) {
                                            Label_0207: {
                                            Label_0262_Outer:
                                                while (true) {
                                                    while (true) {
                                                        Block_12: {
                                                            break Block_12;
                                                            break Block_15;
                                                            break Block_10;
                                                            break Label_0262_Outer;
                                                            this.a(byteArray);
                                                            break Label_0207;
                                                        }
                                                        bigInteger.subtract(bigInteger);
                                                        continue Block_9_Outer;
                                                    }
                                                    bigInteger.subtract(bigInteger);
                                                    continue Label_0262_Outer;
                                                }
                                                modPow.subtract(bigInteger);
                                                return this.c;
                                            }
                                            continue;
                                        }
                                    }
                                    this.a(byteArray);
                                    continue;
                                }
                                bigInteger.subtract(bigInteger);
                                break Label_0304;
                                continue;
                            }
                            Label_0233: {
                                return n4;
                            }
                            modPow.subtract(bigInteger);
                            return n6;
                        }
                        modPow.subtract(bigInteger);
                        return n4;
                    }
                    continue;
                }
            }
            // iftrue(Label_0317:, modPow == null)
        }
    }
    
    public void b(final pscl_ax pscl_ax, final pscl_e pscl_e) {
        this.a(pscl_ax, pscl_e);
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final pscl_d pscl_d, final byte[] array2, final int n3, final int n4) throws pscl_h {
        final byte[] array3 = new byte[this.c];
        this.b(array2, n3, n4, array3, 0);
        if (array3[0] != 0 || array3[1] != 1) {
            return false;
        }
        int n5;
        for (n5 = 2; n5 < array3.length && array3[n5] == -1; ++n5) {}
        if (n5 > array3.length || array3[n5] != 0) {
            throw new pscl_h("Cannot perform unpadding: incorrect format");
        }
        ++n5;
        if (array3.length - n5 != n2) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (array3[i + n5] != array[n + i]) {
                return false;
            }
        }
        return true;
    }
    
    public void a(final pscl_bl pscl_bl, final pscl_e a) throws pscl_bk {
        this.h();
        if (a != null) {
            this.a = a;
        }
        final byte[][] array = new byte[2][];
        Label_0115: {
            try {
                array[0] = pscl_bl.a();
                array[1] = pscl_bl.b();
                this.e = new BigInteger(array[0]);
                this.f = new BigInteger(array[1]);
                this.c = array[0].length;
                if (array[0][0] == 0) {
                    --this.c;
                }
                break Label_0115;
            }
            catch (Exception ex) {
                throw new pscl_bk("Bad RSA key data.");
            }
            finally {
                Label_0192: {
                    if (array != null && array.length >= 2) {
                        for (int i = 0; i < array[1].length; ++i) {
                            array[1][i] = 0;
                        }
                        break Label_0192;
                    }
                    break Label_0192;
                }
                while (true) {}
                while (true) {
                    int n = 0;
                    array[1][n] = 0;
                    ++n;
                    Label_0130: {
                        break Label_0130;
                        n = 0;
                        break Label_0130;
                        Label_0152: {
                            return;
                        }
                    }
                    continue;
                }
            }
            // iftrue(Label_0152:, array == null || array.length < 2)
            // iftrue(Label_0152:, n >= array[1].length)
        }
    }
    
    public int c(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_bk {
        final byte[] array3 = new byte[this.e()];
        this.d(array, n, n2, array3, 0);
        if (array3[0] != 0 || array3[1] != 2) {
            throw new pscl_bk("Can't unpadding");
        }
        int n4;
        for (n4 = 2; n4 < array3.length && array3[n4] != 0; ++n4) {}
        final int n5 = array2.length - n3;
        if (n5 < array3.length - n4) {
            System.arraycopy(array3, n4 + 1, array2, n3, n5);
            return n5;
        }
        System.arraycopy(array3, n4 + 1, array2, n3, array3.length - n4);
        return array3.length - n4;
    }
    
    public int d(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        BigInteger modPow = null;
        BigInteger val = null;
        byte[] byteArray = null;
        Label_0285: {
            try {
                final byte[] val2 = new byte[n2];
                System.arraycopy(array, n, val2, 0, n2);
                val = new BigInteger(val2);
                modPow = val.modPow(this.f, this.e);
                byteArray = modPow.toByteArray();
                if (byteArray == null || byteArray.length == 0) {
                    final int n4 = 0;
                    break Label_0285;
                }
                if (byteArray[0] == 0) {
                    System.arraycopy(byteArray, 1, array2, n3, byteArray.length - 1);
                    final int n5 = byteArray.length - 1;
                    break Label_0285;
                }
                System.arraycopy(byteArray, 0, array2, n3, byteArray.length);
                final int length = byteArray.length;
                break Label_0285;
            }
            catch (Exception ex) {
                final int n6 = 0;
                break Label_0285;
            }
            finally {
                if (byteArray != null) {
                    this.a(byteArray);
                }
                if (modPow != null) {
                    modPow.subtract(modPow);
                }
                Label_0366: {
                    if (val != null) {
                        val.subtract(val);
                        break Label_0366;
                    }
                    break Label_0366;
                }
                while (true) {}
                // iftrue(Label_0172:, byteArray == null)
                // iftrue(Label_0324:, val == null)
                // iftrue(Label_0198:, val == null)
                // iftrue(Label_0214:, byteArray == null)
                // iftrue(Label_0269:, modPow == null)
                // iftrue(Label_0227:, modPow == null)
                // iftrue(Label_0282:, val == null)
                // iftrue(Label_0256:, byteArray == null)
                // iftrue(Label_0311:, modPow == null)
                // iftrue(Label_0240:, val == null)
                // iftrue(Label_0298:, byteArray == null)
                // iftrue(Label_0185:, modPow == null)
                while (true) {
                    Block_13: {
                        final int length;
                    Block_16_Outer:
                        while (true) {
                            Label_0172: {
                                while (true) {
                                Label_0269_Outer:
                                    while (true) {
                                    Label_0227:
                                        while (true) {
                                            Block_14: {
                                                Block_11: {
                                                    final int n4;
                                                    while (true) {
                                                        Label_0298: {
                                                            Block_9: {
                                                            Label_0185_Outer:
                                                                while (true) {
                                                                    Label_0214: {
                                                                        while (true) {
                                                                            Block_7: {
                                                                                break Block_7;
                                                                                Label_0240: {
                                                                                    return;
                                                                                }
                                                                                while (true) {
                                                                                    while (true) {
                                                                                        val.subtract(val);
                                                                                        return;
                                                                                        this.a(byteArray);
                                                                                        break Label_0214;
                                                                                        continue Label_0185_Outer;
                                                                                    }
                                                                                    val.subtract(val);
                                                                                    return;
                                                                                    Label_0282:
                                                                                    return length;
                                                                                    break Block_9;
                                                                                    continue Label_0185_Outer;
                                                                                }
                                                                            }
                                                                            this.a(byteArray);
                                                                            break Label_0172;
                                                                            modPow.subtract(modPow);
                                                                            continue Block_16_Outer;
                                                                        }
                                                                        break Block_14;
                                                                        Label_0324: {
                                                                            return;
                                                                        }
                                                                        this.a(byteArray);
                                                                        break Label_0298;
                                                                    }
                                                                    break Block_11;
                                                                    modPow.subtract(modPow);
                                                                    continue Block_16_Outer;
                                                                }
                                                                break Block_16_Outer;
                                                            }
                                                            val.subtract(val);
                                                            return n4;
                                                            break Block_13;
                                                        }
                                                        continue Label_0269_Outer;
                                                    }
                                                    Label_0198: {
                                                        return n4;
                                                    }
                                                }
                                                modPow.subtract(modPow);
                                                break Label_0227;
                                            }
                                            modPow.subtract(modPow);
                                            continue;
                                        }
                                        continue Block_16_Outer;
                                    }
                                    continue;
                                }
                            }
                            continue;
                        }
                        val.subtract(val);
                        return length;
                    }
                    this.a(byteArray);
                    continue;
                }
            }
        }
    }
    
    public void b(final pscl_bl pscl_bl, final pscl_e pscl_e) throws pscl_bk {
        this.a(pscl_bl, pscl_e);
    }
    
    public int e(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_bk {
        final int n4 = this.c - 3 - n2;
        final byte[] array3 = new byte[this.c];
        array3[0] = 0;
        array3[1] = 1;
        for (int i = 0; i < n4; ++i) {
            array3[i + 2] = -1;
        }
        System.arraycopy(array, n, array3, n4 + 3, n2);
        return this.d(array3, 0, array3.length, array2, n3);
    }
    
    public void h() {
        final BigInteger d = null;
        this.f = d;
        this.e = d;
        this.d = d;
    }
    
    public void i() {
        this.h();
        this.c = -1;
    }
    
    public void finalize() {
        this.i();
    }
    
    private void a(final byte[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = 0;
        }
    }
}
