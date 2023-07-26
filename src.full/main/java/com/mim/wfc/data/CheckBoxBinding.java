// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.CheckBox;
import com.ms.wfc.ui.Control;

class CheckBoxBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final CheckBox checkBox = (CheckBox)control;
        final int \u00f1 = dataManagerField.\u00f1;
        if (b) {
            if (dataManagerField.isNull()) {
                checkBox.setCheckState(checkBox.getThreeState() ? 2 : 0);
                return;
            }
            if (\u00f1 == 11) {
                checkBox.setChecked(dataManagerField.getBoolean());
                return;
            }
            final int int1 = dataManagerField.getInt();
            if (int1 == 0) {
                checkBox.setChecked(false);
                return;
            }
            if (checkBox.getThreeState()) {
                checkBox.setCheckState((int1 == -1) ? 2 : 1);
                return;
            }
            checkBox.setChecked(true);
        }
        else {
            final int checkState = checkBox.getCheckState();
            if (\u00f1 == 11) {
                switch (checkState) {
                    case 1: {
                        dataManagerField.setBoolean(true);
                    }
                    case 0: {
                        dataManagerField.setBoolean(false);
                    }
                    default: {
                        dataManagerField.setNull();
                    }
                }
            }
            else {
                switch (checkState) {
                    case 1: {
                        dataManagerField.setInt(1);
                    }
                    case 0: {
                        dataManagerField.setInt(0);
                    }
                    default: {
                        dataManagerField.setNull();
                    }
                }
            }
        }
    }
}
