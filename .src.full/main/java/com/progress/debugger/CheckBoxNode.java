// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

public class CheckBoxNode
{
    String text;
    boolean selected;
    
    public CheckBoxNode(final String text, final boolean selected) {
        this.text = text;
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public String toString() {
        return this.getClass().getName() + "[" + this.text + "/" + this.selected + "]";
    }
}
