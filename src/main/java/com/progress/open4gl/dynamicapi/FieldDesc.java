// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Parameter;
import java.io.Serializable;

class FieldDesc implements Serializable
{
    private String name;
    private int extents;
    private int type;
    private int userOrder;
    private int xmlMapping;
    private int flag;
    private int numColumns;
    
    FieldDesc(final String s, final int n, final int n2, final int n3) {
        this.init(s, n, n2, n3, 0, 0, 0);
    }
    
    FieldDesc(final String s, final int n, final int n2, final int n3, final int n4, final int n5) {
        this.init(s, n, n2, n3, n4, n5, 0);
    }
    
    FieldDesc(final String s, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.init(s, n, n2, n3, n4, n5, n6);
    }
    
    private void init(final String name, final int extents, final int type, final int numColumns, final int userOrder, final int xmlMapping, final int flag) {
        this.name = name;
        this.extents = extents;
        this.type = type;
        this.numColumns = numColumns;
        this.userOrder = userOrder;
        this.xmlMapping = xmlMapping;
        this.flag = flag;
    }
    
    void print(final Tracer tracer) {
        final String string = "Field " + this.name;
        String s = Parameter.proToName(this.type);
        if (this.extents > 0) {
            s = s + "[" + new Integer(this.extents).toString() + "]";
        }
        tracer.print("      " + string + ",  " + s + ",  " + ("Java type " + Parameter.proToJavaClass(this.type)) + ",  " + ("Column " + new Integer(this.numColumns + 1).toString()));
    }
    
    int getNumColumns() {
        return this.numColumns;
    }
    
    int getExtent() {
        return this.extents;
    }
    
    String getName() {
        return this.name;
    }
    
    String getTypeName() {
        return Parameter.proToName(this.type);
    }
    
    String getJavaType() {
        return Parameter.proToJavaClass(this.type);
    }
    
    int getSqlType() {
        return Parameter.proToSql(this.type);
    }
    
    int getType() {
        return this.type;
    }
    
    public int getUserOrder() {
        return this.userOrder;
    }
    
    public int getXMLMapping() {
        return this.xmlMapping;
    }
    
    public int getFlag() {
        return this.flag;
    }
}
