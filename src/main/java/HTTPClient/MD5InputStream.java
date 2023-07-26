// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.io.FilterInputStream;

class MD5InputStream extends FilterInputStream
{
    private HashVerifier verifier;
    private MessageDigest md5;
    private long rcvd;
    private boolean closed;
    
    public MD5InputStream(final InputStream in, final HashVerifier verifier) {
        super(in);
        this.rcvd = 0L;
        this.closed = false;
        this.verifier = verifier;
        try {
            this.md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex) {
            throw new Error(ex.toString());
        }
    }
    
    public synchronized int read() throws IOException {
        final int read = super.in.read();
        if (read != -1) {
            this.md5.update((byte)read);
        }
        else {
            this.real_close();
        }
        ++this.rcvd;
        return read;
    }
    
    public synchronized int read(final byte[] array, final int n, final int len) throws IOException {
        final int read = super.in.read(array, n, len);
        if (read > 0) {
            this.md5.update(array, n, read);
        }
        else {
            this.real_close();
        }
        this.rcvd += read;
        return read;
    }
    
    public synchronized long skip(final long n) throws IOException {
        final int read = this.read(new byte[(int)n], 0, (int)n);
        if (read > 0) {
            return read;
        }
        return 0L;
    }
    
    public synchronized void close() throws IOException {
        while (this.skip(10000L) > 0L) {}
        this.real_close();
    }
    
    private void real_close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        super.in.close();
        this.verifier.verifyHash(this.md5.digest(), this.rcvd);
    }
}
