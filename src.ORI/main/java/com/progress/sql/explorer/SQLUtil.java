// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.sql.SQLException;
import java.io.FileOutputStream;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;
import com.progress.international.resources.ProgressResources;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;

public class SQLUtil implements ISQLConstants, ISQLProperties
{
    int m_transactionIsolationLevel;
    static SQLExplorerLog m_log;
    
    public SQLUtil() {
        this.m_transactionIsolationLevel = 0;
    }
    
    public BufferedReader getReaderWithI18NSupport(final String s, final String s2) throws IOException {
        return this.getReaderWithI18NSupport(s, s2, 0);
    }
    
    public BufferedReader getReaderWithI18NSupport(final String s, final String charsetName, final int n) throws IOException {
        InputStreamReader inputStreamReader = null;
        boolean b = false;
        int n2 = 1;
        int n3 = (charsetName.length() <= 0) ? 1 : 0;
        final FileInputStream fileInputStream = new FileInputStream(s);
        if (n3 == 0) {
            try {
                inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
            }
            catch (UnsupportedEncodingException ex) {
                n3 = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                n3 = 1;
            }
        }
        if (n3 != 0) {
            inputStreamReader = new InputStreamReader(fileInputStream);
        }
        BufferedReader bufferedReader = (n == 0) ? new BufferedReader(inputStreamReader) : new BufferedReader(inputStreamReader, n);
        bufferedReader.mark(131000);
        long n4 = 0L;
        String line;
        while ((line = bufferedReader.readLine()) != null && n2 != 0) {
            if (line.startsWith("\u0001\u0002\u0003")) {
                n4 += line.length();
                b = true;
            }
            else {
                n2 = 0;
            }
        }
        if (b && line != null) {
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            inputStreamReader = new InputStreamReader(new FileInputStream(s), "UTF8");
            bufferedReader = ((n == 0) ? new BufferedReader(inputStreamReader) : new BufferedReader(inputStreamReader, n));
            bufferedReader.skip(n4);
        }
        else if (!b) {
            bufferedReader.reset();
        }
        SQLUtil.m_log.log(ProgressResources.retrieveTranString("com.progress.international.messages.SQLMsgBundle", "EncodingUsedToReadFile", (Object)inputStreamReader.getEncoding(), (Object)s));
        return bufferedReader;
    }
    
    public boolean isAtCommand(final String s) {
        return s != null && s.trim().startsWith("@");
    }
    
    public boolean isShellCommand(final String s) {
        return s != null && s.trim().startsWith("!");
    }
    
    public String getShellCommand(final String s) {
        return this.getCommand(s, "!");
    }
    
    public String getAtCommand(final String s) {
        return this.getCommand(s, "@");
    }
    
    public String getCommand(final String s, final String str) {
        String s2 = "";
        final int index = s.indexOf(str);
        final int index2 = s.indexOf(ISQLConstants.NEWLINE);
        if (index != -1) {
            if (index2 != -1) {
                s2 = s.substring(index + 1, index2);
            }
            else {
                s2 = s.substring(index + 1);
            }
        }
        return s2;
    }
    
    public String[] convertStringToArray(final String s) throws IOException {
        if (s == null) {
            return new String[0];
        }
        final Vector vector = new Vector<String>();
        final String delim = " \t\n\r\"'";
        String str;
        if (this.isAtCommand(s)) {
            str = s.substring("@".length());
        }
        else if (this.isShellCommand(s)) {
            str = s.substring("!".length());
        }
        else {
            str = s;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, delim, true);
        while (stringTokenizer.hasMoreTokens()) {
            String obj = stringTokenizer.nextToken();
            if (delim.indexOf(obj) != -1) {
                if (!obj.equals("\"") && !obj.equals("'")) {
                    continue;
                }
                for (String nextToken = "", anObject = obj; stringTokenizer.hasMoreTokens() && !nextToken.equals(anObject); nextToken = stringTokenizer.nextToken(), obj += nextToken) {}
            }
            vector.addElement(obj);
        }
        final String[] array = new String[vector.size()];
        for (int i = 0; i < vector.size(); ++i) {
            String substring = vector.elementAt(i);
            if (!ISQLConstants.WINDOWS_PLATFORM && ((substring.startsWith("\"") && substring.endsWith("\"")) || (substring.startsWith("'") && substring.endsWith("'")))) {
                substring = substring.substring(1, substring.length() - 1);
            }
            array[i] = substring;
        }
        return array;
    }
    
    public boolean exists(final String pathname) {
        return new File(pathname).exists();
    }
    
    public boolean isReadable(final String pathname) {
        return new File(pathname).canRead();
    }
    
    public boolean isWritable(final String s) {
        boolean canWrite = false;
        final File file = new File(s);
        try {
            if (!file.exists()) {
                new FileOutputStream(s).close();
                file.delete();
                canWrite = true;
            }
            else {
                canWrite = file.canWrite();
            }
        }
        catch (IOException ex) {
            canWrite = false;
        }
        return canWrite;
    }
    
    public String sqlExceptionString(final SQLException ex) {
        return "SQLState=" + ex.getSQLState() + ISQLConstants.NEWLINE + "ErrorCode=" + ex.getErrorCode() + ISQLConstants.NEWLINE + ex.getMessage() + ISQLConstants.NEWLINE;
    }
    
    public String sqlExceptionString(final SQLException ex, final String str) {
        return str + ISQLConstants.NEWLINE + this.sqlExceptionString(ex);
    }
    
    public boolean isNumeric(final int n) {
        for (int i = 0; i < ISQLConstants.NUMERIC_TYPES.length; ++i) {
            if (n == ISQLConstants.NUMERIC_TYPES[i]) {
                return true;
            }
        }
        return false;
    }
    
    public int columnWidth(final int a, final int b, final int b2) {
        return Math.max(Math.min(a, b2), b);
    }
    
    public int columnWidth(final int n, final int n2, final int n3, final int a) {
        return Math.max(a, this.columnWidth(n, n2, n3));
    }
    
    public int getStatementType(final String s) {
        String s2 = s.toLowerCase().trim();
        for (char c = '('; s2.length() > 0 && s2.charAt(0) == c; s2 = s2.substring(1).trim()) {}
        int i = 0;
    Label_0097:
        for (i = 0; i < ISQLConstants.STATEMENT_TYPES_LIST.length; ++i) {
            for (int j = 0; j < ISQLConstants.STATEMENT_TYPES_LIST[i].length; ++j) {
                if (s2.startsWith(ISQLConstants.STATEMENT_TYPES_LIST[i][j])) {
                    break Label_0097;
                }
            }
        }
        return i;
    }
    
    protected int whichStatement(final int n, final String s) {
        String s2 = s.toLowerCase();
        int n2 = 0;
        int n3 = 1;
        String[][] array = null;
        switch (n) {
            case 6: {
                array = ISQLConstants.GET_STATEMENT_LIST;
                break;
            }
            case 7: {
                array = ISQLConstants.SET_STATEMENT_LIST;
                break;
            }
            default: {
                array = ISQLConstants.SHOW_STATEMENT_LIST;
                break;
            }
        }
        while (n2 < array.length && n3 != 0) {
            s2 = s.toLowerCase();
            int n4 = 1;
            for (int n5 = 0; n5 < array[n2].length && n4 != 0; ++n5) {
                if (s2.trim().startsWith(array[n2][n5])) {
                    s2 = s2.trim().substring(array[n2][n5].length());
                }
                else {
                    n4 = 0;
                }
            }
            if (n4 == 1) {
                n3 = 0;
            }
            else {
                ++n2;
            }
        }
        if (n == 7) {
            switch (n2) {
                case 0: {
                    this.setTransactionLevel(new Integer(s2.trim().substring(0)));
                    break;
                }
            }
        }
        return n2;
    }
    
    protected int getTransactionLevel() {
        return this.m_transactionIsolationLevel;
    }
    
    protected void setTransactionLevel(final int transactionIsolationLevel) {
        this.m_transactionIsolationLevel = transactionIsolationLevel;
    }
    
    static {
        SQLUtil.m_log = SQLExplorerLog.get();
    }
}
