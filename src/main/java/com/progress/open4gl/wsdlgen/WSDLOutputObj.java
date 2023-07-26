// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.IPGDetail;
import javax.wsdl.Message;
import javax.wsdl.Definition;
import javax.wsdl.Output;

public class WSDLOutputObj
{
    public static Output build(final String s, final String s2, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final Output output = definitionObj.createOutput();
        final Message buildOutput = WSDLMessageObj.buildOutput(s, s2, wsdlGenInfo);
        definitionObj.addMessage(buildOutput);
        output.setMessage(buildOutput);
        return output;
    }
    
    public static Output build(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final Output output = definitionObj.createOutput();
        final Message buildOutput = WSDLMessageObj.buildOutput(s, ipgDetail, wsdlGenInfo);
        definitionObj.addMessage(buildOutput);
        output.setMessage(buildOutput);
        return output;
    }
}
