// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public class BuildImplDN extends Generator implements DNImplTemplate
{
    protected static String buildMethods(final PGAppObj pgAppObj, final StringBuffer sb, final StringBuffer sb2) {
        final StringBuffer sb3 = new StringBuffer(16384);
        final boolean dnUseNullableTypes = PGAppObj.getGenInfo().getDNUseNullableTypes();
        Generator.mapper = new DotNetDataMapper();
        Generator.schemaTemplate = new DNSchemaTemplate();
        for (int i = 0; i < pgAppObj.m_pProcs.length; ++i) {
            final PGProc pgProc = pgAppObj.m_pProcs[i];
            final PGProcDetail procDetail = pgProc.getProcDetail();
            if (procDetail.m_strHelp != null) {
                final String strHelp = procDetail.m_strHelp;
            }
            final String buildMethod = Generator.buildMethod(DNImplTemplate.szMethodBody, DNStdTemplate.szRunProc, false, true, pgProc.getProcDetail(), pgProc.getProcPath().replace('\\', '/') + pgProc.getProcName() + "." + pgProc.getProcExt(), sb, sb2);
            final PGParam returnValue = procDetail.getReturnValue();
            String str;
            if (returnValue == null) {
                str = Generator.insertVariable(buildMethod, "%MethodReturn%", "");
            }
            else {
                str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(DNImplTemplate.szProcReturn, "%Cast%", Generator.mapper.proToNative(returnValue, dnUseNullableTypes)));
            }
            sb3.append(str);
        }
        return sb3.toString();
    }
}
