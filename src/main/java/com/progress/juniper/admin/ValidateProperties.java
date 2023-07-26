// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.international.resources.ProgressResources;
import com.progress.common.log.ProLog;
import com.progress.common.property.PropertyManager;

public class ValidateProperties extends JuniperProperties
{
    public ValidateProperties(final String s) throws PropertyException, LoadIOException, Exception {
        this.load(s, false);
    }
    
    public static void main(final String[] array) {
        boolean b = false;
        final DbConfigGetopt dbConfigGetopt = new DbConfigGetopt(array);
        if (dbConfigGetopt.helpRequested()) {
            System.out.println(ProLog.format(7162412257379362586L));
            System.out.println(dbConfigGetopt.usageString());
            System.exit(0);
        }
        if (dbConfigGetopt.getInvalidArgs().length() > 0) {
            System.out.println(ProLog.format(7162412257379362587L, dbConfigGetopt.getInvalidArgs()));
            System.out.println(dbConfigGetopt.usageString());
            System.exit(1);
        }
        final String propFile = dbConfigGetopt.getPropFile();
        try {
            System.out.println(ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Validating property file", (Object)propFile));
            JATools.setIsServer();
            final ValidateProperties validateProperties = new ValidateProperties(propFile);
            b = true;
        }
        catch (PropertyException ex) {
            System.out.println(ex.getMessage());
        }
        catch (Exception ex2) {
            System.out.println(ex2.getMessage());
        }
        finally {
            if (b) {
                System.out.println(ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Validation successful"));
            }
            System.exit(0);
        }
    }
}
