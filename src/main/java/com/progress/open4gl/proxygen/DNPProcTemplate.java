// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface DNPProcTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = DNPProcTemplate.lineSep + DNPProcTemplate.lineSep;
    public static final String szConstructor = DNPProcTemplate.lineSep + "\t\tObject outValue;\n\t\tParameterSet parms = new ParameterSet(%NumParams%);\n\n" + DNStdTemplate.szMethodBodyParams + StdTemplate.szMethodBodySchema + "\t\t// Run procedure" + DNPProcTemplate.lineSep + "%RunProc%" + DNPProcTemplate.lineSep2 + DNStdTemplate.szMethodBodyOut;
    public static final String szRunPersProc = "\t\tPersistProcObj = RunPersistentProcedure(\"%ProProcName%\", %parms%%MetaSchema%);";
    public static final String szRunIntProc = "\t\tbase.runProcedure(\"%ProProcName%\", %parms%%MetaSchema%);";
    public static final String szRunIntProcWithMetaSchema = "\t\tbase.runProcedure(\"%ProProcName%\", %parms%%MetaSchema%);";
    public static final String szRetValInt = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\tif (retObj == null)" + DNPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(BadOutputVal, null);" + DNPProcTemplate.lineSep + "\t\treturn (int) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValUnkInt = "\t\tIntHolder myHolder = new IntHolder();" + DNPProcTemplate.lineSep + "\t\tmyHolder.Value = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn myHolder;" + DNPProcTemplate.lineSep;
    public static final String szRetValNullableInt = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn (int?) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValBool = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\tif (retObj == null)" + DNPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(BadOutputVal, null);" + DNPProcTemplate.lineSep + "\t\treturn (bool) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValUnkBool = "\t\tBooleanHolder myHolder = new BooleanHolder();" + DNPProcTemplate.lineSep + "\t\tmyHolder.Value = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn myHolder;" + DNPProcTemplate.lineSep;
    public static final String szRetValNullableBool = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn (bool?) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValLong = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\tif (retObj == null)" + DNPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(BadOutputVal, null);" + DNPProcTemplate.lineSep + "\t\treturn (long) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValUnkLong = "\t\tLongHolder myHolder = new LongHolder();" + DNPProcTemplate.lineSep + "\t\tmyHolder.Value = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn myHolder;" + DNPProcTemplate.lineSep;
    public static final String szRetValNullableLong = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn (long?) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValDate = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\tif (retObj == null)" + DNPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(BadOutputVal, null);" + DNPProcTemplate.lineSep + "\t\treturn (System.DateTime) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValUnkDate = "\t\tDateHolder myHolder = new DateHolder();" + DNPProcTemplate.lineSep + "\t\tmyHolder.Value = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn myHolder;" + DNPProcTemplate.lineSep;
    public static final String szRetValNullableDate = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn (System.DateTime?) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValDecimal = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\tif (retObj == null)" + DNPProcTemplate.lineSep + "\t\t\tthrow new Open4GLException(BadOutputVal, null);" + DNPProcTemplate.lineSep + "\t\treturn (decimal) retObj;" + DNPProcTemplate.lineSep;
    public static final String szRetValUnkDecimal = "\t\tDecimalHolder myHolder = new DecimalHolder();" + DNPProcTemplate.lineSep + "\t\tmyHolder.Value = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn myHolder;" + DNPProcTemplate.lineSep;
    public static final String szRetValNullableDecimal = "\t\tObject retObj = parms.FunctionReturnValue;" + DNPProcTemplate.lineSep + "\t\treturn (decimal?) retObj;" + DNPProcTemplate.lineSep;
}
