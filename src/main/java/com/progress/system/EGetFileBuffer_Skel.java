// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import java.util.Date;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class EGetFileBuffer_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 3499774685425334046L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class classDef()"), new Operation("java.lang.String description()"), new Operation("java.lang.String eventTypeString()"), new Operation("boolean expedite()"), new Operation("short getCategory()"), new Operation("java.lang.String getErrorString()"), new Operation("java.lang.Object getEventData()"), new Operation("java.lang.String getNotificationName()"), new Operation("java.lang.String getNotificationType()"), new Operation("int getSeverityLevel()"), new Operation("java.lang.String getSource()"), new Operation("java.lang.String getSubCategory()"), new Operation("java.lang.Object issuer()"), new Operation("java.lang.String issuerName()"), new Operation("void setSource(java.lang.String)"), new Operation("java.util.Date timeIssued()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 3499774685425334046L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final EGetFileBuffer eGetFileBuffer = (EGetFileBuffer)remote;
        Label_0776: {
            switch (n) {
                case 0: {
                    remoteCall.releaseInputStream();
                    final Class classDef = eGetFileBuffer.classDef();
                    try {
                        remoteCall.getResultStream(true).writeObject(classDef);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                }
                case 1: {
                    remoteCall.releaseInputStream();
                    final String description = eGetFileBuffer.description();
                    try {
                        remoteCall.getResultStream(true).writeObject(description);
                        return;
                    }
                    catch (IOException ex2) {
                        throw new MarshalException("error marshalling return", ex2);
                    }
                }
                case 2: {
                    remoteCall.releaseInputStream();
                    final String eventTypeString = eGetFileBuffer.eventTypeString();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventTypeString);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final boolean expedite = eGetFileBuffer.expedite();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(expedite);
                        return;
                    }
                    catch (IOException ex4) {
                        throw new MarshalException("error marshalling return", ex4);
                    }
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final short category = eGetFileBuffer.getCategory();
                    try {
                        remoteCall.getResultStream(true).writeShort(category);
                        return;
                    }
                    catch (IOException ex5) {
                        throw new MarshalException("error marshalling return", ex5);
                    }
                }
                case 5: {
                    remoteCall.releaseInputStream();
                    final String errorString = eGetFileBuffer.getErrorString();
                    try {
                        remoteCall.getResultStream(true).writeObject(errorString);
                        return;
                    }
                    catch (IOException ex6) {
                        throw new MarshalException("error marshalling return", ex6);
                    }
                }
                case 6: {
                    remoteCall.releaseInputStream();
                    final Object eventData = eGetFileBuffer.getEventData();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventData);
                        return;
                    }
                    catch (IOException ex7) {
                        throw new MarshalException("error marshalling return", ex7);
                    }
                }
                case 7: {
                    remoteCall.releaseInputStream();
                    final String notificationName = eGetFileBuffer.getNotificationName();
                    try {
                        remoteCall.getResultStream(true).writeObject(notificationName);
                        return;
                    }
                    catch (IOException ex8) {
                        throw new MarshalException("error marshalling return", ex8);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final String notificationType = eGetFileBuffer.getNotificationType();
                    try {
                        remoteCall.getResultStream(true).writeObject(notificationType);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final int severityLevel = eGetFileBuffer.getSeverityLevel();
                    try {
                        remoteCall.getResultStream(true).writeInt(severityLevel);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final String source = eGetFileBuffer.getSource();
                    try {
                        remoteCall.getResultStream(true).writeObject(source);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String subCategory = eGetFileBuffer.getSubCategory();
                    try {
                        remoteCall.getResultStream(true).writeObject(subCategory);
                        return;
                    }
                    catch (IOException ex12) {
                        throw new MarshalException("error marshalling return", ex12);
                    }
                }
                case 14: {
                    break Label_0776;
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final String issuerName = eGetFileBuffer.issuerName();
                    try {
                        remoteCall.getResultStream(true).writeObject(issuerName);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                    String source2;
                    try {
                        source2 = (String)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex16) {
                        throw new UnmarshalException("error unmarshalling arguments", ex16);
                    }
                    catch (ClassNotFoundException ex17) {
                        throw new UnmarshalException("error unmarshalling arguments", ex17);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    eGetFileBuffer.setSource(source2);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final Date timeIssued = eGetFileBuffer.timeIssued();
                    try {
                        remoteCall.getResultStream(true).writeObject(timeIssued);
                        return;
                    }
                    catch (IOException ex14) {
                        throw new MarshalException("error marshalling return", ex14);
                    }
                    break;
                }
                case 12: {
                    remoteCall.releaseInputStream();
                    final Object issuer = eGetFileBuffer.issuer();
                    try {
                        remoteCall.getResultStream(true).writeObject(issuer);
                        return;
                    }
                    catch (IOException ex13) {
                        throw new MarshalException("error marshalling return", ex13);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return EGetFileBuffer_Skel.operations.clone();
    }
}
