// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import java.io.IOException;
import org.apache.soap.util.StringUtils;
import com.progress.wsa.WsaConstants;
import java.io.StringWriter;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import org.apache.soap.rpc.RPCConstants;
import org.apache.soap.Utils;
import org.apache.soap.rpc.RPCMessage;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.Fault;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.Header;
import java.util.Vector;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;

public class WsaResponse extends Response
{
    public WsaResponse(final String s, final String s2, final Parameter parameter, final Vector vector, final Header header, final String s3, final SOAPContext soapContext) {
        super(s, s2, parameter, vector, header, s3, soapContext);
    }
    
    public WsaResponse(final String s, final String s2, final Fault fault, final Vector vector, final Header header, final String s3, final SOAPContext soapContext) {
        super(s, s2, fault, vector, header, s3, soapContext);
    }
    
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        nsStack.pushScope();
        final RPCMessage rpcMessage = (RPCMessage)o;
        final boolean b = clazz == Response.class;
        final String cleanString = Utils.cleanString(rpcMessage.getFullTargetObjectURI());
        final String methodName = rpcMessage.getMethodName();
        final Vector params = rpcMessage.getParams();
        final String s2 = b ? RPCConstants.RESPONSE_SUFFIX : "";
        final String encodingStyleURI = rpcMessage.getEncodingStyleURI();
        final String s3 = (encodingStyleURI != null) ? encodingStyleURI : s;
        final String defaultEncoding = ((PscDeploymentDescriptor)soapContext.getProperty("psc.wsa.deployment_desc")).defaultEncoding();
        if (b) {
            final Response response = (Response)rpcMessage;
            if (!response.generatedFault()) {
                final StringWriter obj = new StringWriter();
                final String prefixFromURI = nsStack.getPrefixFromURI(cleanString, (Writer)obj);
                if (defaultEncoding.equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
                    writer.write('<' + methodName + s2 + " xmlns=\"" + cleanString + "\"");
                }
                else {
                    writer.write('<' + prefixFromURI + ':' + methodName + s2 + obj);
                }
                final String prefixFromURI2 = nsStack.getPrefixFromURI("http://schemas.xmlsoap.org/soap/envelope/", writer);
                if (encodingStyleURI != null && !encodingStyleURI.equals(s)) {
                    writer.write(' ' + prefixFromURI2 + ':' + "encodingStyle" + "=\"" + encodingStyleURI + '\"');
                }
                writer.write('>' + StringUtils.lineSeparator);
                final Parameter returnValue = response.getReturnValue();
                if (returnValue != null) {
                    final String encodingStyleURI2 = returnValue.getEncodingStyleURI();
                    xmlJavaMappingRegistry.querySerializer(Parameter.class, (encodingStyleURI2 != null) ? encodingStyleURI2 : s3).marshall(s3, Parameter.class, (Object)returnValue, (Object)null, writer, nsStack, xmlJavaMappingRegistry, soapContext);
                    writer.write(StringUtils.lineSeparator);
                }
                this.serializeParams(params, s3, writer, nsStack, xmlJavaMappingRegistry, soapContext);
                if (defaultEncoding.equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
                    writer.write("</" + methodName + s2 + '>' + StringUtils.lineSeparator);
                }
                else {
                    writer.write("</" + prefixFromURI + ':' + methodName + s2 + '>' + StringUtils.lineSeparator);
                }
            }
            else {
                response.getFault().marshall(s3, writer, nsStack, xmlJavaMappingRegistry, soapContext);
            }
        }
        else {
            final StringWriter obj2 = new StringWriter();
            final String prefixFromURI3 = nsStack.getPrefixFromURI(cleanString, (Writer)obj2);
            if (defaultEncoding.equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
                writer.write('<' + methodName + s2 + " xmlns=\"" + cleanString + "\"");
            }
            else {
                writer.write('<' + prefixFromURI3 + ':' + methodName + s2 + obj2);
            }
            final String prefixFromURI4 = nsStack.getPrefixFromURI("http://schemas.xmlsoap.org/soap/envelope/", writer);
            if (encodingStyleURI != null && !encodingStyleURI.equals(s)) {
                writer.write(' ' + prefixFromURI4 + ':' + "encodingStyle" + "=\"" + encodingStyleURI + '\"');
            }
            writer.write('>' + StringUtils.lineSeparator);
            this.serializeParams(params, s3, writer, nsStack, xmlJavaMappingRegistry, soapContext);
            if (defaultEncoding.equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
                writer.write("</" + methodName + s2 + '>' + StringUtils.lineSeparator);
            }
            else {
                writer.write("</" + prefixFromURI3 + ':' + methodName + s2 + '>' + StringUtils.lineSeparator);
            }
        }
        nsStack.popScope();
    }
    
    private void serializeParams(final Vector vector, final String s, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IOException {
        if (vector != null) {
            for (int size = vector.size(), i = 0; i < size; ++i) {
                final Parameter parameter = vector.elementAt(i);
                final String encodingStyleURI = parameter.getEncodingStyleURI();
                xmlJavaMappingRegistry.querySerializer(Parameter.class, (encodingStyleURI != null) ? encodingStyleURI : s).marshall(s, Parameter.class, (Object)parameter, (Object)null, writer, nsStack, xmlJavaMappingRegistry, soapContext);
                writer.write(StringUtils.lineSeparator);
            }
        }
    }
}
