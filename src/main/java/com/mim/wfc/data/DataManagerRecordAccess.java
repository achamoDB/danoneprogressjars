// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

public class DataManagerRecordAccess
{
    public DataManager manager;
    
    public int addField(final String str, final int \u00f1, final boolean \u00f2) {
        final int indexAdd = this.manager.findIndexAdd(str);
        final DataManagerField dataManagerField = this.manager.getFields()[indexAdd];
        final int \u00f12 = dataManagerField.\u00f1;
        if (\u00f12 != 0 && \u00f12 != \u00f1) {
            throw new DataException(12, "Field type of manager field " + str + " does not match type of record access class");
        }
        dataManagerField.\u00f1 = \u00f1;
        dataManagerField.\u00f2 = \u00f2;
        dataManagerField.\u00f3 = true;
        dataManagerField.\u00f4 = false;
        return indexAdd;
    }
    
    public DataManagerRecordAccess(final DataManager manager, final String str) {
        this.manager = manager;
        final String tableName = manager.getTableName();
        if (tableName == null) {
            manager.setTableName(str);
            return;
        }
        if (!tableName.equals(str)) {
            throw new DataException(11, "Manager table " + tableName + " does not match record access table " + str);
        }
    }
    
    public void clear() {
        this.manager.clearManager();
    }
}
