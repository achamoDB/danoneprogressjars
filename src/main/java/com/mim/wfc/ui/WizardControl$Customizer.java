// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Form;
import com.ms.wfc.core.VerbExecuteEventHandler;
import com.ms.wfc.core.CustomizerVerb;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.Customizer;

public class WizardControl$Customizer extends Customizer
{
    final WizardControl \u0190;
    
    public WizardControl$Customizer(final WizardControl \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public boolean getHitTest(final Point point) {
        if ((Control.getMouseButtons() & 0x600000) != 0x0) {
            return false;
        }
        final Point pointToScreen = ((Form)this.\u0190).pointToScreen(point);
        return this.\u0190.\u02b7.rectToScreen(this.\u0190.\u02b7.getClientRect()).contains(pointToScreen) || this.\u0190.\u02b8.rectToScreen(this.\u0190.\u02b8.getClientRect()).contains(pointToScreen);
    }
    
    public CustomizerVerb[] getVerbs() {
        return new CustomizerVerb[] { new CustomizerVerb((Object)"Add page", new VerbExecuteEventHandler((Object)this, "onAddPage")), new CustomizerVerb((Object)"Remove page", new VerbExecuteEventHandler((Object)this, "onRemovePage")) };
    }
}
