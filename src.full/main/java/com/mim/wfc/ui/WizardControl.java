// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.Form;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.ui.PaintEventHandler;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Pen;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.CancelEvent;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.IComponentSite;
import com.ms.wfc.core.IComponent;
import com.ms.wfc.ui.Panel;
import com.ms.wfc.core.Container;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.ui.Image;
import com.ms.wfc.core.IRequireBegin;
import com.ms.wfc.ui.UserControl;

public class WizardControl extends UserControl implements IRequireBegin
{
    WizardPage[] \u0117;
    private int \u02a0;
    private int \u02a1;
    private int \u02a2;
    private boolean \u02a3;
    private boolean \u02a4;
    private boolean \u02a5;
    private boolean \u02a6;
    private int \u02a7;
    private Image[] \u0191;
    private String[] \u0262;
    private CancelEventHandler \u02a8;
    private CancelEventHandler \u02b0;
    private EventHandler \u02b1;
    private EventHandler \u02b2;
    private EventHandler \u0202;
    private EventHandler \u02b3;
    int \u02b4;
    private boolean \u00e1;
    private boolean \u02b5;
    Container \u011d;
    Panel \u02b6;
    ImageBtn \u02b7;
    ImageBtn \u02b8;
    ImageBtn \u02bb;
    ImageBtn \u011e;
    ImageBtn \u02bc;
    private String[] \u02bd;
    ImageBtn[] \u02be;
    private static Class \u025e;
    
    private void \u02a0(final WizardPage wizardPage) {
        final IComponentSite componentSite = ((Component)this).getComponentSite();
        if (componentSite != null && ((Component)wizardPage).getComponentSite() == null) {
            componentSite.getContainer().remove((IComponent)wizardPage);
        }
    }
    
    public void setBackImage(final Image image) {
        this.setImage(1, image);
    }
    
    public Image getBackImage() {
        return this.getImage(1);
    }
    
    public void onActivatePage(final Event event) {
        if (this.\u02b1 != null) {
            this.\u02b1.invoke((Object)this, event);
        }
    }
    
    public void onNextPage(final CancelEvent cancelEvent) {
        if (this.\u02a8 != null) {
            this.\u02a8.invoke((Object)this, cancelEvent);
        }
    }
    
    public void onPreviousPage(final CancelEvent cancelEvent) {
        if (this.\u02b0 != null) {
            this.\u02b0.invoke((Object)this, cancelEvent);
        }
    }
    
    public void setButtonWidth(final int \u02a2) {
        this.\u02a2 = \u02a2;
        this.\u02a1();
    }
    
    public int getButtonWidth() {
        return this.\u02a2;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public void showNextPage() {
        this.showPage(1002, false);
    }
    
    public void showPreviousPage() {
        this.showPage(1003, false);
    }
    
    public void showCurrentPage() {
        this.showPage(this.\u02b4, false);
    }
    
    public int getCurrentPage() {
        return this.\u02b4;
    }
    
    public void setCancelImage(final Image image) {
        this.setImage(4, image);
    }
    
    public Image getCancelImage() {
        return this.getImage(4);
    }
    
    public void removeOnActivatePage(final EventHandler eventHandler) {
        this.\u02b1 = (EventHandler)Delegate.remove((Delegate)this.\u02b1, (Delegate)eventHandler);
    }
    
    public void removeOnNextPage(final CancelEventHandler cancelEventHandler) {
        this.\u02a8 = (CancelEventHandler)Delegate.remove((Delegate)this.\u02a8, (Delegate)cancelEventHandler);
    }
    
    public void removeOnPreviousPage(final CancelEventHandler cancelEventHandler) {
        this.\u02b0 = (CancelEventHandler)Delegate.remove((Delegate)this.\u02b0, (Delegate)cancelEventHandler);
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            ((Control)this).invalidate();
        }
    }
    
    private void \u02a1() {
        if (!this.\u00e1) {
            return;
        }
        int n = 25;
        int n2 = 0;
        do {
            final Image image = this.\u02be[n2].getImage();
            if (image != null) {
                final Point size = image.getSize();
                if (size.y + 8 <= n) {
                    continue;
                }
                n = size.y + 8;
            }
        } while (++n2 < 5);
        int n3 = 0;
        do {
            int n4;
            if (this.\u02a2 != 0) {
                n4 = this.\u02a2;
            }
            else {
                n4 = this.\u02be[n3].getWidth();
            }
            this.\u02be[n3].setSize(n4, n);
        } while (++n3 < 5);
        final Point size2 = ((Control)this.\u02b6).getSize();
        size2.y = n + this.\u02a1;
        ((Control)this.\u02b6).setSize(size2);
        int x = size2.x;
        int n5 = 4;
        do {
            final Point location = this.\u02be[n5].getLocation();
            location.y = this.\u02a1;
            final int width = this.\u02be[n5].getWidth();
            location.x = x - width;
            if (!this.\u02a3 || n5 != 3) {
                x -= width + this.\u02a0;
            }
            this.\u02be[n5].setLocation(location);
        } while (--n5 >= 0);
        ((Control)this).invalidate();
        if (this.\u0117 != null && this.\u02b4 >= 0) {
            ((Control)this.\u0117[this.\u02b4]).invalidate();
        }
        this.updateButtonState();
        this.\u02bc.setVisible(this.\u02a6);
    }
    
    protected void btnBack_click(final Object o, final Event event) {
        final CancelEvent cancelEvent = new CancelEvent();
        this.onPreviousPage(cancelEvent);
        if (!cancelEvent.cancel) {
            this.showPreviousPage();
        }
    }
    
    public void onFinish(final Event event) {
        if (this.\u02b2 != null) {
            this.\u02b2.invoke((Object)this, event);
        }
    }
    
    public void removeOnCancel(final EventHandler eventHandler) {
        this.\u0202 = (EventHandler)Delegate.remove((Delegate)this.\u0202, (Delegate)eventHandler);
    }
    
    public void removeOnHelp(final EventHandler eventHandler) {
        this.\u02b3 = (EventHandler)Delegate.remove((Delegate)this.\u02b3, (Delegate)eventHandler);
    }
    
    public void remove(final Control control) {
        if (control instanceof WizardPage && this.\u0117 != null) {
            final WizardPage wizardPage = (WizardPage)control;
            int n = 1000;
            final int length = this.\u0117.length;
            int n2 = 0;
            for (int i = 0; i < length; ++i) {
                if (this.\u0117[i] != wizardPage) {
                    ++n2;
                }
            }
            if (length == n2) {
                return;
            }
            if (((Control)wizardPage).getFocused()) {
                ((Form)this).getForm().setActiveControl((Control)this);
            }
            WizardPage[] \u0117;
            if (n2 > 0) {
                \u0117 = new WizardPage[n2];
                int j = 0;
                int n3 = 0;
                while (j < length) {
                    if (this.\u0117[j] != wizardPage) {
                        \u0117[n3++] = this.\u0117[j];
                    }
                    else {
                        n = j;
                    }
                    ++j;
                }
            }
            else {
                \u0117 = null;
            }
            this.\u0117 = \u0117;
            this.\u02a0(wizardPage);
            super.remove((Control)wizardPage);
            if (!this.\u02b5) {
                this.showPage(n, true);
                ((Control)this).invalidate();
            }
        }
        else {
            super.remove(control);
        }
    }
    
    public void setBackText(final String s) {
        this.setText(1, s);
    }
    
    public String getBackText() {
        return this.getText(1);
    }
    
    public void setUseImageButtons(final boolean \u02a4) {
        this.\u02a4 = \u02a4;
        int n = 0;
        do {
            this.setImage(n, this.\u0191[n]);
        } while (++n < 5);
        this.\u02a1();
    }
    
    public boolean getUseImageButtons() {
        return this.\u02a4;
    }
    
    public void addOnCancel(final EventHandler eventHandler) {
        this.\u0202 = (EventHandler)Delegate.combine((Delegate)this.\u0202, (Delegate)eventHandler);
    }
    
    public void addOnHelp(final EventHandler eventHandler) {
        this.\u02b3 = (EventHandler)Delegate.combine((Delegate)this.\u02b3, (Delegate)eventHandler);
    }
    
    public void showPage(final int n, final boolean b) {
        if (!this.\u00e1) {
            return;
        }
        if (this.\u0117 != null) {
            final int n2 = this.\u0117.length - 1;
            int i;
            if (n >= 1000) {
                int n3 = 0;
                int n4 = 1;
                switch (n) {
                    case 1002: {
                        n3 = this.\u02b4 + 1;
                        break;
                    }
                    case 1003: {
                        n3 = this.\u02b4 - 1;
                        n4 = -1;
                        break;
                    }
                    case 1001: {
                        n3 = n2;
                        n4 = -1;
                        break;
                    }
                }
                for (i = n3; i >= 0; i += n4) {
                    if (i > n2) {
                        break;
                    }
                    if (((Control)this.\u0117[i]).getEnabled()) {
                        break;
                    }
                }
            }
            else if (n >= 0 && n >= n2) {
                i = n2;
            }
            else {
                i = n;
            }
            if (i < 0 || i > n2) {
                i = -1;
            }
            if (i != this.\u02b4 || b) {
                this.\u02b4 = i;
                for (int j = 0; j <= n2; ++j) {
                    ((Control)this.\u0117[j]).setVisible(j == this.\u02b4);
                    if (j == this.\u02b4) {}
                    ((Control)this.\u0117[j]).invalidate();
                }
                if (this.\u02b4 >= 0) {
                    ((Control)this.\u0117[this.\u02b4]).bringToFront();
                    final CancelEvent cancelEvent = new CancelEvent();
                    this.\u0117[this.\u02b4].onActivatePage(cancelEvent);
                    if (!cancelEvent.cancel) {
                        this.onActivatePage(Event.EMPTY);
                    }
                }
            }
        }
        this.updateButtonState();
    }
    
    public void enablePage(final int n, final boolean enabled) {
        if (this.\u0117 != null || n >= 0 || n < this.\u0117.length) {
            ((Control)this.\u0117[n]).setEnabled(enabled);
            this.updateButtonState();
        }
    }
    
    public void setNextImage(final Image image) {
        this.setImage(2, image);
    }
    
    public Image getNextImage() {
        return this.getImage(2);
    }
    
    public void setFinishImage(final Image image) {
        this.setImage(3, image);
    }
    
    public Image getFinishImage() {
        return this.getImage(3);
    }
    
    public void setHelpImage(final Image image) {
        this.setImage(0, image);
    }
    
    public Image getHelpImage() {
        return this.getImage(0);
    }
    
    public void addOnActivatePage(final EventHandler eventHandler) {
        this.\u02b1 = (EventHandler)Delegate.combine((Delegate)this.\u02b1, (Delegate)eventHandler);
    }
    
    public void addOnNextPage(final CancelEventHandler cancelEventHandler) {
        this.\u02a8 = (CancelEventHandler)Delegate.combine((Delegate)this.\u02a8, (Delegate)cancelEventHandler);
    }
    
    public void addOnPreviousPage(final CancelEventHandler cancelEventHandler) {
        this.\u02b0 = (CancelEventHandler)Delegate.combine((Delegate)this.\u02b0, (Delegate)cancelEventHandler);
    }
    
    private void \u02a2(final WizardPage wizardPage) {
        final IComponentSite componentSite = ((Component)this).getComponentSite();
        if (componentSite != null && ((Component)wizardPage).getComponentSite() == null) {
            componentSite.getContainer().add((IComponent)wizardPage);
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
    
    public void dispose() {
        super.dispose();
        this.\u011d.dispose();
    }
    
    protected void btnCancel_click(final Object o, final Event event) {
        this.onCancel(Event.EMPTY);
    }
    
    protected void panelButtons_paint(final Object o, final PaintEvent paintEvent) {
        if (this.\u02a7 == 0) {
            return;
        }
        final Graphics graphics = paintEvent.graphics;
        final Rectangle clientRect = ((Control)this.\u02b6).getClientRect();
        if (this.\u02a1 < ((this.\u02a7 == 1) ? 1 : 2)) {
            return;
        }
        final int x = clientRect.x;
        final int y = clientRect.y;
        final int width = clientRect.width;
        final Pen pen = graphics.getPen();
        if (this.\u02a7 == 1) {
            graphics.setPen(Pen.WINDOWTEXT);
            graphics.drawLine(x, y, x + width, y);
        }
        else {
            final Pen pen2 = new Pen(Color.CONTROLLIGHTLIGHT);
            final Pen pen3 = new Pen(Color.CONTROLDARK);
            graphics.setPen((this.\u02a7 == 2) ? pen2 : pen3);
            graphics.drawLine(x, y, x + width, y);
            graphics.setPen((this.\u02a7 == 2) ? pen3 : pen2);
            graphics.drawLine(x, y + 1, x + width, y + 1);
        }
        graphics.setPen(pen);
    }
    
    public void removeOnFinish(final EventHandler eventHandler) {
        this.\u02b2 = (EventHandler)Delegate.remove((Delegate)this.\u02b2, (Delegate)eventHandler);
    }
    
    public void add(final Control control) {
        super.add(control);
        if (control instanceof WizardPage) {
            final WizardPage wizardPage = (WizardPage)control;
            final int n = (this.\u0117 != null) ? this.\u0117.length : 0;
            final WizardPage[] \u0117 = new WizardPage[n + 1];
            if (n > 0) {
                System.arraycopy(this.\u0117, 0, \u0117, 0, n);
            }
            \u0117[n] = wizardPage;
            this.\u0117 = \u0117;
            this.\u02a2(wizardPage);
            if (!this.\u02b5) {
                this.showPage(1001, true);
                ((Control)this).invalidate();
            }
        }
    }
    
    public void setCancelText(final String s) {
        this.setText(4, s);
    }
    
    public String getCancelText() {
        return this.getText(4);
    }
    
    private void \u0118() {
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "WizardControl");
        ((Control)this).setBackColor(Color.CONTROL);
        ((Control)this).setSize((Point)((IResourceManager)resourceManager).getObject("this_size"));
        ((Control)this).setTabIndex(((IResourceManager)resourceManager).getInt("this_tabIndex"));
        ((Control)this).setText(((IResourceManager)resourceManager).getString("this_text"));
        ((Control)this.\u02b6).setDock(2);
        ((Control)this.\u02b6).setLocation((Point)((IResourceManager)resourceManager).getObject("panelButtons_location"));
        ((Control)this.\u02b6).setSize((Point)((IResourceManager)resourceManager).getObject("panelButtons_size"));
        ((Control)this.\u02b6).setTabIndex(((IResourceManager)resourceManager).getInt("panelButtons_tabIndex"));
        ((Control)this.\u02b6).setText(((IResourceManager)resourceManager).getString("panelButtons_text"));
        ((Control)this.\u02b6).addOnPaint(new PaintEventHandler((Object)this, "panelButtons_paint"));
        this.\u02b7.setAnchor(10);
        this.\u02b7.setLocation((Point)((IResourceManager)resourceManager).getObject("btnBack_location"));
        this.\u02b7.setSize((Point)((IResourceManager)resourceManager).getObject("btnBack_size"));
        this.\u02b7.setTabIndex(((IResourceManager)resourceManager).getInt("btnBack_tabIndex"));
        this.\u02b7.setText(((IResourceManager)resourceManager).getString("btnBack_text"));
        this.\u02b7.setImage((Image)((IResourceManager)resourceManager).getObject("btnBack_image"));
        this.\u02b7.addOnClick(new EventHandler((Object)this, "btnBack_click"));
        this.\u02b8.setAnchor(10);
        this.\u02b8.setLocation((Point)((IResourceManager)resourceManager).getObject("btnNext_location"));
        this.\u02b8.setSize((Point)((IResourceManager)resourceManager).getObject("btnNext_size"));
        this.\u02b8.setTabIndex(((IResourceManager)resourceManager).getInt("btnNext_tabIndex"));
        this.\u02b8.setText(((IResourceManager)resourceManager).getString("btnNext_text"));
        this.\u02b8.setAlignment(3);
        this.\u02b8.setImage((Image)((IResourceManager)resourceManager).getObject("btnNext_image"));
        this.\u02b8.addOnClick(new EventHandler((Object)this, "btnNext_click"));
        this.\u02bb.setAnchor(10);
        this.\u02bb.setEnabled(((IResourceManager)resourceManager).getBoolean("btnFinish_enabled"));
        this.\u02bb.setLocation((Point)((IResourceManager)resourceManager).getObject("btnFinish_location"));
        this.\u02bb.setSize((Point)((IResourceManager)resourceManager).getObject("btnFinish_size"));
        this.\u02bb.setTabIndex(((IResourceManager)resourceManager).getInt("btnFinish_tabIndex"));
        this.\u02bb.setTabStop(false);
        this.\u02bb.setText(((IResourceManager)resourceManager).getString("btnFinish_text"));
        this.\u02bb.setImage((Image)((IResourceManager)resourceManager).getObject("btnFinish_image"));
        this.\u02bb.addOnClick(new EventHandler((Object)this, "btnFinish_click"));
        this.\u011e.setAnchor(10);
        this.\u011e.setLocation((Point)((IResourceManager)resourceManager).getObject("btnCancel_location"));
        this.\u011e.setSize((Point)((IResourceManager)resourceManager).getObject("btnCancel_size"));
        this.\u011e.setTabIndex(((IResourceManager)resourceManager).getInt("btnCancel_tabIndex"));
        this.\u011e.setText(((IResourceManager)resourceManager).getString("btnCancel_text"));
        this.\u011e.setImage((Image)((IResourceManager)resourceManager).getObject("btnCancel_image"));
        this.\u011e.addOnClick(new EventHandler((Object)this, "btnCancel_click"));
        this.\u02bc.setAnchor(10);
        this.\u02bc.setLocation((Point)((IResourceManager)resourceManager).getObject("btnHelp_location"));
        this.\u02bc.setSize((Point)((IResourceManager)resourceManager).getObject("btnHelp_size"));
        this.\u02bc.setTabIndex(((IResourceManager)resourceManager).getInt("btnHelp_tabIndex"));
        this.\u02bc.setText(((IResourceManager)resourceManager).getString("btnHelp_text"));
        this.\u02bc.setImage((Image)((IResourceManager)resourceManager).getObject("btnHelp_image"));
        this.\u02bc.addOnClick(new EventHandler((Object)this, "btnHelp_click"));
        this.setNewControls(new Control[] { (Control)this.\u02b6 });
        ((Control)this.\u02b6).setNewControls(new Control[] { this.\u02bc, this.\u011e, this.\u02bb, this.\u02b8, this.\u02b7 });
    }
    
    public void setXButtonDistance(final int \u02a0) {
        this.\u02a0 = \u02a0;
        this.\u02a1();
    }
    
    public int getXButtonDistance() {
        return this.\u02a0;
    }
    
    public void setFinishEnabled(final boolean \u02a5) {
        this.\u02a5 = \u02a5;
        this.updateButtonState();
    }
    
    public boolean getFinishEnabled() {
        return this.\u02a5;
    }
    
    public void setNewControls(final Control[] array) {
        if (array != null) {
            final int length = array.length;
            final Control[] array2 = new Control[length];
            final Control[] array3 = new Control[length];
            int n = 0;
            ((Control)this).suspendLayout();
            try {
                for (int i = 0; i < length; ++i) {
                    if (array[i] instanceof WizardPage) {
                        final WizardPage wizardPage = (WizardPage)array[i];
                        this.remove((Control)wizardPage);
                        final int \u02e0;
                        if ((\u02e0 = wizardPage.\u02e0()) != -1) {
                            array2[\u02e0] = array[i];
                            continue;
                        }
                    }
                    array3[n++] = array[i];
                }
                for (int j = 0; j < length; ++j) {
                    if (array2[j] != null) {
                        this.add(array2[j]);
                    }
                }
                for (int k = 0; k < n; ++k) {
                    this.add(array3[k]);
                }
            }
            finally {
                ((Control)this).resumeLayout();
            }
        }
        this.showPage(1000, true);
    }
    
    public Control[] getNewControls() {
        return (Control[])this.\u0117;
    }
    
    public void setSeparator(final int \u02a7) {
        if (!Splitter3DBorderStyle.valid(\u02a7)) {
            throw new WFCInvalidEnumException("separator", \u02a7, (WizardControl.\u025e != null) ? WizardControl.\u025e : (WizardControl.\u025e = \u00c6("com.mim.wfc.ui.Splitter3DBorderStyle")));
        }
        this.\u02a7 = \u02a7;
        ((Control)this.\u02b6).invalidate();
    }
    
    public int getSeparator() {
        return this.\u02a7;
    }
    
    public void setPages(final WizardPage[] newControls) {
        if (newControls != null) {
            for (int i = 0; i < newControls.length; ++i) {
                newControls[i].\u02d1(i);
            }
        }
        this.setNewControls((Control[])newControls);
    }
    
    public WizardPage[] getPages() {
        return this.\u0117;
    }
    
    public void showFirstPage() {
        this.showPage(1000, false);
    }
    
    private void setImage(final int n, Image image) {
        this.\u0191[n] = image;
        if (this.\u02a4) {
            if (image == null) {
                final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "WizardControl");
                if (resourceManager != null) {
                    image = (Image)((IResourceManager)resourceManager).getObject("btn" + this.\u02bd[n] + "_image");
                }
            }
        }
        else if (image != null) {
            image = null;
        }
        this.\u02be[n].setImage(image);
        this.\u02a1();
    }
    
    private Image getImage(final int n) {
        return this.\u0191[n];
    }
    
    public void setHelpVisible(final boolean \u02a6) {
        this.\u02a6 = \u02a6;
        this.\u02bc.setVisible(this.\u02a6);
    }
    
    public boolean getHelpVisible() {
        return this.\u02a6;
    }
    
    public void setControlBar3Button(final boolean \u02a3) {
        this.\u02a3 = \u02a3;
        this.\u02a1();
    }
    
    public boolean getControlBar3Button() {
        return this.\u02a3;
    }
    
    public void begin() {
        this.\u00e1 = true;
        this.\u02a1();
        this.showPage(1000, true);
    }
    
    public WizardControl() {
        this.\u0117 = null;
        this.\u02a0 = 10;
        this.\u02a1 = 10;
        this.\u02a2 = 0;
        this.\u02a3 = false;
        this.\u02a4 = true;
        this.\u02a5 = false;
        this.\u02a6 = false;
        this.\u02a7 = 3;
        this.\u0191 = new Image[5];
        this.\u0262 = new String[5];
        this.\u02a8 = null;
        this.\u02b0 = null;
        this.\u02b1 = null;
        this.\u02b2 = null;
        this.\u0202 = null;
        this.\u02b3 = null;
        this.\u02b4 = -1;
        this.\u00e1 = false;
        this.\u02b5 = false;
        this.\u011d = new Container();
        this.\u02b6 = new Panel();
        this.\u02b7 = new ImageBtn();
        this.\u02b8 = new ImageBtn();
        this.\u02bb = new ImageBtn();
        this.\u011e = new ImageBtn();
        this.\u02bc = new ImageBtn();
        this.\u02bd = new String[] { "Help", "Back", "Next", "Finish", "Cancel" };
        this.\u02be = new ImageBtn[] { this.\u02bc, this.\u02b7, this.\u02b8, this.\u02bb, this.\u011e };
        _cls753A._mth821F();
        ((Control)this).setStyle(8, true);
        this.\u02be[0] = this.\u02bc;
        this.\u02be[2] = this.\u02b8;
        this.\u02be[1] = this.\u02b7;
        this.\u02be[3] = this.\u02bb;
        this.\u02be[4] = this.\u011e;
        this.\u0118();
    }
    
    protected void btnNext_click(final Object o, final Event event) {
        final CancelEvent cancelEvent = new CancelEvent();
        this.onNextPage(cancelEvent);
        if (!cancelEvent.cancel) {
            this.showNextPage();
        }
    }
    
    protected void btnFinish_click(final Object o, final Event event) {
        this.onFinish(Event.EMPTY);
    }
    
    protected void btnHelp_click(final Object o, final Event event) {
        this.onHelp(Event.EMPTY);
    }
    
    public void addOnFinish(final EventHandler eventHandler) {
        this.\u02b2 = (EventHandler)Delegate.combine((Delegate)this.\u02b2, (Delegate)eventHandler);
    }
    
    public void onHelp(final Event event) {
        if (this.\u02b3 != null) {
            this.\u02b3.invoke((Object)this, event);
        }
    }
    
    public void setNextText(final String s) {
        this.setText(2, s);
    }
    
    public String getNextText() {
        return this.getText(2);
    }
    
    public void setFinishText(final String s) {
        this.setText(3, s);
    }
    
    public String getFinishText() {
        return this.getText(3);
    }
    
    public void setHelpText(final String s) {
        this.setText(0, s);
    }
    
    public void setYButtonDistance(final int \u02a1) {
        this.\u02a1 = \u02a1;
        this.\u02a1();
    }
    
    public int getYButtonDistance() {
        return this.\u02a1;
    }
    
    public String getHelpText() {
        return this.getText(0);
    }
    
    public void updateButtonState() {
        boolean enabled = false;
        boolean enabled2 = false;
        if (this.\u0117 != null && this.\u02b4 != -1) {
            for (int n = this.\u0117.length - 1, i = this.\u02b4 + 1; i <= n; ++i) {
                if (((Control)this.\u0117[i]).getEnabled()) {
                    enabled2 = true;
                    break;
                }
            }
            for (int j = this.\u02b4 - 1; j >= 0; --j) {
                if (((Control)this.\u0117[j]).getEnabled()) {
                    enabled = true;
                    break;
                }
            }
        }
        boolean visible;
        boolean visible2;
        if (this.\u02a3) {
            if (this.\u02a5) {
                enabled2 = false;
                visible = false;
                visible2 = true;
            }
            else {
                visible = true;
                visible2 = false;
            }
        }
        else {
            visible = true;
            visible2 = true;
        }
        this.\u02b8.setVisible(visible);
        this.\u02bb.setVisible(visible2);
        this.\u02b7.setEnabled(enabled);
        this.\u02b8.setEnabled(enabled2);
        this.\u02bb.setEnabled(this.\u02a5);
    }
    
    public void onCancel(final Event event) {
        if (this.\u0202 != null) {
            this.\u0202.invoke((Object)this, event);
        }
    }
    
    private void setText(final int n, final String text) {
        this.\u0262[n] = text;
        this.\u02be[n].setText(text);
    }
    
    private String getText(final int n) {
        return this.\u0262[n];
    }
}
