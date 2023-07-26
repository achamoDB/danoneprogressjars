// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import com.progress.open4gl.proxygen.IPGDetail;
import javax.wsdl.Message;
import javax.wsdl.Definition;
import javax.wsdl.Input;

public class WSDLInputObj
{
    public static Input build(final String s, final String s2, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final Input input = definitionObj.createInput();
        final Message buildInput = WSDLMessageObj.buildInput(s, s2, wsdlGenInfo);
        definitionObj.addMessage(buildInput);
        input.setMessage(buildInput);
        return input;
    }
    
    public static Input build(final String s, final IPGDetail ipgDetail, final WSDLGenInfo wsdlGenInfo) {
        final Definition definitionObj = wsdlGenInfo.getDefinitionObj();
        final Input input = definitionObj.createInput();
        final Message buildInput = WSDLMessageObj.buildInput(s, ipgDetail, wsdlGenInfo);
        definitionObj.addMessage(buildInput);
        input.setMessage(buildInput);
        return input;
    }
}
