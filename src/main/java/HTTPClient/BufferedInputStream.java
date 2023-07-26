// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

class BufferedInputStream extends FilterInputStream
{
    private byte[] buffer;
    private int pos;
    private int end;
    private int mark_pos;
    private int lr_thrshld;
    
    BufferedInputStream(final InputStream in) {
        super(in);
        this.buffer = new byte[2000];
        this.pos = 0;
        this.end = 0;
        this.mark_pos = -1;
        this.lr_thrshld = 1500;
    }
    
    public int read() throws IOException {
        if (this.pos >= this.end) {
            this.fillBuff();
        }
        return (this.end > this.pos) ? (this.buffer[this.pos++] & 0xFF) : -1;
    }
    
    public int read(final byte[] b, final int off, int len) throws IOException {
        if (len <= 0) {
            return 0;
        }
        if (this.pos >= this.end && len >= this.lr_thrshld && this.mark_pos < 0) {
            return super.in.read(b, off, len);
        }
        if (this.pos >= this.end) {
            this.fillBuff();
        }
        if (this.pos >= this.end) {
            return -1;
        }
        final int n = this.end - this.pos;
        if (len > n) {
            len = n;
        }
        System.arraycopy(this.buffer, this.pos, b, off, len);
        this.pos += len;
        return len;
    }
    
    public long skip(final long n) throws IOException {
        if (n <= 0L) {
            return 0L;
        }
        final int n2 = this.end - this.pos;
        if (n <= n2) {
            this.pos += (int)n;
            return n;
        }
        this.pos = this.end;
        return n2 + super.in.skip(n - n2);
    }
    
    private final void fillBuff() throws IOException {
        if (this.mark_pos > 0) {
            if (this.end >= this.buffer.length) {
                System.arraycopy(this.buffer, this.mark_pos, this.buffer, 0, this.end - this.mark_pos);
                this.pos = this.end - this.mark_pos;
            }
        }
        else if (this.mark_pos != 0 || this.end >= this.buffer.length) {
            this.pos = 0;
        }
        this.end = this.pos;
        final int read = super.in.read(this.buffer, this.pos, this.buffer.length - this.pos);
        if (read > 0) {
            this.end = this.pos + read;
        }
    }
    
    public int available() throws IOException {
        int n = this.end - this.pos;
        if (n == 0) {
            return super.in.available();
        }
        try {
            n += super.in.available();
        }
        catch (IOException ex) {}
        return n;
    }
    
    void markForSearch() {
        this.mark_pos = this.pos;
    }
    
    int pastEnd(final byte[] array, final int[] array2) {
        int str = Util.findStr(array, array2, this.buffer, this.mark_pos, this.pos);
        if (str == -1) {
            this.mark_pos = ((this.pos > array.length) ? (this.pos - array.length) : 0);
        }
        else {
            final int pos = str + array.length;
            str = this.pos - pos;
            this.pos = pos;
            this.mark_pos = -1;
        }
        return str;
    }
}
