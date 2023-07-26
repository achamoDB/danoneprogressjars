// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.io.File;
import com.ms.wfc.io.IDataStream;
import com.ms.wfc.ui.Image;
import com.ms.wfc.io.MemoryStream;
import com.ms.wfc.ui.PictureBox;
import com.ms.wfc.ui.Control;

class PictureBoxBinding implements DataManagerBinding
{
    public void updateData(final Control control, final DataManagerField dataManagerField, final boolean b) {
        final PictureBox pictureBox = (PictureBox)control;
        final int \u00f1 = dataManagerField.\u00f1;
        boolean b2 = false;
        if (b) {
            if ((\u00f1 & 0x2000) != 0x0 || \u00f1 == 0) {
                final byte[] bytes = dataManagerField.getBytes();
                if (bytes != null) {
                    pictureBox.setImage(Image.loadImage((IDataStream)new MemoryStream(bytes)));
                    if (\u00f1 == 0) {
                        dataManagerField.\u00f1 = 8209;
                    }
                }
                else {
                    pictureBox.setImage((Image)null);
                }
            }
            else if (\u00f1 == 8) {
                final String string = dataManagerField.getString();
                if (string != null) {
                    pictureBox.setImage(Image.loadImage((IDataStream)new File(string, 3, Integer.MIN_VALUE, 1)));
                }
                else {
                    pictureBox.setImage((Image)null);
                }
            }
            else {
                b2 = true;
            }
        }
        else if ((\u00f1 & 0x2000) != 0x0 || \u00f1 == 0) {
            final Image image = pictureBox.getImage();
            if (image != null) {
                final MemoryStream memoryStream = new MemoryStream();
                image.save((IDataStream)memoryStream);
                if (memoryStream.getLength() != 0L) {
                    dataManagerField.setBytes(memoryStream.toByteArray());
                }
                else {
                    dataManagerField.setNull();
                }
                if (\u00f1 == 0) {
                    dataManagerField.\u00f1 = 8209;
                }
            }
            else {
                dataManagerField.setNull();
            }
        }
        else if (\u00f1 != 8) {
            b2 = true;
        }
        if (b2) {
            throw new DataException(6, "Data conversion failed on field " + ((dataManagerField.\u00ee == null) ? "???" : dataManagerField.\u00ee) + ": A picture control exspects a byte array (image data) or " + "string (image file name) data type");
        }
    }
}
