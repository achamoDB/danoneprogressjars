// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.dsmapi;

public interface DSMConstants
{
    public static final String NULL = null;
    public static final int DSMAREA_TYPE_INVALID = 0;
    public static final int DSMAREA_TYPE_CBI = 2;
    public static final int DSMAREA_TYPE_BI = 3;
    public static final int DSMAREA_TYPE_TL = 4;
    public static final int DSMAREA_TYPE_EVENT = 5;
    public static final int DSMAREA_TYPE_DATA = 6;
    public static final int DSMAREA_TYPE_AI = 7;
    public static final int DSMAREA_TYPE_MIXED = 255;
    public static final int DSMAREA_TYPE_MAXIMUM = 255;
    public static final int DSMAREA_INVALID = 0;
    public static final int DSMAREA_CONTROL = 1;
    public static final int DSMAREA_CBI = 2;
    public static final int DSMAREA_BI = 3;
    public static final int DSMAREA_TL = 4;
    public static final int DSMAREA_SCHEMA = 6;
    public static final int DSMAREA_DEFAULT = 6;
    public static final int DSMAREA_BASE = 7;
    public static final int DSMAREA_MAXIMUM = 65535;
    public static final int DSMEXTENT_INVALID = 0;
    public static final int DSMEXTENT_MINIMUM = 1;
    public static final int DSMEXTENT_MAXIMUM = 256;
    public static final int DSMTABLE_USER_LAST = 32767;
    public static final int DSMTABLE_USER_FIRST = 1;
    public static final int DSMTABLE_INVALID = 0;
    public static final int DSMTABLE_SYS_FIRST = -1;
    public static final int DSMTABLE_SYS_LAST = -32767;
    public static final int DSMINDEX_USER_LAST = 32767;
    public static final int DSMINDEX_USER_FIRST = 0;
    public static final int DSMINDEX_INVALID = -1;
    public static final int DSMINDEX_SYS_FIRST = -2;
    public static final int DSMINDEX_SYS_LAST = -32767;
    public static final int DSMOBJECT_INVALID = 0;
    public static final int DSMOBJECT_INVALID_MIXEDTABLE = 0;
    public static final int DSMOBJECT_INVALID_MIXEDINDEX = -1;
    public static final int DSMOBJECT_INVALID_TABLE = 0;
    public static final int DSMOBJECT_INVALID_INDEX = -1;
    public static final int DSMOBJECT_TABLE = 1;
    public static final int DSMOBJECT_INDEX = 2;
    public static final int DSMOBJECT_BLOB = 3;
    public static final int DSMOBJECT_RECORD = 4;
    public static final int DSMOBJECT_JAVACLASS = 5;
    public static final int DSMOBJECT_JAVAINDEX = 6;
    public static final int DSMOBJECT_MIXED = 1024;
    public static final int DSMOBJECT_MIXTABLE = 1;
    public static final int DSMOBJECT_MIXINDEX = 2;
    public static final int DSMTABLE_SCANASSOCIATE = 1;
    public static final int DSMINDEX_UNIQUE = 1;
    public static final int DSMINDEX_WORD = 2;
    public static final int DSMINDEX_MULTI_OBJECT = 4;
    public static final long DSMBLOBMAXLEN = 31999L;
    public static final int DSMTXN_START = 1;
    public static final int DSMTXN_PHASE1 = 22;
    public static final int DSMTXN_PHASE1Q2 = 23;
    public static final int DSMTXN_COMMIT = 65;
    public static final int DSMTXN_ABORT = 82;
    public static final int DSMTXN_ABORTED = 83;
    public static final int DSMTXN_FORCE = 84;
    public static final int DSMTXN_SAVE = 129;
    public static final int DSMTXN_UNSAVE = 146;
    public static final int DSMTXN_SAVE_START = 0;
    public static final int DSMTXN_SAVE_MINIMUM = 1;
    public static final int DSMTXN_SAVE_MAXIMUM = 32767;
    public static final int DSMREGULAR = 0;
    public static final int DSMPARTIAL = 1;
    public static final int DSMUNIQUE = 2;
    public static final int DSMDBKEY = 3;
    public static final int DSMCURSOR_INVALID = -1;
    public static final int DSMGETTAG = 1;
    public static final int DSMGETFIRST = 2;
    public static final int DSMGETLAST = 3;
    public static final int DSMGETGE = 4;
    public static final int DSMFINDFIRST = 1;
    public static final int DSMFINDLAST = 2;
    public static final int DSMFINDNEXT = 3;
    public static final int DSMFINDPREV = 4;
    public static final int DSMFINDGE = 5;
    public static final int DSMCREXTABLE = 1;
    public static final int DSMUPDEXTABLE = 1;
    public static final int EXRAW = 1;
    public static final int EXNET = 2;
    public static final int EXEXISTS = 4;
    public static final int EXFIXED = 8;
    public static final int EXFORCE = 16;
    public static final int DSM_TAGDB_BASE = 0;
    public static final int DSM_TAGUSER_BASE = 1000000;
    public static final int DSM_TAGDB_DBNAME = 1;
    public static final int DSM_TAGDB_ACCESS_ENV = 2;
    public static final int DSM_TAGDB_ACCESS_TYPE = 3;
    public static final int DSM_TAGDB_MODULE_RESTRICTIONS = 4;
    public static final int DSM_TAGDB_MSG_NUMBER = 5;
    public static final int DSM_TAGDB_MSG_DISPLAY = 6;
    public static final int DSM_TAGDB_MSG_TRACE = 7;
    public static final int DSM_4GL_ENGINE = 1;
    public static final int DSM_SQL_ENGINE = 2;
    public static final int DSM_JAVA_ENGINE = 4;
    public static final int DSM_ACCESS_STARTUP = 1;
    public static final int DSM_ACCESS_SHARED = 2;
    public static final int DSM_TAGUSER_LOCK_TIMEOUT = 1000001;
    public static final int DSM_TAGUSER_NAME = 1000002;
    public static final int DSM_TAGUSER_PASSWORD = 1000003;
    public static final int DSMCONTEXTDB = 1;
    public static final int DSMCONTEXTUSER = 2;
    public static final int DSMCONTEXTALL = 3;
    public static final int DSMENTERPRISE = 1;
    public static final int DSMTRITON = 2;
    public static final int DSM_DB_OPENFILE = 1;
    public static final int DSM_DB_OPENDB = 2;
    public static final int DSM_DB_SINGLE = 4;
    public static final int DSM_DB_MULTI = 8;
    public static final int DSM_DB_RDONLY = 16;
    public static final int DSM_DB_SCHMC = 32;
    public static final int DSM_DB_CONVERS = 64;
    public static final int DSM_DB_2PHASE = 128;
    public static final int DSM_DB_AIEND = 256;
    public static final int DSM_DB_NOWORD = 512;
    public static final int DSM_DB_TLEND = 1024;
    public static final int DSM_DB_XLCH = 2048;
    public static final int DSM_DB_AIROLL = 4096;
    public static final int DSM_DB_AIBEGIN = 8192;
    public static final int DSM_DB_NOXLTABLES = 16384;
    public static final int DSM_DB_CONTROL = 32768;
    public static final int DSM_DB_REST = 65536;
    public static final int DSMNICEBIT = 8;
    public static final int DSM_LK_WAIT = 0;
    public static final int DSM_LK_NOLOCK = 0;
    public static final int DSM_LK_SHARE = 1;
    public static final int DSM_LK_EXCL = 2;
    public static final int DSM_LK_TYPE = 3;
    public static final int DSM_LK_NOWAIT = 16;
    public static final int DSM_LK_AUTOREL = 32;
    public static final int DSM_LK_UPGRD = 4;
    public static final int DSM_LK_LIMBO = 8;
    public static final int DSM_LK_QUEUED = 32;
    public static final int DSM_LK_NOHOLD = 64;
    public static final int DSM_LK_PURGED = 128;
    public static final int DSM_LK_INTASK = 512;
    public static final String BASEKEY_PROGRESS_TRITON = "SOFTWARE\\Progress\\Triton\\1.0";
    public static final String TRITON_INSTALL_DIR = "InstallRoot";
    public static final String TRITON_CACHE_DIR = "LocalCacheRoot";
    public static final String TRITON_INSTALL = "Triton_InstallRoot";
    public static final String TRITON_CACHE = "Triton_LocalCacheRoot";
    public static final int DSM_S_BLOBOK = 0;
    public static final int DSM_S_BLOBNOMORE = -100001;
    public static final int DSM_S_BLOBBADCNTX = -100002;
    public static final int DSM_S_BLOBLIMIT = -100003;
    public static final int DSM_S_BLOBTOOBIG = -100004;
    public static final int DSM_S_BLOBNOMEMORY = -100005;
    public static final int DSM_S_BLOBDNE = -100006;
    public static final int DSM_S_BLOBBAD = -100007;
    public static final int DSM_S_SEQBAD = -100008;
    public static final int DSM_S_SEQCYC = -100009;
    public static final int DSM_S_SEQNOTFND = -100010;
    public static final int DSM_S_SEQFULL = -100011;
    public static final int DSM_S_SEQDUP = -100012;
    public static final int DSM_S_SEQRANGE = -100013;
    public static final int DSM_S_SUCCESS = 0;
    public static final int DSM_S_FAILURE = -1;
    public static final int DSM_S_NOCURSOR = -2;
    public static final int DSM_S_KEYNOTFOUND = -3;
    public static final int DSM_S_NOTFOUND = -3;
    public static final int DSM_S_BADLOCK = -4;
    public static final int DSM_S_DUPLICATE = -5;
    public static final int DSM_S_IXDLOCK = -1210;
    public static final int DSM_S_TSKGONE = -1211;
    public static final int DSM_S_RQSTQUED = -1216;
    public static final int DSM_S_RQSTREJ = -1217;
    public static final int DSM_S_CTRLC = -1218;
    public static final int DSM_S_IXDUPKEY = 1;
    public static final int DSM_S_IXINCKEY = -2;
    public static final int DSM_S_FNDAMBIG = -1213;
    public static final int DSM_S_FNDFAIL = -1214;
    public static final int DSM_S_ENDLOOP = -1215;
    public static final int DSM_S_AHEADORNG = -1220;
    public static final int DSM_S_LKTBFULL = -1221;
    public static final int DSM_S_NOSTG = -1222;
    public static final int DSM_S_RECTOOBIG = -1223;
    public static final int DSM_S_CURSORERR = -1224;
    public static final int DSM_S_FDDUPKEY = -1235;
    public static final int DSM_S_FDSEQFULL = -1241;
    public static final int DSM_S_FDSEQCYC = -1242;
    public static final int DSM_S_RMNOTFND = -1;
    public static final int DSM_S_RMEXISTS = -2;
    public static final int DSM_S_RECORD_BUFFER_TOO_SMALL = -20000;
    public static final int DSM_S_DSMOBJECT_CREATE_FAILED = -20001;
    public static final int DSM_S_DSMOBJECT_DELETE_FAILED = -20002;
    public static final int DSM_S_DSMOBJECT_LOCK_FAILED = -20003;
    public static final int DSM_S_DSMOBJECT_UNLOCK_FAILED = -20004;
    public static final int DSM_S_AREA_NOT_INIT = -20005;
    public static final int DSM_S_AREA_NULL = -20006;
    public static final int DSM_S_AREA_NO_EXTENT = -20007;
    public static final int DSM_S_AREA_NUMBER_TOO_LARGE = -20008;
    public static final int DSM_S_AREA_ALREADY_ALLOCATED = -20009;
    public static final int DSM_S_INVALID_EXTENT_CREATE = -20010;
    public static final int DSM_S_NO_SUCH_OBJECT = -20011;
    public static final int DSM_S_DSMLOOKUP_REGOPENFAILED = -20012;
    public static final int DSM_S_DSMLOOKUP_QUERYFAILED = -20013;
    public static final int DSM_S_DSMLOOKUP_KEYINVALID = -20014;
    public static final int DSM_S_DSMLOOKUP_INVALIDSIZE = -20015;
    public static final int DSM_S_DSMCONTEXT_ALLOCATION_FAILED = -20016;
    public static final int DSM_S_DSMBADCONTEXT = -20017;
    public static final int DSM_S_NO_USER_CONTEXT = -20018;
    public static final int DSM_S_INVALID_TAG = -20019;
    public static final int DSM_S_INVALID_TAG_VALUE = -20020;
    public static final int DSM_S_USER_STILL_CONNECTED = -20021;
    public static final int DSM_S_DB_ALREADY_CONNECTED = -20022;
    public static final int DSM_S_INVALID_CONTEXT_COPY_MODE = -20023;
    public static final int DSM_S_BADCONTEXT = -20024;
    public static final int DSM_S_NOWAIT = -20025;
    public static final int DSM_S_BADSAVE = -20026;
    public static final int DSM_S_TRANSACTION_ALREADY_STARTED = -20027;
    public static final int DSM_S_NO_TRANSACTION = -20028;
    public static final int DSM_S_INVALID_TRANSACTION_CODE = -20029;
}
