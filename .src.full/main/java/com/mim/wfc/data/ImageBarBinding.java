// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.mim.wfc.ui.ToolboxItem;
import com.mim.wfc.ui.Toolbox;
import com.ms.wfc.ui.Control;

class ImageBarBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final Toolbox toolbox = (Toolbox)control;
        int \u00f1 = dataManagerField.\u00f1;
        if (\u00f1 == 0) {
            switch (toolbox.getSelectionMode()) {
                case 3: {
                    \u00f1 = 8209;
                    break;
                }
                case 1:
                case 2: {
                    \u00f1 = 3;
                    break;
                }
                default: {
                    throw new DataException(6, "Data conversion failed on field " + ((dataManagerField.\u00ee == null) ? "???" : dataManagerField.\u00ee) + ": A no-selection image bar control cannot be bound to a database field");
                }
            }
        }
        if (b) {
            toolbox.setSelectedItem(null, false);
            if ((\u00f1 & 0x2000) != 0x0) {
                final byte[] bytes = dataManagerField.getBytes();
                for (int i = 0; i < bytes.length; ++i) {
                    if (bytes[i] != 0) {
                        toolbox.setSelectedIndex(bytes[i] - 1, true);
                    }
                }
                return;
            }
            toolbox.setSelectedIndex(dataManagerField.getInt(), true);
        }
        else if ((\u00f1 & 0x2000) != 0x0) {
            final int[] selectedIndices = toolbox.getSelectedIndices();
            if (selectedIndices != null) {
                final byte[] bytes2 = new byte[selectedIndices.length];
                for (int j = 0; j < selectedIndices.length; ++j) {
                    bytes2[j] = (byte)(selectedIndices[j] + 1);
                }
                dataManagerField.setBytes(bytes2);
                return;
            }
            dataManagerField.setNull();
        }
        else {
            if (toolbox.getSelectedIndex() < 0) {
                dataManagerField.setNull();
                return;
            }
            dataManagerField.setInt(toolbox.getSelectedIndex());
        }
    }
}
