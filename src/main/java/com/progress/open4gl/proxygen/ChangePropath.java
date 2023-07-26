// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;

public class ChangePropath
{
    public static void main(final String[] array) {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
        if (array.length < 2) {
            System.out.println(progressResources.getTranString("PGCNGPROPATH_Help"));
            return;
        }
        final String s = array[0];
        final String str = array[1];
        PGAppObj loadObject;
        try {
            loadObject = PGAppObj.loadObject(s, new PGGenInfo[1]);
        }
        catch (Exception ex) {
            System.out.println(ex.getClass().getName() + progressResources.getTranString("PGCNGPROPATH_LoadErr") + ex.getMessage());
            return;
        }
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(PGAppObj.getAbsFilename(null, str)));
        }
        catch (FileNotFoundException ex4) {
            System.out.println(progressResources.getTranString("PGCNGPROPATH_NoFileErr") + str);
            return;
        }
        try {
            System.out.println(progressResources.getTranString("PGCNGPROPATH_Processing"));
            for (String str2 = PGAppObj.fixOSPath(1, bufferedReader.readLine()); str2 != null; str2 = PGAppObj.fixOSPath(1, bufferedReader.readLine())) {
                final String fixOSPath = PGAppObj.fixOSPath(1, bufferedReader.readLine());
                if (fixOSPath != null) {
                    System.out.println(progressResources.getTranString("PGCNGPROPATH_OldDir") + str2);
                    System.out.println(progressResources.getTranString("PGCNGPROPATH_NewDir") + fixOSPath);
                    System.out.println(progressResources.getTranString("PGCNGPROPATH_Replaced") + PGAppObj.updatePropath(loadObject, str2, fixOSPath) + progressResources.getTranString("PG_Procedures"));
                }
            }
        }
        catch (IOException ex2) {
            System.out.println(progressResources.getTranString("PGCNGPROPATH_IOErr") + ex2.getMessage());
            return;
        }
        try {
            PGAppObj.saveObject(loadObject, s);
        }
        catch (FileNotFoundException ex5) {
            System.out.println(progressResources.getTranString("PGCNGPROPATH_ROErr"));
        }
        catch (Exception ex3) {
            System.out.println(ex3.getClass().getName() + progressResources.getTranString("PGCNGPROPATH_SaveErr") + ex3.getMessage());
        }
    }
}
