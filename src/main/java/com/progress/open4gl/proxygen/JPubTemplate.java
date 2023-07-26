// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface JPubTemplate
{
    public static final String lineSep = System.getProperty("line.separator");
    public static final String lineSep2 = JPubTemplate.lineSep + JPubTemplate.lineSep;
    public static final String szMethodJ = "%HelpString%" + JPubTemplate.lineSep + "\tpublic %Return% %Method%(%Params%)" + JPubTemplate.lineSep + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException" + JPubTemplate.lineSep + "\t{" + JPubTemplate.lineSep + "\t\t%RetVal%m_%ObjectName%Impl.%Method%(%Params1%);" + JPubTemplate.lineSep + "\t}" + JPubTemplate.lineSep2;
    public static final String szFactPOJ = "%HelpString%" + JPubTemplate.lineSep + "\tpublic %ObjectName% createPO_%Method%(%Params%)" + JPubTemplate.lineSep + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException" + JPubTemplate.lineSep + "\t{" + JPubTemplate.lineSep + "\t\treturn new %ObjectName%(m_%ParentName%Impl%Params1%);" + JPubTemplate.lineSep + "\t}" + JPubTemplate.lineSep2;
    public static final String szFactAOJ = "%HelpString%" + JPubTemplate.lineSep + "\tpublic %ObjectName% createAO_%ObjectName%()" + JPubTemplate.lineSep + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException" + JPubTemplate.lineSep + "\t{" + JPubTemplate.lineSep + "\t\treturn new %ObjectName%(m_%ParentName%Impl);" + JPubTemplate.lineSep + "\t}" + JPubTemplate.lineSep2;
    public static final String szMethodPOJ = "%HelpString%" + JPubTemplate.lineSep + "\tpublic %Return% %Method%(%Params%)" + JPubTemplate.lineSep + "\t\tthrows Open4GLException, RunTime4GLException, SystemErrorException" + JPubTemplate.lineSep + "\t{" + JPubTemplate.lineSep + "\t\t%RetVal%m_%ObjectName%Impl.%Method%(%Params1%);" + JPubTemplate.lineSep + "\t}" + JPubTemplate.lineSep2;
}
