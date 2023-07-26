// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Menu;
import com.ms.wfc.util.Utils;
import com.ms.wfc.core.IComponentSite;
import com.ms.win32.Shell32;
import com.ms.wfc.ui.CreateParams;
import com.ms.win32.User32;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Command;
import com.ms.win32.TPMPARAMS;
import com.ms.wfc.win32.Windows;
import com.ms.wfc.ui.Control;
import com.ms.wfc.app.Message;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.MouseEvent;
import com.ms.win32.NOTIFYICONDATA;
import com.ms.wfc.ui.MouseEventHandler;
import com.ms.wfc.ui.ContextMenu;
import com.ms.wfc.ui.Icon;
import com.ms.wfc.core.Component;

public class SysTray extends Component
{
    private boolean \u01d4;
    private boolean \u0261;
    private String \u0262;
    private Icon \u0263;
    private ContextMenu \u0264;
    private MouseEventHandler \u0265;
    private MouseEventHandler \u0266;
    private MouseEventHandler \u0267;
    private int \u0268;
    private boolean \u0269;
    private SysTray$IconWindow \u026a;
    private static final int \u026b;
    private static int \u026c;
    private static NOTIFYICONDATA data;
    
    public String getText() {
        return this.\u0262;
    }
    
    public void setContextMenu(final ContextMenu \u0264) {
        this.\u0264 = \u0264;
    }
    
    public ContextMenu getContextMenu() {
        return this.\u0264;
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        if (this.\u0265 != null) {
            this.\u0265.invoke((Object)this, mouseEvent);
        }
    }
    
    public void addOnMouseDown(final MouseEventHandler mouseEventHandler) {
        this.\u0266 = (MouseEventHandler)Delegate.combine((Delegate)this.\u0266, (Delegate)mouseEventHandler);
    }
    
    public void removeOnMouseDown(final MouseEventHandler mouseEventHandler) {
        this.\u0266 = (MouseEventHandler)Delegate.remove((Delegate)this.\u0266, (Delegate)mouseEventHandler);
    }
    
    public SysTray() {
        this.\u0268 = 0;
        this.\u0269 = false;
        this.\u026a = null;
        this.\u0268 = ++SysTray.\u026c;
        this.\u026a = new SysTray$IconWindow(this);
    }
    
    protected void onMouseUp(final MouseEvent mouseEvent) {
        if (this.\u0267 != null) {
            this.\u0267.invoke((Object)this, mouseEvent);
        }
    }
    
    public int getHandle() {
        return this.\u026a.getHandle();
    }
    
    private void \u0261(final Message message) {
        this.onMouseMove(new MouseEvent(Control.getMouseButtons(), 0, 0, 0, 0));
    }
    
    public void setVisible(final boolean \u0261) {
        if (this.\u0261 != \u0261) {
            this.\u0263(\u0261);
            this.\u0261 = \u0261;
        }
    }
    
    protected void wndProc(final Message message) {
        if (message.msg == SysTray.\u026b) {
            switch (message.lParam) {
                case 513: {
                    this.\u0264(message, 1048576, 1);
                }
                case 515: {
                    this.\u0264(message, 1048576, 2);
                }
                case 514: {
                    this.\u0262(message, 1048576);
                }
                case 516: {
                    this.\u0264(message, 2097152, 1);
                    if (this.\u0264 != null) {
                        final Point mousePosition = Control.getMousePosition();
                        Windows.TrackPopupMenuEx(((Menu)this.\u0264).getHandle(), 42, mousePosition.x, mousePosition.y, this.\u026a.getHandle(), (TPMPARAMS)null);
                        return;
                    }
                    break;
                }
                case 518: {
                    this.\u0264(message, 2097152, 2);
                }
                case 517: {
                    this.\u0262(message, 2097152);
                }
                case 519: {
                    this.\u0264(message, 4194304, 1);
                }
                case 521: {
                    this.\u0264(message, 4194304, 2);
                }
                case 520: {
                    this.\u0262(message, 4194304);
                }
                case 512: {
                    this.\u0261(message);
                }
            }
        }
        else if (message.msg == 273 && message.lParam == 0 && Command.dispatchID(message.wParam & 0xFFFF)) {
            return;
        }
    }
    
    public boolean getVisible() {
        return this.\u0261;
    }
    
    public void setIcon(final Icon \u0263) {
        this.\u0263 = \u0263;
        if (this.\u0269) {
            this.\u0263(true);
        }
    }
    
    public Icon getIcon() {
        return this.\u0263;
    }
    
    public void removeOnMouseMove(final MouseEventHandler mouseEventHandler) {
        this.\u0265 = (MouseEventHandler)Delegate.remove((Delegate)this.\u0265, (Delegate)mouseEventHandler);
    }
    
    private void \u0262(final Message message, final int n) {
        this.onMouseUp(new MouseEvent(n, 0, 0, 0, 0));
    }
    
    public void removeOnMouseUp(final MouseEventHandler mouseEventHandler) {
        this.\u0267 = (MouseEventHandler)Delegate.remove((Delegate)this.\u0267, (Delegate)mouseEventHandler);
    }
    
    static {
        \u026b = User32.RegisterWindowMessage("SysTrayNotification");
        SysTray.\u026c = 0;
    }
    
    public void addOnMouseMove(final MouseEventHandler mouseEventHandler) {
        this.\u0265 = (MouseEventHandler)Delegate.combine((Delegate)this.\u0265, (Delegate)mouseEventHandler);
    }
    
    public void finalize() {
        this.\u0263(false);
        this.\u026a.destroyHandle();
    }
    
    private synchronized void \u0263(final boolean b) {
        final IComponentSite componentSite = this.getComponentSite();
        if (componentSite != null && componentSite.getDesignMode()) {
            return;
        }
        if (SysTray.data == null) {
            SysTray.data = new NOTIFYICONDATA();
            SysTray.data.uCallbackMessage = SysTray.\u026b;
            SysTray.data.uFlags = 1;
        }
        if (b) {
            if (this.\u026a.getHandle() == 0) {
                this.\u026a.createHandle(new CreateParams());
            }
            SysTray.data.hWnd = this.\u026a.getHandle();
        }
        SysTray.data.uID = this.\u0268;
        SysTray.data.hIcon = 0;
        SysTray.data.szTip = null;
        if (this.\u0263 != null) {
            final NOTIFYICONDATA data = SysTray.data;
            data.uFlags |= 0x2;
            try {
                SysTray.data.hIcon = this.\u0263.getHandle();
            }
            catch (Exception ex) {}
        }
        if (this.\u0262 != null) {
            final NOTIFYICONDATA data2 = SysTray.data;
            data2.uFlags |= 0x4;
            SysTray.data.szTip = this.\u0262;
        }
        if (!b) {
            if (this.\u0269) {
                Shell32.Shell_NotifyIcon(2, SysTray.data);
                this.\u0269 = false;
            }
            return;
        }
        if (!this.\u0269) {
            Shell32.Shell_NotifyIcon(0, SysTray.data);
            this.\u0269 = true;
            return;
        }
        Shell32.Shell_NotifyIcon(1, SysTray.data);
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        if (this.\u0266 != null) {
            this.\u0266.invoke((Object)this, mouseEvent);
        }
    }
    
    public void setEnabled(final boolean \u01d4) {
        this.\u01d4 = \u01d4;
    }
    
    public boolean getEnabled() {
        return this.\u01d4;
    }
    
    public void addOnMouseUp(final MouseEventHandler mouseEventHandler) {
        this.\u0267 = (MouseEventHandler)Delegate.combine((Delegate)this.\u0267, (Delegate)mouseEventHandler);
    }
    
    public void setText(final String \u0262) {
        if (!Utils.equals((Object)this.\u0262, (Object)\u0262)) {
            this.\u0262 = \u0262;
            if (this.\u0269) {
                this.\u0263(true);
            }
        }
    }
    
    private void \u0264(final Message message, final int n, final int n2) {
        this.onMouseDown(new MouseEvent(n, n2, 0, 0, 0));
    }
}
