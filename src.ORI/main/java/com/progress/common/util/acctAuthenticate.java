// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import com.progress.chimera.log.SecurityLog;
import com.progress.osmetrics.OSMetricsImpl;
import com.progress.system.SystemPlugIn;
import java.net.InetAddress;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.util.Random;
import com.progress.common.log.Subsystem;
import com.progress.message.asMsg;

public class acctAuthenticate implements asMsg
{
    public static Subsystem m_securitySubsystem;
    private boolean m_noauditsuccauth;
    private static boolean m_traceSSO;
    private static int BASE;
    private int m_rem;
    private Random m_random;
    private boolean DEBUG;
    
    public acctAuthenticate() {
        this.m_noauditsuccauth = false;
        this.m_random = null;
        this.DEBUG = true;
    }
    
    public static native String authorizeUser(final String p0, final String p1, final String p2);
    
    public native boolean verifyUser(final String p0, final String p1);
    
    public native boolean authenticateUser(final String p0, final String p1, final boolean p2);
    
    public static native String validateGroups(final String p0);
    
    public native String passwdPrompt(final String p0);
    
    public static native String whoami();
    
    public static native String getUserGroups(final String p0);
    
    public static String authorizeUserJNI(final String s, final String s2, final String s3) {
        return authorizeUser(s, s2, s3);
    }
    
    public boolean verifyUserJNI(final String s, final String s2) {
        return this.verifyUser(s, s2);
    }
    
    public boolean authenticateUserJNI(final String s, final String s2, final boolean b) {
        return this.authenticateUser(s, s2, b);
    }
    
    public static String validateGroupsJNI(final String s) {
        return validateGroups(s);
    }
    
    public String passwdPromptJNI(final String s) {
        return this.passwdPrompt(s);
    }
    
    public static String whoamiJNI() {
        return whoami();
    }
    
    public static String getUserGroupsJNI(final String s) {
        return getUserGroups(s);
    }
    
    public boolean validateGroupList(final String s) {
        return this.parseAndLogAuthInfoStr(validateGroups(s));
    }
    
    public String promptForPassword(final String s) {
        return this.promptForUP(s)[1];
    }
    
    public String[] promptForUP(final String s) {
        final String[] array = new String[2];
        final byte[] array2 = new byte[50];
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.DBManBundle");
        if (s == null) {
            System.out.print(progressResources.getTranString("EnterUserNamePrompt"));
            try {
                System.in.read(array2);
                array[0] = new String(array2).trim();
            }
            catch (Exception ex) {
                System.err.println(progressResources.getTranString("InputError", new Object[] { ex.getMessage() }));
            }
        }
        else {
            array[0] = s;
        }
        final String tranString = progressResources.getTranString("PasswordPrompt", new Object[] { array[0] });
        try {
            array[1] = this.passwdPrompt(tranString).trim();
        }
        catch (Exception ex2) {
            System.err.println(progressResources.getTranString("InputError", new Object[] { ex2.getMessage() }));
        }
        return array;
    }
    
    public boolean validate(String replace, final String str, final String s) {
        if (System.getProperty("os.name").startsWith("Windows 9") && replace.equals(System.getProperty("user.name"))) {
            return true;
        }
        final boolean generated = this.isGenerated(str);
        replace = replace.replace('/', '\\');
        if (acctAuthenticate.m_traceSSO) {
            if (generated) {
                System.out.println("***** Auto-validating " + replace + " (" + str + ")");
            }
            else {
                System.out.println("***** Validating " + replace);
            }
        }
        return (!generated || this.validateAutoPassword(replace, str)) && this.parseAndLogAuthInfoStr(authorizeUser(replace, str, s));
    }
    
    public String generatePassword(final String str) {
        final String autoPasswordUsername = this.makeAutoPasswordUsername(str);
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("***** Generating auto-password using " + str);
        }
        final String copyValue = String.copyValueOf(this.clientGeneratePassword(autoPasswordUsername.toCharArray()));
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    returning auto-password " + copyValue);
        }
        return copyValue;
    }
    
    public void setNoAuditSuccAuth() {
        this.m_noauditsuccauth = true;
    }
    
    public String makeAutoPasswordUsername(final String s) {
        return (s.length() < 21) ? s : s.substring(s.length() - 20);
    }
    
    private int charToInt(final char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return 'd' - c;
        }
        return 36;
    }
    
    char intToChar(final int n) {
        if (n < 10) {
            return (char)(n + 48);
        }
        if (n <= 35) {
            return (char)(n + 97 - 10);
        }
        return ':';
    }
    
    int[] charArrToIntArr(final char[] array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = this.charToInt(array[i]);
        }
        return array2;
    }
    
    int[] stringToIntArr(final String s) {
        final int[] array = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            array[i] = this.charToInt(s.charAt(i));
        }
        return array;
    }
    
    char[] intArrToCharArr(final int[] array) {
        final char[] array2 = new char[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = this.intToChar(array[i]);
        }
        return array2;
    }
    
    int[] fixIntArr(final int[] array) {
        int i;
        for (i = 0; i < array.length - 1 && array[i] >= 0; ++i) {}
        while (i >= 0 && array[i] <= 0) {
            --i;
        }
        final int[] array2 = new int[i + 1];
        while (i >= 0) {
            array2[i] = array[i];
            --i;
        }
        return array2;
    }
    
    String intArrToString(final int[] array) {
        String string = "";
        for (int i = 0; i < array.length; ++i) {
            string += this.intToChar(array[i]);
        }
        return string;
    }
    
    int[] mult37DigToDig(final int n, final int n2) {
        final int n3 = n * n2;
        if (n3 >= acctAuthenticate.BASE) {
            final int n4 = n3 / acctAuthenticate.BASE;
            return new int[] { n3 - acctAuthenticate.BASE * n4, n4 };
        }
        return new int[] { n3 };
    }
    
    int[] shift(final int[] array, final int n) {
        final int length = array.length;
        final int[] array2 = new int[n + length];
        for (int i = 0; i < n; ++i) {
            array2[i] = 0;
        }
        for (int j = n; j < n + length; ++j) {
            array2[j] = array[j - n];
        }
        return array2;
    }
    
    private int addDig(final int n, final int n2) {
        final int n3 = n + n2 + this.m_rem;
        if (n3 >= acctAuthenticate.BASE) {
            this.m_rem = 1;
            return n3 - acctAuthenticate.BASE;
        }
        this.m_rem = 0;
        return n3;
    }
    
    private int subDig(final int n, final int n2) {
        final int n3 = n - n2 - this.m_rem;
        if (n3 < 0) {
            this.m_rem = 1;
            return n3 + acctAuthenticate.BASE;
        }
        this.m_rem = 0;
        return n3;
    }
    
    int add37(final int[] array, final int[] array2, final int[] array3) {
        final int length = array.length;
        final int length2 = array2.length;
        array3[this.m_rem = 0] = -1;
        int n;
        for (n = 0; n < length || n < length2; ++n) {
            if (n >= length) {
                array3[n] = this.addDig(0, array2[n]);
            }
            else if (n >= length2) {
                array3[n] = this.addDig(array[n], 0);
            }
            else {
                array3[n] = this.addDig(array[n], array2[n]);
            }
        }
        if (this.m_rem > 0) {
            array3[n] = this.m_rem;
            ++n;
        }
        this.m_rem = 0;
        array3[n] = -1;
        return n;
    }
    
    int[] add37(final int[] array, final int[] array2) {
        final int[] array3 = new int[40];
        final int add37 = this.add37(array, array2, array3);
        final int[] array4 = new int[add37];
        for (int i = 0; i < add37; ++i) {
            array4[i] = array3[i];
        }
        return array4;
    }
    
    int[] sub37(final int[] array, final int[] array2) {
        final int length = array.length;
        final int length2 = array2.length;
        final int[] array3 = new int[length + 1];
        array3[this.m_rem = 0] = -1;
        int n;
        for (n = 0; n < length || n < length2; ++n) {
            if (n >= length) {
                array3[n] = this.subDig(0, array2[n]);
            }
            else if (n >= length2) {
                array3[n] = this.subDig(array[n], 0);
            }
            else {
                array3[n] = this.subDig(array[n], array2[n]);
            }
        }
        if (this.m_rem > 0) {
            array3[n] = this.m_rem;
            ++n;
        }
        this.m_rem = 0;
        return this.fixIntArr(array3);
    }
    
    int[] mult37IntArrs(final int[] array, final int[] array2) {
        int[] add37 = new int[0];
        final int length = array.length;
        final int length2 = array2.length;
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length2; ++j) {
                add37 = this.add37(add37, this.shift(this.mult37DigToDig(array[i], array2[j]), i + j));
            }
        }
        return this.fixIntArr(add37);
    }
    
    int[] getValue(final int[] array, final int[] array2, final int[] array3, final int[] array4) {
        return this.mult37IntArrs(this.add37(array2, this.add37(array, array4)), this.add37(array3, this.add37(array, array4)));
    }
    
    int[] generatePassword(final int[] array, final int[] array2, final int[] array3, final int[] array4) {
        return this.add37(this.getValue(array, array2, array3, array4), array4);
    }
    
    int[] med37(final int[] array, final int[] array2) {
        final int[] array3 = new int[20];
        final int[] add37 = this.add37(array, array2);
        final int length = add37.length;
        this.m_rem = 0;
        for (int i = length - 1; i >= 0; --i) {
            final int n = (add37[i] + this.m_rem * acctAuthenticate.BASE) / 2;
            array3[i] = n;
            if (2 * n != add37[i] + this.m_rem * acctAuthenticate.BASE) {
                this.m_rem = 1;
            }
            else {
                this.m_rem = 0;
            }
        }
        if (array3[length - 1] == 0) {
            array3[length - 1] = -1;
        }
        else {
            array3[length] = -1;
        }
        return this.fixIntArr(array3);
    }
    
    int compare37(final int[] array, final int[] array2) {
        final int length = array.length;
        final int length2 = array2.length;
        if (length != length2) {
            return length - length2;
        }
        for (int i = length - 1; i >= 0; --i) {
            if (array[i] != array2[i]) {
                return array[i] - array2[i];
            }
        }
        return 0;
    }
    
    int[] decode(final int[] array, final int[] array2, final int[] array3) {
        final int[] array4 = new int[0];
        final int n = (array.length + 1) / 2;
        int[] array5 = new int[n];
        int[] array6 = new int[0];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            array5[i] = 36;
        }
        int[] array7;
        for (array7 = this.med37(array5, array6); this.compare37(array7, array6) != 0; array7 = this.med37(array5, array6)) {
            final int[] value = this.getValue(array7, array2, array3, array4);
            if (n2++ > 105) {
                System.out.println("FATAL ERROR in decode, dbg_count++>105");
                return null;
            }
            if (this.compare37(value, array) > 0) {
                array5 = array7;
            }
            else {
                array6 = array7;
            }
        }
        return this.sub37(array7, this.sub37(array, this.getValue(array7, array2, array3, array4)));
    }
    
    int[] addPattern(final int[] array) {
        final int length = array.length;
        final int[] array2 = new int[length + 4];
        for (int i = 0; i < length; ++i) {
            array2[i] = array[i];
        }
        for (int j = 0; j < 4; ++j) {
            final int n = array[j] + j * (j + 2) + 3;
            array2[length + j] = ((n > 36) ? (n - 36) : n);
        }
        return array2;
    }
    
    int[] removePattern(final int[] array) {
        final int length = array.length;
        if (!this.isGeneratedInt(array)) {
            return array;
        }
        array[length - 4] = -1;
        return this.fixIntArr(array);
    }
    
    boolean isGenerated(final char[] array) {
        return this.isGeneratedInt(this.charArrToIntArr(array));
    }
    
    boolean isGenerated(final String s) {
        return s.length() >= 8 && this.isGenerated(s.toCharArray());
    }
    
    boolean isGeneratedInt(final int[] array) {
        final int length = array.length;
        for (int i = 0; i < 4; ++i) {
            final int n = array[i] + i * (i + 2) + 3;
            if (array[length - 4 + i] != ((n > 36) ? (n - 36) : n)) {
                return false;
            }
        }
        return true;
    }
    
    int[] getTime() {
        final String string = new Long(System.currentTimeMillis()).toString();
        final int length = string.length();
        final int[] stringToIntArr = this.stringToIntArr(string);
        final int[] array = new int[length];
        for (int i = 0; i < length; ++i) {
            array[i] = stringToIntArr[length - i - 1];
        }
        return array;
    }
    
    int[] getRandom(final int n) {
        if (this.m_random == null) {
            this.m_random = new Random();
        }
        final int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            int nextInt = this.m_random.nextInt();
            if (nextInt < 0) {
                nextInt = -nextInt;
            }
            array[i] = nextInt - nextInt / acctAuthenticate.BASE * acctAuthenticate.BASE;
        }
        return array;
    }
    
    char[] clientGeneratePassword(final char[] array) {
        final char[] intArrToCharArr = this.intArrToCharArr(this.addPattern(this.generatePassword(this.getTime(), this.charArrToIntArr(array), this.getHostName(), this.getRandom(11))));
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    client generated password: " + new String(intArrToCharArr));
        }
        return intArrToCharArr;
    }
    
    long decodeTime(final char[] obj, final char[] obj2) {
        final int[] charArrToIntArr = this.charArrToIntArr(obj);
        final int[] charArrToIntArr2 = this.charArrToIntArr(obj2);
        final int[] hostName = this.getHostName();
        final int[] decode = this.decode(this.removePattern(charArrToIntArr), charArrToIntArr2, hostName);
        final int length = decode.length;
        final int[] array = new int[length];
        for (int i = 0; i < length; ++i) {
            array[i] = decode[length - i - 1];
        }
        long long1 = 0L;
        try {
            long1 = Long.parseLong(new String(this.intArrToString(array)));
        }
        catch (NumberFormatException ex) {
            if (acctAuthenticate.m_traceSSO) {
                System.out.println("*****    decode time erorr: " + ex.getMessage());
            }
            acctAuthenticate.m_securitySubsystem.log(0, 7021956244000745141L, "" + (Object)obj + (Object)obj2 + hostName);
        }
        return long1;
    }
    
    private int[] getHostName() {
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            final InetAddress[] allByName = InetAddress.getAllByName(localHost.getHostName());
            if (1 < allByName.length) {
                localHost = allByName[0];
            }
        }
        catch (Exception ex) {
            if (acctAuthenticate.m_traceSSO) {
                System.out.println("*****    get host name erorr: " + ex.getMessage());
            }
            return null;
        }
        String s = localHost.getHostName();
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    local host name: " + s);
        }
        final int index = s.indexOf(".");
        if (index > 0) {
            s = s.substring(0, index);
        }
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    resolved local host name: " + s);
        }
        return this.charArrToIntArr(s.toCharArray());
    }
    
    private int getTimeout() {
        final String property = System.getProperty("pwdtimeout");
        if (property != null) {
            try {
                return Integer.parseInt(property);
            }
            catch (NumberFormatException ex) {}
        }
        return 90000;
    }
    
    boolean validateAutoPassword(final char[] data, final char[] array) {
        String str = null;
        final long decodeTime = this.decodeTime(array, data);
        final long currentTimeMillis = System.currentTimeMillis();
        long i = currentTimeMillis - decodeTime;
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    using decode Time: " + Long.toString(decodeTime));
            System.out.println("*****    using current Time: " + Long.toString(currentTimeMillis));
            System.out.println("*****    using delta Time (current - decode): " + Long.toString(i));
        }
        final SystemPlugIn value;
        final OSMetricsImpl osMetrics;
        if ((i < -this.getTimeout() || i > this.getTimeout()) && (value = SystemPlugIn.get()) != null && (osMetrics = value.getOSMetrics()) != null && osMetrics.isInitialized()) {
            final long n = osMetrics.getCurrentTime() * 1000L - decodeTime;
            if (n >= -this.getTimeout() && n <= this.getTimeout()) {
                i = n;
            }
        }
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    check of delta time < : " + Long.toString(-this.getTimeout()));
            System.out.println("*****    check of delta time > : " + Long.toString(this.getTimeout()));
        }
        if (i < -this.getTimeout()) {
            str = "<-" + this.getTimeout() / 1000;
            if (acctAuthenticate.m_traceSSO) {
                System.out.println("*****    passed < password check with: " + str);
            }
        }
        else if (i > this.getTimeout()) {
            str = new Long(i / 1000L).toString();
            if (acctAuthenticate.m_traceSSO) {
                System.out.println("*****    passed < password check with: " + str);
            }
        }
        if (str != null) {
            acctAuthenticate.m_securitySubsystem.log(2, 7021956244000745140L, String.valueOf(data) + ":" + str + ":");
            if (acctAuthenticate.m_traceSSO) {
                System.out.println("*****    failed password check");
            }
            return false;
        }
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    passed password check");
        }
        return true;
    }
    
    boolean validateAutoPassword(final String str, final String s) {
        final String autoPasswordUsername = this.makeAutoPasswordUsername(str);
        if (acctAuthenticate.m_traceSSO) {
            System.out.println("*****    using auto-username " + str);
        }
        return this.validateAutoPassword(autoPasswordUsername.toCharArray(), s.toCharArray());
    }
    
    private static long convert(final String s) {
        final long[] array = { 7021956244000745129L, 7021956244000745130L, 7021956244000745131L, 7021956244000745132L, 7021956244000745133L, 7021956244000745134L, 7021956244000745135L, 7021956244000745136L, 7021956244000745137L, 7021956244000745138L, 7021956244000745139L, 7021956244000745140L, 7021956244000745141L, 7021956244000745189L };
        final String[] array2 = { "asMSG052", "asMSG053", "asMSG054", "asMSG055", "asMSG056", "asMSG057", "asMSG058", "asMSG059", "asMSG060", "asMSG061", "asMSG062", "asMSG063", "asMSG064", "asMSG065" };
        for (int i = 0; i < array2.length; ++i) {
            if (s.equals(array2[i])) {
                return array[i];
            }
        }
        return array[0];
    }
    
    private boolean parseAndLogAuthInfoStr(final String s) {
        int int1;
        try {
            int1 = Integer.parseInt(s.substring(0, 1));
        }
        catch (Exception ex) {
            int1 = 0;
        }
        if (this.m_noauditsuccauth && int1 == 3) {
            return true;
        }
        if (s.length() < 11) {
            acctAuthenticate.m_securitySubsystem.log(0, 7021956244000745137L, "LOGGING ERROR");
            return int1 == 3;
        }
        final long convert = convert(s.substring(1, 9));
        String s2;
        for (s2 = s.substring(10); s2.startsWith(":"); s2 = s2.substring(1)) {}
        acctAuthenticate.m_securitySubsystem.log(int1, convert, s2);
        return int1 == 3;
    }
    
    static {
        acctAuthenticate.m_securitySubsystem = SecurityLog.get();
        acctAuthenticate.m_traceSSO = false;
        final String property = System.getProperty("tracesso");
        System.load(new InstallPath().fullyQualifyFile("auth.dll"));
        if (null != property) {
            acctAuthenticate.m_traceSSO = true;
        }
        acctAuthenticate.BASE = 37;
    }
}
