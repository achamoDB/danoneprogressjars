import java.security.SecureRandom;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kt extends psc_an implements psc_dl, Cloneable, Serializable
{
    private byte[] a;
    private transient psc_dd b;
    public static final int c = 8;
    private psc_km d;
    
    public psc_kt() {
        this.d = new psc_km();
    }
    
    public psc_kt(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Expected no algorithm parameters");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_di.a(array, n, n2);
    }
    
    public byte[] a(final byte[] array) {
        return array;
    }
    
    public String d() {
        return "DESX";
    }
    
    public int g() {
        return 8;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int n) {
    }
    
    public byte[] a(final psc_am psc_am, final boolean b, final psc_di psc_di, final psc_dm psc_dm) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_al b(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.h();
        objectOutputStream.defaultWriteObject();
        this.i();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.j();
    }
    
    private void h() {
        this.f();
    }
    
    private void i() {
        this.e();
    }
    
    private void j() {
        if (this.a == null) {
            return;
        }
        (this.b = psc_au.b(this.a)).c();
        this.d.e();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kt psc_kt = new psc_kt();
        psc_kt.d = (psc_km)this.d.clone();
        if (this.a != null) {
            psc_kt.a = (byte[])psc_au.a(this.a, this.b);
            psc_kt.b = psc_au.a(psc_kt.a);
        }
        return psc_kt;
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        if (this.b != null) {
            this.b.e();
            this.b = null;
            this.a = null;
        }
        psc_j3 e;
        try {
            this.a = ((psc_j8)psc_dc).a("Clear");
            e = ((psc_j8)psc_dc).e();
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        this.d.a(e, secureRandom);
        (this.b = psc_au.b(this.a)).c();
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        if (this.b != null) {
            this.b.e();
            this.b = null;
            this.a = null;
        }
        psc_j3 e;
        try {
            this.a = ((psc_j8)psc_dc).a("Clear");
            e = ((psc_j8)psc_dc).e();
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        this.d.b(e, secureRandom);
        (this.b = psc_au.b(this.a)).c();
    }
    
    public int a(final byte[] array, int n, final byte[] array2, int n2) {
        for (int i = 0; i < 8; ++i, ++n) {
            this.a[i] = (byte)(this.a[i + 8] ^ array[n]);
        }
        this.d.a(this.a, 0, array2, n2);
        for (int j = 16; j < 24; ++j, ++n2) {
            final int n3 = n2;
            array2[n3] ^= this.a[j];
        }
        return 8;
    }
    
    public int b(final byte[] array, int n, final byte[] array2, int n2) {
        for (int i = 0; i < 8; ++i, ++n) {
            this.a[i] = (byte)(this.a[i + 16] ^ array[n]);
        }
        this.d.a(this.a, 0, array2, n2);
        for (int j = 8; j < 16; ++j, ++n2) {
            final int n3 = n2;
            array2[n3] ^= this.a[j];
        }
        return 8;
    }
    
    public void e() {
        if (this.b != null) {
            this.b.c();
        }
        this.d.e();
    }
    
    public void f() {
        if (this.b != null) {
            this.b.d();
        }
        this.d.f();
    }
    
    public void y() {
        super.y();
        this.d.y();
        psc_au.c(this.a, this.b);
        this.b = null;
        this.a = null;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
