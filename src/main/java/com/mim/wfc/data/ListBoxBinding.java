// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ListBox;
import com.ms.wfc.ui.Control;

class ListBoxBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final ListBox listBox = (ListBox)control;
        int \u00f1 = dataManagerField.\u00f1;
        if (\u00f1 == 0) {
            switch (listBox.getSelectionMode()) {
                case 2:
                case 3: {
                    \u00f1 = 8209;
                    break;
                }
                case 1: {
                    \u00f1 = 3;
                    break;
                }
                default: {
                    throw new DataException(6, "Data conversion failed on field " + ((dataManagerField.\u00ee == null) ? "???" : dataManagerField.\u00ee) + ": A no-selection list box control cannot be bound to a database field");
                }
            }
        }
        if (b) {
            if ((\u00f1 & 0x2000) != 0x0) {
                final byte[] bytes = dataManagerField.getBytes();
                for (int itemCount = listBox.getItemCount(), i = 0; i < itemCount; ++i) {
                    final int n = i + 1;
                    boolean b2 = false;
                    if (bytes != null) {
                        for (int j = 0; j < bytes.length; ++j) {
                            if (bytes[j] == n) {
                                b2 = true;
                            }
                        }
                    }
                    listBox.setSelected(i, b2);
                }
                return;
            }
            if (\u00f1 != 8) {
                listBox.setSelectedIndex(dataManagerField.isNull() ? -1 : dataManagerField.getInt());
                return;
            }
            final String string;
            if (dataManagerField.isNull() || (string = dataManagerField.getString()) == null) {
                listBox.setSelectedIndex(-1);
                return;
            }
            listBox.setSelectedItem((Object)string);
        }
        else if ((\u00f1 & 0x2000) != 0x0) {
            final int[] selectedIndices = listBox.getSelectedIndices();
            if (selectedIndices != null) {
                final byte[] bytes2 = new byte[selectedIndices.length];
                for (int k = 0; k < selectedIndices.length; ++k) {
                    bytes2[k] = (byte)(selectedIndices[k] + 1);
                }
                dataManagerField.setBytes(bytes2);
                return;
            }
            dataManagerField.setNull();
        }
        else {
            if (\u00f1 == 8) {
                dataManagerField.setString((String)listBox.getSelectedItem());
                return;
            }
            if (listBox.getSelectedIndex() < 0) {
                dataManagerField.setNull();
                return;
            }
            dataManagerField.setInt(listBox.getSelectedIndex());
        }
    }
}
