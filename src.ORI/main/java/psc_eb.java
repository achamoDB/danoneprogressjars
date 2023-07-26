import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_eb
{
    private Vector a;
    
    public static psc_eb a(final psc_ah psc_ah, final int n) {
        switch (n) {
            case 0:
            case 2:
            case 3: {
                return new psc_eb(psc_ah);
            }
            case 4: {
                return new psc_ec(psc_ah);
            }
            case 1: {
                return new psc_ed(psc_ah);
            }
            default: {
                return new psc_eb(psc_ah);
            }
        }
    }
    
    protected psc_eb(final psc_ah psc_ah) {
        this.a = new Vector();
    }
    
    public String[] k() {
        final int size = this.a.size();
        final String[] array = new String[size];
        for (int i = 0; i < size; ++i) {
            array[i] = ((psc_ea)this.a.elementAt(i)).b();
        }
        return array;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().toString());
        sb.append('[');
        for (int i = 0; i < this.a.size(); ++i) {
            sb.append(((psc_ea)this.a.elementAt(i)).b());
            if (i != this.a.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
    public void l() {
    }
    
    protected int m() {
        return this.a.size();
    }
    
    protected psc_ea a(final int index) {
        if (index < 0 || index >= this.a.size()) {
            return null;
        }
        return this.a.elementAt(index);
    }
    
    protected void a(final psc_ea obj) {
        this.a.addElement(obj);
    }
}
