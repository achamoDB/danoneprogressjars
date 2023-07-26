// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public interface ISQLConstants
{
    public static final int CHAR_MODE = 0;
    public static final int GUI_MODE = 1;
    public static final String COMMAND_PREFIX = "@";
    public static final String ESCAPE_SYMBOL = "!";
    public static final String NEWLINE = System.getProperty("line.separator");
    public static final String DIR_SEPARATOR = System.getProperty("file.separator");
    public static final String WORK_DIR = System.getProperty("user.dir") + ISQLConstants.DIR_SEPARATOR;
    public static final String SQL_EXPLORER_PROMPT = "SQLExplorer>";
    public static final String SQL_EXPLORER_LOGFILE = ISQLConstants.WORK_DIR + "sqlexp.log";
    public static final String SQL_EXPLORER_EXCPFILE = ISQLConstants.WORK_DIR + "SQLExplorer.exceptions";
    public static final String SQL_EXPLORER_HISTORY = ISQLConstants.WORK_DIR + "SQLExplorer.history";
    public static final String SQL_EXPLORER_SESSION = ISQLConstants.WORK_DIR + "SQLExplorerSession.log";
    public static final String SQL_EXPLORER_SAVEFILE = "SQLExplorer.sql";
    public static final String SQL_EXPLORER_PROPERTIES = ISQLConstants.WORK_DIR + "SQLExplorer.properties";
    public static final String SQL_EXPLORER_FONTS = ISQLConstants.WORK_DIR + "SQLExplorer.fonts";
    public static final int COLUMN_BUFFER_SIZE = 31995;
    public static final int INPUT_BUFFER_SIZE = 131000;
    public static final int OUTPUT_BUFFER_SIZE = 131000;
    public static final int OK = 0;
    public static final int NOT_OK = 1;
    public static final int CHAR_MODE_NOT_SUPPORTED = 2;
    public static final int GUI_MODE_NOT_SUPPORTED = 3;
    public static final int SQL_MORE_CONTINUE = 0;
    public static final int SQL_MORE_END = 1;
    public static final String PLATFORM_NAME = System.getProperty("os.name").toLowerCase();
    public static final boolean WINDOWS_PLATFORM = ISQLConstants.PLATFORM_NAME.startsWith("windows");
    public static final boolean HAS_SHELL_SUPPORT = !ISQLConstants.PLATFORM_NAME.startsWith("windows 9");
    public static final String I18N_HEADER = "\u0001\u0002\u0003";
    public static final int[] NUMERIC_TYPES = { -7, -6, 5, 4, -5, 6, 7, 8, 2, 3 };
    public static final int STATEMENT_TYPE_UPDATE = 0;
    public static final int STATEMENT_TYPE_QUERY = 1;
    public static final int STATEMENT_TYPE_DDL = 2;
    public static final int STATEMENT_TYPE_COMMIT = 3;
    public static final int STATEMENT_TYPE_ROLLBACK = 4;
    public static final int STATEMENT_TYPE_CALL = 5;
    public static final int STATEMENT_TYPE_GET = 6;
    public static final int STATEMENT_TYPE_SET = 7;
    public static final int STATEMENT_TYPE_SHOW = 8;
    public static final int STATEMENT_TYPE_OTHER = 9;
    public static final int GET_TRANS_LEVEL = 0;
    public static final int SET_TRANS_LEVEL = 0;
    public static final int SHOW_TRANS_LEVEL = 0;
    public static final int SHOW_CATALOGS = 1;
    public static final String[] STATEMENT_TYPE_LITERALS = { "Update", "Query", "DDL", "Commit", "Rollback", "Call", "Get", "Set", "Show", "Other" };
    public static final String[][] STATEMENT_TYPES_LIST = { { "update", "insert", "delete" }, { "select" }, { "drop", "alter", "create", "grant", "revoke", "rename" }, { "commit" }, { "rollback" }, { "call" }, { "get" }, { "set" }, { "show" } };
    public static final String[][] GET_STATEMENT_LIST = { { "get", "transaction", "isolation", "level" } };
    public static final String[][] SET_STATEMENT_LIST = { { "set", "transaction", "isolation", "level" } };
    public static final String[][] SHOW_STATEMENT_LIST = { { "show", "transaction", "isolation", "level" }, { "show", "catalogs" } };
}
