// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.license;

import com.ms.wfc.app.RegistryKey;
import java.text.NumberFormat;
import com.ms.wfc.app.Registry;
import java.io.InputStream;
import com.ms.wfc.app.Time;
import com.ms.wfc.app.SystemInformation;
import java.io.IOException;
import com.ms.wfc.core.Component;

public class _cls753A
{
    private static String \u00fc;
    private static _cls872D \u00fd;
    private String \u00fe;
    private int \u00ff;
    private int \u0100;
    private int \u0101;
    private int \u0102;
    private int \u0103;
    private int \u0104;
    private static String \u0105;
    private static _cls753A \u0106;
    private static int[] \u0107;
    private static String \u0108;
    
    public static void _mth563B(final Component component) {
        if (component != null && _cls753A.\u00fd != null && _cls753A.\u00fd._mth721B() && component.getDesignMode()) {
            component.dispose();
            throw new LicenseException(10, "License is runtime only");
        }
    }
    
    private static int \u00fc(final int n) {
        return _cls753A.\u0108.charAt((13 * n - ((n << 4) - (n << 2)) << 1) + 1) - '@';
    }
    
    public _cls753A() {
        this.\u00fe = null;
        this.\u00ff = 0;
        this.\u0100 = 0;
        this.\u0101 = 0;
        this.\u0102 = 0;
        this.\u0103 = 0;
        this.\u0104 = 0;
    }
    
    private int \u00fd(final int n) {
        if (this.\u0104 >= this.\u0103) {
            this.\u0104 = 0;
        }
        final char char1 = _cls753A.\u0105.charAt(this.\u0104++);
        int n2 = 0;
        while (_cls753A.\u0107[n2] != n && ++n2 < 64) {}
        return (n2 - char1 % \u00fc(10)) / \u00fc(11);
    }
    
    private void \u00fe(final String \u00fe, final int n) {
        int n2 = 0;
        for (int i = 0; i <= n; ++i) {
            n2 += i * n;
        }
        this.\u0101 = n2 / (n * (n + 1) >> 1);
        this.\u0102 = \u00fc(12) + n * \u00fc(13) % \u00fc(14);
        this.\u00fe = \u00fe;
        this.\u0100 = \u00fe.length();
        this.\u00ff = 0;
    }
    
    private int getByte(final String s, int index) {
        final int \u00fd = this.\u00fd(s.charAt(index++));
        final int n = (\u00fd & 0x1) << \u00fc(2) | (\u00fd & 0x2) << \u00fc(3) | (\u00fd & 0x4) << \u00fc(4) | (\u00fd & 0x8) >> \u00fc(5);
        final int \u00fd2 = this.\u00fd(s.charAt(index));
        return n | ((\u00fd2 & 0x1) << \u00fc(6) | (\u00fd2 & 0x2) << \u00fc(7) | (\u00fd2 & 0x4) << \u00fc(8) | (\u00fd2 & 0x8) >> \u00fc(9));
    }
    
    private void \u00ff(final int n, final StringBuffer sb) {
        sb.append((char)this.\u0102((n & 0x1) << \u00fc(19) | (n & 0x4) | (n & 0x10) >> \u00fc(20) | (n & 0x40) >> \u00fc(21)));
        sb.append((char)this.\u0102((n & 0x2) << \u00fc(22) | (n & 0x8) >> \u00fc(23) | (n & 0x20) >> \u00fc(24) | (n & 0x80) >> \u00fc(25)));
    }
    
    private static int \u0100(final String s, final String s2) {
        int n = 0;
        for (int length = s.length(), i = 0; i < length; ++i) {
            n += s.charAt(i);
        }
        for (int length2 = s2.length(), j = 0; j < length2; ++j) {
            n += s2.charAt(j);
        }
        if (n % \u00fc(15) == 0) {
            n += '5';
        }
        if (n % \u00fc(16) == 0) {
            n += 'o';
        }
        if (n % \u00fc(17) == 0) {
            n += '\u0017';
        }
        if (n % \u00fc(18) == 0) {
            n += '\u00ed';
        }
        return n % '\u0100';
    }
    
    public static void _mth3A49(final int n) {
        if (_cls753A.\u00fd != null) {
            final int mth8C3A = _cls753A.\u00fd._mth8C3A();
            if (n > mth8C3A) {
                throw new LicenseException(8, "License supports no more than " + mth8C3A + " objects");
            }
        }
    }
    
    static {
        _cls753A.\u00fc = "1.2";
        _cls753A.\u00fd = null;
        _cls753A.\u0105 = "MIM Software GmbH";
        _cls753A.\u0106 = new _cls753A();
        _cls753A.\u0107 = new int[] { 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 74, 75, 76, 77, 78, 80, 82, 83, 84, 85, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 107, 109, 110, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 95, 36, 38, 33, 63, 35, 42, 58, 40, 41, 60, 62 };
        _cls753A.\u0108 = "2BfB8FLCw@aCOG#D7ApBgD!DfM4GOCdB!ChE8GvC2C8FDBfA@DjGmt3";
    }
    
    public static void _mth821F() {
        if (_cls753A.\u00fd == null) {
            final InputStream resourceAsStream = _cls753A.\u0106.getClass().getResourceAsStream("license.key");
            if (resourceAsStream == null) {
                throw new LicenseException(2, "License key missing: Resource com/mim/wfc/license/license.key not found");
            }
            String string = new String();
            try {
                int read;
                while ((read = resourceAsStream.read()) != -1) {
                    if (read != 10 && read != 13) {
                        string += (char)read;
                    }
                }
            }
            catch (IOException ex) {
                throw new LicenseException(2, "Error reading file com/mim/wfc/license/license.key");
            }
            if (string == null) {
                throw new LicenseException(3, "No license key in resource license.key");
            }
            final String mth641F = new _cls753A()._mth641F(string, "Ty|,30Xz-ETxA34.L");
            if (mth641F == null) {
                throw new LicenseException(1, "Invalid license key in license.key");
            }
            final _cls872D cls872D = new _cls872D();
            cls872D._mth824B(mth641F);
            if (cls872D._mth632D()) {
                _cls753A.\u00fd = cls872D;
                return;
            }
            boolean b = false;
            final String mth882C = cls872D._mth882C();
            if (mth882C != null) {
                if (!_cls753A.\u00fc.startsWith(mth882C)) {
                    throw new LicenseException(7, "Version number and licensed version number do not match");
                }
                b = true;
            }
            final String mth725A = cls872D._mth725A();
            if (mth725A != null) {
                if (!mth725A.equalsIgnoreCase(SystemInformation.getComputerName())) {
                    throw new LicenseException(5, "Computer name and licensed computer name do not match");
                }
                b = true;
            }
            final String mth589E = cls872D._mth589E();
            if (mth589E != null) {
                if (!mth589E.equalsIgnoreCase(SystemInformation.getUserName())) {
                    throw new LicenseException(6, "User name and licensed user name do not match");
                }
                b = true;
            }
            final Time mth648A = cls872D._mth648A();
            if (mth648A != null) {
                if (mth648A.compareTo(new Time()) < 0) {
                    throw new LicenseException(9, "License is expired");
                }
                b = true;
            }
            final int mth726D = cls872D._mth726D();
            if (mth726D != 0) {
                final int \u0103 = \u0103(mth726D);
                if (\u0103 != 0) {
                    if (\u0103 == 1) {
                        throw new LicenseException(9, "Your license has expired");
                    }
                    throw new LicenseException(11, "MIMLib was not installed properly (error code " + \u0103 + ".");
                }
                else {
                    b = true;
                }
            }
            if (cls872D._mth721B()) {
                b = true;
            }
            if (!b) {
                throw new LicenseException(1, "No valid license descriptor in license.key");
            }
            _cls753A.\u00fd = cls872D;
        }
    }
    
    public String _mth437D(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return null;
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        final int \u0101 = \u0100(s2, s);
        this.\u00fe(s2, \u0101);
        this.\u0104 = 0;
        this.\u0103 = _cls753A.\u0105.length();
        final StringBuffer sb = new StringBuffer();
        this.\u00ff(\u0101 ^ s2.charAt(0), sb);
        for (int i = 0; i < length; ++i) {
            this.\u00ff(s.charAt(i) ^ this.\u0101(), sb);
        }
        return sb.toString();
    }
    
    private int \u0101() {
        if (this.\u00ff >= this.\u0100) {
            this.\u00ff = 0;
        }
        return (this.\u00fe.charAt(this.\u00ff++) + (this.\u0102 += this.\u0101)) % 256;
    }
    
    private int \u0102(final int n) {
        if (this.\u0104 >= this.\u0103) {
            this.\u0104 = 0;
        }
        return _cls753A.\u0107[n * \u00fc(10) + _cls753A.\u0105.charAt(this.\u0104++) % \u00fc(11)];
    }
    
    private static int \u0103(final int n) {
        final Time time = new Time();
        final String s = "Ty|,30Xz-ETxA34.L";
        final _cls753A cls753A = new _cls753A();
        final String s2 = "CLSID\\{45869D0C-8E92-11D2-809A-00001C405077}";
        final RegistryKey subKey = Registry.CLASSES_ROOT.getSubKey(s2);
        if (subKey == null) {
            final RegistryKey subKey2 = Registry.CLASSES_ROOT.createSubKey(s2);
            if (subKey2 == null) {
                return 2;
            }
            subKey2.setValue("InprocServer32", (Object)"oleaut32.dll");
            final RegistryKey subKey3 = subKey2.createSubKey("InprocServer32");
            if (subKey3 != null) {
                subKey3.setValue("ThreadingModel", (Object)"Apartment");
                subKey3.setValue("ProgID", (Object)"JM.JM.1");
                subKey3.close(true);
            }
            final NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMinimumIntegerDigits(2);
            subKey2.setValue("Attribute", (Object)cls753A._mth437D(numberInstance.format(time.getYear() % 100) + numberInstance.format(time.getMonth()) + numberInstance.format(time.getDay()), s));
            subKey2.close(true);
            return 0;
        }
        else {
            final String mth641F = cls753A._mth641F((String)subKey.getValue("Attribute"), s);
            if (mth641F == null) {
                return 3;
            }
            int int1;
            try {
                int1 = Integer.parseInt(mth641F);
            }
            catch (NumberFormatException ex) {
                return 4;
            }
            if (int1 == 0) {
                return 5;
            }
            if (new Time(int1 / 10000, int1 % 10000 / 100, int1 % 100).addDays(n).compareTo(time) < 0) {
                return 1;
            }
            subKey.close();
            return 0;
        }
    }
    
    public String _mth641F(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return null;
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        this.\u0104 = 0;
        this.\u0103 = _cls753A.\u0105.length();
        this.\u00fe(s2, this.getByte(s, 0) ^ s2.charAt(0));
        final StringBuffer sb = new StringBuffer();
        for (int i = \u00fc(0); i < length; i += \u00fc(1)) {
            if (i + 1 >= length) {
                return null;
            }
            sb.append((char)(this.getByte(s, i) ^ this.\u0101()));
        }
        return sb.toString();
    }
}
