// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.net.MalformedURLException;
import com.progress.common.util.PscURLParser;
import java.io.File;
import com.progress.common.util.Environment;
import com.progress.common.util.AppService;
import java.util.Enumeration;
import com.progress.common.property.PropertyManager;
import java.util.Vector;
import com.progress.ubroker.util.CfgValidateErrs;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.common.util.ICfgConst;
import com.progress.ubroker.util.IPropConst;

public class UBPropValidate implements IPropConst, IBTMsgCodes, ICfgConst
{
    private UBValidateObject m_propObj;
    private PropMgrUtils m_propMgr;
    private String m_curPropName;
    private String m_curPropVal;
    private String m_curFullSvcPath;
    private boolean m_isRemote;
    private boolean m_isNameServer;
    private boolean m_isWSA;
    private CfgValidateErrs m_theMessages;
    Vector m_propColls;
    ValidateFlags m_theFlags;
    private String[] m_propNames;
    private String[] m_propVals;
    private boolean m_foundHost;
    private boolean m_regModeIsHost;
    public static boolean DEBUG_TRACE;
    
    public UBPropValidate() {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        try {
            this.m_propMgr = new PropMgrUtils();
        }
        catch (Exception ex) {
            System.out.println(UBToolsMsg.getMsg(7094295313015381967L));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("UBPropValidate1");
                ex.printStackTrace();
            }
        }
    }
    
    public UBPropValidate(final PropMgrUtils propMgr) {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        this.m_propMgr = propMgr;
    }
    
    public UBPropValidate(final PropMgrUtils propMgr, final PropertyManager.PropertyCollection collection) {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        this.m_propMgr = propMgr;
    }
    
    public UBPropValidate(final PropMgrUtils propMgr, final UBValidateObject propObj) {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        this.m_propMgr = propMgr;
        this.m_propObj = propObj;
    }
    
    public UBPropValidate(final PropMgrUtils propMgr, final String curFullSvcPath, final String curPropName, final String curPropVal) {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        this.m_propMgr = propMgr;
        this.m_curFullSvcPath = curFullSvcPath;
        this.m_curPropName = curPropName;
        this.m_curPropVal = curPropVal;
        this.m_theMessages = new CfgValidateErrs();
    }
    
    public UBPropValidate(final PropMgrUtils propMgr, final String curFullSvcPath, final String[] propNames, final String[] propVals) {
        this.m_propObj = null;
        this.m_propMgr = null;
        this.m_curPropName = null;
        this.m_curPropVal = null;
        this.m_curFullSvcPath = null;
        this.m_isRemote = false;
        this.m_isNameServer = false;
        this.m_isWSA = false;
        this.m_theMessages = null;
        this.m_propColls = null;
        this.m_theFlags = null;
        this.m_propNames = null;
        this.m_propVals = null;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        this.m_propMgr = propMgr;
        this.m_curFullSvcPath = curFullSvcPath;
        this.m_propNames = propNames;
        this.m_propVals = propVals;
        this.m_theMessages = new CfgValidateErrs();
    }
    
    public String[] getValMessages() {
        return this.m_theMessages.getErrMsgList();
    }
    
    public CfgValidateErrs getValMessageObject() {
        return this.m_theMessages;
    }
    
    public int getValMessageCount() {
        return this.m_theMessages.size();
    }
    
    public String[] getEnvMessages() {
        return this.m_theMessages.getBadEnvVarMsgList();
    }
    
    public int getEnvMessagesCount() {
        return this.m_theMessages.envVarErrsSize();
    }
    
    public boolean isEnvMessageEmpty() {
        return this.m_theMessages.isEnvVarErrsEmpty();
    }
    
    public void valAllPropsInCollection(final UBValidateObject ubValidateObject) {
        final String str = "";
        final String str2 = "";
        final String fullSvcPath = ubValidateObject.m_fullSvcPath;
        String anObject = null;
        this.m_theMessages = new CfgValidateErrs();
        this.m_propColls = new Vector();
        this.m_theFlags = new ValidateFlags();
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        try {
            final PropMgrUtils propMgr = this.m_propMgr;
            PropMgrUtils.getSvcName(fullSvcPath);
            final Enumeration<String> elements = this.getAllServiceList().elements();
            final PropMgrUtils propMgr2 = this.m_propMgr;
            final String svcTypeStr = PropMgrUtils.getSvcTypeStr(fullSvcPath);
            if (svcTypeStr == "NS") {
                this.m_isNameServer = true;
                this.m_isRemote = this.isRemote(ubValidateObject);
                this.validateNSProps(ubValidateObject);
            }
            if (svcTypeStr == "WSA") {
                this.m_isWSA = true;
            }
            while (elements.hasMoreElements()) {
                try {
                    anObject = elements.nextElement();
                    if (fullSvcPath.equals(anObject)) {
                        continue;
                    }
                    final PropertyManager.PropertyCollection properties = this.m_propMgr.getProperties(anObject);
                    if (properties == null) {
                        System.out.println(UBToolsMsg.getMsg(7094295313015382255L, new Object[] { anObject, svcTypeStr }) + "\n");
                        return;
                    }
                    this.m_propColls.addElement(new UBValidateObject(properties, anObject));
                }
                catch (Exception ex2) {
                    System.out.println(UBToolsMsg.getMsg(7094295313015382255L, new Object[] { anObject, svcTypeStr }) + "\n");
                }
            }
            this.validateMinMaxSrvrInst(ubValidateObject);
            this.validateMinMaxClientPort(ubValidateObject);
            this.validateMinMaxSrvrPort(ubValidateObject);
            this.valEachProp(ubValidateObject);
            if (this.m_regModeIsHost && !this.m_foundHost) {
                final Object[] array = { str + " " + str2 };
                this.m_theMessages.add(str, UBToolsMsg.getMsg(7094295313015383097L));
            }
            this.valRequiredProps(svcTypeStr, fullSvcPath);
        }
        catch (Exception ex) {
            this.m_theMessages.add(str, UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("ValAllPropsInCollection");
                ex.printStackTrace();
            }
        }
        if (this.m_theMessages.isEmpty()) {
            this.m_theMessages.didValidation();
        }
    }
    
    public boolean validateProp() {
        UBCfgPropTable.getPropNum(this.m_curPropName);
        final boolean valOneProp = this.valOneProp(this.m_curFullSvcPath, this.m_curPropName, this.m_curPropVal);
        if (this.m_theMessages.isEmpty()) {
            this.m_theMessages.didValidation();
        }
        return valOneProp;
    }
    
    public boolean validateProp(final String s, final String s2, final String s3) {
        final boolean valOneProp = this.valOneProp(UBCfgPropTable.getPropNum(s2), s, s2, s3);
        if (this.m_theMessages.isEmpty()) {
            this.m_theMessages.didValidation();
        }
        return valOneProp;
    }
    
    public boolean validatePropList() {
        boolean b = true;
        this.m_foundHost = false;
        this.m_regModeIsHost = false;
        if (this.m_propNames.length == this.m_propVals.length && this.m_propNames.length != 0) {
            for (int i = 0; i < this.m_propNames.length; ++i) {
                UBCfgPropTable.getPropNum(this.m_propNames[i]);
                if (!this.valOneProp(this.m_curFullSvcPath, this.m_propNames[i], this.m_propVals[i])) {
                    b = false;
                }
            }
        }
        if (this.m_theMessages.isEmpty()) {
            this.m_theMessages.didValidation();
        }
        return b;
    }
    
    private boolean valEachProp(final UBValidateObject ubValidateObject) {
        boolean valOneProp = true;
        final PropertyManager.PropertyCollection propColl = ubValidateObject.m_propColl;
        while (propColl.hasMoreElements()) {
            try {
                final Object nextElement = propColl.nextElement();
                final String name = ((PropertyManager.Property)nextElement).getName();
                final String value = ((PropertyManager.Property)nextElement).getValue();
                final long propNum = UBCfgPropTable.getPropNum(name);
                final String fullSvcPath = ubValidateObject.m_fullSvcPath;
                if (UBPropValidate.DEBUG_TRACE) {
                    System.out.println("In ValEachProp, property name is " + name);
                }
                if (value == null || value.equals("")) {
                    continue;
                }
                valOneProp = this.valOneProp(propNum, fullSvcPath, name, value);
            }
            catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        ubValidateObject.m_propColl.reset();
        return valOneProp;
    }
    
    private boolean valOneProp(final long lng, final String s, final String s2, final String s3) {
        boolean b = true;
        final UBCfgDataServer ubCfgDataServer = new UBCfgDataServer();
        try {
            final PropMgrUtils propMgr = this.m_propMgr;
            PropMgrUtils.getSvcName(s);
            final PropMgrUtils propMgr2 = this.m_propMgr;
            final String svcTypeStr = PropMgrUtils.getSvcTypeStr(s);
            switch ((int)lng) {
                case 10:
                case 830: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 20: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("APPLICATIONURL, num is " + lng);
                        break;
                    }
                    break;
                }
                case 30: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 40: {
                    final String verifyAppServiceList;
                    if ((verifyAppServiceList = this.verifyAppServiceList(s3)) != null) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382343L, new Object[] { s2, verifyAppServiceList }));
                        break;
                    }
                    break;
                }
                case 50: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 60: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 70: {
                    if (!this.verifyFileName(s3, s2, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381958L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    if (!this.m_isRemote && !this.validateMultiValues(s2, s3, false, false, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381966L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 80: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("CLASSMAIN, num is " + lng);
                        break;
                    }
                    break;
                }
                case 90: {
                    if (!this.verifySwitches(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 100: {
                    if (!this.validateControllingNameServer(s3, s2)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381960L, new Object[] { s3 }));
                        break;
                    }
                    break;
                }
                case 110: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 120: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("DEFAULTCOOKIEDOMAIN, num is " + lng);
                        break;
                    }
                    break;
                }
                case 130: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("DEFAULTCOOKIEPATH, num is " + lng);
                        break;
                    }
                    break;
                }
                case 140: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 150: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("DESCRIPTION, num is " + lng);
                        break;
                    }
                    break;
                }
                case 160: {
                    if (s.indexOf("Messengers") != -1 || s3.equals("")) {
                        break;
                    }
                    if (!this.validateEnvironmentReference(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381962L, new Object[] { s }));
                        b = false;
                        break;
                    }
                    if (svcTypeStr.equals("OD")) {
                        b = this.validateORODEnvironmentGroup(s3, s, svcTypeStr);
                        break;
                    }
                    if (svcTypeStr.equals("OR")) {
                        b = this.validateORODEnvironmentGroup(s3, s, svcTypeStr);
                        break;
                    }
                    if (!this.validateEnvironmentGroup(s3, s)) {
                        b = false;
                        break;
                    }
                    break;
                }
                case 170: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("GROUPNAME, num is " + lng);
                        break;
                    }
                    break;
                }
                case 180: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("HOSTNAME, num is " + lng + " " + s3);
                    }
                    this.m_foundHost = true;
                    break;
                }
                case 181: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 182:
                case 190: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 200: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 210: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 220: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("LOCATION, num is " + lng);
                        break;
                    }
                    break;
                }
                case 230: {
                    this.m_theFlags.setFlag(3);
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 231: {
                    this.m_theFlags.setFlag(4);
                    if (!this.verifyFileName(s3, s2, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381958L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    if (!this.m_isRemote && !this.validateMultiValues(s2, s3, false, false, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381966L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 240: {
                    break;
                }
                case 250: {
                    this.m_theFlags.setFlag(2);
                    if (this.m_isWSA && !this.verifyWSALogLevel(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                    }
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("LOGGINGLEVEL, num is " + lng);
                        break;
                    }
                    break;
                }
                case 251: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyPortValue(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383178L, new Object[] { s2, new Integer(1024).toString(), new Integer(65535).toString() }));
                        break;
                    }
                    break;
                }
                case 260: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 261: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyPortValue(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383178L, new Object[] { s2, new Integer(1024).toString(), new Integer(65535).toString() }));
                        break;
                    }
                    break;
                }
                case 270: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 280: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("MSNGREXECFILE, num is " + lng);
                        break;
                    }
                    break;
                }
                case 290: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("MSNGRSCRIPTFILE, num is " + lng);
                        break;
                    }
                    break;
                }
                case 300: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("MULTICASTGROUP, num is " + lng);
                        break;
                    }
                    break;
                }
                case 310: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("MULTICASTTTL, num is " + lng);
                        break;
                    }
                    break;
                }
                case 320: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 330: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("NEIGHBORNAMESERVERS, num is " + lng);
                        break;
                    }
                    break;
                }
                case 331: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyValWithMax(s3, 500)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383188L, new Object[] { s3, new Integer(500).toString(), s2 }));
                        break;
                    }
                    break;
                }
                case 332: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyValWithMax(s3, 10000)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383188L, new Object[] { s3, new Integer(10000).toString(), s2 }));
                        break;
                    }
                    break;
                }
                case 340: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 350: {
                    if (svcTypeStr.equals("OD") || svcTypeStr.equals("OR")) {
                        if (!s3.equals("State-aware")) {
                            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382933L, new Object[] { s, "State-aware" }));
                            break;
                        }
                        break;
                    }
                    else {
                        if (!this.verifyValList(s3, lng)) {
                            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                            break;
                        }
                        break;
                    }
                    break;
                }
                case 360: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 365: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("PASSWORD, num is " + lng);
                        break;
                    }
                    break;
                }
                case 370: {
                    this.m_theFlags.setFlag(0);
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    if (!this.verifyPortValue(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383178L, new Object[] { s2, new Integer(1024).toString(), new Integer(65535).toString() }));
                        break;
                    }
                    if (!this.m_isRemote) {
                        boolean b2;
                        if (this.m_isNameServer) {
                            b2 = this.validateMultiValues(s2, s3, "NS");
                        }
                        else {
                            b2 = this.validateMultiValues(s2, s3, true, false, s);
                        }
                        if (!b2) {
                            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381963L, new Object[] { s3 }));
                            b = false;
                        }
                        break;
                    }
                    break;
                }
                case 380: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("PRIORITYWEIGHT, num is " + lng);
                        break;
                    }
                    break;
                }
                case 390: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("PROPATH, num is " + lng);
                        break;
                    }
                    break;
                }
                case 391: {
                    if (s3.equals("Register-HostName")) {
                        this.m_regModeIsHost = true;
                    }
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 400: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 410: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("ROOTPATH, num is " + lng);
                        break;
                    }
                    break;
                }
                case 420: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("SCRIPTPATH, num is " + lng);
                        break;
                    }
                    break;
                }
                case 421: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 430: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 440: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 450: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 460: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 470: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 480: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 490: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("SRVRECEXFILE, num is " + lng);
                        break;
                    }
                    break;
                }
                case 500: {
                    if (!this.verifyFileName(s3, s2, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381958L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    if (!this.m_isRemote && !this.validateMultiValues(s2, s3, false, false, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381966L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 510: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyPortValue(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383178L, new Object[] { s2, new Integer(1024).toString(), new Integer(65535).toString() }));
                        break;
                    }
                    break;
                }
                case 520: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                    }
                    if (!this.verifyPortValue(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383178L, new Object[] { s2, new Integer(1024).toString(), new Integer(65535).toString() }));
                        break;
                    }
                    break;
                }
                case 530: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 540: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("SRVRSTARTUPPARAM, num is " + lng);
                    }
                    if (svcTypeStr.equals("OD") || svcTypeStr.equals("OR") || svcTypeStr.equals("MS")) {
                        this.m_theFlags.setFlag(1);
                        final String validateORODSrvrStartupParam = ubCfgDataServer.validateORODSrvrStartupParam(s3);
                        if (validateORODSrvrStartupParam != null) {
                            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382931L, new Object[] { s, validateORODSrvrStartupParam }));
                        }
                        final String verifyAllSrvrStartupParams;
                        if ((verifyAllSrvrStartupParams = ubCfgDataServer.verifyAllSrvrStartupParams()) != null) {
                            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015383067L, new Object[] { s, verifyAllSrvrStartupParams }));
                        }
                        break;
                    }
                    break;
                }
                case 550: {
                    if (!this.verifyProcName(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382340L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 560: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("SRVRSTARTUPPROCPARAM, num is " + lng);
                        break;
                    }
                    break;
                }
                case 570: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("USERNAME, num is " + lng);
                        break;
                    }
                    break;
                }
                case 580: {
                    if (!this.verifyUUID(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    if (!this.m_isRemote && !this.validateMultiValues(s2, s3, false, false, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381965L, new Object[] { s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 590: {
                    if (!this.verifyValList(s3, lng)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        break;
                    }
                    break;
                }
                case 600: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("WORKDIR, num is " + lng);
                        break;
                    }
                    break;
                }
                case 610: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("WSROOT, num is " + lng);
                        break;
                    }
                    break;
                }
                case 630: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 700: {
                    this.m_theFlags.setFlag(7);
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 710: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 740: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 750: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 760: {
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 780: {
                    this.m_theFlags.setFlag(5);
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 790: {
                    this.m_theFlags.setFlag(6);
                    if (!this.verifyBoolean(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382335L, new Object[] { s2 + " " + s3 }));
                        break;
                    }
                    break;
                }
                case 800: {
                    if (!this.verifyNumeric(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                case 620: {
                    this.m_theFlags.setFlag(8);
                    if (!this.verifyURL(s3)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382341L, new Object[] { s2, s3 }));
                        b = false;
                        break;
                    }
                    break;
                }
                default: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("Property " + s2 + " not found");
                        break;
                    }
                    break;
                }
            }
        }
        catch (Exception ex) {
            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("ValOneProp:");
                ex.printStackTrace();
            }
            b = false;
        }
        return b;
    }
    
    private boolean valOneProp(final String s, final String s2, final String str) {
        boolean validateMultiValues = true;
        final long propNum = UBCfgPropTable.getPropNum(s2);
        final boolean remote = this.isRemote(s);
        try {
            switch ((int)propNum) {
                case 70: {
                    if (!this.verifyFileName(str, s2, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381958L, new Object[] { str }), 7094295313015381958L);
                        validateMultiValues = false;
                        break;
                    }
                    if (!remote && !this.validateMultiValues(s2, str, false, true, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381966L, new Object[] { str }), 7094295313015381966L);
                        validateMultiValues = false;
                        break;
                    }
                    break;
                }
                case 370: {
                    if (!this.verifyNumeric(str)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015382338L, new Object[] { s2 + " " + str }), 7094295313015382338L);
                        validateMultiValues = false;
                        break;
                    }
                    if (remote) {
                        break;
                    }
                    validateMultiValues = this.validateMultiValues(s2, str, true, true, s);
                    if (!validateMultiValues) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381963L, new Object[] { str }), 7094295313015381963L);
                        break;
                    }
                    break;
                }
                case 390: {
                    break;
                }
                case 500: {
                    if (!this.verifyFileName(str, s2, s)) {
                        validateMultiValues = false;
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381958L, new Object[] { str }), 7094295313015381958L);
                        break;
                    }
                    if (!this.m_isRemote && !this.validateMultiValues(s2, str, false, true, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381966L, new Object[] { str }), 7094295313015381966L);
                        validateMultiValues = false;
                        break;
                    }
                    break;
                }
                case 600: {
                    if (!this.verifyDirectoryName(str, s2, s)) {
                        this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381959L, new Object[] { str }), 7094295313015381959L);
                        validateMultiValues = false;
                        break;
                    }
                    break;
                }
                default: {
                    if (UBPropValidate.DEBUG_TRACE) {
                        System.out.println("Property " + s2 + " not found");
                        break;
                    }
                    break;
                }
            }
        }
        catch (Exception ex) {
            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("ValOneProp 2");
                ex.printStackTrace();
            }
            validateMultiValues = false;
        }
        return validateMultiValues;
    }
    
    private Vector getAllServiceList() {
        return this.m_propMgr.getAllSvcInstances();
    }
    
    private Vector getAllServiceList(final String s) {
        Vector<String> vector = null;
        if (s.equals("NS")) {
            final String[] nsServices = this.m_propMgr.getNSServices();
            if (nsServices != null) {
                vector = new Vector<String>();
                for (int i = 0; i < nsServices.length; ++i) {
                    vector.addElement(nsServices[i]);
                }
            }
        }
        else {
            final String[] asServices = this.m_propMgr.getASServices();
            final String[] wsServices = this.m_propMgr.getWSServices();
            final String[] orServices = this.m_propMgr.getORServices();
            final String[] odServices = this.m_propMgr.getODServices();
            final String[] adapterServices = this.m_propMgr.getAdapterServices();
            final String[] aiaServices = this.m_propMgr.getAiaServices();
            final String[] wsaServices = this.m_propMgr.getWsaServices();
            vector = new Vector<String>();
            if (asServices != null) {
                for (int j = 0; j < asServices.length; ++j) {
                    vector.addElement(asServices[j]);
                }
            }
            if (wsServices != null) {
                for (int k = 0; k < wsServices.length; ++k) {
                    vector.addElement(wsServices[k]);
                }
            }
            if (orServices != null) {
                for (int l = 0; l < orServices.length; ++l) {
                    vector.addElement(orServices[l]);
                }
            }
            if (odServices != null) {
                for (int n = 0; n < odServices.length; ++n) {
                    vector.addElement(odServices[n]);
                }
            }
            if (adapterServices != null) {
                for (int n2 = 0; n2 < adapterServices.length; ++n2) {
                    vector.addElement(adapterServices[n2]);
                }
            }
            if (aiaServices != null) {
                for (int n3 = 0; n3 < aiaServices.length; ++n3) {
                    vector.addElement(aiaServices[n3]);
                }
            }
            if (wsaServices != null) {
                for (int n4 = 0; n4 < wsaServices.length; ++n4) {
                    vector.addElement(wsaServices[n4]);
                }
            }
        }
        return vector;
    }
    
    private String[] getServiceList(final String s) {
        String[] array = null;
        if (s.equals("AS")) {
            array = this.m_propMgr.getASServices();
        }
        else if (s.equals("WS")) {
            array = this.m_propMgr.getWSServices();
        }
        else if (s.equals("NS")) {
            array = this.m_propMgr.getNSServices();
        }
        else if (s.equals("OD")) {
            array = this.m_propMgr.getODServices();
        }
        else if (s.equals("OR")) {
            array = this.m_propMgr.getORServices();
        }
        else if (s.equals("AD")) {
            array = this.m_propMgr.getAdapterServices();
        }
        else if (s.equals("AIA")) {
            array = this.m_propMgr.getAiaServices();
        }
        return array;
    }
    
    private boolean isNameServer(final String s) {
        boolean b = false;
        if (s.indexOf("NS") >= 0) {
            b = true;
        }
        return b;
    }
    
    private boolean isRemote(final String str) {
        boolean b = false;
        final String string = str + ".location";
        if (str.indexOf("NS") < 0) {
            if (str.indexOf("NameServer") < 0) {
                return b;
            }
        }
        try {
            final String fullPathPropertyValue;
            if ((fullPathPropertyValue = this.m_propMgr.getFullPathPropertyValue(string)) != null && fullPathPropertyValue.toUpperCase().equals("REMOTE")) {
                b = true;
            }
        }
        catch (PropMgrUtils.CantGetPropertyValue cantGetPropertyValue) {
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("Error determining remote Nameserver: " + cantGetPropertyValue.toString());
            }
            b = false;
        }
        return b;
    }
    
    private Vector getValueList(final String s, final boolean b) {
        final Vector<String> vector = new Vector<String>();
        final Enumeration<UBValidateObject> elements = this.m_propColls.elements();
        while (elements.hasMoreElements()) {
            final UBValidateObject ubValidateObject = elements.nextElement();
            final PropMgrUtils propMgr = this.m_propMgr;
            final String svcTypeStr = PropMgrUtils.getSvcTypeStr(ubValidateObject.m_fullSvcPath);
            final String[] valueFromPropColl;
            if ((!b || (svcTypeStr != null && !svcTypeStr.equals("NS"))) && (valueFromPropColl = this.getValueFromPropColl(ubValidateObject, s)) != null) {
                final String s2 = valueFromPropColl[0];
                vector.addElement(valueFromPropColl[1]);
                if (!UBPropValidate.DEBUG_TRACE || valueFromPropColl[1] == null || valueFromPropColl[0] == null) {
                    continue;
                }
                System.out.println("adding value " + valueFromPropColl[1]);
            }
        }
        return vector;
    }
    
    private Vector getValueList(final String s, final String anObject) {
        final Vector<String> vector = new Vector<String>();
        final Enumeration<UBValidateObject> elements = this.m_propColls.elements();
        while (elements.hasMoreElements()) {
            final UBValidateObject ubValidateObject = elements.nextElement();
            final PropMgrUtils propMgr = this.m_propMgr;
            final String svcTypeStr = PropMgrUtils.getSvcTypeStr(ubValidateObject.m_fullSvcPath);
            final String[] valueFromPropColl;
            if (svcTypeStr != null && svcTypeStr.equals(anObject) && (valueFromPropColl = this.getValueFromPropColl(ubValidateObject, s)) != null) {
                final String s2 = valueFromPropColl[0];
                vector.addElement(valueFromPropColl[1]);
                if (!UBPropValidate.DEBUG_TRACE || valueFromPropColl[1] == null || valueFromPropColl[0] == null) {
                    continue;
                }
                System.out.println("Personality one: adding value " + valueFromPropColl[1]);
            }
        }
        return vector;
    }
    
    private Vector getValueListNoPropColls(final String str, final boolean b, final Vector vector, final String s, final boolean b2) {
        final Vector<String> vector2 = new Vector<String>();
        final Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            final String str2 = elements.nextElement();
            final PropMgrUtils propMgr = this.m_propMgr;
            final String svcTypeStr = PropMgrUtils.getSvcTypeStr(str2);
            final String substring = str2.substring(str2.lastIndexOf(".") + 1);
            final String string = str2 + "." + str;
            boolean remote = false;
            if (b2 && b && svcTypeStr.equals("NS")) {
                remote = this.isRemote(str2);
            }
            try {
                final String fullPathPropertyValue;
                if (s.equals(substring) || (fullPathPropertyValue = this.m_propMgr.getFullPathPropertyValue(string)) == null) {
                    continue;
                }
                if (b2 && svcTypeStr.equals("NS") && b && !remote) {
                    vector2.addElement(fullPathPropertyValue);
                }
                else if (b && !svcTypeStr.equals("NS")) {
                    vector2.addElement(fullPathPropertyValue);
                }
                else {
                    if (b) {
                        continue;
                    }
                    vector2.addElement(fullPathPropertyValue);
                }
            }
            catch (PropMgrUtils.CantGetPropertyValue cantGetPropertyValue) {
                this.m_theMessages.add(str, UBToolsMsg.getMsg(7094295313015382257L, new Object[] { cantGetPropertyValue.toString() }));
            }
        }
        return vector2;
    }
    
    private Vector getInstanceList(final String anObject, final String s, final boolean b) {
        Vector<String> allServicesButNS = null;
        Vector<String> vector = null;
        if (this.m_propMgr != null && b) {
            if (s == "NS") {
                final String[] nsServices = this.m_propMgr.getNSServices();
                if (nsServices != null) {
                    allServicesButNS = new Vector<String>();
                    for (int i = 0; i < nsServices.length; ++i) {
                        allServicesButNS.addElement(nsServices[i]);
                    }
                }
            }
            else {
                allServicesButNS = (Vector<String>)this.getAllServicesButNS();
            }
            if (allServicesButNS != null) {
                vector = new Vector<String>();
                final Enumeration<String> elements = vector.elements();
                while (elements.hasMoreElements()) {
                    final String nextElement = elements.nextElement();
                    if (!nextElement.equals(anObject)) {
                        vector.addElement(nextElement);
                    }
                }
            }
        }
        return vector;
    }
    
    private Vector getAllServicesButNS() {
        Vector<Object> vector = null;
        final Object o = null;
        this.m_propMgr.getAllSvcInstances();
        if (o != null) {
            vector = new Vector<Object>();
            for (int i = 0; i < ((__Null)o).length; ++i) {
                if (o[i].indexOf("NS") < 0) {
                    vector.addElement(o[i]);
                }
            }
        }
        return vector;
    }
    
    protected boolean isRemote(final UBValidateObject ubValidateObject) {
        boolean b = false;
        final PropertyManager.PropertyCollection propColl = ubValidateObject.m_propColl;
        while (propColl.hasMoreElements()) {
            try {
                final Object nextElement = propColl.nextElement();
                final String name = ((PropertyManager.Property)nextElement).getName();
                final String value = ((PropertyManager.Property)nextElement).getValue();
                if (!name.equals("location") || !value.equals("remote")) {
                    continue;
                }
                b = true;
            }
            catch (Exception ex) {
                System.out.println("error testing for remote" + ex.toString());
            }
        }
        ubValidateObject.m_propColl.reset();
        return b;
    }
    
    private Vector getNameServerList() {
        final Vector<String> vector = new Vector<String>();
        final String[] nsServices = this.m_propMgr.getNSServices();
        for (int i = 0; i < nsServices.length; ++i) {
            final PropMgrUtils propMgr = this.m_propMgr;
            PropMgrUtils.getSvcName(nsServices[i]);
            final Vector<String> vector2 = vector;
            final PropMgrUtils propMgr2 = this.m_propMgr;
            vector2.addElement(PropMgrUtils.getSvcName(nsServices[i]));
        }
        return vector;
    }
    
    private boolean isOnWindows(final String s) {
        return s.indexOf("Windows") >= 0;
    }
    
    private String getOsName() {
        return System.getProperty("os.name");
    }
    
    private boolean isExpandableName(final String s) {
        boolean b = false;
        if (s.indexOf(64) != -1 || s.indexOf(36) != -1) {
            b = true;
        }
        return b;
    }
    
    private String[] getValueFromPropColl(final UBValidateObject ubValidateObject, final String s) {
        String name = null;
        String value = null;
        String[] array = null;
        boolean b = false;
        final PropertyManager.Property value2 = ubValidateObject.m_propColl.get(s);
        if (value2 != null) {
            name = value2.getName();
            value = value2.getValue();
        }
        final PropertyManager.Property value3 = ubValidateObject.m_propColl.get("location");
        if (value3 != null && value3.getValue().equals("remote")) {
            b = true;
        }
        if (name != null && !b && value != null) {
            array = new String[] { name, value };
        }
        return array;
    }
    
    private Vector getValuesFromPropFile(final String[] array, final String s) {
        final Vector vector = new Vector();
        final String s2 = null;
        final String s3 = null;
        final boolean b = false;
        if (s2 != null && !b && s3 != null) {
            final String[] array2 = { s2, s3 };
        }
        return vector;
    }
    
    private String getExpandedFileName(final String s, final String s2, final String s3) {
        final String expandedPropertyValue = this.m_propMgr.getExpandedPropertyValue(s2, s3);
        if (expandedPropertyValue == null) {
            this.m_theMessages.add(s2, UBToolsMsg.getMsg(7094295313015381964L, new Object[] { s2 }));
        }
        return expandedPropertyValue;
    }
    
    private boolean verifyBoolean(final String s) {
        final Integer n = new Integer(s);
        boolean b = false;
        if (n == 0 || n == 1) {
            b = true;
        }
        return b;
    }
    
    private boolean verifySwitches(final String s) {
        boolean b = true;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) != '1' && s.charAt(i) != '0') {
                b = false;
            }
        }
        return b;
    }
    
    private boolean verifyNumeric(final String s) {
        boolean b = true;
        if (!s.equals("")) {
            try {
                final Integer n = new Integer(s);
            }
            catch (NumberFormatException ex) {
                b = false;
            }
        }
        return b;
    }
    
    private boolean verifyWSALogLevel(final String s) {
        boolean b = true;
        if (!s.equals("")) {
            try {
                final Integer n = new Integer(s);
                if (n < 0 || n > 7) {
                    b = false;
                }
            }
            catch (NumberFormatException ex) {
                b = false;
            }
        }
        return b;
    }
    
    private boolean verifyValWithMax(final String s, final int n) {
        boolean b = true;
        if (!s.equals("")) {
            try {
                if (new Integer(s) > n) {
                    b = false;
                }
            }
            catch (NumberFormatException ex) {
                b = false;
            }
        }
        return b;
    }
    
    private boolean verifyPortValue(final String s) {
        boolean b = true;
        try {
            final int intValue = new Integer(s);
            if (intValue != 0 && (intValue < 1024 || intValue > 65535)) {
                b = false;
            }
        }
        catch (NumberFormatException ex) {
            b = false;
        }
        return b;
    }
    
    private boolean verifyValList(final String s, final long n) {
        boolean b = false;
        try {
            final String[] propValList = UBCfgPropTable.getPropValList(n);
            if (propValList != null) {
                for (int i = 0; i < propValList.length; ++i) {
                    if (s.equals(propValList[i])) {
                        b = true;
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {
            b = false;
        }
        return b;
    }
    
    private String verifyAppServiceList(final String s) {
        int n = 1;
        String trim = null;
        String substring = s;
        try {
            while (substring != null && !substring.equals("")) {
                final int lastIndex;
                if ((lastIndex = substring.lastIndexOf(44)) > 0) {
                    trim = substring.substring(lastIndex + 1).trim();
                    substring = substring.substring(0, lastIndex);
                }
                else {
                    trim = substring;
                    substring = null;
                }
                if (!AppService.validateName(trim)) {
                    n = 0;
                    break;
                }
            }
        }
        catch (Exception ex) {
            n = 0;
        }
        if (n == 1) {
            trim = null;
        }
        return trim;
    }
    
    private boolean verifyUUID(final String s) {
        return true;
    }
    
    private boolean verifyProcName(final String s) {
        return true;
    }
    
    private boolean verifyFileName(final String s, final String s2, final String s3) {
        boolean b = true;
        final String property = System.getProperty("file.separator");
        final String property2 = System.getProperty("path.separator");
        String expandPropertyValueJNI;
        if (this.isExpandableName(s)) {
            expandPropertyValueJNI = new Environment().expandPropertyValueJNI(s);
        }
        else {
            expandPropertyValueJNI = s;
        }
        if (expandPropertyValueJNI != null) {
            try {
                final File file = new File(expandPropertyValueJNI);
                if (expandPropertyValueJNI.indexOf(property2) == -1) {
                    final int lastIndex = expandPropertyValueJNI.lastIndexOf(property);
                    String substring = null;
                    if (lastIndex != -1 && lastIndex != expandPropertyValueJNI.length() - 1) {
                        substring = expandPropertyValueJNI.substring(0, lastIndex);
                    }
                    try {
                        if (!new File(substring).isDirectory()) {
                            b = false;
                        }
                    }
                    catch (Exception ex) {
                        b = false;
                    }
                }
            }
            catch (Exception ex2) {
                b = false;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    private boolean verifyDirectoryName(final String str, final String s, final String s2) {
        boolean b = false;
        String expandPropertyValueJNI;
        if (this.isExpandableName(str)) {
            expandPropertyValueJNI = new Environment().expandPropertyValueJNI(str);
        }
        else {
            expandPropertyValueJNI = str;
        }
        if (expandPropertyValueJNI != null && expandPropertyValueJNI.indexOf(File.pathSeparatorChar) == -1) {
            try {
                if (new File(expandPropertyValueJNI).isDirectory()) {
                    b = true;
                }
            }
            catch (Exception ex) {
                System.out.println(">>>>>>> VALIDATE:  Filename " + str + " is not valid\n");
                this.m_theMessages.add(s, UBToolsMsg.getMsg(7094295313015381959L, new Object[] { str }));
                b = false;
            }
        }
        return b;
    }
    
    private boolean verifyURL(final String s) {
        boolean b = true;
        try {
            new PscURLParser(s).getURL();
        }
        catch (MalformedURLException ex) {
            b = false;
        }
        return b;
    }
    
    private boolean validateControllingNameServer(final String str, final String s) {
        final Vector nameServerList = this.getNameServerList();
        boolean b = false;
        if (UBPropValidate.DEBUG_TRACE) {
            System.out.println(">>>>>>> DEBUG:  found a nameserver ref and its: @" + str + "@");
        }
        if (nameServerList != null) {
            final Enumeration<String> elements = nameServerList.elements();
            while (elements.hasMoreElements()) {
                if (str.equals(elements.nextElement())) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    private boolean validateEnvironmentGroup(final String s, final String s2) {
        boolean b = true;
        final String s3 = "EnvNumError";
        final UBCfgDataServer ubCfgDataServer = new UBCfgDataServer();
        if (!this.m_propMgr.EnvPairsCountValid(s2)) {
            this.m_theMessages.addEnvVarErrs(s3, UBToolsMsg.getMsg(7094295313015382595L, new Object[] { s }));
            b = false;
        }
        final String[][] customizedEnvVars = this.m_propMgr.getCustomizedEnvVars(s);
        if (customizedEnvVars != null) {
            for (int i = 0; i < customizedEnvVars.length; ++i) {
                if (customizedEnvVars[i][1] == null) {
                    this.m_theMessages.addEnvVarErrs(customizedEnvVars[i][0], UBToolsMsg.getMsg(7094295313015382596L, new Object[] { customizedEnvVars[i][0] }));
                    b = false;
                }
            }
        }
        return b;
    }
    
    private boolean validateORODEnvironmentGroup(final String s, final String s2, final String s3) {
        boolean b = true;
        final String s4 = "EnvNumError";
        final UBCfgDataServer ubCfgDataServer = new UBCfgDataServer();
        if (!this.m_propMgr.EnvPairsCountValid(s2)) {
            this.m_theMessages.addEnvVarErrs(s4, UBToolsMsg.getMsg(7094295313015382595L, new Object[] { s }));
            b = false;
        }
        final String[][] customizedEnvVars = this.m_propMgr.getCustomizedEnvVars(s);
        if (customizedEnvVars != null) {
            if (s3.equals("OD")) {
                final int verifyODBCEnv;
                if ((verifyODBCEnv = ubCfgDataServer.verifyODBCEnv(customizedEnvVars)) < 0) {
                    if (verifyODBCEnv == -1) {
                        this.m_theMessages.addEnvVarErrs("ODBC", UBToolsMsg.getMsg(7094295313015382932L, new Object[] { "ODBC", s2, "ODBC" }));
                    }
                    else {
                        this.m_theMessages.addEnvVarErrs("ODBC", UBToolsMsg.getMsg(7094295313015383060L, new Object[] { s2 }));
                    }
                }
            }
            else {
                final int verifyOREnv;
                if (s2.indexOf("OR") >= 0 && (verifyOREnv = ubCfgDataServer.verifyOREnv(customizedEnvVars)) < 0) {
                    if (verifyOREnv == -1) {
                        this.m_theMessages.addEnvVarErrs("Oracle", UBToolsMsg.getMsg(7094295313015382932L, new Object[] { "Oracle", s2, "Oracle" }));
                    }
                    else {
                        this.m_theMessages.addEnvVarErrs("Oracle", UBToolsMsg.getMsg(7094295313015383060L, new Object[] { s2 }));
                    }
                }
            }
        }
        return b;
    }
    
    private boolean validateEnvironmentReference(final String s) {
        boolean b = true;
        if (this.m_propMgr.getCustomizedEnvironment(s) == null) {
            b = false;
        }
        return b;
    }
    
    public boolean validateSvcName(final String[] array, final String s) {
        boolean b = false;
        for (int i = 0; i < array.length; ++i) {
            final PropMgrUtils propMgr = this.m_propMgr;
            if (s.equals(PropMgrUtils.getSvcName(array[i]))) {
                b = true;
                break;
            }
        }
        return b;
    }
    
    private boolean validateMinMaxSrvrInst(final UBValidateObject ubValidateObject) {
        final boolean b = true;
        String s = null;
        String s2 = null;
        String s3 = null;
        final PropertyManager.PropertyCollection propColl = ubValidateObject.m_propColl;
        while (propColl.hasMoreElements()) {
            final Object nextElement = propColl.nextElement();
            final String name = ((PropertyManager.Property)nextElement).getName();
            final String value = ((PropertyManager.Property)nextElement).getValue();
            final long propNum = UBCfgPropTable.getPropNum(name);
            if (name.equals("minSrvrInstance")) {
                s = value;
                if (UBPropValidate.DEBUG_TRACE) {
                    System.out.println(">>>>>>> DEBUG:  Found a min and its: " + s);
                }
            }
            if (name.equals("maxSrvrInstance")) {
                s2 = value;
                if (UBPropValidate.DEBUG_TRACE) {
                    System.out.println(">>>>>>> DEBUG:  found a max and its: " + s2);
                }
            }
            if (propNum == 210L) {
                s3 = value;
            }
        }
        if (s != null && s2 != null) {
            final Long n = new Long(s);
            final Long n2 = new Long(s2);
            Long n3;
            if (s3 != null) {
                n3 = new Long(s3);
            }
            else {
                n3 = new Long(0L);
            }
            if (n > n2) {
                this.m_theMessages.add("maxSrvrInstance", UBToolsMsg.getMsg(7094295313015381961L, new Object[] { "For Server Instances ", s, s2 }));
            }
            else if (n3 < n || n3 > n2) {
                this.m_theMessages.add("initialSrvrInstance", UBToolsMsg.getMsg(7094295313015382336L, new Object[] { "For Initial Server Instances (" + s3 + ")", s, s2 }));
            }
        }
        if (!b) {
            this.m_theMessages.add("maxSrvrInstance", UBToolsMsg.getMsg(7094295313015381961L, new Object[] { "For Server Instances", s, s2 }));
        }
        ubValidateObject.m_propColl.reset();
        return b;
    }
    
    public boolean validateNSProps(final UBValidateObject ubValidateObject) {
        boolean b = true;
        boolean b2 = false;
        int n = 0;
        final String fullSvcPath = ubValidateObject.m_fullSvcPath;
        try {
            final PropMgrUtils propMgr = this.m_propMgr;
            PropMgrUtils.getSvcName(fullSvcPath);
            final PropertyManager.PropertyCollection propertiesNoAncestor = this.m_propMgr.getPropertiesNoAncestor(fullSvcPath);
            if (propertiesNoAncestor != null) {
                while (propertiesNoAncestor.hasMoreElements()) {
                    final Object nextElement = propertiesNoAncestor.nextElement();
                    final String name = ((PropertyManager.Property)nextElement).getName();
                    final String value = ((PropertyManager.Property)nextElement).getValue();
                    final long propNum = UBCfgPropTable.getPropNum(name);
                    ++n;
                    switch ((int)propNum) {
                        case 180: {
                            this.m_foundHost = true;
                            continue;
                        }
                        case 370: {
                            continue;
                        }
                        case 220: {
                            if (value.equals("remote")) {
                                continue;
                            }
                            continue;
                        }
                        default: {
                            b2 = true;
                            continue;
                        }
                    }
                }
            }
            if (this.m_isRemote && b2) {
                this.m_theMessages.add("remoteNameServer", UBToolsMsg.getMsg(7094295313015382594L));
            }
        }
        catch (Exception ex) {
            b = false;
        }
        ubValidateObject.m_propColl.reset();
        return b;
    }
    
    private boolean validateMinMaxSrvrPort(final UBValidateObject ubValidateObject) {
        return this.validateMinMax(ubValidateObject, "srvrMinPort", "srvrMaxPort");
    }
    
    private boolean validateMinMaxClientPort(final UBValidateObject ubValidateObject) {
        return this.validateMinMax(ubValidateObject, "minNSClientPort", "maxNSClientPort");
    }
    
    private boolean validateMinMax(final UBValidateObject ubValidateObject, final String str, final String str2) {
        boolean b = true;
        String s = null;
        String s2 = null;
        final PropertyManager.PropertyCollection propColl = ubValidateObject.m_propColl;
        while (propColl.hasMoreElements()) {
            final Object nextElement = propColl.nextElement();
            final String name = ((PropertyManager.Property)nextElement).getName();
            final String value = ((PropertyManager.Property)nextElement).getValue();
            if (name.equals(str)) {
                s = value;
                if (UBPropValidate.DEBUG_TRACE) {
                    System.out.println(">>>>>>> DEBUG: " + str + ": Found a min and its: " + s);
                }
            }
            if (name.equals(str2)) {
                s2 = value;
                if (!UBPropValidate.DEBUG_TRACE) {
                    continue;
                }
                System.out.println(">>>>>>> DEBUG: " + str2 + ": found a max and its: " + s2);
            }
        }
        if (s != null && s2 != null) {
            try {
                if (this.verifyNumeric(s) && this.verifyNumeric(s2) && new Long(s) > new Long(s2)) {
                    this.m_theMessages.add(str2, UBToolsMsg.getMsg(7094295313015381961L, new Object[] { str + "/" + str2, s, s2 }));
                }
            }
            catch (Exception ex) {
                b = false;
            }
        }
        ubValidateObject.m_propColl.reset();
        return b;
    }
    
    private boolean validateMultiValues(final String s, final String anObject, final boolean b, final boolean b2, final String s2) {
        Vector vector = new Vector<String>();
        final PropMgrUtils propMgr = this.m_propMgr;
        final String svcTypeStr = PropMgrUtils.getSvcTypeStr(s2);
        String substring = "";
        boolean b3 = true;
        final int lastIndex = s2.lastIndexOf(".");
        if (lastIndex >= 0) {
            substring = s2.substring(lastIndex + 1);
        }
        if (!b2) {
            this.m_propColls.elements();
            vector = this.getValueList(s, b);
        }
        else {
            final Vector allServiceList = this.getAllServiceList(svcTypeStr);
            if (allServiceList.elements().hasMoreElements()) {
                if (svcTypeStr == "NS") {
                    vector = this.getValueListNoPropColls(s, b, allServiceList, substring, true);
                }
                else {
                    vector = this.getValueListNoPropColls(s, b, allServiceList, substring, false);
                }
            }
        }
        if (vector != null) {
            final Enumeration<String> elements = vector.elements();
            for (int i = 0; i < vector.size(); ++i) {
                if (elements.nextElement().equals(anObject)) {
                    b3 = false;
                }
            }
        }
        return b3;
    }
    
    private boolean validateMultiValues(final String s, final String anObject, final String s2) {
        final Vector vector = new Vector();
        boolean b = true;
        this.m_propColls.elements();
        final Vector valueList = this.getValueList(s, s2);
        if (valueList != null) {
            final Enumeration<String> elements = valueList.elements();
            for (int i = 0; i < valueList.size(); ++i) {
                if (elements.nextElement().equals(anObject)) {
                    b = false;
                }
            }
        }
        return b;
    }
    
    private boolean valRequiredProps(final String s, final String s2) {
        boolean b = true;
        if (!s.equals("MSNGR") && !s.equals("AIA") && !s.equals("WSA")) {
            try {
                if (!this.m_theFlags.getFlag(0) && !this.m_isRemote) {
                    this.m_theMessages.add("portNumber", UBToolsMsg.getMsg(7094295313015382584L, new Object[] { "portNumber" }));
                    b = false;
                }
            }
            catch (Exception ex) {
                System.out.println("Error retrieving property flags");
            }
        }
        if (s.equals("WS")) {
            b = this.requiredPropsWS();
        }
        else if (s.equals("AS")) {
            b = this.requiredPropsAS();
        }
        else if (s.equals("OR")) {
            b = this.requiredPropsOR(s2);
        }
        else if (s.equals("OD")) {
            b = this.requiredPropsOD(s2);
        }
        else if (s.equals("NS")) {
            b = this.requiredPropsNS();
        }
        else if (s.equals("MSNGR")) {
            b = this.requiredPropsMSNGR();
        }
        else if (s.equals("WSA")) {
            b = this.requiredPropsWSA(s2);
        }
        return b;
    }
    
    private boolean requiredPropsWS() {
        boolean b = true;
        if (!this.m_theFlags.getFlag(0)) {
            b = false;
        }
        return b;
    }
    
    private boolean requiredPropsAS() {
        return true;
    }
    
    private boolean requiredPropsOR(final String s) {
        final boolean b = true;
        try {
            if (!this.m_theFlags.getFlag(1)) {
                this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015382560L, new Object[] { "srvrStartupParam", s }));
            }
        }
        catch (Exception ex) {
            this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("requiredPropsOR");
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    private boolean requiredPropsOD(final String s) {
        boolean b = true;
        try {
            if (!this.m_theFlags.getFlag(1)) {
                this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015382560L, new Object[] { "srvrStartupParam", s }));
                b = false;
            }
        }
        catch (Exception ex) {
            this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("requiredPropsOD");
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    private boolean requiredPropsNS() {
        return true;
    }
    
    private boolean requiredPropsMSNGR() {
        return true;
    }
    
    private boolean requiredPropsWSA(final String s) {
        boolean b = true;
        boolean b2 = true;
        try {
            if (!this.m_theFlags.getFlag(8)) {
                this.m_theMessages.add("wsaUrl", UBToolsMsg.getMsg(7094295313015382560L, new Object[] { "wsaUrl", s }));
                b = false;
            }
        }
        catch (Exception ex) {
            this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("requiredPropsOD");
                ex.printStackTrace();
            }
            b2 = false;
        }
        try {
            if (!this.m_theFlags.getFlag(2) && b2) {
                this.m_theMessages.add("loggingLevel", UBToolsMsg.getMsg(7094295313015382560L, new Object[] { "loggingLevel", s }));
                b = false;
            }
        }
        catch (Exception ex2) {
            this.m_theMessages.add("srvrStartupParam", UBToolsMsg.getMsg(7094295313015381967L, new Object[] { ex2.toString() }));
            if (UBPropValidate.DEBUG_TRACE) {
                System.out.println("requiredPropsOD");
                ex2.printStackTrace();
            }
        }
        return b;
    }
    
    private String getVal(final int beginIndex, final String s) {
        final char[] dst = new char[s.length()];
        final char[] value = new char[s.length()];
        final String substring = s.substring(beginIndex);
        substring.getChars(0, substring.length(), dst, 0);
        String s2;
        try {
            int n = 0;
            int n2 = 0;
            for (int i = 0; i < dst.length; ++i) {
                if (n != 0) {
                    if (dst[i] == '-') {
                        break;
                    }
                    if (dst[i] == ' ') {
                        break;
                    }
                }
                if (dst[i] == ' ') {
                    n = 1;
                }
                if (n != 0 && dst[i] != ' ') {
                    value[n2++] = dst[i];
                }
            }
            s2 = new String(value);
        }
        catch (StringIndexOutOfBoundsException ex) {
            s2 = null;
        }
        return s2;
    }
    
    static {
        UBPropValidate.DEBUG_TRACE = false;
    }
    
    protected class ValidateFlags
    {
        private boolean[] theFlags;
        private final int FLAG_COUNT = 100;
        
        ValidateFlags() {
            this.theFlags = new boolean[100];
            this.initFlags();
        }
        
        boolean[] getFlags() {
            return this.theFlags;
        }
        
        boolean getFlag(final int n) {
            return this.theFlags[n];
        }
        
        void setFlag(final int n) {
            this.theFlags[n] = true;
        }
        
        void clearFlag(final int n) {
            this.theFlags[n] = false;
        }
        
        void initFlags() {
            for (int i = 0; i < 100; ++i) {
                this.clearFlag(i);
            }
        }
    }
}
