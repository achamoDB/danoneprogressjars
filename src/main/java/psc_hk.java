import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_hk
{
    static psc_h5 a(final int n, final OutputStream outputStream, final psc_c2 psc_c2, final String s, final psc_da psc_da, final psc_da psc_da2, final psc_d2 psc_d2) throws psc_b, psc_c, IOException, psc_d {
        final byte[] array = new byte[32];
        psc_c2.q().nextBytes(array);
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 3; i >= 0; --i) {
            array[i] = (byte)(currentTimeMillis & 0xFFL);
            currentTimeMillis >>= 8;
        }
        byte[] e = new byte[0];
        if (psc_d2 != null) {
            e = psc_d2.e();
        }
        final psc_dw[] c = psc_c2.c();
        if (c == null) {
            throw new psc_d("no cipher suites have been set");
        }
        final byte[][] a = psc_ik.a(c, n, false);
        final psc_c5[] j = psc_c2.i();
        final byte[] array2 = new byte[j.length];
        for (int k = 0; k < j.length; ++k) {
            array2[k] = (byte)j[k].a(n);
        }
        final psc_hl psc_hl = new psc_hl(n, array, e, a, array2);
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("STATE: Sending Client Hello");
            psc_c2.z().println(psc_hl.toString());
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        psc_hl.a(byteArrayOutputStream);
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            psc_da.a(byteArray, 0, byteArray.length);
            psc_da2.a(byteArray, 0, byteArray.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("Digesting data failed: " + psc_ap.getMessage());
        }
        final ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        byteArrayOutputStream2.write(22);
        byteArrayOutputStream2.write((byte)(n >> 8));
        byteArrayOutputStream2.write((byte)n);
        byteArrayOutputStream2.write((byte)(byteArray.length >> 8));
        byteArrayOutputStream2.write((byte)byteArray.length);
        byteArrayOutputStream2.write(byteArray, 0, byteArray.length);
        byteArrayOutputStream2.flush();
        outputStream.write(byteArrayOutputStream2.toByteArray());
        byteArrayOutputStream2.close();
        outputStream.flush();
        return new psc_h5(byteArray, psc_hl.b(), psc_hl.c(), psc_hl.d(), psc_hl.e(), 768);
    }
    
    static psc_hj a(final byte[] buf, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws psc_b, psc_c, psc_d, Exception {
        final psc_hl psc_hl = new psc_hl();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        psc_hl.a(byteArrayInputStream);
        return a(buf, inputStream, outputStream, s, socket, psc_hl, psc_c2, psc_d1);
    }
    
    static psc_hj a(final psc_h5 psc_h5, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws psc_b, psc_c, psc_d, Exception {
        final byte[] array = new byte[32];
        final int length = psc_h5.b().length;
        System.arraycopy(psc_h5.b(), 0, array, 32 - length, length);
        return a(psc_h5.a(), inputStream, outputStream, s, socket, new psc_hl(psc_h5.f(), array, psc_h5.c(), psc_h5.d(), psc_h5.e()), psc_c2, psc_d1);
    }
    
    static psc_hj a(final byte[] array, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_hl psc_hl, final psc_c2 psc_c2, final psc_d1 psc_d1) throws psc_b, psc_c, Exception {
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_hl.toString());
        }
        final psc_da a = psc_da.a("MD5", "Java");
        a.i();
        a.a(array, 0, array.length);
        final psc_da a2 = psc_da.a("SHA1", "Java");
        a2.i();
        a2.a(array, 0, array.length);
        final psc_hn psc_hn = new psc_hn(socket, inputStream, a, a2);
        psc_hn.a(psc_c2.v(), psc_c2.z());
        final psc_ho psc_ho = new psc_ho(outputStream, a, a2);
        psc_ho.a(psc_c2.v(), psc_c2.z());
        final psc_hp psc_hp = new psc_hp(psc_c2, psc_hl, psc_hn, psc_ho, s);
        psc_d1.a(psc_hp.f());
        psc_d1.a(psc_hp.g());
        psc_d1.a(psc_hp.i());
        psc_d1.a(psc_hp.h());
        psc_d1.a(768);
        final psc_hz psc_hz = new psc_hz(inputStream, psc_ho, a, a2, socket, psc_hp.h(), true, psc_c2, psc_hp.f(), psc_hp.g());
        psc_hz.a(psc_c2.v(), psc_c2.z());
        psc_ho psc_ho2;
        if (psc_c2.u()) {
            psc_ho2 = new psc_h0(outputStream, a, a2, socket, psc_hp.f(), psc_hp.g());
            ((psc_h0)psc_ho2).a(psc_c2.v(), psc_c2.z());
            ((psc_h0)psc_ho2).b(768);
        }
        else {
            psc_ho2 = new psc_h1(outputStream, a, a2, socket, psc_hp.f(), psc_hp.g());
            ((psc_h1)psc_ho2).a(psc_c2.v(), psc_c2.z());
            ((psc_h1)psc_ho2).b(768);
        }
        return new psc_hj(psc_hz, psc_ho2);
    }
    
    static psc_hj a(final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final byte[] array, final byte[] array2, final int n, final psc_c2 psc_c2, psc_d2 psc_d2, final psc_da psc_da, final psc_da psc_da2, final psc_d1 psc_d3) throws psc_b, psc_c, Exception {
        final psc_hn psc_hn = new psc_hn(socket, inputStream, psc_da, psc_da2, array);
        psc_hn.a(psc_c2.v(), psc_c2.z());
        psc_hn.a(new psc_g6());
        psc_hn.a(new psc_c4());
        final psc_ho psc_ho = new psc_ho(outputStream, psc_da, psc_da2);
        psc_ho.a(psc_c2.v(), psc_c2.z());
        final psc_hr psc_hr = new psc_hr();
        psc_hr.a(psc_hn);
        if (psc_d2 == null || !psc_c9.a(psc_hr.c(), psc_d2.e())) {
            if (psc_d2 != null) {
                psc_c2.a(psc_d2);
            }
            psc_d2 = new psc_d2(psc_hr.c(), s, System.currentTimeMillis(), null, a(psc_hr.d(), psc_c2), null, null, psc_c2, 768);
        }
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_hr.toString());
        }
        final psc_il psc_il = new psc_il(psc_c2, psc_hr, psc_hn, psc_ho, s, array2, n, psc_d2);
        psc_d3.a(psc_il.f());
        psc_d3.a(psc_il.g());
        psc_d3.a(psc_il.b());
        psc_d3.a(psc_il.a());
        final psc_hz psc_hz = new psc_hz(inputStream, psc_ho, psc_da, psc_da2, socket, psc_il.b(), false, psc_c2, psc_il.f(), psc_il.g());
        psc_hz.a(psc_c2.v(), psc_c2.z());
        psc_ho psc_ho2;
        if (psc_c2.u()) {
            psc_ho2 = new psc_h0(outputStream, psc_da, psc_da2, socket, psc_il.f(), psc_il.g());
            ((psc_h0)psc_ho2).a(psc_c2.v(), psc_c2.z());
            ((psc_h0)psc_ho2).b(768);
        }
        else {
            psc_ho2 = new psc_h1(outputStream, psc_da, psc_da2, socket, psc_il.f(), psc_il.g());
            ((psc_h1)psc_ho2).a(psc_c2.v(), psc_c2.z());
            ((psc_h1)psc_ho2).b(768);
        }
        return new psc_hj(psc_hz, psc_ho2);
    }
    
    public static psc_d1 b(final psc_c2 psc_c2, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        try {
            final psc_d1 psc_d3 = new psc_d1();
            final psc_da a = psc_da.a("MD5", "Java");
            a.i();
            final psc_da a2 = psc_da.a("SHA1", "Java");
            a2.i();
            final psc_hn psc_hn = new psc_hn(((psc_hn)inputStream).m(), ((psc_hn)inputStream).l(), a, a2);
            psc_hn.a(psc_c2.v(), psc_c2.z());
            psc_hn.a(((psc_hn)inputStream).h());
            psc_hn.a(((psc_hn)inputStream).i());
            final psc_ho psc_ho = new psc_ho(((psc_ho)outputStream).e(), psc_hn.j(), psc_hn.k());
            psc_ho.a(psc_c2.v(), psc_c2.z());
            psc_ho.a(((psc_hn)inputStream).h());
            psc_ho.a(((psc_hn)inputStream).i());
            psc_ho.a(22);
            while (psc_hn.available() > 0 && psc_hn.f() == 23) {
                final byte[] array = new byte[psc_hn.available()];
                psc_hn.read(array, 0, array.length);
            }
            final psc_il psc_il = new psc_il(psc_c2, psc_hn, psc_ho, psc_d2, s);
            ((psc_hn)inputStream).a(psc_hn.h());
            ((psc_ho)outputStream).a(psc_ho.c());
            psc_d3.a(psc_il.f());
            psc_d3.a(psc_il.g());
            psc_d3.a(psc_il.b());
            psc_d3.a(psc_il.a());
            return psc_d3;
        }
        catch (psc_d psc_d4) {
            throw psc_d4;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (Exception ex) {
            throw new psc_d("Could not make a new input stream");
        }
    }
    
    public static psc_d1 a(final psc_c2 psc_c2, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        try {
            final psc_d1 psc_d3 = new psc_d1();
            final psc_da a = psc_da.a("MD5", "Java");
            a.i();
            final psc_da a2 = psc_da.a("SHA1", "Java");
            a2.i();
            final psc_hn psc_hn = new psc_hn(((psc_hn)inputStream).m(), ((psc_hn)inputStream).l(), a, a2);
            psc_hn.a(psc_c2.v(), psc_c2.z());
            psc_hn.a(((psc_hn)inputStream).h());
            psc_hn.a(((psc_hn)inputStream).i());
            final psc_ho psc_ho = new psc_ho(((psc_ho)outputStream).e(), psc_hn.j(), psc_hn.k());
            psc_ho.a(psc_c2.v(), psc_c2.z());
            psc_ho.a(((psc_hn)inputStream).h());
            psc_ho.a(((psc_hn)inputStream).i());
            psc_ho.a(22);
            while (psc_hn.available() > 0 && psc_hn.f() == 23) {
                final byte[] array = new byte[psc_hn.available()];
                psc_hn.read(array, 0, array.length);
            }
            new psc_nh().a(psc_ho);
            a.i();
            a2.i();
            final psc_hl psc_hl = new psc_hl();
            psc_hl.a(psc_hn);
            final psc_hp psc_hp = new psc_hp(psc_c2, psc_hl, psc_hn, psc_ho, s);
            ((psc_hn)inputStream).a(psc_hn.h());
            ((psc_ho)outputStream).a(psc_hn.h());
            psc_d3.a(psc_hp.f());
            psc_d3.a(psc_hp.g());
            psc_d3.a(psc_hp.h());
            psc_d3.a(psc_hp.i());
            return psc_d3;
        }
        catch (psc_d psc_d4) {
            throw psc_d4;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (Exception ex) {
            throw new psc_d("Could not make a new input stream");
        }
    }
    
    private static psc_dw a(final byte[] array, final psc_c2 psc_c2) throws psc_d {
        final psc_dw[] c = psc_c2.c();
        for (int i = 0; i < c.length; ++i) {
            if (c[i].b(768) != null && psc_c9.a(array, c[i].b(768))) {
                return c[i];
            }
            if (c[i].b(2) != null && psc_c9.a(array, c[i].b(2))) {
                return c[i];
            }
        }
        return null;
    }
}
