// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.net.URL;
import java.util.Calendar;
import java.sql.Array;
import java.sql.Ref;
import java.util.Map;
import java.sql.Statement;
import java.io.Reader;
import java.sql.SQLException;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.OutputSetException;
import java.sql.SQLWarning;
import java.io.InputStream;
import java.sql.Time;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ProBlob;
import java.sql.Blob;
import com.progress.open4gl.ProClob;
import java.sql.Clob;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.sql.Timestamp;
import java.sql.Date;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProResultSet;

public class ResultSet implements ProResultSet
{
    private static final int GET_GREGORIAN_DATE = 1;
    private static final int GET_SQL_DATE = 2;
    private boolean allInvalid;
    private ResultSetMetaData schema;
    private StreamReader reader;
    private ProToJava toJava;
    private int currentCol;
    private int paramNum;
    private int currentRow;
    private boolean isNull;
    private RqContext m_rqCtx;
    private int m_rqThreadid;
    private boolean gotSchemaHeader;
    private int m_marshalLevel;
    private int m_numToSkip;
    private boolean noValue;
    private ResultSet prevSet;
    private ResultSet nextSet;
    private Session session;
    
    public ResultSet(final ResultSetMetaData resultSetMetaData, final StreamReader streamReader) {
        this(resultSetMetaData, streamReader, 0);
    }
    
    public ResultSet(final ResultSetMetaData schema, final StreamReader reader, final int paramNum) {
        this.currentCol = 0;
        this.paramNum = 0;
        this.currentRow = 0;
        this.isNull = false;
        this.m_rqCtx = null;
        this.m_rqThreadid = 0;
        this.gotSchemaHeader = false;
        this.m_marshalLevel = 0;
        this.m_numToSkip = 0;
        this.noValue = false;
        this.paramNum = paramNum;
        this.toJava = new ProToJava(reader);
        this.reader = reader;
        this.schema = schema;
        this.prevSet = null;
        this.nextSet = null;
        this.allInvalid = false;
    }
    
    void setAllInvalid() {
        this.allInvalid = true;
    }
    
    public void setRqContext(final RqContext rqCtx) {
        this.m_rqCtx = rqCtx;
        this.m_rqThreadid = Thread.currentThread().hashCode();
    }
    
    void setSession(final Session session) {
        this.session = session;
    }
    
    boolean isClosed() {
        return this.reader == null;
    }
    
    public void setPrev(final ResultSet prevSet) {
        this.prevSet = prevSet;
    }
    
    public void setNext(final ResultSet nextSet) {
        this.nextSet = nextSet;
    }
    
    public boolean hasSchemaHeader() {
        return this.gotSchemaHeader;
    }
    
    public void setSkipTblHdr(final int numToSkip) {
        this.m_numToSkip = numToSkip;
    }
    
    public int getMarshalLevel() {
        return this.m_marshalLevel;
    }
    
    public boolean next() throws ProSQLException {
        if (this.allInvalid) {
            this.closeAll();
        }
        if (this.reader == null) {
            throw getProSQLException(5L, "S1010");
        }
        if (this.prevSet != null && !this.prevSet.isClosed()) {
            throw getProSQLException(6L, "S1010");
        }
        try {
            final boolean nextRow = this.reader.getNextRow();
            if (nextRow) {
                this.currentCol = 1;
                ++this.currentRow;
                if (RunTimeProperties.isTracing() && this.paramNum > 0) {
                    RunTimeProperties.tracer.print("\tOUTPUT table, parameter: " + this.paramNum + ", " + "row " + this.currentRow, 3);
                }
            }
            else {
                if (this.m_numToSkip > 0) {
                    final Tracer tracer = RunTimeProperties.tracer;
                    for (int i = 1; i <= this.m_numToSkip; ++i) {
                        tracer.print("\tOUTPUT table, parameter " + (this.paramNum + i) + " is null", 3);
                        if (this.nextSet != null || i > 1) {
                            this.reader.readNextTableHdr();
                        }
                    }
                    this.m_numToSkip = 0;
                }
                this.gotSchemaHeader = this.reader.hasSchemaHeader();
                this.m_marshalLevel = this.reader.getMarshalLevel();
                this.close(false, false);
            }
            return nextRow;
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return false;
        }
    }
    
    private void validatePos(final int value) throws ProSQLException {
        if (this.allInvalid) {
            this.closeAll();
        }
        if (this.reader == null) {
            throw getProSQLException(7L, "S1010");
        }
        if (value < 1 || value > this.schema.getColumnCount() || this.currentCol == 0 || value < this.currentCol) {
            throw getProSQLException(8L, "S1002", new Integer(value).toString());
        }
    }
    
    private void positionCol(final int n) throws ProSQLException {
        this.noValue = false;
        try {
            for (int i = n - this.currentCol; i >= 0; --i) {
                this.reader.getNextColumn();
                if (i > 0) {
                    this.reader.skipColumn();
                }
            }
            if (this.toJava.isNull()) {
                this.isNull = true;
                if (this.toJava.noReturnValue()) {
                    this.noValue = true;
                }
            }
            else {
                this.isNull = false;
            }
            this.currentCol = n + 1;
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
        }
    }
    
    boolean noReturnValue() {
        return this.noValue;
    }
    
    public boolean wasNull() throws ProSQLException {
        return this.isNull;
    }
    
    public java.sql.ResultSetMetaData getMetaData() throws ProSQLException {
        return this.schema;
    }
    
    public byte[] getBytes(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 8:
            case 13:
            case 18: {
                return this.getBytes0(n);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "byte array");
            }
        }
    }
    
    public String getString(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 1:
            case 19: {
                return this.getString0(n);
            }
            case 2: {
                final Date date = (Date)this.getDate0(n, 2);
                if (date != null) {
                    return date.toString();
                }
                return null;
            }
            case 34: {
                final Timestamp timestamp = (Timestamp)this.getDateTime(n, 2);
                if (timestamp != null) {
                    return timestamp.toString();
                }
                return null;
            }
            case 40: {
                final GregorianCalendar gregorianCalendar = (GregorianCalendar)this.getDateTimeTZ(n, 1);
                if (gregorianCalendar != null) {
                    final DecimalFormat decimalFormat = new DecimalFormat("#0000;-#0000");
                    final DecimalFormat decimalFormat2 = new DecimalFormat("00");
                    final DecimalFormat decimalFormat3 = new DecimalFormat("000");
                    final StringBuffer sb = new StringBuffer();
                    sb.append(decimalFormat.format(gregorianCalendar.get(1)) + "-");
                    sb.append(decimalFormat2.format(gregorianCalendar.get(2) + 1) + "-");
                    sb.append(decimalFormat2.format(gregorianCalendar.get(5)) + " ");
                    sb.append(decimalFormat2.format(gregorianCalendar.get(11)) + ":");
                    sb.append(decimalFormat2.format(gregorianCalendar.get(12)) + ":");
                    sb.append(decimalFormat2.format(gregorianCalendar.get(13)) + ".");
                    sb.append(decimalFormat3.format(gregorianCalendar.get(14)));
                    int n2 = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000;
                    if (n2 < 0) {
                        sb.append("-");
                        n2 *= -1;
                    }
                    else {
                        sb.append("+");
                    }
                    sb.append(decimalFormat2.format(n2 / 60) + ":");
                    sb.append(decimalFormat2.format(n2 % 60));
                    return sb.toString();
                }
                return null;
            }
            case 3: {
                final boolean boolean0 = this.getBoolean0(n);
                if (this.isNull) {
                    return null;
                }
                return new Boolean(boolean0).toString();
            }
            case 4: {
                final int int0 = this.getInt0(n);
                if (this.isNull) {
                    return null;
                }
                return new Integer(int0).toString();
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                final long int2 = this.getInt640(n);
                if (this.isNull) {
                    return null;
                }
                return new Long(int2).toString();
            }
            case 5: {
                final BigDecimal bigDecimal0 = this.getBigDecimal0(n, 0, true);
                if (bigDecimal0 != null) {
                    return bigDecimal0.toString();
                }
                return null;
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "String");
            }
        }
    }
    
    public Object getParamObject(final int n, final int n2) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            switch (n2) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 10:
                case 11:
                case 13:
                case 14:
                case 34:
                case 36:
                case 37:
                case 39:
                case 40:
                case 41: {
                    switch (n2) {
                        case 5: {
                            return this.toJava.getProDecimal(null);
                        }
                        case 4: {
                            return new Integer(this.toJava.getProInt());
                        }
                        case 41: {
                            return new Long(this.toJava.getProInt64());
                        }
                        case 7: {
                            return new Long(this.toJava.getProInt64());
                        }
                        case 3: {
                            return new Boolean(this.toJava.getProBoolean());
                        }
                        case 1: {
                            return this.toJava.getProString();
                        }
                        case 2: {
                            return this.toJava.getProDate();
                        }
                        case 34: {
                            return this.toJava.getProDateTime();
                        }
                        case 40: {
                            return this.toJava.getProDateTimeTz();
                        }
                        case 8: {
                            return this.toJava.getProRaw();
                        }
                        case 13: {
                            return this.toJava.getProRowid();
                        }
                        case 10: {
                            return new Handle(this.toJava.getProInt64());
                        }
                        case 11: {
                            return this.toJava.getProMemptr();
                        }
                        case 36:
                        case 37:
                        case 39: {
                            return this.toJava.getProLongchar();
                        }
                        case 14: {
                            return new COMHandle(this.toJava.getProInt64());
                        }
                        default: {
                            return null;
                        }
                    }
                    break;
                }
                default: {
                    throw getProSQLException();
                }
            }
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    public long getLong(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 4: {
                return this.getInt0(n);
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                return this.getInt640(n);
            }
            case 5: {
                final BigDecimal bigDecimal0 = this.getBigDecimal0(n, 0, true);
                if (bigDecimal0 != null) {
                    return bigDecimal0.longValue();
                }
                return 0L;
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Long");
            }
        }
    }
    
    public int getInt(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 4: {
                return this.getInt0(n);
            }
            case 3: {
                return this.getBoolean0(n) ? 1 : 0;
            }
            case 5: {
                final BigDecimal bigDecimal0 = this.getBigDecimal0(n, 0, true);
                if (bigDecimal0 != null) {
                    return bigDecimal0.intValue();
                }
                return 0;
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "int");
            }
        }
    }
    
    public double getDouble(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 5: {
                return this.getDouble0(n);
            }
            case 4: {
                return this.getInt0(n);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Double");
            }
        }
    }
    
    public BigDecimal getBigDecimal(final int n, final int n2) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 5: {
                return this.getBigDecimal0(n, n2, true);
            }
            case 4: {
                final int int0 = this.getInt0(n);
                if (this.isNull) {
                    return null;
                }
                return new BigDecimal(new Integer(int0).toString());
            }
            case 41: {
                final long int2 = this.getInt640(n);
                if (this.isNull) {
                    return null;
                }
                return new BigDecimal(new Long(int2).toString());
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "BigDecimal");
            }
        }
    }
    
    public boolean getBoolean(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 3: {
                return this.getBoolean0(n);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Boolean");
            }
        }
    }
    
    public Date getDate(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 2: {
                return (Date)this.getDate0(n, 2);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Date");
            }
        }
    }
    
    public Timestamp getTimestamp(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 34: {
                return (Timestamp)this.getDateTime(n, 2);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Timestamp");
            }
        }
    }
    
    public Clob getClob(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 19: {
                final String string0 = this.getString0(n);
                if (string0 == null) {
                    return null;
                }
                return new ProClob(string0);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Clob");
            }
        }
    }
    
    public Blob getBlob(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 18: {
                final byte[] bytes0 = this.getBytes0(n);
                if (bytes0 == null) {
                    return null;
                }
                return new ProBlob(bytes0);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "Blob");
            }
        }
    }
    
    public GregorianCalendar getGregorianCalendar(final int n) throws ProSQLException {
        this.validatePos(n);
        switch (this.schema.getProColumnType(n)) {
            case 2: {
                return (GregorianCalendar)this.getDate0(n, 1);
            }
            case 34: {
                return (GregorianCalendar)this.getDateTime(n, 1);
            }
            case 40: {
                return (GregorianCalendar)this.getDateTimeTZ(n, 1);
            }
            default: {
                throw getOutConvertException(this.schema.getProColumnType(n), "GregorianCalendar");
            }
        }
    }
    
    static void closeAll(final Session session, ResultSet prevSet) {
        while (prevSet != null) {
            try {
                prevSet.close(false, true);
                prevSet = prevSet.prevSet;
            }
            catch (ProSQLException ex) {}
        }
        if (session != null) {
            session.throwOutput();
        }
    }
    
    void closeAll() {
        ResultSet set = null;
        if (this.session != null) {
            set = this.session.getAnchor();
        }
        while (set != null) {
            try {
                set.close(false, true);
                set = set.prevSet;
            }
            catch (ProSQLException ex) {}
        }
        if (this.session != null) {
            this.session.throwOutput();
        }
    }
    
    static void markAllInvalid(ResultSet prevSet) {
        while (prevSet != null) {
            prevSet.setAllInvalid();
            prevSet = prevSet.prevSet;
        }
    }
    
    public void close() throws ProSQLException {
        if (this.allInvalid) {
            this.closeAll();
        }
        this.close(true, true);
    }
    
    private synchronized void close(final boolean b, final boolean b2) throws ProSQLException {
        if (this.reader == null) {
            return;
        }
        if (b) {
            while (this.next()) {}
        }
        if (this.nextSet == null && this.session != null && !b2) {
            this.session.setNotStreaming();
        }
        this.reader = null;
        this.toJava = null;
        if (this.m_rqCtx != null) {
            try {
                this.m_rqCtx._release(this.m_rqThreadid);
            }
            catch (Open4GLException ex) {}
            finally {
                this.m_rqCtx = null;
                this.m_rqThreadid = 0;
            }
        }
    }
    
    public byte getByte(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public short getShort(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public float getFloat(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public Time getTime(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getAsciiStream(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getUnicodeStream(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getBinaryStream(final int n) throws ProSQLException {
        throw getProSQLException();
    }
    
    public byte getByte(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public short getShort(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public float getFloat(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public Time getTime(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getAsciiStream(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getUnicodeStream(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public InputStream getBinaryStream(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    public SQLWarning getWarnings() throws ProSQLException {
        return null;
    }
    
    public void clearWarnings() throws ProSQLException {
    }
    
    public String getCursorName() throws ProSQLException {
        return new String();
    }
    
    public Object getObject(final int n) throws ProSQLException {
        this.validatePos(n);
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            switch (this.schema.getProColumnType(n)) {
                case 1: {
                    return this.toJava.getProString();
                }
                case 4: {
                    return new Integer(this.toJava.getProInt());
                }
                case 41: {
                    return new Long(this.toJava.getProInt64());
                }
                case 7:
                case 10:
                case 14: {
                    return new Long(this.toJava.getProInt64());
                }
                case 11: {
                    return this.toJava.getProMemptr();
                }
                case 3: {
                    return new Boolean(this.toJava.getProBoolean());
                }
                case 2: {
                    return new Date(this.toJava.getProDate().getTime().getTime());
                }
                case 34: {
                    return new Timestamp(this.toJava.getProDateTime().getTime().getTime());
                }
                case 40: {
                    return this.toJava.getProDateTimeTz();
                }
                case 5: {
                    return this.toJava.getProDecimal(null);
                }
                case 8:
                case 13: {
                    return this.toJava.getProRaw();
                }
                case 19: {
                    return new ProClob(this.toJava.getProString());
                }
                case 18: {
                    return new ProBlob(this.toJava.getProRaw());
                }
                default: {
                    throw new ProSQLException(new OutputSetException(), "S1000");
                }
            }
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    public int findColumn(final String s) throws ProSQLException {
        throw getProSQLException();
    }
    
    static ProSQLException getOutConvertException(final int n, final String s) {
        return new ProSQLException(new OutputSetException(4L, new Object[] { Parameter.proToName(n), s }), "S1C00");
    }
    
    public static ProSQLException getProSQLException(final long n) {
        return new ProSQLException(new OutputSetException(n, null), "S1000");
    }
    
    static ProSQLException getProSQLException(final long n, final String s) {
        return new ProSQLException(new OutputSetException(n, null), s);
    }
    
    static ProSQLException getProSQLException(final long n, final String s, final String s2) {
        return new ProSQLException(new OutputSetException(n, new Object[] { s2 }), s);
    }
    
    static ProSQLException getProSQLException(final long n, final String s, final String s2, final String s3, final String s4) {
        return new ProSQLException(new OutputSetException(n, new Object[] { s2, s3, s4 }), s);
    }
    
    static ProSQLException getProSQLException(final long n, final String s, final String s2, final String s3) {
        return new ProSQLException(new OutputSetException(n, new Object[] { s2, s3 }), s);
    }
    
    static ProSQLException getProSQLException() {
        return getProSQLException(9L, "S1000");
    }
    
    public byte[] getBytes(final int n, final int n2) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(n, n2));
    }
    
    public String getString(final int n, final int n2) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(n, n2));
    }
    
    public long getLong(final int n, final int n2) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(n, n2));
    }
    
    public int getInt(final int n, final int n2) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(n, n2));
    }
    
    public double getDouble(final int n, final int n2) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(n, n2));
    }
    
    public BigDecimal getBigDecimal(final int n, final int n2, final int n3) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(n, n2), n3);
    }
    
    public boolean getBoolean(final int n, final int n2) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(n, n2));
    }
    
    public Date getDate(final int n, final int n2) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(n, n2));
    }
    
    public Timestamp getTimestamp(final int n, final int n2) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(n, n2));
    }
    
    public GregorianCalendar getGregorianCalendar(final int n, final int n2) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(n, n2));
    }
    
    public Blob getBlob(final int n, final int n2) throws ProSQLException {
        return this.getBlob(this.schema.fieldToColumn(n, n2));
    }
    
    public Clob getClob(final int n, final int n2) throws ProSQLException {
        return this.getClob(this.schema.fieldToColumn(n, n2));
    }
    
    public Object getObject(final int n, final int n2) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(n, n2));
    }
    
    public byte[] getBytes(final String s, final int n) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(s, n));
    }
    
    public String getString(final String s, final int n) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(s, n));
    }
    
    public long getLong(final String s, final int n) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(s, n));
    }
    
    public int getInt(final String s, final int n) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(s, n));
    }
    
    public double getDouble(final String s, final int n) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(s, n));
    }
    
    public BigDecimal getBigDecimal(final String s, final int n, final int n2) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s, n), n2);
    }
    
    public boolean getBoolean(final String s, final int n) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(s, n));
    }
    
    public Date getDate(final String s, final int n) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(s, n));
    }
    
    public Timestamp getTimestamp(final String s, final int n) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(s, n));
    }
    
    public GregorianCalendar getGregorianCalendar(final String s, final int n) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(s, n));
    }
    
    public Object getObject(final String s, final int n) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(s, n));
    }
    
    public Blob getBlob(final String s, final int n) throws ProSQLException {
        return this.getBlob(this.schema.fieldToColumn(s, n));
    }
    
    public Clob getClob(final String s, final int n) throws ProSQLException {
        return this.getClob(this.schema.fieldToColumn(s, n));
    }
    
    public byte[] getBytes(final String s) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(s));
    }
    
    public String getString(final String s) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(s));
    }
    
    public long getLong(final String s) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(s));
    }
    
    public int getInt(final String s) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(s));
    }
    
    public double getDouble(final String s) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(s));
    }
    
    public BigDecimal getBigDecimal(final String s, final int n) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s), n);
    }
    
    public boolean getBoolean(final String s) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(s));
    }
    
    public Date getDate(final String s) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(s));
    }
    
    public Timestamp getTimestamp(final String s) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(s));
    }
    
    public GregorianCalendar getGregorianCalendar(final String s) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(s));
    }
    
    public Object getObject(final String s) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(s));
    }
    
    public Blob getBlob(final String s) throws SQLException {
        return this.getBlob(this.schema.fieldToColumn(s));
    }
    
    public Clob getClob(final String s) throws SQLException {
        return this.getClob(this.schema.fieldToColumn(s));
    }
    
    public int getFetchDirection() throws SQLException {
        return 1000;
    }
    
    public int getType() throws SQLException {
        return 1003;
    }
    
    public int getConcurrency() throws SQLException {
        return 1007;
    }
    
    public int getRow() throws SQLException {
        return this.currentRow;
    }
    
    public BigDecimal getBigDecimal(final int n) throws ProSQLException {
        return this.getBigDecimal(n, 0);
    }
    
    public BigDecimal getBigDecimal(final String s) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s), 0);
    }
    
    private byte[] getBytes0(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            return this.toJava.getProRaw();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private String getString0(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            return this.toJava.getProString();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private double getDouble0(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return 0.0;
        }
        try {
            return this.toJava.getProDouble();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return 0.0;
        }
    }
    
    private BigDecimal getBigDecimal0(final int n, final int value, final boolean b) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            return this.toJava.getProDecimal(b ? null : new Integer(value));
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private boolean getBoolean0(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return false;
        }
        try {
            return this.toJava.getProBoolean();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return false;
        }
    }
    
    private Object getDate0(final int n, final int n2) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            final GregorianCalendar proDate = this.toJava.getProDate();
            if (n2 == 1) {
                return proDate;
            }
            return new Date(proDate.getTime().getTime());
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private Object getDateTime(final int n, final int n2) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            final GregorianCalendar proDateTime = this.toJava.getProDateTime();
            if (n2 == 1) {
                return proDateTime;
            }
            return new Timestamp(proDateTime.getTime().getTime());
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private Object getDateTimeTZ(final int n, final int n2) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return null;
        }
        try {
            return this.toJava.getProDateTimeTz();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return null;
        }
    }
    
    private int getInt0(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return 0;
        }
        try {
            return this.toJava.getProInt();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return 0;
        }
    }
    
    private long getInt640(final int n) throws ProSQLException {
        this.positionCol(n);
        if (this.isNull) {
            return 0L;
        }
        try {
            return this.toJava.getProInt64();
        }
        catch (ClientException ex) {
            if (this.session != null) {
                this.session.handleOutputErrors();
            }
            ExceptionConverter.convertToProSQLException(ex);
            return 0L;
        }
    }
    
    public Reader getCharacterStream(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Reader getCharacterStream(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public boolean isBeforeFirst() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isAfterLast() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isFirst() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isLast() throws SQLException {
        throw new SQLException();
    }
    
    public void beforeFirst() throws SQLException {
        throw new SQLException();
    }
    
    public void afterLast() throws SQLException {
        throw new SQLException();
    }
    
    public boolean first() throws SQLException {
        throw new SQLException();
    }
    
    public boolean last() throws SQLException {
        throw new SQLException();
    }
    
    public boolean absolute(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public boolean relative(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public boolean previous() throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchDirection(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchSize(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public int getFetchSize() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowUpdated() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowInserted() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowDeleted() throws SQLException {
        throw new SQLException();
    }
    
    public void updateNull(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBoolean(final int n, final boolean b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final int n, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final int n, final short n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateInt(final int n, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateLong(final int n, final long n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final int n, final float n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDouble(final int n, final double n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBigDecimal(final int n, final BigDecimal bigDecimal) throws SQLException {
        throw new SQLException();
    }
    
    public void updateString(final int n, final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBytes(final int n, final byte[] array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDate(final int n, final Date date) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final int n, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTimestamp(final int n, final Timestamp timestamp) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final int n, final Reader reader, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final int n, final Object o, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final int n, final Object o) throws SQLException {
        throw new SQLException();
    }
    
    public void updateNull(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBoolean(final String s, final boolean b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final String s, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final String s, final short n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateInt(final String s, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateLong(final String s, final long n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final String s, final float n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDouble(final String s, final double n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBigDecimal(final String s, final BigDecimal bigDecimal) throws SQLException {
        throw new SQLException();
    }
    
    public void updateString(final String s, final String s2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBytes(final String s, final byte[] array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDate(final String s, final Date date) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final String s, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTimestamp(final String s, final Timestamp timestamp) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final String s, final Reader reader, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final String s, final Object o, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final String s, final Object o) throws SQLException {
        throw new SQLException();
    }
    
    public void insertRow() throws SQLException {
        throw new SQLException();
    }
    
    public void updateRow() throws SQLException {
        throw new SQLException();
    }
    
    public void deleteRow() throws SQLException {
        throw new SQLException();
    }
    
    public void refreshRow() throws SQLException {
        throw new SQLException();
    }
    
    public void cancelRowUpdates() throws SQLException {
        throw new SQLException();
    }
    
    public void moveToInsertRow() throws SQLException {
        throw new SQLException();
    }
    
    public void moveToCurrentRow() throws SQLException {
        throw new SQLException();
    }
    
    public Statement getStatement() throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final int n, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final String s, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final int n, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final String s, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBlob(final int n, final Blob blob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBlob(final String s, final Blob blob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateClob(final int n, final Clob clob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateClob(final String s, final Clob clob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final int n, final Ref ref) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final String s, final Ref ref) throws SQLException {
        throw new SQLException();
    }
}
