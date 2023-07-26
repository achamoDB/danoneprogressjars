// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import com.progress.common.util.Getopt;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.util.Date;
import java.text.DateFormat;

public class MergeGetopt implements MergePropertiesConst
{
    private static String NEWLINE;
    private static String FILE_SEPARATOR;
    private static final int OPT_HELP = 10;
    private static final int OPT_PROPERTY_TYPE = 20;
    private static final int OPT_ACTION_TYPE = 30;
    private static final int OPT_DELTA = 40;
    private static final int OPT_VALIDATE = 50;
    private static final int OPT_NOBACKUP = 60;
    private static final int OPT_SILENT = 70;
    private static final int OPT_TARGET = 80;
    private static final int OPT_RECURSE = 90;
    private static final int OPT_INSTALL = 100;
    private static final int OPT_UNINSTALL = 110;
    private static final int UNKOPT = 63;
    private int m_propertyType;
    private int m_actionType;
    private String m_deltaFilename;
    private String m_targetFilename;
    private boolean m_validate;
    private boolean m_backup;
    private boolean m_help;
    private boolean m_silent;
    private boolean m_recurse;
    private boolean m_install;
    private boolean m_uninstall;
    private StringBuffer inputArgs;
    private StringBuffer invalidArgs;
    private String m_dlc;
    private String m_groupName;
    
    public String getInputArgs() {
        return this.inputArgs.toString();
    }
    
    public String getInvalidArgs() {
        return this.invalidArgs.toString();
    }
    
    public boolean getHelp() {
        return this.m_help;
    }
    
    private void setHelp(final boolean help) {
        this.m_help = help;
    }
    
    public boolean getSilent() {
        return this.m_silent;
    }
    
    private void setSilent(final boolean silent) {
        this.m_silent = silent;
    }
    
    public boolean getRecurse() {
        return this.m_recurse;
    }
    
    private void setRecurse(final boolean recurse) {
        this.m_recurse = recurse;
    }
    
    public boolean isInstall() {
        return this.m_install;
    }
    
    private void setInstall(final boolean install) {
        this.m_install = install;
    }
    
    public boolean isUninstall() {
        return this.m_uninstall;
    }
    
    private void setUninstall(final boolean uninstall) {
        this.m_uninstall = uninstall;
    }
    
    public String getPropertyFile() {
        final String string = this.m_dlc + MergeGetopt.FILE_SEPARATOR + "properties" + MergeGetopt.FILE_SEPARATOR;
        if (this.m_targetFilename.length() != 0) {
            return this.m_targetFilename;
        }
        if (this.isDatabaseType()) {
            return string + "conmgr.properties";
        }
        if (this.isUbrokerType()) {
            return string + "ubroker.properties";
        }
        if (this.isPluginType()) {
            return string + "AdminServerPlugins.properties";
        }
        if (this.isToolsType()) {
            return string + "JavaTools.properties";
        }
        return "";
    }
    
    private void setPropertyFile(final String targetFilename) {
        this.m_targetFilename = targetFilename;
    }
    
    public String getPropertyFileHeader() {
        final String format = DateFormat.getDateInstance().format(new Date());
        if (this.isDatabaseType()) {
            return "Merged Juniper Properties File: " + format;
        }
        if (this.isUbrokerType()) {
            return "Merged UBroker Properties File: " + format;
        }
        if (this.isPluginType()) {
            return "Merged AdminServer Properties File: " + format;
        }
        if (this.isToolsType()) {
            return "Merged Tools Properties File: " + format;
        }
        return "Merged Properties File: " + format;
    }
    
    public String getPropertyFileHeaderBackup() {
        final String format = DateFormat.getDateInstance().format(new Date());
        if (this.isDatabaseType()) {
            return "Backup Juniper Properties File: " + format;
        }
        if (this.isUbrokerType()) {
            return "Backup UBroker Properties File: " + format;
        }
        if (this.isPluginType()) {
            return "Backup AdminServer Properties File: " + format;
        }
        if (this.isToolsType()) {
            return "Backup Tools Properties File: " + format;
        }
        return "Backup Properties File: " + format;
    }
    
    public boolean isDatabaseType() {
        return this.m_propertyType == 1;
    }
    
    public boolean isUbrokerType() {
        return this.m_propertyType == 2;
    }
    
    public boolean isPluginType() {
        return this.m_propertyType == 3;
    }
    
    public boolean isToolsType() {
        return this.m_propertyType == 4;
    }
    
    public boolean isNoneType() {
        return this.m_propertyType == 5;
    }
    
    public boolean isCreate() {
        return this.m_actionType == 1;
    }
    
    public boolean isDelete() {
        return this.m_actionType == 3;
    }
    
    public boolean isListAll() {
        return this.m_actionType == 5;
    }
    
    public boolean isListOnly() {
        return this.m_actionType == 4;
    }
    
    public boolean isUpdate() {
        return this.m_actionType == 2;
    }
    
    public boolean setActionType(String lowerCase) {
        boolean b = true;
        lowerCase = lowerCase.toLowerCase();
        this.m_actionType = 0;
        if (lowerCase.equals("create")) {
            this.m_actionType = 1;
        }
        else if (lowerCase.equals("delete")) {
            this.m_actionType = 3;
        }
        else if (lowerCase.equals("listall")) {
            this.m_actionType = 5;
        }
        else if (lowerCase.equals("list")) {
            this.m_actionType = 4;
        }
        else if (lowerCase.equals("update")) {
            this.m_actionType = 2;
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean setPropertyType(String lowerCase) {
        boolean b = true;
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("database")) {
            this.m_propertyType = 1;
        }
        else if (lowerCase.equals("ubroker")) {
            this.m_propertyType = 2;
        }
        else if (lowerCase.equals("plugin")) {
            this.m_propertyType = 3;
        }
        else if (lowerCase.equals("tools")) {
            this.m_propertyType = 4;
        }
        else if (lowerCase.equals("none")) {
            this.m_propertyType = 5;
        }
        else {
            b = false;
        }
        return b;
    }
    
    private void setDelta(final String deltaFilename) {
        this.m_deltaFilename = deltaFilename;
    }
    
    public String getDelta() {
        return this.m_deltaFilename;
    }
    
    public boolean getBackup() {
        return this.m_backup;
    }
    
    public String getGroupName() {
        return this.m_groupName;
    }
    
    private void setValidate(final boolean validate) {
        this.m_validate = validate;
    }
    
    public boolean isValidate() {
        return this.m_validate;
    }
    
    public MergeGetopt(final String[] array, final String dlc) {
        this.m_propertyType = 0;
        this.m_actionType = 2;
        this.m_deltaFilename = "";
        this.m_targetFilename = "";
        this.m_validate = false;
        this.m_backup = true;
        this.m_help = false;
        this.m_silent = false;
        this.m_recurse = false;
        this.m_install = false;
        this.m_uninstall = false;
        this.inputArgs = new StringBuffer();
        this.invalidArgs = new StringBuffer();
        this.m_dlc = null;
        this.m_groupName = null;
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PRPMsgBundle");
        this.m_dlc = dlc;
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("type:", 20), new Getopt.GetoptList("action:", 30), new Getopt.GetoptList("delta:", 40), new Getopt.GetoptList("target:", 80), new Getopt.GetoptList("validate", 50), new Getopt.GetoptList("nobackup", 60), new Getopt.GetoptList("help", 10), new Getopt.GetoptList("recurse", 90), new Getopt.GetoptList("silent", 70), new Getopt.GetoptList("install", 100), new Getopt.GetoptList("uninstall", 110), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(true);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 30: {
                    final String optArg = getopt.getOptArg();
                    if (!this.setActionType(optArg)) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("Invalid argument, argument:", (Object)(array[getopt.getOptInd() - 1] + " " + optArg)));
                        continue;
                    }
                    continue;
                }
                case 20: {
                    final String optArg2 = getopt.getOptArg();
                    if (!this.setPropertyType(optArg2)) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("Invalid argument, argument:", (Object)(array[getopt.getOptInd() - 1] + " " + optArg2)));
                        continue;
                    }
                    continue;
                }
                case 40: {
                    final String optArg3 = getopt.getOptArg();
                    final File file = new File(optArg3);
                    if (!file.exists()) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("no delta file", (Object)optArg3));
                        continue;
                    }
                    if (!file.canRead()) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("delta read error", (Object)optArg3));
                        continue;
                    }
                    this.setDelta(optArg3);
                    continue;
                }
                case 80: {
                    final String optArg4 = getopt.getOptArg();
                    final File file2 = new File(optArg4);
                    if (!file2.exists()) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("no target file", (Object)optArg4));
                        continue;
                    }
                    if (!file2.canRead()) {
                        if (this.invalidArgs.length() > 0) {
                            this.invalidArgs.append(MergeGetopt.NEWLINE);
                        }
                        this.invalidArgs.append(progressResources.getTranString("target read error", (Object)optArg4));
                        continue;
                    }
                    this.setPropertyFile(optArg4);
                    continue;
                }
                case 60: {
                    this.m_backup = false;
                    continue;
                }
                case 50: {
                    this.setValidate(true);
                    continue;
                }
                case 10: {
                    this.setHelp(true);
                    continue;
                }
                case 70: {
                    this.setSilent(true);
                    continue;
                }
                case 90: {
                    this.setRecurse(true);
                    continue;
                }
                case 100: {
                    this.setInstall(true);
                    this.setUninstall(false);
                    continue;
                }
                case 110: {
                    this.setUninstall(true);
                    this.setInstall(false);
                    continue;
                }
                case 63: {
                    if (!array[getopt.getOptInd()].startsWith("-") && this.m_groupName == null) {
                        this.m_groupName = getopt.getOptArg();
                        continue;
                    }
                    if (this.invalidArgs.length() > 0) {
                        this.invalidArgs.append(MergeGetopt.NEWLINE);
                    }
                    this.invalidArgs.append(progressResources.getTranString("Invalid argument, argument:", (Object)getopt.getOptArg()));
                    continue;
                }
            }
        }
        for (int i = 0; i < array.length; ++i) {
            this.inputArgs.append(array[i]);
            this.inputArgs.append(" ");
        }
        if (!this.isInstall()) {
            if (!this.isUninstall() || this.getDelta().length() <= 0) {
                return;
            }
        }
        try {
            final FileReader in = new FileReader(this.getDelta());
            final BufferedReader bufferedReader = new BufferedReader(in);
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                final String trim = line.trim();
                if (!trim.startsWith("#")) {
                    continue;
                }
                int fromIndex = 0;
                if (this.isInstall()) {
                    fromIndex = trim.indexOf("install");
                }
                if (this.isUninstall()) {
                    fromIndex = trim.indexOf("uninstall");
                }
                if (fromIndex <= 0) {
                    continue;
                }
                int index = trim.indexOf(61, fromIndex);
                if (index < 0) {
                    continue;
                }
                ++index;
                final StringTokenizer stringTokenizer = new StringTokenizer(trim.substring(index).trim(), ",");
                if (stringTokenizer.countTokens() < 3) {
                    continue;
                }
                this.setPropertyType(stringTokenizer.nextToken());
                this.setActionType(stringTokenizer.nextToken());
                break;
            }
            bufferedReader.close();
            in.close();
        }
        catch (Exception ex) {
            if (this.invalidArgs.length() > 0) {
                this.invalidArgs.append(MergeGetopt.NEWLINE);
            }
            this.invalidArgs.append(progressResources.getTranString("delta read error", (Object)this.getDelta()));
        }
    }
    
    static {
        MergeGetopt.NEWLINE = MergeCommandLine.NEWLINE;
        MergeGetopt.FILE_SEPARATOR = MergeCommandLine.FILE_SEPARATOR;
    }
}
