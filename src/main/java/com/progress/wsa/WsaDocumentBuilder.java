// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WsaDocumentBuilder
{
    private static DocumentBuilderFactory dbf;
    
    public static synchronized DocumentBuilder getXMLDocBuilder() throws IllegalArgumentException {
        try {
            return WsaDocumentBuilder.dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
            throw new IllegalArgumentException(ex.toString());
        }
    }
    
    static {
        WsaDocumentBuilder.dbf = null;
        (WsaDocumentBuilder.dbf = DocumentBuilderFactory.newInstance()).setAttribute("http://apache.org/xml/features/disallow-doctype-decl", new Boolean(true));
        WsaDocumentBuilder.dbf.setNamespaceAware(true);
        WsaDocumentBuilder.dbf.setValidating(false);
    }
}
