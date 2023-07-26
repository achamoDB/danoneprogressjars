// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.events.EPMPWarmStartFinishedEvent;
import com.progress.ubroker.management.events.EPMPWarmStartBeganEvent;
import com.progress.common.property.ERenegadePropertyFileChange;
import com.progress.common.networkevents.IEventObject;
import com.progress.chimera.adminserver.EAdminserverBackOnline;
import com.progress.common.networkevents.EventListener;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.ubroker.util.PropGroupDescriptor;
import com.progress.ubroker.util.UBPreferenceProperties;
import java.util.Vector;
import com.progress.ubroker.util.CfgValidateErrs;
import com.progress.ubroker.util.PropertiesSaveDescriptor;
import com.progress.common.property.PropertyManager;
import java.rmi.Remote;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.common.log.ProLog;
import com.progress.ubroker.util.PropFilename;
import java.rmi.RemoteException;
import com.progress.chimera.adminserver.AdminServerType;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.util.Hashtable;
import com.progress.ubroker.util.IPropConst;

public class PropMgrPlugin extends AbstractUbrokerPlugin implements IBTMsgCodes, IPropConst
{
    static final String IDENTITY_STR = "UBPropMgrPlugin";
    static final int MAX_PMU_OBJECT_ID = 4;
    static final int DEFAULT_PMU_OBJECT_ID = 0;
    static int m_nextPMUid;
    static PropMgrPlugin m_myInstance;
    static Hashtable m_cnames;
    static Hashtable m_watchers;
    PMUObject[] m_pmuObjects;
    int m_myIndex;
    String[] m_args;
    boolean m_initIsDone;
    Object[] m_regUsers;
    private static String aCanonicalName;
    
    public static PropMgrPlugin getInstance() {
        return PropMgrPlugin.m_myInstance;
    }
    
    public PropMgrPlugin() {
        this.m_pmuObjects = null;
        this.m_myIndex = -1;
        this.m_args = null;
        this.m_initIsDone = false;
        this.m_regUsers = null;
    }
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        super.init(n, administrationServer, array);
        if (!LoadablePlatform.validate()) {
            UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015382284L, new Object[] { "UBPropMgrPlugin", LoadablePlatform.getInvalidOSStr() }));
            return false;
        }
        UBToolsMsg.logMsg(5, "in PropMgrPlugin.init()..");
        final boolean mfEnabled = AdminServerType.isMFEnabled();
        final boolean b = AdminServerType.getFathomInstallDir() != null;
        String s = "0";
        if (mfEnabled || b) {
            s = "1";
        }
        final String s2 = "ubroker.schema";
        String s3 = null;
        try {
            s3 = administrationServer.getUbPropFile();
        }
        catch (RemoteException ex) {}
        if (s3 == null) {
            s3 = System.getProperty("Ubroker.ConfigFile");
        }
        if (s3 == null && array.length > 0) {
            s3 = array[0];
        }
        if (s3 == null) {
            s3 = PropFilename.getFullPath();
            ProLog.log("PropMgrPlugin", 2, 7669630165411962937L, new Object[] { s3 });
        }
        else {
            ProLog.log("PropMgrPlugin", 2, 7669630165411962938L, new Object[] { s3 });
        }
        try {
            (this.m_pmuObjects = new PMUObject[5])[PropMgrPlugin.m_nextPMUid++] = new PMUObject(s3, s2, s);
            this.m_initIsDone = true;
            if (this.m_pmuObjects[0].getPMUInitStatus()) {
                PropMgrPlugin.m_myInstance = this;
            }
            UBToolsMsg.logMsg(5, "...PropMgrPlugin.init()..done");
            return true;
        }
        catch (Exception ex2) {
            return this.m_initIsDone = false;
        }
    }
    
    public PropMgrUtils getPropMgrUtils() {
        return this.m_pmuObjects[0].getPMUObject();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return null;
    }
    
    public void shutdown() {
    }
    
    public void addCanonicalName(final String key, final String value) {
        if (key == null || value == null) {
            return;
        }
        if (key.equals("") || value.equals("")) {
            return;
        }
        PropMgrPlugin.m_cnames.put(key, value);
    }
    
    public String getCanonicalName(final String key) {
        final String s = PropMgrPlugin.m_cnames.get(key);
        if (s == null) {
            return "";
        }
        return s;
    }
    
    static String getAvailableCanonicalName() {
        if (!PropMgrPlugin.aCanonicalName.trim().equals("")) {
            return PropMgrPlugin.aCanonicalName;
        }
        if (PropMgrPlugin.m_cnames.isEmpty()) {
            return "";
        }
        return PropMgrPlugin.aCanonicalName = PropMgrPlugin.m_cnames.elements().nextElement();
    }
    
    public BrokerAbnormalShutdownWatcher getWatcher(final String key, final String s, final String s2, final String s3) {
        if (key == null || key.equals("")) {
            return null;
        }
        if (!PropMgrPlugin.m_watchers.containsKey(key)) {
            return this.createWatcher(key, s, s2, s3);
        }
        final BrokerAbnormalShutdownWatcher brokerAbnormalShutdownWatcher = PropMgrPlugin.m_watchers.get(key);
        if (brokerAbnormalShutdownWatcher.isAlive()) {
            if (s2 != null && (!s2.equals("") || !s2.equals("0")) && !new Integer(brokerAbnormalShutdownWatcher.getWatchingPid()).toString().equals(s2)) {
                brokerAbnormalShutdownWatcher.setKeepWatchingFlag(false);
                brokerAbnormalShutdownWatcher.interrupt();
                PropMgrPlugin.m_watchers.remove(key);
                return this.createWatcher(key, s, s2, s3);
            }
            return brokerAbnormalShutdownWatcher;
        }
        else {
            if (brokerAbnormalShutdownWatcher.isWatching()) {
                PropMgrPlugin.m_watchers.remove(key);
                return this.createWatcher(key, s, s2, s3);
            }
            if (s2 != null && (!s2.equals("") || !s2.equals("0")) && !new Integer(brokerAbnormalShutdownWatcher.getWatchingPid()).toString().equals(s2)) {
                PropMgrPlugin.m_watchers.remove(key);
                return this.createWatcher(key, s, s2, s3);
            }
            return brokerAbnormalShutdownWatcher;
        }
    }
    
    private BrokerAbnormalShutdownWatcher createWatcher(final String key, final String s, final String s2, final String s3) {
        if (s2 != null && (!s2.equals("") || !s2.equals("0"))) {
            final BrokerAbnormalShutdownWatcher value = new BrokerAbnormalShutdownWatcher(key, s, s2, s3);
            PropMgrPlugin.m_watchers.put(key, value);
            return value;
        }
        return null;
    }
    
    public void saveProperties(final String s, final Hashtable hashtable) throws Exception {
        this.m_pmuObjects[0].m_pmuObject.saveProperties(s, hashtable);
    }
    
    public boolean isInitDone() {
        return this.m_initIsDone;
    }
    
    public void registerUser(final IPMUAccessCallback ipmuAccessCallback) {
        this.registerUser(ipmuAccessCallback, 0);
    }
    
    public void registerUser(final IPMUAccessCallback ipmuAccessCallback, final int n) {
        this.m_pmuObjects[n].registerUser(ipmuAccessCallback);
    }
    
    public int getPMUObjectForPropFile() {
        return 0;
    }
    
    public String getParentGroupValue(final String s) {
        return this.getParentGroupValue(0, s);
    }
    
    public String getParentGroupValue(final int n, final String s) {
        String parentGroupValue = null;
        try {
            parentGroupValue = this.m_pmuObjects[n].m_pmuObject.getParentGroupValue(s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "getParentGroupValue", ex.toString() }));
        }
        return parentGroupValue;
    }
    
    public String[] getSvcNameForParentGrp(final String s) {
        return this.getSvcNameForParentGrp(0, s);
    }
    
    public String[] getSvcNameForParentGrp(final int n, final String s) {
        String[] svcNameForParentGrp = null;
        try {
            svcNameForParentGrp = this.m_pmuObjects[n].m_pmuObject.getSvcNameForParentGrp(s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "getSvcNameForParentGrp", ex.toString() }));
        }
        return svcNameForParentGrp;
    }
    
    public char[] getPropertiesStream(final String s) {
        return this.getPropertiesStream(0, s);
    }
    
    public char[] getPropertiesStream(final int n, final String s) {
        char[] propertiesStream = null;
        try {
            propertiesStream = this.m_pmuObjects[n].m_pmuObject.getPropertiesStream(s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "getPropertiesStream", ex.toString() }));
        }
        return propertiesStream;
    }
    
    public Hashtable getConfigurationSchema(final String s, final String[] array) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[0].m_pmuObject;
        return PropMgrUtils.m_propMgr.getCategorySchemaHashtable(s, array);
    }
    
    public Hashtable getCategories(final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[0].m_pmuObject;
        return PropMgrUtils.m_propMgr.getCategories(s);
    }
    
    public PropertyManager.PropertyCollection getPropertyCollection(final String s) {
        return this.getPropertyCollection(0, s);
    }
    
    public PropertyManager.PropertyCollection getPropertyCollection(final int n, final String s) {
        try {
            return this.m_pmuObjects[n].m_pmuObject.getProperties(s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(4, UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "getPropertyCollection", ex.toString() }));
            return null;
        }
    }
    
    public boolean updatePropertyCollection(final String s, final char[] array) {
        return this.updatePropertyCollection(0, s, array);
    }
    
    public boolean updatePropertyCollection(final int n, final String s, final char[] array) {
        try {
            return this.m_pmuObjects[n].updateProperties(s, array);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "updatePropertyCollection", ex.toString() }));
            return false;
        }
    }
    
    public boolean saveSvcConfig(final char[] array, final String s) {
        return this.saveSvcConfig(0, array, s);
    }
    
    public boolean saveSvcConfig(final int n, final char[] array, final String s) {
        boolean saveConfig = false;
        try {
            saveConfig = this.m_pmuObjects[n].saveConfig(array, s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PropMgrPlugin", "saveSvcConfig", s, ex.toString() }));
        }
        return saveConfig;
    }
    
    public CfgValidateErrs validateProperties(final PropertiesSaveDescriptor propertiesSaveDescriptor) {
        return this.validateProperties(0, propertiesSaveDescriptor);
    }
    
    public CfgValidateErrs validateProperties(final int n, final PropertiesSaveDescriptor propertiesSaveDescriptor) {
        try {
            return this.m_pmuObjects[n].m_pmuObject.validateProperties(propertiesSaveDescriptor);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PropMgrPlugin", "validateProperties", propertiesSaveDescriptor.getPropGroupName(), ex.toString() }));
            return new CfgValidateErrs();
        }
    }
    
    public boolean delSvcConfig(final String s, final String s2) {
        return this.delSvcConfig(0, s, s2);
    }
    
    public boolean delSvcConfig(final int n, final String s, final String s2) {
        boolean removeConfig = false;
        try {
            removeConfig = this.m_pmuObjects[n].removeConfig(s, s2);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PropMgrPlugin", "delSvcConfig", s2, ex.toString() }));
        }
        return removeConfig;
    }
    
    public String getSvcName(final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[0].m_pmuObject;
        return PropMgrUtils.getSvcName(s);
    }
    
    public String getSvcName(final int n, final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[n].m_pmuObject;
        return PropMgrUtils.getSvcName(s);
    }
    
    public boolean reqUserName(final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[0].m_pmuObject;
        return PropMgrUtils.reqUserName(s);
    }
    
    public Vector getSvcInstances() {
        return this.m_pmuObjects[0].getSvcInstances();
    }
    
    public Vector getSvcInstances(final int n) {
        return this.m_pmuObjects[n].getSvcInstances();
    }
    
    public String getPersonStrFromPropPath(final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[0].m_pmuObject;
        return PropMgrUtils.getSvcTypeStr(s);
    }
    
    public String getPersonStrFromPropPath(final int n, final String s) {
        final PropMgrUtils pmuObject = this.m_pmuObjects[n].m_pmuObject;
        return PropMgrUtils.getSvcTypeStr(s);
    }
    
    public String getPropFilePath() {
        return this.m_pmuObjects[0].m_fullFileSpec;
    }
    
    public String getPropFilePath(final int n) {
        return this.m_pmuObjects[n].m_fullFileSpec;
    }
    
    public String[][] getCustomizedEnvVars(final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[0].m_pmuObject.getCustomizedEnvVars(collection);
    }
    
    public String[][] getCustomizedEnvVars(final int n, final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[n].m_pmuObject.getCustomizedEnvVars(collection);
    }
    
    public String[][] getCustomizedEnvVars(final PropertyManager.PropertyCollection collection, final boolean b) {
        return this.m_pmuObjects[0].m_pmuObject.getCustomizedEnvVars(collection, b);
    }
    
    public String[][] getCustomizedEnvVars(final int n, final PropertyManager.PropertyCollection collection, final boolean b) {
        return this.m_pmuObjects[n].m_pmuObject.getCustomizedEnvVars(collection, b);
    }
    
    public String getPropValueFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[0].m_pmuObject.getPropValueFromCollection(s, collection);
    }
    
    public String getPropValueFromCollection(final int n, final String s, final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[n].m_pmuObject.getPropValueFromCollection(s, collection);
    }
    
    public String getPropValueOrDefaultFromCollection(final String s, final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[0].m_pmuObject.getPropValueOrDefaultFromCollection(s, collection);
    }
    
    public String getPropValueOrDefaultFromCollection(final int n, final String s, final PropertyManager.PropertyCollection collection) {
        return this.m_pmuObjects[n].m_pmuObject.getPropValueOrDefaultFromCollection(s, collection);
    }
    
    public UBPreferenceProperties getPreferences() {
        return this.m_pmuObjects[0].m_pmuObject.getPreferences();
    }
    
    public UBPreferenceProperties getPreferences(final int n) {
        return this.m_pmuObjects[n].m_pmuObject.getPreferences();
    }
    
    public void refreshPreferences() {
        this.m_pmuObjects[0].m_pmuObject.refreshPreferences();
    }
    
    public void refreshPreferences(final int n) {
        this.m_pmuObjects[n].m_pmuObject.refreshPreferences();
    }
    
    public char[] getPreferenceStream() {
        return this.getPreferenceStream(0);
    }
    
    public char[] getPreferenceStream(final int n) {
        try {
            return this.m_pmuObjects[n].m_pmuObject.savePrefPropForRemote();
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "PropMgrPlugin", "getPreferenceStream", ex.toString() }));
            return null;
        }
    }
    
    public synchronized boolean putPreferenceStream(final char[] array) {
        return this.putPreferenceStream(0, array);
    }
    
    public synchronized boolean putPreferenceStream(final int n, final char[] array) {
        return this.m_pmuObjects[n].savePreferences(array);
    }
    
    public PropGroupDescriptor getUBPersonStrForSvcName(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.findUBPersonStrForSvcName(s);
    }
    
    public PropGroupDescriptor getUBPersonStrForSvcName(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.findUBPersonStrForSvcName(s);
    }
    
    public void updateSvcNameCache(final String s) {
        this.updateSvcNameCache(0, s);
    }
    
    public void updateSvcNameCache(final int n, final String s) {
        try {
            this.m_pmuObjects[n].updateSvcNameCache(s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PropMgrPlugin", "updateSvcNameCache", s, ex.toString() }));
        }
    }
    
    public String[] getPersonalities() throws PropMgrUtils.CantGetParentGroup, PropMgrUtils.CantGetPropCollection {
        try {
            return this.m_pmuObjects[0].m_pmuObject.getParentGroupValues();
        }
        catch (PropMgrUtils.CantGetParentGroup cantGetParentGroup) {}
        catch (Exception ex) {}
        return null;
    }
    
    public String[] getPersonalities(final int n) throws PropMgrUtils.CantGetParentGroup, PropMgrUtils.CantGetPropCollection {
        try {
            return this.m_pmuObjects[n].m_pmuObject.getParentGroupValues();
        }
        catch (PropMgrUtils.CantGetParentGroup cantGetParentGroup) {}
        catch (Exception ex) {}
        return null;
    }
    
    public boolean isNameServerPersonality(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.isNameServerPersonality(s);
    }
    
    public boolean isNameServerPersonality(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.isNameServerPersonality(s);
    }
    
    public String[] getNameServerInstances() {
        try {
            return this.m_pmuObjects[0].m_pmuObject.getNameServerInstances();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public String[] getAdminRoles() {
        try {
            return this.m_pmuObjects[0].m_pmuObject.getAdminRoles();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public String[] getNameServerInstances(final int n) {
        try {
            return this.m_pmuObjects[n].m_pmuObject.getNameServerInstances();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public String getUBWorkDir() {
        return this.m_pmuObjects[0].m_pmuObject.getUBWorkDir();
    }
    
    public String getUBWorkDir(final int n) {
        return this.m_pmuObjects[n].m_pmuObject.getUBWorkDir();
    }
    
    public String getExpandedUBWorkDir() {
        return this.m_pmuObjects[0].m_pmuObject.getExpandedUBWorkDir();
    }
    
    public String getExpandedUBWorkDir(final int n) {
        return this.m_pmuObjects[n].m_pmuObject.getExpandedUBWorkDir();
    }
    
    public String getExpandedPropertyValue(final String s, final String s2) {
        return this.m_pmuObjects[0].getExpandedPropertyValue(s, s2);
    }
    
    public String getExpandedPropertyValue(final int n, final String s, final String s2) {
        return this.m_pmuObjects[n].getExpandedPropertyValue(s, s2);
    }
    
    public String getExpandedPropertyValue(final String s, final String s2, final boolean b) {
        return this.m_pmuObjects[0].getExpandedPropertyValue(s, s2, b);
    }
    
    public String getExpandedPropertyValue(final int n, final String s, final String s2, final boolean b) {
        return this.m_pmuObjects[n].getExpandedPropertyValue(s, s2, b);
    }
    
    public String getNSLocation(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.getNSLocation(s);
    }
    
    public String getNSLocation(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.getNSLocation(s);
    }
    
    public String getAutoStartValue(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.getAutoStartValue(s);
    }
    
    public String getAutoStartValue(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.getAutoStartValue(s);
    }
    
    public PropMgrUtils.UBProperties getUBProperties() {
        PropMgrUtils.UBProperties ubProperties;
        try {
            ubProperties = (PropMgrUtils.UBProperties)this.getRPM();
        }
        catch (RemoteException ex) {
            ubProperties = null;
        }
        return ubProperties;
    }
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return this.m_pmuObjects[0].getRPM();
    }
    
    public IPropertyManagerRemote getRPM(final int n) throws RemoteException {
        return this.m_pmuObjects[n].getRPM();
    }
    
    public String[] getSchemaPropFilename() throws RemoteException {
        return this.m_pmuObjects[0].getSchemaPropFn();
    }
    
    public String[] getSchemaPropFilename(final int n) throws RemoteException {
        return this.m_pmuObjects[n].getSchemaPropFn();
    }
    
    public String getSchemaFilename() throws RemoteException {
        return this.m_pmuObjects[0].getSchemaFn();
    }
    
    public String getSchemaFilename(final int n) throws RemoteException {
        return this.m_pmuObjects[n].getSchemaFn();
    }
    
    public String getPropFilename() throws RemoteException {
        return this.m_pmuObjects[0].getPropFn();
    }
    
    public String getPropFilename(final int n) throws RemoteException {
        return this.m_pmuObjects[n].getPropFn();
    }
    
    public boolean uniqueSvcName(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.uniqueSvcName(s);
    }
    
    public boolean uniqueSvcName(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.uniqueSvcName(s);
    }
    
    public boolean handleAddNew(final String s, final String s2) {
        return this.m_pmuObjects[0].m_pmuObject.handleAddNew(s, s2);
    }
    
    public boolean handleAddNew(final int n, final String s, final String s2) {
        return this.m_pmuObjects[n].m_pmuObject.handleAddNew(s, s2);
    }
    
    public boolean handleNSAddNew(final String s, final String s2, final String s3) {
        return this.m_pmuObjects[0].m_pmuObject.handleNSAddNew(s, s2, s3);
    }
    
    public boolean handleAiaAddNew(final String s, final String s2) {
        return this.m_pmuObjects[0].m_pmuObject.handleAiaAddNew(s, s2);
    }
    
    public boolean handleNSAddNew(final int n, final String s, final String s2, final String s3) {
        return this.m_pmuObjects[n].m_pmuObject.handleNSAddNew(s, s2, s3);
    }
    
    public boolean handleWsaAddNew(final String s, final String s2, final String s3, final String s4) {
        return this.m_pmuObjects[0].m_pmuObject.handleWsaAddNew(s, s2, s3, s4);
    }
    
    public int getNSInstRefCnt(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.getNSInstRefCnt(s);
    }
    
    public int getNSInstRefCnt(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.getNSInstRefCnt(s);
    }
    
    public CfgValidateErrs validateOneProperty(final String s, final String s2, final String s3) {
        return this.m_pmuObjects[0].m_pmuObject.validateOneProperty(s, s2, s3);
    }
    
    public CfgValidateErrs validateOneProperty(final int n, final String s, final String s2, final String s3) {
        return this.m_pmuObjects[n].m_pmuObject.validateOneProperty(s, s2, s3);
    }
    
    public Hashtable getLogFNList(final String s) {
        return this.m_pmuObjects[0].m_pmuObject.getLogFNList(s);
    }
    
    public Hashtable getLogFNList(final int n, final String s) {
        return this.m_pmuObjects[n].m_pmuObject.getLogFNList(s);
    }
    
    public void warmStart(final int n) {
        this.m_pmuObjects[n].warmStart();
    }
    
    public void warmStart() {
        this.warmStart(0);
    }
    
    public boolean getWarmStartState() {
        return this.getWarmStartState(0);
    }
    
    public boolean getWarmStartState(final int n) {
        return this.m_pmuObjects[n].getWarmStartState();
    }
    
    static {
        PropMgrPlugin.m_nextPMUid = 0;
        PropMgrPlugin.m_myInstance = null;
        PropMgrPlugin.m_cnames = new Hashtable();
        PropMgrPlugin.m_watchers = new Hashtable();
        PropMgrPlugin.aCanonicalName = "";
    }
    
    class PMUObject
    {
        public String m_fullFileSpec;
        public String m_schemaFullFileSpec;
        public PropMgrUtils m_pmuObject;
        private Vector m_users;
        public IEventStream m_myEvtStream;
        public IEventInterestObject m_evtInterestObj;
        public boolean m_warmStartInProgress;
        
        public PMUObject(final PropMgrPlugin propMgrPlugin, final String s, final String s2, final String s3) {
            this(propMgrPlugin, s, s2);
            this.m_pmuObject.updateCollectStatsDefault(s3);
        }
        
        public PMUObject(final String fullFileSpec, final String schemaFullFileSpec) {
            this.m_fullFileSpec = null;
            this.m_schemaFullFileSpec = null;
            this.m_pmuObject = null;
            this.m_users = new Vector();
            this.m_myEvtStream = null;
            this.m_evtInterestObj = null;
            this.m_warmStartInProgress = false;
            this.m_users = new Vector();
            this.m_fullFileSpec = fullFileSpec;
            this.m_schemaFullFileSpec = schemaFullFileSpec;
            try {
                this.m_pmuObject = new PropMgrUtils(this.m_fullFileSpec, true);
                this.m_evtInterestObj = AbstractUbrokerPlugin.getEventBroker().expressInterest(ERenegadePropertyFileChange.class, this.makePropFileChangeListener(), this.getEventStream());
            }
            catch (Exception ex) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "Constrcutor", fullFileSpec, ex.toString() }));
            }
        }
        
        public PropMgrUtils getPMUObject() {
            return this.m_pmuObject;
        }
        
        public EventListener makePropFileChangeListener() {
            return new ExtPropFileChangeListener();
        }
        
        public synchronized boolean saveConfig(final char[] array, final String str) {
            try {
                this.m_pmuObject.updateRemoteGroupProp(array, "new", str);
                this.m_pmuObject.saveAll(this.m_fullFileSpec, "Update property for " + str);
                return true;
            }
            catch (Exception ex) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "saveConfig", str, ex.toString() }));
                return false;
            }
        }
        
        public synchronized boolean removeConfig(final String str, final String s) {
            try {
                this.m_pmuObject.removeGroup(str, s);
                this.m_pmuObject.saveAll(this.m_fullFileSpec, "After removing group " + s);
                try {
                    this.m_pmuObject.updateSvcNameCache(s);
                }
                catch (Exception ex) {
                    UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381997L, new Object[] { ex.toString() }));
                }
                return true;
            }
            catch (Exception ex2) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "removeConfig", s + "." + str, ex2.toString() }));
                return false;
            }
        }
        
        public synchronized boolean updateProperties(final String s, final char[] array) {
            try {
                this.m_pmuObject.removeGroup(s);
                return this.savePropStream(array, s);
            }
            catch (Exception ex) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "updateProperties", s, ex.toString() }));
                return false;
            }
        }
        
        public synchronized boolean savePreferences(final char[] array) {
            final boolean savePropStream = this.savePropStream(array, "PreferenceRoot.Preference");
            if (savePropStream) {
                this.m_pmuObject.fetchPreferences();
            }
            return savePropStream;
        }
        
        public synchronized void updateSvcNameCache(final String s) {
            try {
                this.m_pmuObject.updateSvcNameCache(s);
            }
            catch (Exception ex) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "updateSvcNameCache", s, ex.toString() }));
            }
        }
        
        public void registerUser(final IPMUAccessCallback obj) {
            this.m_users.addElement(obj);
        }
        
        public Vector getSvcInstances() {
            return this.m_pmuObject.getAllSvcInstances();
        }
        
        public String getExpandedPropertyValue(final String s, final String s2) {
            return this.m_pmuObject.getExpandedPropertyValue(s, s2);
        }
        
        public String getExpandedPropertyValue(final String s, final String s2, final boolean b) {
            return this.m_pmuObject.getExpandedPropertyValue(s, s2, b);
        }
        
        public IPropertyManagerRemote getRPM() throws RemoteException {
            try {
                final PropMgrUtils pmuObject = this.m_pmuObject;
                return PropMgrUtils.m_propMgr;
            }
            catch (Exception ex) {
                UBToolsMsg.logException("PropMgrPlugin.getRPM() failure: " + ex.toString());
                return null;
            }
        }
        
        public String[] getSchemaPropFn() throws RemoteException {
            return new String[] { this.m_schemaFullFileSpec, this.m_fullFileSpec };
        }
        
        public String getSchemaFn() throws RemoteException {
            return this.m_schemaFullFileSpec;
        }
        
        public String getPropFn() throws RemoteException {
            return this.m_fullFileSpec;
        }
        
        public boolean getPMUInitStatus() {
            return this.m_pmuObject != null;
        }
        
        public boolean handleAddNew(final String s, final String s2) {
            return this.m_pmuObject.handleAddNew(s, s2);
        }
        
        private void informPMUUserOnContextReset(final boolean b) {
            if (this.m_users.size() > 0) {
                for (int i = 0; i < this.m_users.size(); ++i) {
                    final IPMUAccessCallback ipmuAccessCallback = this.m_users.elementAt(i);
                    if (ipmuAccessCallback != null) {
                        ipmuAccessCallback.handlePMUContextReset(b);
                    }
                }
            }
        }
        
        private synchronized boolean savePropStream(final char[] array, final String str) {
            try {
                this.m_pmuObject.updateRemoteGroupProp(array, "new", str);
                this.m_pmuObject.saveAll(this.m_fullFileSpec, "Update property for " + str);
                return true;
            }
            catch (Exception ex) {
                UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "saveConfig", str, ex.toString() }));
                return false;
            }
        }
        
        public synchronized void warmStart() {
            final PropMgrPlugin instance = PropMgrPlugin.getInstance();
            if (this.m_warmStartInProgress) {
                return;
            }
            try {
                if (this.m_evtInterestObj != null) {
                    AbstractUbrokerPlugin.getEventBroker().revokeInterest(this.m_evtInterestObj);
                    this.m_evtInterestObj = null;
                }
            }
            catch (RemoteException ex) {
                UBToolsMsg.logError(5, "Error revoking interest of ERenegadePropertyFileChangeEvent:" + ex.getMessage());
            }
            if (instance != null) {
                AbstractUbrokerPlugin.setWarmStartInProgress(this.m_warmStartInProgress = true);
                this.informPMUUserOnContextReset(false);
                try {
                    final PropMgrUtils pmuObject = this.m_pmuObject;
                    PropMgrUtils.m_propMgr.resetPropertyManager();
                    final PropMgrUtils pmuObject2 = this.m_pmuObject;
                    PropMgrUtils.m_propMgr.loadSchema(this.m_schemaFullFileSpec);
                    this.m_pmuObject.loadPropFile(this.m_fullFileSpec, true);
                    this.m_evtInterestObj = AbstractUbrokerPlugin.getEventBroker().expressInterest(ERenegadePropertyFileChange.class, this.makePropFileChangeListener(), this.getEventStream());
                    AbstractGuiPlugin.unregAllRscLookedUp();
                    AbstractGuiPlugin.setSharedResourcesInited(false);
                    AbstractGuiPlugin.initSharedResources(PropMgrPluginFinder.get());
                    this.postWarmStartDoneEvent();
                    this.m_warmStartInProgress = false;
                }
                catch (Exception ex2) {
                    UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "PMUObject", "warmStart", this.m_fullFileSpec, ex2.toString() }));
                }
            }
        }
        
        public boolean getWarmStartState() {
            return this.m_warmStartInProgress;
        }
        
        public void postWarmStartDoneEvent() {
            try {
                AbstractUbrokerPlugin.setWarmStartInProgress(false);
                this.informPMUUserOnContextReset(true);
                AbstractGuiPlugin.getEventBroker().postEvent(new EAdminserverBackOnline(AbstractUbrokerPlugin.getIAdministrationServer()));
            }
            catch (Exception ex) {
                UBToolsMsg.logError(5, "Failed to post adminserverBackOnlin event.");
            }
        }
        
        public IEventStream getEventStream() throws RemoteException {
            if (this.m_myEvtStream == null) {
                try {
                    this.m_myEvtStream = AbstractUbrokerPlugin.getEventBroker().openEventStream("EventStream_For_UBPropMgrPlugin");
                }
                catch (Exception ex) {
                    UBToolsMsg.logError(5, "Failed to open event stream for UBPropMgrPlugin: " + ex.toString());
                }
            }
            return this.m_myEvtStream;
        }
    }
    
    class ExtPropFileChangeListener extends EventListener
    {
        PropMgrPlugin m_pmpInst;
        int m_pmuObjIndex;
        
        public ExtPropFileChangeListener() {
            this.m_pmpInst = null;
            this.m_pmuObjIndex = 0;
            this.m_pmpInst = PropMgrPlugin.getInstance();
        }
        
        public ExtPropFileChangeListener(final int pmuObjIndex) {
            this.m_pmpInst = null;
            this.m_pmuObjIndex = 0;
            this.m_pmpInst = PropMgrPlugin.getInstance();
            this.m_pmuObjIndex = pmuObjIndex;
        }
        
        public void processEvent(final IEventObject eventObject) throws RemoteException {
            if (!(eventObject instanceof ERenegadePropertyFileChange)) {
                return;
            }
            if (!(((ERenegadePropertyFileChange)eventObject).issuer() instanceof PropMgrUtils.UBProperties)) {
                return;
            }
            this.m_pmpInst = PropMgrPlugin.getInstance();
            if (this.m_pmpInst != null && !this.m_pmpInst.getWarmStartState(this.m_pmuObjIndex)) {
                UBToolsMsg.logMsg("Start doing partial warm start...");
                this.postWarmStartBeganEvent();
                this.m_pmpInst.warmStart(this.m_pmuObjIndex);
                this.postWarmStartFinishedEvent();
                UBToolsMsg.logMsg("Partial warm start has completed.");
            }
        }
        
        private void postWarmStartBeganEvent() {
            final String availableCanonicalName = PropMgrPlugin.getAvailableCanonicalName();
            if (availableCanonicalName.equals("")) {
                return;
            }
            try {
                AbstractGuiPlugin.getEventBroker().postEvent(new EPMPWarmStartBeganEvent(AbstractUbrokerPlugin.getIAdministrationServer(), availableCanonicalName));
                UBToolsMsg.logMsg(5, "Posted PMPWarmStartBegan event..");
            }
            catch (Exception ex) {
                UBToolsMsg.logError(5, "Failed to post PMPWarmStartBegan event.");
            }
        }
        
        private void postWarmStartFinishedEvent() {
            final String availableCanonicalName = PropMgrPlugin.getAvailableCanonicalName();
            if (availableCanonicalName.equals("")) {
                return;
            }
            try {
                AbstractGuiPlugin.getEventBroker().postEvent(new EPMPWarmStartFinishedEvent(AbstractUbrokerPlugin.getIAdministrationServer(), availableCanonicalName));
                UBToolsMsg.logMsg(5, "Posted PMPWarmStartFinished event..");
            }
            catch (Exception ex) {
                UBToolsMsg.logError(5, "Failed to post PMPWarmStartFinished event.");
            }
        }
    }
}
