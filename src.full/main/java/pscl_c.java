// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_c implements pscl_d, Cloneable
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int[] f;
    private long g;
    private byte[] h;
    private static final byte[] i;
    private int j;
    private static final int k = 1;
    private static final int l = 2;
    private static final int m = 3;
    private static final int n = 20;
    private static final int o = 64;
    private static final int p = 8;
    private static final int q = 0;
    private static final int r = 1;
    private static final int s = 2;
    private static final int t = 3;
    private static final int u = 4;
    
    public pscl_c() {
        this.f = new int[80];
        this.h = new byte[64];
    }
    
    public pscl_c(final int[] array) {
        this.f = new int[80];
        this.h = new byte[64];
    }
    
    public String a() {
        return "SHA";
    }
    
    public int b() {
        return 64;
    }
    
    public int c() {
        return 8;
    }
    
    public int d() {
        return 20;
    }
    
    public int e() {
        return pscl_c.i.length + 20;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final pscl_c pscl_c = new pscl_c();
        pscl_c.a = this.a;
        pscl_c.b = this.b;
        pscl_c.c = this.c;
        pscl_c.d = this.d;
        pscl_c.e = this.e;
        if (this.f != null) {
            pscl_c.f = this.f.clone();
        }
        pscl_c.g = this.g;
        if (this.h != null) {
            pscl_c.h = this.h.clone();
        }
        pscl_c.j = this.j;
        return pscl_c;
    }
    
    public long h() {
        return this.g;
    }
    
    public void f() {
        this.g = 0L;
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
    }
    
    public void a(final byte[] array, int n, int i) {
        if (i <= 0) {
            return;
        }
        int n2 = (int)(this.g & 0x3FL);
        final int n3 = 64 - n2;
        this.g += i;
        if (i >= n3) {
            if (n2 != 0) {
                System.arraycopy(array, n, this.h, n2, n3);
                n += n3;
                this.c(this.h, 0);
                i -= n3;
                n2 = 0;
            }
            while (i >= 64) {
                this.c(array, n);
                i -= 64;
                n += 64;
            }
        }
        System.arraycopy(array, n, this.h, n2, i);
        n += i;
    }
    
    public void i() {
        final int n = (int)(this.g & 0x3FL);
        final int n2 = (n < 56) ? (56 - n) : (120 - n);
        final byte[] array = new byte[n2];
        array[0] = -128;
        final long n3 = this.g << 3;
        final byte[] array2 = { (byte)(n3 >> 56 & 0xFFL), (byte)(n3 >> 48 & 0xFFL), (byte)(n3 >> 40 & 0xFFL), (byte)(n3 >> 32 & 0xFFL), (byte)(n3 >> 24 & 0xFFL), (byte)(n3 >> 16 & 0xFFL), (byte)(n3 >> 8 & 0xFFL), (byte)(n3 & 0xFFL) };
        this.a(array, 0, n2);
        this.a(array2, 0, 8);
    }
    
    public int a(final byte[] array, final int n) {
        this.i();
        this.b(array, n);
        return 20;
    }
    
    private void c(final byte[] array, int n) {
        final int a = this.a;
        final int b = this.b;
        final int c = this.c;
        final int d = this.d;
        final int e = this.e;
        for (int i = 0; i < 16; ++i) {
            final int n2 = (array[n] & 0xFF) << 24;
            ++n;
            final int n3 = n2 | (array[n] & 0xFF) << 16;
            ++n;
            final int n4 = n3 | (array[n] & 0xFF) << 8;
            ++n;
            final int n5 = n4 | (array[n] & 0xFF);
            ++n;
            this.f[i] = n5;
        }
        for (int j = 16; j < 80; ++j) {
            final int n6 = this.f[j - 3] ^ this.f[j - 8] ^ this.f[j - 14] ^ this.f[j - 16];
            this.f[j] = (n6 << 1 | n6 >>> 31);
        }
        final int n7 = e + (a << 5 | a >>> 27) + ((b & c) | (~b & d)) + (this.f[0] + 1518500249);
        final int n8 = b << 30 | b >>> 2;
        final int n9 = d + (n7 << 5 | n7 >>> 27) + ((a & n8) | (~a & c)) + (this.f[1] + 1518500249);
        final int n10 = a << 30 | a >>> 2;
        final int n11 = c + (n9 << 5 | n9 >>> 27) + ((n7 & n10) | (~n7 & n8)) + (this.f[2] + 1518500249);
        final int n12 = n7 << 30 | n7 >>> 2;
        final int n13 = n8 + (n11 << 5 | n11 >>> 27) + ((n9 & n12) | (~n9 & n10)) + (this.f[3] + 1518500249);
        final int n14 = n9 << 30 | n9 >>> 2;
        final int n15 = n10 + (n13 << 5 | n13 >>> 27) + ((n11 & n14) | (~n11 & n12)) + (this.f[4] + 1518500249);
        final int n16 = n11 << 30 | n11 >>> 2;
        final int n17 = n12 + (n15 << 5 | n15 >>> 27) + ((n13 & n16) | (~n13 & n14)) + (this.f[5] + 1518500249);
        final int n18 = n13 << 30 | n13 >>> 2;
        final int n19 = n14 + (n17 << 5 | n17 >>> 27) + ((n15 & n18) | (~n15 & n16)) + (this.f[6] + 1518500249);
        final int n20 = n15 << 30 | n15 >>> 2;
        final int n21 = n16 + (n19 << 5 | n19 >>> 27) + ((n17 & n20) | (~n17 & n18)) + (this.f[7] + 1518500249);
        final int n22 = n17 << 30 | n17 >>> 2;
        final int n23 = n18 + (n21 << 5 | n21 >>> 27) + ((n19 & n22) | (~n19 & n20)) + (this.f[8] + 1518500249);
        final int n24 = n19 << 30 | n19 >>> 2;
        final int n25 = n20 + (n23 << 5 | n23 >>> 27) + ((n21 & n24) | (~n21 & n22)) + (this.f[9] + 1518500249);
        final int n26 = n21 << 30 | n21 >>> 2;
        final int n27 = n22 + (n25 << 5 | n25 >>> 27) + ((n23 & n26) | (~n23 & n24)) + (this.f[10] + 1518500249);
        final int n28 = n23 << 30 | n23 >>> 2;
        final int n29 = n24 + (n27 << 5 | n27 >>> 27) + ((n25 & n28) | (~n25 & n26)) + (this.f[11] + 1518500249);
        final int n30 = n25 << 30 | n25 >>> 2;
        final int n31 = n26 + (n29 << 5 | n29 >>> 27) + ((n27 & n30) | (~n27 & n28)) + (this.f[12] + 1518500249);
        final int n32 = n27 << 30 | n27 >>> 2;
        final int n33 = n28 + (n31 << 5 | n31 >>> 27) + ((n29 & n32) | (~n29 & n30)) + (this.f[13] + 1518500249);
        final int n34 = n29 << 30 | n29 >>> 2;
        final int n35 = n30 + (n33 << 5 | n33 >>> 27) + ((n31 & n34) | (~n31 & n32)) + (this.f[14] + 1518500249);
        final int n36 = n31 << 30 | n31 >>> 2;
        final int n37 = n32 + (n35 << 5 | n35 >>> 27) + ((n33 & n36) | (~n33 & n34)) + (this.f[15] + 1518500249);
        final int n38 = n33 << 30 | n33 >>> 2;
        final int n39 = n34 + (n37 << 5 | n37 >>> 27) + ((n35 & n38) | (~n35 & n36)) + (this.f[16] + 1518500249);
        final int n40 = n35 << 30 | n35 >>> 2;
        final int n41 = n36 + (n39 << 5 | n39 >>> 27) + ((n37 & n40) | (~n37 & n38)) + (this.f[17] + 1518500249);
        final int n42 = n37 << 30 | n37 >>> 2;
        final int n43 = n38 + (n41 << 5 | n41 >>> 27) + ((n39 & n42) | (~n39 & n40)) + (this.f[18] + 1518500249);
        final int n44 = n39 << 30 | n39 >>> 2;
        final int n45 = n40 + (n43 << 5 | n43 >>> 27) + ((n41 & n44) | (~n41 & n42)) + (this.f[19] + 1518500249);
        final int n46 = n41 << 30 | n41 >>> 2;
        final int n47 = n42 + (n45 << 5 | n45 >>> 27) + (n43 ^ n46 ^ n44) + (this.f[20] + 1859775393);
        final int n48 = n43 << 30 | n43 >>> 2;
        final int n49 = n44 + (n47 << 5 | n47 >>> 27) + (n45 ^ n48 ^ n46) + (this.f[21] + 1859775393);
        final int n50 = n45 << 30 | n45 >>> 2;
        final int n51 = n46 + (n49 << 5 | n49 >>> 27) + (n47 ^ n50 ^ n48) + (this.f[22] + 1859775393);
        final int n52 = n47 << 30 | n47 >>> 2;
        final int n53 = n48 + (n51 << 5 | n51 >>> 27) + (n49 ^ n52 ^ n50) + (this.f[23] + 1859775393);
        final int n54 = n49 << 30 | n49 >>> 2;
        final int n55 = n50 + (n53 << 5 | n53 >>> 27) + (n51 ^ n54 ^ n52) + (this.f[24] + 1859775393);
        final int n56 = n51 << 30 | n51 >>> 2;
        final int n57 = n52 + (n55 << 5 | n55 >>> 27) + (n53 ^ n56 ^ n54) + (this.f[25] + 1859775393);
        final int n58 = n53 << 30 | n53 >>> 2;
        final int n59 = n54 + (n57 << 5 | n57 >>> 27) + (n55 ^ n58 ^ n56) + (this.f[26] + 1859775393);
        final int n60 = n55 << 30 | n55 >>> 2;
        final int n61 = n56 + (n59 << 5 | n59 >>> 27) + (n57 ^ n60 ^ n58) + (this.f[27] + 1859775393);
        final int n62 = n57 << 30 | n57 >>> 2;
        final int n63 = n58 + (n61 << 5 | n61 >>> 27) + (n59 ^ n62 ^ n60) + (this.f[28] + 1859775393);
        final int n64 = n59 << 30 | n59 >>> 2;
        final int n65 = n60 + (n63 << 5 | n63 >>> 27) + (n61 ^ n64 ^ n62) + (this.f[29] + 1859775393);
        final int n66 = n61 << 30 | n61 >>> 2;
        final int n67 = n62 + (n65 << 5 | n65 >>> 27) + (n63 ^ n66 ^ n64) + (this.f[30] + 1859775393);
        final int n68 = n63 << 30 | n63 >>> 2;
        final int n69 = n64 + (n67 << 5 | n67 >>> 27) + (n65 ^ n68 ^ n66) + (this.f[31] + 1859775393);
        final int n70 = n65 << 30 | n65 >>> 2;
        final int n71 = n66 + (n69 << 5 | n69 >>> 27) + (n67 ^ n70 ^ n68) + (this.f[32] + 1859775393);
        final int n72 = n67 << 30 | n67 >>> 2;
        final int n73 = n68 + (n71 << 5 | n71 >>> 27) + (n69 ^ n72 ^ n70) + (this.f[33] + 1859775393);
        final int n74 = n69 << 30 | n69 >>> 2;
        final int n75 = n70 + (n73 << 5 | n73 >>> 27) + (n71 ^ n74 ^ n72) + (this.f[34] + 1859775393);
        final int n76 = n71 << 30 | n71 >>> 2;
        final int n77 = n72 + (n75 << 5 | n75 >>> 27) + (n73 ^ n76 ^ n74) + (this.f[35] + 1859775393);
        final int n78 = n73 << 30 | n73 >>> 2;
        final int n79 = n74 + (n77 << 5 | n77 >>> 27) + (n75 ^ n78 ^ n76) + (this.f[36] + 1859775393);
        final int n80 = n75 << 30 | n75 >>> 2;
        final int n81 = n76 + (n79 << 5 | n79 >>> 27) + (n77 ^ n80 ^ n78) + (this.f[37] + 1859775393);
        final int n82 = n77 << 30 | n77 >>> 2;
        final int n83 = n78 + (n81 << 5 | n81 >>> 27) + (n79 ^ n82 ^ n80) + (this.f[38] + 1859775393);
        final int n84 = n79 << 30 | n79 >>> 2;
        final int n85 = n80 + (n83 << 5 | n83 >>> 27) + (n81 ^ n84 ^ n82) + (this.f[39] + 1859775393);
        final int n86 = n81 << 30 | n81 >>> 2;
        final int n87 = n82 + (n85 << 5 | n85 >>> 27) + ((n83 & n86) | (n83 & n84) | (n86 & n84)) + (this.f[40] - 1894007588);
        final int n88 = n83 << 30 | n83 >>> 2;
        final int n89 = n84 + (n87 << 5 | n87 >>> 27) + ((n85 & n88) | (n85 & n86) | (n88 & n86)) + (this.f[41] - 1894007588);
        final int n90 = n85 << 30 | n85 >>> 2;
        final int n91 = n86 + (n89 << 5 | n89 >>> 27) + ((n87 & n90) | (n87 & n88) | (n90 & n88)) + (this.f[42] - 1894007588);
        final int n92 = n87 << 30 | n87 >>> 2;
        final int n93 = n88 + (n91 << 5 | n91 >>> 27) + ((n89 & n92) | (n89 & n90) | (n92 & n90)) + (this.f[43] - 1894007588);
        final int n94 = n89 << 30 | n89 >>> 2;
        final int n95 = n90 + (n93 << 5 | n93 >>> 27) + ((n91 & n94) | (n91 & n92) | (n94 & n92)) + (this.f[44] - 1894007588);
        final int n96 = n91 << 30 | n91 >>> 2;
        final int n97 = n92 + (n95 << 5 | n95 >>> 27) + ((n93 & n96) | (n93 & n94) | (n96 & n94)) + (this.f[45] - 1894007588);
        final int n98 = n93 << 30 | n93 >>> 2;
        final int n99 = n94 + (n97 << 5 | n97 >>> 27) + ((n95 & n98) | (n95 & n96) | (n98 & n96)) + (this.f[46] - 1894007588);
        final int n100 = n95 << 30 | n95 >>> 2;
        final int n101 = n96 + (n99 << 5 | n99 >>> 27) + ((n97 & n100) | (n97 & n98) | (n100 & n98)) + (this.f[47] - 1894007588);
        final int n102 = n97 << 30 | n97 >>> 2;
        final int n103 = n98 + (n101 << 5 | n101 >>> 27) + ((n99 & n102) | (n99 & n100) | (n102 & n100)) + (this.f[48] - 1894007588);
        final int n104 = n99 << 30 | n99 >>> 2;
        final int n105 = n100 + (n103 << 5 | n103 >>> 27) + ((n101 & n104) | (n101 & n102) | (n104 & n102)) + (this.f[49] - 1894007588);
        final int n106 = n101 << 30 | n101 >>> 2;
        final int n107 = n102 + (n105 << 5 | n105 >>> 27) + ((n103 & n106) | (n103 & n104) | (n106 & n104)) + (this.f[50] - 1894007588);
        final int n108 = n103 << 30 | n103 >>> 2;
        final int n109 = n104 + (n107 << 5 | n107 >>> 27) + ((n105 & n108) | (n105 & n106) | (n108 & n106)) + (this.f[51] - 1894007588);
        final int n110 = n105 << 30 | n105 >>> 2;
        final int n111 = n106 + (n109 << 5 | n109 >>> 27) + ((n107 & n110) | (n107 & n108) | (n110 & n108)) + (this.f[52] - 1894007588);
        final int n112 = n107 << 30 | n107 >>> 2;
        final int n113 = n108 + (n111 << 5 | n111 >>> 27) + ((n109 & n112) | (n109 & n110) | (n112 & n110)) + (this.f[53] - 1894007588);
        final int n114 = n109 << 30 | n109 >>> 2;
        final int n115 = n110 + (n113 << 5 | n113 >>> 27) + ((n111 & n114) | (n111 & n112) | (n114 & n112)) + (this.f[54] - 1894007588);
        final int n116 = n111 << 30 | n111 >>> 2;
        final int n117 = n112 + (n115 << 5 | n115 >>> 27) + ((n113 & n116) | (n113 & n114) | (n116 & n114)) + (this.f[55] - 1894007588);
        final int n118 = n113 << 30 | n113 >>> 2;
        final int n119 = n114 + (n117 << 5 | n117 >>> 27) + ((n115 & n118) | (n115 & n116) | (n118 & n116)) + (this.f[56] - 1894007588);
        final int n120 = n115 << 30 | n115 >>> 2;
        final int n121 = n116 + (n119 << 5 | n119 >>> 27) + ((n117 & n120) | (n117 & n118) | (n120 & n118)) + (this.f[57] - 1894007588);
        final int n122 = n117 << 30 | n117 >>> 2;
        final int n123 = n118 + (n121 << 5 | n121 >>> 27) + ((n119 & n122) | (n119 & n120) | (n122 & n120)) + (this.f[58] - 1894007588);
        final int n124 = n119 << 30 | n119 >>> 2;
        final int n125 = n120 + (n123 << 5 | n123 >>> 27) + ((n121 & n124) | (n121 & n122) | (n124 & n122)) + (this.f[59] - 1894007588);
        final int n126 = n121 << 30 | n121 >>> 2;
        final int n127 = n122 + (n125 << 5 | n125 >>> 27) + (n123 ^ n126 ^ n124) + (this.f[60] - 899497514);
        final int n128 = n123 << 30 | n123 >>> 2;
        final int n129 = n124 + (n127 << 5 | n127 >>> 27) + (n125 ^ n128 ^ n126) + (this.f[61] - 899497514);
        final int n130 = n125 << 30 | n125 >>> 2;
        final int n131 = n126 + (n129 << 5 | n129 >>> 27) + (n127 ^ n130 ^ n128) + (this.f[62] - 899497514);
        final int n132 = n127 << 30 | n127 >>> 2;
        final int n133 = n128 + (n131 << 5 | n131 >>> 27) + (n129 ^ n132 ^ n130) + (this.f[63] - 899497514);
        final int n134 = n129 << 30 | n129 >>> 2;
        final int n135 = n130 + (n133 << 5 | n133 >>> 27) + (n131 ^ n134 ^ n132) + (this.f[64] - 899497514);
        final int n136 = n131 << 30 | n131 >>> 2;
        final int n137 = n132 + (n135 << 5 | n135 >>> 27) + (n133 ^ n136 ^ n134) + (this.f[65] - 899497514);
        final int n138 = n133 << 30 | n133 >>> 2;
        final int n139 = n134 + (n137 << 5 | n137 >>> 27) + (n135 ^ n138 ^ n136) + (this.f[66] - 899497514);
        final int n140 = n135 << 30 | n135 >>> 2;
        final int n141 = n136 + (n139 << 5 | n139 >>> 27) + (n137 ^ n140 ^ n138) + (this.f[67] - 899497514);
        final int n142 = n137 << 30 | n137 >>> 2;
        final int n143 = n138 + (n141 << 5 | n141 >>> 27) + (n139 ^ n142 ^ n140) + (this.f[68] - 899497514);
        final int n144 = n139 << 30 | n139 >>> 2;
        final int n145 = n140 + (n143 << 5 | n143 >>> 27) + (n141 ^ n144 ^ n142) + (this.f[69] - 899497514);
        final int n146 = n141 << 30 | n141 >>> 2;
        final int n147 = n142 + (n145 << 5 | n145 >>> 27) + (n143 ^ n146 ^ n144) + (this.f[70] - 899497514);
        final int n148 = n143 << 30 | n143 >>> 2;
        final int n149 = n144 + (n147 << 5 | n147 >>> 27) + (n145 ^ n148 ^ n146) + (this.f[71] - 899497514);
        final int n150 = n145 << 30 | n145 >>> 2;
        final int n151 = n146 + (n149 << 5 | n149 >>> 27) + (n147 ^ n150 ^ n148) + (this.f[72] - 899497514);
        final int n152 = n147 << 30 | n147 >>> 2;
        final int n153 = n148 + (n151 << 5 | n151 >>> 27) + (n149 ^ n152 ^ n150) + (this.f[73] - 899497514);
        final int n154 = n149 << 30 | n149 >>> 2;
        final int n155 = n150 + (n153 << 5 | n153 >>> 27) + (n151 ^ n154 ^ n152) + (this.f[74] - 899497514);
        final int n156 = n151 << 30 | n151 >>> 2;
        final int n157 = n152 + (n155 << 5 | n155 >>> 27) + (n153 ^ n156 ^ n154) + (this.f[75] - 899497514);
        final int n158 = n153 << 30 | n153 >>> 2;
        final int n159 = n154 + (n157 << 5 | n157 >>> 27) + (n155 ^ n158 ^ n156) + (this.f[76] - 899497514);
        final int n160 = n155 << 30 | n155 >>> 2;
        final int n161 = n156 + (n159 << 5 | n159 >>> 27) + (n157 ^ n160 ^ n158) + (this.f[77] - 899497514);
        final int n162 = n157 << 30 | n157 >>> 2;
        final int n163 = n158 + (n161 << 5 | n161 >>> 27) + (n159 ^ n162 ^ n160) + (this.f[78] - 899497514);
        final int n164 = n159 << 30 | n159 >>> 2;
        final int n165 = n160 + (n163 << 5 | n163 >>> 27) + (n161 ^ n164 ^ n162) + (this.f[79] - 899497514);
        final int n166 = n161 << 30 | n161 >>> 2;
        this.a += n165;
        this.b += n163;
        this.c += n166;
        this.d += n164;
        this.e += n162;
    }
    
    public void b(final byte[] array, int n) {
        array[n] = (byte)(this.a >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.a & 0xFF);
        ++n;
        array[n] = (byte)(this.b >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.b & 0xFF);
        ++n;
        array[n] = (byte)(this.c >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.c & 0xFF);
        ++n;
        array[n] = (byte)(this.d >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.d & 0xFF);
        ++n;
        array[n] = (byte)(this.e >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.e & 0xFF);
        ++n;
    }
    
    public void g() {
        for (int i = 0; i < this.h.length; ++i) {
            this.h[i] = 0;
        }
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        for (int j = 0; j < this.f.length; ++j) {
            this.f[j] = 0;
        }
        this.j = 1;
    }
    
    public void finalize() {
        this.g();
    }
    
    static {
        i = new byte[] { 48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20 };
    }
}
