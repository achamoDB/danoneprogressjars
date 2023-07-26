// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.sql.Clob;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Date;
import java.math.BigDecimal;
import com.progress.open4gl.Open4GLError;
import java.util.GregorianCalendar;
import com.progress.open4gl.Parameter;

public class JdbcDataType
{
    public static int defaultType(final int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 41: {
                return 41;
            }
            case 5: {
                return 5;
            }
            case 7: {
                return 7;
            }
            case 8: {
                return 8;
            }
            case 10: {
                return 7;
            }
            case 11: {
                return 11;
            }
            case 13: {
                return 8;
            }
            case 14: {
                return 7;
            }
            case 15: {
                return 15;
            }
            case 16: {
                return 16;
            }
            case 17: {
                return 17;
            }
            case 34: {
                return 34;
            }
            case 40: {
                return 40;
            }
            case 39: {
                return 39;
            }
            case 36: {
                return 36;
            }
            case 37: {
                return 37;
            }
            default: {
                return 0;
            }
        }
    }
    
    static int validateInputValue(final Object o) throws ClientException {
        final int jdbcProType = getJdbcProType(o);
        if (jdbcProType == -1) {
            throw new ClientException(3, 13L, new Object[] { o.getClass().toString() });
        }
        return jdbcProType;
    }
    
    static int validateInputValue(final Object o, final int n) throws ClientException {
        int jdbcProType = getJdbcProType(o);
        if (jdbcProType == -1) {
            throw new ClientException(3, 13L, new Object[] { o.getClass().toString() });
        }
        if (jdbcProType == 16) {
            return n;
        }
        if (!legalConversion(n, jdbcProType)) {
            final Class<?> class1 = o.getClass();
            final String name = class1.getName();
            String string;
            if (class1.isArray()) {
                String str;
                if (name.charAt(1) == 'B') {
                    str = "byte ";
                }
                else {
                    str = "object ";
                }
                string = str + "array";
            }
            else {
                string = name;
            }
            throw new ClientException(3, 14L, new Object[] { string, Parameter.proToName(n) });
        }
        if (o instanceof GregorianCalendar || (o instanceof String && n == 19) || (o instanceof byte[] && n == 18)) {
            jdbcProType = n;
        }
        return jdbcProType;
    }
    
    static Object convertInputObject(final Object o, final int n) {
        if (o == null) {
            return null;
        }
        if (o instanceof Integer) {
            switch (n) {
                case 4: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof String) {
            switch (n) {
                case 1:
                case 19: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof Boolean) {
            switch (n) {
                case 3: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof BigDecimal) {
            switch (n) {
                case 5: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof Date) {
            switch (n) {
                case 2: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof Timestamp) {
            switch (n) {
                case 34: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof GregorianCalendar) {
            switch (n) {
                case 2:
                case 34:
                case 40: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof Long) {
            switch (n) {
                case 7:
                case 41: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof byte[]) {
            switch (n) {
                case 8:
                case 18: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else if (o instanceof Blob) {
            switch (n) {
                case 18: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
        else {
            if (!(o instanceof Clob)) {
                throw new Open4GLError(12L, null);
            }
            switch (n) {
                case 19: {
                    return o;
                }
                default: {
                    throw new Open4GLError(12L, null);
                }
            }
        }
    }
    
    private static boolean legalConversion(final int n, final int n2) {
        Label_0477: {
            switch (n2) {
                case 4: {
                    switch (n) {
                        case 4:
                        case 7:
                        case 10:
                        case 14: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 41: {
                    switch (n) {
                        case 7:
                        case 10:
                        case 14:
                        case 41: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n) {
                        case 1:
                        case 19: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 3: {
                    if (n == 3) {
                        return true;
                    }
                    break;
                }
                case 5: {
                    if (n == 5) {
                        return true;
                    }
                    break;
                }
                case 2: {
                    switch (n) {
                        case 2:
                        case 34:
                        case 40: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 7: {
                    switch (n) {
                        case 7:
                        case 10:
                        case 14:
                        case 41: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 8: {
                    switch (n) {
                        case 8:
                        case 13:
                        case 18: {
                            return true;
                        }
                        default: {
                            break Label_0477;
                        }
                    }
                    break;
                }
                case 34: {
                    if (n == 34) {
                        return true;
                    }
                    break;
                }
                case 18: {
                    if (n == 18) {
                        return true;
                    }
                    break;
                }
                case 19: {
                    if (n == 19) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
    
    static int getJdbcProType(final Object o) {
        if (o == null) {
            return 16;
        }
        if (o instanceof Integer) {
            return 4;
        }
        if (o instanceof String) {
            return 1;
        }
        if (o instanceof Boolean) {
            return 3;
        }
        if (o instanceof BigDecimal) {
            return 5;
        }
        if (o instanceof Date) {
            return 2;
        }
        if (o instanceof GregorianCalendar) {
            return 2;
        }
        if (o instanceof Long) {
            return 7;
        }
        if (o instanceof byte[]) {
            return 8;
        }
        if (o instanceof Timestamp) {
            return 34;
        }
        if (o instanceof Blob) {
            return 18;
        }
        if (o instanceof Clob) {
            return 19;
        }
        return -1;
    }
}
