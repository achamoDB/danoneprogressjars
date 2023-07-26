// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

class DNSchemaTemplate implements ISchemaTemplate
{
    private static final String lineSep;
    
    public String getMetaSchemaSet() {
        return "\t\t%MetaSchemaName%.addDataTableSchema(%MetaDataName%, %Position%, %ParamType%);" + DNSchemaTemplate.lineSep;
    }
    
    public String getDSMetaSchemaSet() {
        return "\t\t%MetaSchemaName%.addDataSetSchema(%MetaDataName%, %Position%, %ParamType%);" + DNSchemaTemplate.lineSep;
    }
    
    public String getStaticTTMetaData(final boolean b) {
        return DNSchemaTemplate.lineSep + "\tstatic DataTableMetaData ";
    }
    
    public String getNewTTMetaData(final boolean b) {
        return " = new DataTableMetaData(0, ";
    }
    
    public String getStaticDataRelation() {
        return DNSchemaTemplate.lineSep + "\tstatic DataRelationMetaData ";
    }
    
    public String getNewDataRelation() {
        return " = new DataRelationMetaData(";
    }
    
    public String getStaticDSMetaData() {
        return DNSchemaTemplate.lineSep + "\tstatic DataSetMetaData ";
    }
    
    public String getNewDSMetaData() {
        return " = new DataSetMetaData(0, \"";
    }
    
    public String getGetOutParam() {
        return "\t\toutValue = parms.getOutputParameter(%Position%);" + DNSchemaTemplate.lineSep + "\t\t%ParamMap%" + DNSchemaTemplate.lineSep;
    }
    
    public String getDTToDSMetaSchemaSet() {
        return "\t\t%DSMetaSchemaName%.addDataTable(%DTMetaDataName%);" + DNSchemaTemplate.lineSep;
    }
    
    public String getPrimeIdxNaming(final boolean b) {
        return null;
    }
    
    static {
        lineSep = System.getProperty("line.separator");
    }
}
