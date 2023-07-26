// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import org.apache.xerces.parsers.AbstractDOMParser;
import java.awt.Window;
import org.w3c.dom.Node;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import com.progress.wsa.admin.AppContainer;
import org.apache.xerces.parsers.DOMParser;
import com.sonicsw.mx.config.util.SonicFSFileSystem;
import com.sonicsw.ma.gui.IAssociationEditor;
import javax.swing.JFrame;

public class OEServiceDefResourceEditor extends JFrame implements IAssociationEditor
{
    private static final String APP_NAME = "OpenEdge WSM Editor";
    private static final String UNTITLED = "Untitled";
    private SonicFSFileSystem m_fs;
    protected DOMParser parser;
    
    public OEServiceDefResourceEditor() {
        this.parser = new DOMParser();
    }
    
    public void aeInit(final SonicFSFileSystem fs, final String[] array) {
        this.m_fs = fs;
    }
    
    public void aeOpenFile(final String s) throws Exception {
        final OEServiceDefEditor oeServiceDefEditor = new OEServiceDefEditor();
        oeServiceDefEditor.setFileSystem(this.m_fs);
        oeServiceDefEditor.setCurrentPath(s);
        final AppContainer appContainer = new AppContainer();
        final String content = this.m_fs.getContent(s);
        System.out.println(s);
        if (content != null) {
            this.parser.parse(new InputSource(new StringReader(content)));
            appContainer.readXML(((AbstractDOMParser)this.parser).getDocument().getDocumentElement());
        }
        if (oeServiceDefEditor.editInDialog(this, appContainer, true)) {
            this.m_fs.updateFile(s, oeServiceDefEditor.getAppContainer().toString(true), true);
        }
    }
    
    public void aeNewFile() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    public void aeSaveFile() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    public void aeSaveAsFile(final String s) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    public void aeClose() {
        throw new UnsupportedOperationException();
    }
}
