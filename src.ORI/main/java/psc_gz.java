import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_gz
{
    static psc_h5 a(final int n, final OutputStream outputStream, final psc_c2 psc_c2, final String s, final psc_da psc_da, final psc_da psc_da2, final psc_d2 psc_d2) throws IOException, psc_d {
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
        final psc_g0 psc_g0 = new psc_g0(n, array, e, a, array2);
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("STATE: Sending Client Hello");
            psc_c2.z().println(psc_g0.toString());
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        psc_g0.a(byteArrayOutputStream);
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
        return new psc_h5(byteArray, psc_g0.b(), psc_g0.c(), psc_g0.d(), psc_g0.e(), 769);
    }
    
    static psc_hj a(final psc_h5 psc_h5, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws Exception {
        final byte[] array = { 0 };
        final byte[] array2 = new byte[32];
        System.arraycopy(psc_h5.b(), 0, array2, 32 - psc_h5.b().length, psc_h5.b().length);
        return a(psc_h5.a(), inputStream, outputStream, s, new psc_g0(psc_h5.f(), array2, psc_h5.c(), psc_h5.d(), array), socket, psc_c2, psc_d1);
    }
    
    static psc_hj a(final byte[] array, final InputStream inputStream, final OutputStream outputStream, final String s, final psc_g0 psc_g0, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws Exception {
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("Using TLSV1 protocol");
        }
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_g0.toString());
        }
        final psc_da a = psc_da.a("MD5", "Java");
        a.i();
        a.a(array, 0, array.length);
        final psc_da a2 = psc_da.a("SHA1", "Java");
        a2.i();
        a2.a(array, 0, array.length);
        final psc_g2 psc_g2 = new psc_g2(socket, inputStream, a, a2);
        psc_g2.a(psc_c2.v(), psc_c2.z());
        final psc_g3 psc_g3 = new psc_g3(outputStream, a, a2);
        psc_g3.a(psc_c2.v(), psc_c2.z());
        final psc_g4 psc_g4 = new psc_g4(psc_c2, psc_g0, psc_g2, psc_g3, s);
        psc_d1.a(psc_g4.f());
        psc_d1.a(psc_g4.g());
        psc_d1.a(psc_g4.h());
        psc_d1.a(psc_g4.i());
        psc_d1.a(769);
        final psc_hg psc_hg = new psc_hg(inputStream, psc_g3, a, a2, socket, psc_g4.h(), true, psc_c2, psc_g4.f(), psc_g4.g());
        psc_hg.a(psc_c2.v(), psc_c2.z());
        psc_g3 psc_g5;
        if (psc_c2.u()) {
            psc_g5 = new psc_hh(outputStream, a, a2, socket, psc_g4.f(), psc_g4.g());
            ((psc_hh)psc_g5).a(psc_c2.v(), psc_c2.z());
            ((psc_hh)psc_g5).b(769);
        }
        else {
            psc_g5 = new psc_hi(outputStream, a, a2, socket, psc_g4.f(), psc_g4.g());
            ((psc_hi)psc_g5).a(psc_c2.v(), psc_c2.z());
            ((psc_hi)psc_g5).b(769);
        }
        return new psc_hj(psc_hg, psc_g5);
    }
    
    static psc_hj a(final byte[] buf, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws Exception {
        final psc_g0 psc_g0 = new psc_g0();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        byteArrayInputStream.read();
        psc_g0.a(byteArrayInputStream);
        return a(buf, inputStream, outputStream, s, psc_g0, socket, psc_c2, psc_d1);
    }
    
    static psc_hj a(final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final byte[] array, final byte[] array2, final int n, final psc_c2 psc_c2, psc_d2 psc_d2, final psc_da psc_da, final psc_da psc_da2, final psc_d1 psc_d3) throws Exception {
        final psc_g2 psc_g2 = new psc_g2(socket, inputStream, psc_da, psc_da2, array);
        psc_g2.a(psc_c2.v(), psc_c2.z());
        psc_g2.a(new psc_g6());
        psc_g2.a(new psc_c4());
        final psc_g3 psc_g3 = new psc_g3(outputStream, psc_da, psc_da2);
        psc_g3.a(psc_c2.v(), psc_c2.z());
        final psc_g7 psc_g4 = new psc_g7();
        psc_g4.a(psc_g2);
        if (psc_d2 == null || !psc_c9.a(psc_g4.c(), psc_d2.e())) {
            if (psc_d2 != null) {
                psc_c2.a(psc_d2);
            }
            psc_d2 = new psc_d2(psc_g4.c(), s, System.currentTimeMillis(), null, a(psc_g4.d(), psc_c2), null, null, psc_c2, 769);
        }
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_g4.toString());
        }
        final psc_im psc_im = new psc_im(psc_c2, psc_g4, psc_g2, psc_g3, s, array2, n, psc_d2);
        psc_d3.a(psc_im.f());
        psc_d3.a(psc_im.g());
        psc_d3.a(psc_im.a());
        psc_d3.a(psc_im.b());
        final psc_hg psc_hg = new psc_hg(inputStream, psc_g3, psc_da, psc_da2, socket, psc_im.a(), false, psc_c2, psc_im.f(), psc_im.g());
        psc_hg.a(psc_c2.v(), psc_c2.z());
        psc_g3 psc_g5;
        if (psc_c2.u()) {
            psc_g5 = new psc_hh(outputStream, psc_da, psc_da2, socket, psc_im.f(), psc_im.g());
            ((psc_hh)psc_g5).a(psc_c2.v(), psc_c2.z());
            ((psc_hh)psc_g5).b(769);
        }
        else {
            psc_g5 = new psc_hi(outputStream, psc_da, psc_da2, socket, psc_im.f(), psc_im.g());
            ((psc_hi)psc_g5).a(psc_c2.v(), psc_c2.z());
            ((psc_hi)psc_g5).b(769);
        }
        return new psc_hj(psc_hg, psc_g5);
    }
    
    public static psc_d1 b(final psc_c2 psc_c2, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        try {
            final psc_d1 psc_d3 = new psc_d1();
            final psc_da a = psc_da.a("MD5", "Java");
            a.i();
            final psc_da a2 = psc_da.a("SHA1", "Java");
            a2.i();
            final psc_g2 psc_g2 = new psc_g2(((psc_g2)inputStream).m(), ((psc_g2)inputStream).l(), a, a2);
            psc_g2.a(psc_c2.v(), psc_c2.z());
            psc_g2.a(((psc_g2)inputStream).h());
            psc_g2.a(((psc_g2)inputStream).i());
            final psc_g3 psc_g3 = new psc_g3(((psc_g3)outputStream).e(), psc_g2.j(), psc_g2.k());
            psc_g3.a(psc_c2.v(), psc_c2.z());
            psc_g3.a(((psc_g2)inputStream).h());
            psc_g3.a(((psc_g2)inputStream).i());
            psc_g3.b(769);
            psc_g3.a(22);
            while (psc_g2.available() > 0 && psc_g2.f() == 23) {
                final byte[] array = new byte[psc_g2.available()];
                psc_g2.read(array, 0, array.length);
            }
            final psc_im psc_im = new psc_im(psc_c2, psc_g2, psc_g3, psc_d2, s);
            ((psc_g2)inputStream).a(psc_g2.h());
            ((psc_g3)outputStream).a(psc_g2.h());
            psc_d3.a(psc_im.f());
            psc_d3.a(psc_im.g());
            psc_d3.a(psc_im.a());
            psc_d3.a(psc_im.b());
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
            final psc_g2 psc_g2 = new psc_g2(((psc_g2)inputStream).m(), ((psc_g2)inputStream).l(), a, a2);
            psc_g2.a(psc_c2.v(), psc_c2.z());
            psc_g2.a(((psc_g2)inputStream).h());
            psc_g2.a(((psc_g2)inputStream).i());
            final psc_g3 psc_g3 = new psc_g3(((psc_g3)outputStream).e(), psc_g2.j(), psc_g2.k());
            psc_g3.a(psc_c2.v(), psc_c2.z());
            psc_g3.a(((psc_g2)inputStream).h());
            psc_g3.a(((psc_g2)inputStream).i());
            psc_g3.a(22);
            while (psc_g2.available() > 0 && psc_g2.f() == 23) {
                final byte[] array = new byte[psc_g2.available()];
                psc_g2.read(array, 0, array.length);
            }
            new psc_ni().a(psc_g3);
            a.i();
            a2.i();
            final psc_g0 psc_g4 = new psc_g0();
            psc_g4.a(psc_g2);
            final psc_g4 psc_g5 = new psc_g4(psc_c2, psc_g4, psc_g2, psc_g3, s);
            ((psc_g2)inputStream).a(psc_g2.h());
            ((psc_g3)outputStream).a(psc_g2.h());
            psc_d3.a(psc_g5.f());
            psc_d3.a(psc_g5.g());
            psc_d3.a(psc_g5.h());
            psc_d3.a(psc_g5.i());
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
            if (c[i].b(769) != null && psc_c9.a(array, c[i].b(769))) {
                return c[i];
            }
            if (c[i].b(2) != null && psc_c9.a(array, c[i].b(2))) {
                return c[i];
            }
        }
        return null;
    }
}
