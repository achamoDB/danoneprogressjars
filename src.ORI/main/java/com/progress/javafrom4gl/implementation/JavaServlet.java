// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.IntHolder;
import com.progress.javafrom4gl.JavaService;
import com.progress.javafrom4gl.ServiceConnection;

public class JavaServlet implements StopInterface
{
    public static final int WRITELAST_NORMAL = 0;
    public static final int WRITELAST_ABNORMAL = -4;
    public static final int WRITELAST_STOP = -5;
    private RequestThread requestThread;
    private ServiceImpl serviceImpl;
    private ServiceConnection serviceConnection;
    private ExportList exportList;
    InputBufferQueue inputQueue;
    OutputBufferQueue outputQueue;
    private RequestExecuter requestExecuter;
    private ReferenceList referenceList;
    private String connectionID;
    
    public String toString() {
        if (this.serviceConnection != null) {
            return this.serviceConnection.toString();
        }
        return "";
    }
    
    JavaServlet(final ServiceImpl serviceImpl, final String connectionID, final String s, final String s2, final String s3, final StopInterface stopInterface, final String s4, final String s5) throws Exception {
        this.serviceImpl = serviceImpl;
        this.connectionID = connectionID;
        this.requestThread = new RequestThread();
        this.referenceList = new ReferenceList(serviceImpl.getJvmId());
        final JavaService javaService = serviceImpl.getJavaService();
        try {
            this.serviceConnection = javaService._connect(s, s2, connectionID, s3, s5);
        }
        catch (Exception ex) {
            this.requestThread.doStop();
            this.requestThread = null;
            this.referenceList = null;
            this.serviceImpl = null;
            this.connectionID = null;
            throw ex;
        }
        this.exportList = new ExportList(this.serviceConnection._getExportList());
        this.referenceList.registerInitialReference(this.serviceConnection._getInitialObject());
        this.requestExecuter = new RequestExecuter(this.exportList, stopInterface, this.referenceList);
    }
    
    public void doStop() {
        this.serviceConnection._stop();
    }
    
    public void write(final byte[] array, final int n, final int n2, final boolean b) throws Exception {
        this.inputQueue.put(array, n, b, n2);
    }
    
    public void initRequest() throws Exception {
        this.inputQueue = new InputBufferQueue();
        this.outputQueue = new OutputBufferQueue(4000L);
        this.requestExecuter.setIO(this.inputQueue, this.outputQueue);
        this.requestThread.init(this.requestExecuter);
    }
    
    public byte[] read(final IntHolder intHolder) throws Exception {
        return this.outputQueue.poll(intHolder);
    }
    
    public void disconnect() throws Exception {
        this.serviceConnection._disconnect();
        this.requestThread.doStop();
    }
}
