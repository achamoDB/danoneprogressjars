// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.dsm;

import com.progress.common.exception.ProException;
import com.progress.common.log.ProLog;
import com.progress.common.util.InstallPath;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.MathContext;
import java.math.RoundingMode;
import java.math.BigDecimal;
import com.progress.common.util.JNIHandle;

public class DSMAPI implements IDSMConstants
{
    private static native int dsmContextCreateJNI(final JNIHandle p0);
    
    private static native int dsmContextGetLongJNI(final long p0, final int p1, final JNIHandle p2);
    
    private static native int dsmContextSetStringJNI(final long p0, final int p1, final int p2, final String p3);
    
    private static native int dsmContextSetLongJNI(final long p0, final int p1, final long p2);
    
    private static native int dsmContextCopyJNI(final long p0, final int p1);
    
    private static native int dsmContextWriteOptionsJNI(final long p0);
    
    private static native int dsmSrvctlGetFieldsJNI(final long p0, final ServerData p1);
    
    private static native int dsmSrvctlSetFieldsJNI(final long p0, final ServerData p1, final int p2);
    
    private static native int dsmSrvctlGetBestJNI(final long p0, final ServerData p1, final int p2);
    
    private static native int dsmSrvctlGetBestXJNI(final long p0, final ServerData p1, final int p2, final ServerData p3);
    
    private static native int dsmDatabaseProcessEventsJNI(final long p0);
    
    private static native int dsmWatchdogJNI(final long p0);
    
    private static native int dsmShutdownUserJNI(final long p0, final int p1);
    
    private static native int dsmUserConnectJNI(final long p0, final String p1, final int p2);
    
    private static native int dsmUserDisconnectJNI(final long p0, final int p1);
    
    private static native int recGetFieldJNI(final byte[] p0, final int p1, final dbFieldObj p2);
    
    public static DSMContextInfo createContext() throws DSMException {
        final JNIHandle jniHandle = new JNIHandle();
        final DSMBrokerContextInfo dsmBrokerContextInfo = new DSMBrokerContextInfo(jniHandle);
        final int dsmContextCreateJNI = dsmContextCreateJNI(jniHandle);
        if (dsmContextCreateJNI != 0) {
            throw new DSMException(dsmContextCreateJNI, "dsmContextCreate");
        }
        return dsmBrokerContextInfo;
    }
    
    public static DSMContext userConnect(final DSMContextInfo dsmContextInfo, final String s, final int n) throws DSMException {
        final DSMBrokerContext dsmBrokerContext = new DSMBrokerContext(dsmContextInfo);
        final int dsmUserConnectJNI = dsmUserConnectJNI(dsmContextInfo.getHandle().getAddr(), s, n);
        if (dsmUserConnectJNI != 0) {
            throw new DSMException(dsmUserConnectJNI, "dsmUserConnect");
        }
        return dsmBrokerContext;
    }
    
    public static void userDisconnect(final DSMContext dsmContext, final int n) throws DSMException {
        final int dsmUserDisconnectJNI = dsmUserDisconnectJNI(dsmContext.getContextInfo().getHandle().getAddr(), n);
        if (dsmUserDisconnectJNI != 0) {
            throw new DSMException(dsmUserDisconnectJNI, "dsmUserDisconnect");
        }
    }
    
    static void setLong(final DSMContextInfo dsmContextInfo, final int n, final long n2) throws DSMException {
        final int dsmContextSetLongJNI = dsmContextSetLongJNI(dsmContextInfo.getHandle().getAddr(), n, n2);
        if (dsmContextSetLongJNI != 0) {
            throw new DSMException(dsmContextSetLongJNI, "dsmContextSetLong");
        }
    }
    
    static int getLong(final DSMContextInfo dsmContextInfo, final int n) throws DSMException {
        final JNIHandle jniHandle = new JNIHandle();
        final int dsmContextGetLongJNI = dsmContextGetLongJNI(dsmContextInfo.getHandle().getAddr(), n, jniHandle);
        if (dsmContextGetLongJNI != 0) {
            throw new DSMException(dsmContextGetLongJNI, "dsmContextGetLong");
        }
        return (int)jniHandle.getAddr();
    }
    
    static void setString(final DSMContextInfo dsmContextInfo, final int n, final String s) throws DSMException {
        final int dsmContextSetStringJNI = dsmContextSetStringJNI(dsmContextInfo.getHandle().getAddr(), n, s.length(), s);
        if (dsmContextSetStringJNI != 0) {
            throw new DSMException(dsmContextSetStringJNI, "dsmContextSetString");
        }
    }
    
    static ServerData getBestServerData(final DSMContextInfo dsmContextInfo, final int n) throws DSMException {
        final ServerData serverData = new ServerData();
        final int dsmSrvctlGetBestJNI = dsmSrvctlGetBestJNI(dsmContextInfo.getHandle().getAddr(), serverData, n);
        if (dsmSrvctlGetBestJNI != 0) {
            throw new DSMException(dsmSrvctlGetBestJNI, "dsmSrvctlGetBest");
        }
        return serverData;
    }
    
    static ServerData getBestServerDataX(final DSMContextInfo dsmContextInfo, final int n, final ServerData serverData) throws DSMException {
        final ServerData serverData2 = new ServerData();
        final int dsmSrvctlGetBestXJNI = dsmSrvctlGetBestXJNI(dsmContextInfo.getHandle().getAddr(), serverData2, n, serverData);
        if (dsmSrvctlGetBestXJNI != 0) {
            throw new DSMException(dsmSrvctlGetBestXJNI, "dsmSrvctlGetBestX");
        }
        return serverData2;
    }
    
    static ServerData getServerData(final DSMContextInfo dsmContextInfo, final int id) throws DSMException {
        final ServerData serverData = new ServerData();
        serverData.setId(id);
        final int dsmSrvctlGetFieldsJNI = dsmSrvctlGetFieldsJNI(dsmContextInfo.getHandle().getAddr(), serverData);
        if (dsmSrvctlGetFieldsJNI != 0) {
            throw new DSMException(dsmSrvctlGetFieldsJNI, "dsmSrvctlGetFields");
        }
        return serverData;
    }
    
    static void setServerData(final DSMContextInfo dsmContextInfo, final ServerData serverData, final int n) throws DSMException {
        final int dsmSrvctlSetFieldsJNI = dsmSrvctlSetFieldsJNI(dsmContextInfo.getHandle().getAddr(), serverData, n);
        if (dsmSrvctlSetFieldsJNI != 0) {
            throw new DSMException(dsmSrvctlSetFieldsJNI, "dsmSrvctlSetFields");
        }
    }
    
    static void watchdog(final DSMContextInfo dsmContextInfo) throws DSMException {
        final int dsmWatchdogJNI = dsmWatchdogJNI(dsmContextInfo.getHandle().getAddr());
        if (dsmWatchdogJNI != 0) {
            throw new DSMException(dsmWatchdogJNI, "dsmWatchdog");
        }
    }
    
    static void writeContextOptions(final DSMContextInfo dsmContextInfo) throws DSMException {
        final int dsmContextWriteOptionsJNI = dsmContextWriteOptionsJNI(dsmContextInfo.getHandle().getAddr());
        if (dsmContextWriteOptionsJNI != 0) {
            throw new DSMException(dsmContextWriteOptionsJNI, "writeContextOptions");
        }
    }
    
    static void databaseProcessEvents(final DSMContextInfo dsmContextInfo) throws DSMException {
        final int dsmDatabaseProcessEventsJNI = dsmDatabaseProcessEventsJNI(dsmContextInfo.getHandle().getAddr());
        if (dsmDatabaseProcessEventsJNI != 0) {
            throw new DSMException(dsmDatabaseProcessEventsJNI, "dsmDatabaseProcessEvents");
        }
    }
    
    static void disconnectUsers(final DSMContextInfo dsmContextInfo, final int n) throws DSMException {
        final int dsmShutdownUserJNI = dsmShutdownUserJNI(dsmContextInfo.getHandle().getAddr(), n);
        if (dsmShutdownUserJNI != 0) {
            throw new DSMException(dsmShutdownUserJNI, "dsmShutdownUser");
        }
    }
    
    static void setShutdown(final DSMContextInfo dsmContextInfo, final boolean b) throws DSMException {
        long n;
        if (b) {
            n = 2L;
        }
        else {
            n = 1L;
        }
        final int dsmContextSetLongJNI = dsmContextSetLongJNI(dsmContextInfo.getHandle().getAddr(), 45, n);
        if (dsmContextSetLongJNI != 0) {
            throw new DSMException(dsmContextSetLongJNI, "dsmContextSetLong");
        }
    }
    
    public static dbFieldObj recGetBigValue(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(6);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetBigValue(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    public static dbFieldObj recGetLong64Value(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(18);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetLong64Value(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    public static dbFieldObj recGetDecimalValue(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(12);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetDecimalValue(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    public static dbFieldObj recGetDoubleValue(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(8);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetDoubleValue(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    private static dbFieldObj recGetIntValue(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(5);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetIntValue(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    public static dbFieldObj recGetTinyValue(final byte[] array, final int n) throws DSMException {
        dbFieldObj recGetByteValues = recGetByteValues(array, n);
        if (recGetByteValues != null && !recGetByteValues.isUnknown() && !recGetByteValues.isNull()) {
            recGetByteValues.byteValue = recGetByteValues.getByteArray()[0];
        }
        else {
            recGetByteValues = null;
        }
        return recGetByteValues;
    }
    
    public static dbFieldObj recGetByteValues(final byte[] array, final int i) throws DSMException {
        dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(14);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetByteValues(fieldID=" + i + ")");
        }
        if (dbFieldObj.isUnknown() || dbFieldObj.isNull()) {
            dbFieldObj = null;
        }
        return dbFieldObj;
    }
    
    public static Double recGetDouble(final byte[] array, final int n) throws DSMException {
        Double n2 = null;
        final dbFieldObj recGetDoubleValue = recGetDoubleValue(array, n);
        if (recGetDoubleValue != null && !recGetDoubleValue.isUnknown() && !recGetDoubleValue.isNull()) {
            n2 = new Double(recGetDoubleValue.getDouble());
        }
        return n2;
    }
    
    public static int recGetDSMLong(final byte[] array, final int n) throws DSMException {
        int int1 = 0;
        final dbFieldObj recGetIntValue = recGetIntValue(array, n);
        if (recGetIntValue != null && !recGetIntValue.isUnknown() && !recGetIntValue.isNull()) {
            int1 = recGetIntValue.getInt();
        }
        return int1;
    }
    
    public static Integer recGetInteger(final byte[] array, final int n) throws DSMException {
        Integer n2 = null;
        final dbFieldObj recGetIntValue = recGetIntValue(array, n);
        if (recGetIntValue != null && !recGetIntValue.isUnknown() && !recGetIntValue.isNull()) {
            n2 = new Integer(recGetIntValue.getInt());
        }
        return n2;
    }
    
    public static Long recGetUnsignedInteger(final byte[] array, final int n) throws DSMException {
        Long n2 = null;
        final Integer recGetInteger = recGetInteger(array, n);
        if (recGetInteger != null) {
            if (recGetInteger < 0) {
                n2 = new Long(2147483647L + (recGetInteger + 2147483648L) + 1L);
            }
            else {
                n2 = new Long(recGetInteger);
            }
        }
        return n2;
    }
    
    public static Long recGetBig(final byte[] array, final int n) throws DSMException {
        Long n2 = null;
        final dbFieldObj recGetBigValue = recGetBigValue(array, n);
        if (recGetBigValue != null && !recGetBigValue.isUnknown() && !recGetBigValue.isNull()) {
            n2 = new Long(recGetBigValue.getLong());
        }
        return n2;
    }
    
    public static Long recGetLong64(final byte[] array, final int n) throws DSMException {
        Long n2 = null;
        final dbFieldObj recGetLong64Value = recGetLong64Value(array, n);
        if (recGetLong64Value != null && !recGetLong64Value.isUnknown() && !recGetLong64Value.isNull()) {
            n2 = new Long(recGetLong64Value.getLong());
        }
        return n2;
    }
    
    public static Integer recGetUnsignedSmall(final byte[] array, final int n) throws DSMException {
        Integer n2 = null;
        final Integer recGetInteger = recGetInteger(array, n);
        if (recGetInteger != null) {
            if (recGetInteger < 0) {
                n2 = new Integer(32767 + (recGetInteger + 32768) + 1);
            }
            else {
                n2 = new Integer(recGetInteger);
            }
        }
        return n2;
    }
    
    public static Integer recGetSmall(final byte[] array, final int n) throws DSMException {
        return recGetInteger(array, n);
    }
    
    public static Byte recGetTiny(final byte[] array, final int n) throws DSMException {
        Byte b = null;
        final dbFieldObj recGetTinyValue = recGetTinyValue(array, n);
        if (recGetTinyValue != null && !recGetTinyValue.isUnknown() && !recGetTinyValue.isNull()) {
            b = new Byte(recGetTinyValue.getByte());
        }
        return b;
    }
    
    public static Byte[] recGetBytes(final byte[] array, final int n) throws DSMException {
        Byte[] array2 = null;
        final dbFieldObj recGetByteValues = recGetByteValues(array, n);
        if (recGetByteValues != null && !recGetByteValues.isUnknown() && !recGetByteValues.isNull()) {
            final byte[] byteArray = recGetByteValues.getByteArray();
            array2 = new Byte[byteArray.length];
            for (int i = 0; i < byteArray.length; ++i) {
                array2[i] = new Byte(byteArray[i]);
            }
        }
        return array2;
    }
    
    public static String recGetIntegerArray(final byte[] array, final int i, final int n) throws DSMException {
        String str = "";
        final dbFieldObj dbFieldObj = new dbFieldObj();
        for (int j = 1; j <= n; ++j) {
            dbFieldObj.setArrayIndex(j);
            dbFieldObj.setFieldType(5);
            final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
            if (recGetFieldJNI != 0) {
                throw new DSMException(recGetFieldJNI, "recGetIntValue(fieldID=" + i + ")");
            }
            if (j > 1) {
                str += ";";
            }
            if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
                str += dbFieldObj.getInt();
            }
            else {
                str += "?";
            }
        }
        return str;
    }
    
    public static String recGetLongArray(final byte[] array, final int i, final int n) throws DSMException {
        String str = "";
        final dbFieldObj dbFieldObj = new dbFieldObj();
        for (int j = 1; j <= n; ++j) {
            dbFieldObj.setArrayIndex(j);
            dbFieldObj.setFieldType(18);
            final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
            if (recGetFieldJNI != 0) {
                throw new DSMException(recGetFieldJNI, "recGetIntValue(fieldID=" + i + ")");
            }
            if (j > 1) {
                str += ";";
            }
            if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
                str += dbFieldObj.getLong();
            }
            else {
                str += "?";
            }
        }
        return str;
    }
    
    public static String recGetStringArray(final byte[] array, final int i, final int n) throws DSMException {
        String str = "";
        final dbFieldObj dbFieldObj = new dbFieldObj();
        for (int j = 1; j <= n; ++j) {
            dbFieldObj.setArrayIndex(j);
            dbFieldObj.setFieldType(13);
            final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
            if (recGetFieldJNI != 0) {
                throw new DSMException(recGetFieldJNI, "recGetString(fieldID=" + i + ")");
            }
            if (j > 1) {
                str += ";";
            }
            if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
                str += dbFieldObj.getString();
            }
            else {
                str += "?";
            }
        }
        return str;
    }
    
    public static String recGetString(final byte[] array, final int i) throws DSMException {
        String string = null;
        final dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(13);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetString(fieldID=" + i + ")");
        }
        if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
            string = dbFieldObj.getString();
        }
        return string;
    }
    
    public static BigDecimal recGetDecimal(final byte[] array, final int n) throws DSMException {
        BigDecimal bigDecimal = null;
        final dbFieldObj recGetDecimalValue = recGetDecimalValue(array, n);
        if (recGetDecimalValue != null && !recGetDecimalValue.isUnknown() && !recGetDecimalValue.isNull()) {
            bigDecimal = new BigDecimal(recGetDecimalValue.getDouble(), new MathContext(7, RoundingMode.HALF_UP));
        }
        return bigDecimal;
    }
    
    public static Double recGetFloat(final byte[] array, final int n) throws DSMException {
        return recGetDouble(array, n);
    }
    
    public static Date recGetDate(final byte[] array, final int n) throws DSMException {
        Date date = null;
        final dbFieldObj recGetIntValue = recGetIntValue(array, n);
        if (recGetIntValue != null && !recGetIntValue.isUnknown() && !recGetIntValue.isNull()) {
            date = new Date(recGetIntValue.getInt());
        }
        return date;
    }
    
    public static Time recGetTime(final byte[] array, final int n) throws DSMException {
        Time time = null;
        final dbFieldObj recGetIntValue = recGetIntValue(array, n);
        if (recGetIntValue != null && !recGetIntValue.isUnknown() && !recGetIntValue.isNull()) {
            time = new Time(recGetIntValue.getInt());
        }
        return time;
    }
    
    public static Timestamp recGetTimestamp(final byte[] array, final int i) throws DSMException {
        Timestamp timestamp = null;
        final dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(11);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetTimestamp(fieldID=" + i + ")");
        }
        if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
            timestamp = dbFieldObj.getTimestamp();
        }
        return timestamp;
    }
    
    public static Boolean recGetBool(final byte[] array, final int i) throws DSMException {
        Boolean b = null;
        final dbFieldObj dbFieldObj = new dbFieldObj();
        dbFieldObj.setFieldType(4);
        final int recGetFieldJNI = recGetFieldJNI(array, i, dbFieldObj);
        if (recGetFieldJNI != 0) {
            throw new DSMException(recGetFieldJNI, "recGetBool(fieldID=" + i + ")");
        }
        if (!dbFieldObj.isUnknown() && !dbFieldObj.isNull()) {
            b = new Boolean(dbFieldObj.getBoolean());
        }
        return b;
    }
    
    static {
        try {
            System.load(new InstallPath().fullyQualifyFile("rocket.dll"));
            System.load(new InstallPath().fullyQualifyFile("jni_dsm.dll"));
        }
        catch (SecurityException ex) {
            ProLog.log("DSMAPI", 3L, "Warning SecurityException:" + ex.getMessage());
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            ProLog.log("DSMAPI", 3L, "Warning UnsatisfiedLinkError:" + unsatisfiedLinkError.getMessage());
        }
    }
    
    public static class DSMException extends ProException
    {
        public DSMException(final int value, final String s) {
            super("Data storage error %i<status value> calling %s<function name>.", new Object[] { new Integer(value), s });
        }
        
        public int getDSMStatus() {
            return (int)this.getArgument(0);
        }
        
        public String getDSMFunctionName() {
            return (String)this.getArgument(1);
        }
    }
    
    public static class DSMContext
    {
        DSMContextInfo dsmInfo;
        
        public DSMContext() {
        }
        
        public DSMContext(final DSMContextInfo dsmInfo) {
            this.dsmInfo = dsmInfo;
        }
        
        public DSMContextInfo getContextInfo() {
            return this.dsmInfo;
        }
        
        public int getLong(final int n) throws DSMException {
            return DSMAPI.getLong(this.dsmInfo, n);
        }
        
        public void setString(final int n, final String s) throws DSMException {
            DSMAPI.setString(this.dsmInfo, n, s);
        }
        
        public void setLong(final int n, final long n2) throws DSMException {
            DSMAPI.setLong(this.dsmInfo, n, n2);
        }
    }
    
    public static class ServerData
    {
        int m_servid;
        int m_pid;
        int m_stport;
        int m_usrcnt;
        int m_terminate;
        int m_servtype;
        int m_maxsvprot;
        int m_maxsvbrok;
        int m_minport;
        int m_maxport;
        int m_lastport;
        int m_maxclts;
        int m_servergroupIdPort;
        int m_servergroupIdType;
        String m_service;
        
        public ServerData() {
        }
        
        public ServerData(final int servid, final int pid, final int stport, final int usrcnt, final int terminate, final int servtype, final int maxsvprot, final int n, final int minport, final int maxport, final int maxclts, final String service, final int servergroupIdPort, final int servergroupIdType) {
            this.m_servid = servid;
            this.m_pid = pid;
            this.m_stport = stport;
            this.m_usrcnt = usrcnt;
            this.m_terminate = terminate;
            this.m_servtype = servtype;
            this.m_maxsvprot = maxsvprot;
            this.m_minport = minport;
            this.m_maxport = maxport;
            this.m_maxclts = maxclts;
            this.m_service = service;
            this.m_servergroupIdPort = servergroupIdPort;
            this.m_servergroupIdType = servergroupIdType;
        }
        
        public void copyServerData(final ServerData serverData) {
            this.m_servid = serverData.m_servid;
            this.m_pid = serverData.m_pid;
            this.m_stport = serverData.m_stport;
            this.m_usrcnt = serverData.m_usrcnt;
            this.m_terminate = serverData.m_terminate;
            this.m_servtype = serverData.m_servtype;
            this.m_maxsvprot = serverData.m_maxsvprot;
            this.m_minport = serverData.m_minport;
            this.m_maxport = serverData.m_maxport;
            this.m_maxclts = serverData.m_maxclts;
            this.m_service = serverData.m_service;
        }
        
        public int getId() {
            return this.m_servid;
        }
        
        public void setId(final int servid) {
            this.m_servid = servid;
        }
        
        public int getPort() {
            return this.m_stport;
        }
        
        public void setPort(final int stport) {
            this.m_stport = stport;
        }
        
        public void setServType(final int servtype) {
            this.m_servtype = servtype;
        }
        
        public int getPid() {
            return this.m_pid;
        }
        
        public void setPid(final int n) {
        }
        
        public void setMaxport(final int maxport) {
            this.m_maxport = maxport;
        }
        
        public void setMinport(final int minport) {
            this.m_minport = minport;
        }
        
        public void setLastport(final int lastport) {
            this.m_lastport = lastport;
        }
        
        public void setMaxsvbrok(final int maxsvbrok) {
            this.m_maxsvbrok = maxsvbrok;
        }
        
        public void setMaxClients(final int maxclts) {
            this.m_maxclts = maxclts;
        }
        
        public void initServerData(final ServerData serverData) {
            this.m_maxsvprot = serverData.m_maxsvprot;
        }
        
        public void setServerGroupId(final int servergroupIdPort, final int servergroupIdType) {
            this.m_servergroupIdPort = servergroupIdPort;
            this.m_servergroupIdType = servergroupIdType;
        }
        
        public int getServergroupIdPort() {
            return this.m_servergroupIdPort;
        }
        
        public int getServergroupIdType() {
            return this.m_servergroupIdType;
        }
        
        public int getType() {
            return this.m_servergroupIdType;
        }
        
        public boolean isDead() {
            return this.m_pid == 512;
        }
        
        public boolean isErrServ() {
            return this.m_pid == 256;
        }
        
        public int resetUsrcnt() {
            final int n = 32;
            this.m_usrcnt = 0;
            return n;
        }
        
        public boolean serverGroupEquals(final ServerData serverData) {
            return this.m_servergroupIdPort == serverData.getServergroupIdPort() && this.m_servergroupIdType == serverData.getServergroupIdType();
        }
    }
    
    public static class DSMContextInfo implements IDSMConstants, Cloneable
    {
        JNIHandle handle;
        
        public JNIHandle getHandle() {
            return this.handle;
        }
        
        public int getLong(final int n) throws DSMException {
            return DSMAPI.getLong(this, n);
        }
        
        public void setString(final int n, final String s) throws DSMException {
            DSMAPI.setString(this, n, s);
        }
        
        public void setLong(final int n, final long n2) throws DSMException {
            DSMAPI.setLong(this, n, n2);
        }
    }
    
    public static class DSMBrokerContextInfo extends DSMContextInfo implements IDSMConstants
    {
        DSMBrokerContextInfo(final JNIHandle handle) {
            super.handle = handle;
        }
    }
    
    public static class DSMBrokerContext extends DSMContext
    {
        DSMBrokerContextInfo bdsmInfo;
        
        DSMBrokerContext(final DSMContextInfo dsmContextInfo) {
            this.bdsmInfo = (DSMBrokerContextInfo)dsmContextInfo;
        }
        
        public ServerData getBestServer(final int n) throws DSMException {
            return DSMAPI.getBestServerData(this.bdsmInfo, n);
        }
        
        public ServerData getBestServerX(final int n, final ServerData serverData) throws DSMException {
            return DSMAPI.getBestServerDataX(this.bdsmInfo, n, serverData);
        }
        
        public DSMContextInfo getContextInfo() {
            return this.bdsmInfo;
        }
        
        public ServerData getServerData(final int n) throws DSMException {
            return DSMAPI.getServerData(this.bdsmInfo, n);
        }
        
        public void setServerData(final ServerData serverData, final int n) throws DSMException {
            DSMAPI.setServerData(this.bdsmInfo, serverData, n);
        }
        
        public void watchdog() throws DSMException {
            DSMAPI.watchdog(this.bdsmInfo);
        }
        
        public void writeContextOptions() throws DSMException {
            DSMAPI.writeContextOptions(this.bdsmInfo);
        }
        
        public void setShutdown(final boolean b) throws DSMException {
            DSMAPI.setShutdown(this.bdsmInfo, b);
        }
        
        public void databaseProcessEvents() throws DSMException {
            DSMAPI.databaseProcessEvents(this.bdsmInfo);
        }
        
        public void disconnectUsers(final int n) throws DSMException {
            DSMAPI.disconnectUsers(this.bdsmInfo, n);
        }
        
        public int getLong(final int n) throws DSMException {
            return DSMAPI.getLong(this.bdsmInfo, n);
        }
        
        public void setString(final int n, final String s) throws DSMException {
            DSMAPI.setString(this.bdsmInfo, n, s);
        }
        
        public void setLong(final int n, final long n2) throws DSMException {
            DSMAPI.setLong(this.bdsmInfo, n, n2);
        }
    }
    
    public static class dbFieldObj
    {
        private int fieldType;
        private int arrayIndex;
        private int dataFlag;
        private long longValue;
        private double doubleValue;
        private float floatValue;
        private int intValue;
        private short shortValue;
        private byte byteValue;
        private String stringValue;
        private BigDecimal decimalValue;
        private Timestamp timestampValue;
        private boolean booleanValue;
        private byte[] byteArrayValue;
        
        public dbFieldObj() {
            this.arrayIndex = 1;
            this.dataFlag = 0;
        }
        
        public void setFieldType(final int fieldType) {
            this.fieldType = fieldType;
        }
        
        public void setArrayIndex(final int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }
        
        public boolean isUnknown() {
            return (this.dataFlag & 0x100) != 0x0;
        }
        
        public boolean isNull() {
            return (this.dataFlag & 0x40) != 0x0;
        }
        
        public int getDataFlag() {
            return this.dataFlag;
        }
        
        public long getLong() {
            return this.longValue;
        }
        
        public double getDouble() {
            return this.doubleValue;
        }
        
        public float getFloat() {
            return this.floatValue;
        }
        
        public int getInt() {
            return this.intValue;
        }
        
        public short getShort() {
            return this.shortValue;
        }
        
        public byte getByte() {
            return this.byteValue;
        }
        
        public String getString() {
            return this.stringValue;
        }
        
        public BigDecimal getDecimal() {
            return this.decimalValue;
        }
        
        public Timestamp getTimestamp() {
            return this.timestampValue;
        }
        
        public boolean getBoolean() {
            return this.booleanValue;
        }
        
        public byte[] getByteArray() {
            return this.byteArrayValue;
        }
    }
}
