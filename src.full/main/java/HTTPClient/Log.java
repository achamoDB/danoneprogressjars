// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.FileWriter;
import java.util.TimeZone;
import java.util.Calendar;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.Writer;

public class Log
{
    public static final int CONN = 1;
    public static final int RESP = 2;
    public static final int DEMUX = 4;
    public static final int AUTH = 8;
    public static final int COOKI = 16;
    public static final int MODS = 32;
    public static final int SOCKS = 64;
    public static final int URLC = 128;
    public static final int ALL = -1;
    private static final String NL;
    private static final long TZ_OFF;
    private static int facMask;
    private static Writer logWriter;
    private static boolean closeWriter;
    static /* synthetic */ Class class$HTTPClient$Log;
    
    private Log() {
    }
    
    public static void write(final int n, final String s) {
        if ((Log.facMask & n) == 0x0) {
            return;
        }
        try {
            writePrefix();
            Log.logWriter.write(s);
            Log.logWriter.write(Log.NL);
            Log.logWriter.flush();
        }
        catch (IOException obj) {
            System.err.println("Failed to write to log: " + obj);
            System.err.println("Failed log Entry was: " + s);
        }
    }
    
    public static void write(final int n, final String s, final Throwable t) {
        if ((Log.facMask & n) == 0x0) {
            return;
        }
        Class class$;
        Class class$HTTPClient$Log;
        if (Log.class$HTTPClient$Log == null) {
            class$HTTPClient$Log = (Log.class$HTTPClient$Log = (class$ = class$("HTTPClient.Log")));
        }
        else {
            class$ = (class$HTTPClient$Log = Log.class$HTTPClient$Log);
        }
        final Class clazz = class$HTTPClient$Log;
        synchronized (class$) {
            if (!(Log.logWriter instanceof PrintWriter)) {
                Log.logWriter = new PrintWriter(Log.logWriter);
            }
        }
        try {
            writePrefix();
            if (s != null) {
                Log.logWriter.write(s);
            }
            t.printStackTrace((PrintWriter)Log.logWriter);
            Log.logWriter.flush();
        }
        catch (IOException obj) {
            System.err.println("Failed to write to log: " + obj);
            System.err.print("Failed log Entry was: " + s);
            t.printStackTrace(System.err);
        }
    }
    
    public static void write(final int n, final String s, final ByteArrayOutputStream byteArrayOutputStream) {
        if ((Log.facMask & n) == 0x0) {
            return;
        }
        try {
            writePrefix();
            if (s != null) {
                Log.logWriter.write(s);
            }
            Log.logWriter.write(Log.NL);
            Log.logWriter.write(new String(byteArrayOutputStream.toByteArray(), "ISO_8859-1"));
            Log.logWriter.flush();
        }
        catch (IOException obj) {
            System.err.println("Failed to write to log: " + obj);
            System.err.println("Failed log Entry was: " + s);
            System.err.println(new String(byteArrayOutputStream.toByteArray()));
        }
    }
    
    private static final void writePrefix() throws IOException {
        Log.logWriter.write("{" + Thread.currentThread().getName() + "} ");
        final int n = (int)((System.currentTimeMillis() + Log.TZ_OFF) % 86400000L);
        final int n2 = n / 1000;
        final int n3 = n2 / 60;
        final int n4 = n3 / 60;
        Log.logWriter.write("[" + fill2(n4) + ':' + fill2(n3 - n4 * 60) + ':' + fill2(n2 - n3 * 60) + '.' + fill3(n - n2 * 1000) + "] ");
    }
    
    private static final String fill2(final int i) {
        return ((i < 10) ? "0" : "") + i;
    }
    
    private static final String fill3(final int i) {
        return ((i < 10) ? "00" : ((i < 100) ? "0" : "")) + i;
    }
    
    public static boolean isEnabled(final int n) {
        return (Log.facMask & n) != 0x0;
    }
    
    public static void setLogging(final int n, final boolean b) {
        if (b) {
            Log.facMask |= n;
        }
        else {
            Log.facMask &= ~n;
        }
    }
    
    public static void setLogWriter(final Writer logWriter, final boolean closeWriter) {
        if (logWriter == null) {
            return;
        }
        if (Log.closeWriter) {
            try {
                Log.logWriter.close();
            }
            catch (IOException obj) {
                System.err.println("Error closing log stream: " + obj);
            }
        }
        Log.logWriter = logWriter;
        Log.closeWriter = closeWriter;
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        NL = System.getProperty("line.separator");
        Log.facMask = 0;
        Log.logWriter = new OutputStreamWriter(System.err);
        Log.closeWriter = false;
        final Calendar instance = Calendar.getInstance();
        TZ_OFF = TimeZone.getDefault().getOffset(instance.get(0), instance.get(1), instance.get(2), instance.get(5), instance.get(7), instance.get(14));
        try {
            final String property = System.getProperty("HTTPClient.log.file");
            if (property != null) {
                try {
                    setLogWriter(new FileWriter(property), true);
                }
                catch (IOException obj) {
                    System.err.println("failed to open file log stream `" + property + "': " + obj);
                }
            }
        }
        catch (Exception ex) {}
        try {
            Log.facMask = Integer.getInteger("HTTPClient.log.mask", 0);
        }
        catch (Exception ex2) {}
    }
}
