// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.io.Serializable;

public class PGVersion implements Serializable
{
    private static final long serialVersionUID = -353150811241142176L;
    public static final String pxgVersion = "04";
    String m_strVersion;
    
    public PGVersion() {
        this.m_strVersion = "04";
    }
    
    public String getPxgVersion() {
        return this.m_strVersion;
    }
    
    public void setCurrentPxgVersion() {
        this.m_strVersion = "04";
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strVersion != null) {
                String string;
                if (b) {
                    string = str + ":PXGVersion";
                }
                else {
                    string = "PXGVersion";
                }
                xmlSerializer.startElement(s, "PXGVersion", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strVersion.toCharArray(), 0, this.m_strVersion.length());
                xmlSerializer.endElement(s, "PXGVersion", string);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("PXGVersion")) {
                    this.m_strVersion = nodeValue;
                }
            }
        }
    }
}
