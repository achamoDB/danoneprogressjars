// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.IOException;
import java.io.File;
import java.util.Vector;

public class Test
{
    public static void main(final String[] array) {
        try {
            System.out.println("Building file list ...");
            final PGAppObj pgAppObj = new PGAppObj();
            boolean genActiveXProxy = false;
            if (array[4].equalsIgnoreCase("/x")) {
                genActiveXProxy = true;
            }
            pgAppObj.setAppObjectName(array[2]);
            pgAppObj.setHelpString("");
            pgAppObj.setSubAppObject(false);
            final PGGenInfo genInfo = new PGGenInfo();
            PGAppObj.setGenInfo(genInfo);
            genInfo.setVerbose(true);
            genInfo.setWorkDir(array[1] + "/");
            genInfo.setPackage(array[0]);
            pgAppObj.setProcVector(getProcList(array[1] + "/" + array[5], false));
            if (array.length == 7) {
                pgAppObj.setPerProcVector(getProcList(array[1] + "/" + array[6], true));
            }
            System.out.println("Generating Proxies ...");
            genInfo.setGenJavaProxy(!genActiveXProxy);
            genInfo.setGenActiveXProxy(genActiveXProxy);
            genInfo.setAXCompiler("jvc");
            genInfo.setJCompiler(array[3]);
            pgAppObj.generate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static Vector getProcList(final String s, final boolean b) throws IOException {
        final String[] list = new File(s).list();
        final Vector vector = new Vector<PGProc>(list.length);
        for (int i = 0; i < list.length; ++i) {
            final String substring = list[i].substring(0, list[i].indexOf("."));
            final PGProc obj = new PGProc();
            obj.setProPath(s + "/");
            obj.setProcPath("");
            obj.setProcName(substring);
            if (b) {
                obj.setPersistent(true);
            }
            vector.addElement(obj);
        }
        return vector;
    }
}
