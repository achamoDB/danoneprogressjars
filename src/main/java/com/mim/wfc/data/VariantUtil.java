// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.com.SafeArray;
import com.ms.com.Variant;

public class VariantUtil
{
    public static boolean variantEqual(final Variant variant, final Variant variant2) {
        final short getvt = variant.getvt();
        final short getvt2 = variant2.getvt();
        if ((getvt & 0xFFFFBFFF) != (getvt2 & 0xFFFFBFFF)) {
            return false;
        }
        if ((getvt & 0x2000) != 0x0) {
            return \u00fb(variant.toSafeArray(), variant2.toSafeArray());
        }
        if ((getvt & 0x4000) == 0x0 && (getvt2 & 0x4000) == 0x0) {
            switch (getvt & 0xFFF) {
                case 2: {
                    return variant.getShort() == variant2.getShort();
                }
                case 3: {
                    return variant.getInt() == variant2.getInt();
                }
                case 4: {
                    return variant.getFloat() == variant2.getFloat();
                }
                case 5: {
                    return variant.getDouble() == variant2.getDouble();
                }
                case 6: {
                    return variant.getCurrency() == variant2.getCurrency();
                }
                case 7: {
                    return variant.getDate() == variant2.getDate();
                }
                case 8: {
                    final String string = variant.getString();
                    final String string2 = variant2.getString();
                    return (string != null && string2 != null && string.equals(string2)) || (string == null && string2 == null);
                }
                case 9: {
                    return variant.getDispatch() == variant2.getDispatch();
                }
                case 11: {
                    return variant.getBoolean() == variant2.getBoolean();
                }
                case 12: {
                    return false;
                }
                case 13: {
                    return variant.getObject().equals(variant2.getObject());
                }
                case 17: {
                    return variant.getByte() == variant2.getByte();
                }
                default: {
                    return true;
                }
            }
        }
        else {
            final int n = getvt & 0xFFF;
            final int n2 = getvt2 & 0xFFF;
            short n3 = 0;
            short n4 = 0;
            int n5 = 0;
            int n6 = 0;
            float n7 = 0.0f;
            float n8 = 0.0f;
            double n9 = 0.0;
            double n10 = 0.0;
            long n11 = 0L;
            long n12 = 0L;
            double n13 = 0.0;
            double n14 = 0.0;
            String s = null;
            Object anObject = null;
            Object o = null;
            Object o2 = null;
            boolean b = false;
            boolean b2 = false;
            Object o3 = null;
            Object o4 = null;
            byte b3 = 0;
            byte b4 = 0;
            if ((getvt & 0x4000) != 0x0) {
                switch (n) {
                    case 2: {
                        n3 = variant.getShortRef();
                        break;
                    }
                    case 3: {
                        n5 = variant.getIntRef();
                        break;
                    }
                    case 4: {
                        n7 = variant.getFloatRef();
                        break;
                    }
                    case 5: {
                        n9 = variant.getDoubleRef();
                        break;
                    }
                    case 6: {
                        n11 = variant.getCurrencyRef();
                        break;
                    }
                    case 7: {
                        n13 = variant.getDateRef();
                        break;
                    }
                    case 8: {
                        s = variant.getStringRef();
                        break;
                    }
                    case 9: {
                        o = variant.getDispatchRef();
                        break;
                    }
                    case 11: {
                        b = variant.getBooleanRef();
                        break;
                    }
                    case 13: {
                        o3 = variant.getObjectRef();
                        break;
                    }
                    case 17: {
                        b3 = variant.getByteRef();
                        break;
                    }
                }
            }
            else {
                switch (n) {
                    case 2: {
                        n3 = variant.getShort();
                        break;
                    }
                    case 3: {
                        n5 = variant.getInt();
                        break;
                    }
                    case 4: {
                        n7 = variant.getFloat();
                        break;
                    }
                    case 5: {
                        n9 = variant.getDouble();
                        break;
                    }
                    case 6: {
                        n11 = variant.getCurrency();
                        break;
                    }
                    case 7: {
                        n13 = variant.getDate();
                        break;
                    }
                    case 8: {
                        s = variant.getString();
                        break;
                    }
                    case 9: {
                        o = variant.getDispatch();
                        break;
                    }
                    case 11: {
                        b = variant.getBoolean();
                        break;
                    }
                    case 13: {
                        o3 = variant.getObject();
                        break;
                    }
                    case 17: {
                        b3 = variant.getByte();
                        break;
                    }
                }
            }
            if ((getvt2 & 0x4000) != 0x0) {
                switch (n2) {
                    case 2: {
                        n4 = variant2.getShortRef();
                        break;
                    }
                    case 3: {
                        n6 = variant2.getIntRef();
                        break;
                    }
                    case 4: {
                        n8 = variant2.getFloatRef();
                        break;
                    }
                    case 5: {
                        n10 = variant2.getDoubleRef();
                        break;
                    }
                    case 6: {
                        n12 = variant2.getCurrencyRef();
                        break;
                    }
                    case 7: {
                        n14 = variant2.getDateRef();
                        break;
                    }
                    case 8: {
                        anObject = variant2.getStringRef();
                        break;
                    }
                    case 9: {
                        o2 = variant2.getDispatchRef();
                        break;
                    }
                    case 11: {
                        b2 = variant2.getBooleanRef();
                        break;
                    }
                    case 13: {
                        o4 = variant2.getObjectRef();
                        break;
                    }
                    case 17: {
                        b4 = variant2.getByteRef();
                        break;
                    }
                }
            }
            else {
                switch (n2) {
                    case 2: {
                        n4 = variant2.getShort();
                        break;
                    }
                    case 3: {
                        n6 = variant2.getInt();
                        break;
                    }
                    case 4: {
                        n8 = variant2.getFloat();
                        break;
                    }
                    case 5: {
                        n10 = variant2.getDouble();
                        break;
                    }
                    case 6: {
                        n12 = variant2.getCurrency();
                        break;
                    }
                    case 7: {
                        n14 = variant2.getDate();
                        break;
                    }
                    case 8: {
                        anObject = variant2.getString();
                        break;
                    }
                    case 9: {
                        o2 = variant2.getDispatch();
                        break;
                    }
                    case 11: {
                        b2 = variant2.getBoolean();
                        break;
                    }
                    case 13: {
                        o4 = variant2.getObject();
                        break;
                    }
                    case 17: {
                        b4 = variant2.getByte();
                        break;
                    }
                }
            }
            switch (n) {
                case 2: {
                    return n3 == n4;
                }
                case 3: {
                    return n5 == n6;
                }
                case 4: {
                    return n7 == n8;
                }
                case 5: {
                    return n9 == n10;
                }
                case 6: {
                    return n11 == n12;
                }
                case 7: {
                    return n13 == n14;
                }
                case 8: {
                    return (s != null && anObject != null && s.equals(anObject)) || (s == null && anObject == null);
                }
                case 9: {
                    return o == o2;
                }
                case 11: {
                    return b == b2;
                }
                case 12: {
                    return false;
                }
                case 13: {
                    return o3 == o4;
                }
                case 17: {
                    return b3 == b4;
                }
                default: {
                    return true;
                }
            }
        }
    }
    
    private static boolean \u00fb(final SafeArray safeArray, final SafeArray safeArray2) {
        final int getvt = safeArray.getvt();
        if (getvt != safeArray2.getvt()) {
            return false;
        }
        if (safeArray.getNumDim() != 1 || safeArray2.getNumDim() != 1) {
            return false;
        }
        final int lBound = safeArray.getLBound();
        final int lBound2 = safeArray2.getLBound();
        final int uBound = safeArray.getUBound();
        final int uBound2 = safeArray2.getUBound();
        if (lBound != lBound2 || uBound != uBound2) {
            return false;
        }
        for (int i = lBound; i <= uBound; ++i) {
            switch (getvt) {
                case 2: {
                    if (safeArray.getShort(i) != safeArray2.getShort(i)) {
                        return false;
                    }
                    break;
                }
                case 3: {
                    if (safeArray.getInt(i) != safeArray2.getInt(i)) {
                        return false;
                    }
                    break;
                }
                case 4: {
                    if (safeArray.getFloat(i) != safeArray2.getFloat(i)) {
                        return false;
                    }
                    break;
                }
                case 5: {
                    if (safeArray.getDouble(i) != safeArray2.getDouble(i)) {
                        return false;
                    }
                    break;
                }
                case 8: {
                    if (!safeArray.getString(i).equals(safeArray2.getString(i))) {
                        return false;
                    }
                    break;
                }
                case 11: {
                    if (safeArray.getBoolean(i) != safeArray2.getBoolean(i)) {
                        return false;
                    }
                    break;
                }
                case 12: {
                    if (!variantEqual(safeArray.getVariant(i), safeArray2.getVariant(i))) {
                        return false;
                    }
                    break;
                }
                case 17: {
                    if (safeArray.getByte(i) != safeArray2.getByte(i)) {
                        return false;
                    }
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        return true;
    }
}
