// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;

public class ProDebugLib
{
    private String m_errorMsg;
    private Vector m_processInfo;
    private short m_portNum;
    private static boolean libLoadSuccessfull;
    private boolean m_ipv6;
    
    public ProDebugLib() {
        this.m_errorMsg = null;
        this.m_processInfo = null;
        this.m_portNum = 0;
        this.m_ipv6 = true;
    }
    
    private native int getProcessInfo(final StringBuffer p0);
    
    private native int pingOn(final String p0, final short p1, final StringBuffer p2);
    
    private native int pingOff(final String p0, final StringBuffer p1);
    
    private native int getIpver(final short p0);
    
    public int getProcessInfoJNI() {
        final StringBuffer errorMsg = new StringBuffer();
        int i = 0;
        this.m_errorMsg = new String();
        if (this.m_processInfo != null) {
            this.m_processInfo.clear();
        }
        this.m_processInfo = new Vector(0);
        while (i == 0) {
            if ((i = this.getProcessInfo(errorMsg)) == 0) {
                if (errorMsg.length() == 0) {
                    break;
                }
                this.m_processInfo.add(errorMsg.toString());
                errorMsg.setLength(0);
            }
            else {
                this.setErrorMsg(errorMsg);
            }
        }
        return i;
    }
    
    public int pingOnJNI(final String s, final short portNum) {
        final StringBuffer errorMsg = new StringBuffer();
        this.m_errorMsg = new String();
        this.m_portNum = portNum;
        final int pingOn = this.pingOn(s, portNum, errorMsg);
        this.setErrorMsg(errorMsg);
        if (pingOn != 0) {
            this.m_portNum = 0;
        }
        return pingOn;
    }
    
    public int pingOffJNI(final String s) {
        final StringBuffer errorMsg = new StringBuffer();
        this.m_errorMsg = new String();
        final int pingOff = this.pingOff(s, errorMsg);
        this.setErrorMsg(errorMsg);
        this.m_portNum = 0;
        return pingOff;
    }
    
    public int getIpverJNI() {
        final short n = 0;
        final int ipver = this.getIpver(n);
        if (n == 1) {
            this.m_ipv6 = true;
        }
        else {
            this.m_ipv6 = false;
        }
        return ipver;
    }
    
    public Vector getProcessInfoData() {
        return this.m_processInfo;
    }
    
    public int getNumProcessInfo() {
        return this.m_processInfo.size();
    }
    
    public String getProcessInfoAt(final int index) {
        if (index >= this.m_processInfo.size()) {
            return null;
        }
        return this.m_processInfo.elementAt(index);
    }
    
    public String getErrorMsg() {
        if (this.m_errorMsg == null) {
            return new String("");
        }
        return this.m_errorMsg;
    }
    
    private void setErrorMsg(final StringBuffer sb) {
        final int length = sb.length();
        if (length <= 80 || sb.toString().indexOf(10) != -1) {
            this.m_errorMsg = sb.toString();
            return;
        }
        for (int i = 61; i < length; i += 80) {
            while (i < length && sb.charAt(i) != ' ') {
                ++i;
            }
            if (i >= length) {
                break;
            }
            sb.insert(i, '\n');
        }
        this.m_errorMsg = sb.toString();
    }
    
    public short getPortNumber() {
        return this.m_portNum;
    }
    
    public boolean IsIPv6() {
        return this.m_ipv6;
    }
    
    boolean loadStatus() {
        return ProDebugLib.libLoadSuccessfull;
    }
    
    public static void main(final String[] array) {
        final ProDebugLib proDebugLib = new ProDebugLib();
        System.out.println("Getting list of processes...");
        if (proDebugLib.getProcessInfoJNI() == 0) {
            final int numProcessInfo = proDebugLib.getNumProcessInfo();
            if (numProcessInfo == 0) {
                System.out.println("No Processes found!");
            }
            else {
                for (int i = 0; i < numProcessInfo; ++i) {
                    System.out.println(proDebugLib.getProcessInfoAt(i));
                }
            }
        }
        else {
            System.out.println("Error: " + proDebugLib.getErrorMsg());
        }
        final String s = "1000";
        if (proDebugLib.pingOnJNI(s, (short)3000) != 0) {
            System.out.println("Ping error: " + proDebugLib.getErrorMsg());
        }
        else {
            System.out.println("Ping successfull. Port is " + proDebugLib.getPortNumber());
        }
        if (proDebugLib.pingOffJNI(s) != 0) {
            System.out.println("Error when disabling debugging: " + proDebugLib.getErrorMsg());
        }
        else {
            System.out.println("Successfully disabled");
        }
    }
    
    static {
        ProDebugLib.libLoadSuccessfull = false;
        final String property = System.getProperty("os.name");
        try {
            if (!property.startsWith("Sun") && !property.startsWith("HP")) {
                if (!property.startsWith("AIX")) {
                    System.loadLibrary("prodbgtlr");
                    ProDebugLib.libLoadSuccessfull = true;
                    return;
                }
            }
            try {
                System.loadLibrary("prodbgtlr32");
                ProDebugLib.libLoadSuccessfull = true;
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                ProDebugLib.libLoadSuccessfull = true;
                System.loadLibrary("prodbgtlr");
            }
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError2) {
            ProDebugLib.libLoadSuccessfull = false;
            System.err.println("Cannot load the prodbgtlr library.");
        }
    }
}
