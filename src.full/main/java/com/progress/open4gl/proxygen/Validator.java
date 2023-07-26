// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.progress.international.resources.ProgressResources;
import java.io.File;
import java.util.Vector;

public class Validator
{
    public static boolean removeObsoleteProcs(final PGAppObj pgAppObj, final PGGenInfo pgGenInfo) {
        boolean b = false;
        if (removeObsoleteProcs(pgAppObj.getAppObjectName(), pgAppObj.getProcVector(), pgGenInfo)) {
            b = true;
        }
        if (removeObsoleteProcs(pgAppObj.getAppObjectName(), pgAppObj.getPerProcVector(), pgGenInfo)) {
            b = true;
        }
        final PGAppObj[] subObjects = pgAppObj.getSubObjects();
        if (subObjects != null) {
            for (int i = 0; i < subObjects.length; ++i) {
                if (removeObsoleteProcs(subObjects[i], pgGenInfo)) {
                    b = true;
                }
            }
        }
        return b;
    }
    
    static boolean resolveDifferences(final PGProcDetail pgProcDetail, final PGProcDetail pgProcDetail2, final PGAppObj pgAppObj, final PGGenInfo pgGenInfo) {
        boolean compareParams = compareParams(pgProcDetail.getParameters(), pgProcDetail2.getParameters(), pgProcDetail.isCustomized(), pgAppObj, pgGenInfo, 2);
        pgProcDetail.setParameters(pgProcDetail2.getParameters());
        final PGMethod[] internalProcs = pgProcDetail.getInternalProcs();
        final PGMethod[] internalProcs2 = pgProcDetail2.getInternalProcs();
        final int n = (internalProcs == null) ? 0 : internalProcs.length;
        final int n2 = (internalProcs2 == null) ? 0 : internalProcs2.length;
        if (n > 0 && n2 == 0) {
            pgProcDetail.setInternalProcs(new PGMethod[0]);
            compareParams = false;
        }
        else if (n == 0 && n2 > 0) {
            pgProcDetail.setInternalProcs(internalProcs2);
        }
        else if (n > 0 && n2 > 0) {
            if (!compareMethods(internalProcs, internalProcs2, pgAppObj, pgGenInfo)) {
                compareParams = false;
            }
            pgProcDetail.setInternalProcs(internalProcs2);
        }
        return compareParams;
    }
    
    public static boolean checkAllNames(final PGAppObj pgAppObj, final PGGenInfo pgGenInfo) {
        boolean b = true;
        final Vector vector = new Vector<String>();
        final Vector<String> vector2 = new Vector<String>();
        if (!checkAppObjectNames(pgAppObj, vector, vector2, pgGenInfo)) {
            b = false;
        }
        if (pgAppObj.m_pSubObjs != null) {
            for (int i = 0; i < pgAppObj.m_pSubObjs.length; ++i) {
                if (!checkAppObjectNames(pgAppObj.m_pSubObjs[i], vector, vector2, pgGenInfo)) {
                    b = false;
                }
            }
        }
        for (int j = 0; j < vector.size(); ++j) {
            final String s = vector.elementAt(j);
            for (int k = j + 1; k < vector.size(); ++k) {
                if (s.equals(vector.elementAt(k))) {
                    pgGenInfo.logIt(1, "PGLOG_ObjDupError", s, 3);
                    pgGenInfo.logIt(2, null, vector2.elementAt(j), 3);
                    pgGenInfo.logIt(2, null, vector2.elementAt(k), 3);
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
    
    public static String checkProcExists(final PGProc pgProc) {
        String string = null;
        final String string2 = pgProc.getProcName() + ".r";
        String str = pgProc.getProPath();
        if (pgProc.getProcPath() != null) {
            str = pgProc.getProPath() + pgProc.getProcPath();
        }
        if (!new File(PGAppObj.getAbsFilename(str, string2)).exists()) {
            string = str + string2;
        }
        return string;
    }
    
    private static boolean removeObsoleteProcs(final String s, final Vector vector, final PGGenInfo pgGenInfo) {
        boolean b = false;
        final String insertVariable = Generator.insertVariable(PGGenInfo.getResources().getTranString("PGLOG_FileNotFound"), "%AOName%", s);
        int size = vector.size();
        int i = 0;
        while (i < size) {
            final String checkProcExists = checkProcExists(vector.elementAt(i));
            if (checkProcExists == null) {
                ++i;
            }
            else {
                vector.removeElementAt(i);
                --size;
                b = true;
                pgGenInfo.logIt(1, null, insertVariable + checkProcExists, 3);
            }
        }
        return b;
    }
    
    private static boolean checkAppObjectNames(final PGAppObj pgAppObj, final Vector vector, final Vector vector2, final PGGenInfo pgGenInfo) {
        final String appObjectName = pgAppObj.getAppObjectName();
        final PGProc[] procedures = pgAppObj.getProcedures();
        final PGProc[] persistentProcedures = pgAppObj.getPersistentProcedures();
        final ProgressResources resources = PGGenInfo.getResources();
        final String tranString = resources.getTranString("PGLOG_ValContext3");
        final String tranString2 = resources.getTranString("PGLOG_ValContext4");
        boolean b = true;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        vector.addElement(appObjectName);
        if (pgAppObj.isSubAppObject()) {
            vector2.addElement(resources.getTranString("PGLOG_SubAppObjIs") + appObjectName);
        }
        else {
            vector2.addElement(resources.getTranString("PGLOG_AppObjIs") + appObjectName);
        }
        if (procedures != null && procedures.length > 0 && !checkMethodNames(procedures, appObjectName, pgGenInfo)) {
            b = false;
        }
        if (persistentProcedures != null && persistentProcedures.length > 0 && !checkMethodNames(persistentProcedures, appObjectName, pgGenInfo)) {
            b = false;
        }
        final String insertVariable = Generator.insertVariable(tranString, "%AOName%", appObjectName);
        final String insertVariable2 = Generator.insertVariable(tranString2, "%AOName%", appObjectName);
        if (pgGenInfo.genJavaProxy() || pgGenInfo.genActiveXProxy()) {
            b2 = true;
        }
        if (pgGenInfo.genDotNetProxy()) {
            b3 = true;
        }
        if (pgGenInfo.genWebServicesProxy()) {
            b4 = true;
        }
        for (int n = 0; persistentProcedures != null && n < persistentProcedures.length; ++n) {
            final String string = persistentProcedures[n].getProPath() + persistentProcedures[n].getProcPath() + persistentProcedures[n].getProcName() + "." + persistentProcedures[n].getProcExt();
            final PGProcDetail procDetail = persistentProcedures[n].getProcDetail();
            final String methodName = procDetail.getMethodName();
            vector.addElement(methodName);
            vector2.addElement(resources.getTranString("PGLOG_ProcObjIs") + methodName + " (" + string + ")");
            final String insertVariable3 = Generator.insertVariable(insertVariable, "%POName%", methodName);
            final String insertVariable4 = Generator.insertVariable(insertVariable2, "%POName%", methodName);
            final PGMethod[] internalProcs = procDetail.getInternalProcs();
            for (int n2 = 0; internalProcs != null && n2 < internalProcs.length; ++n2) {
                if (!internalProcs[n2].isExcluded()) {
                    final PGMethodDetail methodDetail = internalProcs[n2].getMethodDetail();
                    final String methodName2 = methodDetail.getMethodName();
                    if (b3 && methodName2.equals(methodName)) {
                        pgGenInfo.logIt(1, "PGLOG_POMethodObjDupError", methodName2 + insertVariable3, 3);
                        b = false;
                    }
                    if (!PGAppObj.validateName(methodName2, b2, b3, b4)) {
                        pgGenInfo.logIt(1, "PGLOG_MethodNameError", methodName2 + insertVariable3, 3);
                        pgGenInfo.logIt(2, "PGLOG_ProcFileIs", string, 3);
                        b = false;
                    }
                    for (int i = n2 + 1; i < internalProcs.length; ++i) {
                        if (!internalProcs[i].isExcluded()) {
                            if (methodName2.equals(internalProcs[i].getMethodDetail().getMethodName())) {
                                pgGenInfo.logIt(1, "PGLOG_MethodDupError", methodName2 + insertVariable3, 3);
                                pgGenInfo.logIt(2, "PGLOG_ProcFileIs", string, 3);
                                pgGenInfo.logIt(2, "PGLOG_ProcsAre", internalProcs[n2].getInternalProc() + ", " + internalProcs[i].getInternalProc(), 3);
                                b = false;
                                break;
                            }
                        }
                    }
                    if (!checkParamNames(methodDetail.getParameters(), Generator.insertVariable(insertVariable4, "%POMethod%", methodName2), string, pgGenInfo)) {
                        b = false;
                    }
                }
            }
        }
        return b;
    }
    
    private static boolean checkMethodNames(final PGProc[] array, final String anObject, final PGGenInfo pgGenInfo) {
        final ProgressResources resources = PGGenInfo.getResources();
        final String tranString = resources.getTranString("PGLOG_ValContext1");
        String s = resources.getTranString("PGLOG_ValContext2");
        String s2 = resources.getTranString("PGLOG_ValContext3");
        boolean b = true;
        final boolean persistent = array[0].isPersistent();
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        if (pgGenInfo.genJavaProxy() || pgGenInfo.genActiveXProxy()) {
            b2 = true;
        }
        if (pgGenInfo.genDotNetProxy()) {
            b3 = true;
        }
        if (pgGenInfo.genWebServicesProxy()) {
            b4 = true;
        }
        final String insertVariable = Generator.insertVariable(tranString, "%AOName%", anObject);
        if (persistent) {
            s2 = Generator.insertVariable(s2, "%AOName%", anObject);
        }
        else {
            s = Generator.insertVariable(s, "%AOName%", anObject);
        }
        for (int i = 0; i < array.length; ++i) {
            final String string = array[i].getProPath() + array[i].getProcPath() + array[i].getProcName() + "." + array[i].getProcExt();
            final PGProcDetail procDetail = array[i].getProcDetail();
            final String methodName = procDetail.getMethodName();
            if (b3 && methodName.equals(anObject)) {
                pgGenInfo.logIt(1, "PGLOG_POMethodObjDupError", methodName + insertVariable, 3);
                b = false;
            }
            if (!PGAppObj.validateName(methodName, b2, b3, b4)) {
                if (persistent) {
                    pgGenInfo.logIt(1, "PGLOG_PONameError", methodName + insertVariable, 3);
                }
                else {
                    pgGenInfo.logIt(1, "PGLOG_MethodNameError", methodName + insertVariable, 3);
                }
                pgGenInfo.logIt(2, "PGLOG_ProcFileIs", string, 3);
                b = false;
            }
            for (int n = i + 1; !persistent && n < array.length; ++n) {
                final String string2 = array[n].getProPath() + array[n].getProcPath() + array[n].getProcName() + "." + array[n].getProcExt();
                if (methodName.equals(array[n].getProcDetail().getMethodName())) {
                    pgGenInfo.logIt(1, "PGLOG_MethodDupError", methodName + insertVariable, 3);
                    pgGenInfo.logIt(2, "PGLOG_ProcFileIs", string, 3);
                    pgGenInfo.logIt(2, "PGLOG_ProcFileIs", string2, 3);
                    b = false;
                    break;
                }
            }
            String s3;
            if (persistent) {
                s3 = Generator.insertVariable(s2, "%POName%", methodName);
            }
            else {
                s3 = Generator.insertVariable(s, "%AOMethod%", methodName);
            }
            if (!checkParamNames(procDetail.getParameters(), s3, string, pgGenInfo)) {
                b = false;
            }
        }
        return b;
    }
    
    private static boolean checkParamNames(final PGParam[] array, final String s, final String s2, final PGGenInfo pgGenInfo) {
        boolean b = true;
        boolean b2 = false;
        if (pgGenInfo.genActiveXProxy() || pgGenInfo.genJavaProxy()) {
            b2 = true;
        }
        for (int n = 0; array != null && n < array.length; ++n) {
            final String paramName = array[n].getParamName();
            if (PGAppObj.isKeyword(paramName, b2, pgGenInfo.genDotNetProxy())) {
                pgGenInfo.logIt(1, "PGLOG_ParamKeywordError", paramName + s, 3);
                pgGenInfo.logIt(2, "PGLOG_ProcFileIs", s2, 3);
                pgGenInfo.logIt(2, "PGLOG_ParamsAre", Integer.toString(n + 1), 3);
                b = false;
            }
            for (int i = n + 1; i < array.length; ++i) {
                if (paramName.equals(array[i].getParamName())) {
                    pgGenInfo.logIt(1, "PGLOG_ParamDupError", paramName + s, 3);
                    pgGenInfo.logIt(2, "PGLOG_ProcFileIs", s2, 3);
                    pgGenInfo.logIt(2, "PGLOG_ParamsAre", Integer.toString(n + 1) + ", " + Integer.toString(i + 1), 3);
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
    
    private static boolean compareParams(final PGParam[] array, final PGParam[] array2, final boolean b, final PGAppObj pgAppObj, final PGGenInfo pgGenInfo, final int n) {
        final int n2 = (array == null) ? 0 : array.length;
        final int n3 = (array2 == null) ? 0 : array2.length;
        if (n2 == 0 || n3 == 0 || !b) {
            if (pgGenInfo != null) {
                pgGenInfo.logIt(n, "PGLOG_DefaultsUsed", null, 2);
            }
            return !b || n2 <= 0;
        }
        int n4 = 1;
        for (int i = 0; i < n2; ++i) {
            if (array[i].allowUnknown() != pgAppObj.allowUnknown()) {
                n4 = 0;
                break;
            }
        }
        if (n4 != 0) {
            for (int j = 0; j < n2; ++j) {
                if (array[j].getWriteBeforeImage() != pgAppObj.getWriteBeforeImage()) {
                    n4 = 0;
                    break;
                }
            }
        }
        if (n4 != 0) {
            if (pgGenInfo != null) {
                pgGenInfo.logIt(n, "PGLOG_DefaultsUsed", null, 2);
            }
            return true;
        }
        int k = 0;
        for (int l = 0; l < n3; ++l) {
            for (int n5 = 0; n5 < n2; ++n5) {
                final String paramName = array2[l].getParamName();
                final int paramType = array2[l].getParamType();
                if (paramName.equalsIgnoreCase(array[n5].getParamName()) && paramType == array[n5].getParamType()) {
                    array2[l].setAllowUnknown(array[n5].allowUnknown());
                    array2[l].setWriteBeforeImage(array[n5].getWriteBeforeImage());
                    ++k;
                    break;
                }
            }
        }
        if (k > 0 && pgGenInfo != null) {
            pgGenInfo.logIt(n, "PGLOG_CustsAppliedTo", k + PGGenInfo.getResources().getTranString("PGLOG_MatchingParms"), 2);
        }
        return k == n2;
    }
    
    private static boolean compareMethods(final PGMethod[] array, final PGMethod[] array2, final PGAppObj pgAppObj, final PGGenInfo pgGenInfo) {
        final int length = array.length;
        final int length2 = array2.length;
        final boolean[] array3 = new boolean[length];
        final boolean[] array4 = new boolean[length2];
        String s = null;
        boolean b = true;
        if (pgGenInfo != null) {
            pgGenInfo.logIt(2, "PGLOG_MatchingMethods", null, 2);
        }
        for (int i = 0; i < length2; ++i) {
            int j = 0;
            while (j < length) {
                final String internalProc = array2[i].getInternalProc();
                final int procType = array2[i].getProcType();
                if (internalProc.equalsIgnoreCase(array[j].getInternalProc()) && procType == array[j].getProcType()) {
                    if (pgGenInfo != null) {
                        array4[i] = (array3[j] = true);
                        final PGMethodDetail methodDetail = array[j].getMethodDetail();
                        String string = "";
                        if (methodDetail != null) {
                            final String methodName = methodDetail.getMethodName();
                            if (!internalProc.equals(methodName)) {
                                string = " (" + PGGenInfo.getResources().getTranString("PG_MethodName") + ": " + methodName + ")";
                            }
                            else {
                                string = "";
                            }
                        }
                        s = new String(((procType == 1) ? "PROCEDURE " : "FUNCTION ") + internalProc + string);
                    }
                    if (array[j].isExcluded()) {
                        if (pgGenInfo != null) {
                            pgGenInfo.logIt(3, "PGLOG_Excluded", s, 2);
                        }
                        array2[i].setExcluded(true);
                        break;
                    }
                    if (pgGenInfo != null) {
                        pgGenInfo.logIt(3, null, s, 2);
                    }
                    final PGMethodDetail methodDetail2 = array[j].getMethodDetail();
                    final PGMethodDetail methodDetail3 = array2[i].getMethodDetail();
                    if (methodDetail2 != null && methodDetail2.isCustomized()) {
                        PGMethodDetail.copyScalarInfo(methodDetail2, methodDetail3);
                        if (!compareParams(methodDetail2.getParameters(), methodDetail3.getParameters(), methodDetail2.isCustomized(), pgAppObj, pgGenInfo, 4)) {
                            b = false;
                        }
                        final PGParam returnValue = methodDetail2.getReturnValue();
                        final PGParam returnValue2 = methodDetail3.getReturnValue();
                        if (returnValue2 != null && returnValue != null) {
                            returnValue2.setAllowUnknown(returnValue.allowUnknown());
                        }
                    }
                    else if (pgGenInfo != null) {
                        pgGenInfo.logIt(4, "PGLOG_DefaultsUsed", null, 2);
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
        }
        if (pgGenInfo != null) {
            pgGenInfo.logIt(2, "PGLOG_MissingMethods", null, 2);
            boolean b2 = false;
            for (int k = 0; k < length; ++k) {
                if (!array3[k]) {
                    pgGenInfo.logIt(3, null, ((array[k].getProcType() == 1) ? "PROCEDURE " : "FUNCTION ") + array[k].getInternalProc(), 2);
                    b = false;
                    b2 = true;
                }
            }
            if (!b2) {
                pgGenInfo.logIt(3, "PGLOG_None", null, 2);
            }
            pgGenInfo.logIt(2, "PGLOG_NewMethods", null, 2);
            boolean b3 = false;
            for (int l = 0; l < length2; ++l) {
                if (!array4[l]) {
                    pgGenInfo.logIt(3, null, ((array2[l].getProcType() == 1) ? "PROCEDURE " : "FUNCTION ") + array2[l].getInternalProc(), 2);
                    b3 = true;
                }
            }
            if (!b3) {
                pgGenInfo.logIt(3, "PGLOG_None", null, 2);
            }
        }
        return b;
    }
}
