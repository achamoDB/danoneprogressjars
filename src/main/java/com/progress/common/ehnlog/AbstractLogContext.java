// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.Enumeration;
import java.util.Vector;

abstract class AbstractLogContext implements LogContext
{
    private Vector m_entrytypeNames;
    private static final int TABLE_SIZE = 100;
    private _TBL_ENTRYTYPES[] m_entrytypeTable;
    private long m_logEntryTypes;
    private byte[] m_logSublevelArray;
    private boolean m_logSublevelUsed;
    private String m_hdrMessage;
    private int m_sizeLongestEntryTypeName;
    
    AbstractLogContext() {
        this.m_entrytypeNames = new Vector(64);
        this.m_entrytypeTable = new _TBL_ENTRYTYPES[100];
        this.m_logSublevelArray = new byte[64];
        this.m_logSublevelUsed = false;
        this.m_hdrMessage = "";
        this.m_sizeLongestEntryTypeName = 1;
    }
    
    public abstract String getLogContextName();
    
    public abstract void initEntrytypeNames() throws LogException;
    
    protected int getEntrytypesCapacity() {
        return this.m_entrytypeNames.capacity();
    }
    
    protected void insertEntrytypeName(final String obj, final int index) throws LogException {
        this.m_entrytypeNames.insertElementAt(obj, index);
    }
    
    public void resetLogEntryTypes() {
        this.m_logEntryTypes = 0L;
    }
    
    protected void setEntrytypeName(final String obj, final int index) throws LogException {
        this.m_entrytypeNames.setElementAt(obj, index);
        final int length = obj.length();
        if (this.m_sizeLongestEntryTypeName < length) {
            this.m_sizeLongestEntryTypeName = length;
        }
    }
    
    protected int putEntrytypeName(final String s) throws LogException {
        final int size = this.m_entrytypeNames.size();
        if (null == s) {
            throw new LogException("Cannot add a null entry type name");
        }
        return size;
    }
    
    protected void addToEntrytypeTable(final String original, final long entry_bits, final int entry_index, final int n) throws LogException {
        this.m_entrytypeTable[n] = new _TBL_ENTRYTYPES();
        this.m_entrytypeTable[n].entry_name = new String(original);
        this.m_entrytypeTable[n].entry_bits = entry_bits;
        this.m_entrytypeTable[n].entry_index = entry_index;
    }
    
    public long getEntrytypeBitAt(final int n) {
        if (n < 100) {
            return 0L;
        }
        try {
            if (this.m_entrytypeTable[n] == null || this.m_entrytypeTable[n].entry_bits == -1L) {
                return 0L;
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return 0L;
        }
        return this.m_entrytypeTable[n].entry_bits;
    }
    
    public String getEntrytypeName(final int index) {
        String s;
        try {
            s = this.m_entrytypeNames.elementAt(index);
        }
        catch (Exception ex) {
            s = this.m_entrytypeNames.elementAt(0);
        }
        return s;
    }
    
    public String entrytypeNameAt(final int index) throws LogException {
        try {
            return this.m_entrytypeNames.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Cannot locate entry type in log context");
        }
    }
    
    public Enumeration entrytypeNames() {
        return this.m_entrytypeNames.elements();
    }
    
    public String getMsgHdr() {
        return this.m_hdrMessage;
    }
    
    public void setMsgHdr(final String hdrMessage) {
        this.m_hdrMessage = hdrMessage;
    }
    
    public String setMsgHdr(final String s, final Object[] array) {
        try {
            this.m_hdrMessage = ExceptionMessageAdapter.getMessage(s, array);
        }
        catch (Exception ex) {
            System.err.println("Error Formatting Header: " + ex.toString());
        }
        return this.m_hdrMessage;
    }
    
    public String getErrorStringTag() {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < this.m_sizeLongestEntryTypeName; ++i) {
            buffer.append("-");
        }
        return new String(buffer);
    }
    
    public long getLogEntries() {
        return this.m_logEntryTypes;
    }
    
    public boolean getLogSubLevelUsed() {
        return this.m_logSublevelUsed;
    }
    
    public byte[] getLogSublevelArray() {
        return this.m_logSublevelArray;
    }
    
    public String parseEntrytypeString(final String s, final int n) {
        String string = "";
        final StringBuffer sb = new StringBuffer();
        if (s == null) {
            return "";
        }
        int endIndex = s.indexOf(44);
        int i = 0;
        while (i != -1) {
            boolean b = false;
            String substring = "";
            String s2;
            if (endIndex == -1) {
                s2 = s.substring(i);
            }
            else {
                s2 = s.substring(i, endIndex);
            }
            if (i != endIndex) {
                final int index = s2.indexOf(58);
                final int index2 = s2.indexOf(".*");
                String s3 = s2;
                if (index > -1) {
                    substring = s2.substring(index + 1);
                    s3 = s2.substring(0, index);
                }
                if (index2 > -1) {
                    s3 = s2.substring(0, index2);
                }
                for (int n2 = 0; n2 < 100 && this.m_entrytypeTable[n2] != null; ++n2) {
                    if (this.m_entrytypeTable[n2].entry_bits == -1L) {
                        break;
                    }
                    if (index2 == -1) {
                        if (s3.compareToIgnoreCase(this.m_entrytypeTable[n2].entry_name) == 0) {
                            b = true;
                            sb.append(this.SetLoggingBitMask(this.m_entrytypeTable[n2].entry_bits, substring, n));
                            break;
                        }
                    }
                    else {
                        final String entry_name = this.m_entrytypeTable[n2].entry_name;
                        int compareToIgnoreCase;
                        if (entry_name.length() >= index2) {
                            compareToIgnoreCase = s3.compareToIgnoreCase(this.m_entrytypeTable[n2].entry_name.substring(0, index2));
                        }
                        else {
                            compareToIgnoreCase = -1;
                        }
                        if (compareToIgnoreCase == 0) {
                            final String substring2 = entry_name.substring(index2, index2 + 1);
                            if (substring2.compareTo(".") == 0 || substring2.length() <= 0) {
                                sb.append(this.SetLoggingBitMask(this.m_entrytypeTable[n2].entry_bits, substring, n));
                                b = true;
                            }
                        }
                    }
                }
                if (!b) {
                    sb.append("Ignoring unknown entrytype " + s2 + "\n");
                }
                else {
                    string = string + s2 + ",";
                }
            }
            i = endIndex + 1;
            if (endIndex == -1) {
                i = -1;
            }
            endIndex = s.indexOf(44, endIndex + 1);
        }
        if (string.length() > 0) {
            sb.append("Log entry types activated: " + string + "\n");
        }
        else {
            sb.append("No entry types are activated.\n");
        }
        return sb.toString();
    }
    
    private String SetLoggingBitMask(final long n, final String s, final int n2) {
        boolean b = false;
        int int1 = 0;
        final StringBuffer sb = new StringBuffer();
        if (s.length() > 0) {
            if (s.length() > 0) {
                try {
                    int1 = Integer.parseInt(s, 10);
                }
                catch (NumberFormatException ex) {
                    int1 = 0;
                }
                if (int1 < 2) {
                    sb.append("Invalid logging sub level " + s + "\n");
                }
                else {
                    this.m_logSublevelUsed = true;
                    b = true;
                }
            }
            else {
                sb.append("You specified ':' with no value in the logEntryTypes paramater.\n");
            }
        }
        else {
            b = true;
        }
        if (b) {
            long n3 = n;
            this.m_logEntryTypes |= n3;
            for (int n4 = 0; n3 != 0L; n3 >>= 1, ++n4) {
                if ((n3 & 0x1L) > 0L) {
                    int n5;
                    if (int1 > 1) {
                        n5 = int1;
                    }
                    else {
                        n5 = n2;
                    }
                    this.m_logSublevelArray[n4] = (byte)n5;
                }
            }
        }
        return sb.toString();
    }
}
