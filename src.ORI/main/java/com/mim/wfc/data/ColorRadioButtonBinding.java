// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.mim.wfc.ui.ColorRadioButton;
import com.ms.wfc.ui.Control;

class ColorRadioButtonBinding implements DataManagerBinding
{
    public void updateData(Control control, final DataManagerField dataManagerField, final boolean b) {
        final Control parent = control.getParent();
        if (b) {
            final int int1 = dataManagerField.getInt();
            int n = 0;
            do {
                if (control instanceof ColorRadioButton) {
                    ((ColorRadioButton)control).setChecked(n == int1);
                    ++n;
                }
                control = parent.getNextControl(control, true);
            } while (control != null);
            return;
        }
        int int2 = -1;
        int n2 = 0;
        do {
            if (control instanceof ColorRadioButton) {
                if (((ColorRadioButton)control).getChecked()) {
                    int2 = n2;
                    break;
                }
                ++n2;
            }
            control = parent.getNextControl(control, true);
        } while (control != null);
        if (int2 >= 0) {
            dataManagerField.setInt(int2);
            return;
        }
        dataManagerField.setNull();
    }
}
