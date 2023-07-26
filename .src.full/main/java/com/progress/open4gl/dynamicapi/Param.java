// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class Param
{
    Object value;
    int mode;
    int proDataType;
    boolean isExtent;
    int extentValue;
    boolean mappedTempTable;
    protected boolean xmlValue;
    protected int flags;
    
    public Param() {
        this.mappedTempTable = false;
        this.xmlValue = false;
        this.flags = 0;
    }
    
    public Param(final Param param) {
        this.mappedTempTable = false;
        this.xmlValue = false;
        this.flags = 0;
        this.extentValue = param.extentValue;
        this.isExtent = param.isExtent;
        this.flags = param.flags;
        this.mappedTempTable = param.mappedTempTable;
        this.mode = param.mode;
        this.proDataType = param.proDataType;
        this.value = param.value;
        this.xmlValue = param.xmlValue;
    }
    
    public boolean isDataGraph() {
        return (this.proDataType == 36 || this.proDataType == 37) && !this.isXmlValue();
    }
    
    public void setXmlValue(final boolean xmlValue) {
        if (xmlValue) {
            if (this.proDataType != 36 && this.proDataType != 37) {
                throw new IllegalArgumentException("Cannot represent a parameter as XML unless it is a DATASET or DATASET HANDLE.");
            }
            if (this.mappedTempTable) {
                throw new IllegalArgumentException("Cannot represent a parameter as XML if it is a TEMP-TABLE.");
            }
        }
        this.xmlValue = xmlValue;
    }
    
    public boolean isXmlValue() {
        return this.xmlValue;
    }
    
    public void isRpcStyleSoapMessage(final boolean b) {
        if (b) {
            this.flags |= 0x80;
        }
        else {
            this.flags &= 0xFFFFFF7F;
        }
    }
    
    public void setWriteXmlBeforeImage(final boolean b) {
        if (b) {
            this.flags |= 0x40;
        }
        else {
            this.flags &= 0xFFFFFFBF;
        }
    }
}
