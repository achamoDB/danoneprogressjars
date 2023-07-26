// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface DNImplTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String szMethodBodyVars = "\t\tObject outValue;\n\t\tParameterSet parms = new ParameterSet(%NumParams%);\n\n";
    public static final String szMethodBody = DNStdTemplate.szMethodBodyStart + "\t{\n\t\tRqContext rqCtx = null;\n" + "\t\tif (isSessionAvailable() == false)\n\t\t\tthrow new Open4GLException(NotAvailable, null);\n\n" + "\t\tObject outValue;\n\t\tParameterSet parms = new ParameterSet(%NumParams%);\n\n" + DNStdTemplate.szMethodBodyParams + StdTemplate.szMethodBodySchema + DNStdTemplate.szMethodBodyRun + DNStdTemplate.szMethodBodyOut + "\t\tif (rqCtx != null) rqCtx._release();\n" + "%DSFillErrorString%\n\n" + "\t\t// Return output value\n%MethodReturn%\n\t}\n\n";
    public static final String szFuncReturn = "\t\treturn (%Cast%)(parms.FunctionReturnValue);" + DNImplTemplate.lineSep;
    public static final String szProcReturn = "\t\treturn (%Cast%)(parms.ProcedureReturnValue);" + DNImplTemplate.lineSep;
    public static final String szDSFillErrorString = DNImplTemplate.lineSep + "\t\tif (parms.DataSetFillErrors > 0)" + DNImplTemplate.lineSep + "\t\t\tthrow new Open4GLException(38, new System.Object[]{parms.DataSetFillErrorString});" + DNImplTemplate.lineSep;
}
