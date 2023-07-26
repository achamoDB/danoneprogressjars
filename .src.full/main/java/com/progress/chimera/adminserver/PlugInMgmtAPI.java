// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.registry.Registry;
import com.progress.common.property.PropertyList;
import com.progress.common.property.IPropertyProvider;
import java.rmi.Remote;
import java.util.Vector;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class PlugInMgmtAPI extends UnicastRemoteObject implements IPlugInMgmtAPI
{
    public PlugInMgmtAPI() throws RemoteException {
    }
    
    public String[] convertAddress(final String s) throws UnknownAddressFormatException {
        if (s == null || s.length() == 0) {
            throw new UnknownAddressFormatException(s);
        }
        final String[] array = new String[2];
        final int index = s.indexOf(46);
        array[0] = ((index == -1) ? s : s.substring(0, index));
        array[1] = ((index == -1 || index == s.length()) ? null : s.substring(index + 1));
        return array;
    }
    
    public Vector getPluginInstances(final String s) throws UnknownAddressFormatException {
        final Vector pluginInfo = this.getPluginInfo(s);
        final Vector<Remote> vector = new Vector<Remote>();
        for (int i = 0; i < pluginInfo.size(); ++i) {
            vector.addElement(pluginInfo.elementAt(i).getPluginInstance().getRemoteObject("", ""));
        }
        return vector;
    }
    
    public abstract Vector getServerPluginInfo();
    
    public Vector getPluginInfo(final String s) throws UnknownAddressFormatException {
        final String anotherString = this.convertAddress(s)[0];
        final Vector<ServerPluginInfo> vector = new Vector<ServerPluginInfo>();
        for (int i = 0; i < this.getServerPluginInfo().size(); ++i) {
            final ServerPluginInfo obj = this.getServerPluginInfo().elementAt(i);
            if ((anotherString.equals("*") || obj.getId().equalsIgnoreCase(anotherString)) && obj.getPluginInstance() != null) {
                final Remote remoteObject = obj.getPluginInstance().getRemoteObject("", "");
                if (remoteObject != null && remoteObject instanceof IPropertyProvider) {
                    vector.addElement(obj);
                }
            }
        }
        return vector;
    }
    
    public PropertyList getPropertyValues(final String s) throws UnknownAddressFormatException, UnknownPluginException {
        final String[] convertAddress = this.convertAddress(s);
        final String s2 = convertAddress[0];
        final String s3 = convertAddress[1];
        if (s3 == null) {
            throw new UnknownAddressFormatException(s);
        }
        final Vector pluginInfo = this.getPluginInfo(s2);
        if (pluginInfo.isEmpty()) {
            throw new UnknownPluginException(s2);
        }
        final PropertyList list = new PropertyList();
        for (int i = 0; i < pluginInfo.size(); ++i) {
            final ServerPluginInfo serverPluginInfo = pluginInfo.elementAt(i);
            ((IPropertyProvider)serverPluginInfo.getPluginInstance().getRemoteObject("", "")).getPlugInPropertyManager().makePropertyListElements(list, serverPluginInfo.getId(), s3);
        }
        return list;
    }
    
    public abstract Registry getRMIRegistry();
    
    public class PluginException extends Exception
    {
        PluginException(final String message) {
            super(message);
        }
    }
    
    public class UnknownAddressFormatException extends PluginException
    {
        UnknownAddressFormatException(final String str) {
            super("Could not match address pattern: " + str);
        }
    }
    
    public class UnknownPluginException extends PluginException
    {
        UnknownPluginException(final String str) {
            super("Could not match plug-in name pattern: " + str);
        }
    }
}
