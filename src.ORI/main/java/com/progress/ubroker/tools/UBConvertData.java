// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import com.progress.ubroker.util.IPropConst;

public class UBConvertData implements IPropConst
{
    public static final String THE_90A_STRING = "9.0a";
    public static final String THE_90B_STRING = "9.0b";
    public static final String THE_91A_STRING = "9.1a";
    public static final String THE_91B_STRING = "9.1b";
    private Hashtable m_renamesNewtoOld;
    private Hashtable m_renamesOldtoNew;
    private String[] m_moves;
    private static String[][] the90aRenames;
    private static String[] the90aMoves;
    
    public UBConvertData(final String s) {
        this.m_renamesNewtoOld = null;
        this.m_renamesOldtoNew = null;
        this.m_moves = null;
        this.m_renamesNewtoOld = this.createRenames(s, false);
        this.m_renamesOldtoNew = this.createRenames(s, true);
        this.m_moves = this.createMoves(s);
    }
    
    private Hashtable createRenames(final String s, final boolean b) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if (s.equals("9.0a")) {
            if (b) {
                for (int i = 0; i < UBConvertData.the90aRenames.length; ++i) {
                    hashtable.put(UBConvertData.the90aRenames[i][1], UBConvertData.the90aRenames[i][0]);
                }
            }
            else {
                for (int j = 0; j < UBConvertData.the90aRenames.length; ++j) {
                    hashtable.put(UBConvertData.the90aRenames[j][0], UBConvertData.the90aRenames[j][1]);
                }
            }
        }
        else {
            hashtable = null;
        }
        return hashtable;
    }
    
    private String[] createMoves(final String s) {
        String[] the90aMoves;
        if (s.equals("9.0a")) {
            the90aMoves = UBConvertData.the90aMoves;
        }
        else {
            the90aMoves = null;
        }
        return the90aMoves;
    }
    
    public Hashtable getRenames(final boolean b) {
        if (b) {
            return this.m_renamesOldtoNew;
        }
        return this.m_renamesNewtoOld;
    }
    
    public String[] getMoves() {
        return this.m_moves;
    }
    
    static {
        UBConvertData.the90aRenames = new String[][] { { "brkrLoggingLevel", "loggingLevel" }, { "brkrLogAppend", "logFileMode" } };
        UBConvertData.the90aMoves = new String[] { "srvrStartupProc", "srvrShutdownProc", "srvrConnectProc", "srvrDisconnProc", "srvrActivateProc", "srvrDeactivateProc", "srvrStartupProcParam" };
    }
}
