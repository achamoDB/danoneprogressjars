// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.app.SystemInformation;
import com.ms.wfc.ui.Icon;
import com.ms.win32.User32;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.IButtonControl;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.Event;
import java.text.MessageFormat;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.Label;
import com.ms.wfc.ui.PictureBox;
import com.ms.wfc.core.Container;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.Font;
import com.ms.wfc.ui.Form;

public class MsgBox extends Form
{
    private static String \u02e3;
    private static Font \u03c4;
    private static boolean \u02a4;
    private static int \u01ce;
    private static int \u02a2;
    private static Image[] \u03c5;
    private ImageBtn \u03c6;
    private TextRect \u01d1;
    public static final int OK = 0;
    public static final int OKCANCEL = 1;
    public static final int YESNO = 4;
    public static final int YESNOCANCEL = 3;
    public static final int RETRYCANCEL = 5;
    public static final int ABORTRETRYIGNORE = 2;
    public static final int YESFORALL = 4096;
    public static final int NOFORALL = 8192;
    public static final int DEFBUTTON1 = 0;
    public static final int DEFBUTTON2 = 256;
    public static final int DEFBUTTON3 = 512;
    public static final int DEFBUTTON4 = 1024;
    public static final int DEFBUTTON5 = 2048;
    public static final int ICONASTERISK = 64;
    public static final int ICONERROR = 16;
    public static final int ICONEXCLAMATION = 48;
    public static final int ICONHAND = 16;
    public static final int ICONINFORMATION = 64;
    public static final int ICONQUESTION = 32;
    public static final int ICONSTOP = 16;
    public static final int ICONWARNING = 48;
    public static final int IDOK = 1;
    public static final int IDCANCEL = 2;
    public static final int IDNO = 7;
    public static final int IDYES = 6;
    public static final int IDABORT = 3;
    public static final int IDRETRY = 4;
    public static final int IDIGNORE = 5;
    public static final int IDYESFORALL = 8;
    public static final int IDNOFORALL = 9;
    Container \u011d;
    ImageBtn \u03c7;
    ImageBtn \u011e;
    ImageBtn \u03c8;
    ImageBtn \u03c9;
    ImageBtn \u03ca;
    ImageBtn \u03cb;
    ImageBtn \u03cc;
    ImageBtn \u03cd;
    PictureBox \u03ce;
    Label \u03d0;
    ImageBtn \u03d1;
    ImageBtn \u03d2;
    
    public static int showResource(final String s, final String s2) {
        return showResource(s, s2, 0);
    }
    
    public static int showResource(final String s, final String s2, final int n) {
        final MsgBox msgBox = new MsgBox();
        msgBox.setMsgResource(s, s2, n);
        return msgBox.showDialog();
    }
    
    public static int formatResource(final String s, final String s2, final Object[] array) {
        return formatResource(s, s2, 0, array);
    }
    
    public static int formatResource(final String s, final String s2, final int n, final Object[] arguments) {
        final MsgBox msgBox = new MsgBox();
        msgBox.setMsg(MessageFormat.format(((IResourceManager)new ResourceManager((IResourceLoader)msgBox, s)).getString(s2), arguments), n);
        return msgBox.showDialog();
    }
    
    public void setMsgResource(final String s, final String s2, final int n) {
        this.setMsg(((IResourceManager)new ResourceManager((IResourceLoader)this, s)).getString(s2), n);
    }
    
    public static void setAbortImage(final Image image) {
        setImage(6, image);
    }
    
    public static Image getAbortImage() {
        return getImage(6);
    }
    
    public static void setMaxWidth(final int \u01ce) {
        MsgBox.\u01ce = \u01ce;
    }
    
    public static int getMaxWidth() {
        return MsgBox.\u01ce;
    }
    
    public static void setButtonWidth(final int \u02a2) {
        MsgBox.\u02a2 = \u02a2;
    }
    
    public static int getButtonWidth() {
        return MsgBox.\u02a2;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    private void \u0118() {
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "MsgBox");
        this.\u03c7.setLocation((Point)((IResourceManager)resourceManager).getObject("btnOk_location"));
        this.\u03c7.setSize((Point)((IResourceManager)resourceManager).getObject("btnOk_size"));
        this.\u03c7.setTabIndex(((IResourceManager)resourceManager).getInt("btnOk_tabIndex"));
        this.\u03c7.setText(((IResourceManager)resourceManager).getString("btnOk_text"));
        this.\u03c7.setDialogResult(1);
        this.\u03c7.setImage((Image)((IResourceManager)resourceManager).getObject("btnOk_image"));
        this.\u011e.setLocation((Point)((IResourceManager)resourceManager).getObject("btnCancel_location"));
        this.\u011e.setSize((Point)((IResourceManager)resourceManager).getObject("btnCancel_size"));
        this.\u011e.setTabIndex(((IResourceManager)resourceManager).getInt("btnCancel_tabIndex"));
        this.\u011e.setText(((IResourceManager)resourceManager).getString("btnCancel_text"));
        this.\u011e.setDialogResult(2);
        this.\u011e.setImage((Image)((IResourceManager)resourceManager).getObject("btnCancel_image"));
        ((Control)this).setFont((Font)((IResourceManager)resourceManager).getObject("this_font"));
        ((Control)this).setTabIndex(((IResourceManager)resourceManager).getInt("this_tabIndex"));
        ((Control)this).setText(((IResourceManager)resourceManager).getString("this_text"));
        this.setAutoScaleBaseSize(new Point(7, 16));
        this.setBorderStyle(3);
        this.setCancelButton((IButtonControl)this.\u011e);
        ((Control)this).setClientSize((Point)((IResourceManager)resourceManager).getObject("this_clientSize"));
        this.setControlBox(((IResourceManager)resourceManager).getBoolean("this_controlBox"));
        this.setShowInTaskbar(false);
        this.setStartPosition(1);
        this.addOnActivate(new EventHandler((Object)this, "_activate"));
        this.\u03c8.setLocation((Point)((IResourceManager)resourceManager).getObject("btnYes_location"));
        this.\u03c8.setSize((Point)((IResourceManager)resourceManager).getObject("btnYes_size"));
        this.\u03c8.setTabIndex(((IResourceManager)resourceManager).getInt("btnYes_tabIndex"));
        this.\u03c8.setText(((IResourceManager)resourceManager).getString("btnYes_text"));
        this.\u03c8.setDialogResult(6);
        this.\u03c8.setImage((Image)((IResourceManager)resourceManager).getObject("btnYes_image"));
        this.\u03c9.setLocation((Point)((IResourceManager)resourceManager).getObject("btnNo_location"));
        this.\u03c9.setSize((Point)((IResourceManager)resourceManager).getObject("btnNo_size"));
        this.\u03c9.setTabIndex(((IResourceManager)resourceManager).getInt("btnNo_tabIndex"));
        this.\u03c9.setText(((IResourceManager)resourceManager).getString("btnNo_text"));
        this.\u03c9.setDialogResult(7);
        this.\u03c9.setImage((Image)((IResourceManager)resourceManager).getObject("btnNo_image"));
        this.\u03ca.setLocation((Point)((IResourceManager)resourceManager).getObject("btnRetry_location"));
        this.\u03ca.setSize((Point)((IResourceManager)resourceManager).getObject("btnRetry_size"));
        this.\u03ca.setTabIndex(((IResourceManager)resourceManager).getInt("btnRetry_tabIndex"));
        this.\u03ca.setText(((IResourceManager)resourceManager).getString("btnRetry_text"));
        this.\u03ca.setDialogResult(4);
        this.\u03ca.setImage((Image)((IResourceManager)resourceManager).getObject("btnRetry_image"));
        this.\u03cb.setLocation((Point)((IResourceManager)resourceManager).getObject("btnIgnore_location"));
        this.\u03cb.setSize((Point)((IResourceManager)resourceManager).getObject("btnIgnore_size"));
        this.\u03cb.setTabIndex(((IResourceManager)resourceManager).getInt("btnIgnore_tabIndex"));
        this.\u03cb.setText(((IResourceManager)resourceManager).getString("btnIgnore_text"));
        this.\u03cb.setDialogResult(5);
        this.\u03cb.setImage((Image)((IResourceManager)resourceManager).getObject("btnIgnore_image"));
        this.\u03cc.setLocation((Point)((IResourceManager)resourceManager).getObject("btnAbort_location"));
        this.\u03cc.setSize((Point)((IResourceManager)resourceManager).getObject("btnAbort_size"));
        this.\u03cc.setTabIndex(((IResourceManager)resourceManager).getInt("btnAbort_tabIndex"));
        this.\u03cc.setText(((IResourceManager)resourceManager).getString("btnAbort_text"));
        this.\u03cc.setDialogResult(3);
        this.\u03cc.setImage((Image)((IResourceManager)resourceManager).getObject("btnAbort_image"));
        this.\u03cd.setLocation((Point)((IResourceManager)resourceManager).getObject("btnCancelYesNo_location"));
        this.\u03cd.setSize((Point)((IResourceManager)resourceManager).getObject("btnCancelYesNo_size"));
        this.\u03cd.setTabIndex(((IResourceManager)resourceManager).getInt("btnCancelYesNo_tabIndex"));
        this.\u03cd.setText(((IResourceManager)resourceManager).getString("btnCancelYesNo_text"));
        this.\u03cd.setDialogResult(2);
        this.\u03cd.setImage((Image)((IResourceManager)resourceManager).getObject("btnCancelYesNo_image"));
        ((Control)this.\u03ce).setLocation((Point)((IResourceManager)resourceManager).getObject("iconBox_location"));
        ((Control)this.\u03ce).setSize((Point)((IResourceManager)resourceManager).getObject("iconBox_size"));
        ((Control)this.\u03ce).setTabIndex(((IResourceManager)resourceManager).getInt("iconBox_tabIndex"));
        ((Control)this.\u03ce).setTabStop(false);
        ((Control)this.\u03ce).setText(((IResourceManager)resourceManager).getString("iconBox_text"));
        ((Control)this.\u03d0).setFont((Font)((IResourceManager)resourceManager).getObject("labelText_font"));
        ((Control)this.\u03d0).setLocation((Point)((IResourceManager)resourceManager).getObject("labelText_location"));
        ((Control)this.\u03d0).setSize((Point)((IResourceManager)resourceManager).getObject("labelText_size"));
        ((Control)this.\u03d0).setTabIndex(((IResourceManager)resourceManager).getInt("labelText_tabIndex"));
        ((Control)this.\u03d0).setTabStop(false);
        ((Control)this.\u03d0).setText(((IResourceManager)resourceManager).getString("labelText_text"));
        ((Control)this.\u03d0).setVisible(((IResourceManager)resourceManager).getBoolean("labelText_visible"));
        this.\u03d1.setLocation((Point)((IResourceManager)resourceManager).getObject("btnYesForAll_location"));
        this.\u03d1.setSize((Point)((IResourceManager)resourceManager).getObject("btnYesForAll_size"));
        this.\u03d1.setTabIndex(((IResourceManager)resourceManager).getInt("btnYesForAll_tabIndex"));
        this.\u03d1.setText(((IResourceManager)resourceManager).getString("btnYesForAll_text"));
        this.\u03d1.setImage((Image)((IResourceManager)resourceManager).getObject("btnYesForAll_image"));
        this.\u03d2.setLocation((Point)((IResourceManager)resourceManager).getObject("btnNoForAll_location"));
        this.\u03d2.setSize((Point)((IResourceManager)resourceManager).getObject("btnNoForAll_size"));
        this.\u03d2.setTabIndex(((IResourceManager)resourceManager).getInt("btnNoForAll_tabIndex"));
        this.\u03d2.setText(((IResourceManager)resourceManager).getString("btnNoForAll_text"));
        this.\u03d2.setImage((Image)((IResourceManager)resourceManager).getObject("btnNoForAll_image"));
        this.setNewControls(new Control[] { this.\u03d2, this.\u03d1, (Control)this.\u03d0, (Control)this.\u03ce, this.\u03cd, this.\u03cc, this.\u03cb, this.\u03ca, this.\u03c9, this.\u03c8, this.\u011e, this.\u03c7 });
    }
    
    public static void setOkImage(final Image image) {
        setImage(0, image);
    }
    
    public static Image getOkImage() {
        return getImage(0);
    }
    
    public static void setCancelImage(final Image image) {
        setImage(1, image);
    }
    
    public static Image getCancelImage() {
        return getImage(1);
    }
    
    public void setMsg(final String s, int n) {
        if (MsgBox.\u02e3 != null) {
            ((Control)this).setText(MsgBox.\u02e3);
        }
        if (MsgBox.\u03c4 != null) {
            ((Control)this).setFont(MsgBox.\u03c4);
        }
        final int n2 = MsgBox.\u01ce - ((Control)this.\u03d0).getLeft() - this.\u03cb.getLeft();
        final Graphics graphics = ((Control)this).createGraphics();
        this.\u01d1 = new TextRect(graphics, s, n2);
        graphics.dispose();
        if ((n & 0x7) == 0x0) {
            n |= 0x0;
        }
        boolean visible = false;
        boolean visible2 = false;
        boolean visible3 = false;
        boolean visible4 = false;
        boolean visible5 = false;
        boolean visible6 = false;
        boolean visible7 = false;
        boolean visible8 = false;
        boolean visible9 = false;
        boolean visible10 = false;
        int n3 = 48;
        int n4 = 0;
        final ImageBtn[] array = new ImageBtn[5];
        int n5 = 0;
        switch (n & 0x7) {
            case 1: {
                visible2 = (visible = true);
                array[0] = this.\u03c7;
                array[1] = this.\u011e;
                n5 = 2;
                break;
            }
            case 2: {
                visible7 = (visible9 = (visible8 = true));
                array[0] = this.\u03cc;
                array[1] = this.\u03ca;
                array[2] = this.\u03cb;
                n5 = 3;
                break;
            }
            case 3: {
                array[n4++] = this.\u03c8;
                if ((n & 0x1000) != 0x0) {
                    array[n4++] = this.\u03d1;
                    visible4 = true;
                }
                array[n4++] = this.\u03c9;
                if ((n & 0x2000) != 0x0) {
                    array[n4++] = this.\u03d2;
                    visible6 = true;
                }
                array[n4++] = this.\u03cd;
                n5 = n4;
                visible5 = (visible3 = (visible10 = true));
                n3 = 32;
                break;
            }
            case 4: {
                array[n4++] = this.\u03c8;
                if ((n & 0x1000) != 0x0) {
                    array[n4++] = this.\u03d1;
                    visible4 = true;
                }
                array[n4++] = this.\u03c9;
                if ((n & 0x2000) != 0x0) {
                    array[n4++] = this.\u03d2;
                    visible6 = true;
                }
                n5 = n4;
                visible5 = (visible3 = true);
                n3 = 32;
                break;
            }
            case 5: {
                array[0] = this.\u03ca;
                array[1] = this.\u011e;
                n5 = 2;
                visible7 = (visible2 = true);
                break;
            }
            default: {
                array[0] = this.\u03c7;
                n5 = 1;
                visible = true;
                this.\u03c6 = this.\u03c7;
                break;
            }
        }
        if ((n & 0x800) != 0x0) {
            this.\u03c6 = array[4];
        }
        else if ((n & 0x400) != 0x0) {
            this.\u03c6 = array[3];
        }
        else if ((n & 0x200) != 0x0) {
            this.\u03c6 = array[2];
        }
        else if ((n & 0x100) != 0x0) {
            this.\u03c6 = array[1];
        }
        else {
            this.\u03c6 = array[0];
        }
        if (this.\u03c6 == null) {
            this.\u03c6 = array[0];
        }
        this.\u03c7.setVisible(visible);
        this.\u011e.setVisible(visible2);
        this.\u03c8.setVisible(visible3);
        this.\u03d1.setVisible(visible4);
        this.\u03c9.setVisible(visible5);
        this.\u03d2.setVisible(visible6);
        this.\u03ca.setVisible(visible7);
        this.\u03cb.setVisible(visible8);
        this.\u03cc.setVisible(visible9);
        this.\u03cd.setVisible(visible10);
        if ((n & 0x70) == 0x0) {
            n |= n3;
        }
        int n6 = 0;
        switch (n & 0x70) {
            case 64: {
                n6 = 32516;
                break;
            }
            case 32: {
                n6 = 32514;
                break;
            }
            case 16: {
                n6 = 32513;
                break;
            }
            default: {
                n6 = 32515;
                break;
            }
        }
        final int loadIcon = User32.LoadIcon(0, n6);
        if (loadIcon != 0) {
            this.\u03ce.setImage((Image)new Icon(loadIcon));
        }
        final ImageBtn[] array2 = { this.\u03c7, this.\u011e, this.\u03c8, this.\u03c9, this.\u03ca, this.\u03cb, this.\u03cc, this.\u03cd, this.\u03d1, this.\u03d2 };
        int n7 = 0;
        do {
            if (MsgBox.\u02a2 != 0) {
                array2[n7].setWidth(MsgBox.\u02a2);
            }
            if (MsgBox.\u02a4) {
                if (MsgBox.\u03c5[n7] == null) {
                    continue;
                }
                array2[n7].setImage(MsgBox.\u03c5[n7]);
            }
            else {
                array2[n7].setImage(null);
            }
        } while (++n7 < 10);
        final Point clientSize = ((Control)this).getClientSize();
        final int n8 = ((Control)this).getWidth() - clientSize.x;
        final int n9 = ((Control)this).getHeight() - clientSize.y;
        final int left = this.\u03cb.getLeft();
        final int n10 = clientSize.y - this.\u03cb.getBottom();
        final int height = this.\u03c7.getHeight();
        final int n11 = 2 * n10 + height + (this.\u03c7.getTop() - ((Control)this.\u03d0).getBottom());
        final int n12 = ((Control)this.\u03d0).getLeft() + left;
        final Point textSize = this.\u01d1.getTextSize();
        if (textSize.y < ((Control)this.\u03ce).getHeight()) {
            textSize.y = ((Control)this.\u03ce).getHeight();
        }
        int n13 = n12 + textSize.x;
        final int n14 = n11 + textSize.y;
        int n15 = (n5 - 1) * left;
        for (int i = 0; i < n5; ++i) {
            n15 += array[i].getWidth();
        }
        final int n16 = n15 + 2 * left;
        if (n13 < n16) {
            n13 = n16;
        }
        final int width = n13 + n8;
        final int height2 = n14 + n9;
        ((Control)this).setWidth(width);
        ((Control)this).setHeight(height2);
        final int n17 = n14 - n10 - height;
        int n18 = (n13 - n15) / 2;
        for (final ImageBtn imageBtn : array) {
            imageBtn.setLocation(n18, n17);
            n18 += imageBtn.getWidth() + left;
        }
        final Rectangle workingArea = SystemInformation.getWorkingArea();
        ((Control)this).setLocation((workingArea.width - width) / 2, (workingArea.height - height2) / 2);
    }
    
    public static void setNoImage(final Image image) {
        setImage(3, image);
    }
    
    protected void _activate(final Object o, final Event event) {
        if (this.\u03c6 != null) {
            this.setAcceptButton((IButtonControl)this.\u03c6);
            this.\u03c6.focus();
        }
    }
    
    public static Image getNoImage() {
        return getImage(3);
    }
    
    public static void setNoForAllImage(final Image image) {
        setImage(9, image);
    }
    
    public static Image getNoForAllImage() {
        return getImage(9);
    }
    
    public static void setIgnoreImage(final Image image) {
        setImage(5, image);
    }
    
    public static Image getIgnoreImage() {
        return getImage(5);
    }
    
    public static void setTitle(final String \u02e3) {
        MsgBox.\u02e3 = \u02e3;
    }
    
    public static String getTitle() {
        return MsgBox.\u02e3;
    }
    
    private static void setImage(final int n, final Image image) {
        MsgBox.\u03c5[n] = image;
    }
    
    private static Image getImage(final int n) {
        return MsgBox.\u03c5[n];
    }
    
    public static void setUseImageButtons(final boolean \u02a4) {
        MsgBox.\u02a4 = \u02a4;
    }
    
    public static boolean getUseImageButtons() {
        return MsgBox.\u02a4;
    }
    
    public static int format(final String s, final Object[] array) {
        return format(s, 0, array);
    }
    
    public static int format(final String pattern, final int n, final Object[] arguments) {
        final MsgBox msgBox = new MsgBox();
        msgBox.setMsg(MessageFormat.format(pattern, arguments), n);
        return msgBox.showDialog();
    }
    
    public MsgBox() {
        this.\u011d = new Container();
        this.\u03c7 = new ImageBtn();
        this.\u011e = new ImageBtn();
        this.\u03c8 = new ImageBtn();
        this.\u03c9 = new ImageBtn();
        this.\u03ca = new ImageBtn();
        this.\u03cb = new ImageBtn();
        this.\u03cc = new ImageBtn();
        this.\u03cd = new ImageBtn();
        this.\u03ce = new PictureBox();
        this.\u03d0 = new Label();
        this.\u03d1 = new ImageBtn();
        this.\u03d2 = new ImageBtn();
        this.\u0118();
        this.\u03d1.setDialogResult(8);
        this.\u03d2.setDialogResult(9);
    }
    
    public MsgBox(final String s, final int n) {
        this();
        this.setMsg(s, n);
    }
    
    public static void setYesImage(final Image image) {
        setImage(2, image);
    }
    
    public static void setMsgFont(final Font \u03c4) {
        MsgBox.\u03c4 = \u03c4;
    }
    
    public static Font getMsgFont() {
        return MsgBox.\u03c4;
    }
    
    public static Image getYesImage() {
        return getImage(2);
    }
    
    public static void setYesForAllImage(final Image image) {
        setImage(8, image);
    }
    
    public static Image getYesForAllImage() {
        return getImage(8);
    }
    
    public static void setRetryImage(final Image image) {
        setImage(4, image);
    }
    
    public static Image getRetryImage() {
        return getImage(4);
    }
    
    public static void setCancelYesNoImage(final Image image) {
        setImage(7, image);
    }
    
    public static Image getCancelYesNoImage() {
        return getImage(7);
    }
    
    static {
        MsgBox.\u02a4 = true;
        MsgBox.\u01ce = 400;
        _cls753A._mth821F();
        MsgBox.\u03c5 = new Image[10];
    }
    
    public static int show(final String s) {
        return show(s, 0);
    }
    
    public static int show(final String s, final int n) {
        final MsgBox msgBox = new MsgBox();
        msgBox.setMsg(s, n);
        return msgBox.showDialog();
    }
    
    public void dispose() {
        super.dispose();
        this.\u011d.dispose();
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        this.\u01d1.paint(paintEvent.graphics, ((Control)this.\u03d0).getLocation());
    }
}
