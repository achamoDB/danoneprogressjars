// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Form;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.MeasureItemEvent;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.MouseEventHandler;
import com.ms.wfc.ui.MeasureItemEventHandler;
import com.ms.wfc.ui.DrawItemEventHandler;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.ui.Pen;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.DrawItemEvent;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.ListBox;
import com.ms.wfc.core.Container;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.UserControl;

public class ImageBar extends UserControl implements IToolbox
{
    private boolean \u0281;
    private int \u0198;
    private int \u027e;
    private Point \u03c1;
    private ImageBarItem[] \u0279;
    private ImageBarEventHandler \u0284;
    private int \u03c2;
    private int \u03c3;
    Container \u011d;
    ListBox lb;
    private static Class \u0189;
    
    protected void _mouseEnter(final Object o, final Event event) {
        final Point mousePosition = Control.getMousePosition();
        this._mouseMove(o, new MouseEvent(0, 0, mousePosition.x, mousePosition.y, 0));
        ((Control)this).onMouseEnter(event);
    }
    
    public void scrollToItemByChar(final char ch) {
        final boolean lowerCase = Character.isLowerCase(ch);
        final String string = "" + Character.toLowerCase(ch);
        final String string2 = "" + Character.toUpperCase(ch);
        final int topIndex = this.lb.getTopIndex();
        int n = -1;
        final int itemCount = this.lb.getItemCount();
        final int n2 = ((Control)this.lb).getSize().y / this.\u03c2();
        if (lowerCase ? (topIndex + n2 < itemCount) : (topIndex >= n2)) {
            int n3 = topIndex + (lowerCase ? 1 : -1);
            while (lowerCase ? (n3 < itemCount) : (n3 >= 0)) {
                final String text = ((ImageBarItem)this.lb.getItem(n3)).getText();
                if (text != null && (text.startsWith(string) || text.startsWith(string2))) {
                    n = n3;
                    break;
                }
                if (lowerCase) {
                    ++n3;
                }
                else {
                    --n3;
                }
            }
        }
        if (n < 0) {
            int n4 = lowerCase ? 0 : (itemCount - 1);
            while (lowerCase ? (n4 < topIndex) : (n4 > topIndex)) {
                final String text2 = ((ImageBarItem)this.lb.getItem(n4)).getText();
                if (text2 != null && (text2.startsWith(string) || text2.startsWith(string2))) {
                    n = n4;
                    break;
                }
                if (lowerCase) {
                    ++n4;
                }
                else {
                    --n4;
                }
            }
        }
        if (n >= 0) {
            ((Control)this.lb).sendMessage(407, n, 0);
        }
    }
    
    public boolean getSmallHover() {
        return this.\u0281;
    }
    
    public ImageBarItem[] getItemList() {
        return this.\u0279;
    }
    
    public void setItemList(final ImageBarItem[] array) {
        this.\u0279 = array;
        for (int i = 0; i < array.length; ++i) {
            array[i].\u028b(this);
        }
        this.lb.setItems((Object[])array);
        ((Control)this.lb).invalidate();
    }
    
    public void setSmallHover(final boolean \u0281) {
        this.\u0281 = \u0281;
        ((Control)this.lb).invalidate();
    }
    
    public void removeItem(final ImageBarItem imageBarItem) {
        if (this.\u0279 == null) {
            return;
        }
        for (int i = 0; i < this.\u0279.length; ++i) {
            if (this.\u0279[i] == imageBarItem) {
                int length = this.\u0279.length;
                if (--length == 0) {
                    this.\u0279 = null;
                }
                else {
                    final ImageBarItem[] \u0279 = new ImageBarItem[length];
                    if (i > 0) {
                        System.arraycopy(this.\u0279, 0, \u0279, 0, i);
                    }
                    if (i < this.\u0279.length - 1) {
                        System.arraycopy(this.\u0279, i + 1, \u0279, i, this.\u0279.length - i - 1);
                    }
                    this.\u0279 = \u0279;
                }
                imageBarItem.\u028b(null);
                for (int itemCount = this.lb.getItemCount(), j = 0; j < itemCount; ++j) {
                    if (this.lb.getItem(j) == imageBarItem) {
                        this.lb.removeItem(j);
                        break;
                    }
                }
            }
        }
    }
    
    public ImageBarItem getSelectedItem() {
        if (this.\u0279 != null) {
            for (int i = 0; i < this.\u0279.length; ++i) {
                if (this.\u0279[i].getSelected()) {
                    return this.\u0279[i];
                }
            }
        }
        return null;
    }
    
    private int \u03c1(final ToolboxItem toolboxItem) {
        for (int itemCount = this.lb.getItemCount(), i = 0; i < itemCount; ++i) {
            if (this.lb.getItem(i) == toolboxItem) {
                return i;
            }
        }
        return -1;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    private void \u0118() {
        this.setBackColor(Color.INACTIVECAPTION);
        this.setForeColor(Color.WINDOWTEXT);
        ((Control)this).setSize(new Point(300, 300));
        ((Control)this).setText("ImageBar");
        ((Control)this.lb).setBackColor(Color.INACTIVECAPTION);
        ((Control)this.lb).setDock(5);
        ((Control)this.lb).setSize(new Point(300, 300));
        ((Control)this.lb).setTabIndex(0);
        ((Control)this.lb).setText("lb");
        this.lb.setIntegralHeight(false);
        this.lb.setItemHeight(1);
        this.lb.setDrawMode(1);
        this.lb.setSelectionMode(2);
        this.lb.setUseTabStops(true);
        ((Form)this).setNewControls(new Control[] { (Control)this.lb });
    }
    
    private void clearSelection() {
        if (this.\u0279 == null) {
            return;
        }
        for (int i = 0; i < this.\u0279.length; ++i) {
            final ImageBarItem imageBarItem = this.\u0279[i];
            imageBarItem.getSelected();
            imageBarItem.setSelected(false);
        }
    }
    
    public int getItemCount() {
        if (this.\u0279 != null) {
            return this.\u0279.length;
        }
        return 0;
    }
    
    public void setBackColor(final Color color) {
        super.setBackColor(color);
        if (this.lb != null) {
            ((Control)this.lb).setBackColor(color);
            ((Control)this.lb).invalidate();
        }
    }
    
    protected void _mouseLeave(final Object o, final Event event) {
        if (this.\u03c2 != -1) {
            final int \u03c2 = this.\u03c2;
            this.\u03c2 = -1;
            if (!this.isSelected(\u03c2)) {
                this.invalidateItem(\u03c2);
            }
        }
        ((Control)this).onMouseLeave(event);
    }
    
    private int \u03c2() {
        int n;
        if (this.\u0198 == 3) {
            n = this.\u03c1.y * 2;
        }
        else {
            n = this.\u03c1.y + 4;
        }
        return n;
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (this.lb != null && ((Control)this.lb).getCreated()) {
            ((Control)this.lb).invalidate();
        }
    }
    
    public boolean processDialogChar(final int n) {
        this.scrollToItemByChar((char)n);
        return true;
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
    
    public ImageBarItem getCurrentItem() {
        return (ImageBarItem)this.lb.getItem(this.\u03c2);
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
    
    public void addOnItemClicked(final ImageBarEventHandler imageBarEventHandler) {
        this.\u0284 = (ImageBarEventHandler)Delegate.combine((Delegate)this.\u0284, (Delegate)imageBarEventHandler);
    }
    
    public void invalidateItem(final ToolboxItem toolboxItem) {
        this.invalidateItem(this.\u03c1(toolboxItem));
    }
    
    protected void _drawItem(final Object o, final DrawItemEvent drawItemEvent) {
        final ImageBarItem imageBarItem = (ImageBarItem)this.lb.getItem(drawItemEvent.index);
        if (imageBarItem == null) {
            return;
        }
        Rectangle rectangle;
        Rectangle rectangle2;
        if (this.\u0198 == 1) {
            rectangle = new Rectangle(drawItemEvent.rect.x + 4, drawItemEvent.rect.y + (drawItemEvent.rect.height - this.\u03c1.y) / 2, this.\u03c1.x, this.\u03c1.y);
            rectangle2 = new Rectangle(drawItemEvent.rect.x + rectangle.width + 10, drawItemEvent.rect.y, drawItemEvent.rect.width - 14 - this.\u03c1.x, drawItemEvent.rect.height);
        }
        else if (this.\u0198 == 2) {
            rectangle = new Rectangle(drawItemEvent.rect.x + drawItemEvent.rect.width - 4 - this.\u03c1.x, drawItemEvent.rect.y + (drawItemEvent.rect.height - this.\u03c1.y) / 2, this.\u03c1.x, this.\u03c1.y);
            rectangle2 = new Rectangle(drawItemEvent.rect.x + 10, drawItemEvent.rect.y, drawItemEvent.rect.width - 14 - this.\u03c1.x, drawItemEvent.rect.height);
        }
        else {
            rectangle = new Rectangle((drawItemEvent.rect.width - this.\u03c1.x) / 2, drawItemEvent.rect.y + 4, this.\u03c1.x, this.\u03c1.y);
            rectangle2 = new Rectangle(0, drawItemEvent.rect.y + 4 + this.\u03c1.y + 8, drawItemEvent.rect.width, drawItemEvent.rect.height - drawItemEvent.rect.height / 2 - 8);
        }
        final ListBox listBox = (ListBox)o;
        final Image image = imageBarItem.getImage();
        final String text = imageBarItem.getText();
        final boolean selected = imageBarItem.getSelected();
        if ((drawItemEvent.state & 0x10) != 0x0) {}
        final Color backColor = ((Control)this).getBackColor();
        final Color foreColor = ((Control)this).getForeColor();
        final Graphics graphics = drawItemEvent.graphics;
        final boolean enabled = ((Control)this).getEnabled();
        graphics.setPen((Pen)null);
        graphics.setBrush(new Brush(backColor));
        graphics.drawRect(drawItemEvent.rect);
        graphics.setBackColor(backColor);
        graphics.setTextColor(foreColor);
        if (image != null) {
            final Point size = image.getSize();
            UIUtil.drawImage(graphics, image, new Rectangle(rectangle.x + (rectangle.width - size.x) / 2, rectangle.y + (rectangle.height - size.y) / 2, size.x, size.y), true);
        }
        if (this.\u0198 == 3) {
            UIUtil.drawString(graphics, text, rectangle2, 32801, enabled);
        }
        else {
            UIUtil.drawString(graphics, text, rectangle2, 36, enabled);
        }
        if (enabled) {
            if (selected || (drawItemEvent.index == this.\u03c3 && this.\u027e != 4)) {
                if (this.\u0281) {
                    rectangle.inflate(3, 2);
                    graphics.drawBorder3D(rectangle, 8);
                    return;
                }
                graphics.drawBorder3D(drawItemEvent.rect, 8);
            }
            else if (drawItemEvent.index == this.\u03c2) {
                if (this.\u0281) {
                    rectangle.inflate(3, 2);
                    graphics.drawBorder3D(rectangle, 1);
                    return;
                }
                graphics.drawBorder3D(drawItemEvent.rect, 1);
            }
        }
    }
    
    public ImageBar() {
        this.\u0281 = true;
        this.\u0198 = 3;
        this.\u027e = 0;
        this.\u03c1 = new Point(32, 32);
        this.\u03c2 = -1;
        this.\u03c3 = -1;
        this.\u011d = new Container();
        this.lb = new ListBox();
        _cls753A._mth821F();
        this.\u0118();
        ((Control)this.lb).setBackColor(((Control)this).getBackColor());
        ((Control)this.lb).setForeColor(((Control)this).getForeColor());
        this.lb.setItemHeight(this.\u03c2());
        this.lb.addOnDrawItem(new DrawItemEventHandler((Object)this, "_drawItem"));
        this.lb.addOnMeasureItem(new MeasureItemEventHandler((Object)this, "_measureItem"));
        ((Control)this.lb).addOnMouseMove(new MouseEventHandler((Object)this, "_mouseMove"));
        ((Control)this.lb).addOnMouseDown(new MouseEventHandler((Object)this, "_mouseDown"));
        ((Control)this.lb).addOnMouseUp(new MouseEventHandler((Object)this, "_mouseUp"));
        ((Control)this.lb).addOnMouseEnter(new EventHandler((Object)this, "_mouseEnter"));
        ((Control)this.lb).addOnMouseLeave(new EventHandler((Object)this, "_mouseLeave"));
    }
    
    public ImageBarItem getItem(final int n) {
        if (this.\u0279 == null || n < 0 || n >= this.\u0279.length) {
            return null;
        }
        return this.\u0279[n];
    }
    
    public void selectItem(final ImageBarItem imageBarItem, final boolean selected) {
        if (imageBarItem == null) {
            this.clearSelection();
            return;
        }
        if (selected && (this.\u027e == 2 || this.\u027e == 1)) {
            this.clearSelection();
        }
        imageBarItem.setSelected(selected);
    }
    
    protected void _mouseUp(final Object o, final MouseEvent mouseEvent) {
        final int \u03c3 = this.\u03c3;
        this.\u03c3 = -1;
        if (!this.isSelected(\u03c3)) {
            this.invalidateItem(\u03c3);
        }
        ((Control)this).onMouseUp(mouseEvent);
    }
    
    private void invalidateItem(final int n) {
        if (!((Control)this.lb).getCreated() || this.\u0279 == null || n < 0 || n >= this.\u0279.length) {
            return;
        }
        final int \u03c2 = this.\u03c2();
        final int n2 = (n - this.lb.getTopIndex()) * \u03c2;
        if (n2 > ((Control)this.lb).getHeight()) {
            return;
        }
        ((Control)this.lb).invalidate(new Rectangle(0, n2, ((Control)this.lb).getClientSize().x, \u03c2));
    }
    
    private boolean isSelected(final int n) {
        if (n < 0) {
            return false;
        }
        final ImageBarItem imageBarItem = (ImageBarItem)this.lb.getItem(n);
        return imageBarItem != null && imageBarItem.getSelected();
    }
    
    public void setForeColor(final Color color) {
        super.setForeColor(color);
        if (this.lb != null) {
            ((Control)this.lb).setForeColor(color);
            ((Control)this.lb).invalidate();
        }
    }
    
    public Point getImageSize() {
        return new Point(this.\u03c1);
    }
    
    public boolean processDialogKey(final int n) {
        if ((n & 0x60000) != 0x0) {
            return false;
        }
        final int n2 = n & 0xFFFF;
        int topIndex = this.lb.getTopIndex();
        while (true) {
            switch (n2) {
                default: {
                    return false;
                }
                case 37:
                case 39: {
                    if (topIndex < 0) {
                        topIndex = 0;
                    }
                    if (topIndex > this.lb.getItemCount()) {
                        topIndex = this.lb.getItemCount() - 1;
                    }
                    ((Control)this).sendMessage(407, topIndex, 0);
                    return true;
                }
                case 38: {
                    ++topIndex;
                    continue;
                }
                case 40: {
                    --topIndex;
                    continue;
                }
                case 33: {
                    topIndex -= ((Control)this.lb).getSize().y / this.\u03c2();
                    continue;
                }
                case 34: {
                    topIndex += ((Control)this.lb).getSize().y / this.\u03c2();
                    continue;
                }
                case 36: {
                    topIndex = 0;
                    continue;
                }
                case 35: {
                    topIndex = 100000;
                    continue;
                }
            }
            break;
        }
    }
    
    public void setImageSize(final Point point) {
        if (point == null) {
            final Point \u03c1 = this.\u03c1;
            final Point \u03c12 = this.\u03c1;
            final int n = 32;
            \u03c12.y = n;
            \u03c1.x = n;
        }
        else {
            this.\u03c1.x = point.x;
            this.\u03c1.y = point.y;
        }
        this.lb.setItemHeight(this.\u03c2());
        ((Control)this.lb).invalidate();
    }
    
    protected void onItemClicked(final ImageBarEvent imageBarEvent) {
        if (this.\u0284 != null) {
            this.\u0284.invoke(this, imageBarEvent);
        }
    }
    
    protected void _measureItem(final Object o, final MeasureItemEvent measureItemEvent) {
        measureItemEvent.itemHeight = this.\u03c2();
    }
    
    public int addItem(final ImageBarItem imageBarItem) {
        if (imageBarItem != null) {
            final int n = (this.\u0279 != null) ? this.\u0279.length : 0;
            final ImageBarItem[] \u0279 = new ImageBarItem[n + 1];
            if (this.\u0279 != null) {
                System.arraycopy(this.\u0279, 0, \u0279, 0, n);
            }
            \u0279[n] = imageBarItem;
            this.\u0279 = \u0279;
            imageBarItem.\u028b(this);
            return this.lb.addItem((Object)imageBarItem);
        }
        return -1;
    }
    
    public void selectItems(final ImageBarItem[] array) {
        this.clearSelection();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i].setSelected(true);
            }
        }
    }
    
    public void selectIndex(final int n, final boolean selected) {
        if (n >= 0 && n < this.\u0279.length) {
            this.\u0279[n].setSelected(selected);
        }
    }
    
    public void setSelectionMode(final int \u027e) {
        this.\u027e = \u027e;
    }
    
    public int getSelectionMode() {
        return this.\u027e;
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
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void selectIndices(final int[] array) {
        this.clearSelection();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.selectIndex(array[i], true);
            }
        }
    }
    
    protected void _mouseDown(final Object o, final MouseEvent mouseEvent) {
        final int indexFromPoint = this.lb.indexFromPoint(mouseEvent.x, mouseEvent.y);
        final ImageBarItem imageBarItem = (ImageBarItem)this.lb.getItem(indexFromPoint);
        if (imageBarItem == null) {
            return;
        }
        if (this.\u027e != 0 && this.\u027e != 4) {
            this.selectItem(imageBarItem, this.\u027e == 1 || !imageBarItem.getSelected());
        }
        this.\u03c3 = indexFromPoint;
        if (!this.isSelected(indexFromPoint)) {
            this.invalidateItem(indexFromPoint);
        }
        imageBarItem.onItemClicked(Event.EMPTY);
        this.onItemClicked(new ImageBarEvent(imageBarItem));
        this.onMouseDown(mouseEvent);
    }
    
    private boolean \u03c3(final int n, final int n2, final int n3) {
        if (this.\u0279 == null || n < 0 || n >= this.\u0279.length) {
            return false;
        }
        final int n4 = n - this.lb.getTopIndex();
        final int \u03c2 = this.\u03c2();
        if (this.\u0281) {
            final int x = ((Control)this.lb).getClientSize().x;
            if (x == 0) {
                return false;
            }
            final Rectangle rectangle = new Rectangle(0, n4 * \u03c2, this.\u03c1.x, this.\u03c1.y);
            if (this.\u0198 == 1) {
                rectangle.x = 4;
                final Rectangle rectangle2 = rectangle;
                rectangle2.y += (\u03c2 - this.\u03c1.y) / 2;
            }
            else if (this.\u0198 == 2) {
                rectangle.x = x - this.\u03c1.x - 4;
                final Rectangle rectangle3 = rectangle;
                rectangle3.y += (\u03c2 - this.\u03c1.y) / 2;
            }
            else {
                rectangle.x = (x - this.\u03c1.x) / 2;
                final Rectangle rectangle4 = rectangle;
                rectangle4.y += 2;
            }
            rectangle.inflate(3, 2);
            if (n2 < rectangle.x || n2 > rectangle.x + rectangle.width || n3 < rectangle.y || n3 > rectangle.y + rectangle.height) {
                return false;
            }
        }
        else {
            final int n5 = n4 * \u03c2;
            if (n3 < n5 || n3 > n5 + \u03c2 - 1) {
                return false;
            }
        }
        return true;
    }
    
    public int getAlignment() {
        return this.\u0198;
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        ((Control)this.lb).invalidate();
    }
    
    public void setAlignment(final int \u0199) {
        if (!ToolboxAlignStyle.valid(\u0199)) {
            throw new WFCInvalidEnumException("alignment", \u0199, (ImageBar.\u0189 != null) ? ImageBar.\u0189 : (ImageBar.\u0189 = \u00c6("com.mim.wfc.ui.ImageBarAlignStyle")));
        }
        this.\u0198 = \u0199;
        this.lb.setItemHeight(this.\u03c2());
        ((Control)this.lb).invalidate();
    }
    
    public void removeOnItemClicked(final ImageBarEventHandler imageBarEventHandler) {
        this.\u0284 = (ImageBarEventHandler)Delegate.remove((Delegate)this.\u0284, (Delegate)imageBarEventHandler);
    }
    
    public ImageBarItem[] getSelectedItems() {
        final int \u0276 = this.\u0276();
        if (\u0276 == 0) {
            return null;
        }
        final ImageBarItem[] array = new ImageBarItem[\u0276];
        int n = 0;
        for (int i = 0; i < this.\u0279.length; ++i) {
            if (this.\u0279[i].getSelected()) {
                array[n++] = this.\u0279[i];
            }
        }
        return array;
    }
    
    protected void _mouseMove(final Object o, final MouseEvent mouseEvent) {
        final int indexFromPoint = this.lb.indexFromPoint(mouseEvent.x, mouseEvent.y);
        final boolean \u03c3 = this.\u03c3(indexFromPoint, mouseEvent.x, mouseEvent.y);
        if (this.\u03c2 != -1 && (indexFromPoint != this.\u03c2 || !\u03c3)) {
            final int \u03c2 = this.\u03c2;
            this.\u03c2 = -1;
            if (!this.isSelected(\u03c2)) {
                this.invalidateItem(\u03c2);
            }
        }
        if (\u03c3 && indexFromPoint != this.\u03c2) {
            this.\u03c2 = indexFromPoint;
            if (!this.isSelected(indexFromPoint)) {
                this.invalidateItem(indexFromPoint);
            }
        }
        ((Control)this).onMouseMove(mouseEvent);
    }
}
