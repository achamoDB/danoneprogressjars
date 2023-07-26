// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.ui.Form;
import com.ms.wfc.ui.Menu;
import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.ScrollEvent;
import com.ms.wfc.ui.MessageBox;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.ScrollBar;
import com.ms.wfc.ui.ToolBarButtonClickEventHandler;
import com.ms.wfc.ui.ScrollEventHandler;
import com.ms.wfc.ui.ImageListStreamer;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.Event;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.ToolBarButtonClickEvent;
import com.ms.wfc.ui.ContextMenu;
import com.ms.wfc.ui.MenuItem;
import com.ms.wfc.ui.HScrollBar;
import com.ms.wfc.ui.VScrollBar;
import com.ms.wfc.ui.StatusBar;
import com.ms.wfc.ui.ImageList;
import com.ms.wfc.ui.ToolBarButton;
import com.ms.wfc.ui.ToolBar;
import com.ms.wfc.ui.Panel;
import com.ms.wfc.core.Container;
import com.mim.wfc.ui.PopupText;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.IRequireBegin;
import com.ms.wfc.ui.UserControl;

public class PrintPreviewControl extends UserControl implements IRequireBegin
{
    private boolean \u0134;
    private PrintPreviewEventHandler \u0135;
    private PrintPreviewEventHandler \u0136;
    private EventHandler \u0137;
    private EventHandler \u0138;
    private EventHandler \u0139;
    private boolean \u013a;
    private PopupText \u013b;
    private String \u013c;
    private String \u013d;
    private String \u013e;
    private String \u013f;
    private String \u0140;
    Container \u011d;
    PrintPreviewPanel \u0141;
    Panel \u0142;
    ToolBar \u0143;
    ToolBarButton \u0144;
    ToolBarButton \u0145;
    ToolBarButton \u0146;
    ToolBarButton \u0147;
    ToolBarButton \u0148;
    ToolBarButton \u0149;
    ToolBarButton \u014a;
    ImageList \u014b;
    ToolBarButton \u014c;
    ToolBarButton \u014d;
    ToolBarButton \u014e;
    ToolBarButton \u014f;
    StatusBar \u0150;
    ToolBarButton \u0151;
    ToolBarButton \u0152;
    Panel \u0153;
    VScrollBar \u0154;
    HScrollBar \u0155;
    Panel \u0156;
    MenuItem \u0157;
    ContextMenu \u0158;
    MenuItem \u0159;
    MenuItem \u015a;
    MenuItem \u015b;
    MenuItem \u015c;
    MenuItem \u015d;
    MenuItem \u015e;
    MenuItem \u015f;
    MenuItem \u0160;
    ToolBarButton \u0161;
    ToolBarButton \u0162;
    
    public void setDocTitle(final String docTitle) {
        this.\u0141.setDocTitle(docTitle);
    }
    
    protected void toolBar_buttonDropDown(final Object o, final ToolBarButtonClickEvent toolBarButtonClickEvent) {
        final ToolBarButton button = toolBarButtonClickEvent.button;
        if (button.equals(this.\u0151)) {
            final Rectangle rect = this.\u0151.getRect();
            this.\u0158.show((Control)this.\u0143, new Point(rect.x - 1, rect.y + rect.height));
            return;
        }
        if (button.equals(this.\u0146)) {
            final Rectangle rect2 = this.\u0146.getRect();
            final PreviewLayoutMenu previewLayoutMenu = new PreviewLayoutMenu();
            if (previewLayoutMenu.show((Control)this.\u0143, new Point(rect2.x - 1, rect2.y + rect2.height))) {
                this.setPageLayout(previewLayoutMenu.getPages());
            }
        }
    }
    
    public void addOnDrawPage(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0135 = (PrintPreviewEventHandler)Delegate.combine((Delegate)this.\u0135, (Delegate)printPreviewEventHandler);
    }
    
    public void setPrintImmediate(final boolean \u0135) {
        this.\u0134 = \u0135;
    }
    
    public boolean getPrintImmediate() {
        return this.\u0134;
    }
    
    public void setBorderWidth(final int borderWidth) {
        this.\u0141.setBorderWidth(borderWidth);
    }
    
    public int getBorderWidth() {
        return this.\u0141.getBorderWidth();
    }
    
    public void addOnClose(final EventHandler eventHandler) {
        this.\u0139 = (EventHandler)Delegate.combine((Delegate)this.\u0139, (Delegate)eventHandler);
    }
    
    public void removeOnQueryPageCount(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0136 = (PrintPreviewEventHandler)Delegate.remove((Delegate)this.\u0136, (Delegate)printPreviewEventHandler);
    }
    
    protected void printPreviewPanel_pageSelected(final Object o, final Event event) {
        final int pageCount = this.getPageCount();
        final int activePage = this.getActivePage();
        this.\u0147.setEnabled(activePage != 1);
        this.\u0148.setEnabled(activePage != 1);
        this.\u0149.setEnabled(activePage != pageCount);
        this.\u014a.setEnabled(activePage != pageCount);
        this.onPageSelected(event);
        this.\u0134();
    }
    
    public void setPageColor(final Color pageColor) {
        this.\u0141.setPageColor(pageColor);
    }
    
    public Color getPageColor() {
        return this.\u0141.getPageColor();
    }
    
    public void setActiveFrameColor(final Color activeFrameColor) {
        this.\u0141.setActiveFrameColor(activeFrameColor);
    }
    
    public Color getActiveFrameColor() {
        return this.\u0141.getActiveFrameColor();
    }
    
    public void addOnPageSelected(final EventHandler eventHandler) {
        this.\u0137 = (EventHandler)Delegate.combine((Delegate)this.\u0137, (Delegate)eventHandler);
    }
    
    private void \u0134() {
        this.\u0150.setText(this.\u013c + this.getActivePage() + this.\u013d + this.getPageCount() + this.\u013e + this.getZoomFactor() + "%");
    }
    
    private void \u0135() {
        if (this.\u013b != null) {
            this.\u013b.remove();
            this.\u013b = null;
        }
    }
    
    protected void menuItem50_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(50);
    }
    
    protected void printPreviewPanel_drawPage(final Object o, final PrintPreviewEvent printPreviewEvent) {
        this.onDrawPage(printPreviewEvent);
    }
    
    protected void onDrawPage(final PrintPreviewEvent printPreviewEvent) {
        if (this.\u0135 != null) {
            this.\u0135.invoke(this, printPreviewEvent);
        }
    }
    
    protected void onPageLayoutChanged(final Event event) {
        if (this.\u0138 != null) {
            this.\u0138.invoke((Object)this, event);
        }
    }
    
    public void setPrinter(final Printer printer) {
        this.\u0141.setPrinter(printer);
    }
    
    public Printer getPrinter() {
        return this.\u0141.getPrinter();
    }
    
    public void dispose() {
        super.dispose();
        this.\u011d.dispose();
    }
    
    protected void onClose(final Event event) {
        if (this.\u0139 != null) {
            this.\u0139.invoke((Object)this, event);
        }
    }
    
    public void addOnQueryPageCount(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0136 = (PrintPreviewEventHandler)Delegate.combine((Delegate)this.\u0136, (Delegate)printPreviewEventHandler);
    }
    
    protected void menuItem75_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(75);
    }
    
    protected void menuItem25_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(25);
    }
    
    protected void menuItem10_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(10);
    }
    
    protected void menuItemWholePage_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(-2);
    }
    
    public void setPageLayout(final Point pageLayout) {
        this.\u0141.setPageLayout(pageLayout);
    }
    
    public Point getPageLayout() {
        return this.\u0141.getPageLayout();
    }
    
    protected void onPageSelected(final Event event) {
        if (this.\u0137 != null) {
            this.\u0137.invoke((Object)this, event);
        }
    }
    
    private void \u0118() {
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "PrintPreviewControl");
        ((Control)this).setSize((Point)((IResourceManager)resourceManager).getObject("this_size"));
        ((Control)this).setText(((IResourceManager)resourceManager).getString("this_text"));
        ((Control)this.\u0141).setBackColor(Color.CONTROLDARK);
        ((Control)this.\u0141).setCursor(Cursor.ARROW);
        ((Control)this.\u0141).setDock(5);
        ((Control)this.\u0141).setLocation((Point)((IResourceManager)resourceManager).getObject("printPreviewPanel_location"));
        ((Control)this.\u0141).setSize((Point)((IResourceManager)resourceManager).getObject("printPreviewPanel_size"));
        ((Control)this.\u0141).setTabIndex(((IResourceManager)resourceManager).getInt("printPreviewPanel_tabIndex"));
        this.\u0141.setBorderStyle(1);
        this.\u0141.setZoomFactor(22);
        this.\u0141.setPageCount(50);
        this.\u0141.addOnDrawPage(new PrintPreviewEventHandler(this, "printPreviewPanel_drawPage"));
        this.\u0141.addOnQueryPageCount(new PrintPreviewEventHandler(this, "printPreviewPanel_queryPageCount"));
        this.\u0141.addOnPageSelected(new EventHandler((Object)this, "printPreviewPanel_pageSelected"));
        this.\u0141.addOnPageLayoutChanged(new EventHandler((Object)this, "printPreviewPanel_pageLayoutChanged"));
        ((Control)this.\u0142).setDock(1);
        ((Control)this.\u0142).setSize((Point)((IResourceManager)resourceManager).getObject("panelToolBar_size"));
        ((Control)this.\u0142).setTabIndex(((IResourceManager)resourceManager).getInt("panelToolBar_tabIndex"));
        ((Control)this.\u0142).setText(((IResourceManager)resourceManager).getString("panelToolBar_text"));
        this.\u0144.setImageIndex(0);
        this.\u0144.setText(((IResourceManager)resourceManager).getString("tbbPrint_text"));
        this.\u0144.setToolTipText(((IResourceManager)resourceManager).getString("tbbPrint_toolTipText"));
        this.\u0145.setImageIndex(3);
        this.\u0145.setText(((IResourceManager)resourceManager).getString("tbbSingle_text"));
        this.\u0145.setToolTipText(((IResourceManager)resourceManager).getString("tbbSingle_toolTipText"));
        this.\u0146.setImageIndex(4);
        this.\u0146.setStyle(4);
        this.\u0146.setText(((IResourceManager)resourceManager).getString("tbbDouble_text"));
        this.\u0146.setToolTipText(((IResourceManager)resourceManager).getString("tbbDouble_toolTipText"));
        this.\u0147.setImageIndex(5);
        this.\u0147.setText(((IResourceManager)resourceManager).getString("tbbFirst_text"));
        this.\u0147.setToolTipText(((IResourceManager)resourceManager).getString("tbbFirst_toolTipText"));
        this.\u0148.setImageIndex(6);
        this.\u0148.setText(((IResourceManager)resourceManager).getString("tbbPrev_text"));
        this.\u0148.setToolTipText(((IResourceManager)resourceManager).getString("tbbPrev_toolTipText"));
        this.\u0149.setImageIndex(7);
        this.\u0149.setText(((IResourceManager)resourceManager).getString("tbbNext_text"));
        this.\u0149.setToolTipText(((IResourceManager)resourceManager).getString("tbbNext_toolTipText"));
        this.\u014a.setImageIndex(8);
        this.\u014a.setText(((IResourceManager)resourceManager).getString("tbbLast_text"));
        this.\u014a.setToolTipText(((IResourceManager)resourceManager).getString("tbbLast_toolTipText"));
        this.\u014b.setImageSize(new Point(19, 19));
        this.\u014b.setImageStream((ImageListStreamer)((IResourceManager)resourceManager).getObject("imageListToolBar_imageStream"));
        this.\u014c.setImageIndex(9);
        this.\u014c.setText(((IResourceManager)resourceManager).getString("tbbClose_text"));
        this.\u014c.setToolTipText(((IResourceManager)resourceManager).getString("tbbClose_toolTipText"));
        this.\u014d.setStyle(3);
        this.\u014e.setStyle(3);
        this.\u014f.setStyle(3);
        this.\u0150.setBackColor(Color.CONTROL);
        ((Control)this.\u0150).setLocation((Point)((IResourceManager)resourceManager).getObject("statusBar_location"));
        ((Control)this.\u0150).setSize((Point)((IResourceManager)resourceManager).getObject("statusBar_size"));
        ((Control)this.\u0150).setTabIndex(((IResourceManager)resourceManager).getInt("statusBar_tabIndex"));
        this.\u0150.setText(((IResourceManager)resourceManager).getString("statusBar_text"));
        this.\u0151.setImageIndex(2);
        this.\u0151.setStyle(4);
        this.\u0151.setText(((IResourceManager)resourceManager).getString("tbbZoom_text"));
        this.\u0151.setToolTipText(((IResourceManager)resourceManager).getString("tbbZoom_toolTipText"));
        this.\u0152.setStyle(3);
        ((Control)this.\u0153).setCursor(Cursor.ARROW);
        ((Control)this.\u0153).setDock(2);
        ((Control)this.\u0153).setLocation((Point)((IResourceManager)resourceManager).getObject("panelHScrollBar_location"));
        ((Control)this.\u0153).setSize((Point)((IResourceManager)resourceManager).getObject("panelHScrollBar_size"));
        ((Control)this.\u0153).setTabIndex(((IResourceManager)resourceManager).getInt("panelHScrollBar_tabIndex"));
        ((Control)this.\u0153).setText(((IResourceManager)resourceManager).getString("panelHScrollBar_text"));
        ((Control)this.\u0154).setCursor(Cursor.ARROW);
        ((Control)this.\u0154).setDock(4);
        ((Control)this.\u0154).setLocation((Point)((IResourceManager)resourceManager).getObject("vScrollBar_location"));
        ((Control)this.\u0154).setSize((Point)((IResourceManager)resourceManager).getObject("vScrollBar_size"));
        ((Control)this.\u0154).setTabIndex(((IResourceManager)resourceManager).getInt("vScrollBar_tabIndex"));
        ((Control)this.\u0154).setText(((IResourceManager)resourceManager).getString("vScrollBar_text"));
        ((ScrollBar)this.\u0154).addOnScroll(new ScrollEventHandler((Object)this, "vScrollBar_scroll"));
        ((Control)this.\u0155).setCursor(Cursor.ARROW);
        ((Control)this.\u0155).setDock(5);
        ((Control)this.\u0155).setSize((Point)((IResourceManager)resourceManager).getObject("hScrollBar_size"));
        ((Control)this.\u0155).setTabIndex(((IResourceManager)resourceManager).getInt("hScrollBar_tabIndex"));
        ((Control)this.\u0155).setText(((IResourceManager)resourceManager).getString("hScrollBar_text"));
        ((ScrollBar)this.\u0155).addOnScroll(new ScrollEventHandler((Object)this, "hScrollBar_scroll"));
        ((Control)this.\u0156).setBackColor(Color.WINDOW);
        ((Control)this.\u0156).setCursor(Cursor.ARROW);
        ((Control)this.\u0156).setDock(4);
        ((Control)this.\u0156).setLocation((Point)((IResourceManager)resourceManager).getObject("panelScrollBarCorner_location"));
        ((Control)this.\u0156).setSize((Point)((IResourceManager)resourceManager).getObject("panelScrollBarCorner_size"));
        ((Control)this.\u0156).setTabIndex(((IResourceManager)resourceManager).getInt("panelScrollBarCorner_tabIndex"));
        ((Control)this.\u0156).setText(((IResourceManager)resourceManager).getString("panelScrollBarCorner_text"));
        this.\u0157.setText(((IResourceManager)resourceManager).getString("menuItem500_text"));
        this.\u0157.addOnClick(new EventHandler((Object)this, "menuItem500_click"));
        this.\u0159.setText(((IResourceManager)resourceManager).getString("menuItem200_text"));
        this.\u0159.addOnClick(new EventHandler((Object)this, "menuItem200_click"));
        this.\u015a.setText(((IResourceManager)resourceManager).getString("menuItem100_text"));
        this.\u015a.addOnClick(new EventHandler((Object)this, "menuItem100_click"));
        this.\u015b.setText(((IResourceManager)resourceManager).getString("menuItem75_text"));
        this.\u015b.addOnClick(new EventHandler((Object)this, "menuItem75_click"));
        this.\u015c.setText(((IResourceManager)resourceManager).getString("menuItem50_text"));
        this.\u015c.addOnClick(new EventHandler((Object)this, "menuItem50_click"));
        this.\u015d.setText(((IResourceManager)resourceManager).getString("menuItem25_text"));
        this.\u015d.addOnClick(new EventHandler((Object)this, "menuItem25_click"));
        this.\u015e.setText(((IResourceManager)resourceManager).getString("menuItem10_text"));
        this.\u015e.addOnClick(new EventHandler((Object)this, "menuItem10_click"));
        this.\u015f.setText(((IResourceManager)resourceManager).getString("menuItemPageWidth_text"));
        this.\u015f.addOnClick(new EventHandler((Object)this, "menuItemPageWidth_click"));
        this.\u0160.setText(((IResourceManager)resourceManager).getString("menuItemWholePage_text"));
        this.\u0160.addOnClick(new EventHandler((Object)this, "menuItemWholePage_click"));
        ((Menu)this.\u0158).setMenuItems(new MenuItem[] { this.\u0157, this.\u0159, this.\u015a, this.\u015b, this.\u015c, this.\u015d, this.\u015e, this.\u015f, this.\u0160 });
        this.\u0161.setStyle(3);
        this.\u0162.setImageIndex(1);
        this.\u0162.setText(((IResourceManager)resourceManager).getString("tbbPage_text"));
        this.\u0162.setToolTipText(((IResourceManager)resourceManager).getString("tbbPage_toolTipText"));
        ((Control)this.\u0143).setSize((Point)((IResourceManager)resourceManager).getObject("toolBar_size"));
        ((Control)this.\u0143).setTabIndex(((IResourceManager)resourceManager).getInt("toolBar_tabIndex"));
        this.\u0143.setButtons(new ToolBarButton[] { this.\u0144, this.\u0161, this.\u0162, this.\u014d, this.\u0151, this.\u0152, this.\u0145, this.\u0146, this.\u014e, this.\u0147, this.\u0148, this.\u0149, this.\u014a, this.\u014f, this.\u014c });
        this.\u0143.setButtonSize(new Point(26, 26));
        this.\u0143.setDivider(false);
        this.\u0143.setDropDownArrows(true);
        this.\u0143.setImageList(this.\u014b);
        this.\u0143.setShowToolTips(true);
        this.\u0143.addOnButtonClick(new ToolBarButtonClickEventHandler((Object)this, "toolBar_buttonClick"));
        this.\u0143.addOnButtonDropDown(new ToolBarButtonClickEventHandler((Object)this, "toolBar_buttonDropDown"));
        ((Form)this).setNewControls(new Control[] { (Control)this.\u0154, (Control)this.\u0153, (Control)this.\u0150, (Control)this.\u0142, (Control)this.\u0141 });
        ((Control)this.\u0142).setNewControls(new Control[] { (Control)this.\u0143 });
        ((Control)this.\u0153).setNewControls(new Control[] { (Control)this.\u0155, (Control)this.\u0156 });
        this.\u0141.begin();
    }
    
    public void setPageDistance(final int pageDistance) {
        this.\u0141.setPageDistance(pageDistance);
    }
    
    public int getPageDistance() {
        return this.\u0141.getPageDistance();
    }
    
    public void setZoomFactor(final int zoomFactor) {
        this.\u0141.setZoomFactor(zoomFactor);
    }
    
    public int getZoomFactor() {
        return this.\u0141.getZoomFactor();
    }
    
    public void setActivePage(final int activePage) {
        this.\u0141.setActivePage(activePage);
    }
    
    public int getActivePage() {
        return this.\u0141.getActivePage();
    }
    
    public void setFirstPage(final int firstPage) {
        this.\u0141.setFirstPage(firstPage);
    }
    
    private void \u0136(final ScrollBar scrollBar, final int minimum, final int maximum, final int value, final int smallChange, final int largeChange) {
        scrollBar.setMinimum(minimum);
        scrollBar.setMaximum(maximum);
        scrollBar.setValue(value);
        scrollBar.setSmallChange(smallChange);
        scrollBar.setLargeChange(largeChange);
        ((Control)scrollBar).setEnabled(true);
    }
    
    public int getFirstPage() {
        return this.\u0141.getFirstPage();
    }
    
    public void setXOffset(final int xOffset) {
        this.\u0141.setXOffset(xOffset);
    }
    
    public int getXOffset() {
        return this.\u0141.getXOffset();
    }
    
    public void removeOnDrawPage(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0135 = (PrintPreviewEventHandler)Delegate.remove((Delegate)this.\u0135, (Delegate)printPreviewEventHandler);
    }
    
    public void removeOnPageLayoutChanged(final EventHandler eventHandler) {
        this.\u0138 = (EventHandler)Delegate.remove((Delegate)this.\u0138, (Delegate)eventHandler);
    }
    
    public PrintPreviewPanel getPrintPreviewPanel() {
        return this.\u0141;
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        super.onKeyDown(keyEvent);
        final int keyCode = keyEvent.getKeyCode();
        if (keyCode == 27) {
            this.onClose(Event.EMPTY);
            this.dispose();
            return;
        }
        if (keyCode == 34 || keyCode == 99) {
            this.\u0141.showNextPage();
            return;
        }
        if (keyCode == 33 || keyCode == 105) {
            this.\u0141.showPreviousPage();
            return;
        }
        if (keyCode == 36 || keyCode == 103) {
            this.\u0141.showFirstPage();
            return;
        }
        if (keyCode == 35 || keyCode == 97) {
            this.\u0141.showLastPage();
            return;
        }
        if (keyCode == 251) {
            this.\u0141.toggleZoom();
        }
    }
    
    public void removeOnClose(final EventHandler eventHandler) {
        this.\u0139 = (EventHandler)Delegate.remove((Delegate)this.\u0139, (Delegate)eventHandler);
    }
    
    public void begin() {
        this.\u0141.begin2();
    }
    
    protected void printPreviewPanel_pageLayoutChanged(final Object o, final Event event) {
        this.onPageLayoutChanged(event);
        this.\u0134();
        final Rectangle clientRect = ((Control)this.\u0141).getClientRect();
        final Point point = new Point(clientRect.width, clientRect.height);
        final Point pageSize = this.\u0141.getPageSize();
        final int borderWidth = this.\u0141.getBorderWidth();
        final int n = pageSize.x + 2 * borderWidth;
        final int n2 = pageSize.y + 2 * borderWidth;
        if (n > point.x) {
            this.\u0136((ScrollBar)this.\u0155, 0, n, 0, point.x / 5, point.x);
        }
        else {
            ((Control)this.\u0155).setEnabled(false);
        }
        final Point pageLayout = this.getPageLayout();
        final int n3 = pageLayout.x * pageLayout.y;
        final int pageCount = this.getPageCount();
        if (pageCount > n3) {
            this.getActivePage();
            if (pageSize.y > point.y) {
                this.\u013a = false;
                this.\u0136((ScrollBar)this.\u0154, 1, (pageCount + n3 - 1) * 10, this.\u0141.getActivePage() * 10, 1, 10);
                return;
            }
            this.\u013a = true;
            this.\u0136((ScrollBar)this.\u0154, 1, pageCount + n3 - 1, this.\u0141.getActivePage(), 1, n3);
        }
        else {
            if (n2 > point.y) {
                this.\u013a = false;
                this.\u0136((ScrollBar)this.\u0154, 0, n2, 0, point.y / 5, point.y);
                return;
            }
            ((Control)this.\u0154).setEnabled(false);
        }
    }
    
    public void setPageCount(final int pageCount) {
        this.\u0141.setPageCount(pageCount);
    }
    
    public int getPageCount() {
        return this.\u0141.getPageCount();
    }
    
    public void setYOffset(final int yOffset) {
        this.\u0141.setYOffset(yOffset);
    }
    
    public PrintPreviewControl() {
        this.\u011d = new Container();
        this.\u0141 = new PrintPreviewPanel();
        this.\u0142 = new Panel();
        this.\u0143 = new ToolBar();
        this.\u0144 = new ToolBarButton();
        this.\u0145 = new ToolBarButton();
        this.\u0146 = new ToolBarButton();
        this.\u0147 = new ToolBarButton();
        this.\u0148 = new ToolBarButton();
        this.\u0149 = new ToolBarButton();
        this.\u014a = new ToolBarButton();
        this.\u014b = new ImageList();
        this.\u014c = new ToolBarButton();
        this.\u014d = new ToolBarButton();
        this.\u014e = new ToolBarButton();
        this.\u014f = new ToolBarButton();
        this.\u0150 = new StatusBar();
        this.\u0151 = new ToolBarButton();
        this.\u0152 = new ToolBarButton();
        this.\u0153 = new Panel();
        this.\u0154 = new VScrollBar();
        this.\u0155 = new HScrollBar();
        this.\u0156 = new Panel();
        this.\u0157 = new MenuItem();
        this.\u0158 = new ContextMenu();
        this.\u0159 = new MenuItem();
        this.\u015a = new MenuItem();
        this.\u015b = new MenuItem();
        this.\u015c = new MenuItem();
        this.\u015d = new MenuItem();
        this.\u015e = new MenuItem();
        this.\u015f = new MenuItem();
        this.\u0160 = new MenuItem();
        this.\u0161 = new ToolBarButton();
        this.\u0162 = new ToolBarButton();
        this.\u0141.requireBegin2();
        this.\u0118();
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "PrintPreview");
        this.\u013c = ((IResourceManager)resourceManager).getString("textStatus1");
        this.\u013d = ((IResourceManager)resourceManager).getString("textStatus2");
        this.\u013e = ((IResourceManager)resourceManager).getString("textStatus3");
        this.\u013f = ((IResourceManager)resourceManager).getString("textScroll");
        this.\u0140 = ((IResourceManager)resourceManager).getString("msgError");
        this.\u015f.setText(((IResourceManager)resourceManager).getString("textPageWidth"));
        this.\u0160.setText(((IResourceManager)resourceManager).getString("textEntirePage"));
    }
    
    protected void printPreviewPanel_queryPageCount(final Object o, final PrintPreviewEvent printPreviewEvent) {
        this.onQueryPageCount(printPreviewEvent);
    }
    
    public int getYOffset() {
        return this.\u0141.getYOffset();
    }
    
    protected void onQueryPageCount(final PrintPreviewEvent printPreviewEvent) {
        if (this.\u0136 != null) {
            this.\u0136.invoke(this, printPreviewEvent);
        }
    }
    
    public void removeOnPageSelected(final EventHandler eventHandler) {
        this.\u0137 = (EventHandler)Delegate.remove((Delegate)this.\u0137, (Delegate)eventHandler);
    }
    
    public void addOnPageLayoutChanged(final EventHandler eventHandler) {
        this.\u0138 = (EventHandler)Delegate.combine((Delegate)this.\u0138, (Delegate)eventHandler);
    }
    
    protected void toolBar_buttonClick(final Object o, final ToolBarButtonClickEvent toolBarButtonClickEvent) {
        final ToolBarButton button = toolBarButtonClickEvent.button;
        if (button.equals(this.\u0144)) {
            try {
                if (this.\u0134) {
                    this.\u0141.printNoDialog(1, this.getPageCount());
                    return;
                }
                this.\u0141.print();
                return;
            }
            catch (PrinterException ex) {
                MessageBox.show(this.\u0140 + ex.toString());
                return;
            }
        }
        if (button.equals(this.\u0162)) {
            this.\u0141.showPageSetupDialog();
            return;
        }
        if (button.equals(this.\u0145)) {
            this.setPageLayout(new Point(1, 1));
            return;
        }
        if (button.equals(this.\u0146)) {
            this.setPageLayout(new Point(2, 1));
            return;
        }
        if (button.equals(this.\u0147)) {
            this.\u0141.showFirstPage();
            return;
        }
        if (button.equals(this.\u0148)) {
            this.\u0141.showPreviousPage();
            return;
        }
        if (button.equals(this.\u0149)) {
            this.\u0141.showNextPage();
            return;
        }
        if (button.equals(this.\u014a)) {
            this.\u0141.showLastPage();
            return;
        }
        if (button.equals(this.\u0151)) {
            this.\u0141.toggleZoom();
            return;
        }
        if (button.equals(this.\u014c)) {
            this.onClose(Event.EMPTY);
            this.dispose();
        }
    }
    
    protected void menuItem500_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(500);
    }
    
    protected void menuItem200_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(200);
    }
    
    protected void hScrollBar_scroll(final Object o, final ScrollEvent scrollEvent) {
        if (scrollEvent.type == 8) {
            this.setXOffset(scrollEvent.newValue);
        }
    }
    
    protected void vScrollBar_scroll(final Object o, final ScrollEvent scrollEvent) {
        final int pageCount = this.getPageCount();
        if (this.\u013a) {
            int newValue = scrollEvent.newValue;
            if (newValue > pageCount) {
                newValue = pageCount;
            }
            if (scrollEvent.type == 5) {
                this.\u0137(newValue);
                return;
            }
            if (scrollEvent.type != 8) {
                return;
            }
            this.\u0135();
            this.setActivePage(newValue);
        }
        else {
            final int newValue2 = scrollEvent.newValue;
            final Point pageLayout = this.getPageLayout();
            if (pageCount <= pageLayout.x * pageLayout.y) {
                if (scrollEvent.type == 8) {
                    this.setYOffset(newValue2);
                }
                return;
            }
            int activePage = newValue2 / 10 + 1;
            if (activePage > pageCount) {
                activePage = pageCount;
            }
            if (scrollEvent.type == 5) {
                this.\u0137(activePage);
                return;
            }
            if (scrollEvent.type != 8) {
                return;
            }
            this.\u0135();
            if (this.getActivePage() != activePage) {
                this.setActivePage(activePage);
                return;
            }
            final Rectangle clientRect = ((Control)this.\u0141).getClientRect();
            this.setYOffset((this.\u0141.getPageSize().y + 2 * this.\u0141.getBorderWidth() - new Point(clientRect.width, clientRect.height).y) * (newValue2 % 10) / 9);
        }
    }
    
    protected void menuItem100_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(100);
    }
    
    protected void menuItemPageWidth_click(final Object o, final Event event) {
        this.\u0141.setZoomFactor(-1);
    }
    
    private void \u0137(final int i) {
        final String string = this.\u013f + i;
        if (this.\u013b == null) {
            (this.\u013b = new PopupText()).setShadowSize(5);
            final Point pointToScreen = ((Control)this.\u0154).pointToScreen(new Point(0, 0));
            final Point mousePosition = Control.getMousePosition();
            mousePosition.x = pointToScreen.x - 15;
            this.\u013b.setText(this.\u013f + "XX");
            this.\u013b.setNewText(string);
            this.\u013b.display(mousePosition, 6);
            return;
        }
        this.\u013b.setNewText(string);
    }
    
    public String getDocTitle() {
        return this.\u0141.getDocTitle();
    }
}
