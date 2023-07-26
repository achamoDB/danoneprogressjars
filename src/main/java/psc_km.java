import java.security.SecureRandom;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_km extends psc_an implements psc_dl, Cloneable, Serializable
{
    private byte[] a;
    private transient psc_dd b;
    public static final int c = 8;
    private static final int[] d;
    private static final int[] e;
    private static final int[] f;
    private static final int[] g;
    private static final int[] h;
    private static final int[] i;
    private static final int[] j;
    private static final int[] k;
    
    public psc_km() {
        this.a = new byte[128];
    }
    
    public psc_km(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Expected no algorithm parameters");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_di.a(array, n, n2);
    }
    
    public byte[] a(final byte[] array) {
        return array;
    }
    
    public String d() {
        return "DES";
    }
    
    public int g() {
        return 8;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int n) {
    }
    
    public byte[] a(final psc_am psc_am, final boolean b, final psc_di psc_di, final psc_dm psc_dm) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_al b(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.h();
        objectOutputStream.defaultWriteObject();
        this.i();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.j();
    }
    
    private void h() {
        if (this.b != null) {
            this.b.d();
        }
    }
    
    private void i() {
        if (this.b != null) {
            this.b.c();
        }
    }
    
    private void j() {
        if (this.a == null) {
            return;
        }
        (this.b = psc_au.b(this.a)).c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_km psc_km = new psc_km();
        psc_km.a = (byte[])psc_au.a(this.a, this.b);
        psc_km.b = psc_au.a(psc_km.a);
        return psc_km;
    }
    
    private static void a(final byte[] array, final byte[] array2, final boolean b) {
        int n = 16;
        final int[] array3 = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
        final int n2 = (array2[0] & 0xFF) + ((array2[1] & 0xFF) << 8) + ((array2[2] & 0xFF) << 16) + ((array2[3] & 0xFF) << 24);
        final int n3 = (array2[4] & 0xFF) + ((array2[5] & 0xFF) << 8) + ((array2[6] & 0xFF) << 16) + ((array2[7] & 0xFF) << 24);
        final int n4 = (n3 & 0xF0F0F0F) + ((n2 & 0xF0F0F0F) << 4);
        final int n5 = (n2 & 0xF0F0F0F0) + (n3 >>> 4 & 0xF0F0F0F);
        final int n6 = (n4 >>> 24) + (n4 >>> 8 & 0xFF00) + ((n4 & 0xFF00) << 8) + (n4 << 24);
        final int n7 = (n5 ^ n5 >>> 9) & 0x550055;
        final int n8 = n5 ^ n7 + (n7 << 9);
        final int n9 = (n6 ^ n6 >>> 7) & 0xAA00AA;
        final int n10 = n6 ^ n9 + (n9 << 7);
        final int n11 = (n8 ^ n8 >>> 18) & 0x3333;
        final int n12 = n8 ^ n11 + (n11 << 18);
        final int n13 = (n10 ^ n10 >>> 14) & 0xCCCC;
        int n14 = ((n10 ^ n13 + (n13 << 14)) >>> 8) + (n12 >>> 4 & 0xF000000);
        int n15 = n12 & 0xFFFFFFF;
        while (n-- > 0) {
            final int n16 = array3[n];
            final int n17 = 28 - n16;
            final int n18 = (b ? n : (15 - n)) << 3;
            array[n18] = (byte)(((n15 >>> 8 & 0x20) | (n15 >>> 12 & 0x10) | (n15 >>> 7 & 0x8) | (n15 >>> 21 & 0x4) | (n15 << 1 & 0x2) | (n15 >>> 4 & 0x1)) & 0xFF);
            array[n18 + 1] = (byte)(((n15 << 3 & 0x20) | (n15 >>> 23 & 0x10) | (n15 >>> 11 & 0x8) | (n15 >>> 3 & 0x4) | (n15 >>> 19 & 0x2) | (n15 >>> 9 & 0x1)) & 0xFF);
            array[n18 + 2] = (byte)(((n15 >>> 17 & 0x20) | (n15 >>> 14 & 0x10) | (n15 >>> 8 & 0x8) | (n15 >>> 1 & 0x4) | (n15 >>> 24 & 0x2) | (n15 >>> 7 & 0x1)) & 0xFF);
            array[n18 + 3] = (byte)(((n15 >>> 10 & 0x20) | (n15 >>> 2 & 0x10) | (n15 >>> 23 & 0x8) | (n15 >>> 17 & 0x4) | (n15 >>> 11 & 0x2) | (n15 >>> 1 & 0x1)) & 0xFF);
            array[n18 + 4] = (byte)(((n14 >>> 7 & 0x20) | (n14 >>> 19 & 0x10) | (n14 << 1 & 0x8) | (n14 >>> 6 & 0x4) | (n14 >>> 17 & 0x2) | (n14 >>> 26 & 0x1)) & 0xFF);
            array[n18 + 5] = (byte)(((n14 << 4 & 0x20) | (n14 >>> 7 & 0x10) | (n14 >>> 19 & 0x8) | (n14 >>> 14 & 0x4) | (n14 >>> 3 & 0x2) | (n14 >>> 19 & 0x1)) & 0xFF);
            array[n18 + 6] = (byte)(((n14 >>> 10 & 0x20) | (n14 >>> 16 & 0x10) | (n14 >>> 7 & 0x8) | (n14 >>> 25 & 0x4) | (n14 >>> 4 & 0x2) | (n14 >>> 24 & 0x1)) & 0xFF);
            array[n18 + 7] = (byte)(((n14 >>> 12 & 0x20) | (n14 >>> 9 & 0x10) | (n14 >>> 18 & 0x8) | (n14 >>> 5 & 0x4) | (n14 << 1 & 0x2) | (n14 >>> 3 & 0x1)) & 0xFF);
            n15 = (n15 << n16 | n15 >>> n17);
            n14 = (n14 << n16 | n14 >>> n17);
        }
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        byte[] a;
        try {
            a = ((psc_j3)psc_dc).a("Clear");
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        if (this.b != null) {
            this.b.e();
        }
        a(this.a, a, true);
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        for (int i = 0; i < a.length; ++i) {
            a[i] = 0;
        }
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        byte[] a;
        try {
            a = ((psc_j3)psc_dc).a("Clear");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        if (this.b != null) {
            this.b.e();
        }
        a(this.a, a, false);
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        psc_au.c(a);
    }
    
    private void c(final byte[] array, final int n, final byte[] array2, final int n2) {
        final int n3 = n + 7;
        final int n4 = n3 - 1;
        final int n5 = array[n3] & 0xFF;
        final int n6 = n4;
        final int n7 = n6 - 1;
        final int n8 = n5 + ((array[n6] & 0xFF) << 8);
        final int n9 = n7;
        final int n10 = n9 - 1;
        final int n11 = n8 + ((array[n9] & 0xFF) << 16);
        final int n12 = n10;
        final int n13 = n12 - 1;
        final int n14 = n11 + ((array[n12] & 0xFF) << 24);
        final int n15 = n13;
        final int n16 = n15 - 1;
        final int n17 = array[n15] & 0xFF;
        final int n18 = n16;
        final int n19 = n18 - 1;
        final int n20 = n17 + ((array[n18] & 0xFF) << 8);
        final int n21 = n19;
        final int n22 = n20 + ((array[n21] & 0xFF) << 16) + ((array[n21 - 1] & 0xFF) << 24);
        final int n23 = n14;
        final int n24 = n22;
        final int n25 = (n23 ^ n24 >>> 1) & 0x55555555;
        final int n26 = n23 ^ n25;
        final int n27 = n24 ^ n25 << 1;
        final int n28 = (n26 ^ n26 >>> 12) & 0xF0F0;
        final int n29 = n26 ^ n28 + (n28 << 12);
        final int n30 = (n27 ^ n27 >>> 12) & 0xF0F0;
        final int n31 = n27 ^ n30 + (n30 << 12);
        final int n32 = (n29 ^ n29 >>> 6) & 0xCC00CC;
        final int n33 = n29 ^ n32 + (n32 << 6);
        final int n34 = (n31 ^ n31 >>> 6) & 0xCC00CC;
        final int n35 = n31 ^ n34 + (n34 << 6);
        final int n36 = (n33 ^ n33 >>> 3) & 0xA0A0A0A;
        final int n37 = n33 ^ n36 + (n36 << 3);
        final int n38 = (n35 ^ n35 >>> 3) & 0xA0A0A0A;
        final int n39 = n35 ^ n38 + (n38 << 3);
        final int n40 = (n37 ^ n37 >>> 3) & 0x11111111;
        final int n41 = n37 ^ n40 + (n40 << 3);
        final int n42 = (n39 ^ n39 >>> 3) & 0x11111111;
        final int n43 = n39 ^ n42 + (n42 << 3);
        final int n44 = n41;
        int n45 = n43;
        int n46 = n44;
        int n47;
        int n48;
        int n49;
        int n50;
        int n51;
        int n52;
        int n53;
        int n54;
        int n55;
        int n56;
        int n57;
        int n58;
        int n59;
        int n60;
        int n61;
        for (int i = 0; i <= 112; ++i, n48 = (n47 | psc_km.e[(n46 >>> 23 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n49 = (n48 | psc_km.f[(n46 >>> 19 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n50 = (n49 | psc_km.g[(n46 >>> 15 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n51 = (n50 | psc_km.h[(n46 >>> 11 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n52 = (n51 | psc_km.i[(n46 >>> 7 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n53 = (n52 | psc_km.j[(n46 >>> 3 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n54 = (n53 | psc_km.k[((n46 << 1 | n46 >>> 31) & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n45 ^= n54, n55 = psc_km.d[((n45 >>> 27 | n45 << 5) & 0x3F) ^ (this.a[i] & 0xFF)], ++i, n56 = (n55 | psc_km.e[(n45 >>> 23 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n57 = (n56 | psc_km.f[(n45 >>> 19 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n58 = (n57 | psc_km.g[(n45 >>> 15 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n59 = (n58 | psc_km.h[(n45 >>> 11 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n60 = (n59 | psc_km.i[(n45 >>> 7 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n61 = (n60 | psc_km.j[(n45 >>> 3 & 0x3F) ^ (this.a[i] & 0xFF)]), ++i, n46 ^= (n61 | psc_km.k[((n45 << 1 | n45 >>> 31) & 0x3F) ^ (this.a[i] & 0xFF)]), ++i) {
            n47 = psc_km.d[((n46 >>> 27 | n46 << 5) & 0x3F) ^ (this.a[i] & 0xFF)];
        }
        final int n62 = (n45 ^ n46 >>> 4) & 0xF0F0F0F;
        final int n63 = n45 ^ n62;
        final int n64 = n46 ^ n62 << 4;
        final int n65 = (n63 ^ n63 >>> 18) & 0x3333;
        final int n66 = n63 ^ n65 + (n65 << 18);
        final int n67 = (n64 ^ n64 >>> 18) & 0x3333;
        final int n68 = n64 ^ n67 + (n67 << 18);
        final int n69 = (n66 ^ n66 >>> 9) & 0x550055;
        final int n70 = n66 ^ n69 + (n69 << 9);
        final int n71 = (n68 ^ n68 >>> 9) & 0x550055;
        final int n72 = n68 ^ n71 + (n71 << 9);
        final int n73 = (n70 ^ n70 >>> 6) & 0x3030303;
        final int n74 = n70 ^ n73 + (n73 << 6);
        final int n75 = (n72 ^ n72 >>> 6) & 0x3030303;
        final int n76 = n72 ^ n75 + (n75 << 6);
        final int n77 = (n74 ^ n74 >>> 3) & 0x11111111;
        final int n78 = n74 ^ n77 + (n77 << 3);
        final int n79 = (n76 ^ n76 >>> 3) & 0x11111111;
        final int n80 = n76 ^ n79 + (n79 << 3);
        int n81 = n2 + 7;
        array2[n81] = (byte)n78;
        --n81;
        array2[n81] = (byte)(n78 >>> 8);
        --n81;
        array2[n81] = (byte)(n78 >>> 16);
        --n81;
        array2[n81] = (byte)(n78 >>> 24);
        --n81;
        array2[n81] = (byte)n80;
        --n81;
        array2[n81] = (byte)(n80 >>> 8);
        --n81;
        array2[n81] = (byte)(n80 >>> 16);
        --n81;
        array2[n81] = (byte)(n80 >>> 24);
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.c(array, n, array2, n2);
        return 8;
    }
    
    public int b(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.c(array, n, array2, n2);
        return 8;
    }
    
    public void e() {
        if (this.b != null) {
            this.b.c();
        }
    }
    
    public void f() {
        if (this.b != null) {
            this.b.d();
        }
    }
    
    public void y() {
        super.y();
        psc_au.c(this.a, this.b);
        this.b = null;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        d = new int[] { 8421888, 0, 32768, 8421890, 8421378, 33282, 2, 32768, 512, 8421888, 8421890, 512, 8389122, 8421378, 8388608, 2, 514, 8389120, 8389120, 33280, 33280, 8421376, 8421376, 8389122, 32770, 8388610, 8388610, 32770, 0, 514, 33282, 8388608, 32768, 8421890, 2, 8421376, 8421888, 8388608, 8388608, 512, 8421378, 32768, 33280, 8388610, 512, 2, 8389122, 33282, 8421890, 32770, 8421376, 8389122, 8388610, 514, 33282, 8421888, 514, 8389120, 8389120, 0, 32770, 33280, 0, 8421378 };
        e = new int[] { 1074282512, 1073758208, 16384, 540688, 524288, 16, 1074266128, 1073758224, 1073741840, 1074282512, 1074282496, 1073741824, 1073758208, 524288, 16, 1074266128, 540672, 524304, 1073758224, 0, 1073741824, 16384, 540688, 1074266112, 524304, 1073741840, 0, 540672, 16400, 1074282496, 1074266112, 16400, 0, 540688, 1074266128, 524288, 1073758224, 1074266112, 1074282496, 16384, 1074266112, 1073758208, 16, 1074282512, 540688, 16, 16384, 1073741824, 16400, 1074282496, 524288, 1073741840, 524304, 1073758224, 1073741840, 524304, 540672, 0, 1073758208, 16400, 1073741824, 1074266128, 1074282512, 540672 };
        f = new int[] { 260, 67174656, 0, 67174404, 67109120, 0, 65796, 67109120, 65540, 67108868, 67108868, 65536, 67174660, 65540, 67174400, 260, 67108864, 4, 67174656, 256, 65792, 67174400, 67174404, 65796, 67109124, 65792, 65536, 67109124, 4, 67174660, 256, 67108864, 67174656, 67108864, 65540, 260, 65536, 67174656, 67109120, 0, 256, 65540, 67174660, 67109120, 67108868, 256, 0, 67174404, 67109124, 65536, 67108864, 67174660, 4, 65796, 65792, 67108868, 67174400, 67109124, 260, 67174400, 65796, 4, 67174404, 65792 };
        g = new int[] { -2143285248, -2147479488, -2147479488, 64, 4198464, -2143289280, -2143289344, -2147479552, 0, 4198400, 4198400, -2143285184, -2147483584, 0, 4194368, -2143289344, Integer.MIN_VALUE, 4096, 4194304, -2143285248, 64, 4194304, -2147479552, 4160, -2143289280, Integer.MIN_VALUE, 4160, 4194368, 4096, 4198464, -2143285184, -2147483584, 4194368, -2143289344, 4198400, -2143285184, -2147483584, 0, 0, 4198400, 4160, 4194368, -2143289280, Integer.MIN_VALUE, -2143285248, -2147479488, -2147479488, 64, -2143285184, -2147483584, Integer.MIN_VALUE, 4096, -2143289344, -2147479552, 4198464, -2143289280, -2147479552, 4160, 4194304, -2143285248, 64, 4194304, 4096, 4198464 };
        h = new int[] { 128, 17039488, 17039360, 553648256, 262144, 128, 536870912, 17039360, 537133184, 262144, 16777344, 537133184, 553648256, 553910272, 262272, 536870912, 16777216, 537133056, 537133056, 0, 536871040, 553910400, 553910400, 16777344, 553910272, 536871040, 0, 553648128, 17039488, 16777216, 553648128, 262272, 262144, 553648256, 128, 16777216, 536870912, 17039360, 553648256, 537133184, 16777344, 536870912, 553910272, 17039488, 537133184, 128, 16777216, 553910272, 553910400, 262272, 553648128, 553910400, 17039360, 0, 537133056, 553648128, 262272, 16777344, 536871040, 262144, 0, 537133056, 17039488, 536871040 };
        i = new int[] { 268435464, 270532608, 8192, 270540808, 270532608, 8, 270540808, 2097152, 268443648, 2105352, 2097152, 268435464, 2097160, 268443648, 268435456, 8200, 0, 2097160, 268443656, 8192, 2105344, 268443656, 8, 270532616, 270532616, 0, 2105352, 270540800, 8200, 2105344, 270540800, 268435456, 268443648, 8, 270532616, 2105344, 270540808, 2097152, 8200, 268435464, 2097152, 268443648, 268435456, 8200, 268435464, 270540808, 2105344, 270532608, 2105352, 270540800, 0, 270532616, 8, 8192, 270532608, 2105352, 8192, 2097160, 268443656, 0, 270540800, 268435456, 2097160, 268443656 };
        j = new int[] { 1048576, 34603009, 33555457, 0, 1024, 33555457, 1049601, 34604032, 34604033, 1048576, 0, 33554433, 1, 33554432, 34603009, 1025, 33555456, 1049601, 1048577, 33555456, 33554433, 34603008, 34604032, 1048577, 34603008, 1024, 1025, 34604033, 1049600, 1, 33554432, 1049600, 33554432, 1049600, 1048576, 33555457, 33555457, 34603009, 34603009, 1, 1048577, 33554432, 33555456, 1048576, 34604032, 1025, 1049601, 34604032, 1025, 33554433, 34604033, 34603008, 1049600, 0, 1, 34604033, 0, 1049601, 34603008, 1024, 33554433, 33555456, 1024, 1048577 };
        k = new int[] { 134219808, 2048, 131072, 134350880, 134217728, 134219808, 32, 134217728, 131104, 134348800, 134350880, 133120, 134350848, 133152, 2048, 32, 134348800, 134217760, 134219776, 2080, 133120, 131104, 134348832, 134350848, 2080, 0, 0, 134348832, 134217760, 134219776, 133152, 131072, 133152, 131072, 134350848, 2048, 32, 134348832, 2048, 133152, 134219776, 32, 134217760, 134348800, 134348832, 134217728, 131072, 134219808, 0, 134350880, 131104, 134217760, 134348800, 134219776, 134219808, 0, 134350880, 133120, 133120, 2080, 2080, 131104, 134217728, 134350848 };
    }
}
