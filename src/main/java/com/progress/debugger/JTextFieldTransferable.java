// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.awt.datatransfer.DataFlavor;
import javax.swing.JTextField;
import java.awt.datatransfer.Transferable;

public class JTextFieldTransferable implements Transferable
{
    private JTextField watchTextField;
    public static final DataFlavor jTextFieldFlavor;
    private static final DataFlavor[] flavors;
    
    public JTextFieldTransferable(final JTextField watchTextField) {
        this.watchTextField = watchTextField;
    }
    
    public DataFlavor[] getTransferDataFlavors() {
        return JTextFieldTransferable.flavors;
    }
    
    public boolean isDataFlavorSupported(final DataFlavor dataFlavor) {
        for (int i = 0; i < JTextFieldTransferable.flavors.length; ++i) {
            if (dataFlavor.equals(JTextFieldTransferable.flavors[i])) {
                return true;
            }
        }
        return false;
    }
    
    public Object getTransferData(final DataFlavor dataFlavor) {
        if (!this.isDataFlavorSupported(dataFlavor)) {
            return null;
        }
        if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return this.watchTextField.getSelectedText();
        }
        if (dataFlavor.equals(JTextFieldTransferable.jTextFieldFlavor)) {
            return this.watchTextField.getSelectedText();
        }
        try {
            final String string = this.watchTextField.getText() + " (plain text flavor)";
            final int length = string.length();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            outputStreamWriter.write(string, 0, length);
            outputStreamWriter.flush();
            final byte[] byteArray = out.toByteArray();
            outputStreamWriter.close();
            return new ByteArrayInputStream(byteArray);
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    static {
        jTextFieldFlavor = new DataFlavor(JTextField.class, "Swing JTextField");
        flavors = new DataFlavor[] { DataFlavor.stringFlavor, new DataFlavor("text/plain; charset=ascii", "ASCII text"), JTextFieldTransferable.jTextFieldFlavor };
    }
}
