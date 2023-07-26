// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.VerbExecuteEventHandler;
import com.ms.wfc.core.CustomizerVerb;
import com.ms.wfc.core.Customizer;

public class WizardPage$Customizer extends Customizer
{
    final WizardPage \u0190;
    
    public WizardPage$Customizer(final WizardPage \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public CustomizerVerb[] getVerbs() {
        return new CustomizerVerb[] { new CustomizerVerb((Object)"Add page", new VerbExecuteEventHandler((Object)this, "onAddPage")) };
    }
}
