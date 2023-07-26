// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.BooleanHolder;
import com.progress.open4gl.ProDataGraph;
import java.sql.ResultSet;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.Rowid;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import com.progress.open4gl.Open4GLException;

public class ParameterSet
{
    private Param[] parameters;
    private boolean outputReady;
    private boolean hasResultSet;
    private boolean hasOutputResultSet;
    private boolean hasInputResultSet;
    private int numOutputSets;
    private int numSets;
    private boolean hasInputDataGraphField;
    private int numOutputDataGraphs;
    private int numDataGraphs;
    private boolean function;
    private int funcDatatype;
    protected boolean funcIsExtent;
    private int funcExtentValue;
    protected boolean funcHasHolder;
    private Object functionValue;
    private Object procedureValue;
    private boolean readHdr;
    public static final int INPUT = 1;
    public static final int OUTPUT = 2;
    public static final int INPUT_OUTPUT = 3;
    
    public ParameterSet(final int n) {
        this.funcIsExtent = false;
        this.funcExtentValue = 0;
        this.funcHasHolder = false;
        this.function = false;
        this.outputReady = false;
        this.hasResultSet = false;
        this.hasOutputResultSet = false;
        this.hasInputResultSet = false;
        this.numOutputSets = 0;
        this.numSets = 0;
        this.readHdr = false;
        this.parameters = new Param[n];
        this.hasInputDataGraphField = false;
        this.numOutputDataGraphs = 0;
        this.numDataGraphs = 0;
    }
    
    public ParameterSet(final ParameterSet set) {
        this.funcIsExtent = false;
        this.funcExtentValue = 0;
        this.funcHasHolder = false;
        this.function = set.function;
        this.funcDatatype = set.funcDatatype;
        this.outputReady = set.outputReady;
        this.hasResultSet = set.hasResultSet;
        this.hasOutputResultSet = set.hasOutputResultSet;
        this.hasInputResultSet = set.hasInputResultSet;
        this.numOutputSets = set.numOutputSets;
        this.numSets = set.numSets;
        this.readHdr = set.readHdr;
        this.parameters = new Param[set.getNumParams()];
        this.hasInputDataGraphField = set.hasInputDataGraphField;
        this.numOutputDataGraphs = set.numOutputDataGraphs;
        this.numDataGraphs = set.numDataGraphs;
        this.functionValue = set.functionValue;
        this.procedureValue = set.procedureValue;
        for (int i = 0; i < this.parameters.length; ++i) {
            this.parameters[i] = new Param(set.parameters[i]);
        }
    }
    
    boolean isFunction() {
        return this.function;
    }
    
    public static String inOutString(final int n) {
        switch (n) {
            case 1: {
                return "INPUT";
            }
            case 2: {
                return "OUTPUT";
            }
            case 3: {
                return "INPUT_OUTPUT";
            }
            default: {
                return "";
            }
        }
    }
    
    protected static boolean IsValueType(final int n) {
        switch (n) {
            case 3:
            case 4:
            case 7:
            case 41: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void setBooleanParameter(final int n, final boolean value, final int n2) throws Open4GLException {
        this.setParameter(n, new Boolean(value), n2, 3, false, 0);
    }
    
    public void setBooleanArrayParameter(final int n, final boolean[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 3, true, n3);
    }
    
    public void setIntegerParameter(final int n, final int value, final int n2) throws Open4GLException {
        this.setParameter(n, new Integer(value), n2, 4, false, 0);
    }
    
    public void setIntegerArrayParameter(final int n, final int[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 4, true, n3);
    }
    
    public void setLongParameter(final int n, final long value, final int n2) throws Open4GLException {
        this.setParameter(n, new Long(value), n2, 7, false, 0);
    }
    
    public void setLongArrayParameter(final int n, final long[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 7, true, n3);
    }
    
    public void setInt64Parameter(final int n, final long value, final int n2) throws Open4GLException {
        this.setParameter(n, new Long(value), n2, 41, false, 0);
    }
    
    public void setInt64Parameter(final int n, final Long n2, final int n3) throws Open4GLException {
        this.setParameter(n, n2, n3, 41, false, 0);
    }
    
    public void setInt64ArrayParameter(final int n, final long[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 41, true, n3);
    }
    
    public void setInt64ArrayParameter(final int n, final Long[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 41, true, n3);
    }
    
    public void setBooleanParameter(final int n, final Boolean b, final int n2) throws Open4GLException {
        this.setParameter(n, b, n2, 3, false, 0);
    }
    
    public void setBooleanArrayParameter(final int n, final Boolean[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 3, true, n3);
    }
    
    public void setIntegerParameter(final int n, final Integer n2, final int n3) throws Open4GLException {
        this.setParameter(n, n2, n3, 4, false, 0);
    }
    
    public void setIntegerArrayParameter(final int n, final Integer[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 4, true, n3);
    }
    
    public void setLongParameter(final int n, final Long n2, final int n3) throws Open4GLException {
        this.setParameter(n, n2, n3, 7, false, 0);
    }
    
    public void setLongArrayParameter(final int n, final Long[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 7, true, n3);
    }
    
    public void setDecimalParameter(final int n, final BigDecimal bigDecimal, final int n2) throws Open4GLException {
        this.setParameter(n, bigDecimal, n2, 5, false, 0);
    }
    
    public void setDecimalArrayParameter(final int n, final BigDecimal[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 5, true, n3);
    }
    
    public void setStringParameter(final int n, final String s, final int n2) throws Open4GLException {
        this.setParameter(n, s, n2, 1, false, 0);
    }
    
    public void setStringArrayParameter(final int n, final String[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 1, true, n3);
    }
    
    public void setLongcharParameter(final int n, final String s, final int n2) throws Open4GLException {
        this.setParameter(n, s, n2, 39, false, 0);
    }
    
    public void setLongcharArrayParameter(final int n, final String[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 39, true, n3);
    }
    
    public void setDateParameter(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.setParameter(n, gregorianCalendar, n2, 2, false, 0);
    }
    
    public void setDateArrayParameter(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 2, true, n3);
    }
    
    public void setDateTimeParameter(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.setParameter(n, gregorianCalendar, n2, 34, false, 0);
    }
    
    public void setDateTimeArrayParameter(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 34, true, n3);
    }
    
    public void setDateTimeTzParameter(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.setParameter(n, gregorianCalendar, n2, 40, false, 0);
    }
    
    public void setDateTimeTzArrayParameter(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 40, true, n3);
    }
    
    public void setByteParameter(final int n, final byte[] array, final int n2) throws Open4GLException {
        this.setParameter(n, array, n2, 8, false, 0);
    }
    
    public void setByteArrayParameter(final int n, final byte[][] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 8, true, n3);
    }
    
    public void setRowidParameter(final int n, final Rowid rowid, final int n2) throws Open4GLException {
        this.setParameter(n, rowid, n2, 13, false, 0);
    }
    
    public void setRowidArrayParameter(final int n, final Rowid[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 13, true, n3);
    }
    
    public void setMemptrParameter(final int n, final Memptr memptr, final int n2) throws Open4GLException {
        this.setParameter(n, memptr, n2, 11, false, 0);
    }
    
    public void setMemptrArrayParameter(final int n, final Memptr[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 11, true, n3);
    }
    
    public void setHandleParameter(final int n, final Handle handle, final int n2) throws Open4GLException {
        this.setParameter(n, handle, n2, 10, false, 0);
    }
    
    public void setHandleArrayParameter(final int n, final Handle[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 10, true, n3);
    }
    
    public void setCOMHandleParameter(final int n, final COMHandle comHandle, final int n2) throws Open4GLException {
        this.setParameter(n, comHandle, n2, 14, false, 0);
    }
    
    public void setCOMHandleArrayParameter(final int n, final COMHandle[] array, final int n2, final int n3) throws Open4GLException {
        this.setParameter(n, array, n2, 14, true, n3);
    }
    
    public void setResultSetParameter(final int n, final ResultSet set, final int n2) throws Open4GLException {
        this.setParameter(n, set, n2, 15, false, 0);
    }
    
    public void setDynResultSetParameter(final int n, final ResultSet set, final int n2) throws Open4GLException {
        this.setParameter(n, set, n2, 17, false, 0);
    }
    
    public void setDataGraphParameter(final int n, final ProDataGraph proDataGraph, final int n2) throws Open4GLException {
        this.setParameter(n, proDataGraph, n2, 36, false, 0);
    }
    
    public void setDataGraphParameter(final int n, final ProDataGraph proDataGraph, final int n2, final boolean mappedTempTable) throws Open4GLException {
        this.setParameter(n, proDataGraph, n2, 36, false, 0);
        this.parameters[n - 1].mappedTempTable = mappedTempTable;
    }
    
    public void setDynDataGraphParameter(final int n, final ProDataGraph proDataGraph, final int n2) throws Open4GLException {
        this.setParameter(n, proDataGraph, n2, 37, false, 0);
    }
    
    public void setDynDataGraphParameter(final int n, final ProDataGraph proDataGraph, final int n2, final boolean mappedTempTable) throws Open4GLException {
        this.setParameter(n, proDataGraph, n2, 37, false, 0);
        this.parameters[n - 1].mappedTempTable = mappedTempTable;
    }
    
    public Object getOutputParameter(final int value) throws Open4GLException {
        final int value2 = value - 1;
        if (value2 >= this.parameters.length) {
            throw new Open4GLException("Parameter Index %s is out of bounds for parameter set.", new Object[] { new Integer(value2).toString() });
        }
        final boolean dataGraph = this.parameters[value2].isDataGraph();
        if (!this.outputReady) {
            throw new Open4GLException(21L, null);
        }
        if ((this.parameters[value2].mode & 0x2) == 0x0) {
            throw new Open4GLException(22L, new Object[] { new Integer(value).toString() });
        }
        if (dataGraph) {
            final O4GLProDataGraph o4GLProDataGraph = (O4GLProDataGraph)this.parameters[value2].value;
            Object dataGraph2 = null;
            if (o4GLProDataGraph != null) {
                dataGraph2 = o4GLProDataGraph.m_dataGraph;
            }
            return dataGraph2;
        }
        return this.parameters[value2].value;
    }
    
    boolean hasResultSet() {
        return this.hasResultSet;
    }
    
    boolean hasDataGraph() {
        return this.numDataGraphs > 0;
    }
    
    boolean hasInputDataGraph() {
        return this.hasInputDataGraphField;
    }
    
    boolean hasOutputDataGraph() {
        return this.numOutputDataGraphs > 0;
    }
    
    boolean hasInputResultSet() {
        return this.hasInputResultSet;
    }
    
    int getNumOutputSets() {
        return this.numOutputSets;
    }
    
    int getNumSets() {
        return this.numSets;
    }
    
    boolean hasOutputResultSet() {
        return this.hasOutputResultSet;
    }
    
    boolean getReadHdr() {
        return this.readHdr;
    }
    
    void setReadHdr(final boolean readHdr) {
        this.readHdr = readHdr;
    }
    
    void setOutputReady() {
        this.outputReady = true;
    }
    
    void setOutputNotReady() {
        this.outputReady = false;
    }
    
    boolean allSet() {
        for (int i = 0; i < this.parameters.length; ++i) {
            if (this.parameters[i] == null) {
                return false;
            }
        }
        return true;
    }
    
    public int getNumParams() {
        return this.parameters.length;
    }
    
    int getMode(final int n) {
        return this.parameters[n - 1].mode;
    }
    
    int getProType(final int n) {
        return this.parameters[n - 1].proDataType;
    }
    
    boolean getIsExtent(final int n) {
        return this.parameters[n - 1].isExtent;
    }
    
    int getExtentValue(final int n) {
        return this.parameters[n - 1].extentValue;
    }
    
    boolean isInput(final int n) {
        return (this.parameters[n - 1].mode & 0x1) != 0x0;
    }
    
    public boolean isOutput(final int n) {
        return (this.parameters[n - 1].mode & 0x2) != 0x0;
    }
    
    public boolean isResultSet(final int n) {
        final int proDataType = this.parameters[n - 1].proDataType;
        return proDataType == 15 || proDataType == 17;
    }
    
    public boolean isMappedTable(final int n) {
        return this.parameters[n - 1].mappedTempTable;
    }
    
    public void setIsMappedTable(final int n, final boolean mappedTempTable) {
        if (this.parameters[n - 1] != null) {
            this.parameters[n - 1].mappedTempTable = mappedTempTable;
        }
    }
    
    protected int getMappedTableType(final int n) {
        int n2 = 16;
        if (this.parameters[n - 1].isDataGraph()) {
            switch (this.parameters[n - 1].proDataType) {
                case 36: {
                    n2 = 15;
                    break;
                }
                case 37: {
                    n2 = 17;
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("Unknown DataGraph related data type: " + this.parameters[n - 1].proDataType);
                }
            }
        }
        return n2;
    }
    
    public boolean isDataGraph(final int n) {
        return this.parameters[n - 1].isDataGraph();
    }
    
    public int getFlags(final int n) {
        return this.parameters[n - 1].flags;
    }
    
    boolean isInputFast(final int n) {
        return (this.parameters[n].mode & 0x1) != 0x0;
    }
    
    boolean isOutputFast(final int n) {
        return (this.parameters[n].mode & 0x2) != 0x0;
    }
    
    boolean isResultSetFast(final int n) {
        final int proDataType = this.parameters[n].proDataType;
        return proDataType == 15 || proDataType == 17;
    }
    
    Object getValue(final int n) {
        return this.parameters[n - 1].value;
    }
    
    void setValue(final int n, final Object value) {
        this.parameters[n - 1].value = value;
    }
    
    Object setScalarValues(final OutputParamReader outputParamReader, final BooleanHolder booleanHolder) throws Open4GLException {
        final Tracer tracer = RunTimeProperties.tracer;
        booleanHolder.setBooleanValue(true);
        this.setOutputReady();
        final com.progress.open4gl.dynamicapi.ResultSet outputSet = outputParamReader.getOutputSet();
        if (this.isFunction()) {
            if (this.funcIsExtent) {
                outputParamReader.setOutputSet(new com.progress.open4gl.dynamicapi.ResultSet(new ArrayParameterMetaData(this.funcExtentValue).getMetaData(), new StreamReader(outputParamReader.getStream(), outputParamReader.getSession().getServerMajorVersion())));
            }
            boolean nextOutputParam;
            try {
                boolean b = false;
                if (IsValueType(this.funcDatatype) && !this.funcHasHolder) {
                    b = true;
                }
                nextOutputParam = outputParamReader.nextOutputParam(this.funcDatatype, true, this.funcIsExtent, b, this.funcExtentValue);
                if (this.funcIsExtent) {
                    outputParamReader.setOutputSet(outputSet);
                }
            }
            catch (ClientException ex) {
                throw ExceptionConverter.convertToSystemErrorException(ex);
            }
            if (!nextOutputParam) {
                throw new SystemErrorException(23L, null);
            }
            this.functionValue = outputParamReader.getValue();
        }
        boolean nextOutputParam2;
        try {
            nextOutputParam2 = outputParamReader.nextOutputParam(1, true, false, false, 0);
            this.procedureValue = outputParamReader.getValue();
        }
        catch (ClientException ex2) {
            throw ExceptionConverter.convertToSystemErrorException(ex2);
        }
        if (!nextOutputParam2) {
            throw new SystemErrorException(23L, null);
        }
        final Object value = outputParamReader.getValue();
        if (value == null && outputParamReader.noReturnValue()) {
            booleanHolder.setBooleanValue(false);
        }
        for (int i = 0; i < this.getNumParams(); ++i) {
            if (this.parameters[i].isExtent) {
                outputParamReader.setOutputSet(new com.progress.open4gl.dynamicapi.ResultSet(new ArrayParameterMetaData(new Integer(this.parameters[i].extentValue)).getMetaData(), new StreamReader(outputParamReader.getStream(), outputParamReader.getSession().getServerMajorVersion())));
            }
            boolean nextOutputParam3;
            try {
                int n = this.parameters[i].proDataType;
                if (this.parameters[i].mappedTempTable) {
                    n = this.getMappedTableType(i + 1);
                }
                nextOutputParam3 = outputParamReader.nextOutputParam(n, true, this.parameters[i].isExtent, false, this.parameters[i].extentValue);
            }
            catch (ClientException ex3) {
                throw ExceptionConverter.convertToSystemErrorException(ex3);
            }
            if (this.parameters[i].isExtent) {
                outputParamReader.setOutputSet(outputSet);
            }
            if (!nextOutputParam3) {
                throw new SystemErrorException(24L, null);
            }
            if (!this.isResultSetFast(i)) {
                if (this.isOutputFast(i)) {
                    this.parameters[i].value = outputParamReader.getValue();
                    if (RunTimeProperties.isTracing()) {
                        final Object value2 = this.parameters[i].value;
                        String str;
                        if (value2 != null) {
                            str = value2.toString();
                        }
                        else {
                            str = "null";
                        }
                        if (value2 != null) {
                            if (value2 instanceof COMHandle) {
                                str = new Long(((COMHandle)value2).getLong()).toString();
                            }
                            if (value2 instanceof Handle) {
                                str = new Long(((Handle)value2).getLong()).toString();
                            }
                            if (value2 instanceof GregorianCalendar) {
                                str = ((GregorianCalendar)value2).getTime().toString();
                            }
                        }
                        tracer.print("\tOUTPUT Parameter " + (i + 1) + ": " + str, 3);
                    }
                }
            }
        }
        boolean nextOutputParam4;
        try {
            nextOutputParam4 = outputParamReader.nextOutputParam(0, false, false, false, 0);
        }
        catch (ClientException ex4) {
            throw ExceptionConverter.convertToSystemErrorException(ex4);
        }
        if (nextOutputParam4) {
            throw new SystemErrorException(26L, null);
        }
        tracer.print("       Return Value: " + value, 3);
        return value;
    }
    
    public void cleanUp() {
        this.setOutputNotReady();
        this.function = false;
        this.funcDatatype = 0;
        this.funcIsExtent = false;
        this.funcExtentValue = 0;
        this.funcHasHolder = false;
        this.functionValue = null;
        this.procedureValue = null;
        this.numOutputSets = 0;
        this.numSets = 0;
        this.hasResultSet = false;
        this.hasOutputResultSet = false;
        this.hasInputResultSet = false;
        this.readHdr = false;
        for (int i = 0; i < this.getNumParams(); ++i) {
            this.parameters[i] = null;
        }
    }
    
    public void setParameter(final int n, final Object o, final int n2, final int n3, final boolean b, final int n4) throws Open4GLException {
        this.setParameter(n, o, n2, n3, b, n4, false, false, false);
    }
    
    public void setParameter(final int value, final Object value2, int mode, final int proDataType, final boolean isExtent, final int extentValue, final boolean xmlValue, final boolean b, final boolean writeXmlBeforeImage) throws Open4GLException {
        if (this.outputReady) {
            this.cleanUp();
        }
        if (mode == 3) {
            mode = 3;
        }
        final int n = value - 1;
        if (mode == 0 || (mode & 0xFFFFFFFC) != 0x0) {
            throw new Open4GLException(27L, null);
        }
        try {
            this.parameters[n] = new Param();
            this.parameters[n].mode = mode;
            this.parameters[n].proDataType = proDataType;
            this.parameters[n].isExtent = isExtent;
            this.parameters[n].extentValue = extentValue;
            this.parameters[n].value = value2;
            this.parameters[n].setXmlValue(xmlValue);
            this.parameters[n].isRpcStyleSoapMessage(b);
            this.parameters[n].setWriteXmlBeforeImage(writeXmlBeforeImage);
            if (proDataType == 15 || proDataType == 17) {
                ++this.numSets;
                this.hasResultSet = true;
                if ((mode & 0x2) != 0x0) {
                    ++this.numOutputSets;
                    this.hasOutputResultSet = true;
                }
                if ((mode & 0x1) != 0x0) {
                    this.hasInputResultSet = true;
                }
            }
            if (this.parameters[n].isDataGraph()) {
                ++this.numDataGraphs;
                if (mode == 2 || mode == 3) {
                    ++this.numOutputDataGraphs;
                }
                if (mode == 1 || mode == 3) {
                    this.hasInputDataGraphField = true;
                }
            }
        }
        catch (Exception ex) {
            throw new Open4GLException(28L, new Object[] { new Integer(value).toString() });
        }
    }
    
    public void setBooleanFunction() {
        this.setFunction(3, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setBooleanFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(3, b, b2, n);
    }
    
    public void setIntegerFunction() {
        this.setFunction(4, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setIntegerFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(4, b, b2, n);
    }
    
    public void setLongFunction() {
        this.setFunction(7, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setLongFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(7, b, b2, n);
    }
    
    public void setInt64Function() {
        this.setFunction(41, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setInt64Function(final boolean b, final boolean b2, final int n) {
        this.setFunction(41, b, b2, n);
    }
    
    public void setDecimalFunction() {
        this.setFunction(5, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setDecimalFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(5, b, b2, n);
    }
    
    public void setStringFunction() {
        this.setFunction(1, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setStringFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(1, b, b2, n);
    }
    
    public void setDateFunction() {
        this.setFunction(2, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setDateFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(2, b, b2, n);
    }
    
    public void setDateTimeFunction() {
        this.setFunction(34, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setDateTimeFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(34, b, b2, n);
    }
    
    public void setDateTimeTzFunction() {
        this.setFunction(40, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setDateTimeTzFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(40, b, b2, n);
    }
    
    public void setByteFunction() {
        this.setFunction(8, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setByteFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(8, b, b2, n);
    }
    
    public void setRowidFunction() {
        this.setFunction(13, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setRowidFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(13, b, b2, n);
    }
    
    public void setMemptrFunction() throws Open4GLException {
        this.funcDatatype = 11;
        throw new Open4GLException("MEMPTR not supported as return type for remote function.", (Object[])null);
    }
    
    public void setLongcharFunction() throws Open4GLException {
        this.funcDatatype = 39;
        throw new Open4GLException("LONGCHAR not supported as return type for remote function.", (Object[])null);
    }
    
    public void setHandleFunction() {
        this.setFunction(10, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setHandleFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(10, b, b2, n);
    }
    
    public void setCOMHandleFunction() {
        this.setFunction(14, this.funcIsExtent, this.funcHasHolder, 0);
    }
    
    public void setCOMHandleFunction(final boolean b, final boolean b2, final int n) {
        this.setFunction(14, b, b2, n);
    }
    
    private void setFunction(final int funcDatatype, final boolean funcIsExtent, final boolean funcHasHolder, final int funcExtentValue) {
        this.funcDatatype = funcDatatype;
        this.function = true;
        this.funcIsExtent = funcIsExtent;
        this.funcHasHolder = funcHasHolder;
        this.funcExtentValue = funcExtentValue;
    }
    
    public Object getFunctionReturnValue() {
        if (!this.function) {
            throw new Open4GLError(1L, null);
        }
        return this.functionValue;
    }
    
    public void setIsFuncReturnExtent(final boolean funcIsExtent) {
        this.funcIsExtent = funcIsExtent;
    }
    
    public boolean getIsFunctionReturnExtent() {
        return this.funcIsExtent;
    }
    
    public void setIsReturnUnknown(final boolean funcHasHolder) {
        this.funcHasHolder = funcHasHolder;
    }
    
    public Object getProcedureReturnValue() {
        return this.procedureValue;
    }
    
    public void setParamNum(final DataGraphMetaData dataGraphMetaData, final int parmNum) {
        dataGraphMetaData.setParmNum(parmNum);
    }
    
    public void setInOut(final DataGraphMetaData dataGraphMetaData, final int inOut) {
        dataGraphMetaData.setInOut(inOut);
    }
    
    public void validate() throws Open4GLException {
        for (int i = 0; i < this.parameters.length; ++i) {
            if (this.parameters[i] == null) {
                throw new Open4GLException("Parameter Index %s is not defined in the parameter set.", new Object[] { new Integer(i).toString() });
            }
        }
    }
}
