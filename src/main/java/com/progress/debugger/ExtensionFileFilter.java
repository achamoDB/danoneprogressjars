// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter
{
    String description;
    String[] extensions;
    
    public ExtensionFileFilter(final String s, final String s2) {
        this(s, new String[] { s2 });
    }
    
    public ExtensionFileFilter(final String description, final String[] array) {
        if (description == null) {
            this.description = array[0] + "{" + array.length + "}";
        }
        else {
            this.description = description;
        }
        this.toLower(this.extensions = array.clone());
    }
    
    private void toLower(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = array[i].toLowerCase();
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean accept(final File file) {
        if (file.isDirectory()) {
            return true;
        }
        final String lowerCase = file.getAbsolutePath().toLowerCase();
        for (int i = 0; i < this.extensions.length; ++i) {
            final String suffix = this.extensions[i];
            if (lowerCase.endsWith(suffix) && lowerCase.charAt(lowerCase.length() - suffix.length() - 1) == '.') {
                return true;
            }
        }
        return false;
    }
}
