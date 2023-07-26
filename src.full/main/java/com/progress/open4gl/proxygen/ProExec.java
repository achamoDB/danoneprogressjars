// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.progress.open4gl.Parameter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import com.progress.open4gl.Open4GLException;
import com.progress.message.pgMsg;

public class ProExec implements pgMsg
{
    private static final String lineSep;
    private static boolean m_bCreateProcessError;
    private static int NotProcessedYet;
    private static int Processing;
    private static int Done;
    
    public static int runCompiler(final boolean b, final String s, String absFilename, final String[] array) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final int jCompilerType = genInfo.getJCompilerType();
        final String jcpSwitch = genInfo.getJCPSwitch(jCompilerType);
        final String jSwitches = genInfo.getJSwitches();
        String string = "";
        boolean b2 = false;
        String s2;
        String s3;
        if (jCompilerType == 1) {
            s2 = genInfo.getJCompiler(jCompilerType, true);
            s3 = genInfo.getJClasspath(jCompilerType, true);
        }
        else {
            s2 = genInfo.getJCompiler(jCompilerType, true);
            s3 = genInfo.getJClasspath(jCompilerType, true);
        }
        absFilename = PGAppObj.getAbsFilename(null, absFilename);
        int n = 4;
        if (jSwitches != null && !jSwitches.equals("")) {
            b2 = true;
            ++n;
        }
        final String[] array2 = new String[n];
        array2[0] = s2;
        array2[1] = jcpSwitch;
        array2[2] = s3;
        int n2 = 3;
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            array2[2] = "\"" + array2[2] + "\"";
        }
        if (b2) {
            array2[n2] = jSwitches;
            ++n2;
        }
        if (absFilename.endsWith(".java")) {
            array2[n2] = absFilename;
        }
        else {
            array2[n2] = "@" + absFilename;
        }
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        int exec;
        try {
            exec = exec(array2, array, null);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            exec = 1;
        }
        return exec;
    }
    
    private static void CreateModuleList(final PGAppObj pgAppObj, final String s, final Vector vector, final boolean b) {
        if (b) {
            vector.addElement(s + "*.cs");
            return;
        }
        for (int i = 0; i < pgAppObj.m_pPerProcs.length; ++i) {
            vector.addElement(s + pgAppObj.m_pPerProcs[i].getProcDetail().getMethodName() + ".cs");
        }
        if (pgAppObj.m_pSubObjs != null) {
            for (int j = 0; j < pgAppObj.m_pSubObjs.length; ++j) {
                CreateModuleList(pgAppObj.m_pSubObjs[j], s, vector, b);
            }
        }
        vector.addElement(s + pgAppObj.m_strAppObj + ".cs");
        if (!pgAppObj.isSubAppObject()) {
            vector.addElement(s + "AssemblyInfo.cs");
        }
    }
    
    public static int runCSCCompiler(final PGAppObj pgAppObj, String string, final String[] array, final boolean b) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final int dnCompilerType = genInfo.getDNCompilerType();
        final String s = "/t:library";
        final Vector vector = new Vector<String>();
        String dnSwitches = "";
        String string2 = "";
        final Vector dataSetList = PGGenInfo.getDataSetList();
        final Vector dataTableList = PGGenInfo.getDataTableList();
        final String workDir = genInfo.getWorkDir();
        String string3 = null;
        String s2 = null;
        int n = 0;
        final String dnCompiler = genInfo.getDNCompiler(dnCompilerType, true);
        final String string4 = "/r:" + genInfo.getDNReference(true);
        string = PGAppObj.getAbsFilename(null, string) + "\\";
        final String string5 = "/out:" + string + pgAppObj.m_strAppObj + ".dll";
        if (dnCompilerType == 3) {
            dnSwitches = genInfo.getDNSwitches();
        }
        CreateModuleList(pgAppObj, string, vector, b);
        if (dataSetList.size() > 0) {
            s2 = dataSetList.elementAt(0).getNamespace();
        }
        else if (dataTableList.size() > 0) {
            s2 = dataTableList.elementAt(0).getNamespace();
        }
        if (s2 != null) {
            string3 = PGAppObj.getAbsFilename(null, PGAppObj.getDNPathFromNamespace(workDir, s2, false)) + "\\";
        }
        int n2;
        if (b) {
            n2 = 5 + vector.size();
            if (genInfo.useDNNamespaceAsDirs() && string3 != null && string3.compareTo(string) != 0) {
                n = 1;
                ++n2;
            }
        }
        else {
            n2 = 5 + vector.size() + dataSetList.size() + dataTableList.size();
        }
        final String[] array2 = new String[n2];
        array2[0] = dnCompiler;
        array2[1] = dnSwitches;
        array2[2] = s;
        array2[3] = "\"" + string4 + "\"";
        array2[4] = string5;
        int n3 = 5;
        if (b) {
            if (n == 1) {
                array2[n3++] = string3 + "*.cs";
            }
        }
        else {
            for (int i = 0; i < dataSetList.size(); ++i) {
                array2[n3++] = string3 + dataSetList.elementAt(i).getClassName() + ".cs";
            }
            for (int j = 0; j < dataTableList.size(); ++j) {
                array2[n3++] = string3 + dataTableList.elementAt(j).getClassName() + ".cs";
            }
        }
        for (int k = 0; k < vector.size(); ++k) {
            array2[n3++] = vector.elementAt(k);
        }
        for (int l = 0; l < n2; ++l) {
            string2 = string2 + array2[l] + " ";
        }
        genInfo.logIt(2, null, string2, 2);
        int exec;
        try {
            exec = exec(array2, array, string);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            if (!ProExec.m_bCreateProcessError) {
                exec = 1;
            }
            else {
                exec = 2;
            }
        }
        if (exec == 0) {
            final String dnReferencePath = genInfo.getDNReferencePath(true);
            if (!copyFile(dnReferencePath + "Progress.o4glrt.dll", string + "Progress.o4glrt.dll")) {
                genInfo.logIt(2, "PGLOG_CopyError", "Progress.o4glrt.dll", 3);
            }
            if (!copyFile(dnReferencePath + "Progress.Messages.dll", string + "Progress.Messages.dll")) {
                genInfo.logIt(2, "PGLOG_CopyError", "Progress.Messages.dll", 3);
            }
        }
        return exec;
    }
    
    public static String getDNPath(final String s) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String[] array = { null };
        String string = "";
        final int n = 2;
        final String[] array2 = new String[n];
        array2[0] = genInfo.getDNPathFinder();
        array2[1] = s;
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        try {
            exec(array2, array, null);
        }
        catch (Open4GLException ex) {
            array[0] = null;
        }
        return array[0];
    }
    
    public static int buildSTDataSet(final IPGStrongNameParam ipgStrongNameParam, final boolean b, final String[] array) {
        int n = buildDNDataSetExe(ipgStrongNameParam, array);
        if (n == 0) {
            n = runDNDataSetExe(ipgStrongNameParam, array);
        }
        if (n == 0) {
            n = runXSD(ipgStrongNameParam, array);
        }
        return n;
    }
    
    public static int buildDNDataSetExe(final IPGStrongNameParam ipgStrongNameParam, final String[] array) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String s = "/t:exe";
        String string = "";
        final int n = 4;
        final String className = ipgStrongNameParam.getClassName();
        final String string2 = PGAppObj.getAbsFilename(null, PGAppObj.getDNPathFromNamespace(genInfo.getWorkDir(), ipgStrongNameParam.getNamespace(), false)) + "\\";
        final String dnCompiler = genInfo.getDNCompiler(genInfo.getDNCompilerType(), true);
        final String[] array2 = new String[n];
        array2[0] = dnCompiler;
        array2[1] = s;
        array2[2] = "/out:" + string2 + className + ".exe";
        array2[3] = string2 + className + "Schema.cs";
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        int exec;
        try {
            exec = exec(array2, array, string2);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            if (!ProExec.m_bCreateProcessError) {
                exec = 1;
            }
            else {
                exec = 2;
            }
        }
        return exec;
    }
    
    public static int runDNDataSetExe(final IPGStrongNameParam ipgStrongNameParam, final String[] array) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        String string = "";
        final int n = 1;
        final String className = ipgStrongNameParam.getClassName();
        final String string2 = PGAppObj.getAbsFilename(null, PGAppObj.getDNPathFromNamespace(genInfo.getWorkDir(), ipgStrongNameParam.getNamespace(), false)) + "\\";
        final String[] array2 = new String[n];
        array2[0] = string2 + className + ".exe";
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        int exec;
        try {
            exec = exec(array2, array, string2);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            if (!ProExec.m_bCreateProcessError) {
                exec = 1;
            }
            else {
                exec = 2;
            }
        }
        return exec;
    }
    
    public static int runXSD(final IPGStrongNameParam ipgStrongNameParam, final String[] array) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String s = "/d";
        String string = "";
        final int n = 4;
        final String className = ipgStrongNameParam.getClassName();
        final String namespace = ipgStrongNameParam.getNamespace();
        final String string2 = PGAppObj.getAbsFilename(null, PGAppObj.getDNPathFromNamespace(genInfo.getWorkDir(), namespace, false)) + "\\";
        final String[] array2 = new String[n];
        array2[0] = genInfo.getDNXSDGenerator(genInfo.getDNCompilerType(), true);
        array2[1] = s;
        array2[2] = "/n:" + namespace;
        array2[3] = string2 + className + ".xsd";
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        int exec;
        try {
            exec = exec(array2, array, string2);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            if (!ProExec.m_bCreateProcessError) {
                exec = 1;
            }
            else {
                exec = 2;
            }
        }
        if (exec == 0) {
            try {
                if (ipgStrongNameParam.getParamType() == 15) {
                    convertToDataTableClass((PGDataTableParam)ipgStrongNameParam, array, string2);
                }
                else if (ipgStrongNameParam.getParamType() == 36) {
                    updateDataSetClass((PGDataSetParam)ipgStrongNameParam, array, string2);
                }
            }
            catch (IOException ex2) {
                array[0] = ex2.getMessage();
                exec = 3;
            }
        }
        return exec;
    }
    
    public static int convertToDataTableClass(final PGDataTableParam pgDataTableParam, final String[] array, final String s) throws IOException {
        final int n = 0;
        final String suffix = "[Serializable()]";
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        boolean b = true;
        final String string = s + pgDataTableParam.getClassName() + ".cs";
        final String string2 = s + "XX-YY-" + pgDataTableParam.getClassName() + ".cs.tmp";
        final String str = "        [Serializable()]";
        final String str2 = "        [System.ComponentModel.DesignerCategoryAttribute(\"code\")]";
        final String str3 = "        [System.ComponentModel.ToolboxItem(true)]";
        final String str4 = "                ProDataTable.SetBImageFlag(this, true);";
        final String s2 = "    using Progress.Open4GL;";
        final String str5 = "                this.Namespace = this.TableName + \"NS\";";
        final String str6 = "            public void Add(System.Object o){}";
        final String string3 = "public delegate void " + pgDataTableParam.getClassName() + "RowChangeEventHandler(object sender, " + pgDataTableParam.getClassName() + "RowChangeEvent e);";
        final String string4 = "public class " + pgDataTableParam.getClassName() + "DataTable : DataTable, System.Collections.IEnumerable {";
        final String string5 = "            internal " + pgDataTableParam.getClassName();
        final String string6 = "            internal " + pgDataTableParam.getClassName() + "DataTable() :";
        final String string7 = "            public " + pgDataTableParam.getClassName() + "DataTable() {";
        final String prefix = "            internal DataColumn";
        final String suffix2 = "this.InitClass();";
        final String string8 = "public event " + pgDataTableParam.getClassName() + "RowChangeEventHandler " + pgDataTableParam.getClassName() + "RowDeleting;";
        final String suffix3 = "System.Diagnostics.DebuggerNonUserCodeAttribute()]";
        final String suffix4 = "GetTypedTableSchema(System.Xml.Schema.XmlSchemaSet xs) {";
        final String suffix5 = "GetTypedTableSchema(global::System.Xml.Schema.XmlSchemaSet xs) {";
        final String string9 = "        public class " + pgDataTableParam.getClassName() + "DataTable : ProDataTable, System.Collections.IEnumerable {";
        int n9 = ProExec.NotProcessedYet;
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        try {
            bufferedReader = new BufferedReader(new FileReader(PGAppObj.getAbsFilename(null, string)));
            printWriter = new PrintWriter(new FileOutputStream(PGAppObj.getAbsFilename(null, string2)));
        }
        catch (FileNotFoundException ex) {
            array[0] = ex.getMessage();
            return 1;
        }
        bufferedReader.readLine();
        try {
            String str7 = bufferedReader.readLine();
            while (str7 != null) {
                String str8 = "";
                final int lastIndex;
                if (n2 == 0 && (lastIndex = str7.lastIndexOf("Version")) != -1) {
                    n2 = 1;
                    final String substring = str7.substring(lastIndex, lastIndex + 10);
                    if (substring.startsWith("Version:1") || substring.startsWith("Version: 1")) {
                        b = true;
                        n7 = 1;
                    }
                    else {
                        b = false;
                    }
                }
                else if (b && n3 == 0 && str7.endsWith(suffix)) {
                    int i = 0;
                    n3 = 1;
                    str8 = str7 + ProExec.lineSep;
                    while (i == 0) {
                        str7 = bufferedReader.readLine();
                        if (str7.endsWith(string3)) {
                            i = 1;
                        }
                    }
                }
                else if (b && n5 == 0 && str7.endsWith("using System.Runtime.Serialization;")) {
                    n5 = 1;
                    str8 = s2 + ProExec.lineSep;
                }
                else if (!b && n5 == 0 && str7.startsWith("namespace")) {
                    int j = 0;
                    n5 = 1;
                    n3 = 1;
                    final String string10 = str7 + ProExec.lineSep;
                    str7 = bufferedReader.readLine();
                    str8 = string10 + str7 + ProExec.lineSep + s2 + ProExec.lineSep + ProExec.lineSep;
                    while (j == 0) {
                        str7 = bufferedReader.readLine();
                        if (str7.endsWith(string3)) {
                            j = 1;
                        }
                    }
                }
                else if (n4 == 0 && str7.endsWith(string4)) {
                    n4 = 1;
                    str8 = str + ProExec.lineSep + str2 + ProExec.lineSep + str3 + ProExec.lineSep;
                    str7 = string9;
                }
                else if (n6 == 0 && str7.endsWith(string8)) {
                    n6 = 1;
                    str8 = str7;
                    str7 = ProExec.lineSep + str6;
                }
                else if (str7.startsWith(string5) || str7.startsWith(prefix)) {
                    if (n9 == ProExec.NotProcessedYet && str7.startsWith(string6)) {
                        n9 = ProExec.Processing;
                    }
                    str7 = insertVariable(str7, "internal", "public", 1);
                }
                else if (b && n8 == 0 && str7.startsWith("                    base(\"")) {
                    n8 = 1;
                    str7 = "                    base(\"" + pgDataTableParam.getOrigParamName() + "\") {" + ProExec.lineSep;
                }
                else if (n9 == ProExec.NotProcessedYet && str7.startsWith(string7)) {
                    n9 = ProExec.Processing;
                }
                else if (n7 == 0 && str7.endsWith(suffix3)) {
                    str7 = "";
                }
                else if (n7 == 0 && str7.endsWith(suffix4)) {
                    int k = 0;
                    n7 = 1;
                    while (k == 0) {
                        if (bufferedReader.readLine().endsWith("}")) {
                            k = 1;
                        }
                    }
                    str7 = (str8 = "");
                }
                else if (n7 == 0 && str7.endsWith(suffix5)) {
                    int n10 = 0;
                    int l = 0;
                    n7 = 1;
                    while (l == 0) {
                        if (bufferedReader.readLine().endsWith("}") && ++n10 == 10) {
                            l = 1;
                        }
                    }
                    str7 = (str8 = "");
                }
                else if (!b && n8 == 0 && str7.startsWith("                this.TableName = \"")) {
                    n8 = 1;
                    str7 = "                this.TableName = \"" + pgDataTableParam.getOrigParamName() + "\";" + ProExec.lineSep;
                }
                else if (n9 == ProExec.Processing && str7.endsWith(suffix2)) {
                    n9 = ProExec.Done;
                    if (pgDataTableParam.hasBeforeTable()) {
                        str7 = str7 + ProExec.lineSep + str4;
                    }
                    str7 = str7 + ProExec.lineSep + str5;
                    final String addDTExtentProperties = addDTExtentProperties(pgDataTableParam);
                    if (addDTExtentProperties != null) {
                        str7 += addDTExtentProperties;
                    }
                }
                final String string11 = str8 + str7;
                str7 = bufferedReader.readLine();
                if (str7 != null) {
                    printWriter.println(string11);
                }
            }
            bufferedReader.close();
            printWriter.close();
        }
        catch (IOException ex2) {
            array[0] = ex2.getMessage();
            return 1;
        }
        final File file = new File(PGAppObj.getAbsFilename(null, string2));
        final File dest = new File(PGAppObj.getAbsFilename(null, string));
        if (!dest.delete()) {
            throw new IOException("ERROR: IOException deleting " + string);
        }
        if (!file.renameTo(dest)) {
            throw new IOException("ERROR: IOException renaming " + string2);
        }
        return n;
    }
    
    public static int updateDataSetClass(final PGDataSetParam pgDataSetParam, final String[] array, final String s) throws IOException {
        final int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        final String string = s + pgDataSetParam.getClassName() + ".cs";
        final String string2 = s + "XX-YY-" + pgDataSetParam.getClassName() + ".cs.tmp";
        final String string3 = "public class " + pgDataSetParam.getClassName() + "DataSet : DataSet {";
        final String string4 = "        public class " + pgDataSetParam.getClassName() + "DataSet : ProDataSet {";
        final String suffix = "DataTable() :";
        final String suffix2 = "this.InitClass();";
        final String suffix3 = "using System.Runtime.Serialization;";
        final String str = "                ProDataTable.SetBImageFlag(this, true);";
        final String str2 = "    using Progress.Open4GL;";
        String dataTableName = "";
        int n5 = ProExec.NotProcessedYet;
        boolean b = true;
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        try {
            bufferedReader = new BufferedReader(new FileReader(PGAppObj.getAbsFilename(null, string)));
            printWriter = new PrintWriter(new FileOutputStream(PGAppObj.getAbsFilename(null, string2)));
        }
        catch (FileNotFoundException ex) {
            array[0] = ex.getMessage();
            return 1;
        }
        final int length = suffix.length();
        bufferedReader.readLine();
        try {
            for (String s2 = bufferedReader.readLine(); s2 != null; s2 = bufferedReader.readLine()) {
                final int length2 = s2.length();
                final int lastIndex;
                if (n2 == 0 && (lastIndex = s2.lastIndexOf("Version")) != -1) {
                    n2 = 1;
                    final String substring = s2.substring(lastIndex, lastIndex + 10);
                    b = (substring.startsWith("Version:1") || substring.startsWith("Version: 1"));
                }
                else if (n3 == 0 && s2.endsWith(string3)) {
                    n3 = 1;
                    s2 = string4;
                }
                else if (n4 == 0 && s2.endsWith(suffix3)) {
                    n4 = 1;
                    s2 = str2 + ProExec.lineSep + s2;
                }
                else if (!b && s2.startsWith("                this.TableName = \"")) {
                    final PGDataTableParam dataSetTable = pgDataSetParam.getDataSetTable(s2.substring(34, s2.indexOf(34, 35)));
                    if (dataSetTable != null) {
                        s2 = "                this.TableName = \"" + dataSetTable.getOrigParamName() + "\";" + ProExec.lineSep;
                    }
                }
                else if (b && s2.startsWith("                    base(\"")) {
                    final PGDataTableParam dataSetTable2 = pgDataSetParam.getDataSetTable(s2.substring(26, s2.indexOf(34, 27)));
                    if (dataSetTable2 != null) {
                        s2 = "                    base(\"" + dataSetTable2.getOrigParamName() + "\") {" + ProExec.lineSep;
                    }
                }
                else if (s2.startsWith("                if ((ds.Tables[\"")) {
                    final PGDataTableParam dataSetTable3 = pgDataSetParam.getDataSetTable(s2.substring(32, s2.indexOf(34, 33)));
                    if (dataSetTable3 != null) {
                        s2 = "                if ((ds.Tables[\"" + dataSetTable3.getOrigParamName() + "\"] != null)) {" + ProExec.lineSep;
                    }
                }
                else if (s2.startsWith("            if ((ds.Tables[\"")) {
                    final PGDataTableParam dataSetTable4 = pgDataSetParam.getDataSetTable(s2.substring(28, s2.indexOf(34, 29)));
                    if (dataSetTable4 != null) {
                        s2 = "                if ((ds.Tables[\"" + dataSetTable4.getOrigParamName() + "\"] != null)) {" + ProExec.lineSep;
                    }
                }
                else if (s2.endsWith("]));")) {
                    final int index = s2.indexOf("\"");
                    final String substring2 = s2.substring(0, index + 1);
                    final PGDataTableParam dataSetTable5 = pgDataSetParam.getDataSetTable(s2.substring(index + 1, s2.length() - 5));
                    if (dataSetTable5 != null) {
                        s2 = substring2 + dataSetTable5.getOrigParamName() + "\"]));" + ProExec.lineSep;
                    }
                }
                else if (n5 == ProExec.NotProcessedYet && length2 > length) {
                    final int beginIndex = length2 - length - 1;
                    if (s2.substring(beginIndex, beginIndex + length).endsWith(suffix)) {
                        dataTableName = getDataTableName(s2);
                        n5 = ProExec.Processing;
                    }
                }
                else if (n5 == ProExec.Processing && s2.endsWith(suffix2)) {
                    n5 = ProExec.NotProcessedYet;
                    final PGDataTableParam dataSetTable6 = pgDataSetParam.getDataSetTable(dataTableName);
                    if (dataSetTable6 != null) {
                        if (dataSetTable6.hasBeforeTable()) {
                            s2 = s2 + ProExec.lineSep + str;
                        }
                        final String addDTExtentProperties = addDTExtentProperties(dataSetTable6);
                        if (addDTExtentProperties != null) {
                            s2 += addDTExtentProperties;
                        }
                    }
                }
                printWriter.println(s2);
            }
            bufferedReader.close();
            printWriter.close();
        }
        catch (IOException ex2) {
            array[0] = ex2.getMessage();
            return 1;
        }
        final File file = new File(PGAppObj.getAbsFilename(null, string2));
        final File dest = new File(PGAppObj.getAbsFilename(null, string));
        if (!dest.delete()) {
            throw new IOException("ERROR: IOException deleting " + string);
        }
        if (!file.renameTo(dest)) {
            throw new IOException("ERROR: IOException renaming " + string2);
        }
        return n;
    }
    
    private static String getDataTableName(String trimBlanks) {
        String substring = "";
        if (trimBlanks != null && trimBlanks.length() > 0) {
            trimBlanks = trimBlanks(trimBlanks, true);
            substring = trimBlanks.substring(9, trimBlanks.length() - 13 - 1);
        }
        return substring;
    }
    
    private static String addDTExtentProperties(final PGDataTableParam pgDataTableParam) {
        final PGMetaData[] metaData = pgDataTableParam.getMetaData();
        final StringBuffer sb = new StringBuffer(4096);
        final String s = "                ProDataTable.SetExtentColumns(this, \"%FieldName%\", \"%ColumnName%\", %NumFields%);";
        final String s2 = "                ProDataTable.SetUserOrder(this.Columns[\"%ColumnName%\"], %UserOrder%);";
        final String s3 = "                ProDataTable.SetColumnProType(this.Columns[\"%ColumnName%\"], %ProType%);";
        for (int i = 0; i < metaData.length; ++i) {
            final PGMetaData pgMetaData = metaData[i];
            final int extent = pgMetaData.getExtent();
            if (extent > 1) {
                sb.append(ProExec.lineSep + insertVariable(insertVariable(insertVariable(s, "%FieldName%", pgMetaData.m_strFieldName, 1), "%ColumnName%", pgMetaData.m_strFieldName + "1", 1), "%NumFields%", Integer.toString(extent), 1));
                for (int j = 1; j <= extent; ++j) {
                    sb.append(ProExec.lineSep + insertVariable(insertVariable(s2, "%ColumnName%", pgMetaData.m_strFieldName + Integer.toString(j), 1), "%UserOrder%", Integer.toString(pgMetaData.getUserOrder()), 1));
                    sb.append(ProExec.lineSep + insertVariable(insertVariable(s3, "%ColumnName%", pgMetaData.m_strFieldName + Integer.toString(j), 1), "%ProType%", Parameter.proToProTypeName(pgMetaData.getType()), 1));
                }
            }
            else {
                sb.append(ProExec.lineSep + insertVariable(insertVariable(s2, "%ColumnName%", pgMetaData.m_strFieldName, 1), "%UserOrder%", Integer.toString(pgMetaData.getUserOrder()), 1));
                sb.append(ProExec.lineSep + insertVariable(insertVariable(s3, "%ColumnName%", pgMetaData.m_strFieldName, 1), "%ProType%", Parameter.proToProTypeName(pgMetaData.getType()), 1));
            }
        }
        return sb.toString();
    }
    
    protected static String insertVariable(final String s, final String s2, final String s3, final int n) {
        final StringBuffer sb = new StringBuffer(512);
        int beginIndex = 0;
        int i = s.indexOf(s2);
        int n2 = 0;
        final String str = (s3 == null) ? "" : s3;
        while (i >= 0) {
            ++n2;
            sb.append(s.substring(beginIndex, i));
            beginIndex = i + s2.length();
            sb.append(str);
            if (n2 <= n) {
                i = -1;
            }
            else {
                i = s.indexOf(s2, beginIndex);
            }
        }
        if (beginIndex < s.length()) {
            sb.append(s.substring(beginIndex));
        }
        return sb.toString();
    }
    
    public static int exec(final String[] cmdarray, final String[] array, final String pathname) throws Open4GLException {
        final Runtime runtime = Runtime.getRuntime();
        final StringBuffer sb = new StringBuffer(512);
        final String property = System.getProperty("line.separator");
        File dir = null;
        ProExec.m_bCreateProcessError = false;
        if (pathname != null) {
            dir = new File(pathname);
        }
        int wait;
        try {
            final Process exec = runtime.exec(cmdarray, null, dir);
            if (!PGGenInfo.isCSCCompiler(cmdarray[0])) {
                String line;
                while (null != (line = new BufferedReader(new InputStreamReader(exec.getErrorStream())).readLine())) {
                    sb.append(line);
                    sb.append(property);
                }
            }
            String line2;
            while (null != (line2 = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine())) {
                sb.append(line2);
                if (!PGGenInfo.isDNPathFinder(cmdarray[0])) {
                    sb.append(property);
                }
            }
            wait = exec.waitFor();
        }
        catch (IOException ex) {
            final Object[] array2 = { null };
            final String message = ex.getMessage();
            if (message != null && message.length() > 0 && message.startsWith("CreateProcess:")) {
                ProExec.m_bCreateProcessError = true;
            }
            array2[0] = cmdarray[0] + " //" + ex.getMessage();
            throw new Open4GLException(8099442454849133646L, array2);
        }
        catch (Exception ex2) {
            throw new Open4GLException(8099442454849133646L, new Object[] { cmdarray[0] + " //" + ex2.getMessage() });
        }
        array[0] = sb.toString();
        return wait;
    }
    
    public static int runWSDLGen(final String str, final String[] array) {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        final String fixOSPath = PGAppObj.fixOSPath(2, genInfo.getWorkDir());
        genInfo.getPackage();
        final int n = 8;
        final String[] array2 = new String[n];
        String string = "";
        final String jvm = genInfo.getJVM();
        genInfo.getJCPSwitch(1);
        final String javaPost11Classpath = genInfo.getJavaPost11Classpath();
        array2[0] = jvm;
        array2[1] = "-classpath";
        array2[2] = javaPost11Classpath;
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            array2[2] = "\"" + array2[2] + "\"";
        }
        String str2 = new File(fixOSPath).getAbsolutePath().replace('\\', '/');
        if (!str2.endsWith("/")) {
            str2 += "/";
        }
        String string2 = str2;
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            str2 = "\"" + str2 + "\"";
            string2 = "\"" + string2 + "\"";
        }
        array2[3] = "-DWorkDir=" + str2;
        array2[4] = "-DOutPath=" + string2;
        array2[5] = "-DWSMFile=" + str;
        String s = Generator.insertVariable("<Install Dir>", "<Install Dir>", PGGenInfo.INSTALLDIR);
        if (System.getProperty("os.name").indexOf("Windows") >= 0) {
            s = "\"" + s + "\"";
        }
        array2[6] = "-DInstallDir=" + s;
        array2[7] = "com.progress.open4gl.wsdlgen.WSDLMain";
        for (int i = 0; i < n; ++i) {
            string = string + array2[i] + " ";
        }
        genInfo.logIt(2, null, string, 2);
        int exec;
        try {
            exec = exec(array2, array, null);
        }
        catch (Open4GLException ex) {
            array[0] = ex.getMessage();
            exec = 1;
        }
        return exec;
    }
    
    private static String trimBlanks(final String s, final boolean b) {
        int index;
        int beginIndex;
        for (beginIndex = (index = 0); index < s.length() && s.charAt(index) == ' '; ++index) {
            ++beginIndex;
        }
        if (b) {
            s.trim();
        }
        return s.substring(beginIndex);
    }
    
    public static boolean copyFile(final String pathname, final String pathname2) {
        final File file = new File(pathname);
        final File file2 = new File(pathname2);
        final byte[] array = new byte[2048];
        boolean exists = file.exists();
        if (exists) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final FileOutputStream fileOutputStream = new FileOutputStream(file2);
                int read;
                while ((read = fileInputStream.read(array)) > 0) {
                    fileOutputStream.write(array, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (Exception ex) {
                exists = false;
            }
        }
        return exists;
    }
    
    static {
        lineSep = System.getProperty("line.separator");
        ProExec.m_bCreateProcessError = false;
        ProExec.NotProcessedYet = 1;
        ProExec.Processing = 2;
        ProExec.Done = 3;
    }
}
