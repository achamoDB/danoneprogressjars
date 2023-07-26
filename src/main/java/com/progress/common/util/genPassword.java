// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class genPassword
{
    private static final int OPT_ENCRYPT = 10;
    private static final int OPT_VERIFY = 20;
    private static final int OPT_HELP = 30;
    private static final int UNKOPT = 63;
    
    public static void main(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("password:", 10), new Getopt.GetoptList("verify:", 20), new Getopt.GetoptList("help", 30), new Getopt.GetoptList("", 0) };
        if (array.length == 0) {
            usage();
        }
        final Getopt getopt = new Getopt(array);
        String optArg = null;
        Object optArg2 = null;
        boolean b = false;
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 10: {
                    optArg = getopt.getOptArg();
                    continue;
                }
                case 20: {
                    optArg2 = getopt.getOptArg();
                    b = true;
                    continue;
                }
                case 30: {
                    usage();
                    continue;
                }
                case 63: {
                    usage();
                    continue;
                }
            }
        }
        if (!b) {
            System.out.println(new crypto().encrypt(optArg));
        }
        else if (optArg != null && optArg2 != null) {
            if (new crypto().encrypt(optArg).equals(optArg2)) {
                System.out.println("The passwords match.");
            }
            else {
                System.out.println("The passwords do not match.");
            }
        }
        else {
            usage();
        }
    }
    
    private static void usage() {
        System.out.println("usage: genpassword -password <text>");
        System.out.println("    or genpassword -password <text> -verify <encrypted password>");
        System.exit(1);
    }
}
