// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Vector;
import java.io.File;
import com.progress.common.util.Getopt;
import com.progress.common.property.PropertyManager;
import com.progress.common.networkevents.EventBroker;

public class CreateDBProperties extends JuniperProperties
{
    String m_invalidArgs;
    String m_target;
    String m_db;
    String m_pf;
    String m_out;
    static final int OPT_HELP = 0;
    static final int OPT_TARGET = 1;
    static final int OPT_DB = 2;
    static final int OPT_PF = 3;
    static final int OPT_OUT = 4;
    static final int UNKOPT = 63;
    static final String[] m_keys;
    
    public CreateDBProperties() throws PropertyException {
        super((EventBroker)null);
        this.m_invalidArgs = "";
        this.m_target = null;
        this.m_db = null;
        this.m_pf = null;
        this.m_out = "conmgr.properties";
    }
    
    private void getOptions(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList(CreateDBProperties.m_keys[0], 0), new Getopt.GetoptList(CreateDBProperties.m_keys[1] + ":", 1), new Getopt.GetoptList(CreateDBProperties.m_keys[2] + ":", 2), new Getopt.GetoptList(CreateDBProperties.m_keys[3] + ":", 3), new Getopt.GetoptList(CreateDBProperties.m_keys[4] + ":", 4), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(false);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 0: {
                    this.usage();
                    System.exit(0);
                    continue;
                }
                case 1: {
                    this.m_target = getopt.getOptArg();
                    continue;
                }
                case 2: {
                    this.m_db = getopt.getOptArg();
                    continue;
                }
                case 3: {
                    this.m_pf = getopt.getOptArg();
                    continue;
                }
                case 4: {
                    this.m_out = getopt.getOptArg();
                    continue;
                }
                case 63: {
                    this.m_invalidArgs = this.m_invalidArgs + array[getopt.getOptInd()] + " ";
                    continue;
                }
            }
        }
        if (this.m_invalidArgs.length() > 0) {
            this.usage();
            System.exit(1);
        }
    }
    
    private void usage() {
        System.err.println("Usage:  java CreateDBProperties -target <displayname> -db <ull-path-db> [<options>]");
        System.err.println("where mandatory arguments are:");
        System.err.println("         -target <displayname> Mandatory.  Database configuration");
        System.err.println("                               display name.");
        System.err.println("         -db <full-path-db>    Mandatory.  Absolute path to physical");
        System.err.println("                               database.");
        System.err.println("and where <options> are:");
        System.err.println("         -pf <filename>        Optional;  filename containing legacy");
        System.err.println("                               arguments and their values.");
        System.err.println("         -out <filename>       Optional;  output file containing");
        System.err.println("                               managed database configuration.");
        System.err.println("                               By default, conmgr.properties is created.");
        System.err.println("                               If file exists, it is appended to.");
    }
    
    private String getOutputFile() {
        return this.m_out;
    }
    
    private String getLegacyPfFile() {
        return this.m_pf;
    }
    
    private String getDatabaseDisplayName() {
        return this.m_target;
    }
    
    private String getDatabasePhysicalName() {
        return this.m_db;
    }
    
    public static void main(final String[] array) {
        final CreateDBProperties createDBProperties = null;
        if (array == null && array.length == 0) {
            createDBProperties.usage();
            System.exit(1);
        }
        try {
            boolean b = false;
            JATools.setIsServer();
            final CreateDBProperties createDBProperties2 = new CreateDBProperties();
            createDBProperties2.getOptions(array);
            final String databaseDisplayName = createDBProperties2.getDatabaseDisplayName();
            if (databaseDisplayName == null) {
                System.err.println("-target <displayName> must be specified.");
                b = true;
            }
            final String databasePhysicalName = createDBProperties2.getDatabasePhysicalName();
            if (databasePhysicalName == null) {
                System.err.println("-db <databaseName> must be specified.");
                b = true;
            }
            if (b) {
                createDBProperties2.usage();
                System.exit(1);
            }
            final String outputFile = createDBProperties2.getOutputFile();
            final File file = new File(outputFile);
            if (file.exists() && file.length() > 0L) {
                System.out.println("Appending to file " + outputFile + "...");
                createDBProperties2.load(outputFile);
            }
            final String legacyPfFile = createDBProperties2.getLegacyPfFile();
            boolean b2 = false;
            if (legacyPfFile != null) {
                if (!new File(legacyPfFile).exists()) {
                    System.out.println("Warning: pfFile specified, " + legacyPfFile + ", does not exist.");
                }
                else {
                    b2 = true;
                }
            }
            final String s = "defaultConfiguration";
            final String s2 = "defaultServerGroup";
            final String str = databaseDisplayName;
            final String string = str + "." + s;
            final String string2 = string + "." + s2;
            createDBProperties2.putProperty("Database." + str + ".displayName", databaseDisplayName);
            createDBProperties2.putProperty("Database." + str + ".databaseName", databasePhysicalName);
            createDBProperties2.putProperty("Configuration." + string + ".displayName", s);
            createDBProperties2.putProperty("ServerGroup." + string2 + ".displayName", s2);
            createDBProperties2.putProperty("Database." + str + ".configurations", string);
            createDBProperties2.putProperty("Database." + str + ".defaultConfiguration", string);
            createDBProperties2.putProperty("Configuration." + string + ".database", str);
            createDBProperties2.putProperty("Configuration." + string + ".serverGroups", string2);
            createDBProperties2.putProperty("ServerGroup." + string2 + ".configuration", string);
            if (b2) {
                final Vector<String> vector = new Vector<String>();
                vector.add(databaseDisplayName);
                vector.add(s);
                vector.add(s2);
                createDBProperties2.processLegacyArguments(legacyPfFile, vector, false);
            }
            createDBProperties2.save(outputFile, "Testing...", null, true, false, false, false);
            System.exit(0);
        }
        catch (InvalidServerGroupException ex) {
            System.out.println(ex.getMessage());
        }
        catch (InvalidProSqlTrcLenException ex2) {
            System.out.println(ex2.getMessage());
        }
        catch (InvalidProSqlTrcCharException ex3) {
            System.out.println(ex3.getMessage());
        }
        catch (Exception obj) {
            System.out.println(" Got exeception " + obj);
            obj.printStackTrace();
        }
    }
    
    static {
        m_keys = new String[] { "help", "target", "db", "pf", "out" };
    }
}
