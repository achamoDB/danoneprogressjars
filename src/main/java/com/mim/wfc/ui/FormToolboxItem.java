// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.WFCException;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.Form;

public class FormToolboxItem extends ToolboxItem
{
    private String \u018d;
    private Form \u018e;
    
    private void \u018d() {
        if (this.\u018d == null) {
            return;
        }
        try {
            this.\u018e = (Form)Class.forName(this.\u018d).newInstance();
            super.setText(((Control)this.\u018e).getText());
            super.setImage((Image)this.\u018e.getIcon());
            this.\u018e.addOnClosed(new EventHandler((Object)this, "_formClosed"));
        }
        catch (InstantiationException ex) {
            WFCException.rethrowException((Throwable)ex);
        }
        catch (IllegalAccessException ex2) {
            WFCException.rethrowException((Throwable)ex2);
        }
        catch (ClassNotFoundException ex3) {
            WFCException.rethrowException((Throwable)ex3);
        }
        catch (ClassFormatError classFormatError) {
            WFCException.rethrowException((Throwable)classFormatError);
        }
    }
    
    protected void _formClosed(final Object o, final Event event) {
        this.\u018e = null;
        this.setSelected(false);
    }
    
    public FormToolboxItem() {
        this.\u018d = null;
        this.\u018e = null;
    }
    
    public FormToolboxItem(final Form form) {
        this();
        this.setForm(form);
    }
    
    public FormToolboxItem(final String formClassName) {
        this();
        this.setFormClassName(formClassName);
    }
    
    public Form getFormNoCreate() {
        return this.\u018e;
    }
    
    public void setForm(final Form \u01dd) {
        this.\u018e = \u01dd;
    }
    
    public Form getForm() {
        if (this.\u018e == null) {
            this.\u018d();
        }
        return this.\u018e;
    }
    
    public void setFormClassName(final String \u018d) {
        this.\u018d = \u018d;
        this.\u018e = null;
    }
    
    public Image getImage() {
        if (super.getImage() == null) {
            this.\u018d();
        }
        return super.getImage();
    }
    
    public String getFormClassName() {
        return this.\u018d;
    }
    
    public String getText() {
        if (super.getText() == null) {
            this.\u018d();
        }
        return super.getText();
    }
}
