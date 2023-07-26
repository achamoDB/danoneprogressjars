// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import org.apache.xml.serialize.XMLSerializer;
import java.util.Vector;

public class PGDataLink
{
    String m_strParentBuffer;
    String m_strChildBuffer;
    String m_strName;
    private Vector m_vParentFieldVector;
    private Vector m_vChildFieldVector;
    int m_child_ix;
    int m_parent_ix;
    int m_flag;
    
    public PGDataLink() {
        this.m_flag = 0;
        this.m_strParentBuffer = null;
        this.m_strChildBuffer = null;
        this.m_strName = null;
        this.m_vParentFieldVector = new Vector();
        this.m_vChildFieldVector = new Vector();
        this.m_child_ix = 0;
        this.m_parent_ix = 0;
    }
    
    public PGDataLink(final PGDataLink pgDataLink) {
        this.m_flag = pgDataLink.m_flag;
        this.m_strParentBuffer = pgDataLink.m_strParentBuffer;
        this.m_strChildBuffer = pgDataLink.m_strChildBuffer;
        this.m_strName = pgDataLink.m_strName;
        this.m_vParentFieldVector = pgDataLink.m_vParentFieldVector;
        this.m_vChildFieldVector = pgDataLink.m_vChildFieldVector;
        this.m_child_ix = pgDataLink.m_child_ix;
        this.m_parent_ix = pgDataLink.m_parent_ix;
    }
    
    public String getLinkName() {
        return this.m_strName;
    }
    
    public void setLinkName(final String strName) {
        this.m_strName = strName;
    }
    
    public String getParentBuffer() {
        return this.m_strParentBuffer;
    }
    
    public void setParentBuffer(final String s, final PGDataTableParam[] array) {
        this.m_strParentBuffer = PGAppObj.ProNameToProxyName(s);
        for (int length = array.length, i = 0; i < length; ++i) {
            if (this.m_strParentBuffer.equalsIgnoreCase(array[i].getParamName())) {
                this.m_parent_ix = i;
                break;
            }
        }
    }
    
    public String getChildBuffer() {
        return this.m_strChildBuffer;
    }
    
    public void setChildBuffer(final String s, final PGDataTableParam[] array) {
        this.m_strChildBuffer = PGAppObj.ProNameToProxyName(s);
        for (int length = array.length, i = 0; i < length; ++i) {
            if (this.m_strChildBuffer.equalsIgnoreCase(array[i].getParamName())) {
                this.m_child_ix = i;
                break;
            }
        }
    }
    
    public Vector getParentFieldVector() {
        return this.m_vParentFieldVector;
    }
    
    public Vector getChildFieldVector() {
        return this.m_vChildFieldVector;
    }
    
    public void addToParentFieldList(final String obj) {
        this.m_vParentFieldVector.addElement(obj);
    }
    
    public void addToChildFieldList(final String obj) {
        this.m_vChildFieldVector.addElement(obj);
    }
    
    public String getParentField(final int index) {
        if (index >= this.m_vParentFieldVector.size()) {
            return null;
        }
        return this.m_vParentFieldVector.elementAt(index);
    }
    
    public String getChildField(final int index) {
        if (index >= this.m_vChildFieldVector.size()) {
            return null;
        }
        return this.m_vChildFieldVector.elementAt(index);
    }
    
    public int numFieldPairs() {
        return this.m_vParentFieldVector.size();
    }
    
    public int getChildIndex() {
        return this.m_child_ix;
    }
    
    public void setChildIndex(final int child_ix) {
        this.m_child_ix = child_ix;
    }
    
    public int getParentIndex() {
        return this.m_parent_ix;
    }
    
    public void setParentIndex(final int parent_ix) {
        this.m_parent_ix = parent_ix;
    }
    
    public int getFlag() {
        return this.m_flag;
    }
    
    public void setFlag(final int flag) {
        this.m_flag = flag;
    }
    
    public String getPairsList() {
        String s = "";
        for (int i = 0; i < this.m_vParentFieldVector.size(); ++i) {
            final String str = this.m_vParentFieldVector.elementAt(i);
            final String str2 = this.m_vChildFieldVector.elementAt(i);
            if (i > 0) {
                s += ",";
            }
            s = s + str + "," + str2;
        }
        return s;
    }
    
    public boolean hasSameSchema(final PGDataLink pgDataLink) {
        final Vector parentFieldVector = pgDataLink.getParentFieldVector();
        final Vector childFieldVector = pgDataLink.getChildFieldVector();
        if (this.m_vParentFieldVector.size() != parentFieldVector.size()) {
            return false;
        }
        if (this.m_vChildFieldVector.size() != childFieldVector.size()) {
            return false;
        }
        for (int i = 0; i < this.m_vParentFieldVector.size(); ++i) {
            if (!((String)this.m_vParentFieldVector.elementAt(i)).equals(parentFieldVector.elementAt(i))) {
                return false;
            }
        }
        for (int j = 0; j < this.m_vChildFieldVector.size(); ++j) {
            if (!((String)this.m_vChildFieldVector.elementAt(j)).equals(childFieldVector.elementAt(j))) {
                return false;
            }
        }
        return true;
    }
    
    public void writeDataLinkXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        boolean b2 = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        String string;
        if (b) {
            string = str + ":DataLink";
        }
        else {
            string = "DataLink";
        }
        try {
            final AttributesImpl attributesImpl = new AttributesImpl();
            attributesImpl.addAttribute("", "Name", "Name", "CDATA", this.getLinkName());
            if ((this.m_flag & 0x2) == 0x2) {
                b2 = true;
            }
            attributesImpl.addAttribute("", "isNested", "isNested", "CDATA", b2 ? "true" : "false");
            attributesImpl.addAttribute("", "numFieldPairs", "numFieldPairs", "CDATA", Integer.toString(this.numFieldPairs()));
            xmlSerializer.startElement(s, "DataLink", string, (Attributes)attributesImpl);
            String string2;
            if (b) {
                string2 = str + ":ParentBuffer";
            }
            else {
                string2 = "ParentBuffer";
            }
            xmlSerializer.startElement(s, "ParentBuffer", string2, (Attributes)null);
            final String parentBuffer = this.getParentBuffer();
            ((BaseMarkupSerializer)xmlSerializer).characters(parentBuffer.toCharArray(), 0, parentBuffer.length());
            xmlSerializer.endElement(s, "ParentBuffer", string2);
            String string3;
            if (b) {
                string3 = str + ":ChildBuffer";
            }
            else {
                string3 = "ChildBuffer";
            }
            xmlSerializer.startElement(s, "ChildBuffer", string3, (Attributes)null);
            final String childBuffer = this.getChildBuffer();
            ((BaseMarkupSerializer)xmlSerializer).characters(childBuffer.toCharArray(), 0, childBuffer.length());
            xmlSerializer.endElement(s, "ChildBuffer", string3);
            String string4;
            if (b) {
                string4 = str + ":FieldPairs";
            }
            else {
                string4 = "FieldPairs";
            }
            xmlSerializer.startElement(s, "FieldPairs", string4, (Attributes)null);
            final String pairsList = this.getPairsList();
            ((BaseMarkupSerializer)xmlSerializer).characters(pairsList.toCharArray(), 0, pairsList.length());
            xmlSerializer.endElement(s, "FieldPairs", string4);
            String string5;
            if (b) {
                string5 = str + ":DataLink";
            }
            else {
                string5 = "DataLink";
            }
            xmlSerializer.endElement(s, "DataLink", string5);
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readDataLinkXML(final Node node, final PGDataTableParam[] array) {
        int i = 0;
        final NamedNodeMap attributes = node.getAttributes();
        this.setLinkName(attributes.getNamedItem("Name").getNodeValue());
        final Node namedItem = attributes.getNamedItem("numFieldPairs");
        if (namedItem != null) {
            i = new Integer(namedItem.getNodeValue());
        }
        final Node namedItem2 = attributes.getNamedItem("isNested");
        if (namedItem2 != null) {
            final String nodeValue = namedItem2.getNodeValue();
            if (nodeValue.equals("true") || nodeValue.equals("1")) {
                this.setFlag(2);
            }
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), j = 0; j < length; ++j) {
            final Node item = childNodes.item(j);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue2 = WsaParser.extractNodeValue(item);
                if (localName.equals("ParentBuffer")) {
                    this.setParentBuffer(nodeValue2, array);
                }
                else if (localName.equals("ChildBuffer")) {
                    this.setChildBuffer(nodeValue2, array);
                }
                else if (localName.equals("FieldPairs")) {
                    String substring = new String(nodeValue2);
                    while (i > 0) {
                        final int index = substring.indexOf(44);
                        this.addToParentFieldList(substring.substring(0, index));
                        String s;
                        if (i > 1) {
                            final String substring2 = substring.substring(index + 1);
                            final int index2 = substring2.indexOf(44);
                            s = substring2.substring(0, index2);
                            substring = substring2.substring(index2 + 1);
                        }
                        else {
                            s = substring.substring(index + 1);
                        }
                        this.addToChildFieldList(s);
                        --i;
                    }
                }
            }
        }
    }
}
