// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.io.Serializable;

public final class SDOParameters implements Serializable
{
    private int fetchSize;
    private int maxPrefetchRows;
    private String rowId;
    private boolean stateless;
    private SDOScrollingMode scrollingMode;
    private boolean scrollingModeWasSet;
    public static final int PREFETCH_SIZE = 100000000;
    public static final int DEFAULT_FETCH_SIZE = 200;
    public static final int MINIMUM_FETCH_SIZE = 1;
    public static final int DEFAULT_MAXPREFETCH_ROWS = 100000000;
    public static final int MINIMUM_MAXPREFETCH_ROWS = 1;
    
    public SDOParameters() {
        this.scrollingModeWasSet = false;
        this.rowId = null;
        this.fetchSize = 200;
        this.maxPrefetchRows = 100000000;
        this.stateless = false;
        this.scrollingMode = SDOScrollingMode.KEEP_ROWS;
    }
    
    public void setRowIdentity(final String rowId) {
        this.rowId = rowId;
    }
    
    public String getRowIdentity() {
        return this.rowId;
    }
    
    public boolean scrollingModeWasSet() {
        return this.scrollingModeWasSet;
    }
    
    public void setScrollingMode(final SDOScrollingMode scrollingMode) {
        if (scrollingMode != null) {
            this.scrollingModeWasSet = true;
            this.scrollingMode = scrollingMode;
        }
    }
    
    public SDOScrollingMode getScrollingMode() {
        return this.scrollingMode;
    }
    
    public void setPrefetchMaxRows(final int maxPrefetchRows) {
        if (maxPrefetchRows < 1) {
            this.maxPrefetchRows = 1;
        }
        else {
            this.maxPrefetchRows = maxPrefetchRows;
        }
    }
    
    public int getPrefetchMaxRows() {
        return this.maxPrefetchRows;
    }
    
    public void setFetchSize(final int fetchSize) {
        if (fetchSize < 1) {
            this.fetchSize = 1;
        }
        else {
            this.fetchSize = fetchSize;
        }
    }
    
    public int getFetchSize() {
        return this.fetchSize;
    }
    
    public void setStateless(final boolean stateless) {
        this.stateless = stateless;
    }
    
    public boolean getStateless() {
        return this.stateless;
    }
}
