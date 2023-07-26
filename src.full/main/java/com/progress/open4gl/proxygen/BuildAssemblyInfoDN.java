// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.FileNotFoundException;
import com.progress.open4gl.Open4GLException;
import java.io.PrintWriter;

public class BuildAssemblyInfoDN extends Generator
{
    public static void build(final PGAppObj pgAppObj, final PrintWriter printWriter) throws Open4GLException, FileNotFoundException {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String template = Generator.extractTemplate(11);
        final String dnTitle = genInfo.getDNTitle();
        final String dnVersion = genInfo.getDNVersion();
        final String dnDesc = genInfo.getDNDesc();
        final String dnCompany = genInfo.getDNCompany();
        final String dnProduct = genInfo.getDNProduct();
        final String dnPublicKey = genInfo.getDNPublicKey();
        String s;
        if (dnTitle != null && dnTitle.length() > 0) {
            s = Generator.insertVariable(template, "%AssemblyTitle%", dnTitle);
        }
        else {
            s = Generator.insertVariable(template, "%AssemblyTitle%", "");
        }
        String s2;
        if (dnVersion != null && dnVersion.length() > 0) {
            s2 = Generator.insertVariable(s, "%AssemblyVersion%", dnVersion);
        }
        else {
            s2 = Generator.insertVariable(s, "%AssemblyVersion%", "2.0.*");
        }
        String s3;
        if (dnDesc != null && dnDesc.length() > 0) {
            s3 = Generator.insertVariable(s2, "%AssemblyDescription%", dnDesc);
        }
        else {
            s3 = Generator.insertVariable(s2, "%AssemblyDescription%", "");
        }
        String s4;
        if (dnProduct != null && dnProduct.length() > 0) {
            s4 = Generator.insertVariable(s3, "%AssemblyProduct%", dnProduct);
        }
        else {
            s4 = Generator.insertVariable(s3, "%AssemblyProduct%", "");
        }
        String s5;
        if (dnCompany != null && dnCompany.length() > 0) {
            s5 = Generator.insertVariable(s4, "%AssemblyCompany%", dnCompany);
        }
        else {
            s5 = Generator.insertVariable(s4, "%AssemblyCompany%", "");
        }
        String s6;
        if (genInfo.isDNDelaySign() && dnPublicKey != null && dnPublicKey.length() > 0) {
            s6 = Generator.insertVariable(Generator.insertVariable(s5, "%AssemblyDelaySign%", "true"), "%AssemblyKeyFile%", dnPublicKey);
        }
        else {
            s6 = Generator.insertVariable(Generator.insertVariable(s5, "%AssemblyDelaySign%", "false"), "%AssemblyKeyFile%", "");
        }
        printWriter.print(s6);
    }
}
