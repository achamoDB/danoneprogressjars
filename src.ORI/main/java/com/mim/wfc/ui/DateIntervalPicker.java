// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Form;
import java.util.GregorianCalendar;
import com.ms.wfc.core.WFCInvalidArgumentException;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.app.Time;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.Button;
import com.ms.wfc.ui.DateTimePicker;
import com.ms.wfc.core.Container;
import com.ms.wfc.ui.UserControl;

public class DateIntervalPicker extends UserControl
{
    private boolean \u039f;
    private boolean \u03a0;
    private boolean \u03a1;
    private int \u03a3;
    private boolean \u03a4;
    private boolean \u03a5;
    private int \u03a6;
    private int \u03a7;
    private int \u03a8;
    Container \u011d;
    DateTimePicker \u03a9;
    DateTimePicker \u03aa;
    Button \u03ab;
    Button \u03ac;
    Button \u03ad;
    Button \u03ae;
    Button \u03af;
    Button \u03b0;
    Button \u03b1;
    Button \u03b2;
    Button \u03b3;
    Button \u03b4;
    Button \u03b5;
    Button \u03b6;
    private static Class \u03b7;
    
    public DateTimePicker getToControl() {
        return this.\u03aa;
    }
    
    protected void btnYesterday_click(final Object o, final Event event) {
        final Time addDays = new Time().addDays(-1);
        this.\u03a9.setValue(addDays);
        this.\u03aa.setValue(addDays);
    }
    
    protected void btnThisWeek_click(final Object o, final Event event) {
        final Time time = new Time();
        final int dayOfWeek = time.getDayOfWeek();
        int n = 0;
        int n2 = 0;
        if (this.\u03a3 == 3) {
            switch (dayOfWeek) {
                case 1: {
                    n2 = (this.\u03a4 ? 4 : 6);
                    break;
                }
                case 2: {
                    n2 = (this.\u03a4 ? 3 : 5);
                    break;
                }
                case 3: {
                    n2 = (this.\u03a4 ? 2 : 4);
                    break;
                }
                case 4: {
                    n2 = (this.\u03a4 ? 1 : 3);
                    break;
                }
                case 5: {
                    n2 = (this.\u03a4 ? 0 : 2);
                    break;
                }
                case 6: {
                    if (this.\u03a4) {
                        this.reset();
                        return;
                    }
                    n2 = 1;
                    break;
                }
                case 7: {
                    if (this.\u03a4) {
                        this.reset();
                        return;
                    }
                    n2 = 0;
                    break;
                }
                default: {
                    this.reset();
                    return;
                }
            }
        }
        else if (this.\u03a3 == 1) {
            switch (dayOfWeek) {
                case 1: {
                    n = 0;
                    break;
                }
                case 2: {
                    n = -1;
                    break;
                }
                case 3: {
                    n = -2;
                    break;
                }
                case 4: {
                    n = -3;
                    break;
                }
                case 5: {
                    n = -4;
                    break;
                }
                case 6: {
                    n = -5;
                    if (this.\u03a4) {
                        n2 = -1;
                        break;
                    }
                    break;
                }
                case 7: {
                    n = -6;
                    if (this.\u03a4) {
                        n2 = -2;
                        break;
                    }
                    break;
                }
                default: {
                    return;
                }
            }
        }
        else {
            switch (dayOfWeek) {
                case 1: {
                    n = 0;
                    n2 = (this.\u03a4 ? 4 : 6);
                    break;
                }
                case 2: {
                    n = -1;
                    n2 = (this.\u03a4 ? 3 : 5);
                    break;
                }
                case 3: {
                    n = -2;
                    n2 = (this.\u03a4 ? 2 : 4);
                    break;
                }
                case 4: {
                    n = -3;
                    n2 = (this.\u03a4 ? 1 : 3);
                    break;
                }
                case 5: {
                    n = -4;
                    n2 = (this.\u03a4 ? 0 : 2);
                    break;
                }
                case 6: {
                    n = -5;
                    n2 = (this.\u03a4 ? -1 : 1);
                    break;
                }
                case 7: {
                    n = -6;
                    n2 = (this.\u03a4 ? -2 : 0);
                    break;
                }
                default: {
                    this.reset();
                    return;
                }
            }
        }
        final Time addDays = time.addDays(n);
        final Time addDays2 = time.addDays(n2);
        this.\u03a9.setValue(addDays);
        this.\u03aa.setValue(addDays2);
    }
    
    protected void btnThisMonth_click(final Object o, final Event event) {
        final Time time = new Time();
        final int month = time.getMonth();
        final int year = time.getYear();
        final int day = time.getDay();
        int n;
        int n2;
        if (this.\u03a3 == 3) {
            n = day;
            n2 = this.\u03a0(month, year);
        }
        else if (this.\u03a3 == 1) {
            n = 1;
            n2 = day;
        }
        else {
            n = 1;
            n2 = this.\u03a0(month, year);
        }
        final Time value = new Time(year, month, n);
        final Time value2 = new Time(year, month, n2);
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public int getPresentStyle() {
        return this.\u03a3;
    }
    
    public void setPresentStyle(final int \u03c3) {
        if (!DateIntervalPickerPresentStyle.valid(\u03c3)) {
            throw new WFCInvalidEnumException("presentStyle", \u03c3, (DateIntervalPicker.\u03b7 != null) ? DateIntervalPicker.\u03b7 : (DateIntervalPicker.\u03b7 = \u00c6("com.mim.wfc.ui.DateIntervalPickerPresentStyle")));
        }
        this.\u03a3 = \u03c3;
    }
    
    private void \u0118() {
        ((Control)this).setBackColor(Color.CONTROL);
        ((Control)this).setSize(new Point(435, 118));
        ((Control)this).setText("DateIntervalPicker");
        ((Control)this.\u03a9).setSize(new Point(210, 21));
        ((Control)this.\u03a9).setTabIndex(0);
        this.\u03a9.setValue(new Time(599154347254864464L));
        this.\u03a9.setShowNone(true);
        ((Control)this.\u03aa).setLocation(new Point(225, 0));
        ((Control)this.\u03aa).setSize(new Point(210, 21));
        ((Control)this.\u03aa).setTabIndex(1);
        this.\u03aa.setValue(new Time(599154347254864464L));
        this.\u03aa.setShowNone(true);
        ((Control)this.\u03ab).setLocation(new Point(0, 35));
        ((Control)this.\u03ab).setSize(new Point(100, 23));
        ((Control)this.\u03ab).setTabIndex(2);
        ((Control)this.\u03ab).setText("&Today");
        ((Control)this.\u03ab).addOnClick(new EventHandler((Object)this, "btnToday_click"));
        ((Control)this.\u03ac).setLocation(new Point(0, 65));
        ((Control)this.\u03ac).setSize(new Point(100, 23));
        ((Control)this.\u03ac).setTabIndex(6);
        ((Control)this.\u03ac).setText("Yesterday");
        ((Control)this.\u03ac).addOnClick(new EventHandler((Object)this, "btnYesterday_click"));
        ((Control)this.\u03ad).setLocation(new Point(110, 35));
        ((Control)this.\u03ad).setSize(new Point(100, 23));
        ((Control)this.\u03ad).setTabIndex(3);
        ((Control)this.\u03ad).setText("This &week");
        ((Control)this.\u03ad).addOnClick(new EventHandler((Object)this, "btnThisWeek_click"));
        ((Control)this.\u03ae).setLocation(new Point(110, 65));
        ((Control)this.\u03ae).setSize(new Point(100, 23));
        ((Control)this.\u03ae).setTabIndex(7);
        ((Control)this.\u03ae).setText("Last week");
        ((Control)this.\u03ae).addOnClick(new EventHandler((Object)this, "btnLastWeek_click"));
        ((Control)this.\u03af).setLocation(new Point(225, 35));
        ((Control)this.\u03af).setSize(new Point(100, 23));
        ((Control)this.\u03af).setTabIndex(4);
        ((Control)this.\u03af).setText("This &month");
        ((Control)this.\u03af).addOnClick(new EventHandler((Object)this, "btnThisMonth_click"));
        ((Control)this.\u03b0).setLocation(new Point(225, 65));
        ((Control)this.\u03b0).setSize(new Point(100, 23));
        ((Control)this.\u03b0).setTabIndex(8);
        ((Control)this.\u03b0).setText("Last month");
        ((Control)this.\u03b0).addOnClick(new EventHandler((Object)this, "btnLastMonth_click"));
        ((Control)this.\u03b1).setLocation(new Point(335, 35));
        ((Control)this.\u03b1).setSize(new Point(100, 23));
        ((Control)this.\u03b1).setTabIndex(5);
        ((Control)this.\u03b1).setText("This &year");
        ((Control)this.\u03b1).addOnClick(new EventHandler((Object)this, "btnThisYear_click"));
        ((Control)this.\u03b2).setLocation(new Point(335, 65));
        ((Control)this.\u03b2).setSize(new Point(100, 23));
        ((Control)this.\u03b2).setTabIndex(9);
        ((Control)this.\u03b2).setText("Last year");
        ((Control)this.\u03b2).addOnClick(new EventHandler((Object)this, "btnLastYear_click"));
        ((Control)this.\u03b3).setLocation(new Point(0, 95));
        ((Control)this.\u03b3).setSize(new Point(100, 23));
        ((Control)this.\u03b3).setTabIndex(10);
        ((Control)this.\u03b3).setText("Tomorrow");
        ((Control)this.\u03b3).addOnClick(new EventHandler((Object)this, "btnTomorrow_click"));
        ((Control)this.\u03b4).setLocation(new Point(110, 95));
        ((Control)this.\u03b4).setSize(new Point(100, 23));
        ((Control)this.\u03b4).setTabIndex(11);
        ((Control)this.\u03b4).setText("Next week");
        ((Control)this.\u03b4).addOnClick(new EventHandler((Object)this, "btnNextWeek_click"));
        ((Control)this.\u03b5).setLocation(new Point(225, 95));
        ((Control)this.\u03b5).setSize(new Point(100, 23));
        ((Control)this.\u03b5).setTabIndex(12);
        ((Control)this.\u03b5).setText("Next month");
        ((Control)this.\u03b5).addOnClick(new EventHandler((Object)this, "btnNextMonth_click"));
        ((Control)this.\u03b6).setLocation(new Point(335, 95));
        ((Control)this.\u03b6).setSize(new Point(100, 23));
        ((Control)this.\u03b6).setTabIndex(13);
        ((Control)this.\u03b6).setText("Next year");
        ((Control)this.\u03b6).addOnClick(new EventHandler((Object)this, "btnNextYear_click"));
        ((Form)this).setNewControls(new Control[] { (Control)this.\u03b6, (Control)this.\u03b5, (Control)this.\u03b4, (Control)this.\u03b3, (Control)this.\u03b2, (Control)this.\u03b1, (Control)this.\u03b0, (Control)this.\u03af, (Control)this.\u03ae, (Control)this.\u03ad, (Control)this.\u03ac, (Control)this.\u03ab, (Control)this.\u03aa, (Control)this.\u03a9 });
    }
    
    public boolean getWorkDay() {
        return this.\u03a4;
    }
    
    public void setWorkDay(final boolean \u03c4) {
        this.\u03a4 = \u03c4;
    }
    
    private void \u039f(final Button[] array, final boolean visible, final int n) {
        for (int i = 0; i < array.length; ++i) {
            ((Control)array[i]).setVisible(visible);
            ((Control)array[i]).setLocation(((Control)array[i]).getLocation().x, n);
        }
    }
    
    private void \u025a() {
        int n = 0;
        final Button[] array = { this.\u03ab, this.\u03ad, this.\u03af, this.\u03b1 };
        final Button[] array2 = { this.\u03ac, this.\u03ae, this.\u03b0, this.\u03b2 };
        final Button[] array3 = { this.\u03b3, this.\u03b4, this.\u03b5, this.\u03b6 };
        this.\u039f(array, this.\u03a1, this.\u03a6 + n * this.\u03a7);
        if (this.\u03a1) {
            ++n;
        }
        this.\u039f(array2, this.\u03a0, this.\u03a6 + n * this.\u03a7);
        if (this.\u03a0) {
            ++n;
        }
        this.\u039f(array3, this.\u039f, this.\u03a6 + n * this.\u03a7);
        if (this.\u039f) {
            ++n;
        }
        ((Control)this).setHeight(this.\u03a8 + n * this.\u03a7);
    }
    
    public void setButtonTexts(final String[] array) throws WFCInvalidArgumentException {
        if (array.length != 12) {
            throw new WFCInvalidArgumentException();
        }
        ((Control)this.\u03ab).setText(array[0]);
        ((Control)this.\u03ac).setText(array[1]);
        ((Control)this.\u03b3).setText(array[2]);
        ((Control)this.\u03ad).setText(array[3]);
        ((Control)this.\u03ae).setText(array[4]);
        ((Control)this.\u03b4).setText(array[5]);
        ((Control)this.\u03af).setText(array[6]);
        ((Control)this.\u03b0).setText(array[7]);
        ((Control)this.\u03b5).setText(array[8]);
        ((Control)this.\u03b1).setText(array[9]);
        ((Control)this.\u03b2).setText(array[10]);
        ((Control)this.\u03b6).setText(array[11]);
    }
    
    protected void btnLastWeek_click(final Object o, final Event event) {
        final Time time = new Time();
        int n = 0;
        int n2 = 0;
        switch (time.getDayOfWeek()) {
            case 1: {
                n = -7;
                n2 = (this.\u03a4 ? -3 : -1);
                break;
            }
            case 2: {
                n = -8;
                n2 = (this.\u03a4 ? -4 : -2);
                break;
            }
            case 3: {
                n = -9;
                n2 = (this.\u03a4 ? -5 : -3);
                break;
            }
            case 4: {
                n = -10;
                n2 = (this.\u03a4 ? -6 : -4);
                break;
            }
            case 5: {
                n = -11;
                n2 = (this.\u03a4 ? -7 : -5);
                break;
            }
            case 6: {
                n = -12;
                n2 = (this.\u03a4 ? -8 : -6);
                break;
            }
            case 7: {
                n = -13;
                n2 = (this.\u03a4 ? -9 : -7);
                break;
            }
            default: {
                return;
            }
        }
        final Time addDays = time.addDays(n);
        final Time addDays2 = time.addDays(n2);
        this.\u03a9.setValue(addDays);
        this.\u03aa.setValue(addDays2);
    }
    
    protected void btnLastMonth_click(final Object o, final Event event) {
        final Time time = new Time();
        int year = time.getYear();
        int month = time.getMonth();
        if (month == 1) {
            month = 12;
            --year;
        }
        else {
            --month;
        }
        final Time value = new Time(year, month, 1);
        final Time value2 = new Time(year, month, this.\u03a0(month, year));
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    protected void btnThisYear_click(final Object o, final Event event) {
        final Time time = new Time();
        final int year = time.getYear();
        time.getMonth();
        time.getDay();
        Time value;
        Time value2;
        if (this.\u03a3 == 3) {
            value = time;
            value2 = new Time(year, 12, 31);
        }
        else if (this.\u03a3 == 1) {
            value = new Time(year, 1, 1);
            value2 = time;
        }
        else {
            value = new Time(year, 1, 1);
            value2 = new Time(year, 12, 31);
        }
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    public boolean getPastButtons() {
        return this.\u03a0;
    }
    
    public void setPastButtons(final boolean \u03c0) {
        this.\u03a0 = \u03c0;
        this.\u025a();
    }
    
    public boolean getAllowNullValues() {
        return this.\u03a5;
    }
    
    public void setAllowNullValues(final boolean showNone) {
        this.\u03a5 = showNone;
        this.\u03a9.setShowNone(showNone);
        this.\u03aa.setShowNone(showNone);
    }
    
    public boolean getFutureButtons() {
        return this.\u039f;
    }
    
    public void setFutureButtons(final boolean \u03bf) {
        this.\u039f = \u03bf;
        this.\u025a();
    }
    
    public void begin() {
        this.\u025a();
    }
    
    public void reset() {
        this.\u03a9.setValue((Time)null);
        this.\u03aa.setValue((Time)null);
    }
    
    public void setToValue(final Time value) {
        this.\u03aa.setValue(value);
    }
    
    public Time getToValue() {
        return this.\u03aa.getValue();
    }
    
    public DateIntervalPicker() {
        this.\u039f = false;
        this.\u03a0 = true;
        this.\u03a1 = true;
        this.\u03a3 = 2;
        this.\u03a4 = false;
        this.\u03a5 = true;
        this.\u011d = new Container();
        this.\u03a9 = new DateTimePicker();
        this.\u03aa = new DateTimePicker();
        this.\u03ab = new Button();
        this.\u03ac = new Button();
        this.\u03ad = new Button();
        this.\u03ae = new Button();
        this.\u03af = new Button();
        this.\u03b0 = new Button();
        this.\u03b1 = new Button();
        this.\u03b2 = new Button();
        this.\u03b3 = new Button();
        this.\u03b4 = new Button();
        this.\u03b5 = new Button();
        this.\u03b6 = new Button();
        _cls753A._mth821F();
        this.\u0118();
        this.\u03a6 = ((Control)this.\u03ab).getTop();
        this.\u03a7 = ((Control)this.\u03ac).getTop() - this.\u03a6;
        this.\u03a8 = ((Control)this).getHeight() - 3 * this.\u03a7;
    }
    
    protected void btnTomorrow_click(final Object o, final Event event) {
        final Time addDays = new Time().addDays(1);
        this.\u03a9.setValue(addDays);
        this.\u03aa.setValue(addDays);
    }
    
    protected void btnNextYear_click(final Object o, final Event event) {
        int year = new Time().getYear();
        ++year;
        final Time value = new Time(year, 1, 1);
        final Time value2 = new Time(year, 12, 31);
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public boolean getPresentButtons() {
        return this.\u03a1;
    }
    
    protected void btnToday_click(final Object o, final Event event) {
        final Time time = new Time();
        this.\u03a9.setValue(time);
        this.\u03aa.setValue(time);
    }
    
    public void dispose() {
        super.dispose();
        this.\u011d.dispose();
    }
    
    protected void btnNextWeek_click(final Object o, final Event event) {
        final Time time = new Time();
        int n = 0;
        int n2 = 0;
        switch (time.getDayOfWeek()) {
            case 1: {
                n = 7;
                n2 = (this.\u03a4 ? 11 : 13);
                break;
            }
            case 2: {
                n = 6;
                n2 = (this.\u03a4 ? 10 : 12);
                break;
            }
            case 3: {
                n = 5;
                n2 = (this.\u03a4 ? 9 : 11);
                break;
            }
            case 4: {
                n = 4;
                n2 = (this.\u03a4 ? 8 : 10);
                break;
            }
            case 5: {
                n = 3;
                n2 = (this.\u03a4 ? 7 : 9);
                break;
            }
            case 6: {
                n = 2;
                n2 = (this.\u03a4 ? 6 : 8);
                break;
            }
            case 7: {
                n = 1;
                n2 = (this.\u03a4 ? 5 : 6);
                break;
            }
            default: {
                return;
            }
        }
        final Time addDays = time.addDays(n);
        final Time addDays2 = time.addDays(n2);
        this.\u03a9.setValue(addDays);
        this.\u03aa.setValue(addDays2);
    }
    
    public DateTimePicker getFromControl() {
        return this.\u03a9;
    }
    
    protected void btnNextMonth_click(final Object o, final Event event) {
        final Time time = new Time();
        int year = time.getYear();
        int month = time.getMonth();
        if (month == 12) {
            month = 1;
            ++year;
        }
        else {
            ++month;
        }
        final Time value = new Time(year, month, 1);
        final Time value2 = new Time(year, month, this.\u03a0(month, year));
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    protected void btnLastYear_click(final Object o, final Event event) {
        int year = new Time().getYear();
        --year;
        final Time value = new Time(year, 1, 1);
        final Time value2 = new Time(year, 12, 31);
        this.\u03a9.setValue(value);
        this.\u03aa.setValue(value2);
    }
    
    public void setFromValue(final Time value) {
        this.\u03a9.setValue(value);
    }
    
    public Time getFromValue() {
        return this.\u03a9.getValue();
    }
    
    public void setPresentButtons(final boolean \u03c1) {
        this.\u03a1 = \u03c1;
        this.\u025a();
    }
    
    private int \u03a0(final int n, final int year) {
        int n2 = 0;
        switch (n) {
            case 1: {
                n2 = 31;
                break;
            }
            case 2: {
                n2 = (new GregorianCalendar().isLeapYear(year) ? 29 : 28);
                break;
            }
            case 3: {
                n2 = 31;
                break;
            }
            case 4: {
                n2 = 30;
                break;
            }
            case 5: {
                n2 = 31;
                break;
            }
            case 6: {
                n2 = 30;
                break;
            }
            case 7: {
                n2 = 31;
                break;
            }
            case 8: {
                n2 = 31;
                break;
            }
            case 9: {
                n2 = 30;
                break;
            }
            case 10: {
                n2 = 31;
                break;
            }
            case 11: {
                n2 = 30;
                break;
            }
            case 12: {
                n2 = 31;
                break;
            }
            default: {
                n2 = 0;
                break;
            }
        }
        return n2;
    }
}
