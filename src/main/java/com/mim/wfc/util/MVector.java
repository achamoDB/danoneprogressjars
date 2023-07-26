// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

import java.util.Vector;

public class MVector extends Vector
{
    public Object[] toArray() {
        final int size = this.size();
        if (size == 0) {
            return null;
        }
        final Object[] array = new Object[size];
        for (int i = 0; i < size; ++i) {
            array[i] = super.elementData[i];
        }
        return array;
    }
    
    public void sort() {
        this.sort(0, this.size());
    }
    
    public void sort(int n, int n2) {
        if (n2 < 2) {
            return;
        }
        int[] array;
        if (n2 > 8) {
            array = new int[60];
        }
        else {
            array = null;
        }
        int n3 = 0;
        int i = n2 - 1;
        while (true) {
            n2 = 1 + i - n;
            if (n2 <= 8) {
                while (i > n) {
                    int n4 = n;
                    for (int j = n + 1; j <= i; ++j) {
                        if (this.\u03e9(j, n4) > 0) {
                            n4 = j;
                        }
                    }
                    this.swap(n4, i);
                    --i;
                }
            }
            else {
                this.swap(n + n2 / 2, n);
                int n5 = n;
                int n6 = i + 1;
                while (true) {
                    if (++n5 > i || this.\u03e9(n5, n) > 0) {
                        while (--n6 > n && this.\u03e9(n6, n) >= 0) {}
                        if (n6 < n5) {
                            break;
                        }
                        this.swap(n5, n6);
                    }
                }
                this.swap(n, n6);
                if (n6 - 1 - n >= i - n5) {
                    if (n + 1 < n6) {
                        array[n3++] = n;
                        array[n3++] = n6 - 1;
                    }
                    if (n5 < i) {
                        n = n5;
                        continue;
                    }
                }
                else {
                    if (n5 < i) {
                        array[n3++] = n5;
                        array[n3++] = i;
                    }
                    if (n + 1 < n6) {
                        i = n6 - 1;
                        continue;
                    }
                }
            }
            if (n3 == 0) {
                break;
            }
            i = array[--n3];
            n = array[--n3];
        }
    }
    
    public Object[] getArray() {
        return super.elementData;
    }
    
    public String[] toStringArray() {
        final int size = this.size();
        if (size == 0) {
            return null;
        }
        final String[] array = new String[size];
        for (int i = 0; i < size; ++i) {
            array[i] = super.elementData[i].toString();
        }
        return array;
    }
    
    protected final void swap(final int n, final int n2) {
        final Object element = this.elementAt(n);
        this.setElementAt(this.elementAt(n2), n);
        this.setElementAt(element, n2);
    }
    
    int \u03e9(final int index, final int index2) {
        return this.elementAt(index).compareTo(this.elementAt(index2));
    }
}
