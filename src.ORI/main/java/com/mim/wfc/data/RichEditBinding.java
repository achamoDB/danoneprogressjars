// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.RichEdit;
import com.ms.wfc.ui.Control;

class RichEditBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final RichEdit richEdit = (RichEdit)control;
        if (b) {
            final String string = dataManagerField.getString();
            richEdit.setRTF((string != null) ? string : "");
            return;
        }
        final String text = ((Control)richEdit).getText();
        if (text != null && text.length() != 0) {
            dataManagerField.setString(richEdit.getRTF());
            return;
        }
        dataManagerField.setNull();
    }
}
