// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.SDOFactory;

public class SubAppObject extends ProObject implements SDOFactory
{
    public SubAppObject(final ProObject proObject) throws Open4GLException {
        super(proObject, "SO");
    }
    
    public Procedure CreatePO(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSession() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new Procedure(this, s, set);
    }
}
