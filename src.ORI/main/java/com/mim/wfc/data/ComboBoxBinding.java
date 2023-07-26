// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ComboBox;
import com.ms.wfc.ui.Control;

class ComboBoxBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final ComboBox comboBox = (ComboBox)control;
        final int \u00f1 = dataManagerField.\u00f1;
        if (b) {
            if (\u00f1 == 8) {
                ((Control)comboBox).setText(dataManagerField.getString());
                return;
            }
            comboBox.setSelectedIndex(dataManagerField.getInt());
        }
        else if (\u00f1 == 8) {
            final String text = ((Control)comboBox).getText();
            if (text != null && text.length() != 0) {
                dataManagerField.setString(text);
                return;
            }
            dataManagerField.setNull();
        }
        else {
            final int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                dataManagerField.setInt(selectedIndex);
                return;
            }
            dataManagerField.setNull();
        }
    }
}
