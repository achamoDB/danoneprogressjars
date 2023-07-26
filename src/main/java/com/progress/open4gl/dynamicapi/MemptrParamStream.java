// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.InputStream;
import java.io.IOException;
import com.progress.open4gl.Memptr;
import java.io.OutputStream;

public class MemptrParamStream
{
    public static int sendMemptrs(final ParameterSet set, final OutputStream outputStream) throws ClientException {
        int n = 0;
        try {
            for (int i = 1; i <= set.getNumParams(); ++i) {
                final int mode = set.getMode(i);
                if (mode == 1 || mode == 3) {
                    if (set.getProType(i) == 11) {
                        if (!set.getIsExtent(i)) {
                            ++n;
                            final Memptr memptr = (Memptr)set.getValue(i);
                            if (memptr != null) {
                                final byte[] bytes = memptr.getBytes();
                                if (bytes != null) {
                                    for (int j = 0; j < bytes.length; ++j) {
                                        outputStream.write(bytes[j]);
                                    }
                                    if (mode == 3) {
                                        memptr.putBytes(null);
                                        set.setValue(i, null);
                                    }
                                }
                            }
                        }
                        else {
                            final Memptr[] array = (Memptr[])set.getValue(i);
                            if (array != null) {
                                for (int k = 0; k < array.length; ++k) {
                                    ++n;
                                    final Memptr memptr2 = array[k];
                                    if (memptr2 != null) {
                                        final byte[] bytes2 = memptr2.getBytes();
                                        if (bytes2 != null) {
                                            for (int l = 0; l < bytes2.length; ++l) {
                                                outputStream.write(bytes2[l]);
                                            }
                                            if (mode == 3) {
                                                memptr2.putBytes(null);
                                                set.setValue(i, null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (set.getProType(i) == 39 || ((set.getProType(i) == 36 || set.getProType(i) == 37) && !set.isDataGraph(i))) {
                        if (!set.getIsExtent(i)) {
                            ++n;
                            final String s = (String)set.getValue(i);
                            if (s != null) {
                                final byte[] bytes3 = s.getBytes("UTF8");
                                if (bytes3 != null) {
                                    for (int n2 = 0; n2 < bytes3.length; ++n2) {
                                        outputStream.write(bytes3[n2]);
                                    }
                                    if (mode == 3) {
                                        set.setValue(i, null);
                                    }
                                }
                            }
                        }
                        else {
                            final String[] array2 = (String[])set.getValue(i);
                            if (array2 != null) {
                                for (int n3 = 0; n3 < array2.length; ++n3) {
                                    ++n;
                                    final String s2 = array2[n3];
                                    if (s2 != null) {
                                        final byte[] bytes4 = s2.getBytes("UTF8");
                                        if (bytes4 != null) {
                                            for (int n4 = 0; n4 < bytes4.length; ++n4) {
                                                outputStream.write(bytes4[n4]);
                                            }
                                            if (mode == 3) {
                                                set.setValue(i, null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        return n;
    }
    
    public static int recvMemptrs(final ParameterSet set, final InputStream inputStream) throws ClientException {
        int n = 0;
        try {
            for (int i = 1; i <= set.getNumParams(); ++i) {
                final int mode = set.getMode(i);
                if (mode == 2 || mode == 3) {
                    if (set.getProType(i) == 11) {
                        if (!set.getIsExtent(i)) {
                            ++n;
                            final Memptr memptr = (Memptr)set.getValue(i);
                            if (memptr != null) {
                                final byte[] bytes = memptr.getBytes();
                                if (bytes != null) {
                                    for (int j = 0; j < bytes.length; ++j) {
                                        bytes[j] = (byte)inputStream.read();
                                    }
                                }
                            }
                        }
                        else {
                            final Memptr[] array = (Memptr[])set.getValue(i);
                            for (int k = 0; k < array.length; ++k) {
                                ++n;
                                final Memptr memptr2 = array[k];
                                if (memptr2 != null) {
                                    final byte[] bytes2 = memptr2.getBytes();
                                    if (bytes2 != null) {
                                        for (int l = 0; l < bytes2.length; ++l) {
                                            bytes2[l] = (byte)inputStream.read();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (set.getProType(i) == 39 || (!set.isDataGraph(i) && (set.getProType(i) == 36 || set.getProType(i) == 37))) {
                        if (!set.getIsExtent(i)) {
                            ++n;
                            final String s = (String)set.getValue(i);
                            if (s != null) {
                                final byte[] bytes3 = s.getBytes();
                                if (bytes3 != null) {
                                    for (int n2 = 0; n2 < bytes3.length; ++n2) {
                                        bytes3[n2] = (byte)inputStream.read();
                                    }
                                    set.setValue(i, new String(bytes3, "UTF8"));
                                }
                            }
                        }
                        else {
                            final String[] array2 = (String[])set.getValue(i);
                            for (int n3 = 0; n3 < array2.length; ++n3) {
                                ++n;
                                final String s2 = array2[n3];
                                if (s2 != null) {
                                    final byte[] bytes4 = s2.getBytes();
                                    if (bytes4 != null) {
                                        for (int n4 = 0; n4 < bytes4.length; ++n4) {
                                            bytes4[n4] = (byte)inputStream.read();
                                        }
                                        array2[n3] = new String(bytes4, "UTF8");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        if (n > 0) {
            set.setReadHdr(true);
        }
        return n;
    }
}
