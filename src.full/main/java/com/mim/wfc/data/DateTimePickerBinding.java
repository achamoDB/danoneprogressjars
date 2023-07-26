// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.DateTimePicker;
import com.ms.wfc.ui.Control;

class DateTimePickerBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final DateTimePicker dateTimePicker = (DateTimePicker)control;
        if (b) {
            dateTimePicker.setValue(dataManagerField.getDate());
            return;
        }
        dataManagerField.setDate(dateTimePicker.getValue());
    }
}
