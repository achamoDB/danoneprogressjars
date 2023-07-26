// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface DNPubTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = DNPubTemplate.lineSep + DNPubTemplate.lineSep;
    public static final String szMethodDN = "\t/// <summary>" + DNPubTemplate.lineSep + "\t/// %HelpString%" + DNPubTemplate.lineSep + "\t/// </summary> " + DNPubTemplate.lineSep + "\tpublic %Return% %Method%(%Params%)" + DNPubTemplate.lineSep + "\t{" + DNPubTemplate.lineSep + "\t\t%RetVal%%Method%(%Params1%);" + DNPubTemplate.lineSep + "\t}" + DNPubTemplate.lineSep2;
    public static final String szFactPODN = "\t/// <summary>" + DNPubTemplate.lineSep + "\t/// %HelpString%" + DNPubTemplate.lineSep + "\t/// </summary> " + DNPubTemplate.lineSep + "\tpublic %ObjectName% CreatePO_%Method%(%Params%)" + DNPubTemplate.lineSep + "\t{" + DNPubTemplate.lineSep + "\t\treturn new %ObjectName%(this%Params1%);" + DNPubTemplate.lineSep + "\t}" + DNPubTemplate.lineSep2;
    public static final String szFactAODN = DNPubTemplate.lineSep + "\t/// <summary>" + DNPubTemplate.lineSep + "\t/// %HelpString%" + DNPubTemplate.lineSep + "\t/// </summary> " + DNPubTemplate.lineSep + "\tpublic %ObjectName% CreateAO_%ObjectName%()" + DNPubTemplate.lineSep + "\t{" + DNPubTemplate.lineSep + "\t\treturn new %ObjectName%(this);" + DNPubTemplate.lineSep + "\t}" + DNPubTemplate.lineSep2;
    public static final String szMethodPODN = "\t/// <summary>" + DNPubTemplate.lineSep + "\t/// %HelpString%" + DNPubTemplate.lineSep + "\t/// </summary> " + DNPubTemplate.lineSep + "\tpublic %Return% %Method%(%Params%)" + DNPubTemplate.lineSep + "\t{" + DNPubTemplate.lineSep + "\t\t%RetVal%%Method%(%Params1%);" + DNPubTemplate.lineSep + "\t}" + DNPubTemplate.lineSep2;
}
