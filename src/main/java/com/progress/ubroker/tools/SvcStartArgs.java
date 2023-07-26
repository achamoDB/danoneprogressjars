// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.StringTokenizer;
import java.util.Vector;
import com.progress.common.util.Environment;
import com.progress.ubroker.util.IPropConst;

public class SvcStartArgs implements IPropConst
{
    public static final String JAVA_EXE = "java";
    public static final String JAVA_EXE_DEF = "java.command";
    public static final String PSC_INSTALL_DIR_DEF = "Install.Dir";
    public static final String JIT_OPT = "java.jit";
    public static final String JVMARGS_OPT = "java.jvmargs";
    public static final String JAVA_POLICY = "java.security.policy";
    public static final String DBG_OPT = "jvmstart.debug";
    public static final String NT_JREHOME_ENV_NAME = "JREHOME";
    public static final String NT_CLASSPATH_ENV_NAME = "CLASSPATH";
    public static final String CANONICAL_NAME = "CanonicalName";
    private static Environment Env;
    Vector m_args;
    String[][] m_envVars;
    String m_rmiURL;
    String m_proPath;
    int numEnvVars;
    String m_javaHome;
    String m_classpath;
    
    public SvcStartArgs(final SvcStartArgsPkt svcStartArgsPkt, final String rmiURL) {
        this.m_args = null;
        this.m_envVars = null;
        this.m_javaHome = null;
        this.m_classpath = null;
        final String property = System.getProperty("file.separator");
        System.getProperty("os.name");
        final String property2 = System.getProperty("Install.Dir");
        String property3 = System.getProperty("java.command");
        final String s = "bin";
        final String property4 = System.getProperty("java.jit");
        String str = svcStartArgsPkt.m_jvmArgs;
        if (str == null || str.length() == 0) {
            str = System.getProperty("java.jvmargs");
        }
        final String property5 = System.getProperty("jvmstart.debug");
        String str2 = svcStartArgsPkt.m_javaPolicy;
        if (str2 == null || str2.length() == 0) {
            str2 = System.getProperty("java.security.policy");
        }
        final String classpath = svcStartArgsPkt.m_classpath;
        boolean b = false;
        final boolean serviceOnNT = SvcStartArgsPkt.isServiceOnNT();
        boolean b2 = false;
        if (this.argumentNeedsFixup(property2)) {
            b2 = true;
        }
        this.m_args = new Vector(21);
        this.m_rmiURL = rmiURL;
        String obj = property2 + property + s + property + "jvmStart";
        if (b2) {
            obj = this.doubleQuoteArgument(obj);
        }
        this.m_args.addElement(obj);
        if (!serviceOnNT) {
            this.m_args.addElement("-d");
            this.m_javaHome = System.getProperty("java.home");
            this.m_classpath = System.getProperty("java.class.path");
            if (svcStartArgsPkt.m_umask != null && svcStartArgsPkt.m_umask.length() > 0) {
                this.m_args.addElement("-r");
                this.m_args.addElement(svcStartArgsPkt.m_umask);
            }
        }
        else {
            this.getNTRunEnv();
        }
        if (classpath != null && classpath.length() > 0) {
            this.m_classpath = classpath;
        }
        if (property3 == null) {
            property3 = "java";
        }
        String s2 = System.getProperty("admsrv.jvm");
        if (s2 == null) {
            s2 = this.m_javaHome + property + s + property + property3;
        }
        if (property5 != null && property5.length() > 0 && property5.equals("1")) {
            this.m_args.addElement("-g");
        }
        final String workDir = svcStartArgsPkt.m_workDir;
        if (workDir != null) {
            this.m_args.addElement("-w");
            this.m_args.addElement(this.fixupArg(workDir));
        }
        final String personality = svcStartArgsPkt.m_personality;
        if (personality != null && personality.equals("NS")) {
            b = true;
        }
        final String username = svcStartArgsPkt.m_username;
        if (username != null && !b && username.length() > 0) {
            String obj2;
            if (!serviceOnNT) {
                obj2 = username + ":" + svcStartArgsPkt.m_groupname + ":" + svcStartArgsPkt.m_password;
            }
            else {
                obj2 = username + ":" + svcStartArgsPkt.m_password;
            }
            this.m_args.addElement("-u");
            this.m_args.addElement(obj2);
        }
        this.m_args.addElement("-o");
        this.m_args.addElement("stdout");
        this.m_proPath = svcStartArgsPkt.m_propath;
        this.m_envVars = svcStartArgsPkt.m_envVars;
        int length = 0;
        if (this.m_envVars != null) {
            length = this.m_envVars.length;
        }
        if (this.m_proPath != null) {
            ++length;
        }
        if (length > 0) {
            this.m_args.addElement("-e");
            this.m_args.addElement(Integer.toString(length));
        }
        this.m_args.addElement(this.fixupArg(s2));
        if (property4 != null && property4.length() > 0) {
            this.m_args.addElement(property4);
        }
        if (str != null && str.length() > 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str);
            while (stringTokenizer.hasMoreElements()) {
                this.m_args.addElement(stringTokenizer.nextToken());
            }
        }
        this.m_args.addElement("-classpath");
        this.m_args.addElement(this.fixupArg(this.m_classpath));
        this.m_args.addElement(this.fixupArg("-DInstall.Dir=" + property2));
        this.m_args.addElement(this.fixupArg("-Djava.security.policy=" + str2));
        final String canonicalName = svcStartArgsPkt.m_canonicalName;
        if (serviceOnNT) {
            this.m_args.addElement(this.fixupArg("-DCanonicalName=" + canonicalName));
        }
        else {
            this.m_args.addElement("-DCanonicalName=" + this.fixupArg(canonicalName));
        }
        this.m_args.addElement(svcStartArgsPkt.m_classMain);
        if (!b) {
            this.m_args.addElement("-t");
            this.m_args.addElement(personality);
        }
        this.m_args.addElement("-i");
        this.m_args.addElement(svcStartArgsPkt.m_svcName);
        this.m_args.addElement("-r");
        this.m_args.addElement(this.m_rmiURL);
        this.m_args.addElement("-f");
        this.m_args.addElement(this.fixupArg(svcStartArgsPkt.m_propFileSpec));
    }
    
    public String[] getArgs() {
        final String[] anArray = new String[this.m_args.size()];
        this.m_args.copyInto(anArray);
        return anArray;
    }
    
    public String[][] getEnvVars() {
        return this.m_envVars;
    }
    
    public String getProPath() {
        return this.m_proPath;
    }
    
    private String fixupArg(final String s) {
        if (this.argumentNeedsFixup(s)) {
            return this.doubleQuoteArgument(s);
        }
        return s;
    }
    
    private boolean argumentNeedsFixup(final String s) {
        return s.indexOf(32) > -1 && s.indexOf(34) <= -1;
    }
    
    private String doubleQuoteArgument(final String str) {
        return new String("\"" + str + "\"");
    }
    
    private void getNTRunEnv() {
        this.m_javaHome = SvcStartArgs.Env.getEnvironmentValue("JREHOME");
        try {
            if (this.m_javaHome != null && this.m_javaHome.length() > 0) {
                this.m_classpath = SvcStartArgs.Env.getEnvironmentValueJNI("CLASSPATH");
                return;
            }
        }
        catch (Exception ex) {}
        this.m_javaHome = "@{JAVA\\JREHOME}";
        this.m_classpath = "@{JAVA\\JRECP};@{JAVA\\PROGRESSCP}";
    }
    
    static {
        SvcStartArgs.Env = new Environment();
    }
}
