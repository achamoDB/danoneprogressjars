// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.ResourceBundle;
import com.progress.wsa.admin.WSAD;
import com.progress.common.util.InstallPath;
import java.io.IOException;
import java.io.File;
import com.progress.common.exception.ProException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import com.progress.international.resources.ProgressResources;
import java.io.PrintWriter;

public class Batch
{
    public static int BATCH_VALIDATE;
    public static int BATCH_GEN_DOTNET;
    public static int BATCH_GEN_JAVA;
    public static int BATCH_GEN_WEBSERVICES;
    private static PrintWriter m_debugFile;
    private static String PXGFile;
    private static String XPXGFile;
    private static String genAction;
    private static String startDir;
    private static ProgressResources PGResources;
    
    public static void main(final String[] array) {
        ProException ex = null;
        final PGAppObj[] array2 = { null };
        String workDir = null;
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
            if (Batch.startDir != null && Batch.startDir.length() > 0) {
                System.setProperty("user.dir", Batch.startDir);
            }
            final boolean loadProject = LoadProject(Batch.PXGFile, Batch.XPXGFile, array2);
            if (!loadProject) {
                final PGAppObj pgAppObj = array2[0];
                workDir = PGAppObj.getGenInfo().getWorkDir();
                final PGAppObj pgAppObj2 = array2[0];
                final String checkDir = PGAppObj.checkDir(workDir);
                if (checkDir != null) {
                    ex = new Open4GLException(8099442454849133704L, new Object[] { checkDir });
                }
            }
            if (!loadProject) {
                final PGAppObj pgAppObj3 = array2[0];
                PGAppObj.getGenInfo().setBatch(true);
                System.out.println(Batch.PGResources.getTranString("PGBATCH_GEN"));
                final int generate = array2[0].generate();
                final String string = workDir + array2[0].getAppObjectName();
                String x;
                if ((generate & 0x2) != 0x0) {
                    x = Batch.PGResources.getTranString("PGBATCH_GENFAILED");
                }
                else if ((generate & 0x4) != 0x0) {
                    x = Batch.PGResources.getTranString("PGBATCH_GENWARNING");
                }
                else {
                    x = Batch.PGResources.getTranString("PGBATCH_GENSUCCEEDED");
                }
                System.out.println(x);
                System.out.println(Batch.PGResources.getTranString("PGBATCH_CHKLOG") + " " + string + ".log");
            }
            if (loadProject) {
                if (ex != null) {
                    System.out.println(ex.getMessage());
                }
                System.out.println(Batch.PGResources.getTranString("PGBATCH_FAILED"));
            }
            System.exit(0);
        }
        catch (Open4GLException ex2) {
            System.out.println(ex2.getMessage());
            System.out.println(Batch.PGResources.getTranString("PGBATCH_FAILED"));
            System.exit(0);
        }
        catch (Exception ex3) {
            System.out.println(new Open4GLException(8099442454849133707L, null).getMessage());
            System.out.println(Batch.PGResources.getTranString("PGBATCH_FAILED"));
            System.exit(0);
        }
    }
    
    public static boolean LoadProject(final String s, final String s2, final PGAppObj[] array) {
        final PGGenInfo[] array2 = { null };
        boolean b = false;
        ProException ex = null;
        String s3 = null;
        try {
            if (s == null && s2 == null) {
                ex = new Open4GLException(8099442454849137576L, null);
                b = true;
            }
            if (s != null && s2 != null) {
                ex = new Open4GLException(8099442454849137577L, null);
                b = true;
            }
            if (s != null && s.toLowerCase().endsWith(".pxg")) {
                s3 = s;
                final File file = new File(s3);
                if (file.exists() && file.length() == 0L) {
                    ex = new Open4GLException(8099442454849139556L, new Object[] { s3 });
                    b = true;
                }
                else {
                    array[0] = PGAppObj.loadObject(s, array2);
                    if (array[0] != null) {
                        final PGAppObj pgAppObj = array[0];
                        PGAppObj.setGenInfo(array2[0]);
                    }
                }
            }
            else if (s2 != null && s2.toLowerCase().endsWith(".xpxg")) {
                s3 = s2;
                final File file2 = new File(s3);
                if (file2.exists() && file2.length() == 0L) {
                    ex = new Open4GLException(8099442454849139556L, new Object[] { s3 });
                    b = true;
                }
                else {
                    array[0] = loadXML(s2, Batch.m_debugFile);
                    if (array[0] != null) {
                        array[0].updateDataTypesFromXPXGFile(array[0]);
                        final PGAppObj pgAppObj2 = array[0];
                        if (PGAppObj.getGenInfo() != null) {
                            final PGAppObj pgAppObj3 = array[0];
                            PGAppObj.getGenInfo().doJClasspathUpdate();
                        }
                    }
                }
            }
            else {
                final Object[] array3 = { null };
                if (s != null) {
                    array3[0] = s;
                }
                else {
                    array3[0] = s2;
                }
                ex = new Open4GLException(8099442454849137578L, array3);
                b = true;
            }
            Label_0396: {
                if (!b) {
                    if (array[0] != null) {
                        final PGAppObj pgAppObj4 = array[0];
                        if (PGAppObj.getGenInfo() != null) {
                            break Label_0396;
                        }
                    }
                    ex = new Open4GLException(8607504787811871214L, new Object[] { s3, "" });
                    b = true;
                }
            }
        }
        catch (IOException ex2) {
            final Object[] array4 = { null };
            if (s != null) {
                array4[0] = s;
            }
            else {
                array4[0] = s2;
            }
            ex = new Open4GLException(8099442454849134581L, array4);
            b = true;
        }
        catch (Exception ex3) {
            ex = new Open4GLException(8099442454849133707L, null);
            b = true;
        }
        if (b) {
            System.out.println(ex.getMessage());
        }
        return b;
    }
    
    public static PGAppObj loadXML(final String s, final PrintWriter printWriter) throws IOException {
        final String property = System.getProperty("Install.Dir");
        PGAppObj pgAppObj = null;
        final InstallPath installPath = new InstallPath();
        InstallPath.setInstallPath(property);
        final WSAD loadXPXGFile = WSAD.loadXPXGFile(null, PGUtils.getAbsFilename(null, s));
        if (loadXPXGFile != null) {
            pgAppObj = loadXPXGFile.getPGAppObj();
        }
        return pgAppObj;
    }
    
    static {
        Batch.BATCH_VALIDATE = 1;
        Batch.BATCH_GEN_DOTNET = 2;
        Batch.BATCH_GEN_JAVA = 4;
        Batch.BATCH_GEN_WEBSERVICES = 8;
        Batch.m_debugFile = null;
        Batch.PXGFile = System.getProperty("PXGFile");
        Batch.XPXGFile = System.getProperty("XPXGFile");
        Batch.genAction = System.getProperty("Action");
        Batch.startDir = System.getProperty("ProxyGen.StartDir");
        Batch.PGResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
    }
}
