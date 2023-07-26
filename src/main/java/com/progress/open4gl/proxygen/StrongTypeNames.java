// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.Vector;

public class StrongTypeNames
{
    public static void build(final PGAppObj pgAppObj, final String s) {
        for (int i = 0; i < pgAppObj.m_pProcs.length; ++i) {
            getStrongTypeParams(s, pgAppObj.m_pProcs[i].getProcDetail());
        }
        for (int j = 0; j < pgAppObj.m_pPerProcs.length; ++j) {
            getStrongTypeParams(s, pgAppObj.m_pPerProcs[j].getProcDetail());
        }
    }
    
    public static void build(final PGProc pgProc, final String s) {
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                getStrongTypeParams(s, pgMethod.getMethodDetail());
            }
        }
    }
    
    private static void getStrongTypeParams(final String namespace, final IPGDetail ipgDetail) {
        PGAppObj.getGenInfo();
        final Vector dataSetList = PGGenInfo.getDataSetList();
        final Vector dataTableList = PGGenInfo.getDataTableList();
        final PGParam[] parameters = ipgDetail.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam pgParam = parameters[i];
            final int paramType = pgParam.getParamType();
            if (paramType == 36 || paramType == 15) {
                final IPGStrongNameParam ipgStrongNameParam = (IPGStrongNameParam)pgParam;
                ipgStrongNameParam.setProcName(ipgDetail.getMethodName());
                ipgStrongNameParam.setNamespace(namespace);
                if (paramType == 36) {
                    dataSetList.addElement(pgParam);
                }
                else {
                    dataTableList.addElement(pgParam);
                }
            }
        }
    }
    
    public static void buildForWS(final PGAppObj pgAppObj) {
        for (int i = 0; i < pgAppObj.m_pProcs.length; ++i) {
            getStrongTypeParamsForWS(pgAppObj.m_pProcs[i].getProcDetail());
        }
        for (int j = 0; j < pgAppObj.m_pPerProcs.length; ++j) {
            getStrongTypeParamsForWS(pgAppObj.m_pPerProcs[j].getProcDetail());
        }
    }
    
    public static void buildForWS(final PGProc pgProc) {
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                getStrongTypeParamsForWS(pgMethod.getMethodDetail());
            }
        }
    }
    
    private static void getStrongTypeParamsForWS(final IPGDetail ipgDetail) {
        PGAppObj.getGenInfo();
        final Vector dataSetList = PGGenInfo.getDataSetList();
        final PGParam[] parameters = ipgDetail.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam obj = parameters[i];
            if (obj.getParamType() == 36) {
                final PGDataSetParam pgDataSetParam = (PGDataSetParam)obj;
                final PGDataSetParam pgDataSetParam2 = (PGDataSetParam)obj;
                pgDataSetParam.setProcName(ipgDetail.getMethodName());
                pgDataSetParam.setNamespace(pgDataSetParam2.getXmlNamespace());
                dataSetList.addElement(obj);
            }
        }
    }
    
    public static void updateNames(final Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            final IPGStrongNameParam ipgStrongNameParam = vector.elementAt(i);
            if (ipgStrongNameParam.getClassName() == null) {
                final IPGStrongNameParam sameSchema = findSameSchema(vector, i, ipgStrongNameParam);
                if (sameSchema != null) {
                    ipgStrongNameParam.setClassName(sameSchema.getClassName());
                    ipgStrongNameParam.setSameParamNameIndex(sameSchema.getSameParamNameIndex());
                }
                else {
                    final IPGStrongNameParam sameParamName = findSameParamName(vector, i, ipgStrongNameParam);
                    if (sameParamName != null) {
                        int sameParamNameIndex = sameParamName.getSameParamNameIndex();
                        ipgStrongNameParam.setSameParamNameIndex(++sameParamNameIndex);
                        ipgStrongNameParam.setClassName(ipgStrongNameParam.getParamName() + Integer.toString(sameParamNameIndex));
                        ipgStrongNameParam.setTopMatch(true);
                    }
                    else {
                        ipgStrongNameParam.setClassName(ipgStrongNameParam.getParamName());
                        ipgStrongNameParam.setSameParamNameIndex(1);
                        ipgStrongNameParam.setTopMatch(true);
                    }
                }
            }
        }
        final int size = vector.size();
        int j = 0;
        int n = 0;
        while (j < size) {
            if (!vector.elementAt(n).isTopMatch()) {
                vector.remove(n);
            }
            else {
                ++n;
            }
            ++j;
        }
    }
    
    public static void updateNamesForWS(final Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            final IPGStrongNameParam ipgStrongNameParam = vector.elementAt(i);
            if (ipgStrongNameParam.getClassName() == null) {
                final IPGStrongNameParam sameSchemaWS = findSameSchemaWS(vector, i, ipgStrongNameParam);
                if (sameSchemaWS != null) {
                    ipgStrongNameParam.setClassNameForWS(sameSchemaWS.getClassName());
                    ipgStrongNameParam.setSameParamNameIndex(sameSchemaWS.getSameParamNameIndex());
                }
                else {
                    final IPGStrongNameParam sameParamNameWS = findSameParamNameWS(vector, i, ipgStrongNameParam);
                    if (sameParamNameWS != null) {
                        int sameParamNameIndex = sameParamNameWS.getSameParamNameIndex();
                        ipgStrongNameParam.setSameParamNameIndex(++sameParamNameIndex);
                        ipgStrongNameParam.setClassNameForWS(ipgStrongNameParam.getParamName() + Integer.toString(sameParamNameIndex));
                        ipgStrongNameParam.setTopMatch(true);
                    }
                    else {
                        ipgStrongNameParam.setClassNameForWS(ipgStrongNameParam.getParamName());
                        ipgStrongNameParam.setSameParamNameIndex(1);
                        ipgStrongNameParam.setTopMatch(true);
                    }
                }
            }
        }
        final int size = vector.size();
        int j = 0;
        int n = 0;
        while (j < size) {
            if (!vector.elementAt(n).isTopMatch()) {
                vector.remove(n);
            }
            else {
                ++n;
            }
            ++j;
        }
    }
    
    public static IPGStrongNameParam findSameSchema(final Vector vector, final int n, final IPGStrongNameParam ipgStrongNameParam) {
        for (int i = 0; i < n; ++i) {
            final IPGStrongNameParam ipgStrongNameParam2 = vector.elementAt(i);
            if (ipgStrongNameParam.hasSameSchema(ipgStrongNameParam2)) {
                return ipgStrongNameParam2;
            }
        }
        return null;
    }
    
    public static IPGStrongNameParam findSameParamName(final Vector vector, final int n, final IPGStrongNameParam ipgStrongNameParam) {
        IPGStrongNameParam ipgStrongNameParam2 = null;
        final String paramName = ipgStrongNameParam.getParamName();
        for (int i = 0; i < n; ++i) {
            final IPGStrongNameParam ipgStrongNameParam3 = vector.elementAt(i);
            if (paramName.equals(ipgStrongNameParam3.getParamName())) {
                if (ipgStrongNameParam2 == null) {
                    ipgStrongNameParam2 = ipgStrongNameParam3;
                }
                else if (ipgStrongNameParam3.getSameParamNameIndex() > ipgStrongNameParam2.getSameParamNameIndex()) {
                    ipgStrongNameParam2 = ipgStrongNameParam3;
                }
            }
        }
        return ipgStrongNameParam2;
    }
    
    public static IPGStrongNameParam findSameSchemaWS(final Vector vector, final int n, final IPGStrongNameParam ipgStrongNameParam) {
        for (int i = 0; i < n; ++i) {
            final IPGStrongNameParam ipgStrongNameParam2 = vector.elementAt(i);
            if (ipgStrongNameParam.hasSameSchemaWS(ipgStrongNameParam2)) {
                final String namespace = ipgStrongNameParam.getNamespace();
                final String namespace2 = ipgStrongNameParam2.getNamespace();
                if (namespace == null && namespace2 == null) {
                    return ipgStrongNameParam2;
                }
                if (namespace != null) {
                    if (namespace2 != null) {
                        if (namespace.equals(namespace2)) {
                            return ipgStrongNameParam2;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static IPGStrongNameParam findSameParamNameWS(final Vector vector, final int n, final IPGStrongNameParam ipgStrongNameParam) {
        IPGStrongNameParam ipgStrongNameParam2 = null;
        final String paramName = ipgStrongNameParam.getParamName();
        for (int i = 0; i < n; ++i) {
            final IPGStrongNameParam ipgStrongNameParam3 = vector.elementAt(i);
            if (paramName.equals(ipgStrongNameParam3.getParamName())) {
                final String namespace = ipgStrongNameParam.getNamespace();
                final String namespace2 = ipgStrongNameParam3.getNamespace();
                if (namespace == null || namespace2 == null || namespace.equals(namespace2)) {
                    if (namespace != null || namespace2 == null) {
                        if (namespace != null || namespace2 == null) {
                            if (ipgStrongNameParam2 == null) {
                                ipgStrongNameParam2 = ipgStrongNameParam3;
                            }
                            else if (ipgStrongNameParam3.getSameParamNameIndex() > ipgStrongNameParam2.getSameParamNameIndex()) {
                                ipgStrongNameParam2 = ipgStrongNameParam3;
                            }
                        }
                    }
                }
            }
        }
        return ipgStrongNameParam2;
    }
}
