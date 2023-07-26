// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.sql.ResultSet;
import com.progress.open4gl.dynamicapi.ParameterMetaData;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.dynamicapi.Util;
import com.progress.open4gl.RunTimeProperties;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;
import com.progress.open4gl.dynamicapi.OutputBuffer;
import java.io.OutputStream;
import java.io.InputStream;

public class RequestExecuter implements StopInterface
{
    private static int MAX_RESPONSE_LEN;
    private InputStream input;
    private OutputStream output;
    private ExportList exportList;
    private StopInterface stopInput;
    private ReferenceList referenceList;
    private OutputBuffer outBuffer;
    private Log log;
    
    RequestExecuter(final ExportList exportList, final StopInterface stopInput, final ReferenceList referenceList) {
        this.log = ServiceRuntime.getLog();
        this.outBuffer = new OutputBuffer(2000);
        this.exportList = exportList;
        this.stopInput = stopInput;
        this.referenceList = referenceList;
    }
    
    void setIO(final InputBufferQueue inputBufferQueue, final OutputBufferQueue outputBufferQueue) {
        this.input = new FromUBInputStream(inputBufferQueue);
        this.output = new ToUBOutputStream(outputBufferQueue);
    }
    
    void stop() {
    }
    
    public void doStop() {
    }
    
    void executeRequest() throws Exception {
        try {
            final Request request = new Request(this.input, this.exportList, this.stopInput, this.referenceList, this.output, this.outBuffer, this.log);
            if (request.isSpecialRequest()) {
                request.executeSpecialRequest(this.referenceList, this.output);
            }
            else {
                request.executeRequest(this.exportList, this.stopInput, this.referenceList, this.output);
            }
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, "executeRequest(): ", t);
            this.streamError(this.output, t.toString());
        }
    }
    
    private void streamError(final OutputStream outputStream, final String s) throws Exception {
        try {
            this.outBuffer.reset();
            this.outBuffer.write(7);
            new ErrorStreamer(this.outBuffer).doStream(s);
            this.outBuffer.write(1);
            this.outBuffer.write(0);
            this.outBuffer.write(0);
            final int len = this.outBuffer.getLen();
            outputStream.write(RunTimeProperties.getStreamProtocolVersion());
            outputStream.write(Util.getHighByte((short)len));
            outputStream.write(Util.getLowByte((short)len));
            outputStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
        }
        finally {
            outputStream.close();
        }
    }
    
    static {
        RequestExecuter.MAX_RESPONSE_LEN = 32767;
    }
    
    static class Request
    {
        private FGLCall fglCall;
        private FGLParameters parameters;
        private FGLSchemas schemas;
        private OutputBuffer outBuffer;
        private int clntVer;
        private Log log;
        private ExportList exportList;
        private StopInterface stopInput;
        private ReferenceList referenceList;
        private OutputStream output;
        private InputStream input;
        private boolean isSpecialGetInit;
        private boolean isSpecialRelease;
        
        Request(final InputStream input, final ExportList exportList, final StopInterface stopInput, final ReferenceList referenceList, final OutputStream output, final OutputBuffer outBuffer, final Log log) throws Exception {
            this.outBuffer = outBuffer;
            this.exportList = exportList;
            this.stopInput = stopInput;
            this.referenceList = referenceList;
            this.output = output;
            this.input = input;
            this.log = log;
            this.fglCall = new FGLCall(input, log);
            this.clntVer = this.fglCall.getClientVersion();
            this.isSpecialRelease = this.fglCall.isSpecialRelease();
            this.isSpecialGetInit = this.fglCall.isSpecialGetInit();
            this.parameters = new FGLParameters(input, this.fglCall.getNumParams(), this.isSpecialRelease || !this.isSpecialGetInit, this.isSpecialGetInit || !this.isSpecialRelease, this.clntVer, log);
            if (this.parameters.hasTableParameter()) {
                this.schemas = new FGLSchemas(input, this.parameters, this.clntVer, log);
            }
            else {
                this.schemas = null;
            }
        }
        
        boolean isSpecialRequest() {
            return this.isSpecialRelease || this.isSpecialGetInit;
        }
        
        void executeSpecialRequest(final ReferenceList list, final OutputStream outputStream) {
            this.log.LogStackTrace(1, true, "executeSpecialRequest(): ", new Throwable("Feature not implemented"));
        }
        
        void executeRequest(final ExportList list, final StopInterface stopInterface, final ReferenceList list2, final OutputStream outputStream) throws Exception {
            final Rowid targetreference = this.parameters.getTargetreference();
            byte[] bytes = null;
            if (targetreference != null) {
                bytes = targetreference.getBytes();
            }
            final ObjectHolder byReference = list2.getByReference(bytes);
            final InterfaceHolder interface1 = byReference.getInterface(this.fglCall.getInterfaceName());
            if (!list.interfaceExported(interface1.getName())) {
                throw new Exception("Interface " + interface1.getName() + " is not exported.");
            }
            final MethodHolder methodHolder = new MethodHolder(this.fglCall.getMethodName(), this.parameters, list2, this.schemas, this.input, this.clntVer);
            interface1.populateMethod(methodHolder, this.parameters.getReturnProType());
            methodHolder.invoke(byReference.getObject());
            final Object returnedObject = methodHolder.getReturnedObject();
            Object convertRetVal;
            if (this.parameters.returnedRef()) {
                convertRetVal = new Rowid(list2.registerReference(returnedObject));
            }
            else {
                convertRetVal = DataTypeMap.convertRetVal(this.parameters.getReturnProType(), returnedObject, methodHolder.getReturnType());
            }
            this.parameters.setOutputData(methodHolder.getOutputValues(), convertRetVal);
            this.streamOut(this.parameters, outputStream, this.schemas);
        }
        
        void streamOut(final FGLParameters fglParameters, final OutputStream outputStream, final FGLSchemas fglSchemas) throws Exception {
            try {
                this.outBuffer.reset();
                this.outBuffer.write(8);
                new OutputParameterStreamer(this.outBuffer).streamResultSet(new OutputParamSet(fglParameters), ParameterMetaData.metaData, this.clntVer, 0);
                this.outBuffer.write(1);
                this.outBuffer.write(0);
                this.outBuffer.write(0);
                final int len = this.outBuffer.getLen();
                if (len > RequestExecuter.MAX_RESPONSE_LEN) {
                    throw new Exception("Java output is too large. Use temp-tables");
                }
                outputStream.write(RunTimeProperties.getStreamProtocolVersion());
                outputStream.write(Util.getHighByte((short)len));
                outputStream.write(Util.getLowByte((short)len));
                outputStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
                if (this.clntVer > 9) {
                    for (int n = (fglSchemas == null) ? 0 : fglSchemas.getNumTTOutParms(), i = 0; i < n; ++i) {
                        outputStream.write(RunTimeProperties.getStreamProtocolVersion());
                        outputStream.write(Util.getHighByte((short)1));
                        outputStream.write(Util.getLowByte((short)1));
                        outputStream.write(14);
                    }
                }
                new OutputSetsStreamer(outputStream).streamOut(fglParameters);
            }
            finally {
                outputStream.close();
            }
        }
    }
}
