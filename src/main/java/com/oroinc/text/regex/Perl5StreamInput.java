// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;

public final class Perl5StreamInput
{
    private static final int __DEFAULT_BUFFER_INCREMENT = 4096;
    private int __bufferIncrement;
    private Reader __input;
    boolean _endOfStream;
    boolean _tentativeEOS;
    int _bufferSize;
    int _streamOffset;
    int _bufferOffset;
    int _matchEndOffset;
    char[] _buffer;
    char[] _originalBuffer;
    
    public Perl5StreamInput(final Reader _input, final int _bufferIncrement) {
        this.__input = _input;
        this.__bufferIncrement = _bufferIncrement;
        this._endOfStream = false;
        this._tentativeEOS = false;
        final int bufferSize = 0;
        this._streamOffset = bufferSize;
        this._bufferOffset = bufferSize;
        this._bufferSize = bufferSize;
        final char[] array = new char[_bufferIncrement];
        this._buffer = array;
        this._originalBuffer = array;
        this._matchEndOffset = -1;
    }
    
    public Perl5StreamInput(final Reader reader) {
        this(reader, 4096);
    }
    
    public Perl5StreamInput(final InputStream in, final int n) {
        this(new InputStreamReader(in), n);
    }
    
    public Perl5StreamInput(final InputStream in) {
        this(new InputStreamReader(in), 4096);
    }
    
    private void __fillBuffer(int i, int n) {
        char c;
        for (n += i; i < n; ++i) {
            c = this._originalBuffer[i];
            this._buffer[i] = (Character.isUpperCase(c) ? Character.toLowerCase(c) : c);
        }
    }
    
    void _setMatchEndOffset(final int matchEndOffset) {
        this._matchEndOffset = matchEndOffset;
    }
    
    int _getMatchEndOffset() {
        return this._matchEndOffset;
    }
    
    void _reallocate(final int n, final boolean b) throws IOException {
        if (this._endOfStream) {
            return;
        }
        if (this._tentativeEOS) {
            this._tentativeEOS = false;
            this._endOfStream = true;
            return;
        }
        final int n2 = this._bufferSize - n;
        if (n2 < 0) {
            throw new IOException("Non-negative offset assertion violation.\nPlease report this error to bugs@oroinc.com\n");
        }
        final char[] originalBuffer = new char[n2 + this.__bufferIncrement];
        int read = this.__input.read(originalBuffer, n2, this.__bufferIncrement);
        if (read <= 0) {
            this._endOfStream = true;
            if (read == 0) {
                throw new IOException("read from input Reader returned 0 chars.");
            }
        }
        else {
            this._streamOffset += n;
            this._bufferSize = n2 + read;
            while (this._bufferSize < originalBuffer.length) {
                final int read2 = this.__input.read(originalBuffer, this._bufferSize, originalBuffer.length - this._bufferSize);
                if (read2 == 0) {
                    throw new IOException("read from input Reader returned 0 chars.");
                }
                if (read2 < 0) {
                    this._tentativeEOS = true;
                    break;
                }
                this._bufferSize += read2;
                read += read2;
            }
            System.arraycopy(this._originalBuffer, n, originalBuffer, 0, n2);
            this._originalBuffer = originalBuffer;
            if (b) {
                final char[] buffer = new char[n2 + this.__bufferIncrement];
                System.arraycopy(this._buffer, n, buffer, 0, n2);
                this._buffer = buffer;
                this.__fillBuffer(n2, read);
                return;
            }
            this._buffer = this._originalBuffer;
        }
    }
    
    boolean _read(final boolean b) throws IOException {
        this._streamOffset += this._bufferSize;
        this._bufferSize = this.__input.read(this._originalBuffer);
        if (this._bufferSize > 0) {
            while (this._bufferSize < this._originalBuffer.length) {
                final int read = this.__input.read(this._originalBuffer, this._bufferSize, this._originalBuffer.length - this._bufferSize);
                if (read == 0) {
                    throw new IOException("read from input Reader returned 0 chars.");
                }
                if (read < 0) {
                    break;
                }
                this._bufferSize += read;
            }
        }
        else {
            this._endOfStream = true;
        }
        if (!this._endOfStream && b) {
            this._buffer = new char[this._originalBuffer.length];
            this.__fillBuffer(0, this._bufferSize);
        }
        return !this._endOfStream;
    }
    
    public boolean endOfStream() {
        return this._endOfStream;
    }
}
