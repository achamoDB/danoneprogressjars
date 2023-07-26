// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.net.ProtocolException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;

public class NonStaticCookieModule implements HTTPClientModule
{
    private static Hashtable cookie_cntxt_list;
    private CookiePolicyHandler cookie_handler;
    
    public NonStaticCookieModule() {
        this.cookie_handler = new DefaultCookiePolicyHandler();
    }
    
    public int requestHandler(final Request req, final Response[] resp) {
        NVPair[] hdrs = req.getHeaders();
        int length = hdrs.length;
        for (int idx = 0; idx < hdrs.length; ++idx) {
            final int beg = idx;
            while (idx < hdrs.length && hdrs[idx].getName().equalsIgnoreCase("Cookie")) {
                ++idx;
            }
            if (idx - beg > 0) {
                length -= idx - beg;
                System.arraycopy(hdrs, idx, hdrs, beg, length - beg);
            }
        }
        if (length < hdrs.length) {
            hdrs = Util.resizeArray(hdrs, length);
            req.setHeaders(hdrs);
        }
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, req.getConnection().getContext());
        if (cookie_list.size() == 0) {
            return 0;
        }
        final Vector names = new Vector();
        final Vector lens = new Vector();
        int version = 0;
        synchronized (cookie_list) {
            final Enumeration list = cookie_list.elements();
            Vector remove_list = null;
            while (list.hasMoreElements()) {
                final Cookie cookie = list.nextElement();
                if (cookie.hasExpired()) {
                    Log.write(16, "CookM: cookie has expired and is being removed: ".concat(String.valueOf(String.valueOf(cookie))));
                    if (remove_list == null) {
                        remove_list = new Vector();
                    }
                    remove_list.addElement(cookie);
                }
                else {
                    if (!cookie.sendWith(req) || (this.cookie_handler != null && !this.cookie_handler.sendCookie(cookie, req))) {
                        continue;
                    }
                    int len;
                    int idx2;
                    for (len = cookie.getPath().length(), idx2 = 0; idx2 < lens.size() && lens.elementAt(idx2) >= len; ++idx2) {}
                    names.insertElementAt(cookie.toExternalForm(), idx2);
                    lens.insertElementAt(new Integer(len), idx2);
                    if (!(cookie instanceof Cookie2)) {
                        continue;
                    }
                    version = Math.max(version, ((Cookie2)cookie).getVersion());
                }
            }
            if (remove_list != null) {
                for (int idx3 = 0; idx3 < remove_list.size(); ++idx3) {
                    cookie_list.remove(remove_list.elementAt(idx3));
                }
            }
        }
        // monitorexit(cookie_list)
        if (!names.isEmpty()) {
            final StringBuffer value = new StringBuffer();
            if (version > 0) {
                value.append(String.valueOf(String.valueOf(new StringBuffer("$Version=\"").append(version).append("\"; "))));
            }
            value.append(names.elementAt(0));
            for (int idx4 = 1; idx4 < names.size(); ++idx4) {
                value.append("; ");
                value.append(names.elementAt(idx4));
            }
            hdrs = Util.resizeArray(hdrs, hdrs.length + 1);
            hdrs[hdrs.length - 1] = new NVPair("Cookie", value.toString());
            if (version != 1) {
                int idx4;
                for (idx4 = 0; idx4 < hdrs.length && !hdrs[idx4].getName().equalsIgnoreCase("Cookie2"); ++idx4) {}
                if (idx4 == hdrs.length) {
                    hdrs = Util.resizeArray(hdrs, hdrs.length + 1);
                    hdrs[hdrs.length - 1] = new NVPair("Cookie2", "$Version=\"1\"");
                }
            }
            req.setHeaders(hdrs);
            Log.write(16, String.valueOf(String.valueOf(new StringBuffer("CookM: Sending cookies '").append((Object)value).append("'"))));
        }
        return 0;
    }
    
    public void responsePhase1Handler(final Response resp, final RoRequest req) throws IOException {
        final String set_cookie = resp.getHeader("Set-Cookie");
        final String set_cookie2 = resp.getHeader("Set-Cookie2");
        if (set_cookie == null && set_cookie2 == null) {
            return;
        }
        resp.deleteHeader("Set-Cookie");
        resp.deleteHeader("Set-Cookie2");
        if (set_cookie != null) {
            this.handleCookie(set_cookie, false, req, resp);
        }
        if (set_cookie2 != null) {
            this.handleCookie(set_cookie2, true, req, resp);
        }
    }
    
    public int responsePhase2Handler(final Response resp, final Request req) {
        return 10;
    }
    
    public void responsePhase3Handler(final Response resp, final RoRequest req) {
    }
    
    public void trailerHandler(final Response resp, final RoRequest req) throws IOException {
        final String set_cookie = resp.getTrailer("Set-Cookie");
        final String set_cookie2 = resp.getTrailer("Set-Cookie2");
        if (set_cookie == null && set_cookie2 == null) {
            return;
        }
        resp.deleteTrailer("Set-Cookie");
        resp.deleteTrailer("Set-Cookie2");
        if (set_cookie != null) {
            this.handleCookie(set_cookie, false, req, resp);
        }
        if (set_cookie2 != null) {
            this.handleCookie(set_cookie2, true, req, resp);
        }
    }
    
    private void handleCookie(final String set_cookie, final boolean cookie2, final RoRequest req, final Response resp) throws ProtocolException {
        Cookie[] cookies;
        if (cookie2) {
            cookies = Cookie2.parse(set_cookie, req);
        }
        else {
            cookies = Cookie.parse(set_cookie, req);
        }
        if (Log.isEnabled(16)) {
            Log.write(16, String.valueOf(String.valueOf(new StringBuffer("CookM: Received and parsed ").append(cookies.length).append(" cookies:"))));
            for (int idx = 0; idx < cookies.length; ++idx) {
                Log.write(16, String.valueOf(String.valueOf(new StringBuffer("CookM: Cookie ").append(idx).append(": ").append(cookies[idx]))));
            }
        }
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, req.getConnection().getContext());
        synchronized (cookie_list) {
            for (int idx2 = 0; idx2 < cookies.length; ++idx2) {
                final Cookie cookie3 = cookie_list.get(cookies[idx2]);
                if (cookie3 != null && cookies[idx2].hasExpired()) {
                    Log.write(16, "CookM: cookie has expired and is being removed: ".concat(String.valueOf(String.valueOf(cookie3))));
                    cookie_list.remove(cookie3);
                }
                else if (!cookies[idx2].hasExpired() && (this.cookie_handler == null || this.cookie_handler.acceptCookie(cookies[idx2], req, resp))) {
                    cookie_list.put(cookies[idx2], cookies[idx2]);
                }
            }
        }
        // monitorexit(cookie_list)
    }
    
    public static void discardAllCookies() {
        NonStaticCookieModule.cookie_cntxt_list.clear();
    }
    
    public static void discardAllCookies(final Object context) {
        if (context != null) {
            NonStaticCookieModule.cookie_cntxt_list.remove(context);
        }
    }
    
    public static Cookie[] listAllCookies() {
        synchronized (NonStaticCookieModule.cookie_cntxt_list) {
            Cookie[] cookies = new Cookie[0];
            int idx = 0;
            final Enumeration cntxt_list = NonStaticCookieModule.cookie_cntxt_list.elements();
            while (cntxt_list.hasMoreElements()) {
                final Hashtable hashtable;
                final Hashtable cntxt = hashtable = cntxt_list.nextElement();
                // monitorenter(hashtable)
                try {
                    cookies = Util.resizeArray(cookies, idx + cntxt.size());
                    final Enumeration cookie_list = cntxt.elements();
                    while (cookie_list.hasMoreElements()) {
                        cookies[idx++] = cookie_list.nextElement();
                    }
                    // monitorexit(hashtable)
                    continue;
                }
                finally {}
                break;
            }
            // monitorexit(NonStaticCookieModule.cookie_cntxt_list)
            return cookies;
        }
    }
    
    public static Cookie[] listAllCookies(final Object context) {
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, context);
        synchronized (cookie_list) {
            final Cookie[] cookies = new Cookie[cookie_list.size()];
            int idx = 0;
            final Enumeration enum1 = cookie_list.elements();
            while (enum1.hasMoreElements()) {
                cookies[idx++] = enum1.nextElement();
            }
            // monitorexit(cookie_list)
            return cookies;
        }
    }
    
    public static void addCookie(final Cookie cookie) {
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, HTTPConnection.getDefaultContext());
        cookie_list.put(cookie, cookie);
    }
    
    public static void addCookie(final Cookie cookie, final Object context) {
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, context);
        cookie_list.put(cookie, cookie);
    }
    
    public static void removeCookie(final Cookie cookie) {
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, HTTPConnection.getDefaultContext());
        cookie_list.remove(cookie);
    }
    
    public static void removeCookie(final Cookie cookie, final Object context) {
        final Hashtable cookie_list = Util.getList(NonStaticCookieModule.cookie_cntxt_list, context);
        cookie_list.remove(cookie);
    }
    
    public synchronized CookiePolicyHandler setCookiePolicyHandler(final CookiePolicyHandler handler) {
        final CookiePolicyHandler old = this.cookie_handler;
        this.cookie_handler = handler;
        return old;
    }
    
    static {
        NonStaticCookieModule.cookie_cntxt_list = new Hashtable();
    }
}
