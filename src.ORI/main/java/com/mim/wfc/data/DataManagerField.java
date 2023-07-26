// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.com.SafeArray;
import com.ms.wfc.app.Time;
import com.ms.com.Variant;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.Component;

public class DataManagerField extends Component
{
    String \u00ee;
    Control \u00ef;
    Variant \u00f0;
    int \u00f1;
    boolean \u00f2;
    boolean \u00f3;
    boolean \u00f4;
    boolean \u00f5;
    
    public long getCurrency() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toCurrency();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 6, this.\u00ee);
            }
        }
        return 0L;
    }
    
    public void setCurrency(final long n) {
        this.\u00f0.putCurrency(n);
    }
    
    public void setDateAndTime(final Time time) {
        if (time == null) {
            this.\u00f0.putNull();
            return;
        }
        this.\u00f0.putDate(time.toDouble());
    }
    
    public void setUpdatable(final boolean \u00f2) {
        this.\u00f2 = \u00f2;
    }
    
    public boolean getUpdatable() {
        return this.\u00f2;
    }
    
    public String getString() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toString();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 8, this.\u00ee);
            }
        }
        return null;
    }
    
    public void setString(final String s) {
        if (s == null) {
            this.\u00f0.putNull();
            return;
        }
        this.\u00f0.putString(s);
    }
    
    public void setNull() {
        this.\u00f0.putNull();
    }
    
    public float getFloat() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toFloat();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 4, this.\u00ee);
            }
        }
        return 0.0f;
    }
    
    public void setFloat(final float n) {
        this.\u00f0.putFloat(n);
    }
    
    public void setName(final String \u00ee) {
        this.\u00ee = \u00ee;
    }
    
    public String getName() {
        return this.\u00ee;
    }
    
    public void setType(final int \u00f1) {
        this.\u00f1 = \u00f1;
    }
    
    public int getType() {
        return this.\u00f1;
    }
    
    public byte[] getBytes() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            if ((getvt & 0x2000) == 0x0) {
                DataException.failVariantType(getvt, 8209, this.\u00ee);
            }
            try {
                return this.\u00f0.toSafeArray().toByteArray();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 8209, this.\u00ee);
            }
        }
        return null;
    }
    
    public void setBytes(final byte[] array) {
        if (array == null || array.length == 0) {
            this.\u00f0.putNull();
            return;
        }
        final SafeArray safeArray = new SafeArray(17, array.length);
        safeArray.fromByteArray(array);
        this.\u00f0.putSafeArray(safeArray);
    }
    
    public short getShort() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toShort();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 2, this.\u00ee);
            }
        }
        return -1;
    }
    
    public void setShort(final short n) {
        this.\u00f0.putShort(n);
    }
    
    public Variant getValue() {
        return this.\u00f0;
    }
    
    public void setValue(final Variant \u00f0) {
        this.\u00f0 = \u00f0;
    }
    
    public DataManagerField() {
        this.\u00f1 = 0;
        this.\u00f2 = true;
        this.\u00f3 = true;
        this.\u00f4 = false;
        this.\u00f5 = true;
        this.\u00f0 = new Variant();
    }
    
    public DataManagerField(final String \u00ee) {
        this();
        this.\u00ee = \u00ee;
    }
    
    public DataManagerField(final String s, final int \u00f1, final boolean \u00f2) {
        this(s);
        this.\u00f1 = \u00f1;
        this.\u00f2 = \u00f2;
    }
    
    public int getInt() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toInt();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 3, this.\u00ee);
            }
        }
        return -1;
    }
    
    public void setInt(final int n) {
        this.\u00f0.putInt(n);
    }
    
    public Time getDate() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return new Time(this.\u00f0.toDate());
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 7, this.\u00ee);
            }
        }
        return null;
    }
    
    public void setDate(final Time time) {
        if (time == null) {
            this.\u00f0.putNull();
            return;
        }
        this.\u00f0.putDate(new Time(time.getYear(), time.getMonth(), time.getDay()).toDouble());
    }
    
    static int \u00ee(final int n) {
        int n2 = 0;
        switch (n) {
            case 20: {
                n2 = 3;
                break;
            }
            case 128: {
                n2 = 8209;
                break;
            }
            case 11: {
                n2 = 11;
                break;
            }
            case 8: {
                n2 = 8;
                break;
            }
            case 129: {
                n2 = 8;
                break;
            }
            case 6: {
                n2 = 6;
                break;
            }
            case 7: {
                n2 = 7;
                break;
            }
            case 133: {
                n2 = 7;
                break;
            }
            case 134: {
                n2 = 7;
                break;
            }
            case 14: {
                n2 = 5;
                break;
            }
            case 5: {
                n2 = 5;
                break;
            }
            case 9: {
                n2 = 9;
                break;
            }
            case 3: {
                n2 = 3;
                break;
            }
            case 205: {
                n2 = 8209;
                break;
            }
            case 201: {
                n2 = 8;
                break;
            }
            case 203: {
                n2 = 8;
                break;
            }
            case 131: {
                n2 = 5;
                break;
            }
            case 4: {
                n2 = 4;
                break;
            }
            case 2: {
                n2 = 2;
                break;
            }
            case 16: {
                n2 = 17;
                break;
            }
            case 21: {
                n2 = 3;
                break;
            }
            case 19: {
                n2 = 3;
                break;
            }
            case 18: {
                n2 = 2;
                break;
            }
            case 17: {
                n2 = 17;
                break;
            }
            case 204: {
                n2 = 8209;
                break;
            }
            case 200: {
                n2 = 8;
                break;
            }
            case 12: {
                n2 = 12;
                break;
            }
            case 139: {
                n2 = 5;
                break;
            }
            case 202: {
                n2 = 8;
                break;
            }
            case 130: {
                n2 = 8;
                break;
            }
        }
        return n2;
    }
    
    public Object getDispatch() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toDispatch();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 9, this.\u00ee);
            }
        }
        return null;
    }
    
    public void setDispatch(final Object o) {
        if (o == null) {
            this.\u00f0.putNull();
            return;
        }
        this.\u00f0.putDispatch(o);
    }
    
    public byte getByte() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toByte();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 17, this.\u00ee);
            }
        }
        return -1;
    }
    
    public void setByte(final byte b) {
        this.\u00f0.putByte(b);
    }
    
    public boolean getBoolean() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toBoolean();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 11, this.\u00ee);
            }
        }
        return false;
    }
    
    public void setBoolean(final boolean b) {
        this.\u00f0.putBoolean(b);
    }
    
    public boolean isNull() {
        return this.\u00f0.getvt() == 1;
    }
    
    public void setAutoSelect(final boolean \u00f3) {
        this.\u00f3 = \u00f3;
    }
    
    public boolean getAutoSelect() {
        return this.\u00f3;
    }
    
    public void setBoundControl(final Control \u00ef) {
        this.\u00ef = \u00ef;
    }
    
    public Control getBoundControl() {
        return this.\u00ef;
    }
    
    public double getDouble() {
        final short getvt = this.\u00f0.getvt();
        if (getvt != 1) {
            try {
                return this.\u00f0.toDouble();
            }
            catch (ClassCastException ex) {
                DataException.failVariantType(getvt, 5, this.\u00ee);
            }
        }
        return 0.0;
    }
    
    public void setDouble(final double n) {
        this.\u00f0.putDouble(n);
    }
}
