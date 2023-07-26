// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface ImplTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String szMethodBodyThrows = "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException\n";
    public static final String szMethodBodyVars = "\t\tObject outValue;\n\t\tParameterSet params = new ParameterSet(%NumParams%);\n\n";
    public static final String szMethodBodyHandleRqCtx = "\t\t// Session-Managed always returns null" + ImplTemplate.lineSep + "\t\tif (rqCtx != null)\n\t\t{\n\t\t\tif (!rqCtx._isStreaming())\n\t\t\t\trqCtx._release();" + ImplTemplate.lineSep + "\t\t\telse\n\t\t\t{" + ImplTemplate.lineSep + "\t\t\t\t// If set, there's a ResultSetHolder parm" + ImplTemplate.lineSep + "\t\t\t\tlastResultSet = %LastResultSetHolder%;" + ImplTemplate.lineSep + "\t\t\t\tif (lastResultSet != null)\n\t\t\t\t\tlastResultSet.setRqContext(rqCtx);" + ImplTemplate.lineSep + "\t\t\t}\n\t\t}\n" + ImplTemplate.lineSep;
    public static final String szMetaSchemaNew = "\t\tMetaSchema %SchemaName% = new MetaSchema();";
    public static final String szMethodBody = StdTemplate.szMethodBodyStart + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException\n" + "\t{\n\t\tRqContext rqCtx = null;\n" + "\t\tcom.progress.open4gl.dynamicapi.ResultSet lastResultSet = null;\n\n" + "\t\tif (isSessionAvailable() == false)\n\t\t\tthrow new Open4GLException(m_notAvailable, null);\n\n" + "\t\tObject outValue;\n\t\tParameterSet params = new ParameterSet(%NumParams%);\n\n" + StdTemplate.szMethodBodyParams + StdTemplate.szMethodBodySchema + StdTemplate.szMethodBodyRun + StdTemplate.szMethodBodyOut + ImplTemplate.szMethodBodyHandleRqCtx + "\t\t// Return output value\n%MethodReturn%\n\t}\n\n";
    public static final String szFuncReturn = "\t\treturn (%Cast%)(params.getFunctionReturnValue());" + ImplTemplate.lineSep;
    public static final String szProcReturn = "\t\treturn (%Cast%)(params.getProcedureReturnValue());" + ImplTemplate.lineSep;
}
