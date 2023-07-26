// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.progress.wsa.admin.WsaParser;
import java.util.Vector;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import org.apache.xml.serialize.XMLSerializer;

public class PGDataTableParam extends PGParam implements IPGStrongNameParam
{
    int m_unique_id;
    int m_flag;
    String m_strPrime_ix_cols;
    String m_strMulti_ix_cols;
    String m_strClassName;
    String m_strNamespace;
    String m_strProcName;
    boolean m_bBeforeTable;
    boolean m_bUniquePrimaryKey;
    String[] m_strPrimeKeyColNames;
    String[][] m_strNonPrimeKeyIndexes;
    String[] m_strNonPrimeKeyIndexNames;
    String m_strPrimeKeyIndexName;
    boolean m_bTopMatch;
    int m_sameParamNameIndex;
    String m_strXmlNodeName;
    boolean m_bReferenceOnly;
    
    public PGDataTableParam() {
        this.m_unique_id = 0;
        this.m_flag = 0;
        this.m_strPrime_ix_cols = null;
        this.m_strClassName = null;
        this.m_strNamespace = null;
        this.m_strProcName = null;
        this.m_bTopMatch = false;
        this.m_bBeforeTable = false;
        this.m_sameParamNameIndex = 0;
        this.m_bUniquePrimaryKey = false;
        this.m_strPrimeKeyColNames = null;
        this.m_strNonPrimeKeyIndexes = null;
        this.m_strPrimeKeyIndexName = null;
        this.m_strNonPrimeKeyIndexNames = null;
        this.m_bReferenceOnly = false;
    }
    
    public PGDataTableParam(final PGDataTableParam pgDataTableParam) {
        super(pgDataTableParam);
        this.m_unique_id = pgDataTableParam.m_unique_id;
        this.m_flag = pgDataTableParam.m_flag;
        this.m_strPrime_ix_cols = pgDataTableParam.m_strPrime_ix_cols;
        this.m_strClassName = pgDataTableParam.m_strClassName;
        this.m_strNamespace = pgDataTableParam.m_strNamespace;
        this.m_strProcName = pgDataTableParam.m_strProcName;
        this.m_bTopMatch = pgDataTableParam.m_bTopMatch;
        this.m_sameParamNameIndex = pgDataTableParam.m_sameParamNameIndex;
        this.m_bBeforeTable = pgDataTableParam.m_bBeforeTable;
        this.m_bUniquePrimaryKey = pgDataTableParam.m_bUniquePrimaryKey;
        this.m_strPrimeKeyColNames = pgDataTableParam.m_strPrimeKeyColNames;
        this.m_strNonPrimeKeyIndexes = pgDataTableParam.m_strNonPrimeKeyIndexes;
        this.m_strPrimeKeyIndexName = pgDataTableParam.m_strPrimeKeyIndexName;
        this.m_strNonPrimeKeyIndexNames = pgDataTableParam.m_strNonPrimeKeyIndexNames;
        this.m_bReferenceOnly = pgDataTableParam.m_bReferenceOnly;
    }
    
    public PGDataTableParam(final PGParam pgParam) {
        super(pgParam);
    }
    
    public int getUniqueID() {
        return this.m_unique_id;
    }
    
    void setUniqueID(final int unique_id) {
        this.m_unique_id = unique_id;
    }
    
    public int getFlag() {
        return this.m_flag;
    }
    
    void setFlag(final int flag) {
        this.m_flag = flag;
    }
    
    public String getClassName() {
        return this.m_strClassName;
    }
    
    public void setClassName(final String strClassName) {
        this.m_strClassName = strClassName;
    }
    
    public void setClassNameForWS(final String strClassName) {
        this.m_strClassName = strClassName;
    }
    
    public String getProcName() {
        return this.m_strProcName;
    }
    
    public void setProcName(final String strProcName) {
        this.m_strProcName = strProcName;
    }
    
    public String getNamespace() {
        return this.m_strNamespace;
    }
    
    public void setNamespace(final String strNamespace) {
        this.m_strNamespace = strNamespace;
    }
    
    public void setTopMatch(final boolean bTopMatch) {
        this.m_bTopMatch = bTopMatch;
    }
    
    public boolean isTopMatch() {
        return this.m_bTopMatch;
    }
    
    public void setBeforeTable(final boolean bBeforeTable) {
        this.m_bBeforeTable = bBeforeTable;
    }
    
    public boolean hasBeforeTable() {
        return this.m_bBeforeTable;
    }
    
    public boolean hasUniquePrimaryKey() {
        return this.m_bUniquePrimaryKey;
    }
    
    public String[] getPrimeKeyColNames() {
        return this.m_strPrimeKeyColNames;
    }
    
    public String[][] getNonPrimeKeyIndexes() {
        return this.m_strNonPrimeKeyIndexes;
    }
    
    public String getPrimeKeyIndexName() {
        return this.m_strPrimeKeyIndexName;
    }
    
    public String[] getNonPrimeKeyIndexNames() {
        return this.m_strNonPrimeKeyIndexNames;
    }
    
    public String getNonPrimeKeyIndexName(final int n) {
        return this.m_strNonPrimeKeyIndexNames[n];
    }
    
    public void setSameParamNameIndex(final int sameParamNameIndex) {
        this.m_sameParamNameIndex = sameParamNameIndex;
    }
    
    public int getSameParamNameIndex() {
        return this.m_sameParamNameIndex;
    }
    
    public String getPrimeIndexCols() {
        return this.m_strPrime_ix_cols;
    }
    
    public String getMultiIndexCols() {
        return this.m_strMulti_ix_cols;
    }
    
    public String getXmlNodeName() {
        return this.m_strXmlNodeName;
    }
    
    public void setXmlNodeName(final String strXmlNodeName) {
        this.m_strXmlNodeName = strXmlNodeName;
    }
    
    public boolean getReferenceOnly() {
        return this.m_bReferenceOnly;
    }
    
    public void setReferenceOnly(final boolean bReferenceOnly) {
        this.m_bReferenceOnly = bReferenceOnly;
    }
    
    void setIndexCols(String strMulti_ix_cols) {
        int n = 1;
        this.m_strMulti_ix_cols = strMulti_ix_cols;
        if (strMulti_ix_cols == null || strMulti_ix_cols.length() == 0) {
            return;
        }
        strMulti_ix_cols.length();
        if (strMulti_ix_cols.charAt(0) == '0' || strMulti_ix_cols.charAt(0) == '1') {
            final int index = strMulti_ix_cols.indexOf(":");
            if (index == -1) {
                this.setPrimeIndexCols(strMulti_ix_cols, "Idx" + Integer.toString(n++));
                strMulti_ix_cols = null;
            }
            else {
                final String substring = strMulti_ix_cols.substring(0, index);
                strMulti_ix_cols = strMulti_ix_cols.substring(index + 1);
                final int index2 = strMulti_ix_cols.indexOf(".");
                if (index2 == -1) {
                    this.setPrimeIndexCols(substring, strMulti_ix_cols);
                    strMulti_ix_cols = null;
                }
                else {
                    this.setPrimeIndexCols(substring, strMulti_ix_cols.substring(0, index2));
                    strMulti_ix_cols = strMulti_ix_cols.substring(index2 + 1);
                }
            }
            if (strMulti_ix_cols == null || strMulti_ix_cols.length() == 0) {
                return;
            }
        }
        int i = 0;
        int n2 = 1;
        while (i < strMulti_ix_cols.length()) {
            if (strMulti_ix_cols.charAt(i) == '.') {
                ++n2;
            }
            ++i;
        }
        this.m_strNonPrimeKeyIndexes = new String[n2][];
        this.m_strNonPrimeKeyIndexNames = new String[n2];
        int j = 0;
        int beginIndex = 0;
        while (j < n2) {
            final int index3 = strMulti_ix_cols.indexOf(".", beginIndex);
            String s;
            if (index3 != -1) {
                s = strMulti_ix_cols.substring(beginIndex, index3);
            }
            else {
                s = strMulti_ix_cols.substring(beginIndex);
            }
            final int index4 = s.indexOf(":");
            String substring2;
            if (index4 != -1) {
                substring2 = s.substring(0, index4);
                this.m_strNonPrimeKeyIndexNames[j] = s.substring(index4 + 1);
            }
            else {
                substring2 = s;
                this.m_strNonPrimeKeyIndexNames[j] = "Idx" + Integer.toString(n++);
            }
            int k = 0;
            int n3 = 1;
            while (k < substring2.length()) {
                if (substring2.charAt(k) == ',') {
                    ++n3;
                }
                ++k;
            }
            this.m_strNonPrimeKeyIndexes[j] = new String[n3];
            final String[] array = this.m_strNonPrimeKeyIndexes[j];
            int l = 0;
            int beginIndex2 = 0;
            while (l < n3) {
                final int index5 = substring2.indexOf(",", beginIndex2);
                if (index5 != -1) {
                    this.m_strNonPrimeKeyIndexes[j][l] = substring2.substring(beginIndex2, index5);
                }
                else {
                    this.m_strNonPrimeKeyIndexes[j][l] = substring2.substring(beginIndex2);
                }
                beginIndex2 = index5 + 1;
                ++l;
            }
            beginIndex = index3 + 1;
            if (n3 == 1) {
                final String anotherString = this.m_strNonPrimeKeyIndexes[j][0];
                for (int n4 = 0; n4 < super.m_pMetaData.length; ++n4) {
                    if (super.m_pMetaData[n4].getFieldName().equalsIgnoreCase(anotherString)) {
                        super.m_pMetaData[n4].setIsUnique(true);
                    }
                }
            }
            ++j;
        }
    }
    
    void setPrimeIndexCols(String substring, final String strPrimeKeyIndexName) {
        int n = 0;
        this.m_strPrime_ix_cols = substring;
        this.m_strPrimeKeyIndexName = strPrimeKeyIndexName;
        if (substring == null || substring.length() == 0) {
            return;
        }
        for (int i = 0; i < substring.length(); ++i) {
            if (substring.charAt(i) == ',') {
                ++n;
            }
        }
        this.m_bUniquePrimaryKey = (substring.charAt(0) == '1');
        substring = substring.substring(2);
        this.m_strPrimeKeyColNames = new String[n];
        int j = 0;
        int beginIndex = 0;
        while (j < n) {
            final int index = substring.indexOf(",", beginIndex);
            if (index != -1) {
                this.m_strPrimeKeyColNames[j] = substring.substring(beginIndex, index);
            }
            else {
                this.m_strPrimeKeyColNames[j] = substring.substring(beginIndex);
            }
            beginIndex = index + 1;
            ++j;
        }
    }
    
    protected boolean isPrimaryUniqueColumn(final String str) {
        if (!this.m_bUniquePrimaryKey) {
            return false;
        }
        for (int i = 0; i < this.m_strPrimeKeyColNames.length; ++i) {
            if (this.m_strPrimeKeyColNames[i].compareToIgnoreCase(str) == 0) {
                return true;
            }
        }
        return false;
    }
    
    public String getStrongName() {
        String str;
        if (this.m_strNamespace != null && this.m_strNamespace != "") {
            str = this.m_strNamespace + "." + this.m_strClassName;
        }
        else {
            str = this.m_strClassName;
        }
        return str + "DataTable";
    }
    
    public boolean hasSameSchema(final IPGStrongNameParam ipgStrongNameParam) {
        final PGDataTableParam pgDataTableParam = (PGDataTableParam)ipgStrongNameParam;
        final PGMetaData[] metaData = pgDataTableParam.getMetaData();
        if (super.m_pMetaData.length != metaData.length) {
            return false;
        }
        if (this.m_strPrime_ix_cols != null && !this.m_strPrime_ix_cols.equals(pgDataTableParam.m_strPrime_ix_cols)) {
            return false;
        }
        for (int i = 0; i < super.m_pMetaData.length; ++i) {
            if (!super.m_pMetaData[i].hasSameSchema(metaData[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasSameSchemaWS(final IPGStrongNameParam ipgStrongNameParam) {
        final PGDataTableParam pgDataTableParam = (PGDataTableParam)ipgStrongNameParam;
        final PGMetaData[] metaData = pgDataTableParam.getMetaData();
        if (!super.m_strName.equals(pgDataTableParam.getParamName())) {
            return false;
        }
        final String xmlNodeName = pgDataTableParam.getXmlNodeName();
        boolean b = false;
        if (xmlNodeName == null && this.m_strXmlNodeName == null) {
            b = true;
        }
        else if (xmlNodeName == null || this.m_strXmlNodeName == null) {
            b = false;
        }
        else if (xmlNodeName.equals(this.m_strXmlNodeName)) {
            b = true;
        }
        if (!b) {
            return false;
        }
        if (super.m_pMetaData.length != metaData.length) {
            return false;
        }
        if (this.m_strPrime_ix_cols != null && !this.m_strPrime_ix_cols.equals(pgDataTableParam.m_strPrime_ix_cols)) {
            return false;
        }
        for (int i = 0; i < super.m_pMetaData.length; ++i) {
            if (!super.m_pMetaData[i].hasSameSchemaWS(metaData[i])) {
                return false;
            }
        }
        return true;
    }
    
    void setDataTableMetaData(final PGDataTableParam pgDataTableParam) {
        super.m_pMetaData = pgDataTableParam.m_pMetaData;
        this.m_bUniquePrimaryKey = pgDataTableParam.m_bUniquePrimaryKey;
        this.m_bBeforeTable = pgDataTableParam.m_bBeforeTable;
        this.m_strPrime_ix_cols = pgDataTableParam.m_strPrime_ix_cols;
        this.m_strPrimeKeyColNames = pgDataTableParam.m_strPrimeKeyColNames;
        this.m_strPrimeKeyIndexName = pgDataTableParam.m_strPrimeKeyIndexName;
        this.m_strNonPrimeKeyIndexes = pgDataTableParam.m_strNonPrimeKeyIndexes;
        this.m_strNonPrimeKeyIndexNames = pgDataTableParam.m_strNonPrimeKeyIndexNames;
    }
    
    public void writeDataTableXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        String string;
        if (b) {
            string = str + ":DataTable";
        }
        else {
            string = "DataTable";
        }
        try {
            final AttributesImpl attributesImpl = new AttributesImpl();
            attributesImpl.addAttribute("", "Name", "Name", "CDATA", this.getParamName());
            attributesImpl.addAttribute("", "hasBeforeTable", "hasBeforeTable", "CDATA", this.hasBeforeTable() ? "true" : "false");
            if (this.m_strXmlNodeName != null) {
                attributesImpl.addAttribute("", "XmlNodeName", "XmlNodeName", "CDATA", this.m_strXmlNodeName);
            }
            xmlSerializer.startElement(s, "DataTable", string, (Attributes)attributesImpl);
            final PGMetaData[] metaData = this.getMetaData();
            for (int i = 0; i < metaData.length; ++i) {
                String string2;
                if (b) {
                    string2 = str + ":MetaData";
                }
                else {
                    string2 = "MetaData";
                }
                xmlSerializer.startElement(s, "MetaData", string2, (Attributes)null);
                metaData[i].writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "MetaData", string2);
            }
            final String primeKeyIndexName = this.getPrimeKeyIndexName();
            if (primeKeyIndexName != null) {
                String string3;
                if (b) {
                    string3 = str + ":Index";
                }
                else {
                    string3 = "Index";
                }
                final AttributesImpl attributesImpl2 = new AttributesImpl();
                attributesImpl2.addAttribute("", "Name", "Name", "CDATA", primeKeyIndexName);
                attributesImpl2.addAttribute("", "isPrimary", "isPrimary", "CDATA", "true");
                attributesImpl2.addAttribute("", "isUnique", "isUnique", "CDATA", this.hasUniquePrimaryKey() ? "true" : "false");
                xmlSerializer.startElement(s, "Index", string3, (Attributes)attributesImpl2);
                final String[] primeKeyColNames = this.getPrimeKeyColNames();
                String string4;
                if (b) {
                    string4 = str + ":Field";
                }
                else {
                    string4 = "Field";
                }
                for (int j = 0; j < primeKeyColNames.length; ++j) {
                    xmlSerializer.startElement(s, "Field", string4, (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(primeKeyColNames[j].toCharArray(), 0, primeKeyColNames[j].length());
                    xmlSerializer.endElement(s, "Field", string4);
                }
                String string5;
                if (b) {
                    string5 = str + ":Index";
                }
                else {
                    string5 = "Index";
                }
                xmlSerializer.endElement(s, "Index", string5);
            }
            final String[] nonPrimeKeyIndexNames = this.getNonPrimeKeyIndexNames();
            final String[][] nonPrimeKeyIndexes = this.getNonPrimeKeyIndexes();
            if (nonPrimeKeyIndexNames != null && nonPrimeKeyIndexNames.length > 0) {
                for (int k = 0; k < nonPrimeKeyIndexNames.length; ++k) {
                    final String value = nonPrimeKeyIndexNames[k];
                    if (value != null) {
                        String string6;
                        if (b) {
                            string6 = str + ":Index";
                        }
                        else {
                            string6 = "Index";
                        }
                        final AttributesImpl attributesImpl3 = new AttributesImpl();
                        attributesImpl3.addAttribute("", "Name", "Name", "CDATA", value);
                        attributesImpl3.addAttribute("", "isPrimary", "isPrimary", "CDATA", "false");
                        attributesImpl3.addAttribute("", "isUnique", "isUnique", "CDATA", "true");
                        xmlSerializer.startElement(s, "Index", string6, (Attributes)attributesImpl3);
                        final String[] array = nonPrimeKeyIndexes[k];
                        String string7;
                        if (b) {
                            string7 = str + ":Field";
                        }
                        else {
                            string7 = "Field";
                        }
                        for (int l = 0; l < array.length; ++l) {
                            xmlSerializer.startElement(s, "Field", string7, (Attributes)null);
                            ((BaseMarkupSerializer)xmlSerializer).characters(array[l].toCharArray(), 0, array[l].length());
                            xmlSerializer.endElement(s, "Field", string7);
                        }
                        String string8;
                        if (b) {
                            string8 = str + ":Index";
                        }
                        else {
                            string8 = "Index";
                        }
                        xmlSerializer.endElement(s, "Index", string8);
                    }
                }
            }
            String string9;
            if (b) {
                string9 = str + ":DataTable";
            }
            else {
                string9 = "DataTable";
            }
            xmlSerializer.endElement(s, "DataTable", string9);
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readDataTableXML(final Node node) {
        final Vector vector = new Vector<PGMetaData>();
        final Vector vector2 = new Vector<Node>();
        final NamedNodeMap attributes = node.getAttributes();
        this.setParamName(attributes.getNamedItem("Name").getNodeValue());
        final Node namedItem = attributes.getNamedItem("hasBeforeTable");
        if (namedItem != null) {
            final String nodeValue = namedItem.getNodeValue();
            if (nodeValue.equals("true") || nodeValue.equals("1")) {
                this.setBeforeTable(true);
            }
        }
        final Node namedItem2 = attributes.getNamedItem("XmlNodeName");
        if (namedItem2 != null) {
            this.setXmlNodeName(namedItem2.getNodeValue());
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                if (localName.equals("MetaData")) {
                    final PGMetaData obj = new PGMetaData();
                    obj.readXML(item);
                    vector.addElement(obj);
                }
                else if (localName.equals("Index")) {
                    final NamedNodeMap attributes2 = item.getAttributes();
                    final String nodeValue2 = attributes2.getNamedItem("isPrimary").getNodeValue();
                    if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
                        final String nodeValue3 = attributes2.getNamedItem("isUnique").getNodeValue();
                        if (nodeValue3.equals("true") || nodeValue3.equals("1")) {
                            this.m_bUniquePrimaryKey = true;
                        }
                        this.m_strPrimeKeyIndexName = attributes2.getNamedItem("Name").getNodeValue();
                        final Vector vector3 = new Vector<String>();
                        final NodeList childNodes2 = item.getChildNodes();
                        for (int length2 = childNodes2.getLength(), j = 0; j < length2; ++j) {
                            final Node item2 = childNodes2.item(j);
                            if (item2.getNodeType() == 1 && item2.getLocalName().equals("Field")) {
                                vector3.addElement(WsaParser.extractNodeValue(item2));
                            }
                        }
                        if (!vector3.isEmpty()) {
                            final int size = vector3.size();
                            this.m_strPrimeKeyColNames = new String[size];
                            for (int k = 0; k < size; ++k) {
                                this.m_strPrimeKeyColNames[k] = vector3.elementAt(k);
                            }
                        }
                    }
                    else {
                        vector2.addElement(item);
                    }
                }
            }
        }
        if (!vector.isEmpty()) {
            final PGMetaData[] array = new PGMetaData[vector.size()];
            vector.copyInto(array);
            this.setMetaData(array);
        }
        if (!vector2.isEmpty()) {
            final int size2 = vector2.size();
            this.m_strNonPrimeKeyIndexes = new String[size2][];
            this.m_strNonPrimeKeyIndexNames = new String[size2];
            for (int l = 0; l < size2; ++l) {
                final Node node2 = vector2.elementAt(l);
                this.m_strNonPrimeKeyIndexNames[l] = node2.getAttributes().getNamedItem("Name").getNodeValue();
                final Vector vector4 = new Vector<String>();
                final NodeList childNodes3 = node2.getChildNodes();
                for (int length3 = childNodes3.getLength(), n = 0; n < length3; ++n) {
                    final Node item3 = childNodes3.item(n);
                    if (item3.getNodeType() == 1 && item3.getLocalName().equals("Field")) {
                        vector4.addElement(WsaParser.extractNodeValue(item3));
                    }
                }
                if (!vector4.isEmpty()) {
                    final int size3 = vector4.size();
                    this.m_strNonPrimeKeyIndexes[l] = new String[size3];
                    for (int index = 0; index < size3; ++index) {
                        this.m_strNonPrimeKeyIndexes[l][index] = vector4.elementAt(index);
                    }
                }
            }
        }
    }
}
