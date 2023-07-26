// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import javax.wsdl.WSDLException;
import java.io.IOException;
import com.progress.wsa.admin.WSAD;
import com.progress.open4gl.proxygen.PGAppObj;
import com.progress.common.util.InstallPath;

public class WSDLMain
{
    public static void main(final String[] array) throws Exception, IOException, WSDLException {
        final String property = System.getProperty("WorkDir");
        final String property2 = System.getProperty("OutPath");
        final String property3 = System.getProperty("WSMFile");
        final String property4 = System.getProperty("InstallDir");
        System.setProperty("user.dir", "");
        final InstallPath installPath = new InstallPath();
        InstallPath.setInstallPath(property4);
        final WSAD loadWSMFile = WSAD.loadWSMFile(property4, PGAppObj.getAbsFilename(property2, property3));
        if (loadWSMFile != null) {
            final PGAppObj pgAppObj = loadWSMFile.getPGAppObj();
            PGAppObj.getGenInfo().setWorkDir(property);
            pgAppObj.buildTestWSDLs();
        }
    }
}
