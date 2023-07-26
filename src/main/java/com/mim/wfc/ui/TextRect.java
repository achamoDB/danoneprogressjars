// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Point;

class TextRect
{
    private String[] \u0271;
    private Point[] \u0272;
    private Point \u0273;
    private int \u0274;
    
    private void \u0271(final Graphics graphics, final int n) {
        if (this.\u0271 == null) {
            this.\u0272 = null;
            return;
        }
        final int n2 = 2064;
        this.\u0272 = new Point[this.\u0271.length];
        for (int i = 0; i < this.\u0271.length; ++i) {
            this.\u0272[i] = graphics.getTextSize(this.\u0271[i], n, n2);
        }
    }
    
    public void initialize(final Graphics graphics, final String s, final int n) {
        this.\u0272(s);
        this.\u0271(graphics, n);
        this.\u0273();
    }
    
    public Point getTextSize() {
        return new Point(this.\u0273);
    }
    
    public int getTextLength() {
        return this.\u0274;
    }
    
    public TextRect() {
        this.\u0273 = new Point(0, 0);
    }
    
    public TextRect(final Graphics graphics, final String s, final int n) {
        this.\u0273 = new Point(0, 0);
        this.initialize(graphics, s, n);
    }
    
    public void paint(final Graphics graphics, final Point point) {
        this.paint(graphics, point.x, point.y);
    }
    
    public void paint(final Graphics graphics, final int n, final int n2) {
        if (this.\u0271 != null) {
            final int n3 = 2064;
            final Rectangle rectangle = new Rectangle(n, n2, this.\u0273.x, 0);
            for (int i = 0; i < this.\u0271.length; ++i) {
                rectangle.height = this.\u0272[i].y;
                graphics.drawString(this.\u0271[i], rectangle, n3);
                final Rectangle rectangle2 = rectangle;
                rectangle2.y += rectangle.height;
            }
        }
    }
    
    private void \u0272(final String s) {
        this.\u0271 = null;
        this.\u0272 = null;
        if (s == null || (this.\u0274 = s.length()) == 0) {
            this.\u0274 = 0;
            return;
        }
        int n = 0;
        int fromIndex = 0;
        while (true) {
            final int index = s.indexOf(10, fromIndex);
            if (index < 0) {
                break;
            }
            ++n;
            fromIndex = index + 1;
        }
        ++n;
        this.\u0271 = new String[n];
        int n2 = 0;
        int beginIndex = 0;
        while (true) {
            final int index2 = s.indexOf(10, beginIndex);
            if (index2 < 0) {
                break;
            }
            int index3 = index2 - 1;
            if (index3 > 0 && s.charAt(index3) == '\r') {
                --index3;
            }
            String substring;
            if (index3 > beginIndex) {
                substring = s.substring(beginIndex, index3 + 1);
            }
            else {
                substring = new String(" ");
            }
            this.\u0271[n2++] = substring;
            beginIndex = index2 + 1;
        }
        this.\u0271[n2] = s.substring(beginIndex);
    }
    
    private void \u0273() {
        this.\u0273 = new Point(0, 0);
        if (this.\u0272 == null) {
            return;
        }
        for (int i = 0; i < this.\u0272.length; ++i) {
            final Point \u0273 = this.\u0273;
            \u0273.y += this.\u0272[i].y;
            if (this.\u0273.x < this.\u0272[i].x) {
                this.\u0273.x = this.\u0272[i].x;
            }
        }
    }
}
