// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface DNStdTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = DNStdTemplate.lineSep + DNStdTemplate.lineSep;
    public static final String szRunProc = "\t\trqCtx = runProcedure(\"%ProProcName%\", %parms%%MetaSchema%);" + DNStdTemplate.lineSep;
    public static final String szMetaDeclareInit = "%MetaDeclare%" + DNStdTemplate.lineSep2;
    public static final String szMetaStaticInit = "%MetaStatic%" + DNStdTemplate.lineSep;
    public static final String szJavaDoc = "\t/**" + DNStdTemplate.lineSep + "\t*\t%JDDesc%" + DNStdTemplate.lineSep + "\t*\t%TblDesc%" + DNStdTemplate.lineSep + "\t*/";
    public static final String szMethodBodyStart = "/// <summary>" + DNStdTemplate.lineSep + "\t/// %HelpString%" + DNStdTemplate.lineSep + "\t/// </summary> " + DNStdTemplate.lineSep + "\tpublic %Return% %Name%(%Params%)" + DNStdTemplate.lineSep;
    public static final String szMethodBodyTrace = "\t\tif (RunTimeProperties.isTracing())" + DNStdTemplate.lineSep + "\t\t{" + DNStdTemplate.lineSep + "\t\t\tTracer tracer = RunTimeProperties.tracer;" + DNStdTemplate.lineSep + "%TraceParams%\t\t}" + DNStdTemplate.lineSep2;
    public static final String szMethodBodyParams = "\t\t// Set up input parameters" + DNStdTemplate.lineSep + "%InParams%" + DNStdTemplate.lineSep2 + "\t\t// Set up input/output parameters" + DNStdTemplate.lineSep + "%InOutParams%" + DNStdTemplate.lineSep2 + "\t\t// Set up Out parameters" + DNStdTemplate.lineSep + "%OutParams%" + DNStdTemplate.lineSep2;
    public static final String szMethodBodyRun = "\t\t// Set up return type" + DNStdTemplate.lineSep + "\t\t%SetRetType%" + DNStdTemplate.lineSep2 + "\t\t// Run procedure" + DNStdTemplate.lineSep + "%RunProc%" + DNStdTemplate.lineSep2;
    public static final String szMethodBodyOut = "\t\t// Get output parameters" + DNStdTemplate.lineSep + "%GetOut%" + DNStdTemplate.lineSep2;
    public static final String szMethodBodyExtentHolderOut = "if (outValue == null)" + DNStdTemplate.lineSep + "\t\t\t%ParamName% = null;" + DNStdTemplate.lineSep + "\t\telse" + DNStdTemplate.lineSep + "\t\t{" + DNStdTemplate.lineSep + "\t\t\t%TypeName%[] %ParamName%ArrayVal = (%TypeName%[]) outValue;" + DNStdTemplate.lineSep + "\t\t\t%ParamName% = new %TypeHolderName%[%ParamName%ArrayVal.Length];" + DNStdTemplate.lineSep + "\t\t\tfor (int i = 0; i < %ParamName%ArrayVal.Length; i++)" + DNStdTemplate.lineSep + "\t\t\t{" + DNStdTemplate.lineSep + "\t\t\t\t%ParamName%[i] = new %TypeHolderName%();" + DNStdTemplate.lineSep + "\t\t\t\t%ParamName%[i].Value = %ParamName%ArrayVal[i];" + DNStdTemplate.lineSep + "\t\t\t}" + DNStdTemplate.lineSep + "\t\t}" + DNStdTemplate.lineSep;
    public static final String szDataColumns = "\t\tparentCols = new DataColumn[%NumFieldPairs%];" + DNStdTemplate.lineSep + "\t\tchildCols  = new DataColumn[%NumFieldPairs%];" + DNStdTemplate.lineSep;
    public static final String szParentCol = "\t\tparentCols[%Index%] = ds.Tables[\"%ParentBuffer%\"].Columns[\"%ParentColumnName%\"];" + DNStdTemplate.lineSep;
    public static final String szChildCol = "\t\tchildCols[%Index%] = ds.Tables[\"%ChildBuffer%\"].Columns[\"%ChildColumnName%\"];" + DNStdTemplate.lineSep;
    public static final String szDataLink = "\t\tdrel = new DataRelation(\"%RelationName%\", parentCols, childCols, false);" + DNStdTemplate.lineSep + "\t\tds.Relations.Add(drel);" + DNStdTemplate.lineSep2;
}
