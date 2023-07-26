// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.license;

import com.ms.wfc.app.Time;

public class _cls872D
{
    private boolean \u0109;
    private boolean \u010a;
    private boolean \u010b;
    private int \u010c;
    private String \u010d;
    private String \u010e;
    private String \u010f;
    private String \u0110;
    private Time \u0111;
    private int \u0112;
    private int \u0113;
    
    public boolean _mth721B() {
        return this.\u010b;
    }
    
    public _cls872D() {
        this.\u0109 = false;
        this.\u010a = false;
        this.\u010b = false;
        this.\u010c = 0;
        this.\u010d = null;
        this.\u010e = null;
        this.\u010f = null;
        this.\u0110 = null;
        this.\u0111 = null;
        this.\u0112 = 0;
        this.\u0113 = 0;
    }
    
    public boolean _mth632D() {
        return this.\u0109;
    }
    
    public String _mth589E() {
        return this.\u0110;
    }
    
    public int _mth726D() {
        return this.\u0112;
    }
    
    public int _mth533C() {
        return this.\u010c;
    }
    
    public String _mth1E38() {
        return this.\u010d;
    }
    
    public Time _mth648A() {
        return this.\u0111;
    }
    
    public int _mth8C3A() {
        return this.\u0113;
    }
    
    public String _mth725A() {
        return this.\u010f;
    }
    
    public String _mth882C() {
        return this.\u010e;
    }
    
    public void _mth824B(final String s) {
        this.\u0109 = false;
        this.\u010a = false;
        this.\u010b = false;
        this.\u010c = 0;
        this.\u010d = null;
        this.\u010e = null;
        this.\u010f = null;
        this.\u0110 = null;
        this.\u0111 = null;
        this.\u0112 = 0;
        this.\u0113 = 0;
        if (s == null) {
            return;
        }
        if (s.equals("MIM")) {
            this.\u0109 = true;
            return;
        }
        int index;
        for (int length = s.length(), i = 0; i < length; i = index + 1) {
            index = s.indexOf(59, i + 1);
            if (index < 0) {
                throw new LicenseException(1, "Invalid option in license key");
            }
            final char char1 = s.charAt(i);
            switch (char1) {
                case 99: {
                    final String substring = s.substring(i + 1, index);
                    try {
                        this.\u0113 = Integer.parseInt(substring);
                        break;
                    }
                    catch (NumberFormatException ex) {
                        throw new LicenseException(1, "Invalid count in license key");
                    }
                }
                case 100: {
                    this.\u010a = true;
                    break;
                }
                case 101: {
                    final String substring2 = s.substring(i + 1, index);
                    try {
                        final int int1 = Integer.parseInt(substring2);
                        int n = int1 / 10000;
                        if (n < 100) {
                            if (n < 50) {
                                n += 2000;
                            }
                            else {
                                n += 1900;
                            }
                        }
                        this.\u0111 = new Time(n, int1 % 10000 / 100, int1 % 100);
                        break;
                    }
                    catch (NumberFormatException ex2) {
                        throw new LicenseException(1, "Invalid expiration date in license key");
                    }
                }
                case 110: {
                    this.\u010f = s.substring(i + 1, index);
                    break;
                }
                case 112: {
                    this.\u010d = s.substring(i + 1, index);
                    break;
                }
                case 114: {
                    this.\u010b = true;
                    i += 2;
                    break;
                }
                case 115: {
                    final String substring3 = s.substring(i + 1, index);
                    try {
                        this.\u010c = Integer.parseInt(substring3);
                        break;
                    }
                    catch (NumberFormatException ex3) {
                        throw new LicenseException(1, "Invalid serial number in license key");
                    }
                }
                case 116: {
                    final String substring4 = s.substring(i + 1, index);
                    try {
                        this.\u0112 = Integer.parseInt(substring4);
                        break;
                    }
                    catch (NumberFormatException ex4) {
                        throw new LicenseException(1, "Invalid expiration time in license key");
                    }
                }
                case 117: {
                    this.\u0110 = s.substring(i + 1, index);
                    break;
                }
                case 118: {
                    this.\u010e = s.substring(i + 1, index);
                    break;
                }
                default: {
                    throw new LicenseException(1, "Invalid license descriptor '" + char1 + "' in license.key");
                }
            }
        }
    }
    
    public boolean _mth539A() {
        return this.\u010a;
    }
}
