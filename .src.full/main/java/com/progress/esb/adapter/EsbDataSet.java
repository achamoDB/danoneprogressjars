// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.util.log.XQLogImpl;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGParam;
import com.sonicsw.xq.XQLog;

public class EsbDataSet
{
    private static XQLog m_log;
    
    public static String serializeParameter(final Object o, final PGParam pgParam) throws ScriptEngineException {
        final int paramType = pgParam.getParamType();
        pgParam.getExtent();
        pgParam.getParamName();
        if (paramType != 36 && paramType != 37) {
            throw new ScriptEngineException("Incorrect parameter type - Expected dataset, got " + Parameter.proToName(paramType), 0);
        }
        if (pgParam.isExtentField()) {
            throw new ScriptEngineException("Incorrect parameter type - Expected simple value, got array", 0);
        }
        return o.toString();
    }
    
    public static Object parseParameter(final String s, final PGParam pgParam) throws ScriptEngineException {
        final int paramType = pgParam.getParamType();
        pgParam.getExtent();
        if (paramType != 36 && paramType != 37) {
            throw new ScriptEngineException("Incorrect parameter type - Expected dataset, got " + Parameter.proToName(paramType), 0);
        }
        return s;
    }
    
    static {
        EsbDataSet.m_log = XQLogImpl.getCategoryLog(64);
    }
}
