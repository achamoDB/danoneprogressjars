import java.net.SocketException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStream;
import java.net.Socket;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_gy
{
    private static final int a = 0;
    private static final int b = 1;
    
    private psc_gy() {
    }
    
    private static int a(final int n, final Socket socket, final InputStream inputStream, final byte[] b, final int n2, final int n3) throws InterruptedIOException, IOException, SocketException {
        int read = -1;
        int soTimeout = 0;
        final long currentTimeMillis = System.currentTimeMillis();
        int n4 = 0;
        int n5 = 0;
        while (true) {
            try {
                switch (n) {
                    case 0: {
                        read = inputStream.read();
                        break;
                    }
                    case 1: {
                        int read2;
                        for (read2 = 0; n5 < n3 && read2 != -1; n5 += read2) {
                            read2 = inputStream.read(b, n5 + n2, n3 - n5);
                            if (read2 != -1) {}
                        }
                        if (read2 == -1 && n5 == 0) {
                            read = -1;
                            break;
                        }
                        read = n5;
                        break;
                    }
                }
            }
            catch (InterruptedIOException ex) {
                if (n4 == 0) {
                    soTimeout = socket.getSoTimeout();
                    n4 = 1;
                }
                final int soTimeout2 = soTimeout - (int)(System.currentTimeMillis() - currentTimeMillis);
                if (soTimeout2 <= 0) {
                    socket.setSoTimeout(soTimeout);
                    throw ex;
                }
                socket.setSoTimeout(soTimeout2);
                continue;
            }
            break;
        }
        if (n4 != 0) {
            socket.setSoTimeout(soTimeout);
        }
        return read;
    }
    
    public static int a(final Socket socket, final InputStream inputStream) throws InterruptedIOException, IOException, SocketException {
        return a(0, socket, inputStream, null, 0, 0);
    }
    
    public static int a(final Socket socket, final InputStream inputStream, final byte[] array) throws InterruptedIOException, IOException, SocketException {
        return a(1, socket, inputStream, array, 0, array.length);
    }
    
    public static int a(final Socket socket, final InputStream inputStream, final byte[] array, final int n, final int n2) throws InterruptedIOException, IOException, SocketException {
        return a(1, socket, inputStream, array, n, n2);
    }
}
