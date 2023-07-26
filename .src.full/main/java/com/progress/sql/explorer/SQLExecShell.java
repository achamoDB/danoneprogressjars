// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import com.progress.common.util.InstallPath;

public class SQLExecShell implements ISQLConstants
{
    int m_exitValue;
    static InstallPath m_dlc;
    
    public static native int charEscapeToShell(final String p0);
    
    public static native int guiEscapeToShell(final String p0);
    
    public int getEscapeToShellExit() {
        return this.m_exitValue;
    }
    
    public String runEscapeToShell(final int n, final String s) {
        final String property = System.getProperty("user.dir");
        final String string = ".tmp" + System.currentTimeMillis();
        final String fullyQualifyFile = SQLExecShell.m_dlc.fullyQualifyFile("sqlsh.exe");
        String str = "";
        final String property2 = System.getProperty("line.separator");
        final String property3 = System.getProperty("file.separator");
        try {
            str = "";
            final String string2 = property + property3 + string;
            final File file = new File(string2);
            if (ISQLConstants.WINDOWS_PLATFORM) {
                if (s.startsWith("echo")) {
                    final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                    String str2 = s.substring(4).trim();
                    if (str2.substring(str2.length() - 1).equals(";")) {
                        str2 = str2.substring(0, str2.length() - 1).trim();
                    }
                    if (str2.startsWith("\"") || str2.startsWith("'")) {
                        final String substring = str2.substring(0, 1);
                        final String substring2 = str2.substring(1);
                        if (substring.equals(substring2.substring(substring2.length() - 1))) {}
                        str2 = substring2.substring(0, substring2.length() - 1);
                    }
                    if (str2.length() == 0) {
                        bufferedWriter.write("\n");
                    }
                    else {
                        bufferedWriter.write(str2);
                    }
                    bufferedWriter.close();
                }
                else {
                    this.m_exitValue = guiEscapeToShell(fullyQualifyFile.replace('\\', '/') + " " + string2 + " " + s);
                }
            }
            else {
                this.m_exitValue = charEscapeToShell(s + " > " + string2 + " 2>&1");
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                str += line + property2;
            }
            bufferedReader.close();
            file.delete();
        }
        catch (FileNotFoundException ex) {
            str = str + "File not found: " + ex.getMessage();
        }
        catch (IOException ex2) {
            str += ex2.getMessage();
        }
        return str;
    }
    
    public SQLExecShell() {
        this.m_exitValue = 1;
    }
    
    public static void main(final String[] array) {
        final SQLExecShell sqlExecShell = new SQLExecShell();
    }
    
    static {
        SQLExecShell.m_dlc = new InstallPath();
        SQLExecShell.m_dlc = new InstallPath();
        System.load(SQLExecShell.m_dlc.fullyQualifyFile("sqlsh.dll"));
    }
}
