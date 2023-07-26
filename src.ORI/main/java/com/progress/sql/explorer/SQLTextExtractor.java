// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.io.IOException;
import com.progress.chimera.common.Tools;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class SQLTextExtractor implements ISQLConstants
{
    String m_statementTerminator;
    String m_commentIndicator;
    String m_altcommentIndicator;
    String m_statementIgnore;
    String m_statementContinuation;
    String m_unfilteredInput;
    String m_unfilteredComments;
    BufferedReader m_inputReader;
    BufferedWriter m_prompt;
    int m_promptCount;
    StringBuffer m_commandBuffer;
    static SQLExplorerLog m_log;
    
    public SQLTextExtractor(final BufferedReader inputReader) {
        this.m_statementTerminator = ";";
        this.m_commentIndicator = "#";
        this.m_altcommentIndicator = "--";
        this.m_statementIgnore = "!";
        this.m_statementContinuation = "_";
        this.m_unfilteredInput = "";
        this.m_unfilteredComments = "";
        this.m_inputReader = null;
        this.m_prompt = null;
        this.m_promptCount = 1;
        this.m_commandBuffer = null;
        this.m_inputReader = inputReader;
    }
    
    public SQLTextExtractor(final BufferedReader inputReader, final BufferedWriter prompt) {
        this.m_statementTerminator = ";";
        this.m_commentIndicator = "#";
        this.m_altcommentIndicator = "--";
        this.m_statementIgnore = "!";
        this.m_statementContinuation = "_";
        this.m_unfilteredInput = "";
        this.m_unfilteredComments = "";
        this.m_inputReader = null;
        this.m_prompt = null;
        this.m_promptCount = 1;
        this.m_commandBuffer = null;
        this.m_inputReader = inputReader;
        this.m_prompt = prompt;
    }
    
    public void clearUnfilteredInput() {
        this.m_unfilteredInput = "";
    }
    
    public String getUnfilteredInput() {
        return this.m_unfilteredInput;
    }
    
    public void setUnfilteredInput(final String str) {
        this.m_unfilteredInput += str;
    }
    
    public void clearUnfilteredComments() {
        this.m_unfilteredComments = "";
    }
    
    public String getUnfilteredComments() {
        return this.m_unfilteredComments;
    }
    
    public void setUnfilteredComments(final String str) {
        this.m_unfilteredComments += str;
    }
    
    public String getStatementTerminator() {
        return this.m_statementTerminator;
    }
    
    public void setStatementTerminator(final String statementTerminator) {
        this.m_statementTerminator = statementTerminator;
    }
    
    public String getCommentIndicator() {
        return this.m_commentIndicator;
    }
    
    public void setCommentIndicator(final String commentIndicator) {
        this.m_commentIndicator = commentIndicator;
    }
    
    public String getStatementContinuation() {
        return this.m_statementContinuation;
    }
    
    public void setStatementContinuation(final String statementContinuation) {
        this.m_statementContinuation = statementContinuation;
    }
    
    public String getStatementIgnore() {
        return this.m_statementIgnore;
    }
    
    public void setStatementIgnore(final String statementIgnore) {
        this.m_statementIgnore = statementIgnore;
    }
    
    private String readInput() {
        String s = "";
        int i = 1;
        while (i == 1) {
            String line;
            try {
                line = this.m_inputReader.readLine();
            }
            catch (IOException ex) {
                Tools.px(ex, "### Exception reading SQL Statement. ###");
                line = null;
            }
            if (line == null) {
                s = null;
                i = 0;
            }
            else {
                this.setUnfilteredInput(line + ISQLConstants.NEWLINE);
                final String upperCase = line.trim().toUpperCase();
                if (0 == upperCase.length()) {
                    this.displayPromptMain("");
                }
                else if (upperCase.startsWith(this.m_commentIndicator)) {
                    this.displayPromptMain("");
                    this.setUnfilteredComments(line + ISQLConstants.NEWLINE);
                }
                else if (upperCase.startsWith(this.m_altcommentIndicator)) {
                    this.displayPromptMain("");
                    this.setUnfilteredComments(line + ISQLConstants.NEWLINE);
                }
                else if (upperCase.startsWith("QUIT") || upperCase.startsWith("EXIT")) {
                    this.m_commandBuffer.setLength(0);
                    s = null;
                    i = 0;
                }
                else if (upperCase.endsWith(this.m_statementContinuation)) {
                    s = s + line.substring(0, line.lastIndexOf(this.m_statementContinuation)) + " ";
                }
                else {
                    s += line;
                    i = 0;
                }
            }
        }
        return s;
    }
    
    private void displayPromptContinue() {
        if (this.m_prompt != null) {
            try {
                this.m_prompt.write(this.m_promptCount++ + "> ");
                this.m_prompt.flush();
            }
            catch (IOException ex) {
                Tools.px("### Failed to prompt. ###", ex);
            }
        }
    }
    
    public void displayPromptMain(final String str) {
        if (this.m_prompt != null) {
            try {
                if (str.equals("")) {
                    this.m_prompt.write("SQLExplorer>");
                }
                else {
                    this.m_prompt.write("SQLExplorer>" + str + ISQLConstants.NEWLINE);
                }
                this.m_prompt.flush();
            }
            catch (IOException ex) {
                Tools.px("### Failed to prompt. ###", ex);
            }
        }
    }
    
    public String getNextStatement() {
        this.displayPromptMain("");
        return this.nextStatement();
    }
    
    private String processSpecialStatement(final String s) {
        String input = s;
        int n = 1;
        int n2 = 0;
        int n3 = 0;
        while (input != null && n == 1) {
            boolean b = false;
            final String trim = input.trim();
            final String upperCase = trim.toUpperCase();
            if (upperCase.startsWith("BEGIN")) {
                ++n2;
            }
            else if (upperCase.startsWith("END")) {
                b = true;
                ++n3;
            }
            if (b || trim.endsWith(this.m_statementTerminator)) {
                final int lastIndex = input.lastIndexOf(this.m_statementTerminator);
                if (n2 > 0 && n2 == n3) {
                    if (lastIndex > 0) {
                        this.m_commandBuffer.append(input.substring(0, lastIndex));
                    }
                    else if (b) {
                        this.m_commandBuffer.append(input);
                    }
                    n = 0;
                }
                else {
                    this.m_commandBuffer.append(input);
                }
            }
            else {
                this.m_commandBuffer.append(input);
            }
            this.m_commandBuffer.append(ISQLConstants.NEWLINE);
            if (n == 1) {
                this.displayPromptContinue();
                input = this.readInput();
            }
        }
        return input;
    }
    
    private String processStatement(final String s) {
        String input = s;
        for (int n = 1; input != null && n == 1; input = this.readInput()) {
            final String trim = input.trim();
            if (trim.endsWith(this.m_statementTerminator)) {
                this.m_commandBuffer.append(input.substring(0, input.lastIndexOf(this.m_statementTerminator)));
                n = 0;
            }
            else if (trim.startsWith("@") || trim.startsWith("!")) {
                this.m_commandBuffer.append(input);
                n = 0;
            }
            else {
                this.m_commandBuffer.append(input);
            }
            this.m_commandBuffer.append(ISQLConstants.NEWLINE);
            if (n == 1) {
                this.displayPromptContinue();
            }
        }
        return input;
    }
    
    public String nextStatement() {
        this.clearUnfilteredInput();
        this.clearUnfilteredComments();
        this.m_promptCount = 1;
        this.m_commandBuffer = new StringBuffer(131000);
        final String input;
        String s = input = this.readInput();
        if (input != null) {
            final String upperCase = input.trim().toUpperCase();
            if (upperCase.startsWith("CREATE PROCEDURE") || upperCase.startsWith("CREATE TRIGGER")) {
                s = this.processSpecialStatement(input);
            }
            else {
                s = this.processStatement(input);
            }
        }
        String string = this.m_commandBuffer.toString();
        if (s == null && this.m_commandBuffer.length() <= 0) {
            string = null;
        }
        return string;
    }
    
    static {
        SQLTextExtractor.m_log = SQLExplorerLog.get();
    }
}
