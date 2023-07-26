// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.ProLog;
import com.progress.common.util.Getopt;
import com.progress.chimera.adminserver.AdminServerType;
import com.progress.message.cfMsg;

public class DbConfigGetopt implements cfMsg
{
    static final String NEWLINE;
    static final int OPT_PROPFILE = 0;
    static final int OPT_DASHF = 1;
    static final int OPT_HELP = 2;
    static final int UNKOPT = 63;
    static final String[] m_keys;
    boolean m_help;
    String m_propFile;
    private String m_inputArgs;
    private String m_invalidArgs;
    
    public String getInputArgs() {
        return this.m_inputArgs;
    }
    
    public String getInvalidArgs() {
        return this.m_invalidArgs.trim();
    }
    
    public boolean helpRequested() {
        return this.m_help;
    }
    
    public String getPropFile() {
        return this.m_propFile;
    }
    
    public DbConfigGetopt(final String[] array) {
        this.m_help = false;
        this.m_propFile = AdminServerType.DB_PROPERTIES_FULLFILE;
        this.m_inputArgs = "";
        this.m_invalidArgs = "";
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList(DbConfigGetopt.m_keys[0] + ":", 0), new Getopt.GetoptList(DbConfigGetopt.m_keys[1] + ":", 0), new Getopt.GetoptList(DbConfigGetopt.m_keys[2], 2), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(false);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 0: {
                    this.m_propFile = getopt.getOptArg();
                    continue;
                }
                case 2: {
                    this.m_help = true;
                    continue;
                }
                case 63: {
                    final int optInd = getopt.getOptInd();
                    final String str = array[optInd];
                    if (array.length == 1 && !array[optInd].startsWith("-")) {
                        this.m_propFile = array[optInd];
                        continue;
                    }
                    this.m_invalidArgs = this.m_invalidArgs + str + " ";
                    continue;
                }
            }
        }
        for (int i = 0; i < array.length; ++i) {
            this.m_inputArgs = this.m_inputArgs + array[i] + " ";
        }
    }
    
    public String usageString() {
        return ProLog.format(7162412257379362585L, "<", this.getPropFile());
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
        m_keys = new String[] { "propertyfile", "f", "help", "" };
    }
}
