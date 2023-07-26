import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fm implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    private int f;
    private String g;
    private String h;
    protected int i;
    protected psc_n j;
    private static final int k = 4194305;
    private static final int l = 4194306;
    
    public psc_fm(final int f, final byte[] array, final int n, final int i) throws psc_v {
        this.j = null;
        this.i = i;
        this.f = f;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i psc_i3 = null;
            final psc_j psc_j = new psc_j();
            switch (f) {
                case 0: {
                    psc_i = new psc_z(i | 0x400001);
                    break;
                }
                case 1: {
                    psc_i = new psc_z(i | 0x400002);
                    break;
                }
                case 2:
                case 3:
                case 4: {
                    psc_i = new psc_z(i);
                    break;
                }
            }
            switch (f) {
                case 0:
                case 4: {
                    psc_i2 = new psc_fl(0, 1, 3);
                    psc_i3 = new psc_aa(0, 1, 2);
                    break;
                }
                case 1:
                case 2:
                case 3: {
                    psc_i2 = new psc_fl(0, 1, 16);
                    psc_i3 = new psc_aa(0, 1, 16);
                    break;
                }
            }
            psc_l.a(array, n, new psc_i[] { psc_i, psc_i2, psc_i3, psc_j });
            if (psc_i2.a) {
                this.g = new String(psc_i2.b, psc_i2.c, psc_i2.d);
            }
            if (psc_i3.a) {
                this.h = new String(psc_i3.b, psc_i3.c, psc_i3.d);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the ORName.");
        }
    }
    
    public psc_fm(final int f) {
        this.j = null;
        this.f = f;
    }
    
    public int b() {
        return this.f;
    }
    
    public void a(final String g) {
        if (g != null) {
            this.g = g;
        }
    }
    
    public void b(final String h) {
        if (h != null) {
            this.h = h;
        }
    }
    
    public String c() {
        return this.g;
    }
    
    public String d() {
        return this.h;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.g != null) {
            sb.append(this.g);
        }
        if (this.h != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(this.h);
        }
        return sb.toString();
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Unable to determine length of the BER");
        }
    }
    
    public int a(final int i) {
        this.i = i;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.j == null || n2 != this.i) {
                this.a(n2);
            }
            final int a = this.j.a(array, n);
            this.j = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.j = null;
            throw new psc_v("Unable to encode ORName");
        }
    }
    
    private int a() {
        if (this.j != null && this.i == this.i) {
            return 0;
        }
        try {
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i psc_i3 = null;
            final psc_j psc_j = new psc_j();
            switch (this.f) {
                case 0: {
                    psc_i = new psc_z(this.i | 0x400001, 0);
                    break;
                }
                case 1: {
                    psc_i = new psc_z(this.i | 0x400002, 0);
                    break;
                }
                case 2:
                case 3:
                case 4: {
                    psc_i = new psc_z(this.i, 0);
                    break;
                }
            }
            if (this.g != null) {
                switch (this.f) {
                    case 0:
                    case 4: {
                        psc_i2 = new psc_fl(0, true, 0, this.g, 1, 3);
                        break;
                    }
                    case 1:
                    case 2:
                    case 3: {
                        psc_i2 = new psc_fl(0, true, 0, this.g, 1, 16);
                        break;
                    }
                }
                this.j = new psc_n(new psc_i[] { psc_i, psc_i2, psc_j });
            }
            else {
                switch (this.f) {
                    case 0:
                    case 4: {
                        psc_i3 = new psc_aa(0, true, 0, this.h, 1, 2);
                        break;
                    }
                    case 1:
                    case 2:
                    case 3: {
                        psc_i3 = new psc_aa(0, true, 0, this.h, 1, 16);
                        break;
                    }
                }
                this.j = new psc_n(new psc_i[] { psc_i, psc_i3, psc_j });
            }
            return this.j.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fm)) {
            return false;
        }
        final psc_fm psc_fm = (psc_fm)o;
        if (this.f != psc_fm.f) {
            return false;
        }
        if (this.g != null) {
            if (!this.g.equals(psc_fm.g)) {
                return false;
            }
        }
        else if (psc_fm.g != null) {
            return false;
        }
        if (this.h != null) {
            if (!this.h.equals(psc_fm.h)) {
                return false;
            }
        }
        else if (psc_fm.h != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fm psc_fm = new psc_fm(this.f);
        if (this.h != null) {
            psc_fm.h = new String(this.h);
        }
        if (this.g != null) {
            psc_fm.g = new String(this.g);
        }
        psc_fm.i = this.i;
        if (this.j != null) {
            psc_fm.a();
        }
        return psc_fm;
    }
}
