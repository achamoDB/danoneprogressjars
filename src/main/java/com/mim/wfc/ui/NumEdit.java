// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.KeyPressEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.KeyEvent;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.CancelEvent;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.DecimalFormat;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Edit;

public class NumEdit extends Edit
{
    private double \u00f0;
    private boolean \u01a8;
    private int \u01a9;
    private int \u01aa;
    private int \u01ab;
    private double[] \u01ac;
    private double \u01ad;
    private double \u01ae;
    private boolean \u01af;
    private boolean \u01b0;
    private boolean \u01b1;
    private String \u01b2;
    private Color \u01b3;
    private CancelEventHandler \u01b4;
    private EventHandler \u01b5;
    private EventHandler \u01b6;
    private int \u01b7;
    private int \u01b8;
    private Color \u01b9;
    private boolean \u01ba;
    
    public int getPrecision() {
        return this.\u01aa;
    }
    
    private boolean \u01a8() {
        String source = ((Control)this).getText();
        if (source != null && this.\u01b2 != null && source.startsWith(this.\u01b2)) {
            source = source.substring(this.\u01b2.length());
        }
        boolean b = true;
        if (source != null && source.length() != 0) {
            final NumberFormat \u0283 = this.\u01a9(false);
            try {
                this.\u00f0 = \u0283.parse(source).doubleValue();
                this.\u01a8 = false;
                return true;
            }
            catch (ParseException ex) {
                b = false;
                if (\u0283 instanceof DecimalFormat) {
                    final DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat)\u0283).getDecimalFormatSymbols();
                    final char decimalSeparator = decimalFormatSymbols.getDecimalSeparator();
                    final char groupingSeparator = decimalFormatSymbols.getGroupingSeparator();
                    final int index = source.indexOf(groupingSeparator);
                    final int index2 = source.indexOf(decimalSeparator);
                    if (index >= 0 && index2 < 0) {
                        final String replace = source.replace(groupingSeparator, decimalSeparator);
                        try {
                            this.\u00f0 = \u0283.parse(replace).doubleValue();
                            this.\u01a8 = false;
                            return true;
                        }
                        catch (ParseException ex2) {}
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException ex3) {
                b = false;
            }
        }
        this.\u01a8 = true;
        this.\u00f0 = 0.0;
        return b;
    }
    
    public void onValidate(final CancelEvent cancelEvent) {
        if (this.\u01b4 != null) {
            this.\u01b4.invoke((Object)this, cancelEvent);
        }
    }
    
    private NumberFormat \u01a9(final boolean b) {
        final NumberFormat numberInstance = NumberFormat.getNumberInstance();
        if (b) {
            numberInstance.setGroupingUsed(this.\u01b1);
            numberInstance.setMinimumIntegerDigits((this.\u01a9 > 0) ? this.\u01a9 : 1);
            numberInstance.setMinimumFractionDigits(this.\u01af ? this.\u01aa : 0);
            numberInstance.setMaximumFractionDigits(this.\u01aa);
        }
        return numberInstance;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public void setStepWidth2(final double n) {
        this.\u01ac[1] = n;
    }
    
    public double getStepWidth2() {
        return this.\u01ac[1];
    }
    
    public void setNullValue(final boolean \u01a8) {
        this.\u01a8 = \u01a8;
        if (\u01a8) {
            this.\u00f0 = 0.0;
        }
        this.display(((Control)this).getFocused());
        this.onValueChanged(Event.EMPTY);
    }
    
    public boolean validate() {
        if (this.\u01ba) {
            return true;
        }
        try {
            this.\u01ba = true;
            if (!this.\u01a8()) {
                if (this.\u01b5 != null) {
                    this.onValidationFailed(Event.EMPTY);
                }
                else {
                    ((Control)this).focus();
                    ((Control)this).select();
                    MsgBox.showResource("NumEdit", "msgParse");
                }
                return false;
            }
            if (!this.\u01a8) {
                if ((this.\u01ae != 0.0 || this.\u01ad != 0.0) && (this.\u00f0 < this.\u01ae || this.\u00f0 > this.\u01ad)) {
                    final NumberFormat \u0283 = this.\u01a9(true);
                    final String format = \u0283.format(this.\u01ae);
                    final String format2 = \u0283.format(this.\u01ad);
                    if (this.\u01b5 != null) {
                        this.onValidationFailed(Event.EMPTY);
                    }
                    else {
                        final String[] array = { format, format2 };
                        ((Control)this).focus();
                        ((Control)this).select();
                        MsgBox.formatResource("NumEdit", "msgRange", array);
                    }
                    return false;
                }
                if (this.\u01b0) {
                    final double number = this.\u01ac[0];
                    if (this.\u00f0 != (int)(this.\u00f0 / number) * number) {
                        final String format3 = this.\u01a9(true).format(number);
                        if (this.\u01b5 != null) {
                            this.onValidationFailed(Event.EMPTY);
                        }
                        else {
                            final String[] array2 = { format3 };
                            ((Control)this).focus();
                            ((Control)this).select();
                            MsgBox.formatResource("NumEdit", "msgStep", array2);
                        }
                        return false;
                    }
                }
            }
            final CancelEvent cancelEvent = new CancelEvent();
            this.onValidate(cancelEvent);
            if (cancelEvent.cancel) {
                return false;
            }
            this.onValueChanged(Event.EMPTY);
        }
        finally {
            this.\u01ba = false;
        }
        return true;
    }
    
    private void \u01aa(final boolean b, final int n) {
        final double n2 = this.\u01ac[n];
        if (n2 == 0.0) {
            return;
        }
        final double \u00f0 = this.\u00f0;
        double \u00f02;
        if (this.\u01a8) {
            \u00f02 = 0.0;
        }
        else {
            final int n3 = (int)(\u00f0 / n2);
            if (\u00f0 != n3 * n2) {
                if (b) {
                    if (\u00f0 >= 0.0) {
                        \u00f02 = (n3 + 1) * n2;
                    }
                    else {
                        \u00f02 = n3 * n2;
                    }
                }
                else if (\u00f0 >= 0.0) {
                    \u00f02 = n3 * n2;
                }
                else {
                    \u00f02 = (n3 - 1) * n2;
                }
            }
            else if (b) {
                \u00f02 = \u00f0 + n2;
            }
            else {
                \u00f02 = \u00f0 - n2;
            }
        }
        if (this.\u01ae != 0.0 || this.\u01ad != 0.0) {
            if (\u00f02 > this.\u01ad) {
                \u00f02 = this.\u01ad;
            }
            if (\u00f02 < this.\u01ae) {
                \u00f02 = this.\u01ae;
            }
        }
        this.\u00f0 = \u00f02;
        this.\u01a8 = false;
        this.display(true);
    }
    
    public boolean getNullValue() {
        return this.\u01a8;
    }
    
    public void setMaxValue(final double \u01ad) {
        this.\u01ad = \u01ad;
    }
    
    public void setShowTrailingZeros(final boolean \u01b0) {
        this.\u01af = \u01b0;
        this.display(((Control)this).getFocused());
    }
    
    public boolean getShowTrailingZeros() {
        return this.\u01af;
    }
    
    public double getMaxValue() {
        return this.\u01ad;
    }
    
    public void addOnValidate(final CancelEventHandler cancelEventHandler) {
        this.\u01b4 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01b4, (Delegate)cancelEventHandler);
    }
    
    public void setCurrencySymbol(final String \u028b) {
        this.\u01b2 = \u028b;
        this.display(((Control)this).getFocused());
    }
    
    public String getCurrencySymbol() {
        return this.\u01b2;
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            ((Control)this).invalidate();
        }
    }
    
    protected void onEnter(final Event event) {
        super.onEnter(event);
        this.display(true);
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
    
    public void setValidateStep(final boolean \u01b0) {
        this.\u01b0 = \u01b0;
    }
    
    public boolean getValidateStep() {
        return this.\u01b0;
    }
    
    public void removeOnValueChanged(final EventHandler eventHandler) {
        this.\u01b6 = (EventHandler)Delegate.remove((Delegate)this.\u01b6, (Delegate)eventHandler);
    }
    
    public void setStepWidth3(final double n) {
        this.\u01ac[2] = n;
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        final int keyCode = keyEvent.getKeyCode();
        if (keyCode == 38 || keyCode == 40) {
            this.\u01a8();
            if (this.\u01b8 < 0) {
                this.\u01b8 = (keyEvent.getControl() ? 2 : (keyEvent.getShift() ? 1 : 0));
                this.\u01b7 = 0;
            }
            if (this.\u01ab != 0 && ++this.\u01b7 > this.\u01ab) {
                if (this.\u01b8 < 2) {
                    ++this.\u01b8;
                }
                this.\u01b7 = 0;
            }
            this.\u01aa(keyCode == 38, this.\u01b8);
            keyEvent.handled = true;
            return;
        }
        super.onKeyDown(keyEvent);
    }
    
    public double getStepWidth3() {
        return this.\u01ac[2];
    }
    
    public void setValue(final double \u00f0) {
        this.\u00f0 = \u00f0;
        this.\u01a8 = false;
        this.display(((Control)this).getFocused());
        this.onValueChanged(Event.EMPTY);
    }
    
    public double getValue() {
        return this.\u00f0;
    }
    
    public void setMinValue(final double \u0288) {
        this.\u01ae = \u0288;
    }
    
    public double getMinValue() {
        return this.\u01ae;
    }
    
    public void setProgressiveStepCount(final int \u01ab) {
        this.\u01ab = \u01ab;
    }
    
    public NumEdit() {
        this.\u00f0 = 0.0;
        this.\u01a8 = true;
        this.\u01a9 = 1;
        this.\u01aa = 0;
        this.\u01ab = 40;
        this.\u01ac = new double[] { 1.0, 5.0, 20.0 };
        this.\u01ad = 0.0;
        this.\u01ae = 0.0;
        this.\u01af = true;
        this.\u01b0 = false;
        this.\u01b7 = 0;
        this.\u01b8 = -1;
        this.\u01b9 = null;
        this.\u01ba = false;
        _cls753A._mth821F();
        ((Control)this).setSize(100, 20);
        this.setHideSelection(true);
        this.display(false);
    }
    
    public int getProgressiveStepCount() {
        return this.\u01ab;
    }
    
    public void removeOnValidate(final CancelEventHandler cancelEventHandler) {
        this.\u01b4 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01b4, (Delegate)cancelEventHandler);
    }
    
    public void addOnValidationFailed(final EventHandler eventHandler) {
        this.\u01b5 = (EventHandler)Delegate.combine((Delegate)this.\u01b5, (Delegate)eventHandler);
    }
    
    public void removeOnValidationFailed(final EventHandler eventHandler) {
        this.\u01b5 = (EventHandler)Delegate.remove((Delegate)this.\u01b5, (Delegate)eventHandler);
    }
    
    public void addOnValueChanged(final EventHandler eventHandler) {
        this.\u01b6 = (EventHandler)Delegate.combine((Delegate)this.\u01b6, (Delegate)eventHandler);
    }
    
    protected void onLeave(final Event event) {
        if (this.validate()) {
            super.onLeave(event);
            this.display(false);
        }
    }
    
    public void setDigits(final int \u0283) {
        this.\u01a9 = \u0283;
        this.display(((Control)this).getFocused());
    }
    
    public int getDigits() {
        return this.\u01a9;
    }
    
    public void setTextAlign(final int textAlign) {
        super.setTextAlign(textAlign);
        this.display(((Control)this).getFocused());
    }
    
    public static boolean validateActiveNumEdit(final Control control) {
        for (Control control2 = control.getNextControl((Control)null, true); control2 != null; control2 = control.getNextControl(control2, true)) {
            if (control2.getFocused() && control2 instanceof NumEdit) {
                return ((NumEdit)control2).validate();
            }
            validateActiveNumEdit(control2);
        }
        return true;
    }
    
    protected void onValueChanged(final Event event) {
        if (this.\u01b6 != null) {
            this.\u01b6.invoke((Object)this, event);
        }
    }
    
    protected void onKeyUp(final KeyEvent keyEvent) {
        final int keyCode = keyEvent.getKeyCode();
        if (keyCode == 38 || keyCode == 40) {
            this.\u01b7 = 0;
            this.\u01b8 = -1;
            keyEvent.handled = true;
            return;
        }
        super.onKeyUp(keyEvent);
    }
    
    public void setGrouping(final boolean \u028a) {
        this.\u01b1 = \u028a;
        this.display(((Control)this).getFocused());
    }
    
    public boolean getGrouping() {
        return this.\u01b1;
    }
    
    protected void onKeyPress(final KeyPressEvent keyPressEvent) {
        final char keyChar = keyPressEvent.getKeyChar();
        if (keyChar == '-' || keyChar == '.' || keyChar == ',' || (keyChar >= '0' && keyChar <= '9') || keyChar == '\b' || keyChar == '\u0003' || keyChar == '\u0016' || keyChar == '\u0018') {
            super.onKeyPress(keyPressEvent);
            return;
        }
        keyPressEvent.handled = true;
    }
    
    public void setStepWidth1(final double n) {
        this.\u01ac[0] = n;
    }
    
    public double getStepWidth1() {
        return this.\u01ac[0];
    }
    
    private void display(final boolean b) {
        String text = "";
        if (!this.\u01a8) {
            if (this.\u01b2 != null && !b) {
                text += this.\u01b2;
            }
            text += this.\u01a9(true).format(this.\u00f0);
        }
        if (this.\u01b3 != null) {
            ((Control)this).setForeColor((b || this.\u01a8 || this.\u00f0 >= 0.0) ? this.\u01b9 : this.\u01b3);
        }
        super.setText(text);
        ((Control)this).invalidate();
    }
    
    protected void onValidationFailed(final Event event) {
        if (this.\u01b5 != null) {
            this.\u01b5.invoke((Object)this, event);
        }
    }
    
    public void setNegativeColor(final Color \u01b4) {
        if (this.\u01b9 == null) {
            this.\u01b9 = ((Control)this).getForeColor();
        }
        this.\u01b3 = \u01b4;
        if (\u01b4 == null && this.\u01b9 != null) {
            ((Control)this).setForeColor(this.\u01b9);
        }
        this.display(((Control)this).getFocused());
    }
    
    public Color getNegativeColor() {
        return this.\u01b3;
    }
    
    public void setText(final String s) {
    }
    
    public void setPrecision(final int \u01aa) {
        this.\u01aa = \u01aa;
        this.display(((Control)this).getFocused());
    }
}
