// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.mim.wfc.ui.NumEdit;
import com.ms.wfc.ui.Control;

class NumEditBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final NumEdit numEdit = (NumEdit)control;
        final int \u00f1 = dataManagerField.\u00f1;
        if (b) {
            if (dataManagerField.isNull()) {
                numEdit.setNullValue(true);
                return;
            }
            if (\u00f1 == 6) {
                numEdit.setValue(dataManagerField.getCurrency() / 10000.0);
                return;
            }
            numEdit.setValue(dataManagerField.getDouble());
        }
        else {
            if (numEdit.getNullValue()) {
                dataManagerField.setNull();
                return;
            }
            if (\u00f1 == 6) {
                dataManagerField.setCurrency((long)(numEdit.getValue() * 10000.0));
                return;
            }
            dataManagerField.setDouble(numEdit.getValue());
        }
    }
}
