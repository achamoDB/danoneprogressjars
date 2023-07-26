// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.RunTimeProperties;
import java.io.InputStream;

class ErrorMessageReader
{
    private InputStream stream;
    private ResultSet errorSet;
    private int condition;
    private int errorNum;
    private int flags;
    private int unused;
    private String errorString;
    private String returnString;
    private boolean noRet;
    private Tracer tracer;
    
    ErrorMessageReader(final InputStream stream) throws ClientException {
        this.tracer = RunTimeProperties.tracer;
        this.stream = stream;
        this.errorSet = new ResultSet(ErrorMetaData.metaData, new StreamReader(this.stream, 9));
    }
    
    Open4GLException get4GLError() {
        try {
            if (!this.errorSet.next()) {
                throw new ClientException(2, 19L, null);
            }
            this.condition = this.errorSet.getInt(1);
            this.errorNum = this.errorSet.getInt(2);
            this.flags = this.errorSet.getInt(3);
            this.unused = this.errorSet.getInt(4);
            this.errorString = this.errorSet.getString(5);
            this.returnString = this.errorSet.getString(6);
            this.noRet = this.errorSet.noReturnValue();
            if (this.errorSet.next()) {
                throw new ClientException(2, 20L, null);
            }
            return RunTime4GLException.createException(this.condition, this.errorNum, this.errorString, this.returnString, this.noRet);
        }
        catch (ProSQLException ex) {
            return ex.getProException();
        }
        catch (ClientException ex2) {
            return ExceptionConverter.convertToSystemErrorException(ex2);
        }
        catch (Throwable t) {
            this.tracer.print(t, 0);
            return new Open4GLException();
        }
    }
}
