// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

public class HttpHeaderElement
{
    private String name;
    private String value;
    private NVPair[] parameters;
    
    public HttpHeaderElement(final String name) {
        this.name = name;
        this.value = null;
        this.parameters = new NVPair[0];
    }
    
    public HttpHeaderElement(final String name, final String value, final NVPair[] array) {
        this.name = name;
        this.value = value;
        if (array != null) {
            System.arraycopy(array, 0, this.parameters = new NVPair[array.length], 0, array.length);
        }
        else {
            this.parameters = new NVPair[0];
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public NVPair[] getParams() {
        return this.parameters;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof HttpHeaderElement && this.name.equalsIgnoreCase(((HttpHeaderElement)o).name);
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        this.appendTo(sb);
        return sb.toString();
    }
    
    public void appendTo(final StringBuffer sb) {
        sb.append(this.name);
        if (this.value != null) {
            if (Util.needsQuoting(this.value)) {
                sb.append("=\"");
                sb.append(Util.quoteString(this.value, "\\\""));
                sb.append('\"');
            }
            else {
                sb.append('=');
                sb.append(this.value);
            }
        }
        for (int i = 0; i < this.parameters.length; ++i) {
            sb.append(";");
            sb.append(this.parameters[i].getName());
            final String value = this.parameters[i].getValue();
            if (value != null) {
                if (Util.needsQuoting(value)) {
                    sb.append("=\"");
                    sb.append(Util.quoteString(value, "\\\""));
                    sb.append('\"');
                }
                else {
                    sb.append('=');
                    sb.append(value);
                }
            }
        }
    }
}
