// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.international.resources.ProgressResources;
import com.progress.common.exception.ProException;
import com.progress.common.util.PropertyFilter;
import java.util.Date;
import java.text.DateFormat;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.juniper.admin.JuniperProperties;
import com.progress.juniper.admin.JATools;

public class MergeProperties implements MergePropertiesConst
{
    private static String FILE_SEPARATOR;
    private boolean m_validate;
    private boolean m_backup;
    private boolean m_recurse;
    private int m_type;
    private int m_action;
    private String m_target;
    private String m_delta;
    private String m_propertyFileBase;
    private PropertyManager m_propertyManager;
    protected static boolean m_recursive;
    protected static boolean m_includeAllRegistered;
    protected static boolean m_includeAncestors;
    protected static boolean m_saveComments;
    protected static boolean m_includeCommentedDefaults;
    
    public MergeProperties() {
        this.m_backup = true;
        this.m_validate = false;
        this.m_recurse = false;
        this.m_action = 2;
        this.m_propertyManager = null;
        this.m_propertyFileBase = System.getProperty("Install.Dir") + MergeProperties.FILE_SEPARATOR + "properties" + MergeProperties.FILE_SEPARATOR;
    }
    
    public MergeProperties(final int type, String s) throws mergeFileException, mergePropertyException, mergeGroupException, mergeException {
        this();
        try {
            if (s != null && s.length() != 0 && s.indexOf(MergeProperties.FILE_SEPARATOR) < 0) {
                s = this.m_propertyFileBase + s;
            }
            switch (type) {
                case 1: {
                    if (s == null || s.length() == 0) {
                        s = this.m_propertyFileBase + "conmgr.properties";
                    }
                    JATools.setIsServer();
                    this.m_propertyManager = new JuniperProperties(s);
                    break;
                }
                case 2: {
                    if (s == null || s.length() == 0) {
                        s = this.m_propertyFileBase + "ubroker.properties";
                    }
                    PropMgrUtils.setUpdateUtility(MergeProperties.m_includeAllRegistered = true);
                    final PropMgrUtils propMgrUtils = new PropMgrUtils(s, true, false);
                    this.m_propertyManager = PropMgrUtils.m_propMgr;
                    break;
                }
                case 3: {
                    if (s == null || s.length() == 0) {
                        s = this.m_propertyFileBase + "AdminServerPlugins.properties";
                    }
                    this.m_propertyManager = new MergePropMgr(s);
                    break;
                }
                case 4: {
                    if (s == null || s.length() == 0) {
                        s = this.m_propertyFileBase + "JavaTools.properties";
                    }
                    this.m_propertyManager = new MergePropMgr(s);
                    break;
                }
                case 5: {
                    if (s == null || s.length() == 0) {
                        throw new mergeFileException("");
                    }
                    this.m_propertyManager = new MergePropMgr(s);
                    break;
                }
                default: {
                    return;
                }
            }
            this.m_type = type;
            this.m_target = s;
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            throw new mergeGroupException(ex.getGroupName());
        }
        catch (PropertyManager.PropertyNameException ex2) {
            throw new mergePropertyException(ex2.getPropertyName());
        }
        catch (PropertyManager.PropertyValueException ex3) {
            throw new mergePropertyException(ex3.getPropertyName());
        }
        catch (PropertyManager.LoadIOException ex5) {
            throw new mergeFileException(s);
        }
        catch (PropertyManager.LoadFileNotFoundException ex6) {
            throw new mergeFileException(s);
        }
        catch (PropMgrUtils.LoadPropFileError loadPropFileError) {
            throw new mergeFileException(s);
        }
        catch (Exception ex4) {
            throw new mergeException(ex4.getMessage());
        }
    }
    
    public void setRecurse(final boolean recurse) {
        this.m_recurse = recurse;
    }
    
    public void setValidate(final boolean validate) {
        this.m_validate = validate;
    }
    
    public void setBackup(final boolean backup) {
        this.m_backup = backup;
    }
    
    public void setType(final int type) {
        if (this.m_propertyManager == null) {
            this.m_type = type;
        }
    }
    
    public void setAction(final int action) {
        this.m_action = action;
    }
    
    public void setTargetFile(final String target) {
        if (this.m_propertyManager == null) {
            this.m_target = target;
        }
    }
    
    public void setDeltaFile(final String delta) {
        this.m_delta = delta;
    }
    
    public void mergeprop() throws mergeFileException, mergePropertyException, mergeGroupException, mergeException {
        this.mergeprop(this.m_type, this.m_action, this.m_target, this.m_delta, this.m_backup);
    }
    
    public void mergeprop(final int n, final String s) throws mergeFileException, mergePropertyException, mergeGroupException, mergeException {
        this.mergeprop(this.m_type, n, this.m_target, s, this.m_backup);
    }
    
    public void mergeprop(final int n, int n2, String str, String s, final boolean b) throws mergeFileException, mergePropertyException, mergeGroupException, mergeException {
        try {
            if (s == null) {
                s = "";
            }
            if (n2 == 0) {
                n2 = 2;
            }
            if (str != null && str.length() != 0 && str.indexOf(MergeProperties.FILE_SEPARATOR) < 0) {
                str = this.m_propertyFileBase + str;
            }
            String str2 = null;
            String s2 = null;
            switch (n) {
                case 1: {
                    str2 = "Backup Juniper Properties File: ";
                    s2 = "Merged Juniper Properties File";
                    if (str == null || str.length() == 0) {
                        str = this.m_propertyFileBase + "conmgr.properties";
                    }
                    if (this.m_propertyManager == null) {
                        JATools.setIsServer();
                        this.m_propertyManager = new JuniperProperties(str);
                        break;
                    }
                    break;
                }
                case 2: {
                    str2 = "Backup UBroker Properties File: ";
                    s2 = "Merged UBroker Properties File";
                    if (str == null || str.length() == 0) {
                        str = this.m_propertyFileBase + "ubroker.properties";
                    }
                    MergeProperties.m_includeAllRegistered = true;
                    if (this.m_propertyManager == null) {
                        PropMgrUtils.setUpdateUtility(true);
                        final PropMgrUtils propMgrUtils = new PropMgrUtils(str, true, false);
                        this.m_propertyManager = PropMgrUtils.m_propMgr;
                        break;
                    }
                    break;
                }
                case 3: {
                    str2 = "Backup AdminServer Properties File: ";
                    s2 = "Merged AdminServer Properties File";
                    if (str == null || str.length() == 0) {
                        str = this.m_propertyFileBase + "AdminServerPlugins.properties";
                    }
                    if (this.m_propertyManager == null) {
                        this.m_propertyManager = new MergePropMgr(str);
                        break;
                    }
                    break;
                }
                case 4: {
                    str2 = "Backup Tools Properties File: ";
                    s2 = "Merged Tools Properties File";
                    if (str == null || str.length() == 0) {
                        str = this.m_propertyFileBase + "JavaTools.properties";
                    }
                    if (this.m_propertyManager == null) {
                        this.m_propertyManager = new MergePropMgr(str);
                        break;
                    }
                    break;
                }
                case 5: {
                    str2 = "Backup Properties File: ";
                    s2 = "Merged Properties File";
                    if (str == null || str.length() == 0) {
                        throw new PropertyManager.LoadFileNotFoundException("");
                    }
                    if (this.m_propertyManager == null) {
                        this.m_propertyManager = new MergePropMgr(str);
                        break;
                    }
                    break;
                }
                default: {
                    return;
                }
            }
            final MergeUtility mergeUtility = new MergeUtility(this.m_propertyManager);
            switch (n2) {
                case 5: {
                    if (this.m_recurse && n == 1) {
                        mergeUtility.listDatabaseGroup(s);
                    }
                    else {
                        mergeUtility.listAll(s);
                    }
                }
                case 4: {
                    mergeUtility.listOnly(s);
                }
                default: {
                    if (b) {
                        mergeUtility.doBackup(str, str2 + DateFormat.getDateInstance().format(new Date()));
                    }
                    switch (n2) {
                        case 2: {
                            mergeUtility.update(s);
                            break;
                        }
                        case 3: {
                            if (this.m_recurse && n == 1) {
                                mergeUtility.deleteDatabaseGroup(s);
                                break;
                            }
                            mergeUtility.delete(s);
                            break;
                        }
                        case 1: {
                            mergeUtility.create(s);
                            break;
                        }
                    }
                    if (!this.m_validate) {
                        this.m_propertyManager.save(str, s2, null, MergeProperties.m_recursive, MergeProperties.m_includeAllRegistered, MergeProperties.m_includeAncestors, MergeProperties.m_saveComments, MergeProperties.m_includeCommentedDefaults);
                    }
                    break;
                }
            }
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            throw new mergeGroupException(ex.getGroupName());
        }
        catch (PropertyManager.PropertyNameException ex2) {
            throw new mergePropertyException(ex2.getPropertyName());
        }
        catch (PropertyManager.PropertyValueException ex3) {
            throw new mergePropertyException(ex3.getPropertyName());
        }
        catch (PropertyManager.LoadIOException ex5) {
            throw new mergeFileException(str);
        }
        catch (PropertyManager.LoadFileNotFoundException ex6) {
            throw new mergeFileException(str);
        }
        catch (PropMgrUtils.LoadPropFileError loadPropFileError) {
            throw new mergeFileException(str);
        }
        catch (Exception ex4) {
            throw new mergeException(ex4.getMessage());
        }
    }
    
    public static void main(final String[] array) {
        if (array.length < 3) {
            Usage(1);
        }
        int i = 0;
        int type = 0;
        int action = 0;
        boolean b = false;
        boolean b2 = true;
        boolean recurse = false;
        boolean b3 = false;
        boolean b4 = false;
        String targetFile = null;
        String deltaFile = null;
        String s = null;
        while (i < array.length) {
            s = array[i];
            if (s.equalsIgnoreCase("-delta")) {
                if (++i < array.length) {
                    deltaFile = array[i];
                }
                else {
                    Usage(5);
                }
            }
            if (s.equalsIgnoreCase("-target")) {
                if (++i < array.length) {
                    targetFile = array[i];
                }
                else {
                    Usage(4);
                }
            }
            if (s.equalsIgnoreCase("-type")) {
                if (++i < array.length) {
                    s = array[i];
                }
                else {
                    Usage(1);
                }
                if (s.equalsIgnoreCase("database")) {
                    type = 1;
                }
                if (s.equalsIgnoreCase("ubroker")) {
                    type = 2;
                }
                if (s.equalsIgnoreCase("plugin")) {
                    type = 3;
                }
                if (s.equalsIgnoreCase("tools")) {
                    type = 4;
                }
                if (s.equalsIgnoreCase("none")) {
                    type = 5;
                }
                if (type == 0) {
                    System.err.println(" -type <database|ubroker|plugin|tools|none> must be specified");
                    System.exit(1);
                }
            }
            if (s.equalsIgnoreCase("-recurse")) {
                recurse = true;
            }
            if (s.equalsIgnoreCase("-validate")) {
                b = true;
            }
            if (s.equalsIgnoreCase("-nobackup")) {
                b2 = false;
            }
            if (s.equalsIgnoreCase("-filter")) {
                b4 = true;
            }
            if (s.equalsIgnoreCase("-action")) {
                if (++i < array.length) {
                    s = array[i];
                }
                else {
                    Usage(2);
                }
                if (s.equalsIgnoreCase("list")) {
                    action = 4;
                }
                if (s.equalsIgnoreCase("listall")) {
                    action = 5;
                }
                if (s.equalsIgnoreCase("delete")) {
                    action = 3;
                }
                if (s.equalsIgnoreCase("create")) {
                    action = 1;
                }
                if (s.equalsIgnoreCase("update")) {
                    action = 2;
                }
                if (s.equalsIgnoreCase("multiple")) {
                    b3 = true;
                    action = -1;
                }
                if (action == 0) {
                    System.err.println("-action <list|listall|delete|create|update> must be specified");
                    System.exit(2);
                }
            }
            ++i;
        }
        if (action == -1 && b4) {
            action = 4;
        }
        if (action != 4 && action != 5) {
            if (deltaFile == null) {
                System.err.println("-delta <file> must be specified");
                System.exit(5);
            }
        }
        else if (deltaFile != null) {
            System.err.println("-delta <file> not allowed with LIST or LISTALL");
            System.exit(5);
        }
        else {
            deltaFile = s;
        }
        if (type == 5 && targetFile == null) {
            System.err.println("-target <file> must be specified");
            System.exit(4);
        }
        try {
            if (!b3) {
                final MergeProperties mergeProperties = new MergeProperties();
                mergeProperties.setType(type);
                mergeProperties.setAction(action);
                mergeProperties.setValidate(b);
                mergeProperties.setRecurse(recurse);
                mergeProperties.setBackup(b2);
                mergeProperties.setDeltaFile(deltaFile);
                mergeProperties.setTargetFile(targetFile);
                mergeProperties.mergeprop();
            }
            else {
                final MergeProperties mergeProperties2 = new MergeProperties(type, targetFile);
                if (b4) {
                    mergeProperties2.setFilter();
                    mergeProperties2.mergeprop(4, deltaFile);
                }
                else {
                    mergeProperties2.setValidate(true);
                    mergeProperties2.mergeprop(2, deltaFile);
                    mergeProperties2.setValidate(b);
                    mergeProperties2.setBackup(b2);
                    mergeProperties2.mergeprop(3, deltaFile);
                }
            }
            System.exit(0);
        }
        catch (Exception obj) {
            System.err.println(" Got exeception " + obj);
            obj.printStackTrace();
        }
    }
    
    private void setFilter() {
        if (this.m_propertyManager != null) {
            this.m_propertyManager.setGetPropertyFilter(new PropertyFilter(this.m_propertyManager));
        }
    }
    
    private static void Usage(final int status) {
        System.err.println("Usage: mergeprop <parameter> ...");
        System.err.println("       Where <parameter> is:");
        System.err.println("                 -action <update|create|delete|list|listall");
        System.err.println("                 -type   <dateabase|ubroker|plugin|tools|none>");
        System.err.println("                 -target <file>");
        System.err.println("                 -delta  <file>");
        System.err.println("                 -validate");
        System.err.println("                 -recurse  (-type database only)");
        System.err.println("                 -nobackup");
        System.exit(status);
    }
    
    static {
        MergeProperties.FILE_SEPARATOR = MergeCommandLine.FILE_SEPARATOR;
        MergeProperties.m_recursive = true;
        MergeProperties.m_includeAllRegistered = false;
        MergeProperties.m_includeAncestors = false;
        MergeProperties.m_saveComments = true;
        MergeProperties.m_includeCommentedDefaults = false;
    }
    
    public static class mergeGroupException extends ProException
    {
        public mergeGroupException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property group name", new Object[] { s }), (Object[])null);
        }
    }
    
    public static class mergePropertyException extends ProException
    {
        public mergePropertyException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Invalid property name", new Object[] { s }), (Object[])null);
        }
    }
    
    public static class mergeFileException extends ProException
    {
        public mergeFileException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "Property file not found", new Object[] { s }), (Object[])null);
        }
    }
    
    public static class mergeException extends ProException
    {
        public mergeException(final String s) {
            super(ProgressResources.retrieveTranString("com.progress.international.messages.PRPMsgBundle", "errors found", new Object[] { s }), (Object[])null);
        }
    }
}
