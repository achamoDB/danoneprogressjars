// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.IOException;
import com.progress.open4gl.Open4GLException;
import java.io.PrintWriter;

public class BuildImplJ extends Generator implements ImplTemplate
{
    public static void build(final PGAppObj pgAppObj, final PrintWriter printWriter) throws Open4GLException, IOException {
        final StringBuffer sb = new StringBuffer(4096);
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String package1 = genInfo.getPackage();
        Generator.mapper = new JavaDataMapper();
        Generator.schemaTemplate = new JSchemaTemplate();
        String s;
        if (pgAppObj.m_bSubObject) {
            s = Generator.extractTemplate(6);
        }
        else {
            s = Generator.extractTemplate(2);
        }
        final String insertVariable = Generator.insertVariable(s, "%ObjectName%", pgAppObj.m_strAppObj);
        String s2;
        if (package1 != null && package1.length() > 0) {
            s2 = Generator.insertVariable(insertVariable, "%Package%", "package " + package1 + ";");
        }
        else {
            s2 = Generator.insertVariable(insertVariable, "%Package%", "");
        }
        if (!pgAppObj.m_bSubObject) {
            s2 = Generator.insertVariable(s2, "%Service%", genInfo.getServiceName());
        }
        printWriter.print(Generator.insertVariable(Generator.insertVariable(s2, "%Methods%", buildMethods(pgAppObj, sb)), "%MetaData%", sb.toString()));
    }
    
    private static String buildMethods(final PGAppObj pgAppObj, final StringBuffer sb) {
        final StringBuffer sb2 = new StringBuffer(16384);
        for (int i = 0; i < pgAppObj.m_pProcs.length; ++i) {
            final PGProc pgProc = pgAppObj.m_pProcs[i];
            final PGProcDetail procDetail = pgProc.getProcDetail();
            final String buildMethod = Generator.buildMethod(ImplTemplate.szMethodBody, StdTemplate.szRunProc, false, false, pgProc.getProcDetail(), pgProc.getProcPath().replace('\\', '/') + pgProc.getProcName() + "." + pgProc.getProcExt(), sb);
            final PGParam returnValue = procDetail.getReturnValue();
            String str;
            if (returnValue == null) {
                str = Generator.insertVariable(buildMethod, "%MethodReturn%", "");
            }
            else {
                str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(ImplTemplate.szProcReturn, "%Cast%", Generator.mapper.proToNative(returnValue, null)));
            }
            sb2.append(str);
        }
        return sb2.toString();
    }
}
