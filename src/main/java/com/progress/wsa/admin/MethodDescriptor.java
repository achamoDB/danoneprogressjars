// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.soap.util.xml.QName;

public class MethodDescriptor
{
    public static final int WSA_METHOD_TYPE_CONSTRUCTOR = 0;
    public static final int WSA_METHOD_TYPE_RELEASE = 1;
    public static final int WSA_METHOD_TYPE_CREATE_PROC = 2;
    public static final int WSA_METHOD_TYPE_INVOKE_NORMAL = 3;
    public static final int WSA_METHOD_TYPE_INVOKE_PERSISTENT_UDF = 4;
    public static final int WSA_METHOD_TYPE_INVOKE_PERSISTENT = 4;
    public static final int WSA_METHOD_TYPE_METHOD = 5;
    public static final int WSA_METHOD_TYPE_CREATE_SUB = 6;
    public static final int WSA_METHOD_TYPE_INVOKE_PERSISTENT_PROC = 7;
    public static final int WSA_METHOD_TYPE_WSA_4GLPROVIDER = 8;
    private QName m_methodName;
    private MethodParam m_returnParam;
    private MethodParam[] m_params;
    private String m_procedureName;
    private String m_wsaMethodName;
    private int m_wsaMethodType;
    private boolean m_hasConnectReturnString;
    
    public MethodDescriptor(final QName qName, final String procedureName, final String wsaMethodName, final int wsaMethodType, final MethodParam returnParam, final MethodParam[] params) {
        this.m_methodName = new QName("", "");
        this.m_returnParam = null;
        this.m_params = new MethodParam[0];
        this.m_procedureName = "";
        this.m_wsaMethodName = "";
        this.m_wsaMethodType = -1;
        this.m_hasConnectReturnString = false;
        this.m_methodName = ((null != qName) ? qName : new QName("", "unspecified"));
        this.m_returnParam = returnParam;
        this.m_params = params;
        if (null != procedureName) {
            this.m_procedureName = procedureName.replace('\\', '/');
        }
        else {
            this.m_procedureName = procedureName;
        }
        this.m_wsaMethodName = wsaMethodName;
        this.m_wsaMethodType = wsaMethodType;
    }
    
    public QName name() {
        return this.m_methodName;
    }
    
    public int parameterCount() {
        return (null == this.m_params) ? 0 : this.m_params.length;
    }
    
    public int inInOutParameterCount() {
        int n = 0;
        if (null != this.m_params && 0 < this.m_params.length) {
            for (int i = 0; i < this.m_params.length; ++i) {
                if (2 != this.m_params[i].inOutType()) {
                    ++n;
                }
            }
        }
        return n;
    }
    
    public int outInOutParameterCount() {
        int n = 0;
        if (null != this.m_params && 0 < this.m_params.length) {
            for (int i = 0; i < this.m_params.length; ++i) {
                if (1 != this.m_params[i].inOutType()) {
                    ++n;
                }
            }
        }
        return n;
    }
    
    public Class returnType() {
        return this.m_returnParam.javaType();
    }
    
    public boolean hasConnectReturnString() {
        return this.m_hasConnectReturnString;
    }
    
    public void setConnectReturnString(final boolean hasConnectReturnString) {
        this.m_hasConnectReturnString = hasConnectReturnString;
    }
    
    public int proReturnType() {
        return this.m_returnParam.pscType();
    }
    
    public boolean funcReturnsExtent() {
        return this.m_returnParam.isExtent();
    }
    
    public MethodParam getReturnParam() {
        return this.m_returnParam;
    }
    
    public String procedureName() throws NullPointerException {
        if (null == this.m_procedureName || 0 == this.m_procedureName.length()) {
            throw new NullPointerException("Null or empty 4GL procedure name");
        }
        return this.m_procedureName;
    }
    
    public String wsaMethodName() {
        return this.m_wsaMethodName;
    }
    
    public int wsaMethodType() {
        return this.m_wsaMethodType;
    }
    
    public boolean useRetVal() {
        return !this.m_returnParam.javaType().getName().equals("void");
    }
    
    public MethodParam parameter(final int n) throws IndexOutOfBoundsException {
        MethodParam methodParam = null;
        if (null != this.m_params) {
            if (0 > n || this.m_params.length - 1 < n) {
                throw new IndexOutOfBoundsException("Not a valid method parameter index");
            }
            methodParam = this.m_params[n];
        }
        return methodParam;
    }
    
    public MethodParam parameter(final QName qName) throws IndexOutOfBoundsException {
        MethodParam methodParam = null;
        if (null != this.m_params) {
            if (null == qName) {
                throw new NullPointerException("Not a valid method parameter name");
            }
            int n = 0;
            while (true) {
                methodParam = this.m_params[n];
                if (qName.equals((Object)methodParam.name())) {
                    break;
                }
                ++n;
            }
        }
        return methodParam;
    }
    
    public MethodParam parameter(final String s) throws IndexOutOfBoundsException {
        if (null != s && 0 < s.length()) {
            int index = s.indexOf(":");
            QName qName;
            if (-1 != index) {
                qName = new QName(s.substring(0, index++), s.substring(index));
            }
            else {
                qName = new QName("", s);
            }
            return this.parameter(qName);
        }
        throw new NullPointerException("Not a valid method parameter name");
    }
}
