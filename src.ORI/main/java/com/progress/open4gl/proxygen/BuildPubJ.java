// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.GregorianCalendar;
import java.io.IOException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.util.ProgressVersion;
import java.io.PrintWriter;

public class BuildPubJ extends Generator implements JPubTemplate
{
    public static void build(final PGAppObj pgAppObj, final PrintWriter printWriter) throws Open4GLException {
        final StringBuffer sb = new StringBuffer(4096);
        Generator.mapper = new JavaDataMapper();
        Generator.schemaTemplate = new JSchemaTemplate();
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String package1 = genInfo.getPackage();
        String s;
        if (pgAppObj.m_bSubObject) {
            s = Generator.extractTemplate(5);
        }
        else {
            s = Generator.extractTemplate(1);
        }
        final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(s, "%CreatedBy%", PGGenInfo.getResources().getTranString("PGPROXY_CreatedBy")), "%Version%", ProgressVersion.getVersionString()), "%Date%", getDateTime()), "%JDDesc%", pgAppObj.getHelpString()), "%JDAuthor%", genInfo.getAuthor()), "%JDVersion%", genInfo.getVersion()), "%ObjectName%", pgAppObj.m_strAppObj), "%Service%", genInfo.getServiceName()), "%SessionMode%", "IPoolProps.SM_SESSION_MANAGED");
        String s2;
        if (package1 != null && package1.length() > 0) {
            s2 = Generator.insertVariable(insertVariable, "%Package%", "package " + package1 + ";");
        }
        else {
            s2 = Generator.insertVariable(insertVariable, "%Package%", "");
        }
        printWriter.print(Generator.insertVariable(s2, "%Methods%", buildMethods(pgAppObj)));
    }
    
    public static void build(final PGProc pgProc, final String str, final PrintWriter printWriter) throws Open4GLException, IOException {
        String strHelp = "";
        PGAppObj.getGenInfo();
        final String template = Generator.extractTemplate(3);
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(template, "%CreatedBy%", PGGenInfo.getResources().getTranString("PGPROXY_CreatedBy")), "%Version%", ProgressVersion.getVersionString()), "%Date%", getDateTime());
        if (procDetail.m_strHelp != null) {
            strHelp = procDetail.m_strHelp;
        }
        final String insertVariable2 = Generator.insertVariable(Generator.insertVariable(insertVariable, "%JDDesc%", strHelp), "%ObjectName%", procDetail.m_strMethod);
        String s;
        if (str != null && str.length() > 0) {
            s = Generator.insertVariable(insertVariable2, "%Package%", "package " + str + ";");
        }
        else {
            s = Generator.insertVariable(insertVariable2, "%Package%", "");
        }
        printWriter.print(Generator.insertVariable(buildConstructor(s, pgProc), "%Methods%", buildMethods(pgProc)));
    }
    
    private static String buildMethods(final PGAppObj pgAppObj) {
        final StringBuffer sb = new StringBuffer(16384);
        if (!pgAppObj.m_bSubObject) {
            for (int i = 0; i < pgAppObj.m_pSubObjs.length; ++i) {
                String strHelp = "";
                final PGAppObj pgAppObj2 = pgAppObj.m_pSubObjs[i];
                if (pgAppObj2.m_strHelp != null) {
                    strHelp = pgAppObj2.m_strHelp;
                }
                sb.append(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(JPubTemplate.szFactAOJ, "%HelpString%", Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", strHelp)), "%ObjectName%", pgAppObj2.m_strAppObj), "%ParentName%", pgAppObj.m_strAppObj));
            }
        }
        for (int j = 0; j < pgAppObj.m_pPerProcs.length; ++j) {
            final PGProcDetail procDetail = pgAppObj.m_pPerProcs[j].getProcDetail();
            String strHelp2 = "";
            if (procDetail.m_strHelp != null) {
                strHelp2 = procDetail.m_strHelp;
            }
            final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(JPubTemplate.szFactPOJ, "%HelpString%", Generator.insertVariable(Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", strHelp2), "%TblDesc%", Generator.buildSchemaDoc(procDetail.getParameters(), false))), "%ObjectName%", procDetail.m_strMethod), "%Method%", procDetail.m_strMethod), "%ParentName%", pgAppObj.m_strAppObj);
            final StringBuffer sb2 = new StringBuffer(128);
            for (int k = 0; k < procDetail.m_pParams.length; ++k) {
                final PGParam pgParam = procDetail.m_pParams[k];
                if (k > 0) {
                    sb2.append(", ");
                }
                sb2.append(Generator.mapper.proToNative(pgParam) + " " + pgParam.m_strName);
            }
            final String insertVariable2 = Generator.insertVariable(insertVariable, "%Params%", sb2.toString());
            final StringBuffer sb3 = new StringBuffer(128);
            for (int l = 0; l < procDetail.m_pParams.length; ++l) {
                final PGParam pgParam2 = procDetail.m_pParams[l];
                sb3.append(", ");
                sb3.append(" " + pgParam2.m_strName);
            }
            sb.append(Generator.insertVariable(insertVariable2, "%Params1%", sb3.toString()));
        }
        for (int n = 0; n < pgAppObj.m_pProcs.length; ++n) {
            final PGProcDetail procDetail2 = pgAppObj.m_pProcs[n].getProcDetail();
            final PGParam returnValue = procDetail2.getReturnValue();
            String strHelp3 = "";
            if (procDetail2.m_strHelp != null) {
                strHelp3 = procDetail2.m_strHelp;
            }
            final String insertVariable3 = Generator.insertVariable(JPubTemplate.szMethodJ, "%HelpString%", Generator.insertVariable(Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", strHelp3), "%TblDesc%", Generator.buildSchemaDoc(procDetail2.getParameters(), false)));
            String s;
            if (returnValue == null) {
                s = Generator.insertVariable(insertVariable3, "%Return%", "void");
            }
            else {
                s = Generator.insertVariable(insertVariable3, "%Return%", Generator.mapper.proToNative(returnValue));
            }
            final String insertVariable4 = Generator.insertVariable(s, "%Method%", procDetail2.m_strMethod);
            final StringBuffer sb4 = new StringBuffer(128);
            for (int n2 = 0; n2 < procDetail2.m_pParams.length; ++n2) {
                final PGParam pgParam3 = procDetail2.m_pParams[n2];
                if (n2 > 0) {
                    sb4.append(", ");
                }
                sb4.append(Generator.mapper.proToNative(pgParam3) + " " + pgParam3.m_strName);
            }
            final String insertVariable5 = Generator.insertVariable(insertVariable4, "%Params%", sb4.toString());
            final StringBuffer sb5 = new StringBuffer(128);
            for (int n3 = 0; n3 < procDetail2.m_pParams.length; ++n3) {
                final PGParam pgParam4 = procDetail2.m_pParams[n3];
                if (n3 > 0) {
                    sb5.append(", ");
                }
                sb5.append(" " + pgParam4.m_strName);
            }
            final String insertVariable6 = Generator.insertVariable(Generator.insertVariable(insertVariable5, "%Params1%", sb5.toString()), "%ObjectName%", pgAppObj.m_strAppObj);
            String str;
            if (returnValue == null) {
                str = Generator.insertVariable(insertVariable6, "%RetVal%", "");
            }
            else {
                str = Generator.insertVariable(insertVariable6, "%RetVal%", "return ");
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    private static String buildMethods(final PGProc pgProc) {
        final StringBuffer sb = new StringBuffer(16384);
        final PGProcDetail procDetail = pgProc.getProcDetail();
        for (int i = 0; i < procDetail.m_pInternalProcs.length; ++i) {
            final PGMethod pgMethod = procDetail.m_pInternalProcs[i];
            if (!pgMethod.m_bExcluded) {
                final PGMethodDetail methodDetail = pgMethod.getMethodDetail();
                final String insertVariable = Generator.insertVariable(JPubTemplate.szMethodPOJ, "%HelpString%", Generator.insertVariable(Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", methodDetail.m_strHelp), "%TblDesc%", Generator.buildSchemaDoc(methodDetail.getParameters(), false)));
                String s;
                if (methodDetail.m_pRetVal == null) {
                    s = Generator.insertVariable(insertVariable, "%Return%", "void");
                }
                else {
                    s = Generator.insertVariable(insertVariable, "%Return%", Generator.mapper.proToNative(methodDetail.m_pRetVal));
                }
                final String insertVariable2 = Generator.insertVariable(s, "%Method%", methodDetail.m_strMethod);
                final StringBuffer sb2 = new StringBuffer(128);
                for (int j = 0; j < methodDetail.m_pParams.length; ++j) {
                    final PGParam pgParam = methodDetail.m_pParams[j];
                    if (j > 0) {
                        sb2.append(", ");
                    }
                    sb2.append(Generator.mapper.proToNative(pgParam) + " " + pgParam.m_strName);
                }
                final String insertVariable3 = Generator.insertVariable(insertVariable2, "%Params%", sb2.toString());
                final StringBuffer sb3 = new StringBuffer(128);
                for (int k = 0; k < methodDetail.m_pParams.length; ++k) {
                    final PGParam pgParam2 = methodDetail.m_pParams[k];
                    if (k > 0) {
                        sb3.append(", ");
                    }
                    sb3.append(" " + pgParam2.m_strName);
                }
                final String insertVariable4 = Generator.insertVariable(Generator.insertVariable(insertVariable3, "%Params1%", sb3.toString()), "%ObjectName%", procDetail.m_strMethod);
                String str;
                if (methodDetail.m_pRetVal == null) {
                    str = Generator.insertVariable(insertVariable4, "%RetVal%", "");
                }
                else {
                    str = Generator.insertVariable(insertVariable4, "%RetVal%", "return ");
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }
    
    private static String buildConstructor(final String s, final PGProc pgProc) {
        String strHelp = "";
        final StringBuffer sb = new StringBuffer(128);
        final PGProcDetail procDetail = pgProc.getProcDetail();
        if (procDetail.m_strHelp != null) {
            strHelp = procDetail.m_strHelp;
        }
        final String insertVariable = Generator.insertVariable(s, "%JavaDoc%", Generator.insertVariable(Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", strHelp), "%TblDesc%", Generator.buildSchemaDoc(procDetail.getParameters(), false)));
        for (int i = 0; i < procDetail.m_pParams.length; ++i) {
            final PGParam pgParam = procDetail.m_pParams[i];
            sb.append(", ");
            sb.append(Generator.mapper.proToNative(pgParam) + " " + pgParam.m_strName);
        }
        final String insertVariable2 = Generator.insertVariable(insertVariable, "%Params%", sb.toString());
        final StringBuffer sb2 = new StringBuffer(128);
        for (int j = 0; j < procDetail.m_pParams.length; ++j) {
            final PGParam pgParam2 = procDetail.m_pParams[j];
            sb2.append(", ");
            sb2.append(pgParam2.m_strName);
        }
        return Generator.insertVariable(insertVariable2, "%Params1%", sb2.toString());
    }
    
    private static String getDateTime() {
        return new GregorianCalendar().getTime().toString();
    }
}
