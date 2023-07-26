// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;

class RetryException extends IOException
{
    Request request;
    Response response;
    RetryException first;
    RetryException next;
    IOException exception;
    boolean conn_reset;
    boolean restart;
    
    public RetryException() {
        this.request = null;
        this.response = null;
        this.first = null;
        this.next = null;
        this.exception = null;
        this.conn_reset = true;
        this.restart = false;
    }
    
    public RetryException(final String message) {
        super(message);
        this.request = null;
        this.response = null;
        this.first = null;
        this.next = null;
        this.exception = null;
        this.conn_reset = true;
        this.restart = false;
    }
    
    void addToListAfter(final RetryException ex) {
        if (ex == null) {
            return;
        }
        if (ex.next != null) {
            this.next = ex.next;
        }
        ex.next = this;
    }
}
