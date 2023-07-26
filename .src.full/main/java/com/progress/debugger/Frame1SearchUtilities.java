// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;

public class Frame1SearchUtilities
{
    private int indexInText;
    private int lastStartingIndex;
    private int lastSelectedIndex;
    private String lastWordSearch;
    boolean findHasBeenActivated;
    boolean caseMatchOn;
    boolean previousDirectionDown;
    boolean foundInPreviousSearch;
    
    public Frame1SearchUtilities() {
        this.indexInText = -1;
        this.lastStartingIndex = 0;
        this.lastSelectedIndex = 0;
        this.lastWordSearch = "";
        this.findHasBeenActivated = false;
        this.caseMatchOn = false;
        this.previousDirectionDown = true;
        this.foundInPreviousSearch = false;
    }
    
    int tableSearchDown(final Vector vector, String lowerCase, final boolean b) {
        if (lowerCase.compareTo(this.lastWordSearch) != 0) {
            this.foundInPreviousSearch = false;
            this.resetSearchRowIndex();
        }
        final int size = vector.size();
        if (!this.previousDirectionDown) {
            this.lastStartingIndex = this.lastSelectedIndex + 1;
        }
        for (int i = this.lastStartingIndex; i < size; ++i) {
            String s = vector.elementAt(i).elementAt(0).toString();
            if (!b) {
                s = s.toLowerCase();
                lowerCase = lowerCase.toLowerCase();
                this.caseMatchOn = false;
            }
            else {
                this.caseMatchOn = true;
            }
            final int index = s.indexOf(lowerCase);
            if (index != -1) {
                this.indexInText = index;
                this.lastSelectedIndex = i;
                this.lastStartingIndex = i + 1;
                this.lastWordSearch = lowerCase;
                this.findHasBeenActivated = true;
                this.previousDirectionDown = true;
                this.foundInPreviousSearch = true;
                return i;
            }
        }
        this.previousDirectionDown = true;
        if (this.foundInPreviousSearch) {
            this.resetSearchRowIndex();
            final int tableSearchDown = this.tableSearchDown(vector, lowerCase, b);
            if (tableSearchDown != -1) {
                return tableSearchDown;
            }
        }
        return -1;
    }
    
    int tableSearchUp(final Vector vector, String lowerCase, final boolean b) {
        if (lowerCase.compareTo(this.lastWordSearch) != 0) {
            this.foundInPreviousSearch = false;
            this.resetSearchRowIndex();
        }
        vector.size();
        if (this.previousDirectionDown) {
            this.lastStartingIndex = this.lastSelectedIndex - 1;
        }
        for (int i = this.lastStartingIndex; i > -1; --i) {
            String s = vector.elementAt(i).elementAt(0).toString();
            if (!b) {
                s = s.toLowerCase();
                lowerCase = lowerCase.toLowerCase();
                this.caseMatchOn = false;
            }
            else {
                this.caseMatchOn = true;
            }
            final int index = s.indexOf(lowerCase);
            if (index != -1) {
                this.indexInText = index;
                this.lastSelectedIndex = i;
                this.lastStartingIndex = i - 1;
                this.lastWordSearch = lowerCase;
                this.findHasBeenActivated = true;
                this.previousDirectionDown = false;
                this.foundInPreviousSearch = true;
                return i;
            }
        }
        this.previousDirectionDown = false;
        if (this.foundInPreviousSearch) {
            this.lastStartingIndex = vector.size() - 1;
            final int tableSearchUp = this.tableSearchUp(vector, lowerCase, b);
            if (tableSearchUp != -1) {
                return tableSearchUp;
            }
        }
        return -1;
    }
    
    void resetSearchRowIndex() {
        this.lastStartingIndex = 0;
        this.lastSelectedIndex = 0;
    }
    
    String getLastWordSearch() {
        return this.lastWordSearch;
    }
    
    int getRowIndex() {
        return this.lastSelectedIndex;
    }
    
    void setLastWordSearch(final String lastWordSearch) {
        this.lastWordSearch = lastWordSearch;
    }
}
