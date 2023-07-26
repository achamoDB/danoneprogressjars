// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import com.progress.wsa.xmr.DateTimeTZHolder;
import com.progress.wsa.xmr.DateTimeHolder;
import com.progress.wsa.xmr.ArrayParamHolder;
import com.progress.wsa.xmr.DateTimeTZArrayHolder;
import com.progress.wsa.xmr.DateTimeArrayHolder;
import java.util.GregorianCalendar;
import com.progress.wsa.admin.MethodParam;

public class ParamHolderDetail
{
    private boolean isHolder;
    private boolean isExtent;
    private int pscType;
    private Object outParam;
    private Class outParamType;
    private Class arrayClass;
    
    public ParamHolderDetail(final MethodParam methodParam, final Object outParam) {
        this.isHolder = true;
        this.isExtent = false;
        this.arrayClass = null;
        this.isExtent = methodParam.isExtent();
        this.pscType = methodParam.pscType();
        this.outParam = outParam;
        this.arrayClass = methodParam.javaType();
        this.createHolder();
    }
    
    public ParamHolderDetail(final int pscType, final Object outParam) {
        this.isHolder = true;
        this.isExtent = false;
        this.arrayClass = null;
        this.pscType = pscType;
        this.outParam = outParam;
        this.createHolder();
    }
    
    private void createHolder() {
        if (this.pscType == 2) {
            this.isHolder = false;
        }
        if (this.isExtent) {
            this.isHolder = true;
        }
        if (this.isExtent) {
            switch (this.pscType) {
                case 34: {
                    this.outParam = new DateTimeArrayHolder((GregorianCalendar[])this.outParam);
                    this.outParamType = DateTimeArrayHolder.class;
                    break;
                }
                case 40: {
                    this.outParam = new DateTimeTZArrayHolder((GregorianCalendar[])this.outParam);
                    this.outParamType = DateTimeTZArrayHolder.class;
                    break;
                }
                default: {
                    this.outParam = new ArrayParamHolder((Object[])this.outParam, this.arrayClass);
                    this.outParamType = ArrayParamHolder.class;
                    break;
                }
            }
        }
        else {
            switch (this.pscType) {
                case 34: {
                    this.outParam = new DateTimeHolder((GregorianCalendar)this.outParam);
                    this.outParamType = DateTimeHolder.class;
                    break;
                }
                case 40: {
                    this.outParam = new DateTimeTZHolder((GregorianCalendar)this.outParam);
                    this.outParamType = DateTimeTZHolder.class;
                    break;
                }
                default: {
                    this.outParamType = GregorianCalendar.class;
                    break;
                }
            }
        }
    }
    
    public boolean hasHolder() {
        return this.isHolder;
    }
    
    public Object getHolder() {
        return this.outParam;
    }
    
    public Class getType() {
        return this.outParamType;
    }
}
