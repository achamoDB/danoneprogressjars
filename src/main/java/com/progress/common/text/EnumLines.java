// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.text;

import java.util.StringTokenizer;
import java.util.Enumeration;

public class EnumLines implements Enumeration
{
    private boolean m_ignoreEOLN;
    private boolean m_moreLines;
    private String m_nextLine;
    private boolean m_nextLinePrepared;
    private StringTokenizer m_tokenizer;
    
    public EnumLines(final String str) {
        this.m_ignoreEOLN = false;
        this.m_moreLines = true;
        this.m_nextLine = null;
        this.m_nextLinePrepared = false;
        this.m_tokenizer = new StringTokenizer(str, "\r\n", true);
    }
    
    public boolean hasMoreElements() {
        if (!this.m_nextLinePrepared) {
            this.prepareNextLine();
        }
        return this.m_moreLines;
    }
    
    public Object nextElement() {
        if (!this.m_nextLinePrepared) {
            this.prepareNextLine();
        }
        final String nextLine = this.m_nextLine;
        this.m_nextLinePrepared = false;
        this.m_nextLine = null;
        return nextLine;
    }
    
    private void prepareNextLine() {
        this.m_moreLines = true;
        this.m_nextLine = null;
        while (true) {
            while (this.m_tokenizer.hasMoreElements()) {
                final String nextLine = (String)this.m_tokenizer.nextElement();
                if (nextLine.equals("\r")) {
                    continue;
                }
                if (nextLine.equals("\n")) {
                    if (this.m_ignoreEOLN) {
                        this.m_ignoreEOLN = false;
                        continue;
                    }
                    this.m_nextLine = "";
                }
                else {
                    this.m_nextLine = nextLine;
                    this.m_ignoreEOLN = true;
                }
                this.m_nextLinePrepared = true;
                return;
            }
            this.m_moreLines = false;
            continue;
        }
    }
}
