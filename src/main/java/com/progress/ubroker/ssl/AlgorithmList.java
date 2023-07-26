// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.io.IOException;
import java.util.HashMap;

public class AlgorithmList
{
    public static final String EDH_DSS_RC4_SHA = "EDH-DSS-RC4-SHA";
    public static final String EDH_RSA_DES_CBC3_SHA = "EDH-RSA-DES-CBC3-SHA";
    public static final String EDH_DSS_DES_CBC3_SHA = "EDH-DSS-DES-CBC3-SHA";
    public static final String DES_CBC3_SHA = "DES-CBC3-SHA";
    public static final String RC4_SHA = "RC4-SHA";
    public static final String RC4_MD5 = "RC4-MD5";
    public static final String EDH_RSA_DES_CBC_SHA = "EDH-RSA-DES-CBC-SHA";
    public static final String EDH_DSS_DES_CBC_SHA = "EDH-DSS-DES-CBC-SHA";
    public static final String DES_CBC_SHA = "DES-CBC-SHA";
    public static final String DES_CBC3_MD5 = "DES-CBC3-MD5";
    public static final String RC2_CBC_MD5 = "RC2-CBC-MD5";
    public static final String DES_CBC_MD5 = "DES-CBC-MD5";
    public static final String EXP_EDH_DSS_DES_56_SHA = "EXP-EDH-DSS-DES-56-SHA";
    public static final String EXP_EDH_DSS_RC4_56_SHA = "EXP-EDH-DSS-RC4-56-SHA";
    public static final String EXP_DES_56_SHA = "EXP-DES-56-SHA";
    public static final String EXP_RC4_56_SHA = "EXP-RC4-56-SHA";
    public static final String EXP_EDH_RSA_DES_CBC_SHA = "EXP-EDH-RSA-DES-CBC-SHA";
    public static final String EXP_EDH_DSS_DES_CBC_SHA = "EXP-EDH-DSS-DES-CBC-SHA";
    public static final String EXP_DES_CBC_SHA = "EXP-DES-CBC-SHA";
    public static final String EXP_RC2_CBC_MD5 = "EXP-RC2-CBC-MD5";
    public static final String EXP_RC4_MD5 = "EXP-RC4-MD5";
    public static final String EXP_RC4_64_MD5 = "EXP-RC4-64-MD5";
    public static final String DEFAULT_LIST = "RC4-MD5:RC4-SHA";
    private static final String a = ":";
    private static HashMap b;
    private String[] c;
    
    public AlgorithmList() throws IOException {
        this("RC4-MD5:RC4-SHA");
    }
    
    public AlgorithmList(final String s) throws IOException {
        if (s == null) {
            throw new IOException("Illegal SSL algorithm: null");
        }
        if (s.length() == 0) {
            throw new IOException("Illegal SSL algorithm: <empty>");
        }
        this.c = s.split(":");
        this.a();
    }
    
    public psc_dw[] getCipherSuites() {
        final psc_dw[] array = new psc_dw[this.c.length];
        for (int i = 0; i < this.c.length; ++i) {
            array[i] = this.a(this.c[i]);
        }
        return array;
    }
    
    private psc_dw a(final String key) {
        return AlgorithmList.b.get(key);
    }
    
    private void a() throws IOException {
        for (int i = 0; i < this.c.length; ++i) {
            if (!AlgorithmList.b.containsKey(this.c[i])) {
                throw new IOException("Unsupported SSL cipher suite name:" + this.c[i]);
            }
        }
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.c[0]);
        for (int i = 1; i < this.c.length; ++i) {
            sb.append(":");
            sb.append(this.c[i]);
        }
        return sb.toString();
    }
    
    static {
        (AlgorithmList.b = new HashMap()).put("EDH-RSA-DES-CBC3-SHA", new psc_ma());
        AlgorithmList.b.put("EDH-DSS-DES-CBC3-SHA", new psc_md());
        AlgorithmList.b.put("DES-CBC3-SHA", new psc_l4());
        AlgorithmList.b.put("RC4-SHA", new psc_l1());
        AlgorithmList.b.put("RC4-MD5", new psc_du());
        AlgorithmList.b.put("EDH-RSA-DES-CBC-SHA", new psc_mb());
        AlgorithmList.b.put("EDH-DSS-DES-CBC-SHA", new psc_me());
        AlgorithmList.b.put("DES-CBC-SHA", new psc_l6());
        AlgorithmList.b.put("EXP-EDH-DSS-DES-56-SHA", new psc_me());
        AlgorithmList.b.put("EXP-DES-56-SHA", new psc_mh());
        AlgorithmList.b.put("EXP-EDH-RSA-DES-CBC-SHA", new psc_mc());
        AlgorithmList.b.put("EXP-EDH-DSS-DES-CBC-SHA", new psc_mf());
        AlgorithmList.b.put("EXP-DES-CBC-SHA", new psc_l7());
        AlgorithmList.b.put("DES-CBC3-MD5", new psc_l3());
        AlgorithmList.b.put("EXP-RC2-CBC-MD5", new psc_l9());
        AlgorithmList.b.put("EXP-RC4-MD5", new psc_l8());
        AlgorithmList.b.put("RC2-CBC-MD5", new psc_l2());
        AlgorithmList.b.put("DES-CBC-MD5", new psc_l5());
    }
}
