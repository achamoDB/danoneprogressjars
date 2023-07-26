// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.RasterOp;
import com.ms.wfc.ui.Image;
import com.ms.win32.RECT;
import com.ms.wfc.ui.Point;
import com.ms.win32.User32;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.app.SystemInformation;
import com.ms.wfc.ui.MessageBox;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Control;

public class UIUtil
{
    public static void centerWindow(final Control control) {
        positionWindow(control, 0, 0, 0, null);
    }
    
    public static void drawString(final Graphics graphics, final String s, final Rectangle rectangle, final int n, final boolean b) {
        if (b) {
            graphics.drawString(s, rectangle, n);
            return;
        }
        final Rectangle rectangle3;
        final Rectangle rectangle2 = rectangle3 = new Rectangle(rectangle);
        ++rectangle3.x;
        final Rectangle rectangle4 = rectangle2;
        ++rectangle4.y;
        final Color textColor = graphics.getTextColor();
        final boolean opaque = graphics.getOpaque();
        graphics.setOpaque(false);
        graphics.setTextColor(Color.CONTROLLIGHTLIGHT);
        graphics.drawString(s, rectangle2, n);
        graphics.setTextColor(Color.CONTROLDARK);
        graphics.drawString(s, rectangle, n);
        graphics.setOpaque(opaque);
        graphics.setTextColor(textColor);
    }
    
    public static void printExceptionSimple(final Exception ex, final String s) {
        ex.printStackTrace();
        MessageBox.show(ex.toString(), s, 4);
    }
    
    public static void positionWindow(final Control control, final int n, final int n2, final int n3, final Control control2) {
        final Rectangle workingArea = SystemInformation.getWorkingArea();
        final Rectangle rectangle = (control2 == null) ? workingArea : control2.getBounds();
        final int width = control.getWidth();
        final int height = control.getHeight();
        int x = 0;
        switch (n) {
            case 1:
            case 4:
            case 7: {
                x = rectangle.x;
                break;
            }
            case 3:
            case 6:
            case 9: {
                x = rectangle.x + rectangle.width - width;
                break;
            }
            default: {
                x = rectangle.width / 2 - width / 2;
                break;
            }
        }
        int y = 0;
        switch (n) {
            case 7:
            case 8:
            case 9: {
                y = rectangle.y;
                break;
            }
            case 1:
            case 2:
            case 3: {
                y = rectangle.y + rectangle.height - height;
                break;
            }
            default: {
                y = rectangle.height / 2 - height / 2;
                break;
            }
        }
        if (x < 0) {
            x = 0;
        }
        else if (x + width > workingArea.width) {
            x = workingArea.width - width;
        }
        if (y < 0) {
            y = 0;
        }
        else if (y + height > workingArea.height) {
            y = workingArea.height - height;
        }
        control.setLocation(x + n2, y + n3);
    }
    
    public static void positionWindow(final Control control, final int n, final int n2, final int n3) {
        positionWindow(control, n, n2, n3, null);
    }
    
    public static void positionWindow(final Control control, final int n) {
        positionWindow(control, n, 0, 0, null);
    }
    
    public static Bitmap printToBitmap(final Control control) {
        final int handle = control.getHandle();
        final Bitmap bitmap = new Bitmap(control.getWidth(), control.getHeight());
        final Graphics graphics = bitmap.getGraphics();
        final int handle2 = graphics.getHandle();
        User32.SendMessage(handle, 791, handle2, 2);
        final Point clientSize = control.getClientSize();
        final Bitmap bitmap2 = new Bitmap(clientSize.x, clientSize.y);
        final Graphics graphics2 = bitmap2.getGraphics();
        User32.SendMessage(handle, 791, graphics2.getHandle(), 12);
        graphics2.dispose();
        final Point pointToScreen = control.pointToScreen(new Point());
        final RECT rect = new RECT();
        User32.GetWindowRect(handle, rect);
        graphics.drawImage((Image)bitmap2, new Point(pointToScreen.x - rect.left, pointToScreen.y - rect.top));
        User32.SendMessage(handle, 791, handle2, 21);
        graphics.dispose();
        return bitmap;
    }
    
    public static void convertBitmapColors(final Bitmap bitmap, final Color[] array, final Color[] array2) {
        final Graphics graphics = bitmap.getGraphics();
        final Point size = bitmap.getSize();
        final int x = size.x;
        final int y = size.y;
        final int length = array.length;
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                final Color pixel = graphics.getPixel(j, i);
                for (int k = 0; k < length; ++k) {
                    if (pixel.equals((Object)array[k])) {
                        graphics.setPixel(j, i, array2[k]);
                    }
                }
            }
        }
        graphics.dispose();
    }
    
    public static void printException(final Exception ex, final String title) {
        ex.printStackTrace();
        final String title2 = MsgBox.getTitle();
        MsgBox.setTitle(title);
        MsgBox.show(ex.toString());
        MsgBox.setTitle(title2);
    }
    
    public static void printException(final Exception ex) {
        ex.printStackTrace();
        MsgBox.show(ex.toString());
    }
    
    public static void drawImage(final Graphics graphics, final Image image, final Rectangle rectangle, final boolean b) {
        if (b) {
            graphics.drawImage(image, rectangle, false);
            return;
        }
        final Rectangle rectangle3;
        final Rectangle rectangle2 = rectangle3 = new Rectangle(rectangle);
        ++rectangle3.x;
        final Rectangle rectangle4 = rectangle2;
        ++rectangle4.y;
        graphics.drawImage(image, rectangle2, false, RasterOp.WHITE);
        graphics.drawImage(image, rectangle, false, RasterOp.BLACK);
        \u0292(graphics, rectangle);
    }
    
    public static void drawStringEllipsis(final Graphics graphics, String str, final Rectangle rectangle, int n, final boolean b, final boolean b2) {
        final Point textSize = graphics.getTextSize(str);
        if (b2) {
            n |= 0x8000;
        }
        else if (textSize.x > rectangle.width) {
            final int x = graphics.getTextSize("...").x;
            while (str.length() > 0) {
                str = str.substring(1);
                if (graphics.getTextSize(str).x + x > rectangle.width) {
                    continue;
                }
                break;
            }
            str = "..." + str;
        }
        drawString(graphics, str, rectangle, n, b);
    }
    
    private static void \u0292(final Graphics graphics, final Rectangle rectangle) {
        final Color color = new Color(255, 255, 255);
        final Color color2 = new Color(0, 0, 0);
        final Color controllightlight = Color.CONTROLLIGHTLIGHT;
        final Color controldark = Color.CONTROLDARK;
        for (int n = rectangle.y + rectangle.height, i = rectangle.y; i < n; ++i) {
            for (int n2 = rectangle.x + rectangle.width, j = rectangle.x; j < n2; ++j) {
                final Color pixel = graphics.getPixel(j, i);
                if (pixel.equals((Object)color)) {
                    graphics.setPixel(j, i, controllightlight);
                }
                else if (pixel.equals((Object)color2)) {
                    graphics.setPixel(j, i, controldark);
                }
            }
        }
    }
}
