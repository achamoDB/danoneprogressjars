// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface StdTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = StdTemplate.lineSep + StdTemplate.lineSep;
    public static final String szRunProc = "\t\trqCtx = runProcedure(\"%ProProcName%\", params%MetaSchema%);" + StdTemplate.lineSep;
    public static final String szStaticInit = "%MetaDeclare%" + StdTemplate.lineSep2 + "\tstatic" + StdTemplate.lineSep + "\t{" + StdTemplate.lineSep + "%MetaStatic%" + StdTemplate.lineSep + "\t}" + StdTemplate.lineSep;
    public static final String szJavaDoc = "\t/**" + StdTemplate.lineSep + "\t*\t%JDDesc%" + StdTemplate.lineSep + "\t*\t%TblDesc%" + StdTemplate.lineSep + "\t*/";
    public static final String szMethodBodyStart = "\t/* %HelpString%" + StdTemplate.lineSep + "\t*/" + StdTemplate.lineSep + "\tpublic %Return% %Name%(%Params%)" + StdTemplate.lineSep;
    public static final String szMethodBodyTrace = "\t\tif (RunTimeProperties.isTracing())" + StdTemplate.lineSep + "\t\t{" + StdTemplate.lineSep + "\t\t\tTracer tracer = RunTimeProperties.tracer;" + StdTemplate.lineSep + "%TraceParams%\t\t}" + StdTemplate.lineSep2;
    public static final String szMethodBodyParams = "\t\t// Set up input parameters" + StdTemplate.lineSep + "%InParams%" + StdTemplate.lineSep2 + "\t\t// Set up input/output parameters" + StdTemplate.lineSep + "%InOutParams%" + StdTemplate.lineSep2 + "\t\t// Set up Out parameters" + StdTemplate.lineSep + "%OutParams%" + StdTemplate.lineSep2;
    public static final String szMethodBodySchema = "\t\t// Setup local MetaSchema if any params are tables" + StdTemplate.lineSep + "%NewMetaSchema%" + StdTemplate.lineSep + "%AddSchemas%" + StdTemplate.lineSep2;
    public static final String szMethodBodyRun = "\t\t// Set up return type" + StdTemplate.lineSep + "\t\t%SetRetType%" + StdTemplate.lineSep2 + "\t\t// Run procedure" + StdTemplate.lineSep + "%RunProc%" + StdTemplate.lineSep2;
    public static final String szMethodBodyOut = "\t\t// Get output parameters" + StdTemplate.lineSep + "%GetOut%" + StdTemplate.lineSep2;
    public static final String szTraceParam = "\t\t\ttracer.print(\"%traceName%: \" + %traceVal%);" + StdTemplate.lineSep;
}
