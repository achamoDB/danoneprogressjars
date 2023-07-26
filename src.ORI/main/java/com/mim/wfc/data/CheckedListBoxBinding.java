// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ListBox;
import com.ms.wfc.ui.CheckedListBox;
import com.ms.wfc.ui.Control;

class CheckedListBoxBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final CheckedListBox checkedListBox = (CheckedListBox)control;
        if (b) {
            final byte[] bytes = dataManagerField.getBytes();
            final int itemCount = ((ListBox)checkedListBox).getItemCount();
            for (byte b2 = 0; b2 < itemCount; ++b2) {
                boolean b3 = false;
                if (bytes != null) {
                    for (int i = 0; i < bytes.length; ++i) {
                        if (bytes[i] == b2) {
                            b3 = true;
                        }
                    }
                }
                checkedListBox.setItemCheck((int)b2, b3);
            }
            return;
        }
        final int[] checkedIndices = checkedListBox.getCheckedIndices();
        if (checkedIndices != null) {
            final byte[] bytes2 = new byte[checkedIndices.length];
            for (int j = 0; j < checkedIndices.length; ++j) {
                bytes2[j] = (byte)checkedIndices[j];
            }
            dataManagerField.setBytes(bytes2);
            return;
        }
        dataManagerField.setNull();
    }
}
