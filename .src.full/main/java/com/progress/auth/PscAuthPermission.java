// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.auth;

import java.util.StringTokenizer;
import java.util.Set;
import javax.security.auth.Subject;
import java.security.PermissionCollection;
import java.security.BasicPermission;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Matcher;
import java.security.Permission;

public class PscAuthPermission extends Permission
{
    public static final int ACTION_M_NONE = 0;
    public static final int ACTION_M_READ = 1;
    public static final int ACTION_M_WRITE = 2;
    public static final int ACTION_M_EXECUTE = 4;
    public static final int ACTION_M_DELETE = 8;
    public static final int ACTION_M_KILL = 16;
    public static final int ACTION_M_ADDTRIM = 32;
    public static final int ACTION_M_WSA = 64;
    public static final int ACTION_M_DEPLOY_WSA = 128;
    public static final int ACTION_M_ALL = 255;
    public static final String ACTION_NONE = "none";
    public static final String ACTION_READ = "read";
    public static final String ACTION_WRITE = "write";
    public static final String ACTION_EXECUTE = "execute";
    public static final String ACTION_KILL = "kill";
    public static final String ACTION_ADDTRIM = "addtrim";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_WSA = "wsa";
    public static final String ACTION_DEPLOY_WSA = "deployWsa";
    public static final String ACTION_ALL = "*";
    public static final String ACTION_READ_WRITE = "READ,WRITE";
    public static final String ACTION_DENY_READ = "!read";
    public static final String ACTION_DENY_WRITE = "!write";
    public static final String ACTION_DENY_EXECUTE = "!execute";
    public static final String ACTION_DENY_KILL = "!kill";
    public static final String ACTION_DENY_ADDTRIM = "!addtrim";
    public static final String ACTION_DENY_DELETE = "!delete";
    public static final String ACTION_DENY_WSA = "!wsa";
    public static final String ACTION_DENY_DEPLOY_WSA = "!deployWsa";
    public static final String ACTION_DENY_ALL = "!*";
    private String m_permissionName;
    private int m_grantActions;
    private int m_denyActions;
    private Perl5Matcher m_matcher;
    private Pattern m_nameExpression;
    private Exception m_initException;
    private boolean m_initComplete;
    private boolean m_matchAllPermissionNames;
    private boolean m_denyAllActions;
    private boolean m_grantAllActions;
    
    public PscAuthPermission(final String name) {
        super(name);
        this.m_permissionName = "";
        this.m_grantActions = 0;
        this.m_denyActions = 0;
        this.m_matcher = new Perl5Matcher();
        this.m_nameExpression = null;
        this.m_initException = null;
        this.m_initComplete = false;
        this.m_matchAllPermissionNames = false;
        this.m_denyAllActions = false;
        this.m_grantAllActions = false;
        this.init(name);
    }
    
    public PscAuthPermission(final String name, final String s) {
        super(name);
        this.m_permissionName = "";
        this.m_grantActions = 0;
        this.m_denyActions = 0;
        this.m_matcher = new Perl5Matcher();
        this.m_nameExpression = null;
        this.m_initException = null;
        this.m_initComplete = false;
        this.m_matchAllPermissionNames = false;
        this.m_denyAllActions = false;
        this.m_grantAllActions = false;
        this.init(name, s);
    }
    
    private void init(final String s) {
        if (s == null) {
            this.m_initException = new NullPointerException("name can't be null");
        }
        else if (s.equals("")) {
            this.m_initException = new IllegalArgumentException("name can't be empty");
        }
        else if (!this.m_initComplete) {
            this.m_permissionName = this.cleanupRegExpPattern(this.cleanupQuotes(s));
            final Perl5Compiler perl5Compiler = new Perl5Compiler();
            try {
                this.m_nameExpression = perl5Compiler.compile(this.m_permissionName, 32785);
            }
            catch (Exception initException) {
                this.m_initException = initException;
            }
            this.m_initComplete = true;
        }
    }
    
    private void init(final String s, final String s2) {
        this.init(s);
        this.parseActions(s2);
    }
    
    public Exception getInitException() {
        return this.m_initException;
    }
    
    public boolean implies(final Permission permission) {
        boolean b = false;
        if (null == this.m_initException && permission != null && permission.getClass() == this.getClass() && null != this.m_matcher) {
            final PscAuthPermission pscAuthPermission = (PscAuthPermission)permission;
            final int grantActionMask = pscAuthPermission.getGrantActionMask();
            pscAuthPermission.getDenyActionMask();
            if (grantActionMask == (grantActionMask & this.m_grantActions) && this.m_matcher.matches(pscAuthPermission.getName(), this.m_nameExpression)) {
                b = true;
            }
        }
        return b;
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        if (o instanceof PscAuthPermission) {
            final PscAuthPermission pscAuthPermission = (PscAuthPermission)o;
            return this.getName().equals(pscAuthPermission.getName()) && this.getGrantActionMask() == pscAuthPermission.getGrantActionMask() && this.getDenyActionMask() == pscAuthPermission.getDenyActionMask();
        }
        final BasicPermission basicPermission = (BasicPermission)o;
        return this.getName().equals(basicPermission.getName()) && this.getActions().equals(basicPermission.getActions());
    }
    
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    public String getGrantActions() {
        return this.getActions();
    }
    
    public String getActions() {
        String string;
        if (0x0 != (this.m_grantActions & 0x0)) {
            string = new String("none");
        }
        else {
            final StringBuffer sb = new StringBuffer();
            if (0x0 != (this.m_grantActions & 0x1)) {
                sb.append("read");
            }
            if (0x0 != (this.m_grantActions & 0x2)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("write");
            }
            if (0x0 != (this.m_grantActions & 0x4)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("execute");
            }
            if (0x0 != (this.m_grantActions & 0x10)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("kill");
            }
            if (0x0 != (this.m_grantActions & 0x20)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("addtrim");
            }
            if (0x0 != (this.m_grantActions & 0x8)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("delete");
            }
            string = sb.toString();
        }
        return string;
    }
    
    public String getDenyActions() {
        String string;
        if (0x0 != (this.m_grantActions & 0x0)) {
            string = new String("none");
        }
        else {
            final StringBuffer sb = new StringBuffer();
            if (0x0 != (this.m_denyActions & 0x1)) {
                sb.append("!read");
            }
            if (0x0 != (this.m_denyActions & 0x2)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!write");
            }
            if (0x0 != (this.m_denyActions & 0x4)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!execute");
            }
            if (0x0 != (this.m_denyActions & 0x10)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!kill");
            }
            if (0x0 != (this.m_denyActions & 0x20)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!addtrim");
            }
            if (0x0 != (this.m_denyActions & 0x8)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!delete");
            }
            if (0x0 != (this.m_denyActions & 0x40)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!wsa");
            }
            if (0x0 != (this.m_denyActions & 0x80)) {
                if (0 < sb.length()) {
                    sb.append(",");
                }
                sb.append("!deployWsa");
            }
            string = sb.toString();
        }
        return string;
    }
    
    public int getGrantActionMask() {
        return this.m_grantActions;
    }
    
    public int getDenyActionMask() {
        return this.m_grantActions;
    }
    
    public boolean matchGrantAction(final int n) {
        final int n2 = n & 0xFF;
        return n2 == (this.m_grantActions & n2);
    }
    
    public boolean matchDenyAction(final int n) {
        final int n2 = n & 0xFF;
        return n2 == (this.m_grantActions & n2);
    }
    
    public boolean matchPermissionName(final String s) {
        return this.m_matcher.matches(s, this.m_nameExpression);
    }
    
    public boolean isActionDenied(final int n) {
        boolean b = false;
        if (0x0 != (n & this.m_denyActions)) {
            b = true;
        }
        return b;
    }
    
    public PermissionCollection newPermissionCollection() {
        return new PscAuthPermissionCollection();
    }
    
    public boolean canDo(final Subject subject) {
        boolean b = false;
        if (null != subject) {
            try {
                final Set<?> publicCredentials = subject.getPublicCredentials(Class.forName("java.security.Permissions"));
                if (null != publicCredentials) {
                    PermissionCollection collection = null;
                    for (Object o = publicCredentials.iterator().next(); null != o; o = publicCredentials.iterator().next()) {
                        if (o instanceof PermissionCollection) {
                            collection = (PermissionCollection)o;
                            b = collection.implies(this);
                            break;
                        }
                    }
                    if (null != collection) {
                        b = collection.implies(this);
                    }
                }
            }
            catch (Exception ex) {}
        }
        return b;
    }
    
    protected void parseActions(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer((null != s) ? s : "none", ",");
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        this.m_grantActions = 0;
        this.m_denyActions = 0;
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if ("none".equalsIgnoreCase(nextToken)) {
                n = 1;
                n2 = 1;
                n3 = 1;
                n4 = 1;
                n5 = 1;
                n6 = 1;
                n7 = 1;
                n8 = 1;
                this.m_denyActions = 255;
                this.m_grantActions = 0;
            }
            else if (n == 0 && "read".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x1;
            }
            else if (n2 == 0 && "write".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x2;
            }
            else if (n3 == 0 && "execute".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x4;
            }
            else if (n4 == 0 && "kill".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x10;
            }
            else if (n5 == 0 && "addtrim".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x20;
            }
            else if (n6 == 0 && "delete".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x8;
            }
            else if (n7 == 0 && "wsa".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x40;
            }
            else if (n8 == 0 && "deployWsa".equalsIgnoreCase(nextToken)) {
                this.m_grantActions |= 0x80;
            }
            else if (n == 0 && "!read".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x1;
            }
            else if (n2 == 0 && "!write".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x2;
            }
            else if (n3 == 0 && "!execute".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x4;
            }
            else if (n4 == 0 && "!kill".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x10;
            }
            else if (n5 == 0 && "!addtrim".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x20;
            }
            else if (n6 == 0 && "!delete".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x8;
            }
            else if (n7 == 0 && "!wsa".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x40;
            }
            else if (n8 == 0 && "!deployWsa".equalsIgnoreCase(nextToken)) {
                this.m_denyActions |= 0x80;
            }
            else {
                if (0 != this.m_denyActions || !"*".equalsIgnoreCase(nextToken)) {
                    continue;
                }
                n = 1;
                n2 = 1;
                n3 = 1;
                n4 = 1;
                n5 = 1;
                n6 = 1;
                n7 = 1;
                n8 = 1;
                this.m_grantActions |= 0xFF;
                this.m_denyActions = 0;
            }
        }
    }
    
    private String cleanupRegExpPattern(final String s) {
        char char1 = ' ';
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length + 20);
        for (int i = 0; i < length; ++i) {
            final char c = char1;
            char1 = s.charAt(i);
            final char c2 = (i < length - 1) ? s.charAt(i + 1) : ' ';
            if ('*' == char1 && '.' != c && ']' != c && '}' != c && '>' != c && ')' != c) {
                sb.append('.');
            }
            if ('.' == char1 && '\\' != c && '*' != c2 && '+' != c2 && '?' != c2) {
                sb.append('\\');
            }
            sb.append(char1);
        }
        return sb.toString();
    }
    
    private String cleanupQuotes(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length + 20);
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if ('\'' != char1) {
                if ('\"' != char1) {
                    sb.append(char1);
                }
            }
        }
        return sb.toString();
    }
}
