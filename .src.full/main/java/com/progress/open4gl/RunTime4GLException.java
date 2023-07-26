// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class RunTime4GLException extends Open4GLException
{
    public static final int NO_CONDITION = 0;
    public static final int ERROR_CONDITION = 1;
    public static final int STOP_CONDITION = 2;
    public static final int QUIT_CONDITION = 3;
    private int proErrorNum;
    private boolean noRetString;
    private String returnString;
    
    public RunTime4GLException(final long n, final Object[] array, final int proErrorNum) {
        super(n, array);
        this.noRetString = true;
        this.proErrorNum = proErrorNum;
    }
    
    public static RunTime4GLException createException(final int n, final int n2, final String s, final String procReturnString, final boolean b) {
        final Object[] array = { null };
        if (s == null) {
            array[0] = new Open4GLException(70L, null).getMessage();
        }
        else {
            array[0] = s;
        }
        RunTime4GLException ex = null;
        switch (n) {
            case 1: {
                ex = new RunTime4GLErrorException(38L, array, n2);
                break;
            }
            case 2: {
                ex = new RunTime4GLStopException(68L, array, n2);
                break;
            }
            case 3: {
                ex = new RunTime4GLQuitException(69L, null, n2);
                break;
            }
            default: {
                ex = new RunTime4GLException(38L, array, n2);
                break;
            }
        }
        if (!b) {
            ex.setProcReturnString(procReturnString);
        }
        return ex;
    }
    
    public void setProcReturnString(final String returnString) {
        this.noRetString = false;
        this.returnString = returnString;
    }
    
    public boolean hasProcReturnString() {
        return !this.noRetString;
    }
    
    public String getProcReturnString() {
        return this.returnString;
    }
}
