// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface JPProcTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = JPProcTemplate.lineSep + JPProcTemplate.lineSep;
    public static final String szConstructor = "//----Constructor" + JPProcTemplate.lineSep + "\tpublic %Name%Impl(ProObject appObj%Params%)" + JPProcTemplate.lineSep + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException\n" + "\t{" + JPProcTemplate.lineSep + "\t\tsuper(appObj);" + JPProcTemplate.lineSep2 + "\t\tObject outValue;\n\t\tParameterSet params = new ParameterSet(%NumParams%);\n\n" + StdTemplate.szMethodBodyParams + StdTemplate.szMethodBodySchema + "\t\t// Run procedure" + JPProcTemplate.lineSep + "%RunProc%" + JPProcTemplate.lineSep2 + StdTemplate.szMethodBodyOut + "\t}";
    public static final String szRunPersProc = "\t\tm_persistProc = runPersistentProcedure(\"%ProProcName%\", params%MetaSchema%);";
    public static final String szRunIntProc = "\t\trunProcedure(\"%ProProcName%\", params%MetaSchema%);";
    public static final String szRetValInt = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn ((Integer)(params.getFunctionReturnValue())).intValue();" + JPProcTemplate.lineSep;
    public static final String szArrayRetValInt = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn (int[])params.getFunctionReturnValue();" + JPProcTemplate.lineSep;
    public static final String szRetValBool = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn ((Boolean)(params.getFunctionReturnValue())).booleanValue();" + JPProcTemplate.lineSep;
    public static final String szArrayRetValBool = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn (boolean[])params.getFunctionReturnValue();" + JPProcTemplate.lineSep;
    public static final String szRetValLong = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn ((Long)(params.getFunctionReturnValue())).longValue();" + JPProcTemplate.lineSep;
    public static final String szArrayRetValLong = "\t\tObject retObj = params.getFunctionReturnValue();" + JPProcTemplate.lineSep + "\t\tif (retObj == null)" + JPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(m_badOutputVal, null);" + JPProcTemplate.lineSep + "\t\treturn (long[])params.getFunctionReturnValue();" + JPProcTemplate.lineSep;
}
