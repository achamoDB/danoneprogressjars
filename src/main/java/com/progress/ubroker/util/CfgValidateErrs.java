// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.io.Serializable;

public class CfgValidateErrs implements Serializable
{
    public static final int GET_PROP_LIST = 1;
    public static final int GET_MSG_LIST = 2;
    public static final int NOT_VALIDATED = 0;
    public static final int HAVE_INVALID_PROPERTIES = 1;
    public static final int VALIDATED = 2;
    public static final String INSTANCE_NAME = "instName";
    public static final String NAME_NOT_UNIQUE = "New instance name is not unique.";
    public int m_validated;
    Vector m_cfgErrors;
    Hashtable m_envVarErrs;
    
    public CfgValidateErrs() {
        this.m_validated = 0;
        this.m_cfgErrors = new Vector();
        this.m_envVarErrs = new Hashtable();
        this.m_validated = 0;
    }
    
    public void add(final String s, final String s2) {
        this.m_cfgErrors.addElement(new ValidateErrObj(s, s2));
        this.m_validated = 1;
    }
    
    public void add(final String s, final String s2, final long n) {
        this.m_cfgErrors.addElement(new ValidateErrObj(s, s2, n));
        this.m_validated = 1;
    }
    
    public void addEnvVarErrs(final String key, final String value) {
        this.m_envVarErrs.put(key, value);
        this.m_validated = 1;
    }
    
    public int size() {
        return this.m_cfgErrors.size();
    }
    
    public int envVarErrsSize() {
        return this.m_envVarErrs.size();
    }
    
    public String[] getInvalidProplist() {
        String[] array = null;
        if (this.size() > 0) {
            array = new String[this.m_cfgErrors.size()];
            final Enumeration<ValidateErrObj> elements = this.m_cfgErrors.elements();
            int n = 0;
            while (elements.hasMoreElements()) {
                array[n++] = elements.nextElement().getPropName();
            }
        }
        return array;
    }
    
    public String[] getErrMsgList() {
        String[] array = null;
        if (this.size() > 0) {
            array = new String[this.m_cfgErrors.size()];
            final Enumeration<ValidateErrObj> elements = this.m_cfgErrors.elements();
            int n = 0;
            while (elements.hasMoreElements()) {
                array[n++] = elements.nextElement().getDetailErrMsg();
            }
        }
        return array;
    }
    
    public Integer[] getErrCodeList() {
        Integer[] array = null;
        if (this.size() > 0) {
            array = new Integer[this.m_cfgErrors.size()];
            final Enumeration<ValidateErrObj> elements = this.m_cfgErrors.elements();
            int n = 0;
            while (elements.hasMoreElements()) {
                array[n++] = new Integer(elements.nextElement().getErrCode());
            }
        }
        return array;
    }
    
    public String[] getBadEnvVarPropList() {
        return this.getKeyOrElementlist(1, this.m_envVarErrs);
    }
    
    public String[] getBadEnvVarMsgList() {
        return this.getKeyOrElementlist(2, this.m_envVarErrs);
    }
    
    public boolean isEmpty() {
        return this.m_cfgErrors.size() == 0 && this.m_envVarErrs.size() == 0;
    }
    
    public boolean isEnvVarErrsEmpty() {
        return this.m_envVarErrs.size() == 0;
    }
    
    public void setValidated(final int validated) {
        this.m_validated = validated;
    }
    
    public void didValidation() {
        this.setValidated(2);
    }
    
    public boolean isCfgValidated() {
        return this.m_validated == 2 || this.foundInvalidProp();
    }
    
    public boolean foundInvalidProp() {
        return this.m_validated == 1;
    }
    
    public void setDupNameError() {
        this.add("instName", "New instance name is not unique.");
    }
    
    private String[] getKeyOrElementlist(final int n, final Hashtable hashtable) {
        final int size = hashtable.size();
        if (size > 0) {
            Enumeration<String> enumeration;
            if (n == 1) {
                enumeration = hashtable.keys();
            }
            else {
                enumeration = hashtable.elements();
            }
            final String[] array = new String[size];
            int n2 = 0;
            while (enumeration.hasMoreElements()) {
                array[n2++] = enumeration.nextElement();
            }
            return array;
        }
        return null;
    }
    
    class ValidateErrObj implements Serializable
    {
        String badPropertyName;
        String detailErrMsg;
        long proErrCode;
        
        ValidateErrObj(final String badPropertyName, final String detailErrMsg) {
            this.badPropertyName = null;
            this.detailErrMsg = null;
            this.proErrCode = 0L;
            this.badPropertyName = badPropertyName;
            this.detailErrMsg = detailErrMsg;
        }
        
        ValidateErrObj(final String badPropertyName, final String detailErrMsg, final long proErrCode) {
            this.badPropertyName = null;
            this.detailErrMsg = null;
            this.proErrCode = 0L;
            this.badPropertyName = badPropertyName;
            this.detailErrMsg = detailErrMsg;
            this.proErrCode = proErrCode;
        }
        
        public String getPropName() {
            return this.badPropertyName;
        }
        
        public String getDetailErrMsg() {
            return this.detailErrMsg;
        }
        
        public int getErrCode() {
            return new Long(this.proErrCode).intValue();
        }
    }
}
