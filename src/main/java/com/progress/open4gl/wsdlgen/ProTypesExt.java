// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import javax.wsdl.extensions.ExtensibilityElement;

public class ProTypesExt implements ExtensibilityElement
{
    public static final QName Q_ELEM_TYPES_SCHEMA;
    protected QName elementType;
    protected Element schemaDef;
    
    public ProTypesExt() {
        this.elementType = ProTypesExt.Q_ELEM_TYPES_SCHEMA;
        this.schemaDef = null;
    }
    
    public void setElementType(final QName elementType) {
        this.elementType = elementType;
    }
    
    public QName getElementType() {
        return this.elementType;
    }
    
    public void setRequired(final Boolean b) {
    }
    
    public Boolean getRequired() {
        return new Boolean(false);
    }
    
    public void setSchemaDefinition(final Element schemaDef) {
        this.schemaDef = schemaDef;
    }
    
    public Element getSchemaDefinition() {
        return this.schemaDef;
    }
    
    static {
        Q_ELEM_TYPES_SCHEMA = new QName("http://www.w3.org/2001/XMLSchema", "schema");
    }
}
