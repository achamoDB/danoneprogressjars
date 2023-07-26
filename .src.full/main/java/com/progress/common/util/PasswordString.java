// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.Random;

public class PasswordString
{
    private byte[] m_data;
    private byte[] m_key;
    private long m_seed;
    private Random m_generator;
    
    public PasswordString() {
        this.m_data = null;
        this.m_key = null;
        this.m_seed = System.currentTimeMillis();
        this.m_generator = new Random(this.m_seed);
    }
    
    public PasswordString(final long n) {
        this.m_data = null;
        this.m_key = null;
        this.m_seed = System.currentTimeMillis();
        this.m_generator = new Random(this.m_seed);
    }
    
    public PasswordString(final PasswordString passwordString) {
        this.m_data = null;
        this.m_key = null;
        this.m_seed = System.currentTimeMillis();
        this.m_generator = new Random(this.m_seed);
        if (null != passwordString) {
            try {
                if (0 < passwordString.length()) {
                    this.setValue(passwordString.toString());
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public PasswordString(final String value) {
        this.m_data = null;
        this.m_key = null;
        this.m_seed = System.currentTimeMillis();
        this.m_generator = new Random(this.m_seed);
        try {
            this.setValue(value);
        }
        catch (Exception ex) {}
    }
    
    protected void finalize() throws Throwable {
        this.empty();
        this.m_seed = 0L;
        if (null != this.m_key) {
            for (int i = 0; i < this.m_key.length; ++i) {
                this.m_key[i] = 0;
            }
        }
    }
    
    public void setValue(final String s) throws NullPointerException {
        if (null == s) {
            throw new NullPointerException("Null required argument");
        }
        this.empty();
        final int length = s.length();
        if (0 < length) {
            this.m_key = new byte[length];
            this.m_generator.nextBytes(this.m_key);
            this.m_data = s.getBytes();
            this.flipBits();
        }
    }
    
    public int length() throws Exception {
        return (null == this.m_data) ? 0 : this.m_data.length;
    }
    
    public void empty() {
        if (null != this.m_data) {
            final int length = this.m_data.length;
            if (0 < length) {
                for (int i = 0; i < length; ++i) {
                    this.m_data[i] = 0;
                }
                this.m_data = null;
            }
        }
    }
    
    public String toString() {
        return this.getValue();
    }
    
    public String exportPassword() throws Exception {
        final StringBuffer sb = new StringBuffer();
        sb.append(Long.toHexString(this.m_seed).toUpperCase());
        sb.append("=");
        if (null != this.m_data && 0 < this.m_data.length) {
            sb.append(Base64.encode(this.m_data));
        }
        return new String(sb.toString());
    }
    
    public void importPassword(final String s) throws Exception {
        if (null == s) {
            throw new NullPointerException("input required");
        }
        int index = s.indexOf("=");
        if (-1 == index) {
            throw new Exception("Invalid encoded value.");
        }
        this.empty();
        this.m_seed = Long.parseLong(s.substring(0, index++), 16);
        this.m_generator = new Random(this.m_seed);
        if (index < s.length()) {
            this.m_data = Base64.decode(s.substring(index));
            this.m_key = new byte[this.m_data.length];
            this.m_generator.nextBytes(this.m_key);
        }
    }
    
    public boolean test(final String s) {
        boolean b = false;
        if (null != s) {
            if (null != this.m_data) {
                if (0 < this.m_data.length) {
                    final byte[] bytes = s.getBytes();
                    if (this.m_data.length == bytes.length) {
                        for (int i = 0; i < bytes.length; ++i) {
                            bytes[i] ^= this.m_key[i];
                        }
                        b = true;
                        for (int j = 0; j < bytes.length; ++j) {
                            if (bytes[j] != this.m_data[j]) {
                                b = false;
                                break;
                            }
                        }
                        for (int k = 0; k < bytes.length; ++k) {
                            bytes[k] = 0;
                        }
                    }
                }
                else if (0 == s.length()) {
                    b = true;
                }
            }
            else if (0 == s.length()) {
                b = true;
            }
        }
        return b;
    }
    
    protected String getValue() {
        String s;
        if (null != this.m_data && 0 < this.m_data.length) {
            try {
                this.flipBits();
                s = new String(this.m_data);
                this.flipBits();
            }
            catch (Exception ex) {
                s = new String("");
            }
        }
        else {
            s = new String("");
        }
        return s;
    }
    
    protected void flipBits() {
        final int length = this.m_data.length;
        if (0 < length) {
            for (int i = 0; i < length; ++i) {
                this.m_data[i] ^= this.m_key[i];
            }
        }
    }
}
