import java.net.Socket;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_h2
{
    public static psc_h5 a(final byte[] buf, final psc_c2 psc_c2) throws IOException {
        final psc_h3 x = new psc_h3();
        x.a(new ByteArrayInputStream(buf));
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(x);
        }
        return new psc_h5(buf, x.b(), x.c(), x.d(), new byte[] { 0 }, x.a());
    }
    
    static psc_h5 a(final int n, final OutputStream outputStream, final psc_c2 psc_c2, final String s, final psc_da psc_da, final psc_da psc_da2, final psc_d2 psc_d2) throws IOException, psc_d {
        final byte[] array = new byte[16];
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
        final psc_h3 psc_h3 = new psc_h3(n, array, e, psc_ik.a(c, n, true));
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("STATE: Sending Client Hello");
            psc_c2.z().println(psc_h3.toString());
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        psc_h3.a(byteArrayOutputStream);
        byteArrayOutputStream.close();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            psc_da.a(byteArray, 0, byteArray.length);
            psc_da2.a(byteArray, 0, byteArray.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("Digesting data failed: " + psc_ap.getMessage());
        }
        final ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        byteArrayOutputStream2.write((byte)(byteArray.length >> 8 | 0x80));
        byteArrayOutputStream2.write((byte)(byteArray.length & 0xFF));
        byteArrayOutputStream2.write(byteArray, 0, byteArray.length);
        byteArrayOutputStream2.flush();
        outputStream.write(byteArrayOutputStream2.toByteArray());
        byteArrayOutputStream2.close();
        outputStream.flush();
        return new psc_h5(byteArray, array, e, psc_h3.d(), new byte[] { 0 }, 2);
    }
    
    static psc_hj a(final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_h5 psc_h5, final int n, final psc_c2 psc_c2, final byte[] array, final psc_d1 psc_d1) throws psc_b, psc_c, psc_d, Exception {
        final psc_h6 psc_h6 = new psc_h6(socket, inputStream, array, 0L);
        psc_h6.a(psc_c2.v(), psc_c2.z());
        psc_h6.a(new psc_g6());
        final psc_h7 psc_h7 = new psc_h7(outputStream, 1L);
        psc_h7.a(psc_c2.v(), psc_c2.z());
        final psc_ia psc_ia = new psc_ia();
        psc_ia.a(psc_h6);
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_ia.toString());
        }
        final psc_in psc_in = new psc_in(psc_c2, psc_ia, psc_h6, psc_h7, s, psc_h5);
        psc_d1.a(psc_in.d());
        psc_d1.a(psc_in.a());
        psc_d1.a(psc_in.b());
        psc_d1.a(psc_in.c());
        final psc_ih psc_ih = new psc_ih(inputStream, socket, true, psc_in.d(), psc_h6.i());
        psc_ih.a(psc_c2.v(), psc_c2.z());
        psc_h7 psc_h8;
        if (psc_c2.u()) {
            psc_h8 = new psc_ii(outputStream, socket, psc_in.d(), psc_h7.c());
            ((psc_ii)psc_h8).a(psc_c2.v(), psc_c2.z());
        }
        else {
            psc_h8 = new psc_ij(outputStream, socket, psc_in.d(), psc_h7.c());
            ((psc_ij)psc_h8).a(psc_c2.v(), psc_c2.z());
        }
        return new psc_hj(psc_ih, psc_h8);
    }
    
    static psc_hj a(final psc_h5 psc_h5, final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket, final psc_c2 psc_c2, final psc_d1 psc_d1) throws psc_b, psc_c, psc_d, Exception {
        final psc_h6 psc_h6 = new psc_h6(socket, inputStream, 1L);
        psc_h6.a(psc_c2.v(), psc_c2.z());
        final psc_h7 psc_h7 = new psc_h7(outputStream, 0L);
        psc_h7.a(psc_c2.v(), psc_c2.z());
        final psc_h8 psc_h8 = new psc_h8(psc_c2, psc_h5, psc_h6, psc_h7, s);
        psc_d1.a(psc_h8.d());
        psc_d1.a(psc_h8.b());
        psc_d1.a(psc_h8.c());
        psc_d1.a(psc_h8.e());
        final psc_ih psc_ih = new psc_ih(inputStream, socket, true, psc_h8.d(), psc_h6.i());
        psc_ih.a(psc_c2.v(), psc_c2.z());
        psc_h7 psc_h9;
        if (psc_c2.u()) {
            psc_h9 = new psc_ii(outputStream, socket, psc_h8.d(), psc_h7.c());
            ((psc_ii)psc_h9).a(psc_c2.v(), psc_c2.z());
        }
        else {
            psc_h9 = new psc_ij(outputStream, socket, psc_h8.d(), psc_h7.c());
            ((psc_ij)psc_h9).a(psc_c2.v(), psc_c2.z());
        }
        return new psc_hj(psc_ih, psc_h9);
    }
}
