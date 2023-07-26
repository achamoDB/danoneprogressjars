import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ef extends psc_an implements psc_az, Cloneable, Serializable
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] e;
    private long f;
    private byte[] g;
    private static final byte[] h;
    private int i;
    private static final int j = 1;
    private static final int k = 2;
    private static final int l = 3;
    private static final int m = 16;
    private static final int n = 64;
    private static final int o = 8;
    private static final int p = 7;
    private static final int q = 12;
    private static final int r = 17;
    private static final int s = 22;
    private static final int t = 5;
    private static final int u = 9;
    private static final int v = 14;
    private static final int w = 20;
    private static final int x = 4;
    private static final int y = 11;
    private static final int z = 16;
    private static final int aa = 23;
    private static final int ab = 6;
    private static final int ac = 10;
    private static final int ad = 15;
    private static final int ae = 21;
    
    public psc_ef() {
        this.e = new int[16];
        this.g = new byte[64];
    }
    
    public psc_ef(final int[] array) throws psc_be {
        this.e = new int[16];
        this.g = new byte[64];
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String e() {
        return "MD5";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        byte[] a;
        try {
            a = psc_q.a("MD5", 10, null, 0, 0);
        }
        catch (psc_m psc_m) {
            a = null;
        }
        return a;
    }
    
    public int f() {
        return 64;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 16;
    }
    
    public int i() {
        return psc_ef.h.length + 16;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ef psc_ef = new psc_ef();
        psc_ef.a = this.a;
        psc_ef.b = this.b;
        psc_ef.c = this.c;
        psc_ef.d = this.d;
        if (this.e != null) {
            psc_ef.e = this.e.clone();
        }
        psc_ef.f = this.f;
        if (this.g != null) {
            psc_ef.g = this.g.clone();
        }
        psc_ef.i = this.i;
        return psc_ef;
    }
    
    public void j() {
        this.f = 0L;
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
    }
    
    public void a(final byte[] array, int n, int i) throws psc_en {
        if (i <= 0) {
            return;
        }
        int n2 = (int)this.f >> 3 & 0x3F;
        final int n3 = 64 - n2;
        this.f += (long)i << 3;
        if (i >= n3) {
            if (n2 != 0) {
                System.arraycopy(array, n, this.g, n2, n3);
                n += n3;
                this.c(this.g, 0);
                i -= n3;
                n2 = 0;
            }
            while (i >= 64) {
                this.c(array, n);
                i -= 64;
                n += 64;
            }
        }
        System.arraycopy(array, n, this.g, n2, i);
        n += i;
    }
    
    public int b(final byte[] array, int n) throws psc_en {
        final int n2 = (int)(this.f >> 3 & 0x3FL);
        final int n3 = (n2 < 56) ? (56 - n2) : (120 - n2);
        final byte[] array2 = new byte[n3];
        array2[0] = -128;
        final byte[] array3 = { (byte)(this.f & 0xFFL), (byte)(this.f >> 8 & 0xFFL), (byte)(this.f >> 16 & 0xFFL), (byte)(this.f >> 24 & 0xFFL), (byte)(this.f >> 32 & 0xFFL), (byte)(this.f >> 40 & 0xFFL), (byte)(this.f >> 48 & 0xFFL), (byte)(this.f >> 56 & 0xFFL) };
        this.a(array2, 0, n3);
        this.a(array3, 0, 8);
        array[n] = (byte)(this.a & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.b & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.c & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.d & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 24 & 0xFF);
        ++n;
        return 16;
    }
    
    public int a(final byte[] array, int n, final byte[] array2, int n2) {
        final int n3 = psc_ef.h.length + 16;
        int i;
        for (i = 0; i < psc_ef.h.length; ++i, ++n2) {
            array2[n2] = psc_ef.h[i];
        }
        while (i < n3) {
            array2[n2] = array[n];
            ++i;
            ++n2;
            ++n;
        }
        return n3;
    }
    
    private void c(final byte[] array, int n) {
        final int a = this.a;
        final int b = this.b;
        final int c = this.c;
        final int d = this.d;
        for (int i = 0; i < 16; ++i) {
            final int n2 = array[n] & 0xFF;
            ++n;
            final int n3 = n2 | (array[n] & 0xFF) << 8;
            ++n;
            final int n4 = n3 | (array[n] & 0xFF) << 16;
            ++n;
            final int n5 = n4 | (array[n] & 0xFF) << 24;
            ++n;
            this.e[i] = n5;
        }
        final int n6 = a + (((b & c) | (~b & d)) + this.e[0] - 680876936);
        final int n7 = (n6 << 7 | n6 >>> 25) + b;
        final int n8 = d + (((n7 & b) | (~n7 & c)) + this.e[1] - 389564586);
        final int n9 = (n8 << 12 | n8 >>> 20) + n7;
        final int n10 = c + (((n9 & n7) | (~n9 & b)) + this.e[2] + 606105819);
        final int n11 = (n10 << 17 | n10 >>> 15) + n9;
        final int n12 = b + (((n11 & n9) | (~n11 & n7)) + this.e[3] - 1044525330);
        final int n13 = (n12 << 22 | n12 >>> 10) + n11;
        final int n14 = n7 + (((n13 & n11) | (~n13 & n9)) + this.e[4] - 176418897);
        final int n15 = (n14 << 7 | n14 >>> 25) + n13;
        final int n16 = n9 + (((n15 & n13) | (~n15 & n11)) + this.e[5] + 1200080426);
        final int n17 = (n16 << 12 | n16 >>> 20) + n15;
        final int n18 = n11 + (((n17 & n15) | (~n17 & n13)) + this.e[6] - 1473231341);
        final int n19 = (n18 << 17 | n18 >>> 15) + n17;
        final int n20 = n13 + (((n19 & n17) | (~n19 & n15)) + this.e[7] - 45705983);
        final int n21 = (n20 << 22 | n20 >>> 10) + n19;
        final int n22 = n15 + (((n21 & n19) | (~n21 & n17)) + this.e[8] + 1770035416);
        final int n23 = (n22 << 7 | n22 >>> 25) + n21;
        final int n24 = n17 + (((n23 & n21) | (~n23 & n19)) + this.e[9] - 1958414417);
        final int n25 = (n24 << 12 | n24 >>> 20) + n23;
        final int n26 = n19 + (((n25 & n23) | (~n25 & n21)) + this.e[10] - 42063);
        final int n27 = (n26 << 17 | n26 >>> 15) + n25;
        final int n28 = n21 + (((n27 & n25) | (~n27 & n23)) + this.e[11] - 1990404162);
        final int n29 = (n28 << 22 | n28 >>> 10) + n27;
        final int n30 = n23 + (((n29 & n27) | (~n29 & n25)) + this.e[12] + 1804603682);
        final int n31 = (n30 << 7 | n30 >>> 25) + n29;
        final int n32 = n25 + (((n31 & n29) | (~n31 & n27)) + this.e[13] - 40341101);
        final int n33 = (n32 << 12 | n32 >>> 20) + n31;
        final int n34 = n27 + (((n33 & n31) | (~n33 & n29)) + this.e[14] - 1502002290);
        final int n35 = (n34 << 17 | n34 >>> 15) + n33;
        final int n36 = n29 + (((n35 & n33) | (~n35 & n31)) + this.e[15] + 1236535329);
        final int n37 = (n36 << 22 | n36 >>> 10) + n35;
        final int n38 = n31 + (((n37 & n33) | (n35 & ~n33)) + this.e[1] - 165796510);
        final int n39 = (n38 << 5 | n38 >>> 27) + n37;
        final int n40 = n33 + (((n39 & n35) | (n37 & ~n35)) + this.e[6] - 1069501632);
        final int n41 = (n40 << 9 | n40 >>> 23) + n39;
        final int n42 = n35 + (((n41 & n37) | (n39 & ~n37)) + this.e[11] + 643717713);
        final int n43 = (n42 << 14 | n42 >>> 18) + n41;
        final int n44 = n37 + (((n43 & n39) | (n41 & ~n39)) + this.e[0] - 373897302);
        final int n45 = (n44 << 20 | n44 >>> 12) + n43;
        final int n46 = n39 + (((n45 & n41) | (n43 & ~n41)) + this.e[5] - 701558691);
        final int n47 = (n46 << 5 | n46 >>> 27) + n45;
        final int n48 = n41 + (((n47 & n43) | (n45 & ~n43)) + this.e[10] + 38016083);
        final int n49 = (n48 << 9 | n48 >>> 23) + n47;
        final int n50 = n43 + (((n49 & n45) | (n47 & ~n45)) + this.e[15] - 660478335);
        final int n51 = (n50 << 14 | n50 >>> 18) + n49;
        final int n52 = n45 + (((n51 & n47) | (n49 & ~n47)) + this.e[4] - 405537848);
        final int n53 = (n52 << 20 | n52 >>> 12) + n51;
        final int n54 = n47 + (((n53 & n49) | (n51 & ~n49)) + this.e[9] + 568446438);
        final int n55 = (n54 << 5 | n54 >>> 27) + n53;
        final int n56 = n49 + (((n55 & n51) | (n53 & ~n51)) + this.e[14] - 1019803690);
        final int n57 = (n56 << 9 | n56 >>> 23) + n55;
        final int n58 = n51 + (((n57 & n53) | (n55 & ~n53)) + this.e[3] - 187363961);
        final int n59 = (n58 << 14 | n58 >>> 18) + n57;
        final int n60 = n53 + (((n59 & n55) | (n57 & ~n55)) + this.e[8] + 1163531501);
        final int n61 = (n60 << 20 | n60 >>> 12) + n59;
        final int n62 = n55 + (((n61 & n57) | (n59 & ~n57)) + this.e[13] - 1444681467);
        final int n63 = (n62 << 5 | n62 >>> 27) + n61;
        final int n64 = n57 + (((n63 & n59) | (n61 & ~n59)) + this.e[2] - 51403784);
        final int n65 = (n64 << 9 | n64 >>> 23) + n63;
        final int n66 = n59 + (((n65 & n61) | (n63 & ~n61)) + this.e[7] + 1735328473);
        final int n67 = (n66 << 14 | n66 >>> 18) + n65;
        final int n68 = n61 + (((n67 & n63) | (n65 & ~n63)) + this.e[12] - 1926607734);
        final int n69 = (n68 << 20 | n68 >>> 12) + n67;
        final int n70 = n63 + ((n69 ^ n67 ^ n65) + this.e[5] - 378558);
        final int n71 = (n70 << 4 | n70 >>> 28) + n69;
        final int n72 = n65 + ((n71 ^ n69 ^ n67) + this.e[8] - 2022574463);
        final int n73 = (n72 << 11 | n72 >>> 21) + n71;
        final int n74 = n67 + ((n73 ^ n71 ^ n69) + this.e[11] + 1839030562);
        final int n75 = (n74 << 16 | n74 >>> 16) + n73;
        final int n76 = n69 + ((n75 ^ n73 ^ n71) + this.e[14] - 35309556);
        final int n77 = (n76 << 23 | n76 >>> 9) + n75;
        final int n78 = n71 + ((n77 ^ n75 ^ n73) + this.e[1] - 1530992060);
        final int n79 = (n78 << 4 | n78 >>> 28) + n77;
        final int n80 = n73 + ((n79 ^ n77 ^ n75) + this.e[4] + 1272893353);
        final int n81 = (n80 << 11 | n80 >>> 21) + n79;
        final int n82 = n75 + ((n81 ^ n79 ^ n77) + this.e[7] - 155497632);
        final int n83 = (n82 << 16 | n82 >>> 16) + n81;
        final int n84 = n77 + ((n83 ^ n81 ^ n79) + this.e[10] - 1094730640);
        final int n85 = (n84 << 23 | n84 >>> 9) + n83;
        final int n86 = n79 + ((n85 ^ n83 ^ n81) + this.e[13] + 681279174);
        final int n87 = (n86 << 4 | n86 >>> 28) + n85;
        final int n88 = n81 + ((n87 ^ n85 ^ n83) + this.e[0] - 358537222);
        final int n89 = (n88 << 11 | n88 >>> 21) + n87;
        final int n90 = n83 + ((n89 ^ n87 ^ n85) + this.e[3] - 722521979);
        final int n91 = (n90 << 16 | n90 >>> 16) + n89;
        final int n92 = n85 + ((n91 ^ n89 ^ n87) + this.e[6] + 76029189);
        final int n93 = (n92 << 23 | n92 >>> 9) + n91;
        final int n94 = n87 + ((n93 ^ n91 ^ n89) + this.e[9] - 640364487);
        final int n95 = (n94 << 4 | n94 >>> 28) + n93;
        final int n96 = n89 + ((n95 ^ n93 ^ n91) + this.e[12] - 421815835);
        final int n97 = (n96 << 11 | n96 >>> 21) + n95;
        final int n98 = n91 + ((n97 ^ n95 ^ n93) + this.e[15] + 530742520);
        final int n99 = (n98 << 16 | n98 >>> 16) + n97;
        final int n100 = n93 + ((n99 ^ n97 ^ n95) + this.e[2] - 995338651);
        final int n101 = (n100 << 23 | n100 >>> 9) + n99;
        final int n102 = n95 + ((n99 ^ (n101 | ~n97)) + this.e[0] - 198630844);
        final int n103 = (n102 << 6 | n102 >>> 26) + n101;
        final int n104 = n97 + ((n101 ^ (n103 | ~n99)) + this.e[7] + 1126891415);
        final int n105 = (n104 << 10 | n104 >>> 22) + n103;
        final int n106 = n99 + ((n103 ^ (n105 | ~n101)) + this.e[14] - 1416354905);
        final int n107 = (n106 << 15 | n106 >>> 17) + n105;
        final int n108 = n101 + ((n105 ^ (n107 | ~n103)) + this.e[5] - 57434055);
        final int n109 = (n108 << 21 | n108 >>> 11) + n107;
        final int n110 = n103 + ((n107 ^ (n109 | ~n105)) + this.e[12] + 1700485571);
        final int n111 = (n110 << 6 | n110 >>> 26) + n109;
        final int n112 = n105 + ((n109 ^ (n111 | ~n107)) + this.e[3] - 1894986606);
        final int n113 = (n112 << 10 | n112 >>> 22) + n111;
        final int n114 = n107 + ((n111 ^ (n113 | ~n109)) + this.e[10] - 1051523);
        final int n115 = (n114 << 15 | n114 >>> 17) + n113;
        final int n116 = n109 + ((n113 ^ (n115 | ~n111)) + this.e[1] - 2054922799);
        final int n117 = (n116 << 21 | n116 >>> 11) + n115;
        final int n118 = n111 + ((n115 ^ (n117 | ~n113)) + this.e[8] + 1873313359);
        final int n119 = (n118 << 6 | n118 >>> 26) + n117;
        final int n120 = n113 + ((n117 ^ (n119 | ~n115)) + this.e[15] - 30611744);
        final int n121 = (n120 << 10 | n120 >>> 22) + n119;
        final int n122 = n115 + ((n119 ^ (n121 | ~n117)) + this.e[6] - 1560198380);
        final int n123 = (n122 << 15 | n122 >>> 17) + n121;
        final int n124 = n117 + ((n121 ^ (n123 | ~n119)) + this.e[13] + 1309151649);
        final int n125 = (n124 << 21 | n124 >>> 11) + n123;
        final int n126 = n119 + ((n123 ^ (n125 | ~n121)) + this.e[4] - 145523070);
        final int n127 = (n126 << 6 | n126 >>> 26) + n125;
        final int n128 = n121 + ((n125 ^ (n127 | ~n123)) + this.e[11] - 1120210379);
        final int n129 = (n128 << 10 | n128 >>> 22) + n127;
        final int n130 = n123 + ((n127 ^ (n129 | ~n125)) + this.e[2] + 718787259);
        final int n131 = (n130 << 15 | n130 >>> 17) + n129;
        final int n132 = n125 + ((n129 ^ (n131 | ~n127)) + this.e[9] - 343485551);
        final int n133 = (n132 << 21 | n132 >>> 11) + n131;
        this.a += n127;
        this.b += n133;
        this.c += n131;
        this.d += n129;
    }
    
    public void y() {
        super.y();
        this.d(this.g);
        this.c(this.e);
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.i = 1;
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
        h = new byte[] { 48, 32, 48, 12, 6, 8, 42, -122, 72, -122, -9, 13, 2, 5, 5, 0, 4, 16 };
    }
}
