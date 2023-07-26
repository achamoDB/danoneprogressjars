// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import org.apache.soap.util.xml.QName;

public class MethodParam
{
    public static final int PARAM_TYPE_IN = 1;
    public static final int PARAM_TYPE_OUT = 2;
    public static final int PARAM_TYPE_IN_OUT = 3;
    private QName m_paramName;
    private Class m_javaClass;
    private int m_pscType;
    private int m_paramNumber;
    private int m_inOutType;
    private boolean m_isNillable;
    private ResultSetMetaData m_rsMetaData;
    private boolean m_isExtent;
    private int m_extentValue;
    private boolean m_writeXmlBeforeImage;
    
    public MethodParam(final QName qName, final Class javaClass, final int pscType, final int paramNumber, final int inOutType, final boolean isNillable, final boolean writeXmlBeforeImage, final boolean isExtent, final int extentValue) {
        this.m_paramName = null;
        this.m_javaClass = null;
        this.m_pscType = -1;
        this.m_paramNumber = -1;
        this.m_inOutType = -1;
        this.m_isNillable = false;
        this.m_rsMetaData = null;
        this.m_isExtent = false;
        this.m_extentValue = 0;
        this.m_writeXmlBeforeImage = false;
        this.m_paramName = ((null != qName) ? qName : new QName("", "unspecified"));
        this.m_javaClass = javaClass;
        this.m_pscType = pscType;
        this.m_paramNumber = paramNumber;
        this.m_inOutType = inOutType;
        this.m_isNillable = isNillable;
        this.m_writeXmlBeforeImage = writeXmlBeforeImage;
        this.m_rsMetaData = null;
        this.m_isExtent = isExtent;
        this.m_extentValue = extentValue;
    }
    
    public MethodParam(final QName qName, final Class javaClass, final int pscType, final int paramNumber, final int inOutType, final boolean isNillable, final boolean writeXmlBeforeImage, final ResultSetMetaData rsMetaData) {
        this.m_paramName = null;
        this.m_javaClass = null;
        this.m_pscType = -1;
        this.m_paramNumber = -1;
        this.m_inOutType = -1;
        this.m_isNillable = false;
        this.m_rsMetaData = null;
        this.m_isExtent = false;
        this.m_extentValue = 0;
        this.m_writeXmlBeforeImage = false;
        this.m_paramName = ((null != qName) ? qName : new QName("", "unspecified"));
        this.m_javaClass = javaClass;
        this.m_pscType = pscType;
        this.m_paramNumber = paramNumber;
        this.m_inOutType = inOutType;
        this.m_isNillable = isNillable;
        this.m_writeXmlBeforeImage = writeXmlBeforeImage;
        this.m_rsMetaData = rsMetaData;
    }
    
    public QName name() {
        return this.m_paramName;
    }
    
    public Class javaType() {
        return this.m_javaClass;
    }
    
    public int pscType() {
        return this.m_pscType;
    }
    
    public int paramNumber() {
        return this.m_paramNumber;
    }
    
    public boolean isNillable() {
        return this.m_isNillable;
    }
    
    public int inOutType() {
        return this.m_inOutType;
    }
    
    public ResultSetMetaData metaData() {
        return this.m_rsMetaData;
    }
    
    public boolean isExtent() {
        return this.m_isExtent;
    }
    
    public int getExtent() {
        return this.m_extentValue;
    }
    
    public boolean writeXmlBeforeImage() {
        return this.m_writeXmlBeforeImage;
    }
}
