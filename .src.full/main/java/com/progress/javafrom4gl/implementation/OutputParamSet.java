// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.InputResultSet;

public class OutputParamSet extends InputResultSet
{
    private FGLParameters parameters;
    private int numParam;
    private int currentRow;
    
    OutputParamSet(final FGLParameters parameters) {
        this.parameters = parameters;
        this.numParam = parameters.getNum4GLParameters();
        this.currentRow = 0;
    }
    
    public boolean next() {
        return ++this.currentRow - 3 <= this.numParam;
    }
    
    public Object getObject(final int n) {
        return this.getObject0(n);
    }
    
    private Object getObject0(final int n) {
        switch (this.currentRow) {
            case 1: {
                switch (n) {
                    case 1: {
                        return new Integer(4);
                    }
                    case 2: {
                        return new Integer(0);
                    }
                    case 3: {
                        return new Integer(0);
                    }
                    default: {
                        throw new Error();
                    }
                }
                break;
            }
            case 2: {
                switch (n) {
                    case 1: {
                        return new Integer(4);
                    }
                    case 2: {
                        return new Integer(0);
                    }
                    case 3: {
                        return new Integer(0);
                    }
                    default: {
                        throw new Error();
                    }
                }
                break;
            }
            case 3: {
                switch (n) {
                    case 1: {
                        return new Integer(1);
                    }
                    case 2: {
                        return null;
                    }
                    case 3: {
                        return null;
                    }
                    default: {
                        throw new Error();
                    }
                }
                break;
            }
            default: {
                switch (n) {
                    case 1: {
                        return new Integer(this.parameters.getOutputProType(this.currentRow - 3));
                    }
                    case 2: {
                        return this.parameters.getOutputValue(this.currentRow - 3);
                    }
                    case 3: {
                        return new Integer(this.parameters.getOutputInOut(this.currentRow - 3));
                    }
                    default: {
                        throw new Error();
                    }
                }
                break;
            }
        }
    }
}
