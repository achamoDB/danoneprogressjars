// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.common.networkevents.EventBroker;
import com.progress.ubroker.tools.UbrokerPropertyManager;
import com.progress.common.exception.ProException;
import com.progress.international.resources.ProgressResources;
import com.progress.common.util.UUID;
import com.progress.ubroker.tools.UBPropValidate;
import java.io.Reader;
import java.io.CharArrayReader;
import java.io.Writer;
import java.io.CharArrayWriter;
import java.util.Enumeration;
import java.util.Vector;
import com.progress.common.log.ProLog;
import com.progress.ubroker.tools.UBToolsMsg;
import java.util.Date;
import com.progress.chimera.common.Tools;
import java.util.Hashtable;
import com.progress.common.property.PropertyManager;
import com.progress.common.util.PropertyFilter;

public class PropMgrUtils implements IPropConst, IPUMessages, IUBLogfileInfo
{
    public static UBProperties m_propMgr;
    public static PropertyFilter m_propValFilter;
    public static UBRegProperties m_regPropObj;
    public static final boolean DEBUG_TRACE = false;
    static PropertyManager.PropertyCollection m_parentGroups;
    String[] m_parentGroupNames;
    String[] m_parentGroupValues;
    String[] m_ubWSservices;
    String[] m_ubASservices;
    String[] m_ubORservices;
    String[] m_ubODservices;
    String[] m_ubMSSservices;
    String[] m_msngrServices;
    String[] m_nsServices;
    String[] m_adapterServices;
    String[] m_svcList;
    String[] m_aiaServices;
    String[] m_wsaServices;
    String[] m_ubGroupPath;
    String m_currentGroupName;
    String m_propFilePath;
    UBPreferenceProperties m_preferences;
    boolean m_loadStatus;
    boolean m_checkVersion;
    int m_nextPortNumber;
    Hashtable m_portNumberTable;
    private static boolean m_updateUtility;
    public static final String m_schemaFile = "ubroker.schema";
    
    public static void setUpdateUtility(final boolean updateUtility) {
        PropMgrUtils.m_updateUtility = updateUtility;
    }
    
    public static boolean isUpdateUtility() {
        return PropMgrUtils.m_updateUtility;
    }
    
    public static void setPropertyFilter() {
        PropMgrUtils.m_propValFilter = new PropertyFilter(PropMgrUtils.m_propMgr);
    }
    
    public static String getSvcName(final String s) {
        final int lastIndex = s.lastIndexOf(".");
        if (lastIndex == -1) {
            return null;
        }
        return s.substring(lastIndex + 1);
    }
    
    public static String getSvcTypeStr(final String s) {
        final int index = s.indexOf(".");
        if (index == -1) {
            if (s.equalsIgnoreCase("NameServer")) {
                return "NS";
            }
            return null;
        }
        else {
            final String substring = s.substring(0, index);
            if (substring.equalsIgnoreCase("UBroker")) {
                final int index2 = s.indexOf(".", index + 1);
                if (index2 == -1) {
                    return s.substring(index + 1);
                }
                return s.substring(index + 1, index2);
            }
            else {
                if (substring.equalsIgnoreCase("NameServer")) {
                    return "NS";
                }
                if (substring.equalsIgnoreCase("WebSpeed")) {
                    return "MSNGR";
                }
                if (substring.equalsIgnoreCase("Adapter")) {
                    return "AD";
                }
                if (substring.equalsIgnoreCase("AIA")) {
                    return "AIA";
                }
                if (substring.equalsIgnoreCase("WSA")) {
                    return "WSA";
                }
                return null;
            }
        }
    }
    
    public static String getSvcGroupPath(final String s) {
        String s2 = null;
        if (s.equals("WS") || s.equals("WebSpeed")) {
            s2 = IPropConst.ubGroupPath[1];
        }
        else if (s.equals("AS") || s.equals("AppServer")) {
            s2 = IPropConst.ubGroupPath[2];
        }
        else if (s.equals("NS") || s.equals("NameServer")) {
            s2 = "NameServer";
        }
        else if (s.equals("OR") || s.equals("Oracle DataServer")) {
            s2 = IPropConst.ubGroupPath[3];
        }
        else if (s.equals("OD") || s.equals("ODBC DataServer")) {
            s2 = IPropConst.ubGroupPath[4];
        }
        else if (s.equals("MS") || s.equals("MSS DataServer")) {
            s2 = IPropConst.ubGroupPath[5];
        }
        else if (s.equals("MSNGR") || s.equals("Messengers")) {
            s2 = "WebSpeed.Messengers";
        }
        else if (s.equals("AD") || s.equals("SonicMQ Adapter")) {
            s2 = "Adapter";
        }
        else if (s.equals("AIA") || s.equals("AppServer Internet Adapter")) {
            s2 = "AIA";
        }
        else if (s.equals("WSA") || s.equals("Web Services Adapter")) {
            s2 = "WSA";
        }
        return s2;
    }
    
    public static String getSvcGroupPath(final int n) {
        if (n == 1) {
            return IPropConst.ubGroupPath[1];
        }
        if (n == 2) {
            return IPropConst.ubGroupPath[2];
        }
        if (n == 6) {
            return "NameServer";
        }
        if (n == 3) {
            return IPropConst.ubGroupPath[3];
        }
        if (n == 4) {
            return IPropConst.ubGroupPath[4];
        }
        if (n == 5) {
            return IPropConst.ubGroupPath[5];
        }
        if (n == 7) {
            return "WebSpeed.Messengers";
        }
        if (n == 8) {
            return "Adapter";
        }
        return null;
    }
    
    public static boolean reqUserName(final String s) {
        final int svcTypeFromSvcGrpPath = getSvcTypeFromSvcGrpPath(s);
        return svcTypeFromSvcGrpPath == 1 || svcTypeFromSvcGrpPath == 2 || svcTypeFromSvcGrpPath == 8;
    }
    
    public static int getSvcTypeFromSvcGrpPath(final String s) {
        if (s.indexOf(IPropConst.ubGroupPath[1]) >= 0) {
            return 1;
        }
        if (s.indexOf(IPropConst.ubGroupPath[2]) >= 0) {
            return 2;
        }
        if (s.indexOf("NameServer") >= 0) {
            return 6;
        }
        if (s.indexOf(IPropConst.ubGroupPath[3]) >= 0) {
            return 3;
        }
        if (s.indexOf(IPropConst.ubGroupPath[4]) >= 0) {
            return 4;
        }
        if (s.indexOf(IPropConst.ubGroupPath[5]) >= 0) {
            return 5;
        }
        if (s.indexOf("WebSpeed") >= 0) {
            return 7;
        }
        if (s.indexOf("Adapter") >= 0) {
            return 8;
        }
        if (s.indexOf("AIA") >= 0) {
            return 9;
        }
        if (s.indexOf("WSA") >= 0) {
            return 10;
        }
        return -1;
    }
    
    public static String getFullPropPath(final String str, final String str2) {
        return str2 + "." + str;
    }
    
    public static String getFullPropPath(final String str, final int n) {
        String str2 = "";
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                str2 = IPropConst.ubGroupPath[n];
                break;
            }
            case 6: {
                str2 = "NameServer";
                break;
            }
            case 7: {
                str2 = "WebSpeed.Messengers";
                break;
            }
            case 8: {
                str2 = "Adapter";
                break;
            }
            case 9: {
                str2 = "AIA";
                break;
            }
        }
        if (str2.length() > 0) {
            return str2 + "." + str;
        }
        return null;
    }
    
    public PropMgrUtils() throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this.m_parentGroupNames = null;
        this.m_parentGroupValues = null;
        this.m_ubWSservices = null;
        this.m_ubASservices = null;
        this.m_ubORservices = null;
        this.m_ubODservices = null;
        this.m_ubMSSservices = null;
        this.m_msngrServices = null;
        this.m_nsServices = null;
        this.m_adapterServices = null;
        this.m_svcList = null;
        this.m_aiaServices = null;
        this.m_wsaServices = null;
        this.m_ubGroupPath = null;
        this.m_currentGroupName = null;
        this.m_propFilePath = null;
        this.m_preferences = null;
        this.m_loadStatus = false;
        this.m_checkVersion = true;
        this.m_nextPortNumber = new Integer("3001");
        this.m_portNumberTable = null;
        PropMgrUtils.m_parentGroups = null;
        this.m_propFilePath = null;
        this.m_currentGroupName = null;
        try {
            PropMgrUtils.m_propMgr = new UBProperties();
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex, "UBroker:  Invaid property.");
        }
        catch (Throwable t) {
            Tools.px(t, "UBroker:  Invaid property.");
        }
    }
    
    public PropMgrUtils(final boolean versionCheck) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this();
        PropMgrUtils.m_propMgr.setVersionCheck(versionCheck);
    }
    
    public PropMgrUtils(final boolean versionCheck, final String propFilePath) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this();
        this.m_propFilePath = propFilePath;
        PropMgrUtils.m_propMgr.setVersionCheck(versionCheck);
        try {
            this.m_loadStatus = this.loadPropFile(propFilePath);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new LoadPropFileError(this.m_propFilePath, ex.toString());
        }
    }
    
    public PropMgrUtils(final String s) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this(s, true, true);
    }
    
    public PropMgrUtils(final String propFilePath, final boolean b, final boolean b2) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this.m_parentGroupNames = null;
        this.m_parentGroupValues = null;
        this.m_ubWSservices = null;
        this.m_ubASservices = null;
        this.m_ubORservices = null;
        this.m_ubODservices = null;
        this.m_ubMSSservices = null;
        this.m_msngrServices = null;
        this.m_nsServices = null;
        this.m_adapterServices = null;
        this.m_svcList = null;
        this.m_aiaServices = null;
        this.m_wsaServices = null;
        this.m_ubGroupPath = null;
        this.m_currentGroupName = null;
        this.m_propFilePath = null;
        this.m_preferences = null;
        this.m_loadStatus = false;
        this.m_checkVersion = true;
        this.m_nextPortNumber = new Integer("3001");
        this.m_portNumberTable = null;
        PropMgrUtils.m_parentGroups = null;
        this.m_propFilePath = propFilePath;
        this.m_currentGroupName = null;
        try {
            PropMgrUtils.m_propMgr = new UBProperties(b2);
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex, "PropMgrUtils: " + ex.getMessage());
        }
        if (b) {
            try {
                this.m_loadStatus = this.loadPropFile(this.m_propFilePath, true);
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
                throw new LoadPropFileError(this.m_propFilePath, ex2.toString());
            }
        }
    }
    
    public PropMgrUtils(final String s, final boolean b) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this(s, b, true);
    }
    
    public PropMgrUtils(final String propFilePath, final PropertyManager.IPropertyValueFilter getPropertyFilter) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, CantGetPropCollection {
        this.m_parentGroupNames = null;
        this.m_parentGroupValues = null;
        this.m_ubWSservices = null;
        this.m_ubASservices = null;
        this.m_ubORservices = null;
        this.m_ubODservices = null;
        this.m_ubMSSservices = null;
        this.m_msngrServices = null;
        this.m_nsServices = null;
        this.m_adapterServices = null;
        this.m_svcList = null;
        this.m_aiaServices = null;
        this.m_wsaServices = null;
        this.m_ubGroupPath = null;
        this.m_currentGroupName = null;
        this.m_propFilePath = null;
        this.m_preferences = null;
        this.m_loadStatus = false;
        this.m_checkVersion = true;
        this.m_nextPortNumber = new Integer("3001");
        this.m_portNumberTable = null;
        PropMgrUtils.m_parentGroups = null;
        this.m_propFilePath = propFilePath;
        this.m_currentGroupName = null;
        try {
            PropMgrUtils.m_propMgr = new UBProperties();
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex, "UBroker:  Invaid property.");
        }
        PropMgrUtils.m_propMgr.setGetPropertyFilter(getPropertyFilter);
        try {
            this.m_loadStatus = this.loadPropFile(this.m_propFilePath, true);
        }
        catch (Exception ex2) {
            throw new LoadPropFileError(this.m_propFilePath, ex2.toString());
        }
    }
    
    public void updateCollectStatsDefault(final String s) {
        try {
            if (s.equals(PropMgrUtils.m_propMgr.getProperty("UBroker.collectStatsData"))) {
                return;
            }
            final String property = PropMgrUtils.m_propMgr.getProperty("UBroker.flushStatsData");
            if (s.equals("1") && property.equals("0")) {
                PropMgrUtils.m_propMgr.putProperty("UBroker.flushStatsData", "255");
            }
            PropMgrUtils.m_propMgr.putProperty("UBroker.collectStatsData", s);
            PropMgrUtils.m_propMgr.save(this.m_propFilePath, "Update default for UBroker.collectStatsData");
        }
        catch (Exception ex) {}
    }
    
    public String getPropFilePath() {
        return this.m_propFilePath;
    }
    
    public Hashtable getPortNumbers() {
        if (this.m_portNumberTable == null) {
            this.loadPortNumberTable();
        }
        return this.m_portNumberTable;
    }
    
    private void loadPortNumberTable() {
    }
    
    public boolean loadPropFile(final String s) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, PropertyManager.PropertyVersionException, PropertyManager.PropertyValueException, PropertyManager.PropertySyntaxException {
        return this.loadPropFile(s, false);
    }
    
    public boolean loadPropFile(final String str, final boolean b) throws EnumGroupError, LoadPropFileError, CantGetParentGroup, PropertyManager.PropertyVersionException, PropertyManager.PropertyValueException, PropertyManager.PropertySyntaxException {
        try {
            UBToolsMsg.logMsg(5, "PropMgrUtils.loadPropfile(" + str + ") at " + new Date().toString());
            try {
                PropMgrUtils.m_propMgr.load(str, b, 2001);
            }
            catch (PropertyManager.PropertyVersionException ex6) {}
            catch (PropertyManager.PropertySyntaxException ex) {
                throw new LoadPropFileError(str, ex.toString());
            }
            catch (PropertyManager.LoadFileNotFoundException ex7) {
                try {
                    PropMgrUtils.m_propMgr.createPropertiesFileFromSchema(str);
                }
                catch (Exception ex2) {
                    throw new LoadPropFileError(str, ex2.toString());
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                try {
                    PropMgrUtils.m_parentGroups = this.getProperties("ParentGroup");
                }
                catch (Exception ex3) {
                    ProLog.logdErr("PropMgrUtils", ex3.getMessage());
                }
                this.m_svcList = PropMgrUtils.m_propMgr.groups();
                this.m_ubWSservices = this.getWSSvcGrp();
                this.m_ubASservices = this.getASSvcGrp();
                this.m_nsServices = this.getNSSvcGrp();
                this.m_msngrServices = this.getMsngrSvcGrp();
                this.m_ubORservices = this.getORSvcGrp();
                this.m_ubODservices = this.getODSvcGrp();
                this.m_ubMSSservices = this.getMSSSvcGrp();
                this.m_adapterServices = this.getAdapterSvcGrp();
                this.m_aiaServices = this.getAiaSvcGrp();
                this.m_wsaServices = this.getWsaSvcGrp();
                this.fetchPreferences();
                return this.m_loadStatus = true;
            }
            catch (Exception ex4) {
                throw new EnumGroupError(ex4.toString());
            }
        }
        catch (Exception ex5) {
            throw new LoadPropFileError(str, ex5.toString());
        }
    }
    
    public String[] getParentGroupNames() throws CantGetParentGroup, CantGetPropCollection {
        if (this.m_parentGroupNames != null) {
            return this.m_parentGroupNames;
        }
        if (this.m_loadStatus) {
            Label_0059: {
                if (PropMgrUtils.m_parentGroups == null) {
                    try {
                        PropMgrUtils.m_parentGroups = this.getProperties("ParentGroup");
                        break Label_0059;
                    }
                    catch (Exception ex) {
                        throw new CantGetParentGroup("ParentGroup", ex.toString());
                    }
                }
                PropMgrUtils.m_parentGroups.reset();
            }
            if (PropMgrUtils.m_parentGroups != null) {
                int n = 0;
                this.m_parentGroupNames = new String[PropMgrUtils.m_parentGroups.size()];
                while (PropMgrUtils.m_parentGroups.hasMoreElements()) {
                    final PropertyManager.Property property = (PropertyManager.Property)PropMgrUtils.m_parentGroups.nextElement();
                    if (property != null) {
                        this.m_parentGroupNames[n++] = property.getName();
                    }
                }
                return this.m_parentGroupNames;
            }
        }
        return null;
    }
    
    public String[] getParentGroupValues() throws CantGetParentGroup, CantGetPropCollection {
        if (this.m_parentGroupValues != null) {
            return this.m_parentGroupValues;
        }
        if (this.m_loadStatus) {
            Label_0059: {
                if (PropMgrUtils.m_parentGroups == null) {
                    try {
                        PropMgrUtils.m_parentGroups = this.getProperties("ParentGroup");
                        break Label_0059;
                    }
                    catch (Exception ex) {
                        throw new CantGetParentGroup("ParentGroup", ex.toString());
                    }
                }
                PropMgrUtils.m_parentGroups.reset();
            }
            if (PropMgrUtils.m_parentGroups != null) {
                final Vector vector = new Vector<String>();
                while (PropMgrUtils.m_parentGroups.hasMoreElements()) {
                    final PropertyManager.Property property = (PropertyManager.Property)PropMgrUtils.m_parentGroups.nextElement();
                    if (property != null) {
                        vector.addElement(property.getValue());
                    }
                }
                vector.copyInto(this.m_parentGroupValues = new String[vector.size()]);
                return this.m_parentGroupValues;
            }
        }
        return null;
    }
    
    public String getParentGroupValue(final String s) throws CantGetParentGroup, CantGetPropCollection {
        if (this.m_loadStatus) {
            Label_0047: {
                if (PropMgrUtils.m_parentGroups == null) {
                    try {
                        PropMgrUtils.m_parentGroups = this.getProperties("ParentGroup");
                        break Label_0047;
                    }
                    catch (Exception ex) {
                        throw new CantGetParentGroup("ParentGroup", ex.toString());
                    }
                }
                PropMgrUtils.m_parentGroups.reset();
            }
            if (PropMgrUtils.m_parentGroups != null) {
                return this.getPropValueFromCollection(s, PropMgrUtils.m_parentGroups);
            }
        }
        return null;
    }
    
    public String[] getServicesList() {
        return this.m_svcList;
    }
    
    public String[] getWSServices() {
        return this.m_ubWSservices;
    }
    
    public String[] getASServices() {
        return this.m_ubASservices;
    }
    
    public String[] getNSServices() {
        return this.m_nsServices;
    }
    
    public String[] getORServices() {
        return this.m_ubORservices;
    }
    
    public String[] getAdapterServices() {
        return this.m_adapterServices;
    }
    
    public String[] getODServices() {
        return this.m_ubODservices;
    }
    
    public String[] getMsngrServices() {
        return this.m_msngrServices;
    }
    
    public String[] getMSSServices() {
        return this.m_ubMSSservices;
    }
    
    public String[] getAiaServices() {
        return this.m_aiaServices;
    }
    
    public String[] getWsaServices() {
        return this.m_wsaServices;
    }
    
    public String[] getSvcGrp(final String s, final boolean b, final boolean b2, final boolean b3) throws CantFindGroup {
        try {
            return PropMgrUtils.m_propMgr.groups(s, b, b2, b3);
        }
        catch (Exception ex) {
            throw new CantFindGroup(s, ex.toString());
        }
    }
    
    public String[] getSvcGrpForParentGrp(final String s) throws CantFindGroup {
        String parentGroupValue = null;
        String[] svcGrp;
        try {
            parentGroupValue = this.getParentGroupValue(s);
            svcGrp = this.getSvcGrp(parentGroupValue, false, true, false);
        }
        catch (Exception ex) {
            throw new CantFindGroup(parentGroupValue, (String)null);
        }
        return svcGrp;
    }
    
    public String[] getSvcNameForParentGrp(final String s) throws CantFindGroup {
        String[] svcGrp = null;
        if (s != null) {
            try {
                svcGrp = this.getSvcGrp(s, false, false, false);
            }
            catch (Exception ex) {
                throw new CantFindGroup(s, (String)null);
            }
        }
        return svcGrp;
    }
    
    private String[] getServiceInstanceByType(final String s) throws CantFindGroup {
        String[] svcGrp;
        try {
            svcGrp = this.getSvcGrp(s, false, true, false);
        }
        catch (Exception ex) {
            throw new CantFindGroup(s, (String)null);
        }
        return svcGrp;
    }
    
    public String[] getWSSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType(IPropConst.ubGroupPath[1]);
    }
    
    public String[] getASSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType(IPropConst.ubGroupPath[2]);
    }
    
    public String[] getORSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType(IPropConst.ubGroupPath[3]);
    }
    
    public String[] getODSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType(IPropConst.ubGroupPath[4]);
    }
    
    public String[] getMSSSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType(IPropConst.ubGroupPath[5]);
    }
    
    public String[] getNSSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType("NameServer");
    }
    
    public String[] getMsngrSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType("WebSpeed.Messengers");
    }
    
    public String[] getAdapterSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType("Adapter");
    }
    
    public String[] getAiaSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType("AIA");
    }
    
    public String[] getWsaSvcGrp() throws CantFindGroup {
        return this.getServiceInstanceByType("WSA");
    }
    
    public String[] getWSSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll(IPropConst.ubGroupPath[1]);
    }
    
    public String[] getASSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll(IPropConst.ubGroupPath[2]);
    }
    
    public String[] getORSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll(IPropConst.ubGroupPath[3]);
    }
    
    public String[] getODSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll(IPropConst.ubGroupPath[4]);
    }
    
    public String[] getMSSSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll(IPropConst.ubGroupPath[5]);
    }
    
    public String[] getNSSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll("NameServer");
    }
    
    public String[] getAdapterSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll("Adapter");
    }
    
    public String[] getAiaSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll("AIA");
    }
    
    public String[] getWsaSvcGrpAll() throws CantFindGroup {
        return this.getSvcGrpAll("WSA");
    }
    
    private String[] getSvcGrpAll(final String s) throws CantFindGroup {
        String[] svcGrp;
        try {
            svcGrp = this.getSvcGrp(s, true, true, true);
        }
        catch (Exception ex) {
            throw new CantFindGroup(s, (String)null);
        }
        return svcGrp;
    }
    
    public Vector getAllSvcInstances() {
        final Vector<String> vector = new Vector<String>();
        if (this.m_ubWSservices != null) {
            for (int i = 0; i < this.m_ubWSservices.length; ++i) {
                vector.addElement(this.m_ubWSservices[i]);
            }
        }
        if (this.m_ubASservices != null) {
            for (int j = 0; j < this.m_ubASservices.length; ++j) {
                vector.addElement(this.m_ubASservices[j]);
            }
        }
        if (this.m_ubORservices != null) {
            for (int k = 0; k < this.m_ubORservices.length; ++k) {
                vector.addElement(this.m_ubORservices[k]);
            }
        }
        if (this.m_ubODservices != null) {
            for (int l = 0; l < this.m_ubODservices.length; ++l) {
                vector.addElement(this.m_ubODservices[l]);
            }
        }
        if (this.m_nsServices != null) {
            for (int n = 0; n < this.m_nsServices.length; ++n) {
                vector.addElement(this.m_nsServices[n]);
            }
        }
        if (this.m_adapterServices != null) {
            for (int n2 = 0; n2 < this.m_adapterServices.length; ++n2) {
                vector.addElement(this.m_adapterServices[n2]);
            }
        }
        if (this.m_aiaServices != null) {
            for (int n3 = 0; n3 < this.m_aiaServices.length; ++n3) {
                vector.addElement(this.m_aiaServices[n3]);
            }
        }
        if (this.m_ubMSSservices != null) {
            for (int n4 = 0; n4 < this.m_ubMSSservices.length; ++n4) {
                vector.addElement(this.m_ubMSSservices[n4]);
            }
        }
        if (this.m_wsaServices != null) {
            for (int n5 = 0; n5 < this.m_wsaServices.length; ++n5) {
                vector.addElement(this.m_wsaServices[n5]);
            }
        }
        return vector;
    }
    
    public PropertyManager.PropertyCollection getProperties(final String s) throws CantGetPropCollection {
        try {
            return PropMgrUtils.m_propMgr.properties(s, true, true);
        }
        catch (Exception ex) {
            throw new CantGetPropCollection(s, ex.toString());
        }
    }
    
    public PropertyManager.PropertyCollection getPropertiesNoAncestor(final String s) throws CantGetPropCollection {
        try {
            return PropMgrUtils.m_propMgr.properties(s, false, false);
        }
        catch (Exception ex) {
            throw new CantGetPropCollection(s, ex.toString());
        }
    }
    
    public PropGroupDescriptor findUBPersonStrForSvcName(final String s) {
        final String[] array = new String[2];
        if (this.m_ubWSservices != null) {
            final String matchSvcName = this.matchSvcName(this.m_ubWSservices, s);
            if (matchSvcName != null) {
                return new PropGroupDescriptor(matchSvcName);
            }
        }
        if (this.m_ubASservices != null) {
            final String matchSvcName2 = this.matchSvcName(this.m_ubASservices, s);
            if (matchSvcName2 != null) {
                return new PropGroupDescriptor(matchSvcName2);
            }
        }
        if (this.m_nsServices != null) {
            final String matchSvcName3 = this.matchSvcName(this.m_nsServices, s);
            if (matchSvcName3 != null) {
                return new PropGroupDescriptor(matchSvcName3);
            }
        }
        if (this.m_msngrServices != null) {
            final String matchSvcName4 = this.matchSvcName(this.m_msngrServices, s);
            if (matchSvcName4 != null) {
                return new PropGroupDescriptor(matchSvcName4);
            }
        }
        if (this.m_ubORservices != null) {
            final String matchSvcName5 = this.matchSvcName(this.m_ubORservices, s);
            if (matchSvcName5 != null) {
                return new PropGroupDescriptor(matchSvcName5);
            }
        }
        if (this.m_ubODservices != null) {
            final String matchSvcName6 = this.matchSvcName(this.m_ubODservices, s);
            if (matchSvcName6 != null) {
                return new PropGroupDescriptor(matchSvcName6);
            }
        }
        if (this.m_adapterServices != null) {
            final String matchSvcName7 = this.matchSvcName(this.m_adapterServices, s);
            if (matchSvcName7 != null) {
                return new PropGroupDescriptor(matchSvcName7);
            }
        }
        if (this.m_aiaServices != null) {
            final String matchSvcName8 = this.matchSvcName(this.m_aiaServices, s);
            if (matchSvcName8 != null) {
                return new PropGroupDescriptor(matchSvcName8);
            }
        }
        if (this.m_ubMSSservices != null) {
            final String matchSvcName9 = this.matchSvcName(this.m_ubMSSservices, s);
            if (matchSvcName9 != null) {
                return new PropGroupDescriptor(matchSvcName9);
            }
        }
        if (this.m_wsaServices != null) {
            final String matchSvcName10 = this.matchSvcName(this.m_wsaServices, s);
            if (matchSvcName10 != null) {
                return new PropGroupDescriptor(matchSvcName10);
            }
        }
        return null;
    }
    
    public void updateSvcNameCache(final String s) throws EnumGroupError {
        final int svcTypeFromSvcGrpPath = getSvcTypeFromSvcGrpPath(s);
        try {
            switch (svcTypeFromSvcGrpPath) {
                case 1: {
                    this.m_ubWSservices = this.getWSSvcGrp();
                    break;
                }
                case 2: {
                    this.m_ubASservices = this.getASSvcGrp();
                    break;
                }
                case 3: {
                    this.m_ubORservices = this.getORSvcGrp();
                    break;
                }
                case 4: {
                    this.m_ubODservices = this.getODSvcGrp();
                    break;
                }
                case 5: {
                    this.m_ubMSSservices = this.getMSSSvcGrp();
                    break;
                }
                case 6: {
                    this.m_nsServices = this.getNSSvcGrp();
                    break;
                }
                case 8: {
                    this.m_adapterServices = this.getAdapterSvcGrp();
                    break;
                }
                case 9: {
                    this.m_aiaServices = this.getAiaSvcGrp();
                    break;
                }
                case 10: {
                    this.m_wsaServices = this.getWsaSvcGrp();
                    break;
                }
            }
        }
        catch (Exception ex) {
            throw new EnumGroupError(ex.toString());
        }
    }
    
    public String getPropValueFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        String value = null;
        if (collection != null) {
            collection.reset();
            final PropertyManager.Property value2 = collection.get(s);
            if (value2 != null) {
                value = value2.getValue();
            }
        }
        return value;
    }
    
    public String getPropDefaultValueFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        String defaultValue = null;
        if (collection != null) {
            collection.reset();
            final PropertyManager.Property value = collection.get(s);
            if (value != null) {
                defaultValue = value.getDefaultValue();
            }
        }
        return defaultValue;
    }
    
    public String getPropValueOrDefaultFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        String valueOrDefault = null;
        if (collection != null) {
            collection.reset();
            final PropertyManager.Property value = collection.get(s);
            if (value != null) {
                valueOrDefault = value.getValueOrDefault();
            }
        }
        return valueOrDefault;
    }
    
    public String[] getPropEnumListFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        final PropertyManager.EnumProperty enumProperty = (PropertyManager.EnumProperty)collection.get(s);
        if (enumProperty != null) {
            return enumProperty.getEnum();
        }
        return null;
    }
    
    public String getIntPropValueFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        final PropertyManager.IntProperty intProperty = (PropertyManager.IntProperty)collection.get(s);
        if (intProperty != null) {
            return intProperty.getValue();
        }
        return null;
    }
    
    public String[] getArrayPropValue(final String s) {
        return PropMgrUtils.m_propMgr.getArrayProperty(this.propFullPath(s));
    }
    
    public void putArrayPropValue(final String s, final String[] array) throws CantPutProperties {
        try {
            PropMgrUtils.m_propMgr.putArrayProperty(this.propFullPath(s), array);
        }
        catch (Exception ex) {
            throw new CantPutProperties(ex.toString());
        }
    }
    
    public void putProperties(final String[] array, final String[] array2) throws CantPutProperties {
        final int length = array.length;
        try {
            for (int i = 0; i < length; ++i) {
                PropMgrUtils.m_propMgr.putProperty(this.propFullPath(array[i]), array2[i]);
            }
        }
        catch (Exception ex) {
            throw new CantPutProperties(ex.toString());
        }
    }
    
    public void putProperties(final String[] array, final String[] array2, final PropertyManager.PropertyCollection collection) throws CantPutProperties {
        final int length = array.length;
        try {
            for (int i = 0; i < length; ++i) {
                this.putProperty(this.propFullPath(array[i]), array2[i], collection);
            }
        }
        catch (Exception ex) {
            throw new CantPutProperties(ex.toString());
        }
    }
    
    public boolean putProperty(final String s, final String value, final PropertyManager.PropertyCollection collection) throws CantPutProperty {
        final PropertyManager.Property value2 = collection.get(s);
        if (value2 != null) {
            try {
                value2.setValue(value);
                return true;
            }
            catch (Exception ex) {
                throw new CantPutProperty(s, value, ex.toString());
            }
        }
        return false;
    }
    
    public String getPropertyValue(final String s) throws CantGetPropertyValue {
        return this.getPropertyValue(s, true);
    }
    
    public String getPropertyValue(final String s, final boolean b) throws CantGetPropertyValue {
        try {
            return PropMgrUtils.m_propMgr.getProperty(this.propFullPath(s), b);
        }
        catch (Exception ex) {
            throw new CantGetPropertyValue(s, ex.toString());
        }
    }
    
    public void putPropertyValue(final String s, final String s2) throws CantPutPropertyValue {
        try {
            PropMgrUtils.m_propMgr.putProperty(this.propFullPath(s), s2);
        }
        catch (Exception ex) {
            throw new CantPutPropertyValue(s, s2, ex.toString());
        }
    }
    
    public String getFullPathPropertyValue(final String s) throws CantGetPropertyValue {
        try {
            return PropMgrUtils.m_propMgr.getProperty(s);
        }
        catch (Exception ex) {
            throw new CantGetPropertyValue(s, ex.toString());
        }
    }
    
    public String propFullPath(final String str) {
        if (this.m_currentGroupName != null) {
            return this.m_currentGroupName + "." + str;
        }
        return null;
    }
    
    public void setCurrentGroupName(final String currentGroupName) {
        this.m_currentGroupName = currentGroupName;
    }
    
    public String getCurrentGroupName() {
        return this.m_currentGroupName;
    }
    
    public void saveProperties(final String s, final Hashtable hashtable) throws Exception {
        final Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String s2 = keys.nextElement();
            final String string = s + "." + s2;
            final Integer value = hashtable.get(s2);
            String string2 = value.toString();
            String property = null;
            try {
                property = PropMgrUtils.m_propMgr.getProperty(string);
            }
            catch (Exception ex) {}
            if (string2 == null) {
                string2 = "";
            }
            if (property == null) {
                property = "";
            }
            if (!string2.equals(property)) {
                try {
                    PropMgrUtils.m_propMgr.putProperty(string, string2);
                    if (value instanceof Integer) {
                        PropMgrUtils.m_propMgr.putIntProperty(string, value);
                    }
                    else {
                        PropMgrUtils.m_propMgr.putProperty(string, string2);
                    }
                }
                catch (Exception ex2) {
                    ProLog.logdErr("Ubroker.properties", 3, "Could not save property " + string);
                }
            }
        }
        PropMgrUtils.m_propMgr.save(this.m_propFilePath, "Update property for " + s);
    }
    
    public void saveAll(final String s, final String s2) throws SaveAllError {
        try {
            PropMgrUtils.m_propMgr.save(s, s2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SaveAllError(ex.toString());
        }
    }
    
    public void saveGroup(final String s, final String s2, final String s3, final boolean b, final boolean b2, final boolean b3) throws SaveGroupError {
        try {
            PropMgrUtils.m_propMgr.save(s, s2, s3, b, b2, b3);
        }
        catch (Exception ex) {
            throw new SaveGroupError(s3, s, ex.toString());
        }
    }
    
    public void removeGroup(final String str, final String str2) throws RemoveGroupError {
        this.removeGroup(str2 + "." + str);
    }
    
    public void removeGroup(final String s) throws RemoveGroupError {
        try {
            PropMgrUtils.m_propMgr.removeGroup(s);
            this.updateInstanceList(s);
        }
        catch (Exception ex) {
            throw new RemoveGroupError(s, ex.toString());
        }
    }
    
    public char[] saveGroupPropForRemote(String parentName, final String str) throws SaveGroupForRemoteError {
        final CharArrayWriter charArrayWriter = new CharArrayWriter(500);
        try {
            boolean b = true;
            do {
                PropMgrUtils.m_propMgr.save(charArrayWriter, "Exporting properties for [" + parentName + "]", parentName, b, false, false, false);
                parentName = PropMgrUtils.m_propMgr.getParentName(parentName);
                b = false;
            } while (parentName != null);
            if (str != null) {
                boolean b2 = true;
                String s = "environment." + str;
                do {
                    PropMgrUtils.m_propMgr.save(charArrayWriter, "", s, b2, false, false, false);
                    s = PropMgrUtils.m_propMgr.getParentName(s);
                    b2 = false;
                } while (s != null);
            }
            PropMgrUtils.m_propMgr.save(charArrayWriter, "", "PreferenceRoot", false, false, false, false);
            return charArrayWriter.toCharArray();
        }
        catch (Exception ex) {
            throw new SaveGroupForRemoteError(parentName, ex.toString());
        }
    }
    
    public char[] savePrefPropForRemote() throws SaveGroupForRemoteError {
        final CharArrayWriter charArrayWriter = new CharArrayWriter(500);
        try {
            PropMgrUtils.m_propMgr.save(charArrayWriter, "", "PreferenceRoot", false, false, false, false);
            return charArrayWriter.toCharArray();
        }
        catch (Exception ex) {
            throw new SaveGroupForRemoteError("PreferenceRoot", ex.toString());
        }
    }
    
    public void loadRemoteGroupProp(final char[] buf, final String s, final String s2) throws CantLoadPropertiesRemote {
        try {
            PropMgrUtils.m_propMgr.load(new CharArrayReader(buf));
        }
        catch (Exception ex) {
            throw new CantLoadPropertiesRemote(s, s2, ex.toString());
        }
    }
    
    public void updateRemoteGroupProp(final char[] buf, final String s, final String s2) throws CantLoadPropertiesRemote {
        try {
            PropMgrUtils.m_propMgr.update(new CharArrayReader(buf));
        }
        catch (Exception ex) {
            throw new CantLoadPropertiesRemote(s, s2, ex.toString());
        }
    }
    
    public CfgValidateErrs validateProperties(final PropertiesSaveDescriptor propertiesSaveDescriptor) {
        try {
            final String string = propertiesSaveDescriptor.getPropGroupName() + "." + propertiesSaveDescriptor.getSvcName();
            final Vector propList = propertiesSaveDescriptor.getPropList();
            if (propList.size() > 0) {
                final String[] anArray = new String[propList.size()];
                propList.copyInto(anArray);
                final Vector valueList = propertiesSaveDescriptor.getValueList();
                final String[] anArray2 = new String[valueList.size()];
                valueList.copyInto(anArray2);
                final UBPropValidate ubPropValidate = new UBPropValidate(this, string, anArray, anArray2);
                ubPropValidate.validatePropList();
                final CfgValidateErrs valMessageObject = ubPropValidate.getValMessageObject();
                valMessageObject.getInvalidProplist();
                valMessageObject.getErrCodeList();
                valMessageObject.getErrMsgList();
                return valMessageObject;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new CfgValidateErrs();
    }
    
    public CfgValidateErrs validateOneProperty(final String s, final String s2, final String s3) {
        final UBPropValidate ubPropValidate = new UBPropValidate(this, s, s2, s3);
        ubPropValidate.validateProp();
        return ubPropValidate.getValMessageObject();
    }
    
    public char[] getPropertiesStream(final String str, final String str2) throws SaveGroupForRemoteError {
        String string;
        if (str2 != null) {
            string = str + "." + str2;
        }
        else {
            string = str;
        }
        return this.getPropertiesStream(string);
    }
    
    public char[] getPropertiesStream(final String s) throws SaveGroupForRemoteError {
        return this.saveGroupPropForRemote(s, this.getEnvSubKeyStr(s));
    }
    
    public String getEnvSubKeyStr(final String str) {
        String property = null;
        try {
            property = PropMgrUtils.m_propMgr.getProperty(str + "." + "environment");
            if (property.trim().length() == 0) {
                property = null;
            }
        }
        catch (Exception ex) {}
        return property;
    }
    
    public String getFullEnvSubKeyStr(final String str, final int n) {
        return "Environment." + this.getEnvSubKeyStr(getSvcGroupPath(n) + "." + str);
    }
    
    public boolean isPropertyFileLoaded() {
        return this.m_loadStatus;
    }
    
    public PropertyManager.PropertyCollection getCustomizedEnvironment(final PropertyManager.PropertyCollection collection) {
        final String propValueFromCollection = this.getPropValueFromCollection("environment", collection);
        PropertyManager.PropertyCollection properties = null;
        if (propValueFromCollection != null) {
            try {
                properties = this.getProperties("Environment." + propValueFromCollection);
                this.m_currentGroupName = "Environment." + propValueFromCollection;
            }
            catch (CantGetPropCollection collection2) {}
            catch (Exception ex) {}
        }
        return properties;
    }
    
    public PropertyManager.PropertyCollection getCustomizedEnvironment(final String s) {
        PropertyManager.PropertyCollection properties = null;
        if (s != null) {
            try {
                properties = this.getProperties("Environment." + s);
                this.m_currentGroupName = "Environment." + s;
            }
            catch (CantGetPropCollection collection) {}
            catch (Exception ex) {}
        }
        return properties;
    }
    
    public synchronized String[][] getCustomizedEnvVars(final PropertyManager.PropertyCollection collection) {
        return this.getCustomizedEnvVars(collection, false);
    }
    
    public synchronized String[][] getCustomizedEnvVars(final PropertyManager.PropertyCollection collection, final boolean b) {
        final String currentGroupName = this.m_currentGroupName;
        PropertyManager.PropertyCollection customizedEnvironment = null;
        try {
            customizedEnvironment = this.getCustomizedEnvironment(collection);
        }
        catch (Exception ex) {}
        if (customizedEnvironment != null) {
            String[][] array;
            if (b) {
                array = this.expandEnvVars(customizedEnvironment, false);
            }
            else {
                array = this.getEnvVars(customizedEnvironment);
            }
            this.m_currentGroupName = currentGroupName;
            return array;
        }
        return null;
    }
    
    public synchronized String[][] getCustomizedEnvVars(final String s) {
        return this.getCustomizedEnvVars(s, true);
    }
    
    public synchronized String[][] getCustomizedEnvVars(final String s, final boolean b) {
        final String currentGroupName = this.m_currentGroupName;
        PropertyManager.PropertyCollection customizedEnvironment = null;
        try {
            customizedEnvironment = this.getCustomizedEnvironment(s);
        }
        catch (Exception ex) {}
        if (customizedEnvironment != null) {
            String[][] array;
            if (b) {
                array = this.expandEnvVars(customizedEnvironment, true);
            }
            else {
                array = this.getEnvVars(customizedEnvironment);
            }
            this.m_currentGroupName = currentGroupName;
            return array;
        }
        return null;
    }
    
    private String[][] getEnvVars(final PropertyManager.PropertyCollection collection) {
        final String[][] array = new String[collection.size()][2];
        int n = 0;
        while (collection.hasMoreElements()) {
            array[n][0] = ((PropertyManager.Property)collection.nextElement()).getName();
            try {
                array[n][1] = this.getPropertyValue(array[n][0]);
            }
            catch (Exception ex) {
                array[n][1] = null;
            }
            ++n;
        }
        return array;
    }
    
    private String[][] expandEnvVars(final PropertyManager.PropertyCollection collection, final boolean b) {
        final String[][] array = new String[collection.size()][2];
        int n = 0;
        PropMgrUtils.m_propMgr.setGetPropertyFilter(PropMgrUtils.m_propValFilter);
        while (collection.hasMoreElements()) {
            final PropertyManager.Property property = (PropertyManager.Property)collection.nextElement();
            array[n][0] = property.getName();
            try {
                array[n][1] = this.getPropertyValue(array[n][0]);
            }
            catch (Exception ex) {
                if (b) {
                    array[n][1] = null;
                }
                else {
                    array[n][1] = property.getValue();
                }
            }
            ++n;
        }
        PropMgrUtils.m_propMgr.setGetPropertyFilter(null);
        return array;
    }
    
    public boolean EnvPairsCountValid(final String s) {
        final boolean b = true;
        try {
            final String envSubKeyStr = this.getEnvSubKeyStr(s);
            if (envSubKeyStr != null) {
                final PropertyManager.PropertyCollection customizedEnvironment = this.getCustomizedEnvironment(envSubKeyStr);
                if (customizedEnvironment != null) {
                    return customizedEnvironment.size() <= 32;
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
        return b;
    }
    
    public String getUBWorkDir() {
        return this.setGroupGetProperty("UBroker", "workDir");
    }
    
    public synchronized String getExpandedUBWorkDir() {
        final String currentGroupName = this.m_currentGroupName;
        this.m_currentGroupName = "UBroker";
        String propertyValue = null;
        try {
            this.setGetPropertyFilter();
            propertyValue = this.getPropertyValue("workDir");
            PropMgrUtils.m_propMgr.setGetPropertyFilter(null);
        }
        catch (Exception ex) {}
        this.m_currentGroupName = currentGroupName;
        return propertyValue;
    }
    
    public synchronized String getExpandedPropertyValue(final String s, final String s2) {
        return this.getExpandedPropertyValue(s, s2, true);
    }
    
    public synchronized String getExpandedPropertyValue(final String s, final String currentGroupName, final boolean b) {
        final String currentGroupName2 = this.m_currentGroupName;
        this.m_currentGroupName = currentGroupName;
        String propertyValue = null;
        try {
            this.setGetPropertyFilter();
            propertyValue = this.getPropertyValue(s, b);
            PropMgrUtils.m_propMgr.setGetPropertyFilter(null);
        }
        catch (Exception ex) {}
        this.m_currentGroupName = currentGroupName2;
        return propertyValue;
    }
    
    public String getNSLocation(final String s) {
        return this.setGroupGetProperty(s, "location");
    }
    
    public String[] getNameServerInstances() throws CantFindGroup {
        String[] svcGrp;
        try {
            svcGrp = this.getSvcGrp("NameServer", false, false, false);
        }
        catch (Exception ex) {
            throw new CantFindGroup("NameServer", (String)null);
        }
        return svcGrp;
    }
    
    public String[] getAdminRoles() throws CantFindGroup {
        String[] svcGrp;
        try {
            svcGrp = this.getSvcGrp("AdminRole", false, false, false);
        }
        catch (Exception ex) {
            throw new CantFindGroup("AdminRole", (String)null);
        }
        return svcGrp;
    }
    
    public void updateInstanceList(final String s) {
        final int svcTypeFromSvcGrpPath = getSvcTypeFromSvcGrpPath(s);
        try {
            switch (svcTypeFromSvcGrpPath) {
                case 1: {
                    this.m_ubWSservices = this.getWSSvcGrp();
                    break;
                }
                case 2: {
                    this.m_ubASservices = this.getASSvcGrp();
                    break;
                }
                case 6: {
                    this.m_nsServices = this.getNSSvcGrp();
                    break;
                }
                case 3: {
                    this.m_ubORservices = this.getORSvcGrp();
                    break;
                }
                case 4: {
                    this.m_ubODservices = this.getODSvcGrp();
                    break;
                }
                case 5: {
                    this.m_ubMSSservices = this.getMSSSvcGrp();
                    break;
                }
                case 8: {
                    this.m_adapterServices = this.getAdapterSvcGrp();
                    break;
                }
                case 9: {
                    this.m_aiaServices = this.getAiaSvcGrp();
                }
                case 10: {
                    this.m_wsaServices = this.getWsaSvcGrp();
                    break;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public String getAutoStartValue(final String s) {
        return this.setGroupGetProperty(s, "autoStart");
    }
    
    public boolean isNameServerPersonality(final String s) {
        return s.equals("NameServer");
    }
    
    public UBPreferenceProperties getPreferences() {
        return this.m_preferences;
    }
    
    public int getPreferenceIntProperty(final String str) {
        return PropMgrUtils.m_propMgr.getIntProperty("PreferenceRoot.Preference." + str);
    }
    
    public void putPreferences(final UBPreferenceProperties preferences) {
        final String s = "PreferenceRoot.Preference.";
        try {
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolConnectSvcRetry", preferences.m_toolConnectSvcRetry);
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolConnectSvcRetryInterval", preferences.m_toolConnectSvcRetryInterval);
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolPingSvcRetry", preferences.m_toolPingSvcRetry);
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolShutdownSvcConfirmRetry", preferences.m_toolShutdownSvcConfirmRetry);
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolShutdownSvcConfirmRetryInterval", preferences.m_toolShutdownSvcConfirmRetryInterval);
            PropMgrUtils.m_propMgr.putIntProperty(s + "toolGetSvcStatusRetry", preferences.m_toolGetSvcStatusRetry);
            PropMgrUtils.m_propMgr.putIntProperty(s + "admSrvrRegisteredRetry", preferences.m_admSrvrRegisteredRetry);
            PropMgrUtils.m_propMgr.putIntProperty(s + "admSrvrRegisteredRetryInterval", preferences.m_admSrvrRegisteredRetryInterval);
            this.m_preferences = preferences;
        }
        catch (Exception ex) {}
    }
    
    public synchronized void reloadProperties(final String s) throws EnumGroupError {
        try {
            PropMgrUtils.m_propMgr.update(s);
            try {
                PropMgrUtils.m_parentGroups = this.getProperties("ParentGroup");
            }
            catch (Exception ex2) {}
            this.m_svcList = PropMgrUtils.m_propMgr.groups();
            this.m_ubWSservices = this.getWSSvcGrp();
            this.m_ubASservices = this.getASSvcGrp();
            this.m_nsServices = this.getNSSvcGrp();
            this.m_msngrServices = this.getMsngrSvcGrp();
            this.m_ubORservices = this.getORSvcGrp();
            this.m_ubODservices = this.getODSvcGrp();
            this.m_adapterServices = this.getAdapterSvcGrp();
            this.m_ubMSSservices = this.getMSSSvcGrp();
            this.m_wsaServices = this.getWsaSvcGrp();
            this.refreshPreferences();
            this.m_loadStatus = true;
        }
        catch (Exception ex) {
            throw new EnumGroupError(ex.toString());
        }
    }
    
    private String setGroupGetProperty(final String currentGroupName, final String s) {
        final String currentGroupName2 = this.m_currentGroupName;
        this.m_currentGroupName = currentGroupName;
        String propertyValue = null;
        try {
            propertyValue = this.getPropertyValue(s);
        }
        catch (Exception ex) {}
        this.m_currentGroupName = currentGroupName2;
        return propertyValue;
    }
    
    public void fetchPreferences() {
        if (this.m_preferences == null) {
            this.m_preferences = new UBPreferenceProperties(this);
        }
        else {
            this.m_preferences.refetchAll(this);
        }
    }
    
    public void refreshPreferences() {
        this.fetchPreferences();
    }
    
    public boolean removeProperty(final String s) {
        boolean b = false;
        try {
            PropMgrUtils.m_propMgr.removeProperty(s);
            b = true;
        }
        catch (Exception ex) {}
        return b;
    }
    
    public boolean uniqueSvcName(final String s) {
        return this.findUBPersonStrForSvcName(s) == null;
    }
    
    private boolean updateLogFileName(final String s, final String str, final String s2) {
        boolean b = false;
        try {
            PropMgrUtils.m_propMgr.putProperty(s + "." + str + "." + s2, this.adjustLogFilename(PropMgrUtils.m_propMgr.getProperty(s + "." + s2), str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            b = false;
        }
        return b;
    }
    
    private String adjustLogFilename(final String s, final String s2) {
        String string = null;
        final String property = System.getProperty("file.separator");
        if (s.indexOf(s2) < 0) {
            final int lastIndex = s.lastIndexOf(property);
            string = s.substring(0, lastIndex + 1) + s2 + "." + s.substring(lastIndex + 1);
        }
        return string;
    }
    
    public boolean handleAddNew(final String s, final String s2) {
        final boolean b = false;
        final int svcTypeFromSvcGrpPath = getSvcTypeFromSvcGrpPath(s);
        if (svcTypeFromSvcGrpPath == 6) {
            return this.handleNSAddNew(s, s2, "local");
        }
        if (svcTypeFromSvcGrpPath == 9) {
            return this.handleAiaAddNew(s, s2);
        }
        if (svcTypeFromSvcGrpPath == 2 || svcTypeFromSvcGrpPath == 1) {
            this.updateLogFileName(s, s2, "mqBrokerLogFile");
            this.updateLogFileName(s, s2, "mqServerLogFile");
        }
        this.updateLogFileName(s, s2, "srvrLogFile");
        this.updateLogFileName(s, s2, "brokerLogFile");
        final String fullPropPath = getFullPropPath(s2, s);
        try {
            final String string = new UUID().toString();
            final String[] nameServerInstances = this.getNameServerInstances();
            String s3 = null;
            if (nameServerInstances != null && nameServerInstances.length > 0) {
                s3 = nameServerInstances[0];
            }
            PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "uuid", string);
            if (s3 != null) {
                PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "controllingNameServer", s3);
            }
            if (svcTypeFromSvcGrpPath == 8) {
                PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "appserviceNameList", PropMgrUtils.m_propMgr.findGroup("Adapter").findRegisteredProperty("appserviceNameList".toLowerCase()).getDefaultValue());
            }
            else {
                PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "appserviceNameList", s2);
            }
            this.saveAll(this.m_propFilePath, fullPropPath + " is added");
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (b) {
                try {
                    this.removeGroup(fullPropPath);
                }
                catch (Exception ex2) {}
            }
            return false;
        }
    }
    
    public boolean handleAiaAddNew(final String str, final String s) {
        final String fullPropPath = getFullPropPath(s, str);
        boolean b;
        try {
            PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "logFile", this.adjustLogFilename(PropMgrUtils.m_propMgr.getProperty(str + "." + "logFile"), s));
            b = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                this.removeGroup(fullPropPath);
            }
            catch (Exception ex3) {}
            return false;
        }
        try {
            final String[] nameServerInstances = this.getNameServerInstances();
            String s2 = null;
            if (nameServerInstances != null && nameServerInstances.length > 0) {
                s2 = nameServerInstances[0];
            }
            if (s2 != null) {
                PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "controllingNameServer", s2);
            }
            b = true;
            this.saveAll(this.m_propFilePath, fullPropPath + " is added");
            return true;
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
            if (b) {
                try {
                    this.removeGroup(fullPropPath);
                }
                catch (Exception ex4) {}
            }
            return false;
        }
    }
    
    public boolean handleWsaAddNew(final String str, final String s, final String s2, final String s3) {
        boolean b = false;
        final String fullPropPath = getFullPropPath(s, str);
        try {
            PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "logFile", this.adjustLogFilename(PropMgrUtils.m_propMgr.getProperty(str + "." + "logFile"), s));
            b = true;
            if (getSvcTypeFromSvcGrpPath(str) == 10) {
                try {
                    if (s2.equals("remote")) {
                        PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "location", s2);
                    }
                    PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "wsaUrl", s3);
                }
                catch (Exception ex2) {}
                this.saveAll(this.m_propFilePath, fullPropPath + " is added");
                return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (b) {
                try {
                    this.removeGroup(fullPropPath);
                }
                catch (Exception ex3) {}
            }
        }
        return false;
    }
    
    public boolean handleNSAddNew(final String str, final String s, final String s2) {
        boolean b = false;
        final String fullPropPath = getFullPropPath(s, str);
        try {
            PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "srvrLogFile", this.adjustLogFilename(PropMgrUtils.m_propMgr.getProperty(str + "." + "srvrLogFile"), s));
            b = true;
            if (getSvcTypeFromSvcGrpPath(str) == 6) {
                if (s2.equals("remote")) {
                    try {
                        PropMgrUtils.m_propMgr.putProperty(fullPropPath + "." + "location", s2);
                    }
                    catch (Exception ex2) {}
                }
                this.saveAll(this.m_propFilePath, fullPropPath + " is added");
                return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (b) {
                try {
                    this.removeGroup(fullPropPath);
                }
                catch (Exception ex3) {}
            }
        }
        return false;
    }
    
    public int getNSInstRefCnt(final String s) {
        int n = 0;
        if (this.m_ubWSservices.length > 0) {
            n += this.getNSRefCnt(s, this.m_ubWSservices, "controllingNameServer");
        }
        if (this.m_ubASservices.length > 0) {
            n += this.getNSRefCnt(s, this.m_ubASservices, "controllingNameServer");
        }
        if (this.m_ubORservices.length > 0) {
            n += this.getNSRefCnt(s, this.m_ubORservices, "controllingNameServer");
        }
        if (this.m_ubODservices.length > 0) {
            n += this.getNSRefCnt(s, this.m_ubODservices, "controllingNameServer");
        }
        if (this.m_msngrServices.length > 0) {
            n += this.getNSRefCnt(s, this.m_msngrServices, "controllingNameServer");
        }
        if (this.m_nsServices.length > 0) {
            n += this.getNSRefCnt(s, this.m_nsServices, "neighborNameServers");
        }
        return n;
    }
    
    public Hashtable getLogFNList(final String s) {
        final int svcTypeFromSvcGrpPath = getSvcTypeFromSvcGrpPath(s);
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        switch (svcTypeFromSvcGrpPath) {
            case 1:
            case 2:
            case 8: {
                final String expandedPropertyValue = this.getExpandedPropertyValue("srvrLogFile", s);
                final String expandedPropertyValue2 = this.getExpandedPropertyValue("brokerLogFile", s);
                hashtable.put(ProgressResources.retrieveTranString("com.progress.international.messages.MMCMsgBundle", "SRVR_LOG_DISP_NAME"), expandedPropertyValue);
                hashtable.put(ProgressResources.retrieveTranString("com.progress.international.messages.MMCMsgBundle", "BRKR_LOG_DISP_NAME"), expandedPropertyValue2);
                break;
            }
            case 6: {
                hashtable.put(ProgressResources.retrieveTranString("com.progress.international.messages.MMCMsgBundle", "SRVR_LOG_DISP_NAME"), this.getExpandedPropertyValue("srvrLogFile", s));
                break;
            }
            case 3:
            case 4:
            case 5: {
                hashtable.put(ProgressResources.retrieveTranString("com.progress.international.messages.MMCMsgBundle", "BRKR_LOG_DISP_NAME"), this.getExpandedPropertyValue("brokerLogFile", s));
            }
            case 9:
            case 10: {
                hashtable.put(ProgressResources.retrieveTranString("com.progress.international.messages.MMCMsgBundle", "LOG_DISP_NAME"), this.getExpandedPropertyValue("logFile", s));
                break;
            }
        }
        return hashtable;
    }
    
    private int getNSRefCnt(final String s, final String[] array, final String str) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            try {
                final String property = PropMgrUtils.m_propMgr.getProperty(array[i] + "." + str);
                if (str.equals("controllingNameServer")) {
                    if (s.equals(property)) {
                        ++n;
                    }
                }
                else if (s.indexOf(property) > 0) {
                    ++n;
                }
            }
            catch (Exception ex) {}
        }
        return n;
    }
    
    private synchronized int getNextFreePortNumber() {
        return this.m_nextPortNumber += 100;
    }
    
    private void setGetPropertyFilter() {
        if (PropMgrUtils.m_propValFilter == null) {
            setPropertyFilter();
        }
        PropMgrUtils.m_propMgr.setGetPropertyFilter(PropMgrUtils.m_propValFilter);
    }
    
    private String matchSvcName(final String[] array, final String s) {
        int n = 1;
        int n2 = 0;
        while (n != 0 && n2 < array.length) {
            final int index = array[n2].indexOf("." + s);
            if (index > 0) {
                if (array[n2].substring(index + 1).equals(s)) {
                    n = 0;
                }
                else {
                    ++n2;
                }
            }
            else {
                ++n2;
            }
        }
        if (n == 0) {
            return array[n2];
        }
        return null;
    }
    
    static {
        PropMgrUtils.m_propValFilter = null;
        PropMgrUtils.m_regPropObj = null;
        PropMgrUtils.m_parentGroups = null;
        PropMgrUtils.m_updateUtility = false;
    }
    
    public static class CantLoadPropertiesRemote extends ProException
    {
        private String m_parentObjectException;
        
        public CantLoadPropertiesRemote(final String s, final String s2, final String parentObjectException) {
            super(8103383104523083835L, new Object[] { s, s2 });
            this.m_parentObjectException = parentObjectException;
        }
        
        public String getParentObjectExecption() {
            return this.m_parentObjectException;
        }
    }
    
    public static class EnumGroupError extends ProException
    {
        public EnumGroupError(final String s) {
            super(8103383104523083836L, new Object[] { s });
        }
    }
    
    public static class LoadPropFileError extends ProException
    {
        String m_parentExceptionStr;
        
        public LoadPropFileError(final String s, final String parentExceptionStr) {
            super(8103383104523083837L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantFindGroup extends ProException
    {
        String m_parentExceptionStr;
        
        public CantFindGroup(final String s, final String parentExceptionStr) {
            super(8103383104523083838L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantGetPropCollection extends ProException
    {
        String m_parentExceptionStr;
        
        public CantGetPropCollection(final String s, final String parentExceptionStr) {
            super(8103383104523083839L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantPutProperty extends ProException
    {
        String m_parentExceptionStr;
        
        public CantPutProperty(final String s, final String s2, final String parentExceptionStr) {
            super(8103383104523083840L, new Object[] { s, s2 });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantGetPropertyValue extends ProException
    {
        String m_parentExceptionStr;
        
        public CantGetPropertyValue(final String s, final String parentExceptionStr) {
            super(8103383104523083841L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantPutPropertyValue extends ProException
    {
        String m_parentExceptionStr;
        
        public CantPutPropertyValue(final String s, final String s2, final String parentExceptionStr) {
            super(8103383104523083842L, new Object[] { s, s2 });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantPutProperties extends ProException
    {
        public CantPutProperties(final String s) {
            super(8103383104523083843L, new Object[] { s });
        }
    }
    
    public static class SaveGroupError extends ProException
    {
        String m_parentExceptionStr;
        
        public SaveGroupError(final String s, final String s2, final String parentExceptionStr) {
            super(8103383104523083844L, new Object[] { s, s2 });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class SaveAllError extends ProException
    {
        public SaveAllError(final String s) {
            super(8103383104523083847L, new Object[] { s });
        }
    }
    
    public static class SaveGroupForRemoteError extends ProException
    {
        String m_parentExceptionStr;
        
        public SaveGroupForRemoteError(final String s, final String parentExceptionStr) {
            super(8103383104523083845L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class CantGetParentGroup extends ProException
    {
        String m_parentExceptionStr;
        
        public CantGetParentGroup(final String s, final String parentExceptionStr) {
            super(8103383104523083846L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    public static class RemoveGroupError extends ProException
    {
        String m_parentExceptionStr;
        
        public RemoveGroupError(final String s, final String parentExceptionStr) {
            super(8103383104523083848L, new Object[] { s });
            this.m_parentExceptionStr = parentExceptionStr;
        }
        
        public String getParentExceptionStr() {
            return this.m_parentExceptionStr;
        }
    }
    
    class UBRegProperties
    {
        boolean m_regParentGroups;
        boolean m_regUBCommonProp;
        boolean m_regUBWSProp;
        boolean m_regUBASProp;
        boolean m_regNSProp;
        boolean m_regEnvGroup;
        boolean m_regWebSpeedGroup;
        boolean m_regORDSProp;
        boolean m_regODDSProp;
        boolean m_regMSSDSProp;
        boolean m_regPreferences;
        boolean m_regAdapterGroup;
        boolean m_regAiaGroup;
        
        public UBRegProperties() {
            this.m_regParentGroups = false;
            this.m_regUBCommonProp = false;
            this.m_regUBWSProp = false;
            this.m_regUBASProp = false;
            this.m_regNSProp = false;
            this.m_regEnvGroup = false;
            this.m_regWebSpeedGroup = false;
            this.m_regORDSProp = false;
            this.m_regODDSProp = false;
            this.m_regMSSDSProp = false;
            this.m_regPreferences = false;
            this.m_regAdapterGroup = false;
            this.m_regAiaGroup = false;
            try {
                this.regParentGroups();
                this.regUBCommonProperties();
                this.regUBWSProperties();
                this.regUBASProperties();
                this.regNSProperties();
                this.regEnvPropGroup();
                this.regWebSpeedGroup();
                this.regORDSProperties();
                this.regODDSProperties();
                this.regMSSDSProperties();
                this.regAdapterProperties();
                this.regAiaProperties();
                this.regPreferences();
            }
            catch (Exception ex) {
                UBToolsMsg.logException("Failed to register properties: " + ex.toString());
            }
        }
        
        private void regParentGroups() throws PropertyManager.PropertyValueException {
            PropMgrUtils.m_propMgr.registerGroup("ParentGroup", false, false, true, new PropertyManager.Property[] { new PropertyManager.Property("WebSpeed", IPropConst.ubGroupPath[1]), new PropertyManager.Property("AppServer", IPropConst.ubGroupPath[2]), new PropertyManager.Property("Oracle DataServer", IPropConst.ubGroupPath[3]), new PropertyManager.Property("ODBC DataServer", IPropConst.ubGroupPath[4]), new PropertyManager.Property("MSS DataServer", IPropConst.ubGroupPath[5]), new PropertyManager.Property("NameServer", "NameServer"), new PropertyManager.Property("Messengers", "WebSpeed.Messengers"), new PropertyManager.Property("SonicMQ Adapter", "Adapter"), new PropertyManager.Property("AppServer Internet Adapter", "AIA") });
            this.m_regParentGroups = true;
        }
        
        private void regUBCommonProperties() throws PropertyManager.PropertyValueException {
            final String[] array = { "Stateless", "State-reset", "State-aware" };
            final String[] array2 = { "1", "0" };
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[0], false, false, true, new PropertyManager.Property[] { new PropertyManager.EnumProperty("operatingMode", "Stateless", array, 0), new PropertyManager.IntProperty("portNumber", "3001", 0), new PropertyManager.Property("userName", "", 0), new PropertyManager.Property("password", "", 0), new PropertyManager.Property("groupName", "", 0), new PropertyManager.Property("workDir", " ", 0), new PropertyManager.Property("srvrLogFile", " ", 0), new PropertyManager.Property("brokerLogFile", " ", 0), new PropertyManager.IntProperty("brkrLoggingLevel", IPropConst.BRKR_LOGLEVEL_DEF_VAL.toString(), 0), new PropertyManager.EnumProperty("brkrLogAppend", "0", array2, 0), new PropertyManager.EnumProperty("4glSrcCompile", "0", array2, 0), new PropertyManager.IntProperty("srvrLoggingLevel", IPropConst.SRVR_LOGLEVEL_DEF_VAL.toString(), 0), new PropertyManager.EnumProperty("srvrLogAppend", "0", array2, 0), new PropertyManager.IntProperty("srvrStartupTimeout", IPropConst.SRVR_STARTUP_TIMEOUT_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("requestTimeout", IPropConst.REQUEST_TIMEOUT_DEF_VAL.toString(), 0), new PropertyManager.EnumProperty("autoStart", "0", array2, 0), new PropertyManager.EnumProperty("defaultService", "0", array2, 0), new PropertyManager.Property("srvrStartupParam", "-p web/objects/web_disp.p cpstream iso8859-1 -weblogerror", 0), new PropertyManager.IntProperty("initialSrvrInstance", IPropConst.INI_SRVR_INST_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("minSrvrInstance", IPropConst.MIN_SRVR_INST_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("maxSrvrInstance", IPropConst.MAX_SRVR_INST_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("srvrMinPort", "3202", 0), new PropertyManager.IntProperty("srvrMaxPort", "3502", 0), new PropertyManager.Property("controllingNameServer", " ", 0), new PropertyManager.IntProperty("priorityWeight", IPropConst.PRIO_WEIGHT_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("registrationRetry", IPropConst.REG_RETRY_DEF_VAL.toString(), 0), new PropertyManager.Property("srvrExecFile", " ", 0), new PropertyManager.Property("PROPATH", " ", 0), new PropertyManager.Property("environment", " ", 0), new PropertyManager.IntProperty("infoVersion", "9010", 0), new PropertyManager.Property("appserviceNameList", " ", 0), new PropertyManager.Property("uuid", " ", 0), new PropertyManager.Property("description", " ", 0), new PropertyManager.Property("jvmArgs", (System.getProperty("os.name").indexOf("Windows") < 0) ? "$JVMARGS" : "${JAVA\\JVMARGS}", 0), new PropertyManager.IntProperty("maxClientInstance", IPropConst.MAX_CLIENT_INST_DEF_VAL.toString(), 0), new PropertyManager.IntProperty("autoTrimTimeout", IPropConst.AUTO_TRIM_TIMEOUT_DEF_VAL.toString(), 0), new PropertyManager.Property("registrationMode", "Register-IP", 0), new PropertyManager.Property("hostName", " ", 0) });
            this.m_regUBCommonProp = true;
        }
        
        private void regUBWSProperties() throws PropertyManager.PropertyValueException {
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[1], false, false, true, new PropertyManager.Property[] { new PropertyManager.EnumProperty("operatingMode", "Stateless", new String[] { "Stateless" }, 0), new PropertyManager.Property("srvrExecFile", "_progres.exe", 1), new PropertyManager.EnumProperty("srvrAppMode", "Development", new String[] { "Development", "Production" }, 1), new PropertyManager.EnumProperty("srvrDebug", "Default", new String[] { "Default", "Enabled", "Disabled" }, 1), new PropertyManager.EnumProperty("defaultService", "0", new String[] { "1", "0" }, 1), new PropertyManager.Property("applicationURL", " ", 1), new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t WS", 1), new PropertyManager.Property("defaultCookiePath", " ", 1), new PropertyManager.Property("defaultCookieDomain", " ", 1), new PropertyManager.Property("wsRoot", "/webspeed", 1), new PropertyManager.Property("fileUploadDirectory", " ", 1), new PropertyManager.IntProperty("binaryUploadMaxSize", IPropConst.BIN_UPLOAD_MAXSIZE_DEF.toString(), 1) });
            this.m_regUBWSProp = true;
        }
        
        private void regUBASProperties() throws PropertyManager.PropertyValueException {
            final String[] array = { "1", "0" };
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[2], false, false, true, new PropertyManager.Property[] { new PropertyManager.Property("srvrStartupProc", " ", 2), new PropertyManager.Property("srvrStartupProcParam", " ", 2), new PropertyManager.Property("srvrConnectProc", " ", 2), new PropertyManager.Property("srvrShutdownProc", " ", 2), new PropertyManager.Property("srvrDisconnProc", " ", 2), new PropertyManager.Property("srvrActivateProc", " ", 2), new PropertyManager.Property("srvrDeactivateProc", " ", 2), new PropertyManager.Property("srvrExecFile", "_proapsv.exe", 2), new PropertyManager.EnumProperty("defaultService", "1", array, 2), new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t AS", 2), new PropertyManager.EnumProperty("DebuggerEnabled", "0", array, 2) });
            this.m_regUBASProp = true;
        }
        
        private void regNSProperties() throws PropertyManager.PropertyValueException {
            final String[] array = { "local", "remote" };
            final String[] array2 = { "0", "1" };
            PropMgrUtils.m_propMgr.registerGroup("NameServer", false, false, false, new PropertyManager.Property[] { new PropertyManager.Property("infoVersion", "9010", 6), new PropertyManager.Property("hostName", "localhost", 6), new PropertyManager.Property("workDir", " ", 6), new PropertyManager.Property("classMain", "com.progress.nameserver.NameServer", 6), new PropertyManager.Property("portNumber", "", 6), new PropertyManager.Property("brokerKeepAliveTimeout", IPropConst.BRKR_KEEP_ALIVE_TIMEOUT_VAL.toString(), 6), new PropertyManager.Property("environment", " ", 6), new PropertyManager.Property("srvrLogFile", " ", 6), new PropertyManager.Property("neighborNameServers", " ", 6), new PropertyManager.EnumProperty("location", "local", array, 6), new PropertyManager.EnumProperty("autoStart", "0", array2, 6), new PropertyManager.EnumProperty("logAppend", "1", array2, 6), new PropertyManager.IntProperty("loggingLevel", IPropConst.LOGLEVEL_DEF.toString(), 6), new PropertyManager.Property("jvmArgs", (System.getProperty("os.name").indexOf("Windows") < 0) ? "$JVMARGS" : "${JAVA\\JVMARGS}", 6) });
            this.m_regNSProp = true;
        }
        
        private void regEnvPropGroup() throws PropertyManager.PropertyValueException {
            PropMgrUtils.m_propMgr.registerGroup("Environment", false, false, false, null, true);
            this.m_regEnvGroup = true;
        }
        
        private void regWebSpeedGroup() throws PropertyManager.PropertyValueException {
            final String[] array = { "0", "1" };
            final PropertyManager.Property[] array2 = { new PropertyManager.Property("infoVersion", "9010", 7), new PropertyManager.Property("Components", "", 7), new PropertyManager.Property("ScriptPath", "", 7), new PropertyManager.Property("rootPath", "", 7) };
            final PropertyManager.Property[] array3 = { new PropertyManager.Property("controllingNameServer", ""), new PropertyManager.EnumProperty("AllowMsngrCmds", "0", array), new PropertyManager.Property("msngrExecFile", ""), new PropertyManager.Property("msngrScriptFile", ""), new PropertyManager.Property("workDir", ""), new PropertyManager.EnumProperty("useConnID", "0", array), new PropertyManager.IntProperty("minNSClientPort", IPropConst.NSCLIENTPORT_DEF.toString()), new PropertyManager.IntProperty("maxNSClientPort", IPropConst.NSCLIENTPORT_DEF.toString()), new PropertyManager.IntProperty("nsClientPortRetryInterval", IPropConst.NSCLIENTPORT_DEF.toString()), new PropertyManager.IntProperty("nsClientPortRetry", IPropConst.NSCLIENTPORT_DEF.toString()) };
            PropMgrUtils.m_propMgr.registerGroup("WebSpeed", false, false, false, array2, true);
            PropMgrUtils.m_propMgr.registerGroup("WebSpeed.Messengers", false, false, false, array3, true);
            this.m_regWebSpeedGroup = true;
        }
        
        private void regORDSProperties() {
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[3], false, false, false, new PropertyManager.Property[] { new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t OR", 3) }, true);
            this.m_regORDSProp = true;
        }
        
        private void regODDSProperties() {
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[4], false, false, false, new PropertyManager.Property[] { new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t OD", 4) }, true);
            this.m_regODDSProp = true;
        }
        
        private void regMSSDSProperties() {
            PropMgrUtils.m_propMgr.registerGroup(IPropConst.ubGroupPath[5], false, false, false, new PropertyManager.Property[] { new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t MS", 5) }, true);
            this.m_regMSSDSProp = true;
        }
        
        private void regAdapterProperties() throws PropertyManager.PropertyValueException {
            final String[] array = { "1", "0" };
            PropMgrUtils.m_propMgr.registerGroup("Adapter", false, false, true, new PropertyManager.Property[] { new PropertyManager.IntProperty("portNumber", "3600", 8), new PropertyManager.Property("userName", "", 8), new PropertyManager.Property("password", "", 8), new PropertyManager.Property("groupName", "", 8), new PropertyManager.Property("workDir", " ", 8), new PropertyManager.Property("srvrLogFile", " ", 8), new PropertyManager.Property("brokerLogFile", " ", 8), new PropertyManager.IntProperty("brkrLoggingLevel", IPropConst.BRKR_LOGLEVEL_DEF_VAL.toString(), 8), new PropertyManager.EnumProperty("brkrLogAppend", "0", array, 8), new PropertyManager.IntProperty("srvrLoggingLevel", IPropConst.SRVR_LOGLEVEL_DEF_VAL.toString(), 8), new PropertyManager.EnumProperty("srvrLogAppend", "0", array, 8), new PropertyManager.Property("controllingNameServer", " ", 8), new PropertyManager.IntProperty("registrationRetry", IPropConst.REG_RETRY_DEF_VAL.toString(), 8), new PropertyManager.Property("environment", " ", 8), new PropertyManager.IntProperty("infoVersion", "9010", 8), new PropertyManager.Property("appserviceNameList", " ", 8), new PropertyManager.Property("uuid", " ", 8), new PropertyManager.Property("description", " ", 8), new PropertyManager.Property("jvmArgs", (System.getProperty("os.name").indexOf("Windows") < 0) ? "$JVMARGS" : "${JAVA\\JVMARGS}", 8), new PropertyManager.IntProperty("maxClientInstance", IPropConst.MAX_CLIENT_INST_DEF_VAL.toString(), 8), new PropertyManager.Property("registrationMode", "Register-IP", 8), new PropertyManager.Property("hostName", " ", 8), new PropertyManager.Property("classMain", "com.progress.ubroker.ubroker -t AD", 8), new PropertyManager.Property("srvrStartupParam", "", 8) });
            this.m_regAdapterGroup = true;
        }
        
        private void regAiaProperties() throws PropertyManager.PropertyValueException {
            final String[] array = { "1", "0" };
            final String s = (System.getProperty("os.name").indexOf("Windows") < 0) ? "$JVMARGS" : "${JAVA\\JVMARGS}";
            PropMgrUtils.m_propMgr.registerGroup("Adapter", false, false, true, new PropertyManager.Property[] { new PropertyManager.Property("logFile", " ", 9), new PropertyManager.IntProperty("loggingLevel", IPropConst.LOGLEVEL_DEF_VAL.toString(), 9), new PropertyManager.EnumProperty("logAppend", "0", array, 9), new PropertyManager.Property("controllingNameServer", " ", 9), new PropertyManager.EnumProperty("httpsEnabled", "0", array, 9), new PropertyManager.IntProperty("idleConnectionTimeout", IPropConst.IDDLECON_TIMEOUT_DEF_VAL.toString(), 9), new PropertyManager.IntProperty("minNSClientPort", IPropConst.NSCLIENTPORT_DEF.toString(), 9), new PropertyManager.IntProperty("maxNSClientPort", IPropConst.NSCLIENTPORT_DEF.toString(), 9), new PropertyManager.IntProperty("nsClientPortRetryInterval", IPropConst.NSCLIENTPORT_DEF.toString(), 9), new PropertyManager.IntProperty("nsClientPortRetry", IPropConst.NSCLIENTPORT_DEF.toString(), 9), new PropertyManager.IntProperty("securePort", IPropConst.SECUREPORT_DEF.toString(), 9), new PropertyManager.IntProperty("soReadTimeout", IPropConst.SO_READ_TIMEOUT_DEFAULT.toString(), 9), new PropertyManager.EnumProperty("allowAiaCmds", "0", array, 9), new PropertyManager.Property("adminIPList", " ", 9) });
            this.m_regAdapterGroup = true;
        }
        
        private void regPreferences() throws PropertyManager.PropertyValueException {
            PropMgrUtils.m_propMgr.registerGroup("PreferenceRoot", false, true, true, new PropertyManager.Property[] { new PropertyManager.IntProperty("toolGetSvcStatusRetry", new Long(12L).toString()), new PropertyManager.IntProperty("toolPingSvcRetry", new Long(4L).toString()), new PropertyManager.IntProperty("toolShutdownSvcConfirmRetry", new Long(10L).toString()), new PropertyManager.IntProperty("toolShutdownSvcConfirmRetryInterval", new Long(3000L).toString()), new PropertyManager.IntProperty("toolConnectSvcRetry", new Long(20L).toString()), new PropertyManager.IntProperty("toolConnectSvcRetryInterval", new Long(3000L).toString()), new PropertyManager.IntProperty("admSrvrRegisteredRetry", new Long(6L).toString()), new PropertyManager.IntProperty("admSrvrRegisteredRetryInterval", new Long(3000L).toString()) }, true);
            PropMgrUtils.m_propMgr.registerGroup("PreferenceRoot.Preference", true, true, true, null, true);
            this.m_regPreferences = true;
        }
    }
    
    public class UBProperties extends UbrokerPropertyManager
    {
        boolean m_checkVersion;
        boolean m_versionMatched;
        int m_numFileLoad;
        boolean hasEventBrkr;
        
        public UBProperties(final PropMgrUtils propMgrUtils) throws PropertyException {
            this(propMgrUtils, true);
        }
        
        public UBProperties(final boolean hasEventBrkr) throws PropertyException {
            super("ubroker.schema", hasEventBrkr ? ((EventBroker)AbstractGuiPlugin.getEventBroker()) : null);
            this.m_checkVersion = true;
            this.m_versionMatched = false;
            this.m_numFileLoad = 0;
            this.hasEventBrkr = false;
            this.hasEventBrkr = hasEventBrkr;
        }
        
        public boolean hasEventBrkr() {
            return this.hasEventBrkr;
        }
        
        public Hashtable getCategories(final String str) {
            final String[] arrayProperty = super.m_metaSchema.getArrayProperty("Group." + str + ".Categories");
            if (arrayProperty != null && arrayProperty.length > 0) {
                final Hashtable<String, Hashtable> hashtable = new Hashtable<String, Hashtable>();
                for (int i = 0; i < arrayProperty.length; ++i) {
                    final String key = arrayProperty[i];
                    hashtable.put(key, this.getCategoryAttributeHashtable(key));
                }
                return hashtable;
            }
            return new Hashtable();
        }
        
        public void setVersionCheck(final boolean checkVersion) {
            this.m_checkVersion = checkVersion;
        }
        
        public boolean versionMatched() {
            return !this.m_checkVersion || this.m_versionMatched;
        }
        
        protected boolean chkPropertyVersion(final String s) {
            ++this.m_numFileLoad;
            return this.m_versionMatched = true;
        }
    }
}
