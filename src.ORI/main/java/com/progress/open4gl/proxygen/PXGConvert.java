// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.progress.wsa.admin.WSAD;
import java.io.IOException;
import com.progress.open4gl.Open4GLException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import com.progress.common.util.ProgressVersion;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;

public class PXGConvert
{
    public static void main(final String[] array) {
        int n = 0;
        boolean b = false;
        PGAppObj loadObject = null;
        final PGGenInfo[] array2 = { null };
        PrintWriter printWriter = null;
        final String property = System.getProperty("PXGFile");
        String s = System.getProperty("XPXGFile");
        final String property2 = System.getProperty("DebugOutFile");
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
            System.out.println(progressResources.getTranString("PGCONVERT_PRODUCT") + ProgressVersion.getVersionString());
            if (property2 != null) {
                printWriter = new PrintWriter(new FileOutputStream(property2), true);
                b = true;
                printWriter.println("In main");
            }
        }
        catch (IOException ex10) {
            final Open4GLException ex = new Open4GLException(8099442454849134581L, new Object[] { property2 });
            if (ex != null) {
                System.out.println(ex.getMessage());
                if (b) {
                    printWriter.println(ex.getMessage());
                }
            }
            n = 1;
        }
        try {
            if (property == null) {
                final Open4GLException ex2 = new Open4GLException(8099442454849134581L, new Object[] { property });
                if (ex2 != null) {
                    System.out.println(ex2.getMessage());
                    if (b) {
                        printWriter.println(ex2.getMessage());
                    }
                }
                n = 1;
            }
            else if (!property.toLowerCase().endsWith(".pxg")) {
                final Open4GLException ex3 = new Open4GLException(8099442454849134855L, new Object[] { property });
                if (ex3 != null) {
                    System.out.println(ex3.getMessage());
                    if (b) {
                        printWriter.println(ex3.getMessage());
                    }
                }
                n = 1;
            }
            if (n == 0) {
                if (b) {
                    printWriter.println("Loading PXGFile");
                }
                loadObject = PGAppObj.loadObject(property, array2);
                if (loadObject == null) {
                    final Open4GLException ex4 = new Open4GLException(8099442454849133706L, null);
                    if (ex4 != null) {
                        System.out.println(ex4.getMessage());
                        if (b) {
                            printWriter.println(ex4.getMessage());
                        }
                    }
                    n = 1;
                }
            }
            if (n == 0) {
                updateAO(loadObject);
            }
            if (n == 0) {
                if (b) {
                    printWriter.println("Validating XPXG location");
                }
                if (s == null) {
                    final int lastIndex = property.lastIndexOf(".");
                    if (lastIndex > -1) {
                        s = property.substring(0, lastIndex) + ".xpxg";
                    }
                    else {
                        final Open4GLException ex5 = new Open4GLException(8099442454849134855L, new Object[] { s });
                        if (ex5 != null) {
                            System.out.println(ex5.getMessage());
                            if (b) {
                                printWriter.println(ex5.getMessage());
                            }
                        }
                        n = 1;
                    }
                }
                else if (!s.toLowerCase().endsWith(".xpxg")) {
                    final Open4GLException ex6 = new Open4GLException(8099442454849134855L, new Object[] { s });
                    if (ex6 != null) {
                        System.out.println(ex6.getMessage());
                        if (b) {
                            printWriter.println(ex6.getMessage());
                        }
                    }
                    n = 1;
                }
                else {
                    final int lastIndex2 = s.lastIndexOf(System.getProperty("file.separator"));
                    if (lastIndex2 > -1) {
                        final String checkDir = PGUtils.checkDir(s.substring(0, lastIndex2));
                        if (checkDir != null) {
                            final Open4GLException ex7 = new Open4GLException(8099442454849133704L, new Object[] { checkDir });
                            if (ex7 != null) {
                                System.out.println(ex7.getMessage());
                                if (b) {
                                    printWriter.println(ex7.getMessage());
                                }
                            }
                            n = 1;
                        }
                    }
                }
            }
            if (n == 0) {
                if (b) {
                    printWriter.println("Saving XPXG");
                }
                saveXML(loadObject, s);
            }
        }
        catch (IOException ex11) {
            final Open4GLException ex8 = new Open4GLException(8099442454849134581L, new Object[] { property });
            if (ex8 != null) {
                System.out.println(ex8.getMessage());
                if (b) {
                    printWriter.println(ex8.getMessage());
                }
            }
            n = 1;
        }
        catch (Exception ex12) {
            final Object[] array3 = new Object[0];
            final Open4GLException ex9 = new Open4GLException(8099442454849133707L, null);
            if (ex9 != null) {
                System.out.println(ex9.getMessage());
                if (b) {
                    printWriter.println(ex9.getMessage());
                }
            }
            n = 1;
        }
        if (n == 0) {
            System.out.println(progressResources.getTranString("PGCONVERT_GENSUCCEEDED"));
        }
        else {
            System.out.println(progressResources.getTranString("PGCONVERT_FAILED"));
        }
        if (b && printWriter != null) {
            printWriter.close();
        }
        System.exit(0);
    }
    
    public static void updateAO(final PGAppObj pgAppObj) throws Exception {
        try {
            PGAppObj.getGenInfo().setUserDefinedAppService(true);
        }
        catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void saveXML(final PGAppObj pgAppObj, final String s) throws IOException, Exception {
        try {
            new WSAD(pgAppObj).saveXPXGFile(PGUtils.getAbsFilename(null, s));
        }
        catch (Exception ex) {
            throw ex;
        }
    }
}
