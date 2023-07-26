// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.text.ParseException;
import java.util.TimeZone;
import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.Element;
import org.apache.soap.util.Bean;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.Serializer;

public class DateTimeSerializer implements Serializer, Deserializer
{
    protected ThreadLocalSimpleDateFormat fiveDigitYearFormat;
    protected ThreadLocalSimpleDateFormat fourDigitYearFormat;
    
    public DateTimeSerializer() {
        this.fiveDigitYearFormat = new ThreadLocalSimpleDateFormat("yyyyy-MM-dd'T'HH:mm:ss'.'SSS");
        this.fourDigitYearFormat = new ThreadLocalSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS");
    }
    
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        nsStack.pushScope();
        writer.write("<" + o2.toString());
        if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
            writer.write(" xsi:type=\"xsd:dateTime\"");
        }
        final GregorianCalendar calData = ((DateTimeHolder)o).getCalData();
        if (null != calData) {
            final Date time = calData.getTime();
            ThreadLocalSimpleDateFormat threadLocalSimpleDateFormat;
            if (calData.get(1) > 9999) {
                threadLocalSimpleDateFormat = this.fiveDigitYearFormat;
            }
            else {
                threadLocalSimpleDateFormat = this.fourDigitYearFormat;
            }
            writer.write(">" + threadLocalSimpleDateFormat.format(time) + "</" + o2.toString() + ">");
        }
        else {
            writer.write(" xsi:nil=\"true\"/>");
        }
        nsStack.popScope();
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        final Element element = (Element)node;
        final String attributeNS = DOMUtils.getAttributeNS(element, "http://www.w3.org/2001/XMLSchema-instance", "nil");
        if (null != attributeNS && (attributeNS.equals("true") || attributeNS.equals("1"))) {
            return new Bean(GregorianCalendar.class, (Object)null);
        }
        final String childCharacterData = DOMUtils.getChildCharacterData(element);
        int beginIndex = 0;
        if (childCharacterData.charAt(0) == '-') {
            beginIndex = 1;
        }
        final String str = new String(childCharacterData.substring(beginIndex, childCharacterData.length()));
        if (str.length() < 19) {
            throw new IllegalArgumentException("Invalid dateTime value '' sent to deserializer");
        }
        try {
            final String s2 = new String("GMT");
            boolean b = false;
            if (beginIndex == 1) {
                if (childCharacterData.charAt(6) == '-') {
                    b = true;
                }
            }
            else if (childCharacterData.charAt(5) == '-') {
                b = true;
            }
            String str2;
            if (b) {
                str2 = new String("yyyyy-MM-dd'T'HH:mm:ss");
            }
            else {
                str2 = new String("yyyy-MM-dd'T'HH:mm:ss");
            }
            int amount = 0;
            final int beginIndex2 = str.indexOf(".") + 1;
            String id;
            int endIndex;
            if (str.indexOf("Z") != -1) {
                id = s2.concat("+00:00");
                endIndex = str.indexOf("Z");
                str2 += "z";
            }
            else if (str.indexOf("+") != -1) {
                id = s2.concat(str.substring(str.length() - 6, str.length()));
                endIndex = str.indexOf("+");
                str2 += "z";
            }
            else if (str.indexOf("-", str.indexOf("T")) != -1) {
                id = s2.concat(str.substring(str.length() - 6, str.length()));
                endIndex = str.lastIndexOf("-");
                str2 += "z";
            }
            else {
                id = null;
                endIndex = str.length();
            }
            if (beginIndex2 != 0) {
                int n = endIndex - beginIndex2;
                if (n >= 3) {
                    endIndex = beginIndex2 + 3;
                    if (n > 3) {
                        if (new Integer(str.substring(beginIndex2 + 3, beginIndex2 + 4)) >= 5) {
                            n = 1;
                        }
                        else {
                            n = 0;
                        }
                    }
                    else {
                        n = 0;
                    }
                }
                amount = new Integer(str.substring(beginIndex2, endIndex)) + n;
            }
            final ThreadLocalSimpleDateFormat threadLocalSimpleDateFormat = new ThreadLocalSimpleDateFormat(str2);
            Date time;
            if (b) {
                time = threadLocalSimpleDateFormat.parse(str.substring(0, 20) + id);
            }
            else {
                time = threadLocalSimpleDateFormat.parse(str.substring(0, 19) + id);
            }
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(time);
            gregorianCalendar.add(14, amount);
            if (id != null) {
                gregorianCalendar.setTimeZone(TimeZone.getTimeZone(id));
            }
            return new Bean(GregorianCalendar.class, (Object)gregorianCalendar);
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid dateTime value '" + str + "' sent to deserializer");
        }
    }
}
