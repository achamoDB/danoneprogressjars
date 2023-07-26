import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class pscl_au
{
    private static final int a = 20;
    
    public static int a(final InputStream inputStream) throws InterruptedIOException, IOException {
        int read = -1;
        int i = 0;
        while (i < 20) {
            try {
                read = inputStream.read();
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return read;
    }
    
    public static int a(final InputStream inputStream, final byte[] b) throws InterruptedIOException, IOException {
        int read = -1;
        int i = 0;
        while (i < 20) {
            try {
                read = inputStream.read(b);
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return read;
    }
    
    public static int a(final InputStream inputStream, final byte[] b, final int off, final int len) throws InterruptedIOException, IOException {
        int read = -1;
        int i = 0;
        while (i < 20) {
            try {
                read = inputStream.read(b, off, len);
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return read;
    }
    
    public static int b(final InputStream inputStream) throws InterruptedIOException, IOException {
        int c = -1;
        int i = 0;
        while (i < 20) {
            try {
                if (inputStream instanceof pscl_ar) {
                    c = ((pscl_ar)inputStream).c();
                }
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return c;
    }
    
    public static int c(final InputStream inputStream) throws InterruptedIOException, IOException {
        int d = -1;
        int i = 0;
        while (i < 20) {
            try {
                if (inputStream instanceof pscl_ar) {
                    d = ((pscl_ar)inputStream).d();
                }
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return d;
    }
    
    public static int d(final InputStream inputStream) throws InterruptedIOException, IOException {
        int b = -1;
        int i = 0;
        while (i < 20) {
            try {
                if (inputStream instanceof pscl_ar) {
                    b = ((pscl_ar)inputStream).b();
                }
            }
            catch (InterruptedIOException ex) {
                if (i == 20) {
                    throw ex;
                }
                Thread.currentThread();
                Thread.yield();
                ++i;
                continue;
            }
            break;
        }
        return b;
    }
}
