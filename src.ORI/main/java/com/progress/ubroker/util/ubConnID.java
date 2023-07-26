// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class ubConnID
{
    public static String DELIMITER;
    public String m_wholeID;
    public String m_hostName;
    public String m_brokerName;
    public int m_portNumber;
    public String m_uuid;
    
    public ubConnID() {
        this.m_wholeID = null;
        this.m_hostName = null;
        this.m_brokerName = null;
        this.m_portNumber = 0;
        this.m_uuid = null;
    }
    
    public ubConnID(final String wholeID) {
        this.m_wholeID = wholeID;
        this.parseParts();
    }
    
    public String create(final String hostName, final String brokerName, final int portNumber, final String uuid) {
        this.m_hostName = hostName;
        this.m_brokerName = brokerName;
        this.m_portNumber = portNumber;
        this.m_uuid = uuid;
        return this.m_wholeID = this.makeWholeID();
    }
    
    private String makeWholeID() {
        return this.m_hostName + ubConnID.DELIMITER + this.m_brokerName + ubConnID.DELIMITER + this.m_portNumber + ubConnID.DELIMITER + this.m_uuid;
    }
    
    private void parseParts() {
        String s = this.m_wholeID;
        int i = 99;
        int n = 0;
        final int length = ubConnID.DELIMITER.length();
        while (i > 0) {
            i = s.indexOf(ubConnID.DELIMITER);
            String substring;
            if (i > 0) {
                substring = s.substring(0, i);
                s = s.substring(i + length);
            }
            else {
                substring = s;
            }
            switch (n) {
                case 0: {
                    this.m_hostName = substring;
                    break;
                }
                case 1: {
                    this.m_brokerName = substring;
                    break;
                }
                case 2: {
                    this.m_portNumber = new Integer(substring);
                    break;
                }
                case 3: {
                    this.m_uuid = substring;
                    break;
                }
            }
            ++n;
        }
    }
    
    static {
        ubConnID.DELIMITER = "::";
    }
}
