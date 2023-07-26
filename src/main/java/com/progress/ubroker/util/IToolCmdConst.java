// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public interface IToolCmdConst
{
    public static final int TOOL_CMD_VERSION = 3;
    public static final int COMMON_CMD_MIN = 1;
    public static final int GET_SVC_CONFIG = 1;
    public static final int SAVE_SVC_CONFIG = 2;
    public static final int REPLACE_PROP = 3;
    public static final int GET_PREF_PROP = 4;
    public static final int PREF_PROP_CHANGED = 5;
    public static final int GET_NS_NAMES = 6;
    public static final int GET_AR_NAMES = 7;
    public static final int GET_SSL_ALIAS_NAMES = 8;
    public static final int VAL_SAV_SVC_CFG = 9;
    public static final int LOAD_GUI_SCHEMA = 10;
    public static final int FETCH_RPM = 11;
    public static final int GET_GUI_SCHEMA_PROP_FN = 12;
    public static final int GET_PROPERTY_VALUE = 13;
    public static final int VALIDATE_ONE_PROPERTY = 14;
    public static final int NS_INST_REFERENCES = 15;
    public static final int COMMON_CMD_MAX = 50;
    public static final int GUITOOL_CMD_MIN = 100;
    public static final int START_SVC = 100;
    public static final int STOP_SVC = 101;
    public static final int PING_SVC = 102;
    public static final int ADD_NEW_GUI_TOOL = 103;
    public static final int REMOVE_GUI_TOOL = 104;
    public static final int GET_UB_SUM_STAT_LBL = 105;
    public static final int GET_UB_SUM_STAT_DATA = 106;
    public static final int GET_UB_STAT_LBL = 107;
    public static final int GET_UB_STAT_DATA = 108;
    public static final int TRIM_UBSRVR_BY = 109;
    public static final int ADD_UBSRVR = 110;
    public static final int GET_NS_SUM_STAT_DATA = 111;
    public static final int GET_NS_STAT_DATA = 112;
    public static final int GET_NS_LOCATION = 113;
    public static final int GUITOOL_CMD_MAX = 200;
    public static final int MAX_CMD = 200;
    public static final int CMD_NOTEXEC = 0;
    public static final int CMD_SUCCESS = 1;
    public static final int CMD_FAILED = -1;
    public static final int CMD_INVALID = -2;
    public static final int CMD_MISSING_ARG = -3;
    public static final int SCHEMA_FN_IDX = 0;
    public static final int PROP_FN_IDX = 1;
}
