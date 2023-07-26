// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import java.util.Date;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class YodaPluginEventSuper_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -8860080668403469005L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class classDef()"), new Operation("java.lang.String description()"), new Operation("java.lang.String eventTypeString()"), new Operation("boolean expedite()"), new Operation("short getCategory()"), new Operation("java.lang.String getErrorString()"), new Operation("java.lang.Object getEventData()"), new Operation("java.lang.String getInstanceName()"), new Operation("java.lang.String getNotificationName()"), new Operation("java.lang.String getNotificationType()"), new Operation("com.progress.chimera.common.IChimeraHierarchy getPluginObj()"), new Operation("java.lang.String getPropGrpFullSpec()"), new Operation("int getSeverityLevel()"), new Operation("java.lang.String getSource()"), new Operation("java.lang.String getSubCategory()"), new Operation("java.lang.Object guiID()"), new Operation("java.lang.Object issuer()"), new Operation("java.lang.String issuerName()"), new Operation("void setSource(java.lang.String)"), new Operation("java.util.Date timeIssued()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -8860080668403469005L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final YodaPluginEventSuper yodaPluginEventSuper = (YodaPluginEventSuper)remote;
        Label_0984: {
            switch (n) {
                case 0: {
                    remoteCall.releaseInputStream();
                    final Class classDef = yodaPluginEventSuper.classDef();
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
                    final String description = yodaPluginEventSuper.description();
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
                    final String eventTypeString = yodaPluginEventSuper.eventTypeString();
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
                    final boolean expedite = yodaPluginEventSuper.expedite();
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
                    final short category = yodaPluginEventSuper.getCategory();
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
                    final String errorString = yodaPluginEventSuper.getErrorString();
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
                    final Object eventData = yodaPluginEventSuper.getEventData();
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
                    final String instanceName = yodaPluginEventSuper.getInstanceName();
                    try {
                        remoteCall.getResultStream(true).writeObject(instanceName);
                        return;
                    }
                    catch (IOException ex8) {
                        throw new MarshalException("error marshalling return", ex8);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final String notificationName = yodaPluginEventSuper.getNotificationName();
                    try {
                        remoteCall.getResultStream(true).writeObject(notificationName);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final String notificationType = yodaPluginEventSuper.getNotificationType();
                    try {
                        remoteCall.getResultStream(true).writeObject(notificationType);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final IChimeraHierarchy pluginObj = yodaPluginEventSuper.getPluginObj();
                    try {
                        remoteCall.getResultStream(true).writeObject(pluginObj);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String propGrpFullSpec = yodaPluginEventSuper.getPropGrpFullSpec();
                    try {
                        remoteCall.getResultStream(true).writeObject(propGrpFullSpec);
                        return;
                    }
                    catch (IOException ex12) {
                        throw new MarshalException("error marshalling return", ex12);
                    }
                }
                case 12: {
                    remoteCall.releaseInputStream();
                    final int severityLevel = yodaPluginEventSuper.getSeverityLevel();
                    try {
                        remoteCall.getResultStream(true).writeInt(severityLevel);
                        return;
                    }
                    catch (IOException ex13) {
                        throw new MarshalException("error marshalling return", ex13);
                    }
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final String source = yodaPluginEventSuper.getSource();
                    try {
                        remoteCall.getResultStream(true).writeObject(source);
                        return;
                    }
                    catch (IOException ex14) {
                        throw new MarshalException("error marshalling return", ex14);
                    }
                }
                case 14: {
                    remoteCall.releaseInputStream();
                    final String subCategory = yodaPluginEventSuper.getSubCategory();
                    try {
                        remoteCall.getResultStream(true).writeObject(subCategory);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final Object guiID = yodaPluginEventSuper.guiID();
                    try {
                        remoteCall.getResultStream(true).writeObject(guiID);
                        return;
                    }
                    catch (IOException ex16) {
                        throw new MarshalException("error marshalling return", ex16);
                    }
                }
                case 18: {
                    break Label_0984;
                }
                case 17: {
                    remoteCall.releaseInputStream();
                    final String issuerName = yodaPluginEventSuper.issuerName();
                    try {
                        remoteCall.getResultStream(true).writeObject(issuerName);
                        return;
                    }
                    catch (IOException ex19) {
                        throw new MarshalException("error marshalling return", ex19);
                    }
                    String source2;
                    try {
                        source2 = (String)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex20) {
                        throw new UnmarshalException("error unmarshalling arguments", ex20);
                    }
                    catch (ClassNotFoundException ex21) {
                        throw new UnmarshalException("error unmarshalling arguments", ex21);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    yodaPluginEventSuper.setSource(source2);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex22) {
                        throw new MarshalException("error marshalling return", ex22);
                    }
                }
                case 19: {
                    remoteCall.releaseInputStream();
                    final Date timeIssued = yodaPluginEventSuper.timeIssued();
                    try {
                        remoteCall.getResultStream(true).writeObject(timeIssued);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                    break;
                }
                case 16: {
                    remoteCall.releaseInputStream();
                    final Object issuer = yodaPluginEventSuper.issuer();
                    try {
                        remoteCall.getResultStream(true).writeObject(issuer);
                        return;
                    }
                    catch (IOException ex17) {
                        throw new MarshalException("error marshalling return", ex17);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return YodaPluginEventSuper_Skel.operations.clone();
    }
}
