// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import java.io.Serializable;
import com.ms.wfc.ui.Control;
import com.ms.wfc.util.HashTable;

public class DataManagerBinder
{
    HashTable \u00ed;
    
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        if (!b && !dataManagerField.\u00f2) {
            return;
        }
        DataManagerBinding dataManagerBinding = this.getBinding(dataManagerField.\u00ee);
        if (dataManagerBinding == null) {
            for (Serializable s = control.getClass(); s != null; s = ((Class<? extends Control>)s).getSuperclass()) {
                dataManagerBinding = this.getBinding(((Class)s).getName());
                if (dataManagerBinding != null) {
                    break;
                }
            }
        }
        if (dataManagerBinding != null) {
            dataManagerBinding.updateData(control, dataManagerField, b);
            return;
        }
        if (b) {
            control.setText(dataManagerField.getString());
            return;
        }
        final String text = control.getText();
        if (text != null && text.length() != 0) {
            dataManagerField.setString(text);
            return;
        }
        dataManagerField.setNull();
    }
    
    public void addBinding(final String s, final DataManagerBinding dataManagerBinding) {
        this.\u00ed.setValue((Object)s, (Object)dataManagerBinding);
    }
    
    DataManagerBinder() {
        this.\u00ed = new HashTable();
        this.addBinding("com.ms.wfc.ui.CheckBox", new CheckBoxBinding());
        this.addBinding("com.ms.wfc.ui.RadioButton", new RadioButtonBinding());
        this.addBinding("com.ms.wfc.ui.ComboBox", new ComboBoxBinding());
        this.addBinding("com.ms.wfc.ui.ListBox", new ListBoxBinding());
        this.addBinding("com.ms.wfc.ui.CheckedListBox", new CheckedListBoxBinding());
        this.addBinding("com.ms.wfc.ui.PictureBox", new PictureBoxBinding());
        this.addBinding("com.ms.wfc.ui.ScrollBar", new ScrollBarBinding());
        this.addBinding("com.ms.wfc.ui.TrackBar", new TrackBarBinding());
        this.addBinding("com.ms.wfc.ui.ProgressBar", new ProgressBarBinding());
        this.addBinding("com.ms.wfc.ui.DateTimePicker", new DateTimePickerBinding());
        this.addBinding("com.ms.wfc.ui.RichEdit", new RichEditBinding());
        this.addBinding("com.mim.wfc.ui.ColorCheckBox", new ColorCheckBoxBinding());
        this.addBinding("com.mim.wfc.ui.ColorRadioButton", new ColorRadioButtonBinding());
        this.addBinding("com.mim.wfc.ui.NumEdit", new NumEditBinding());
        this.addBinding("com.mim.wfc.ui.ImageBar", new ImageBarBinding());
    }
    
    public DataManagerBinding getBinding(final String s) {
        return (DataManagerBinding)this.\u00ed.getValue((Object)s);
    }
}
