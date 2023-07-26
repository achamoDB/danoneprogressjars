// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import com.progress.common.ehnlog.IAppLogger;
import java.io.PrintWriter;

public class AiaMgt implements IAiaDispInfoConst
{
    private IAiaDisplayInfo m_scpDI;
    private IAiaDisplayInfo m_propDI;
    private AiaStatus m_statusObj;
    PrintWriter m_wtr;
    private static boolean DEBUG_TRACE;
    private IAppLogger log;
    
    public AiaMgt(final IAiaDisplayInfo propDI, final IAiaDisplayInfo scpDI, final IAppLogger log) {
        this.m_scpDI = null;
        this.m_propDI = null;
        this.m_statusObj = null;
        this.m_wtr = null;
        this.log = null;
        this.m_scpDI = scpDI;
        this.m_propDI = propDI;
        this.log = log;
    }
    
    public boolean processCommand(final String s, final PrintWriter printWriter) {
        final boolean b = false;
        this.m_statusObj = null;
        if (s.indexOf("GetServletStatus") >= 0) {
            try {
                if (this.m_scpDI != null) {
                    this.m_statusObj = new AiaStatus(this.m_scpDI);
                }
                else {
                    printWriter.println(">>>  The status descriptor object is null");
                }
                if (AiaMgt.DEBUG_TRACE) {
                    if (this.m_statusObj == null) {
                        printWriter.println(">>> The status object came back null ");
                    }
                    if (this.m_scpDI == null) {
                        printWriter.println(">>> The connection pool object came back null");
                    }
                }
                try {
                    this.writeServletStatusPage(s, printWriter);
                }
                catch (Exception ex) {
                    this.m_statusObj = null;
                    printWriter.println("Error in writeServletStatusPage: " + ex.toString());
                }
            }
            catch (Exception ex2) {
                this.m_statusObj = null;
                printWriter.println(">>>>Exception occured in processCommand:  " + ex2.toString());
            }
        }
        return b;
    }
    
    public void writeServletStatusPage(final String str, final PrintWriter printWriter) {
        final AiaDisplayInfoDesc[][] array = null;
        printWriter.println("<html><center><head><TITLE>AIA status page</title></head>");
        try {
            if (this.m_statusObj != null) {
                final String displayInfoTitle = this.m_statusObj.getDisplayInfoTitle();
                final AiaDisplayInfoDesc[][] displayInfoTable = this.m_statusObj.getDisplayInfoTable();
                final String[] displayInfoLabels = this.m_statusObj.getDisplayInfoLabels();
                printWriter.println("<BODY><h2>" + displayInfoTitle + "</h2>");
                if (displayInfoTable == null) {
                    printWriter.println("<H3><TAB indent = 5><FONT COLOR=red>No active connections running in the servlet. Try later.</FONT></h3>");
                }
                else {
                    this.writeTable(displayInfoLabels, displayInfoTable, printWriter);
                }
            }
            else {
                printWriter.println("Processing " + str + ", Problem getting status from the servlet engine. " + " status object is null");
            }
        }
        catch (Exception ex) {
            printWriter.println("writeServletStatusPage: Exception in status block: " + ex.toString());
        }
        if (this.m_propDI != null) {
            try {
                final String displayInfoTitle2 = this.m_propDI.getDisplayInfoTitle();
                final String[] displayInfoLabels2 = this.m_propDI.getDisplayInfoLabels();
                final AiaDisplayInfoDesc[][] displayInfoTable2 = this.m_propDI.getDisplayInfoTable();
                printWriter.println("<h2>" + displayInfoTitle2 + "</h2>");
                if (displayInfoTable2 != null && displayInfoLabels2 != null) {
                    this.writeTable(displayInfoLabels2, displayInfoTable2, printWriter);
                }
                else {
                    printWriter.println("Processing " + str + ", Problem getting properties table from the servlet engine . " + "table or labels are null");
                }
            }
            catch (Exception ex2) {
                printWriter.println("writeServletStatusPage: Exception in properties block: " + ex2.toString());
            }
        }
        else {
            printWriter.println("Processing " + str + ", Problem getting properties from the servlet engine." + " m_propDI is null");
        }
        printWriter.println("</body></center></html>");
        printWriter.close();
    }
    
    public void writeTable(final String[] array, final AiaDisplayInfoDesc[][] array2, final PrintWriter printWriter) {
        if (array2 != null) {
            try {
                final int length = array2.length;
                final int length2 = array2[0].length;
                printWriter.println(" <TABLE BORDER WIDTH=700>");
                printWriter.println("<COLGROUP>");
                printWriter.println("<THEAD>");
                printWriter.println("<TR>");
                for (int i = 0; i < array.length; ++i) {
                    printWriter.println("<TH>" + array[i] + "</TH>");
                }
                printWriter.println("</TR>");
                printWriter.println("</THEAD>");
                printWriter.println("<TBODY>");
                for (int j = 0; j < array2.length; ++j) {
                    printWriter.println("<TR>");
                    String string = "";
                    for (int k = 0; k < array2[0].length; ++k) {
                        if (array2[j][k] != null) {
                            final String translateAlign = this.translateAlign(array2[j][k].getAlignmentPref());
                            String data = array2[j][k].getData();
                            if (data == "") {
                                data = "None";
                            }
                            string = string + "<TD ALIGN=" + translateAlign + ">" + data + "</TD>";
                        }
                    }
                    printWriter.println(string);
                    printWriter.println("</TR>");
                }
                printWriter.println("</TBODY>");
                printWriter.println("</TABLE>");
            }
            catch (Exception ex) {
                printWriter.println("Problem creating HTML table in servlet." + ex.toString());
            }
        }
        else {
            printWriter.println("No table to display, try again later.");
        }
    }
    
    String translateAlign(final int n) {
        String s = null;
        switch (n) {
            case 0: {
                s = "CENTER";
                break;
            }
            case 2: {
                s = "RIGHT";
                break;
            }
            case 1: {
                s = "LEFT";
                break;
            }
        }
        return s;
    }
    
    public boolean authorizedCommand(final String s) {
        final AiaProperties aiaProperties = (AiaProperties)this.m_propDI;
        boolean b;
        if (aiaProperties.allowAiaCmds == 0) {
            b = false;
        }
        else if (aiaProperties.adminIPList == null || aiaProperties.adminIPList.length == 0) {
            b = true;
        }
        else {
            b = false;
            for (int i = 0; i < aiaProperties.adminIPList.length; ++i) {
                if (s.equals(aiaProperties.adminIPList[i])) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    static {
        AiaMgt.DEBUG_TRACE = false;
    }
}
