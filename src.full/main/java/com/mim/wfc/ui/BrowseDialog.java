// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IComponentSite;
import com.mim.wfc.shell.ShellBrowser;
import com.ms.wfc.ui.Control;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.Component;

public class BrowseDialog extends Component
{
    private String \u02e3;
    private String \u02e4;
    
    public BrowseDialog() {
        _cls753A._mth821F();
    }
    
    public String browse(final Control control) {
        return new ShellBrowser(this.\u02e3, this.\u02e4).browse(control);
    }
    
    public void setTitle(final String \u02e3) {
        this.\u02e3 = \u02e3;
    }
    
    public String getTitle() {
        return this.\u02e3;
    }
    
    public void setComponentSite(final IComponentSite componentSite) {
        super.setComponentSite(componentSite);
        _cls753A._mth563B(this);
    }
    
    public void setStartDirectory(final String \u02e4) {
        this.\u02e4 = \u02e4;
    }
    
    public String getStartDirectory() {
        return this.\u02e4;
    }
}
