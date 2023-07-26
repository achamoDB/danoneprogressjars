import java.security.SecureRandom;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ko extends psc_an implements psc_dl, Cloneable, Serializable
{
    private int[] a;
    private transient psc_dd b;
    private int c;
    public static final int d = 8;
    
    public psc_ko() {
        this.a = new int[64];
        this.c = 128;
    }
    
    public psc_ko(final int[] array) throws psc_be {
        this.a = new int[64];
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        int c;
        if (array == null || array.length == 0) {
            c = 128;
        }
        else {
            if (array.length != 1) {
                throw new psc_be("Incorrect number of algorithm parameters:expected 1, effectiveKeyBits.");
            }
            c = array[0];
        }
        if (c < 1 || c > 1024) {
            throw new psc_be("Effective key bits should be between 1 and 1024.");
        }
        this.c = c;
    }
    
    public int[] c() {
        return new int[] { this.c };
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_ls.a(this, array, n, n2, psc_di, psc_dm);
    }
    
    public byte[] a(final byte[] array) {
        return psc_ls.a(this.c, array);
    }
    
    public String d() {
        return "RC2";
    }
    
    public int g() {
        return 8;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int c) {
        if (c < 1 || c > 1024) {
            this.c = 128;
        }
        this.c = c;
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
        final psc_ko psc_ko = new psc_ko();
        psc_ko.a = (int[])psc_au.a(this.a, this.b);
        psc_ko.b = psc_au.a(psc_ko.a);
        psc_ko.c = this.c;
        return psc_ko;
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    private void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        byte[] a;
        try {
            a = ((psc_j6)psc_dc).a("Clear");
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        final byte[] array = new byte[128];
        if (a != null) {
            System.arraycopy(a, 0, array, 0, a.length);
        }
        for (int i = a.length; i < 128; ++i) {
            array[i] = psc_n3.a[array[i - 1] + array[i - a.length] & 0xFF];
        }
        final int n = (this.c + 7) / 8;
        array[128 - n] = psc_n3.a[array[128 - n] & 255 >>> 7 - (this.c + 7) % 8];
        for (int j = 127 - n; j >= 0; --j) {
            array[j] = psc_n3.a[(array[j + 1] ^ array[j + n]) & 0xFF];
        }
        if (this.b != null) {
            this.b.e();
        }
        for (int k = 0; k < 64; ++k) {
            this.a[k] = (array[2 * k + 1] << 8 & 0xFFFF) + (array[k * 2] & 0xFF);
        }
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        for (int l = 0; l < a.length; ++l) {
            a[l] = 0;
        }
        for (int n2 = 0; n2 < array.length; ++n2) {
            array[n2] = 0;
        }
    }
    
    public int a(final byte[] array, int n, final byte[] array2, final int n2) {
        final int n3 = array[n] & 0xFF;
        ++n;
        final int n4 = n3 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n5 = array[n] & 0xFF;
        ++n;
        final int n6 = n5 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n7 = array[n] & 0xFF;
        ++n;
        final int n8 = n7 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n9 = array[n] & 0xFF;
        ++n;
        final int n10 = n9 + ((array[n] & 0xFF) << 8);
        final int n11 = (n10 & n8) + (~n10 & n6) + (this.a[0] + n4);
        final int n12 = n11 << 1 | (n11 & 0xFFFF) >>> 15;
        final int n13 = (n12 & n10) + (~n12 & n8) + (this.a[1] + n6);
        final int n14 = n13 << 2 | (n13 & 0xFFFF) >>> 14;
        final int n15 = (n14 & n12) + (~n14 & n10) + (this.a[2] + n8);
        final int n16 = n15 << 3 | (n15 & 0xFFFF) >>> 13;
        final int n17 = (n16 & n14) + (~n16 & n12) + (this.a[3] + n10);
        final int n18 = n17 << 5 | (n17 & 0xFFFF) >>> 11;
        final int n19 = (n18 & n16) + (~n18 & n14) + (this.a[4] + n12);
        final int n20 = n19 << 1 | (n19 & 0xFFFF) >>> 15;
        final int n21 = (n20 & n18) + (~n20 & n16) + (this.a[5] + n14);
        final int n22 = n21 << 2 | (n21 & 0xFFFF) >>> 14;
        final int n23 = (n22 & n20) + (~n22 & n18) + (this.a[6] + n16);
        final int n24 = n23 << 3 | (n23 & 0xFFFF) >>> 13;
        final int n25 = (n24 & n22) + (~n24 & n20) + (this.a[7] + n18);
        final int n26 = n25 << 5 | (n25 & 0xFFFF) >>> 11;
        final int n27 = (n26 & n24) + (~n26 & n22) + (this.a[8] + n20);
        final int n28 = n27 << 1 | (n27 & 0xFFFF) >>> 15;
        final int n29 = (n28 & n26) + (~n28 & n24) + (this.a[9] + n22);
        final int n30 = n29 << 2 | (n29 & 0xFFFF) >>> 14;
        final int n31 = (n30 & n28) + (~n30 & n26) + (this.a[10] + n24);
        final int n32 = n31 << 3 | (n31 & 0xFFFF) >>> 13;
        final int n33 = (n32 & n30) + (~n32 & n28) + (this.a[11] + n26);
        final int n34 = n33 << 5 | (n33 & 0xFFFF) >>> 11;
        final int n35 = (n34 & n32) + (~n34 & n30) + (this.a[12] + n28);
        final int n36 = n35 << 1 | (n35 & 0xFFFF) >>> 15;
        final int n37 = (n36 & n34) + (~n36 & n32) + (this.a[13] + n30);
        final int n38 = n37 << 2 | (n37 & 0xFFFF) >>> 14;
        final int n39 = (n38 & n36) + (~n38 & n34) + (this.a[14] + n32);
        final int n40 = n39 << 3 | (n39 & 0xFFFF) >>> 13;
        final int n41 = (n40 & n38) + (~n40 & n36) + (this.a[15] + n34);
        final int n42 = n41 << 5 | (n41 & 0xFFFF) >>> 11;
        final int n43 = (n42 & n40) + (~n42 & n38) + (this.a[16] + n36);
        final int n44 = n43 << 1 | (n43 & 0xFFFF) >>> 15;
        final int n45 = (n44 & n42) + (~n44 & n40) + (this.a[17] + n38);
        final int n46 = n45 << 2 | (n45 & 0xFFFF) >>> 14;
        final int n47 = (n46 & n44) + (~n46 & n42) + (this.a[18] + n40);
        final int n48 = n47 << 3 | (n47 & 0xFFFF) >>> 13;
        final int n49 = (n48 & n46) + (~n48 & n44) + (this.a[19] + n42);
        final int n50 = n49 << 5 | (n49 & 0xFFFF) >>> 11;
        final int n51 = n44 + this.a[n50 & 0x3F];
        final int n52 = n46 + this.a[n51 & 0x3F];
        final int n53 = n48 + this.a[n52 & 0x3F];
        final int n54 = n50 + this.a[n53 & 0x3F];
        final int n55 = (n54 & n53) + (~n54 & n52) + (this.a[20] + n51);
        final int n56 = n55 << 1 | (n55 & 0xFFFF) >>> 15;
        final int n57 = (n56 & n54) + (~n56 & n53) + (this.a[21] + n52);
        final int n58 = n57 << 2 | (n57 & 0xFFFF) >>> 14;
        final int n59 = (n58 & n56) + (~n58 & n54) + (this.a[22] + n53);
        final int n60 = n59 << 3 | (n59 & 0xFFFF) >>> 13;
        final int n61 = (n60 & n58) + (~n60 & n56) + (this.a[23] + n54);
        final int n62 = n61 << 5 | (n61 & 0xFFFF) >>> 11;
        final int n63 = (n62 & n60) + (~n62 & n58) + (this.a[24] + n56);
        final int n64 = n63 << 1 | (n63 & 0xFFFF) >>> 15;
        final int n65 = (n64 & n62) + (~n64 & n60) + (this.a[25] + n58);
        final int n66 = n65 << 2 | (n65 & 0xFFFF) >>> 14;
        final int n67 = (n66 & n64) + (~n66 & n62) + (this.a[26] + n60);
        final int n68 = n67 << 3 | (n67 & 0xFFFF) >>> 13;
        final int n69 = (n68 & n66) + (~n68 & n64) + (this.a[27] + n62);
        final int n70 = n69 << 5 | (n69 & 0xFFFF) >>> 11;
        final int n71 = (n70 & n68) + (~n70 & n66) + (this.a[28] + n64);
        final int n72 = n71 << 1 | (n71 & 0xFFFF) >>> 15;
        final int n73 = (n72 & n70) + (~n72 & n68) + (this.a[29] + n66);
        final int n74 = n73 << 2 | (n73 & 0xFFFF) >>> 14;
        final int n75 = (n74 & n72) + (~n74 & n70) + (this.a[30] + n68);
        final int n76 = n75 << 3 | (n75 & 0xFFFF) >>> 13;
        final int n77 = (n76 & n74) + (~n76 & n72) + (this.a[31] + n70);
        final int n78 = n77 << 5 | (n77 & 0xFFFF) >>> 11;
        final int n79 = (n78 & n76) + (~n78 & n74) + (this.a[32] + n72);
        final int n80 = n79 << 1 | (n79 & 0xFFFF) >>> 15;
        final int n81 = (n80 & n78) + (~n80 & n76) + (this.a[33] + n74);
        final int n82 = n81 << 2 | (n81 & 0xFFFF) >>> 14;
        final int n83 = (n82 & n80) + (~n82 & n78) + (this.a[34] + n76);
        final int n84 = n83 << 3 | (n83 & 0xFFFF) >>> 13;
        final int n85 = (n84 & n82) + (~n84 & n80) + (this.a[35] + n78);
        final int n86 = n85 << 5 | (n85 & 0xFFFF) >>> 11;
        final int n87 = (n86 & n84) + (~n86 & n82) + (this.a[36] + n80);
        final int n88 = n87 << 1 | (n87 & 0xFFFF) >>> 15;
        final int n89 = (n88 & n86) + (~n88 & n84) + (this.a[37] + n82);
        final int n90 = n89 << 2 | (n89 & 0xFFFF) >>> 14;
        final int n91 = (n90 & n88) + (~n90 & n86) + (this.a[38] + n84);
        final int n92 = n91 << 3 | (n91 & 0xFFFF) >>> 13;
        final int n93 = (n92 & n90) + (~n92 & n88) + (this.a[39] + n86);
        final int n94 = n93 << 5 | (n93 & 0xFFFF) >>> 11;
        final int n95 = (n94 & n92) + (~n94 & n90) + (this.a[40] + n88);
        final int n96 = n95 << 1 | (n95 & 0xFFFF) >>> 15;
        final int n97 = (n96 & n94) + (~n96 & n92) + (this.a[41] + n90);
        final int n98 = n97 << 2 | (n97 & 0xFFFF) >>> 14;
        final int n99 = (n98 & n96) + (~n98 & n94) + (this.a[42] + n92);
        final int n100 = n99 << 3 | (n99 & 0xFFFF) >>> 13;
        final int n101 = (n100 & n98) + (~n100 & n96) + (this.a[43] + n94);
        final int n102 = n101 << 5 | (n101 & 0xFFFF) >>> 11;
        final int n103 = n96 + this.a[n102 & 0x3F];
        final int n104 = n98 + this.a[n103 & 0x3F];
        final int n105 = n100 + this.a[n104 & 0x3F];
        final int n106 = n102 + this.a[n105 & 0x3F];
        final int n107 = (n106 & n105) + (~n106 & n104) + (this.a[44] + n103);
        final int n108 = n107 << 1 | (n107 & 0xFFFF) >>> 15;
        final int n109 = (n108 & n106) + (~n108 & n105) + (this.a[45] + n104);
        final int n110 = n109 << 2 | (n109 & 0xFFFF) >>> 14;
        final int n111 = (n110 & n108) + (~n110 & n106) + (this.a[46] + n105);
        final int n112 = n111 << 3 | (n111 & 0xFFFF) >>> 13;
        final int n113 = (n112 & n110) + (~n112 & n108) + (this.a[47] + n106);
        final int n114 = n113 << 5 | (n113 & 0xFFFF) >>> 11;
        final int n115 = (n114 & n112) + (~n114 & n110) + (this.a[48] + n108);
        final int n116 = n115 << 1 | (n115 & 0xFFFF) >>> 15;
        final int n117 = (n116 & n114) + (~n116 & n112) + (this.a[49] + n110);
        final int n118 = n117 << 2 | (n117 & 0xFFFF) >>> 14;
        final int n119 = (n118 & n116) + (~n118 & n114) + (this.a[50] + n112);
        final int n120 = n119 << 3 | (n119 & 0xFFFF) >>> 13;
        final int n121 = (n120 & n118) + (~n120 & n116) + (this.a[51] + n114);
        final int n122 = n121 << 5 | (n121 & 0xFFFF) >>> 11;
        final int n123 = (n122 & n120) + (~n122 & n118) + (this.a[52] + n116);
        final int n124 = n123 << 1 | (n123 & 0xFFFF) >>> 15;
        final int n125 = (n124 & n122) + (~n124 & n120) + (this.a[53] + n118);
        final int n126 = n125 << 2 | (n125 & 0xFFFF) >>> 14;
        final int n127 = (n126 & n124) + (~n126 & n122) + (this.a[54] + n120);
        final int n128 = n127 << 3 | (n127 & 0xFFFF) >>> 13;
        final int n129 = (n128 & n126) + (~n128 & n124) + (this.a[55] + n122);
        final int n130 = n129 << 5 | (n129 & 0xFFFF) >>> 11;
        final int n131 = (n130 & n128) + (~n130 & n126) + (this.a[56] + n124);
        final int n132 = n131 << 1 | (n131 & 0xFFFF) >>> 15;
        final int n133 = (n132 & n130) + (~n132 & n128) + (this.a[57] + n126);
        final int n134 = n133 << 2 | (n133 & 0xFFFF) >>> 14;
        final int n135 = (n134 & n132) + (~n134 & n130) + (this.a[58] + n128);
        final int n136 = n135 << 3 | (n135 & 0xFFFF) >>> 13;
        final int n137 = (n136 & n134) + (~n136 & n132) + (this.a[59] + n130);
        final int n138 = n137 << 5 | (n137 & 0xFFFF) >>> 11;
        final int n139 = (n138 & n136) + (~n138 & n134) + (this.a[60] + n132);
        final int n140 = n139 << 1 | (n139 & 0xFFFF) >>> 15;
        final int n141 = (n140 & n138) + (~n140 & n136) + (this.a[61] + n134);
        final int n142 = n141 << 2 | (n141 & 0xFFFF) >>> 14;
        final int n143 = (n142 & n140) + (~n142 & n138) + (this.a[62] + n136);
        final int n144 = n143 << 3 | (n143 & 0xFFFF) >>> 13;
        final int n145 = (n144 & n142) + (~n144 & n140) + (this.a[63] + n138);
        final int n146 = n145 << 5 | (n145 & 0xFFFF) >>> 11;
        int n147 = n2;
        array2[n147] = (byte)(n140 & 0xFF);
        ++n147;
        array2[n147] = (byte)((n140 & 0xFF00) >>> 8);
        ++n147;
        array2[n147] = (byte)(n142 & 0xFF);
        ++n147;
        array2[n147] = (byte)((n142 & 0xFF00) >>> 8);
        ++n147;
        array2[n147] = (byte)(n144 & 0xFF);
        ++n147;
        array2[n147] = (byte)((n144 & 0xFF00) >>> 8);
        ++n147;
        array2[n147] = (byte)(n146 & 0xFF);
        ++n147;
        array2[n147] = (byte)((n146 & 0xFF00) >>> 8);
        return 8;
    }
    
    public int b(final byte[] array, int n, final byte[] array2, final int n2) {
        final int n3 = array[n] & 0xFF;
        ++n;
        final int n4 = n3 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n5 = array[n] & 0xFF;
        ++n;
        final int n6 = n5 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n7 = array[n] & 0xFF;
        ++n;
        final int n8 = n7 + ((array[n] & 0xFF) << 8);
        ++n;
        final int n9 = array[n] & 0xFF;
        ++n;
        final int n10 = n9 + ((array[n] & 0xFF) << 8);
        final int n11 = ((n10 & 0xFFFF) >>> 5 | n10 << 11) - (this.a[63] + (n8 & n6) + (~n8 & n4));
        final int n12 = ((n8 & 0xFFFF) >>> 3 | n8 << 13) - (this.a[62] + (n6 & n4) + (~n6 & n11));
        final int n13 = ((n6 & 0xFFFF) >>> 2 | n6 << 14) - (this.a[61] + (n4 & n11) + (~n4 & n12));
        final int n14 = ((n4 & 0xFFFF) >>> 1 | n4 << 15) - (this.a[60] + (n11 & n12) + (~n11 & n13));
        final int n15 = ((n11 & 0xFFFF) >>> 5 | n11 << 11) - (this.a[59] + (n12 & n13) + (~n12 & n14));
        final int n16 = ((n12 & 0xFFFF) >>> 3 | n12 << 13) - (this.a[58] + (n13 & n14) + (~n13 & n15));
        final int n17 = ((n13 & 0xFFFF) >>> 2 | n13 << 14) - (this.a[57] + (n14 & n15) + (~n14 & n16));
        final int n18 = ((n14 & 0xFFFF) >>> 1 | n14 << 15) - (this.a[56] + (n15 & n16) + (~n15 & n17));
        final int n19 = ((n15 & 0xFFFF) >>> 5 | n15 << 11) - (this.a[55] + (n16 & n17) + (~n16 & n18));
        final int n20 = ((n16 & 0xFFFF) >>> 3 | n16 << 13) - (this.a[54] + (n17 & n18) + (~n17 & n19));
        final int n21 = ((n17 & 0xFFFF) >>> 2 | n17 << 14) - (this.a[53] + (n18 & n19) + (~n18 & n20));
        final int n22 = ((n18 & 0xFFFF) >>> 1 | n18 << 15) - (this.a[52] + (n19 & n20) + (~n19 & n21));
        final int n23 = ((n19 & 0xFFFF) >>> 5 | n19 << 11) - (this.a[51] + (n20 & n21) + (~n20 & n22));
        final int n24 = ((n20 & 0xFFFF) >>> 3 | n20 << 13) - (this.a[50] + (n21 & n22) + (~n21 & n23));
        final int n25 = ((n21 & 0xFFFF) >>> 2 | n21 << 14) - (this.a[49] + (n22 & n23) + (~n22 & n24));
        final int n26 = ((n22 & 0xFFFF) >>> 1 | n22 << 15) - (this.a[48] + (n23 & n24) + (~n23 & n25));
        final int n27 = ((n23 & 0xFFFF) >>> 5 | n23 << 11) - (this.a[47] + (n24 & n25) + (~n24 & n26));
        final int n28 = ((n24 & 0xFFFF) >>> 3 | n24 << 13) - (this.a[46] + (n25 & n26) + (~n25 & n27));
        final int n29 = ((n25 & 0xFFFF) >>> 2 | n25 << 14) - (this.a[45] + (n26 & n27) + (~n26 & n28));
        final int n30 = ((n26 & 0xFFFF) >>> 1 | n26 << 15) - (this.a[44] + (n27 & n28) + (~n27 & n29));
        final int n31 = n27 - this.a[n28 & 0x3F];
        final int n32 = n28 - this.a[n29 & 0x3F];
        final int n33 = n29 - this.a[n30 & 0x3F];
        final int n34 = n30 - this.a[n31 & 0x3F];
        final int n35 = ((n31 & 0xFFFF) >>> 5 | n31 << 11) - (this.a[43] + (n32 & n33) + (~n32 & n34));
        final int n36 = ((n32 & 0xFFFF) >>> 3 | n32 << 13) - (this.a[42] + (n33 & n34) + (~n33 & n35));
        final int n37 = ((n33 & 0xFFFF) >>> 2 | n33 << 14) - (this.a[41] + (n34 & n35) + (~n34 & n36));
        final int n38 = ((n34 & 0xFFFF) >>> 1 | n34 << 15) - (this.a[40] + (n35 & n36) + (~n35 & n37));
        final int n39 = ((n35 & 0xFFFF) >>> 5 | n35 << 11) - (this.a[39] + (n36 & n37) + (~n36 & n38));
        final int n40 = ((n36 & 0xFFFF) >>> 3 | n36 << 13) - (this.a[38] + (n37 & n38) + (~n37 & n39));
        final int n41 = ((n37 & 0xFFFF) >>> 2 | n37 << 14) - (this.a[37] + (n38 & n39) + (~n38 & n40));
        final int n42 = ((n38 & 0xFFFF) >>> 1 | n38 << 15) - (this.a[36] + (n39 & n40) + (~n39 & n41));
        final int n43 = ((n39 & 0xFFFF) >>> 5 | n39 << 11) - (this.a[35] + (n40 & n41) + (~n40 & n42));
        final int n44 = ((n40 & 0xFFFF) >>> 3 | n40 << 13) - (this.a[34] + (n41 & n42) + (~n41 & n43));
        final int n45 = ((n41 & 0xFFFF) >>> 2 | n41 << 14) - (this.a[33] + (n42 & n43) + (~n42 & n44));
        final int n46 = ((n42 & 0xFFFF) >>> 1 | n42 << 15) - (this.a[32] + (n43 & n44) + (~n43 & n45));
        final int n47 = ((n43 & 0xFFFF) >>> 5 | n43 << 11) - (this.a[31] + (n44 & n45) + (~n44 & n46));
        final int n48 = ((n44 & 0xFFFF) >>> 3 | n44 << 13) - (this.a[30] + (n45 & n46) + (~n45 & n47));
        final int n49 = ((n45 & 0xFFFF) >>> 2 | n45 << 14) - (this.a[29] + (n46 & n47) + (~n46 & n48));
        final int n50 = ((n46 & 0xFFFF) >>> 1 | n46 << 15) - (this.a[28] + (n47 & n48) + (~n47 & n49));
        final int n51 = ((n47 & 0xFFFF) >>> 5 | n47 << 11) - (this.a[27] + (n48 & n49) + (~n48 & n50));
        final int n52 = ((n48 & 0xFFFF) >>> 3 | n48 << 13) - (this.a[26] + (n49 & n50) + (~n49 & n51));
        final int n53 = ((n49 & 0xFFFF) >>> 2 | n49 << 14) - (this.a[25] + (n50 & n51) + (~n50 & n52));
        final int n54 = ((n50 & 0xFFFF) >>> 1 | n50 << 15) - (this.a[24] + (n51 & n52) + (~n51 & n53));
        final int n55 = ((n51 & 0xFFFF) >>> 5 | n51 << 11) - (this.a[23] + (n52 & n53) + (~n52 & n54));
        final int n56 = ((n52 & 0xFFFF) >>> 3 | n52 << 13) - (this.a[22] + (n53 & n54) + (~n53 & n55));
        final int n57 = ((n53 & 0xFFFF) >>> 2 | n53 << 14) - (this.a[21] + (n54 & n55) + (~n54 & n56));
        final int n58 = ((n54 & 0xFFFF) >>> 1 | n54 << 15) - (this.a[20] + (n55 & n56) + (~n55 & n57));
        final int n59 = n55 - this.a[n56 & 0x3F];
        final int n60 = n56 - this.a[n57 & 0x3F];
        final int n61 = n57 - this.a[n58 & 0x3F];
        final int n62 = n58 - this.a[n59 & 0x3F];
        final int n63 = ((n59 & 0xFFFF) >>> 5 | n59 << 11) - (this.a[19] + (n60 & n61) + (~n60 & n62));
        final int n64 = ((n60 & 0xFFFF) >>> 3 | n60 << 13) - (this.a[18] + (n61 & n62) + (~n61 & n63));
        final int n65 = ((n61 & 0xFFFF) >>> 2 | n61 << 14) - (this.a[17] + (n62 & n63) + (~n62 & n64));
        final int n66 = ((n62 & 0xFFFF) >>> 1 | n62 << 15) - (this.a[16] + (n63 & n64) + (~n63 & n65));
        final int n67 = ((n63 & 0xFFFF) >>> 5 | n63 << 11) - (this.a[15] + (n64 & n65) + (~n64 & n66));
        final int n68 = ((n64 & 0xFFFF) >>> 3 | n64 << 13) - (this.a[14] + (n65 & n66) + (~n65 & n67));
        final int n69 = ((n65 & 0xFFFF) >>> 2 | n65 << 14) - (this.a[13] + (n66 & n67) + (~n66 & n68));
        final int n70 = ((n66 & 0xFFFF) >>> 1 | n66 << 15) - (this.a[12] + (n67 & n68) + (~n67 & n69));
        final int n71 = ((n67 & 0xFFFF) >>> 5 | n67 << 11) - (this.a[11] + (n68 & n69) + (~n68 & n70));
        final int n72 = ((n68 & 0xFFFF) >>> 3 | n68 << 13) - (this.a[10] + (n69 & n70) + (~n69 & n71));
        final int n73 = ((n69 & 0xFFFF) >>> 2 | n69 << 14) - (this.a[9] + (n70 & n71) + (~n70 & n72));
        final int n74 = ((n70 & 0xFFFF) >>> 1 | n70 << 15) - (this.a[8] + (n71 & n72) + (~n71 & n73));
        final int n75 = ((n71 & 0xFFFF) >>> 5 | n71 << 11) - (this.a[7] + (n72 & n73) + (~n72 & n74));
        final int n76 = ((n72 & 0xFFFF) >>> 3 | n72 << 13) - (this.a[6] + (n73 & n74) + (~n73 & n75));
        final int n77 = ((n73 & 0xFFFF) >>> 2 | n73 << 14) - (this.a[5] + (n74 & n75) + (~n74 & n76));
        final int n78 = ((n74 & 0xFFFF) >>> 1 | n74 << 15) - (this.a[4] + (n75 & n76) + (~n75 & n77));
        final int n79 = ((n75 & 0xFFFF) >>> 5 | n75 << 11) - (this.a[3] + (n76 & n77) + (~n76 & n78));
        final int n80 = ((n76 & 0xFFFF) >>> 3 | n76 << 13) - (this.a[2] + (n77 & n78) + (~n77 & n79));
        final int n81 = ((n77 & 0xFFFF) >>> 2 | n77 << 14) - (this.a[1] + (n78 & n79) + (~n78 & n80));
        final int n82 = ((n78 & 0xFFFF) >>> 1 | n78 << 15) - (this.a[0] + (n79 & n80) + (~n79 & n81));
        int n83 = n2;
        array2[n83] = (byte)(n82 & 0xFF);
        ++n83;
        array2[n83] = (byte)((n82 & 0xFF00) >>> 8);
        ++n83;
        array2[n83] = (byte)(n81 & 0xFF);
        ++n83;
        array2[n83] = (byte)((n81 & 0xFF00) >>> 8);
        ++n83;
        array2[n83] = (byte)(n80 & 0xFF);
        ++n83;
        array2[n83] = (byte)((n80 & 0xFF00) >>> 8);
        ++n83;
        array2[n83] = (byte)(n79 & 0xFF);
        ++n83;
        array2[n83] = (byte)((n79 & 0xFF00) >>> 8);
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
}
