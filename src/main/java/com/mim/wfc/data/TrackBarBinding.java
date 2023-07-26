// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.TrackBar;
import com.ms.wfc.ui.Control;

class TrackBarBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final TrackBar trackBar = (TrackBar)control;
        if (b) {
            int value;
            if (dataManagerField.isNull()) {
                value = trackBar.getMinimum();
            }
            else {
                value = dataManagerField.getInt();
            }
            trackBar.setValue(value);
            return;
        }
        dataManagerField.setInt(trackBar.getValue());
    }
}
