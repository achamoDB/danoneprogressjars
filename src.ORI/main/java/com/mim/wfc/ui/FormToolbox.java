// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

public class FormToolbox extends Toolbox
{
    public FormToolbox() {
        this.setSingleColumn(true);
    }
    
    public void setFormItemList(final FormToolboxItem[] itemList) {
        this.setItemList(itemList);
    }
    
    public FormToolboxItem[] getFormItemList() {
        return (FormToolboxItem[])this.getItemList();
    }
}
