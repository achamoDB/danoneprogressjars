// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.IOException;
import com.progress.open4gl.Open4GLException;
import java.io.PrintWriter;

public class BuildPProcJ extends Generator implements JPProcTemplate, ImplTemplate
{
    public static void build(final PGProc pgProc, final String str, final PrintWriter printWriter) throws Open4GLException, IOException {
        final StringBuffer sb = new StringBuffer(4096);
        final String insertVariable = Generator.insertVariable(Generator.extractTemplate(4), "%ObjectName%", pgProc.getProcDetail().getMethodName());
        String s;
        if (str != null && str.length() > 0) {
            s = Generator.insertVariable(insertVariable, "%Package%", "package " + str + ";");
        }
        else {
            s = Generator.insertVariable(insertVariable, "%Package%", "");
        }
        printWriter.print(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(s, "%Constructor%", BuildConstructor(pgProc, sb)), "%Methods%", buildMethods(pgProc, sb)), "%MetaData%", sb.toString()));
    }
    
    private static String buildMethods(final PGProc pgProc, final StringBuffer sb) {
        final StringBuffer sb2 = new StringBuffer(16384);
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                final PGMethodDetail methodDetail = pgMethod.getMethodDetail();
                String s;
                if (methodDetail.getProcType() == 1) {
                    s = ImplTemplate.szProcReturn;
                }
                else {
                    s = ImplTemplate.szFuncReturn;
                }
                final String buildMethod = Generator.buildMethod(ImplTemplate.szMethodBody, "\t\trunProcedure(\"%ProProcName%\", params%MetaSchema%);", false, false, methodDetail, pgMethod.getInternalProc(), sb);
                final PGParam returnValue = methodDetail.getReturnValue();
                String str = null;
                if (returnValue == null) {
                    str = Generator.insertVariable(buildMethod, "%MethodReturn%", "");
                }
                else if (returnValue.effectiveAllowUnknown()) {
                    str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(s, "%Cast%", Generator.mapper.proToNative(returnValue)));
                }
                else {
                    switch (returnValue.getParamType()) {
                        case 3: {
                            if (returnValue.isExtentField()) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szArrayRetValBool);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szRetValBool);
                            break;
                        }
                        case 4: {
                            if (returnValue.isExtentField()) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szArrayRetValInt);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szRetValInt);
                            break;
                        }
                        case 7:
                        case 41: {
                            if (returnValue.isExtentField()) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szArrayRetValLong);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", JPProcTemplate.szRetValLong);
                            break;
                        }
                        default: {
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(s, "%Cast%", Generator.mapper.proToNative(returnValue)));
                            break;
                        }
                    }
                }
                sb2.append(str);
            }
        }
        return sb2.toString();
    }
    
    private static String BuildConstructor(final PGProc pgProc, final StringBuffer sb) {
        return Generator.buildMethod(JPProcTemplate.szConstructor, "\t\tm_persistProc = runPersistentProcedure(\"%ProProcName%\", params%MetaSchema%);", true, false, pgProc.getProcDetail(), pgProc.getProcPath().replace('\\', '/') + pgProc.getProcName() + "." + pgProc.getProcExt(), sb);
    }
}
