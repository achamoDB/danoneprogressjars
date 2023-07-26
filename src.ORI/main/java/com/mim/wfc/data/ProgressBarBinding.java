// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ProgressBar;
import com.ms.wfc.ui.Control;

class ProgressBarBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final ProgressBar progressBar = (ProgressBar)control;
        if (b) {
            int value;
            if (dataManagerField.isNull()) {
                value = progressBar.getMinimum();
            }
            else {
                value = dataManagerField.getInt();
            }
            progressBar.setValue(value);
            return;
        }
        dataManagerField.setInt(progressBar.getValue());
    }
}
