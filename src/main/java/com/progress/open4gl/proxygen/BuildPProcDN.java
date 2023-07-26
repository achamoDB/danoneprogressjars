// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public class BuildPProcDN extends Generator implements DNPProcTemplate, DNImplTemplate
{
    protected static String buildMethods(final PGProc pgProc, final StringBuffer sb, final StringBuffer sb2) {
        final StringBuffer sb3 = new StringBuffer(16384);
        final boolean dnUseNullableTypes = PGProc.getGenInfo().getDNUseNullableTypes();
        final PGMethod[] internalProcs = pgProc.getProcDetail().getInternalProcs();
        for (int i = 0; i < internalProcs.length; ++i) {
            final PGMethod pgMethod = internalProcs[i];
            if (!pgMethod.isExcluded()) {
                final PGMethodDetail methodDetail = pgMethod.getMethodDetail();
                String s;
                if (methodDetail.getProcType() == 1) {
                    s = DNImplTemplate.szProcReturn;
                }
                else {
                    s = DNImplTemplate.szFuncReturn;
                }
                final String buildMethod = Generator.buildMethod(DNImplTemplate.szMethodBody, "\t\tbase.runProcedure(\"%ProProcName%\", %parms%%MetaSchema%);", false, true, methodDetail, pgMethod.getInternalProc(), sb, sb2);
                final PGParam returnValue = methodDetail.getReturnValue();
                String str;
                if (returnValue == null) {
                    str = Generator.insertVariable(buildMethod, "%MethodReturn%", "");
                }
                else if (returnValue.isExtentField()) {
                    str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(s, "%Cast%", Generator.mapper.proToNative(returnValue, dnUseNullableTypes)));
                }
                else {
                    final int unknownType = DotNetDataMapper.getUnknownType(returnValue, dnUseNullableTypes);
                    switch (returnValue.getParamType()) {
                        case 3: {
                            if (unknownType == 1) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValUnkBool);
                                break;
                            }
                            if (unknownType == 2) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValNullableBool);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValBool);
                            break;
                        }
                        case 4: {
                            if (unknownType == 1) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValUnkInt);
                                break;
                            }
                            if (unknownType == 2) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValNullableInt);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValInt);
                            break;
                        }
                        case 7:
                        case 41: {
                            if (unknownType == 1) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValUnkLong);
                                break;
                            }
                            if (unknownType == 2) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValNullableLong);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValLong);
                            break;
                        }
                        case 2:
                        case 34:
                        case 40: {
                            if (unknownType == 1) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValUnkDate);
                                break;
                            }
                            if (unknownType == 2) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValNullableDate);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValDate);
                            break;
                        }
                        case 5: {
                            if (unknownType == 1) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValUnkDecimal);
                                break;
                            }
                            if (unknownType == 2) {
                                str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValNullableDecimal);
                                break;
                            }
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", DNPProcTemplate.szRetValDecimal);
                            break;
                        }
                        default: {
                            str = Generator.insertVariable(buildMethod, "%MethodReturn%", Generator.insertVariable(s, "%Cast%", Generator.mapper.proToNative(returnValue, dnUseNullableTypes)));
                            break;
                        }
                    }
                }
                sb3.append(str);
            }
        }
        return sb3.toString();
    }
    
    protected static String BuildConstructor(final PGProc pgProc, final StringBuffer sb, final StringBuffer sb2) {
        final String string = pgProc.getProcPath().replace('\\', '/') + pgProc.getProcName() + "." + pgProc.getProcExt();
        pgProc.getProcDetail();
        return Generator.buildMethod(DNPProcTemplate.szConstructor, "\t\tPersistProcObj = RunPersistentProcedure(\"%ProProcName%\", %parms%%MetaSchema%);", true, true, pgProc.getProcDetail(), string, sb, sb2);
    }
}
