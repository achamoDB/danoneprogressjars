// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ScrollBar;
import com.ms.wfc.ui.Control;

class ScrollBarBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final ScrollBar scrollBar = (ScrollBar)control;
        if (b) {
            int value;
            if (dataManagerField.isNull()) {
                value = scrollBar.getMinimum();
            }
            else {
                value = dataManagerField.getInt();
            }
            scrollBar.setValue(value);
            return;
        }
        dataManagerField.setInt(scrollBar.getValue());
    }
}
