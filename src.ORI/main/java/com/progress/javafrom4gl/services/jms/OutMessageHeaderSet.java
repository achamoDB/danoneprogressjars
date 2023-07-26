// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import java.util.Vector;

public class OutMessageHeaderSet extends OutMessageBodySet
{
    public static final int MSG_ID = 1;
    public static final int PART_ID = 2;
    public static final int ITEM_NAME = 3;
    public static final int ITEM_DATA_TYPE = 4;
    public static final int VALUE = 5;
    
    void addRow(final Integer obj, final Integer obj2, final String obj3, final Integer obj4, final String obj5) {
        final Vector<Integer> obj6 = new Vector<Integer>();
        obj6.addElement(obj);
        obj6.addElement(obj2);
        obj6.addElement((Integer)obj3);
        obj6.addElement(obj4);
        obj6.addElement((Integer)obj5);
        super.rows.addElement(obj6);
        ++super.numRows;
    }
    
    void addRow(final Integer obj, final Integer obj2, final String obj3, final Integer obj4, final byte[] obj5) {
        final Vector<Integer> obj6 = new Vector<Integer>();
        obj6.addElement(obj);
        obj6.addElement(obj2);
        obj6.addElement((Integer)obj3);
        obj6.addElement(obj4);
        obj6.addElement((Integer)(Object)obj5);
        super.rows.addElement(obj6);
        ++super.numRows;
    }
    
    OutMessageHeaderSet() {
    }
}
