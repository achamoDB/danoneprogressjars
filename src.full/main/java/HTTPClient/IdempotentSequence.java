// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Enumeration;
import java.util.Hashtable;

class IdempotentSequence
{
    private static final int UNKNOWN = 0;
    private static final int HEAD = 1;
    private static final int GET = 2;
    private static final int POST = 3;
    private static final int PUT = 4;
    private static final int DELETE = 5;
    private static final int OPTIONS = 6;
    private static final int TRACE = 7;
    private static final int PROPFIND = 8;
    private static final int PROPPATCH = 9;
    private static final int MKCOL = 10;
    private static final int COPY = 11;
    private static final int MOVE = 12;
    private static final int LOCK = 13;
    private static final int UNLOCK = 14;
    private int[] m_history;
    private String[] r_history;
    private int m_len;
    private int r_len;
    private boolean analysis_done;
    private Hashtable threads;
    private static final Object INDET;
    
    public IdempotentSequence() {
        this.analysis_done = false;
        this.threads = new Hashtable();
        this.m_history = new int[10];
        this.r_history = new String[10];
        this.m_len = 0;
        this.r_len = 0;
    }
    
    public void add(final Request request) {
        if (this.m_len >= this.m_history.length) {
            this.m_history = Util.resizeArray(this.m_history, this.m_history.length + 10);
        }
        this.m_history[this.m_len++] = methodNum(request.getMethod());
        if (this.r_len >= this.r_history.length) {
            this.r_history = Util.resizeArray(this.r_history, this.r_history.length + 10);
        }
        this.r_history[this.r_len++] = request.getRequestURI();
    }
    
    public boolean isIdempotent(final Request request) {
        if (!this.analysis_done) {
            this.do_analysis();
        }
        return this.threads.get(request.getRequestURI());
    }
    
    private void do_analysis() {
        for (int i = 0; i < this.r_len; ++i) {
            final Object value = this.threads.get(this.r_history[i]);
            if (this.m_history[i] == 0) {
                this.threads.put(this.r_history[i], Boolean.FALSE);
            }
            else if (value == null) {
                if (methodHasSideEffects(this.m_history[i])) {
                    if (methodIsComplete(this.m_history[i])) {
                        this.threads.put(this.r_history[i], Boolean.TRUE);
                    }
                    else {
                        this.threads.put(this.r_history[i], Boolean.FALSE);
                    }
                }
                else {
                    this.threads.put(this.r_history[i], IdempotentSequence.INDET);
                }
            }
            else if (value == IdempotentSequence.INDET && methodHasSideEffects(this.m_history[i])) {
                this.threads.put(this.r_history[i], Boolean.FALSE);
            }
        }
        final Enumeration<String> keys = this.threads.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            if (this.threads.get(s) == IdempotentSequence.INDET) {
                this.threads.put(s, Boolean.TRUE);
            }
        }
    }
    
    public static boolean methodIsIdempotent(final String s) {
        return methodIsIdempotent(methodNum(s));
    }
    
    private static boolean methodIsIdempotent(final int n) {
        switch (n) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean methodIsComplete(final String s) {
        return methodIsComplete(methodNum(s));
    }
    
    private static boolean methodIsComplete(final int n) {
        switch (n) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 14: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean methodHasSideEffects(final String s) {
        return methodHasSideEffects(methodNum(s));
    }
    
    private static boolean methodHasSideEffects(final int n) {
        switch (n) {
            case 1:
            case 2:
            case 6:
            case 7:
            case 8:
            case 13:
            case 14: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    private static int methodNum(final String s) {
        if (s.equals("GET")) {
            return 2;
        }
        if (s.equals("POST")) {
            return 3;
        }
        if (s.equals("HEAD")) {
            return 1;
        }
        if (s.equals("PUT")) {
            return 4;
        }
        if (s.equals("DELETE")) {
            return 5;
        }
        if (s.equals("OPTIONS")) {
            return 6;
        }
        if (s.equals("TRACE")) {
            return 7;
        }
        if (s.equals("PROPFIND")) {
            return 8;
        }
        if (s.equals("PROPPATCH")) {
            return 9;
        }
        if (s.equals("MKCOL")) {
            return 10;
        }
        if (s.equals("COPY")) {
            return 11;
        }
        if (s.equals("MOVE")) {
            return 12;
        }
        if (s.equals("LOCK")) {
            return 13;
        }
        if (s.equals("UNLOCK")) {
            return 14;
        }
        return 0;
    }
    
    public static void main(final String[] array) {
        final IdempotentSequence idempotentSequence = new IdempotentSequence();
        idempotentSequence.add(new Request(null, "GET", "/b1", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b2", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b1", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b3", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b2", null, null, null, false));
        idempotentSequence.add(new Request(null, "POST", "/b8", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b3", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b1", null, null, null, false));
        idempotentSequence.add(new Request(null, "TRACE", "/b4", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b9", null, null, null, false));
        idempotentSequence.add(new Request(null, "LINK", "/b4", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b4", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b5", null, null, null, false));
        idempotentSequence.add(new Request(null, "HEAD", "/b5", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b5", null, null, null, false));
        idempotentSequence.add(new Request(null, "POST", "/b9", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b6", null, null, null, false));
        idempotentSequence.add(new Request(null, "DELETE", "/b6", null, null, null, false));
        idempotentSequence.add(new Request(null, "HEAD", "/b6", null, null, null, false));
        idempotentSequence.add(new Request(null, "OPTIONS", "/b7", null, null, null, false));
        idempotentSequence.add(new Request(null, "TRACE", "/b7", null, null, null, false));
        idempotentSequence.add(new Request(null, "GET", "/b7", null, null, null, false));
        idempotentSequence.add(new Request(null, "PUT", "/b7", null, null, null, false));
        if (!idempotentSequence.isIdempotent(new Request(null, null, "/b1", null, null, null, false))) {
            System.err.println("Sequence b1 failed");
        }
        if (!idempotentSequence.isIdempotent(new Request(null, null, "/b2", null, null, null, false))) {
            System.err.println("Sequence b2 failed");
        }
        if (!idempotentSequence.isIdempotent(new Request(null, null, "/b3", null, null, null, false))) {
            System.err.println("Sequence b3 failed");
        }
        if (idempotentSequence.isIdempotent(new Request(null, null, "/b4", null, null, null, false))) {
            System.err.println("Sequence b4 failed");
        }
        if (!idempotentSequence.isIdempotent(new Request(null, null, "/b5", null, null, null, false))) {
            System.err.println("Sequence b5 failed");
        }
        if (idempotentSequence.isIdempotent(new Request(null, null, "/b6", null, null, null, false))) {
            System.err.println("Sequence b6 failed");
        }
        if (idempotentSequence.isIdempotent(new Request(null, null, "/b7", null, null, null, false))) {
            System.err.println("Sequence b7 failed");
        }
        if (idempotentSequence.isIdempotent(new Request(null, null, "/b8", null, null, null, false))) {
            System.err.println("Sequence b8 failed");
        }
        if (idempotentSequence.isIdempotent(new Request(null, null, "/b9", null, null, null, false))) {
            System.err.println("Sequence b9 failed");
        }
        System.out.println("Tests finished");
    }
    
    static {
        INDET = new Object();
    }
}
