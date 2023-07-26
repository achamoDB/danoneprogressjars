import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_k4
{
    public static void a(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        for (int i = 0; i < vector2.size(); ++i) {
            final Object element = vector2.elementAt(i);
            if (!vector.contains(element)) {
                vector.addElement(element);
            }
        }
    }
    
    public static void b(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        for (int i = 0; i < vector2.size(); ++i) {
            final Object element = vector2.elementAt(i);
            if (vector.contains(element)) {
                vector.removeElement(element);
            }
        }
    }
    
    public static void c(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        int i = vector.size();
        while (i > 0) {
            --i;
            if (!vector2.contains(vector.elementAt(i))) {
                vector.removeElementAt(i);
            }
        }
    }
    
    public static void d(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        for (int i = 0; i < vector2.size(); ++i) {
            final Object element = vector2.elementAt(i);
            if (!vector.contains(element)) {
                vector.addElement(element);
            }
        }
    }
    
    public static boolean a(final byte[] array, final byte[] array2) {
        if (array == null && array2 == null) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean a(final byte[] array, final int n, final int n2, final byte[] array2) {
        return a(array, n, n2, array2, 0, array2.length);
    }
    
    public static boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array == null && array2 == null) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        if (n2 != n4) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (array[n + i] != array2[n3 + i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean a(final Vector vector, final byte[] array) {
        for (int i = 0; i < vector.size(); ++i) {
            if (a(vector.elementAt(i), array)) {
                return true;
            }
        }
        return false;
    }
    
    public static void e(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        int i = vector.size();
        while (i > 0) {
            --i;
            if (!a(vector2, vector.elementAt(i))) {
                vector.removeElementAt(i);
            }
        }
    }
    
    public static void f(final Vector vector, final Vector vector2) {
        if (vector == null || vector2 == null) {
            return;
        }
        for (int i = 0; i < vector2.size(); ++i) {
            final byte[] obj = vector2.elementAt(i);
            if (!a(vector, obj)) {
                vector.addElement(obj);
            }
        }
    }
    
    public static boolean g(final Vector vector, final Vector vector2) {
        final int size = vector.size();
        if (size != vector2.size()) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (!vector.elementAt(i).equals(vector2.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean h(final Vector vector, final Vector vector2) {
        final int size = vector.size();
        if (size != vector2.size()) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (!vector.contains(vector2.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean a(final psc_aj psc_aj, final psc_bg psc_bg) {
        final int c = psc_aj.c();
        psc_aj d;
        try {
            d = psc_bg.d(c);
        }
        catch (psc_g psc_g) {
            return false;
        }
        if (d == null) {
            return false;
        }
        switch (c) {
            case 18: {
                final psc_bm a = ((psc_bq)psc_aj).a();
                final psc_bm a2 = ((psc_bq)d).a();
                try {
                    for (int i = 0; i < a.c(); ++i) {
                        if (a2.b(a.a(i))) {
                            return true;
                        }
                    }
                }
                catch (psc_v psc_v) {
                    return false;
                }
                return false;
            }
            default: {
                final byte[] array = new byte[psc_aj.e(0)];
                final byte[] array2 = new byte[d.e(0)];
                if (array.length != array2.length) {
                    return false;
                }
                psc_aj.d(array, 0, 0);
                d.d(array2, 0, 0);
                for (int j = 0; j < array.length; ++j) {
                    if (array[j] != array2[j]) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    
    public static boolean a(final psc_bg psc_bg, final psc_bg psc_bg2) {
        if (psc_bg == null) {
            return true;
        }
        if (psc_bg2 == null) {
            return false;
        }
        try {
            for (int i = 0; i < psc_bg.a(); ++i) {
                if (!a(psc_bg.c(i), psc_bg2)) {
                    return false;
                }
            }
            return true;
        }
        catch (psc_g psc_g) {
            return false;
        }
    }
}
