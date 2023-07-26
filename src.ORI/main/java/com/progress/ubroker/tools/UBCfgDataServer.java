// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import com.progress.common.util.ICfgConst;

public class UBCfgDataServer implements ICfgConst
{
    private Hashtable m_srvrParamTable;
    private boolean m_isInitialized;
    
    public UBCfgDataServer() {
        this.m_srvrParamTable = null;
        this.m_isInitialized = false;
        this.loadSrvrStartupParamTable();
    }
    
    private void loadSrvrStartupParamTable() {
        this.m_srvrParamTable = new Hashtable();
        try {
            for (int i = 0; i < ICfgConst.OROD_STARTUP_LST.length; ++i) {
                this.m_srvrParamTable.put(ICfgConst.OROD_STARTUP_LST[i][0], new dSTableObj(ICfgConst.OROD_STARTUP_LST[i][1], ICfgConst.OROD_STARTUP_LST[i][0]));
            }
            this.m_isInitialized = true;
        }
        catch (Exception obj) {
            System.out.println("Error loading OR/OD srvrStartupParam table " + obj);
        }
    }
    
    public dSTableObj getTableVal(final String key) {
        return this.m_srvrParamTable.get(key);
    }
    
    public void setTableVal(final String key, final dSTableObj value) {
        this.m_srvrParamTable.put(key, value);
    }
    
    public String validateORODSrvrStartupParam(final String s) {
        boolean b = false;
        s.toCharArray();
        String string = null;
        final StringBuffer sb = new StringBuffer();
        try {
            final Enumeration<String> elements = (Enumeration<String>)this.tokenizeString(s).elements();
            String str = null;
            String str2 = null;
            String s2 = null;
            int n = 0;
            while (elements.hasMoreElements()) {
                final String s3 = elements.nextElement();
                if (s3.startsWith("-")) {
                    if (n != 0) {
                        str2 = "";
                        s2 = s3;
                    }
                    else {
                        str = s3;
                    }
                    n = 1;
                }
                else {
                    if (s2 != null) {
                        str = s2;
                        s2 = null;
                    }
                    n = 0;
                    str2 = s3;
                }
                if (str != null && str2 != null) {
                    if (!this.verifySrvrStartupParam(str, str2)) {
                        sb.append(str + "," + str2 + " ");
                        b = true;
                    }
                    str2 = null;
                }
            }
        }
        catch (StringIndexOutOfBoundsException ex) {}
        if (b) {
            string = sb.toString();
        }
        return string;
    }
    
    private Vector tokenizeString(final String s) {
        Vector<String> vector = new Vector<String>();
        try {
            final char[] charArray = s.toCharArray();
            StringBuffer sb = null;
            int n = 0;
            for (int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == ' ') {
                    if (n != 0) {
                        n = 0;
                        vector.addElement(sb.toString());
                    }
                }
                else {
                    if (n == 0) {
                        sb = new StringBuffer();
                        n = 1;
                    }
                    sb.append(charArray[i]);
                }
            }
            vector.addElement(sb.toString());
        }
        catch (Exception ex) {
            vector = null;
        }
        return vector;
    }
    
    public boolean verifySrvrStartupParam(final String s, final String s2) {
        boolean b = true;
        if (!this.m_isInitialized) {
            this.loadSrvrStartupParamTable();
        }
        final dSTableObj tableVal = this.getTableVal(s);
        if (tableVal != null) {
            if (!tableVal.compareValue(s2)) {
                b = false;
            }
            if (tableVal.getValue().equals("X") && !s2.equals("")) {
                b = true;
            }
            tableVal.setChecked(true);
            this.setTableVal(s, tableVal);
        }
        return b;
    }
    
    public String verifyAllSrvrStartupParams() {
        boolean b = true;
        final StringBuffer sb = new StringBuffer();
        String string = null;
        if (!this.m_isInitialized) {
            this.loadSrvrStartupParamTable();
        }
        try {
            final Enumeration<dSTableObj> elements = (Enumeration<dSTableObj>)this.m_srvrParamTable.elements();
            while (elements.hasMoreElements()) {
                final dSTableObj dsTableObj = elements.nextElement();
                if (!dsTableObj.isChecked()) {
                    sb.append(dsTableObj.getKey() + " ");
                    b = false;
                }
            }
        }
        catch (Exception obj) {
            System.out.println("Error loading OR/OD srvrStartupParam table " + obj);
        }
        if (!b) {
            string = sb.toString();
        }
        return string;
    }
    
    public int verifyOREnv(final String[][] array) {
        int n = 0;
        int n2 = 0;
        try {
            for (int i = 0; i < array.length; ++i) {
                if (array[i][0].equals("ORACLE_HOME") && array[i][1] != null && !array[i][1].equals("")) {
                    ++n2;
                    if (array[i][1].equals("C:\\orant")) {
                        n = -2;
                    }
                }
                if (array[i][0].equals("ORACLE_SID") && array[i][1] != null && !array[i][1].equals("")) {
                    ++n2;
                    if (array[i][1].equals("ORCL")) {
                        n = -2;
                    }
                }
                if (n2 == 2) {
                    break;
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Exception verifying Oracle environment: " + ex.toString());
            n = -1;
        }
        if (n2 < 2) {
            n = -1;
        }
        return n;
    }
    
    public int verifyODBCEnv(final String[][] array) {
        int n = 0;
        try {
            int i = 0;
            while (i < array.length) {
                if (array[i][0].equals("ODBC_HOME") && array[i][1] != null && !array[i][1].equals("")) {
                    if (array[i][1].equals("/usr/odbnt") || array[i][1].equals("/usr/odbunx")) {
                        n = -2;
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Exception verifying ODBC environment: " + ex.toString());
            n = -1;
        }
        return n;
    }
}
