// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import com.progress.common.util.ICfgConst;

public class UBCfgPropTable implements ICfgConst
{
    static boolean propTableInited;
    static final Hashtable propTable;
    static final Hashtable valTable;
    
    public static long getPropNum(final String key) {
        Object value = new Object();
        if (UBCfgPropTable.propTable.size() == 0) {
            initPropTable();
        }
        try {
            value = UBCfgPropTable.propTable.get(key);
        }
        catch (Exception ex) {
            System.out.println(" " + ex.toString());
        }
        long longValue;
        if (value == null) {
            longValue = 0L;
        }
        else {
            longValue = (long)value;
        }
        return longValue;
    }
    
    public static String[] getPropValList(final long value) {
        String[] value2 = (String[])new Object();
        if (UBCfgPropTable.valTable.size() == 0) {
            initValTable();
        }
        try {
            value2 = UBCfgPropTable.valTable.get(new Long(value));
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        String[] array;
        if (value2 == null) {
            array = null;
        }
        else {
            array = value2;
        }
        return array;
    }
    
    private static void initPropTable() {
        for (int length = ICfgConst.propNameToNumTable.length, i = 0; i < length; ++i) {
            UBCfgPropTable.propTable.put(ICfgConst.propNameToNumTable[i].propName, new Long(ICfgConst.propNameToNumTable[i].propNum));
        }
    }
    
    private static void initValTable() {
        for (int length = ICfgConst.propNumToValTable.length, i = 0; i < length; ++i) {
            UBCfgPropTable.valTable.put(new Long(ICfgConst.propNumToValTable[i].propNum), ICfgConst.propNumToValTable[i].propVal);
        }
    }
    
    static {
        UBCfgPropTable.propTableInited = false;
        propTable = new Hashtable();
        valTable = new Hashtable();
    }
}
