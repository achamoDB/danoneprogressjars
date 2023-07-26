// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.ListBox;
import com.ms.wfc.ui.ComboBox;
import com.ms.wfc.data.Field;
import com.ms.wfc.data.Fields;
import java.util.Vector;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.data.Recordset;

public class StringIdTable
{
    private String[] \u00f9;
    private int \u00fa;
    
    public void set(final Recordset recordset) {
        _cls753A._mth821F();
        final Vector vector = new Vector<String>();
        recordset.moveFirst();
        while (!recordset.getEOF()) {
            final Fields fields = recordset.getFields();
            if (fields == null) {
                break;
            }
            final Field item = fields.getItem(0);
            if (item == null) {
                continue;
            }
            final int \u00ee = DataManagerField.\u00ee(item.getType());
            if (\u00ee == 0 || \u00ee == 1) {
                continue;
            }
            vector.addElement(item.getValue().toString());
            recordset.moveNext();
        }
        final int size = vector.size();
        if (size > 0) {
            this.\u00f9 = new String[size];
            for (int i = 0; i < size; ++i) {
                this.\u00f9[i] = vector.elementAt(i);
            }
        }
    }
    
    public void set(final Recordset recordset, final int \u00fa) {
        this.set(recordset);
        this.\u00fa = \u00fa;
    }
    
    public void set(final String[] \u00f9) {
        this.\u00f9 = \u00f9;
    }
    
    public void set(final String[] array, final int \u00fa) {
        this.set(array);
        this.\u00fa = \u00fa;
    }
    
    public void fillComboBox(final ComboBox comboBox) {
        comboBox.setItems((Object[])this.\u00f9);
    }
    
    public StringIdTable(final Recordset recordset) {
        _cls753A._mth821F();
        this.set(recordset);
    }
    
    public StringIdTable(final Recordset recordset, final int n) {
        _cls753A._mth821F();
        this.set(recordset, n);
    }
    
    public StringIdTable(final String[] array) {
        _cls753A._mth821F();
        this.set(array);
    }
    
    public StringIdTable(final String[] array, final int n) {
        _cls753A._mth821F();
        this.set(array, n);
    }
    
    public void showIdInComboBox(final ComboBox comboBox, final int n) {
        final String id2Str = this.id2Str(n);
        if (id2Str != null) {
            comboBox.setSelectedItem((Object)id2Str);
            return;
        }
        comboBox.setSelectedIndex(-1);
    }
    
    public int getIdFromListBox(final ListBox listBox) {
        final Object selectedItem = listBox.getSelectedItem();
        if (selectedItem != null) {
            return this.str2Id((String)selectedItem);
        }
        return -1;
    }
    
    public int str2Id(final String anObject) {
        if (this.\u00f9 != null && anObject != null) {
            for (int i = 0; i < this.\u00f9.length; ++i) {
                if (this.\u00f9[i].equals(anObject)) {
                    return i + this.\u00fa;
                }
            }
        }
        return -1;
    }
    
    public String id2Str(int n) {
        if (this.\u00f9 != null && n >= this.\u00fa) {
            n -= this.\u00fa;
            if (n >= 0 && n < this.\u00f9.length) {
                return this.\u00f9[n];
            }
        }
        return null;
    }
    
    public int getIdFromComboBox(final ComboBox comboBox) {
        final String s = (String)comboBox.getSelectedItem();
        if (s != null) {
            return this.str2Id(s);
        }
        return -1;
    }
    
    public void showIdsInListBox(final ListBox listBox, final byte[] array) {
        final Object[] items = listBox.getItems();
        if (items != null && this.\u00f9 != null) {
            for (int i = 0; i < items.length; ++i) {
                final int str2Id = this.str2Id((String)items[i]);
                boolean b = false;
                if (array != null) {
                    for (int j = 0; j < array.length; ++j) {
                        if (array[j] == str2Id) {
                            b = true;
                            break;
                        }
                    }
                }
                listBox.setSelected(i, b);
            }
        }
    }
    
    public void fillListBox(final ListBox listBox) {
        listBox.setItems((Object[])this.\u00f9);
    }
    
    public byte[] getIdsFromListBox(final ListBox listBox) {
        final Object[] selectedItems = listBox.getSelectedItems();
        if (selectedItems != null && this.\u00f9 != null) {
            final byte[] array = new byte[selectedItems.length];
            for (int i = 0; i < selectedItems.length; ++i) {
                array[i] = (byte)this.str2Id((String)selectedItems[i]);
            }
            return array;
        }
        return null;
    }
    
    public void showIdInListBox(final ListBox listBox, final int n) {
        final Object[] items = listBox.getItems();
        if (items != null && this.\u00f9 != null) {
            final String id2Str = this.id2Str(n);
            if (id2Str != null) {
                for (int i = 0; i < items.length; ++i) {
                    if (id2Str.equals(items[i])) {
                        listBox.setSelected(i, true);
                        return;
                    }
                }
            }
        }
    }
    
    public String[] getStrings() {
        return this.\u00f9;
    }
}
