// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.io.IOException;
import java.net.Socket;

public class Port
{
    public static final int MAX_PORT_VALUE = 65535;
    public static final int MIN_PORT_VALUE = 1;
    private int portNumber;
    private String PortName;
    
    public Port(final int i) throws PortRangeException, ServiceNameException {
        this(i + "");
    }
    
    public Port(final String name) throws PortRangeException, ServiceNameException {
        this.portNumber = 0;
        this.setName(name);
    }
    
    public void setName(final String portName) throws PortRangeException, ServiceNameException {
        this.setPortNum(this.PortName = portName);
    }
    
    public String getName() {
        return this.PortName;
    }
    
    public int getPortInt() {
        return this.portNumber;
    }
    
    public boolean isInUse() {
        boolean b = false;
        try {
            final Socket socket = new Socket("localhost", this.portNumber);
            if (socket != null) {
                b = true;
                socket.close();
            }
        }
        catch (IOException ex) {
            b = false;
        }
        return b;
    }
    
    private void validatePortRange(final int n) throws PortRangeException {
        if (n < 1 || n > 65535) {
            throw new PortRangeException(n);
        }
    }
    
    private void setPortNum(String s) throws PortRangeException, ServiceNameException {
        try {
            this.validatePortRange(this.portNumber = Integer.parseInt(s));
        }
        catch (NumberFormatException ex) {
            if (s == null || NetLib.getPortNumByName(s, "tcp") <= 0) {
                if (s == null) {
                    s = "null";
                }
                throw new ServiceNameException(s);
            }
            this.validatePortRange(this.portNumber = NetLib.getPortNumByName(s, "tcp"));
        }
    }
    
    public static class PortException extends Exception
    {
        PortException(final String message) {
            super(message);
        }
    }
    
    public static class PortRangeException extends PortException
    {
        private static String errMessage;
        
        PortRangeException(final int i) {
            super(PortRangeException.errMessage + i);
        }
        
        static {
            PortRangeException.errMessage = "Specify a value greater than 1 and less than or equal to 65535: ";
        }
    }
    
    public static class ServiceNameException extends PortException
    {
        private static String message;
        
        ServiceNameException(final String str) {
            super(ServiceNameException.message + str);
        }
        
        static {
            ServiceNameException.message = "An invalid service name was specified: ";
        }
    }
}
