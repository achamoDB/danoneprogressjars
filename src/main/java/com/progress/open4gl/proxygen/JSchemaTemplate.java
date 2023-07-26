// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

class JSchemaTemplate implements ISchemaTemplate
{
    private static final String lineSep;
    
    public String getMetaSchemaSet() {
        return "\t\t%MetaSchemaName%.addResultSetSchema(%MetaDataName%, %Position%, %ParamType%);" + JSchemaTemplate.lineSep;
    }
    
    public String getDSMetaSchemaSet() {
        return "\t\t%MetaSchemaName%.addProDataGraphSchema(%MetaDataName%, %Position%, %ParamType% %mappedTT%);" + JSchemaTemplate.lineSep;
    }
    
    public String getStaticTTMetaData(final boolean b) {
        if (b) {
            return JSchemaTemplate.lineSep + "\tstatic ResultSetMetaData ";
        }
        return JSchemaTemplate.lineSep + "\tstatic ProDataObjectMetaData ";
    }
    
    public String getNewTTMetaData(final boolean b) {
        if (b) {
            return " = new ResultSetMetaData(0, ";
        }
        return " = new ProDataObjectMetaData(";
    }
    
    public String getStaticDataRelation() {
        return JSchemaTemplate.lineSep + "\tstatic ProDataRelationMetaData ";
    }
    
    public String getNewDataRelation() {
        return " = new ProDataRelationMetaData(";
    }
    
    public String getStaticDSMetaData() {
        return JSchemaTemplate.lineSep + "\tstatic ProDataGraphMetaData ";
    }
    
    public String getNewDSMetaData() {
        return " = new ProDataGraphMetaData(0, \"";
    }
    
    public String getGetOutParam() {
        return "\t\toutValue = params.getOutputParameter(%Position%);" + JSchemaTemplate.lineSep + "\t\t%ParamMap%" + JSchemaTemplate.lineSep;
    }
    
    public String getDTToDSMetaSchemaSet() {
        return "\t\t%DSMetaSchemaName%.addTable(%DTMetaDataName%);" + JSchemaTemplate.lineSep;
    }
    
    public String getPrimeIdxNaming(final boolean b) {
        if (b) {
            return null;
        }
        return ".setPrimeUniqueName(\"%PrimeIdxName%\");" + JSchemaTemplate.lineSep;
    }
    
    static {
        lineSep = System.getProperty("line.separator");
    }
}
