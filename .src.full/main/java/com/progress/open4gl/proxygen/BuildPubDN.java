// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.GregorianCalendar;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.util.ProgressVersion;
import java.io.PrintWriter;

public class BuildPubDN extends Generator implements DNPubTemplate
{
    public static void build(final PGAppObj pgAppObj, final PrintWriter printWriter) throws Open4GLException, FileNotFoundException {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String dnNamespace = genInfo.getDNNamespace();
        final StringBuffer sb = new StringBuffer(4096);
        final StringBuffer sb2 = new StringBuffer(4096);
        String s;
        if (pgAppObj.m_bSubObject) {
            s = Generator.extractTemplate(9);
        }
        else {
            s = Generator.extractTemplate(7);
        }
        final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(s, "%CreatedBy%", PGGenInfo.getResources().getTranString("PGPROXY_CreatedBy")), "%Version%", ProgressVersion.getVersionString()), "%Date%", getDateTime()), "%HelpString%", pgAppObj.getHelpString());
        final String author = genInfo.getAuthor();
        String s2;
        if (author != null && author.length() > 0) {
            s2 = Generator.insertVariable(insertVariable, "%DNAuthor%", "@author " + author);
        }
        else {
            s2 = Generator.insertVariable(insertVariable, "%DNAuthor%", "");
        }
        final String version = genInfo.getVersion();
        String s3;
        if (version != null && version.length() > 0) {
            s3 = Generator.insertVariable(s2, "%DNVersion%", "@version " + version);
        }
        else {
            s3 = Generator.insertVariable(s2, "%DNVersion%", "");
        }
        final String insertVariable2 = Generator.insertVariable(Generator.insertVariable(s3, "%Service%", genInfo.getServiceName()), "%SessionMode%", "IPoolProps_Fields.SM_SESSION_MANAGED");
        String s4;
        if (dnNamespace != null && dnNamespace.length() > 0) {
            s4 = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(insertVariable2, "%Namespace%", "namespace " + dnNamespace), "%StartNSParen%", "{"), "%EndNSParen%", "}");
        }
        else {
            s4 = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(insertVariable2, "%Namespace%", ""), "%StartNSParen%", ""), "%EndNSParen%", "");
        }
        printWriter.print(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(s4, "%ProxyGenVersion%", "1"), "%Methods%", buildMethods(pgAppObj, sb, sb2)), "%MetaDeclare%", sb.toString()), "%MetaStatic%", sb2.toString()), "%ObjectName%", pgAppObj.m_strAppObj));
    }
    
    public static void build(final PGProc pgProc, final PrintWriter printWriter) throws Open4GLException, IOException, FileNotFoundException {
        final String dnNamespace = PGAppObj.getGenInfo().getDNNamespace();
        final StringBuffer sb = new StringBuffer(4096);
        final StringBuffer sb2 = new StringBuffer(4096);
        final String template = Generator.extractTemplate(8);
        final PGProcDetail procDetail = pgProc.getProcDetail();
        final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(template, "%CreatedBy%", PGGenInfo.getResources().getTranString("PGPROXY_CreatedBy")), "%Version%", ProgressVersion.getVersionString()), "%Date%", getDateTime());
        String s;
        if (dnNamespace != null && dnNamespace.length() > 0) {
            s = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(insertVariable, "%Namespace%", "namespace " + dnNamespace), "%StartNSParen%", "{"), "%EndNSParen%", "}");
        }
        else {
            s = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(insertVariable, "%Namespace%", ""), "%StartNSParen%", ""), "%EndNSParen%", "");
        }
        printWriter.print(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(buildConstructor(s, pgProc, sb, sb2), "%Methods%", BuildPProcDN.buildMethods(pgProc, sb, sb2)), "%MetaDeclare%", sb.toString()), "%MetaStatic%", sb2.toString()), "%ObjectName%", procDetail.m_strMethod));
    }
    
    private static String buildMethods(final PGAppObj pgAppObj, final StringBuffer sb, final StringBuffer sb2) {
        final DotNetDataMapper dotNetDataMapper = new DotNetDataMapper();
        final StringBuffer sb3 = new StringBuffer(16384);
        final boolean dnUseNullableTypes = PGAppObj.getGenInfo().getDNUseNullableTypes();
        if (!pgAppObj.m_bSubObject) {
            for (int i = 0; i < pgAppObj.m_pSubObjs.length; ++i) {
                String strHelp = "";
                final PGAppObj pgAppObj2 = pgAppObj.m_pSubObjs[i];
                if (pgAppObj2.m_strHelp != null) {
                    strHelp = pgAppObj2.m_strHelp;
                }
                sb3.append(Generator.insertVariable(Generator.insertVariable(DNPubTemplate.szFactAODN, "%HelpString%", strHelp), "%ObjectName%", pgAppObj2.m_strAppObj));
            }
        }
        for (int j = 0; j < pgAppObj.m_pPerProcs.length; ++j) {
            final PGProcDetail procDetail = pgAppObj.m_pPerProcs[j].getProcDetail();
            String strHelp2 = "";
            if (procDetail.m_strHelp != null) {
                strHelp2 = procDetail.m_strHelp;
            }
            final String insertVariable = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(DNPubTemplate.szFactPODN, "%HelpString%", strHelp2), "%ObjectName%", procDetail.m_strMethod), "%Method%", procDetail.m_strMethod);
            final StringBuffer sb4 = new StringBuffer(128);
            for (int k = 0; k < procDetail.m_pParams.length; ++k) {
                final PGParam pgParam = procDetail.m_pParams[k];
                final int n = pgParam.m_enumType & 0xFF;
                if (k > 0) {
                    sb4.append(", ");
                }
                String str;
                if (n == 36 || n == 15) {
                    str = dotNetDataMapper.proToNative(pgParam, ((IPGStrongNameParam)pgParam).getStrongName());
                }
                else {
                    str = dotNetDataMapper.proToNative(pgParam, dnUseNullableTypes);
                }
                sb4.append(str + " " + pgParam.m_strName);
            }
            final String insertVariable2 = Generator.insertVariable(insertVariable, "%Params%", sb4.toString());
            final StringBuffer sb5 = new StringBuffer(128);
            for (int l = 0; l < procDetail.m_pParams.length; ++l) {
                final PGParam pgParam2 = procDetail.m_pParams[l];
                sb5.append(", ");
                sb5.append(dotNetDataMapper.getModifier(pgParam2.m_enumType) + pgParam2.m_strName);
            }
            sb3.append(Generator.insertVariable(insertVariable2, "%Params1%", sb5.toString()));
        }
        sb3.append(BuildImplDN.buildMethods(pgAppObj, sb, sb2));
        return sb3.toString();
    }
    
    private static String buildConstructor(final String s, final PGProc pgProc, final StringBuffer sb, final StringBuffer sb2) {
        final DotNetDataMapper dotNetDataMapper = new DotNetDataMapper();
        String strHelp = "";
        final boolean dnUseNullableTypes = PGProc.getGenInfo().getDNUseNullableTypes();
        final StringBuffer sb3 = new StringBuffer(128);
        final PGProcDetail procDetail = pgProc.getProcDetail();
        if (procDetail.m_strHelp != null) {
            strHelp = procDetail.m_strHelp;
        }
        final String insertVariable = Generator.insertVariable(s, "%JavaDoc%", Generator.insertVariable(Generator.insertVariable(StdTemplate.szJavaDoc, "%JDDesc%", strHelp), "%TblDesc%", Generator.buildSchemaDoc(procDetail.getParameters(), true)));
        for (int i = 0; i < procDetail.m_pParams.length; ++i) {
            final PGParam pgParam = procDetail.m_pParams[i];
            sb3.append(", ");
            final int n = pgParam.m_enumType & 0xFF;
            String str;
            if (n == 36 || n == 15) {
                str = dotNetDataMapper.proToNative(pgParam, ((IPGStrongNameParam)pgParam).getStrongName());
            }
            else {
                str = dotNetDataMapper.proToNative(pgParam, dnUseNullableTypes);
            }
            sb3.append(str + " " + pgParam.m_strName);
        }
        return Generator.insertVariable(Generator.insertVariable(insertVariable, "%Params%", sb3.toString()), "%Constructor%", BuildPProcDN.BuildConstructor(pgProc, sb, sb2));
    }
    
    private static String getDateTime() {
        return new GregorianCalendar().getTime().toString();
    }
}
