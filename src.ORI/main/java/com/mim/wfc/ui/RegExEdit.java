// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.KeyEvent;
import com.mim.wfc.util.RegExException;
import com.ms.wfc.ui.KeyPressEvent;
import com.ms.wfc.core.Component;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.CancelEvent;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Event;
import com.mim.wfc.util.RegEx;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.ui.Edit;

public class RegExEdit extends Edit
{
    private String \u020e;
    private int \u020f;
    private CancelEventHandler \u01b4;
    private EventHandler \u0210;
    private EventHandler \u0211;
    private RegEx \u0212;
    private static Class \u0213;
    
    private void \u020e() {
        final String text = ((Control)this).getText();
        final int selectionStart = this.getSelectionStart();
        final int selectionEnd = this.getSelectionEnd();
        final int length = text.length();
        String text2;
        int stopIndex;
        if (selectionEnd == selectionStart) {
            if (selectionStart == 0) {
                return;
            }
            text2 = text.substring(0, selectionStart - 1);
            if (selectionStart < length) {
                text2 += text.substring(selectionStart);
            }
            stopIndex = selectionStart - 1;
        }
        else {
            text2 = text.substring(0, selectionStart);
            if (selectionEnd < length) {
                text2 += text.substring(selectionEnd);
            }
            stopIndex = selectionStart;
        }
        if (this.\u0212.match(text2, true)) {
            if (this.\u0212.stoppedAtLiteralNode() && selectionStart == length && this.\u020f == 2) {
                stopIndex = this.\u0212.getStopIndex();
                ((Control)this).setText(text.substring(0, stopIndex));
            }
            else {
                ((Control)this).setText(text2);
            }
            this.select(stopIndex, stopIndex);
            return;
        }
        if (this.\u0212.stoppedAtLiteralNode() && selectionStart == length && this.\u020f == 2) {
            final int stopIndex2 = this.\u0212.getStopIndex();
            ((Control)this).setText(text.substring(0, stopIndex2));
            this.select(stopIndex2, stopIndex2);
            return;
        }
        this.onInvalidCharacter(Event.EMPTY);
    }
    
    public void addOnInvalidCharacter(final EventHandler eventHandler) {
        this.\u0211 = (EventHandler)Delegate.combine((Delegate)this.\u0211, (Delegate)eventHandler);
    }
    
    public void onValidate(final CancelEvent cancelEvent) {
        if (this.\u01b4 != null) {
            this.\u01b4.invoke((Object)this, cancelEvent);
        }
    }
    
    public void removeOnMatchFailed(final EventHandler eventHandler) {
        this.\u0210 = (EventHandler)Delegate.remove((Delegate)this.\u0210, (Delegate)eventHandler);
    }
    
    public String toString() {
        if (this.\u0212 != null) {
            return this.\u0212.toString();
        }
        return "";
    }
    
    public RegExEdit() {
        this.\u020f = 0;
        _cls753A._mth821F();
        ((Control)this).setSize(100, 20);
    }
    
    public void setMode(final int \u020f) {
        if (!RegExEditMode.valid(\u020f)) {
            throw new WFCInvalidEnumException("mode", \u020f, (RegExEdit.\u0213 != null) ? RegExEdit.\u0213 : (RegExEdit.\u0213 = \u00c6("com.mim.wfc.ui.RegExEditMode")));
        }
        this.\u020f = \u020f;
    }
    
    public int getMode() {
        return this.\u020f;
    }
    
    public void removeOnValidate(final CancelEventHandler cancelEventHandler) {
        this.\u01b4 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01b4, (Delegate)cancelEventHandler);
    }
    
    public void addOnMatchFailed(final EventHandler eventHandler) {
        this.\u0210 = (EventHandler)Delegate.combine((Delegate)this.\u0210, (Delegate)eventHandler);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    protected void onLeave(final Event event) {
        if (this.validate()) {
            super.onLeave(event);
        }
    }
    
    private void \u020f(final char c) {
        String text = ((Control)this).getText();
        final int selectionStart = this.getSelectionStart();
        final int selectionEnd = this.getSelectionEnd();
        final int length = text.length();
        if (selectionStart == 0 && selectionEnd == length) {
            text = "";
        }
        String text2 = text.substring(0, selectionStart) + c;
        if (selectionStart == selectionEnd) {
            if (selectionStart < length) {
                text2 += text.substring(selectionStart + 1);
            }
        }
        else if (selectionEnd < length) {
            text2 += text.substring(selectionEnd);
        }
        if (this.\u0212.match(text2, true)) {
            ((Control)this).setText(text2);
            this.select(selectionStart + 1, selectionStart + 1);
            return;
        }
        if ((selectionStart == selectionEnd || selectionStart == 0) && selectionEnd == length && this.\u0212.stoppedAtLiteralNode() && this.\u020f == 2) {
            String string = text + this.\u0212.getStopNodeText();
            if (this.\u0212.match(string, true)) {
                final String string2 = string + c;
                if (this.\u0212.match(string2, true)) {
                    string = string2;
                }
                ((Control)this).setText(string);
                final int length2 = string.length();
                this.select(length2, length2);
            }
        }
        else {
            this.onInvalidCharacter(Event.EMPTY);
        }
    }
    
    public boolean validate() {
        if (this.\u0212 != null) {
            final String text = ((Control)this).getText();
            if (text.length() != 0 && !this.\u0212.match(text, false)) {
                if (this.\u0210 != null) {
                    this.onMatchFailed(Event.EMPTY);
                }
                else {
                    MsgBox.showResource("RegExEdit", "msgFail");
                    if (!((Control)this).getFocused()) {
                        ((Control)this).focus();
                    }
                }
                return false;
            }
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onValidate(cancelEvent);
        return !cancelEvent.cancel;
    }
    
    public void addOnValidate(final CancelEventHandler cancelEventHandler) {
        this.\u01b4 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01b4, (Delegate)cancelEventHandler);
    }
    
    protected void onKeyPress(final KeyPressEvent keyPressEvent) {
        if (this.\u020f != 0 && this.\u0212 != null) {
            final char keyChar = keyPressEvent.getKeyChar();
            if (keyChar == '\b') {
                this.\u020e();
                keyPressEvent.handled = true;
                return;
            }
            if (keyChar != '\u0003' && keyChar != '\u0016' && keyChar != '\u0018') {
                this.\u020f(keyChar);
                keyPressEvent.handled = true;
                return;
            }
        }
        super.onKeyPress(keyPressEvent);
    }
    
    protected void onInvalidCharacter(final Event event) {
        if (this.\u0211 != null) {
            this.\u0211.invoke((Object)this, event);
        }
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void setExpression(String \u020f) throws RegExException {
        if (\u020f != null && \u020f.length() == 0) {
            \u020f = null;
        }
        if ((this.\u020e = \u020f) != null) {
            (this.\u0212 = new RegEx()).compile(\u020f);
            return;
        }
        this.\u0212 = null;
    }
    
    public String getExpression() {
        return this.\u020e;
    }
    
    protected void onEnter(final Event event) {
        super.onEnter(event);
        final int length = ((Control)this).getText().length();
        this.setSelectionStart(0);
        if (length > 0) {
            this.setSelectionEnd(length);
        }
        else {
            this.setSelectionEnd(0);
        }
        ((Control)this).invalidate();
    }
    
    public void removeOnInvalidCharacter(final EventHandler eventHandler) {
        this.\u0211 = (EventHandler)Delegate.remove((Delegate)this.\u0211, (Delegate)eventHandler);
    }
    
    private void \u0210() {
        final String text = ((Control)this).getText();
        final int selectionStart = this.getSelectionStart();
        final int selectionEnd = this.getSelectionEnd();
        final int length = text.length();
        String text2;
        if (selectionEnd > selectionStart) {
            text2 = text.substring(0, selectionStart);
            if (selectionEnd < length) {
                text2 += text.substring(selectionEnd);
            }
        }
        else {
            text2 = text.substring(0, selectionStart);
            if (selectionStart + 1 < length) {
                text2 += text.substring(selectionStart + 1);
            }
        }
        if (this.\u0212.match(text2, true)) {
            ((Control)this).setText(text2);
            this.select(selectionStart, selectionStart);
            return;
        }
        this.onInvalidCharacter(Event.EMPTY);
    }
    
    protected void onMatchFailed(final Event event) {
        if (this.\u0210 != null) {
            this.\u0210.invoke((Object)this, event);
        }
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        if (this.\u020f != 0 && this.\u0212 != null && keyEvent.getKeyCode() == 46) {
            this.\u0210();
            keyEvent.handled = true;
            return;
        }
        super.onKeyDown(keyEvent);
    }
}
