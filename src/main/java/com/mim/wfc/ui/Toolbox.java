// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Component;
import com.ms.wfc.ui.KeyPressEvent;
import com.ms.wfc.app.Message;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.LayoutEvent;
import com.ms.wfc.core.Event;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Point;

public class Toolbox extends ViewPanel implements IToolbox
{
    private ToolboxItem \u0275;
    private ToolboxItem \u0276;
    private ToolboxItem \u0277;
    private boolean \u0278;
    private ToolboxItem[] \u0279;
    private Point \u027a;
    private Point \u027b;
    private Point \u027c;
    private Point \u027d;
    private int \u027e;
    private boolean \u027f;
    private boolean \u0280;
    private boolean \u0281;
    private int \u0198;
    private int \u0282;
    private int \u0283;
    private int page;
    private EventHandler \u0284;
    private EventHandler \u0285;
    private EventHandler \u0286;
    private static Class \u0287;
    
    public void setItemList(final ToolboxItem[] \u0279) {
        this.\u0279 = \u0279;
        if (\u0279 != null) {
            for (int i = 0; i < \u0279.length; ++i) {
                \u0279[i].\u028b(this);
            }
        }
        this.\u0277();
    }
    
    public ToolboxItem[] getItemList() {
        return this.\u0279;
    }
    
    private void scrollIntoView(final ToolboxItem toolboxItem) {
        final Point scrollOffset = this.getScrollOffset();
        final Point clientSize = ((Control)this).getClientSize();
        final Rectangle rectangle = new Rectangle(scrollOffset.x, scrollOffset.y, clientSize.x, clientSize.y);
        final Rectangle \u028e = toolboxItem.\u028e;
        if (Rectangle.union(rectangle, \u028e).equals((Object)rectangle)) {
            return;
        }
        if (\u028e.width > rectangle.width || \u028e.x < rectangle.x) {
            scrollOffset.x = \u028e.x;
        }
        else if (\u028e.x + \u028e.width > rectangle.x + rectangle.width) {
            scrollOffset.x = \u028e.x + \u028e.width - rectangle.width;
        }
        if (\u028e.height > rectangle.height || \u028e.y < rectangle.y) {
            scrollOffset.y = \u028e.y;
        }
        else if (\u028e.y + \u028e.height > rectangle.y + rectangle.height) {
            scrollOffset.y = \u028e.y + \u028e.height - rectangle.height;
        }
        this.getDocSize();
        if (scrollOffset.x <= this.\u027d.x) {
            scrollOffset.x = 0;
        }
        if (scrollOffset.y <= this.\u027d.y) {
            scrollOffset.y = 0;
        }
        this.setScrollOffset(scrollOffset);
    }
    
    public void addOnItemReleased(final EventHandler eventHandler) {
        this.\u0285 = (EventHandler)Delegate.combine((Delegate)this.\u0285, (Delegate)eventHandler);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        ((Control)this).performLayout();
    }
    
    private void clearSelection() {
        if (this.\u0279 == null) {
            return;
        }
        for (int i = 0; i < this.\u0279.length; ++i) {
            final ToolboxItem toolboxItem = this.\u0279[i];
            toolboxItem.getSelected();
            toolboxItem.setSelected(false);
        }
    }
    
    protected void onLayout(final LayoutEvent layoutEvent) {
        super.onLayout(layoutEvent);
        if (((Control)this).getHandleCreated()) {
            this.\u0277();
        }
    }
    
    public void removeOnItemCurrent(final EventHandler eventHandler) {
        this.\u0286 = (EventHandler)Delegate.remove((Delegate)this.\u0286, (Delegate)eventHandler);
    }
    
    public void setCurrentItemByChar(final char ch) {
        if (this.\u0276 == null) {
            return;
        }
        final int itemIndex = this.findItemIndex(this.\u0276);
        if (itemIndex < 0) {
            return;
        }
        final boolean lowerCase = Character.isLowerCase(ch);
        final String string = "" + Character.toLowerCase(ch);
        final String string2 = "" + Character.toUpperCase(ch);
        int n = -1;
        final int length = this.\u0279.length;
        int n2 = itemIndex;
        while (true) {
            String text;
            do {
                if (lowerCase) {
                    ++n2;
                }
                else {
                    --n2;
                }
                if (n2 < 0 || n2 >= length) {
                    if (n < 0) {
                        int n3 = lowerCase ? 0 : (length - 1);
                        do {
                            final String text2 = this.\u0279[n3].getText();
                            if (text2 != null && (text2.startsWith(string) || text2.startsWith(string2))) {
                                n = n3;
                                break;
                            }
                            if (lowerCase) {
                                ++n3;
                            }
                            else {
                                --n3;
                            }
                        } while (n3 >= 0 && n3 < length);
                    }
                    if (n >= 0) {
                        this.setCurrentItem(this.\u0279[n]);
                    }
                    return;
                }
                text = this.\u0279[n2].getText();
            } while (text == null || (!text.startsWith(string) && !text.startsWith(string2)));
            n = n2;
            continue;
        }
    }
    
    public void setItemBorderSize(final Point \u027b) {
        this.\u027b = \u027b;
        ((Control)this).invalidate();
    }
    
    public Point getItemBorderSize() {
        return this.\u027b;
    }
    
    public void setSelectedIndices(final int[] array) {
        this.clearSelection();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.setSelectedIndex(array[i], true);
            }
        }
    }
    
    public int[] getSelectedIndices() {
        final int \u0276 = this.\u0276();
        if (\u0276 == 0) {
            return null;
        }
        final int[] array = new int[\u0276];
        int n = 0;
        for (int i = 0; i < this.\u0279.length; ++i) {
            if (this.\u0279[i].getSelected()) {
                array[n++] = i;
            }
        }
        return array;
    }
    
    public ToolboxItem getCurrentItem() {
        return this.\u0276;
    }
    
    public void setCurrentItem(final ToolboxItem \u0276) {
        if (this.\u0276 != null) {
            this.invalidateItem(this.\u0276);
        }
        if ((this.\u0276 = \u0276) != null) {
            this.scrollIntoView(\u0276);
            this.invalidateItem(\u0276);
            \u0276.onItemCurrent(Event.EMPTY);
        }
        this.onItemCurrent(Event.EMPTY);
    }
    
    public void insertItem(final ToolboxItem toolboxItem, final ToolboxItem toolboxItem2) {
        final int itemIndex = this.findItemIndex(toolboxItem2);
        if (itemIndex < 0) {
            return;
        }
        if (toolboxItem != null) {
            final ToolboxItem[] \u0279 = new ToolboxItem[((this.\u0279 != null) ? this.\u0279.length : 0) + 1];
            if (this.\u0279 != null) {
                System.arraycopy(this.\u0279, 0, \u0279, 0, itemIndex);
                System.arraycopy(this.\u0279, itemIndex, \u0279, itemIndex + 1, this.\u0279.length - itemIndex);
            }
            \u0279[itemIndex] = toolboxItem;
            this.\u0279 = \u0279;
            toolboxItem.\u028b(this);
        }
        this.\u0277();
    }
    
    public void invalidateItem(final ToolboxItem toolboxItem) {
        this.invalidateDoc(toolboxItem.\u028e);
        if (this.\u0281) {
            final Image image = toolboxItem.getImage();
            if (image != null) {
                final Point size = image.getSize();
                final Rectangle rectangle = new Rectangle(toolboxItem.\u028e.x, toolboxItem.\u028e.y, size.x, size.y);
                rectangle.inflate(this.\u027b.x, this.\u027b.y);
                this.invalidateDoc(rectangle);
            }
        }
    }
    
    private void \u0275(final Graphics graphics, final ToolboxItem toolboxItem) {
        final Rectangle \u028e = toolboxItem.\u028e;
        Rectangle rectangle = null;
        Rectangle rectangle2 = null;
        Point size = null;
        final Image image = toolboxItem.getImage();
        final String text = toolboxItem.getText();
        final boolean b = ((Control)this).getEnabled() && toolboxItem.getEnabled();
        final boolean selected = toolboxItem.getSelected();
        boolean \u0281 = this.\u0281;
        if (image != null) {
            size = image.getSize();
            rectangle = new Rectangle(\u028e.x, \u028e.y, size.x, size.y);
        }
        else {
            \u0281 = false;
        }
        if (text != null) {
            rectangle2 = new Rectangle(\u028e.x + this.\u027b.x, \u028e.y + this.\u027b.y, \u028e.width - 2 * this.\u027b.x, \u028e.height - 2 * this.\u027b.y);
        }
        if (image != null) {
            if (this.\u0198 == 1) {
                final Rectangle rectangle3 = rectangle;
                rectangle3.x += this.\u027b.x;
                final Rectangle rectangle4 = rectangle;
                rectangle4.y += (\u028e.height - rectangle.height) / 2;
                if (text != null) {
                    final Rectangle rectangle5 = rectangle2;
                    rectangle5.x += rectangle.width + this.\u027b.x + this.\u027c.x;
                    final Rectangle rectangle6 = rectangle2;
                    rectangle6.width -= this.\u027c.x + this.\u027b.x + size.x;
                }
            }
            else if (this.\u0198 == 2) {
                final Rectangle rectangle7 = rectangle;
                rectangle7.x += \u028e.width - rectangle.width - 2 * this.\u027b.x;
                final Rectangle rectangle8 = rectangle;
                rectangle8.y += (\u028e.height - rectangle.height) / 2;
                if (text != null) {
                    final Rectangle rectangle9 = rectangle2;
                    rectangle9.width -= this.\u027c.x + size.x;
                }
            }
            else {
                final Rectangle rectangle10 = rectangle;
                rectangle10.x += (\u028e.width - rectangle.width) / 2;
                final Rectangle rectangle11 = rectangle;
                rectangle11.y += this.\u027b.y;
                if (text != null) {
                    final Rectangle rectangle12 = rectangle2;
                    rectangle12.y += size.y + this.\u027b.x + this.\u027c.y;
                    final Rectangle rectangle13 = rectangle2;
                    rectangle13.height -= size.y + this.\u027c.y;
                }
            }
        }
        Color backColor = ((Control)this).getBackColor();
        if (b && (selected || toolboxItem == this.\u0276 || toolboxItem == this.\u0277)) {
            if (!\u0281) {
                backColor = Color.CONTROLLIGHT;
            }
            Rectangle rectangle14;
            if (\u0281) {
                rectangle14 = new Rectangle(rectangle);
                rectangle14.inflate(this.\u027b.x, this.\u027b.y);
            }
            else {
                rectangle14 = \u028e;
            }
            int n;
            if (toolboxItem == this.\u0277 && toolboxItem == this.\u0276) {
                n = 512;
            }
            else if (selected || toolboxItem == this.\u0277) {
                n = 1024;
                if (!\u0281) {
                    backColor = Color.CONTROLLIGHTLIGHT;
                }
            }
            else {
                n = 0;
            }
            graphics.drawButton(rectangle14, n);
        }
        if (image != null) {
            UIUtil.drawImage(graphics, image, rectangle, b);
        }
        if (text != null) {
            final Color backColor2 = graphics.getBackColor();
            graphics.setBackColor(backColor);
            int n2;
            if (this.\u0198 == 3) {
                n2 = 32785;
                if (image == null) {
                    n2 |= 0x24;
                }
            }
            else {
                n2 = 32804;
            }
            UIUtil.drawString(graphics, text, rectangle2, n2, b);
            graphics.setBackColor(backColor2);
        }
    }
    
    protected void onMouseUp(final MouseEvent mouseEvent) {
        if (this.\u0277 != null) {
            boolean b = false;
            if (this.\u027f) {
                final ToolboxItem itemFromPoint = this.getItemFromPoint(mouseEvent.x, mouseEvent.y);
                if (itemFromPoint != null && itemFromPoint != this.\u0277) {
                    this.moveItem(this.\u0277, itemFromPoint);
                    b = true;
                }
            }
            if (!b && this.\u027e != 0 && this.\u027e != 4 && !((Component)this).getDesignMode()) {
                this.setSelectedItem(this.\u0277, this.\u027e == 1 || !this.\u0277.getSelected());
            }
            ((Control)this).setCursor(Cursor.DEFAULT);
            this.setPressedItem(null);
            this.onMouseMove(mouseEvent);
        }
        super.onMouseUp(mouseEvent);
    }
    
    protected void onItemClicked(final Event event) {
        if (this.\u0284 != null) {
            this.\u0284.invoke((Object)this, event);
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
    
    protected void onGotFocus(final Event event) {
        if (this.\u0276 == null && this.\u0279 != null) {
            this.setCurrentItem(this.\u0279[0]);
        }
        super.onGotFocus(event);
        ((Control)this).invalidate();
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        final ToolboxItem itemFromPoint = this.getItemFromPoint(mouseEvent.x, mouseEvent.y);
        if (itemFromPoint == null || !itemFromPoint.getEnabled()) {
            return;
        }
        this.setPressedItem(itemFromPoint);
        super.onMouseDown(mouseEvent);
        ((Control)this).focus();
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        if (super.buffer == null) {
            this.\u0119();
        }
        final Graphics graphics = paintEvent.graphics;
        final Graphics graphics2 = super.buffer.getGraphics();
        graphics2.setBackColor(graphics.getBackColor());
        graphics2.setTextColor(graphics.getTextColor());
        graphics2.setFont(graphics.getFont());
        Brush brush = graphics.getBrush();
        graphics2.setBrush(brush);
        graphics2.setPen(graphics.getPen());
        this.setGraphics(graphics2);
        final Point docSize = this.getDocSize();
        final Rectangle rectangle = new Rectangle(0, 0, docSize.x, docSize.y);
        final boolean designMode = ((Component)this).getDesignMode();
        if (designMode && brush == Brush.NULL) {
            brush = Brush.CONTROL;
        }
        graphics2.fill(rectangle, brush);
        if (designMode && this.getBorderStyle() == 0) {
            graphics2.drawFocusRect(rectangle);
        }
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                this.\u0275(graphics2, this.\u0279[i]);
            }
        }
        this.resetGraphics(graphics2);
        graphics2.dispose();
        paintEvent.graphics.drawImage((Image)super.buffer, 0, 0);
    }
    
    public int getAlignment() {
        return this.\u0198;
    }
    
    public void setAlignment(final int \u0199) {
        if (!ToolboxAlignStyle.valid(\u0199)) {
            throw new WFCInvalidEnumException("alignment", \u0199, (Toolbox.\u0287 != null) ? Toolbox.\u0287 : (Toolbox.\u0287 = \u00c6("com.mim.wfc.ui.ToolboxAlignStyle")));
        }
        this.\u0198 = \u0199;
        ((Control)this).invalidate();
    }
    
    public void removeOnItemClicked(final EventHandler eventHandler) {
        this.\u0284 = (EventHandler)Delegate.remove((Delegate)this.\u0284, (Delegate)eventHandler);
    }
    
    public void setSelectedItems(final ToolboxItem[] array) {
        this.clearSelection();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i].setSelected(true);
            }
        }
    }
    
    public ToolboxItem[] getSelectedItems() {
        final int \u0276 = this.\u0276();
        if (\u0276 == 0) {
            return null;
        }
        final ToolboxItem[] array = new ToolboxItem[\u0276];
        int n = 0;
        for (int i = 0; i < this.\u0279.length; ++i) {
            if (this.\u0279[i].getSelected()) {
                array[n++] = this.\u0279[i];
            }
        }
        return array;
    }
    
    public void addOnItemCurrent(final EventHandler eventHandler) {
        this.\u0286 = (EventHandler)Delegate.combine((Delegate)this.\u0286, (Delegate)eventHandler);
    }
    
    public void setSingleColumn(final boolean \u0280) {
        this.\u0280 = \u0280;
        this.\u0277();
    }
    
    public boolean getSingleColumn() {
        return this.\u0280;
    }
    
    public boolean getSmallHover() {
        return this.\u0281;
    }
    
    public void setSmallHover(final boolean \u0281) {
        this.\u0281 = \u0281;
        ((Control)this).invalidate();
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        final ToolboxItem itemFromPoint = this.getItemFromPoint(mouseEvent.x, mouseEvent.y);
        if (this.\u0276 != null && itemFromPoint != this.\u0276) {
            if (this.\u0277 != null && this.\u0277 == this.\u0276 && Control.getMouseButtons() == 1048576 && ((Control)this).getCursor() == Cursor.DEFAULT) {
                final Cursor cursor = this.\u0277.getCursor();
                if (cursor != null) {
                    ((Control)this).setCursor(cursor);
                }
            }
            if (!((Control)this).getFocused()) {
                this.setCurrentItem(null);
            }
        }
        if (itemFromPoint != null) {
            if (this.\u0277 == null || this.\u0277 == this.\u0276 || this.\u027f) {
                this.setCurrentItem(itemFromPoint);
            }
            if (this.\u0277 != null && this.\u0277 == this.\u0276 && Control.getMouseButtons() == 1048576) {
                ((Control)this).setCursor(Cursor.DEFAULT);
            }
        }
        super.onMouseMove(mouseEvent);
    }
    
    public void setBorderSize(final Point \u027d) {
        this.\u027d = \u027d;
        this.\u0277();
    }
    
    public Point getBorderSize() {
        return this.\u027d;
    }
    
    public ToolboxItem getItemFromPoint(final int n, final int n2) {
        final Point viewToDoc = this.viewToDoc(new Point(n, n2));
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i].\u028e.contains(viewToDoc)) {
                    return this.\u0279[i];
                }
            }
        }
        return null;
    }
    
    public ToolboxItem getPressedItem() {
        return this.\u0277;
    }
    
    public void setPressedItem(final ToolboxItem \u0277) {
        if (this.\u0277 != null) {
            this.invalidateItem(this.\u0277);
            this.\u0277.onItemReleased(Event.EMPTY);
            this.onItemReleased(Event.EMPTY);
        }
        if ((this.\u0277 = \u0277) != null) {
            this.invalidateItem(\u0277);
            \u0277.onItemClicked(Event.EMPTY);
            this.onItemClicked(Event.EMPTY);
        }
    }
    
    public void moveItem(final ToolboxItem toolboxItem, final ToolboxItem toolboxItem2) {
        this.\u0278 = true;
        this.removeItem(toolboxItem);
        this.insertItem(toolboxItem, toolboxItem2);
        this.\u0278 = false;
        this.\u0277();
    }
    
    public void removeItem(final ToolboxItem toolboxItem) {
        final int itemIndex = this.findItemIndex(toolboxItem);
        if (itemIndex < 0) {
            return;
        }
        int length = this.\u0279.length;
        if (--length == 0) {
            this.\u0279 = null;
        }
        else {
            final ToolboxItem[] \u0279 = new ToolboxItem[length];
            if (itemIndex > 0) {
                System.arraycopy(this.\u0279, 0, \u0279, 0, itemIndex);
            }
            if (itemIndex < this.\u0279.length - 1) {
                System.arraycopy(this.\u0279, itemIndex + 1, \u0279, itemIndex, this.\u0279.length - itemIndex - 1);
            }
            this.\u0279 = \u0279;
        }
        toolboxItem.\u028b(null);
        this.\u0277();
    }
    
    public void setSelectedItem(final ToolboxItem toolboxItem) {
        this.setSelectedItem(toolboxItem, true);
    }
    
    public void setSelectedItem(final ToolboxItem toolboxItem, final boolean selected) {
        if (toolboxItem == null) {
            this.clearSelection();
            return;
        }
        if (selected && (this.\u027e == 2 || this.\u027e == 1)) {
            this.clearSelection();
        }
        toolboxItem.setSelected(selected);
    }
    
    public ToolboxItem getSelectedItem() {
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i].getSelected()) {
                    return this.\u0279[i];
                }
            }
        }
        return null;
    }
    
    protected int findItemIndex(final ToolboxItem toolboxItem) {
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i] == toolboxItem) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void setTextDistance(final Point \u027c) {
        this.\u027c = \u027c;
        this.\u0277();
    }
    
    public Point getTextDistance() {
        return this.\u027c;
    }
    
    protected void onMouseLeave(final Event event) {
        if (this.\u0276 != null && !((Control)this).getFocused()) {
            this.setCurrentItem(null);
        }
        super.onMouseLeave(event);
    }
    
    protected void onItemReleased(final Event event) {
        if (this.\u0285 != null) {
            this.\u0285.invoke((Object)this, event);
        }
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        if (this.\u0276 != null) {
            int itemIndex = this.findItemIndex(this.\u0276);
            if (itemIndex >= 0) {
                switch (keyEvent.getKeyCode()) {
                    case 13:
                    case 32: {
                        this.setPressedItem(this.\u0276);
                        ((Control)this).update();
                        this.setPressedItem(null);
                        keyEvent.handled = true;
                        return;
                    }
                    case 37: {
                        --itemIndex;
                        break;
                    }
                    case 39: {
                        ++itemIndex;
                        break;
                    }
                    case 38: {
                        itemIndex -= this.\u0282;
                        break;
                    }
                    case 40: {
                        itemIndex += this.\u0282;
                        break;
                    }
                    case 33: {
                        itemIndex -= this.page;
                        break;
                    }
                    case 34: {
                        itemIndex += this.page;
                        break;
                    }
                    case 36: {
                        itemIndex = 0;
                        break;
                    }
                    case 35: {
                        itemIndex = 100000;
                        break;
                    }
                    default: {
                        return;
                    }
                }
                if (itemIndex < 0) {
                    itemIndex = 0;
                }
                if (itemIndex >= this.\u0279.length) {
                    itemIndex = this.\u0279.length - 1;
                }
                this.setCurrentItem(this.\u0279[itemIndex]);
                keyEvent.handled = true;
            }
        }
        if (!keyEvent.handled) {
            super.onKeyDown(keyEvent);
        }
    }
    
    public void setEnableReorder(final boolean \u027f) {
        this.\u027f = \u027f;
    }
    
    protected void onLostFocus(final Event event) {
        this.setCurrentItem(null);
        super.onLostFocus(event);
        ((Control)this).invalidate();
    }
    
    public boolean getEnableReorder() {
        return this.\u027f;
    }
    
    private int \u0276() {
        int n = 0;
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i].getSelected()) {
                    ++n;
                }
            }
        }
        return n;
    }
    
    public void addOnItemClicked(final EventHandler eventHandler) {
        this.\u0284 = (EventHandler)Delegate.combine((Delegate)this.\u0284, (Delegate)eventHandler);
    }
    
    public void removeOnItemReleased(final EventHandler eventHandler) {
        this.\u0285 = (EventHandler)Delegate.remove((Delegate)this.\u0285, (Delegate)eventHandler);
    }
    
    public Toolbox() {
        this.\u0275 = null;
        this.\u0276 = null;
        this.\u0277 = null;
        this.\u0278 = false;
        this.\u0279 = null;
        this.\u027a = new Point(40, 80);
        this.\u027b = new Point(4, 4);
        this.\u027c = new Point(4, 4);
        this.\u027d = new Point(8, 8);
        this.\u027e = 0;
        this.\u027f = true;
        this.\u0280 = false;
        this.\u0281 = true;
        this.\u0198 = 3;
        this.\u0282 = 0;
        this.\u0283 = 0;
        this.page = 0;
        ((Control)this).setTabStop(true);
        this.setDoubleBuffer(true);
        this.setBorderStyle(2);
        this.setMode(1);
        ((Control)this).setSize(100, 180);
    }
    
    protected void onItemCurrent(final Event event) {
        if (this.\u0286 != null) {
            this.\u0286.invoke((Object)this, event);
        }
    }
    
    protected void wndProc(final Message message) {
        switch (message.msg) {
            case 135: {
                super.wndProc(message);
                message.result |= 0x1;
            }
            default: {
                super.wndProc(message);
            }
        }
    }
    
    private void \u0277() {
        if (this.\u0278) {
            return;
        }
        int x = 0;
        int y = 0;
        final Point maxClientSize = this.getMaxClientSize();
        final Point clientSize = ((Control)this).getClientSize();
        final boolean vScrollVisible = this.isVScrollVisible();
        if (this.\u0279 != null) {
            final int length = this.\u0279.length;
            int x2 = this.\u027a.x;
            final int y2 = this.\u027a.y;
            if (this.\u0280) {
                x2 = clientSize.x - 2 * this.\u027d.x;
                this.\u0282 = 1;
            }
            else {
                this.\u0282 = (maxClientSize.x - 2 * this.\u027d.x) / x2;
                if (this.\u0282 == 0) {
                    this.\u0282 = 1;
                }
            }
            for (int i = 0; i < this.\u0279.length; ++i) {
                final Rectangle \u028e = this.\u0279[i].\u028e;
                final int n = i % this.\u0282;
                final int n2 = i / this.\u0282;
                \u028e.x = this.\u027d.x + n * x2;
                \u028e.y = this.\u027d.y + n2 * y2;
                \u028e.width = x2;
                \u028e.height = y2;
            }
            this.\u0283 = length / this.\u0282;
            if (length % this.\u0282 != 0) {
                ++this.\u0283;
            }
            x = 2 * this.\u027d.x + this.\u0282 * x2;
            y = 2 * this.\u027d.y + this.\u0283 * y2;
        }
        if (x < maxClientSize.x) {
            x = maxClientSize.x;
        }
        if (y < maxClientSize.y) {
            y = maxClientSize.y;
        }
        final Point scrollOffset = this.getScrollOffset();
        this.setDocSize(x, y);
        this.setScrollOffset(scrollOffset);
        this.page = this.\u0282 * (clientSize.y - 2 * this.\u027d.y) / this.\u027a.y;
        if (this.\u0280 && vScrollVisible != this.isVScrollVisible()) {
            this.\u0277();
        }
        ((Control)this).invalidate();
    }
    
    public void addItem(final ToolboxItem toolboxItem) {
        if (toolboxItem != null) {
            final int n = (this.\u0279 != null) ? this.\u0279.length : 0;
            final ToolboxItem[] \u0279 = new ToolboxItem[n + 1];
            if (this.\u0279 != null) {
                System.arraycopy(this.\u0279, 0, \u0279, 0, n);
            }
            \u0279[n] = toolboxItem;
            this.\u0279 = \u0279;
            toolboxItem.\u028b(this);
            this.\u0277();
        }
    }
    
    public void setSelectionMode(final int \u027e) {
        this.\u027e = \u027e;
    }
    
    public int getSelectionMode() {
        return this.\u027e;
    }
    
    protected void onKeyPress(final KeyPressEvent keyPressEvent) {
        super.onKeyPress(keyPressEvent);
        if (keyPressEvent.handled) {
            return;
        }
        this.setCurrentItemByChar(keyPressEvent.getKeyChar());
    }
    
    public void setSelectedIndex(final int n) {
        this.setSelectedIndex(n, true);
    }
    
    public void setSelectedIndex(final int n, final boolean b) {
        if (n < 0) {
            this.clearSelection();
            return;
        }
        if (n < this.\u0279.length) {
            this.setSelectedItem(this.\u0279[n], b);
        }
    }
    
    public int getSelectedIndex() {
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i].getSelected()) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void setItemSize(final Point \u027a) {
        this.\u027a = \u027a;
        this.\u0277();
    }
    
    public Point getItemSize() {
        return this.\u027a;
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        ((Control)this).invalidate();
    }
}
