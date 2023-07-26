// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Prodebug
{
    boolean packFrame;
    
    public Prodebug() {
        this.packFrame = false;
        final Frame1 frame1 = new Frame1();
        if (this.packFrame) {
            frame1.pack();
        }
        else {
            frame1.validate();
        }
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension size = frame1.getSize();
        if (size.height > screenSize.height) {
            size.height = screenSize.height;
        }
        if (size.width > screenSize.width) {
            size.width = screenSize.width;
        }
        frame1.setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
    }
    
    public static void main(final String[] array) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Prodebug();
        }
        catch (Exception ex) {}
    }
}
