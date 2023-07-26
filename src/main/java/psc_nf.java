import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_nf extends psc_an
{
    private psc_ng a;
    protected boolean b;
    
    protected psc_nf() {
    }
    
    public static psc_nf b(final psc_ng a) throws psc_ao, psc_be, psc_en {
        if (a == null) {
            throw new psc_be("A null JSAFE_SessionSpec was passed in.");
        }
        final psc_bd[] a2 = a.a();
        if (a2 == null) {
            throw new psc_en("No DeviceBuilders found for this JSAFE_SessionSpec");
        }
        for (int i = 0; i < a2.length; ++i) {
            final psc_nf a3 = a2[i].a(a);
            if (a3 != null) {
                a3.b = true;
                a3.a = a;
                return a3;
            }
        }
        throw new psc_ao("A JSAFE_Session object is not available");
    }
    
    public abstract void c();
    
    public abstract String d();
    
    public psc_ng e() {
        return this.a;
    }
    
    boolean a(final psc_ng psc_ng) {
        return psc_ng.hashCode() == this.a.hashCode();
    }
    
    public Object a(final String s) {
        return null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone a JSAFE_Session");
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        throw new IOException("Cannot serialize a JSAFE_Session");
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        throw new IOException("Cannot deserialize a JSAFE_Session");
    }
    
    public void y() {
        this.c();
    }
}
