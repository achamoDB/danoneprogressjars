// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.apache.xml.serialize.XMLSerializer;

public interface XMLSerializable
{
    void writeXML(final XMLSerializer p0, final String p1, final String p2) throws SAXException;
    
    void readXML(final Node p0);
}
